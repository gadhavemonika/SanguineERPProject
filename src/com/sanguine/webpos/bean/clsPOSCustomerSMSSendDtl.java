package com.sanguine.webpos.bean;

public class clsPOSCustomerSMSSendDtl {
	private String strCustomerName;
	private long strMobileNumber;
	private String strMessage;

	private String strClientCode;

	public String getStrCustomerName() {
		return strCustomerName;
	}

	public void setStrCustomerName(String strCustomerName) {
		this.strCustomerName = strCustomerName;
	}

	public long getStrMobileNumber() {
		return strMobileNumber;
	}

	public void setStrMobileNumber(long strMobileNumber) {
		this.strMobileNumber = strMobileNumber;
	}

	public String getStrMessage() {
		return strMessage;
	}

	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}
}
