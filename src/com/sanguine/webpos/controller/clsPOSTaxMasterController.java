package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
import com.sanguine.webpos.bean.clsPOSTaxMasterBean;
import com.sanguine.webpos.bean.clsReorderTimeBean;
import com.sanguine.webpos.bean.clsSettlementDetailsBean;

@Controller
public class clsPOSTaxMasterController {

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	// Open POSTaxMaster
	@RequestMapping(value = "/frmPOSTaxMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		// return new ModelAndView("frmPOSGroupMaster");

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSTaxMaster_1", "command", new clsPOSTaxMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSTaxMaster", "command", new clsPOSTaxMasterBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/savePOSTaxMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSTaxMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";

		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjTaxMaster = new JSONObject();
			jObjTaxMaster.put("TaxCode", objBean.getStrTaxCode());
			jObjTaxMaster.put("TaxDesc", objBean.getStrTaxDesc());
			jObjTaxMaster.put("TaxType", objBean.getStrTaxType());

			jObjTaxMaster.put("TaxShortName", objBean.getStrTaxShortName());
			jObjTaxMaster.put("Amount", objBean.getDblAmount());

			jObjTaxMaster.put("TaxOnSP", objBean.getStrTaxOnSP());
			jObjTaxMaster.put("Percent", objBean.getDblPercent());

			jObjTaxMaster.put("TaxOnGD", objBean.getStrTaxOnGD());
			jObjTaxMaster.put("TaxRounded", objGlobal.funIfNull(objBean.getStrTaxRounded(), "N", "Y"));
			jObjTaxMaster.put("TaxCalculation", objBean.getStrTaxCalculation());
			jObjTaxMaster.put("TaxOnTax", objGlobal.funIfNull(objBean.getStrTaxOnTax(), "N", "Y"));

			jObjTaxMaster.put("TaxIndicator", objBean.getStrTaxIndicator());

			jObjTaxMaster.put("ValidFrom", objBean.getDteValidFrom());

			jObjTaxMaster.put("ValidTo", objBean.getDteValidTo());
			jObjTaxMaster.put("ItemType", objBean.getStrItemType());

			String operationType = "";
			operationType = objGlobal.funIfNull(objBean.getStrHomeDelivery(), "", ",HomeDelivery") + objGlobal.funIfNull(objBean.getStrDinningInn(), "", ",DineIn") + objGlobal.funIfNull(objBean.getStrTakeAway(), "", ",TakeAway");
			StringBuilder sb = new StringBuilder(operationType);
			operationType = sb.delete(0, 1).toString();
			jObjTaxMaster.put("OperationType", operationType);

			jObjTaxMaster.put("AccountCode", objBean.getStrAccountCode());

			jObjTaxMaster.put("User", webStockUserCode);
			jObjTaxMaster.put("ClientCode", clientCode);
			jObjTaxMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjTaxMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

			// Settlement Data

			List<clsSettlementDetailsBean> list = objBean.getListSettlementCode();
			JSONArray jArrList = new JSONArray();
			if (null != list) {
				for (int i = 0; i < list.size(); i++) {
					clsSettlementDetailsBean obj = new clsSettlementDetailsBean();
					obj = (clsSettlementDetailsBean) list.get(i);
					if (obj.getStrApplicableYN() != null) {
						JSONObject jObjData = new JSONObject();

						jObjData.put("SettlementCode", obj.getStrSettlementCode());
						jObjData.put("SettlementDesc", obj.getStrSettlementDesc());

						jArrList.add(jObjData);
					}

				}
			}
			jObjTaxMaster.put("SettlementDetails", jArrList);

			// Group Data

			List<clsPOSGroupMasterBean> gList = objBean.getListGroupCode();
			JSONArray jArrGroupList = new JSONArray();
			if (null != gList) {
				for (int i = 0; i < gList.size(); i++) {
					clsPOSGroupMasterBean obj = new clsPOSGroupMasterBean();
					obj = (clsPOSGroupMasterBean) gList.get(i);
					if (obj.getStrApplicableYN() != null) {
						JSONObject jObjData = new JSONObject();

						jObjData.put("GroupCode", obj.getStrGroupCode());
						jObjData.put("GroupName", obj.getStrGroupName());

						jArrGroupList.add(jObjData);
					}

				}
			}
			jObjTaxMaster.put("GroupDetails", jArrGroupList);

			// Pos Data
			List<clsPOSMasterBean> poslist = objBean.getListPOSCode();
			JSONArray jArrPosList = new JSONArray();
			if (null != poslist) {
				for (int i = 0; i < poslist.size(); i++) {
					clsPOSMasterBean obj = new clsPOSMasterBean();
					obj = (clsPOSMasterBean) poslist.get(i);
					if (obj.getStrApplicableYN() != null) {
						JSONObject jObjData = new JSONObject();

						jObjData.put("PosCode", obj.getStrPosCode());

						jArrPosList.add(jObjData);
					}

				}
			}
			jObjTaxMaster.put("PosList", jArrPosList);

			// Tax Data
			String taxOnTaxCode = "";
			List<clsPOSTaxMasterBean> taxlist = objBean.getListTaxOnTaxCode();
			JSONArray jArrTaxList = new JSONArray();
			if (null != taxlist) {
				for (int i = 0; i < taxlist.size(); i++) {
					clsPOSTaxMasterBean obj = new clsPOSTaxMasterBean();
					obj = (clsPOSTaxMasterBean) taxlist.get(i);
					if (obj.getStrApplicableYN() != null) {
						taxOnTaxCode += "," + obj.getStrTaxCode();

					}

				}
			}
			sb = new StringBuilder(taxOnTaxCode);
			taxOnTaxCode = sb.delete(0, 1).toString();
			jObjTaxMaster.put("TaxList", taxOnTaxCode);

			// Area Data
			List<clsPOSAreaMasterBean> arealist = objBean.getListAreaCode();
			String areaCode = "";
			if (null != arealist) {
				for (int i = 0; i < arealist.size(); i++) {
					clsPOSAreaMasterBean obj = new clsPOSAreaMasterBean();
					obj = (clsPOSAreaMasterBean) arealist.get(i);
					if (obj.getStrApplicableYN() != null) {
						areaCode += "," + obj.getStrAreaCode();

					}

				}
			}
			sb = new StringBuilder(areaCode);
			areaCode = sb.delete(0, 1).toString();
			jObjTaxMaster.put("AreaList", areaCode);

			String posURL = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funSaveTaxMaster";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjTaxMaster.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSTaxMaster.html?saddr=" + urlHits);

		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/loadPOSTaxMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPOSTaxMasterBean funSetSearchFields(@RequestParam("taxCode") String taxCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSTaxMasterBean objPOSMaster = null;
		List<clsSettlementDetailsBean> listSettleData = new ArrayList<clsSettlementDetailsBean>();
		List<clsPOSMasterBean> listPOS = new ArrayList<clsPOSMasterBean>();
		List<clsPOSGroupMasterBean> listGroup = new ArrayList<clsPOSGroupMasterBean>();
		List<clsPOSAreaMasterBean> listArea = new ArrayList<clsPOSAreaMasterBean>();
		List<clsPOSTaxMasterBean> listTax = new ArrayList<clsPOSTaxMasterBean>();
		JSONObject jObjSearchDetails = new JSONObject();
		/*
		 * String posUrl =
		 * "http://localhost:8080/prjSanguineWebService/APOSSearchIntegration/funGetPOSSearchData"
		 * +
		 * "?masterName=POSTaxMaster&searchCode="+taxCode+"&clientCode="+clientCode
		 * ;
		 */
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetTaxMasterData" + "?taxCode=" + taxCode + "&clientCode=" + clientCode;
		System.out.println(posUrl);
		System.out.println(posUrl);

		jObjSearchDetails = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		// JSONArray jObjSearchDetails=(JSONArray)
		// jObjSearchDetails.get("POSTaxMaster");
		if (null != jObjSearchDetails) {
			objPOSMaster = new clsPOSTaxMasterBean();
			objPOSMaster.setStrAccountCode((String) jObjSearchDetails.get("strAccountCode"));

			objPOSMaster.setStrItemType((String) jObjSearchDetails.get("strItemType"));

			String[] spOperation = ((String) jObjSearchDetails.get("strOperationType")).split(",");
			for (int i = 0; i < spOperation.length; i++)

			{
				if (spOperation[i].equals("HomeDelivery"))
					objPOSMaster.setStrHomeDelivery("Y");
				if (spOperation[i].equals("DineIn"))
					objPOSMaster.setStrDinningInn("Y");
				if (spOperation[i].equals("TakeAway"))
					objPOSMaster.setStrTakeAway("Y");

			}

			objPOSMaster.setStrTaxCalculation((String) jObjSearchDetails.get("strTaxCalculation"));
			objPOSMaster.setStrTaxCode((String) jObjSearchDetails.get("strTaxCode"));
			objPOSMaster.setStrTaxDesc((String) jObjSearchDetails.get("strTaxDesc"));
			objPOSMaster.setStrTaxIndicator((String) jObjSearchDetails.get("strTaxIndicator"));
			objPOSMaster.setStrTaxOnGD((String) jObjSearchDetails.get("strTaxOnGD"));
			objPOSMaster.setStrTaxOnSP((String) jObjSearchDetails.get("strTaxOnSP"));
			objPOSMaster.setStrTaxOnTax((String) jObjSearchDetails.get("strTaxOnTax"));
			objPOSMaster.setStrTaxRounded((String) jObjSearchDetails.get("strTaxRounded"));
			objPOSMaster.setStrTaxShortName((String) jObjSearchDetails.get("strTaxShortName"));

			objPOSMaster.setDblAmount((double) jObjSearchDetails.get("dblAmount"));
			objPOSMaster.setDblPercent((double) jObjSearchDetails.get("dblPercent"));
			objPOSMaster.setStrTaxType((String) jObjSearchDetails.get("strTaxType"));
			objPOSMaster.setDteValidFrom((String) jObjSearchDetails.get("dteValidFrom"));
			objPOSMaster.setDteValidTo((String) jObjSearchDetails.get("dteValidTo"));

			// POS Settlement Details
			JSONArray jArrSettlementList = (JSONArray) jObjSearchDetails.get("SettlementDtl");
			if (null != jArrSettlementList) {
				for (int cnt = 0; cnt < jArrSettlementList.size(); cnt++) {
					JSONObject jobj = (JSONObject) jArrSettlementList.get(cnt);
					clsSettlementDetailsBean objSettlementDtl = new clsSettlementDetailsBean();
					objSettlementDtl.setStrSettlementCode((String) jobj.get("SettlementCode"));
					objSettlementDtl.setStrSettlementDesc((String) jobj.get("SettlementDesc"));
					objSettlementDtl.setStrApplicableYN((Boolean) jobj.get("ApplicableYN"));

					listSettleData.add(objSettlementDtl);
				}
			}
			objPOSMaster.setListSettlementCode(listSettleData);

			// Group Data
			JSONArray jArrGrpList = (JSONArray) jObjSearchDetails.get("GroupDtl");
			if (null != jArrGrpList) {
				for (int cnt = 0; cnt < jArrGrpList.size(); cnt++) {
					JSONObject jobj = (JSONObject) jArrGrpList.get(cnt);
					clsPOSGroupMasterBean objPOSDtl = new clsPOSGroupMasterBean();
					objPOSDtl.setStrGroupCode((String) jobj.get("GroupCode"));
					objPOSDtl.setStrGroupName((String) jobj.get("GroupName"));
					objPOSDtl.setStrApplicableYN(true);

					listGroup.add(objPOSDtl);
				}
			}
			objPOSMaster.setListGroupCode(listGroup);

			// POS Data
			JSONArray jArrPosList = (JSONArray) jObjSearchDetails.get("PosDtl");
			if (null != jArrPosList) {
				for (int cnt = 0; cnt < jArrPosList.size(); cnt++) {
					JSONObject jobj = (JSONObject) jArrPosList.get(cnt);
					clsPOSMasterBean objPOSDtl = new clsPOSMasterBean();
					objPOSDtl.setStrPosCode((String) jobj.get("PosCode"));
					objPOSDtl.setStrPosName((String) jobj.get("PosName"));
					objPOSDtl.setStrApplicableYN(true);

					listPOS.add(objPOSDtl);
				}
			}
			objPOSMaster.setListPOSCode(listPOS);

			// Area Data
			JSONArray jArrAreaList = (JSONArray) jObjSearchDetails.get("AreaDtl");
			if (null != jArrAreaList) {
				for (int cnt = 0; cnt < jArrAreaList.size(); cnt++) {
					JSONObject jobj = (JSONObject) jArrAreaList.get(cnt);
					clsPOSAreaMasterBean objAreaDtl = new clsPOSAreaMasterBean();
					objAreaDtl.setStrAreaCode((String) jobj.get("AreaCode"));
					objAreaDtl.setStrAreaName((String) jobj.get("AreaName"));
					objAreaDtl.setStrApplicableYN(true);

					listArea.add(objAreaDtl);
				}
			}
			objPOSMaster.setListAreaCode(listArea);

			// Tax Data
			JSONArray jArrTaxList = (JSONArray) jObjSearchDetails.get("TaxData");
			if (null != jArrTaxList) {
				for (int cnt = 0; cnt < jArrTaxList.size(); cnt++) {
					JSONObject jobj = (JSONObject) jArrTaxList.get(cnt);
					clsPOSTaxMasterBean objTaxDtl = new clsPOSTaxMasterBean();
					objTaxDtl.setStrTaxCode((String) jobj.get("TaxCode"));
					objTaxDtl.setStrTaxDesc((String) jobj.get("TaxDesc"));
					objTaxDtl.setStrApplicableYN(true);

					listTax.add(objTaxDtl);
				}
			}
			objPOSMaster.setListTaxOnTaxCode(listTax);
		}

		if (null == objPOSMaster) {
			objPOSMaster = new clsPOSTaxMasterBean();
			objPOSMaster.setStrTaxCode("Invalid Code");
		}

		return objPOSMaster;
	}

