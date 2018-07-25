package com.sanguine.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
@SuppressWarnings("serial")
public class clsProcessMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strProcessCode")
	private String strProcessCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsProcessMasterModel_ID() {
	}

	public clsProcessMasterModel_ID(String strProcessCode, String strClientCode) {
		this.strProcessCode = strProcessCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrProcessCode() {
		return strProcessCode;
	}

	public void setStrProcessCode(String strProcessCode) {
		this.strProcessCode = strProcessCode;
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
		clsProcessMasterModel_ID objModelId = (clsProcessMasterModel_ID) obj;
		if (this.strProcessCode.equals(objModelId.getStrProcessCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strProcessCode.hashCode() + this.strClientCode.hashCode();
	}

}
