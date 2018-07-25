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
import com.sanguine.webpos.bean.clsPOSAreaMasterBean;
import com.sanguine.webpos.bean.clsPOSGroupMasterBean;
import com.sanguine.webpos.bean.clsPOSMasterBean;
import com.sanguine.webpos.bean.clsPOSTableMasterBean;
import com.sanguine.webpos.bean.clsSettlementDetailsBean;

@Controller
public class clsPOSTableMasterController {

	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	// Open POSTableMaster
	@RequestMapping(value = "/frmPOSTableMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsPOSTableMasterBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {

		String clientCode = request.getSession().getAttribute("clientCode").toString();

		JSONArray jArrList = null;

		jArrList = objPOSGlobal.funGetAllAreaForMaster(clientCode);

		// Area List

		Map areaList = new HashMap<>();
		areaList.put("All", "All");
		if (null != jArrList) {
			for (int cnt = 0; cnt < jArrList.size(); cnt++) {
				JSONObject jObj = (JSONObject) jArrList.get(cnt);
				areaList.put(jObj.get("strAreaCode").toString(), jObj.get("strAreaName").toString());
			}
		}
		// POS LIST
		jArrList = new JSONArray();
		jArrList = objPOSGlobal.funGetAllPOSForMaster(clientCode);
		Map posList = new HashMap<>();

		if (null != jArrList) {
			for (int cnt = 0; cnt < jArrList.size(); cnt++) {
				JSONObject jObj = (JSONObject) jArrList.get(cnt);
				posList.put(jObj.get("strPosCode").toString(), jObj.get("strPosName").toString());
			}
		}
		// Waiter List

		jArrList = new JSONArray();
		jArrList = objPOSGlobal.funGetAllWaiterForMaster(clientCode);

		Map waiterList = new HashMap<>();
		waiterList.put("All", "All");
		if (null != jArrList) {
			for (int cnt = 0; cnt < jArrList.size(); cnt++) {
				JSONObject jObj = (JSONObject) jArrList.get(cnt);
				waiterList.put(jObj.get("strWaiterNo").toString(), jObj.get("strWShortName").toString());
			}
		}
		model.put("areaList", areaList);
		model.put("posList", posList);
		model.put("waiterList", waiterList);

		return new ModelAndView("frmPOSTableMaster");
	}

