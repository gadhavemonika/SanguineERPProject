package com.sanguine.crm.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
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
import com.sanguine.base.service.intfBaseService;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.crm.service.clsInvoiceHdService;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsInvoiceProfitReportController {

	@Autowired
	private clsInvoiceHdService objInvoiceHdService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobalfunction;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsProductMasterService objProductMasterService;

	@Autowired
	private clsInvoiceController objclsInvoiceController;
	
	@Autowired
	private intfBaseService objBaseService;

	@RequestMapping(value = "/frmInvoiceProfitReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmInvoiceProfitReport_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmInvoiceProfitReport", "command", new clsReportBean());
		}
	}

	@RequestMapping(value = "/rptInvoiceProfitReport", method = RequestMethod.POST)
	private void funReorderLevelReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		String type = objBean.getStrDocType();
		funCallInvoiceProfitReport(type, resp, req, objBean);

	}

	private void funCallInvoiceProfitReport(String type, HttpServletResponse resp, HttpServletRequest req, clsReportBean objBean) {
		
		Connection con = objGlobalfunction.funGetConnection(req);
		try {

			String startDate = req.getSession().getAttribute("startDate").toString();
			String[] sp = startDate.split(" ");
			String[] spDate = sp[0].split("/");
			startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
			String fromDate = objBean.getDteFromDate();
			String toDate = objBean.getDteToDate();
			String fDate = objGlobalfunction.funGetDate("yyyy-MM-dd", objBean.getDteFromDate());
			String tDate = objGlobalfunction.funGetDate("yyyy-MM-dd", objBean.getDteToDate());
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String locationCode = req.getSession().getAttribute("locationCode").toString();
			 String cmbCurrency = req.getSession().getAttribute("currencyCode").toString();
			double conversionRate=1;
			String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
			StringBuilder sbSql = new StringBuilder();
			sbSql.append("select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster where strCurrencyCode='"+cmbCurrency+"' and strClientCode='"+clientCode+"' ");
			try
			{
				List listConversion = objBaseService.funGetListModuleWise(sbSql, "sql","WebBooks");
				conversionRate=Double.parseDouble(listConversion.get(0).toString());
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			ArrayList fieldList = new ArrayList();
			ArrayList fieldList2 = new ArrayList();
			clsReportBean objProd1 = new clsReportBean();
			fieldList2.add(objProd1);
			String reportName="";
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHDQuery = " select b.strProdCode,c.strProdName,sum(b.dblQty),sum(b.dblUnitPrice) , " + " sum(b.dblQty)*sum(b.dblUnitPrice),count(b.strProdCode) " + "  from tblinvoicehd a,tblinvoicedtl b ,tblproductmaster c " + " where Date(a.dteInvDate) between '" + fDate + "' and '" + tDate + "'  " + " and a.strInvCode=b.strInvCode and a.strInvCode like '" + propertyCode + "%' "
					+ " and b.strProdCode=c.strProdCode and a.strClientCode='" + clientCode + "' group by b.strProdCode ";
						
			Map<Integer, List<String>> hmProductProfit = new HashMap<Integer,List<String>>();
			sbSql.setLength(0);
			sbSql.append("SELECT b.strProdCode,c.strProdName,c.dblCostRM,b.dblQty, b.dblUnitPrice, b.dblQty*b.dblUnitPrice,right(b.strProdCode,7) "
					+ " FROM tblinvoicehd a,tblinvoicedtl b,tblproductmaster c "
					+ " WHERE DATE(a.dteInvDate) BETWEEN '" + fDate + "' and '" + tDate + "' AND a.strInvCode=b.strInvCode "
					+ " AND b.strProdCode=c.strProdCode AND a.strClientCode='"+clientCode+"' "
					+ " order by b.strProdCode");
			List prodList = objGlobalFunctionsService.funGetList(sbSql.toString(), "sql");

			String prevProdCode="";
			int cnt=-1;
			
			List<clsReportBean> listProduct=new ArrayList<clsReportBean>(); 
			for(int i=0;i<prodList.size();i++)
			{
				Object[] objArr = (Object[]) prodList.get(i);
				String prodCode = objArr[0].toString();
				double qty = Double.parseDouble(objArr[3].toString());
				double purchaseAmt = (Double.parseDouble(objArr[2].toString())*qty)/conversionRate;
				double saleAmt = (Double.parseDouble(objArr[5].toString()))/conversionRate;
								
				clsReportBean objProd = new clsReportBean();
				if(prevProdCode.equals(objArr[0].toString()) )
				{
					objProd=listProduct.get(cnt);
					objProd.setDblQty(qty+objProd.getDblQty());
					objProd.setDblPurAmt(objProd.getDblPurAmt()+purchaseAmt);
					objProd.setDblSaleAmt(objProd.getDblSaleAmt()+saleAmt);
					System.out.println(prodCode+"      "+qty+"      "+purchaseAmt+"      "+saleAmt);
				}
				else
				{
					objProd.setStrProdCode(prodCode);
					objProd.setStrProdName(objArr[1].toString());
					objProd.setDblQty(qty);
					objProd.setDblPurAmt(purchaseAmt);
					objProd.setDblSaleAmt(saleAmt);
					listProduct.add(objProd);
					cnt++;
				}
				prevProdCode=prodCode;
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
			hm.put("fromDate", fromDate);
			hm.put("toDate", toDate);
			hm.put("listData", listProduct);
			
			if(type.equals("PDF")) 
			{
				reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptInvoiceProfitReport.jrxml");
			}
			else
			{
				reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptInvoiceProfitReportForExcel.jrxml");
			}

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			JasperPrint jp = JasperFillManager.fillReport(jr, hm, new JREmptyDataSource());
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			jprintlist.add(jp);
			ServletOutputStream servletOutputStream = resp.getOutputStream();
			
			if(type.equals("PDF")) {
				// if(objBean.getStrDocType().equals("PDF"))
				// {
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=rptInvoiceProfitReport" + fromDate + "_To_" + toDate + "" + userCode + ".pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();

			} else {
				JRExporter exporter = new JRXlsExporter();
				resp.setContentType("application/xlsx");
				exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=rptInvoiceProfitReport" + fromDate + "_To_" + toDate + "" + userCode + ".xls");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
				// }

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

}
