package com.sanguine.webbooks.controller;

import java.math.BigDecimal;
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
import com.sanguine.util.clsReportBean;
import com.sanguine.webbooks.bean.clsIncomeStmtReportBean;

@Controller
public class clsIncomeStatementReportController
{
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

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
	@RequestMapping(value = "/frmIncomeStatement", method = RequestMethod.GET)
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
			return new ModelAndView("frmIncomeStatement_1", "command", new clsIncomeStmtReportBean());
		}
		else if ("1".equalsIgnoreCase(urlHits))
		{
			return new ModelAndView("frmIncomeStatement", "command", new clsIncomeStmtReportBean());
		}
		else
		{
			return null;
		}

	}

	@RequestMapping(value = "/rptIncomeStatement", method = RequestMethod.GET)
	private void funReport(@ModelAttribute("command") clsIncomeStmtReportBean objBean, HttpServletResponse resp, HttpServletRequest req)
	{
		Connection con = objGlobalFunctions.funGetConnection(req);
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
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webbooks/rptIncomeStatement.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String startDate = req.getSession().getAttribute("startDate").toString();

			String[] sp = startDate.split(" ");
			String[] spDate = sp[0].split("/");
			startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
			HashMap<String, Double> hmMap = new HashMap<String, Double>();

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

			List<clsIncomeStmtReportBean> listOfIncomeStatement = new ArrayList<clsIncomeStmtReportBean>();
			
			/**
			 * Income statement
			 * 
			 * 
			 */

			
			
			BigDecimal totalSalesAmt=new BigDecimal(0);
			BigDecimal totalOtherExpenses=new BigDecimal(0);
			BigDecimal totalOtherIncome=new BigDecimal(0);
			BigDecimal totalExpenseAmt=new BigDecimal(0);
			
			Map<String,clsIncomeStmtReportBean> hmSalesIncStmt = new HashMap<String,clsIncomeStmtReportBean>();
			List  lisAccCode=new ArrayList();
			lisAccCode.add("1001-006-01");
			lisAccCode.add("1002-002-00");
			lisAccCode.add("1002-001-00");
			
			
			
			// Sale JV
//			funCalculateSalesAndCostOfGoods("INCOME", "tbljvhd", "tbljvdtl", dteFromDate, dteToDate, "dblDrAmt", clientCode, sbSql, hmSalesIncStmt,lisAccCode);
				
				
//				funCalculateIncomeStmt("INCOME", "tbljvhd", "tbljvdtl", dteFromDate, dteToDate, "dblCrAmt", clientCode, sbSql, hmSalesIncStmt);
			
			// Sale Receipt
//				funCalculateIncomeStmt("INCOME", "tblreceipthd", "tblreceiptdtl", dteFromDate, dteToDate, "dblCrAmt", clientCode, sbSql, hmSalesIncStmt);
						
			// Sale Payment
//				funCalculateIncomeStmt("INCOME", "tblpaymenthd", "tblpaymentdtl", dteFromDate, dteToDate, "dblCrAmt", clientCode, sbSql, hmSalesIncStmt);	
			
			List <clsReportBean>list=funGetSalesAndCost( dteFromDate, dteToDate, conversionRate, req);
			
			BigDecimal purAmt=new BigDecimal(0);
			BigDecimal salesAmt = new BigDecimal(0);
			for(clsReportBean obj:list)
			{
				 purAmt=purAmt.add(BigDecimal.valueOf(obj.getDblPurAmt()));
				 salesAmt=salesAmt.add(BigDecimal.valueOf(obj.getDblSaleAmt()));
			}
			Map<String,clsIncomeStmtReportBean> hmOtherExpensesIncStmt = new HashMap<String,clsIncomeStmtReportBean>();
			
			lisAccCode=new ArrayList();
			lisAccCode.add("1004-001-00");
//			funCalculateSalesAndCostOfGoods("OTHER EXPENSES", "tbljvhd", "tbljvdtl", dteFromDate, dteToDate, "dblDrAmt", clientCode, sbSql, hmOtherExpensesIncStmt,lisAccCode);
			// Cost Of Goods JV
//				funCalculateIncomeStmt("OTHER EXPENSES", "tbljvhd", "tbljvdtl", dteFromDate, dteToDate, "dblCrAmt", clientCode, sbSql, hmOtherExpensesIncStmt);
					
			// Cost Of Goods Receipt 
//				funCalculateIncomeStmt("OTHER EXPENSES", "tblreceipthd", "tblreceiptdtl", dteFromDate, dteToDate, "dblDrAmt", clientCode, sbSql, hmOtherExpensesIncStmt);
						
			// Cost Of Goods Payment	
//				funCalculateIncomeStmt("OTHER EXPENSES", "tblpaymenthd", "tblpaymentdtl", dteFromDate, dteToDate, "dblDrAmt", clientCode, sbSql, hmOtherExpensesIncStmt);
						
			// Calculate Gross Profit = Total Income (Sales) - Total Other Expenses (COG)	
				
			clsIncomeStmtReportBean objIncomeStmtreportBean=new clsIncomeStmtReportBean();	
			for(Map.Entry<String, clsIncomeStmtReportBean> entry:hmSalesIncStmt.entrySet())
			{
				totalSalesAmt=totalSalesAmt.add(entry.getValue().getDblValue());
				objIncomeStmtreportBean.setStrGroupCategory(entry.getValue().getStrGroupCategory());
				objIncomeStmtreportBean.setStrGroupName(entry.getValue().getStrGroupName());
				objIncomeStmtreportBean.setStrAccountName("SALES");
				objIncomeStmtreportBean.setStrCategory("INCOME");
			}
			objIncomeStmtreportBean.setDblValue(totalSalesAmt);
//			listOfIncomeStatement.add(objIncomeStmtreportBean);
				
			objIncomeStmtreportBean=new clsIncomeStmtReportBean();
			for(Map.Entry<String, clsIncomeStmtReportBean> entry:hmOtherExpensesIncStmt.entrySet())
			{
				totalOtherExpenses=totalOtherExpenses.add(entry.getValue().getDblValue());
				//listOfIncomeStatement.add(entry.getValue());
				objIncomeStmtreportBean.setStrGroupCategory(entry.getValue().getStrGroupCategory());
				objIncomeStmtreportBean.setStrGroupName(entry.getValue().getStrGroupName());
				objIncomeStmtreportBean.setStrAccountName("COST OF GOODS SOLD");
				objIncomeStmtreportBean.setStrCategory("INCOME");
			}
			objIncomeStmtreportBean.setDblValue(totalOtherExpenses);
//			listOfIncomeStatement.add(objIncomeStmtreportBean);
			
			clsIncomeStmtReportBean objSales1 = new clsIncomeStmtReportBean();
			objSales1.setStrGroupCategory("GROSS PROFIT");
			objSales1.setStrGroupName("GROSS PROFIT");
//			BigDecimal grossProfit=totalSalesAmt.subtract(totalOtherExpenses);
//			objSales1.setDblValue(grossProfit);
			objSales1.setStrAccountName("");
			objSales1.setStrCategory("INCOME");
//			listOfIncomeStatement.add(objSales1);
			BigDecimal grossProfit=salesAmt.subtract(purAmt);			
			hm.put("dblGrossProfit", grossProfit);
			hm.put("dblSales", salesAmt);
			hm.put("dblCostofGood", purAmt);
			
			
			
			Map<String,clsIncomeStmtReportBean> hmOtherIncomeIncStmt = new HashMap<String,clsIncomeStmtReportBean>();	
				
			// Other Incomes JV
				funCalculateIncomeStmt("OTHER INCOME", "tbljvhd", "tbljvdtl", dteFromDate, dteToDate, "dblCrAmt", clientCode, sbSql, hmOtherIncomeIncStmt,propertyCode);
						
			// Other Incomes Receipt 
				funCalculateIncomeStmt("OTHER INCOME", "tblreceipthd", "tblreceiptdtl", dteFromDate, dteToDate, "dblCrAmt", clientCode, sbSql, hmOtherIncomeIncStmt,propertyCode);
									
			// Other Incomes Payment	
				funCalculateIncomeStmt("OTHER INCOME", "tblpaymenthd", "tblpaymentdtl", dteFromDate, dteToDate, "dblCrAmt", clientCode, sbSql, hmOtherIncomeIncStmt,propertyCode);
								
			
			Map<String,clsIncomeStmtReportBean> hmExpenseIncStmt = new HashMap<String,clsIncomeStmtReportBean>();	
				
			// Expense JV
				funCalculateIncomeStmt("EXPENSE", "tbljvhd", "tbljvdtl", dteFromDate, dteToDate, "dblDrAmt", clientCode, sbSql, hmExpenseIncStmt,propertyCode);
						
			// Expense Receipt 
				funCalculateIncomeStmt("EXPENSE", "tblreceipthd", "tblreceiptdtl", dteFromDate, dteToDate, "dblDrAmt", clientCode, sbSql, hmExpenseIncStmt,propertyCode);
							
			// Expense Payment	
				funCalculateIncomeStmt("EXPENSE", "tblpaymenthd", "tblpaymentdtl", dteFromDate, dteToDate, "dblDrAmt", clientCode, sbSql, hmExpenseIncStmt,propertyCode);

				
				for(Map.Entry<String, clsIncomeStmtReportBean> entry:hmOtherIncomeIncStmt.entrySet())
				{
					totalOtherIncome=totalOtherIncome.add(entry.getValue().getDblValue());
					//listOfIncomeStatement.add(entry.getValue());
					clsIncomeStmtReportBean objIncomeStmtreport=new clsIncomeStmtReportBean();
					objIncomeStmtreport.setStrGroupCategory(entry.getValue().getStrGroupCategory());
					objIncomeStmtreport.setStrGroupName(entry.getValue().getStrGroupName());
					objIncomeStmtreport.setStrAccountName(entry.getValue().getStrAccountName());
					objIncomeStmtreport.setDblValue(entry.getValue().getDblValue());
					objIncomeStmtreport.setStrCategory("");
					listOfIncomeStatement.add(objIncomeStmtreport);
					
				}
				totalOtherIncome=totalOtherIncome.add(grossProfit);
				
				hm.put("totalIncome" ,totalOtherIncome);
				
				
//			for(Map.Entry<String, clsIncomeStmtReportBean> entry:hmOtherIncomeIncStmt.entrySet())
//			{
//				totalOtherIncome=totalOtherIncome.add(entry.getValue().getDblValue());
//				listOfIncomeStatement.add(entry.getValue());
//			}
			
			for(Map.Entry<String, clsIncomeStmtReportBean> entry:hmExpenseIncStmt.entrySet())
			{
				totalExpenseAmt=totalExpenseAmt.add(entry.getValue().getDblValue());
				listOfIncomeStatement.add(entry.getValue());
			}
			
			//double totalAmt=totalSalesAmt-totalOtherExpenses+totalOtherIncome-totalExpenseAmt;
			
			BigDecimal value1=totalSalesAmt.subtract(totalOtherExpenses);
			BigDecimal value2=totalOtherIncome.subtract(totalExpenseAmt);
			
//			BigDecimal totalAmt=value1.add(value2);
			
			BigDecimal totalAmt=totalOtherIncome.subtract(totalExpenseAmt);
			hm.put("dblProfitOfTheYear" ,totalAmt);
			
			
			/*
			sbSql.setLength(0);
			sbSql.append("select a.strType,b.strCategory,b.strGroupName,d.strCrDr,sum(d.dblCrAmt) as Sale,a.strAccountName "
					+ "from tblacmaster a,tblacgroupmaster b,tbljvhd c,tbljvdtl d "
					+ "where a.strGroupCode=b.strGroupCode "
					+ "and a.strAccountCode=d.strAccountCode "
					+ "and c.strVouchNo=d.strVouchNo "
					+ "and b.strCategory='INCOME' "
					+ "and a.strType='GL Code' "
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
					double totalCreditAmount = Double.parseDouble(objArr[4].toString());
					totalSalesAmt=totalSalesAmt+totalCreditAmount;
					clsIncomeStmtReportBean objSales = new clsIncomeStmtReportBean();
					objSales.setStrGroupCategory(groupCategory);
					objSales.setStrGroupName(groupName);
					objSales.setDblValue(totalCreditAmount);
					objSales.setStrAccountName(objArr[5].toString());
					listOfImcomeStatement.add(objSales);
				}
			}*/
			
			
		
			
			/*
			sbSql.setLength(0);
			sbSql.append("select a.strType,b.strCategory,b.strGroupName,d.strCrDr,sum(d.dblCrAmt) as Sale,a.strAccountName "
					+ "from tblacmaster a,tblacgroupmaster b,tblreceipthd c,tblreceiptdtl d "
					+ "where a.strGroupCode=b.strGroupCode "
					+ "and a.strAccountCode=d.strAccountCode "
					+ "and c.strVouchNo=d.strVouchNo "
					+ "and b.strCategory='INCOME' "
					+ "and a.strType='GL Code' "
					+ "and date(c.dteVouchDate) between '" + dteFromDate + "' and '" + dteToDate + "' "
					+ "group by a.strAccountCode order by b.strCategory;");
			List listReceipt = objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
			if (listReceipt!= null && listReceipt.size() > 0)
			{
				for(int cn=0;cn<listReceipt.size();cn++)
				{
					Object[] objArr = (Object[]) listReceipt.get(cn);
	
					String groupCategory = objArr[1].toString();
					String groupName = objArr[2].toString();
					double totalCreditAmount = Double.parseDouble(objArr[4].toString());
					totalSalesAmt=totalSalesAmt+totalCreditAmount;
					clsIncomeStmtReportBean objSales = new clsIncomeStmtReportBean();
					objSales.setStrGroupCategory(groupCategory);
					objSales.setStrGroupName(groupName);
					objSales.setDblValue(totalCreditAmount);
					objSales.setStrAccountName(objArr[5].toString());
					listOfImcomeStatement.add(objSales);
				}
			}*/

			
		
			
			/*
			sbSql.setLength(0);
			sbSql.append("select a.strType,b.strCategory,b.strGroupName,d.strCrDr,sum(d.dblCrAmt) as CostOfSale,a.strAccountName "
					+ "from tblacmaster a,tblacgroupmaster b,tblpaymenthd c,tblpaymentdtl d "
					+ "where a.strGroupCode=b.strGroupCode "
					+ "and a.strAccountCode=d.strAccountCode "
					+ "and c.strVouchNo=d.strVouchNo "
					+ "and b.strCategory='INCOME' "
					+ "and a.strType='GL Code' "
					+ "and date(c.dteVouchDate) between '" + dteFromDate + "' and '" + dteToDate + "' "
					+ "group by a.strAccountCode order by b.strCategory; ");
			
			List listPayments = objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
			if (listPayments != null && listPayments.size() > 0)
			{
				
				for(int cn=0;cn<listPayments.size();cn++)
				{
					Object[] objArr = (Object[]) listPayments.get(cn);
	
					String groupCategory = objArr[1].toString();
					String groupName = objArr[2].toString();
					double totaldebitAmountAmount = Double.parseDouble(objArr[4].toString());
				
					clsIncomeStmtReportBean objCostOfSales = new clsIncomeStmtReportBean();
					objCostOfSales.setStrGroupCategory(groupCategory);
					objCostOfSales.setStrGroupName(groupName);
					objCostOfSales.setDblValue(totaldebitAmountAmount);
					objCostOfSales.setStrAccountName(objArr[5].toString());
					listOfImcomeStatement.add(objCostOfSales);
				}
			}*/
			
			
			
			/*
			sbSql.setLength(0);
			sbSql.append("select a.strType,b.strCategory,b.strGroupName,d.strCrDr,sum(d.dblDrAmt) as Sale,a.strAccountName "
					+ "from tblacmaster a,tblacgroupmaster b,tbljvhd c,tbljvdtl d "
					+ "where a.strGroupCode=b.strGroupCode "
					+ "and a.strAccountCode=d.strAccountCode "
					+ "and c.strVouchNo=d.strVouchNo "
					+ "and b.strCategory='Other Expenses' "
					+ "and a.strType='GL Code' "
					+ "and date(c.dteVouchDate) between '" + dteFromDate + "' and '" + dteToDate + "' "
					+ "group by a.strAccountCode order by b.strCategory; ");
			listJV = objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
			if (listJV != null && listJV.size() > 0)
			{
				for(int cn=0;cn<listJV.size();cn++)
				{
					Object[] objArr = (Object[]) listJV.get(cn);
					
					String groupCategory = objArr[1].toString();
					String groupName = objArr[2].toString();
					double totalExpenseAmount = Double.parseDouble(objArr[4].toString());
					totalOtherExpenses=totalOtherExpenses+totalExpenseAmount;
					clsIncomeStmtReportBean objExpenses = new clsIncomeStmtReportBean();
					objExpenses.setStrGroupCategory(groupCategory);
					objExpenses.setStrGroupName(groupName);
					objExpenses.setDblValue(totalExpenseAmount);
					objExpenses.setStrAccountName(objArr[5].toString());
					listOfImcomeStatement.add(objExpenses);
				}
			}
			
			sbSql.setLength(0);
			sbSql.append("select a.strType,b.strCategory,b.strGroupName,d.strCrDr,sum(d.dblDrAmt) as CostOfSale,a.strAccountName "
					+ "from tblacmaster a,tblacgroupmaster b,tblpaymenthd  c,tblpaymentdtl d "
					+ "where a.strGroupCode=b.strGroupCode "
					+ "and a.strAccountCode=d.strAccountCode "
					+ "and c.strVouchNo=d.strVouchNo "
					+ "and b.strCategory='Other Expenses' "
					+ "and a.strType='GL Code'  "
					+ "and date(c.dteVouchDate) between '" + dteFromDate + "' and '" + dteToDate + "' "
					+ "group by a.strAccountCode order by b.strCategory; ");
			listPayments = objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
			if (listPayments!= null && listPayments.size() > 0)
			{
				for(int cn=0;cn<listPayments.size();cn++)
				{
					Object[] objArr = (Object[]) listPayments.get(cn);
	
					String groupCategory = objArr[1].toString();
					String groupName = objArr[2].toString();
					double totalExpenseAmount = Double.parseDouble(objArr[4].toString());
					totalOtherExpenses=totalOtherExpenses+totalExpenseAmount;
					clsIncomeStmtReportBean objExpenses = new clsIncomeStmtReportBean();
					objExpenses.setStrGroupCategory(groupCategory);
					objExpenses.setStrGroupName(groupName);
					objExpenses.setDblValue(totalExpenseAmount);
					objExpenses.setStrAccountName(objArr[5].toString());
					listOfImcomeStatement.add(objExpenses);
				}
			}
			
			
			
			sbSql.setLength(0);
			sbSql.append("select a.strType,b.strCategory,b.strGroupName,d.strCrDr,sum(d.dblDrAmt) as Sale,a.strAccountName "
					+ "from tblacmaster a,tblacgroupmaster b,tblreceipthd c,tblreceiptdtl d "
					+ "where a.strGroupCode=b.strGroupCode "
					+ "and a.strAccountCode=d.strAccountCode "
					+ "and c.strVouchNo=d.strVouchNo "
					+ "and b.strCategory='Other Expenses' "
					+ "and a.strType='GL Code' "
					+ "and date(c.dteVouchDate) between '" + dteFromDate + "' and '" + dteToDate + "' "
					+ "group by a.strAccountCode order by b.strCategory;");
			List listReceiptExpense = objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
			if (listReceiptExpense!= null && listReceiptExpense.size() > 0)
			{
				for(int cn=0;cn<listReceiptExpense.size();cn++)
				{
					Object[] objArr = (Object[]) listReceiptExpense.get(cn);
	
					String groupCategory = objArr[1].toString();
					String groupName = objArr[2].toString();
					double totalExpenseAmount = Double.parseDouble(objArr[4].toString());
					totalOtherExpenses=totalOtherExpenses+totalExpenseAmount;
					clsIncomeStmtReportBean objSales = new clsIncomeStmtReportBean();
					objSales.setStrGroupCategory(groupCategory);
					objSales.setStrGroupName(groupName);
					objSales.setDblValue(totalExpenseAmount);
					objSales.setStrAccountName(objArr[5].toString());
					listOfImcomeStatement.add(objSales);
				}
			}
			*/
			
				/*clsIncomeStmtReportBean objSales1 = new clsIncomeStmtReportBean();
				objSales1.setStrGroupCategory("Gross Profit");
				objSales1.setStrGroupName("Gross Profit");
				objSales1.setDblValue(totalSalesAmt-totalOtherExpenses);
				objSales1.setStrAccountName("");
				listOfIncomeStatement.add(objSales1);
						
				hm.put("dblGrossProfit",totalSalesAmt-totalOtherExpenses);*/
				
						
			/*
			sbSql.setLength(0);
			sbSql.append("select a.strType,b.strCategory,b.strGroupName,d.strCrDr,sum(d.dblCrAmt) as Sale,a.strAccountName "
					+ "from tblacmaster a,tblacgroupmaster b,tbljvhd c,tbljvdtl d "
					+ "where a.strGroupCode=b.strGroupCode "
					+ "and a.strAccountCode=d.strAccountCode "
					+ "and c.strVouchNo=d.strVouchNo "
					+ "and b.strCategory='Other Income' "
					+ "and a.strType='GL Code' "
					+ "and date(c.dteVouchDate) between '" + dteFromDate + "' and '" + dteToDate + "' "
					+ "group by a.strAccountCode order by b.strCategory; ");
			 listJV = objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
			if (listJV != null && listJV.size() > 0)
			{
				for(int cn=0;cn<listJV.size();cn++)
				{
					Object[] objArr = (Object[]) listJV.get(cn);
	
					String groupCategory = objArr[1].toString();
					String groupName = objArr[2].toString();
					double totalCreditAmount = Double.parseDouble(objArr[4].toString());
					otherIncome=otherIncome+totalCreditAmount;
					clsIncomeStmtReportBean objSales = new clsIncomeStmtReportBean();
					objSales.setStrGroupCategory(groupCategory);
					objSales.setStrGroupName(groupName);
					objSales.setDblValue(totalCreditAmount);
					objSales.setStrAccountName(objArr[5].toString());
					listOfImcomeStatement.add(objSales);
				}
			}
			
			sbSql.setLength(0);
			sbSql.append("select a.strType,b.strCategory,b.strGroupName,d.strCrDr,sum(d.dblCrAmt) as Sale,a.strAccountName "
					+ "from tblacmaster a,tblacgroupmaster b,tblreceipthd c,tblreceiptdtl d "
					+ "where a.strGroupCode=b.strGroupCode "
					+ "and a.strAccountCode=d.strAccountCode "
					+ "and c.strVouchNo=d.strVouchNo "
					+ "and b.strCategory='Other Income' "
					+ "and a.strType='GL Code' "
					+ "and date(c.dteVouchDate) between '" + dteFromDate + "' and '" + dteToDate + "' "
					+ "group by a.strAccountCode order by b.strCategory;");
			 listReceipt = objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
			if (listReceipt!= null && listReceipt.size() > 0)
			{
				for(int cn=0;cn<listReceipt.size();cn++)
				{
					Object[] objArr = (Object[]) listReceipt.get(cn);
	
					String groupCategory = objArr[1].toString();
					String groupName = objArr[2].toString();
					double totalCreditAmount = Double.parseDouble(objArr[4].toString());
					otherIncome=otherIncome+totalCreditAmount;
					clsIncomeStmtReportBean objSales = new clsIncomeStmtReportBean();
					objSales.setStrGroupCategory(groupCategory);
					objSales.setStrGroupName(groupName);
					objSales.setDblValue(totalCreditAmount);
					objSales.setStrAccountName(objArr[5].toString());
					listOfImcomeStatement.add(objSales);
				}
			}

			
			
			sbSql.setLength(0);
			sbSql.append("select a.strType,b.strCategory,b.strGroupName,d.strCrDr,sum(d.dblCrAmt) as CostOfSale,a.strAccountName "
					+ "from tblacmaster a,tblacgroupmaster b,tblpaymenthd c,tblpaymentdtl d "
					+ "where a.strGroupCode=b.strGroupCode "
					+ "and a.strAccountCode=d.strAccountCode "
					+ "and c.strVouchNo=d.strVouchNo "
					+ "and b.strCategory='Other Income' "
					+ "and a.strType='GL Code' "
					+ "and date(c.dteVouchDate) between '" + dteFromDate + "' and '" + dteToDate + "' "
					+ "group by a.strAccountCode order by b.strCategory; ");
			
			 listPayments = objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
			if (listPayments != null && listPayments.size() > 0)
			{
				
				for(int cn=0;cn<listPayments.size();cn++)
				{
					Object[] objArr = (Object[]) listPayments.get(cn);
	
					String groupCategory = objArr[1].toString();
					String groupName = objArr[2].toString();
					double totalCreditAmount = Double.parseDouble(objArr[4].toString());
					otherIncome=otherIncome+totalCreditAmount;
					clsIncomeStmtReportBean objCostOfSales = new clsIncomeStmtReportBean();
					objCostOfSales.setStrGroupCategory(groupCategory);
					objCostOfSales.setStrGroupName(groupName);
					objCostOfSales.setDblValue(totalCreditAmount);
					objCostOfSales.setStrAccountName(objArr[5].toString());
					listOfImcomeStatement.add(objCostOfSales);
				}
			}
			*/
			
			//hm.put("otherIncome", otherIncome);
			
			
			/*
			sbSql.setLength(0);
			sbSql.append("select a.strType,b.strCategory,b.strGroupName,d.strCrDr,sum(d.dblDrAmt) as Sale,a.strAccountName "
					+ "from tblacmaster a,tblacgroupmaster b,tbljvhd c,tbljvdtl d "
					+ "where a.strGroupCode=b.strGroupCode "
					+ "and a.strAccountCode=d.strAccountCode "
					+ "and c.strVouchNo=d.strVouchNo "
					+ "and b.strCategory='EXPENSE' "
					+ "and a.strType='GL Code' "
					+ "and date(c.dteVouchDate) between '" + dteFromDate + "' and '" + dteToDate + "' "
					+ "group by a.strAccountCode order by b.strCategory; ");
			listJV = objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
			if (listJV != null && listJV.size() > 0)
			{
				for(int cn=0;cn<listJV.size();cn++)
				{
					Object[] objArr = (Object[]) listJV.get(cn);
					
					String groupCategory = objArr[1].toString();
					String groupName = objArr[2].toString();
					double totalExpenseAmount = Double.parseDouble(objArr[4].toString());
					totalExpenseAmt=totalExpenseAmt+totalExpenseAmount;
					clsIncomeStmtReportBean objExpenses = new clsIncomeStmtReportBean();
					objExpenses.setStrGroupCategory(groupCategory);
					objExpenses.setStrGroupName(groupName);
					objExpenses.setDblValue(totalExpenseAmount);
					objExpenses.setStrAccountName(objArr[5].toString());
					listOfImcomeStatement.add(objExpenses);
				}
			}
			
			sbSql.setLength(0);
			sbSql.append("select a.strType,b.strCategory,b.strGroupName,d.strCrDr,sum(d.dblDrAmt) as CostOfSale,a.strAccountName "
					+ "from tblacmaster a,tblacgroupmaster b,tblpaymenthd  c,tblpaymentdtl d "
					+ "where a.strGroupCode=b.strGroupCode "
					+ "and a.strAccountCode=d.strAccountCode "
					+ "and c.strVouchNo=d.strVouchNo "
					+ "and b.strCategory='EXPENSE' "
					+ "and a.strType='GL Code'  "
					+ "and date(c.dteVouchDate) between '" + dteFromDate + "' and '" + dteToDate + "' "
					+ "group by a.strAccountCode order by b.strCategory; ");
			listPayments = objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
			if (listPayments!= null && listPayments.size() > 0)
			{
				for(int cn=0;cn<listPayments.size();cn++)
				{
					Object[] objArr = (Object[]) listPayments.get(cn);
	
					String groupCategory = objArr[1].toString();
					String groupName = objArr[2].toString();
					double totalExpenseAmount = Double.parseDouble(objArr[4].toString());
					totalExpenseAmt=totalExpenseAmt+totalExpenseAmount;
					clsIncomeStmtReportBean objExpenses = new clsIncomeStmtReportBean();
					objExpenses.setStrGroupCategory(groupCategory);
					objExpenses.setStrGroupName(groupName);
					objExpenses.setDblValue(totalExpenseAmount);
					objExpenses.setStrAccountName(objArr[5].toString());
					listOfImcomeStatement.add(objExpenses);
				}
			}
			
			
			
			sbSql.setLength(0);
			sbSql.append("select a.strType,b.strCategory,b.strGroupName,d.strCrDr,sum(d.dblDrAmt) as Sale,a.strAccountName "
					+ "from tblacmaster a,tblacgroupmaster b,tblreceipthd c,tblreceiptdtl d "
					+ "where a.strGroupCode=b.strGroupCode "
					+ "and a.strAccountCode=d.strAccountCode "
					+ "and c.strVouchNo=d.strVouchNo "
					+ "and b.strCategory='EXPENSE' "
					+ "and a.strType='GL Code' "
					+ "and date(c.dteVouchDate) between '" + dteFromDate + "' and '" + dteToDate + "' "
					+ "group by a.strAccountCode order by b.strCategory;");
			 listReceiptExpense = objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
			if (listReceiptExpense!= null && listReceiptExpense.size() > 0)
			{
				for(int cn=0;cn<listReceiptExpense.size();cn++)
				{
					Object[] objArr = (Object[]) listReceiptExpense.get(cn);
	
					String groupCategory = objArr[1].toString();
					String groupName = objArr[2].toString();
					double totalExpenseAmount = Double.parseDouble(objArr[4].toString());
					totalExpenseAmt=totalExpenseAmt+totalExpenseAmount;
					clsIncomeStmtReportBean objSales = new clsIncomeStmtReportBean();
					objSales.setStrGroupCategory(groupCategory);
					objSales.setStrGroupName(groupName);
					objSales.setDblValue(totalExpenseAmount);
					objSales.setStrAccountName(objArr[5].toString());
					listOfImcomeStatement.add(objSales);
				}
			}
			
			hm.put("totalExpenseAmt", totalExpenseAmt);
			double totalAmt=totalSalesAmt-totalOtherExpenses+otherIncome-totalExpenseAmt;
			hm.put("dblProfitOfTheYear" ,totalAmt);

		*/
//
//			final String CATAGARISORTNAME = "INCOME EXPENSE TOTAL";
//			Comparator<clsIncomeStmtReportBean> objComparator = new Comparator<clsIncomeStmtReportBean>()
//			{
//
//				@Override
//				public int compare(clsIncomeStmtReportBean o1, clsIncomeStmtReportBean o2)
//				{
//					return CATAGARISORTNAME.indexOf(o1.getStrGroupCategory()) - (CATAGARISORTNAME.indexOf(o2.getStrGroupCategory()));
//				}
//			};
//
//			Collections.sort(listOfImcomeStatement, objComparator);
//
//			final String GROUPSORTNAME = "    TOTAL";
//
//			Comparator<clsIncomeStmtReportBean> objComparator1 = new Comparator<clsIncomeStmtReportBean>()
//			{
//
//				@Override
//				public int compare(clsIncomeStmtReportBean o1, clsIncomeStmtReportBean o2)
//				{
//					return GROUPSORTNAME.indexOf(o1.getStrGroupName()) - (GROUPSORTNAME.indexOf(o2.getStrGroupName()));
//				}
//			};

//			Collections.sort(listOfImcomeStatement);

//			hm.put("listOfImcomeStatement", listOfImcomeStatement);


			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listOfIncomeStatement);
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

	
	
	private void funCalculateIncomeStmt(String catType, String hdTableName, String dtlTableName, String fromDate, String toDate
			, String crdrColumn, String clientCode, StringBuilder sbSql, Map<String,clsIncomeStmtReportBean> hmIncomeStatement,String propCode)
	{
		sbSql.setLength(0);
		StringBuilder sbOp=new StringBuilder(); 		
		
		
//		sbSql.append("select a.strType,b.strCategory,b.strGroupName,d.strCrDr,sum(d."+crdrColumn+"),a.strAccountName,a.strAccountCode "
//				+ "from tblacmaster a,tblacgroupmaster b,"+hdTableName+" c,"+dtlTableName+" d "
//				+ "where a.strGroupCode=b.strGroupCode "
//				+ "and a.strAccountCode=d.strAccountCode "
//				+ "and c.strVouchNo=d.strVouchNo "
//				+ "and b.strCategory='"+catType+"' "
//				+ "and a.strType='GL Code' "
//				+ "and date(c.dteVouchDate) between '" + fromDate + "' and '" + toDate + "' and c.strClientCode='"+clientCode+"' "
//				+ "group by a.strAccountCode order by b.strCategory; ");
		
		
		
		
		sbSql.append("select a.strType,b.strGroupCode,b.strGroupName,ifnull(d.strCrDr,''),if((c.strVouchNo is null),0,ifnull(sum(d.dblDrAmt),0)),a.strAccountName,a.strAccountCode,if((c.strVouchNo is null),0,ifnull(sum(d.dblCrAmt),0)) from  tblacgroupmaster b,tblacmaster a "
				+" left outer join "+dtlTableName+" d on  a.strAccountCode=d.strAccountCode " 
				+" left outer join "+hdTableName+"  c on c.strVouchNo=d.strVouchNo   and date(c.dteVouchDate) between '" + fromDate + "' and '" + toDate + "' and c.strClientCode='"+clientCode+"' and a.strPropertyCode='"+propCode+"'  "
				+ "where a.strGroupCode=b.strGroupCode "
				+ "and b.strCategory='"+catType+"' "
				+ "and a.strType='GL Code' "
				+ "group by a.strAccountCode  ");
		
		
		List listJV = objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
		if (listJV != null && listJV.size() > 0)
		{
			for(int cn=0;cn<listJV.size();cn++)
			{
				Object[] objArr = (Object[]) listJV.get(cn);

				String groupCategory = objArr[1].toString();
				String groupName = objArr[2].toString();
				BigDecimal totalAmount = BigDecimal.valueOf(Double.parseDouble(objArr[4].toString())).subtract(BigDecimal.valueOf(Double.parseDouble(objArr[7].toString())));
				
				String accountCode=objArr[6].toString();
				clsIncomeStmtReportBean objBean = new clsIncomeStmtReportBean();
				
				if(hmIncomeStatement.containsKey(accountCode))
				{
					objBean=hmIncomeStatement.get(accountCode);
					objBean.setDblValue(objBean.getDblValue().add(totalAmount));
				}
				else
				{
					objBean.setStrGroupCategory(groupCategory);
					objBean.setStrGroupName(groupName);
					objBean.setStrAccountName(objArr[5].toString());
				    objBean.setDblValue(totalAmount);
				}
				objBean.setStrCategory(catType);
				hmIncomeStatement.put(accountCode, objBean);
			}
		}
	}

	
	private void funCalculateSalesAndCostOfGoods(String catType, String hdTableName, String dtlTableName, String fromDate, String toDate
			, String crdrColumn, String clientCode, StringBuilder sbSql, Map<String,clsIncomeStmtReportBean> hmIncomeStatement,List lisAccCode)
	{
		
		for(int i=0;i<lisAccCode.size();i++)
		{	
		
		sbSql.setLength(0);
//		sbSql.append("select a.strType,b.strCategory,b.strGroupName,d.strCrDr,sum(d."+crdrColumn+"),a.strAccountName,a.strAccountCode "
//				+ "from tblacmaster a,tblacgroupmaster b,"+hdTableName+" c,"+dtlTableName+" d "
//				+ "where a.strGroupCode=b.strGroupCode "
//				+ "and a.strAccountCode=d.strAccountCode "
//				+ "and c.strVouchNo=d.strVouchNo "
////				+ "and b.strCategory='"+catType+"' "
//				+" and a.strAccountCode='"+lisAccCode.get(i).toString()+"' "
//				+ "and a.strType='GL Code' "
//				+ "and date(c.dteVouchDate) between '" + fromDate + "' and '" + toDate + "' and c.strClientCode='"+clientCode+"' "
//				+ "group by a.strAccountCode ");
		
		sbSql.append("select a.strType,b.strCategory,b.strGroupName,ifnull(d.strCrDr,''),ifnull(sum(d.dblDrAmt),0),a.strAccountName,a.strAccountCode from  tblacgroupmaster b,tblacmaster a "
				+" left outer join "+dtlTableName+" d on  a.strAccountCode=d.strAccountCode " 
				+" left outer join "+hdTableName+"  c on c.strVouchNo=d.strVouchNo   and date(c.dteVouchDate) between '" + fromDate + "' and '" + toDate + "' and c.strClientCode='"+clientCode+"' "
				+ "where a.strGroupCode=b.strGroupCode "
				+ "and a.strAccountCode='"+lisAccCode.get(i).toString()+"' "
				+ "and a.strType='GL Code' "
				+ "group by a.strAccountCode  ");
		
		
		List listJV = objGlobalFunctionsService.funGetListModuleWise(sbSql.toString(), "sql");
		if (listJV != null && listJV.size() > 0)
		{
			for(int cn=0;cn<listJV.size();cn++)
			{
				Object[] objArr = (Object[]) listJV.get(cn);

				String groupCategory = objArr[1].toString();
				String groupName = objArr[2].toString();
				BigDecimal totalAmount = BigDecimal.valueOf(Double.parseDouble(objArr[4].toString()));
				String accountCode=objArr[6].toString();
				clsIncomeStmtReportBean objBean = new clsIncomeStmtReportBean();
				
				if(hmIncomeStatement.containsKey(accountCode))
				{
					objBean=hmIncomeStatement.get(accountCode);
					objBean.setDblValue(objBean.getDblValue().add(totalAmount));
				}
				else
				{
					objBean.setStrGroupCategory(groupCategory);
					objBean.setStrGroupName(groupName);
					objBean.setDblValue(totalAmount);
					objBean.setStrAccountName(objArr[5].toString());
				}
				hmIncomeStatement.put(accountCode, objBean);
			}
		}
	 }
	}
	
    public List<clsReportBean> funGetSalesAndCost(String fDate,String tDate,double conversionRate, HttpServletRequest req)
    {
		StringBuilder sbSql = new StringBuilder();
//    	String fDate = objGlobalfunction.funGetDate("yyyy-MM-dd", objBean.getDteFromDate());
//		DString tDate = objGlobalfunction.funGetDate("yyyy-MM-dd", objBean.getDteToDate());
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String locationCode = req.getSession().getAttribute("locationCode").toString();
	
		Map<Integer, List<String>> hmProductProfit = new HashMap<Integer,List<String>>();
		sbSql.setLength(0);
		sbSql.append("SELECT b.strProdCode,c.strProdName,c.dblCostRM,b.dblQty, b.dblUnitPrice, b.dblQty*b.dblUnitPrice,right(b.strProdCode,7) "
				+ " FROM tblinvoicehd a,tblinvoicedtl b,tblproductmaster c "
				+ " WHERE DATE(a.dteInvDate) BETWEEN '" + fDate + "' and '" + tDate + "' AND a.strInvCode=b.strInvCode "
				+ " AND b.strProdCode=c.strProdCode AND a.strClientCode='"+clientCode+"' "
				+ " order by b.strProdCode");
		List prodList = objGlobalFunctionsService.funGetList(sbSql.toString(), "sql");

		String prevProdCode="";
		int cnt=-1;
		
		List<clsReportBean> listProduct=new ArrayList<clsReportBean>(); 
		for(int i=0;i<prodList.size();i++)
		{
			Object[] objArr = (Object[]) prodList.get(i);
			String prodCode = objArr[0].toString();
			double qty = Double.parseDouble(objArr[3].toString());
			double purchaseAmt = (Double.parseDouble(objArr[2].toString())*qty)/conversionRate;
			double saleAmt = (Double.parseDouble(objArr[5].toString()))/conversionRate;
							
			clsReportBean objProd = new clsReportBean();
			if(prevProdCode.equals(objArr[0].toString()) )
			{
				objProd=listProduct.get(cnt);
				objProd.setDblQty(qty+objProd.getDblQty());
				objProd.setDblPurAmt(objProd.getDblPurAmt()+purchaseAmt);
				objProd.setDblSaleAmt(objProd.getDblSaleAmt()+saleAmt);
				System.out.println(prodCode+"      "+qty+"      "+purchaseAmt+"      "+saleAmt);
			}
			else
			{
				objProd.setStrProdCode(prodCode);
				objProd.setStrProdName(objArr[1].toString());
				objProd.setDblQty(qty);
				objProd.setDblPurAmt(purchaseAmt);
				objProd.setDblSaleAmt(saleAmt);
				listProduct.add(objProd);
				cnt++;
			}
			prevProdCode=prodCode;
		}
            return listProduct;
    }
	
    	

	
	
	public int funInsertCurrentAccountBal(String acCode, String fromDate, String toDate, String userCode, String propertyCode, String clientCode)
	{
		String sql = "";

		sql = " Delete from tblcurrentaccountbal where a.strUserCode='" + userCode + "' and a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' ";
		objGlobalFunctionsService.funDeleteWebBookCurrentAccountBal(clientCode, userCode, propertyCode);

		sql = " INSERT INTO tblcurrentaccountbal (strAccountCode, strAccountName,  dteBalDate,strDrCrCode, strTransecType, dblDrAmt,dblCrAmt,dblBalAmt,strUserCode, strPropertyCode, strClientCode)  " + " (SELECT b.strAccountCode,b.strAccountName,DATE_FORMAT(DATE(a.dteVouchDate),'%d-%m-%Y'),'','JV', sum(b.dblDrAmt),sum(b.dblCrAmt),sum(b.dblDrAmt - b.dblCrAmt),'" + userCode + "','" + propertyCode
				+ "','" + clientCode + "' " + " FROM tbljvhd a, tbljvdtl b " + " WHERE a.strVouchNo=b.strVouchNo AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " AND b.strAccountCode='" + acCode + "'  AND a.strPropertyCode=b.strPropertyCode " + " AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' group by b.strAccountCode ) ";

		objGlobalFunctionsService.funUpdateAllModule(sql, "sql");

		sql = " INSERT INTO tblcurrentaccountbal (strAccountCode, strAccountName,  dteBalDate,strDrCrCode, strTransecType, dblDrAmt,dblCrAmt,dblBalAmt,strUserCode, strPropertyCode, strClientCode)  " + " (SELECT b.strAccountCode,b.strAccountName,DATE_FORMAT(DATE(a.dteVouchDate),'%d-%m-%Y'),'','Payment', sum(b.dblDrAmt),sum(b.dblCrAmt),sum(b.dblDrAmt - b.dblCrAmt),'super','01','189.001' "
				+ "  FROM tblpaymenthd a, tblpaymentdtl b " + " WHERE a.strVouchNo=b.strVouchNo AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " AND b.strAccountCode='" + acCode + "'  AND a.strPropertyCode=b.strPropertyCode " + " AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' group by b.strAccountCode ) ";

		objGlobalFunctionsService.funUpdateAllModule(sql, "sql");

		sql = " INSERT INTO tblcurrentaccountbal (strAccountCode, strAccountName,  dteBalDate,strDrCrCode, strTransecType, dblDrAmt,dblCrAmt,dblBalAmt,strUserCode, strPropertyCode, strClientCode)  " + " (SELECT b.strAccountCode,b.strAccountName,DATE_FORMAT(DATE(a.dteVouchDate),'%d-%m-%Y'),'','Receipt', sum(b.dblDrAmt),sum(b.dblCrAmt),sum(b.dblDrAmt - b.dblCrAmt),'super','01','189.001' "
				+ " FROM tblreceipthd a, tblreceiptdtl b  " + " WHERE a.strVouchNo=b.strVouchNo AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " AND b.strAccountCode='" + acCode + "'  AND a.strPropertyCode=b.strPropertyCode " + " AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' group by b.strAccountCode ) ";

		objGlobalFunctionsService.funUpdateAllModule(sql, "sql");

		return 1;

	}

}
