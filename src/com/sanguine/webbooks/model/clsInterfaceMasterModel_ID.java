package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsInterfaceMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strInterfaceCode")
	private String strInterfaceCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsInterfaceMasterModel_ID() {
	}

	public clsInterfaceMasterModel_ID(String strInterfaceCode, String strClientCode) {
		this.strInterfaceCode = strInterfaceCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrInterfaceCode() {
		return strInterfaceCode;
	}

	public void setStrInterfaceCode(String strInterfaceCode) {
		this.strInterfaceCode = strInterfaceCode;
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
		clsInterfaceMasterModel_ID objModelId = (clsInterfaceMasterModel_ID) obj;
		if (this.strInterfaceCode.equals(objModelId.getStrInterfaceCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strInterfaceCode.hashCode() + this.strClientCode.hashCode();
	}

}
