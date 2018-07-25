package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
import com.sanguine.webpos.bean.clsPOSCustomerAreaMasterAmountBean;
import com.sanguine.webpos.bean.clsPOSCustomerAreaMasterBean;
import com.sanguine.webpos.bean.clsPOSCustomerMasterBean;
import com.sanguine.webpos.bean.clsPOSCustomerTypeMasterBean;
import com.sanguine.webpos.bean.clsPOSMasterBean;
import com.sanguine.webpos.bean.clsSettlementDetailsBean;

@Controller
public class clsPOSCustomerAreaMasterController {

	@Autowired
	private clsGlobalFunctions objGlobal;

	@RequestMapping(value = "/frmPOSCustAreaMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		// return new ModelAndView("frmPOSGroupMaster");

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSCustomerAreaMaster_1", "command", new clsPOSCustomerAreaMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSCustomerAreaMaster", "command", new clsPOSCustomerAreaMasterBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/savePOSCustomerAreaMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSCustomerAreaMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		JSONObject jSONObject = new JSONObject();
		List<clsPOSCustomerAreaMasterAmountBean> listdata = null;
		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjCustomerAreaMaster = new JSONObject();
			jObjCustomerAreaMaster.put("strCustomerAreaCode", objBean.getStrCustomerAreaCode());
			jObjCustomerAreaMaster.put("strCustomerAreaName", objBean.getStrCustomerAreaName());
			jObjCustomerAreaMaster.put("strAddress", objBean.getStrAddress());
			jObjCustomerAreaMaster.put("strHomeDeliveryCharges", objBean.getStrHomeDeliveryCharges());
			jObjCustomerAreaMaster.put("strZone", objBean.getStrZone());
			jObjCustomerAreaMaster.put("dblDeliveryBoyPayOut", objBean.getDblDeliveryBoyPayOut());
			jObjCustomerAreaMaster.put("strHelperPayOut", objBean.getStrHelperPayOut());

			listdata = objBean.getListCustAreaAmount();
			// System.out.println(listdata.size()+" listItem :"+listdata);
			clsPOSCustomerAreaMasterAmountBean obj;

			JSONArray jArrList = new JSONArray();
			if (null != listdata)

			{
				for (int i = 0; i < listdata.size(); i++) {

					obj = listdata.get(i);
					JSONObject jObjData = new JSONObject();
					jObjData.put("amount", obj.getDblAmount());
					jObjData.put("amount1", obj.getDblAmount1());
					jObjData.put("deliveryCharges", obj.getDblDeliveryCharges());
					jObjData.put("customerType", obj.getStrCustomerType());

					jArrList.add(jObjData);

				}
			}

			jObjCustomerAreaMaster.put("List", jArrList);
			jObjCustomerAreaMaster.put("User", webStockUserCode);
			jObjCustomerAreaMaster.put("ClientCode", clientCode);
			jObjCustomerAreaMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjCustomerAreaMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			System.out.println(objBean);

			String posURL = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funSaveCustomerAreaMaster";

			URL url = new URL(posURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjCustomerAreaMaster.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSCustAreaMaster.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/loadPOSCustomerAreaMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPOSCustomerAreaMasterBean funSetSearchFields(@RequestParam("POSCustomerAreaCode") String CustomerAreaCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSCustomerAreaMasterBean objPOSCustomerAreaMaster = null;
		List<clsPOSCustomerAreaMasterAmountBean> list = new ArrayList<clsPOSCustomerAreaMasterAmountBean>();

		JSONObject jObjSearchDetails = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funLoadCustomerAreaMasterData" + "?searchCode=" + CustomerAreaCode + "&clientCode=" + clientCode);

		if (null != jObjSearchDetails) {
			objPOSCustomerAreaMaster = new clsPOSCustomerAreaMasterBean();
			// objPOSCustomerAreaMaster.setStrCustomerAreaCode();
			objPOSCustomerAreaMaster.setStrCustomerAreaName(jObjSearchDetails.get("strBuildingName").toString());
			objPOSCustomerAreaMaster.setStrAddress(jObjSearchDetails.get("strAddress").toString());
			objPOSCustomerAreaMaster.setStrHomeDeliveryCharges((Double.parseDouble(jObjSearchDetails.get("dblHomeDeliCharge").toString())));
			objPOSCustomerAreaMaster.setStrZone(jObjSearchDetails.get("strZoneCode").toString());
			objPOSCustomerAreaMaster.setDblDeliveryBoyPayOut((Double.parseDouble(jObjSearchDetails.get("dblDeliveryBoyPayOut").toString())));
			objPOSCustomerAreaMaster.setStrHelperPayOut((Double.parseDouble(jObjSearchDetails.get("dblHelperPayOut").toString())));

		}

		JSONArray jArrCustomerAreaList = (JSONArray) jObjSearchDetails.get("CustomerDtlData");
		if (null != jArrCustomerAreaList) {
			for (int cnt = 0; cnt < jArrCustomerAreaList.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArrCustomerAreaList.get(cnt);
				clsPOSCustomerAreaMasterAmountBean objDtl = new clsPOSCustomerAreaMasterAmountBean();

				objDtl.setDblAmount(Double.parseDouble(jobj.get("dblBillAmount").toString()));
				objDtl.setDblAmount1(Double.parseDouble(jobj.get("dblBillAmount1").toString()));
				objDtl.setDblDeliveryCharges(Double.parseDouble(jobj.get("dblDeliveryCharges").toString()));
				objDtl.setStrCustomerType((String) jobj.get("strCustTypeCode"));

				list.add(objDtl);
			}
		}
		objPOSCustomerAreaMaster.setListCustAreaAmount(list);

		if (null == objPOSCustomerAreaMaster) {
			objPOSCustomerAreaMaster = new clsPOSCustomerAreaMasterBean();
			objPOSCustomerAreaMaster.setStrCustomerAreaCode("Invalid Code");
		}

		return objPOSCustomerAreaMaster;
	}

}
