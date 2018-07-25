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
@Table(name = "tblposlinkupmaster")
@IdClass(clsInterfaceMasterModel_ID.class)
public class clsInterfaceMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsInterfaceMasterModel() {
	}

	public clsInterfaceMasterModel(clsInterfaceMasterModel_ID objModelID) {
		strInterfaceCode = objModelID.getStrInterfaceCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strInterfaceCode", column = @Column(name = "strInterfaceCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "intGId", updatable = false, nullable = false)
	private long intGId;

	@Column(name = "strInterfaceCode")
	private String strInterfaceCode;

	@Column(name = "strInterfaceName")
	private String strInterfaceName;

	@Column(name = "strAccountCode")
	private String strAccountCode;

	@Column(name = "strAccountName")
	private String strAccountName;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteDateCreated", updatable = false)
	private String dteDateCreated;

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

	public String getStrInterfaceCode() {
		return strInterfaceCode;
	}

	public void setStrInterfaceCode(String strInterfaceCode) {
		this.strInterfaceCode = (String) setDefaultValue(strInterfaceCode, "NA");
	}

	public String getStrInterfaceName() {
		return strInterfaceName;
	}

	public void setStrInterfaceName(String strInterfaceName) {
		this.strInterfaceName = (String) setDefaultValue(strInterfaceName, "NA");
	}

	public String getStrAccountCode() {
		return strAccountCode;
	}

	public void setStrAccountCode(String strAccountCode) {
		this.strAccountCode = (String) setDefaultValue(strAccountCode, "NA");
	}

	public String getStrAccountName() {
		return strAccountName;
	}

	public void setStrAccountName(String strAccountName) {
		this.strAccountName = (String) setDefaultValue(strAccountName, "NA");
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
