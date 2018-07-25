package com.sanguine.webbooks.controller;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.sanguine.util.clsReportBean;
import com.sanguine.webbooks.bean.clsCreditorOutStandingReportBean;
import com.sanguine.webbooks.bean.clsTaxRegisterReportFields;

@Controller
public class clsTaxRegisterController
{

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private intfBaseService objBaseService;

	@Autowired
	clsCurrencyMasterService objCurrencyMasterService;

	// Open Buget Form
	@RequestMapping(value = "/frmTaxRegister", method = RequestMethod.GET)
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
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		Map<String, String> hmCurrency = objCurrencyMasterService.funCurrencyListToDisplay(clientCode);
		if (hmCurrency.isEmpty())
		{
			hmCurrency.put("", "");
		}
		model.put("currencyList", hmCurrency);

		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits))
		{
			return new ModelAndView("frmTaxRegister_1", "command", new clsCreditorOutStandingReportBean());
		}
		else if ("1".equalsIgnoreCase(urlHits))
		{
			return new ModelAndView("frmTaxRegister", "command", new clsCreditorOutStandingReportBean());
		}
		else
		{
			return null;
		}

	}

	@RequestMapping(value = "/rptTaxRegister", method = RequestMethod.GET)
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
			String webStockDB = req.getSession().getAttribute("WebStockDB").toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null)
			{
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
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webbooks/rptTaxRegister.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlQuery = "select b.strInvCode,DATE_FORMAT(date(c.dteInvDate),'%d-%m-%Y') as dteInvDate ,b.dblTaxableAmt,b.dblTaxAmt,a.strTaxDesc,d.strSettlementDesc, c.strUserCreated " + " from  " + webStockDB + ".tbltaxhd a," + webStockDB + ".tblinvtaxdtl b," + webStockDB + ".tblinvoicehd c ," + webStockDB + ".tblsettlementmaster d " + " where a.strTaxCode=b.strTaxCode and b.strInvCode=c.strInvCode and c.strSettlementCode=d.strSettlementCode  " + " and c.dteInvDate between '" + dteFromDate + "' and '" + dteToDate + "' and c.strClientCode='" + clientCode + "' and a.strTaxOnSP='Sales'  ";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery taxQuery = new JRDesignQuery();
			taxQuery.setText(sqlQuery);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset taxDataset = (JRDesignDataset) datasetMap.get("dsTax");
			taxDataset.setQuery(taxQuery);

			String taxSummary = "select a.strTaxCode,a.strTaxDesc,ifnull(b.dblTaxableAmt,0),ifnull(b.dblTaxAmt,0) " + "from " + webStockDB + ".tbltaxhd a left outer join " + webStockDB + ".tblinvtaxdtl b " + " on a.strTaxCode=b.strTaxCode  where a.strTaxOnSP='Sales' ";

			JRDesignQuery taxSummQuery = new JRDesignQuery();
			taxSummQuery.setText(taxSummary);
			JRDesignDataset taxSumDataset = (JRDesignDataset) datasetMap.get("dsTaxSummary");
			taxSumDataset.setQuery(taxSummQuery);

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

			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf"))
			{
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setContentLength(bytes.length);
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			}
			else if (type.trim().equalsIgnoreCase("xls"))
			{
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptTaxRegister." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
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

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getTaxRegisterDetail", method = RequestMethod.GET)
	private @ResponseBody Map<String,List<clsTaxRegisterReportFields>> funGetTaxRegister(@RequestParam(value = "fromDate") String fromDate, @RequestParam(value = "toDate") String toDate, @RequestParam(value = "currency") String currency, HttpServletRequest req, HttpServletResponse resp)
	{   
		DecimalFormat df = new DecimalFormat("####0.00");
		Map<String,List<clsTaxRegisterReportFields>> mapDetailSummaryMap=new HashMap<>();
		try
		{
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockDB = req.getSession().getAttribute("WebStockDB").toString();

			fromDate = objGlobal.funGetDate("yyyy-MM-dd", fromDate);
			toDate = objGlobal.funGetDate("yyyy-MM-dd", toDate);

			StringBuilder sbSql = new StringBuilder();
			sbSql.append("select dblConvToBaseCurr from " + webStockDB + ".tblcurrencymaster where strCurrencyCode='" + currency + "' and strClientCode='" + clientCode + "' ");
			List list = objBaseService.funGetList(sbSql, "sql");
			double conversionRate = Double.parseDouble(list.get(0).toString());

			sbSql.setLength(0);
			sbSql.append("select b.strInvCode,DATE_FORMAT(date(c.dteInvDate),'%d-%m-%Y') as dteInvDate ,a.strTaxDesc,b.dblTaxableAmt,b.dblTaxAmt,d.strSettlementDesc, c.strUserCreated ");
			sbSql.append(" from  " + webStockDB + ".tbltaxhd a," + webStockDB + ".tblinvtaxdtl b," + webStockDB + ".tblinvoicehd c ," + webStockDB + ".tblsettlementmaster d ");
			sbSql.append(" where a.strTaxCode=b.strTaxCode and b.strInvCode=c.strInvCode and c.strSettlementCode=d.strSettlementCode " + " and date(c.dteInvDate) between '" + fromDate + "' and '" + toDate + "' "
				+ " and c.strClientCode='" + clientCode + "' and a.strTaxOnSP='Sales' ");
			List listTaxRegister = objBaseService.funGetList(sbSql, "sql");

			List<clsTaxRegisterReportFields> listTaxRegisterDetail = new ArrayList<clsTaxRegisterReportFields>();
			for (int i = 0; i < listTaxRegister.size(); i++)
			{
				Object[] arrObj = (Object[]) listTaxRegister.get(i);
				clsTaxRegisterReportFields objTaxRegisterReportFields = new clsTaxRegisterReportFields();
				objTaxRegisterReportFields.setTransDocCode(arrObj[0].toString());
				objTaxRegisterReportFields.setTransDocDate(arrObj[1].toString());
				objTaxRegisterReportFields.setTaxDesc(arrObj[2].toString());
				System.out.println(df.format(Double.parseDouble(arrObj[4].toString()) * conversionRate));
				objTaxRegisterReportFields.setTaxAmount(Double.valueOf(df.format(Double.parseDouble(arrObj[4].toString()) * conversionRate)));
				objTaxRegisterReportFields.setTaxableAmount(Double.valueOf(df.format(Double.parseDouble(arrObj[3].toString()) * conversionRate)));
				listTaxRegisterDetail.add(objTaxRegisterReportFields);
			}
			
			sbSql.setLength(0);
			/*sbSql.append("select b.strPOCode,DATE_FORMAT(date(c.dtPODate),'%d-%m-%Y') as dtPODate ,a.strTaxDesc,b.strTaxableAmt,b.strTaxAmt,c.strUserCreated  ");
			sbSql.append(" from  " + webStockDB + ".tbltaxhd a," + webStockDB + ".tblpotaxdtl b," + webStockDB + ".tblpurchaseorderhd c ");
			sbSql.append(" where a.strTaxCode=b.strTaxCode and b.strPOCode=c.strPOCode  " 
				+ " and date(c.dtPODate) between '" + fromDate + "' and '" + toDate + "' "
				+ " and c.strClientCode='" + clientCode + "' and a.strTaxOnSP='Purchase' ");
			*/
			sbSql.append(" select b.strGRNCode,DATE_FORMAT(date(c.dtGRNDate),'%d-%m-%Y') as dtGRNDate ,a.strTaxDesc,b.strTaxableAmt,b.strTaxAmt,c.strUserCreated " 
						+" from   " + webStockDB + ".tbltaxhd a," + webStockDB + ".tblgrntaxdtl b," + webStockDB + ".tblgrnhd c  where a.strTaxCode=b.strTaxCode and b.strGRNCode  =c.strGRNCode "  
						+" and date(c.dtGRNDate)  between '" + fromDate + "' and '" + toDate + "'  and c.strClientCode='" + clientCode + "' and a.strTaxOnSP='Purchase' ");
			
			
			listTaxRegister = objBaseService.funGetList(sbSql, "sql");
			for (int i = 0; i < listTaxRegister.size(); i++)
			{
				Object[] arrObj = (Object[]) listTaxRegister.get(i);
				clsTaxRegisterReportFields objTaxRegisterReportFields = new clsTaxRegisterReportFields();
				objTaxRegisterReportFields.setTransDocCode(arrObj[0].toString());
				objTaxRegisterReportFields.setTransDocDate(arrObj[1].toString());
				objTaxRegisterReportFields.setTaxDesc(arrObj[2].toString());
				objTaxRegisterReportFields.setTaxAmount(Double.valueOf(df.format(Double.parseDouble(arrObj[4].toString()) * conversionRate)));
				objTaxRegisterReportFields.setTaxableAmount(Double.valueOf(df.format(Double.parseDouble(arrObj[3].toString()) * conversionRate)));
				listTaxRegisterDetail.add(objTaxRegisterReportFields);
			}
			mapDetailSummaryMap.put("TaxDetailData", listTaxRegisterDetail);
						
			
			List<clsTaxRegisterReportFields> listTaxRegisterSummaryData = new ArrayList<clsTaxRegisterReportFields>();
			sbSql.setLength(0);
			sbSql.append("select a.strTaxDesc,sum(b.dblTaxableAmt),sum(b.dblTaxAmt) ");
			sbSql.append(" from  " + webStockDB + ".tbltaxhd a," + webStockDB + ".tblinvtaxdtl b," + webStockDB + ".tblinvoicehd c ," + webStockDB + ".tblsettlementmaster d ");
			sbSql.append(" where a.strTaxCode=b.strTaxCode and b.strInvCode=c.strInvCode and c.strSettlementCode=d.strSettlementCode " + " and date(c.dteInvDate) between '" + fromDate + "' and '" + toDate + "' and c.strClientCode='" + clientCode + "' and a.strTaxOnSP='Sales'  group by a.strTaxCode ");
			List listTaxSummary = objBaseService.funGetList(sbSql, "sql");

			for (int i = 0; i < listTaxSummary.size(); i++)
			{
				Object[] arrObj = (Object[]) listTaxSummary.get(i);
				clsTaxRegisterReportFields objTaxRegisterReportFields = new clsTaxRegisterReportFields();
				
				objTaxRegisterReportFields.setTaxDesc(arrObj[0].toString());
				objTaxRegisterReportFields.setTaxAmount(Double.valueOf(df.format(Double.parseDouble(arrObj[2].toString()) * conversionRate)));
				objTaxRegisterReportFields.setTaxableAmount(Double.valueOf(df.format(Double.parseDouble(arrObj[1].toString()) * conversionRate)));
				
				listTaxRegisterSummaryData.add(objTaxRegisterReportFields);
			}
			
			sbSql.setLength(0);
			
			/*sbSql.append("select c.strTaxDesc,sum(b.strTaxableAmt),sum(b.strTaxAmt) ");
			sbSql.append(" from " + webStockDB + ".tblpurchaseorderhd a," + webStockDB + ".tblpotaxdtl b," + webStockDB + ".tbltaxhd c ");
			sbSql.append(" where a.strPOCode=b.strPOCode and a.strClientCode=b.strClientCode and b.strTaxCode=c.strTaxCode and b.strClientCode=c.strClientCode ");
			sbSql.append(" and date(a.dtPODate) between '"+fromDate+"' and '"+toDate+"' and a.strClientCode='"+clientCode+"' ");
			sbSql.append(" group by c.strTaxCode ");
			*/
			sbSql.append(" select c.strTaxDesc, SUM(b.strTaxableAmt), SUM(b.strTaxAmt)  "
			 + " from sanguine_webmms.tblgrnhd a,sanguine_webmms.tblgrntaxdtl b,sanguine_webmms.tbltaxhd c  "
			 + " where a.strGRNCode=b.strGRNCode and a.strClientCode=b.strClientCode and b.strTaxCode=c.strTaxCode  "
			 + " and b.strClientCode=c.strClientCode and DATE(a.dtGRNDate) between '"+fromDate+"' and '"+toDate+"' AND a.strClientCode='"+clientCode+"' "
			 + " group by c.strTaxCode;");
			
			listTaxSummary = objBaseService.funGetList(sbSql, "sql");

			for (int i = 0; i < listTaxSummary.size(); i++)
			{
				Object[] arrObj = (Object[]) listTaxSummary.get(i);
				clsTaxRegisterReportFields objTaxRegisterReportFields = new clsTaxRegisterReportFields();
				
				objTaxRegisterReportFields.setTaxDesc(arrObj[0].toString());
				objTaxRegisterReportFields.setTaxAmount(Double.valueOf(df.format(Double.parseDouble(arrObj[2].toString()) * conversionRate)));
				objTaxRegisterReportFields.setTaxableAmount(Double.valueOf(df.format(Double.parseDouble(arrObj[1].toString()) * conversionRate)));
				
				listTaxRegisterSummaryData.add(objTaxRegisterReportFields);
			}
			
			mapDetailSummaryMap.put("TaxSummaryData", listTaxRegisterSummaryData);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{

			return mapDetailSummaryMap;
		}
	}

	@RequestMapping(value = "/taxRegisterExcelReport", method = RequestMethod.GET)
	private ModelAndView funGenerateTaxRegisterExcel(@RequestParam(value = "fromDate") String fromDate, @RequestParam(value = "toDate") String toDate, @RequestParam(value = "currency") String currency, HttpServletRequest req, HttpServletResponse resp)
	{
		DecimalFormat df = new DecimalFormat("####0.00");
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String webStockDB = req.getSession().getAttribute("WebStockDB").toString();
		fromDate = objGlobal.funGetDate("yyyy-MM-dd", fromDate);
		toDate = objGlobal.funGetDate("yyyy-MM-dd", toDate);
		List listExcelData = new ArrayList();

		try
		{
			StringBuilder sbSql = new StringBuilder();
			sbSql.append("select dblConvToBaseCurr from " + webStockDB + ".tblcurrencymaster where strCurrencyCode='" + currency + "' and strClientCode='" + clientCode + "' ");
			List list = objBaseService.funGetList(sbSql, "sql");
			double conversionRate = Double.parseDouble(list.get(0).toString());

			double totalTaxableAmt = 0, totalTaxAmt = 0;
			String[] columnNames =
			{ "Bill No", "Bill Date", "Tax Desc", "Taxable Amt", "Tax Amt" };
			listExcelData.add(columnNames);

			sbSql.setLength(0);
			sbSql.append("select b.strInvCode,DATE_FORMAT(date(c.dteInvDate),'%d-%m-%Y') as dteInvDate ,a.strTaxDesc,b.dblTaxableAmt,b.dblTaxAmt,d.strSettlementDesc, c.strUserCreated ");
			sbSql.append(" from  " + webStockDB + ".tbltaxhd a," + webStockDB + ".tblinvtaxdtl b," + webStockDB + ".tblinvoicehd c ," + webStockDB + ".tblsettlementmaster d ");
			sbSql.append(" where a.strTaxCode=b.strTaxCode and b.strInvCode=c.strInvCode and c.strSettlementCode=d.strSettlementCode " + " and date(c.dteInvDate) between '" + fromDate + "' and '" + toDate + "' and c.strClientCode='" + clientCode + "' and a.strTaxOnSP='Sales'  ");
			List listTaxRegister = objBaseService.funGetList(sbSql, "sql");

			List<List> listTaxReg = new ArrayList<List>();
			for (int i = 0; i < listTaxRegister.size(); i++)
			{
				Object[] arrObj = (Object[]) listTaxRegister.get(i);
				List<String> listData = new ArrayList<String>();
				listData.add(arrObj[0].toString());
				listData.add(arrObj[1].toString());
				listData.add(arrObj[2].toString());
				listData.add(String.valueOf(df.format(Double.parseDouble(arrObj[3].toString()) * conversionRate)));
				listData.add(String.valueOf(df.format(Double.parseDouble(arrObj[4].toString()) * conversionRate)));
				listTaxReg.add(listData);
				totalTaxableAmt += Double.parseDouble(arrObj[3].toString()) * conversionRate;
				totalTaxAmt += Double.parseDouble(arrObj[4].toString()) * conversionRate;
			}

			
			sbSql.setLength(0);
			/*sbSql.append("select b.strPOCode,DATE_FORMAT(date(c.dtPODate),'%d-%m-%Y') as dtPODate ,a.strTaxDesc,b.strTaxableAmt,b.strTaxAmt,c.strUserCreated  ");
			sbSql.append(" from  " + webStockDB + ".tbltaxhd a," + webStockDB + ".tblpotaxdtl b," + webStockDB + ".tblpurchaseorderhd c ");
			sbSql.append(" where a.strTaxCode=b.strTaxCode and b.strPOCode=c.strPOCode  " 
				+ " and date(c.dtPODate) between '" + fromDate + "' and '" + toDate + "' "
				+ " and c.strClientCode='" + clientCode + "' and a.strTaxOnSP='Purchase' ");
			*/
			
			
			sbSql.append("SELECT b.strGRNCode, DATE_FORMAT(DATE(c.dtGRNDate),'%d-%m-%Y') AS dtGRNDate,a.strTaxDesc,b.strTaxableAmt,b.strTaxAmt,c.strUserCreated"
					+ " FROM sanguine_webmms.tbltaxhd a,sanguine_webmms.tblgrntaxdtl b,sanguine_webmms.tblgrnhd c"
					+ " WHERE a.strTaxCode=b.strTaxCode AND b.strGRNCode=c.strGRNCode "
					+ " AND DATE(c.dtGRNDate) between '" + fromDate + "' and '" + toDate + "' "
					+ " AND c.strClientCode='" + clientCode + "' AND a.strTaxOnSP='Purchase'");
			
			listTaxRegister = objBaseService.funGetList(sbSql, "sql");
			for (int i = 0; i < listTaxRegister.size(); i++)
			{
				Object[] arrObj = (Object[]) listTaxRegister.get(i);
				List<String> listData = new ArrayList<String>();
				listData.add(arrObj[0].toString());
				listData.add(arrObj[1].toString());
				listData.add(arrObj[2].toString());
				listData.add(String.valueOf(df.format(Double.parseDouble(arrObj[3].toString()) * conversionRate)));
				listData.add(String.valueOf(df.format(Double.parseDouble(arrObj[4].toString()) * conversionRate)));
				listTaxReg.add(listData);
				totalTaxableAmt += Double.parseDouble(arrObj[3].toString()) * conversionRate;
				totalTaxAmt += Double.parseDouble(arrObj[4].toString()) * conversionRate;
			}
			
			List<String> listTotals = new ArrayList<String>();
			listTotals.add("TOTALS");
			listTotals.add("");
			listTotals.add("");
			listTotals.add(String.valueOf(df.format(totalTaxableAmt)));
			listTotals.add(String.valueOf(df.format(totalTaxAmt)));
			listTaxReg.add(listTotals);

			listExcelData.add(listTaxReg);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return new ModelAndView("excelView", "stocklist", listExcelData);

	}

}
