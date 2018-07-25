package com.sanguine.crm.controller;

import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.crm.bean.clsAgeingReportBean;
import com.sanguine.crm.model.clsPartyMasterModel;
import com.sanguine.crm.service.clsPartyMasterService;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;

@Controller
public class clsAgeingReportController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsPartyMasterService objPartyService;

	// Open ExciseChallan
	@RequestMapping(value = "/frmAgeingReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		ArrayList<String> permissionType = new ArrayList<String>();
		permissionType.add("PERMITTED");
		permissionType.add("GENERAL");
		permissionType.add("UCC");
		permissionType.add("ALL");

		model.put("permissionType", permissionType);

		ArrayList<String> dnType = new ArrayList<String>();
		dnType.add("JOB ORDER");
		dnType.add("GENERAL");
		dnType.add("ALL");

		model.put("dnType", dnType);

		ArrayList<String> orderBy = new ArrayList<String>();
		orderBy.add("PARTY NAME");
		orderBy.add("DATE");

		model.put("orderBy", orderBy);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmAgeingReport_1", "command", new clsAgeingReportBean());
		} else {
			return new ModelAndView("frmAgeingReport", "command", new clsAgeingReportBean());
		}
	}

	@RequestMapping(value = "/rptAgeingReport", method = RequestMethod.GET)
	private void funReportAgeingReport(@ModelAttribute("command") clsAgeingReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String SCCode = objBean.getStrSCCode();
		String fromDate = objBean.getDteFromDate();
		String[] frmDate = fromDate.split("-");
		fromDate = frmDate[2] + "-" + frmDate[1] + "-" + frmDate[0];
		String toDate = objBean.getDteToDate();
		String[] toDte = toDate.split("-");
		toDate = toDte[2] + "-" + toDte[1] + "-" + toDte[0];
		String dNCode = objBean.getStrDNCode();
		String dNType = objBean.getStrDNType();
		String orderBy = objBean.getStrOrderBy();
		String permissionType = objBean.getStrPermissionType();
		String exporttype = objBean.getStrExportType();

		funCallReportAgeingReport(SCCode, dNCode, fromDate, toDate, dNType, orderBy, permissionType, exporttype, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallReportAgeingReport(String SCCode, String dNCode, String fromDate, String toDate, String dNType, String orderBy, String permissionType, String exporttype, HttpServletResponse resp, HttpServletRequest req) {
		try {

			objGlobal = new clsGlobalFunctions();
			String SCName = "";

			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptAgeing.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			clsPartyMasterModel objpartymaster = new clsPartyMasterModel();
			if (!SCCode.isEmpty()) {
				objpartymaster = objPartyService.funGetPartyDtl(SCCode, clientCode);
				SCName = objpartymaster.getStrPName();
			} else {
				SCName = "ALL";
			}

			String sqlDtl = " SELECT CONCAT(c.strPName,' ',c.strSAdd1,' ',c.strSAdd2,' ',c.strSCity,' ',c.strSPin,' ',c.strSState,' ',c.strSCountry) AS subContractingDtl " + " ,b.strJACode,b.dteJADate,d.dteExpDate,e.dteSRDate,e.strSRNo, " + " DATEDIFF(e.dteSRDate,b.dteJADate) AS dateDif " + " FROM tbljoborderallocationhd b, tblpartymaster c,tbldeliverynotehd d,tblscreturnhd e "
					+ " WHERE b.strSCCode=c.strPCode AND b.strJACode=d.strJACode AND d.strDNCode=e.strSCDNCode and " + " Date(b.dteJADate) between '" + fromDate + "' and '" + toDate + "' " + " and b.strClientCode=c.strClientCode " + " and b.strClientCode=d.strClientCode " + " and b.strClientCode=e.strClientCode " + " and b.strClientCode='" + clientCode + "' ";

			if (dNCode.isEmpty()) {
				dNCode = "ALL";

			} else {
				sqlDtl = sqlDtl + " AND e.strSCDNCode='" + dNCode + "' ";
			}

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sqlDtl);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsAgeing");
			subDataset.setQuery(subQuery);
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
			hm.put("strclientCode", clientCode);
			hm.put("strFromDate", fromDate);
			hm.put("strToDate", toDate);
			hm.put("SCName", SCName);
			hm.put("strType", dNType);
			hm.put("strDCCode", dNCode);

			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (exporttype.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setContentLength(bytes.length);
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (exporttype.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptBillPassingReport." + exporttype.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
