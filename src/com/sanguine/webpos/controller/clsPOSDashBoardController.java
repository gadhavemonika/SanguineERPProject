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
public class clsPOSDashBoardController {
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobalFunctionsController;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	Map<String, String> hmPOSData;

	@RequestMapping(value = "/frmPOSDashboard", method = RequestMethod.GET)
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
			return new ModelAndView("frmPOSDashboard_1", "command", new clsPOSDashboardBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSDashboard", "command", new clsPOSDashboardBean());
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/loadPOSWiseSalesReportForDashboard" }, method = RequestMethod.GET)
	@ResponseBody
	public clsPOSDashboardBean FunLoadPOSWiseSalesReport(HttpServletRequest req) {
		LinkedHashMap resMap = new LinkedHashMap();
		clsPOSDashboardBean objBean = new clsPOSDashboardBean();

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String fromDate = req.getParameter("fromDate");

		String toDate = req.getParameter("toDate");

		String strReportType = req.getParameter("strReportTypedata");
		String strPOSName = req.getParameter("strPOSName");

		String posCode = "ALL";

		if (!strPOSName.equalsIgnoreCase("ALL")) {
			posCode = hmPOSData.get(strPOSName);
		}

		objBean = FunGetData(clientCode, fromDate, toDate, strReportType, posCode);

		return objBean;
	}

	private clsPOSDashboardBean FunGetData(String clientCode, String fromDate, String toDate, String strReportType, String posCode) {
		HashMap<String, clsWebPOSReportBean> mapChartData = new HashMap<String, clsWebPOSReportBean>();
		double totalSettleAmt = 0;
		clsPOSDashboardBean objBean = new clsPOSDashboardBean();

		String fromDate1 = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];

		String toDate1 = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];

		JSONObject jObjFillter = new JSONObject();
		jObjFillter.put("fromDate", fromDate1);
		jObjFillter.put("toDate", toDate1);
		jObjFillter.put("strReportType", strReportType);
		jObjFillter.put("POSCode", posCode);

		JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funGetPOSDashboardSalesReport", jObjFillter);

