package com.sanguine.webbooks.controller;

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
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webbooks.bean.clsCreditorOutStandingReportBean;
import com.sanguine.webbooks.bean.clsIncomeStmtReportBean;

@Controller
public class clsBalanceSheetController {

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

	
		@RequestMapping(value = "/frmBalanceSheet", method = RequestMethod.GET)
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
				return new ModelAndView("frmBalanceSheet_1", "command", new clsCreditorOutStandingReportBean());
			}
			else if ("1".equalsIgnoreCase(urlHits))
			{
				return new ModelAndView("frmBalanceSheet", "command", new clsCreditorOutStandingReportBean());
			}
			else
			{
				return null;
			}

		}
		
		
		@RequestMapping(value = "/rptBalanceSheet1", method = RequestMethod.GET)
		private void funReport(@ModelAttribute("command") clsCreditorOutStandingReportBean objBean, HttpServletResponse resp, HttpServletRequest req)
		{
			objGlobal = new clsGlobalFunctions();
			Connection con = objGlobal.funGetConnection(req);
			try
			{

				String clientCode = req.getSession().getAttribute("clientCode").toString();
				String companyName = req.getSession().getAttribute("companyName").toString();
				String userCode = req.getSession().getAttribute("usercode").toString();
				String propertyCode = req.getSession().getAttribute("propertyCode").toString();
				String currencyCode = objBean.getStrCurrency();
				double conversionRate = 1;
				String webStockDB = req.getSession().getAttribute("WebStockDB").toString();
				StringBuilder sbSql = new StringBuilder();
				sbSql.append("select dblConvToBaseCurr from " + webStockDB + ".tblcurrencymaster where strCurrencyCode='" + currencyCode + "' and strClientCode='" + clientCode + "' ");
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

				String fd = fromDate.split("-")[0];
				String fm = fromDate.split("-")[1];
				String fy = fromDate.split("-")[2];

				String td = toDate.split("-")[0];
				String tm = toDate.split("-")[1];
				String ty = toDate.split("-")[2];

				String dteFromDate = fy + "-" + fm + "-" + fd;
				String dteToDate = ty + "-" + tm + "-" + td;
				String reportName = servletContext.getRealPath("/WEB-INF/reports/webbooks/rptBalanceSheet.jrxml");
				String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

				ArrayList dataList = new ArrayList();

			

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

				
				List<clsIncomeStmtReportBean> listOfBalancesheet = new ArrayList<clsIncomeStmtReportBean>();
			
				 /* Income statement
				 * 
				 * 
				 */

				List <String>listACC=new ArrayList<String>();
				
				listACC.add("1000-001-00");
				
				listACC.add("1000-002-00");
				listACC.add("1000-003-00");
				listACC.add("1000-004-00");
				listACC.add("1000-005-00");
				listACC.add("1000-006-00");
				
				///NON Current Asset
				listACC.add("1001-011-01");
				listACC.add("1001-011-00");
				listACC.add("1001-010-19");
				listACC.add("1001-010-18");
				listACC.add("1001-010-17");
				listACC.add("1001-011-02");
				listACC.add("1001-011-03");

				listACC.add("1001-011-04");
				listACC.add("1001-011-05");
				listACC.add("1001-011-06");
				listACC.add("1001-011-07");
				listACC.add("1001-011-08");
				listACC.add("1001-010-16");
				listACC.add("1001-010-14");
				listACC.add("1001-010-13");
				listACC.add("1001-010-01");
				listACC.add("1001-010-02");
				listACC.add("1001-010-03");

				
				listACC.add("1001-010-04");
				listACC.add("1001-010-05");
				listACC.add("1001-010-06");
				listACC.add("1001-010-07");
				listACC.add("1001-010-08");
				listACC.add("1001-010-09");
				listACC.add("1001-010-10");
				listACC.add("1001-010-11");
				listACC.add("1001-010-12");
				listACC.add("1001-011-09");
				listACC.add("1001-011-10");
				listACC.add("1001-011-27");
				listACC.add("1001-011-28");
				listACC.add("1001-011-29");
				listACC.add("1001-011-30");
				listACC.add("1001-011-31");
				listACC.add("1001-011-32");
				listACC.add("1001-011-33");
				listACC.add("1001-011-34");
				listACC.add("1001-011-35");
				listACC.add("1001-011-36");
				listACC.add("1001-011-37");
				listACC.add("1001-011-38");
				listACC.add("1001-011-26");
				listACC.add("1001-011-25");
				listACC.add("1001-011-11");
				listACC.add("1001-011-13");
				listACC.add("1001-011-14");
				listACC.add("1001-011-15");
				listACC.add("1001-011-16");
				listACC.add("1001-011-17");

				listACC.add("1001-011-18");
				listACC.add("1001-011-19");
				listACC.add("1001-011-21");
				listACC.add("1001-011-22");
				listACC.add("1001-011-23");
				listACC.add("1001-011-24");
				listACC.add("1001-011-39");
				listACC.add("1001-010-00");
				listACC.add("1001-001-00");
				listACC.add("1001-003-01");
				listACC.add("1001-003-02");
				listACC.add("1001-003-03");
				listACC.add("1001-003-04");
				listACC.add("1001-003-05");
				listACC.add("1001-003-06");
				listACC.add("1001-003-07");
				listACC.add("1001-003-08");
				listACC.add("1001-003-09");
				listACC.add("1001-003-10");
				listACC.add("1001-003-11");
				listACC.add("1001-004-00");
				listACC.add("1001-003-00");
				listACC.add("1001-002-10");
				listACC.add("1001-001-01");
				listACC.add("1001-001-02");
				listACC.add("1001-002-00");
				listACC.add("1001-002-01");
				listACC.add("1001-002-02");
				listACC.add("1001-002-03");
				listACC.add("1001-002-04");
				listACC.add("1001-002-05");
				listACC.add("1001-002-06");
				listACC.add("1001-002-07");
				listACC.add("1001-002-08");
				listACC.add("1001-002-09");
				listACC.add("1001-004-01");
				listACC.add("1001-004-02");
				listACC.add("1001-005-00");
				listACC.add("1001-007-03");

				listACC.add("1001-007-04");
				listACC.add("1001-007-05");
				listACC.add("1001-007-06");
				listACC.add("1001-007-07");

				listACC.add("1001-008-00");
				listACC.add("1001-008-01");
				listACC.add("1001-008-02");
				listACC.add("1001-008-03");
				listACC.add("1001-008-04");
				listACC.add("1001-009-00");
				listACC.add("1001-009-02");
				listACC.add("1001-007-02");
				listACC.add("1001-006-00");
				listACC.add("1001-005-01");
				listACC.add("1001-005-02");
				listACC.add("1001-005-03");
				listACC.add("1001-006-012");
				listACC.add("1001-006-02");
				listACC.add("1001-006-03");
				listACC.add("1001-006-04");
				listACC.add("1001-006-05");
				listACC.add("1001-007-00");
				listACC.add("1001-010-15");

//////Current Libiality
				
				listACC.add("1006-003-02");
				listACC.add("1006-003-01");
				listACC.add("1006-003-00");
				listACC.add("1006-002-21");
				listACC.add("1006-002-20");
				listACC.add("1006-002-19");
				listACC.add("1006-002-14");
				listACC.add("1006-004-00");
				listACC.add("1006-004-01");
				listACC.add("1006-004-02");
				listACC.add("1006-004-03");
				listACC.add("1006-004-04");
				listACC.add("1006-005-00");
				listACC.add("1006-005-01");
				listACC.add("1006-005-02");


				listACC.add("1006-005-03");
				listACC.add("1006-002-13");
				listACC.add("1006-002-12");
				listACC.add("1006-000-00");
				listACC.add("1006-001-00");
				listACC.add("1006-001-01");
				listACC.add("1006-001-02");
				listACC.add("1006-002-00");
				listACC.add("1006-002-01");
				listACC.add("1006-002-02");
				listACC.add("1006-002-03");
				listACC.add("1006-002-04");
				listACC.add("1006-002-11");
				listACC.add("1006-002-10");
				listACC.add("1006-002-09");
				listACC.add("1006-002-08");
				listACC.add("1006-002-07");
				listACC.add("1006-002-06");
				listACC.add("1006-002-05");

				
				BigDecimal totalSalesAmt=new BigDecimal(0);
				BigDecimal totalOtherExpenses=new BigDecimal(0);
				BigDecimal totalOtherIncome=new BigDecimal(0);
				BigDecimal totalExpenseAmt=new BigDecimal(0);
				
				Map<String,clsIncomeStmtReportBean> hmSalesIncStmt = new HashMap<String,clsIncomeStmtReportBean>();
				String startDate = req.getSession().getAttribute("startDate").toString();

				String[] sp = startDate.split(" ");
				String[] spDate = sp[0].split("/");
				startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
				int cnt=1;
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
				
				// Sale JV
				funCalculateBalanceSheetListAccountCode("ASSETS", "tbljvhd", "tbljvdtl", startDate, newToDate,cnt , clientCode, sbSql, hmSalesIncStmt,listACC,propertyCode);
				cnt++;
				// Sale Receipt
				funCalculateBalanceSheetListAccountCode("ASSETS", "tblreceipthd", "tblreceiptdtl", startDate, newToDate,cnt , clientCode, sbSql, hmSalesIncStmt,listACC,propertyCode);
							
				// Sale Payment
				funCalculateBalanceSheetListAccountCode("ASSETS", "tblpaymenthd", "tblpaymentdtl", startDate, newToDate, cnt, clientCode, sbSql, hmSalesIncStmt,listACC,propertyCode);	
					
					}catch(Exception e)
					{
						
					}
				}
				

				// Sale JV
				funCalculateBalanceSheetListAccountCode("ASSETS", "tbljvhd", "tbljvdtl", dteFromDate, dteToDate, cnt, clientCode, sbSql, hmSalesIncStmt,listACC,propertyCode);
				cnt++;
				// Sale Receipt
				funCalculateBalanceSheetListAccountCode("ASSETS", "tblreceipthd", "tblreceiptdtl", dteFromDate, dteToDate, cnt, clientCode, sbSql, hmSalesIncStmt,listACC,propertyCode);
							
				// Sale Payment
				funCalculateBalanceSheetListAccountCode("ASSETS", "tblpaymenthd", "tblpaymentdtl", dteFromDate, dteToDate, cnt, clientCode, sbSql, hmSalesIncStmt,listACC,propertyCode);	
					
				
				
				
