package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsPOSSalesDtlModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strPOSItemCode")
	private String strPOSItemCode;

	@Column(name = "strClientCode")
	private String strClientCode;
	
	@Column(name = "strCostCenterCode")
	private String strCostCenterCode;
	
	@Column(name = "strLocationCode")
	private String strLocationCode;
	
	@Column(name = "strWSItemCode")
	private String strWSItemCode;
	
	@Column(name = "strSACode")
	private String  strSACode;
	
	public clsPOSSalesDtlModel_ID() {
	}

	public clsPOSSalesDtlModel_ID(String strPOSItemCode, String strClientCode,String strCostCenterCode,String strLocationCode,String strWSItemCode,String strSACode) {
		this.strPOSItemCode = strPOSItemCode;
		this.strClientCode = strClientCode;
		this.strLocationCode = strLocationCode;
		this.strWSItemCode = strWSItemCode;
		this.strSACode = strSACode;
	}

	// Setter-Getter Methods
	public String getStrPOSItemCode() {
		return strPOSItemCode;
	}

	public void setStrPOSItemCode(String strPOSItemCode) {
		this.strPOSItemCode = strPOSItemCode;
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
		clsPOSSalesDtlModel_ID objModelId = (clsPOSSalesDtlModel_ID) obj;
		if (this.strPOSItemCode.equals(objModelId.getStrPOSItemCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strPOSItemCode.hashCode() + this.strClientCode.hashCode();
	}

	public String getStrCostCenterCode() {
		return strCostCenterCode;
	}

	public void setStrCostCenterCode(String strCostCenterCode) {
		this.strCostCenterCode = strCostCenterCode;
	}

	public String getStrLocationCode() {
		return strLocationCode;
	}

	public void setStrLocationCode(String strLocationCode) {
		this.strLocationCode = strLocationCode;
	}

	public String getStrWSItemCode() {
		return strWSItemCode;
	}

	public void setStrWSItemCode(String strWSItemCode) {
		this.strWSItemCode = strWSItemCode;
	}

	public String getStrSACode() {
		return strSACode;
	}

	public void setStrSACode(String strSACode) {
		this.strSACode = strSACode;
	}

}
