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
@Table(name = "tblproductionhd")
@IdClass(clsProductionHdModel_ID.class)
public class clsProductionHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsProductionHdModel() {
	}

	public clsProductionHdModel(clsProductionHdModel_ID clsProductionHdModel_ID) {
		strPDCode = clsProductionHdModel_ID.getStrPDCode();
		strClientCode = clsProductionHdModel_ID.getStrClientCode();
	}

	private String strPDCode, dtPDDate, strLocCode, strLocationName, strNarration;
	private String strWOCode, strUserCreated, dtCreatedDate, strUserModified, dtLastModified, strAuthorise, strClientCode;
	private long intid;

	@Column(name = "intid")
	public long getIntid() {
		return intid;
	}

	public void setIntid(long intid) {
		this.intid = intid;
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strPDCode", column = @Column(name = "strPDCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strPDCode", nullable = false, updatable = false)
	public String getStrPDCode() {
		return strPDCode;
	}

	public void setStrPDCode(String strPDCode) {
		this.strPDCode = strPDCode;
	}

	@Column(name = "dtPDDate", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getDtPDDate() {
		return dtPDDate;
	}

	public void setDtPDDate(String dtPDDate) {
		this.dtPDDate = dtPDDate;
	}

	@Column(name = "strLocCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	@Transient
	public String getStrLocationName() {
		return strLocationName;
	}

	public void setStrLocationName(String strLocationName) {
		this.strLocationName = strLocationName;
	}

	@Column(name = "strNarration", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	@Column(name = "strWOCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrWOCode() {
		return strWOCode;
	}

	public void setStrWOCode(String strWOCode) {
		this.strWOCode = strWOCode;
	}

	@Column(name = "strUserCreated", nullable = false, updatable = false, columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	@Column(name = "dtCreatedDate", nullable = false, updatable = false, columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getDtCreatedDate() {
		return dtCreatedDate;
	}

	public void setDtCreatedDate(String dtCreatedDate) {
		this.dtCreatedDate = dtCreatedDate;
	}

	@Column(name = "strUserModified", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	@Column(name = "dtLastModified", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	@Column(name = "strAuthorise", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	private int intLevel = 0;

	@Column(name = "intLevel", columnDefinition = "Int(8) default '0'")
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
		// return value !=null && (value instanceof String &&
		// value.toString().length()>0) ? value : defaultValue;
	}
}
