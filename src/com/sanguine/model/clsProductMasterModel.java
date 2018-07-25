package com.sanguine.model;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "tblproductmaster")
@IdClass(clsProductMasterModel_ID.class)
public class clsProductMasterModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public clsProductMasterModel() {
	}

	public clsProductMasterModel(clsProductMasterModel_ID clsProductMasterModel_ID) {
		strProdCode = clsProductMasterModel_ID.getStrProdCode();
		strClientCode = clsProductMasterModel_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strProdCode", column = @Column(name = "strProdCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "strPartNo", nullable = false)
	private String strPartNo;

	@Column(name = "strProdName", nullable = false)
	private String strProdName;

	@Column(name = "strUOM", nullable = false)
	private String strUOM;

	@Column(name = "strSGCode", nullable = false)
	private String strSGCode;

	@Column(name = "strProdType", nullable = false)
	private String strProdType;

	@Column(name = "dblCostRM")
	private double dblCostRM;

	@Column(name = "dblCostManu", nullable = false)
	private double dblCostManu;

	@Column(name = "strLocCode", nullable = false)
	private String strLocCode;

	@Column(name = "dblOrduptoLvl", nullable = false)
	private double dblOrduptoLvl;

	@Column(name = "dblReorderLvl", nullable = false)
	private double dblReorderLvl;

	@Column(name = "strNotInUse", nullable = false)
	private String strNotInUse;

	@Column(name = "strExpDate", nullable = false)
	private String strExpDate;

	@Column(name = "strLotNo", nullable = false)
	private String strLotNo;

	@Column(name = "strRevLevel", nullable = false)
	private String strRevLevel;

	@Column(name = "strSlNo", nullable = false)
	private String strSlNo;

	@Column(name = "strForSale", nullable = false)
	private String strForSale;

	@Column(name = "strSaleNo", nullable = false)
	private String strSaleNo;

	@Column(name = "strDesc", nullable = false)
	private String strDesc;

	@Column(name = "dblUnitPrice", nullable = false)
	private double dblUnitPrice;

	@Column(name = "strTaxIndicator", nullable = false)
	private String strTaxIndicator;

	@Column(name = "strExceedPO", nullable = false)
	private String strExceedPO;

	@Column(name = "strStagDel", nullable = false)
	private String strStagDel;

	@Column(name = "intDelPeriod", nullable = false)
	private int intDelPeriod;

	@Column(name = "strType", nullable = false)
	private String strType;

	@Column(name = "strSpecification", nullable = false)
	private String strSpecification;

	@Column(name = "strProductImage", length = 1000000000, nullable = false)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@JsonIgnore
	private Blob strProductImage;

	@Column(name = "dblWeight", nullable = false)
	private double dblWeight;

	@Column(name = "strBomCal", nullable = false)
	private String strBomCal;

	@Column(name = "strWtUOM", nullable = false)
	private String strWtUOM;

	@Column(name = "strCalAmtOn", nullable = false)
	private String strCalAmtOn;

	@Column(name = "strUserCreated", nullable = false, updatable = false)
	private String strUserCreated;

	@Column(name = "dtCreatedDate", nullable = false, updatable = false)
	private String dtCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dtLastModified")
	private String dtLastModified;

	@Column(name = "dblBatchQty", nullable = false)
	private double dblBatchQty;

	@Column(name = "dblMaxLvl", nullable = false)
	private double dblMaxLvl;

	@Column(name = "strBinNo", nullable = false)
	private String strBinNo;

	@Column(name = "strClass", nullable = false)
	private String strClass;

	@Column(name = "strTariffNo", nullable = false)
	private String strTariffNo;

	@Column(name = "dblListPrice", nullable = false)
	private double dblListPrice;

	@Column(name = "strRemark", nullable = false)
	private String strRemark;

	@Column(name = "strReceivedUOM", nullable = false)
	private String strReceivedUOM;

	@Column(name = "strIssueUOM", nullable = false)
	private String strIssueUOM;

	@Column(name = "strRecipeUOM", nullable = false)
	private String strRecipeUOM;

	@Column(name = "dblReceiveConversion", nullable = false, columnDefinition = "DECIMAL(18,4) NOT NULL default '1.00'")
	private double dblReceiveConversion;

	@Column(name = "dblIssueConversion", nullable = false, columnDefinition = "DECIMAL(18,4) NOT NULL default '1.00'")
	private double dblIssueConversion;

	@Column(name = "dblRecipeConversion", nullable = false, columnDefinition = "DECIMAL(18,4) NOT NULL default '1.00'")
	private double dblRecipeConversion;

	@Column(name = "strClientCode", nullable = false, updatable = false)
	private String strClientCode;

	@Column(name = "intId", nullable = false, updatable = false)
	private Long intId;

	@Column(name = "strNonStockableItem", columnDefinition = "VARCHAR(3) default 'N'")
	private String strNonStockableItem;

	@Column(name = "strPickMRPForTaxCal", columnDefinition = "VARCHAR(1) default 'N'")
	private String strPickMRPForTaxCal;

	@Column(name = "strExciseable", columnDefinition = "VARCHAR(1) default 'N'")
	private String strExciseable;

	@Transient
	private String strPosItemCode;

	@Transient
	private String strSuppPartNo;

	@Transient
	private String strSuppPartDesc;

	@Transient
	private String strLocName;

	@Transient
	private String strSGName;

	@Transient
	private String strSelectedPOSItem;

	@Transient
	private String strPOSItemName;

	@Column(name = "strComesaItem")
	private String strComesaItem;

	@Column(name = "dblYieldPer")
	private double dblYieldPer;

	@Column(name = "strBarCode")
	private String strBarCode;

	@Column(name = "dblMRP", nullable = false, columnDefinition = "DECIMAL(18,2) NOT NULL default '0.00'")
	private double dblMRP;

	@Transient
	private double dblOverQty;

	@Transient
	private double dblAcceptQty;

	@Transient
	private double dblStock;

	@Column(name = "strProdNameMarathi")
	private String strProdNameMarathi;

	@Transient
	private double prevUnitPrice;

	@Transient
	private String prevInvCode;

	@Column(name = "strManufacturerCode")
	private String strManufacturerCode;

	public String getStrSelectedPOSItem() {
		return strSelectedPOSItem;
	}

	public void setStrSelectedPOSItem(String strSelectedPOSItem) {
		this.strSelectedPOSItem = strSelectedPOSItem;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrPartNo() {
		return strPartNo;
	}

	public void setStrPartNo(String strPartNo) {
		this.strPartNo = strPartNo;
	}

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	public String getStrUOM() {
		return strUOM;
	}

	public void setStrUOM(String strUOM) {
		this.strUOM = strUOM;
	}

	public String getStrSGCode() {
		return strSGCode;
	}

	public void setStrSGCode(String strSGCode) {
		this.strSGCode = strSGCode;
	}

	public String getStrProdType() {
		return strProdType;
	}

	public void setStrProdType(String strProdType) {
		this.strProdType = strProdType;
	}

	public double getDblCostRM() {
		return dblCostRM;
	}

	public void setDblCostRM(double dblCostRM) {
		this.dblCostRM = dblCostRM;
	}

	public double getDblCostManu() {
		return dblCostManu;
	}

	public void setDblCostManu(double dblCostManu) {
		this.dblCostManu = dblCostManu;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public double getDblOrduptoLvl() {
		return dblOrduptoLvl;
	}

	public void setDblOrduptoLvl(double dblOrduptoLvl) {
		this.dblOrduptoLvl = dblOrduptoLvl;
	}

	public double getDblReorderLvl() {
		return dblReorderLvl;
	}

	public void setDblReorderLvl(double dblReorderLvl) {
		this.dblReorderLvl = dblReorderLvl;
	}

	public String getStrNotInUse() {
		return strNotInUse;
	}

	public void setStrNotInUse(String strNotInUse) {
		this.strNotInUse = strNotInUse;
	}

	public String getStrExpDate() {
		return strExpDate;
	}

	public void setStrExpDate(String strExpDate) {
		this.strExpDate = strExpDate;
	}

	public String getStrLotNo() {
		return strLotNo;
	}

	public void setStrLotNo(String strLotNo) {
		this.strLotNo = strLotNo;
	}

	public String getStrRevLevel() {
		return strRevLevel;
	}

	public void setStrRevLevel(String strRevLevel) {
		this.strRevLevel = strRevLevel;
	}

	public String getStrSlNo() {
		return strSlNo;
	}

	public void setStrSlNo(String strSlNo) {
		this.strSlNo = strSlNo;
	}

	public String getStrForSale() {
		return strForSale;
	}

	public void setStrForSale(String strForSale) {
		this.strForSale = strForSale;
	}

	public String getStrSaleNo() {
		return strSaleNo;
	}

	public void setStrSaleNo(String strSaleNo) {
		this.strSaleNo = strSaleNo;
	}

	public String getStrDesc() {
		return strDesc;
	}

	public void setStrDesc(String strDesc) {
		this.strDesc = strDesc;
	}

	public double getDblUnitPrice() {
		return dblUnitPrice;
	}

	public void setDblUnitPrice(double dblUnitPrice) {
		this.dblUnitPrice = dblUnitPrice;
	}

	public String getStrTaxIndicator() {
		return strTaxIndicator;
	}

	public void setStrTaxIndicator(String strTaxIndicator) {
		this.strTaxIndicator = strTaxIndicator;
	}

	public String getStrExceedPO() {
		return strExceedPO;
	}

	public void setStrExceedPO(String strExceedPO) {
		this.strExceedPO = strExceedPO;
	}

	public String getStrStagDel() {
		return strStagDel;
	}

	public void setStrStagDel(String strStagDel) {
		this.strStagDel = strStagDel;
	}

	public int getIntDelPeriod() {
		return intDelPeriod;
	}

	public void setIntDelPeriod(int intDelPeriod) {
		this.intDelPeriod = intDelPeriod;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getStrSpecification() {
		return strSpecification;
	}

	public void setStrSpecification(String strSpecification) {
		this.strSpecification = strSpecification;
	}

	public Blob getStrProductImage() {
		return strProductImage;
	}

	public void setStrProductImage(Blob strProductImage) {
		this.strProductImage = strProductImage;
	}

	public double getDblWeight() {
		return dblWeight;
	}

	public void setDblWeight(double dblWeight) {
		this.dblWeight = dblWeight;
	}

	public String getStrBomCal() {
		return strBomCal;
	}

	public void setStrBomCal(String strBomCal) {
		this.strBomCal = strBomCal;
	}

	public String getStrWtUOM() {
		return strWtUOM;
	}

	public void setStrWtUOM(String strWtUOM) {
		this.strWtUOM = strWtUOM;
	}

	public String getStrCalAmtOn() {
		return strCalAmtOn;
	}

	public void setStrCalAmtOn(String strCalAmtOn) {
		this.strCalAmtOn = strCalAmtOn;
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

	public double getDblBatchQty() {
		return dblBatchQty;
	}

	public void setDblBatchQty(double dblBatchQty) {
		this.dblBatchQty = dblBatchQty;
	}

	public double getDblMaxLvl() {
		return dblMaxLvl;
	}

	public void setDblMaxLvl(double dblMaxLvl) {
		this.dblMaxLvl = dblMaxLvl;
	}

	public String getStrBinNo() {
		return strBinNo;
	}

	public void setStrBinNo(String strBinNo) {
		this.strBinNo = strBinNo;
	}

	public String getStrClass() {
		return strClass;
	}

	public void setStrClass(String strClass) {
		this.strClass = strClass;
	}

	public String getStrTariffNo() {
		return strTariffNo;
	}

	public void setStrTariffNo(String strTariffNo) {
		this.strTariffNo = strTariffNo;
	}

	public double getDblListPrice() {
		return dblListPrice;
	}

	public void setDblListPrice(double dblListPrice) {
		this.dblListPrice = dblListPrice;
	}

	public String getStrRemark() {
		return strRemark;
	}

	public void setStrRemark(String strRemark) {
		this.strRemark = strRemark;
	}

	public String getStrReceivedUOM() {
		return strReceivedUOM;
	}

	public void setStrReceivedUOM(String strReceivedUOM) {
		this.strReceivedUOM = strReceivedUOM;
	}

	public String getStrIssueUOM() {
		return strIssueUOM;
	}

	public void setStrIssueUOM(String strIssueUOM) {
		this.strIssueUOM = strIssueUOM;
	}

	public String getStrRecipeUOM() {
		return strRecipeUOM;
	}

	public void setStrRecipeUOM(String strRecipeUOM) {
		this.strRecipeUOM = strRecipeUOM;
	}

	public double getDblReceiveConversion() {
		return dblReceiveConversion;
	}

	public void setDblReceiveConversion(double dblReceiveConversion) {
		this.dblReceiveConversion = dblReceiveConversion;
	}

	public double getDblIssueConversion() {
		return dblIssueConversion;
	}

	public void setDblIssueConversion(double dblIssueConversion) {
		this.dblIssueConversion = dblIssueConversion;
	}

	public double getDblRecipeConversion() {
		return dblRecipeConversion;
	}

	public void setDblRecipeConversion(double dblRecipeConversion) {
		this.dblRecipeConversion = dblRecipeConversion;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
		this.intId = intId;
	}

	public String getStrNonStockableItem() {
		return strNonStockableItem;
	}

	public void setStrNonStockableItem(String strNonStockableItem) {
		this.strNonStockableItem = strNonStockableItem;
	}

	public String getStrPosItemCode() {
		return strPosItemCode;
	}

	public void setStrPosItemCode(String strPosItemCode) {
		this.strPosItemCode = strPosItemCode;
	}

	public String getStrSuppPartNo() {
		return strSuppPartNo;
	}

	public void setStrSuppPartNo(String strSuppPartNo) {
		this.strSuppPartNo = strSuppPartNo;
	}

	public String getStrSuppPartDesc() {
		return strSuppPartDesc;
	}

	public void setStrSuppPartDesc(String strSuppPartDesc) {
		this.strSuppPartDesc = strSuppPartDesc;
	}

	public String getStrLocName() {
		return strLocName;
	}

	public void setStrLocName(String strLocName) {
		this.strLocName = strLocName;
	}

	public String getStrSGName() {
		return strSGName;
	}

	public void setStrSGName(String strSGName) {
		this.strSGName = strSGName;
	}

	public String getStrPOSItemName() {
		return strPOSItemName;
	}

	public void setStrPOSItemName(String strPOSItemName) {
		this.strPOSItemName = strPOSItemName;
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

	public double getDblYieldPer() {
		return dblYieldPer;
	}

	public void setDblYieldPer(double dblYieldPer) {
		this.dblYieldPer = dblYieldPer;
	}

	public String getStrBarCode() {
		return strBarCode;
	}

	public void setStrBarCode(String strBarCode) {
		this.strBarCode = strBarCode;
	}

	public double getDblMRP() {
		return dblMRP;
	}

	public void setDblMRP(double dblMRP) {
		this.dblMRP = dblMRP;
	}

	public double getDblOverQty() {
		return dblOverQty;
	}

	public void setDblOverQty(double dblOverQty) {
		this.dblOverQty = dblOverQty;
	}

	public double getDblAcceptQty() {
		return dblAcceptQty;
	}

	public void setDblAcceptQty(double dblAcceptQty) {
		this.dblAcceptQty = dblAcceptQty;
	}

	public double getDblStock() {
		return dblStock;
	}

	public void setDblStock(double dblStock) {
		this.dblStock = dblStock;
	}

	public String getStrPickMRPForTaxCal() {
		return strPickMRPForTaxCal;
	}

	public void setStrPickMRPForTaxCal(String strPickMRPForTaxCal) {
		this.strPickMRPForTaxCal = (String) setDefaultValue(strPickMRPForTaxCal, "N");
	}

	public String getStrExciseable() {
		return strExciseable;
	}

	public void setStrExciseable(String strExciseable) {
		this.strExciseable = (String) setDefaultValue(strExciseable, "N");
	}

	public String getStrProdNameMarathi() {
		return strProdNameMarathi;
	}

	public void setStrProdNameMarathi(String strProdNameMarathi) {
		this.strProdNameMarathi = (String) setDefaultValue(strProdNameMarathi, "");
	}

	public double getPrevUnitPrice() {
		return prevUnitPrice;
	}

	public void setPrevUnitPrice(double prevUnitPrice) {
		this.prevUnitPrice = prevUnitPrice;
	}

	public String getPrevInvCode() {
		return prevInvCode;
	}

	public void setPrevInvCode(String prevInvCode) {
		this.prevInvCode = prevInvCode;
	}

	public String getStrManufacturerCode() {
		return strManufacturerCode;
	}

	public void setStrManufacturerCode(String strManufacturerCode) {
		this.strManufacturerCode = (String) setDefaultValue(strManufacturerCode, "");
		;
	}

	public String getStrComesaItem() {
		return strComesaItem;
	}

	public void setStrComesaItem(String strComesaItem) {
		this.strComesaItem = (String) setDefaultValue(strComesaItem, "");
	}

}
