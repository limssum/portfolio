package com.VO;
// 회원정보 모델
public class PlayerVO {
	
	private String id;
	private String pw;
	
	
	public PlayerVO(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}
	
	public PlayerVO(String id) {
		this.id = id;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPw() {
		return pw;
	}


	public void setPw(String pw) {
		this.pw = pw;
	}
	
	

}
