package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsPOSAssignHomeDeliveryBean;
import com.sanguine.webpos.bean.clsPOSCounterMasterBean;
import com.sanguine.webpos.bean.clsPOSMasterBean;
import com.sanguine.webpos.bean.clsPOSMenuHeadBean;
import com.sanguine.webpos.bean.clsPOSMultiBillSettleInCashBean;
import com.sanguine.webpos.bean.clsPOSTableMasterBean;

@Controller
public class clsMultiBillSettleInCashController {

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	Map map = new HashMap();

	@RequestMapping(value = "/frmPOSMultiBillSettle", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsPOSAssignHomeDeliveryBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		model.put("urlHits", urlHits);
		List list = funLoadUnsettleBillDtlData(request);

		model.put("tblheader", list.get(0));
		model.put("details", list.get(1));
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSMultiBillSettle_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSMultiBillSettle");
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/loadUnsettleBillDtlData", method = RequestMethod.GET)
	public List funLoadUnsettleBillDtlData(HttpServletRequest req) {
		List listmain = new ArrayList();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String posCode = req.getSession().getAttribute("loginPOS").toString();
		String posUrL = clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOSDate" + "?POSCode=" + posCode;
		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(posUrL);

		String posDate = (String) jObj.get("POSDate");

		// JSONArray jArrUnsettleBillList=null;

		List<clsPOSMultiBillSettleInCashBean> listMenuHedaData = new ArrayList<clsPOSMultiBillSettleInCashBean>();
		JSONObject jObjUnsettleBillData = new JSONObject();

		String posURL1 = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funGetSettleBillDtlData1" + "?clientCode=" + clientCode + "&posCode=" + posCode + "&posDate=" + URLEncoder.encode(posDate);
		JSONObject jObj1 = objGlobal.funGETMethodUrlJosnObjectData(posURL1);

		// jArrUnsettleBillList=(JSONArray) jObj1.get("UnsettleBillDtl");
		JSONArray jArrUnsettleBillList = (JSONArray) jObj1.get("UnsettleBillDtl");

		String strDataType = (String) jObj1.get("DataType");

		List<String> list = new ArrayList();
		if (strDataType.equals("TableDetailWise")) {
			list.add("Bill NO");
			list.add("Table");
			list.add("Waiter");
			list.add("Customer");
			list.add("Time");
			list.add("Amount");
			list.add("Select");
			listmain.add(list);
		} else {
			list.add("Bill NO");
			list.add("Table");
			list.add("Customer");
			list.add("Area");
			list.add("Delivery Boy");
			list.add("Time");
			list.add("Amount");
			list.add("Select");

			listmain.add(list);
		}

		if (strDataType.equals("TableDetailWise")) {
			if (null != jArrUnsettleBillList) {
				for (int cnt = 0; cnt < jArrUnsettleBillList.size(); cnt++) {
					JSONObject jobj = (JSONObject) jArrUnsettleBillList.get(cnt);
					clsPOSMultiBillSettleInCashBean objUnsettleBillDtl = new clsPOSMultiBillSettleInCashBean();
					objUnsettleBillDtl.setStrBillNo((String) jobj.get("strBillNo"));
					map.put(jobj.get("strTableName"), jobj.get("strTableNo"));
					// objUnsettleBillDtl.put("strTableName", strTableName);
					objUnsettleBillDtl.setStrTableName((String) jobj.get("strTableName"));

					objUnsettleBillDtl.setStrWShortName((String) jobj.get("strWShortName"));
					objUnsettleBillDtl.setStrCustomerName((String) jobj.get("strCustomerName"));

					objUnsettleBillDtl.setDteBillDate((String) jobj.get("dteBillDate"));
					objUnsettleBillDtl.setDblGrandTotal((long) jobj.get("dblGrandTotal"));

					listMenuHedaData.add(objUnsettleBillDtl);
				}

				listmain.add(listMenuHedaData);
			}
		} else {
			if (null != jArrUnsettleBillList) {
				for (int cnt = 0; cnt < jArrUnsettleBillList.size(); cnt++) {
					JSONObject jobj = (JSONObject) jArrUnsettleBillList.get(cnt);
					clsPOSMultiBillSettleInCashBean objUnsettleBillDtl = new clsPOSMultiBillSettleInCashBean();
					objUnsettleBillDtl.setStrBillNo((String) jobj.get("strBillNo"));
					map.put(jobj.get("strTableName"), jobj.get("strTableNo"));
					// objUnsettleBillDtl.put("strTableName", strTableName);
					objUnsettleBillDtl.setStrTableName((String) jobj.get("strTableName"));

					objUnsettleBillDtl.setStrCustomerName((String) jobj.get("strCustomerName"));
					objUnsettleBillDtl.setStrBuildingName((String) jobj.get("strBuildingName"));
					objUnsettleBillDtl.setStrDPName((String) jobj.get("strDPName"));
					objUnsettleBillDtl.setDteBillDate((String) jobj.get("dteBillDate"));
					objUnsettleBillDtl.setDblGrandTotal((long) jobj.get("dblGrandTotal"));

					listMenuHedaData.add(objUnsettleBillDtl);
				}

				listmain.add(listMenuHedaData);
			}

		}

		JSONObject jObjUnsettleBillDataDtl = new JSONObject();

		jObjUnsettleBillDataDtl.put("UnsettleBillDtl", jArrUnsettleBillList);
		jObjUnsettleBillDataDtl.put("DataType", strDataType);

		return listmain;

	}

	@RequestMapping(value = "/settlePOSMultiBill", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSMultiBillSettleInCashBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		String posCode = "";
		String userCode = "";
		String tableNo = "";
		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();
			posCode = req.getSession().getAttribute("loginPOS").toString();

			String posUrL = clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOSDate" + "?POSCode=" + posCode;
			JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(posUrL);

			JSONObject jObjCounterMaster = new JSONObject();

			String posDate1 = (String) jObj.get("POSDate");

			String[] output1 = posDate1.split(" ");
			String posDate = output1[0];

			// Menu Head Data

			List<clsPOSMultiBillSettleInCashBean> list = objBean.getListUnsettleBillDtl();
			JSONArray jArrList = new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				clsPOSMultiBillSettleInCashBean obj = new clsPOSMultiBillSettleInCashBean();
				obj = (clsPOSMultiBillSettleInCashBean) list.get(i);
				if (obj.getStrSelectedData() != null) {
					if (obj.getStrSelectedData().toString().equalsIgnoreCase("Tick")) {

						JSONObject jObjData = new JSONObject();

						jObjData.put("BillNo", obj.getStrBillNo());
						jObjData.put("dblSettleAmt", obj.getDblGrandTotal());
						// String tableName=obj.getStrTableName();
						// jObjData.put("TableName",tableName);

						String tableName = obj.getStrTableName();

						if (map.containsKey(tableName)) {
							tableNo = (String) map.get(tableName);

						}
						// jObjData.put("DPCode",obj.getStrDPCode());
						jObjData.put("TableName", tableName);
						jObjData.put("TableNo", tableNo);

						jObjData.put("GrandTotal", obj.getDblGrandTotal());

						// jObjData.put("MenuName",obj.getStrMenuHeadName());

						jArrList.add(jObjData);
					}
				}
			}

			jObjCounterMaster.put("UnsettleBillDetails", jArrList);
			jObjCounterMaster.put("ClientCode", clientCode);
			jObjCounterMaster.put("POSDate", posDate);
			jObjCounterMaster.put("User", webStockUserCode);
			jObjCounterMaster.put("POSCode", posCode);

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funSettleBills";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjCounterMaster.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSMultiBillSettle.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

}
