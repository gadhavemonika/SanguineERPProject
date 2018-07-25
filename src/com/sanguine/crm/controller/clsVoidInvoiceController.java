package com.sanguine.crm.controller;

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

import com.sanguine.crm.model.clsInvoiceModelDtl;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webpms.bean.clsVoidBillBean;

@Controller
public class clsVoidInvoiceController 
{

	@Autowired
	private clsSetupMasterService objSetupMasterService;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	
	
	@RequestMapping(value = "/frmVoidInvoiceReport", method = RequestMethod.GET)
	public ModelAndView funOpenReportForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmVoidInvoiceReport_1", "command", new clsVoidBillBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmVoidInvoiceReport", "command", new clsVoidBillBean());
		} else {
			return null;
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/voidInvoiceReportSummary", method = RequestMethod.GET)
	public void funGenerateVoidBillReportSummary(@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate, HttpServletRequest req, HttpServletResponse resp) {
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptVoidInvoiceReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
	
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
			hm.put("dteFromDate", fromDate);
			hm.put("dteToDate", toDate);
			hm.put("stReportName", "Void Invoice Report");
			
			// get all parameters
			String sqlVoid="SELECT a.strInvCode, DATE_FORMAT(a.dteInvDate,'%d-%m-%Y'),e.strPName,b.strProdCode,c.strProdName,sum(b.dblQty),sum(b.dblPrice),SUM(b.dblPrice)*SUM(b.dblQty)"
					+ " FROM tblinvoicehd a,tblvoidedproductinvoicedtl b,tblproductmaster c,tbllocationmaster d,tblpartymaster e"
					+ " WHERE a.strInvCode=b.strInvCode and b.strProdCode=c.strProdCode and a.strLocCode=d.strLocCode and a.strCustCode=e.strPCode"
					+ " and DATE_FORMAT(Date(a.dteInvDate),'%d-%m-%Y') between '"+fromDate+"' and '"+toDate+"'"
					+ " GROUP BY a.strInvCode,b.strProdCode"
					+ " ORDER BY a.strInvCode ";
			
	
			List listOVoidDtl = objGlobalFunctionsService.funGetDataList(sqlVoid, "sql");
			ArrayList<clsInvoiceModelDtl> fieldList = new ArrayList<clsInvoiceModelDtl>();

			for (int i = 0; i < listOVoidDtl.size(); i++) {
				Object[] arr = (Object[]) listOVoidDtl.get(i);
				clsInvoiceModelDtl objVoidBean = new clsInvoiceModelDtl();
				objVoidBean.setStrSOCode(arr[0].toString());                  //Invoice No
				objVoidBean.setStrSerialNo(arr[1].toString());// Invoice date
				objVoidBean.setStrRemarks(arr[2].toString());	//Customer
				objVoidBean.setStrProdCode(arr[4].toString());                  //Product Name
				objVoidBean.setDblQty(Double.valueOf(arr[5].toString()));// Quantity
				objVoidBean.setDblBillRate(Double.valueOf(arr[6].toString()));	//unit price
				objVoidBean.setDblPrice(Double.valueOf(arr[7].toString()));	//total price
				fieldList.add(objVoidBean);

			}
			
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
				resp.setHeader("Content-Disposition", "inline;filename=PaxReport.pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
