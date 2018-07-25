package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblexcisemaster")
@IdClass(clsExciseMasterModel_ID.class)
public class clsExciseMasterModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public clsExciseMasterModel() {
	}

	public clsExciseMasterModel(clsExciseMasterModel_ID clsExciseMasterModel_ID) {
		strExciseCode = clsExciseMasterModel_ID.getStrExciseCode();
		strClientCode = clsExciseMasterModel_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strExciseCode", column = @Column(name = "strExciseCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private String strExciseCode;

	private String strExciseChapterNo;

	private String strSGCode;

	private double dblExcisePercent;

	private String strCessTax;

	private String strRank;

	private String strDesc;

	private String strClientCode;

	private String strUserCreated, strUserModified, dtCreatedDate, dtLastModified;

	// Getter And Setter

	public String getStrExciseCode() {
		return strExciseCode;
	}

	public void setStrExciseCode(String strExciseCode) {
		this.strExciseCode = strExciseCode;
	}

	public String getStrExciseChapterNo() {
		return strExciseChapterNo;
	}

	public void setStrExciseChapterNo(String strExciseChapterNo) {
		this.strExciseChapterNo = strExciseChapterNo;
	}

	public String getStrSGCode() {
		return strSGCode;
	}

	public void setStrSGCode(String strSGCode) {
		this.strSGCode = strSGCode;
	}

	public double getDblExcisePercent() {
		return dblExcisePercent;
	}

	public void setDblExcisePercent(double dblExcisePercent) {
		this.dblExcisePercent = dblExcisePercent;
	}

	public String getStrRank() {
		return strRank;
	}

	public void setStrRank(String strRank) {
		this.strRank = strRank;
	}

	public String getStrDesc() {
		return strDesc;
	}

	public void setStrDesc(String strDesc) {
		this.strDesc = strDesc;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
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

	public String getDtCreatedDate() {
		return dtCreatedDate;
	}

	public void setDtCreatedDate(String dtCreatedDate) {
		this.dtCreatedDate = dtCreatedDate;
	}

	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	public String getStrCessTax() {
		return strCessTax;
	}

	public void setStrCessTax(String strCessTax) {
		this.strCessTax = strCessTax;
	}

}
