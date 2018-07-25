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
@Table(name = "tblstockadjustmenthd")
@IdClass(clsStkAdjustmentHdModel_ID.class)
public class clsStkAdjustmentHdModel implements Serializable {
	public clsStkAdjustmentHdModel() {
	}

	public clsStkAdjustmentHdModel(clsStkAdjustmentHdModel_ID clsStkAdjustmentHdModel_ID) {
		strSACode = clsStkAdjustmentHdModel_ID.getStrSACode();
		strClientCode = clsStkAdjustmentHdModel_ID.getStrClientCode();
	}

	@Column(name = "intId", nullable = false, updatable = false)
	private long intId;

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strSACode", column = @Column(name = "strSACode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strSACode")
	private String strSACode;

	@Column(name = "dtSADate")
	private String dtSADate;

	@Column(name = "strLocCode")
	private String strLocCode;

	@Column(name = "strNarration")
	private String strNarration;

	@Column(name = "strAuthorise")
	private String strAuthorise;

	@Column(name = "strUserCreated", nullable = false, updatable = false)
	private String strUserCreated;

	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtCreatedDate", nullable = false, updatable = false)
	private String dtCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dtLastModified")
	private String dtLastModified;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strReasonCode", columnDefinition = "VARCHAR(20) NOT NULL default '' ")
	private String strReasonCode;

	@Column(name = "dblTotalAmt", columnDefinition = "DECIMAL(18,4) NOT NULL default '0'")
	private Double dblTotalAmt;

	@Column(name = "strConversionUOM", columnDefinition = "VARCHAR(20) NOT NULL default '' ")
	private String strConversionUOM;

	@Transient
	private String strLocName;

	public String getStrSACode() {
		return strSACode;
	}

	public void setStrSACode(String strSACode) {
		this.strSACode = strSACode;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getDtSADate() {
		return dtSADate;
	}

	public void setDtSADate(String dtSADate) {
		this.dtSADate = dtSADate;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
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

	public String getStrReasonCode() {
		return strReasonCode;
	}

	public void setStrReasonCode(String strReasonCode) {
		this.strReasonCode = (String) setDefaultValue(strReasonCode, "");
	}

	public Double getDblTotalAmt() {
		return dblTotalAmt;
	}

	public void setDblTotalAmt(Double dblTotalAmt) {
		this.dblTotalAmt = dblTotalAmt;
	}

	public String getStrConversionUOM() {
		return strConversionUOM;
	}

	public void setStrConversionUOM(String strConversionUOM) {
		this.strConversionUOM = strConversionUOM;
	}
}
