package com.sanguine.webbooks.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
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
import com.sanguine.webbooks.bean.clsWebBooksAgeingReportBean;
import com.sanguine.webbooks.model.clsLedgerSummaryModel;

@Controller
public class clsWebBooksAgeingReportController
{

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

	@RequestMapping(value = "/frmWebBooksAgeingReport", method = RequestMethod.GET)
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
		clsWebBooksAgeingReportBean objBean = new clsWebBooksAgeingReportBean();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propertyCode = request.getSession().getAttribute("propertyCode").toString();
		Map<String, String> hmCurrency = objCurrencyMasterService.funCurrencyListToDisplay(clientCode);
		if (hmCurrency.isEmpty())
		{
			hmCurrency.put("", "");
		}
		model.put("currencyList", hmCurrency);

		if ("2".equalsIgnoreCase(urlHits))
		{
			return new ModelAndView("frmWebBooksAgeingReport_1", "command", objBean);
		}
		else if ("1".equalsIgnoreCase(urlHits))
		{
			return new ModelAndView("frmWebBooksAgeingReport", "command", objBean);
		}
		else
		{
			return null;
		}

	}

	@RequestMapping(value = "/getAgeingData", method = RequestMethod.GET)
	public @ResponseBody List<clsCreditorOutStandingReportBean> funGetAgeingData(HttpServletRequest request, HttpServletResponse response)
	{

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propertyCode = request.getSession().getAttribute("propertyCode").toString();

		String glCode = request.getParameter("glCode").toString();
		String fromDate = request.getParameter("fromDate").toString();
		String toDate = request.getParameter("toDate").toString();
		String toDateInddMMyyyy = toDate;

		String currency = request.getParameter("currency").toString();
		String DCType = "Debtor";

		fromDate = objGlobalFunctions.funGetDate("yyyy-MM-dd", fromDate);
		toDate = objGlobalFunctions.funGetDate("yyyy-MM-dd", toDate);

		List<clsCreditorOutStandingReportBean> listOfAgeingData = new ArrayList<>();

		try
		{

			String startDateInddMMyyyy = request.getSession().getAttribute("startDate").toString();
			String startDate = startDateInddMMyyyy.split("/")[2] + "-" + startDateInddMMyyyy.split("/")[1] + "-" + startDateInddMMyyyy.split("/")[0];
			SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

			Date date1 = objSimpleDateFormat.parse(toDateInddMMyyyy);
			int endDateYear = date1.getYear() + 1900;
			int endDateMonth = date1.getMonth() + 1;
			int endDateDay = date1.getDate();

			// Date dteEndDate = new Date(endDateYear, endDateMonth, endDateDay);

			LocalDate endDateLocalDate = LocalDate.of(endDateYear, endDateMonth, endDateDay);

			Date date2 = objSimpleDateFormat.parse(toDateInddMMyyyy);
			int startDateYear = date2.getYear() + 1900;
			int startDateMonth = date2.getMonth() + 1;
			int startDateDay = date2.getDate();

			Date dteStartDate = new Date(startDateYear, startDateMonth, startDateDay);

			LocalDate startDateLocalDate = LocalDate.of(endDateYear, endDateMonth, endDateDay);

			Map<String, Double> mapDebtorBal = new HashMap<>();

			Map<String, clsCreditorOutStandingReportBean> mapDrOpeningBal = funCalCreditorDebtorOPBalance(glCode, clientCode, startDate, fromDate, DCType, request);

			for (Map.Entry<String, clsCreditorOutStandingReportBean> entryOpBal : mapDrOpeningBal.entrySet())
			{
				String code = entryOpBal.getKey();
				
				mapDebtorBal.put(code,entryOpBal.getValue().getDblBalAmt());

			}

			Map<String, Map<String, clsCreditorOutStandingReportBean>> mapDaysInterval = new HashMap<>();

			// mapDaysInterval.put("0-30", mapDrOpeningBal);

			Map<String, clsCreditorOutStandingReportBean> hmOutstanding0_30 = new HashMap<String, clsCreditorOutStandingReportBean>();
			Map<String, clsCreditorOutStandingReportBean> hmOutstanding31_60 = new HashMap<String, clsCreditorOutStandingReportBean>();
			Map<String, clsCreditorOutStandingReportBean> hmOutstanding61_90 = new HashMap<String, clsCreditorOutStandingReportBean>();
			Map<String, clsCreditorOutStandingReportBean> hmOutstanding91_120 = new HashMap<String, clsCreditorOutStandingReportBean>();
			Map<String, clsCreditorOutStandingReportBean> hmOutstanding121_150 = new HashMap<String, clsCreditorOutStandingReportBean>();

			for (Map.Entry<String, clsCreditorOutStandingReportBean> opBalEntry : mapDrOpeningBal.entrySet())
			{
				String debtorCode = opBalEntry.getKey();
				clsCreditorOutStandingReportBean objOpBal = opBalEntry.getValue();

				clsCreditorOutStandingReportBean obj0 = new clsCreditorOutStandingReportBean();
				obj0.setStrDebtorCode(objOpBal.getStrDebtorCode());
				obj0.setStrDebtorName(objOpBal.getStrDebtorName());
				obj0.setDblBalAmt(0.00);
				obj0.setDblCrAmt(0.00);
				obj0.setDblDrAmt(0.00);
				obj0.setDblOpnAmt(0.00);

				clsCreditorOutStandingReportBean obj1 = new clsCreditorOutStandingReportBean();
				obj1.setStrDebtorCode(objOpBal.getStrDebtorCode());
				obj1.setStrDebtorName(objOpBal.getStrDebtorName());
				obj1.setDblBalAmt(0.00);
				obj1.setDblCrAmt(0.00);
				obj1.setDblDrAmt(0.00);
				obj1.setDblOpnAmt(0.00);

				clsCreditorOutStandingReportBean obj2 = new clsCreditorOutStandingReportBean();
				obj2.setStrDebtorCode(objOpBal.getStrDebtorCode());
				obj2.setStrDebtorName(objOpBal.getStrDebtorName());
				obj2.setDblBalAmt(0.00);
				obj2.setDblCrAmt(0.00);
				obj2.setDblDrAmt(0.00);
				obj2.setDblOpnAmt(0.00);

				clsCreditorOutStandingReportBean obj3 = new clsCreditorOutStandingReportBean();
				obj3.setStrDebtorCode(objOpBal.getStrDebtorCode());
				obj3.setStrDebtorName(objOpBal.getStrDebtorName());
				obj3.setDblBalAmt(0.00);
				obj3.setDblCrAmt(0.00);
				obj3.setDblDrAmt(0.00);
				obj3.setDblOpnAmt(0.00);

				clsCreditorOutStandingReportBean obj4 = new clsCreditorOutStandingReportBean();
				obj4.setStrDebtorCode(objOpBal.getStrDebtorCode());
				obj4.setStrDebtorName(objOpBal.getStrDebtorName());
				obj4.setDblBalAmt(0.00);
				obj4.setDblCrAmt(0.00);
				obj4.setDblDrAmt(0.00);
				obj4.setDblOpnAmt(0.00);

				hmOutstanding0_30.put(debtorCode, obj0);
				hmOutstanding31_60.put(debtorCode, obj1);
				hmOutstanding61_90.put(debtorCode, obj2);
				hmOutstanding91_120.put(debtorCode, obj3);
				hmOutstanding121_150.put(debtorCode, obj4);

			}
			mapDaysInterval.put("0-30", mapDrOpeningBal);
			// mapDaysInterval.put("0-30", hmOutstanding31_60);
			mapDaysInterval.put("31-60", hmOutstanding31_60);
			mapDaysInterval.put("61-90", hmOutstanding61_90);
			mapDaysInterval.put("91-120", hmOutstanding91_120);
			mapDaysInterval.put("121-150", hmOutstanding121_150);

			for (int i = 0; i < 5; i++)
			{

				System.out.println("" + startDateLocalDate.toString());
				startDateLocalDate = startDateLocalDate.minusDays(30);
				System.out.println("" + startDateLocalDate.toString());

				int fromDateYear = startDateLocalDate.getYear();
				int fromDateMonth = startDateLocalDate.getMonthValue();
				int fromDateDay = startDateLocalDate.getDayOfMonth();

				fromDate = fromDateYear + "-" + fromDateMonth + "-" + fromDateDay;

				String key = "0-30";
				if (i == 0)
				{
					key = "0-30";
				}
				if (i == 1)
				{
					key = "31-60";
				}
				if (i == 2)
				{
					key = "61-90";
				}

				if (i == 3)
				{
					key = "91-120";
				}

				if (i == 4)
				{
					key = "121-150";
				}

				Map<String, clsCreditorOutStandingReportBean> mapNewDrOutstanding = funCalCreditorDebtorOutstanding(glCode, clientCode, fromDate, toDate, DCType, request, mapDrOpeningBal);

				if (mapDaysInterval.containsKey(key))
				{
					Map<String, clsCreditorOutStandingReportBean> mapOldDrOutstanding = mapDaysInterval.get(key);

					for (Map.Entry<String, clsCreditorOutStandingReportBean> opNewBalEntry : mapNewDrOutstanding.entrySet())
					{
						String newDebtorCode = opNewBalEntry.getKey();
						clsCreditorOutStandingReportBean newDebotr = opNewBalEntry.getValue();

						if (mapOldDrOutstanding.containsKey(newDebtorCode))
						{
							clsCreditorOutStandingReportBean oldDebotr = mapOldDrOutstanding.get(newDebtorCode);

							oldDebotr.setDblBalAmt(oldDebotr.getDblBalAmt() + newDebotr.getDblBalAmt());
							oldDebotr.setDblCrAmt(oldDebotr.getDblCrAmt() + newDebotr.getDblCrAmt());
							oldDebotr.setDblDrAmt(oldDebotr.getDblDrAmt() + newDebotr.getDblDrAmt());
							// oldDebotr.setDblOpnAmt(oldDebotr.getDblOpnAmt() + newDebotr.getDblOpnAmt());

							mapDebtorBal.put(newDebtorCode, oldDebotr.getDblBalAmt());
						}
						else
						{
							continue;
						}

					}

				}

				System.out.println("" + endDateLocalDate.toString());
				endDateLocalDate = endDateLocalDate.minusDays(30);
				System.out.println("" + endDateLocalDate.toString());

				int toDateYear = endDateLocalDate.getYear();
				int toDateMonth = endDateLocalDate.getMonthValue();
				int toDateDay = endDateLocalDate.getDayOfMonth();

				toDate = toDateYear + "-" + toDateMonth + "-" + toDateDay;

			}

			Map<String, clsCreditorOutStandingReportBean> mapNewDebtor = new HashMap<>();

			for (Map.Entry<String, Map<String, clsCreditorOutStandingReportBean>> entryDaysInterval : mapDaysInterval.entrySet())
			{
				String days = entryDaysInterval.getKey();

				Map<String, clsCreditorOutStandingReportBean> mapDebtorOutstanding = entryDaysInterval.getValue();

				for (Map.Entry<String, clsCreditorOutStandingReportBean> debtorEntry : mapDebtorOutstanding.entrySet())
				{
					String newDebtorCode = debtorEntry.getKey();
					clsCreditorOutStandingReportBean oldDebotr = debtorEntry.getValue();

					if (mapNewDebtor.containsKey(newDebtorCode))
					{
						clsCreditorOutStandingReportBean newDebotr = mapNewDebtor.get(newDebtorCode);

						switch (days)
						{
						case "0-30":
							newDebotr.setDblCol0(newDebotr.getDblCol0() + oldDebotr.getDblDrAmt());

							break;

						case "31-60":
							newDebotr.setDblCol1(newDebotr.getDblCol1() + oldDebotr.getDblDrAmt());
							break;

						case "61-90":
							newDebotr.setDblCol2(newDebotr.getDblCol2() + oldDebotr.getDblDrAmt());
							break;

						case "91-120":
							newDebotr.setDblCol3(newDebotr.getDblCol3() + oldDebotr.getDblDrAmt());
							break;

						case "121-150":
							newDebotr.setDblCol4(newDebotr.getDblCol4() + oldDebotr.getDblDrAmt());
							break;
						}

						mapNewDebtor.put(newDebtorCode, newDebotr);
					}
					else
					{
						switch (days)
						{
						case "0-30":
							oldDebotr.setDblCol0(oldDebotr.getDblDrAmt());
							break;

						case "31-60":
							oldDebotr.setDblCol1(oldDebotr.getDblDrAmt());
							break;

						case "61-90":
							oldDebotr.setDblCol2(oldDebotr.getDblDrAmt());
							break;

						case "91-120":
							oldDebotr.setDblCol3(oldDebotr.getDblDrAmt());
							break;

						case "121-150":
							oldDebotr.setDblCol4(oldDebotr.getDblDrAmt());
							break;
						}
						mapNewDebtor.put(newDebtorCode, oldDebotr);
					}
				}
			}

			Comparator<clsCreditorOutStandingReportBean> debtorNameComparator = new Comparator<clsCreditorOutStandingReportBean>()
			{

				@Override
				public int compare(clsCreditorOutStandingReportBean o1, clsCreditorOutStandingReportBean o2)
				{
					return (o1.getStrDebtorName().compareToIgnoreCase(o2.getStrDebtorName()));
				}
			};

			/*
			 * fromDate = request.getParameter("fromDate").toString(); toDate =
			 * request.getParameter("toDate").toString(); String type = "PDF"; String fd =
			 * fromDate.split("-")[0]; String fm = fromDate.split("-")[1]; String fy =
			 * fromDate.split("-")[2];
			 * 
			 * String td = toDate.split("-")[0]; String tm = toDate.split("-")[1]; String ty
			 * = toDate.split("-")[2];
			 * 
			 * String dteFromDate = fy + "-" + fm + "-" + fd; String dteToDate = ty + "-" +
			 * tm + "-" + td;
			 * 
			 * startDate = request.getSession().getAttribute("startDate").toString();
			 * startDate = startDate.split("/")[2] + "-" + startDate.split("/")[1] + "-" +
			 * startDate.split("/")[0];
			 * 
			 * SimpleDateFormat obj = new SimpleDateFormat("dd-MM-yyyy"); Date dt1 =
			 * obj.parse(fromDate); GregorianCalendar cal = new GregorianCalendar();
			 * cal.setTime(dt1); cal.add(Calendar.DATE, +1); String newfromDate =
			 * (cal.getTime().getYear() + 1900) + "-" + (cal.getTime().getMonth() + 1) + "-"
			 * + (cal.getTime().getDate());
			 * 
			 * double conversionRate = 1; String webStockDB =
			 * request.getSession().getAttribute("WebStockDB").toString(); StringBuilder
			 * sbSql = new StringBuilder();
			 * 
			 * String currencyCode = currency; sbSql.append("select dblConvToBaseCurr from "
			 * + webStockDB + ".tblcurrencymaster where strCurrencyCode='" + currencyCode +
			 * "' and strClientCode='" + clientCode + "' "); try { List list =
			 * objBaseService.funGetList(sbSql, "sql"); conversionRate =
			 * Double.parseDouble(list.get(0).toString()); } catch (Exception e) {
			 * e.printStackTrace(); }
			 * 
			 * Map<String, clsCreditorOutStandingReportBean> hmOutstanding =
			 * objGlobalFunctions.funCalCreditorDebtorOPBalance(glCode, clientCode,
			 * startDate, dteFromDate, "Debtor", request);
			 * 
			 * sbSql.setLength(0); sbSql.
			 * append("select debtor.strDebtorCode,'',debtor.strCrDr, sum(ifnull(debtor.dblAmt,0.00)) "
			 * +
			 * " from tbljvdebtordtl debtor inner join tbljvhd hd on hd.strVouchNo=debtor.strVouchNo "
			 * + " where debtor.strAccountCode='" + glCode +
			 * "' and debtor.strCrDr='Cr' and date(hd.dteVouchDate) between '" + newfromDate
			 * + "' AND '" + dteToDate + "' " + " and hd.strClientCode='" + clientCode +
			 * "' " + " group by debtor.strCrDr,debtor.strDebtorCode "); List listJVAmt =
			 * objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks"); if (listJVAmt
			 * != null && listJVAmt.size() > 0) { for (int j = 0; j < listJVAmt.size(); j++)
			 * { Object[] arrObj = (Object[]) listJVAmt.get(j);
			 * clsCreditorOutStandingReportBean objOutStBean = new
			 * clsCreditorOutStandingReportBean(); if
			 * (hmOutstanding.containsKey(arrObj[0].toString())) { objOutStBean =
			 * hmOutstanding.get(arrObj[0].toString()); double creditAmt =
			 * objOutStBean.getDblCrAmt() + Double.parseDouble(arrObj[3].toString());
			 * objOutStBean.setDblCrAmt(creditAmt); //
			 * objOutStBean.setDblDrAmt(objOutStBean.getDblBalAmt()); } else {
			 * objOutStBean.setStrDebtorCode(arrObj[0].toString());
			 * objOutStBean.setStrDebtorName(arrObj[1].toString());
			 * objOutStBean.setDblBalAmt(0.00);
			 * objOutStBean.setDblCrAmt(Double.parseDouble(arrObj[3].toString()));
			 * objOutStBean.setDblDrAmt(0.00); objOutStBean.setDblOpnAmt(0.00); }
			 * objOutStBean.setDblBalAmt(objOutStBean.getDblBalAmt() -
			 * objOutStBean.getDblCrAmt());
			 * 
			 * hmOutstanding.put(arrObj[0].toString(), objOutStBean); } }
			 * 
			 * sbSql.setLength(0); sbSql.
			 * append("select debtor.strDebtorCode,'',debtor.strCrDr, sum(ifnull(debtor.dblAmt,0.00)) "
			 * +
			 * " from tblpaymentdebtordtl debtor inner join tblpaymenthd hd on hd.strVouchNo=debtor.strVouchNo "
			 * + " where debtor.strAccountCode='" + glCode +
			 * "' and debtor.strCrDr='Cr' and date(hd.dteVouchDate) between '" + newfromDate
			 * + "' AND '" + dteToDate + "' " + " and hd.strClientCode='" + clientCode +
			 * "' " + " group by debtor.strCrDr,debtor.strDebtorCode "); List listPaymentAmt
			 * = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks"); if
			 * (listPaymentAmt != null && listPaymentAmt.size() > 0) { for (int j = 0; j <
			 * listPaymentAmt.size(); j++) { Object[] arrObj = (Object[])
			 * listPaymentAmt.get(j); clsCreditorOutStandingReportBean objOutStBean = new
			 * clsCreditorOutStandingReportBean(); if
			 * (hmOutstanding.containsKey(arrObj[0].toString())) { objOutStBean =
			 * hmOutstanding.get(arrObj[0].toString()); double creditAmt =
			 * objOutStBean.getDblCrAmt() + Double.parseDouble(arrObj[3].toString());
			 * objOutStBean.setDblCrAmt(creditAmt); //
			 * objOutStBean.setDblDrAmt(objOutStBean.getDblBalAmt()); } else {
			 * objOutStBean.setStrDebtorCode(arrObj[0].toString());
			 * objOutStBean.setStrDebtorName(arrObj[1].toString());
			 * objOutStBean.setDblBalAmt(0.00);
			 * objOutStBean.setDblCrAmt(Double.parseDouble(arrObj[3].toString()));
			 * objOutStBean.setDblDrAmt(0.00); objOutStBean.setDblOpnAmt(0.00); }
			 * objOutStBean.setDblBalAmt(objOutStBean.getDblBalAmt() -
			 * objOutStBean.getDblCrAmt());
			 * 
			 * hmOutstanding.put(arrObj[0].toString(), objOutStBean); } }
			 * 
			 * sbSql.setLength(0); sbSql.
			 * append("select debtor.strDebtorCode,'',debtor.strCrDr, sum(ifnull(debtor.dblAmt,0.00)) "
			 * +
			 * " from tblreceiptdebtordtl debtor inner join tblreceipthd hd on hd.strVouchNo=debtor.strVouchNo "
			 * + " where debtor.strAccountCode='" + glCode +
			 * "' and debtor.strCrDr='Cr' and date(hd.dteVouchDate) between '" + newfromDate
			 * + "' AND '" + dteToDate + "' " + " and hd.strClientCode='" + clientCode +
			 * "' " + " group by debtor.strCrDr,debtor.strDebtorCode "); List listReceiptAmt
			 * = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks"); if
			 * (listReceiptAmt != null && listReceiptAmt.size() > 0) { for (int j = 0; j <
			 * listReceiptAmt.size(); j++) { Object[] arrObj = (Object[])
			 * listReceiptAmt.get(j); clsCreditorOutStandingReportBean objOutStBean = new
			 * clsCreditorOutStandingReportBean(); if
			 * (hmOutstanding.containsKey(arrObj[0].toString())) { objOutStBean =
			 * hmOutstanding.get(arrObj[0].toString()); double creditAmt =
			 * objOutStBean.getDblCrAmt() + Double.parseDouble(arrObj[3].toString());
			 * objOutStBean.setDblCrAmt(creditAmt); //
			 * objOutStBean.setDblDrAmt(objOutStBean.getDblBalAmt()); } else {
			 * objOutStBean.setStrDebtorCode(arrObj[0].toString());
			 * objOutStBean.setStrDebtorName(arrObj[1].toString());
			 * objOutStBean.setDblBalAmt(0.00);
			 * objOutStBean.setDblCrAmt(Double.parseDouble(arrObj[3].toString()));
			 * objOutStBean.setDblDrAmt(0.00); objOutStBean.setDblOpnAmt(0.00); }
			 * objOutStBean.setDblBalAmt(objOutStBean.getDblBalAmt() -
			 * objOutStBean.getDblCrAmt());
			 * 
			 * hmOutstanding.put(arrObj[0].toString(), objOutStBean); } }
			 * 
			 * for (Map.Entry<String, clsCreditorOutStandingReportBean> entry :
			 * hmOutstanding.entrySet()) { String debtorCode = entry.getKey();
			 * clsCreditorOutStandingReportBean objCreditorOutStandingReportBean =
			 * entry.getValue(); if (objCreditorOutStandingReportBean.getDblBalAmt() > 0) {
			 * objCreditorOutStandingReportBean.setDblBalAmt(
			 * objCreditorOutStandingReportBean.getDblBalAmt() * conversionRate);
			 * objCreditorOutStandingReportBean.setDblCrAmt(objCreditorOutStandingReportBean
			 * .getDblCrAmt() * conversionRate);
			 * objCreditorOutStandingReportBean.setDblDrAmt(objCreditorOutStandingReportBean
			 * .getDblDrAmt() * conversionRate);
			 * objCreditorOutStandingReportBean.setDblOpnAmt(
			 * objCreditorOutStandingReportBean.getDblOpnAmt() * conversionRate);
			 * 
			 * }
			 * 
			 * if (mapNewDebtor.containsKey(debtorCode)) { clsCreditorOutStandingReportBean
			 * objDebtor = mapNewDebtor.get(debtorCode);
			 * 
			 * objDebtor.setDblBalAmt(objCreditorOutStandingReportBean.getDblBalAmt()); } }
			 */

			for (Map.Entry<String, Double> entryBal : mapDebtorBal.entrySet())
			{
				String code = entryBal.getKey();
				double bal = entryBal.getValue();

				if (mapNewDebtor.containsKey(code))
				{
					mapNewDebtor.get(code).setDblBalAmt(bal);
				}

			}

			listOfAgeingData.addAll(mapNewDebtor.values());

			Collections.sort(listOfAgeingData, debtorNameComparator);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return listOfAgeingData;
	}

	public Map<String, clsCreditorOutStandingReportBean> funCalCreditorDebtorOPBalance(String glCode, String clientCode, String fromDate, String toDate, String DCType, HttpServletRequest req) throws Exception
	{

		StringBuilder sbSql = new StringBuilder();

		Map<String, clsCreditorOutStandingReportBean> hmOutstanding = new HashMap<String, clsCreditorOutStandingReportBean>();

		// Fill map with debtors and creditors

		String webStockDB = req.getSession().getAttribute("WebStockDB").toString();

		if (DCType.equals("Debtor"))
		{
			sbSql.setLength(0);
			sbSql.append("select sd.strDebtorCode,sd.strDebtorFullName,0.00 " + " from tblsundarydebtormaster sd," + webStockDB + ".tbllinkup link " + " where sd.strDebtorCode=link.strAccountCode and sd.strClientCode='" + clientCode + "'   ");// and sd.strDebtorCode='D00000100'
			List listDebtors = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
			if (listDebtors != null && listDebtors.size() > 0)
			{
				for (int j = 0; j < listDebtors.size(); j++)
				{
					Object[] arrObj = (Object[]) listDebtors.get(j);
					clsCreditorOutStandingReportBean objOutStBean = new clsCreditorOutStandingReportBean();
					objOutStBean.setStrDebtorCode(arrObj[0].toString());
					objOutStBean.setStrDebtorName(arrObj[1].toString());
					objOutStBean.setDblBalAmt(Double.parseDouble(arrObj[2].toString()));
					objOutStBean.setDblCrAmt(0.00);
					objOutStBean.setDblDrAmt(0.00);
					objOutStBean.setDblOpnAmt(Double.parseDouble(arrObj[2].toString()));

					hmOutstanding.put(arrObj[0].toString(), objOutStBean);
				}
			}

			// Opening Balance
			sbSql.setLength(0);
			sbSql.append("select sd.strDebtorCode,sd.strDebtorFullName, ifnull(op.dblOpeningbal,0.00) as Op_Bal " + " from tblsundarydebtormaster sd left outer join tblsundarydebtoropeningbalance op on sd.strDebtorCode=op.strDebtorCode " + " where op.strAccountCode='" + glCode + "' and op.strClientCode='" + clientCode + "'  ");
			List listOpeningBalance = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
			if (listOpeningBalance != null && listOpeningBalance.size() > 0)
			{
				for (int j = 0; j < listOpeningBalance.size(); j++)
				{
					Object[] arrObj = (Object[]) listOpeningBalance.get(j);
					clsCreditorOutStandingReportBean objOutStBean = new clsCreditorOutStandingReportBean();

					if (hmOutstanding.containsKey(arrObj[0].toString()))
					{
						objOutStBean = hmOutstanding.get(arrObj[0].toString());
						objOutStBean.setDblBalAmt(Double.parseDouble(arrObj[2].toString()));
						objOutStBean.setDblDrAmt(Double.parseDouble(arrObj[2].toString()));
					}
					else
					{
						objOutStBean.setStrDebtorCode(arrObj[0].toString());
						objOutStBean.setStrDebtorName(arrObj[1].toString());
						objOutStBean.setDblBalAmt(Double.parseDouble(arrObj[2].toString()));
						objOutStBean.setDblCrAmt(0.00);
						objOutStBean.setDblDrAmt(Double.parseDouble(arrObj[2].toString()));
						objOutStBean.setDblOpnAmt(Double.parseDouble(arrObj[2].toString()));
					}
					hmOutstanding.put(arrObj[0].toString(), objOutStBean);
				}
			}
		}
		else
		{
			sbSql.setLength(0);
			sbSql.append("select sd.strCreditorCode,sd.strCreditorFullName,0.00 " + " from tblsundarycreditormaster sd," + webStockDB + ".tbllinkup link " + " where sd.strCreditorCode=link.strAccountCode and sd.strClientCode='" + clientCode + "' and sd.strDebtorCode='D00000024' ");
			List listCreditors = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
			if (listCreditors != null && listCreditors.size() > 0)
			{
				for (int j = 0; j < listCreditors.size(); j++)
				{
					Object[] arrObj = (Object[]) listCreditors.get(j);
					clsCreditorOutStandingReportBean objOutStBean = new clsCreditorOutStandingReportBean();
					objOutStBean.setStrDebtorCode(arrObj[0].toString());
					objOutStBean.setStrDebtorName(arrObj[1].toString());
					objOutStBean.setDblBalAmt(Double.parseDouble(arrObj[2].toString()));
					objOutStBean.setDblCrAmt(0.00);
					objOutStBean.setDblDrAmt(0.00);
					objOutStBean.setDblOpnAmt(Double.parseDouble(arrObj[2].toString()));

					hmOutstanding.put(arrObj[0].toString(), objOutStBean);
				}
			}

			// Opening Balance
			sbSql.setLength(0);
			sbSql.append("select sd.strCreditorCode,sd.strCreditorFullName, ifnull(op.dblOpeningbal,0.00) as Op_Bal " + " from tblsundarycreditormaster sd left outer join tblsundarycreditoropeningbalance op on sd.strCreditorCode=op.strCreditorCode " + " where op.strAccountCode='" + glCode + "' and op.strClientCode='" + clientCode + "'  ");
			List listOpBalCreditor = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
			if (listOpBalCreditor != null && listOpBalCreditor.size() > 0)
			{
				for (int j = 0; j < listOpBalCreditor.size(); j++)
				{
					Object[] arrObj = (Object[]) listOpBalCreditor.get(j);
					clsCreditorOutStandingReportBean objOutStBean = new clsCreditorOutStandingReportBean();

					if (arrObj[0].toString().equals("C00000001"))
					{
						System.out.println(arrObj[0].toString());
					}

					if (hmOutstanding.containsKey(arrObj[0].toString()))
					{
						objOutStBean = hmOutstanding.get(arrObj[0].toString());
						objOutStBean.setDblBalAmt(Double.parseDouble(arrObj[2].toString()));
						objOutStBean.setDblCrAmt(Double.parseDouble(arrObj[2].toString()));
					}
					else
					{
						objOutStBean.setStrDebtorCode(arrObj[0].toString());
						objOutStBean.setStrDebtorName(arrObj[1].toString());
						objOutStBean.setDblBalAmt(Double.parseDouble(arrObj[2].toString()));
						objOutStBean.setDblCrAmt(Double.parseDouble(arrObj[2].toString()));
						objOutStBean.setDblDrAmt(0.00);
						objOutStBean.setDblOpnAmt(Double.parseDouble(arrObj[2].toString()));
					}
					hmOutstanding.put(arrObj[0].toString(), objOutStBean);
				}
			}
		}

		return hmOutstanding;
	}

	public Map<String, clsCreditorOutStandingReportBean> funCalCreditorDebtorOutstanding(String glCode, String clientCode, String fromDate, String toDate, String DCType, HttpServletRequest req, Map<String, clsCreditorOutStandingReportBean> hmOutstanding) throws Exception
	{
		StringBuilder sbSql = new StringBuilder();
		hmOutstanding = new HashMap<>();

		// Fill map with debtors and creditors

		String webStockDB = req.getSession().getAttribute("WebStockDB").toString();

		// JV Amount
		sbSql.setLength(0);
		sbSql.append("select debtor.strDebtorCode,'',debtor.strCrDr, sum(ifnull(debtor.dblAmt,0.00)) " + " from tbljvdebtordtl debtor inner join tbljvhd hd on hd.strVouchNo=debtor.strVouchNo " + " where debtor.strAccountCode='" + glCode + "' and date(hd.dteVouchDate) between '" + fromDate + "' AND '" + toDate + "' " + " and hd.strClientCode='" + clientCode + "'  " + " group by debtor.strCrDr,debtor.strDebtorCode ");// and debtor.strDebtorCode='D00000100'
		List listJVAmt = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
		if (listJVAmt != null && listJVAmt.size() > 0)
		{
			for (int j = 0; j < listJVAmt.size(); j++)
			{
				Object[] arrObj = (Object[]) listJVAmt.get(j);
				clsCreditorOutStandingReportBean objOutStBean = new clsCreditorOutStandingReportBean();
				objOutStBean.setDblDrAmt(0.00);
				objOutStBean.setDblCrAmt(0.00);

				if (arrObj[0].toString().equals("C00000001"))
				{
					System.out.println(arrObj[0].toString());
				}

				double debitAmt = 0.00,
						creditAmt = 0.00;
				if (arrObj[2].toString().equals("Dr"))
				{
					debitAmt = Double.parseDouble(arrObj[3].toString());
				}
				else
				{
					creditAmt = Double.parseDouble(arrObj[3].toString());
				}
				double netAmt = (debitAmt - creditAmt);
				if (!DCType.equals("Debtor"))
				{
					netAmt = (creditAmt - debitAmt);
				}

				if (hmOutstanding.containsKey(arrObj[0].toString()))
				{
					objOutStBean = hmOutstanding.get(arrObj[0].toString());
					netAmt += objOutStBean.getDblBalAmt();
					objOutStBean.setDblBalAmt(netAmt);
					if (DCType.equals("Debtor"))
					{
						objOutStBean.setDblDrAmt(objOutStBean.getDblBalAmt());
					}
					else
					{
						objOutStBean.setDblCrAmt(objOutStBean.getDblBalAmt());
					}
				}
				else
				{
					objOutStBean.setStrDebtorCode(arrObj[0].toString());
					objOutStBean.setStrDebtorName(arrObj[1].toString());
					objOutStBean.setDblBalAmt(netAmt);
					objOutStBean.setDblOpnAmt(0.00);
				}

				hmOutstanding.put(arrObj[0].toString(), objOutStBean);
			}
		}

		// Payment Amount
		sbSql.setLength(0);
		sbSql.append("select debtor.strDebtorCode,'',debtor.strCrDr, sum(ifnull(debtor.dblAmt,0.00)) " + " from tblpaymentdebtordtl debtor inner join tblpaymenthd hd on hd.strVouchNo=debtor.strVouchNo " + " where debtor.strAccountCode='" + glCode + "' and date(hd.dteVouchDate) between '" + fromDate + "' AND '" + toDate + "' " + " and hd.strClientCode='" + clientCode + "'  " + " group by debtor.strCrDr,debtor.strDebtorCode ");
		List listPaymentAmt = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
		if (listPaymentAmt != null && listPaymentAmt.size() > 0)
		{
			for (int j = 0; j < listPaymentAmt.size(); j++)
			{
				Object[] arrObj = (Object[]) listPaymentAmt.get(j);
				clsCreditorOutStandingReportBean objOutStBean = new clsCreditorOutStandingReportBean();

				double debitAmt = 0.00,
						creditAmt = 0.00;
				objOutStBean.setDblDrAmt(0.00);
				objOutStBean.setDblCrAmt(0.00);

				if (arrObj[2].toString().equals("Dr"))
				{
					debitAmt = Double.parseDouble(arrObj[3].toString());
				}
				else
				{
					creditAmt = Double.parseDouble(arrObj[3].toString());
				}
				double netAmt = (debitAmt - creditAmt);
				if (!DCType.equals("Debtor"))
				{
					netAmt = (creditAmt - debitAmt);
				}

				if (hmOutstanding.containsKey(arrObj[0].toString()))
				{
					objOutStBean = hmOutstanding.get(arrObj[0].toString());
					netAmt += objOutStBean.getDblBalAmt();
					objOutStBean.setDblBalAmt(netAmt);

					if (DCType.equals("Debtor"))
					{
						objOutStBean.setDblDrAmt(objOutStBean.getDblBalAmt());
					}
					else
					{
						objOutStBean.setDblCrAmt(objOutStBean.getDblBalAmt());
					}
				}
				else
				{
					objOutStBean.setStrDebtorCode(arrObj[0].toString());
					objOutStBean.setStrDebtorName(arrObj[1].toString());
					objOutStBean.setDblBalAmt(netAmt);
					objOutStBean.setDblOpnAmt(0.00);
				}

				hmOutstanding.put(arrObj[0].toString(), objOutStBean);
			}
		}

		// Receipt Amount
		sbSql.setLength(0);
		sbSql.append("select debtor.strDebtorCode,'',debtor.strCrDr, sum(ifnull(debtor.dblAmt,0.00)) " + " from tblreceiptdebtordtl debtor inner join tblreceipthd hd on hd.strVouchNo=debtor.strVouchNo " + " where debtor.strAccountCode='" + glCode + "' and date(hd.dteVouchDate) between '" + fromDate + "' AND '" + toDate + "' " + " and hd.strClientCode='" + clientCode + "'  " + " group by debtor.strCrDr,debtor.strDebtorCode ");
		List listReceiptAmt = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
		if (listReceiptAmt != null && listReceiptAmt.size() > 0)
		{
			for (int j = 0; j < listReceiptAmt.size(); j++)
			{
				Object[] arrObj = (Object[]) listReceiptAmt.get(j);
				clsCreditorOutStandingReportBean objOutStBean = new clsCreditorOutStandingReportBean();

				objOutStBean.setDblDrAmt(0.00);
				objOutStBean.setDblCrAmt(0.00);

				double debitAmt = 0.00,
						creditAmt = 0.00;
				if (arrObj[2].toString().equals("Dr"))
				{
					debitAmt = Double.parseDouble(arrObj[3].toString());
				}
				else
				{
					creditAmt = Double.parseDouble(arrObj[3].toString());
				}
				double netAmt = (debitAmt - creditAmt);
				if (!DCType.equals("Debtor"))
				{
					netAmt = (creditAmt - debitAmt);
				}

				if (hmOutstanding.containsKey(arrObj[0].toString()))
				{
					objOutStBean = hmOutstanding.get(arrObj[0].toString());
					netAmt += objOutStBean.getDblBalAmt();
					objOutStBean.setDblBalAmt(netAmt);

					if (DCType.equals("Debtor"))
					{
						objOutStBean.setDblDrAmt(objOutStBean.getDblBalAmt());
					}
					else
					{
						objOutStBean.setDblCrAmt(objOutStBean.getDblBalAmt());
					}
				}
				else
				{
					objOutStBean.setStrDebtorCode(arrObj[0].toString());
					objOutStBean.setStrDebtorName(arrObj[1].toString());
					objOutStBean.setDblBalAmt(netAmt);
					objOutStBean.setDblOpnAmt(0.00);
				}

				hmOutstanding.put(arrObj[0].toString(), objOutStBean);
			}
		}

		return hmOutstanding;
	}

}