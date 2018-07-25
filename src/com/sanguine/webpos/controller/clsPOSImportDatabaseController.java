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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsPOSImportDatabaseBean;
import com.sanguine.webpos.bean.clsPOSTableMasterBean;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

@Controller
public class clsPOSImportDatabaseController {
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	String dbURL = "";

	@RequestMapping(value = "/frmPOSImportDatabase", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsPOSImportDatabaseBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {

		return new ModelAndView("frmPOSImportDatabase");

	}

	@RequestMapping(value = "/ConnectDatabase", method = RequestMethod.GET)
	public @ResponseBody boolean funLoadTableData(HttpServletRequest req, @RequestParam("strDatabaseName") String strDatabaseName, @RequestParam("strIPAddress") String strIPAddress, @RequestParam("strPortNo") String strPortNo, @RequestParam("strUserName") String strUserName, @RequestParam("strPassword") String strPassword) {
		boolean flag = false;

		Connection dbCon = null;
		try {
			int cnt = 0;
			String dbName = strDatabaseName;
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			dbURL = "jdbc:sqlserver://" + strIPAddress + ":" + strPortNo + ";databaseName=" + dbName;

			dbCon = DriverManager.getConnection(dbURL, strUserName, strPassword);

			if (dbCon.isValid(cnt)) {
				flag = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return flag;

	}

	@RequestMapping(value = "/importDatabase", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSImportDatabaseBean objBean, BindingResult result, HttpServletRequest req) {

		try {

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();
			String posCode = req.getSession().getAttribute("loginPOS").toString();

			String strIPAddress = objBean.getStrIPAddress();
			String strPortNo = objBean.getStrPortNo();
			String dbName = objBean.getStrDatabaseName();
			String strUserName = objBean.getStrUserName();
			String strPassword = objBean.getStrPassword();
			JSONObject jObjAreaMaster = new JSONObject();
			String dbURL = "jdbc:sqlserver://" + strIPAddress + ":" + strPortNo + ";databaseName=" + dbName;
			jObjAreaMaster.put("dbURL", dbURL);
			jObjAreaMaster.put("strUserName", strUserName);
			jObjAreaMaster.put("strPassword", strPassword);
			jObjAreaMaster.put("posCode", posCode);
			jObjAreaMaster.put("User", webStockUserCode);
			jObjAreaMaster.put("ClientCode", clientCode);

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funImportDatabase";
			JSONObject jsonObj = objGlobal.funPOSTMethodUrlJosnObjectData(posURL, jObjAreaMaster);
			boolean flag = (boolean) jsonObj.get("flag");
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", flag);

			return new ModelAndView("redirect:/frmPOSImportDatabase.html");
		} catch (Exception ex) {

			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

}
