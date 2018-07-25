package com.sanguine.webbooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
import com.sanguine.webbooks.bean.clsEmployeeLedgerBean;
import com.sanguine.webbooks.model.clsDebtorMaster;
import com.sanguine.webbooks.model.clsJVDebtorDtlModel;
import com.sanguine.webbooks.model.clsLedgerSummaryModel;
import com.sanguine.webbooks.service.clsDebtorLedgerService;

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

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class clsEmployeeLedgerController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsGlobalFunctions objclsGlobalFunctions;

	@Autowired
	clsCurrencyMasterService objCurrencyMasterService;
	
	@Autowired
	intfBaseService objBaseService;
	
	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;
	
	
	// Open Debtor Ledger
	@RequestMapping(value = "/frmEmployeeLedger", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") clsEmployeeLedgerBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest req) 
	{
		ModelAndView objModelAndView = new ModelAndView();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		
		String sql="select strAccountCode from tblacmaster where strClientCode='"+clientCode+"' and strEmployee='Yes'  and strPropertyCode = '"+propertyCode+"' ";
		List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		if (list.size() > 0) {
			objBean.setStrGLCode(list.get(0).toString());
		}		
		
		Map<String, String> hmCurrency = objCurrencyMasterService.funCurrencyListToDisplay(clientCode);
		if (hmCurrency.isEmpty()) {
			hmCurrency.put("", "");
		}
		model.put("currencyList", hmCurrency);

		return objModelAndView;
	}
	
	// Get Employee Details
	@RequestMapping(value = "/getEmployeeLedger", method = RequestMethod.GET)
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

		objclsGlobalFunctions.funInvokeWebBookLedger(glCode, creditorCode, startDate, propertyCode, fromDate, toDate, clientCode, userCode, req, resp, "Employee",currency);


		String hql = "  from clsLedgerSummaryModel a where a.strUserCode='" + userCode + "' and a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' order by a.dteVochDate,a.strTransTypeForOrderBy asc ";

		List listBillLedger = objGlobalFunctionsService.funGetListModuleWise(hql, "hql");

		return listBillLedger;
	}

	@RequestMapping(value = "/rptEmployeeReport", method = RequestMethod.GET)
	private void funCreditorLedgerPDF(HttpServletResponse resp, HttpServletRequest req) 
	{
		Connection con = objGlobal.funGetConnection(req);
		try
		{
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String currency=req.getParameter("currency").toString();
		clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currency, clientCode);
		double currValue = 1.0;
		if (objCurrModel != null) {
			currValue = objCurrModel.getDblConvToBaseCurr();

		}
		String employeeCode=req.getParameter("employeeCode").toString();
		String fromDate=req.getParameter("fromDate").toString();
		String toDate=req.getParameter("toDate").toString();
		String ledgerName =req.getParameter("ledgerName").toString();
		String glCode =req.getParameter("glCode").toString();
		String glName =req.getParameter("glName").toString();
		String employeeName =req.getParameter("employeeName").toString();
		
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
		hm.put("creditorCode", employeeCode);
		hm.put("creditorName", employeeName);
		hm.put("reportHeading", "Emnployee Ledger");
		hm.put("strCreditorCode", "Emnployee Code");
		hm.put("strCreditorName", "Emnployee Name");
		
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
				resp.setHeader("Content-Disposition", "inline;filename=EmployeeLedger_" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".pdf");
				}
				else
				{
				resp.setHeader("Content-Disposition", "inline;filename=EmployeeLedger_" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".pdf");
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


}
