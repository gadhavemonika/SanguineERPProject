package com.sanguine.webpos.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.sanguine.webpos.bean.clsWebPOSReportBean;

@Controller
public class clsPOSDayWiseSalesSummaryFlashController {

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private ServletContext servletContext;

	Map map = new HashMap();

	@RequestMapping(value = "/frmPOSDayWiseSalesSummaryFlash", method = RequestMethod.GET)
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

		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOS");
		JSONArray jArryPosList = (JSONArray) jObj.get("posList");
		for (int i = 0; i < jArryPosList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryPosList.get(i);
			poslist.add(josnObjRet.get("strPosName"));
			map.put(josnObjRet.get("strPosName"), josnObjRet.get("strPosCode"));
		}
		model.put("posList", poslist);

		Map settlementList = new HashMap();

		settlementList.put("ALL", "ALL");
		jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetAllSettlement");
		jArryPosList = (JSONArray) jObj.get("settlementList");
		for (int i = 0; i < jArryPosList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryPosList.get(i);

			settlementList.put(josnObjRet.get("strSettelmentCode"), josnObjRet.get("strSettlementName"));
		}
		model.put("settlementList", settlementList);

		if ("2".equalsIgnoreCase(urlHits)) {

			return new ModelAndView("frmPOSDayWiseSalesSummaryFlash_1", "command", new clsWebPOSReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSDayWiseSalesSummaryFlash", "command", new clsWebPOSReportBean());
		} else {
			return null;
		}

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptPOSDayWiseSalesSummeryFlash", method = RequestMethod.POST)
	private ModelAndView funReport(@ModelAttribute("command") clsWebPOSReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String fromDate = objBean.getFromDate();

		String toDate = objBean.getToDate();

		String operationType = objBean.getStrOperationType();
		String posName = objBean.getStrPOSName();
		String settlementName = objBean.getStrSettlementName();
		String viewBy = objBean.getStrViewBy();
		String withDiscount = objGlobalFunctions.funIfNull(objBean.getStrWithDiscount(), "N", "Y");
		Map resMap = new LinkedHashMap();

		resMap = FunGetData(clientCode, withDiscount, fromDate, toDate, operationType, settlementName, posName, viewBy);

		List ExportList = new ArrayList();

		String dteFromDate = objBean.getFromDate();
		String dteToDate = objBean.getToDate();
		String FileName = "DayWiseSalesSummeryFlash_" + dteFromDate + "_To_" + dteToDate;

		ExportList.add(FileName);
		long rowCount = (long) resMap.get("RowCount");
		long colCount = (long) resMap.get("ColCount");
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
		for (int i = 0; i < headerList.length; i++) {
			if (i == 0)
				TotalHeaderList.add("TOTAL");

			else
				TotalHeaderList.add(headerList[i]);
		}

		rowlist.add(TotalHeaderList);

		List totalList = (List) resMap.get("Total");
		rowlist.add(totalList);

		ExportList.add(rowlist);
		return new ModelAndView("excelViewWithReportName", "listWithReportName", ExportList);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/loadDaywiseSalesSummary1" }, method = RequestMethod.GET)
	@ResponseBody
	public Map FunLoadDaywiseSalesSummary1(HttpServletRequest req) {
		LinkedHashMap resMap = new LinkedHashMap();

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String withDiscount = req.getParameter("withDiscount");

		String fromDate = req.getParameter("fromDate");

		String toDate = req.getParameter("toDate");

		String operationType = req.getParameter("operationType");
		String settlementName = req.getParameter("settlementName");
		String posName = req.getParameter("posName");
		resMap = FunGetData(clientCode, withDiscount, fromDate, toDate, operationType, settlementName, posName, "ITEM'S GROUP WISE");
		return resMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/loadDaywiseSalesSummary2" }, method = RequestMethod.GET)
	@ResponseBody
	public Map FunLoadDaywiseSalesSummary2(HttpServletRequest req) {
		LinkedHashMap resMap = new LinkedHashMap();

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String withDiscount = req.getParameter("withDiscount");

		String fromDate = req.getParameter("fromDate");

		String toDate = req.getParameter("toDate");

		String operationType = req.getParameter("operationType");
		String settlementName = req.getParameter("settlementName");
		String posName = req.getParameter("posName");
		resMap = FunGetData(clientCode, withDiscount, fromDate, toDate, operationType, settlementName, posName, "NONE");
		return resMap;
	}

	private LinkedHashMap FunGetData(String clientCode, String withDiscount, String fromDate, String toDate, String operationType, String settlementName, String posName, String viewBy) {

		LinkedHashMap resMap = new LinkedHashMap();

		List colHeader = new ArrayList();

		colHeader.add(" ");

		String fromDate1 = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];

		String toDate1 = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];

		String posCode = "ALL";

		if (map.containsKey(posName)) {
			posCode = (String) map.get(posName);

		}

		JSONObject jObjFillter = new JSONObject();
		jObjFillter.put("fromDate", fromDate1);
		jObjFillter.put("toDate", toDate1);
		jObjFillter.put("operationType", operationType);
		jObjFillter.put("posCode", posCode);
		jObjFillter.put("withDiscount", withDiscount);
		jObjFillter.put("settlementName", settlementName);
		jObjFillter.put("posName", posName);
		JSONObject jObj = new JSONObject();
		if (viewBy.equalsIgnoreCase("ITEM'S GROUP WISE")) {

			jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funGetDayWiseSalesSummary1", jObjFillter);

		}

		else {
			jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funGetDayWiseSalesSummary2", jObjFillter);
		}
		JSONArray jColHeaderArr = (JSONArray) jObj.get("Col Header");
		colHeader = jColHeaderArr;
		long colCount = (long) jObj.get("Col Count");
		long rowCount = (long) jObj.get("Row Count");

		List listTotal = new ArrayList();

		listTotal.add("Total");

		listTotal.add(posName);
		for (int i = 0; i < rowCount; i++) {
			resMap.put("" + i, (List) jObj.get("" + i));

		}

		for (int i = 2; i < colCount; i++) {
			double total = 0.00;
			for (int j = 0; j < rowCount; j++) {
				List arr = (List) jObj.get("" + j);
				total += Double.parseDouble(arr.get(i).toString());
			}
			listTotal.add(total);
			if (total == 0) {
				int len = listTotal.indexOf(total);
				listTotal.remove(len);
				colHeader.remove(i);

				colCount--;
				for (int j = 0; j < rowCount; j++) {
					List arr = (List) jObj.get("" + j);
					arr.remove(i);
					resMap.put("" + j, arr);
				}
				i--;
			}

		}

		resMap.put("Header", colHeader);
		resMap.put("ColCount", colCount);
		resMap.put("RowCount", rowCount);
		resMap.put("Total", listTotal);

		return resMap;
	}
}
