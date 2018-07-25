package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsPOSAreaMasterBean;
import com.sanguine.webpos.bean.clsPOSMasterBean;
import com.sanguine.webpos.bean.clsPOSPromationMasterBean;
import com.sanguine.webpos.bean.clsPOSTaxMasterBean;
import com.sanguine.webpos.bean.clsPromotionDayTimeDtlBean;
import com.sanguine.webpos.bean.clsPromotionDtlBean;
import com.sanguine.webpos.bean.clsReorderTimeBean;
import com.sanguine.webpos.bean.clsSettlementDetailsBean;

@Controller
public class clsPOSPromotionMasterController {
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	Map map = new HashMap();

	@RequestMapping(value = "/frmPOSPromationMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsPOSPromationMasterBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {

		String clientCode = request.getSession().getAttribute("clientCode").toString();

		JSONArray jArrList = null;

		jArrList = objPOSGlobal.funGetAllAreaForMaster(clientCode);

		// Area List

		Map areaList = new HashMap<>();
		areaList.put("All", "All");
		if (null != jArrList) {
			for (int cnt = 0; cnt < jArrList.size(); cnt++) {
				JSONObject jObj = (JSONObject) jArrList.get(cnt);
				areaList.put(jObj.get("strAreaCode").toString(), jObj.get("strAreaName").toString());
			}
		}
		model.put("areaList", areaList);
		Map mapPOSList = new HashMap();
		mapPOSList.put("All", "All");

		jArrList = new JSONArray();
		jArrList = objPOSGlobal.funGetAllPOSForMaster(clientCode);
		for (int i = 0; i < jArrList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArrList.get(i);

			mapPOSList.put(josnObjRet.get("strPosCode"), josnObjRet.get("strPosName"));
		}

		model.put("posList", mapPOSList);

		return new ModelAndView("frmPOSPromationMaster");

	}

