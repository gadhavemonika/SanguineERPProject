package com.sanguine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblunitconversion")
public class clsUnitConversionModel {
	@Id
	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "strReceivedUOM")
	private String strReceivedUOM;

	@Column(name = "strIssueUOM")
	private String strIssueUOM;

	@Column(name = "dblReceiveConversion")
	private double dblReceiveConversion;

	@Column(name = "strRecipeUOM")
	private String strRecipeUOM;

	@Column(name = "dblIssueConversion")
	private double dblIssueConversion;

	@Column(name = "dblRecipeConversion")
	private double dblRecipeConversion;

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrReceivedUOM() {
		return strReceivedUOM;
	}

	public void setStrReceivedUOM(String strReceivedUOM) {
		this.strReceivedUOM = strReceivedUOM;
	}

	public String getStrIssueUOM() {
		return strIssueUOM;
	}

	public void setStrIssueUOM(String strIssueUOM) {
		this.strIssueUOM = strIssueUOM;
	}

	public double getDblReceiveConversion() {
		return dblReceiveConversion;
	}

	public void setDblReceiveConversion(double dblReceiveConversion) {
		this.dblReceiveConversion = dblReceiveConversion;
	}

	public String getStrRecipeUOM() {
		return strRecipeUOM;
	}

	public void setStrRecipeUOM(String strRecipeUOM) {
		this.strRecipeUOM = strRecipeUOM;
	}

	public double getDblIssueConversion() {
		return dblIssueConversion;
	}

	public void setDblIssueConversion(double dblIssueConversion) {
		this.dblIssueConversion = dblIssueConversion;
	}

	public double getDblRecipeConversion() {
		return dblRecipeConversion;
	}

	public void setDblRecipeConversion(double dblRecipeConversion) {
		this.dblRecipeConversion = dblRecipeConversion;
	}

}
