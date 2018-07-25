package com.sanguine.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sanguine.bean.clsFundFlowBean;
import com.sanguine.model.clsGRNHdModel;
import com.sanguine.model.clsStockFlashModel;

/**
 * @author jai chandra
 *
 */
/**
 * @author jai chandra
 *
 */
/**
 * @author jai chandra
 *
 */
public class clsReportBean {
	public String getStrLicenceCode() {
		return strLicenceCode;
	}

	public void setStrLicenceCode(String strLicenceCode) {
		this.strLicenceCode = strLicenceCode;
	}

	private String strDocCode;

	private String strToDocCode;

	private String strFromDocCode;

	private String strDocType;

	private String strFromLoc;

	private String strToLoc;

	private String strFromLocCode;

	private String strToLocCode;

	private String dtFromDate;

	private String dtToDate;

	private String dtExpFromDate;

	private String dtExpToDate;

	private String strAgainst;

	private String strProdCode;

	private String strProdType;

	Map<String, String> group;

	Map<String, String> subGroup;

	private String strGCode;

	private String strSGCode;

	private String strPropertyCode;

	private String strLocationCode;

	private double dblPercentage;

	private String strIncludeItems;

	private String strSuppCode;

	private String strTaxCode;

	private String strExportType;

	private String strSOCode;

	private String strShowBOM;

	private String strReportType;

	private String dteFromFulfillment;

	private String dteToFulfillment;

	private String strSCCode;

	private String strLicenceCode;

	private String dteFromDate;

	private String dteToDate;

	private String strWeekDay;

	private String strCustCode;

	private String strUserCode;

	private String strProdName;

	private double dblQty;

	private double dblAmt;

	private String strLocName;

	private String strSGName;

	private String strUOM;

	private String strReportView;

	private String strSRCode;

	private String dteSRDate;

	private double dblPurPrice;

	private double dblPurAmt;

	private double dblSalePrice;

	private double dblSaleAmt;

	private String strDSCode;
	
	private String strCurrency;

	private double dblCStock;
	
	private double dblPStock;
		
	private double dblVariance;
		
	private double dblCostRM;
		
	private double dblvalue;
	
	private List<clsFundFlowBean> listFundFlowBean = new ArrayList<clsFundFlowBean>();

	public String getStrWeekDay() {
		return strWeekDay;
	}

	public void setStrWeekDay(String strWeekDay) {
		this.strWeekDay = strWeekDay;
	}

	private List<clsGRNHdModel> listGRNHdModel;

	public String getStrProdType() {
		return strProdType;
	}

	public void setStrProdType(String strProdType) {
		this.strProdType = strProdType;
	}

	public String getStrDocCode() {
		return strDocCode;
	}

	public void setStrDocCode(String strDocCode) {
		this.strDocCode = strDocCode;
	}

	public String getStrDocType() {
		return strDocType;
	}

	public void setStrDocType(String strDocType) {
		this.strDocType = strDocType;
	}

	public String getStrFromLoc() {
		return strFromLoc;
	}

	public void setStrFromLoc(String strFromLoc) {
		this.strFromLoc = strFromLoc;
	}

	public String getStrToLoc() {
		return strToLoc;
	}

	public void setStrToLoc(String strToLoc) {
		this.strToLoc = strToLoc;
	}

	public String getDtFromDate() {
		return dtFromDate;
	}

	public void setDtFromDate(String dtFromDate) {
		this.dtFromDate = dtFromDate;
	}

	public String getDtToDate() {
		return dtToDate;
	}

	public void setDtToDate(String dtToDate) {
		this.dtToDate = dtToDate;
	}

	public String getStrFromLocCode() {
		return strFromLocCode;
	}

	public void setStrFromLocCode(String strFromLocCode) {
		this.strFromLocCode = strFromLocCode;
	}

	public String getStrToLocCode() {
		return strToLocCode;
	}

