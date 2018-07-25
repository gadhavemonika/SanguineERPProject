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
@Table(name = "tblratemaster")
@IdClass(clsRateMasterModel_ID.class)
public class clsRateMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsRateMasterModel() {
	}

	public clsRateMasterModel(clsRateMasterModel_ID objModelID) {
		strBrandCode = objModelID.getStrBrandCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strBrandCode", column = @Column(name = "strBrandCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strBrandCode")
	private String strBrandCode;

	@Transient
	private String strBrandName;

	@Column(name = "dblRate")
	private Double dblRate;

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

	public String getStrBrandCode() {
		return strBrandCode;
	}

	public void setStrBrandCode(String strBrandCode) {
		this.strBrandCode = strBrandCode;
	}

	public String getStrBrandName() {
		return strBrandName;
	}

	public void setStrBrandName(String strBrandName) {
		this.strBrandName = strBrandName;
	}

	public Double getDblRate() {
		return dblRate;
	}

	public void setDblRate(Double dblRate) {
		this.dblRate = dblRate;
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
