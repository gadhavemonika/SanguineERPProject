package com.sanguine.webbooks.controller;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.sanguine.base.service.intfBaseService;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webbooks.bean.clsCreditorOutStandingReportBean;
import com.sanguine.webbooks.bean.clsDebtorLedgerBean;
import com.sanguine.webbooks.model.clsLedgerSummaryModel;

@Controller
public class clsTrailBalanceFlashController {

	
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

	@RequestMapping(value = "/frmTrailBalanceFlash", method = RequestMethod.GET)
	public ModelAndView funOpenForm( Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		clsCreditorOutStandingReportBean objBean= new clsCreditorOutStandingReportBean();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propertyCode = request.getSession().getAttribute("propertyCode").toString();
		String sql="select strAccountCode from tblacmaster where strClientCode='"+clientCode+"' and strPropertyCode = '"+propertyCode+"' ";
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
			return new ModelAndView("frmTrailBalanceFlash_1", "command",objBean);
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmTrailBalanceFlash", "command", objBean);
		} else {
			return null;
		}

	}
	
	@RequestMapping(value = "/rptTrialBalanceFlash", method = RequestMethod.GET)
	private  @ResponseBody List funReport(@RequestParam("fromDat") String fromDate ,@RequestParam("toDat") String toDate,@RequestParam("GLCode") String glCode,@RequestParam("currency") String currencyCode, HttpServletResponse resp, HttpServletRequest req) 
	{
		objGlobal = new clsGlobalFunctions();
		ArrayList fieldList = new ArrayList();
		Connection con = objGlobal.funGetConnection(req);
		try {

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			
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

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String type = "PDF";
			String fd = fromDate.split("-")[0];
			String fm = fromDate.split("-")[1];
			String fy = fromDate.split("-")[2];

			String td = toDate.split("-")[0];
			String tm = toDate.split("-")[1];
			String ty = toDate.split("-")[2];

			String dteFromDate = fy + "-" + fm + "-" + fd;
			String dteToDate = ty + "-" + tm + "-" + td;
			
			String startDate = req.getSession().getAttribute("startDate").toString();

			String[] sp = startDate.split(" ");
			String[] spDate = sp[0].split("/");
			startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
			HashMap<String, Double> hmMap = new HashMap<String, Double>();

			String sql = " select b.strGroupCode,b.strGroupName,a.strAccountCode,a.strAccountName " + " from tblacmaster a,tblacgroupmaster b " + " where a.strGroupCode=b.strGroupCode and  a.strClientCode='" + clientCode + "' and a.strPropertyCode='" + propertyCode + "' ORDER by a.strGroupCode  ";

			List listAc = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
			{
				if (listAc.size() > 0) {
					for (int i = 0; i < listAc.size(); i++) {
						Object[] objArr = (Object[]) listAc.get(i);
						String acCode = objArr[2].toString();
						double openingbal = 0.00;
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

								funInsertCurrentAccountBal(acCode, startDate, newToDate, userCode, propertyCode, clientCode);
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
								sql = " select sum(a.dblDrAmt-a.dblCrAmt)  " + " from tblcurrentaccountbal a " + " where a.strAccountCode='" + acCode + "' and a.strUserCode='" + userCode + "' " + " and a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' group by a.strAccountCode ";

								List listOpBalOld = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
								{
									if (listOpBalOld.size() > 0) {
										openingbal = Double.parseDouble(listOpBalOld.get(0).toString()) / currValue;
									}
								}
								funInsertCurrentAccountBal(acCode, dteFromDate, dteToDate, userCode, propertyCode, clientCode);

								sql = " SELECT c.strGroupCode,c.strGroupName,a.strAccountCode,a.strAccountName, " + " ifnull(SUM(b.dblCrAmt),0.00), ifnull(SUM(b.dblDrAmt),0.00) " + " FROM tblacmaster a " + " left outer join tblcurrentaccountbal b on a.strAccountCode=b.strAccountCode AND b.strUserCode='" + userCode + "' AND b.strPropertyCode='" + propertyCode + "' AND b.strClientCode='"
										+ clientCode + "' " + " left outer join tblacgroupmaster c on  a.strGroupCode=c.strGroupCode " + " WHERE   a.strAccountCode='" + acCode + "' GROUP BY a.strAccountCode; ";

								List listMainBal = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
								
								
									if (listMainBal.size() > 0) {
										for (int j = 0; j < listMainBal.size(); j++) {
											clsCreditorOutStandingReportBean objRtpBean = new clsCreditorOutStandingReportBean();
											Object[] balArr = (Object[]) listMainBal.get(j);

											objRtpBean.setDblOpnAmt(openingbal);
											objRtpBean.setStrGroupCode(balArr[0].toString());
											objRtpBean.setStrGroupName(balArr[1].toString());
											objRtpBean.setStrAccountCode(balArr[2].toString());
											objRtpBean.setStrAccountName(balArr[3].toString());
											objRtpBean.setDblCrAmt(Double.parseDouble(balArr[4].toString()) * conversionRate );
											objRtpBean.setDblDrAmt(Double.parseDouble(balArr[5].toString()) * conversionRate);
											fieldList.add(objRtpBean);

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
										fieldList.add(objRtpBean);
									}
								

							} catch (Exception ex) {
								ex.printStackTrace();
							}
						} else {

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
							funInsertCurrentAccountBal(acCode, dteFromDate, dteToDate, userCode, propertyCode, clientCode);

							sql = " select c.strGroupCode,c.strGroupName,a.strAccountCode,a.strAccountName,sum(a.dblCrAmt),sum(a.dblDrAmt) " + " from tblcurrentaccountbal a,tblacmaster b,tblacgroupmaster c " + " where a.strAccountCode=b.strAccountCode  and b.strGroupCode=c.strGroupCode " + " and a.strAccountCode='" + acCode + "' and a.strUserCode='" + userCode + "' " + " and a.strPropertyCode='"
									+ propertyCode + "' and a.strClientCode='" + clientCode + "' group by a.strAccountCode ";

							List listMainBal = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
							{
								
								if (listMainBal.size() > 0) {
									for (int j = 0; j < listMainBal.size(); j++) {
										clsCreditorOutStandingReportBean objRtpBean = new clsCreditorOutStandingReportBean();
										Object[] balArr = (Object[]) listMainBal.get(j);

										objRtpBean.setDblOpnAmt(openingbal);
										objRtpBean.setStrGroupCode(balArr[0].toString());
										objRtpBean.setStrGroupName(balArr[1].toString());
										objRtpBean.setStrAccountCode(balArr[2].toString());
										objRtpBean.setStrAccountName(balArr[3].toString());
										objRtpBean.setDblCrAmt(Double.parseDouble(balArr[4].toString()) * conversionRate);
										objRtpBean.setDblDrAmt(Double.parseDouble(balArr[5].toString()) * conversionRate);
										fieldList.add(objRtpBean);

									}
								}
								else{
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
									fieldList.add(objRtpBean);
								}
							}
						}

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
		return fieldList;

	}

	public int funInsertCurrentAccountBal(String acCode, String fromDate, String toDate, String userCode, String propertyCode, String clientCode) {
		String sql = "";

		sql = " Delete from tblcurrentaccountbal where a.strUserCode='" + userCode + "' and a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' ";
		objGlobalFunctionsService.funDeleteWebBookCurrentAccountBal(clientCode, userCode, propertyCode);

		sql = " INSERT INTO tblcurrentaccountbal (strAccountCode, strAccountName,  dteBalDate,strDrCrCode, strTransecType, dblDrAmt,dblCrAmt,dblBalAmt,strUserCode, strPropertyCode, strClientCode)  " + " (SELECT b.strAccountCode,b.strAccountName,DATE_FORMAT(DATE(a.dteVouchDate),'%d-%m-%Y'),'','JV', sum(b.dblDrAmt),sum(b.dblCrAmt),sum(b.dblDrAmt - b.dblCrAmt),'" + userCode + "','" + propertyCode
				+ "','" + clientCode + "' " + " FROM tbljvhd a, tbljvdtl b " + " WHERE a.strVouchNo=b.strVouchNo AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " AND b.strAccountCode='" + acCode + "'  AND a.strPropertyCode=b.strPropertyCode " + " AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' group by b.strAccountCode ) ";

		objGlobalFunctionsService.funUpdateAllModule(sql, "sql");

		sql = " INSERT INTO tblcurrentaccountbal (strAccountCode, strAccountName,  dteBalDate,strDrCrCode, strTransecType, dblDrAmt,dblCrAmt,dblBalAmt,strUserCode, strPropertyCode, strClientCode)  " + " (SELECT b.strAccountCode,b.strAccountName,DATE_FORMAT(DATE(a.dteVouchDate),'%d-%m-%Y'),'','Payment', sum(b.dblDrAmt),sum(b.dblCrAmt),sum(b.dblDrAmt - b.dblCrAmt),'" + userCode + "','" + propertyCode
				+ "','" + clientCode + "' " + "  FROM tblpaymenthd a, tblpaymentdtl b " + " WHERE a.strVouchNo=b.strVouchNo AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " AND b.strAccountCode='" + acCode + "'  AND a.strPropertyCode=b.strPropertyCode " + " AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' group by b.strAccountCode ) ";

		objGlobalFunctionsService.funUpdateAllModule(sql, "sql");

		sql = " INSERT INTO tblcurrentaccountbal (strAccountCode, strAccountName,  dteBalDate,strDrCrCode, strTransecType, dblDrAmt,dblCrAmt,dblBalAmt,strUserCode, strPropertyCode, strClientCode)  " + " (SELECT b.strAccountCode,b.strAccountName,DATE_FORMAT(DATE(a.dteVouchDate),'%d-%m-%Y'),'','Receipt', sum(b.dblDrAmt),sum(b.dblCrAmt),sum(b.dblDrAmt - b.dblCrAmt),'" + userCode + "','" + propertyCode
				+ "','" + clientCode + "' " + " FROM tblreceipthd a, tblreceiptdtl b  " + " WHERE a.strVouchNo=b.strVouchNo AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " AND b.strAccountCode='" + acCode + "'  AND a.strPropertyCode=b.strPropertyCode " + " AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' group by b.strAccountCode ) ";

		objGlobalFunctionsService.funUpdateAllModule(sql, "sql");

		return 1;

	}
	
	@RequestMapping(value = "/frmGeneralTrialBalanceLedger", method = RequestMethod.GET)
	public ModelAndView funOpenFormViaTrialBalance(@ModelAttribute("command") clsDebtorLedgerBean objBean, BindingResult result, Map<String, Object> model,@RequestParam("formname") String formname ,@RequestParam("glCode") String glCode,@RequestParam("accountCode") String accountCode,
			@RequestParam("fromDat") String fromDat,@RequestParam("toDat") String toDat,@RequestParam("currency") String currency, HttpServletResponse resp, HttpServletRequest req) {
		
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		 
		objBean.setDteFromDate(fromDat);
		objBean.setDteToDate(toDat);
		objBean.setStrGLCode(glCode);
		objBean.setCurrency(currency);
		
		Map<String, String> hmCurrency = objCurrencyMasterService.funCurrencyListToDisplay(clientCode);
		if (hmCurrency.isEmpty()) {
			hmCurrency.put("", "");
		}
		
		model.put("currencyList", hmCurrency);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmGeneralLedger_1", "command",objBean);
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmGeneralLedger", "command", objBean);
		} else {
			return null;
		}

	}

}
