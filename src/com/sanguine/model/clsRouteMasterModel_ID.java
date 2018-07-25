package com.sanguine.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
@SuppressWarnings("serial")
public class clsRouteMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strRouteCode")
	private String strRouteCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsRouteMasterModel_ID() {
	}

	public clsRouteMasterModel_ID(String strRouteCode, String strClientCode) {
		this.strRouteCode = strRouteCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrRouteCode() {
		return strRouteCode;
	}

	public void setStrRouteCode(String strRouteCode) {
		this.strRouteCode = strRouteCode;
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
		clsRouteMasterModel_ID objModelId = (clsRouteMasterModel_ID) obj;
		if (this.strRouteCode.equals(objModelId.getStrRouteCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strRouteCode.hashCode() + this.strClientCode.hashCode();
	}

}
