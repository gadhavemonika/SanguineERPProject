package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "tblpurchasereturnhd")
@IdClass(clsPurchaseReturnHdModel_ID.class)
public class clsPurchaseReturnHdModel implements Serializable {
	private String strPRCode, strSuppCode, strAgainst, strGRNCode, strNarration, strLocCode, strVehNo, strMInBy, dtPRDate;
	private String strTimeInOut, strPurCode, strUserCreated, strUserModified, dtDateCreated, dtLastModified, strClientCode;
	private String strAuthorise;
	private double dblSubTotal, dblDisRate, dblDisAmt, dblExtra, dblTotal;
	private double intid;
	private String strLocName;
	private String strSupplierName, strPRNo;
	@Column(name = "strCurrency",columnDefinition = "VARCHAR(50) NOT NULL DEFAULT 'NA'")
	private String strCurrency;
	
	@Column(name = "dblConversion", nullable = false,columnDefinition = "DECIMAL(18,2) NOT NULL DEFAULT '1.00'")
	private double dblConversion;
	
	@Column(name = "strJVNo",columnDefinition = "VARCHAR(50) NOT NULL default ''")
	private String strJVNo;

	@Transient
	public String getStrSupplierName() {
		return strSupplierName;
	}

	public void setStrSupplierName(String strSupplierName) {
		this.strSupplierName = strSupplierName;
	}

	@Transient
	public String getStrLocName() {
		return strLocName;
	}

	public void setStrLocName(String strLocName) {
		this.strLocName = strLocName;
	}

	public clsPurchaseReturnHdModel() {
	}

	public clsPurchaseReturnHdModel(clsPurchaseReturnHdModel_ID clsPurchaseReturnHdModel_ID) {
		strPRCode = clsPurchaseReturnHdModel_ID.getStrPRCode();
		strClientCode = clsPurchaseReturnHdModel_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strPRCode", column = @Column(name = "strPRCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strPRCode")
	public String getStrPRCode() {
		return strPRCode;
	}

	public void setStrPRCode(String strPRCode) {
		this.strPRCode = strPRCode;
	}

	@Column(name = "strSuppCode")
	public String getStrSuppCode() {
		return strSuppCode;
	}

	public void setStrSuppCode(String strSuppCode) {
		this.strSuppCode = strSuppCode;
	}

	@Column(name = "dtPRDate")
	public String getDtPRDate() {
		return dtPRDate;
	}

	public void setDtPRDate(String dtPRDate) {
		this.dtPRDate = dtPRDate;
	}

	@Column(name = "strAgainst")
	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	@Column(name = "strGRNCode")
	public String getStrGRNCode() {
		return strGRNCode;
	}

	public void setStrGRNCode(String strGRNCode) {
		this.strGRNCode = strGRNCode;
	}

	@Column(name = "strNarration")
	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	@Column(name = "strLocCode")
	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	@Column(name = "strVehNo")
	public String getStrVehNo() {
		return strVehNo;
	}

	public void setStrVehNo(String strVehNo) {
		this.strVehNo = strVehNo;
	}

	@Column(name = "strMInBy")
	public String getStrMInBy() {
		return strMInBy;
	}

	public void setStrMInBy(String strMInBy) {
		this.strMInBy = strMInBy;
	}

	@Column(name = "strTimeInOut")
	public String getStrTimeInOut() {
		return strTimeInOut;
	}

	public void setStrTimeInOut(String strTimeInOut) {
		this.strTimeInOut = strTimeInOut;
	}

	@Column(name = "strPurCode")
	public String getStrPurCode() {
		return strPurCode;
	}

	public void setStrPurCode(String strPurCode) {
		this.strPurCode = strPurCode;
	}

	@Column(name = "strUserCreated", updatable = false)
	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	@Column(name = "strUserModified")
	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	@Column(name = "dtDateCreated", updatable = false)
	public String getDtDateCreated() {
		return dtDateCreated;
	}

	public void setDtDateCreated(String dtDateCreated) {
		this.dtDateCreated = dtDateCreated;
	}

	@Column(name = "dtLastModified")
	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	@Column(name = "strAuthorise")
	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	@Column(name = "dblSubTotal")
	public double getDblSubTotal() {
		return dblSubTotal;
	}

	public void setDblSubTotal(double dblSubTotal) {
		this.dblSubTotal = dblSubTotal;
	}

	@Column(name = "dblDisRate")
	public double getDblDisRate() {
		return dblDisRate;
	}

	public void setDblDisRate(double dblDisRate) {
		this.dblDisRate = dblDisRate;
	}

	@Column(name = "dblDisAmt")
	public double getDblDisAmt() {
		return dblDisAmt;
	}

	public void setDblDisAmt(double dblDisAmt) {
		this.dblDisAmt = dblDisAmt;
	}

	@Column(name = "dblExtra")
	public double getDblExtra() {
		return dblExtra;
	}

	public void setDblExtra(double dblExtra) {
		this.dblExtra = dblExtra;
	}

	@Column(name = "dblTotal")
	public double getDblTotal() {
		return dblTotal;
	}

	public void setDblTotal(double dblTotal) {
		this.dblTotal = dblTotal;
	}

	@Column(name = "intid")
	public double getIntid() {
		return intid;
	}

	public void setIntid(double intid) {
		this.intid = intid;
	}

	@Column(name = "strClientCode")
	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	private int intLevel = 0;

	@Column(name = "intLevel", columnDefinition = "Int(8) default '0'")
	public int getIntLevel() {
		return intLevel;
	}

	public void setIntLevel(int intLevel) {
		this.intLevel = (int) setDefaultValue(intLevel, "0");
	}
	
	

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
	}

	public double getDblConversion() {
		return dblConversion;
	}

	public void setDblConversion(double dblConversion) {
		this.dblConversion = dblConversion;
	}

	private Object setDefaultValue(Object value, Object defaultValue) {
		if (value != null && (value instanceof String && value.toString().length() > 0)) {
			return value;
		} else if (value != null && !(value instanceof String)) {
			return value;

		} else {
			return defaultValue;
		}
		// return value !=null && (value instanceof String &&
		// value.toString().length()>0) ? value : defaultValue;
	}

	public String getStrPRNo() {
		return strPRNo;
	}

	public void setStrPRNo(String strPRNo) {
		this.strPRNo = strPRNo;
	}

	public String getStrJVNo() {
		return strJVNo;
	}

	public void setStrJVNo(String strJVNo) {
		this.strJVNo = strJVNo;
	}

}
