package com.pl_project.pl_project.services;

// import java.io.File;
import java.io.IOException;
// import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pl_project.pl_project.payload.FileResponse;
import com.pl_project.pl_project.payload.HistoryResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public interface FileService {
	FileResponse uploadImage(String path,MultipartFile file) throws IOException;
	void DownloadImage(HttpServletRequest request, HttpServletResponse response, String fileName) throws IOException;
	HistoryResponse listConversions(String username)throws IOException;
}

