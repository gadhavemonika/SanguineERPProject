package com.sanguine.webpms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import org.hibernate.annotations.CollectionOfElements;

@Entity
@Table(name = "tblcheckinhd")
@IdClass(clsCheckInModel_ID.class)
public class clsCheckInHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsCheckInHdModel() {
	}

	public clsCheckInHdModel(clsCheckInModel_ID objModelID) {
		strCheckInNo = objModelID.getStrCheckInNo();
		strClientCode = objModelID.getStrClientCode();
	}

	@CollectionOfElements(fetch = FetchType.LAZY)
	@JoinTable(name = "tblcheckindtl", joinColumns = { @JoinColumn(name = "strCheckInNo"), @JoinColumn(name = "strClientCode") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strCheckInNo", column = @Column(name = "strCheckInNo")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	List<clsCheckInDtl> listCheckInDtl = new ArrayList<clsCheckInDtl>();

	// Variable Declaration
	@Column(name = "strCheckInNo")
	private String strCheckInNo;

	@Column(name = "strRegistrationNo")
	private String strRegistrationNo;

	@Column(name = "strType")
	private String strType;

	@Column(name = "strReservationNo")
	private String strReservationNo;

	@Column(name = "strWalkInNo")
	private String strWalkInNo;

	@Column(name = "dteArrivalDate")
	private String dteArrivalDate;

	@Column(name = "dteDepartureDate")
	private String dteDepartureDate;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dteDateCreated", updatable = false)
	private String dteDateCreated;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "tmeArrivalTime")
	private String tmeArrivalTime;

	@Column(name = "tmeDepartureTime")
	private String tmeDepartureTime;

	@Column(name = "dteCheckInDate")
	private String dteCheckInDate;

	@Column(name = "strRoomNo")
	private String strRoomNo;

	@Column(name = "strExtraBedCode")
	private String strExtraBedCode;

	@Column(name = "intNoOfAdults")
	private int intNoOfAdults;

	@Column(name = "intNoOfChild")
	private int intNoOfChild;

	
	@Column(name = "strNoPostFolio")
	private String strNoPostFolio;
	
	@Column(name="strRemarks")
	private String strRemarks;
	
	@Column(name="strReasonCode")
	private String strReasonCode;

	
	public List<clsCheckInDtl> getListCheckInDtl() {
		return listCheckInDtl;
	}

	public void setListCheckInDtl(List<clsCheckInDtl> listCheckInDtl) {
		this.listCheckInDtl = listCheckInDtl;
	}

	// Setter-Getter Methods
	public String getStrCheckInNo() {
		return strCheckInNo;
	}

	public void setStrCheckInNo(String strCheckInNo) {
		this.strCheckInNo = (String) setDefaultValue(strCheckInNo, "");
	}

	public String getStrRegistrationNo() {
		return strRegistrationNo;
	}

	public void setStrRegistrationNo(String strRegistrationNo) {
		this.strRegistrationNo = (String) setDefaultValue(strRegistrationNo, "");
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = (String) setDefaultValue(strType, "");
	}

	public String getStrReservationNo() {
		return strReservationNo;
	}

	public void setStrReservationNo(String strReservationNo) {
		this.strReservationNo = (String) setDefaultValue(strReservationNo, "");
	}

	public String getStrWalkInNo() {
		return strWalkInNo;
	}

	public void setStrWalkInNo(String strWalkInNo) {
		this.strWalkInNo = (String) setDefaultValue(strWalkInNo, "");
	}

	public String getDteArrivalDate() {
		return dteArrivalDate;
	}

	public void setDteArrivalDate(String dteArrivalDate) {
		this.dteArrivalDate = dteArrivalDate;
	}

	public String getDteDepartureDate() {
		return dteDepartureDate;
	}

	public void setDteDepartureDate(String dteDepartureDate) {
		this.dteDepartureDate = dteDepartureDate;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = (String) setDefaultValue(strUserCreated, "");
	}

	public String getStrUserEdited() {
		return strUserEdited;
	}

	public void setStrUserEdited(String strUserEdited) {
		this.strUserEdited = (String) setDefaultValue(strUserEdited, "");
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
		this.strClientCode = (String) setDefaultValue(strClientCode, "");
	}

	public String getTmeArrivalTime() {
		return tmeArrivalTime;
	}

	public void setTmeArrivalTime(String tmeArrivalTime) {
		this.tmeArrivalTime = tmeArrivalTime;
	}

	public String getTmeDepartureTime() {
		return tmeDepartureTime;
	}

	public void setTmeDepartureTime(String tmeDepartureTime) {
		this.tmeDepartureTime = tmeDepartureTime;
	}

	public String getDteCheckInDate() {
		return dteCheckInDate;
	}

	public void setDteCheckInDate(String dteCheckInDate) {
		this.dteCheckInDate = dteCheckInDate;
	}

	public String getStrRoomNo() {
		return strRoomNo;
	}

	public void setStrRoomNo(String strRoomNo) {
		this.strRoomNo = strRoomNo;
	}

	public String getStrExtraBedCode() {
		return strExtraBedCode;
	}

	public void setStrExtraBedCode(String strExtraBedCode) {
		this.strExtraBedCode = strExtraBedCode;
	}

	public int getIntNoOfAdults() {
		return intNoOfAdults;
	}

	public void setIntNoOfAdults(int intNoOfAdults) {
		this.intNoOfAdults = intNoOfAdults;
	}

	public int getIntNoOfChild() {
		return intNoOfChild;
	}

	public void setIntNoOfChild(int intNoOfChild) {
		this.intNoOfChild = intNoOfChild;
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

	public String getStrNoPostFolio() {
		return strNoPostFolio;
	}

	public void setStrNoPostFolio(String strNoPostFolio) {
		this.strNoPostFolio =(String) setDefaultValue(strNoPostFolio, "N"); ;
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
