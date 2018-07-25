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

@Entity
@Table(name = "tblreorderlevel")
@IdClass(clsProductReOrderLevelModel_ID.class)
public class clsProductReOrderLevelModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public clsProductReOrderLevelModel() {
	}

	public clsProductReOrderLevelModel(clsProductReOrderLevelModel_ID clsProductReOrderLevelModel) {
		strLocationCode = clsProductReOrderLevelModel.getStrLocationCode();
		strClientCode = clsProductReOrderLevelModel.getStrClientCode();
		strProdCode = clsProductReOrderLevelModel.getStrProdCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strLocationCode", column = @Column(name = "strLocationCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")), @AttributeOverride(name = "strProdCode", column = @Column(name = "strProdCode")) })
	@Column(name = "strLocationCode")
	private String strLocationCode;

	@Column(name = "dblReOrderQty")
	private double dblReOrderQty;

	@Column(name = "dblReOrderLevel")
	private double dblReOrderLevel;

	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "dblPrice")
	private double dblPrice;

	@Transient
	private String strLocationName;

	public String getStrLocationCode() {
		return strLocationCode;
	}

	public void setStrLocationCode(String strLocationCode) {
		this.strLocationCode = strLocationCode;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrLocationName() {
		return strLocationName;
	}

	public void setStrLocationName(String strLocationName) {
		this.strLocationName = strLocationName;
	}

	public double getDblReOrderQty() {
		return dblReOrderQty;
	}

	public void setDblReOrderQty(double dblReOrderQty) {
		this.dblReOrderQty = dblReOrderQty;
	}

	public double getDblReOrderLevel() {
		return dblReOrderLevel;
	}

	public void setDblReOrderLevel(double dblReOrderLevel) {
		this.dblReOrderLevel = dblReOrderLevel;
	}

	public double getDblPrice() {
		return dblPrice;
	}

	public void setDblPrice(double dblPrice) {
		this.dblPrice = dblPrice;
	}
}
