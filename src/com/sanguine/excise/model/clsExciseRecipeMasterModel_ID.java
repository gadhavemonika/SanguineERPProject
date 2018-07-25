package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsExciseRecipeMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strRecipeCode")
	private String strRecipeCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsExciseRecipeMasterModel_ID() {
	}

	public clsExciseRecipeMasterModel_ID(String strRecipeCode, String strClientCode) {
		this.strRecipeCode = strRecipeCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrRecipeCode() {
		return strRecipeCode;
	}

	public void setStrRecipeCode(String strRecipeCode) {
		this.strRecipeCode = strRecipeCode;
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
		clsExciseRecipeMasterModel_ID objModelId = (clsExciseRecipeMasterModel_ID) obj;
		if (this.strRecipeCode.equals(objModelId.getStrRecipeCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strRecipeCode.hashCode() + this.strClientCode.hashCode();
	}

}