		JSONArray jArrSearchList = (JSONArray) jObj.get("jArr");
		JSONObject objtotal = (JSONObject) jObj.get("jObjTotal");
		List<clsWebPOSReportBean> arrGraphList = new ArrayList<clsWebPOSReportBean>();
		if (strReportType.equalsIgnoreCase("POS Wise")) {
			if (null != jArrSearchList) {
				for (int i = 0; i < jArrSearchList.size(); i++) {
					JSONObject jsonObject = (JSONObject) jArrSearchList.get(i);
					clsWebPOSReportBean objPOSSaleBean = new clsWebPOSReportBean();
					objPOSSaleBean.setStrPOSCode(jsonObject.get("POSCode").toString());
					objPOSSaleBean.setStrPOSName(jsonObject.get("POSName").toString());
					objPOSSaleBean.setDblSettlementAmt(Math.rint(Double.parseDouble(jsonObject.get("SettleAmt").toString())));
					objPOSSaleBean.setDblGrandTotal(Math.rint(Double.parseDouble(jsonObject.get("GrandAmt").toString())));
					arrGraphList.add(objPOSSaleBean);
				}
			}
		} else if (strReportType.equalsIgnoreCase("Month Wise")) {
			HashMap<String, clsWebPOSReportBean> mapLineGraphData = new LinkedHashMap();
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
					objPOSSaleBean.setDblSettlementAmt(Math.rint(Double.parseDouble(jsonObject.get("TotalAmt").toString())));
					arrGraphList.add(objPOSSaleBean);

					/*
					 * if(mapLineGraphData.size()>0) {
					 * if(mapLineGraphData.containsKey
					 * (jsonObject.get("MonthName").toString())) {
					 * objPOSSaleBean =
					 * mapLineGraphData.get(jsonObject.get("MonthName"
					 * ).toString()); double
					 * amt=Double.valueOf(objPOSSaleBean.getDblSettlementAmt());
					 * 
					 * JSONArray mJsonPosArray = (JSONArray)
					 * jsonObject.get("POSDtls"); JSONObject mJsonPosObject =
					 * new JSONObject(); double totalAmt=amt; for (int k = 0; k
					 * < mJsonPosArray.size(); k++) { mJsonPosObject =
					 * (JSONObject) mJsonPosArray.get(k);
					 * totalSettleAmt+=Double.
					 * parseDouble(mJsonPosObject.get("TotalAmt").toString());
					 * totalAmt
					 * +=Double.parseDouble(mJsonPosObject.get("TotalAmt"
					 * ).toString()); }
					 * objPOSSaleBean.setDblSettlementAmt(totalAmt);
					 * mapLineGraphData
					 * .put(jsonObject.get("MonthName").toString(
					 * ),objPOSSaleBean);
					 * 
					 * } else { objPOSSaleBean = new clsWebPOSReportBean();
					 * objPOSSaleBean
					 * .setStrItemCode(jsonObject.get("MonthName").toString());
					 * objPOSSaleBean
					 * .setStrItemName(jsonObject.get("MonthName").toString());
					 * JSONArray mJsonPosArray = (JSONArray)
					 * jsonObject.get("POSDtls"); JSONObject mJsonPosObject =
					 * new JSONObject(); double totalAmt=0; for (int k = 0; k <
					 * mJsonPosArray.size(); k++) { mJsonPosObject =
					 * (JSONObject) mJsonPosArray.get(k);
					 * totalSettleAmt+=Double.
					 * parseDouble(mJsonPosObject.get("TotalAmt").toString());
					 * totalAmt
					 * +=Double.parseDouble(mJsonPosObject.get("TotalAmt"
					 * ).toString()); }
					 * objPOSSaleBean.setDblSettlementAmt(totalAmt);
					 * mapLineGraphData
					 * .put(jsonObject.get("MonthName").toString(
					 * ),objPOSSaleBean); }
					 * 
					 * } else { objPOSSaleBean = new clsWebPOSReportBean();
					 * objPOSSaleBean
					 * .setStrItemCode(jsonObject.get("MonthName").toString());
					 * objPOSSaleBean
					 * .setStrItemName(jsonObject.get("MonthName").toString());
					 * JSONArray mJsonPosArray = (JSONArray)
					 * jsonObject.get("POSDtls"); JSONObject mJsonPosObject =
					 * new JSONObject(); double totalAmt=0; for (int k = 0; k <
					 * mJsonPosArray.size(); k++) { mJsonPosObject =
					 * (JSONObject) mJsonPosArray.get(k);
					 * totalSettleAmt+=Double.
					 * parseDouble(mJsonPosObject.get("TotalAmt").toString());
					 * totalAmt
					 * +=Double.parseDouble(mJsonPosObject.get("TotalAmt"
					 * ).toString()); }
					 * objPOSSaleBean.setDblSettlementAmt(totalAmt);
					 * mapLineGraphData
					 * .put(jsonObject.get("MonthName").toString(
					 * ),objPOSSaleBean);
					 * 
					 * } }
					 * 
					 * if(mapLineGraphData.size()>0) { for (Map.Entry<String,
					 * clsWebPOSReportBean> entry : mapLineGraphData.entrySet())
					 * { clsWebPOSReportBean obj = entry.getValue();
					 * arrGraphList.add(obj);
					 * 
					 * } }
					 */

				}
			}

		} else if (strReportType.equalsIgnoreCase("Operation Wise")) {
			clsWebPOSReportBean objPOSSaleBean = null;
			if (null != jArrSearchList) {
				for (int i = 0; i < jArrSearchList.size(); i++) {
					JSONObject jsonObject = (JSONObject) jArrSearchList.get(i);
					objPOSSaleBean = new clsWebPOSReportBean();
					objPOSSaleBean.setStrItemCode(jsonObject.get("Operation Type").toString());
					objPOSSaleBean.setStrItemName(jsonObject.get("Operation Type").toString());
					objPOSSaleBean.setDblSettlementAmt(Math.rint(Double.parseDouble(jsonObject.get("SettleAmt").toString())));
					arrGraphList.add(objPOSSaleBean);
				}
			}
		} else {
			String code = "", name = "";
			if (strReportType.equalsIgnoreCase("Group Wise")) {
				code = "GroupCode";
				name = "GroupName";
			} else if (strReportType.equalsIgnoreCase("Subgroup Wise")) {
				code = "SubGroupCode";
				name = "SubGroupName";
			} else if (strReportType.equalsIgnoreCase("Menu Wise")) {
				code = "MenuCode";
				name = "MenuName";
			} else if (strReportType.equalsIgnoreCase("Item Wise")) {
				code = "ItemCode";
				name = "ItemName";
			}

			if (null != jArrSearchList) {
				for (int i = 0; i < jArrSearchList.size(); i++) {
					JSONObject jsonObject = (JSONObject) jArrSearchList.get(i);
					clsWebPOSReportBean objPOSSaleBean = null;
					if (mapChartData.size() > 0) {
						if (mapChartData.containsKey(jsonObject.get(code).toString())) {
							objPOSSaleBean = mapChartData.get(jsonObject.get(code).toString());
							double amt = Double.valueOf(objPOSSaleBean.getDblSettlementAmt());

							JSONArray mJsonPosArray = (JSONArray) jsonObject.get("POSDtls");
							JSONObject mJsonPosObject = new JSONObject();
							double totalAmt = amt;
							for (int k = 0; k < mJsonPosArray.size(); k++) {
								mJsonPosObject = (JSONObject) mJsonPosArray.get(k);
								totalSettleAmt += Double.parseDouble(mJsonPosObject.get("TotalAmt").toString());
								totalAmt += Double.parseDouble(mJsonPosObject.get("TotalAmt").toString());
							}
							objPOSSaleBean.setDblSettlementAmt(totalAmt);
							mapChartData.put(jsonObject.get(code).toString(), objPOSSaleBean);

						} else {
							objPOSSaleBean = new clsWebPOSReportBean();
							objPOSSaleBean.setStrItemCode(jsonObject.get(code).toString());
							objPOSSaleBean.setStrItemName(jsonObject.get(name).toString());
							JSONArray mJsonPosArray = (JSONArray) jsonObject.get("POSDtls");
							JSONObject mJsonPosObject = new JSONObject();
							double totalAmt = 0;
							for (int k = 0; k < mJsonPosArray.size(); k++) {
								mJsonPosObject = (JSONObject) mJsonPosArray.get(k);
								totalSettleAmt += Double.parseDouble(mJsonPosObject.get("TotalAmt").toString());
								totalAmt += Double.parseDouble(mJsonPosObject.get("TotalAmt").toString());
							}
							objPOSSaleBean.setDblSettlementAmt(totalAmt);
							mapChartData.put(jsonObject.get(code).toString(), objPOSSaleBean);
						}

					} else {
						objPOSSaleBean = new clsWebPOSReportBean();
						objPOSSaleBean.setStrItemCode(jsonObject.get(code).toString());
						objPOSSaleBean.setStrItemName(jsonObject.get(name).toString());
						JSONArray mJsonPosArray = (JSONArray) jsonObject.get("POSDtls");
						JSONObject mJsonPosObject = new JSONObject();
						double totalAmt = 0;
						for (int k = 0; k < mJsonPosArray.size(); k++) {
							mJsonPosObject = (JSONObject) mJsonPosArray.get(k);
							totalSettleAmt += Double.parseDouble(mJsonPosObject.get("TotalAmt").toString());
							totalAmt += Double.parseDouble(mJsonPosObject.get("TotalAmt").toString());
						}
						objPOSSaleBean.setDblSettlementAmt(totalAmt);
						mapChartData.put(jsonObject.get(code).toString(), objPOSSaleBean);

					}

				}

				if (mapChartData.size() > 0) {
					for (Map.Entry<String, clsWebPOSReportBean> entry : mapChartData.entrySet()) {
						clsWebPOSReportBean obj = entry.getValue();
						arrGraphList.add(obj);

					}
				}

			}
		}

		objBean.setArrGraphList(arrGraphList);

		return objBean;
	}

}