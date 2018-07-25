package com.sanguine.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblattvaluemaster")
@IdClass(clsAttributeValueMasterModel_ID.class)
public class clsAttributeValueMasterModel {

	public clsAttributeValueMasterModel() {
	}

	public clsAttributeValueMasterModel(clsAttributeValueMasterModel_ID clsAttributeValueMasterModel_ID) {
		strAVCode = clsAttributeValueMasterModel_ID.getStrAVCode();
		strClientCode = clsAttributeValueMasterModel_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strAVCode", column = @Column(name = "strAVCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strAVCode")
	private String strAVCode;

	@Column(name = "strAttCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strAttCode;

	@Column(name = "strAVName", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strAVName;

	@Column(name = "strAVDesc", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strAVDesc;

	@Column(name = "StrUserCreated", updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserCreated;

	@Column(name = "StrUserModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserModified;

	@Column(name = "DtCreatedDate", updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtCreatedDate;

	@Column(name = "DtLastModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtLastModified;

	@Column(name = "StrClientCode")
	private String strClientCode;

	@Column(name = "IntId", updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
	private long intId;

	public String getStrAVCode() {
		return strAVCode;
	}

	public void setStrAVCode(String strAVCode) {
		this.strAVCode = strAVCode;
	}

	public String getStrAVName() {
		return strAVName;
	}

	public void setStrAVName(String strAVName) {
		this.strAVName = (String) setDefaultValue(strAVName, "");
	}

	public String getStrAttCode() {
		return strAttCode;
	}

	public void setStrAttCode(String strAttCode) {
		this.strAttCode = (String) setDefaultValue(strAttCode, "");
	}

	public String getStrAVDesc() {
		return strAVDesc;
	}

	public void setStrAVDesc(String strAVDesc) {
		this.strAVDesc = (String) setDefaultValue(strAVDesc, "");
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = (String) setDefaultValue(strUserCreated, "");
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = (String) setDefaultValue(strUserModified, "");
	}

	public String getDtCreatedDate() {
		return dtCreatedDate;
	}

	public void setDtCreatedDate(String dtCreatedDate) {
		this.dtCreatedDate = (String) setDefaultValue(dtCreatedDate, "");
	}

	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = (String) setDefaultValue(dtLastModified, "");
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
