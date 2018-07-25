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
import com.sanguine.service.clsRecipeMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webpos.bean.clsChangePasswordBean;
import com.sanguine.webpos.bean.clsMoveKOTBean;

@Controller
public class clsPOSChangePasswordController {
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

	@RequestMapping(value = "/frmChangePassword", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsChangePasswordBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		String userCode = request.getSession().getAttribute("usercode").toString();
		model.put("userCode", userCode);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmChangePassword1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmChangePassword");
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/savrOrUpdateUserPassword", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsChangePasswordBean objBean, BindingResult result, HttpServletRequest req) {

		String posCode = "";

		try {

			String clientCode = req.getSession().getAttribute("clientCode").toString();

			JSONObject jObjAreaMaster = new JSONObject();
			jObjAreaMaster.put("oldPassword", objBean.getStrOldPass());
			jObjAreaMaster.put("newPassword", objBean.getStrNewPass());
			jObjAreaMaster.put("userCode", objBean.getStrUserCode());

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funSaveUserPassword";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjAreaMaster.toString().getBytes());
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

			JSONObject jObj = new JSONObject();
			jObj = objGlobal.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funSaveUserPasswordInMMS", jObjAreaMaster);

			req.getSession().setAttribute("success", true);

			req.getSession().setAttribute("successMessage", " " + op);

			return new ModelAndView("redirect:/frmChangePassword.html");
		} catch (Exception ex) {

			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/checkUserName", method = RequestMethod.GET)
	public @ResponseBody String funCheckUserName(HttpServletRequest req) {
		String result = "";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getParameter("userCode");
		String oldPass = req.getParameter("oldPass");

		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funGetOldPassword" + "?userCode=" + userCode + "&oldPass=" + oldPass;

		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(posUrl);
		result = jObj.get("PasswordMatch").toString();
		if (result.equalsIgnoreCase("true")) {
			result = "true";
		} else {
			result = "false";
		}

		return result;
	}

}
