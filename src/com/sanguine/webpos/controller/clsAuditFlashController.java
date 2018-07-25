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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsAuditFlashBean;
import com.sanguine.webpos.bean.clsPhysicalStockFlashBean;
import com.sanguine.webpos.bean.clsWebPOSReportBean;

@Controller
public class clsAuditFlashController {

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobalFunctionsController;

	@Autowired
	private ServletContext servletContext;

	@RequestMapping(value = "/frmPOSAuditFlash", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOS");
		JSONArray jArryPosList = (JSONArray) jObj.get("posList");
		Map posList = new HashMap<>();
		posList.put("All", "All");
		if (null != jArryPosList) {
			for (int cnt = 0; cnt < jArryPosList.size(); cnt++) {
				JSONObject jObj1 = (JSONObject) jArryPosList.get(cnt);
				posList.put(jObj1.get("strPosCode").toString(), jObj1.get("strPosName").toString());
			}
		}
		model.put("posList", posList);

		Map userList = new HashMap<>();
		userList.put("All", "All");
		jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetAllUserName");
		jArryPosList = (JSONArray) jObj.get("userList");
		for (int i = 0; i < jArryPosList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryPosList.get(i);
			userList.put(josnObjRet.get("strUserCode"), josnObjRet.get("strUserName"));
		}
		model.put("userList", userList);

		Map Reasonlist = new HashMap<>();
		Reasonlist.put("All", "All");
		JSONArray jArryReasonList = objPOSGlobalFunctionsController.funGetAllReasonMaster(strClientCode);
		for (int i = 0; i < jArryReasonList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryReasonList.get(i);
			Reasonlist.put(josnObjRet.get("strReasonCode").toString(), josnObjRet.get("strReasonName").toString());
		}
		model.put("ReasonMasterList", Reasonlist);

		if ("2".equalsIgnoreCase(urlHits)) {

			return new ModelAndView("frmPOSAuditFlash_1", "command", new clsWebPOSReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSAuditFlash", "command", new clsWebPOSReportBean());
		} else {
			return null;
		}

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptPOSAuditFlash", method = RequestMethod.POST)
	private ModelAndView funReport(@ModelAttribute("command") clsWebPOSReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String fromDate = objBean.getFromDate();

		String toDate = objBean.getToDate();

		String strReportType = objBean.getStrReportType();
		String strUserName = objBean.getStrSGName();
		String posName = objBean.getStrPOSName();
		String strReason = objBean.getStrReasonMaster();
		String auditType = objBean.getStrPSPCode();
		Map resMap = new LinkedHashMap();

		resMap = FunGetData(clientCode, strUserName, fromDate, toDate, strReportType, strReason, posName, auditType);

		List ExportList = new ArrayList();

		String dteFromDate = objBean.getFromDate();
		String dteToDate = objBean.getToDate();
		String FileName = "AuditFlash_" + dteFromDate + "_To_" + dteToDate;

		ExportList.add(FileName);

		List List = (List) resMap.get("ColHeader");

		String[] headerList = new String[List.size()];
		for (int i = 0; i < List.size(); i++) {
			headerList[i] = (String) List.get(i);
		}

		ExportList.add(headerList);

		List dataList = (List) resMap.get("List");
		for (int i = 0; i < 2; i++) {
			List list = new ArrayList();
			for (int j = 0; i < List.size(); i++) {
				list.add(" ");
			}
			dataList.add(list);
		}

		List totalList = (List) resMap.get("totalList");
		dataList.add(totalList);

		ExportList.add(dataList);
		return new ModelAndView("excelViewWithReportName", "listWithReportName", ExportList);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/loadAuditFlash" }, method = RequestMethod.GET)
	@ResponseBody
	public Map FunLoadDaywiseSalesSummary1(HttpServletRequest req) {
		LinkedHashMap resMap = new LinkedHashMap();

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String strUserName = req.getParameter("strUserName");

		String fromDate = req.getParameter("fromDate");

		String toDate = req.getParameter("toDate");

		String strReportType = req.getParameter("strReportType");

		String posName = req.getParameter("posName");

		String strReason = req.getParameter("strReason");

		String auditType = req.getParameter("auditType");

		resMap = FunGetData(clientCode, strUserName, fromDate, toDate, strReportType, strReason, posName, auditType);
		return resMap;
	}

	@SuppressWarnings({ "unchecked" })
	private LinkedHashMap FunGetData(String clientCode, String strUserName, String fromDate, String toDate, String strReportType, String strReason, String posName, String auditType) {
		LinkedHashMap resMap = new LinkedHashMap();

		double amtTotal = 0, netTotal = 0, paxTotal = 0;

		List colHeader = new ArrayList();

		String fromDate1 = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];

		String toDate1 = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];

		JSONObject jObjFillter = new JSONObject();
		jObjFillter.put("fromDate", fromDate1);
		jObjFillter.put("toDate", toDate1);
		jObjFillter.put("userCode", strUserName);
		jObjFillter.put("posCode", posName);
		jObjFillter.put("strReportType", strReportType);
		jObjFillter.put("reasonCode", strReason);
		jObjFillter.put("auditType", auditType);
		jObjFillter.put("clientCode", clientCode);
		JSONObject jObj = new JSONObject();

		jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funGetAuditFlash", jObjFillter);

		JSONArray jColHeaderArr = (JSONArray) jObj.get("ColHeader");
		List list = new ArrayList();
		JSONArray jarr = (JSONArray) jObj.get("jArr");
		List totalList = new ArrayList();
		totalList.add("Total");
		if (null != jarr) {
			switch (auditType) {
			case "Modified Bill": {
				if (strReportType.equals("Summary")) {
					for (int i = 0; i < jarr.size(); i++) {
						JSONObject jObjtemp = (JSONObject) jarr.get(i);

						// clsAuditFlashBean objClsGroupWaiseSalesBean=new
						// clsAuditFlashBean();
						List arrList = new ArrayList();
						arrList.add(jObjtemp.get("BillNo").toString());
						arrList.add(jObjtemp.get("BillDate").toString());
						arrList.add(jObjtemp.get("ModifiedDate").toString());
						arrList.add(jObjtemp.get("EntryTime").toString());
						arrList.add(jObjtemp.get("ModifyTime").toString());
						arrList.add(Double.parseDouble(jObjtemp.get("BillAmt").toString()));
						arrList.add(Double.parseDouble(jObjtemp.get("NetAmt").toString()));
						arrList.add(jObjtemp.get("UserCreated").toString());
						arrList.add(jObjtemp.get("UserEdited").toString());
						arrList.add(jObjtemp.get("ReasonName").toString());
						list.add(arrList);

						amtTotal = amtTotal + (Double.parseDouble(jObjtemp.get("BillAmt").toString()));
						netTotal = netTotal + (Double.parseDouble(jObjtemp.get("NetAmt").toString()));

					}
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(amtTotal);
					totalList.add(netTotal);
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					resMap.put("List", list);
					resMap.put("totalList", totalList);
				} else {
					for (int i = 0; i < jarr.size(); i++) {
						JSONObject jObjtemp = (JSONObject) jarr.get(i);

						List arrList = new ArrayList();
						arrList.add(jObjtemp.get("BillNo").toString());
						arrList.add(jObjtemp.get("BillDate").toString());
						arrList.add(jObjtemp.get("ModifiedDate").toString());
						arrList.add(jObjtemp.get("EntryTime").toString());
						arrList.add(jObjtemp.get("ModifyTime").toString());
						arrList.add(jObjtemp.get("ItemName").toString());
						arrList.add(Double.parseDouble(jObjtemp.get("Qty").toString()));
						arrList.add(Double.parseDouble(jObjtemp.get("Amount").toString()));
						arrList.add(jObjtemp.get("UserCreated").toString());
						arrList.add(jObjtemp.get("UserEdited").toString());

						list.add(arrList);
						amtTotal = amtTotal + (Double.parseDouble(jObjtemp.get("Qty").toString()));
						netTotal = netTotal + (Double.parseDouble(jObjtemp.get("Amount").toString()));

					}
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(amtTotal);
					totalList.add(netTotal);
					totalList.add(" ");
					totalList.add(" ");
					resMap.put("List", list);
					resMap.put("totalList", totalList);
				}

			}
				break;
			case "Voided Bill": {
				if (strReportType.equals("Summary")) {
					for (int i = 0; i < jarr.size(); i++) {
						JSONObject jObjtemp = (JSONObject) jarr.get(i);

						List arrList = new ArrayList();
						arrList.add(jObjtemp.get("BillNo").toString());
						arrList.add(jObjtemp.get("BillDate").toString());
						arrList.add(jObjtemp.get("VoidedDate").toString());
						arrList.add(jObjtemp.get("EntryTime").toString());
						arrList.add(jObjtemp.get("VoidedTime").toString());
						arrList.add(Double.parseDouble(jObjtemp.get("Amount").toString()));
						arrList.add(jObjtemp.get("UserEdited").toString());
						arrList.add(jObjtemp.get("Reason").toString());
						list.add(arrList);
						amtTotal = amtTotal + (Double.parseDouble(jObjtemp.get("Amount").toString()));

					}
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");

					totalList.add(amtTotal);
					totalList.add(" ");
					totalList.add(" ");
					resMap.put("List", list);
					resMap.put("totalList", totalList);
				} else {
					for (int i = 0; i < jarr.size(); i++) {
						JSONObject jObjtemp = (JSONObject) jarr.get(i);

						List arrList = new ArrayList();
						arrList.add(jObjtemp.get("BillNo").toString());
						arrList.add(jObjtemp.get("BillDate").toString());
						arrList.add(jObjtemp.get("VodedDate").toString());
						arrList.add(jObjtemp.get("EntryTime").toString());
						arrList.add(jObjtemp.get("VoidedTime").toString());
						arrList.add(jObjtemp.get("ItemName").toString());
						arrList.add(Double.parseDouble(jObjtemp.get("Qty").toString()));
						arrList.add(Double.parseDouble(jObjtemp.get("Amount").toString()));

						arrList.add(jObjtemp.get("UserEdited").toString());
						arrList.add(jObjtemp.get("Reason").toString());

						list.add(arrList);
						amtTotal = amtTotal + (Double.parseDouble(jObjtemp.get("Qty").toString()));
						netTotal = netTotal + (Double.parseDouble(jObjtemp.get("Amount").toString()));

					}
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(amtTotal);
					totalList.add(netTotal);
					totalList.add(" ");
					totalList.add(" ");

					resMap.put("totalList", totalList);
					resMap.put("List", list);
				}

			}
				break;
			case "Voided Advanced Order": {
				if (strReportType.equals("Summary")) {
					for (int i = 0; i < jarr.size(); i++) {
						JSONObject jObjtemp = (JSONObject) jarr.get(i);

						List arrList = new ArrayList();
						arrList.add(jObjtemp.get("BillNo").toString());
						arrList.add(jObjtemp.get("BillDate").toString());
						arrList.add(jObjtemp.get("VoidedDate").toString());
						arrList.add(jObjtemp.get("EntryTime").toString());
						arrList.add(jObjtemp.get("VoidedTime").toString());
						arrList.add(Double.parseDouble(jObjtemp.get("Amount").toString()));
						arrList.add(jObjtemp.get("UserEdited").toString());
						arrList.add(jObjtemp.get("Reason").toString());
						list.add(arrList);
						amtTotal = amtTotal + (Double.parseDouble(jObjtemp.get("Amount").toString()));

					}
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(amtTotal);
					totalList.add(" ");
					totalList.add(" ");
					resMap.put("List", list);
					resMap.put("totalList", totalList);
				} else {
					for (int i = 0; i < jarr.size(); i++) {
						JSONObject jObjtemp = (JSONObject) jarr.get(i);

						List arrList = new ArrayList();
						arrList.add(jObjtemp.get("BillNo").toString());
						arrList.add(jObjtemp.get("BillDate").toString());
						arrList.add(jObjtemp.get("VodedDate").toString());
						arrList.add(jObjtemp.get("EntryTime").toString());
						arrList.add(jObjtemp.get("VoidedTime").toString());
						arrList.add(jObjtemp.get("ItemName").toString());
						arrList.add(Double.parseDouble(jObjtemp.get("Qty").toString()));
						arrList.add(Double.parseDouble(jObjtemp.get("Amount").toString()));

						arrList.add(jObjtemp.get("UserEdited").toString());
						arrList.add(jObjtemp.get("Reason").toString());
						list.add(arrList);
						amtTotal = amtTotal + (Double.parseDouble(jObjtemp.get("Qty").toString()));
						netTotal = netTotal + (Double.parseDouble(jObjtemp.get("Amount").toString()));

					}
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(amtTotal);
					totalList.add(netTotal);
					totalList.add(" ");
					totalList.add(" ");
					resMap.put("totalList", totalList);
					resMap.put("List", list);
				}

			}
				break;
			case "Line Void": {
				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jObjtemp = (JSONObject) jarr.get(i);

					List arrList = new ArrayList();
					arrList.add(jObjtemp.get("POS").toString());
					arrList.add(jObjtemp.get("Date").toString());
					arrList.add(jObjtemp.get("Time").toString());
					arrList.add(jObjtemp.get("ItemName").toString());

					arrList.add(Double.parseDouble(jObjtemp.get("Qty").toString()));
					arrList.add(Double.parseDouble(jObjtemp.get("Amt").toString()));
					arrList.add(jObjtemp.get("KOTNo").toString());
					arrList.add(jObjtemp.get("UserCreated").toString());
					list.add(arrList);
					amtTotal = amtTotal + (Double.parseDouble(jObjtemp.get("Qty").toString()));
					netTotal = netTotal + (Double.parseDouble(jObjtemp.get("Amt").toString()));

				}
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(amtTotal);
				totalList.add(netTotal);
				totalList.add(" ");
				totalList.add(" ");
				resMap.put("List", list);
				resMap.put("totalList", totalList);
			}
				break;
			case "Voided KOT": {
				if (strReportType.equals("Summary")) {
					for (int i = 0; i < jarr.size(); i++) {
						JSONObject jObjtemp = (JSONObject) jarr.get(i);

						List arrList = new ArrayList();
						arrList.add(jObjtemp.get("POS").toString());
						arrList.add(jObjtemp.get("Table").toString());
						arrList.add(jObjtemp.get("Waiter").toString());
						arrList.add(jObjtemp.get("KOTNo").toString());
						arrList.add(Double.parseDouble(jObjtemp.get("Pax").toString()));
						arrList.add(Double.parseDouble(jObjtemp.get("Amount").toString()));

						arrList.add(jObjtemp.get("Reason").toString());
						arrList.add(jObjtemp.get("UserCreated").toString());
						arrList.add(jObjtemp.get("DateCreated").toString());
						list.add(arrList);
						amtTotal = amtTotal + (Double.parseDouble(jObjtemp.get("Amount").toString()));
						paxTotal = paxTotal + (Double.parseDouble(jObjtemp.get("Pax").toString()));

					}
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(paxTotal);
					totalList.add(amtTotal);
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					resMap.put("totalList", totalList);
					resMap.put("List", list);
				}

				else {
					for (int i = 0; i < jarr.size(); i++) {
						JSONObject jObjtemp = (JSONObject) jarr.get(i);

						List arrList = new ArrayList();
						arrList.add(jObjtemp.get("POS").toString());
						arrList.add(jObjtemp.get("Table").toString());
						arrList.add(jObjtemp.get("Waiter").toString());
						arrList.add(jObjtemp.get("KOTNo").toString());
						arrList.add(jObjtemp.get("ItemName").toString());
						arrList.add(Double.parseDouble(jObjtemp.get("Pax").toString()));
						arrList.add(Double.parseDouble(jObjtemp.get("Qty").toString()));
						arrList.add(Double.parseDouble(jObjtemp.get("Amount").toString()));
						arrList.add(jObjtemp.get("Reason").toString());
						arrList.add(jObjtemp.get("UserCreated").toString());
						arrList.add(jObjtemp.get("DateCreated").toString());
						list.add(arrList);
						amtTotal = amtTotal + (Double.parseDouble(jObjtemp.get("Qty").toString()));
						netTotal = netTotal + (Double.parseDouble(jObjtemp.get("Amount").toString()));
						paxTotal = paxTotal + (Double.parseDouble(jObjtemp.get("Pax").toString()));

					}
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(paxTotal);
					totalList.add(amtTotal);
					totalList.add(netTotal);
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					resMap.put("List", list);
					resMap.put("totalList", totalList);
				}

			}
				break;
			case "Time Audit": {
				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jObjtemp = (JSONObject) jarr.get(i);

					List arrList = new ArrayList();
					arrList.add(jObjtemp.get("BillNo").toString());
					arrList.add(jObjtemp.get("BillDate").toString());
					arrList.add(jObjtemp.get("BillTime").toString());
					arrList.add(jObjtemp.get("KOTTime").toString());
					arrList.add(jObjtemp.get("SettleTime").toString());
					arrList.add(jObjtemp.get("Difference").toString());
					arrList.add(jObjtemp.get("UserCreated").toString());
					arrList.add(jObjtemp.get("UserEdited").toString());
					list.add(arrList);
					resMap.put("List", list);

				}
			}
				break;
			case "KOT Analysis": {
				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jObjtemp = (JSONObject) jarr.get(i);

					List arrList = new ArrayList();
					arrList.add(jObjtemp.get("BillNo").toString());
					arrList.add(jObjtemp.get("BillDate").toString());
					arrList.add(jObjtemp.get("KOTNo").toString());

					arrList.add(Double.parseDouble(jObjtemp.get("Qty").toString()));
					arrList.add(jObjtemp.get("ItemName").toString());
					arrList.add(jObjtemp.get("Waiter").toString());
					arrList.add(jObjtemp.get("Table").toString());
					list.add(arrList);
					amtTotal = amtTotal + (Double.parseDouble(jObjtemp.get("Qty").toString()));

				}
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(amtTotal);
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(" ");
				resMap.put("List", list);
				resMap.put("totalList", totalList);
			}
				break;
			case "Moved KOT": {
				if (strReportType.equals("Summary")) {
					for (int i = 0; i < jarr.size(); i++) {
						JSONObject jObjtemp = (JSONObject) jarr.get(i);

						List arrList = new ArrayList();
						arrList.add(jObjtemp.get("POS").toString());
						arrList.add(jObjtemp.get("Table").toString());
						arrList.add(jObjtemp.get("Waiter").toString());
						arrList.add(jObjtemp.get("KOTNo").toString());
						arrList.add(Double.parseDouble(jObjtemp.get("Pax").toString()));
						arrList.add(Double.parseDouble(jObjtemp.get("Amount").toString()));

						arrList.add(jObjtemp.get("Reason").toString());
						arrList.add(jObjtemp.get("UserCreated").toString());
						arrList.add(jObjtemp.get("DateCreated").toString());
						list.add(arrList);
						amtTotal = amtTotal + (Double.parseDouble(jObjtemp.get("Amount").toString()));
						paxTotal = paxTotal + (Double.parseDouble(jObjtemp.get("Pax").toString()));

					}
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");

					totalList.add(paxTotal);
					totalList.add(amtTotal);
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					resMap.put("List", list);
					resMap.put("totalList", totalList);
				} else {
					for (int i = 0; i < jarr.size(); i++) {
						JSONObject jObjtemp = (JSONObject) jarr.get(i);

						List arrList = new ArrayList();
						arrList.add(jObjtemp.get("POS").toString());
						arrList.add(jObjtemp.get("Table").toString());
						arrList.add(jObjtemp.get("Waiter").toString());
						arrList.add(jObjtemp.get("KOTNo").toString());
						arrList.add(jObjtemp.get("ItemName").toString());
						arrList.add(Double.parseDouble(jObjtemp.get("Pax").toString()));
						arrList.add(Double.parseDouble(jObjtemp.get("Qty").toString()));
						arrList.add(Double.parseDouble(jObjtemp.get("Amount").toString()));
						arrList.add(jObjtemp.get("Reason").toString());
						arrList.add(jObjtemp.get("UserCreated").toString());
						arrList.add(jObjtemp.get("DateCreated").toString());
						list.add(arrList);
						amtTotal = amtTotal + (Double.parseDouble(jObjtemp.get("Amount").toString()));
						netTotal = netTotal + (Double.parseDouble(jObjtemp.get("Qty").toString()));
						paxTotal = paxTotal + (Double.parseDouble(jObjtemp.get("Pax").toString()));

					}
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(paxTotal);
					totalList.add(amtTotal);
					totalList.add(netTotal);
					totalList.add(" ");
					totalList.add(" ");
					totalList.add(" ");
					resMap.put("List", list);
					resMap.put("totalList", totalList);
				}

			}
			}// end of switch

		}// end of if(jArr.size()>0)

		resMap.put("ColHeader", jColHeaderArr);
		return resMap;
	}
}
