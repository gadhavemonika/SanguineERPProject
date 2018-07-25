package com.sanguine.webbooks.controller;

import java.sql.SQLException;
import java.text.ParseException;
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
import com.sanguine.webbooks.model.clsLedgerSummaryModel;

@Controller
public class clsCreditorTrialBalanceController {

	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	
	@Autowired
	private clsCurrencyMasterService objCurrencyMasterService;

	@Autowired
	intfBaseService objBaseService;

	@RequestMapping(value = "/frmCreditorTrialBalanceReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propertyCode = request.getSession().getAttribute("propertyCode").toString();
		clsCreditorOutStandingReportBean objBean= new clsCreditorOutStandingReportBean();
		String sql="select strAccountCode from tblacmaster where strClientCode='"+clientCode+"' and strCreditor='Yes'  and strPropertyCode = '"+propertyCode+"' ";
		List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		if (list.size() > 0) {
			objBean.setStrGLCode(list.get(0).toString());
		}
		Map<String, String> hmCurrency = objCurrencyMasterService.funCurrencyListToDisplay(clientCode);
		if (hmCurrency.isEmpty()) {
			hmCurrency.put("", "");
		}
		model.put("currencyList", hmCurrency);
		
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCreditorTrialBalanceReport_1", "command", objBean);
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCreditorTrialBalanceReport", "command", objBean);
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/rptCreditorTrialBalanceReport", method = RequestMethod.GET)
	private void funReport(@ModelAttribute("command") clsCreditorOutStandingReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		Connection con = objGlobal.funGetConnection(req);
		try {

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String currencyCode=objBean.getStrCurrency();
			String glCode=objBean.getStrAccountCode();
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
			
			
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String type = "PDF";
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
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webbooks/rptCreditorTrialBalanceReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			ArrayList fieldList = new ArrayList();
			String startDate = req.getSession().getAttribute("startDate").toString();

			String[] sp = startDate.split(" ");
			String[] spDate = sp[0].split("/");
			startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
			HashMap<String, Double> hmMap = new HashMap<String, Double>();
			String sunCrAccountCode = glCode;
			String sql="";
			StringBuilder sbSqllink=new StringBuilder();
			sbSqllink.append(" select a.strAccountCode,a.strMasterDesc from "+webStockDB+".tbllinkup a where a.strWebBookAccCode='"+glCode+"' and a.strPropertyCode='"+propertyCode+"' and a.strClientCode='"+clientCode+"' ");
			 List listDebtor = objBaseService.funGetListModuleWise(sbSqllink, "sql", "WebStocks");	 
					 if (listDebtor.size() > 0 && listDebtor != null) {
					for (int i = 0; i < listDebtor.size(); i++) {
						Object[] debArr = (Object[]) listDebtor.get(i);
						
						String sunCrCode=debArr[0].toString();
						String sunCrName=debArr[1].toString();
						double openingbal = 0.00;

						String tempFromDate =  objGlobal.funGetDate("yyyy-MM-dd", fromDate);;
						 tempFromDate = tempFromDate.split("-")[2] + "-" + tempFromDate.split("-")[1] + "-" + tempFromDate.split("-")[0];
						SimpleDateFormat obj = new SimpleDateFormat("dd-MM-yyyy");
						Date dt1;
						try {
							dt1 = obj.parse(tempFromDate);
							GregorianCalendar cal = new GregorianCalendar();
							cal.setTime(dt1);
							cal.add(Calendar.DATE, -1);
							String newToDate = (cal.getTime().getYear() + 1900) + "-" + (cal.getTime().getMonth() + 1) + "-" + (cal.getTime().getDate());

							funProcessWebBookCreditorTrailBal(sunCrAccountCode, sunCrCode, startDate, newToDate, clientCode, userCode, propertyCode, req, resp);

							/*
							 * sql =
							 * " select sum(a.dblDebitAmt-a.dblCreditAmt) from tblledgersummary a "
							 * + " where  a.strUserCode='"+userCode+"' " +
							 * " and a.strPropertyCode='"
							 * +propertyCode+"' and a.strClientCode='"
							 * +clientCode+
							 * "' group by a.strUserCode,a.strPropertyCode,strClientCode "
							 * ;
							 */
							sql = " select ifnull(sum(bal),0) from  (select (IF(a.strCrDr='Dr',a.dblOpeningbal,0) - IF(a.strCrDr='Cr',a.dblOpeningbal,0)) bal " 
								+ " from tblsundarycreditoropeningbalance a " + " where a.strCreditorCode='" + sunCrCode + "' and a.strAccountCode='" + sunCrAccountCode + "' AND a.strClientCode='" + clientCode + "' " + " UNION All "
								+ " SELECT SUM(a.dblDebitAmt-a.dblCreditAmt) bal " + " FROM tblledgersummary a " + " WHERE a.strUserCode='super' AND a.strPropertyCode='" + propertyCode + "' " + " AND a.strClientCode='" + clientCode + "' " 
								+ " GROUP BY a.strUserCode,a.strPropertyCode,strClientCode ) opening ";

							List listOpBalOld = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
							
								if (listOpBalOld.size() > 0) {
								//	openingbal = Double.parseDouble(listOpBalOld.get(0).toString()) / currValue;
									openingbal = Double.parseDouble(listOpBalOld.get(0).toString()) / conversionRate;
								}
							

							funProcessWebBookCreditorTrailBal(sunCrAccountCode, sunCrCode, dteFromDate, dteToDate, clientCode, userCode, propertyCode, req, resp);

							sql = " select sum(a.dblCreditAmt),sum(a.dblDebitAmt) " + " from tblledgersummary a "
								+ " where a.strUserCode='" + userCode + "' " + " and a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' " 
								+ " group by a.strUserCode,a.strPropertyCode,a.strClientCode  ";

							List listMainBal = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
							if(listMainBal.size()>0)
							{
							for (int j = 0; j < listMainBal.size(); j++) {
								clsCreditorOutStandingReportBean objRptBean = new clsCreditorOutStandingReportBean();
								Object[] obArr = (Object[]) listMainBal.get(j);
								objRptBean.setDblOpnAmt(openingbal);
								objRptBean.setStrDebtorCode(sunCrCode);
								objRptBean.setStrDebtorName(sunCrName);
								/*objRptBean.setDblCrAmt(Double.parseDouble(obArr[0].toString()) / currValue);
								objRptBean.setDblDrAmt(Double.parseDouble(obArr[1].toString()) / currValue);*/
								objRptBean.setDblCrAmt(Double.parseDouble(obArr[0].toString()) / conversionRate);
								objRptBean.setDblDrAmt(Double.parseDouble(obArr[1].toString()) / conversionRate);
								fieldList.add(objRptBean);
							}
							}else if(openingbal!=0){
								clsCreditorOutStandingReportBean objRptBean = new clsCreditorOutStandingReportBean();
								objRptBean.setDblOpnAmt(openingbal);
								objRptBean.setStrDebtorCode(sunCrCode);
								objRptBean.setStrDebtorName(sunCrName);
								objRptBean.setDblCrAmt(0);
								objRptBean.setDblDrAmt(0);
								fieldList.add(objRptBean);
							}

						} catch (Exception e) {
							// TODO: handle exception
						}

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
				hm.put("dteFromDate", fromDate);
				hm.put("dteToDate", toDate);

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
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	@SuppressWarnings("rawtypes")
	public int funProcessWebBookCreditorTrailBal(String acCode, String detorCretditorCode, String fromDate, String toDate, String clientCode, String userCode, String propertyCode, HttpServletRequest req, HttpServletResponse resp) {
		objGlobalFunctions.funDeleteAndInsertWebBookLedgerTable(clientCode, userCode, propertyCode);
		// jv
		if (detorCretditorCode == "C00000040") {
			System.out.print(detorCretditorCode);
		}
		String sql = " SELECT DATE(a.dteVouchDate),a.strVouchNo,'JV'  ,c.strBillNo , DATE(c.dteInvoiceDate) , " + " b.dblDrAmt ,b.dblCrAmt ,b.dblDrAmt - b.dblCrAmt ,'Cr','','1','" + userCode + "','" + propertyCode + "','" + clientCode + "' " 
		        + " FROM tbljvhd a, tbljvdtl b,tbljvdebtordtl c "
				+ " WHERE a.strVouchNo=b.strVouchNo AND a.strVouchNo=c.strVouchNo " 
				+ " AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " 
				+ " AND b.strAccountCode='" + acCode + "' and c.strDebtorCode='" + detorCretditorCode + "' "
				+ " AND a.strPropertyCode=b.strPropertyCode AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' ";
		List listjv = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		if (listjv.size() > 0) {
			for (int i = 0; i < listjv.size(); i++) {
				clsLedgerSummaryModel objSummaryledger = new clsLedgerSummaryModel();
				Object[] objArr = (Object[]) listjv.get(i);
				objSummaryledger.setDteVochDate(objArr[0].toString());
				objSummaryledger.setStrVoucherNo(objArr[1].toString());
				objSummaryledger.setStrTransType(objArr[2].toString());
				objSummaryledger.setStrChequeBillNo(objArr[3].toString());
				objSummaryledger.setDteBillDate(objArr[4].toString());
				objSummaryledger.setDblDebitAmt(Double.parseDouble(objArr[5].toString()));
				objSummaryledger.setDblCreditAmt(Double.parseDouble(objArr[6].toString()));
				;
				objSummaryledger.setDblBalanceAmt(Double.parseDouble(objArr[7].toString()));
				objSummaryledger.setStrBalCrDr(objArr[8].toString());
				objSummaryledger.setStrNarration(objArr[9].toString());
				objSummaryledger.setStrTransTypeForOrderBy(objArr[10].toString());
				objSummaryledger.setStrClientCode(clientCode);
				objSummaryledger.setStrUserCode(userCode);
				objSummaryledger.setStrPropertyCode(propertyCode);

				objGlobalFunctionsService.funAddUpdateLedgerSummary(objSummaryledger);
			}

		}

		sql = " SELECT DATE(a.dteVouchDate) ,a.strVouchNo,'Payment', a.strChequeNo, DATE(a.dteChequeDate) ,b.dblDrAmt , b.dblCrAmt ,b.dblDrAmt - b.dblCrAmt ,'Dr','','2','" + userCode + "','" + propertyCode + "','" + clientCode + "' " 
			    + " FROM tblpaymenthd a, tblpaymentdtl b,tblpaymentdebtordtl c "
				+ " WHERE a.strVouchNo=b.strVouchNo and a.strVouchNo=c.strVouchNo "
			    + " AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " 
				+ " AND b.strAccountCode='" + acCode + "' and c.strDebtorCode='" + detorCretditorCode + "' AND a.strPropertyCode=b.strPropertyCode " 
			    + " AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "'  ";



		List listPay = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		if (listPay.size() > 0) {
			for (int i = 0; i < listPay.size(); i++) {
				clsLedgerSummaryModel objSummaryledger = new clsLedgerSummaryModel();
				Object[] objArr = (Object[]) listPay.get(i);
				objSummaryledger.setDteVochDate(objArr[0].toString());
				objSummaryledger.setStrVoucherNo(objArr[1].toString());
				objSummaryledger.setStrTransType(objArr[2].toString());
				objSummaryledger.setStrChequeBillNo(objArr[3].toString());
				objSummaryledger.setDteBillDate(objArr[4].toString());
				objSummaryledger.setDblDebitAmt(Double.parseDouble(objArr[5].toString()));
				objSummaryledger.setDblCreditAmt(Double.parseDouble(objArr[6].toString()));
				;
				objSummaryledger.setDblBalanceAmt(Double.parseDouble(objArr[7].toString()));
				objSummaryledger.setStrBalCrDr(objArr[8].toString());
				objSummaryledger.setStrNarration(objArr[9].toString());
				objSummaryledger.setStrTransTypeForOrderBy(objArr[10].toString());
				objSummaryledger.setStrClientCode(clientCode);
				objSummaryledger.setStrUserCode(userCode);
				objSummaryledger.setStrPropertyCode(propertyCode);

				objGlobalFunctionsService.funAddUpdateLedgerSummary(objSummaryledger);
			}
		}

		// receipt
		sql = "  SELECT DATE(a.dteVouchDate) ,a.strVouchNo,'Receipt', a.strChequeNo, " 
			    + " DATE(a.dteChequeDate) ,  b.dblDrAmt , b.dblCrAmt ,b.dblDrAmt - b.dblCrAmt ,b.strCrDr,'','3','" + userCode + "','" + propertyCode + "','" + clientCode + "' "
				+ " FROM tblreceipthd a, tblreceiptdtl b,tblreceiptdebtordtl c  WHERE a.strVouchNo=b.strVouchNo and a.strVouchNo=c.strVouchNo "
			    + " AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " and  b.strAccountCode='" + acCode + "'  and c.strDebtorCode='" + detorCretditorCode + "'  "
				+ " AND a.strPropertyCode=b.strPropertyCode " 
			    + " AND a.strPropertyCode='"+ propertyCode + "' AND a.strClientCode='" + clientCode + "'  ";
		List listRecept = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		if (listRecept.size() > 0) {
			for (int i = 0; i < listRecept.size(); i++) {
				clsLedgerSummaryModel objSummaryledger = new clsLedgerSummaryModel();
				Object[] objArr = (Object[]) listRecept.get(i);
				objSummaryledger.setDteVochDate(objArr[0].toString());
				objSummaryledger.setStrVoucherNo(objArr[1].toString());
				objSummaryledger.setStrTransType(objArr[2].toString());
				objSummaryledger.setStrChequeBillNo(objArr[3].toString());
				objSummaryledger.setDteBillDate(objArr[4].toString());
				objSummaryledger.setDblDebitAmt(Double.parseDouble(objArr[5].toString()));
				objSummaryledger.setDblCreditAmt(Double.parseDouble(objArr[6].toString()));
				;
				objSummaryledger.setDblBalanceAmt(Double.parseDouble(objArr[7].toString()));
				objSummaryledger.setStrBalCrDr(objArr[8].toString());
				objSummaryledger.setStrNarration(objArr[9].toString());
				objSummaryledger.setStrTransTypeForOrderBy(objArr[10].toString());
				objSummaryledger.setStrClientCode(clientCode);
				objSummaryledger.setStrUserCode(userCode);
				objSummaryledger.setStrPropertyCode(propertyCode);

				objGlobalFunctionsService.funAddUpdateLedgerSummary(objSummaryledger);
			}
		}

		return 1;

	}
	


}
