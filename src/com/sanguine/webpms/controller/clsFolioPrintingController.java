package com.sanguine.webpms.controller;

import java.math.BigDecimal;
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
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webpms.bean.clsFolioPrintingBean;
import com.sanguine.webpms.dao.clsWebPMSDBUtilityDao;
import com.sanguine.webpms.service.clsFolioService;

@Controller
public class clsFolioPrintingController {
	@Autowired
	private clsFolioService objFolioService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsWebPMSDBUtilityDao objWebPMSUtility;

	// Open Folio Printing
	@RequestMapping(value = "/frmFolioPrinting", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmFolioPrinting_1", "command", new clsFolioPrintingBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmFolioPrinting", "command", new clsFolioPrintingBean());
		} else {
			return null;
		}
	}

	// Save folio posting
	@RequestMapping(value = "/rptFolioPrinting", method = RequestMethod.GET)
	public void funGenerateFolioPrintingReport(@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate, @RequestParam("folioNo") String folioNo, HttpServletRequest req, HttpServletResponse resp) {
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webpms/rptFolioPrinting.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			List<clsFolioPrintingBean> dataList = new ArrayList<clsFolioPrintingBean>();
			@SuppressWarnings("rawtypes")
			HashMap reportParams = new HashMap();

			String sqlParametersFromFolio = "SELECT a.strFolioNo,e.strRoomDesc,a.strRegistrationNo,a.strReservationNo " + " ,date(b.dteArrivalDate),b.tmeArrivalTime ,ifnull(date(b.dteDepartureDate),'NA'),ifnull(b.tmeDepartureTime,'NA')" + " ,d.strGuestPrefix,d.strFirstName,d.strMiddleName,d.strLastName ,b.intNoOfAdults,b.intNoOfChild,'NA' "
					+ " FROM tblfoliohd a LEFT OUTER JOIN tblreservationhd b ON a.strReservationNo=b.strReservationNo " + " LEFT OUTER JOIN tblguestmaster d ON a.strGuestCode=d.strGuestCode " + " LEFT OUTER JOIN tblroom e ON a.strRoomNo=e.strRoomCode " + " where a.strFolioNo='" + folioNo + "' and a.strClientCode='" + clientCode + "'";

			String sqlFolio = "select strReservationNo,strWalkInNo from tblfoliohd where strFolioNo='" + folioNo + "' and strClientCode='" + clientCode + "' ";
			List folioDtl = objFolioService.funGetParametersList(sqlFolio);
			if (folioDtl.size() > 0) {
				Object[] arrFolioDtl = (Object[]) folioDtl.get(0);
				if (!arrFolioDtl[1].toString().isEmpty()) {
					sqlParametersFromFolio = "SELECT a.strFolioNo,e.strRoomDesc,a.strRegistrationNo,a.strReservationNo " + " ,date(b.dteWalkinDate),b.tmeWalkinTime ,ifnull(date(b.dteCheckOutDate),'NA'),ifnull(b.tmeCheckOutTime,'NA')" + " ,d.strGuestPrefix,d.strFirstName,d.strMiddleName,d.strLastName ,b.intNoOfAdults,b.intNoOfChild,'NA' "
							+ " FROM tblfoliohd a LEFT OUTER JOIN tblwalkinhd b ON a.strWalkinNo=b.strWalkinNo " + " LEFT OUTER JOIN tblguestmaster d ON a.strGuestCode=d.strGuestCode " + " LEFT OUTER JOIN tblroom e ON a.strRoomNo=e.strRoomCode " + " where a.strFolioNo='" + folioNo + "' and a.strClientCode='" + clientCode + "'";
				}
			}

			// get all parameters

			List listOfParametersFromFolio = objFolioService.funGetParametersList(sqlParametersFromFolio);
			if (listOfParametersFromFolio.size() > 0) {
				Object[] arr = (Object[]) listOfParametersFromFolio.get(0);

				String folio = arr[0].toString();
				String roomNo = arr[1].toString();
				String registrationNo = arr[2].toString();
				String reservationNo = arr[3].toString();
				String arrivalDate = arr[4].toString();
				String arrivalTime = arr[5].toString();
				String departureDate = arr[6].toString();
				String departureTime = arr[7].toString();
				String gPrefix = arr[8].toString();
				String gFirstName = arr[9].toString();
				String gMiddleName = arr[10].toString();
				String gLastName = arr[11].toString();
				String adults = arr[12].toString();
				String childs = arr[13].toString();
				String billNo = arr[14].toString();

				reportParams.put("pCompanyName", companyName);
				reportParams.put("pAddress1", objSetup.getStrAdd1() + "," + objSetup.getStrAdd2() + "," + objSetup.getStrCity());
				reportParams.put("pAddress2", objSetup.getStrState() + "," + objSetup.getStrCountry() + "," + objSetup.getStrPin());
				reportParams.put("pContactDetails", "");
				reportParams.put("strImagePath", imagePath);
				reportParams.put("pGuestName", gPrefix + " " + gFirstName + " " + gMiddleName + " " + gLastName);
				reportParams.put("pFolioNo", folio);
				reportParams.put("pRoomNo", roomNo);
				reportParams.put("pRegistrationNo", registrationNo);
				reportParams.put("pReservationNo", reservationNo);
				reportParams.put("pArrivalDate", arrivalDate);
				reportParams.put("pArrivalTime", arrivalTime);
				reportParams.put("pDepartureDate", departureDate);
				reportParams.put("pDepartureTime", departureTime);
				reportParams.put("pAdult", adults);
				reportParams.put("pChild", childs);
				reportParams.put("pGuestAddress", "");
				reportParams.put("pRemarks", "");
				reportParams.put("strUserCode", userCode);
				reportParams.put("pBillNo", billNo);

				// get folio details
				String sqlFolioDtl = "SELECT DATE_FORMAT(b.dteDocDate,'%Y-%m-%d'),b.strDocNo,b.strPerticulars,b.dblDebitAmt,b.dblCreditAmt,b.dblBalanceAmt " + " FROM tblfoliohd a LEFT OUTER JOIN tblfoliodtl b ON a.strFolioNo=b.strFolioNo " + " WHERE DATE(b.dteDocDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " AND a.strFolioNo='" + folioNo + "' and b.strRevenueType!='Discount'"
									+ " order by b.strRevenueType desc";
				List folioDtlList = objFolioService.funGetParametersList(sqlFolioDtl);
				for (int i = 0; i < folioDtlList.size(); i++) {
					Object[] folioArr = (Object[]) folioDtlList.get(i);
					String docDate = folioArr[0].toString();
					if (folioArr[1] == null) {
						continue;
					} else {
						clsFolioPrintingBean folioPrintingBean = new clsFolioPrintingBean();
						String docNo = folioArr[1].toString();
						String particulars = folioArr[2].toString();
						double debitAmount = Double.parseDouble(folioArr[3].toString());
						double creditAmount = Double.parseDouble(folioArr[4].toString());
						double balance = debitAmount - creditAmount;

						folioPrintingBean.setDteDocDate(docDate);
						folioPrintingBean.setStrDocNo(docNo);
						folioPrintingBean.setStrPerticulars(particulars);
						folioPrintingBean.setDblDebitAmt(debitAmount);
						folioPrintingBean.setDblCreditAmt(creditAmount);
						folioPrintingBean.setDblBalanceAmt(balance);

						dataList.add(folioPrintingBean);
						

						sqlFolioDtl = "SELECT a.dteDocDate,a.strDocNo,b.strTaxDesc,b.dblTaxAmt,0,0 " + " FROM tblfoliodtl a,tblfoliotaxdtl b where a.strDocNo=b.strDocNo " + " and DATE(a.dteDocDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " AND a.strFolioNo='" + folioNo + "' and a.strDocNo='" + docNo + "'";
						List listFolioTaxDtl = objWebPMSUtility.funExecuteQuery(sqlFolioDtl, "sql");
						for (int cnt = 0; cnt < listFolioTaxDtl.size(); cnt++) {
							Object[] arrObjFolioTaxDtl = (Object[]) listFolioTaxDtl.get(cnt);

							folioPrintingBean = new clsFolioPrintingBean();
							folioPrintingBean.setDteDocDate(arrObjFolioTaxDtl[0].toString());
							folioPrintingBean.setStrDocNo(arrObjFolioTaxDtl[1].toString());
							folioPrintingBean.setStrPerticulars(arrObjFolioTaxDtl[2].toString());
							folioPrintingBean.setDblDebitAmt(Double.parseDouble(arrObjFolioTaxDtl[3].toString()));
							folioPrintingBean.setDblCreditAmt(Double.parseDouble(arrObjFolioTaxDtl[4].toString()));
							folioPrintingBean.setDblBalanceAmt(Double.parseDouble(arrObjFolioTaxDtl[3].toString()) - Double.parseDouble(arrObjFolioTaxDtl[4].toString()));
							dataList.add(folioPrintingBean);
						}
					}
				}
				
				sqlFolioDtl = "SELECT DATE_FORMAT(b.dteDocDate,'%Y-%m-%d'),b.strDocNo,b.strPerticulars,b.dblDebitAmt,b.dblCreditAmt,b.dblBalanceAmt,b.strRevenueType" 
						+ " FROM tblfoliohd a LEFT OUTER JOIN tblfoliodtl b ON a.strFolioNo=b.strFolioNo " 
						+ " WHERE DATE(b.dteDocDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " 
						+ " AND a.strFolioNo='" + folioNo + "' and b.strRevenueType='Discount'";
				folioDtlList = objFolioService.funGetParametersList(sqlFolioDtl);
				if(folioDtlList.size()>0)
				{
				for (int j = 0; j < folioDtlList.size(); j++) {
					clsFolioPrintingBean folioPrintingBean = new clsFolioPrintingBean();
					Object[] obj = (Object[])folioDtlList.get(0);
					BigDecimal bgDebit = (BigDecimal)obj[3];
					BigDecimal bgCredit = (BigDecimal)obj[4];
					folioPrintingBean.setDblCreditAmt(bgDebit.doubleValue());
					double balance = bgDebit.doubleValue() - bgCredit.doubleValue();

					folioPrintingBean.setDteDocDate(obj[0].toString());
					folioPrintingBean.setStrDocNo(obj[1].toString());
					folioPrintingBean.setStrPerticulars("Discount");
					folioPrintingBean.setDblDebitAmt(0);
					folioPrintingBean.setDblCreditAmt(bgCredit.doubleValue());
					folioPrintingBean.setDblBalanceAmt(balance);

					dataList.add(folioPrintingBean);
				}
				}
				
				// get payment details
				String sqlPaymentDtl = "SELECT b.dteDocDate,c.strReceiptNo,e.strSettlementDesc,'0.00' as debitAmt,d.dblSettlementAmt as creditAmt" + " ,'0.00' as balance " + " FROM tblfoliohd a LEFT OUTER JOIN tblfoliodtl b ON a.strFolioNo=b.strFolioNo " + " left outer join tblreceipthd c on a.strFolioNo=c.strFolioNo and a.strReservationNo=c.strReservationNo "
						+ " left outer join tblreceiptdtl d on c.strReceiptNo=d.strReceiptNo " + " left outer join tblsettlementmaster e on d.strSettlementCode=e.strSettlementCode " + " WHERE DATE(b.dteDocDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' AND a.strFolioNo='" + folioNo + "' " + " group by a.strFolioNo ";
				List paymentDtlList = objFolioService.funGetParametersList(sqlPaymentDtl);
				for (int i = 0; i < paymentDtlList.size(); i++) {
					Object[] paymentArr = (Object[]) paymentDtlList.get(i);

					String docDate = paymentArr[0].toString();
					if (paymentArr[1] == null) {
						continue;
					} else {
						clsFolioPrintingBean folioPrintingBean = new clsFolioPrintingBean();

						String docNo = paymentArr[1].toString();
						String particulars = paymentArr[2].toString();
						double debitAmount = Double.parseDouble(paymentArr[3].toString());
						double creditAmount = Double.parseDouble(paymentArr[4].toString());
						double balance = debitAmount - creditAmount;

						folioPrintingBean.setDteDocDate(docDate);
						folioPrintingBean.setStrDocNo(docNo);
						folioPrintingBean.setStrPerticulars(particulars);
						folioPrintingBean.setDblDebitAmt(debitAmount);
						folioPrintingBean.setDblCreditAmt(creditAmount);
						folioPrintingBean.setDblBalanceAmt(balance);

						dataList.add(folioPrintingBean);
					}
				}
			}

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
}
