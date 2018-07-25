package com.sanguine.webbooks.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.base.service.intfBaseService;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsTaxHdModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsTaxMasterService;
import com.sanguine.util.clsReportBean;
import com.sanguine.webbooks.bean.clsTaxReportDayWiseBean;

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
public class clsTaxReportDayWiseController {
 
	
	@Autowired
	private clsCurrencyMasterService objCurrencyMasterService;
	
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	
	@Autowired
	private clsTaxMasterService objTaxMasterService;
	
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	
	@Autowired
	private intfBaseService objBaseService;
	
	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;
	
	@RequestMapping(value = "/frmTaxReportDayWise", method = RequestMethod.GET)
	public ModelAndView funOpenTaxReportDayWiseReport(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		Map<String, String> hmCurrency = objCurrencyMasterService.funCurrencyListToDisplay(clientCode);
		if (hmCurrency.isEmpty()) {
			hmCurrency.put("", "");
		}
		model.put("currencyList", hmCurrency);
		
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmTaxReportDayWise_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmTaxReportDayWise", "command", new clsReportBean());
		}
	}
	
	//clsTaxMasterService
	@SuppressWarnings("unchecked")
	@Transactional
	@RequestMapping(value = "/loadAllTax", method = RequestMethod.GET)
	public @ResponseBody List<clsTaxHdModel> funLoadAllTax(HttpServletRequest request) {
		
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String sql = "select * from tbltaxhd where strClientCode='" + clientCode + "' ";
		List list = objGlobalFunctionsService.funGetList(sql, "sql");
		clsTaxHdModel obModel;
		List<clsTaxHdModel> listModel=new ArrayList();
		if(null!=list && list.size()>0){
			for(int i=0;i<list.size();i++){
				obModel=new clsTaxHdModel();
				Object ob[]=(Object[])list.get(i);
				obModel.setStrTaxCode(ob[0].toString());
				obModel.setStrTaxDesc(ob[2].toString());
				listModel.add(obModel);
			}
		}else{
			obModel=new clsTaxHdModel();
			obModel.setStrTaxCode("Invalid Code");
			listModel.add(obModel);
		}
		return listModel;
	}
	
	@RequestMapping(value = "/rptTaxReportDayWise1", method = RequestMethod.POST)
	public @ResponseBody void funReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req)
	{
		String fDate = objBean.getDtFromDate();
		String tDate = objBean.getDtToDate();
	

		String fd = fDate.split("-")[0];
		String fm = fDate.split("-")[1];
		String fy = fDate.split("-")[2];

		String td = tDate.split("-")[0];
		String tm = tDate.split("-")[1];
		String ty = tDate.split("-")[2];

		String dteFromDate = fy + "-" + fm + "-" + fd;
		String dteToDate = ty + "-" + tm + "-" + td;
		String reportName = servletContext.getRealPath("/WEB-INF/reports/webbooks/rptTaxReportDayWise.jrxml");
		String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
		
		String companyName = req.getSession().getAttribute("companyName").toString();

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		if (objSetup == null) {
			objSetup = new clsPropertySetupModel();
		}
		double conversionRate=1;
		String currencyCode=objBean.getStrCurrency();
		String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster where strCurrencyCode='"+currencyCode+"' and strClientCode='"+clientCode+"' ");
		try
		{
			List list = objBaseService.funGetList(sbSql,"sql");
			conversionRate=Double.parseDouble(list.get(0).toString());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		String tempTaxCode[] = objBean.getStrTaxCode().split(",");
		String strSellectTaxCodes = "";

		for (int i = 0; i < tempTaxCode.length; i++) {
			if (strSellectTaxCodes.length() > 0) {
				strSellectTaxCodes = strSellectTaxCodes + ",  '" + tempTaxCode[i] + "' ";
			} else {
				strSellectTaxCodes = " in('" + tempTaxCode[i] + "' ";

			}
		}
		strSellectTaxCodes=strSellectTaxCodes+")";
		List<clsTaxReportDayWiseBean> listAllTaxDtl=new ArrayList<>(); 
		
		Map<String,clsTaxReportDayWiseBean> mapTaxSummaryTot=new HashMap<>();
		try{
			 sbSql.setLength(0);
			 sbSql=new StringBuilder(" select DATE_FORMAT(Date(c.dteInvDate),'%d-%m-%Y')  , a.strTaxCode,a.strTaxDesc,"
					+ " \"Invoice\",sum(a.dblTaxAmt),sum(a.dblTaxableAmt) " 
					+" from tblinvtaxdtl a ,tbltaxhd b,tblinvoicehd c "
					+" where a.strTaxCode=b.strTaxCode  "
	 				+" and c.strInvCode=a.strInvCode  "
	 				+" and Date(c.dteInvDate) between '"+fDate+"' and '"+tDate+"'"
	 						+ " and a.strTaxCode ");
					sbSql.append(strSellectTaxCodes);
					
					sbSql.append("group by DATE_FORMAT(Date(c.dteInvDate),'%d-%m-%Y') ,a.strTaxCode");
					//invoice
					List listInv = objGlobalFunctionsService.funGetList(sbSql.toString(), "sql");
					//List listInv=objBaseService.funGetListModuleWise(sbSql, "sql", "WebStocks");
					clsTaxReportDayWiseBean obBean;
					if(listInv!=null &&listInv.size()>0){
						for(int i=0;i<listInv.size();i++){
							obBean = new clsTaxReportDayWiseBean();
							Object ob[]=(Object[])listInv.get(i);
							obBean.setStrTaxDate(ob[0].toString());
							obBean.setStrTaxCode(ob[1].toString());
							obBean.setStrTaxDesc(ob[2].toString());
							obBean.setStrTaxFrom(ob[3].toString());
							obBean.setTaxableAmount(Double.parseDouble(ob[5].toString())*conversionRate);
							obBean.setTaxAmount(Double.parseDouble(ob[4].toString())*conversionRate);
							if(Double.parseDouble(ob[4].toString())>0){
								listAllTaxDtl.add(obBean);	
								if(mapTaxSummaryTot.containsKey(ob[1].toString())){
									clsTaxReportDayWiseBean objTemp=new clsTaxReportDayWiseBean();
									clsTaxReportDayWiseBean objTemp1=mapTaxSummaryTot.get(ob[1].toString());
									double tax=0,taxable=0;
									tax=objTemp1.getTaxAmount()+obBean.getTaxAmount();
									taxable=objTemp1.getTaxableAmount()+obBean.getTaxableAmount();
									objTemp.setStrTaxCode(obBean.getStrTaxCode());
									objTemp.setStrTaxDesc(obBean.getStrTaxDesc());
									objTemp.setTaxAmount(tax);
									objTemp.setTaxableAmount(taxable);
									
									mapTaxSummaryTot.put(ob[1].toString(),objTemp);
								}else{
									mapTaxSummaryTot.put(ob[1].toString(),obBean);	
								}
								
							}
								
						}
					}
					//sale return
					sbSql.setLength(0);
					sbSql.append("select DATE_FORMAT(Date(c.dteSRDate),'%d-%m-%Y')  , a.strTaxCode,a.strTaxDesc,\"Sale Return\",sum(a.strTaxAmt),sum(a.strTaxableAmt) " 
							+" from tblsalesreturntaxdtl a ,tbltaxhd b,tblsalesreturnhd c "
							+" where a.strTaxCode=b.strTaxCode  " 
 							+" and c.strSRCode=a.strSRCode  "
 							+" and Date(c.dteSRDate) between '"+fDate+"' and '"+tDate+"' "
 							+" and a.strTaxCode  ");
					sbSql.append(strSellectTaxCodes);
					sbSql.append(" group by DATE_FORMAT(Date(c.dteSRDate),'%d-%m-%Y') ,a.strTaxCode;");	
				//	List listSale=objBaseService.funGetListModuleWise(sbSql, "sql", "WebStocks");
					List listSale = objGlobalFunctionsService.funGetList(sbSql.toString(), "sql");
					if(listSale!=null &&listSale.size()>0){
						for(int i=0;i<listSale.size();i++){
							obBean = new clsTaxReportDayWiseBean();
							Object ob[]=(Object[])listSale.get(i);
							obBean.setStrTaxDate(ob[0].toString());
							obBean.setStrTaxCode(ob[1].toString());
							obBean.setStrTaxDesc(ob[2].toString());
							obBean.setStrTaxFrom(ob[3].toString());
							obBean.setTaxableAmount(Double.parseDouble(ob[5].toString())*conversionRate);
							obBean.setTaxAmount(Double.parseDouble(ob[4].toString())*conversionRate);
							if(Double.parseDouble(ob[4].toString())>0){
								listAllTaxDtl.add(obBean);
								if(mapTaxSummaryTot.containsKey(ob[1].toString())){
									clsTaxReportDayWiseBean objTemp=new clsTaxReportDayWiseBean();
									clsTaxReportDayWiseBean objTemp1=mapTaxSummaryTot.get(ob[1].toString());
									double tax=0,taxable=0;
									tax=objTemp1.getTaxAmount()+obBean.getTaxAmount();
									taxable=objTemp1.getTaxableAmount()+obBean.getTaxableAmount();
									objTemp.setStrTaxCode(obBean.getStrTaxCode());
									objTemp.setStrTaxDesc(obBean.getStrTaxDesc());
									objTemp.setTaxAmount(tax);
									objTemp.setTaxableAmount(taxable);
									
									mapTaxSummaryTot.put(ob[1].toString(),objTemp);
								}else{
									mapTaxSummaryTot.put(ob[1].toString(),obBean);	
								}
							}
						}
					}
					// purchase return
					sbSql.setLength(0);
					sbSql.append(" select DATE_FORMAT(Date(c.dtPRDate),'%d-%m-%Y')  , a.strTaxCode,a.strTaxDesc,\"Purchase Return\",sum(a.strTaxAmt),sum(a.strTaxableAmt) " 
							+" from tblpurchasereturntaxdtl a ,tbltaxhd b,tblpurchasereturnhd c "
							+"  where a.strTaxCode=b.strTaxCode  "
 							+" and c.strPRCode=a.strPRCode  "
 							+" and Date(c.dtPRDate) between  '"+fDate+"' and '"+tDate+"' "
 							+" and a.strTaxCode  ");
					sbSql.append(strSellectTaxCodes);
					sbSql.append(" group by DATE_FORMAT(Date(c.dtPRDate),'%d-%m-%Y') ,a.strTaxCode;");
					List listPurchRet = objGlobalFunctionsService.funGetList(sbSql.toString(), "sql");
					//List listPurchRet=objBaseService.funGetListModuleWise(sbSql, "sql", "WebStocks");
					if(listPurchRet!=null &&listPurchRet.size()>0){
						for(int i=0;i<listPurchRet.size();i++){
							obBean = new clsTaxReportDayWiseBean();
							Object ob[]=(Object[])listPurchRet.get(i);
							obBean.setStrTaxDate(ob[0].toString());
							obBean.setStrTaxCode(ob[1].toString());
							obBean.setStrTaxDesc(ob[2].toString());
							obBean.setStrTaxFrom(ob[3].toString());
							obBean.setTaxableAmount(Double.parseDouble(ob[5].toString())*conversionRate);
							obBean.setTaxAmount(Double.parseDouble(ob[4].toString())*conversionRate);
							if(Double.parseDouble(ob[4].toString())>0){
								listAllTaxDtl.add(obBean);	
								
								if(mapTaxSummaryTot.containsKey(ob[1].toString())){
									clsTaxReportDayWiseBean objTemp=new clsTaxReportDayWiseBean();
									clsTaxReportDayWiseBean objTemp1=mapTaxSummaryTot.get(ob[1].toString());
									double tax=0,taxable=0;
									tax=objTemp1.getTaxAmount()+obBean.getTaxAmount();
									taxable=objTemp1.getTaxableAmount()+obBean.getTaxableAmount();
									objTemp.setStrTaxCode(obBean.getStrTaxCode());
									objTemp.setStrTaxDesc(obBean.getStrTaxDesc());
									objTemp.setTaxAmount(tax);
									objTemp.setTaxableAmount(taxable);
									
									mapTaxSummaryTot.put(ob[1].toString(),objTemp);
								}else{
									mapTaxSummaryTot.put(ob[1].toString(),obBean);	
								}
							}	
						}
					}
					//Grn
					
					sbSql.setLength(0);
					sbSql.append("select DATE_FORMAT(Date(c.dtGRNDate),'%d-%m-%Y')  , a.strTaxCode,a.strTaxDesc,\"GRN\",sum(a.strTaxAmt),sum(a.strTaxableAmt) " 
							+" from tblgrntaxdtl a ,tbltaxhd b,tblgrnhd c "
							+"  where a.strTaxCode=b.strTaxCode  "
 							+" and c.strGRNCode=a.strGRNCode  "
 							+" and Date(c.dtGRNDate) between '"+fDate+"' and '"+tDate+"'"
 							+" and a.strTaxCode ");
					sbSql.append(strSellectTaxCodes);
					sbSql.append(" group by DATE_FORMAT(Date(c.dtGRNDate),'%d-%m-%Y') ,a.strTaxCode;");
					
					List listGrn = objGlobalFunctionsService.funGetList(sbSql.toString(), "sql");
					//List listGrn=objBaseService.funGetListModuleWise(sbSql, "sql", "WebStocks");
					if(listGrn!=null &&listGrn.size()>0){
						for(int i=0;i<listGrn.size();i++){
							obBean = new clsTaxReportDayWiseBean();
							Object ob[]=(Object[])listGrn.get(i);
							obBean.setStrTaxDate(ob[0].toString());
							obBean.setStrTaxCode(ob[1].toString());
							obBean.setStrTaxDesc(ob[2].toString());
							obBean.setStrTaxFrom(ob[3].toString());
							obBean.setTaxableAmount(Double.parseDouble(ob[5].toString())*conversionRate);
							obBean.setTaxAmount(Double.parseDouble(ob[4].toString())*conversionRate);
							if(Double.parseDouble(ob[4].toString())>0){
								listAllTaxDtl.add(obBean);	
								
								if(mapTaxSummaryTot.containsKey(ob[1].toString())){
									clsTaxReportDayWiseBean objTemp=new clsTaxReportDayWiseBean();
									clsTaxReportDayWiseBean objTemp1=mapTaxSummaryTot.get(ob[1].toString());
									double tax=0,taxable=0;
									tax=objTemp1.getTaxAmount()+obBean.getTaxAmount();
									taxable=objTemp1.getTaxableAmount()+obBean.getTaxableAmount();
									objTemp.setStrTaxCode(obBean.getStrTaxCode());
									objTemp.setStrTaxDesc(obBean.getStrTaxDesc());
									objTemp.setTaxAmount(tax);
									objTemp.setTaxableAmount(taxable);
									
									mapTaxSummaryTot.put(ob[1].toString(),objTemp);
								}else{
									mapTaxSummaryTot.put(ob[1].toString(),obBean);	
								}
							}
						}
					}
					List<clsTaxReportDayWiseBean> listTaxSummary=new ArrayList();
					Collections.sort(listAllTaxDtl, clsTaxReportDayWiseBean.TaxRtpDateComparator);
					if(mapTaxSummaryTot.size()>0){
						for(Map.Entry<String,clsTaxReportDayWiseBean> ent:mapTaxSummaryTot.entrySet()){
							listTaxSummary.add(ent.getValue());
						}
					}
					Collections.sort(listTaxSummary, clsTaxReportDayWiseBean.TaxRtpTaxComparator);
					
					
					//System.out.println(listAllTaxDtl);
					
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
					hm.put("dataListTaxSummary", listTaxSummary);
					
					
					List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
					JasperDesign jd = JRXmlLoader.load(reportName);
					JasperReport jr = JasperCompileManager.compileReport(jd);
					JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listAllTaxDtl);
					JasperPrint print = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
					jprintlist.add(print);
					ServletOutputStream servletOutputStream = resp.getOutputStream();
					if (jprintlist.size() > 0) {
						//if (type.equals("PDF")) {
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

							/*} else {
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
						}*/
					}
					
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
	
	 
	
	@RequestMapping(value = "/rptTaxReportDayWise", method = RequestMethod.GET)
	public @ResponseBody void funGetTaxRegister(@RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate,
			@RequestParam(value = "cur") String currency, @RequestParam(value = "hidTaxCodes") String hidTaxCodes, HttpServletRequest req, HttpServletResponse resp)
	{	
/*	private ModelAndView funCallTaxReportDayWise(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
*/		
		//String fromDate = new clsGlobalFunctions().funGetDate("yyyy-MM-dd", objBean.getDtFromDate());
		//String toDate = new clsGlobalFunctions().funGetDate("yyyy-MM-dd", objBean.getDtToDate());
	}
	
}
