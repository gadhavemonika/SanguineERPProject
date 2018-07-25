package com.sanguine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblstocktransferdtl")
public class clsStkTransferDtlModel {
	@GeneratedValue
	@Id
	@Column(name = "intId")
	private long intId;

	@Column(name = "strSTCode")
	private String strSTCode;

	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "dblQty", columnDefinition = "Decimal(18,4) NOT NULL default '0.00'")
	private double dblQty;

	@Column(name = "dblWeight", columnDefinition = "Decimal(18,4) NOT NULL default '0.00'")
	private double dblWeight;

	@Column(name = "dblPrice", columnDefinition = "Decimal(18,4) NOT NULL default '0.00'")
	private double dblPrice;

	@Column(name = "dblTotalPrice", columnDefinition = "Decimal(18,4) NOT NULL default '0.00'")
	private double dblTotalPrice;

	@Column(name = "strProdChar")
	private String strProdChar;

	@Column(name = "intProdIndex")
	private int intProdIndex;

	@Column(name = "strRemark")
	private String strRemark;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Transient
	private String strUOM;

	@Transient
	private String strProdName;

	@Transient
	private String strProdType;

	@Transient
	private String strPOSItemCode;

	@Transient
	private double dblTotalWt;

	@Transient
	private String dtSTDate;

	@Transient
	private String strMaterialIssue;

	@Transient
	private String strAuthorise;

	@Transient
	private String strAgainst;

	@Transient
	private String strFromLoc;

	@Transient
	private String strToLoc;

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrSTCode() {
		return strSTCode;
	}

	public void setStrSTCode(String strSTCode) {
		this.strSTCode = strSTCode;
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

	public int getIntProdIndex() {
		return intProdIndex;
	}

	public void setIntProdIndex(int intProdIndex) {
		this.intProdIndex = intProdIndex;
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

	public String getDtSTDate() {
		return dtSTDate;
	}

	public void setDtSTDate(String dtSTDate) {
		this.dtSTDate = dtSTDate;
	}

	public String getStrMaterialIssue() {
		return strMaterialIssue;
	}

	public void setStrMaterialIssue(String strMaterialIssue) {
		this.strMaterialIssue = strMaterialIssue;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	public String getStrFromLoc() {
		return strFromLoc;
	}

	public void setStrFromLoc(String strFromLoc) {
		this.strFromLoc = strFromLoc;
	}

	public String getStrToLoc() {
		return strToLoc;
	}

	public void setStrToLoc(String strToLoc) {
		this.strToLoc = strToLoc;
	}

	public double getDblTotalPrice() {
		return dblTotalPrice;
	}

	public void setDblTotalPrice(double dblTotalPrice) {
		this.dblTotalPrice = dblTotalPrice;
	}

}
