package com.sanguine.webpms.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.util.Calendar;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.util.clsReportBean;
import com.sanguine.webpms.bean.clsExpectedArrivalListBean;
import com.sanguine.webpms.bean.clsFolioPrintingBean;
import com.sanguine.webpms.bean.clsRoomTypeInventoryReportBean;
import com.sanguine.webpms.service.clsFolioService;

@Controller
public class clsRoomTypeInventoryReportController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private clsSetupMasterService objSetupMasterService;

	// Open Folio Printing
	@RequestMapping(value = "/frmRoomTypeInventoryReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmRoomTypeInventoryReport_1", "command", new clsReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmRoomTypeInventoryReport", "command", new clsReportBean());
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rptRoomTypeInventoryReport", method = RequestMethod.GET)
	public void funGenerateFolioPrintingReport(@RequestParam("fromDate") String date, @RequestParam("strDocType") String strDocType, HttpServletRequest req, HttpServletResponse resp) {
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Date dt1 = sf.parse(date);
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(dt1);
			String sql = " select strRoomTypeDesc from tblroomtypemaster where strClientCode='" + clientCode + "' ";
			List listOfRoomType = objGlobalFunctionsService.funGetDataList(sql, "sql");

			List<String> listdate = new ArrayList<String>();
			GregorianCalendar calgeoOnly = new GregorianCalendar();
			calgeoOnly.setTime(dt1);
			listdate.add(date);
			for (int dteCount = 0; dteCount < 6; dteCount++) {
				calgeoOnly.add(Calendar.DATE, 1);
				String newToDate = (calgeoOnly.getTime().getYear() + 1900) + "-" + (calgeoOnly.getTime().getMonth() + 1) + "-" + (calgeoOnly.getTime().getDate());
				listdate.add(newToDate);
			}

			HashMap<String, Integer> hmdateForQuery = new HashMap<String, Integer>();
			HashMap<String, Object> hmRoomTypeInventry = new HashMap<String, Object>();

			for (int i = 0; i < listOfRoomType.size(); i++) {

				HashMap<String, Integer> hmday = new HashMap<String, Integer>();
				hmday.put(date, 0);
				GregorianCalendar calgeo = new GregorianCalendar();
				calgeo.setTime(dt1);
				for (int dteCount = 0; dteCount < 6; dteCount++) {

					calgeo.add(Calendar.DATE, 1);
					String newToDate = (calgeo.getTime().getYear() + 1900) + "-" + (calgeo.getTime().getMonth() + 1) + "-" + (calgeo.getTime().getDate());
					System.out.println(newToDate);
					hmday.put(newToDate, 0);
				}
				hmRoomTypeInventry.put(listOfRoomType.get(i).toString(), hmday);
				hmdateForQuery = hmday;

			}

			for (String key : hmdateForQuery.keySet()) {
				String eachDateSql = " select d.strRoomTypeDesc,count(d.strRoomTypeDesc)  " + " from tblcheckinhd a,tblcheckindtl b,tblroom c, tblroomtypemaster  d " + "  where a.strCheckInNo=b.strCheckInNo " + " and b.strRoomNo=c.strRoomCode and c.strRoomTypeCode=d.strRoomTypeCode " + " and date(a.dteCheckInDate) = '" + key + "' "
						+ " and a.strClientCode=b.strClientCode  and b.strClientCode=c.strClientCode " + " and c.strClientCode=d.strClientCode and d.strClientCode='" + clientCode + "' " + " group by d.strRoomTypeDesc ";
				List listOfRoomTypeData = objGlobalFunctionsService.funGetDataList(eachDateSql, "sql");
				for (int j = 0; j < listOfRoomTypeData.size(); j++) {
					Object[] arrObj = (Object[]) listOfRoomTypeData.get(j);
					int dayRoomTypesdata = Integer.parseInt(arrObj[1].toString());
					HashMap<String, Integer> hmDayData = new HashMap<String, Integer>();
					hmDayData = (HashMap<String, Integer>) hmRoomTypeInventry.get(arrObj[0].toString());
					hmDayData.put(key, dayRoomTypesdata);

					HashMap<String, Integer> hmtemPDayData = new HashMap<String, Integer>();
					for (String dayDkey : hmDayData.keySet()) {
						hmtemPDayData.put(dayDkey, hmDayData.get(dayDkey));
					}
					hmRoomTypeInventry.put(arrObj[0].toString(), hmtemPDayData);

				}
			}

			List<clsRoomTypeInventoryReportBean> listmainData = new ArrayList<clsRoomTypeInventoryReportBean>();
			for (int i = 0; i < listOfRoomType.size(); i++) {
				clsRoomTypeInventoryReportBean objRow = new clsRoomTypeInventoryReportBean();
				objRow.setStrRoomTypeDesc(listOfRoomType.get(i).toString());
				HashMap<String, Integer> hmDaywiseData = (HashMap<String, Integer>) hmRoomTypeInventry.get(listOfRoomType.get(i).toString());

				objRow.setStrDay1(hmDaywiseData.get(listdate.get(0)).toString());
				objRow.setStrDay2(hmDaywiseData.get(listdate.get(1)).toString());
				objRow.setStrDay3(hmDaywiseData.get(listdate.get(2)).toString());
				objRow.setStrDay4(hmDaywiseData.get(listdate.get(3)).toString());
				objRow.setStrDay5(hmDaywiseData.get(listdate.get(4)).toString());
				objRow.setStrDay6(hmDaywiseData.get(listdate.get(5)).toString());
				objRow.setStrDay7(hmDaywiseData.get(listdate.get(6)).toString());

				listmainData.add(objRow);
			}

			ArrayList fieldList = new ArrayList();

			fieldList.add(listmainData);

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webpms/rptRoomTypeInventoryReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			List<clsFolioPrintingBean> dataList = new ArrayList<clsFolioPrintingBean>();
			@SuppressWarnings("rawtypes")
			String propNameSql = "select a.strPropertyName  from "+webStockDB+".tblpropertymaster a where a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' ";
			List listPropName = objGlobalFunctionsService.funGetDataList(propNameSql, "sql");
			String propName = "";
			if (listPropName.size() > 0) {
				propName = listPropName.get(0).toString();
			}

			HashMap reportParams = new HashMap();

			reportParams.put("pCompanyName", companyName);
			reportParams.put("pAddress1", objSetup.getStrAdd1() + "," + objSetup.getStrAdd2() + "," + objSetup.getStrCity());
			reportParams.put("pAddress2", objSetup.getStrState() + "," + objSetup.getStrCountry() + "," + objSetup.getStrPin());
			reportParams.put("strImagePath", imagePath);
			reportParams.put("strUserCode", userCode);
			reportParams.put("pDate", objGlobal.funGetDate("yyyy-MM-dd", date));
			reportParams.put("propName", propName);
			reportParams.put("listOfMainData", listmainData);
			reportParams.put("Day1", objGlobal.funGetDate("yyyy-MM-dd", listdate.get(0)).toString());
			reportParams.put("Day2", objGlobal.funGetDate("yyyy-MM-dd", listdate.get(1)).toString());
			reportParams.put("Day3", objGlobal.funGetDate("yyyy-MM-dd", listdate.get(2)).toString());
			reportParams.put("Day4", objGlobal.funGetDate("yyyy-MM-dd", listdate.get(3)).toString());
			reportParams.put("Day5", objGlobal.funGetDate("yyyy-MM-dd", listdate.get(4)).toString());
			reportParams.put("Day6", objGlobal.funGetDate("yyyy-MM-dd", listdate.get(5)).toString());
			reportParams.put("Day7", objGlobal.funGetDate("yyyy-MM-dd", listdate.get(6)).toString());

			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr, reportParams, beanCollectionDataSource);
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			/*
			 * if (jp != null) {
			 * 
			 * 
			 * JRExporter exporter = new JRPdfExporter();
			 * resp.setContentType("application/pdf");
			 * exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST,
			 * jprintlist);
			 * exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM,
			 * servletOutputStream);
			 * exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS,
			 * Boolean.TRUE); resp.setHeader("Content-Disposition",
			 * "inline;filename=rptRoomTypeInventorReport_"
			 * +date+"_"+userCode+".pdf"); exporter.exportReport();
			 * servletOutputStream.flush(); servletOutputStream.close(); }
			 */
			ServletOutputStream servletOutputStream = resp.getOutputStream();
			if (jp != null) {

				jprintlist.add(jp);
				if (strDocType.equals("PDF")) {
					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=rptRoomTypeInventorReport_" + date + "_" + userCode + ".pdf");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();

				} else {
					JRExporter exporter = new JRXlsExporter();
					resp.setContentType("application/xlsx");
					exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=rptRoomTypeInventorReport_" + date + "_" + userCode + ".xls");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				}

			} else {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.getWriter().append("No Record Found");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}