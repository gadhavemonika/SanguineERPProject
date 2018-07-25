package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsCategoryMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strCategoryCode")
	private String strCategoryCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsCategoryMasterModel_ID() {
	}

	public clsCategoryMasterModel_ID(String strCategoryCode, String strClientCode) {
		this.strCategoryCode = strCategoryCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrCategoryCode() {
		return strCategoryCode;
	}

	public void setStrCategoryCode(String strCategoryCode) {
		this.strCategoryCode = strCategoryCode;
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
		clsCategoryMasterModel_ID objModelId = (clsCategoryMasterModel_ID) obj;
		if (this.strCategoryCode.equals(objModelId.getStrCategoryCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strCategoryCode.hashCode() + this.strClientCode.hashCode();
	}

}
