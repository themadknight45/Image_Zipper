package com.pl_project.pl_project.controllers;
import java.io.IOException;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.autoconfigure.cache.CacheProperties.Couchbase;
// import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.objenesis.instantiator.basic.NewInstanceInstantiator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
// import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pl_project.pl_project.payload.FileResponse;
import com.pl_project.pl_project.payload.HistoryResponse;
// import com.pl_project.pl_project.security.jwt.AuthTokenFilter;
import com.pl_project.pl_project.services.FileService;

import io.github.bucket4j.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.core.io.InputStreamResource;
// import org.springframework.stereotype.Controller;
// import org.springframework.util.FileCopyUtils;

@RestController
@RequestMapping("/file")
public class FileController {
	@Value("${project.image}")
	private String path;
	@Value("${project.token}")
	private int Token;
	
	@Autowired
	private FileService fileService;
	
	// private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
	
	private final Bucket bucket;
	public FileController() {
		Bandwidth limit = Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder()
            .addLimit(limit)
            .build();
	}
	
	@PostMapping("/upload")
	public ResponseEntity<FileResponse>fileUploadController(
		@RequestParam("image")MultipartFile image){
			if (bucket.tryConsume(1)) {
				FileResponse fileResponse=new FileResponse();
				try {
					String Username = SecurityContextHolder.getContext().getAuthentication().getName();
					fileResponse = fileService.uploadImage(Username, image);
				} catch (IOException e) {
					e.printStackTrace();
					return new ResponseEntity<>(fileResponse,HttpStatus.INTERNAL_SERVER_ERROR);
				}
				return new ResponseEntity<>(fileResponse,HttpStatus.OK);
			}
			return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
	}
	
	@GetMapping("/download/{filetype}/{username}/{fileName:.+}")
	public void fileDownloadController(
			HttpServletRequest request, HttpServletResponse response,@PathVariable String filetype,@PathVariable String username,
			@PathVariable("fileName") String fileName) throws IOException {
//			logger.info(filetype+"/"+username+"/"+fileName);
			fileService.DownloadImage(request,response,filetype+"/"+username+"/"+fileName);
	}
	
	@GetMapping("/list")
	public ResponseEntity<HistoryResponse>List_Conversions(){
			String Username = SecurityContextHolder.getContext().getAuthentication().getName();
			HistoryResponse response=new HistoryResponse();
			try {
				response = fileService.listConversions(Username);
			} catch (IOException e) {
				e.printStackTrace();
				return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
	@ExceptionHandler()
	public Exception exceptionHandler() {
		return new Exception("exception somewhere");
	}
	
}
