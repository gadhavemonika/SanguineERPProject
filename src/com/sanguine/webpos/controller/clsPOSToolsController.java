package com.sanguine.webpos.controller;

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

import com.sanguine.bean.clsFormSearchElements;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsTreeMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSecurityShellService;
import com.sanguine.webpos.bean.clsDatabaseBackupBean;
import com.sanguine.webpos.bean.clsFormMasterBean;
import com.sanguine.webpos.bean.clsPOSConfigSettingBean;
import com.sanguine.webpos.bean.clsPOSMasterBean;
import com.sanguine.webpos.bean.clsPOSMenuHeadBean;

@Controller
public class clsPOSToolsController {

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	Map map = new HashMap();

	@RequestMapping(value = "/frmPOSClearMasterTransaction", method = RequestMethod.GET)
	public ModelAndView funOpenPOSTools(Map<String, Object> model, HttpServletRequest req) {

		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		model.put("headerName", "Transaction List");

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSClearMasterTransaction");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSClearMasterTransaction");
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/loadPosName", method = RequestMethod.GET)
	public @ResponseBody List funLoadPropertyMaster(HttpServletRequest req) {
		JSONObject jObjDtl = new JSONObject();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		jObjDtl.put("clientCode", clientCode);
		List<Object> posList = new ArrayList<Object>();
		posList.add("All");
		map.put("All", "All");
		JSONArray jArrList = new JSONArray();
		jArrList = objPOSGlobal.funGetAllPOSForMaster(clientCode);
		for (int i = 0; i < jArrList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArrList.get(i);
			posList.add(josnObjRet.get("strPosName"));
			// map.put(josnObjRet.get("strPosName"),
			// josnObjRet.get("strPosCode"));
			map.put(josnObjRet.get("strPosCode"), josnObjRet.get("strPosName"));
		}
		return posList;

	}

