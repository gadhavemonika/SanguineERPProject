package com.sanguine.webpms.model;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class clsReservationDtlModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsReservationDtlModel() {
	}

	// Variable Declaration
	private String strRoomType;

	private String strRoomNo;

	private String strGuestCode;

	private String strExtraBedCode;

	private String strPayee;
	
	private String strRemark;
	
	private String strAddress;

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
		this.strRoomNo = (String) setDefaultValue(strRoomNo, "");
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

	public String getStrPayee() {
		return strPayee;
	}

	public void setStrPayee(String strPayee) {
		this.strPayee = strPayee;
	}

	public String getStrRemark() {
		return strRemark;
	}

	public void setStrRemark(String strRemark) {
		this.strRemark = strRemark;
	}

	public String getStrAddress() {
		return strAddress;
	}

	public void setStrAddress(String strAddress) {
		this.strAddress = (String) setDefaultValue(strAddress, "");
	}

}
