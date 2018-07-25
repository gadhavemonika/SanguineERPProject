package com.sanguine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblauthorizeuser")
public class clsAuthorizeUserModel {
	@Id
	@Column(name = "strAuthorizeCode")
	private String strAuthorizeCode;

	@Column(name = "strFormName", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strFormName;

	@Column(name = "strTransCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strTransCode;

	@Column(name = "strUserCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserCode;

	@Column(name = "strComments", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strComments;

	@Column(name = "dteAuthorizeDate", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dteAuthorizeDate;

	@Column(name = "strUserCreated", updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserCreated;

	@Column(name = "dteCreatedDate", updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dteCreatedDate;

	@Column(name = "strUserModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserModified;

	@Column(name = "dteLastModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dteLastModified;

	@Column(name = "strClientCode", updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strClientCode;

	@Column(name = "intId", updatable = false, columnDefinition = "BIGINT UNSIGNED")
	private long intId;

	@Column(name = "intLevel", columnDefinition = "INT NOT NULL default '0'")
	private int intLevel;
	

	public String getStrAuthorizeCode() {
		return strAuthorizeCode;
	}

	public void setStrAuthorizeCode(String strAuthorizeCode) {
		this.strAuthorizeCode = strAuthorizeCode;
	}

	public String getStrFormName() {
		return strFormName;
	}

	public void setStrFormName(String strFormName) {
		this.strFormName = strFormName;
	}

	public String getStrTransCode() {
		return strTransCode;
	}

	public void setStrTransCode(String strTransCode) {
		this.strTransCode = strTransCode;
	}

	public String getStrUserCode() {
		return strUserCode;
	}

	public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
	}

	public String getStrComments() {
		return strComments;
	}

	public void setStrComments(String strComments) {
		this.strComments = strComments;
	}

	public String getDteAuthorizeDate() {
		return dteAuthorizeDate;
	}

	public void setDteAuthorizeDate(String dteAuthorizeDate) {
		this.dteAuthorizeDate = dteAuthorizeDate;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getDteCreatedDate() {
		return dteCreatedDate;
	}

	public void setDteCreatedDate(String dteCreatedDate) {
		this.dteCreatedDate = dteCreatedDate;
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

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public int getIntLevel() {
		return intLevel;
	}

	public void setIntLevel(int intLevel) {
		this.intLevel = intLevel;
	}

	
	
}
