package com.alex.bean;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.annotation.JsonProperty;

public class user {

	@JsonProperty
	private String UserName;
	@JsonProperty
	private String pass_hash;
	
	public user()
	{
		
	}
	
	
	public user(String userName, String pass_hash) {
		super();
		UserName = userName;
		this.pass_hash = pass_hash;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getPass_hash() {
		return pass_hash;
	}
	public void setPass_hash(String pass_hash) {
		this.pass_hash = pass_hash;
	}
	
	public String toHash()
	{
		return this.getPass_hash();
	}


	
	
}
