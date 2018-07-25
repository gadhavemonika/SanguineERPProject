package com.sanguine.webpms.controller;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webpms.bean.clsTaxCalculation;
import com.sanguine.webpms.bean.clsTaxProductDtl;
import com.sanguine.webpms.bean.clsVoidBillBean;
import com.sanguine.webpms.model.clsBillDtlModel;
import com.sanguine.webpms.model.clsBillHdModel;
import com.sanguine.webpms.model.clsBillTaxDtlModel;
import com.sanguine.webpms.model.clsVoidBillDtlModel;
import com.sanguine.webpms.model.clsVoidBillHdModel;
import com.sanguine.webpms.model.clsVoidBillTaxDtlModel;
import com.sanguine.webpms.service.clsVoidBillService;

@Transactional
@Controller
public class clsVoidBillController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	
	@Autowired
	private clsGlobalFunctions objGlobal;
	
	@Autowired 
	private clsVoidBillService objVoidBillService;
	
	@Autowired
	clsPMSUtilityFunctions objPMSUtility;
	
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	Map<String,String> hmReason=new HashMap<>();
	
	@RequestMapping(value="/frmVoidBill",method=RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String,Object> model,HttpServletRequest request){
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String sql="select strReasonCode,strReasonDesc from tblreasonmaster where strClientCode='"+clientCode+"'";
		List listReason = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		
		if(null!=listReason){
			if(listReason.size()>0){
				for(int i=0;i<listReason.size();i++){
					Object ob[]=(Object[]) listReason.get(i);	
					hmReason.put(ob[0].toString(), ob[1].toString());
				}
			}
		}
		model.put("listReason", hmReason);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmVoidBill_1", "command", new clsVoidBillBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmVoidBill", "command", new clsVoidBillBean());
		} else {
			return null;
		}
	}
	
	@RequestMapping(value="/loadVoidBill",method=RequestMethod.GET)
	public @ResponseBody List<clsVoidBillBean> funLoadVoidBill(@RequestParam("strBillNo")String strBillNo,HttpServletRequest req)
	{
		List<clsVoidBillBean> listVoidDtl=new ArrayList();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String sql=" select a.strBillNo,a.strFolioNo,a.dteBillDate,a.dblGrandTotal ," //3
				+ " ifnull(b.strPerticulars,''),ifnull(sum(b.dblDebitAmt),0),ifnull(sum(b.dblCreditAmt),0),ifnull(b.dblBalanceAmt,0),"
				+ " c.strGuestCode,IFNULL(d.strFirstName,''),IFNULL(d.strLastName,''),e.strRoomDesc ,IFNULL(b.strDocNo,''),a.strRoomNo,IFNULL(b.strRevenueCode,'')"
				+ " from tblbillhd a left outer join tblbilldtl b on a.strBillNo=b.strBillNo "
				+ " left outer join tblcheckindtl c on a.strCheckInNo=c.strCheckInNo "
				+ " left outer join tblguestmaster d on c.strGuestCode=d.strGuestCode "
				+ " left outer join tblroom e on c.strRoomNo=e.strRoomCode "
				+ " where a.strBillNo='"+strBillNo+"' and a.strRoomNo=e.strRoomCode "
				+ " group by b.strDocNo;";
		
		List listBillDetails = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		if(null!=listBillDetails){
			if(listBillDetails.size()>0){
				clsVoidBillBean obVoidBean;
				for(int i=0;i<listBillDetails.size();i++){
					obVoidBean=new clsVoidBillBean();
					
					Object ob[]=(Object[]) listBillDetails.get(i);
					obVoidBean.setStrBillNo(ob[0].toString());
					obVoidBean.setStrFolioNo(ob[1].toString());
					obVoidBean.setStrBilldate(ob[2].toString());
					obVoidBean.setDblTotalAmt(Double.parseDouble(ob[3].toString()));
					obVoidBean.setStrMenuHead(ob[4].toString());
					obVoidBean.setDblIncomeHeadPrice(Double.parseDouble(ob[5].toString())-Double.parseDouble(ob[6].toString()));
					obVoidBean.setStrGuestCode(ob[8].toString());
					obVoidBean.setStrGuestName(ob[9].toString()+" "+ob[10].toString());
					obVoidBean.setStrRoomNo(ob[13].toString());
					obVoidBean.setStrDocNo(ob[12].toString());
					obVoidBean.setStrRoomName(ob[11].toString());
					obVoidBean.setStrRevenueCode(ob[14].toString());
					
					listVoidDtl.add(obVoidBean);
				}
			}
		}
		
		return listVoidDtl;
	}
	
	@RequestMapping(value = "/voidBill", method = RequestMethod.POST)
	public ModelAndView funVoidBill(@ModelAttribute("command") @Valid clsVoidBillBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String PMSDate = objGlobal.funGetDate("yyyy-MM-dd", req.getSession().getAttribute("PMSDate").toString());
		String strSuccessMsg="";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String strBillNo="";
		if (!result.hasErrors()) {
			if(null!=objBean){
				String voidType=objBean.getStrVoidType();
				strBillNo=objBean.getStrBillNo();
				List<clsVoidBillBean> listVoidBilldtlBean =objBean.getListVoidBilldtl();
				
				clsBillHdModel objBillModel=objVoidBillService.funGetBillData(objBean.getStrRoomNo(), strBillNo, clientCode);
				
				if(null!=listVoidBilldtlBean){
					if(voidType.equals("fullVoid")){
						if(objBillModel!=null){
							clsVoidBillHdModel objVoidHdModel=new clsVoidBillHdModel();
							objVoidHdModel.setStrBillNo(objBillModel.getStrBillNo());
							objVoidHdModel.setDteBillDate(PMSDate);
							objVoidHdModel.setStrClientCode(clientCode);
							objVoidHdModel.setStrCheckInNo(objBillModel.getStrCheckInNo());
							objVoidHdModel.setStrFolioNo(objBillModel.getStrFolioNo());
							objVoidHdModel.setStrRoomNo(objBillModel.getStrRoomNo());
							objVoidHdModel.setStrExtraBedCode(objBillModel.getStrExtraBedCode());
							objVoidHdModel.setStrRegistrationNo(objBillModel.getStrRegistrationNo());
							objVoidHdModel.setStrReservationNo(objBillModel.getStrReservationNo());
							objVoidHdModel.setDblGrandTotal(objBillModel.getDblGrandTotal());
							objVoidHdModel.setStrUserCreated(objBillModel.getStrUserCreated());
							objVoidHdModel.setStrUserEdited(userCode);
							objVoidHdModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
							objVoidHdModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
							objVoidHdModel.setStrVoidType(voidType);
							objVoidHdModel.setStrReasonCode(objBean.getStrReason());
							objVoidHdModel.setStrReasonName(hmReason.get(objBean.getStrReason()));
							objVoidHdModel.setStrRemark(objBean.getStrRemark());
							List<clsVoidBillDtlModel> listVoidBillDtlModels=new ArrayList();
							
							String sql = " Select *  from tblbilldtl a where a.strBillNo='" + strBillNo + "' and a.strClientCode='" + clientCode + "' ";
							List listBillDtl = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
							
							for (int j = 0; j < listBillDtl.size(); j++) {
								Object[] objMdl = (Object[]) listBillDtl.get(j);
								clsVoidBillDtlModel voidbillDtlModel = new clsVoidBillDtlModel();
									voidbillDtlModel.setStrFolioNo(objMdl[1].toString());
									voidbillDtlModel.setDteDocDate(objMdl[2].toString());
									voidbillDtlModel.setStrDocNo(objMdl[3].toString());
									voidbillDtlModel.setStrPerticulars(objMdl[4].toString());
									voidbillDtlModel.setStrRevenueType(objMdl[5].toString());
									voidbillDtlModel.setStrRevenueCode(objMdl[6].toString());
									voidbillDtlModel.setDblDebitAmt(Double.parseDouble(objMdl[7].toString()));
									voidbillDtlModel.setDblCreditAmt(Double.parseDouble(objMdl[8].toString()));
									voidbillDtlModel.setDblBalanceAmt(Double.parseDouble(objMdl[9].toString()));
									listVoidBillDtlModels.add(voidbillDtlModel);
								
							}
							
							sql = " Select *  from tblvoidbilldtl a where a.strBillNo='" + strBillNo + "' and a.strClientCode='" + clientCode + "' ";
							listBillDtl = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
							
							for (int j = 0; j < listBillDtl.size(); j++) {
								Object[] objMdl = (Object[]) listBillDtl.get(j);
								clsVoidBillDtlModel voidbillDtlModel = new clsVoidBillDtlModel();
									voidbillDtlModel.setStrFolioNo(objMdl[1].toString());
									voidbillDtlModel.setDteDocDate(objMdl[2].toString());
									voidbillDtlModel.setStrDocNo(objMdl[3].toString());
									voidbillDtlModel.setStrPerticulars(objMdl[4].toString());
									voidbillDtlModel.setStrRevenueType(objMdl[5].toString());
									voidbillDtlModel.setStrRevenueCode(objMdl[6].toString());
									voidbillDtlModel.setDblDebitAmt(Double.parseDouble(objMdl[7].toString()));
									voidbillDtlModel.setDblCreditAmt(Double.parseDouble(objMdl[8].toString()));
									voidbillDtlModel.setDblBalanceAmt(Double.parseDouble(objMdl[9].toString()));
									listVoidBillDtlModels.add(voidbillDtlModel);
								
							}
							objVoidHdModel.setListVoidBillDtlModels(listVoidBillDtlModels);
							
							List<clsVoidBillTaxDtlModel> listVoidBillTaxDtlModels=new ArrayList();
							sql = " Select *  from tblbilltaxdtl a where a.strBillNo='" + strBillNo + "' and a.strClientCode='" + clientCode + "' ";
							List listBillTaxDtl = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
							if(listBillTaxDtl.size()>0){
								for (int j = 0; j < listBillTaxDtl.size(); j++) {
									Object[] objMdl = (Object[]) listBillTaxDtl.get(j);
									clsVoidBillTaxDtlModel obVoidTaxDtl=new clsVoidBillTaxDtlModel();
									obVoidTaxDtl.setStrDocNo(objMdl[1].toString());
									obVoidTaxDtl.setStrTaxCode(objMdl[2].toString());
									obVoidTaxDtl.setStrTaxDesc(objMdl[3].toString());
									obVoidTaxDtl.setDblTaxableAmt(Double.parseDouble(objMdl[4].toString()));
									obVoidTaxDtl.setDblTaxAmt(Double.parseDouble(objMdl[5].toString()));
									listVoidBillTaxDtlModels.add(obVoidTaxDtl);
								}
							}
							
							sql = " Select *  from tblvoidbilltaxdtl a where a.strBillNo='" + strBillNo + "' and a.strClientCode='" + clientCode + "' ";
							listBillTaxDtl = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
							if(listBillTaxDtl.size()>0){
								for (int j = 0; j < listBillTaxDtl.size(); j++) {
									Object[] objMdl = (Object[]) listBillTaxDtl.get(j);
									clsVoidBillTaxDtlModel obVoidTaxDtl=new clsVoidBillTaxDtlModel();
									obVoidTaxDtl.setStrDocNo(objMdl[1].toString());
									obVoidTaxDtl.setStrTaxCode(objMdl[2].toString());
									obVoidTaxDtl.setStrTaxDesc(objMdl[3].toString());
									obVoidTaxDtl.setDblTaxableAmt(Double.parseDouble(objMdl[4].toString()));
									obVoidTaxDtl.setDblTaxAmt(Double.parseDouble(objMdl[5].toString()));
									listVoidBillTaxDtlModels.add(obVoidTaxDtl);
								}
							}
							
							objVoidHdModel.setListVoidBillTaxDtlModels(listVoidBillTaxDtlModels);
							
							objVoidBillService.funUpdateVoidBillData(objBillModel,objVoidHdModel);
							strSuccessMsg="Bill Voided Successfully : ";
						}
						
					}else{
						// Item voided
						
						if(objBillModel!=null){
							clsVoidBillHdModel objVoidHdModel=new clsVoidBillHdModel();
							objVoidHdModel.setStrBillNo(objBillModel.getStrBillNo());
							objVoidHdModel.setDteBillDate(PMSDate);
							objVoidHdModel.setStrClientCode(clientCode);
							objVoidHdModel.setStrCheckInNo(objBillModel.getStrCheckInNo());
							objVoidHdModel.setStrFolioNo(objBillModel.getStrFolioNo());
							objVoidHdModel.setStrRoomNo(objBillModel.getStrRoomNo());
							objVoidHdModel.setStrExtraBedCode(objBillModel.getStrExtraBedCode());
							objVoidHdModel.setStrRegistrationNo(objBillModel.getStrRegistrationNo());
							objVoidHdModel.setStrReservationNo(objBillModel.getStrReservationNo());
							objVoidHdModel.setDblGrandTotal(0);
							objVoidHdModel.setStrUserCreated(objBillModel.getStrUserCreated());
							objVoidHdModel.setStrUserEdited(userCode);
							objVoidHdModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
							objVoidHdModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
							objVoidHdModel.setStrVoidType(voidType);
							objVoidHdModel.setStrReasonCode(objBean.getStrReason());
							objVoidHdModel.setStrReasonName(hmReason.get(objBean.getStrReason()));
							objVoidHdModel.setStrRemark(objBean.getStrRemark());
							
							List<clsVoidBillDtlModel> listVoidBillDtlModels=new ArrayList();
							
							String sql = " Select *  from tblbilldtl a where a.strBillNo='" + strBillNo + "' and a.strClientCode='" + clientCode + "' ";
							List listBillDtl = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
							
							double grandTotalVoidBill = 0;
							double grandTotalBillHd = 0;
							clsVoidBillBean objVoidListBean;
							clsBillDtlModel objBillDtlModel=new clsBillDtlModel();
							clsVoidBillDtlModel voidbillDtlModel = new clsVoidBillDtlModel();
							clsTaxProductDtl objTaxProductDtl = new clsTaxProductDtl();
							
							
							Map<String,clsBillDtlModel> hmBillDtl=new HashMap<>();
							
							List<clsBillDtlModel> listBillDtlModels=new ArrayList();
							List<clsTaxProductDtl> listTaxProdDtl = new ArrayList<clsTaxProductDtl>();
							List<clsBillTaxDtlModel> listBillTaxModels =new ArrayList();//objBillModel.getListBillTaxDtlModels();
							List<clsVoidBillTaxDtlModel> listVoidBillTaxDtlModels=new ArrayList();
							
							if(listVoidBilldtlBean.size()>0){
								for (int j = 0; j < listBillDtl.size(); j++) {
									Object[] objMdl = (Object[]) listBillDtl.get(j);
									
									for(int k=0;k<listVoidBilldtlBean.size();k++)
									{
										objVoidListBean = listVoidBilldtlBean.get(k);
										if(objMdl[3].toString().equals(objVoidListBean.getStrDocNo()))
										{
											//Bill detail data
											objBillDtlModel=new clsBillDtlModel();
											grandTotalBillHd += Double.parseDouble(objMdl[7].toString());
											objBillDtlModel.setStrFolioNo(objMdl[1].toString());
											objBillDtlModel.setDteDocDate(objMdl[2].toString());
											objBillDtlModel.setStrDocNo(objMdl[3].toString());
											objBillDtlModel.setStrPerticulars(objMdl[4].toString());
											objBillDtlModel.setStrRevenueType(objMdl[5].toString());
											objBillDtlModel.setStrRevenueCode(objMdl[6].toString());
											objBillDtlModel.setDblDebitAmt(Double.parseDouble(objMdl[7].toString()));
											objBillDtlModel.setDblCreditAmt(Double.parseDouble(objMdl[8].toString()));
											objBillDtlModel.setDblBalanceAmt(Double.parseDouble(objMdl[9].toString()));
											
											listBillDtlModels.add(objBillDtlModel);
											hmBillDtl.put(objMdl[3].toString(), objBillDtlModel);
											
											objTaxProductDtl = new clsTaxProductDtl();
											objTaxProductDtl.setStrTaxProdCode(objBillDtlModel.getStrRevenueCode());
											objTaxProductDtl.setStrTaxProdName(objBillDtlModel.getStrPerticulars());
											objTaxProductDtl.setDblTaxProdAmt(objBillDtlModel.getDblDebitAmt());
											
											listTaxProdDtl.add(objTaxProductDtl);
											Map<String, List<clsTaxCalculation>> hmTaxCalDtl = objPMSUtility.funCalculatePMSTax(listTaxProdDtl, "Income Head");
											
											if (hmTaxCalDtl.size() > 0) {
												List<clsTaxCalculation> listTaxCal = hmTaxCalDtl.get(objBillDtlModel.getStrRevenueCode());
												for (clsTaxCalculation objTaxCal : listTaxCal) {
													clsBillTaxDtlModel objBillTaxDtl = new clsBillTaxDtlModel();
													objBillTaxDtl.setStrDocNo(objBillDtlModel.getStrDocNo());
													objBillTaxDtl.setStrTaxCode(objTaxCal.getStrTaxCode());
													objBillTaxDtl.setStrTaxDesc(objTaxCal.getStrTaxDesc());
													objBillTaxDtl.setDblTaxableAmt(objTaxCal.getDblTaxableAmt());
													objBillTaxDtl.setDblTaxAmt(objTaxCal.getDblTaxAmt());
													listBillTaxModels.add(objBillTaxDtl);
												}
											}
										}
									}
								}
								objBillModel.setListBillTaxDtlModels(listBillTaxModels);
								objBillModel.setListBillDtlModels(listBillDtlModels);
								objBillModel.setDblGrandTotal(grandTotalBillHd);
								
								for (int j = 0; j < listBillDtl.size(); j++) {
									Object[] objMdl = (Object[]) listBillDtl.get(j);
									if(!hmBillDtl.containsKey(objMdl[3].toString()))
									{
										voidbillDtlModel=new clsVoidBillDtlModel();
										grandTotalVoidBill += Double.parseDouble(objMdl[7].toString());
										voidbillDtlModel.setStrFolioNo(objMdl[1].toString());
										voidbillDtlModel.setDteDocDate(objMdl[2].toString());
										voidbillDtlModel.setStrDocNo(objMdl[3].toString());
										voidbillDtlModel.setStrPerticulars(objMdl[4].toString());
										voidbillDtlModel.setStrRevenueType(objMdl[5].toString());
										voidbillDtlModel.setStrRevenueCode(objMdl[6].toString());
										voidbillDtlModel.setDblDebitAmt(Double.parseDouble(objMdl[7].toString()));
										voidbillDtlModel.setDblCreditAmt(Double.parseDouble(objMdl[8].toString()));
										voidbillDtlModel.setDblBalanceAmt(Double.parseDouble(objMdl[9].toString()));
										
										listVoidBillDtlModels.add(voidbillDtlModel);
										
										objTaxProductDtl = new clsTaxProductDtl();
										objTaxProductDtl.setStrTaxProdCode(objBillDtlModel.getStrRevenueCode());
										objTaxProductDtl.setStrTaxProdName(objBillDtlModel.getStrPerticulars());
										objTaxProductDtl.setDblTaxProdAmt(objBillDtlModel.getDblDebitAmt());
										
										listTaxProdDtl.add(objTaxProductDtl);
										Map<String, List<clsTaxCalculation>> hmTaxCalDtl = objPMSUtility.funCalculatePMSTax(listTaxProdDtl, "Income Head");
										
										if (hmTaxCalDtl.size() > 0) {
											List<clsTaxCalculation> listTaxCal = hmTaxCalDtl.get(objBillDtlModel.getStrRevenueCode());
											for (clsTaxCalculation objTaxCal : listTaxCal) {
												clsVoidBillTaxDtlModel objVoidBillTaxDtl = new clsVoidBillTaxDtlModel();
												objVoidBillTaxDtl.setStrDocNo(objBillDtlModel.getStrDocNo());
												objVoidBillTaxDtl.setStrTaxCode(objTaxCal.getStrTaxCode());
												objVoidBillTaxDtl.setStrTaxDesc(objTaxCal.getStrTaxDesc());
												objVoidBillTaxDtl.setDblTaxableAmt(objTaxCal.getDblTaxableAmt());
												objVoidBillTaxDtl.setDblTaxAmt(objTaxCal.getDblTaxAmt());
												listVoidBillTaxDtlModels.add(objVoidBillTaxDtl);
											}
										}
									}
								}
								objVoidHdModel.setListVoidBillTaxDtlModels(listVoidBillTaxDtlModels);
								objVoidHdModel.setListVoidBillDtlModels(listVoidBillDtlModels);
								objVoidHdModel.setDblGrandTotal(grandTotalVoidBill);
								
								objVoidBillService.funUpdateVoidBillItemData(objBillModel,objVoidHdModel);
								strSuccessMsg="Bill Successfully Voided ";
							}
						}
					}
						
					
				}
				
			}
		}
		
		req.getSession().setAttribute("success", true);
		req.getSession().setAttribute("successMessage", strSuccessMsg.concat(strBillNo));

		return new ModelAndView("redirect:/frmVoidBill.html?saddr=" + urlHits);
	}
	

	
	@RequestMapping(value = "/frmVoidBillReport", method = RequestMethod.GET)
	public ModelAndView funOpenReportForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmVoidBillReport_1", "command", new clsVoidBillBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmVoidBillReport", "command", new clsVoidBillBean());
		} else {
			return null;
		}
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/voidBillReportDetail", method = RequestMethod.GET)
	public void funGenerateVoidBillReport(@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate, HttpServletRequest req, HttpServletResponse resp) {
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webpms/rptVoidBillReportDtl.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
		

			HashMap reportParams = new HashMap();

			reportParams.put("pCompanyName", companyName);
			reportParams.put("pAddress1", objSetup.getStrAdd1() + "," + objSetup.getStrAdd2() + "," + objSetup.getStrCity());
			reportParams.put("pAddress2", objSetup.getStrState() + "," + objSetup.getStrCountry() + "," + objSetup.getStrPin());
			reportParams.put("pContactDetails", "");
			reportParams.put("strImagePath", imagePath);
			reportParams.put("strUserCode", userCode);
			reportParams.put("pFromDate", fromDate);
			reportParams.put("pTtoDate", toDate);
			//reportParams.put("propName", propName);

			// get all parameters
			
			String sqlVoid=" SELECT a.strBillNo, DATE_FORMAT(a.dteBillDate,'%d-%m-%Y'),a.strCheckInNo,b.strPerticulars, "//3
					+ " SUM(b.dblDebitAmt), a.strReasonName,a.strRemark,a.strUserCreated,a.strUserEdited,a.strVoidType, "//9
					+ " d.strRoomDesc, CONCAT(e.strGuestPrefix,\" \",e.strFirstName,\" \",e.strLastName) AS gName "
					+ " FROM tblvoidbillhd a inner join tblvoidbilldtl b on a.strBillNo=b.strBillNo "
					+ " left outer join tblcheckindtl c on a.strCheckInNo=c.strCheckInNo "
					+ " left outer join tblroom d on a.strRoomNo=d.strRoomCode "
  					+ " left outer join tblguestmaster e on c.strGuestCode=e.strGuestCode " 
  					+ " where c.strPayee='Y' AND a.strVoidType='fullVoid' or a.strVoidType='itemVoid' " 
 					+ " AND DATE(a.dteBillDate) BETWEEN '"+fromDate+"' AND '"+toDate+"' "
 					+ " GROUP BY a.strBillNo,b.strPerticulars " 
					+ " ORDER BY a.dteBillDate,a.strBillNo;";
			
			List listOfPax = objGlobalFunctionsService.funGetDataList(sqlVoid, "sql");
			ArrayList<clsVoidBillBean> fieldList = new ArrayList<clsVoidBillBean>();

			for (int i = 0; i < listOfPax.size(); i++) {
				Object[] arr = (Object[]) listOfPax.get(i);
				clsVoidBillBean objVoidBean = new clsVoidBillBean();
				objVoidBean.setStrBillNo(arr[0].toString());
				objVoidBean.setStrBilldate(arr[1].toString().split(" ")[0]);
				objVoidBean.setStrMenuHead(arr[3].toString());
				objVoidBean.setDblIncomeHeadPrice(Double.parseDouble(arr[4].toString()));
				objVoidBean.setStrReason(arr[5].toString());
				objVoidBean.setStrRemark(arr[6].toString());
				
				objVoidBean.setStrUserCreated(arr[7].toString());
				objVoidBean.setStrVoidedUser(arr[8].toString());
				objVoidBean.setStrVoidType(arr[9].toString());
				objVoidBean.setStrRoomName(arr[10].toString());
				objVoidBean.setStrGuestName(arr[11].toString());
				fieldList.add(objVoidBean);

			}
			
			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
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
				resp.setHeader("Content-Disposition", "inline;filename=PaxReport.pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/voidBillReportSummary", method = RequestMethod.GET)
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
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webpms/rptVoidBillReportSummary.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
		

			HashMap reportParams = new HashMap();

			reportParams.put("pCompanyName", companyName);
			reportParams.put("pAddress1", objSetup.getStrAdd1() + "," + objSetup.getStrAdd2() + "," + objSetup.getStrCity());
			reportParams.put("pAddress2", objSetup.getStrState() + "," + objSetup.getStrCountry() + "," + objSetup.getStrPin());
			reportParams.put("pContactDetails", "");
			reportParams.put("strImagePath", imagePath);
			reportParams.put("strUserCode", userCode);
			reportParams.put("pFromDate", fromDate);
			reportParams.put("pTtoDate", toDate);
			//reportParams.put("propName", propName);

			// get all parameters
			String sqlVoid="SELECT a.strBillNo, DATE_FORMAT(a.dteBillDate,'%d-%m-%Y'),a.dblGrandTotal,a.strRemark, "
					+ " a.strUserCreated,a.strUserEdited,a.strVoidType, d.strRoomDesc, "
					+ " CONCAT(e.strGuestPrefix,\" \",e.strFirstName,\" \",e.strLastName) AS gName,a.strReasonName "
					+ " FROM tblvoidbillhd a "
					+ " left outer join  tblcheckindtl c on a.strCheckInNo=c.strCheckInNo "
					+ " left outer join tblroom d on a.strRoomNo=d.strRoomCode "
					+ " left outer join tblguestmaster e on c.strGuestCode=e.strGuestCode "
					+ " where c.strPayee='Y' AND a.strVoidType='itemVoid' "
 					+ " OR a.strVoidType='fullVoid' and c.strRoomNo=d.strRoomCode "
					+ " and Date(a.dteBillDate) between '"+fromDate+"' and '"+toDate+"' "
					+ " order by a.strBillNo;";
			
	
			List listOfPax = objGlobalFunctionsService.funGetDataList(sqlVoid, "sql");
			ArrayList<clsVoidBillBean> fieldList = new ArrayList<clsVoidBillBean>();

			for (int i = 0; i < listOfPax.size(); i++) {
				Object[] arr = (Object[]) listOfPax.get(i);
				clsVoidBillBean objVoidBean = new clsVoidBillBean();
				objVoidBean.setStrBillNo(arr[0].toString());
				objVoidBean.setStrBilldate(arr[1].toString().split(" ")[0]);
				objVoidBean.setDblIncomeHeadPrice(Double.parseDouble(arr[2].toString()));
				objVoidBean.setStrRemark(arr[3].toString());
				
				objVoidBean.setStrUserCreated(arr[4].toString());
				objVoidBean.setStrVoidedUser(arr[5].toString());
				objVoidBean.setStrVoidType(arr[6].toString());
				objVoidBean.setStrRoomName(arr[7].toString());
				objVoidBean.setStrGuestName(arr[8].toString());
				objVoidBean.setStrReason(arr[9].toString());
				fieldList.add(objVoidBean);

			}
			
			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
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
