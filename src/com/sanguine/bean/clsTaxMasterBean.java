package com.sanguine.bean;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.sanguine.model.clsSettlementMasterModel;
import com.sanguine.model.clsTaxSubGroupDtl;

public class clsTaxMasterBean {
	private String strTaxCode;
	@NotEmpty
	private String strTaxDesc;
	private String strTaxOnSP;
	private String strTaxType;
	private String strTaxOnGD;
	@NotEmpty
	private String dtValidFrom;
	@NotEmpty
	private String dtValidTo;
	private String strTaxRounded;
	private String strTaxOnTax;
	private String strTaxIndicator;
	private String strClientCode;
	private String strTaxCalculation;
	private String strPropertyCode;
	private String strTaxOnST;
	private String strExcisable;
	private String strApplOn;
	private String strPartyIndicator;
	private double dblPercent;
	private double dblAmount;
	private String strTaxOnTaxCode;
	private String strTaxOnSubGroup;
	private String strCalTaxOnMRP;
	private double dblAbatement;
	private String strTOTOnMRPItems;
	private String strShortName;
	private String strGSTNo;
	private String strNotApplicableForComesa;
	private String strTaxReversal;

	private List<clsSettlementMasterModel> listSettlement;

	private List<clsTaxSubGroupDtl> listTaxSGDtl;

	private String strExternalCode;

	public String getStrTaxCode() {
		return strTaxCode;
	}

	public void setStrTaxCode(String strTaxCode) {
		this.strTaxCode = strTaxCode;
	}

	public String getStrTaxDesc() {
		return strTaxDesc;
	}

	public void setStrTaxDesc(String strTaxDesc) {
		this.strTaxDesc = strTaxDesc;
	}

	public String getStrTaxOnSP() {
		return strTaxOnSP;
	}

	public void setStrTaxOnSP(String strTaxOnSP) {
		this.strTaxOnSP = strTaxOnSP;
	}

	public String getStrTaxType() {
		return strTaxType;
	}

	public void setStrTaxType(String strTaxType) {
		this.strTaxType = strTaxType;
	}

	public String getStrTaxOnGD() {
		return strTaxOnGD;
	}

	public void setStrTaxOnGD(String strTaxOnGD) {
		this.strTaxOnGD = strTaxOnGD;
	}

	public String getDtValidFrom() {
		return dtValidFrom;
	}

	public void setDtValidFrom(String dtValidFrom) {
		this.dtValidFrom = dtValidFrom;
	}

	public String getDtValidTo() {
		return dtValidTo;
	}

	public void setDtValidTo(String dtValidTo) {
		this.dtValidTo = dtValidTo;
	}

	public String getStrTaxRounded() {
		return strTaxRounded;
	}

	public void setStrTaxRounded(String strTaxRounded) {
		this.strTaxRounded = strTaxRounded;
	}

	public String getStrTaxOnTax() {
		return strTaxOnTax;
	}

	public void setStrTaxOnTax(String strTaxOnTax) {
		this.strTaxOnTax = strTaxOnTax;
	}

	public String getStrTaxIndicator() {
		return strTaxIndicator;
	}

	public void setStrTaxIndicator(String strTaxIndicator) {
		this.strTaxIndicator = strTaxIndicator;
	}

	public String getStrTaxCalculation() {
		return strTaxCalculation;
	}

	public void setStrTaxCalculation(String strTaxCalculation) {
		this.strTaxCalculation = strTaxCalculation;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrTaxOnST() {
		return strTaxOnST;
	}

	public void setStrTaxOnST(String strTaxOnST) {
		this.strTaxOnST = strTaxOnST;
	}

	public String getStrExcisable() {
		return strExcisable;
	}

	public void setStrExcisable(String strExcisable) {
		this.strExcisable = strExcisable;
	}

	public String getStrApplOn() {
		return strApplOn;
	}

	public void setStrApplOn(String strApplOn) {
		this.strApplOn = strApplOn;
	}

	public String getStrPartyIndicator() {
		return strPartyIndicator;
	}

	public void setStrPartyIndicator(String strPartyIndicator) {
		this.strPartyIndicator = strPartyIndicator;
	}

	public double getDblPercent() {
		return dblPercent;
	}

	public void setDblPercent(double dblPercent) {
		this.dblPercent = dblPercent;
	}

	public double getDblAmount() {
		return dblAmount;
	}

	public void setDblAmount(double dblAmount) {
		this.dblAmount = dblAmount;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public List<clsSettlementMasterModel> getListSettlement() {
		return listSettlement;
	}

	public void setListSettlement(List<clsSettlementMasterModel> listSettlement) {
		this.listSettlement = listSettlement;
	}

	public String getStrTaxOnTaxCode() {
		return strTaxOnTaxCode;
	}

	public void setStrTaxOnTaxCode(String strTaxOnTaxCode) {
		this.strTaxOnTaxCode = strTaxOnTaxCode;
	}

	public String getStrTaxOnSubGroup() {
		return strTaxOnSubGroup;
	}

	public void setStrTaxOnSubGroup(String strTaxOnSubGroup) {
		this.strTaxOnSubGroup = strTaxOnSubGroup;
	}

	public List<clsTaxSubGroupDtl> getListTaxSGDtl() {
		return listTaxSGDtl;
	}

	public void setListTaxSGDtl(List<clsTaxSubGroupDtl> listTaxSGDtl) {
		this.listTaxSGDtl = listTaxSGDtl;
	}

	public String getStrCalTaxOnMRP() {
		return strCalTaxOnMRP;
	}

	public void setStrCalTaxOnMRP(String strCalTaxOnMRP) {
		this.strCalTaxOnMRP = strCalTaxOnMRP;
	}

	public double getDblAbatement() {
		return dblAbatement;
	}

	public void setDblAbatement(double dblAbatement) {
		this.dblAbatement = dblAbatement;
	}

	public String getStrTOTOnMRPItems() {
		return strTOTOnMRPItems;
	}

	public void setStrTOTOnMRPItems(String strTOTOnMRPItems) {
		this.strTOTOnMRPItems = strTOTOnMRPItems;
	}

	public String getStrShortName() {
		return strShortName;
	}

	public void setStrShortName(String strShortName) {
		this.strShortName = strShortName;
	}

	public String getStrGSTNo() {
		return strGSTNo;
	}

	public void setStrGSTNo(String strGSTNo) {
		this.strGSTNo = strGSTNo;
	}

	public String getStrExternalCode() {
		return strExternalCode;
	}

	public void setStrExternalCode(String strExternalCode) {
		this.strExternalCode = strExternalCode;
	}

	public String getStrNotApplicableForComesa() {
		return strNotApplicableForComesa;
	}

	public void setStrNotApplicableForComesa(String strNotApplicableForComesa) {
		this.strNotApplicableForComesa = strNotApplicableForComesa;
	}

	public String getStrTaxReversal() {
		return strTaxReversal;
	}

	public void setStrTaxReversal(String strTaxReversal) {
		this.strTaxReversal = strTaxReversal;
	}	
}
