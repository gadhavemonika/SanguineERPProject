package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblacgroupmaster")
@IdClass(clsACGroupMasterModel_ID.class)
public class clsACGroupMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsACGroupMasterModel() {
	}

	public clsACGroupMasterModel(clsACGroupMasterModel_ID objModelID) {
		strGroupCode = objModelID.getStrGroupCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strGroupCode", column = @Column(name = "strGroupCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "intGId", nullable = false, updatable = false)
	private long intGId;

	@Column(name = "strGroupCode")
	private String strGroupCode;

	@Column(name = "strGroupName")
	private String strGroupName;

	@Column(name = "strShortName")
	private String strShortName;

	@Column(name = "strCategory")
	private String strCategory;

	@Column(name = "strDefaultType")
	private String strDefaultType;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteCreatedDate", updatable = false)
	private String dteCreatedDate;

	@Column(name = "dteLastModified")
	private String dteLastModified;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	// Setter-Getter Methods
	public long getIntGId() {
		return intGId;
	}

	public void setIntGId(long intGId) {
		this.intGId = (long) setDefaultValue(intGId, "NA");
	}

	public String getStrGroupCode() {
		return strGroupCode;
	}

	public void setStrGroupCode(String strGroupCode) {
		this.strGroupCode = (String) setDefaultValue(strGroupCode, "NA");
	}

	public String getStrGroupName() {
		return strGroupName;
	}

	public void setStrGroupName(String strGroupName) {
		this.strGroupName = (String) setDefaultValue(strGroupName, "NA");
	}

	public String getStrShortName() {
		return strShortName;
	}

	public void setStrShortName(String strShortName) {
		this.strShortName = (String) setDefaultValue(strShortName, "NA");
	}

	public String getStrCategory() {
		return strCategory;
	}

	public void setStrCategory(String strCategory) {
		this.strCategory = (String) setDefaultValue(strCategory, "NA");
	}

	public String getStrDefaultType() {
		return strDefaultType;
	}

	public void setStrDefaultType(String strDefaultType) {
		this.strDefaultType = (String) setDefaultValue(strDefaultType, "NA");
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = (String) setDefaultValue(strUserCreated, "NA");
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

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = (String) setDefaultValue(strPropertyCode, "NA");
	}

	public String getDteCreatedDate() {
		return dteCreatedDate;
	}

	public void setDteCreatedDate(String dteCreatedDate) {
		this.dteCreatedDate = dteCreatedDate;
	}

	public String getDteLastModified() {
		return dteLastModified;
	}

	public void setDteLastModified(String dteLastModified) {
		this.dteLastModified = dteLastModified;
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
