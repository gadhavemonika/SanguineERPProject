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
@Table(name = "vreasonmaster")
@IdClass(clsWebBooksReasonMasterModel_ID.class)
public class clsWebBooksReasonMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsWebBooksReasonMasterModel() {
	}

	public clsWebBooksReasonMasterModel(clsWebBooksReasonMasterModel_ID objModelID) {
		strReasonCode = objModelID.getStrReasonCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strReasonCode", column = @Column(name = "strReasonCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "intGId")
	private long intGId;

	@Column(name = "strReasonCode")
	private String strReasonCode;

	@Column(name = "strReasonDesc")
	private String strReasonDesc;

	@Column(name = "strExcludeInInvoicePrinting")
	private String strExcludeInInvoicePrinting;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteCreatedDate")
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
		this.intGId = (Long) setDefaultValue(intGId, "NA");
	}

	public String getStrReasonCode() {
		return strReasonCode;
	}

	public void setStrReasonCode(String strReasonCode) {
		this.strReasonCode = (String) setDefaultValue(strReasonCode, "NA");
	}

	public String getStrReasonDesc() {
		return strReasonDesc;
	}

	public void setStrReasonDesc(String strReasonDesc) {
		this.strReasonDesc = (String) setDefaultValue(strReasonDesc, "NA");
	}

	public String getStrExcludeInInvoicePrinting() {
		return strExcludeInInvoicePrinting;
	}

	public void setStrExcludeInInvoicePrinting(String strExcludeInInvoicePrinting) {
		this.strExcludeInInvoicePrinting = (String) setDefaultValue(strExcludeInInvoicePrinting, "NA");
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

}
