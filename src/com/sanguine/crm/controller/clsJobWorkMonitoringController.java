package com.sanguine.crm.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import antlr.collections.List;

import com.mysql.jdbc.Connection;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.crm.model.clsDeliveryNoteHdModel;
import com.sanguine.crm.model.clsSubContractorMasterModel;
import com.sanguine.crm.service.clsDeliveryNoteService;
import com.sanguine.crm.service.clsSubContractorMasterService;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsSupplierMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsSupplierMasterService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsJobWorkMonitoringController {
	@Autowired
	clsDeliveryNoteService objDeliveryNoteService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSupplierMasterService objSupplierMasterService;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@RequestMapping(value = "/frmJobWorkMonitoringReport", method = RequestMethod.GET)
	public ModelAndView funOpenDueDateMonitoringReport(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmJobWorkMonitoringReport_1", "command", new clsReportBean());
		} else {

			return new ModelAndView("frmJobWorkMonitoringReport", "command", new clsReportBean());
		}

	}

	@RequestMapping(value = "/rptJobWorkMonitoringReport", method = RequestMethod.GET)
	private void funDueDateMonitoringReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String dNCode = objBean.getStrDocCode();
		if (dNCode == null || dNCode.equals("")) {
			dNCode = "ALL";
		}
		String SCCode = objBean.getStrSCCode();
		if (SCCode == null || SCCode.equals("")) {
			SCCode = "ALL";
		}
		String type = objBean.getStrExportType();
		String fromDate = objBean.getDtFromDate();
		String toDate = objBean.getDtToDate();

		funCallSCrapGenratedReport(dNCode, SCCode, type, fromDate, toDate, resp, req);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallSCrapGenratedReport(String dNCode, String SCCode, String type, String fromDate, String toDate, HttpServletResponse resp, HttpServletRequest req) {
		try {

			String SCName = "";
			objGlobal = new clsGlobalFunctions();
			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}

			if (!SCCode.equals("ALL")) {
				clsSupplierMasterModel objPartyModel = objSupplierMasterService.funGetObject(SCCode, clientCode);
				SCName = objPartyModel.getStrPName();
			} else {
				SCName = "ALL";
			}

			String sqlDtl = " select k.strProdName as RawProd,e.dblQty,e.dblQty*e.dblWeight,b.strRef,b.dteJADate,j.strPName," + " j.strSAdd1,j.strSAdd2,j.strSCity,j.strSState,j.strSCountry,c.strNatureOfProcessing,f.dteSRDate," + " l.strProdName as FiniProd,g.dblAcceptQty,(g.dblQtyRbl-g.dblAcceptQty) as pendingQty,d.dteExpDate "
					+ " from tbljoborderallocationhd b , tbljoborderallocationdtl c,tbldeliverynotehd d,tbldeliverynotedtl e, " + " tblscreturnhd f,tblscreturndtl g, tblpartymaster j,tblproductmaster k,tblproductmaster l " + " where b.strJACode = c.strJACode " + " and b.strJACode=d.strJACode " + " and d.strDNCode = e.strDNCode " + " and k.strProdCode=e.strProdCode "
					+ " and l.strProdCode = g.strProdCode  " + " and d.dteDNDate " + " between '" + fromDate + "' and '" + toDate + "' ";
			if (!SCCode.equals("ALL")) {
				sqlDtl = sqlDtl + " and d.strSCCode ='" + SCCode + "' ";
			}
			if (!dNCode.equals("ALL")) {
				sqlDtl = sqlDtl + " and d.strDNCode = '" + dNCode + "' ";
			}

			sqlDtl = sqlDtl + " group by e.strProdCode, d.strDNCode;  ";

			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptJobWorkMonitoring.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlDtl);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsWorkRegister");
			subDataset.setQuery(newQuery);

			JasperReport jr = JasperCompileManager.compileReport(jd);

			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);
			hm.put("strAddr1", objSetup.getStrAdd1());
			hm.put("strAddr2", objSetup.getStrAdd2());
			hm.put("strCity", objSetup.getStrCity());
			hm.put("strState", objSetup.getStrState());
			hm.put("strCountry", objSetup.getStrCountry());
			hm.put("strPin", objSetup.getStrPin());
			hm.put("DNCode", dNCode);
			hm.put("strPName", SCName);
			hm.put("fromDate", fromDate);
			hm.put("toDate", toDate);
			hm.put("ptype", "");

			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setContentLength(bytes.length);
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptJobWorkMonitoringReport." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

}
