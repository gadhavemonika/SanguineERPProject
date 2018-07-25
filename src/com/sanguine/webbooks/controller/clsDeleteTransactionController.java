package com.sanguine.webbooks.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.base.service.intfBaseService;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsReasonMaster;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webbooks.bean.clsDeleteTransactionBean;
import com.sanguine.webbooks.model.clsDeleteTransactionModel;
import com.sanguine.webbooks.model.clsJVDebtorDtlModel;
import com.sanguine.webbooks.model.clsJVDtlModel;
import com.sanguine.webbooks.model.clsJVHdModel;
import com.sanguine.webbooks.model.clsPaymentDebtorDtlModel;
import com.sanguine.webbooks.model.clsPaymentDtl;
import com.sanguine.webbooks.model.clsPaymentHdModel;
import com.sanguine.webbooks.model.clsReceiptDebtorDtlModel;
import com.sanguine.webbooks.model.clsReceiptDtlModel;
import com.sanguine.webbooks.model.clsReceiptHdModel;
import com.sanguine.webbooks.model.clsWebBooksAuditDebtorDtlModel;
import com.sanguine.webbooks.model.clsWebBooksAuditDtlModel;
import com.sanguine.webbooks.model.clsWebBooksAuditHdModel;
import com.sanguine.webbooks.service.clsDeleteTransactionService;
import com.sanguine.webbooks.service.clsJVService;
import com.sanguine.webbooks.service.clsPaymentService;
import com.sanguine.webbooks.service.clsReceiptService;

@Controller
public class clsDeleteTransactionController {

	@Autowired
	private clsDeleteTransactionService objDeleteTransactionService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsReceiptService objReceiptService;

	@Autowired
	private clsPaymentService objPaymentService;

	@Autowired
	private clsJVService objJVService;
	

	@Autowired
	intfBaseService objBaseService;

	// Open DeleteTransaction
	@RequestMapping(value = "/frmWebBooksDeleteTransaction", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") clsDeleteTransactionBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest req) {
		Map<String, String> mapTransForms = new HashMap<String, String>();
		String sql = "select strFormName,strFormDesc from clsTreeMasterModel " + "where strType='T' and strModule='5' " + "order by strFormName";
		List list = objGlobalFunctionsService.funGetList(sql, "hql");
		for (int cnt = 0; cnt < list.size(); cnt++) {
			Object[] arrObj = (Object[]) list.get(cnt);
			mapTransForms.put(arrObj[0].toString(), arrObj[1].toString());
		}
		ModelAndView objModelAndView = new ModelAndView();
		objModelAndView.addObject("listFormName", mapTransForms);
		return objModelAndView;
	}


