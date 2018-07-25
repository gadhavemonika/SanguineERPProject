package com.sanguine.webbooks.controller;

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
import com.sanguine.webbooks.bean.clsChartOfAccountReportBean;

@Controller
public class clsChartOfAccountReportController {

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
	
	// Open Buget Form
	@RequestMapping(value = "/frmChartOfAccountReport", method = RequestMethod.GET)
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
			return new ModelAndView("frmChartOfAccountReport_1", "command", new clsChartOfAccountReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmChartOfAccountReport", "command", new clsChartOfAccountReportBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/rptChartOfAccountReport", method = RequestMethod.GET)
	private void funReport(@ModelAttribute("command") clsChartOfAccountReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		try {

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String type = "PDF";
			
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webbooks/rptChartOfAccReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			ArrayList<clsChartOfAccountReportBean> fieldList = new ArrayList();
			String startDate = req.getSession().getAttribute("startDate").toString();

			String[] sp = startDate.split(" ");
			String[] spDate = sp[0].split("/");
			startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
			

			String sql = "select b.strGroupCode, b.strGroupName, a.strAccountCode, " 
					+ " a.strAccountName, a.strOperational, a.strType, "
					+ " a.strDebtor, a.strCreditor, a.strEmployee ,b.strCategory "   
					+ " from tblacmaster a, tblacgroupmaster b "
					+ " where a.strGroupCode = b.strGroupCode "
					+ " order by a.strGroupCode, a.strAccountCode";

			List listAc = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
			if(listAc.size()>0)
			{
				for(int i=0;i<listAc.size();i++)
				{
					Object[] obj = (Object[]) listAc.get(i);
					clsChartOfAccountReportBean objBeanDtl = new clsChartOfAccountReportBean();
					objBeanDtl.setStrGroupCode(obj[0].toString());
					objBeanDtl.setStrGroupName(obj[1].toString());
					objBeanDtl.setStrAccountCode(obj[2].toString());
					objBeanDtl.setStrAccountName(obj[3].toString());
					objBeanDtl.setStrOperational(obj[4].toString());
					objBeanDtl.setStrType(obj[5].toString());
					objBeanDtl.setStrDebtor(obj[6].toString());
					objBeanDtl.setStrCreditor(obj[7].toString());
					objBeanDtl.setStrEmployee(obj[8].toString());
					objBeanDtl.setStrGrpCategory(obj[9].toString());
					fieldList.add(objBeanDtl);
				}
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
			

		List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();

		JasperDesign jd = JRXmlLoader.load(reportName);
		JasperReport jr = JasperCompileManager.compileReport(jd);
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList,false);
		JasperPrint print = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
		jprintlist.add(print);
		ServletOutputStream servletOutputStream = resp.getOutputStream();
		if (jprintlist.size() > 0) {
			// if(objBean.getStrDocType().equals("PDF"))
			// {
			JRExporter exporter = new JRPdfExporter();
			resp.setContentType("application/pdf");
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
			exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
			// resp.setHeader("Content-Disposition",
			// "inline;filename=rptProductWiseGRNReport_"+dteFromDate+"_To_"+dteToDate+"_"+userCode+".pdf");
			exporter.exportReport();
			servletOutputStream.flush();
			servletOutputStream.close();

		} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}

	}

	

}
