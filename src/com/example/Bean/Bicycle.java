package com.example.Bean;

public class Bicycle {
	private String carnumber,password,id;

	public Bicycle(String carnumber,String password){
		this.carnumber = carnumber;
		this.password = password;
	}
	public Bicycle(String id,String carnumber,String password){
		this.id = id;
		this.carnumber = carnumber;
		this.password = password;
	}
	public String getCarnumber() {
		return carnumber;
	}

	public void setCarnumber(String carnumber) {
		this.carnumber = carnumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
