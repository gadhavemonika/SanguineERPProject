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
public class clsPOSComparisonwiseDashboardController {
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobalFunctionsController;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	Map<String, String> hmPOSData;

	@RequestMapping(value = "/frmPOSComparisonwiseDashboard", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsPOSDashboardBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {
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

		hmPOSData = new HashMap<String, String>();
		JSONArray jArryPosList = objPOSGlobalFunctionsController.funGetAllPOSForMaster(strClientCode);
		for (int i = 0; i < jArryPosList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryPosList.get(i);
			poslist.add(josnObjRet.get("strPosName"));
			hmPOSData.put(josnObjRet.get("strPosName").toString(), josnObjRet.get("strPosCode").toString());
		}
		model.put("posList", poslist);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSComparisonwiseDashboard_1", "command", new clsPOSDashboardBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSComparisonwiseDashboard", "command", new clsPOSDashboardBean());
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/loadComparisonwiseDashboardForPOS" }, method = RequestMethod.GET)
	@ResponseBody
	public clsPOSDashboardBean FunLoadPOSWiseSalesReport(HttpServletRequest req) {
		LinkedHashMap resMap = new LinkedHashMap();
		clsPOSDashboardBean objBean = new clsPOSDashboardBean();

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String fromDate = req.getParameter("fromDate");

		String toDate = req.getParameter("toDate");

		String strReportType = req.getParameter("reportType");
		String strPOSName = "";

		String posCode = "ALL";

		if (!strPOSName.equalsIgnoreCase("ALL")) {
			posCode = hmPOSData.get(strPOSName);
		}

		objBean = FunGetPOSData(clientCode, fromDate, toDate, strReportType, posCode);

		return objBean;
	}

	private clsPOSDashboardBean FunGetPOSData(String clientCode, String fromDate, String toDate, String strReportType, String posCode) {
		HashMap<String, clsWebPOSReportBean> mapData = new HashMap<String, clsWebPOSReportBean>();
		double totalSettleAmt = 0;
		clsPOSDashboardBean objBean = new clsPOSDashboardBean();

		String fromDate1 = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];

		String toDate1 = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];

		JSONObject jObjFillter = new JSONObject();
		jObjFillter.put("fromDate", fromDate1);
		jObjFillter.put("toDate", toDate1);
		jObjFillter.put("reportType", strReportType);
		JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funGetComparisonwiseDashboardDtl", jObjFillter);

		JSONArray jArrSearchList = (JSONArray) jObj.get("jArr");
		JSONObject objtotal = (JSONObject) jObj.get("jObjTotal");

		List<clsWebPOSReportBean> arrMonthList = new ArrayList<clsWebPOSReportBean>();
		List<clsWebPOSReportBean> arrGraphList = new ArrayList<clsWebPOSReportBean>();
		List<clsWebPOSReportBean> arrItemList = new ArrayList<clsWebPOSReportBean>();
		HashMap<String, clsWebPOSReportBean> mapMonth = new LinkedHashMap();
		clsWebPOSReportBean objPOSSaleBean = null;
		clsWebPOSReportBean objPOSSaleBean1 = null;

