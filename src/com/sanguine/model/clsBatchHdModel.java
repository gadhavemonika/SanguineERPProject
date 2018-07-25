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

@SuppressWarnings("serial")
@Entity
@Table(name = "tblbatchhd")
@IdClass(clsBatchHdModel_ID.class)
public class clsBatchHdModel implements Serializable {

	public clsBatchHdModel() {
	}

	public clsBatchHdModel(clsBatchHdModel_ID clsBatchHdModel_ID) {
		strTransCode = clsBatchHdModel_ID.getStrTransCode();
		strProdCode = clsBatchHdModel_ID.getStrProdCode();
		strClientCode = clsBatchHdModel_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strTransCode", column = @Column(name = "strTransCode")), @AttributeOverride(name = "strProdCode", column = @Column(name = "strProdCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strBatchCode", columnDefinition = "VARCHAR(25) NOT NULL default ''")
	private String strBatchCode;

	@Column(name = "strTransCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strTransCode;

	@Column(name = "strTransType", columnDefinition = "VARCHAR(25) NOT NULL default ''")
	private String strTransType;

	@Column(name = "strManuBatchCode", columnDefinition = "VARCHAR(25) NOT NULL default ''")
	private String strManuBatchCode;

	@Column(name = "strProdCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strProdCode;

	@Column(name = "dblQty", columnDefinition = "DECIMAL(18,4) NULL default '0'")
	private double dblQty;

	@Column(name = "dtExpiryDate", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String dtExpiryDate;

	@Column(name = "intSrNo", nullable = false, updatable = false, columnDefinition = "INT NOT NULL default '0'")
	private long intSrNo;

	@Column(name = "strFromLocCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strFromLocCode;

	@Column(name = "strToLocCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strToLocCode;

	@Column(name = "dblPendingQty", columnDefinition = "DECIMAL(18.4) NOT NULL default '0'")
	private double dblPendingQty;

	@Column(name = "strTransCodeforUpdate", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strTransCodeforUpdate;

	@Column(name = "strPropertyCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strPropertyCode;

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strClientCode;

	@Transient
	private String strProdName;

	public String getStrBatchCode() {
		return strBatchCode;
	}

	public void setStrBatchCode(String strBatchCode) {
		this.strBatchCode = strBatchCode;
	}

	public String getStrTransCode() {
		return strTransCode;
	}

	public void setStrTransCode(String strTransCode) {
		this.strTransCode = strTransCode;
	}

	public String getStrTransType() {
		return strTransType;
	}

	public void setStrTransType(String strTransType) {
		this.strTransType = strTransType;
	}

	public String getStrManuBatchCode() {
		return strManuBatchCode;
	}

	public void setStrManuBatchCode(String strManuBatchCode) {
		this.strManuBatchCode = strManuBatchCode;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = dblQty;
	}

	public String getDtExpiryDate() {
		return dtExpiryDate;
	}

	public void setDtExpiryDate(String dtExpiryDate) {
		this.dtExpiryDate = dtExpiryDate;
	}

	public long getIntSrNo() {
		return intSrNo;
	}

	public void setIntSrNo(long intSrNo) {
		this.intSrNo = intSrNo;
	}

	public String getStrFromLocCode() {
		return strFromLocCode;
	}

	public void setStrFromLocCode(String strFromLocCode) {
		this.strFromLocCode = strFromLocCode;
	}

	public String getStrToLocCode() {
		return strToLocCode;
	}

	public void setStrToLocCode(String strToLocCode) {
		this.strToLocCode = strToLocCode;
	}

	public double getDblPendingQty() {
		return dblPendingQty;
	}

	public void setDblPendingQty(double dblPendingQty) {
		this.dblPendingQty = dblPendingQty;
	}

	public String getStrTransCodeforUpdate() {
		return strTransCodeforUpdate;
	}

	public void setStrTransCodeforUpdate(String strTransCodeforUpdate) {
		this.strTransCodeforUpdate = strTransCodeforUpdate;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

}
