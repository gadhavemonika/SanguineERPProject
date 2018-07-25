package com.sanguine.crm.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
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
import com.sanguine.crm.bean.clsSubGruopWiseItemSalesReportBean;
import com.sanguine.excise.bean.clsSubCategoryMasterBean;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webbooks.bean.clsCreditorOutStandingReportBean;
import com.sanguine.webbooks.bean.clsIncomeStmtReportBean;

@Controller
public class clsSubGroupWiseItemSalesReportController {

	@Autowired
	private clsGlobalFunctions objGlobal;
	
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

	
	
		@RequestMapping(value = "/frmSubGroupWiseItemSalesReport", method = RequestMethod.GET)
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
			String clientCode = request.getSession().getAttribute("clientCode").toString();
			Map<String, String> hmCurrency = objCurrencyMasterService.funCurrencyListToDisplay(clientCode);
			if (hmCurrency.isEmpty())
			{
				hmCurrency.put("", "");
			}
			model.put("currencyList", hmCurrency);

			if ("2".equalsIgnoreCase(urlHits))
			{
				return new ModelAndView("frmSubGroupWiseItemSalesReport_1", "command", new clsInvoiceBean());
			}
			else if ("1".equalsIgnoreCase(urlHits))
			{
				return new ModelAndView("frmSubGroupWiseItemSalesReport", "command", new clsInvoiceBean());
			}
			else
			{
				return null;
			}

		}
		
		
		@RequestMapping(value = "/rptSubGroupWiseItemSalesReport", method = RequestMethod.GET)
		private void funReport(@ModelAttribute("command") clsInvoiceBean objBean, HttpServletResponse resp, HttpServletRequest req)
		{
			
			Connection con = objGlobal.funGetConnection(req);
			try
			{

				String clientCode = req.getSession().getAttribute("clientCode").toString();
				String companyName = req.getSession().getAttribute("companyName").toString();
				String userCode = req.getSession().getAttribute("usercode").toString();
				String propertyCode = req.getSession().getAttribute("propertyCode").toString();
				String locCode =  req.getSession().getAttribute("locationCode").toString();
				String currencyCode = objBean.getStrCurrency();
				double conversionRate = 1;
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
					conversionRate = Double.parseDouble(list.get(0).toString());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				double currValue = Double.parseDouble(req.getSession().getAttribute("currValue").toString());

				clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
				if (objSetup == null)
				{
					objSetup = new clsPropertySetupModel();
				}
				String type = "PDF";
				String fromDate = objBean.getDteFromDate();
				String toDate = objBean.getDteToDate();

				String dteFromDate = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];
				String dteToDate = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];
				String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptSubGruopWiseItemSalesReport.jrxml");
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

				
				List<clsInvoiceBean> listOfData = new ArrayList<clsInvoiceBean>();
			
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT b.strSGCode,c.strSGName, SUM(d.dblQty)/" + currValue + ", SUM(d.dblQty*d.dblUnitPrice)/" + currValue + ",e.strGName, SUM(d.dblProdDiscAmount)/" + currValue + ",b.strProdName\n" 
						+ " FROM tblinvoicehd a,tblproductmaster b,tblsubgroupmaster c,tblinvoicedtl d,tblgroupmaster e,tblpartymaster f\n" 
						+ " WHERE a.strInvCode=d.strInvCode AND d.strProdCode=b.strProdCode AND b.strSGCode=c.strSGCode AND c.strGCode=e.strGCode \n"  
						+ " AND a.strCustCode=f.strPCode AND a.strLocCode='"+locCode+"' AND DATE(a.dteInvDate) BETWEEN '"+dteFromDate+"' AND '"+dteToDate+"' \n"  
						+ " AND a.strClientCode='"+clientCode+"'\n" 
						+ " GROUP BY c.strSGCode,d.strProdCode");
				List listOfDtl = objGlobalFunctionsService.funGetList(sql.toString(), "sql");
				if (!listOfDtl.isEmpty()) {
					for (int i = 0; i < listOfDtl.size(); i++) {
						Object[] obj = (Object[]) listOfDtl.get(i);
						clsInvoiceBean objInvBean = new clsInvoiceBean();
						objInvBean.setStrProdCode(obj[0].toString());//subGroup Code
						objInvBean.setStrProdType(obj[1].toString());//SubGroup Name
						objInvBean.setDblQty(Double.parseDouble(obj[2].toString()));//qty
						objInvBean.setDblTotalAmt(Double.parseDouble(obj[3].toString()));//amount
						objInvBean.setStrProdName(obj[6].toString());//product Name
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
				else
				{
					JRExporter exporter = new JRXlsExporter();
					resp.setContentType("application/xlsx");
					exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					// resp.setHeader("Content-Disposition",
					// "inline;filename=rptProductWiseGRNReport_"+dteFromDate+"_To_"+dteToDate+"_"+userCode+".xls");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
					// }

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
