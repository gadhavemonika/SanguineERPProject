package com.sanguine.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.base.service.intfBaseService;
import com.sanguine.bean.clsDeleteTransactionBean;
import com.sanguine.crm.controller.clsInvoiceController;
import com.sanguine.crm.controller.clsSalesOrderController;
import com.sanguine.crm.controller.clsSalesReturnController;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsDeleteTransModel;
import com.sanguine.model.clsProductionHdModel;
import com.sanguine.model.clsProductionOrderHdModel;
import com.sanguine.model.clsStkPostingHdModel;
import com.sanguine.model.clsStkTransferHdModel;
import com.sanguine.model.clsWorkOrderHdModel;
import com.sanguine.service.clsDelTransService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsProductionOrderService;
import com.sanguine.service.clsProductionService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsStkAdjustmentService;
import com.sanguine.service.clsStkPostingService;
import com.sanguine.service.clsStkTransferService;
import com.sanguine.service.clsWorkOrderService;

@Controller
public class clsDeleteTransController {
	@Autowired
	clsDelTransService objDelTransService;
	@Autowired
	clsGlobalFunctionsService objGlobalFunService;

	@Autowired
	clsMISController objMIS;

	@Autowired
	clsGRNController objGRN;

	@Autowired
	clsMaterialReturnController objMatReturn;

	@Autowired
	clsStkAdjustmentController objStkAdj;

	@Autowired
	clsMaterialReqController objMatReq;

	@Autowired
	clsStkTransferController objStkTransfer;

	@Autowired
	clsProductionController objProduction;

	@Autowired
	clsPurchaseReturnController objPurRet;

	@Autowired
	clsStockController objOpStk;

	@Autowired
	clsPurchaseOrderController objPurOrder;

	@Autowired
	clsStkPostingController objPhyStk;

	@Autowired
	clsPurchaseIndentHdController objPurchaseIndent;

	@Autowired
	clsBillPassingController objBillPassing;

	@Autowired
	clsGlobalFunctions objGlobal;

	@Autowired
	private clsStkPostingService objStkPostService;

	@Autowired
	private clsProductionService objPDService;

	@Autowired
	private clsWorkOrderService objWorkOrderService;

	@Autowired
	clsProductionOrderService objProductionOrderService;

	@Autowired
	private clsStkTransferService objStkTransService;

	@Autowired
	private clsStkAdjustmentService objStkAdjService;

	@Autowired
	private clsInvoiceController objInvController;

	@Autowired
	private clsProductionOrderController objProductionOrderController;

	@Autowired
	private clsWorkOrderController objWorkOrderController;

	@Autowired
	private clsProductionController onjProductionController;
	
	@Autowired 
	private intfBaseService objBaseService;
	
	@Autowired
	private clsSalesReturnController objSalesReturnController;
	

