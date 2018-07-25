package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblsalesorderbom")
public class clsSalesOrderBOMModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public clsSalesOrderBOMModel() {
	}

	// Variable Declaration

	@Id
	@GeneratedValue
	@Column(name = "intId")
	private Long intId;

	@Column(name = "strSOCode")
	private String strSOCode;

	@Column(name = "strProdCode")
	private String strProdCode;

	@Transient
	private String strProdName;

	@Column(name = "strProcessCode")
	private String strProcessCode;

	@Transient
	private String strProcessName;

	@Column(name = "strParentCode")
	private String strParentCode;

	@Transient
	private String strParentName;

	@Column(name = "strParentProcessCode")
	private String strParentProcessCode;

	@Transient
	private String strParentProcessName;

	@Column(name = "strChildCode")
	private String strChildCode;

	@Transient
	private String strChildName;

	@Column(name = "dblQty")
	private Double dblQty;

	@Column(name = "dblWeight")
	private Double dblWeight;

	@Column(name = "intindex")
	private Long intindex;

	@Column(name = "strRemarks")
	private String strRemarks;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteLastModified")
	private String dteLastModified;

	@Column(name = "dteDateCreated")
	private String dteDateCreated;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Transient
	private Double dblRate;

	@Transient
	private String strChecked;

	// Setter-Getter Methods

	public String getStrChecked() {
		return strChecked;
	}

	public void setStrChecked(String strChecked) {
		this.strChecked = strChecked;
	}

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
		this.intId = intId;
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

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	public String getStrProcessCode() {
		return strProcessCode;
	}

	public void setStrProcessCode(String strProcessCode) {
		this.strProcessCode = strProcessCode;
	}

	public String getStrProcessName() {
		return strProcessName;
	}

	public void setStrProcessName(String strProcessName) {
		this.strProcessName = strProcessName;
	}

	public String getStrParentCode() {
		return strParentCode;
	}

	public void setStrParentCode(String strParentCode) {
		this.strParentCode = strParentCode;
	}

	public String getStrParentName() {
		return strParentName;
	}

	public void setStrParentName(String strParentName) {
		this.strParentName = strParentName;
	}

	public String getStrParentProcessCode() {
		return strParentProcessCode;
	}

	public void setStrParentProcessCode(String strParentProcessCode) {
		this.strParentProcessCode = strParentProcessCode;
	}

	public String getStrParentProcessName() {
		return strParentProcessName;
	}

	public void setStrParentProcessName(String strParentProcessName) {
		this.strParentProcessName = strParentProcessName;
	}

	public String getStrChildCode() {
		return strChildCode;
	}

	public void setStrChildCode(String strChildCode) {
		this.strChildCode = strChildCode;
	}

	public String getStrChildName() {
		return strChildName;
	}

	public void setStrChildName(String strChildName) {
		this.strChildName = strChildName;
	}

	public Double getDblQty() {
		return dblQty;
	}

	public void setDblQty(Double dblQty) {
		this.dblQty = dblQty;
	}

	public Double getDblWeight() {
		return dblWeight;
	}

	public void setDblWeight(Double dblWeight) {
		this.dblWeight = dblWeight;
	}

	public Long getIntindex() {
		return intindex;
	}

	public void setIntindex(Long intindex) {
		this.intindex = intindex;
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
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

	public String getDteLastModified() {
		return dteLastModified;
	}

	public void setDteLastModified(String dteLastModified) {
		this.dteLastModified = dteLastModified;
	}

	public String getDteDateCreated() {
		return dteDateCreated;
	}

	public void setDteDateCreated(String dteDateCreated) {
		this.dteDateCreated = dteDateCreated;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public Double getDblRate() {
		return dblRate;
	}

	public void setDblRate(Double dblRate) {
		this.dblRate = dblRate;
	}

}
