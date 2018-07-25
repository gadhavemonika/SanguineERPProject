package com.sanguine.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JREmptyDataSource;
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

import com.ibm.icu.text.DecimalFormat;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import com.sanguine.bean.clsPurchaseRegisterReportBean;
import com.sanguine.model.clsBillPassHdModel;
import com.sanguine.model.clsBomHdModel;
import com.sanguine.model.clsExpiryFlashModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsStockFlashModel;
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
public class clsReportsController {

	@Autowired
	private clsGlobalFunctionsService objGlobalService;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	@Autowired
	private ServletContext servletContext;
	private clsGlobalFunctions objGlobal = null;
	@Autowired
	private clsSupplierMasterService objSupplierMasterService;
	@Autowired
	private clsLocationMasterService objLocationMasterService;
	@Autowired
	private clsSubGroupMasterService objSubGroupMasterService;
	@Autowired
	clsBillPassingService objBillPassingService;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	private clsGroupMasterService objGrpMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsLocationMasterService objLocService;

	@Autowired
	private clsRequisitionService objReqService;

	@Autowired
	private clsStkAdjustmentService objStkAdjService;

	@Autowired
	private clsRecipeMasterService objRecipeMasterService;

	/**
	 * Report Code
	 * 
	 * @return
	 * @throws JRException
	 * @Authjor Jai chandra 03-02-2015
	 */