	// Save or Update DeleteTransaction
		@RequestMapping(value = "/saveWebBooksDeleteTrans", method = RequestMethod.POST)
		public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsDeleteTransactionBean objBean, BindingResult result, HttpServletRequest req) {
			if (!result.hasErrors()) {
				String clientCode = req.getSession().getAttribute("clientCode").toString();
				String userCode = req.getSession().getAttribute("usercode").toString();
				String propertyCode = req.getSession().getAttribute("propertyCode").toString();
				funSaveTransactionToAuditTable(objBean.getStrFormName(), objBean.getStrTransCode(), clientCode, propertyCode);

				clsDeleteTransactionModel objModel = funPrepareModel(objBean, userCode, clientCode);
				objDeleteTransactionService.funAddUpdateDeleteTransaction(objModel);

				int cnt=funDeleteTransactionRecord(objBean.getStrFormName(), objBean.getStrTransCode(), clientCode, propertyCode,userCode);
				if(cnt==1)
				{
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Transaction Code : ".concat(objBean.getStrTransCode()));
				}else{
					req.getSession().setAttribute("success", false);
					req.getSession().setAttribute("successMessage", "Transaction Code : ".concat(objBean.getStrTransCode()));
				}return new ModelAndView("redirect:/frmWebBooksDeleteTransaction.html");
			} else {
				return new ModelAndView("frmWebBooksDeleteTransaction");
			}
		}

	// Convert bean to model function
	private clsDeleteTransactionModel funPrepareModel(clsDeleteTransactionBean objBean, String userCode, String clientCode) {

		clsDeleteTransactionModel objModel = new clsDeleteTransactionModel();
		objModel.setStrTransCode(objBean.getStrTransCode());
		objModel.setStrReasonCode(objBean.getStrReasonCode());
		objModel.setStrNarration(objGlobal.funIfNull(objBean.getStrNarration(), "", objBean.getStrNarration()));
		objModel.setStrUserCode(userCode);
		objModel.setStrFormName(objBean.getStrFormName());
		objModel.setStrClientCode(clientCode);
		objModel.setStrDataPostFlag("N");
		objModel.setDteDeleteDate(objGlobal.funGetCurrentDate("yyyy/MM/dd"));

		return objModel;
	}

	private int funSaveTransactionToAuditTable(String formName, String transCode, String clientCode, String propertyCode) {
		switch (formName) {
		case "frmJV":
			funAddJVRecordToAudit(transCode, clientCode, propertyCode, formName);
			break;

		case "frmReceipt":
			funAddReceiptRecordToAudit(transCode, clientCode, propertyCode, formName);
			break;

		case "frmPayment":
			funAddPaymentRecordToAudit(transCode, clientCode, propertyCode, formName);
			break;
		}
		return 1;
	}

	private int funDeleteTransactionRecord(String formName, String transCode, String clientCode, String propertyCode,String userCode) {
		StringBuilder sqlDelete=new StringBuilder();
		int i=0;
		try{
			String narration="Entry deleted By "+userCode+" On "+objGlobal.funGetCurrentDateTime("yyyy-MM-dd");
			switch (formName) {
			case "frmJV":
				clsJVHdModel objJVHd = objJVService.funGetJVList(transCode, clientCode, propertyCode);
				
				if(objJVHd.getStrSource().equalsIgnoreCase("User"))
				{
					sqlDelete.append( "update  clsJVHdModel a  set a.dblAmt=0.0 , a.strNarration='"+narration+"',a.strSource='',a.strSourceDocNo='' where strVouchNo='"+transCode+"'");	
					objBaseService.funExcecteUpdateModuleWise(sqlDelete,"hql",  "WebBooks");
					sqlDelete.setLength(0);
					
					sqlDelete.append( "update  tbljvdtl  a set a.dblDrAmt=0.0, a.dblCrAmt=0.0 , a.strNarration='"+narration+"',a.strAccountCode='',a.strAccountName='' where strVouchNo='"+transCode+"'");	
					objBaseService.funExcecteUpdateModuleWise(sqlDelete,"sql",  "WebBooks");
					sqlDelete.setLength(0);
					
					sqlDelete.append( "update  tbljvdebtordtl a set a.dblAmt=0.0 , a.strNarration='"+narration+"',a.strDebtorCode='',a.strDebtorName='' where strVouchNo='"+transCode+"'");	
					objBaseService.funExcecteUpdateModuleWise(sqlDelete,"sql",  "WebBooks");
					sqlDelete.setLength(0);
					i++;
				}
				break;

			case "frmReceipt":
				/*clsReceiptHdModel objReceiptHd = objReceiptService.funGetReceiptList(transCode, clientCode, propertyCode);
				objReceiptService.funDeleteReceipt(objReceiptHd);*/
		
						sqlDelete.setLength(0);
						sqlDelete.append("update  clsReceiptHdModel a  set a.dblAmt=0.0  , a.strNarration='"+narration+"' where strVouchNo='" + transCode + "'"  );
						objBaseService.funExcecteUpdateModuleWise(sqlDelete,"hql",  "WebBooks");
						sqlDelete.setLength(0);
						sqlDelete.append("update  tblreceiptdtl a set a.dblDrAmt=0.0, a.dblCrAmt=0.0  , a.strNarration='"+narration+"',a.strAccountCode='',a.strAccountName='' where strVouchNo='" + transCode + "' " );
						objBaseService.funExcecteUpdateModuleWise(sqlDelete,"sql",  "WebBooks");
						sqlDelete.setLength(0);
						sqlDelete.append("update  tblreceiptdebtordtl a set a.dblAmt=0.0 , a.strNarration='"+narration+"',a.strDebtorCode='',a.strDebtorName=''  where strVouchNo='" + transCode + "'" );
						objBaseService.funExcecteUpdateModuleWise(sqlDelete,"sql",  "WebBooks");
						sqlDelete.setLength(0);
						sqlDelete.append("update  tblreceiptinvdtl  a set a.dblInvAmt=0.0,a.dblPayedAmt=0.0    where strVouchNo='" + transCode + "'  ");
						objBaseService.funExcecteUpdateModuleWise(sqlDelete,"sql",  "WebBooks");
						sqlDelete.setLength(0);
				i++;
				break;

			case "frmPayment":
//				clsPaymentHdModel objPaymentHd = objPaymentService.funGetPaymentList(transCode, clientCode, propertyCode);
//				objPaymentService.funDeletePayment(objPaymentHd);
				
				sqlDelete.setLength(0);
				sqlDelete.append("update clsPaymentHdModel a  set a.dblAmt=0.0 , a.strNarration='"+narration+"'  where strVouchNo='" + transCode + "'"  );
				objBaseService.funExcecteUpdateModuleWise(sqlDelete,"hql",  "WebBooks");
				sqlDelete.setLength(0);
				sqlDelete.append("update tblpaymentdtl a set a.dblDrAmt=0.0, a.dblCrAmt=0.0, a.strNarration='"+narration+"',a.strAccountCode='',a.strAccountName=''  where strVouchNo='" + transCode + "'  ");
				objBaseService.funExcecteUpdateModuleWise(sqlDelete,"sql",  "WebBooks");
				sqlDelete.setLength(0);
				sqlDelete.append("update tblpaymentdebtordtl a set a.dblAmt=0.0, a.strNarration='"+narration+"',a.strDebtorCode='',a.strDebtorName=''   where strVouchNo='" + transCode + "'  ");
				objBaseService.funExcecteUpdateModuleWise(sqlDelete,"sql",  "WebBooks");
				sqlDelete.setLength(0);
				sqlDelete.append("update tblpaymentgrndtl a set a.dblGRNAmt=0.0,a.dblPayedAmt=0.0 where strVouchNo='" + transCode + "'  ");
				objBaseService.funExcecteUpdateModuleWise(sqlDelete,"sql",  "WebBooks");
				sqlDelete.setLength(0);
			    i++;
				break;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return i;
	}


	
	private int funAddJVRecordToAudit(String transCode, String clientCode, String propertyCode, String formName) {
		clsJVHdModel objJVModel = objJVService.funGetJVList(transCode, clientCode, propertyCode);

		clsWebBooksAuditHdModel objAuditHdModel = new clsWebBooksAuditHdModel();
		objAuditHdModel.setStrTransNo(objJVModel.getStrVouchNo());
		objAuditHdModel.setStrType(objJVModel.getStrType());
		objAuditHdModel.setStrNarration(objJVModel.getStrNarration());
		objAuditHdModel.setStrSancCode(objJVModel.getStrSancCode());
		objAuditHdModel.setDteVouchDate(objJVModel.getDteVouchDate());
		objAuditHdModel.setIntVouchMonth(objJVModel.getIntVouchMonth());
		objAuditHdModel.setDblAmt(objJVModel.getDblAmt());
		objAuditHdModel.setStrTransType(formName);
		objAuditHdModel.setStrTransMode(objJVModel.getStrTransMode());
		objAuditHdModel.setStrModuleType(objJVModel.getStrModuleType());
		objAuditHdModel.setStrMasterPOS(objJVModel.getStrMasterPOS());
		objAuditHdModel.setStrUserCreated(objJVModel.getStrUserCreated());
		objAuditHdModel.setStrUserEdited(objJVModel.getStrUserEdited());
		objAuditHdModel.setDteDateCreated(objJVModel.getDteDateCreated());
		objAuditHdModel.setDteDateEdited(objJVModel.getDteDateEdited());
		objAuditHdModel.setStrClientCode(objJVModel.getStrClientCode());
		objAuditHdModel.setStrPropertyCode(objJVModel.getStrPropertyCode());
		objAuditHdModel.setStrReceiptType("");
		objAuditHdModel.setDteChequeDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objAuditHdModel.setDteClearence(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objAuditHdModel.setIntVouchNum(0);
		objAuditHdModel.setStrAccCode("");
		objAuditHdModel.setStrReceivedFrom("");
		objAuditHdModel.setStrCrDr("");
		objAuditHdModel.setIntOnHold(0);
		objAuditHdModel.setStrDebtorCode("");
		objAuditHdModel.setStrChequeNo("");
		objAuditHdModel.setStrDrawnOn("");
		objAuditHdModel.setStrBranch("");

		List<clsWebBooksAuditDtlModel> listAuditDtlModel = new ArrayList<clsWebBooksAuditDtlModel>();
		List<clsJVDtlModel> listJVDtlModel = objJVModel.getListJVDtlModel();
		for (clsJVDtlModel objJVDtlModel : listJVDtlModel) {
			clsWebBooksAuditDtlModel objAuditDtlModel = new clsWebBooksAuditDtlModel();
			objAuditDtlModel.setStrAccountCode(objJVDtlModel.getStrAccountCode());
			objAuditDtlModel.setStrAccountName(objJVDtlModel.getStrAccountName());
			objAuditDtlModel.setStrCrDr(objJVDtlModel.getStrCrDr());
			objAuditDtlModel.setStrNarration(objJVDtlModel.getStrNarration());
			objAuditDtlModel.setStrOneLine(objJVDtlModel.getStrOneLine());
			objAuditDtlModel.setStrPropertyCode(objJVDtlModel.getStrPropertyCode());
			objAuditDtlModel.setDblCrAmt(objJVDtlModel.getDblCrAmt());
			objAuditDtlModel.setDblDrAmt(objJVDtlModel.getDblDrAmt());
			listAuditDtlModel.add(objAuditDtlModel);
		}

		List<clsWebBooksAuditDebtorDtlModel> listAuditDebtorDtlModel = new ArrayList<clsWebBooksAuditDebtorDtlModel>();
		List<clsJVDebtorDtlModel> listJVDebtorDtlModel = objJVModel.getListJVDebtorDtlModel();
		for (clsJVDebtorDtlModel objJVDebtorDtlModel : listJVDebtorDtlModel) {
			clsWebBooksAuditDebtorDtlModel objAuditDebtorDtlModel = new clsWebBooksAuditDebtorDtlModel();
			objAuditDebtorDtlModel.setStrDebtorCode(objJVDebtorDtlModel.getStrDebtorCode());
			objAuditDebtorDtlModel.setStrDebtorName(objJVDebtorDtlModel.getStrDebtorName());
			objAuditDebtorDtlModel.setStrCrDr(objJVDebtorDtlModel.getStrCrDr());
			objAuditDebtorDtlModel.setDblAmt(objJVDebtorDtlModel.getDblAmt());
			objAuditDebtorDtlModel.setStrBillNo(objJVDebtorDtlModel.getStrBillNo());
			objAuditDebtorDtlModel.setStrDebtorCode(objJVDebtorDtlModel.getStrDebtorCode());
			objAuditDebtorDtlModel.setStrInvoiceNo(objJVDebtorDtlModel.getStrInvoiceNo());
			objAuditDebtorDtlModel.setStrNarration(objJVDebtorDtlModel.getStrNarration());
			objAuditDebtorDtlModel.setStrGuest(objJVDebtorDtlModel.getStrGuest());
			objAuditDebtorDtlModel.setStrAccountCode(objJVDebtorDtlModel.getStrAccountCode());
			objAuditDebtorDtlModel.setStrCreditNo(objJVDebtorDtlModel.getStrCreditNo());
			objAuditDebtorDtlModel.setDteBillDate(objJVDebtorDtlModel.getDteBillDate());
			objAuditDebtorDtlModel.setDteInvoiceDate(objJVDebtorDtlModel.getDteInvoiceDate());
			objAuditDebtorDtlModel.setDteDueDate(objJVDebtorDtlModel.getDteDueDate());
			objAuditDebtorDtlModel.setStrPropertyCode(objJVDebtorDtlModel.getStrPropertyCode());
			objAuditDebtorDtlModel.setStrPOSCode(objJVDebtorDtlModel.getStrPOSCode());
			objAuditDebtorDtlModel.setStrPOSName(objJVDebtorDtlModel.getStrPOSName());
			objAuditDebtorDtlModel.setStrRegistrationNo(objJVDebtorDtlModel.getStrRegistrationNo());
			listAuditDebtorDtlModel.add(objAuditDebtorDtlModel);
		}
		objAuditHdModel.setListAuditDtlModel(listAuditDtlModel);
		objAuditHdModel.setListAuditDebtorDtlModel(listAuditDebtorDtlModel);
		objDeleteTransactionService.funAddUpdateAuditHd(objAuditHdModel);

		return 1;
	}

	private int funAddReceiptRecordToAudit(String transCode, String clientCode, String propertyCode, String formName) {
		clsReceiptHdModel objReceiptModel = objReceiptService.funGetReceiptList(transCode, clientCode, propertyCode);

		clsWebBooksAuditHdModel objAuditHdModel = new clsWebBooksAuditHdModel();
		objAuditHdModel.setStrTransNo(objReceiptModel.getStrVouchNo());
		objAuditHdModel.setStrAccCode(objReceiptModel.getStrCFCode());
		objAuditHdModel.setStrType(objReceiptModel.getStrType());
		objAuditHdModel.setStrDebtorCode(objReceiptModel.getStrDebtorCode());
		objAuditHdModel.setStrChequeNo(objReceiptModel.getStrChequeNo());
		objAuditHdModel.setStrDrawnOn(objReceiptModel.getStrDrawnOn());
		objAuditHdModel.setStrBranch(objReceiptModel.getStrBranch());
		objAuditHdModel.setStrNarration(objReceiptModel.getStrNarration());
		objAuditHdModel.setStrSancCode(objReceiptModel.getStrSancCode());
		objAuditHdModel.setDblAmt(objReceiptModel.getDblAmt());
		objAuditHdModel.setStrCrDr(objReceiptModel.getStrCrDr());
		objAuditHdModel.setDteVouchDate(objReceiptModel.getDteVouchDate());
		objAuditHdModel.setIntVouchMonth(objReceiptModel.getIntVouchMonth());
		objAuditHdModel.setDteChequeDate(objReceiptModel.getDteChequeDate());
		objAuditHdModel.setDteClearence(objReceiptModel.getDteClearence());
		objAuditHdModel.setStrTransMode(objReceiptModel.getStrTransMode());
		objAuditHdModel.setStrUserCreated(objReceiptModel.getStrUserCreated());
		objAuditHdModel.setStrUserEdited(objReceiptModel.getStrUserEdited());
		objAuditHdModel.setDteDateCreated(objReceiptModel.getDteDateCreated());
		objAuditHdModel.setDteDateEdited(objReceiptModel.getDteDateEdited());
		objAuditHdModel.setStrReceiptType(objReceiptModel.getStrReceiptType());
		objAuditHdModel.setStrModuleType(objReceiptModel.getStrModuleType());
		objAuditHdModel.setStrClientCode(objReceiptModel.getStrClientCode());
		objAuditHdModel.setStrPropertyCode(objReceiptModel.getStrPropertyCode());
		objAuditHdModel.setStrReceivedFrom(objReceiptModel.getStrReceivedFrom());
		objAuditHdModel.setIntOnHold(objReceiptModel.getIntOnHold());
		objAuditHdModel.setStrMasterPOS("");
		objAuditHdModel.setStrTransType(formName);
		objAuditHdModel.setIntVouchNum(0);

		List<clsWebBooksAuditDtlModel> listAuditDtlModel = new ArrayList<clsWebBooksAuditDtlModel>();
		List<clsReceiptDtlModel> listRecDtlModel = objReceiptModel.getListReceiptDtlModel();
		for (clsReceiptDtlModel objRecDtlModel : listRecDtlModel) {
			clsWebBooksAuditDtlModel objAuditDtlModel = new clsWebBooksAuditDtlModel();
			objAuditDtlModel.setStrAccountCode(objRecDtlModel.getStrAccountCode());
			objAuditDtlModel.setStrAccountName(objRecDtlModel.getStrAccountName());
			objAuditDtlModel.setStrCrDr(objRecDtlModel.getStrCrDr());
			objAuditDtlModel.setStrNarration(objRecDtlModel.getStrNarration());
			objAuditDtlModel.setStrPropertyCode(objRecDtlModel.getStrPropertyCode());
			objAuditDtlModel.setDblCrAmt(objRecDtlModel.getDblCrAmt());
			objAuditDtlModel.setDblDrAmt(objRecDtlModel.getDblDrAmt());
			objAuditDtlModel.setStrOneLine("");
			listAuditDtlModel.add(objAuditDtlModel);
		}

		List<clsWebBooksAuditDebtorDtlModel> listAuditDebtorDtlModel = new ArrayList<clsWebBooksAuditDebtorDtlModel>();
		List<clsReceiptDebtorDtlModel> listRecDebtorDtlModel = objReceiptModel.getListReceiptDebtorDtlModel();
		for (clsReceiptDebtorDtlModel objRecDebtorDtlModel : listRecDebtorDtlModel) {
			clsWebBooksAuditDebtorDtlModel objAuditDebtorDtlModel = new clsWebBooksAuditDebtorDtlModel();
			objAuditDebtorDtlModel.setStrDebtorCode(objRecDebtorDtlModel.getStrDebtorCode());
			objAuditDebtorDtlModel.setStrDebtorName(objRecDebtorDtlModel.getStrDebtorName());
			objAuditDebtorDtlModel.setStrCrDr(objRecDebtorDtlModel.getStrCrDr());
			objAuditDebtorDtlModel.setDblAmt(objRecDebtorDtlModel.getDblAmt());
			objAuditDebtorDtlModel.setStrBillNo(objRecDebtorDtlModel.getStrBillNo());
			objAuditDebtorDtlModel.setStrDebtorCode(objRecDebtorDtlModel.getStrDebtorCode());
			objAuditDebtorDtlModel.setStrInvoiceNo(objRecDebtorDtlModel.getStrInvoiceNo());
			objAuditDebtorDtlModel.setStrNarration(objRecDebtorDtlModel.getStrNarration());
			objAuditDebtorDtlModel.setStrGuest(objRecDebtorDtlModel.getStrGuest());
			objAuditDebtorDtlModel.setStrAccountCode(objRecDebtorDtlModel.getStrAccountCode());
			objAuditDebtorDtlModel.setStrCreditNo(objRecDebtorDtlModel.getStrCreditNo());
			objAuditDebtorDtlModel.setDteBillDate(objRecDebtorDtlModel.getDteBillDate());
			objAuditDebtorDtlModel.setDteInvoiceDate(objRecDebtorDtlModel.getDteInvoiceDate());
			objAuditDebtorDtlModel.setDteDueDate(objRecDebtorDtlModel.getDteDueDate());
			objAuditDebtorDtlModel.setStrPropertyCode(objRecDebtorDtlModel.getStrPropertyCode());
			objAuditDebtorDtlModel.setStrPOSCode("");
			objAuditDebtorDtlModel.setStrPOSName("");
			objAuditDebtorDtlModel.setStrRegistrationNo("");
			listAuditDebtorDtlModel.add(objAuditDebtorDtlModel);
		}
		objAuditHdModel.setListAuditDtlModel(listAuditDtlModel);
		objAuditHdModel.setListAuditDebtorDtlModel(listAuditDebtorDtlModel);
		objDeleteTransactionService.funAddUpdateAuditHd(objAuditHdModel);

		return 1;
	}

	private int funAddPaymentRecordToAudit(String transCode, String clientCode, String propertyCode, String formName) {
		clsPaymentHdModel objPaymentModel = objPaymentService.funGetPaymentList(transCode, clientCode, propertyCode);

		clsWebBooksAuditHdModel objAuditHdModel = new clsWebBooksAuditHdModel();
		objAuditHdModel.setStrTransNo(objPaymentModel.getStrVouchNo());
		objAuditHdModel.setStrAccCode(objPaymentModel.getStrBankCode());
		objAuditHdModel.setStrNarration(objPaymentModel.getStrNarration());
		objAuditHdModel.setStrSancCode(objPaymentModel.getStrSancCode());
		objAuditHdModel.setStrChequeNo(objPaymentModel.getStrChequeNo());
		objAuditHdModel.setDblAmt(objPaymentModel.getDblAmt());
		objAuditHdModel.setStrCrDr(objPaymentModel.getStrCrDr());
		objAuditHdModel.setDteVouchDate(objPaymentModel.getDteVouchDate());
		objAuditHdModel.setIntVouchMonth(objPaymentModel.getIntVouchMonth());
		objAuditHdModel.setDteChequeDate(objPaymentModel.getDteChequeDate());
		objAuditHdModel.setStrTransMode(objPaymentModel.getStrTransMode());
		objAuditHdModel.setStrUserCreated(objPaymentModel.getStrUserCreated());
		objAuditHdModel.setStrUserEdited(objPaymentModel.getStrUserEdited());
		objAuditHdModel.setDteDateCreated(objPaymentModel.getDteDateCreated());
		objAuditHdModel.setDteDateEdited(objPaymentModel.getDteDateEdited());
		objAuditHdModel.setStrModuleType(objPaymentModel.getStrModuleType());
		objAuditHdModel.setDteClearence(objPaymentModel.getDteClearence());
		objAuditHdModel.setStrType(objPaymentModel.getStrType());
		objAuditHdModel.setStrDrawnOn(objPaymentModel.getStrDrawnOn());
		objAuditHdModel.setStrBranch(objPaymentModel.getStrBranch());
		objAuditHdModel.setStrClientCode(objPaymentModel.getStrClientCode());
		objAuditHdModel.setStrPropertyCode(objPaymentModel.getStrPropertyCode());
		objAuditHdModel.setStrReceivedFrom("");
		objAuditHdModel.setIntOnHold(0);
		objAuditHdModel.setStrReceiptType("");
		objAuditHdModel.setStrDebtorCode("");
		objAuditHdModel.setStrMasterPOS("");
		objAuditHdModel.setStrTransType(formName);
		objAuditHdModel.setIntVouchNum(0);

		List<clsWebBooksAuditDtlModel> listAuditDtlModel = new ArrayList<clsWebBooksAuditDtlModel>();
		List<clsPaymentDtl> listPaymentDtlModel = objPaymentModel.getListPaymentDtlModel();
		for (clsPaymentDtl objPaymentDtlModel : listPaymentDtlModel) {
			clsWebBooksAuditDtlModel objAuditDtlModel = new clsWebBooksAuditDtlModel();
			objAuditDtlModel.setStrAccountCode(objPaymentDtlModel.getStrAccountCode());
			objAuditDtlModel.setStrAccountName(objPaymentDtlModel.getStrAccountName());
			objAuditDtlModel.setStrCrDr(objPaymentDtlModel.getStrCrDr());
			objAuditDtlModel.setStrNarration(objPaymentDtlModel.getStrNarration());
			objAuditDtlModel.setStrPropertyCode(objPaymentDtlModel.getStrPropertyCode());
			objAuditDtlModel.setDblCrAmt(objPaymentDtlModel.getDblCrAmt());
			objAuditDtlModel.setDblDrAmt(objPaymentDtlModel.getDblDrAmt());
			objAuditDtlModel.setStrOneLine("");
			listAuditDtlModel.add(objAuditDtlModel);
		}

		List<clsWebBooksAuditDebtorDtlModel> listAuditDebtorDtlModel = new ArrayList<clsWebBooksAuditDebtorDtlModel>();
		List<clsPaymentDebtorDtlModel> listPaymentDebtorDtlModel = objPaymentModel.getListPaymentDebtorDtlModel();
		for (clsPaymentDebtorDtlModel objPaymentDebtorDtlModel : listPaymentDebtorDtlModel) {
			clsWebBooksAuditDebtorDtlModel objAuditDebtorDtlModel = new clsWebBooksAuditDebtorDtlModel();
			objAuditDebtorDtlModel.setStrDebtorCode(objPaymentDebtorDtlModel.getStrDebtorCode());
			objAuditDebtorDtlModel.setStrDebtorName(objPaymentDebtorDtlModel.getStrDebtorName());
			objAuditDebtorDtlModel.setStrCrDr(objPaymentDebtorDtlModel.getStrCrDr());
			objAuditDebtorDtlModel.setDblAmt(objPaymentDebtorDtlModel.getDblAmt());
			objAuditDebtorDtlModel.setStrBillNo(objPaymentDebtorDtlModel.getStrBillNo());
			objAuditDebtorDtlModel.setStrDebtorCode(objPaymentDebtorDtlModel.getStrDebtorCode());
			objAuditDebtorDtlModel.setStrInvoiceNo(objPaymentDebtorDtlModel.getStrInvoiceNo());
			objAuditDebtorDtlModel.setStrNarration(objPaymentDebtorDtlModel.getStrNarration());
			objAuditDebtorDtlModel.setStrGuest(objPaymentDebtorDtlModel.getStrGuest());
			objAuditDebtorDtlModel.setStrAccountCode(objPaymentDebtorDtlModel.getStrAccountCode());
			objAuditDebtorDtlModel.setStrCreditNo(objPaymentDebtorDtlModel.getStrCreditNo());
			objAuditDebtorDtlModel.setDteBillDate(objPaymentDebtorDtlModel.getDteBillDate());
			objAuditDebtorDtlModel.setDteInvoiceDate(objPaymentDebtorDtlModel.getDteInvoiceDate());
			objAuditDebtorDtlModel.setDteDueDate(objPaymentDebtorDtlModel.getDteDueDate());
			objAuditDebtorDtlModel.setStrPropertyCode(objPaymentDebtorDtlModel.getStrPropertyCode());
			objAuditDebtorDtlModel.setStrPOSCode("");
			objAuditDebtorDtlModel.setStrPOSName("");
			objAuditDebtorDtlModel.setStrRegistrationNo("");
			listAuditDebtorDtlModel.add(objAuditDebtorDtlModel);
		}
		objAuditHdModel.setListAuditDtlModel(listAuditDtlModel);
		objAuditHdModel.setListAuditDebtorDtlModel(listAuditDebtorDtlModel);
		objDeleteTransactionService.funAddUpdateAuditHd(objAuditHdModel);

		return 1;
	}

	@RequestMapping(value = "/loadWebStockReasonMasterData", method = RequestMethod.GET)
	public @ResponseBody clsReasonMaster funAssignFields(@RequestParam("reasonCode") String code, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
		String sql = "select strReasonCode,strReasonName from "+webStockDB+".tblreasonmaster " + "where strClientCode ='" + clientCode + "' and  strReasonCode='" + code + "' ";

		List list = objGlobalFunctionsService.funGetList(sql, "sql");

		clsReasonMaster objModel = new clsReasonMaster();
		if (list.size() > 0) {
			Object[] ob = (Object[]) list.get(0);
			objModel.setStrReasonCode(ob[0].toString());
			objModel.setStrReasonName(ob[1].toString());
		}
		return objModel;
	}


}
