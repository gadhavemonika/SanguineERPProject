package com.sanguine.webpms.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tbltaxgroup")
@IdClass(clsTaxGroupMasterModel_ID.class)
public class clsTaxGroupMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsTaxGroupMasterModel() {
	}

	public clsTaxGroupMasterModel(clsTaxGroupMasterModel_ID objModelID) {
		strTaxGroupCode = objModelID.getStrTaxGroupCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strTaxGroupCode", column = @Column(name = "strTaxGroupCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "intGId", updatable = false, nullable = false)
	private long intGId;

	@Column(name = "strTaxGroupCode")
	private String strTaxGroupCode;

	@Column(name = "strTaxGroupDesc")
	private String strTaxGroupDesc;

	@Column(name = "strUserCreated", updatable = false, nullable = false)
	private String strUserCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dteDateCreated", updatable = false, nullable = false)
	private String dteDateCreated;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	@Column(name = "strClientCode")
	private String strClientCode;

	// Setter-Getter Methods
	public long getIntGId() {
		return intGId;
	}

	public void setIntGId(long intGId) {
		this.intGId = (Long) setDefaultValue(intGId, "0");
	}

	public String getStrTaxGroupCode() {
		return strTaxGroupCode;
	}

	public void setStrTaxGroupCode(String strTaxGroupCode) {
		this.strTaxGroupCode = (String) setDefaultValue(strTaxGroupCode, "NA");
	}

	public String getStrTaxGroupDesc() {
		return strTaxGroupDesc;
	}

	public void setStrTaxGroupDesc(String strTaxGroupDesc) {
		this.strTaxGroupDesc = (String) setDefaultValue(strTaxGroupDesc, "NA");
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
