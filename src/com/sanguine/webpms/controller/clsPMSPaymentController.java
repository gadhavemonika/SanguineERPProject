package com.sanguine.webpms.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsPropertyMaster;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsPropertyMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webpms.bean.clsFolioDtlBean;
import com.sanguine.webpms.bean.clsPMSPaymentBean;
import com.sanguine.webpms.bean.clsPaymentReciptBean;
import com.sanguine.webpms.dao.clsPMSPaymentDao;
import com.sanguine.webpms.dao.clsPMSSettlementMasterDao;
import com.sanguine.webpms.model.clsCheckInDtl;
import com.sanguine.webpms.model.clsCheckInHdModel;
import com.sanguine.webpms.model.clsFolioHdModel;
import com.sanguine.webpms.model.clsGuestMasterHdModel;
import com.sanguine.webpms.model.clsPMSPaymentHdModel;
import com.sanguine.webpms.model.clsPMSPaymentReceiptDtl;
import com.sanguine.webpms.model.clsPMSSettlementMasterHdModel;
import com.sanguine.webpms.model.clsPropertySetupHdModel;
import com.sanguine.webpms.model.clsReservationDtlModel;
import com.sanguine.webpms.model.clsReservationHdModel;
import com.sanguine.webpms.model.clsRoomMasterModel;
import com.sanguine.webpms.service.clsCheckInService;
import com.sanguine.webpms.service.clsFolioService;
import com.sanguine.webpms.service.clsGuestMasterService;
import com.sanguine.webpms.service.clsPMSPaymentService;
import com.sanguine.webpms.service.clsPropertySetupService;
import com.sanguine.webpms.service.clsReservationService;
import com.sanguine.webpms.service.clsRoomMasterService;

@Controller
public class clsPMSPaymentController {
	@Autowired
	private clsPMSPaymentService objPaymentService;

	@Autowired
	private clsPMSPaymentDao objPaymentDao;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsPropertySetupService objPropertySetupService;

	@Autowired
	private clsGuestMasterService objGuestService;

	@Autowired
	private clsPropertyMasterService objPropertyMasterService;

	@Autowired
	clsPMSPaymentDao objDao;

	@Autowired
	private clsReservationService objReservationService;
	@Autowired
	private clsFolioService objFolioService;

	@Autowired
	clsCheckInService objCheckInService;

	@Autowired
	clsPMSSettlementMasterDao objtPMSSettlement;

	@Autowired
	clsRoomMasterService objRoomMaster;

	// Open Payment
	@RequestMapping(value = "/frmPMSPayment", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";

		List<String> listAgainst = new ArrayList<>();
		listAgainst.add("Reservation");
		// listAgainst.add("Check-In");
		listAgainst.add("Folio-No");
		listAgainst.add("Bill");
		model.put("listAgainst", listAgainst);

		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (urlHits.equalsIgnoreCase("1")) {

			return new ModelAndView("frmPMSPayment", "command", new clsPMSPaymentBean());
		} else {
			return new ModelAndView("frmPMSPayment_1", "command", new clsPMSPaymentBean());
		}
	}

	// Load Payemt Data
	@RequestMapping(value = "/loadReceiptData", method = RequestMethod.GET)
	public @ResponseBody clsPMSPaymentBean funLoadReceiptData(@RequestParam("receiptNo") String receiptNo, HttpServletRequest request) {

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsPMSPaymentBean objPaymentRecBean = new clsPMSPaymentBean();
		clsPMSPaymentHdModel objPaymentModel = objPaymentDao.funGetPaymentModel(receiptNo, clientCode);

		objPaymentRecBean.setStrReceiptNo(objPaymentModel.getStrReceiptNo());
		objPaymentRecBean.setStrAgainst(objPaymentModel.getStrAgainst());

		if (objPaymentModel.getStrAgainst().equals("Reservation")) {
			objPaymentRecBean.setStrDocNo(objPaymentModel.getStrReservationNo());
		} else if (objPaymentModel.getStrAgainst().equals("Folio-No")) {
			objPaymentRecBean.setStrDocNo(objPaymentModel.getStrFolioNo());

		} else if (objPaymentModel.getStrAgainst().equals("Check-In")) {
			objPaymentRecBean.setStrDocNo(objPaymentModel.getStrCheckInNo());
		} else {
			objPaymentRecBean.setStrDocNo(objPaymentModel.getStrBillNo());
		}
		objPaymentRecBean.setStrFolioNo(objPaymentModel.getStrFolioNo());
		objPaymentRecBean.setStrRegistrationNo(objPaymentModel.getStrRegistrationNo());
		objPaymentRecBean.setStrFlagOfAdvAmt(objPaymentModel.getStrFlagOfAdvAmt());
		clsPMSPaymentReceiptDtl objPaymentDtlModel = objPaymentModel.getListPaymentRecieptDtlModel().get(0);
		objPaymentRecBean.setStrRemarks(objPaymentDtlModel.getStrRemarks());
		objPaymentRecBean.setStrCardNo(objPaymentDtlModel.getStrCardNo());
		objPaymentRecBean.setStrSettlementCode(objPaymentDtlModel.getStrSettlementCode());
		objPaymentRecBean.setDblPaidAmt(objPaymentModel.getDblPaidAmt());
		objPaymentRecBean.setDblReceiptAmt(objPaymentModel.getDblReceiptAmt());
		objPaymentRecBean.setDblSettlementAmt(objPaymentDtlModel.getDblSettlementAmt());
		objPaymentRecBean.setDteExpiryDate(objGlobal.funGetDate("yyyy/MM/dd", objPaymentDtlModel.getDteExpiryDate()));

		return objPaymentRecBean;
	}

