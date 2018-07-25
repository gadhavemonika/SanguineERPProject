package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblexciselocationmaster")
@IdClass(clsExciseLocationMasterModel_ID.class)
public class clsExciseLocationMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsExciseLocationMasterModel() {
	}

	public clsExciseLocationMasterModel(clsExciseLocationMasterModel_ID objModelID) {
		strReceivable = objModelID.getStrLocCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strReceivable", column = @Column(name = "strReceivable")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strLocCode")
	private String strLocCode;

	@Column(name = "intId")
	private long intId;

	@Column(name = "strLocName")
	private String strLocName;

	@Column(name = "strLocDesc")
	private String strLocDesc;

	@Column(name = "strAvlForSale")
	private String strAvlForSale;

	@Column(name = "strActive")
	private String strActive;

	@Column(name = "strPickable")
	private String strPickable;

	@Column(name = "strReceivable")
	private String strReceivable;

	@Column(name = "strExciseNo")
	private String strExciseNo;

	@Column(name = "strType")
	private String strType;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "strMonthEnd")
	private String strMonthEnd;

	@Column(name = "strLocPropertyCode")
	private String strLocPropertyCode;

	@Column(name = "strExternalCode")
	private String strExternalCode;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "dteDateCreated")
	private String dteDateCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteLastModified")
	private String dteDateEdited;

	@Column(name = "strClientCode")
	private String strClientCode;

	// Setter-Getter Methods
	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = (String) setDefaultValue(strLocCode, "NA");
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = (Long) setDefaultValue(intId, "NA");
	}

	public String getStrLocName() {
		return strLocName;
	}

	public void setStrLocName(String strLocName) {
		this.strLocName = (String) setDefaultValue(strLocName, "NA");
	}

	public String getStrLocDesc() {
		return strLocDesc;
	}

	public void setStrLocDesc(String strLocDesc) {
		this.strLocDesc = (String) setDefaultValue(strLocDesc, "NA");
	}

	public String getStrAvlForSale() {
		return strAvlForSale;
	}

	public void setStrAvlForSale(String strAvlForSale) {
		this.strAvlForSale = (String) setDefaultValue(strAvlForSale, "NA");
	}

	public String getStrActive() {
		return strActive;
	}

	public void setStrActive(String strActive) {
		this.strActive = (String) setDefaultValue(strActive, "NA");
	}

	public String getStrPickable() {
		return strPickable;
	}

	public void setStrPickable(String strPickable) {
		this.strPickable = (String) setDefaultValue(strPickable, "NA");
	}

	public String getStrReceivable() {
		return strReceivable;
	}

	public void setStrReceivable(String strReceivable) {
		this.strReceivable = (String) setDefaultValue(strReceivable, "NA");
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = (String) setDefaultValue(strUserCreated, "NA");
	}

	public String getStrExciseNo() {
		return strExciseNo;
	}

	public void setStrExciseNo(String strExciseNo) {
		this.strExciseNo = (String) setDefaultValue(strExciseNo, "NA");
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = (String) setDefaultValue(strType, "NA");
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = (String) setDefaultValue(strPropertyCode, "NA");
	}

	public String getStrMonthEnd() {
		return strMonthEnd;
	}

	public void setStrMonthEnd(String strMonthEnd) {
		this.strMonthEnd = (String) setDefaultValue(strMonthEnd, "NA");
	}

	public String getStrExternalCode() {
		return strExternalCode;
	}

	public void setStrExternalCode(String strExternalCode) {
		this.strExternalCode = (String) setDefaultValue(strExternalCode, "NA");
	}

	public String getStrLocPropertyCode() {
		return strLocPropertyCode;
	}

	public void setStrLocPropertyCode(String strLocPropertyCode) {
		this.strLocPropertyCode = (String) setDefaultValue(strLocPropertyCode, "NA");
	}

	public String getDteDateCreated() {
		return dteDateCreated;
	}

	public void setDteDateCreated(String dteDateCreated) {
		this.dteDateCreated = dteDateCreated;
	}

	public String getDteDateEdited() {
		return dteDateEdited;
	}

	public void setDteDateEdited(String dteDateEdited) {
		this.dteDateEdited = dteDateEdited;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = (String) setDefaultValue(strUserModified, "NA");
	}

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
