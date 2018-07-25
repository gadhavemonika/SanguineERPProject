package com.sanguine.webpos.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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
import com.sanguine.webpos.bean.clsPOSReasonMasterBean;
import com.sanguine.webpos.bean.clsPOSStockInOutHd;
import com.sanguine.webpos.bean.clsWebPOSReportBean;

@Controller
public class clsPOSStockInOutFlashController {

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private ServletContext servletContext;

	List<clsPOSReasonMasterBean> listReason = new ArrayList<clsPOSReasonMasterBean>();

	Map mapPOS = new HashMap();
	Map<String, String> mapReason = new HashMap<String, String>();

	@RequestMapping(value = "/frmPOSStkInOutFlash", method = RequestMethod.GET)
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

			mapPOS.put(josnObjRet.get("strPosName"), josnObjRet.get("strPosCode"));
		}
		model.put("posList", poslist);

		List typeList = new ArrayList();
		typeList.add("Item wise");
		typeList.add("MenuHead wise");
		typeList.add("SubGroup wise");
		typeList.add("Group wise");
		model.put("typeList", typeList);

		List reportTypeList = new ArrayList();
		reportTypeList.add("Stock In");
		reportTypeList.add("Stock Out");
		model.put("operationTypeList", reportTypeList);

		List reasonList = new ArrayList();
		funGetStockInOutReasons(strClientCode);
		for (int cnt = 0; cnt < listReason.size(); cnt++) {
			clsPOSReasonMasterBean obj = listReason.get(cnt);
			reasonList.add(obj.getStrReasonName());
		}
		model.put("reasonList", reasonList);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSStkInOutFlash_1", "command", new clsWebPOSReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSStkInOutFlash", "command", new clsWebPOSReportBean());
		} else {
			return null;
		}
	}

	private clsPOSStockInOutHd funGetStockInOutReasons(String clientCode) {
		clsPOSStockInOutHd objBean = null;

		JSONObject jObjReasonDetails = new JSONObject();
		JSONArray jArrReasonList = null;
		String posUrl = "http://localhost:8080/prjSanguineWebService/WebPOSReport/funGetReasonForStockInOutFlash" + "?ClientCode=" + clientCode;
		System.out.println(posUrl);

		jObjReasonDetails = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		jArrReasonList = (JSONArray) jObjReasonDetails.get("ReasonList");

		if (null != jArrReasonList) {
			for (int cnt = 0; cnt < jArrReasonList.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArrReasonList.get(cnt);
				// objBean=new clsPOSPhysicalStockPostingBean();
				// objBean.setStrReason(jobj.get("ReasonName"));
				clsPOSReasonMasterBean objReasonDtl = new clsPOSReasonMasterBean();
				objReasonDtl.setStrReasonCode((String) jobj.get("ReasonCode"));
				objReasonDtl.setStrReasonName((String) jobj.get("ReasonName"));
				listReason.add(objReasonDtl);
				mapReason.put((String) jobj.get("ReasonCode"), (String) jobj.get("ReasonName"));
			}

		}
		return objBean;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/loadPOSStockInOutFlash" }, method = RequestMethod.GET)
	@ResponseBody
	public Map FunLoadPOSStockInOutFlash(HttpServletRequest req) {
		LinkedHashMap resMap = new LinkedHashMap();

		String reasonCode = "";
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String strFromdate = req.getParameter("fromDate");

		String strToDate = req.getParameter("toDate");

		String posName = req.getParameter("posName");

		String viewType = req.getParameter("viewType");

		String operationType = req.getParameter("operationType");
		String operationTypeCode = req.getParameter("operationTypeCode");

		String searchValue = req.getParameter("searchValue");

		String reasonName = req.getParameter("reasonName");

		String time = req.getParameter("time");

		if (mapReason.size() > 0) {
			for (String key : mapReason.keySet()) {
				if (mapReason.get(key).equals(reasonName)) {
					reasonCode = key;
				}
			}
		}

		if (time.equals("first")) {
			String strPosCode = req.getSession().getAttribute("loginPOS").toString();
			String posURL = "http://localhost:8080/prjSanguineWebService/APOSIntegration/funGetPOSDate" + "?POSCode=" + strPosCode;
			JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(posURL);
			String posDate = jObj.get("POSDate").toString();
			String[] arrDate = posDate.split(" ");
			strFromdate = arrDate[0].split("-")[2] + "-" + arrDate[0].split("-")[1] + "-" + arrDate[0].split("-")[0];
			strToDate = arrDate[0].split("-")[2] + "-" + arrDate[0].split("-")[1] + "-" + arrDate[0].split("-")[0];
		}

		resMap = FunGetStockInOutFlashData(clientCode, strFromdate, strToDate, posName, operationType, operationTypeCode, viewType, searchValue, reasonCode, "N");

		return resMap;
	}

	@SuppressWarnings({ "unchecked" })
	private LinkedHashMap FunGetStockInOutFlashData(String clientCode, String strFromdate, String strToDate, String strPOSName, String operationType, String operationTypeCode, String viewType, String searchData, String reasonCode, String isExportOperation) {
		LinkedHashMap resMap = new LinkedHashMap();

		double amtTotal = 0, netTotal = 0, paxTotal = 0;

		List colHeader = new ArrayList();
		String posCode = "All";

		String fromDate1 = strFromdate.split("-")[2] + "-" + strFromdate.split("-")[1] + "-" + strFromdate.split("-")[0];

		String toDate1 = strToDate.split("-")[2] + "-" + strToDate.split("-")[1] + "-" + strToDate.split("-")[0];

		if (!strPOSName.equalsIgnoreCase("ALL")) {
			if (mapPOS.containsKey(strPOSName)) {
				posCode = (String) mapPOS.get(strPOSName);
			}
		}

		JSONObject jObjFillter = new JSONObject();
		jObjFillter.put("fromdate", fromDate1);
		jObjFillter.put("toDate", toDate1);
		jObjFillter.put("posCode", posCode);
		jObjFillter.put("operationType", operationType);
		jObjFillter.put("operationTypeCode", operationTypeCode);
		jObjFillter.put("viewType", viewType);
		jObjFillter.put("searchData", searchData);
		jObjFillter.put("reasonCode", reasonCode);

		JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/WebPOSReport/funStockInOutFlashReport", jObjFillter);
		List list = new ArrayList();
		List totalList = new ArrayList();
		JSONArray jarr = (JSONArray) jObj.get("jArr");

		totalList.add("Total :");
		if (isExportOperation.equals("Y")) {
			totalList.add("   ");
		}

		if (null != jarr) {
			if (jarr.size() > 0) {
				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jObjtemp = (JSONObject) jarr.get(i);
					List arrList = new ArrayList();
					if (isExportOperation.equals("Y")) {
						arrList.add(i + 1);
					}

					arrList.add(jObjtemp.get("GroupName").toString());
					arrList.add(jObjtemp.get("Qty").toString());
					arrList.add(jObjtemp.get("Purchase Rate").toString());
					arrList.add(jObjtemp.get("Amount").toString());
					arrList.add(jObjtemp.get("StockInOut Code").toString());
					arrList.add(jObjtemp.get("StockInOut Date").toString());
					arrList.add(jObjtemp.get("POSName").toString());

					list.add(arrList);
				}

				JSONObject jObjTotal = (JSONObject) jObj.get("jObjTotal");
				totalList.add(Double.parseDouble(jObjTotal.get("sumQty").toString()));
				totalList.add(Double.parseDouble(jObjTotal.get("sumPurchaseRate").toString()));
				totalList.add(Double.parseDouble(jObjTotal.get("sumAmount").toString()));

			}

		}

		List arrListHeader = null;
		arrListHeader = new ArrayList();
		if (isExportOperation.equals("Y")) {
			arrListHeader.add("Serial No");
		}
		arrListHeader.add("Item Name");
		arrListHeader.add("Qty");
		arrListHeader.add("Purchase Rate");
		arrListHeader.add("Amount");
		if (operationType.equals("Stock In")) {
			arrListHeader.add("StockIn No");
			arrListHeader.add("StockIn Date");
		} else {
			arrListHeader.add("StockOut No");
			arrListHeader.add("StockOut Date");
		}
		arrListHeader.add("POS Name");

		resMap.put("listHeader", arrListHeader);
		resMap.put("listDetails", list);
		resMap.put("totalList", totalList);

		return resMap;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptPOSStockInOutFlashReport", method = RequestMethod.POST)
	private ModelAndView funReport(@ModelAttribute("command") clsWebPOSReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String posName = objBean.getStrPOSName();

		String strFromdate = objBean.getFromDate();
		String strToDate = objBean.getToDate();
		String operationType = objBean.getStrReportType();
		String operationTypeCode = objBean.getStrViewType();
		String viewType = objBean.getStrType();
		String searchValue = objBean.getStrOperationType();
		String reasonCode = "";

		if (mapReason.size() > 0) {
			for (String key : mapReason.keySet()) {
				if (mapReason.get(key).equals(objBean.getStrReasonCode())) {
					reasonCode = key;
				}
			}
		}

		Map resMap = new LinkedHashMap();

		resMap = FunGetStockInOutFlashData(clientCode, strFromdate, strToDate, posName, operationType, operationTypeCode, viewType, searchValue, reasonCode, "Y");
		List ExportList = new ArrayList();

		String FileName = "Stock In Out Flash " + strFromdate + "_To_" + strToDate;

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