//				
//				// Sale JV
//					funCalculateBalanceSheet("ASSETS", "tbljvhd", "tbljvdtl", dteFromDate, dteToDate, "dblCrAmt", clientCode, sbSql, hmSalesIncStmt);
//				
//				// Sale Receipt
//					funCalculateBalanceSheet("ASSETS", "tblreceipthd", "tblreceiptdtl", dteFromDate, dteToDate, "dblCrAmt", clientCode, sbSql, hmSalesIncStmt);
//							
//				// Sale Payment
//					funCalculateBalanceSheet("ASSETS", "tblpaymenthd", "tblpaymentdtl", dteFromDate, dteToDate, "dblCrAmt", clientCode, sbSql, hmSalesIncStmt);	
//					
				listACC=new ArrayList<String>();
				
				Map<String,clsIncomeStmtReportBean> hmOtherExpensesIncStmt = new HashMap<String,clsIncomeStmtReportBean>();
				

//				// LIABILITY JV
//					funCalculateBalanceSheet("LIABILITY", "tbljvhd", "tbljvdtl", dteFromDate, dteToDate, "dblDrAmt", clientCode, sbSql, hmOtherExpensesIncStmt);
//						
//				// LIABILITY 
//					funCalculateBalanceSheet("LIABILITY", "tblreceipthd", "tblreceiptdtl", dteFromDate, dteToDate, "dblDrAmt", clientCode, sbSql, hmOtherExpensesIncStmt);
//							
//				//LIABILITY	
//					funCalculateBalanceSheet("LIABILITY", "tblpaymenthd", "tblpaymentdtl", dteFromDate, dteToDate, "dblDrAmt", clientCode, sbSql, hmOtherExpensesIncStmt);
							
				// Calculate Gross Profit = Total Income (Sales) - Total Other Expenses (COG)	
					
				for(Map.Entry<String, clsIncomeStmtReportBean> entry:hmSalesIncStmt.entrySet())
				{
					totalSalesAmt=totalSalesAmt.add(entry.getValue().getDblValue());
					listOfBalancesheet.add(entry.getValue());
				}
					
				for(Map.Entry<String, clsIncomeStmtReportBean> entry:hmOtherExpensesIncStmt.entrySet())
				{
					totalOtherExpenses=totalOtherExpenses.add(entry.getValue().getDblValue());
					listOfBalancesheet.add(entry.getValue());
				}
			   
				listACC=new ArrayList<String>();
				
				listACC.add("1007-001-00");
				listACC.add("1007-002-00");
				
				listACC.add("1008-001-00");
				listACC.add("1008-002-00");
				Map<String,clsIncomeStmtReportBean> hmShareCapital= new HashMap<String,clsIncomeStmtReportBean>();	
				
				 cnt=1;
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

			 //  CAPITAL AND RESERVES Receipt 
			    funCalculateBalanceSheetListAccountCode("CAPITAL AND RESERVES", "tbljvhd", "tbljvdtl", startDate, newToDate, cnt, clientCode, sbSql, hmShareCapital,listACC,propertyCode);
			    cnt++;
			   //  CAPITAL AND RESERVES Receipt 
				funCalculateBalanceSheetListAccountCode("CAPITAL AND RESERVES", "tblreceipthd", "tblreceiptdtl", startDate, newToDate, cnt, clientCode, sbSql, hmShareCapital,listACC,propertyCode);
										
			 //  CAPITAL AND RESERVES Payment	
				funCalculateBalanceSheetListAccountCode("CAPITAL AND RESERVES", "tblpaymenthd", "tblpaymentdtl", startDate, newToDate, cnt, clientCode, sbSql, hmShareCapital,listACC,propertyCode);

				}catch(Exception e)
						{
							
						}
					}
					
					
				funCalculateBalanceSheetListAccountCode("CAPITAL AND RESERVES", "tbljvhd", "tbljvdtl", dteFromDate, dteToDate, cnt, clientCode, sbSql, hmShareCapital,listACC,propertyCode);
				 cnt++;	
			// Other SHARE CAPITAL Receipt 
				funCalculateBalanceSheetListAccountCode("CAPITAL AND RESERVES", "tblreceipthd", "tblreceiptdtl", dteFromDate, dteToDate, cnt, clientCode, sbSql, hmShareCapital,listACC,propertyCode);
									
			// Other SHARE CAPITAL Payment	
				funCalculateBalanceSheetListAccountCode("CAPITAL AND RESERVES", "tblpaymenthd", "tblpaymentdtl", dteFromDate, dteToDate, cnt, clientCode, sbSql, hmShareCapital,listACC,propertyCode);

							
				/*			
				Map<String,clsIncomeStmtReportBean> hmShareCapital= new HashMap<String,clsIncomeStmtReportBean>();	
					
				// Other SHARE CAPITAL JV
					funCalculateBalanceSheet("CAPITAL AND RESERVES", "tbljvhd", "tbljvdtl", dteFromDate, dteToDate, "dblCrAmt", clientCode, sbSql, hmShareCapital);
							
				// Other SHARE CAPITAL Receipt 
					funCalculateBalanceSheet("CAPITAL AND RESERVES", "tblreceipthd", "tblreceiptdtl", dteFromDate, dteToDate, "dblCrAmt", clientCode, sbSql, hmShareCapital);
										
				// Other SHARE CAPITAL Payment	
					funCalculateBalanceSheet("CAPITAL AND RESERVES", "tblpaymenthd", "tblpaymentdtl", dteFromDate, dteToDate, "dblCrAmt", clientCode, sbSql, hmShareCapital);
	*/
				for(Map.Entry<String, clsIncomeStmtReportBean> entry:hmShareCapital.entrySet())
				{
					totalOtherIncome=totalOtherIncome.add(entry.getValue().getDblValue());
					listOfBalancesheet.add(entry.getValue());
				}
				
				
		Comparator<clsIncomeStmtReportBean> objComparator = new Comparator<clsIncomeStmtReportBean>()
					{
						@Override
						public int compare(clsIncomeStmtReportBean o1, clsIncomeStmtReportBean o2)
						{
							return o1.getStrCategory().compareToIgnoreCase(o2.getStrCategory());
						}
					};

		 Collections.sort(listOfBalancesheet, objComparator);
		 
		Comparator<clsIncomeStmtReportBean> objgroup = new Comparator<clsIncomeStmtReportBean>()
					{
						@Override
						public int compare(clsIncomeStmtReportBean o1, clsIncomeStmtReportBean o2)
						{
							return o1.getStrGroupCategory().compareToIgnoreCase(o2.getStrGroupCategory());
						}
					};

		 Collections.sort(listOfBalancesheet, objgroup);
		 
			Comparator<clsIncomeStmtReportBean> objAcc = new Comparator<clsIncomeStmtReportBean>()
					{
						@Override
						public int compare(clsIncomeStmtReportBean o1, clsIncomeStmtReportBean o2)
						{
							return o1.getStrAccountCode().compareToIgnoreCase(o2.getStrAccountCode());
						}
					};

		 Collections.sort(listOfBalancesheet, objAcc);
			
		 Map<String,BigDecimal> hmCalNetAssets=new HashMap<String,BigDecimal>();
		if(listOfBalancesheet.size()>0)
		{
		for(clsIncomeStmtReportBean obj:listOfBalancesheet)
		{
			if(hmCalNetAssets.containsKey(obj.getStrGroupName()))
			{
				hmCalNetAssets.put(obj.getStrGroupName(),hmCalNetAssets.get(obj.getStrGroupName()).add(obj.getDblValue()));
			}else{
				hmCalNetAssets.put(obj.getStrGroupName(), obj.getDblValue());
				
			}
			
		}
		}
		
		BigDecimal netCurrenAsset=new BigDecimal(0); 
		BigDecimal totNetAsset=new BigDecimal(0); 
		if(!hmCalNetAssets.isEmpty()){
		netCurrenAsset=hmCalNetAssets.get("CURRENT ASSETS").subtract(hmCalNetAssets.get("CURRENT LIABILITIES"));
		totNetAsset=hmCalNetAssets.get("NON- CURRENT ASSETS").add(netCurrenAsset);
		}
		
		hm.put("netCurrenAsset",netCurrenAsset);
		hm.put("totNetAsset",totNetAsset);
		
		
				
				////////////////////////////////////////////////////////////////////////////////////////////
				

				/**
				 * Income statement
				 * 
				 * 
				 */

				
				/*
				
				
				
				Map<String,clsCreditorOutStandingReportBean> hmBalanceSheetCatWiseData=new HashMap<String,clsCreditorOutStandingReportBean>();
				double totalSalesAmt=0;
				
				sbSql.setLength(0);
				sbSql.setLength(0);
				sbSql.append("select a.strType,b.strCategory,b.strGroupName,d.strCrDr,sum(d.dblCrAmt) as Sale,sum(d.dblDrAmt) as Purchase,a.strAccountName,a.strAccountCode "
					+ "from tblacmaster a,tblacgroupmaster b,tbljvhd c,tbljvdtl d "
					+ "where a.strGroupCode=b.strGroupCode and a.strAccountCode=d.strAccountCode "
					+ "and c.strVouchNo=d.strVouchNo and a.strType='GL Code' "
					+ "and date(c.dteVouchDate) between '" + dteFromDate + "' and '" + dteToDate + "' "
					+ "group by a.strAccountCode order by b.strCategory; ");
				List listJV = objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
				if (listJV != null && listJV.size() > 0)
				{
					
					for(int cn=0;cn<listJV.size();cn++)
					{
						
						Object[] objArr = (Object[]) listJV.get(cn);
						String groupCategory = objArr[1].toString();
						String groupName = objArr[2].toString();
						double creditAmount = Double.parseDouble(objArr[4].toString());
						double debitAmount = Double.parseDouble(objArr[5].toString());
						double totalAmt=debitAmount-creditAmount;
						clsCreditorOutStandingReportBean objExpenses = new clsCreditorOutStandingReportBean();
						objExpenses.setStrGroupCategory(groupCategory);
						objExpenses.setStrGroupName(groupName);
						objExpenses.setStrAccountName(objArr[6].toString());
						if(hmBalanceSheetCatWiseData.containsKey(objArr[7].toString()))
						{
							objExpenses=hmBalanceSheetCatWiseData.get(objArr[7].toString());
							objExpenses.setDblValue(totalAmt+objExpenses.getDblValue());
						}
						else
						{
							objExpenses.setDblValue(totalAmt);
							hmBalanceSheetCatWiseData.put(objArr[7].toString(), objExpenses);
						}
						
						
					}
	//					if(hmBalanceSheetCatWiseData.containsKey(groupCategory)) 
	//					{
	//						totalAmt=hmBalanceSheetCatWiseData.get(groupCategory)+totalAmt;
	//					}
	//					hmBalanceSheetCatWiseData.put(groupCategory,(totalAmt));
				}
				
				sbSql.setLength(0);
				sbSql.append("select a.strType,b.strCategory,b.strGroupName,d.strCrDr,sum(d.dblCrAmt) as Sale,sum(d.dblDrAmt) as Purchase,a.strAccountName,a.strAccountCode "
					+ "from tblacmaster a,tblacgroupmaster b,tblpaymenthd c,tblpaymentdtl d "
					+ "where a.strGroupCode=b.strGroupCode and a.strAccountCode=d.strAccountCode "
					+ "and c.strVouchNo=d.strVouchNo and a.strType='GL Code' "
					+ "and date(c.dteVouchDate) between '" + dteFromDate + "' and '" + dteToDate + "' "
					+ "group by a.strAccountCode order by b.strCategory; ");
				List listPayment = objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
				if (listPayment != null && listPayment.size() > 0)
				{
					for(int cn=0;cn<listPayment.size();cn++)
					{
					Object[] objArr = (Object[]) listPayment.get(cn);
					String groupCategory = objArr[1].toString();
					String groupName = objArr[2].toString();
					double creditAmount = Double.parseDouble(objArr[4].toString());
					double debitAmount = Double.parseDouble(objArr[5].toString());
					double totalAmt=debitAmount-creditAmount;
					clsCreditorOutStandingReportBean objExpenses = new clsCreditorOutStandingReportBean();
					objExpenses.setStrGroupCategory(groupCategory);
					objExpenses.setStrGroupName(groupName);
					objExpenses.setStrAccountName(objArr[6].toString());
					if(hmBalanceSheetCatWiseData.containsKey(objArr[7].toString()))
					{
						objExpenses=hmBalanceSheetCatWiseData.get(objArr[7].toString());
						objExpenses.setDblValue(totalAmt+objExpenses.getDblValue());
					}
					else
					{
						objExpenses.setDblValue(totalAmt);
						hmBalanceSheetCatWiseData.put(objArr[7].toString(), objExpenses);
					}
					}
//					if(hmBalanceSheetCatWiseData.containsKey(groupCategory)) 
//					{
//						totalAmt=hmBalanceSheetCatWiseData.get(groupCategory)+totalAmt;
//					}
//					hmBalanceSheetCatWiseData.put(groupCategory,(totalAmt));
				}
				
				sbSql.setLength(0);
				sbSql.append("select a.strType,b.strCategory,b.strGroupName,d.strCrDr,sum(d.dblCrAmt) as Sale,sum(d.dblDrAmt) as Purchase,a.strAccountName,a.strAccountCode "
					+ "from tblacmaster a,tblacgroupmaster b,tblreceipthd c,tblreceiptdtl d "
					+ "where a.strGroupCode=b.strGroupCode and a.strAccountCode=d.strAccountCode "
					+ "and c.strVouchNo=d.strVouchNo and a.strType='GL Code' "
					+ "and date(c.dteVouchDate) between '" + dteFromDate + "' and '" + dteToDate + "' "
					+ "group by a.strAccountCode order by b.strCategory; ");
				List listReceipt = objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
				if (listReceipt != null && listReceipt.size() > 0)
				{
					for(int cn=0;cn<listReceipt.size();cn++)
					{
					Object[] objArr = (Object[]) listReceipt.get(cn);
					String groupCategory = objArr[1].toString();
					String groupName = objArr[2].toString();
					double creditAmount = Double.parseDouble(objArr[4].toString());
					double debitAmount = Double.parseDouble(objArr[5].toString());
					double totalAmt=debitAmount-creditAmount;
					clsCreditorOutStandingReportBean objExpenses = new clsCreditorOutStandingReportBean();
					objExpenses.setStrGroupCategory(groupCategory);
					objExpenses.setStrGroupName(groupName);
					objExpenses.setStrAccountName(objArr[6].toString());
					if(hmBalanceSheetCatWiseData.containsKey(objArr[7].toString()))
					{
						objExpenses=hmBalanceSheetCatWiseData.get(objArr[7].toString());
						objExpenses.setDblValue(totalAmt+objExpenses.getDblValue());
					}
					else
					{
						objExpenses.setDblValue(totalAmt);
						hmBalanceSheetCatWiseData.put(objArr[7].toString(), objExpenses);
					}
					
					}

				}
		
				for(Map.Entry<String, clsCreditorOutStandingReportBean> entry:hmBalanceSheetCatWiseData.entrySet())
				{
					String key = entry.getKey();
					clsCreditorOutStandingReportBean objBean1=entry.getValue();
					listOfImcomeStatement.add(objBean1);
				}

//				final String SORT_NAME = "INCOME EXPENSE TOTAL";
//
				Comparator<clsCreditorOutStandingReportBean> objComparator = new Comparator<clsCreditorOutStandingReportBean>()
				{
					@Override
					public int compare(clsCreditorOutStandingReportBean o1, clsCreditorOutStandingReportBean o2)
					{
						return o1.getStrGroupCategory().compareToIgnoreCase(o2.getStrGroupCategory());
					}
				};

				Collections.sort(listOfImcomeStatement, objComparator);
				*/