	@Autowired
	private clsSalesOrderController objSalesOrderController;
	
	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/frmDeleteTransaction", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") clsDeleteTransactionBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest req) {
		String strModule = "1";
		Map<String, String> mapTransForms = new HashMap<String, String>();
		String sql = "select strFormName,strFormDesc from clsTreeMasterModel " + "where strType='T' and strModule='" + strModule + "' " + "order by strFormName";
		List list = objGlobalFunService.funGetList(sql, "hql");
		for (int cnt = 0; cnt < list.size(); cnt++) {
			Object[] arrObj = (Object[]) list.get(cnt);
			mapTransForms.put(arrObj[0].toString(), arrObj[1].toString());
		}
		mapTransForms.put("frmInvoice", "Invoice");
		mapTransForms.put("frmSalesReturn", "Sales Return");
		mapTransForms.put("frmSalesOrder", "Sales Order");
		
		mapTransForms.remove("frmExciseOpeningStock");
		mapTransForms.remove("frmStockAdjustment");
		
		ModelAndView objModelAndView = new ModelAndView();
		objModelAndView.addObject("listFormName", mapTransForms);
		return objModelAndView;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/deleteTransaction", method = RequestMethod.POST)
	public ModelAndView funDeleteTransaction(@ModelAttribute("command") clsDeleteTransactionBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest req) throws Exception {
		objGlobal = new clsGlobalFunctions();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		Map<String, String> mapTransForms = new HashMap<String, String>();
		String res="";

		String sql = "select strFormName,strFormDesc from clsTreeMasterModel " + "where strType='T' " + "order by strFormName";

		List list = objGlobalFunService.funGetList(sql, "hql");
		for (int cnt = 0; cnt < list.size(); cnt++) {
			Object[] arrObj = (Object[]) list.get(cnt);
			mapTransForms.put(arrObj[0].toString(), arrObj[1].toString());
		}
		mapTransForms.put("frmInvoice", "Invoice");
		mapTransForms.put("frmSalesReturn", "Sales Return");
		mapTransForms.put("frmSalesOrder", "Sales Order");
		String formName = mapTransForms.get(objBean.getStrFormName());
		clsDeleteTransModel objModel = new clsDeleteTransModel();
		objModel.setStrFormName(formName);
		objModel.setStrTransCode(objBean.getStrTransCode());
		objModel.setStrReasonCode(objGlobal.funIfNull(objBean.getStrReasonCode(), "NA", objBean.getStrReasonCode()));
		objModel.setStrNarration(objGlobal.funIfNull(objBean.getStrNarration(), "", objBean.getStrNarration()));
		objModel.setDtDeleteDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserCode(userCode);
		objModel.setStrClientCode(clientCode);
		objModel.setStrDataPostFlag("N");
		clsStkPostingHdModel objPhyModel = objStkPostService.funGetModelObject(objBean.getStrTransCode(), clientCode);

		String sql_UpdateHd = "update ";
		String sql_DeleteDtl = "delete from ";
		clsDeleteTransModel objDeleteModelForSA = new clsDeleteTransModel();
		if (objPhyModel != null) {
			objDeleteModelForSA.setStrFormName(formName);
			objDeleteModelForSA.setStrTransCode(objBean.getStrTransCode());
			objDeleteModelForSA.setStrReasonCode(objGlobal.funIfNull(objBean.getStrReasonCode(), "NA", objBean.getStrReasonCode()));
			objDeleteModelForSA.setStrNarration(objGlobal.funIfNull(objBean.getStrNarration(), "", objBean.getStrNarration()));
			objDeleteModelForSA.setDtDeleteDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			objDeleteModelForSA.setStrUserCode(userCode);
			objDeleteModelForSA.setStrClientCode(clientCode);
			objDeleteModelForSA.setStrDataPostFlag("N");
		}

		String sql_StckAdjDeleteHd = "delete from ";
		String sql_StckAdjDeleteDtl = "delete from ";

		boolean flgTrans = false,isWebBookModuleActive=false;
		String queryType = "hql";
		String type = "Deleted";
		String narration="Entry deleted By "+userCode+" On "+objGlobal.funGetCurrentDateTime("yyyy-MM-dd");
		StringBuilder sbSql=new StringBuilder();
		
		clsCompanyMasterModel objCompModel = objSetupMasterService.funGetObject(clientCode);
		if(objCompModel.getStrWebBookModule().equalsIgnoreCase("Yes")){
			isWebBookModuleActive=true;
		}
		List listData;
		List listPay=null;
		switch (formName) {
		case "GRN(Good Receiving Note)":
			flgTrans = true;
			sbSql.setLength(0);
			
			
			sbSql.append("select strVouchNo from tblpaymentgrndtl where strGRNCode='"+objBean.getStrTransCode() +"' ");
			if(isWebBookModuleActive){
				listPay= objBaseService.funGetListModuleWise(sbSql ,"sql",  "WebBooks");	
			}
			
			if(listPay!=null && listPay.size()>0){
				res="Transaction is present";
			}else{
				objGRN.funSaveAudit(objBean.getStrTransCode(), type, req);
				sql_UpdateHd += " clsGRNHdModel set dblSubTotal=0,dblDisRate=0,dblDisAmt=0,dblTaxAmt=0,dblExtra=0,dblTotal=0,"
						+ " dblLessAmt=0,dblValueTotal=0,dblRoundOff=0,dblFOB=0,dblFreight=0,dblInsurance=0,"
						+ " dblOtherCharges=0,dblVATClaim=0,dblClearingCharges=0,strNarration='"+narration+"',strPONo=''  where strGRNCode=";
			
				sql_DeleteDtl += " clsGRNDtlModel where strGRNCode=";
				
				objDelTransService.funDeleteRecord("delete from clsGRNTaxDtlModel where strGRNCode='" + objBean.getStrTransCode() + "'  and strClientCode='" + clientCode + "'", "hql");
				sbSql.setLength(0);
				sbSql.append("select strPRCode from clsPurchaseReturnHdModel where strGRNCode='"+objBean.getStrTransCode()+"' ");
				
				 listData= objBaseService.funGetListModuleWise(sbSql ,"hql",  "WebStocks");
				if(null!=listData && listData.size()>0)
				{
					objPurRet.funSaveAudit(listData.get(0).toString(), type, req);
					sbSql.setLength(0);
					sbSql.append("update clsPurchaseReturnHdModel set dblDisAmt=0,dblDisRate=0,dblExtra=0,dblSubTotal=0,dblTaxAmt=0,dblTotal=0,strNarration='"+narration+"',strGRNCode='' "
							+ " where strPRCode='" + listData.get(0).toString() + "'  and strClientCode='" + clientCode + "'");
					objDelTransService.funDeleteRecord(sbSql.toString(), "hql");
					objDelTransService.funDeleteRecord("delete from clsPurchaseReturnTaxDtlModel where strPRCode='" + listData.get(0).toString() + "'  and strClientCode='" + clientCode + "'", "hql");
					objDelTransService.funDeleteRecord("delete from clsPurchaseReturnDtlModel where strPRCode='" + listData.get(0).toString() + "'  and strClientCode='" + clientCode + "'", "hql");
				}
				
				queryType = "hql";
				if(isWebBookModuleActive){
					funDeleteJV(formName,objBean.getStrTransCode(),userCode);	
				}
				
			}
						break;

		case "Opening Stock":
			flgTrans = true;
			objOpStk.funSaveAudit(objBean.getStrTransCode(), type, req);
			sql_UpdateHd += " clsInitialInventoryModel where strOpStkCode=";
			sql_DeleteDtl += "clsOpeningStkDtl where strOpStkCode=";
			queryType = "hql";
			break;

		case "Physical Stk Posting":
			flgTrans = true;
			if (objPhyModel != null) {
				objStkAdj.funSaveAudit(objPhyModel.getStrSACode(), type, req);
				sql_StckAdjDeleteHd += "clsStkAdjustmentHdModel where strSACode=";
				sql_StckAdjDeleteDtl += "clsStkAdjustmentDtlModel where strSACode=";
			}
			objPhyStk.funSaveAudit(objBean.getStrTransCode(), type, req);
			sql_UpdateHd += "clsStkPostingHdModel where strPSCode=";
			sql_DeleteDtl += "clsStkPostingDtlModel where strPSCode=";
			queryType = "hql";
			break;

		case "Production Order":
			flgTrans = true;
			objProductionOrderController.funSaveAudit(objBean.getStrTransCode(), type, req);
			sql_UpdateHd += "clsProductionOrderHdModel where strOPCode=";
			sql_DeleteDtl += "clsProductionOrderDtlModel where strOPCode=";
			queryType = "hql";
			break;

		case "Purchase Indent":
			flgTrans = true;
			objPurchaseIndent.funSaveAudit(objBean.getStrTransCode(), type, req);
			sql_UpdateHd += "tblpurchaseindendhd set dblTotal=0,strNarration='"+narration+"' where strPICode=";
			sql_DeleteDtl += "tblpurchaseindenddtl where strPICode=";
			queryType = "sql";
			break;

		case "Purchase Order":
			flgTrans = true;
			
			sbSql.setLength(0);
			sbSql.append("select strGRNCode from clsGRNHdModel where strPONo='"+objBean.getStrTransCode()+"' ");
			listData= objBaseService.funGetListModuleWise(sbSql ,"hql",  "WebStocks");
			String grnCode="";
			if(null!=listData && listData.size()>0)
			{
				 grnCode=listData.get(0).toString();
			}
			
			sbSql.setLength(0);
			sbSql.append("select strVouchNo from tblpaymentgrndtl where strGRNCode='"+grnCode +"' ");
			if(isWebBookModuleActive){
				listPay= objBaseService.funGetListModuleWise(sbSql ,"sql",  "WebBooks");	
			}
			if(listPay!=null && listPay.size()>0){
				res="Transaction is present";
			}else{
				objPurOrder.funSaveAudit(objBean.getStrTransCode(), type, req);
				sql_UpdateHd += " clsPurchaseOrderHdModel set dblTotal=0,dblExtra=0,dblFinalAmt=0,dblDiscount=0,dblTaxAmt=0,dblFOB=0,dblFreight=0,dblInsurance=0,dblOtherCharges=0,"
						+ " dblCIF=0,dblClearingAgentCharges=0,dblVATClaim=0,dblExchangeRate=0 where strPOCode=";
				sql_DeleteDtl += "clsPurchaseOrderDtlModel where strPOCode=";
				 if(grnCode!=""){
					 objGRN.funSaveAudit(grnCode, type, req);
						sql_UpdateHd += " clsGRNHdModel set dblSubTotal=0,dblDisRate=0,dblDisAmt=0,dblTaxAmt=0,dblExtra=0,dblTotal=0,"
								+ " dblLessAmt=0,dblValueTotal=0,dblRoundOff=0,dblFOB=0,dblFreight=0,dblInsurance=0,"
								+ " dblOtherCharges=0,dblVATClaim=0,dblClearingCharges=0,strNarration='"+narration+"',strPONo='' where strGRNCode='"+grnCode+ "' and strClientCode='" + clientCode + "' ";
					
						sql_DeleteDtl += " clsGRNDtlModel where strGRNCode=";
						
						objDelTransService.funDeleteRecord("delete from clsGRNTaxDtlModel where strGRNCode='" + grnCode + "'  and strClientCode='" + clientCode + "'", "hql");
						 sbSql=new StringBuilder();
						sbSql.append("select strPRCode from clsPurchaseReturnHdModel where strGRNCode='"+grnCode+"' ");
						
						 listData= objBaseService.funGetListModuleWise(sbSql ,"hql",  "WebStocks");
						if(null!=listData && listData.size()>0)
						{
							objPurRet.funSaveAudit(listData.get(0).toString(), type, req);
							sbSql.setLength(0);
							sbSql.append("update clsPurchaseReturnHdModel set dblDisAmt=0,dblDisRate=0,dblExtra=0,dblSubTotal=0,dblTaxAmt=0,dblTotal=0,strNarration='"+narration+"' "
									+ " where strPRCode='" + listData.get(0).toString() + "'  and strClientCode='" + clientCode + "'");
							objDelTransService.funDeleteRecord(sbSql.toString(), "hql");
							objDelTransService.funDeleteRecord("delete from clsPurchaseReturnTaxDtlModel where strPRCode='" + listData.get(0).toString() + "'  and strClientCode='" + clientCode + "'", "hql");
							objDelTransService.funDeleteRecord("delete from clsPurchaseReturnDtlModel where strPRCode='" + listData.get(0).toString() + "'  and strClientCode='" + clientCode + "'", "hql");
						}
						if(isWebBookModuleActive){
							funDeleteJV(formName,objBean.getStrTransCode(),userCode);	
						}
				 }
			}
			queryType = "hql";
			break;

		case "Purchase Return":
			flgTrans = true;
			objPurRet.funSaveAudit(objBean.getStrTransCode(), type, req);
			sql_UpdateHd += " clsPurchaseReturnHdModel set dblDisAmt=0,dblDisRate=0,dblExtra=0,dblSubTotal=0,dblTaxAmt=0,dblTotal=0,strNarration='"+narration+"'  where strPRCode=";
			sql_DeleteDtl += " clsPurchaseReturnDtlModel where strPRCode=";
			objDelTransService.funDeleteRecord(" delete from clsPurchaseReturnTaxDtlModel where strPRCode='" + objBean.getStrTransCode() + "'  and strClientCode='" + clientCode + "'", "hql");
			queryType = "hql";
			if(isWebBookModuleActive){
				funDeleteJV(formName,objBean.getStrTransCode(),userCode);	
			}
			
			break;

		case "Stock Adjustment":
			flgTrans = true;
			objStkAdj.funSaveAudit(objBean.getStrTransCode(), type, req);
			sql_UpdateHd += "clsStkAdjustmentHdModel where strSACode=";
			sql_DeleteDtl += "clsStkAdjustmentDtlModel where strSACode=";
			queryType = "hql";
			break;

		case "Stock Transfer":
			flgTrans = true;
			objStkTransfer.funSaveAudit(objBean.getStrTransCode(), type, req);
			sql_UpdateHd += "clsStkTransferHdModel set dblTotalAmt=0,strNarration='"+narration+"' where strSTCode=";
			sql_DeleteDtl += "clsStkTransferDtlModel where strSTCode=";
			queryType = "hql";
			break;

		case "Work Order":
			flgTrans = true;
			objWorkOrderController.funSaveAudit(objBean.getStrTransCode(), type, req);
			sql_UpdateHd += "clsWorkOrderHdModel where strWOCode=";
			sql_DeleteDtl += "clsWorkOrderDtlModel where strWOCode=";
			queryType = "hql";
			break;

		case "Material Return":
			flgTrans = true;
			objMatReturn.funSaveAudit(objBean.getStrTransCode(), type, req);
			sql_UpdateHd += "clsMaterialReturnHdModel set strNarration='"+narration+"' where strMRetCode=";
			sql_DeleteDtl += "clsMaterialReturnDtlModel where strMRetCode=";
			queryType = "hql";
			break;

		case "Material Issue Slip":
			flgTrans = true;
			objMIS.funSaveAudit(objBean.getStrTransCode(), type, req);
			objDelTransService.funDeleteRecord(" update tblmishd set dblTotalAmt=0,dblReqQty=0,strNarration='"+narration+"' where strMISCode='"+objBean.getStrTransCode()+"' "
					+ " and  strClientCode='" + clientCode + "' ","sql");
			objDelTransService.funDeleteRecord("delete from tblmisdtl where strMISCode=" + objBean.getStrTransCode() + "'  and strClientCode='" + clientCode + "'", "sql");
			queryType = "sql";
			break;

		case "Material Requisition":
			flgTrans = true;
			objMatReq.funSaveAudit(objBean.getStrTransCode(), type, req);
			objDelTransService.funDeleteRecord(" update tblreqhd set dblSubTotal=0,strNarration='"+narration+"' where strReqCode='"+objBean.getStrTransCode()+"' "
					+ " and  strClientCode='" + clientCode + "' ","sql");
			objDelTransService.funDeleteRecord("delete from tblreqdtl where strReqCode=" + objBean.getStrTransCode() + "'  and strClientCode='" + clientCode + "'", "sql");
		
			queryType = "sql";
			break;

		case "Material Production":
			// flgTrans=true;
			onjProductionController.funSaveAudit(objBean.getStrTransCode(), type, req);
			clsProductionHdModel objPDModel = objPDService.funGetObject(objBean.getStrTransCode(), clientCode);
			String pdCode = objBean.getStrTransCode();
			objDelTransService.funDeleteRecord("delete from clsProductionHdModel where strPDCode='" + pdCode + "'  and strClientCode='" + clientCode + "'", "hql");
			objDelTransService.funDeleteRecord("delete from clsProductionDtlModel where strPDCode='" + pdCode + "'  and strClientCode='" + clientCode + "'", "hql");
			String woCode = objPDModel.getStrWOCode();
			clsWorkOrderHdModel objWO = objWorkOrderService.funGetWOHd(woCode, clientCode);
			if (objWO.getStrWOCode() != null) {
				List woList = objWorkOrderService.funGetGeneratedWOAgainstOPData(objWO.getStrSOCode(), clientCode);
				if (woList.size() == 1) {
					objWorkOrderController.funSaveAudit(woCode, type, req);
					objDelTransService.funDeleteRecord("delete from clsWorkOrderHdModel where strWOCode='" + woCode + "'  and strClientCode='" + clientCode + "'", "hql");
					objDelTransService.funDeleteRecord("delete from tblworkorderdtl where strWOCode='" + woCode + "'  and strClientCode='" + clientCode + "'", "sql");

					clsProductionOrderHdModel objPOModel = objProductionOrderService.funGetObject(objWO.getStrSOCode(), clientCode);
					objProductionOrderController.funSaveAudit(objWO.getStrSOCode(), type, req);
					objDelTransService.funDeleteRecord("delete from clsProductionOrderHdModel where strOPCode='" + objPOModel.getStrOPCode() + "'  and strClientCode='" + clientCode + "'", "hql");
					objDelTransService.funDeleteRecord("delete from clsProductionOrderDtlModel where strOPCode='" + objPOModel.getStrOPCode() + "'  and strClientCode='" + clientCode + "'", "hql");

					clsStkTransferHdModel objSTModel = objStkTransService.funGetModel(pdCode, clientCode);
					if (objSTModel.getStrSTCode() != null) {
						objStkTransfer.funSaveAudit(objSTModel.getStrSTCode(), type, req);
						objDelTransService.funDeleteRecord("delete from clsStkTransferHdModel where strSTCode='" + objSTModel.getStrSTCode() + "'  and strClientCode='" + clientCode + "'", "hql");
						objDelTransService.funDeleteRecord("delete from clsStkTransferDtlModel where strSTCode='" + objSTModel.getStrSTCode() + "'  and strClientCode='" + clientCode + "'", "hql");
					}

				}

			}

			break;

		case "Invoice":

			sbSql.setLength(0);
			sbSql.append("select strVouchNo from tblreceiptinvdtl where strInvCode='"+objBean.getStrTransCode()+"' ");
			List listRec=null;
			if(isWebBookModuleActive){
				listRec= objBaseService.funGetListModuleWise(sbSql ,"sql",  "WebBooks");	
			}
			if(null!=listRec && listRec.size()>0)
			{
				res="Transaction is present";
			}else{
				objInvController.funSaveAudit(objBean.getStrTransCode(), type, req);
				objDelTransService.funDeleteRecord("update tblinvoicehd a set a.dblTaxAmt=0,a.dblTotalAmt=0,a.dblSubTotalAmt=0 ,a.dblDiscount=0,a.dblDiscountAmt=0,a.dblGrandTotal=0,strNarration='"+narration+"',a.strSOCode='' where a.strInvCode='" + objBean.getStrTransCode() + "' and a.strClientCode='" + clientCode + "'", "sql");
				objDelTransService.funDeleteRecord("delete from tblinvprodtaxdtl where strInvCode='" + objBean.getStrTransCode() + "' and strClientCode='" + clientCode + "'", "sql");
				objDelTransService.funDeleteRecord("delete from tblinvsalesorderdtl where strInvCode='" + objBean.getStrTransCode() + "' and strClientCode='" + clientCode + "'", "sql");
				objDelTransService.funDeleteRecord("delete from tblinvsettlementdtl where strInvCode='" + objBean.getStrTransCode() + "' and strClientCode='" + clientCode + "'", "sql");
				objDelTransService.funDeleteRecord("delete from tblinvtaxdtl where strInvCode='" + objBean.getStrTransCode() + "' and strClientCode='" + clientCode + "'", "sql");
				objDelTransService.funDeleteRecord("delete from tblinvoicedtl where strInvCode='" + objBean.getStrTransCode() + "' and strClientCode='" + clientCode + "'", "sql");
				/*clsStkAdjustmentHdModel objStk = objStkAdjService.funGetDataModelByInvCode(objBean.getStrTransCode(), clientCode);
				if (objStk.getStrSACode() != null) {
					objStkAdj.funSaveAudit(objBean.getStrTransCode(), type, req);
					objDelTransService.funDeleteRecord("delete from tblinvoicehd where strInvCode='" + objBean.getStrTransCode() + "' and strClientCode='" + clientCode + "'", "sql");
					objDelTransService.funDeleteRecord("delete from tblinvoicehd where strInvCode='" + objBean.getStrTransCode() + "' and strClientCode='" + clientCode + "'", "sql");

				}*/

				StringBuilder sqlSalsRet=new StringBuilder();
				sqlSalsRet.append("select strSRCode from clsSalesReturnHdModel where strDCCode='"+objBean.getStrTransCode()+"' ");
				
				List listSalRet= objBaseService.funGetListModuleWise(sqlSalsRet ,"hql",  "WebStocks");
				if(null!=listSalRet && listSalRet.size()>0)
				{
					objDelTransService.funDeleteRecord("update tblsalesreturnhd a set a.dblTotalAmt=0 ,a.dblTaxAmt=0,a.dblDiscAmt=0,a.dblDiscPer=0,strDCCode=''  where a.strSRCode='" + listSalRet.get(0).toString() + "'  and a.strClientCode='" + clientCode + "'", "sql");
					objDelTransService.funDeleteRecord("delete from clsSalesReturnDtlModel where strSRCode='" + listSalRet.get(0).toString() + "'  and strClientCode='" + clientCode + "'", "hql");
					objDelTransService.funDeleteRecord("delete from clsSalesRetrunTaxModel where strSRCode='" + listSalRet.get(0).toString() + "'  and strClientCode='" + clientCode + "'", "hql");
					
				}
				if(isWebBookModuleActive){
					funDeleteJV(formName,objBean.getStrTransCode(),userCode);	
				}
				
			}
						
			break;


		case "Sales Return":
			flgTrans = true;
			objSalesReturnController.funSaveAudit(objBean.getStrTransCode(), type, req);
			sql_UpdateHd += " clsSalesReturnHdModel set dblDiscAmt=0,dblDiscPer=0,dblTotalAmt=0,dblTaxAmt=0 where strSRCode=";
			sql_DeleteDtl += " clsSalesReturnDtlModel where strSRCode=";
			objDelTransService.funDeleteRecord(" delete from tblsalesreturntaxdtl where strSRCode='" + objBean.getStrTransCode() + "'  and strClientCode='" + clientCode + "'", "sql");
			queryType = "hql";
			if(isWebBookModuleActive){
				funDeleteJV(formName,objBean.getStrTransCode(),userCode);	
			}
			
			break;
			
		
		case "Sales Order":
			String invCode="";
			sbSql.setLength(0);
			sbSql.append("select strInvCode from clsInvoiceHdModel where strSOCode='"+objBean.getStrTransCode()+"' ");
			listData= objBaseService.funGetListModuleWise(sbSql ,"hql",  "WebCRM");
			if(null!=listData && listData.size()>0)
			{
				invCode=listData.get(0).toString();
			}
			sbSql.setLength(0);
			sbSql.append("select strVouchNo from tblreceiptinvdtl where strInvCode='"+invCode+"' ");
			listRec=null;
			if(isWebBookModuleActive){
				listRec= objBaseService.funGetListModuleWise(sbSql ,"sql",  "WebBooks");	
			}
			
			if(null!=listRec && listRec.size()>0)
			{
				res="Transaction is present";
			}else{
				flgTrans = true;
				objSalesOrderController.funSaveAudit(objBean.getStrTransCode(), type, req);
				sql_UpdateHd += " clsSalesOrderHdModel set dblSubTotal=0,dblDisRate=0,dblDisAmt=0,dblTaxAmt=0,dblExtra=0,dblTotal=0,strNarration='"+narration+"' where strSOCode=";
				sql_DeleteDtl += "clsSalesOrderDtl where strSOCode=";
				 
				if(invCode!="")
				{
					
					objInvController.funSaveAudit(invCode, type, req);
					objDelTransService.funDeleteRecord("update tblinvoicehd a set a.dblTaxAmt=0,a.dblTotalAmt=0,a.dblSubTotalAmt=0 ,a.dblDiscount=0,a.dblDiscountAmt=0,a.dblGrandTotal=0,strNarration='"+narration+"',a.strSOCode='' where a.strInvCode='" + invCode + "' and a.strClientCode='" + clientCode + "'", "sql");
					objDelTransService.funDeleteRecord("delete from tblinvprodtaxdtl where strInvCode='" + invCode + "' and strClientCode='" + clientCode + "'", "sql");
					objDelTransService.funDeleteRecord("delete from tblinvsalesorderdtl where strInvCode='" + invCode + "' and strClientCode='" + clientCode + "'", "sql");
					objDelTransService.funDeleteRecord("delete from tblinvsettlementdtl where strInvCode='" + invCode + "' and strClientCode='" + clientCode + "'", "sql");
					objDelTransService.funDeleteRecord("delete from tblinvoicedtl where strInvCode='" + invCode + "' and strClientCode='" + clientCode + "'", "sql");

					StringBuilder sqlSalsRet1=new StringBuilder();
					sqlSalsRet1.append("select strSRCode from clsSalesReturnHdModel where strDCCode='"+invCode+"' ");
					
					List listSalRet1= objBaseService.funGetListModuleWise(sqlSalsRet1 ,"hql",  "WebStocks");
					if(null!=listSalRet1 && listSalRet1.size()>0)
					{
						objDelTransService.funDeleteRecord("update tblsalesreturnhd a set a.dblTotalAmt=0 ,a.dblTaxAmt=0,a.dblDiscAmt=0,a.dblDiscPer=0,strDCCode=''  where a.strSRCode='" + listSalRet1.get(0).toString() + "'  and a.strClientCode='" + clientCode + "'", "sql");
						objDelTransService.funDeleteRecord("delete from clsSalesReturnDtlModel where strSRCode='" + listSalRet1.get(0).toString() + "'  and strClientCode='" + clientCode + "'", "hql");
						objDelTransService.funDeleteRecord("delete from clsSalesRetrunTaxModel where strSRCode='" + listSalRet1.get(0).toString() + "'  and strClientCode='" + clientCode + "'", "hql");
					}
					if(isWebBookModuleActive){
						funDeleteJV(formName,objBean.getStrTransCode(),userCode);	
					}
					
				}
			}
			
			queryType = "hql";
			break;
		}

		if (flgTrans) {
			if (formName.equals("Physical Stk Posting")) {
				objDelTransService.funInsertRecord(objDeleteModelForSA);
				sql_StckAdjDeleteHd += "'" + objPhyModel.getStrSACode() + "' and strClientCode='" + clientCode + "'";
				sql_StckAdjDeleteDtl += "'" + objPhyModel.getStrSACode() + "' and strClientCode='" + clientCode + "'";
				objDelTransService.funDeleteRecord(sql_StckAdjDeleteHd, queryType);
				objDelTransService.funDeleteRecord(sql_StckAdjDeleteDtl, queryType);
			}
			objDelTransService.funInsertRecord(objModel);
			sql_UpdateHd += "'" + objBean.getStrTransCode() + "' and strClientCode='" + clientCode + "'";
	        sql_DeleteDtl += "'" + objBean.getStrTransCode() + "' and strClientCode='" + clientCode + "'";	
		   if(!res.equals("Transaction is present")){
			    objDelTransService.funDeleteRecord(sql_DeleteDtl, queryType);
				objDelTransService.funDeleteRecord(sql_UpdateHd, queryType);   
		   }
	        
			
			
		}
		
		req.getSession().setAttribute("success", true);
		if(res.equals("")){
			res="Transaction Code : ".concat(objBean.getStrTransCode());	
		}
		req.getSession().setAttribute("successMessage", res);
		return new ModelAndView("redirect:/frmDeleteTransaction.html");
	}

	@RequestMapping(value = "/setReportFormName", method = RequestMethod.GET)
	private void funCallJsperReport(@RequestParam("docCode") String docCode1, HttpServletResponse resp, HttpServletRequest req) {
		String[] sp = docCode1.split(",");
		String currCode = req.getSession().getAttribute("currencyCode").toString();
		String docCode = sp[0];
		switch (sp[1]) {
		case "frmGRN":
			objGRN.funCallReport(docCode, "pdf", resp, req);
			break;

		case "frmMaterialReturn":
			objMatReturn.funCallReport(docCode, "pdf", resp, req);
			break;

		case "frmOpeningStock":
			objOpStk.funCallOpeningReport(docCode, "pdf", resp, req);
			break;

		case "frmPhysicalStkPosting":
			objPhyStk.funCallReport(docCode, "pdf", resp, req);
			break;

		case "frmProductionOrder":
			// objProduction.funCallReport(docCode,"pdf",resp,req);
			break;

		case "frmPurchaseIndent":
			objPurchaseIndent.funCallReport(docCode, "pdf", resp, req);
			break;

		case "frmPurchaseOrder":
			objPurOrder.funCallReport(docCode, "pdf", resp, req);
			break;

		case "frmPurchaseReturn":
			objPurRet.funCallReport(docCode,currCode, "pdf", resp, req);
			break;

		case "frmStockAdjustment":
			objStkAdj.funCallReportBeanWise(docCode, "pdf", resp, req);
			break;

		case "frmWorkOrder":
			break;

		case "frmStockTransfer":
			objStkTransfer.funCallReport(docCode, "pdf", resp, req);
			break;

		case "frmMIS":
			objMIS.funCallReport(docCode, "pdf", resp, req);
			break;

		case "frmMaterialReq":
			objMatReq.funCallReport(docCode, "pdf", resp, req);
			break;

		case "frmProduction":
			// objProduction.funCallReport(docCode,"pdf",resp,req);
			break;

		case "frmBillPassing":
			// objBillPassing.funCallReport(docCode,"pdf",resp,req);
			break;

		case "frmInvoice":
			String invoiceformat = req.getAttribute("invoieFormat").toString();
			if (invoiceformat == "Format 1") {
				objInvController.funOpenInvoiceRetailReport(docCode, "pdf", resp, req);

			} else if (invoiceformat == "Format 2") {

				objInvController.funOpenInvoiceRetailReport(docCode, "pdf", resp, req);
			} else {
				objInvController.funOpenInvoiceRetailReport(docCode, "pdf", resp, req);
			}

			break;
		}

	}
	
	private void funDeleteJV(String formName,String sourceDocNo,String userCode) throws Exception
	{
		boolean flgTrans = false;
		String queryType = "hql";
		StringBuilder sqlDelete=new StringBuilder();
		String narration="Entry deleted By "+userCode+" On "+objGlobal.funGetCurrentDateTime("yyyy-MM-dd");

			
			StringBuilder sql=new StringBuilder();
		    sql.append("select strVouchNo from clsJVHdModel where strSourceDocNo like '"+sourceDocNo+"%"+"' ");
			
			List listJv= objBaseService.funGetListModuleWise(sql ,"hql",  "WebBooks");
			if(null!=listJv && listJv.size()>0)
			{
				for(int i=0;i<listJv.size();i++)
				{
					sqlDelete.append( "update  clsJVHdModel a  set a.dblAmt=0.0 , a.strNarration='"+narration+"' ,a.strSource='',a.strSourceDocNo='' where a.strVouchNo='"+listJv.get(i).toString()+"'");	
					objBaseService.funExcecteUpdateModuleWise(sqlDelete,"hql",  "WebBooks");
					sqlDelete.setLength(0);
					
					sqlDelete.append( "update  tbljvdtl  a set a.dblDrAmt=0.0, a.dblCrAmt=0.0 , a.strNarration='"+narration+"' ,a.strAccountCode='',a.strAccountName='' where a.strVouchNo='"+listJv.get(i).toString()+"'");	
					objBaseService.funExcecteUpdateModuleWise(sqlDelete,"sql",  "WebBooks");
					sqlDelete.setLength(0);
					
					sqlDelete.append( "update  tbljvdebtordtl a set a.dblAmt=0.0 , a.strNarration='"+narration+"' ,a.strDebtorCode='',a.strDebtorName='' where a.strVouchNo='"+listJv.get(i).toString()+"'");	
					objBaseService.funExcecteUpdateModuleWise(sqlDelete,"sql",  "WebBooks");
					sqlDelete.setLength(0);
				}
				/*sqlDelete.setLength(0);
				sqlDelete.append("select strVouchNo from tblreceiptinvdtl where strInvCode='"+sourceDocNo+"' ");
				
				List listRec= objBaseService.funGetListModuleWise(sqlDelete ,"sql",  "WebBooks");
				if(null!=listRec && listRec.size()>0)
				{
					for(int i=0;i<listRec.size();i++)
					{
						sqlDelete.setLength(0);
						sqlDelete.append("update  clsReceiptHdModel a  set a.dblAmt=0.0  , a.strNarration='"+narration+"' where a.strVouchNo='" + listRec.get(i).toString() + "'"  );
						objBaseService.funExcecteUpdateModuleWise(sqlDelete,"hql",  "WebBooks");
						sqlDelete.setLength(0);
						sqlDelete.append("update  tblreceiptdtl a set a.dblDrAmt=0.0, a.dblCrAmt=0.0  , a.strNarration='"+narration+"' ,a.strAccountCode='',a.strAccountName='' where a.strVouchNo='" + listRec.get(i).toString() + "' " );
						objBaseService.funExcecteUpdateModuleWise(sqlDelete,"sql",  "WebBooks");
						sqlDelete.setLength(0);
						sqlDelete.append("update  tblreceiptdebtordtl a set a.dblAmt=0.0 , a.strNarration='"+narration+"' ,a.strDebtorCode='',a.strDebtorName=''  where a.strVouchNo='" + listRec.get(i).toString() + "'" );
						objBaseService.funExcecteUpdateModuleWise(sqlDelete,"sql",  "WebBooks");
						sqlDelete.setLength(0);
						sqlDelete.append("update  tblreceiptinvdtl  a set a.dblInvAmt=0.0,a.dblPayedAmt=0.0  where a.strVouchNo='" + listRec.get(i).toString() + "'  ");
						objBaseService.funExcecteUpdateModuleWise(sqlDelete,"sql",  "WebBooks");
						sqlDelete.setLength(0);

					}
				}
				sqlDelete.setLength(0);
				sqlDelete.append("select strVouchNo from tblpaymentgrndtl where strGRNCode='"+sourceDocNo+"' ");
				
				List listPay= objBaseService.funGetListModuleWise(sqlDelete ,"sql",  "WebBooks");
				if(null!=listPay && listPay.size()>0)
				{
					for(int i=0;i<listPay.size();i++)
					{
						sqlDelete.setLength(0);
						sqlDelete.append("update clsPaymentHdModel a  set a.dblAmt=0.0 , a.strNarration='"+narration+"'  where a.strVouchNo='" + listPay.get(i).toString() + "'"  );
						objBaseService.funExcecteUpdateModuleWise(sqlDelete,"hql",  "WebBooks");
						sqlDelete.setLength(0);
						sqlDelete.append("update tblpaymentdtl a set a.dblDrAmt=0.0, a.dblCrAmt=0.0, a.strNarration='"+narration+"' ,a.strAccountCode='',a.strAccountName='' where a.strVouchNo='" + listPay.get(i).toString() + "'  ");
						objBaseService.funExcecteUpdateModuleWise(sqlDelete,"sql",  "WebBooks");
						sqlDelete.setLength(0);
						sqlDelete.append("update tblpaymentdebtordtl a set a.dblAmt=0.0, a.strNarration='"+narration+"' ,a.strDebtorCode='',a.strDebtorName=''  where a.strVouchNo='" + listPay.get(i).toString() + "'  ");
						objBaseService.funExcecteUpdateModuleWise(sqlDelete,"sql",  "WebBooks");
						sqlDelete.setLength(0);
						sqlDelete.append("update tblpaymentgrndtl a set a.dblGRNAmt=0.0,a.dblPayedAmt=0.0 where a.strVouchNo='" + listPay.get(i).toString() + "'  ");
						objBaseService.funExcecteUpdateModuleWise(sqlDelete,"sql",  "WebBooks");
						sqlDelete.setLength(0);
					}
				}
			*/
			}
	}





}
