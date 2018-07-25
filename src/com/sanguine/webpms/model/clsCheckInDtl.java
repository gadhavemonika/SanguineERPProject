package com.sanguine.webpms.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class clsCheckInDtl implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsCheckInDtl() {
	}

	// Variable Declaration
	private String strRegistrationNo;

	private String strGuestCode;

	private String strPayee;

	private String strRoomNo;

	private int intNoOfFolios;

	private String strExtraBedCode;
	
	private String strRoomType;

	// Setter-Getter Methods
	public String getStrRegistrationNo() {
		return strRegistrationNo;
	}

	public void setStrRegistrationNo(String strRegistrationNo) {
		this.strRegistrationNo = (String) setDefaultValue(strRegistrationNo, "");
	}

	public String getStrGuestCode() {
		return strGuestCode;
	}

	public void setStrGuestCode(String strGuestCode) {
		this.strGuestCode = (String) setDefaultValue(strGuestCode, "");
	}

	public String getStrPayee() {
		return strPayee;
	}

	public void setStrPayee(String strPayee) {
		this.strPayee = (String) setDefaultValue(strPayee, "");
	}

	public String getStrRoomNo() {
		return strRoomNo;
	}

	public void setStrRoomNo(String strRoomNo) {
		this.strRoomNo = (String) setDefaultValue(strRoomNo, "");
	}

	public int getIntNoOfFolios() {
		return intNoOfFolios;
	}

	public void setIntNoOfFolios(int intNoOfFolios) {
		this.intNoOfFolios = (Integer) setDefaultValue(intNoOfFolios, "0");
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

	public String getStrRoomType() {
		return strRoomType;
	}

	public void setStrRoomType(String strRoomType) {
		this.strRoomType = strRoomType;
	}

}
