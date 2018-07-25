package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblpermitmaster")
public class clsPermitMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsPermitMasterModel() {
	}

	// Variable Declaration

	@Id
	@GeneratedValue
	@Column(name = "intId")
	private long intId;

	@Column(name = "strPermitCode")
	private String strPermitCode;

	@Column(name = "strPermitName")
	private String strPermitName;

	@Column(name = "strPermitNo")
	private String strPermitNo;

	@Column(name = "StrPermitPlace")
	private String strPermitPlace;

	@Column(name = "dtePermitIssue")
	private String dtePermitIssue;

	@Column(name = "dtePermitExp")
	private String dtePermitExp;

	@Column(name = "strPermitStatus")
	private String strPermitStatus;

	@Column(name = "dtePermitEdited")
	private String dtePermitEdited;

	@Column(name = "strPermitUserCode")
	private String strPermitUserCode;

	// Setter-Getter Methods
	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = (Long) setDefaultValue(intId, "NA");
	}

	public String getStrPermitCode() {
		return strPermitCode;
	}

	public void setStrPermitCode(String strPermitCode) {
		this.strPermitCode = (String) setDefaultValue(strPermitCode, "NA");
	}

	public String getStrPermitName() {
		return strPermitName;
	}

	public void setStrPermitName(String strPermitName) {
		this.strPermitName = (String) setDefaultValue(strPermitName, "NA");
	}

	public String getStrPermitNo() {
		return strPermitNo;
	}

	public void setStrPermitNo(String strPermitNo) {
		this.strPermitNo = (String) setDefaultValue(strPermitNo, "NA");
	}

	public String getStrPermitPlace() {
		return strPermitPlace;
	}

	public void setStrPermitPlace(String strPermitPlace) {
		this.strPermitPlace = strPermitPlace;
	}

	public String getDtePermitIssue() {
		return dtePermitIssue;
	}

	public void setDtePermitIssue(String dtePermitIssue) {
		this.dtePermitIssue = dtePermitIssue;
	}

	public String getDtePermitExp() {
		return dtePermitExp;
	}

	public void setDtePermitExp(String dtePermitExp) {
		this.dtePermitExp = dtePermitExp;
	}

	public String getStrPermitStatus() {
		return strPermitStatus;
	}

	public void setStrPermitStatus(String strPermitStatus) {
		this.strPermitStatus = (String) setDefaultValue(strPermitStatus, "NA");
	}

	public String getDtePermitEdited() {
		return dtePermitEdited;
	}

	public void setDtePermitEdited(String dtePermitEdited) {
		this.dtePermitEdited = dtePermitEdited;
	}

	public String getStrPermitUserCode() {
		return strPermitUserCode;
	}

	public void setStrPermitUserCode(String strPermitUserCode) {
		this.strPermitUserCode = (String) setDefaultValue(strPermitUserCode, "NA");
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
