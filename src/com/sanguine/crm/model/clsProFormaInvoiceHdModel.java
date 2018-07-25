package com.sanguine.crm.model;

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

import org.hibernate.annotations.CollectionOfElements;

import com.sanguine.webbooks.model.clsJVDebtorDtlModel;
import com.sanguine.webbooks.model.clsJVDtlModel;

@Entity
@Table(name = "tblproformainvoicehd")
@IdClass(clsProFormaInvoiceHdModel_ID.class)
public class clsProFormaInvoiceHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsProFormaInvoiceHdModel() {
	}

	public clsProFormaInvoiceHdModel(clsProFormaInvoiceHdModel_ID objModelID) {
		strInvCode = objModelID.getStrInvCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@CollectionOfElements(fetch = FetchType.LAZY)
	@JoinTable(name = "tblproformainvoicedtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strInvCode") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strInvCode", column = @Column(name = "strInvCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsProFormaInvoiceModelDtl> listInvDtlModel = new ArrayList<clsProFormaInvoiceModelDtl>();

	@CollectionOfElements(fetch = FetchType.LAZY)
	@JoinTable(name = "tblproformainvtaxdtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strInvCode") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strInvCode", column = @Column(name = "strInvCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsProFormaInvoiceTaxDtlModel> listInvTaxDtlModel = new ArrayList<clsProFormaInvoiceTaxDtlModel>();

	@CollectionOfElements(fetch = FetchType.LAZY)
	@JoinTable(name = "tblproformainvprodtaxdtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strInvCode") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strInvCode", column = @Column(name = "strInvCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsProFormaInvoiceProdTaxDtl> listInvProdTaxDtlModel = new ArrayList<clsProFormaInvoiceProdTaxDtl>();

	@CollectionOfElements(fetch = FetchType.LAZY)
	@JoinTable(name = "tblproforminvsalesorderdtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strInvCode") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strInvCode", column = @Column(name = "strInvCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsProFormaInvSalesOrderDtl> listInvSalesOrderModel = new ArrayList<clsProFormaInvSalesOrderDtl>();

	// Variable Declaration
	@Column(name = "strInvCode")
	private String strInvCode;

	@Column(name = "intid")
	private long intid;

	@Column(name = "dteInvDate")
	private String dteInvDate;

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

	@Column(name = "strPackNo", columnDefinition = "VARCHAR(100) NOT NULL default ''")
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

	@Column(name = "strInvNo")
	private String strInvNo;

	@Column(name = "strReaCode")
	private String strReaCode;

	@Column(name = "strSerialNo", columnDefinition = "VARCHAR(100) NOT NULL default ''")
	private String strSerialNo;

	@Column(name = "strWarrPeriod")
	private String strWarrPeriod;

	@Column(name = "strWarraValidity")
	private String strWarraValidity;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "dblTaxAmt")
	private double dblTaxAmt;

	@Column(name = "dblTotalAmt")
	private double dblTotalAmt;

	@Column(name = "dblSubTotalAmt")
	private double dblSubTotalAmt;

	@Column(name = "dblGrandTotal")
	private double dblGrandTotal;

	@Column(name = "dblDiscount")
	private double dblDiscount;

	@Column(name = "dblDiscountAmt")
	private double dblDiscountAmt;

	@Column(name = "strExciseable")
	private String strExciseable;

	@Column(name = "strDulpicateFlag")
	private String strDulpicateFlag;

	@Column(name = "strSettlementCode")
	private String strSettlementCode;

	@Column(name = "strCurrencyCode")
	private String strCurrencyCode;

	@Column(name = "dblCurrencyConv")
	private double dblCurrencyConv;

	@Column(name = "strMobileNo")
	private String strMobileNo;
	
	@Column(name = "intLevel", updatable=false, columnDefinition = "Int(8) default '0'")
	private int intLevel = 0;

	public int getIntLevel() {
		return intLevel;
	}

	public void setIntLevel(int intLevel) {
		this.intLevel = (int) setDefaultValue(intLevel, "0");
	}
	

	public String getStrMobileNo() {
		return strMobileNo;
	}

	public void setStrMobileNo(String strMobileNo) {
		this.strMobileNo = strMobileNo;
	}

	public String getStrInvCode() {
		return strInvCode;
	}

	public void setStrInvCode(String strInvCode) {
		this.strInvCode = strInvCode;
	}

	public long getIntid() {
		return intid;
	}

	public void setIntid(long intid) {
		this.intid = intid;
	}

	public String getDteInvDate() {
		return dteInvDate;
	}

	public void setDteInvDate(String dteInvDate) {
		this.dteInvDate = dteInvDate;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = (String) setDefaultValue(strAgainst, "");
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
		this.strCustCode = strCustCode;
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
		;
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
		this.strLocCode = strLocCode;
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
		this.strUserCreated = strUserCreated;
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
		this.strUserModified = strUserModified;
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
		this.strAuthorise = (String) setDefaultValue(strAuthorise, "No");
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

	public String getStrInvNo() {
		return strInvNo;
	}

	public void setStrInvNo(String strInvNo) {
		this.strInvNo = (String) setDefaultValue(strInvNo, "");
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
		this.strClientCode = strClientCode;
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

	public double getDblTaxAmt() {
		return dblTaxAmt;
	}

	public void setDblTaxAmt(double dblTaxAmt) {
		this.dblTaxAmt = dblTaxAmt;
	}

	public double getDblTotalAmt() {
		return dblTotalAmt;
	}

	public void setDblTotalAmt(double dblTotalAmt) {
		this.dblTotalAmt = dblTotalAmt;
	}

	public double getDblSubTotalAmt() {
		return dblSubTotalAmt;
	}

	public void setDblSubTotalAmt(double dblSubTotalAmt) {
		this.dblSubTotalAmt = dblSubTotalAmt;
	}

	public double getDblDiscount() {
		return dblDiscount;
	}

	public void setDblDiscount(double dblDiscount) {
		this.dblDiscount = dblDiscount;
	}

	public double getDblDiscountAmt() {
		return dblDiscountAmt;
	}

	public void setDblDiscountAmt(double dblDiscountAmt) {
		this.dblDiscountAmt = dblDiscountAmt;
	}

	public List<clsProFormaInvoiceModelDtl> getListInvDtlModel() {
		return listInvDtlModel;
	}

	public void setListInvDtlModel(List<clsProFormaInvoiceModelDtl> listInvDtlModel) {
		this.listInvDtlModel = listInvDtlModel;
	}

	public List<clsProFormaInvoiceTaxDtlModel> getListInvTaxDtlModel() {
		return listInvTaxDtlModel;
	}

	public void setListInvTaxDtlModel(List<clsProFormaInvoiceTaxDtlModel> listInvTaxDtlModel) {
		this.listInvTaxDtlModel = listInvTaxDtlModel;
	}

	public List<clsProFormaInvoiceProdTaxDtl> getListInvProdTaxDtlModel() {
		return listInvProdTaxDtlModel;
	}

	public void setListInvProdTaxDtlModel(List<clsProFormaInvoiceProdTaxDtl> listInvProdTaxDtlModel) {
		this.listInvProdTaxDtlModel = listInvProdTaxDtlModel;
	}

	public String getStrExciseable() {
		return strExciseable;
	}

	public void setStrExciseable(String strExciseable) {
		this.strExciseable = strExciseable;
	}

	public List<clsProFormaInvSalesOrderDtl> getListInvSalesOrderModel() {
		return listInvSalesOrderModel;
	}

	public void setListInvSalesOrderModel(List<clsProFormaInvSalesOrderDtl> listInvSalesOrderModel) {
		this.listInvSalesOrderModel = listInvSalesOrderModel;
	}

	public String getStrDulpicateFlag() {
		return strDulpicateFlag;
	}

	public void setStrDulpicateFlag(String strDulpicateFlag) {
		this.strDulpicateFlag = strDulpicateFlag;
	}

	public double getDblGrandTotal() {
		return dblGrandTotal;
	}

	public void setDblGrandTotal(double dblGrandTotal) {
		this.dblGrandTotal = dblGrandTotal;
	}

	public String getStrCurrencyCode() {
		return strCurrencyCode;
	}

	public void setStrCurrencyCode(String strCurrencyCode) {
		this.strCurrencyCode = (String) setDefaultValue(strCurrencyCode, "");
	}

	public double getDblCurrencyConv() {
		return dblCurrencyConv;
	}

	public void setDblCurrencyConv(double dblCurrencyConv) {
		this.dblCurrencyConv = dblCurrencyConv;
	}

}
