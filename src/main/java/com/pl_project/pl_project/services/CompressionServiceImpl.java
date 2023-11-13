package com.pl_project.pl_project.services;

// import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
// import java.io.InputStream;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.util.Iterator;
import java.util.UUID;
// import java.io.ByteArrayOutputStream;
// import javax.imageio.IIOImage;
// import javax.imageio.ImageIO;
// import javax.imageio.ImageWriteParam;
// import javax.imageio.ImageWriter;
// import javax.imageio.stream.ImageOutputStream;
// import java.io.FileOutputStream;
// import java.io.OutputStream;
// import java.nio.file.Path;
// import java.nio.file.Paths;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.multipart.MultipartFile;
import net.coobird.thumbnailator.Thumbnails;

@Service
public class CompressionServiceImpl implements CompressionService{
	@Override
	public String compressFile(String input_path,String path,String name) throws IOException {
		File input = new File(input_path);
		String randomID=UUID.randomUUID().toString();
		String fileName1= randomID.concat(name.substring(name.lastIndexOf('.')));
		String outPath=path+"/"+fileName1;
		File output = new File(outPath);
		Thumbnails.of(input).scale(1) .outputQuality(0.1).toFile(output);
		return outPath;
	}
	@ExceptionHandler()
	public String exceptionHandler() {
		return "exception somewhere";
	}
}