	@RequestMapping(value = "/savePOSTableMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSTableMasterBean objBean, BindingResult result, HttpServletRequest req) {

		try {

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjTableMaster = new JSONObject();
			jObjTableMaster.put("TableCode", objBean.getStrTableNo());
			jObjTableMaster.put("TableName", objBean.getStrTableName());

			jObjTableMaster.put("AreaName", objBean.getStrAreaName());

			jObjTableMaster.put("WaiterName", objBean.getStrWaiterName());
			jObjTableMaster.put("PaxCapacity", objBean.getIntPaxCapacity());
			jObjTableMaster.put("Operational", objGlobal.funIfNull(objBean.getStrOperational(), "N", "Y"));

			jObjTableMaster.put("POSName", objBean.getStrPOSCode());
			jObjTableMaster.put("TableDtl", objBean.getListTableDtl());
			jObjTableMaster.put("User", webStockUserCode);
			jObjTableMaster.put("ClientCode", clientCode);
			jObjTableMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjTableMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funSaveTableMaster";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjTableMaster.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSTableMaster.html");
		} catch (Exception ex) {

			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/savePOSTableSequence", method = RequestMethod.POST)
	public ModelAndView funSaveTableSequnces(@ModelAttribute("command") @Valid clsPOSTableMasterBean objBean, BindingResult result, HttpServletRequest req) {
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();

			JSONObject jObjTableMaster = new JSONObject();

			// Table Sequence Data

			List<clsPOSTableMasterBean> list = objBean.getListTableDtl();
			JSONArray jArrList = new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				clsPOSTableMasterBean obj = new clsPOSTableMasterBean();
				obj = (clsPOSTableMasterBean) list.get(i);

				JSONObject jObjData = new JSONObject();
				if (null != obj.getStrTableNo()) {
					if (obj.getStrTableNo().contains(",")) {
						String[] tblNo = obj.getStrTableNo().split(",");
						jObjData.put("Sequence", obj.getIntSequence());
						jObjData.put("TableNo", tblNo[0]);
						jArrList.add(jObjData);
						jObjData = new JSONObject();
						jObjData.put("Sequence", (obj.getIntSequence()) + 1);
						jObjData.put("TableNo", tblNo[1]);
						jArrList.add(jObjData);
					} else {
						jObjData.put("Sequence", obj.getIntSequence());
						jObjData.put("TableNo", obj.getStrTableNo());

						jArrList.add(jObjData);
					}
				}
			}

			jObjTableMaster.put("TableDetails", jArrList);
			jObjTableMaster.put("clientCode", clientCode);

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funSaveTableSequence";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjTableMaster.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSTableMaster.html");
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/loadPOSTableMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPOSTableMasterBean funSetSearchFields(@RequestParam("tableCode") String tableCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSTableMasterBean objPOSTableMaster = null;

		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funGetTableMasterData" + "?tableNo=" + tableCode + "&clientCode=" + clientCode;
		System.out.println(posUrl);

		try {
			URL url = new URL(posUrl);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = "", op = "";
			while ((output = br.readLine()) != null) {
				op += output;
			}
			System.out.println("Obj=" + op);
			conn.disconnect();

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(op);
			jObjSearchDetails = (JSONObject) obj;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (null != jObjSearchDetails) {

			objPOSTableMaster = new clsPOSTableMasterBean();
			objPOSTableMaster.setStrTableNo((String) jObjSearchDetails.get("strTableNo"));
			objPOSTableMaster.setStrTableName((String) jObjSearchDetails.get("strTableName"));

			objPOSTableMaster.setStrAreaName((String) jObjSearchDetails.get("strAreaCode"));

			objPOSTableMaster.setStrWaiterName((String) jObjSearchDetails.get("strWaiterNo"));
			objPOSTableMaster.setIntPaxCapacity((long) jObjSearchDetails.get("intPaxNo"));
			objPOSTableMaster.setStrOperational((String) jObjSearchDetails.get("strOperational"));

			objPOSTableMaster.setStrPOSCode((String) jObjSearchDetails.get("strPOSCode"));
		}

		if (null == objPOSTableMaster) {
			objPOSTableMaster = new clsPOSTableMasterBean();
			objPOSTableMaster.setStrTableNo("Invalid Code");
		}

		return objPOSTableMaster;
	}

	@RequestMapping(value = "/LoadTableData", method = RequestMethod.GET)
	public @ResponseBody List<clsPOSTableMasterBean> funLoadTableData(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		JSONArray jArrSettlementList = null;
		List<clsPOSTableMasterBean> listTableData = new ArrayList<clsPOSTableMasterBean>();
		JSONObject jObjSettlementData = new JSONObject();
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funGetTableDtl" + "?clientCode=" + clientCode;

		jObjSettlementData = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		jArrSettlementList = (JSONArray) jObjSettlementData.get("TableDtl");

		if (null != jArrSettlementList) {
			for (int cnt = 0; cnt < jArrSettlementList.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArrSettlementList.get(cnt);
				clsPOSTableMasterBean objSettlementDtl = new clsPOSTableMasterBean();
				objSettlementDtl.setStrTableNo((String) jobj.get("TableNo"));
				objSettlementDtl.setStrTableName((String) jobj.get("TableName"));
				objSettlementDtl.setStrAreaName((String) jobj.get("AreaName"));
				objSettlementDtl.setStrPOSCode((String) jobj.get("PosName"));

				listTableData.add(objSettlementDtl);
			}
		}
		return listTableData;
	}

	@RequestMapping(value = "/checkTableName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckPOSName(@RequestParam("name") String name, @RequestParam("code") String code, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		int count = objPOSGlobal.funCheckName(name, code, clientCode, "POSTableMaster");
		if (count > 0)
			return false;
		else
			return true;

	}
}
