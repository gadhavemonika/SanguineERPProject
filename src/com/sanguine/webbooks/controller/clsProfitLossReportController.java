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
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webbooks.bean.clsDebtorLedgerBean;
import com.sanguine.webbooks.bean.clsProfitLossReportBean;

@Controller
public class clsProfitLossReportController {

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	private clsGlobalFunctions objGlobal = null;

	// Open AccountHolderMaster
	@RequestMapping(value = "/frmProfitLossWebBook", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (urlHits.equalsIgnoreCase("1")) {
			return new ModelAndView("frmProfitLossWebBook", "command", new clsProfitLossReportBean());
		} else {
			return new ModelAndView("frmProfitLossWebBook_1", "command", new clsProfitLossReportBean());
		}
	}

	@RequestMapping(value = "/rptProfitLossReport", method = RequestMethod.GET)
	private void funReport(@ModelAttribute("command") clsProfitLossReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		Connection con = objGlobal.funGetConnection(req);
		String strCurr = req.getSession().getAttribute("currValue").toString();
		double currValue = Double.parseDouble(strCurr);
		try {

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
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
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webbooks/rptProfitLossReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			List<clsProfitLossReportBean> dataListPaymnet = new ArrayList<clsProfitLossReportBean>();
			List<clsProfitLossReportBean> dataListRecipt = new ArrayList<clsProfitLossReportBean>();
			List<clsProfitLossReportBean> dataListExtraExpense = new ArrayList<clsProfitLossReportBean>();
			// String
			// sqlDtl="select a.strVouchNo as strVouchNo,b.dblAmt/"+currValue+",a.strNarration from tblpaymenthd a ,tblpaymentdebtordtl b where a.strVouchNo=b.strVouchNo "
			// +" and a.dteVouchDate between '"+dteFromDate+"' and '"+dteToDate+"' and a.strPropertyCode='"+propertyCode+"' and a.strClientCode='"+clientCode+"'  ";
			// List listPayment =
			// objGlobalFunctionsService.funGetDataList(sqlDtl,"sql");
			// double totalPayment=0.0;
			// double totalRecipt=0.0;
			// if(listPayment.size()>0)
			// {
			// for(int i=0;i<listPayment.size() ;i++)
			// {
			// Object [] obj=(Object[]) listPayment.get(i);
			// clsProfitLossReportBean objProfitLoss=new
			// clsProfitLossReportBean();
			// objProfitLoss.setStrVouchNo(obj[0].toString());
			// objProfitLoss.setDblAmt(Double.parseDouble(obj[1].toString()));
			// objProfitLoss.setStrNarration(obj[2].toString());
			// totalPayment=totalPayment+Double.parseDouble(obj[1].toString());
			// dataListPaymnet.add(objProfitLoss);
			// }
			// }
			//
			// String
			// sqlRecipt="select a.strVouchNo as strVouchNo ,b.dblAmt/"+currValue+" as dblAmt ,a.strNarration   from tblreceipthd a,tblreceiptdebtordtl b  "
			// +" where a.strVouchNo=b.strVouchNo and a.dteVouchDate between '"+dteFromDate+"' and '"+dteToDate+"' and a.strPropertyCode='"+propertyCode+"' and a.strClientCode='"+clientCode+"' ";
			//
			// List listRecipt =
			// objGlobalFunctionsService.funGetDataList(sqlRecipt,"sql");
			// if(listRecipt.size()>0)
			// {
			// for(int i=0;i<listRecipt.size() ;i++)
			// {
			// Object [] obj=(Object[]) listRecipt.get(i);
			// clsProfitLossReportBean objProfitLoss=new
			// clsProfitLossReportBean();
			// objProfitLoss.setStrVouchNo(obj[0].toString());
			// objProfitLoss.setDblAmt(Double.parseDouble(obj[1].toString()));
			// objProfitLoss.setStrNarration(obj[2].toString());
			// totalRecipt=totalRecipt+Double.parseDouble(obj[1].toString());
			// dataListRecipt.add(objProfitLoss);
			// }
			// }
			// double totalExpenses=0.0;
			//
			//
			//
			//
			//
			// String
			// sqlExtraExpense="select a.strAccountName,ifnull((b.dblAmt-c.dblAmt)/"+currValue+",0) as dblAmt from tblacgroupmaster  d, tblacmaster a "
			// +" left outer join tblreceiptdebtordtl b on a.strAccountCode=b.strAccountCode  and b.dteBillDate  between '"+dteFromDate+"' and '"+dteToDate+"' "
			// +" left outer join tblpaymentdebtordtl c on a.strAccountCode=c.strAccountCode   and b.dteBillDate  between '"+dteFromDate+"' and '"+dteToDate+"' "
			// +" where a.strType='GL Code' and a.strCreditor ='No' and a.strDebtor='No' and a.strClientCode='"+clientCode+"' and a.strPropertyCode='"+propertyCode+"' and a.strGroupCode=d.strGroupCode and d.strCategory='EXPENSE' ";
			//
			// List listExpenses =
			// objGlobalFunctionsService.funGetDataList(sqlExtraExpense,"sql");
			// if(listExpenses.size()>0)
			// {
			// for(int i=0;i<listExpenses.size() ;i++)
			// {
			// Object [] obj=(Object[]) listExpenses.get(i);
			// if(!(Double.parseDouble(obj[1].toString())==0))
			// {
			// clsProfitLossReportBean objProfitLoss=new
			// clsProfitLossReportBean();
			// objProfitLoss.setStrVouchNo(obj[0].toString());
			// objProfitLoss.setDblAmt(Double.parseDouble(obj[1].toString()));
			// totalExpenses=totalExpenses+Double.parseDouble(obj[1].toString());
			// dataListExtraExpense.add(objProfitLoss);}
			// }
			// }

			double totalIncome = 0.0;
			double totalExpense = 0.0;
			String sqlIncome = "select a.strAccountName,ifnull((b.dblAmt-c.dblAmt)/17.0,0) as dblAmt ,a.strAccountCode ,d.strGroupName,d.strCategory " + " from tblacgroupmaster  d  ,tblacmaster a  " + " left outer join tblreceiptdebtordtl b on a.strAccountCode=b.strAccountCode " + " and b.dteBillDate  between '" + dteFromDate + "' and '" + dteToDate + "'   "
					+ " left outer join tblpaymentdebtordtl c on a.strAccountCode=c.strAccountCode   " + " and c.dteBillDate  between '" + dteFromDate + "' and '" + dteToDate + "'   " + " where a.strType='GL Code' and a.strCreditor ='No' and a.strDebtor='No' and a.strGroupCode=d.strGroupCode " + " and d.strCategory='INCOME' " + " and a.strClientCode='" + clientCode + "' and a.strPropertyCode='"
					+ propertyCode + "' "
					+ " order by b.dteBillDate;";

			List listIncome = objGlobalFunctionsService.funGetDataList(sqlIncome, "sql");
			if (listIncome.size() > 0) {
				for (int i = 0; i < listIncome.size(); i++) {
					Object[] obj = (Object[]) listIncome.get(i);
					clsProfitLossReportBean objProfitLoss = new clsProfitLossReportBean();
					objProfitLoss.setStrVouchNo(obj[0].toString());
					objProfitLoss.setDblAmt(Double.parseDouble(obj[1].toString()));
					objProfitLoss.setStrNarration(obj[2].toString());
					dataListRecipt.add(objProfitLoss);

					totalIncome = totalIncome + Double.parseDouble(obj[1].toString());
				}
			}

			String sqlExtraExpense = "select a.strAccountName,ifnull((b.dblAmt-c.dblAmt)/17.0,0) as dblAmt ,a.strAccountCode ,d.strGroupName,d.strCategory " + " from tblacgroupmaster  d  ,tblacmaster a  " + " left outer join tblreceiptdebtordtl b on a.strAccountCode=b.strAccountCode " + " and b.dteBillDate  between '" + dteFromDate + "' and '" + dteToDate + "'   "
					+ " left outer join tblpaymentdebtordtl c on a.strAccountCode=c.strAccountCode   " + " and c.dteBillDate  between '" + dteFromDate + "' and '" + dteToDate + "'   " + " where a.strType='GL Code' and a.strCreditor ='No' and a.strDebtor='No' and a.strGroupCode=d.strGroupCode " + " and d.strCategory='EXPENSE' " + " and a.strClientCode='" + clientCode + "' and a.strPropertyCode='"
					+ propertyCode + "' order by b.dteBillDate ;";

			List listExpenses = objGlobalFunctionsService.funGetDataList(sqlExtraExpense, "sql");
			if (listExpenses.size() > 0) {
				for (int i = 0; i < listExpenses.size(); i++) {
					Object[] obj = (Object[]) listExpenses.get(i);
					if (!(Double.parseDouble(obj[1].toString()) == 0)) {
						clsProfitLossReportBean objProfitLoss = new clsProfitLossReportBean();
						objProfitLoss.setStrVouchNo(obj[0].toString());
						objProfitLoss.setDblAmt(Double.parseDouble(obj[1].toString()));
						totalExpense = totalExpense + Double.parseDouble(obj[1].toString());
						dataListExtraExpense.add(objProfitLoss);
					}
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
			hm.put("fromDate", fromDate);
			hm.put("toDate", toDate);
			hm.put("dataListExtraExpense", dataListExtraExpense);
			hm.put("dataListRecipt", dataListRecipt);
			hm.put("totalExpense", totalExpense);
			hm.put("totalIncome", totalIncome);

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			JasperPrint jp = JasperFillManager.fillReport(jr, hm, new JREmptyDataSource());
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			jprintlist.add(jp);

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
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptReciptReport." + type.trim());
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

}
