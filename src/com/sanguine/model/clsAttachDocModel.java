package com.sanguine.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "tblattachdocument")
public class clsAttachDocModel {
	@Id
	@GeneratedValue
	@Column(name = "intId")
	private long intId;

	@Column(name = "strTrans", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strTrans;

	@Column(name = "strCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strCode;

	@Column(name = "strActualFileName", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strActualFileName;

	@Column(name = "strChangedFileName", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strChangedFileName;

	@Column(name = "intFileNo", columnDefinition = "BIGINT UNSIGNED")
	private long intFileNo;

	@Column(name = "strUserCreated", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserCreated;

	@Column(name = "dtCreatedDate", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtCreatedDate;

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strClientCode;

	@Column(name = "binContent")
	@Lob
	private Blob binContent;

	@Column(name = "strContentType", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strContentType;

	@Column(name = "strModuleName", columnDefinition = "VARCHAR(50) NOT NULL default ''")
	private String strModuleName;

	public String getStrTrans() {
		return strTrans;
	}

	public void setStrTrans(String strTrans) {
		this.strTrans = strTrans;
	}

	public String getStrCode() {
		return strCode;
	}

	public void setStrCode(String strCode) {
		this.strCode = strCode;
	}

	public String getStrActualFileName() {
		return strActualFileName;
	}

	public void setStrActualFileName(String strActualFileName) {
		this.strActualFileName = strActualFileName;
	}

	public String getStrChangedFileName() {
		return strChangedFileName;
	}

	public void setStrChangedFileName(String strChangedFileName) {
		this.strChangedFileName = strChangedFileName;
	}

	public long getIntFileNo() {
		return intFileNo;
	}

	public void setIntFileNo(long intFileNo) {
		this.intFileNo = intFileNo;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getDtCreatedDate() {
		return dtCreatedDate;
	}

	public void setDtCreatedDate(String dtCreatedDate) {
		this.dtCreatedDate = dtCreatedDate;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public Blob getBinContent() {
		return binContent;
	}

	public void setBinContent(Blob binContent) {
		this.binContent = binContent;
	}

	public String getStrContentType() {
		return strContentType;
	}

	public void setStrContentType(String strContentType) {
		this.strContentType = strContentType;
	}

	public String getStrModuleName() {
		return strModuleName;
	}

	public void setStrModuleName(String strModuleName) {
		this.strModuleName = strModuleName;
	}

}
