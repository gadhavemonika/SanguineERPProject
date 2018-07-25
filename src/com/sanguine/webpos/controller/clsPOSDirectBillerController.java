package com.sanguine.webpos.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsDirectBillerBean;

@Controller
public class clsPOSDirectBillerController {

	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobalFunctionsController;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	// Open POSDirectBiller
	@RequestMapping(value = "/frmPOSDirectBiller", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String posClientCode = request.getSession().getAttribute("posClientCode").toString();
		String posCode = request.getSession().getAttribute("loginPOS").toString();
		String posDate = request.getSession().getAttribute("POSDate").toString().split(" ")[0];
		String userCode = request.getSession().getAttribute("usercode").toString();

		// direct biller model attribute
		clsDirectBillerBean objDirectBillerBean = new clsDirectBillerBean();

		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funGetItemPricingDtl?posCode=" + posCode + "&clientCode=" + clientCode + "&posDate=" + posDate);
		JSONArray jsonArrForDirectBillerMenuItemPricing = (JSONArray) jObj.get("MenuItemPricingDtl");
		objDirectBillerBean.setJsonArrForDirectBillerMenuItemPricing(jsonArrForDirectBillerMenuItemPricing);

		String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funGetMenuHeads" + "?posCode=" + posCode + "&userCode=" + userCode;
		jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(posURL);
		JSONArray jsonArrForDirectBillerMenuHeads = (JSONArray) jObj.get("MenuHeads");
		objDirectBillerBean.setJsonArrForDirectBillerMenuHeads(jsonArrForDirectBillerMenuHeads);

		posURL = clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funGetButttonList" + "?transName=DirectBiller&posCode=" + posCode + "&posClientCode=" + posClientCode;

		jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(posURL);

		JSONArray jsonArrForDirectBillerFooterButtons = (JSONArray) jObj.get("buttonList");
		objDirectBillerBean.setJsonArrForDirectBillerFooterButtons(jsonArrForDirectBillerFooterButtons);

		jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funPopularItem?posCode=" + posCode + "&clientCode=" + clientCode + "&posDate=" + posDate);

		JSONArray jsonArrForPopularItems = (JSONArray) jObj.get("PopularItems");

		objDirectBillerBean.setJsonArrForPopularItems(jsonArrForPopularItems);

		model.put("urlHits", urlHits);
		model.put("billNo", "");
		model.put("billDate", posDate.split("-")[2] + "-" + posDate.split("-")[1] + "-" + posDate.split("-")[0]);

		return new ModelAndView("frmPOSDirectBiller", "command", objDirectBillerBean);

	}

}
