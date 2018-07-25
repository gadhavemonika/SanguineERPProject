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
import com.sanguine.webpms.bean.clsTaxDtlBean;
import com.sanguine.webpms.bean.clsTaxReportBean;

@Controller
public class clsTaxReportController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private clsSetupMasterService objSetupMasterService;

	// Open Folio Printing
	@RequestMapping(value = "/frmTaxReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmTaxReport_1", "command", new clsTaxReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmTaxReport", "command", new clsTaxReportBean());
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/rptTaxReport", method = RequestMethod.GET)
	public void funGenerateFolioPrintingReport(@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate, HttpServletRequest req, HttpServletResponse resp) {
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
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webpms/rptTaxReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			List<clsTaxReportBean> dataList = new ArrayList<clsTaxReportBean>();

			// add all parameters
			@SuppressWarnings("rawtypes")
			HashMap reportParams = new HashMap();

			reportParams.put("pCompanyName", companyName);
			reportParams.put("pAddress1", (objSetup.getStrAdd1() == null ? ' ' : objSetup.getStrAdd1()) + "," + (objSetup.getStrAdd2() == null ? ' ' : objSetup.getStrAdd2()) + "," + (objSetup.getStrCity() == null ? ' ' : objSetup.getStrCity()));
			reportParams.put("pAddress2", (objSetup.getStrState() == null ? ' ' : objSetup.getStrState()) + "," + (objSetup.getStrCountry() == null ? ' ' : objSetup.getStrCountry()) + "," + (objSetup.getStrPin() == null ? ' ' : objSetup.getStrPin()));
			reportParams.put("pContactDetails", "");
			reportParams.put("strImagePath", imagePath);

			// get all guests by registration

			sbSql.setLength(0);
			sbSql.append("select b.strRegistrationNo,c.strGuestPrefix,c.strFirstName,c.strMiddleName,c.strLastName " + ",DATE_FORMAT(b.dteArrivalDate,'%d-%m-%Y')as CheckInDate,DATE_FORMAT(b.dteDepartureDate,'%d-%m-%Y') as CheckOutDate" + ",a.strRoomNo,e.strRoomTypeDesc " + " from tblcheckindtl a " + " left outer join tblcheckinhd b on a.strCheckInNo=b.strCheckInNo "
					+ " left OUTER join tblguestmaster c on a.strGuestCode=c.strGuestCode " + " left outer join tblroom d on a.strRoomNo=d.strRoomCode " + " left outer join tblroomtypemaster e on d.strRoomTypeCode=e.strRoomTypeCode " + " where date(b.dteArrivalDate) between '" + fromDate + "' and '" + toDate + "' and a.strPayee='Y' ");
			List listGuests = objGlobalFunctionsService.funGetDataList(sbSql.toString(), "sql");// sql

			for (int g = 0; g < listGuests.size(); g++) {
				Object[] obj = (Object[]) listGuests.get(g);
				clsTaxReportBean rooTaxBean = new clsTaxReportBean();
				String registrationNo = obj[0].toString();
				rooTaxBean.setStrRegistrationNo(registrationNo);

				String gPrefix = obj[1].toString();
				String gFirstName = obj[2].toString();
				String gMiddleName = obj[3].toString();
				String gLastName = obj[4].toString();
				rooTaxBean.setStrGuestName(gFirstName + " " + gMiddleName + " " + gLastName);

				rooTaxBean.setDteCheckInDate(obj[5].toString());
				rooTaxBean.setDteCheckOutDate(obj[6].toString());
				rooTaxBean.setStrRoomNo(obj[7].toString());
				rooTaxBean.setStrRoomType(obj[8].toString());

				// get tax detail by folio generated
				List<clsTaxDtlBean> listTaxDtlBeans = new ArrayList<clsTaxDtlBean>();
				sbSql.setLength(0);
				sbSql.append("select a.strRegistrationNo,DATE_FORMAT(b.dteDocDate,'%d-%m-%Y'),c.strDocNo,a.strFolioNo,'NA',d.strIncomeHeadDesc" + ",c.strTaxDesc,c.dblTaxAmt " + " from tblfoliohd a, tblfoliodtl b, tblfoliotaxdtl c, tblincomehead d " + " where a.strFolioNo=b.strFolioNo and  b.strFolioNo=c.strFolioNo and b.strRevenueCode=d.strIncomeHeadCode "
						+ " and b.strDocNo=c.strDocNo and a.strRegistrationNo='" + registrationNo + "' " + " and b.strRevenueType='Income Head'");
				List listTaxDtlForFolio = objGlobalFunctionsService.funGetDataList(sbSql.toString(), "sql");// sql
				for (int t = 0; t < listTaxDtlForFolio.size(); t++) {
					Object[] objTaxDtl = (Object[]) listTaxDtlForFolio.get(t);

					clsTaxDtlBean objTaxDtlBean = new clsTaxDtlBean();
					objTaxDtlBean.setDteDocDate(objTaxDtl[1].toString());
					objTaxDtlBean.setStrDocNo(objTaxDtl[2].toString());
					objTaxDtlBean.setStrFolioNo(objTaxDtl[3].toString());
					objTaxDtlBean.setStrBillNo(objTaxDtl[4].toString());
					objTaxDtlBean.setStrIncomeHead((objTaxDtl[5].toString()));
					objTaxDtlBean.setStrTaxDesc((objTaxDtl[6].toString()));
					objTaxDtlBean.setDblTaxAmt(Double.parseDouble(objTaxDtl[7].toString()));

					listTaxDtlBeans.add(objTaxDtlBean);
				}

				// get tax detail by bill generated
				sbSql.setLength(0);
				sbSql.append("select a.strRegistrationNo,DATE_FORMAT(b.dteDocDate,'%d-%m-%Y'),c.strDocNo,a.strFolioNo,a.strBillNo" + " ,d.strIncomeHeadDesc,c.strTaxDesc,c.dblTaxAmt " + " from tblbillhd a, tblbilldtl b, tblbilltaxdtl c, tblincomehead d " + " where a.strBillNo=b.strBillNo and b.strBillNo=c.strBillNo and b.strDocNo=c.strDocNo "
						+ " and b.strRevenueCode=d.strIncomeHeadCode and a.strRegistrationNo='" + registrationNo + "' " + " and b.strRevenueType='Income Head' ");
				List listTaxDtlForBill = objGlobalFunctionsService.funGetDataList(sbSql.toString(), "sql");// sql
				for (int t = 0; t < listTaxDtlForBill.size(); t++) {
					Object[] objTaxDtl = (Object[]) listTaxDtlForBill.get(t);
					clsTaxDtlBean objTaxDtlBean = new clsTaxDtlBean();

					objTaxDtlBean.setDteDocDate(objTaxDtl[1].toString());
					objTaxDtlBean.setStrDocNo(objTaxDtl[2].toString());
					objTaxDtlBean.setStrFolioNo(objTaxDtl[3].toString());
					objTaxDtlBean.setStrBillNo(objTaxDtl[4].toString());
					objTaxDtlBean.setStrIncomeHead((objTaxDtl[5].toString()));
					objTaxDtlBean.setStrTaxDesc((objTaxDtl[6].toString()));
					objTaxDtlBean.setDblTaxAmt(Double.parseDouble(objTaxDtl[7].toString()));
					listTaxDtlBeans.add(objTaxDtlBean);
				}

				// get tax detail by folio generated for Rooms
				sbSql.setLength(0);
				sbSql.append("select a.strRegistrationNo,DATE_FORMAT(b.dteDocDate,'%d-%m-%Y'),c.strDocNo,a.strFolioNo,'NA'" + ",b.strPerticulars,c.strTaxDesc,c.dblTaxAmt " + " from tblfoliohd a, tblfoliodtl b, tblfoliotaxdtl c " + " where a.strFolioNo=b.strFolioNo and b.strFolioNo=c.strFolioNo and b.strDocNo=c.strDocNo " + " and a.strRegistrationNo='" + registrationNo
						+ "' and (b.strRevenueType='Room' or b.strRevenueType='ExtraBed') ");
				List listTaxDtlForRoomFolio = objGlobalFunctionsService.funGetDataList(sbSql.toString(), "sql");// sql
				for (int t = 0; t < listTaxDtlForRoomFolio.size(); t++) {
					Object[] objTaxDtl = (Object[]) listTaxDtlForRoomFolio.get(t);

					clsTaxDtlBean objTaxDtlBean = new clsTaxDtlBean();
					objTaxDtlBean.setDteDocDate(objTaxDtl[1].toString());
					objTaxDtlBean.setStrDocNo(objTaxDtl[2].toString());
					objTaxDtlBean.setStrFolioNo(objTaxDtl[3].toString());
					objTaxDtlBean.setStrBillNo(objTaxDtl[4].toString());
					objTaxDtlBean.setStrIncomeHead((objTaxDtl[5].toString()));
					objTaxDtlBean.setStrTaxDesc((objTaxDtl[6].toString()));
					objTaxDtlBean.setDblTaxAmt(Double.parseDouble(objTaxDtl[7].toString()));

					listTaxDtlBeans.add(objTaxDtlBean);
				}

				// get tax detail by bill generated for Rooms
				sbSql.setLength(0);
				sbSql.append("select a.strRegistrationNo,DATE_FORMAT(b.dteDocDate,'%d-%m-%Y'),c.strDocNo,a.strFolioNo,a.strBillNo" + " ,b.strPerticulars,c.strTaxDesc,c.dblTaxAmt " + " from tblbillhd a, tblbilldtl b ,tblbilltaxdtl c " + " where a.strBillNo=b.strBillNo and b.strBillNo=c.strBillNo and b.strDocNo=c.strDocNo " + " and a.strRegistrationNo='" + registrationNo
						+ "' and (b.strRevenueType='Room' or b.strRevenueType='ExtraBed') ");
				List listTaxDtlForRoomBill = objGlobalFunctionsService.funGetDataList(sbSql.toString(), "sql");// sql
				for (int t = 0; t < listTaxDtlForRoomBill.size(); t++) {
					Object[] objTaxDtl = (Object[]) listTaxDtlForRoomBill.get(t);
					clsTaxDtlBean objTaxDtlBean = new clsTaxDtlBean();

					objTaxDtlBean.setDteDocDate(objTaxDtl[1].toString());
					objTaxDtlBean.setStrDocNo(objTaxDtl[2].toString());
					objTaxDtlBean.setStrFolioNo(objTaxDtl[3].toString());
					objTaxDtlBean.setStrBillNo(objTaxDtl[4].toString());
					objTaxDtlBean.setStrIncomeHead((objTaxDtl[5].toString()));
					objTaxDtlBean.setStrTaxDesc((objTaxDtl[6].toString()));
					objTaxDtlBean.setDblTaxAmt(Double.parseDouble(objTaxDtl[7].toString()));
					listTaxDtlBeans.add(objTaxDtlBean);
				}

				// added tax detail
				rooTaxBean.setListClsTaxDtls(listTaxDtlBeans);
				dataList.add(rooTaxBean);
			}

			// income head wise summary
			List<clsTaxDtlBean> listIncomeHeadType = new ArrayList<>();
			sbSql.setLength(0);
			sbSql.append("select b.strRevenueType,sum(c.dblTaxAmt) " + " from tblbillhd a, tblbilldtl b, tblbilltaxdtl c " + " where a.strBillNo=b.strBillNo and b.strDocNo=c.strDocNo and b.strBillNo=c.strBillNo " + " and date(a.dteBillDate) between '" + fromDate + "' and '" + toDate + "' " + " group by b.strRevenueType ");
			List listIncomeHeadSummaryForBill = objGlobalFunctionsService.funGetDataList(sbSql.toString(), "sql");//
			for (int iht = 0; iht < listIncomeHeadSummaryForBill.size(); iht++) {
				Object[] objIncomeHeaTypes = (Object[]) listIncomeHeadSummaryForBill.get(iht);
				clsTaxDtlBean objTaxDtlBean = new clsTaxDtlBean();
				objTaxDtlBean.setStrIncomeHead((objIncomeHeaTypes[0].toString()));
				objTaxDtlBean.setDblTaxAmt(Double.parseDouble(objIncomeHeaTypes[1].toString()));
				listIncomeHeadType.add(objTaxDtlBean);
			}

			sbSql.setLength(0);
			sbSql.append("select b.strRevenueType,sum(c.dblTaxAmt) " + " from tblfoliohd a, tblfoliodtl b, tblfoliotaxdtl c " + " where a.strFolioNo=b.strFolioNo and b.strDocNo=c.strDocNo and b.strFolioNo=c.strFolioNo " + " and date(b.dteDocDate) between '" + fromDate + "' and '" + toDate + "' " + " group by b.strRevenueType ");
			List listIncomeHeadSummaryForFolio = objGlobalFunctionsService.funGetDataList(sbSql.toString(), "sql");//
			for (int iht = 0; iht < listIncomeHeadSummaryForFolio.size(); iht++) {
				Object[] objIncomeHeaTypes = (Object[]) listIncomeHeadSummaryForFolio.get(iht);
				clsTaxDtlBean objTaxDtlBean = new clsTaxDtlBean();
				objTaxDtlBean.setStrIncomeHead((objIncomeHeaTypes[0].toString()));
				objTaxDtlBean.setDblTaxAmt(Double.parseDouble(objIncomeHeaTypes[1].toString()));

				listIncomeHeadType.add(objTaxDtlBean);
			}
			reportParams.put("listIncomeHeadType", listIncomeHeadType);

			// tax wise summary
			List<clsTaxDtlBean> listTaxSummary = new ArrayList<>();
			sbSql.setLength(0);
			sbSql.append("select ifnull(c.strTaxDesc,''),ifnull(sum(c.dblTaxAmt),0) " + " from tblbillhd a left outer join tblbilldtl b on a.strBillNo=b.strBillNo " + " left outer join tblbilltaxdtl c on b.strDocNo=c.strDocNo and b.strBillNo=c.strBillNo " + " where date(a.dteBillDate) between '" + fromDate + "' and '" + toDate + "'  and c.strTaxDesc!='' " + " group by c.strTaxCode ");
			List listTaxWiseSummaryForBill = objGlobalFunctionsService.funGetDataList(sbSql.toString(), "sql");//
			for (int ts = 0; ts < listTaxWiseSummaryForBill.size(); ts++) {
				Object[] objTaxes = (Object[]) listTaxWiseSummaryForBill.get(ts);
				clsTaxDtlBean objTaxDtlBean = new clsTaxDtlBean();
				objTaxDtlBean.setStrTaxDesc((objTaxes[0].toString()));
				objTaxDtlBean.setDblTaxAmt(Double.parseDouble(objTaxes[1].toString()));

				listTaxSummary.add(objTaxDtlBean);
			}

			List listTaxWiseSummaryForFolio = objGlobalFunctionsService.funGetDataList("select c.strTaxDesc,sum(c.dblTaxAmt) " + "from tblfoliohd a left outer join tblfoliodtl b on a.strFolioNo=b.strFolioNo " + "left outer join tblfoliotaxdtl c on b.strDocNo=c.strDocNo and b.strFolioNo=c.strFolioNo " + "where date(b.dteDocDate) between '" + fromDate + "' and '" + toDate + "' "
					+ "group by c.strTaxCode ", "sql");//
			for (int ts = 0; ts < listTaxWiseSummaryForFolio.size(); ts++) {
				Object[] objTaxes = (Object[]) listTaxWiseSummaryForFolio.get(ts);
				clsTaxDtlBean objTaxDtlBean = new clsTaxDtlBean();
				objTaxDtlBean.setStrTaxDesc((objTaxes[0].toString()));
				objTaxDtlBean.setDblTaxAmt(Double.parseDouble(objTaxes[1].toString()));
				listTaxSummary.add(objTaxDtlBean);
			}

			reportParams.put("listTaxSummary", listTaxSummary);
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
		} finally {
			sbSql = null;
		}
	}
}
