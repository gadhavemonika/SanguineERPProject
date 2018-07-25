package com.sanguine.crm.model;

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
@Table(name = "tbljoborderhd")
@IdClass(clsJobOrderModel_ID.class)
public class clsJobOrderModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsJobOrderModel() {
	}

	public clsJobOrderModel(clsJobOrderModel_ID objModelID) {
		strJOCode = objModelID.getStrJOCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strJOCode", column = @Column(name = "strJOCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strJOCode")
	private String strJOCode;

	@Column(name = "intId")
	private Long intId;

	@Column(name = "dteJODate")
	private String dteJODate;

	@Column(name = "strSOCode")
	private String strSOCode;

	@Column(name = "strProdCode")
	private String strProdCode;

	@Transient
	private String strProdName;

	@Column(name = "dblQty")
	private Double dblQty;

	@Column(name = "strParentJOCode")
	private String strParentJOCode;

	@Column(name = "strStatus")
	private String strStatus;

	@Column(name = "strAuthorise")
	private String strAuthorise;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "dteDateCreated")
	private String dteDateCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteLastModified")
	private String dteLastModified;

	@Column(name = "strClientCode")
	private String strClientCode;

	// Setter-Getter Methods

	public String getStrJOCode() {
		return strJOCode;
	}

	public void setStrJOCode(String strJOCode) {
		this.strJOCode = strJOCode;
	}

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
		this.intId = intId;
	}

	public String getDteJODate() {
		return dteJODate;
	}

	public void setDteJODate(String dteJODate) {
		this.dteJODate = dteJODate;
	}

	public String getStrSOCode() {
		return strSOCode;
	}

	public void setStrSOCode(String strSOCode) {
		this.strSOCode = strSOCode;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public Double getDblQty() {
		return dblQty;
	}

	public void setDblQty(Double dblQty) {
		this.dblQty = dblQty;
	}

	public String getStrParentJOCode() {
		return strParentJOCode;
	}

	public void setStrParentJOCode(String strParentJOCode) {
		this.strParentJOCode = strParentJOCode;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
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

	public String getDteLastModified() {
		return dteLastModified;
	}

	public void setDteLastModified(String dteLastModified) {
		this.dteLastModified = dteLastModified;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

}
