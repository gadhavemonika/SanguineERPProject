package com.sanguine.model;

import java.sql.Blob;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "tblcompanylogomodel")
public class clsCompanyLogoModel {

	@Id
	@Column(name = "strCompanyCode")
	private String strCompanyCode;

	@Column(name = "strCompanyLogo", length = 100000, nullable = false)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@JsonIgnore
	private Blob strCompanyLogo;

	public String getStrCompanyCode() {
		return strCompanyCode;
	}

	public void setStrCompanyCode(String strCompanyCode) {
		this.strCompanyCode = strCompanyCode;
	}

	public Blob getStrCompanyLogo() {
		return strCompanyLogo;
	}

	public void setStrCompanyLogo(Blob strCompanyLogo) {
		this.strCompanyLogo = strCompanyLogo;
	}

}
