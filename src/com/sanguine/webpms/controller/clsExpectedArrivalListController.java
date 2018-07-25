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
import com.sanguine.webpms.bean.clsExpectedArrivalListBean;
import com.sanguine.webpms.bean.clsFolioPrintingBean;
import com.sanguine.webpms.service.clsFolioService;

@Controller
public class clsExpectedArrivalListController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private clsSetupMasterService objSetupMasterService;

	// Open Folio Printing
	@RequestMapping(value = "/frmExpectedArrivalList", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExpectedArrivalList_1", "command", new clsExpectedArrivalListBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExpectedArrivalList", "command", new clsExpectedArrivalListBean());
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rptExpectedArrivalList", method = RequestMethod.GET)
	public void funGenerateFolioPrintingReport(@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate, HttpServletRequest req, HttpServletResponse resp) {
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
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webpms/rptExpectedArrivalList.jrxml");
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
			String pAddress1 = objSetup.getStrAdd1() + "," + objSetup.getStrAdd2() + "," + objSetup.getStrCity();
			String pAddress2 = objSetup.getStrState() + "," + objSetup.getStrCountry() + "," + objSetup.getStrPin();

			companyName = companyName.equalsIgnoreCase(null) ? " " : companyName;
			pAddress1 = pAddress1.equalsIgnoreCase(null) ? " " : pAddress1;
			pAddress2 = pAddress2.equalsIgnoreCase(null) ? " " : pAddress2;
			reportParams.put("pCompanyName", companyName);
			reportParams.put("pAddress1", pAddress1);
			reportParams.put("pAddress2", pAddress2);
			reportParams.put("pContactDetails", "");
			reportParams.put("strImagePath", imagePath);
			reportParams.put("strUserCode", userCode);
			reportParams.put("pFromDate", fromDate);
			reportParams.put("pTtoDate", toDate);
			reportParams.put("propName", propName);

			// get all parameters
			String sqlParametersExpArrival = "SELECT a.strReservationNo,ifnull(h.strBookingTypeDesc,'NA'), DATE_FORMAT(a.dteDateCreated,'%d-%m-%Y'),ifnull(c.strCorporateDesc,'NA'), " + "ifnull(d.strDescription,'NA'), IFNULL(i.dblReceiptAmt,0), ifnull(e.strBookerName,'NA'),DATE_FORMAT(a.dteConfirmDate,'%d-%m-%Y'), "
					+ "DATE_FORMAT(a.dteCancelDate,'%d-%m-%Y'),ifnull(f.strDescription,'NA'),ifnull(g.strBillingInstDesc,'NA'),concat(j.strFirstName,' ',j.strMiddleName,' ',j.strLastName) " + ",j.strGuestCode FROM tblreservationhd a left outer join tblreservationdtl b on a.strReservationNo=b.strReservationNo  " + "left outer join tblcorporatemaster c on a.strCorporateCode=c.strCorporateCode  "
					+ "left outer join tblagentmaster d on a.strAgentCode=d.strAgentCode  " + "left outer join tblbookermaster e on a.strBookerCode=e.strBookerCode  " + "left outer join tblbusinesssource f on a.strBusinessSourceCode=f.strBusinessSourceCode " + "left outer join tblbillinginstructions g on a.strBillingInstCode=g.strBillingInstCode  "
					+ "left outer join tblbookingtype h on a.strBookingTypeCode=h.strBookingTypeCode " + "left outer join tblreceipthd i on a.strReservationNo=i.strRegistrationNo  " + "left outer join tblguestmaster j on j.strGuestCode=b.strGuestCode  " + "WHERE DATE(a.dteArrivalDate) BETWEEN '" + fromDate + "' and '" + toDate + "'  " + "and a.strClientCode='" + clientCode
					+ "' and a.strPropertyCode='" + propertyCode + "' " + " and a.strReservationNo not in (select strReservationNo from tblcheckinhd) ";

			List listOfExpArrival = objGlobalFunctionsService.funGetDataList(sqlParametersExpArrival, "sql");
			ArrayList fieldList = new ArrayList();

			for (int i = 0; i < listOfExpArrival.size(); i++) {
				Object[] arr = (Object[]) listOfExpArrival.get(i);
				String strReservationNo = arr[0].toString();
				String strBookingTypeDesc = arr[1].toString();
				String dteDateCreated = arr[2].toString();
				String strCorporateDesc = arr[3].toString();
				String agentDescription = arr[4].toString();
				String dblReceiptAmt = arr[5].toString();
				String strBookerName = arr[6].toString();
				String dteConfirmDate = arr[7].toString();
				String dteCancelDate = arr[8].toString();
				String businessSrc = arr[9].toString();
				String strBillingInstDesc = arr[10].toString();
				String strFirstName = arr[11].toString();
				String strGuestCode = arr[12].toString();

				clsExpectedArrivalListBean expectedArrivalListBean = new clsExpectedArrivalListBean();

				expectedArrivalListBean.setStrReservationNo(strReservationNo);
				expectedArrivalListBean.setStrBookingTypeDesc(strBookingTypeDesc);
				expectedArrivalListBean.setDteDateCreated(dteDateCreated);
				expectedArrivalListBean.setStrCorporateDesc(strCorporateDesc);
				expectedArrivalListBean.setAgentDescription(agentDescription);
				expectedArrivalListBean.setDblReceiptAmt(dblReceiptAmt);
				expectedArrivalListBean.setStrBookerName(strBookerName);
				expectedArrivalListBean.setDteConfirmDate(dteConfirmDate);
				expectedArrivalListBean.setDteCancelDate(dteCancelDate);
				expectedArrivalListBean.setBusinessSrc(businessSrc);
				expectedArrivalListBean.setStrBillingInstDesc(strBillingInstDesc);
				expectedArrivalListBean.setStrFirstName(strFirstName);
				expectedArrivalListBean.setStrGuestCode(strGuestCode);

				String sqlExpectedArrivalDtl = "select a.strFirstName,a.strMiddleName,a.strLastName,b.strRoomTypeDesc,a.strAddress,a.strArrivalFrom,a.strProceedingTo " + "from tblguestmaster a,tblroomtypemaster b,tblreservationhd c,tblroom d,tblreservationdtl e " + " where  date(c.dteArrivalDate) between '" + fromDate + "' and '" + toDate + "'  and c.strReservationNo='" + strReservationNo
						+ "' and c.strReservationNo=e.strReservationNo and e.strGuestCode=a.strGuestCode and d.strRoomTypeCode=b.strRoomTypeCode  group by e.strRoomType,e.strGuestCode";
				List expectedArrivalDtlList = objGlobalFunctionsService.funGetDataList(sqlExpectedArrivalDtl, "sql");
				for (int j = 0; j < expectedArrivalDtlList.size(); j++) {
					Object[] GuestArr = (Object[]) expectedArrivalDtlList.get(j);

					String guestFirstName = GuestArr[0].toString();
					String strMiddleName = GuestArr[1].toString();
					strMiddleName = strMiddleName.equalsIgnoreCase("NA") ? " " : strMiddleName;
					String strLastName = GuestArr[2].toString();
					String strRoomTypeDesc = GuestArr[3].toString();
					String strAddress = GuestArr[4].toString();
					String strArrivalFrom = GuestArr[5].toString();
					String strProceedingTo = GuestArr[6].toString();

					expectedArrivalListBean.setGuestFirstName(guestFirstName);
					expectedArrivalListBean.setStrMiddleName(strMiddleName);
					expectedArrivalListBean.setStrLastName(strLastName);
					expectedArrivalListBean.setStrRoomTypeDesc(strRoomTypeDesc);
					expectedArrivalListBean.setStrAddress(strAddress);
					expectedArrivalListBean.setStrArrivalFrom(strArrivalFrom);
					expectedArrivalListBean.setStrProceedingTo(strProceedingTo);
					fieldList.add(expectedArrivalListBean);
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
				resp.setHeader("Content-Disposition", "inline;filename=ExpectedArrival.pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// check
}