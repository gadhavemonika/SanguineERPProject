package com.sanguine.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;

@Entity
@Table(name = "tbllinkup")
@IdClass(clsLinkUpModel_ID.class)
public class clsLinkUpHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsLinkUpHdModel() {
	}

	public clsLinkUpHdModel(clsLinkUpModel_ID objModelID) {
		strMasterCode = objModelID.getStrOperationType();
		strClientCode = objModelID.getStrClientCode();
		strPropertyCode = objModelID.getStrPropertyCode();
		strOperationType = objModelID.getStrOperationType();
		strModuleType = objModelID.getStrModuleType();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strSGCode", column = @Column(name = "strSGCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")), @AttributeOverride(name = "strPropertyCode", column = @Column(name = "strPropertyCode")), @AttributeOverride(name = "strOperationType", column = @Column(name = "strOperationType")),
			@AttributeOverride(name = "strModuleType", column = @Column(name = "strModuleType")) })
	// Variable Declaration
	@Column(name = "strMasterCode")
	private String strMasterCode;

	@Column(name = "strMasterDesc")
	private String strMasterDesc;

	@Column(name = "strMasterName")
	private String strMasterName;

	@Column(name = "strAccountCode")
	private String strAccountCode;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dteCreatedDate")
	private String dteCreatedDate;

	@Column(name = "dteLastModified")
	private String dteLastModified;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strExSuppCode")
	private String strExSuppCode;

	@Column(name = "strExSuppName")
	private String strExSuppName;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "strOperationType")
	private String strOperationType;

	@Column(name = "strModuleType")
	private String strModuleType;
	
	@Column(name = "strWebBookAccCode")
	private String strWebBookAccCode;
	
	@Column(name = "strWebBookAccName")
	private String strWebBookAccName;

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

	// Setter-Getter Methods

	public String getStrMasterCode() {
		return strMasterCode;
	}

	public void setStrMasterCode(String strMasterCode) {
		this.strMasterCode = strMasterCode;
	}

	public String getStrMasterDesc() {
		return strMasterDesc;
	}

	public void setStrMasterDesc(String strMasterDesc) {
		this.strMasterDesc = strMasterDesc;
	}

	public String getStrMasterName() {
		return strMasterName;
	}

	public void setStrMasterName(String strMasterName) {
		this.strMasterName = strMasterName;
	}

	public String getStrAccountCode() {
		return strAccountCode;
	}

	public void setStrAccountCode(String strAccountCode) {
		this.strAccountCode = strAccountCode;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getStrUserEdited() {
		return strUserEdited;
	}

	public void setStrUserEdited(String strUserEdited) {
		this.strUserEdited = strUserEdited;
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
		this.strClientCode = strClientCode;
	}

	public String getStrOperationType() {
		return strOperationType;
	}

	public void setStrOperationType(String strOperationType) {
		this.strOperationType = strOperationType;
	}

	public String getStrExSuppCode() {
		return strExSuppCode;
	}

	public void setStrExSuppCode(String strExSuppCode) {
		this.strExSuppCode = strExSuppCode;
	}

	public String getStrExSuppName() {
		return strExSuppName;
	}

	public void setStrExSuppName(String strExSuppName) {
		this.strExSuppName = strExSuppName;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrModuleType() {
		return strModuleType;
	}

	public void setStrModuleType(String strModuleType) {
		this.strModuleType = strModuleType;
	}

	public String getStrWebBookAccCode() {
		return strWebBookAccCode;
	}

	public void setStrWebBookAccCode(String strWebBookAccCode) {
		this.strWebBookAccCode = (String) setDefaultValue( strWebBookAccCode, "");
	}

	public String getStrWebBookAccName() {
		return strWebBookAccName;
	}

	public void setStrWebBookAccName(String strWebBookAccName) {
		this.strWebBookAccName = (String) setDefaultValue( strWebBookAccName, "");
		 
	}
	
}
