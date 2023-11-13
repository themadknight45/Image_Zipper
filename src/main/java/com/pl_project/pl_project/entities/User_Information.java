package com.pl_project.pl_project.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name ="User_information")
@Entity
public class User_Information {
	public User_Information(Long id, String username, String inputfileUrl, String compressesfileUrl) {
		super();
		this.id = id;
		this.username = username;
		this.inputfileUrl = inputfileUrl;
		this.compressesfileUrl = compressesfileUrl;
	}
	@Id
    private Long id;
	@Column
    private String username;
    public User_Information() {
		super();
	}
	@Column
    private String inputfileUrl;
    @Column
    private String compressesfileUrl;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getInputfileUrl() {
		return inputfileUrl;
	}
	public void setInputfileUrl(String inputfileUrl) {
		this.inputfileUrl = inputfileUrl;
	}
	public String getCompressesfileUrl() {
		return compressesfileUrl;
	}
	public void setCompressesfileUrl(String compressesfileUrl) {
		this.compressesfileUrl = compressesfileUrl;
	}
	@Override
	public String toString() {
		return "User_Information [id=" + id + ", username=" + username + ", inputfileUrl=" + inputfileUrl
				+ ", compressesfileUrl=" + compressesfileUrl + "]";
	}
    
  
}
