package com.sanguine.webbooks.controller;

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
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webbooks.bean.clsChequeReportBean;
import com.sanguine.webbooks.bean.clsProfitLossReportBean;


@Controller
public class clsChequeReceivedReport 
{
	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;
	
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;
	
	@Autowired
	intfBaseService objBaseService;

	Map<String,String> hmBank=new HashMap<>();
	// Open Cheque Issued Form
	@RequestMapping(value = "/frmChequeReceived", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		try {
			List<String> listBank=funGetBankList(clientCode);
			model.put("bankList", listBank);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (urlHits.equalsIgnoreCase("1")) {
			return new ModelAndView("frmChequeReceived", "command", new clsChequeReportBean());
		} else {
			return new ModelAndView("frmChequeReceived_1", "command", new clsChequeReportBean());
		}
	}
	
	
	@RequestMapping(value = "/rptChequeReceivedReport", method = RequestMethod.GET)
	private void funReport(@ModelAttribute("command") clsChequeReportBean objBean, HttpServletResponse resp, HttpServletRequest req) 
	{
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			double conversionRate=1;
			String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String type = "PDF";
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
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webbooks/rptChequeIssuedAndReceivedReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			ArrayList fieldList = new ArrayList();
			String startDate = req.getSession().getAttribute("startDate").toString();

			String[] sp = startDate.split(" ");
			String[] spDate = sp[0].split("/");
			startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
			HashMap<String, Double> hmMap = new HashMap<String, Double>();
			StringBuilder sbSql=new StringBuilder();
			sbSql.append("select a.strVouchNo, a.strCFCode, b.strAccountName, a.strChequeNo, a.dblAmt, a.dteVouchDate, a.dteChequeDate, c.strBankName,c.strBankCode     "
					+ " from tblreceipthd a, tblacmaster b, tblbankmaster c"
					+ " where a.strCFCode = b.strAccountCode "
					+ " and a.strDrawnOn = c.strBankCode and a.strtype = 'Cheque' "
					+ " and a.dteVouchDate between '"+dteFromDate+"' and '"+dteToDate+"' "
					+ " and a.dteChequeDate between '"+dteFromDate+"' and '"+dteToDate+"' ");
			if(!objBean.getStrBankName().toString().equals("All"))
			{
				sbSql.append(" and a.strCFCode='"+hmBank.get(objBean.getStrBankName())+"' ");
			}
			sbSql.append(" order by a.strCFCode,a.strVouchNo "); 
			
			List listCheckIssue = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
			if(listCheckIssue.size()>0)
			{
				for (int j = 0; j < listCheckIssue.size(); j++) 
				{
					clsChequeReportBean objRptBean = new clsChequeReportBean();
					Object[] obArr = (Object[]) listCheckIssue.get(j);
					objRptBean.setStrVoucherNo(obArr[0].toString());
					objRptBean.setStrBankOrCFCode(obArr[1].toString());
					objRptBean.setStrAccountName(obArr[2].toString());
					objRptBean.setStrChequeNo(obArr[3].toString());
					objRptBean.setDblAmt(Double.valueOf(obArr[4].toString()));
					objRptBean.setDteVouchDate(objGlobal.funGetDate("dd-MM-yyyy",obArr[5].toString()));
					objRptBean.setDteChequeDate(objGlobal.funGetDate("dd-MM-yyyy",obArr[6].toString()));
					objRptBean.setStrBankName(obArr[7].toString());
					objRptBean.setStrBankCode(obArr[8].toString());
					fieldList.add(objRptBean);
				}
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
			hm.put("dteFromDate", fromDate);
			hm.put("dteToDate", toDate);
			hm.put("stReportName", "Cheque Received Report");
			hm.put("stBankOrCFCCode", "CFC Code");
			hm.put("filterByBank", objBean.getStrBankName());

			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
			JasperPrint print = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
			jprintlist.add(print);
			ServletOutputStream servletOutputStream = resp.getOutputStream();
			if (jprintlist.size() > 0) {
				// if(objBean.getStrDocType().equals("PDF"))
				// {
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				// resp.setHeader("Content-Disposition",
				// "inline;filename=rptProductWiseGRNReport_"+dteFromDate+"_To_"+dteToDate+"_"+userCode+".pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();

			} else {
				JRExporter exporter = new JRXlsExporter();
				resp.setContentType("application/xlsx");
				exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				// resp.setHeader("Content-Disposition",
				// "inline;filename=rptProductWiseGRNReport_"+dteFromDate+"_To_"+dteToDate+"_"+userCode+".xls");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
				// }

			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			
		}
	}
	
	public List<String>  funGetBankList(String clientCode) throws Exception 
	{
		List<String> listBank= new ArrayList<>();
		listBank.add("All");
		StringBuilder sbSql=new StringBuilder();
		sbSql.append("select a.strAccountCode,a.strAccountName from tblacmaster a where a.strClientCode='"+clientCode+"' and a.strType='Bank'; ");
		List listGetRecord = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
		if(listGetRecord.size()>0)
		{
			for (int j = 0; j < listGetRecord.size(); j++) 
			{
				Object[] obArr = (Object[]) listGetRecord.get(j);
				hmBank.put(obArr[1].toString(),obArr[0].toString());
				listBank.add(obArr[1].toString());
			}	
		}

		return listBank;
	}
	
}
