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
@Table(name = "tbleditotherinfo")
@IdClass(clsWebClubEditOtherInfoModel_ID.class)
public class clsWebClubEditOtherInfoModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsWebClubEditOtherInfoModel() {
	}

	public clsWebClubEditOtherInfoModel(clsWebClubEditOtherInfoModel_ID objModelID) {
		strMemberCode = objModelID.getStrMemberCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strMemberCode", column = @Column(name = "strMemberCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strMemberCode")
	private String strMemberCode;

	@Column(name = "dteAddressChangeDate")
	private String dteAddressChangeDate;

	@Column(name = "strBloodGroupMember")
	private String strBloodGroupMember;

	@Column(name = "strCarSticker1")
	private String strCarSticker1;

	@Column(name = "strCarSticker2")
	private String strCarSticker2;

	@Column(name = "strCarSticker3")
	private String strCarSticker3;

	@Column(name = "strCarSticker4")
	private String strCarSticker4;

	@Column(name = "dblChidDepositeAmt")
	private double dblChidDepositeAmt;

	@Column(name = "strChidDepositeCategory")
	private String strChidDepositeCategory;

	@Column(name = "dteChidDepositeRecepitDate")
	private String dteChidDepositeRecepitDate;

	@Column(name = "strChidDepositeRecepitNo")
	private String strChidDepositeRecepitNo;

	@Column(name = "dteCorporateEndingDate")
	private String dteCorporateEndingDate;

	@Column(name = "dteCorporateStartingDate")
	private String dteCorporateStartingDate;

	@Column(name = "strElectionName")
	private String strElectionName;

	@Column(name = "dteEligibilityDate")
	private String dteEligibilityDate;

	@Column(name = "dteMaturityDate")
	private String dteMaturityDate;

	@Column(name = "strMrAndMrs")
	private String strMrAndMrs;

	@Column(name = "dteMembershipCancelledDate")
	private String dteMembershipCancelledDate;

	@Column(name = "strOldCategory")
	private String strOldCategory;

	@Column(name = "dteReInStatementDate")
	private String dteReInStatementDate;

	@Column(name = "strSenior")
	private String strSenior;

	@Column(name = "dteSeniorDate")
	private String dteSeniorDate;

	@Column(name = "strSeniorFlag")
	private String strSeniorFlag;

	@Column(name = "strSenoirMember")
	private String strSenoirMember;

	@Column(name = "strStatusCorporateNomineeMship")
	private String strStatusCorporateNomineeMship;

	@Column(name = "strTransferFlag")
	private String strTransferFlag;

	@Column(name = "strNomineeName")
	private String strNomineeName;

	@Column(name = "strOutStationMember")
	private String strOutStationMember;

	@Column(name = "strSecurityDeposit")
	private String strSecurityDeposit;

	@Column(name = "strSuperSenior")
	private String strSuperSenior;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "dteCreatedDate")
	private String dteCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteLastModified")
	private String dteLastModified;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	// Setter-Getter Methods
	public String getStrMemberCode() {
		return strMemberCode;
	}

	public void setStrMemberCode(String strMemberCode) {
		this.strMemberCode = (String) setDefaultValue(strMemberCode, "NA");
	}

	public String getDteAddressChangeDate() {
		return dteAddressChangeDate;
	}

	public void setDteAddressChangeDate(String dteAddressChangeDate) {
		this.dteAddressChangeDate = dteAddressChangeDate;
	}

	public String getStrBloodGroupMember() {
		return strBloodGroupMember;
	}

	public void setStrBloodGroupMember(String strBloodGroupMember) {
		this.strBloodGroupMember = (String) setDefaultValue(strBloodGroupMember, "NA");
	}

	public String getStrCarSticker1() {
		return strCarSticker1;
	}

	public void setStrCarSticker1(String strCarSticker1) {
		this.strCarSticker1 = (String) setDefaultValue(strCarSticker1, "NA");
	}

	public String getStrCarSticker2() {
		return strCarSticker2;
	}

	public void setStrCarSticker2(String strCarSticker2) {
		this.strCarSticker2 = (String) setDefaultValue(strCarSticker2, "NA");
	}

	public String getStrCarSticker3() {
		return strCarSticker3;
	}

	public void setStrCarSticker3(String strCarSticker3) {
		this.strCarSticker3 = (String) setDefaultValue(strCarSticker3, "NA");
	}

	public String getStrCarSticker4() {
		return strCarSticker4;
	}

	public void setStrCarSticker4(String strCarSticker4) {
		this.strCarSticker4 = (String) setDefaultValue(strCarSticker4, "NA");
	}

	public double getDblChidDepositeAmt() {
		return dblChidDepositeAmt;
	}

	public void setDblChidDepositeAmt(double dblChidDepositeAmt) {
		this.dblChidDepositeAmt = (Double) setDefaultValue(dblChidDepositeAmt, "NA");
	}

	public String getStrChidDepositeCategory() {
		return strChidDepositeCategory;
	}

	public void setStrChidDepositeCategory(String strChidDepositeCategory) {
		this.strChidDepositeCategory = (String) setDefaultValue(strChidDepositeCategory, "NA");
	}

	public String getDteChidDepositeRecepitDate() {
		return dteChidDepositeRecepitDate;
	}

	public void setDteChidDepositeRecepitDate(String dteChidDepositeRecepitDate) {
		this.dteChidDepositeRecepitDate = dteChidDepositeRecepitDate;
	}

	public String getStrChidDepositeRecepitNo() {
		return strChidDepositeRecepitNo;
	}

	public void setStrChidDepositeRecepitNo(String strChidDepositeRecepitNo) {
		this.strChidDepositeRecepitNo = (String) setDefaultValue(strChidDepositeRecepitNo, "NA");
	}

	public String getDteCorporateEndingDate() {
		return dteCorporateEndingDate;
	}

	public void setDteCorporateEndingDate(String dteCorporateEndingDate) {
		this.dteCorporateEndingDate = dteCorporateEndingDate;
	}

	public String getDteCorporateStartingDate() {
		return dteCorporateStartingDate;
	}

	public void setDteCorporateStartingDate(String dteCorporateStartingDate) {
		this.dteCorporateStartingDate = dteCorporateStartingDate;
	}

	public String getStrElectionName() {
		return strElectionName;
	}

	public void setStrElectionName(String strElectionName) {
		this.strElectionName = (String) setDefaultValue(strElectionName, "NA");
	}

	public String getDteEligibilityDate() {
		return dteEligibilityDate;
	}

	public void setDteEligibilityDate(String dteEligibilityDate) {
		this.dteEligibilityDate = dteEligibilityDate;
	}

	public String getDteMaturityDate() {
		return dteMaturityDate;
	}

	public void setDteMaturityDate(String dteMaturityDate) {
		this.dteMaturityDate = dteMaturityDate;
	}

	public String getStrMrAndMrs() {
		return strMrAndMrs;
	}

	public void setStrMrAndMrs(String strMrAndMrs) {
		this.strMrAndMrs = (String) setDefaultValue(strMrAndMrs, "NA");
	}

	public String getDteMembershipCancelledDate() {
		return dteMembershipCancelledDate;
	}

	public void setDteMembershipCancelledDate(String dteMembershipCancelledDate) {
		this.dteMembershipCancelledDate = dteMembershipCancelledDate;
	}

	public String getStrOldCategory() {
		return strOldCategory;
	}

	public void setStrOldCategory(String strOldCategory) {
		this.strOldCategory = (String) setDefaultValue(strOldCategory, "NA");
	}

	public String getDteReInStatementDate() {
		return dteReInStatementDate;
	}

	public void setDteReInStatementDate(String dteReInStatementDate) {
		this.dteReInStatementDate = dteReInStatementDate;
	}

	public String getStrSenior() {
		return strSenior;
	}

	public void setStrSenior(String strSenior) {
		this.strSenior = (String) setDefaultValue(strSenior, "NA");
	}

	public String getDteSeniorDate() {
		return dteSeniorDate;
	}

	public void setDteSeniorDate(String dteSeniorDate) {
		this.dteSeniorDate = dteSeniorDate;
	}

	public String getStrSeniorFlag() {
		return strSeniorFlag;
	}

	public void setStrSeniorFlag(String strSeniorFlag) {
		this.strSeniorFlag = (String) setDefaultValue(strSeniorFlag, "NA");
	}

	public String getStrSenoirMember() {
		return strSenoirMember;
	}

	public void setStrSenoirMember(String strSenoirMember) {
		this.strSenoirMember = (String) setDefaultValue(strSenoirMember, "NA");
	}

	public String getStrStatusCorporateNomineeMship() {
		return strStatusCorporateNomineeMship;
	}

	public void setStrStatusCorporateNomineeMship(String strStatusCorporateNomineeMship) {
		this.strStatusCorporateNomineeMship = (String) setDefaultValue(strStatusCorporateNomineeMship, "NA");
	}

	public String getStrTransferFlag() {
		return strTransferFlag;
	}

	public void setStrTransferFlag(String strTransferFlag) {
		this.strTransferFlag = (String) setDefaultValue(strTransferFlag, "NA");
	}

	public String getStrNomineeName() {
		return strNomineeName;
	}

	public void setStrNomineeName(String strNomineeName) {
		this.strNomineeName = (String) setDefaultValue(strNomineeName, "NA");
	}

	public String getStrOutStationMember() {
		return strOutStationMember;
	}

	public void setStrOutStationMember(String strOutStationMember) {
		this.strOutStationMember = (String) setDefaultValue(strOutStationMember, "NA");
	}

	public String getStrSecurityDeposit() {
		return strSecurityDeposit;
	}

	public void setStrSecurityDeposit(String strSecurityDeposit) {
		this.strSecurityDeposit = (String) setDefaultValue(strSecurityDeposit, "NA");
	}

	public String getStrSuperSenior() {
		return strSuperSenior;
	}

	public void setStrSuperSenior(String strSuperSenior) {
		this.strSuperSenior = (String) setDefaultValue(strSuperSenior, "NA");
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = (String) setDefaultValue(strUserCreated, "NA");
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
		this.strUserModified = (String) setDefaultValue(strUserModified, "NA");
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

}
