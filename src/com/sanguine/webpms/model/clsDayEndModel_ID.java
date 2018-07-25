package com.sanguine.webpms.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
@SuppressWarnings("serial")
public class clsDayEndModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "dtePMSDate")
	private String dtePMSDate;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsDayEndModel_ID() {
	}

	public clsDayEndModel_ID(String strPropertyCode, String dtePMSDate, String strClientCode) {
		this.strPropertyCode = strPropertyCode;
		this.dtePMSDate = dtePMSDate;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getDtePMSDate() {
		return dtePMSDate;
	}

	public void setDtePMSDate(String dtePMSDate) {
		this.dtePMSDate = dtePMSDate;
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
		clsDayEndModel_ID objModelId = (clsDayEndModel_ID) obj;
		if (this.strPropertyCode.equals(objModelId.getStrPropertyCode()) && this.dtePMSDate.equals(objModelId.getDtePMSDate()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strPropertyCode.hashCode() + this.dtePMSDate.hashCode() + this.strClientCode.hashCode();
	}

}
