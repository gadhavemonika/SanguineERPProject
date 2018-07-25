package com.sanguine.util;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class clsGRNDtlUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "strGRNCode")
	private String strGRNCode;

	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public String getStrGRNCode() {
		return strGRNCode;
	}

	public void setStrGRNCode(String strGRNCode) {
		this.strGRNCode = strGRNCode;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}
}
