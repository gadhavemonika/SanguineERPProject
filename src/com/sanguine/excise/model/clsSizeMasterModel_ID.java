package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsSizeMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strSizeCode")
	private String strSizeCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsSizeMasterModel_ID() {
	}

	public clsSizeMasterModel_ID(String strSizeCode, String strClientCode) {
		this.strSizeCode = strSizeCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrSizeCode() {
		return strSizeCode;
	}

	public void setStrSizeCode(String strSizeCode) {
		this.strSizeCode = strSizeCode;
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
		clsSizeMasterModel_ID objModelId = (clsSizeMasterModel_ID) obj;
		if (this.strSizeCode.equals(objModelId.getStrSizeCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strSizeCode.hashCode() + this.strClientCode.hashCode();
	}

}
