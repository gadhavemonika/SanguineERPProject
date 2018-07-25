package com.sanguine.crm.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.math.BigDecimal;
import com.sanguine.crm.bean.clsInvoiceBean;
import com.sanguine.crm.service.clsCRMSettlementMasterService;
import com.sanguine.crm.service.clsInvoiceHdService;
import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webpms.bean.clsCheckInBean;


@Controller
public class clsProductWiseFlashController {

	@Autowired
	private clsInvoiceHdService objInvoiceHdService;

	@Autowired
	private clsGlobalFunctionsService objGlobalService;
	@Autowired
	private clsLocationMasterService objLocationMasterService;

	@Autowired
	private clsCRMSettlementMasterService objSettlementService;
	
	@Autowired
	private clsCurrencyMasterService objCurrencyMasterService;

	@Autowired
	private clsSetupMasterService objSetupMasterService;
	
	String baseCurrencyCode="";
	
	Map<String,String> currencyList=new TreeMap<String, String>();
	
	@RequestMapping(value = "/frmProductWiseFlash", method = RequestMethod.GET)
	public ModelAndView funInvoice(@ModelAttribute("command") clsInvoiceBean objBean, BindingResult result, HttpServletRequest req, Map<String, Object> model) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		return funGetModelAndView(req,"","","","","","","Without DrillDown","");
	}

	
	private ModelAndView funGetModelAndView(HttpServletRequest req,String code,String fromDate,String toDate,String locCode,String custCode,String settleCode,String reportName,String currencyCode) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
      
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		ModelAndView objModelView = null;
		if ("2".equalsIgnoreCase(urlHits)) {
			objModelView = new ModelAndView("frmProductWiseFlash_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			objModelView = new ModelAndView("frmProductWiseFlash");
		}
		Map<String, String> mapProperty = objGlobalService.funGetPropertyList(clientCode);
		if (mapProperty.isEmpty()) {
			mapProperty.put("", "");
		}
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String locationCode = req.getSession().getAttribute("locationCode").toString();
		Map<String, String> settlementList = objSettlementService.funGetSettlementComboBox(clientCode);
		settlementList.put("All", "All");
		objModelView.addObject("settlementList", settlementList);
		
		currencyList.put("All", "All");
		List<clsCurrencyMasterModel> listCurrency = objCurrencyMasterService.funGetAllCurrencyDataModel(clientCode);
		if (listCurrency != null) 
		{
			for(int cnt=0;cnt<listCurrency.size();cnt++)
			{
				clsCurrencyMasterModel objModel=listCurrency.get(cnt);
				currencyList.put(objModel.getStrCurrencyCode(),objModel.getStrCurrencyName());
			}
		
		}
		objModelView.addObject("currencyList", currencyList);
		
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		baseCurrencyCode= objSetup.getStrCurrencyCode();

		objModelView.addObject("listProperty", mapProperty);
		objModelView.addObject("LoggedInProp", propertyCode);
		objModelView.addObject("LoggedInLoc", locationCode);
		objModelView.addObject("code", code);
		objModelView.addObject("fromDate", fromDate);
		objModelView.addObject("toDate", toDate);
		objModelView.addObject("locCode", locCode);
		objModelView.addObject("custCode", custCode);
		objModelView.addObject("settleCode", settleCode);
		objModelView.addObject("reportName", reportName);
		objModelView.addObject("currencyCode", currencyCode);
		return objModelView;
	}
	
	
	@RequestMapping(value = "/frmProductWiseFlashForInvoice", method = RequestMethod.GET)
	public ModelAndView funOpenProductWiseFlashForInvoiceForm(@ModelAttribute("command") clsInvoiceBean objBean, BindingResult result, HttpServletRequest req, Map<String, Object> model) {
		String urlHits = "1";
		String reportName=req.getParameter("reportName").toString();
		String code=req.getParameter("code").toString();
		String fromDate=req.getParameter("fromDate").toString();
		String toDate=req.getParameter("toDate").toString();
		String locCode=req.getParameter("locCode").toString();
		String custCode=req.getParameter("custCode").toString();
		String settleCode=req.getParameter("settleCode").toString();
		String currencyCode=req.getParameter("currencyCode").toString();

		 
		
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		return funGetModelAndView(req,code,fromDate,toDate,locCode,custCode,settleCode,reportName,currencyCode);
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadCustProductWiseDtl", method = RequestMethod.GET)
	public @ResponseBody List funProductWiseInvoiceFlash(@RequestParam("settlementcode") String settlementcode, HttpServletRequest request) {

		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		String custCode = request.getParameter("custCode").toString();
		String prodCode = request.getParameter("prodCode").toString();
		String regionCode = request.getParameter("regionCode").toString();
		String currencyCode=request.getParameter("currencyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String currencyName="";
		List listInvoice = new ArrayList();
		List listofInvoiveTotal = new ArrayList();
		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblTotalQty = new BigDecimal(0);

		double currValue = 1.0;
		if(currencyCode.equalsIgnoreCase("All"))
		{
			currencyName=currencyList.get(baseCurrencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(baseCurrencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		else
		{
			currencyName=currencyList.get(currencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		
		List listofInvFlash = new ArrayList();

		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String sqlInvoiceFlash = "SELECT  b.strProdCode,c.strProdName,d.strPName,sum(b.dblQty)/" + currValue + ", SUM(b.dblQty*b.dblPrice)/" + currValue + ",ifnull(e.strRegionDesc,''),a.strInvCode "
				+ " FROM tblinvoicedtl b, tblproductmaster c,tblinvoicehd a,tblpartymaster d  left outer join tblregionmaster e on d.strRegion=e.strRegionCode "
				+ " WHERE a.strInvCode=b.strInvCode  and b.strProdCode=c.strProdCode and a.strCustCode=d.strPCode and a.strLocCode='" + locCode + "' " + " and date(a.dteInvDate) between '" + fromDate + "' and '" + toDate + "' and  a.strClientCode='" + strClientCode + "'";
		if (!settlementcode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strSettlementCode='" + settlementcode + "' ";
		}
		if (!custCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCustCode='" + custCode + "' ";
		}
		if (!prodCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and b.strProdCode='" + prodCode + "' ";
		}
		if (!regionCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and e.strRegionCode='" + regionCode + "' ";
		}

		if(!currencyCode.equalsIgnoreCase("All"))
		{
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCurrencyCode='" + currencyCode + "' ";
		}
		sqlInvoiceFlash += "group by b.strProdCode,a.strCustCode,e.strRegionDesc ";

		DecimalFormat df = new DecimalFormat("#.##");

		List listOfInvoice = objGlobalService.funGetList(sqlInvoiceFlash, "sql");
		if (!listOfInvoice.isEmpty()) {
			for (int i = 0; i < listOfInvoice.size(); i++) {
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objInvoice[6].toString());
				DataList.add(objInvoice[0].toString());
				DataList.add(objInvoice[1].toString());
				DataList.add(objInvoice[2].toString());
				DataList.add(objInvoice[5].toString());
				DataList.add(objInvoice[3].toString());
				DataList.add(objInvoice[4].toString());
				DataList.add(currencyName);
				
				listofInvFlash.add(DataList);
				BigDecimal value = new BigDecimal(Double.parseDouble(objInvoice[4].toString()));
				dblTotalValue = dblTotalValue.add(value);
				BigDecimal qtyValue = new BigDecimal(Double.parseDouble(objInvoice[3].toString()));
				dblTotalQty = dblTotalQty.add(qtyValue);

			}

		}

		listofInvoiveTotal.add(listofInvFlash);
		listofInvoiveTotal.add(dblTotalQty);
		listofInvoiveTotal.add(dblTotalValue);

		return listofInvoiveTotal;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/exportCustProductWiseFlash", method = RequestMethod.GET)
	private ModelAndView funExportProductWiseInvoiceFlash(@RequestParam("settlementcode") String settlementcode, HttpServletRequest request) {

		List retList = new ArrayList();
		List detailList = new ArrayList();
		List headerList = new ArrayList();
		// Totals at Bottom
		List totalsList = new ArrayList();
		totalsList.add("Total");
		totalsList.add("");
		totalsList.add("");
		totalsList.add("");
		totalsList.add("");
		totalsList.add("");

		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		String custCode = request.getParameter("custCode").toString();
		String prodCode = request.getParameter("prodCode").toString();
		String regionCode = request.getParameter("regionCode").toString();
		String currencyCode=request.getParameter("currencyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String currencyName="";
		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblTotalQty = new BigDecimal(0);
		
		double currValue = 1.0;
		if(currencyCode.equalsIgnoreCase("All"))
		{
			currencyName=currencyList.get(baseCurrencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(baseCurrencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		else
		{
			currencyName=currencyList.get(currencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		
		String sqlInvoiceFlash = "SELECT  b.strProdCode,c.strProdName,d.strPName,sum(b.dblQty)/" + currValue + ", SUM(b.dblQty*b.dblPrice)/" + currValue + ",ifnull(e.strRegionDesc,''),a.strInvCode "
				+ " FROM tblinvoicedtl b, tblproductmaster c,tblinvoicehd a,tblpartymaster d  left outer join tblregionmaster e on d.strRegion=e.strRegionCode "
				+ " WHERE a.strInvCode=b.strInvCode  and b.strProdCode=c.strProdCode and a.strCustCode=d.strPCode and a.strLocCode='" + locCode + "' " + " and date(a.dteInvDate) between '" + fromDate + "' and '" + toDate + "' and  a.strClientCode='" + clientCode + "'";
		if (!settlementcode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strSettlementCode='" + settlementcode + "' ";
		}
		if (!custCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCustCode='" + custCode + "' ";
		}
		if (!prodCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and b.strProdCode='" + prodCode + "' ";
		}
		if (!regionCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and e.strRegionCode='" + regionCode + "' ";
		}

		if(!currencyCode.equalsIgnoreCase("All"))
		{
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCurrencyCode='" + currencyCode + "' ";
		}
		sqlInvoiceFlash += "group by b.strProdCode,a.strCustCode,e.strRegionDesc ";

		DecimalFormat df = new DecimalFormat("#.##");
		double floatingPoint = 0.0;
		List listOfInvoice = objGlobalService.funGetList(sqlInvoiceFlash, "sql");
		if (!listOfInvoice.isEmpty()) {
		for (int i = 0; i < listOfInvoice.size(); i++) 
		   {
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objInvoice[6].toString());
				DataList.add(objInvoice[0].toString());
				DataList.add(objInvoice[1].toString());
				DataList.add(objInvoice[2].toString());
				DataList.add(objInvoice[5].toString());
				DataList.add(currencyName);
				DataList.add(Double.valueOf(objInvoice[3].toString()));
				DataList.add(Double.valueOf(objInvoice[4].toString()));
				
				detailList.add(DataList);
				BigDecimal value = new BigDecimal(Double.parseDouble(objInvoice[4].toString()));
				dblTotalValue = dblTotalValue.add(value);			
				BigDecimal valueQty = new BigDecimal(Double.parseDouble(objInvoice[3].toString()));
				dblTotalQty = dblTotalQty.add(valueQty);
			}
		}

		totalsList.add(dblTotalQty);
		totalsList.add(dblTotalValue);
	
		headerList.add("Invoice No");
		headerList.add("Product Code");
		headerList.add("Product Name");
		headerList.add("Customer Name");
		headerList.add("Region Name");
		headerList.add("Currency");
		headerList.add("Quantity");
		headerList.add("Sales Amount");

		Object[] objHeader = (Object[]) headerList.toArray();

		String[] ExcelHeader = new String[objHeader.length];
		for (int k = 0; k < objHeader.length; k++) {
			ExcelHeader[k] = objHeader[k].toString();
		}
		
		//detailList.add(listofInvFlash);
		List blankList = new ArrayList();
		detailList.add(blankList);// Blank Row at Bottom
		detailList.add(totalsList);
		
		retList.add("CustProductWiseInvoiceData_" + fromDate + "to" + toDate + "_" + userCode);
		retList.add(ExcelHeader);
		retList.add(detailList);
		return new ModelAndView("excelViewWithReportName", "listWithReportName", retList);
	}
	

}
