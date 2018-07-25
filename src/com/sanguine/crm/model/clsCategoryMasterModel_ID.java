package com.sanguine.crm.model;

import java.io.Serializable;
import javax.persistence.Embeddable;


@SuppressWarnings("serial")
@Embeddable
public class clsCategoryMasterModel_ID implements Serializable {

	private String strCategoryCode;

	private String strClientCode;

	public clsCategoryMasterModel_ID() {
	}

	public clsCategoryMasterModel_ID(String strCategoryCode, String strClientCode) {
		this.strCategoryCode = strCategoryCode;
		this.strClientCode = strClientCode;
	}

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

	

}