	/**
	 * Open Structure Update Form
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/frmPOSTools", method = RequestMethod.GET)
	public ModelAndView funOpenStructureUpdateForm(Map<String, Object> model, HttpServletRequest req) {

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSTools_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSTools");
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/posUpdateStructure", method = RequestMethod.GET)
	public @ResponseBody String funPOSUpdateStructure(HttpServletRequest req) {
		JSONObject jObjDtl = new JSONObject();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String posCode = req.getSession().getAttribute("loginPOS").toString();
		String dateCreated = objGlobal.funGetCurrentDateTime("yyyy-MM-dd");
		String dateEdited = objGlobal.funGetCurrentDateTime("yyyy-MM-dd");

		jObjDtl.put("clientCode", clientCode);
		jObjDtl.put("userCode", userCode);
		jObjDtl.put("dateCreated", dateCreated);
		jObjDtl.put("dateEdited", dateEdited);
		jObjDtl.put("posCode", posCode);

		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTools/funPOSUpdateStructure";

		JSONObject jObj = objGlobal.funPOSTMethodUrlJosnObjectData(posUrl, jObjDtl);
		// objStructureUpdateService.funUpdateStructure(clientCode);
		return "Structure Update Successfully";
	}

	@RequestMapping(value = "/POSClearTransaction", method = RequestMethod.GET)
	public @ResponseBody String funPOSClearTransaction(@RequestParam(value = "frmName") String frmName, @RequestParam(value = "posName") String posName, @RequestParam(value = "fromDate") String fromDate, @RequestParam(value = "toDate") String toDate, @RequestParam(value = "userName") String userName, @RequestParam(value = "chkAllSelected") String chkAllSelected, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String loginPosCode = req.getSession().getAttribute("loginPOS").toString();
		JSONObject jObjPosCode = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOSDate" + "?POSCode=" + loginPosCode);
		String posDate = jObjPosCode.get("POSDate").toString().split(" ")[0];

		String str[] = frmName.split(",");
		JSONArray jArr = new JSONArray();
		for (int i = 0; i < str.length; i++) {
			JSONObject jObj = new JSONObject();
			String formName = str[i];
			jObj.put("formName", formName);
			jArr.add(jObj);
		}

		String posCode = "";
		if (map.containsKey(posName)) {
			posCode = (String) map.get(posName);

		}

		JSONObject jObjDtl = new JSONObject();
		jObjDtl.put("clientCode", clientCode);
		jObjDtl.put("chkAllSelected", chkAllSelected);
		jObjDtl.put("posCode", posCode);
		jObjDtl.put("fromDate", fromDate);
		jObjDtl.put("toDate", toDate);
		jObjDtl.put("userName", userName);
		jObjDtl.put("posDate", posDate);
		jObjDtl.put("str", jArr);
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTools/funCleanTransaction";
		JSONObject jObj1 = objGlobal.funPOSTMethodUrlJosnObjectData(posUrl, jObjDtl);
		String result = jObj1.get("return").toString();
		if (result.equalsIgnoreCase("true")) {
			System.out.println("Transaction Clear Successfully");
		}
		// objStructureUpdateService.funClearTransaction(clientCode,str);
		return "Transaction Clear Successfully";
	}

	@RequestMapping(value = "/POSClearMaster", method = RequestMethod.GET)
	public @ResponseBody String funPOSClearMaster(@RequestParam(value = "frmName") String frmName, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String str[] = frmName.split(",");

		JSONArray jArr = new JSONArray();
		for (int i = 0; i < str.length; i++) {
			JSONObject jObj = new JSONObject();
			String formName = str[i];
			jObj.put("formName", formName);
			jArr.add(jObj);
		}

		JSONObject jObjDtl = new JSONObject();
		jObjDtl.put("clientCode", clientCode);
		jObjDtl.put("str", jArr);
		// String posUrl =
		// clsPOSGlobalFunctionsController.POSWSURL+"/WebPOSTools/funClearMaster";
		// JSONObject
		// jObj=objGlobal.funPOSTMethodUrlJosnObjectData(posUrl,jObjDtl);
		//
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTools/funCleanMaster";
		JSONObject jObj1 = objGlobal.funPOSTMethodUrlJosnObjectData(posUrl, jObjDtl);
		String msg = (String) jObj1.get("return");
		System.out.println("Master Clear Successfully");
		// +"?clientCode="+clientCode+"&str="+str;
		// objStructureUpdateService.funClearMaster(clientCode,str);
		return "Master Clear Successfully";
	}

	@RequestMapping(value = "/frmOpenPOSConfigSetting", method = RequestMethod.GET)
	public ModelAndView funOpenPOSConfigSetting(Map<String, Object> model, HttpServletRequest req) {

		clsPOSConfigSettingBean objBean = new clsPOSConfigSettingBean();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		objBean = funLoadConfigData(clientCode);
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSConfigSetting", "command", objBean);
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSConfigSetting", "command", objBean);
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/frmOpenPOSDBBackup", method = RequestMethod.GET)
	public ModelAndView frmOpenPOSDBBackup(Map<String, Object> model, HttpServletRequest req) {

		clsDatabaseBackupBean objBean = new clsDatabaseBackupBean();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		model.put("urlHits", urlHits);

		String backupPath = System.getProperty("user.dir") + "\\DBBackup";
		model.put("backupPath", backupPath);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSDataBaseBackup", "command", objBean);
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSDataBaseBackup", "command", objBean);
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/savePOSConfigSetting", method = RequestMethod.POST)
	public ModelAndView funSavePOSConfigSetting(@ModelAttribute("command") @Valid clsPOSConfigSettingBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		JSONObject jObjPOSMaster = new JSONObject();
		jObjPOSMaster.put("strServer", objBean.getStrServer());
		jObjPOSMaster.put("strDBName", objBean.getStrDBName());
		jObjPOSMaster.put("strUserID", objBean.getStrUserID());
		jObjPOSMaster.put("strPassword", objBean.getStrPassword());
		jObjPOSMaster.put("strIPAddress", objBean.getStrIPAddress());
		jObjPOSMaster.put("strPort", objBean.getStrPort());
		jObjPOSMaster.put("strBackupPath", objBean.getStrBackupPath());
		jObjPOSMaster.put("strExportPath", objBean.getStrExportPath());
		jObjPOSMaster.put("strImagePath", objBean.getStrImagePath());
		jObjPOSMaster.put("strHOWebServiceUrl", objBean.getStrHOWebServiceUrl());
		jObjPOSMaster.put("strMMSWebServiceUrl", objBean.getStrMMSWebServiceUrl());
		jObjPOSMaster.put("strOS", objBean.getStrOS());
		jObjPOSMaster.put("strDefaultPrinter", objBean.getStrDefaultPrinter());
		jObjPOSMaster.put("strPrinterType", objBean.getStrPrinterType());
		jObjPOSMaster.put("strTouchScreenMode", objBean.getStrTouchScreenMode());
		jObjPOSMaster.put("strServerFilePath", objBean.getStrServerFilePath());
		jObjPOSMaster.put("strSelectWaiterFromCardSwipe", objBean.getStrSelectWaiterFromCardSwipe());
		jObjPOSMaster.put("strMySQBackupFilePath", objBean.getStrMySQBackupFilePath());
		jObjPOSMaster.put("strHOCommunication", objBean.getStrHOCommunication());
		jObjPOSMaster.put("strAdvReceiptPrinter", objBean.getStrAdvReceiptPrinter());
		jObjPOSMaster.put("strClientCode", clientCode);

		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTools/funGetSaveConfigSetting" + "?clientCode=" + clientCode;

		JSONObject jObjSettlementData = new JSONObject();

		jObjSettlementData = objGlobal.funPOSTMethodUrlJosnObjectData(posUrl, jObjPOSMaster);

		req.getSession().setAttribute("success", true);
		req.getSession().setAttribute("successMessage", " " + jObjSettlementData.get("status").toString());

		return new ModelAndView("frmPOSConfigSetting", "command", objBean);

	}

	private clsPOSConfigSettingBean funLoadConfigData(String strClientCode) {
		clsPOSConfigSettingBean objBean = new clsPOSConfigSettingBean();
		JSONObject jObjSettlementData = new JSONObject();
		JSONObject jObjPOSMaster = new JSONObject();
		jObjPOSMaster.put("strClientCode", strClientCode);

		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTools/funLoadConfigSetting";
		jObjSettlementData = objGlobal.funPOSTMethodUrlJosnObjectData(posUrl, jObjPOSMaster);

		if (jObjSettlementData.size() > 0) {
			JSONArray jArrConfigData = (JSONArray) jObjSettlementData.get("configSetting");

			if (jArrConfigData.size() > 0) {
				JSONObject jObj = (JSONObject) jArrConfigData.get(0);

				objBean.setStrServer(jObj.get("strServer").toString());
				objBean.setStrDBName(jObj.get("strDBName").toString());
				objBean.setStrUserID(jObj.get("strUserID").toString());
				objBean.setStrPassword(jObj.get("strPassword").toString());
				objBean.setStrIPAddress(jObj.get("strIPAddress").toString());
				objBean.setStrPort(jObj.get("strPort").toString());
				objBean.setStrBackupPath(jObj.get("strBackupPath").toString());
				objBean.setStrExportPath(jObj.get("strExportPath").toString());
				objBean.setStrImagePath(jObj.get("strImagePath").toString());
				objBean.setStrHOWebServiceUrl(jObj.get("strHOWebServiceUrl").toString());
				objBean.setStrMMSWebServiceUrl(jObj.get("strMMSWebServiceUrl").toString());
				objBean.setStrOS(jObj.get("strOS").toString());
				objBean.setStrDefaultPrinter(jObj.get("strDefaultPrinter").toString());
				objBean.setStrPrinterType(jObj.get("strPrinterType").toString());
				objBean.setStrTouchScreenMode(jObj.get("strTouchScreenMode").toString());
				objBean.setStrServerFilePath(jObj.get("strServerFilePath").toString());
				objBean.setStrSelectWaiterFromCardSwipe(jObj.get("strSelectWaiterFromCardSwipe").toString());
				objBean.setStrMySQBackupFilePath(jObj.get("strMySQBackupFilePath").toString());
				objBean.setStrHOCommunication(jObj.get("strHOCommunication").toString());
				objBean.setStrAdvReceiptPrinter(jObj.get("strAdvReceiptPrinter").toString());

			}
		}
		return objBean;

	}

	@RequestMapping(value = "/loadMastersData", method = RequestMethod.GET)
	public @ResponseBody JSONObject funLoadMenuHeadDtlData(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String strType = req.getParameter("strHeadingType").toString();
		JSONObject jObjRet = new JSONObject();
		String posUrl = "";
		JSONArray jObj = null;
		List<clsFormMasterBean> listMenuHedaData = new ArrayList<clsFormMasterBean>();
		JSONObject jObjData = new JSONObject();

		if (strType.equalsIgnoreCase("Master")) {
			posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTools/funClearMaster";
		} else {
			posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTools/funClearTransaction";
		}

		jObjData = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		jObj = (JSONArray) jObjData.get("jArr");

		jObjRet.put("masterDtl", jObj);
		return jObjRet;
	}

	@RequestMapping(value = "/loadDBBackupData", method = RequestMethod.GET)
	public @ResponseBody JSONObject funSavePOSDataBaseBackup(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userName = req.getParameter("userName").toString();
		String password = req.getParameter("password").toString();
		String dataBase = req.getParameter("dataBase").toString();
		String backupPath = req.getParameter("backupPath").toString();
		String posCode = req.getSession().getAttribute("loginPOS").toString();

		JSONObject jObjPosCode = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOSDate" + "?POSCode=" + posCode);
		String posDate = jObjPosCode.get("POSDate").toString().split(" ")[0];

		String userCode = req.getSession().getAttribute("usercode").toString();

		JSONObject jObjRet = new JSONObject();

		JSONArray jObj = null;

		JSONObject jObjDtl = new JSONObject();
		jObjDtl.put("clientCode", clientCode);
		jObjDtl.put("backupPath", backupPath);
		jObjDtl.put("posCode", posCode);
		jObjDtl.put("posDate", posDate);
		jObjDtl.put("userCode", userCode);

		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTools/funDBBackup";
		JSONObject jObj1 = objGlobal.funPOSTMethodUrlJosnObjectData(posUrl, jObjDtl);

		return jObjRet;
	}
}
