package com.sanguine.webpms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsMarketSourceMasterModel_ID implements Serializable {
	
	@Column(name = "strMarketSourceCode")
	private String strMarketSourceCode;

	@Column(name = "strClientCode")
	private String strClientCode;
	
	public clsMarketSourceMasterModel_ID() {
	}

	public clsMarketSourceMasterModel_ID(String strMarketSourceCode, String strClientCode) {
		this.strMarketSourceCode = strMarketSourceCode;
		this.strClientCode = strClientCode;
	}

	public String getStrMarketSourceCode() {
		return strMarketSourceCode;
	}

	public void setStrMarketSourceCode(String strMarketSourceCode) {
		this.strMarketSourceCode = strMarketSourceCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}
	
	

}
