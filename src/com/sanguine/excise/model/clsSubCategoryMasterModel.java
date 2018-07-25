package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author pc
 *
 */
@Entity
@Table(name = "tblsubcategorymaster")
@IdClass(clsSubCategoryMasterModel_ID.class)
public class clsSubCategoryMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsSubCategoryMasterModel() {
	}

	public clsSubCategoryMasterModel(clsSubCategoryMasterModel_ID objModelID) {
		strSubCategoryCode = objModelID.getStrSubCategoryCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strSubCategoryCode", column = @Column(name = "strSubCategoryCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strSubCategoryCode")
	private String strSubCategoryCode;

	@Column(name = "intId")
	private long intId;

	@Column(name = "strSubCategoryName")
	private String strSubCategoryName;

	@Column(name = "intPegSize")
	private long intPegSize;

	@Column(name = "strCategoryCode")
	private String strCategoryCode;

	@Transient
	private String strCategoryName;

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
	public String getStrSubCategoryCode() {
		return strSubCategoryCode;
	}

	public void setStrSubCategoryCode(String strSubCategoryCode) {
		this.strSubCategoryCode = (String) setDefaultValue(strSubCategoryCode, "NA");
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = (Long) setDefaultValue(intId, "NA");
	}

	public String getStrSubCategoryName() {
		return strSubCategoryName;
	}

	public void setStrSubCategoryName(String strSubCategoryName) {
		this.strSubCategoryName = (String) setDefaultValue(strSubCategoryName, "NA");
	}

	public long getIntPegSize() {
		return intPegSize;
	}

	public void setIntPegSize(long intPegSize) {
		this.intPegSize = intPegSize;
	}

	public String getStrCategoryCode() {
		return strCategoryCode;
	}

	public void setStrCategoryCode(String strCategoryCode) {
		this.strCategoryCode = (String) setDefaultValue(strCategoryCode, "NA");
	}

	public String getStrCategoryName() {
		return strCategoryName;
	}

	public void setStrCategoryName(String strCategoryName) {
		this.strCategoryName = strCategoryName;
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