	@RequestMapping(value = "/LoadPOSData", method = RequestMethod.GET)
	public @ResponseBody List<clsPOSMasterBean> funLoadPOSData(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		List<clsPOSMasterBean> listPOSData = new ArrayList<clsPOSMasterBean>();

		JSONArray jArryPosList = objPOSGlobal.funGetAllPOSForMaster(clientCode);

		if (null != jArryPosList) {
			for (int cnt = 0; cnt < jArryPosList.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArryPosList.get(cnt);
				clsPOSMasterBean objPOSDtl = new clsPOSMasterBean();
				objPOSDtl.setStrPosCode((String) jobj.get("strPosCode"));
				objPOSDtl.setStrPosName((String) jobj.get("strPosName"));

				listPOSData.add(objPOSDtl);
			}
		}
		return listPOSData;
	}

	@RequestMapping(value = "/LoadGroupData", method = RequestMethod.GET)
	public @ResponseBody List<clsPOSGroupMasterBean> funLoadGroupData(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		List<clsPOSGroupMasterBean> listPOSData = new ArrayList<clsPOSGroupMasterBean>();

		JSONArray jArryPosList = objPOSGlobal.funGetAllGroup(clientCode);

		if (null != jArryPosList) {
			for (int cnt = 0; cnt < jArryPosList.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArryPosList.get(cnt);
				clsPOSGroupMasterBean objPOSDtl = new clsPOSGroupMasterBean();
				objPOSDtl.setStrGroupCode((String) jobj.get("strGroupCode"));
				objPOSDtl.setStrGroupName((String) jobj.get("strGroupName"));

				listPOSData.add(objPOSDtl);
			}
		}
		return listPOSData;
	}

