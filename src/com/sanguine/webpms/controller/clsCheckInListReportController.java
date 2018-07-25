package com.sanguine.webpms.controller;

import java.util.ArrayList;
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
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webpms.bean.clsCheckInListReportBean;
import com.sanguine.webpms.bean.clsFolioPrintingBean;
import com.sanguine.webpms.service.clsFolioService;

@Controller
public class clsCheckInListReportController {

	@Autowired
	private clsFolioService objFolioService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	// Open Folio Printing
	@RequestMapping(value = "/frmCheckInList", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCheckInList_1", "command", new clsCheckInListReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCheckInList", "command", new clsCheckInListReportBean());
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rptCheckInList", method = RequestMethod.GET)
	public void funGenerateCheckInListReport(@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate, HttpServletRequest req, HttpServletResponse resp) {
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webpms/rptCheckInList.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			List<clsFolioPrintingBean> dataList = new ArrayList<clsFolioPrintingBean>();
			@SuppressWarnings("rawtypes")
			String propNameSql = "select a.strPropertyName from "+webStockDB+".tblpropertymaster a " + " where a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' ";
			List listPropName = objGlobalFunctionsService.funGetDataList(propNameSql, "sql");
			String propName = "";
			if (listPropName.size() > 0) {
				propName = listPropName.get(0).toString();
			}

			HashMap reportParams = new HashMap();

			reportParams.put("pCompanyName", companyName);
			reportParams.put("pAddress1", objSetup.getStrAdd1() + "," + objSetup.getStrAdd2() + "," + objSetup.getStrCity());
			reportParams.put("pAddress2", objSetup.getStrState() + "," + objSetup.getStrCountry() + "," + objSetup.getStrPin());
			reportParams.put("pContactDetails", "");
			reportParams.put("strImagePath", imagePath);
			reportParams.put("strUserCode", userCode);
			reportParams.put("pFromDate", fromDate);
			reportParams.put("pTtoDate", toDate);
			reportParams.put("propName", propName);

			// get all parameters
			String sqlParametersCheckInList = " SELECT ch.strReservationNo, IFNULL(h.strBookingTypeDesc,'NA'), " + " DATE_FORMAT(ch.dteDateCreated,'%d-%m-%Y'),IFNULL(c.strCorporateDesc,'NA'), " + " IFNULL(k.strBookerName,'NA'), DATE_FORMAT(a.dteCancelDate,'%d-%m-%Y'), " + " IFNULL(f.strDescription,'NA'),IFNULL(g.strBillingInstDesc,'NA'), "
					+ " CONCAT(j.strFirstName,' ',j.strMiddleName,' ',j.strLastName),j.strGuestCode,ch.strCheckInNo,sum(i.dblReceiptAmt),b.strRoomNo " 
					+ " FROM tblcheckinhd ch " + " LEFT OUTER JOIN tblreservationhd a ON a.strReservationNo = ch.strReservationNo and a.strClientCode='"
					+ clientCode
					+ "' "
					+ " LEFT OUTER JOIN tblreservationdtl b ON a.strReservationNo=b.strReservationNo and b.strClientCode='"
					+ clientCode
					+ "' "
					+ " LEFT OUTER JOIN tblcorporatemaster c ON a.strCorporateCode=c.strCorporateCode and c.strClientCode='"
					+ clientCode
					+ "' "
					+ " LEFT OUTER JOIN tblbusinesssource f ON a.strBusinessSourceCode=f.strBusinessSourceCode and f.strClientCode='"
					+ clientCode
					+ "' "
					+ " LEFT OUTER JOIN tblbillinginstructions g ON a.strBillingInstCode=g.strBillingInstCode and g.strClientCode='"
					+ clientCode
					+ "' "
					+ " LEFT OUTER JOIN tblbookingtype h ON a.strBookingTypeCode=h.strBookingTypeCode and h.strClientCode='"
					+ clientCode
					+ "' "
					+ " LEFT OUTER JOIN tblreceipthd i ON ch.strCheckInNo=i.strCheckInNo And i.strAgainst='Check-In' and i.strClientCode='"
					+ clientCode
					+ "' "
					+ " LEFT OUTER JOIN tblguestmaster j ON j.strGuestCode=b.strGuestCode and j.strClientCode='"
					+ clientCode
					+ "' "
					+ " LEFT OUTER JOIN tblbookermaster k ON k.strBookerCode=a.strBookerCode AND k.strClientCode='"
					+ clientCode + "' " + " WHERE DATE(a.dteArrivalDate) BETWEEN '" + fromDate + "' and '" + toDate + "' " + " AND ch.strClientCode='" + clientCode + "' AND a.strPropertyCode='" + propertyCode + "' " + " group by ch.strReservationNo ";

			List listOfCheckIn = objGlobalFunctionsService.funGetDataList(sqlParametersCheckInList, "sql");
			ArrayList fieldList = new ArrayList();

			for (int i = 0; i < listOfCheckIn.size(); i++) {
				Object[] arr = (Object[]) listOfCheckIn.get(i);
				String strCheckInCode = arr[10].toString();
				clsCheckInListReportBean checkInListBean = new clsCheckInListReportBean();

				checkInListBean.setStrReservationNo(arr[0].toString());
				checkInListBean.setStrBookingTypeDesc(arr[1].toString());
				checkInListBean.setDteDateCreated(arr[2].toString());
				checkInListBean.setStrCorporateDesc(arr[3].toString());
				// checkInListBean.setAgentDescription(agentDescription);
				checkInListBean.setStrBookerName(arr[4].toString());
				checkInListBean.setDteCancelDate(arr[5].toString());
				checkInListBean.setBusinessSrc(arr[6].toString());
				checkInListBean.setStrBillingInstDesc(arr[7].toString());
				checkInListBean.setStrFirstName(arr[8].toString());
				checkInListBean.setStrGuestCode(arr[9].toString());
				checkInListBean.setDblReceiptAmt((arr[11] == null) ? 0 : Double.parseDouble(arr[11].toString()));
				checkInListBean.setStrRoomTypeCode(arr[12].toString());
				
				String sqlCheckInListDtl = " select a.strFirstName,a.strMiddleName,a.strLastName,b.strRoomTypeDesc,a.strAddress," 
						+ " a.strArrivalFrom,a.strProceedingTo,b.strRoomTypeCode"
						+ " from tblguestmaster a,tblroomtypemaster b,tblcheckinhd c,tblroom d,tblcheckindtl e " + " where  date(c.dteArrivalDate) between '" + fromDate + "' and '" + toDate + "' and c.strCheckInNo='" + strCheckInCode + "' "
						+ " and c.strCheckInNo=e.strCheckInNo and e.strGuestCode=a.strGuestCode" + " and e.strRoomNo=d.strRoomCode and  d.strRoomTypeCode=b.strRoomTypeCode" + " and  a.strClientCode='" + clientCode + "' and  b.strClientCode='" + clientCode + "' " + " and  c.strClientCode='" + clientCode + "' and  d.strClientCode='" + clientCode + "' " + " and  e.strClientCode='" + clientCode
						+ "' ";
				List checkInDtlList = objGlobalFunctionsService.funGetDataList(sqlCheckInListDtl, "sql");
				for (int j = 0; j < checkInDtlList.size(); j++) {
					Object[] GuestArr = (Object[]) checkInDtlList.get(j);
					checkInListBean.setGuestFirstName(GuestArr[0].toString());
					checkInListBean.setStrMiddleName(GuestArr[1].toString());
					checkInListBean.setStrLastName(GuestArr[2].toString());
					checkInListBean.setStrRoomTypeDesc(GuestArr[3].toString());
					checkInListBean.setStrAddress(GuestArr[4].toString());
					checkInListBean.setStrArrivalFrom(GuestArr[5].toString());
					checkInListBean.setStrProceedingTo(GuestArr[6].toString());
					
					fieldList.add(checkInListBean);
				}
			}

			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr, reportParams, beanCollectionDataSource);
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			if (jp != null) {
				jprintlist.add(jp);
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=CheckInList.pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
