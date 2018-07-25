package com.sanguine.bean;

import java.util.List;

import com.sanguine.model.clsProdAttMasterModel;
import com.sanguine.model.clsProdCharMasterModel;
import com.sanguine.model.clsProdProcessModel;
import com.sanguine.model.clsProdSuppMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsProductReOrderLevelModel;

public class clsProductMasterBean {
	private String strProdCode;
	private String strPartNo;
	private String strProdName;
	private String strUOM;
	private String strSGCode;
	private String strProdType;
	private double dblCostRM;
	private double dblCostManu;
	private String strLocCode;
	private double dblOrduptoLvl;
	private double dblReorderLvl;
	private String strNotInUse;
	private String strExpDate;
	private String strLotNo;
	private String strRevLevel;
	private String strSlNo;
	private String strForSale;
	private String strSaleNo;
	private String strDesc;
	private double dblUnitPrice;
	private String strTaxIndicator;
	private String strExceedPO;
	private String strStagDel;
	private int intDelPeriod;
	private String strType;
	private String strSpecification;
	private String strImagePath;
	private double dblWeight;
	private String strBomCal;
	private String strWtUOM;
	private String strCalAmtOn;
	private String strUserCreated;
	private String dtCreatedDate;
	private String strUserModified;
	private String dtLastModified;
	private double dblBatchQty;
	private double dblMaxLvl;
	private String strBinNo;
	private String strClass;
	private String strTariffNo;
	private double dblListPrice, dblAttValue;
	private String strRemark;
	private String strClientCode, strSuppId, strProcessCode;
	private String strProdProcessCode;
	private String strProdAttCode, strAttCode, strAVCode;
	private String strReceivedUOM, strIssueUOM, strRecipeUOM;
	private double dblReceiveConversion, dblIssueConversion, dblRecipeConversion;
	private String strProdConv;
	private String strNonStockableItem;
	private List<clsProdSuppMasterModel> listProdSupp;
	private List<clsProdAttMasterModel> listProdAtt;
	private List<clsProdProcessModel> listProdProcess;
	private List<clsProdCharMasterModel> listProdChar;
	private List<clsProductReOrderLevelModel> listReorderLvl;
	private List<clsProdSuppMasterModel> listProdCustMargin;

	private List<clsProductMasterModel> listProdModel;

	private double dblYieldPer;
	private String strBarCode;
	private double dblMRP;
	private String strPickMRPForTaxCal;
	private String strExciseable;
	private String strPCode;
	private String strProdNameMarathi;
	private String strManufacturerCode;

	private String strComesaItem;

	public List<clsProductReOrderLevelModel> getListReorderLvl() {
		return listReorderLvl;
	}

	public void setListReorderLvl(List<clsProductReOrderLevelModel> listReorderLvl) {
		this.listReorderLvl = listReorderLvl;
	}

	public List<clsProdSuppMasterModel> getListProdSupp() {
		return listProdSupp;
	}

	public void setListProdSupp(List<clsProdSuppMasterModel> listProdSupp) {
		this.listProdSupp = listProdSupp;
	}

	public List<clsProdAttMasterModel> getListProdAtt() {
		return listProdAtt;
	}

	public void setListProdAtt(List<clsProdAttMasterModel> listProdAtt) {
		this.listProdAtt = listProdAtt;
	}

	public List<clsProdProcessModel> getListProdProcess() {
		return listProdProcess;
	}

	public void setListProdProcess(List<clsProdProcessModel> listProdProcess) {
		this.listProdProcess = listProdProcess;
	}

	public List<clsProdCharMasterModel> getListProdChar() {
		return listProdChar;
	}

	public void setListProdChar(List<clsProdCharMasterModel> listProdChar) {
		this.listProdChar = listProdChar;
	}

	public String getStrProdAttCode() {
		return strProdAttCode;
	}

	public void setStrProdAttCode(String strProdAttCode) {
		this.strProdAttCode = strProdAttCode;
	}

	public double getDblAttValue() {
		return dblAttValue;
	}

	public void setDblAttValue(double dblAttValue) {
		this.dblAttValue = dblAttValue;
	}

	public String getStrAttCode() {
		return strAttCode;
	}

	public void setStrAttCode(String strAttCode) {
		this.strAttCode = strAttCode;
	}

	public String getStrAVCode() {
		return strAVCode;
	}

	public void setStrAVCode(String strAVCode) {
		this.strAVCode = strAVCode;
	}

	public String getStrProdProcessCode() {
		return strProdProcessCode;
	}

