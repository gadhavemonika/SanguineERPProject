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
@Table(name = "tblmembermaster")
@IdClass(clsWebClubDependentMasterModel_ID.class)
public class clsWebClubDependentMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsWebClubDependentMasterModel() {
	}

	public clsWebClubDependentMasterModel(clsWebClubDependentMasterModel_ID objModelID) {
		strCustomerCode = objModelID.getStrCustomerCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strCustomerCode", column = @Column(name = "strCustomerCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strCustomerCode")
	private String strCustomerCode;

	@Column(name = "strMemberCode")
	private String strMemberCode;

	@Column(name = "strDependentFullName")
	private String strDependentFullName;

	@Column(name = "strGender")
	private String strGender;

	@Column(name = "strDepedentRelation")
	private String strDepedentRelation;

	@Column(name = "strMaritalStatus")
	private String strMaritalStatus;

	@Column(name = "strProfessionCode")
	private String strProfessionCode;

	@Column(name = "dteMembershipExpiryDate")
	private String dteMembershipExpiryDate;

	@Column(name = "strBlocked")
	private String strBlocked;

	@Column(name = "dteMemberBlockDate")
	private String dteMemberBlockDate;

	@Column(name = "strUserCreated", nullable = false, updatable = false)
	private String strUserCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteCreatedDate", nullable = false, updatable = false)
	private String dteCreatedDate;

	@Column(name = "dteModifiedDate")
	private String dteModifiedDate;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "dteDependentDateofBirth")
	private String dteDependentDateofBirth;

	@Column(name = "strDependentReasonCode")
	private String strDependentReasonCode;

	@Column(name = "intGId", nullable = false, updatable = false)
	private Long intGId;

	// Setter-Getter Methods
	public String getStrMemberCode() {
		return strMemberCode;
	}

	public void setStrMemberCode(String strMemberCode) {
		this.strMemberCode = (String) setDefaultValue(strMemberCode, "NA");
	}

	public String getStrGender() {
		return strGender;
	}

	public void setStrGender(String strGender) {
		this.strGender = (String) setDefaultValue(strGender, "N");
	}

	public String getStrDepedentRelation() {
		return strDepedentRelation;
	}

	public void setStrDepedentRelation(String strDepedentRelation) {
		this.strDepedentRelation = (String) setDefaultValue(strDepedentRelation, "NA");
	}

	public String getStrMaritalStatus() {
		return strMaritalStatus;
	}

	public void setStrMaritalStatus(String strMaritalStatus) {
		this.strMaritalStatus = (String) setDefaultValue(strMaritalStatus, "N");
	}

	public String getStrProfessionCode() {
		return strProfessionCode;
	}

	public void setStrProfessionCode(String strProfessionCode) {
		this.strProfessionCode = (String) setDefaultValue(strProfessionCode, "NA");
	}

	public String getDteMembershipExpiryDate() {
		return dteMembershipExpiryDate;
	}

	public void setDteMembershipExpiryDate(String dteMembershipExpiryDate) {
		this.dteMembershipExpiryDate = dteMembershipExpiryDate;
	}

	public String getStrBlocked() {
		return strBlocked;
	}

	public void setStrBlocked(String strBlocked) {
		this.strBlocked = (String) setDefaultValue(strBlocked, "N");
	}

	public String getDteMemberBlockDate() {
		return dteMemberBlockDate;
	}

	public void setDteMemberBlockDate(String dteMemberBlockDate) {
		this.dteMemberBlockDate = dteMemberBlockDate;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = (String) setDefaultValue(strUserCreated, "NA");
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = (String) setDefaultValue(strUserModified, "NA");
	}

	public String getDteCreatedDate() {
		return dteCreatedDate;
	}

	public void setDteCreatedDate(String dteCreatedDate) {
		this.dteCreatedDate = dteCreatedDate;
	}

	public String getDteModifiedDate() {
		return dteModifiedDate;
	}

	public void setDteModifiedDate(String dteModifiedDate) {
		this.dteModifiedDate = dteModifiedDate;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = (String) setDefaultValue(strClientCode, "NA");
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = (String) setDefaultValue(strPropertyCode, "NA");
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

	public String getStrCustomerCode() {
		return strCustomerCode;
	}

	public void setStrCustomerCode(String strCustomerCode) {
		this.strCustomerCode = strCustomerCode;
	}

	public String getStrDependentFullName() {
		return strDependentFullName;
	}

	public void setStrDependentFullName(String strDependentFullName) {
		this.strDependentFullName = strDependentFullName;
	}

	public String getDteDependentDateofBirth() {
		return dteDependentDateofBirth;
	}

	public void setDteDependentDateofBirth(String dteDependentDateofBirth) {
		this.dteDependentDateofBirth = dteDependentDateofBirth;
	}

	public String getStrDependentReasonCode() {
		return strDependentReasonCode;
	}

	public void setStrDependentReasonCode(String strDependentReasonCode) {
		this.strDependentReasonCode = strDependentReasonCode;
	}

	public Long getIntGId() {
		return intGId;
	}

	public void setIntGId(Long intGId) {
		this.intGId = intGId;
	}

}
