package com.sanguine.webpos.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsGroupWaiseSalesBean;
import com.sanguine.webpos.bean.clsPOSItemModifierMasterBean;
import com.sanguine.webpos.bean.clsPOSTaxWaiseBean;
import com.sanguine.webpos.bean.clsPOSWiseSalesReportBean;
import com.sanguine.webpos.bean.clsWebPOSReportBean;
import com.sanguine.webpos.util.clsGroupWiseComparator;

@Controller
public class clsPOSWiseSalesReportController {

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private ServletContext servletContext;

	Map map = new HashMap();

	@RequestMapping(value = "/frmPOSWiseSalesComparison", method = RequestMethod.GET)
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

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSWiseSalesReport_1", "command", new clsWebPOSReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSWiseSalesReport", "command", new clsWebPOSReportBean());
		} else {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptPOSWiseSalesReport", method = RequestMethod.POST)
	private ModelAndView funReport(@ModelAttribute("command") clsWebPOSReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String fromDate = objBean.getFromDate();
		String toDate = objBean.getToDate();
		String strViewType = objBean.getStrViewType();

		Map resMap = new LinkedHashMap();

		resMap = FunGetData(clientCode, fromDate, toDate, strViewType);

		List ExportList = new ArrayList();

		String FileName = "POSWiseSalesReport_" + fromDate + "_To_" + toDate;

		ExportList.add(FileName);

		List List = (List) resMap.get("listcol");

		String[] headerList = new String[List.size()];
		for (int i = 0; i < List.size(); i++) {
			headerList[i] = (String) List.get(i);
		}

		ExportList.add(headerList);

		List dataList = (List) resMap.get("List");
		List totalList = (List) resMap.get("totalList");

		dataList.add(totalList);

		ExportList.add(dataList);

		return new ModelAndView("excelViewWithReportName", "listWithReportName", ExportList);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/loadPOSWiseSalesReport" }, method = RequestMethod.GET)
	@ResponseBody
	public Map FunLoadPOSWiseSalesReport(HttpServletRequest req) {
		LinkedHashMap resMap = new LinkedHashMap();

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String fromDate = req.getParameter("fromDate");

		String toDate = req.getParameter("toDate");

		String strViewType = req.getParameter("strViewTypedata");

		resMap = FunGetData(clientCode, fromDate, toDate, strViewType);

		return resMap;
	}

	private LinkedHashMap FunGetData(String clientCode, String fromDate, String toDate, String strViewType) {

		LinkedHashMap resMap = new LinkedHashMap();

		List list = new ArrayList();
		List listcol = new ArrayList();
		List totalList = new ArrayList();
		totalList.add("Total");

		String fromDate1 = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];

		String toDate1 = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];

		JSONObject jObjFillter = new JSONObject();
		jObjFillter.put("fromDate", fromDate1);
		jObjFillter.put("toDate", toDate1);
		jObjFillter.put("strViewType", strViewType);

		JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funGetPOSWiseSalesReport", jObjFillter);

		JSONArray jArrSearchList = (JSONArray) jObj.get("jArr");
		JSONObject objtotal = (JSONObject) jObj.get("jObjTotal");

		if (strViewType.equalsIgnoreCase("ITEM WISE")) {

			if (null != jArrSearchList) {
				for (int i = 0; i < jArrSearchList.size(); i++) {
					JSONObject subJsonObject = (JSONObject) jArrSearchList.get(i);
					List arrList = new ArrayList();

					arrList.add(subJsonObject.get("strItemCode").toString());
					arrList.add(subJsonObject.get("strItemName").toString());
					arrList.add(subJsonObject.get("strPOSName").toString());
					arrList.add(Double.parseDouble(subJsonObject.get("dblAmount").toString()));

					list.add(arrList);
				}
			}
			listcol.add("Item Code");
			listcol.add("Item Name");
			listcol.add("Pos");
			listcol.add("Amount");

		}

		if (strViewType.equalsIgnoreCase("GROUP WISE")) {

			if (null != jArrSearchList) {
				for (int i = 0; i < jArrSearchList.size(); i++) {
					JSONObject subJsonObject = (JSONObject) jArrSearchList.get(i);
					List arrList = new ArrayList();

					arrList.add(subJsonObject.get("strGroupCode").toString());
					arrList.add(subJsonObject.get("strGroupName").toString());
					arrList.add(subJsonObject.get("strPosName").toString());
					arrList.add(Double.parseDouble(subJsonObject.get("dblAmount").toString()));

					list.add(arrList);
				}
			}
			listcol.add("Group Code");
			listcol.add("Group Name");
			listcol.add("Pos");
			listcol.add("Amount");

		}

		if (strViewType.equalsIgnoreCase("SUB GROUP WISE")) {

			if (null != jArrSearchList) {
				for (int i = 0; i < jArrSearchList.size(); i++) {
					JSONObject subJsonObject = (JSONObject) jArrSearchList.get(i);
					List arrList = new ArrayList();

					arrList.add(subJsonObject.get("strSubGroupCode").toString());
					arrList.add(subJsonObject.get("strSubGroupName").toString());
					arrList.add(subJsonObject.get("strPOSName").toString());
					arrList.add(Double.parseDouble(subJsonObject.get("dblAmount").toString()));

					list.add(arrList);
				}
			}
			listcol.add("Sub Group Code");
			listcol.add("Sub Group Name");
			listcol.add("Pos");
			listcol.add("Amount");

		}

		if (strViewType.equalsIgnoreCase("MENU HEAD WISE")) {
			;
			if (null != jArrSearchList) {
				for (int i = 0; i < jArrSearchList.size(); i++) {
					JSONObject subJsonObject = (JSONObject) jArrSearchList.get(i);
					List arrList = new ArrayList();

					arrList.add(subJsonObject.get("strMenuCode").toString());
					arrList.add(subJsonObject.get("strMenuName").toString());
					arrList.add(subJsonObject.get("strPOSName").toString());
					arrList.add(Double.parseDouble(subJsonObject.get("dblAmount").toString()));

					list.add(arrList);
				}
			}
			listcol.add("MENU HEAD  Code");
			listcol.add("MENU HEAD  Name");
			listcol.add("Pos");
			listcol.add("Amount");

		}

		totalList.add(Double.parseDouble(objtotal.get("total").toString()));

		resMap.put("List", list);
		resMap.put("totalList", totalList);
		resMap.put("listcol", listcol);
		return resMap;
	}
}
