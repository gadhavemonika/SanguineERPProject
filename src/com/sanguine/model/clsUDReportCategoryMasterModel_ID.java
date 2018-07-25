package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsUDReportCategoryMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strUDCCode")
	private String strUDCCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsUDReportCategoryMasterModel_ID() {
	}

	public clsUDReportCategoryMasterModel_ID(String strUDCCode, String strClientCode) {
		this.strUDCCode = strUDCCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrUDCCode() {
		return strUDCCode;
	}

	public void setStrUDCCode(String strUDCCode) {
		this.strUDCCode = strUDCCode;
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
		clsUDReportCategoryMasterModel_ID objModelId = (clsUDReportCategoryMasterModel_ID) obj;
		if (this.strUDCCode.equals(objModelId.getStrUDCCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strUDCCode.hashCode() + this.strClientCode.hashCode();
	}

}
