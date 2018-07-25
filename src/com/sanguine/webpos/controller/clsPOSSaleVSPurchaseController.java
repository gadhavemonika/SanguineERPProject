package com.sanguine.webpos.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.util.clsPOSDashboardBean;
import com.sanguine.webpos.bean.clsWebPOSReportBean;

@Controller
public class clsPOSSaleVSPurchaseController {
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@RequestMapping(value = "/frmPOSSaleVSPurchase", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsPOSDashboardBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSSaleVSPurchase_1", "command", new clsPOSDashboardBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSSaleVSPurchase", "command", new clsPOSDashboardBean());
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/loadSaleVSPurchaseDtl" }, method = RequestMethod.GET)
	@ResponseBody
	public clsPOSDashboardBean FunLoadPOSWiseSalesReport(HttpServletRequest req) {
		LinkedHashMap resMap = new LinkedHashMap();
		clsPOSDashboardBean objBean = new clsPOSDashboardBean();

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String fromDate = req.getParameter("fromDate");

		String toDate = req.getParameter("toDate");

		objBean = FunGetData(clientCode, fromDate, toDate);

		return objBean;
	}

	private clsPOSDashboardBean FunGetData(String clientCode, String fromDate, String toDate) {
		clsPOSDashboardBean objBean = new clsPOSDashboardBean();

		String fromDate1 = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];

		String toDate1 = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];

		JSONObject jObjFillter = new JSONObject();
		jObjFillter.put("fromDate", fromDate1);
		jObjFillter.put("toDate", toDate1);

		JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funGetSalePurchaseComparisonDtl", jObjFillter);

		JSONArray jArrSearchList = (JSONArray) jObj.get("jArr");
		JSONObject objtotal = (JSONObject) jObj.get("jObjTotal");
		List<clsWebPOSReportBean> arrGraphList = new ArrayList<clsWebPOSReportBean>();

		clsWebPOSReportBean objPOSSaleBean = null;

		/*
		 * objPOSSaleBean = new clsWebPOSReportBean();
		 * objPOSSaleBean.setStrItemCode("APR");
		 * objPOSSaleBean.setStrItemName("APR");
		 * objPOSSaleBean.setDblSettlementAmt(0);
		 * mapLineGraphData.put("APR",objPOSSaleBean);
		 * 
		 * objPOSSaleBean = new clsWebPOSReportBean();
		 * objPOSSaleBean.setStrItemCode("MAY");
		 * objPOSSaleBean.setStrItemName("MAY");
		 * objPOSSaleBean.setDblSettlementAmt(0);
		 * mapLineGraphData.put("MAY",objPOSSaleBean);
		 * 
		 * objPOSSaleBean = new clsWebPOSReportBean();
		 * objPOSSaleBean.setStrItemCode("JUN");
		 * objPOSSaleBean.setStrItemName("JUN");
		 * objPOSSaleBean.setDblSettlementAmt(0);
		 * mapLineGraphData.put("JUN",objPOSSaleBean);
		 * 
		 * objPOSSaleBean = new clsWebPOSReportBean();
		 * objPOSSaleBean.setStrItemCode("JUL");
		 * objPOSSaleBean.setStrItemName("JUL");
		 * objPOSSaleBean.setDblSettlementAmt(0);
		 * mapLineGraphData.put("JUL",objPOSSaleBean);
		 * 
		 * objPOSSaleBean = new clsWebPOSReportBean();
		 * objPOSSaleBean.setStrItemCode("AUG");
		 * objPOSSaleBean.setStrItemName("AUG");
		 * objPOSSaleBean.setDblSettlementAmt(0);
		 * mapLineGraphData.put("AUG",objPOSSaleBean);
		 * 
		 * 
		 * objPOSSaleBean = new clsWebPOSReportBean();
		 * objPOSSaleBean.setStrItemCode("SEP");
		 * objPOSSaleBean.setStrItemName("SEP");
		 * objPOSSaleBean.setDblSettlementAmt(0);
		 * mapLineGraphData.put("SEP",objPOSSaleBean);
		 * 
		 * 
		 * objPOSSaleBean = new clsWebPOSReportBean();
		 * objPOSSaleBean.setStrItemCode("OCT");
		 * objPOSSaleBean.setStrItemName("OCT");
		 * objPOSSaleBean.setDblSettlementAmt(0);
		 * mapLineGraphData.put("OCT",objPOSSaleBean);
		 * 
		 * 
		 * objPOSSaleBean = new clsWebPOSReportBean();
		 * objPOSSaleBean.setStrItemCode("SEP");
		 * objPOSSaleBean.setStrItemName("SEP");
		 * objPOSSaleBean.setDblSettlementAmt(0);
		 * mapLineGraphData.put("SEP",objPOSSaleBean);
		 * 
		 * 
		 * objPOSSaleBean = new clsWebPOSReportBean();
		 * objPOSSaleBean.setStrItemCode("NOV");
		 * objPOSSaleBean.setStrItemName("NOV");
		 * objPOSSaleBean.setDblSettlementAmt(0);
		 * mapLineGraphData.put("NOV",objPOSSaleBean);
		 * 
		 * objPOSSaleBean = new clsWebPOSReportBean();
		 * objPOSSaleBean.setStrItemCode("DEC");
		 * objPOSSaleBean.setStrItemName("DEC");
		 * objPOSSaleBean.setDblSettlementAmt(0);
		 * mapLineGraphData.put("DEC",objPOSSaleBean);
		 * 
		 * objPOSSaleBean = new clsWebPOSReportBean();
		 * objPOSSaleBean.setStrItemCode("JAN");
		 * objPOSSaleBean.setStrItemName("JAN");
		 * objPOSSaleBean.setDblSettlementAmt(0);
		 * mapLineGraphData.put("JAN",objPOSSaleBean);
		 * 
		 * objPOSSaleBean = new clsWebPOSReportBean();
		 * objPOSSaleBean.setStrItemCode("FEB");
		 * objPOSSaleBean.setStrItemName("FEB");
		 * objPOSSaleBean.setDblSettlementAmt(0);
		 * mapLineGraphData.put("FEB",objPOSSaleBean);
		 * 
		 * objPOSSaleBean = new clsWebPOSReportBean();
		 * objPOSSaleBean.setStrItemCode("MAR");
		 * objPOSSaleBean.setStrItemName("MAR");
		 * objPOSSaleBean.setDblSettlementAmt(0);
		 * mapLineGraphData.put("MAR",objPOSSaleBean);
		 */

		if (null != jArrSearchList) {
			for (int i = 0; i < jArrSearchList.size(); i++) {
				JSONObject jsonObject = (JSONObject) jArrSearchList.get(i);
				objPOSSaleBean = new clsWebPOSReportBean();

				objPOSSaleBean.setStrItemCode(jsonObject.get("MonthName").toString());
				objPOSSaleBean.setStrItemName(jsonObject.get("MonthName").toString());
				objPOSSaleBean.setDblAmount((Math.rint(Double.parseDouble(jsonObject.get("TotalPurchaseAmt").toString())))); // purchase
																																// Amount
				objPOSSaleBean.setDblSettlementAmt(Math.rint(Double.parseDouble(jsonObject.get("TotalSaleAmt").toString()))); // sale
																																// Amount

				arrGraphList.add(objPOSSaleBean);
			}
		}

		objBean.setArrGraphList(arrGraphList);

		return objBean;
	}
}