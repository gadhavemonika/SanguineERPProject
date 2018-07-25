package com.sanguine.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.sanguine.bean.clsProductWiseGRNReportBean;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsSubGroupMasterService;

@Controller
public class clsProductWiseGRNReportController {

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsSubGroupMasterService objSubGrpMasterService;// service to add
															// combobox on Auto
															// PI Help Fprm:RP

	@RequestMapping(value = "/frmProductWiseGRNReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";

		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmProductWiseGRNReport_1", "command", new clsProductWiseGRNReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmProductWiseGRNReport", "command", new clsProductWiseGRNReportBean());
		} else {
			return null;
		}

	}

	/**
	 * Load Subgroup Data
	 * 
	 * @param code
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/loadAllSubGroup", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> funSubGroupFroGroup(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		Map<String, String> mpSubgroup = objSubGrpMasterService.funGetSubgroupsCombobox(clientCode);
		return mpSubgroup;
	}

	@RequestMapping(value = "/rptProductWiseGRNReport", method = RequestMethod.GET)
	private void funCallProductWiseReport(@ModelAttribute("command") clsProductWiseGRNReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		Connection con = objGlobalFunctions.funGetConnection(req);
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String tempLoc[] = objBean.getStrLocCode().split(",");
		String tempsubGroupCode[] = objBean.getStrCatCode().split(",");
		String fromDate = objBean.getDteFromDate();
		String toDate = objBean.getDteToDate();
		String locCode = "";
		String strSGCode = "";
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		if (objSetup == null) {
			objSetup = new clsPropertySetupModel();
		}

		for (int i = 0; i < tempLoc.length; i++) {
			if (locCode.length() > 0) {
				locCode = locCode + " or a.strLocCode='" + tempLoc[i] + "' ";
			} else {
				locCode = "a.strLocCode='" + tempLoc[i] + "' ";

			}
		}

		for (int i = 0; i < tempsubGroupCode.length; i++) {
			if (strSGCode.length() > 0) {
				strSGCode = strSGCode + " or d.strSGCode='" + tempsubGroupCode[i] + "' ";
			} else {
				strSGCode = "d.strSGCode='" + tempsubGroupCode[i] + "' ";

			}
		}
		String fd = fromDate.split("-")[0];
		String fm = fromDate.split("-")[1];
		String fy = fromDate.split("-")[2];

		String td = toDate.split("-")[0];
		String tm = toDate.split("-")[1];
		String ty = toDate.split("-")[2];

		String dteFromDate = fy + "-" + fm + "-" + fd;
		String dteToDate = ty + "-" + tm + "-" + td;

		String reportName = servletContext.getRealPath("/WEB-INF/reports/rptProductWiseGRNReport.jrxml");
		String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

		ArrayList fieldList = new ArrayList();

		String sqlQuery = "select c.strSGName,e.strLocName,d.strProdName,sum(b.dblQty) ,sum(b.dblTotalPrice),d.strUOM,d.dblCostRM from tblgrnhd a,tblgrndtl b ,tblsubgroupmaster c,tblproductmaster d,tbllocationmaster e " + " where a.strGRNCode=b.strGRNCode and b.strProdCode=d.strProdCode and d.strSGCode=c.strSGCode and a.strLocCode=e.strLocCode " + " and a.dtGRNDate between '" + dteFromDate
				+ "' and '" + dteToDate + "'  ";

		if (!locCode.equals("All")) {
			sqlQuery = sqlQuery + " and (" + locCode + ")  ";
		}

		if (!strSGCode.equals("All")) {
			sqlQuery = sqlQuery + " and (" + strSGCode + " ) ";
		}

		sqlQuery = sqlQuery + "group by a.strLocCode, d.strSGCode, b.strProdCode order by a.strLocCode, d.strSGCode, b.strProdCode ";

		List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");

		for (int j = 0; j < listProdDtl.size(); j++) {
			clsProductWiseGRNReportBean objProdBean = new clsProductWiseGRNReportBean();
			Object[] prodArr = (Object[]) listProdDtl.get(j);

			objProdBean.setStrSubGroupName(prodArr[0].toString());
			objProdBean.setStrLocName(prodArr[1].toString());
			objProdBean.setStrProductName(prodArr[2].toString());
			objProdBean.setDblQty(Double.parseDouble(prodArr[3].toString()));
			objProdBean.setDblAmount(Double.parseDouble(prodArr[4].toString()));
			objProdBean.setStrUOM(prodArr[5].toString());
			objProdBean.setDblUnitPrice(Double.parseDouble(prodArr[6].toString()));
			fieldList.add(objProdBean);

		}

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
		hm.put("dteFromDate", objBean.getDteFromDate());
		hm.put("dteToDate", objBean.getDteToDate());
		// hm.put("fieldList",fieldList);

		try {

			// JasperDesign jd = JRXmlLoader.load(reportName);
			// JasperReport jr = JasperCompileManager.compileReport(jd);
			// JasperPrint jp = JasperFillManager.fillReport(jr, hm, new
			// JREmptyDataSource());
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			// jprintlist.add(jp);

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
			JasperPrint print = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
			jprintlist.add(print);
			ServletOutputStream servletOutputStream = resp.getOutputStream();
			if (jprintlist.size() > 0) {
				if (objBean.getStrDocType().equals("PDF")) {
					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=rptProductWiseGRNReport_" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".pdf");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();

				} else {
					JRExporter exporter = new JRXlsExporter();
					resp.setContentType("application/xlsx");
					exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=rptProductWiseGRNReport_" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".xls");
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
