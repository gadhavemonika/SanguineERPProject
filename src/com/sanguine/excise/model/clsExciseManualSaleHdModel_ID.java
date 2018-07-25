package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsExciseManualSaleHdModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "intId")
	private long intId;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsExciseManualSaleHdModel_ID() {
	}

	public clsExciseManualSaleHdModel_ID(long intId, String strClientCode) {
		this.intId = intId;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}
}
