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
@Table(name = "vareamaster")
@IdClass(clsAreaMasterModel_ID.class)
public class clsAreaMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsAreaMasterModel() {
	}

	public clsAreaMasterModel(clsAreaMasterModel_ID objModelID) {
		strAreaCode = objModelID.getStrAreaCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strAreaCode", column = @Column(name = "strAreaCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strAreaCode")
	private String strAreaCode;

	@Column(name = "strAreaName")
	private String strAreaName;

	@Column(name = "strCityCode")
	private String strCityCode;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "dteUserCreatedDate", updatable = false)
	private String dteUserCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteLastModifiedDate")
	private String dteLastModifiedDate;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "intGId", updatable = false, nullable = false)
	private long intGId;

	// Setter-Getter Methods
	public String getStrAreaCode() {
		return strAreaCode;
	}

	public void setStrAreaCode(String strAreaCode) {
		this.strAreaCode = (String) setDefaultValue(strAreaCode, "NA");
	}

	public String getStrAreaName() {
		return strAreaName;
	}

	public void setStrAreaName(String strAreaName) {
		this.strAreaName = (String) setDefaultValue(strAreaName, "NA");
	}

	public String getStrCityCode() {
		return strCityCode;
	}

	public void setStrCityCode(String strCityCode) {
		this.strCityCode = (String) setDefaultValue(strCityCode, "NA");
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

	public long getIntGId() {
		return intGId;
	}

	public void setIntGId(long intGId) {
		this.intGId = (Long) setDefaultValue(intGId, "NA");
	}

	public String getDteUserCreatedDate() {
		return dteUserCreatedDate;
	}

	public void setDteUserCreatedDate(String dteUserCreatedDate) {
		this.dteUserCreatedDate = dteUserCreatedDate;
	}

	public String getDteLastModifiedDate() {
		return dteLastModifiedDate;
	}

	public void setDteLastModifiedDate(String dteLastModifiedDate) {
		this.dteLastModifiedDate = dteLastModifiedDate;
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
