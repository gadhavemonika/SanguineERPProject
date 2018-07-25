package com.sanguine.webbooks.controller;

import java.util.ArrayList;
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

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.excise.model.clsCategoryMasterModel;
import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webbooks.bean.clsChargeMasterBean;
import com.sanguine.webbooks.bean.clsSundryDebtorMasterBean;
import com.sanguine.webbooks.model.clsACGroupMasterModel;
import com.sanguine.webbooks.model.clsChargeMasterModel;
import com.sanguine.webbooks.model.clsChargeMasterModel_ID;
import com.sanguine.webbooks.model.clsSundaryCreditorMasterModel;
import com.sanguine.webbooks.model.clsSundryDebtorMasterModel;
import com.sanguine.webbooks.model.clsSundryDebtorMasterModel_ID;
import com.sanguine.webbooks.model.clsSundryDebtorOpeningBalMasterModel;
import com.sanguine.webbooks.model.clsWebBooksAccountMasterModel;
import com.sanguine.webbooks.service.clsSundryDebtorMasterService;
import com.sanguine.webbooks.service.clsWebBooksAccountMasterService;

@Controller
public class clsSundryDebtorMasterController {

	@Autowired
	private clsSundryDebtorMasterService objSundryDebtorMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;
	
	@Autowired
	private clsWebBooksAccountMasterService objWebBooksAccountMasterService;

	@Autowired
	private clsCurrencyMasterService objCurrencyMasterService;

	// Open SundryDebtorMaster
	@RequestMapping(value = "/frmSundryDebtorMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		ArrayList<String> listMrMrs = new ArrayList<String>();
		listMrMrs.add("Mr.");
		listMrMrs.add("Mrs.");

		ArrayList<String> listBlocked = new ArrayList<String>();
		listBlocked.add("Yes");
		listBlocked.add("No");

		ArrayList<String> listAMCCycle = new ArrayList<String>();
		listAMCCycle.add("Monthly");
		listAMCCycle.add("Yearly");

		ArrayList<String> listECSYN = new ArrayList<String>();
		listECSYN.add("Yes");
		listECSYN.add("No");

		ArrayList<String> listECSActivated = new ArrayList<String>();
		listECSActivated.add("Yes");
		listECSActivated.add("No");

		ArrayList<String> listAccountType = new ArrayList<String>();
		listAccountType.add("Saving");
		listAccountType.add("Current");

		ArrayList<String> listDrCr = new ArrayList<String>();
		listDrCr.add("Dr");
		listDrCr.add("Cr");
		
		Map<String, String> hmCurrency = objCurrencyMasterService.funCurrencyListToDisplay(clientCode);
		if (hmCurrency.isEmpty()) {
			hmCurrency.put("", "");
		}
		
		ArrayList<String> listOperational = new ArrayList<String>();
		listOperational.add("Yes");
		listOperational.add("No");

		model.put("urlHits", urlHits);
		model.put("listMrMrs", listMrMrs);
		model.put("listBlocked", listBlocked);
		model.put("listCurrencyType", hmCurrency);
		model.put("listAMCCycle", listAMCCycle);
		model.put("listECSYN", listECSYN);
		model.put("listAccountType", listAccountType);
		model.put("listECSActivated", listECSActivated);
		model.put("listDrCr", listDrCr);
		model.put("listOperational", listOperational);

		if (urlHits.equalsIgnoreCase("1")) {
			return new ModelAndView("frmSundryDebtorMaster", "command", new clsSundryDebtorMasterModel());
		} else {
			return new ModelAndView("frmSundryDebtorMaster_1", "command", new clsSundryDebtorMasterModel());
		}
	}

	// Load Master Data On Form
	@RequestMapping(value = "/loadSundryDebtorMasterData", method = RequestMethod.GET)
	public @ResponseBody clsSundryDebtorMasterModel funAssignFields(@RequestParam("debtorCode") String debtorCode, HttpServletRequest req) {
		clsSundryDebtorMasterModel objModel = null;
		clsSundryDebtorOpeningBalMasterModel obj = new clsSundryDebtorOpeningBalMasterModel();
		List<clsSundryDebtorOpeningBalMasterModel> list = new ArrayList<clsSundryDebtorOpeningBalMasterModel>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		objModel = objSundryDebtorMasterService.funGetSundryDebtorMaster(debtorCode, clientCode);
		double currValue = Double.parseDouble(req.getSession().getAttribute("currValue").toString());
		if(objModel!=null)
		{
			List listModel = objWebBooksAccountMasterService.funGetWebBooksAccountMaster(objModel.getStrAccountCode(), clientCode);
			if(!listModel.isEmpty()){
				Object objects[] = (Object[]) listModel.get(0);

				clsWebBooksAccountMasterModel objAccModel = (clsWebBooksAccountMasterModel) objects[0];
				objModel.setStrAccountName(objAccModel.getStrAccountName());
				
			}else{
				objModel.setStrAccountName("");
			}
			
			if(objModel.getStrCurrencyType()!=null){
				if(objModel.getDblConversion()==0){
					currValue=1;
				}else{
					currValue=objModel.getDblConversion();	
				}
			}
		}
		
		
		String sqlQuery = "select a.strAccountCode,a.strAccountName,a.dblOpeningbal /"+currValue+",a.strCrDr " + "FROM tblsundarydebtoropeningbalance a " + "where a.strDebtorCode = '" + debtorCode + "'  and a.strClientCode='" + clientCode + "'";

		List listProperty = objGlobalFunctionsService.funGetListModuleWise(sqlQuery, "sql");

		objModel.setListSundryDetorOpenongBalModel(listProperty);

		if (null == objModel) {
			objModel = new clsSundryDebtorMasterModel();
			objModel.setStrDebtorCode("Invalid Code");
		}
		return objModel;
	}

