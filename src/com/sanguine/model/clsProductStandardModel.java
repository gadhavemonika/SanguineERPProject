package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblproductstandard")
@IdClass(clsProductStandard_ID.class)
public class clsProductStandardModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsProductStandardModel() {
	}

	public clsProductStandardModel(clsProductStandard_ID clsProductStandard_ID) {
		strLocCode = clsProductStandard_ID.getStrLocCode();
		strPropertyCode = clsProductStandard_ID.getStrPropertyCode();
		strClientCode = clsProductStandard_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strProdCode", column = @Column(name = "strProdCode")), @AttributeOverride(name = "strLocCode", column = @Column(name = "strLocCode")), @AttributeOverride(name = "strPropertyCode", column = @Column(name = "strPropertyCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "strRemarks")
	private String strRemarks;

	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "dblQty")
	private double dblQty;

	@Column(name = "dblUnitPrice")
	private double dblUnitPrice;

	@Column(name = "dblTotalPrice")
	private double dblTotalPrice;

	@Column(name = "strStandardType")
	private String strStandardType;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strLocCode")
	private String strLocCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}

	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = dblQty;
	}

	public double getDblUnitPrice() {
		return dblUnitPrice;
	}

	public void setDblUnitPrice(double dblUnitPrice) {
		this.dblUnitPrice = dblUnitPrice;
	}

	public double getDblTotalPrice() {
		return dblTotalPrice;
	}

	public void setDblTotalPrice(double dblTotalPrice) {
		this.dblTotalPrice = dblTotalPrice;
	}

	public String getStrStandardType() {
		return strStandardType;
	}

	public void setStrStandardType(String strStandardType) {
		this.strStandardType = strStandardType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
