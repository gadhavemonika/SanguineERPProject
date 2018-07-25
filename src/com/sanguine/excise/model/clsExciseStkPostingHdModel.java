package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblexcisestockpostinghd")
@IdClass(clsExciseStockPostingHdModel_ID.class)
public class clsExciseStkPostingHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsExciseStkPostingHdModel() {
	}

	public clsExciseStkPostingHdModel(clsExciseStockPostingHdModel_ID objModelID) {
		strPSPCode = objModelID.getStrPSPCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strPSPCode", column = @Column(name = "strPSPCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strPSPCode")
	private String strPSPCode;

	@Column(name = "intId")
	private long intId;

	@Column(name = "strLicenceCode")
	private String strLicenceCode;

	@Transient
	private String strLicenceNo;

	@Column(name = "dtePostingDate")
	private String dtePostingDate;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "dteDateCreated")
	private String dteDateCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	@Column(name = "strClientCode")
	private String strClientCode;

	// Setter-Getter Methods

	public String getStrPSPCode() {
		return strPSPCode;
	}

	public void setStrPSPCode(String strPSPCode) {
		this.strPSPCode = strPSPCode;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrLicenceCode() {
		return strLicenceCode;
	}

	public void setStrLicenceCode(String strLicenceCode) {
		this.strLicenceCode = strLicenceCode;
	}

	public String getStrLicenceNo() {
		return strLicenceNo;
	}

	public void setStrLicenceNo(String strLicenceNo) {
		this.strLicenceNo = strLicenceNo;
	}

	public String getDtePostingDate() {
		return dtePostingDate;
	}

	public void setDtePostingDate(String dtePostingDate) {
		this.dtePostingDate = dtePostingDate;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getDteDateCreated() {
		return dteDateCreated;
	}

	public void setDteDateCreated(String dteDateCreated) {
		this.dteDateCreated = dteDateCreated;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
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