	// Save or Update SundryDebtorMaster
	@RequestMapping(value = "/saveSundryDebtorMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsSundryDebtorMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		double currValue=1;
		try {
			urlHits = req.getParameter("saddr").toString();
			
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			clsSundryDebtorMasterModel objModel = funPrepareModel(objBean, userCode, clientCode, propertyCode);
			List<clsSundryDebtorOpeningBalMasterModel> list = objBean.getListSundryDetorOpenongBalModel();
			clsSundryDebtorOpeningBalMasterModel obj = new clsSundryDebtorOpeningBalMasterModel();
			List<clsSundryDebtorOpeningBalMasterModel> listOfOpeningBal = new ArrayList<clsSundryDebtorOpeningBalMasterModel>();
			
			if(objBean.getStrCurrencyType()!=null){
				clsCurrencyMasterModel obCurrModel=objCurrencyMasterService.funGetCurrencyMaster(objBean.getStrCurrencyType(),clientCode);
				if(obCurrModel!=null){
					currValue=obCurrModel.getDblConvToBaseCurr();
					objModel.setDblConversion(currValue);
				}
			}else{
				objModel.setDblConversion(currValue);
			}
			if (!list.equals(null)) {
				for (int i = 1; i < list.size(); i++) {
					obj = (clsSundryDebtorOpeningBalMasterModel) list.get(i);
					obj.setStrAccountCode(obj.getStrAccountCode());
					obj.setStrAccountName(obj.getStrAccountName());
					obj.setDblOpeningbal(String.valueOf(Double.parseDouble(obj.getDblOpeningbal())*currValue));
					obj.setStrCrDr(obj.getStrCrDr());
					listOfOpeningBal.add(obj);
				}
				objModel.setListSundryDetorOpenongBalModel(listOfOpeningBal);
			}

			objSundryDebtorMasterService.funAddUpdateSundryDebtorMaster(objModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Debtor Code : ".concat(objModel.getStrDebtorCode()));

			return new ModelAndView("redirect:/frmSundryDebtorMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmSundryDebtorMaster.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsSundryDebtorMasterModel funPrepareModel(clsSundryDebtorMasterBean objBean, String userCode, String clientCode, String propertyCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsSundryDebtorMasterModel objModel;
		if (objBean.getStrDebtorCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblsundarydebtormaster", "DebtorMaster", "intGId", clientCode);
			String debtorCode = "D" + String.format("%08d", lastNo);
			objModel = new clsSundryDebtorMasterModel(new clsSundryDebtorMasterModel_ID(debtorCode, clientCode));
			objModel.setIntGId(lastNo);

		} else {
			objModel = new clsSundryDebtorMasterModel(new clsSundryDebtorMasterModel_ID(objBean.getStrDebtorCode(), clientCode));
		}
		/* setting main data */
		objModel.setStrPrefix(objBean.getStrPrefix());
		objModel.setStrFirstName(objBean.getStrFirstName());
		objModel.setStrMiddleName(objBean.getStrMiddleName());
		objModel.setStrLastName(objBean.getStrLastName());
		objModel.setStrCategoryCode(objBean.getStrCategoryCode());
		objModel.setStrDebtorFullName(objBean.getStrFirstName()+" "+objBean.getStrMiddleName()+" "+objBean.getStrLastName());
		
		/* setting main data */

		objModel.setStrAddressLine1(objBean.getStrAddressLine1());
		objModel.setStrAddressLine2(objBean.getStrAddressLine2());
		objModel.setStrAddressLine3(objBean.getStrAddressLine3());
		objModel.setStrBlocked(objBean.getStrBlocked());
		objModel.setStrCity(objBean.getStrCity());
		objModel.setLongZipCode(objBean.getLongZipCode());
		objModel.setStrTelNo1(objBean.getStrTelNo1());
		objModel.setStrTelNo2(objBean.getStrTelNo2());
		objModel.setStrFax(objBean.getStrFax());
		objModel.setStrArea(objBean.getStrArea());
		objModel.setStrEmail(objBean.getStrEmail());
		objModel.setStrContactPerson1(objBean.getStrContactPerson1());
		objModel.setStrContactDesignation1(objBean.getStrContactDesignation1());
		objModel.setStrContactEmail1(objBean.getStrContactEmail1());
		objModel.setStrContactTelNo1(objBean.getStrContactTelNo1());
		objModel.setStrContactPerson2(objBean.getStrContactPerson2());
		objModel.setStrContactDesignation2(objBean.getStrContactDesignation2());
		objModel.setStrContactEmail2(objBean.getStrContactEmail2());
		objModel.setStrContactTelNo2(objBean.getStrContactTelNo2());
		objModel.setStrLandmark(objBean.getStrLandmark());
		//objModel.setStrDebtorFullName(objBean.getStrDebtorFullName());
		objModel.setStrExpired(objBean.getStrExpired());
		objModel.setStrExpiryReasonCode(objBean.getStrExpiryReasonCode());
		objModel.setStrECSYN(objBean.getStrECSYN());
		objModel.setStrAccountNo(objBean.getStrAccountNo());
		objModel.setStrHolderName(objBean.getStrHolderName());
		objModel.setStrMICRNo(objBean.getStrMICRNo());
		objModel.setDblECS(objBean.getDblECS());
		objModel.setStrSaveCurAccount(objBean.getStrSaveCurAccount());
		objModel.setStrAlternateCode(objBean.getStrAlternateCode());
		objModel.setDblOutstanding(objBean.getDblOutstanding());
		objModel.setStrStatus(objBean.getStrStatus());
		objModel.setIntDays1(objBean.getIntDays1());
		objModel.setIntDays2(objBean.getIntDays2());
		objModel.setIntDays3(objBean.getIntDays3());
		objModel.setIntDays4(objBean.getIntDays4());
		objModel.setIntDays5(objBean.getIntDays5());
		objModel.setDblCrAmt(objBean.getDblCrAmt());
		objModel.setDblDrAmt(objBean.getDblDrAmt());
		objModel.setDteLetterProcess(objBean.getDteLetterProcess());
		objModel.setStrReminder1(objBean.getStrReminder1());
		objModel.setStrReminder2(objBean.getStrReminder2());
		objModel.setStrReminder3(objBean.getStrReminder3());
		objModel.setStrReminder4(objBean.getStrReminder4());
		objModel.setStrReminder5(objBean.getStrReminder5());
		objModel.setDblLicenseFee(objBean.getDblLicenseFee());
		objModel.setDblAnnualFee(objBean.getDblAnnualFee());
		objModel.setStrRemarks(objBean.getStrRemarks());
		objModel.setStrClientApproval(objBean.getStrClientApproval());
		objModel.setStrAMCLink(objBean.getStrAMCLink());
		objModel.setStrCurrencyType(objBean.getStrCurrencyType());
		objModel.setStrAccountHolderCode(objBean.getStrAccountHolderCode());
		objModel.setStrAccountHolderName(objBean.getStrAccountHolderName());
		objModel.setStrAMCCycle(objBean.getStrAMCCycle());
		objModel.setDteStartDate(objBean.getDteStartDate());
		objModel.setStrAMCRemarks(objBean.getStrAMCRemarks());
		objModel.setStrClientComment(objBean.getStrClientComment());
		objModel.setStrBillingToCode(objBean.getStrBillingToCode());
		objModel.setDblAnnualFeeInCurrency(objBean.getDblAnnualFeeInCurrency());
		objModel.setDblLicenseFeeInCurrency(objBean.getDblLicenseFeeInCurrency());
		objModel.setStrState(objBean.getStrState());
		objModel.setStrRegion(objBean.getStrRegion());
		objModel.setStrCountry(objBean.getStrCountry());
		objModel.setStrConsolidated(objBean.getStrConsolidated());
		objModel.setIntCreditDays(objBean.getIntCreditDays());
		objModel.setStrDebtorStatusCode(objBean.getStrDebtorStatusCode());
		objModel.setLongMobileNo(objBean.getLongMobileNo());
		objModel.setStrECSActivate(objBean.getStrECSActivate());
		objModel.setStrReminderStatus1(objBean.getStrReminderStatus1());
		objModel.setDteRemainderDate1(objBean.getDteRemainderDate1());
		objModel.setStrReminderStatus2(objBean.getStrReminderStatus2());
		objModel.setDteRemainderDate2(objBean.getDteRemainderDate2());
		objModel.setStrReminderStatus3(objBean.getStrReminderStatus3());
		objModel.setDteRemainderDate3(objBean.getDteRemainderDate3());
		objModel.setStrReminderStatus4(objBean.getStrReminderStatus4());
		objModel.setDteRemainderDate4(objBean.getDteRemainderDate4());
		objModel.setStrReminderStatus5(objBean.getStrReminderStatus5());
		objModel.setDteRemainderDate5(objBean.getDteRemainderDate5());
		objModel.setStrAllInvoiceHeader(objBean.getStrAllInvoiceHeader());
		objModel.setStrAccountCode(objBean.getStrAccountCode());

		objModel.setStrClientCode(clientCode);
		objModel.setStrPropertyCode(propertyCode);
		objModel.setStrUserCreated(userCode);
		objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserEdited(userCode);
		objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrOperational(objBean.getStrOperational());

		return objModel;
	}

}
