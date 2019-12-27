package com.edu.sejong.model;

public class Excel {
	private String contents1 = "테스트 내용1";
	private String contents2 = "테스트 내용2";
	private String contents3 = "테스트 내용3";
	private String contents4 = "테스트 내용4";
	
	public String getContents1() {
		return contents1;
	}
	public void setContents1(String contents1) {
		this.contents1 = contents1;
	}
	public String getContents2() {
		return contents2;
	}
	public void setContents2(String contents2) {
		this.contents2 = contents2;
	}
	public String getContents3() {
		return contents3;
	}
	public void setContents3(String contents3) {
		this.contents3 = contents3;
	}
	public String getContents4() {
		return contents4;
	}
	public void setContents4(String contents4) {
		this.contents4 = contents4;
	}
	
	@Override
	public String toString() {
		return "Excel [contents1=" + contents1 + ", contents2=" + contents2 + ", contents3=" + contents3
				+ ", contents4=" + contents4 + "]";
	}
	
}
