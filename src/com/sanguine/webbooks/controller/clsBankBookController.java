package com.sanguine.webbooks.controller;

import java.sql.SQLException;
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

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
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
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webbooks.bean.clsCreditorOutStandingReportBean;

@Controller
public class clsBankBookController {

	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	
	@Autowired
	private clsCurrencyMasterService objCurrencyMasterService;

	// Open Buget Form
	@RequestMapping(value = "/frmBankBook", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
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
			return new ModelAndView("frmBankBook_1", "command", new clsCreditorOutStandingReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmBankBook", "command", new clsCreditorOutStandingReportBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/rptBankBook", method = RequestMethod.GET)
	private void funReport(@ModelAttribute("command") clsCreditorOutStandingReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		Connection con = objGlobal.funGetConnection(req);
		try {
			//String strCurr = req.getSession().getAttribute("currValue").toString();
			//double currValue = Double.parseDouble(strCurr);
			double currValue=1;
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String startDate = req.getSession().getAttribute("startDate").toString();
			String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
			String currency=objBean.getStrCurrency();
				
			startDate = startDate.split("/")[2] + "-" + startDate.split("/")[1] + "-" + startDate.split("/")[0];
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}

			String fromDate = objBean.getDteFromDate();
			String toDate = objBean.getDteToDate();
			String type = "PDF";
			String fd = fromDate.split("-")[0];
			String fm = fromDate.split("-")[1];
			String fy = fromDate.split("-")[2];

			String td = toDate.split("-")[0];
			String tm = toDate.split("-")[1];
			String ty = toDate.split("-")[2];

			String dteFromDate = fy + "-" + fm + "-" + fd;
			String dteToDate = ty + "-" + tm + "-" + td;
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webbooks/rptBankBook.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			ArrayList fieldList = new ArrayList();


			if (!startDate.equals(dteFromDate)) {
				String tempFromDate = dteFromDate.split("-")[2] + "-" + dteFromDate.split("-")[1] + "-" + dteFromDate.split("-")[0];
				SimpleDateFormat obj = new SimpleDateFormat("dd-MM-yyyy");
				Date dt1;
				try {
					dt1 = obj.parse(tempFromDate);
					GregorianCalendar cal = new GregorianCalendar();
					cal.setTime(dt1);
					cal.add(Calendar.DATE, -1);
					String newToDate = (cal.getTime().getYear() + 1900) + "-" + (cal.getTime().getMonth() + 1) + "-" + (cal.getTime().getDate());
                   String sql = " select '" + fromDate + "','Op','Opening' ,'" + fromDate + "','','0.00','0.00',IFNULL(sum(op),0.00),pname,conv2,currencyName from " 
							+ " (SELECT a.strVouchNo, DATE_FORMAT(a.dteVouchDate,'%d-%m-%Y') vouchDate,sum(a.dblAmt) payment,0.00 receipt, IFNULL(a.strNarration,''),sum(a.dblAmt)-0.00 op,c.strPName as pname,"
							+ " if((d.strCurrencyCode is null) or (d.strCurrencyCode ='NA' ) or (d.strCurrencyCode ='' ),"
							+ " (select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster  where strCurrencyCode='"+currency+"') ,a.dblConversion) as conv2,ifnull(d.strCurrencyName,(select strCurrencyName from "+webStockDB+".tblcurrencymaster where strCurrencyCode=a.strCurrency)) as currencyName " 
							+ " FROM  tblpaymentdtl b,"+webStockDB+".tblpartymaster c, tblpaymenthd  a left outer join "+webStockDB+".tblcurrencymaster d on a.strCurrency=d.strCurrencyCode and a.strCurrency='"+currency+"'"
							+ " WHERE a.strVouchNo=b.strVouchNo and b.strAccountCode='"+ objBean.getStrAccountCode() + "' "
							+ " and  date(a.dteVouchDate) BETWEEN '" + startDate + "' and '" + newToDate + "' AND a.strClientCode='" + clientCode + "' AND a.strSancCode=c.strDebtorCode" 
							+ " UNION all " 
							+ " SELECT a.strVouchNo, DATE_FORMAT(a.dteVouchDate,'%d-%m-%Y') vouchDate,0.00 payment,sum(a.dblAmt) receipt, IFNULL(a.strNarration,''),0.00-sum(a.dblAmt) op,c.strPName AS pname,"
							+ " if((d.strCurrencyCode is null) or (d.strCurrencyCode ='NA' ) or (d.strCurrencyCode ='' ),"
							+ " (select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster  where strCurrencyCode='"+currency+"') ,a.dblConversion) as conv2,ifnull(d.strCurrencyName,(select strCurrencyName from "+webStockDB+".tblcurrencymaster where strCurrencyCode=a.strCurrency))as currencyName  " 
							+ " FROM tblreceiptdtl b,"+webStockDB+".tblpartymaster c, tblreceipthd  a left outer join "+webStockDB+".tblcurrencymaster d on a.strCurrency=d.strCurrencyCode and a.strCurrency='"+currency+"'"
							+ " WHERE a.strVouchNo=b.strVouchNo and b.strAccountCode='" + objBean.getStrAccountCode() + "' and  date(a.dteVouchDate) BETWEEN '" + startDate + "' and '" + newToDate + "' "
							+ " AND a.strClientCode='" + clientCode + "' AND a.strSancCode=c.strDebtorCode) opening order by vouchDate";

					List listProdDtl = objGlobalFunctionsService.funGetDataList(sql, "sql");

					for (int j = 0; j < listProdDtl.size(); j++) {
						clsCreditorOutStandingReportBean objProdBean = new clsCreditorOutStandingReportBean();
						Object[] prodArr = (Object[]) listProdDtl.get(j);
						currValue=Double.parseDouble(prodArr[9].toString());
						
						objProdBean.setDteVouchDate(prodArr[1].toString());
						objProdBean.setStrVouchNo("");
						objProdBean.setStrTransectionName(prodArr[8].toString());
						objProdBean.setDteBillDate(prodArr[3].toString());
						objProdBean.setStrBillNo(prodArr[4].toString());
						objProdBean.setStrCurrency(prodArr[10].toString());
						objProdBean.setDblCrAmt(Double.parseDouble(prodArr[5].toString()) / currValue);// Recipt Amount
						objProdBean.setDblDrAmt(Double.parseDouble(prodArr[6].toString()) / currValue);// Payment Amount
						objProdBean.setDblBalAmt(Double.parseDouble(prodArr[7].toString()) / currValue);
						
					
					
						fieldList.add(objProdBean);
					}

					String sqlQuery = "  SELECT  DATE_FORMAT(a.dteVouchDate,'%d-%m-%Y') vouchDate,a.strVouchNo,'Payment' Doc,a.dblAmt payment,0.00 receipt, a.dblAmt-0.00,a.strChequeNo,"
							+ " if((c.strCurrencyCode is null) or (c.strCurrencyCode ='NA' ) or (c.strCurrencyCode =(select strCurrencyName from "+webStockDB+".tblcurrencymaster where strCurrencyCode=a.strCurrency) ),"
							+ " (select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster  where strCurrencyCode='"+currency+"') ,a.dblConversion) as conv2,ifnull(c.strCurrencyName,(select strCurrencyName from "+webStockDB+".tblcurrencymaster where strCurrencyCode=a.strCurrency)) AS currency " 
							+ " FROM tblpaymentdtl b,tblpaymenthd  a left outer join "+webStockDB+".tblcurrencymaster c on a.strCurrency=c.strCurrencyCode and a.strCurrency='"+currency+"'"
							+ " WHERE a.strVouchNo=b.strVouchNo and b.strAccountCode='" + objBean.getStrAccountCode() + "' "
							+ " and date(a.dteVouchDate) BETWEEN '" + dteFromDate + "' and '" + dteToDate + "' AND a.strClientCode='"+ clientCode + "' " 
							+ " UNION all " 
							+ " SELECT DATE_FORMAT(a.dteVouchDate,'%d-%m-%Y') vouchDate,"
							+ " a.strVouchNo,'Receipt' Doc,0.00 payment,a.dblAmt receipt, 0.00-a.dblAmt,a.strChequeNo,"
							+ " if((c.strCurrencyCode is null) or (c.strCurrencyCode ='NA' ) or (c.strCurrencyCode =(select strCurrencyName from "+webStockDB+".tblcurrencymaster where strCurrencyCode=a.strCurrency) ),"
							+ " (select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster  where strCurrencyCode='"+currency+"') ,a.dblConversion) as conv2,ifnull(c.strCurrencyName,(select strCurrencyName from "+webStockDB+".tblcurrencymaster where strCurrencyCode=a.strCurrency)) AS currency " 
							+ " FROM  tblreceiptdtl b,tblreceipthd a left outer join "+webStockDB+".tblcurrencymaster c on a.strCurrency=c.strCurrencyCode and a.strCurrency='"+currency+"'"
							+ " WHERE a.strVouchNo=b.strVouchNo and b.strAccountCode='" + objBean.getStrAccountCode() + "' and date(a.dteVouchDate) BETWEEN '" + dteFromDate + "' and '" + dteToDate
							+ "' AND a.strClientCode='" + clientCode + "' "
							+ " order by vouchDate";
					List listDtl = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");

					for (int j = 0; j < listDtl.size(); j++) {
						clsCreditorOutStandingReportBean objProdBean = new clsCreditorOutStandingReportBean();
						Object[] prodArr = (Object[]) listDtl.get(j);	
						currValue=Double.parseDouble(prodArr[7].toString());
						
						objProdBean.setDteVouchDate(prodArr[0].toString());
						objProdBean.setStrVouchNo(prodArr[1].toString());
						String sqlQuery1="";
						if(prodArr[2].toString().equalsIgnoreCase("Payment"))
						{
							sqlQuery1 = "select ifnull(b.strPName,'') from tblpaymenthd a left outer join sanguine_webmms.tblpartymaster b on a.strSancCode=b.strDebtorCode "
								+" where a.strVouchNo='"+prodArr[1].toString()+"'; ";
						}
						else
						{
							sqlQuery1="select ifnull(b.strPName,'') from tblreceipthd a left outer join sanguine_webmms.tblpartymaster b on a.strDebtorCode=b.strDebtorCode\n" + 
									"where a.strVouchNo='"+prodArr[1].toString()+"';";
						}
						List list = objGlobalFunctionsService.funGetDataList(sqlQuery1, "sql");
						if(list.size()>0)
						{
							objProdBean.setStrTransectionName(list.get(0).toString());
						}
						
						objProdBean.setDteBillDate("");
						objProdBean.setStrBillNo(prodArr[6].toString());
						objProdBean.setStrCurrency(prodArr[8].toString());
						objProdBean.setDblCrAmt(Double.parseDouble(prodArr[4].toString()) / currValue);// Recipt Amount
						objProdBean.setDblDrAmt(Double.parseDouble(prodArr[3].toString()) / currValue);// Payment Amount
						objProdBean.setDblBalAmt(Double.parseDouble(prodArr[5].toString()) / currValue);
						objProdBean.setStrBillNo(prodArr[6].toString());
						fieldList.add(objProdBean);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else {
				String sqlQuery = "  SELECT  DATE_FORMAT(a.dteVouchDate,'%d-%m-%Y') vouchDate,a.strVouchNo,'Payment' Doc,a.dblAmt payment,0.00 receipt, a.dblAmt-0.00,a.strChequeNo,"
						+ " IF((c.strCurrencyCode IS NULL) OR (c.strCurrencyCode ='NA') OR (c.strCurrencyCode =''), "
						+ "(SELECT dblConvToBaseCurr FROM "+webStockDB+".tblcurrencymaster WHERE strCurrencyCode='"+currency+"'),a.dblConversion) AS conv2,ifnull(c.strCurrencyName,(select strCurrencyName from "+webStockDB+".tblcurrencymaster where strCurrencyCode=a.strCurrency)) AS currency " 
						+ " FROM tblpaymentdtl b,tblpaymenthd a LEFT OUTER JOIN sanguine_webmms.tblcurrencymaster c ON a.strCurrency=c.strCurrencyCode AND a.strCurrency='"+currency+"' "
						+ " WHERE a.strVouchNo=b.strVouchNo and b.strAccountCode='" + objBean.getStrAccountCode() + "' "
						+ " and date(a.dteVouchDate) BETWEEN '" + dteFromDate + "' and '" + dteToDate + "' AND a.strClientCode='"+ clientCode + "' "
						+ " UNION all " + " SELECT DATE_FORMAT(a.dteVouchDate,'%d-%m-%Y') vouchDate,a.strVouchNo,'Receipt' Doc,0.00 payment,"
						+ " a.dblAmt receipt, 0.00-a.dblAmt,a.strChequeNo, " 
						+ " IF((c.strCurrencyCode IS NULL) OR (c.strCurrencyCode ='NA') OR (c.strCurrencyCode =''), "
						+ "(SELECT dblConvToBaseCurr FROM "+webStockDB+".tblcurrencymaster WHERE strCurrencyCode='"+currency+"'),a.dblConversion) AS conv2,ifnull(c.strCurrencyName,(select strCurrencyName from "+webStockDB+".tblcurrencymaster where strCurrencyCode=a.strCurrency)) AS currency " 
						+ " FROM  tblreceiptdtl b,tblreceipthd a LEFT OUTER JOIN sanguine_webmms.tblcurrencymaster c ON a.strCurrency=c.strCurrencyCode AND a.strCurrency='"+currency+"' "
						+ " WHERE a.strVouchNo=b.strVouchNo "
						+ " and b.strAccountCode='" + objBean.getStrAccountCode() + "' "
						+ " and  date(a.dteVouchDate) BETWEEN '" + dteFromDate + "' and '" + dteToDate
						+ "' AND a.strClientCode='" + clientCode + "'  order by vouchDate";
				List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");

				for (int j = 0; j < listProdDtl.size(); j++) {
					clsCreditorOutStandingReportBean objProdBean = new clsCreditorOutStandingReportBean();
					Object[] prodArr = (Object[]) listProdDtl.get(j);		
					currValue=Double.parseDouble(prodArr[7].toString());
					
					objProdBean.setDteVouchDate(prodArr[0].toString());
					objProdBean.setStrVouchNo(prodArr[1].toString());
					String sqlQuery1="";
					if(prodArr[2].toString().equalsIgnoreCase("Payment"))
					{
						sqlQuery1 = "select ifnull(b.strPName,'') from tblpaymenthd a left outer join sanguine_webmms.tblpartymaster b on a.strSancCode=b.strDebtorCode "
							+" where a.strVouchNo='"+prodArr[1].toString()+"'; ";
					}
					else
					{
						sqlQuery1="select ifnull(b.strPName,'') from tblreceipthd a left outer join sanguine_webmms.tblpartymaster b on a.strSancCode=b.strDebtorCode\r\n" + 
								"where a.strVouchNo='"+prodArr[1].toString()+"';";
					}
					List list = objGlobalFunctionsService.funGetDataList(sqlQuery1, "sql");
					if(list.size()>0)
					{
						objProdBean.setStrTransectionName(list.get(0).toString());
					}
					objProdBean.setDteBillDate("");
					objProdBean.setStrBillNo(prodArr[6].toString());
					objProdBean.setStrCurrency(prodArr[8].toString());
					objProdBean.setDblCrAmt(Double.parseDouble(prodArr[3].toString()) / currValue);// Recipt Amount
					objProdBean.setDblDrAmt(Double.parseDouble(prodArr[4].toString()) / currValue);// Payment Amount
					objProdBean.setDblBalAmt(Double.parseDouble(prodArr[5].toString()) / currValue);
					
					fieldList.add(objProdBean);
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
			hm.put("strACName", objBean.getStrAccountName());
			hm.put("strBankCode", objBean.getStrAccountCode());
			hm.put("strCurrency", objCurrencyMasterService.funGetCurrencyMaster(currency,clientCode).getStrCurrencyName());

			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
			JasperPrint print = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
			jprintlist.add(print);
			ServletOutputStream servletOutputStream = resp.getOutputStream();
			if (jprintlist.size() > 0) {
				if (type.equals("PDF")) {
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
				}

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
