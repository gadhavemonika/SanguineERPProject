package com.sanguine.webclub.bean;

import com.mysql.jdbc.Blob;

public class clsWebClubMemberPhotoBean {

	private String strMemberCode;

	private String strMemberName;

	private Blob strMemberImage;

	public String getStrMemberCode() {
		return strMemberCode;
	}

	public void setStrMemberCode(String strMemberCode) {
		this.strMemberCode = strMemberCode;
	}

	public String getStrMemberName() {
		return strMemberName;
	}

	public void setStrMemberName(String strMemberName) {
		this.strMemberName = strMemberName;
	}

	public Blob getStrMemberImage() {
		return strMemberImage;
	}

	public void setStrMemberImage(Blob strMemberImage) {
		this.strMemberImage = strMemberImage;
	}

}
