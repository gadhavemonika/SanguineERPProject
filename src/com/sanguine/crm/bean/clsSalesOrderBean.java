package com.sanguine.crm.bean;

import java.util.List;

import com.sanguine.crm.model.clsSalesCharModel;
import com.sanguine.crm.model.clsSalesOrderDtl;
import com.sanguine.crm.model.clsSalesOrderTaxDtlModel;

public class clsSalesOrderBean {
	// Variable Declaration
	private String strSOCode;

	private long intId;

	private String dteSODate;

	private String strCustCode;

	private String strCustPONo;

	private String dteCPODate;

	private String strStatus;

	private String strLocCode;

	private String dteFulmtDate;

	private String strPayMode;

	private String strAgainst;

	private long intwarmonth;

	private String strCurrency;

	private String strBAdd1;

	private String strBAdd2;

	private String strBCity;

	private String strBState;

	private String strBCountry;

	private String strBPin;

	private String strSAdd1;

	private String strSAdd2;

	private String strSCity;

	private String strSState;

	private String strSCountry;

	private String strSPin;

	private double dblSubTotal;

	private double dblDisRate;

	private double dblDisAmt;

	private double dblTaxAmt;

	private double dblExtra;

	private double dblTotal;

	private String dtefulfilled;

	private String strNarration;

	private String strBOMFlag;

	private String strAuthorise;

	private String strImgName;

	private String strSysModel;

	private String strCranenModel;

	private String strMaxCap;

	private String strWipeRopeDia;

	private String strNoFall;

	private String strSuppVolt;

	private String strBoomLen;

	private String strReaCode;

	private String strCode;

	private String strWarrPeriod;

	private String strWarraValidity;

	private double dblConversion;

	private String strCloseSO;

	private String strUserCreated;

	private String dteDateCreated;

	private String strUserModified;

	private String dteLastModified;

	private String strClientCode;

	private String strLocName;

	private String strcustName;

	private String strSettlementCode;

	private String strMobileNoForSettlement;

	private List<clsSalesOrderDtl> listSODtl;

	private List<clsSalesCharModel> listSalesChar;
	
	private List<clsSalesOrderTaxDtlModel> listSalesOrderTaxDtl;

	public List<clsSalesOrderDtl> getListSODtl() {
		return listSODtl;
	}

	public void setListSODtl(List<clsSalesOrderDtl> listSODtl) {
		this.listSODtl = listSODtl;
	}

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

	public String getDtefulfilled() {
		return dtefulfilled;
	}

	public void setDtefulfilled(String dtefulfilled) {
		this.dtefulfilled = dtefulfilled;
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

	public String getStrLocName() {
		return strLocName;
	}

	public void setStrLocName(String strLocName) {
		this.strLocName = strLocName;
	}

	public String getStrcustName() {
		return strcustName;
	}

	public void setStrcustName(String strcustName) {
		this.strcustName = strcustName;
	}

	public List<clsSalesCharModel> getListSalesChar() {
		return listSalesChar;
	}

	public void setListSalesChar(List<clsSalesCharModel> listSalesChar) {
		this.listSalesChar = listSalesChar;
	}

	public String getStrSettlementCode() {
		return strSettlementCode;
	}

	public void setStrSettlementCode(String strSettlementCode) {
		this.strSettlementCode = strSettlementCode;
	}

	public String getStrMobileNoForSettlement() {
		return strMobileNoForSettlement;
	}

	public void setStrMobileNoForSettlement(String strMobileNoForSettlement) {
		this.strMobileNoForSettlement = strMobileNoForSettlement;
	}

	public List<clsSalesOrderTaxDtlModel> getListSalesOrderTaxDtl() {
		return listSalesOrderTaxDtl;
	}

	public void setListSalesOrderTaxDtl(List<clsSalesOrderTaxDtlModel> listSalesOrderTaxDtl) {
		this.listSalesOrderTaxDtl = listSalesOrderTaxDtl;
	}

}
