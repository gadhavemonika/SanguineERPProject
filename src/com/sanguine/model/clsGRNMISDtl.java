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
@Table(name = "tblgrnmisdtl")
@IdClass(clsGRNMISDtl_ID.class)
public class clsGRNMISDtl implements Serializable {

	public clsGRNMISDtl() {
	}

	public clsGRNMISDtl(clsGRNMISDtl_ID clsGRNMISDtl_ID) {
		this.strGRNCode = clsGRNMISDtl_ID.getStrGRNCode();
		this.strMISCode = clsGRNMISDtl_ID.getStrMISCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strGRNCode", column = @Column(name = "strGRNCode")), @AttributeOverride(name = "strMISCode", column = @Column(name = "strMISCode")) })
	@Column(name = "strGRNCode")
	private String strGRNCode;

	@Column(name = "strMISCode", columnDefinition = "VARCHAR(25) NOT NULL default '' ")
	private String strMISCode;

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(25) NOT NULL default ''")
	private String strClientCode;

	public String getStrGRNCode() {
		return strGRNCode;
	}

	public void setStrGRNCode(String strGRNCode) {
		this.strGRNCode = strGRNCode;
	}

	public String getStrMISCode() {
		return strMISCode;
	}

	public void setStrMISCode(String strMISCode) {
		this.strMISCode = strMISCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
