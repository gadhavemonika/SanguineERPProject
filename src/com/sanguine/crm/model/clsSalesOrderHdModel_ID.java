package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsSalesOrderHdModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strSOCode")
	private String strSOCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsSalesOrderHdModel_ID() {
	}

	public clsSalesOrderHdModel_ID(String strSOCode, String strClientCode) {
		this.strSOCode = strSOCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrSOCode() {
		return strSOCode;
	}

	public void setStrSOCode(String strSOCode) {
		this.strSOCode = strSOCode;
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
		clsSalesOrderHdModel_ID objModelId = (clsSalesOrderHdModel_ID) obj;
		if (this.strSOCode.equals(objModelId.getStrSOCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strSOCode.hashCode() + this.strClientCode.hashCode();
	}

}
