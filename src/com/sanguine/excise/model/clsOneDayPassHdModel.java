package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblonedaypass")
public class clsOneDayPassHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsOneDayPassHdModel() {
	}

	// Variable Declaration
	@Id
	@GeneratedValue
	@Column(name = "intId")
	private Long intId;

	@Column(name = "dteDate")
	private String dteDate;

	@Column(name = "intFromNo")
	private Long intFromNo;

	@Column(name = "intToNo")
	private Long intToNo;

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

	// Getter Setter Method

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
		this.intId = intId;
	}

	public String getDteDate() {
		return dteDate;
	}

	public void setDteDate(String dteDate) {
		this.dteDate = dteDate;
	}

	public Long getIntFromNo() {
		return intFromNo;
	}

	public void setIntFromNo(Long intFromNo) {
		this.intFromNo = intFromNo;
	}

	public Long getIntToNo() {
		return intToNo;
	}

	public void setIntToNo(Long intToNo) {
		this.intToNo = intToNo;
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

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
