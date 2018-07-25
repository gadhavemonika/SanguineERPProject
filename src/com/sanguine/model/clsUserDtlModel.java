package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbluserdtl")
public class clsUserDtlModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "intId")
	private long intId;
	private String strUserCode;
	private String strFormName;
	private String strAdd;
	private String strEdit;
	private String strDelete;
	private String strView;
	private String strPrint;
	private String strGrant;
	private String strAuthorise;
	private String strFormType;
	private int intFormKey;
	private int intFormNo;
	private String strUserCreated;
	private String dtCreatedDate;
	private String strUserModified;
	private String dtLastModified;
	private String strDesktop;
	private String strModule;

	@Column(name = "strUserName")
	private String strUserName;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strUserCode")
	public String getStrUserCode() {
		return strUserCode;
	}

	public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
	}

	@Column(name = "strFormName")
	public String getStrFormName() {
		return strFormName;
	}

	public void setStrFormName(String strFormName) {
		this.strFormName = strFormName;
	}

	@Column(name = "strAdd")
	public String getStrAdd() {
		return strAdd;
	}

	public void setStrAdd(String strAdd) {
		this.strAdd = strAdd;
	}

	@Column(name = "strEdit")
	public String getStrEdit() {
		return strEdit;
	}

	public void setStrEdit(String strEdit) {
		this.strEdit = strEdit;
	}

	@Column(name = "strDelete")
	public String getStrDelete() {
		return strDelete;
	}

	public void setStrDelete(String strDelete) {
		this.strDelete = strDelete;
	}

	@Column(name = "strView")
	public String getStrView() {
		return strView;
	}

	public void setStrView(String strView) {
		this.strView = strView;
	}

	@Column(name = "strPrint")
	public String getStrPrint() {
		return strPrint;
	}

	public void setStrPrint(String strPrint) {
		this.strPrint = strPrint;
	}

	@Column(name = "strGrant")
	public String getStrGrant() {
		return strGrant;
	}

	public void setStrGrant(String strGrant) {
		this.strGrant = strGrant;
	}

	@Column(name = "strAuthorise")
	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	@Column(name = "strFormType")
	public String getStrFormType() {
		return strFormType;
	}

	public void setStrFormType(String strFormType) {
		this.strFormType = strFormType;
	}

	@Column(name = "intFormKey")
	public int getIntFormKey() {
		return intFormKey;
	}

	public void setIntFormKey(int intFormKey) {
		this.intFormKey = intFormKey;
	}

	@Column(name = "intFormNo")
	public int getIntFormNo() {
		return intFormNo;
	}

	public void setIntFormNo(int intFormNo) {
		this.intFormNo = intFormNo;
	}

	@Column(name = "strUserCreated")
	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	@Column(name = "dtCreatedDate")
	public String getDtCreatedDate() {
		return dtCreatedDate;
	}

	public void setDtCreatedDate(String dtCreatedDate) {
		this.dtCreatedDate = dtCreatedDate;
	}

	@Column(name = "strUserModified")
	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	@Column(name = "dtLastModified")
	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	@Column(name = "strDesktop")
	public String getStrDesktop() {
		return strDesktop;
	}

	public void setStrDesktop(String strDesktop) {
		this.strDesktop = strDesktop;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrUserName() {
		return strUserName;
	}

	public void setStrUserName(String strUserName) {
		this.strUserName = strUserName;
	}

	@Column(name = "strModule")
	public String getStrModule() {
		return strModule;
	}

	public void setStrModule(String strModule) {
		this.strModule = strModule;
	}
}
