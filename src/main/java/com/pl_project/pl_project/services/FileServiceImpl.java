package com.pl_project.pl_project.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;
import com.pl_project.pl_project.dao.UserInformationDao;
import com.pl_project.pl_project.entities.User_Information;
import com.pl_project.pl_project.payload.FileResponse;
import com.pl_project.pl_project.payload.HistoryResponse;
import com.pl_project.pl_project.security.jwt.AuthTokenFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Service
public class FileServiceImpl implements FileService{
	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
	@Autowired
	CompressionService compressionService;
	@Value("${project.image}")
	private String path;
	@Autowired 
	private UserInformationDao infodao;
	
	private static String EXTERNAL_FILE_PATH = "";
	
	public void setExternal_file_path() throws IOException{
		if(EXTERNAL_FILE_PATH.length()==0) {
			File f1 = new File(path);
			if(!f1.exists()) {
				f1.mkdir();
			}
			String temp = f1.getCanonicalPath();
			temp = temp.substring(0, temp.length()-6);
			
			
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<temp.length();i++) {
				if(temp.charAt(i)=='\\') {
					sb.append('/');
				}
				else {
					sb.append(temp.charAt(i));
				}
			}
			EXTERNAL_FILE_PATH = sb.toString();
			logger.info("logger val "+EXTERNAL_FILE_PATH);
		}
	}
	
	@Override
	public FileResponse uploadImage(String username, MultipartFile file) throws IOException {
		setExternal_file_path();
		String temppath=path+"/"+username;
		//create folder if not created
		File f=new File(temppath);	
		if(!f.exists()) {
			f.mkdir();
		}
		//File name
		 String name=file.getOriginalFilename();
		
		//RandomName
		String randomID=UUID.randomUUID().toString();

		String fileName1= randomID.concat(name.substring(name.lastIndexOf('.')));

		//Fullpath
		String filePath=temppath+"/"+fileName1;
		Files.copy(file.getInputStream(), Paths.get(filePath));
		String compressedFilePath=compressionService.compressFile(filePath, temppath,name);
		
		User_Information info=new User_Information(); 
		info.setId(infodao.count()+1);
		info.setUsername(username);info.setInputfileUrl(filePath);info.setCompressesfileUrl(compressedFilePath);
		infodao.save(info);	
		compressedFilePath="/file/download/"+compressedFilePath;
		FileResponse response=new FileResponse(name, "sucessfully_uploaded", compressedFilePath);
		return response;
	}
	
	@Override
	public void DownloadImage(HttpServletRequest request, HttpServletResponse response, String fileName)throws IOException {
		setExternal_file_path();
		File file = new File(EXTERNAL_FILE_PATH + fileName);
		if (file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());

			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

			FileCopyUtils.copy(inputStream, response.getOutputStream());

		}
		else {
			response.sendError(-2, "file "+fileName+" doesn't exist");
		}
	}

	@Override
	public HistoryResponse listConversions(String username) throws IOException {
		HistoryResponse response=new HistoryResponse();
		List<List<String>>l=new ArrayList<>();
		List<User_Information>list =infodao.findByusername(username);
		for(User_Information u:list) {
			List<String>temp=new ArrayList<>();
			temp.add("/file/download/"+u.getInputfileUrl());
			temp.add("/file/download/"+u.getCompressesfileUrl());
			l.add(temp);
		}
		response.setList(l);
		response.setResponse_msg("Here are your past conversions");
		return response;
	}
	@ExceptionHandler()
	public String exceptionHandler() {
		return "exception somewhere";
	}
}
