package com.sanguine.webbooks.apgl.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webbooks.bean.clsCreditorOutStandingReportBean;

@Controller
public class clsCreditorsOutStandingReportController {

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsCurrencyMasterService objCurrencyMasterService;
	
	@Autowired
	private clsGlobalFunctions objGlobalfunction;

	@Autowired
	intfBaseService objBaseService;
	// Open Buget Form
	@RequestMapping(value = "/frmCreditorsOutStandingReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		clsCreditorOutStandingReportBean objBean= new clsCreditorOutStandingReportBean();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propertyCode = request.getSession().getAttribute("propertyCode").toString();
		String sql="select strAccountCode from tblacmaster where strClientCode='"+clientCode+"' and strCreditor='Yes'  and strPropertyCode = '"+propertyCode+"' ";
		List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		if (list.size() > 0) {
			objBean.setStrGLCode(list.get(0).toString());
		}
		model.put("urlHits", urlHits);
		Map<String, String> hmCurrency = objCurrencyMasterService.funCurrencyListToDisplay(clientCode);
		if (hmCurrency.isEmpty()) {
			hmCurrency.put("", "");
		}
		model.put("currencyList", hmCurrency);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCreditorsOutStandingReport_1", "command", objBean);
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCreditorsOutStandingReport", "command", objBean);
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/rptCreditorsOutStandingReport", method = RequestMethod.GET)
	private void funReport(@ModelAttribute("command") clsCreditorOutStandingReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		
		Connection con = objGlobalfunction.funGetConnection(req);
		try {

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String currencyCode=objBean.getStrCurrency();
			double conversionRate=1;
			String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
			StringBuilder sbSql = new StringBuilder();
			String glCode=objBean.getStrAccountCode();
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
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
		
			String fromDate = objBean.getDteFromDate();
			String toDate = objBean.getDteToDate();
			String type = "PDF";
			String fd = fromDate.split("-")[0];
			String fm = fromDate.split("-")[1];
			String fy = fromDate.split("-")[2];

			String td = toDate.split("-")[0];
			String tm = toDate.split("-")[1];
			String ty = toDate.split("-")[2];

			String dteFromDate = fy + "-" + fm + "-" + fd;
			String dteToDate = ty + "-" + tm + "-" + td;
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webbooks/rptCreditorsOutStandingReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			SimpleDateFormat obj = new SimpleDateFormat("dd-MM-yyyy");
			Date dt1 = obj.parse(fromDate);
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(dt1);
			cal.add(Calendar.DATE, +1);
			String newfromDate = (cal.getTime().getYear() + 1900) + "-" + (cal.getTime().getMonth() + 1) + "-" + (cal.getTime().getDate());
			ArrayList fieldList = new ArrayList();
			
			String startDate = req.getSession().getAttribute("startDate").toString();
			startDate = startDate.split("/")[2] + "-" + startDate.split("/")[1] + "-" + startDate.split("/")[0];
			
			
			Map<String,clsCreditorOutStandingReportBean> hmOutstanding = objGlobalfunction.funCalCreditorDebtorOPBalance(glCode, clientCode, startDate, dteFromDate, "Creditor", req);
			
			if(hmOutstanding.containsKey("C00000001"))
			{
				System.out.println(hmOutstanding.get("C00000001"));
			}
			
			sbSql.setLength(0);
			sbSql.append("select debtor.strDebtorCode,'',debtor.strCrDr, sum(ifnull(debtor.dblAmt,0.00)) "
				+ " from tbljvdebtordtl debtor inner join tbljvhd hd on hd.strVouchNo=debtor.strVouchNo "
				+ " where debtor.strAccountCode='"+glCode+"' and debtor.strCrDr='Dr' and date(hd.dteVouchDate) between '" + newfromDate + "' AND '" + dteToDate + "' "
				+ " and hd.strClientCode='"+clientCode+"' "
				+ " group by debtor.strCrDr,debtor.strDebtorCode ");
			List listJVAmt = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
			if (listJVAmt != null && listJVAmt.size() > 0) {
				for (int j = 0; j < listJVAmt.size(); j++) {
					Object[] arrObj = (Object[]) listJVAmt.get(j);
					clsCreditorOutStandingReportBean objOutStBean = new clsCreditorOutStandingReportBean();
			
					if(hmOutstanding.containsKey("C00000001"))
					{
						System.out.println(hmOutstanding.get("C00000001"));
					}
					
					if(hmOutstanding.containsKey(arrObj[0].toString()))
					{
						objOutStBean=hmOutstanding.get(arrObj[0].toString());
						double debitAmt=objOutStBean.getDblDrAmt()+Double.parseDouble(arrObj[3].toString());
						objOutStBean.setDblDrAmt(debitAmt);
						//objOutStBean.setDblCrAmt(objOutStBean.getDblBalAmt());
					}
					else
					{
						objOutStBean.setStrDebtorCode(arrObj[0].toString());
						objOutStBean.setStrDebtorName(arrObj[1].toString());
						objOutStBean.setDblBalAmt(0.00);
						objOutStBean.setDblDrAmt(Double.parseDouble(arrObj[3].toString()));
						objOutStBean.setDblCrAmt(0.00);
						objOutStBean.setDblOpnAmt(0.00);
					}
					objOutStBean.setDblBalAmt(objOutStBean.getDblBalAmt()-objOutStBean.getDblDrAmt());
					
					hmOutstanding.put(arrObj[0].toString(),objOutStBean);
				}
			}
			
			
			sbSql.setLength(0);
			sbSql.append("select debtor.strDebtorCode,'',debtor.strCrDr, sum(ifnull(debtor.dblAmt,0.00)) "
				+ " from tblpaymentdebtordtl debtor inner join tblpaymenthd hd on hd.strVouchNo=debtor.strVouchNo "
				+ " where debtor.strAccountCode='"+glCode+"' and debtor.strCrDr='Dr' and date(hd.dteVouchDate) between '" + newfromDate + "' AND '" + dteToDate + "' "
				+ " and hd.strClientCode='"+clientCode+"' "
				+ " group by debtor.strCrDr,debtor.strDebtorCode ");
			List listPaymentAmt = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
			if (listPaymentAmt != null && listPaymentAmt.size() > 0) {
				for (int j = 0; j < listPaymentAmt.size(); j++) {
					Object[] arrObj = (Object[]) listPaymentAmt.get(j);
					clsCreditorOutStandingReportBean objOutStBean = new clsCreditorOutStandingReportBean();
					
					if(hmOutstanding.containsKey("C00000001"))
					{
						System.out.println(hmOutstanding.get("C00000001"));
					}
					
					if(hmOutstanding.containsKey(arrObj[0].toString()))
					{
						objOutStBean=hmOutstanding.get(arrObj[0].toString());
						double debitAmt=objOutStBean.getDblDrAmt()+Double.parseDouble(arrObj[3].toString());
						objOutStBean.setDblDrAmt(debitAmt);
						//objOutStBean.setDblCrAmt(objOutStBean.getDblBalAmt());
					}
					else
					{
						objOutStBean.setStrDebtorCode(arrObj[0].toString());
						objOutStBean.setStrDebtorName(arrObj[1].toString());
						objOutStBean.setDblBalAmt(0.00);
						objOutStBean.setDblDrAmt(Double.parseDouble(arrObj[3].toString()));
						objOutStBean.setDblCrAmt(0.00);
						objOutStBean.setDblOpnAmt(0.00);
					}
					objOutStBean.setDblBalAmt(objOutStBean.getDblBalAmt()-objOutStBean.getDblDrAmt());
					
					hmOutstanding.put(arrObj[0].toString(),objOutStBean);
				}
			}
			
			
			sbSql.setLength(0);
			sbSql.append("select debtor.strDebtorCode,'',debtor.strCrDr, sum(ifnull(debtor.dblAmt,0.00)) "
				+ " from tblreceiptdebtordtl debtor inner join tblreceipthd hd on hd.strVouchNo=debtor.strVouchNo "
				+ " where debtor.strAccountCode='"+glCode+"' and debtor.strCrDr='Dr' and date(hd.dteVouchDate) between '" + newfromDate + "' AND '" + dteToDate + "' "
				+ " and hd.strClientCode='"+clientCode+"' "
				+ " group by debtor.strCrDr,debtor.strDebtorCode ");
			List listReceiptAmt = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
			if (listReceiptAmt != null && listReceiptAmt.size() > 0) {
				for (int j = 0; j < listReceiptAmt.size(); j++) {
					Object[] arrObj = (Object[]) listReceiptAmt.get(j);
					clsCreditorOutStandingReportBean objOutStBean = new clsCreditorOutStandingReportBean();
					if(hmOutstanding.containsKey(arrObj[0].toString()))
					{
						objOutStBean=hmOutstanding.get(arrObj[0].toString());
						double debitAmt=objOutStBean.getDblDrAmt()+Double.parseDouble(arrObj[3].toString());
						objOutStBean.setDblDrAmt(debitAmt);
						//objOutStBean.setDblCrAmt(objOutStBean.getDblBalAmt());
					}
					else
					{
						objOutStBean.setStrDebtorCode(arrObj[0].toString());
						objOutStBean.setStrDebtorName(arrObj[1].toString());
						objOutStBean.setDblBalAmt(0.00);
						objOutStBean.setDblDrAmt(Double.parseDouble(arrObj[3].toString()));
						objOutStBean.setDblCrAmt(0.00);
						objOutStBean.setDblOpnAmt(0.00);
					}
					objOutStBean.setDblBalAmt(objOutStBean.getDblBalAmt()-objOutStBean.getDblDrAmt());
					
					hmOutstanding.put(arrObj[0].toString(),objOutStBean);
				}
			}
			
			for(Map.Entry<String, clsCreditorOutStandingReportBean> entry:hmOutstanding.entrySet())
			{
				if(entry.getKey().equals("C00000001"))
				{
					System.out.println(entry.getValue());
				}
				clsCreditorOutStandingReportBean objCreditorOutStandingReportBean=entry.getValue();
				if(objCreditorOutStandingReportBean.getDblBalAmt()>0)
				{
					objCreditorOutStandingReportBean.setDblBalAmt(objCreditorOutStandingReportBean.getDblBalAmt()*conversionRate);
					objCreditorOutStandingReportBean.setDblCrAmt(objCreditorOutStandingReportBean.getDblCrAmt()*conversionRate);
					objCreditorOutStandingReportBean.setDblDrAmt(objCreditorOutStandingReportBean.getDblDrAmt()*conversionRate);
					objCreditorOutStandingReportBean.setDblOpnAmt(objCreditorOutStandingReportBean.getDblOpnAmt()*conversionRate);
					fieldList.add(objCreditorOutStandingReportBean);
				}
			}
			
			
			/*
			StringBuilder sbSqllink=new StringBuilder();
			sbSqllink.append(" select a.strAccountCode,a.strMasterDesc from "+webStockDB+".tbllinkup a where a.strWebBookAccCode='"+glCode+"' and a.strPropertyCode='"+propertyCode+"' and a.strClientCode='"+clientCode+"' ");
			 List listDebtor = objBaseService.funGetListModuleWise(sbSqllink, "sql", "WebStocks");	 
					 if (listDebtor.size() > 0 && listDebtor != null) {
							for (int j = 0; j < listDebtor.size(); j++) {
								Object[] debArr = (Object[]) listDebtor.get(j);
								String debtorCode=debArr[0].toString();
								String debtorName=debArr[1].toString();
								double bal=objGlobalfunction.funGetBalanceAmtCreditorDetor("Creditor",debtorCode,fromDate,currencyCode,req,resp);
								
								double debitAmt=0.0;
								double creditAmt=0.0;
//								String sqlQuery = "select c.strDebtorCode,c.strDebtorName ,sum(b.dblDrAmt),sum(b.dblCrAmt) " 
//										+ " FROM tbljvhd a,tbljvdtl b,tbljvdebtordtl c where a.strVouchNo= b.strVouchNo and b.strVouchNo=c.strVouchNo " 
//										+ " and date(a.dteVouchDate) between '" + dteFromDate + "' and '" + dteToDate + "' and a.strClientCode='" + clientCode + "' and a.strPropertyCode='" + propertyCode+ "' and c.strDebtorCode='"+debtorCode+"'  "
//										+ " group by c.strDebtorCode order by c.strDebtorCode ";

								String sqlQuery = " SELECT DATE(a.dteVouchDate),a.strVouchNo,'JV'  ,c.strBillNo , DATE(c.dteInvoiceDate) , " + " sum(b.dblDrAmt) ,sum(b.dblCrAmt) ,'Cr','','1','" + userCode + "','" + propertyCode + "','" + clientCode + "' " 
								        + " FROM tbljvhd a, tbljvdtl b,tbljvdebtordtl c "
										+ " WHERE a.strVouchNo=b.strVouchNo AND a.strVouchNo=c.strVouchNo " 
										+ " AND DATE(a.dteVouchDate) BETWEEN '" + newfromDate + "' AND '" + dteToDate + "' " 
										+ " AND b.strAccountCode='" + glCode + "' and c.strDebtorCode='" + debtorCode + "' "
										+ " AND a.strPropertyCode=b.strPropertyCode AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' "
										+ " group by c.strDebtorCode order by c.strDebtorCode ";
								List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");
   
								if (listProdDtl.size() > 0 && listProdDtl != null) {
									Object[] prodArr = (Object[]) listProdDtl.get(j);
									
									String billNo = "";
									 debitAmt =debitAmt+Double.parseDouble(prodArr[5].toString());
//									 creditAmt =Double.parseDouble(prodArr[6].toString());
									
								}
								
								

								String sqlreceipt  = "  SELECT DATE(a.dteVouchDate) ,a.strVouchNo,'Receipt', a.strChequeNo, " 
									    + " DATE(a.dteChequeDate) ,  sum(b.dblDrAmt) , sum(b.dblCrAmt) ,b.strCrDr,'','3','" + userCode + "','" + propertyCode + "','" + clientCode + "' "
										+ " FROM tblreceipthd a, tblreceiptdtl b,tblreceiptdebtordtl c  WHERE a.strVouchNo=b.strVouchNo and a.strVouchNo=c.strVouchNo "
									    + " AND DATE(a.dteVouchDate) BETWEEN '" + newfromDate + "' AND '" + dteToDate + "' " + " and  b.strAccountCode='" + glCode + "'  and c.strDebtorCode='" + debtorCode + "'  "
										+ " AND a.strPropertyCode=b.strPropertyCode " 
									    + " AND a.strPropertyCode='"+ propertyCode + "' AND a.strClientCode='" + clientCode + "'  "
									    + " group by c.strDebtorCode";
								
								List listREcipt = objGlobalFunctionsService.funGetDataList(sqlreceipt, "sql");

								if (listREcipt.size() > 0 && listREcipt != null) {
									Object[] payment = (Object[]) listREcipt.get(0);
//									creditAmt =creditAmt+ Double.parseDouble(payment[6].toString());
									debitAmt =debitAmt+ Double.parseDouble(payment[5].toString());
									
								}
								
								String sqlPay=" SELECT DATE(a.dteVouchDate) ,a.strVouchNo,'Payment', a.strChequeNo, DATE(a.dteChequeDate) ,sum(b.dblDrAmt) , sum(b.dblCrAmt)  ,'Dr','','2','" + userCode + "','" + propertyCode + "','" + clientCode + "' " 
									    + " FROM tblpaymenthd a, tblpaymentdtl b,tblpaymentdebtordtl c "
										+ " WHERE a.strVouchNo=b.strVouchNo and a.strVouchNo=c.strVouchNo "
									    + " AND DATE(a.dteVouchDate) BETWEEN '" + newfromDate + "' AND '" + dteToDate + "' " 
										+ " AND b.strAccountCode='" + glCode + "' and c.strDebtorCode='" + debtorCode + "' AND a.strPropertyCode=b.strPropertyCode " 
									    + " AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "'  "
									    + " group by c.strDebtorCode";
							

							

							List listPaymt = objGlobalFunctionsService.funGetDataList(sqlPay, "sql");
							
							if (listPaymt.size() > 0 && listPaymt != null) {
								Object[] payment = (Object[]) listPaymt.get(0);
//								creditAmt =creditAmt+ Double.parseDouble(payment[6].toString());
								debitAmt =debitAmt+ Double.parseDouble(payment[5].toString());
								
							}
							if((bal-debitAmt)<0){
								clsCreditorOutStandingReportBean objProdBean = new clsCreditorOutStandingReportBean();

								objProdBean.setStrDebtorCode(debtorCode);
								objProdBean.setStrDebtorName(debtorName);
								//objProdBean.setDblCrAmt(Double.parseDouble(prodArr[2].toString()) / currValue);
								//objProdBean.setDblDrAmt(Double.parseDouble(prodArr[3].toString()) / currValue);
								objProdBean.setDblCrAmt(debitAmt * conversionRate);
								objProdBean.setDblDrAmt(bal * conversionRate);
								objProdBean.setDblBalAmt((bal-debitAmt)*conversionRate);
								fieldList.add(objProdBean);
								
							}
					
							}
							}
*/
			
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
					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=rptCreditorsOutStandingReport_" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".pdf");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();

				} else {
					JRExporter exporter = new JRXlsExporter();
					resp.setContentType("application/xlsx");
					exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=rptCreditorsOutStandingReport_" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".xls");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
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
