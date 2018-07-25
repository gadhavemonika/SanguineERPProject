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
@Table(name = "tblagentmaster")
@IdClass(clsAgentMasterModel_ID.class)
public class clsAgentMasterHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsAgentMasterHdModel() {
	}

	public clsAgentMasterHdModel(clsAgentMasterModel_ID objModelID) {
		strAgentCode = objModelID.getStrAgentCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strAgentCode", column = @Column(name = "strAgentCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strAgentCode")
	private String strAgentCode;

	@Column(name = "dteFromDate")
	private String dteFromDate;

	@Column(name = "dteToDate")
	private String dteToDate;

	@Column(name = "strDescription")
	private String strDescription;

	@Column(name = "strAgentCommCode")
	private String strAgentCommCode;

	@Column(name = "strCorporateCode")
	private String strCorporateCode;

	@Column(name = "dblAdvToReceive")
	private double dblAdvToReceive;

	@Column(name = "strAddress")
	private String strAddress;

	@Column(name = "strCity")
	private String strCity;

	@Column(name = "strState")
	private String strState;

	@Column(name = "strCountry")
	private String strCountry;

	@Column(name = "strEmailId")
	private String strEmailId;

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

	@Column(name = "lngTelphoneNo")
	private Long lngTelphoneNo;

	@Column(name = "lngMobileNo")
	private Long lngMobileNo;

	@Column(name = "lngFaxNo")
	private Long lngFaxNo;

	// Setter-Getter Methods
	public String getStrAgentCode() {
		return strAgentCode;
	}

	public void setStrAgentCode(String strAgentCode) {
		this.strAgentCode = strAgentCode;
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

	public String getStrDescription() {
		return strDescription;
	}

	public void setStrDescription(String strDescription) {
		this.strDescription = (String) setDefaultValue(strDescription, "NA");
	}

	public String getStrAgentCommCode() {
		return strAgentCommCode;
	}

	public void setStrAgentCommCode(String strAgentCommCode) {
		this.strAgentCommCode = (String) setDefaultValue(strAgentCommCode, "NA");
	}

	public String getStrCorporateCode() {
		return strCorporateCode;
	}

	public void setStrCorporateCode(String strCorporateCode) {
		this.strCorporateCode = (String) setDefaultValue(strCorporateCode, "NA");
	}

	public double getDblAdvToReceive() {
		return dblAdvToReceive;
	}

	public void setDblAdvToReceive(double dblAdvToReceive) {
		this.dblAdvToReceive = (Double) setDefaultValue(dblAdvToReceive, "0.0000");
	}

	public String getStrAddress() {
		return strAddress;
	}

	public void setStrAddress(String strAddress) {
		this.strAddress = (String) setDefaultValue(strAddress, "NA");
	}

	public String getStrCity() {
		return strCity;
	}

	public void setStrCity(String strCity) {
		this.strCity = (String) setDefaultValue(strCity, "NA");
	}

	public String getStrState() {
		return strState;
	}

	public void setStrState(String strState) {
		this.strState = (String) setDefaultValue(strState, "NA");
	}

	public String getStrCountry() {
		return strCountry;
	}

	public void setStrCountry(String strCountry) {
		this.strCountry = (String) setDefaultValue(strCountry, "NA");
	}

	public String getStrEmailId() {
		return strEmailId;
	}

	public void setStrEmailId(String strEmailId) {
		this.strEmailId = (String) setDefaultValue(strEmailId, "NA");
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

	public Long getLngTelphoneNo() {
		return lngTelphoneNo;
	}

	public void setLngTelphoneNo(Long lngTelphoneNo) {
		this.lngTelphoneNo = lngTelphoneNo;
	}

	public Long getLngMobileNo() {
		return lngMobileNo;
	}

	public void setLngMobileNo(Long lngMobileNo) {
		this.lngMobileNo = lngMobileNo;
	}

	public Long getLngFaxNo() {
		return lngFaxNo;
	}

	public void setLngFaxNo(Long lngFaxNo) {
		this.lngFaxNo = lngFaxNo;
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
