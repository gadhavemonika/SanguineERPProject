package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsAddKOTToBillBean;
import com.sanguine.webpos.bean.clsAuditFlashBean;
import com.sanguine.webpos.bean.clsBillItemDtlBean;
import com.sanguine.webpos.bean.clsKOTItemDtl;
import com.sanguine.webpos.bean.clsPOSTableMasterBean;
import com.sanguine.webpos.bean.clsPOSUserAccessBean;
import com.sanguine.webpos.bean.clsPOSUserRegistrationBean;
import com.sanguine.webpos.bean.clsPhysicalStockFlashBean;
import com.sanguine.webpos.bean.clsWebPOSReportBean;

@Controller
public class clsAddKOTToBillController {

	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobalFunctionsController;

	@Autowired
	private ServletContext servletContext;

	Map map = new HashMap();
	List<clsPOSTableMasterBean> listTable = new ArrayList<clsPOSTableMasterBean>();

	@RequestMapping(value = "/frmPOSAddKOTToBill", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String posCode = request.getSession().getAttribute("loginPOS").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		List tableList = new ArrayList();
		tableList.add("ALL");
		funGetTableList(posCode);
		for (int cnt = 0; cnt < listTable.size(); cnt++) {
			clsPOSTableMasterBean obj = listTable.get(cnt);
			tableList.add(obj.getStrTableName());
			map.put(obj.getStrTableName(), obj.getStrTableNo());
		}
		model.put("tableList", tableList);

		if ("2".equalsIgnoreCase(urlHits)) {

			return new ModelAndView("frmPOSAddKOTToBill_1", "command", new clsAddKOTToBillBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSAddKOTToBill", "command", new clsAddKOTToBillBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/saveAddKOTToBill", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsAddKOTToBillBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";

		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObj = new JSONObject();
			clsPOSUserAccessBean obj = null;
			List<clsKOTItemDtl> listKot = objBean.getListKOTDtl();
			List<clsBillItemDtlBean> listBill = objBean.getListBillDtl();
			String billNo = "";
			JSONArray jArrKOTList = new JSONArray();

			if (listKot.size() > 0 && listBill.size() > 0) {
				for (int i = 0; i < listKot.size(); i++) {
					clsKOTItemDtl objKOTDtl = listKot.get(i);
					String[] arrKOT = objKOTDtl.getStrKOTNo().split(",");
					for (int cnt = 0; cnt < arrKOT.length; cnt++) {
						JSONObject jObjData = new JSONObject();
						jObjData.put("KOTNo", arrKOT[cnt]);
						jArrKOTList.add(jObjData);
					}
				}

				for (int i = 0; i < listBill.size(); i++) {
					clsBillItemDtlBean objBillDtl = listBill.get(i);
					billNo = objBillDtl.getStrBillNo();
				}

			}

			jObj.put("KOTList", jArrKOTList);
			jObj.put("BillNo", billNo);

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funSaveAddKOTToBill";
			System.out.print(posURL);
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
			return new ModelAndView("redirect:/frmPOSAddKOTToBill.html?saddr=" + urlHits);

		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/LoadADDKOTTableData", method = RequestMethod.GET)
	public @ResponseBody clsAddKOTToBillBean funLoadTableData(@RequestParam("TableName") String tableName, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String posCode = req.getSession().getAttribute("loginPOS").toString();
		clsAddKOTToBillBean obj = new clsAddKOTToBillBean();
		obj = funGetKOTList(tableName, posCode);
		return obj;
	}

	@RequestMapping(value = "/LoadUnsettleBill", method = RequestMethod.GET)
	public @ResponseBody clsAddKOTToBillBean funGetBilList(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String posCode = req.getSession().getAttribute("loginPOS").toString();
		clsAddKOTToBillBean obj = new clsAddKOTToBillBean();
		obj = funGetBilList(posCode);
		return obj;
	}

	private clsAddKOTToBillBean funGetTableList(String posCode) {
		clsAddKOTToBillBean obj = new clsAddKOTToBillBean();
		JSONArray jArrTableList = null;
		List<clsPOSTableMasterBean> listTableData = new ArrayList<clsPOSTableMasterBean>();
		JSONObject jObjTableData = new JSONObject();
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funGetTableList" + "?posCode=" + posCode;

		jObjTableData = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		jArrTableList = (JSONArray) jObjTableData.get("TableList");

		if (null != jArrTableList) {
			for (int cnt = 0; cnt < jArrTableList.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArrTableList.get(cnt);
				clsPOSTableMasterBean objTableDtl = new clsPOSTableMasterBean();
				objTableDtl.setStrTableNo((String) jobj.get("strTableNo"));
				objTableDtl.setStrTableName((String) jobj.get("strTableName"));

				listTableData.add(objTableDtl);
				listTable.add(objTableDtl);

			}
			obj.setListTableDtl(listTableData);
		}
		return obj;
	}

	private clsAddKOTToBillBean funGetKOTList(String tableName, String posCode) {
		clsAddKOTToBillBean obj = new clsAddKOTToBillBean();
		JSONArray jArrKOTList = null;
		List<clsKOTItemDtl> listKOTData = new ArrayList<clsKOTItemDtl>();
		JSONObject jObjKOTData = new JSONObject();
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funGetKOTListForAddKOTToBill" + "?posCode=" + posCode + "&tableName=" + tableName;

		jObjKOTData = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		jArrKOTList = (JSONArray) jObjKOTData.get("KOTListForAddKOTToBill");

		if (null != jArrKOTList) {
			for (int cnt = 0; cnt < jArrKOTList.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArrKOTList.get(cnt);
				clsKOTItemDtl objKOTDtl = new clsKOTItemDtl();
				objKOTDtl.setStrTableNo((String) jobj.get("TableNo"));
				objKOTDtl.setStrKOTNo((String) jobj.get("KOTNo"));
				listKOTData.add(objKOTDtl);

			}
			obj.setListKOTDtl(listKOTData);
		}
		return obj;
	}

	private clsAddKOTToBillBean funGetBilList(String posCode) {
		clsAddKOTToBillBean obj = new clsAddKOTToBillBean();
		JSONArray jArrBillList = null;
		List<clsBillItemDtlBean> listBillData = new ArrayList<clsBillItemDtlBean>();
		JSONObject jObjBillList = new JSONObject();
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funGetUnsettleBillListForAddKOTToBill" + "?posCode=" + posCode;

		jObjBillList = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		jArrBillList = (JSONArray) jObjBillList.get("UnsettleBillList");

		if (null != jArrBillList) {
			for (int cnt = 0; cnt < jArrBillList.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArrBillList.get(cnt);
				clsBillItemDtlBean objBillDtl = new clsBillItemDtlBean();
				objBillDtl.setStrBillNo((String) jobj.get("BillNo"));
				listBillData.add(objBillDtl);

			}
			obj.setListBillDtl(listBillData);
		}
		return obj;
	}

}