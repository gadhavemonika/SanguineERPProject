package com.sanguine.webpos.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsBillSettlementBean;
import com.sanguine.webpos.bean.clsWebPOSReportBean;

@Controller
public class clsPOSBillSettlement {

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	clsPOSGlobalFunctionsController objPOSGlobalFunctionsController;

	@RequestMapping(value = "/frmPOSRestaurantDtl", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSettleBillFrontEnd_1", "command", new clsBillSettlementBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSettleBillFrontEnd", "command", new clsBillSettlementBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/fillUnsettleBillData", method = RequestMethod.GET)
	public @ResponseBody Map funFillUnSettleBill(Map<String, Object> model, HttpServletRequest req) {
		List listUnsettlebill = new ArrayList();
		Map hmUnsettleBill = new HashMap();
		try {
			String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSBillSettlement/funFillUnsettleBillData";
			JSONObject objRows = new JSONObject();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String strPosCode = req.getSession().getAttribute("loginPOS").toString();
			JSONObject jobj = objPOSGlobalFunctionsController.funGetPOSDate(req);
			String posDate = jobj.get("POSDate").toString();

			objRows.put("posDate", posDate);
			objRows.put("strPosCode", strPosCode);
			objRows.put("clientCode", clientCode);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(posUrl, objRows);
			String gShowBillsType = jObj.get("gShowBillsType").toString();
			String gCMSIntegrationYN = jObj.get("gCMSIntegrationYN").toString();
			JSONArray jarr = (JSONArray) jObj.get("jArr");

			for (int i = 0; i < jarr.size(); i++) {
				LinkedList setFillGrid = new LinkedList();
				JSONObject jObjtemp = (JSONObject) jarr.get(i);
				if (gShowBillsType.equalsIgnoreCase("Table Detail Wise")) {

					setFillGrid.add(jObjtemp.get("strBillNo").toString());
					setFillGrid.add(jObjtemp.get("strTableName").toString());
					setFillGrid.add(jObjtemp.get("strWShortName").toString());
					setFillGrid.add(jObjtemp.get("strCustomerName").toString());
					setFillGrid.add(jObjtemp.get("dteBillDate").toString());
					setFillGrid.add(Double.parseDouble(jObjtemp.get("dblGrandTotal").toString()));
					listUnsettlebill.add(setFillGrid);
				} else {
					setFillGrid.add(jObjtemp.get("strBillNo").toString());
					setFillGrid.add(jObjtemp.get("strTableName").toString());
					setFillGrid.add(jObjtemp.get("strCustomerName").toString());
					setFillGrid.add(jObjtemp.get("strBuildingName").toString());
					setFillGrid.add(jObjtemp.get("strDPName").toString());
					setFillGrid.add(jObjtemp.get("dteBillDate").toString());
					setFillGrid.add(Double.parseDouble(jObjtemp.get("dblGrandTotal").toString()));
					listUnsettlebill.add(setFillGrid);

				}

				hmUnsettleBill.put("listUnsettlebill", listUnsettlebill);
				hmUnsettleBill.put("gShowBillsType", gShowBillsType);
				hmUnsettleBill.put("gCMSIntegrationYN", gCMSIntegrationYN);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return hmUnsettleBill;
	}

	@RequestMapping(value = "/fillBillSettlementData", method = RequestMethod.GET)
	public ModelAndView funOpenBillSettlement(@ModelAttribute("command") clsBillSettlementBean objBean, Map<String, Object> model, HttpServletRequest request) {
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String urlHits = "2";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		// String billNo=request.getParameter("bill").toString();
		// String
		// selectedTableNo=request.getParameter("selectedTableNo").toString();
		// String
		// selectedRowIndex=request.getParameter("selectedRowIndex").toString();

		String billNo = objBean.getStrBillNo();
		String selectedTableNo = objBean.getStrTableNo();
		String selectedRowIndex = objBean.getSelectedRow();

		String billType = "";
		//
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSBillSettlement/fillRowSelected";
		JSONObject objRows = new JSONObject();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String strPosCode = request.getSession().getAttribute("loginPOS").toString();
		JSONObject jobj = objPOSGlobalFunctionsController.funGetPOSDate(request);
		String posDate = jobj.get("POSDate").toString();
		String isSuperUser = request.getSession().getAttribute("superuser").toString();
		boolean superuser = true;
		if ("YES".equalsIgnoreCase(isSuperUser)) {
			superuser = true;
		}

		request.setAttribute("billNo", billNo);

		objRows.put("billNo", billNo);
		objRows.put("selectedTableNo", selectedTableNo);
		objRows.put("selectedRowIndex", selectedRowIndex);
		objRows.put("clientCode", clientCode);
		objRows.put("posCode", strPosCode);
		objRows.put("billType", billType);
		objRows.put("superuser", superuser);

		JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(posUrl, objRows);

		List listSettlemode = (List) jObj.get("jArrSettlementMode");
		model.put("listSettlemode", listSettlemode);

		// String path=request.getContextPath().toString();
		// try{
		// // String searchUrl="/fillBillSettlementData.html?";
		// res.sendRedirect("fillBillSettlementData.html?");
		//
		// }catch(Exception e){
		// e.printStackTrace();
		// }
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmBillSettlement_1", "command", new clsBillSettlementBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmBillSettlement", "command", new clsBillSettlementBean());
		} else {
			return null;
		}

	}
}
