package com.sanguine.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
@SuppressWarnings("serial")
public class clsVehicleRouteModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "intId")
	private long intId;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsVehicleRouteModel_ID() {
	}

	public clsVehicleRouteModel_ID(long intId, String strClientCode) {
		this.intId = intId;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

}
