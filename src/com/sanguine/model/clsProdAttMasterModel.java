package com.sanguine.model;

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
@Table(name = "tblprodattmaster")
@IdClass(clsProdAttMasterModel_ID.class)
public class clsProdAttMasterModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public clsProdAttMasterModel() {
	}

	public clsProdAttMasterModel(clsProdAttMasterModel_ID clsProdAttMasterModel_ID) {
		this.strProdCode = clsProdAttMasterModel_ID.getStrProdCode();
		this.strAttCode = clsProdAttMasterModel_ID.getStrAttCode();
		this.strClientCode = clsProdAttMasterModel_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strAttCode", column = @Column(name = "strAttCode")), @AttributeOverride(name = "strProdCode", column = @Column(name = "strProdCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "strAttCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	private String strAttCode;

	@Column(name = "dblAttValue", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	private double dblAttValue;

	@Column(name = "strAVCode", nullable = true, columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	private String strAVCode;

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	private String strClientCode;

	@Transient
	String strAttName;

	public String getStrAttName() {
		return strAttName;
	}

	public void setStrAttName(String strAttName) {
		this.strAttName = strAttName;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrAttCode() {
		return strAttCode;
	}

	public void setStrAttCode(String strAttCode) {
		this.strAttCode = strAttCode;
	}

	public double getDblAttValue() {
		return dblAttValue;
	}

	public void setDblAttValue(double dblAttValue) {
		this.dblAttValue = dblAttValue;
	}

	public String getStrAVCode() {
		return strAVCode;
	}

	public void setStrAVCode(String strAVCode) {
		this.strAVCode = strAVCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
