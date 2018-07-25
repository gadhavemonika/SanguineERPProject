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

import javax.servlet.ServletContext;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsAddKOTToBillBean;
import com.sanguine.webpos.bean.clsBillItemDtlBean;
import com.sanguine.webpos.bean.clsKOTItemDtl;
import com.sanguine.webpos.bean.clsPOSMoveTableBean;
import com.sanguine.webpos.bean.clsPOSTableMasterBean;
import com.sanguine.webpos.bean.clsPOSUserAccessBean;

@Controller
public class clsPOSMoveTableController {

	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobalFunctionsController;

	@Autowired
	private ServletContext servletContext;

	Map<String, String> map = new HashMap<String, String>();
	List<clsPOSTableMasterBean> listTable = new ArrayList<clsPOSTableMasterBean>();

	@RequestMapping(value = "/frmPOSMoveTable", method = RequestMethod.GET)
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

		if ("2".equalsIgnoreCase(urlHits)) {

			return new ModelAndView("frmPOSMoveTable_1", "command", new clsPOSMoveTableBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSMoveTable", "command", new clsPOSMoveTableBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/saveMoveTable", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSMoveTableBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";

		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObj = new JSONObject();
			clsPOSUserAccessBean obj = null;
			List<clsPOSTableMasterBean> listAllTable = objBean.getListOfAllTable();
			List<clsPOSTableMasterBean> listOccupiedTable = objBean.getListOfOccupiedTable();
			String movedFromTableName = "", movedFromTableNo = "", movedToTableName = "", movedToTableNo = "";
			JSONArray jArrKOTList = new JSONArray();

			if (listAllTable.size() > 0 && listOccupiedTable.size() > 0) {
				for (int i = 0; i < listOccupiedTable.size(); i++) {
					clsPOSTableMasterBean objTableDtl = listOccupiedTable.get(i);
					movedFromTableName = objTableDtl.getStrTableName();
					if (map.size() > 0) {
						for (String key : map.keySet()) {
							if (map.get(key).equals(movedFromTableName)) {
								movedFromTableNo = key;
							}
						}
					}
				}

				for (int i = 0; i < listAllTable.size(); i++) {
					clsPOSTableMasterBean objTableDtl = listAllTable.get(i);
					movedToTableName = objTableDtl.getStrTableName();
					if (map.size() > 0) {
						for (String key : map.keySet()) {
							if (map.get(key).equals(movedToTableName)) {
								movedToTableNo = key;
							}
						}
					}
				}

			}

			jObj.put("MovedFromTable", movedFromTableNo);
			jObj.put("MovedToTable", movedToTableNo);

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funSaveMoveTable";
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

			return new ModelAndView("redirect:/frmPOSMoveTable.html?saddr=" + urlHits);

		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/LoadMoveTableData", method = RequestMethod.GET)
	public @ResponseBody clsPOSMoveTableBean funLoadTableData(@RequestParam("TableStatus") String tableStatus, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String posCode = req.getSession().getAttribute("loginPOS").toString();
		clsPOSMoveTableBean obj = new clsPOSMoveTableBean();
		obj = funGetTableList(posCode, tableStatus);
		return obj;
	}

	private clsPOSMoveTableBean funGetTableList(String posCode, String tableStatus) {
		clsPOSMoveTableBean obj = new clsPOSMoveTableBean();
		JSONArray jArrTableList = null;
		List<clsPOSTableMasterBean> listTableData = new ArrayList<clsPOSTableMasterBean>();
		JSONObject jObjTableData = new JSONObject();
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funGetTableListForMoveTable" + "?posCode=" + posCode + "&tableStatus=" + tableStatus;
		// posCode+"&tableStatus="+tableStatus
		jObjTableData = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		jArrTableList = (JSONArray) jObjTableData.get("TableListForMoveTable");

		if (null != jArrTableList) {
			for (int cnt = 0; cnt < jArrTableList.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArrTableList.get(cnt);
				clsPOSTableMasterBean objTableDtl = new clsPOSTableMasterBean();
				objTableDtl.setStrTableNo((String) jobj.get("TableNo"));
				objTableDtl.setStrTableName((String) jobj.get("TableName"));
				objTableDtl.setStrStatus((String) jobj.get("TableStatus"));
				listTableData.add(objTableDtl);
				listTable.add(objTableDtl);
				if (tableStatus.equals("All")) {
					map.put((String) jobj.get("TableNo"), (String) jobj.get("TableName"));
				}
			}
			if (!tableStatus.equals("All")) {
				obj.setListOfOccupiedTable(listTableData);
			} else {
				obj.setListOfAllTable(listTableData);
			}

		}
		return obj;
	}

}
