package com.sanguine.webpms.controller;

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
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webpms.bean.clsRevenueHeadDtlReportBean;
import com.sanguine.webpms.bean.clsRevenueHeadReportBean;

@Controller
public class clsRevenueHeadReportController {
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	private HashMap<String, clsRevenueHeadReportBean> mapIncomeHeads;

	private HashMap<String, Map<String, clsRevenueHeadDtlReportBean>> mapIHTaxDtls;

	// Open Folio Printing
	@RequestMapping(value = "/frmRevenueHeadReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmRevenueHeadReport_1", "command", new clsRevenueHeadReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmRevenueHeadReport", "command", new clsRevenueHeadReportBean());
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/rptRevenueHeadReport", method = RequestMethod.GET)
	public void funGenerateIncomeHeadReport(@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate, HttpServletRequest req, HttpServletResponse resp) {
		StringBuilder sbSql = new StringBuilder();
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webpms/rptRevenueHeadReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			// add all parameters
			@SuppressWarnings("rawtypes")
			HashMap reportParams = new HashMap();

			reportParams.put("pCompanyName", companyName);
			reportParams.put("pAddress1", (objSetup.getStrAdd1() == null ? ' ' : objSetup.getStrAdd1()) + "," + (objSetup.getStrAdd2() == null ? ' ' : objSetup.getStrAdd2()) + "," + (objSetup.getStrCity() == null ? ' ' : objSetup.getStrCity()));
			reportParams.put("pAddress2", (objSetup.getStrState() == null ? ' ' : objSetup.getStrState()) + "," + (objSetup.getStrCountry() == null ? ' ' : objSetup.getStrCountry()) + "," + (objSetup.getStrPin() == null ? ' ' : objSetup.getStrPin()));
			reportParams.put("pContactDetails", "");
			reportParams.put("strImagePath", imagePath);

			// main data list
			List<clsRevenueHeadReportBean> dataList = new ArrayList<clsRevenueHeadReportBean>();
			mapIncomeHeads = new HashMap<String, clsRevenueHeadReportBean>();
			mapIHTaxDtls = new HashMap<String, Map<String, clsRevenueHeadDtlReportBean>>();

			// income head types for booked
			sbSql.setLength(0);
			sbSql.append("SELECT b.strRevenueType, sum(b.dblDebitAmt),SUM(b.dblBalanceAmt) " + " FROM tblbillhd a, tblbilldtl b " + " where a.strBillNo=b.strBillNo and DATE(a.dteBillDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " GROUP BY b.strRevenueType ");
			List listIncomeHeadsForBilled = objGlobalFunctionsService.funGetDataList(sbSql.toString(), "sql");
			for (int t = 0; t < listIncomeHeadsForBilled.size(); t++) {
				Object[] objIncomeHeadsForBilled = (Object[]) listIncomeHeadsForBilled.get(t);
				String incomeHeadTypeName = objIncomeHeadsForBilled[0].toString();
				double bookedAmt = Double.parseDouble(objIncomeHeadsForBilled[1].toString());
				clsRevenueHeadReportBean objIncomeHeadBean = new clsRevenueHeadReportBean();
				objIncomeHeadBean.setStrIncomeHeadType(incomeHeadTypeName);
				objIncomeHeadBean.setDblBookedAmt(bookedAmt);
				objIncomeHeadBean.setDblUnBookedAmt(0.00);

				// get dtl for this incomeHeadTypeName
				sbSql.setLength(0);
				sbSql.append("SELECT c.strTaxDesc,  sum(c.dblTaxAmt) " + " FROM tblbillhd a, tblbilldtl b,tblbilltaxdtl c " + " where a.strBillNo=b.strBillNo and b.strDocNo=c.strDocNo AND b.strBillNo=c.strBillNo " + " and DATE(a.dteBillDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " and b.strRevenueType='" + incomeHeadTypeName + "' " + " GROUP BY c.strTaxCode ");
				List listIncomeHeadDtlsForBilled = objGlobalFunctionsService.funGetDataList(sbSql.toString(), "sql");

				List<clsRevenueHeadDtlReportBean> listIncomeHeadTaxDtl = new ArrayList<clsRevenueHeadDtlReportBean>();
				for (int td = 0; td < listIncomeHeadDtlsForBilled.size(); td++) {
					Object[] objIncomeHeadDtlsForBilled = (Object[]) listIncomeHeadDtlsForBilled.get(td);
					String taxDesc = objIncomeHeadDtlsForBilled[0].toString();
					double bookedDtlAmt = Double.parseDouble(objIncomeHeadDtlsForBilled[1].toString());
					clsRevenueHeadDtlReportBean objIncomeHeadDtlsBean = new clsRevenueHeadDtlReportBean();
					objIncomeHeadDtlsBean.setStrTaxDesc((taxDesc));
					objIncomeHeadDtlsBean.setDblBookedAmt(bookedDtlAmt);
					objIncomeHeadDtlsBean.setDblUnBookedAmt(0.00);
					listIncomeHeadTaxDtl.add(objIncomeHeadDtlsBean);

					if (mapIHTaxDtls.containsKey(incomeHeadTypeName)) {
						Map<String, clsRevenueHeadDtlReportBean> mapTaxDtlBean = mapIHTaxDtls.get(incomeHeadTypeName);
						mapTaxDtlBean.put(taxDesc, objIncomeHeadDtlsBean);
						mapIHTaxDtls.put(incomeHeadTypeName, mapTaxDtlBean);
					} else {
						Map<String, clsRevenueHeadDtlReportBean> mapTaxDtlBean = new HashMap<String, clsRevenueHeadDtlReportBean>();
						mapTaxDtlBean.put(taxDesc, objIncomeHeadDtlsBean);
						mapIHTaxDtls.put(incomeHeadTypeName, mapTaxDtlBean);
					}
				}
				objIncomeHeadBean.setListIncomeHeadTaxDtl(listIncomeHeadTaxDtl);
				mapIncomeHeads.put(incomeHeadTypeName, objIncomeHeadBean);
				dataList.add(objIncomeHeadBean);
			
			}

			// income head types for unbooked
			sbSql.setLength(0);
			sbSql.append("SELECT b.strRevenueType, sum(b.dblDebitAmt) " + " FROM tblfoliohd a,tblfoliodtl b " + " where a.strFolioNo=b.strFolioNo " + " and DATE(b.dteDocDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " GROUP BY b.strRevenueType ");
			List listIncomeHeadsForFolio = objGlobalFunctionsService.funGetDataList(sbSql.toString(), "sql");
			for (int t = 0; t < listIncomeHeadsForFolio.size(); t++) {
				Object[] objIncomeHeadsForBilled = (Object[]) listIncomeHeadsForFolio.get(t);
				String incomeHeadTypeName = objIncomeHeadsForBilled[0].toString();
				double unbookedAmt = Double.parseDouble(objIncomeHeadsForBilled[1].toString());

				boolean isExists = isExistsIHTypeName(incomeHeadTypeName);
				clsRevenueHeadReportBean objIncomeHeadBean = null;
				if (isExists) {
					objIncomeHeadBean = mapIncomeHeads.get(incomeHeadTypeName);
					objIncomeHeadBean.setDblUnBookedAmt(unbookedAmt);
				} else {
					objIncomeHeadBean = new clsRevenueHeadReportBean();
					objIncomeHeadBean.setStrIncomeHeadType(incomeHeadTypeName);
					objIncomeHeadBean.setDblBookedAmt(0.00);
					objIncomeHeadBean.setDblUnBookedAmt(unbookedAmt);
				}

				// get dtl for this incomeHeadTypeName
				sbSql.setLength(0);
				sbSql.append("SELECT c.strTaxDesc,  sum(c.dblTaxAmt) " + " FROM tblfoliohd a, tblfoliodtl b, tblfoliotaxdtl c " + " where a.strFolioNo=b.strFolioNo and b.strDocNo=c.strDocNo AND b.strFolioNo=c.strFolioNo " + " and DATE(b.dteDocDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " and b.strRevenueType='" + incomeHeadTypeName + "' " + " GROUP BY c.strTaxCode ");
				List listIncomeHeadDtlsForFolio = objGlobalFunctionsService.funGetDataList(sbSql.toString(), "sql");
				List<clsRevenueHeadDtlReportBean> listIncomeHeadTaxDtl = null;
				if (isExists) {
					listIncomeHeadTaxDtl = objIncomeHeadBean.getListIncomeHeadTaxDtl();
				} else {
					listIncomeHeadTaxDtl = new ArrayList<clsRevenueHeadDtlReportBean>();
				}

				for (int td = 0; td < listIncomeHeadDtlsForFolio.size(); td++) {
					Object[] objIncomeHeadDtlsForBilled = (Object[]) listIncomeHeadDtlsForFolio.get(td);
					String taxDesc = objIncomeHeadDtlsForBilled[0].toString();
					double unbookedDtlAmt = Double.parseDouble(objIncomeHeadDtlsForBilled[1].toString());
					boolean isTaxExists = isTaxExists(incomeHeadTypeName, taxDesc);

					clsRevenueHeadDtlReportBean objIncomeHeadDtlsBean = null;
					if (isTaxExists) {
						objIncomeHeadDtlsBean = mapIHTaxDtls.get(incomeHeadTypeName).get(taxDesc);
						objIncomeHeadDtlsBean.setDblUnBookedAmt(unbookedDtlAmt);
					} else {
						objIncomeHeadDtlsBean = new clsRevenueHeadDtlReportBean();
						objIncomeHeadDtlsBean.setStrTaxDesc((taxDesc));
						objIncomeHeadDtlsBean.setDblBookedAmt(0.00);
						objIncomeHeadDtlsBean.setDblUnBookedAmt(unbookedDtlAmt);
					}

					if (!isExists) {
						listIncomeHeadTaxDtl.add(objIncomeHeadDtlsBean);
					}
				}
				objIncomeHeadBean.setListIncomeHeadTaxDtl(listIncomeHeadTaxDtl);
				mapIncomeHeads.put(incomeHeadTypeName, objIncomeHeadBean);

				if (!isExists) {
					dataList.add(objIncomeHeadBean);
				}
			}

			// set totals
			for (int i = 0; i < dataList.size(); i++) {
				double bookedTotal = 0.00;
				double unBookedTotal = 0.00;
				double totalTotal = 0.00;
				clsRevenueHeadReportBean objIH = dataList.get(i);
				objIH.setDblTotalAmt(objIH.getDblBookedAmt() + objIH.getDblUnBookedAmt());
				bookedTotal = bookedTotal + objIH.getDblBookedAmt();
				unBookedTotal = unBookedTotal + objIH.getDblUnBookedAmt();
				totalTotal = totalTotal + objIH.getDblTotalAmt();

				List<clsRevenueHeadDtlReportBean> listTax = objIH.getListIncomeHeadTaxDtl();
				for (int j = 0; j < listTax.size(); j++) {
					clsRevenueHeadDtlReportBean objTax = listTax.get(j);
					objTax.setDblTotalAmt(objTax.getDblBookedAmt() + objTax.getDblUnBookedAmt());
					bookedTotal = bookedTotal + objTax.getDblBookedAmt();
					unBookedTotal = unBookedTotal + objTax.getDblUnBookedAmt();
					totalTotal = totalTotal + objTax.getDblTotalAmt();
				}
				objIH.setDblBookedTotal(bookedTotal);
				objIH.setDblUnBookedTotal(unBookedTotal);
				objIH.setDblTotalTotal(totalTotal);
			}

			// get receipts detail
			List<clsRevenueHeadReportBean> listAdvRecei = new ArrayList<clsRevenueHeadReportBean>();
			sbSql.setLength(0);
			sbSql.append("select a.strAgainst,sum(a.dblReceiptAmt) " + " from tblreceipthd a " + " where date(a.dteReceiptDate) between '" + fromDate + "' and '" + toDate + "' " + " group by a.strAgainst ");
			List listAdvReceipts = objGlobalFunctionsService.funGetDataList(sbSql.toString(), "sql");
			for (int i = 0; i < listAdvReceipts.size(); i++) {
				Object[] objAdvRecei = (Object[]) listAdvReceipts.get(i);
				String name = objAdvRecei[0].toString();
				double amount = Double.parseDouble(objAdvRecei[1].toString());
				clsRevenueHeadReportBean objAdvReceBean = new clsRevenueHeadReportBean();
				objAdvReceBean.setStrName(name);
				objAdvReceBean.setDblAmount(amount);
				listAdvRecei.add(objAdvReceBean);
			}
			reportParams.put("pListOfAdvReceipts", listAdvRecei);

			// get collection break ups detail
			List<clsRevenueHeadReportBean> listCollectionBreakup = new ArrayList<clsRevenueHeadReportBean>();
			List listCollBreakup = objGlobalFunctionsService.funGetDataList("select a.strSettlementDesc,b.dblSettlementAmt " + "from tblsettlementmaster a " + "left outer join tblreceiptdtl b  on a.strSettlementCode=b.strSettlementCode " + "left outer join tblreceipthd c on b.strReceiptNo=c.strReceiptNo " + "where date(c.dteReceiptDate) BETWEEN '" + fromDate + "' and '" + toDate + "' "
					+ "GROUP BY a.strSettlementCode ", "sql");
			for (int i = 0; i < listCollBreakup.size(); i++) {
				Object[] objAdvRecei = (Object[]) listCollBreakup.get(i);
				String name = objAdvRecei[0].toString();
				double amount = Double.parseDouble(objAdvRecei[1].toString());
				clsRevenueHeadReportBean objCollBreakupBean = new clsRevenueHeadReportBean();
				objCollBreakupBean.setStrName(name);
				objCollBreakupBean.setDblAmount(amount);
				listCollectionBreakup.add(objCollBreakupBean);
			}
			reportParams.put("pListOfColleBreakup", listCollectionBreakup);

			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(dataList);
			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr, reportParams, beanCollectionDataSource);
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			if (jp != null) {
				jprintlist.add(jp);
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=Folio.pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isTaxExists(String incomeHeadTypeName, String taxDesc) {
		boolean isExists = false;
		try {
			if (mapIHTaxDtls.containsKey(incomeHeadTypeName)) {
				Map<String, clsRevenueHeadDtlReportBean> mapTaxDtl = mapIHTaxDtls.get(incomeHeadTypeName);
				if (mapTaxDtl.containsKey(taxDesc)) {
					isExists = true;
				} else {
					isExists = false;
				}
			} else {
				isExists = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return isExists;
		}
	}

	private boolean isExistsIHTypeName(String incomeHeadTypeName) {
		boolean isExists = false;
		try {
			if (mapIncomeHeads.containsKey(incomeHeadTypeName)) {
				isExists = true;
			} else {
				isExists = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return isExists;
		}
	}
}
