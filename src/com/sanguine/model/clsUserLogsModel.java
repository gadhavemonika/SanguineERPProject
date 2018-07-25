package com.sanguine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbluserlogs")
public class clsUserLogsModel {
	@GeneratedValue
	@Id
	@Column(name = "intId", columnDefinition = "BIGINT(20) NOT NULL")
	private long intId;

	@Column(name = "strUserCode", columnDefinition = "VARCHAR(20) NOT NULL default ''")
	private String strUserCode;

	@Column(name = "dteLoginDate", columnDefinition = "VARCHAR(20) NOT NULL default ''")
	private String dteLoginDate;

	@Column(name = "tmeLoginTime", columnDefinition = "VARCHAR(100) NOT NULL default ''")
	private String tmeLoginTime;

	@Column(name = "strLoggedInProperty", columnDefinition = "VARCHAR(20) NOT NULL default ''")
	private String strLoggedInProperty;

	@Column(name = "strLoggedInLocation", columnDefinition = "VARCHAR(20) NOT NULL default ''")
	private String strLoggedInLocation;

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(20) NOT NULL")
	private String strClientCode;

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrUserCode() {
		return strUserCode;
	}

	public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
	}

	public String getDteLoginDate() {
		return dteLoginDate;
	}

	public void setDteLoginDate(String dteLoginDate) {
		this.dteLoginDate = dteLoginDate;
	}

	public String getTmeLoginTime() {
		return tmeLoginTime;
	}

	public void setTmeLoginTime(String tmeLoginTime) {
		this.tmeLoginTime = tmeLoginTime;
	}

	public String getStrLoggedInProperty() {
		return strLoggedInProperty;
	}

	public void setStrLoggedInProperty(String strLoggedInProperty) {
		this.strLoggedInProperty = strLoggedInProperty;
	}

	public String getStrLoggedInLocation() {
		return strLoggedInLocation;
	}

	public void setStrLoggedInLocation(String strLoggedInLocation) {
		this.strLoggedInLocation = strLoggedInLocation;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}
}