	public void setStrToLocCode(String strToLocCode) {
		this.strToLocCode = strToLocCode;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public Map<String, String> getGroup() {
		return group;
	}

	public void setGroup(Map<String, String> group) {
		this.group = group;
	}

	public Map<String, String> getSubGroup() {
		return subGroup;
	}

	public void setSubGroup(Map<String, String> subGroup) {
		this.subGroup = subGroup;
	}

	public String getStrGCode() {
		return strGCode;
	}

	public void setStrGCode(String strGCode) {
		this.strGCode = strGCode;
	}

	public String getStrSGCode() {
		return strSGCode;
	}

	public void setStrSGCode(String strSGCode) {
		this.strSGCode = strSGCode;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrLocationCode() {
		return strLocationCode;
	}

	public void setStrLocationCode(String strLocationCode) {
		this.strLocationCode = strLocationCode;
	}

	public String getStrIncludeItems() {
		return strIncludeItems;
	}

	public void setStrIncludeItems(String strIncludeItems) {
		this.strIncludeItems = strIncludeItems;
	}

	public String getStrSuppCode() {
		return strSuppCode;
	}

	public void setStrSuppCode(String strSuppCode) {
		this.strSuppCode = strSuppCode;
	}

	public String getStrTaxCode() {
		return strTaxCode;
	}

	public void setStrTaxCode(String strTaxCode) {
		this.strTaxCode = strTaxCode;
	}

	public Double getDblPercentage() {
		return dblPercentage;
	}

	public void setDblPercentage(double dblPercentage) {
		this.dblPercentage = dblPercentage;
	}

	public String getDtExpFromDate() {
		return dtExpFromDate;
	}

	public void setDtExpFromDate(String dtExpFromDate) {
		this.dtExpFromDate = dtExpFromDate;
	}

	public String getDtExpToDate() {
		return dtExpToDate;
	}

	public void setDtExpToDate(String dtExpToDate) {
		this.dtExpToDate = dtExpToDate;
	}

	public String getStrExportType() {
		return strExportType;
	}

	public void setStrExportType(String strExportType) {
		this.strExportType = strExportType;
	}

	public String getStrToDocCode() {
		return strToDocCode;
	}

	public void setStrToDocCode(String strToDocCode) {
		this.strToDocCode = strToDocCode;
	}

	public String getStrFromDocCode() {
		return strFromDocCode;
	}

	public void setStrFromDocCode(String strFromDocCode) {
		this.strFromDocCode = strFromDocCode;
	}

	public String getStrSOCode() {
		return strSOCode;
	}

	public void setStrSOCode(String strSOCode) {
		this.strSOCode = strSOCode;
	}

	public String getStrShowBOM() {
		return strShowBOM;
	}

	public void setStrShowBOM(String strShowBOM) {
		this.strShowBOM = strShowBOM;
	}

	public String getStrReportType() {
		return strReportType;
	}

	public void setStrReportType(String strReportType) {
		this.strReportType = strReportType;
	}

	public String getDteFromFulfillment() {
		return dteFromFulfillment;
	}

	public void setDteFromFulfillment(String dteFromFulfillment) {
		this.dteFromFulfillment = dteFromFulfillment;
	}

	public String getDteToFulfillment() {
		return dteToFulfillment;
	}

	public void setDteToFulfillment(String dteToFulfillment) {
		this.dteToFulfillment = dteToFulfillment;
	}

	public String getStrSCCode() {
		return strSCCode;
	}

	public void setStrSCCode(String strSCCode) {
		this.strSCCode = strSCCode;
	}

	public List<clsGRNHdModel> getListGRNHdModel() {
		return listGRNHdModel;
	}

	public void setListGRNHdModel(List<clsGRNHdModel> listGRNHdModel) {
		this.listGRNHdModel = listGRNHdModel;
	}

	public String getDteFromDate() {
		return dteFromDate;
	}

	public void setDteFromDate(String dteFromDate) {
		this.dteFromDate = dteFromDate;
	}

	public String getDteToDate() {
		return dteToDate;
	}

	public void setDteToDate(String dteToDate) {
		this.dteToDate = dteToDate;
	}

	public String getStrCustCode() {
		return strCustCode;
	}

	public void setStrCustCode(String strCustCode) {
		this.strCustCode = strCustCode;
	}

	public String getStrUserCode() {
		return strUserCode;
	}

	public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
	}

	public List<clsFundFlowBean> getListFundFlowBean() {
		return listFundFlowBean;
	}

	public void setListFundFlowBean(List<clsFundFlowBean> listFundFlowBean) {
		this.listFundFlowBean = listFundFlowBean;
	}

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = dblQty;
	}

	public double getDblAmt() {
		return dblAmt;
	}

	public void setDblAmt(double dblAmt) {
		this.dblAmt = dblAmt;
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

	public String getStrUOM() {
		return strUOM;
	}

	public void setStrUOM(String strUOM) {
		this.strUOM = strUOM;
	}

	public String getStrReportView() {
		return strReportView;
	}

	public void setStrReportView(String strReportView) {
		this.strReportView = strReportView;
	}

	public String getDteSRDate() {
		return dteSRDate;
	}

	public void setDteSRDate(String dteSRDate) {
		this.dteSRDate = dteSRDate;
	}

	public String getStrSRCode() {
		return strSRCode;
	}

	public void setStrSRCode(String strSRCode) {
		this.strSRCode = strSRCode;
	}

	public double getDblPurPrice() {
		return dblPurPrice;
	}

	public void setDblPurPrice(double dblPurPrice) {
		this.dblPurPrice = dblPurPrice;
	}

	public double getDblPurAmt() {
		return dblPurAmt;
	}

	public void setDblPurAmt(double dblPurAmt) {
		this.dblPurAmt = dblPurAmt;
	}

	public double getDblSalePrice() {
		return dblSalePrice;
	}

	public void setDblSalePrice(double dblSalePrice) {
		this.dblSalePrice = dblSalePrice;
	}

	public double getDblSaleAmt() {
		return dblSaleAmt;
	}

	public void setDblSaleAmt(double dblSaleAmt) {
		this.dblSaleAmt = dblSaleAmt;
	}

	public String getStrDSCode() {
		return strDSCode;
	}

	public void setStrDSCode(String strDSCode) {
		this.strDSCode = strDSCode;
	}

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
	}

	public double getDblCStock() {
		return dblCStock;
	}

	public void setDblCStock(double dblCStock) {
		this.dblCStock = dblCStock;
	}

	public double getDblPStock() {
		return dblPStock;
	}

	public void setDblPStock(double dblPStock) {
		this.dblPStock = dblPStock;
	}

	public double getDblVariance() {
		return dblVariance;
	}

	public void setDblVariance(double dblVariance) {
		this.dblVariance = dblVariance;
	}

	public double getDblCostRM() {
		return dblCostRM;
	}

	public void setDblCostRM(double dblCostRM) {
		this.dblCostRM = dblCostRM;
	}

	public double getDblvalue() {
		return dblvalue;
	}

	public void setDblvalue(double dblvalue) {
		this.dblvalue = dblvalue;
	}

	
}
