package com.sanguine.webbooks.apgl.controller;

import java.sql.SQLException;
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
import com.sanguine.base.service.intfBaseService;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webbooks.bean.clsCreditorOutStandingReportBean;

@Controller
public class clsDebtorOutStandingList {

	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsCurrencyMasterService objCurrencyMasterService;

	@Autowired
	intfBaseService objBaseService;
	

	@RequestMapping(value = "/frmDebtorOutStandingList", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		Map<String, String> hmCurrency = objCurrencyMasterService.funCurrencyListToDisplay(clientCode);
		if (hmCurrency.isEmpty()) {
			hmCurrency.put("", "");
		}
		model.put("currencyList", hmCurrency);
		
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmDebtorOutStandingList_1", "command", new clsCreditorOutStandingReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmDebtorOutStandingList", "command", new clsCreditorOutStandingReportBean());
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/rptDebtorOutStandingList", method = RequestMethod.GET)
	private void funReport(@ModelAttribute("command") clsCreditorOutStandingReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		Connection con = objGlobal.funGetConnection(req);
		try {

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String currencyCode=objBean.getStrCurrency();
			double conversionRate=1;
			String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
			StringBuilder sbSql = new StringBuilder();
			sbSql.append("select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster where strCurrencyCode='"+currencyCode+"' and strClientCode='"+clientCode+"' ");
			try
			{
				List list = objBaseService.funGetList(sbSql,"sql");
				conversionRate=Double.parseDouble(list.get(0).toString());
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			double currValue = Double.parseDouble(req.getSession().getAttribute("currValue").toString());
			String type = "PDF";
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}

			String fromDate = objBean.getDteFromDate();
			String toDate = objBean.getDteToDate();

			String fd = fromDate.split("-")[0];
			String fm = fromDate.split("-")[1];
			String fy = fromDate.split("-")[2];

			String td = toDate.split("-")[0];
			String tm = toDate.split("-")[1];
			String ty = toDate.split("-")[2];

			String dteFromDate = fy + "-" + fm + "-" + fd;
			String dteToDate = ty + "-" + tm + "-" + td;
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webbooks/rptDebtorOutStandingList.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			ArrayList fieldList = new ArrayList();

			String sqlQuery = "select c.strDebtorCode,c.strDebtorName ,sum(b.dblDrAmt),sum(b.dblCrAmt) " 
					+ " FROM tbljvhd a,tbljvdtl b,tbljvdebtordtl c where a.strVouchNo= b.strVouchNo and b.strVouchNo=c.strVouchNo " 
					+ " and a.dteVouchDate between '" + dteFromDate + "' and '" + dteToDate + "' and a.strClientCode='" + clientCode + "' and a.strPropertyCode='" + propertyCode+ "' and b.strAccountCode='0020-02-00'  "
					+ " group by c.strDebtorCode ";

			List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");

			for (int j = 0; j < listProdDtl.size(); j++) {
				clsCreditorOutStandingReportBean objProdBean = new clsCreditorOutStandingReportBean();
				Object[] prodArr = (Object[]) listProdDtl.get(j);

				String billNo = "";
				if (prodArr[3].toString().contains(":")) {

					billNo = prodArr[3].toString().split(":")[1];
				}
				objProdBean.setStrDebtorCode(prodArr[0].toString());
				objProdBean.setStrDebtorName(prodArr[1].toString());
				//objProdBean.setDblCrAmt(Double.parseDouble(prodArr[2].toString()) / currValue);
				//objProdBean.setDblDrAmt(Double.parseDouble(prodArr[3].toString()) / currValue);
				objProdBean.setDblCrAmt(Double.parseDouble(prodArr[2].toString()) * conversionRate);
				objProdBean.setDblDrAmt(Double.parseDouble(prodArr[3].toString()) * conversionRate);
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
			hm.put("dteFromDate", fromDate);
			hm.put("dteToDate", toDate);

			// hm.put("fieldList",fieldList);

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
				if (type.equals("PDF")) {
					if (type.equals("PDF")) {
						JRExporter exporter = new JRPdfExporter();
						resp.setContentType("application/pdf");
						exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
						exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
						exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
						resp.setHeader("Content-Disposition", "inline;filename=rptDebtorOutStandingList" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".pdf");
						exporter.exportReport();
						servletOutputStream.flush();
						servletOutputStream.close();

					} else {
						JRExporter exporter = new JRXlsExporter();
						resp.setContentType("application/xlsx");
						exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
						exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
						exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
						resp.setHeader("Content-Disposition", "inline;filename=rptDebtorOutStandingList" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".xls");
						exporter.exportReport();
						servletOutputStream.flush();
						servletOutputStream.close();
					}

				}
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
