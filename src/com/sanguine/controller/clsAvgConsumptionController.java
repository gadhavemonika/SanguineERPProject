package com.sanguine.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.sanguine.bean.clsAvgConsumptionReportBean;
import com.sanguine.crm.bean.clsProductionComPilationBean;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsGroupMasterService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsSubGroupMasterService;
import com.sanguine.util.clsReportBean;

import net.sf.jasperreports.engine.JRDataSource;
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

@Controller
public class clsAvgConsumptionController {

	@Autowired
	private clsGlobalFunctionsService objGlobalService;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	private clsGroupMasterService objGrpMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsSubGroupMasterService objSubGroupMasterService;
	@Autowired
	private clsLocationMasterService objLocationMasterService;

	@RequestMapping(value = "/frmAvgConsumptionReport", method = RequestMethod.GET)
	public ModelAndView funOpenCategoryWiseSalesOrderForm(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmAvgConsumptionReport");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmAvgConsumptionReport_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmAvgConsumptionReport", "command", new clsReportBean());
		}

	}

	@RequestMapping(value = "/rptAvgConsumptionReport", method = RequestMethod.GET)
	private void funCustomerWiseCategoryWiseSalesOrderReportReport(@RequestParam(value = "formDate") String fromdate, @RequestParam(value = "toDate") String todate, @RequestParam(value = "locCode") String locCode, HttpServletResponse resp, HttpServletRequest req) {

		funCallAvgConsumptionReport(fromdate, todate, locCode, resp, req);

		// else
		// {
		// funLoadExcelAvgConsumptionReport(objBean, resp, req);
		// }
	}

	@SuppressWarnings({ "unused", "unused", "unused", "unchecked" })
	private void funCallAvgConsumptionReport(String fromdate, String todate, String locCode, HttpServletResponse resp, HttpServletRequest req) {

		Connection con = objGlobalFunctions.funGetConnection(req);
		// String locCode
		// =req.getSession().getAttribute("locationCode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		String[] sp = startDate.split(" ");
		String[] spDate = sp[0].split("/");
		startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
		clsLocationMasterModel objLocCode = objLocationMasterService.funGetObject(locCode, clientCode);

		String repeortfileName = "AvgConsumptionReport" + "_" + objLocCode.getStrLocName() + "_" + fromdate + "_To_" + todate + "_" + userCode;
		repeortfileName = repeortfileName.replaceAll(" ", "");

		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		if (objSetup == null) {
			objSetup = new clsPropertySetupModel();
		}

		String reportName = servletContext.getRealPath("/WEB-INF/reports/rptAvgConsumptionReport.jrxml");
		String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

		String propNameSql = "select a.strPropertyName  from dbwebmms.tblpropertymaster a where a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' ";
		List listPropName = objGlobalFunctionsService.funGetDataList(propNameSql, "sql");
		String propName = "";
		if (listPropName.size() > 0) {
			propName = listPropName.get(0).toString();
		}

		ArrayList<clsAvgConsumptionReportBean> fieldList = new ArrayList<clsAvgConsumptionReportBean>();
		// String currDate=objGlobal.funGetCurrentDateTime("dd-MM-yyyy");
		// String[] splcurrDate=currDate.split(" ");
		// currDate=splcurrDate[0];
		// String preDates = funBeforeSevenDayDate(currDate,"dd-MM-yyyy");
		// String[] splpreDate=preDates.split("#");
		// String preDayOne=splpreDate[0];
		// String[] splpreDayOne=preDayOne.split("-");
		// preDayOne =splpreDayOne[2]+"-"+splpreDayOne[1]+"-"+splpreDayOne[0];
		// String[] splDate=currDate.split("-");

		String dteFromDate = fromdate;
		String[] tempsplFDate = dteFromDate.split("-");
		String dtFDate = tempsplFDate[2] + "-" + tempsplFDate[1] + "-" + tempsplFDate[0];

		String dteToDate = todate;
		String[] tempsplToDate = dteToDate.split("-");
		String dtToDate = tempsplToDate[2] + "-" + tempsplToDate[1] + "-" + tempsplToDate[0];
		long diffDay = funDayDifference(dteFromDate, dteToDate);
		if (diffDay == 0 || diffDay > 0) {
			diffDay = diffDay + 1;
		}

		objGlobal.funInvokeStockFlash(startDate, locCode, dtFDate, dtToDate, clientCode, userCode, "Both", req, resp);
		String sqlProd = " ";
		if (objSetup.getStrShowAllProdToAllLoc() == null || objSetup.getStrShowAllProdToAllLoc().equalsIgnoreCase("N")) {
			sqlProd = " select a.strProdCode,a.strProdName,a.dblReceiveConversion,a.strUOM from tblproductmaster a where a.strProdType='Procured' and a.strNotInUse='N' and a.strClientCode='" + clientCode + "'  ";
		} else {
			sqlProd = " select a.strProdCode,a.strProdName,a.dblReceiveConversion,a.strUOM from tblproductmaster a,tblreorderlevel b where a.strProdCode=b.strProdCode and b.strLocationCode='" + locCode + "' and a.strProdType='Procured' and a.strNotInUse='N' and a.strClientCode='" + clientCode + "' ";
		}
		List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlProd, "sql");
		for (int j = 0; j < listProdDtl.size(); j++) {
			Object[] obj = (Object[]) listProdDtl.get(j);
			String pCode = obj[0].toString();
			String pName = obj[1].toString();
			double dblReceiveConversion = Double.parseDouble(obj[2].toString());
			String strUOM = obj[3].toString();
			clsAvgConsumptionReportBean objAvgBean = new clsAvgConsumptionReportBean();

			String sqlStk = " select a.dblOpeningStk,a.dblClosingStk,a.dblMISOut," + " (a.dblGRN+dblSCGRN+a.dblStkTransIn+a.dblStkAdjIn+a.dblMISIn+a.dblQtyProduced+a.dblMaterialReturnIn) as Receipts, " + " (a.dblStkTransOut-a.dblStkAdjOut-a.dblMISOut-a.dblQtyConsumed-a.dblSales-a.dblMaterialReturnOut-a.dblDeliveryNote) as Issue " + " from tblcurrentstock a where a.strLocCode='" + locCode
					+ "' and a.strProdCode='" + pCode + "' and a.strClientCode='" + clientCode + "' and a.strUserCode='" + userCode + "' ";
			List listSTK = objGlobalFunctionsService.funGetDataList(sqlStk, "sql");
			Object[] objStk = (Object[]) listSTK.get(0);
			double dblOpeningStk = Double.parseDouble(objStk[0].toString());
			double dblCloseStk = Double.parseDouble(objStk[1].toString());
			double dblAvgConsum = Double.parseDouble(objStk[2].toString());
			objAvgBean.setDblAvgConsump(Double.parseDouble(objStk[2].toString()));
			objAvgBean.setStrProdCode(pCode);
			objAvgBean.setStrProdName(pName);
			objAvgBean.setStrUom(strUOM);
			objAvgBean.setDblCurrentStock(dblCloseStk);
			objAvgBean.setDblOpeningStk(dblOpeningStk);

			dblAvgConsum = dblAvgConsum / diffDay;
			if (dblAvgConsum < 0) {
				dblAvgConsum = 0.00;
			}

			objAvgBean.setDblAvgConsump(dblAvgConsum);
			objAvgBean.setDblRecipts(Double.parseDouble(objStk[3].toString()));
			double issueQty = Double.parseDouble(objStk[4].toString());

			if (issueQty < 0) {
				issueQty = -1 * issueQty;
			}

			objAvgBean.setDblIssue(issueQty);

			String sqlProdMinQty = " select a.dblReOrderQty from tblreorderlevel a where a.strLocationCode='" + locCode + "' and a.strProdCode='" + pCode + "' and a.strClientCode='" + clientCode + "'  ";
			List<BigDecimal> listProdMinQty = objGlobalFunctionsService.funGetDataList(sqlProdMinQty, "sql");
			if (null != listProdMinQty) {
				if (listProdMinQty.isEmpty()) {
					objAvgBean.setDblMinOrderQty(0);
				}
			} else {
				objAvgBean.setDblMinOrderQty(0);
			}
			objAvgBean.setDblRecConverstion(dblReceiveConversion);

			String sqlSuppName = " select a.strSuppCode,b.strPName  from tblprodsuppmaster a ,tblpartymaster b " + " where  a.strSuppCode=b.strPCode and a.strProdCode='" + pCode + "' and a.strDefault='Y' and a.strClientCode='" + clientCode + "'  ";
			List listSuppName = objGlobalFunctionsService.funGetDataList(sqlSuppName, "sql");
			if (null != listSuppName) {

				for (int k = 0; k < listSuppName.size(); k++) {
					Object[] objSupp = (Object[]) listSuppName.get(k);
					String sCode = objSupp[0].toString();
					String sName = objSupp[1].toString();
					objAvgBean.setStrSuppName(sName);
					objAvgBean.setStrSuppCode(sCode);
				}
				if (listSuppName.isEmpty()) {
					objAvgBean.setStrSuppName("");
					objAvgBean.setStrSuppCode("");
				}

			} else {
				objAvgBean.setStrSuppName("");
				objAvgBean.setStrSuppCode("");
			}

			fieldList.add(objAvgBean);

		}

		Collections.sort(fieldList, clsAvgConsumptionReportBean.suppNameComparator);

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
		hm.put("dteFromDate", dteFromDate);
		hm.put("dteToDate", dteToDate);

		try {
			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			if (jp != null) {
				jprintlist.add(jp);
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				// if(objBean.getStrDocType().equals("PDF"))
				// {
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=" + repeortfileName + ".pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();

				// }
				// else
				// {
				//
				// resp.setContentType("application/xlsx");
				// JRExporter exporterXLS = new JRXlsExporter();
				// exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST,
				// jprintlist);
				// exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
				// servletOutputStream );
				// resp.setHeader( "Content-Disposition",
				// "attachment;filename=rptAvgConsumptionReport"+objGlobal.funGetCurrentDateTime("yyyy-MM-dd")+".xls"
				// );
				// exporterXLS.exportReport();
				// servletOutputStream.flush();
				// servletOutputStream.close();
				//
				// }
			}
			//
			//
			// else if(type.trim().equalsIgnoreCase("xls"))
			// {
			// JRExporter exporterXLS = new JRXlsExporter();
			// exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
			// exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
			// resp.getOutputStream() );
			// resp.setHeader( "Content-Disposition", "attachment;filename=" +
			// "rptReceiptIssueConsolidated."+type.trim() );
			// exporterXLS.exportReport();
			// resp.setContentType("application/xlsx");
			// }

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/loadExcelAvgConsumptionReport", method = RequestMethod.GET)
	public ModelAndView funLoadExcelAvgConsumptionReport(@RequestParam(value = "formDate") String fromdate, @RequestParam(value = "toDate") String todate, @RequestParam(value = "locCode") String locCode, HttpServletResponse resp, HttpServletRequest req) {

		// String locCode
		// =req.getSession().getAttribute("locationCode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		String[] sp = startDate.split(" ");
		String[] spDate = sp[0].split("/");
		String rpeortName = "AvgConsumptionReport";

		startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];

		String dteFromDate = fromdate;
		String[] tempsplFDate = dteFromDate.split("-");
		String dtFDate = tempsplFDate[2] + "-" + tempsplFDate[1] + "-" + tempsplFDate[0];

		String dteToDate = todate;
		String[] tempsplToDate = dteToDate.split("-");
		String dtToDate = tempsplToDate[2] + "-" + tempsplToDate[1] + "-" + tempsplToDate[0];

		long diffDay = funDayDifference(dteFromDate, dteToDate);

		objGlobal.funInvokeStockFlash(startDate, locCode, dtFDate, dtToDate, clientCode, userCode, "Both", req, resp);

		List ExportList = new ArrayList();

		clsLocationMasterModel objLocCode = objLocationMasterService.funGetObject(locCode, clientCode);

		rpeortName = rpeortName + "_" + objLocCode.getStrLocName() + "_" + fromdate + "_To_" + todate + "_" + userCode;
		rpeortName = rpeortName.replaceAll(" ", "");
		ExportList.add(rpeortName);

		ArrayList<clsAvgConsumptionReportBean> fieldList = new ArrayList<clsAvgConsumptionReportBean>();

		List<String> listHeader = new ArrayList<String>();
		listHeader.add("Sr.no");
		listHeader.add("Product Name");
		listHeader.add("Uom");
		listHeader.add("U/M");
		listHeader.add("Opening Stk");
		// listHeader.add("Avg Consumption");
		// listHeader.add("Closing STK");
		// listHeader.add("Ordered Qty");
		// listHeader.add("Minimum Order Qty");
		// listHeader.add("Issue");
		// listHeader.add("Receipts");
		// listHeader.add("Name Of Vendor");
		listHeader.add("Receipts");
		listHeader.add("Issue");
		listHeader.add("Closing STK");
		listHeader.add("Avg Consumption");
		listHeader.add("Ordered Qty");
		listHeader.add("Minimum Order Qty");
		listHeader.add("Name Of Vendor");

		String[] ExcelHeader = new String[listHeader.size()];
		ExcelHeader = listHeader.toArray(ExcelHeader);
		ExportList.add(ExcelHeader);
		// ////////////////

		String sqlProd = " select a.strProdCode,a.strProdName,a.dblReceiveConversion,a.strUOM from tblproductmaster a where a.strProdType='Procured' and a.strNotInUse='N'  ";

		List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlProd, "sql");
		for (int j = 0; j < listProdDtl.size(); j++) {
			Object[] obj = (Object[]) listProdDtl.get(j);
			String pCode = obj[0].toString();
			String pName = obj[1].toString();
			double dblReceiveConversion = Double.parseDouble(obj[2].toString());
			String strUOM = obj[3].toString();
			clsAvgConsumptionReportBean objAvgBean = new clsAvgConsumptionReportBean();

			String sqlStk = " select a.dblOpeningStk,a.dblClosingStk,a.dblMISOut," + " (a.dblGRN+dblSCGRN+a.dblStkTransIn+a.dblStkAdjIn+a.dblMISIn+a.dblQtyProduced+a.dblMaterialReturnIn) as Receipts, " + " (a.dblStkTransOut-a.dblStkAdjOut-a.dblMISOut-a.dblQtyConsumed-a.dblSales-a.dblMaterialReturnOut-a.dblDeliveryNote) as Issue " + " from tblcurrentstock a where a.strLocCode='" + locCode
					+ "' and a.strProdCode='" + pCode + "' and a.strClientCode='" + clientCode + "' and a.strUserCode='" + userCode + "' ";
			List listSTK = objGlobalFunctionsService.funGetDataList(sqlStk, "sql");
			Object[] objStk = (Object[]) listSTK.get(0);
			double dblOpeningStk = Double.parseDouble(objStk[0].toString());
			double dblCloseStk = Double.parseDouble(objStk[1].toString());
			double dblAvgConsum = Double.parseDouble(objStk[2].toString());

			objAvgBean.setDblAvgConsump(Double.parseDouble(objStk[2].toString()));
			objAvgBean.setStrProdCode(pCode);
			objAvgBean.setStrProdName(pName);
			objAvgBean.setStrUom(strUOM);
			objAvgBean.setDblCurrentStock(dblCloseStk);
			objAvgBean.setDblOpeningStk(dblOpeningStk);

			dblAvgConsum = dblAvgConsum / diffDay;
			if (dblAvgConsum < 0) {
				dblAvgConsum = 0.00;
			}

			objAvgBean.setDblAvgConsump(dblAvgConsum);

			objAvgBean.setDblRecipts(Double.parseDouble(objStk[3].toString()));
			double issueQty = Double.parseDouble(objStk[4].toString());

			if (issueQty < 0) {
				issueQty = -1 * issueQty;
			}

			objAvgBean.setDblIssue(issueQty);

			String sqlProdMinQty = " select a.dblReOrderQty from tblreorderlevel a where a.strLocationCode='" + locCode + "' and a.strProdCode='" + pCode + "' and a.strClientCode='" + clientCode + "'  ";
			List<BigDecimal> listProdMinQty = objGlobalFunctionsService.funGetDataList(sqlProdMinQty, "sql");
			if (null != listProdMinQty) {
				if (listProdMinQty.isEmpty()) {
					objAvgBean.setDblMinOrderQty(0);
				}
			} else {
				objAvgBean.setDblMinOrderQty(0);
			}
			objAvgBean.setDblRecConverstion(dblReceiveConversion);

			String sqlSuppName = " select a.strSuppCode,b.strPName  from tblprodsuppmaster a ,tblpartymaster b " + " where  a.strSuppCode=b.strPCode and a.strProdCode='" + pCode + "' and a.strDefault='Y' and a.strClientCode='" + clientCode + "'  ";
			List listSuppName = objGlobalFunctionsService.funGetDataList(sqlSuppName, "sql");
			if (null != listSuppName) {

				for (int k = 0; k < listSuppName.size(); k++) {
					Object[] objSupp = (Object[]) listSuppName.get(k);
					String sCode = objSupp[0].toString();
					String sName = objSupp[1].toString();
					objAvgBean.setStrSuppName(sName);
					objAvgBean.setStrSuppCode(sCode);
				}
				if (listSuppName.isEmpty()) {
					objAvgBean.setStrSuppName("");
					objAvgBean.setStrSuppCode("");
				}

			} else {
				objAvgBean.setStrSuppName("");
				objAvgBean.setStrSuppCode("");
			}

			fieldList.add(objAvgBean);

		}

		Collections.sort(fieldList, clsAvgConsumptionReportBean.suppNameComparator);
		@SuppressWarnings("rawtypes")
		List listExportDtl = new ArrayList<>();
		int srNo = 1;
		DecimalFormat df = new DecimalFormat("#.##");
		for (clsAvgConsumptionReportBean objBean : fieldList) {
			List listobj = new ArrayList<>();
			listobj.add(srNo);
			listobj.add(objBean.getStrProdName());
			listobj.add(objBean.getStrUom());
			listobj.add(objBean.getDblRecConverstion());
			listobj.add(objBean.getDblOpeningStk());
			// listobj.add(df.format(objBean.getDblAvgConsump()));
			// listobj.add(objBean.getDblCurrentStock());
			// listobj.add("");
			// listobj.add(objBean.getDblMinOrderQty());
			// listobj.add(objBean.getDblIssue());
			// listobj.add(objBean.getDblRecipts());
			// listobj.add(objBean.getStrSuppName());
			listobj.add(objBean.getDblRecipts());
			listobj.add(objBean.getDblIssue());
			listobj.add(objBean.getDblCurrentStock());
			listobj.add(df.format(objBean.getDblAvgConsump()));
			listobj.add("");
			listobj.add(objBean.getDblMinOrderQty());
			listobj.add(objBean.getStrSuppName());

			listExportDtl.add(listobj);
			srNo++;
		}

		ExportList.add(listExportDtl);

		// ////////

		return new ModelAndView("excelViewWithReportName", "listWithReportName", ExportList);
	}

	private String funBeforeSevenDayDate(String dte, String pattern) {
		int day = 0;
		int month = 0;
		int year = 0;
		if (pattern.equals("yyyy-mm-dd")) {
			String[] date = dte.split("-");
			day = Integer.parseInt(date[2].toString());
			month = Integer.parseInt(date[1].toString());
			year = Integer.parseInt(date[0].toString());
		} else {
			String[] date = dte.split("-");
			day = Integer.parseInt(date[0].toString());
			month = Integer.parseInt(date[1].toString());
			year = Integer.parseInt(date[2].toString());
		}

		int day1 = 0;
		int day2 = 0;
		int day3 = 0;
		int day4 = 0;
		int day5 = 0;
		int day6 = 0;
		int day7 = 0;

		int month1 = 0;
		int month2 = 0;
		int month3 = 0;
		int month4 = 0;
		int month5 = 0;
		int month6 = 0;
		int month7 = 0;

		int year1 = 0;
		int year2 = 0;
		int year3 = 0;
		int year4 = 0;
		int year5 = 0;
		int year6 = 0;
		int year7 = 0;

		day1 = day - 1;
		month1 = month;
		year1 = year;
		if (day1 < 0) {
			day1 = 30 - (-day3);

			month1 = month1 - 1;
			if (month1 == 0) {
				month1 = 12;
				year1 = year1 - 1;
			}

		}
		if (day1 == 0) {
			day1 = 30;

		}

		// System.out.println("Day1="+day1+"-"+month1+"-"+year1);
		String date1 = year1 + "-" + month1 + "-" + day1;
		day2 = day1 - 1;
		month2 = month1;
		year2 = year1;
		if (day2 < 0) {
			day2 = 30 - (-day3);
			month2 = month2 - 1;
			if (month2 == 0) {
				month2 = 12;
				year2 = year2 - 1;
			}

		}
		if (day2 == 0) {
			day2 = 30;

		}
		// System.out.println("Day2="+day2+"-"+month2+"-"+year2);
		String date2 = year2 + "-" + month2 + "-" + day2;

		day3 = day2 - 1;
		month3 = month2;
		year3 = year2;
		if (day3 < 0) {
			day3 = 30 - (-day3);
			month3 = month3 - 1;

			if (month3 == 0) {
				month3 = 12;
				year3 = year3 - 1;
			}

		}
		if (day3 == 0) {
			day3 = 30;

		}
		// System.out.println("Day3="+day3+"-"+month3+"-"+year3);
		String date3 = year3 + "-" + month3 + "-" + day3;

		day4 = day3 - 1;
		month4 = month3;
		year4 = year3;
		if (day4 < 0) {
			day4 = 30 - (-day4);
			month4 = month4 - 1;
			if (month4 == 0) {
				month4 = 12;
				year4 = year4 - 1;
			}
		}
		if (day4 == 0) {
			day4 = 30;

		}
		// System.out.println("Day4="+day4+"-"+month4+"-"+year4);
		String date4 = year4 + "-" + month4 + "-" + day4;

		day5 = day4 - 1;
		month5 = month4;
		year5 = year4;
		if (day5 < 0) {
			day5 = 30 - (-day5);
			month5 = month5 - 1;
			if (month5 == 0) {
				month5 = 12;
				year5 = year5 - 1;
			}
		}
		if (day5 == 0) {
			day5 = 30;

		}
		// System.out.println("Day5="+day5+"-"+month5+"-"+year5);
		String date5 = year5 + "-" + month5 + "-" + day5;

		day6 = day5 - 1;
		month6 = month5;
		year6 = year5;
		if (day6 < 0) {
			day6 = 30 - (-day6);
			month6 = month6 - 1;
			if (month6 == 0) {
				month6 = 12;
				year6 = year6 - 1;
			}
		}
		if (day6 == 0) {
			day6 = 30;

		}
		// System.out.println("Day6="+day6+"-"+month6+"-"+year6);
		String date6 = year6 + "-" + month6 + "-" + day6;

		day7 = day6 - 1;
		month7 = month6;
		year7 = year6;
		if (day7 < 0) {
			day7 = 30 - (-day7);
			month7 = month7 - 1;
			if (month7 == 0) {
				month7 = 12;
				year7 = year7 - 1;
			}
		}
		if (day7 == 0) {
			day7 = 30;

		}
		// System.out.println("Day7="+day7+"-"+month7+"-"+year7);
		String date7 = year7 + "-" + month7 + "-" + day7;

		return date1 + "#" + date2 + "#" + date3 + "#" + date4 + "#" + date5 + "#" + date6 + "#" + date7;
	}

	@SuppressWarnings("finally")
	private long funDayDifference(String fromDate, String toDate) {
		long diffDays = 0;
		try {

			// HH converts hour in 24 hours format (0-23), day calculation
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

			Date d1 = null;
			Date d2 = null;

			d1 = format.parse(fromDate);
			d2 = format.parse(toDate);

			// in milliseconds
			long diff = d2.getTime() - d1.getTime();

			// long diffSeconds = diff / 1000 % 60;
			// long diffMinutes = diff / (60 * 1000) % 60;
			// long diffHours = diff / (60 * 60 * 1000) % 24;
			diffDays = diff / (24 * 60 * 60 * 1000);

			System.out.print(diffDays + " days, ");
			// System.out.print(diffHours + " hours, ");
			// System.out.print(diffMinutes + " minutes, ");
			// System.out.print(diffSeconds + " seconds.");

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return diffDays;
		}
	}

}
