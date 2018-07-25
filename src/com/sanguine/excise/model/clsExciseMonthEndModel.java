package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblmonthend")
@IdClass(clsExciseMonthEndModel_ID.class)
public class clsExciseMonthEndModel implements Serializable {

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public clsExciseMonthEndModel() {
	}

	public clsExciseMonthEndModel(clsExciseMonthEndModel_ID clsMonthEndModel_ID) {
		strMonthEnd = clsMonthEndModel_ID.getStrMonthEnd();
		strLocCode = clsMonthEndModel_ID.getStrLocCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strMonthEnd", column = @Column(name = "strMonthEnd")), @AttributeOverride(name = "strLocCode", column = @Column(name = "strLocCode")) })
	@Column(name = "strMonthEnd", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strMonthEnd;

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

	public String getStrMonthEnd() {
		return strMonthEnd;
	}

	public void setStrMonthEnd(String strMonthEnd) {
		this.strMonthEnd = strMonthEnd;
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
