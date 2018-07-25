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
@Table(name = "tblcurrencydetails")
@IdClass(clsWebClubCurrencyDetailsMasterModel_ID.class)
public class clsWebClubCurrencyDetailsMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsWebClubCurrencyDetailsMasterModel() {
	}

	public clsWebClubCurrencyDetailsMasterModel(clsWebClubCurrencyDetailsMasterModel_ID objModelID) {
		strCurrCode = objModelID.getStrCurrCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strCurrCode", column = @Column(name = "strCurrCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strCurrCode")
	private String strCurrCode;

	@Column(name = "intId")
	public long intId;

	@Column(name = "strDesc")
	private String strDesc;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "dteCreatedDate", updatable = false)
	private String dteCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteLastModifiedDate")
	private String dteLastModifiedDate;

	@Column(name = "strCurrUnit")
	private String strCurrUnit;

	@Column(name = "strExchangeRate")
	private String strExchangeRate;

	@Column(name = "strTraChkRate")
	private String strTraChkRate;

	@Column(name = "intDec")
	private int intDec;

	@Column(name = "strShortDesc")
	private String strShortDesc;

	@Column(name = "strLongDeciDesc")
	private String strLongDeciDesc;

	@Column(name = "strShortDeciDesc")
	private String strShortDeciDesc;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	// Setter Getter

	public String getStrCurrCode() {
		return strCurrCode;
	}

	public void setStrCurrCode(String strCurrCode) {
		this.strCurrCode = strCurrCode;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrDesc() {
		return strDesc;
	}

	public void setStrDesc(String strDesc) {
		this.strDesc = strDesc;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
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
		this.strUserModified = strUserModified;
	}

	public String getDteLastModifiedDate() {
		return dteLastModifiedDate;
	}

	public void setDteLastModifiedDate(String dteLastModifiedDate) {
		this.dteLastModifiedDate = dteLastModifiedDate;
	}

	public String getStrCurrUnit() {
		return strCurrUnit;
	}

	public void setStrCurrUnit(String strCurrunit) {
		this.strCurrUnit = strCurrunit;
	}

	public String getStrExchangeRate() {
		return strExchangeRate;
	}

	public void setStrExchangeRate(String strExchangeRate) {
		this.strExchangeRate = strExchangeRate;
	}

	public String getStrTraChkRate() {
		return strTraChkRate;
	}

	public void setStrTraChkRate(String strTraChkRate) {
		this.strTraChkRate = strTraChkRate;
	}

	public int getIntDec() {
		return intDec;
	}

	public void setIntDec(int intDec) {
		this.intDec = intDec;
	}

	public String getStrShortDesc() {
		return strShortDesc;
	}

	public void setStrShortDesc(String strShortDesc) {
		this.strShortDesc = strShortDesc;
	}

	public String getStrLongDeciDesc() {
		return strLongDeciDesc;
	}

	public void setStrLongDeciDesc(String strLongDeciDesc) {
		this.strLongDeciDesc = strLongDeciDesc;
	}

	public String getStrShortDeciDesc() {
		return strShortDeciDesc;
	}

	public void setStrShortDeciDesc(String strShortDeciDesc) {
		this.strShortDeciDesc = strShortDeciDesc;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
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
