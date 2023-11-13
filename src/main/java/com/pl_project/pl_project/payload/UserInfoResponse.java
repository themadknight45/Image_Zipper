package com.pl_project.pl_project.payload;

public class UserInfoResponse {
	private Integer id;
	private String username;
	private String email;

	public UserInfoResponse(Integer integer, String username, String email) {
		this.id = integer;
		this.username = username;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
