package com.sanguine.bean;

import java.util.List;

import com.sanguine.model.clsGRNDtlModel;
import com.sanguine.model.clsGRNTaxDtlModel;
import com.sanguine.model.clsProdCharMasterModel;
import com.sanguine.util.clsGRNFixedAmtTax;

public class clsGRNBean {
	private String strGRNCode;

	private String dtGRNDate;

	private String strSuppCode;

	private String strSuppName;

	private String strAgainst;

	private String strPONo;

	private String strBillNo;

	private String dtBillDate;

	private String dtDueDate;

	private String strPayMode;

	private String strNarration;

	private String strLocCode;

	private String strLocName;

	private String strVehNo;

	private String strMInBy;

	private String strTimeInOut;

	private String strGRNNo;

	private String strRefNo;

	private String dtRefDate;

	private String strAuthorise;

	private String strCurrency;

	private String strShipmentMode;

	private String dtShipmentDate;

	private String strCountryofOrigin;

	private String strConsignedCountry;

	private double dblConversion;

	private double dblSubTotal;

	private double dblDisRate;

	private double dblDisAmt = 0;

	private double dblTaxAmt = 0;

	private double dblTotal;

	private double dblExtra = 0;

	private double dblLessAmt = 0;

	private double dblRoundOff = 0;

	private List<clsGRNDtlModel> listGRNDtl;

	private List<clsGRNFixedAmtTax> listFixedAmtTax;

	private List<clsGRNTaxDtlModel> listGRNTaxDtl;

	private List<clsTransectionProdCharBean> listProdChar;
	
	private String strAuthorize;
	
	private String strCurrencyCode;
	
	private double dblFOB;
	
	private double dblFreight;
	
	private double dblInsurance;
	
	private double dblOtherCharges;
	
	private double dblCIF;
	
	private double dblClearingAgentCharges;
	
	private double dblVATClaim;
	

	public List<clsGRNTaxDtlModel> getListGRNTaxDtl() {
		return listGRNTaxDtl;
	}

	public void setListGRNTaxDtl(List<clsGRNTaxDtlModel> listGRNTaxDtl) {
		this.listGRNTaxDtl = listGRNTaxDtl;
	}

	public List<clsGRNDtlModel> getListGRNDtl() {
		return listGRNDtl;
	}

	public void setListGRNDtl(List<clsGRNDtlModel> listGRNDtl) {
		this.listGRNDtl = listGRNDtl;
	}

	public List<clsGRNFixedAmtTax> getListFixedAmtTax() {
		return listFixedAmtTax;
	}

	public void setListFixedAmtTax(List<clsGRNFixedAmtTax> listFixedAmtTax) {
		this.listFixedAmtTax = listFixedAmtTax;
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
		this.strPONo = (String) setDefaultValue(strPONo, " ");
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

	public String getStrGRNNo() {
		return strGRNNo;
	}

	public void setStrGRNNo(String strGRNNo) {
		this.strGRNNo = strGRNNo;
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
		this.strCurrency = strCurrency;
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

	public String getStrLocName() {
		return strLocName;
	}

	public void setStrLocName(String strLocName) {
		this.strLocName = strLocName;
	}

	public String getDtBillDate() {
		return dtBillDate;
	}

	public void setDtBillDate(String dtBillDate) {
		this.dtBillDate = dtBillDate;
	}

	public String getStrSuppName() {
		return strSuppName;
	}

	public void setStrSuppName(String strSuppName) {
		this.strSuppName = strSuppName;
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

	public List<clsTransectionProdCharBean> getListProdChar() {
		return listProdChar;
	}

	public void setListProdChar(List<clsTransectionProdCharBean> listProdChar) {
		this.listProdChar = listProdChar;
	}

	public double getDblRoundOff() {
		return dblRoundOff;
	}

	public void setDblRoundOff(double dblRoundOff) {
		this.dblRoundOff = dblRoundOff;
	}

	public String getStrAuthorize() {
		return strAuthorize;
	}

	public void setStrAuthorize(String strAuthorize) {
		this.strAuthorize = strAuthorize;
	}

	public String getStrCurrencyCode() {
		return strCurrencyCode;
	}

	public void setStrCurrencyCode(String strCurrencyCode) {
		this.strCurrencyCode =(String) setDefaultValue(strCurrencyCode, ""); ;
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
		this.dblVATClaim = dblVATClaim;
	}

	public double getDblFOB() {
		return dblFOB;
	}

	public void setDblFOB(double dblFOB) {
		this.dblFOB = dblFOB;
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




	
}
