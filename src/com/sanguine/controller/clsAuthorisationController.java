package com.sanguine.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsAuthoriseTransactionBean;
import com.sanguine.model.clsAuthorizeUserModel;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsPropertyMaster;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsPurchaseOrderHdModel;
import com.sanguine.model.clsSupplierMasterModel;
import com.sanguine.service.clsAuthorizeService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsPropertyMasterService;
import com.sanguine.service.clsPurchaseOrderService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsSupplierMasterService;

@Controller
public class clsAuthorisationController
{
	@Autowired
	clsGlobalFunctionsService objGlobalFunService;

	@Autowired
	clsAuthorizeService objAuthService;

	@Autowired
	clsStkPostingController objStkPosting;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsPurchaseOrderService objPurchaseOrderService;

	@Autowired
	private clsPropertyMasterService objPropertyMasterService;

	@Autowired
	private clsSupplierMasterService objSupplierMasterService;
	
	@Autowired
	clsJVGeneratorController objJVGenerator;

	private Map<String, String> map;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/frmAuthorisationTool", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") clsAuthoriseTransactionBean objBean, BindingResult result, HttpServletRequest req, Map<String, Object> model)
	{
		String urlHits = "1";
		try
		{
			urlHits = req.getParameter("saddr").toString();
		}
		catch (NullPointerException e)
		{
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		Map<String, String> mapTransForms = new HashMap<String, String>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();

		String sql = "select b.strFormName,a.strFormDesc from clsTreeMasterModel a,clsWorkFlowForSlabBasedAuth b " + "where a.strFormName=b.strFormName and strClientCode='" + clientCode + "' " + "and (b.strUser1='" + userCode + "' or b.strUser2='" + userCode + "' or b.strUser3='" + userCode + "' " + "or b.strUser4='" + userCode + "' or b.strUser5='" + userCode + "' ) " + "order by a.strFormDesc";
		List listForms = objGlobalFunService.funGetList(sql, "hql");
		int count = 0;
		for (int cnt = 0; cnt < listForms.size(); cnt++)
		{
			Object[] arrObj = (Object[]) listForms.get(cnt);
			String transName = arrObj[0].toString();
			count = funCountTransaction(transName, clientCode, userCode);
			mapTransForms.put(arrObj[0].toString(), arrObj[1].toString() + "  (" + count + ")");
			// mapTransForms.put(arrObj[0].toString(),arrObj[1].toString());
		}
		ModelAndView objModelAndView = null;
		if ("2".equalsIgnoreCase(urlHits))
		{
			objModelAndView = new ModelAndView("frmAuthorisationTool_1");
		}
		else if ("1".equalsIgnoreCase(urlHits))
		{
			objModelAndView = new ModelAndView("frmAuthorisationTool");
		}
		else
		{
			objModelAndView = new ModelAndView("frmAuthorisationTool");
		}

		objModelAndView.addObject("listFormName", mapTransForms);
		return objModelAndView;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/saveAuthorizedTrans", method = RequestMethod.POST)
	public ModelAndView funSaveAuthorizedDoc(@ModelAttribute("command") @Valid clsAuthoriseTransactionBean objBean, BindingResult result, HttpServletRequest req) throws ParseException
	{
		String urlHits = "1";
		try
		{
			urlHits = req.getParameter("saddr").toString();
		}
		catch (NullPointerException e)
		{
			urlHits = "1";
		}
		
		Map<String, String> mapTransForms = new HashMap<String, String>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		int formLevel = 0, userLevel = 0;

		clsCompanyMasterModel objCompModel = objSetupMasterService.funGetObject(clientCode);
		
		String sql = "select intLevel from clsWorkFlowForSlabBasedAuth " + "where strFormName='" + objBean.getStrFormName() + "' and strClientCode='" + clientCode + "'";
		List listUserLevel = objGlobalFunService.funGetList(sql, "hql");
		if (listUserLevel.size() > 0)
		{
			formLevel = (Integer) listUserLevel.get(0);
		}
		userLevel = objBean.getIntLevel();

		ArrayList<String> arrListTransCode = objBean.getChkTransCodes();
		ArrayList<String> arrListComments = objBean.getTxtComments();

		sql = "";
		String sql_UpdateAuthorize = "";
		String strTempSACode = "";
		String strSACode = "";
		String sqlUpdateAuthorizeUser = "";		
		
		
		for (int cnt = 0; cnt < arrListTransCode.size(); cnt++)
		{
			if (null != arrListTransCode.get(cnt))
			{
				clsAuthorizeUserModel objModel = funPrepareModel(objBean.getStrFormName(), arrListTransCode.get(cnt), userLevel, arrListComments.get(cnt), clientCode, userCode);
				objAuthService.funAddUpdate(objModel);
				String authLevelColumnName="";
				switch (objBean.getStrFormName())
				{
					case "frmGRN":
						sql = "update clsGRNHdModel set intLevel=" + userLevel + " where strGRNCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						sql_UpdateAuthorize = "update clsGRNHdModel set strAuthorise='Yes' where strGRNCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
		
						authLevelColumnName=objGlobalFunctions.funGetAuthLevelColumnName(userLevel);
						if(!authLevelColumnName.trim().isEmpty())
						{
							sqlUpdateAuthorizeUser="update clsGRNHdModel set "+authLevelColumnName+"='" + userCode + "' where strGRNCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						}
						
						break;
		
					case "frmPurchaseOrder":
						sql = "update clsPurchaseOrderHdModel set intLevel=" + userLevel + " where strPOCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						sql_UpdateAuthorize = "update clsPurchaseOrderHdModel set strAuthorise='Yes' where strPOCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
		
						authLevelColumnName=objGlobalFunctions.funGetAuthLevelColumnName(userLevel);
						if(!authLevelColumnName.trim().isEmpty())
						{
							sqlUpdateAuthorizeUser="update clsPurchaseOrderHdModel set "+authLevelColumnName+"='" + userCode + "' where strPOCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						}
						break;
		
					case "frmBillPassing":
						sql = "update clsBillPassHdModel set intLevel=" + userLevel + " where strBillPassNo='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						sql_UpdateAuthorize = "update clsBillPassHdModel set strAuthorise='Yes' where strBillPassNo='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						
						authLevelColumnName=objGlobalFunctions.funGetAuthLevelColumnName(userLevel);
						if(!authLevelColumnName.trim().isEmpty())
						{
							sqlUpdateAuthorizeUser="update clsBillPassHdModel set "+authLevelColumnName+"='" + userCode + "' where strBillPassNo='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						}
						break;
		
					case "frmMaterialReq":
						sql = "update clsRequisitionHdModel set intLevel=" + userLevel + " where strReqCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						sql_UpdateAuthorize = "update clsRequisitionHdModel set strAuthorise='Yes' where strReqCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
		
						authLevelColumnName=objGlobalFunctions.funGetAuthLevelColumnName(userLevel);
						if(!authLevelColumnName.trim().isEmpty())
						{
							sqlUpdateAuthorizeUser="update clsRequisitionHdModel set "+authLevelColumnName+"='" + userCode + "' where strReqCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						}
						break;
		
					case "frmMaterialReturn":
						sql = "update clsMaterialReturnHdModel set intLevel=" + userLevel + " where strMRetCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						sql_UpdateAuthorize = "update clsMaterialReturnHdModel set strAuthorise='Yes' where strMRetCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
		
						authLevelColumnName=objGlobalFunctions.funGetAuthLevelColumnName(userLevel);
						if(!authLevelColumnName.trim().isEmpty())
						{
							sqlUpdateAuthorizeUser="update clsMaterialReturnHdModel set "+authLevelColumnName+"='" + userCode + "' where strMRetCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						}
						break;
		
					case "frmMIS":
						sql = "update clsMISHdModel set intLevel=" + userLevel + " where strMISCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						sql_UpdateAuthorize = "update clsMISHdModel set strAuthorise='Yes' where strMISCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
		
						authLevelColumnName=objGlobalFunctions.funGetAuthLevelColumnName(userLevel);
						if(!authLevelColumnName.trim().isEmpty())
						{
							sqlUpdateAuthorizeUser="update clsMISHdModel set "+authLevelColumnName+"='" + userCode + "' where strMISCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						}
						break;
		
					case "frmOpeningStock":
						sql = "update clsInitialInventoryModel set intLevel=" + userLevel + " where strOpStkCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						sql_UpdateAuthorize = "update clsInitialInventoryModel set strAuthorise='Yes' where strOpStkCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
		
		
						authLevelColumnName=objGlobalFunctions.funGetAuthLevelColumnName(userLevel);
						if(!authLevelColumnName.trim().isEmpty())
						{
							sqlUpdateAuthorizeUser="update clsInitialInventoryModel set "+authLevelColumnName+"='" + userCode + "' where strOpStkCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						}
						break;
		
					case "frmPhysicalStkPosting":
						sql = "update clsStkPostingHdModel set intLevel=" + userLevel + " where strPSCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						sql_UpdateAuthorize = "update clsStkPostingHdModel set strAuthorise='Yes' where strPSCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
		
						authLevelColumnName=objGlobalFunctions.funGetAuthLevelColumnName(userLevel);
						if(!authLevelColumnName.trim().isEmpty())
						{
							sqlUpdateAuthorizeUser="update clsStkPostingHdModel set "+authLevelColumnName+"='" + userCode + "' where strPSCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						}
						break;
		
					case "frmProduction":
						sql = "update clsProductionHdModel set intLevel=" + userLevel + " where strPDCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						sql_UpdateAuthorize = "update clsProductionHdModel set strAuthorise='Yes' where strPDCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
		
						authLevelColumnName=objGlobalFunctions.funGetAuthLevelColumnName(userLevel);
						if(!authLevelColumnName.trim().isEmpty())
						{
							sqlUpdateAuthorizeUser="update clsProductionHdModel set "+authLevelColumnName+"='" + userCode + "' where strPDCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						}
						break;
		
					case "frmProductionOrder":
						sql = "update clsProductionOrderHdModel set intLevel=" + userLevel + " where strOPCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						sql_UpdateAuthorize = "update clsProductionOrderHdModel set strAuthorise='Yes' where strOPCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
		
						authLevelColumnName=objGlobalFunctions.funGetAuthLevelColumnName(userLevel);
						if(!authLevelColumnName.trim().isEmpty())
						{
							sqlUpdateAuthorizeUser="update clsProductionOrderHdModel set "+authLevelColumnName+"='" + userCode + "' where strOPCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						}
						break;
		
					case "frmPurchaseReturn":
						sql = "update clsPurchaseReturnHdModel set intLevel=" + userLevel + " where strPRCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						sql_UpdateAuthorize = "update clsPurchaseReturnHdModel set strAuthorise='Yes' where strPRCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
		
						authLevelColumnName=objGlobalFunctions.funGetAuthLevelColumnName(userLevel);
						if(!authLevelColumnName.trim().isEmpty())
						{
							sqlUpdateAuthorizeUser="update clsPurchaseReturnHdModel set "+authLevelColumnName+"='" + userCode + "' where strPRCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						}
						break;
		
					case "frmPurchaseIndent":
						sql = "update clsPurchaseIndentHdModel set intLevel=" + userLevel + " where strPIcode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						sql_UpdateAuthorize = "update clsPurchaseIndentHdModel set strAuthorise='Yes' where strPIcode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
		
						authLevelColumnName=objGlobalFunctions.funGetAuthLevelColumnName(userLevel);
						if(!authLevelColumnName.trim().isEmpty())
						{
							sqlUpdateAuthorizeUser="update clsPurchaseIndentHdModel set "+authLevelColumnName+"='" + userCode + "' where strPIcode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						}
						break;
		
					case "frmRateContract":
						sql = "update clsRateContractHdModel set intLevel=" + userLevel + " where strRateContNo='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						sql_UpdateAuthorize = "update clsRateContractHdModel set strAuthorise='Yes' where strRateContNo='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
		
						authLevelColumnName=objGlobalFunctions.funGetAuthLevelColumnName(userLevel);
						if(!authLevelColumnName.trim().isEmpty())
						{
							sqlUpdateAuthorizeUser="update clsRateContractHdModel set "+authLevelColumnName+"='" + userCode + "' where strRateContNo='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						}
						break;
		
					case "frmStockAdjustment":
						sql = "update clsStkAdjustmentHdModel set intLevel=" + userLevel + " where strSACode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						sql_UpdateAuthorize = "update clsStkAdjustmentHdModel set strAuthorise='Yes' where strSACode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
		
						authLevelColumnName=objGlobalFunctions.funGetAuthLevelColumnName(userLevel);
						if(!authLevelColumnName.trim().isEmpty())
						{
							sqlUpdateAuthorizeUser="update clsStkAdjustmentHdModel set "+authLevelColumnName+"='" + userCode + "' where strSACode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						}
						break;
		
					case "frmStockTransfer":
						sql = "update clsStkTransferHdModel set intLevel=" + userLevel + " where strSTCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						sql_UpdateAuthorize = "update clsStkTransferHdModel set strAuthorise='Yes' where strSTCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
		
						authLevelColumnName=objGlobalFunctions.funGetAuthLevelColumnName(userLevel);
						if(!authLevelColumnName.trim().isEmpty())
						{
							sqlUpdateAuthorizeUser="update clsStkTransferHdModel set "+authLevelColumnName+"='" + userCode + "' where strSTCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						}
						break;
		
					case "frmWorkOrder":
						sql = "update clsWorkOrderHdModel set intLevel=" + userLevel + " where strWOCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						sql_UpdateAuthorize = "update clsWorkOrderHdModel set strAuthorise='Yes' where strWOCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
		
						authLevelColumnName=objGlobalFunctions.funGetAuthLevelColumnName(userLevel);
						if(!authLevelColumnName.trim().isEmpty())
						{
							sqlUpdateAuthorizeUser="update clsWorkOrderHdModel set "+authLevelColumnName+"='" + userCode + "' where strWOCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						}
						break;
						
					case "frmInovice":
						sql = "update clsInvoiceHdModel set intLevel=" + userLevel + " where strInvCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						sql_UpdateAuthorize = "update clsInvoiceHdModel set strAuthorise='Yes' where strInvCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
		
						authLevelColumnName=objGlobalFunctions.funGetAuthLevelColumnName(userLevel);
						if(!authLevelColumnName.trim().isEmpty())
						{
							sqlUpdateAuthorizeUser="update clsInvoiceHdModel set "+authLevelColumnName+"='" + userCode + "' where strInvCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						}
						break;
						
					case "frmSalesOrder":
						sql = "update clsSalesOrderHdModel set intLevel=" + userLevel + " where strSOCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						sql_UpdateAuthorize = "update clsSalesOrderHdModel set strAuthorise='Yes' where strSOCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
		
						authLevelColumnName=objGlobalFunctions.funGetAuthLevelColumnName(userLevel);
						if(!authLevelColumnName.trim().isEmpty())
						{
							sqlUpdateAuthorizeUser="update clsSalesOrderHdModel set "+authLevelColumnName+"='" + userCode + "' where strSOCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						}
						break;
					
					case "frmSalesReturn":
						sql = "update clsSalesReturnHdModel set intLevel=" + userLevel + " where strSRCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						sql_UpdateAuthorize = "update clsSalesReturnHdModel set strAuthorise='Yes' where strSRCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
		
						authLevelColumnName=objGlobalFunctions.funGetAuthLevelColumnName(userLevel);
						if(!authLevelColumnName.trim().isEmpty())
						{
							sqlUpdateAuthorizeUser="update clsSalesReturnHdModel set "+authLevelColumnName+"='" + userCode + "' where strSRCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						}
						break;
						
					case "frmDeliveryChallan":
						sql = "update clsDeliveryChallanHdModel set intLevel=" + userLevel + " where strDCCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						sql_UpdateAuthorize = "update clsDeliveryChallanHdModel set strAuthorise='Yes' where strDCCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
		
						authLevelColumnName=objGlobalFunctions.funGetAuthLevelColumnName(userLevel);
						if(!authLevelColumnName.trim().isEmpty())
						{
							sqlUpdateAuthorizeUser="update clsDeliveryChallanHdModel set "+authLevelColumnName+"='" + userCode + "' where strDCCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						}
						break;	
						
					case "frmStockReq":
						sql = "update clsRequisitionHdModel set intLevel=" + userLevel + " where strReqCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						sql_UpdateAuthorize = "update clsRequisitionHdModel set strAuthorise='Yes' where strReqCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
		
						authLevelColumnName=objGlobalFunctions.funGetAuthLevelColumnName(userLevel);
						if(!authLevelColumnName.trim().isEmpty())
						{
							sqlUpdateAuthorizeUser="update clsRequisitionHdModel set "+authLevelColumnName+"='" + userCode + "' where strReqCode='" + arrListTransCode.get(cnt) + "' and strClientCode = '" + clientCode + "'";
						}
						break;
				}
	
				if (sql.trim().length() > 0)
				{
					objAuthService.funUpdateTransLevel(sql);
					
					if (sqlUpdateAuthorizeUser.trim().length() > 0)
					{
						objAuthService.funUpdateTransLevel(sqlUpdateAuthorizeUser);
					}
					
					//new ModelAndView("redirect:/openRptGrnSlip.html?rptGRNCode="+arrListTransCode.get(cnt));
	//				String url="openRptGrnSlip.html?rptGRNCode="+arrListTransCode.get(cnt);//
	//				new ModelAndView("<a href="+url+" target=\"_blank\"/>");
					
					if (userLevel == formLevel)
					{
						objAuthService.funUpdateTransLevel(sql_UpdateAuthorize);
	
						// sms Send Code for Purchase Order
						if (objBean.getStrFormName().equalsIgnoreCase("frmPurchaseOrder"))
						{
							if (null != arrListTransCode.get(cnt))
							{
								funSendSMSForPO(arrListTransCode.get(cnt), clientCode, propCode);
							}
						}
						
						if (objCompModel.getStrWebBookModule().equals("Yes")) {
							
							if (objBean.getStrFormName().equalsIgnoreCase("frmGRN"))
							{
								objJVGenerator.funGenrateJVforGRN(arrListTransCode.get(cnt), clientCode, userCode, propCode, req);
							}
							else if (objBean.getStrFormName().equalsIgnoreCase("frmInovice"))
							{
								objJVGenerator.funGenrateJVforInvoice(arrListTransCode.get(cnt), clientCode, userCode, propCode, req);
							}	else if (objBean.getStrFormName().equalsIgnoreCase("frmSalesReturn"))
							{
								objJVGenerator.funGenrateJVforSalesReturn(arrListTransCode.get(cnt), clientCode, userCode, propCode, req);
							}
							else if (objBean.getStrFormName().equalsIgnoreCase("frmPurchaseReturn"))
							{
								objJVGenerator.funGenrateJVforPurchaseReturn(arrListTransCode.get(cnt), clientCode, userCode, propCode, req);
							}
						}
					}
				}
				
				if (objBean.getStrFormName().equals("frmPhysicalStkPosting"))
				{
					strSACode = objGlobalFunctions.funGenerateStkAdjustement(arrListTransCode.get(cnt), req);
				}
				if (strTempSACode.length() > 0)
				{
					strTempSACode = strTempSACode + "," + strSACode;
				}
				else
				{
					strTempSACode = strSACode;
				}
			}
		}

		sql = "select b.strFormName,a.strFormDesc from clsTreeMasterModel a,clsWorkFlowForSlabBasedAuth b " + "where a.strFormName=b.strFormName and strClientCode='" + clientCode + "' " + "and (b.strUser1='" + userCode + "' or b.strUser2='" + userCode + "' or b.strUser3='" + userCode + "' " + "or b.strUser4='" + userCode + "' or b.strUser5='" + userCode + "' ) " + "order by a.strFormDesc";

		List listForms = objGlobalFunService.funGetList(sql, "hql");
		int count = 0;
		for (int cnt = 0; cnt < listForms.size(); cnt++)
		{
			Object[] arrObj = (Object[]) listForms.get(cnt);
			String transName = arrObj[0].toString();
			count = funCountTransaction(transName, clientCode, userCode);
			mapTransForms.put(arrObj[0].toString(), arrObj[1].toString() + "(" + count + ")");
		}

		ModelAndView objModelAndView = null;
		if ("2".equalsIgnoreCase(urlHits))
		{
			objModelAndView = new ModelAndView("frmAuthorisationTool_1");
		}
		else if ("1".equalsIgnoreCase(urlHits))
		{
			objModelAndView = new ModelAndView("frmAuthorisationTool");
		}
		else
		{
			objModelAndView = new ModelAndView("frmAuthorisationTool");
		}

		if (!strTempSACode.equals(""))
		{
			map = new HashMap<String, String>();
			map.put("StkAdjCode", "SA Code : ".concat(strTempSACode));
			objModelAndView.addAllObjects(map);
		}
		objModelAndView.addObject("listFormName", mapTransForms);
		return objModelAndView;
		
	}

	private clsAuthorizeUserModel funPrepareModel(String formName, String transCode, int level, String comments, String clientCode, String userCode)
	{
		long lastNo = objGlobalFunService.funGetLastNo("tblauthorizeuser", "", "intId", clientCode);
		String authorizeCode = "A" + String.format("%06d", lastNo);
		clsAuthorizeUserModel objModel = new clsAuthorizeUserModel();
		objModel.setStrAuthorizeCode(authorizeCode);
		objModel.setIntId(lastNo);
		objModel.setStrFormName(formName);
		objModel.setStrTransCode(transCode);
		objModel.setStrUserCode(userCode);
		objModel.setStrComments(comments);
		objModel.setIntLevel(level);
		objModel.setDteAuthorizeDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserCreated(userCode);
		objModel.setStrUserModified(userCode);
		objModel.setDteCreatedDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setDteLastModified(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrClientCode(clientCode);

		return objModel;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getTransactionCodes", method = RequestMethod.GET)
	public @ResponseBody List funShowTransactionCode(HttpServletRequest request)
	{
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String transName = request.getParameter("transName").toString();
		String sql = "", queryType = "sql";

		switch (transName)
		{
			case "frmGRN":
				sql = "select a.strGRNCode,DATE_FORMAT(date(a.dtGRNDate),'%m-%d-%Y'),b.strPName,a.dblTotal,(a.intlevel+1) " + "from tblgrnhd a left outer join tblpartymaster b on a.strSuppCode=b.strPCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmGRN') " + "group by a.strGRNCode";
				queryType = "sql";
				break;
	
			case "frmPurchaseOrder":
	
				sql = "select a.strPOCode,DATE_FORMAT(date(a.dtPODate),'%m-%d-%Y'),b.strPName,a.dblFinalAmt,(a.intlevel+1) " + "from tblpurchaseorderhd a left outer join tblpartymaster b on a.strSuppCode=b.strPCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmPurchaseOrder') " + "group by a.strPOCode ";
				queryType = "sql";
				break;
	
			case "frmBillPassing":
				sql = "select a.strBillPassNo,DATE_FORMAT(date(a.dtPassDate),'%m-%d-%Y'),ifnull(b.strPName,''),a.dblBillAmt,(a.intlevel+1) " + "from tblbillpasshd a left outer join tblpartymaster b on a.strSuppCode=b.strPCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmBillPassing') " + "group by a.strBillPassNo";
				queryType = "sql";
				break;
	
			case "frmMIS":
				sql = "select a.strMISCode,DATE_FORMAT(date(a.dtMISDate),'%m-%d-%Y'),b.strLocName,a.strNarration,(a.intlevel+1) " + "from tblmishd a left outer join tbllocationmaster b on a.strLocFrom=b.strLocCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmMIS') " + "group by a.strMISCode";
				queryType = "sql";
				break;
	
			case "frmMaterialReq":
				sql = "select a.strReqCode,DATE_FORMAT(date(a.dtReqDate),'%m-%d-%Y'),b.strLocName,a.dblSubTotal,(a.intlevel+1) " + "from tblreqhd a left outer join tbllocationmaster b on a.strLocBy=b.strLocCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmMaterialReq') " + "group by a.strReqCode";
				queryType = "sql";
				break;
	
			case "frmMaterialReturn":
				sql = "select a.strMRetCode,DATE_FORMAT(date(a.dtMRetDate),'%m-%d-%Y'),b.strLocName,a.strNarration,(a.intlevel+1) " + "from tblmaterialreturnhd a left outer join tbllocationmaster b on a.strLocFrom=b.strLocCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmMaterialReturn') " + "group by a.strMRetCode";
				queryType = "sql";
				break;
	
			case "frmPhysicalStkPosting":
				sql = "select a.strPSCode,DATE_FORMAT(date(a.dtPSDate),'%m-%d-%Y') as dtPSDate,b.strLocName,'',(a.intlevel+1) " + "from tblstockpostinghd a left outer join tbllocationmaster b on a.strLocCode=b.strLocCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmPhysicalStkPosting') " + "group by a.strPSCode";
				queryType = "sql";
				break;
	
			case "frmProduction":
				sql = "select a.strPDCode,DATE_FORMAT(date(a.dtPDDate),'%m-%d-%Y'),b.strLocName,a.strNarration,(a.intlevel+1) " + "from tblproductionhd a left outer join tbllocationmaster b on a.strLocCode=b.strLocCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmProduction') " + "group by a.strPDCode";
				queryType = "sql";
				break;
	
			case "frmProductionOrder":
				sql = "select a.strOPCode,DATE_FORMAT(date(a.dtOPDate),'%m-%d-%Y'),b.strLocName,a.strNarration,(a.intlevel+1) " + "from tblproductionorderhd a left outer join tbllocationmaster b on a.strLocCode=b.strLocCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmProductionOrder') " + "group by a.strOPCode";
				queryType = "sql";
				break;
	
			case "frmPurchaseIndent":
				sql = "select a.strPIcode,DATE_FORMAT(date(a.dtPIDate),'%m-%d-%Y'),b.strLocName,a.strNarration,(a.intlevel+1) " + "from tblpurchaseindendhd a left outer join tbllocationmaster b on a.strLocCode=b.strLocCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmPurchaseIndent') " + "group by a.strPIcode";
				queryType = "sql";
				break;
	
			case "frmPurchaseReturn":
				sql = "select a.strPRCode,DATE_FORMAT(date(a.dtPRDate),'%m-%d-%Y'),b.strLocName,a.strNarration,(a.intlevel+1) " + "from tblpurchasereturnhd a left outer join tbllocationmaster b on a.strLocCode=b.strLocCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmPurchaseReturn') " + "group by a.strPRCode";
				queryType = "sql";
				break;
	
			case "frmStockAdjustment":
				sql = "select a.strSACode,DATE_FORMAT(date(a.dtSADate),'%m-%d-%Y'),b.strLocName,a.strNarration,(a.intlevel+1) " + "from tblstockadjustmenthd a left outer join tbllocationmaster b on a.strLocCode=b.strLocCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmStockAdjustment') " + "group by a.strSACode";
				queryType = "sql";
				break;
	
			case "frmStockTransfer":
				sql = "select a.strSTCode,DATE_FORMAT(date(a.dtSTDate),'%m-%d-%Y'),b.strLocName,a.strNarration,(a.intlevel+1) " + "from tblstocktransferhd a left outer join tbllocationmaster b on a.strFromLocCode=b.strLocCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmStockTransfer') " + "group by a.strSTCode";
				queryType = "sql";
				break;
	
			case "frmRateContract":
				sql = "select a.strRateContNo,DATE_FORMAT(date(a.dtRateContDate),'%m-%d-%Y'),b.strPName,'',(a.intlevel+1) " + "from tblrateconthd a left outer join tblpartymaster b on a.strSuppCode=b.strPCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmRateContract') " + "group by a.strGRNCode";
				queryType = "sql";
				break;
				
			case "frmInovice":
				sql = "select a.strInvCode,DATE_FORMAT(date(a.dteInvDate),'%m-%d-%Y'),b.strPName,a.dblGrandTotal,(a.intlevel+1) " 
					+ " from tblinvoicehd a left outer join tblpartymaster b on a.strCustCode=b.strPCode " 
					+ " where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmInovice') " + "group by a.strInvCode";
				queryType = "sql";
				break;
			
			case "frmSalesOrder":
				sql = "select a.strSOCode,DATE_FORMAT(date(a.dteSODate),'%m-%d-%Y'),b.strPName,a.dblTotal,(a.intlevel+1) " 
						+ " from tblsalesorderhd a left outer join tblpartymaster b on a.strCustCode=b.strPCode " 
						+ " where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmSalesOrder') " + "group by a.strSOCode";
				queryType = "sql";
				break;
			
			case "frmSalesReturn":
				sql = "select a.strSRCode,DATE_FORMAT(date(a.dteSRDate),'%m-%d-%Y'),b.strPName,a.dblTotalAmt,(a.intlevel+1) " 
						+ " from tblsalesreturnhd a left outer join tblpartymaster b on a.strCustCode=b.strPCode " 
						+ " where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmSalesReturn') " + "group by a.strSRCode";
				queryType = "sql";
				break;
				
			case "frmDeliveryChallan":
				sql = "select a.strDCCode,DATE_FORMAT(date(a.dteDCDate),'%m-%d-%Y'),b.strPName,'',(a.intlevel+1) " 
						+ " from tbldeliverychallanhd a left outer join tblpartymaster b on a.strCustCode=b.strPCode " 
						+ " where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmDeliveryChallan') " + "group by a.strDCCode";
				queryType = "sql";
				break;
				
			case "frmStockReq":
				sql = "select a.strReqCode,DATE_FORMAT(date(a.dtReqDate),'%m-%d-%Y'),b.strLocName,a.dblSubTotal,(a.intlevel+1) " + "from tblreqhd a left outer join tbllocationmaster b on a.strLocBy=b.strLocCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmStockReq') " + "group by a.strReqCode";
				queryType = "sql";
				break;
				
		}

		List listTransCodes = new ArrayList<Object>();
		System.out.println(sql);
		if (sql.trim().length() > 0)
		{
			listTransCodes = objGlobalFunService.funGetList(sql, queryType);
		}

		return listTransCodes;
	}

	@SuppressWarnings("rawtypes")
	public int funCountTransaction(String transName, String clientCode, String userCode)
	{
		String sql = "";
		int count = 0;
		List list = null;
		switch (transName)
		{
		case "frmGRN":
			sql = "select count(*)" + "from tblgrnhd a left outer join tblpartymaster b on a.strSuppCode=b.strPCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmGRN') ";
			list = objGlobalFunService.funGetList(sql, "sql");
			if (list.size() > 0)
			{
				count = Integer.parseInt(list.get(0).toString());
			}
			break;
		case "frmPurchaseOrder":

			sql = "select count(*) " + "from tblpurchaseorderhd a left outer join tblpartymaster b on a.strSuppCode=b.strPCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmPurchaseOrder') ";
			list = objGlobalFunService.funGetList(sql, "sql");
			if (list.size() > 0)
			{
				count = Integer.parseInt(list.get(0).toString());
			}
			break;

		case "frmBillPassing":
			sql = "select count(*) " + "from tblbillpasshd a left outer join tblpartymaster b on a.strSuppCode=b.strPCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmBillPassing') " + "group by a.strBillPassNo";
			list = objGlobalFunService.funGetList(sql, "sql");
			if (list.size() > 0)
			{
				count = Integer.parseInt(list.get(0).toString());
			}
			break;

		case "frmMIS":
			sql = "select count(*) from tblmishd a " + "left outer join tbllocationmaster b on a.strLocFrom=b.strLocCode and b.strClientCode = '" + clientCode + "' " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmMIS') ";
			list = objGlobalFunService.funGetList(sql, "sql");
			if (list.size() > 0)
			{
				count = Integer.parseInt(list.get(0).toString());
			}
			break;
		case "frmMaterialReq":
			sql = "select count(*) " + "from tblreqhd a left outer join tbllocationmaster b on a.strLocBy=b.strLocCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmMaterialReq') ";
			list = objGlobalFunService.funGetList(sql, "sql");
			if (list.size() > 0)
			{
				count = Integer.parseInt(list.get(0).toString());
			}
			break;

		case "frmMaterialReturn":
			sql = "select count(*) " + "from tblmaterialreturnhd a left outer join tbllocationmaster b on a.strLocFrom=b.strLocCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmMaterialReturn') ";
			list = objGlobalFunService.funGetList(sql, "sql");
			if (list.size() > 0)
			{
				count = Integer.parseInt(list.get(0).toString());
			}
			break;

		case "frmPhysicalStkPosting":
			sql = "select count(*) " + "from tblstockpostinghd a left outer join tbllocationmaster b on a.strLocCode=b.strLocCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmPhysicalStkPosting') ";
			list = objGlobalFunService.funGetList(sql, "sql");
			if (list.size() > 0)
			{
				count = Integer.parseInt(list.get(0).toString());
			}
			break;

		case "frmProduction":
			sql = "select count(*) " + "from tblproductionhd a left outer join tbllocationmaster b on a.strLocCode=b.strLocCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmProduction') ";
			list = objGlobalFunService.funGetList(sql, "sql");
			if (list.size() > 0)
			{
				count = Integer.parseInt(list.get(0).toString());
			}
			break;

		case "frmProductionOrder":
			sql = "select count(*) " + "from tblproductionorderhd a left outer join tbllocationmaster b on a.strLocCode=b.strLocCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmProductionOrder') ";
			list = objGlobalFunService.funGetList(sql, "sql");
			if (list.size() > 0)
			{
				count = Integer.parseInt(list.get(0).toString());
			}
			break;

		case "frmPurchaseIndent":
			sql = "select count(*) " + "from tblpurchaseindendhd a left outer join tbllocationmaster b on a.strLocCode=b.strLocCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmPurchaseIndent') ";
			list = objGlobalFunService.funGetList(sql, "sql");
			if (list.size() > 0)
			{
				count = Integer.parseInt(list.get(0).toString());
			}
			break;

		case "frmPurchaseReturn":
			sql = "select count(*) " + "from tblpurchasereturnhd a left outer join tbllocationmaster b on a.strLocCode=b.strLocCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmPurchaseReturn') ";
			list = objGlobalFunService.funGetList(sql, "sql");
			if (list.size() > 0)
			{
				count = Integer.parseInt(list.get(0).toString());
			}
			break;

		case "frmStockAdjustment":
			sql = "count(*) " + "from tblstockadjustmenthd a left outer join tbllocationmaster b on a.strLocCode=b.strLocCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmStockAdjustment') ";

			list = objGlobalFunService.funGetList(sql, "sql");
			if (list.size() > 0)
			{
				count = Integer.parseInt(list.get(0).toString());
			}
			break;

		case "frmStockTransfer":
			sql = "select count(*) " + "from tblstocktransferhd a left outer join tbllocationmaster b on a.strFromLocCode=b.strLocCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmStockTransfer') ";

			list = objGlobalFunService.funGetList(sql, "sql");
			if (list.size() > 0)
			{
				count = Integer.parseInt(list.get(0).toString());
			}
			break;

		case "frmRateContract":
			sql = "select count(*) " + "from tblrateconthd a left outer join tblpartymaster b on a.strSuppCode=b.strPCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmRateContract') ";

			list = objGlobalFunService.funGetList(sql, "sql");
			if (list.size() > 0)
			{
				count = Integer.parseInt(list.get(0).toString());
			}
			break;
			
		case "frmInovice":
			sql = "select count(*) " + "from tblinvoicehd a left outer join tblpartymaster b on a.strCustCode=b.strPCode " 
				+ " where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmInovice') ";
			list = objGlobalFunService.funGetList(sql, "sql");
			if (list.size() > 0)
			{
				count = Integer.parseInt(list.get(0).toString());
			}
			break;
			
		case "frmSalesOrder":
				sql = "select count(*) " + "from tblsalesorderhd a left outer join tblpartymaster b on a.strCustCode=b.strPCode " 
					+ " where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmSalesOrder') ";
				list = objGlobalFunService.funGetList(sql, "sql");
				if (list.size() > 0)
				{
					count = Integer.parseInt(list.get(0).toString());
				}
				break;
		case "frmSalesReturn":
			sql = "select count(*) " + "from tblsalesreturnhd a left outer join tblpartymaster b on a.strCustCode=b.strPCode " 
					+ " where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmSalesReturn') ";
				list = objGlobalFunService.funGetList(sql, "sql");
				if (list.size() > 0)
				{
					count = Integer.parseInt(list.get(0).toString());
				}
				break;
		
		case "frmDeliveryChallan":
			sql = "select count(*) " + "from tbldeliverychallanhd a left outer join tblpartymaster b on a.strCustCode=b.strPCode " 
					+ " where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmDeliveryChallan') ";
				list = objGlobalFunService.funGetList(sql, "sql");
				if (list.size() > 0)
				{
					count = Integer.parseInt(list.get(0).toString());
				}
				break;
				
		case "frmStockReq":
			sql = "select count(*) " + "from tblreqhd a left outer join tbllocationmaster b on a.strLocBy=b.strLocCode " + "where a.strClientCode = '" + clientCode + "' " + "and (a.intLevel +1) IN (select if(strUser1 = '" + userCode + "',1,if(strUser2 = '" + userCode + "',2,if(strUser3 = '" + userCode + "',3,if(strUser4 = '" + userCode + "',4,if(strUser5 = '" + userCode + "',5,0))))) as intLevel " + "from tblworkflowforslabbasedauth " + "where strformname = 'frmStockReq') ";
			list = objGlobalFunService.funGetList(sql, "sql");
			if (list.size() > 0)
			{
				count = Integer.parseInt(list.get(0).toString());
			}
			break;
			
		}

		return count;
	}

	private void funSendSMSForPO(String poCode, String clientCode, String propCode)
	{

		String strMobileNo = "";
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);

		clsPurchaseOrderHdModel objPurchaseOrderHdModel = objPurchaseOrderService.funGetObject(poCode, clientCode);

		String smsAPIUrl = objSetup.getStrSMSAPI();

		String smsContent = objSetup.getStrSMSContent();

		if (!smsAPIUrl.equals(""))
		{
			if (smsContent.contains("%%CompanyName"))
			{
				List<clsCompanyMasterModel> listCompanyModel = objSetupMasterService.funGetListCompanyMasterModel(clientCode);
				smsContent = smsContent.replace("%%CompanyName", listCompanyModel.get(0).getStrCompanyName());
			}
			if (smsContent.contains("%%PropertyName"))
			{
				clsPropertyMaster objProperty = objPropertyMasterService.funGetProperty(propCode, clientCode);
				smsContent = smsContent.replace("%%PropertyName", objProperty.getPropertyName());
			}

			if (smsContent.contains("%%ContactPerson"))
			{
				clsSupplierMasterModel objModel = objSupplierMasterService.funGetObject(objPurchaseOrderHdModel.getStrSuppCode(), clientCode);
				smsContent = smsContent.replace("%%ContactPerson", objModel.getStrContact());
			}

			if (smsContent.contains("%%PONo"))
			{
				smsContent = smsContent.replace("%%PONo", poCode);
			}

			if (smsContent.contains("%%PODate"))
			{
				smsContent = smsContent.replace("%%PODate", objGlobalFunctions.funGetDate("dd-MM-yyyy", objPurchaseOrderHdModel.getDtPODate()));
			}

			if (smsContent.contains("%%DeleveryDate"))
			{
				smsContent = smsContent.replace("%%DeleveryDate", objGlobalFunctions.funGetDate("dd-MM-yyyy", objPurchaseOrderHdModel.getDtDelDate()));
			}

			if (smsContent.contains("%%Amount"))
			{
				smsContent = smsContent.replace("%%Amount", String.valueOf(objPurchaseOrderHdModel.getDblFinalAmt()));
			}

			if (smsAPIUrl.contains("ReceiverNo"))
			{
				clsSupplierMasterModel objModel = objSupplierMasterService.funGetObject(objPurchaseOrderHdModel.getStrSuppCode(), clientCode);
				smsAPIUrl = smsAPIUrl.replace("ReceiverNo", objModel.getStrMobile());
				strMobileNo = objModel.getStrMobile();
			}
			if (smsAPIUrl.contains("MsgContent"))
			{
				smsAPIUrl = smsAPIUrl.replace("MsgContent", smsContent);
				smsAPIUrl = smsAPIUrl.replace(" ", "%20");
			}
		}

		if (!smsAPIUrl.equals(""))
		{
			URL url;
			HttpURLConnection uc = null;
			StringBuilder output = new StringBuilder();

			try
			{
				url = new URL(smsAPIUrl);
				uc = (HttpURLConnection) url.openConnection();
				if (strMobileNo.length() >= 10)
				{
					BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), Charset.forName("UTF-8")));
					String inputLine;
					while ((inputLine = in.readLine()) != null)
					{
						output.append(inputLine);
					}
					in.close();
				}

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				uc.disconnect();
			}
		}

	}

}
