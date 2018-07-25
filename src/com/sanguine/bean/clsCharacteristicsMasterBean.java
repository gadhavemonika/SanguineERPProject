package com.sanguine.bean;

import org.hibernate.validator.constraints.NotEmpty;

public class clsCharacteristicsMasterBean {
	private String strCharCode;
	@NotEmpty
	private String strCharName;
	private String strCharType;
	private String strCharDesc;

	public String getStrCharCode() {
		return strCharCode;
	}

	public void setStrCharCode(String strCharCode) {
		this.strCharCode = strCharCode;
	}

	public String getStrCharName() {
		return strCharName;
	}

	public void setStrCharName(String strCharName) {
		this.strCharName = strCharName;
	}

	public String getStrCharType() {
		return strCharType;
	}

	public void setStrCharType(String strCharType) {
		this.strCharType = strCharType;
	}

	public String getStrCharDesc() {
		return strCharDesc;
	}

	public void setStrCharDesc(String strCharDesc) {
		this.strCharDesc = strCharDesc;
	}
}
