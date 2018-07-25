package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Embeddable;


@Embeddable
public class clsUserDefinedReportDtlModel implements Serializable {

	// Variable Declaration
	
	private int intSrNo;
	
	private String strType;
	
	private String strColumn;
	
	private String strOperator;
	
	private String strFGroup;
	
	private String strTGroup;
	
	private String strFAccount;
	
	private String strTAccount;
	
	private String strDescription;
	
	private String strConstant;
	
	private String strFormula;
	
	private String strPrint;

	public int getIntSrNo() {
		return intSrNo;
	}

	public void setIntSrNo(int intSrNo) {
		this.intSrNo = intSrNo;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getStrColumn() {
		return strColumn;
	}

	public void setStrColumn(String strColumn) {
		this.strColumn = strColumn;
	}

	public String getStrOperator() {
		return strOperator;
	}

	public void setStrOperator(String strOperator) {
		this.strOperator = strOperator;
	}

	public String getStrFGroup() {
		return strFGroup;
	}

	public void setStrFGroup(String strFGroup) {
		this.strFGroup = strFGroup;
	}

	public String getStrTGroup() {
		return strTGroup;
	}

	public void setStrTGroup(String strTGroup) {
		this.strTGroup = strTGroup;
	}

	public String getStrFAccount() {
		return strFAccount;
	}

	public void setStrFAccount(String strFAccount) {
		this.strFAccount = strFAccount;
	}

	public String getStrTAccount() {
		return strTAccount;
	}

	public void setStrTAccount(String strTAccount) {
		this.strTAccount = strTAccount;
	}

	public String getStrDescription() {
		return strDescription;
	}

	public void setStrDescription(String strDescription) {
		this.strDescription = strDescription;
	}

	public String getStrConstant() {
		return strConstant;
	}

	public void setStrConstant(String strConstant) {
		this.strConstant = strConstant;
	}

	public String getStrFormula() {
		return strFormula;
	}

	public void setStrFormula(String strFormula) {
		this.strFormula = strFormula;
	}

	public String getStrPrint() {
		return strPrint;
	}

	public void setStrPrint(String strPrint) {
		this.strPrint = strPrint;
	}
	
	
	
}