	// Save or Update Payment
	@RequestMapping(value = "/savePMSPayment", method = RequestMethod.GET)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPMSPaymentBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsPMSPaymentHdModel objHdModel = objPaymentService.funPreparePaymentModel(objBean, clientCode, req, userCode);
			objPaymentDao.funAddUpdatePaymentHd(objHdModel);
			String propCode = req.getSession().getAttribute("propertyCode").toString();
//			funSendSMSPayment(objHdModel.getStrReceiptNo(), clientCode, propCode);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Payment Code : ".concat(objHdModel.getStrReceiptNo()));
			req.getSession().setAttribute("GenerateSlip", objHdModel.getStrReceiptNo());
			req.getSession().setAttribute("Against", objHdModel.getStrAgainst());
			return new ModelAndView("redirect:/frmPMSPayment.html");
		} else {
			return new ModelAndView("frmPMSPayment.jsp");
		}
	}

	// Load Table Data On Form
	@RequestMapping(value = "/loadPaymentGuestDetails", method = RequestMethod.GET)
	public @ResponseBody List funLoadPaymentGuestDetails(@RequestParam("docCode") String docCode, @RequestParam("docName") String docName, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		String sql = "";
		List listGuestDataDtl = new ArrayList();
		
		if (docName.equals("Bill")) 
		{
			sql = " select d.strGuestCode,d.strFirstName,d.strMiddleName,d.strLastName, a.strCheckInNo,a.dblGrandTotal,a.strReservationNo " 
		       + "from tblbillhd a,tblcheckinhd b,tblcheckindtl c,tblguestmaster d " 
			   + " where a.strCheckInNo=b.strCheckInNo and b.strCheckInNo=c.strCheckInNo " 
		       + " and c.strGuestCode=d.strGuestCode and a.strBillNo='" + docCode + "' " + " and c.strPayee='Y' ";
			List listBillData = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
			
			if(listBillData.size()>0)
			{
				for(int i=0;i<listBillData.size();i++)
				{
				Object[] obj = (Object[])listBillData.get(i);
				clsPaymentReciptBean objPaymentReciptBean = new clsPaymentReciptBean();
				String sqlRecipt="SELECT ifnull(sum(a.dblReceiptAmt),0) "
						+ " FROM tblreceipthd a left outer join tblbillhd b on a.strBillNo=b.strBillNo "
						+ " and a.strReservationNo and b.strReservationNo,tblreceiptdtl c "
						+ " WHERE a.strReceiptNo=c.strReceiptNo and b.strBillNo='" + docCode + "'  ";
				
				List listRecipt = objGlobalFunctionsService.funGetListModuleWise(sqlRecipt, "sql");
				double reciptAmt=0.0;
				if(listRecipt!=null)
				{
					if(listRecipt.size()>0)
					{
						reciptAmt=Double.parseDouble(listRecipt.get(0).toString());	
						
					}
				}
				
				String sqlAdvanceAmt="SELECT IFNULL(SUM(a.dblReceiptAmt),0)"
						+ " FROM tblreceipthd a,tblreceiptdtl b WHERE a.strReceiptNo=b.strReceiptNo "
						+ " AND a.strReservationNo='" + obj[6].toString() + "' and a.strAgainst='Reservation' ";
				
				List listAdvanceAmt = objGlobalFunctionsService.funGetListModuleWise(sqlAdvanceAmt, "sql");
				double advanceAmt=0.0;
				if(listAdvanceAmt!=null)
				{
					if(listAdvanceAmt.size()>0)
					{
						advanceAmt=Double.parseDouble(listAdvanceAmt.get(0).toString());	
					}
				}
				
				
				objPaymentReciptBean.setStrGuestCode(obj[0].toString());
				objPaymentReciptBean.setStrFirstName(obj[1].toString());
				objPaymentReciptBean.setStrMiddleName(obj[2].toString());
				objPaymentReciptBean.setStrLastName(obj[3].toString());
				objPaymentReciptBean.setDblBalanceAmount(Double.parseDouble(obj[5].toString())-(reciptAmt+advanceAmt));
				listGuestDataDtl.add(objPaymentReciptBean);
				}
			}
		else 
		{
			sql = " select c.strGuestCode,c.strFirstName,c.strMiddleName,c.strLastName " + " from tblcheckindtl a,tblguestmaster c " + " where a.strGuestCode=c.strGuestCode " + " and a.strCheckInNo='" + docCode + "' and a.strPayee='Y'";
			List listData = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
			if(listData.size()>0)
			{
				for(int i=0;i<listData.size();i++)
				{
				Object[] obj = (Object[])listData.get(i);
				clsPaymentReciptBean objPaymentReciptBean = new clsPaymentReciptBean();
				objPaymentReciptBean.setStrGuestCode(obj[0].toString());
				objPaymentReciptBean.setStrFirstName(obj[1].toString());
				objPaymentReciptBean.setStrMiddleName(obj[2].toString());
				objPaymentReciptBean.setStrLastName(obj[3].toString());
				objPaymentReciptBean.setDblBalanceAmount(0);
				listGuestDataDtl.add(objPaymentReciptBean);
				}
			}
		}
	}
//			if(listBillData.size()>0)
//			{
//				for(int i=0;i<listBillData.size();i++)
//				{
//				Object[] obj = (Object[])listBillData.get(i);
//				clsPaymentReciptBean objPaymentReciptBean = new clsPaymentReciptBean();
//	   
//				
//				objPaymentReciptBean.setStrGuestCode(obj[0].toString());
//				objPaymentReciptBean.setStrFirstName(obj[1].toString());
//				objPaymentReciptBean.setStrMiddleName(obj[2].toString());
//				objPaymentReciptBean.setStrLastName(obj[3].toString());
//				sql = "select dblDebitAmt-ifnull(dblDiscAmt,0)"
//					+ " from " 
//					+ " (select a.strBillNo, SUM(a.dblDebitAmt)dblDebitAmt "
//					+ " from tblbilldtl a "
//					+ " where a.strBillNo='" + docCode + "') a "
//					+ " left outer join "
//					+ "(select a.strBillNo,sum(ifnull(a.dblDiscAmt,0))dblDiscAmt "
//					+ " from tblbilldiscount a "
//					+ " where a.strBillNo='" + docCode + "') b on a.strBillNo=b.strBillNo ";
//				List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
//				if(list.size()>0)
//				{
//					Object ob = list.get(0);
////			        String sqlRecipt="select sum(a.dblReceiptAmt) from tblreceipthd a where a.strCheckInNo='"+obj[4].toString()+"'  group by a.strCheckInNo " ;
//					String sqlRecipt="select a.dblReceiptAmt from tblreceipthd a,tblreceiptdtl b,tblbillhd c " 
//							+ " where a.strReceiptNo=b.strReceiptNo " 
//							+ " and a.strReservationNo=c.strReservationNo "
//							+ " and c.strBillNo='" + docCode + "' ";
//					
//					List listRecipt = objGlobalFunctionsService.funGetListModuleWise(sqlRecipt, "sql");
//					double reciptAmt=0.0;
//					if(listRecipt.size()>0)
//					{
//						reciptAmt=Double.parseDouble(listRecipt.get(0).toString());	
//						
//					}
//					
//					objPaymentReciptBean.setDblBalanceAmount(Double.parseDouble(obj[5].toString())-reciptAmt);
//				}
//				
//				
//				
//				
//				
//				listGuestDataDtl.add(objPaymentReciptBean);
//				
//				}
//			}
		

		else if (docName.equals("Reservation")) 
		{
			/*sql = " select a.strGuestCode,b.strFirstName,b.strMiddleName,b.strLastName,c.dblRoomRate " 
					+ " from tblreservationdtl a,tblguestmaster b, tblreservationroomratedtl c " 
					+ " where a.strGuestCode=b.strGuestCode and a.strReservationNo=c.strReservationNo and a.strClientCode=b.strClientCode "  
					+ " and b.strClientCode=c.strClientCode and a.strReservationNo='" + docCode + "' ";
			*/
			sql="select b.strGuestCode,c.strFirstName,c.strMiddleName,c.strLastName "
				+ " from tblreservationhd a,tblreservationdtl b,tblguestmaster c "
				+ " where a.strReservationNo=b.strReservationNo and b.strGuestCode=c.strGuestCode "
				+ " and a.strReservationNo='"+docCode+"'";

			
			List listReservData = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
			
			if(listReservData.size()>0)
			{
				for(int i=0;i<listReservData.size();i++)
				{
					Object[] obj = (Object[])listReservData.get(i);
					clsPaymentReciptBean objPaymentReciptBean = new clsPaymentReciptBean();
					
					String sqlRecipt="SELECT IFNULL(SUM(a.dblReceiptAmt),0),IFNULL(SUM(d.dblGrandTotal),0)"
							+ " FROM tblreceipthd a left outer join tblbillhd d on a.strBillNo=d.strBillNo and a.strFolioNo=d.strFolioNo,"
							+ " tblreceiptdtl b, tblreservationhd c"
							+ " WHERE a.strReceiptNo=b.strReceiptNo AND a.strReservationNo=c.strReservationNo "
							+ " AND c.strReservationNo='" + docCode + "' ";	
					
				
				    boolean flgIsFullPaymentFound=false;
					List listRecipt = objGlobalFunctionsService.funGetListModuleWise(sqlRecipt, "sql");
					double reciptAmt=0.0;
					for(int cnt=0;cnt<listRecipt.size();cnt++)
					{
						Object[] objReceipt = (Object[])listRecipt.get(cnt);
						if(Double.parseDouble(objReceipt[0].toString())==Double.parseDouble(objReceipt[1].toString()))
						{
							flgIsFullPaymentFound=true;
							break;
						}
						else
						{
							reciptAmt=Double.parseDouble(objReceipt[0].toString());
						}
					}
					objPaymentReciptBean.setStrGuestCode(obj[0].toString());
					objPaymentReciptBean.setStrFirstName(obj[1].toString());
					objPaymentReciptBean.setStrMiddleName(obj[2].toString());
					objPaymentReciptBean.setStrLastName(obj[3].toString());
					
					if(!flgIsFullPaymentFound)
					{
						String sqlPaymentAmt=" select withPkgAmt,withoutPkgAmt from "
								+ " (select ifnull(sum(a.dblIncomeHeadAmt),0) withPkgAmt from tblroompackagedtl a where a.strReservationNo='" + docCode + "' ) a,"
								+ " (select ifnull(sum(a.dblRoomRate),0) withoutPkgAmt from tblreservationroomratedtl a where a.strReservationNo='" + docCode + "' ) b ";
						List listPaymentAmt = objGlobalFunctionsService.funGetListModuleWise(sqlPaymentAmt, "sql");
						if(listPaymentAmt.size()>0)
						{
							for(int cnt=0;cnt<listPaymentAmt.size();cnt++)
							{
								Object[] objPayment = (Object[])listPaymentAmt.get(cnt);
		
								if(Double.parseDouble(objPayment[0].toString())>0)
								{
									objPaymentReciptBean.setDblBalanceAmount((Double.parseDouble(objPayment[0].toString()))-reciptAmt);
								}
								else
								{
									objPaymentReciptBean.setDblBalanceAmount((Double.parseDouble(objPayment[1].toString()))-reciptAmt);		
								}
								
							}
						}	
					}
							
				
				
				//objPaymentReciptBean.setDblBalanceAmount(Double.parseDouble(obj[4].toString())-reciptAmt);	
				listGuestDataDtl.add(objPaymentReciptBean);
				}
			}
			else 
			{
				sql = " select c.strGuestCode,c.strFirstName,c.strMiddleName,c.strLastName " + " from tblcheckindtl a,tblguestmaster c " + " where a.strGuestCode=c.strGuestCode " + " and a.strCheckInNo='" + docCode + "' and a.strPayee='Y'";
				List listData = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
				if(listData.size()>0)
				{
					for(int i=0;i<listData.size();i++)
					{
					Object[] obj = (Object[])listData.get(i);
					clsPaymentReciptBean objPaymentReciptBean = new clsPaymentReciptBean();
					objPaymentReciptBean.setStrGuestCode(obj[0].toString());
					objPaymentReciptBean.setStrFirstName(obj[1].toString());
					objPaymentReciptBean.setStrMiddleName(obj[2].toString());
					objPaymentReciptBean.setStrLastName(obj[3].toString());
					objPaymentReciptBean.setDblBalanceAmount(0);
					listGuestDataDtl.add(objPaymentReciptBean);
					}
				}
			}
			
//			if(listReservData.size()>0)
//			{
//				for(int i=0;i<listReservData.size();i++)
//				{
//				Object[] obj = (Object[])listReservData.get(i);
//				clsPaymentReciptBean objPaymentReciptBean = new clsPaymentReciptBean();
//				objPaymentReciptBean.setStrGuestCode(obj[0].toString());
//				objPaymentReciptBean.setStrFirstName(obj[1].toString());
//				objPaymentReciptBean.setStrMiddleName(obj[2].toString());
//				objPaymentReciptBean.setStrLastName(obj[3].toString());
//				objPaymentReciptBean.setDblBalanceAmount(0);
//				listGuestDataDtl.add(objPaymentReciptBean);
//				}
//			}
		} 
		else if (docName.equals("Folio-No")) 
		{
			clsFolioDtlBean objFolioDtlBean = new clsFolioDtlBean();
			//sql = " select c.strGuestCode,c.strFirstName,c.strMiddleName,c.strLastName " + " from tblcheckindtl a,tblguestmaster c ,tblfoliohd d  " + " where a.strGuestCode=c.strGuestCode and a.strGuestCode=d.strGuestCode " + " and a.strCheckInNo=d.strCheckInNo " + " and a.strRegistrationNo =d.strRegistrationNo  " + " and d.strFolioNo='" + docCode + "' and a.strPayee='Y' ";
//			sql ="select a.strGuestCode,a.strFirstName,a.strMiddleName,a.strLastName,sum(amount - ifnull(discount,0)),a.strCheckInNo "
//				 + " from"
//				 + "(SELECT c.strGuestCode,c.strFirstName,c.strMiddleName,c.strLastName,sum(e.dblDebitAmt) as amount,a.strCheckInNo"
//				 + " FROM tblcheckindtl a,tblguestmaster c,tblfoliohd d,tblfoliodtl e"
//				 + " WHERE a.strGuestCode=c.strGuestCode AND a.strGuestCode=d.strGuestCode "
//				 + " AND a.strCheckInNo=d.strCheckInNo AND a.strRegistrationNo =d.strRegistrationNo "
//				 + " AND d.strFolioNo='" + docCode + "' AND a.strPayee='Y' and e.strRevenueType!='discount'"
//				 + "  and d.strFolioNo=e.strFolioNo) as a,"
//				 + " (SELECT c.strGuestCode,c.strFirstName,c.strMiddleName,c.strLastName,sum(e.dblDebitAmt) as discount"
//				 + " FROM tblcheckindtl a,tblguestmaster c,tblfoliohd d,tblfoliodtl e"
//				 + " WHERE a.strGuestCode=c.strGuestCode AND a.strGuestCode=d.strGuestCode "
//				 + " AND a.strCheckInNo=d.strCheckInNo AND a.strRegistrationNo =d.strRegistrationNo "
//				 + " AND d.strFolioNo='" + docCode + "' AND a.strPayee='Y'"
//				 + " and e.strRevenueType='Discount' and d.strFolioNo=e.strFolioNo) as b;";
			
			sql="SELECT a.strGuestCode,a.strFirstName,a.strMiddleName,a.strLastName,IFNULL(SUM(amount - IFNULL(discount,0)),0),a.strReservationNo ,a.strCheckInNo"
			 +" FROM(SELECT c.strGuestCode,c.strFirstName,c.strMiddleName,c.strLastName, SUM(e.dblDebitAmt) AS amount, "
			 +" a.strCheckInNo,f.strReservationNo FROM tblcheckindtl a,tblguestmaster c,tblfoliohd d,tblfoliodtl e,tblcheckinhd f "
			 +" WHERE a.strGuestCode=c.strGuestCode AND a.strGuestCode=d.strGuestCode AND a.strCheckInNo=d.strCheckInNo  "
			 +" AND a.strRegistrationNo =d.strRegistrationNo AND d.strFolioNo='" + docCode + "' "
			 +" AND a.strPayee='Y' AND e.strRevenueType!='discount' AND d.strFolioNo=e.strFolioNo and a.strCheckInNo=f.strCheckInNo) AS a, ( "
			 +" SELECT c.strGuestCode,c.strFirstName,c.strMiddleName,c.strLastName, SUM(e.dblDebitAmt) AS discount "
			 +" FROM tblcheckindtl a,tblguestmaster c,tblfoliohd d,tblfoliodtl e "
			 +" WHERE a.strGuestCode=c.strGuestCode AND a.strGuestCode=d.strGuestCode " 
			 +" AND a.strCheckInNo=d.strCheckInNo AND a.strRegistrationNo =d.strRegistrationNo " 
			 +" AND d.strFolioNo='" + docCode + "' AND a.strPayee='Y' AND e.strRevenueType='Discount'  "
			 +" AND d.strFolioNo=e.strFolioNo) AS b;" ;
			
			List listFoliaData = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
			
			if(listFoliaData.size()>0)
			{
				for(int i=0;i<listFoliaData.size();i++)
				{
				Object[] obj = (Object[])listFoliaData.get(i);
				clsPaymentReciptBean objPaymentReciptBean = new clsPaymentReciptBean();
				
				String sqlRecipt="select sum(a.dblReceiptAmt) from tblreceipthd a where a.strReservationNo='"+obj[5].toString()+"'  group by a.strCheckInNo " ;
				
				List listRecipt = objGlobalFunctionsService.funGetListModuleWise(sqlRecipt, "sql");
				double reciptAmt=0.0;
				if(listRecipt.size()>0)
				{
					reciptAmt=Double.parseDouble(listRecipt.get(0).toString());	
					
				}
				objPaymentReciptBean.setStrGuestCode(obj[0].toString());
				objPaymentReciptBean.setStrFirstName(obj[1].toString());
				objPaymentReciptBean.setStrMiddleName(obj[2].toString());
				objPaymentReciptBean.setStrLastName(obj[3].toString());
				objPaymentReciptBean.setDblBalanceAmount(Double.parseDouble(obj[4].toString())-reciptAmt);
				listGuestDataDtl.add(objPaymentReciptBean);
				}
			}
		} 
		else 
		{
			sql = " select c.strGuestCode,c.strFirstName,c.strMiddleName,c.strLastName " + " from tblcheckindtl a,tblguestmaster c " + " where a.strGuestCode=c.strGuestCode " + " and a.strCheckInNo='" + docCode + "' and a.strPayee='Y'";
			List listData = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
			if(listData.size()>0)
			{
				for(int i=0;i<listData.size();i++)
				{
				Object[] obj = (Object[])listData.get(i);
				clsPaymentReciptBean objPaymentReciptBean = new clsPaymentReciptBean();
				objPaymentReciptBean.setStrGuestCode(obj[0].toString());
				objPaymentReciptBean.setStrFirstName(obj[1].toString());
				objPaymentReciptBean.setStrMiddleName(obj[2].toString());
				objPaymentReciptBean.setStrLastName(obj[3].toString());
				objPaymentReciptBean.setDblBalanceAmount(0);
				listGuestDataDtl.add(objPaymentReciptBean);
				}
			}
		}
		
		return listGuestDataDtl;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rptReservationPaymentRecipt", method = RequestMethod.GET)
	public void funGeneratePaymentRecipt(@RequestParam("reciptNo") String reciptNo, @RequestParam("checkAgainst") String checkAgainst, HttpServletRequest req, HttpServletResponse resp) {
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}

			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			String userName = "";
			String sqlUserName = "select strUserName from "+webStockDB+".tbluserhd where strUserCode='" + userCode + "' ";

			List listOfUser = objGlobalFunctionsService.funGetDataList(sqlUserName, "sql");
			if (listOfUser.size() > 0) {
				// Object[] userData = (Object[]) listOfUser.get(0);
				userName = listOfUser.get(0).toString();
			}

			HashMap reportParams = new HashMap();
			reportParams.put("pCompanyName", companyName);
			reportParams.put("pAddress1", objSetup.getStrAdd1() + "," + objSetup.getStrAdd2() + "," + objSetup.getStrCity());
			reportParams.put("pAddress2", objSetup.getStrState() + "," + objSetup.getStrCountry() + "," + objSetup.getStrPin());
			reportParams.put("pContactDetails", "");
			reportParams.put("strImagePath", imagePath);
			reportParams.put("userName", userName);
			ArrayList datalist = new ArrayList();
			String reportName = "";
			if (checkAgainst.equalsIgnoreCase("Reservation")) {
				reportName = servletContext.getRealPath("/WEB-INF/reports/webpms/rptReservationPaymentRecipt.jrxml");
				/*String sqlPayment = "select a.strReceiptNo,ifnull(d.intNoOfAdults,''),ifnull(d.intNoOfChild,'')  ,ifnull(a.strReservationNo,'')" + ",ifnull(c.strRoomType,''),DATE_FORMAT(d.dteArrivalDate,'%d-%m-%Y'),DATE_FORMAT(d.dteDepartureDate,'%d-%m-%Y'),f.strFirstName" + ",f.strMiddleName,f.strLastName,ifnull(e.strSettlementDesc,''),a.dblPaidAmt,b.strRemarks"
						+ ",DATE_FORMAT(a.dteReceiptDate,'%d-%m-%Y') " + "from tblreceipthd a left outer join  tblreceiptdtl b on a.strReceiptNo=b.strReceiptNo " + "left outer join  tblreservationdtl c on a.strReservationNo=c.strReservationNo " + "left outer join  tblreservationhd d on a.strReservationNo=d.strReservationNo "
						+ "left outer join  tblsettlementmaster e on b.strSettlementCode=e.strSettlementCode " + "left outer join  tblguestmaster f    on c.strGuestCode=f.strGuestCode " + "where a.strReceiptNo='" + reciptNo + "' and a.strClientCode='" + clientCode + "'  ";
				*/
				String sqlPayment = "select a.strReceiptNo,ifnull(d.intNoOfAdults,''),ifnull(d.intNoOfChild,'')  ,ifnull(a.strReservationNo,'')" + ",ifnull(c.strRoomType,''),DATE_FORMAT(d.dteArrivalDate,'%d-%m-%Y'),DATE_FORMAT(d.dteDepartureDate,'%d-%m-%Y'),f.strFirstName" + ",f.strMiddleName,f.strLastName,ifnull(e.strSettlementDesc,''),a.dblPaidAmt,b.strRemarks"
						+ ",DATE_FORMAT(a.dteReceiptDate,'%d-%m-%Y'),g.strRoomTypeDesc " + "from tblreceipthd a left outer join  tblreceiptdtl b on a.strReceiptNo=b.strReceiptNo " + "left outer join  tblreservationdtl c on a.strReservationNo=c.strReservationNo " + "left outer join  tblreservationhd d on a.strReservationNo=d.strReservationNo "
						+ "left outer join  tblsettlementmaster e on b.strSettlementCode=e.strSettlementCode " + "left outer join  tblguestmaster f    on c.strGuestCode=f.strGuestCode,tblroomtypemaster g " + "where c.strRoomType=g.strRoomTypeCode "
						+ "and a.strReceiptNo='" + reciptNo + "' and a.strClientCode='" + clientCode + "'  ";
				List listOfPayment = objGlobalFunctionsService.funGetDataList(sqlPayment, "sql");

				for (int i = 0; i < listOfPayment.size(); i++) {
					Object PaymentData[] = (Object[]) listOfPayment.get(i);

					String strReceiptNo = PaymentData[0].toString();
					String intNoOfAdults = PaymentData[1].toString();
					String intNoOfChild = PaymentData[2].toString();
					String strReservationNo = PaymentData[3].toString();
					String strRoomType = PaymentData[14].toString();
					String dteArrivalDate = PaymentData[5].toString();
					String dteDepartureDate = PaymentData[6].toString();
					String strFirstName = PaymentData[7].toString();
					String strMiddleName = PaymentData[8].toString();
					String strLastName = PaymentData[9].toString();
					String strSettlementDesc = PaymentData[10].toString();
					String dblPaidAmt = PaymentData[11].toString();
					String strRemarks = PaymentData
							[12].toString();
					String dteReciptDate = PaymentData[13].toString();
					String dteModifiedDate = PaymentData[13].toString();

					clsPaymentReciptBean objPaymentReciptBean = new clsPaymentReciptBean();
					objPaymentReciptBean.setStrReceiptNo(strReceiptNo);
					objPaymentReciptBean.setIntNoOfAdults(intNoOfAdults);
					objPaymentReciptBean.setIntNoOfChild(intNoOfChild);
					objPaymentReciptBean.setStrReservationNo(strReservationNo);
					objPaymentReciptBean.setStrRoomType(strRoomType);
					objPaymentReciptBean.setDteArrivalDate(dteArrivalDate);
					objPaymentReciptBean.setDteDepartureDate(dteDepartureDate);
					objPaymentReciptBean.setStrFirstName(strFirstName);
					objPaymentReciptBean.setStrMiddleName(strMiddleName);
					objPaymentReciptBean.setStrLastName(strLastName);
					objPaymentReciptBean.setStrSettlementDesc(strSettlementDesc);
					objPaymentReciptBean.setDblPaidAmt(dblPaidAmt);
					objPaymentReciptBean.setStrRemarks(strRemarks);
					objPaymentReciptBean.setDteReciptDate(dteReciptDate);
					objPaymentReciptBean.setDteModifiedDate(dteModifiedDate);
					datalist.add(objPaymentReciptBean);
				}
			} else if (checkAgainst.equalsIgnoreCase("Bill")) {
				reportName = servletContext.getRealPath("/WEB-INF/reports/webpms/rptBillNoPaymentRecipt.jrxml");
				/*
				 * String sqlPayment=
				 * "select a.strReceiptNo,ifnull(e.intNoOfAdults,'NA'),ifnull(e.intNoOfChild,'NA'),a.strReservationNo ,c.strBillNo,ifnull(e.strRoomType,'NA') "
				 * +
				 * ",DATE_FORMAT(g.dteArrivalDate,'%d-%m-%Y'),DATE_FORMAT(g.dteDepartureDate,'%d-%m-%Y'),ifnull(f.strFirstName,'NA'),ifnull(f.strMiddleName,'NA')"
				 * +
				 * ",ifnull(f.strLastName,'NA'),d.strSettlementDesc,a.dblPaidAmt,b.strRemarks,DATE_FORMAT(a.dteReceiptDate,'%d-%m-%Y') "
				 * +
				 * "from tblreceipthd a left outer join  tblreceiptdtl b on a.strReceiptNo=b.strReceiptNo "
				 * +
				 * "left outer join tblbillhd c on a.strRegistrationNo=c.strRegistrationNo  "
				 * +
				 * "left outer join  tblsettlementmaster d on b.strSettlementCode=d.strSettlementCode "
				 * +
				 * "left outer join tblreservationdtl e on a.strReservationNo=e.strReservationNo "
				 * +
				 * "left outer join tblreservationhd g on a.strReservationNo=g.strReservationNo "
				 * +
				 * "left outer join tblguestmaster f on e.strGuestCode=f.strGuestCode "
				 * +"where a.strReceiptNo='"+reciptNo+"' and a.strClientCode='"+
				 * clientCode+"'  ";
				 */

				String sqlPayment = " select a.strReceiptNo ,ifnull(c.intNoOfAdults,''),ifnull(c.intNoOfChild,''),a.strReservationNo,e.strBillNo, " + " ifnull(j.strRoomTypeDesc,'') ,DATE_FORMAT(c.dteArrivalDate,'%d-%m-%Y'), DATE_FORMAT(c.dteDepartureDate,'%d-%m-%Y'), " + " ifnull(h.strFirstName,''), ifnull(h.strMiddleName,''),ifnull(h.strLastName,''), "
						+ "f.strSettlementDesc,a.dblPaidAmt,b.strRemarks, DATE_FORMAT(a.dteReceiptDate,'%d-%m-%Y') " + " from tblreceipthd a ,tblreceiptdtl b,tblcheckinhd c ,tblcheckindtl d ,tblbillhd e , " + " tblsettlementmaster f ,tblguestmaster h, tblroom i,tblroomtypemaster j " + " where a.strReceiptNo=b.strReceiptNo and a.strCheckInNo =c.strCheckInNo  "
						+ "and c.strCheckInNo=d.strCheckInNo   and a.strReservationNo =c.strReservationNo " + " and a.strCheckInNo = e.strCheckInNo    " + "and a.strReservationNo =e.strReservationNo " + " and d.strCheckInNo = e.strCheckInNo    " + "and c.strCheckInNo = e.strCheckInNo  and d.strRoomNo = e.strRoomNo " + "  and d.strRoomNo = i.strRoomCode and i.strRoomTypeCode=j.strRoomTypeCode "
						+ " and b.strSettlementCode=f.strSettlementCode and d.strGuestCode=h.strGuestCode " + " and a.strReceiptNo='" + reciptNo + "' and a.strClientCode='" + clientCode + "' ";

				List listOfPayment = objGlobalFunctionsService.funGetDataList(sqlPayment, "sql");

				for (int i = 0; i < listOfPayment.size(); i++) {
					Object PaymentData[] = (Object[]) listOfPayment.get(i);

					String strReceiptNo = PaymentData[0].toString();
					String intNoOfAdults = PaymentData[1].toString();
					String intNoOfChild = PaymentData[2].toString();
					String strReservationNo = PaymentData[3].toString();
					String strBillNo = PaymentData[4].toString();
					String strRoomType = PaymentData[5].toString();
					String dteArrivalDate = PaymentData[6].toString();
					String dteDepartureDate = PaymentData[7].toString();
					String strFirstName = PaymentData[8].toString();
					String strMiddleName = PaymentData[9].toString();
					String strLastName = PaymentData[10].toString();
					String strSettlementDesc = PaymentData[11].toString();
					String dblPaidAmt = PaymentData[12].toString();
					String strRemarks = PaymentData[13].toString();
					String dteReciptDate = PaymentData[14].toString();
					String dteModifiedDate = PaymentData[14].toString();

					clsPaymentReciptBean objPaymentReciptBean = new clsPaymentReciptBean();
					objPaymentReciptBean.setStrReceiptNo(strReceiptNo);
					objPaymentReciptBean.setIntNoOfAdults(intNoOfAdults);
					objPaymentReciptBean.setIntNoOfChild(intNoOfChild);
					objPaymentReciptBean.setStrReservationNo(strReservationNo);
					objPaymentReciptBean.setStrRoomType(strRoomType);
					objPaymentReciptBean.setDteArrivalDate(dteArrivalDate);
					objPaymentReciptBean.setDteDepartureDate(dteDepartureDate);
					objPaymentReciptBean.setStrFirstName(strFirstName);
					objPaymentReciptBean.setStrMiddleName(strMiddleName);
					objPaymentReciptBean.setStrLastName(strLastName);
					objPaymentReciptBean.setStrSettlementDesc(strSettlementDesc);
					objPaymentReciptBean.setDblPaidAmt(dblPaidAmt);
					objPaymentReciptBean.setStrRemarks(strRemarks);
					objPaymentReciptBean.setDteReciptDate(dteReciptDate);
					objPaymentReciptBean.setDteModifiedDate(dteModifiedDate);
					objPaymentReciptBean.setStrBillNo(strBillNo);
					datalist.add(objPaymentReciptBean);
				}
			} else {
				reportName = servletContext.getRealPath("/WEB-INF/reports/webpms/rptCheckInPaymentRecipt.jrxml");

				String sqlPayment = "select a.strReceiptNo,ifnull(c.intNoOfAdults,''),ifnull(c.intNoOfChild,''),a.strReservationNo ,c.strCheckInNo,ifnull(e.strRoomType,'') " + ",DATE_FORMAT(c.dteArrivalDate,'%d-%m-%Y'),DATE_FORMAT(c.dteDepartureDate,'%d-%m-%Y'),ifnull(f.strFirstName,''),ifnull(f.strMiddleName,'')"
						+ ",ifnull(f.strLastName,''),ifnull(d.strSettlementDesc,''),a.dblPaidAmt,b.strRemarks,DATE_FORMAT(a.dteReceiptDate,'%d-%m-%Y') " + "from tblreceipthd a left outer join  tblreceiptdtl b on a.strReceiptNo=b.strReceiptNo " + "left outer join tblcheckinhd c on a.strRegistrationNo=c.strRegistrationNo  "
						+ "left outer join tblreservationdtl e on a.strReservationNo=e.strReservationNo " + "left outer join tblguestmaster f on e.strGuestCode=f.strGuestCode " + "left outer join  tblsettlementmaster d on b.strSettlementCode=d.strSettlementCode " + "where a.strReceiptNo='" + reciptNo + "' and a.strClientCode='" + clientCode + "'  ";
				List listOfPayment = objGlobalFunctionsService.funGetDataList(sqlPayment, "sql");

				for (int i = 0; i < listOfPayment.size(); i++) {
					Object PaymentData[] = (Object[]) listOfPayment.get(i);

					String strReceiptNo = PaymentData[0].toString();
					String intNoOfAdults = PaymentData[1].toString();
					String intNoOfChild = PaymentData[2].toString();
					String strReservationNo = PaymentData[3].toString();
					String strCheckInNo = PaymentData[4].toString();
					String strRoomType = PaymentData[5].toString();
					String dteArrivalDate = PaymentData[6].toString();
					String dteDepartureDate = PaymentData[7].toString();
					String strFirstName = PaymentData[8].toString();
					String strMiddleName = PaymentData[9].toString();
					String strLastName = PaymentData[10].toString();
					String strSettlementDesc = PaymentData[11].toString();
					String dblPaidAmt = PaymentData[12].toString();
					String strRemarks = PaymentData[13].toString();
					String dteReciptDate = PaymentData[14].toString();
					String dteModifiedDate = PaymentData[14].toString();

					clsPaymentReciptBean objPaymentReciptBean = new clsPaymentReciptBean();
					objPaymentReciptBean.setStrReceiptNo(strReceiptNo);
					objPaymentReciptBean.setIntNoOfAdults(intNoOfAdults);
					objPaymentReciptBean.setIntNoOfChild(intNoOfChild);
					objPaymentReciptBean.setStrReservationNo(strReservationNo);
					objPaymentReciptBean.setStrRoomType(strRoomType);
					objPaymentReciptBean.setDteArrivalDate(dteArrivalDate);
					objPaymentReciptBean.setDteDepartureDate(dteDepartureDate);
					objPaymentReciptBean.setStrFirstName(strFirstName);
					objPaymentReciptBean.setStrMiddleName(strMiddleName);
					objPaymentReciptBean.setStrLastName(strLastName);
					objPaymentReciptBean.setStrSettlementDesc(strSettlementDesc);
					objPaymentReciptBean.setDblPaidAmt(dblPaidAmt);
					objPaymentReciptBean.setStrRemarks(strRemarks);
					objPaymentReciptBean.setDteReciptDate(dteReciptDate);
					objPaymentReciptBean.setDteModifiedDate(dteModifiedDate);
					objPaymentReciptBean.setStrCheckInNo(strCheckInNo);
					datalist.add(objPaymentReciptBean);
				}
			}

			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(datalist);
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
				resp.setHeader("Content-Disposition", "inline;filename=PaymentRecipt.pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Open Payment
	@RequestMapping(value = "/frmPMSPaymentAdvanceAmount", method = RequestMethod.GET)
	public ModelAndView funOpenMSPaymentAdvanceAmount(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";

		List<String> listAgainst = new ArrayList<>();
		listAgainst.add("Reservation");
		// listAgainst.add("Check-In");
		listAgainst.add("Folio-No");
		listAgainst.add("Bill");
		model.put("listAgainst", listAgainst);
		String AdvAmount = request.getParameter("AdvAmount").toString();
		// String checkAgainst=request.getParameter("checkAgainst").toString();
		double dblBalanceAmt=0.0;
		if(AdvAmount.charAt(2)=='R')
		{
			String sqlReservation="select ifnull(sum(a.dblIncomeHeadAmt),0)-ifnull(b.dblReceiptAmt,0) from tblroompackagedtl a left outer join  tblreceipthd b   on  a.strReservationNo=b.strReservationNo "
					 +" where a.strReservationNo='"+AdvAmount+"'  group by a.strReservationNo " ;
			List listResevation = objGlobalFunctionsService.funGetDataList(sqlReservation, "sql");
			if (listResevation.size()>0) 
			{
				dblBalanceAmt=Double.parseDouble(listResevation.get(0).toString());
			}
			else
			{
				sqlReservation="select ifnull(sum(a.dblRoomRate),0)-ifnull(b.dblReceiptAmt,0) from tblreservationroomratedtl a left outer join  tblreceipthd b   on  a.strReservationNo=b.strReservationNo "
						 +" where a.strReservationNo='"+AdvAmount+"'  group by a.strReservationNo " ;
				listResevation = objGlobalFunctionsService.funGetDataList(sqlReservation, "sql");

				if (listResevation.size()>0) 
				{
					dblBalanceAmt=Double.parseDouble(listResevation.get(0).toString());
				}
			}
			
		}
		if(AdvAmount.charAt(2)=='C'){
			
		}
		
		request.setAttribute("code", AdvAmount);
		request.setAttribute("dblBalanceAmt", dblBalanceAmt);
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (urlHits.equalsIgnoreCase("1")) {

			return new ModelAndView("frmPMSPayment", "command", new clsPMSPaymentBean());
		} else {
			return new ModelAndView("frmPMSPayment_1", "command", new clsPMSPaymentBean());
		}
	}

	private void funSendSMSPayment(String paymentNo, String clientCode, String propCode) {

		String strMobileNo = "";
		clsPropertySetupHdModel objSetup = objPropertySetupService.funGetPropertySetup(propCode, clientCode);
		String settleDesc = "";
		String strGuesCode = "";
		clsPMSPaymentHdModel modelData = objDao.funGetPaymentModel(paymentNo, clientCode);

		List listPayemntDtl = modelData.getListPaymentRecieptDtlModel();
		clsPMSPaymentReceiptDtl objPaymentReceiptDtl = (clsPMSPaymentReceiptDtl) listPayemntDtl.get(0);

		List listSettle = objtPMSSettlement.funGetPMSSettlementMaster(objPaymentReceiptDtl.getStrSettlementCode(), clientCode);
		if (listSettle.size() > 0) {
			clsPMSSettlementMasterHdModel objSettlemode = (clsPMSSettlementMasterHdModel) listSettle.get(0);
			settleDesc = objSettlemode.getStrSettlementDesc();
		}
		if (modelData.getStrAgainst().equalsIgnoreCase("Reservation")) {
			clsReservationHdModel objModel = objReservationService.funGetReservationList(modelData.getStrReservationNo(), clientCode, propCode);
			List<clsReservationDtlModel> listReservationmodel = objModel.getListReservationDtlModel();

			if (listReservationmodel.size() > 0) {
				for (int i = 0; i < listReservationmodel.size(); i++) {
					clsReservationDtlModel objDtl = listReservationmodel.get(i);
					if (objDtl.getStrPayee().equals("Y")) {
						strGuesCode = objDtl.getStrGuestCode();

						List list = objGuestService.funGetGuestMaster(objDtl.getStrGuestCode(), clientCode);
						clsGuestMasterHdModel objGuestModel = null;

						if (list.size() > 0) {
							objGuestModel = (clsGuestMasterHdModel) list.get(0);

						}

						String smsAPIUrl = objSetup.getStrSMSAPI();

						String smsContent = objSetup.getStrAdvAmtSMSContent();

						if (!smsAPIUrl.equals("")) {
							if (smsContent.contains("%%CompanyName")) {
								List<clsCompanyMasterModel> listCompanyModel = objPropertySetupService.funGetListCompanyMasterModel(clientCode);
								smsContent = smsContent.replace("%%CompanyName", listCompanyModel.get(0).getStrCompanyName());
							}
							if (smsContent.contains("%%PropertyName")) {
								clsPropertyMaster objProperty = objPropertyMasterService.funGetProperty(propCode, clientCode);
								smsContent = smsContent.replace("%%PropertyName", objProperty.getPropertyName());
							}

							if (smsContent.contains("%%PaymentNo")) {
								smsContent = smsContent.replace("%%RNo", paymentNo);
							}

							if (smsContent.contains("%%SettlementDesc")) {
								smsContent = smsContent.replace("%%SettlementDesc", settleDesc);
							}

							if (smsContent.contains("%%GuestName")) {
								smsContent = smsContent.replace("%%GuestName", objGuestModel.getStrFirstName() + " " + objGuestModel.getStrMiddleName() + " " + objGuestModel.getStrLastName());
							}
							if (smsContent.contains("%%Amount")) {
								smsContent = smsContent.replace("%%Amount", String.valueOf(modelData.getDblPaidAmt()));
							}

							if (smsContent.contains("%%RoomNo")) {
								clsRoomMasterModel roomNo = objRoomMaster.funGetRoomMaster(objModel.getStrRoomNo(), clientCode);
								smsContent = smsContent.replace("%%RoomNo", roomNo.getStrRoomDesc());
							}

							if (smsAPIUrl.contains("ReceiverNo")) {

								smsAPIUrl = smsAPIUrl.replace("ReceiverNo", String.valueOf(objGuestModel.getLngMobileNo()));
								strMobileNo = String.valueOf(objGuestModel.getLngMobileNo());
							}
							if (smsAPIUrl.contains("MsgContent")) {
								smsAPIUrl = smsAPIUrl.replace("MsgContent", smsContent);
								smsAPIUrl = smsAPIUrl.replace(" ", "%20");
							}

							URL url;
							HttpURLConnection uc = null;
							StringBuilder output = new StringBuilder();

							try {
								url = new URL(smsAPIUrl);
								uc = (HttpURLConnection) url.openConnection();
								if (String.valueOf(objGuestModel.getLngMobileNo()).length() >= 10) {
									BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), Charset.forName("UTF-8")));
									String inputLine;
									while ((inputLine = in.readLine()) != null) {
										output.append(inputLine);
									}
									in.close();
								}

							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								uc.disconnect();
							}
						}
					}
				}
			}
		}

		if (modelData.getStrAgainst().equalsIgnoreCase("Folio-No")) {
			clsFolioHdModel objFolioModel = objFolioService.funGetFolioList(modelData.getStrFolioNo(), clientCode, propCode);

			clsCheckInHdModel objHdModel = objCheckInService.funGetCheckInData(objFolioModel.getStrCheckInNo().toString(), clientCode);
			clsCheckInDtl objDtlModel = null;

			List listcheckIn = objHdModel.getListCheckInDtl();
			String smsAPIUrl = objSetup.getStrSMSAPI();

			String smsContent = objSetup.getStrAdvAmtSMSContent();

			if (listcheckIn.size() > 0) {
				for (int i = 0; i < listcheckIn.size(); i++) {
					clsCheckInDtl objDtl = (clsCheckInDtl) listcheckIn.get(i);
					if (objDtl.getStrPayee().equals("Y")) {

						List list1 = objGuestService.funGetGuestMaster(objDtl.getStrGuestCode(), clientCode);
						clsGuestMasterHdModel objGuestModel = null;
						if (list1.size() > 0) {
							objGuestModel = (clsGuestMasterHdModel) list1.get(0);

						}
						if (smsContent.contains("%%GuestName")) {
							smsContent = smsContent.replace("%%GuestName", objGuestModel.getStrFirstName() + " " + objGuestModel.getStrMiddleName() + " " + objGuestModel.getStrLastName());
						}

						if (smsContent.contains("%%CompanyName")) {
							List<clsCompanyMasterModel> listCompanyModel = objPropertySetupService.funGetListCompanyMasterModel(clientCode);
							smsContent = smsContent.replace("%%CompanyName", listCompanyModel.get(0).getStrCompanyName());
						}
						if (smsContent.contains("%%PropertyName")) {
							clsPropertyMaster objProperty = objPropertyMasterService.funGetProperty(propCode, clientCode);
							smsContent = smsContent.replace("%%PropertyName", objProperty.getPropertyName());
						}

						if (smsContent.contains("%%PaymentNo")) {
							smsContent = smsContent.replace("%%RNo", paymentNo);
						}

						if (smsContent.contains("%%SettlementDesc")) {
							smsContent = smsContent.replace("%%SettlementDesc", settleDesc);
						}

						if (smsContent.contains("%%GuestName")) {
							smsContent = smsContent.replace("%%GuestName", objGuestModel.getStrFirstName() + " " + objGuestModel.getStrMiddleName() + " " + objGuestModel.getStrLastName());
						}
						if (smsContent.contains("%%Amount")) {
							smsContent = smsContent.replace("%%Amount", String.valueOf(modelData.getDblPaidAmt()));
						}

						if (smsContent.contains("%%RoomNo")) {
							clsRoomMasterModel roomNo = objRoomMaster.funGetRoomMaster(objDtl.getStrRoomNo(), clientCode);
							smsContent = smsContent.replace("%%RoomNo", roomNo.getStrRoomDesc());
						}

						if (smsAPIUrl.contains("ReceiverNo")) {

							smsAPIUrl = smsAPIUrl.replace("ReceiverNo", String.valueOf(objGuestModel.getLngMobileNo()));
							strMobileNo = String.valueOf(objGuestModel.getLngMobileNo());
						}
						if (smsAPIUrl.contains("MsgContent")) {
							smsAPIUrl = smsAPIUrl.replace("MsgContent", smsContent);
							smsAPIUrl = smsAPIUrl.replace(" ", "%20");
						}

						URL url;
						HttpURLConnection uc = null;
						StringBuilder output = new StringBuilder();

						try {
							url = new URL(smsAPIUrl);
							uc = (HttpURLConnection) url.openConnection();
							if (String.valueOf(objGuestModel.getLngMobileNo()).length() >= 10) {
								BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), Charset.forName("UTF-8")));
								String inputLine;
								while ((inputLine = in.readLine()) != null) {
									output.append(inputLine);
								}
								in.close();
							}

						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							uc.disconnect();
						}
					}
				}
			}

		}

		if (modelData.getStrAgainst().equalsIgnoreCase("BillNo")) {

		}

	}
}
