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
@Table(name = "tblbankmaster")
@IdClass(clsBankMasterModel_ID.class)
public class clsBankMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsBankMasterModel() {
	}

	public clsBankMasterModel(clsBankMasterModel_ID objModelID) {
		strBankCode = objModelID.getStrBankCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strBankCode", column = @Column(name = "strBankCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strBankCode")
	private String strBankCode;

	@Column(name = "intGId", nullable = false, updatable = false)
	private long intGId;

	@Column(name = "strBankName")
	private String strBankName;

	@Column(name = "strBranch")
	private String strBranch;

	@Column(name = "strMICR")
	private String strMICR;

	@Column(name = "dteCreatedDate", updatable = false)
	private String dteCreatedDate;

	@Column(name = "dteLastModified")
	private String dteLastModified;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	// Setter-Getter Methods
	public String getStrBankCode() {
		return strBankCode;
	}

	public void setStrBankCode(String strBankCode) {
		this.strBankCode = (String) setDefaultValue(strBankCode, "NA");
	}

	public long getIntGId() {
		return intGId;
	}

	public void setIntGId(long intGId) {
		this.intGId = (Long) setDefaultValue(intGId, "NA");
	}

	public String getStrBankName() {
		return strBankName;
	}

	public void setStrBankName(String strBankName) {
		this.strBankName = (String) setDefaultValue(strBankName, "NA");
	}

	public String getStrBranch() {
		return strBranch;
	}

	public void setStrBranch(String strBranch) {
		this.strBranch = (String) setDefaultValue(strBranch, "NA");
	}

	public String getStrMICR() {
		return strMICR;
	}

	public void setStrMICR(String strMICR) {
		this.strMICR = (String) setDefaultValue(strMICR, "NA");
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
