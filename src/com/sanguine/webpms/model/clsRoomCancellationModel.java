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
@Table(name = "tblroomcancelation")
@IdClass(clsRoomCancellationModel_ID.class)
public class clsRoomCancellationModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsRoomCancellationModel() {
	}

	public clsRoomCancellationModel(clsRoomCancellationModel_ID objModelID) {
		strReservationNo = objModelID.getStrReservationNo();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strReservationNo", column = @Column(name = "strReservationNo")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strReservationNo")
	private String strReservationNo;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "dteArrivalFromDate")
	private String dteArrivalFromDate;

	@Column(name = "dteArrivalToDate")
	private String dteArrivalToDate;

	@Column(name = "strGuestCode")
	private String strGuestCode;

	@Column(name = "dteCancelDate")
	private String dteCancelDate;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dteDateCreated", updatable = false)
	private String dteDateCreated;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;
	
	@Column(name = "strRemarks")
	private String strRemarks;
	
	@Column(name = "strReasonCode")
	private String strReasonCode;
	

	// Setter-Getter Methods
	public String getStrReservationNo() {
		return strReservationNo;
	}

	public void setStrReservationNo(String strReservationNo) {
		this.strReservationNo = (String) setDefaultValue(strReservationNo, "NA");
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = (String) setDefaultValue(strPropertyCode, "NA");
	}

	public String getDteArrivalFromDate() {
		return dteArrivalFromDate;
	}

	public void setDteArrivalFromDate(String dteArrivalFromDate) {
		this.dteArrivalFromDate = dteArrivalFromDate;
	}

	public String getDteArrivalToDate() {
		return dteArrivalToDate;
	}

	public void setDteArrivalToDate(String dteArrivalToDate) {
		this.dteArrivalToDate = dteArrivalToDate;
	}

	public String getStrGuestCode() {
		return strGuestCode;
	}

	public void setStrGuestCode(String strGuestCode) {
		this.strGuestCode = (String) setDefaultValue(strGuestCode, "NA");
	}

	public String getDteCancelDate() {
		return dteCancelDate;
	}

	public void setDteCancelDate(String dteCancelDate) {
		this.dteCancelDate = dteCancelDate;
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

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}

	public String getStrReasonCode() {
		return strReasonCode;
	}

	public void setStrReasonCode(String strReasonCode) {
		this.strReasonCode = strReasonCode;
	}

}
