package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class clsSettlementMasterModel_ID implements Serializable {

	private String strSettlementCode;

	private String strClientCode;

	public clsSettlementMasterModel_ID() {
	}

	public clsSettlementMasterModel_ID(String strSettlementCode, String strClientCode) {
		this.strSettlementCode = strSettlementCode;
		this.strClientCode = strClientCode;
	}

	public String getStrSettlementCode() {
		return strSettlementCode;
	}

	public void setStrSettlementCode(String strSettlementCode) {
		this.strSettlementCode = strSettlementCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
