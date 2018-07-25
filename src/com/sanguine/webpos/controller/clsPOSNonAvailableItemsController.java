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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
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

import com.mysql.jdbc.Connection;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webpos.bean.clsChangePasswordBean;
import com.sanguine.webpos.bean.clsPOSAssignHomeDeliveryBean;
import com.sanguine.webpos.bean.clsPOSBillHdDtl;
import com.sanguine.webpos.bean.clsPOSDeliveryPersonMaster;

@Controller
public class clsPOSNonAvailableItemsController {
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	Map map = new HashMap();

	@RequestMapping(value = "/frmPOSNonAvailableItems", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsChangePasswordBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		// List itemList = new ArrayList();
		//
		// JSONArray jArryList=new JSONArray();
		//
		// jArryList =objPOSGlobal.funFillItemTable(clientCode);
		//
		//
		// for(int i =0 ;i<jArryList.size();i++)
		// {
		// JSONObject josnObjRet = (JSONObject) jArryList.get(i);
		// itemList.add(josnObjRet.get("strItemCode"));
		// map.put(josnObjRet.get("strItemCode"),
		// josnObjRet.get("strItemName"));
		// }
		// model.put("itemList",itemList);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSNonAvailableItems_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSNonAvailableItems");
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/funFillItemTable", method = RequestMethod.GET)
	public @ResponseBody JSONObject funloadBillAndDelBoyData(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		JSONArray jArrItemList = null;

		JSONObject jObj = new JSONObject();

		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funFillItemTable" + "?clientCode=" + clientCode;

		jObj = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		jArrItemList = (JSONArray) jObj.get("itemList");

		for (int i = 0; i < jArrItemList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArrItemList.get(i);

			map.put(josnObjRet.get("strItemName"), josnObjRet.get("strItemCode"));
		}

		JSONObject jObjRet = new JSONObject();

		jObjRet.put("itemList", jArrItemList);

		return jObjRet;

	}

	@RequestMapping(value = "/funGetNonAvailableItems", method = RequestMethod.GET)
	public @ResponseBody JSONObject funGetNonAvailableItems(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		JSONObject jObjBillAndDeliveryBoyData = new JSONObject();
		JSONArray jArrItemList = null;

		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetNonAvailableItems" + "?strClientCode=" + clientCode;

		JSONObject jObj = new JSONObject();
		jObj = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		jArrItemList = (JSONArray) jObj.get("NonAvailableItemList");

		JSONObject jObjRet = new JSONObject();
		jObjRet.put("NonAvailableItemList", jArrItemList);

		return jObjRet;

	}

	@RequestMapping(value = "/funItemsMouseClicked", method = RequestMethod.POST)
	public ModelAndView funItemsMouseClicked(HttpServletRequest req) {

		String urlHits = "1";
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String itemName = req.getParameter("code");
			String itemCode = "";
			if (map.containsKey(itemName)) {
				itemCode = (String) map.get(itemName);
			}

			String posCode = req.getSession().getAttribute("loginPOS").toString();
			String posUrL = clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOSDate" + "?POSCode=" + posCode;
			JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(posUrL);

			String posDate = (String) jObj.get("POSDate");

			JSONObject jObjItemsDtl = new JSONObject();
			jObj.put("strPOSCode", posCode);
			jObj.put("strItemCode", itemCode);
			jObj.put("strClientCode", clientCode);
			jObj.put("strItemName", itemName);
			jObj.put("dteDate", posDate);

			JSONArray jArr = new JSONArray();
			jArr.add(jObj);
			JSONObject jObjData = new JSONObject();
			jObjData.put("NonAvailableItemDtl", jArr);
			String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funSaveNonAvailableItem";

			URL url = new URL(posUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjData.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSNonAvailableItems.html?saddr=" + urlHits);
		} catch (Exception e) {
			urlHits = "1";
			e.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/funRemoveNonAvailableItem", method = RequestMethod.POST)
	public ModelAndView funRemoveNonAvailableItem(HttpServletRequest req) {

		String urlHits = "1";

		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String itemName = req.getParameter("code");
			String posCode = req.getSession().getAttribute("loginPOS").toString();
			String itemCode = "";

			if (map.containsKey(itemName)) {
				itemCode = (String) map.get(itemName);
			}

			JSONObject jObj = new JSONObject();
			jObj.put("posCode", posCode);
			jObj.put("itemCode", itemCode);
			jObj.put("clientCode", clientCode);

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funRemoveNonAvailableItem";
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

			return new ModelAndView("redirect:/frmPOSNonAvailableItems.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

}
