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
import com.sanguine.webbooks.model.clsCurrentAccountBalMaodel;

@Controller
public class clsBankBalanceReportController {

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
	@RequestMapping(value = "/frmBankBalanceReport", method = RequestMethod.GET)
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
			return new ModelAndView("frmBankBalanceReport_1", "command", new clsCreditorOutStandingReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmBankBalanceReport", "command", new clsCreditorOutStandingReportBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/rptBankBalanceReport", method = RequestMethod.GET)
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
			
//			double currValue = Double.parseDouble(req.getSession().getAttribute("currValue").toString());

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
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webbooks/rptBankBalanceReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			ArrayList bankBalList = new ArrayList();
			ArrayList cashBalList = new ArrayList();
			String startDate = req.getSession().getAttribute("startDate").toString();

			String[] sp = startDate.split(" ");
			String[] spDate = sp[0].split("/");
			startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
			HashMap<String, String> hmMap = new HashMap<String, String>();

			double total=0.0;
			String prevAcName="";
			String sql = " select  b.strGroupCode,ifNull(b.strGroupName,''),a.strAccountCode,a.strAccountName,left(a.strAccountCode,8) as groupAcCode "
					+ " from tblacmaster a,tblacgroupmaster b " 
					+ " where  a.strGroupCode=b.strGroupCode and a.strType='BANK' and  a.strClientCode='" + clientCode + "' and a.strPropertyCode='" + propertyCode + "' ";
			HashMap<String,clsCreditorOutStandingReportBean> hmTB=new HashMap<String,clsCreditorOutStandingReportBean>();
			List listAc = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
			{
				if (listAc.size() > 0) {
					for (int i = 0; i < listAc.size(); i++) {
						Object[] objArr = (Object[]) listAc.get(i);
						String acCode = objArr[2].toString();
						String groupAcCode = objArr[4].toString();
						double openingbal = 0.00;
						hmTB=new HashMap<String,clsCreditorOutStandingReportBean>();
						
						if(hmMap.containsKey(groupAcCode))
						{
							
						}
						else
						{
							hmMap.put(groupAcCode, objArr[1].toString());
							prevAcName=objArr[1].toString();
							clsCreditorOutStandingReportBean objRtpBean = new clsCreditorOutStandingReportBean();
							objRtpBean.setDblOpnAmt(0.0);
							objRtpBean.setStrGroupCode("");
							objRtpBean.setStrGroupName("");
							objRtpBean.setStrAccountCode(acCode);
							objRtpBean.setStrAccountName(objArr[3].toString());
							objRtpBean.setDblCrAmt(0.0);
							objRtpBean.setDblDrAmt(0.0);
							objRtpBean.setDblBalAmt(0.0);
							objRtpBean.setStrGroupCategory(groupAcCode);
							objRtpBean.setStrCurrency("Total "+prevAcName);
							objRtpBean.setStrName("");
							
							bankBalList.add(objRtpBean);
						}
						
						if (!startDate.equals(dteFromDate)) {
							String tempFromDate = dteFromDate.split("-")[2] + "-" + dteFromDate.split("-")[1] + "-" + dteFromDate.split("-")[0];
							SimpleDateFormat obj = new SimpleDateFormat("dd-MM-yyyy");
							Date dt1;
							try {
								dt1 = obj.parse(tempFromDate);
								GregorianCalendar cal = new GregorianCalendar();
								cal.setTime(dt1);
								cal.add(Calendar.DATE, -1);
								String newToDate = (cal.getTime().getYear() + 1900) + "-" + (cal.getTime().getMonth() + 1) + "-" + (cal.getTime().getDate());

								funInsertCurrentAccountBal(acCode, startDate, newToDate, userCode, propertyCode, clientCode,hmTB);

								
								 sbSql.setLength(0);
								 sbSql.append(" select IF(a.strCrDr='Dr',a.intOpeningBal,0) dblDebitAmt, IF(a.strCrDr='Cr',a.intOpeningBal,0) dblCreditAmt, (IF(a.strCrDr='Dr',a.intOpeningBal,0) - IF(a.strCrDr='Cr',a.intOpeningBal,0)) dblBalanceAmt  from tblacmaster a where a.strAccountCode='"+acCode+"' "
										+" and a.strClientCode='"+clientCode+"' and a.strPropertyCode='" + propertyCode + "'");
						
									
								List list=new ArrayList();
								try {
									list = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
									if(list.size()>0)
									{
										Object[] obj1 = (Object[]) list.get(0);
										openingbal+=Double.parseDouble(obj1[0].toString())-Double.parseDouble(obj1[1].toString());
									}
									} catch (Exception e) {
										// TODO Auto-generated catch block
									}
									if(!hmTB.isEmpty()){
										for(Map.Entry<String, clsCreditorOutStandingReportBean> entry:hmTB.entrySet())
										{
											clsCreditorOutStandingReportBean objHmBean =entry.getValue();
											objHmBean.setStrGroupCode(objArr[0].toString());
											objHmBean.setStrGroupName(objArr[1].toString());
											openingbal+=objHmBean.getDblDrAmt()-objHmBean.getDblCrAmt();
											objHmBean.setDblOpnAmt(openingbal);
										}
										}
									hmTB=new HashMap<String,clsCreditorOutStandingReportBean>();
									funInsertCurrentAccountBal(acCode, dteFromDate, dteToDate, userCode, propertyCode, clientCode,hmTB);

								    if(!hmTB.isEmpty()){
									for(Map.Entry<String, clsCreditorOutStandingReportBean> entry:hmTB.entrySet())
									{
										clsCreditorOutStandingReportBean objHmBean =entry.getValue();
										objHmBean.setStrGroupCode(objArr[0].toString());
										objHmBean.setStrGroupName(objArr[1].toString());
										objHmBean.setDblOpnAmt(openingbal);
										objHmBean.setDblCrAmt(objHmBean.getDblCrAmt());
										objHmBean.setDblDrAmt(objHmBean.getDblDrAmt());
										objHmBean.setDblBalAmt(openingbal + objHmBean.getDblDrAmt() - objHmBean.getDblCrAmt());
										objHmBean.setStrGroupCategory(groupAcCode);
										objHmBean.setStrName(prevAcName);
										if(hmMap.containsKey(groupAcCode))
										{
											objHmBean.setStrCurrency("Total "+prevAcName);
										}
										
										total = total + openingbal;
										bankBalList.add(objHmBean);
										
									}
									}else{
										 acCode = objArr[2].toString();
										 String acName = objArr[3].toString();
										 String groupCode = objArr[0].toString();
										 String groupName = objArr[1].toString();
										 
										clsCreditorOutStandingReportBean objRtpBean = new clsCreditorOutStandingReportBean();
										

										objRtpBean.setDblOpnAmt(openingbal);
										objRtpBean.setStrGroupCode(groupCode);
										objRtpBean.setStrGroupName(groupName);
										objRtpBean.setStrAccountCode(acCode);
										objRtpBean.setStrAccountName(acName);
										objRtpBean.setDblCrAmt(0);
										objRtpBean.setDblDrAmt(0);
										objRtpBean.setDblBalAmt(openingbal + 0 - 0);
										objRtpBean.setStrGroupCategory(groupAcCode);
										objRtpBean.setStrName(prevAcName);
										if(hmMap.containsKey(groupAcCode))
										{
											objRtpBean.setStrCurrency("Total "+prevAcName);
										}
										
										total = total + openingbal;
										bankBalList.add(objRtpBean);
//										if(openingbal!=0.0)
//										{
//											bankBalList.add(objRtpBean);
//										}
										
									}
							
							} 
							catch (Exception ex) 
							{
								ex.printStackTrace();
							}
						} 
						else 
						{
							
							
							 sbSql.setLength(0);
							 sbSql.append(" select IF(a.strCrDr='Dr',a.intOpeningBal,0) dblDebitAmt, IF(a.strCrDr='Cr',a.intOpeningBal,0) dblCreditAmt, (IF(a.strCrDr='Dr',a.intOpeningBal,0) - IF(a.strCrDr='Cr',a.intOpeningBal,0)) dblBalanceAmt  from tblacmaster a where a.strAccountCode='"+acCode+"' "
									+" and a.strClientCode='"+clientCode+"' and a.strPropertyCode='" + propertyCode + "'");
							 List list=new ArrayList();
								try 
								{
									list = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
									if(list.size()>0)
									{
										Object[] obj = (Object[]) list.get(0);
										openingbal+=Double.parseDouble(obj[0].toString())-Double.parseDouble(obj[1].toString());
									}
								} 
								catch (Exception e) 
								{
									// TODO Auto-generated catch block
								}
							

							funInsertCurrentAccountBal(acCode, dteFromDate, dteToDate, userCode, propertyCode, clientCode,hmTB);

							if(!hmTB.isEmpty())
							{
								for(Map.Entry<String, clsCreditorOutStandingReportBean> entry:hmTB.entrySet())
								{
									clsCreditorOutStandingReportBean objHmBean =entry.getValue();
									objHmBean.setStrGroupCode(objArr[0].toString());
									objHmBean.setStrGroupName(objArr[1].toString());
									objHmBean.setDblOpnAmt(openingbal);
									objHmBean.setDblCrAmt(objHmBean.getDblCrAmt());
									objHmBean.setDblDrAmt(objHmBean.getDblDrAmt());
									objHmBean.setDblBalAmt(openingbal + objHmBean.getDblDrAmt() - objHmBean.getDblCrAmt());
									objHmBean.setStrGroupCategory(groupAcCode);
									objHmBean.setStrName(prevAcName);
									if(hmMap.containsKey(groupAcCode))
									{
										objHmBean.setStrCurrency("Total "+prevAcName);
									}
									
									total = total + openingbal;
									bankBalList.add(objHmBean);
								
								}
							}
							else
							{
									 acCode = objArr[2].toString();
									 String acName = objArr[3].toString();
									 String groupCode = objArr[0].toString();
									 String groupName = objArr[1].toString();
									 
									clsCreditorOutStandingReportBean objRtpBean = new clsCreditorOutStandingReportBean();
									

									objRtpBean.setDblOpnAmt(openingbal);
									objRtpBean.setStrGroupCode(groupCode);
									objRtpBean.setStrGroupName(groupName);
									objRtpBean.setStrAccountCode(acCode);
									objRtpBean.setStrAccountName(acName);
									objRtpBean.setDblCrAmt(0);
									objRtpBean.setDblDrAmt(0);
									objRtpBean.setDblBalAmt(openingbal + 0 - 0);
									objRtpBean.setStrGroupCategory(groupAcCode);
									objRtpBean.setStrName(prevAcName);
									if(hmMap.containsKey(groupAcCode))
									{
										objRtpBean.setStrCurrency("Total "+prevAcName);
									}
									
									total = total + openingbal;
									bankBalList.add(objRtpBean);
//									if(openingbal!=0.0)
//									{
//										bankBalList.add(objRtpBean);
//									}
									
							}
						}

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
			hm.put("bankBalList", bankBalList);
			hm.put("cashBalList", cashBalList);
			hm.put("total",total);

			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(bankBalList);
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

	public int funInsertCurrentAccountBal(String acCode, String fromDate, String toDate, String userCode, String propertyCode, String clientCode,HashMap<String,clsCreditorOutStandingReportBean> hmTB) throws Exception {
		String sql = "";

//		sql = " Delete from tblcurrentaccountbal where a.strUserCode='" + userCode + "' and a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' ";
//		objGlobalFunctionsService.funDeleteWebBookCurrentAccountBal(clientCode, userCode, propertyCode);
//
//		sql = " INSERT INTO tblcurrentaccountbal (strAccountCode, strAccountName,  dteBalDate,strDrCrCode, strTransecType, dblDrAmt,dblCrAmt,dblBalAmt,strUserCode, strPropertyCode, strClientCode)  " + " (SELECT b.strAccountCode,b.strAccountName,DATE_FORMAT(DATE(a.dteVouchDate),'%d-%m-%Y'),'','JV', sum(b.dblDrAmt),sum(b.dblCrAmt),sum(b.dblDrAmt - b.dblCrAmt),'" + userCode + "','" + propertyCode
//				+ "','" + clientCode + "' " + " FROM tbljvhd a, tbljvdtl b " + " WHERE a.strVouchNo=b.strVouchNo AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " AND b.strAccountCode='" + acCode + "'  AND a.strPropertyCode=b.strPropertyCode " + " AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' group by b.strAccountCode ) ";
//
//		objGlobalFunctionsService.funUpdateAllModule(sql, "sql");
//
//		sql = " INSERT INTO tblcurrentaccountbal (strAccountCode, strAccountName,  dteBalDate,strDrCrCode, strTransecType, dblDrAmt,dblCrAmt,dblBalAmt,strUserCode, strPropertyCode, strClientCode)  " + " (SELECT b.strAccountCode,b.strAccountName,DATE_FORMAT(DATE(a.dteVouchDate),'%d-%m-%Y'),'','Payment', sum(b.dblDrAmt),sum(b.dblCrAmt),sum(b.dblDrAmt - b.dblCrAmt),'" + userCode + "','" + propertyCode
//				+ "','" + clientCode + "' " + "  FROM tblpaymenthd a, tblpaymentdtl b " + " WHERE a.strVouchNo=b.strVouchNo AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " AND b.strAccountCode='" + acCode + "'  AND a.strPropertyCode=b.strPropertyCode " + " AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' group by b.strAccountCode ) ";
//
//		objGlobalFunctionsService.funUpdateAllModule(sql, "sql");
//
//		sql = " INSERT INTO tblcurrentaccountbal (strAccountCode, strAccountName,  dteBalDate,strDrCrCode, strTransecType, dblDrAmt,dblCrAmt,dblBalAmt,strUserCode, strPropertyCode, strClientCode)  " + " (SELECT b.strAccountCode,b.strAccountName,DATE_FORMAT(DATE(a.dteVouchDate),'%d-%m-%Y'),'','Receipt', sum(b.dblDrAmt),sum(b.dblCrAmt),sum(b.dblDrAmt - b.dblCrAmt),'" + userCode + "','" + propertyCode
//				+ "','" + clientCode + "' " + " FROM tblreceipthd a, tblreceiptdtl b  " + " WHERE a.strVouchNo=b.strVouchNo AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " AND b.strAccountCode='" + acCode + "'  AND a.strPropertyCode=b.strPropertyCode " + " AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' group by b.strAccountCode ) ";
//
//		objGlobalFunctionsService.funUpdateAllModule(sql, "sql");
//		
//		
		
//		sbSql.append("select c.strGroupCode,c.strGroupName, a.strAccountCode, a.strAccountName,sum(ifnull(a.dblDrAmt,0)) , sum(ifnull(a.dblCrAmt,0))  from "
//		   +" (select b.strAccountCode, b.strAccountName, b.dblDrAmt , b.dblCrAmt from tbljvhd a, tbljvdtl b where a.strVouchNo = b.strVouchNo and a.dteVouchDate BETWEEN '" + fromDate + "' AND '" + toDate + "'  AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' AND b.strAccountCode='" + acCode + "'  " 
//		   +" union all "
//		   +" select b.strAccountCode, b.strAccountName, b.dblDrAmt , b.dblCrAmt from tblpaymenthd a, tblpaymentdtl b where a.strVouchNo = b.strVouchNo  and a.dteVouchDate  BETWEEN '" + fromDate + "' AND '" + toDate + "'  AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' AND b.strAccountCode='" + acCode + "'  "
//		   +" union all "
//		   +" select b.strAccountCode, b.strAccountName, b.dblDrAmt , b.dblCrAmt from tblreceipthd a, tblreceiptdtl b where a.strVouchNo = b.strVouchNo and a.dteVouchDate  BETWEEN '" + fromDate + "' AND '" + toDate + "'   AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' AND b.strAccountCode='" + acCode + "'  ) a, tblacmaster b , tblacgroupmaster c "
//		   +" where a.strAccountCode =  b.strAccountCode and b.strGroupCode = c.strGroupCode "
//		   +" Group By c.strGroupCode,c.strGroupName, a.strAccountCode, a.strAccountName "
//		   +" Having sum(a.dblDrAmt)>0 Or sum(a.dblCrAmt) > 0 " ); 
		
		
		StringBuilder sbSql = new StringBuilder(); 
		sbSql.append(" SELECT b.strAccountCode,b.strAccountName,sum(b.dblDrAmt),sum(b.dblCrAmt),sum(b.dblDrAmt - b.dblCrAmt),'" + userCode + "','" + propertyCode+ "',"
				+ " '" + clientCode + "' " + " FROM tbljvhd a, tbljvdtl b " + " WHERE a.strVouchNo=b.strVouchNo AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " AND b.strAccountCode='" + acCode + "'  AND a.strPropertyCode=b.strPropertyCode " + " AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' group by b.strAccountCode ") ;

		
		List listJvAmt = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
		if (listJvAmt  != null && listJvAmt .size() > 0) {
			for (int j = 0; j < listJvAmt .size(); j++) {
				clsCreditorOutStandingReportBean objOutStBean=new clsCreditorOutStandingReportBean();
				Object[] arrObj = (Object[]) listJvAmt .get(j);
				if(hmTB.containsKey(arrObj[0].toString()))
				{
				    objOutStBean=hmTB.get(arrObj[0].toString());
					objOutStBean.setDblDrAmt(objOutStBean.getDblDrAmt()+(Double.parseDouble(arrObj[2].toString())));
					objOutStBean.setDblCrAmt(objOutStBean.getDblCrAmt()+(Double.parseDouble(arrObj[3].toString())));
				}else{
				objOutStBean.setStrAccountCode(arrObj[0].toString());
				objOutStBean.setStrAccountName(arrObj[1].toString());
				objOutStBean.setDblDrAmt(Double.parseDouble(arrObj[2].toString()));
				objOutStBean.setDblCrAmt(Double.parseDouble(arrObj[3].toString()));
				
				}
				hmTB.put(arrObj[0].toString(), objOutStBean);
			}
			}
		sbSql.setLength(0);
		sbSql.append(" SELECT b.strAccountCode,b.strAccountName, sum(b.dblDrAmt),sum(b.dblCrAmt),sum(b.dblDrAmt - b.dblCrAmt),'" + userCode + "','" + propertyCode
				+ "','" + clientCode + "' " + "  FROM tblpaymenthd a, tblpaymentdtl b " + " WHERE a.strVouchNo=b.strVouchNo AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " AND b.strAccountCode='" + acCode + "'  AND a.strPropertyCode=b.strPropertyCode " + " AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' group by b.strAccountCode ") ;

		List listPayAmt = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
		if (listPayAmt  != null && listPayAmt .size() > 0) {
			for (int j = 0; j < listPayAmt .size(); j++) {
				clsCreditorOutStandingReportBean objOutStBean=new clsCreditorOutStandingReportBean();
				Object[] arrObj = (Object[]) listPayAmt .get(j);
				if(hmTB.containsKey(arrObj[0].toString()))
				{
				    objOutStBean=hmTB.get(arrObj[0].toString());
					objOutStBean.setDblDrAmt(objOutStBean.getDblDrAmt()+(Double.parseDouble(arrObj[2].toString())));
					objOutStBean.setDblCrAmt(objOutStBean.getDblCrAmt()+(Double.parseDouble(arrObj[3].toString())));
				}else{
				objOutStBean.setStrAccountCode(arrObj[0].toString());
				objOutStBean.setStrAccountName(arrObj[1].toString());
				objOutStBean.setDblDrAmt(Double.parseDouble(arrObj[2].toString()));
				objOutStBean.setDblCrAmt(Double.parseDouble(arrObj[3].toString()));
				
				}
				hmTB.put(arrObj[0].toString(), objOutStBean);
			}
			}
		sbSql.setLength(0);
		sbSql.append("SELECT b.strAccountCode,b.strAccountName, sum(b.dblDrAmt),sum(b.dblCrAmt),sum(b.dblDrAmt - b.dblCrAmt),'" + userCode + "','" + propertyCode
				+ "','" + clientCode + "' " + " FROM tblreceipthd a, tblreceiptdtl b  " + " WHERE a.strVouchNo=b.strVouchNo AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " AND b.strAccountCode='" + acCode + "'  AND a.strPropertyCode=b.strPropertyCode " + " AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' group by b.strAccountCode ") ;

		
		List listRecAmt = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
		if (listRecAmt  != null && listRecAmt .size() > 0) {
			for (int j = 0; j < listRecAmt .size(); j++) {
				clsCreditorOutStandingReportBean objOutStBean=new clsCreditorOutStandingReportBean();
				Object[] arrObj = (Object[]) listRecAmt .get(j);
				if(hmTB.containsKey(arrObj[0].toString()))
				{
				    objOutStBean=hmTB.get(arrObj[0].toString());
					objOutStBean.setDblDrAmt(objOutStBean.getDblDrAmt()+(Double.parseDouble(arrObj[2].toString())));
					objOutStBean.setDblCrAmt(objOutStBean.getDblCrAmt()+(Double.parseDouble(arrObj[3].toString())));
				}else{
				objOutStBean.setStrAccountCode(arrObj[0].toString());
				objOutStBean.setStrAccountName(arrObj[1].toString());
				objOutStBean.setDblDrAmt(Double.parseDouble(arrObj[2].toString()));
				objOutStBean.setDblCrAmt(Double.parseDouble(arrObj[3].toString()));
				
				}
				hmTB.put(arrObj[0].toString(), objOutStBean);
			}
			}
		
		return 1;

	}

}
