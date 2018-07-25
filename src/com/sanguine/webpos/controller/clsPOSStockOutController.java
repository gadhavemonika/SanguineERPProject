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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
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
import com.sanguine.webpos.bean.clsPOSPSPDtl;
import com.sanguine.webpos.bean.clsPOSPhysicalStockPostingBean;
import com.sanguine.webpos.bean.clsPOSReasonMasterBean;
import com.sanguine.webpos.bean.clsPOSStockInOutDtl;
import com.sanguine.webpos.bean.clsPOSStockInOutHd;
import com.sanguine.webpos.bean.clsPOSUserAccessBean;

@Controller
public class clsPOSStockOutController {

	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobalFunctionsController;

	@Autowired
	private ServletContext servletContext;

	Map<String, String> map = new HashMap<String, String>();
	private Map<String, clsPOSPSPDtl> hmPSPDtl = null;
	final static Logger logger = Logger.getLogger(clsPOSStockInOutHd.class);
	List<clsPOSReasonMasterBean> listReason = new ArrayList<clsPOSReasonMasterBean>();

	@RequestMapping(value = "/frmPOSStkOut", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String posCode = request.getSession().getAttribute("loginPOS").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		List reasonList = new ArrayList();
		funGetPSPReasonList("All");
		for (int cnt = 0; cnt < listReason.size(); cnt++) {
			clsPOSReasonMasterBean obj = listReason.get(cnt);
			reasonList.add(obj.getStrReasonName());
			map.put(obj.getStrReasonCode(), obj.getStrReasonName());
		}
		model.put("reasonList", reasonList);

		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {

			return new ModelAndView("frmPOSStkOut_1", "command", new clsPOSStockInOutHd());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSStkOut", "command", new clsPOSStockInOutHd());
		} else {
			return null;
		}

	}

	private clsPOSStockInOutHd funGetPSPReasonList(String reasonCode) {
		clsPOSStockInOutHd objBean = null;

		JSONObject jObjReasonDetails = new JSONObject();
		JSONArray jArrReasonList = null;
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funGetReasonCode" + "?ReasonCode=" + reasonCode + "&Type=" + "strStkOut";
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
				map.put((String) jobj.get("ReasonCode"), (String) jobj.get("ReasonName"));
			}

		}
		return objBean;
	}

	@RequestMapping(value = "/saveStockOut", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSStockInOutHd objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";

		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();
			String posCode = req.getSession().getAttribute("loginPOS").toString();

			JSONObject jObj = new JSONObject();
			clsPOSUserAccessBean obj = null;
			List<clsPOSStockInOutDtl> listOfItem = objBean.getListOfItem();

			String reasonCode = "";
			JSONArray jArrItemList = new JSONArray();

			if (listOfItem.size() > 0) {
				for (int i = 1; i < listOfItem.size(); i++) {
					clsPOSStockInOutDtl objStkOutDtl = listOfItem.get(i);
					JSONObject jObjItemDtl = new JSONObject();
					jObjItemDtl.put("ItemCode", objStkOutDtl.getStrItemCode());
					jObjItemDtl.put("ItemName", objStkOutDtl.getStrItemName());
					jObjItemDtl.put("Qty", objStkOutDtl.getDblQuantity());
					jObjItemDtl.put("PurchaseRate", objStkOutDtl.getDblPurchaseRate());
					jObjItemDtl.put("Amount", objStkOutDtl.getDblAmount());
					jArrItemList.add(jObjItemDtl);
				}
			}

			if (objBean.getStrReasonCode() != null) {
				if (map.size() > 0) {
					for (String key : map.keySet()) {
						if (map.get(key).equals(objBean.getStrReasonCode())) {
							reasonCode = key;
						}
					}
				}
			}

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOSDate" + "?POSCode=" + posCode;
			JSONObject jObjPosDate = objGlobal.funGETMethodUrlJosnObjectData(posURL);
			String posDate = (String) jObjPosDate.get("POSDate");
			jObj.put("ReasonCode", reasonCode);
			jObj.put("ItemList", jArrItemList);
			jObj.put("POSCode", posCode);
			jObj.put("ClientCode", clientCode);
			jObj.put("UserCode", webStockUserCode);
			jObj.put("PurchaseBillNo", objBean.getStrPurchaseBillNo());
			jObj.put("PurchaseBillDate", objBean.getDtePurchaseBillDate());
			jObj.put("POSDate", posDate);
			jObj.put("StockOutCode", posDate);
			if (objBean.getStrStkOutCode() != null) {
				jObj.put("StockOutCode", objBean.getStrStkOutCode());
			} else {
				jObj.put("StockOutCode", "");
			}
			posURL = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funSaveStockOut";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObj.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSStkOut.html?saddr=" + urlHits);

		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/loadStockOutDetails", method = RequestMethod.GET)
	public @ResponseBody clsPOSStockInOutHd funLoadStckInDetails(@RequestParam("StockOutCode") String stockOutCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSStockInOutHd objBean = null;

		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funGetStockOutData" + "?searchCode=" + stockOutCode;
		System.out.println(posUrl);

		try {
			URL url = new URL(posUrl);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = "", op = "";
			while ((output = br.readLine()) != null) {
				op += output;
			}
			System.out.println("Obj=" + op);
			conn.disconnect();

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(op);
			jObjSearchDetails = (JSONObject) obj;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray jArrSearchList = (JSONArray) jObjSearchDetails.get("StockOut");
		if (null != jArrSearchList) {
			String reasonName = "", purchaseBillNo = "", purchaseBillDate = "", StockOutCode = "";
			objBean = new clsPOSStockInOutHd();
			List listStkOutlist = new ArrayList<>();
			for (int cnt = 0; cnt < jArrSearchList.size(); cnt++) {
				JSONArray jArrList = (JSONArray) jArrSearchList.get(cnt);
				clsPOSStockInOutDtl objStockInOutDtl = new clsPOSStockInOutDtl();
				objStockInOutDtl.setStrItemCode((String) jArrList.get(2));
				objStockInOutDtl.setStrItemName((String) jArrList.get(1));
				objStockInOutDtl.setStrStkOutCode((String) jArrList.get(0));
				objStockInOutDtl.setDblQuantity(Double.valueOf(jArrList.get(3).toString()));
				objStockInOutDtl.setDblPurchaseRate(Double.valueOf(jArrList.get(4).toString()));
				objStockInOutDtl.setDblAmount(Double.valueOf(jArrList.get(5).toString()));
				StockOutCode = (String) jArrList.get(0);
				purchaseBillNo = (String) jArrList.get(6);
				purchaseBillDate = (String) jArrList.get(7);
				reasonName = (String) jArrList.get(9);
				listStkOutlist.add(objStockInOutDtl);
			}
			objBean.setListOfItem(listStkOutlist);
			objBean.setStrReasonCode(reasonName);
			objBean.setStrStkOutCode(StockOutCode);
			objBean.setStrPurchaseBillNo(purchaseBillNo);
			String[] billDate = purchaseBillDate.split("-");
			objBean.setDtePurchaseBillDate(billDate[2] + "-" + billDate[1] + "-" + billDate[0]);

		}
		return objBean;
	}

}
