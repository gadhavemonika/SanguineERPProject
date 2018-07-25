package com.sanguine.webpos.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.crm.bean.clsSalesOrderBean;
import com.sanguine.webpos.bean.clsDayEndProcessBean;
import com.sanguine.webpos.bean.clsPOSItemModifierMasterBean;
import com.sanguine.webpos.bean.clsPOSMenuHeadBean;
import com.sanguine.webpos.bean.clsPOSSalesFlashReportsBean;

@Controller
public class clsPOSDayEndDialogController {

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private ServletContext servletContext;

	@RequestMapping(value = "/frmPOSDayEndDialog", method = RequestMethod.GET)
	public ModelAndView funOpenPOSTools(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		// req.getSession().setAttribute("pageload", 1);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSDayEndDialog", "command", new clsDayEndProcessBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSDayEndDialog", "command", new clsDayEndProcessBean());
		} else {
			return null;
		}

	}

	// all reports data from DB through web service
	@RequestMapping(value = "/loadAllReportsforMail", method = RequestMethod.GET)
	public @ResponseBody List<clsDayEndProcessBean> funLoadAllReportsName(HttpServletRequest request) {
		List<clsDayEndProcessBean> listbean = new ArrayList<clsDayEndProcessBean>();
		clsDayEndProcessBean obBean;// =new clsDayEndProcessBean();

		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String strPOSCode = request.getSession().getAttribute("loginPOS").toString();

		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/DayEndProcessTransaction" + "/funLoadAllReportsName?strPOSCode=" + strPOSCode + "&strClientCode=" + strClientCode);
		ArrayList alReportName = new ArrayList<String>();
		ArrayList alCheckRpt = new ArrayList<Boolean>();

		Gson gson = new Gson();
		Type listType = new TypeToken<List<String>>() {
		}.getType();
		alReportName = gson.fromJson(jObj.get("ReportName").toString(), listType);
		alCheckRpt = gson.fromJson(jObj.get("CheckReport").toString(), listType);
		for (int i = 0; i < alReportName.size(); i++) {
			obBean = new clsDayEndProcessBean();
			obBean.setStrReportName(alReportName.get(i).toString());
			if (alCheckRpt.size() == alReportName.size()) {
				obBean.setStrReportCheck(Boolean.parseBoolean(alCheckRpt.get(i).toString()));
			} else {
				obBean.setStrReportCheck(Boolean.parseBoolean("false"));
			}

			listbean.add(obBean);
		}

		return listbean;
	}

	@RequestMapping(value = "/DayEndMailReport", method = RequestMethod.POST)
	public ModelAndView funGetSelectedMailReport(@ModelAttribute("command") @Valid clsDayEndProcessBean objBean, BindingResult result, HttpServletRequest req) {
		JSONObject jsMailReportData = new JSONObject();
		String urlHits = "2";
		String userCode = req.getSession().getAttribute("usercode").toString();
		String strClientCode = req.getSession().getAttribute("clientCode").toString();
		String strPOSDate = req.getSession().getAttribute("POSDate").toString();
		String strPOSCode = req.getSession().getAttribute("loginPOS").toString();

		clsDayEndProcessBean obDayEndd;
		ArrayList alReportName = new ArrayList<String>();
		ArrayList alCheckRpt = new ArrayList<Boolean>();
		if (!result.hasErrors()) {
			List<clsDayEndProcessBean> listMailReport = objBean.getListMailReport();
			for (int i = 0; i < listMailReport.size(); i++) {
				obDayEndd = listMailReport.get(i);
				// alReportName.add(obDayEndd.getStrReportName());
				if (obDayEndd.getStrReportCheck() == null) {
					alCheckRpt.add(false);
				} else {
					alReportName.add(obDayEndd.getStrReportName());
					alCheckRpt.add(obDayEndd.getStrReportCheck());
				}
			}

		}

		Gson gson = new Gson();
		Type type = new TypeToken<List<String>>() {
		}.getType();
		String ReportName = gson.toJson(alReportName, type);
		String CheckReport = gson.toJson(alCheckRpt, type);
		jsMailReportData.put("ReportName", ReportName);
		// jsMailReportData.put("CheckReport", CheckReport);

		jsMailReportData.put("strPOSCode", strPOSCode);
		jsMailReportData.put("userCode", userCode);
		jsMailReportData.put("strPOSDate", strPOSDate);
		jsMailReportData.put("strClientCode", strClientCode);
		String Status = "true";
		JSONObject job = new JSONObject();
		job = objGlobalFunctions.funPOSTMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/DayEndProcessTransaction/funSendDayEndMailReports", jsMailReportData);
		/*
		 * JSONObject jsOb=(JSONObject)job.get("final");
		 * Status=jsOb.get("Status").toString();
		 */
		return new ModelAndView("redirect:/frmPOSDayEndDialog.html?saddr=" + urlHits);// "redirect:/frmPOSDayEndDialog.html?saddr="+urlHits
		// return Status;
		// return null;
	}

}
