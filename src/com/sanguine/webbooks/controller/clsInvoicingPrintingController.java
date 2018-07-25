package com.sanguine.webbooks.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webbooks.bean.clsInvoicePrintingBean;

@Controller
public class clsInvoicingPrintingController {

	@Autowired
	private ServletContext servletContext;
	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@RequestMapping(value = "/frmInvoicingPrinting", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmInvoicingPrinting_1", "command", new clsInvoicePrintingBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmInvoicingPrinting", "command", new clsInvoicePrintingBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/rptInvoicePrinting", method = RequestMethod.GET)
	private void funReportInvoicingPrintingReport(@ModelAttribute("command") clsInvoicePrintingBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String strFromCatCode = objBean.getStrCatFormCode();
		String strToCatCode = objBean.getStrCatToCode();
		String innFromDate = objBean.getStrInnFromDate();
		String innToDate = objBean.getStrInnToDate();
		String innvFromNo = objBean.getStrInvoiceFormCode();
		String innvToNo = objBean.getStrInvoiceToCode();
		String fromAmt = objBean.getStrAmountForm();
		String toAmt = objBean.getStrAmountTo();

		// String showBOM = objBean.getStrShowBOM();
		if (objBean.getStrTFCat().equals("Total") && objBean.getStrTFDebtor().equals("Total")) {
			funCallReportInvoicingPrintingList(strFromCatCode, strToCatCode, innFromDate, innToDate, innvFromNo, innvToNo, fromAmt, toAmt, resp, req);
		} else {
			funCallReportParticulorInvoicingPrintingReport(strFromCatCode, strToCatCode, innFromDate, innToDate, innvFromNo, innvToNo, fromAmt, toAmt, resp, req);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallReportParticulorInvoicingPrintingReport(String strFromCatCode, String strToCatCode, String innFromDate, String innToDate, String innvFromNo, String innvToNo, String fromAmt, String toAmt, HttpServletResponse resp, HttpServletRequest req) {
		try {

			String type = "pdf";
			String Name = "";
			String address = "";
			String MemberShipNo = "";
			String Monthly = "";
			String BillNo = "";
			String BDate = "";

			String DrBal = "";
			String DrPeriod = "";
			String CrPeriod = "";
			String closeDRBal = "";
			String CRBal = "";

			String hd1 = "";
			String hd2 = "";
			String hd3 = "";
			String ft1 = "";
			String ft2 = "";
			String ft3 = "";

			objGlobal = new clsGlobalFunctions();
			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			String reportName = servletContext.getRealPath("/WEB-INF/reports/webbooks/rptInvoicePrinting.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHdFTData = "select a.strHdLine1,a.strHdLine2,a.strHdLine3,a.strFtLine1,a.strFtLine2,a.strFtLine3  " + " from tblinvprocesshd a " + " where a.strClientCode='" + clientCode + "' " + " and date(a.dteInvDate) between '" + innFromDate + "' and '" + innToDate + "'  ";

			List listHDFT = objGlobalFunctionsService.funGetDataList(sqlHdFTData, "sql");
			if (!listHDFT.isEmpty()) {
				Object[] arrObj = (Object[]) listHDFT.get(0);
				hd1 = arrObj[0].toString();
				hd2 = arrObj[1].toString();
				hd3 = arrObj[2].toString();

				ft1 = arrObj[3].toString();
				ft2 = arrObj[4].toString();
				ft3 = arrObj[5].toString();
			}

			String sqlHd = "select a.strDebtorName as Name, a.strAddress1 as add1 ,a.strAddress2 as add2 , a.strAddress3 as add3 ," + " a.strDebtorCode as MemberShipNo ,a.strMonth as Monthly,a.strInvNo as BillNo ," + " a.dteInvDate as BDate ,a.dblOpenBal as DrBal,a.dblTotalDr as DrPeriod," + " a.dblTotalCr as CrPeriod,dblClgBal as closeDRBal,dblOpenBalCr as CRBal " + " from tblInvDebtorHd a "
					+ " where a.strCategoryCode='" + strFromCatCode + "' " + " and a.strInvno ='" + innvFromNo + "'  " + " and a.dteInvDate between '" + innFromDate + "' and '" + innToDate + "' " + " and a.dblClgBal between '" + fromAmt + "' and '" + toAmt + "'  " + " and a.strClientCode='" + clientCode + "' ";

			//
			// "select a.strDebtorName as Name, a.strAddress1 as add1 ,a.strAddress2 as add2 , a.strAddress3 as add3 , "
			// +" a.strDebtorCode as MemberShipNo ,a.strMonth as Monthly,a.strInvNo as BillNo , a.dteInvDate as BDate  "
			// +" from tblInvDebtorHd a "
			// +" where a.strCategoryCode between '"+strFromCatCode+"' and '"+strToCatCode+"' "
			// +" and a.strInvno between '"+innvFromNo+"' and '"+innvToNo+"' "
			// +" and a.dteInvDate between '"+innFromDate+"' and '"+innToDate+"' "
			// +" and a.dblOpenBal between '"+fromAmt+"' and '"+toAmt+"' "
			// +" and a.strClientCode='"+clientCode+"' ";

			List list = objGlobalFunctionsService.funGetDataList(sqlHd, "sql");
			if (!list.isEmpty()) {
				Object[] arrObj = (Object[]) list.get(0);
				Name = arrObj[0].toString();
				address = arrObj[3].toString() + " , " + arrObj[2].toString() + " , " + arrObj[1].toString();
				MemberShipNo = arrObj[4].toString();
				Monthly = arrObj[5].toString();
				BillNo = arrObj[6].toString();
				BDate = arrObj[7].toString();

				DrBal = arrObj[7].toString();
				DrPeriod = arrObj[7].toString();
				CrPeriod = arrObj[7].toString();
				closeDRBal = arrObj[7].toString();
				CRBal = arrObj[7].toString();

			}
			String sqlDtlCr = "select Date(a.dtechqBillDate) as BillDate,a.strChqBillNo as BillNo,a.strParticulars as Particulars, a.dblAmt as Amt " + " from tblInvDebtorDtl a , tblinvdebtorhd b " + "where a.strCrDr='Cr' " + " and a.strClientCode='" + clientCode + "' and a.strInvno=b.strInvno and b.strInvno ='" + innvFromNo + "' " + " order by a.dtechqBillDate ";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery CrQuery = new JRDesignQuery();
			CrQuery.setText(sqlDtlCr);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset CrDataset = (JRDesignDataset) datasetMap.get("dsCredit");
			CrDataset.setQuery(CrQuery);

			String sqlDtlDr = "select Date(a.dtechqBillDate) as BillDate,a.strChqBillNo as BillNo,a.strParticulars as Particulars, a.dblAmt as Amt " + " from tblInvDebtorDtl a , tblinvdebtorhd b " + "where a.strCrDr='Dr' " + " and a.strClientCode='" + clientCode + "' and a.strInvno=b.strInvno and b.strInvno ='" + innvFromNo + "' " + " order by a.dtechqBillDate ";

			JRDesignQuery DrQuery = new JRDesignQuery();
			DrQuery.setText(sqlDtlDr);
			JRDesignDataset DrDataset = (JRDesignDataset) datasetMap.get("dsDebit");
			DrDataset.setQuery(DrQuery);

			String sqlSummaryData = "select a.strParticulars as Particulars, sum(a.dblAmt) as Amt  " + " from tblInvDebtorDtl a  " + " where a.strInvNo='" + innvFromNo + "' and a.strCrDr='Dr'  " + " group by a.strParticulars order by a.dtechqBillDate ";

			JRDesignQuery summData = new JRDesignQuery();
			summData.setText(sqlSummaryData);
			JRDesignDataset summDataset = (JRDesignDataset) datasetMap.get("dsDebitSummary");
			summDataset.setQuery(summData);

			JasperReport jr = JasperCompileManager.compileReport(jd);

			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);

			hm.put("strName", Name);
			hm.put("Address", address);
			hm.put("strMemberShipNo", MemberShipNo);
			hm.put("strMonth", Monthly);
			hm.put("strBillNo", BillNo);
			hm.put("Date", BDate);

			hm.put("Dbalance", DrBal);
			hm.put("DP", DrPeriod);
			hm.put("DB", CRBal);
			hm.put("CP", CrPeriod);
			hm.put("CBD", closeDRBal);
			hm.put("DueDate", BDate);

			hm.put("HD1", hd1);
			hm.put("HD2", hd2);
			hm.put("HD3", hd3);
			hm.put("FT1", ft1);
			hm.put("FT2", ft2);
			hm.put("FT3", ft3);

			// hm.put("strBAdd1",strBAdd1 );
			// hm.put("strBAdd2",strBAdd2);
			// hm.put("strBCity",strBCity );
			// hm.put("strBState",strBState);
			// hm.put("strBCountry",strBCountry );
			// hm.put("strBPin",strBPin );
			// hm.put("strNarration", strNarration);
			// hm.put("strSOCode",strSOCode );
			// hm.put("dteSODate",dteSODate );
			//

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
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptInvoicePrinting." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// for List Printing
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallReportInvoicingPrintingList(String strFromCatCode, String strToCatCode, String innFromDate, String innToDate, String innvFromNo, String innvToNo, String fromAmt, String toAmt, HttpServletResponse resp, HttpServletRequest req) {
		try {

			String type = "pdf";
			String hd1 = "";
			String hd2 = "";
			String hd3 = "";
			String ft1 = "";
			String ft2 = "";
			String ft3 = "";

			objGlobal = new clsGlobalFunctions();
			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			String reportName = servletContext.getRealPath("/WEB-INF/reports/webbooks/rptInvoicePrintingList.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHdData = "select a.strHdLine1,a.strHdLine2,a.strHdLine3,a.strFtLine1,a.strFtLine2,a.strFtLine3  " + " from tblinvprocesshd a " + " where a.strClientCode='" + clientCode + "' " + " and date(a.dteInvDate) between '" + innFromDate + "' and '" + innToDate + "'  ";

			List list = objGlobalFunctionsService.funGetDataList(sqlHdData, "sql");
			if (!list.isEmpty()) {
				Object[] arrObj = (Object[]) list.get(0);
				hd1 = arrObj[0].toString();
				hd2 = arrObj[1].toString();
				hd3 = arrObj[2].toString();

				ft1 = arrObj[3].toString();
				ft2 = arrObj[4].toString();
				ft3 = arrObj[5].toString();
			}

			String sql = "select b.strCrDr,a.strDebtorName, a.strAddress1 ,a.strAddress2  , a.strAddress3  , a.strDebtorCode  " + " ,a.strMonth ,a.strInvNo  , a.dteInvDate ,a.dblOpenBal ,a.dblTotalDr , " + " a.dblTotalCr ,dblClgBal ,dblOpenBalCr, " + " Date(b.dtechqBillDate) ,b.strChqBillNo ,b.strParticulars , b.dblAmt  " + " from tblInvDebtorHd a , tblInvDebtorDtl b  "
					+ " where a.strCategoryCode between '"
					+ strFromCatCode + "' and '" + strToCatCode + "' " + " and a.strInvno=b.strInvno  " + " and a.strInvno between '" + innvFromNo + "' and '" + innvToNo + "' " + " and a.dteInvDate  " + " between '" + innFromDate + "' and '" + innToDate + "' " + " and a.dblClgBal  " + " between '" + fromAmt + "' and '" + toAmt + "' " + " and a.strClientCode='" + clientCode + "' ";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sql);
			jd.setQuery(newQuery);

			JasperReport jr = JasperCompileManager.compileReport(jd);

			HashMap hm = new HashMap();
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);
			hm.put("HD1", hd1);
			hm.put("HD2", hd2);
			hm.put("HD3", hd3);
			hm.put("FT1", ft1);
			hm.put("FT2", ft2);
			hm.put("FT3", ft3);

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
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptInvoicePrintingList." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
