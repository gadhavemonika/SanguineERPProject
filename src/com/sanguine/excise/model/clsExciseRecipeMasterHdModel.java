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
@Table(name = "tblexciserecipermasterhd")
@IdClass(clsExciseRecipeMasterModel_ID.class)
public class clsExciseRecipeMasterHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsExciseRecipeMasterHdModel() {
	}

	public clsExciseRecipeMasterHdModel(clsExciseRecipeMasterModel_ID objModelID) {
		strRecipeCode = objModelID.getStrRecipeCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strRecipeCode", column = @Column(name = "strRecipeCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strRecipeCode")
	private String strRecipeCode;

	@Column(name = "intId")
	private long intId;

	@Column(name = "strParentCode")
	private String strParentCode;

	@Column(name = "strParentName")
	private String strParentName;

	@Column(name = "dteValidFrom")
	private String dtValidFrom;

	@Column(name = "dteValidTo")
	private String dtValidTo;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteCreatedDate")
	private String dteDateCreated;

	@Column(name = "dteLastModified")
	private String dteDateEdited;

	@Column(name = "strClientCode")
	private String strClientCode;

	// Setter-Getter Methods

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = (Long) setDefaultValue(intId, "NA");
	}

	public String getStrParentCode() {
		return strParentCode;
	}

	public void setStrParentCode(String strParentCode) {
		this.strParentCode = (String) setDefaultValue(strParentCode, "NA");
	}

	public String getStrRecipeCode() {
		return strRecipeCode;
	}

	public void setStrRecipeCode(String strRecipeCode) {
		this.strRecipeCode = strRecipeCode;
	}

	public String getStrParentName() {
		return strParentName;
	}

	public void setStrParentName(String strParentName) {
		this.strParentName = strParentName;
	}

	public String getDtValidFrom() {
		return dtValidFrom;
	}

	public void setDtValidFrom(String dtValidFrom) {
		this.dtValidFrom = dtValidFrom;
	}

	public String getDtValidTo() {
		return dtValidTo;
	}

	public void setDtValidTo(String dtValidTo) {
		this.dtValidTo = dtValidTo;
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