	public void setStrProdProcessCode(String strProdProcessCode) {
		this.strProdProcessCode = strProdProcessCode;
	}

	public String getStrProcessCode() {
		return strProcessCode;
	}

	public void setStrProcessCode(String strProcessCode) {
		this.strProcessCode = strProcessCode;
	}

	private String strSuppCode, strSuppUOM, dtLastDate, strLeadTime, strDefault, strSuppPartNo, strSuppPartDesc;
	private double dblLastCost, dblMaxQty, dblProcessWeight, dblCycleTime;

	public String getStrSuppId() {
		return strSuppId;
	}

	public void setStrSuppId(String strSuppId) {
		this.strSuppId = strSuppId;
	}

	public double getDblProcessWeight() {
		return dblProcessWeight;
	}

	public void setDblProcessWeight(double dblProcessWeight) {
		this.dblProcessWeight = dblProcessWeight;
	}

	public double getDblCycleTime() {
		return dblCycleTime;
	}

	public void setDblCycleTime(double dblCycleTime) {
		this.dblCycleTime = dblCycleTime;
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

	public String getStrImagePath() {
		return strImagePath;
	}

	public void setStrImagePath(String strImagePath) {
		this.strImagePath = strImagePath;
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

	private Long intId;

	public String getStrSuppCode() {
		return strSuppCode;
	}

	public void setStrSuppCode(String strSuppCode) {
		this.strSuppCode = strSuppCode;
	}

	public String getStrSuppUOM() {
		return strSuppUOM;
	}

	public void setStrSuppUOM(String strSuppUOM) {
		this.strSuppUOM = strSuppUOM;
	}

	public String getDtLastDate() {
		return dtLastDate;
	}

	public void setDtLastDate(String dtLastDate) {
		this.dtLastDate = dtLastDate;
	}

	public String getStrLeadTime() {
		return strLeadTime;
	}

	public void setStrLeadTime(String strLeadTime) {
		this.strLeadTime = strLeadTime;
	}

	public String getStrDefault() {
		return strDefault;
	}

	public void setStrDefault(String strDefault) {
		this.strDefault = strDefault;
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

	public double getDblLastCost() {
		return dblLastCost;
	}

	public void setDblLastCost(double dblLastCost) {
		this.dblLastCost = dblLastCost;
	}

	public double getDblMaxQty() {
		return dblMaxQty;
	}

	public void setDblMaxQty(double dblMaxQty) {
		this.dblMaxQty = dblMaxQty;
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

	public String getStrProdConv() {
		return strProdConv;
	}

	public void setStrProdConv(String strProdConv) {
		this.strProdConv = strProdConv;
	}

	public String getStrNonStockableItem() {
		return strNonStockableItem;
	}

	public void setStrNonStockableItem(String strNonStockableItem) {
		this.strNonStockableItem = strNonStockableItem;
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

	public String getStrPickMRPForTaxCal() {
		return strPickMRPForTaxCal;
	}

	public void setStrPickMRPForTaxCal(String strPickMRPForTaxCal) {
		this.strPickMRPForTaxCal = strPickMRPForTaxCal;
	}

	public String getStrExciseable() {
		return strExciseable;
	}

	public void setStrExciseable(String strExciseable) {
		this.strExciseable = strExciseable;
	}

	public String getStrPCode() {
		return strPCode;
	}

	public void setStrPCode(String strPCode) {
		this.strPCode = strPCode;
	}

	public List<clsProdSuppMasterModel> getListProdCustMargin() {
		return listProdCustMargin;
	}

	public void setListProdCustMargin(List<clsProdSuppMasterModel> listProdCustMargin) {
		this.listProdCustMargin = listProdCustMargin;
	}

	public List<clsProductMasterModel> getListProdModel() {
		return listProdModel;
	}

	public void setListProdModel(List<clsProductMasterModel> listProdModel) {
		this.listProdModel = listProdModel;
	}

	public String getStrProdNameMarathi() {
		return strProdNameMarathi;
	}

	public void setStrProdNameMarathi(String strProdNameMarathi) {
		this.strProdNameMarathi = strProdNameMarathi;
	}

	public String getStrManufacturerCode() {
		return strManufacturerCode;
	}

	public void setStrManufacturerCode(String strManufacturerCode) {
		this.strManufacturerCode = strManufacturerCode;
	}

	public String getStrComesaItem() {
		return strComesaItem;
	}

	public void setStrComesaItem(String strComesaItem) {
		this.strComesaItem = strComesaItem;
	}

}
