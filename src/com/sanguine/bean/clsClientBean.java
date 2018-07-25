package com.sanguine.bean;

import org.hibernate.validator.constraints.NotEmpty;

public class clsClientBean {
	@NotEmpty
	private String strClientCode;
	@NotEmpty
	private String strPassword;

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrPassword() {
		return strPassword;
	}

	public void setStrPassword(String strPassword) {
		this.strPassword = strPassword;
	}

}
