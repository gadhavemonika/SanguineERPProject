package com.sanguine.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblmaterialreturnhd")
@IdClass(clsMaterialReturnHdModel_ID.class)
public class clsMaterialReturnHdModel {
	public clsMaterialReturnHdModel() {
	}

	public clsMaterialReturnHdModel(clsMaterialReturnHdModel_ID clsMaterialReturnHdModel_ID) {
		strMRetCode = clsMaterialReturnHdModel_ID.getStrMRetCode();
		strClientCode = clsMaterialReturnHdModel_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strMRetCode", column = @Column(name = "strMRetCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strMRetCode")
	private String strMRetCode;

	@Column(name = "dtMRetDate", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtMRetDate;

	@Column(name = "strAgainst", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strAgainst;

	@Column(name = "strMISCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strMISCode;

	@Column(name = "strLocFrom", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strLocFrom;

	@Column(name = "strLocTo", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strLocTo;

	@Column(name = "strNarration", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strNarration;

	@Column(name = "strAuthorise", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strAuthorise;

	@Column(name = "dtCreatedDate", nullable = false, updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtCreatedDate;

	@Column(name = "strUserModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserModified;

	@Column(name = "dtLastModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtLastModified;

	@Column(name = "strUserCreated", nullable = false, updatable = false)
	private String strUserCreated;

	@Column(name = "strClientCode", nullable = false, updatable = false)
	private String strClientCode;

	@Column(name = "intId", nullable = false, updatable = false)
	private long intId;

	@Transient
	private String strLocFromName;

	@Transient
	private String strLocToName;

	public String getStrMRetCode() {
		return strMRetCode;
	}

	public void setStrMRetCode(String strMRetCode) {
		this.strMRetCode = strMRetCode;
	}

	public String getDtMRetDate() {
		return dtMRetDate;
	}

	public void setDtMRetDate(String dtMRetDate) {
		this.dtMRetDate = dtMRetDate;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	public String getStrMISCode() {
		return strMISCode;
	}

	public void setStrMISCode(String strMISCode) {
		this.strMISCode = strMISCode;
	}

	public String getStrLocFrom() {
		return strLocFrom;
	}

	public void setStrLocFrom(String strLocFrom) {
		this.strLocFrom = strLocFrom;
	}

	public String getStrLocTo() {
		return strLocTo;
	}

	public void setStrLocTo(String strLocTo) {
		this.strLocTo = strLocTo;
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

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrLocFromName() {
		return strLocFromName;
	}

	public void setStrLocFromName(String strLocFromName) {
		this.strLocFromName = strLocFromName;
	}

	public String getStrLocToName() {
		return strLocToName;
	}

	public void setStrLocToName(String strLocToName) {
		this.strLocToName = strLocToName;
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

}
