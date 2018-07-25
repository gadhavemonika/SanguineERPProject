package com.sanguine.webpos.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsPOSMakeBillBean;
import com.sanguine.webpos.bean.clsPOSMakeKOTBean;

@Controller
public class clsPOSMakeBillController {
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;
	@Autowired
	private clsGlobalFunctions objGlobal;

	@RequestMapping(value = "/frmPOSMakeBill", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		clsPOSMakeBillBean objMakeBillBean = new clsPOSMakeBillBean();

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String posCode = request.getSession().getAttribute("loginPOS").toString();
		String posDate = request.getSession().getAttribute("POSDate").toString().split(" ")[0];

		// get and set menu item pricing for direct biller in json
		String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeBillController/funLoadOccupiedTableDtl" + "?clientCode=" + clientCode + "&posCode=" + posCode;

		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(posURL);
		JSONArray jsonArrForTableDtl = (JSONArray) jObj.get("tableDtl");
		String areaName = (String) jObj.get("areaName");

		objMakeBillBean.setJsonArrForTableDtl(jsonArrForTableDtl);

		model.put("areaName", areaName);
		model.put("gCustAddressSelectionForBill", clsPOSGlobalFunctionsController.hmPOSSetupValues.get("CustAddressSelectionForBill"));
		model.put("gCRMInterface", clsPOSGlobalFunctionsController.hmPOSSetupValues.get("CRMInterface"));
		model.put("gPrintType", clsPOSGlobalFunctionsController.hmPOSSetupValues.get("PrintType"));
		return new ModelAndView("frmPOSMakeBill", "command", objMakeBillBean);

	}

	@RequestMapping(value = "/funLoadTableForArea", method = RequestMethod.GET)
	public @ResponseBody JSONObject funLoadTableForArea(@RequestParam("areaCode") String areaCode, HttpServletRequest request) {
		String posCode = request.getSession().getAttribute("loginPOS").toString();
		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeBillController/funLoadTableForArea?areaCode=" + areaCode + "&posCode=" + posCode);

		return jObj;
	}

	@RequestMapping(value = "/funFillItemTableDtl", method = RequestMethod.GET)
	public @ResponseBody JSONObject funFillItemTableDtl(@RequestParam("tableNo") String tableNo, HttpServletRequest request) {
		String posCode = request.getSession().getAttribute("loginPOS").toString();
		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeBillController/funFillItemTableDtl?tableNo=" + tableNo + "&posCode=" + posCode);

		return jObj;
	}

}
