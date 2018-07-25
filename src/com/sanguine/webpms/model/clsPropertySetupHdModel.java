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
@Table(name = "tblpropertysetup")
@IdClass(clsPropertySetupModel_ID.class)
public class clsPropertySetupHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsPropertySetupHdModel() {
	}

	public clsPropertySetupHdModel(clsPropertySetupModel_ID objModelID) {
		strPropertyCode = objModelID.getStrPropertyCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strPropertyCode", column = @Column(name = "strPropertyCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "tmeCheckInTime")
	private String tmeCheckInTime;

	@Column(name = "tmeCheckOutTime")
	private String tmeCheckOutTime;
	
	@Column(name = "strRoomLimit")
	private String strRoomLimit;
	
	@Column(name = "strGSTNo")
	private String strGSTNo;
	

	public String getStrGSTNo() {
		return strGSTNo;
	}

	public void setStrGSTNo(String strGSTNo) {
		this.strGSTNo = strGSTNo;
	}

	// SMS setUp Tab Start
	@Column(name = "strSMSProvider", columnDefinition = "VARCHAR(50) default ''")
	private String strSMSProvider;
	@Column(name = "strSMSAPI", columnDefinition = "VARCHAR(300) default ''")
	private String strSMSAPI;

	@Column(name = "strReservationSMSContent")
	private String strReservationSMSContent;
	@Column(name = "strCheckInSMSContent")
	private String strCheckInSMSContent;
	@Column(name = "strAdvAmtSMSContent")
	private String strAdvAmtSMSContent;
	@Column(name = "strCheckOutSMSContent")
	private String strCheckOutSMSContent;

	// SMS setUp Tab End

	// Setter-Getter Methods
	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = (String) setDefaultValue(strPropertyCode, "NA");
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = (String) setDefaultValue(strClientCode, "NA");
	}

	public String getTmeCheckInTime() {
		return tmeCheckInTime;
	}

	public void setTmeCheckInTime(String tmeCheckInTime) {
		this.tmeCheckInTime = tmeCheckInTime;
	}

	public String getTmeCheckOutTime() {
		return tmeCheckOutTime;
	}

	public void setTmeCheckOutTime(String tmeCheckOutTime) {
		this.tmeCheckOutTime = tmeCheckOutTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getStrSMSProvider() {
		return strSMSProvider;
	}

	public void setStrSMSProvider(String strSMSProvider) {
		this.strSMSProvider = strSMSProvider;
	}

	public String getStrSMSAPI() {
		return strSMSAPI;
	}

	public void setStrSMSAPI(String strSMSAPI) {
		this.strSMSAPI = strSMSAPI;
	}

	public String getStrReservationSMSContent() {
		return strReservationSMSContent;
	}

	public void setStrReservationSMSContent(String strReservationSMSContent) {
		this.strReservationSMSContent = strReservationSMSContent;
	}

	public String getStrCheckInSMSContent() {
		return strCheckInSMSContent;
	}

	public void setStrCheckInSMSContent(String strCheckInSMSContent) {
		this.strCheckInSMSContent = strCheckInSMSContent;
	}

	public String getStrAdvAmtSMSContent() {
		return strAdvAmtSMSContent;
	}

	public void setStrAdvAmtSMSContent(String strAdvAmtSMSContent) {
		this.strAdvAmtSMSContent = strAdvAmtSMSContent;
	}

	public String getStrCheckOutSMSContent() {
		return strCheckOutSMSContent;
	}

	public void setStrCheckOutSMSContent(String strCheckOutSMSContent) {
		this.strCheckOutSMSContent = strCheckOutSMSContent;
	}

	public String getStrRoomLimit() {
		return strRoomLimit;
	}

	public void setStrRoomLimit(String strRoomLimit) {
		this.strRoomLimit = (String) setDefaultValue(strRoomLimit, "");
	}

}
