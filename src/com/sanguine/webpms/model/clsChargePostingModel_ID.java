package com.sanguine.webpms.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
@SuppressWarnings("serial")
public class clsChargePostingModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strService")
	private String strService;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsChargePostingModel_ID() {
	}

	public clsChargePostingModel_ID(String strService, String strClientCode) {
		this.strService = strService;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrService() {
		return strService;
	}

	public void setStrService(String strService) {
		this.strService = strService;
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
		clsChargePostingModel_ID objModelId = (clsChargePostingModel_ID) obj;
		if (this.strService.equals(objModelId.getStrService()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strService.hashCode() + this.strClientCode.hashCode();
	}

}
