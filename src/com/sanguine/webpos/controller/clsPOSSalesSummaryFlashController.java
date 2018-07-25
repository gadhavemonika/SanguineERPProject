package com.sanguine.webpos.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpos.bean.clsPOSBillReportBean;
import com.sanguine.webpos.bean.clsWebPOSReportBean;

@Controller
public class clsPOSSalesSummaryFlashController {

	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobalFunctions;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private ServletContext servletContext;

	@RequestMapping(value = "/frmPOSSalesSummaryFlash", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		List poslist = new ArrayList();
		poslist.add("ALL");

		// JSONObject jObj =
		// objGlobalFunctions.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL+"/WebPOSSGMaster/funGetPOS");
		JSONArray jArryPosList = objPOSGlobalFunctions.funGetAllPOSForMaster(strClientCode);
		// JSONArray jArryPosList =(JSONArray) jObj.get("posList");
		for (int i = 0; i < jArryPosList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryPosList.get(i);
			poslist.add(josnObjRet.get("strPosName"));
		}
		model.put("posList", poslist);
		String strPosCode = request.getSession().getAttribute("loginPOS").toString();
		String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOSDate" + "?POSCode=" + strPosCode;
		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(posURL);
		String posDate = jObj.get("POSDate").toString();
		request.setAttribute("POSDate", posDate);

		List payModeList = new ArrayList<String>();
		payModeList.add("ALL");
		Map hmPayData = funGetPaymentMode("ALL");
		List list = (List) hmPayData.get("payName");
		for (int i = 0; i < list.size(); i++) {
			payModeList.add(list.get(i));
		}
		model.put("payModeList", payModeList);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSSalesSummaryFlash_1", "command", new clsWebPOSReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSSalesSummaryFlash", "command", new clsWebPOSReportBean());
		} else {
			return null;
		}

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadColumnData", method = RequestMethod.GET)
	private @ResponseBody List funColumnData(HttpServletResponse resp, HttpServletRequest req) {
		List listPayMode = new ArrayList();
		try {

			String payMode = req.getParameter("payMode").toString();
			Map hmPayData = funGetPaymentMode(payMode);
			listPayMode = (List) hmPayData.get("payName");

		} catch (Exception e) {

		}
		return listPayMode;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadPaymentData", method = RequestMethod.GET)
	private @ResponseBody List funReport(HttpServletResponse resp, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();

		List list = new ArrayList();
		try {
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptsalesFlashSummary.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();

			String fromDate = req.getParameter("fromDate").toString();
			String toDate = req.getParameter("toDate").toString();
			String strFromdate = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];

			String strToDate = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];

			String payName = req.getParameter("payMode").toString();

			String strPOSName = req.getParameter("posName").toString();

			String strReportType = req.getParameter("reportType").toString();

			String posCode = "ALL";
			String payCode = "ALL";
			Map hmPayData = funGetPaymentMode(payName);
			if (!strPOSName.equalsIgnoreCase("ALL")) {
				posCode = funGetPOSCode(strPOSName);
			}

			if (!payName.equalsIgnoreCase("ALL")) {

				Map hmPayCode = (Map) hmPayData.get("payCode");
				payCode = (String) hmPayCode.get(payName);

			}
			List listPayMode = (List) hmPayData.get("payName");
			JSONObject jObjFillter = new JSONObject();
			jObjFillter.put("strFromdate", strFromdate);
			jObjFillter.put("strToDate", strToDate);
			jObjFillter.put("posCode", posCode);
			jObjFillter.put("payMode", payCode);
			jObjFillter.put("userCode", userCode);
			jObjFillter.put("reportType", strReportType);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funSalesSummaryFlash", jObjFillter);

			JSONArray jarr = (JSONArray) jObj.get("jArr");

			for (int i = 0; i < jarr.size(); i++) {
				JSONObject jObjtemp = (JSONObject) jarr.get(i);

				list.add(jObjtemp.get("strPOSCode").toString());
				list.add(jObjtemp.get("strPosName").toString());
				list.add(jObjtemp.get("dteBillDate").toString());

				Map hmSettelmentDesc = (Map) jObjtemp.get("hmSettelmentDesc");

				for (int cnt = 0; cnt < listPayMode.size(); cnt++) {
					if (hmSettelmentDesc.containsKey(listPayMode.get(cnt))) {
						list.add(hmSettelmentDesc.get(listPayMode.get(cnt)).toString());

					} else {
						list.add("0");

					}

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;

	}

	public String funGetPOSCode(String strPOSName) {
		String posCode = "";
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSPOSMaster/funGetPOSNameData";

		System.out.println(posUrl);

		try {
			JSONObject objRows = new JSONObject();
			objRows.put("strPOSName", strPOSName);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(posUrl, objRows);
			posCode = jObj.get("strPosCode").toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return posCode;

	}

	public Map funGetPaymentMode(String payName) {
		List listPayMode = new ArrayList<String>();
		Map hmPayData = new HashMap();
		Map hmPayCode = new HashMap();

		String sqlPaymentMode = "";
		if (payName.equalsIgnoreCase("ALL")) {
			sqlPaymentMode = "select strSettelmentDesc,strSettelmentCode from jpos.tblsettelmenthd order by strSettelmentDesc";
		} else {
			sqlPaymentMode = "select strSettelmentDesc,strSettelmentCode  from jpos.tblsettelmenthd where strSettelmentDesc='" + payName + "' order by strSettelmentDesc";
		}
		List list = objGlobalFunctionsService.funGetList(sqlPaymentMode, "sql");
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Object[] arrObj = (Object[]) list.get(i);
				listPayMode.add(arrObj[0].toString());
				hmPayCode.put(arrObj[0].toString(), arrObj[1].toString());
			}
		}
		hmPayData.put("payName", listPayMode);
		hmPayData.put("payCode", hmPayCode);
		return hmPayData;
	}

}
