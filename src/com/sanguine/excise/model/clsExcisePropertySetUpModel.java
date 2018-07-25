package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblexcisepropertymaster")
public class clsExcisePropertySetUpModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public clsExcisePropertySetUpModel() {
	}

	// Variable Declaration

	@Id
	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strBrandMaster")
	private String strBrandMaster;

	@Column(name = "strSizeMaster")
	private String strSizeMaster;

	@Column(name = "strSubCategory")
	private String strSubCategory;

	@Column(name = "strCategory")
	private String strCategory;

	@Column(name = "strSupplier")
	private String strSupplier;

	@Column(name = "strRecipe")
	private String strRecipe;

	@Column(name = "strCity")
	private String strCity;

	@Column(name = "strPermit")
	private String strPermit;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dteDateCreated")
	private String dteDateCreated;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	// Setter-Getter Methods

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrBrandMaster() {
		return strBrandMaster;
	}

	public void setStrBrandMaster(String strBrandMaster) {
		this.strBrandMaster = strBrandMaster;
	}

	public String getStrSizeMaster() {
		return strSizeMaster;
	}

	public void setStrSizeMaster(String strSizeMaster) {
		this.strSizeMaster = strSizeMaster;
	}

	public String getStrSubCategory() {
		return strSubCategory;
	}

	public void setStrSubCategory(String strSubCategory) {
		this.strSubCategory = strSubCategory;
	}

	public String getStrCategory() {
		return strCategory;
	}

	public void setStrCategory(String strCategory) {
		this.strCategory = strCategory;
	}

	public String getStrSupplier() {
		return strSupplier;
	}

	public void setStrSupplier(String strSupplier) {
		this.strSupplier = strSupplier;
	}

	public String getStrRecipe() {
		return strRecipe;
	}

	public void setStrRecipe(String strRecipe) {
		this.strRecipe = strRecipe;
	}

	public String getStrCity() {
		return strCity;
	}

	public void setStrCity(String strCity) {
		this.strCity = strCity;
	}

	public String getStrPermit() {
		return strPermit;
	}

	public void setStrPermit(String strPermit) {
		this.strPermit = strPermit;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getStrUserEdited() {
		return strUserEdited;
	}

	public void setStrUserEdited(String strUserEdited) {
		this.strUserEdited = strUserEdited;
	}

	public String getDteDateCreated() {
		return dteDateCreated;
	}

	public void setDteDateCreated(String dteDateCreated) {
		this.dteDateCreated = dteDateCreated;
	}

	public String getDteDateEdited() {
		return dteDateEdited;
	}

	public void setDteDateEdited(String dteDateEdited) {
		this.dteDateEdited = dteDateEdited;
	}

}
