package com.sanguine.webbooks.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionOfElements;

@Entity
@Table(name = "tbljvhd")
@IdClass(clsJVModel_ID.class)
public class clsJVHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsJVHdModel() {
	}

	public clsJVHdModel(clsJVModel_ID objModelID) {
		strVouchNo = objModelID.getStrVouchNo();
		strClientCode = objModelID.getStrClientCode();
	}

	@CollectionOfElements(fetch = FetchType.LAZY)
	// @OneToMany(fetch = FetchType.LAZY, mappedBy = "tbljvhd")
	@JoinTable(name = "tbljvdtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strVouchNo") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strVouchNo", column = @Column(name = "strVouchNo")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsJVDtlModel> listJVDtlModel = new ArrayList<clsJVDtlModel>();

	@CollectionOfElements(fetch = FetchType.LAZY)
	// @OneToMany(fetch = FetchType.LAZY, mappedBy = "tbljvhd")
	@JoinTable(name = "tbljvdebtordtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strVouchNo") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strVouchNo", column = @Column(name = "strVouchNo")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsJVDebtorDtlModel> listJVDebtorDtlModel = new ArrayList<clsJVDebtorDtlModel>();

	public List<clsJVDtlModel> getListJVDtlModel() {
		return listJVDtlModel;
	}

	public void setListJVDtlModel(List<clsJVDtlModel> listJVDtlModel) {
		this.listJVDtlModel = listJVDtlModel;
	}

	public List<clsJVDebtorDtlModel> getListJVDebtorDtlModel() {
		return listJVDebtorDtlModel;
	}

	public void setListJVDebtorDtlModel(List<clsJVDebtorDtlModel> listJVDebtorDtlModel) {
		this.listJVDebtorDtlModel = listJVDebtorDtlModel;
	}

	// Variable Declaration
	@Column(name = "strVouchNo")
	private String strVouchNo;

	@Column(name = "intId")
	private long intId;

	@Column(name = "strNarration")
	private String strNarration;

	@Column(name = "strSancCode")
	private String strSancCode;

	@Column(name = "strType")
	private String strType;

	@Column(name = "dteVouchDate")
	private String dteVouchDate;

	@Column(name = "intVouchMonth")
	private long intVouchMonth;

	@Column(name = "dblAmt")
	private double dblAmt;

	@Column(name = "strTransType")
	private String strTransType;

	@Column(name = "strTransMode")
	private String strTransMode;

	@Column(name = "strModuleType")
	private String strModuleType;

	@Column(name = "strMasterPOS")
	private String strMasterPOS;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dteDateCreated", updatable = false)
	private String dteDateCreated;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "intVouchNum")
	private String intVouchNum;
	
	@Column(name = "strSource")
	private String strSource;
	
	@Column(name = "strSourceDocNo")
	private String strSourceDocNo;
	
	@Column(name = "strCurrency",columnDefinition = "varchar(10) NOT NULL default ''")
	private String strCurrency;
	
	@Column(name = "dblConversion",columnDefinition = "double(18,2) NOT NULL default '1'")
	private double dblConversion;
	

	// Setter-Getter Methods
	public String getStrVouchNo() {
		return strVouchNo;
	}

	public void setStrVouchNo(String strVouchNo) {
		this.strVouchNo = (String) setDefaultValue(strVouchNo, "NA");
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = (Long) setDefaultValue(intId, "0");
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = (String) setDefaultValue(strNarration, "");
	}

	public String getStrSancCode() {
		return strSancCode;
	}

	public void setStrSancCode(String strSancCode) {
		this.strSancCode = (String) setDefaultValue(strSancCode, "NA");
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = (String) setDefaultValue(strType, "NA");
	}

	public String getDteVouchDate() {
		return dteVouchDate;
	}

	public void setDteVouchDate(String dteVouchDate) {
		this.dteVouchDate = dteVouchDate;
	}

	public long getIntVouchMonth() {
		return intVouchMonth;
	}

	public void setIntVouchMonth(long intVouchMonth) {
		this.intVouchMonth = (Long) setDefaultValue(intVouchMonth, "0");
	}

	public double getDblAmt() {
		return dblAmt;
	}

	public void setDblAmt(double dblAmt) {
		this.dblAmt = (Double) setDefaultValue(dblAmt, "0.0000");
	}

	public String getStrTransType() {
		return strTransType;
	}

	public void setStrTransType(String strTransType) {
		this.strTransType = (String) setDefaultValue(strTransType, "NA");
	}

	public String getStrTransMode() {
		return strTransMode;
	}

	public void setStrTransMode(String strTransMode) {
		this.strTransMode = (String) setDefaultValue(strTransMode, "NA");
	}

	public String getStrModuleType() {
		return strModuleType;
	}

	public void setStrModuleType(String strModuleType) {
		this.strModuleType = (String) setDefaultValue(strModuleType, "NA");
	}

	public String getStrMasterPOS() {
		return strMasterPOS;
	}

	public void setStrMasterPOS(String strMasterPOS) {
		this.strMasterPOS = (String) setDefaultValue(strMasterPOS, "NA");
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

	public String getStrSource() {
		return strSource;
	}

	public void setStrSource(String strSource) {
		this.strSource = strSource;
	}

	public String getStrSourceDocNo() {
		return strSourceDocNo;
	}

	public void setStrSourceDocNo(String strSourceDocNo) {
		this.strSourceDocNo = strSourceDocNo;
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

	public String getIntVouchNum() {
		return intVouchNum;
	}

	public void setIntVouchNum(String intVouchNum) {
		this.intVouchNum = intVouchNum;
	}
	
	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = (String) setDefaultValue(strCurrency, "");
	}

	public double getDblConversion() {
		return dblConversion;
	}

	public void setDblConversion(double dblConversion) {
		this.dblConversion = (double) setDefaultValue(dblConversion,1.0);
	}


}
