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
@Table(name = "tblattributemaster")
@IdClass(clsAttributeMasterModel_ID.class)
public class clsAttributeMasterModel {
	public clsAttributeMasterModel() {

	}

	public clsAttributeMasterModel(clsAttributeMasterModel_ID clsAttributeMasterModel_ID) {
		strAttCode = clsAttributeMasterModel_ID.getStrAttCode();
		strClientCode = clsAttributeMasterModel_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strAttCode", column = @Column(name = "strAttCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strAttCode")
	private String strAttCode;

	@Column(name = "strAttType", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strAttType;

	@Column(name = "strPAttCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strPAttCode;

	@Column(name = "strAttName", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strAttName;

	@Column(name = "strAttDesc", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strAttDesc;

	@Column(name = "StrUserCreated", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserCreated;

	@Column(name = "StrUserModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserModified;

	@Column(name = "DtCreatedDate", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtCreatedDate;

	@Column(name = "DtLastModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtLastModified;

	@Column(name = "StrClientCode")
	private String strClientCode;

	@Column(name = "IntId", nullable = false, columnDefinition = "BIGINT UNSIGNED")
	private long intId;

	@Transient
	private String strAVCode;

	public String getStrAttCode() {
		return strAttCode;
	}

	public void setStrAttCode(String strAttCode) {
		this.strAttCode = strAttCode;
	}

	public String getStrAVCode() {
		return strAVCode;
	}

	public void setStrAVCode(String strAVCode) {
		this.strAVCode = strAVCode;
	}

	public String getStrAttType() {
		return strAttType;
	}

	public void setStrAttType(String strAttType) {
		this.strAttType = strAttType;
	}

	public String getStrPAttCode() {
		return strPAttCode;
	}

	public void setStrPAttCode(String strPAttCode) {
		this.strPAttCode = (String) setDefaultValue(strPAttCode, "");
	}

	public String getStrAttName() {
		return strAttName;
	}

	public void setStrAttName(String strAttName) {
		this.strAttName = strAttName;
	}

	public String getStrAttDesc() {
		return strAttDesc;
	}

	public void setStrAttDesc(String strAttDesc) {
		this.strAttDesc = (String) setDefaultValue(strAttDesc, "");
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getDtCreatedDate() {
		return dtCreatedDate;
	}

	public void setDtCreatedDate(String dtCreatedDate) {
		this.dtCreatedDate = dtCreatedDate;
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

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
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
