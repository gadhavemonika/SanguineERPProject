package com.sanguine.webpms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblbusinesssource")
public class clsBusinessSourceMasterModel {
	@Id
	@Column(name = "strBusinessSourceCode")
	private String strBusinessSourceCode;

	@Column(name = "strDescription")
	private String strDescription;

	@Column(name = "strInvolvesAmt")
	private String strInvolvesAmt;

	@Column(name = "strInstAccepted")
	private String strInstAccepted;

	@Column(name = "strReqSlipReqd")
	private String strReqSlipReqd;

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

	public String getStrBusinessSourceCode() {
		return strBusinessSourceCode;
	}

	public void setStrBusinessSourceCode(String strBusinessSourceCode) {
		this.strBusinessSourceCode = strBusinessSourceCode;
	}

	public String getStrDescription() {
		return strDescription;
	}

	public void setStrDescription(String strDescription) {
		this.strDescription = strDescription;
	}

	public String getStrInvolvesAmt() {
		return strInvolvesAmt;
	}

	public void setStrInvolvesAmt(String strInvolvesAmt) {
		this.strInvolvesAmt = strInvolvesAmt;
	}

	public String getStrInstAccepted() {
		return strInstAccepted;
	}

	public void setStrInstAccepted(String strInstAccepted) {
		this.strInstAccepted = strInstAccepted;
	}

	public String getStrReqSlipReqd() {
		return strReqSlipReqd;
	}

	public void setStrReqSlipReqd(String strReqSlipReqd) {
		this.strReqSlipReqd = strReqSlipReqd;
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
