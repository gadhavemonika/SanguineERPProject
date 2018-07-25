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
@Table(name = "tblcitymaster")
@IdClass(clsWebClubCityMasterModel_ID.class)
public class clsWebClubCityMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsWebClubCityMasterModel() {
	}

	public clsWebClubCityMasterModel(clsWebClubCityMasterModel_ID objModelID) {
		strCityCode = objModelID.getStrCityCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strCityCode", column = @Column(name = "strCityCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strCityCode")
	private String strCityCode;

	@Column(name = "strCityName")
	private String strCityName;

	@Column(name = "strCountryCode")
	private String strCountryCode;

	@Column(name = "strStateCode")
	private String strStateCode;

	@Column(name = "strSTDCode")
	private String strSTDCode;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "intGId", updatable = false)
	private long intGId;

	@Column(name = "dtCreatedDate", updatable = false)
	private String dtCreatedDate;

	@Column(name = "dtLastModified")
	private String dtLastModified;

	// Setter-Getter Methods
	public String getStrCityCode() {
		return strCityCode;
	}

	public void setStrCityCode(String strCityCode) {
		this.strCityCode = (String) setDefaultValue(strCityCode, "NA");
	}

	public String getStrCityName() {
		return strCityName;
	}

	public void setStrCityName(String strCityName) {
		this.strCityName = (String) setDefaultValue(strCityName, "NA");
	}

	public String getStrCountryCode() {
		return strCountryCode;
	}

	public void setStrCountryCode(String strCountryCode) {
		this.strCountryCode = (String) setDefaultValue(strCountryCode, "NA");
	}

	public String getStrStateCode() {
		return strStateCode;
	}

	public void setStrStateCode(String strStateCode) {
		this.strStateCode = (String) setDefaultValue(strStateCode, "NA");
	}

	public String getStrSTDCode() {
		return strSTDCode;
	}

	public void setStrSTDCode(String strSTDCode) {
		this.strSTDCode = (String) setDefaultValue(strSTDCode, "NA");
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = (String) setDefaultValue(strUserCreated, "NA");
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = (String) setDefaultValue(strUserModified, "NA");
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

	public long getIntGId() {
		return intGId;
	}

	public void setIntGId(long intGId) {
		this.intGId = intGId;
	}

	public String getDtCreatedDate() {
		return dtCreatedDate;
	}

	public void setDtCreatedDate(String dtCreatedDate) {
		this.dtCreatedDate = dtCreatedDate;
	}

	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

}
