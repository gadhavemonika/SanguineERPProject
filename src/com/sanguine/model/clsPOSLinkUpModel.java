package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblposlinkup")
@IdClass(clsPOSLinkUpModel_ID.class)
public class clsPOSLinkUpModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsPOSLinkUpModel() {
	}

	public clsPOSLinkUpModel(clsPOSLinkUpModel_ID objModelID) {
		strPOSItemCode = objModelID.getStrPOSItemCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strPOSItemCode", column = @Column(name = "strPOSItemCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strPOSItemCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	private String strPOSItemCode;

	@Column(name = "strPOSItemName", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	private String strPOSItemName;

	@Column(name = "strWSItemCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	private String strWSItemCode;

	@Column(name = "strWSItemName", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	private String strWSItemName;

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	private String strClientCode;

	@Transient
	private String strSACode;

	// Setter-Getter Methods
	public String getStrPOSItemCode() {
		return strPOSItemCode;
	}

	public void setStrPOSItemCode(String strPOSItemCode) {
		this.strPOSItemCode = (String) setDefaultValue(strPOSItemCode, "NA");
	}

	public String getStrPOSItemName() {
		return strPOSItemName;
	}

	public void setStrPOSItemName(String strPOSItemName) {
		this.strPOSItemName = (String) setDefaultValue(strPOSItemName, "NA");
	}

	public String getStrWSItemCode() {
		return strWSItemCode;
	}

	public void setStrWSItemCode(String strWSItemCode) {
		this.strWSItemCode = (String) setDefaultValue(strWSItemCode, "");
	}

	public String getStrWSItemName() {
		return strWSItemName;
	}

	public void setStrWSItemName(String strWSItemName) {
		this.strWSItemName = (String) setDefaultValue(strWSItemName, "");
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = (String) setDefaultValue(strClientCode, "NA");
	}

	public String getStrSACode() {
		return strSACode;
	}

	public void setStrSACode(String strSACode) {
		this.strSACode = strSACode;
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
