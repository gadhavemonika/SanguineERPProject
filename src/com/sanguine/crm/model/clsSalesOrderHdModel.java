package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "tblsalesorderhd")
@IdClass(clsSalesOrderHdModel_ID.class)
public class clsSalesOrderHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsSalesOrderHdModel() {
	}

	public clsSalesOrderHdModel(clsSalesOrderHdModel_ID objModelID) {
		strSOCode = objModelID.getStrSOCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strSOCode", column = @Column(name = "strSOCode")), 
	@AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strSOCode")
	private String strSOCode;

	@Column(name = "intId")
	private long intId;

	@Column(name = "dteSODate")
	private String dteSODate;

	@Column(name = "strCustCode")
	private String strCustCode;

	@Column(name = "strCustPONo")
	private String strCustPONo;

	@Column(name = "strStatus")
	private String strStatus;

	@Column(name = "dteFulmtDate")
	private String dteFulmtDate;

	@Column(name = "strLocCode")
	private String strLocCode;

	@Column(name = "strPayMode")
	private String strPayMode;

	@Column(name = "strBAdd1")
	private String strBAdd1;

	@Column(name = "strBAdd2")
	private String strBAdd2;

	@Column(name = "strBCity")
	private String strBCity;

	@Column(name = "strBState")
	private String strBState;

	@Column(name = "strBCountry")
	private String strBCountry;

	@Column(name = "strBPin")
	private String strBPin;

	@Column(name = "strSAdd1")
	private String strSAdd1;

	@Column(name = "strSAdd2")
	private String strSAdd2;

	@Column(name = "strSCity")
	private String strSCity;

	@Column(name = "strSState")
	private String strSState;

	@Column(name = "strSCountry")
	private String strSCountry;

	@Column(name = "strSPin")
	private String strSPin;

	@Column(name = "dblSubTotal")
	private double dblSubTotal;

	@Column(name = "dblDisRate")
	private double dblDisRate;

	@Column(name = "dblDisAmt")
	private double dblDisAmt;

	@Column(name = "dblTaxAmt")
	private double dblTaxAmt;

	@Column(name = "dblExtra")
	private double dblExtra;

	@Column(name = "dblTotal")
	private double dblTotal;

	@Column(name = "strNarration")
	private String strNarration;

	@Column(name = "strBOMFlag")
	private String strBOMFlag;

	@Column(name = "strAuthorise")
	private String strAuthorise;

	@Column(name = "strImgName")
	private String strImgName;

	@Column(name = "dteCPODate")
	private String dteCPODate;

	@Column(name = "strSysModel")
	private String strSysModel;

	@Column(name = "strCranenModel")
	private String strCranenModel;

	@Column(name = "strMaxCap")
	private String strMaxCap;

	@Column(name = "strWipeRopeDia")
	private String strWipeRopeDia;

	@Column(name = "strNoFall")
	private String strNoFall;

	@Column(name = "strSuppVolt")
	private String strSuppVolt;

	@Column(name = "strBoomLen")
	private String strBoomLen;

	@Column(name = "strCurrency")
	private String strCurrency;

	@Column(name = "strReaCode")
	private String strReaCode;

	@Column(name = "strAgainst")
	private String strAgainst;

	@Column(name = "strCode")
	private String strCode;

	@Column(name = "strWarrPeriod")
	private String strWarrPeriod;

	@Column(name = "strWarraValidity")
	private String strWarraValidity;

	@Column(name = "intwarmonth")
	private long intwarmonth;

	@Column(name = "dblConversion")
	private double dblConversion;

	@Column(name = "strCloseSO", nullable = true)
	private String strCloseSO;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "dteDateCreated", updatable = false)
	private String dteDateCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteLastModified")
	private String dteLastModified;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strSettlementCode")
	private String strSettlementCode;

	@Column(name = "strMobileNo")
	private String strMobileNo;

	// Setter-Getter Methods
	public String getStrSOCode() {
		return strSOCode;
	}

	public void setStrSOCode(String strSOCode) {
		this.strSOCode = strSOCode;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getDteSODate() {
		return dteSODate;
	}

	public void setDteSODate(String dteSODate) {
		this.dteSODate = dteSODate;
	}

	public String getStrCustCode() {
		return strCustCode;
	}

	public void setStrCustCode(String strCustCode) {
		this.strCustCode = strCustCode;
	}

	public String getStrCustPONo() {
		return strCustPONo;
	}

	public void setStrCustPONo(String strCustPONo) {
		this.strCustPONo = strCustPONo;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public String getDteFulmtDate() {
		return dteFulmtDate;
	}

	public void setDteFulmtDate(String dteFulmtDate) {
		this.dteFulmtDate = dteFulmtDate;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrPayMode() {
		return strPayMode;
	}

	public void setStrPayMode(String strPayMode) {
		this.strPayMode = strPayMode;
	}

	public String getStrBAdd1() {
		return strBAdd1;
	}

	public void setStrBAdd1(String strBAdd1) {
		this.strBAdd1 = strBAdd1;
	}

	public String getStrBAdd2() {
		return strBAdd2;
	}

	public void setStrBAdd2(String strBAdd2) {
		this.strBAdd2 = strBAdd2;
	}

	public String getStrBCity() {
		return strBCity;
	}

	public void setStrBCity(String strBCity) {
		this.strBCity = strBCity;
	}

	public String getStrBState() {
		return strBState;
	}

	public void setStrBState(String strBState) {
		this.strBState = strBState;
	}

	public String getStrBCountry() {
		return strBCountry;
	}

	public void setStrBCountry(String strBCountry) {
		this.strBCountry = strBCountry;
	}

	public String getStrBPin() {
		return strBPin;
	}

	public void setStrBPin(String strBPin) {
		this.strBPin = strBPin;
	}

	public String getStrSAdd1() {
		return strSAdd1;
	}

	public void setStrSAdd1(String strSAdd1) {
		this.strSAdd1 = strSAdd1;
	}

	public String getStrSAdd2() {
		return strSAdd2;
	}

	public void setStrSAdd2(String strSAdd2) {
		this.strSAdd2 = strSAdd2;
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

	public double getDblExtra() {
		return dblExtra;
	}

	public void setDblExtra(double dblExtra) {
		this.dblExtra = dblExtra;
	}

	public double getDblTotal() {
		return dblTotal;
	}

	public void setDblTotal(double dblTotal) {
		this.dblTotal = dblTotal;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrBOMFlag() {
		return strBOMFlag;
	}

	public void setStrBOMFlag(String strBOMFlag) {
		this.strBOMFlag = strBOMFlag;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	public String getStrImgName() {
		return strImgName;
	}

	public void setStrImgName(String strImgName) {
		this.strImgName = strImgName;
	}

	public String getDteCPODate() {
		return dteCPODate;
	}

	public void setDteCPODate(String dteCPODate) {
		this.dteCPODate = dteCPODate;
	}

	public String getStrSysModel() {
		return strSysModel;
	}

	public void setStrSysModel(String strSysModel) {
		this.strSysModel = strSysModel;
	}

	public String getStrCranenModel() {
		return strCranenModel;
	}

	public void setStrCranenModel(String strCranenModel) {
		this.strCranenModel = strCranenModel;
	}

	public String getStrMaxCap() {
		return strMaxCap;
	}

	public void setStrMaxCap(String strMaxCap) {
		this.strMaxCap = strMaxCap;
	}

	public String getStrWipeRopeDia() {
		return strWipeRopeDia;
	}

	public void setStrWipeRopeDia(String strWipeRopeDia) {
		this.strWipeRopeDia = strWipeRopeDia;
	}

	public String getStrNoFall() {
		return strNoFall;
	}

	public void setStrNoFall(String strNoFall) {
		this.strNoFall = strNoFall;
	}

	public String getStrSuppVolt() {
		return strSuppVolt;
	}

	public void setStrSuppVolt(String strSuppVolt) {
		this.strSuppVolt = strSuppVolt;
	}

	public String getStrBoomLen() {
		return strBoomLen;
	}

	public void setStrBoomLen(String strBoomLen) {
		this.strBoomLen = strBoomLen;
	}

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
	}

	public String getStrReaCode() {
		return strReaCode;
	}

	public void setStrReaCode(String strReaCode) {
		this.strReaCode = strReaCode;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	public String getStrCode() {
		return strCode;
	}

	public void setStrCode(String strCode) {
		this.strCode = strCode;
	}

	public String getStrWarrPeriod() {
		return strWarrPeriod;
	}

	public void setStrWarrPeriod(String strWarrPeriod) {
		this.strWarrPeriod = strWarrPeriod;
	}

	public String getStrWarraValidity() {
		return strWarraValidity;
	}

	public void setStrWarraValidity(String strWarraValidity) {
		this.strWarraValidity = strWarraValidity;
	}

	public long getIntwarmonth() {
		return intwarmonth;
	}

	public void setIntwarmonth(long intwarmonth) {
		this.intwarmonth = intwarmonth;
	}

	public double getDblConversion() {
		return dblConversion;
	}

	public void setDblConversion(double dblConversion) {
		this.dblConversion = dblConversion;
	}

	public String getStrCloseSO() {
		return strCloseSO;
	}

	public void setStrCloseSO(String strCloseSO) {
		this.strCloseSO = strCloseSO;
		;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getDteDateCreated() {
		return dteDateCreated;
	}

	public void setDteDateCreated(String dteDateCreated) {
		this.dteDateCreated = dteDateCreated;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
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
		this.strClientCode = strClientCode;
	}

	public String getStrSettlementCode() {
		return strSettlementCode;
	}

	public void setStrSettlementCode(String strSettlementCode) {
		this.strSettlementCode = (String) setDefaultValue(strSettlementCode, "");
	}

	public String getStrMobileNo() {
		return strMobileNo;
	}

	public void setStrMobileNo(String strMobileNo) {
		this.strMobileNo = strMobileNo;
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
