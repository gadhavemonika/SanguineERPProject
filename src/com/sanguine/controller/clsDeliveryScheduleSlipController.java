package com.sanguine.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
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
import com.sanguine.model.clsDeliveryScheduleModuledtl;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsDeliveryScheduleSlipController {

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@RequestMapping(value = "/frmDeliveryScheduleSlip", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmDeliveryScheduleSlip_1", "command", new clsReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmDeliveryScheduleSlip", "command", new clsReportBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/rptDeliveryScheduleSlip", method = RequestMethod.GET)
	private void funReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		funCallReport(objBean, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallReport(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		String prodType = objBean.getStrProdType();
		String type = objBean.getStrDocType();
		objGlobal = new clsGlobalFunctions();
		Connection con = objGlobal.funGetConnection(req);
		try {

			String dsCode = "", locationCode = "", locationName = "";
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String strLocCode = req.getSession().getAttribute("locationCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);

			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptDeliveryScheduleSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlDS = " select a.strDSCode, b.strLocCode, b.strLocName " + " from tbldeliveryschedulehd a, tbllocationmaster b " + " where a.strLocCode=b.strLocCode and a.strClientCode=b.strClientCode " + " and a.strClientCode='" + clientCode + "' and  a.strDSCode='" + objBean.getStrDSCode() + "' ";

			List listds = objGlobalFunctionsService.funGetList(sqlDS, "sql");

			if (listds.size() > 0) {

				for (int i = 0; i < listds.size(); i++) {
					Object[] obProdDtl = (Object[]) listds.get(i);

					dsCode = obProdDtl[0].toString();
					locationCode = obProdDtl[1].toString();
					locationName = obProdDtl[2].toString();

				}
			}

			String sql = " select b.strProdCode, c.strProdName, b.strUOM, b.dblQty, b.dblUnitPrice, b.dblTotalPrice " + " from tbldeliveryschedulehd a, tbldeliveryscheduledtl b, tblproductmaster c " + " where a.strDSCode=b.strDSCode and b.strProdCode=c.strProdCode and a.strDSCode='" + objBean.getStrDSCode() + "' "
					+ " and a.strClientCode=b.strClientCode and b.strClientCode=c.strClientCode and a.strClientCode='" + clientCode + "' ";

			// getting multi copy of small data in table in Detail thats why we
			// add in subDataset
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sql);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsProddtl");
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
			hm.put("dsCode", dsCode);
			hm.put("locationCode", locationCode);
			hm.put("locationName", locationName);
			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setContentLength(bytes.length);
				servletOutputStream.write(bytes, 0, bytes.length);
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptDeliveryScheduleSlip." + type.trim());
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptDeliveryScheduleSlip." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
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

	/**
	 * Open DS Slip After Saving
	 * 
	 * @param resp
	 * @param req
	 */
	@RequestMapping(value = "/openRptDSSlip", method = RequestMethod.GET)
	private void funCallReportOnSubmit(HttpServletResponse resp, HttpServletRequest req) {
		String DSCode = req.getParameter("rptDSCode").toString();
		// req.getSession().removeAttribute("rptGRNCode");
		String type = "pdf";
		clsReportBean objBean = new clsReportBean();
		objBean.setStrDocType(type);
		objBean.setStrDSCode(DSCode);
		funCallReport(objBean, resp, req);

	}

}
