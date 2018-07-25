package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsAddKOTToBillBean;
import com.sanguine.webpos.bean.clsPOSPSPDtl;
import com.sanguine.webpos.bean.clsPOSPhysicalStockPostingBean;
import com.sanguine.webpos.bean.clsPOSUserAccessBean;
import com.sanguine.webpos.bean.clsWebPOSReportBean;

@Controller
public class clsPOSStockFlashController {

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private ServletContext servletContext;

	private Vector vItemCode = new java.util.Vector();

	Map map = new HashMap();

	@RequestMapping(value = "/frmPOSStockFlashReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		List poslist = new ArrayList();
		poslist.add("ALL");

		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/APOSIntegration/funGetPOS");
		JSONArray jArryPosList = (JSONArray) jObj.get("posList");
		for (int i = 0; i < jArryPosList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryPosList.get(i);
			poslist.add(josnObjRet.get("strPosName"));

			map.put(josnObjRet.get("strPosName"), josnObjRet.get("strPosCode"));
		}
		model.put("posList", poslist);

		List typeList = new ArrayList();
		typeList.add("Both");
		typeList.add("Raw Material");
		typeList.add("Menu Item");
		model.put("typeList", typeList);

		List reportTypeList = new ArrayList();
		reportTypeList.add("Stock");
		reportTypeList.add("Raw ReOrder");
		model.put("reportTypeList", reportTypeList);

		List groupList = new ArrayList<String>();
		groupList.add("ALL");

		JSONArray jGroupArry = funGetAllGroup(strClientCode);
		for (int i = 0; i < jGroupArry.size(); i++) {
			JSONObject jObjtemp = (JSONObject) jGroupArry.get(i);
			groupList.add(jObjtemp.get("strGroupName").toString());
		}
		model.put("groupList", groupList);

		List viewByList = new ArrayList();
		viewByList.add("Positive");
		viewByList.add("Negative");
		viewByList.add("Both");
		model.put("viewByList", viewByList);

		List showZeroBalList = new ArrayList();
		showZeroBalList.add("Yes");
		showZeroBalList.add("No");
		model.put("showZeroBalList", showZeroBalList);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSStockFlashReport_1", "command", new clsWebPOSReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSStockFlashReport", "command", new clsWebPOSReportBean());
		} else {
			return null;
		}
	}

	public JSONArray funGetAllGroup(String strClientCode) {
		List sglist = new ArrayList<String>();
		JSONArray jArry = new JSONArray();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetAllGroup";
		try {
			JSONObject objRows = new JSONObject();
			objRows.put("strClientCode", strClientCode);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(posUrl, objRows);
			jArry = (JSONArray) jObj.get("allGroupData");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jArry;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/loadPOSStockFlash" }, method = RequestMethod.GET)
	@ResponseBody
	public Map FunLoadStockFlashReport(HttpServletRequest req) {
		LinkedHashMap resMap = new LinkedHashMap();

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String strFromdate = req.getParameter("fromDate");

		String strToDate = req.getParameter("toDate");

		String posName = req.getParameter("posName");

		String type = req.getParameter("type");

		String reportType = req.getParameter("reportType");

		String groupwise = req.getParameter("groupwise");

		String showStockWith = req.getParameter("showStockWith");

		String showZeroBalStockYN = req.getParameter("showZeroBalStockYN");
		String time = req.getParameter("time");
		if (time.equals("first")) {
			String strPosCode = req.getSession().getAttribute("loginPOS").toString();
			String posURL = "http://localhost:8080/prjSanguineWebService/APOSIntegration/funGetPOSDate" + "?POSCode=" + strPosCode;
			JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(posURL);
			String posDate = jObj.get("POSDate").toString();
			String[] arrDate = posDate.split(" ");
			strFromdate = arrDate[0].split("-")[2] + "-" + arrDate[0].split("-")[1] + "-" + arrDate[0].split("-")[0];
			strToDate = arrDate[0].split("-")[2] + "-" + arrDate[0].split("-")[1] + "-" + arrDate[0].split("-")[0];
		}

		if (reportType.equals("Stock")) {
			resMap = FunGetStockFlashData(clientCode, strFromdate, strToDate, posName, type, reportType, groupwise, showStockWith, showZeroBalStockYN);
		} else {
			resMap = FunGetReOrderStockFlashData(clientCode, strFromdate, strToDate, posName, type, reportType, groupwise, showStockWith, showZeroBalStockYN);

		}

		return resMap;
	}

	@SuppressWarnings({ "unchecked" })
	private LinkedHashMap FunGetStockFlashData(String clientCode, String strFromdate, String strToDate, String strPOSName, String type, String reportType, String groupwise, String showStockWith, String showZeroBalStockYN) {
		LinkedHashMap resMap = new LinkedHashMap();

		double amtTotal = 0, netTotal = 0, paxTotal = 0;

		List colHeader = new ArrayList();
		String posCode = "All";

		String fromDate1 = strFromdate.split("-")[2] + "-" + strFromdate.split("-")[1] + "-" + strFromdate.split("-")[0];

		String toDate1 = strToDate.split("-")[2] + "-" + strToDate.split("-")[1] + "-" + strToDate.split("-")[0];

		if (!strPOSName.equalsIgnoreCase("ALL")) {
			if (map.containsKey(strPOSName)) {
				posCode = (String) map.get(strPOSName);
			}

		}

		JSONObject jObjFillter = new JSONObject();
		jObjFillter.put("fromdate", fromDate1);
		jObjFillter.put("toDate", toDate1);
		jObjFillter.put("posCode", posCode);
		jObjFillter.put("type", type);
		jObjFillter.put("reportType", reportType);
		jObjFillter.put("groupName", groupwise);
		jObjFillter.put("balStockSign", showStockWith);
		jObjFillter.put("zeroStockBalYN", showZeroBalStockYN);

		JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/WebPOSReport/funStockFlashReport", jObjFillter);
		List list = new ArrayList();
		List totalList = new ArrayList();
		JSONArray jarr = (JSONArray) jObj.get("jArr");

		totalList.add("Total :");

		if (null != jarr) {
			if (jarr.size() > 0) {
				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jObjtemp = (JSONObject) jarr.get(i);
					List arrList = new ArrayList();
					arrList.add(jObjtemp.get("groupName").toString());
					arrList.add(jObjtemp.get("subGroupName").toString());
					arrList.add(jObjtemp.get("itemName").toString());
					arrList.add(jObjtemp.get("Opening Stock").toString());
					arrList.add(jObjtemp.get("Stock In").toString());
					arrList.add(jObjtemp.get("Stock Out").toString());
					arrList.add(jObjtemp.get("Sale").toString());
					arrList.add(jObjtemp.get("Balance").toString());

					list.add(arrList);
				}

				JSONObject jObjTotal = (JSONObject) jObj.get("jObjTatol");
				;
				totalList.add(Double.parseDouble(jObjTotal.get("sumOpeningStock").toString()));
				totalList.add(Double.parseDouble(jObjTotal.get("sumStockIn").toString()));
				totalList.add(Double.parseDouble(jObjTotal.get("sumStockOut").toString()));
				totalList.add(Double.parseDouble(jObjTotal.get("sumSaleAmt").toString()));
				totalList.add(Double.parseDouble(jObjTotal.get("sumBalAmt").toString()));

			}

		}

		List arrListHeader = null;
		arrListHeader = new ArrayList();
		arrListHeader.add("Group");
		arrListHeader.add("SubGroup");
		arrListHeader.add("Item Name");
		arrListHeader.add("Opg Stock");
		arrListHeader.add("Stock In");
		arrListHeader.add("Stock Out");
		arrListHeader.add("Sale");
		arrListHeader.add("Bal");

		resMap.put("listHeader", arrListHeader);
		resMap.put("listDetails", list);
		resMap.put("totalList", totalList);
		return resMap;
	}

	@SuppressWarnings({ "unchecked" })
	private LinkedHashMap FunGetReOrderStockFlashData(String clientCode, String strFromdate, String strToDate, String strPOSName, String type, String reportType, String groupwise, String showStockWith, String showZeroBalStockYN) {
		LinkedHashMap resMap = new LinkedHashMap();

		double amtTotal = 0, netTotal = 0, paxTotal = 0;

		List colHeader = new ArrayList();
		String posCode = "All";

		String fromDate1 = strFromdate.split("-")[2] + "-" + strFromdate.split("-")[1] + "-" + strFromdate.split("-")[0];

		String toDate1 = strToDate.split("-")[2] + "-" + strToDate.split("-")[1] + "-" + strToDate.split("-")[0];

		if (!strPOSName.equalsIgnoreCase("ALL")) {
			if (map.containsKey(strPOSName)) {
				posCode = (String) map.get(strPOSName);
			}

		}

		JSONObject jObjFillter = new JSONObject();
		jObjFillter.put("fromdate", fromDate1);
		jObjFillter.put("toDate", toDate1);
		jObjFillter.put("posCode", posCode);
		jObjFillter.put("type", type);
		jObjFillter.put("reportType", reportType);
		jObjFillter.put("groupName", groupwise);
		jObjFillter.put("balStockSign", showStockWith);
		jObjFillter.put("zeroStockBalYN", showZeroBalStockYN);

		JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/WebPOSReport/funStockFlashReport", jObjFillter);
		List list = new ArrayList();
		List totalList = new ArrayList();
		JSONArray jarr = (JSONArray) jObj.get("jArr");

		totalList.add("Total :");

		if (null != jarr) {
			if (jarr.size() > 0) {
				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jObjtemp = (JSONObject) jarr.get(i);
					List arrList = new ArrayList();
					arrList.add(jObjtemp.get("groupName").toString());
					arrList.add(jObjtemp.get("subGroupName").toString());
					arrList.add(jObjtemp.get("itemName").toString());
					arrList.add(jObjtemp.get("Opening Stock").toString());
					arrList.add(jObjtemp.get("Stock In").toString());
					arrList.add(jObjtemp.get("Stock Out").toString());
					arrList.add(jObjtemp.get("Sale").toString());
					arrList.add(jObjtemp.get("Balance").toString());

					vItemCode.add(jObjtemp.get("itemCode").toString());

					list.add(arrList);
				}

				JSONObject jObjTotal = (JSONObject) jObj.get("jObjTatol");
				;
				totalList.add(Double.parseDouble(jObjTotal.get("sumOpeningStock").toString()));
				totalList.add(Double.parseDouble(jObjTotal.get("sumStockIn").toString()));
				totalList.add(Double.parseDouble(jObjTotal.get("sumStockOut").toString()));
				totalList.add(Double.parseDouble(jObjTotal.get("sumSaleAmt").toString()));
				totalList.add(Double.parseDouble(jObjTotal.get("sumBalAmt").toString()));
				totalList.add(Math.rint(Double.parseDouble(jObjTotal.get("openProductionQty").toString())));
				totalList.add("    ");
				totalList.add("    ");
				totalList.add(Double.parseDouble(jObjTotal.get("sumReorderQty").toString()));

			}

		}

		List arrListHeader = null;
		arrListHeader = new ArrayList();
		arrListHeader.add("Group");
		arrListHeader.add("SubGroup");
		arrListHeader.add("Item Name");
		arrListHeader.add("Opg Stock");
		arrListHeader.add("Stock In");
		arrListHeader.add("Stock Out");
		arrListHeader.add("Sale");
		arrListHeader.add("Bal");
		arrListHeader.add("Order Qty");
		arrListHeader.add("Min Level");
		arrListHeader.add("Max Level");
		arrListHeader.add("ReOrder Qty");

		resMap.put("listHeader", arrListHeader);
		resMap.put("listDetails", list);
		resMap.put("totalList", totalList);

		return resMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/generateProductionStockEntry" }, method = RequestMethod.POST)
	@ResponseBody
	public clsWebPOSReportBean funLoadTableData(HttpServletRequest req) {
		List itemList = null;
		clsWebPOSReportBean obj = new clsWebPOSReportBean();
		String urlHits = "1";
		try {
			if (vItemCode.size() > 0) {
				String clientCode = req.getSession().getAttribute("clientCode").toString();
				String webStockUserCode = req.getSession().getAttribute("usercode").toString();
				String posCode = req.getSession().getAttribute("loginPOS").toString();
				String type = req.getParameter("type");
			}

			return obj;

		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return obj;
		}

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptPOSStockFlashReport", method = RequestMethod.POST)
	private ModelAndView funReport(@ModelAttribute("command") clsWebPOSReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String posName = objBean.getStrPOSName();

		String strFromdate = objBean.getFromDate();
		String strToDate = objBean.getToDate();
		String type = objBean.getStrType();
		String reportType = objBean.getStrReportType();
		String groupwise = objBean.getStrGroupName();
		String showStockWith = objBean.getStrViewBy();
		String showZeroBalStockYN = objBean.getStrDocType();

		Map resMap = new LinkedHashMap();

		if (reportType.equals("Stock")) {
			resMap = FunGetStockFlashData(clientCode, strFromdate, strToDate, posName, type, reportType, groupwise, showStockWith, showZeroBalStockYN);
		} else {
			resMap = FunGetReOrderStockFlashData(clientCode, strFromdate, strToDate, posName, type, reportType, groupwise, showStockWith, showZeroBalStockYN);

		}

		List ExportList = new ArrayList();

		String FileName = "Stock Flash " + strFromdate + "_To_" + strToDate;

		ExportList.add(FileName);

		List List = (List) resMap.get("listHeader");

		String[] headerList = new String[List.size()];
		for (int i = 0; i < List.size(); i++) {
			headerList[i] = (String) List.get(i);
		}

		ExportList.add(headerList);

		List dataList = (List) resMap.get("listDetails");
		List totalList = (List) resMap.get("totalList");

		dataList.add(totalList);

		ExportList.add(dataList);

		return new ModelAndView("excelViewWithReportName", "listWithReportName", ExportList);
	}

}
