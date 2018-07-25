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
import com.sanguine.webpms.bean.clsBillPrintingBean;
import com.sanguine.webpms.bean.clsFolioPrintingBean;
import com.sanguine.webpms.dao.clsWebPMSDBUtilityDao;
import com.sanguine.webpms.model.clsPropertySetupHdModel;
import com.sanguine.webpms.service.clsFolioService;
import com.sanguine.webpms.service.clsPropertySetupService;

@Controller
public class clsBillPrintingController {
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
	
	@Autowired
	private clsPropertySetupService objPropertySetupService;

	// Open Folio Printing
	@RequestMapping(value = "/frmBillPrinting", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmBillPrinting_1", "command", new clsBillPrintingBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmBillPrinting", "command", new clsBillPrintingBean());
		} else {
			return null;
		}
	}

	// Save folio posting
	@RequestMapping(value = "/rptBillPrinting", method = RequestMethod.GET)
	public void funGenerateBillPrintingReport(@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate, @RequestParam("billNo") String billNo, HttpServletRequest req, HttpServletResponse resp) {
		try {
			boolean flgBillRecord = false;
			String registrationNo = "";
			String reservationNo = "";
			double balance = 0.0;
			String GSTNo="",companyName="";
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode); //mms property setup
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webpms/rptBillPrinting.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			List<clsBillPrintingBean> dataList = new ArrayList<clsBillPrintingBean>();
			@SuppressWarnings("rawtypes")
			HashMap reportParams = new HashMap();

			// get all parameters
			/*
			 * String sqlParametersFromBill =
			 * "SELECT a.strFolioNo,a.strRoomNo,a.strRegistrationNo,a.strReservationNo"
			 * + " ,b.dteArrivalDate,b.tmeArrivalTime " +
			 * ",ifnull(b.dteDepartureDate,'NA'),ifnull(b.tmeDepartureTime,'NA') "
			 * +
			 * " ,ifnull(d.strGuestPrefix,''),ifnull(d.strFirstName,''),ifnull(d.strMiddleName,''),ifnull(d.strLastName,'') "
			 * + ",b.intNoOfAdults,b.intNoOfChild" + " ,a.strBillNo " +
			 * " FROM tblbillhd a LEFT OUTER JOIN tblreservationhd b ON a.strReservationNo=b.strReservationNo "
			 * +
			 * " LEFT OUTER JOIN tblreservationdtl c ON b.strReservationNo=c.strReservationNo AND a.strRoomNo=c.strRoomNo "
			 * +
			 * " LEFT OUTER JOIN tblguestmaster d ON c.strGuestCode=d.strGuestCode "
			 * + " where a.strBillNo='" + billNo + "' ";
			 */

			String sqlParametersFromBill = " SELECT a.strFolioNo,e.strRoomDesc,a.strRegistrationNo,a.strReservationNo ,date(b.dteArrivalDate),b.tmeArrivalTime , " + " ifnull(date(b.dteDepartureDate),'NA'),ifnull(b.tmeDepartureTime,'NA')  , ifnull(d.strGuestPrefix,''), " + " ifnull(d.strFirstName,''),ifnull(d.strMiddleName,''),ifnull(d.strLastName,'') , "
					+ " b.intNoOfAdults,b.intNoOfChild ,a.strBillNo ,d.strGuestCode,a.strGSTNo,a.strCompanyName"//17
					+ " FROM tblbillhd a  " + " LEFT OUTER JOIN tblcheckinhd  b ON a.strReservationNo=b.strReservationNo " + " LEFT OUTER JOIN tblcheckindtl c ON b.strCheckInNo=c.strCheckInNo AND a.strRoomNo=c.strRoomNo  " + " LEFT OUTER JOIN tblguestmaster d ON c.strGuestCode=d.strGuestCode  "
					+ " LEFT OUTER JOIN tblroom e ON e.strRoomCode=a.strRoomNo " + "where a.strBillNo='" + billNo + "'  ";

			List listOfParametersFromBill = objFolioService.funGetParametersList(sqlParametersFromBill);

			if (listOfParametersFromBill.size() > 0) {
				Object[] arr = (Object[]) listOfParametersFromBill.get(0);


				String guestDtl=" select ifnull(d.strDefaultAddr,''),ifnull(d.strAddressLocal,''),ifnull(d.strCityLocal,''),ifnull(d.strStateLocal,''),ifnull(d.strCountryLocal,''),IFNULL(d.intPinCodeLocal,''),"//20
						+ " ifnull(d.strAddrPermanent,''),ifnull(d.strCityPermanent,''),ifnull(d.strStatePermanent,''),ifnull(d.strCountryPermanent,''),IFNULL(d.intPinCodePermanent,''), "//25
						+ " ifnull(d.strAddressOfc,''),ifnull(d.strCityOfc,''),ifnull(d.strStateOfc,''),ifnull(d.strCountryOfc,''),IFNULL(d.intPinCodeOfc,'')"
						+ "from tblguestmaster d where d.strGuestCode='"+arr[15].toString()+"'";
				List listguest = objFolioService.funGetParametersList(guestDtl);
				
				String folio = arr[0].toString();
				String roomNo = arr[1].toString();
				registrationNo = arr[2].toString();
				reservationNo = arr[3].toString();
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
				if(!arr[16].toString().equals(""))
				{
					GSTNo=arr[16].toString();
				}
				if(!arr[17].toString().equals(""))
				{
					companyName=arr[17].toString();
				}
				String guestAddr="";
				if(listguest.size()>0){
					Object[] arrGuest = (Object[]) listguest.get(0);
					if(arrGuest[0].toString().equalsIgnoreCase("Permanent")){ //check default addr
						guestAddr=arrGuest[6].toString()+","+arrGuest[7].toString()+","+arrGuest[8].toString()+","+arrGuest[9].toString()+","+arrGuest[10].toString();
					}else if(arrGuest[0].toString().equalsIgnoreCase("Office")){
						guestAddr=arrGuest[11].toString()+","+arrGuest[12].toString()+","+arrGuest[13].toString()+","+arrGuest[14].toString()+","+arrGuest[15].toString();
					}else{ //Local
						guestAddr=arrGuest[1].toString()+","+arrGuest[2].toString()+","+arrGuest[3].toString()+","+arrGuest[4].toString()+","+arrGuest[5].toString();
					}
				}
				
				
				// String billNo = arr[14].toString();

				
				reportParams.put("pCompanyName", companyName);
				reportParams.put("pGSTNo", GSTNo);
				reportParams.put("pAddress1", objSetup.getStrAdd1() + "," + objSetup.getStrAdd2() + "," + objSetup.getStrCity());
				reportParams.put("pAddress2", objSetup.getStrState() + "," + objSetup.getStrCountry() + "," + objSetup.getStrPin());
				reportParams.put("pContactDetails", "");
				reportParams.put("strImagePath", imagePath);
				reportParams.put("pGuestName", gPrefix + " " + gFirstName + " " + gMiddleName + " " + gLastName);
				reportParams.put("pFolioNo", folio);
				reportParams.put("pRoomNo", roomNo);
				reportParams.put("pRegistrationNo", registrationNo);
				reportParams.put("pReservationNo", reservationNo);
				reportParams.put("pArrivalDate", objGlobal.funGetDate("dd-MM-yyyy", arrivalDate));
				reportParams.put("pArrivalTime", arrivalTime);
				reportParams.put("pDepartureDate", objGlobal.funGetDate("dd-MM-yyyy", departureDate));
				reportParams.put("pDepartureTime", departureTime);
				reportParams.put("pAdult", adults);
				reportParams.put("pChild", childs);
				reportParams.put("pGuestAddress", guestAddr);
				reportParams.put("pRemarks", "");
				reportParams.put("strUserCode", userCode);
				reportParams.put("pBillNo", billNo);

				// get bill details
				String sqlBillDtl = "SELECT date(b.dteDocDate),b.strDocNo,b.strPerticulars,b.dblDebitAmt,b.dblCreditAmt,b.dblBalanceAmt " + " FROM tblbillhd a inner join tblbilldtl b ON a.strFolioNo=b.strFolioNo  and a.strBillNo=b.strBillNo " + " WHERE a.strBillNo='" + billNo + "' ";
				// + " and DATE(b.dteDocDate) BETWEEN '" + fromDate + "' AND '"
				// + toDate + "' ";
				List billDtlList = objFolioService.funGetParametersList(sqlBillDtl);
				for (int i = 0; i < billDtlList.size(); i++) {
					Object[] folioArr = (Object[]) billDtlList.get(i);

					String docDate = folioArr[0].toString();
					if (folioArr[1] == null) {
						continue;
					} else {
						clsBillPrintingBean billPrintingBean = new clsBillPrintingBean();

						String docNo = folioArr[1].toString();
						String particulars = folioArr[2].toString();
						double debitAmount = Double.parseDouble(folioArr[3].toString());
						double creditAmount = Double.parseDouble(folioArr[4].toString());
						balance = balance + debitAmount - creditAmount;
						
//						String debitAmount = folioArr[3].toString();
//						String creditAmount = folioArr[4].toString();
//						String balance = folioArr[5].toString();

						billPrintingBean.setDteDocDate(objGlobal.funGetDate("dd-MM-yyyy", (docDate))); 
						billPrintingBean.setStrDocNo(docNo);
						billPrintingBean.setStrPerticulars(particulars);
						billPrintingBean.setDblDebitAmt(debitAmount);
						billPrintingBean.setDblCreditAmt(creditAmount);
						billPrintingBean.setDblBalanceAmt(balance);

						dataList.add(billPrintingBean);

						sqlBillDtl = "SELECT date(a.dteDocDate),a.strDocNo,b.strTaxDesc,b.dblTaxAmt,0,0 " + " FROM tblbilldtl a, tblbilltaxdtl b where a.strDocNo=b.strDocNo " + " AND a.strBillNo='" + billNo + "' and a.strDocNo='" + docNo + "' ";
						// + " and DATE(a.dteDocDate) BETWEEN '" + fromDate +
						// "' AND '" + toDate + "' ";
						List listBillTaxDtl = objWebPMSUtility.funExecuteQuery(sqlBillDtl, "sql");
						for (int cnt = 0; cnt < listBillTaxDtl.size(); cnt++) {
							Object[] arrObjBillTaxDtl = (Object[]) listBillTaxDtl.get(cnt);

							billPrintingBean = new clsBillPrintingBean();
							billPrintingBean.setDteDocDate(arrObjBillTaxDtl[0].toString());
							billPrintingBean.setStrDocNo(arrObjBillTaxDtl[1].toString());
							billPrintingBean.setStrPerticulars(arrObjBillTaxDtl[2].toString());
							
							double debitAmt = Double.parseDouble(arrObjBillTaxDtl[3].toString());
							double creditAmt = Double.parseDouble(arrObjBillTaxDtl[4].toString());
							balance = balance + debitAmt - creditAmt;
							
							billPrintingBean.setDblDebitAmt(debitAmt);
							billPrintingBean.setDblCreditAmt(creditAmt);
							billPrintingBean.setDblBalanceAmt(balance);
							dataList.add(billPrintingBean);
						}
					}
				}

				flgBillRecord = true;
			}

			if (flgBillRecord) {
				// get payment details

				String sqlPaymentDtl = "SELECT date(c.dteReceiptDate),c.strReceiptNo,e.strSettlementDesc,'0.00' as debitAmt " + " ,d.dblSettlementAmt as creditAmt,'0.00' as balance " + " FROM tblreceipthd c, tblreceiptdtl d, tblsettlementmaster e " + " where c.strReceiptNo=d.strReceiptNo and d.strSettlementCode=e.strSettlementCode " + " and c.strReservationNo='" + reservationNo
						+ "' and c.strAgainst='Reservation' ";

				List paymentDtlList = objFolioService.funGetParametersList(sqlPaymentDtl);
				for (int i = 0; i < paymentDtlList.size(); i++) {
					Object[] paymentArr = (Object[]) paymentDtlList.get(i);

					String docDate = paymentArr[0].toString();
					if (paymentArr[1] == null) {
						continue;
					} else {
						clsBillPrintingBean folioPrintingBean = new clsBillPrintingBean();
						String docNo = paymentArr[1].toString();
						String particulars = paymentArr[2].toString();
						double debitAmount = Double.parseDouble(paymentArr[3].toString());
						double creditAmount = Double.parseDouble(paymentArr[4].toString());
						balance = balance + debitAmount - creditAmount;
						
//						String debitAmount = paymentArr[3].toString();
//						String creditAmount = paymentArr[4].toString();
//						String balance = paymentArr[5].toString();

						folioPrintingBean.setDteDocDate(objGlobal.funGetDate("dd-MM-yyyy", (docDate))); 
						folioPrintingBean.setStrDocNo(docNo);
						folioPrintingBean.setStrPerticulars(particulars);
						folioPrintingBean.setDblDebitAmt(debitAmount);
						folioPrintingBean.setDblCreditAmt(creditAmount);
						folioPrintingBean.setDblBalanceAmt(balance);

						dataList.add(folioPrintingBean);
					}
				}

				if (!(paymentDtlList.size() > 0)) {
					sqlPaymentDtl = "SELECT date(c.dteReceiptDate),c.strReceiptNo,e.strSettlementDesc,'0.00' as debitAmt " + " ,d.dblSettlementAmt as creditAmt,'0.00' as balance " + " FROM tblreceipthd c, tblreceiptdtl d, tblsettlementmaster e " + " where c.strReceiptNo=d.strReceiptNo and d.strSettlementCode=e.strSettlementCode " + " and c.strRegistrationNo='" + registrationNo
							+ "' and c.strAgainst='Check-In' ";

					List checkInReceiptDtl = objFolioService.funGetParametersList(sqlPaymentDtl);
					for (int i = 0; i < checkInReceiptDtl.size(); i++) {
						Object[] paymentArr = (Object[]) checkInReceiptDtl.get(i);

						String docDate = paymentArr[0].toString();
						if (paymentArr[1] == null) {
							continue;
						} else {
							clsBillPrintingBean folioPrintingBean = new clsBillPrintingBean();
							String docNo = paymentArr[1].toString();
							String particulars = paymentArr[2].toString();
							
							double debitAmount = Double.parseDouble(paymentArr[3].toString());
							double creditAmount = Double.parseDouble(paymentArr[4].toString());
							balance = balance + debitAmount - creditAmount;
							
//							String debitAmount = paymentArr[3].toString();
//							String creditAmount = paymentArr[4].toString();
//							String balance = paymentArr[5].toString();

							folioPrintingBean.setDteDocDate(objGlobal.funGetDate("dd-MM-yyyy", (docDate))); 
							folioPrintingBean.setStrDocNo(docNo);
							folioPrintingBean.setStrPerticulars(particulars);
							folioPrintingBean.setDblDebitAmt(debitAmount);
							folioPrintingBean.setDblCreditAmt(creditAmount);
							folioPrintingBean.setDblBalanceAmt(balance);

							dataList.add(folioPrintingBean);
						}
					}

				}

				/*
				 * String sqlPaymentDtl =
				 * "SELECT date(b.dteDocDate),c.strReceiptNo,e.strSettlementDesc,'0.00' as debitAmt"
				 * + " ,d.dblSettlementAmt as creditAmt,'0.00' as balance " +
				 * " FROM tblbillhd a " +
				 * "LEFT OUTER JOIN tblbilldtl b ON a.strFolioNo=b.strFolioNo "
				 * +
				 * " left outer join tblreceipthd c on a.strFolioNo=c.strFolioNo and a.strReservationNo=c.strReservationNo "
				 * +
				 * " left outer join tblreceiptdtl d on c.strReceiptNo=d.strReceiptNo "
				 * +
				 * " left outer join tblsettlementmaster e on d.strSettlementCode=e.strSettlementCode "
				 * + " WHERE a.strBillNo='" + billNo + "' "; //+
				 * " and DATE(b.dteDocDate) BETWEEN '" + fromDate + "' AND '" +
				 * toDate + "'"
				 */

				sqlPaymentDtl = "SELECT date(c.dteReceiptDate),c.strReceiptNo,e.strSettlementDesc,'0.00' as debitAmt " + " ,d.dblSettlementAmt as creditAmt,'0.00' as balance " + " FROM tblbillhd a,tblreceipthd c, tblreceiptdtl d, tblsettlementmaster e " + " where a.strBillNo=c.strBillNo and c.strReceiptNo=d.strReceiptNo and d.strSettlementCode=e.strSettlementCode " + " and a.strBillNo='" + billNo
						+ "' and c.strAgainst='Bill' ";

				List billReceitDtl = objFolioService.funGetParametersList(sqlPaymentDtl);
				for (int i = 0; i < billReceitDtl.size(); i++) {
					Object[] paymentArr = (Object[]) billReceitDtl.get(i);

					String docDate = paymentArr[0].toString();
					if (paymentArr[1] == null) {
						continue;
					} else {
						clsBillPrintingBean folioPrintingBean = new clsBillPrintingBean();
						String docNo = paymentArr[1].toString();
						String particulars = paymentArr[2].toString();
						
						double debitAmount = Double.parseDouble(paymentArr[3].toString());
						double creditAmount = Double.parseDouble(paymentArr[4].toString());
						balance = balance + debitAmount - creditAmount;
						
//						String debitAmount = paymentArr[3].toString();
//						String creditAmount = paymentArr[4].toString();
//						String balance = paymentArr[5].toString();

						folioPrintingBean.setDteDocDate(objGlobal.funGetDate("dd-MM-yyyy", (docDate))); 
						folioPrintingBean.setStrDocNo(docNo);
						folioPrintingBean.setStrPerticulars(particulars);
						folioPrintingBean.setDblDebitAmt(debitAmount);
						folioPrintingBean.setDblCreditAmt(creditAmount);
						folioPrintingBean.setDblBalanceAmt(balance);

						dataList.add(folioPrintingBean);
					}
				}

				String sqlDisc = " select date(a.dteBillDate),'','Discount','0.00',a.dblDiscAmt,'0.00' from  tblbilldiscount a " + " WHERE a.strBillNo='" + billNo + "' and strClientCode='" + clientCode + "' ";

				List billDiscList = objFolioService.funGetParametersList(sqlDisc);
				for (int i = 0; i < billDiscList.size(); i++) {
					Object[] billDicArr = (Object[]) billDiscList.get(i);

					clsBillPrintingBean folioPrintingBean = new clsBillPrintingBean();
					String docDate = billDicArr[0].toString();
					String docNo = billDicArr[1].toString();
					String particulars = billDicArr[2].toString();
					
					double debitAmount = Double.parseDouble(billDicArr[3].toString());
					double creditAmount = Double.parseDouble(billDicArr[4].toString());
					balance = balance + debitAmount - creditAmount;
					
//					String debitAmount = billDicArr[3].toString();
//					String creditAmount = billDicArr[4].toString();
//					String balance = billDicArr[5].toString();

					folioPrintingBean.setDteDocDate(objGlobal.funGetDate("dd-MM-yyyy", (docDate))); 
					folioPrintingBean.setStrDocNo(docNo);
					folioPrintingBean.setStrPerticulars(particulars);
					folioPrintingBean.setDblDebitAmt(debitAmount);
					folioPrintingBean.setDblCreditAmt(creditAmount);
					folioPrintingBean.setDblBalanceAmt(balance);

					dataList.add(folioPrintingBean);

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
				resp.setHeader("Content-Disposition", "inline;filename=Bill.pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/rptBillPrintingForCheckIn", method = RequestMethod.GET)
	public void funGenerateBillPrintingReporForCheckIn(@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate, @RequestParam("checkInNo") String checkInNo, @RequestParam("against") String against, HttpServletRequest req, HttpServletResponse resp) {
		try {
			boolean flgBillRecord = false;
			String registrationNo = "";
			String reservationNo = "";
			String GSTNo="",companyName="";
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			List billNo = new ArrayList();
			String folio = "";
			String roomNo = "";

			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webpms/rptBillPrintingForCheckIn.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			List<clsBillPrintingBean> dataList = new ArrayList<clsBillPrintingBean>();
			@SuppressWarnings("rawtypes")
			HashMap reportParams = new HashMap();

			// String sqlParametersFromBill =
			// " SELECT a.strFolioNo,a.strRoomNo,a.strRegistrationNo,a.strReservationNo ,date(b.dteArrivalDate),b.tmeArrivalTime , "
			// +
			// " ifnull(date(b.dteDepartureDate),'NA'),ifnull(b.tmeDepartureTime,'NA')  , ifnull(d.strGuestPrefix,''), "
			// +
			// " ifnull(d.strFirstName,''),ifnull(d.strMiddleName,''),ifnull(d.strLastName,'') , "
			// + " b.intNoOfAdults,b.intNoOfChild ,a.strBillNo  "
			// + " FROM tblbillhd a  "
			// +
			// " LEFT OUTER JOIN tblcheckinhd  b ON a.strReservationNo=b.strReservationNo "
			// +
			// " LEFT OUTER JOIN tblcheckindtl c ON b.strCheckInNo=c.strCheckInNo AND a.strRoomNo=c.strRoomNo  "
			// +
			// " LEFT OUTER JOIN tblguestmaster d ON c.strGuestCode=d.strGuestCode  where a.strBillNo='"+billNo+"'  ";

			String sqlParametersFromBill = "SELECT a.strFolioNo,a.strRoomNo,a.strRegistrationNo,a.strReservationNo, DATE(b.dteArrivalDate),b.tmeArrivalTime, "
					+ " IFNULL(DATE(b.dteDepartureDate),'NA'), IFNULL(b.tmeDepartureTime,'NA'), IFNULL(d.strGuestPrefix,''),"
					+ " IFNULL(d.strFirstName,''), IFNULL(d.strMiddleName,''), IFNULL(d.strLastName,''), b.intNoOfAdults,b.intNoOfChild,"
					+ " a.strBillNo,IFNULL(d.strDefaultAddr,''), IFNULL(d.strAddressLocal,''), IFNULL(d.strCityLocal,''), "
					+ " IFNULL(d.strStateLocal,''), IFNULL(d.strCountryLocal,''),d.intPinCodeLocal, IFNULL(d.strAddrPermanent,''), "
					+ " IFNULL(d.strCityPermanent,''), IFNULL(d.strStatePermanent,''), IFNULL(d.strCountryPermanent,''),d.intPinCodePermanent, "
					+ " IFNULL(d.strAddressOfc,''), IFNULL(d.strCityOfc,''), IFNULL(d.strStateOfc,''), "
					+ " IFNULL(d.strCountryOfc,''),d.intPinCodeOfc,a.strGSTNo,a.strCompanyName "
					+ " FROM tblbillhd a LEFT OUTER JOIN tblcheckinhd b ON a.strReservationNo=b.strReservationNo"
					+ " LEFT OUTER JOIN tblcheckindtl c ON b.strCheckInNo=c.strCheckInNo AND a.strRoomNo=c.strRoomNo"
					+ " LEFT OUTER JOIN tblguestmaster d ON c.strGuestCode=d.strGuestCode"
					+ " WHERE a.strCheckInNo='"+checkInNo+"' AND c.strPayee='Y' ";
			
			List listOfParametersFromBill = objFolioService.funGetParametersList(sqlParametersFromBill);

			if (listOfParametersFromBill.size() > 0) {
				Object[] arr = (Object[]) listOfParametersFromBill.get(0);

				// String folio = arr[0].toString();
				// String roomNo = arr[1].toString();
				registrationNo = arr[2].toString();
				reservationNo = arr[3].toString();
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
				// String billNo = arr[14].toString();
				
				String guestAddr="";
				if(arr[15].toString().equalsIgnoreCase("Permanent")){ //check default addr
					guestAddr=arr[21].toString()+","+arr[22].toString()+","+arr[23].toString()+","+arr[24].toString()+","+arr[25].toString();
				}else if(arr[15].toString().equalsIgnoreCase("Office")){
					guestAddr=arr[26].toString()+","+arr[27].toString()+","+arr[28].toString()+","+arr[29].toString()+","+arr[30].toString();
				}else{ //Local
					guestAddr=arr[16].toString()+","+arr[17].toString()+","+arr[18].toString()+","+arr[19].toString()+","+arr[20].toString();
				}
				
				if(!arr[15].toString().equals(""))
				{
					GSTNo=arr[31].toString();
				}
				if(!arr[16].toString().equals(""))
				{
					companyName=arr[32].toString();
				}

				reportParams.put("pCompanyName", companyName);
				reportParams.put("pGSTNo", GSTNo);
				reportParams.put("pAddress1", objSetup.getStrAdd1() + "," + objSetup.getStrAdd2() + "," + objSetup.getStrCity());
				reportParams.put("pAddress2", objSetup.getStrState() + "," + objSetup.getStrCountry() + "," + objSetup.getStrPin());
				reportParams.put("pContactDetails", "");
				reportParams.put("strImagePath", imagePath);
				reportParams.put("pGuestName", gPrefix + " " + gFirstName + " " + gMiddleName + " " + gLastName);
				// reportParams.put("pFolioNo", folio);
				// reportParams.put("pRoomNo", roomNo);
				reportParams.put("pRegistrationNo", registrationNo);
				reportParams.put("pReservationNo", reservationNo);
				reportParams.put("pArrivalDate", objGlobal.funGetDate("dd-MM-yyyy", arrivalDate));
				reportParams.put("pArrivalTime", arrivalTime);
				reportParams.put("pDepartureDate", objGlobal.funGetDate("dd-MM-yyyy", departureDate));
				reportParams.put("pDepartureTime", departureTime);
				reportParams.put("pAdult", adults);
				reportParams.put("pChild", childs);
				reportParams.put("pGuestAddress", guestAddr);
				reportParams.put("pRemarks", "");
				reportParams.put("strUserCode", userCode);
				reportParams.put("checkInNo", checkInNo);
				reportParams.put("pBillNo", arr[14].toString());

				// get bill details
				String sqlBillDtl = "SELECT date(b.dteDocDate),b.strDocNo,b.strPerticulars,b.dblDebitAmt,b.dblCreditAmt,b.dblBalanceAmt,a.strBillNo,c.strRoomDesc,a.strFolioNo" + " FROM tblbillhd a inner join tblbilldtl b ON a.strFolioNo=b.strFolioNo ,tblroom c " + " WHERE a.strCheckInNo='" + checkInNo + "' and a.strBillNo=b.strBillNo and a.strRoomNo=c.strRoomCode ORDER BY a.strBillNo";
				//
				// +
				// "and a.dteBillDate between '"+fromDate+"' and '"+toDate+"' "

				List billDtlList = objFolioService.funGetParametersList(sqlBillDtl);
				for (int i = 0; i < billDtlList.size(); i++) {
					Object[] folioArr = (Object[]) billDtlList.get(i);

					String docDate = folioArr[0].toString();
					if (folioArr[1] == null) {
						continue;
					} else {
						clsBillPrintingBean billPrintingBean = new clsBillPrintingBean();

						String docNo = folioArr[1].toString();
						String strPerticulars = folioArr[2].toString();
						String debitAmount = folioArr[3].toString();
						String creditAmount = folioArr[4].toString();
						String balance = folioArr[5].toString();
						if (!billNo.contains(folioArr[6].toString())) {
							billNo.add(folioArr[6].toString());
						}
						if (!roomNo.contains(folioArr[7].toString())) {
							roomNo = roomNo + "," + folioArr[7].toString();
						}
						if (!folio.contains(folioArr[8].toString())) {
							folio = folio + "," + folioArr[8].toString();
						}

						billPrintingBean.setDteDocDate(objGlobal.funGetDate("dd-MM-yyyy", (docDate))); 
						billPrintingBean.setStrDocNo(docNo);
						billPrintingBean.setStrPerticulars(strPerticulars);
						billPrintingBean.setDblDebitAmt(Double.parseDouble(debitAmount));
						billPrintingBean.setDblCreditAmt(Double.parseDouble(creditAmount));
						billPrintingBean.setDblBalanceAmt(Double.parseDouble(balance));

						dataList.add(billPrintingBean);

						sqlBillDtl = " SELECT date(a.dteDocDate),a.strDocNo,b.strTaxDesc,b.dblTaxAmt,0,0 " + " FROM tblbilldtl a, tblbilltaxdtl b where a.strDocNo=b.strDocNo  " + " AND a.strBillNo='" + folioArr[6].toString() + "'  and a.strDocNo='" + docNo + "'  ";
						// + " and DATE(a.dteDocDate) BETWEEN '" + fromDate +
						// "' AND '" + toDate + "' ";
						List listBillTaxDtl = objWebPMSUtility.funExecuteQuery(sqlBillDtl, "sql");
						for (int cnt = 0; cnt < listBillTaxDtl.size(); cnt++) {
							Object[] arrObjBillTaxDtl = (Object[]) listBillTaxDtl.get(cnt);

							billPrintingBean = new clsBillPrintingBean();
							billPrintingBean.setDteDocDate(arrObjBillTaxDtl[0].toString());
							billPrintingBean.setStrDocNo(arrObjBillTaxDtl[1].toString());
							billPrintingBean.setStrPerticulars(arrObjBillTaxDtl[2].toString());
							billPrintingBean.setDblDebitAmt(Double.parseDouble(arrObjBillTaxDtl[3].toString()));
							billPrintingBean.setDblCreditAmt(Double.parseDouble(arrObjBillTaxDtl[4].toString()));
							billPrintingBean.setDblBalanceAmt(Double.parseDouble(arrObjBillTaxDtl[5].toString()));
							dataList.add(billPrintingBean);
						}
					}
				}

				flgBillRecord = true;
			}

			if (flgBillRecord) {
				// get payment details

				String sqlPaymentDtl = "SELECT date(c.dteReceiptDate),c.strReceiptNo,e.strSettlementDesc,'0.00' as debitAmt " + " ,d.dblSettlementAmt as creditAmt,'0.00' as balance " + " FROM tblreceipthd c, tblreceiptdtl d, tblsettlementmaster e " + " where c.strReceiptNo=d.strReceiptNo and d.strSettlementCode=e.strSettlementCode " + " and c.strReservationNo='" + reservationNo
						+ "' and c.strAgainst='Reservation' ";

				List paymentDtlList = objFolioService.funGetParametersList(sqlPaymentDtl);
				for (int i = 0; i < paymentDtlList.size(); i++) {
					Object[] paymentArr = (Object[]) paymentDtlList.get(i);

					String docDate = paymentArr[0].toString();
					if (paymentArr[1] == null) {
						continue;
					} else {
						clsBillPrintingBean folioPrintingBean = new clsBillPrintingBean();
						String docNo = paymentArr[1].toString();
						String particulars = paymentArr[2].toString();
						String debitAmount = paymentArr[3].toString();
						String creditAmount = paymentArr[4].toString();
						String balance = paymentArr[5].toString();

						folioPrintingBean.setDteDocDate(objGlobal.funGetDate("dd-MM-yyyy", (docDate))); 
						folioPrintingBean.setStrDocNo(docNo);
						folioPrintingBean.setStrPerticulars(particulars);
						folioPrintingBean.setDblDebitAmt(Double.parseDouble(debitAmount));
						folioPrintingBean.setDblCreditAmt(Double.parseDouble(creditAmount));
						folioPrintingBean.setDblBalanceAmt(Double.parseDouble(balance));

						dataList.add(folioPrintingBean);
					}
				}

				if (!(paymentDtlList.size() > 0)) {
					sqlPaymentDtl = "SELECT date(c.dteReceiptDate),c.strReceiptNo,e.strSettlementDesc,'0.00' as debitAmt " + " ,d.dblSettlementAmt as creditAmt,'0.00' as balance " + " FROM tblreceipthd c, tblreceiptdtl d, tblsettlementmaster e " + " where c.strReceiptNo=d.strReceiptNo and d.strSettlementCode=e.strSettlementCode " + " and c.strRegistrationNo='" + registrationNo
							+ "' and c.strAgainst='Check-In' ";

					List checkInReceiptDtl = objFolioService.funGetParametersList(sqlPaymentDtl);
					for (int i = 0; i < checkInReceiptDtl.size(); i++) {
						Object[] paymentArr = (Object[]) checkInReceiptDtl.get(i);

						String docDate = paymentArr[0].toString();
						if (paymentArr[1] == null) {
							continue;
						} else {
							clsBillPrintingBean folioPrintingBean = new clsBillPrintingBean();
							String docNo = paymentArr[1].toString();
							String particulars = paymentArr[2].toString();
							String debitAmount = paymentArr[3].toString();
							String creditAmount = paymentArr[4].toString();
							String balance = paymentArr[5].toString();

							folioPrintingBean.setDteDocDate(objGlobal.funGetDate("dd-MM-yyyy", (docDate))); 
							folioPrintingBean.setStrDocNo(docNo);
							folioPrintingBean.setStrPerticulars(particulars);
							folioPrintingBean.setDblDebitAmt(Double.parseDouble(debitAmount));
							folioPrintingBean.setDblCreditAmt(Double.parseDouble(creditAmount));
							folioPrintingBean.setDblBalanceAmt(Double.parseDouble(balance));

							dataList.add(folioPrintingBean);
						}
					}

				}

				/*
				 * String sqlPaymentDtl =
				 * "SELECT date(b.dteDocDate),c.strReceiptNo,e.strSettlementDesc,'0.00' as debitAmt"
				 * + " ,d.dblSettlementAmt as creditAmt,'0.00' as balance " +
				 * " FROM tblbillhd a " +
				 * "LEFT OUTER JOIN tblbilldtl b ON a.strFolioNo=b.strFolioNo "
				 * +
				 * " left outer join tblreceipthd c on a.strFolioNo=c.strFolioNo and a.strReservationNo=c.strReservationNo "
				 * +
				 * " left outer join tblreceiptdtl d on c.strReceiptNo=d.strReceiptNo "
				 * +
				 * " left outer join tblsettlementmaster e on d.strSettlementCode=e.strSettlementCode "
				 * + " WHERE a.strBillNo='" + billNo + "' "; //+
				 * " and DATE(b.dteDocDate) BETWEEN '" + fromDate + "' AND '" +
				 * toDate + "'"
				 */

				for (int cnt = 0; cnt < billNo.size(); cnt++) {
					sqlPaymentDtl = "SELECT date(c.dteReceiptDate),c.strReceiptNo,e.strSettlementDesc,'0.00' as debitAmt " + " ,d.dblSettlementAmt as creditAmt,'0.00' as balance " + " FROM tblbillhd a,tblreceipthd c, tblreceiptdtl d, tblsettlementmaster e " + " where a.strBillNo=c.strBillNo and c.strReceiptNo=d.strReceiptNo and d.strSettlementCode=e.strSettlementCode " + " and a.strBillNo='"
							+ billNo.get(cnt) + "' and c.strAgainst='Bill' ";

					List billReceitDtl = objFolioService.funGetParametersList(sqlPaymentDtl);
					for (int i = 0; i < billReceitDtl.size(); i++) {
						Object[] paymentArr = (Object[]) billReceitDtl.get(i);

						String docDate = paymentArr[0].toString();
						if (paymentArr[1] == null) {
							continue;
						} else {
							clsBillPrintingBean folioPrintingBean = new clsBillPrintingBean();
							String docNo = paymentArr[1].toString();
							String particulars = paymentArr[2].toString();
							String debitAmount = paymentArr[3].toString();
							String creditAmount = paymentArr[4].toString();
							String balance = paymentArr[5].toString();

							folioPrintingBean.setDteDocDate(objGlobal.funGetDate("dd-MM-yyyy", (docDate))); 
							folioPrintingBean.setStrDocNo(docNo);
							folioPrintingBean.setStrPerticulars(particulars);
							folioPrintingBean.setDblDebitAmt(Double.parseDouble(debitAmount));
							folioPrintingBean.setDblCreditAmt(Double.parseDouble(creditAmount));
							folioPrintingBean.setDblBalanceAmt(Double.parseDouble(balance));

							dataList.add(folioPrintingBean);
						}
					}

					String sqlDisc = " select date(a.dteBillDate),'','Discount','0.00',a.dblDiscAmt,'0.00' from  tblbilldiscount a " + " WHERE a.strBillNo='" + billNo.get(cnt) + "' and strClientCode='" + clientCode + "' ";

					List billDiscList = objFolioService.funGetParametersList(sqlDisc);
					for (int i = 0; i < billDiscList.size(); i++) {
						Object[] billDicArr = (Object[]) billDiscList.get(i);

						clsBillPrintingBean folioPrintingBean = new clsBillPrintingBean();
						String docDate = billDicArr[0].toString();
						String docNo = billDicArr[1].toString();
						String particulars = billDicArr[2].toString();
						String debitAmount = billDicArr[3].toString();
						String creditAmount = billDicArr[4].toString();
						String balance = billDicArr[5].toString();

						folioPrintingBean.setDteDocDate(objGlobal.funGetDate("dd-MM-yyyy", (docDate))); 
						folioPrintingBean.setStrDocNo(docNo);
						folioPrintingBean.setStrPerticulars(particulars);
						folioPrintingBean.setDblDebitAmt(Double.parseDouble(debitAmount));
						folioPrintingBean.setDblCreditAmt(Double.parseDouble(creditAmount));
						folioPrintingBean.setDblBalanceAmt(Double.parseDouble(balance));

						dataList.add(folioPrintingBean);

					}

				}

			}

			reportParams.put("pFolioNo", folio.substring(1, folio.length()));
			reportParams.put("pRoomNo", roomNo.substring(1, roomNo.length()));
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
				resp.setHeader("Content-Disposition", "inline;filename=Bill.pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
