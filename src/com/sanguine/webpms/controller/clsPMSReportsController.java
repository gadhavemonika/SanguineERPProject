package com.sanguine.webpms.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import com.sanguine.bean.clsStockFlashBean;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsBillPassHdModel;
import com.sanguine.model.clsBomHdModel;
import com.sanguine.model.clsExpiryFlashModel;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsSupplierMasterModel;
import com.sanguine.model.clsTreeMasterModel;
import com.sanguine.service.clsBillPassingService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsGroupMasterService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsRecipeMasterService;
import com.sanguine.service.clsRequisitionService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsStkAdjustmentService;
import com.sanguine.service.clsSubGroupMasterService;
import com.sanguine.service.clsSupplierMasterService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsPMSReportsController {

	@Autowired
	private clsSetupMasterService objSetupMasterService;
	@Autowired
	private ServletContext servletContext;
	private clsGlobalFunctions objGlobal = null;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	/**
	 * Report Code
	 * 
	 * @return
	 * @throws JRException
	 * @Authjor Jai chandra 03-02-2015
	 */

	/**
	 * Open Expected Wise Report form
	 * 
	 * @return
	 * @throws JRException
	 */
	@RequestMapping(value = "/frmExpectedDepartureList", method = RequestMethod.GET)
	public ModelAndView funOpenProductwiseSupplierwiseForm(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmExpectedDepartureList");
		String webStockDB=request.getSession().getAttribute("WebStockDB").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		List listOfProperty = objGlobalFunctionsService.funGetList("select strPropertyName from "+webStockDB+".tblpropertymaster");
		listOfProperty.add(0, "ALL");
		model.put("listOfProperty", listOfProperty);

		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExpectedDepartureList_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmExpectedDepartureList", "command", new clsReportBean());
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/rptExpectedDepartureList", method = RequestMethod.POST)
	public void funConsReceiptValStoreWiseBreskUPReport(@ModelAttribute("command") clsReportBean objBean, HttpServletRequest request, HttpServletResponse resp) {
		try {
			objGlobal = new clsGlobalFunctions();
			String companyName = request.getSession().getAttribute("companyName").toString();
			String userCode = request.getSession().getAttribute("usercode").toString();
			String propertyCode = "";
			String propCode = request.getSession().getAttribute("propertyCode").toString();
			String clientCode = request.getSession().getAttribute("clientCode").toString();
			String webStockDB=request.getSession().getAttribute("WebStockDB").toString();
			String fromDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtFromDate());
			String toDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtToDate());
			String fromlocation = objBean.getStrFromLocCode();
			String type = objBean.getStrDocType();
			String propName = objBean.getStrPropertyCode();
			if (!propName.equals("ALL")) {
				List listOfProperty = objGlobalFunctionsService.funGetList(" select strPropertyCode from "+webStockDB+".tblpropertymaster where strPropertyName='" + propName + "' and strClientCode='" + clientCode + "' ");
				if (!listOfProperty.isEmpty()) {
					propertyCode = (String) listOfProperty.get(0);
				}
			}
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			Connection con = objGlobal.funGetConnection(request);

			String sql = " select p.strPropertyName,a.strRegistrationNo," + " DATE_FORMAT(a.dteArrivalDate,'%d-%m-%Y') as dteArrivalDate," + " a.tmeArrivalTime,a.strReservationNo,e.strRoomTypeDesc," + " DATE_FORMAT(a.dteDepartureDate,'%d-%m-%Y') as dteDepartureDate," + " a.tmeDepartureTime ,f.strRoomDesc,c.strCountry,c.strFirstName," + " c.strLastName "
					+ " from "+webStockDB+".tblpropertymaster p,tblcheckinhd a,tblcheckindtl b,tblguestmaster c ," + " tblreservationhd d,tblroomtypemaster e,tblroom f " + " where p.strPropertyCode=d.strPropertyCode " + " and a.strCheckInNo=b.strCheckInNo and b.strGuestCode=c.strGuestCode " + " and a.strReservationNo=d.strReservationNo and b.strRoomNo=f.strRoomCode"
					+ " and f.strRoomTypeCode=e.strRoomTypeCode " + " and date(a.dteDepartureDate) between '"
					+ fromDate + "' and '" + toDate + "' " + " and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + clientCode + "' " + " and c.strClientCode='" + clientCode + "' " + " and d.strClientCode='" + clientCode + "' " + " and e.strClientCode='" + clientCode + "' " + " and f.strClientCode='" + clientCode + "' " + " and p.strClientCode='" + clientCode + "' ";
			if (!propName.equals("ALL")) {
				sql = sql + " and d.strPropertyCode='" + propertyCode + "'  ";
			}
			sql = sql + " order by p.strPropertyCode,a.dteDepartureDate ";

			String reportName = servletContext.getRealPath("/WEB-INF/reports/webpms/rptExpectedDepartureList.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sql);
			jd.setQuery(newQuery);
			JasperReport jr = JasperCompileManager.compileReport(jd);

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
			hm.put("dtFromDate", objBean.getDtFromDate());
			hm.put("dtToDate", objBean.getDtToDate());

			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			/**
			 * Exporting Report in various Format
			 */
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setContentLength(bytes.length);
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptExpectedDepartureList." + type.trim());
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptExpectedDepartureList." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
