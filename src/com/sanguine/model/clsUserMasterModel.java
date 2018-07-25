package com.sanguine.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CollectionOfElements;

import com.sanguine.webbooks.model.clsJVDtlModel;

@Entity
@Table(name = "tbluserhd")
@IdClass(clsUserMasterModel_ID.class)
public class clsUserMasterModel {
	public clsUserMasterModel() {
	}

	public clsUserMasterModel(clsUserMasterModel_ID clsUserMasterModel_ID) {
		this.strUserCode = clsUserMasterModel_ID.getStrUserCode();
		this.strClientCode = clsUserMasterModel_ID.getStrClientCode();
	}

	@CollectionOfElements(fetch = FetchType.LAZY)
	@JoinTable(name = "tbluserlocdtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strUserCode") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strUserCode", column = @Column(name = "strUserCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsUserLocationDtl> listUserLocDtlModel = new ArrayList<clsUserLocationDtl>();

	private String strUserCode, strUserName, strPassword, strSuperUser, strType, strRetire, strProperty, strLoginStatus;
	private String strUserCreated, dtCreatedDate, strUserModified, dtLastModified, strSignatureImg, strClientCode, strShowDashBoard;
	private String strReorderLevel;
	private long intid;

	public long getIntid() {
		return intid;
	}

	public void setIntid(long intid) {
		this.intid = intid;
	}

	@Column(name = "strUserCode")
	public String getStrUserCode1() {
		return strUserCode;
	}

	public void setStrUserCode1(String strUserCode1) {
		this.strUserCode = strUserCode1;
	}

	@Column(name = "strUserName")
	public String getStrUserName1() {
		return strUserName;
	}

	public void setStrUserName1(String strUserName1) {
		this.strUserName = strUserName1;
	}

	@Column(name = "strPassword", columnDefinition = "VARCHAR(255) NOT NULL")
	public String getStrPassword1() {
		return strPassword;
	}

	public void setStrPassword1(String strPassword1) {
		this.strPassword = strPassword1;
	}

	@Column(name = "strSuperUser")
	public String getStrSuperUser() {
		return strSuperUser;
	}

	public void setStrSuperUser(String strSuperUser) {
		this.strSuperUser = strSuperUser;
	}

	@Column(name = "strType")
	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	@Column(name = "strRetire")
	public String getStrRetire() {
		return strRetire;
	}

	public void setStrRetire(String strRetire) {
		this.strRetire = strRetire;
	}

	@Column(name = "strProperty")
	public String getStrProperty() {
		return strProperty;
	}

	public void setStrProperty(String strProperty) {
		this.strProperty = strProperty;
	}

	@Column(name = "strLoginStatus")
	public String getStrLoginStatus() {
		return strLoginStatus;
	}

	public void setStrLoginStatus(String strLoginStatus) {
		this.strLoginStatus = strLoginStatus;
	}

	@Column(name = "strUserCreated", updatable = false)
	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	@Column(name = "dtCreatedDate", updatable = false)
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

	@Column(name = "strSignatureImg")
	public String getStrSignatureImg() {
		return strSignatureImg;
	}

	public void setStrSignatureImg(String strSignatureImg) {
		this.strSignatureImg = strSignatureImg;
	}

	@Column(name = "strClientCode", updatable = false)
	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Column(name = "strShowDashBoard", updatable = false)
	public String getStrShowDashBoard() {
		return strShowDashBoard;
	}

	public void setStrShowDashBoard(String strShowDashBoard) {
		this.strShowDashBoard = strShowDashBoard;
	}

	public List<clsUserLocationDtl> getListUserLocDtlModel() {
		return listUserLocDtlModel;
	}

	public void setListUserLocDtlModel(List<clsUserLocationDtl> listUserLocDtlModel) {
		this.listUserLocDtlModel = listUserLocDtlModel;
	}

	@Transient
	private String strLocation;

	public String getStrLocation() {
		return strLocation;
	}

	public void setStrLocation(String strLocation) {
		this.strLocation = strLocation;
	}
	@Column(name = "strReorderLevel")
	public String getStrReorderLevel() {
		return strReorderLevel;
	}

	public void setStrReorderLevel(String strReorderLevel) {
		this.strReorderLevel = strReorderLevel;
	}
    
}
