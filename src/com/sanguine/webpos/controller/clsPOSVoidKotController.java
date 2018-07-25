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

import com.google.gson.JsonArray;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsPOSVoidKotBean;

@Controller
public class clsPOSVoidKotController {

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	clsPOSGlobalFunctionsController objPOSGlobalFunctionsController;

	@RequestMapping(value = "/frmPOSVoidKot", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String strPosCode = request.getSession().getAttribute("loginPOS").toString();
		model.put("urlHits", urlHits);
		Map<String, String> mapTableCombo = funLoadTableData(strPosCode);
		List listTableCombo = new ArrayList();
		listTableCombo.add("All Tables");
		for (Map.Entry map : mapTableCombo.entrySet()) {
			listTableCombo.add(map.getKey());

		}
		model.put("tableData", listTableCombo);
		List listReson = funLoadResonCode();
		model.put("listReson", listReson);
		// return new ModelAndView("frmPOSGroupMaster");

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSVoidKot_1", "command", new clsPOSVoidKotBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSVoidKot", "command", new clsPOSVoidKotBean());
		} else {
			return null;
		}

	}

	public Map funLoadTableData(String strPosCode) {

		Map mapTableCombo = new HashMap<String, String>();
		JSONArray jArry = new JSONArray();
		try {
			String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/webPosVoidKot/funLoadTable";
			JSONObject objRows = new JSONObject();
			objRows.put("strPosCode", strPosCode);
			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(posUrl, objRows);
			JSONArray jarr = (JSONArray) jObj.get("jArr");
			for (int i = 0; i < jarr.size(); i++) {
				JSONObject jObjtemp = (JSONObject) jarr.get(i);
				mapTableCombo.put(jObjtemp.get("strTableName").toString(), jObjtemp.get("strTableNo").toString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mapTableCombo;
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
	@RequestMapping(value = "/fillRGridData", method = RequestMethod.GET)
	public @ResponseBody LinkedList funFillRGridData(HttpServletRequest req) {

		String tableNo = "";
		String strPosCode = req.getSession().getAttribute("loginPOS").toString();
		String tableName = req.getParameter("tableName");
		Map mapFillGrid = new HashMap();
		LinkedList listFillGrid = new LinkedList();
		try {
			String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/webPosVoidKot/funFillHelpGrid";
			JSONObject objRows = new JSONObject();
			if (!tableName.equalsIgnoreCase("All Tables")) {
				Map<String, String> mapTableData = funLoadTableData(strPosCode);
				tableNo = mapTableData.get(tableName);
			}

			objRows.put("tableNo", tableNo);
			objRows.put("tableName", tableName);
			objRows.put("strPosCode", strPosCode);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(posUrl, objRows);
			JSONArray jarr = (JSONArray) jObj.get("jArr");

			for (int i = 0; i < jarr.size(); i++) {
				JSONObject jObjtemp = (JSONObject) jarr.get(i);
				LinkedList setFillGrid = new LinkedList();
				setFillGrid.add(jObjtemp.get("strKOTNo").toString());
				setFillGrid.add(jObjtemp.get("strTableName").toString());
				setFillGrid.add(jObjtemp.get("strWShortName").toString());
				setFillGrid.add(jObjtemp.get("strTakeAwayYesNo").toString());
				setFillGrid.add(jObjtemp.get("strUserCreated").toString());
				// setFillGrid.add(Integer.parseInt(jObjtemp.get("intPaxNo").toString()));
				setFillGrid.add(Double.parseDouble(jObjtemp.get("dblAmount").toString()));
				// setFillGrid.add( jObjtemp.get("strManualKOTNo").toString());

				listFillGrid.add(setFillGrid);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listFillGrid;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/fillItemDataTable", method = RequestMethod.GET)
	@ResponseBody
	public Map funFillItemTableData(HttpServletRequest req) {

		String KotNo = req.getParameter("kot");

		String strPosCode = req.getSession().getAttribute("loginPOS").toString();
		// String tableNo=req.getParameter("tableNo");
		String tableNo = "";
		String tableName = req.getParameter("cmbTableName").toString();
		Map mapFilltable = new HashMap();
		LinkedList listFillItemGrid = new LinkedList();
		try {
			String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/webPosVoidKot/fillItemTableData";
			JSONObject objRows = new JSONObject();
			if (!tableName.equalsIgnoreCase("All Tables")) {
				Map<String, String> mapTableData = funLoadTableData(strPosCode);
				tableNo = mapTableData.get(tableName);
			}

			objRows.put("tableNo", tableNo);
			objRows.put("KotNo", KotNo);
			objRows.put("strPosCode", strPosCode);
			objRows.put("tableName", tableName);
			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(posUrl, objRows);
			JSONArray jarr = (JSONArray) jObj.get("jArr");
			for (int i = 0; i < jarr.size(); i++) {

				JSONObject jObjtemp = (JSONObject) jarr.get(i);
				LinkedList setFillGrid = new LinkedList();
				setFillGrid.add(jObjtemp.get("strItemName").toString());
				setFillGrid.add(Double.parseDouble(jObjtemp.get("dblItemQuantity").toString()));
				setFillGrid.add(Double.parseDouble(jObjtemp.get("dblAmount").toString()));
				setFillGrid.add(jObjtemp.get("strItemCode").toString());
				setFillGrid.add(jObjtemp.get("strUserCreated").toString());
				setFillGrid.add(jObjtemp.get("dteDateCreated").toString());

				listFillItemGrid.add(setFillGrid);

			}

			double totalAmount = Double.parseDouble(jObj.get("totalAmount").toString());
			double tax = Double.parseDouble(jObj.get("taxAmt").toString());
			double subTotalAmt = Double.parseDouble(jObj.get("subTotalAmt").toString());
			mapFilltable.put("listFillItemGrid", listFillItemGrid);
			mapFilltable.put("totalAmount", totalAmount);
			mapFilltable.put("taxAmt", tax);
			mapFilltable.put("KotNo", KotNo);
			mapFilltable.put("subTotalAmt", subTotalAmt);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return mapFilltable;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/doneButtonClick", method = RequestMethod.GET)
	@ResponseBody
	public String funDoneButtonClick1(HttpServletRequest req) {

		String result = "";
		try {
			JSONObject jobj = objPOSGlobalFunctionsController.funGetPOSDate(req);
			JSONObject jservice = new JSONObject();
			String voidedDate = jobj.get("POSDate").toString();
			String strPosCode = req.getSession().getAttribute("loginPOS").toString();
			JSONArray jarr1 = new JSONArray();
			String[] arrDelQty = null;
			String[] arreDelAmount = null;
			String[] arrDelItemName = null;
			String delItemcode = req.getParameter("delItemcode").toString();
			String delKotNo = req.getParameter("delKotNo").toString();
			String tableNo = req.getParameter("delTableNo").toString();
			String reasonCode = req.getParameter("reasonCode").toString();
			String remarks = req.getParameter("remarks").toString();
			String delItemName = req.getParameter("delItemName").toString();
			String delQuatity = req.getParameter("delQuatity").toString();
			String delAmount = req.getParameter("delAmount").toString();
			double taxAmt = Double.parseDouble(req.getParameter("taxAmt").toString());
			String[] itemCode = delItemcode.split("aa");
			String[] qty = delQuatity.split("aa");

			String[] amount = delAmount.split("aa");
			String[] itemName = delItemName.split("//aa");
			// arrDelItemCode=new String[itemCode.length-1];

			Map<String, String> mapTableData = funLoadTableData(strPosCode);
			String delTableNo = mapTableData.get(tableNo);

			for (int i = 1; i < itemCode.length; i++) {
				JSONObject jObj1 = new JSONObject();
				jObj1.put("itemCode", itemCode[i]);
				jObj1.put("qty", qty[i]);
				jObj1.put("amount", amount[i]);
				jObj1.put("itemName", itemName[i]);
				jarr1.add(jObj1);

			}
			jservice.put("jarr1", jarr1);

			arrDelQty = new String[qty.length - 1];
			JSONObject obj = new JSONObject();
			for (int i = 1; i < qty.length; i++) {
				// arrDelQty[i-1]=qty[i];
				// jArrDelItemCode.add(qty[i]);

				obj.put("Qty", qty);
			}

			arreDelAmount = new String[amount.length - 1];
			for (int i = 1; i < amount.length; i++) {
				arreDelAmount[i - 1] = amount[i];
			}

			arrDelItemName = new String[itemName.length - 1];
			for (int i = 1; i < itemName.length; i++) {
				arrDelItemName[i - 1] = itemName[i];
			}
			JSONArray jArrDelItemCode = new JSONArray();
			JSONArray jArrDelQty = new JSONArray();
			JSONArray jArreDelAmount = new JSONArray();
			JSONArray jArrDelItemName = new JSONArray();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();

			String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/webPosVoidKot/funDoneBtnclick";
			JSONObject objRows = new JSONObject();

			objRows.put("delKotNo", delKotNo);
			objRows.put("delTableNo", delTableNo);
			objRows.put("remarks", remarks);

			objRows.put("reasonCode", reasonCode);
			objRows.put("taxAmt", taxAmt);
			objRows.put("voidedDate", voidedDate);
			objRows.put("clientCode", clientCode);
			objRows.put("userCode", userCode);
			objRows.put("jservice", jservice);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(posUrl, objRows);
			result = jObj.get("true").toString();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return result;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/fullVoidKotButtonClick", method = RequestMethod.GET)
	@ResponseBody
	public String funFullVoidKot(HttpServletRequest req) {
		String result = "";
		try {
			JSONObject jobj = objPOSGlobalFunctionsController.funGetPOSDate(req);
			JSONObject jservice = new JSONObject();
			String voidedDate = jobj.get("POSDate").toString();
			String strPosCode = req.getSession().getAttribute("loginPOS").toString();
			String delKotNo = req.getParameter("delKotNo").toString();
			String reasonCode = req.getParameter("reasonCode").toString();
			String remarks = req.getParameter("remarks").toString();
			// String reasonCode,String Kot,String strClientCode,String
			// voidedDate,String userCode,String voidKOTRemark
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();

			String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/webPosVoidKot/funClickFullVoidKot";
			JSONObject objRows = new JSONObject();

			objRows.put("delKotNo", delKotNo);
			objRows.put("remarks", remarks);
			objRows.put("reasonCode", reasonCode);
			objRows.put("voidedDate", voidedDate);
			objRows.put("clientCode", clientCode);
			objRows.put("userCode", userCode);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(posUrl, objRows);
			result = jObj.get("sucessfully").toString();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return result;

	}

}
