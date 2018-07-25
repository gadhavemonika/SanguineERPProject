package com.sanguine.webpms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.sanguine.webpms.bean.clsCancelationReportBean;
import com.sanguine.webpms.bean.clsFolioPrintingBean;

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

@Controller
public class clsCancelationReportController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@RequestMapping(value = "/frmCancelationReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCancelationReport_1", "command", new clsCancelationReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCancelationReport", "command", new clsCancelationReportBean());
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rptCancelationReport", method = RequestMethod.GET)
	public void funGenerateCancelationReport(@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate, HttpServletRequest req, HttpServletResponse resp) {
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
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webpms/rptCancelationReport.jrxml");
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
			reportParams.put("pContactDetails", "");
			reportParams.put("strImagePath", imagePath);
			reportParams.put("strUserCode", userCode);
			reportParams.put("pFromDate", fromDate);
			reportParams.put("pTtoDate", toDate);
			reportParams.put("propName", propName);

			// get all parameters
			String sqlCancel = " select a.strReservationNo,CONCAT(c.strFirstName,' ',c.strMiddleName,' ',c.strLastName) as strGuestName, " + " e.strBookingTypeDesc,h.strRoomTypeDesc,DATE_FORMAT(b.dteReservationDate,'%d-%m-%Y') as dteReservationDate, " + " DATE_FORMAT(a.dteCancelDate,'%d-%m-%Y')  as dteCancelDate,f.strRoomDesc, g.strReasonDesc, a.strRemarks "
					+ " from tblroomcancelation a,tblreservationhd b,tblguestmaster c ,tblreservationdtl d,tblbookingtype e,tblroom f, tblreasonmaster g,tblroomtypemaster h " 
					+ " where date(a.dteCancelDate) " 
					+ " between '"
					+ fromDate
					+ "' and '"
					+ toDate
					+ "' "
					+ " and a.strReservationNo=b.strReservationNo "
					+ " and b.strCancelReservation='Y' "
					+ " and b.strReservationNo=d.strReservationNo "
					+ " and d.strGuestCode=c.strGuestCode "
					+ " and b.strBookingTypeCode = e.strBookingTypeCode "
					+ " AND d.strRoomType=f.strRoomTypeCode  "
					+ " and a.strReasonCode=g.strReasonCode "
					+ " AND a.strClientCode=b.strClientCode"
					+ " and h.strRoomTypeCode=d.strRoomType "
					+ "  group by b.strReservationNo,d.strGuestCode " ;

			List listOfCancelation = objGlobalFunctionsService.funGetDataList(sqlCancel, "sql");
			ArrayList fieldList = new ArrayList();

			for (int i = 0; i < listOfCancelation.size(); i++) {
				Object[] arr = (Object[]) listOfCancelation.get(i);
				clsCancelationReportBean objCancelBean = new clsCancelationReportBean();
				objCancelBean.setStrReservationNo(arr[0].toString());
				objCancelBean.setStrGuestName(arr[1].toString());
				objCancelBean.setStrBookingTypeDesc(arr[2].toString());
				objCancelBean.setStrRoomType(arr[3].toString());
				// checkInListBean.setAgentDescription(agentDescription);
				objCancelBean.setDteReservationDate(arr[4].toString());
				objCancelBean.setDteCancelDate(arr[5].toString());
				objCancelBean.setStrRoomDesc(arr[6].toString());
				objCancelBean.setStrReasonDesc(arr[7].toString());
				objCancelBean.setStrRemarks(arr[8].toString());

				fieldList.add(objCancelBean);

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
				resp.setHeader("Content-Disposition", "inline;filename=CancelationReport.pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
