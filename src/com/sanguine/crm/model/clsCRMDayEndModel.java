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
@Table(name = "tbldayEnd")
@IdClass(clsCRMDayEndModel_ID.class)
public class clsCRMDayEndModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public clsCRMDayEndModel() {
	}

	public clsCRMDayEndModel(clsCRMDayEndModel_ID clsCRMDayEndModel_ID) {
		strDayEnd = clsCRMDayEndModel_ID.getStrDayEnd();
		strLocCode = clsCRMDayEndModel_ID.getStrLocCode();
		strClientCode = clsCRMDayEndModel_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strDayEnd", column = @Column(name = "strDayEnd")), @AttributeOverride(name = "strLocCode", column = @Column(name = "strLocCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strDayEnd", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strDayEnd;

	@Column(name = "strLocCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strLocCode;

	@Column(name = "strUserCreated", nullable = false, updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserCreated;

	@Column(name = "strUserModified", columnDefinition = "VARCHAR(25) NOT NULL default ''")
	private String strUserModified;

	@Column(name = "dtDateCreated", nullable = false, updatable = false, columnDefinition = "VARCHAR(25) NOT NULL default ''")
	private String dtDateCreated;

	@Column(name = "dtLastModified", columnDefinition = "VARCHAR(25) NOT NULL default ''")
	private String dtLastModified;

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(25) NOT NULL default ''")
	private String strClientCode;

	public String getStrDayEnd() {
		return strDayEnd;
	}

	public void setStrDayEnd(String strDayEnd) {
		this.strDayEnd = strDayEnd;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
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

	public String getDtDateCreated() {
		return dtDateCreated;
	}

	public void setDtDateCreated(String dtDateCreated) {
		this.dtDateCreated = dtDateCreated;
	}

	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

}
