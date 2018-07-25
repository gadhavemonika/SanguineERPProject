package com.sanguine.model;

import javax.persistence.Embeddable;

@Embeddable
public class clsUserLocationDtl {

	private String strPropertyCode;

	private String strModule;

	private String strLocCode;

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrModule() {
		return strModule;
	}

	public void setStrModule(String strModule) {
		this.strModule = strModule;
	}
}
