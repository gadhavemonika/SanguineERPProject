package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tbldeliverychallanhd")
@IdClass(clsDeliveryChallanHdModel_ID.class)
public class clsDeliveryChallanHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsDeliveryChallanHdModel() {
	}

	public clsDeliveryChallanHdModel(clsDeliveryChallanHdModel_ID objModelID) {
		strDCCode = objModelID.getStrDCCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strDCCode", column = @Column(name = "strDCCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strDCCode")
	private String strDCCode;

	@Column(name = "intid")
	private long intid;

	@Column(name = "dteDCDate")
	private String dteDCDate;

	@Column(name = "strAgainst")
	private String strAgainst;

	@Column(name = "strSOCode")
	private String strSOCode;

	@Column(name = "strCustCode")
	private String strCustCode;

	@Column(name = "strPONo")
	private String strPONo;

	@Column(name = "strNarration")
	private String strNarration;

	@Column(name = "strPackNo")
	private String strPackNo;

	@Column(name = "strLocCode")
	private String strLocCode;

	@Column(name = "strVehNo")
	private String strVehNo;

	@Column(name = "strMInBy")
	private String strMInBy;

	@Column(name = "strTimeInOut")
	private String strTimeInOut;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "dteCreatedDate", updatable = false)
	private String dteCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteLastModified")
	private String dteLastModified;

	@Column(name = "strAuthorise")
	private String strAuthorise;

	@Column(name = "strDktNo")
	private String strDktNo;

	@Column(name = "strSAdd1")
	private String strSAdd1;

	@Column(name = "strSAdd2")
	private String strSAdd2;

	@Column(name = "strSCity")
	private String strSCity;

	@Column(name = "strSState")
	private String strSState;

	@Column(name = "strSCtry")
	private String strSCtry;

	@Column(name = "strSPin")
	private String strSPin;

	@Column(name = "strDCNo")
	private String strDCNo;

	@Column(name = "strReaCode")
	private String strReaCode;

	@Column(name = "strSerialNo")
	private String strSerialNo;

	@Column(name = "strWarrPeriod")
	private String strWarrPeriod;

	@Column(name = "strWarraValidity")
	private String strWarraValidity;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strCloseDC", columnDefinition = "CHAR(1) NOT NULL default 'N'")
	private String strCloseDC;

	@Column(name = "strSettlementCode")
	private String strSettlementCode;

	// Setter-Getter Methods
	public String getStrDCCode() {
		return strDCCode;
	}

	public void setStrDCCode(String strDCCode) {
		this.strDCCode = (String) setDefaultValue(strDCCode, "");
	}

	public long getIntid() {
		return intid;
	}

	public void setIntid(long intid) {
		this.intid = (Long) setDefaultValue(intid, "");
	}

	public String getDteDCDate() {
		return dteDCDate;
	}

	public void setDteDCDate(String dteDCDate) {
		this.dteDCDate = dteDCDate;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = (String) setDefaultValue(strAgainst, "N");
	}

	public String getStrSOCode() {
		return strSOCode;
	}

	public void setStrSOCode(String strSOCode) {
		this.strSOCode = (String) setDefaultValue(strSOCode, "");
	}

	public String getStrCustCode() {
		return strCustCode;
	}

	public void setStrCustCode(String strCustCode) {
		this.strCustCode = (String) setDefaultValue(strCustCode, "");
	}

	public String getStrPONo() {
		return strPONo;
	}

	public void setStrPONo(String strPONo) {
		this.strPONo = (String) setDefaultValue(strPONo, "");
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = (String) setDefaultValue(strNarration, "");
	}

	public String getStrPackNo() {
		return strPackNo;
	}

	public void setStrPackNo(String strPackNo) {
		this.strPackNo = (String) setDefaultValue(strPackNo, "");
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = (String) setDefaultValue(strLocCode, "");
	}

	public String getStrVehNo() {
		return strVehNo;
	}

	public void setStrVehNo(String strVehNo) {
		this.strVehNo = (String) setDefaultValue(strVehNo, "");
	}

	public String getStrMInBy() {
		return strMInBy;
	}

	public void setStrMInBy(String strMInBy) {
		this.strMInBy = (String) setDefaultValue(strMInBy, "");
	}

	public String getStrTimeInOut() {
		return strTimeInOut;
	}

	public void setStrTimeInOut(String strTimeInOut) {
		this.strTimeInOut = (String) setDefaultValue(strTimeInOut, "");
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = (String) setDefaultValue(strUserCreated, "");
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
		this.strUserModified = (String) setDefaultValue(strUserModified, "");
	}

	public String getDteLastModified() {
		return dteLastModified;
	}

	public void setDteLastModified(String dteLastModified) {
		this.dteLastModified = dteLastModified;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = (String) setDefaultValue(strAuthorise, "");
	}

	public String getStrDktNo() {
		return strDktNo;
	}

	public void setStrDktNo(String strDktNo) {
		this.strDktNo = (String) setDefaultValue(strDktNo, "");
	}

	public String getStrSAdd1() {
		return strSAdd1;
	}

	public void setStrSAdd1(String strSAdd1) {
		this.strSAdd1 = (String) setDefaultValue(strSAdd1, "");
	}

	public String getStrSAdd2() {
		return strSAdd2;
	}

	public void setStrSAdd2(String strSAdd2) {
		this.strSAdd2 = (String) setDefaultValue(strSAdd2, "");
	}

	public String getStrSCity() {
		return strSCity;
	}

	public void setStrSCity(String strSCity) {
		this.strSCity = (String) setDefaultValue(strSCity, "");
	}

	public String getStrSState() {
		return strSState;
	}

	public void setStrSState(String strSState) {
		this.strSState = (String) setDefaultValue(strSState, "");
	}

	public String getStrSCtry() {
		return strSCtry;
	}

	public void setStrSCtry(String strSCtry) {
		this.strSCtry = (String) setDefaultValue(strSCtry, "");
	}

	public String getStrSPin() {
		return strSPin;
	}

	public void setStrSPin(String strSPin) {
		this.strSPin = (String) setDefaultValue(strSPin, "");
	}

	public String getStrDCNo() {
		return strDCNo;
	}

	public void setStrDCNo(String strDCNo) {
		this.strDCNo = (String) setDefaultValue(strDCNo, "");
	}

	public String getStrReaCode() {
		return strReaCode;
	}

	public void setStrReaCode(String strReaCode) {
		this.strReaCode = (String) setDefaultValue(strReaCode, "");
	}

	public String getStrSerialNo() {
		return strSerialNo;
	}

	public void setStrSerialNo(String strSerialNo) {
		this.strSerialNo = (String) setDefaultValue(strSerialNo, "");
	}

	public String getStrWarrPeriod() {
		return strWarrPeriod;
	}

	public void setStrWarrPeriod(String strWarrPeriod) {
		this.strWarrPeriod = (String) setDefaultValue(strWarrPeriod, "");
	}

	public String getStrWarraValidity() {
		return strWarraValidity;
	}

	public void setStrWarraValidity(String strWarraValidity) {
		this.strWarraValidity = (String) setDefaultValue(strWarraValidity, "");
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = (String) setDefaultValue(strClientCode, "");
	}

	public String getStrSettlementCode() {
		return strSettlementCode;
	}

	public void setStrSettlementCode(String strSettlementCode) {
		this.strSettlementCode = (String) setDefaultValue(strSettlementCode, "");
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

	public String getStrCloseDC() {
		return strCloseDC;
	}

	public void setStrCloseDC(String strCloseDC) {
		this.strCloseDC = (String) setDefaultValue(strCloseDC, "N");
	}

}
