package com.sanguine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblstockadjustmentdtl")
public class clsStkAdjustmentDtlModel {
	@GeneratedValue
	@Id
	@Column(name = "intId")
	private long intId;

	@Column(name = "strSACode")
	private String strSACode;

	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "strProdChar")
	private String strProdChar;

	@Column(name = "strRemark")
	private String strRemark;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "dblQty", columnDefinition = "DECIMAL(18,4) NOT NULL default '0'")
	private double dblQty;

	@Column(name = "strDisplayQty", columnDefinition = "VARCHAR(20) NOT NULL default ''")
	private String strDisplayQty;

	@Column(name = "dblPrice", columnDefinition = "DECIMAL(18,4) NOT NULL default '0'")
	private double dblPrice;

	@Column(name = "dblWeight")
	private double dblWeight;

	@Column(name = "strType")
	private String strType;

	@Column(name = "intIndex")
	private int intIndex;

	@Column(name = "dblRate", columnDefinition = "DECIMAL(18,4) NOT NULL default '0'")
	private double dblRate;

	@Transient
	private String strUOM;

	@Transient
	private String strProdName;

	@Transient
	private String strProdType;

	@Transient
	private double dblTotalWt;

	@Transient
	private String strPOSItemCode;

	@Transient
	private String strParentCode;

	@Transient
	private String strParentName;

	@Transient
	private double dblParentQty;

	@Transient
	private String strBOMCode;

	@Transient
	private String strRecipeUOM;

	@Transient
	private double dblRecipeConversion;

	@Transient
	private double dblParentRate;

	@Transient
	private String strPartNo;

	@Column(name = "strWSLinkedProdCode", columnDefinition = "VARCHAR(20) NOT NULL default ''")
	private String strWSLinkedProdCode;

	@Transient
	private double dblStandardPrice;

	@Transient
	private double dblStandardAmt;
	
	@Transient
	private double dblParentDiscountedAmt;

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrSACode() {
		return strSACode;
	}

	public void setStrSACode(String strSACode) {
		this.strSACode = strSACode;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrProdChar() {
		return strProdChar;
	}

	public void setStrProdChar(String strProdChar) {
		this.strProdChar = strProdChar;
	}

	public String getStrRemark() {
		return strRemark;
	}

	public void setStrRemark(String strRemark) {
		this.strRemark = strRemark;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = dblQty;
	}

	public double getDblPrice() {
		return dblPrice;
	}

	public void setDblPrice(double dblPrice) {
		this.dblPrice = dblPrice;
	}

	public double getDblWeight() {
		return dblWeight;
	}

	public void setDblWeight(double dblWeight) {
		this.dblWeight = dblWeight;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public int getIntIndex() {
		return intIndex;
	}

	public void setIntIndex(int intIndex) {
		this.intIndex = intIndex;
	}

	public String getStrUOM() {
		return strUOM;
	}

	public void setStrUOM(String strUOM) {
		this.strUOM = strUOM;
	}

	public String getStrProdType() {
		return strProdType;
	}

	public void setStrProdType(String strProdType) {
		this.strProdType = strProdType;
	}

	public double getDblTotalWt() {
		return dblTotalWt;
	}

	public void setDblTotalWt(double dblTotalWt) {
		this.dblTotalWt = dblTotalWt;
	}

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	public String getStrPOSItemCode() {
		return strPOSItemCode;
	}

	public void setStrPOSItemCode(String strPOSItemCode) {
		this.strPOSItemCode = strPOSItemCode;
	}

	public double getDblRate() {
		return dblRate;
	}

	public void setDblRate(double dblRate) {
		this.dblRate = dblRate;
	}

	public String getStrDisplayQty() {
		return strDisplayQty;
	}

	public void setStrDisplayQty(String strDisplayQty) {
		this.strDisplayQty = strDisplayQty;
	}

	public String getStrParentCode() {
		return strParentCode;
	}

	public void setStrParentCode(String strParentCode) {
		this.strParentCode = strParentCode;
	}

	public String getStrParentName() {
		return strParentName;
	}

	public void setStrParentName(String strParentName) {
		this.strParentName = strParentName;
	}

	public double getDblParentQty() {
		return dblParentQty;
	}

	public void setDblParentQty(double dblParentQty) {
		this.dblParentQty = dblParentQty;
	}

	public String getStrBOMCode() {
		return strBOMCode;
	}

	public void setStrBOMCode(String strBOMCode) {
		this.strBOMCode = strBOMCode;
	}

	public double getDblRecipeConversion() {
		return dblRecipeConversion;
	}

	public void setDblRecipeConversion(double dblRecipeConversion) {
		this.dblRecipeConversion = dblRecipeConversion;
	}

	public String getStrRecipeUOM() {
		return strRecipeUOM;
	}

	public void setStrRecipeUOM(String strRecipeUOM) {
		this.strRecipeUOM = strRecipeUOM;
	}

	public double getDblParentRate() {
		return dblParentRate;
	}

	public void setDblParentRate(double dblParentRate) {
		this.dblParentRate = dblParentRate;
	}

	public String getStrPartNo() {
		return strPartNo;
	}

	public void setStrPartNo(String strPartNo) {
		this.strPartNo = strPartNo;
	}

	public String getStrWSLinkedProdCode() {
		return strWSLinkedProdCode;
	}

	public void setStrWSLinkedProdCode(String strWSLinkedProdCode) {
		this.strWSLinkedProdCode = (String) setDefaultValue(strWSLinkedProdCode, "");
	}

	private Object setDefaultValue(Object value, Object defaultValue) {
		if (value != null && (value instanceof String && value.toString().length() > 0)) {
			return value;
		} else if (value != null && !(value instanceof String)) {
			return value;

		} else {
			return defaultValue;
		}
	}

	public double getDblStandardPrice() {
		return dblStandardPrice;
	}

	public void setDblStandardPrice(double dblStandardPrice) {
		this.dblStandardPrice = dblStandardPrice;
	}

	public double getDblStandardAmt() {
		return dblStandardAmt;
	}

	public void setDblStandardAmt(double dblStandardAmt) {
		this.dblStandardAmt = dblStandardAmt;
	}

	public double getDblParentDiscountedAmt() {
		return dblParentDiscountedAmt;
	}

	public void setDblParentDiscountedAmt(double dblParentDiscountedAmt) {
		this.dblParentDiscountedAmt = dblParentDiscountedAmt;
	}

	@Override
	public String toString() {
		return "clsStkAdjustmentDtlModel [intId=" + intId + ", strSACode="
				+ strSACode + ", strProdCode=" + strProdCode + ", strProdChar="
				+ strProdChar + ", strRemark=" + strRemark + ", strClientCode="
				+ strClientCode + ", dblQty=" + dblQty + ", strDisplayQty="
				+ strDisplayQty + ", dblPrice=" + dblPrice + ", dblWeight="
				+ dblWeight + ", strType=" + strType + ", intIndex=" + intIndex
				+ ", dblRate=" + dblRate + ", strUOM=" + strUOM
				+ ", strProdName=" + strProdName + ", strProdType="
				+ strProdType + ", dblTotalWt=" + dblTotalWt
				+ ", strPOSItemCode=" + strPOSItemCode + ", strParentCode="
				+ strParentCode + ", strParentName=" + strParentName
				+ ", dblParentQty=" + dblParentQty + ", strBOMCode="
				+ strBOMCode + ", strRecipeUOM=" + strRecipeUOM
				+ ", dblRecipeConversion=" + dblRecipeConversion
				+ ", dblParentRate=" + dblParentRate + ", strPartNo="
				+ strPartNo + ", strWSLinkedProdCode=" + strWSLinkedProdCode
				+ ", dblStandardPrice=" + dblStandardPrice
				+ ", dblStandardAmt=" + dblStandardAmt
				+ ", dblParentDiscountedAmt=" + dblParentDiscountedAmt + "]";
	}

	

}
