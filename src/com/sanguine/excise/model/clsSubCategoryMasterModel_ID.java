package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsSubCategoryMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strSubCategoryCode")
	private String strSubCategoryCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsSubCategoryMasterModel_ID() {
	}

	public clsSubCategoryMasterModel_ID(String strSubCategoryCode, String strClientCode) {
		this.strSubCategoryCode = strSubCategoryCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrSubCategoryCode() {
		return strSubCategoryCode;
	}

	public void setStrSubCategoryCode(String strSubCategoryCode) {
		this.strSubCategoryCode = strSubCategoryCode;
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
		clsSubCategoryMasterModel_ID objModelId = (clsSubCategoryMasterModel_ID) obj;
		if (this.strSubCategoryCode.equals(objModelId.getStrSubCategoryCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strSubCategoryCode.hashCode() + this.strClientCode.hashCode();
	}

}
