package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sanguine.base.model.clsBaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "tblgrnhd")
public class clsGRNHdModel extends clsBaseModel implements Serializable {
	@Id
	@Column(name = "strGRNCode")
	private String strGRNCode;

	@Column(name = "dtGRNDate", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtGRNDate;

	@Column(name = "strSuppCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strSuppCode;

	@Column(name = "strAgainst", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strAgainst;

	@Column(name = "strPONo", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strPONo;

	@Column(name = "strBillNo", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strBillNo;

	@Column(name = "dtBillDate", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtBillDate;

	@Column(name = "dtDueDate", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtDueDate;

	@Column(name = "strPayMode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strPayMode;

	@Column(name = "strNarration", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strNarration;

	@Column(name = "strLocCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strLocCode;

	@Column(name = "strVehNo", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strVehNo;

	@Column(name = "strMInBy", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strMInBy;

	@Column(name = "strTimeInOut", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strTimeInOut;

	@Column(name = "strNo", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strNo;

	@Column(name = "strRefNo", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strRefNo;

	@Column(name = "dtRefDate", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtRefDate;

	@Column(name = "strUserCreated", nullable = false, updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserCreated;

	@Column(name = "dtDateCreated", nullable = false, updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtDateCreated;

	@Column(name = "strUserModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserModified;

	@Column(name = "dtLastModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtLastModified;

	@Column(name = "strAuthorise", nullable = false, updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strAuthorise;

	@Column(name = "strCurrency", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strCurrency;

	@Column(name = "strShipmentMode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strShipmentMode;

	@Column(name = "dtShipmentDate", insertable = false, updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtShipmentDate;

	@Column(name = "strCountryofOrigin", insertable = false, updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strCountryofOrigin;

	@Column(name = "strConsignedCountry", insertable = false, updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strConsignedCountry;

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strClientCode;

	@Column(name = "dblConversion", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblConversion;

	@Column(name = "dblSubTotal", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblSubTotal;

	@Column(name = "dblDisRate", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblDisRate;

	@Column(name = "dblDisAmt", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblDisAmt;

	@Column(name = "dblTaxAmt", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblTaxAmt;

	@Column(name = "dblTotal", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblTotal;

	@Column(name = "dblExtra", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblExtra;

	@Column(name = "dblLessAmt", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblLessAmt;

	@Column(name = "dblValueTotal", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblValueTotal;

	@Column(name = "intId", nullable = false, updatable = false)
	private long intId;

	@Column(name = "dblRoundOff", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblRoundOff;

	@Column(name = "dblFOB", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblFOB;
	
	@Column(name = "dblFreight", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblFreight;
	
	@Column(name = "dblInsurance", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblInsurance;
	
	@Column(name = "dblOtherCharges", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblOtherCharges;
	
	@Column(name = "dblVATClaim", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblVATClaim;
	
	@Column(name = "dblClearingCharges", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblClearingCharges;
	
	@Transient
	private String strSuppName;

	@Transient
	private String strLocName;

	@Column(name = "strJVNo",columnDefinition = "VARCHAR(50) NOT NULL default ''")
	private String strJVNo;
	
	public String getStrSuppName() {
		return strSuppName;
	}

	public void setStrSuppName(String strSuppName) {
		this.strSuppName = strSuppName;
	}

	public String getStrLocName() {
		return strLocName;
	}

	public void setStrLocName(String strLocName) {
		this.strLocName = strLocName;
	}

	public String getStrGRNCode() {
		return strGRNCode;
	}

	public void setStrGRNCode(String strGRNCode) {
		this.strGRNCode = strGRNCode;
	}

	public String getDtGRNDate() {
		return dtGRNDate;
	}

	public void setDtGRNDate(String dtGRNDate) {
		this.dtGRNDate = dtGRNDate;
	}

	public String getStrSuppCode() {
		return strSuppCode;
	}

	public void setStrSuppCode(String strSuppCode) {
		this.strSuppCode = strSuppCode;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	public String getStrPONo() {
		return strPONo;
	}

	public void setStrPONo(String strPONo) {
		this.strPONo = (String) setDefaultValue(strPONo, "");
	}

	public String getStrBillNo() {
		return strBillNo;
	}

	public void setStrBillNo(String strBillNo) {
		this.strBillNo = strBillNo;
	}

	public String getDtDueDate() {
		return dtDueDate;
	}

	public void setDtDueDate(String dtDueDate) {
		this.dtDueDate = dtDueDate;
	}

	public String getStrPayMode() {
		return strPayMode;
	}

	public void setStrPayMode(String strPayMode) {
		this.strPayMode = strPayMode;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrVehNo() {
		return strVehNo;
	}

	public void setStrVehNo(String strVehNo) {
		this.strVehNo = strVehNo;
	}

	public String getStrMInBy() {
		return strMInBy;
	}

	public void setStrMInBy(String strMInBy) {
		this.strMInBy = strMInBy;
	}

	public String getStrTimeInOut() {
		return strTimeInOut;
	}

	public void setStrTimeInOut(String strTimeInOut) {
		this.strTimeInOut = strTimeInOut;
	}

	public String getStrNo() {
		return strNo;
	}

	public void setStrNo(String strNo) {
		this.strNo = strNo;
	}

	public String getStrRefNo() {
		return strRefNo;
	}

	public void setStrRefNo(String strRefNo) {
		this.strRefNo = strRefNo;
	}

	public String getDtRefDate() {
		return dtRefDate;
	}

	public void setDtRefDate(String dtRefDate) {
		this.dtRefDate = dtRefDate;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getDtDateCreated() {
		return dtDateCreated;
	}

	public void setDtDateCreated(String dtDateCreated) {
		this.dtDateCreated = dtDateCreated;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = (String) setDefaultValue(strCurrency, "");;
	}

	public String getStrShipmentMode() {
		return strShipmentMode;
	}

	public void setStrShipmentMode(String strShipmentMode) {
		this.strShipmentMode = strShipmentMode;
	}

	public String getDtShipmentDate() {
		return dtShipmentDate;
	}

	public void setDtShipmentDate(String dtShipmentDate) {
		this.dtShipmentDate = dtShipmentDate;
	}

	public String getStrCountryofOrigin() {
		return strCountryofOrigin;
	}

	public void setStrCountryofOrigin(String strCountryofOrigin) {
		this.strCountryofOrigin = strCountryofOrigin;
	}

	public String getStrConsignedCountry() {
		return strConsignedCountry;
	}

	public void setStrConsignedCountry(String strConsignedCountry) {
		this.strConsignedCountry = strConsignedCountry;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public double getDblConversion() {
		return dblConversion;
	}

	public void setDblConversion(double dblConversion) {
		this.dblConversion = dblConversion;
	}

	public double getDblSubTotal() {
		return dblSubTotal;
	}

	public void setDblSubTotal(double dblSubTotal) {
		this.dblSubTotal = dblSubTotal;
	}

	public double getDblDisRate() {
		return dblDisRate;
	}

	public void setDblDisRate(double dblDisRate) {
		this.dblDisRate = dblDisRate;
	}

	public double getDblDisAmt() {
		return dblDisAmt;
	}

	public void setDblDisAmt(double dblDisAmt) {
		this.dblDisAmt = dblDisAmt;
	}

	public double getDblTaxAmt() {
		return dblTaxAmt;
	}

	public void setDblTaxAmt(double dblTaxAmt) {
		this.dblTaxAmt = dblTaxAmt;
	}

	public double getDblTotal() {
		return dblTotal;
	}

	public void setDblTotal(double dblTotal) {
		this.dblTotal = dblTotal;
	}

	public double getDblExtra() {
		return dblExtra;
	}

	public void setDblExtra(double dblExtra) {
		this.dblExtra = dblExtra;
	}

	public double getDblLessAmt() {
		return dblLessAmt;
	}

	public void setDblLessAmt(double dblLessAmt) {
		this.dblLessAmt = dblLessAmt;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getDtBillDate() {
		return dtBillDate;
	}

	public void setDtBillDate(String dtBillDate) {
		this.dtBillDate = dtBillDate;
	}

	public double getDblValueTotal() {
		return dblValueTotal;
	}

	public void setDblValueTotal(double dblValueTotal) {
		this.dblValueTotal = dblValueTotal;
	}

	@Column(name = "intLevel", updatable=false, columnDefinition = "Int(8) default '0'")
	private int intLevel = 0;

	public int getIntLevel() {
		return intLevel;
	}

	public void setIntLevel(int intLevel) {
		this.intLevel = (int) setDefaultValue(intLevel, "0");
	}

	public double getDblRoundOff() {
		return dblRoundOff;
	}

	public void setDblRoundOff(double dblRoundOff) {
		this.dblRoundOff = dblRoundOff;
	}

	public double getDblFOB() {
		return dblFOB;
	}

	public void setDblFOB(double dblFOB) {
		this.dblFOB = dblFOB;
	}

	public double getDblFreight() {
		return dblFreight;
	}

	public void setDblFreight(double dblFreight) {
		this.dblFreight = dblFreight;
	}

	public double getDblInsurance() {
		return dblInsurance;
	}

	public void setDblInsurance(double dblInsurance) {
		this.dblInsurance = dblInsurance;
	}

	public double getDblOtherCharges() {
		return dblOtherCharges;
	}

	public void setDblOtherCharges(double dblOtherCharges) {
		this.dblOtherCharges = dblOtherCharges;
	}

	public double getDblVATClaim() {
		return dblVATClaim;
	}

	public void setDblVATClaim(double dblVATClaim) {
		this.dblVATClaim = (Double) setDefaultValue (dblVATClaim,"0.0000");
	}

	public double getDblClearingCharges() {
		return dblClearingCharges;
	}

	public void setDblClearingCharges(double dblClearingCharges) {
		this.dblClearingCharges = (Double) setDefaultValue (dblClearingCharges,"0.0000");
	}

	public String getStrJVNo() {
		return strJVNo;
	}

	public void setStrJVNo(String strJVNo) {
		this.strJVNo =(String) setDefaultValue(strJVNo, ""); ;
	}
}
