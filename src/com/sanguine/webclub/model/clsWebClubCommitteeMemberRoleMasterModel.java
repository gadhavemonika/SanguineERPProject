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
@Table(name = "tblcommitteememberrolemaster")
@IdClass(clsWebClubCommitteeMemberRoleMasterModel_ID.class)
public class clsWebClubCommitteeMemberRoleMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsWebClubCommitteeMemberRoleMasterModel() {
	}

	public clsWebClubCommitteeMemberRoleMasterModel(clsWebClubCommitteeMemberRoleMasterModel_ID objModelID) {
		strRoleCode = objModelID.getStrRoleCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strRoleCode", column = @Column(name = "strRoleCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strRoleCode")
	private String strRoleCode;

	@Column(name = "intId")
	public long intId;

	@Column(name = "strRoleDesc")
	private String strRoleDesc;

	@Column(name = "intRoleRank")
	private long intRoleRank;

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

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	// Setter-Getter Methods
	public String getStrRoleCode() {
		return strRoleCode;
	}

	public void setStrRoleCode(String strRoleCode) {
		this.strRoleCode = (String) setDefaultValue(strRoleCode, "NA");
	}

	public String getStrRoleDesc() {
		return strRoleDesc;
	}

	public void setStrRoleDesc(String strRoleDesc) {
		this.strRoleDesc = (String) setDefaultValue(strRoleDesc, "NA");
	}

	public long getIntRoleRank() {
		return intRoleRank;
	}

	public void setIntRoleRank(long intRoleRank) {
		this.intRoleRank = (Long) setDefaultValue(intRoleRank, "NA");
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

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = (String) setDefaultValue(strPropertyCode, "NA");
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

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

}