	@RequestMapping(value = "/savePromotionMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSPromationMasterBean objBean, BindingResult result, HttpServletRequest req) {

		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjPromotionMaster = new JSONObject();
			jObjPromotionMaster.put("strPromoCode", objBean.getStrPromoCode());
			jObjPromotionMaster.put("strPromoName", objBean.getStrPromoName());
			jObjPromotionMaster.put("dteFromDate", objBean.getDteFromDate());

			jObjPromotionMaster.put("dteToDate", objBean.getDteToDate());
			jObjPromotionMaster.put("strPromotionOn", objBean.getStrPromotionOn());

			jObjPromotionMaster.put("strPromoItemCode", objBean.getStrPromoItemCode());

			map.put(objBean.getStrPromoItemCode(), objBean.getStrPromoItemName());

			jObjPromotionMaster.put("strType", objBean.getStrType());

			jObjPromotionMaster.put("strOperator", objBean.getStrOperator());
			jObjPromotionMaster.put("dblBuyQty", objBean.getDblBuyQty());
			jObjPromotionMaster.put("strGetPromoOn", objBean.getStrGetPromoOn());
			jObjPromotionMaster.put("strGetItemCode", objBean.getStrGetItemCode());

			map.put(objBean.getStrGetItemCode(), objBean.getStrGetItemName());

			jObjPromotionMaster.put("dblGetQty", objBean.getDblGetQty());
			jObjPromotionMaster.put("strDiscountType", objBean.getStrDiscountType());
			jObjPromotionMaster.put("dblDiscount", objBean.getDblDiscount());
			jObjPromotionMaster.put("strPromoNote", objBean.getStrPromoNote());

			jObjPromotionMaster.put("posCode", objBean.getStrPOSCode());
			jObjPromotionMaster.put("areaCode", objBean.getStrAreaCode());
			jObjPromotionMaster.put("User", webStockUserCode);
			jObjPromotionMaster.put("ClientCode", clientCode);

			// Settlement Data

			List<clsPromotionDtlBean> list = objBean.getListBuyPromotionDtl();
			JSONArray jArrList = new JSONArray();
			if (null != list) {
				for (int i = 0; i < list.size(); i++) {
					clsPromotionDtlBean obj = new clsPromotionDtlBean();
					obj = (clsPromotionDtlBean) list.get(i);
					if (obj.getStrApplicableYN() != null) {
						JSONObject jObjData = new JSONObject();

						jObjData.put("strItemCode", obj.getStrItemCode());

						jArrList.add(jObjData);
					}

				}
			}
			jObjPromotionMaster.put("BuyItemDetails", jArrList);

			// Pos Data
			List<clsPromotionDtlBean> poslist = objBean.getListGetPromotionDtl();
			JSONArray jArrPosList = new JSONArray();
			if (null != poslist) {
				for (int i = 0; i < poslist.size(); i++) {
					clsPromotionDtlBean obj = new clsPromotionDtlBean();
					obj = (clsPromotionDtlBean) poslist.get(i);
					if (obj.getStrApplicableYN() != null) {
						JSONObject jObjData = new JSONObject();

						jObjData.put("strItemCode", obj.getStrItemCode());

						jArrPosList.add(jObjData);
					}

				}
			}
			jObjPromotionMaster.put("GetItemDetails", jArrPosList);

			// Tax Data
			String taxOnTaxCode = "";
			List<clsPromotionDayTimeDtlBean> dayTimeList = objBean.getListPromotionDayTimeDtl();
			JSONArray jArrTimeList = new JSONArray();

			// Area Data

			if (null != dayTimeList) {
				for (int i = 0; i < dayTimeList.size(); i++) {
					clsPromotionDayTimeDtlBean obj = new clsPromotionDayTimeDtlBean();
					obj = (clsPromotionDayTimeDtlBean) dayTimeList.get(i);
					if (null != obj.getStrDay()) {
						JSONObject jObjData = new JSONObject();
						jObjData.put("strDay", obj.getStrDay());
						jObjData.put("tmeFromTime", obj.getTmeFromTime());
						jObjData.put("tmeToTime", obj.getTmeToTime());

						jArrTimeList.add(jObjData);
					}

				}
			}
			jObjPromotionMaster.put("dayTimeDetails", jArrTimeList);

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funSavePromotionMaster";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjPromotionMaster.toString().getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = "", op = "";

			while ((output = br.readLine()) != null) {
				op += output;
			}
			System.out.println("Result= " + op);
			conn.disconnect();

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", " " + op);

			return new ModelAndView("redirect:/frmPOSPromationMaster.html");

		} catch (Exception ex) {
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/loadPromotionMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPOSPromationMasterBean funSetSearchFields(@RequestParam("promoCode") String promoCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSPromationMasterBean objPromotionMaster = null;
		List<clsPromotionDtlBean> listBuyData = new ArrayList<clsPromotionDtlBean>();
		List<clsPromotionDtlBean> listGetData = new ArrayList<clsPromotionDtlBean>();
		List<clsPromotionDayTimeDtlBean> listDayTime = new ArrayList<clsPromotionDayTimeDtlBean>();

		JSONObject jObjSearchDetails = new JSONObject();
		/*
		 * String posUrl =
		 * "http://localhost:8080/prjSanguineWebService/APOSSearchIntegration/funGetPOSSearchData"
		 * +
		 * "?masterName=POSPromotionMaster&searchCode="+promoCode+"&clientCode="
		 * +clientCode;
		 */

		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funGetPromotionMasterData" + "?promoCode=" + promoCode + "&clientCode=" + clientCode;
		System.out.println(posUrl);

		jObjSearchDetails = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		if (null != jObjSearchDetails) {
			objPromotionMaster = new clsPOSPromationMasterBean();
			objPromotionMaster.setStrPromoCode((String) jObjSearchDetails.get("strPromoCode"));
			objPromotionMaster.setStrPromoName((String) jObjSearchDetails.get("strPromoName"));
			objPromotionMaster.setStrPOSCode((String) jObjSearchDetails.get("strPOSCode"));
			objPromotionMaster.setDteFromDate((String) jObjSearchDetails.get("dteFromDate"));
			objPromotionMaster.setDteToDate((String) jObjSearchDetails.get("dteToDate"));

			String promoOn = (String) jObjSearchDetails.get("strPromotionOn");
			objPromotionMaster.setStrPromotionOn(promoOn);

			objPromotionMaster.setStrPromoItemCode((String) jObjSearchDetails.get("strPromoItemCode"));
			objPromotionMaster.setStrPromoItemName((String) jObjSearchDetails.get("strPromoItemName"));

			objPromotionMaster.setStrType((String) jObjSearchDetails.get("strType"));
			objPromotionMaster.setStrOperator((String) jObjSearchDetails.get("strOperator"));
			objPromotionMaster.setDblBuyQty((long) jObjSearchDetails.get("dblBuyQty"));

			String getPromoOn = (String) jObjSearchDetails.get("strGetPromoOn");

			objPromotionMaster.setStrGetPromoOn(getPromoOn);
			objPromotionMaster.setStrAreaCode((String) jObjSearchDetails.get("areaCode"));
			objPromotionMaster.setStrGetItemCode((String) jObjSearchDetails.get("strGetItemCode"));
			objPromotionMaster.setStrGetItemName((String) jObjSearchDetails.get("strGetItemName"));

			objPromotionMaster.setStrPromoNote((String) jObjSearchDetails.get("strPromoNote"));

			// Buy Details
			JSONArray jArrBuyList = (JSONArray) jObjSearchDetails.get("BuyData");
			if (null != jArrBuyList) {

				if (promoOn.equalsIgnoreCase("MenuHead")) {
					for (int cnt = 0; cnt < jArrBuyList.size(); cnt++) {
						JSONObject jobj = (JSONObject) jArrBuyList.get(cnt);
						clsPromotionDtlBean objBean = new clsPromotionDtlBean();
						objBean.setStrItemCode((String) jobj.get("buyPromoItemCode"));

						listBuyData.add(objBean);
					}
				}
			}
			objPromotionMaster.setListBuyPromotionDtl(listBuyData);

			// Get Data
			JSONArray jArrGetList = (JSONArray) jObjSearchDetails.get("GetData");
			if (null != jArrGetList) {
				if (getPromoOn.equalsIgnoreCase("Item")) {
					JSONObject jobj = (JSONObject) jArrGetList.get(0);

					objPromotionMaster.setDblGetQty((long) jobj.get("GetQty"));
					objPromotionMaster.setDblDiscount((long) jobj.get("Discount"));
					objPromotionMaster.setStrDiscountType((String) jobj.get("DiscountType"));
				}
				if (getPromoOn.equalsIgnoreCase("MenuHead")) {

					for (int cnt = 0; cnt < jArrGetList.size(); cnt++) {
						JSONObject jobj = (JSONObject) jArrGetList.get(cnt);
						clsPromotionDtlBean objPOSDtl = new clsPromotionDtlBean();
						objPOSDtl.setStrItemCode((String) jobj.get("GetPromoItemCode"));

						objPromotionMaster.setDblGetQty((long) jobj.get("GetQty"));
						objPromotionMaster.setDblDiscount((long) jobj.get("Discount"));
						objPromotionMaster.setStrDiscountType((String) jobj.get("DiscountType"));

						listGetData.add(objPOSDtl);
					}
				}
			}
			objPromotionMaster.setListGetPromotionDtl(listGetData);

			// DayTime Data
			JSONArray jArrDayTimeList = (JSONArray) jObjSearchDetails.get("TimeData");
			if (null != jArrGetList) {
				for (int cnt = 0; cnt < jArrDayTimeList.size(); cnt++) {
					JSONObject jobj = (JSONObject) jArrDayTimeList.get(cnt);
					clsPromotionDayTimeDtlBean objPOSDtl = new clsPromotionDayTimeDtlBean();
					objPOSDtl.setStrDay((String) jobj.get("Day"));
					objPOSDtl.setTmeFromTime((String) jobj.get("FromTime"));
					objPOSDtl.setTmeToTime((String) jobj.get("ToTime"));

					listDayTime.add(objPOSDtl);
				}
			}
			objPromotionMaster.setListPromotionDayTimeDtl(listDayTime);

		}

		if (null == objPromotionMaster) {
			objPromotionMaster = new clsPOSPromationMasterBean();
			objPromotionMaster.setStrPromoCode("Invalid Code");
		}

		return objPromotionMaster;
	}

	@RequestMapping(value = "/loadMenuHeadDataForPromotion", method = RequestMethod.GET)
	public @ResponseBody List<clsPromotionDtlBean> funLoadSettlmentData(@RequestParam("menuCode") String menuCode, HttpServletRequest req)

	{

		JSONArray jArrSettlementList = null;
		List<clsPromotionDtlBean> listSettleData = new ArrayList<clsPromotionDtlBean>();
		JSONObject jObjSettlementData = new JSONObject();
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funGetMenuHeadDtlForPromotionMaster" + "?menuCode=" + menuCode;

		jObjSettlementData = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		jArrSettlementList = (JSONArray) jObjSettlementData.get("MenuHeadDtl");

		if (null != jArrSettlementList) {
			for (int cnt = 0; cnt < jArrSettlementList.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArrSettlementList.get(cnt);
				clsPromotionDtlBean objBean = new clsPromotionDtlBean();
				objBean.setStrItemCode((String) jobj.get("strItemCode"));
				objBean.setStrItemName((String) jobj.get("strItemName"));
				objBean.setStrRate((long) jobj.get("strRate"));

				listSettleData.add(objBean);
			}
		}
		return listSettleData;
	}

	@RequestMapping(value = "/funCheckDuplicateBuyPromoItem", method = RequestMethod.GET)
	public @ResponseBody JSONObject funCheckDuplicateBuyPromoItem(@RequestParam("promoItemCode") String promoItemCode, @RequestParam("promoCode") String promoCode, @RequestParam("areaCode") String areaCode, @RequestParam("posCode") String posCode, HttpServletRequest req) {
		JSONObject jObj = new JSONObject();
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funCheckDuplicateBuyPromoItem" + "?promoItemCode=" + promoItemCode + "&promoCode=" + promoCode + "&areaCode=" + areaCode + "&posCode=" + posCode;

		jObj = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		return jObj;

	}

}
