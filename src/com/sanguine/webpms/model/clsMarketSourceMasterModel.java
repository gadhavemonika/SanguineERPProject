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
@Table(name = "tblmarketsource")
@IdClass(clsMarketSourceMasterModel_ID.class)
public class clsMarketSourceMasterModel implements Serializable{
	
	
	public clsMarketSourceMasterModel() {
	}

	public clsMarketSourceMasterModel(clsMarketSourceMasterModel_ID objModelID) {
		strMarketSourceCode = objModelID.getStrMarketSourceCode();
		strClientCode = objModelID.getStrClientCode();
	}
	
	@Id
	@AttributeOverrides({
		@AttributeOverride(name = "strMarketSourceCode", column = @Column(name = "strMarketSourceCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode"))
	})
	

	@Column(name = "strMarketSourceCode")
	private String strMarketSourceCode;
	
	@Column(name = "strDescription")
	private String strDescription;
	
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
	
	@Column(name = "strReqSlipReqd")
	private String strReqSlipReqd;

	public String getStrMarketSourceCode() {
		return strMarketSourceCode;
	}

	public void setStrMarketSourceCode(String strMarketSourceCode) {
		this.strMarketSourceCode = strMarketSourceCode;
	}

	public String getStrDescription() {
		return strDescription;
	}

	public void setStrDescription(String strDescription) {
		this.strDescription = strDescription;
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

	public String getStrReqSlipReqd() {
		return strReqSlipReqd;
	}

	public void setStrReqSlipReqd(String strReqSlipReqd) {
		this.strReqSlipReqd = strReqSlipReqd;
	}


}
