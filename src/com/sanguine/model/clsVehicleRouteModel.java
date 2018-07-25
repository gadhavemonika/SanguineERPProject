package com.sanguine.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblvehroutedtl")
public class clsVehicleRouteModel {

	@Column(name = "strVehCode")
	private String strVehCode;

	@Transient
	private String strVehNo;

	@Column(name = "strRouteCode")
	private String strRouteCode;

	@Transient
	private String strRouteName;

	@Column(name = "dtFromDate")
	private String dtFromDate;

	@Column(name = "dtToDate")
	private String dtToDate;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "dtLastModified")
	private String dtLastModified;

	@Column(name = "dtCreatedDate", updatable = false)
	private String dtCreatedDate;

	@Id
	@Column(name = "intId")
	private long intId;

	public String getStrVehCode() {
		return strVehCode;
	}

	public void setStrVehCode(String strVehCode) {
		this.strVehCode = strVehCode;
	}

	public String getStrVehNo() {
		return strVehNo;
	}

	public void setStrVehNo(String strVehNo) {
		this.strVehNo = strVehNo;
	}

	public String getStrRouteCode() {
		return strRouteCode;
	}

	public void setStrRouteCode(String strRouteCode) {
		this.strRouteCode = strRouteCode;
	}

	public String getStrRouteName() {
		return strRouteName;
	}

	public void setStrRouteName(String strRouteName) {
		this.strRouteName = strRouteName;
	}

	public String getDtFromDate() {
		return dtFromDate;
	}

	public void setDtFromDate(String dtFromDate) {
		this.dtFromDate = dtFromDate;
	}

	public String getDtToDate() {
		return dtToDate;
	}

	public void setDtToDate(String dtToDate) {
		this.dtToDate = dtToDate;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	public String getDtCreatedDate() {
		return dtCreatedDate;
	}

	public void setDtCreatedDate(String dtCreatedDate) {
		this.dtCreatedDate = dtCreatedDate;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

}
