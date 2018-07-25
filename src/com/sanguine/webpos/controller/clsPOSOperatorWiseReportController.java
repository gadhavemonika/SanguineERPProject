package com.sanguine.webpos.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import com.sanguine.webpos.bean.clsOperatorWiseReportBean;
import com.sanguine.webpos.bean.clsRevenueHeadWiseSalesReportBean;
import com.sanguine.webpos.bean.clsWebPOSReportBean;
import com.sanguine.webpos.util.clsOperatorWiseComparator;
import com.sanguine.webpos.util.clsRevenueHeadWiseComparator;

@Controller
public class clsPOSOperatorWiseReportController {

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private ServletContext servletContext;

	Map posMap = new HashMap();

	@RequestMapping(value = "/frmPOSOperatorWiseReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		posMap.put("All", "All");
		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOS");
		JSONArray jArryPosList = (JSONArray) jObj.get("posList");
		for (int i = 0; i < jArryPosList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryPosList.get(i);

			posMap.put(josnObjRet.get("strPosCode"), josnObjRet.get("strPosName"));
		}
		model.put("posList", posMap);

		Map userMap = new HashMap();
		userMap.put("All", "All");
		jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetAllUserName");
		jArryPosList = (JSONArray) jObj.get("userList");
		for (int i = 0; i < jArryPosList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryPosList.get(i);

			userMap.put(josnObjRet.get("strUserCode"), josnObjRet.get("strUserName"));
		}
		model.put("userList", userMap);

		Map SettlementMap = new HashMap();
		SettlementMap.put("All", "All");
		jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetAllSettlement");
		jArryPosList = (JSONArray) jObj.get("settlementList");
		for (int i = 0; i < jArryPosList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryPosList.get(i);

			SettlementMap.put(josnObjRet.get("strSettelmentCode"), josnObjRet.get("strSettlementName"));
		}
		model.put("settlementList", SettlementMap);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSOperatorWiseReport_1", "command", new clsWebPOSReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSOperatorWiseReport", "command", new clsWebPOSReportBean());
		} else {
			return null;
		}

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptPOSOperatorWiseReport", method = RequestMethod.POST)
	private void funReport(@ModelAttribute("command") clsWebPOSReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String strDocType = objBean.getStrDocType();
		String userName = objBean.getStrSGName();
		String settlementName = objBean.getStrReportType();

		try {

			String reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptOperatorWiseSettlementReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();

			String strFromdate = objBean.getFromDate().split("-")[2] + "-" + objBean.getFromDate().split("-")[1] + "-" + objBean.getFromDate().split("-")[0];

			String strToDate = objBean.getToDate().split("-")[2] + "-" + objBean.getToDate().split("-")[1] + "-" + objBean.getToDate().split("-")[0];

			String strRevenueHead = objBean.getStrRevenueHead();

			String strPOSName = objBean.getStrPOSName();

			JSONObject jObjFillter = new JSONObject();
			jObjFillter.put("strFromdate", strFromdate);
			jObjFillter.put("strToDate", strToDate);
			jObjFillter.put("posCode", strPOSName);
			jObjFillter.put("strShiftNo", "1");

			jObjFillter.put("settlementType", settlementName);
			jObjFillter.put("userCode", userName);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funOperatorWiseReport", jObjFillter);
			List<clsOperatorWiseReportBean> list = new ArrayList<clsOperatorWiseReportBean>();
			JSONArray jarr = (JSONArray) jObj.get("jArr");

			for (int i = 0; i < jarr.size(); i++) {
				JSONObject jObjtemp = (JSONObject) jarr.get(i);

				clsOperatorWiseReportBean objClsGroupWaiseSalesBean = new clsOperatorWiseReportBean();
				objClsGroupWaiseSalesBean.setStrUserCode(jObjtemp.get("strUserCode").toString());
				objClsGroupWaiseSalesBean.setStrUserName(jObjtemp.get("strUserName").toString());
				objClsGroupWaiseSalesBean.setStrPOSName(jObjtemp.get("strPOSName").toString());
				objClsGroupWaiseSalesBean.setSettleAmt(Double.parseDouble(jObjtemp.get("dblAmount").toString()));
				objClsGroupWaiseSalesBean.setDiscountAmt(Double.parseDouble(jObjtemp.get("discountAmt").toString()));
				objClsGroupWaiseSalesBean.setStrSettlementDesc(jObjtemp.get("strSettlementDesc").toString());
				objClsGroupWaiseSalesBean.setStrPOSCode(jObjtemp.get("strPOSCode").toString());

				objClsGroupWaiseSalesBean.setDiscountAmt(0);

				list.add(objClsGroupWaiseSalesBean);
			}
			Comparator<clsOperatorWiseReportBean> groupComparator = new Comparator<clsOperatorWiseReportBean>() {

				@Override
				public int compare(clsOperatorWiseReportBean o1, clsOperatorWiseReportBean o2) {
					return o1.getStrUserCode().compareToIgnoreCase(o2.getStrUserCode());
				}
			};

			Collections.sort(list, new clsOperatorWiseComparator(groupComparator));

			HashMap hm = new HashMap();
			hm.put("posCode", strPOSName);
			hm.put("posName", posMap.get(strPOSName).toString());
			hm.put("imagePath", imagePath);
			hm.put("clientName", companyName);
			hm.put("fromDateToDisplay", strFromdate);
			hm.put("toDateToDisplay", strToDate);
			hm.put("shiftNo", "1");
			hm.put("userName", userCode);

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);

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
					resp.setHeader("Content-Disposition", "inline;filename=OperatorWiseSalesReport_" + strFromdate + "_To_" + strToDate + "_" + userCode + ".pdf");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				} else {
					JRExporter exporter = new JRXlsExporter();
					resp.setContentType("application/xlsx");
					exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=OperatorWiseSalesReport_" + strFromdate + "_To_" + strToDate + "_" + userCode + ".xls");
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

		System.out.println("Hi");

	}

}
