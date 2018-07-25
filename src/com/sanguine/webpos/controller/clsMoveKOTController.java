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
import java.util.Vector;

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
import com.sanguine.webpos.bean.clsDeliveryBoyChargesBean;
import com.sanguine.webpos.bean.clsDeliveryBoyMasterBean;
import com.sanguine.webpos.bean.clsMoveKOTBean;
import com.sanguine.webpos.bean.clsPOSAreaMasterBean;

@Controller
public class clsMoveKOTController {
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	Vector vTableNo = new Vector();
	Map map = new HashMap();
	List openKOTList = new ArrayList();
	List openTableList = new ArrayList();

	@RequestMapping(value = "/frmPOSMoveKOT", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsMoveKOTBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {

		String strPosCode = request.getSession().getAttribute("loginPOS").toString();

		String clientCode = request.getSession().getAttribute("clientCode").toString();

		JSONArray jArrList = new JSONArray();
		jArrList = objPOSGlobal.funGetAllPOSForMaster(clientCode);

		Map posList = new HashMap<>();
		posList.put("All", "All");
		for (int cnt = 0; cnt < jArrList.size(); cnt++) {
			JSONObject jObj = (JSONObject) jArrList.get(cnt);
			posList.put(jObj.get("strPosCode").toString(), jObj.get("strPosName").toString());
		}
		model.put("posList", posList);

		Map tableList = new HashMap<>();
		tableList.put("All", "All");

		JSONArray jArrTableList = new JSONArray();
		jArrTableList = objPOSGlobal.funGetTableList(strPosCode, clientCode);
		for (int i = 0; i < jArrTableList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArrTableList.get(i);

			tableList.put(josnObjRet.get("strTableNo"), josnObjRet.get("strTableName"));
		}

		model.put("tableList", tableList);

		return new ModelAndView("frmPOSMoveKOT");

	}

	@RequestMapping(value = "/saveMoveKOT", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsMoveKOTBean objBean, BindingResult result, HttpServletRequest req, @RequestParam("KOTNo") String KOTNo, @RequestParam("tableName") String tableName, @RequestParam("selectedIndx") int selectedIndx) {

		try {

			JSONObject jObjMoveKOT = new JSONObject();

			String strTableNo = objBean.getStrTableNo();

			jObjMoveKOT.put("openTableNo", map.get(KOTNo));
			jObjMoveKOT.put("KOTNo", KOTNo);
			jObjMoveKOT.put("TableNo", vTableNo.get(selectedIndx));
			String posURL = "http://localhost:8080/prjSanguineWebService/WebPOSTransactions/funSaveMoveKOT";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjMoveKOT.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSMoveKOT.html");
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/loadKOTData", method = RequestMethod.GET)
	public @ResponseBody JSONObject loadKOTData(HttpServletRequest req) {

		String loginPosCode = req.getSession().getAttribute("loginPOS").toString();

		String tableNo = req.getParameter("tableNo");

		String posCode = req.getParameter("posCode");
		JSONArray jArrSettlementList = null;

		JSONObject jObjSettlementData = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/WebPOSTransactions/funGetOpenKOTDtl" + "?tableNo=" + tableNo + "&posCode=" + posCode + "&loginPosCode=" + loginPosCode;

		jObjSettlementData = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		jArrSettlementList = (JSONArray) jObjSettlementData.get("KOTDtl");

		if (null != jArrSettlementList) {

			for (int i = 0; i < jArrSettlementList.size(); i++) {
				JSONObject jobj = (JSONObject) jArrSettlementList.get(i);

				map.put((String) jobj.get("KOTNo"), (String) jobj.get("TableNo"));
			}
		}

		JSONArray jArrKOTList = (JSONArray) jObjSettlementData.get("KOTList");

		JSONObject jObjKOTData = new JSONObject();

		jObjKOTData.put("KOTList", jArrKOTList);

		return jObjKOTData;

	}

	@RequestMapping(value = "/loadTableData", method = RequestMethod.GET)
	public @ResponseBody JSONArray funGetTableDtl(HttpServletRequest req) {
		String posCode = req.getParameter("posCode");

		vTableNo.removeAllElements();
		JSONArray jArrSettlementList = null;

		JSONObject jObjSettlementData = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/WebPOSTransactions/funGetTableDtl" + "?posCode=" + posCode;

		jObjSettlementData = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		jArrSettlementList = (JSONArray) jObjSettlementData.get("TableDtl");

		if (null != jArrSettlementList) {

			for (int i = 0; i < jArrSettlementList.size(); i++) {
				JSONObject jobj = (JSONObject) jArrSettlementList.get(i);

				vTableNo.add((String) jobj.get("TableNo"));

			}
		}

		return jArrSettlementList;
	}

}
