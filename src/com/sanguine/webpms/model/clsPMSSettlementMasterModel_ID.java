package com.sanguine.webpms.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
@SuppressWarnings("serial")
public class clsPMSSettlementMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strSettlementCode")
	private String strSettlementCode;

	public clsPMSSettlementMasterModel_ID() {
	}

	public clsPMSSettlementMasterModel_ID(String strSettlementCode) {
		this.strSettlementCode = strSettlementCode;
	}

	// Setter-Getter Methods
	public String getStrSettlementCode() {
		return strSettlementCode;
	}

	public void setStrSettlementCode(String strSettlementCode) {
		this.strSettlementCode = strSettlementCode;
	}

	// HashCode and Equals Funtions
	@Override
	public boolean equals(Object obj) {
		clsPMSSettlementMasterModel_ID objModelId = (clsPMSSettlementMasterModel_ID) obj;
		if (this.strSettlementCode.equals(objModelId.getStrSettlementCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strSettlementCode.hashCode();
	}

}
