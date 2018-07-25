package com.sanguine.webpos.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsCustomerHistoryFlashBean;

@Controller
public class clsCustomerHistoryFlashController {
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	Map map = new HashMap();

	@RequestMapping(value = "/frmPOSCustomerHistoryFlash", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		String posCode = request.getSession().getAttribute("loginPOS").toString();
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List<Object> posList = new ArrayList<Object>();
		posList.add("ALL");

		JSONArray jArrList = new JSONArray();
		jArrList = objPOSGlobal.funGetAllPOSForMaster(clientCode);
		for (int i = 0; i < jArrList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArrList.get(i);
			posList.add(josnObjRet.get("strPosName"));
			map.put(josnObjRet.get("strPosName"), josnObjRet.get("strPosCode"));
		}

		model.put("posList", posList);
		String posUrL = "http://localhost:8080/prjSanguineWebService/APOSIntegration/funGetPOSDate" + "?POSCode=" + posCode;
		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(posUrL);

		String posDate = (String) jObj.get("POSDate");
		String[] output = posDate.split(" ");
		String POSDate = output[0];
		model.put("posDate", POSDate);

		Map mapAmount = new HashMap<>();

		mapAmount.put("<=", "<=");
		mapAmount.put(">=", ">=");
		mapAmount.put("=", "=");
		model.put("mapAmount", mapAmount);

		Map mapReportType = new HashMap<>();

		mapReportType.put("Bill Wise", "Bill Wise");
		mapReportType.put("Item Wise", "Item Wise");

		model.put("mapReportType", mapReportType);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSCustomerHistoryFlash_1", "command", new clsCustomerHistoryFlashBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSCustomerHistoryFlash", "command", new clsCustomerHistoryFlashBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/loadFunFillAllTables", method = RequestMethod.POST)
	public @ResponseBody JSONObject loadFunFillAllTables(HttpServletRequest req) {
		List listmain = new ArrayList();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String posName = req.getParameter("posName");
		String reportType = req.getParameter("reportType");
		String fromDate = req.getParameter("fromDate");
		String toDate = req.getParameter("toDate");
		String selectedTab = req.getParameter("selectedTab");
		String custCode = "", custName = "", cmbAmount = "", txtAmount = "";
		String posCode = "";

		if (posName.equalsIgnoreCase("ALL")) {
			posCode = "All";
		} else {
			if (map.containsKey(posName)) {
				posCode = (String) map.get(posName);

			}
		}

		if (selectedTab.equalsIgnoreCase("Customer Wise")) {
			custCode = req.getParameter("custCode");
			custName = req.getParameter("custName");
		}
		if (selectedTab.equalsIgnoreCase("Top Spenders")) {
			cmbAmount = req.getParameter("custCode");
			txtAmount = req.getParameter("custName");
		}
		String webStockUserCode = req.getSession().getAttribute("usercode").toString();

		JSONObject jObjCustomerHistoryFlash = new JSONObject();
		jObjCustomerHistoryFlash.put("posCode", posCode);
		jObjCustomerHistoryFlash.put("reportType", reportType);
		jObjCustomerHistoryFlash.put("selectedTab", selectedTab);
		jObjCustomerHistoryFlash.put("fromDate", fromDate);
		jObjCustomerHistoryFlash.put("toDate", toDate);
		if (selectedTab.equalsIgnoreCase("Customer Wise")) {
			jObjCustomerHistoryFlash.put("custCode", custCode);
		}
		if (selectedTab.equalsIgnoreCase("Top Spenders")) {
			jObjCustomerHistoryFlash.put("cmbAmount", cmbAmount);
			jObjCustomerHistoryFlash.put("txtAmount", txtAmount);
		}
		jObjCustomerHistoryFlash.put("webStockUserCode", webStockUserCode);

		JSONObject jObj;
		JSONObject jObjAllTableData = new JSONObject();
		JSONArray jArrForAllTbl = null;
		JSONArray jArrForAllTbl1 = null;
		jObj = objGlobal.funPOSTMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/WebPOSTransactions/funFillAllTables", jObjCustomerHistoryFlash);

		// String posUrl =
		// "http://localhost:8080/prjSanguineWebService/WebPOSTransactions/FunFillAllTables"
		// +
		// "?posCode="+posCode+"&reportType="+reportType+"&selectedTab="+selectedTab+"&fromDate="+fromDate+"&toDate="+toDate+"&custCode="+custCode+"&webStockUserCode="+webStockUserCode;
		//
		// jObjAllTableData =objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		JSONObject jObjTblDataDtl = new JSONObject();
		String strTabName = (String) jObj.get("tabName");
		String strCmbName = (String) jObj.get("cmbName");
		if (strTabName.equalsIgnoreCase("Customer Wise")) {
			if (strCmbName.equalsIgnoreCase("Bill Wise")) {
				jArrForAllTbl = (JSONArray) jObj.get("CustomerWiseTblData");
				jArrForAllTbl1 = (JSONArray) jObj.get("TotalTblData");

				jObjTblDataDtl.put("CustomerWiseTblData", jArrForAllTbl);
				jObjTblDataDtl.put("TotalTblData", jArrForAllTbl1);
			} else {
				jArrForAllTbl = (JSONArray) jObj.get("CustomerWiseTblData");
				jArrForAllTbl1 = (JSONArray) jObj.get("TotalTblData");

				jObjTblDataDtl.put("CustomerWiseTblData", jArrForAllTbl);
				jObjTblDataDtl.put("TotalTblData", jArrForAllTbl1);
			}
			jObjTblDataDtl.put("strCmbName", strCmbName);

		}
		if (strTabName.equalsIgnoreCase("Top Spenders")) {
			jArrForAllTbl = (JSONArray) jObj.get("TopSpendersTblData");
			jArrForAllTbl1 = (JSONArray) jObj.get("TotalTblData");

			jObjTblDataDtl.put("TopSpendersTblData", jArrForAllTbl);
			jObjTblDataDtl.put("TotalTblData", jArrForAllTbl1);
		}
		if (strTabName.equalsIgnoreCase("Non Spenders")) {
			jArrForAllTbl = (JSONArray) jObj.get("NonSpendersTblData");

			jObjTblDataDtl.put("NonSpendersTblData", jArrForAllTbl);
		}
		jObjTblDataDtl.put("strTabName", strTabName);
		return jObjTblDataDtl;

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptPOSCustomerHistoryFlash", method = RequestMethod.POST)
	private ModelAndView funReport(@ModelAttribute("command") clsCustomerHistoryFlashBean objBean, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String posName = objBean.getStrPOSName();

		String fromDate = objBean.getDteFromDate();

		String toDate = objBean.getDteToDate();
		String reportType = "";

		String custCode = objBean.getStrCustCode();
		String custName = objBean.getStrCustName();

		String selectedTab = objBean.getStrTabVal();

		String cmbAmount = "", txtAmount = "";
		String webStockUserCode = req.getSession().getAttribute("usercode").toString();
		if (selectedTab.equalsIgnoreCase("tab2")) {
			selectedTab = "Top Spenders";
			reportType = objBean.getStrAmount();
			cmbAmount = objBean.getStrAmount();
			txtAmount = objBean.getStrAmt();
		} else {
			selectedTab = "Non Spenders";
			reportType = "Non Spenders";
			cmbAmount = "";
			txtAmount = "";
		}

		String FileName = "";
		Map resMap = new LinkedHashMap();

		resMap = funGetData(fromDate, toDate, posName, selectedTab, webStockUserCode, cmbAmount, txtAmount, reportType);

		List ExportList = new ArrayList();

		String dteFromDate = objBean.getDteFromDate();
		String dteToDate = objBean.getDteToDate();
		String reportName = "";
		if (selectedTab.equalsIgnoreCase("Top Spenders")) {
			FileName = "TopSpenderWiseSales_" + dteFromDate + "_To_" + dteToDate;
			reportName = "Top Spender Wise Sales";
		} else {
			FileName = "NonSpenderWiseSales_" + dteFromDate + "_To_" + dteToDate;
			reportName = "Non Spender Wise Sales";
		}
		ExportList.add(FileName);
		long rowCount = (long) resMap.get("RowCount");
		long colCount = (long) resMap.get("ColCount");
		List<String> list = new ArrayList<String>();
		list = (List) resMap.get("Header");
		List rowlist = new ArrayList();

		List<String> listTitelName = new ArrayList<String>();

		listTitelName.add("");
		listTitelName.add(reportName);

		listTitelName.add("");

		ExportList.add(listTitelName);

		// List<String> listHeader =new ArrayList<String>();
		// //String[] headerList = new String[List.size()];
		// for(int i = 0; i < List.size(); i++)
		// {
		// listHeader.add((String)List.get(i));
		//
		// }String[] ExcelHeader = new String[listHeader.size()];
		// ExcelHeader = listHeader.toArray(ExcelHeader);

		List<String> listTitel2 = new ArrayList<String>();

		listTitel2.add("");
		listTitel2.add("");
		listTitel2.add("");
		ExportList.add(listTitel2);

		String[] headerList = new String[list.size()];
		headerList = list.toArray(headerList);
		ExportList.add(headerList);

		// ExportList.add(headerList);

		for (int i = 0; i < resMap.size(); i++) {
			List DataList = new ArrayList();
			if (i == 1) {
				for (int j = 0; j < 2; j++) {
					DataList = new ArrayList();
					for (int k = 0; k < colCount; k++) {
						DataList.add(" ");
					}
					rowlist.add(DataList);
				}
			}

			if (i <= (rowCount + 1)) {
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
		// List TotalHeaderList=new ArrayList();
		// for(int i=0; i<headerList.length; i++)
		// {
		// if(i==0)
		// TotalHeaderList.add("TOTAL");
		// else if(i==1)
		// TotalHeaderList.add(" ");
		// // else
		// // TotalHeaderList.add(headerList[i]);
		// }

		// rowlist.add(TotalHeaderList);
		if (selectedTab.equalsIgnoreCase("Top Spenders")) {
			List totalList = (List) resMap.get("Total");
			rowlist.add(totalList);
		}
		ExportList.add(rowlist);
		return new ModelAndView("styleExcelTitleCellBorderView", "sheetlist", ExportList);
	}

	private LinkedHashMap funGetData(String fromDate, String toDate, String posName, String selectedTab, String webStockUserCode, String cmbAmount, String txtAmount, String reportType) {
		String posCode = "";

		String longMobileNo = "", strCustomerName = "", strBillNo = "", dblGrandTotal = "";
		JSONArray jArrForTopSpendersTbl = null;
		JSONArray jArrForTotalTbl = null;
		JSONArray jArrForNonSpendersTbl = null;
		LinkedHashMap resMap = new LinkedHashMap();

		List<String> colHeader = new ArrayList<String>();

		// colHeader.add(" ");

		String fromDate1 = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];

		String toDate1 = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];

		if (posName.equalsIgnoreCase("ALL")) {
			posCode = "All";
		}

		if (map.containsKey(posName)) {
			posCode = (String) map.get(posName);

		}

		JSONObject jObjFillter = new JSONObject();
		jObjFillter.put("fromDate", fromDate);
		jObjFillter.put("toDate", toDate);
		jObjFillter.put("posCode", posCode);
		jObjFillter.put("selectedTab", selectedTab);
		jObjFillter.put("webStockUserCode", webStockUserCode);
		jObjFillter.put("cmbAmount", cmbAmount);
		jObjFillter.put("txtAmount", txtAmount);
		jObjFillter.put("reportType", reportType);
		JSONObject jObj = new JSONObject();
		// if(txtVal.equalsIgnoreCase("tab2"))
		// {
		//
		// jObj =
		// objGlobal.funPOSTMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/WebPOSReport/funGetBillWiseSettlementSalesSummary1",jObjFillter);
		//
		// }
		//
		// else
		// {
		jObj = objGlobal.funPOSTMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/WebPOSTransactions/funFillAllTables", jObjFillter);
		// }

		long totalSettleAmt = 0;
		String tabName = (String) jObj.get("tabName");
		colHeader.add("From Date");
		colHeader.add("To Date");
		colHeader.add("POS Name");
		// if(tabName.equalsIgnoreCase("Non Spenders"))
		// {
		//
		// colHeader.add("Mobile Number");
		// colHeader.add("Customer Name");
		// colHeader.add("Last Transaction Date");
		// }
		// else
		if (tabName.equalsIgnoreCase("Top Spenders")) {
			// colHeader.add("Mobile Number");
			// colHeader.add("Customer Name");
			// colHeader.add("No. Of Bills");
			// colHeader.add("Sales Amount");
			jArrForTotalTbl = (JSONArray) jObj.get("TotalTblData");
			for (int i = 0; i < jArrForTotalTbl.size(); i++) {
				JSONObject josnObjRet1 = (JSONObject) jArrForTotalTbl.get(i);
				totalSettleAmt = (long) josnObjRet1.get("totalSettleAmt");

			}
		}

		long colCount = (long) jObj.get("Col Count");
		long rowCount = (long) jObj.get("Row Count");

		List listTotal = new ArrayList();

		listTotal.add("Total");
		listTotal.add(" ");

		listTotal.add(totalSettleAmt);
		// listTotal.add(posName);

		//
		// List allSpendersTblData= new ArrayList();
		int i = 0;
		List topHeaderData = new ArrayList();
		topHeaderData.add(fromDate1);
		topHeaderData.add(toDate1);
		topHeaderData.add(posName);
		resMap.put("" + i, topHeaderData);

		for (i = 0; i < rowCount; i++) {

			if (tabName.equalsIgnoreCase("Top Spenders")) {
				List allTblData = new ArrayList();
				int k = 1;

				jArrForTopSpendersTbl = (JSONArray) jObj.get("TopSpendersTblData");
				allTblData.add("Mobile Number");
				allTblData.add("Customer Name");
				allTblData.add("No. Of. Bills");
				allTblData.add("Sales Amount");
				resMap.put("" + k, allTblData);

				for (int j = 0; j < jArrForTopSpendersTbl.size(); j++) {
					List allSpendersTblData = new ArrayList();
					allSpendersTblData = new ArrayList();
					JSONObject josnObjRet = (JSONObject) jArrForTopSpendersTbl.get(j);

					allSpendersTblData.add(josnObjRet.get("LongMobileNo"));
					allSpendersTblData.add(josnObjRet.get("StrCustomerName"));
					allSpendersTblData.add(josnObjRet.get("strBillNo"));
					allSpendersTblData.add(josnObjRet.get("dblGrandTotal"));

					resMap.put("" + (j + 2), allSpendersTblData);
				}

			} else {
				List allTblData = new ArrayList();
				int k = 1;

				jArrForNonSpendersTbl = (JSONArray) jObj.get("NonSpendersTblData");
				allTblData.add("Mobile Number");
				allTblData.add("Customer Name");
				allTblData.add("Last Transaction Date");
				resMap.put("" + k, allTblData);
				for (int j = 0; j < jArrForNonSpendersTbl.size(); j++) {
					List allSpendersTblData = new ArrayList();
					JSONObject josnObjRet = (JSONObject) jArrForNonSpendersTbl.get(j);

					allSpendersTblData.add(josnObjRet.get("longMobileNo"));
					allSpendersTblData.add(josnObjRet.get("strCustomerName"));
					allSpendersTblData.add(josnObjRet.get("dteBillDate"));

					resMap.put("" + (j + 2), allSpendersTblData);
				}
			}
		}
		// long columns=colCount-1;

		// if(tabName.equalsIgnoreCase("Top Spenders"))
		// {
		// for(int i=3;i<colCount-1;i++)
		// {
		// double total=0.00;
		// for(int j=0; j<rowCount; j++)
		// {
		// List arr=(List)jObj.get(""+j);
		// total+=Double.parseDouble(arr.get(i).toString());
		// }
		// listTotal.add(total);
		// if(total==0)
		// {
		// int len=listTotal.indexOf(total);
		// listTotal.remove(len);
		// colHeader.remove(i);
		//
		// colCount--;
		// for(int j=0; j<rowCount; j++)
		// {
		// List arr=(List)jObj.get(""+j);
		// arr.remove(i);
		// resMap.put(""+j,arr);
		// }
		// i--;
		// }
		//
		//
		// }
		// }
		// else
		// {
		// for(int i=3;i<colCount;i++)
		// {
		// double total=0.00;
		// for(int j=0; j<rowCount; j++)
		// {
		// List arr=(List)jObj.get(""+j);
		// total+=Double.parseDouble(arr.get(i).toString());
		// }
		// listTotal.add(total);
		// if(total==0)
		// {
		// int len=listTotal.indexOf(total);
		// listTotal.remove(len);
		// colHeader.remove(i);
		//
		// colCount--;
		// for(int j=0; j<rowCount; j++)
		// {
		// List arr=(List)jObj.get(""+j);
		// arr.remove(i);
		// resMap.put(""+j,arr);
		// }
		// i--;
		// }
		//
		//
		// }
		// }

		resMap.put("Header", colHeader);
		resMap.put("ColCount", colCount);
		resMap.put("RowCount", rowCount);
		resMap.put("Total", listTotal);

		return resMap;
	}
}
