package com.sanguine.controller;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.sanguine.bean.clsProductWiseGRNReportBean;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;

@Controller
public class clsSupplierWiseProductGRNReportController {

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@RequestMapping(value = "/frmSupplierWiseProductGRNReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";

		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSupplierWiseProductGRNReport_1", "command", new clsProductWiseGRNReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSupplierWiseProductGRNReport", "command", new clsProductWiseGRNReportBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/rptSupplierWiseProducttGRNReport", method = RequestMethod.GET)
	private void funCallProductWiseReport(@ModelAttribute("command") clsProductWiseGRNReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		Connection con = objGlobalFunctions.funGetConnection(req);
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String tempPCode[] = objBean.getStrPCode().split(",");
		String tempsubGroupCode[] = objBean.getStrCatCode().split(",");
		String fromDate = objBean.getDteFromDate();
		String toDate = objBean.getDteToDate();
		String strSGCode = "";
		String pCode = "";
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		if (objSetup == null) {
			objSetup = new clsPropertySetupModel();
		}

		String fd = fromDate.split("-")[0];
		String fm = fromDate.split("-")[1];
		String fy = fromDate.split("-")[2];

		String td = toDate.split("-")[0];
		String tm = toDate.split("-")[1];
		String ty = toDate.split("-")[2];

		String dteFromDate = fy + "-" + fm + "-" + fd;
		String dteToDate = ty + "-" + tm + "-" + td;

		String reportName = servletContext.getRealPath("/WEB-INF/reports/rptSupplierWiseProductGRNReport.jrxml");
		String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

		ArrayList fieldList = new ArrayList();

		for (int i = 0; i < tempPCode.length; i++) {
			if (pCode.length() > 0) {
				pCode = pCode + " or a.strSuppCode='" + tempPCode[i] + "' ";
			} else {
				pCode = "a.strSuppCode='" + tempPCode[i] + "' ";

			}
		}

		for (int i = 0; i < tempsubGroupCode.length; i++) {
			if (strSGCode.length() > 0) {
				strSGCode = strSGCode + " or c.strSGCode='" + tempsubGroupCode[i] + "' ";
			} else {
				strSGCode = "c.strSGCode='" + tempsubGroupCode[i] + "' ";

			}
		}

		String sqlQuery = "select c.strSGName,e.strPName,d.strProdName,sum(b.dblQty) ,sum(b.dblTotalPrice),d.strUOM ,f.dblLastCost  " + " from tblgrnhd a,tblgrndtl b ,tblsubgroupmaster c,tblproductmaster d,tblpartymaster e ,tblprodsuppmaster f " + " where a.strGRNCode=b.strGRNCode and b.strProdCode=d.strProdCode "
				+ " and d.strSGCode=c.strSGCode and a.strSuppCode =e.strPCode and a.strSuppCode=f.strSuppCode and b.strProdCode=f.strProdCode " + " and a.dtGRNDate between '" + dteFromDate + "' and '" + dteToDate + "'  " + " and (" + pCode + ")  " + " and (" + strSGCode + " ) ";

		sqlQuery = sqlQuery + " group by a.strSuppCode, d.strSGCode, b.strProdCode order by a.strSuppCode, d.strSGCode, b.strProdCode ";

		List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");

		for (int j = 0; j < listProdDtl.size(); j++) {
			clsProductWiseGRNReportBean objProdBean = new clsProductWiseGRNReportBean();
			Object[] prodArr = (Object[]) listProdDtl.get(j);

			objProdBean.setStrSubGroupName(prodArr[0].toString());
			objProdBean.setStrSuppName(prodArr[1].toString());
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
