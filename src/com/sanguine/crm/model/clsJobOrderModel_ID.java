package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsJobOrderModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strJOCode")
	private String strJOCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsJobOrderModel_ID() {
	}

	public clsJobOrderModel_ID(String strJOCode, String strClientCode) {
		this.strJOCode = strJOCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrJOCode() {
		return strJOCode;
	}

	public void setStrJOCode(String strJOCode) {
		this.strJOCode = strJOCode;
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
		clsJobOrderModel_ID objModelId = (clsJobOrderModel_ID) obj;
		if (this.strJOCode.equals(objModelId.getStrJOCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strJOCode.hashCode() + this.strClientCode.hashCode();
	}

}
