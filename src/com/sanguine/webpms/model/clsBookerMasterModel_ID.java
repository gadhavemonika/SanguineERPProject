package com.sanguine.webpms.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
@SuppressWarnings("serial")
public class clsBookerMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strBookerCode")
	private String strBookerCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsBookerMasterModel_ID() {
	}

	public clsBookerMasterModel_ID(String strBookerCode, String strClientCode) {
		this.strBookerCode = strBookerCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrBookerCode() {
		return strBookerCode;
	}

	public void setStrBookerCode(String strBookerCode) {
		this.strBookerCode = strBookerCode;
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
		clsBookerMasterModel_ID objModelId = (clsBookerMasterModel_ID) obj;
		if (this.strBookerCode.equals(objModelId.getStrBookerCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strBookerCode.hashCode() + this.strClientCode.hashCode();
	}

}
