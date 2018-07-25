package com.sanguine.webbooks.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignQuery;
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
import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.util.clsReportBean;
import com.sanguine.webbooks.bean.clsBankReconciliationBean;
import com.sanguine.webbooks.bean.clsCreditorOutStandingReportBean;

@Controller
public class clsBankReconciliationReportController {

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

	
		@RequestMapping(value = "/frmBankReconciliationReport", method = RequestMethod.GET)
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
				return new ModelAndView("frmBankReconciliationReport_1", "command", new clsBankReconciliationBean());
			}
			else if ("1".equalsIgnoreCase(urlHits))
			{
				return new ModelAndView("frmBankReconciliationReport", "command", new clsBankReconciliationBean());
			}
			else
			{
				return null;
			}

		}
		
		@RequestMapping(value = "/openRptBankReconciliationReport", method = RequestMethod.GET)
		private void funOpenPaymentReport(@ModelAttribute("command") clsBankReconciliationBean objBean, HttpServletResponse resp, HttpServletRequest req){
			
			String type = "pdf";
			funCallPaymentdtlReport(objBean, type, resp, req);
		}


		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void funCallPaymentdtlReport(clsBankReconciliationBean objBean, String type, HttpServletResponse resp, HttpServletRequest req) {
			try {

				String strVouchNo = "",strCurrency="",currency="";
			
				String strNarration = "",strPayment="",strChequeNo="",strDrawnOn="";
				// objGlobal=new clsGlobalFunctions();
				Connection con = objGlobal.funGetConnection(req);
//				currency = objBean.getStrCurrency();
				String clientCode = req.getSession().getAttribute("clientCode").toString();
				String companyName = req.getSession().getAttribute("companyName").toString();
				String userCode = req.getSession().getAttribute("usercode").toString();
				String propertyCode = req.getSession().getAttribute("propertyCode").toString();
				String fDate = objBean.getDteFromDate();
				String[] fromDt = fDate.split("-");
				String fromDate = fromDt[2]+"-"+fromDt[1]+"-"+fromDt[0]; 
				String tDate = objBean.getDteToDate();
				String[] toDt = tDate.split("-");
				String toDate = toDt[2]+"-"+toDt[1]+"-"+toDt[0]; 
				
				clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
				if (objSetup == null) {
					objSetup = new clsPropertySetupModel();
				}
				String reportName = servletContext.getRealPath("/WEB-INF/reports/webbooks/rptBankReconciliationReport.jrxml");
				String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
				ArrayList fieldList = new ArrayList();
				double balAsPerBank=0.0,dblBalAmount=0.0;
				balAsPerBank = objBean.getDblBalAmount();
				String sqlDtl = "select 'Uncleared Payments' as transTupe,a.strVouchNo,a.strChequeNo,DATE_FORMAT(a.dteChequeDate,'%d-%m-%Y'),sum(b.dblDrAmt)-sum(b.dblCrAmt) as dblAmount from tblpaymenthd a,tblpaymentdtl b \n"  
						+ "where a.strVouchNo=b.strVouchNo  and b.strAccountCode='"+objBean.getStrBankCode()+"' and a.strType='Cheque' and\n" 
						+ "a.strPropertyCode='"+propertyCode+"' and a.strClientCode='"+clientCode+"'\n" 
						+ "and date(a.dteVouchDate) between '"+fromDate+"' and '"+toDate+"'  and date(a.dteClearence)='1990-01-01'\n" 
						+ "group by a.strVouchNo\n" 
						+ "union All \n" 
						+ "select 'Uncleared Receipts' as transType, a.strVouchNo,a.strChequeNo,DATE_FORMAT(a.dteChequeDate,'%d-%m-%Y'),sum(b.dblDrAmt)-sum(b.dblCrAmt) as dblAmount from tblreceipthd a,tblreceiptdtl b where a.strVouchNo=b.strVouchNo and \n"  
						+ "a.strPropertyCode='"+propertyCode+"' and a.strClientCode='"+clientCode+"' and b.strAccountCode='"+objBean.getStrBankCode()+"'\n"  
						+ "and a.strType='Cheque' and date(a.dteVouchDate) between '"+fromDate+"' and '"+toDate+"' and date(a.dteClearence)='1990-01-01'\n"  
						+ "group by a.strVouchNo;";
				List list = objGlobalFunctionsService.funGetListModuleWise(sqlDtl, "sql");
				if(list.size()>0)
				{
					for (int j = 0; j < list.size(); j++) 
					{
						clsBankReconciliationBean objRptBean = new clsBankReconciliationBean();
						Object[] obArr = (Object[]) list.get(j);
						objRptBean.setStrTransactionType(obArr[0].toString());
						objRptBean.setStrVouchNo(obArr[1].toString());
						objRptBean.setStrChequeNo(obArr[2].toString());
						objRptBean.setDteChequeDate(obArr[3].toString());
						objRptBean.setDblAmount(Double.parseDouble(obArr[4].toString()));
						if(obArr[0].toString().equalsIgnoreCase("Uncleared Payments"))
						{
							double paymentAmt=0.0;
							if(Double.parseDouble(obArr[4].toString())<0)
							{
								balAsPerBank = balAsPerBank - Double.parseDouble(obArr[4].toString().split("-")[1]);
							}
							else
							{
								balAsPerBank = balAsPerBank - Double.parseDouble(obArr[4].toString());
							}
							
						}
						else
						{
							balAsPerBank = balAsPerBank + Double.parseDouble(obArr[4].toString());
						}
						fieldList.add(objRptBean);
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
				hm.put("strVouchNo", "");
				hm.put("dteVouchDate","");
				hm.put("strNarration", strNarration);
				hm.put("strPaymentType", strPayment);
				hm.put("lblCardNo", "");
				hm.put("strCardNo", "");
				hm.put("dteFromDate", fDate);
				hm.put("dteToDate", tDate);
				hm.put("strBankName",objBean.getStrBankName());
				hm.put("dblBalAmount",objBean.getDblBalAmount());
				hm.put("balAsPerBank", balAsPerBank);
//				hm.put("strCurrency", currency);
				
				

				List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();

				JasperDesign jd = JRXmlLoader.load(reportName);
				JasperReport jr = JasperCompileManager.compileReport(jd);
				JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
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

				} else {
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

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
}
