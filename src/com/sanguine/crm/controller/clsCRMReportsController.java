package com.sanguine.crm.controller;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.sanguine.bean.clsAvgConsumptionReportBean;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.crm.bean.clsProductionComPilationBean;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsGroupMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsSubGroupMasterService;
import com.sanguine.util.clsReportBean;

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

@Controller
public class clsCRMReportsController {
	@Autowired
	private clsGlobalFunctionsService objGlobalService;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	@Autowired
	private ServletContext servletContext;
	private clsGlobalFunctions objGlobal = null;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	private clsGroupMasterService objGrpMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsSubGroupMasterService objSubGroupMasterService;

	@RequestMapping(value = "/frmCategoryWiseSalesOrderReport", method = RequestMethod.GET)
	public ModelAndView funOpenCategoryWiseSalesOrderForm(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmCategoryWiseSalesOrderReport");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCategoryWiseSalesOrderReport_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmCategoryWiseSalesOrderReport", "command", new clsReportBean());
		}

	}

	@RequestMapping(value = "/frmCustomerWiseCategorySalesOrderReport", method = RequestMethod.GET)
	public ModelAndView funOpenCustomerWiseCategoryWiseSalesOrderForm(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmCustomerWiseCategorySalesOrderReport");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCustomerWiseCategorySalesOrderReport_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmCustomerWiseCategorySalesOrderReport", "command", new clsReportBean());
		}

	}

	/**
	 * Call Product Wise Supplier Wise Report
	 * 
	 * @param objBean
	 * @param resp
	 * @param req
	 */
	@RequestMapping(value = "/rptCategoryWiseSalesOrderReport", method = RequestMethod.POST)
	private void funCategoryWiseSalesOrderReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		// funCallCategoryWiseSalesOrderReport(objBean, resp, req);

		if (objBean.getStrReportType().equals("Normal Order")) {
			funCallProductionCompilationReport(objBean, resp, req);
		} else {
			funCallProductionCompilationAdvOrderReport(objBean, resp, req);
		}

	}

	@RequestMapping(value = "/rptCustomerWiseCategoryWiseSalesOrderReport", method = RequestMethod.POST)
	private void funCustomerWiseCategoryWiseSalesOrderReportReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		funCallCustomerWiseCategoryWiseSalesOrderReport(objBean, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	private void funCallCategoryWiseSalesOrderReport(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		try {

			String type = objBean.getStrDocType();

			Connection con = objGlobalFunctions.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}

			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptCategoryWiseSalesOrderReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			// group code
			String strGroupCodes = objBean.getStrGCode();
			String tempstrGCode[] = strGroupCodes.split(",");
			String strGCodes = "(";
			for (int i = 0; i < tempstrGCode.length; i++) {
				if (i == 0) {
					strGCodes = strGCodes + "'" + tempstrGCode[i] + "'";
				} else {
					strGCodes = strGCodes + ",'" + tempstrGCode[i] + "'";
				}
			}
			strGCodes += ")";

			// subgroup code
			String strSubGroupCodes = objBean.getStrSGCode();
			String tempstrSGCode[] = strSubGroupCodes.split(",");
			String strSGCodes = "(";
			for (int i = 0; i < tempstrSGCode.length; i++) {
				if (i == 0) {
					strSGCodes = strSGCodes + "'" + tempstrSGCode[i] + "'";
				} else {
					strSGCodes = strSGCodes + ",'" + tempstrSGCode[i] + "'";
				}
			}
			strSGCodes += ")";

			String sqlQuery = "select e.strGCode,d.strSGCode,c.strProdCode,c.strProdName,e.strGName,d.strSGName,sum(b.dblQty),sum(b.dblWeight*b.dblQty),sum(b.dblUnitPrice*b.dblQty) " + "from tblsalesorderhd a,tblsalesorderdtl b,tblproductmaster c,tblsubgroupmaster d,tblgroupmaster e " + "where a.strSOCode=b.strSOCode " + "and b.strProdCode=c.strProdCode " + "and c.strSGCode=d.strSGCode "
					+ "and d.strGCode=e.strGCode " + "and e.strGCode IN " + strGCodes + " ";
			if (objBean.getStrSGCode().length() > 0) {
				sqlQuery = sqlQuery + " and c.strSGCode IN " + strSGCodes + " ";
			} else {
				sqlQuery = sqlQuery + " and d.strSGCode=d.strSGCode ";
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

			sqlQuery = sqlQuery + "and date(a.dteFulmtDate) between '" + dteFromDate + "' and '" + dteToDate + "' " + "group by b.strProdCode,e.strGName,d.strSGName " + "order by b.strProdCode,e.strGName,d.strSGName ";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlQuery);
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
			hm.put("dteFromDate", objBean.getDteFromDate());
			hm.put("dteToDate", objBean.getDteToDate());

			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setContentLength(bytes.length);
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptCategoryWiseSalesOrderReport_" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".pdf");
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptCategoryWiseSalesOrderReport." + dteFromDate + " To " + dteToDate + "&" + userCode + ".xlsx");
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallCustomerWiseCategoryWiseSalesOrderReport(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		try {

			// Connection con = objGlobalFunctions.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}

			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptCustomerWiseCategoryWiseSalesOrderReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			// group code
			String strGroupCodes = objBean.getStrGCode();
			String tempstrGCode[] = strGroupCodes.split(",");
			String strGCodes = "(";
			for (int i = 0; i < tempstrGCode.length; i++) {
				if (i == 0) {
					strGCodes = strGCodes + "'" + tempstrGCode[i] + "'";
				} else {
					strGCodes = strGCodes + ",'" + tempstrGCode[i] + "'";
				}
			}
			strGCodes += ")";

			// subgroup code
			String strSubGroupCodes = objBean.getStrSGCode();
			String tempstrSGCode[] = strSubGroupCodes.split(",");
			String strSGCodes = "(";
			for (int i = 0; i < tempstrSGCode.length; i++) {
				if (i == 0) {
					strSGCodes = strSGCodes + "'" + tempstrSGCode[i] + "'";
				} else {
					strSGCodes = strSGCodes + ",'" + tempstrSGCode[i] + "'";
				}
			}
			strSGCodes += ")";

			ArrayList fieldList = new ArrayList();

			String sqlQuery = "select f.strPName,c.strProdCode,c.strProdName,e.strGName,d.strSGName,sum(b.dblAcceptQty),sum(b.dblWeight*b.dblAcceptQty),a.strSOCode " + "from tblsalesorderhd a,tblsalesorderdtl b,tblproductmaster c,tblsubgroupmaster d,tblgroupmaster e,tblpartymaster f " + "where a.strSOCode=b.strSOCode " + "and b.strProdCode=c.strProdCode " + "and c.strSGCode=d.strSGCode "
					+ "and d.strGCode=e.strGCode " + "and a.strCustCode=f.strPCode ";
			if (objBean.getStrDocCode() != null && objBean.getStrDocCode().length() > 0) {
				sqlQuery = sqlQuery + "and a.strCustCode='" + objBean.getStrDocCode() + "' ";
			} else {
				//
			}
			if (objBean.getStrSGCode().length() > 0) {
				sqlQuery = sqlQuery + " and d.strSGCode IN " + strSGCodes + " ";
			} else {
				sqlQuery = sqlQuery + " and d.strSGCode=d.strSGCode ";
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

			String fromFulDate = objBean.getDteFromFulfillment();
			String toFulDate = objBean.getDteToFulfillment();

			String ffd = fromFulDate.split("-")[0];
			String ffm = fromFulDate.split("-")[1];
			String ffy = fromFulDate.split("-")[2];

			String tfd = toFulDate.split("-")[0];
			String tfm = toFulDate.split("-")[1];
			String tfy = toFulDate.split("-")[2];

			String dteFromFulDate = ffy + "-" + ffm + "-" + ffd;
			String dteToFulDate = tfy + "-" + tfm + "-" + tfd;

			sqlQuery = sqlQuery + " and date(a.dteSODate) between '" + dteFromDate + "' and '" + dteToDate + "' " + " and date(a.dteFulmtDate) between '" + dteFromFulDate + "' and '" + dteToFulDate + "'   " + " group by e.strGName,d.strSGName,f.strPName,b.strProdCode " + " order by f.strPName; ";

			List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");

			for (int j = 0; j < listProdDtl.size(); j++) {
				clsProductionComPilationBean objProdCompilationBean = new clsProductionComPilationBean();
				Object[] prodArr = (Object[]) listProdDtl.get(j);
				{
					String prodCode = prodArr[1].toString();
					String soCode = prodArr[7].toString();
					objProdCompilationBean.setStrPName(prodArr[0].toString());
					objProdCompilationBean.setStrSGName(prodArr[4].toString());
					objProdCompilationBean.setStrProdCode(prodCode);
					objProdCompilationBean.setStrProdName(prodArr[2].toString());
					objProdCompilationBean.setDblAcceptQty(Double.parseDouble(prodArr[5].toString()));
					objProdCompilationBean.setDblWeight(Double.parseDouble(prodArr[6].toString()));

					String sql = " select strCharValue from tblsaleschar where strSOCode='" + soCode + "' and strProdCode ='" + prodCode + "' ";
					List listChar = objGlobalFunctionsService.funGetDataList(sql, "sql");
					String strChars = "";
					for (int k = 0; k < listChar.size(); k++) {
						strChars = strChars + listChar.get(k).toString() + "/";
					}
					objProdCompilationBean.setStrCharistics(strChars);

				}
				fieldList.add(objProdCompilationBean);
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

			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			if (jp != null) {
				jprintlist.add(jp);
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=rptCustomerWiseCategoryWiseSalesOrderReport_" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@SuppressWarnings({ "unused", "unused", "unused", "unchecked" })
	private void funCallProductionCompilationReport(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		Connection con = objGlobalFunctions.funGetConnection(req);
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		if (objSetup == null) {
			objSetup = new clsPropertySetupModel();
		}

		String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptCategoryWiseSalesOrderReport.jrxml");
		String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

		String propNameSql = "select a.strPropertyName  from dbwebmms.tblpropertymaster a where a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' ";
		List listPropName = objGlobalFunctionsService.funGetDataList(propNameSql, "sql");
		String propName = "";
		if (listPropName.size() > 0) {
			propName = listPropName.get(0).toString();
		}

		// group code
		String strGroupCodes = objBean.getStrGCode();
		String tempstrGCode[] = strGroupCodes.split(",");
		String strGCodes = "(";
		for (int i = 0; i < tempstrGCode.length; i++) {
			if (i == 0) {
				strGCodes = strGCodes + "'" + tempstrGCode[i] + "'";
			} else {
				strGCodes = strGCodes + ",'" + tempstrGCode[i] + "'";
			}
		}
		strGCodes += ")";

		// subgroup code
		String strSubGroupCodes = objBean.getStrSGCode();
		String tempstrSGCode[] = strSubGroupCodes.split(",");
		String strSGCodes = "(";
		for (int i = 0; i < tempstrSGCode.length; i++) {
			if (i == 0) {
				strSGCodes = strSGCodes + "'" + tempstrSGCode[i] + "'";
			} else {
				strSGCodes = strSGCodes + ",'" + tempstrSGCode[i] + "'";
			}
		}
		strSGCodes += ")";

		ArrayList fieldList = new ArrayList();

		String sqlQuery = "select d.strSGName,c.strProdCode,c.strProdName,sum(b.dblAcceptQty),sum(b.dblWeight*b.dblAcceptQty),a.strSOCode,d.strSGCode " + "from tblsalesorderhd a,tblsalesorderdtl b,tblproductmaster c,tblsubgroupmaster d,tblgroupmaster e " + "where a.strSOCode=b.strSOCode "
		// + " and a.strCloseSO='Y' "
				+ "and b.strProdCode=c.strProdCode " + "and c.strSGCode=d.strSGCode " + "and d.strGCode=e.strGCode " + "and e.strGCode IN " + strGCodes + " ";
		if (objBean.getStrSGCode().length() > 0) {
			sqlQuery = sqlQuery + " and c.strSGCode IN " + strSGCodes + " ";
		} else {
			sqlQuery = sqlQuery + " and d.strSGCode=d.strSGCode ";
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

		String fromFulDate = objBean.getDteFromFulfillment();
		String toFulDate = objBean.getDteToFulfillment();

		String ffd = fromFulDate.split("-")[0];
		String ffm = fromFulDate.split("-")[1];
		String ffy = fromFulDate.split("-")[2];

		String tfd = toFulDate.split("-")[0];
		String tfm = toFulDate.split("-")[1];
		String tfy = toFulDate.split("-")[2];

		String dteFromFulDate = ffy + "-" + ffm + "-" + ffd;
		String dteToFulDate = tfy + "-" + tfm + "-" + tfd;

		sqlQuery = sqlQuery + "and date(a.dteSODate) between '" + dteFromDate + "' and '" + dteToDate + "' " + " and date(a.dteFulmtDate) between '" + dteFromFulDate + "' and '" + dteToFulDate + "' " + " group by b.strProdCode,e.strGName,d.strSGName " + "order by b.strProdCode,e.strGName,d.strSGName ";

		List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");

		for (int j = 0; j < listProdDtl.size(); j++) {
			clsProductionComPilationBean objProdCompilationBean = new clsProductionComPilationBean();
			Object[] prodArr = (Object[]) listProdDtl.get(j);
			{
				String prodCode = prodArr[1].toString();
				String soCode = prodArr[5].toString();
				objProdCompilationBean.setStrSGName(prodArr[0].toString());
				objProdCompilationBean.setStrProdCode(prodCode);
				objProdCompilationBean.setStrProdName(prodArr[2].toString());
				objProdCompilationBean.setDblAcceptQty(Double.parseDouble(prodArr[3].toString()));
				objProdCompilationBean.setDblWeight(Double.parseDouble(prodArr[4].toString()));
				objProdCompilationBean.setStrSGCode(prodArr[6].toString());
				String sql = " select strCharValue from tblsaleschar where strSOCode='" + soCode + "' and strProdCode ='" + prodCode + "' ";
				List listChar = objGlobalFunctionsService.funGetDataList(sql, "sql");
				String strChars = "";
				for (int k = 0; k < listChar.size(); k++) {
					strChars = strChars + listChar.get(k).toString() + "/";
				}
				objProdCompilationBean.setStrCharistics(strChars);

			}
			fieldList.add(objProdCompilationBean);
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

		try {
			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			if (jp != null) {
				jprintlist.add(jp);
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=rptCategoryWiseSalesOrderReport" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private void funCallProductionCompilationAdvOrderReport(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		String type = objBean.getStrDocType();

		Connection con = objGlobalFunctions.funGetConnection(req);
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		if (objSetup == null) {
			objSetup = new clsPropertySetupModel();
		}

		String strReportType = objBean.getStrReportType();
		String suppName = "";

		String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptCategoryWiseSalesOrderAdvOrderReport.jrxml");
		String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

		String propNameSql = "select a.strPropertyName  from dbwebmms.tblpropertymaster a where a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' ";
		List listPropName = objGlobalFunctionsService.funGetDataList(propNameSql, "sql");
		String propName = "";
		if (listPropName.size() > 0) {
			propName = listPropName.get(0).toString();
		}

		// group code
		String strGroupCodes = objBean.getStrGCode();
		String tempstrGCode[] = strGroupCodes.split(",");
		String strGCodes = "(";
		for (int i = 0; i < tempstrGCode.length; i++) {
			if (i == 0) {
				strGCodes = strGCodes + "'" + tempstrGCode[i] + "'";
			} else {
				strGCodes = strGCodes + ",'" + tempstrGCode[i] + "'";
			}
		}
		strGCodes += ")";

		// subgroup code
		String strSubGroupCodes = objBean.getStrSGCode();
		String tempstrSGCode[] = strSubGroupCodes.split(",");
		String strSGCodes = "(";
		for (int i = 0; i < tempstrSGCode.length; i++) {
			if (i == 0) {
				strSGCodes = strSGCodes + "'" + tempstrSGCode[i] + "'";
			} else {
				strSGCodes = strSGCodes + ",'" + tempstrSGCode[i] + "'";
			}
		}
		strSGCodes += ")";

		ArrayList fieldList = new ArrayList();

		String sqlQuery = "select d.strSGName,c.strProdCode,c.strProdName,sum(b.dblQty),sum(b.dblWeight*b.dblQty),a.strSOCode,f.strCharCode,f.strCharValue,g.strCharName " + " from tblsalesorderhd a,tblsalesorderdtl b,tblproductmaster c,tblsubgroupmaster d,tblgroupmaster e,tblsaleschar f ,tblcharacteristics g " + "where a.strSOCode=b.strSOCode "
		// + " and a.strCloseSO='Y' "
				+ " and a.strSOCode=f.strSOCode " + " and b.strProdCode=f.strProdCode " + " and a.strStatus='" + strReportType + "'  " + " and b.strProdCode=c.strProdCode " + " and c.strSGCode=d.strSGCode " + " and d.strGCode=e.strGCode  and g.strCharCode=f.strCharCode " + " and e.strGCode IN " + strGCodes + " ";
		if (objBean.getStrSGCode().length() > 0) {
			sqlQuery = sqlQuery + " and c.strSGCode IN " + strSGCodes + " ";
		} else {
			sqlQuery = sqlQuery + " and d.strSGCode=d.strSGCode ";
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

		String fromFulDate = objBean.getDteFromFulfillment();
		String toFulDate = objBean.getDteToFulfillment();

		String ffd = fromFulDate.split("-")[0];
		String ffm = fromFulDate.split("-")[1];
		String ffy = fromFulDate.split("-")[2];

		String tfd = toFulDate.split("-")[0];
		String tfm = toFulDate.split("-")[1];
		String tfy = toFulDate.split("-")[2];

		String dteFromFulDate = ffy + "-" + ffm + "-" + ffd;
		String dteToFulDate = tfy + "-" + tfm + "-" + tfd;

		sqlQuery = sqlQuery + " and date(a.dteSODate) between '" + dteFromDate + "' and '" + dteToDate + "' " + " and date(a.dteFulmtDate) between '" + dteFromFulDate + "' and '" + dteToFulDate + "' " + " group by f.strCharCode,b.strProdCode,d.strSGName,a.strSOCode " + "order by a.strSOCode ";

		List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");

		for (int j = 0; j < listProdDtl.size(); j++) {
			clsProductionComPilationBean objProdCompilationBean = new clsProductionComPilationBean();
			Object[] prodArr = (Object[]) listProdDtl.get(j);
			{
				String prodCode = prodArr[1].toString();
				String soCode = prodArr[5].toString();
				objProdCompilationBean.setStrSGName(prodArr[0].toString());
				objProdCompilationBean.setStrProdCode(prodCode);
				objProdCompilationBean.setStrProdName(prodArr[2].toString());
				objProdCompilationBean.setDblQty(Double.parseDouble(prodArr[3].toString()));
				objProdCompilationBean.setDblWeight(Double.parseDouble(prodArr[4].toString()));

				// String sql
				// =" select strCharValue from tblsaleschar where strSOCode='"+soCode+"' and strProdCode ='"+prodCode+"' ";
				// List listChar =
				// objGlobalFunctionsService.funGetDataList(sql,"sql");
				// String strChars="";
				// for (int k = 0; k < listChar.size(); k++)
				// {
				// strChars=strChars+listChar.get(k).toString()+"/";
				// }
				// objProdCompilationBean.setStrCharistics(strChars);

				objProdCompilationBean.setStrCharCode(prodArr[8].toString()); // for
																				// lable
																				// of
																				// charistic
				objProdCompilationBean.setStrCharistics(prodArr[7].toString());
				objProdCompilationBean.setStrSOCode(soCode);

			}
			fieldList.add(objProdCompilationBean);
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
		hm.put("orderType", strReportType);

		try {
			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			if (jp != null) {
				jprintlist.add(jp);
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=rptCategoryWiseSalesOrderAdvOrderReport_" + dteFromDate + "_To_" + dteToDate + "" + userCode + ".pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@RequestMapping(value = "/frmCustomerWiseLocationWiseSOReport", method = RequestMethod.GET)
	public ModelAndView funOpenCustomerWiseLocationWiseSalesOrderForm(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmCustomerWiseLocationWiseSOReport");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCustomerWiseLocationWiseSOReport_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmCustomerWiseLocationWiseSOReport", "command", new clsReportBean());
		}

	}

	@RequestMapping(value = "/rptCustomerWiseLocationWiseSO", method = RequestMethod.POST)
	private void funCustomerWiseLocationWiseSOReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		funCallCustomerWiseLocationWiseSOReport(objBean, resp, req);

	}

	private void funCallCustomerWiseLocationWiseSOReport(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		String type = objBean.getStrDocType();

		Connection con = objGlobalFunctions.funGetConnection(req);
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		if (objSetup == null) {
			objSetup = new clsPropertySetupModel();
		}
		String suppName = "";

		String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptCustomerWiseLocationWiseSOReport.jrxml");
		String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

		String propNameSql = "select a.strPropertyName  from dbwebmms.tblpropertymaster a where a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' ";
		List listPropName = objGlobalFunctionsService.funGetDataList(propNameSql, "sql");
		String propName = "";
		if (listPropName.size() > 0) {
			propName = listPropName.get(0).toString();
		}

		// Customer code
		String strCustCodes = objBean.getStrSuppCode();
		String tempstrCCode[] = strCustCodes.split(",");
		String strCCodes = "(";
		for (int i = 0; i < tempstrCCode.length; i++) {
			if (i == 0) {
				strCCodes = strCCodes + "'" + tempstrCCode[i] + "'";
			} else {
				strCCodes = strCCodes + ",'" + tempstrCCode[i] + "'";
			}
		}
		strCCodes += ")";

		// LocCode code
		String strLocCodes = objBean.getStrLocationCode();
		String tempstrLocCode[] = strLocCodes.split(",");
		String locCodes = "(";
		for (int i = 0; i < tempstrLocCode.length; i++) {
			if (i == 0) {
				locCodes = locCodes + "'" + tempstrLocCode[i] + "'";
			} else {
				locCodes = locCodes + ",'" + tempstrLocCode[i] + "'";
			}
		}
		locCodes += ")";

		ArrayList fieldList = new ArrayList();

		String sqlQuery = " SELECT a.strCustCode,f.strPName,e.strLocCode,g.strLocName,d.strProdCode,e.strProdName," + " d.dblAcceptQty as dblQty,d.dblWeight FROM tblsalesorderhd a ,tblsalesorderdtl d  ,tblproductmaster e," + "  tblpartymaster f, tbllocationmaster g " + " WHERE a.strSOCode=d.strSOCode " + " and d.strProdCode=e.strProdCode " + " and e.strLocCode=g.strLocCode "
		// + " AND a.strCloseSO='Y' "
				+ " AND f.strPCode=a.strCustCode " + " AND a.strClientCode='" + clientCode + "' " + " AND e.strClientCode='" + clientCode + "' " + " AND d.strClientCode='" + clientCode + "' " + " AND f.strClientCode='" + clientCode + "' " + " AND g.strClientCode='" + clientCode + "' " + " and e.strLocCode IN " + locCodes + " " + " and a.strCustCode IN " + strCCodes + " ";

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

		String fromFulDate = objBean.getDteFromFulfillment();
		String toFulDate = objBean.getDteToFulfillment();

		String ffd = fromFulDate.split("-")[0];
		String ffm = fromFulDate.split("-")[1];
		String ffy = fromFulDate.split("-")[2];

		String tfd = toFulDate.split("-")[0];
		String tfm = toFulDate.split("-")[1];
		String tfy = toFulDate.split("-")[2];

		String dteFromFulDate = ffy + "-" + ffm + "-" + ffd;
		String dteToFulDate = tfy + "-" + tfm + "-" + tfd;

		sqlQuery = sqlQuery + " and date(a.dteSODate) between '" + dteFromDate + "' and '" + dteToDate + "' " + " and date(a.dteFulmtDate) between '" + dteFromFulDate + "' and '" + dteToFulDate + "' " + " GROUP BY a.strCustCode,d.strProdCode   ";

		List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");

		// for (int j = 0; j < listProdDtl.size(); j++)
		// {
		// clsCustomerWiseLocationWiseSOBean objCustWiseLocwiseSO = new
		// clsCustomerWiseLocationWiseSOBean();
		// Object[] prodArr = (Object[]) listProdDtl.get(j);
		// {
		//
		// objCustWiseLocwiseSO.setStrCustCode(prodArr[0].toString());
		// objCustWiseLocwiseSO.setStrCustName(prodArr[1].toString());
		// objCustWiseLocwiseSO.setStrLocCode(prodArr[2].toString());
		// objCustWiseLocwiseSO.setStrLocName(prodArr[3].toString());
		// objCustWiseLocwiseSO.setStrProdCode(prodArr[4].toString());
		// objCustWiseLocwiseSO.setStrProdName(prodArr[5].toString());
		// objCustWiseLocwiseSO.setDblQty(Double.parseDouble(prodArr[6].toString()));
		// objCustWiseLocwiseSO.setDblWeight(Double.parseDouble(prodArr[7].toString()));
		//
		// }
		// fieldList.add(objCustWiseLocwiseSO);
		// }
		//
		// HashMap hm = new HashMap();
		// hm.put("strCompanyName", companyName);
		// hm.put("strUserCode", userCode);
		// hm.put("strImagePath", imagePath);
		// hm.put("strAddr1", objSetup.getStrAdd1());
		// hm.put("strAddr2", objSetup.getStrAdd2());
		// hm.put("strCity", objSetup.getStrCity());
		// hm.put("strState", objSetup.getStrState());
		// hm.put("strCountry", objSetup.getStrCountry());
		// hm.put("strPin", objSetup.getStrPin());
		// hm.put("dteFromDate", objBean.getDteFromDate());
		// hm.put("dteToDate", objBean.getDteToDate());
		//
		// try
		// {
		// JRDataSource beanCollectionDataSource = new
		// JRBeanCollectionDataSource(fieldList);
		// JasperDesign jd = JRXmlLoader.load(reportName);
		// JasperReport jr = JasperCompileManager.compileReport(jd);
		// JasperPrint jp = JasperFillManager.fillReport(jr, hm,
		// beanCollectionDataSource);
		// List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
		// if (jp != null)
		// {
		// jprintlist.add(jp);
		// ServletOutputStream servletOutputStream = resp.getOutputStream();
		// JRExporter exporter = new JRPdfExporter();
		// resp.setContentType("application/pdf");
		// exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST,
		// jprintlist);
		// exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM,
		// servletOutputStream);
		// exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS,
		// Boolean.TRUE);
		// resp.setHeader("Content-Disposition",
		// "inline;filename=rptCategoryWiseSOAdvOrderReport.pdf");
		// exporter.exportReport();
		// servletOutputStream.flush();
		// servletOutputStream.close();
		//
		// }
		//
		// }catch(Exception ex)
		// {
		// ex.printStackTrace();
		// }
		try {
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlQuery);
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
			hm.put("dteFromDate", dteFromFulDate);
			hm.put("dteToDate", dteToFulDate);

			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setContentLength(bytes.length);
				resp.setHeader("Content-Disposition", "inline;filename=rptCustomerWiseLocationWiseSOReport_" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".pdf");
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptCustomerWiseLocationWiseSOReport_" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".xlsx");
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/frmPendingCustomerList", method = RequestMethod.GET)
	public ModelAndView funOpenPendingCustomerListForm(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmPendingCustomerList");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPendingCustomerList_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmPendingCustomerList", "command", new clsReportBean());
		}

	}

	@RequestMapping(value = "/rptPendingCustomerList", method = RequestMethod.GET)
	private void funPendingCustomerList(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String dteFullfillment = objBean.getDteToFulfillment();
		String type = objBean.getStrDocType();
		funCallPendingCustomerList(dteFullfillment, type, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallPendingCustomerList(String dteFullfillment, String type, HttpServletResponse resp, HttpServletRequest req) {
		try {
			objGlobal = new clsGlobalFunctions();
			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);

			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptPendingCustomerList.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHDQuery = " select 1 from dual; ";

			String sqlDtlQuery = " SELECT '' as SrNo, a.strPCode,a.strPName,a.strPhone,a.strMobile  from tblpartymaster a  where " + "  a.strPType='cust' and a.strManualCode<> ''  and a.strClientCode='" + clientCode + "'   and a.strPCode  " + "NOT IN(select strCustCode from tblsalesorderhd where Date(dteFulmtDate)='" + dteFullfillment + "' ) ";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlHDQuery);
			jd.setQuery(newQuery);

			JRDesignQuery subQuery = new JRDesignQuery();

			subQuery.setText(sqlDtlQuery);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsCustList");
			subDataset.setQuery(subQuery);

			JasperReport jr = JasperCompileManager.compileReport(jd);
			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);
			System.out.println(imagePath);
			hm.put("strAddr1", objSetup.getStrAdd1());
			hm.put("strAddr2", objSetup.getStrAdd2());
			hm.put("strCity", objSetup.getStrCity());
			hm.put("strState", objSetup.getStrState());
			hm.put("strCountry", objSetup.getStrCountry());
			hm.put("strPin", objSetup.getStrPin());
			hm.put("dteFullfillment", objGlobal.funGetDate("dd-MM-yyyy", dteFullfillment));
			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setContentLength(bytes.length);
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptPendingCustomerList_" + dteFullfillment + "_" + userCode + ".pdf");
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRPdfExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptPendingCustomerList_" + dteFullfillment + "_" + userCode + ".xlsx");
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/frmProductPriceList", method = RequestMethod.GET)
	public ModelAndView funOpenProductPriceListForm(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmPriceList");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmProductPriceList_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmProductPriceList", "command", new clsReportBean());
		}

	}

	@RequestMapping(value = "/rptProductPriceList", method = RequestMethod.GET)
	private void funPriceList(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		String type = objBean.getStrDocType();
		funCallPriceList(type, resp, req);

	}

	private void funCallPriceList(String type, HttpServletResponse resp, HttpServletRequest req) {
		try {
			objGlobal = new clsGlobalFunctions();
			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);

			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptProductPriceList.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHDQuery = " select 1 from dual; ";

			String sqlDtlQuery = "SELECT '' as SrNo, b.strSGCode,b.strSGName,a.strProdCode,a.strProdName,a.dblMRP " + " from tblproductmaster a,tblsubgroupmaster b " + " where a.strSGCode=b.strSGCode and a.strProdType<> 'Procured' " + " and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + clientCode + "' " + " order by b.strSGCode; ";
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlHDQuery);
			jd.setQuery(newQuery);

			JRDesignQuery subQuery = new JRDesignQuery();

			subQuery.setText(sqlDtlQuery);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsPriceList");
			subDataset.setQuery(subQuery);

			JasperReport jr = JasperCompileManager.compileReport(jd);
			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);
			System.out.println(imagePath);
			hm.put("strAddr1", objSetup.getStrAdd1());
			hm.put("strAddr2", objSetup.getStrAdd2());
			hm.put("strCity", objSetup.getStrCity());
			hm.put("strState", objSetup.getStrState());
			hm.put("strCountry", objSetup.getStrCountry());
			hm.put("strPin", objSetup.getStrPin());
			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setContentLength(bytes.length);
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptProductPriceList." + type.trim());
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRPdfExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptProductPriceList." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/rptProductExcelPriceList", method = RequestMethod.GET)
	public ModelAndView funLoadExcelPriceList(String type, HttpServletResponse resp, HttpServletRequest req) {
		List ExportList = new ArrayList();
		try {

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();

			List<String> listHeader = new ArrayList<String>();
			listHeader.add("Sr.no");
			listHeader.add("Sub Group Code");
			listHeader.add("Sub Group Name");
			listHeader.add("Product Code");
			listHeader.add("Product Name");
			listHeader.add("Price");

			String[] ExcelHeader = new String[listHeader.size()];
			ExcelHeader = listHeader.toArray(ExcelHeader);
			ExportList.add(ExcelHeader);

			List listProductDtl = new ArrayList<>();

			String sqlDtlQuery = "SELECT '' as SrNo, b.strSGCode,b.strSGName,a.strProdCode,a.strProdName,a.dblMRP " + " from tblproductmaster a,tblsubgroupmaster b " + " where a.strSGCode=b.strSGCode and a.strProdType<> 'Procured' " + " and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + clientCode + "' " + " order by b.strSGCode; ";
			List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlDtlQuery, "sql");
			int cnt = 1;
			for (int rowCount = 0; rowCount < listProdDtl.size(); rowCount++) {
				Object[] obj = (Object[]) listProdDtl.get(rowCount);

				List listobj = new ArrayList<>();
				listobj.add(cnt);
				listobj.add(obj[1]);
				listobj.add(obj[2]);
				listobj.add(obj[3]);
				listobj.add(obj[4]);
				listobj.add(obj[5]);
				listProductDtl.add(listobj);
				cnt++;
			}
			ExportList.add(listProductDtl);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("excelView", "stocklist", ExportList);
	}

	@RequestMapping(value = "/frmLocationwiseCategorywiseSOReport", method = RequestMethod.GET)
	public ModelAndView funOpenLocationWiseCategoryWiseSalesOrderForm(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmLocationwiseCategorywiseSOReport");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmLocationwiseCategorywiseSOReport_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmLocationwiseCategorywiseSOReport", "command", new clsReportBean());
		}

	}

	@RequestMapping(value = "/rptLocationWiseCategoryWiseSOReport", method = RequestMethod.POST)
	private void funLocationwiseCategoryWiseSO(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		String type = objBean.getStrDocType();
		funCallLocationWiseCategoryWiseSOReport(objBean, resp, req);
	}

	private void funCallLocationWiseCategoryWiseSOReport(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		String type = objBean.getStrDocType();

		Connection con = objGlobalFunctions.funGetConnection(req);
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		if (objSetup == null) {
			objSetup = new clsPropertySetupModel();
		}
		String suppName = "";

		String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptLocationwiseCategorywiseSOReport.jrxml");
		String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

		String propNameSql = "select a.strPropertyName  from dbwebmms.tblpropertymaster a where a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' ";
		List listPropName = objGlobalFunctionsService.funGetDataList(propNameSql, "sql");
		String propName = "";
		if (listPropName.size() > 0) {
			propName = listPropName.get(0).toString();
		}

		// Customer code
		String strSGCode = objBean.getStrSGCode();
		String tempstrSGCode[] = strSGCode.split(",");
		String strSGCodes = "(";
		for (int i = 0; i < tempstrSGCode.length; i++) {
			if (i == 0) {
				strSGCodes = strSGCodes + "'" + tempstrSGCode[i] + "'";
			} else {
				strSGCodes = strSGCodes + ",'" + tempstrSGCode[i] + "'";
			}
		}
		strSGCodes += ")";

		// LocCode code
		String strLocCodes = objBean.getStrLocationCode();
		String tempstrLocCode[] = strLocCodes.split(",");
		String locCodes = "(";
		for (int i = 0; i < tempstrLocCode.length; i++) {
			if (i == 0) {
				locCodes = locCodes + "'" + tempstrLocCode[i] + "'";
			} else {
				locCodes = locCodes + ",'" + tempstrLocCode[i] + "'";
			}
		}
		locCodes += ")";

		ArrayList fieldList = new ArrayList();

		String sqlQuery = " SELECT h.strSGName,f.strPName,c.strLocCode,g.strLocName,d.strProdCode,e.strProdName," + "d.dblQty,d.dblWeight" + " FROM tblproductionorderhd c,tblproductionorderdtl d, tblproductmaster e,tblpartymaster f," + " tbllocationmaster g,tblSubgroupmaster h " + "WHERE c.strOPCode=d.strOPCode " + "AND d.strProdCode=e.strProdCode " + "AND e.strSGCode=h.strSGCode "
				+ "AND g.strLocCode=c.strLocCode " + "AND e.strClientCode='" + clientCode + "' " + "AND c.strClientCode='" + clientCode + "' " + "AND d.strClientCode='" + clientCode + "' " + "AND f.strClientCode='" + clientCode + "' " + "AND g.strClientCode='" + clientCode + "' " + " and c.strLocCode IN " + locCodes + " " + " and e.strSGCode IN " + strSGCodes + " ";

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

		sqlQuery = sqlQuery + "and date(c.dtfulfilled) between '" + dteFromDate + "' and '" + dteToDate + "' group by d.strProdCode,c.strLocCode   ";

		List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");

		try {
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlQuery);
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
			hm.put("dteFromDate", dteFromDate);
			hm.put("dteToDate", dteToDate);

			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setContentLength(bytes.length);
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptLocationwiseCategorywiseSOReport_" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".pdf");
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptLocationwiseCategorywiseSOReport_" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".xlsx");
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/frmSOCustomersList", method = RequestMethod.GET)
	public ModelAndView funOpenSOCustomersListForm(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmSOCustomersList");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSOCustomersList_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmSOCustomersList", "command", new clsReportBean());
		}

	}

	@RequestMapping(value = "/rptSOCustomersList", method = RequestMethod.GET)
	private void funSOCustomersList(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String dteFromDate = objBean.getDteFromDate();
		String dteToDate = objBean.getDteToDate();
		String type = objBean.getStrDocType();
		funSOCustomersList(dteFromDate, dteToDate, type, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funSOCustomersList(String dteFromDate, String dteToDate, String type, HttpServletResponse resp, HttpServletRequest req) {
		try {
			objGlobal = new clsGlobalFunctions();
			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);

			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptSOCustomersList.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHDQuery = " select 1 from dual; ";

			String sqlDtlQuery = "SELECT '' as SrNo, a.strCustCode,b.strPName ,a.strSOCode,c.dblQty " + " from tblsalesorderhd a,tblpartymaster b,tblsalesorderdtl c  " + " where a.strCustCode=b.strPCode and a.strSOCode=c.strSOCode and date(a.dteFulmtDate) " + " between '" + dteFromDate + "' and '" + dteToDate + "' " + " and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + clientCode + "' ";
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlHDQuery);
			jd.setQuery(newQuery);

			JRDesignQuery subQuery = new JRDesignQuery();

			subQuery.setText(sqlDtlQuery);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsCustList");
			subDataset.setQuery(subQuery);

			JasperReport jr = JasperCompileManager.compileReport(jd);
			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);
			System.out.println(imagePath);
			hm.put("strAddr1", objSetup.getStrAdd1());
			hm.put("strAddr2", objSetup.getStrAdd2());
			hm.put("strCity", objSetup.getStrCity());
			hm.put("strState", objSetup.getStrState());
			hm.put("strCountry", objSetup.getStrCountry());
			hm.put("strPin", objSetup.getStrPin());
			hm.put("dteFromDate", objGlobal.funGetDate("dd-MM-yyyy", dteFromDate));
			hm.put("dteToDate", objGlobal.funGetDate("dd-MM-yyyy", dteToDate));
			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setContentLength(bytes.length);
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptSOCustomersList_" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".pdf");
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRPdfExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptSOCustomersList_" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".xlsx");
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/frmShopOrderList", method = RequestMethod.GET)
	public ModelAndView funOpenSOVarienceListForm(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmShopOrderList");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmShopOrderList_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmShopOrderList", "command", new clsReportBean());
		}

	}

	@RequestMapping(value = "/rptConsolidateCustomerWiseSalesOrder", method = RequestMethod.GET)
	public void funOpenInvoiceReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		String date = objBean.getDteFromDate();

		String type = objBean.getStrDocType();
		funCallReport(date, type, resp, req);

	}

	public void funCallReport(String date, String type, HttpServletResponse resp, HttpServletRequest req) {
		try {
			objGlobal = new clsGlobalFunctions();
			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			HashMap hm = new HashMap();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);

			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptConsolidateCustomerWiseSalesOrder.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHDQuery = " select 1 from dual; ";

			String sqlDtlQuery = "select b.strSGName,a.strProdName,c.strPName,e.dblAcceptQty " + " from tblproductmaster a,tblsubgroupmaster b,tblpartymaster c,tblsalesorderhd d,tblsalesorderdtl e  " + " where d.strSOCode=e.strSOCode and  e.strProdCode=a.strProdCode and a.strSGCode=b.strSGCode and d.strCustCode=c.strPCode " + " and d.strClientCode='" + clientCode + "' and e.strClientCode='"
					+ clientCode + "' and a.strClientCode ='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + " and c.strClientCode='" + clientCode + "' and d.dteSODate='" + date + "' " + " order by a.strSGCode,a.strProdCode ";
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlHDQuery);
			jd.setQuery(newQuery);

			JRDesignQuery subQuery = new JRDesignQuery();

			subQuery.setText(sqlDtlQuery);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("crossTab");
			subDataset.setQuery(subQuery);

			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setContentLength(bytes.length);
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptConsolidateCustomerWiseSalesOrder_" + date + "_" + userCode + ".pdf");
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRPdfExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptConsolidateCustomerWiseSalesOrder_" + date + "_" + userCode + ".xlsx");
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
