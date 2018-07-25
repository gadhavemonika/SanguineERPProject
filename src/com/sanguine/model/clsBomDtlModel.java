package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblbommasterdtl")
public class clsBomDtlModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@GeneratedValue
	@Id
	@Column(name = "intId")
	private int intId;

	@Column(name = "strBOMCode", columnDefinition = "VARCHAR(10) NOT NULL default ''")
	private String strBOMCode;

	@Column(name = "strChildCode", columnDefinition = "VARCHAR(10) NOT NULL default ''")
	private String strChildCode;

	@Column(name = "dblQty", columnDefinition = "Decimal(18,4) NOT NULL default '0.00'")
	private double dblQty;

	@Column(name = "dblWeight", columnDefinition = "Decimal(18,4) NOT NULL default '0.00'")
	private double dblWeight;

	@Column(name = "intIndex", columnDefinition = "Int(11) default '0'")
	private int intIndex;

	@Transient
	private String strProdName;

	@Column(name = "strUOM", columnDefinition = "VARCHAR(100) NOT NULL default ''")
	private String strUOM;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "dblQtyConversion", columnDefinition = "Decimal(18,4) NOT NULL default '0.00'")
	private double dblQtyConversion;

	public String getStrBOMCode() {
		return strBOMCode;
	}

	public void setStrBOMCode(String strBOMCode) {
		this.strBOMCode = (String) setDefaultValue(strBOMCode, "");
	}

	public String getStrChildCode() {
		return strChildCode;
	}

	public void setStrChildCode(String strChildCode) {
		this.strChildCode = (String) setDefaultValue(strChildCode, "");
	}

	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = (double) setDefaultValue(dblQty, 0.00);
	}

	public double getDblWeight() {
		return dblWeight;
	}

	public void setDblWeight(double dblWeight) {
		this.dblWeight = (double) setDefaultValue(dblWeight, 0.00);
	}

	public int getIntIndex() {
		return intIndex;
	}

	public void setIntIndex(int intIndex) {
		this.intIndex = (int) setDefaultValue(intIndex, 0);
	}

	public int getIntId() {
		return intId;
	}

	public void setIntId(int intId) {
		this.intId = intId;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = (String) setDefaultValue(strClientCode, "");
	}

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = (String) setDefaultValue(strProdName, "");
	}

	public String getStrUOM() {
		return strUOM;
	}

	public void setStrUOM(String strUOM) {
		this.strUOM = (String) setDefaultValue(strUOM, 0.00);
	}

	public double getDblQtyConversion() {
		return dblQtyConversion;
	}

	public void setDblQtyConversion(double dblQtyConversion) {
		this.dblQtyConversion = (double) setDefaultValue(dblQtyConversion, 0.00);
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
}
