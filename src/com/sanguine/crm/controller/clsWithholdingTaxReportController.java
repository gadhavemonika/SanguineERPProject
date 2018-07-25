package com.sanguine.crm.controller;


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
import com.sanguine.crm.bean.clsInvoiceBean;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;

@Controller
public class clsWithholdingTaxReportController {

	@Autowired
	private clsGlobalFunctions objGlobal;
	
	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	intfBaseService objBaseService;

	
	
		@RequestMapping(value = "/frmWithholdingTaxReport", method = RequestMethod.GET)
		public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request)
		{
			String urlHits = "1";
			try
			{
				urlHits = request.getParameter("saddr").toString();
			}
			catch (NullPointerException e)
			{
				urlHits = "1";
			}
			model.put("urlHits", urlHits);
			if ("2".equalsIgnoreCase(urlHits))
			{
				return new ModelAndView("frmWithholdingTaxReport_1", "command", new clsInvoiceBean());
			}
			else if ("1".equalsIgnoreCase(urlHits))
			{
				return new ModelAndView("frmWithholdingTaxReport", "command", new clsInvoiceBean());
			}
			else
			{
				return null;
			}

		}
		
		
		@RequestMapping(value = "/rptWithholdingTaxReport", method = RequestMethod.GET)
		private void funReport(@ModelAttribute("command") clsInvoiceBean objBean, HttpServletResponse resp, HttpServletRequest req)
		{
			
			Connection con = objGlobal.funGetConnection(req);
			try
			{

				String clientCode = req.getSession().getAttribute("clientCode").toString();
				String companyName = req.getSession().getAttribute("companyName").toString();
				String userCode = req.getSession().getAttribute("usercode").toString();
				String propertyCode = req.getSession().getAttribute("propertyCode").toString();
				String currencyCode = objBean.getStrCurrency();
				String customerCode = objBean.getStrCustCode();
				String webStockDB = req.getSession().getAttribute("WebStockDB").toString();
				StringBuilder sbSql = new StringBuilder();
				sbSql.append("select dblConvToBaseCurr from " + webStockDB + ".tblcurrencymaster where ");
				if(currencyCode!=null)
				{
					sbSql.append( "strCurrencyCode='" + currencyCode + "' and strClientCode='" + clientCode + "' ");
				}
				else
				{
					sbSql.append( " strClientCode='" + clientCode + "' ");
				}
				
						
				try
				{
					List list = objBaseService.funGetList(sbSql, "sql");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
				if (objSetup == null)
				{
					objSetup = new clsPropertySetupModel();
				}
				
				String fromDate = objBean.getDteFromDate();
				String toDate = objBean.getDteToDate();

				String dteFromDate = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];
				String dteToDate = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];
				String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptWithholdingTaxReport.jrxml");
				String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

				
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
				hm.put("customerName", objBean.getStrCustName());
				
				List<clsInvoiceBean> listOfData = new ArrayList<clsInvoiceBean>();
			
				StringBuilder sql = new StringBuilder();
				sql.append("select d.strPName,a.strInvCode,DATE_FORMAT(a.dteInvDate,'%d-%m-%Y'),a.dblSubTotalAmt as Net_Total,"
						+ " (a.dblSubTotalAmt*(6/100)) as WT_Amt,((a.dblSubTotalAmt-(a.dblSubTotalAmt*(6/100)))+a.dblTaxAmt) as Credit\n"  
						+ " ,a.dblTaxAmt as Tax_Amt\n"  
						+ " from tblinvoicehd a,tblinvtaxdtl c,tblpartymaster d\n"  
						+ " where a.strInvCode=c.strInvCode ");
				if(customerCode.equalsIgnoreCase(""))
				{
					sql.append("and a.strCustCode=d.strPCode"); 
				}
				else
				{
					sql.append("and a.strCustCode=d.strPCode and a.strCustCode='"+customerCode+"'"); 
				}
				sql.append(" and d.strApplForWT='Y' and date(a.dteInvDate) between '"+dteFromDate+"' and '"+dteToDate+"'"
						+ "group by a.strInvCode order by a.dteInvDate,a.strInvCode");
						
				List listOfDtl = objGlobalFunctionsService.funGetList(sql.toString(), "sql");
				if (!listOfDtl.isEmpty()) {
					for (int i = 0; i < listOfDtl.size(); i++) {
						Object[] obj = (Object[]) listOfDtl.get(i);
						clsInvoiceBean objInvBean = new clsInvoiceBean();
						objInvBean.setStrCustName(obj[0].toString());//Customer Name
						objInvBean.setStrInvCode(obj[1].toString());//Invoice Code
						objInvBean.setDteInvDate(obj[2].toString());//Invoice Date
						objInvBean.setDblSubTotalAmt(Double.parseDouble(obj[3].toString()));//Net Amount
						objInvBean.setDblTotalAmt(Double.parseDouble(obj[4].toString()));// With Holding Tax 6%
						objInvBean.setDblGrandTotal(Double.parseDouble(obj[5].toString()));//Credit Amount
						objInvBean.setDblTaxAmt(Double.parseDouble(obj[6].toString()));//Tax Amount
						listOfData.add(objInvBean);
					}
					
				}
				

				List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();

				JasperDesign jd = JRXmlLoader.load(reportName);
				JasperReport jr = JasperCompileManager.compileReport(jd);
				JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listOfData);
				JasperPrint print = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
				jprintlist.add(print);
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				if (jprintlist.size() > 0)
				{
					
					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();

				}
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					con.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}

		}
		
	
}
