package com.sanguine.crm.controller;

import java.util.HashMap;
import java.util.List;
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
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsDeliveryChallanInvoiceController {

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private ServletContext servletContext;

	@RequestMapping(value = "/frmDeliveryChallanSlipInvoice", method = RequestMethod.GET)
	public ModelAndView funDeliveryChallanSlipInvoice(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmDeliveryChallanSlipInvoice_1", "command", new clsReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmDeliveryChallanSlipInvoice", "command", new clsReportBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/rptDeliveryNoteChallanReport", method = RequestMethod.GET)
	private void funDeliveryNoteReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String dNCode = objBean.getStrDocCode();
		String strReportType = objBean.getStrReportType();
		String fromDate = objBean.getDtFromDate();
		String toDate = objBean.getDtToDate();
		String type = objBean.getStrExportType();
		if (strReportType.equals("Challan")) {
			funCallDeliveryNoteReport(dNCode, type, fromDate, toDate, strReportType, resp, req);
		} else {
			funCallDeliveryNoteInnvoiceReport(dNCode, type, fromDate, toDate, strReportType, resp, req);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallDeliveryNoteReport(String dNCode, String type, String fromDate, String toDate, String strReportType, HttpServletResponse resp, HttpServletRequest req) {

		try {
			String strVehNo = "";
			String dteDNDate = "";
			String strPName = "";
			String strSAdd1 = "";
			String strSAdd2 = "";
			String strSCity = "";
			String strSState = "";
			String strSPin = "";
			String strSCountry = "";

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

			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptDeliveryNoteChallanReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHd = " select a.dteDNDate,a.strVehNo,b.strPName,b.strSAdd1,b.strSAdd2,b.strSCity,b.strSState,b.strSPin,b.strSCountry  " + " from tbldeliverynotehd a, tblpartymaster b where a.strDNCode='" + dNCode + "' and a.strSCCode=b.strPCode  " + " and a.strClientCode='" + clientCode + "' and a.strClientCode=b.strClientCode ";

			List list = objGlobalFunctionsService.funGetList(sqlHd, "sql");
			if (!list.isEmpty()) {
				Object[] arrObj = (Object[]) list.get(0);
				dteDNDate = arrObj[0].toString();
				strVehNo = arrObj[1].toString();
				strPName = arrObj[2].toString();
				strSAdd1 = arrObj[3].toString();
				strSAdd2 = arrObj[4].toString();
				strSCity = arrObj[5].toString();
				strSState = arrObj[6].toString();
				strSPin = arrObj[7].toString();
				strSCountry = arrObj[8].toString();

			}

			// String
			// sqlDtl="select b.intId,a.strProdCode,b.strPartNo,b.strProdName,a.dblQty,b.strUOM "
			// +
			// "	from tbldeliverynotedtl a ,tblproductmaster b ,tbldeliverynotehd c "
			// +
			// "	where c.strSCCode='"+SCCode+"' and a.strProdCode=b.strProdCode and a.strDNCode=c.strDNCode "
			// +
			// "	and a.strClientCode='"+clientCode+"' and a.strClientCode = b.strClientCode "
			// + "   and c.dteDNDate between '"+fromDate+"' and '"+toDate+"'";

			String sqlDtl = " select b.strProdCode as ProdCode,c.strPartNo as PartNo,c.strProdName as ProductName,b.dblQty as Qty, " + " b.dblQty*b.dblWeight as Weight,(b.dblQty*b.dblRate) as value , 0.00 as Duty " + " from tbldeliverynotehd a,tbldeliverynotedtl b,tblproductmaster c " + " where a.strDNCode=b.strDNCode  " + " and b.strProdCode=c.strProdCode " + " and a.strDNCode='" + dNCode
					+ "'  " + " and a.strClientCode =b.strClientCode " + " and b.strClientCode = c.strClientCode  " + " and a.strClientCode='" + clientCode + "' ";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sqlDtl);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsDeliveryNoteChallan");
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
			hm.put("DNDate", dteDNDate);
			hm.put("strPartyName", strPName);
			hm.put("Transportno", strVehNo);
			hm.put("strSAdd1", strSAdd1);
			hm.put("strSAdd2", strSAdd2);
			hm.put("strSCity", strSCity);
			hm.put("strSCountry", strSCountry);
			hm.put("strSState", strSState);
			hm.put("strSPin", strSPin);

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
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptDeliveryNoteChallanReport." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallDeliveryNoteInnvoiceReport(String dNCode, String type, String fromDate, String toDate, String strReportType, HttpServletResponse resp, HttpServletRequest req) {

		try {
			String strVehNo = "";
			String dteDNDate = "";
			String strPName = "";
			String strSAdd1 = "";
			String strSAdd2 = "";
			String strSCity = "";
			String strSState = "";
			String strSPin = "";
			String strSCountry = "";
			String jw = "";
			String modes = "";

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

			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptDeliveryNoteChallanInnvoiceReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHd = "  select a.dteDNDate,a.strVehNo,b.strPName,b.strSAdd1,b.strSAdd2,b.strSCity,b.strSState,b.strSPin,b.strSCountry,  " + " a.strJACode as JW,c.strDispatchMode as modeofTransp  " + " from tbldeliverynotehd a, tblpartymaster b, tbljoborderallocationhd c " + " where a.strDNCode='" + dNCode + "' and a.strSCCode=b.strPCode  "
					+ " and a.strJACode=c.strJACode  and a.strClientCode='" + clientCode + "' " + " and a.strClientCode=b.strClientCode  ";

			List list = objGlobalFunctionsService.funGetList(sqlHd, "sql");
			if (!list.isEmpty()) {
				Object[] arrObj = (Object[]) list.get(0);
				dteDNDate = arrObj[0].toString();
				strVehNo = arrObj[1].toString();
				strPName = arrObj[2].toString();
				strSAdd1 = arrObj[3].toString();
				strSAdd2 = arrObj[4].toString();
				strSCity = arrObj[5].toString();
				strSState = arrObj[6].toString();
				strSPin = arrObj[7].toString();
				strSCountry = arrObj[8].toString();
				jw = arrObj[9].toString();
				modes = arrObj[10].toString();

			}

			// String sql=" SELECT 1 from dual";

			JasperDesign jd = JRXmlLoader.load(reportName);
			// JRDesignQuery newHDQuery= new JRDesignQuery();
			// newHDQuery.setText(sql);
			// jd.setQuery(newHDQuery);

			String sqlHdtable = " select c.strProdName,c.strPartNo as PartNo,b.dblQty as Qty " + " from tbldeliverynotehd a,tbldeliverynotedtl b,tblproductmaster c," + " tbljoborderallocationdtl d where a.strDNCode=b.strDNCode " + " and b.strProdCode=c.strProdCode and a.strDNCode='" + dNCode + "' " + " and a.strJACode=d.strJACode and a.strClientCode =b.strClientCode "
					+ " and b.strClientCode = c.strClientCode and a.strClientCode='" + clientCode + "' ";

			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlHdtable);
			jd.setQuery(newQuery);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsDNHdTable");
			subDataset.setQuery(newQuery);
			String sqlDtl = " select c.strPartNo as PartNo,c.strProdName as ProductName," + " d.strNatureOfProcessing as Processing,b.dblQty as Qty, b.dblQty*b.dblWeight as Weight," + " (b.dblQty*b.dblRate) as Amount " + " from tbldeliverynotehd a,tbldeliverynotedtl b,tblproductmaster c, tbljoborderallocationdtl d "
					+ " where a.strDNCode=b.strDNCode and b.strProdCode=c.strProdCode and a.strDNCode='" + dNCode + "' " + " and a.strJACode=d.strJACode and a.strClientCode =b.strClientCode " + " and b.strClientCode = c.strClientCode  and a.strClientCode='" + clientCode + "' ";

			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sqlDtl);
			JRDesignDataset mainTableDataset = (JRDesignDataset) datasetMap.get("dsDNInnvoiceDtl");
			mainTableDataset.setQuery(subQuery);
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
			hm.put("DNDate", dteDNDate);
			hm.put("strPartyName", strPName);
			hm.put("Transportno", strVehNo);
			hm.put("strSAdd1", strSAdd1);
			hm.put("strSAdd2", strSAdd2);
			hm.put("strSCity", strSCity);
			hm.put("strSCountry", strSCountry);
			hm.put("strSState", strSState);
			hm.put("strSPin", strSPin);
			hm.put("strmodes", modes);
			hm.put("jw", jw);

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
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptDeliveryNoteChallanReport." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

}
