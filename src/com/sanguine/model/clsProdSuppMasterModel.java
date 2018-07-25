package com.sanguine.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblprodsuppmaster")
@IdClass(clsProdSuppMasterModel_ID.class)
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class clsProdSuppMasterModel {
	private String strSuppCode, strProdCode, strSuppUOM, dtLastDate, strLeadTime, strDefault, strSuppPartNo, strSuppPartDesc, strClientCode;
	private double dblLastCost, dblMaxQty, dblMargin, dblStandingOrder;
	@Transient
	private String strSuppName;

	private String strProdName;

	public clsProdSuppMasterModel() {
	}

	public clsProdSuppMasterModel(clsProdSuppMasterModel_ID clsProdSuppMasterModel_ID) {
		this.strSuppCode = clsProdSuppMasterModel_ID.getStrSuppCode();
		this.strProdCode = clsProdSuppMasterModel_ID.getStrProdCode();
		this.strClientCode = clsProdSuppMasterModel_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strSuppCode", column = @Column(name = "strSuppCode")), @AttributeOverride(name = "strProdCode", column = @Column(name = "strProdCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	/*
	 * @Column(name = "intId") private long intId;
	 */
	@Column(name = "strSuppCode")
	public String getStrSuppCode() {
		return strSuppCode;
	}

	public void setStrSuppCode(String strSuppCode) {
		this.strSuppCode = strSuppCode;
	}

	@Column(name = "strProdCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	@Column(name = "strSuppUOM", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrSuppUOM() {
		return strSuppUOM;
	}

	public void setStrSuppUOM(String strSuppUOM) {
		this.strSuppUOM = strSuppUOM;
	}

	@Column(name = "dtLastDate", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getDtLastDate() {
		return dtLastDate;
	}

	public void setDtLastDate(String dtLastDate) {
		this.dtLastDate = dtLastDate;
	}

	@Column(name = "strLeadTime", columnDefinition = "VARCHAR(50) default '0'")
	public String getStrLeadTime() {
		return strLeadTime;
	}

	public void setStrLeadTime(String strLeadTime) {
		this.strLeadTime = strLeadTime;
	}

	@Column(name = "strDefault", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrDefault() {
		return strDefault;
	}

	public void setStrDefault(String strDefault) {
		this.strDefault = strDefault;
	}

	@Column(name = "strSuppPartNo", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrSuppPartNo() {
		return strSuppPartNo;
	}

	public void setStrSuppPartNo(String strSuppPartNo) {
		this.strSuppPartNo = strSuppPartNo;
	}

	@Column(name = "strSuppPartDesc", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrSuppPartDesc() {
		return strSuppPartDesc;
	}

	public void setStrSuppPartDesc(String strSuppPartDesc) {
		this.strSuppPartDesc = strSuppPartDesc;
	}

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Column(name = "dblLastCost", columnDefinition = "DECIMAL(18,4) NOT NULL dafault '0.0000'")
	public double getDblLastCost() {
		return dblLastCost;
	}

	public void setDblLastCost(double dblLastCost) {
		this.dblLastCost = dblLastCost;
	}

	@Column(name = "dblMaxQty", columnDefinition = "DECIMAL(18,4) NOT NULL dafault '0.0000'")
	public double getDblMaxQty() {
		return dblMaxQty;
	}

	public void setDblMaxQty(double dblMaxQty) {
		this.dblMaxQty = dblMaxQty;
	}

	public String getStrSuppName() {
		return strSuppName;
	}

	public void setStrSuppName(String strSuppName) {
		this.strSuppName = strSuppName;
	}

	@Column(name = "dblMargin", columnDefinition = "DECIMAL(18,2) NOT NULL dafault '0.0000'")
	public double getDblMargin() {
		return dblMargin;
	}

	public void setDblMargin(double dblMargin) {
		this.dblMargin = dblMargin;
	}

	@Transient
	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	public double getDblStandingOrder() {
		return dblStandingOrder;
	}

	public void setDblStandingOrder(double dblStandingOrder) {
		this.dblStandingOrder = dblStandingOrder;
	}

}
