package com.sanguine.webpms.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
@SuppressWarnings("serial")
public class clsAgentCommisionModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strAgentCommCode")
	private String strAgentCommCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsAgentCommisionModel_ID() {
	}

	public clsAgentCommisionModel_ID(String strAgentCommCode, String strClientCode) {
		this.strAgentCommCode = strAgentCommCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrAgentCommCode() {
		return strAgentCommCode;
	}

	public void setStrAgentCommCode(String strAgentCommCode) {
		this.strAgentCommCode = strAgentCommCode;
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
		clsAgentCommisionModel_ID objModelId = (clsAgentCommisionModel_ID) obj;
		if (this.strAgentCommCode.equals(objModelId.getStrAgentCommCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strAgentCommCode.hashCode() + this.strClientCode.hashCode();
	}

}