		if (null != jArrSearchList) {
			for (int i = 0; i < jArrSearchList.size(); i++) {
				JSONObject jsonObject = (JSONObject) jArrSearchList.get(i);
				objPOSSaleBean1 = new clsWebPOSReportBean();
				objPOSSaleBean1.setStrItemName(jsonObject.get("POSName").toString());
				arrItemList.add(objPOSSaleBean1);

				JSONArray jArrMontwiseDataList = (JSONArray) jsonObject.get("MonthDtls");

				if (null != jArrMontwiseDataList) {
					for (int cnt = 0; cnt < jArrMontwiseDataList.size(); cnt++) {
						JSONObject jsonObject1 = (JSONObject) jArrMontwiseDataList.get(cnt);
						objPOSSaleBean = new clsWebPOSReportBean();
						objPOSSaleBean.setStrItemCode(jsonObject.get("POSName").toString());
						objPOSSaleBean.setStrItemName(jsonObject1.get("MonthName").toString());
						double totalAmt = 0;
						totalAmt = Double.parseDouble(jsonObject1.get("TotalSaleAmt").toString());
						objPOSSaleBean.setDblAmount(totalAmt);
						arrGraphList.add(objPOSSaleBean);

						if (mapMonth.size() > 0) {
							if (mapMonth.containsKey(jsonObject1.get("MonthName").toString())) {

							} else {
								clsWebPOSReportBean objMonth = new clsWebPOSReportBean();
								objMonth.setStrItemName(jsonObject1.get("MonthName").toString());
								mapMonth.put(jsonObject1.get("MonthName").toString(), objMonth);
							}
						} else {
							clsWebPOSReportBean objMonth = new clsWebPOSReportBean();
							objMonth.setStrItemName(jsonObject1.get("MonthName").toString());
							mapMonth.put(jsonObject1.get("MonthName").toString(), objMonth);
						}
					}
				}

			}
		}

		if (mapMonth.size() > 0) {
			for (Map.Entry<String, clsWebPOSReportBean> entry : mapMonth.entrySet()) {
				clsWebPOSReportBean obj = entry.getValue();
				arrMonthList.add(obj);
			}
		}

		objBean.setArrItemList(arrItemList);
		objBean.setArrGraphList(arrGraphList);
		objBean.setArrMonthList(arrMonthList);

