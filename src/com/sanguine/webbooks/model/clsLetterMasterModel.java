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
@Table(name = "tbllettermaster")
@IdClass(clsLetterMasterModel_ID.class)
public class clsLetterMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsLetterMasterModel() {
	}

	public clsLetterMasterModel(clsLetterMasterModel_ID objModelID) {
		strLetterCode = objModelID.getStrLetterCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strLetterCode", column = @Column(name = "strLetterCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "intGId", updatable = false, nullable = false)
	private long intGId;

	@Column(name = "strLetterCode")
	private String strLetterCode;

	@Column(name = "strLetterName")
	private String strLetterName;

	@Column(name = "strArea")
	private String strArea;

	@Column(name = "strReminderYN")
	private String strReminderYN;

	@Column(name = "strReminderLetter")
	private String strReminderLetter;

	@Column(name = "strView")
	private String strView;

	@Column(name = "strIsCircular")
	private String strIsCircular;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dteDateCreated", updatable = false)
	private String dteDateCreated;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	// Setter-Getter Methods
	public long getIntGId() {
		return intGId;
	}

	public void setIntGId(long intGId) {
		this.intGId = (Long) setDefaultValue(intGId, "0");
	}

	public String getStrLetterCode() {
		return strLetterCode;
	}

	public void setStrLetterCode(String strLetterCode) {
		this.strLetterCode = (String) setDefaultValue(strLetterCode, "NA");
	}

	public String getStrLetterName() {
		return strLetterName;
	}

	public void setStrLetterName(String strLetterName) {
		this.strLetterName = (String) setDefaultValue(strLetterName, "NA");
	}

	public String getStrArea() {
		return strArea;
	}

	public void setStrArea(String strArea) {
		this.strArea = (String) setDefaultValue(strArea, "NA");
	}

	public String getStrReminderYN() {
		return strReminderYN;
	}

	public void setStrReminderYN(String strReminderYN) {
		this.strReminderYN = (String) setDefaultValue(strReminderYN, "N");
	}

	public String getStrReminderLetter() {
		return strReminderLetter;
	}

	public void setStrReminderLetter(String strReminderLetter) {
		this.strReminderLetter = (String) setDefaultValue(strReminderLetter, "NA");
	}

	public String getStrView() {
		return strView;
	}

	public void setStrView(String strView) {
		this.strView = (String) setDefaultValue(strView, "NA");
	}

	public String getStrIsCircular() {
		return strIsCircular;
	}

	public void setStrIsCircular(String strIsCircular) {
		this.strIsCircular = (String) setDefaultValue(strIsCircular, "N");
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
