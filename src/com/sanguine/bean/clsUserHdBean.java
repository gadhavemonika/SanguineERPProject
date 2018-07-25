package com.sanguine.bean;

import org.hibernate.validator.constraints.NotEmpty;

public class clsUserHdBean {
	@NotEmpty
	private String strUserCode;
	private String strUserName;
	@NotEmpty
	private String strPassword;
	private String strSuperUser, strType, strRetire, strProperty, strLoginStatus;

	public String getStrUserCode() {
		return strUserCode;
	}

	public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
	}

	public String getStrUserName() {
		return strUserName;
	}

	public void setStrUserName(String strUserName) {
		this.strUserName = strUserName;
	}

	public String getStrPassword() {
		return strPassword;
	}

	public void setStrPassword(String strPassword) {
		this.strPassword = strPassword;
	}

	public String getStrSuperUser() {
		return strSuperUser;
	}

	public void setStrSuperUser(String strSuperUser) {
		this.strSuperUser = strSuperUser;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getStrRetire() {
		return strRetire;
	}

	public void setStrRetire(String strRetire) {
		this.strRetire = strRetire;
	}

	public String getStrProperty() {
		return strProperty;
	}

	public void setStrProperty(String strProperty) {
		this.strProperty = strProperty;
	}

	public String getStrLoginStatus() {
		return strLoginStatus;
	}

	public void setStrLoginStatus(String strLoginStatus) {
		this.strLoginStatus = strLoginStatus;
	}
}
