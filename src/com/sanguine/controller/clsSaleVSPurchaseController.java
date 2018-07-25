package com.sanguine.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.util.clsPOSDashboardBean;
import com.sanguine.webpos.controller.clsPOSGlobalFunctionsController;

@Controller
public class clsSaleVSPurchaseController {
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@RequestMapping(value = "/frmSaleVSPurchase", method = RequestMethod.GET)
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
			return new ModelAndView("frmSaleVSPurchase_1", "command", new clsPOSDashboardBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSaleVSPurchase", "command", new clsPOSDashboardBean());
		} else {
			return null;
		}
	}

	/*
	 * @SuppressWarnings({ "rawtypes", "unchecked" })
	 * 
	 * @RequestMapping(value={"/loadSaleVSPurchaseDtl"},
	 * method=RequestMethod.GET)
	 * 
	 * @ResponseBody public clsPOSDashboardBean
	 * FunLoadPOSWiseSalesReport(HttpServletRequest req) { LinkedHashMap resMap
	 * = new LinkedHashMap(); clsPOSDashboardBean objBean=new
	 * clsPOSDashboardBean();
	 * 
	 * String clientCode=req.getSession().getAttribute("clientCode").toString();
	 * 
	 * String fromDate=req.getParameter("fromDate");
	 * 
	 * String toDate=req.getParameter("toDate");
	 * 
	 * objBean=FunGetData(clientCode,fromDate,toDate);
	 * 
	 * return objBean; }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * private clsPOSDashboardBean FunGetData(String clientCode, String
	 * fromDate,String toDate) { clsPOSDashboardBean objBean = new
	 * clsPOSDashboardBean();
	 * 
	 * String
	 * fromDate1=fromDate.split("-")[2]+"-"+fromDate.split("-")[1]+"-"+fromDate
	 * .split("-")[0];
	 * 
	 * String
	 * toDate1=toDate.split("-")[2]+"-"+toDate.split("-")[1]+"-"+toDate.split
	 * ("-")[0];
	 * 
	 * JSONObject jObjFillter = new JSONObject(); jObjFillter.put("fromDate",
	 * fromDate1); jObjFillter.put("toDate", toDate1);
	 * 
	 * JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(
	 * "http://localhost:8080/prjSanguineWebService/WebPOSReport/funGetSalePurchaseComparisonDtl"
	 * ,jObjFillter);
	 * 
	 * JSONArray jArrSearchList=(JSONArray) jObj.get("jArr"); JSONObject
	 * objtotal =(JSONObject) jObj.get("jObjTotal"); List<clsWebPOSReportBean>
	 * arrGraphList=new ArrayList<clsWebPOSReportBean>();
	 * 
	 * clsWebPOSReportBean objPOSSaleBean = null;
	 * 
	 * 
	 * 
	 * if(null!=jArrSearchList) { for(int i=0;i<jArrSearchList.size();i++) {
	 * JSONObject jsonObject = (JSONObject) jArrSearchList.get(i);
	 * objPOSSaleBean=new clsWebPOSReportBean();
	 * if(jsonObject.get("MonthName").toString().equals("January")) {
	 * objPOSSaleBean.setStrItemCode("JAN");
	 * objPOSSaleBean.setStrItemName("JAN"); } else
	 * if(jsonObject.get("MonthName").toString().equals("February")) {
	 * objPOSSaleBean.setStrItemCode("FEB");
	 * objPOSSaleBean.setStrItemName("FEB"); } else
	 * if(jsonObject.get("MonthName").toString().equals("March")) {
	 * objPOSSaleBean.setStrItemCode("MAR");
	 * objPOSSaleBean.setStrItemName("MAR"); } else
	 * if(jsonObject.get("MonthName").toString().equals("April")) {
	 * objPOSSaleBean.setStrItemCode("APR");
	 * objPOSSaleBean.setStrItemName("APR"); } else
	 * if(jsonObject.get("MonthName").toString().equals("May")) {
	 * objPOSSaleBean.setStrItemCode("MAY");
	 * objPOSSaleBean.setStrItemName("MAY"); } else
	 * if(jsonObject.get("MonthName").toString().equals("June")) {
	 * objPOSSaleBean.setStrItemCode("JUN");
	 * objPOSSaleBean.setStrItemName("JUN"); } else
	 * if(jsonObject.get("MonthName").toString().equals("July")) {
	 * objPOSSaleBean.setStrItemCode("JUL");
	 * objPOSSaleBean.setStrItemName("JUL"); } else
	 * if(jsonObject.get("MonthName").toString().equals("August")) {
	 * objPOSSaleBean.setStrItemCode("AUG");
	 * objPOSSaleBean.setStrItemName("AUG"); } else
	 * if(jsonObject.get("MonthName").toString().equals("September")) {
	 * objPOSSaleBean.setStrItemCode("SEP");
	 * objPOSSaleBean.setStrItemName("SEP"); } else
	 * if(jsonObject.get("MonthName").toString().equals("October")) {
	 * objPOSSaleBean.setStrItemCode("OCT");
	 * objPOSSaleBean.setStrItemName("OCT"); } else
	 * if(jsonObject.get("MonthName").toString().equals("November")) {
	 * objPOSSaleBean.setStrItemCode("NOV");
	 * objPOSSaleBean.setStrItemName("NOV"); } else
	 * if(jsonObject.get("MonthName").toString().equals("December")) {
	 * objPOSSaleBean.setStrItemCode("DEC");
	 * objPOSSaleBean.setStrItemName("DEC"); }
	 * objPOSSaleBean.setDblAmount((Math.
	 * rint(Double.parseDouble(jsonObject.get("TotalPurchaseAmt"
	 * ).toString())))); //purchase Amount
	 * objPOSSaleBean.setDblSettlementAmt(Math
	 * .rint(Double.parseDouble(jsonObject.get("TotalSaleAmt").toString()))); //
	 * sale Amount
	 * 
	 * arrGraphList.add(objPOSSaleBean); } }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * objBean.setArrGraphList(arrGraphList);
	 * 
	 * return objBean; }
	 */
}