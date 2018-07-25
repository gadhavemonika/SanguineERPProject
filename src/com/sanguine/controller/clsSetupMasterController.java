package com.sanguine.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsSetupMasterBean;
import com.sanguine.model.clsCompanyLogoModel;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProcessSetupModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsTCMasterModel;
import com.sanguine.model.clsTCTransModel;
import com.sanguine.model.clsTransactionTimeModel;
import com.sanguine.model.clsTreeMasterModel;
import com.sanguine.model.clsWorkFlowForSlabBasedAuth;
import com.sanguine.model.clsWorkFlowModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsReasonMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsTCMasterService;
import com.sanguine.service.clsTCTransService;
import com.sanguine.service.clsTransactionTimeService;
import com.sanguine.service.clsUserMasterService;

@Controller
public class clsSetupMasterController
{

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsUserMasterService objUserMasterService;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsTCMasterService objTCMaster;

	@Autowired
	private clsTCTransService objTCTransService;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsReasonMasterService objReasonMasterService;

	@Autowired
	private clsCurrencyMasterService objCurrencyMasterService;

	@Autowired
	private clsTransactionTimeService objTransactionTimeService;

	@Autowired
	private clsLocationMasterService objLocationMasterService;

	@RequestMapping(value = "/frmSetup", method = RequestMethod.GET)
	String funOpenSetupForm(@ModelAttribute("setUpAttribute") @Valid clsSetupMasterBean objBean, BindingResult result, HttpServletRequest request, Model model, Map<String, Object> model1)
	{
		String urlHits = "1";
		try
		{
			urlHits = request.getParameter("saddr").toString();
		}
		catch (NullPointerException e)
		{
			urlHits = "1";
		}
		model1.put("urlHits", urlHits);
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String financialYear = request.getSession().getAttribute("financialYear").toString();
		clsCompanyMasterModel ob = objSetupMasterService.funGetObject(clientCode);
		clsSetupMasterBean bean = new clsSetupMasterBean();
		bean.setStrCompanyCode(ob.getStrCompanyCode());
		bean.setStrCompanyName(ob.getStrCompanyName());
		bean.setStrFinYear(financialYear);
		bean.setDtStart(ob.getDtStart());
		bean.setDtEnd(ob.getDtEnd());
		bean.setDtLastTransDate(ob.getDtLastTransDate());
		bean.setStrDbName(ob.getStrDbName());
		bean.setStrIndustryType(ob.getStrIndustryType());
		bean.setStrBankAccountNo("");
		bean.setStrBankName("");
		bean.setStrBranchName("");
		bean.setStrBankAdd1("");
		bean.setStrBankAdd2("");
		bean.setStrBankCity("");
		bean.setStrSwiftCode("");
		bean.setStrNegStock("");
		bean.setStrPOBOM("");
		bean.setStrSOBOM("");
		bean.setStrTotalWorkhour("");
		bean.setDtFromTime("");
		bean.setDtToTime("");
		bean.setStrWorkFlowbasedAuth("");
		bean.setIntId(0);
		bean.setStrAdd1("");
		bean.setStrAdd2("");
		bean.setStrCity("");
		bean.setStrState("");
		bean.setStrCountry("");
		bean.setStrPin("");
		bean.setStrBAdd1("");
		bean.setStrBAdd2("");
		bean.setStrBCity("");
		bean.setStrBState("");
		bean.setStrBCountry("");
		bean.setStrBPin("");
		bean.setStrSAdd1("");
		bean.setStrSAdd2("");
		bean.setStrSCity("");
		bean.setStrSState("");
		bean.setStrSCountry("");
		bean.setStrSPin("");
		bean.setStrPhone("");
		bean.setStrFax("");
		bean.setStrEmail("");
		bean.setStrWebsite("");
		bean.setIntDueDays("");
		bean.setStrCST("");
		bean.setStrVAT("");
		bean.setStrSerTax("");
		bean.setStrPanNo("");
		bean.setStrLocCode("");
		bean.setStrAsseeCode("");
		bean.setStrPurEmail("");
		bean.setStrSaleEmail("");
		bean.setStrRangeDiv("");
		bean.setStrAcceptanceNo("");
		bean.setStrLate("");
		bean.setStrRej("");
		bean.setStrPChange("");
		bean.setStrExDelay("");
		bean.setStrTC1("");
		bean.setStrTC2("");
		bean.setStrTC3("");
		bean.setStrTC4("");
		bean.setStrTC5("");
		bean.setStrTC6("");
		bean.setStrTC7("");
		bean.setStrTC8("");
		bean.setStrTC9("");
		bean.setStrTC10("");
		bean.setStrTC11("");
		bean.setStrTC12("");
		bean.setStrShowAllPropCustomer(true);
		List<clsTreeMasterModel> listProcessForms1 = null;
		listProcessForms1 = objSetupMasterService.funGetProcessSetupForms();
		bean.setListProcessSetupForm(listProcessForms1);

		Map<String, String> mapReason = objReasonMasterService.funGetResonList(clientCode);
		if (mapReason.isEmpty())
		{
			mapReason.put("", "");
		}
		Map<String, String> mapCurrency = objCurrencyMasterService.funGetAllCurrency(clientCode);
		if (mapCurrency.isEmpty())
		{
			mapCurrency.put("", "");
		}
		model.addAttribute("listCurrency", mapCurrency);
		model.addAttribute("listReason", mapReason);
		model.addAttribute("setUpAttribute", bean);
		model.addAttribute("processSetupFormList", listProcessForms1);
		model.addAttribute("auditFormList", listProcessForms1);
		if ("2".equalsIgnoreCase(urlHits))
		{
			return "frmSetup_1";
		}
		else if ("1".equalsIgnoreCase(urlHits))
		{
			return "frmSetup";
		}
		else
		{
			return "frmSetup";
		}

	}

	@ModelAttribute("properties")
	public Map<String, String> getAllProperties(HttpServletRequest request)
	{
		Map<String, String> properties = null;
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String usercode = request.getSession().getAttribute("usercode").toString();
		if (usercode.equalsIgnoreCase("SANGUINE"))
		{
			properties = objUserMasterService.funProperties(clientCode);
		}
		else
		{
			List<String> listPropCodes = new ArrayList<String>();
			List listProperty = objGlobalFunctionsService.funGetDataList("select a.strPropertyCode  from tbluserlocdtl a " + " where a.strUserCode='" + usercode + "' and a.strClientCode='" + clientCode + "' " + " group by a.strPropertyCode ORDER by a.strPropertyCode ", "sql");

			for (Object ob : listProperty)
			{
				listPropCodes.add(ob.toString());
			}

			properties = objUserMasterService.funGetUserWiseProperties(listPropCodes, clientCode);

		}

		if (properties.isEmpty())
		{
			properties.put("", "");
		}
		return properties;
	}

	@ModelAttribute("users")
	public Map<String, String> getAllUsers()
	{
		Map<String, String> users = objUserMasterService.funGetUsers();
		if (users.isEmpty())
		{
			users.put("", "");
		}
		return users;
	}

	@RequestMapping(value = "/loadTCForSetup", method = RequestMethod.GET)
	public @ResponseBody clsTCMasterModel funAssignFields(@RequestParam("tcCode") String tcCode, HttpServletRequest req)
	{
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsTCMasterModel objTCModel = objSetupMasterService.funGetTCForSetup(tcCode, clientCode);
		return objTCModel;
	}

