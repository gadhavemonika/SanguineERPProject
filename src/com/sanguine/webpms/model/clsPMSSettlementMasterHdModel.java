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
@Table(name = "tblsettlementmaster")
@IdClass(clsPMSSettlementMasterModel_ID.class)
public class clsPMSSettlementMasterHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsPMSSettlementMasterHdModel() {
	}

	public clsPMSSettlementMasterHdModel(clsPMSSettlementMasterModel_ID objModelID) {
		strSettlementCode = objModelID.getStrSettlementCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strSettlementCode", column = @Column(name = "strSettlementCode")) })
	// Variable Declaration
	@Column(name = "strSettlementCode")
	private String strSettlementCode;

	@Column(name = "strSettlementDesc")
	private String strSettlementDesc;

	@Column(name = "strSettlementType")
	private String strSettlementType;

	@Column(name = "strApplicable")
	private String strApplicable;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strAccountCode", columnDefinition = "VARCHAR(20) NOT NULL default ''")
	private String strAccountCode;

	// Setter-Getter Methods
	public String getStrSettlementCode() {
		return strSettlementCode;
	}

	public void setStrSettlementCode(String strSettlementCode) {
		this.strSettlementCode = (String) setDefaultValue(strSettlementCode, "");
	}

	public String getStrSettlementDesc() {
		return strSettlementDesc;
	}

	public void setStrSettlementDesc(String strSettlementDesc) {
		this.strSettlementDesc = (String) setDefaultValue(strSettlementDesc, "");
	}

	public String getStrSettlementType() {
		return strSettlementType;
	}

	public void setStrSettlementType(String strSettlementType) {
		this.strSettlementType = (String) setDefaultValue(strSettlementType, "");
	}

	public String getStrApplicable() {
		return strApplicable;
	}

	public void setStrApplicable(String strApplicable) {
		this.strApplicable = (String) setDefaultValue(strApplicable, "");
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = (String) setDefaultValue(strClientCode, "");
	}

	public String getStrAccountCode() {
		return strAccountCode;
	}

	public void setStrAccountCode(String strAccountCode) {
		this.strAccountCode = (String) setDefaultValue(strAccountCode, "");
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
