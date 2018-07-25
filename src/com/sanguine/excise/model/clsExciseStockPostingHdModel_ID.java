package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsExciseStockPostingHdModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strPSPCode")
	private String strPSPCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsExciseStockPostingHdModel_ID() {
	}

	public clsExciseStockPostingHdModel_ID(String strPSPCode, String strClientCode) {
		this.strPSPCode = strPSPCode;
		this.strClientCode = strClientCode;
	}

	@Override
	public int hashCode() {
		return this.strPSPCode.hashCode() + this.strClientCode.hashCode();
	}

	public String getStrPSPCode() {
		return strPSPCode;
	}

	public void setStrPSPCode(String strPSPCode) {
		this.strPSPCode = strPSPCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	// HashCode and Equals Funtions
	@Override
	public boolean equals(Object obj) {
		clsExciseStockPostingHdModel_ID objModelId = (clsExciseStockPostingHdModel_ID) obj;
		if (this.strPSPCode.equals(objModelId.getStrPSPCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

}
