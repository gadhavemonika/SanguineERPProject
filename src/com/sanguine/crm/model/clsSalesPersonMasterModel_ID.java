package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsSalesPersonMasterModel_ID implements Serializable{

	@Column(name = "strSalesPersonCode")
	private String strSalesPersonCode;

	@Column(name = "strClientCode")
	private String strClientCode;
	
	public clsSalesPersonMasterModel_ID(){
		
	}
	
public clsSalesPersonMasterModel_ID(String strSalesPersonCode, String strClientCode){
		this.strClientCode=strClientCode;
		this.strSalesPersonCode=strSalesPersonCode;
	}

public String getStrSalesPersonCode() {
	return strSalesPersonCode;
}

public void setStrSalesPersonCode(String strSalesPersonCode) {
	this.strSalesPersonCode = strSalesPersonCode;
}

public String getStrClientCode() {
	return strClientCode;
}

public void setStrClientCode(String strClientCode) {
	this.strClientCode = strClientCode;
}

//HashCode and Equals Funtions
	@Override
	public boolean equals(Object obj) {
		clsSalesPersonMasterModel_ID objModelId = (clsSalesPersonMasterModel_ID) obj;
		if (this.strSalesPersonCode.equals(objModelId.getStrSalesPersonCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strSalesPersonCode.hashCode() + this.strClientCode.hashCode();
	}
}
