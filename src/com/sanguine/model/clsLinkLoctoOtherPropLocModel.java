package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "tbllinklocotherproploc")
@IdClass(clsLinkLoctoOtherPropLocModel_ID.class)
public class clsLinkLoctoOtherPropLocModel implements Serializable {

	public clsLinkLoctoOtherPropLocModel() {
	}

	public clsLinkLoctoOtherPropLocModel(clsLinkLoctoOtherPropLocModel_ID model_ID) {
		strPropertyCode = model_ID.getStrPropertyCode();
		strLocCode = model_ID.getStrLocCode();
		strToLoc = model_ID.getStrToLoc();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strPropertyCode", column = @Column(name = "strPropertyCode")), @AttributeOverride(name = "strLocCode", column = @Column(name = "strLocCode")), @AttributeOverride(name = "strToLoc", column = @Column(name = "strToLoc")) })
	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "strLocCode")
	private String strLocCode;

	@Column(name = "strToLoc")
	private String strToLoc;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "dtCreatedDate")
	private String dtCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dtLastModified")
	private String dtLastModified;

	@Column(name = "strClientCode")
	private String strClientCode;

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrToLoc() {
		return strToLoc;
	}

	public void setStrToLoc(String strToLoc) {
		this.strToLoc = strToLoc;
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

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