	/**
	 * Open Product Wise Supplier Wise Report form
	 * 
	 * @return
	 * @throws JRException
	 */
	@RequestMapping(value = "/frmProductwiseSupplierwise", method = RequestMethod.GET)
	public ModelAndView funOpenProductwiseSupplierwiseForm(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmWebStockHelpProductWiseSupplierWise");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmProductwiseSupplierwise_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmProductwiseSupplierwise", "command", new clsReportBean());
		}

	}

	/**
	 * Call Product Wise Supplier Wise Report
	 * 
	 * @param objBean
	 * @param resp
	 * @param req
	 */
	@RequestMapping(value = "/rptProductwiseSupplierwise", method = RequestMethod.GET)
	private void funReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		funCallReport(objBean, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallReport(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		try {
			objGlobal = new clsGlobalFunctions();
			String fromDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtFromDate());
			String toDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtToDate());
			String suppCode = objBean.getStrDocCode();
			String type = objBean.getStrDocType();

			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String suppName = "";

			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptProductwiseSupplierwise.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlDtlQuery = "select c.strPCode as PartyCode,c.strPName as PartyName," + "a.strProdCode as ProdCode ,b.strProdName as ProductName,a.dblLastCost," + "b.strUOM,DATE_FORMAT(a.dtLastDate,'%d-%m-%Y'),a.strDefault " + "from tblprodsuppmaster a,tblproductmaster b,tblpartymaster c " + "where a.strSuppCode=c.strPCode and a.strProdCode=b.strProdCode "
					+ "and date(a.dtLastDate) between '" + fromDate + "' and '" + toDate + "' " + "and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "' ";
			if (suppCode.trim().length() > 0) {
				clsSupplierMasterModel objModel = objSupplierMasterService.funGetObject(suppCode, clientCode);
				suppName = objModel.getStrPName();
				sqlDtlQuery = sqlDtlQuery + " and a.strSuppCode='" + suppCode + "'";
			}
			sqlDtlQuery = sqlDtlQuery + " order by DATE(a.dtLastDate),c.strPName,b.strProdName  ";
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sqlDtlQuery);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsProdwiseSuppwise");
			subDataset.setQuery(subQuery);
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
			hm.put("strSuppName", suppName);

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
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptProductwiseSupplierwise." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open Reorder level Wise form
	 * 
	 * @return
	 * @throws JRException
	 */
	@RequestMapping(value = "/frmReorderLevelwise", method = RequestMethod.GET)
	public ModelAndView funOpenReoderLevelwiseForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmReorderLevelwise_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmReorderLevelwise", "command", new clsReportBean());
		}
	}

	/**
	 * Call Reorder Level Wise Report
	 * 
	 * @param objBean
	 * @param resp
	 * @param req
	 */
	@RequestMapping(value = "/rptReorderLevelwise", method = RequestMethod.GET)
	private void funReportReoderLevelwise(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String locCode = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		funCallReportReoderLevelwise(locCode, type, resp, req);
	}

	@RequestMapping(value = "/invokeReorderLevelwise", method = RequestMethod.GET)
	private void funCallReportOnClickReoderLevelwise(@RequestParam(value = "docCode") String docCode, HttpServletResponse resp, HttpServletRequest req) {
		String type = "pdf";
		funCallReportReoderLevelwise(docCode, type, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallReportReoderLevelwise(String locCode, String type, HttpServletResponse resp, HttpServletRequest req) {
		try {
			objGlobal = new clsGlobalFunctions();
			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			clsLocationMasterModel objModel = objLocationMasterService.funGetObject(locCode, clientCode);
			String locName = objModel.getStrLocName();

			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptReorderLevelwise.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlDtlQuery = " select e.strGName,d.strSGName,b.strLocName as LocationName," + "c.strProdName as ProductName,a.dblReOrderQty as OrderQuantity ,a.dblReOrderLevel as ReOrderLevel  " + "from tblreorderlevel a,tbllocationmaster b,tblproductmaster c " + "left outer join tblsubgroupmaster d on c.strSGCode=d.strSGCode and  d.strClientCode='" + clientCode + "' "
					+ "left outer join tblgroupmaster e on e.strGCode=d.strGCode and e.strClientCode='" + clientCode + "' " + "where a.strLocationCode=b.strLocCode and a.strProdCode=c.strProdCode and b.strPropertyCode='" + propertyCode + "' " + "and a.strLocationCode='" + locCode + "'  and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' and c.strClientCode='"
					+ clientCode + "' ";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sqlDtlQuery);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsReorderLevelwise");
			subDataset.setQuery(subQuery);
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
			hm.put("strLocName", locName);

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
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptReorderLevelwise." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open Bill Passing Report Form
	 * 
	 * @return
	 * @throws JRException
	 * @Author Vikash Kumar 05-02-2015
	 **/

	@RequestMapping(value = "/frmBillPassingReport", method = RequestMethod.GET)
	public ModelAndView funOpenBillPassingReortForm() throws JRException {
		return new ModelAndView("frmBillPassingReport", "command", new clsReportBean());
	}

	/**
	 * Call Bill Passing Report
	 * 
	 * @param objBean
	 * @param resp
	 * @param req
	 */
	@RequestMapping(value = "/rptBillPassingReport", method = RequestMethod.GET)
	private void funReportBillPassingReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String BPCode = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		funCallReportBillPassingReport(BPCode, type, resp, req);
	}

	@RequestMapping(value = "/invokeBillPassingReport", method = RequestMethod.GET)
	private void funCallReportOnClickBillPassingReport(@RequestParam(value = "docCode") String docCode, HttpServletResponse resp, HttpServletRequest req) {
		String type = "pdf";
		funCallReportBillPassingReport(docCode, type, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallReportBillPassingReport(String BPCode, String type, HttpServletResponse resp, HttpServletRequest req) {
		try {
			objGlobal = new clsGlobalFunctions();
			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptBillPassingReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			clsBillPassHdModel objBillPassHd = objBillPassingService.funGetObject(BPCode, clientCode);
			String narration = objBillPassHd.getStrNarration();
			String passDate = objBillPassHd.getDtPassDate();
			// String retDate=passDate;
			String[] onlydate = passDate.split(" ");
			String[] spPassDate = onlydate[0].split("-");
			passDate = spPassDate[2] + "-" + spPassDate[1] + "-" + spPassDate[0];

			String dtBill = objBillPassHd.getDtBillDate();
			String[] onlyBilldate = dtBill.split(" ");
			String[] spBillDate = onlyBilldate[0].split("-");
			dtBill = spBillDate[2] + "-" + spBillDate[1] + "-" + spBillDate[0];

			String against = objBillPassHd.getStrAgainst();
			String suppcode = objBillPassHd.getStrSuppCode();
			clsSupplierMasterModel objSuppModel = objSupplierMasterService.funGetObject(suppcode, clientCode);
			String suppName = objSuppModel.getStrPName();
			String sqlDtlQuery = " select a.strGRNCode , DATE_FORMAT(a.dtGRNDate,'%d-%m-%Y') as GRNDate ," + "ifNULL(a.strChallanNo,'') as ChallanNo ,a.dblGRNAmt ,a.dblAdjustAmt ,a.dblFreight ," + "a.dblSurcharge ,a.dblTotal ,DATE_FORMAT(b.dtBillDate,'%d-%m-%Y') as BillDate," + "DATE_FORMAT(b.dtPassDate,'%d-%m-%Y') as PassDate ,c.strPName,b.strNarration," + "ifNULL(b.strAgainst,'') as Againsts "
					+ "from tblbillpassdtl a ,tblbillpasshd b ,tblpartymaster c " + "where a.strBillPassNo='" + BPCode + "' and b.strSuppCode=c.strPCode " + "and a.strclientCode='" + clientCode + "' and a.strClientCode='" + clientCode + "' " + "and c.strClientCode='" + clientCode + "' ";

			double GRNAmt = 0.00;
			String getamt = "select sum(dblGRNAmt) from tblbillpassdtl " + "where strBillPassNo='" + BPCode + "' and strClientCode='" + clientCode + "' ";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs_getAmt = (ResultSet) stmt.executeQuery(getamt);
			if (rs_getAmt.next()) {
				GRNAmt = rs_getAmt.getDouble(1);
			}

			String sqlDtlQueyTbl2 = " select strTaxCode ,strTaxDesc ,strTaxableAmt ,strTaxAmt " + "from tblbillpassingtaxdtl where strBillPassNo='" + BPCode + "' and strclientCode='" + clientCode + "' ";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sqlDtlQuery);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsBP");
			subDataset.setQuery(subQuery);

			JRDesignQuery subQueryTbl2 = new JRDesignQuery();
			subQueryTbl2.setText(sqlDtlQueyTbl2);
			Map<String, JRDataset> datasetMapTbl2 = jd.getDatasetMap();
			JRDesignDataset subDatasetTbl2 = (JRDesignDataset) datasetMapTbl2.get("dsTaxdtl");
			subDatasetTbl2.setQuery(subQueryTbl2);

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
			hm.put("strSuppName", suppName);
			hm.put("strNarration", narration);
			hm.put("dtpassDate", passDate);
			hm.put("dtBillDate", dtBill);
			hm.put("strAgainst", against);
			hm.put("strTotGRNAmt", GRNAmt);
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
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptBillPassingReport." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open Cost Of Issue Report Form
	 * 
	 * @param req
	 * @return
	 * @throws JRException
	 */
	@RequestMapping(value = "/frmCostOfIssue", method = RequestMethod.GET)
	public ModelAndView funOpenStkTransferDetailForm(Map<String, Object> model, HttpServletRequest request) {
		ModelAndView mv = null;
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		Map<String, String> mapGroup = objGrpMasterService.funGetGroups(clientCode);
		mapGroup.put("ALL", "ALL");
		Map<String, String> mapSubGroup = new HashMap<String, String>();
		mapSubGroup.put("ALL", "ALL");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			mv = new ModelAndView("frmCostOfIssue_1", "command", new clsReportBean());

		} else {
			mv = new ModelAndView("frmCostOfIssue", "command", new clsReportBean());
		}
		mv.addObject("listgroup", mapGroup);
		mv.addObject("listsubGroup", mapSubGroup);

		return mv;
	}

	/**
	 * Calling Cost of Issue Report
	 * 
	 * @param objBean
	 * @param resp
	 * @param req
	 */
	@RequestMapping(value = "/rptCostOfIssue", method = RequestMethod.POST)
	private void funDetailform(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String stCode = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
	
		funCallReport(stCode, type, resp, req, objBean);
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void funCallReport(String stCode, String type, HttpServletResponse resp, HttpServletRequest req,
			clsReportBean objBean) {
		objGlobal = new clsGlobalFunctions();
		Connection con = objGlobal.funGetConnection(req);
		try {
			String fromDate = objBean.getDtFromDate();
			String toDate = objBean.getDtToDate();
			String tempToLoc[] = objBean.getStrToLocCode().split(",");
			String tempFromLoc[] = objBean.getStrFromLocCode().split(",");
			String tempSG[] = objBean.getStrSGCode().split(",");
			String strToLocCodes = "";
			String strFromLocCodes = "";
			String strSGCodes = "";

			for (int i = 0; i < tempFromLoc.length; i++) {
				if (strFromLocCodes.length() > 0) {
					strFromLocCodes = strFromLocCodes + " or a.strLocFrom='" + tempFromLoc[i] + "' ";
				} else {
					strFromLocCodes = "a.strLocFrom='" + tempFromLoc[i] + "' ";

				}
			}

			for (int i = 0; i < tempToLoc.length; i++) {
				if (strToLocCodes.length() > 0) {
					strToLocCodes = strToLocCodes + " or a.strLocTo='" + tempToLoc[i] + "' ";
				} else {
					strToLocCodes = "a.strLocTo='" + tempToLoc[i] + "' ";

				}
			}

			for (int i = 0; i < tempSG.length; i++) {
				if (strSGCodes.length() > 0) {
					strSGCodes = strSGCodes + " or e.strSGCode='" + tempSG[i] + "' ";
				} else {
					strSGCodes = "e.strSGCode='" + tempSG[i] + "' ";

				}
			}

			fromDate = objGlobal.funGetDate("yyyy-MM-dd", fromDate);
			toDate = objGlobal.funGetDate("yyyy-MM-dd", toDate);

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptCostOfIssue.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHDQuery = "select g.strLocName,f.strGName,e.strSGName,b.strProdCode,d.strProdName,if(d.strIssueUOM='null' || d.strIssueUOM=null,' ',d.strIssueUOM) as UOM,ifnull(sum(c.dblQty),'0') as reqQty, "
					+ " ifnull(sum(b.dblQty),'0') as misQty ,ifnull(sum(c.dblQty)-sum(b.dblQty),'0') as BalQty,b.dblUnitPrice as Rate ,"
					+ " sum(b.dblQty)*b.dblUnitPrice as Value "
					+ ",h.TaxPer,((h.TaxPer/100)*SUM(b.dblQty)*b.dblUnitPrice)TaxAmt "
					+ " from tblmishd a inner join tblmisdtl b on  a.strMISCode=b.strMISCode"
					+ " inner join tblproductmaster d on d.strProdCode=b.strProdCode "
					+ " left outer join  tblreqdtl c on  c.strReqCode=b.strReqCode and "
					+ " c.strProdCode=b.strProdCode  and c.strClientCode='" + clientCode + "' "
					+ " left outer join tblsubgroupmaster e on e.strSGCode=d.strSGCode and e.strClientCode='"
					+ clientCode + "' "
					+ " left outer join tblgroupmaster f on e.strGCode=f.strGCode and f.strClientCode='" + clientCode
					+ "'  " + " left outer join tbllocationmaster g on g.strLocCode=a.strLocTo and g.strClientCode='"
					+ clientCode + "' "
					+ " left outer join (select b.strProdCode,b.strProdName,sum(a.dblPercent)TaxPer "
					+ "from tbltaxhd a,tblproductmaster b\r\n" + "where a.strTaxIndicator=b.strTaxIndicator "
					+ "group by b.strProdCode) h on b.strProdCode=h.strProdCode and d.strProdCode=h.strProdCode "
					+ " where Date(a.dtMISDate ) between '" + fromDate + "' and '" + toDate + "' and a.strClientCode='"
					+ clientCode + "' " + " and b.strClientCode='" + clientCode + "'   and d.strClientCode='"
					+ clientCode + "'  ";

			sqlHDQuery = sqlHDQuery + " and " + "(" + strFromLocCodes + ")" + "and " + "(" + strToLocCodes + ")"
					+ " and " + "(" + strSGCodes + ")"
					+ " GROUP by f.strGName,b.strProdCode order by g.strLocName,f.strGName,b.strProdCode ";
			System.out.println(sqlHDQuery);
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlHDQuery);
			jd.setQuery(newQuery);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode.toUpperCase());
			hm.put("strImagePath", imagePath);
			// System.out.println(imagePath);
			hm.put("strAddr1", objSetup.getStrAdd1());
			hm.put("strAddr2", objSetup.getStrAdd2());
			hm.put("strCity", objSetup.getStrCity());
			hm.put("strState", objSetup.getStrState());
			hm.put("strCountry", objSetup.getStrCountry());
			hm.put("strPin", objSetup.getStrPin());
			hm.put("fromDate", objBean.getDtFromDate());
			hm.put("toDate", objBean.getDtToDate());
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
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptCostOfIssue." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

////Cost Issue Report For 1000 Oaks
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/rptCostOfIssueExport", method = RequestMethod.POST)
	private ModelAndView funExportExcelCostIssueReport(String stCode, String type, HttpServletResponse resp, HttpServletRequest req, clsReportBean objBean) {
		objGlobal = new clsGlobalFunctions();
		Connection con = objGlobal.funGetConnection(req);
		List ExportList = new ArrayList();
		
			String fromDate = objBean.getDtFromDate();
			String toDate = objBean.getDtToDate();
			String tempToLoc[] = objBean.getStrToLocCode().split(",");
			String tempFromLoc[] = objBean.getStrFromLocCode().split(",");
			String tempSG[] = objBean.getStrSGCode().split(",");
			String strToLocCodes = "";
			String strFromLocCodes = "";
			String strSGCodes = "";
			List listStock = new ArrayList();
			String userCode = req.getSession().getAttribute("usercode").toString();

			ExportList.add("CostOfIssue_" + fromDate + "to" + toDate + "_" + userCode);

			List titleData = new ArrayList<>();
			titleData.add("Cost Of Issue Report");
			ExportList.add(titleData);

			List filterData = new ArrayList<>();
			filterData.add("From Date");
			filterData.add(fromDate);
			filterData.add("To Date");
			filterData.add(toDate);

			ExportList.add(filterData);
			for (int i = 0; i < tempFromLoc.length; i++) {
				if (strFromLocCodes.length() > 0) {
					strFromLocCodes = strFromLocCodes + " or a.strLocFrom='" + tempFromLoc[i] + "' ";
				} else {
					strFromLocCodes = "a.strLocFrom='" + tempFromLoc[i] + "' ";

				}
			}

			for (int i = 0; i < tempToLoc.length; i++) {
				if (strToLocCodes.length() > 0) {
					strToLocCodes = strToLocCodes + " or a.strLocTo='" + tempToLoc[i] + "' ";
				} else {
					strToLocCodes = "a.strLocTo='" + tempToLoc[i] + "' ";

				}
			}

			for (int i = 0; i < tempSG.length; i++) {
				if (strSGCodes.length() > 0) {
					strSGCodes = strSGCodes + " or f.strSGCode='" + tempSG[i] + "' ";
				} else {
					strSGCodes = "f.strSGCode='" + tempSG[i] + "' ";

				}
			}

			fromDate = objGlobal.funGetDate("yyyy-MM-dd", fromDate);
			toDate = objGlobal.funGetDate("yyyy-MM-dd", toDate);
			
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			Map<String,List> hmDtl=new HashMap<String,List>();
			Set setHeader=new HashSet();
			
			String sql="select sum(b.dblTotalPrice),a.strLocTo,d.strLocName as toLoc,a.strLocFrom ,c.strLocName as fromLoc from tblmishd a "
					+" left outer join tbllocationmaster c on a.strLocFrom =c.strLocCode " 
					+" left outer join tbllocationmaster d on a.strLocTo=d.strLocCode , "
					+" tblmisdtl b left outer join tblproductmaster  e on  b.strProdCode=e.strProdCode " 
					+" left outer join tblsubgroupmaster f on  e.strSGCode=f.strSGCode "
					+" where a.strMISCode=b.strMISCode and Date(a.dtMISDate ) between '" + fromDate + "' and '" + toDate + "' and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + clientCode + "'   and d.strClientCode='" + clientCode + "'  "
					+ " and " + "(" + strFromLocCodes + ")" + "and " + "(" + strToLocCodes + ")" + " and " + "(" + strSGCodes + ") " 
//					+ "and a.strLocFrom ='' and a.strLocTo and f.strSGCode=''"
					+ " group by a.strLocFrom,a.strLocTo "
					+" order by c.strLocName,d.strLocName "; 
			
			
			List listcostIssue = objGlobalService.funGetList(sql);
			if(listcostIssue.size()>0)
			{
				List listFinal=new ArrayList();
				for(int i=0;i<listcostIssue.size();i++)
				{
					Object[] obj= (Object[])listcostIssue.get(i);
					
					setHeader.add(obj[4].toString());
					 
			        if(hmDtl.containsKey(obj[2].toString()))
			        {
			        	listFinal = hmDtl.get(obj[2].toString());
			        	List dataset=new ArrayList();
			        	dataset.add(obj[0].toString());//Amount
						dataset.add(obj[2].toString());//To Location Name
						dataset.add(obj[4].toString());//From Location Name
						listFinal.add(dataset);
						hmDtl.put(obj[2].toString(), listFinal);
			        }else{
			        	    listFinal=new ArrayList();
			        	    List dataset=new ArrayList();
							dataset.add(obj[0].toString());//Amount
							dataset.add(obj[2].toString());//To Location Name
							dataset.add(obj[4].toString());//From Location Name
							listFinal.add(dataset);
							hmDtl.put(obj[2].toString(), listFinal);
			        }
				}
			}
			List finalList=new ArrayList();
			for(Map.Entry<String,List>entry:hmDtl.entrySet())
			{
				Iterator itr = setHeader.iterator();
				List listOuter= entry.getValue();
				List dataList=new ArrayList();
				dataList.add(entry.getKey());
				double totHoizontalAmt=0.0;
		        while(itr.hasNext())
		        {
		        	double amount=0.0;
		        	String toLoc=itr.next().toString();
	            	for(int i=0;i<listOuter.size();i++)
	            	{
	            		List listInner=(List) listOuter.get(i);
	            		if(toLoc.equals(listInner.get(2)))
	 		            {
	            			amount=Double.parseDouble(listInner.get(0).toString());
	 		            }
	            	}
	            	dataList.add(amount);
	            	totHoizontalAmt=totHoizontalAmt+amount;
		        }
		        dataList.add(totHoizontalAmt);
		        finalList.add(dataList);
				
			}
		
			String header = "";
//			String[] ExcelHeader =  new String[listMonth.size()]; ;
			Iterator itr = setHeader.iterator();
			header+=" "+",";
		    while(itr.hasNext())
		    {
				
				 header+=itr.next()+",";
				
				
			}
			header+="Total Qty";
			
			String[] ExcelHeader = header.split(",");
			ExportList.add(ExcelHeader);
			ExportList.add(finalList);
			
		return new ModelAndView("excelViewFromDateTodateWithReportName", "listFromDateTodateWithReportName", ExportList);
	}
	
	
	/**
	 * Open Receipt Register Report Form
	 * 
	 * @return
	 * @throws JRException
	 */
	@RequestMapping(value = "/frmReceiptRegister", method = RequestMethod.GET)
	public ModelAndView funOpenReceiptRegister(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmReceiptRegister");

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmReceiptRegister_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmReceiptRegister", "command", new clsReportBean());
		}

	}

	/**
	 * Calling Receipt Register Report
	 * 
	 * @param objBean
	 * @param resp
	 * @param req
	 */
	@RequestMapping(value = "/rptReceiptRegister", method = RequestMethod.POST)
	private void funReceiptRegister(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		funCallReceiptRegisterReport(objBean, resp, req);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void funCallReceiptRegisterReport(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		Connection con = objGlobal.funGetConnection(req);
		try {
			// String LocCode=objBean.getStrDocCode();
			String type = objBean.getStrDocType();
			String fromDate = objBean.getDtFromDate();
			String toDate = objBean.getDtToDate();
			String fromTempDate = objGlobal.funGetDate("yyyy-MM-dd", fromDate);
			String toTempDate = objGlobal.funGetDate("yyyy-MM-dd", toDate);
			String tempLoc[] = objBean.getStrDocCode().split(",");
			String tempSupp[] = objBean.getStrSuppCode().split(",");

			String strLocCodes = "";
			String strSuppCodes = "";

			for (int i = 0; i < tempLoc.length; i++) {
				if (strLocCodes.length() > 0) {
					strLocCodes = strLocCodes + " or a.strLocCode='" + tempLoc[i] + "' ";
				} else {
					strLocCodes = " a.strLocCode='" + tempLoc[i] + "' ";

				}
			}

			for (int i = 0; i < tempSupp.length; i++) {
				if (strSuppCodes.length() > 0) {
					strSuppCodes = strSuppCodes + " or a.strSuppCode='" + tempSupp[i] + "' ";
				} else {
					strSuppCodes = " a.strSuppCode='" + tempSupp[i] + "' ";

				}
			}

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup;
			objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);

			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}

			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptReceiptRegister.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHDQuery = "select c.strPName Supplier_Name,a.strGRNCode GRN_No,DATE_FORMAT(a.dtGRNDate,'%d-%m-%Y') GRN_Date, a.strAgainst GRN_Type, a.strPONo PO_No," + " a.strBillNo Bill_No, DATE_FORMAT(a.dtBillDate,'%d-%m-%Y') Bill_Date, a.dblSubTotal, a.dblTaxAmt, a.dblTotal, "
					+ " b.strProdCode P_Code, d.strProdName Product_Name, d.strUOM UOM, b.dblQty Qty_Recd, b.dblRejected Qty_Rejected, " + " b.dblUnitPrice Price, b.dblTotalPrice Amount, e.strLocName Location_Name " + " from tblgrnhd a, tblgrndtl b, tblpartymaster c, " + " tblproductmaster d, tbllocationmaster e  " + " Where a.strGRNCode = b.strGRNCode "
					+ " and a.strSuppCode = c.strPCode and a.strLocCode = e.strLocCode " + " and b.strProdCode = d.strProdCode  " + " and a.dtGRNDate >= '" + fromTempDate + "' " + " and a.dtGRNDate <= '" + toTempDate + "' and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "'" + " and d.strClientCode='" + clientCode
					+ "' and e.strClientCode='" + clientCode + "'";

			if (objBean.getStrDocCode() != "") {
				sqlHDQuery = sqlHDQuery + " and " + "(" + strLocCodes + ") ";
			}

			if (objBean.getStrSuppCode() != "") {
				sqlHDQuery = sqlHDQuery + " and " + "(" + strSuppCodes + ") ";
			}

			sqlHDQuery += " Order By Supplier_Name, a.strGRNCode";
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlHDQuery);
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
			hm.put("fromDate", fromDate);
			hm.put("toDate", toDate);
			hm.put("LocName", "");
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
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setContentType("application/vnd.ms-excel");
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptReceiptRegisterReport." + type.trim());
				exporterXLS.exportReport();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Open Reorder Level Report from
	 * 
	 * @param req
	 * @return
	 * @throws JRException
	 */
	@RequestMapping(value = "/frmReorderLevelReport", method = RequestMethod.GET)
	public ModelAndView funOpenReorderLevelReport(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsReportBean objBean = new clsReportBean();
		Map<String, String> mapGroup = objGrpMasterService.funGetGroups(clientCode);
		mapGroup.put("ALL", "ALL");
		Map<String, String> mapSubGroup = new HashMap<String, String>();
		mapSubGroup.put("ALL", "ALL");
		objBean.setGroup(mapGroup);
		objBean.setSubGroup(mapSubGroup);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmReorderLevelReport_1", "command", objBean);
		} else {
			return new ModelAndView("frmReorderLevelReport", "command", objBean);
		}
	}
	
	@RequestMapping(value = "/rptReorderLevelFromNotification", method = RequestMethod.GET)
	private void funReportReorderLevelFromNotification(@RequestParam(value = "locCode") String locCode, HttpServletResponse resp, HttpServletRequest req) {
		clsReportBean objBean=new clsReportBean();
		objBean.setStrGCode("");
		objBean.setStrSGCode("");
		locCode= req.getSession().getAttribute("locationCode").toString();
		funCallReorderLevelReport(locCode, "pdf", resp, req, objBean);
	}
	

	/**
	 * Calling Reorder Level Report
	 * 
	 * @param objBean
	 * @param resp
	 * @param req
	 */
	@RequestMapping(value = "/rptReorderLevelReport", method = RequestMethod.POST)
	private void funReorderLevelReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String LocCode = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		funCallReorderLevelReport(LocCode, type, resp, req, objBean);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void funCallReorderLevelReport(String LocCode, String type, HttpServletResponse resp, HttpServletRequest req, clsReportBean objBean) {
		objGlobal = new clsGlobalFunctions();
		Connection con = objGlobal.funGetConnection(req);
		try {
			String strSGCode = objBean.getStrSGCode();
			String strGCode = objBean.getStrGCode();
			String startDate = req.getSession().getAttribute("startDate").toString();
			String[] sp = startDate.split(" ");
			String[] spDate = sp[0].split("/");
			startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
			String fromDate = startDate;
			String toDate = objGlobal.funGetCurrentDate("yyyy-MM-dd");

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			clsLocationMasterModel objLocCode = objLocationMasterService.funGetObject(LocCode, clientCode);
			if (null == objLocCode) {
				objLocCode = new clsLocationMasterModel();
				objLocCode.setStrLocCode("Invalid Code");
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptReorderLevelReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String stockableItem = "Y";
			// objGlobalFunctions.funInvokeStockFlash(startDate, LocCode,
			// fromDate, toDate, clientCode, userCode,stockableItem);
			funStockFlash(startDate, LocCode, fromDate, toDate, clientCode, userCode, stockableItem, req, resp);
			String sqlHDQuery = "select a.strProdCode,a.strProdName,f.strGName,e.strSGName,a.strNonStockableItem,a.strPartNo," + " c.dblClosingStk,a.dblCostRM,a.dblWeight,b.dblReOrderLevel, b.dblReOrderQty, " + " ifnull(d.pendingQty,0) as OpenReq ,((b.dblReOrderLevel-c.dblClosingStk-ifnull(d.pendingQty,0))+ b.dblReOrderQty) as OrderQty,"
					+ " a.strIssueUOM,(a.dblCostRM * ((b.dblReOrderLevel-c.dblClosingStk- IFNULL(d.pendingQty,0))+ b.dblReOrderQty)) AS Value," + " case a.strClass " + " WHEN '' THEN 'NA' " + " else a.strClass " + " end  as strClass " + " from tblproductmaster a  " + " inner join tblreorderlevel b ON a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "' "
					+ " inner join tblcurrentstock c ON b.strProdCode = c.strProdCode and c.strClientCode='" + clientCode + "' and c.strUserCode = '" + userCode + "' " + " left outer join (select a.strProdCode, (a.dblQty - IFNULL(d.dblQty,0)) as pendingQty " + " from tblreqdtl a left outer join tblreqhd b ON a.strReqCode = b.strReqCode  and b.strClientCode='" + clientCode + "' "
					+ " left outer join tblmisdtl d ON  a.strReqCode=d.strReqCode and a.strProdCode = d.strProdCode " + " and d.strClientCode='" + clientCode + "' Where b.strLocby ='" + LocCode + "' and a.strClientCode='" + clientCode + "' " + "	Group By a.strProdCode) d ON a.strProdCode = d.strProdCode ";
			String strSGCodes = "";
			if(!strSGCode.equals("")){
				String tempstrSGCode[] = strSGCode.split(",");
				
				for (int i = 0; i < tempstrSGCode.length; i++) {
					if (strSGCodes.length() > 0) {
						strSGCodes = strSGCodes + " or a.strSGCode='" + tempstrSGCode[i] + "' ";
					} else {
						strSGCodes = "a.strSGCode='" + tempstrSGCode[i] + "' ";
					}
				}

			}
			String strGCodes = "";
			if(!strGCode.equals("")){
				String tempstrGCode[] = strGCode.split(",");
				
				for (int i = 0; i < tempstrGCode.length; i++) {
					if (strGCodes.length() > 0) {
						strGCodes = strGCodes + " or f.strGCode='" + tempstrGCode[i] + "' ";
					} else {
						strGCodes = "f.strGCode='" + tempstrGCode[i] + "' ";
					}

				}
			}
			

			sqlHDQuery = sqlHDQuery + " inner join tblsubgroupmaster e on a.strSGCode=e.strSGCode";
			if(!strSGCode.equals("")){
				sqlHDQuery = sqlHDQuery +" and (" + strSGCodes + ") ";
			}
			
			sqlHDQuery = sqlHDQuery	+ "  and e.strClientCode='" + clientCode + "' inner join tblgroupmaster f on e.strGCode=f.strGCode " ;
			
			if(!strGCode.equals("")){
				
				sqlHDQuery = sqlHDQuery +" and (" + strGCodes + ") ";
			
			}
			sqlHDQuery = sqlHDQuery	+ "  and f.strClientCode='" + clientCode + "' " + " where  b.strLocationCode='" + LocCode
					+ "' and c.dblClosingStk <= b.dblReOrderLevel and b.dblReOrderLevel > 0 and a.strClientCode='" + clientCode + "' ";
			List masterList = objGlobalFunctionsService.funGetListReportQuery(sqlHDQuery);

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlHDQuery);
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
			hm.put("Location", objLocCode.getStrLocName());

			JRDataSource jrDataSource = new JRBeanCollectionDataSource(masterList);
			JasperPrint p = JasperFillManager.fillReport(jr, hm, jrDataSource);
			/**
			 * Exporting Report in various Format
			 */
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptReorderLevelReport." + type.trim());
				resp.setContentLength(bytes.length);
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptReorderLevelReport." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	/**
	 * Export to Excel Reorder Level Report
	 * 
	 * @param param1
	 * @param resp
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/ExportReOrderLevelRpt", method = RequestMethod.GET)
	private ModelAndView funExportXLSReorderLevelReport(@RequestParam(value = "param1") String param1, @RequestParam(value = "param2") String param2, HttpServletResponse resp, HttpServletRequest req, ModelMap map) {
		String[] spParam1 = param1.split(",");
		String LocCode = spParam1[1];
		ModelAndView mv = new ModelAndView();
		objGlobal = new clsGlobalFunctions();
		try {
			String startDate = req.getSession().getAttribute("startDate").toString();
			String[] sp = startDate.split(" ");
			String[] spDate = sp[0].split("/");
			startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];

			String toDate = objGlobal.funGetCurrentDate("yyyy-MM-dd");
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();

			String fromDate = startDate;
			String stockableItem = "Y";
			funStockFlash(startDate, LocCode, fromDate, toDate, clientCode, userCode, stockableItem, req, resp);
			// objGlobalFunctions.funInvokeStockFlash(startDate, LocCode,
			// fromDate, toDate, clientCode, userCode,stockableItem);
			String sqlHDQuery = "select ifnull(a.strProdCode,'') as strProdCode,ifnull(a.strProdName,'') as strProdName," 
					+ " ifnull(f.strGName,'') as strGName,ifnull(e.strSGName,'') as strSGName,ifnull(a.strNonStockableItem,'') as strNonStockableItem,ifnull(a.strPartNo,'') as strPartNo,"
					+ " ifnull(c.dblClosingStk,0) as dblClosingStk ,ifnull(a.dblCostRM,'') as dblCostRM ,ifnull(a.dblWeight,'') as dblWeight,ifnull(b.dblReOrderLevel,'') as dblReOrderLevel, ifnull(b.dblReOrderQty,'') as dblReOrderQty, " 
					+ " ifnull(d.pendingQty,0) as OpenReq ,((b.dblReOrderLevel-c.dblClosingStk-ifnull(d.pendingQty,0))+ b.dblReOrderQty) as OrderQty,"
					+ " a.strIssueUOM,(a.dblCostRM * ((b.dblReOrderLevel-c.dblClosingStk- IFNULL(d.pendingQty,0))+ b.dblReOrderQty)) AS Value," 
					+ "	ifnull(h.strPCode,'') as strSuppCode,ifnull(h.strPName,'') as strSupplierName ," + " ifnull(g.dblLastCost,0) as dblLastCost ,ifnull(date(g.dtLastDate),'') as dtLastDate ," 
					+ " case a.strClass " + " WHEN '' THEN 'NA' " + " else a.strClass "
					+ " end  as strClass " + " from tblproductmaster a  " + " inner join tblreorderlevel b ON a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "' " 
					+ " inner join tblcurrentstock c ON b.strProdCode = c.strProdCode and c.strClientCode='" + clientCode + "' and c.strUserCode = '" + userCode + "' "
					+ " left outer join (select a.strProdCode, (a.dblQty - IFNULL(d.dblQty,0)) as pendingQty " + " from tblreqdtl a left outer join tblreqhd b ON a.strReqCode = b.strReqCode  and b.strClientCode='" + clientCode + "' " + " left outer join tblmisdtl d ON  a.strReqCode=d.strReqCode and a.strProdCode = d.strProdCode " + " and d.strClientCode='" + clientCode + "' Where b.strLocby ='"
					+ LocCode + "' and a.strClientCode='" + clientCode + "' " + "	Group By a.strProdCode) d ON a.strProdCode = d.strProdCode ";

			sqlHDQuery = sqlHDQuery + "left outer join tblprodsuppmaster g on g.strProdCode=a.strProdCode" + " and g.strDefault='Y'  and g.strClientCode='" + clientCode + "' " + " left outer join tblpartymaster h on h.strPCode=g.strSuppCode and h.strClientCode='" + clientCode + "' ";

			String tempstrSGCode[] = param1.split(",");
			String strSGCodes = "";
			for (int i = 2; i < tempstrSGCode.length; i++) {
				if (strSGCodes.length() > 0) {
					strSGCodes = strSGCodes + " or a.strSGCode='" + tempstrSGCode[i] + "' ";
				} else {
					strSGCodes = "a.strSGCode='" + tempstrSGCode[i] + "' ";
				}

			}
			sqlHDQuery = sqlHDQuery + " left outer join tblsubgroupmaster e on a.strSGCode=e.strSGCode and " + "(" + strSGCodes + ") " + "  and e.strClientCode='" + clientCode + "' " + "	left outer join tblgroupmaster f on e.strGCode=f.strGCode and f.strClientCode='" + clientCode + "' " + " where  b.strLocationCode='" + LocCode
					+ "' and c.dblClosingStk <= b.dblReOrderLevel and b.dblReOrderLevel > 0 and a.strClientCode='" + clientCode + "' ";

			String tempstrGCode[] = param2.split(",");
			String strGCodes = "";
			for (int i = 0; i < tempstrGCode.length; i++) {
				if (strGCodes.length() > 0) {
					strGCodes = strGCodes + " or a.strGCode='" + tempstrGCode[i] + "' ";
				} else {
					strGCodes = "a.strGCode='" + tempstrGCode[i] + "' ";
				}

			}
			sqlHDQuery = sqlHDQuery + " and " + "(" + strSGCodes + ") " + " " + " group by f.strGCode,e.strSGCode,a.strProdCode ";
			mv = funExcelExport(sqlHDQuery, req, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * Excel Export Function
	 * 
	 * @param sql
	 * @param req
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ModelAndView funExcelExport(String sql, HttpServletRequest req, ModelMap map) {
		List listStock = new ArrayList();
		String[] ExcelHeader = { "Product Code", "Product Name", "Class", "Group", "Sub Group", "Non Stockable Item", "UOM", "Closing Stock", "Rate", "ReOrder Level", "ReOrder Qty", "Open Req", "Order Qty", "Value", "Supplier Code", "Supplier Name", "Last Price", "Last Date" };
		listStock.add(ExcelHeader);

		List listStockFlashModel = new ArrayList();
		List list = objGlobalFunctionsService.funGetList(sql, "sql");
		if (list.size() > 0 && !list.isEmpty() && list != null) {
			for (int i = 0; i < list.size(); i++) {
				Object[] arrObj = (Object[]) list.get(i);
				List DataList = new ArrayList<>();
				DataList.add(arrObj[0].toString());
				DataList.add(arrObj[1].toString());
				DataList.add(arrObj[19].toString());
				DataList.add(arrObj[2].toString());
				DataList.add(arrObj[3].toString());
				DataList.add(arrObj[4].toString());
				DataList.add(arrObj[13].toString());
				DataList.add(Double.parseDouble(arrObj[6].toString()));
				DataList.add(Double.parseDouble(arrObj[7].toString()));
				DataList.add(Double.parseDouble(arrObj[9].toString()));
				DataList.add(Double.parseDouble(arrObj[10].toString()));
				DataList.add(Double.parseDouble(arrObj[11].toString()));
				if (Double.parseDouble(arrObj[12].toString()) < 0) {
					DataList.add((-1) * Double.parseDouble(arrObj[12].toString()));
					DataList.add((-1) * Double.parseDouble(arrObj[14].toString()));
				} else {
					DataList.add(Double.parseDouble(arrObj[12].toString()));
					DataList.add(Double.parseDouble(arrObj[14].toString()));
				}
				DataList.add(arrObj[15].toString());
				DataList.add(arrObj[16].toString());
				DataList.add(Double.parseDouble(arrObj[17].toString()));
				DataList.add(arrObj[18].toString());

				listStockFlashModel.add(DataList);
			}
			listStock.add(listStockFlashModel);

		}
		// return a view which will be resolved by an excel view resolver
		return new ModelAndView("excelView", "stocklist", listStock);

	}

	/**
	 * Open Non Moving Items Report Form
	 * 
	 * @param objPropBean
	 * @param result
	 * @param req
	 * @return
	 * @throws JRException
	 */
	@RequestMapping(value = "/frmNonMovingItemsReport", method = RequestMethod.GET)
	public ModelAndView funOpenNonMovingItemsReport(@ModelAttribute("command") clsReportBean objPropBean, BindingResult result, HttpServletRequest req) throws JRException {
		return funGetModelAndView(req);
	}

	@SuppressWarnings("unchecked")
	private ModelAndView funGetModelAndView(HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		ModelAndView objModelView = new ModelAndView("frmNonMovingItemsReport");

		Map<String, String> mapProperty = objGlobalService.funGetPropertyList(clientCode);
		if (mapProperty.isEmpty()) {
			mapProperty.put("", "");
		}
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String locationCode = req.getSession().getAttribute("locationCode").toString();

		objModelView.addObject("listProperty", mapProperty);
		objModelView.addObject("LoggedInProp", propertyCode);
		objModelView.addObject("LoggedInLoc", locationCode);

		HashMap<String, String> mapLocation = (HashMap<String, String>) objGlobalService.funGetLocationList(propertyCode, clientCode);
		if (mapLocation.isEmpty()) {
			mapLocation.put("", "");
		}
		mapLocation = clsGlobalFunctions.funSortByValues(mapLocation);
		objModelView.addObject("listLocation", mapLocation);
		return objModelView;
	}

	/**
	 * Calling Non Moving Items Report
	 * 
	 * @param objBean
	 * @param resp
	 * @param req
	 */
	@RequestMapping(value = "/rptNonMovingItemsReport", method = RequestMethod.POST)
	private void funNonMovingItemsReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		funCallNonMovingItemsReport(resp, req, objBean);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void funCallNonMovingItemsReport(HttpServletResponse resp, HttpServletRequest req, clsReportBean objBean) {
		String type = objBean.getStrDocType();
		String beanfromDate = objBean.getDtFromDate();
		String beantoDate = objBean.getDtToDate();
		String strSubGroupCodes = "";
		objGlobal = new clsGlobalFunctions();
		Connection con = objGlobal.funGetConnection(req);
		try {
			String fromDate = objGlobal.funGetDate("yyyy-MM-dd", beanfromDate);
			String toDate = objGlobal.funGetDate("yyyy-MM-dd", beantoDate);

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = objBean.getStrPropertyCode();
			String LocCode = objBean.getStrLocationCode();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptNonMovingReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/.png");

			String startDate = req.getSession().getAttribute("startDate").toString();
			String[] sp = startDate.split(" ");
			String[] spDate = sp[0].split("/");
			startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];

			String stockableItem = "Y";
			objGlobalFunctions.funInvokeStockFlash(startDate, LocCode, fromDate, toDate, clientCode, userCode, stockableItem, req, resp);
			String updateQuery = "update tblcurrentstock a join (select b.strProdCode,  max(a.dtMISDate) MISDate," + " b.dblQty, b.strMISCode from tblmishd a, tblmisdtl b where a.strMISCode = b.strMISCode " + " and date(a.dtMISDate) between '" + fromDate + "' and '" + toDate + "' " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' "
					+ " group by b.strProdCode) b on a.strProdCode = b.strProdCode " + " set strLastISDate_Qty = CONCAT(date(b.MISDate),CONCAT(CONCAT(' ','(',CONCAT(b.strMISCode,Concat('-',Round(b.dblQty,0)))),')'))   " + " where a.strUserCode='" + userCode + "' and a.strClientCode='" + clientCode + "' ";

			objGlobalFunctionsService.funUpdate(updateQuery, "sql");

			updateQuery = "update tblcurrentstock a join (select b.strProdCode,  max(a.dtGRNDate) GRNDate," + " b.dblQty, b.strGRNCode from tblgrnhd a, tblgrndtl b  " + " where a.strGRNCode = b.strGRNCode and date(a.dtGRNDate) between '" + fromDate + "' and '" + toDate + "' " + " group by b.strProdCode) b on a.strProdCode = b.strProdCode "
					+ "  set strLastGRNDate_Qty = CONCAT(date(b.GRNDate),CONCAT(concat(' ','(',CONCAT(b.strGRNCode,Concat('-',Round(b.dblQty,0)))),')')) " + " where a.strUserCode='" + userCode + "' and a.strClientCode='" + clientCode + "' ";
			objGlobalFunctionsService.funUpdate(updateQuery, "sql");

			String sql = "select a.strPropertyName,b.strLocName " + " from tblpropertymaster a ,tbllocationmaster b " + " where a.strPropertyCode=b.strPropertyCode " + " and a.strPropertyCode='" + propertyCode + "' and b.strLocCode='" + LocCode + "' ";
			List list = objGlobalFunctionsService.funGetList(sql, "sql");
			Object[] ob = (Object[]) list.get(0);
			String propertyName = ob[0].toString();
			String LocName = ob[1].toString();

			String sqlHDQuery = "select d.strGName,c.strSGName,a.strProdCode,a.strProdName,b.strIssueUOM,a.dblClosingStk,b.dblCostRM," + " (a.dblClosingStk *b.dblCostRM) as value ,a.strLastGRNDate_Qty as strLastGRNDate_Qty,a.strLastISDate_Qty from tblcurrentstock a " + " inner join tblproductmaster b on a.strProdCode=b.strProdCode and b.strClientCode='" + clientCode + "' "
					+ " inner join tblsubgroupmaster c on b.strSGCode=c.strSGCode and c.strClientCode='" + clientCode + "' " + " inner join tblgroupmaster d on c.strGCode=d.strGCode and d.strClientCode='" + clientCode + "' " + " where a.strUserCode='" + userCode + "' and a.strClientCode='" + clientCode + "'  and a.strLocCode='" + LocCode + "'";
			String strSubGroupCode[] = objBean.getStrSGCode().split(",");
			for (int i = 0; i < strSubGroupCode.length; i++) {
				if (strSubGroupCodes.length() > 0) {
					strSubGroupCodes = strSubGroupCodes + " or c.strSGCode='" + strSubGroupCode[i] + "' ";
				} else {
					strSubGroupCodes = "c.strSGCode='" + strSubGroupCode[i] + "' ";
				}
			}
			sqlHDQuery = sqlHDQuery + " and " + "(" + strSubGroupCodes + ")";
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlHDQuery);
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
			hm.put("dtFromDate", beanfromDate);
			hm.put("dtToDate", beantoDate);
			hm.put("strPropertyName", propertyName);
			hm.put("strLocName", LocName);
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
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptNonMovingItemsReport." + type.trim());
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptNonMovingItemsReport." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	/**
	 * Open Tax BreakUp Report Form
	 * 
	 * @param objPropBean
	 * @param result
	 * @param req
	 * @return
	 * @throws JRException
	 */
	@RequestMapping(value = "/frmTaxBreakUpReport", method = RequestMethod.GET)
	public ModelAndView funOpenTaxBerakUpReport(@ModelAttribute("command") clsReportBean objPropBean, BindingResult result, HttpServletRequest req) throws JRException {
		return funGetTaxBerakUpModelAndView(req);
	}

	@SuppressWarnings("unchecked")
	private ModelAndView funGetTaxBerakUpModelAndView(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		ModelAndView objModelView = new ModelAndView("frmTaxBreakUpReport");

		Map<String, String> mapProperty = objGlobalService.funGetPropertyList(clientCode);
		if (mapProperty.isEmpty()) {
			mapProperty.put("", "");
		}
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String locationCode = req.getSession().getAttribute("locationCode").toString();

		objModelView.addObject("listProperty", mapProperty);
		objModelView.addObject("LoggedInProp", propertyCode);
		objModelView.addObject("LoggedInLoc", locationCode);

		HashMap<String, String> mapLocation = (HashMap<String, String>) objGlobalService.funGetLocationList(propertyCode, clientCode);
		if (mapLocation.isEmpty()) {
			mapLocation.put("", "");
		}
		mapLocation = clsGlobalFunctions.funSortByValues(mapLocation);
		objModelView.addObject("listLocation", mapLocation);

		return objModelView;
	}

	/**
	 * Calling Tax BreakUp Report
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/rptTaxBreakUpReport", method = RequestMethod.POST)
	private void funTaxBreakupReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		funCallTaxBreakUpReport(resp, req, objBean);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void funCallTaxBreakUpReport(HttpServletResponse resp, HttpServletRequest req, clsReportBean objBean) {
		String type = objBean.getStrDocType();
		String beanfromDate = objBean.getDtFromDate();
		String beantoDate = objBean.getDtToDate();
		objGlobal = new clsGlobalFunctions();
		Connection con = objGlobal.funGetConnection(req);
		try {
			String fromDate = objGlobal.funGetDate("yyyy-MM-dd", beanfromDate);
			String toDate = objGlobal.funGetDate("yyyy-MM-dd", beantoDate);

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = objBean.getStrPropertyCode();
			String LocCode = objBean.getStrLocationCode();
			String taxCode = objBean.getStrTaxCode();
			String suppCode = objBean.getStrSuppCode();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptTaxBreakUpReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			String sqlDtlQuery = "select a.strGRNCode, a.dtGRNDate, c.strPName, a.strBillNo, b.strTaxCode," + "b.strTaxDesc, b.strTaxableAmt, b.strTaxAmt from tblgrnhd a, tblgrntaxdtl b, tblpartymaster c " + "where a.strGRNCode = b.strGRNCode  and a.strSuppCode= c.strPCode ";
			if (!taxCode.equals("") && !suppCode.equals("")) {
				sqlDtlQuery += " and b.strTaxCode='" + taxCode + "' and  c.strPCode='" + suppCode + "' ";
			}
			if (!taxCode.equals("") && suppCode.equals("")) {
				sqlDtlQuery += " and b.strTaxCode='" + taxCode + "' ";
			}
			if (taxCode.equals("") && !suppCode.equals("")) {
				sqlDtlQuery += " and  c.strPCode='" + suppCode + "' ";
			}
			sqlDtlQuery += "and  a.dtGRNDate between '" + fromDate + "' and '" + toDate + "' and a.strLocCode='" + LocCode + "' " + " and a.strClientCode = '" + clientCode + "' and b.strClientCode = '" + clientCode + "' and c.strClientCode = '" + clientCode + "' " + " order by a.dtGRNDate, a.strGRNCode ";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlDtlQuery);
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
			hm.put("dtFromDate", beanfromDate);
			hm.put("dtToDate", beantoDate);

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
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptTaxBreakUpReport." + type.trim());
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptTaxBreakUpReport." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	/**
	 * Open Slow Moving Items Report Form
	 * 
	 * @param objPropBean
	 * @param result
	 * @param req
	 * @return
	 * @throws JRException
	 */
	@RequestMapping(value = "/frmSlowMovingItemsReport", method = RequestMethod.GET)
	public ModelAndView funOpenSlowMovingItemsReport(@ModelAttribute("command") clsReportBean objPropBean, BindingResult result, HttpServletRequest req) throws JRException {
		return funGetNonMovingModelAndView(req);
	}

	@SuppressWarnings("unchecked")
	private ModelAndView funGetNonMovingModelAndView(HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		ModelAndView objModelView = new ModelAndView("frmSlowMovingItemsReport");

		Map<String, String> mapProperty = objGlobalService.funGetPropertyList(clientCode);
		if (mapProperty.isEmpty()) {
			mapProperty.put("", "");
		}
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String locationCode = req.getSession().getAttribute("locationCode").toString();
		objModelView.addObject("listProperty", mapProperty);
		objModelView.addObject("LoggedInProp", propertyCode);
		objModelView.addObject("LoggedInLoc", locationCode);

		HashMap<String, String> mapLocation = (HashMap<String, String>) objGlobalService.funGetLocationList(propertyCode, clientCode);
		if (mapLocation.isEmpty()) {
			mapLocation.put("", "");
		}
		mapLocation = clsGlobalFunctions.funSortByValues(mapLocation);
		objModelView.addObject("listLocation", mapLocation);
		return objModelView;
	}

	/**
	 * Calling Slow Moving Items Report
	 * 
	 * @param objBean
	 * @param resp
	 * @param req
	 */
	@RequestMapping(value = "/rptSlowMovingItemsReport", method = RequestMethod.POST)
	private void funSlowMovingItemsReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		funCallSlowMovingItemsReport(resp, req, objBean);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void funCallSlowMovingItemsReport(HttpServletResponse resp, HttpServletRequest req, clsReportBean objBean) {
		String type = objBean.getStrDocType();
		String beanfromDate = objBean.getDtFromDate();
		String beantoDate = objBean.getDtToDate();
		double dblPercentage = objBean.getDblPercentage();
		objGlobal = new clsGlobalFunctions();
		Connection con = objGlobal.funGetConnection(req);
		try {
			String fromDate = objGlobal.funGetDate("yyyy-MM-dd", beanfromDate);
			String toDate = objGlobal.funGetDate("yyyy-MM-dd", beantoDate);

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = objBean.getStrPropertyCode();
			String LocCode = objBean.getStrLocationCode();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptSlowMovingItemsReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String startDate = req.getSession().getAttribute("startDate").toString();
			String[] sp = startDate.split(" ");
			String[] spDate = sp[0].split("/");
			startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];

			String stockableItem = "Y";
			objGlobalFunctions.funInvokeStockFlash(startDate, LocCode, fromDate, toDate, clientCode, userCode, stockableItem, req, resp);
			String sql = "select a.strPropertyName,b.strLocName " + " from tblpropertymaster a ,tbllocationmaster b " + " where a.strPropertyCode=b.strPropertyCode " + " and a.strPropertyCode='" + propertyCode + "' and b.strLocCode='" + LocCode + "' ";
			List list = objGlobalFunctionsService.funGetList(sql, "sql");
			Object[] ob = (Object[]) list.get(0);
			String propertyName = ob[0].toString();
			String LocName = ob[1].toString();

			String strSubGroupCodes = "";

			String updateQuery = "update tblcurrentstock set dblPercentage = round((dblMISOut*100) /dblClosingStk,2) " + " where dblClosingStk != 0 and strUserCode='" + userCode + "' and strClientCode='" + clientCode + "' ";
			objGlobalFunctionsService.funUpdate(updateQuery, "sql");

			String sqlHDQuery = "select d.strGName,c.strSGName,a.strProdCode,a.strProdName,b.strUOM,dblOpeningStk as OpeningStk," + " (dblGRN+dblSCGRN+dblStkTransIn+dblStkAdjIn+dblMISIn+dblMaterialReturnIn+dblQtyProduced) as ReceiptQty , " + " (dblDeliveryNote+dblStkTransOut+dblStkAdjOut+dblMISOut+dblSales+dblMaterialReturnOut+dblQtyConsumed) as IssueQty, "
					+ " a.dblClosingStk as ClosingQty, (a.dblClosingStk*b.dblCostRM) as value, a.dblPercentage as Percentage " + " from tblcurrentstock  a, tblproductmaster b , tblsubgroupmaster c,tblgroupmaster d " + "  where a.strProdCode=b.strProdCode  and b.strSGCode=c.strSGCode and d.strGCode=c.strGCode and a.strLocCode='" + LocCode + "' " + " and a.strUserCode='" + userCode
					+ "' and a.strClientCode='" + clientCode + "' " + " and dblPercentage >=0 and dblPercentage <= " + dblPercentage + " ";

			String strSubGroupCode[] = objBean.getStrSGCode().split(",");
			for (int i = 0; i < strSubGroupCode.length; i++) {
				if (strSubGroupCodes.length() > 0) {
					strSubGroupCodes = strSubGroupCodes + " or c.strSGCode='" + strSubGroupCode[i] + "' ";
				} else {
					strSubGroupCodes = "c.strSGCode='" + strSubGroupCode[i] + "' ";
				}
			}
			sqlHDQuery = sqlHDQuery + " and " + "(" + strSubGroupCodes + ") ";

			sqlHDQuery = sqlHDQuery + " order by dblPercentage, d.strGName, c.strSGName ";
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlHDQuery);
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
			hm.put("dtFromDate", beanfromDate);
			hm.put("dtToDate", beantoDate);
			hm.put("strPropertyName", propertyName);
			hm.put("strLocName", LocName);
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
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptSlowMovingItemsReport." + type.trim());
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptSlowMovingItemsReport." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	/**
	 * Open Expire Flash Form
	 * 
	 * @param objPropBean
	 * @param result
	 * @param req
	 * @return
	 * @throws JRException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/frmExpiryFlash", method = RequestMethod.GET)
	public ModelAndView funOpenExpiryFlash(@ModelAttribute("command") clsReportBean objPropBean, BindingResult result, HttpServletRequest req) throws JRException {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		ModelAndView objModelView = new ModelAndView("frmExpiryFlash");

		Map<String, String> mapProperty = objGlobalService.funGetPropertyList(clientCode);
		if (mapProperty.isEmpty()) {
			mapProperty.put("", "");
		}
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String locationCode = req.getSession().getAttribute("locationCode").toString();
		objModelView.addObject("listProperty", mapProperty);
		objModelView.addObject("LoggedInProp", propertyCode);
		objModelView.addObject("LoggedInLoc", locationCode);

		HashMap<String, String> mapLocation = (HashMap<String, String>) objGlobalService.funGetLocationList(propertyCode, clientCode);
		if (mapLocation.isEmpty()) {
			mapLocation.put("", "");
		}
		mapLocation = clsGlobalFunctions.funSortByValues(mapLocation);
		objModelView.addObject("listLocation", mapLocation);
		return objModelView;

	}

	/**
	 * Calling Expire Flash Report
	 * 
	 * @param param1
	 * @param req
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/frmExpiryFlashReport", method = RequestMethod.GET)
	private @ResponseBody List<clsExpiryFlashModel> funExpiryFlashReport(@RequestParam(value = "param1") String param1, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String[] spParam1 = param1.split(",");
		String locCode = spParam1[1];
		String fDate = spParam1[3];
		String tDate = spParam1[4];
		String ExpfDate = spParam1[5];
		String ExptDate = spParam1[6];
		String prodCode = spParam1[7];
		String suppCode = spParam1[8];
		String fromDate = objGlobal.funGetDate("yyyy-MM-dd", fDate);
		String toDate = objGlobal.funGetDate("yyyy-MM-dd", tDate);
		ExpfDate = objGlobal.funGetDate("yyyy-MM-dd", ExpfDate);
		ExptDate = objGlobal.funGetDate("yyyy-MM-dd", ExptDate);
		String hql = "select a.strBatchCode,a.strManuBatchCode,a.strProdCode,b.strProdName,a.strTransCode,d.strPName, DATE_FORMAT(c.dtGRNDate,'%d-%m-%Y'),DATE_FORMAT(a.dtExpiryDate,'%d-%m-%Y'),a.dblQty,a.dblPendingQty" + " from clsBatchHdModel a,clsProductMasterModel b,clsGRNHdModel c,clsSupplierMasterModel d " + " where a.strProdCode=b.strProdCode and a.strTransCode=c.strGRNCode"
				+ " and c.strSuppCode=d.strPCode and a.strTransType='GRN' and a.dblPendingQty > 0 and c.dtGRNDate between '" + fromDate + "' and '" + toDate + "'" + " and  a.dtExpiryDate between '" + ExpfDate + "' and '" + ExptDate + "' and c.strLocCode='" + locCode + "' ";
		if (!prodCode.equalsIgnoreCase("ALL")) {
			hql = hql + " and a.strProdCode='" + prodCode + "'";

		}
		if (!suppCode.equalsIgnoreCase("ALL")) {
			hql = hql + " and c.strSuppCode='" + suppCode + "' ";
		}
		hql = hql + " and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "'";

		List<clsExpiryFlashModel> ExpiryList = new ArrayList<clsExpiryFlashModel>();
		List list = objGlobalFunctionsService.funGetList(hql, "hql");
		for (int i = 0; i < list.size(); i++) {
			Object[] ob = (Object[]) list.get(i);

			clsExpiryFlashModel expModel = new clsExpiryFlashModel();
			expModel.setStrBatchCode(ob[0].toString());
			expModel.setStrManuBatchCode(ob[1].toString());
			expModel.setStrProdCode(ob[2].toString());
			expModel.setStrProdName(ob[3].toString());
			expModel.setStrTransCode(ob[4].toString());
			expModel.setStrPartyName(ob[5].toString());
			expModel.setDtGRNDate(ob[6].toString());
			expModel.setDtExpiryDate(ob[7].toString());
			expModel.setDblQty(Double.valueOf(ob[8].toString()));
			expModel.setDblPendingQty(Double.valueOf(ob[9].toString()));
			ExpiryList.add(expModel);
		}
		return ExpiryList;
	}

	/**
	 * Export to Excel Expire Flash
	 * 
	 * @param param1
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ExportExpiryFlashReport", method = RequestMethod.GET)
	public ModelAndView downloadExcel(@RequestParam(value = "param1") String param1, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String[] spParam1 = param1.split(",");
		String locCode = spParam1[1];
		String fDate = spParam1[3];
		String tDate = spParam1[4];
		String ExpfDate = spParam1[5];
		String ExptDate = spParam1[6];
		String fromDate = objGlobal.funGetDate("yyyy-MM-dd", fDate);
		String toDate = objGlobal.funGetDate("yyyy-MM-dd", tDate);
		ExpfDate = objGlobal.funGetDate("yyyy-MM-dd", ExpfDate);
		ExptDate = objGlobal.funGetDate("yyyy-MM-dd", ExptDate);

		List ExpStock = new ArrayList();
		String[] ExcelHeader = { "Product Name", "Batch Code", "Supplier Name", "GRN Date", "GRN Code", "GRN Qty", "Balance Qty", "Expiry Date" };
		ExpStock.add(ExcelHeader);

		String hql = "select a.strBatchCode,a.strManuBatchCode,a.strProdCode,b.strProdName,a.strTransCode,d.strPName, DATE_FORMAT(c.dtGRNDate,'%d-%m-%Y'),DATE_FORMAT(a.dtExpiryDate,'%d-%m-%Y'),a.dblQty,a.dblPendingQty" + " from clsBatchHdModel a,clsProductMasterModel b,clsGRNHdModel c,clsSupplierMasterModel d " + " where a.strProdCode=b.strProdCode and a.strTransCode=c.strGRNCode"
				+ " and c.strSuppCode=d.strPCode and a.strTransType='GRN' and a.dblPendingQty > 0 and c.dtGRNDate between '" + fromDate + "' and '" + toDate + "'" + " and  a.dtExpiryDate between '" + ExpfDate + "' and '" + ExptDate + "' and c.strLocCode='" + locCode + "' and a.strClientCode='" + clientCode + "' " + "and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode
				+ "' and c.strClientCode='" + clientCode + "'";

		List list = objGlobalFunctionsService.funGetList(hql, "hql");

		List listExpFlashModel = new ArrayList();
		for (int cnt = 0; cnt < list.size(); cnt++) {
			Object[] arrObj = (Object[]) list.get(cnt);
			List DataList = new ArrayList<>();
			DataList.add(arrObj[3].toString());
			DataList.add(arrObj[0].toString());
			DataList.add(arrObj[5].toString());
			DataList.add(arrObj[6].toString());
			DataList.add(arrObj[4].toString());
			DataList.add(Double.parseDouble(arrObj[8].toString()));
			DataList.add(Double.parseDouble(arrObj[9].toString()));
			DataList.add(arrObj[7].toString());
			listExpFlashModel.add(DataList);
		}
		ExpStock.add(listExpFlashModel);
		return new ModelAndView("excelView", "stocklist", ExpStock);
	}

	/**
	 * Open Audit Flash Form
	 * 
	 * @param objPropBean
	 * @param result
	 * @param req
	 * @return
	 * @throws JRException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/frmAuditFlash", method = RequestMethod.GET)
	public ModelAndView funOpenAuditFlash(@ModelAttribute("command") clsReportBean objPropBean, BindingResult result, HttpServletRequest req) throws JRException {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		ModelAndView objModelView = new ModelAndView("frmAuditFlash");

		Map<String, String> mapProperty = objGlobalService.funGetPropertyList(clientCode);
		if (mapProperty.isEmpty()) {
			mapProperty.put("", "");
		}
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String locationCode = req.getSession().getAttribute("locationCode").toString();
		objModelView.addObject("listProperty", mapProperty);
		objModelView.addObject("LoggedInProp", propertyCode);
		objModelView.addObject("LoggedInLoc", locationCode);

		HashMap<String, String> mapLocation = (HashMap<String, String>) objGlobalService.funGetLocationList(propertyCode, clientCode);
		if (!mapLocation.isEmpty()) {
			mapLocation = clsGlobalFunctions.funSortByValues(mapLocation);
			mapLocation.put("ALL", "ALL");
		} else {
			mapLocation.put("", "");
		}

		objModelView.addObject("listLocation", mapLocation);

		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);

		List<clsTreeMasterModel> Auditlist = objSetupMasterService.funGetAuditForms();

		Map<String, String> mapAuditForms = new HashMap<String, String>();
		mapAuditForms.put("Invoice", "Invoice");
		if (objSetup != null && null != objSetup.getStrAuditFrom()) {
			String strForms = objSetup.getStrAuditFrom();
			String trmp[] = strForms.split(",");
			for (clsTreeMasterModel FormList : Auditlist) {
				for (int i = 0; i < trmp.length; i++) {
					if (FormList.getStrFormName().equalsIgnoreCase(trmp[i].toString())) {
						mapAuditForms.put(FormList.getStrFormName(), FormList.getStrFormDesc());
					}
				}

			}
		}
		objModelView.addObject("listAuditName", mapAuditForms);
		return objModelView;

	}

	/**
	 * Loading Data in Expire Flash
	 * 
	 * @param param1
	 * @param fDate
	 * @param tDate
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/frmAuditFlashReport", method = RequestMethod.GET)
	private @ResponseBody List funAuditFlashReport(@RequestParam(value = "param1") String param1, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String[] spParam1 = param1.split(",");

		String locCode = spParam1[0];
		String strTransType = spParam1[2];
		String strReportType = spParam1[3];
		String fromDate = objGlobal.funGetDate("yyyy-MM-dd", fDate);
		String toDate = objGlobal.funGetDate("yyyy-MM-dd", tDate);
		String sql = "";

		if (strReportType.equalsIgnoreCase("Edited")) {
			switch (strTransType) {
			case "GRN(Good Receiving Note)":

				sql = "select left(a.strTransCode,12),DATE_FORMAT(a.dtTransDate,'%d-%m-%Y'),c.strPCode," + " c.strPName,d.strLocCode,d.strLocName,a.strBillNo,a.strPayMode,a.dblSubTotal," + " a.strUserCreated,a.strUserModified,DATE_FORMAT(a.dtDateCreated,'%d-%m-%Y')," + " DATE_FORMAT(a.dtLastModified,'%d-%m-%Y')" + " FROM tblaudithd a"
						+ " left outer join tblpartymaster c on a.strSuppCode=c.strPCode and c.strClientCode='" + clientCode + "' " + " left outer join tbllocationmaster d on a.strLocCode=d.strLocCode and d.strClientCode='" + clientCode + "' " + " where  a.strTransType='" + strTransType + "' " + " and date(a.dtTransDate) between '" + fromDate + "' and '" + toDate + "' "
						+ " and a.strTransMode='Edit'  and a.strClientCode='" + clientCode + "'  ";

				if (!locCode.equalsIgnoreCase("ALL")) {
					sql = sql + " and  a.strLocCode='" + locCode + "' ";
				}
				sql = sql + " group by left(a.strTransCode,12)";

				break;

			case "Opening Stock":

				sql = "select left(a.strtranscode,12) as strOpStkCode,a.strLocCode,c.strLocName,a.strUserCreated,a.strUserModified," + "DATE_FORMAT(a.dtDateCreated,'%d-%m-%Y') as dtDateCreated," + "DATE_FORMAT(a.dtLastModified,'%d-%m-%Y') as dtLastModified  FROM tblaudithd a " + "left outer join tbllocationmaster c on a.strLocCode=c.strLocCode and c.strClientCode='" + clientCode + "'  "
						+ "where a.strTransType='" + strTransType + "' and date(a.dtTransDate) between '" + fromDate + "' and '" + toDate + "' " + "and a.strTransMode='Edit' and a.strClientCode='" + clientCode + "' ";
				if (!locCode.equalsIgnoreCase("ALL")) {
					sql = sql + " and  a.strLocCode='" + locCode + "' ";
				}
				sql = sql + "group by left(a.strtranscode,12) ";
				break;

			case "Physical Stk Posting":

				sql = "select left(a.strtranscode,12) AS strPSCode,DATE_FORMAT(a.dtTransDate,'%d-%m-%Y') as dtPSDate," + " a.strLocCode,c.strLocName,a.strCode," + " a.strUserCreated,a.strUserModified, DATE_FORMAT(a.dtDateCreated,'%d-%m-%Y') as dtDateCreated," + " DATE_FORMAT(a.dtLastModified,'%d-%m-%Y') as dtLastModified " + " FROM tblaudithd a"
						+ " left outer join tbllocationmaster c on a.strLocCode=c.strLocCode and c.strClientCode='" + clientCode + "' " + " where a.strTransType='" + strTransType + "' and date(a.dtTransDate) between '" + fromDate + "' and '" + toDate + "' " + " and a.strTransMode='Edit' and a.strClientCode='" + clientCode + "' ";

				if (!locCode.equalsIgnoreCase("ALL")) {
					sql = sql + " and  a.strLocCode='" + locCode + "' ";
				}
				sql = sql + " group by left(a.strtranscode,12)";

				break;

			case "Production Order":

				break;

			case "Purchase Indent":

				sql = "select left(a.strtranscode,12) as strPICode,a.dtTransDate,c.strLocCode,c.strLocName," + "a.strNarration,a.dblTotalAmt,a.strUserCreated,a.strUserModified, DATE_FORMAT(a.dtDateCreated,'%d-%m-%Y') as dtDateCreated, " + "DATE_FORMAT(a.dtLastModified,'%d-%m-%Y') as dtLastModified FROM tblaudithd a "
						+ "left outer join tbllocationmaster c on a.strLocCode=c.strLocCode and c.strClientCode='" + clientCode + "' " + "where  a.strTransType='" + strTransType + "' " + "and date(a.dtTransDate) between '" + fromDate + "' and '" + toDate + "' and a.strTransMode='Edit' " + "and a.strClientCode='" + clientCode + "' ";

				if (!locCode.equalsIgnoreCase("ALL")) {
					sql = sql + " and  a.strLocCode='" + locCode + "' ";
				}
				sql = sql + " group by left(a.strtranscode,12)";

				break;

			case "Purchase Order":

				sql = "select  left(a.strtranscode,12) as strPOCode,DATE_FORMAT(a.dtTransDate,'%d-%m-%Y'),c.strPCode as strSuupCode," + " c.strPName,a.strAgainst,a.dblTotalAmt,a.strUserCreated,a.strUserModified," + " DATE_FORMAT(a.dtDateCreated,'%d-%m-%Y') as dtDateCreated," + " DATE_FORMAT(a.dtLastModified,'%d-%m-%Y') as dtLastModified FROM tblaudithd a  "
						+ " left outer join tblpartymaster c on a.strSuppCode=c.strPCode and c.strClientCode='" + clientCode + "' " + " where a.strTransType='" + strTransType + "' " + " and date(a.dtTransDate) between '" + fromDate + "' and '" + toDate + "'  and a.strTransMode='Edit' " + " and a.strClientCode='" + clientCode + "'  " + " group by left(a.strtranscode,12)";

				break;

			case "Purchase Return":

				sql = "select left(a.strtranscode,12),DATE_FORMAT(a.dtTransDate,'%d-%m-%Y') as dtTransDate,a.strAgainst,a.strNarration," + " c.strPCode as strSuppCode,c.strPName,a.dblTotalAmt,a.strUserCreated,a.strUserModified, " + " DATE_FORMAT(a.dtDateCreated,'%d-%m-%Y') as dtDateCreated," + " DATE_FORMAT(a.dtLastModified,'%d-%m-%Y') as dtLastModified  FROM tblaudithd a"
						+ " left outer join tblpartymaster c on a.strSuppCode=c.strPCode and c.strClientCode='" + clientCode + "' " + " where  a.strTransType='" + strTransType + "' " + "	and date(a.dtTransDate) between '" + fromDate + "' and '" + toDate + "'  and a.strTransMode='Edit' " + "	and a.strClientCode='" + clientCode + "'  " + " group by left(a.strtranscode,12)";

				break;

			case "Stock Adjustment":

				sql = "select left(a.strtranscode,12) as strSACode,DATE_FORMAT(a.dtTransDate,'%d-%m-%Y') as dtSADate,a.strLocCode," + " c.strLocName,a.strNarration,a.dblTotalAmt,a.strUserCreated,a.strUserModified, " + " DATE_FORMAT(a.dtDateCreated,'%d-%m-%Y'),DATE_FORMAT(a.dtLastModified,'%d-%m-%Y')  " + " FROM tblaudithd a  "
						+ " left outer join tbllocationmaster c on a.strLocCode=c.strLocCode and c.strClientCode='" + clientCode + "' " + " where  a.strTransType='" + strTransType + "' and a.strTransMode='Edit'" + " and date(a.dtTransDate) between '" + fromDate + "' and '" + toDate + "' and a.strClientCode='" + clientCode + "' ";

				if (!locCode.equalsIgnoreCase("ALL")) {
					sql = sql + " and  a.strLocCode='" + locCode + "' ";
				}
				sql = sql + " group by left(a.strtranscode,12)";
				break;

			case "Stock Transfer":

				sql = "select left(a.strtranscode,12) as strSTCode,DATE_FORMAT(a.dtTransDate,'%d-%m-%Y') as dtTransDate," + " c.strLocName as fromLocation,d.strLocName as toLocation," + " a.strAgainst,  a.strMaterialIssue,a.strNarration,a.strUserCreated,a.strUserModified," + " DATE_FORMAT(a.dtDateCreated,'%d-%m-%Y') as dtDateCreated,DATE_FORMAT(a.dtLastModified,'%d-%m-%Y')  as dtLastModified"
						+ " FROM tblaudithd a" + " left outer join tbllocationmaster c on a.strLocBy=c.strLocCode " + " and c.strClientCode='" + clientCode + "'  left outer join tbllocationmaster d on a.strLocOn=d.strLocCode" + " and d.strClientCode='" + clientCode + "'  " + "	where a.strTransType='" + strTransType + "' " + "	and date(a.dtTransDate) between '" + fromDate + "' and '" + toDate
						+ "'  and a.strTransMode='Edit' " + "	and a.strClientCode='" + clientCode + "' ";

				if (!locCode.equalsIgnoreCase("ALL")) {
					sql = sql + " and  a.strLocBy='" + locCode + "' ";
				}
				sql = sql + " group by left(a.strtranscode,12)";

				break;

			case "Work Order":

				break;

			case "Material Return":

				sql = "select left(a.strtranscode,12) as strMRetCode,DATE_FORMAT(a.dtTransDate,'%d-%m-%Y') as dtMRetDate," + " c.strLocName as fromLocation,d.strLocName as toLocation,a.strAgainst,  a.strCode," + " a.strNarration,a.strUserCreated,a.strUserModified,DATE_FORMAT(a.dtDateCreated,'%d-%m-%Y') as dtDateCreated,"
						+ " DATE_FORMAT(a.dtLastModified,'%d-%m-%Y') as dtLastModified FROM tblaudithd a" + " left outer join tbllocationmaster c on a.strLocBy=c.strLocCode and c.strClientCode='" + clientCode + "' " + " left outer join tbllocationmaster d on a.strLocOn =d.strLocCode and d.strClientCode='" + clientCode + "' " + " where  a.strTransType='" + strTransType + "' "
						+ " and date(a.dtTransDate) between '" + fromDate + "' and '" + toDate + "' and a.strTransMode='Edit'" + "	and a.strClientCode='" + clientCode + "' ";
				if (!locCode.equalsIgnoreCase("ALL")) {
					sql = sql + " and  a.strLocBy='" + locCode + "' ";
				}

				sql = sql + " group by left(a.strtranscode,12)";
				break;

			case "Material Issue Slip":

				sql = "select left(a.strtranscode,12) as strMISCode,DATE_FORMAT(a.dtTransDate,'%d-%m-%Y') as dtMISDate,c.strLocName as fromLocation," + " d.strLocName as toLocation,a.strAgainst,a.strCode,  a.dblTotalAmt,a.strNarration," + " a.strUserCreated,a.strUserModified, DATE_FORMAT(a.dtDateCreated,'%d-%m-%Y') as dtDateCreated,"
						+ " DATE_FORMAT(a.dtLastModified,'%d-%m-%Y')  as dtLastModified" + " FROM tblaudithd a  " + " left outer join tbllocationmaster c on a.strLocBy=c.strLocCode and c.strClientCode='" + clientCode + "' " + " left outer join tbllocationmaster d on a.strLocOn=d.strLocCode and d.strClientCode='" + clientCode + "' " + " where  a.strTransType='" + strTransType + "' "
						+ " and date(a.dtTransDate) between '" + fromDate + "' and '" + toDate + "'" + " and a.strClientCode='" + clientCode + "' ";
				if (!locCode.equalsIgnoreCase("ALL")) {
					sql = sql + " and  a.strLocBy='" + locCode + "' ";
				}
				sql = sql + " group by left(a.strtranscode,12)";
				break;

			case "Material Requisition":

				sql = "select left(a.strtranscode,12) as strReqCode,DATE_FORMAT(a.dtTransDate,'%d-%m-%Y') as dtReqDate,c.strLocName as fromLocation," + " d.strLocName as toLocation,a.strAgainst,  a.dblSubTotal,a.strNarration,a.strUserCreated," + " a.strUserModified, DATE_FORMAT(a.dtDateCreated,'%d-%m-%Y') as dtDateCreated,DATE_FORMAT(a.dtLastModified,'%d-%m-%Y') as dtLastModified"
						+ " FROM tblaudithd a" + " left outer join tbllocationmaster c on a.strLocBy=c.strLocCode and c.strClientCode='" + clientCode + "' " + " left outer join tbllocationmaster d on a.strLocOn=d.strLocCode and d.strClientCode='" + clientCode + "' " + "	where a.strTransType='Material Requisition'" + "	and date(a.dtTransDate) between '" + fromDate + "' and '" + toDate
						+ "'  and a.strClientCode='" + clientCode + "' ";
				if (!locCode.equalsIgnoreCase("ALL")) {
					sql = sql + " and  a.strLocBy='" + locCode + "' ";
				}
				sql = sql + " group by left(a.strtranscode,12)";
				break;

			case "Invoice":
				sql = "select left(a.strtranscode,12) as strInvCode,DATE_FORMAT(a.dtTransDate,'%d-%m-%Y') as dteInvDate,a.strLocCode," + " c.strLocName,a.strNarration,a.dblTotalAmt,a.strUserCreated,a.strUserModified, " + " DATE_FORMAT(a.dtDateCreated,'%d-%m-%Y'),DATE_FORMAT(a.dtLastModified,'%d-%m-%Y')  " + " FROM tblaudithd a  "
						+ " left outer join tbllocationmaster c on a.strLocCode=c.strLocCode and c.strClientCode='" + clientCode + "' " + " where  a.strTransType='" + strTransType + "'" + " and date(a.dtTransDate) between '" + fromDate + "' and '" + toDate + "' and a.strClientCode='" + clientCode + "' ";

				if (!locCode.equalsIgnoreCase("ALL")) {
					sql = sql + " and  a.strLocCode='" + locCode + "' ";
				}
				sql = sql + " group by left(a.strtranscode,12)";
				break;

			}
		}
		if (strReportType.equalsIgnoreCase("Deleted")) {
			sql = "select a.strTransCode,DATE_FORMAT(a.dtDeleteDate,'%d-%m-%Y') as dtDeleteDate ,a.strUserCode,b.strReasonName,a.strNarration " + " from tbldeletedetails a left outer join tblreasonmaster b on a.strReasonCode=b.strReasonCode and b.strClientCode='" + clientCode + "' " + " where TRIM(a.strFormName)='" + strTransType.trim() + "' and a.strClientCode='" + clientCode + "' ";
		}
		List list = new ArrayList();
		try {
			list = objGlobalFunctionsService.funGetList(sql, "sql");
			if (list == null) {
				list = new ArrayList();
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Open Expire Report Slip
	 * 
	 * @param resp
	 * @param req
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/funOpenAuditRptSlip", method = RequestMethod.GET)
	private void funCallReportOnSubmit(HttpServletResponse resp, HttpServletRequest req) {
		String strTransCode = req.getParameter("strTransCode").toString();
		String TransType = req.getParameter("TransType").toString();
		String TransMode = "";
		if (req.getParameter("TransMode") != null) {
			TransMode = req.getParameter("TransMode").toString();
		}
		String type = "pdf";
		try {
			String sql = "";
			if (TransMode.equalsIgnoreCase("Edited")) {
				sql = "select strTransCode from tblaudithd a where left(a.strTransCode,12)='" + strTransCode + "' and a.strTransType='" + TransType + "'";
			}
			if (TransMode.equalsIgnoreCase("Deleted")) {
				sql = "select strTransCode from tbldeletedetails a where a.strTransCode='" + strTransCode + "' and a.strFormName='" + TransType + "'";
			}
			List list = objGlobalFunctionsService.funGetList(sql, "sql");
			if (list != null && !list.isEmpty()) {
				List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				for (int i = 0; i < list.size(); i++) {
					JasperPrint p = funGetReport(list.get(i).toString(), TransType, resp, req);
					jprintlist.add(p);
				}

				/**
				 * Exporting Report in various Format
				 */
				if (type.trim().equalsIgnoreCase("pdf")) {

					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					resp.setHeader("Content-Disposition", "inline;filename=" + "rptAuditSlip." + type.trim());
					exporter.exportReport();

					servletOutputStream.flush();
					servletOutputStream.close();
				} else if (type.trim().equalsIgnoreCase("xls")) {
					JRExporter exporterXLS = new JRXlsExporter();
					exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
					resp.setHeader("Content-Disposition", "attachment;filename=" + "rptAuditSlip." + type.trim());
					exporterXLS.exportReport();
					resp.setContentType("application/xlsx");

				}
			}
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public JasperPrint funGetReport(String TransCode, String TransType, HttpServletResponse resp, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		Connection con = objGlobal.funGetConnection(req);
		JasperPrint p = null;
		try {
			String dtTransDate = "", strFromLoc = "";
			String strToLoc = "";
			String strAgainst = "";
			String strReqCode = "";
			String strOrderBy = "";
			String strNarration = "";
			String strUserCreated = "";
			String strLocationName = "";
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			if (TransType.equalsIgnoreCase("Material Requisition")) {
				String sql = "select a.strTransCode,DATE_FORMAT(a.dtTransDate,'%d-%m-%Y') as dtTransDate ,b.strLocName as FromLocation,c.strLocName as ToLocation," + "a.strAgainst, a.strNarration from tblaudithd a " + " left outer join tbllocationmaster b on a.strLocBy=b.strLocCode and b.strClientCode='" + clientCode + "'  "
						+ " left outer join tbllocationmaster c on a.strLocOn=c.strLocCode and c.strClientCode='" + clientCode + "'  " + " where a.strTransCode='" + TransCode + "' and a.strClientCode='" + clientCode + "'  and a.strTransType='" + TransType + "' ";
				List list = objGlobalFunctionsService.funGetList(sql, "sql");

				if (!list.isEmpty()) {
					Object[] arrObj = (Object[]) list.get(0);
					dtTransDate = arrObj[1].toString();
					strFromLoc = arrObj[2].toString();
					strToLoc = arrObj[3].toString();
					strAgainst = arrObj[4].toString();
					strNarration = arrObj[5].toString();

					String reportName = servletContext.getRealPath("/WEB-INF/reports/rptRequisitionSlip.jrxml");
					String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
					sql = "select a.strProdCode,p.strProdName,a.dblQty,a.dblUnitPrice,a.dblTotalPrice,a.strRemarks," + " p.strIssueUOM,p.strBinNo " + " from tblauditdtl a, tblproductmaster p where a.strProdCode=p.strProdCode  " + " and a.strTransCode='" + TransCode + "' and p.strClientCode='" + clientCode + "' ";
					JasperDesign jd = JRXmlLoader.load(reportName);
					JRDesignQuery subQuery = new JRDesignQuery();
					subQuery.setText(sql);
					Map<String, JRDataset> datasetMap = jd.getDatasetMap();
					JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsReqslp");
					subDataset.setQuery(subQuery);
					JasperReport jr = JasperCompileManager.compileReport(jd);

					HashMap hm = new HashMap();
					hm.put("strCompanyName", companyName);
					hm.put("strUserCode", userCode);
					hm.put("strImagePath", imagePath);
					hm.put("strReqCode", TransCode);
					hm.put("strAddr1", objSetup.getStrAdd1());
					hm.put("strAddr2", objSetup.getStrAdd2());
					hm.put("strCity", objSetup.getStrCity());
					hm.put("strState", objSetup.getStrState());
					hm.put("strCountry", objSetup.getStrCountry());
					hm.put("strPin", objSetup.getStrPin());
					hm.put("dtReqDate", dtTransDate);
					hm.put("strAgainst", strAgainst);
					hm.put("strFromLoc", strFromLoc);
					hm.put("strToLoc", strToLoc);
					hm.put("strNarration", strNarration);
					p = JasperFillManager.fillReport(jr, hm, con);
				}

			}
			if (TransType.trim().equalsIgnoreCase("Material Issue Slip")) {
				String sqlMisHd = "select a.strTransCode as strMISCode ,DATE_FORMAT(a.dtTransDate,'%d-%m-%Y') as dtMISDate,c.strLocName as strFromLoc ," + " d.strLocName as strToLoc, a.strAgainst as strAgainst,ifnull(a.strCode,'') as strReqCode, " + " ifnull(b.strUserCreated,'') as strOrderBy,a.strNarration as strNarration,a.strUserModified "
						+ " from tblaudithd a left outer join tblreqhd b on a.strCode=b.strReqCode  and b.strClientCode='" + clientCode + "' " + " left outer join  tbllocationmaster c on a.strLocBy=c.strLocCode and c.strClientCode='" + clientCode + "' " + " left outer join tbllocationmaster d on a.strLocOn=d.strLocCode and d.strClientCode='" + clientCode + "' " + " where a.strTransCode='"
						+ TransCode + "' and a.strTransType='" + TransType + "'  and a.strClientCode='" + clientCode + "' ";
				List list = objGlobalFunctionsService.funGetList(sqlMisHd, "sql");

				if (!list.isEmpty()) {
					Object[] arrObj = (Object[]) list.get(0);
					dtTransDate = arrObj[1].toString();
					strFromLoc = arrObj[2].toString();
					strToLoc = arrObj[3].toString();
					strAgainst = arrObj[4].toString();
					strReqCode = arrObj[5].toString();
					strOrderBy = arrObj[6].toString();
					strNarration = arrObj[7].toString();
					strUserCreated = arrObj[8].toString();
					String reportName = servletContext.getRealPath("/WEB-INF/reports/rptMaterialIssueSlip.jrxml");
					String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
					String sql = "select a.strProdCode,p.strProdName,a.dblQty,a.dblUnitPrice,a.dblTotalPrice, " + " a.strRemarks,p.strIssueUOM,p.strBinNo " + " from tblauditdtl a, tblproductmaster p " + " where a.strProdCode=p.strProdCode and a.strTransCode='" + TransCode + "' and p.strClientCode='" + clientCode + "' ";
					// getting multi copy of small data in table in Detail thats
					// why we add in subDataset
					JasperDesign jd = JRXmlLoader.load(reportName);
					JRDesignQuery subQuery = new JRDesignQuery();
					subQuery.setText(sql);
					Map<String, JRDataset> datasetMap = jd.getDatasetMap();
					JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsMISdtl");
					subDataset.setQuery(subQuery);
					JasperReport jr = JasperCompileManager.compileReport(jd);

					HashMap hm = new HashMap();
					hm.put("strCompanyName", companyName);
					hm.put("strUserCode", userCode);
					hm.put("strImagePath", imagePath);
					hm.put("strMISCode", TransCode);
					hm.put("dtMISDate", dtTransDate);
					hm.put("strAgainst", strAgainst);
					hm.put("strFromLoc", strFromLoc);
					hm.put("strToLoc", strToLoc);
					hm.put("strNarration", strNarration);
					hm.put("strAddr1", objSetup.getStrAdd1());
					hm.put("strAddr2", objSetup.getStrAdd2());
					hm.put("strCity", objSetup.getStrCity());
					hm.put("strState", objSetup.getStrState());
					hm.put("strCountry", objSetup.getStrCountry());
					hm.put("strPin", objSetup.getStrPin());
					hm.put("strShowValue", objSetup.getStrShowValMISSlip());
					hm.put("strReqCode", strReqCode);
					hm.put("strOrderBy", strOrderBy);
					p = JasperFillManager.fillReport(jr, hm, con);
				}
			}
			if (TransType.equalsIgnoreCase("Purchase Indent")) {
				String sql = "select a.strTransCode,DATE_FORMAT(a.dtTransDate,'%d-%m-%Y') as dtTransDate, b.strLocName " + " from tblaudithd a " + " left outer join tbllocationmaster b on a.strLocCode=b.strLocCode and b.strClientCode='" + clientCode + "' " + " where a.strTransCode='" + TransCode + "' and  a.strTransType='" + TransType + "'  and b.strClientCode='" + clientCode + "' ";
				List list = objGlobalFunctionsService.funGetList(sql, "sql");

				if (!list.isEmpty()) {
					Object[] arrObj = (Object[]) list.get(0);
					dtTransDate = arrObj[1].toString();
					strLocationName = arrObj[2].toString();

					sql = "SELECT  a.strProdCode,p.strProdName,ifnull(a.dblReOrderQty,0) as dblReOrderLevel, " + " a.dblQty, a.strRemarks as strPurpose,date(a.dtReqDate) as dtReqDate ,a.dblCStock as strInStock," + " ifnull(a.strAgainst,'') as strAgainst " + " from tblauditdtl a " + " inner join tblproductmaster p ON a.strProdCode = p.strProdCode and p.strClientCode='" + clientCode + "' "
							+ " where a.strTransCode='" + TransCode + "' and a.strClientCode='" + clientCode + "'";

					String reportName = servletContext.getRealPath("/WEB-INF/reports/rptPurchaseIndentSlip.jrxml");
					String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
					JasperDesign jd = JRXmlLoader.load(reportName);
					JRDesignQuery subQuery = new JRDesignQuery();
					subQuery.setText(sql);
					Map<String, JRDataset> datasetMap = jd.getDatasetMap();
					JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsPIdtl");
					subDataset.setQuery(subQuery);
					JasperReport jr = JasperCompileManager.compileReport(jd);
					HashMap hm = new HashMap();
					hm.put("strCompanyName", companyName);
					hm.put("strUserCode", userCode);
					hm.put("strImagePath", imagePath);
					hm.put("strPICode", TransCode);
					hm.put("strPIDate", dtTransDate);
					hm.put("strLocName", strLocationName);
					hm.put("strAddr1", objSetup.getStrAdd1());
					hm.put("strAddr2", objSetup.getStrAdd2());
					hm.put("strCity", objSetup.getStrCity());
					hm.put("strState", objSetup.getStrState());
					hm.put("strCountry", objSetup.getStrCountry());
					hm.put("strPin", objSetup.getStrPin());
					p = JasperFillManager.fillReport(jr, hm, con);
				}
			}
			if (TransType.equalsIgnoreCase("Purchase Order")) {

				String reportName=servletContext.getRealPath("/WEB-INF/reports/rptPurchaseOrderSlipForAll.jrxml");
				if(objSetup.getStrPOSlipFormat().equalsIgnoreCase("Format 1"))
				{
					reportName = servletContext.getRealPath("/WEB-INF/reports/rptPurchaseOrderSlip.jrxml");
					if (clientCode.equals("226.001")) {
						reportName = servletContext.getRealPath("/WEB-INF/reports/rptPurchaseOrderSlip2.jrxml");
					}
				}
				else if(objSetup.getStrPOSlipFormat().equalsIgnoreCase("Format 2"))
				{
					reportName = servletContext.getRealPath("/WEB-INF/reports/rptPurchaseOrderSlipWithoutAmtRateAndDisc.jrxml");
				}
				else if(objSetup.getStrPOSlipFormat().equalsIgnoreCase("Format 3")){
					reportName = servletContext.getRealPath("/WEB-INF/reports/rptPurchaseOrderSlipForAll.jrxml");
				}
				
				String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
				String sql = "SELECT po.strTransCode as strPOCode,date(po.dtTransDate) as dtPODate,po.strSuppCode,po.strAgainst,po.strCode as strSOCode ,po.dblTotalAmt as dblFinalAmt, " + " po.strVAddress1,po.strVAddress2,po.strVCity,po.strVState,po.strVCountry,ifnull(po.strVPin,'') as strVPin,po.strSAddress1, "
						+ " po.strSAddress2,po.strSCity,po.strSState,po.strSCountry,po.strSPin,po.strYourRef,po.strPerRef,po.strEOE, " + " po.strCode,date(po.dtDelDate) as dtDelDate,po.dblExtra,po.strFinalAmtInWord as AmtInWords,po.dblDiscount," + " po.strPayMode,po.strCurrency,po.strAmedment,po.strAmntNO,stredit,po.strUserAmed,date(po.dtPayDate) as dtPayDate, "
						+ " po.dblConversion,po.dtLastModified,po.strAuthorise,po.dblTaxAmt,s.strPName,s.strContact,s.strPhone,s.strMobile, " + "	s.strFax, s.strMAdd1, s.strMAdd2,s.strMCity, s.strMPin,s.strMState,s.strMCountry,u.strUserName,u.strSignatureImg " + " ,((po.dblDiscount*100)/po.dblTotalAmt ) as dblDisPer "
						+ "	FROM tblaudithd AS po  left outer JOIN  tblpartymaster AS s ON po.strSuppCode = s.strPCode " + "	and s.strClientCode='" + clientCode + "'  left outer join  tbluserhd u on po.strUserModified=u.strUserCode and " + "	u.strClientCode='" + clientCode + "'  left outer join tblcurrencymaster c on po.strCurrency=c.strShortName and " + " c.strClientCode='" + clientCode
						+ "' WHERE (po.strTransCode = '" + TransCode + "' and po.strTransType='" + TransType + "'  and po.strClientCode='" + clientCode + "' ) ";

				JasperDesign jd = JRXmlLoader.load(reportName);
				JRDesignQuery newQuery = new JRDesignQuery();
				newQuery.setText(sql);
				jd.setQuery(newQuery);
				String sql2 = "SELECT  1 slno, a.strProdCode,c.strProdName as strProdName, c.strUOM, a.dblQty as dblOrdQty, " + " a.dblUnitPrice as dblPrice, a.dbldiscount,a.dblQty*a.dblUnitPrice-a.dbldiscount dblamt,  c.strPartNo," + " a.dblWeight,c.strCalAmtOn, a.strProdChar,c.strSpecification,a.strProcessCode,d.strProcessName," + " a.strRemarks,b.strEOE,a.strUpdate FROM  tblauditdtl a"
						+ " INNER JOIN tblaudithd b ON a.strTransCode = b.strTransCode and b.strClientCode='" + clientCode + "' " + " INNER JOIN tblproductmaster c ON a.strProdCode = c.strProdCode and c.strClientCode='" + clientCode + "' " + " left outer JOIN tblprocessmaster d ON a.strProcessCode = d.strProcessCode and d.strClientCode='" + clientCode + "' " + " where a.strTransCode='"
						+ TransCode + "' and a.strClientCode='" + clientCode + "' ";
				JRDesignQuery subQuery = new JRDesignQuery();
				subQuery.setText(sql2);
				Map<String, JRDataset> datasetMap = jd.getDatasetMap();
				JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsPODtl");
				subDataset.setQuery(subQuery);

				String temp[] = TransCode.split("-");

				String sql3 = "select a.strTCName,if(b.strTCDesc='null','',b.strTCDesc) as strTCDesc  from tbltctransdtl b,tbltcmaster a" + " where b.strTCCode=a.strTCCode and  b.strTransCode='" + temp[0] + "'" + " and b.strTransType='" + TransType + "'  and  b.strClientCode='" + clientCode + "'  and a.strClientCode='" + clientCode + "' ";
				JRDesignQuery subQuery1 = new JRDesignQuery();
				subQuery1.setText(sql3);
				JRDesignDataset subDataset1 = (JRDesignDataset) datasetMap.get("dsTC");
				subDataset1.setQuery(subQuery1);
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
				if (objSetup.getStrFax().equals("null")) {
					hm.put("strFax", "");
				} else {
					hm.put("strFax", objSetup.getStrFax());
				}
				hm.put("strPhoneNo", objSetup.getStrPhone());
				hm.put("strEmailAddress", objSetup.getStrEmail());
				hm.put("strWebSite", objSetup.getStrWebsite());
				p = JasperFillManager.fillReport(jr, hm, con);
			}
			if (TransType.equalsIgnoreCase("GRN(Good Receiving Note)")) {
				String reportName = servletContext.getRealPath("/WEB-INF/reports/rptGrnDtlSlip.jrxml");
				String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
				String sql = "SELECT g.strTransCode as strGRNCode, date(g.dtTransDate) as dtGRNDate, g.strSuppCode, g.strAgainst, g.strPONo, g.strBillNo, " + " date(g.dtBillDate) as dtBillDate,  date(g.dtDueDate) as dtDueDate, g.strPayMode, g.dblSubTotal, " + " g.dblDisRate, g.dblDiscount as dblDisAmt, g.dblTaxAmt, g.dblExtra, g.dblTotalAmt as dblTotal,  g.strNarration, g.strLocCode, "
						+ " s.strPCode, s.strPName, s.strBAdd1, s.strBAdd2, s.strBCity, s.strBPin,  s.strBState, s.strBCountry, " + " g.strNo,g.strRefNo, date(g.dtRefDate) as dtRefDate,g.dblLessAmt,dblTaxAmt,'0.0' as dblDisRate,'' as strNarration,'' as strVehNo " + " FROM tblaudithd AS g " + " INNER JOIN tblpartymaster AS s ON g.strSuppCode = s.strPCode and s.strClientCode='" + clientCode + "' "
						+ "  WHERE g.strTransCode = '" + TransCode + "' and g.strTransType='" + TransType + "'  and g.strClientCode='" + clientCode + "' ";
				JasperDesign jd = JRXmlLoader.load(reportName);
				JRDesignQuery newQuery = new JRDesignQuery();
				newQuery.setText(sql);
				jd.setQuery(newQuery);

				String sql2 = "select g.strTransCode as strGRNCode,g.strProdCode,p.strProdName,p.strReceivedUOM,g.dblQty,g.dblRejected,g.dblDiscount, " + " g.strTaxType,g.dblTaxableAmt,g.dblTax,dblTaxAmt, g.dblUnitPrice,g.dblWeight,g.strProdChar,g.dblDCQty, " + " g.dblDCWt,g.strRemarks,g.dblQtyRbl,g.strProdChar as strGRNProdChar ,g.dblPOWeight,g.strCode, g.dblRework,g.dblPackForw, "
						+ " g.dblRate,g.dblValue,p.strPartNo from tblauditdtl g,tblproductmaster p  " + " where g.strProdCode=p.strProdCode and g.strTransCode='" + TransCode + "' and g.strClientCode='" + clientCode + "' ";
				JRDesignQuery subQuery = new JRDesignQuery();
				subQuery.setText(sql2);
				Map<String, JRDataset> datasetMap = jd.getDatasetMap();
				JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsGrnDtl");
				subDataset.setQuery(subQuery);

				String Sql3 = "select a.strTaxDesc,a.strTaxAmt from tblauditgrntaxdtl a where strGRNCode='" + TransCode + "' and strClientCode='" + clientCode + "'";

				JRDesignQuery taxQuery = new JRDesignQuery();
				taxQuery.setText(Sql3);
				JRDesignDataset taxDataset = (JRDesignDataset) datasetMap.get("dsTaxDtl");
				taxDataset.setQuery(taxQuery);
				JasperReport jr = JasperCompileManager.compileReport(jd);
				HashMap hm = new HashMap();
				hm.put("strCompanyName", companyName);
				hm.put("strUserCode", userCode);
				hm.put("strImagePath", imagePath);
				hm.put("strGRN Code", TransCode);
				hm.put("strAddr1", objSetup.getStrAdd1());
				hm.put("strAddr2", objSetup.getStrAdd2());
				hm.put("strCity", objSetup.getStrCity());
				hm.put("strState", objSetup.getStrState());
				hm.put("strCountry", objSetup.getStrCountry());
				hm.put("strPin", objSetup.getStrPin());
				p = JasperFillManager.fillReport(jr, hm, con);
			}
			if (TransType.equalsIgnoreCase("Purchase Return")) {
				String reportName = servletContext.getRealPath("/WEB-INF/reports/rptPurchaseReturnSlip.jrxml");
				String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

				String sql = "select a.strTransCode as strPRCode,a.dtTransDate as dtPRDate,b.strLocName,c.strPName," + " ifnull(a.strAgainst,'') as strAgainst, ifnull(a.strGRNCode,'') as strGRNCode,a.strNarration, " + " a.strMInBy,a.strTimeInOut,a.strVehNo, e.strProdCode,e.strProdName,d.strUOM as UOM,d.dblQty, "
						+ " d.dblUnitPrice,d.dblDiscount,  d.dblTotalPrice,ifnull(d.dblTax,0) as dblTax,ifnull(d.dblTaxAmt,0) as dblTaxAmt, " + " ifnull(d.dblTax,'') as dblTaxPercentage,ifnull(d.dblTaxableAmt,'') as dblTaxableAmt, " + " a.dblDiscount as dblDisAmt,a.dblDisRate,a.dblExtra,a.dblSubTotal,ifnull(a.dblTaxAmt,0) as dblTaxAmt," + "	ifnull(a.dblTotalAmt,0) as dblTotal"
						+ "	from tblaudithd a,tbllocationmaster b,tblpartymaster c,tblauditdtl d, tblproductmaster e " + "	where d.strProdCode=e.strProdCode and a.strLocCode=b.strLocCode and a.strSuppCode=c.strPCode " + "	and a.strTransCode='" + TransCode + "' and  a.strTransType='" + TransType + "'  and a.strTransCode=d.strTransCode and a.strClientCode='" + clientCode + "' "
						+ " and a.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "' and d.strClientCode='" + clientCode + "' ";

				JasperDesign jd = JRXmlLoader.load(reportName);
				JRDesignQuery newQuery = new JRDesignQuery();
				newQuery.setText(sql);
				jd.setQuery(newQuery);
				JasperReport jr = JasperCompileManager.compileReport(jd);

				HashMap hm = new HashMap();
				hm.put("strCompanyName", companyName);
				hm.put("strUserCode", userCode.toUpperCase());
				hm.put("strImagePath", imagePath);
				hm.put("strAddr1", objSetup.getStrAdd1());
				hm.put("strAddr2", objSetup.getStrAdd2());
				hm.put("strCity", objSetup.getStrCity());
				hm.put("strState", objSetup.getStrState());
				hm.put("strCountry", objSetup.getStrCountry());
				hm.put("strPin", objSetup.getStrPin());
				hm.put("strPhoneNo", objSetup.getStrPhone());
				hm.put("strEmailAddress", objSetup.getStrEmail());
				hm.put("strWebSite", objSetup.getStrWebsite());
				p = JasperFillManager.fillReport(jr, hm, con);
			}
			if (TransType.equalsIgnoreCase("Material Return")) {
				String reportName = servletContext.getRealPath("/WEB-INF/reports/rptMaterialReturnDetail.jrxml");
				String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

				String sqlDtlQuery = "select a.strTransCode as strMRetCode,a.strProdCode,e.strProdName,a.dblQty,a.strRemarks,date(b.dtTransDate) as dtMRetDate," + " c.strLocName as LocFrom,d.strLocName as ToLoc,b.strCode as strMISCode,b.strAgainst,b.strAuthorise " + " from tblauditdtl a,tblaudithd b,tbllocationmaster c ,tbllocationmaster d ,tblproductmaster e "
						+ " where a.strTransCode=b.strTransCode and b.strLocBy =c.strLocCode and b.strLocOn=d.strLocCode " + " and b.strTransCode='" + TransCode + "' and e.strProdCode=a.strProdCode and b.strTransType='" + TransType + "'  " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "' and d.strClientCode='"
						+ clientCode + "' " + " and e.strClientCode='" + clientCode + "' ";

				String sqlHDQuery = "";
				JasperDesign jd = JRXmlLoader.load(reportName);
				JRDesignQuery subQuery = new JRDesignQuery();
				subQuery.setText(sqlDtlQuery);
				Map<String, JRDataset> datasetMap = jd.getDatasetMap();
				JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsMatReturn");
				subDataset.setQuery(subQuery);
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

				p = JasperFillManager.fillReport(jr, hm, con);
			}
			if (TransType.equalsIgnoreCase("Opening Stock")) {
				String sql = "select a.strTransCode,date_format(a.dtTransDate, '%d-%m-%Y') ,b.strLocName " + " from tblaudithd a,tbllocationmaster b " + " where a.strLocCode=b.strLocCode and a.strTransCode='" + TransCode + "' and a.strTransType='" + TransType + "'  and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + clientCode + "' ";

				List list = objGlobalFunctionsService.funGetList(sql, "sql");
				Object[] ob = (Object[]) list.get(0);
				String strOpStkCode = ob[0].toString();
				String dtOpeningStkdate = ob[1].toString();
				String LocName = ob[2].toString();

				String reportName = servletContext.getRealPath("/WEB-INF/reports/rptOpeningStockSlip.jrxml");
				String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

				String sqlHDQuery = "select d.strGName,c.strSGName,a.strProdCode,b.strProdName,a.strUOM,a.dblQty,a.dblUnitPrice as dblCostPerUnit , " + " (a.dblQty*a.dblUnitPrice) as Value " + " from tblauditdtl a, tblproductmaster b, tblsubgroupmaster c, tblgroupmaster d " + " where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=d.strGCode " + " and a.strTransCode='"
						+ TransCode + "' and a.strClientCode='" + clientCode + "'" + " and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "' and d.strClientCode='" + clientCode + "'  ";
				JasperDesign jd = JRXmlLoader.load(reportName);
				JRDesignQuery newQuery = new JRDesignQuery();
				newQuery.setText(sqlHDQuery);
				jd.setQuery(newQuery);
				JasperReport jr = JasperCompileManager.compileReport(jd);
				HashMap hm = new HashMap();
				hm.put("strOpStkCode", strOpStkCode);
				hm.put("dtOpeningStkdate", dtOpeningStkdate);
				hm.put("LocName", LocName);
				hm.put("strCompanyName", companyName);
				hm.put("strUserCode", userCode);
				hm.put("strImagePath", imagePath);
				hm.put("strAddr1", objSetup.getStrAdd1());
				hm.put("strAddr2", objSetup.getStrAdd2());
				hm.put("strCity", objSetup.getStrCity());
				hm.put("strState", objSetup.getStrState());
				hm.put("strCountry", objSetup.getStrCountry());
				hm.put("strPin", objSetup.getStrPin());
				p = JasperFillManager.fillReport(jr, hm, con);
			}
			if (TransType.equalsIgnoreCase("Stock Adjustment")) {
				String reportName = servletContext.getRealPath("/WEB-INF/reports/rptStockAdjustmentSlip.jrxml");
				String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

				String sqlHDQuery = "select a.strTransCode as strSACode,a.dtTransDate as dtSADate,a.strLocCode,b.strLocName,a.strNarration,a.strAuthorise " + "  from tblaudithd a,tbllocationmaster b " + "  where a.strLocCode=b.strLocCode and  a.strTransCode='" + TransCode + "' and a.strTransType='" + TransType + "'  " + "  and a.strClientCode='" + clientCode + "' and b.strClientCode='"
						+ clientCode + "' ";

				String sqlDtlQuery = "select a.strProdCode,a.strRemarks as strRemark,b.strProdName, b.strProdType,a.dblQty,b.strUOM,a.strType, " + " a.dblUnitPrice as dblRate,'' as strDisplayQty,(a.dblUnitPrice*a.dblQty) as dblPrice  " + " from tblauditdtl a, tblproductmaster b where a.strProdCode=b.strProdCode " + " and  a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode
						+ "' and a.strTransCode='" + TransCode + "' ";

				JasperDesign jd = JRXmlLoader.load(reportName);
				JRDesignQuery newQuery = new JRDesignQuery();
				newQuery.setText(sqlHDQuery);
				jd.setQuery(newQuery);
				JRDesignQuery subQuery = new JRDesignQuery();
				subQuery.setText(sqlDtlQuery);
				Map<String, JRDataset> datasetMap = jd.getDatasetMap();
				JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsStkDtl");
				subDataset.setQuery(subQuery);
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
				p = JasperFillManager.fillReport(jr, hm, con);
			}
			if (TransType.equalsIgnoreCase("Stock Transfer")) {
				String reportName = servletContext.getRealPath("/WEB-INF/reports/rptStockTransferSlip.jrxml");
				String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

				String sql = "select a.strTransCode as strSTCode,DATE_FORMAT(a.dtTransDate,'%m-%d-%Y') as dtSTDate,a.strLocBy as strFromLocCode, " + " a.strLocOn as strToLocCode,a.strNarration, a.strMaterialIssue as strMaterialIssue ,a.strWOCode ," + " a.strAgainst,b.strLocName as strFromLocName,c.strLocName as strToLocName "
						+ " from tblaudithd a,tbllocationmaster b,tbllocationmaster c where a.strLocBy=b.strLocCode " + " and a.strLocOn=c.strLocCode and a.strTransCode='" + TransCode + "' and  a.strTransType='" + TransType + "'  " + " and a.strClientCode='" + clientCode + "'  and b.strClientCode='" + clientCode + "'  and c.strClientCode='" + clientCode + "' ";

				JasperDesign jd = JRXmlLoader.load(reportName);
				JRDesignQuery newQuery = new JRDesignQuery();
				newQuery.setText(sql);
				jd.setQuery(newQuery);
				String sql2 = "select a.strTransCode as strSTCode,a.strProdCode,b.strProdName,a.dblQty,a.dblWeight,a.dblUnitPrice as dblPrice, " + "  a.strRemarks as strRemark " + "  from tblauditdtl a,tblproductmaster b  where a.strProdCode=b.strProdCode and " + "  a.strTransCode='" + TransCode + "'  and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' ";

				JRDesignQuery subQuery = new JRDesignQuery();
				subQuery.setText(sql2);
				Map<String, JRDataset> datasetMap = jd.getDatasetMap();
				JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsStkDtl");
				subDataset.setQuery(subQuery);

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
				hm.put("strFax", objSetup.getStrFax());
				hm.put("strPhoneNo", objSetup.getStrPhone());
				hm.put("strEmailAddress", objSetup.getStrEmail());
				hm.put("strWebSite", objSetup.getStrWebsite());
				p = JasperFillManager.fillReport(jr, hm, con);
			}
			if (TransType.equalsIgnoreCase("Physical Stk Posting")) {
				String reportName = servletContext.getRealPath("/WEB-INF/reports/rptPhysicalStockPosting.jrxml");
				String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

				String sqlHDQuery = "select a.strTransCode as strPSCode,a.dtTransDate as dtPSDate,a.strLocCode,b.strLocName " + " from tblaudithd a,tbllocationmaster b " + " where a.strclientcode='" + clientCode + "' and b.strclientCode='" + clientCode + "' " + " and a.strLocCode=b.strLocCode and a.strTransCode='" + TransCode + "' and a.strTransType='" + TransType + "'  ";

				String sqlDtlQuery = "select a.strProdCode,b.strProdName,b.strUOM,a.dblUnitPrice as dblCostRM,b.strWtUOM,a.dblCStock,a.dblQty as dblPStock, " + " a.dblQty-a.dblCStock as variance,(a.dblQty-a.dblCStock)*a.dblUnitPrice as AjdValue  " + " from tblauditdtl a,tblproductmaster b where a.strProdCode=b.strProdCode " + " and a.strTransCode='" + TransCode + "' and a.strClientCode='"
						+ clientCode + "' and  b.strClientCode='" + clientCode + "' ";

				JasperDesign jd = JRXmlLoader.load(reportName);
				JRDesignQuery newQuery = new JRDesignQuery();
				newQuery.setText(sqlHDQuery);
				jd.setQuery(newQuery);
				JRDesignQuery subQuery = new JRDesignQuery();
				subQuery.setText(sqlDtlQuery);
				Map<String, JRDataset> datasetMap = jd.getDatasetMap();
				JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsPhyStkPost");
				subDataset.setQuery(subQuery);
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
				hm.put("strFax", objSetup.getStrFax());
				hm.put("strPhoneNo", objSetup.getStrPhone());
				hm.put("strEmailAddress", objSetup.getStrEmail());
				hm.put("strWebSite", objSetup.getStrWebsite());
				p = JasperFillManager.fillReport(jr, hm, con);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return p;

		}
	}

	/**
	 * Open Consolidated Receipt Value Misc Supplier Report Form
	 * 
	 * @param req
	 * @return
	 * @throws JRException
	 */
	@RequestMapping(value = "/frmConsReceiptValMiscSuppReqReport", method = RequestMethod.GET)
	public ModelAndView funOpenConsReceiptValMiscSuppReqReport(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmConsReceiptValMiscSuppReqReport_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmConsReceiptValMiscSuppReqReport", "command", new clsReportBean());
		}

	}

	/**
	 * Calling Consolidated Receipt Value Misc Supplier Report
	 * 
	 * @param objBean
	 * @param request
	 * @param resp
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/rptConsReceiptValMiscSuppReqReport", method = RequestMethod.POST)
	public void funConsReceiptValMiscSuppReqReport(@ModelAttribute("command") clsReportBean objBean, HttpServletRequest request, HttpServletResponse resp) {
		try {
			objGlobal = new clsGlobalFunctions();
			String companyName = request.getSession().getAttribute("companyName").toString();
			String userCode = request.getSession().getAttribute("usercode").toString();
			String propertyCode = request.getSession().getAttribute("propertyCode").toString();
			String clientCode = request.getSession().getAttribute("clientCode").toString();
			String fromDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtFromDate());
			String toDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtToDate());
			String fromlocation = objBean.getStrFromLocCode();

			String type = objBean.getStrDocType();
			Connection con = objGlobal.funGetConnection(request);

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String hql = "select a.strSuppCode, b.strPName,sum(a.dblSubTotal) as SubTotal,sum(a.dblTaxAmt) TaxAmt, sum(dblTotal) Value " + " from tblgrnhd a, tblpartymaster b " + " where a.strSuppCode = b.strPCode " + " and date(a.dtGRNDate) >= '" + fromDate + "' and date(a.dtGRNDate) <= '" + toDate + "' " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "'";
			String strlocCode = "";
			String LocCodes[] = fromlocation.split(",");
			for (int i = 0; i < LocCodes.length; i++) {
				if (strlocCode.length() > 0) {
					strlocCode = strlocCode + " or a.strLocCode='" + LocCodes[i] + "' ";
				} else {
					strlocCode = "a.strLocCode='" + LocCodes[i] + "' ";
				}
			}
			if (objBean.getStrFromLocCode() != "") {
				hql = hql + " and " + "(" + strlocCode + ")";
			}

			hql = hql + " Group By a.strSuppCode, b.strPName order by b.strPName ";

			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptConsReceiptValMiscSuppReqReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText("Select 1 From Dual");
			jd.setQuery(newQuery);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(hql);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("Dataset");
			subDataset.setQuery(subQuery);

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
			hm.put("strFax", objSetup.getStrFax());
			hm.put("strPhoneNo", objSetup.getStrPhone());
			hm.put("strEmailAddress", objSetup.getStrEmail());
			hm.put("strWebSite", objSetup.getStrWebsite());
			hm.put("FromDate", objBean.getDtFromDate());
			hm.put("ToDate", objBean.getDtToDate());
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
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptConsReceiptValAllSuppReqReport." + type.trim());
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptConsReceiptValAllSuppReqReport." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Open Consolidated Receipt Value StoreWise BreakkUP Report Form
	 * 
	 * @param req
	 * @return
	 * @throws JRException
	 */
	@RequestMapping(value = "/frmConsReceiptValStoreWiseBreskUPReport", method = RequestMethod.GET)
	public ModelAndView funOpenConsReceiptValStoreWiseBreskUPReport(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmConsReceiptValStoreWiseBreskUPReport_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmConsReceiptValStoreWiseBreskUPReport", "command", new clsReportBean());
		}

	}

	/**
	 * Calling Open Consolidated Receipt Value StoreWise BreakkUP Report
	 * 
	 * @param objBean
	 * @param request
	 * @param resp
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/rptConsReceiptValStoreWiseBreskUPReport", method = RequestMethod.POST)
	public void funConsReceiptValStoreWiseBreskUPReport(@ModelAttribute("command") clsReportBean objBean, HttpServletRequest request, HttpServletResponse resp) {
		try {
			objGlobal = new clsGlobalFunctions();
			String companyName = request.getSession().getAttribute("companyName").toString();
			String userCode = request.getSession().getAttribute("usercode").toString();
			String propertyCode = request.getSession().getAttribute("propertyCode").toString();
			String clientCode = request.getSession().getAttribute("clientCode").toString();
			String fromDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtFromDate());
			String toDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtToDate());
			String fromlocation = objBean.getStrFromLocCode();
			String type = objBean.getStrDocType();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			Connection con = objGlobal.funGetConnection(request);

			String hql = "select a.strLocCode, c.strLocName, a.strSuppCode, b.strPName,sum(a.dblSubTotal) as SubTotal,sum(a.dblTaxAmt) TaxAmt ,sum(dblTotal) Value " + " from tblgrnhd a, tblpartymaster b, tbllocationmaster c " + " where a.strSuppCode = b.strPCode and a.strLocCode = c.strLocCode " + " and date(a.dtGRNDate) >= '" + fromDate + "' and date(a.dtGRNDate) <= '" + toDate + "' "
					+ " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "'";
			String strlocCode = "";
			String LocCodes[] = fromlocation.split(",");
			for (int i = 0; i < LocCodes.length; i++) {
				if (strlocCode.length() > 0) {
					strlocCode = strlocCode + " or a.strLocCode='" + LocCodes[i] + "' ";
				} else {
					strlocCode = "a.strLocCode='" + LocCodes[i] + "' ";
				}
			}
			if (objBean.getStrFromLocCode() != "") {
				hql = hql + " and " + "(" + strlocCode + ")";
			}

			hql = hql + " Group By a.strLocCode, a.strSuppCode, b.strPName order by a.strLocCode,b.strPName ";

			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptConsReceiptValStoreWiseBreskUPReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(hql);
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
			hm.put("strFax", objSetup.getStrFax());
			hm.put("strPhoneNo", objSetup.getStrPhone());
			hm.put("strEmailAddress", objSetup.getStrEmail());
			hm.put("strWebSite", objSetup.getStrWebsite());
			hm.put("FromDate", objBean.getDtFromDate());
			hm.put("ToDate", objBean.getDtToDate());
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
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptConsReceiptValStoreWiseBreskUPReport." + type.trim());
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptConsReceiptValStoreWiseBreskUPReport." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Open Group Consumption Form
	 * 
	 * @param req
	 * @return
	 * @throws JRException
	 */
	@RequestMapping(value = "/frmGroupConsumption", method = RequestMethod.GET)
	public ModelAndView funOpenGroupConsumptionReport(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		ModelAndView mv = null;
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		Map<String, String> mapGroup = objGrpMasterService.funGetGroups(clientCode);
		mapGroup.put("ALL", "ALL");

		if ("2".equalsIgnoreCase(urlHits)) {
			mv = new ModelAndView("frmGroupConsumption_1", "command", new clsReportBean());
		} else {
			mv = new ModelAndView("frmGroupConsumption", "command", new clsReportBean());
		}
		mv.addObject("listgroup", mapGroup);

		return mv;
	}

	/**
	 * Calling Group Consumption Report
	 * 
	 * @param objBean
	 * @param request
	 * @param resp
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/rptGroupConsumption", method = RequestMethod.GET)
	public void funGroupConsumptionReport(@ModelAttribute("command") clsReportBean objBean, HttpServletRequest request, HttpServletResponse resp) {
		try {
			objGlobal = new clsGlobalFunctions();
			String companyName = request.getSession().getAttribute("companyName").toString();
			String userCode = request.getSession().getAttribute("usercode").toString();
			String propertyCode = request.getSession().getAttribute("propertyCode").toString();
			String clientCode = request.getSession().getAttribute("clientCode").toString();
			String fromDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtFromDate());
			String toDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtToDate());
			String type = objBean.getStrDocType();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			Connection con = objGlobal.funGetConnection(request);

			String tempLoc[] = objBean.getStrFromLocCode().split(",");
			String tempGCodes[] = objBean.getStrGCode().split(",");
			String strLocCodes = "";
			String strGCodes = "";

			for (int i = 0; i < tempLoc.length; i++) {
				if (strLocCodes.length() > 0) {
					strLocCodes = strLocCodes + " or a.strLocFrom='" + tempLoc[i] + "' ";
				} else {
					strLocCodes = " a.strLocFrom='" + tempLoc[i] + "' ";

				}
			}

			for (int i = 0; i < tempGCodes.length; i++) {
				if (strGCodes.length() > 0) {
					strGCodes = strGCodes + " or f.strGCode='" + tempGCodes[i] + "' ";
				} else {
					strGCodes = "f.strGCode='" + tempGCodes[i] + "' ";

				}
			}

			String sql = "select f.strGCode,f.strGName, sum(b.dblQty)*d.dblCostRM as Value " + " from tblmishd a , tblmisdtl b ,tblproductmaster d  " + " left outer join tblsubgroupmaster e on e.strSGCode=d.strSGCode" + "	and e.strClientCode='" + clientCode + "' " + "	left outer join tblgroupmaster f on e.strGCode=f.strGCode" + "	and f.strClientCode='" + clientCode + "' "
					+ "	left outer join tbllocationmaster g on g.strClientCode='" + clientCode + "' " + " where Date(a.dtMISDate ) between '" + fromDate + "' and '" + toDate + "'" + " and a.strClientCode='" + clientCode + "'  and b.strClientCode='" + clientCode + "'" + " and a.strMISCode=b.strMISCode and d.strProdCode=b.strProdCode " + " and g.strLocCode=a.strLocTo and d.strClientCode='"
					+ clientCode + "' ";

			if (objBean.getStrFromLocCode() != "") {
				sql = sql + " and (" + strLocCodes + ") ";
			}
			sql = sql + " and (" + strGCodes + ")  GROUP by f.strGCode  order by f.strGCode ";

			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptGroupConsumption.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText("Select 1 from Dual");
			jd.setQuery(newQuery);

			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sql);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsGroupCons");
			subDataset.setQuery(subQuery);

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
			hm.put("strFax", objSetup.getStrFax());
			hm.put("strPhoneNo", objSetup.getStrPhone());
			hm.put("strEmailAddress", objSetup.getStrEmail());
			hm.put("strWebSite", objSetup.getStrWebsite());
			hm.put("FromDate", objBean.getDtFromDate());
			hm.put("ToDate", objBean.getDtToDate());
			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			/**
			 * Exporting Report in various Format
			 */
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptGroupConsumption." + type.trim());
				resp.setContentLength(bytes.length);
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptGroupConsumption." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Open Receipt Issue Consolidated form
	 * 
	 * @param req
	 * @return
	 * @throws JRException
	 */
	@RequestMapping(value = "/frmReceiptIssueConsolidated", method = RequestMethod.GET)
	public ModelAndView funOpenReceiptissueConsolidatedFrom(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		ModelAndView objModelView = null;
		Map<String, String> mapProperty = objGlobalService.funGetPropertyList(clientCode);
		if (mapProperty.isEmpty()) {
			mapProperty.put("", "");
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			objModelView = new ModelAndView("frmReceiptIssueConsolidated_1", "command", new clsReportBean());
		} else {
			objModelView = new ModelAndView("frmReceiptIssueConsolidated", "command", new clsReportBean());
		}

		objModelView.addObject("listProperty", mapProperty);

		return objModelView;
	}

	/**
	 * Calling Receipt Issue Consolidated form
	 * 
	 * @param objBean
	 * @param request
	 * @param resp
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/rptReceiptIssueConsolidated", method = RequestMethod.POST)
	public void funOpenReceiptissueConsolidatedReport(@ModelAttribute("command") clsReportBean objBean, HttpServletRequest request, HttpServletResponse resp) {
		try {
			objGlobal = new clsGlobalFunctions();
			String companyName = request.getSession().getAttribute("companyName").toString();
			String userCode = request.getSession().getAttribute("usercode").toString();
			String propertyCode = request.getSession().getAttribute("propertyCode").toString();
			String clientCode = request.getSession().getAttribute("clientCode").toString();
			String fromDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtFromDate());
			String toDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtToDate());
			String type = objBean.getStrDocType();
			String locCode = objBean.getStrLocationCode();

			String startDate = request.getSession().getAttribute("startDate").toString();
			String[] sp = startDate.split(" ");
			String[] spDate = sp[0].split("/");
			startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];

			String stockableItem = "Y";
			objGlobalFunctions.funInvokeStockFlash(startDate, locCode, fromDate, toDate, clientCode, userCode, stockableItem, request, resp);
			clsLocationMasterModel locModel = objLocationMasterService.funGetObject(locCode, clientCode);
			String strLocName = locModel.getStrLocName();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			Connection con = objGlobal.funGetConnection(request);
			String sql = "select d.strGName,c.strSGName,a.strProdCode,b.strProdName,ifnull(b.strUOM,'') as strUOM,a.dblOpeningStk," + " (a.dblGRN+dblSCGRN+a.dblStkTransIn+a.dblStkAdjIn+a.dblMISIn+a.dblQtyProduced+a.dblMaterialReturnIn) as Rec_Qty, " + " (a.dblGRN+dblSCGRN+a.dblStkTransIn+a.dblStkAdjIn+a.dblMISIn+a.dblQtyProduced+a.dblMaterialReturnIn)*b.dblCostRM as Rec_Value ,"
					+ " (a.dblStkTransOut-a.dblStkAdjOut-a.dblMISOut-a.dblQtyConsumed-a.dblSales-a.dblMaterialReturnOut-a.dblDeliveryNote) as Issue, " + " (a.dblStkTransOut-a.dblStkAdjOut-a.dblMISOut-a.dblQtyConsumed-a.dblSales-a.dblMaterialReturnOut-a.dblDeliveryNote)*b.dblCostRM as Issue_Value ," + " b.dblCostRM,a.dblClosingStk"
					+ " from tblcurrentstock a,tblproductmaster b,tblsubgroupmaster c,tblgroupmaster d" + " where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=d.strGCode and a.strLocCode='" + locCode + "'" + " and a.strClientCode='" + clientCode + "' and a.strUserCode='" + userCode + "' ";
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptReceiptIssueConsolidated.jrxml");
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
			hm.put("strFax", objSetup.getStrFax());
			hm.put("strPhoneNo", objSetup.getStrPhone());
			hm.put("strEmailAddress", objSetup.getStrEmail());
			hm.put("strWebSite", objSetup.getStrWebsite());
			hm.put("FromDate", objBean.getDtFromDate());
			hm.put("ToDate", objBean.getDtToDate());
			hm.put("Location", strLocName);
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
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptReceiptIssueConsolidated." + type.trim());
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptReceiptIssueConsolidated." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Open Item Variance Price Flash Form
	 * 
	 * @param req
	 * @return
	 * @throws JRException
	 */
	@RequestMapping(value = "/frmItemVariancePriceFlash", method = RequestMethod.GET)
	public ModelAndView funOpenItemVarianceReport(HttpServletRequest req) throws JRException {
		ModelAndView mv = new ModelAndView("frmItemVariancePriceFlash", "command", new clsReportBean());
		return mv;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/rptItemsVarReport", method = RequestMethod.POST)
	public void funOpenItemsVarReport(@ModelAttribute("command") clsReportBean objBean, HttpServletRequest request, HttpServletResponse resp) {
		try {
			objGlobal = new clsGlobalFunctions();
			String companyName = request.getSession().getAttribute("companyName").toString();
			String userCode = request.getSession().getAttribute("usercode").toString();
			String propertyCode = request.getSession().getAttribute("propertyCode").toString();
			String clientCode = request.getSession().getAttribute("clientCode").toString();
			String fromDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtFromDate());
			String toDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtToDate());
			String type = objBean.getStrDocType();
			String ProdCode = objBean.getStrProdCode();
			String strSuppCode = objBean.getStrSuppCode();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			Connection con = objGlobal.funGetConnection(request);

			String sql = "select b.strProdName,b.strUOM,b.dblCostRM,a.dtLastDate as GrnDate,c.strPName,a.strDefault,a.dblLastCost" + " from tblprodsuppmaster a, tblproductmaster b, tblpartymaster c " + " where a.strProdCode = b.strProdCode and a.strSuppCode = c.strPCode ";
			if (ProdCode != null && ProdCode.length() > 0) {
				sql = sql + " and a.strProdCode='" + ProdCode + "' ";
			}
			if (strSuppCode != null && strSuppCode.length() > 0) {
				sql = sql + " and  a.strSuppCode='" + strSuppCode + "' ";
			}
			sql = sql + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "' " + " and a.strProdCode IN(select strProdCode from (select strProdCode,dblLastCost, count(*) from tblprodsuppmaster" + " where strClientCode='" + clientCode + "' group by strProdCode, dblLastCost) a Group by strProdCode "
					+ " having count(*) > 1 order by strProdCode) " + " and a.dtLastDate >= '" + fromDate + "' and a.dtLastDate <= '" + toDate + "' " + " order by b.strProdName, a.dtLastDate";

			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptItemsVarianceReport.jrxml");
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
			hm.put("strFax", objSetup.getStrFax());
			hm.put("strPhoneNo", objSetup.getStrPhone());
			hm.put("strEmailAddress", objSetup.getStrEmail());
			hm.put("strWebSite", objSetup.getStrWebsite());
			hm.put("FromDate", objBean.getDtFromDate());
			hm.put("ToDate", objBean.getDtToDate());
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
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptItemsVarianceReport." + type.trim());
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptItemsVarianceReport." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Open Food Cost Report Form
	 * 
	 * @param req
	 * @return
	 * @throws JRException
	 */
	@RequestMapping(value = "/frmFoodCost", method = RequestMethod.GET)
	public ModelAndView funOpenFoodCostForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		ModelAndView mv = null;
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		Map<String, String> mapGroup = objGrpMasterService.funGetGroups(clientCode);
		mapGroup.put("ALL", "ALL");
		Map<String, String> mapSubGroup = new HashMap<String, String>();
		mapSubGroup.put("ALL", "ALL");

		if ("2".equalsIgnoreCase(urlHits)) {
			mv = new ModelAndView("frmFoodCost_1", "command", new clsReportBean());
		} else {
			mv = new ModelAndView("frmFoodCost", "command", new clsReportBean());
		}
		mv.addObject("listgroup", mapGroup);
		mv.addObject("listsubGroup", mapSubGroup);

		return mv;
	}
	/**
	 * Calling Food Cost Report
	 * 
	 * @param objBean
	 * @param resp
	 * @param req
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/rptFoodCost", method = RequestMethod.POST)
	private void funOpenRptFoodCost(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		try {
			objGlobal = new clsGlobalFunctions();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String fromDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtFromDate());
			String toDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtToDate());
			String type = objBean.getStrDocType();
			String tempLoc[] = objBean.getStrLocationCode().split(",");
			String tempSGCodes[] = objBean.getStrSGCode().split(",");
			String strLocCodes = "";
			String strGCodes = "";

			for (int i = 0; i < tempLoc.length; i++) {
				if (strLocCodes.length() > 0) {
					strLocCodes = strLocCodes + " or  h.strLocCode= '" + tempLoc[i] + "' ";
				} else {
					strLocCodes = "  h.strLocCode = '" + tempLoc[i] + "' ";

				}
			}

			for (int i = 0; i < tempSGCodes.length; i++) {
				if (strGCodes.length() > 0) {
					strGCodes = strGCodes + " or e.strSGCode='" + tempSGCodes[i] + "' ";
				} else {
					strGCodes = "e.strSGCode='" + tempSGCodes[i] + "' ";

				}
			}

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			Connection con = objGlobal.funGetConnection(req);

		
//			String sql = " SELECT c.strProdCode, c.strProdName, c.strReceivedUOM, SUM(a.dblQuantity) AS dblQuantity,sum(a.dblAmount) AS dblAmount, "
//					+ " sum(a.dblAmount-a.dblPercentAmt) SaleValue, IFNULL(d.RecipeCost,c.dblCostRM) CostPrice, "
//					+ " sum(a.dblQuantity)* IFNULL(d.RecipeCost,c.dblCostRM) CostValue, "
//					+ " sum(a.dblAmount-a.dblPercentAmt) - sum(a.dblQuantity)* IFNULL(d.RecipeCost,c.dblCostRM) Profit, "
//					+ "IFNULL(sum(a.dblQuantity)* IFNULL(d.RecipeCost,c.dblCostRM)/sum(a.dblAmount-a.dblPercentAmt),0.00)*100 AS FoodCostPer, "
//					+ " IFNULL(e.strSGName,'') AS strSGName, IFNULL(f.strGName,'') AS strGName, a.dblRate, h.strLocName  "
//					+ " FROM tblpossalesdtl a LEFT OUTER JOIN tblstockadjustmenthd b ON a.strSACode = b.strSACode and a.strLocationCode=b.strLocCode "
//					+ "LEFT OUTER JOIN tblproductmaster c ON a.strWSItemCode = c.strProdCode "
//					+" LEFT OUTER JOIN ( SELECT strParentCode, SUM((c.dblReceiveConversion/c.dblIssueConversion/c.dblRecipeConversion)*c.dblCostRM*b.dblQty) RecipeCost "
//					+" FROM tblbommasterhd a,tblbommasterdtl b, tblproductmaster c WHERE a.strBOMCode=b.strBOMCode AND b.strChildCode=c.strProdCode "
//					+" GROUP BY strParentCode) d ON c.strProdCode = d.strParentCode LEFT OUTER "
//					+" JOIN tblsubgroupmaster e ON e.strSGCode=c.strSGCode ";
//		
//		sql = sql + " AND (" + strGCodes + ") ";
//					
//		sql = sql + " LEFT OUTER JOIN tblgroupmaster f ON f.strGCode=e.strGCode " + " LEFT OUTER JOIN tbllocationmaster h ON b.strLocCode = h.strLocCode ";
//
//		sql = sql + " WHERE c.strProdCode<>'null' AND DATE(b.dtSADate) BETWEEN " + "'" + fromDate + "'" + " and " + "'" + toDate + "' ";
//				
//				if (objBean.getStrLocationCode() != "") {
//					sql = sql + " and (" + strLocCodes + ") ";
//				}
//				
//		sql = sql + " AND (a.dblQuantity* IFNULL(d.RecipeCost,c.dblCostRM))>0 ";
//		
//		sql = sql + " GROUP BY c.strProdCode ORDER BY h.strLocName ASC, f.strGName ASC, e.strSGName ASC, c.strProdName ASC " ;

			
			String sql = " SELECT c.strProdCode, c.strProdName, c.strReceivedUOM, SUM(a.dblQuantity) AS dblQuantity,sum(a.dblAmount) AS dblAmount, "
					+ " sum(a.dblAmount-a.dblPercentAmt) SaleValue, IFNULL(d.RecipeCost,c.dblCostRM) CostPrice, "
					+ " sum(a.dblQuantity)* IFNULL(d.RecipeCost,c.dblCostRM) CostValue, "
					+ " sum(a.dblAmount-a.dblPercentAmt) - sum(a.dblQuantity)* IFNULL(d.RecipeCost,c.dblCostRM) Profit, "
					+ " IFNULL(sum(a.dblQuantity)* IFNULL(d.RecipeCost,c.dblCostRM)/sum(a.dblAmount-a.dblPercentAmt),0.00)*100 AS FoodCostPer, "
					+ " IFNULL(e.strSGName,'') AS strSGName, IFNULL(f.strGName,'') AS strGName, a.dblRate, h.strLocName , "
					+ " c.dblUnitPrice AS dblStdPrice,  c.dblUnitPrice*SUM(a.dblQuantity) AS dblStdPriceValue, "
					+ " ( "
					+ " CASE WHEN (((c.dblUnitPrice*SUM(a.dblQuantity)) - SUM(a.dblQuantity)* IFNULL(d.RecipeCost,c.dblCostRM))>0 "
					+ " )  THEN "
					+ " ((c.dblUnitPrice*SUM(a.dblQuantity)) - SUM(a.dblQuantity)* IFNULL(d.RecipeCost,c.dblCostRM))"
					+ "  ELSE 0.00 END "
					+ " ) AS dblVarience  "
					+ " FROM tblpossalesdtl a LEFT OUTER JOIN tblstockadjustmenthd b ON a.strSACode = b.strSACode and a.strLocationCode=b.strLocCode "
					+ " LEFT OUTER JOIN tblproductmaster c ON a.strWSItemCode = c.strProdCode "
					+"  LEFT OUTER JOIN ( SELECT strParentCode, SUM((c.dblReceiveConversion/c.dblIssueConversion/c.dblRecipeConversion)*c.dblCostRM*b.dblQty) RecipeCost "
					+"  FROM tblbommasterhd a,tblbommasterdtl b, tblproductmaster c WHERE a.strBOMCode=b.strBOMCode AND b.strChildCode=c.strProdCode "
					+" GROUP BY strParentCode) d ON c.strProdCode = d.strParentCode LEFT OUTER "
					+" JOIN tblsubgroupmaster e ON e.strSGCode=c.strSGCode ";
		
			sql = sql + " AND (" + strGCodes + ") ";
						
			sql = sql + " LEFT OUTER JOIN tblgroupmaster f ON f.strGCode=e.strGCode " + " LEFT OUTER JOIN tbllocationmaster h ON b.strLocCode = h.strLocCode ";
	
			sql = sql + " WHERE c.strProdCode<>'null' AND DATE(b.dtSADate) BETWEEN " + "'" + fromDate + "'" + " and " + "'" + toDate + "' ";
					
					if (objBean.getStrLocationCode() != "") {
						sql = sql + " and (" + strLocCodes + ") ";
					}
					
			sql = sql + " AND (a.dblQuantity* IFNULL(d.RecipeCost,c.dblCostRM))>0 ";
			
			sql = sql + " GROUP BY c.strProdCode ORDER BY h.strLocName ASC, f.strGName ASC, e.strSGName ASC, c.strProdName ASC " ;
		
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptFoodCost.jrxml");
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
			hm.put("strFax", objSetup.getStrFax());
			hm.put("strPhoneNo", objSetup.getStrPhone());
			hm.put("strEmailAddress", objSetup.getStrEmail());
			hm.put("strWebSite", objSetup.getStrWebsite());
			hm.put("fromDate", objBean.getDtFromDate());
			hm.put("toDate", objBean.getDtToDate());
			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			/**
			 * Exporting Report in various Format
			 */
			if (type.trim().equalsIgnoreCase("pdf")) {
				JRExporter exporterPDF = new JRPdfExporter();
				exporterPDF.setParameter(JRPdfExporterParameter.JASPER_PRINT, p);
				exporterPDF.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setContentType("application/pdf");
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptFoodCost." + type.trim());
				exporterPDF.exportReport();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptFoodCost." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	// Food Costing Summary Report 
	
	@SuppressWarnings({ "rawtypes", "unchecked" } )
	@RequestMapping(value = "/rptFoodCostSummary",  method = RequestMethod.POST)
	public void funGenerateFoodCostSummary(clsReportBean objBean, HttpServletRequest req, HttpServletResponse resp) {
		try {
			objGlobal = new clsGlobalFunctions();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String fromDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtFromDate());
			String toDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtToDate());
			String type = objBean.getStrDocType();
			String tempLoc[] = objBean.getStrLocationCode().split(",");
			String tempSGCodes[] = objBean.getStrSGCode().split(",");
			String strLocCodes = "";
			String strGCodes = "";

			for (int i = 0; i < tempLoc.length; i++) {
				if (strLocCodes.length() > 0) {
					strLocCodes = strLocCodes + " or  h.strLocCode= '" + tempLoc[i] + "' ";
				} else {
					strLocCodes = "  h.strLocCode = '" + tempLoc[i] + "' ";

				}
			}

			for (int i = 0; i < tempSGCodes.length; i++) {
				if (strGCodes.length() > 0) {
					strGCodes = strGCodes + " or e.strSGCode='" + tempSGCodes[i] + "' ";
				} else {
					strGCodes = "e.strSGCode='" + tempSGCodes[i] + "' ";

				}
			}
			
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			Connection con = objGlobal.funGetConnection(req);
			
			String sql = " SELECT e.strSGCode,h.strLocName,f.strGName,e.strSGName, sum(a.dblAmount-a.dblPercentAmt) SaleValue, "
					+ " SUM(a.dblQuantity* IFNULL(d.RecipeCost,c.dblCostRM)) AS CostValue, "
					+ " sum( (a.dblAmount-a.dblPercentAmt) - (a.dblQuantity)* IFNULL(d.RecipeCost,c.dblCostRM)) AS Profit ,"
					+ " SUM(IFNULL((a.dblQuantity)* IFNULL(d.RecipeCost,c.dblCostRM)/((a.dblRate*a.dblQuantity)-a.dblPercentAmt),0.00)*100) AS FoodCostPer "
					+ " FROM tblpossalesdtl a LEFT OUTER JOIN tblstockadjustmenthd b ON a.strSACode = b.strSACode "
					+ " LEFT OUTER JOIN tblproductmaster c ON a.strWSItemCode = c.strProdCode "
					+ " LEFT OUTER JOIN ( SELECT strParentCode, SUM((c.dblReceiveConversion/c.dblIssueConversion/c.dblRecipeConversion)*c.dblCostRM*b.dblQty) RecipeCost "
					+ " FROM tblbommasterhd a,tblbommasterdtl b, tblproductmaster c "
					+ "  WHERE a.strBOMCode=b.strBOMCode AND b.strChildCode=c.strProdCode GROUP BY strParentCode) d ON c.strProdCode = d.strParentCode " 
					+ " LEFT OUTER JOIN tblsubgroupmaster e ON e.strSGCode=c.strSGCode ";
			
				sql = sql + " AND (" + strGCodes + ") ";
							
				sql = sql + " LEFT OUTER JOIN tblgroupmaster f ON f.strGCode=e.strGCode " + " LEFT OUTER JOIN tbllocationmaster h ON b.strLocCode = h.strLocCode ";
				
				if (objBean.getStrLocationCode() != "") {
						sql = sql + " and (" + strLocCodes + ") ";
				}
		
				sql = sql + " WHERE c.strProdCode<>'null' AND DATE(b.dtSADate) BETWEEN " + "'" + fromDate + "'" + " and " + "'" + toDate + "' AND (a.dblQuantity* IFNULL(d.RecipeCost,c.dblCostRM))>0 ";
				
				sql = sql + " GROUP BY e.strSGCode, f.strGCode ORDER BY h.strLocName ASC, f.strGName ASC, e.strSGName ASC " ;
				
		/*String sql = "select z.strSGCode ,z.strLocName,z.strGName,z.strSGName,sum(z.SaleValue) as SaleValue,sum(z.CostValue) as CostValue,sum(z.Profit) as Profit,sum(z.FoodCostPer) as FoodCostPer    from"
				        + " ( SELECT c.strProdCode, c.strProdName, c.strReceivedUOM, a.dblParentQty AS dblQuantity,a.dblRate, a.dblParentQty * a.dblRate SaleValue, " 
						+" IFNULL(d.RecipeCost,c.dblCostRM) CostPrice, a.dblParentQty* IFNULL(d.RecipeCost,c.dblCostRM) CostValue, "
						+ " (a.dblParentQty * a.dblRate) - (a.dblParentQty)* IFNULL(d.RecipeCost,c.dblCostRM) Profit, "
						+ " (a.dblParentQty)* IFNULL(d.RecipeCost,c.dblCostRM)/(a.dblParentQty * a.dblRate)*100 AS FoodCostPer, IFNULL(e.strSGName,'') AS strSGName, IFNULL(f.strGName,'') AS strGName, h.strLocName,e.strSGCode, f.strGCode  " 
						+" FROM tblstockadjustmentdtl a LEFT OUTER JOIN tblstockadjustmenthd b ON a.strSACode = b.strSACode "
						+ "LEFT OUTER JOIN tblproductmaster c ON a.strWSLinkedProdCode = c.strProdCode "
						+" LEFT OUTER JOIN ( SELECT strParentCode, SUM((c.dblReceiveConversion/c.dblIssueConversion/c.dblRecipeConversion)*c.dblCostRM*b.dblQty) RecipeCost "
						+" FROM tblbommasterhd a,tblbommasterdtl b, tblproductmaster c WHERE a.strBOMCode=b.strBOMCode AND b.strChildCode=c.strProdCode "
						+" GROUP BY strParentCode) d ON c.strProdCode = d.strParentCode LEFT OUTER "
						+" JOIN tblsubgroupmaster e ON e.strSGCode=c.strSGCode ";
			sql = sql + " AND (" + strGCodes + ") ";
						
			sql = sql + " LEFT OUTER JOIN tblgroupmaster f ON f.strGCode=e.strGCode " + " LEFT OUTER JOIN tbllocationmaster h ON b.strLocCode = h.strLocCode ";
			
			if (objBean.getStrLocationCode() != "") {
					sql = sql + " and (" + strLocCodes + ") ";
			}

			sql = sql + " WHERE c.strProdCode<>'null' AND DATE(b.dtSADate) BETWEEN " + "'" + fromDate + "'" + " and " + "'" + toDate + "' AND (a.dblParentQty* IFNULL(d.RecipeCost,c.dblCostRM))>0 ";
			
			sql = sql + " GROUP BY c.strProdCode ORDER BY h.strLocName ASC, f.strGName ASC, e.strSGName ASC, c.strProdName ASC " 
						+" )z GROUP BY z.strSGCode "
						+" ORDER BY z.strLocName ASC, z.strGName ASC, z.strSGName ASC ";*/
			
			
				
				
				String reportName = servletContext.getRealPath("/WEB-INF/reports/rptFoodCostSummary.jrxml");
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
				hm.put("strFax", objSetup.getStrFax());
				hm.put("strPhoneNo", objSetup.getStrPhone());
				hm.put("strEmailAddress", objSetup.getStrEmail());
				hm.put("strWebSite", objSetup.getStrWebsite());
				hm.put("fromDate", objBean.getDtFromDate());
				hm.put("toDate", objBean.getDtToDate());
				JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
				/**
				 * Exporting Report in various Format
				 */
				if (type.trim().equalsIgnoreCase("pdf")) {
					JRExporter exporterPDF = new JRPdfExporter();
					exporterPDF.setParameter(JRPdfExporterParameter.JASPER_PRINT, p);
					exporterPDF.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
					resp.setContentType("application/pdf");
					resp.setHeader("Content-Disposition", "inline;filename=" + "rptFoodCost." + type.trim());
					exporterPDF.exportReport();
				} else if (type.trim().equalsIgnoreCase("xls")) {
					JRExporter exporterXLS = new JRXlsExporter();
					exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
					exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
					resp.setHeader("Content-Disposition", "attachment;filename=" + "rptFoodCost." + type.trim());
					exporterXLS.exportReport();
					resp.setContentType("application/xlsx");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		

	}



	// for Getting fast Reports
	@SuppressWarnings("deprecation")
	public void funStockFlash(String startDate, String locCode, String fromDate, String toDate, String clientCode, String userCode, String stockableItem, HttpServletRequest req, HttpServletResponse resp) {
		funDelNInsertStkTempTable(clientCode, userCode, locCode, stockableItem);
		if (!startDate.equals(fromDate)) {
			String tempFromDate = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];
			SimpleDateFormat obj = new SimpleDateFormat("dd-MM-yyyy");
			Date dt1;

			try {
				dt1 = obj.parse(tempFromDate);
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(dt1);
				cal.add(Calendar.DATE, -1);
				String newToDate = (cal.getTime().getYear() + 1900) + "-" + (cal.getTime().getMonth() + 1) + "-" + (cal.getTime().getDate());

				objGlobalFunctions.funProcessStock(locCode, startDate, newToDate, clientCode, userCode, req, resp);

				String sql = "Update tblcurrentstock set dblOpeningStk=dblClosingStk, dblGRN=0, dblSCGRN=0" + ", dblStkTransIn=0, dblStkAdjIn=0, dblMISIn=0, dblMaterialReturnIn=0, dblQtyProduced=0" + ", dblStkTransOut=0, dblStkAdjOut=0, dblMISOut=0, dblQtyConsumed=0, dblSales=0" + ", dblMaterialReturnOut=0, dblDeliveryNote=0, dblPurchaseReturn=0 " + " where strUserCode='" + userCode
						+ "' and strClientCode='" + clientCode + "' ";
				objGlobalFunctionsService.funUpdate(sql, "sql");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		objGlobalFunctions.funProcessStock(locCode, fromDate, toDate, clientCode, userCode, req, resp);
	}

	public void funDelNInsertStkTempTable(String clientCode, String userCode, String locCode, String stockableItem) {
		objGlobalFunctionsService.funDeleteCurrentStock(clientCode, userCode);

		String sql = "insert into tblcurrentstock (strProdCode,strProdName,strLocCode,strClientCode,strUserCode) " + " select a.strProdCode,a.strProdName,'" + locCode + "','" + clientCode + "','" + userCode + "' from tblproductmaster a ";
		if (stockableItem.equals("N")) {
			sql += " and a.strNonStockableItem='Y' ";
		}
		;

		objGlobalFunctionsService.funExcuteQuery(sql);

	}

	// open LossCaluculation Report

	@RequestMapping(value = "/frmLossCalculationReport", method = RequestMethod.GET)
	public ModelAndView funOpenLossCalculationReport(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmLossCalculationReport_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmLossCalculationReport", "command", new clsReportBean());
		}

	}

	// Assign filed function to set data onto form for Report.
	@RequestMapping(value = "/loadLossData", method = RequestMethod.GET)
	public @ResponseBody clsBomHdModel funAssignFieldOfLossCost(@RequestParam("recipeCode") String recipeCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsBomHdModel objBomHd = objRecipeMasterService.funGetObject(recipeCode, clientCode);
		if (null == objBomHd) {
			objBomHd = new clsBomHdModel();
			objBomHd.setStrBOMCode("Invalid Code");
		}

		return objBomHd;
	}

	@RequestMapping(value = "/rptLossCalculationReport", method = RequestMethod.POST)
	private void funOpenRptLossCalculationReport(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String bomCode = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		funCallLossCalculationReport(bomCode, type, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallLossCalculationReport(String bomCode, String type, HttpServletResponse resp, HttpServletRequest req) {
		try {
			objGlobal = new clsGlobalFunctions();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			Connection con = objGlobal.funGetConnection(req);

			String sql = " select b.strParentCode,d.strProdName AS RecipeName,d.dblListPrice,c.strChildCode,e.strProdName AS ProdName," + " e.strUOM, e.dblWeight AS InitialWt,e.dblYieldPer,(100-e.dblYieldPer) AS lossPer, " + " ((100-e.dblYieldPer)*e.dblWeight/100) AS LossWT,e.dblCostRM AS Rate, " + " ((100-e.dblYieldPer)*e.dblCostRM/100) as LossRecipeCost, "
					+ " (((100-e.dblYieldPer)*e.dblCostRM/100)*100/(SELECT SUM(((100-r.dblYieldPer)*r.dblCostRM/100)) " + " FROM tblbommasterhd p,tblbommasterdtl q, tblproductmaster r WHERE ";
			if (!bomCode.equals("") || !bomCode.isEmpty()) {
				sql = sql + " p.strBOMCode='" + bomCode + "' AND ";
			}
			sql = sql + "  p.strBOMCode=q.strBOMCode AND q.strChildCode = r.strProdCode " + " AND p.strParentCode=d.strProdCode AND p.strClientCode='" + clientCode + "' " + " AND p.strClientCode=q.strClientCode AND q.strClientCode=r.strClientCode)) AS eachProdPer " + " from  tblstockadjustmentdtl a,tblbommasterhd b,tblbommasterdtl c ,tblproductmaster d," + " tblproductmaster e "
					+ " where a.strProdCode=b.strParentCode and b.strParentCode=d.strProdCode " + " and b.strBOMCode=c.strBOMCode and c.strChildCode=e.strProdCode  " + " and c.strClientCode=b.strClientCode and b.strClientCode=c.strClientCode  " + " and c.strClientCode=d.strClientCode and c.strClientCode=e.strClientCode " + " and a.strClientCode='" + clientCode + "' ";

			if (!bomCode.equals("") || !bomCode.isEmpty()) {
				sql = sql + " and  b.strBOMCode='" + bomCode + "' group by c.strChildCode  order by b.strParentCode ";
			} else {
				sql = sql + "   group by c.strChildCode  order by b.strParentCode ";
			}

			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptLossCalculationReport.jrxml");
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
			hm.put("strPin", objSetup.getStrPin());

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
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptLossCalculationReport." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/frmRecipeCosting", method = RequestMethod.GET)
	public ModelAndView funOpenProductionOrderSlipForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmRecipeCosting_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmRecipeCosting", "command", new clsReportBean());
		}

	}

	// Assign filed function to set data onto form for Report.
	@RequestMapping(value = "/loadRecipeData", method = RequestMethod.GET)
	public @ResponseBody clsBomHdModel funAssignFields(@RequestParam("recipeCode") String recipeCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsBomHdModel objBomHd = objRecipeMasterService.funGetObject(recipeCode, clientCode);
		if (null == objBomHd) {
			objBomHd = new clsBomHdModel();
			objBomHd.setStrBOMCode("Invalid Code");
		}

		return objBomHd;
	}

	@RequestMapping(value = "/rptRecipeCosting", method = RequestMethod.GET)
	private void funRecipeCostingReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String bomCode = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		String yieldCalculation = objBean.getStrProdType();

		funPrepardRecipeCostingReport(bomCode, type, resp, req, yieldCalculation);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funPrepardRecipeCostingReport(String bomCode, String type, HttpServletResponse resp, HttpServletRequest req, String yieldCalculation) {

		try {

			objGlobal = new clsGlobalFunctions();
			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptRecipeCosting.jrxml");

			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			String sql = "";
			if (!(yieldCalculation == null)) {

				sql = " select a.strParentCode,d.strProdName as RecipeName, c.dblListPrice,b.strChildCode,c.strProdName as ProdName,c.strUOM," + " (b.dblQty/c.dblRecipeConversion) as InitialWt,c.dblYieldPer,(100-c.dblYieldPer) as lossPer,(c.dblYieldPer*(b.dblQty/c.dblRecipeConversion)/100) as finalWT ," + " c.dblCostRM as Rate,(c.dblYieldPer*c.dblCostRM/100) as RecipeCost,"
						+ " ((c.dblYieldPer*c.dblCostRM/100)*100/(select  sum((r.dblYieldPer*r.dblCostRM/100))" + " from tblbommasterhd p,tblbommasterdtl q, tblproductmaster r" + " where ";
				if (!bomCode.equals("") || !bomCode.isEmpty()) {
					sql = sql + " p.strBOMCode='" + bomCode + "' ";
				}
				sql = sql + " and p.strBOMCode=q.strBOMCode " + " and q.strChildCode = r.strProdCode and p.strParentCode=d.strProdCode" + " and p.strClientCode='" + clientCode + "' and p.strClientCode=q.strClientCode" + " and q.strClientCode=r.strClientCode  )) as eachProdPer " + " from tblbommasterhd a,tblbommasterdtl b,tblproductmaster c ,tblproductmaster d " + " where  "
						+ " a.strBOMCode=b.strBOMCode " + " and b.strChildCode = c.strProdCode and a.strParentCode=d.strProdCode  " + " and a.strClientCode='" + clientCode + "' and a.strClientCode=b.strClientCode " + " and b.strClientCode=c.strClientCode  ";

				if (!bomCode.equals("") || !bomCode.isEmpty()) {
					sql = sql + " and  a.strBOMCode='" + bomCode + "' group by b.strChildCode  order by a.strParentCode ";
				} else {
					sql = sql + "   group by b.strChildCode  order by a.strParentCode ";
				}

			} else {
				sql = " SELECT a.strParentCode,d.strProdName AS RecipeName, d.dblListPrice,b.strChildCode,c.strProdName AS ProdName,c.strUOM," + " (b.dblQty/c.dblRecipeConversion) AS InitialWt,c.dblYieldPer,(100-c.dblYieldPer) AS lossPer,(c.dblYieldPer*(b.dblQty/c.dblRecipeConversion)/100) AS finalWT," + " c.dblCostRM AS Rate, ((b.dblQty/c.dblRecipeConversion)*c.dblCostRM) AS RecipeCost,"
						+ " ((c.dblYieldPer*c.dblCostRM/100)*100/( SELECT SUM((r.dblYieldPer*r.dblCostRM/100))" + " FROM tblbommasterhd p,tblbommasterdtl q, tblproductmaster r" + " where ";
				if (!bomCode.equals("") || !bomCode.isEmpty()) {
					sql = sql + " p.strBOMCode='" + bomCode + "' and ";
				}
				sql = sql + "  p.strBOMCode=q.strBOMCode " + " and q.strChildCode = r.strProdCode and p.strParentCode=d.strProdCode" + " and p.strClientCode='" + clientCode + "' and p.strClientCode=q.strClientCode" + " and q.strClientCode=r.strClientCode  )) as eachProdPer " + " from tblbommasterhd a,tblbommasterdtl b,tblproductmaster c ,tblproductmaster d " + " where  "
						+ " a.strBOMCode=b.strBOMCode " + " and b.strChildCode = c.strProdCode and a.strParentCode=d.strProdCode  " + " and a.strClientCode='" + clientCode + "' and a.strClientCode=b.strClientCode " + " and b.strClientCode=c.strClientCode  ";

				if (!bomCode.equals("") || !bomCode.isEmpty()) {
					sql = sql + " and  a.strBOMCode='" + bomCode + "' group by b.strChildCode  order by a.strParentCode ";
				} else {
					sql = sql + "   group by b.strChildCode  order by a.strParentCode ";
				}

			}

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
			hm.put("strPin", objSetup.getStrPin());

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
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptRecipeCosting." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	@RequestMapping(value = "/rptStockFlash", method = RequestMethod.GET)
	private void funStockFlashReport(@RequestParam(value = "strExportType") String strExportType, @RequestParam(value = "prodType") String prodType, @RequestParam(value = "param1") String param1, @RequestParam(value = "ManufCode") String strManufactureCode, HttpServletRequest req, HttpServletResponse resp) {
		String type = strExportType;
		String[] spParam1 = param1.split(",");
		String reportType = spParam1[0];
		String locCode = spParam1[1];
		String propCode = spParam1[2];
		String showZeroItems = spParam1[3];
		String strSGCode = spParam1[4];
		String strNonStkItems = spParam1[5];
		String strGCode = spParam1[6];
		String qtyWithUOM = spParam1[7];
		funPrepardStockFlashReport(type, prodType, req, resp, locCode, showZeroItems, strManufactureCode);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funPrepardStockFlashReport(String type, String prodType, HttpServletRequest req, HttpServletResponse resp, String locCode, String showZeroItems, String strManufactureCode) {
		try {
			objGlobal = new clsGlobalFunctions();
			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String strCurr = req.getSession().getAttribute("currValue").toString();
			double currValue = Double.parseDouble(strCurr);
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptStockFlash.jrxml");

			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			String sql = "";

			sql = " SELECT f.strPropertyName,a.strProdCode,b.strProdName,e.strLocName,d.strGName,c.strSGName,b.strUOM,b.strBinNo,"
			// + " b.dblCostRM,"
					+ " (if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice))/" + currValue + " as dblCostRM, " + " funGetUOM(a.dblOpeningStk,b.dblRecipeConversion,b.dblIssueConversion," + " b.strReceivedUOM,b.strRecipeUOM) AS OpeningStk," + " funGetUOM((a.dblGRN+dblSCGRN+a.dblStkTransIn+a.dblStkAdjIn+a.dblMISIn+a.dblQtyProduced+a.dblMaterialReturnIn),"
					+ " b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM) AS Receipts," + " funGetUOM((a.dblStkTransOut-a.dblStkAdjOut-a.dblMISOut-a.dblQtyConsumed-" + " a.dblSales-a.dblMaterialReturnOut-a.dblDeliveryNote)," + " b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM) AS Issue,"
					+ " funGetUOM(a.dblClosingStk,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM),"
					// + " (a.dblClosingStk*b.dblCostRM) AS Value,"
					+ " (a.dblClosingStk*if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice))/" + currValue + " as Value," + " a.dblClosingStk AS IssueUOMStock,b.dblIssueConversion,b.strIssueUOM," + " b.strPartNo "
					/*
					 * +
					 * " FROM tblcurrentstock a,tblproductmaster b,tblsubgroupmaster c,tblgroupmaster d,tbllocationmaster e,"
					 * + " tblpropertymaster f " +
					 * " WHERE a.strProdCode=b.strProdCode AND b.strSGCode=c.strSGCode "
					 * +
					 * " AND c.strGCode=d.strGCode AND a.strLocCode=e.strLocCode "
					 * + " AND e.strPropertyCode=f.strPropertyCode " +
					 * " and a.strLocCode='"+locCode+"' " ;
					 */
					+ " FROM tblcurrentstock a " + " left outer join tblproductmaster b on a.strProdCode=b.strProdCode " + " left outer join tblsubgroupmaster c on b.strSGCode=c.strSGCode " + " left outer join tblgroupmaster d on c.strGCode=d.strGCode " + " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode "
					+ " left outer join tblpropertymaster f on e.strPropertyCode=f.strPropertyCode " + " left outer join tblreorderlevel g on a.strProdCode=g.strProdCode and g.strLocationCode='" + locCode + "'  " + " where  a.strUserCode='" + userCode + "' ";
			if (!(prodType.equalsIgnoreCase("ALL"))) {
				sql += " and  b.strProdType <> '" + prodType + "'  ";
			}
			if(!strManufactureCode.equals("")){
				sql += " and b.strManufacturerCode='" + strManufactureCode + "'";	
			}
			sql += "and a.strClientCode='" + clientCode + "' "
			// + "and g.strClientCode='"+clientCode+"' "
					+ "and a.strUserCode='" + userCode + "' ";

			if (showZeroItems.equals("No")) {
				sql += "and (a.dblOpeningStk >0 or a.dblGRN >0 or dblSCGRN >0 or a.dblStkTransIn >0 or a.dblStkAdjIn >0 " + "or a.dblMISIn >0 or a.dblQtyProduced >0 or a.dblMaterialReturnIn>0 or a.dblStkTransOut >0 " + "or a.dblStkAdjOut >0 or a.dblMISOut >0 or a.dblQtyConsumed  >0 or a.dblSales  >0 " + "or a.dblMaterialReturnOut  >0 or a.dblDeliveryNote > 0)";
			}

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText("Select 1 from Dual;");
			jd.setQuery(newQuery);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sql);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataSet = (JRDesignDataset) datasetMap.get("dsStockFlash");
			subDataSet.setQuery(subQuery);
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
			hm.put("strPin", objSetup.getStrPin());

			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				// byte[] bytes = null;
				// bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				// resp.setContentType("application/pdf");
				// resp.setContentLength(bytes.length);
				// servletOutputStream.write(bytes, 0, bytes.length);
				// servletOutputStream.flush();
				// servletOutputStream.close();

				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, p);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptStockFlash_" + userCode + "." + type.trim());
				exporter.exportReport();

				servletOutputStream.flush();
				servletOutputStream.close();

				// } else if (type.trim().equalsIgnoreCase("xls")) {
				// JRExporter exporterXLS = new JRXlsExporter();
				// exporterXLS
				// .setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				// exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
				// resp.getOutputStream());
				// resp.setHeader("Content-Disposition", "attachment;filename="
				// + "rptRecipeCosting." + type.trim());
				// exporterXLS.exportReport();
				// resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	@RequestMapping(value = "/frmProductPurchaseReciept", method = RequestMethod.GET)
	public ModelAndView funOpenProductPurchase(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmProductPurchaseReciept");

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmReceiptRegister_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmReceiptRegister", "command", new clsReportBean());
		}

	}

	@RequestMapping(value = "/rptProductPurchaseReciept", method = RequestMethod.POST)
	private void funProductPurchaseReciept(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		funCallProductPurchaseReciept(objBean, resp, req);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void funCallProductPurchaseReciept(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		Connection con = objGlobal.funGetConnection(req);
		try {
			String fromDate = objBean.getDtFromDate();
			String toDate = objBean.getDtToDate();
			String fromTempDate = objGlobal.funGetDate("yyyy-MM-dd", fromDate);
			String toTempDate = objGlobal.funGetDate("yyyy-MM-dd", toDate);
			String tempLoc[] = objBean.getStrDocCode().split(",");
			// String tempSupp[] = objBean.getStrSuppCode().split(",");

			String strLocCodes = "";

			for (int i = 0; i < tempLoc.length; i++) {
				if (strLocCodes.length() > 0) {
					strLocCodes = strLocCodes + " or a.strLocCode='" + tempLoc[i] + "' ";

				} else {
					strLocCodes = " a.strLocCode='" + tempLoc[i] + "' ";
				}
			}

			String type = objBean.getStrDocType();
			String tempSupp[] = objBean.getStrSuppCode().split(",");
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			if (tempSupp.length == 1) // for all supp of that date range
			{
				if (tempSupp[0].length() == 0) {
					String sqlsupp = " select a.strSuppCode from tblgrnhd a where a.dtGRNDate >= '" + fromTempDate + "' AND a.dtGRNDate <= '" + toTempDate + "' ";

					if (objBean.getStrDocCode() != "") {
						sqlsupp = sqlsupp + " and " + "(" + strLocCodes + ") ";
					}
					sqlsupp = sqlsupp + "  group by a.strSuppCode order by a.strSuppCode  ";
					List listSuppDtl = objGlobalFunctionsService.funGetDataList(sqlsupp, "sql");
					for (int i = 0; i < listSuppDtl.size(); i++) {
						String supp = listSuppDtl.get(i).toString();
						// String supp = arrSupp.toString();

						JasperPrint jp = funAddProductPurchaseReciept(objBean, resp, req, supp);
						jprintlist.add(jp);

					}
				}

			}
			for (String supp : tempSupp) {
				if (supp.length() > 0) {
					JasperPrint jp = funAddProductPurchaseReciept(objBean, resp, req, supp);
					jprintlist.add(jp);
				}

			}

			ServletOutputStream servletOutputStream = resp.getOutputStream();
			if (jprintlist.size() > 0) {
				if (type.equals("PDF")) {
					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=rptProductPurchaseReciept.pdf");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();

				} else {
					JRExporter exporter = new JRXlsExporter();
					resp.setContentType("application/xlsx");
					exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=rptProductPurchaseReciept.xls");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				}

			} else {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.getWriter().append("No Record Found");

			}

			// if(type.trim().equalsIgnoreCase("pdf"))
			// {
			// ServletOutputStream servletOutputStream = resp.getOutputStream();
			// byte[] bytes = null;
			// bytes = JasperRunManager.runReportToPdf(jr,hm, con);
			// resp.setContentType("application/pdf");
			// resp.setContentLength(bytes.length);
			// servletOutputStream.write(bytes, 0, bytes.length);
			// servletOutputStream.flush();
			// servletOutputStream.close();
			// }
			// else if(type.trim().equalsIgnoreCase("xls"))
			// {
			//
			// JRExporter exporterXLS = new JRXlsExporter();
			// exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
			// exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
			// resp.getOutputStream());
			// resp.setContentType("application/vnd.ms-excel");
			// resp.setHeader( "Content-Disposition", "attachment;filename=" +
			// "rptProductPurchaseReciept."+type.trim());
			// exporterXLS.exportReport();
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	private JasperPrint funAddProductPurchaseReciept(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req, String supp) {
		JasperPrint jp = null;

		objGlobal = new clsGlobalFunctions();
		Connection con = objGlobal.funGetConnection(req);
		try {
			// String LocCode=objBean.getStrDocCode();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String type = objBean.getStrDocType();
			String fromDate = objBean.getDtFromDate();
			String toDate = objBean.getDtToDate();
			String fromTempDate = objGlobal.funGetDate("yyyy-MM-dd", fromDate);
			String toTempDate = objGlobal.funGetDate("yyyy-MM-dd", toDate);
			String tempLoc[] = objBean.getStrDocCode().split(",");
			// String tempSupp[] = objBean.getStrSuppCode().split(",");

			String strLocCodes = "";
			String pname = "All";
			if (!supp.equals("")) {
				clsSupplierMasterModel objPartyModel = objSupplierMasterService.funGetObject(supp, clientCode);
				pname = objPartyModel.getStrPName();
			}

			String strLocationNames = "";
			double dblsubTotal = 0.0;
			double dblTaxTotal = 0.0;
			List arrSubTotal = new ArrayList();

			for (int i = 0; i < tempLoc.length; i++) {
				if (strLocCodes.length() > 0) {
					strLocCodes = strLocCodes + " or a.strLocCode='" + tempLoc[i] + "' ";
					clsLocationMasterModel objLocCode = objLocationMasterService.funGetObject(tempLoc[i], clientCode);
					strLocationNames = strLocationNames + "," + objLocCode.getStrLocName();

				} else {
					strLocCodes = " a.strLocCode='" + tempLoc[i] + "' ";
					clsLocationMasterModel objLocCode = objLocationMasterService.funGetObject(tempLoc[i], clientCode);
					strLocationNames = strLocationNames + "," + objLocCode.getStrLocName();

				}
			}

			// for (int i = 0; i < tempSupp.length; i++) {
			// if (strSuppCodes.length() > 0) {
			// strSuppCodes = strSuppCodes + " or a.strSuppCode='" + tempSupp[i]
			// + "' ";
			// } else {
			// strSuppCodes = " a.strSuppCode='" + tempSupp[i] + "' ";
			//
			// }
			// }

			clsPropertySetupModel objSetup;
			objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);

			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}

			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptProductPurchaseReceipt.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			// String
			// sql="select c.strPName Supplier_Name,a.strGRNCode GRN_No, a.dtGRNDate GRN_Date, a.strAgainst GRN_Type, a.strPONo PO_No,"
			// +
			// " a.strBillNo Bill_No, a.dtBillDate Bill_Date, a.dblSubTotal, a.dblTaxAmt,a.dblTotal, "
			// +
			// " b.strProdCode P_Code, d.strProdName Product_Name, d.strUOM UOM, SUM(b.dblQty) Qty_Recd, SUM(b.dblRejected) Qty_Rejected, "
			// +
			// " b.dblUnitPrice Price, SUM(b.dblTotalPrice) Amount, e.strLocName Location_Name "
			// + " from tblgrnhd a, tblgrndtl b, tblpartymaster c, "
			// +
			// " tblproductmaster d, tbllocationmaster e ,tblsubgroupmaster f  "
			// + " Where a.strGRNCode = b.strGRNCode "
			// +
			// " and a.strSuppCode = c.strPCode and a.strLocCode = e.strLocCode "
			// +
			// " and b.strProdCode = d.strProdCode  and d.strSGCode=f.strSGCode  "
			// + " and a.dtGRNDate >= '"+fromTempDate+"' "
			// +
			// " and a.dtGRNDate <= '"+toTempDate+"' and a.strClientCode='"+clientCode+"' "
			// +
			// " and b.strClientCode='"+clientCode+"' and c.strClientCode='"+clientCode+"'"
			// +
			// " and d.strClientCode='"+clientCode+"' and e.strClientCode='"+clientCode+"'";
			String sql = " select a.strGRNCode as  GRN_No,b.strProdCode as P_Code,c.strProdName as Product_Name,sum(b.dblQty) as Qty_Recd ,a.dblSubTotal,b.dblUnitPrice as Price,a.dblTaxAmt" + " ,b.dblTotalPrice as Amount,c.strUOM as UOM" + " from tblgrnhd a, tblgrndtl b ,tblproductmaster c, tblsubgroupmaster d  "
					+ "where a.strGRNCode=b.strGRNCode and b.strProdCode=c.strProdCode and c.strSGCode=d.strSGCode   " + "AND a.dtGRNDate >= '" + fromTempDate + "' AND a.dtGRNDate <= '" + toTempDate + "' ";

			if (objBean.getStrDocCode() != "") {
				sql = sql + " and " + "(" + strLocCodes + ") ";
			}

			sql = sql + " and a.strSuppCode='" + supp + "'  ";

			sql += " group by b.strProdCode order by d.intSortingNo,c.strProdName  ";

			String sqlTotal = "  select  a.dblSubTotal,a.dblTaxAmt  from tblgrnhd a, tblgrndtl b  " + " where a.strGRNCode=b.strGRNCode " + "  AND a.dtGRNDate >= '" + fromTempDate + "' AND a.dtGRNDate <= '" + toTempDate + "' ";
			if (objBean.getStrDocCode() != "") {
				sqlTotal = sqlTotal + " and " + "(" + strLocCodes + ") ";
			}
			sqlTotal = sqlTotal + " and a.strSuppCode='" + supp + "'  ";
			sqlTotal += " group by a.strGRNCode  ";
			List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlTotal, "sql");

			for (int i = 0; i < listProdDtl.size(); i++) {
				Object[] objProdDtl = (Object[]) listProdDtl.get(i);

				dblsubTotal = dblsubTotal + Double.parseDouble(objProdDtl[0].toString());
				dblTaxTotal = dblTaxTotal + Double.parseDouble(objProdDtl[1].toString());

			}
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText("Select 1 from Dual;");
			jd.setQuery(newQuery);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sql);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataSet = (JRDesignDataset) datasetMap.get("dsProdPurchase");
			subDataSet.setQuery(subQuery);
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
			hm.put("FromDate", fromDate);
			hm.put("ToDate", toDate);
			hm.put("FromLoc", strLocationNames);
			hm.put("dblsubTotal", dblsubTotal);
			hm.put("dblTaxTotal", dblTaxTotal);
			hm.put("strSuppName", pname);

			jp = JasperFillManager.fillReport(jr, hm, con);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return jp;
		}

	}

	@RequestMapping(value = "/rptStkFlashMiniReport", method = RequestMethod.GET)
	private void funStkFlashMiniReport(@RequestParam(value = "param1") String param1, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, @RequestParam(value = "prodType") String prodType, @RequestParam(value = "ManufCode") String strManufactureCode, HttpServletRequest req, HttpServletResponse resp) {
		try {
			String[] spParam1 = param1.split(",");
			String exportType = spParam1[8];
			String userCode = req.getSession().getAttribute("usercode").toString();
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			JasperPrint jp = funCallStkFlashMiniReport(param1, fDate, tDate, prodType, strManufactureCode, req, resp);
			jprintlist.add(jp);
			ServletOutputStream servletOutputStream = resp.getOutputStream();
			if (jprintlist.size() > 0) {
				if (exportType.equals("PDF")) {
					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=rptStockMiniFlashReport_" + fDate + "_To_" + tDate + "_" + userCode + ".pdf");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();

				} else {
					JRExporter exporter = new JRXlsExporter();
					resp.setContentType("application/xlsx");
					exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=rptStockMiniFlashReport_" + fDate + "_To_" + tDate + "_" + userCode + ".xls");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				}

			} else {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.getWriter().append("No Record Found");

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private JasperPrint funCallStkFlashMiniReport(String param1, String fDate, String tDate, String prodType, String strManufactureCode, HttpServletRequest req, HttpServletResponse resp) {
		JasperPrint jp = null;
		try {

			String strCurr = req.getSession().getAttribute("currValue").toString();
			double currValue = Double.parseDouble(strCurr);
			List listStockFlashModel = new ArrayList<clsStockFlashModel>();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String[] spParam1 = param1.split(",");
			String reportType = spParam1[0];
			String locCode = spParam1[1];
			String propCode = spParam1[2];
			String showZeroItems = spParam1[3];
			String strSGCode = spParam1[4];
			String strNonStkItems = spParam1[5];
			String strGCode = spParam1[6];
			String qtyWithUOM = spParam1[7];
			String fromDate = objGlobalFunctions.funGetDate("yyyy-MM-dd", fDate);
			String toDate = objGlobalFunctions.funGetDate("yyyy-MM-dd", tDate);
			String startDate = req.getSession().getAttribute("startDate").toString();
			String[] sp = startDate.split(" ");
			String[] spDate = sp[0].split("/");
			startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
			System.out.println(startDate);
			objGlobalFunctions.funInvokeStockFlash(startDate, locCode, fromDate, toDate, clientCode, userCode, "both", req, resp);
			ArrayList fieldList = new ArrayList();
			String sql = "";
			// if(qtyWithUOM.equals("No"))
			// {
			// if(strGCode.equals("ALL") && strSGCode.equals("ALL") ) // for All
			// Group and All SubGroup
			// {
			// sql=" select a.strProdCode,b.strProdName, "
			// +
			// " a.dblClosingStk,(a.dblClosingStk*b.dblCostRM) as Value ,d.strGName,c.strSGName "
			// +
			// "from tblcurrentstock a,tblproductmaster b,tblsubgroupmaster c,tblgroupmaster d,tbllocationmaster e"
			// + ",tblpropertymaster f  "
			// +
			// "where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=d.strGCode "
			// +
			// "and a.strLocCode=e.strLocCode and e.strPropertyCode=f.strPropertyCode "
			// +
			// "and a.strClientCode='"+clientCode+"' and a.strUserCode='"+userCode+"' "
			// +
			// "and a.strLocCode='"+locCode+"' and e.strPropertyCode='"+propCode+"' "
			// + "  order by c.intSortingNo ";
			// if(!(prodType.equalsIgnoreCase("ALL"))){
			// sql+=" and  b.strProdType <> '"+prodType+"'  ";
			// }
			//
			//
			// }
			// else if( !(strGCode.equals("ALL")) && strSGCode.equals("ALL") )
			// // for Particulor group and All SubGroup
			// {
			// sql="select a.strProdCode,b.strProdName,"
			// +
			// " a.dblClosingStk,(a.dblClosingStk*b.dblCostRM) as Value ,d.strGName,c.strSGName "
			// +
			// "from tblcurrentstock a,tblproductmaster b,tblsubgroupmaster c,tblgroupmaster d,tbllocationmaster e"
			// + ",tblpropertymaster f  "
			// +
			// "where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=d.strGCode "
			// +
			// "and a.strLocCode=e.strLocCode and e.strPropertyCode=f.strPropertyCode "
			// +
			// "and a.strClientCode='"+clientCode+"' and a.strUserCode='"+userCode+"' "
			// + "and c.strGCode='"+strGCode+"' "
			// +
			// "and a.strLocCode='"+locCode+"' and e.strPropertyCode='"+propCode+"' "
			// + "  order by c.intSortingNo ";
			// if(!(prodType.equalsIgnoreCase("ALL"))){
			// sql+=" and  b.strProdType <> '"+prodType+"'  ";
			// }
			//
			//
			// }else // // for Particulor group and Particulor SubGroup
			// {
			// sql="select a.strProdCode,b.strProdName, "
			// +
			// " a.dblClosingStk,(a.dblClosingStk*b.dblCostRM) as Value ,d.strGName,c.strSGName "
			// +
			// "from tblcurrentstock a,tblproductmaster b,tblsubgroupmaster c,tblgroupmaster d,tbllocationmaster e"
			// + ",tblpropertymaster f  "
			// +
			// "where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=d.strGCode "
			// +
			// "and a.strLocCode=e.strLocCode and e.strPropertyCode=f.strPropertyCode "
			// +
			// "and a.strClientCode='"+clientCode+"' and a.strUserCode='"+userCode+"' "
			// +
			// "and c.strGCode='"+strGCode+"' and b.strSGCode='"+strSGCode+"' "
			// +
			// "and a.strLocCode='"+locCode+"' and e.strPropertyCode='"+propCode+"' "
			// + "  order by c.intSortingNo ";
			// if(!(prodType.equalsIgnoreCase("ALL"))){
			// sql+=" and  b.strProdType <> '"+prodType+"'  ";
			// }
			//
			// }
			//
			// }

			sql = "select a.strProdCode,b.strProdName,a.dblClosingStk,"
					// + "(a.dblClosingStk*b.dblCostRM) as Value,"
					+ "(a.dblClosingStk*if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice))/" + currValue + " as Value," + "d.strGName,c.strSGName "
					/*
					 * +
					 * "from tblcurrentstock a,tblproductmaster b,tblsubgroupmaster c,tblgroupmaster d,tbllocationmaster e "
					 * + ",tblpropertymaster f " +
					 * "where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=d.strGCode "
					 * +
					 * "and a.strLocCode=e.strLocCode and e.strPropertyCode=f.strPropertyCode "
					 * ;
					 */
					+ " FROM tblcurrentstock a " + " left outer join tblproductmaster b on a.strProdCode=b.strProdCode " + " left outer join tblsubgroupmaster c on b.strSGCode=c.strSGCode " + " left outer join tblgroupmaster d on c.strGCode=d.strGCode " + " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode "
					+ " left outer join tblpropertymaster f on e.strPropertyCode=f.strPropertyCode " + " left outer join tblreorderlevel g on a.strProdCode=g.strProdCode and g.strLocationCode='" + locCode + "'  " + " where  a.strUserCode='" + userCode + "' ";

			if (strNonStkItems.equals("Non Stockable")) {
				sql += "	and b.strNonStockableItem='Y' ";
			} else if (strNonStkItems.equals("Stockable")) {
				sql += "	and b.strNonStockableItem='N' ";
			}

			if (!(prodType.equalsIgnoreCase("ALL"))) {
				sql += " and b.strProdType <> '" + prodType + "'  ";
			}
			sql += "and a.strClientCode='" + clientCode + "'"
			// + " and g.strLocationCode='"+locCode+"' "
			// + " and g.strClientCode='"+clientCode+"'"
					+ " and a.strUserCode='" + userCode + "' ";

			if(!strManufactureCode.equals("")){
				sql += " and b.strManufacturerCode='" + strManufactureCode + "'";	
			}

			if (!strGCode.equalsIgnoreCase("All")) {
				sql += "and d.strGCode='" + strGCode + "' ";
			}

			if (!strSGCode.equalsIgnoreCase("All")) {
				sql += "and c.strSGCode='" + strSGCode + "' ";
			}
			/*
			 * if(strNonStkItems.equals("Stockable")) { sql+=
			 * "and b.strNonStockableItem='N' "; }
			 * if(strNonStkItems.equals("Non Stockable")) { sql+=
			 * "and b.strNonStockableItem='Y' "; }
			 */
			if (showZeroItems.equals("No")) {
				sql += "and (a.dblOpeningStk >0 or a.dblGRN >0 or dblSCGRN >0 or a.dblStkTransIn >0 or a.dblStkAdjIn >0 " + "or a.dblMISIn >0 or a.dblQtyProduced >0 or a.dblMaterialReturnIn>0 or a.dblStkTransOut >0 " + "or a.dblStkAdjOut >0 or a.dblMISOut >0 or a.dblQtyConsumed  >0 or a.dblSales  >0 " + "or a.dblMaterialReturnOut  >0 or a.dblDeliveryNote > 0)";
			}

			clsLocationMasterModel objLocCode = objLocationMasterService.funGetObject(locCode, clientCode);
			// System.out.println(sql);
			// List list=objStkFlashService.funGetStockFlashData(sql,clientCode,
			// userCode);
			List list = objGlobalService.funGetList(sql);
			double totValue = 0.00,totClosingStk=0.00;
			DecimalFormat df = new DecimalFormat("#.##");
			for (int cnt = 0; cnt < list.size(); cnt++) {
				Object[] arrObj = (Object[]) list.get(cnt);
				double closeStk = Double.parseDouble(arrObj[2].toString());
				double value = Double.parseDouble(arrObj[3].toString());

				clsStockFlashModel objStkFlashModel = new clsStockFlashModel();
				objStkFlashModel.setStrProdCode(arrObj[0].toString());
				objStkFlashModel.setStrProdName(arrObj[1].toString());
				objStkFlashModel.setDblClosingStock(df.format(closeStk).toString());
				objStkFlashModel.setDblValue(df.format(value).toString());
				objStkFlashModel.setStrGroupName(arrObj[4].toString());
				objStkFlashModel.setStrSubGroupName(arrObj[5].toString());
				if (value < 0) {
					value = value * (0);
				}
				totValue += value;
				totClosingStk+=closeStk;
				listStockFlashModel.add(objStkFlashModel);

			}

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String suppName = "";

			// String reportName =
			// servletContext.getRealPath("/WEB-INF/reports/webcrm/rptShopOrderList.jrxml");
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptStockMiniFlashReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
			String propNameSql = "select a.strPropertyName  from "+webStockDB+".tblpropertymaster a where a.strPropertyCode='" + propCode + "' and a.strClientCode='" + clientCode + "' ";
			List listPropName = objGlobalFunctionsService.funGetDataList(propNameSql, "sql");
			String propName = "";
			if (listPropName.size() > 0) {
				propName = listPropName.get(0).toString();
			}
			String companyName = req.getSession().getAttribute("companyName").toString();

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
			hm.put("dteFromDate", fDate);
			hm.put("dteToDate", tDate);
			hm.put("stkMiniFlashList", listStockFlashModel);
			hm.put("strLocName", objLocCode.getStrLocName());
			hm.put("totValue", totValue);
			hm.put("totClosingStk", totClosingStk);

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			jp = JasperFillManager.fillReport(jr, hm, new JREmptyDataSource());

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return jp;
		}

	}

	@RequestMapping(value = "/rptStkTransferFlashReport", method = RequestMethod.GET)
	private void funStkTransferFlashReport(@RequestParam(value = "param1") String param1, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, HttpServletRequest req, HttpServletResponse resp) {
		try {
			String userCode = req.getSession().getAttribute("usercode").toString();
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			JasperPrint jp = funCallStkTransferFlashReport(param1, fDate, tDate, req, resp);
			jprintlist.add(jp);
			if (jprintlist.size() > 0) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();

				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=rptStkTransferFlashReport_" + fDate + "_To_" + tDate + "_" + userCode + ".pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();

			} else {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.getWriter().append("No Record Found");

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private JasperPrint funCallStkTransferFlashReport(String param1, String fDate, String tDate, HttpServletRequest req, HttpServletResponse resp) {
		JasperPrint jp = null;
		try {
			List listStockFlashModel = new ArrayList<clsStockFlashModel>();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String[] spParam1 = param1.split(",");
			String fromLocCode = spParam1[1];
			String toLocCode = spParam1[2];

			String fromDate = objGlobalFunctions.funGetDate("yyyy-MM-dd", fDate);
			String toDate = objGlobalFunctions.funGetDate("yyyy-MM-dd", tDate);

			String sql = "  select b.strProdCode,c.strProdName,sum(b.dblQty),d.strGName,e.strSGName " + "  from tblstocktransferhd a,tblstocktransferdtl b,tblproductmaster c " + " ,tblgroupmaster d,tblsubgroupmaster e  where a.strSTCode=b.strSTCode  " + "  and a.strFromLocCode='" + fromLocCode + "' and a.strToLocCode='" + toLocCode + "' " + " and Date(a.dtSTDate)  between '" + fromDate
					+ "' and '" + toDate + "' " + "  and b.strProdCode=c.strProdCode  and c.strSGCode=e.strSGCode and d.strGCode=e.strGCode " + " group by b.strProdCode  order by e.intSortingNo  ";

			System.out.println(sql);
			List list = objGlobalService.funGetList(sql);
			List listStockTransferFlashModel = new ArrayList<clsStockFlashModel>();

			for (int cnt = 0; cnt < list.size(); cnt++) {
				Object[] arrObj = (Object[]) list.get(cnt);
				clsStockFlashModel objStkTransferFlashModel = new clsStockFlashModel();
				objStkTransferFlashModel.setStrProdCode(arrObj[0].toString());
				objStkTransferFlashModel.setStrProdName(arrObj[1].toString());
				objStkTransferFlashModel.setDblIssue(arrObj[2].toString());
				objStkTransferFlashModel.setStrGroupName(arrObj[3].toString());
				objStkTransferFlashModel.setStrSubGroupName(arrObj[4].toString());
				listStockTransferFlashModel.add(objStkTransferFlashModel);

			}
			clsLocationMasterModel objFromLocCode = objLocationMasterService.funGetObject(fromLocCode, clientCode);
			clsLocationMasterModel objToLocCode = objLocationMasterService.funGetObject(toLocCode, clientCode);
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String suppName = "";

			// String reportName =
			// servletContext.getRealPath("/WEB-INF/reports/webcrm/rptShopOrderList.jrxml");
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptStockTransferFlashReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
			String propNameSql = "select a.strPropertyName  from "+webStockDB+".tblpropertymaster a where a.strPropertyCode='" + propCode + "' and a.strClientCode='" + clientCode + "' ";
			List listPropName = objGlobalFunctionsService.funGetDataList(propNameSql, "sql");
			String propName = "";
			if (listPropName.size() > 0) {
				propName = listPropName.get(0).toString();
			}
			String companyName = req.getSession().getAttribute("companyName").toString();

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
			hm.put("dteFromDate", fDate);
			hm.put("dteToDate", tDate);
			hm.put("stkTransferFlashList", listStockTransferFlashModel);
			hm.put("strFromLocName", objFromLocCode.getStrLocName());
			hm.put("strToLocName", objToLocCode.getStrLocName());

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			jp = JasperFillManager.fillReport(jr, hm, new JREmptyDataSource());

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return jp;
		}

	}

	@RequestMapping(value = "/frmPurchaseRegisterReport", method = RequestMethod.GET)
	public ModelAndView funOpenPurchaseRegisterReport(Map<String, Object> model, HttpServletRequest request)
	{
		request.getSession().setAttribute("formName", "frmPurchaseRegisterReport");

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

		Map mapViewType = new HashMap<String, String>();
		mapViewType.put("Item Wise", "Item Wise");
		mapViewType.put("Bill Wise", "Bill Wise");

		model.put("mapViewType", mapViewType);

		if ("2".equalsIgnoreCase(urlHits))
		{
			return new ModelAndView("frmPurchaseRegisterReport_1", "command", new clsReportBean());
		}
		else
		{
			return new ModelAndView("frmPurchaseRegisterReport", "command", new clsReportBean());
		}

	}

	@RequestMapping(value = "/rptPurchaseRegisterReport", method = RequestMethod.POST)
	private void funCategoryWiseSalesOrderReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req)
	{

		// funCallCategoryWiseSalesOrderReport(objBean, resp, req);

		if (objBean.getStrReportView().equalsIgnoreCase("Item Wise"))
		{
			funCallPurchaseRegisterReport(objBean, resp, req);
		}
		else
		{
			funCallBillWisePurchaseRegisterReport(objBean, resp, req);
		}
	}
	@SuppressWarnings(
			{ "unused", "unused", "unused", "unchecked" })
	private void funCallBillWisePurchaseRegisterReport(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req)
	{

		Connection con = objGlobalFunctions.funGetConnection(req);
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		if (objSetup == null)
		{
			objSetup = new clsPropertySetupModel();
		}

		String reportName = servletContext.getRealPath("/WEB-INF/reports/rptBillWisePurchaseRegisterReport.jrxml");
		String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

		String webStockDB = req.getSession().getAttribute("WebStockDB").toString();

		String propNameSql = "select a.strPropertyName  from " + webStockDB + ".tblpropertymaster a where a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' ";
		List listPropName = objGlobalFunctionsService.funGetDataList(propNameSql, "sql");
		String propName = "";
		if (listPropName.size() > 0)
		{
			propName = listPropName.get(0).toString();
		}

		ArrayList fieldList = new ArrayList();

		String sqlQuery = " SELECT d.strPName,a.strBillNo, a.dblTotal AS Amt,e.strLocName, DATE_FORMAT(a.dtGRNDate,'%d-%m-%Y')dtGRNDate " + " FROM tblgrnhd a,tblpartymaster d,tbllocationmaster e" + " WHERE  a.strSuppCode=d.strPCode  " + " and a.strLocCode=e.strLocCode ";

		if (null != objBean.getStrDocCode() && objBean.getStrDocCode().length() > 0)
		{
			sqlQuery = sqlQuery + " and a.strSuppCode='" + objBean.getStrDocCode() + "' ";
		}

		String fromDate = objBean.getDteFromDate();
		String toDate = objBean.getDteToDate();

		String fd = fromDate.split("-")[2];
		String fm = fromDate.split("-")[1];
		String fy = fromDate.split("-")[0];

		String td = toDate.split("-")[2];
		String tm = toDate.split("-")[1];
		String ty = toDate.split("-")[0];

		String dteFromDate = fd + "-" + fm + "-" + fy;
		String dteToDate = td + "-" + tm + "-" + ty;

		sqlQuery = sqlQuery + " and date(a.dtGRNDate) between  '" + fromDate + "' and '" + toDate + "'" + " ";
		sqlQuery = sqlQuery + " ORDER BY  a.dtGRNDate,d.strPName,a.strBillNo ";

		List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");

		java.text.DecimalFormat objDecimalFormat = new java.text.DecimalFormat("0.00");

		for (int j = 0; j < listProdDtl.size(); j++)
		{
			clsPurchaseRegisterReportBean objProdBean = new clsPurchaseRegisterReportBean();
			Object[] prodArr = (Object[]) listProdDtl.get(j);

			objProdBean.setStrPName(prodArr[0].toString());
			objProdBean.setStrBillNo(prodArr[1].toString());

			objProdBean.setDblAmount(Double.parseDouble(prodArr[2].toString()));
			objProdBean.setStrFromLocation(prodArr[3].toString());
			objProdBean.setDtGRNDate(prodArr[4].toString());

			fieldList.add(objProdBean);

		}

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

		try
		{
			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			jprintlist.add(jp);
			ServletOutputStream servletOutputStream = resp.getOutputStream();

			if (jprintlist.size() > 0)
			{
				if (objBean.getStrDocType().equals("PDF"))
				{
					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=rptBillWisePurchaseRegisterReport" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".pdf");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();

				}
				else
				{
					JRExporter exporter = new JRXlsExporter();
					resp.setContentType("application/xlsx");
					exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=rptBillWisePurchaseRegisterReport" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".xls");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				}

			}
			else
			{
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.getWriter().append("No Record Found");

			}

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

	}

	@SuppressWarnings(
			{ "unused", "unused", "unused", "unchecked" })
			private void funCallPurchaseRegisterReport(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req)
			{

				Connection con = objGlobalFunctions.funGetConnection(req);
				String clientCode = req.getSession().getAttribute("clientCode").toString();
				String companyName = req.getSession().getAttribute("companyName").toString();
				String userCode = req.getSession().getAttribute("usercode").toString();
				String propertyCode = req.getSession().getAttribute("propertyCode").toString();
				clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
				if (objSetup == null)
				{
					objSetup = new clsPropertySetupModel();
				}

				String reportName = servletContext.getRealPath("/WEB-INF/reports/rptPurchaseRegisterReport.jrxml");
				String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

				String webStockDB = req.getSession().getAttribute("WebStockDB").toString();

				String propNameSql = "select a.strPropertyName  from " + webStockDB + ".tblpropertymaster a where a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' ";
				List listPropName = objGlobalFunctionsService.funGetDataList(propNameSql, "sql");
				String propName = "";
				if (listPropName.size() > 0)
				{
					propName = listPropName.get(0).toString();
				}

				ArrayList fieldList = new ArrayList();

				String sqlQuery = " select d.strPName,c.strProdName,b.dblQty,c.strUOM,b.dblUnitPrice,(b.dblQty*b.dblUnitPrice) as Amt,DATE_FORMAT(a.dtGRNDate,'%d-%m-%Y') " + " from tblgrnhd a,tblgrndtl b,tblproductmaster c,tblpartymaster d where a.strGRNCode=b.strGRNCode " + " and a.strSuppCode=d.strPCode and b.strProdCode=c.strProdCode ";

				if (null != objBean.getStrDocCode() && objBean.getStrDocCode().length() > 0)
				{
					sqlQuery = sqlQuery + " and a.strSuppCode='" + objBean.getStrDocCode() + "' ";
				}

				String fromDate = objBean.getDteFromDate();
				String toDate = objBean.getDteToDate();

				String fd = fromDate.split("-")[0];
				String fm = fromDate.split("-")[1];
				String fy = fromDate.split("-")[2];

				String td = toDate.split("-")[0];
				String tm = toDate.split("-")[1];
				String ty = toDate.split("-")[2];

				String dteFromDate = fy + "-" + fm + "-" + fd;
				String dteToDate = ty + "-" + tm + "-" + td;

				sqlQuery = sqlQuery + " and date(a.dtGRNDate) between  '" + fromDate + "' and '" + toDate + "'" + " order by d.strPName,a.dtGRNDate ,c.strProdName  ";

				List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");

				for (int j = 0; j < listProdDtl.size(); j++)
				{
					clsPurchaseRegisterReportBean objProdBean = new clsPurchaseRegisterReportBean();
					Object[] prodArr = (Object[]) listProdDtl.get(j);

					objProdBean.setStrPName(prodArr[0].toString());
					objProdBean.setStrProdName(prodArr[1].toString());
					objProdBean.setDblQty(Double.parseDouble(prodArr[2].toString()));
					objProdBean.setStrUOM(prodArr[3].toString());
					objProdBean.setDblUnitPrice(Double.parseDouble(prodArr[4].toString()));
					objProdBean.setDblAmount(Double.parseDouble(prodArr[5].toString()));
					objProdBean.setDtGRNDate(prodArr[6].toString());
					fieldList.add(objProdBean);

				}

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
				hm.put("dteFromDate", objBean.getDteFromDate());
				hm.put("dteToDate", objBean.getDteToDate());

				try
				{
					JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
					JasperDesign jd = JRXmlLoader.load(reportName);
					JasperReport jr = JasperCompileManager.compileReport(jd);
					JasperPrint jp = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
					List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
					jprintlist.add(jp);
					ServletOutputStream servletOutputStream = resp.getOutputStream();

					if (jprintlist.size() > 0)
					{
						if (objBean.getStrDocType().equals("PDF"))
						{
							JRExporter exporter = new JRPdfExporter();
							resp.setContentType("application/pdf");
							exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
							exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
							exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
							resp.setHeader("Content-Disposition", "inline;filename=rptPurchaseRegisterReport" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".pdf");
							exporter.exportReport();
							servletOutputStream.flush();
							servletOutputStream.close();

						}
						else
						{
							JRExporter exporter = new JRXlsExporter();
							resp.setContentType("application/xlsx");
							exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
							exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
							exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
							resp.setHeader("Content-Disposition", "inline;filename=rptPurchaseRegisterReport" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".xls");
							exporter.exportReport();
							servletOutputStream.flush();
							servletOutputStream.close();
						}

					}
					else
					{
						resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
						resp.getWriter().append("No Record Found");

					}

				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}

			}
	
	@RequestMapping(value = "/frmMISLocationWiseCategoryWiseReport", method = RequestMethod.GET)
	public ModelAndView frmOpenMISLocationWiseCategoryWiseReport(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmMISLocationWiseCategoryWiseReport");

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMISLocationWiseCategoryWiseReport_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmMISLocationWiseCategoryWiseReport", "command", new clsReportBean());
		}

	}

	@RequestMapping(value = "/rptMISLocationWiseCategoryWiseReport", method = RequestMethod.POST)
	private ModelAndView funCallMISLocationWiseCategoryWiseReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		ModelAndView mod = funShowMISLocationWiseCategoryWiseReport(objBean, resp, req);
		return mod;
	}

	@SuppressWarnings({ "unused", "unused", "unused", "unchecked" })
	private ModelAndView funShowMISLocationWiseCategoryWiseReport(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req)
	{

		Connection con = objGlobalFunctions.funGetConnection(req);
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		if (objSetup == null)
		{
			objSetup = new clsPropertySetupModel();
		}

		
		String fromDate = objBean.getDtFromDate();
		String toDate = objBean.getDtToDate();

		String fd = fromDate.split("-")[0];
		String fm = fromDate.split("-")[1];
		String fy = fromDate.split("-")[2];

		String td = toDate.split("-")[0];
		String tm = toDate.split("-")[1];
		String ty = toDate.split("-")[2];

		String dteFromDate = fy + "-" + fm + "-" + fd;
		String dteToDate = ty + "-" + tm + "-" + td;
		
		String tempToLoc[] = objBean.getStrToLoc().split(",");
		String fromLoc = objBean.getStrFromLoc();
		// String tempSG[] = objBean.getStrSGCode().split(",");
		String strToLocCodes = "";
		String strFromLocCodes = "";

		for (int i = 0; i < tempToLoc.length; i++)
		{
			if (strToLocCodes.length() > 0)
			{
				strToLocCodes = strToLocCodes + " or a.strLocTo='" + tempToLoc[i] + "' ";
			}
			else
			{
				strToLocCodes = "a.strLocTo='" + tempToLoc[i] + "' ";

			}
		}
		
		String periodFromDate = fromDate + "  -  " + toDate;
		List listLOCWiseData = new ArrayList();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String printedOnDate = dtf.format(now);
		listLOCWiseData.add("rptMISLocationWiseReport_" + dteFromDate + "to" + dteToDate + "_" + userCode);
		List titleData = new ArrayList<>();
		titleData.add("MIS Location Wise Category Wise Report");
		listLOCWiseData.add(titleData);
		List filterData = new ArrayList<>();
		filterData.add("Period From");
		filterData.add(periodFromDate);
		filterData.add("Printed On");
		filterData.add(printedOnDate);

		listLOCWiseData.add(filterData);
		
		ArrayList<String> locNameList = new ArrayList<String>();
		ArrayList<Object> hearderList = new ArrayList<Object>();

		hearderList.add("SGName");
		hearderList.add("Product");
		hearderList.add("Issue UOM");
		for (int i = 0; i < tempToLoc.length; i++)
		{
			clsLocationMasterModel locModel = objLocationMasterService.funGetObject(tempToLoc[i], clientCode);
			locNameList.add(locModel.getStrLocName());
			hearderList.add("Qty " + locModel.getStrLocName());
			hearderList.add("Amt " + locModel.getStrLocName());
		}

		// String reportName =
		// servletContext.getRealPath("/WEB-INF/reports/rptMISLocationWiseReport.jrxml");
		// String imagePath =
		// servletContext.getRealPath("/resources/images/company_Logo.png");

		String webStockDB = req.getSession().getAttribute("WebStockDB").toString();
		String propNameSql = "select a.strPropertyName  from " + webStockDB + ".tblpropertymaster a where a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' ";
		List listPropName = objGlobalFunctionsService.funGetDataList(propNameSql, "sql");
		String propName = "";
		if (listPropName.size() > 0)
		{
			propName = listPropName.get(0).toString();
		}

		ArrayList fieldList = new ArrayList();

//		listLOCWiseData.add("rptMISLocationWiseReport_" + dteFromDate + "to" + dteToDate + "_" + userCode);

		String sqlQuery = " select DISTINCT e.strSGName,c.strProdName,c.strIssueUOM, b.strProdCode,c.strSGCode " + " from tblmishd a ,tblmisdtl b,tblproductmaster c ,tbllocationmaster d,tblsubgroupmaster e   " + " where a.strMISCode=b.strMISCode and a.strLocFrom='" + fromLoc + "' and b.strProdCode=c.strProdCode  " + " and a.strLocTo=d.strLocCode and c.strSGCode=e.strSGCode  " + " and date(a.dtMISDate) between '" + fromDate + "' and '" + toDate + "'  "

				+ " and " + " ( " + strToLocCodes + ") "

				+ " group by b.strProdCode,a.strLocTo " + " order by e.strSGName ASC, c.strProdName ASC ";

		List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");
		/*
		 * HashMap<String,HashMap<String,List>> hmProd = new
		 * HashMap<String,HashMap<String,List>>(); for (int j = 0; j <
		 * listProdDtl.size(); j++) { Object[] arrObj=(Object[])listProdDtl.get(j); List
		 * listQtyNAmt = new ArrayList<>(); listQtyNAmt.add(arrObj[4].toString());
		 * listQtyNAmt.add(arrObj[5].toString());
		 * 
		 * HashMap<String,List> hmLocValue = new HashMap<String,List>();
		 * hmLocValue.put(arrObj[3].toString(), listQtyNAmt);
		 * hmProd.put(arrObj[1].toString(), hmLocValue); }
		 */
		/*
		 * List totList=new ArrayList<>(); totList.add(""); totList.add("");
		 * totList.add("");
		 */
		List blankList = new ArrayList<>();
		blankList.add("");
		blankList.add("");
		blankList.add("");

		Object[] objHeader = (Object[]) hearderList.toArray();
		String[] ExcelHeader = new String[objHeader.length + 2];
		for (int k = 0; k < objHeader.length; k++)
		{
			blankList.add("");
			// totList.add("");
			ExcelHeader[k] = objHeader[k].toString();
		}
		blankList.add("");
		blankList.add("");
		ExcelHeader[objHeader.length] = "Qty Total";
		ExcelHeader[objHeader.length + 1] = "Amt Total";

		listLOCWiseData.add(ExcelHeader);
		

		String preSubGroup = "";
		double totAmt = 0.00;
		String sgCode = "";
		int cnt = 0;
		for (int j = 0; j < listProdDtl.size(); j++)
		{
			Object[] arrObj = (Object[]) listProdDtl.get(j);
			List DataList = new ArrayList<>();
			if (preSubGroup.length() == 0)
			{
				preSubGroup = arrObj[0].toString();
			}
			if (sgCode.length() == 0)
			{
				sgCode = arrObj[4].toString();
			}

			if (!preSubGroup.equals(arrObj[0].toString()))
			{
				fieldList.add(blankList);
				String sgName = preSubGroup;
				preSubGroup = arrObj[0].toString();

				double totSGCol1 = 0,totalQty=0;
				List totList = new ArrayList<>();
				totList.add(sgName);
				totList.add("");
				totList.add("Total");
				for (int i = 0; i < tempToLoc.length; i++)
				{
					String sqlSGTot = " select c.strSGCode,e.strSGName,sum(b.dblTotalPrice),d.strLocName,sum(b.dblQty) " + " from tblmishd a ,tblmisdtl b,tblproductmaster c ,tbllocationmaster d,tblsubgroupmaster e " + " where a.strMISCode=b.strMISCode and a.strLocFrom='" + fromLoc + "' and b.strProdCode=c.strProdCode " + " and a.strLocTo=d.strLocCode and c.strSGCode=e.strSGCode and date(a.dtMISDate) " + " between '" + fromDate + "' and '" + toDate + "' and a.strLocTo='" + tempToLoc[i] + "' " + " and e.strSGCode='" + sgCode + "'  " + " group by e.strSGCode,a.strLocTo " + " order by e.strSGName ASC, c.strProdName ASC ";

					List listSGTot = objGlobalFunctionsService.funGetDataList(sqlSGTot, "sql");
					if (listSGTot.size() != 0)
					{
						for (int num = 0; num < listSGTot.size(); num++)
						{
							Object[] arrObjSG = (Object[]) listSGTot.get(num);

							if (sgName.equalsIgnoreCase(arrObjSG[1].toString()))
							{
								totList.add(arrObjSG[4].toString());
								totList.add(arrObjSG[2].toString());
								totSGCol1 += Double.parseDouble(arrObjSG[2].toString());
								totalQty += Double.parseDouble(arrObjSG[2].toString());
							}

						}
					}
					else
					{
						totList.add(0);
						totList.add(0.0);
					}

				}
				totList.add(totalQty);
				totList.add(totSGCol1);
				totSGCol1 = 0;
				fieldList.add(totList);
				fieldList.add(blankList);
				sgCode = arrObj[4].toString();

			}

			double qtyCol = 0;
			double amtCol = 0;

			DataList.add(arrObj[0].toString());
			DataList.add(arrObj[1].toString());
			DataList.add(arrObj[2].toString());
			String prodCode = arrObj[3].toString();
			// String sgCode=arrObj[4].toString();
			for (int i = 0; i < tempToLoc.length; i++)
			{

				String sqlloc = " select  e.strSGName,c.strProdName,c.strUOM, " + " sum(b.dblQty ),sum(b.dblTotalPrice) , a.strLocTo " + " from tblmishd a ,tblmisdtl b,tblproductmaster c ,tbllocationmaster d,tblsubgroupmaster e " + " where a.strMISCode=b.strMISCode and a.strLocFrom='" + fromLoc + "' and b.strProdCode=c.strProdCode " + " and b.strProdCode= '" + prodCode + "' " + " and a.strLocTo=d.strLocCode and c.strSGCode=e.strSGCode and " + " a.strLocTo='" + tempToLoc[i] + "'  " + " and date(a.dtMISDate) between '" + fromDate + "' and '" + toDate + "' " + " group by b.strProdCode,a.strLocTo " + " order by e.strSGName ASC, c.strProdName ASC ";

				List listlocTot = objGlobalFunctionsService.funGetDataList(sqlloc, "sql");

				if (listlocTot.size() != 0)
				{
					for (int k = 0; k < listlocTot.size(); k++)
					{
						Object[] arrLocTot = (Object[]) listlocTot.get(k);
						qtyCol += Double.parseDouble(arrLocTot[3].toString());
						amtCol += Double.parseDouble(arrLocTot[4].toString());
						DataList.add(arrLocTot[3].toString());
						DataList.add(arrLocTot[4].toString());

					}
				}
				else
				{
					DataList.add(0.0);
					DataList.add(0.0);
				}

			}
			DataList.add(qtyCol);
			DataList.add(amtCol);
			qtyCol = 0;
			amtCol = 0;
			fieldList.add(DataList);
			double totSGCol2 = 0,totalQty=0;

			if ((cnt + 1) == listProdDtl.size())
			{
				fieldList.add(blankList);
				String sgName = preSubGroup;
				preSubGroup = arrObj[0].toString();

				List totList = new ArrayList<>();
				totList.add(sgName);
				totList.add("");
				totList.add("Total");
				for (int i = 0; i < tempToLoc.length; i++)
				{
					String sqlSGTot = " select c.strSGCode,e.strSGName,sum(b.dblTotalPrice),d.strLocName,sum(b.dblQty) " + " from tblmishd a ,tblmisdtl b,tblproductmaster c ,tbllocationmaster d,tblsubgroupmaster e " + " where a.strMISCode=b.strMISCode and a.strLocFrom='" + fromLoc + "' and b.strProdCode=c.strProdCode " + " and a.strLocTo=d.strLocCode and c.strSGCode=e.strSGCode and date(a.dtMISDate) " + " between '" + fromDate + "' and '" + toDate + "' and a.strLocTo='" + tempToLoc[i] + "' " + " and e.strSGCode='" + sgCode + "'  " + " group by e.strSGCode,a.strLocTo  order by e.strSGName ASC, c.strProdName ASC ";

					List listSGTot = objGlobalFunctionsService.funGetDataList(sqlSGTot, "sql");
					if (listSGTot.size() != 0)
					{
						for (int num = 0; num < listSGTot.size(); num++)
						{
							Object[] arrObjSG = (Object[]) listSGTot.get(num);

							if (sgName.equalsIgnoreCase(arrObjSG[1].toString()))
							{
								totList.add(arrObjSG[4].toString());
								totList.add(arrObjSG[2].toString());
								totSGCol2 += Double.parseDouble(arrObjSG[2].toString());
								totalQty += Double.parseDouble(arrObjSG[2].toString());
							}

						}
					}
					else
					{
						totList.add(0);
						totList.add(0.0);
					}

				}
				totList.add(totalQty);
				totList.add(totSGCol2);
				totSGCol2 = 0;
				fieldList.add(totList);

			}

			cnt++;

		}

		Object[] arrObj = (Object[]) listProdDtl.get(0);
		List DataList = new ArrayList<>();
		if (preSubGroup.length() == 0)
		{
			preSubGroup = arrObj[0].toString();
		}
		if (sgCode.length() == 0)
		{
			sgCode = arrObj[4].toString();
		}

		if (!preSubGroup.equals(arrObj[0].toString()))
		{
			fieldList.add(blankList);
			String sgName = preSubGroup;
			preSubGroup = arrObj[0].toString();

			double grandTotal = 0,totalQty=0;
			List totList = new ArrayList<>();
			totList.add("");
			totList.add("");
			totList.add("Grand Total");
			for (int i = 0; i < tempToLoc.length; i++)
			{
				String sqlGrandTot = " SELECT c.strSGCode,e.strSGName, SUM(b.dblTotalPrice),d.strLocName,sum(b.dblQty) " + " FROM tblmishd a,tblmisdtl b,tblproductmaster c,tbllocationmaster d,tblsubgroupmaster e " + " WHERE a.strMISCode=b.strMISCode AND a.strLocFrom='" + fromLoc + "' AND b.strProdCode=c.strProdCode AND a.strLocTo=d.strLocCode " + " AND DATE(a.dtMISDate) BETWEEN '" + fromDate + "' and '" + toDate + "' AND a.strLocTo='" + tempToLoc[i] + "' AND e.strSGCode='" + sgCode + "' " + " GROUP BY e.strSGCode,a.strLocTo; ";

				List listGrandTot = objGlobalFunctionsService.funGetDataList(sqlGrandTot, "sql");
				if (listGrandTot.size() != 0)
				{
					for (int num = 0; num < listGrandTot.size(); num++)
					{
						Object[] arrObjSG = (Object[]) listGrandTot.get(num);

						if (sgName.equalsIgnoreCase(arrObjSG[1].toString()))
						{
							totList.add(arrObjSG[4].toString());
							totList.add(arrObjSG[2].toString());
							grandTotal += Double.parseDouble(arrObjSG[2].toString());
							totalQty+=Double.parseDouble(arrObjSG[4].toString());
						}

					}
				}
				else
				{
					totList.add(0);
					totList.add(0.0);
				}

			}

			totList.add(totalQty);
			totList.add(grandTotal);
			// grandTotal = 0;
			fieldList.add(totList);
		}

		listLOCWiseData.add(fieldList);//2
		
		listLOCWiseData.add(companyName);	
//		listLOCWiseData.add("MIS Location Wise Category Wise Report");
		listLOCWiseData.add("Reporting For:"+fromLoc);
//			
//		String periodFromDate = fromDate + "  -  " + toDate;
//
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
//		LocalDateTime now = LocalDateTime.now();
//		String printedOnDate = dtf.format(now);
//
//		listLOCWiseData.add("Period From:"+periodFromDate);		
//		listLOCWiseData.add("Printed On:"+printedOnDate);
//		

		if(objBean.getStrDocType().equalsIgnoreCase("PDF"))
		{
			return new ModelAndView("pdfViewMISLocationWiseCategoryWiseReport", "listWithReportName", listLOCWiseData);
		}
		else
		{
			return new ModelAndView("excelViewFromDateTodateWithReportName", "listFromDateTodateWithReportName", listLOCWiseData);
		}

	}
	
	
	
	@RequestMapping(value = "/frmMISLocationWiseReport", method = RequestMethod.GET)
	public ModelAndView frmOpenMISLocationWiseReport(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmMISLocationWiseReport");

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMISLocationWiseReport_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmMISLocationWiseReport", "command", new clsReportBean());
		}

	}

	@RequestMapping(value = "/rptMISLocWiseReport", method = RequestMethod.POST)
	private void funCallMISLocWiseReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		funShowMISLocWiseReport(objBean, resp, req);
	}

	@SuppressWarnings({ "unused", "unused", "unused", "unchecked" })
	private void funShowMISLocWiseReport(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		Connection con = objGlobalFunctions.funGetConnection(req);
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		if (objSetup == null) {
			objSetup = new clsPropertySetupModel();
		}

		String tempToLoc[] = objBean.getStrToLoc().split(",");
		String fromLoc = objBean.getStrFromLoc();
		// String tempSG[] = objBean.getStrSGCode().split(",");
		String strToLocCodes = "";
		String strFromLocCodes = "";

		for (int i = 0; i < tempToLoc.length; i++) {
			if (strToLocCodes.length() > 0) {
				strToLocCodes = strToLocCodes + " or a.strLocTo='" + tempToLoc[i] + "' ";
			} else {
				strToLocCodes = "a.strLocTo='" + tempToLoc[i] + "' ";

			}
		}
		ArrayList<String> locNameList = new ArrayList<String>();
		ArrayList<Object> hearderList = new ArrayList<Object>();

		hearderList.add("SGName");
		hearderList.add("Product");
		hearderList.add("UOM");
		for (int i = 0; i < tempToLoc.length; i++) {
			clsLocationMasterModel locModel = objLocationMasterService.funGetObject(tempToLoc[i], clientCode);
			locNameList.add(locModel.getStrLocName());
			hearderList.add("Qty " + locModel.getStrLocName());
			hearderList.add("Amt " + locModel.getStrLocName());
		}

		clsLocationMasterModel objlocModel = objLocationMasterService.funGetObject(fromLoc, clientCode);

		String reportName = servletContext.getRealPath("/WEB-INF/reports/rptMISLocWiseReport.jrxml");
		String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

		String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
		String propNameSql = "select a.strPropertyName  from "+webStockDB+".tblpropertymaster a where a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' ";
		List listPropName = objGlobalFunctionsService.funGetDataList(propNameSql, "sql");
		String propName = "";
		if (listPropName.size() > 0) {
			propName = listPropName.get(0).toString();
		}

		String fromDate = objBean.getDtFromDate();
		String toDate = objBean.getDtToDate();

		String fd = fromDate.split("-")[0];
		String fm = fromDate.split("-")[1];
		String fy = fromDate.split("-")[2];

		String td = toDate.split("-")[0];
		String tm = toDate.split("-")[1];
		String ty = toDate.split("-")[2];

		String dteFromDate = fy + "-" + fm + "-" + fd;
		String dteToDate = ty + "-" + tm + "-" + td;

		List listLOCWiseData = new ArrayList();
		ArrayList fieldList = new ArrayList();

		listLOCWiseData.add("rptMISLocationWiseReport_" + dteFromDate + "to" + dteToDate + "_" + userCode);

		String sqlQuery = " select  a.strLocTo,d.strLocName, b.strProdCode,c.strProdName,c.strIssueUOM,sum(b.dblQty),sum(b.dblTotalPrice) " + " from tblmishd a ,tblmisdtl b,tblproductmaster c ,tbllocationmaster d " + " where a.strMISCode=b.strMISCode  and a.strLocFrom='" + fromLoc + "' and b.strProdCode=c.strProdCode " + " and a.strLocTo=d.strLocCode  and date(a.dtMISDate) between '" + fromDate
				+ "' and '" + toDate + "' " + " and " + " ( " + strToLocCodes + ") "

				+ "  group by b.strProdCode,a.strLocTo  order by a.strLocTo,c.strProdName ";

		List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");
		for (int j = 0; j < listProdDtl.size(); j++) {
			clsReportBean objProdBean = new clsReportBean();
			Object[] prodArr = (Object[]) listProdDtl.get(j);

			objProdBean.setStrToLoc(prodArr[0].toString());
			objProdBean.setStrLocName(prodArr[1].toString());
			objProdBean.setStrProdCode(prodArr[2].toString());
			objProdBean.setStrProdName(prodArr[3].toString());
			objProdBean.setStrUOM(prodArr[4].toString());
			objProdBean.setDblQty(Double.parseDouble(prodArr[5].toString()));
			objProdBean.setDblAmt(Double.parseDouble(prodArr[6].toString()));

			fieldList.add(objProdBean);

		}

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
		hm.put("fromDate", dteFromDate);
		hm.put("toDate", dteToDate);
		hm.put("strFromLoc", objlocModel.getStrLocName());

		try {

			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
			JasperPrint print = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
			jprintlist.add(print);
			ServletOutputStream servletOutputStream = resp.getOutputStream();
			if (jprintlist.size() > 0) {
				if (objBean.getStrDocType().equals("PDF")) {
					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=rptMISLocWiseReport_" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".pdf");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();

				} else {
					JRExporter exporter = new JRXlsExporter();
					resp.setContentType("application/xlsx");
					exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=rptMISLocWiseReport_" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".xls");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				}

			} else {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.getWriter().append("No Record Found");

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@RequestMapping(value = "/frmPendingRecipe", method = RequestMethod.GET)
	public ModelAndView frmOpenPendingRecipeReport(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmPendingRecipe");

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPendingRecipe_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmPendingRecipe", "command", new clsReportBean());
		}

	}

	@RequestMapping(value = "/rptPendingRecipeList", method = RequestMethod.POST)
	private void funCallPendingReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		try {
			Connection con = objGlobalFunctions.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			String tempsubGroupCode[] = objBean.getStrSGCode().split(",");
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptPendingRecipeReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String strSGCode = "";
			for (int i = 0; i < tempsubGroupCode.length; i++) {
				if (strSGCode.length() > 0) {
					strSGCode = strSGCode + " or a.strSGCode='" + tempsubGroupCode[i] + "' ";
				} else {
					strSGCode = "a.strSGCode='" + tempsubGroupCode[i] + "' ";

				}
			}

			String sqlQuery = " select a.strProdName,b.strSGName,c.strLocName,a.dblCostRM,  a.strUOM ,a.strProdCode,  a.strLocCode,a.strSGCode " + " from tblproductmaster a , tblsubgroupmaster b ,tbllocationmaster c  where a.strProdType='Produced'  and a.strSGCode=b.strSGCode " + " and a.strLocCode=c.strLocCode and a.strClientCode='" + clientCode + "'  ";

			if (!strSGCode.equals("All")) {
				sqlQuery = sqlQuery + " and (" + strSGCode + " ) ";
			}

			sqlQuery = sqlQuery + " and a.strProdCode  not in ( select d.strParentCode from tblbommasterhd d where d.strClientCode='" + clientCode + "'   ) ";
			List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");
			ArrayList fieldList = new ArrayList();
			for (int j = 0; j < listProdDtl.size(); j++) {
				clsReportBean objProdBean = new clsReportBean();
				Object[] prodArr = (Object[]) listProdDtl.get(j);

				objProdBean.setStrProdName(prodArr[0].toString());
				objProdBean.setStrSGName(prodArr[1].toString());
				objProdBean.setStrLocName(prodArr[2].toString());
				objProdBean.setDblAmt(Double.parseDouble(prodArr[3].toString()));
				objProdBean.setStrUOM(prodArr[4].toString());
				objProdBean.setStrProdCode(prodArr[5].toString());

				fieldList.add(objProdBean);

			}

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

			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
			JasperPrint print = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
			jprintlist.add(print);
			ServletOutputStream servletOutputStream = resp.getOutputStream();
			if (jprintlist.size() > 0) {
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=rptPendingRecipeReport_" + userCode + ".pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
