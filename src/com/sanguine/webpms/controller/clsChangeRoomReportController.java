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
import com.sanguine.webpms.bean.clsChangeRoomReportBean;
import com.sanguine.webpms.bean.clsCheckInListReportBean;
import com.sanguine.webpms.bean.clsFolioPrintingBean;
import com.sanguine.webpms.service.clsFolioService;

@Controller
public class clsChangeRoomReportController {

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
	@RequestMapping(value = "/frmChangeRoomReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmChangeRoomReport_1", "command", new clsChangeRoomReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmChangeRoomReport", "command", new clsChangeRoomReportBean());
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rptChangeRoom", method = RequestMethod.GET)
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
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webpms/rptChangeRoomReport.jrxml");
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
			String sqlParametersChangeRoom = "select f.strRoomNo as previousRoom,f.strRoomTypeCode as previousroomType,d.strFolioNo,concat(e.strFirstName,' ',e.strMiddleName,' ',e.strLastName) "
						+ " ,b.strRemarks,g.strReasonDesc,a.strRoomDesc as newRoom,a.strRoomTypeDesc as newRoomType,f.strUserEdited,DATE_FORMAT(f.dteChangeDate,'%Y-%m-%d %H:%i:%s') "
						+ " from tblfoliohd d,tblcheckinhd b left outer join tblcheckindtl c on b.strCheckInNo=c.strCheckInNo " 
						+ " left outer join tblroom a on a.strRoomCode=c.strRoomNo , "
						+ " tblguestmaster e,tblchangeroom f,tblreasonmaster g "
						+ " where  a.strStatus!='Free' and a.strClientCode='"+clientCode+"' " 
						+ " and b.strCheckInNo=d.strCheckInNo "
						+ " and c.strGuestCode=e.strGuestCode and g.strReasonCode=b.strReasonCode"
						+ " and d.strFolioNo = f.strFolioNo and date(f.dteChangeDate) between '"+fromDate+"' and '"+toDate+"'";

			List listOfChangeRoom = objGlobalFunctionsService.funGetDataList(sqlParametersChangeRoom, "sql");
			ArrayList fieldList = new ArrayList();

			for (int i = 0; i < listOfChangeRoom.size(); i++) {
				Object[] arr = (Object[]) listOfChangeRoom.get(i);
				clsChangeRoomReportBean objBean = new clsChangeRoomReportBean();
				objBean.setStrOldRoomName(arr[0].toString());
				objBean.setStrOldRoomTypeDesc(arr[1].toString());
				objBean.setStrFolioNo(arr[2].toString());
				objBean.setStrGuestName(arr[3].toString());
				objBean.setStrReasonCode(arr[5].toString());
				objBean.setStrRemarks(arr[4].toString());
				objBean.setStrNewRoomName(arr[6].toString());
				objBean.setStrNewRoomTypeDesc(arr[7].toString());
				objBean.setStrUserChange(arr[8].toString());
				objBean.setDteChangeRoom(arr[9].toString());
				fieldList.add(objBean);
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
				resp.setHeader("Content-Disposition", "inline;filename=ChangeRoomReport.pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
