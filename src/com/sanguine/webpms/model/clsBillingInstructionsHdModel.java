package com.sanguine.webpms.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;

@Entity
@Table(name = "tblbillinginstructions")
@IdClass(clsBillingInstructionsModel_ID.class)
public class clsBillingInstructionsHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsBillingInstructionsHdModel() {
	}

	public clsBillingInstructionsHdModel(clsBillingInstructionsModel_ID objModelID) {
		strBillingInstCode = objModelID.getStrBillingInstCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strBillingInstCode", column = @Column(name = "strBillingInstCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strBillingInstCode")
	private String strBillingInstCode;

	@Column(name = "strBillingInstDesc")
	private String strBillingInstDesc;

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
	public String getStrBillingInstCode() {
		return strBillingInstCode;
	}

	public void setStrBillingInstCode(String strBillingInstCode) {
		this.strBillingInstCode = (String) setDefaultValue(strBillingInstCode, "NA");
	}

	public String getStrBillingInstDesc() {
		return strBillingInstDesc;
	}

	public void setStrBillingInstDesc(String strBillingInstDesc) {
		this.strBillingInstDesc = (String) setDefaultValue(strBillingInstDesc, "NA");
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
