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
@Table(name = "tblmembertypemaster")
@IdClass(clsWebClubMemberCategoryMasterModel_ID.class)
public class clsWebClubMemberCategoryMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsWebClubMemberCategoryMasterModel() {
	}

	public clsWebClubMemberCategoryMasterModel(clsWebClubMemberCategoryMasterModel_ID objModelID) {
		strCatCode = objModelID.getStrCatCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strCatCode", column = @Column(name = "strCatCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strCatCode")
	private String strCatCode;

	@Column(name = "strCatName")
	private String strCatName;

	@Column(name = "strGroupCategoryCode")
	private String strGroupCategoryCode;

	@Column(name = "intCreditLimit")
	private long intCreditLimit;

	@Column(name = "strRemarks")
	private String strRemarks;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "strVotingRights")
	private String strVotingRights;

	@Column(name = "strcatdesc")
	private String strcatdesc;

	@Column(name = "strRuleCode")
	private String strRuleCode;

	@Column(name = "strCorporate")
	private String strCorporate;

	@Column(name = "dblCreditAmt")
	private double dblCreditAmt;

	@Column(name = "dblDisAmt")
	private double dblDisAmt;

	@Column(name = "intGId", updatable = false)
	private long intGId;

	@Column(name = "dtCreatedDate")
	private String dtCreatedDate;

	@Column(name = "strTenure")
	private String strTenure;

	@Column(name = "dtModifiedDate")
	private String dtModifiedDate;

	// Setter-Getter Methods
	public String getStrCatCode() {
		return strCatCode;
	}

	public void setStrCatCode(String strCatCode) {
		this.strCatCode = (String) setDefaultValue(strCatCode, "NA");
	}

	public String getStrCatName() {
		return strCatName;
	}

	public void setStrCatName(String strCatName) {
		this.strCatName = (String) setDefaultValue(strCatName, "NA");
	}

	public String getStrGroupCategoryCode() {
		return strGroupCategoryCode;
	}

	public void setStrGroupCategoryCode(String strGroupCategoryCode) {
		this.strGroupCategoryCode = (String) setDefaultValue(strGroupCategoryCode, "NA");
	}

	public long getIntCreditLimit() {
		return intCreditLimit;
	}

	public void setIntCreditLimit(long intCreditLimit) {
		this.intCreditLimit = (Long) setDefaultValue(intCreditLimit, "NA");
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = (String) setDefaultValue(strRemarks, "NA");
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

	public String getStrVotingRights() {
		return strVotingRights;
	}

	public void setStrVotingRights(String strVotingRights) {
		this.strVotingRights = (String) setDefaultValue(strVotingRights, "NA");
	}

	public String getStrcatdesc() {
		return strcatdesc;
	}

	public void setStrcatdesc(String strcatdesc) {
		this.strcatdesc = (String) setDefaultValue(strcatdesc, "NA");
	}

	public String getStrRuleCode() {
		return strRuleCode;
	}

	public void setStrRuleCode(String strRuleCode) {
		this.strRuleCode = (String) setDefaultValue(strRuleCode, "NA");
	}

	public String getStrCorporate() {
		return strCorporate;
	}

	public void setStrCorporate(String strCorporate) {
		this.strCorporate = (String) setDefaultValue(strCorporate, "NA");
	}

	public double getDblCreditAmt() {
		return dblCreditAmt;
	}

	public void setDblCreditAmt(double dblCreditAmt) {
		this.dblCreditAmt = (Double) setDefaultValue(dblCreditAmt, "NA");
	}

	public double getDblDisAmt() {
		return dblDisAmt;
	}

	public void setDblDisAmt(double dblDisAmt) {
		this.dblDisAmt = (Double) setDefaultValue(dblDisAmt, "NA");
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

	public long getIntGId() {
		return intGId;
	}

	public void setIntGId(long intGId) {
		this.intGId = intGId;
	}

	public String getDtCreatedDate() {
		return dtCreatedDate;
	}

	public void setDtCreatedDate(String dtCreatedDate) {
		this.dtCreatedDate = dtCreatedDate;
	}

	public String getStrTenure() {
		return strTenure;
	}

	public void setStrTenure(String strTenure) {
		this.strTenure = strTenure;
	}

	public String getDtModifiedDate() {
		return dtModifiedDate;
	}

	public void setDtModifiedDate(String dtModifiedDate) {
		this.dtModifiedDate = dtModifiedDate;
	}

}
