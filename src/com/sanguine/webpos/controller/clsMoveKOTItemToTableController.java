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
import com.sanguine.webpos.bean.clsKOTItemdtlBean;
import com.sanguine.webpos.bean.clsMoveKOTBean;
import com.sanguine.webpos.bean.clsMoveKOTItemsToTableBean;
import com.sanguine.webpos.bean.clsSettlementDetailsBean;

@Controller
public class clsMoveKOTItemToTableController {

	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	Map map = new HashMap();
	List openKOTList = new ArrayList();
	List openTableList = new ArrayList();

	@RequestMapping(value = "/frmPOSMoveKOTItemToTable", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsMoveKOTItemsToTableBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {

		String loginPosCode = request.getSession().getAttribute("loginPOS").toString();

		String clientCode = request.getSession().getAttribute("clientCode").toString();

		JSONArray jArrList = new JSONArray();
		jArrList = objPOSGlobal.funGetAllPOSForMaster(clientCode);

		Map tableList = new HashMap<>();
		tableList.put("All", "Select Table");

		JSONArray jArrTableList = new JSONArray();
		jArrTableList = objPOSGlobal.funGetTableList(loginPosCode, clientCode);
		for (int i = 0; i < jArrTableList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArrTableList.get(i);

			tableList.put(josnObjRet.get("strTableNo"), josnObjRet.get("strTableName"));
		}

		model.put("tableList", tableList);

		String posURL = "http://localhost:8080/prjSanguineWebService/WebPOSTransactions/funGetBusyTableDtl" + "?loginPosCode=" + loginPosCode;
		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(posURL);

		jArrTableList = new JSONArray();
		jArrTableList = (JSONArray) jObj.get("TableDtl");
		Map busyTblList = new HashMap<>();
		busyTblList.put("All", "Select Table");
		for (int i = 0; i < jArrTableList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArrTableList.get(i);

			busyTblList.put(josnObjRet.get("TableNo"), josnObjRet.get("TableName"));
		}

		model.put("busyTblList", busyTblList);
		return new ModelAndView("frmPOSMoveKOTItemToTable");

	}

	@RequestMapping(value = "/saveMoveKOTItemsToTable", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsMoveKOTItemsToTableBean objBean, BindingResult result, HttpServletRequest req) {
		String loginPosCode = req.getSession().getAttribute("loginPOS").toString();
		String webStockUserCode = req.getSession().getAttribute("usercode").toString();
		String strClientCode = req.getSession().getAttribute("clientCode").toString();
		try {

			JSONObject jObjMoveKOT = new JSONObject();
			String arrKOTNo = objBean.getjObjKOTItemList();
			String strBusyTbl = objBean.getStrBusyTbl();
			String strTableNo = objBean.getStrTableNo();
			List<clsKOTItemdtlBean> list = objBean.getItemDtlList();
			JSONArray jArrList = new JSONArray();
			if (null != list) {
				for (int i = 0; i < list.size(); i++) {
					clsKOTItemdtlBean obj = new clsKOTItemdtlBean();
					obj = (clsKOTItemdtlBean) list.get(i);

					JSONObject jObjData = new JSONObject();
					jObjData = (JSONObject) map.get(obj.getStrItemCode());
					jObjData.remove("dblItemQuantity");
					jObjData.put("dblItemQuantity", obj.getDblItemQuantity());
					jObjData.remove("dblAmount");
					jObjData.put("dblAmount", obj.getDblAmount());
					jArrList.add(jObjData);

				}
			}

			String strPosCode = req.getSession().getAttribute("loginPOS").toString();
			String posURL = "http://localhost:8080/prjSanguineWebService/APOSIntegration/funGetPOSDate" + "?POSCode=" + strPosCode;
			JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(posURL);

			String posDate = (String) jObj.get("POSDate");
			jObjMoveKOT.put("loginPosCode", loginPosCode);
			jObjMoveKOT.put("clientCode", strClientCode);
			jObjMoveKOT.put("userCode", webStockUserCode);
			jObjMoveKOT.put("posDate", posDate);
			jObjMoveKOT.put("busyTblNo", strBusyTbl);
			jObjMoveKOT.put("tableNo", strTableNo);
			jObjMoveKOT.put("arrKOTNo", arrKOTNo);
			jObjMoveKOT.put("listItemDtl", jArrList);

			posURL = "";
			posURL = "http://localhost:8080/prjSanguineWebService/WebPOSTransactions/funSaveMoveKOTItemToTable";
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

			return new ModelAndView("redirect:/frmPOSMoveKOTItemToTable.html");
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/loadOpenKOTsForMoveKOTItem", method = RequestMethod.GET)
	public @ResponseBody JSONObject loadKOTData(HttpServletRequest req) {

		String loginPosCode = req.getSession().getAttribute("loginPOS").toString();

		String tableNo = req.getParameter("tableNo");

		JSONArray jArrSettlementList = null;

		JSONObject jObjSettlementData = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/WebPOSTransactions/funGetOpenKOTsForMoveKOTItem" + "?tableNo=" + tableNo + "&loginPosCode=" + loginPosCode;

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

	@RequestMapping(value = "/loadKOTItemsDtl", method = RequestMethod.GET)
	public @ResponseBody JSONArray funGetKOTItemsDtl(HttpServletRequest req) {
		String loginPosCode = req.getSession().getAttribute("loginPOS").toString();
		String KOTNo = req.getParameter("KOTNo");

		JSONArray jArrSettlementList = null;
		String tableNo = (String) map.get(KOTNo);
		JSONObject jObjSettlementData = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/WebPOSTransactions/funGetKOTItemsDtl" + "?KOTNo=" + KOTNo + "&tableNo=" + tableNo + "&loginPosCode=" + loginPosCode;

		jObjSettlementData = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		jArrSettlementList = (JSONArray) jObjSettlementData.get("KOTItemsDtl");

		if (null != jArrSettlementList) {

			for (int i = 0; i < jArrSettlementList.size(); i++) {
				JSONObject jobj = (JSONObject) jArrSettlementList.get(i);

				map.put((String) jobj.get("strItemCode"), jobj);

			}
		}

		return jArrSettlementList;
	}

	@RequestMapping(value = "/getKOTItemMap", method = RequestMethod.POST)
	public @ResponseBody String funGetKOTItemMap(HttpServletRequest req) {
		String loginPosCode = req.getSession().getAttribute("loginPOS").toString();
		Map arrKOTNo = req.getParameterMap();

		JSONArray jArrSettlementList = null;
		/*
		 * String tableNo=(String)map.get(KOTNo); JSONObject
		 * jObjSettlementData=new JSONObject(); String posUrl =
		 * "http://localhost:8080/prjSanguineWebService/WebPOSTransactions/funGetKOTItemsDtl"
		 * + "?KOTNo="+KOTNo+"&tableNo="+tableNo+"&loginPosCode="+loginPosCode;
		 * 
		 * jObjSettlementData =objGlobal.funGETMethodUrlJosnObjectData(posUrl);
		 * 
		 * jArrSettlementList=(JSONArray) jObjSettlementData.get("KOTItemsDtl");
		 * 
		 * if(null!=jArrSettlementList) {
		 * 
		 * for(int i=0; i<jArrSettlementList.size();i++) { JSONObject
		 * jobj=(JSONObject) jArrSettlementList.get(i);
		 * 
		 * 
		 * 
		 * map.put((String)jobj.get("strItemCode"),jobj);
		 * 
		 * } }
		 */
		return loginPosCode;
	}

}
