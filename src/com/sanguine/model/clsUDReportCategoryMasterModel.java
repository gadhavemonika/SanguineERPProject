package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tbludcategory")
@IdClass(clsUDReportCategoryMasterModel_ID.class)
public class clsUDReportCategoryMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsUDReportCategoryMasterModel() {
	}

	public clsUDReportCategoryMasterModel(clsUDReportCategoryMasterModel_ID objModelID) {
		strUDCCode = objModelID.getStrUDCCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strUDCCode", column = @Column(name = "strUDCCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "intUDCode")
	private long intUDCode;

	@Column(name = "strUDCCode")
	private String strUDCCode;

	@Column(name = "strUDCName")
	private String strUDCName;

	@Column(name = "strUDCDesc")
	private String strUDCDesc;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteDateCreated", updatable = false)
	private String dteDateCreated;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	@Column(name = "strClientCode")
	private String strClientCode;

	// Setter-Getter Methods
	public long getIntUDCode() {
		return intUDCode;
	}

	public void setIntUDCode(long intUDCode) {
		this.intUDCode = (Long) setDefaultValue(intUDCode, "NA");
	}

	public String getStrUDCCode() {
		return strUDCCode;
	}

	public void setStrUDCCode(String strUDCCode) {
		this.strUDCCode = (String) setDefaultValue(strUDCCode, "NA");
	}

	public String getStrUDCName() {
		return strUDCName;
	}

	public void setStrUDCName(String strUDCName) {
		this.strUDCName = (String) setDefaultValue(strUDCName, "NA");
	}

	public String getStrUDCDesc() {
		return strUDCDesc;
	}

	public void setStrUDCDesc(String strUDCDesc) {
		this.strUDCDesc = (String) setDefaultValue(strUDCDesc, "NA");
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
