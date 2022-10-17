package com.VO;
// 피카츄 모델
public class PikachuVO {
	
	private String Pname;
	private int Plevel;
	private int Peng;
	private String id;
	
	public PikachuVO(String id, String pname, int plevel, int peng) {
		this.Pname = pname;
		this.Plevel = plevel;
		this.Peng = peng;
		this.id = id;
	}
	
	public PikachuVO(int plevel, int peng) {
		this.Plevel = plevel;
		this.Peng = peng;
	}
	
	public PikachuVO(String pname) {
		this.Pname = pname;
	}
	
	public PikachuVO() {
		
	}

	public String getPname() {
		return Pname;
	}

	public void setPname(String pname) {
		Pname = pname;
	}

	public int getPlevel() {
		return Plevel;
	}

	public void setPlevel(int plevel) {
		Plevel = plevel;
	}

	public int getPeng() {
		return Peng;
	}

	public void setPeng(int peng) {
		Peng = peng;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
