package com.sanguine.excise.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsPurchaseAnylasisReportController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	private clsGlobalFunctions objGlobal = null;

	@RequestMapping(value = "/frmExcisePurchaseAnylasisReport", method = RequestMethod.GET)
	public ModelAndView funOpenPurchaseAnylasisReport(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExcisePurchaseAnylasisReport_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmExcisePurchaseAnylasisReport", "command", new clsReportBean());
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/rptPurchaseAnylasisReport", method = RequestMethod.POST)
	private ModelAndView funCallPurchaseAnylasisReportReportt(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String fromDate = objBean.getDtFromDate();
		String toDate = objBean.getDtToDate();

		String monthHeader = "";

		// LocalDate date1 = new LocalDate(fromDate);
		// LocalDate date2 = new LocalDate(toDate);
		//
		// while(date1.isBefore(date2)){
		// monthHeader = monthHeader+","+ date1.toString("MMMM-yyyy");
		// date1 = date1.plus(Period.months(1));
		// }
		// LocalDate date1 = new LocalDate(fromDate);
		// LocalDate date2 = new LocalDate(toDate);
		//
		// while(date1.isBefore(date2)){
		// monthHeader = monthHeader+","+ date1.toString("MMMM-yyyy");
		// date1 = date1.plus(Period.months(1));
		// }

		// String header="";
		//
		List ExportList = new ArrayList();
		// String[] ExcelHeader=header.split(",");
		// ExportList.add(ExcelHeader);
		// String sqlDtl=" ";
		//
		// List
		// list=objGlobalFunctionsService.funGetList(sqlDtl.toString(),"sql");
		// List PurchaseAnylasisReportlist=new ArrayList();
		// for(int i=0;i<list.size();i++)
		// {
		// Object[] ob=(Object[])list.get(i);
		//
		// List DataList=new ArrayList<>();
		// DataList.add(ob[0].toString());
		// DataList.add(ob[1].toString());
		// DataList.add(ob[2].toString());
		// DataList.add(ob[3].toString());
		//
		//
		//
		// PurchaseAnylasisReportlist.add(DataList);
		// }
		//
		//
		// ExportList.add(PurchaseAnylasisReportlist);
		//
		return new ModelAndView("excelView", "stocklist", ExportList);
	}
	//
	//
	// private String funGetMonthName(String dte)
	// {
	//
	// Date date = objGlobal.funStringToDate(dte);
	// String month = new SimpleDateFormat("MMM").format(dte);
	// String year = new SimpleDateFormat("yyyy").format(dte);
	// String monthAndYear = month+"("+year+")";
	//
	//
	//
	// // SWITCH(SEASON)
	// // {
	// // CASE 1:
	// // SYSTEM.OUT.PRINTLN("JAN");
	// // BREAK;
	// // CASE 2:
	// // SYSTEM.OUT.PRINTLN("FEB");
	// // BREAK;
	// // CASE 3:
	// // SYSTEM.OUT.PRINTLN("MAR");
	// // BREAK;
	// // CASE 4:
	// // SYSTEM.OUT.PRINTLN("APR");
	// // BREAK;
	// // CASE 5:
	// // SYSTEM.OUT.PRINTLN("MAY");
	// // BREAK;
	// // CASE 6:
	// // SYSTEM.OUT.PRINTLN("JUN");
	// // BREAK;
	// // CASE 7:
	// // SYSTEM.OUT.PRINTLN("JUL");
	// // BREAK;
	// // CASE 8:
	// // SYSTEM.OUT.PRINTLN("AUG");
	// // BREAK;
	// // CASE 9:
	// // SYSTEM.OUT.PRINTLN("SEP");
	// // BREAK;
	// // CASE 10:
	// // SYSTEM.OUT.PRINTLN("OCT");
	// // BREAK;
	// // CASE 11:
	// // SYSTEM.OUT.PRINTLN("NOV");
	// // BREAK;
	// // CASE 12:
	// // SYSTEM.OUT.PRINTLN("DEC");
	// // BREAK;
	// // DEFAULT:
	// // SYSTEM.OUT.PRINTLN("NOT SESSION DATA FOUND!!!");
	// // BREAK;
	// //
	// // }
	// //
	//
	//
	//
	// return monthAndYear;
	//
	// // String[] yymm = monthAndyear.split("-");
	// // String yy=yymm[0];
	// // int mm = Integer.parseInt(yymm[1]);
	//
	//
	// }
	//
	//
	//
	// private int funDiffDate(String startDate,String endDate)
	// {
	// Date frmdate =
	// objGlobal.funStringToDate((startDate.split("-")[0]+"-"+startDate.split("-")[1]+"-"+startDate.split("-")[2]));
	// Date todate =
	// objGlobal.funStringToDate((endDate.split("-")[0]+"-"+endDate.split("-")[1]+"-"+endDate.split("-")[2]));
	// Calendar startCalendar = new GregorianCalendar();
	// startCalendar.setTime(frmdate);
	// Calendar endCalendar = new GregorianCalendar();
	// endCalendar.setTime(todate);
	//
	// int diffYear = endCalendar.get(Calendar.YEAR) -
	// startCalendar.get(Calendar.YEAR);
	// int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) -
	// startCalendar.get(Calendar.MONTH);
	//
	// return diffMonth+1;
	// }
	//
	//

}
