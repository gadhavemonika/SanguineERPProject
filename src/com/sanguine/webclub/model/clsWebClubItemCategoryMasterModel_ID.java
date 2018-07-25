package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubItemCategoryMasterModel_ID implements Serializable {

	@Column(name = "strItemCategoryCode")
	private String strItemCategoryCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebClubItemCategoryMasterModel_ID() {
	}

	public clsWebClubItemCategoryMasterModel_ID(String strItemCategoryCode, String strClientCode) {
		this.strItemCategoryCode = strItemCategoryCode;
		this.strClientCode = strClientCode;
	}

	public String getStrItemCategoryCode() {
		return strItemCategoryCode;
	}

	public void setStrItemCategoryCode(String strItemCategoryCode) {
		this.strItemCategoryCode = strItemCategoryCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}
}
