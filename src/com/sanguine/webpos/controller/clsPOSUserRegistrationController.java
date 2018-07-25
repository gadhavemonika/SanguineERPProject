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

import org.hibernate.usertype.UserType;
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
import com.sanguine.webpos.bean.clsPOSCustomerTypeMasterBean;
import com.sanguine.webpos.bean.clsPOSMasterBean;
import com.sanguine.webpos.bean.clsPOSTaxMasterBean;
import com.sanguine.webpos.bean.clsPOSUserAccessBean;
import com.sanguine.webpos.bean.clsPOSUserRegistrationBean;
import com.sanguine.webpos.bean.clsSettlementDetailsBean;

@Controller
public class clsPOSUserRegistrationController {
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;
	private String userType = "";
	Map map = new HashMap();

	@RequestMapping(value = "/frmPOSUserRegistration", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsPOSUserRegistrationBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		// String
		// clientCode=request.getSession().getAttribute("clientCode").toString();

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSUserRegistration_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSUserRegistration");
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/saveUsersAccess", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSUserRegistrationBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";

		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjUserAccess = new JSONObject();
			clsPOSUserAccessBean obj = null;
			JSONArray jArrMasterList = new JSONArray();
			jObjUserAccess.put("UserCode", objBean.getStrUserCode());
			jObjUserAccess.put("ClientCode", clientCode);
			if (objBean.getStrUserType().equalsIgnoreCase("YES")) {
				jObjUserAccess.put("UserType", "Super");
			} else {
				jObjUserAccess.put("UserType", "Op");
			}

			// Access Master Forms Data

			List<clsPOSUserAccessBean> listMaster = objBean.getListMasterForm();
			for (int i = 0; i < listMaster.size(); i++) {

				obj = (clsPOSUserAccessBean) listMaster.get(i);
				JSONObject jObjData = new JSONObject();
				String grant = "false";
				if (obj.getStrGrant() != null) {
					grant = "true";
					jObjData.put("FormName", obj.getStrFormName());
					jObjData.put("Grant", "true");
					jObjData.put("TLA", "false");
					jObjData.put("Audit", "false");
					jArrMasterList.add(jObjData);
				}
			}

			// Access Transaction Forms Data
			List<clsPOSUserAccessBean> listTransaction = objBean.getListTransactionForm();
			JSONArray jArrTransactionList = new JSONArray();
			for (int i = 0; i < listTransaction.size(); i++) {

				obj = (clsPOSUserAccessBean) listTransaction.get(i);
				JSONObject jObjData = new JSONObject();
				if (obj.getStrGrant() != null) {
					jObjData.put("FormName", obj.getStrFormName());
					jObjData.put("Grant", objGlobal.funIfNull(obj.getStrGrant(), "false", "true"));
					jObjData.put("TLA", objGlobal.funIfNull(obj.getStrTLA(), "false", "true"));
					jObjData.put("Audit", objGlobal.funIfNull(obj.getStrAuditing(), "false", "true"));
					jArrTransactionList.add(jObjData);
				}
			}

			// Access Reports Forms Data
			List<clsPOSUserAccessBean> listReports = objBean.getListReportForm();
			JSONArray jArrReportsList = new JSONArray();
			for (int i = 0; i < listReports.size(); i++) {

				obj = (clsPOSUserAccessBean) listReports.get(i);
				JSONObject jObjData = new JSONObject();
				if (obj.getStrGrant() != null) {
					jObjData.put("FormName", obj.getStrFormName());
					jObjData.put("Grant", "true");
					jObjData.put("TLA", "false");
					jObjData.put("Audit", "false");
					jArrReportsList.add(jObjData);
				}
			}

			// Access Utilities Forms Data
			List<clsPOSUserAccessBean> listUtilities = objBean.getListUtilitiesForm();
			JSONArray jArrUtilitiesList = new JSONArray();
			for (int i = 0; i < listUtilities.size(); i++) {

				obj = (clsPOSUserAccessBean) listUtilities.get(i);
				JSONObject jObjData = new JSONObject();
				if (obj.getStrGrant() != null) {
					jObjData.put("FormName", obj.getStrFormName());
					jObjData.put("Grant", "true");
					jObjData.put("TLA", "false");
					jObjData.put("Audit", "false");
					jArrUtilitiesList.add(jObjData);
				}
			}

			jObjUserAccess.put("MasterFormDetails", jArrMasterList);
			jObjUserAccess.put("TransactionFormDetails", jArrTransactionList);
			jObjUserAccess.put("ReportsFormDetails", jArrReportsList);
			jObjUserAccess.put("UtilitiesFormDetails", jArrUtilitiesList);

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funSaveUserAccess";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjUserAccess.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSUserRegistration.html?saddr=" + urlHits);

		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/LoadMasterModuleData", method = RequestMethod.GET)
	public @ResponseBody List<clsPOSUserRegistrationBean> funLoadMasterModuleData(@RequestParam("ModuleType") String moduleType, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		List<clsPOSUserRegistrationBean> listMasterModuleData = new ArrayList<clsPOSUserRegistrationBean>();

		JSONArray jArryPosList = objPOSGlobal.funGetAllForm(clientCode);

		if (null != jArryPosList)

		{
			for (int cnt = 0; cnt < jArryPosList.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArryPosList.get(cnt);
				clsPOSUserRegistrationBean objFormDtl = new clsPOSUserRegistrationBean();
				String moduleName = (String) jobj.get("strModuleName");
				String type = (String) jobj.get("strModuleType");
				if (moduleType.equals("M")) {
					if ((!moduleName.equals("Customer Master")) && type.equals(moduleType)) {
						objFormDtl.setStrFormName((String) jobj.get("strFormName"));
						objFormDtl.setStrModuleName((String) jobj.get("strModuleName"));
						objFormDtl.setStrModuleType((String) jobj.get("strModuleType"));
						listMasterModuleData.add(objFormDtl);
					}
				} else if (moduleType.equals("T") && type.equals(moduleType)) {
					objFormDtl.setStrFormName((String) jobj.get("strFormName"));
					objFormDtl.setStrModuleName((String) jobj.get("strModuleName"));
					objFormDtl.setStrModuleType((String) jobj.get("strModuleType"));
					listMasterModuleData.add(objFormDtl);
				} else if (moduleType.equals("R") && type.equals(moduleType)) {
					objFormDtl.setStrFormName((String) jobj.get("strFormName"));
					objFormDtl.setStrModuleName((String) jobj.get("strModuleName"));
					objFormDtl.setStrModuleType((String) jobj.get("strModuleType"));
					listMasterModuleData.add(objFormDtl);
				} else if (moduleType.equals("U") && type.equals(moduleType)) {
					objFormDtl.setStrFormName((String) jobj.get("strFormName"));
					objFormDtl.setStrModuleName((String) jobj.get("strModuleName"));
					objFormDtl.setStrModuleType((String) jobj.get("strModuleType"));
					listMasterModuleData.add(objFormDtl);
				}
			}
		}

		if (listMasterModuleData.size() > 0 && (moduleType.equals("T"))) {
			clsPOSUserRegistrationBean objFormDtl = new clsPOSUserRegistrationBean();
			objFormDtl.setStrFormName("frmCustomerMaster");
			objFormDtl.setStrModuleName("Customer Master");
			objFormDtl.setStrModuleType(moduleType);
			listMasterModuleData.add(objFormDtl);
		}

		return listMasterModuleData;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadWebStockUserMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPOSUserRegistrationBean funSetSearchFields(@RequestParam("UserCode") String UserCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSUserRegistrationBean objUserMaster = new clsPOSUserRegistrationBean();
		objUserMaster = funGetUserData(clientCode, UserCode);
		return objUserMaster;
	}

	@RequestMapping(value = "/loadUsersModuleData", method = RequestMethod.GET)
	public @ResponseBody clsPOSUserRegistrationBean funLoadUsersModule(@RequestParam("userCode") String userCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSUserRegistrationBean objUserRegistration = null;
		List<clsPOSUserAccessBean> listAllSelectedModules = new ArrayList<clsPOSUserAccessBean>();
		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funGetUserAccessData" + "?searchCode=" + userCode;
		System.out.println(posUrl);

		jObjSearchDetails = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		JSONArray jArrSearchList = (JSONArray) jObjSearchDetails.get("POSUserAccessMaster");
		if (null != jArrSearchList) {
			objUserRegistration = new clsPOSUserRegistrationBean();
			objUserRegistration = funGetUserData(clientCode, userCode);
			for (int cnt = 0; cnt < jArrSearchList.size(); cnt++) {
				JSONArray jArrList = (JSONArray) jArrSearchList.get(cnt);
				clsPOSUserAccessBean objUser = new clsPOSUserAccessBean();
				objUser.setStrUserCode(userCode);
				objUser.setStrFormName((String) jArrList.get(1)); // formName
				objUser.setStrGrant((String) jArrList.get(2)); // grant
				objUser.setStrAuditing((String) jArrList.get(3)); // tla
				objUser.setStrTLA((String) jArrList.get(4)); // audit
				listAllSelectedModules.add(objUser);
			}
			objUserRegistration.setListUsersSelectedForms(listAllSelectedModules);
		}

		if (null == objUserRegistration) {
			objUserRegistration = new clsPOSUserRegistrationBean();
			objUserRegistration.setStrUserCode("Invalid Code");
		}

		return objUserRegistration;
	}

	private clsPOSUserRegistrationBean funGetUserData(String clientCode, String userCode) {
		clsPOSUserRegistrationBean objUserMaster = null;
		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/MMSIntegration/funGetUserMaster" + "?ClientCode=" + clientCode;
		System.out.println("posUrl:" + posUrl);

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
			JSONArray jArrSearchList = (JSONArray) jObjSearchDetails.get("webstockusermaster");
			if (null != jArrSearchList) {
				for (int i = 0; i < jArrSearchList.size(); i++) {
					JSONArray jArrList = (JSONArray) jArrSearchList.get(i);
					if (userCode.equals(((String) jArrList.get(0)))) {
						objUserMaster = new clsPOSUserRegistrationBean();
						objUserMaster.setStrUserCode((String) jArrList.get(0));
						objUserMaster.setStrUserName((String) jArrList.get(1));
						objUserMaster.setStrUserType((String) jArrList.get(2));
						String user = (String) jArrList.get(2);
						if (user.equals("YES")) {
							userType = "Super";
						} else {
							userType = "Op";
						}
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return objUserMaster;
	}
}
