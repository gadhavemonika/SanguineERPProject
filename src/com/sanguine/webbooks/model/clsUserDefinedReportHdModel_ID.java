package com.sanguine.webbooks.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsUserDefinedReportHdModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strReportId")
	private String strReportId;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsUserDefinedReportHdModel_ID() {
	}

	public clsUserDefinedReportHdModel_ID(String strReportId, String strClientCode) {
		this.strReportId = strReportId;
		this.strClientCode = strClientCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}



	public String getStrReportId() {
		return strReportId;
	}

	public void setStrReportId(String strReportId) {
		this.strReportId = strReportId;
	}

	// HashCode and Equals Funtions
	@Override
	public boolean equals(Object obj) {
		clsUserDefinedReportHdModel_ID objModelId = (clsUserDefinedReportHdModel_ID) obj;
		if (this.strReportId.equals(objModelId.getStrReportId()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strReportId.hashCode() + this.strClientCode.hashCode();
	}

}