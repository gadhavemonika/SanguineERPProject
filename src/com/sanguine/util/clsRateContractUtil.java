package com.sanguine.util;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class clsRateContractUtil implements Serializable {

	@Column(name = "strRateContNo")
	private String strRateContNo;

	@Column(name = "strProdCode")
	private String strProdCode;

	public String getStrRateContNo() {
		return strRateContNo;
	}

	public void setStrRateContNo(String strRateContNo) {
		this.strRateContNo = strRateContNo;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}
}
