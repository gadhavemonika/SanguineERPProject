package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class clsTransporterModelDtl implements Serializable {

	// Variable Declaration

	@Column(name = "strVehCode")
	private String strVehCode;

	@Column(name = "strVehNo")
	private String strVehNo;

	public String getStrVehCode() {
		return strVehCode;
	}

	public void setStrVehCode(String strVehCode) {
		this.strVehCode = strVehCode;
	}

	public String getStrVehNo() {
		return strVehNo;
	}

	public void setStrVehNo(String strVehNo) {
		this.strVehNo = strVehNo;
	}
}
