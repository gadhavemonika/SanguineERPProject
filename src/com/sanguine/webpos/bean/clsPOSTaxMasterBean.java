package com.sanguine.webpos.bean;

import java.util.List;

public class clsPOSTaxMasterBean {
	// Variable Declaration
	private String strTaxCode;

	private String strTaxType;

	private String strTaxDesc;

	private String strTaxShortName;

	private double dblAmount;

	private String strTaxOnSP;

	private double dblPercent;

	private List<clsPOSMasterBean> listPOSCode;

	private String strTaxOnGD;

	private String strTaxCalculation;

	private String strTaxRounded;

	private String strTaxIndicator;

	private String strTaxOnTax;

	private List<clsSettlementDetailsBean> listSettlementCode;

	private List<clsPOSGroupMasterBean> listGroupCode;

	private String dteValidFrom;

	private String dteValidTo;

	private List<clsPOSTaxMasterBean> listTaxOnTaxCode;

	private String strItemType;

	private String strHomeDelivery;

	private String strDinningInn;

	private String strTakeAway;

	private List<clsPOSAreaMasterBean> listAreaCode;

	private String strAccountCode;

	private Boolean strApplicableYN;

	// Setter-Getter Methods

	public String getStrTaxCode() {
		return strTaxCode;
	}

	public void setStrTaxCode(String strTaxCode) {
		this.strTaxCode = strTaxCode;
	}

	public String getStrTaxType() {
		return strTaxType;
	}

	public void setStrTaxType(String strTaxType) {
		this.strTaxType = strTaxType;
	}

	public String getStrTaxDesc() {
		return strTaxDesc;
	}

	public void setStrTaxDesc(String strTaxDesc) {
		this.strTaxDesc = strTaxDesc;
	}

	public String getStrTaxShortName() {
		return strTaxShortName;
	}

	public void setStrTaxShortName(String strTaxShortName) {
		this.strTaxShortName = strTaxShortName;
	}

	public double getDblAmount() {
		return dblAmount;
	}

	public void setDblAmount(double dblAmount) {
		this.dblAmount = dblAmount;
	}

	public String getStrTaxOnSP() {
		return strTaxOnSP;
	}

	public void setStrTaxOnSP(String strTaxOnSP) {
		this.strTaxOnSP = strTaxOnSP;
	}

	public double getDblPercent() {
		return dblPercent;
	}

	public void setDblPercent(double dblPercent) {
		this.dblPercent = dblPercent;
	}

	public void setListPOSCode(List<clsPOSMasterBean> listPOSCode) {
		this.listPOSCode = listPOSCode;
	}

	public String getStrTaxOnGD() {
		return strTaxOnGD;
	}

	public void setStrTaxOnGD(String strTaxOnGD) {
		this.strTaxOnGD = strTaxOnGD;
	}

	public String getStrTaxCalculation() {
		return strTaxCalculation;
	}

	public void setStrTaxCalculation(String strTaxCalculation) {
		this.strTaxCalculation = strTaxCalculation;
	}

	public String getStrTaxRounded() {
		return strTaxRounded;
	}

	public void setStrTaxRounded(String strTaxRounded) {
		this.strTaxRounded = strTaxRounded;
	}

	public List<clsPOSMasterBean> getListPOSCode() {
		return listPOSCode;
	}

	public String getStrTaxIndicator() {
		return strTaxIndicator;
	}

	public void setStrTaxIndicator(String strTaxIndicator) {
		this.strTaxIndicator = strTaxIndicator;
	}

	public String getStrTaxOnTax() {
		return strTaxOnTax;
	}

	public void setStrTaxOnTax(String strTaxOnTax) {
		this.strTaxOnTax = strTaxOnTax;
	}

	public List<clsSettlementDetailsBean> getListSettlementCode() {
		return listSettlementCode;
	}

	public void setListSettlementCode(List<clsSettlementDetailsBean> listSettlementCode) {
		this.listSettlementCode = listSettlementCode;
	}

	public List<clsPOSGroupMasterBean> getListGroupCode() {
		return listGroupCode;
	}

	public void setListGroupCode(List<clsPOSGroupMasterBean> listGroupCode) {
		this.listGroupCode = listGroupCode;
	}

	public String getDteValidFrom() {
		return dteValidFrom;
	}

	public void setDteValidFrom(String dteValidFrom) {
		this.dteValidFrom = dteValidFrom;
	}

	public String getDteValidTo() {
		return dteValidTo;
	}

	public void setDteValidTo(String dteValidTo) {
		this.dteValidTo = dteValidTo;
	}

	public List<clsPOSTaxMasterBean> getListTaxOnTaxCode() {
		return listTaxOnTaxCode;
	}

	public void setListTaxOnTaxCode(List<clsPOSTaxMasterBean> listTaxOnTaxCode) {
		this.listTaxOnTaxCode = listTaxOnTaxCode;
	}

	public String getStrItemType() {
		return strItemType;
	}

	public void setStrItemType(String strItemType) {
		this.strItemType = strItemType;
	}

	public String getStrHomeDelivery() {
		return strHomeDelivery;
	}

	public void setStrHomeDelivery(String strHomeDelivery) {
		this.strHomeDelivery = strHomeDelivery;
	}

	public String getStrDinningInn() {
		return strDinningInn;
	}

	public void setStrDinningInn(String strDinningInn) {
		this.strDinningInn = strDinningInn;
	}

	public String getStrTakeAway() {
		return strTakeAway;
	}

	public void setStrTakeAway(String strTakeAway) {
		this.strTakeAway = strTakeAway;
	}

	public List<clsPOSAreaMasterBean> getListAreaCode() {
		return listAreaCode;
	}

	public void setListAreaCode(List<clsPOSAreaMasterBean> listAreaCode) {
		this.listAreaCode = listAreaCode;
	}

	public String getStrAccountCode() {
		return strAccountCode;
	}

	public void setStrAccountCode(String strAccountCode) {
		this.strAccountCode = strAccountCode;
	}

	public Boolean getStrApplicableYN() {
		return strApplicableYN;
	}

	public void setStrApplicableYN(Boolean strApplicableYN) {
		this.strApplicableYN = strApplicableYN;
	}

}
