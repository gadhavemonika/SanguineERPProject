package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblcompanytypemaster")
@IdClass(clsWebClubCompanyTypeMasterModel_ID.class)
public class clsWebClubCompanyTypeMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsWebClubCompanyTypeMasterModel() {
	}

	public clsWebClubCompanyTypeMasterModel(clsWebClubCompanyTypeMasterModel_ID objModelID) {
		strCompanyTypeCode = objModelID.getStrCompanyTypeCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strCompanyTypeCode", column = @Column(name = "strCompanyTypeCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strCompanyTypeCode")
	private String strCompanyTypeCode;

	@Column(name = "strCompanyName")
	private String strCompanyName;

	@Column(name = "strAnnualTurnOver")
	private String strAnnualTurnOver;

	@Column(name = "strCapitalAndReserved")
	private String strCapitalAndReserved;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "dteCreatedDate", updatable = false)
	private String dteCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteLastModified")
	private String dteLastModified;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "intGId", updatable = false)
	private long intGId;

	// Setter-Getter Methods
	public String getStrCompanyTypeCode() {
		return strCompanyTypeCode;
	}

	public void setStrCompanyTypeCode(String strCompanyTypeCode) {
		this.strCompanyTypeCode = (String) setDefaultValue(strCompanyTypeCode, "NA");
	}

	public String getStrCompanyName() {
		return strCompanyName;
	}

	public void setStrCompanyName(String strCompanyName) {
		this.strCompanyName = (String) setDefaultValue(strCompanyName, "NA");
	}

	public String getStrAnnualTurnOver() {
		return strAnnualTurnOver;
	}

	public void setStrAnnualTurnOver(String strAnnualTurnOver) {
		this.strAnnualTurnOver = (String) setDefaultValue(strAnnualTurnOver, "NA");
	}

	public String getStrCapitalAndReserved() {
		return strCapitalAndReserved;
	}

	public void setStrCapitalAndReserved(String strCapitalAndReserved) {
		this.strCapitalAndReserved = (String) setDefaultValue(strCapitalAndReserved, "NA");
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = (String) setDefaultValue(strUserCreated, "NA");
	}

	public String getDteCreatedDate() {
		return dteCreatedDate;
	}

	public void setDteCreatedDate(String dteCreatedDate) {
		this.dteCreatedDate = dteCreatedDate;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = (String) setDefaultValue(strUserModified, "NA");
	}

	public String getDteLastModified() {
		return dteLastModified;
	}

	public void setDteLastModified(String dteLastModified) {
		this.dteLastModified = dteLastModified;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = (String) setDefaultValue(strClientCode, "NA");
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = (String) setDefaultValue(strPropertyCode, "NA");
	}

	public long getIntGId() {
		return intGId;
	}

	public void setIntGId(long intGId) {
		this.intGId = (Long) setDefaultValue(intGId, "NA");
	}

	// Function to Set Default Values
	private Object setDefaultValue(Object value, Object defaultValue) {
		if (value != null && (value instanceof String && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Double && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Integer && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Long && value.toString().length() > 0)) {
			return value;
		} else {
			return defaultValue;
		}
	}

}
