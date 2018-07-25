package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblregionmaster")
@IdClass(clsWebClubRegionMasterModel_ID.class)
public class clsWebClubRegionMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsWebClubRegionMasterModel() {
	}

	public clsWebClubRegionMasterModel(clsWebClubRegionMasterModel_ID objModelID) {
		strRegionCode = objModelID.getStrRegionCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strRegionCode", column = @Column(name = "strRegionCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strRegionCode")
	private String strRegionCode;

	@Column(name = "strRegionName")
	private String strRegionName;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "intGId", updatable = false)
	private long intGId;

	@Column(name = "dtCreatedDate", updatable = false)
	private String dtCreatedDate;

	@Column(name = "dtLastModified")
	private String dtLastModified;

	// Setter-Getter Methods
	public String getStrRegionCode() {
		return strRegionCode;
	}

	public void setStrRegionCode(String strRegionCode) {
		this.strRegionCode = (String) setDefaultValue(strRegionCode, "NA");
	}

	public String getStrRegionName() {
		return strRegionName;
	}

	public void setStrRegionName(String strRegionName) {
		this.strRegionName = (String) setDefaultValue(strRegionName, "NA");
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

}
