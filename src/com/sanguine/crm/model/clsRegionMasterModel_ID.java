package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class clsRegionMasterModel_ID implements Serializable  {
	

	private String strRegionCode;

	private String strClientCode;

	public clsRegionMasterModel_ID() {
	}

	public clsRegionMasterModel_ID(String strCategoryCode, String strClientCode) {
		this.strRegionCode = strCategoryCode;
		this.strClientCode = strClientCode;
	}



	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrRegionCode() {
		return strRegionCode;
	}

	public void setStrRegionCode(String strRegionCode) {
		this.strRegionCode = strRegionCode;
	}

}
