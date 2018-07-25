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
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsPOSAIPBReportBean;
import com.sanguine.webpos.bean.clsWebPOSReportBean;

@Controller
public class clsPOSAvgTicketValueReportController {

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private ServletContext servletContext;

	Map map = new HashMap();

	@RequestMapping(value = "/frmPOSATV", method = RequestMethod.GET)
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

		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOS");
		JSONArray jArryPosList = (JSONArray) jObj.get("posList");
		for (int i = 0; i < jArryPosList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryPosList.get(i);
			poslist.add(josnObjRet.get("strPosName"));

			map.put(josnObjRet.get("strPosName"), josnObjRet.get("strPosCode"));
		}
		model.put("posList", poslist);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSAvgTicketValueReport_1", "command", new clsWebPOSReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSAvgTicketValueReport", "command", new clsWebPOSReportBean());
		} else {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/frmPOSATV", method = RequestMethod.POST)
	private void funReport(@ModelAttribute("command") clsWebPOSReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		// objGlobal=new clsGlobalFunctions();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		// String posCode=req.getSession().getAttribute("loginPOS").toString();
		List listLive = null;
		List listQFile = null;
		List listModLive = null;
		List listModQFile = null;
		try {
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptAVT1.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();

			String strFromdate = objBean.getFromDate().split("-")[2] + "-" + objBean.getFromDate().split("-")[1] + "-" + objBean.getFromDate().split("-")[0];

			String strToDate = objBean.getToDate().split("-")[2] + "-" + objBean.getToDate().split("-")[1] + "-" + objBean.getToDate().split("-")[0];

			String type = objBean.getStrDocType();

			String strPOSName = objBean.getStrPOSName();
			String posCode = "ALL";
			String strOrderType = objBean.getStrOrderType();

			if (!strPOSName.equalsIgnoreCase("ALL")) {
				if (map.containsKey(strPOSName)) {
					posCode = (String) map.get(strPOSName);
					// System.out.println(posCode.length());
				}

			}

			JSONObject jObjFillter = new JSONObject();
			jObjFillter.put("strFromdate", strFromdate);
			jObjFillter.put("strToDate", strToDate);
			jObjFillter.put("posCode", posCode);
			jObjFillter.put("userCode", userCode);

			jObjFillter.put("strShiftNo", "1");

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funATVReport", jObjFillter);
			List<clsPOSAIPBReportBean> list = new ArrayList<clsPOSAIPBReportBean>();
			JSONArray jarr = (JSONArray) jObj.get("jArr");
			for (int i = 0; i < jarr.size(); i++) {
				JSONObject jObjtemp = (JSONObject) jarr.get(i);

				clsPOSAIPBReportBean objclsPOSAIPBBean = new clsPOSAIPBReportBean();

				objclsPOSAIPBBean.setTblatvreport_strPosCode(jObjtemp.get("strPosCode").toString());
				objclsPOSAIPBBean.setTblatvreport_strPosName(jObjtemp.get("strPosName").toString());
				objclsPOSAIPBBean.setTblatvreport_dteDate(jObjtemp.get("dteDate").toString());

				objclsPOSAIPBBean.setDblDiningAmt(Double.parseDouble(jObjtemp.get("dblDiningAmt").toString()));
				objclsPOSAIPBBean.setTblatvreport_dblDiningNoBill(Double.parseDouble(jObjtemp.get("dblDiningNoBill").toString()));
				objclsPOSAIPBBean.setTblatvreport_dblDiningAvg(Double.parseDouble(jObjtemp.get("dblDiningAvg").toString()));
				objclsPOSAIPBBean.setTblatvreport_dblHDAmt(Double.parseDouble(jObjtemp.get("dblHDAmt").toString()));
				objclsPOSAIPBBean.setTblatvreport_dblHDNoBill(Double.parseDouble(jObjtemp.get("dblHDNoBill").toString()));
				objclsPOSAIPBBean.setTblatvreport_dblHdAvg(Double.parseDouble(jObjtemp.get("dblHdAvg").toString()));
				objclsPOSAIPBBean.setTblatvreport_dblTAAmt(Double.parseDouble(jObjtemp.get("dblTAAmt").toString()));
				objclsPOSAIPBBean.setTblatvreport_dblTANoBill(Double.parseDouble(jObjtemp.get("dblTANoBill").toString()));
				objclsPOSAIPBBean.setTblatvreport_dblTAAvg(Double.parseDouble(jObjtemp.get("dblTAAvg").toString()));
				list.add(objclsPOSAIPBBean);
			}
			JSONArray jarr1 = (JSONArray) jObj.get("jArr1");
			// int a= jarr1.size();

			JSONObject jObjtemp1 = (JSONObject) jarr1.get(0);

			String strClientName = jObjtemp1.get("strClientName").toString();
			String strAddressLine1 = jObjtemp1.get("strAddressLine1").toString();
			String strAddressLine2 = jObjtemp1.get("strAddressLine2").toString();
			String strAddressLine3 = jObjtemp1.get("strAddressLine3").toString();
			String strEmail = jObjtemp1.get("strEmail").toString();
			String strCityName = jObjtemp1.get("strCityName").toString();
			String strState = jObjtemp1.get("strState").toString();
			String strCountry = jObjtemp1.get("strCountry").toString();
			String intTelephoneNo = jObjtemp1.get("intTelephoneNo").toString();

			HashMap hm = new HashMap();
			hm.put("posCode", posCode);
			hm.put("posName", strPOSName);
			hm.put("imagePath", imagePath);
			hm.put("clientName", companyName);
			hm.put("fromDateToDisplay", strFromdate);
			hm.put("toDateToDisplay", strToDate);
			hm.put("shiftNo", "1");
			hm.put("userName", userCode);
			hm.put("list", list);
			hm.put("strClientName", strClientName);
			hm.put("strAddressLine1", strAddressLine1);
			hm.put("strAddressLine2", strAddressLine2);
			hm.put("strAddressLine3", strAddressLine3);
			hm.put("strEmail", strEmail);
			hm.put("strCityName", strCityName);
			hm.put("strState", strState);
			hm.put("strCountry", strCountry);
			hm.put("intTelephoneNo", intTelephoneNo);

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			// jp = JasperFillManager.fillReport(jr, hm, new
			// JREmptyDataSource());

			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(list);
			JasperPrint print = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
			jprintlist.add(print);

			if (jprintlist.size() > 0) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				if (objBean.getStrDocType().equals("PDF")) {
					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=PTVReport" + strFromdate + "_To_" + strToDate + "_" + userCode + ".pdf");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				} else {
					JRExporter exporter = new JRXlsExporter();
					resp.setContentType("application/xlsx");
					exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=PTVReport" + strFromdate + "_To_" + strToDate + "_" + userCode + ".xls");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				}
			} else {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.getWriter().append("No Record Found");

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
