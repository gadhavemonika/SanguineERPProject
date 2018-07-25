package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsInvSettlementdtlModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strInvCode")
	private String strInvCode;

	@Column(name = "strSettlementCode")
	private String strSettlementCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "dteInvDate")
	private String dteInvDate;

	public clsInvSettlementdtlModel_ID() {
	}

	public clsInvSettlementdtlModel_ID(String strInvCode, String strSettlementCode, String strClientCode, String dteInvDate) {
		this.strInvCode = strInvCode;
		this.strSettlementCode = strSettlementCode;
		this.strClientCode = strClientCode;
		this.dteInvDate = dteInvDate;
	}

	// Setter-Getter Methods
	public String getStrInvCode() {
		return strInvCode;
	}

	public void setStrInvCode(String strInvCode) {
		this.strInvCode = strInvCode;
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

	public String getDteInvDate() {
		return dteInvDate;
	}

	public void setDteInvDate(String dteInvDate) {
		this.dteInvDate = dteInvDate;
	}

	// HashCode and Equals Funtions
	@Override
	public boolean equals(Object obj) {
		clsInvSettlementdtlModel_ID objModelId = (clsInvSettlementdtlModel_ID) obj;
		if (this.strInvCode.equals(objModelId.getStrInvCode()) && this.strSettlementCode.equals(objModelId.getStrSettlementCode()) && this.strClientCode.equals(objModelId.getStrClientCode()) && this.dteInvDate.equals(objModelId.getDteInvDate())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strInvCode.hashCode() + this.strSettlementCode.hashCode() + this.strClientCode.hashCode() + this.dteInvDate.hashCode();
	}

}
