package com.sanguine.webpms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsAgentMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strAgentCode")
	private String strAgentCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsAgentMasterModel_ID() {
	}

	public clsAgentMasterModel_ID(String strAgentCode, String strClientCode) {
		this.strAgentCode = strAgentCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrAgentCode() {
		return strAgentCode;
	}

	public void setStrAgentCode(String strAgentCode) {
		this.strAgentCode = strAgentCode;
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
		clsAgentMasterModel_ID objModelId = (clsAgentMasterModel_ID) obj;
		if (this.strAgentCode.equals(objModelId.getStrAgentCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strAgentCode.hashCode() + this.strClientCode.hashCode();
	}

}
