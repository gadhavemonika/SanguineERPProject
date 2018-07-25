package com.sanguine.webclub.bean;

import java.util.ArrayList;

import com.sanguine.webclub.model.clsWebClubCommitteeMasterDtl;

public class clsWebClubCommitteeMasterBean {
	// Variable Declaration
	private String strCommitteeCode;

	private String strCommitteeName;

	private String strType;

	private String strUserCreated;

	private String dteCreatedDate;

	private String strUserModified;

	private String dteLastModified;

	private String strClientCode;

	private String strPropertyID;

	private String strRoleDesc;

	private ArrayList<clsWebClubCommitteeMasterDtl> listCommitteeMasterDtl = new ArrayList<clsWebClubCommitteeMasterDtl>();

	// Setter-Getter Methods
	public String getStrCommitteeCode() {
		return strCommitteeCode;
	}

	public void setStrCommitteeCode(String strCommitteeCode) {
		this.strCommitteeCode = strCommitteeCode;
	}

	public String getStrCommitteeName() {
		return strCommitteeName;
	}

	public void setStrCommitteeName(String strCommitteeName) {
		this.strCommitteeName = strCommitteeName;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
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

	public String getStrPropertyID() {
		return strPropertyID;
	}

	public void setStrPropertyID(String strPropertyID) {
		this.strPropertyID = strPropertyID;
	}

	public String getStrRoleDesc() {
		return strRoleDesc;
	}

	public void setStrRoleDesc(String strRoleDesc) {
		this.strRoleDesc = strRoleDesc;
	}

	public ArrayList<clsWebClubCommitteeMasterDtl> getListCommitteeMasterDtl() {
		return listCommitteeMasterDtl;
	}

	public void setListCommitteeMasterDtl(ArrayList<clsWebClubCommitteeMasterDtl> listCommitteeMasterDtl) {
		this.listCommitteeMasterDtl = listCommitteeMasterDtl;
	}

}
