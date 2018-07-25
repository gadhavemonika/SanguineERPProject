package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblcurrencymaster")
@IdClass(clsCurrencyMasterModel_ID.class)
public class clsCurrencyMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsCurrencyMasterModel() {
	}

	public clsCurrencyMasterModel(clsCurrencyMasterModel_ID objModelID) {
		strCurrencyCode = objModelID.getStrCurrencyCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strCurrencyCode", column = @Column(name = "strCurrencyCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strCurrencyCode")
	private String strCurrencyCode;

	@Column(name = "intID")
	private long intID;

	@Column(name = "strCurrencyName")
	private String strCurrencyName;

	@Column(name = "strShortName")
	private String strShortName;

	@Column(name = "strBankName")
	private String strBankName;

	@Column(name = "strSwiftCode")
	private String strSwiftCode;

	@Column(name = "strIbanNo")
	private String strIbanNo;

	@Column(name = "strRouting")
	private String strRouting;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "strAccountNo")
	private String strAccountNo;

	@Column(name = "dblConvToBaseCurr")
	private double dblConvToBaseCurr;

	@Column(name = "strSubUnit")
	private String strSubUnit;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "dtCreatedDate")
	private String dtCreatedDate;

	@Column(name = "dtLastModified")
	private String dtLastModified;

	// Setter-Getter Methods
	public String getStrCurrencyCode() {
		return strCurrencyCode;
	}

	public void setStrCurrencyCode(String strCurrencyCode) {
		this.strCurrencyCode = (String) setDefaultValue(strCurrencyCode, "NA");
	}

	public long getIntID() {
		return intID;
	}

	public void setIntID(long intID) {
		this.intID = (Long) setDefaultValue(intID, "NA");
	}

	public String getStrCurrencyName() {
		return strCurrencyName;
	}

	public void setStrCurrencyName(String strCurrencyName) {
		this.strCurrencyName = (String) setDefaultValue(strCurrencyName, "NA");
	}

	public String getStrShortName() {
		return strShortName;
	}

	public void setStrShortName(String strShortName) {
		this.strShortName = (String) setDefaultValue(strShortName, "NA");
	}

	public String getStrBankName() {
		return strBankName;
	}

	public void setStrBankName(String strBankName) {
		this.strBankName = (String) setDefaultValue(strBankName, "NA");
	}

	public String getStrSwiftCode() {
		return strSwiftCode;
	}

	public void setStrSwiftCode(String strSwiftCode) {
		this.strSwiftCode = (String) setDefaultValue(strSwiftCode, "NA");
	}

	public String getStrIbanNo() {
		return strIbanNo;
	}

	public void setStrIbanNo(String strIbanNo) {
		this.strIbanNo = (String) setDefaultValue(strIbanNo, "NA");
	}

	public String getStrRouting() {
		return strRouting;
	}

	public void setStrRouting(String strRouting) {
		this.strRouting = (String) setDefaultValue(strRouting, "NA");
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

	public String getStrAccountNo() {
		return strAccountNo;
	}

	public void setStrAccountNo(String strAccountNo) {
		this.strAccountNo = (String) setDefaultValue(strAccountNo, "NA");
	}

	public double getDblConvToBaseCurr() {
		return dblConvToBaseCurr;
	}

	public void setDblConvToBaseCurr(double dblConvToBaseCurr) {
		this.dblConvToBaseCurr = (Double) setDefaultValue(dblConvToBaseCurr, "NA");
	}

	public String getStrSubUnit() {
		return strSubUnit;
	}

	public void setStrSubUnit(String strSubUnit) {
		this.strSubUnit = (String) setDefaultValue(strSubUnit, "NA");
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = (String) setDefaultValue(strClientCode, "NA");
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
