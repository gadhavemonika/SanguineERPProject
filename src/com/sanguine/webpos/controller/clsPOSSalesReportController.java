package com.sanguine.webpos.controller;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsPOSAuditorReportBean;
import com.sanguine.webpos.bean.clsPOSItemModifierMasterBean;
import com.sanguine.webpos.bean.clsPOSSalesFlashReportsBean;

import com.sanguine.webpos.bean.clsWebPOSReportBean;

@Controller
public class clsPOSSalesReportController {

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobalFunctionsController;
	@Autowired
	private ServletContext servletContext;
	private clsGlobalFunctions objGlobal = null;

	Map<String, String> hmPOSData;
	Map<String, String> hmPayMode;
	Map<String, String> hmOperator;
	List<clsPOSSalesFlashReportsBean> listSalesReport;
	List listStockFlashModel;
	DecimalFormat decimalFormat;
	double totalDiscAmt = 0, totalSubTotal = 0, totalTaxAmt = 0, totalSettleAmt = 0, totalTipAmt = 0;

	@RequestMapping(value = "/frmPOSSalesReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		decimalFormat = new DecimalFormat("#.##");
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		List poslist = new ArrayList();
		poslist.add("All");

		hmPOSData = new HashMap<String, String>();
		JSONArray jArryPosList = objPOSGlobalFunctionsController.funGetAllPOSForMaster(strClientCode);
		for (int i = 0; i < jArryPosList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryPosList.get(i);
			poslist.add(josnObjRet.get("strPosName"));
			hmPOSData.put(josnObjRet.get("strPosName").toString(), josnObjRet.get("strPosCode").toString());
		}
		model.put("posList", poslist);

		List listHH = new ArrayList();
		List listMM = new ArrayList();
		List listOperator = new ArrayList();
		List listPayMode = new ArrayList();

		listHH.add("HH");
		for (int i = 1; i <= 12; i++) {
			listHH.add(i);
		}
		model.put("HH", listHH);

		listMM.add("MM");
		for (int i = 1; i <= 60; i++) {
			listMM.add(i);
		}
		model.put("MM", listMM);

		hmPayMode = new HashMap<String, String>();
		JSONArray jArrySettleList = objPOSGlobalFunctionsController.funGetSettlementDetails(strClientCode);
		listPayMode.add("All");
		for (int i = 0; i < jArrySettleList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArrySettleList.get(i);
			listPayMode.add(josnObjRet.get("SettlementDesc"));// SettlementCode
																// SettlementDesc
			hmPayMode.put(josnObjRet.get("SettlementDesc").toString(), josnObjRet.get("SettlementCode").toString());
		}
		model.put("PayMode", listPayMode);

		hmOperator = new HashMap<String, String>();
		listOperator.add("All");
		JSONArray jArryUserList = objPOSGlobalFunctionsController.funGetAllUsersDetails(strClientCode);
		for (int i = 0; i < jArryUserList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryUserList.get(i);
			listOperator.add(josnObjRet.get("strUserName"));
			hmOperator.put(josnObjRet.get("strUserName").toString(), josnObjRet.get("strUserCode").toString());
		}
		model.put("Operator", listOperator);

		// for pos date
		String strPosCode = request.getSession().getAttribute("loginPOS").toString();
		String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOSDate" + "?POSCode=" + strPosCode;
		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(posURL);
		String posDate = jObj.get("POSDate").toString();
		request.setAttribute("POSDate", posDate);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSSalesReport_1", "command", new clsWebPOSReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSSalesReport", "command", new clsWebPOSReportBean());
		} else {
			return null;
		}
	}

	// @RequestMapping(value = "/getPOSDate", method = RequestMethod.GET)
	// public @ResponseBody JSONObject funGetPOSDate(HttpServletRequest req)
	// {
	//
	// String strPosCode=req.getSession().getAttribute("loginPOS").toString();
	// String posURL
	// =clsPOSGlobalFunctionsController.POSWSURL+"/APOSIntegration/funGetPOSDate"
	// + "?POSCode="+strPosCode;
	// JSONObject jObj=objGlobalFunctions.funGETMethodUrlJosnObjectData(posURL);
	//
	// return (jObj);
	// }

	// rptPOSSalesReport

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptPOSSalesReport", method = RequestMethod.POST)
	private ModelAndView funReport(@ModelAttribute("command") clsWebPOSReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String LoginPOSCode = req.getSession().getAttribute("loginPOS").toString();

		String FromDate = "", FromTime = "", ToDate = "", ToTime = "";

		String FromDateTime = objBean.getFromDate() + ":" + objBean.getStrHHFrom() + "/" + objBean.getStrMMFrom() + "/" + objBean.getStrAMPMFrom();
		String ToDateTime = objBean.getToDate() + ":" + objBean.getStrHHTo() + "/" + objBean.getStrMMTo() + "/" + objBean.getStrAMPMTo();

		/*
		 * String FromDateTime=objBean.getFromDate();
		 * 
		 * String ToDateTime=objBean.getToDate();
		 */
		String operationType = objBean.getStrOperationType();
		String strPOSName = objBean.getStrPOSName();

		String strOperator = objBean.getStrOperator();
		String strPayMode = objBean.getStrPayMode();
		String strFromBill = objBean.getStrFromBillNo();
		String strToBill = objBean.getStrToBillNo();
		String reportType = objBean.getStrReportType();
		String Type = objBean.getStrType();
		String Customer = objBean.getStrCustomer();
		String ConsolidatePOS = objBean.getStrConsolidatePOS();
		String ReportName = objBean.getStrReportName();

		FromDate = FromDateTime.split(":")[0];
		FromTime = FromDateTime.split(":")[1];
		ToDate = ToDateTime.split(":")[0];
		ToTime = ToDateTime.split(":")[1];

		String strFromdate = FromDate.split("-")[2] + "-" + FromDate.split("-")[1] + "-" + FromDate.split("-")[0];
		String strToDate = ToDate.split("-")[2] + "-" + ToDate.split("-")[1] + "-" + ToDate.split("-")[0];

		Map resMap = new LinkedHashMap();

		resMap = FunGetData(strPOSName, FromDateTime, ToDateTime, strOperator, strPayMode, strFromBill, strToBill, reportType, Type, Customer, ConsolidatePOS, ReportName, userCode, LoginPOSCode);

		List ExportList = new ArrayList();

		String dteFromDate = objBean.getFromDate();
		String dteToDate = objBean.getToDate();

		String FileName = "SalesFlashReport_" + ReportName.substring(3) + "_" + dteFromDate + "_To_" + dteToDate;

		ExportList.add(FileName);
		int rowCount = (int) resMap.get("RowCount");
		int colCount = (int) resMap.get("ColCount");
		List List = (List) resMap.get("Header");

		List rowlist = new ArrayList();
		String[] headerList = new String[List.size()];

		for (int i = 0; i < List.size(); i++) {
			headerList[i] = (String) List.get(i);
		}

		ExportList.add(headerList);
		for (int i = 0; i < resMap.size(); i++) {
			if (i < rowCount) {
				List ob = (List) resMap.get("" + i);
				rowlist.add(ob);
			}

		}
		for (int i = 0; i < 2; i++) {
			List DataList = new ArrayList();
			for (int j = 0; j < colCount; j++) {
				DataList.add(" ");
			}
			rowlist.add(DataList);
		}

		// Total Header
		List TotalHeaderList = new ArrayList();
		// String rName=ReportName.substring(2);
		switch (ReportName.substring(3)) {
		case "SettlementWise":
			TotalHeaderList.add("Total");
			TotalHeaderList.add("Sales Amount");
			break;

		case "BillWise":
			TotalHeaderList.add("Total");
			TotalHeaderList.add("Sub Total");
			TotalHeaderList.add("Disc ");
			TotalHeaderList.add("Tax Total");
			TotalHeaderList.add("Sales Amount");
			TotalHeaderList.add("Tip Amount");
			break;

		case "ItemWise":
			TotalHeaderList.add("Total");
			TotalHeaderList.add("Quantity");
			TotalHeaderList.add("Sales Amount");
			TotalHeaderList.add("Sub Total");
			TotalHeaderList.add("Discount ");

			break;
		case "MenuHeadWise":
			TotalHeaderList.add("Total");
			TotalHeaderList.add("Quantity");
			TotalHeaderList.add("Sales Amount");
			TotalHeaderList.add("Sub Total");
			TotalHeaderList.add("Discount ");
			break;
		case "GroupWise":
			TotalHeaderList.add("Total");
			TotalHeaderList.add("Quantity");
			TotalHeaderList.add("Sub Total");
			TotalHeaderList.add("Net Total");
			TotalHeaderList.add("Discount ");
			break;
		case "SubGroupWise":
			TotalHeaderList.add("Total");
			TotalHeaderList.add("Quantity");
			TotalHeaderList.add("Sub Total");
			TotalHeaderList.add("Net Total");
			TotalHeaderList.add("Discount ");
			break;
		case "CustWise":
			TotalHeaderList.add("Total");
			TotalHeaderList.add("Quantity");
			TotalHeaderList.add("Sales Amount");
			break;

		case "WaiterWise":
			TotalHeaderList.add("Total");
			TotalHeaderList.add("Sales Amount");
			break;
		case "DeliveryBoyWise":
			TotalHeaderList.add("Total");
			TotalHeaderList.add("Sales Amount");
			break;
		case "CostCenterWise":
			TotalHeaderList.add("Total");
			TotalHeaderList.add("Quantity");
			TotalHeaderList.add("Sub Total");
			TotalHeaderList.add("Sales Amount");
			TotalHeaderList.add("Discount ");
			break;
		case "HomeDeliveryWise":
			TotalHeaderList.add("Total");
			TotalHeaderList.add("Discount ");
			TotalHeaderList.add("Tax ");
			TotalHeaderList.add("Sales Amount");
			break;
		case "TableWise":
			TotalHeaderList.add("Total");
			TotalHeaderList.add("Sales Amount");
			break;
		case "HourlyWise":
			TotalHeaderList.add("Total");
			TotalHeaderList.add("Sales Amount");
			break;
		case "AreaWise":
			TotalHeaderList.add("Total");
			TotalHeaderList.add("Sales Amount");
			break;
		case "DayWiseSales":
			TotalHeaderList.add("Total");
			TotalHeaderList.add("Total Bill");
			TotalHeaderList.add("Sub Total");
			TotalHeaderList.add("Total Discount");
			TotalHeaderList.add("Tax Total ");
			TotalHeaderList.add("Total Amount");
			break;
		case "TaxWiseSales":
			TotalHeaderList.add("Total");
			TotalHeaderList.add("Total Taxable");
			TotalHeaderList.add("Total Tax");
			break;
		case "TipReport":
			TotalHeaderList.add("Total");
			TotalHeaderList.add("Discount");
			TotalHeaderList.add("Sub Total");
			TotalHeaderList.add("Tax Total ");
			TotalHeaderList.add("Tip Amount");
			TotalHeaderList.add("Sales Amount");
			break;
		case "ItemModifierWise":
			TotalHeaderList.add("Total");
			TotalHeaderList.add("Quantity");
			TotalHeaderList.add("Sales Amount");
			break;
		case "MenuHeadWiseWithModifier":
			TotalHeaderList.add("Total");
			TotalHeaderList.add("Quantity");
			TotalHeaderList.add("Sales Amount");
			break;
		case "ItemHourlyWise":
			TotalHeaderList.add("Total");
			TotalHeaderList.add("Total Amount");
			TotalHeaderList.add("Total Discount");
			break;
		case "OperatorWise":
			TotalHeaderList.add("Total");
			TotalHeaderList.add("Discount Amount");
			TotalHeaderList.add("Sales Amount");
			break;
		case "MonthlySalesFlash":
			TotalHeaderList.add("Total");
			TotalHeaderList.add("Total Sale");
			break;
		}

		rowlist.add(TotalHeaderList);

		List totalList = (List) resMap.get("Total");
		rowlist.add(totalList);

		ExportList.add(rowlist);
		return new ModelAndView("excelViewWithReportName", "listWithReportName", ExportList);
	}

	private LinkedHashMap FunGetData(String strPOSName, String FromDateTime, String ToDateTime, String strOperator, String strPayMode, String strFromBill, String strToBill, String reportType, String Type, String Customer, String ConsolidatePOS, String ReportName, String userCode, String LoginPOSCode) {

		LinkedHashMap resMap = new LinkedHashMap();

		List colHeader = new ArrayList();

		colHeader.add(" ");

		String FromDate = "", FromTime = "", ToDate = "", ToTime = "";

		FromDate = FromDateTime.split(":")[0];
		FromTime = FromDateTime.split(":")[1];
		ToDate = ToDateTime.split(":")[0];
		ToTime = ToDateTime.split(":")[1];

		String strFromdate = FromDate.split("-")[2] + "-" + FromDate.split("-")[1] + "-" + FromDate.split("-")[0];
		String strToDate = ToDate.split("-")[2] + "-" + ToDate.split("-")[1] + "-" + ToDate.split("-")[0];
		String dateFrom = "", field = null, dateTo = "";
		try {
			if (funGetFromTime(FromTime) != null) {
				dateFrom = strFromdate + " " + funGetFromTime(FromTime);
				field = "dteBillDate";
			} else {
				dateFrom = strFromdate;
				field = "date(dteBillDate)";
			}
			if (funGetToTime(ToTime) != null) {
				dateTo = strToDate + " " + funGetToTime(ToTime);
			} else {
				dateTo = strToDate;
			}
			ConsolidatePOS = objGlobalFunctions.funIfNull(ConsolidatePOS, "N", "Y");
			if (ConsolidatePOS.equalsIgnoreCase("true")) {
				ConsolidatePOS = "Y";
			} else {
				ConsolidatePOS = "N";
			}
			//
			String posCode = "All";
			String PayMode = "All";

			if (!strPOSName.equalsIgnoreCase("ALL")) {
				posCode = hmPOSData.get(strPOSName);
			}

			if (!strPayMode.equalsIgnoreCase("All")) {
				PayMode = hmPayMode.get(strPayMode);
			}

			JSONObject jObjFillter = new JSONObject();
			jObjFillter.put("strFromdate", dateFrom);
			jObjFillter.put("strToDate", dateTo);
			jObjFillter.put("posCode", posCode);
			jObjFillter.put("LoginPOSCode", LoginPOSCode);
			jObjFillter.put("strShiftNo", "1");
			jObjFillter.put("userCode", userCode);
			jObjFillter.put("field", field);
			jObjFillter.put("strPayMode", PayMode);
			jObjFillter.put("strOperator", strOperator);
			jObjFillter.put("strFromBill", strFromBill);
			jObjFillter.put("strToBill", strToBill);
			jObjFillter.put("reportType", reportType);
			jObjFillter.put("Type", Type);
			jObjFillter.put("Customer", Customer);
			jObjFillter.put("ConsolidatePOS", ConsolidatePOS);
			jObjFillter.put("ReportName", ReportName.substring(3));

			// JSONObject jObjFillter = new JSONObject();
			/*
			 * jObjFillter.put("fromDate", fromDate1); jObjFillter.put("toDate",
			 * toDate1); jObjFillter.put("operationType", operationType);
			 * jObjFillter.put("posCode", posCode);
			 * jObjFillter.put("withDiscount", withDiscount);
			 */
			JSONObject jObj = new JSONObject();

			jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesReport", jObjFillter);

			JSONArray jColHeaderArr = (JSONArray) jObj.get("ColHeader");
			colHeader = jColHeaderArr;
			int colCount = Integer.parseInt(jObj.get("colCount").toString());
			int rowCount = Integer.parseInt(jObj.get("RowCount").toString());
			double totalSale = 0;
			double totalDiscAmt = 0, totalSubTotal = 0, totalTaxAmt = 0, totalSettleAmt = 0, totalTipAmt = 0, totalQty1 = 0, totalAmount = 0, subTotal = 0, discountTotal = 0, SalesAmount = 0, Tax = 0, totalDiscount = 0, totalSubTotalDWise = 0, totalTax = 0, totalTaxableAmt = 0, totalDisc = 0;

			int totalNoOfBills = 0;
			int totalQty = 0;
			List listTotal = new ArrayList();
			switch (ReportName.substring(3)) {
			case "SettlementWise":
				totalSale = Double.parseDouble(jObj.get("TotalSale").toString());
				listTotal.add("Total");
				listTotal.add(totalSale);
				break;
			case "BillWise":
				totalDiscAmt = Double.parseDouble(jObj.get("totalDiscAmt").toString());
				totalSubTotal = Double.parseDouble(jObj.get("totalSubTotal").toString());
				totalTaxAmt = Double.parseDouble(jObj.get("totalTaxAmt").toString());
				totalSettleAmt = Double.parseDouble(jObj.get("totalSettleAmt").toString());
				totalTipAmt = Double.parseDouble(jObj.get("totalTipAmt").toString());
				listTotal.add("Total");
				listTotal.add(totalSubTotal);
				listTotal.add(totalDiscAmt);
				listTotal.add(totalTaxAmt);
				listTotal.add(totalSettleAmt);
				listTotal.add(totalTipAmt);
				break;
			case "ItemWise":
				totalQty1 = Double.parseDouble(jObj.get("totalQty").toString());
				totalQty = (int) totalQty1;
				totalAmount = Double.parseDouble(jObj.get("totalAmount").toString());
				subTotal = Double.parseDouble(jObj.get("subTotal").toString());
				discountTotal = Double.parseDouble(jObj.get("discountTotal").toString());
				listTotal.add("Total");
				listTotal.add(totalQty);
				listTotal.add(totalAmount);
				listTotal.add(subTotal);
				listTotal.add(discountTotal);
				break;
			case "MenuHeadWise":
				totalQty1 = Double.parseDouble(jObj.get("totalQty").toString());
				totalQty = (int) totalQty1;
				totalAmount = Double.parseDouble(jObj.get("totalAmount").toString());
				subTotal = Double.parseDouble(jObj.get("subTotal").toString());
				discountTotal = Double.parseDouble(jObj.get("discountTotal").toString());
				listTotal.add("Total");
				listTotal.add(totalQty);
				listTotal.add(totalAmount);
				listTotal.add(subTotal);
				listTotal.add(discountTotal);
				break;
			case "GroupWise":
				totalQty1 = Double.parseDouble(jObj.get("totalQty").toString());
				totalQty = (int) totalQty1;
				totalAmount = Double.parseDouble(jObj.get("totalAmount").toString());
				subTotal = Double.parseDouble(jObj.get("subTotal").toString());
				discountTotal = Double.parseDouble(jObj.get("discountTotal").toString());
				listTotal.add("Total");
				listTotal.add(totalQty);
				listTotal.add(totalAmount);
				listTotal.add(subTotal);
				listTotal.add(discountTotal);
				break;
			case "SubGroupWise":
				totalQty1 = Double.parseDouble(jObj.get("totalQty").toString());
				totalQty = (int) totalQty1;
				totalAmount = Double.parseDouble(jObj.get("SalesAmt").toString());
				subTotal = Double.parseDouble(jObj.get("subTotal").toString());
				discountTotal = Double.parseDouble(jObj.get("discountTotal").toString());
				listTotal.add("Total");
				listTotal.add(totalQty);
				listTotal.add(totalAmount);
				listTotal.add(subTotal);
				listTotal.add(discountTotal);
				break;
			case "CustWise":
				totalQty1 = Double.parseDouble(jObj.get("billCount").toString());
				totalQty = (int) totalQty1;
				totalAmount = Double.parseDouble(jObj.get("grandTotal").toString());
				listTotal.add("Total");
				listTotal.add(totalQty);
				listTotal.add(totalAmount);
				break;
			case "WaiterWise":
				totalSale = Double.parseDouble(jObj.get("TotalAmount").toString());
				listTotal.add("Total");
				listTotal.add(totalSale);
				break;
			case "DeliveryBoyWise":
				totalSale = Double.parseDouble(jObj.get("TotalAmount").toString());
				listTotal.add("Total");
				listTotal.add(totalSale);
				break;
			case "CostCenterWise":
				totalQty1 = Double.parseDouble(jObj.get("totalQty").toString());
				totalQty = (int) totalQty1;
				totalAmount = Double.parseDouble(jObj.get("totalAmt").toString());
				subTotal = Double.parseDouble(jObj.get("subTotal").toString());
				discountTotal = Double.parseDouble(jObj.get("discountTotal").toString());
				listTotal.add("Total");
				listTotal.add(totalQty);
				listTotal.add(subTotal);
				listTotal.add(totalAmount);
				listTotal.add(discountTotal);
				break;
			case "HomeDeliveryWise":
				SalesAmount = Double.parseDouble(jObj.get("SalesAmt").toString());
				Tax = Double.parseDouble(jObj.get("sumtax").toString());
				discountTotal = Double.parseDouble(jObj.get("sumDisc").toString());
				listTotal.add("Total");
				listTotal.add(discountTotal);
				listTotal.add(Tax);
				listTotal.add(SalesAmount);
				break;
			case "TableWise":
				SalesAmount = Double.parseDouble(jObj.get("SalesAmt").toString());
				listTotal.add("Total");
				listTotal.add(SalesAmount);
				break;
			case "HourlyWise":
				SalesAmount = Double.parseDouble(jObj.get("SalesAmt").toString());
				listTotal.add("Total");
				listTotal.add(SalesAmount);
				break;
			case "AreaWise":
				SalesAmount = Double.parseDouble(jObj.get("SalesAmt").toString());
				listTotal.add("Total");
				listTotal.add(SalesAmount);
				break;
			case "DayWiseSales":
				totalNoOfBills = Integer.parseInt(jObj.get("totalNoOfBills").toString());
				totalAmount = Double.parseDouble(jObj.get("totAmount").toString());
				totalSubTotalDWise = Double.parseDouble(jObj.get("totalSubTotal").toString());
				totalDiscount = Double.parseDouble(jObj.get("totalDiscount").toString());
				totalTaxAmt = Double.parseDouble(jObj.get("totalTaxAmt").toString());
				listTotal.add("Total");
				listTotal.add(totalNoOfBills);
				listTotal.add(totalSubTotalDWise);
				listTotal.add(totalDiscount);
				listTotal.add(totalTaxAmt);
				listTotal.add(totalAmount);
				break;
			case "TaxWiseSales":
				totalTax = Double.parseDouble(jObj.get("totalTax").toString());
				totalTaxableAmt = Double.parseDouble(jObj.get("totalTaxableAmt").toString());
				listTotal.add("Total");
				listTotal.add(totalTaxableAmt);
				listTotal.add(totalTax);
				break;
			case "TipReport":
				SalesAmount = Double.parseDouble(jObj.get("SalesAmount").toString());
				totalSubTotal = Double.parseDouble(jObj.get("subTotal").toString());
				totalDiscount = Double.parseDouble(jObj.get("Disc").toString());
				totalTaxAmt = Double.parseDouble(jObj.get("totalTaxAmt").toString());
				totalTipAmt = Double.parseDouble(jObj.get("tipAmountTotal").toString());
				listTotal.add("Total");
				listTotal.add(totalDiscount);
				listTotal.add(totalSubTotal);
				listTotal.add(totalTaxAmt);
				listTotal.add(totalTipAmt);
				listTotal.add(SalesAmount);
				break;
			case "ItemModifierWise":
				totalQty1 = Double.parseDouble(jObj.get("totalQty").toString());
				totalQty = (int) totalQty1;
				SalesAmount = Double.parseDouble(jObj.get("totalAmount").toString());
				listTotal.add("Total");
				listTotal.add(totalQty);
				listTotal.add(SalesAmount);
				break;
			case "MenuHeadWiseWithModifier":
				totalQty1 = Double.parseDouble(jObj.get("totalQty").toString());
				totalQty = (int) totalQty1;
				SalesAmount = Double.parseDouble(jObj.get("totalAmount").toString());
				listTotal.add("Total");
				listTotal.add(totalQty);
				listTotal.add(SalesAmount);

				break;
			case "ItemHourlyWise":
				totalDisc = Double.parseDouble(jObj.get("totalDisc").toString());
				SalesAmount = Double.parseDouble(jObj.get("totalAmount").toString());
				listTotal.add("Total");
				listTotal.add(totalDisc);
				listTotal.add(SalesAmount);
				break;
			case "OperatorWise":
				totalDisc = Double.parseDouble(jObj.get("totalDisc").toString());
				SalesAmount = Double.parseDouble(jObj.get("totalAmount").toString());
				listTotal.add("Total");
				listTotal.add(totalDisc);
				listTotal.add(SalesAmount);
				break;
			case "MonthlySalesFlash":
				SalesAmount = Double.parseDouble(jObj.get("totalSale").toString());
				listTotal.add("Total");
				listTotal.add(SalesAmount);
				break;

			}
			for (int i = 0; i < rowCount; i++) {
				resMap.put("" + i, (List) jObj.get("" + i));
			}
			resMap.put("Header", colHeader);
			resMap.put("ColCount", colCount);
			resMap.put("RowCount", rowCount);
			resMap.put("Total", listTotal);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resMap;
	}

	/* load Settlement wise data */
	@RequestMapping(value = "/loadSettlementWiseSalesReport", method = RequestMethod.POST)
	public @ResponseBody List<clsPOSSalesFlashReportsBean> funLoadSettlementWiseSalesReport(@RequestParam("POSName") String strPOSName, @RequestParam("FromDate") String FromDateTime, @RequestParam("ToDate") String ToDateTime, @RequestParam("Operator") String strOperator, @RequestParam("PayMode") String strPayMode, @RequestParam("txtFromBillNo") String strFromBill,
			@RequestParam("txtToBillNo") String strToBill, @RequestParam("txtReportType") String reportType, @RequestParam("txtType") String Type, @RequestParam("txtCustomer") String Customer, @RequestParam("chkConsolidatePOS") String ConsolidatePOS, @RequestParam("hidReportName") String ReportName, HttpServletResponse resp, HttpServletRequest req) {
		listSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
		listStockFlashModel = new ArrayList();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String LoginPOSCode = req.getSession().getAttribute("loginPOS").toString();

		String FromDate = "", FromTime = "", ToDate = "", ToTime = "";

		FromDate = FromDateTime.split(":")[0];
		FromTime = FromDateTime.split(":")[1];
		ToDate = ToDateTime.split(":")[0];
		ToTime = ToDateTime.split(":")[1];

		String strFromdate = FromDate.split("-")[2] + "-" + FromDate.split("-")[1] + "-" + FromDate.split("-")[0];
		String strToDate = ToDate.split("-")[2] + "-" + ToDate.split("-")[1] + "-" + ToDate.split("-")[0];
		try {
			JSONObject jObjFillter = new JSONObject();
			jObjFillter = funGetJSONData(strPOSName, FromDateTime, ToDateTime, strOperator, strPayMode, strFromBill, strToBill, reportType, Type, Customer, ConsolidatePOS, ReportName, userCode, LoginPOSCode);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesReport", jObjFillter);

			clsPOSSalesFlashReportsBean objBean = new clsPOSSalesFlashReportsBean();
			// objBean.setTotalSubTotal(jObj.get("TotalSale").toString());

			double totalSale = Double.parseDouble(jObj.get("TotalSale").toString());

			objBean.setTotalSubTotal(String.valueOf(decimalFormat.format(totalSale)));
			listSalesReport.add(objBean);

			List<clsPOSSalesFlashReportsBean> tmplistSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<clsPOSSalesFlashReportsBean>>() {
			}.getType();
			tmplistSalesReport = gson.fromJson(jObj.get("ListSettlementWiseSales").toString(), listType);
			clsPOSSalesFlashReportsBean obj;
			for (int i = 0; i < tmplistSalesReport.size(); i++) {
				obj = new clsPOSSalesFlashReportsBean();
				List DataList = new ArrayList<>();
				obj.setStrField1(tmplistSalesReport.get(i).getStrField1());
				obj.setStrField2(tmplistSalesReport.get(i).getStrField2());
				obj.setStrField3(tmplistSalesReport.get(i).getStrField3());

				double saleAmt = Double.parseDouble(tmplistSalesReport.get(i).getStrField3());
				double salePer = (saleAmt / totalSale) * 100;
				obj.setStrField4(String.valueOf(decimalFormat.format(salePer)));
				listSalesReport.add(obj);

				/*
				 * DataList.add(tmplistSalesReport.get(i).getStrField1());
				 * DataList.add(tmplistSalesReport.get(i).getStrField1());
				 * DataList.add(tmplistSalesReport.get(i).getStrField1());
				 * DataList.add(String.valueOf(decimalFormat.format(salePer)));
				 * listStockFlashModel.add(DataList);
				 */
			}
			System.out.print("@controller " + listSalesReport.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listSalesReport;
	}

	/* load bill wise data */
	@RequestMapping(value = "/loadBillWiseSalesReport", method = RequestMethod.POST)
	public @ResponseBody List<clsPOSSalesFlashReportsBean> funGetSalesDetails(@RequestParam("POSName") String strPOSName, @RequestParam("FromDate") String FromDateTime, @RequestParam("ToDate") String ToDateTime, @RequestParam("Operator") String strOperator, @RequestParam("PayMode") String strPayMode, @RequestParam("txtFromBillNo") String strFromBill, @RequestParam("txtToBillNo") String strToBill,
			@RequestParam("txtReportType") String reportType, @RequestParam("txtType") String Type, @RequestParam("txtCustomer") String Customer, @RequestParam("chkConsolidatePOS") String ConsolidatePOS, @RequestParam("hidReportName") String ReportName, HttpServletResponse resp, HttpServletRequest req) {
		listSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String LoginPOSCode = req.getSession().getAttribute("loginPOS").toString();
		try {

			JSONObject jObjFillter = new JSONObject();
			jObjFillter = funGetJSONData(strPOSName, FromDateTime, ToDateTime, strOperator, strPayMode, strFromBill, strToBill, reportType, Type, Customer, ConsolidatePOS, ReportName, userCode, LoginPOSCode);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesReport", jObjFillter);

			clsPOSSalesFlashReportsBean objBean = new clsPOSSalesFlashReportsBean();
			double totalDiscAmt = 0, totalSubTotal = 0, totalTaxAmt = 0, totalSettleAmt = 0, totalTipAmt = 0;
			totalDiscAmt = Double.parseDouble(jObj.get("totalDiscAmt").toString());
			totalSubTotal = Double.parseDouble(jObj.get("totalSubTotal").toString());
			totalTaxAmt = Double.parseDouble(jObj.get("totalTaxAmt").toString());
			totalSettleAmt = Double.parseDouble(jObj.get("totalSettleAmt").toString());
			totalTipAmt = Double.parseDouble(jObj.get("totalTipAmt").toString());

			objBean.setTotalDiscAmt(String.valueOf(decimalFormat.format(totalDiscAmt)));
			objBean.setTotalSubTotal(String.valueOf(decimalFormat.format(totalSubTotal)));
			objBean.setTotalTaxAmt(String.valueOf(decimalFormat.format(totalTaxAmt)));
			objBean.setTotalSettleAmt(String.valueOf(decimalFormat.format(totalSettleAmt)));
			objBean.setTotalTipAmt(String.valueOf(decimalFormat.format(totalTipAmt)));
			listSalesReport.add(objBean);

			List<clsPOSSalesFlashReportsBean> tmplistSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<clsPOSSalesFlashReportsBean>>() {
			}.getType();
			tmplistSalesReport = gson.fromJson(jObj.get("TempListBillWiseSales").toString(), listType);

			for (int i = 0; i < tmplistSalesReport.size(); i++) {
				listSalesReport.add(tmplistSalesReport.get(i));
			}
			System.out.print("@controller " + listSalesReport.size());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listSalesReport;
	}

	/* load Item wise data */
	@RequestMapping(value = "/loadItemWiseSalesReport", method = RequestMethod.POST)
	public @ResponseBody List<clsPOSSalesFlashReportsBean> funLoadItemWiseSalesReport(@RequestParam("POSName") String strPOSName, @RequestParam("FromDate") String FromDateTime, @RequestParam("ToDate") String ToDateTime, @RequestParam("Operator") String strOperator, @RequestParam("PayMode") String strPayMode, @RequestParam("txtFromBillNo") String strFromBill,
			@RequestParam("txtToBillNo") String strToBill, @RequestParam("txtReportType") String reportType, @RequestParam("txtType") String Type, @RequestParam("txtCustomer") String Customer, @RequestParam("chkConsolidatePOS") String ConsolidatePOS, @RequestParam("hidReportName") String ReportName, HttpServletResponse resp, HttpServletRequest req) {
		listSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String LoginPOSCode = req.getSession().getAttribute("loginPOS").toString();
		JSONObject jObjFillter = new JSONObject();
		jObjFillter = funGetJSONData(strPOSName, FromDateTime, ToDateTime, strOperator, strPayMode, strFromBill, strToBill, reportType, Type, Customer, ConsolidatePOS, ReportName, userCode, LoginPOSCode);

		try {

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesReport", jObjFillter);

			clsPOSSalesFlashReportsBean objBean = new clsPOSSalesFlashReportsBean();
			// objBean.setTotalSubTotal(jObj.get("TotalSale").toString());

			double totalQty1 = 0, totalAmount = 0, subTotal = 0, discountTotal = 0;
			totalQty1 = Double.parseDouble(jObj.get("totalQty").toString());
			int totalQty = (int) totalQty1;
			totalAmount = Double.parseDouble(jObj.get("totalAmount").toString());
			subTotal = Double.parseDouble(jObj.get("subTotal").toString());
			discountTotal = Double.parseDouble(jObj.get("discountTotal").toString());

			objBean.setTotalQuantity(String.valueOf(totalQty));
			objBean.setTotalAmount(String.valueOf(decimalFormat.format(totalAmount)));
			objBean.setTotalSubTotal(String.valueOf(decimalFormat.format(subTotal)));
			objBean.setTotalDiscAmt(String.valueOf(decimalFormat.format(discountTotal)));

			listSalesReport.add(objBean);

			List<clsPOSSalesFlashReportsBean> tmplistSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<clsPOSSalesFlashReportsBean>>() {
			}.getType();
			tmplistSalesReport = gson.fromJson(jObj.get("ListItemWiseSales").toString(), listType);
			clsPOSSalesFlashReportsBean obj;
			for (int i = 0; i < tmplistSalesReport.size(); i++) {
				obj = new clsPOSSalesFlashReportsBean();
				obj.setStrField1(tmplistSalesReport.get(i).getStrField1());
				obj.setStrField2(tmplistSalesReport.get(i).getStrField2());
				obj.setStrField3(tmplistSalesReport.get(i).getStrField3());
				obj.setStrField4(tmplistSalesReport.get(i).getStrField4());
				obj.setStrField5(tmplistSalesReport.get(i).getStrField5());
				obj.setStrField6(tmplistSalesReport.get(i).getStrField6());

				listSalesReport.add(obj);
			}
			System.out.print("@controller " + listSalesReport.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listSalesReport;
	}

	/* load Menu Head wise data */
	@RequestMapping(value = "/loadMenuHeadWiseSalesReport", method = RequestMethod.POST)
	public @ResponseBody List<clsPOSSalesFlashReportsBean> funLoadMenuHeadWiseSalesReport(@RequestParam("POSName") String strPOSName, @RequestParam("FromDate") String FromDateTime, @RequestParam("ToDate") String ToDateTime, @RequestParam("Operator") String strOperator, @RequestParam("PayMode") String strPayMode, @RequestParam("txtFromBillNo") String strFromBill,
			@RequestParam("txtToBillNo") String strToBill, @RequestParam("txtReportType") String reportType, @RequestParam("txtType") String Type, @RequestParam("txtCustomer") String Customer, @RequestParam("chkConsolidatePOS") String ConsolidatePOS, @RequestParam("hidReportName") String ReportName, HttpServletResponse resp, HttpServletRequest req) {
		listSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String LoginPOSCode = req.getSession().getAttribute("loginPOS").toString();
		JSONObject jObjFillter = new JSONObject();
		jObjFillter = funGetJSONData(strPOSName, FromDateTime, ToDateTime, strOperator, strPayMode, strFromBill, strToBill, reportType, Type, Customer, ConsolidatePOS, ReportName, userCode, LoginPOSCode);

		try {

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesReport", jObjFillter);

			clsPOSSalesFlashReportsBean objBean = new clsPOSSalesFlashReportsBean();
			// objBean.setTotalSubTotal(jObj.get("TotalSale").toString());

			double totalQty1 = 0, totalAmount = 0, subTotal = 0, discountTotal = 0;
			totalQty1 = Double.parseDouble(jObj.get("totalQty").toString());
			int totalQty = (int) totalQty1;
			totalAmount = Double.parseDouble(jObj.get("totalAmount").toString());
			subTotal = Double.parseDouble(jObj.get("subTotal").toString());
			discountTotal = Double.parseDouble(jObj.get("discountTotal").toString());

			objBean.setTotalQuantity(String.valueOf(totalQty));
			objBean.setTotalAmount(String.valueOf(decimalFormat.format(totalAmount)));
			objBean.setTotalSubTotal(String.valueOf(decimalFormat.format(subTotal)));
			objBean.setTotalDiscAmt(String.valueOf(decimalFormat.format(discountTotal)));

			listSalesReport.add(objBean);

			List<clsPOSSalesFlashReportsBean> tmplistSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<clsPOSSalesFlashReportsBean>>() {
			}.getType();
			tmplistSalesReport = gson.fromJson(jObj.get("ListMenuHeadWiseSales").toString(), listType);
			clsPOSSalesFlashReportsBean obj;
			for (int i = 0; i < tmplistSalesReport.size(); i++) {
				obj = new clsPOSSalesFlashReportsBean();
				obj.setStrField1(tmplistSalesReport.get(i).getStrField1());
				obj.setStrField2(tmplistSalesReport.get(i).getStrField2());
				obj.setStrField3(tmplistSalesReport.get(i).getStrField3());
				obj.setStrField4(tmplistSalesReport.get(i).getStrField4());
				obj.setStrField5(tmplistSalesReport.get(i).getStrField5());
				obj.setStrField6(tmplistSalesReport.get(i).getStrField6());

				double saleAmt = Double.parseDouble(tmplistSalesReport.get(i).getStrField4());
				double salePer = (saleAmt / totalAmount) * 100;
				obj.setStrField7(String.valueOf(decimalFormat.format(salePer)));
				listSalesReport.add(obj);
			}
			System.out.print("@controller " + listSalesReport.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listSalesReport;
	}

	/* load Group wise data */
	@RequestMapping(value = "/loadGroupWiseSalesReport", method = RequestMethod.POST)
	public @ResponseBody List<clsPOSSalesFlashReportsBean> funLoadGroupWiseSalesReport(@RequestParam("POSName") String strPOSName, @RequestParam("FromDate") String FromDateTime, @RequestParam("ToDate") String ToDateTime, @RequestParam("Operator") String strOperator, @RequestParam("PayMode") String strPayMode, @RequestParam("txtFromBillNo") String strFromBill,
			@RequestParam("txtToBillNo") String strToBill, @RequestParam("txtReportType") String reportType, @RequestParam("txtType") String Type, @RequestParam("txtCustomer") String Customer, @RequestParam("chkConsolidatePOS") String ConsolidatePOS, @RequestParam("hidReportName") String ReportName, HttpServletResponse resp, HttpServletRequest req) {
		listSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String LoginPOSCode = req.getSession().getAttribute("loginPOS").toString();
		JSONObject jObjFillter = new JSONObject();
		jObjFillter = funGetJSONData(strPOSName, FromDateTime, ToDateTime, strOperator, strPayMode, strFromBill, strToBill, reportType, Type, Customer, ConsolidatePOS, ReportName, userCode, LoginPOSCode);

		try {

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesReport", jObjFillter);

			clsPOSSalesFlashReportsBean objBean = new clsPOSSalesFlashReportsBean();
			// objBean.setTotalSubTotal(jObj.get("TotalSale").toString());

			double totalQty1 = 0, totalAmount = 0, subTotal = 0, discountTotal = 0;
			totalQty1 = Double.parseDouble(jObj.get("totalQty").toString());
			int totalQty = (int) totalQty1;
			totalAmount = Double.parseDouble(jObj.get("totalAmount").toString());
			subTotal = Double.parseDouble(jObj.get("subTotal").toString());
			discountTotal = Double.parseDouble(jObj.get("discountTotal").toString());

			objBean.setTotalQuantity(String.valueOf(totalQty));
			objBean.setTotalAmount(String.valueOf(decimalFormat.format(totalAmount)));
			objBean.setTotalSubTotal(String.valueOf(decimalFormat.format(subTotal)));
			objBean.setTotalDiscAmt(String.valueOf(decimalFormat.format(discountTotal)));

			listSalesReport.add(objBean);

			List<clsPOSSalesFlashReportsBean> tmplistSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<clsPOSSalesFlashReportsBean>>() {
			}.getType();
			tmplistSalesReport = gson.fromJson(jObj.get("ListGroupWiseSales").toString(), listType);
			clsPOSSalesFlashReportsBean obj;
			for (int i = 0; i < tmplistSalesReport.size(); i++) {
				obj = new clsPOSSalesFlashReportsBean();
				obj.setStrField1(tmplistSalesReport.get(i).getStrField1());
				obj.setStrField2(tmplistSalesReport.get(i).getStrField2());
				obj.setStrField3(tmplistSalesReport.get(i).getStrField3());
				obj.setStrField4(tmplistSalesReport.get(i).getStrField4());
				obj.setStrField5(tmplistSalesReport.get(i).getStrField5());
				obj.setStrField6(tmplistSalesReport.get(i).getStrField6());

				double saleAmt = Double.parseDouble(tmplistSalesReport.get(i).getStrField4());
				double salePer = (saleAmt / totalAmount) * 100;
				obj.setStrField7(String.valueOf(decimalFormat.format(salePer)));
				listSalesReport.add(obj);
			}
			System.out.print("@controller " + listSalesReport.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listSalesReport;
	}

	/* load Sub Group wise data */
	@RequestMapping(value = "/loadSubGroupWiseSalesReport", method = RequestMethod.POST)
	public @ResponseBody List<clsPOSSalesFlashReportsBean> loadSubGroupWiseSalesReport(@RequestParam("POSName") String strPOSName, @RequestParam("FromDate") String FromDateTime, @RequestParam("ToDate") String ToDateTime, @RequestParam("Operator") String strOperator, @RequestParam("PayMode") String strPayMode, @RequestParam("txtFromBillNo") String strFromBill,
			@RequestParam("txtToBillNo") String strToBill, @RequestParam("txtReportType") String reportType, @RequestParam("txtType") String Type, @RequestParam("txtCustomer") String Customer, @RequestParam("chkConsolidatePOS") String ConsolidatePOS, @RequestParam("hidReportName") String ReportName, HttpServletResponse resp, HttpServletRequest req) {
		listSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String LoginPOSCode = req.getSession().getAttribute("loginPOS").toString();

		JSONObject jObjFillter = new JSONObject();
		jObjFillter = funGetJSONData(strPOSName, FromDateTime, ToDateTime, strOperator, strPayMode, strFromBill, strToBill, reportType, Type, Customer, ConsolidatePOS, ReportName, userCode, LoginPOSCode);

		try {

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesReport", jObjFillter);

			clsPOSSalesFlashReportsBean objBean = new clsPOSSalesFlashReportsBean();
			// objBean.setTotalSubTotal(jObj.get("TotalSale").toString());

			double totalQty1 = 0, totalAmount = 0, subTotal = 0, discountTotal = 0;
			totalQty1 = Double.parseDouble(jObj.get("totalQty").toString());
			int totalQty = (int) totalQty1;
			totalAmount = Double.parseDouble(jObj.get("SalesAmt").toString());
			subTotal = Double.parseDouble(jObj.get("subTotal").toString());
			discountTotal = Double.parseDouble(jObj.get("discountTotal").toString());

			objBean.setTotalQuantity(String.valueOf(totalQty));
			objBean.setTotalAmount(String.valueOf(decimalFormat.format(totalAmount)));
			objBean.setTotalSubTotal(String.valueOf(decimalFormat.format(subTotal)));
			objBean.setTotalDiscAmt(String.valueOf(decimalFormat.format(discountTotal)));

			listSalesReport.add(objBean);

			List<clsPOSSalesFlashReportsBean> tmplistSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<clsPOSSalesFlashReportsBean>>() {
			}.getType();
			tmplistSalesReport = gson.fromJson(jObj.get("ListSubGroupWiseSales").toString(), listType);
			clsPOSSalesFlashReportsBean obj;
			for (int i = 0; i < tmplistSalesReport.size(); i++) {
				obj = new clsPOSSalesFlashReportsBean();
				obj.setStrField1(tmplistSalesReport.get(i).getStrField1());
				obj.setStrField2(tmplistSalesReport.get(i).getStrField2());
				obj.setStrField3(tmplistSalesReport.get(i).getStrField3());
				obj.setStrField4(tmplistSalesReport.get(i).getStrField4());
				obj.setStrField5(tmplistSalesReport.get(i).getStrField5());
				obj.setStrField6(tmplistSalesReport.get(i).getStrField6());

				double saleAmt = Double.parseDouble(tmplistSalesReport.get(i).getStrField4());
				double salePer = (saleAmt / totalAmount) * 100;
				obj.setStrField7(String.valueOf(decimalFormat.format(salePer)));
				listSalesReport.add(obj);
			}
			System.out.print("@controller " + listSalesReport.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listSalesReport;
	}

	/* load Customer wise data */
	@RequestMapping(value = "/loadCustWiseSalesReport", method = RequestMethod.POST)
	public @ResponseBody List<clsPOSSalesFlashReportsBean> funLoadCustWiseSalesReport(@RequestParam("POSName") String strPOSName, @RequestParam("FromDate") String FromDateTime, @RequestParam("ToDate") String ToDateTime, @RequestParam("Operator") String strOperator, @RequestParam("PayMode") String strPayMode, @RequestParam("txtFromBillNo") String strFromBill,
			@RequestParam("txtToBillNo") String strToBill, @RequestParam("txtReportType") String reportType, @RequestParam("txtType") String Type, @RequestParam("txtCustomer") String Customer, @RequestParam("chkConsolidatePOS") String ConsolidatePOS, @RequestParam("hidReportName") String ReportName, HttpServletResponse resp, HttpServletRequest req) {
		listSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String LoginPOSCode = req.getSession().getAttribute("loginPOS").toString();

		JSONObject jObjFillter = new JSONObject();
		jObjFillter = funGetJSONData(strPOSName, FromDateTime, ToDateTime, strOperator, strPayMode, strFromBill, strToBill, reportType, Type, Customer, ConsolidatePOS, ReportName, userCode, LoginPOSCode);

		try {

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesReport", jObjFillter);

			clsPOSSalesFlashReportsBean objBean = new clsPOSSalesFlashReportsBean();
			// objBean.setTotalSubTotal(jObj.get("TotalSale").toString());

			double totalQty1 = 0, totalAmount = 0, subTotal = 0, discountTotal = 0;
			totalQty1 = Double.parseDouble(jObj.get("billCount").toString());
			int totalQty = (int) totalQty1;
			totalAmount = Double.parseDouble(jObj.get("grandTotal").toString());

			objBean.setTotalQuantity(String.valueOf(totalQty));
			objBean.setTotalAmount(String.valueOf(decimalFormat.format(totalAmount)));

			listSalesReport.add(objBean);

			List<clsPOSSalesFlashReportsBean> tmplistSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<clsPOSSalesFlashReportsBean>>() {
			}.getType();
			tmplistSalesReport = gson.fromJson(jObj.get("ListCustWiseSales").toString(), listType);
			clsPOSSalesFlashReportsBean obj;
			for (int i = 0; i < tmplistSalesReport.size(); i++) {
				obj = new clsPOSSalesFlashReportsBean();
				obj.setStrField1(tmplistSalesReport.get(i).getStrField1());
				obj.setStrField2(tmplistSalesReport.get(i).getStrField2());
				obj.setStrField3(tmplistSalesReport.get(i).getStrField3());
				/*
				 * obj.setStrField4(tmplistSalesReport.get(i).getStrField4());
				 * obj.setStrField5(tmplistSalesReport.get(i).getStrField5());
				 * obj.setStrField6(tmplistSalesReport.get(i).getStrField6());
				 * obj.setStrField7(tmplistSalesReport.get(i).getStrField7());
				 */
				listSalesReport.add(obj);
			}
			System.out.print("@controller " + listSalesReport.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listSalesReport;
	}

	/* load Waiter wise data */
	@RequestMapping(value = "/loadWaiterWiseSalesReport", method = RequestMethod.POST)
	public @ResponseBody List<clsPOSSalesFlashReportsBean> funLoadWaiterWiseSalesReport(@RequestParam("POSName") String strPOSName, @RequestParam("FromDate") String FromDateTime, @RequestParam("ToDate") String ToDateTime, @RequestParam("Operator") String strOperator, @RequestParam("PayMode") String strPayMode, @RequestParam("txtFromBillNo") String strFromBill,
			@RequestParam("txtToBillNo") String strToBill, @RequestParam("txtReportType") String reportType, @RequestParam("txtType") String Type, @RequestParam("txtCustomer") String Customer, @RequestParam("chkConsolidatePOS") String ConsolidatePOS, @RequestParam("hidReportName") String ReportName, HttpServletResponse resp, HttpServletRequest req) {
		listSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String LoginPOSCode = req.getSession().getAttribute("loginPOS").toString();

		String FromDate = "", FromTime = "", ToDate = "", ToTime = "";

		FromDate = FromDateTime.split(":")[0];
		FromTime = FromDateTime.split(":")[1];
		ToDate = ToDateTime.split(":")[0];
		ToTime = ToDateTime.split(":")[1];

		String strFromdate = FromDate.split("-")[2] + "-" + FromDate.split("-")[1] + "-" + FromDate.split("-")[0];
		String strToDate = ToDate.split("-")[2] + "-" + ToDate.split("-")[1] + "-" + ToDate.split("-")[0];
		try {
			JSONObject jObjFillter = new JSONObject();
			jObjFillter = funGetJSONData(strPOSName, FromDateTime, ToDateTime, strOperator, strPayMode, strFromBill, strToBill, reportType, Type, Customer, ConsolidatePOS, ReportName, userCode, LoginPOSCode);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesReport", jObjFillter);

			clsPOSSalesFlashReportsBean objBean = new clsPOSSalesFlashReportsBean();
			// objBean.setTotalSubTotal(jObj.get("TotalSale").toString());

			double totalSale = 0;
			totalSale = Double.parseDouble(jObj.get("TotalAmount").toString());

			objBean.setTotalSubTotal(String.valueOf(decimalFormat.format(totalSale)));
			listSalesReport.add(objBean);

			List<clsPOSSalesFlashReportsBean> tmplistSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<clsPOSSalesFlashReportsBean>>() {
			}.getType();
			tmplistSalesReport = gson.fromJson(jObj.get("ListWaiterWiseSales").toString(), listType);
			clsPOSSalesFlashReportsBean obj;
			for (int i = 0; i < tmplistSalesReport.size(); i++) {
				obj = new clsPOSSalesFlashReportsBean();
				obj.setStrField1(tmplistSalesReport.get(i).getStrField1());
				obj.setStrField2(tmplistSalesReport.get(i).getStrField2());
				obj.setStrField3(tmplistSalesReport.get(i).getStrField3());
				obj.setStrField4(tmplistSalesReport.get(i).getStrField4());
				listSalesReport.add(obj);
			}
			System.out.print("@controller " + listSalesReport.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listSalesReport;
	}

	/* load DeliveryBoy wise data */
	@RequestMapping(value = "/loadDeliveryBoyWiseSalesReport", method = RequestMethod.POST)
	public @ResponseBody List<clsPOSSalesFlashReportsBean> funLoadDeliveryBoyWiseSalesReport(@RequestParam("POSName") String strPOSName, @RequestParam("FromDate") String FromDateTime, @RequestParam("ToDate") String ToDateTime, @RequestParam("Operator") String strOperator, @RequestParam("PayMode") String strPayMode, @RequestParam("txtFromBillNo") String strFromBill,
			@RequestParam("txtToBillNo") String strToBill, @RequestParam("txtReportType") String reportType, @RequestParam("txtType") String Type, @RequestParam("txtCustomer") String Customer, @RequestParam("chkConsolidatePOS") String ConsolidatePOS, @RequestParam("hidReportName") String ReportName, HttpServletResponse resp, HttpServletRequest req) {
		listSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String LoginPOSCode = req.getSession().getAttribute("loginPOS").toString();

		String FromDate = "", FromTime = "", ToDate = "", ToTime = "";

		FromDate = FromDateTime.split(":")[0];
		FromTime = FromDateTime.split(":")[1];
		ToDate = ToDateTime.split(":")[0];
		ToTime = ToDateTime.split(":")[1];

		String strFromdate = FromDate.split("-")[2] + "-" + FromDate.split("-")[1] + "-" + FromDate.split("-")[0];
		String strToDate = ToDate.split("-")[2] + "-" + ToDate.split("-")[1] + "-" + ToDate.split("-")[0];
		try {
			JSONObject jObjFillter = new JSONObject();
			jObjFillter = funGetJSONData(strPOSName, FromDateTime, ToDateTime, strOperator, strPayMode, strFromBill, strToBill, reportType, Type, Customer, ConsolidatePOS, ReportName, userCode, LoginPOSCode);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesReport", jObjFillter);

			clsPOSSalesFlashReportsBean objBean = new clsPOSSalesFlashReportsBean();
			// objBean.setTotalSubTotal(jObj.get("TotalSale").toString());

			double totalSale = 0;
			totalSale = Double.parseDouble(jObj.get("TotalAmount").toString());

			objBean.setTotalSubTotal(String.valueOf(decimalFormat.format(totalSale)));
			listSalesReport.add(objBean);

			List<clsPOSSalesFlashReportsBean> tmplistSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<clsPOSSalesFlashReportsBean>>() {
			}.getType();
			tmplistSalesReport = gson.fromJson(jObj.get("ListDelBoyWiseSales").toString(), listType);
			clsPOSSalesFlashReportsBean obj;
			for (int i = 0; i < tmplistSalesReport.size(); i++) {
				obj = new clsPOSSalesFlashReportsBean();
				obj.setStrField1(tmplistSalesReport.get(i).getStrField1());
				obj.setStrField2(tmplistSalesReport.get(i).getStrField2());
				obj.setStrField3(tmplistSalesReport.get(i).getStrField3());
				obj.setStrField4(tmplistSalesReport.get(i).getStrField4());
				listSalesReport.add(obj);
			}
			System.out.print("@controller " + listSalesReport.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listSalesReport;
	}

	/* load Cost Center wise data */
	@RequestMapping(value = "/loadCostCenterWiseSalesReport", method = RequestMethod.POST)
	public @ResponseBody List<clsPOSSalesFlashReportsBean> funLoadCostCenterWiseSalesReport(@RequestParam("POSName") String strPOSName, @RequestParam("FromDate") String FromDateTime, @RequestParam("ToDate") String ToDateTime, @RequestParam("Operator") String strOperator, @RequestParam("PayMode") String strPayMode, @RequestParam("txtFromBillNo") String strFromBill,
			@RequestParam("txtToBillNo") String strToBill, @RequestParam("txtReportType") String reportType, @RequestParam("txtType") String Type, @RequestParam("txtCustomer") String Customer, @RequestParam("chkConsolidatePOS") String ConsolidatePOS, @RequestParam("hidReportName") String ReportName, HttpServletResponse resp, HttpServletRequest req) {
		listSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String LoginPOSCode = req.getSession().getAttribute("loginPOS").toString();

		String FromDate = "", FromTime = "", ToDate = "", ToTime = "";

		FromDate = FromDateTime.split(":")[0];
		FromTime = FromDateTime.split(":")[1];
		ToDate = ToDateTime.split(":")[0];
		ToTime = ToDateTime.split(":")[1];

		String strFromdate = FromDate.split("-")[2] + "-" + FromDate.split("-")[1] + "-" + FromDate.split("-")[0];
		String strToDate = ToDate.split("-")[2] + "-" + ToDate.split("-")[1] + "-" + ToDate.split("-")[0];
		try {
			JSONObject jObjFillter = new JSONObject();
			jObjFillter = funGetJSONData(strPOSName, FromDateTime, ToDateTime, strOperator, strPayMode, strFromBill, strToBill, reportType, Type, Customer, ConsolidatePOS, ReportName, userCode, LoginPOSCode);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesReport", jObjFillter);

			clsPOSSalesFlashReportsBean objBean = new clsPOSSalesFlashReportsBean();
			double totalQty1 = 0, totalAmount = 0, subTotal = 0, discountTotal = 0;
			totalQty1 = Double.parseDouble(jObj.get("totalQty").toString());
			int totalQty = (int) totalQty1;
			totalAmount = Double.parseDouble(jObj.get("totalAmt").toString());
			subTotal = Double.parseDouble(jObj.get("subTotal").toString());
			discountTotal = Double.parseDouble(jObj.get("discountTotal").toString());

			objBean.setTotalQuantity(String.valueOf(totalQty));
			objBean.setTotalAmount(String.valueOf(decimalFormat.format(totalAmount)));
			objBean.setTotalSubTotal(String.valueOf(decimalFormat.format(subTotal)));
			objBean.setTotalDiscAmt(String.valueOf(decimalFormat.format(discountTotal)));
			listSalesReport.add(objBean);

			List<clsPOSSalesFlashReportsBean> tmplistSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<clsPOSSalesFlashReportsBean>>() {
			}.getType();
			tmplistSalesReport = gson.fromJson(jObj.get("ListCostCentWiseSales").toString(), listType);
			clsPOSSalesFlashReportsBean obj;
			for (int i = 0; i < tmplistSalesReport.size(); i++) {
				obj = new clsPOSSalesFlashReportsBean();
				obj.setStrField1(tmplistSalesReport.get(i).getStrField1());
				obj.setStrField2(tmplistSalesReport.get(i).getStrField2());
				obj.setStrField3(tmplistSalesReport.get(i).getStrField3());
				obj.setStrField4(tmplistSalesReport.get(i).getStrField4());
				obj.setStrField5(tmplistSalesReport.get(i).getStrField5());
				obj.setStrField6(tmplistSalesReport.get(i).getStrField6());

				double saleAmt = Double.parseDouble(tmplistSalesReport.get(i).getStrField4());
				double salePer = (saleAmt / totalAmount) * 100;
				obj.setStrField7(String.valueOf(decimalFormat.format(salePer)));
				listSalesReport.add(obj);

			}
			System.out.print("@controller " + listSalesReport.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listSalesReport;
	}

	/* load Home Delivery wise data */
	@RequestMapping(value = "/loadHomeDelWiseSalesReport", method = RequestMethod.POST)
	public @ResponseBody List<clsPOSSalesFlashReportsBean> funLoadHomeDeliveryWiseSalesReport(@RequestParam("POSName") String strPOSName, @RequestParam("FromDate") String FromDateTime, @RequestParam("ToDate") String ToDateTime, @RequestParam("Operator") String strOperator, @RequestParam("PayMode") String strPayMode, @RequestParam("txtFromBillNo") String strFromBill,
			@RequestParam("txtToBillNo") String strToBill, @RequestParam("txtReportType") String reportType, @RequestParam("txtType") String Type, @RequestParam("txtCustomer") String Customer, @RequestParam("chkConsolidatePOS") String ConsolidatePOS, @RequestParam("hidReportName") String ReportName, HttpServletResponse resp, HttpServletRequest req) {
		listSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String LoginPOSCode = req.getSession().getAttribute("loginPOS").toString();

		String FromDate = "", FromTime = "", ToDate = "", ToTime = "";

		FromDate = FromDateTime.split(":")[0];
		FromTime = FromDateTime.split(":")[1];
		ToDate = ToDateTime.split(":")[0];
		ToTime = ToDateTime.split(":")[1];

		String strFromdate = FromDate.split("-")[2] + "-" + FromDate.split("-")[1] + "-" + FromDate.split("-")[0];
		String strToDate = ToDate.split("-")[2] + "-" + ToDate.split("-")[1] + "-" + ToDate.split("-")[0];
		try {
			JSONObject jObjFillter = new JSONObject();
			jObjFillter = funGetJSONData(strPOSName, FromDateTime, ToDateTime, strOperator, strPayMode, strFromBill, strToBill, reportType, Type, Customer, ConsolidatePOS, ReportName, userCode, LoginPOSCode);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesReport", jObjFillter);

			clsPOSSalesFlashReportsBean objBean = new clsPOSSalesFlashReportsBean();
			double SalesAmount = 0, Tax = 0, discountTotal = 0;

			SalesAmount = Double.parseDouble(jObj.get("SalesAmt").toString());
			Tax = Double.parseDouble(jObj.get("sumtax").toString());
			discountTotal = Double.parseDouble(jObj.get("sumDisc").toString());

			objBean.setTotalAmount(String.valueOf(decimalFormat.format(SalesAmount)));
			objBean.setTotalSubTotal(String.valueOf(decimalFormat.format(Tax)));// for
																				// Tax
			objBean.setTotalDiscAmt(String.valueOf(decimalFormat.format(discountTotal)));
			listSalesReport.add(objBean);

			List<clsPOSSalesFlashReportsBean> tmplistSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<clsPOSSalesFlashReportsBean>>() {
			}.getType();
			tmplistSalesReport = gson.fromJson(jObj.get("ListHomeDelWiseSales").toString(), listType);
			clsPOSSalesFlashReportsBean obj;
			for (int i = 0; i < tmplistSalesReport.size(); i++) {
				obj = new clsPOSSalesFlashReportsBean();
				obj.setStrField1(tmplistSalesReport.get(i).getStrField1());
				obj.setStrField2(tmplistSalesReport.get(i).getStrField2());
				obj.setStrField3(tmplistSalesReport.get(i).getStrField3());
				obj.setStrField4(tmplistSalesReport.get(i).getStrField4());
				obj.setStrField5(tmplistSalesReport.get(i).getStrField5());
				obj.setStrField6(tmplistSalesReport.get(i).getStrField6());
				obj.setStrField7(tmplistSalesReport.get(i).getStrField7());
				obj.setStrField8(tmplistSalesReport.get(i).getStrField8());
				obj.setStrField9(tmplistSalesReport.get(i).getStrField9());
				obj.setStrField10(tmplistSalesReport.get(i).getStrField10());
				obj.setStrField11(tmplistSalesReport.get(i).getStrField11());
				listSalesReport.add(obj);

			}
			System.out.print("@controller " + listSalesReport.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listSalesReport;
	}

	/* load Table wise data */
	@RequestMapping(value = "/loadTableWiseSalesReport", method = RequestMethod.POST)
	public @ResponseBody List<clsPOSSalesFlashReportsBean> funLoadTableWiseSalesReport(@RequestParam("POSName") String strPOSName, @RequestParam("FromDate") String FromDateTime, @RequestParam("ToDate") String ToDateTime, @RequestParam("Operator") String strOperator, @RequestParam("PayMode") String strPayMode, @RequestParam("txtFromBillNo") String strFromBill,
			@RequestParam("txtToBillNo") String strToBill, @RequestParam("txtReportType") String reportType, @RequestParam("txtType") String Type, @RequestParam("txtCustomer") String Customer, @RequestParam("chkConsolidatePOS") String ConsolidatePOS, @RequestParam("hidReportName") String ReportName, HttpServletResponse resp, HttpServletRequest req) {
		listSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String LoginPOSCode = req.getSession().getAttribute("loginPOS").toString();

		String FromDate = "", FromTime = "", ToDate = "", ToTime = "";

		FromDate = FromDateTime.split(":")[0];
		FromTime = FromDateTime.split(":")[1];
		ToDate = ToDateTime.split(":")[0];
		ToTime = ToDateTime.split(":")[1];

		String strFromdate = FromDate.split("-")[2] + "-" + FromDate.split("-")[1] + "-" + FromDate.split("-")[0];
		String strToDate = ToDate.split("-")[2] + "-" + ToDate.split("-")[1] + "-" + ToDate.split("-")[0];
		try {
			JSONObject jObjFillter = new JSONObject();
			jObjFillter = funGetJSONData(strPOSName, FromDateTime, ToDateTime, strOperator, strPayMode, strFromBill, strToBill, reportType, Type, Customer, ConsolidatePOS, ReportName, userCode, LoginPOSCode);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesReport", jObjFillter);

			clsPOSSalesFlashReportsBean objBean = new clsPOSSalesFlashReportsBean();
			double SalesAmount = 0;

			SalesAmount = Double.parseDouble(jObj.get("SalesAmt").toString());

			objBean.setTotalAmount(String.valueOf(decimalFormat.format(SalesAmount)));
			listSalesReport.add(objBean);

			List<clsPOSSalesFlashReportsBean> tmplistSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<clsPOSSalesFlashReportsBean>>() {
			}.getType();
			tmplistSalesReport = gson.fromJson(jObj.get("ListTableWiseSales").toString(), listType);
			clsPOSSalesFlashReportsBean obj;
			for (int i = 0; i < tmplistSalesReport.size(); i++) {
				obj = new clsPOSSalesFlashReportsBean();
				obj.setStrField1(tmplistSalesReport.get(i).getStrField1());
				obj.setStrField2(tmplistSalesReport.get(i).getStrField2());
				obj.setStrField3(tmplistSalesReport.get(i).getStrField3());
				listSalesReport.add(obj);

			}
			System.out.print("@controller " + listSalesReport.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listSalesReport;
	}

	/* load Hourly Head wise data */
	@RequestMapping(value = "/loadHourlyWiseSalesReport", method = RequestMethod.POST)
	public @ResponseBody List<clsPOSSalesFlashReportsBean> funLoadHourlyWiseSalesReport(@RequestParam("POSName") String strPOSName, @RequestParam("FromDate") String FromDateTime, @RequestParam("ToDate") String ToDateTime, @RequestParam("Operator") String strOperator, @RequestParam("PayMode") String strPayMode, @RequestParam("txtFromBillNo") String strFromBill,
			@RequestParam("txtToBillNo") String strToBill, @RequestParam("txtReportType") String reportType, @RequestParam("txtType") String Type, @RequestParam("txtCustomer") String Customer, @RequestParam("chkConsolidatePOS") String ConsolidatePOS, @RequestParam("hidReportName") String ReportName, HttpServletResponse resp, HttpServletRequest req) {
		listSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String LoginPOSCode = req.getSession().getAttribute("loginPOS").toString();

		JSONObject jObjFillter = new JSONObject();
		jObjFillter = funGetJSONData(strPOSName, FromDateTime, ToDateTime, strOperator, strPayMode, strFromBill, strToBill, reportType, Type, Customer, ConsolidatePOS, ReportName, userCode, LoginPOSCode);

		try {

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesReport", jObjFillter);

			clsPOSSalesFlashReportsBean objBean = new clsPOSSalesFlashReportsBean();
			// objBean.setTotalSubTotal(jObj.get("TotalSale").toString());
			double SalesAmount = 0;

			SalesAmount = Double.parseDouble(jObj.get("SalesAmt").toString());

			objBean.setTotalAmount(String.valueOf(decimalFormat.format(SalesAmount)));
			listSalesReport.add(objBean);

			List<clsPOSSalesFlashReportsBean> tmplistSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<clsPOSSalesFlashReportsBean>>() {
			}.getType();
			tmplistSalesReport = gson.fromJson(jObj.get("ListHourWiseSales").toString(), listType);
			clsPOSSalesFlashReportsBean obj;
			for (int i = 0; i < tmplistSalesReport.size(); i++) {
				obj = new clsPOSSalesFlashReportsBean();
				obj.setStrField1(tmplistSalesReport.get(i).getStrField1().substring(0, 5));
				obj.setStrField2(tmplistSalesReport.get(i).getStrField2());
				obj.setStrField3(tmplistSalesReport.get(i).getStrField3());

				double saleAmt = Double.parseDouble(tmplistSalesReport.get(i).getStrField3());
				double salePer = (saleAmt / SalesAmount) * 100;
				obj.setStrField4(String.valueOf(decimalFormat.format(salePer)));
				listSalesReport.add(obj);
			}
			System.out.print("@controller " + listSalesReport.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listSalesReport;
	}

	/* load Area wise data */
	@RequestMapping(value = "/loadAreaWiseSalesReport", method = RequestMethod.POST)
	public @ResponseBody List<clsPOSSalesFlashReportsBean> funLoadAreaWiseSalesReport(@RequestParam("POSName") String strPOSName, @RequestParam("FromDate") String FromDateTime, @RequestParam("ToDate") String ToDateTime, @RequestParam("Operator") String strOperator, @RequestParam("PayMode") String strPayMode, @RequestParam("txtFromBillNo") String strFromBill,
			@RequestParam("txtToBillNo") String strToBill, @RequestParam("txtReportType") String reportType, @RequestParam("txtType") String Type, @RequestParam("txtCustomer") String Customer, @RequestParam("chkConsolidatePOS") String ConsolidatePOS, @RequestParam("hidReportName") String ReportName, HttpServletResponse resp, HttpServletRequest req) {
		listSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String LoginPOSCode = req.getSession().getAttribute("loginPOS").toString();

		JSONObject jObjFillter = new JSONObject();
		jObjFillter = funGetJSONData(strPOSName, FromDateTime, ToDateTime, strOperator, strPayMode, strFromBill, strToBill, reportType, Type, Customer, ConsolidatePOS, ReportName, userCode, LoginPOSCode);

		try {

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesReport", jObjFillter);

			clsPOSSalesFlashReportsBean objBean = new clsPOSSalesFlashReportsBean();
			// objBean.setTotalSubTotal(jObj.get("TotalSale").toString());
			double SalesAmount = 0;

			SalesAmount = Double.parseDouble(jObj.get("SalesAmt").toString());

			objBean.setTotalAmount(String.valueOf(decimalFormat.format(SalesAmount)));
			listSalesReport.add(objBean);

			List<clsPOSSalesFlashReportsBean> tmplistSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<clsPOSSalesFlashReportsBean>>() {
			}.getType();
			tmplistSalesReport = gson.fromJson(jObj.get("ListAreaWiseSales").toString(), listType);
			clsPOSSalesFlashReportsBean obj;
			for (int i = 0; i < tmplistSalesReport.size(); i++) {
				obj = new clsPOSSalesFlashReportsBean();
				obj.setStrField1(tmplistSalesReport.get(i).getStrField1().substring(0, 5));
				obj.setStrField2(tmplistSalesReport.get(i).getStrField2());
				obj.setStrField3(tmplistSalesReport.get(i).getStrField3());

				listSalesReport.add(obj);
			}
			System.out.print("@controller " + listSalesReport.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listSalesReport;
	}

	/* load Day wise data */
	@RequestMapping(value = "/loadDayWiseSalesReport", method = RequestMethod.POST)
	public @ResponseBody List<clsPOSSalesFlashReportsBean> funLoadDayWiseSalesReport(@RequestParam("POSName") String strPOSName, @RequestParam("FromDate") String FromDateTime, @RequestParam("ToDate") String ToDateTime, @RequestParam("Operator") String strOperator, @RequestParam("PayMode") String strPayMode, @RequestParam("txtFromBillNo") String strFromBill,
			@RequestParam("txtToBillNo") String strToBill, @RequestParam("txtReportType") String reportType, @RequestParam("txtType") String Type, @RequestParam("txtCustomer") String Customer, @RequestParam("chkConsolidatePOS") String ConsolidatePOS, @RequestParam("hidReportName") String ReportName, HttpServletResponse resp, HttpServletRequest req) {
		listSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String LoginPOSCode = req.getSession().getAttribute("loginPOS").toString();

		JSONObject jObjFillter = new JSONObject();
		jObjFillter = funGetJSONData(strPOSName, FromDateTime, ToDateTime, strOperator, strPayMode, strFromBill, strToBill, reportType, Type, Customer, ConsolidatePOS, ReportName, userCode, LoginPOSCode);

		try {

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesReport", jObjFillter);

			clsPOSSalesFlashReportsBean objBean = new clsPOSSalesFlashReportsBean();
			// objBean.setTotalSubTotal(jObj.get("TotalSale").toString());

			double SalesAmount = 0;
			double totalDiscount = 0, totalSubTotalDWise = 0, totalAmount = 0, totalTaxAmt = 0;
			int totalNoOfBills = 0;
			totalNoOfBills = Integer.parseInt(jObj.get("totalNoOfBills").toString());
			totalAmount = Double.parseDouble(jObj.get("totAmount").toString());
			totalSubTotalDWise = Double.parseDouble(jObj.get("totalSubTotal").toString());
			totalDiscount = Double.parseDouble(jObj.get("totalDiscount").toString());
			totalTaxAmt = Double.parseDouble(jObj.get("totalTaxAmt").toString());

			objBean.setTotalQuantity(String.valueOf(totalNoOfBills));// totalNoOfBills
			objBean.setTotalAmount(String.valueOf(decimalFormat.format(totalAmount)));
			objBean.setTotalSubTotal(String.valueOf(decimalFormat.format(totalSubTotalDWise)));
			objBean.setTotalDiscAmt(String.valueOf(decimalFormat.format(totalDiscount)));
			objBean.setTotalTaxAmt(String.valueOf(decimalFormat.format(totalTaxAmt)));

			listSalesReport.add(objBean);

			List<clsPOSSalesFlashReportsBean> tmplistSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<clsPOSSalesFlashReportsBean>>() {
			}.getType();
			tmplistSalesReport = gson.fromJson(jObj.get("ListDayWiseSales").toString(), listType);
			clsPOSSalesFlashReportsBean obj;
			for (int i = 0; i < tmplistSalesReport.size(); i++) {
				obj = new clsPOSSalesFlashReportsBean();
				obj.setStrField1(tmplistSalesReport.get(i).getStrField1());
				obj.setStrField2(tmplistSalesReport.get(i).getStrField2());
				obj.setStrField3(tmplistSalesReport.get(i).getStrField3());
				obj.setStrField4(tmplistSalesReport.get(i).getStrField4());
				obj.setStrField5(tmplistSalesReport.get(i).getStrField5());
				obj.setStrField6(tmplistSalesReport.get(i).getStrField6());

				listSalesReport.add(obj);
			}
			System.out.print("@controller " + listSalesReport.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listSalesReport;
	}

	/* load Tax wise data */
	@RequestMapping(value = "/loadTaxWiseSalesReport", method = RequestMethod.POST)
	public @ResponseBody List<clsPOSSalesFlashReportsBean> funLoadTaxWiseSalesReport(@RequestParam("POSName") String strPOSName, @RequestParam("FromDate") String FromDateTime, @RequestParam("ToDate") String ToDateTime, @RequestParam("Operator") String strOperator, @RequestParam("PayMode") String strPayMode, @RequestParam("txtFromBillNo") String strFromBill,
			@RequestParam("txtToBillNo") String strToBill, @RequestParam("txtReportType") String reportType, @RequestParam("txtType") String Type, @RequestParam("txtCustomer") String Customer, @RequestParam("chkConsolidatePOS") String ConsolidatePOS, @RequestParam("hidReportName") String ReportName, HttpServletResponse resp, HttpServletRequest req) {
		listSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String LoginPOSCode = req.getSession().getAttribute("loginPOS").toString();

		JSONObject jObjFillter = new JSONObject();
		jObjFillter = funGetJSONData(strPOSName, FromDateTime, ToDateTime, strOperator, strPayMode, strFromBill, strToBill, reportType, Type, Customer, ConsolidatePOS, ReportName, userCode, LoginPOSCode);

		try {

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesReport", jObjFillter);

			clsPOSSalesFlashReportsBean objBean = new clsPOSSalesFlashReportsBean();
			// objBean.setTotalSubTotal(jObj.get("TotalSale").toString());

			double totalTax = 0, totalTaxableAmt = 0;

			totalTax = Double.parseDouble(jObj.get("totalTax").toString());
			totalTaxableAmt = Double.parseDouble(jObj.get("totalTaxableAmt").toString());

			objBean.setTotalSubTotal(String.valueOf(decimalFormat.format(totalTax)));
			objBean.setTotalTaxAmt(String.valueOf(decimalFormat.format(totalTaxableAmt)));

			listSalesReport.add(objBean);

			List<clsPOSSalesFlashReportsBean> tmplistSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<clsPOSSalesFlashReportsBean>>() {
			}.getType();
			tmplistSalesReport = gson.fromJson(jObj.get("ListTaxWiseSales").toString(), listType);
			clsPOSSalesFlashReportsBean obj;

			for (int i = 0; i < tmplistSalesReport.size(); i++) {
				obj = new clsPOSSalesFlashReportsBean();
				obj.setStrField1(tmplistSalesReport.get(i).getStrField1());
				obj.setStrField2(tmplistSalesReport.get(i).getStrField2());
				obj.setStrField3(tmplistSalesReport.get(i).getStrField3());
				obj.setStrField4(tmplistSalesReport.get(i).getStrField4());
				obj.setStrField5(tmplistSalesReport.get(i).getStrField5());
				obj.setStrField6(tmplistSalesReport.get(i).getStrField6());
				obj.setStrField7(tmplistSalesReport.get(i).getStrField7());

				listSalesReport.add(obj);
			}
			System.out.print("@controller " + listSalesReport.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listSalesReport;
	}

	/* load Tip wise data */
	@RequestMapping(value = "/loadTipWiseSalesReport", method = RequestMethod.POST)
	public @ResponseBody List<clsPOSSalesFlashReportsBean> funLoadTipSalesReport(@RequestParam("POSName") String strPOSName, @RequestParam("FromDate") String FromDateTime, @RequestParam("ToDate") String ToDateTime, @RequestParam("Operator") String strOperator, @RequestParam("PayMode") String strPayMode, @RequestParam("txtFromBillNo") String strFromBill,
			@RequestParam("txtToBillNo") String strToBill, @RequestParam("txtReportType") String reportType, @RequestParam("txtType") String Type, @RequestParam("txtCustomer") String Customer, @RequestParam("chkConsolidatePOS") String ConsolidatePOS, @RequestParam("hidReportName") String ReportName, HttpServletResponse resp, HttpServletRequest req) {
		listSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String LoginPOSCode = req.getSession().getAttribute("loginPOS").toString();

		JSONObject jObjFillter = new JSONObject();
		jObjFillter = funGetJSONData(strPOSName, FromDateTime, ToDateTime, strOperator, strPayMode, strFromBill, strToBill, reportType, Type, Customer, ConsolidatePOS, ReportName, userCode, LoginPOSCode);

		try {

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesReport", jObjFillter);

			clsPOSSalesFlashReportsBean objBean = new clsPOSSalesFlashReportsBean();
			// objBean.setTotalSubTotal(jObj.get("TotalSale").toString());

			double SalesAmount = 0;
			double totalDiscount = 0, totalSubTotal = 0, totalTaxAmt = 0, totalTipAmt = 0;
			SalesAmount = Double.parseDouble(jObj.get("SalesAmount").toString());
			totalSubTotal = Double.parseDouble(jObj.get("subTotal").toString());
			totalDiscount = Double.parseDouble(jObj.get("Disc").toString());
			totalTaxAmt = Double.parseDouble(jObj.get("totalTaxAmt").toString());
			totalTipAmt = Double.parseDouble(jObj.get("tipAmountTotal").toString());

			objBean.setTotalDiscAmt(String.valueOf(decimalFormat.format(totalDiscount)));
			objBean.setTotalSubTotal(String.valueOf(decimalFormat.format(totalSubTotal)));
			objBean.setTotalTaxAmt(String.valueOf(decimalFormat.format(totalTaxAmt)));
			objBean.setTotalTipAmt(String.valueOf(decimalFormat.format(totalTipAmt)));
			objBean.setTotalAmount(String.valueOf(decimalFormat.format(totalTipAmt)));

			listSalesReport.add(objBean);

			List<clsPOSSalesFlashReportsBean> tmplistSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<clsPOSSalesFlashReportsBean>>() {
			}.getType();
			tmplistSalesReport = gson.fromJson(jObj.get("ListTipWiseSales").toString(), listType);
			clsPOSSalesFlashReportsBean obj;
			for (int i = 0; i < tmplistSalesReport.size(); i++) {
				obj = new clsPOSSalesFlashReportsBean();
				obj.setStrField1(tmplistSalesReport.get(i).getStrField1());
				obj.setStrField2(tmplistSalesReport.get(i).getStrField2());
				obj.setStrField3(tmplistSalesReport.get(i).getStrField3());
				obj.setStrField4(tmplistSalesReport.get(i).getStrField4());
				obj.setStrField5(tmplistSalesReport.get(i).getStrField5());
				obj.setStrField6(tmplistSalesReport.get(i).getStrField6());
				obj.setStrField7(tmplistSalesReport.get(i).getStrField7());
				obj.setStrField8(tmplistSalesReport.get(i).getStrField8());
				obj.setStrField9(tmplistSalesReport.get(i).getStrField9());
				obj.setStrField10(tmplistSalesReport.get(i).getStrField10());
				obj.setStrField11(tmplistSalesReport.get(i).getStrField11());
				listSalesReport.add(obj);
			}
			System.out.print("@controller " + listSalesReport.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listSalesReport;
	}

	/* load Item Modifier wise data */
	@RequestMapping(value = "/loadItemModifierWiseSalesReport", method = RequestMethod.POST)
	public @ResponseBody List<clsPOSSalesFlashReportsBean> funLoadItemModifierWiseSalesReport(@RequestParam("POSName") String strPOSName, @RequestParam("FromDate") String FromDateTime, @RequestParam("ToDate") String ToDateTime, @RequestParam("Operator") String strOperator, @RequestParam("PayMode") String strPayMode, @RequestParam("txtFromBillNo") String strFromBill,
			@RequestParam("txtToBillNo") String strToBill, @RequestParam("txtReportType") String reportType, @RequestParam("txtType") String Type, @RequestParam("txtCustomer") String Customer, @RequestParam("chkConsolidatePOS") String ConsolidatePOS, @RequestParam("hidReportName") String ReportName, HttpServletResponse resp, HttpServletRequest req) {
		listSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String LoginPOSCode = req.getSession().getAttribute("loginPOS").toString();

		JSONObject jObjFillter = new JSONObject();
		jObjFillter = funGetJSONData(strPOSName, FromDateTime, ToDateTime, strOperator, strPayMode, strFromBill, strToBill, reportType, Type, Customer, ConsolidatePOS, ReportName, userCode, LoginPOSCode);

		try {

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesReport", jObjFillter);

			clsPOSSalesFlashReportsBean objBean = new clsPOSSalesFlashReportsBean();
			// objBean.setTotalSubTotal(jObj.get("TotalSale").toString());

			double SalesAmount = 0;
			// double totalAmount = 0;
			double totalQty = 0;
			totalQty = Double.parseDouble(jObj.get("totalQty").toString());
			SalesAmount = Double.parseDouble(jObj.get("totalAmount").toString());
			objBean.setTotalQuantity(String.valueOf(totalQty));// totalNoOfBills
			objBean.setTotalAmount(String.valueOf(decimalFormat.format(SalesAmount)));
			listSalesReport.add(objBean);

			List<clsPOSSalesFlashReportsBean> tmplistSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<clsPOSSalesFlashReportsBean>>() {
			}.getType();
			tmplistSalesReport = gson.fromJson(jObj.get("ListModWiseSales").toString(), listType);
			clsPOSSalesFlashReportsBean obj;
			for (int i = 0; i < tmplistSalesReport.size(); i++) {
				obj = new clsPOSSalesFlashReportsBean();
				obj.setStrField1(tmplistSalesReport.get(i).getStrField1());
				obj.setStrField2(tmplistSalesReport.get(i).getStrField2());
				obj.setStrField3(tmplistSalesReport.get(i).getStrField3());
				obj.setStrField4(tmplistSalesReport.get(i).getStrField4());

				listSalesReport.add(obj);
			}
			System.out.print("@controller " + listSalesReport.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listSalesReport;
	}

	/* load Menu head wise with Modifier wise data */
	@RequestMapping(value = "/loadMenuHeadWiseWithModSalesReport", method = RequestMethod.POST)
	public @ResponseBody List<clsPOSSalesFlashReportsBean> funLoadMenuHeadWiseWithModifierSalesReport(@RequestParam("POSName") String strPOSName, @RequestParam("FromDate") String FromDateTime, @RequestParam("ToDate") String ToDateTime, @RequestParam("Operator") String strOperator, @RequestParam("PayMode") String strPayMode, @RequestParam("txtFromBillNo") String strFromBill,
			@RequestParam("txtToBillNo") String strToBill, @RequestParam("txtReportType") String reportType, @RequestParam("txtType") String Type, @RequestParam("txtCustomer") String Customer, @RequestParam("chkConsolidatePOS") String ConsolidatePOS, @RequestParam("hidReportName") String ReportName, HttpServletResponse resp, HttpServletRequest req) {
		listSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String LoginPOSCode = req.getSession().getAttribute("loginPOS").toString();

		JSONObject jObjFillter = new JSONObject();
		jObjFillter = funGetJSONData(strPOSName, FromDateTime, ToDateTime, strOperator, strPayMode, strFromBill, strToBill, reportType, Type, Customer, ConsolidatePOS, ReportName, userCode, LoginPOSCode);

		try {

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesReport", jObjFillter);

			clsPOSSalesFlashReportsBean objBean = new clsPOSSalesFlashReportsBean();
			// objBean.setTotalSubTotal(jObj.get("TotalSale").toString());

			double SalesAmount = 0;
			// double totalAmount = 0;
			double totalQty = 0;
			totalQty = Double.parseDouble(jObj.get("totalQty").toString());
			SalesAmount = Double.parseDouble(jObj.get("totalAmount").toString());
			objBean.setTotalQuantity(String.valueOf(totalQty));// totalNoOfBills
			objBean.setTotalAmount(String.valueOf(decimalFormat.format(SalesAmount)));
			listSalesReport.add(objBean);

			List<clsPOSSalesFlashReportsBean> tmplistSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<clsPOSSalesFlashReportsBean>>() {
			}.getType();
			tmplistSalesReport = gson.fromJson(jObj.get("ListMenuHeadModWiseSales").toString(), listType);
			clsPOSSalesFlashReportsBean obj;
			for (int i = 0; i < tmplistSalesReport.size(); i++) {
				obj = new clsPOSSalesFlashReportsBean();
				obj.setStrField1(tmplistSalesReport.get(i).getStrField1());
				obj.setStrField2(tmplistSalesReport.get(i).getStrField2());
				obj.setStrField3(tmplistSalesReport.get(i).getStrField3());
				obj.setStrField4(tmplistSalesReport.get(i).getStrField4());

				listSalesReport.add(obj);
			}
			System.out.print("@controller " + listSalesReport.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listSalesReport;
	}

	/* loadItem Hourly wise with Modifier wise data */
	@RequestMapping(value = "/loadItemHourlyWiseSalesReport", method = RequestMethod.POST)
	public @ResponseBody List<clsPOSSalesFlashReportsBean> funLoadItemHourlyWiseSalesReport(@RequestParam("POSName") String strPOSName, @RequestParam("FromDate") String FromDateTime, @RequestParam("ToDate") String ToDateTime, @RequestParam("Operator") String strOperator, @RequestParam("PayMode") String strPayMode, @RequestParam("txtFromBillNo") String strFromBill,
			@RequestParam("txtToBillNo") String strToBill, @RequestParam("txtReportType") String reportType, @RequestParam("txtType") String Type, @RequestParam("txtCustomer") String Customer, @RequestParam("chkConsolidatePOS") String ConsolidatePOS, @RequestParam("hidReportName") String ReportName, HttpServletResponse resp, HttpServletRequest req) {
		listSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String LoginPOSCode = req.getSession().getAttribute("loginPOS").toString();

		JSONObject jObjFillter = new JSONObject();
		jObjFillter = funGetJSONData(strPOSName, FromDateTime, ToDateTime, strOperator, strPayMode, strFromBill, strToBill, reportType, Type, Customer, ConsolidatePOS, ReportName, userCode, LoginPOSCode);

		try {

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesReport", jObjFillter);

			clsPOSSalesFlashReportsBean objBean = new clsPOSSalesFlashReportsBean();
			// objBean.setTotalSubTotal(jObj.get("TotalSale").toString());

			double SalesAmount = 0;
			// double totalAmount = 0;
			double totalDisc = 0;
			totalDisc = Double.parseDouble(jObj.get("totalDisc").toString());
			SalesAmount = Double.parseDouble(jObj.get("totalAmount").toString());
			objBean.setTotalDiscAmt(String.valueOf(totalDisc));
			objBean.setTotalAmount(String.valueOf(decimalFormat.format(SalesAmount)));
			listSalesReport.add(objBean);

			List<clsPOSSalesFlashReportsBean> tmplistSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<clsPOSSalesFlashReportsBean>>() {
			}.getType();
			tmplistSalesReport = gson.fromJson(jObj.get("ListItemHourlyWiseSales").toString(), listType);
			clsPOSSalesFlashReportsBean obj;
			for (int i = 0; i < tmplistSalesReport.size(); i++) {
				obj = new clsPOSSalesFlashReportsBean();
				obj.setStrField1(tmplistSalesReport.get(i).getStrField1());
				obj.setStrField2(tmplistSalesReport.get(i).getStrField2());
				obj.setStrField3(tmplistSalesReport.get(i).getStrField3());
				obj.setStrField4(tmplistSalesReport.get(i).getStrField4());
				obj.setStrField5(tmplistSalesReport.get(i).getStrField5());
				listSalesReport.add(obj);
			}
			System.out.print("@controller " + listSalesReport.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listSalesReport;
	}

	/* load Operator wise data */
	@RequestMapping(value = "/loadOperstorWiseSalesReport", method = RequestMethod.POST)
	public @ResponseBody List<clsPOSSalesFlashReportsBean> funLoadOperatorSalesReport(@RequestParam("POSName") String strPOSName, @RequestParam("FromDate") String FromDateTime, @RequestParam("ToDate") String ToDateTime, @RequestParam("Operator") String strOperator, @RequestParam("PayMode") String strPayMode, @RequestParam("txtFromBillNo") String strFromBill,
			@RequestParam("txtToBillNo") String strToBill, @RequestParam("txtReportType") String reportType, @RequestParam("txtType") String Type, @RequestParam("txtCustomer") String Customer, @RequestParam("chkConsolidatePOS") String ConsolidatePOS, @RequestParam("hidReportName") String ReportName, HttpServletResponse resp, HttpServletRequest req) {
		listSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String LoginPOSCode = req.getSession().getAttribute("loginPOS").toString();

		JSONObject jObjFillter = new JSONObject();
		jObjFillter = funGetJSONData(strPOSName, FromDateTime, ToDateTime, strOperator, strPayMode, strFromBill, strToBill, reportType, Type, Customer, ConsolidatePOS, ReportName, userCode, LoginPOSCode);

		try {

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesReport", jObjFillter);

			clsPOSSalesFlashReportsBean objBean = new clsPOSSalesFlashReportsBean();
			// objBean.setTotalSubTotal(jObj.get("TotalSale").toString());

			double SalesAmount = 0;
			// double totalAmount = 0;
			double totalDisc = 0;
			totalDisc = Double.parseDouble(jObj.get("totalDisc").toString());
			SalesAmount = Double.parseDouble(jObj.get("totalAmount").toString());
			objBean.setTotalDiscAmt(String.valueOf(totalDisc));
			objBean.setTotalAmount(String.valueOf(decimalFormat.format(SalesAmount)));
			listSalesReport.add(objBean);

			List<clsPOSSalesFlashReportsBean> tmplistSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<clsPOSSalesFlashReportsBean>>() {
			}.getType();
			tmplistSalesReport = gson.fromJson(jObj.get("ListOperatorWiseSales").toString(), listType);
			clsPOSSalesFlashReportsBean obj;
			for (int i = 0; i < tmplistSalesReport.size(); i++) {
				obj = new clsPOSSalesFlashReportsBean();
				obj.setStrField1(tmplistSalesReport.get(i).getStrField1());
				obj.setStrField2(tmplistSalesReport.get(i).getStrField2());
				obj.setStrField3(tmplistSalesReport.get(i).getStrField3());
				obj.setStrField4(tmplistSalesReport.get(i).getStrField4());
				obj.setStrField5(tmplistSalesReport.get(i).getStrField5());
				obj.setStrField6(tmplistSalesReport.get(i).getStrField6());
				listSalesReport.add(obj);
			}
			System.out.print("@controller " + listSalesReport.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listSalesReport;
	}

	/* load Monthly wise data */
	@RequestMapping(value = "/loadMonthlyWiseSalesReport", method = RequestMethod.POST)
	public @ResponseBody List<clsPOSSalesFlashReportsBean> funLoadMonthlySalesReport(@RequestParam("POSName") String strPOSName, @RequestParam("FromDate") String FromDateTime, @RequestParam("ToDate") String ToDateTime, @RequestParam("Operator") String strOperator, @RequestParam("PayMode") String strPayMode, @RequestParam("txtFromBillNo") String strFromBill,
			@RequestParam("txtToBillNo") String strToBill, @RequestParam("txtReportType") String reportType, @RequestParam("txtType") String Type, @RequestParam("txtCustomer") String Customer, @RequestParam("chkConsolidatePOS") String ConsolidatePOS, @RequestParam("hidReportName") String ReportName, HttpServletResponse resp, HttpServletRequest req) {
		listSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String LoginPOSCode = req.getSession().getAttribute("loginPOS").toString();

		JSONObject jObjFillter = new JSONObject();
		jObjFillter = funGetJSONData(strPOSName, FromDateTime, ToDateTime, strOperator, strPayMode, strFromBill, strToBill, reportType, Type, Customer, ConsolidatePOS, ReportName, userCode, LoginPOSCode);

		try {

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesReport", jObjFillter);

			clsPOSSalesFlashReportsBean objBean = new clsPOSSalesFlashReportsBean();
			// objBean.setTotalSubTotal(jObj.get("TotalSale").toString());

			double SalesAmount = 0;
			SalesAmount = Double.parseDouble(jObj.get("totalSale").toString());
			objBean.setTotalAmount(String.valueOf(decimalFormat.format(SalesAmount)));
			listSalesReport.add(objBean);

			List<clsPOSSalesFlashReportsBean> tmplistSalesReport = new ArrayList<clsPOSSalesFlashReportsBean>();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<clsPOSSalesFlashReportsBean>>() {
			}.getType();
			tmplistSalesReport = gson.fromJson(jObj.get("ListMonthlySales").toString(), listType);
			clsPOSSalesFlashReportsBean obj;
			for (int i = 0; i < tmplistSalesReport.size(); i++) {
				obj = new clsPOSSalesFlashReportsBean();
				obj.setStrField1(tmplistSalesReport.get(i).getStrField1());
				obj.setStrField2(tmplistSalesReport.get(i).getStrField2());
				obj.setStrField3(tmplistSalesReport.get(i).getStrField3());
				listSalesReport.add(obj);
			}
			System.out.print("@controller " + listSalesReport.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listSalesReport;
	}

	/* return json data used to send webservice */
	public JSONObject funGetJSONData(String strPOSName, String FromDateTime, String ToDateTime, String strOperator, String strPayMode, String strFromBill, String strToBill, String reportType, String Type, String Customer, String ConsolidatePOS, String ReportName, String userCode, String LoginPOSCode) {
		JSONObject jObjFillter = new JSONObject();
		try {

			String FromDate = "", FromTime = "", ToDate = "", ToTime = "";

			FromDate = FromDateTime.split(":")[0];
			FromTime = FromDateTime.split(":")[1];
			ToDate = ToDateTime.split(":")[0];
			ToTime = ToDateTime.split(":")[1];

			String strFromdate = FromDate.split("-")[2] + "-" + FromDate.split("-")[1] + "-" + FromDate.split("-")[0];
			String strToDate = ToDate.split("-")[2] + "-" + ToDate.split("-")[1] + "-" + ToDate.split("-")[0];

			String dateFrom = "", field = null, dateTo = "";

			if (funGetFromTime(FromTime) != null) {
				dateFrom = strFromdate + " " + funGetFromTime(FromTime);
				field = "dteBillDate";
			} else {
				dateFrom = strFromdate;
				field = "date(dteBillDate)";
			}
			if (funGetToTime(ToTime) != null) {
				dateTo = strToDate + " " + funGetToTime(ToTime);
			} else {
				dateTo = strToDate;
			}
			if (ConsolidatePOS.equalsIgnoreCase("true")) {
				ConsolidatePOS = "Y";
			} else {
				ConsolidatePOS = "N";
			}
			// ConsolidatePOS=objGlobalFunctions.funIfNull(ConsolidatePOS,"N","Y");
			String posCode = "All";
			String PayMode = "All";

			if (!strPOSName.equalsIgnoreCase("ALL")) {
				posCode = hmPOSData.get(strPOSName);
			}

			if (!strPayMode.equalsIgnoreCase("All")) {
				PayMode = hmPayMode.get(strPayMode);
			}

			jObjFillter.put("strFromdate", dateFrom);
			jObjFillter.put("strToDate", dateTo);
			jObjFillter.put("posCode", posCode);
			jObjFillter.put("LoginPOSCode", LoginPOSCode);
			jObjFillter.put("strShiftNo", "1");
			jObjFillter.put("userCode", userCode);
			jObjFillter.put("field", field);
			jObjFillter.put("strPayMode", PayMode);
			jObjFillter.put("strOperator", strOperator);
			jObjFillter.put("strFromBill", strFromBill);
			jObjFillter.put("strToBill", strToBill);
			jObjFillter.put("reportType", reportType);
			jObjFillter.put("Type", Type);
			jObjFillter.put("Customer", Customer);
			jObjFillter.put("ConsolidatePOS", ConsolidatePOS);
			jObjFillter.put("ReportName", ReportName.substring(3));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jObjFillter;
	}

	private String funGetFromTime(String FromTime) throws Exception {
		String fromTime = null;
		String Hour = FromTime.split("/")[0];
		String Minute = FromTime.split("/")[1];
		String Ampm = FromTime.split("/")[2];
		if (Hour == ("HH") && !(Minute == ("MM"))) {
			SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
			SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
			String Time = Hour + ":" + Minute + " " + Ampm;
			Date date = parseFormat.parse(Time);
			fromTime = displayFormat.format(date);
		}
		return fromTime;
	}

	private String funGetToTime(String ToTime) throws Exception {
		String toTime = null;
		String Hour = ToTime.split("/")[0];
		String Minute = ToTime.split("/")[1];
		String Ampm = ToTime.split("/")[2];
		if (Hour == ("HH") && !(Minute == ("MM"))) {
			SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
			SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
			String Time = Hour + ":" + Minute + " " + Ampm;
			Date date = parseFormat.parse(Time);
			ToTime = displayFormat.format(date);
		}
		return toTime;
	}

	/**
	 * Excel Export
	 * */

	/*
	 * @SuppressWarnings({ "unchecked", "rawtypes" })
	 * 
	 * @RequestMapping(value = "/ExportExcelStkVariance", method =
	 * RequestMethod.GET) protected void buildExcelDocument(Map<String, Object>
	 * model, HSSFWorkbook workbook, HttpServletRequest request,
	 * HttpServletResponse response) throws Exception { objGlobal=new
	 * clsGlobalFunctions();
	 * response.setContentType("application/vnd.ms-excel"); response.setHeader(
	 * "Content-Disposition", "inline;filename=stkVarianceReport.xls");
	 * 
	 * String
	 * clientCode=request.getSession().getAttribute("clientCode").toString();
	 * String reportName=request.getParameter("reportName");
	 * 
	 * String[] spParam1=param1.split(","); String strLocCode=spParam1[0];
	 * String fromDate=objGlobal.funGetDate("yyyy-MM-dd",spParam1[1]); String
	 * toDate=objGlobal.funGetDate("yyyy-MM-dd",spParam1[2]);
	 * 
	 * double value=0;
	 * 
	 * String sql=
	 * "select b.strProdCode,c.strProdName,sum(b.dblCStock),sum(b.dblPStock),sum(b.dblVariance),c.dblCostRM,(c.dblCostRM *sum(b.dblVariance)) as value "
	 * +
	 * " from clsStkPostingHdModel a,clsStkPostingDtlModel b,clsProductMasterModel c"
	 * + " where a.strPSCode=b.strPSCode and b.strProdCode=c.strProdCode " +
	 * " and a.dtPSDate between '"+fromDate+"' and '"+toDate+"' " +
	 * " and a.strClientCode='"
	 * +clientCode+"' and  b.strClientCode='"+clientCode+"' " +
	 * " and c.strClientCode='"+clientCode+"'"; if(strLocCode.trim().length()>0)
	 * { sql=sql+"and a.strLocCode='"+strLocCode+"' "; }
	 * sql=sql+"group by b.strProdCode"; List
	 * list=objGlobalFunctionsService.funGetList(sql, "hql");
	 * 
	 * List listStockFlashModel=new ArrayList();
	 * 
	 * for(int cnt=0;cnt<list.size();cnt++) { Object[]
	 * arrObj=(Object[])list.get(cnt); List DataList=new ArrayList<>();
	 * DataList.add(arrObj[0].toString()); DataList.add(arrObj[1].toString());
	 * DataList.add(Double.parseDouble(arrObj[2].toString()));
	 * DataList.add(Double.parseDouble(arrObj[3].toString()));
	 * DataList.add(Double.parseDouble(arrObj[4].toString()));
	 * DataList.add(Double.parseDouble(arrObj[5].toString()));
	 * DataList.add(Double.parseDouble(arrObj[6].toString()));
	 * value=value+Double.parseDouble(arrObj[6].toString());
	 * listStockFlashModel.add(DataList); }
	 * 
	 * // create a new Excel sheet HSSFSheet sheet =
	 * workbook.createSheet("Sheet"); sheet.setDefaultColumnWidth(20);
	 * 
	 * // create style for header cells CellStyle style =
	 * workbook.createCellStyle(); Font font = workbook.createFont();
	 * font.setFontName("Arial");
	 * style.setFillForegroundColor(HSSFColor.BLUE.index);
	 * style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	 * font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	 * font.setColor(HSSFColor.WHITE.index); style.setFont(font);
	 * 
	 * HSSFRow header = sheet.createRow(0);
	 * header.createCell(0).setCellValue("Product Code");
	 * header.getCell(0).setCellStyle(style);
	 * header.createCell(1).setCellValue("Product Name");
	 * header.getCell(1).setCellStyle(style);
	 * header.createCell(2).setCellValue("Computer Stk");
	 * header.getCell(2).setCellStyle(style);
	 * header.createCell(3).setCellValue("Phy Stk");
	 * header.getCell(3).setCellStyle(style);
	 * header.createCell(4).setCellValue("Variance");
	 * header.getCell(4).setCellStyle(style);
	 * header.createCell(5).setCellValue("Unit Price");
	 * header.getCell(5).setCellStyle(style);
	 * header.createCell(6).setCellValue("Value");
	 * header.getCell(6).setCellStyle(style);
	 * 
	 * // create style for Data cells CellStyle Datastyle =
	 * workbook.createCellStyle();
	 * Datastyle.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
	 * // create header row
	 * 
	 * 
	 * // create data rows int ColrowCount = 1 ; for(int
	 * rowCount=0;rowCount<listStockFlashModel.size();rowCount++) { HSSFRow aRow
	 * = sheet.createRow(ColrowCount++); List arrObj=(List)
	 * listStockFlashModel.get(rowCount); for(int
	 * Count=0;Count<arrObj.size();Count++) {
	 * if(arrObj.get(Count).toString().length()>0) {
	 * if(isNumeric(arrObj.get(Count).toString())) {
	 * aRow.createCell(Count).setCellValue
	 * (Double.parseDouble(arrObj.get(Count).toString()));
	 * aRow.getCell(Count).setCellStyle(Datastyle); } else {
	 * aRow.createCell(Count).setCellValue(arrObj.get(Count).toString()); } }
	 * else { aRow.createCell(Count).setCellValue(""); } }
	 * 
	 * }
	 * 
	 * // create style for Footer cells CellStyle sellStyle =
	 * workbook.createCellStyle(); Font cellfont = workbook.createFont();
	 * cellfont.setFontName("Arial"); cellfont.setColor(HSSFColor.BLACK.index);
	 * cellfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	 * sellStyle.setFont(cellfont); HSSFRow aRow1 =
	 * sheet.createRow(ColrowCount+1);
	 * aRow1.createCell(5).setCellValue("Total Variance Value");
	 * aRow1.getCell(5).setCellStyle(sellStyle);
	 * aRow1.createCell(6).setCellValue("value");
	 * aRow1.getCell(6).setCellStyle(sellStyle);
	 * 
	 * workbook.write(response.getOutputStream());
	 * 
	 * }
	 */

}
