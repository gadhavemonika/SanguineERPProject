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

@Entity
@Table(name = "tblAudithd")
@IdClass(clsAuditModel_ID.class)
public class clsAuditHdModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public clsAuditHdModel() {
	}

	public clsAuditHdModel(clsAuditModel_ID clsAuditModel_ID) {
		strTransCode = clsAuditModel_ID.getStrTransCode();
		strClientCode = clsAuditModel_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strTransCode", column = @Column(name = "strTransCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "intId", nullable = false, updatable = false)
	private long intId;

	@Column(name = "strTransCode")
	private String strTransCode;

	@Column(name = "dtTransDate")
	private String dtTransDate;

	@Column(name = "strTransType", columnDefinition = "VARCHAR(20) NOT NULL default ' ' ")
	private String strTransType;

	@Column(name = "strTransMode", columnDefinition = "VARCHAR(10) NOT NULL default ' ' ")
	private String strTransMode;

	@Column(name = "strAgainst", nullable = true, columnDefinition = "VARCHAR(20)  default ' ' ")
	private String strAgainst;

	@Column(name = "strLocBy", nullable = true, columnDefinition = "VARCHAR(10)  default ' ' ")
	private String strLocBy;

	@Column(name = "strLocOn", nullable = true, columnDefinition = "VARCHAR(10)  default ' ' ")
	private String strLocOn;

	@Column(name = "strLocCode", nullable = true, columnDefinition = "VARCHAR(10) default ' ' ")
	private String strLocCode;

	@Transient
	private String strLocOnName;

	@Transient
	private String strLocByName;

	@Column(name = "dblWOQty", columnDefinition = "DECIMAL(18,4) NOT NULL default '0' ")
	private double dblWOQty;

	@Column(name = "dblSubTotal", columnDefinition = "DECIMAL(18,4) NOT NULL default '0' ")
	private double dblSubTotal;

	@Column(name = "dblTotalAmt", columnDefinition = "DECIMAL(18,4) NOT NULL default '0' ")
	private double dblTotalAmt;

	@Column(name = "strWoCode", nullable = true, columnDefinition = "VARCHAR(20) default ' ' ")
	private String strWoCode;

	@Column(name = "strCloseReq", nullable = true, columnDefinition = "CHAR(4) default ' ' ")
	private String strCloseReq;

	@Column(name = "strNarration", nullable = true, columnDefinition = "VARCHAR(200)  default ' ' ")
	private String strNarration;

	@Column(name = "strAuthorise", nullable = true, columnDefinition = "VARCHAR(4) NOT NULL default 'No' ")
	private String strAuthorise;

	@Column(name = "strUserCreated", nullable = false, updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserCreated;

	@Column(name = "strUserModified", columnDefinition = "VARCHAR(20) NOT NULL default ' ' ")
	private String strUserModified;

	@Column(name = "dtDateCreated", nullable = false, updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtDateCreated;

	@Column(name = "dtLastModified")
	private String dtLastModified;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strClosePO", nullable = true, columnDefinition = "VARCHAR(10) default ' '")
	private String strClosePO;

	@Column(name = "dblTaxAmt", columnDefinition = "DECIMAL(18,4) NOT NULL default '0'")
	private double dblTaxAmt;

	@Column(name = "dtDelDate", nullable = true, columnDefinition = "VARCHAR(25)  default ' '")
	private String dtDelDate;

	@Column(name = "dblExtra", columnDefinition = "DECIMAL(18,4) NOT NULL default '0'")
	private double dblExtra;

	@Column(name = "dblLessAmt", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblLessAmt;

	@Column(name = "strExcise", nullable = true, columnDefinition = "VARCHAR(20)  default ' ' ")
	private String strExcise;

	@Column(name = "dblDiscount", columnDefinition = "DECIMAL(18,4) NOT NULL default '0'")
	private double dblDiscount;

	@Column(name = "dblDisRate", columnDefinition = "DECIMAL(18,4) NOT NULL default '0'")
	private double dblDisRate;

	@Column(name = "strPayMode", nullable = true, columnDefinition = "VARCHAR(20)  default ' '")
	private String strPayMode;

	@Column(name = "dtPayDate", nullable = true, columnDefinition = "VARCHAR(25)  default ' '")
	private String dtPayDate;

	@Column(name = "strShipmentMode", nullable = true, columnDefinition = "VARCHAR(255)  default ''")
	private String strShipmentMode;

	@Column(name = "strVehNo", nullable = true, columnDefinition = "VARCHAR(255)  default ''")
	private String strVehNo;

	@Column(name = "strMInBy", nullable = true, columnDefinition = "VARCHAR(255) default ''")
	private String strMInBy;

	@Column(name = "strTimeInOut", nullable = true, columnDefinition = "VARCHAR(255)  default ''")
	private String strTimeInOut;

	@Column(name = "strNo", nullable = true, columnDefinition = "VARCHAR(255)  default ''")
	private String strNo;

	@Column(name = "strRefNo", nullable = true, columnDefinition = "VARCHAR(255)  default ''")
	private String strRefNo;

	@Column(name = "dtRefDate", nullable = true, columnDefinition = "VARCHAR(255) default ''")
	private String dtRefDate;

	@Column(name = "strSuppCode", nullable = true, columnDefinition = "VARCHAR(255)  default ''")
	private String strSuppCode;

	@Column(name = "strBillNo", nullable = true, columnDefinition = "VARCHAR(255) default ''")
	private String strBillNo;

	@Column(name = "dtBillDate", nullable = true, columnDefinition = "VARCHAR(255)  default ''")
	private String dtBillDate;

	@Column(name = "dtDueDate", nullable = true, columnDefinition = "VARCHAR(255)  default ''")
	private String dtDueDate;

	@Column(name = "strGRNCode", nullable = true, columnDefinition = "VARCHAR(15)  default ''")
	private String strGRNCode;

	@Column(name = "strCode", nullable = true, columnDefinition = "VARCHAR(25)  default ''")
	private String strCode;

	@Column(name = "strVAddress1", nullable = true, columnDefinition = "VARCHAR(225)  default ''")
	private String strVAddress1;

	@Column(name = "strVAddress2", nullable = true, columnDefinition = "VARCHAR(225)  default ''")
	private String strVAddress2;

	@Column(name = "strVCity", nullable = true, columnDefinition = "VARCHAR(225)  default ''")
	private String strVCity;

	@Column(name = "strVState", nullable = true, columnDefinition = "VARCHAR(225)  default ''")
	private String strVState;

	@Column(name = "strVCountry", nullable = true, columnDefinition = "VARCHAR(225)  default ''")
	private String strVCountry;

	@Column(name = "strVPin", nullable = true, columnDefinition = "VARCHAR(7)  default ''")
	private String strVPin;

	@Column(name = "strSAddress1", nullable = true, columnDefinition = "VARCHAR(225)  default ''")
	private String strSAddress1;

	@Column(name = "strSAddress2", nullable = true, columnDefinition = "VARCHAR(225)  default ''")
	private String strSAddress2;

	@Column(name = "strSCity", nullable = true, columnDefinition = "VARCHAR(225)  default ''")
	private String strSCity;

	@Column(name = "strSState", nullable = true, columnDefinition = "VARCHAR(225)  default ''")
	private String strSState;

	@Column(name = "strSCountry", nullable = true, columnDefinition = "VARCHAR(225)  default ''")
	private String strSCountry;

	@Column(name = "strSPin", nullable = true, columnDefinition = "VARCHAR(7)  default ''")
	private String strSPin;

	@Column(name = "strYourRef", nullable = true, columnDefinition = "VARCHAR(225)  default ''")
	private String strYourRef;

	@Column(name = "strPerRef", nullable = true, columnDefinition = "VARCHAR(225)  default ''")
	private String strPerRef;

	@Column(name = "strEOE", nullable = true, columnDefinition = "VARCHAR(225)  default ''")
	private String strEOE;

	@Column(name = "strCurrency", nullable = true, columnDefinition = "VARCHAR(5)  default ''")
	private String strCurrency;

	@Column(name = "strAmedment", nullable = true, columnDefinition = "VARCHAR(25)  default ''")
	private String strAmedment;

	@Column(name = "strAmntNO", nullable = true, columnDefinition = "VARCHAR(25)  default ''")
	private String strAmntNO;

	@Column(name = "strUserAmed", nullable = true, columnDefinition = "VARCHAR(25)  default ''")
	private String strUserAmed;

	@Column(name = "dblConversion", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblConversion;

	@Column(name = "strFinalAmtInWord", nullable = true, columnDefinition = "VARCHAR(500)  default ''")
	private String strFinalAmtInWord;

	@Column(name = "strEdit", nullable = true, columnDefinition = "VARCHAR(10)  default ''")
	private String strEdit;

	@Column(name = "strPONo", nullable = true, columnDefinition = "VARCHAR(255) default ''")
	private String strPONo;

	@Column(name = "strMaterialIssue", nullable = true, columnDefinition = "VARCHAR(4) default ''")
	private String strMaterialIssue;

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrTransCode() {
		return strTransCode;
	}

	public void setStrTransCode(String strTransCode) {
		this.strTransCode = strTransCode;
	}

	public String getDtTransDate() {
		return dtTransDate;
	}

	public void setDtTransDate(String dtTransDate) {
		this.dtTransDate = dtTransDate;
	}

	public String getStrTransType() {
		return strTransType;
	}

	public void setStrTransType(String strTransType) {
		this.strTransType = strTransType;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	public String getStrLocBy() {
		return strLocBy;
	}

	public void setStrLocBy(String strLocBy) {
		this.strLocBy = strLocBy;
	}

	public String getStrLocOn() {
		return strLocOn;
	}

	public void setStrLocOn(String strLocOn) {
		this.strLocOn = strLocOn;
	}

	public double getDblWOQty() {
		return dblWOQty;
	}

	public void setDblWOQty(double dblWOQty) {
		this.dblWOQty = dblWOQty;
	}

	public double getDblSubTotal() {
		return dblSubTotal;
	}

	public void setDblSubTotal(double dblSubTotal) {
		this.dblSubTotal = dblSubTotal;
	}

	public String getStrLocOnName() {
		return strLocOnName;
	}

	public void setStrLocOnName(String strLocOnName) {
		this.strLocOnName = strLocOnName;
	}

	public String getStrLocByName() {
		return strLocByName;
	}

	public void setStrLocByName(String strLocByName) {
		this.strLocByName = strLocByName;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrWoCode() {
		return strWoCode;
	}

	public void setStrWoCode(String strWoCode) {
		this.strWoCode = strWoCode;
	}

	public String getStrCloseReq() {
		return strCloseReq;
	}

	public void setStrCloseReq(String strCloseReq) {
		this.strCloseReq = strCloseReq;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
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

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrTransMode() {
		return strTransMode;
	}

	public void setStrTransMode(String strTransMode) {
		this.strTransMode = strTransMode;
	}

	public double getDblTotalAmt() {
		return dblTotalAmt;
	}

	public void setDblTotalAmt(double dblTotalAmt) {
		this.dblTotalAmt = dblTotalAmt;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrClosePO() {
		return strClosePO;
	}

	public void setStrClosePO(String strClosePO) {
		this.strClosePO = strClosePO;
	}

	public double getDblTaxAmt() {
		return dblTaxAmt;
	}

	public void setDblTaxAmt(double dblTaxAmt) {
		this.dblTaxAmt = dblTaxAmt;
	}

	public String getDtDelDate() {
		return dtDelDate;
	}

	public void setDtDelDate(String dtDelDate) {
		this.dtDelDate = dtDelDate;
	}

	public double getDblExtra() {
		return dblExtra;
	}

	public void setDblExtra(double dblExtra) {
		this.dblExtra = dblExtra;
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

	public String getDtPayDate() {
		return dtPayDate;
	}

	public void setDtPayDate(String dtPayDate) {
		this.dtPayDate = dtPayDate;
	}

	public double getDblLessAmt() {
		return dblLessAmt;
	}

	public void setDblLessAmt(double dblLessAmt) {
		this.dblLessAmt = dblLessAmt;
	}

	public String getStrShipmentMode() {
		return strShipmentMode;
	}

	public void setStrShipmentMode(String strShipmentMode) {
		this.strShipmentMode = strShipmentMode;
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

	public String getStrSuppCode() {
		return strSuppCode;
	}

	public void setStrSuppCode(String strSuppCode) {
		this.strSuppCode = strSuppCode;
	}

	public String getStrBillNo() {
		return strBillNo;
	}

	public void setStrBillNo(String strBillNo) {
		this.strBillNo = strBillNo;
	}

	public String getDtBillDate() {
		return dtBillDate;
	}

	public void setDtBillDate(String dtBillDate) {
		this.dtBillDate = dtBillDate;
	}

	public String getDtDueDate() {
		return dtDueDate;
	}

	public void setDtDueDate(String dtDueDate) {
		this.dtDueDate = dtDueDate;
	}

	public double getDblDisRate() {
		return dblDisRate;
	}

	public void setDblDisRate(double dblDisRate) {
		this.dblDisRate = dblDisRate;
	}

	public String getStrGRNCode() {
		return strGRNCode;
	}

	public void setStrGRNCode(String strGRNCode) {
		this.strGRNCode = strGRNCode;
	}

	public String getStrCode() {
		return strCode;
	}

	public void setStrCode(String strCode) {
		this.strCode = strCode;
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

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
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

	public String getStrFinalAmtInWord() {
		return strFinalAmtInWord;
	}

	public void setStrFinalAmtInWord(String strFinalAmtInWord) {
		this.strFinalAmtInWord = strFinalAmtInWord;
	}

	public String getStrEdit() {
		return strEdit;
	}

	public void setStrEdit(String strEdit) {
		this.strEdit = strEdit;
	}

	public String getStrPONo() {
		return strPONo;
	}

	public void setStrPONo(String strPONo) {
		this.strPONo = strPONo;
	}

	public String getStrMaterialIssue() {
		return strMaterialIssue;
	}

	public void setStrMaterialIssue(String strMaterialIssue) {
		this.strMaterialIssue = strMaterialIssue;
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

}
