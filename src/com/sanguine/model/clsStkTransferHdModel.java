package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "tblstocktransferhd")
@IdClass(clsStkTransferHdModel_ID.class)
public class clsStkTransferHdModel implements Serializable {

	public clsStkTransferHdModel() {
	}

	public clsStkTransferHdModel(clsStkTransferHdModel_ID clsStkTransferHdModel_ID) {
		strSTCode = clsStkTransferHdModel_ID.getStrSTCode();
		strClientCode = clsStkTransferHdModel_ID.getStrClientCode();
	}

	@Column(name = "intId", nullable = false, updatable = false)
	private long intId;

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strSTCode", column = @Column(name = "strSTCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strSTCode")
	private String strSTCode;

	@Column(name = "dtSTDate")
	private String dtSTDate;

	@Column(name = "strFromLocCode", columnDefinition = "VARCHAR(15) NOT NULL default ' '")
	private String strFromLocCode;

	@Column(name = "strToLocCode", columnDefinition = "VARCHAR(15) NOT NULL default ' '")
	private String strToLocCode;

	@Column(name = "strNarration", columnDefinition = "VARCHAR(300) NOT NULL default ' '")
	private String strNarration;

	@Column(name = "strAuthorise", columnDefinition = "VARCHAR(10) NOT NULL default ' '")
	private String strAuthorise;

	@Column(name = "strUserCreated", nullable = false, updatable = false)
	private String strUserCreated;

	@Column(name = "dtCreatedDate", nullable = false, updatable = false)
	private String dtCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dtLastModified")
	private String dtLastModified;
	

	
	@Column(name = "strNo", columnDefinition = "VARCHAR(20) NOT NULL default ' '")
	private String strNo;

	@Column(name = "strMaterialIssue", columnDefinition = "VARCHAR(10) NOT NULL default ' '")
	private String strMaterialIssue;

	@Column(name = "strWOCode", columnDefinition = "VARCHAR(20) NOT NULL default ' '")
	private String strWOCode;

	@Column(name = "strAgainst", columnDefinition = "VARCHAR(50) NOT NULL default ' '")
	private String strAgainst;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "dblTotalAmt", columnDefinition = "Decimal(18,4) NOT NULL default '0.00'")
	private String dblTotalAmt;
	
	@Column(name = "strReqCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	private String strReqCode;

	@Transient
	private String strFromLocName;

	@Transient
	private String strToLocName;

	public String getStrSTCode() {
		return strSTCode;
	}

	public void setStrSTCode(String strSTCode) {
		this.strSTCode = strSTCode;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getDtSTDate() {
		return dtSTDate;
	}

	public void setDtSTDate(String dtSTDate) {
		this.dtSTDate = dtSTDate;
	}

	public String getStrFromLocCode() {
		return strFromLocCode;
	}

	public void setStrFromLocCode(String strFromLocCode) {
		this.strFromLocCode = strFromLocCode;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getDtCreatedDate() {
		return dtCreatedDate;
	}

	public void setDtCreatedDate(String dtCreatedDate) {
		this.dtCreatedDate = dtCreatedDate;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrToLocCode() {
		return strToLocCode;
	}

	public void setStrToLocCode(String strToLocCode) {
		this.strToLocCode = strToLocCode;
	}

	public String getStrNo() {
		return strNo;
	}

	public void setStrNo(String strNo) {
		this.strNo = strNo;
	}

	public String getStrMaterialIssue() {
		return strMaterialIssue;
	}

	public void setStrMaterialIssue(String strMaterialIssue) {
		this.strMaterialIssue = strMaterialIssue;
	}

	public String getStrWOCode() {
		return strWOCode;
	}

	public void setStrWOCode(String strWOCode) {
		this.strWOCode = strWOCode;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst =  (String) setDefaultValue(strAgainst, "");;
	}

	public String getStrFromLocName() {
		return strFromLocName;
	}

	public void setStrFromLocName(String strFromLocName) {
		this.strFromLocName = strFromLocName;
	}

	public String getStrToLocName() {
		return strToLocName;
	}

	public void setStrToLocName(String strToLocName) {
		this.strToLocName = strToLocName;
	}

	@Column(name = "intLevel", updatable=false, columnDefinition = "Int(8) default '0'")
	private int intLevel = 0;

	public int getIntLevel() {
		return intLevel;
	}

	public void setIntLevel(int intLevel) {
		this.intLevel = (int) setDefaultValue(intLevel, "0");
	}

	private Object setDefaultValue(Object value, Object defaultValue) {
		if (value != null && (value instanceof String && value.toString().length() > 0)) {
			return value;
		} else if (value != null && !(value instanceof String)) {
			return value;

		} else {
			return defaultValue;
		}
		// return value !=null && (value instanceof String &&
		// value.toString().length()>0) ? value : defaultValue;
	}

	public String getDblTotalAmt() {
		return dblTotalAmt;
	}

	public void setDblTotalAmt(String dblTotalAmt) {
		this.dblTotalAmt = dblTotalAmt;
	}

	public String getStrReqCode() {
		return strReqCode;
	}

	public void setStrReqCode(String strReqCode) {
		this.strReqCode =(String) setDefaultValue(strReqCode, ""); 
	}

	
	
}
