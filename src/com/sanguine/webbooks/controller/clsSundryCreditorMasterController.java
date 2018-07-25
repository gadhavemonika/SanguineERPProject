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
import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webbooks.bean.clsSundryCreditorBean;
import com.sanguine.webbooks.model.clsSundaryCreditorMasterModel;
import com.sanguine.webbooks.model.clsSundryCreditorMasterModel_ID;
import com.sanguine.webbooks.model.clsSundryCreditorOpeningBalMasterModel;
import com.sanguine.webbooks.model.clsWebBooksAccountMasterModel;
import com.sanguine.webbooks.service.clsSundryCreditorMasterService;
import com.sanguine.webbooks.service.clsWebBooksAccountMasterService;

@Controller
public class clsSundryCreditorMasterController {

	@Autowired
	private clsSundryCreditorMasterService objSundryCreditorMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;
	
	@Autowired
	private clsWebBooksAccountMasterService objWebBooksAccountMasterService;

	@Autowired
	private clsCurrencyMasterService objCurrencyMasterService;


	@RequestMapping(value = "/frmSundryCreditorMaster", method = RequestMethod.GET)
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

		Map<String, String> hmCurrency = objCurrencyMasterService.funCurrencyListToDisplay(clientCode);
		if (hmCurrency.isEmpty()) {
			hmCurrency.put("", "");
		}
		
		
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
			return new ModelAndView("frmSundryCreditorMaster", "command", new clsSundaryCreditorMasterModel());
		} else {
			return new ModelAndView("frmSundryCreditorMaster_1", "command", new clsSundaryCreditorMasterModel());
		}
	}

	// Save or Update SundryCreditorMaster
	@RequestMapping(value = "/saveSundryCreditorMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsSundryCreditorBean objBean, BindingResult result, HttpServletRequest req) {
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
			
			clsSundaryCreditorMasterModel objModel = funPrepareModel(objBean, userCode, clientCode, propertyCode);

			List<clsSundryCreditorOpeningBalMasterModel> list = objBean.getListSundryCreditorOpenongBalModel();
			clsSundryCreditorOpeningBalMasterModel obj = new clsSundryCreditorOpeningBalMasterModel();
			List<clsSundryCreditorOpeningBalMasterModel> listOfOpeningBal = new ArrayList<clsSundryCreditorOpeningBalMasterModel>();
			
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
					obj = (clsSundryCreditorOpeningBalMasterModel) list.get(i);
					obj.setStrAccountCode(obj.getStrAccountCode());
					obj.setStrAccountName(obj.getStrAccountName());
					obj.setDblOpeningbal(String.valueOf(Double.parseDouble(obj.getDblOpeningbal())*currValue));
					obj.setStrCrDr(obj.getStrCrDr());
					listOfOpeningBal.add(obj);
				}
				objModel.setListSundryCreditorOpenongBalModel(listOfOpeningBal);
			}

			objSundryCreditorMasterService.funAddUpdateSundryCreditorMaster(objModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Creditor Code : ".concat(objModel.getStrCreditorCode()));

			return new ModelAndView("redirect:/frmSundryCreditorMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmSundryCreditorMaster.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsSundaryCreditorMasterModel funPrepareModel(clsSundryCreditorBean objBean, String userCode, String clientCode, String propertyCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsSundaryCreditorMasterModel objModel;
		if (objBean.getStrCreditorCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblsundaryCreditormaster", "CreditorMaster", "intGId", clientCode);
			String CreditorCode = "C" + String.format("%08d", lastNo);
			objModel = new clsSundaryCreditorMasterModel(new clsSundryCreditorMasterModel_ID(CreditorCode, clientCode));
			objModel.setIntGId(lastNo);

		} else {
			objModel = new clsSundaryCreditorMasterModel(new clsSundryCreditorMasterModel_ID(objBean.getStrCreditorCode(), clientCode));
		}
		/* setting main data */
		objModel.setStrPrefix(objBean.getStrPrefix());
		objModel.setStrFirstName(objBean.getStrFirstName());
		objModel.setStrMiddleName(objBean.getStrMiddleName());
		objModel.setStrLastName(objBean.getStrLastName());
		objModel.setStrCategoryCode(objBean.getStrCategoryCode());
		objModel.setStrCreditorFullName(objBean.getStrFirstName()+" "+objBean.getStrMiddleName()+" "+objBean.getStrLastName());
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
	//	objModel.setStrCreditorFullName(objBean.getStrCreditorFullName());
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
		objModel.setStrCreditorStatusCode(objBean.getStrCreditorStatusCode());
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

	// Load Master Data On Form
	@RequestMapping(value = "/loadSundryCreditorMasterData", method = RequestMethod.GET)
	public @ResponseBody clsSundaryCreditorMasterModel funAssignFields(@RequestParam("creditorCode") String CreditorCode, HttpServletRequest req) {
		clsSundaryCreditorMasterModel objModel = null;
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		objModel = objSundryCreditorMasterService.funGetSundryCreditorMaster(CreditorCode, clientCode);
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
				
		String sqlQuery = "select a.strAccountCode,a.strAccountName,a.dblOpeningbal /"+currValue+",a.strCrDr " + "FROM tblsundarycreditoropeningbalance a " + "where a.strCreditorCode = '" + CreditorCode + "'  and a.strClientCode='" + clientCode + "'";

		List listProperty = objGlobalFunctionsService.funGetListModuleWise(sqlQuery, "sql");

		objModel.setListSundryCreditorOpenongBalModel(listProperty);

		if (null == objModel) {
			objModel = new clsSundaryCreditorMasterModel();
			objModel.setStrCreditorCode("Invalid Code");
		}
		return objModel;
	}

}
