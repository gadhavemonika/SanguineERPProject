package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
@SuppressWarnings("serial")
public class clsLinkUpModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strMasterCode")
	private String strMasterCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "strOperationType")
	private String strOperationType;

	@Column(name = "strModuleType")
	private String strModuleType;

	public clsLinkUpModel_ID() {
	}

	public clsLinkUpModel_ID(String strMasterCode, String strClientCode, String strPropertyCode, String strOperationType, String strModuleType) {
		this.strMasterCode = strMasterCode;
		this.strClientCode = strClientCode;
		this.strPropertyCode = strPropertyCode;
		this.strOperationType = strOperationType;
		this.strModuleType = strModuleType;
	}

	// Setter-Getter Methods

	public String getStrClientCode() {
		return strClientCode;
	}

	public String getStrMasterCode() {
		return strMasterCode;
	}

	public void setStrMasterCode(String strMasterCode) {
		this.strMasterCode = strMasterCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	// HashCode and Equals Funtions
	@Override
	public boolean equals(Object obj) {
		clsLinkUpModel_ID objModelId = (clsLinkUpModel_ID) obj;
		if (this.strMasterCode.equals(objModelId.getStrMasterCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strMasterCode.hashCode() + this.strClientCode.hashCode();
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrOperationType() {
		return strOperationType;
	}

	public void setStrOperationType(String strOperationType) {
		this.strOperationType = strOperationType;
	}

	public String getStrModuleType() {
		return strModuleType;
	}

	public void setStrModuleType(String strModuleType) {
		this.strModuleType = strModuleType;
	}

}