		return objBean;
	}

	/*
	 * 
	 * @SuppressWarnings({ "rawtypes", "unchecked" })
	 * 
	 * @RequestMapping(value={"/loadComparisonwiseDashboard"},
	 * method=RequestMethod.GET)
	 * 
	 * @ResponseBody public clsPOSDashboardBean
	 * FunLoadPOSWiseGroupwiseSalesReport(HttpServletRequest req) {
	 * LinkedHashMap resMap = new LinkedHashMap(); clsPOSDashboardBean
	 * objBean=new clsPOSDashboardBean();
	 * 
	 * String clientCode=req.getSession().getAttribute("clientCode").toString();
	 * 
	 * String fromDate=req.getParameter("fromDate");
	 * 
	 * String toDate=req.getParameter("toDate");
	 * 
	 * String strReportType=req.getParameter("reportType"); String
	 * strPOSName="";
	 * 
	 * String posCode= "ALL";
	 * 
	 * if(!strPOSName.equalsIgnoreCase("ALL")) { posCode=
	 * hmPOSData.get(strPOSName); }
	 * 
	 * objBean=FunGetPOSData(clientCode,fromDate,toDate,strReportType,posCode);
	 * 
	 * return objBean; }
	 * 
	 * 
	 * 
	 * private clsPOSDashboardBean FunGetData(String clientCode, String
	 * fromDate,String toDate, String strReportType,String posCode) {
	 * HashMap<String,clsWebPOSReportBean> mapData=new
	 * HashMap<String,clsWebPOSReportBean>(); double totalSettleAmt=0;
	 * clsPOSDashboardBean objBean = new clsPOSDashboardBean();
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
	 * jObjFillter.put("reportType", strReportType); JSONObject jObj =
	 * objGlobalFunctions
	 * .funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController
	 * .POSWSURL+"/WebPOSReport/funGetComparisonwiseDashboardDtl",jObjFillter);
	 * 
	 * JSONArray jArrSearchList=(JSONArray) jObj.get("jArr"); JSONObject
	 * objtotal =(JSONObject) jObj.get("jObjTotal");
	 * 
	 * clsWebPOSReportBean objPOSSaleBean = null; clsWebPOSReportBean
	 * objPOSSaleBean1 = null;
	 * 
	 * 
	 * List<clsWebPOSReportBean> arrMonthList=new
	 * ArrayList<clsWebPOSReportBean>(); List<clsWebPOSReportBean>
	 * arrGroupDtlList=new ArrayList<clsWebPOSReportBean>();
	 * List<clsWebPOSReportBean> arrPOSList=new
	 * ArrayList<clsWebPOSReportBean>(); HashMap<String,clsWebPOSReportBean>
	 * mapMonth=new LinkedHashMap(); HashMap<String,clsWebPOSReportBean>
	 * mapGroup=new LinkedHashMap(); clsWebPOSReportBean objPOSBean = null;
	 * clsWebPOSReportBean objMonthBean = null; clsWebPOSReportBean objGroupBean
	 * = null;
	 * 
	 * if(null!=jArrSearchList) { for(int i=0;i<jArrSearchList.size();i++) {
	 * JSONObject jsonPOSObj = (JSONObject) jArrSearchList.get(i);
	 * objPOSBean=new clsWebPOSReportBean();
	 * objPOSBean.setStrItemCode(jsonPOSObj.get("POSCode").toString());
	 * arrPOSList.add(objPOSBean);
	 * 
	 * 
	 * JSONArray jArrMontwiseDataList=(JSONArray) jsonPOSObj.get("MonthDtls");
	 * 
	 * if(null!=jArrMontwiseDataList) { for(int
	 * cnt=0;cnt<jArrMontwiseDataList.size();cnt++) { JSONObject jsonMonthObj =
	 * (JSONObject) jArrMontwiseDataList.get(cnt); objMonthBean = new
	 * clsWebPOSReportBean();
	 * 
	 * //********************for month map if(mapMonth.size()>0) {
	 * if(mapMonth.containsKey(jsonMonthObj.get("MonthName").toString())) {
	 * 
	 * } else { objMonthBean = new clsWebPOSReportBean();
	 * objMonthBean.setStrItemName(jsonMonthObj.get("MonthName").toString());
	 * mapMonth.put(jsonMonthObj.get("MonthName").toString(),objMonthBean); } }
	 * else { objMonthBean = new clsWebPOSReportBean();
	 * objMonthBean.setStrItemName(jsonMonthObj.get("MonthName").toString());
	 * mapMonth.put(jsonMonthObj.get("MonthName").toString(),objMonthBean); }
	 * 
	 * 
	 * 
	 * //***************************for group details
	 * 
	 * 
	 * 
	 * JSONArray jArrGroupWiseDataList=(JSONArray)
	 * jsonMonthObj.get("GroupDtls");
	 * 
	 * if(null!=jArrGroupWiseDataList) { for(int
	 * count=0;count<jArrGroupWiseDataList.size();count++) { JSONObject
	 * jsonGroupObj = (JSONObject) jArrGroupWiseDataList.get(count);
	 * objGroupBean = new clsWebPOSReportBean();
	 * 
	 * //********************for month map if(mapGroup.size()>0) {
	 * if(mapGroup.containsKey(jsonGroupObj.get("GroupCode").toString())) {
	 * 
	 * } else { clsWebPOSReportBean objGroup = new clsWebPOSReportBean();
	 * objGroup.setStrItemCode(jsonGroupObj.get("GroupCode").toString());
	 * objGroup.setStrItemName(jsonGroupObj.get("GroupName").toString());
	 * objGroup.setStrPOSName(jsonGroupObj.get("POSName").toString());
	 * objGroup.setDblAmount
	 * (Double.valueOf(jsonGroupObj.get("TotalSaleAmt").toString()));
	 * mapGroup.put(jsonGroupObj.get("GroupCode").toString(),objGroup); } } else
	 * { objGroupBean = new clsWebPOSReportBean();
	 * objGroupBean.setStrItemCode(jsonGroupObj.get("GroupCode").toString());
	 * objGroupBean.setStrItemName(jsonGroupObj.get("GroupName").toString());
	 * objGroupBean.setStrPOSName(jsonGroupObj.get("POSName").toString());
	 * objGroupBean
	 * .setDblAmount(Double.valueOf(jsonGroupObj.get("TotalSaleAmt").
	 * toString()));
	 * mapGroup.put(jsonGroupObj.get("GroupCode").toString(),objGroupBean); }
	 * 
	 * 
	 * 
	 * //***************************for group details
	 * 
	 * } }
	 * 
	 * } }
	 * 
	 * } }
	 * 
	 * 
	 * if(mapMonth.size()>0) { for (Map.Entry<String, clsWebPOSReportBean> entry
	 * : mapMonth.entrySet()) { objMonthBean = entry.getValue();
	 * arrMonthList.add(objMonthBean); } }
	 * 
	 * if(mapGroup.size()>0) { for (Map.Entry<String, clsWebPOSReportBean> entry
	 * : mapGroup.entrySet()) { objGroupBean = entry.getValue();
	 * arrGroupDtlList.add(objGroupBean); } }
	 * 
	 * objBean.setArrItemList(arrPOSList);
	 * objBean.setArrGraphList(arrGroupDtlList);
	 * objBean.setArrMonthList(arrMonthList);
	 * 
	 * JSONObject jObjData=new JSONObject();
	 * 
	 * jObjData.put("jArr",jArrSearchList);
	 * 
	 * return objBean; }
	 */

	@RequestMapping(value = "/loadComparisonwiseDashboard", method = RequestMethod.GET)
	public @ResponseBody JSONObject funLoadUnsettleBillDtlData(HttpServletRequest req) {
		List listmain = new ArrayList();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String fromDate = req.getParameter("fromDate");
		String toDate = req.getParameter("toDate");
		String strReportType = req.getParameter("reportType");

		String fromDate1 = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];
		String toDate1 = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];

		JSONObject jObjFillter = new JSONObject();
		jObjFillter.put("fromDate", fromDate1);
		jObjFillter.put("toDate", toDate1);
		jObjFillter.put("reportType", strReportType);
		JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funGetComparisonwiseDashboardDtl", jObjFillter);

		JSONArray jArrSearchList = (JSONArray) jObj.get("jArr");
		JSONObject jObjData = new JSONObject();
		JSONArray jArrPOSList = new JSONArray();

		if (null != jArrSearchList) {
			for (int i = 0; i < jArrSearchList.size(); i++) {
				JSONObject jsonPOSObj = (JSONObject) jArrSearchList.get(i);
				JSONObject objPOS = new JSONObject();
				objPOS.put("POSCode", jsonPOSObj.get("POSCode").toString());
				JSONArray arrGroupDtls = new JSONArray();
				JSONArray jArrMontwiseDataList = (JSONArray) jsonPOSObj.get("MonthDtls");

				if (null != jArrMontwiseDataList) {
					for (int cnt = 0; cnt < jArrMontwiseDataList.size(); cnt++) {
						JSONObject jsonMonthObj = (JSONObject) jArrMontwiseDataList.get(cnt);
						// ***************************for group details

						JSONArray jArrGroupWiseDataList = (JSONArray) jsonMonthObj.get("GroupDtls");

						if (null != jArrGroupWiseDataList) {
							for (int count = 0; count < jArrGroupWiseDataList.size(); count++) {
								JSONObject jsonGroupObj = (JSONObject) jArrGroupWiseDataList.get(count);
								JSONObject jObjGroup = new JSONObject();
								jObjGroup.put("GroupCode", jsonGroupObj.get("GroupCode").toString());
								jObjGroup.put("GroupName", jsonGroupObj.get("GroupName").toString());
								jObjGroup.put("TotalSaleAmt", Double.valueOf(jsonGroupObj.get("TotalSaleAmt").toString()));
								arrGroupDtls.add(jObjGroup);

							}
						}

					}
				}

				objPOS.put("GroupDtls", arrGroupDtls);
				jArrPOSList.add(objPOS);

			}
		}

		jObjData.put("jArr", jArrSearchList);
		jObjData.put("POSDetailArrList", jArrPOSList);

		return jObjData;

	}

}