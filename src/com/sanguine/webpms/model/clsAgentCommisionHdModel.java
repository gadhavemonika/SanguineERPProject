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
@Table(name = "tblagentcommision")
@IdClass(clsAgentCommisionModel_ID.class)
public class clsAgentCommisionHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsAgentCommisionHdModel() {
	}

	public clsAgentCommisionHdModel(clsAgentCommisionModel_ID objModelID) {
		strAgentCommCode = objModelID.getStrAgentCommCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strAgentCommCode", column = @Column(name = "strAgentCommCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strAgentCommCode")
	private String strAgentCommCode;

	@Column(name = "dteFromDate")
	private String dteFromDate;

	@Column(name = "dteToDate")
	private String dteToDate;

	@Column(name = "strCalculatedOn")
	private String strCalculatedOn;

	@Column(name = "strCommisionPaid")
	private String strCommisionPaid;

	@Column(name = "strCommisionOn")
	private String strCommisionOn;

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
	public String getStrAgentCommCode() {
		return strAgentCommCode;
	}

	public void setStrAgentCommCode(String strAgentCommCode) {
		this.strAgentCommCode = (String) setDefaultValue(strAgentCommCode, "NA");
	}

	public String getDteFromDate() {
		return dteFromDate;
	}

	public void setDteFromDate(String dteFromDate) {
		this.dteFromDate = dteFromDate;
	}

	public String getDteToDate() {
		return dteToDate;
	}

	public void setDteToDate(String dteToDate) {
		this.dteToDate = dteToDate;
	}

	public String getStrCalculatedOn() {
		return strCalculatedOn;
	}

	public void setStrCalculatedOn(String strCalculatedOn) {
		this.strCalculatedOn = (String) setDefaultValue(strCalculatedOn, "NA");
	}

	public String getStrCommisionPaid() {
		return strCommisionPaid;
	}

	public void setStrCommisionPaid(String strCommisionPaid) {
		this.strCommisionPaid = (String) setDefaultValue(strCommisionPaid, "NA");
	}

	public String getStrCommisionOn() {
		return strCommisionOn;
	}

	public void setStrCommisionOn(String strCommisionOn) {
		this.strCommisionOn = (String) setDefaultValue(strCommisionOn, "NA");
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
