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
@Table(name = "tblcommitteemaster")
@IdClass(clsWebClubCommitteeMasterModel_ID.class)
public class clsWebClubCommitteeMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsWebClubCommitteeMasterModel() {
	}

	public clsWebClubCommitteeMasterModel(clsWebClubCommitteeMasterModel_ID objModelID) {
		strCommitteeCode = objModelID.getStrCommitteeCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strCommitteeCode", column = @Column(name = "strCommitteeCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strCommitteeCode")
	private String strCommitteeCode;

	@Column(name = "strCommitteeName")
	private String strCommitteeName;

	@Column(name = "strType")
	private String strType;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "dteCreatedDate")
	private String dteCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteLastModified")
	private String dteLastModified;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyID")
	private String strPropertyID;

	// Setter-Getter Methods
	public String getStrCommitteeCode() {
		return strCommitteeCode;
	}

	public void setStrCommitteeCode(String strCommitteeCode) {
		this.strCommitteeCode = (String) setDefaultValue(strCommitteeCode, "NA");
	}

	public String getStrCommitteeName() {
		return strCommitteeName;
	}

	public void setStrCommitteeName(String strCommitteeName) {
		this.strCommitteeName = (String) setDefaultValue(strCommitteeName, "NA");
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = (String) setDefaultValue(strType, "NA");
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = (String) setDefaultValue(strUserCreated, "NA");
	}

	public String getDteCreatedDate() {
		return dteCreatedDate;
	}

	public void setDteCreatedDate(String dteCreatedDate) {
		this.dteCreatedDate = dteCreatedDate;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = (String) setDefaultValue(strUserModified, "NA");
	}

	public String getDteLastModified() {
		return dteLastModified;
	}

	public void setDteLastModified(String dteLastModified) {
		this.dteLastModified = dteLastModified;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = (String) setDefaultValue(strClientCode, "NA");
	}

	public String getStrPropertyID() {
		return strPropertyID;
	}

	public void setStrPropertyID(String strPropertyID) {
		this.strPropertyID = (String) setDefaultValue(strPropertyID, "NA");
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
