package com.sanguine.webbooks.controller;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webbooks.bean.clsLetterMasterBean;
import com.sanguine.webbooks.bean.clsLetterProcessingBean;
import com.sanguine.webbooks.model.clsLetterMasterModel;
import com.sanguine.webbooks.model.clsLetterMasterModel_ID;
import com.sanguine.webbooks.model.clsLetterProcessingModel;
import com.sanguine.webbooks.model.clsLetterProcessingModel_ID;
import com.sanguine.webbooks.service.clsLetterProcessingService;

@Controller
public class clsLetterProcessingController {

	@Autowired
	private clsLetterProcessingService objLetterProcessingService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	private clsGlobalFunctions objGlobal = null;

	// Open LetterProcessing
	@RequestMapping(value = "/frmLetterProcessing", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		ArrayList<String> listReminderStatusUpdateLog = new ArrayList<>();
		listReminderStatusUpdateLog.add("Yes");
		listReminderStatusUpdateLog.add("No");

		List<String> listParameters = new ArrayList<>();

		listParameters = objGlobalFunctionsService.funGetDataList("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = 'vwdebtormemberdtl'", "sql");

		model.put("urlHits", urlHits);
		model.put("listReminderStatusUpdateLog", listReminderStatusUpdateLog);
		model.put("listParameters", listParameters);

		if (urlHits.equalsIgnoreCase("1")) {
			return new ModelAndView("frmLetterProcessing", "command", new clsLetterProcessingBean());
		} else {
			return new ModelAndView("frmLetterProcessing_1", "command", new clsLetterProcessingBean());
		}
	}

	// Save or Update Letter Master
	@RequestMapping(value = "/saveLetterProcessing", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsLetterProcessingBean objBean, BindingResult result, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			funPrepareAndSaveModel(objBean, clientCode, userCode, propertyCode);

			req.getSession().setAttribute("letterCode", objBean.getStrLetterCode());
			req.getSession().setAttribute("fromDate", objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteDrDate()));
			req.getSession().setAttribute("toDate", objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteCrDate()));

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Letter Code : ".concat(objBean.getStrLetterCode()));

			return new ModelAndView("redirect:/frmLetterProcessing.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmLetterProcessing.html?saddr=" + urlHits);
		}
	}

	private void funPrepareAndSaveModel(clsLetterProcessingBean objBean, String clientCode, String userCode, String propertyCode) {
		objGlobal = new clsGlobalFunctions();

		objLetterProcessingService.funClearLetterProcessing(userCode);

		String sqlQuery = "";
		if (objBean.getStrCondition().trim().length() > 0) {
			sqlQuery = "select Customer_Code,Member_Full_Name from dbwebmms.vwdebtormemberdtl  where " + objBean.getStrCondition();
		} else {
			sqlQuery = "select Customer_Code,Member_Full_Name from dbwebmms.vwdebtormemberdtl  ";
		}
		List listDebtors = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");

		for (int i = 0; i < listDebtors.size(); i++) {
			clsLetterProcessingModel objModel = new clsLetterProcessingModel();

			objModel.setStrLetterCode(objBean.getStrLetterCode());
			objModel.setStrLetterName(objBean.getStrLetterName().toUpperCase());

			objModel.setStrCatCode(objBean.getStrCatCode());
			objModel.setStrCatName(objBean.getStrCatName());
			objModel.setStrArea(objBean.getStrArea());
			objModel.setStrCity(objBean.getStrCity());
			objModel.setStrZip(objBean.getStrZip());
			objModel.setStrDebtorAddr1(objBean.getStrDebtorAddr1());
			objModel.setStrDebtorAddr2(objBean.getStrDebtorAddr2());
			objModel.setStrDebtorAddr3(objBean.getStrDebtorAddr3());
			objModel.setDblOpBal(objBean.getDblOpBal());
			objModel.setDblOutstanding(objBean.getDblOutstanding());
			objModel.setStrLetter(objBean.getStrLetter());

			objModel.setStrUserCreated(userCode);
			objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

			objModel.setStrUserEdited(userCode);
			objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

			objModel.setStrClientCode(clientCode);
			objModel.setStrPropertyCode(propertyCode);

			Object[] arrDebtors = (Object[]) listDebtors.get(i);

			objModel.setStrDebtorCode(arrDebtors[0].toString());
			objModel.setStrDebtorName(arrDebtors[1].toString());

			objLetterProcessingService.funAddUpdateLetterProcessing(objModel);
		}
	}

	/* To Check SQL Query Syntax */

	@RequestMapping(value = "/checkSQLQueryWithParameters", method = RequestMethod.GET)
	public @ResponseBody String funCheckSQLQuerySyntax(@RequestParam("sqlQuery") String sqlQuery, HttpServletRequest request) {

		String response = "{\"result\":\"true\"}";
		try {
			@SuppressWarnings("rawtypes")
			List listDebtors = objLetterProcessingService.funGetDebtoMemberList(sqlQuery);
		} catch (Exception e) {
			response = "{\"result\":\"" + e.getCause().getMessage() + "\"}";
			// e.printStackTrace();
		} finally {
			return response;
		}
	}

	@RequestMapping(value = "/rptLetterProcessingSlip", method = RequestMethod.GET)
	public void funGenerateJasperReport(@RequestParam("letterCode") String letterCode, @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate, HttpServletRequest req, HttpServletResponse resp) {
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webbooks/rptLetterProcessingSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlDtl = "select a.strLetterCode,a.strLetterName,a.strDebtorCode,a.strDebtorName from tblletterprocesstemp a " + "where a.strLetterCode='" + letterCode + "' " + "and a.strClientCode='" + clientCode + "' " + "and a.strPropertyCode='" + propertyCode + "' ";

			// sqlDtl=sqlDtl+"and a.dteFromDate='"+fromDate+"'";
			// sqlDtl=sqlDtl+"and a.dteToDate='"+toDate+"'";

			sqlDtl = sqlDtl + "group by a.strLetterCode,a.strDebtorCode ";
			sqlDtl = sqlDtl + "order by a.strLetterCode,a.strDebtorCode ";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sqlDtl);
			jd.setQuery(subQuery);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			@SuppressWarnings("rawtypes")
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
			hm.put("strclientCode", clientCode);

			Connection con = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/dbwebbooks?user=root&password=root");
			} catch (Exception e) {
				System.out.println("Error in conection");
			}
			String type = "pdf";

			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setContentLength(bytes.length);
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptLetterProcessingSlip." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
