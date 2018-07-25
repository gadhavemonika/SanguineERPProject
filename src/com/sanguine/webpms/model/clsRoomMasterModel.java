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
@Table(name = "tblroom")
@IdClass(clsRoomMasterModel_ID.class)
public class clsRoomMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsRoomMasterModel() {
	}

	public clsRoomMasterModel(clsRoomMasterModel_ID objModelID) {
		strRoomCode = objModelID.getStrRoomCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strRoomCode", column = @Column(name = "strRoomCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strRoomCode")
	private String strRoomCode;

	@Column(name = "strRoomDesc")
	private String strRoomDesc;

	@Column(name = "strRoomTypeCode")
	private String strRoomTypeCode;

	@Column(name = "strFloorCode")
	private String strFloorCode;

	@Column(name = "strBedType")
	private String strBedType;

	@Column(name = "strFurniture")
	private String strFurniture;

	@Column(name = "strExtraBedCode")
	private String strExtraBedCode;

	@Column(name = "strUpholstery")
	private String strUpholstery;

	@Column(name = "strLocation")
	private String strLocation;

	@Column(name = "strBathTypeCode")
	private String strBathTypeCode;

	@Column(name = "strColorScheme")
	private String strColorScheme;

	@Column(name = "strPolishType")
	private String strPolishType;

	@Column(name = "strGuestAmenities")
	private String strGuestAmenities;

	@Column(name = "strInterConnectRooms")
	private String strInterConnectRooms;

	@Column(name = "strProvisionForSmoking")
	private String strProvisionForSmokingYN;

	@Column(name = "strDeactivate")
	private String strDeactiveYN;

	@Column(name = "strUserCreated", updatable = false, nullable = false)
	private String strUserCreated;

	@Column(name = "dteDateCreated", updatable = false, nullable = false)
	private String dteDateCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strStatus")
	private String strStatus;

	@Column(name = "strAccountCode", columnDefinition = "VARCHAR(20) NOT NULL default ''")
	private String strAccountCode;

	private String strRoomTypeDesc;
	
	
	// Setter-Getter Methods

	public String getStrRoomTypeDesc() {
		return strRoomTypeDesc;
	}

	public void setStrRoomTypeDesc(String strRoomTypeDesc) {
		this.strRoomTypeDesc = strRoomTypeDesc;
	}

	public String getStrRoomCode() {
		return strRoomCode;
	}

	public void setStrRoomCode(String strRoomCode) {
		this.strRoomCode = (String) setDefaultValue(strRoomCode, "NA");
	}

	public String getStrRoomDesc() {
		return strRoomDesc;
	}

	public void setStrRoomDesc(String strRoomDesc) {
		this.strRoomDesc = (String) setDefaultValue(strRoomDesc, "NA");
	}

	public String getStrRoomTypeCode() {
		return strRoomTypeCode;
	}

	public void setStrRoomTypeCode(String strRoomTypeCode) {
		this.strRoomTypeCode = strRoomTypeCode;
	}

	public String getStrFloorCode() {
		return strFloorCode;
	}

	public void setStrFloorCode(String strFloorCode) {
		this.strFloorCode = (String) setDefaultValue(strFloorCode, "NA");
	}

	public String getStrBedType() {
		return strBedType;
	}

	public void setStrBedType(String strBedType) {
		this.strBedType = (String) setDefaultValue(strBedType, "NA");
	}

	public String getStrFurniture() {
		return strFurniture;
	}

	public void setStrFurniture(String strFurniture) {
		this.strFurniture = (String) setDefaultValue(strFurniture, "NA");
	}

	public String getStrExtraBedCode() {
		return strExtraBedCode;
	}

	public void setStrExtraBedCode(String strExtraBedCode) {
		this.strExtraBedCode = strExtraBedCode;
	}

	public String getStrUpholstery() {
		return strUpholstery;
	}

	public void setStrUpholstery(String strUpholstery) {
		this.strUpholstery = (String) setDefaultValue(strUpholstery, "NA");
	}

	public String getStrLocation() {
		return strLocation;
	}

	public void setStrLocation(String strLocation) {
		this.strLocation = (String) setDefaultValue(strLocation, "NA");
	}

	public String getStrBathTypeCode() {
		return strBathTypeCode;
	}

	public void setStrBathTypeCode(String strBathTypeCode) {
		this.strBathTypeCode = strBathTypeCode;
	}

	public String getStrColorScheme() {
		return strColorScheme;
	}

	public void setStrColorScheme(String strColorScheme) {
		this.strColorScheme = strColorScheme;
	}

	public String getStrPolishType() {
		return strPolishType;
	}

	public void setStrPolishType(String strPolishType) {
		this.strPolishType = (String) setDefaultValue(strPolishType, "NA");
	}

	public String getStrGuestAmenities() {
		return strGuestAmenities;
	}

	public void setStrGuestAmenities(String strGuestAmenities) {
		this.strGuestAmenities = (String) setDefaultValue(strGuestAmenities, "NA");
	}

	public String getStrInterConnectRooms() {
		return strInterConnectRooms;
	}

	public void setStrInterConnectRooms(String strInterConnectRooms) {
		this.strInterConnectRooms = (String) setDefaultValue(strInterConnectRooms, "NA");
	}

	public String getStrProvisionForSmokingYN() {
		return strProvisionForSmokingYN;
	}

	public void setStrProvisionForSmokingYN(String strProvisionForSmokingYN) {
		this.strProvisionForSmokingYN = (String) setDefaultValue(strProvisionForSmokingYN, "N");
	}

	public String getStrDeactiveYN() {
		return strDeactiveYN;
	}

	public void setStrDeactiveYN(String strDeactiveYN) {
		this.strDeactiveYN = (String) setDefaultValue(strDeactiveYN, "N");
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = (String) setDefaultValue(strUserCreated, "NA");
	}

	public String getDteDateCreated() {
		return dteDateCreated;
	}

	public void setDteDateCreated(String dteDateCreated) {
		this.dteDateCreated = dteDateCreated;
	}

	public String getStrUserEdited() {
		return strUserEdited;
	}

	public void setStrUserEdited(String strUserEdited) {
		this.strUserEdited = (String) setDefaultValue(strUserEdited, "NA");
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

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public String getStrAccountCode() {
		return strAccountCode;
	}

	public void setStrAccountCode(String strAccountCode) {
		this.strAccountCode = (String) setDefaultValue(strAccountCode, "NA");
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
