package com.sanguine.webpms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

@Embeddable
public class clsWalkinDtl implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsWalkinDtl() {
	}

	// Variable Declaration
	@Column(name = "strRoomType")
	private String strRoomType;

	@Column(name = "strRoomNo")
	private String strRoomNo;

	@Column(name = "intNoOfAdults")
	private int intNoOfAdults;

	@Column(name = "intNoOfChild")
	private int intNoOfChild;

	@Column(name = "strGuestCode")
	private String strGuestCode;

	@Column(name = "strExtraBedCode")
	private String strExtraBedCode;
	
	@Column(name = "strRemark")
	private String strRemark;

	// Setter-Getter Methods
	public String getStrRoomType() {
		return strRoomType;
	}

	public void setStrRoomType(String strRoomType) {
		this.strRoomType = (String) setDefaultValue(strRoomType, "");
	}

	public String getStrRoomNo() {
		return strRoomNo;
	}

	public void setStrRoomNo(String strRoomNo) {
		this.strRoomNo = (String) setDefaultValue(strRoomNo, "NA");
	}

	public int getIntNoOfAdults() {
		return intNoOfAdults;
	}

	public void setIntNoOfAdults(int intNoOfAdults) {
		this.intNoOfAdults = (Integer) setDefaultValue(intNoOfAdults, "0");
	}

	public int getIntNoOfChild() {
		return intNoOfChild;
	}

	public void setIntNoOfChild(int intNoOfChild) {
		this.intNoOfChild = (Integer) setDefaultValue(intNoOfChild, "0");
	}

	// Function to Set Default Values
	private Object setDefaultValue(Object value, Object defaultValue) {
		if (value != null && (value instanceof String && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Double && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Integer && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Long && value.toString().length() > 0)) {
			return value;
		} else {
			return defaultValue;
		}
	}

	public String getStrGuestCode() {
		return strGuestCode;
	}

	public void setStrGuestCode(String strGuestCode) {
		this.strGuestCode = strGuestCode;
	}

	public String getStrExtraBedCode() {
		return strExtraBedCode;
	}

	public void setStrExtraBedCode(String strExtraBedCode) {
		this.strExtraBedCode = strExtraBedCode;
	}

	public String getStrRemark() {
		return strRemark;
	}

	public void setStrRemark(String strRemark) {
		this.strRemark = strRemark;
	}

}
