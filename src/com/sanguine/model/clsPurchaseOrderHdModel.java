package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "tblpurchaseorderhd")
@IdClass(clsPurchaseOrderHdModel_ID.class)
public class clsPurchaseOrderHdModel implements Serializable {
	public clsPurchaseOrderHdModel() {
	}

	public clsPurchaseOrderHdModel(clsPurchaseOrderHdModel_ID clsPurchaseOrderHdModel_ID) {
		strPOCode = clsPurchaseOrderHdModel_ID.getStrPOCode();
		strClientCode = clsPurchaseOrderHdModel_ID.getStrClientCode();
	}

	@Column(name = "intId", updatable = false)
	private long intId;

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strPOCode", column = @Column(name = "strPOCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strPOCode")
	private String strPOCode;

	@Column(name = "dtPODate")
	private String dtPODate;

	@Column(name = "strSuppCode")
	private String strSuppCode;

	@Column(name = "strAgainst")
	private String strAgainst;

	@Column(name = "strSOCode")
	private String strSOCode;

	@Column(name = "dblTotal", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblTotal;

	@Column(name = "dblTaxAmt", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblTaxAmt;

	@Column(name = "strVAddress1")
	private String strVAddress1;

	@Column(name = "strVAddress2")
	private String strVAddress2;

	@Column(name = "strVCity")
	private String strVCity;

	@Column(name = "strVState")
	private String strVState;

	@Column(name = "strVCountry")
	private String strVCountry;

	@Column(name = "strVPin")
	private String strVPin;

	@Column(name = "strSAddress1")
	private String strSAddress1;

	@Column(name = "strSAddress2")
	private String strSAddress2;

	@Column(name = "strSCity")
	private String strSCity;

	@Column(name = "strSState")
	private String strSState;

	@Column(name = "strSCountry")
	private String strSCountry;

	@Column(name = "strSPin")
	private String strSPin;

	@Column(name = "strYourRef")
	private String strYourRef;

	@Column(name = "strPerRef")
	private String strPerRef;

	@Column(name = "strEOE")
	private String strEOE;

	@Column(name = "strCode")
	private String strCode;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "dtDateCreated", updatable = false)
	private String dtDateCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dtLastModified")
	private String dtLastModified;

	@Column(name = "strClosePO")
	private String strClosePO;

	@Column(name = "strAuthorise", updatable = false)
	private String strAuthorise;

	@Column(name = "dtDelDate")
	private String dtDelDate;

	@Column(name = "dblExtra")
	private double dblExtra;

	@Column(name = "dblFinalAmt")
	private double dblFinalAmt;

	@Column(name = "strExcise")
	private String strExcise;

	@Column(name = "dblDiscount", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblDiscount;

	@Column(name = "strPayMode")
	private String strPayMode;

	@Column(name = "strCurrency")
	private String strCurrency;

	@Column(name = "strAmedment")
	private String strAmedment;

	@Column(name = "strAmntNO")
	private String strAmntNO;

	@Column(name = "strEdit")
	private String strEdit;

	@Column(name = "strUserAmed")
	private String strUserAmed;

	@Column(name = "dtPayDate")
	private String dtPayDate;

	@Column(name = "dblConversion", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblConversion;

	@Column(name = "strClientCode", updatable = false)
	private String strClientCode;

	@Column(name = "strFinalAmtInWord", columnDefinition = "VARCHAR(500) NOT NULL default ''")
	private String strFinalAmtInWord;

	@Column(name = "strPropCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strPropCode;

	@Column(name = "dblFOB", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblFOB;

	@Column(name = "dblFreight", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblFreight;

	@Column(name = "dblInsurance", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblInsurance;

	@Column(name = "dblOtherCharges", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblOtherCharges;

	@Column(name = "dblCIF", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblCIF;

	@Column(name = "dblClearingAgentCharges", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblClearingAgentCharges;

	@Column(name = "dblVATClaim", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblVATClaim;
	
	@Column(name = "dblExchangeRate", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblExchangeRate;

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

	public String getStrPOCode() {
		return strPOCode;
	}

	public void setStrPOCode(String strPOCode) {
		this.strPOCode = strPOCode;
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

	public String getStrSOCode() {
		return strSOCode;
	}

	public void setStrSOCode(String strSOCode) {
		this.strSOCode = strSOCode;
	}

	public double getDblTotal() {
		return dblTotal;
	}

	public void setDblTotal(double dblTotal) {
		this.dblTotal = dblTotal;
	}

	public String getStrVAddress1() {
		return strVAddress1;
	}

	public void setStrVAddress1(String strVAddress1) {
		this.strVAddress1 = strVAddress1;
	}

	public String getStrVAddress2() {
		return strVAddress2;
	}

	public void setStrVAddress2(String strVAddress2) {
		this.strVAddress2 = strVAddress2;
	}

	public String getStrVCity() {
		return strVCity;
	}

	public void setStrVCity(String strVCity) {
		this.strVCity = strVCity;
	}

	public String getStrVState() {
		return strVState;
	}

	public void setStrVState(String strVState) {
		this.strVState = strVState;
	}

	public String getStrVCountry() {
		return strVCountry;
	}

	public void setStrVCountry(String strVCountry) {
		this.strVCountry = strVCountry;
	}

	public String getStrVPin() {
		return strVPin;
	}

	public void setStrVPin(String strVPin) {
		this.strVPin = strVPin;
	}

	public String getStrSAddress1() {
		return strSAddress1;
	}

	public void setStrSAddress1(String strSAddress1) {
		this.strSAddress1 = strSAddress1;
	}

	public String getStrSAddress2() {
		return strSAddress2;
	}

	public void setStrSAddress2(String strSAddress2) {
		this.strSAddress2 = strSAddress2;
	}

	public String getStrSCity() {
		return strSCity;
	}

	public void setStrSCity(String strSCity) {
		this.strSCity = strSCity;
	}

	public String getStrSState() {
		return strSState;
	}

	public void setStrSState(String strSState) {
		this.strSState = strSState;
	}

	public String getStrSCountry() {
		return strSCountry;
	}

	public void setStrSCountry(String strSCountry) {
		this.strSCountry = strSCountry;
	}

	public String getStrSPin() {
		return strSPin;
	}

	public void setStrSPin(String strSPin) {
		this.strSPin = strSPin;
	}

	public String getStrYourRef() {
		return strYourRef;
	}

	public void setStrYourRef(String strYourRef) {
		this.strYourRef = strYourRef;
	}

	public String getStrPerRef() {
		return strPerRef;
	}

	public void setStrPerRef(String strPerRef) {
		this.strPerRef = strPerRef;
	}

	public String getStrEOE() {
		return strEOE;
	}

	public void setStrEOE(String strEOE) {
		this.strEOE = strEOE;
	}

	public String getStrCode() {
		return strCode;
	}

	public void setStrCode(String strCode) {
		this.strCode = strCode;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	public double getDblExtra() {
		return dblExtra;
	}

	public void setDblExtra(double dblExtra) {
		this.dblExtra = dblExtra;
	}

	public double getDblFinalAmt() {
		return dblFinalAmt;
	}

	public void setDblFinalAmt(double dblFinalAmt) {
		this.dblFinalAmt = dblFinalAmt;
	}

	public String getStrExcise() {
		return strExcise;
	}

	public void setStrExcise(String strExcise) {
		this.strExcise = strExcise;
	}

	public double getDblDiscount() {
		return dblDiscount;
	}

	public void setDblDiscount(double dblDiscount) {
		this.dblDiscount = dblDiscount;
	}

	public String getStrPayMode() {
		return strPayMode;
	}

	public void setStrPayMode(String strPayMode) {
		this.strPayMode = strPayMode;
	}

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = (String) setDefaultValue(strCurrency, "");
	}

	public String getStrAmedment() {
		return strAmedment;
	}

	public void setStrAmedment(String strAmedment) {
		this.strAmedment = strAmedment;
	}

	public String getStrAmntNO() {
		return strAmntNO;
	}

	public void setStrAmntNO(String strAmntNO) {
		this.strAmntNO = strAmntNO;
	}

	public String getStrUserAmed() {
		return strUserAmed;
	}

	public void setStrUserAmed(String strUserAmed) {
		this.strUserAmed = strUserAmed;
	}

	public double getDblConversion() {
		return dblConversion;
	}

	public void setDblConversion(double dblConversion) {
		this.dblConversion = dblConversion;
	}

	public String getStrClosePO() {
		return strClosePO;
	}

	public void setStrClosePO(String strClosePO) {
		this.strClosePO = strClosePO;
	}

	public String getStrEdit() {
		return strEdit;
	}

	public void setStrEdit(String strEdit) {
		this.strEdit = strEdit;
	}

	public double getDblTaxAmt() {
		return dblTaxAmt;
	}

	public void setDblTaxAmt(double dblTaxAmt) {
		this.dblTaxAmt = dblTaxAmt;
	}

	@Column(name = "intLevel", updatable=false, columnDefinition = "Int(8) default '0'")
	private int intLevel = 0;

	public int getIntLevel() {
		return intLevel;
	}

	public void setIntLevel(int intLevel) {
		this.intLevel = (int) setDefaultValue(intLevel, "0");
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

	public String getStrFinalAmtInWord() {
		return strFinalAmtInWord;
	}

	public void setStrFinalAmtInWord(String strFinalAmtInWord) {
		this.strFinalAmtInWord = strFinalAmtInWord;
	}

	public String getStrPropCode() {
		return strPropCode;
	}

	public void setStrPropCode(String strPropCode) {
		this.strPropCode = strPropCode;
	}

	public String getDtPODate() {
		return dtPODate;
	}

	public void setDtPODate(String dtPODate) {
		this.dtPODate = dtPODate;
	}

	public String getDtDateCreated() {
		return dtDateCreated;
	}

	public void setDtDateCreated(String dtDateCreated) {
		this.dtDateCreated = dtDateCreated;
	}

	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	public String getDtDelDate() {
		return dtDelDate;
	}

	public void setDtDelDate(String dtDelDate) {
		this.dtDelDate = dtDelDate;
	}

	public String getDtPayDate() {
		return dtPayDate;
	}

	public void setDtPayDate(String dtPayDate) {
		this.dtPayDate = dtPayDate;
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

	public double getDblCIF() {
		return dblCIF;
	}

	public void setDblCIF(double dblCIF) {
		this.dblCIF = dblCIF;
	}

	public double getDblClearingAgentCharges() {
		return dblClearingAgentCharges;
	}

	public void setDblClearingAgentCharges(double dblClearingAgentCharges) {
		this.dblClearingAgentCharges = dblClearingAgentCharges;
	}

	public double getDblVATClaim() {
		return dblVATClaim;
	}

	public void setDblVATClaim(double dblVATClaim) {
		this.dblVATClaim = dblVATClaim;
	}

	public double getDblExchangeRate() {
		return dblExchangeRate;
	}

	public void setDblExchangeRate(double dblExchangeRate) {
		this.dblExchangeRate = (Double) setDefaultValue(dblExchangeRate, 0.00);
	}
	
	
	
}
