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
@Table(name = "tblbookingtype")
@IdClass(clsBookingTypeModel_ID.class)
public class clsBookingTypeHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsBookingTypeHdModel() {
	}

	public clsBookingTypeHdModel(clsBookingTypeModel_ID objModelID) {
		strBookingTypeCode = objModelID.getStrBookingTypeCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strBookingTypeCode", column = @Column(name = "strBookingTypeCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strBookingTypeCode")
	private String strBookingTypeCode;

	@Column(name = "strBookingTypeDesc")
	private String strBookingTypeDesc;

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
	public String getStrBookingTypeCode() {
		return strBookingTypeCode;
	}

	public void setStrBookingTypeCode(String strBookingTypeCode) {
		this.strBookingTypeCode = (String) setDefaultValue(strBookingTypeCode, "NA");
	}

	public String getStrBookingTypeDesc() {
		return strBookingTypeDesc;
	}

	public void setStrBookingTypeDesc(String strBookingTypeDesc) {
		this.strBookingTypeDesc = (String) setDefaultValue(strBookingTypeDesc, "NA");
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
