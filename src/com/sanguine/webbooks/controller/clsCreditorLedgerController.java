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
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
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
import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webbooks.bean.clsCreditorOutStandingReportBean;
import com.sanguine.webbooks.bean.clsDebtorLedgerBean;
import com.sanguine.webbooks.service.clsDebtorLedgerService;

@Controller
public class clsCreditorLedgerController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	clsDebtorLedgerService objDebtorLedgerService;

	@Autowired
	clsPaymentController objPaymentController;

	@Autowired
	clsJVController objJVController;

	@Autowired
	private clsGlobalFunctions objclsGlobalFunctions;

	@Autowired
	private clsReceiptController objclsReceiptController;

	@Autowired
	clsCurrencyMasterService objCurrencyMasterService;
	
	@Autowired
	intfBaseService objBaseService;
	
	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;
	
	// Open Debtor Ledger
	@RequestMapping(value = "/frmCreditorLedger", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") clsDebtorLedgerBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest req) {
		objDebtorLedgerService.funGetDebtorDetails("M000002", "060.001", "02");
		
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
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
		ModelAndView objModelAndView = new ModelAndView();
		return objModelAndView;
	}

	// Process and Show Creditor Ledger
	@RequestMapping(value = "/showCreditorLedger", method = RequestMethod.POST)
	public ModelAndView funShowDebtorLedger(@ModelAttribute("command") @Valid clsDebtorLedgerBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			return new ModelAndView("redirect:/frmDebtorLedger.html");
		} else {
			return new ModelAndView("frmCreditorLedger");
		}
	}

	// Get Creditor Details
	@RequestMapping(value = "/getCreditorLedger", method = RequestMethod.GET)
	public @ResponseBody List funLoadMemberDetails(@RequestParam(value = "param1") String param1, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate,@RequestParam(value = "currency") String currency, HttpServletRequest req, HttpServletResponse resp) {
		List retList = new ArrayList();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		
		String userCode = req.getSession().getAttribute("usercode").toString();
		String[] spParam1 = param1.split(",");
		String glCode = spParam1[0];
		String creditorCode = spParam1[1];
		String prodCode = spParam1[2];
		String fromDate = objGlobal.funGetDate("yyyy-MM-dd", fDate);
		String toDate = objGlobal.funGetDate("yyyy-MM-dd", tDate);

		double conversionRate=1;
		String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster where strCurrencyCode='"+currency+"' and strClientCode='"+clientCode+"' ");
		try
		{
			List list = objBaseService.funGetList(sbSql,"sql");
			conversionRate=Double.parseDouble(list.get(0).toString());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		startDate = startDate.split("/")[2] + "-" + startDate.split("/")[1] + "-" + startDate.split("/")[0];

		objclsGlobalFunctions.funInvokeWebBookLedger(glCode, creditorCode, startDate, propertyCode, fromDate, toDate, clientCode, userCode, req, resp, "Creditor",currency);


		String hql = "  from clsLedgerSummaryModel a where a.strUserCode='" + userCode + "' and a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' order by a.dteVochDate,a.strTransTypeForOrderBy asc ";

		List listBillLedger = objGlobalFunctionsService.funGetListModuleWise(hql, "hql");

		return listBillLedger;
	}

	@RequestMapping(value = "/openSlipLedger", method = RequestMethod.GET)
	private void funCallJsperReport(@RequestParam("docCode") String docCode, HttpServletResponse resp, HttpServletRequest req) {
		String[] sp = docCode.split(",");
		docCode = sp[0];
		double conversionRate=1;
		double currValue = Double.parseDouble(req.getSession().getAttribute("currValue").toString());
		switch (sp[1]) {
		case "Payment":
			objPaymentController.funCallPaymentdtlReport(docCode, "pdf", resp, req, currValue,conversionRate);
			break;

		case "JV":
			objJVController.funCallJVdtlReport(docCode, "pdf", resp, req,conversionRate);
			break;

		case "Recepit":
			objclsReceiptController.funCallReciptdtlReport(docCode, "pdf", resp, req, currValue,conversionRate);
			break;

		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/frmExportLedger", method = RequestMethod.GET)
	private ModelAndView funExportLedger(@RequestParam(value = "param1") String param1, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, HttpServletRequest req, HttpServletResponse resp) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String[] spParam1 = param1.split(",");
		String glCode = spParam1[0];
		String creditorCode = spParam1[1];
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		double currValue = 1.0;
			
			String currency=req.getParameter("currency").toString();
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currency, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
	
			}

		String fromDate = objGlobal.funGetDate("yyyy-MM-dd", fDate);
		String toDate = objGlobal.funGetDate("yyyy-MM-dd", tDate);
		List listLedger = new ArrayList();
		listLedger.add("Ledger_" + fromDate + "to" + toDate + "_" + userCode);
		String[] ExcelHeader = { "Transaction Date", "Transaction Type", "Ref No", "Chq/BillNo", "Bill Date", "Dr", "Cr", "Balance" };
		listLedger.add(ExcelHeader);

		String sql = " SELECT DATE_FORMAT(DATE(dteVochDate),'%d-%m-%Y'),strTransType,strVoucherNo,strChequeBillNo,DATE_FORMAT(DATE(dteBillDate),'%d-%m-%Y'),dblDebitAmt/" + currValue + ",dblCreditAmt/" + currValue + ",dblBalanceAmt/" + currValue + " " + " from tblledgersummary where strUserCode='" + userCode + "' and strPropertyCode='" + propertyCode + "' AND strClientCode='" + clientCode + "'  " + " order by dteVochDate, strTransTypeForOrderBy ";

		/*
		 * String sql=
		 * " select DATE_FORMAT(DATE(TransDate),'%d-%m-%Y'),TransName,RefNo,Chq_BillNo,BillDate,Dr,Cr from ( "
		 * ; sql+=
		 * "  select a.dteVouchDate TransDate,'1' TransType,'JV' TransName,a.strVouchNo RefNo,c.strInvoiceNo Chq_BillNo,DATE_FORMAT(DATE(c.dteInvoiceDate),'%d-%m-%Y') BillDate,b.dblDrAmt Dr,b.dblCrAmt Cr "
		 * + " from tbljvhd a, tbljvdtl b,tbljvdebtordtl c " +
		 * " where a.strVouchNo=b.strVouchNo and a.strVouchNo=c.strVouchNo " +
		 * " and date(a.dteVouchDate) between '"+fromDate+"' and '"+toDate+"' "
		 * +
		 * " and b.strAccountCode='"+glCode+"'and c.strDebtorCode='"+creditorCode
		 * +"' and a.strPropertyCode=b.strPropertyCode " +
		 * " and a.strPropertyCode='"
		 * +propertyCode+"' and a.strClientCode='"+clientCode+"' " +
		 * " Union all " +
		 * " select a.dteVouchDate TransDate,'2' TransType,'Payment' TransName,a.strVouchNo RefNo,a.strChequeNo Chq_BillNo,DATE_FORMAT(DATE(a.dteChequeDate),'%d-%m-%Y')  BillDate,b.dblDrAmt Dr, b.dblCrAmt Cr "
		 * + " from tblpaymenthd a, tblpaymentdtl b " +
		 * " where a.strVouchNo=b.strVouchNo " +
		 * " and date(a.dteVouchDate) between '"+fromDate+"' and '"+toDate+"' "
		 * +
		 * " AND b.strAccountCode ='"+glCode+"' and b.strDebtorCode='"+creditorCode
		 * +"' and a.strPropertyCode=b.strPropertyCode " +
		 * " and a.strPropertyCode='"
		 * +propertyCode+"' and a.strClientCode='"+clientCode+"' " +
		 * " ) ledger " + " order by TransDate, TransType ";
		 */
		double debit = 0.0;
		double credit = 0.0;
		
		List listBillLedger = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		List objList = new ArrayList();
		double bal = 0.00;
		
		for (int i = 0; i < listBillLedger.size(); i++) {
			Object[] obj = (Object[]) listBillLedger.get(i);
			bal = bal + Double.parseDouble(obj[5].toString()) - Double.parseDouble(obj[6].toString());
			List listemp = new ArrayList();
			debit=debit+Double.parseDouble(obj[5].toString());
			credit=credit+Double.parseDouble(obj[6].toString());
			listemp.add(obj[0]);
			listemp.add(obj[1]);
			listemp.add(obj[2]);
			listemp.add(obj[3]);
			listemp.add(obj[4]);
			
			if(Double.parseDouble(obj[5].toString())< 0){
				double de=Double.parseDouble(obj[5].toString())*-1; 
				listemp.add("("+de+")");
			}else{
				listemp.add(obj[5]);
			}
			
			if(Double.parseDouble(obj[6].toString())< 0){
				double cre=Double.parseDouble(obj[6].toString())*-1; 
				listemp.add("("+cre+")");
			}else{
				listemp.add(obj[6]);
			}
			
			if(bal < 0){
				listemp.add("("+bal*-1+")");
				
			}else{
				listemp.add(bal);
			}
			
			
			objList.add(listemp);
			
		}
		
		listLedger.add(objList);
		
		// Summary Detail
		
		List listemp = new ArrayList();
		
		listemp.add("");
		listemp.add("");
		listemp.add("");
		listemp.add("");
		listemp.add("");
		listemp.add("");
		listemp.add("");
		listemp.add("");
		objList.add(listemp);
		
		listemp = new ArrayList();
		
		listemp.add("Transaction Type");
		listemp.add("Amount");
		listemp.add("");
		listemp.add("");
		listemp.add("");
		listemp.add("");
		listemp.add("");
		listemp.add("");
		objList.add(listemp);
		
		
		listemp = new ArrayList();
		
		listemp.add("Total Debit");
	
		if(debit < 0){
			listemp.add("("+debit*-1+")");
			
		}else{
			listemp.add(debit);
		}
		
		listemp.add("");
		listemp.add("");
		listemp.add("");
		listemp.add("");
		listemp.add("");
		listemp.add("");
		objList.add(listemp);
		
		
		
		listemp = new ArrayList();
		
		listemp.add("Total Credit");
		
		if(credit < 0){
			listemp.add("("+credit*-1+")");
			
		}else{
			listemp.add(credit);
		}
		
		listemp.add("");
		listemp.add("");
		listemp.add("");
		listemp.add("");
		listemp.add("");
		listemp.add("");
		objList.add(listemp);
		
		
		
		listemp = new ArrayList();
		
		listemp.add("Closing Balance");
		
		if((debit-credit) < 0){
			listemp.add("("+ (debit-credit)*-1 +")");
			
		}else{
			listemp.add(debit-credit);
		}
		
		listemp.add("");
		listemp.add("");
		listemp.add("");
		listemp.add("");
		listemp.add("");
		listemp.add("");
		objList.add(listemp);
		
		
		return new ModelAndView("excelViewWithReportName", "listWithReportName", listLedger);
	}
	
	@RequestMapping(value = "/rptCreditorReport", method = RequestMethod.GET)
	private void funCreditorLedgerPDF(HttpServletResponse resp, HttpServletRequest req) 
	{
		Connection con = objGlobal.funGetConnection(req);
		try
		{
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		double currValue = 1.0;
		
		String currency=req.getParameter("currency").toString();
		clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currency, clientCode);
		if (objCurrModel != null) {
			currValue = objCurrModel.getDblConvToBaseCurr();

		}
		String creditorCode=req.getParameter("creditorCode").toString();
		String fromDate=req.getParameter("fromDate").toString();
		String toDate=req.getParameter("toDate").toString();
		String ledgerName =req.getParameter("ledgerName").toString();
		String glCode =req.getParameter("glCode").toString();
		String glName =req.getParameter("glName").toString();
		String creditorName =req.getParameter("creditorName").toString();
		
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		if (objSetup == null) {
			objSetup = new clsPropertySetupModel();
		}
		String reportName = servletContext.getRealPath("/WEB-INF/reports/webbooks/rptCreditorsLedgerToolReport.jrxml");
		String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
		String fd = fromDate.split("-")[0];
		String fm = fromDate.split("-")[1];
		String fy = fromDate.split("-")[2];

		String td = toDate.split("-")[0];
		String tm = toDate.split("-")[1];
		String ty = toDate.split("-")[2];

		String dteFromDate = fy + "-" + fm + "-" + fd;
		String dteToDate = ty + "-" + tm + "-" + td;
		ArrayList fieldList = new ArrayList();
		String sql = " SELECT DATE_FORMAT(DATE(dteVochDate),'%d-%m-%Y'),strTransType,strVoucherNo,strChequeBillNo,DATE_FORMAT(DATE(dteBillDate),'%d-%m-%Y'),dblDebitAmt/" + currValue + ",dblCreditAmt/" + currValue + ",dblBalanceAmt/" + currValue + " " + " from tblledgersummary where strUserCode='" + userCode + "' and strPropertyCode='" + propertyCode + "' AND strClientCode='" + clientCode + "'  " + " order by dteVochDate, strTransTypeForOrderBy ";
		List listDtl = objGlobalFunctionsService.funGetDataList(sql, "sql");

		
		if (listDtl.size() > 0 && listDtl != null) 
		{
			double prevBal = 0.0;
			double balanceAmt=0.0;
			for (int j = 0; j < listDtl.size(); j++) 
			{
				Object[] obj = (Object[]) listDtl.get(j);
				clsCreditorOutStandingReportBean objBeanData = new clsCreditorOutStandingReportBean();
				if(j==0)
				{
					prevBal = 0.0;
					objBeanData = new clsCreditorOutStandingReportBean();
					objBeanData.setDteVouchDate(obj[0].toString());
					objBeanData.setStrTransectionName(obj[1].toString());
					objBeanData.setStrVouchNo(obj[2].toString());
					objBeanData.setStrBillNo(obj[3].toString());
					objBeanData.setDteBillDate(obj[4].toString());
					objBeanData.setDblDrAmt(Double.parseDouble(obj[5].toString()));
					objBeanData.setDblCrAmt(Double.parseDouble(obj[6].toString()));
					prevBal = Double.parseDouble(obj[5].toString())-Double.parseDouble(obj[6].toString());
					objBeanData.setDblBalAmt(prevBal);
					
				}
				else
				{
					objBeanData = new clsCreditorOutStandingReportBean();
					objBeanData.setDteVouchDate(obj[0].toString());
					objBeanData.setStrTransectionName(obj[1].toString());
					objBeanData.setStrVouchNo(obj[2].toString());
					objBeanData.setStrBillNo(obj[3].toString());
					objBeanData.setDteBillDate(obj[4].toString());
					objBeanData.setDblDrAmt(Double.parseDouble(obj[5].toString()));
					objBeanData.setDblCrAmt(Double.parseDouble(obj[6].toString()));
					if(j==1)
					{
					balanceAmt = prevBal + Double.parseDouble(obj[5].toString()) - Double.parseDouble(obj[6].toString());
					}
					else
					{
					balanceAmt = balanceAmt  + Double.parseDouble(obj[5].toString()) - Double.parseDouble(obj[6].toString());
					}
					objBeanData.setDblBalAmt(balanceAmt);
					
				}
				fieldList.add(objBeanData);
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
		
		hm.put("glCode", glCode);
		hm.put("glName", glName);
		hm.put("creditorCode", creditorCode);
		hm.put("creditorName", creditorName);
		if(ledgerName.equalsIgnoreCase("creditorLedger"))
		{
			hm.put("reportHeading", "Creditor Ledger");
			hm.put("strCreditorCode", "Creditor Code");
			hm.put("strCreditorName", "Creditor Name");
		}
		else
		{
			hm.put("reportHeading", "Debtor Ledger");
			hm.put("strCreditorCode", "Debtor Code");
			hm.put("strCreditorName", "Debtor Name");
		}

		List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
		// jprintlist.add(jp);

		JasperDesign jd = JRXmlLoader.load(reportName);
		JasperReport jr = JasperCompileManager.compileReport(jd);
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
		JasperPrint print = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
		jprintlist.add(print);
		ServletOutputStream servletOutputStream = resp.getOutputStream();
		if (jprintlist.size() > 0) {
			JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				if(ledgerName.equalsIgnoreCase("creditorLedger"))
				{
				resp.setHeader("Content-Disposition", "inline;filename=CreditorLedger_" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".pdf");
				}
				else
				{
				resp.setHeader("Content-Disposition", "inline;filename=DebtorLedger_" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".pdf");
				}
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();

			

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
	
	@RequestMapping(value = "/loadCurrencyForCreditor", method = RequestMethod.GET)
	public @ResponseBody String funLoadCurrencyForCreditor(@RequestParam(value = "creditorCode") String creditorCode, HttpServletRequest req, HttpServletResponse resp) 
	{
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String currency="";
		List list = objclsGlobalFunctions.funGetDebotCreditorCurrency(creditorCode, clientCode, propertyCode);
		if(list.size()>0)
		{
			currency = list.get(0).toString();
		}
		return currency;
	}
	


}
