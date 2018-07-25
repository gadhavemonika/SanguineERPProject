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
public class clsPOSAdvanceOrderFlashController {

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobalFunctionsController;

	@Autowired
	private ServletContext servletContext;

	Map map = new HashMap();

	@RequestMapping(value = "/frmPOSAdvanceOrderFlash", method = RequestMethod.GET)
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

		List advOrderList = new ArrayList();
		advOrderList.add("ALL");

		jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetAllAdvOrderType");
		jArryPosList = (JSONArray) jObj.get("advOrderList");
		for (int i = 0; i < jArryPosList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryPosList.get(i);
			advOrderList.add(josnObjRet.get("strAdvOrderTypeName"));
			map.put(josnObjRet.get("strAdvOrderTypeName"), josnObjRet.get("strAdvOrderTypeCode"));
		}
		model.put("advOrderList", advOrderList);

		if ("2".equalsIgnoreCase(urlHits)) {

			return new ModelAndView("frmPOSAdvanceOrderFlash_1", "command", new clsWebPOSReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSAdvanceOrderFlash", "command", new clsWebPOSReportBean());
		} else {
			return null;
		}

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptPOSAdvOrderFlash", method = RequestMethod.POST)
	private ModelAndView funReport(@ModelAttribute("command") clsWebPOSReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String userCode = req.getSession().getAttribute("usercode").toString();

		String strPosCode = req.getSession().getAttribute("loginPOS").toString();

		String fromDate = objBean.getFromDate();

		String toDate = objBean.getToDate();

		String strReportType = objBean.getStrReportType();

		String posName = objBean.getStrPOSName();

		String strDateFilter = objBean.getDteDate();

		String strCustomerCode = objBean.getStrPSPCode();

		String operationType = objBean.getStrOperationType();

		String strOrderMode = objBean.getStrViewType();

		String strStatus = objBean.getStrViewBy();

		Map resMap = new LinkedHashMap();

		resMap = FunGetData(clientCode, userCode, fromDate, toDate, strDateFilter, strReportType, strCustomerCode, posName, operationType, strOrderMode, strStatus, strPosCode);

		List ExportList = new ArrayList();

		String dteFromDate = objBean.getFromDate();
		String dteToDate = objBean.getToDate();
		String FileName = "AdvanceOrderFlash_" + dteFromDate + "_To_" + dteToDate;

		if (operationType.equals("Item wise")) {
			FileName = "AdvOrder_ItemWise";

		} else if (operationType.equals("Customer wise")) {
			FileName = "AdvOrder_CustomerWise";

		} else if (operationType.equals("Bill wise")) {
			FileName = "AdvOrder_BillWise";

		} else if (operationType.equals("Menu Head wise")) {
			FileName = "AdvOrder_MenuHeadWise";

		} else if (operationType.toString().equalsIgnoreCase("Group Wise")) {
			FileName = "AdvOrder_GroupWise";

		}
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
	@RequestMapping(value = { "/loadAdvOrderFlash" }, method = RequestMethod.GET)
	@ResponseBody
	public Map FunLoadDaywiseSalesSummary1(HttpServletRequest req) {
		LinkedHashMap resMap = new LinkedHashMap();

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String userCode = req.getSession().getAttribute("usercode").toString();

		String strPosCode = req.getSession().getAttribute("loginPOS").toString();

		String strDateFilter = req.getParameter("strDateFilter");

		String fromDate = req.getParameter("fromDate");

		String toDate = req.getParameter("toDate");

		String strReportType = req.getParameter("strReportType");

		String posName = req.getParameter("posName");

		String strCustomerCode = req.getParameter("strCustomerCode");

		String operationType = req.getParameter("operationType");

		String strOrderMode = req.getParameter("strOrderMode");

		String strStatus = req.getParameter("strStatus");

		resMap = FunGetData(clientCode, userCode, fromDate, toDate, strDateFilter, strReportType, strCustomerCode, posName, operationType, strOrderMode, strStatus, strPosCode);
		return resMap;
	}

	@SuppressWarnings({ "unchecked" })
	private LinkedHashMap FunGetData(String clientCode, String userCode, String fromDate, String toDate, String strDateFilter, String strReportType, String strCustomerCode, String posName, String operationType, String strOrderMode, String strStatus, String strPosCode) {
		LinkedHashMap resMap = new LinkedHashMap();

		double total1 = 0, total2 = 0, total3 = 0, total4 = 0;

		List colHeader = new ArrayList();

		String fromDate1 = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];

		String toDate1 = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];

		String posCode = "ALL";

		if (map.containsKey(posName)) {
			posCode = (String) map.get(posName);
		}

		String advOrderCode = "ALL";

		if (map.containsKey(strOrderMode)) {
			advOrderCode = (String) map.get(strOrderMode);
		}

		JSONObject jObjFillter = new JSONObject();
		jObjFillter.put("fromDate", fromDate1);
		jObjFillter.put("toDate", toDate1);
		jObjFillter.put("strDateFilter", strDateFilter);
		jObjFillter.put("posCode", posCode);
		jObjFillter.put("strReportType", strReportType);
		jObjFillter.put("strCustomerCode", strCustomerCode);
		jObjFillter.put("operationType", operationType);
		jObjFillter.put("advOrderCode", advOrderCode);
		jObjFillter.put("clientCode", clientCode);
		jObjFillter.put("strStatus", strStatus);
		jObjFillter.put("userCode", userCode);
		jObjFillter.put("LogedInPOSCode", strPosCode);
		JSONObject jObj = new JSONObject();

		jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funGetAdvanceOrderFlash", jObjFillter);

		JSONArray jColHeaderArr = (JSONArray) jObj.get("ColHeader");
		List list = new ArrayList();
		JSONArray jarr = (JSONArray) jObj.get("jArr");
		List totalList = new ArrayList();
		totalList.add("Total");
		if (null != jarr) {
			switch (operationType) {
			case "Item Wise":

				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jObjtemp = (JSONObject) jarr.get(i);

					// clsAuditFlashBean objClsGroupWaiseSalesBean=new
					// clsAuditFlashBean();
					List arrList = new ArrayList();
					arrList.add(jObjtemp.get("AdvBookingNo").toString());
					arrList.add(jObjtemp.get("AdvBookingDate").toString());
					arrList.add(jObjtemp.get("DteOrderFor").toString());

					arrList.add(jObjtemp.get("CustomerName").toString());
					arrList.add(jObjtemp.get("AdvOrderTypeName").toString());
					arrList.add(jObjtemp.get("OperationType").toString());
					arrList.add(jObjtemp.get("ItemName").toString());
					arrList.add(Double.parseDouble(jObjtemp.get("Quantity").toString()));
					arrList.add(Double.parseDouble(jObjtemp.get("GrandTotal").toString()));
					arrList.add(jObjtemp.get("ManualAdvOrderNo").toString());
					list.add(arrList);

					total1 = total1 + (Double.parseDouble(jObjtemp.get("Quantity").toString()));
					total2 = total2 + (Double.parseDouble(jObjtemp.get("GrandTotal").toString()));

				}
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(" ");

				totalList.add(total1);
				totalList.add(total2);
				totalList.add(" ");

				resMap.put("List", list);
				resMap.put("totalList", totalList);

				break;
			case "Customer Wise": {

				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jObjtemp = (JSONObject) jarr.get(i);

					List arrList = new ArrayList();
					arrList.add(jObjtemp.get("AdvBookingNo").toString());
					arrList.add(jObjtemp.get("AdvBookingDate").toString());
					arrList.add(jObjtemp.get("CustomerName").toString());
					arrList.add(jObjtemp.get("DteOrderFor").toString());
					arrList.add(jObjtemp.get("AdvOrderTypeName").toString());
					arrList.add(jObjtemp.get("OperationType").toString());

					arrList.add(Double.parseDouble(jObjtemp.get("DiscountAmt").toString()));
					arrList.add(Double.parseDouble(jObjtemp.get("GrandTotal").toString()));
					arrList.add(Double.parseDouble(jObjtemp.get("AdvDeposite").toString()));
					arrList.add(Double.parseDouble(jObjtemp.get("Balance").toString()));
					arrList.add(jObjtemp.get("ManualAdvOrderNo").toString());
					list.add(arrList);
					total1 = total1 + (Double.parseDouble(jObjtemp.get("DiscountAmt").toString()));
					total2 = total2 + (Double.parseDouble(jObjtemp.get("GrandTotal").toString()));
					total3 = total3 + (Double.parseDouble(jObjtemp.get("AdvDeposite").toString()));
					total4 = total4 + (Double.parseDouble(jObjtemp.get("Balance").toString()));

				}
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(total1);
				totalList.add(total2);
				totalList.add(total3);
				totalList.add(total4);
				totalList.add(" ");

				resMap.put("List", list);
				resMap.put("totalList", totalList);

			}
				break;

			case "Bill Wise": {

				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jObjtemp = (JSONObject) jarr.get(i);

					List arrList = new ArrayList();
					arrList.add(jObjtemp.get("AdvBookingNo").toString());
					arrList.add(jObjtemp.get("AdvBookingDate").toString());
					arrList.add(jObjtemp.get("DteOrderFor").toString());
					arrList.add(jObjtemp.get("SettleDate").toString());
					arrList.add(jObjtemp.get("WShortName").toString());
					arrList.add(jObjtemp.get("AdvOrderTypeName").toString());
					arrList.add(jObjtemp.get("OperationType").toString());
					arrList.add(jObjtemp.get("POSCode").toString());
					arrList.add(jObjtemp.get("SettelmentMode").toString());
					arrList.add(Double.parseDouble(jObjtemp.get("GrandTotal").toString()));
					arrList.add(Double.parseDouble(jObjtemp.get("AdvDeposite").toString()));
					arrList.add(Double.parseDouble(jObjtemp.get("Balance").toString()));
					arrList.add(jObjtemp.get("BillNo").toString());
					arrList.add(Double.parseDouble(jObjtemp.get("SettlementAmt").toString()));
					arrList.add(jObjtemp.get("ManualAdvOrderNo").toString());
					list.add(arrList);
					total1 = total1 + (Double.parseDouble(jObjtemp.get("GrandTotal").toString()));
					total2 = total2 + (Double.parseDouble(jObjtemp.get("AdvDeposite").toString()));
					total3 = total3 + (Double.parseDouble(jObjtemp.get("Balance").toString()));
					total4 = total4 + (Double.parseDouble(jObjtemp.get("SettlementAmt").toString()));
				}
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(total1);
				totalList.add(total2);
				totalList.add(total3);
				totalList.add(" ");
				totalList.add(total4);
				totalList.add(" ");

				resMap.put("List", list);
				resMap.put("totalList", totalList);

			}
				break;

