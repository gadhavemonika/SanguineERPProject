package com.sanguine.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
@SuppressWarnings("serial")
public class clsVehicleMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strVehCode")
	private String strVehCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsVehicleMasterModel_ID() {
	}

	public clsVehicleMasterModel_ID(String strVehCode, String strClientCode) {
		this.strVehCode = strVehCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrVehCode() {
		return strVehCode;
	}

	public void setStrVehCode(String strVehCode) {
		this.strVehCode = strVehCode;
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
		clsVehicleMasterModel_ID objModelId = (clsVehicleMasterModel_ID) obj;
		if (this.strVehCode.equals(objModelId.getStrVehCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strVehCode.hashCode() + this.strClientCode.hashCode();
	}

}
