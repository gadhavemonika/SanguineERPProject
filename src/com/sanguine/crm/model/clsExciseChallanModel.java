package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblexcisechallan")
@IdClass(clsExciseChallanModel_ID.class)
public class clsExciseChallanModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsExciseChallanModel() {
	}

	public clsExciseChallanModel(clsExciseChallanModel_ID objModelID) {
		strECCode = objModelID.getStrECCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strECCode", column = @Column(name = "strECCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strECCode")
	private String strECCode;

	@Column(name = "intId")
	private Long intId;

	@Column(name = "strScode")
	private String strScode;

	@Column(name = "strAgainst")
	private String strAgainst;

	@Column(name = "strChallanType")
	private String strChallanType;

	@Column(name = "dteDispatchDate")
	private String dteDispatchDate;

	@Column(name = "dteDispatchTime")
	private String dteDispatchTime;

	@Column(name = "dteECDate")
	private String dteECDate;

	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "strProcessCode")
	private String strProcessCode;

	@Column(name = "dblQty")
	private Double dblQty;

	@Column(name = "dblUnitPrice")
	private Double dblUnitPrice;

	@Column(name = "strTariff")
	private String strTariff;

	@Column(name = "strDuration")
	private String strDuration;

	@Column(name = "strIdentityMarks")
	private String strIdentityMarks;

	@Column(name = "strGRChallanCode")
	private String strGRChallanCode;

	@Column(name = "dteGRChallanDate")
	private String dteGRChallanDate;

	@Column(name = "strCurrency")
	private String strCurrency;

	@Column(name = "dteDateCreated")
	private String dteDateCreated;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "strClientCode")
	private String strClientCode;

	// Setter-Getter Methods

	public String getStrECCode() {
		return strECCode;
	}

	public void setStrECCode(String strECCode) {
		this.strECCode = strECCode;
	}

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
		this.intId = intId;
	}

	public String getStrScode() {
		return strScode;
	}

	public void setStrScode(String strScode) {
		this.strScode = strScode;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	public String getStrChallanType() {
		return strChallanType;
	}

	public void setStrChallanType(String strChallanType) {
		this.strChallanType = strChallanType;
	}

	public String getDteDispatchDate() {
		return dteDispatchDate;
	}

	public void setDteDispatchDate(String dteDispatchDate) {
		this.dteDispatchDate = dteDispatchDate;
	}

	public String getDteDispatchTime() {
		return dteDispatchTime;
	}

	public void setDteDispatchTime(String dteDispatchTime) {
		this.dteDispatchTime = dteDispatchTime;
	}

	public String getDteECDate() {
		return dteECDate;
	}

	public void setDteECDate(String dteECDate) {
		this.dteECDate = dteECDate;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrProcessCode() {
		return strProcessCode;
	}

	public void setStrProcessCode(String strProcessCode) {
		this.strProcessCode = strProcessCode;
	}

	public Double getDblQty() {
		return dblQty;
	}

	public void setDblQty(Double dblQty) {
		this.dblQty = dblQty;
	}

	public Double getDblUnitPrice() {
		return dblUnitPrice;
	}

	public void setDblUnitPrice(Double dblUnitPrice) {
		this.dblUnitPrice = dblUnitPrice;
	}

	public String getStrTariff() {
		return strTariff;
	}

	public void setStrTariff(String strTariff) {
		this.strTariff = strTariff;
	}

	public String getStrDuration() {
		return strDuration;
	}

	public void setStrDuration(String strDuration) {
		this.strDuration = strDuration;
	}

	public String getStrIdentityMarks() {
		return strIdentityMarks;
	}

	public void setStrIdentityMarks(String strIdentityMarks) {
		this.strIdentityMarks = strIdentityMarks;
	}

	public String getDteGRChallanDate() {
		return dteGRChallanDate;
	}

	public void setDteGRChallanDate(String dteGRChallanDate) {
		this.dteGRChallanDate = dteGRChallanDate;
	}

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
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

	public String getStrGRChallanCode() {
		return strGRChallanCode;
	}

	public void setStrGRChallanCode(String strGRChallanCode) {
		this.strGRChallanCode = strGRChallanCode;
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

}
