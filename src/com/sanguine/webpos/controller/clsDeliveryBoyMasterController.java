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
import com.sanguine.webpos.bean.clsDeliveryBoyChargesBean;
import com.sanguine.webpos.bean.clsDeliveryBoyMasterBean;

@Controller
public class clsDeliveryBoyMasterController {

	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	Map map = new HashMap();

	@RequestMapping(value = "/frmPOSDeliveryPersonMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsDeliveryBoyMasterBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSDeliveryPersonMaster_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSDeliveryPersonMaster");
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/savePOSDeliveryBoyMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsDeliveryBoyMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		String posCode = "";
		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjSettlementMaster = new JSONObject();
			jObjSettlementMaster.put("DPCode", objBean.getStrDPCode());
			jObjSettlementMaster.put("DPName", objBean.getStrDPName());
			jObjSettlementMaster.put("Operational", objBean.getStrOperational());
			jObjSettlementMaster.put("User", webStockUserCode);
			jObjSettlementMaster.put("ClientCode", clientCode);

			// Delivery Charges Data

			List<clsDeliveryBoyChargesBean> list = objBean.getListDeliveryBoyCharges();
			JSONArray jArrList = new JSONArray();
			if (null != list) {
				for (int i = 0; i < list.size(); i++) {
					clsDeliveryBoyChargesBean obj = new clsDeliveryBoyChargesBean();
					obj = (clsDeliveryBoyChargesBean) list.get(i);

					JSONObject jObjData = new JSONObject();
					if (null != obj.getStrAreaCode()) {
						jObjData.put("AreaCode", obj.getStrAreaCode());
						jObjData.put("Incentives", obj.getDblIncentives());
						jArrList.add(jObjData);
						map.put(obj.getStrAreaCode(), obj.getStrAreaName());
					}
				}
			}
			jObjSettlementMaster.put("DeliveryBoyCharges", jArrList);

			String posURL = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funSaveDeliveryBoyMaster";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjSettlementMaster.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSDeliveryPersonMaster.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/loadPOSDeliveryBoyMasterData", method = RequestMethod.GET)
	public @ResponseBody clsDeliveryBoyMasterBean funSetSearchFields(@RequestParam("dpCode") String dpCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsDeliveryBoyMasterBean objPOSWaiterMaster = null;
		List<clsDeliveryBoyChargesBean> listSettleData = new ArrayList<clsDeliveryBoyChargesBean>();
		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetDeliveryBoyMasterData" + "?dpCode=" + dpCode + "&clientCode=" + clientCode;
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

		if (null != jObjSearchDetails) {
			objPOSWaiterMaster = new clsDeliveryBoyMasterBean();
			objPOSWaiterMaster.setStrDPCode((String) jObjSearchDetails.get("strDPCode"));
			objPOSWaiterMaster.setStrDPName((String) jObjSearchDetails.get("strDPName"));
			objPOSWaiterMaster.setStrOperational((String) jObjSearchDetails.get("strOperational"));
			JSONArray jArrDelChargesList = (JSONArray) jObjSearchDetails.get("SettleData");
			if (null != jArrDelChargesList) {
				for (int cnt = 0; cnt < jArrDelChargesList.size(); cnt++) {
					JSONObject jobj = (JSONObject) jArrDelChargesList.get(cnt);
					clsDeliveryBoyChargesBean objSettlementDtl = new clsDeliveryBoyChargesBean();
					objSettlementDtl.setStrAreaCode((String) jobj.get("AreaCode"));

					String areaCode = (String) jobj.get("AreaCode");
					String areaName = "";
					if (map.containsKey(areaCode)) {
						areaName = (String) map.get(areaCode);

					}
					objSettlementDtl.setStrAreaName(areaName);
					objSettlementDtl.setDblIncentives((long) jobj.get("Incentives"));

					listSettleData.add(objSettlementDtl);
				}
			}
			objPOSWaiterMaster.setListDeliveryBoyCharges(listSettleData);

		}

		if (null == objPOSWaiterMaster) {
			objPOSWaiterMaster = new clsDeliveryBoyMasterBean();
			objPOSWaiterMaster.setStrDPCode("Invalid Code");
		}

		return objPOSWaiterMaster;
	}

	@RequestMapping(value = "/checkDPName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckDPName(@RequestParam("name") String name, @RequestParam("code") String code, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		int count = objPOSGlobal.funCheckName(name, code, clientCode, "POSDeliveryBoyMaster");

		// int
		// count=objPOSGlobal.funCheckName("",Name,clientCode,"POSDeliveryBoyMaster");

		if (count > 0)
			return false;
		else
			return true;

	}

}
