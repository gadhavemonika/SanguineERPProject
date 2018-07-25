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
@Table(name = "tblstockpostinghd")
@IdClass(clsStkPostingHdModel_ID.class)
public class clsStkPostingHdModel implements Serializable {
	public clsStkPostingHdModel() {
	}

	public clsStkPostingHdModel(clsStkPostingHdModel_ID clsStkPostingHdModel_ID) {
		strPSCode = clsStkPostingHdModel_ID.getStrPSCode();
		strClientCode = clsStkPostingHdModel_ID.getStrClientCode();
	}

	@Column(name = "intId", nullable = false, updatable = false)
	private long intId;

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strPSCode", column = @Column(name = "strPSCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strPSCode")
	private String strPSCode;

	@Column(name = "dtPSDate")
	private String dtPSDate;

	@Column(name = "strLocCode")
	private String strLocCode;

	@Column(name = "strSACode")
	private String strSACode;

	@Column(name = "strAuthorise")
	private String strAuthorise;

	@Column(name = "intIndex", nullable = false, updatable = false)
	private int intIndex;

	@Column(name = "strUserCreated", nullable = false, updatable = false)
	private String strUserCreated;

	@Column(name = "dtCreatedDate", nullable = false, updatable = false)
	private String dtCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dtLastModified")
	private String dtLastModified;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "dblTotalAmt")
	private double dblTotalAmt;

	@Column(name = "strNarration")
	private String strNarration;

	@Column(name = "strConversionUOM", columnDefinition = "VARCHAR(15)NOT NULL default ''")
	private String strConversionUOM;

	@Transient
	private String strLocName;

	@Column(name = "strPhyStkFrom", columnDefinition = "VARCHAR(10)NOT NULL default 'System'")
	private String strPhyStkFrom;

	public String getStrPSCode() {
		return strPSCode;
	}

	public void setStrPSCode(String strPSCode) {
		this.strPSCode = strPSCode;
	}

	public String getDtPSDate() {
		return dtPSDate;
	}

	public void setDtPSDate(String dtPSDate) {
		this.dtPSDate = dtPSDate;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrSACode() {
		return strSACode;
	}

	public void setStrSACode(String strSACode) {
		this.strSACode = strSACode;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	public int getIntIndex() {
		return intIndex;
	}

	public void setIntIndex(int intIndex) {
		this.intIndex = intIndex;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
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

	public String getStrLocName() {
		return strLocName;
	}

	public void setStrLocName(String strLocName) {
		this.strLocName = strLocName;
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
		// return value !=null && (value instanceof String &&
		// value.toString().length()>0) ? value : defaultValue;
	}

	public double getDblTotalAmt() {
		return dblTotalAmt;
	}

	public void setDblTotalAmt(double dblTotalAmt) {
		this.dblTotalAmt = dblTotalAmt;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrConversionUOM() {
		return strConversionUOM;
	}

	public void setStrConversionUOM(String strConversionUOM) {
		this.strConversionUOM = strConversionUOM;
	}

	public String getStrPhyStkFrom() {
		return strPhyStkFrom;
	}

	public void setStrPhyStkFrom(String strPhyStkFrom) {
		this.strPhyStkFrom = strPhyStkFrom;
	}
}
