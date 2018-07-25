package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblproductionorderdtl")
public class clsProductionOrderDtlModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long intId;
	private String strOPCode;
	private String strProdCode;
	private String strProdName;
	private double dblQty;
	private double dblUnitPrice;
	private double dblWeight;
	private String strProdChar;
	private String strSpCode;
	private int intIndex;
	private String strClientCode;

	@Transient
	private String strChecked;

	@Transient
	private double dblstock;

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Column(name = "intId")
	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	@Column(name = "strOPCode")
	public String getStrOPCode() {
		return strOPCode;
	}

	public void setStrOPCode(String strOPCode) {
		this.strOPCode = strOPCode;
	}

	@Column(name = "strProdCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	@Transient
	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	@Column(name = "dblQty", columnDefinition = "DECIMAL(18,4) NOT NULL dafault '0.0000'")
	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = dblQty;
	}

	@Column(name = "dblUnitPrice", columnDefinition = "DECIMAL(18,4) NOT NULL dafault '0.0000'")
	public double getDblUnitPrice() {
		return dblUnitPrice;
	}

	public void setDblUnitPrice(double dblUnitPrice) {
		this.dblUnitPrice = dblUnitPrice;
	}

	@Column(name = "dblWeight", columnDefinition = "DECIMAL(18,4) NOT NULL dafault '0.0000'")
	public double getDblWeight() {
		return dblWeight;
	}

	public void setDblWeight(double dblWeight) {
		this.dblWeight = dblWeight;
	}

	@Column(name = "strProdChar", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrProdChar() {
		return strProdChar;
	}

	public void setStrProdChar(String strProdChar) {
		this.strProdChar = strProdChar;
	}

	@Column(name = "strSpCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrSpCode() {
		return strSpCode;
	}

	public void setStrSpCode(String strSpCode) {
		this.strSpCode = strSpCode;
	}

	@Column(name = "intIndex")
	public int getIntIndex() {
		return intIndex;
	}

	public void setIntIndex(int intIndex) {
		this.intIndex = intIndex;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStrChecked() {
		return strChecked;
	}

	public void setStrChecked(String strChecked) {
		this.strChecked = strChecked;
	}

	public double getDblstock() {
		return dblstock;
	}

	public void setDblstock(double dblstock) {
		this.dblstock = dblstock;
	}

}
