package com.sanguine.webpos.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsPOSZoneMasterBean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import com.sanguine.webpos.bean.clasPOSBulkItemPricingTableDataBean;
import com.sanguine.webpos.bean.clsPOSBulkPricingMasterBean;
import com.sanguine.webpos.bean.clsPOSCustomerTypeMasterBean;
import com.sanguine.webpos.bean.clsPOSZoneMasterBean;
import com.sanguine.webpos.bean.clsWebPOSReportBean;

@Controller
public class clsPOSBulkPricingMasterController {

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	;

	Map map = new HashMap();
	Map map2 = new HashMap();
	Map map3 = new HashMap();
	Map map4 = new HashMap();

	@RequestMapping(value = "/frmPOSBulkMenuItemPricing", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request)

	{
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String urlHits = "1";

		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		List poslist = new ArrayList();
		List MenuHeadlist = new ArrayList();
		List areaList = new ArrayList();
		List costCenterList = new ArrayList();

		// poslist.add("ALL");
		map.put("ALL", "ALL");
		map2.put("ALL", "ALL");
		map3.put("ALL", "ALL");
		map4.put("ALL", "ALL");

		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOS");

		JSONArray jArryPosList = (JSONArray) jObj.get("posList");

		for (int i = 0; i < jArryPosList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryPosList.get(i);
			// poslist.add(josnObjRet.get("strPosName"));

			map.put(josnObjRet.get("strPosCode"), josnObjRet.get("strPosName"));
		}
		model.put("posList", map);

		JSONObject jObj1 = objGlobalFunctions.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetCostCenter");

		JSONArray jArrycostCenterList = (JSONArray) jObj1.get("CostCenterList");

		for (int i = 0; i < jArrycostCenterList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArrycostCenterList.get(i);
			// costCenterList.add(josnObjRet.get("strCostCenterName"));

			map2.put(josnObjRet.get("strCostCenterCode"), josnObjRet.get("strCostCenterName"));
		}
		model.put("costCenterList", map2);

		// area list
		JSONArray jArrList = null;
		areaList.add("ALL");
		jArrList = objPOSGlobal.funGetAllAreaForMaster(strClientCode);

		// Area List

		if (null != jArrList) {
			for (int cnt = 0; cnt < jArrList.size(); cnt++) {
				JSONObject jObjarea = (JSONObject) jArrList.get(cnt);

				// areaList.add((String)jObjarea.get("strAreaName"));

				map3.put(jObjarea.get("strAreaCode"), jObjarea.get("strAreaName"));
			}
			model.put("areaList", map3);
		}

		// Menu Head list
		JSONArray jArrMenuHeadList = null;

		jArrMenuHeadList = objPOSGlobal.funGetAllMenuHeadForMaster(strClientCode);

		// Area List

		if (null != jArrMenuHeadList) {
			for (int cnt = 0; cnt < jArrMenuHeadList.size(); cnt++) {
				JSONObject jObj11 = (JSONObject) jArrMenuHeadList.get(cnt);

				// MenuHeadlist.add((String)jObj11.get("strMenuName"));

				map4.put(jObj11.get("strMenuCode"), jObj11.get("strMenuName"));
			}
			model.put("MenuHeadlist", map4);
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSBulkMenuItemPricingr_1", "command", new clsPOSBulkPricingMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSBulkMenuItemPricing", "command", new clsPOSBulkPricingMasterBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/updateBulkItemPricingMasterMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSBulkPricingMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		System.out.println(objBean);
		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			String POSCode = objBean.getStrPOSName();

			String areaCode = objBean.getStrArea();

			String costCenterCode = objBean.getStrCostCenter();

			String menuHeadCode = objBean.getStrMenuHead();

			JSONObject jObjMaster = new JSONObject();
			jObjMaster.put("posCode", POSCode);
			jObjMaster.put("areaCode", areaCode);
			jObjMaster.put("costCenterCode", costCenterCode);
			jObjMaster.put("menuHeadCode", menuHeadCode);
			jObjMaster.put("strSortBy", objBean.getStrSortBy());
			jObjMaster.put("strExpiredItem", objBean.getStrExpiredItem());
			jObjMaster.put("User", webStockUserCode);
			jObjMaster.put("ClientCode", clientCode);
			jObjMaster.put("dteDateCreated", objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjMaster.put("dteDateEdited", objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));

			List<clasPOSBulkItemPricingTableDataBean> listdata = objBean.getListdata();

			clasPOSBulkItemPricingTableDataBean obj;

			JSONArray jArrList = new JSONArray();
			for (int i = 0; i < listdata.size(); i++) {

				obj = listdata.get(i);
				JSONObject jObjData = new JSONObject();
				jObjMaster.put("posCode", POSCode);
				jObjData.put("ItemCode", obj.getItemCode());
				jObjData.put("ItemName", obj.getItemName());
				jObjData.put("MenuCode", map.get(obj.getMenuName()));
				jObjData.put("MenuName", obj.getMenuName());
				jObjData.put("Popular", obj.getPopular());
				jObjData.put("PriceSunday", obj.getPriceSunday());
				jObjData.put("PriceMonday", obj.getPriceMonday());
				jObjData.put("PriceTuesday", obj.getPriceTuesday());
				jObjData.put("PriceWednesday", obj.getPriceWednesday());
				jObjData.put("PriceThursday", obj.getPriceThursday());
				jObjData.put("PriceFriday", obj.getPriceFriday());
				jObjData.put("PriceSaturday", obj.getPriceSaturday());
				jObjData.put("FromDate", obj.getFromDate());
				jObjData.put("ToDate", obj.getToDate());
				jObjData.put("TimeFrom", obj.getTimeFrom());
				jObjData.put("AMPMFrom", obj.getAMPMFrom());
				jObjData.put("TimeTo", obj.getTimeTo());
				jObjData.put("AMPMTo", obj.getAMPMTo());
				jObjData.put("CostCenterCode", map.get(obj.getCostCenter()));
				jObjData.put("CostCenter", obj.getCostCenter());
				jObjData.put("TextColor", obj.getTextColor());
				jObjData.put("Areacode", map.get(obj.getArea()));
				jObjData.put("Area", obj.getArea());
				jObjData.put("SubMenuHeadName", obj.getSubMenuHeadCode());
				jObjData.put("SubMenuHeadCode", map.get(obj.getSubMenuHeadCode()));
				jObjData.put("HourlyPricing", obj.getHourlyPricing());

				jArrList.add(jObjData);
			}
			jObjMaster.put("List", jArrList);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funUpdateBulkItemPricingMaster", jObjMaster);

			return new ModelAndView("redirect:/frmPOSZoneMaster.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/exportPOSBulkItemPricingReport", method = RequestMethod.GET)
	// private ModelAndView funexport(@ModelAttribute("command")
	// clsPOSBulkPricingMasterBean objBean, HttpServletResponse
	// resp,HttpServletRequest req)
	private ModelAndView funexport(@RequestParam(value = "param1") String param1, HttpServletRequest req) {

		String[] spParam1 = param1.split(",");

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String strPOSCode = spParam1[0];

		String strArea = spParam1[1];

		String strCostCenter = spParam1[2];

		String strMenuHead = spParam1[3];

		String strSortBy = spParam1[4];

		String strExpiredItem = spParam1[5];

		/*
		 * String strPOSCode =objBean.getStrPOSName();
		 * 
		 * String strArea=objBean.getStrArea();
		 * 
		 * String strCostCenter=objBean.getStrCostCenter();
		 * 
		 * String strMenuHead=objBean.getStrMenuHead();
		 * 
		 * String strSortBy=objBean.getStrSortBy();
		 * 
		 * String strExpiredItem=objBean.getStrExpiredItem();
		 */

		Map resMap = new LinkedHashMap();

		resMap = FunGetData(clientCode, strPOSCode, strArea, strCostCenter, strMenuHead, strSortBy, strExpiredItem);

		List ExportList = new ArrayList();

		String FileName = "BulkItemPricing";

		ExportList.add(FileName);

		List List = (List) resMap.get("listcol");

		String[] headerList = new String[List.size()];
		for (int i = 0; i < List.size(); i++) {
			headerList[i] = (String) List.get(i);
		}

		ExportList.add(headerList);

		List dataList = (List) resMap.get("List");

		ExportList.add(dataList);

		return new ModelAndView("excelViewWithReportName", "listWithReportName", ExportList);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/loadBulkItemPricingMaster" }, method = RequestMethod.POST)
	@ResponseBody
	public Map FunLoadBulkItemPricing(HttpServletRequest req) {
		LinkedHashMap resMap = new LinkedHashMap();

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String posName = req.getParameter("posName");

		String area = req.getParameter("area");

		String costCenter = req.getParameter("costCenter");

		String menuHead = req.getParameter("menuHead");

		String sortBy = req.getParameter("sortBy");

		String expriedItem = req.getParameter("expriedItem");

		resMap = FunGetData(clientCode, posName, area, costCenter, menuHead, sortBy, expriedItem);
		return resMap;
	};

	@SuppressWarnings({ "unchecked" })
	private LinkedHashMap FunGetData(String clientCode, String strPOSCode, String area, String costCenter, String menuHead, String sortBy, String expriedItem) {
		LinkedHashMap resMap = new LinkedHashMap();

		double amtTotal = 0, netTotal = 0, paxTotal = 0;

		List colHeader = new ArrayList();

		JSONObject jObjFillter = new JSONObject();

		jObjFillter.put("posCode", strPOSCode);
		jObjFillter.put("area", area);
		jObjFillter.put("costCenter", costCenter);
		jObjFillter.put("menuHead", menuHead);
		jObjFillter.put("sortBy", sortBy);
		jObjFillter.put("expriedItem", expriedItem);

		JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funRetriveBulkItemPricingMaster", jObjFillter);
		List list = new ArrayList();
		List listcol = new ArrayList();

		JSONArray jarr = (JSONArray) jObj.get("jArr");

		if (null != jarr) {
			for (int i = 0; i < jarr.size(); i++) {
				JSONObject jObjtemp = (JSONObject) jarr.get(i);
				List arrList = new ArrayList();
				String[] fromdate = jObjtemp.get("dteFromDate").toString().split(" ");
				String[] todate = jObjtemp.get("dteToDate").toString().split(" ");

				arrList.add(jObjtemp.get("strItemCode").toString());
				arrList.add(jObjtemp.get("strItemName").toString());
				arrList.add(jObjtemp.get("strMenuName").toString());
				arrList.add(jObjtemp.get("strPopular").toString());
				arrList.add(Double.parseDouble(jObjtemp.get("strPriceSunday").toString()));
				arrList.add(Double.parseDouble(jObjtemp.get("strPriceMonday").toString()));
				arrList.add(Double.parseDouble(jObjtemp.get("strPriceTuesday").toString()));
				arrList.add(Double.parseDouble(jObjtemp.get("strPriceWednesday").toString()));
				arrList.add(Double.parseDouble(jObjtemp.get("strPriceThursday").toString()));
				arrList.add(Double.parseDouble(jObjtemp.get("strPriceFriday").toString()));
				arrList.add(Double.parseDouble(jObjtemp.get("strPriceSaturday").toString()));
				arrList.add(fromdate[0]);
				arrList.add(todate[0]);
				arrList.add(jObjtemp.get("tmeTimeFrom").toString());
				arrList.add(jObjtemp.get("strAMPMFrom").toString());
				arrList.add(jObjtemp.get("tmeTimeTo").toString());
				arrList.add(jObjtemp.get("strAMPMTo").toString());
				arrList.add(jObjtemp.get("strCostCenterName").toString());
				arrList.add(jObjtemp.get("strTextColor").toString());
				arrList.add(jObjtemp.get("strAreaName").toString());
				arrList.add(jObjtemp.get("strSubMenuHeadName").toString());
				arrList.add(jObjtemp.get("strHourlyPricing").toString());
				arrList.add(jObjtemp.get("ISExpired").toString());

				list.add(arrList);

			}

			listcol.add("Item Code");
			listcol.add("Item Name");
			listcol.add("Menu Name");
			listcol.add("Popular");
			listcol.add("PriceSunday");
			listcol.add("PriceMonday");
			listcol.add("PriceTuesday");
			listcol.add("PriceWednesday");
			listcol.add("PriceThursday");
			listcol.add("PriceFriday");
			listcol.add("PriceSaturday ");
			listcol.add("FromDate");
			listcol.add("ToDate");
			listcol.add("TimeFrom");
			listcol.add("AMPMFrom");
			listcol.add("TimeTo");
			listcol.add("AMPMTo");
			listcol.add("CostCenter");
			listcol.add("TextColor");
			listcol.add("Area");
			listcol.add("SubMenuHeadCode");
			listcol.add("HourlyPricing");
			listcol.add("IsExpired");

			resMap.put("List", list);
			resMap.put("listcol", listcol);
		}

		return resMap;
	}
}
