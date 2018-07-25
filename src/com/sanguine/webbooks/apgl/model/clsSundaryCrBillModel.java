package com.sanguine.webbooks.apgl.model;

import java.io.Serializable;
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

@Entity
@Table(name = "tblscbillhd")
@IdClass(clsSundaryCrBillModel_ID.class)
public class clsSundaryCrBillModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsSundaryCrBillModel() {
	}

	public clsSundaryCrBillModel(clsSundaryCrBillModel_ID objModelID) {
		strVoucherNo = objModelID.getStrVoucherNo();
		strClientCode = objModelID.getStrClientCode();
	}

	@CollectionOfElements(fetch = FetchType.LAZY)
	@JoinTable(name = "tblscbilldtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strVoucherNo") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strVoucherNo", column = @Column(name = "strVoucherNo")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsSundaryCrBillDtlModel> listSCBillDtlModel = new ArrayList<clsSundaryCrBillDtlModel>();

	@CollectionOfElements(fetch = FetchType.LAZY)
	@JoinTable(name = "tblscbillgrndtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strVoucherNo") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strVoucherNo", column = @Column(name = "strVoucherNo")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsSundaryCrBillGRNDtlModel> listSCBillGRNDtlModel = new ArrayList<clsSundaryCrBillGRNDtlModel>();

	// Variable Declaration
	@Column(name = "strVoucherNo")
	private String strVoucherNo;

	@Column(name = "strSuppCode")
	private String strSuppCode;

	@Column(name = "strBillNo")
	private String strBillNo;

	@Column(name = "dteBillDate")
	private String dteBillDate;

	@Column(name = "dteDueDate")
	private String dteDueDate;

	@Column(name = "dblTotalAmount")
	private double dblTotalAmount;

	@Column(name = "strModuleType")
	private String strModuleType;

	@Column(name = "dteVoucherDate")
	private String dteVoucherDate;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dteDateCreated")
	private String dteDateCreated;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "strNarration")
	private String strNarration;

	@Column(name = "intVouchNum")
	private long intVouchNum;

	@Column(name = "intVoucherMonth")
	private long intVoucherMonth;

	@Column(name = "strSuppName")
	private String strSuppName;

	@Column(name = "strAcCode")
	private String strAcCode;

	@Column(name = "strAcName")
	private String strAcName;

	@Column(name = "strCreditorCode")
	private String strCreditorCode;

	@Transient
	private String strCreditorName;

	// Setter-Getter Methods
	public String getStrVoucherNo() {
		return strVoucherNo;
	}

	public void setStrVoucherNo(String strVoucherNo) {
		this.strVoucherNo = (String) setDefaultValue(strVoucherNo, "NA");
	}

	public String getStrSuppCode() {
		return strSuppCode;
	}

	public void setStrSuppCode(String strSuppCode) {
		this.strSuppCode = (String) setDefaultValue(strSuppCode, "NA");
	}

	public String getStrBillNo() {
		return strBillNo;
	}

	public void setStrBillNo(String strBillNo) {
		this.strBillNo = (String) setDefaultValue(strBillNo, "NA");
	}

	public String getDteBillDate() {
		return dteBillDate;
	}

	public void setDteBillDate(String dteBillDate) {
		this.dteBillDate = dteBillDate;
	}

	public String getDteDueDate() {
		return dteDueDate;
	}

	public void setDteDueDate(String dteDueDate) {
		this.dteDueDate = dteDueDate;
	}

	public double getDblTotalAmount() {
		return dblTotalAmount;
	}

	public void setDblTotalAmount(double dblTotalAmount) {
		this.dblTotalAmount = (Double) setDefaultValue(dblTotalAmount, "NA");
	}

	public String getStrModuleType() {
		return strModuleType;
	}

	public void setStrModuleType(String strModuleType) {
		this.strModuleType = (String) setDefaultValue(strModuleType, "NA");
	}

	public String getDteVoucherDate() {
		return dteVoucherDate;
	}

	public void setDteVoucherDate(String dteVoucherDate) {
		this.dteVoucherDate = dteVoucherDate;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = (String) setDefaultValue(strUserCreated, "NA");
	}

	public String getStrUserEdited() {
		return strUserEdited;
	}

	public void setStrUserEdited(String strUserEdited) {
		this.strUserEdited = (String) setDefaultValue(strUserEdited, "NA");
	}

	public String getDteDateCreated() {
		return dteDateCreated;
	}

	public void setDteDateCreated(String dteDateCreated) {
		this.dteDateCreated = dteDateCreated;
	}

	public String getDteDateEdited() {
		return dteDateEdited;
	}

	public void setDteDateEdited(String dteDateEdited) {
		this.dteDateEdited = dteDateEdited;
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

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = (String) setDefaultValue(strNarration, "NA");
	}

	public long getIntVouchNum() {
		return intVouchNum;
	}

	public void setIntVouchNum(long intVouchNum) {
		this.intVouchNum = (Long) setDefaultValue(intVouchNum, "NA");
	}

	public long getIntVoucherMonth() {
		return intVoucherMonth;
	}

	public void setIntVoucherMonth(long intVoucherMonth) {
		this.intVoucherMonth = (Long) setDefaultValue(intVoucherMonth, "NA");
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

	public List<clsSundaryCrBillDtlModel> getListSCBillDtlModel() {
		return listSCBillDtlModel;
	}

	public void setListSCBillDtlModel(List<clsSundaryCrBillDtlModel> listSCBillDtlModel) {
		this.listSCBillDtlModel = listSCBillDtlModel;
	}

	public List<clsSundaryCrBillGRNDtlModel> getListSCBillGRNDtlModel() {
		return listSCBillGRNDtlModel;
	}

	public void setListSCBillGRNDtlModel(List<clsSundaryCrBillGRNDtlModel> listSCBillGRNDtlModel) {
		this.listSCBillGRNDtlModel = listSCBillGRNDtlModel;
	}

	public String getStrSuppName() {
		return strSuppName;
	}

	public void setStrSuppName(String strSuppName) {
		this.strSuppName = strSuppName;
	}

	public String getStrAcCode() {
		return strAcCode;
	}

	public void setStrAcCode(String strAcCode) {
		this.strAcCode = strAcCode;
	}

	public String getStrAcName() {
		return strAcName;
	}

	public void setStrAcName(String strAcName) {
		this.strAcName = strAcName;
	}

	public String getStrCreditorCode() {
		return strCreditorCode;
	}

	public void setStrCreditorCode(String strCreditorCode) {
		this.strCreditorCode = strCreditorCode;
	}

	public String getStrCreditorName() {
		return strCreditorName;
	}

	public void setStrCreditorName(String strCreditorName) {
		this.strCreditorName = strCreditorName;
	}

}
