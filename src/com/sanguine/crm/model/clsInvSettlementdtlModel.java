package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblinvsettlementdtl")
@IdClass(clsInvSettlementdtlModel_ID.class)
public class clsInvSettlementdtlModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsInvSettlementdtlModel() {
	}

	public clsInvSettlementdtlModel(clsInvSettlementdtlModel_ID objModelID) {
		strInvCode = objModelID.getStrInvCode();
		strSettlementCode = objModelID.getStrSettlementCode();
		strClientCode = objModelID.getStrClientCode();
		dteInvDate = objModelID.getDteInvDate();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strInvCode", column = @Column(name = "strInvCode")), @AttributeOverride(name = "strSettlementCode", column = @Column(name = "strSettlementCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")), @AttributeOverride(name = "dteInvDate", column = @Column(name = "dteInvDate")) })
	// Variable Declaration
	@Column(name = "strInvCode")
	private String strInvCode;

	@Column(name = "strSettlementCode")
	private String strSettlementCode;

	@Column(name = "dblSettlementAmt")
	private double dblSettlementAmt;

	@Column(name = "dblPaidAmt")
	private double dblPaidAmt;

	@Column(name = "strExpiryDate")
	private String strExpiryDate;

	@Column(name = "strCardName")
	private String strCardName;

	@Column(name = "strRemark")
	private String strRemark;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strCustomerCode")
	private String strCustomerCode;

	@Column(name = "dblActualAmt")
	private double dblActualAmt;

	@Column(name = "dblRefundAmt")
	private double dblRefundAmt;

	@Column(name = "strGiftVoucherCode")
	private String strGiftVoucherCode;

	@Column(name = "strDataPostFlag")
	private String strDataPostFlag;

	@Column(name = "strFolioNo")
	private String strFolioNo;

	@Column(name = "strRoomNo")
	private String strRoomNo;

	@Column(name = "dteInvDate")
	private String dteInvDate;

	@Transient
	private String strSettlementName;

	// Setter-Getter Methods
	public String getStrInvCode() {
		return strInvCode;
	}

	public void setStrInvCode(String strInvCode) {
		this.strInvCode = (String) setDefaultValue(strInvCode, "");
	}

	public String getStrSettlementCode() {
		return strSettlementCode;
	}

	public void setStrSettlementCode(String strSettlementCode) {
		this.strSettlementCode = (String) setDefaultValue(strSettlementCode, "");
	}

	public double getDblSettlementAmt() {
		return dblSettlementAmt;
	}

	public void setDblSettlementAmt(double dblSettlementAmt) {
		this.dblSettlementAmt = (Double) setDefaultValue(dblSettlementAmt, "");
	}

	public double getDblPaidAmt() {
		return dblPaidAmt;
	}

	public void setDblPaidAmt(double dblPaidAmt) {
		this.dblPaidAmt = (Double) setDefaultValue(dblPaidAmt, "");
	}

	public String getStrExpiryDate() {
		return strExpiryDate;
	}

	public void setStrExpiryDate(String strExpiryDate) {
		this.strExpiryDate = (String) setDefaultValue(strExpiryDate, "");
	}

	public String getStrCardName() {
		return strCardName;
	}

	public void setStrCardName(String strCardName) {
		this.strCardName = (String) setDefaultValue(strCardName, "");
	}

	public String getStrRemark() {
		return strRemark;
	}

	public void setStrRemark(String strRemark) {
		this.strRemark = (String) setDefaultValue(strRemark, "");
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = (String) setDefaultValue(strClientCode, "");
	}

	public String getStrCustomerCode() {
		return strCustomerCode;
	}

	public void setStrCustomerCode(String strCustomerCode) {
		this.strCustomerCode = (String) setDefaultValue(strCustomerCode, "");
	}

	public double getDblActualAmt() {
		return dblActualAmt;
	}

	public void setDblActualAmt(double dblActualAmt) {
		this.dblActualAmt = (Double) setDefaultValue(dblActualAmt, "");
	}

	public double getDblRefundAmt() {
		return dblRefundAmt;
	}

	public void setDblRefundAmt(double dblRefundAmt) {
		this.dblRefundAmt = (Double) setDefaultValue(dblRefundAmt, "");
	}

	public String getStrGiftVoucherCode() {
		return strGiftVoucherCode;
	}

	public void setStrGiftVoucherCode(String strGiftVoucherCode) {
		this.strGiftVoucherCode = (String) setDefaultValue(strGiftVoucherCode, "");
	}

	public String getStrDataPostFlag() {
		return strDataPostFlag;
	}

	public void setStrDataPostFlag(String strDataPostFlag) {
		this.strDataPostFlag = (String) setDefaultValue(strDataPostFlag, "");
	}

	public String getStrFolioNo() {
		return strFolioNo;
	}

	public void setStrFolioNo(String strFolioNo) {
		this.strFolioNo = (String) setDefaultValue(strFolioNo, "");
	}

	public String getStrRoomNo() {
		return strRoomNo;
	}

	public void setStrRoomNo(String strRoomNo) {
		this.strRoomNo = (String) setDefaultValue(strRoomNo, "");
	}

	public String getDteInvDate() {
		return dteInvDate;
	}

	public void setDteInvDate(String dteInvDate) {
		this.dteInvDate = dteInvDate;
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

	public String getStrSettlementName() {
		return strSettlementName;
	}

	public void setStrSettlementName(String strSettlementName) {
		this.strSettlementName = strSettlementName;
	}

}
