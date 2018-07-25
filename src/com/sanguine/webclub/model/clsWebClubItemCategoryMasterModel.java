package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblitemcategory")
@IdClass(clsWebClubItemCategoryMasterModel_ID.class)
public class clsWebClubItemCategoryMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsWebClubItemCategoryMasterModel() {
	}

	public clsWebClubItemCategoryMasterModel(clsWebClubItemCategoryMasterModel_ID objModelID) {
		strItemCategoryCode = objModelID.getStrItemCategoryCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strItemCategoryCode", column = @Column(name = "strItemCategoryCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strItemCategoryCode")
	private String strItemCategoryCode;

	@Column(name = "intId")
	public long intId;

	@Column(name = "intRowId")
	public long intRowId;

	@Column(name = "strItemCategoryName")
	private String strItemCategoryName;

	@Column(name = "strAccountIn")
	private String strAccountIn;

	@Column(name = "strGLCode")
	private String strGLCode;

	@Column(name = "strSideledgerCode")
	private String strSideledgerCode;

	@Column(name = "strTaxCode")
	private String strTaxCode;

	@Column(name = "strTaxType")
	private String strTaxType;

	@Column(name = "strAddUserId")
	private String strAddUserId;

	@Column(name = "strItemTypeCode")
	private String strItemTypeCode;

	@Column(name = "strTaxName")
	private String strTaxName;

	@Column(name = "strCatItemType")
	private String strCatItemType;

	@Column(name = "strDisAccIn")
	private String strDisAccIn;

	@Column(name = "strFreeze")
	private String strFreeze;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "dteCreatedDate", updatable = false)
	private String dteCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteLastModifiedDate")
	private String dteLastModifiedDate;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	public String getStrItemCategoryCode() {
		return strItemCategoryCode;
	}

	public void setStrItemCategoryCode(String strItemCategoryCode) {
		this.strItemCategoryCode = strItemCategoryCode;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public long getIntRowId() {
		return intRowId;
	}

	public void setIntRowId(long intRowId) {
		this.intRowId = intRowId;
	}

	public String getStrItemCategoryName() {
		return strItemCategoryName;
	}

	public void setStrItemCategoryName(String strItemCategoryName) {
		this.strItemCategoryName = strItemCategoryName;
	}

	public String getStrAccountIn() {
		return strAccountIn;
	}

	public void setStrAccountIn(String strAccountIn) {
		this.strAccountIn = strAccountIn;
	}

	public String getStrGLCode() {
		return strGLCode;
	}

	public void setStrGLCode(String strGLCode) {
		this.strGLCode = strGLCode;
	}

	public String getStrSideledgerCode() {
		return strSideledgerCode;
	}

	public void setStrSideledgerCode(String strSideledgerCode) {
		this.strSideledgerCode = strSideledgerCode;
	}

	public String getStrTaxCode() {
		return strTaxCode;
	}

	public void setStrTaxCode(String strTaxCode) {
		this.strTaxCode = strTaxCode;
	}

	public String getStrTaxType() {
		return strTaxType;
	}

	public void setStrTaxType(String strTaxType) {
		this.strTaxType = strTaxType;
	}

	public String getStrAddUserId() {
		return strAddUserId;
	}

	public void setStrAddUserId(String strAddUserId) {
		this.strAddUserId = strAddUserId;
	}

	public String getStrItemTypeCode() {
		return strItemTypeCode;
	}

	public void setStrItemTypeCode(String strItemTypeCode) {
		this.strItemTypeCode = strItemTypeCode;
	}

	public String getStrTaxName() {
		return strTaxName;
	}

	public void setStrTaxName(String strTaxName) {
		this.strTaxName = strTaxName;
	}

	public String getStrCatItemType() {
		return strCatItemType;
	}

	public void setStrCatItemType(String strCatItemType) {
		this.strCatItemType = strCatItemType;
	}

	public String getStrDisAccIn() {
		return strDisAccIn;
	}

	public void setStrDisAccIn(String strDisAccIn) {
		this.strDisAccIn = strDisAccIn;
	}

	public String getStrFreeze() {
		return strFreeze;
	}

	public void setStrFreeze(String strFreeze) {
		this.strFreeze = strFreeze;
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

	public String getDteLastModifiedDate() {
		return dteLastModifiedDate;
	}

	public void setDteLastModifiedDate(String dteLastModifiedDate) {
		this.dteLastModifiedDate = dteLastModifiedDate;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	// Function to Set Default Values
	private Object setDefaultValue(Object value, Object defaultValue) {
		if (value != null && (value instanceof String && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Double && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Integer && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Long && value.toString().length() > 0)) {
			return value;
		} else {
			return defaultValue;
		}
	}
}
