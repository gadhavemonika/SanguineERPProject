package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblexcisepermitmaster")
@IdClass(clsExcisePermitMasterModel_ID.class)
public class clsExcisePermitMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsExcisePermitMasterModel() {
	}

	public clsExcisePermitMasterModel(clsExcisePermitMasterModel_ID objModelID) {
		strPermitCode = objModelID.getStrPermitCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strPermitCode", column = @Column(name = "strPermitCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strPermitCode")
	private String strPermitCode;

	@Column(name = "intId")
	private long intId;

	@Column(name = "strPermitName")
	private String strPermitName;

	@Column(name = "strPermitNo")
	private String strPermitNo;

	@Column(name = "dtePermitExp")
	private String dtePermitExp;

	@Column(name = "strPermitType")
	private String strPermitType;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dteDateCreated")
	private String dteDateCreated;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	@Column(name = "strClientCode")
	private String strClientCode;

	// Setter-Getter Methods
	public String getStrPermitCode() {
		return strPermitCode;
	}

	public void setStrPermitCode(String strPermitCode) {
		this.strPermitCode = (String) setDefaultValue(strPermitCode, "NA");
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = (Long) setDefaultValue(intId, "0");
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

	public String getDtePermitExp() {
		return dtePermitExp;
	}

	public void setDtePermitExp(String dtePermitExp) {
		this.dtePermitExp = dtePermitExp;
	}

	public String getStrPermitType() {
		return strPermitType;
	}

	public void setStrPermitType(String strPermitType) {
		this.strPermitType = (String) setDefaultValue(strPermitType, "NA");
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = (String) setDefaultValue(strUserCreated, "NA");
	}

	public String getStrUserEdited() {
		return strUserEdited;
	}

	public void setStrUserEdited(String strUserEdited) {
		this.strUserEdited = (String) setDefaultValue(strUserEdited, "NA");
	}

	public String getDteDateCreated() {
		return dteDateCreated;
	}

	public void setDteDateCreated(String dteDateCreated) {
		this.dteDateCreated = dteDateCreated;
	}

	public String getDteDateEdited() {
		return dteDateEdited;
	}

	public void setDteDateEdited(String dteDateEdited) {
		this.dteDateEdited = dteDateEdited;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = (String) setDefaultValue(strClientCode, "NA");
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