	@RequestMapping(value = "/loadPropertySetupForm", method = RequestMethod.POST)
	String funLoadPropertySetup(@ModelAttribute("setUpAttribute") @Valid clsSetupMasterBean bean, BindingResult result, HttpServletRequest request, Model model, Map<String, Object> model1, HttpServletResponse response)
	{
		String urlHits = "1";
		try
		{
			urlHits = request.getParameter("saddr").toString();
		}
		catch (NullPointerException e)
		{
			urlHits = "1";
		}

		try
		{
			model1.put("urlHits", urlHits);
			// String code=bean.getStrProperty();
			String code = bean.getStrProperty();
			// request.getSession().getAttribute("userProperty").toString();
			if (code.equals("ALL"))
			{
				code = bean.getStrProperty();
			}

			String clientCode = request.getSession().getAttribute("clientCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(code, clientCode);

			List<clsProcessSetupModel> listclsProcessSetupModel = objSetupMasterService.funGetProcessSetupModelList(code, clientCode);
			List<clsTreeMasterModel> listProcessForms1 = null;

			if (listclsProcessSetupModel.size() > 0)
			{
				listProcessForms1 = funSetSeletedProcess(listclsProcessSetupModel);
			}
			else
			{
				listProcessForms1 = objSetupMasterService.funGetProcessSetupForms();
			}

			// bean.setStrAdd1(objSetup.getStrAdd1());
			bean.setListProcessSetupForm(listProcessForms1);

			List<clsWorkFlowModel> listclsWorkFlowModel = objSetupMasterService.funGetWorkFlowModelList(code, clientCode);
			bean.setListclsWorkFlowModel(listclsWorkFlowModel);
			List<clsWorkFlowForSlabBasedAuth> listclsWorkFlowForSlabBasedAuth = objSetupMasterService.funGetWorkFlowForSlabBasedAuthList(code, clientCode);
			if (null == objSetup)
			{
				funSetBlankPropertyData(bean);
			}
			else
			{
				funSetPropertyData(objSetup, bean);
			}

			String sql_TC = "select a.strTCCode,b.strTCName,a.strTCDesc " + "from clsTCTransModel a,clsTCMasterModel b " + "where a.strTCCode=b.strTCCode and a.strTransCode=:transCode " + "and a.strClientCode=:clientCode and a.strTransType=:transType";
			List listTC_Setup = objTCTransService.funGetTCTransList(sql_TC, code, clientCode, "Property Setup");

			List<clsTCMasterModel> listTCMasterForSetup = new ArrayList<clsTCMasterModel>();
			for (int cnt = 0; cnt < listTC_Setup.size(); cnt++)
			{
				clsTCMasterModel objTCMasterModel = new clsTCMasterModel();
				Object[] arrObject = (Object[]) listTC_Setup.get(cnt);
				objTCMasterModel.setStrTCCode(arrObject[0].toString());
				objTCMasterModel.setStrTCName(arrObject[1].toString());
				objTCMasterModel.setStrTCDesc(arrObject[2].toString());
				listTCMasterForSetup.add(objTCMasterModel);
			}
			// bean.setListTCForSetup(listTCMasterForSetup);
			Map<String, String> mapCurrency = objCurrencyMasterService.funGetAllCurrency(clientCode);
			if (mapCurrency.isEmpty())
			{
				mapCurrency.put("", "");
			}
			model.addAttribute("listCurrency", mapCurrency);
			model.addAttribute("setUpAttribute", bean);
			model.addAttribute("processSetupFormList", listProcessForms1);
			model.addAttribute("listclsWorkFlowForSlabBasedAuth", listclsWorkFlowForSlabBasedAuth);
			model.addAttribute("listTCForSetup", listTCMasterForSetup);
			List<clsTreeMasterModel> Auditlist = null;
			List<clsTreeMasterModel> AuditTemplist = new ArrayList<clsTreeMasterModel>();
			Auditlist = objSetupMasterService.funGetAuditForms();
			String strForms = "";

			List<clsTransactionTimeModel> listBeanTransactionTimeModel = new ArrayList<clsTransactionTimeModel>();
			List<clsTransactionTimeModel> listTransactionTimeModel = objTransactionTimeService.funLoadTransactionTime(code, clientCode, "");
			if (listTransactionTimeModel.size() > 0)
			{
				for (clsTransactionTimeModel ob : listTransactionTimeModel)
				{
					clsLocationMasterModel objLoc = objLocationMasterService.funGetObject(ob.getStrLocCode(), clientCode);
					ob.setStrLocName(objLoc.getStrLocName());

					// for displaying in UI
					SimpleDateFormat displayFormat2 = new SimpleDateFormat("hh:mma");
					SimpleDateFormat parseFormat2 = new SimpleDateFormat("HH:mm");
					Date fdate, tdate;

					String fromDate = ob.getTmeFrom();
					fdate = parseFormat2.parse(fromDate);

					String toDate = ob.getTmeTo();
					tdate = parseFormat2.parse(toDate);

					String fTime = displayFormat2.format(fdate);
					String tTime = displayFormat2.format(tdate);
					ob.setTmeFrom(fTime);
					ob.setTmeTo(tTime);

					listBeanTransactionTimeModel.add(ob);
				}
			}
			model.addAttribute("listTransactionTime", listBeanTransactionTimeModel);

			if (objSetup != null && null != objSetup.getStrAuditFrom())
			{
				strForms = objSetup.getStrAuditFrom();
				String trmp[] = strForms.split(",");
				for (clsTreeMasterModel FormList : Auditlist)
				{
					clsTreeMasterModel on = new clsTreeMasterModel();
					on.setStrFormDesc(FormList.getStrFormDesc());
					on.setStrFormName(FormList.getStrFormName());
					for (int i = 0; i < trmp.length; i++)
					{
						if (FormList.getStrFormName().equalsIgnoreCase(trmp[i].toString()))
						{
							on.setStrAuditForm("on");
						}
					}
					AuditTemplist.add(on);
				}
			}
			else
			{
				AuditTemplist = Auditlist;
			}
			model.addAttribute("auditFormList", AuditTemplist);
			Map<String, String> mapReason = objReasonMasterService.funGetResonList(clientCode);
			if (mapReason.isEmpty())
			{
				mapReason.put("", "");
			}
			model.addAttribute("listReason", mapReason);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		if ("2".equalsIgnoreCase(urlHits))
		{
			return "frmSetup_1";
		}
		else if ("1".equalsIgnoreCase(urlHits))
		{
			return "frmSetup";
		}
		else
		{
			return "frmSetup";
		}
	}

	private void funSetPropertyData(clsPropertySetupModel objSetup, clsSetupMasterBean bean)
	{
		// bean.setListProcessSetupForm(listProcessForms1);
		bean.setStrIndustryType(objSetup.getStrIndustryType());

		/*
		 * Main Address
		 */
		bean.setStrAdd1(objSetup.getStrAdd1());
		bean.setStrAdd2(objSetup.getStrAdd2());
		bean.setStrCity(objSetup.getStrCity());
		bean.setStrState(objSetup.getStrState());
		bean.setStrCountry(objSetup.getStrCountry());
		bean.setStrPin(objSetup.getStrPin());
		/*
		 * Billing Address
		 */
		bean.setStrBAdd1(objSetup.getStrBAdd1());
		bean.setStrBAdd2(objSetup.getStrBAdd2());
		bean.setStrBCity(objSetup.getStrBCity());
		bean.setStrBState(objSetup.getStrBState());
		bean.setStrBCountry(objSetup.getStrBCountry());
		bean.setStrBPin(objSetup.getStrBPin());
		/*
		 * Shipping Address
		 */
		bean.setStrSAdd1(objSetup.getStrSAdd1());
		bean.setStrSAdd2(objSetup.getStrSAdd2());
		bean.setStrSCity(objSetup.getStrSCity());
		bean.setStrSState(objSetup.getStrSState());
		bean.setStrSCountry(objSetup.getStrSCountry());
		bean.setStrSPin(objSetup.getStrSPin());
		/*
		 * Others Company Tab
		 */
		bean.setStrPhone(objSetup.getStrPhone());
		bean.setStrFax(objSetup.getStrFax());
		bean.setStrEmail(objSetup.getStrEmail());
		bean.setStrWebsite(objSetup.getStrWebsite());
		bean.setIntDueDays(objSetup.getIntDueDays());
		bean.setStrMask(objSetup.getStrMask());
		bean.setStrCST(objSetup.getStrCST());
		bean.setStrVAT(objSetup.getStrVAT());
		bean.setStrSerTax(objSetup.getStrSerTax());
		bean.setStrPanNo(objSetup.getStrPanNo());
		bean.setStrLocCode(objSetup.getStrLocCode());
		bean.setStrAsseeCode(objSetup.getStrAsseeCode());
		bean.setStrPurEmail(objSetup.getStrPurEmail());
		bean.setStrSaleEmail(objSetup.getStrSaleEmail());
		bean.setStrRangeAdd(objSetup.getStrRangeAdd());
		bean.setStrRangeDiv(objSetup.getStrRangeDiv());
		bean.setStrCommi(objSetup.getStrCommi());
		bean.setStrRegNo(objSetup.getStrRegNo());
		bean.setStrDivision(objSetup.getStrDivision());
		bean.setDblBondAmt(objSetup.getDblBondAmt());
		bean.setStrAcceptanceNo(objSetup.getStrAcceptanceNo());
		bean.setStrDivisionAdd(objSetup.getStrDivisionAdd());
		;
		bean.setStrECCNo(objSetup.getStrECCNo());

		/*
		 * General Tab
		 */
		bean.setStrNegStock(objSetup.getStrNegStock());
		bean.setStrPOBOM(objSetup.getStrPOBOM());
		bean.setStrSOBOM(objSetup.getStrSOBOM());
		bean.setStrTotalWorkhour(objSetup.getStrTotalWorkhour());
		bean.setDtFromTime(objSetup.getDtFromTime());
		bean.setDtToTime(objSetup.getDtToTime());
		bean.setStrWorkFlowbasedAuth(objSetup.getStrWorkFlowbasedAuth());
		bean.setIntdec(objSetup.getIntdec());

		bean.setIntqtydec(objSetup.getIntqtydec());
		bean.setStrListPriceInPO(objSetup.getStrListPriceInPO());
		bean.setStrCMSModule(objSetup.getStrCMSModule());
		bean.setStrBatchMethod(objSetup.getStrBatchMethod());
		bean.setStrTPostingType(objSetup.getStrTPostingType());
		bean.setStrAutoDC(objSetup.getStrAutoDC());
		bean.setStrAudit(objSetup.getStrAudit());
		bean.setStrRatePickUpFrom(objSetup.getStrRatePickUpFrom());
		bean.setStrShowReqVal(objSetup.getStrShowReqVal());
		bean.setStrShowStkReq(objSetup.getStrShowStkReq());
		bean.setStrShowValMISSlip(objSetup.getStrShowValMISSlip());
		bean.setStrChangeUOMTrans(objSetup.getStrChangeUOMTrans());
		bean.setStrShowProdMaster(objSetup.getStrShowProdMaster());
		bean.setStrShowProdDoc(objSetup.getStrShowProdDoc());
		bean.setStrAllowDateChangeInMIS(objSetup.getStrAllowDateChangeInMIS());
		bean.setStrShowTransAsc_Desc(objSetup.getStrShowTransAsc_Desc());
		bean.setStrNameChangeProdMast(objSetup.getStrNameChangeProdMast());
		bean.setStrStkAdjReason(objSetup.getStrStkAdjReason());
		bean.setIntNotificationTimeinterval(objSetup.getIntNotificationTimeinterval());
		bean.setStrMonthEnd(objSetup.getStrMonthEnd());
		bean.setStrShowAllProdToAllLoc(objSetup.getStrShowAllProdToAllLoc());
		bean.setStrLocWiseProductionOrder(objSetup.getStrLocWiseProductionOrder());
		bean.setStrShowAvgQtyInOP(objGlobal.funIfNull(objSetup.getStrShowAvgQtyInOP(), "Y", objSetup.getStrShowAvgQtyInOP()));
		bean.setStrShowStockInOP(objGlobal.funIfNull(objSetup.getStrShowStockInOP(), "Y", objSetup.getStrShowStockInOP()));
		bean.setStrShowAvgQtyInSO(objGlobal.funIfNull(objSetup.getStrShowAvgQtyInSO(), "Y", objSetup.getStrShowAvgQtyInSO()));
		bean.setStrShowStockInSO(objGlobal.funIfNull(objSetup.getStrShowStockInSO(), "Y", objSetup.getStrShowStockInSO()));
		bean.setStrEffectOfDiscOnPO(objGlobal.funIfNull(objSetup.getStrEffectOfDiscOnPO(), "Y", objSetup.getStrEffectOfDiscOnPO()));
		bean.setStrInvFormat(objSetup.getStrInvFormat());
		bean.setStrInvNote(objSetup.getStrInvNote());
		bean.setStrCurrencyCode(objSetup.getStrCurrencyCode());
		/*
		 * if(objSetup.getStrShowAllPropCustomer()==null ||
		 * objSetup.getStrShowAllPropCustomer().equalsIgnoreCase("N")) {
		 * bean.setStrShowAllPropCustomer(false); }else {
		 * bean.setStrShowAllPropCustomer(true); }
		 */

		bean.setStrShowAllPropCustomer(funGetBoolean(objSetup.getStrShowAllPropCustomer()));
		// bean.setStrShowAllPropCustomer(objGlobal.funIfNull(objSetup.getStrShowAllPropCustomer()
		// ,"N",objSetup.getStrShowAllPropCustomer()));
		bean.setStrEffectOfInvoice(objSetup.getStrEffectOfInvoice());
		bean.setStrEffectOfGRNWebBook(objSetup.getStrEffectOfGRNWebBook());
		bean.setStrMultiCurrency(objSetup.getStrMultiCurrency());

		bean.setStrShowAllPartyToAllLoc(objSetup.getStrShowAllPartyToAllLoc());
		bean.setStrShowAllTaxesOnTransaction(objSetup.getStrShowAllTaxesOnTransaction());

		bean.setStrSOKOTPrint(funGetBoolean(objSetup.getStrSOKOTPrint()));
		bean.setStrRateHistoryFormat(objSetup.getStrRateHistoryFormat());
		bean.setStrPOSlipFormat(objSetup.getStrPOSlipFormat());
		bean.setStrSRSlipFormat(objSetup.getStrSRSlipFormat());

		/*
		 * Bank Tab
		 */
		bean.setStrBankName(objSetup.getStrBankName());
		bean.setStrBranchName(objSetup.getStrBranchName());
		bean.setStrBankAdd1(objSetup.getStrBAdd1());
		bean.setStrBankAdd2(objSetup.getStrBAdd2());
		bean.setStrBankCity(objSetup.getStrBankCity());
		bean.setStrBankAccountNo(objSetup.getStrBankAccountNo());
		bean.setStrSwiftCode(objSetup.getStrSwiftCode());

		/*
		 * Supplier Performance Tab
		 */
		bean.setStrLate(objSetup.getStrLate());
		bean.setStrRej(objSetup.getStrRej());
		;
		bean.setStrPChange(objSetup.getStrPChange());
		bean.setStrExDelay(objSetup.getStrExDelay());

		/*
		 * SMS Tab
		 */
		bean.setStrSMSProvider(objSetup.getStrSMSProvider());
		bean.setStrSMSAPI(objSetup.getStrSMSAPI());
		;
		bean.setStrSMSContent(objSetup.getStrSMSContent());
	}

	private void funSetBlankPropertyData(clsSetupMasterBean bean)
	{
		// bean.setListProcessSetupForm(listProcessForms1);
		bean.setStrIndustryType("Hospitality");

		/*
		 * Main Address
		 */
		bean.setStrAdd1("");
		bean.setStrAdd2("");
		bean.setStrCity("");
		bean.setStrState("");
		bean.setStrCountry("");
		bean.setStrPin("");
		/*
		 * Billing Address
		 */
		bean.setStrBAdd1("");
		bean.setStrBAdd2("");
		bean.setStrBCity("");
		bean.setStrBState("");
		bean.setStrBCountry("");
		bean.setStrBPin("");
		/*
		 * Shipping Address
		 */
		bean.setStrSAdd1("");
		bean.setStrSAdd2("");
		bean.setStrSCity("");
		bean.setStrSState("");
		bean.setStrSCountry("");
		bean.setStrSPin("");
		/*
		 * Others Company Tab
		 */
		bean.setStrPhone("");
		bean.setStrFax("");
		bean.setStrEmail("");
		bean.setStrWebsite("");
		bean.setIntDueDays("");
		bean.setStrMask("");
		bean.setStrCST("");
		bean.setStrVAT("");
		bean.setStrSerTax("");
		bean.setStrPanNo("");
		bean.setStrLocCode("");
		bean.setStrAsseeCode("");
		bean.setStrPurEmail("");
		bean.setStrSaleEmail("");
		bean.setStrRangeAdd("");
		bean.setStrRangeDiv("");
		bean.setStrCommi("");
		bean.setStrRegNo("");
		bean.setStrDivision("");
		bean.setDblBondAmt("");
		bean.setStrAcceptanceNo("");
		/*
		 * General Tab
		 */
		bean.setStrNegStock("");
		bean.setStrPOBOM("");
		bean.setStrSOBOM("");
		bean.setStrTotalWorkhour("");
		bean.setDtFromTime("");
		bean.setDtToTime("");
		bean.setStrWorkFlowbasedAuth("");
		bean.setIntdec(2);

		bean.setIntqtydec(2);
		bean.setStrListPriceInPO("");
		bean.setStrCMSModule("");
		bean.setStrBatchMethod("");
		bean.setStrTPostingType("");
		bean.setStrAutoDC("");
		bean.setStrAudit("");
		bean.setStrShowReqVal("");
		bean.setStrShowStkReq("");
		bean.setStrShowValMISSlip("");
		bean.setStrChangeUOMTrans("");
		bean.setStrShowProdMaster("");
		bean.setStrShowProdDoc("");
		bean.setStrAllowDateChangeInMIS("");
		bean.setStrShowTransAsc_Desc("");
		bean.setStrNameChangeProdMast("");
		bean.setStrStkAdjReason("");
		bean.setIntNotificationTimeinterval(1);
		bean.setStrMonthEnd("N");
		/*
		 * Bank Tab
		 */
		bean.setStrBankName("");
		bean.setStrBranchName("");
		bean.setStrBankAdd1("");
		bean.setStrBankAdd2("");
		bean.setStrBankCity("");
		bean.setStrBankAccountNo("");
		bean.setStrSwiftCode("");

		/*
		 * Supplier Performance Tab
		 */
		bean.setStrLate("");
		bean.setStrRej("");
		;
		bean.setStrPChange("");
		bean.setStrExDelay("");

		/*
		 * SMS Tab
		 */
		bean.setStrSMSProvider("SANGUINE");
		bean.setStrSMSAPI("");
		;
		bean.setStrSMSContent("");

	}

	@RequestMapping(value = "/saveSetupData", method = RequestMethod.POST)
	ModelAndView funSaveSetupData(@ModelAttribute("command") @Valid clsSetupMasterBean bean, BindingResult result, HttpServletRequest req, @RequestParam("companyLogo") MultipartFile file) throws IOException
	{
		String urlHits = "1";
		FileOutputStream fileOuputStream = null;
		try
		{
			urlHits = req.getParameter("saddr").toString();
		}
		catch (NullPointerException e)
		{
			urlHits = "1";
		}
		try
		{
			if (!result.hasErrors())
			{
				String userCode = req.getSession().getAttribute("usercode").toString();
				// String userCode="SUPER";
				String companyCode = bean.getStrCompanyCode();
				String propertyCode = bean.getStrProperty();
				String clientCode = req.getSession().getAttribute("clientCode").toString();
				List<clsTreeMasterModel> listclsProcessSetupForms = bean.getListProcessSetupForm();
				List<clsWorkFlowModel> listWorkFlowModel = bean.getListclsWorkFlowModel();
				List<clsWorkFlowForSlabBasedAuth> listWorkFlowForSlabBasedAuth = bean.getListclsWorkFlowForSlabBasedAuth();
				if (null != listclsProcessSetupForms && listclsProcessSetupForms.size() > 0)
				{
					objSetupMasterService.funDeleteProcessSetup(propertyCode, clientCode);
					funSaveProcessSetupForm(listclsProcessSetupForms, propertyCode, clientCode);
				}

				objSetupMasterService.funDeleteWorkFlowAutorization(propertyCode, clientCode);
				if (null != listWorkFlowModel && listWorkFlowModel.size() > 0)
				{

					for (clsWorkFlowModel ob : listWorkFlowModel)
					{
						ob.setStrPropertyCode(propertyCode);
						ob.setStrClientCode(clientCode);
						ob.setStrUserCreated(userCode);
						ob.setStrUserModified(userCode);
						ob.setStrCompanyCode(bean.getStrCompanyCode());
						ob.setDtDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
						ob.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
						objSetupMasterService.funAddWorkFlowAuthorization(ob);
					}
				}

				// Save Terms and Condition fields....
				if (null != bean.getListTCForSetup())
				{
					clsGlobalFunctions objGlobal = new clsGlobalFunctions();

					String sql_Delete = "delete from clsTCTransModel where strTransCode='" + propertyCode + "' " + "and strTransType='Property Setup' and strClientCode='" + clientCode + "'";
					objTCTransService.funDeleteTCTransList(sql_Delete);
					List<clsTCTransModel> listTCTransModel = objGlobal.funPrepareTCTransModel(bean.getListTCForSetup(), propertyCode, userCode, clientCode, "Property Setup");
					for (int cnt = 0; cnt < listTCTransModel.size(); cnt++)
					{
						clsTCTransModel objTCTrans = listTCTransModel.get(cnt);
						objTCTransService.funAddTCTrans(objTCTrans);
					}
				}

				objSetupMasterService.funDeleteWorkFlowForslabBasedAuth(propertyCode, clientCode);

				if (null != listWorkFlowForSlabBasedAuth && listWorkFlowForSlabBasedAuth.size() > 0)
				{

					for (clsWorkFlowForSlabBasedAuth ob : listWorkFlowForSlabBasedAuth)
					{
						ob.setStrPropertyCode(propertyCode);
						ob.setStrClientCode(clientCode);
						ob.setStrCompanyCode(companyCode);
						ob.setStrUserCreated(userCode);
						ob.setStrUserModified(userCode);
						ob.setDtDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
						ob.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
						objSetupMasterService.funAddWorkFlowForslabBasedAuth(ob);
					}
				}
				clsPropertySetupModel PropertySetupModel = funPrepareMaster(bean, userCode, req);
				objSetupMasterService.funAddUpdatePropertySetupModel(PropertySetupModel);
				// System.out.println(file.getBytes());
				if (file.getSize() != 0)
				{
					Blob blobProdImage = Hibernate.createBlob(file.getInputStream());
					clsCompanyLogoModel comLogo = new clsCompanyLogoModel();
					comLogo.setStrCompanyCode(companyCode);
					comLogo.setStrCompanyLogo(blobProdImage);
					objSetupMasterService.funSaveUpdateCompanyLogo(comLogo);
				}
				if (null != bean.getListclsTransactionTimeModel())
				{
					for (clsTransactionTimeModel ob : bean.getListclsTransactionTimeModel())
					{

						ob.setStrPropertyCode(propertyCode);
						ob.setStrClientCode(clientCode);
						ob.setStrTransactionName("");

						// for saving in Database
						SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
						SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mma");
						Date fdate,
								tdate;
						String fromDate = ob.getTmeFrom();
						fdate = parseFormat.parse(fromDate);
						String todate = ob.getTmeTo();
						tdate = parseFormat.parse(todate);

						String fTime = displayFormat.format(fdate);
						String tTime = displayFormat.format(tdate);
						ob.setTmeFrom(fTime);
						ob.setTmeTo(tTime);

						objTransactionTimeService.funAddUpdate(ob);
					}
				}
				clsCompanyLogoModel objCompanyLogo = objSetupMasterService.funGetCompanyLogoObject(companyCode);
				if (objCompanyLogo.getStrCompanyLogo() != null)
				{
					Blob blob = objCompanyLogo.getStrCompanyLogo();
					String imagePath = servletContext.getRealPath("/resources/images");
					int blobLength = (int) blob.length();
					byte[] blobAsBytes = blob.getBytes(1, blobLength);

					fileOuputStream = new FileOutputStream(imagePath + "/company_Logo.png");
					fileOuputStream.write(blobAsBytes);
					fileOuputStream.close();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		funSetAuthorizationFormStatus(req);
		return new ModelAndView("redirect:/frmSetup.html?saddr=" + urlHits);
	}

	@SuppressWarnings("finally")
	private int funSetAuthorizationFormStatus(HttpServletRequest req)
	{
		try
		{
			Map<String, Boolean> hmAuthorizationStatus = new HashMap<String, Boolean>();
			if (null != req.getSession().getAttribute("hmAuthorization"))
			{
				req.getSession().removeAttribute("hmAuthorization");
			}
			hmAuthorizationStatus.put("GRN", false);
			hmAuthorizationStatus.put("Material Req", false);
			hmAuthorizationStatus.put("MIS", false);
			hmAuthorizationStatus.put("Purchase Order", false);
			hmAuthorizationStatus.put("Purchase Return", false);
			hmAuthorizationStatus.put("Purchase Indent", false);
			hmAuthorizationStatus.put("Material Return", false);
			hmAuthorizationStatus.put("Bill Passing", false);
			hmAuthorizationStatus.put("Production", false);
			hmAuthorizationStatus.put("Production Order", false);
			hmAuthorizationStatus.put("Stock Adjustment", false);
			hmAuthorizationStatus.put("Stock Transfer", false);
			hmAuthorizationStatus.put("Physical Stock Posting", false);
			hmAuthorizationStatus.put("Rate Contract", false);
			hmAuthorizationStatus.put("Work Order", false);
			hmAuthorizationStatus.put("Invoice", false);
			hmAuthorizationStatus.put("Sales Order", false);
			hmAuthorizationStatus.put("Sales Return", false);
			hmAuthorizationStatus.put("Delivery Challan", false);
			hmAuthorizationStatus.put("Stock Req", false);

			String sql_Auth = "select distinct(strFormName) from tblworkflowforslabbasedauth ";
			List listAuthorization = objGlobalFunctionsService.funGetList(sql_Auth, "sql");
			for (int cnt = 0; cnt < listAuthorization.size(); cnt++)
			{
				String formName = (String) listAuthorization.get(cnt);
				if (formName.equals("frmMaterialReq"))
				{
					hmAuthorizationStatus.remove("Material Req");
					hmAuthorizationStatus.put("Material Req", true);
				}
				else if (formName.equals("frmGRN"))
				{
					hmAuthorizationStatus.remove("GRN");
					hmAuthorizationStatus.put("GRN", true);
				}
				else if (formName.equals("frmBillPassing"))
				{
					hmAuthorizationStatus.remove("Bill Passing");
					hmAuthorizationStatus.put("Bill Passing", true);
				}
				else if (formName.equals("frmMaterialReturn"))
				{
					hmAuthorizationStatus.remove("Material Return");
					hmAuthorizationStatus.put("Material Return", true);
				}
				else if (formName.equals("frmMIS"))
				{
					hmAuthorizationStatus.remove("MIS");
					hmAuthorizationStatus.put("MIS", true);
				}
				else if (formName.equals("frmPhysicalStkPosting"))
				{
					hmAuthorizationStatus.remove("Physical Stock Posting");
					hmAuthorizationStatus.put("Physical Stock Posting", true);
				}
				else if (formName.equals("frmProduction"))
				{
					hmAuthorizationStatus.remove("Production");
					hmAuthorizationStatus.put("Production", true);
				}
				else if (formName.equals("frmProductionOrder"))
				{
					hmAuthorizationStatus.remove("Production Order");
					hmAuthorizationStatus.put("Production Order", true);
				}
				else if (formName.equals("frmProduction"))
				{
					hmAuthorizationStatus.remove("Production");
					hmAuthorizationStatus.put("Production", true);
				}
				else if (formName.equals("frmPurchaseIndent"))
				{
					hmAuthorizationStatus.remove("Purchase Indent");
					hmAuthorizationStatus.put("Purchase Indent", true);
				}
				else if (formName.equals("frmPurchaseOrder"))
				{
					hmAuthorizationStatus.remove("Purchase Order");
					hmAuthorizationStatus.put("Purchase Order", true);
				}
				else if (formName.equals("frmPurchaseReturn"))
				{
					hmAuthorizationStatus.remove("Purchase Return");
					hmAuthorizationStatus.put("Purchase Return", true);
				}
				else if (formName.equals("frmRateContract"))
				{
					hmAuthorizationStatus.remove("Rate Contract");
					hmAuthorizationStatus.put("Rate Contract", true);
				}
				else if (formName.equals("frmStockAdjustment"))
				{
					hmAuthorizationStatus.remove("Stock Adjustment");
					hmAuthorizationStatus.put("Stock Adjustment", true);
				}
				else if (formName.equals("frmStockTransfer"))
				{
					hmAuthorizationStatus.remove("Stock Transfer");
					hmAuthorizationStatus.put("Stock Transfer", true);
				}
				else if (formName.equals("frmWorkOrder"))
				{
					hmAuthorizationStatus.remove("Work Order");
					hmAuthorizationStatus.put("Work Order", true);
				}
				else if (formName.equals("frmInvoice"))
				{
					hmAuthorizationStatus.remove("Invoice");
					hmAuthorizationStatus.put("Invoice", true);
				}
				else if (formName.equals("frmSalesOrder"))
				{
					hmAuthorizationStatus.remove("Sales Order");
					hmAuthorizationStatus.put("Sales Order", true);
				}
				else if (formName.equals("frmSalesReturn"))
				{
					hmAuthorizationStatus.remove("Sales Return");
					hmAuthorizationStatus.put("Sales Return", true);
				}
				else if (formName.equals("frmDeliveryChallan"))
				{
					hmAuthorizationStatus.remove("Delivery Challan");
					hmAuthorizationStatus.put("Delivery Challan", true);
				}
				else if (formName.equals("frmStockReq"))
				{
					hmAuthorizationStatus.remove("Stock Req");
					hmAuthorizationStatus.put("Stock Req", true);
				}
			}

			req.getSession().setAttribute("hmAuthorization", hmAuthorizationStatus);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			return 1;
		}
	}

	private clsPropertySetupModel funPrepareMaster(clsSetupMasterBean bean, String string, HttpServletRequest req)
	{
		clsGlobalFunctions ob = new clsGlobalFunctions();
		clsPropertySetupModel objPropertySetupModel = new clsPropertySetupModel();
		objPropertySetupModel.setStrPropertyCode(bean.getStrProperty());
		objPropertySetupModel.setStrCompanyCode(bean.getStrCompanyCode());
		objPropertySetupModel.setStrIndustryType(bean.getStrIndustryType());
		/*
		 * Main Address
		 */
		objPropertySetupModel.setStrAdd1(bean.getStrAdd1());
		objPropertySetupModel.setStrAdd2(bean.getStrAdd2());
		objPropertySetupModel.setStrCity(bean.getStrCity());
		objPropertySetupModel.setStrState(bean.getStrState());
		objPropertySetupModel.setStrCountry(bean.getStrCountry());
		objPropertySetupModel.setStrPin(bean.getStrPin());
		/*
		 * Billing Address
		 */
		objPropertySetupModel.setStrBAdd1(bean.getStrBAdd1());
		objPropertySetupModel.setStrBAdd2(bean.getStrBAdd2());
		objPropertySetupModel.setStrBCity(bean.getStrBCity());
		objPropertySetupModel.setStrBState(bean.getStrBState());
		objPropertySetupModel.setStrBCountry(bean.getStrBCountry());
		objPropertySetupModel.setStrBPin(bean.getStrBPin());
		/*
		 * Shipping Address
		 */
		objPropertySetupModel.setStrSAdd1(bean.getStrSAdd1());
		objPropertySetupModel.setStrSAdd2(bean.getStrSAdd2());
		objPropertySetupModel.setStrSCity(bean.getStrSCity());
		objPropertySetupModel.setStrSState(bean.getStrSState());
		objPropertySetupModel.setStrSCountry(bean.getStrSCountry());
		objPropertySetupModel.setStrSPin(bean.getStrSPin());
		/*
		 * Others Company Tab
		 */
		objPropertySetupModel.setStrPhone(bean.getStrPhone());
		objPropertySetupModel.setStrFax(bean.getStrFax());
		objPropertySetupModel.setStrEmail(bean.getStrEmail());
		objPropertySetupModel.setStrWebsite(bean.getStrWebsite());
		objPropertySetupModel.setIntDueDays(bean.getIntDueDays());
		objPropertySetupModel.setStrMask(bean.getStrMask());
		objPropertySetupModel.setStrCST(bean.getStrCST());
		objPropertySetupModel.setStrVAT(bean.getStrVAT());
		objPropertySetupModel.setStrSerTax(bean.getStrSerTax());
		objPropertySetupModel.setStrPanNo(bean.getStrPanNo());
		objPropertySetupModel.setStrLocCode(bean.getStrLocCode());
		objPropertySetupModel.setStrAsseeCode(bean.getStrAsseeCode());
		objPropertySetupModel.setStrPurEmail(bean.getStrPurEmail());
		objPropertySetupModel.setStrSaleEmail(bean.getStrSaleEmail());
		objPropertySetupModel.setStrRangeAdd(bean.getStrRangeAdd());
		objPropertySetupModel.setStrRangeDiv(bean.getStrRangeDiv());
		objPropertySetupModel.setStrCommi(bean.getStrCommi());
		objPropertySetupModel.setStrRegNo(bean.getStrRegNo());
		objPropertySetupModel.setStrDivision(bean.getStrDivision());
		objPropertySetupModel.setDblBondAmt(bean.getDblBondAmt());
		objPropertySetupModel.setStrAcceptanceNo(bean.getStrAcceptanceNo());
		objPropertySetupModel.setStrDivisionAdd(bean.getStrDivisionAdd());
		objPropertySetupModel.setStrECCNo(bean.getStrECCNo());

		/*
		 * General Tab
		 */
		objPropertySetupModel.setStrNegStock(bean.getStrNegStock());
		objPropertySetupModel.setStrPOBOM(bean.getStrPOBOM());
		objPropertySetupModel.setStrSOBOM(bean.getStrSOBOM());
		objPropertySetupModel.setStrTotalWorkhour(bean.getStrTotalWorkhour());
		objPropertySetupModel.setDtFromTime(bean.getDtFromTime());
		objPropertySetupModel.setDtToTime(bean.getDtToTime());
		objPropertySetupModel.setStrWorkFlowbasedAuth(bean.getStrWorkFlowbasedAuth());
		objPropertySetupModel.setIntdec(bean.getIntdec());
		objPropertySetupModel.setIntqtydec(bean.getIntqtydec());
		objPropertySetupModel.setStrListPriceInPO(bean.getStrListPriceInPO());
		objPropertySetupModel.setStrCMSModule(bean.getStrCMSModule());
		objPropertySetupModel.setStrBatchMethod(bean.getStrBatchMethod());
		objPropertySetupModel.setStrTPostingType(bean.getStrTPostingType());
		objPropertySetupModel.setStrAutoDC(bean.getStrAutoDC());
		objPropertySetupModel.setStrAudit(bean.getStrAudit());
		objPropertySetupModel.setStrRatePickUpFrom(bean.getStrRatePickUpFrom());
		objPropertySetupModel.setStrShowReqVal(bean.getStrShowReqVal());
		objPropertySetupModel.setStrShowStkReq(bean.getStrShowStkReq());
		objPropertySetupModel.setStrShowValMISSlip(bean.getStrShowValMISSlip());
		objPropertySetupModel.setStrChangeUOMTrans(bean.getStrChangeUOMTrans());
		objPropertySetupModel.setStrShowProdMaster(bean.getStrShowProdMaster());
		objPropertySetupModel.setStrShowProdDoc(bean.getStrShowProdDoc());
		objPropertySetupModel.setStrAllowDateChangeInMIS(bean.getStrAllowDateChangeInMIS());
		objPropertySetupModel.setStrShowTransAsc_Desc(bean.getStrShowTransAsc_Desc());
		objPropertySetupModel.setStrNameChangeProdMast(bean.getStrNameChangeProdMast());
		objPropertySetupModel.setStrStkAdjReason(bean.getStrStkAdjReason());
		objPropertySetupModel.setIntNotificationTimeinterval(bean.getIntNotificationTimeinterval());
		objPropertySetupModel.setStrMonthEnd(bean.getStrMonthEnd());
		objPropertySetupModel.setStrShowAllProdToAllLoc(bean.getStrShowAllProdToAllLoc());
		objPropertySetupModel.setStrLocWiseProductionOrder(bean.getStrLocWiseProductionOrder());
		objPropertySetupModel.setStrShowAvgQtyInOP(objGlobal.funIfNull(bean.getStrShowAvgQtyInOP(), "N", bean.getStrShowAvgQtyInOP()));
		objPropertySetupModel.setStrShowStockInOP(objGlobal.funIfNull(bean.getStrShowStockInOP(), "N", bean.getStrShowStockInOP()));
		objPropertySetupModel.setStrShowAvgQtyInSO(objGlobal.funIfNull(bean.getStrShowAvgQtyInSO(), "N", bean.getStrShowAvgQtyInSO()));
		objPropertySetupModel.setStrShowStockInSO(objGlobal.funIfNull(bean.getStrShowStockInSO(), "N", bean.getStrShowStockInSO()));
		objPropertySetupModel.setStrEffectOfDiscOnPO(objGlobal.funIfNull(bean.getStrEffectOfDiscOnPO(), "N", bean.getStrEffectOfDiscOnPO()));
		objPropertySetupModel.setStrInvFormat(bean.getStrInvFormat());
		objPropertySetupModel.setStrInvNote(bean.getStrInvNote());
		objPropertySetupModel.setStrCurrencyCode(bean.getStrCurrencyCode());
		/*
		 * if(bean.isStrShowAllPropCustomer()==true) {
		 * objPropertySetupModel.setStrShowAllPropCustomer("Y"); }else {
		 * objPropertySetupModel.setStrShowAllPropCustomer("N"); }
		 */
		objPropertySetupModel.setStrShowAllPropCustomer(funGetYN(bean.isStrShowAllPropCustomer()));
		// objPropertySetupModel.setStrShowAllPropCustomer(objGlobal.funIfNull(bean.getStrShowAllPropCustomer(),"N",bean.getStrShowAllPropCustomer()));
		objPropertySetupModel.setStrEffectOfInvoice(bean.getStrEffectOfInvoice());
		objPropertySetupModel.setStrEffectOfGRNWebBook(bean.getStrEffectOfGRNWebBook());
		objPropertySetupModel.setStrMultiCurrency(bean.getStrMultiCurrency());
		objPropertySetupModel.setStrShowAllPartyToAllLoc(objGlobal.funIfNull(bean.getStrShowAllPartyToAllLoc(), "N", bean.getStrShowAllPartyToAllLoc()));
		objPropertySetupModel.setStrShowAllTaxesOnTransaction(bean.getStrShowAllTaxesOnTransaction());

		if (bean.isStrSOKOTPrint())
		{
			objPropertySetupModel.setStrSOKOTPrint("Y");
		}
		else
		{
			objPropertySetupModel.setStrSOKOTPrint("N");
		}
		objPropertySetupModel.setStrRateHistoryFormat(bean.getStrRateHistoryFormat());
		objPropertySetupModel.setStrPOSlipFormat(bean.getStrPOSlipFormat());
		objPropertySetupModel.setStrSRSlipFormat(bean.getStrSRSlipFormat());

		/*
		 * Bank Tab
		 */
		objPropertySetupModel.setStrBankName(bean.getStrBankName());
		objPropertySetupModel.setStrBranchName(bean.getStrBranchName());
		objPropertySetupModel.setStrBankAdd1(bean.getStrBAdd1());
		objPropertySetupModel.setStrBankAdd2(bean.getStrBAdd2());
		objPropertySetupModel.setStrBankCity(bean.getStrBankCity());
		objPropertySetupModel.setStrBankAccountNo(bean.getStrBankAccountNo());
		objPropertySetupModel.setStrSwiftCode(bean.getStrSwiftCode());

		/*
		 * Supplier Performance Tab
		 */
		objPropertySetupModel.setStrLate(bean.getStrLate());
		objPropertySetupModel.setStrRej(bean.getStrRej());
		;
		objPropertySetupModel.setStrPChange(bean.getStrPChange());
		objPropertySetupModel.setStrExDelay(bean.getStrExDelay());

		/*
		 * SMS Tab
		 */
		objPropertySetupModel.setStrSMSProvider(bean.getStrSMSProvider());
		objPropertySetupModel.setStrSMSAPI(bean.getStrSMSAPI());
		objPropertySetupModel.setStrSMSContent(bean.getStrSMSContent());

		objPropertySetupModel.setDtCreatedDate(ob.funGetCurrentDateTime("yyyy-MM-dd"));
		objPropertySetupModel.setDtLastModified(ob.funGetCurrentDateTime("yyyy-MM-dd"));
		objPropertySetupModel.setStrUserCreated(string);
		objPropertySetupModel.setStrUserModified(string);
		objPropertySetupModel.setClientCode(req.getSession().getAttribute("clientCode").toString());
		List auditFormList = bean.getListAuditForm();
		String AuditFrom = "";
		if (auditFormList != null)
		{
			for (int i = 0; i < auditFormList.size(); i++)
			{
				clsTreeMasterModel objTemp = (clsTreeMasterModel) auditFormList.get(i);

				if (null != objTemp.getStrAuditForm() && objTemp.getStrAuditForm().equalsIgnoreCase("on"))
				{
					AuditFrom = AuditFrom + "," + objTemp.getStrFormName();
				}
			}
			objPropertySetupModel.setStrAuditFrom(AuditFrom);
		}
		else
		{
			objPropertySetupModel.setStrAuditFrom("");
		}
		return objPropertySetupModel;
	}

	private List<clsTreeMasterModel> funSetSeletedProcess(List<clsProcessSetupModel> listclsProcessSetupModel)
	{
        Map<String,clsTreeMasterModel> hmProcessMap=new TreeMap<>();
		ArrayList<clsTreeMasterModel> listProcessForms1 = new ArrayList<clsTreeMasterModel>();

		for (int i = 0; i < listclsProcessSetupModel.size(); i++)
		{
			clsTreeMasterModel objTemp = new clsTreeMasterModel();
			clsProcessSetupModel obj = (clsProcessSetupModel) listclsProcessSetupModel.get(i);
			String processes = obj.getStrProcess();
			String formName = obj.getStrForm();
			String formDesc = obj.getStrFormDesc();
			if(hmProcessMap.containsKey(formName))
			{
				
			}
			else
			{
				objTemp.setStrFormName(formName);
				objTemp.setStrFormDesc(formDesc);
				
				if (processes.length() > 0)
				{
					String str = processes;
					String delimiter = ",";
					String[] temp;
					temp = str.split(delimiter);
					for (int j = 0; j < temp.length; j++)
					{
						String test = temp[j];
						switch (test)
						{

						case "Sales Order":
							objTemp.setStrSalesOrder(test);
							break;

						case "Production Order":
							objTemp.setStrProductionOrder(test);
							break;

						case "Direct":
							objTemp.setStrDirect(test);
							break;

						case "Minimum Level":
							objTemp.setStrMinimumLevel(test);
							break;

						case "Requisition":
							objTemp.setStrRequisition(test);
							break;

						case "Purchase Indent":
							objTemp.setStrPurchaseIndent(test);
							break;

						case "Service Order":
							objTemp.setStrSalesOrder(test);
							break;

						case "Work Order":
							objTemp.setStrWorkOrder(test);
							break;

						case "Project":
							objTemp.setStrProject(test);
							break;

						case "Purchase Order":
							objTemp.setStrPurchaseOrder(test);
							break;

						case "Purchase Return":
							objTemp.setStrPurchaseReturn(test);
							break;

						case "Sales Return":
							objTemp.setStrSalesReturn(test);
							break;

						case "Rate Contractor":
							objTemp.setStrRateContractor(test);
							break;

						case "GRN":
							objTemp.setStrGRN(test);
							break;

						case "Delivery Note":
							objTemp.setStrDeliveryNote(test);
							break;

						case "Opening Stock":
							objTemp.setStrOpeningStock(test);
							break;

						case "Sub Contractor GRN":
							objTemp.setStrSubContractorGRN(test);
							break;

						case "Sales Projection":
							objTemp.setStrSalesProjection(test);
							break;

						case "MIS":
							objTemp.setStrMIS(test);
							break;

						case "Invoice":
							objTemp.setStrInvoice(test);
							break;

						case "Delivery Schedule":
							objTemp.setStrDeliverySchedule(test);
							break;

						}

					}

				}
				listProcessForms1.add(objTemp);
				hmProcessMap.put(formName,objTemp);
			}
		}
		return listProcessForms1;

	}
	public void funSaveProcessSetupForm(List<clsTreeMasterModel> listForms, String propertyCode, String clientCode)
	{
		for (int i = 0; i < listForms.size(); i++)
		{
			clsProcessSetupModel objProcessSetupModel = new clsProcessSetupModel();
			String Process = "";
			String formName;
			String formDescription;
			clsTreeMasterModel objTemp = (clsTreeMasterModel) listForms.get(i);
			System.out.println("Form Desc=" + objTemp.getStrFormDesc());

			if (null != objTemp.getStrSalesOrder())
			{
				Process = objTemp.getStrSalesOrder();
			}

			if (null != objTemp.getStrProductionOrder())
			{
				Process += "," + objTemp.getStrProductionOrder();
			}

			if (null != objTemp.getStrMinimumLevel())
			{
				Process += "," + objTemp.getStrMinimumLevel();
			}

			if (null != objTemp.getStrRequisition())
			{
				Process += "," + objTemp.getStrRequisition();

			}

			if (null != objTemp.getStrDirect())
			{
				Process += "," + objTemp.getStrDirect();
			}

			if (null != objTemp.getStrPurchaseIndent())
			{
				Process += "," + objTemp.getStrPurchaseIndent();
			}

			if (null != objTemp.getStrServiceOrder())
			{
				Process += "," + objTemp.getStrServiceOrder();
			}

			if (null != objTemp.getStrWorkOrder())
			{
				Process += "," + objTemp.getStrWorkOrder();
			}

			if (null != objTemp.getStrProject())
			{
				Process += "," + objTemp.getStrProject();
			}

			if (null != objTemp.getStrPurchaseOrder())
			{
				Process += "," + objTemp.getStrPurchaseOrder();
			}

			if (null != objTemp.getStrPurchaseReturn())
			{
				Process += "," + objTemp.getStrPurchaseReturn();
			}

			if (null != objTemp.getStrSalesReturn())
			{
				Process += "," + objTemp.getStrSalesReturn();
			}

			if (null != objTemp.getStrRateContractor())
			{
				Process += "," + objTemp.getStrRateContractor();
			}

			if (null != objTemp.getStrGRN())
			{
				Process += "," + objTemp.getStrGRN();
			}

			if (null != objTemp.getStrDeliveryNote())
			{
				Process += "," + objTemp.getStrDeliveryNote();
			}

			if (null != objTemp.getStrOpeningStock())
			{
				Process += "," + objTemp.getStrOpeningStock();
			}

			if (null != objTemp.getStrSubContractorGRN())
			{
				Process += "," + objTemp.getStrSubContractorGRN();
			}

			if (null != objTemp.getStrSalesProjection())
			{
				Process += "," + objTemp.getStrSalesProjection();
			}

			if (null != objTemp.getStrInvoice())
			{
				Process += "," + objTemp.getStrInvoice();
			}

			if (null != objTemp.getStrMIS())
			{
				Process += "," + objTemp.getStrMIS();
			}

			if (null != objTemp.getStrDeliverySchedule())
			{
				Process += "," + objTemp.getStrDeliverySchedule();
			}

			if (",".equals(Process.trim()))
			{
				Process = "";
			}

			formName = objTemp.getStrFormName();
			formDescription = objTemp.getStrFormDesc();
			objProcessSetupModel.setStrForm(formName);
			objProcessSetupModel.setStrFormDesc(formDescription);
			objProcessSetupModel.setStrProcess(Process);
			objProcessSetupModel.setStrClientCode(clientCode);
			objProcessSetupModel.setStrPropertyCode(propertyCode);

			objSetupMasterService.funAddUpdateProcessSetup(objProcessSetupModel);

		}

	}

	/*
	*/
	public static String[] mySplit(String text, String delemeter)
	{
		java.util.List<String> parts = new java.util.ArrayList<String>();

		text += delemeter;

		for (int i = text.indexOf(delemeter), j = 0; i != -1;)
		{
			parts.add(text.substring(j, i));
			j = i + delemeter.length();
			i = text.indexOf(delemeter, j);
		}

		return parts.toArray(new String[0]);
	}

	@RequestMapping(value = "/getCompanyLogo", method = RequestMethod.GET)
	public void getImage(HttpServletRequest req, HttpServletResponse response)
	{
		String strCompanyCode = req.getSession().getAttribute("companyCode").toString();
		clsCompanyLogoModel objSetup = objSetupMasterService.funGetCompanyLogoObject(strCompanyCode);
		try
		{
			Blob image = null;
			byte[] imgData = null;
			image = objSetup.getStrCompanyLogo();
			if (null != image && image.length() > 0)
			{
				imgData = image.getBytes(1, (int) image.length());
				response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
				OutputStream o = response.getOutputStream();
				o.write(imgData);
				o.flush();
				o.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public boolean funGetBoolean(String value)
	{
		if (value == null || value.equalsIgnoreCase("N") || value.equalsIgnoreCase("No"))
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public String funGetYesNo(boolean value)
	{
		if (value == false)
		{
			return "No";
		}
		else
		{
			return "Yes";
		}
	}

	public String funGetYN(boolean value)
	{
		if (value == false)
		{
			return "N";
		}
		else
		{
			return "Y";
		}
	}

}
