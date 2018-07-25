package com.sanguine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionOfElements;

import com.sanguine.base.model.clsBaseModel;

@Entity
@Table(name = "tbltaxhd")
@IdClass(clsTaxHdModel_ID.class)
public class clsTaxHdModel extends clsBaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsTaxHdModel() {
	}

	public clsTaxHdModel(clsTaxHdModel_ID clsTaxHdModel_ID) {
		strTaxCode = clsTaxHdModel_ID.getStrTaxCode();
		strClientCode = clsTaxHdModel_ID.getStrClientCode();
	}

	@CollectionOfElements(fetch = FetchType.LAZY)
	@JoinTable(name = "tbltaxsubgroupdtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strTaxCode") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strTaxCode", column = @Column(name = "strTaxCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	List<clsTaxSubGroupDtl> listTaxSGDtl = new ArrayList<clsTaxSubGroupDtl>();

	@Column(name = "strTaxCode")
	private String strTaxCode;

	@Column(name = "strTaxDesc")
	private String strTaxDesc;

	@Column(name = "strTaxOnSP")
	private String strTaxOnSP;

	@Column(name = "strTaxType")
	private String strTaxType;

	@Column(name = "strTaxOnGD")
	private String strTaxOnGD;

	@Column(name = "dtValidFrom")
	private String dtValidFrom;

	@Column(name = "dtValidTo")
	private String dtValidTo;

	@Column(name = "strTaxRounded")
	private String strTaxRounded;

	@Column(name = "strTaxOnTax")
	private String strTaxOnTax;

	@Column(name = "strTaxIndicator")
	private String strTaxIndicator;

	@Column(name = "strClientCode", nullable = false, updatable = false)
	private String strClientCode;

	@Column(name = "strTaxCalculation")
	private String strTaxCalculation;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "strTaxOnST")
	private String strTaxOnST;

	@Column(name = "strExcisable")
	private String strExcisable;

	@Column(name = "strApplOn")
	private String strApplOn;

	@Column(name = "strPartyIndicator")
	private String strPartyIndicator;

	@Column(name = "intId", nullable = false, updatable = false)
	private long intId;

	@Column(name = "dblPercent")
	private double dblPercent;

	@Column(name = "dblAmount")
	private double dblAmount;

	@Column(name = "strUserCreated", nullable = false, updatable = false)
	private String strUserCreated;

	@Column(name = "dtCreatedDate", nullable = false, updatable = false)
	private String dtCreatedDate;

	@Column(name = "dtLastModified")
	private String dtLastModified;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "strTaxOnTaxCode")
	private String strTaxOnTaxCode;

	@Column(name = "strTaxOnSubGroup")
	private String strTaxOnSubGroup;

	@Column(name = "strCalTaxOnMRP")
	private String strCalTaxOnMRP;

	@Column(name = "dblAbatement")
	private double dblAbatement;

	@Column(name = "strTOTOnMRPItems")
	private String strTOTOnMRPItems;

	@Column(name = "strShortName")
	private String strShortName;

	@Column(name = "strGSTNo")
	private String strGSTNo;

	@Column(name = "strExternalCode")
	private String strExternalCode;

	@Column(name = "strNotApplicableForComesa")
	private String strNotApplicableForComesa;
	
	@Column(name = "strTaxReversal")
	private String strTaxReversal;
	

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

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
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

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
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

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
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
		this.strNotApplicableForComesa = (String) setDefaultValue(strNotApplicableForComesa, "N");
	}

	public String getStrTaxReversal() {
		return strTaxReversal;
	}

	public void setStrTaxReversal(String strTaxReversal) {
		this.strTaxReversal = (String) setDefaultValue(strTaxReversal, "N");
	}
}
