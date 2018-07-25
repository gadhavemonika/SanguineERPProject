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
@Table(name = "tblmemberformgeneration")
@IdClass(clsWebClubMemberFormGenerationModel_ID.class)
public class clsWebClubMemberFormGenerationModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsWebClubMemberFormGenerationModel() {
	}

	public clsWebClubMemberFormGenerationModel(clsWebClubMemberFormGenerationModel_ID objModelID) {
		strFormNo = objModelID.getStrFormNo();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strFormNo", column = @Column(name = "intFormNo")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "intGId")
	private long intGId;

	@Column(name = "intFormNo")
	private String strFormNo;

	@Column(name = "strPrint")
	private String strPrint;

	@Column(name = "strRePrint")
	private String strRePrint;

	@Column(name = "dtePrintDate")
	private String dtePrintDate;

	@Column(name = "strMemberCode")
	private String strMemberCode;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "dteCreatedDate")
	private String dteCreatedDate;

	@Column(name = "dteLastModified")
	private String dteLastModified;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "intReprintCount")
	private long intReprintCount;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "strProspectName")
	private String strProspectName;

	@Column(name = "strCategoryCode")
	private String strCategoryCode;

	// Setter-Getter Methods
	public long getIntGId() {
		return intGId;
	}

	public void setIntGId(long intGId) {
		this.intGId = (Long) setDefaultValue(intGId, "NA");
	}

	public String getStrFormNo() {
		return strFormNo;
	}

	public void setStrFormNo(String strFormNo) {
		this.strFormNo = strFormNo;
	}

	public String getStrPrint() {
		return strPrint;
	}

	public void setStrPrint(String strPrint) {
		this.strPrint = (String) setDefaultValue(strPrint, "NA");
	}

	public String getStrRePrint() {
		return strRePrint;
	}

	public void setStrRePrint(String strRePrint) {
		this.strRePrint = (String) setDefaultValue(strRePrint, "NA");
	}

	public String getDtePrintDate() {
		return dtePrintDate;
	}

	public void setDtePrintDate(String dtePrintDate) {
		this.dtePrintDate = dtePrintDate;
	}

	public String getStrMemberCode() {
		return strMemberCode;
	}

	public void setStrMemberCode(String strMemberCode) {
		this.strMemberCode = (String) setDefaultValue(strMemberCode, "NA");
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

	public String getDteLastModified() {
		return dteLastModified;
	}

	public void setDteLastModified(String dteLastModified) {
		this.dteLastModified = dteLastModified;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = (String) setDefaultValue(strUserModified, "NA");
	}

	public long getIntReprintCount() {
		return intReprintCount;
	}

	public void setIntReprintCount(long intReprintCount) {
		this.intReprintCount = (Long) setDefaultValue(intReprintCount, "NA");
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

	public String getStrProspectName() {
		return strProspectName;
	}

	public void setStrProspectName(String strProspectName) {
		this.strProspectName = (String) setDefaultValue(strProspectName, "NA");
	}

	public String getStrCategoryCode() {
		return strCategoryCode;
	}

	public void setStrCategoryCode(String strCategoryCode) {
		this.strCategoryCode = (String) setDefaultValue(strCategoryCode, "NA");
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
