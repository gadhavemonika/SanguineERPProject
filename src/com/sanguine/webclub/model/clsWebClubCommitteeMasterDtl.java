package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@Table(name = "tblcommitteedtl")
public class clsWebClubCommitteeMasterDtl implements Serializable {
	private static final long serialVersionUID = 1L;

	// Variable Declaration
	@Column(name = "strCommitteeCode")
	private String strCommitteeCode;

	@Column(name = "strMemberCode")
	private String strMemberCode;

	@Column(name = "strMemberName")
	private String strMemberName;

	@Column(name = "strRoleCode")
	private String strRoleCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Transient
	private String strRoleName;

	@Id
	@Column(name = "intId")
	private long intId;

	// Setter-Getter Methods
	public String getStrCommitteeCode() {
		return strCommitteeCode;
	}

	public void setStrCommitteeCode(String strCommitteeCode) {
		this.strCommitteeCode = (String) setDefaultValue(strCommitteeCode, "NA");
	}

	public String getStrMemberCode() {
		return strMemberCode;
	}

	public void setStrMemberCode(String strMemberCode) {
		this.strMemberCode = (String) setDefaultValue(strMemberCode, "NA");
	}

	public String getStrMemberName() {
		return strMemberName;
	}

	public void setStrMemberName(String strMemberName) {
		this.strMemberName = (String) setDefaultValue(strMemberName, "NA");
	}

	public String getStrRoleCode() {
		return strRoleCode;
	}

	public void setStrRoleCode(String strRoleCode) {
		this.strRoleCode = (String) setDefaultValue(strRoleCode, "NA");
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

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = (Long) setDefaultValue(intId, 0);
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

	public String getStrRoleName() {
		return strRoleName;
	}

	public void setStrRoleName(String strRoleName) {
		this.strRoleName = strRoleName;
	}

}
