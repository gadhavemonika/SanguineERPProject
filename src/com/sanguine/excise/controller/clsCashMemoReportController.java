package com.sanguine.excise.controller;

import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsCashMemoReportController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private ServletContext servletContext;

	private clsGlobalFunctions objGlobal;

	@RequestMapping(value = "/frmExciseCashMemoReport", method = RequestMethod.GET)
	public ModelAndView funOpenPurchaseAnylasisReport(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseCashMemoReport_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmExciseCashMemoReport", "command", new clsReportBean());
		}

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptExciseCashMemoReport", method = RequestMethod.POST)
	private void funCallPurchaseAnylasisReportReportt(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String strFromDate = objBean.getDtFromDate();
		String strToDate = objBean.getDtToDate();
		Connection con = null;
		JasperPrint jp = null;
		String reportName = "";

		try {

			// Get New Connection From
			objGlobal = new clsGlobalFunctions();
			con = objGlobal.funGetConnection(req);

			String strfindFirstNo = "SELECT MIN(intBillNo) FROM tblexcisesaledata " + " WHERE DATE(dteBillDate) BETWEEN '" + strFromDate + "' AND '" + strToDate + "' " + " AND strClientCode='" + clientCode + "' ";

			List ObjList = objGlobalFunctionsService.funGetDataList(strfindFirstNo, "sql");
			BigInteger firstNo = new BigInteger("1");
			if (ObjList.size() > 0) {
				if (ObjList.get(0) != null) {
					firstNo = (BigInteger) ObjList.get(0);
				}
			}
			Long startBillNo = firstNo.longValue();

			if (startBillNo % 2 == 1) {
				reportName = servletContext.getRealPath("/WEB-INF/exciseReport/rptCashMemoMainO_E.jasper");

			} else {
				reportName = servletContext.getRealPath("/WEB-INF/exciseReport/rptCashMemoMainE_O.jasper");
			}

			// JasperDesign jd = JRXmlLoader.load(reportName);
			// JasperReport jr = JasperCompileManager.compileReport(jd);
			HashMap<String, Object> hm = new HashMap<String, Object>();

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date dteFromDate = formatter.parse(strFromDate);
			Date dteToDate = formatter.parse(strToDate);

			hm.put("strClientCode", clientCode);
			hm.put("dteFromDate", dteFromDate);
			hm.put("dteToDate", dteToDate);
			jp = JasperFillManager.fillReport(reportName, hm, con);
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			if (jp != null) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				jprintlist.add(jp);
				resp.setContentType("application/pdf");

				JRExporter exporter = new JRPdfExporter();
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename= rptCashMemo" + dteFromDate + " To " + dteToDate + ".pdf");
				exporter.exportReport();

				servletOutputStream.flush();
				servletOutputStream.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

}