//
//				hm.put("listOfImcomeStatement", listOfImcomeStatement);
//
//				dataList.add("1");

				List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();

				JasperDesign jd = JRXmlLoader.load(reportName);
				JasperReport jr = JasperCompileManager.compileReport(jd);
				JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listOfBalancesheet);
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
		
		
		
		@RequestMapping(value = "/rptBalanceSheet", method = RequestMethod.GET)
		private ModelAndView funGenerateBalanceSheetInExcel(@ModelAttribute("command") clsCreditorOutStandingReportBean objBean, HttpServletResponse resp, HttpServletRequest req)
		{
			List listExcelData = new ArrayList();
			Connection con = objGlobal.funGetConnection(req);
			try
			{
				String clientCode = req.getSession().getAttribute("clientCode").toString();
				String companyName = req.getSession().getAttribute("companyName").toString();
				String userCode = req.getSession().getAttribute("usercode").toString();
				String propertyCode = req.getSession().getAttribute("propertyCode").toString();
				String currencyCode = objBean.getStrCurrency();
				double conversionRate = 1;
				String webStockDB = req.getSession().getAttribute("WebStockDB").toString();
				StringBuilder sbSql = new StringBuilder();
				sbSql.append("select dblConvToBaseCurr from " + webStockDB + ".tblcurrencymaster where strCurrencyCode='" + currencyCode + "' and strClientCode='" + clientCode + "' ");
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

				String fd = fromDate.split("-")[0];
				String fm = fromDate.split("-")[1];
				String fy = fromDate.split("-")[2];

				String td = toDate.split("-")[0];
				String tm = toDate.split("-")[1];
				String ty = toDate.split("-")[2];

				String dteFromDate = fy + "-" + fm + "-" + fd;
				String dteToDate = ty + "-" + tm + "-" + td;
								
				Map<String,Map<String,Double>> hmBalanceSheetCatWiseData=new HashMap<String,Map<String,Double>>();
								
				sbSql.setLength(0);
				sbSql.append("select a.strType,b.strCategory,b.strGroupName,d.strCrDr,sum(d.dblCrAmt) as Sale,sum(d.dblDrAmt) as Purchase,a.strAccountName "
					+ "from tblacmaster a,tblacgroupmaster b,tbljvhd c,tbljvdtl d "
					+ "where a.strGroupCode=b.strGroupCode and a.strAccountCode=d.strAccountCode "
					+ "and c.strVouchNo=d.strVouchNo and a.strType='GL Code' "
					+ "and date(c.dteVouchDate) between '" + dteFromDate + "' and '" + dteToDate + "' "
					+ "group by a.strAccountCode order by b.strCategory; ");
				List listJV = objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
				if (listJV != null && listJV.size() > 0)
				{
					for(int cn=0;cn<listJV.size();cn++)
					{
						Object[] objArr = (Object[]) listJV.get(cn);
						String groupCategory = objArr[1].toString();
						String groupName = objArr[2].toString();
						double creditAmount = Double.parseDouble(objArr[4].toString());
						double debitAmount = Double.parseDouble(objArr[5].toString());
						double totalAmt=creditAmount+debitAmount;
						Map<String,Double> hmGroupWiseData=new HashMap<String,Double>();
						
						if(hmBalanceSheetCatWiseData.containsKey(groupCategory)) 
						{
							hmGroupWiseData=hmBalanceSheetCatWiseData.get(groupCategory);
							if(hmGroupWiseData.containsKey(groupName))
								totalAmt=hmGroupWiseData.get(groupName)+totalAmt;
						}
						hmGroupWiseData.put(groupName,totalAmt);
						hmBalanceSheetCatWiseData.put(groupCategory,hmGroupWiseData);
					}
				}
				
				sbSql.setLength(0);
				sbSql.append("select a.strType,b.strCategory,b.strGroupName,d.strCrDr,sum(d.dblCrAmt) as Sale,sum(d.dblDrAmt) as Purchase,a.strAccountName "
					+ "from tblacmaster a,tblacgroupmaster b,tblpaymenthd c,tblpaymentdtl d "
					+ "where a.strGroupCode=b.strGroupCode and a.strAccountCode=d.strAccountCode "
					+ "and c.strVouchNo=d.strVouchNo and a.strType='GL Code' "
					+ "and date(c.dteVouchDate) between '" + dteFromDate + "' and '" + dteToDate + "' "
					+ "group by a.strAccountCode order by b.strCategory; ");
				List listPayment = objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
				if (listPayment != null && listPayment.size() > 0)
				{
					for(int cn=0;cn<listPayment.size();cn++)
					{
						Object[] objArr = (Object[]) listPayment.get(cn);
						String groupCategory = objArr[1].toString();
						String groupName = objArr[2].toString();
						double creditAmount = Double.parseDouble(objArr[4].toString());
						double debitAmount = Double.parseDouble(objArr[5].toString());
						double totalAmt=creditAmount+debitAmount;
						
						Map<String,Double> hmGroupWiseData=new HashMap<String,Double>();
						if(hmBalanceSheetCatWiseData.containsKey(groupCategory)) 
						{
							hmGroupWiseData=hmBalanceSheetCatWiseData.get(groupCategory);
							if(hmGroupWiseData.containsKey(groupName))
								totalAmt=hmGroupWiseData.get(groupName)+totalAmt;
						}
						hmGroupWiseData.put(groupName,totalAmt);
						hmBalanceSheetCatWiseData.put(groupCategory,hmGroupWiseData);
					}
				}
				
				sbSql.setLength(0);
				sbSql.append("select a.strType,b.strCategory,b.strGroupName,d.strCrDr,sum(d.dblCrAmt) as Sale,sum(d.dblDrAmt) as Purchase,a.strAccountName "
					+ "from tblacmaster a,tblacgroupmaster b,tblreceipthd c,tblreceiptdtl d "
					+ "where a.strGroupCode=b.strGroupCode and a.strAccountCode=d.strAccountCode "
					+ "and c.strVouchNo=d.strVouchNo and a.strType='GL Code' "
					+ "and date(c.dteVouchDate) between '" + dteFromDate + "' and '" + dteToDate + "' "
					+ "group by a.strAccountCode order by b.strCategory; ");
				List listReceipt = objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
				if (listReceipt != null && listReceipt.size() > 0)
				{
					for(int cn=0;cn<listReceipt.size();cn++)
					{
						Object[] objArr = (Object[]) listReceipt.get(cn);
						String groupCategory = objArr[1].toString();
						String groupName = objArr[2].toString();
						double creditAmount = Double.parseDouble(objArr[4].toString());
						double debitAmount = Double.parseDouble(objArr[5].toString());
						double totalAmt=creditAmount+debitAmount;
						
						Map<String,Double> hmGroupWiseData=new HashMap<String,Double>();
						if(hmBalanceSheetCatWiseData.containsKey(groupCategory)) 
						{
							hmGroupWiseData=hmBalanceSheetCatWiseData.get(groupCategory);
							if(hmGroupWiseData.containsKey(groupName))
								totalAmt=hmGroupWiseData.get(groupName)+totalAmt;
						}
						hmGroupWiseData.put(groupName,totalAmt);
						hmBalanceSheetCatWiseData.put(groupCategory,hmGroupWiseData);
					}
				}
				
				List<List<String>> listAssets=new ArrayList<List<String>>();
				List<String> listCatName=new ArrayList<String>();
				listCatName.add("ASSETS");
				listAssets.add(listCatName);
				
				List<List<String>> listLiabilities=new ArrayList<List<String>>();
				listCatName=new ArrayList<String>();
				listCatName.add("LIABILITIES");
				listLiabilities.add(listCatName);
				
				List<List<String>> listCapitals=new ArrayList<List<String>>();
				listCatName=new ArrayList<String>();
				listCatName.add("CAPITALS");
				listCapitals.add(listCatName);
				
				double total=0,capitalTotal=0;
				for(Map.Entry<String, Map<String,Double>> entry:hmBalanceSheetCatWiseData.entrySet())
				{
					if(entry.getKey().equals("ASSET") || entry.getKey().equals("INVENTORY") || entry.getKey().equals("INCOME") 
						|| entry.getKey().equals("SUNDRY DEBTOR") || entry.getKey().equals("BANK BALANCE") || entry.getKey().equals("CASH BALANCE"))
					{
						List<String> listAssetGroups=new ArrayList<String>();
						for(Map.Entry<String, Double> entryGroup:entry.getValue().entrySet())
						{
							listAssetGroups.add(entryGroup.getKey());
							listAssetGroups.add(entryGroup.getValue().toString());
							listAssets.add(listAssetGroups);
							total+=entryGroup.getValue();
						}
					}
					
					if(entry.getKey().equals("LIABILITY") || entry.getKey().equals("EXPENSE") || entry.getKey().equals("SUNDRY CREDITOR"))
					{
						List<String> listLiabilityGroups=new ArrayList<String>();
						for(Map.Entry<String, Double> entryLiability:entry.getValue().entrySet())
						{
							listLiabilityGroups.add(entryLiability.getKey());
							listLiabilityGroups.add(entryLiability.getValue().toString());
							listLiabilities.add(listLiabilityGroups);
							total+=entryLiability.getValue();
						}
					}
					
					if(entry.getKey().equals("SHARE CAPITAL") || entry.getKey().equals("EQUITY  CAPITAL"))
					{
						List<String> listCapitalGroups=new ArrayList<String>();
						for(Map.Entry<String, Double> entryCapitals:entry.getValue().entrySet())
						{
							listCapitalGroups.add(entryCapitals.getKey());
							listCapitalGroups.add(entryCapitals.getValue().toString());
							listCapitals.add(listCapitalGroups);
							capitalTotal+=entryCapitals.getValue();
						}
					}
				}
				
				String[] arrStr={"",""};
				
				listExcelData.add("BALANCE SHEET");	//0
				List listData1=new ArrayList();
				listData1.add(companyName);				
				listData1.add("BALANCE SHEET");
				listExcelData.add(listData1);	//1
				
				List listDates=new ArrayList();
				listDates.add("From Date : "+fromDate);
				listDates.add("To Date : "+toDate);
				listExcelData.add(listDates);	//2
								
				List listData=new ArrayList();
								
				List<List> listTotals=new ArrayList<List>();
				List list=new ArrayList();
				list.add("TOTAL");
				list.add(String.valueOf(total));
				listTotals.add(list);
				
				List<List> listCapitalTotals=new ArrayList<List>();
				List list2=new ArrayList();
				list2.add("CAPITAL TOTAL");
				list2.add(String.valueOf(capitalTotal));
				listCapitalTotals.add(list2);
				
				listData.add(listAssets);
				listData.add(listLiabilities);
				listData.add(listTotals);
				listData.add(listCapitals);
				listData.add(listCapitalTotals);
								
				listExcelData.add(listData);	//3
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
			
			return new ModelAndView("excelViewForAccountReports", "excelDataList", listExcelData);

		}
		
		private void funCalculateBalanceSheet(String catType, String hdTableName, String dtlTableName, String fromDate, String toDate
				, String crdrColumn, String clientCode, StringBuilder sbSql, Map<String,clsIncomeStmtReportBean> hmIncomeStatement)
		{
			sbSql.setLength(0);
//			sbSql.append("select a.strType, a.strGroupCode,b.strGroupName,d.strCrDr,sum(d.dblCrAmt) as Sale,sum(d.dblDrAmt) as Purchase,a.strAccountName,a.strAccountCode "
//					+ "from tblacmaster a,tblacgroupmaster b,"+hdTableName+" c,"+dtlTableName+" d "
//					+ "where a.strGroupCode=b.strGroupCode "
//					+ "and a.strAccountCode=d.strAccountCode "
//					+ "and c.strVouchNo=d.strVouchNo "
//					+ "and b.strCategory='"+catType+"' "
//					+ "and a.strType='GL Code' "
//					+ "and date(c.dteVouchDate) between '" + fromDate + "' and '" + toDate + "' and c.strClientCode='"+clientCode+"' "
//					+ "group by a.strAccountCode order by b.strCategory,a.strGroupCode ; ");
			
			
			StringBuilder sbOp=new StringBuilder(); 			
			sbSql.append("select a.strType, a.strGroupCode,b.strGroupName,ifnull(d.strCrDr,''),ifnull(sum(d.dblCrAmt),0) as Sale,ifnull(sum(d.dblDrAmt),0) as Purchase,a.strAccountName,a.strAccountCode "
					+"  from  tblacgroupmaster b,tblacmaster a "
					+" left outer join tbljvdtl d on  a.strAccountCode=d.strAccountCode " 
					+" left outer join tbljvhd  c on c.strVouchNo=d.strVouchNo   and date(c.dteVouchDate) between '" + fromDate + "' and '" + toDate + "' and c.strClientCode='"+clientCode+"' "
					+ "where a.strGroupCode=b.strGroupCode "
					+ "and b.strCategory='"+catType+"' "
					+ "and a.strType='GL Code' "
					+ "group by a.strAccountCode  order by a.strGroupCode ,a.strAccountCode ; ");
			
			List listJV = objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
			if (listJV != null && listJV.size() > 0)
			{
				for(int cn=0;cn<listJV.size();cn++)
				{
					Object[] objArr = (Object[]) listJV.get(cn);

					String groupCategory = objArr[1].toString();
					String groupName = objArr[2].toString();
					BigDecimal creditAmount = BigDecimal.valueOf(Double.parseDouble(objArr[4].toString()));
					BigDecimal debitAmount = BigDecimal.valueOf(Double.parseDouble(objArr[5].toString()));
					
					
					BigDecimal totalAmt=debitAmount.subtract(creditAmount);
					
					String accountCode=objArr[6].toString();
					clsIncomeStmtReportBean objBean = new clsIncomeStmtReportBean();
					
					if(hmIncomeStatement.containsKey(accountCode))
					{
						objBean=hmIncomeStatement.get(accountCode);
						objBean.setDblValue(objBean.getDblValue().add(totalAmt));
					}
					else
					{
						objBean.setStrGroupCategory(groupCategory);
						objBean.setStrGroupName(groupName);
						
						objBean.setStrAccountName(objArr[6].toString());
						objBean.setStrAccountCode(objArr[7].toString());
						
						sbOp.setLength(0);
						BigDecimal opAmt=new BigDecimal(0);
						sbOp.append(" select IF(a.strCrDr='Dr',a.intOpeningBal,0) dblDebitAmt, IF(a.strCrDr='Cr',a.intOpeningBal,0) dblCreditAmt, (IF(a.strCrDr='Dr',a.intOpeningBal,0) - IF(a.strCrDr='Cr',a.intOpeningBal,0)) dblBalanceAmt  from tblacmaster a where a.strAccountCode='"+objArr[7].toString()+"' "
					     +" and a.strClientCode='"+clientCode+"' ");
						List listOP = objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
					    if(listOP.size()>0)
					    {
					    	Object obj[]=(Object[])listOP.get(0);
					    	opAmt=BigDecimal.valueOf( Double.parseDouble(obj[2].toString()));
					    }
					    objBean.setDblValue(opAmt.add(totalAmt));
					
					}
					objBean.setStrCategory(catType);
					hmIncomeStatement.put(accountCode, objBean);
				}
			}
			////////////////////////////////////////////////Payment
			
			
			
			sbSql.append("select a.strType, a.strGroupCode,b.strGroupName,ifnull(d.strCrDr,''),ifnull(sum(d.dblCrAmt),0) as Sale,ifnull(sum(d.dblDrAmt),0) as Purchase,a.strAccountName,a.strAccountCode "
					+"  from  tblacgroupmaster b,tblacmaster a "
					+" left outer join tblreceiptdtl d on  a.strAccountCode=d.strAccountCode " 
					+" left outer join tblreceipthd  c on c.strVouchNo=d.strVouchNo   and date(c.dteVouchDate) between '" + fromDate + "' and '" + toDate + "' and c.strClientCode='"+clientCode+"' "
					+ "where a.strGroupCode=b.strGroupCode "
					+ "and b.strCategory='"+catType+"' "
					+ "and a.strType='GL Code' "
					+ "group by a.strAccountCode  order by a.strGroupCode ,a.strAccountCode ; ");
			
			List listRec = objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
			if (listRec != null && listRec.size() > 0)
			{
				for(int cn=0;cn<listRec.size();cn++)
				{
					Object[] objArr = (Object[]) listRec.get(cn);

					String groupCategory = objArr[1].toString();
					String groupName = objArr[2].toString();
					BigDecimal creditAmount = BigDecimal.valueOf(Double.parseDouble(objArr[4].toString()));
					BigDecimal debitAmount = BigDecimal.valueOf(Double.parseDouble(objArr[5].toString()));
					
					
					BigDecimal totalAmt=debitAmount.subtract(creditAmount);
					
					String accountCode=objArr[6].toString();
					clsIncomeStmtReportBean objBean = new clsIncomeStmtReportBean();
					
					if(hmIncomeStatement.containsKey(accountCode))
					{
						objBean=hmIncomeStatement.get(accountCode);
						objBean.setDblValue(objBean.getDblValue().add(totalAmt));
					}
					else
					{
						objBean.setStrGroupCategory(groupCategory);
						objBean.setStrGroupName(groupName);
						objBean.setDblValue(totalAmt);
						objBean.setStrAccountName(objArr[6].toString());
						objBean.setStrAccountCode(objArr[7].toString());
					}
					objBean.setStrCategory(catType);
					hmIncomeStatement.put(accountCode, objBean);
				}
			}
			
			sbSql.append("select a.strType, a.strGroupCode,b.strGroupName,ifnull(d.strCrDr,''),ifnull(sum(d.dblCrAmt),0) as Sale,ifnull(sum(d.dblDrAmt),0) as Purchase,a.strAccountName,a.strAccountCode "
					+"  from  tblacgroupmaster b,tblacmaster a "
					+" left outer join tblpaymentdtl d on  a.strAccountCode=d.strAccountCode " 
					+" left outer join tblpaymenthd  c on c.strVouchNo=d.strVouchNo   and date(c.dteVouchDate) between '" + fromDate + "' and '" + toDate + "' and c.strClientCode='"+clientCode+"' "
					+ "where a.strGroupCode=b.strGroupCode "
					+ "and b.strCategory='"+catType+"' "
					+ "and a.strType='GL Code' "
					+ "group by a.strAccountCode  order by a.strGroupCode ,a.strAccountCode ; ");
			
			List listPay= objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
			if (listPay != null && listPay.size() > 0)
			{
				for(int cn=0;cn<listPay.size();cn++)
				{
					Object[] objArr = (Object[]) listPay.get(cn);

					String groupCategory = objArr[1].toString();
					String groupName = objArr[2].toString();
					BigDecimal creditAmount = BigDecimal.valueOf(Double.parseDouble(objArr[4].toString()));
					BigDecimal debitAmount = BigDecimal.valueOf(Double.parseDouble(objArr[5].toString()));
					
					
					BigDecimal totalAmt=debitAmount.subtract(creditAmount);
					
					String accountCode=objArr[6].toString();
					clsIncomeStmtReportBean objBean = new clsIncomeStmtReportBean();
					
					if(hmIncomeStatement.containsKey(accountCode))
					{
						objBean=hmIncomeStatement.get(accountCode);
						objBean.setDblValue(objBean.getDblValue().add(totalAmt));
					}
					else
					{
						objBean.setStrGroupCategory(groupCategory);
						objBean.setStrGroupName(groupName);
						objBean.setDblValue(totalAmt);
						objBean.setStrAccountName(objArr[6].toString());
						objBean.setStrAccountCode(objArr[7].toString());
					}
					objBean.setStrCategory(catType);
					hmIncomeStatement.put(accountCode, objBean);
				}
			}
			
		}
		
		
		
		
		private void funCalculateBalanceSheetListAccountCode(String catType, String hdTableName, String dtlTableName, String fromDate, String toDate
				, int cnt, String clientCode, StringBuilder sbSql, Map<String,clsIncomeStmtReportBean> hmIncomeStatement,List<String> list,String propCode)
		{
//			sbSql.setLength(0);
//			sbSql.append("select a.strType, a.strGroupCode,b.strGroupName,d.strCrDr,sum(d.dblCrAmt) as Sale,sum(d.dblDrAmt) as Purchase,a.strAccountName,a.strAccountCode "
//					+ "from tblacmaster a,tblacgroupmaster b,"+hdTableName+" c,"+dtlTableName+" d "
//					+ "where a.strGroupCode=b.strGroupCode "
//					+ "and a.strAccountCode=d.strAccountCode "
//					+ "and c.strVouchNo=d.strVouchNo "
//					+ "and b.strCategory='"+catType+"' "
//					+ "and a.strType='GL Code' "
//					+ "and date(c.dteVouchDate) between '" + fromDate + "' and '" + toDate + "' and c.strClientCode='"+clientCode+"' "
//					+ "group by a.strAccountCode order by b.strCategory,a.strGroupCode ; ");
			
			StringBuilder sbOp=new StringBuilder(); 		
			
			for(String acc:list)
			{
			sbSql.setLength(0);
			sbSql.append("select a.strType,a.strGroupCode,b.strGroupName,ifnull(d.strCrDr,''),if((c.strVouchNo is null),0, IFNULL(SUM(d.dblCrAmt),0) )AS Sale, if((c.strVouchNo is null),0,IFNULL(SUM(d.dblDrAmt),0)) AS Purchase,a.strAccountName,a.strAccountCode, "
					+" b.strCategory from  tblacgroupmaster b,tblacmaster a "
					+" left outer join "+dtlTableName+" d on  a.strAccountCode=d.strAccountCode " 
					+" left outer join "+hdTableName+"  c on c.strVouchNo=d.strVouchNo   and date(c.dteVouchDate) between '" + fromDate + "' and '" + toDate + "' and c.strClientCode='"+clientCode+"'  and a.strPropertyCode='"+propCode+"' "
					+" where a.strGroupCode=b.strGroupCode "
					+" and a.strAccountCode='"+acc+"' "
//					+" and a.strType='GL Code'  "
					+" group by a.strAccountCode  ");
			
			List listJV = objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
			if (listJV != null && listJV.size() > 0)
			{
				for(int cn=0;cn<listJV.size();cn++)
				{
					Object[] objArr = (Object[]) listJV.get(cn);

					String groupCategory = objArr[1].toString();
					String groupName = objArr[2].toString();
					BigDecimal creditAmount = BigDecimal.valueOf(Double.parseDouble(objArr[4].toString()));
					BigDecimal debitAmount = BigDecimal.valueOf(Double.parseDouble(objArr[5].toString()));
					
					
					BigDecimal totalAmt=debitAmount.subtract(creditAmount);
					
					String accountCode=objArr[7].toString();
					clsIncomeStmtReportBean objBean = new clsIncomeStmtReportBean();
					
					if(hmIncomeStatement.containsKey(accountCode))
					{
						objBean=hmIncomeStatement.get(accountCode);
						objBean.setDblValue(objBean.getDblValue().add(totalAmt));
					}
					else
					{
						objBean.setStrGroupCategory(groupCategory);
						objBean.setStrGroupName(groupName);
						
						objBean.setStrAccountName(objArr[6].toString());
						objBean.setStrAccountCode(objArr[7].toString());
						
						BigDecimal opAmt=new BigDecimal(0);
						if(cnt==1)
						{
						sbOp.setLength(0);
						
						sbOp.append(" select IF(a.strCrDr='Dr',a.intOpeningBal,0) dblDebitAmt, IF(a.strCrDr='Cr',a.intOpeningBal,0) dblCreditAmt, (IF(a.strCrDr='Dr',a.intOpeningBal,0) - IF(a.strCrDr='Cr',a.intOpeningBal,0)) dblBalanceAmt  from tblacmaster a where a.strAccountCode='"+objArr[7].toString()+"' "
					     +" and a.strClientCode='"+clientCode+"' ");
						List listOP = objGlobalFunctionsService.funGetListModuleWise(sbOp.toString(), "sql");
					    if(listOP.size()>0)
					    {
					    	Object obj[]=(Object[])listOP.get(0);
					    	opAmt=BigDecimal.valueOf( Double.parseDouble(obj[2].toString()));
					    }
						}
					    objBean.setDblValue(opAmt.add(totalAmt));
					}
					objBean.setStrCategory(catType);
					hmIncomeStatement.put(accountCode, objBean);
				}
			}
			}
		}
		
		

		
}
