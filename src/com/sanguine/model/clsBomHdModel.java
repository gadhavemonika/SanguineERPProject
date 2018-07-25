package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblbommasterhd")
public class clsBomHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "strBOMCode")
	private String strBOMCode;

	@Column(name = "strParentCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strParentCode;

	@Transient
	private String strParentName;

	@Column(name = "strProcessCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strProcessCode;

	@Column(name = "dtValidFrom", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtValidFrom;

	@Column(name = "dtValidTo", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtValidTo;

	@Column(name = "strUserCreated", nullable = false, updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserCreated;

	@Column(name = "dtCreatedDate", nullable = false, updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtCreatedDate;

	@Column(name = "strUserModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserModified;

	@Column(name = "dtLastModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtLastModified;

	@Column(name = "strClientCode", nullable = false, updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strClientCode;

	@Column(name = "intId", nullable = false, updatable = false)
	private Long intId;

	@Column(name = "strUOM", columnDefinition = "VARCHAR(10) NOT NULL default ''")
	private String strUOM;

	@Column(name = "dblQty", columnDefinition = "Decimal(18,4) NOT NULL default '0.00'")
	private double dblQty;

	@Column(name = "strMethod", nullable = true, columnDefinition = "VARCHAR(100)  default ''")
	private String strMethod;

	@Column(name = "strBOMType")
	private String strBOMType;

	public String getStrBOMCode() {
		return strBOMCode;
	}

	public void setStrBOMCode(String strBOMCode) {
		this.strBOMCode = strBOMCode;
	}

	public String getStrParentCode() {
		return strParentCode;
	}

	public void setStrParentCode(String strParentCode) {
		this.strParentCode = strParentCode;
	}

	public String getStrProcessCode() {
		return strProcessCode;
	}

	public void setStrProcessCode(String strProcessCode) {
		this.strProcessCode = strProcessCode;
	}

	public String getDtValidFrom() {
		return dtValidFrom;
	}

	public void setDtValidFrom(String dtValidFrom) {
		this.dtValidFrom = dtValidFrom;
	}

	public String getDtValidTo() {
		return dtValidTo;
	}

	public void setDtValidTo(String dtValidTo) {
		this.dtValidTo = dtValidTo;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getDtCreatedDate() {
		return dtCreatedDate;
	}

	public void setDtCreatedDate(String dtCreatedDate) {
		this.dtCreatedDate = dtCreatedDate;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
		this.intId = intId;
	}

	public String getStrUOM() {
		return strUOM;
	}

	public void setStrUOM(String strUOM) {
		this.strUOM = strUOM;
	}

	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = dblQty;
	}

	@Column(name = "intLevel", columnDefinition = "Int(8) default '0'")
	private int intLevel = 0;

	public int getIntLevel() {
		return intLevel;
	}

	public void setIntLevel(int intLevel) {
		this.intLevel = (int) setDefaultValue(intLevel, "0");
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

	public String getStrMethod() {
		return strMethod;
	}

	public void setStrMethod(String strMethod) {
		this.strMethod = (String) setDefaultValue(strMethod, "");
	}

	public String getStrParentName() {
		return strParentName;
	}

	public void setStrParentName(String strParentName) {
		this.strParentName = strParentName;
	}

	public String getStrBOMType() {
		return strBOMType;
	}

	public void setStrBOMType(String strBOMType) {
		this.strBOMType = strBOMType;
	}
}
