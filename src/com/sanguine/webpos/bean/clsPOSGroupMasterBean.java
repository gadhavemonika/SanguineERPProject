package com.sanguine.webpos.bean;

public class clsPOSGroupMasterBean {

	private String strGroupCode;

	private String strGroupName;

	private String strOperational;

	private String strShortName;

	private String strOperationType;

	private Boolean strApplicableYN;

	public String getStrShortName() {
		return strShortName;
	}

	public void setStrShortName(String strShortName) {
		this.strShortName = strShortName;
	}

	public String getStrGroupCode() {
		return strGroupCode;
	}

	public void setStrGroupCode(String strGroupCode) {
		this.strGroupCode = strGroupCode;
	}

	public String getStrGroupName() {
		return strGroupName;
	}

	public void setStrGroupName(String strGroupName) {
		this.strGroupName = strGroupName;
	}

	public String getStrOperational() {
		return strOperational;
	}

	public void setStrOperational(String strOperational) {
		this.strOperational = strOperational;
	}

	public String getStrOperationType() {
		return strOperationType;
	}

	public void setStrOperationType(String strOperationType) {
		this.strOperationType = strOperationType;
	}

	public Boolean getStrApplicableYN() {
		return strApplicableYN;
	}

	public void setStrApplicableYN(Boolean strApplicableYN) {
		this.strApplicableYN = strApplicableYN;
	}

}
