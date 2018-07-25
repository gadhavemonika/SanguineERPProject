package com.sanguine.webpos.controller;

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
import com.sanguine.webpos.bean.clsPOSCustomerTypeMasterBean;

@Controller
public class clsPOSCustomerTypeMasterController {

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	@RequestMapping(value = "/frmPOSCustomerTypeMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request)

	{
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		// return new ModelAndView("frmPOSGroupMaster");

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSCustomerTypeMaster_1", "command", new clsPOSCustomerTypeMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSCustomerTypeMaster", "command", new clsPOSCustomerTypeMasterBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/checkCustomerTypeName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckAreaName(@RequestParam("strTypeMasterCode") String code, @RequestParam("strCustomerType") String Name, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		int count = objPOSGlobal.funCheckName(Name, code, clientCode, "POSCustomerTypeMaster");

		if (count > 0)
			return false;
		else
			return true;

	}

	@RequestMapping(value = "/savePOSCustomerTypeMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSCustomerTypeMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";

		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjCustomerTypeMaster = new JSONObject();
			jObjCustomerTypeMaster.put("CustomerTypeCode", objBean.getStrCustomerTypeMasterCode());
			jObjCustomerTypeMaster.put("CustomerType", objBean.getStrCustomerType());
			jObjCustomerTypeMaster.put("Discount", objBean.getDblDiscount());
			jObjCustomerTypeMaster.put("User", webStockUserCode);
			jObjCustomerTypeMaster.put("ClientCode", clientCode);
			/*
			 * //jObjGroupMaster.put("Operational",
			 * objBean.getStrOperational()); jObjGroupMaster.put("Operational",
			 * "N"); jObjGroupMaster.put("OperationType",
			 * objBean.getStrOperationType());
			 */
			jObjCustomerTypeMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjCustomerTypeMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			System.out.println(objBean);

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funSaveCustomerTypeMaster";

			URL url = new URL(posURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjCustomerTypeMaster.toString().getBytes());
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

			// sString result1=objGlobal.funPOSTMethodUrlJosnObjectData(posURL,
			// jObjCustomerTypeMaster);

			req.getSession().setAttribute("success", true);

			req.getSession().setAttribute("successMessage", " " + op);

			return new ModelAndView("redirect:/frmPOSCustomerTypeMaster.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadPOSCustomerTypeMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPOSCustomerTypeMasterBean funSetSearchFields(@RequestParam("POSCustomerTypeCode") String CustomerTypeCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSCustomerTypeMasterBean objPOSCustomerTypeMaster = null;

		JSONObject jObjSearchDetails = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funLoadCustomerTypeMasterData" + "?searchCode=" + CustomerTypeCode + "&clientCode=" + clientCode);
		if (null != jObjSearchDetails) {
			objPOSCustomerTypeMaster = new clsPOSCustomerTypeMasterBean();
			objPOSCustomerTypeMaster.setStrCustomerTypeMasterCode(jObjSearchDetails.get("strCustTypeCode").toString());
			objPOSCustomerTypeMaster.setStrCustomerType(jObjSearchDetails.get("strCustType").toString());
			objPOSCustomerTypeMaster.setDblDiscount(Double.parseDouble(jObjSearchDetails.get("dblDiscPer").toString()));

		}
		if (null == objPOSCustomerTypeMaster) {
			objPOSCustomerTypeMaster = new clsPOSCustomerTypeMasterBean();
			objPOSCustomerTypeMaster.setStrCustomerTypeMasterCode("Invalid Code");
		}

		return objPOSCustomerTypeMaster;
	}

}