	@RequestMapping(value = "/LoadTaxData", method = RequestMethod.GET)
	public @ResponseBody List<clsPOSTaxMasterBean> funLoadTaxData(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		List<clsPOSTaxMasterBean> listTaxData = new ArrayList<clsPOSTaxMasterBean>();

		JSONArray jArryPosList = objPOSGlobal.funGetAllTaxForMaster(clientCode);

		if (null != jArryPosList) {
			for (int cnt = 0; cnt < jArryPosList.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArryPosList.get(cnt);
				clsPOSTaxMasterBean objPOSDtl = new clsPOSTaxMasterBean();
				objPOSDtl.setStrTaxCode((String) jobj.get("strTaxCode"));
				objPOSDtl.setStrTaxDesc((String) jobj.get("strTaxDesc"));

				listTaxData.add(objPOSDtl);
			}
		}
		return listTaxData;
	}

	@RequestMapping(value = "/LoadAreaData", method = RequestMethod.GET)
	public @ResponseBody List<clsPOSAreaMasterBean> funLoadAreaData(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		List<clsPOSAreaMasterBean> listAreaData = new ArrayList<clsPOSAreaMasterBean>();

		JSONArray jArryPosList = objPOSGlobal.funGetAllAreaForMaster(clientCode);

		if (null != jArryPosList) {
			for (int cnt = 0; cnt < jArryPosList.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArryPosList.get(cnt);
				clsPOSAreaMasterBean objAreaDtl = new clsPOSAreaMasterBean();
				objAreaDtl.setStrAreaCode((String) jobj.get("strAreaCode"));
				objAreaDtl.setStrAreaName((String) jobj.get("strAreaName"));

				listAreaData.add(objAreaDtl);
			}
		}
		return listAreaData;
	}
}
