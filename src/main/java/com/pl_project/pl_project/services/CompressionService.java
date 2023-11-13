package com.pl_project.pl_project.services;

// import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;
@Service
public interface CompressionService {
	public String compressFile(String input_path,String path,String name) throws IOException;
}
