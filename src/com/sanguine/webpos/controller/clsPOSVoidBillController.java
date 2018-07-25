package com.sanguine.webpos.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsPOSVoidKotBean;

@Controller
public class clsPOSVoidBillController {

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	clsPOSGlobalFunctionsController objPOSGlobalFunctionsController;
	@Autowired
	clsPOSVoidKotController objVoidController;

	@RequestMapping(value = "/frmPOSVoidBill", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String strPosCode = request.getSession().getAttribute("loginPOS").toString();
		model.put("urlHits", urlHits);

		List listReson = funLoadResonCode();
		model.put("listReson", listReson);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSVoidBill_1", "command", new clsPOSVoidKotBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSVoidBill", "command", new clsPOSVoidKotBean());
		} else {
			return null;
		}
	}

	public List funLoadResonCode() {

		List listResonCombo = new ArrayList();
		JSONArray jArry = new JSONArray();
		try {
			String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/webPosVoidKot/funLoadReson";
			JSONObject objRows = new JSONObject();

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(posUrl, objRows);
			JSONArray jarr = (JSONArray) jObj.get("jArr");
			for (int i = 0; i < jarr.size(); i++) {
				JSONObject jObjtemp = (JSONObject) jarr.get(i);
				listResonCombo.add(jObjtemp.get("resoncode").toString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listResonCombo;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/fillBillGridData", method = RequestMethod.GET)
	public @ResponseBody LinkedList funFillRGridData(HttpServletRequest req) {

		String strPosCode = req.getSession().getAttribute("loginPOS").toString();

		LinkedList listFillGrid = new LinkedList();
		try {
			String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSVoidBill/funVoidBill";
			JSONObject objRows = new JSONObject();

			JSONObject jobj = objPOSGlobalFunctionsController.funGetPOSDate(req);
			String SearchBillNo = "";

			String posDate = jobj.get("POSDate").toString();
			objRows.put("posDate", posDate);
			objRows.put("strPosCode", strPosCode);
			objRows.put("SearchBillNo", SearchBillNo);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(posUrl, objRows);
			JSONArray jarr = (JSONArray) jObj.get("jArr");

			for (int i = 0; i < jarr.size(); i++) {
				JSONObject jObjtemp = (JSONObject) jarr.get(i);
				LinkedList setFillGrid = new LinkedList();
				setFillGrid.add(jObjtemp.get("strBillNo").toString());
				setFillGrid.add(jObjtemp.get("dteBillDate").toString());
				setFillGrid.add(Double.parseDouble(jObjtemp.get("dblGrandTotal").toString()));
				setFillGrid.add(jObjtemp.get("strTableName").toString());
				listFillGrid.add(setFillGrid);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listFillGrid;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/fillBillDtlData", method = RequestMethod.GET)
	public @ResponseBody Map funFillBillDtla(HttpServletRequest req) {

		LinkedList listFillGrid = new LinkedList();
		String strPosCode = req.getSession().getAttribute("loginPOS").toString();
		String strClientCode = req.getSession().getAttribute("clientCode").toString();
		Map mapFilltable = new HashMap();

		try {
			String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSVoidBill/funfillBillDtlData";
			JSONObject objRows = new JSONObject();
			JSONObject jobj = objPOSGlobalFunctionsController.funGetPOSDate(req);

			String billNo = req.getParameter("billNo");
			String posDate = jobj.get("POSDate").toString();
			objRows.put("posDate", posDate);
			objRows.put("strPosCode", strPosCode);
			objRows.put("billNo", billNo);
			objRows.put("strClientCode", strClientCode);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(posUrl, objRows);
			JSONArray jarr = (JSONArray) jObj.get("jArr");

			for (int i = 0; i < jarr.size(); i++) {
				JSONObject jObjtemp = (JSONObject) jarr.get(i);
				LinkedList setFillGrid = new LinkedList();
				setFillGrid.add(jObjtemp.get("strItemName").toString());
				setFillGrid.add(jObjtemp.get("dblQuantity").toString());
				setFillGrid.add(Double.parseDouble(jObjtemp.get("dblAmount").toString()));
				setFillGrid.add(jObjtemp.get("strItemCode").toString());
				setFillGrid.add(jObjtemp.get("strKOTNo").toString());
				listFillGrid.add(setFillGrid);
				JSONArray jArrMod = (JSONArray) jObjtemp.get("ModifierData");
				for (int j = 0; j < jArrMod.size(); j++) {
					JSONObject jobjMod = (JSONObject) jArrMod.get(j);
					setFillGrid = new LinkedList();
					setFillGrid.add(jobjMod.get("modifierName").toString());
					setFillGrid.add(jobjMod.get("dblQuantityMod").toString());
					setFillGrid.add(Double.parseDouble(jobjMod.get("dblAmountMod").toString()));
					setFillGrid.add(jobjMod.get("strModifierCode").toString());
					setFillGrid.add(jobjMod.get("strItemCodeMod").toString());
					listFillGrid.add(setFillGrid);

				}

			}
			double totalAmount = Double.parseDouble(jObj.get("grandTotal").toString());
			double tax = Double.parseDouble(jObj.get("totalTaxAmount").toString());
			double subTotalAmt = Double.parseDouble(jObj.get("subTotal").toString());
			String userCreated = jObj.get("userCreated").toString();
			mapFilltable.put("listFillGrid", listFillGrid);
			mapFilltable.put("totalAmount", totalAmount);
			mapFilltable.put("taxAmt", tax);
			mapFilltable.put("userCreated", userCreated);
			mapFilltable.put("subTotalAmt", subTotalAmt);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mapFilltable;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/voidItem", method = RequestMethod.GET)
	@ResponseBody
	public String funVoidBill(HttpServletRequest req) {

		String result = "";
		String userCode = req.getSession().getAttribute("usercode").toString();
		try {
			JSONObject jobj = objPOSGlobalFunctionsController.funGetPOSDate(req);
			JSONObject jservice = new JSONObject();
			String posDate = jobj.get("POSDate").toString();
			String strPosCode = req.getSession().getAttribute("loginPOS").toString();
			JSONArray jarr1 = new JSONArray();
			String[] arrDelQty = null;
			String[] arreDelAmount = null;
			String[] arrDelItemName = null;
			String itemCode = req.getParameter("delItemcode").toString();
			String billNo = req.getParameter("delbillNo").toString();
			String tableNo = req.getParameter("delTableNo").toString();
			String reasonCode = req.getParameter("reasonCode").toString();
			String remarks = req.getParameter("remarks").toString();
			String itemName = req.getParameter("delItemName").toString();
			String quantity = req.getParameter("quantity").toString();
			String amount = req.getParameter("delAmount").toString();
			double taxAmt = Double.parseDouble(req.getParameter("taxAmt").toString());

			String modItemCode = req.getParameter("delModItemcode").toString();

			JSONArray jArrDelItemCode = new JSONArray();
			JSONArray jArrDelQty = new JSONArray();
			JSONArray jArreDelAmount = new JSONArray();
			JSONArray jArrDelItemName = new JSONArray();
			String clientCode = req.getSession().getAttribute("clientCode").toString();

			String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSVoidBill/funVoidItemData";
			JSONObject objRows = new JSONObject();

			objRows.put("billNo", billNo);
			objRows.put("delTableNo", tableNo);
			objRows.put("remarks", remarks);
			objRows.put("reasonCode", reasonCode);
			objRows.put("taxAmt", taxAmt);
			objRows.put("voidedDate", posDate);
			objRows.put("clientCode", clientCode);
			objRows.put("userCode", userCode);
			objRows.put("itemCode", itemCode);
			objRows.put("quantity", Double.parseDouble(quantity));
			objRows.put("amount", Double.parseDouble(amount));
			objRows.put("itemName", itemName);
			objRows.put("modItemCode", modItemCode);
			objRows.put("strPosCode", strPosCode);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(posUrl, objRows);
			result = jObj.get("true").toString();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return result;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/fullVoidBillButtonClick", method = RequestMethod.GET)
	@ResponseBody
	public String funFullVoidBill(HttpServletRequest req) {
		String result = "";
		try {
			JSONObject jobj = objPOSGlobalFunctionsController.funGetPOSDate(req);
			JSONObject jservice = new JSONObject();
			String voidedDate = jobj.get("POSDate").toString();
			String strPosCode = req.getSession().getAttribute("loginPOS").toString();
			String billNo = req.getParameter("billNo").toString();
			String reasonCode = req.getParameter("reasonCode").toString();
			String remarks = req.getParameter("remarks").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();

			String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSVoidBill/fullVoidBill";
			JSONObject objRows = new JSONObject();

			objRows.put("billNo", billNo);
			objRows.put("remarks", remarks);
			objRows.put("reasonCode", reasonCode);
			objRows.put("voidedDate", voidedDate);
			objRows.put("clientCode", clientCode);
			objRows.put("userCode", userCode);
			objRows.put("strPosCode", strPosCode);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(posUrl, objRows);
			result = jObj.get("sucessfully").toString();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return result;

	}
}
