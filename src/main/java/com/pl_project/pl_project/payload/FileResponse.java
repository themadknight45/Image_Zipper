package com.pl_project.pl_project.payload;
public class FileResponse {
	public FileResponse() {
		super();
		this.fileName="";
		this.messase="Error Uploading file";
		this.downloadLink="Link couldn't be generated";
	}
	public FileResponse(String fileName, String messase, String downloadLink) {
		super();
		this.fileName = fileName;
		this.messase = messase;
		this.downloadLink = downloadLink;
	}
	private String fileName;
	private String messase;
	private String downloadLink;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getMessase() {
		return messase;
	}
	public void setMessase(String messase) {
		this.messase = messase;
	}
	public String getDownloadLink() {
		return downloadLink;
	}
	public void setDownloadLink(String downloadLink) {
		this.downloadLink = downloadLink;
	}
	
}
