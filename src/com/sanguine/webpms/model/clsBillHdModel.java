package com.sanguine.webpms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
@Table(name = "tblbillhd")
@IdClass(clsBillModel_ID.class)
public class clsBillHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsBillHdModel() {
	}

	public clsBillHdModel(clsBillModel_ID objModelID) {
		strBillNo = objModelID.getStrBillNo();
		strClientCode = objModelID.getStrClientCode();
	}

	@CollectionOfElements(fetch = FetchType.LAZY)
	@JoinTable(name = "tblbilldtl", joinColumns = { @JoinColumn(name = "strBillNo"), @JoinColumn(name = "strClientCode") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strBillNo", column = @Column(name = "strBillNo")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsBillDtlModel> listBillDtlModels = new ArrayList<clsBillDtlModel>();
	// private Set<clsBillDtlModel> setBillDtlModels=new
	// TreeSet<clsBillDtlModel>();

	@CollectionOfElements(fetch = FetchType.LAZY)
	@JoinTable(name = "tblbilltaxdtl", joinColumns = { @JoinColumn(name = "strBillNo"), @JoinColumn(name = "strClientCode") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strBillNo", column = @Column(name = "strBillNo")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsBillTaxDtlModel> listBillTaxDtlModels = new ArrayList<clsBillTaxDtlModel>();

	// private Set<clsBillTaxDtlModel> setBillTaxDtlModels=new
	// TreeSet<clsBillTaxDtlModel>();

	// Variable Declaration
	@Column(name = "strBillNo")
	private String strBillNo;

	@Column(name = "dteBillDate")
	private String dteBillDate;

	@Column(name = "strFolioNo")
	private String strFolioNo;

	@Column(name = "strRoomNo")
	private String strRoomNo;

	@Column(name = "strCheckInNo")
	private String strCheckInNo;

	@Column(name = "strRegistrationNo")
	private String strRegistrationNo;

	@Column(name = "strReservationNo")
	private String strReservationNo;

	@Column(name = "dblGrandTotal")
	private double dblGrandTotal;

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

	@Column(name = "strExtraBedCode")
	private String strExtraBedCode;

	@Transient
	private String strSplitType;

	@Column(name = "strBillSettled")
	private String strBillSettled;
	
	@Column(name = "strGSTNo")
	private String strGSTNo;
	
	@Column(name = "strCompanyName")
	private String strCompanyName;

	public String getStrCompanyName() {
		return strCompanyName;
	}

	public void setStrCompanyName(String strCompanyName) {
		this.strCompanyName = strCompanyName;
	}

	public String getStrGSTNo() {
		return strGSTNo;
	}

	public void setStrGSTNo(String strGSTNo) {
		this.strGSTNo = strGSTNo;
	}

	// Setter-Getter Methods
	public String getStrBillNo() {
		return strBillNo;
	}

	public void setStrBillNo(String strBillNo) {
		this.strBillNo = (String) setDefaultValue(strBillNo, "");
	}

	public String getDteBillDate() {
		return dteBillDate;
	}

	public void setDteBillDate(String dteBillDate) {
		this.dteBillDate = dteBillDate;
	}

	public String getStrFolioNo() {
		return strFolioNo;
	}

	public void setStrFolioNo(String strFolioNo) {
		this.strFolioNo = (String) setDefaultValue(strFolioNo, "");
	}

	public String getStrRegistrationNo() {
		return strRegistrationNo;
	}

	public void setStrRegistrationNo(String strRegistrationNo) {
		this.strRegistrationNo = (String) setDefaultValue(strRegistrationNo, "");
	}

	public String getStrReservationNo() {
		return strReservationNo;
	}

	public void setStrReservationNo(String strReservationNo) {
		this.strReservationNo = (String) setDefaultValue(strReservationNo, "");
	}

	public double getDblGrandTotal() {
		return dblGrandTotal;
	}

	public void setDblGrandTotal(double dblGrandTotal) {
		this.dblGrandTotal = (Double) setDefaultValue(dblGrandTotal, "0.0000");
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = (String) setDefaultValue(strUserCreated, "");
	}

	public String getStrUserEdited() {
		return strUserEdited;
	}

	public void setStrUserEdited(String strUserEdited) {
		this.strUserEdited = (String) setDefaultValue(strUserEdited, "");
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
		this.strClientCode = (String) setDefaultValue(strClientCode, "");
	}

	public String getStrCheckInNo() {
		return strCheckInNo;
	}

	public void setStrCheckInNo(String strCheckInNo) {
		this.strCheckInNo = strCheckInNo;
	}

	public String getStrRoomNo() {
		return strRoomNo;
	}

	public void setStrRoomNo(String strRoomNo) {
		this.strRoomNo = strRoomNo;
	}

	public List<clsBillDtlModel> getListBillDtlModels() {
		return listBillDtlModels;
	}

	public void setListBillDtlModels(List<clsBillDtlModel> listBillDtlModels) {
		this.listBillDtlModels = listBillDtlModels;
	}

	public String getStrExtraBedCode() {
		return strExtraBedCode;
	}

	public void setStrExtraBedCode(String strExtraBedCode) {
		this.strExtraBedCode = strExtraBedCode;
	}

	public List<clsBillTaxDtlModel> getListBillTaxDtlModels() {
		return listBillTaxDtlModels;
	}

	public void setListBillTaxDtlModels(List<clsBillTaxDtlModel> listBillTaxDtlModels) {
		this.listBillTaxDtlModels = listBillTaxDtlModels;
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

	public String getStrSplitType() {
		return strSplitType;
	}

	public void setStrSplitType(String strSplitType) {
		this.strSplitType = strSplitType;
	}

	public String getStrBillSettled() {
		return strBillSettled;
	}

	public void setStrBillSettled(String strBillSettled) {
		this.strBillSettled = (String) setDefaultValue(strBillSettled, "N");
	}

	/*
	 * public Set<clsBillDtlModel> getSetBillDtlModels() { return
	 * setBillDtlModels; }
	 * 
	 * public void setSetBillDtlModels(Set<clsBillDtlModel> setBillDtlModels) {
	 * this.setBillDtlModels = setBillDtlModels; }
	 * 
	 * public Set<clsBillTaxDtlModel> getSetBillTaxDtlModels() { return
	 * setBillTaxDtlModels; }
	 * 
	 * public void setSetBillTaxDtlModels(Set<clsBillTaxDtlModel>
	 * setBillTaxDtlModels) { this.setBillTaxDtlModels = setBillTaxDtlModels; }
	 */

}
