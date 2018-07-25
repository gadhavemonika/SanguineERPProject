package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "tblexciserecipermasterdtl")
public class clsExciseRecipeMasterDtlModel implements Serializable {

	public clsExciseRecipeMasterDtlModel() {
	}

	// Variable Declaration
	@Id
	@Column(name = "intId")
	private long intId;

	@Column(name = "strRecipeCode")
	private String strRecipeCode;

	@Column(name = "strBrandCode")
	private String strBrandCode;

	@Transient
	private String strBrandName;

	@Column(name = "dblQty")
	private double dblQty;

	// @Column(name="strUOM")
	// private String strUOM;

	@Column(name = "strClientCode")
	private String strClientCode;

	// Setter-Getter Methods
	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = (Long) setDefaultValue(intId, "NA");
	}

	public String getStrRecipeCode() {
		return strRecipeCode;
	}

	public void setStrRecipeCode(String strRecipeCode) {
		this.strRecipeCode = (String) setDefaultValue(strRecipeCode, "NA");
	}

	public String getStrBrandCode() {
		return strBrandCode;
	}

	public void setStrBrandCode(String strBrandCode) {
		this.strBrandCode = strBrandCode;
	}

	public String getStrBrandName() {
		return strBrandName;
	}

	public void setStrBrandName(String strBrandName) {
		this.strBrandName = strBrandName;
	}

	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = (Double) setDefaultValue(dblQty, "NA");
	}

	// public String getStrUOM(){
	// return strUOM;
	// }
	// public void setStrUOM(String strUOM){
	// this. strUOM = (String) setDefaultValue( strUOM, "NA");
	// }

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = (String) setDefaultValue(strClientCode, "NA");
	}

	// Function to Set Default Values
	private Object setDefaultValue(Object value, Object defaultValue) {
		if (value != null && (value instanceof String && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Double && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Integer && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Long && value.toString().length() > 0)) {
			return value;
		} else {
			return defaultValue;
		}
	}

}