			case "MenuHead Wise": {

				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jObjtemp = (JSONObject) jarr.get(i);

					List arrList = new ArrayList();
					arrList.add(jObjtemp.get("AdvOrdNo").toString());
					arrList.add(jObjtemp.get("DteOrderFor").toString());
					arrList.add(jObjtemp.get("BillDate").toString());
					arrList.add(jObjtemp.get("CustomerName").toString());
					arrList.add(jObjtemp.get("OrderType").toString());
					arrList.add(jObjtemp.get("OperationType").toString());
					arrList.add(jObjtemp.get("MenuName").toString());

					arrList.add(Double.parseDouble(jObjtemp.get("Qty").toString()));
					arrList.add(Double.parseDouble(jObjtemp.get("Amount").toString()));

					arrList.add(jObjtemp.get("ManualAdvOrderNo").toString());
					list.add(arrList);
					total1 = total1 + (Double.parseDouble(jObjtemp.get("Qty").toString()));
					total2 = total2 + (Double.parseDouble(jObjtemp.get("Amount").toString()));

				}
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(total1);
				totalList.add(total2);
				totalList.add(" ");

				resMap.put("List", list);
				resMap.put("totalList", totalList);

			}
				break;

			case "Group Wise": {

				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jObjtemp = (JSONObject) jarr.get(i);

					List arrList = new ArrayList();
					arrList.add(jObjtemp.get("AdvBookingNo").toString());
					arrList.add(jObjtemp.get("DteOrderFor").toString());
					arrList.add(jObjtemp.get("AdvBookingDate").toString());
					arrList.add(jObjtemp.get("CustomerName").toString());
					arrList.add(jObjtemp.get("AdvOrderTypeName").toString());
					arrList.add(jObjtemp.get("OperationType").toString());
					arrList.add(jObjtemp.get("GroupName").toString());

					arrList.add(Double.parseDouble(jObjtemp.get("Quantity").toString()));
					arrList.add(Double.parseDouble(jObjtemp.get("GrandTotal").toString()));

					arrList.add(jObjtemp.get("ManualAdvOrderNo").toString());
					list.add(arrList);
					total1 = total1 + (Double.parseDouble(jObjtemp.get("Quantity").toString()));
					total2 = total2 + (Double.parseDouble(jObjtemp.get("GrandTotal").toString()));

				}
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(" ");
				totalList.add(total1);
				totalList.add(total2);
				totalList.add(" ");
				resMap.put("List", list);
				resMap.put("totalList", totalList);

			}
				break;

			}// end of switch

		}// end of if(jArr.size()>0)

		resMap.put("ColHeader", jColHeaderArr);
		return resMap;
	}

}
