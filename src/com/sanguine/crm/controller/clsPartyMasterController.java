package com.sanguine.crm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.sanguine.crm.bean.clsPartyMasterBean;
import com.sanguine.crm.model.clsPartyMasterModel;
import com.sanguine.crm.model.clsPartyMasterModel_ID;
import com.sanguine.crm.model.clsPartyTaxIndicatorDtlModel;
import com.sanguine.crm.service.clsPartyMasterService;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProdSuppMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsPropertyMaster;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsSupplierMasterModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsGroupMasterService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsPropertyMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webbooks.model.clsSundryDebtorMasterModel;
import com.sanguine.webbooks.model.clsSundryDebtorMasterModel_ID;
import com.sanguine.webbooks.service.clsSundryDebtorMasterService;

@Controller
public class clsPartyMasterController {
	
	@Autowired
	private clsPartyMasterService objPartyMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private clsGroupMasterService objGrpMasterService;
	
	@Autowired
	clsGlobalFunctions objGlobalFunctions;
	
	@Autowired
	private clsProductMasterService objProductMasterService;
	@Autowired
	private clsSundryDebtorMasterService objSundryDebtorMasterService;

	@Autowired
	private clsLocationMasterService objLocationMasterService;

	@Autowired
	private clsPropertyMasterService objPropertyMasterService;

	@Autowired
	clsCurrencyMasterService objCurrencyMasterService;
	
	@Autowired
	private intfBaseService objBaseService;

	Map<String,String> hmCategory=new HashMap<>();

	@RequestMapping(value = "/frmCustomerMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		String propertyCode = request.getSession().getAttribute("propertyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsPropertyMaster objMaster = objPropertyMasterService.funGetProperty(propertyCode, clientCode);
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		List<String> listType = new ArrayList<>();
		// listType.add("");
		listType.add("Local");
		listType.add("Foreign");
		model.put("typeList", listType);
		model.put("propertyCode", propertyCode);
		model.put("propertyName", objMaster.getPropertyName());


		/*List<String> listCategory = new ArrayList<>();
		// listCategory.add("");
		listCategory.add("Wholesale Dealer");
		listCategory.add("Industrial Consumer");
		listCategory.add("Government Department");
		listCategory.add("Bakery");
		listCategory.add("Retailor");
		model.put("categoryList", listCategory);
		*/
		
		try {
			List<String> listCategory=funGetCategoryList(clientCode);
			model.put("categoryList", listCategory);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Map<String,String> hmRegion=funGetRegionList(clientCode);
			model.put("hmRegion", hmRegion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<String> listPartyIndiacator = new ArrayList<>();
		listPartyIndiacator.add("");
		listPartyIndiacator.add("A");
		listPartyIndiacator.add("B");
		listPartyIndiacator.add("C");
		listPartyIndiacator.add("D");
		listPartyIndiacator.add("E");
		listPartyIndiacator.add("F");
		listPartyIndiacator.add("G");
		listPartyIndiacator.add("H");
		listPartyIndiacator.add("I");
		listPartyIndiacator.add("J");
		listPartyIndiacator.add("K");
		listPartyIndiacator.add("L");
		listPartyIndiacator.add("M");
		listPartyIndiacator.add("N");
		listPartyIndiacator.add("O");
		listPartyIndiacator.add("P");
		listPartyIndiacator.add("Q");
		listPartyIndiacator.add("R");
		listPartyIndiacator.add("S");
		listPartyIndiacator.add("T");
		listPartyIndiacator.add("U");
		listPartyIndiacator.add("V");
		listPartyIndiacator.add("W");
		listPartyIndiacator.add("X");
		listPartyIndiacator.add("Y");
		listPartyIndiacator.add("Z");
		model.put("partyIndicatorList", listPartyIndiacator);

		Map<String, String> hmCurrency = objCurrencyMasterService.funCurrencyListToDisplay(clientCode);
		if (hmCurrency.isEmpty()) {
			hmCurrency.put("", "");
		}
		model.put("currencyList", hmCurrency);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCustomerMaster_1", "command", new clsPartyMasterModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCustomerMaster", "command", new clsPartyMasterModel());
		} else {
			return null;
		}
	}

	// Save or Update group master function to save or update record of group
	// master into database and also validates all fields of form.
	@RequestMapping(value = "/saveCustomerMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPartyMasterBean objBean, BindingResult result, HttpServletRequest req, HttpServletResponse resp) {
		
		String urlHits = "1";
		String pNameMarathi = "";
		try {
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");
			urlHits = req.getParameter("saddr").toString();
			pNameMarathi = req.getParameter("txtPNameMarathi").toString();
			objBean.setStrPartyNameMarathi(pNameMarathi);
		} catch (Exception e) {
			e.printStackTrace();
			urlHits = "1";
		}
		clsSundryDebtorMasterModel objDebtorModel = null;
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		if (!result.hasErrors()) {
			clsPartyMasterModel objModel = funPrepareModel(objBean, userCode, clientCode);
			clsCompanyMasterModel objCompModel = objSetupMasterService.funGetObject(clientCode);
			// objModel.setStrDebtorCode("");
			if (objCompModel.getStrWebBookModule().equalsIgnoreCase("Yes")) {
				objDebtorModel = funPrepareDebtorModel(objBean, userCode, clientCode, propertyCode);
				objSundryDebtorMasterService.funAddUpdateSundryDebtorMaster(objDebtorModel);
				objModel.setStrDebtorCode(objDebtorModel.getStrDebtorCode());
			} else {
				objModel.setStrDebtorCode("");
			}
			objPartyMasterService.funAddUpdate(objModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Party Code : ".concat(objModel.getStrPCode()));
			return new ModelAndView("redirect:/frmCustomerMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmCustomerMaster?saddr=" + urlHits, "command", new clsPartyMasterModel());
		}
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadPartyMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPartyMasterModel funAssignFields(@RequestParam("partyCode") String PCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPartyMasterModel objModel = objPartyMasterService.funGetObject(PCode, clientCode);
		if (null == objModel) {
			objModel = new clsPartyMasterModel();
			objModel.setStrPCode("Invalid Code");
			return objModel;
		} else if (!objModel.getStrLocCode().equals("")) {
			clsLocationMasterModel objLocCode = objLocationMasterService.funGetObject(objModel.getStrLocCode(), clientCode);
			if (objLocCode != null) {
				objModel.setStrLocName(objLocCode.getStrLocName());
			}
		}
		objModel.setDtInstallions(objGlobalFunctions.funGetDate("yyyy-MM-dd", objModel.getDtInstallions()));
		return objModel;
	}

	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadCustomerTaxDtl", method = RequestMethod.GET)
	public @ResponseBody List funAssignFieldsForSupplierTaxDtl(@RequestParam("partyCode") String PCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String sql = "select a.strTaxCode,b.strTaxDesc from tblpartytaxdtl a,tbltaxhd b " + "where a.strTaxCode=b.strTaxCode and a.strPCode='" + PCode + "'";
		List arrList = objGlobalFunctionsService.funGetList(sql, clientCode);

		return arrList;
	}

	
	// Returns a single group master record by passing group code as primary
	// key. Also generates next Group Code if transaction is for Save Master
	private clsPartyMasterModel funPrepareModel(clsPartyMasterBean objBean, String userCode, String clientCode) {
		long lastNo = 0;
		clsPartyMasterModel objModel;
		if (objBean.getStrPCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblpartymaster", "PartyMaster", "intPid", clientCode);
			String PCode = "C" + String.format("%06d", lastNo);
			objModel = new clsPartyMasterModel(new clsPartyMasterModel_ID(PCode, clientCode));
			objModel.setIntPId(lastNo);
			objModel.setStrUserCreated(userCode);
			objModel.setDtCreatedDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsPartyMasterModel objModel1 = objPartyMasterService.funGetObject(objBean.getStrPCode(), clientCode);
			if (null == objModel1) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblpartymaster", "PartyMaster", "intPid", clientCode);
				String PCode = "C" + String.format("%06d", lastNo);
				objModel = new clsPartyMasterModel(new clsPartyMasterModel_ID(PCode, clientCode));
				objModel.setIntPId(lastNo);
				objModel.setStrUserCreated(userCode);
				objModel.setDtCreatedDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				objModel = new clsPartyMasterModel(new clsPartyMasterModel_ID(objBean.getStrPCode(), clientCode));
			}
		}
		objModel.setStrPName(objBean.getStrPName());
		objModel.setStrPhone(objBean.getStrPhone());
		objModel.setStrMobile(objBean.getStrMobile());
		objModel.setStrFax(objBean.getStrFax());
		objModel.setStrContact(objBean.getStrContact());
		objModel.setStrEmail(objBean.getStrEmail());

		objModel.setStrBankName(objBean.getStrBankName());
		objModel.setStrBankAdd1(objBean.getStrBankAdd1());
		objModel.setStrBankAdd2(objBean.getStrBankAdd2());
		objModel.setStrTaxNo1(objBean.getStrTaxNo1());
		objModel.setStrTaxNo2(objBean.getStrTaxNo2());
		objModel.setStrPmtTerms(objGlobalFunctions.funIfNull(objBean.getStrPmtTerms(), "", ""));
		objModel.setStrAcCrCode(objBean.getStrAcCrCode());
		objModel.setStrMAdd1(objBean.getStrMAdd1());
		objModel.setStrMAdd2(objBean.getStrMAdd2());
		objModel.setStrMCity(objBean.getStrMCity());
		objModel.setStrMPin(objBean.getStrMPin());
		objModel.setStrMState(objBean.getStrMState());
		objModel.setStrMCountry(objBean.getStrMCountry());
		objModel.setStrBAdd1(objBean.getStrBAdd1());
		objModel.setStrBAdd2(objBean.getStrBAdd2());
		objModel.setStrBCity(objBean.getStrBCity());
		objModel.setStrBPin(objBean.getStrBPin());
		objModel.setStrBState(objBean.getStrBState());
		objModel.setStrBCountry(objBean.getStrBCountry());
		objModel.setStrSAdd1(objBean.getStrSAdd1());
		objModel.setStrSAdd2(objBean.getStrSAdd2());
		objModel.setStrSCity(objBean.getStrSCity());
		objModel.setStrSPin(objBean.getStrSPin());
		objModel.setStrSState(objBean.getStrSState());
		objModel.setStrSCountry(objBean.getStrSCountry());
		objModel.setStrCST(objBean.getStrCST());
		objModel.setStrVAT(objBean.getStrVAT());
		objModel.setStrExcise(objBean.getStrExcise());
		objModel.setStrServiceTax(objBean.getStrServiceTax());
		objModel.setStrSubType(objGlobalFunctions.funIfNull(objBean.getStrSubType(), "", ""));
		objModel.setDtExpiryDate(objGlobalFunctions.funIfNull(objBean.getDtExpiryDate(), "2014-7-30", objBean.getDtExpiryDate()));
		objModel.setStrManualCode(objBean.getStrManualCode());
		objModel.setStrRegistration(objBean.getStrRegistration());
		objModel.setStrRange(objBean.getStrRange());
		objModel.setStrDivision(objBean.getStrDivision());
		objModel.setStrCommissionerate(objBean.getStrCommissionerate());
		objModel.setStrBankAccountNo(objBean.getStrBankAccountNo());
		objModel.setStrBankABANo(objBean.getStrBankABANo());
		objModel.setIntCreditDays(objBean.getIntCreditDays());
		objModel.setDblCreditLimit(objBean.getDblCreditLimit());
		objModel.setDblLatePercentage(objBean.getDblLatePercentage());
		objModel.setDblRejectionPercentage(objBean.getDblRejectPercentage());
		objModel.setStrIBANNo(objBean.getStrIBANNo());
		objModel.setStrSwiftCode(objBean.getStrSwiftCode());
		objModel.setStrCategory(objBean.getStrCategory());
		objModel.setStrExcisable("");
		objModel.setStrCategory(objGlobalFunctions.funIfNull(objBean.getStrCategory(), "", objBean.getStrCategory()));
		objModel.setStrUserModified(userCode);
		objModel.setDtLastModified(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setDtCreatedDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserCreated(userCode);
		objModel.setStrPartyType(objGlobalFunctions.funIfNull(objBean.getStrPartyType(), " ", objBean.getStrPartyType()));
		objModel.setStrPartyIndi(objBean.getStrPartyIndi());
		objModel.setDblDiscount(objBean.getDblDiscount());
		objModel.setStrOperational(objBean.getStrOperational());
		objModel.setStrECCNo(objBean.getStrECCNo());
		objModel.setStrPType("cust");
		objModel.setStrAccManager(objBean.getStrAccManager());
		objModel.setDtInstallions(objGlobalFunctions.funGetDate("yyyy-MM-dd", objBean.getDtInstallions()));
		objModel.setStrGSTNo(objBean.getStrGSTNo());
		if (objBean.getStrPartyNameMarathi() == null) {
			objModel.setStrPNHindi("");
		} else {
			objModel.setStrPNHindi(objBean.getStrPartyNameMarathi());
		}
		if (objBean.getStrDebtorCode() == null) {
			objModel.setStrDebtorCode("");
		}
		objModel.setStrLocCode(objBean.getStrLocCode());
		objModel.setStrPropCode(objBean.getStrPropCode());
		objModel.setStrCurrency(objGlobalFunctions.funIfNull(objBean.getStrCurrency(), " ", objBean.getStrCurrency()));
		objModel.setStrApplForWT(objBean.getStrApplForWT());
		objModel.setStrRegion(objBean.getStrRegion());
		List<clsPartyTaxIndicatorDtlModel> listPartyTaxDtl = new ArrayList<clsPartyTaxIndicatorDtlModel>();
		List<clsPartyTaxIndicatorDtlModel> arrListTemp = objBean.getListclsPartyTaxIndicatorDtlModel();
		if (arrListTemp != null) {
			Iterator<clsPartyTaxIndicatorDtlModel> it = arrListTemp.iterator();
			while (it.hasNext()) {
				clsPartyTaxIndicatorDtlModel Obj = it.next();
				if (null == Obj.getStrTaxCode()) {
					it.remove();
				} else {
					listPartyTaxDtl.add(Obj);
				}
			}
		}
		objModel.setArrListPartyTaxDtlModel(listPartyTaxDtl);
		objModel.setDblReturnDiscount(objBean.getDblReturnDiscount());
		List<clsProdSuppMasterModel> arrListProdSupp = objBean.getListclsProdSuppMasterModel();
		if (arrListProdSupp != null) {
			objProductMasterService.funDeleteSuppProds(objBean.getStrPCode(), clientCode);
			for (int i = 0; i < arrListProdSupp.size(); i++) {
				clsProdSuppMasterModel Obj = arrListProdSupp.get(i);
				Obj.setStrSuppCode(objModel.getStrPCode());
				Obj.setStrClientCode(clientCode);
				Obj.setDtLastDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
				if (Obj.getDblStandingOrder() != 0) {
					System.out.println(i + "    " + Obj.getStrProdCode() + "===");
					objProductMasterService.funAddUpdateProdSupplier(Obj);
				}
			}
		}
		return objModel;
	}

	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadAllProductData", method = RequestMethod.GET)
	public @ResponseBody List funLoadAllProduct(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		List arrList = objProductMasterService.funGetALLProducedlProduct(clientCode);

		return arrList;
	}

	
// Assign filed function to set data onto form for edit transaction.
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadPartyProdData", method = RequestMethod.GET)
	public @ResponseBody List funAssigndPartyProdFields(@RequestParam("partyCode") String PCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List listProdsupp = objProductMasterService.funGetProdSuppWaiseProdList(PCode, clientCode);

		List listGenProdsupp = new ArrayList<clsProdSuppMasterModel>();
		if (null != listProdsupp) {
			for (int i = 0; i < listProdsupp.size(); i++) {
				Object[] arrObj = (Object[]) listProdsupp.get(i);
				clsProductMasterModel objModel = objProductMasterService.funGetObject(arrObj[0].toString(), clientCode);
				clsProdSuppMasterModel obj = new clsProdSuppMasterModel();
				clsPartyMasterModel objCust = objPartyMasterService.funGetObject(PCode, clientCode);
				if (null != objModel) {
					obj.setStrSuppCode(PCode);
					obj.setStrSuppName(objCust.getStrPName());
					obj.setStrProdCode(arrObj[0].toString());
					obj.setStrProdName(objModel.getStrProdName());
					obj.setDblLastCost(Double.parseDouble(arrObj[1].toString()));
					obj.setDblMargin(Double.parseDouble(arrObj[2].toString()));
					obj.setDblStandingOrder(Double.parseDouble(arrObj[3].toString()));
					obj.setStrClientCode(clientCode);
					listGenProdsupp.add(obj);
				}
			}
		}
		return listGenProdsupp;
	}

	
	@RequestMapping(value = "loadAllCustomer", method = RequestMethod.GET)
	public @ResponseBody List<clsPartyMasterModel> funGetAllCustomerList(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List<clsPartyMasterModel> listCustomerdtl = objPartyMasterService.funGetListCustomer(clientCode);
		if (listCustomerdtl.isEmpty()) {
			listCustomerdtl = new ArrayList<clsPartyMasterModel>();
		}
		return listCustomerdtl;
	}

	
	private clsSundryDebtorMasterModel funPrepareDebtorModel(clsPartyMasterBean objBean, String userCode, String clientCode, String propertyCode) {
		
		long lastNo = 0;
		clsSundryDebtorMasterModel objModel=null;
		if (objBean.getStrDebtorCode() == null || objBean.getStrDebtorCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNoModuleWise("tblsundarydebtormaster", "DebtorMaster", "intGId", clientCode, "5-WebBook");
			String debtorCode = "D" + String.format("%08d", lastNo);
			objModel = new clsSundryDebtorMasterModel(new clsSundryDebtorMasterModel_ID(debtorCode, clientCode));
			objModel.setIntGId(lastNo);
			objModel.setDteStartDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));

		} else {
			objModel = objSundryDebtorMasterService.funGetSundryDebtorMaster(objBean.getStrDebtorCode().trim(), clientCode);
			if (null == objModel) {
				lastNo = objGlobalFunctionsService.funGetLastNoModuleWise("tblsundarydebtormaster", "DebtorMaster", "intGId", clientCode, "5-WebBook");
				String debtorCode = "D" + String.format("%08d", lastNo);
				objModel = new clsSundryDebtorMasterModel(new clsSundryDebtorMasterModel_ID(debtorCode, clientCode));
				objModel.setIntGId(lastNo);
				objModel.setDteStartDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				/*String strStarDate = objModel1.getDteStartDate();
				objModel = new clsSundryDebtorMasterModel(new clsSundryDebtorMasterModel_ID(objBean.getStrDebtorCode(), clientCode));
				objModel.setDteStartDate(strStarDate);*/
			}
		}
		
		
		/* setting main data */
		objModel.setStrPrefix("");
		objModel.setStrFirstName(objBean.getStrPName());
		objModel.setStrMiddleName("");
		objModel.setStrLastName("");
		objModel.setStrCategoryCode("");
		/* setting main data */

		objModel.setStrAddressLine1(objBean.getStrMAdd1());
		objModel.setStrAddressLine2(objBean.getStrMAdd2());
		objModel.setStrAddressLine3("");
		objModel.setStrBlocked("NO");
		objModel.setStrCity(objBean.getStrMCity());
		objModel.setLongZipCode(objBean.getStrMPin());
		objModel.setStrTelNo1(objBean.getStrPhone());
		objModel.setStrTelNo2(objBean.getStrMobile());
		objModel.setStrFax(objBean.getStrFax());
		objModel.setStrArea("");
		objModel.setStrEmail(objBean.getStrEmail());
		objModel.setStrContactPerson1(objBean.getStrContact());
		objModel.setStrContactDesignation1("");
		objModel.setStrContactEmail1("");
		objModel.setStrContactTelNo1("");
		objModel.setStrContactPerson2("");
		objModel.setStrContactDesignation2("");
		objModel.setStrContactEmail2("");
		objModel.setStrContactTelNo2("");
		objModel.setStrLandmark("");
		objModel.setStrDebtorFullName(objBean.getStrPName());
		objModel.setStrExpired("N");
		objModel.setStrExpiryReasonCode("NA");
		objModel.setStrECSYN("N");
		objModel.setStrAccountNo("");
		objModel.setStrHolderName("");
		objModel.setStrMICRNo("");
		objModel.setDblECS("0.00");
		objModel.setStrSaveCurAccount("NA");
		objModel.setStrAlternateCode("");
		objModel.setDblOutstanding("0.00");
		objModel.setStrStatus("NA");
		objModel.setIntDays1("0");
		objModel.setIntDays2("0");
		objModel.setIntDays3("0");
		objModel.setIntDays4("0");
		objModel.setIntDays5("0");
		objModel.setDblCrAmt("0");
		objModel.setDblDrAmt("0");
		objModel.setDteLetterProcess(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrReminder1("0");
		objModel.setStrReminder2("0");
		objModel.setStrReminder3("0");
		objModel.setStrReminder4("0");
		objModel.setStrReminder5("0");
		objModel.setDblLicenseFee("NA");
		objModel.setDblAnnualFee("NA");
		objModel.setStrRemarks("NA");
		objModel.setStrClientApproval("NA");
		objModel.setStrAMCLink("NA");
		objModel.setStrCurrencyType("NA");
		objModel.setStrAccountHolderCode("NA");
		objModel.setStrAccountHolderName("NA");
		objModel.setStrAMCCycle("Yearly");

		objModel.setStrAMCRemarks("");
		objModel.setStrClientComment("");
		objModel.setStrBillingToCode("");
		objModel.setDblAnnualFeeInCurrency("");
		objModel.setDblLicenseFeeInCurrency("");
		objModel.setStrState("");
		objModel.setStrRegion("");
		objModel.setStrCountry("");
		objModel.setStrConsolidated("NA");
		objModel.setIntCreditDays("NA");
		objModel.setStrDebtorStatusCode("NA");
		objModel.setLongMobileNo("NA");
		objModel.setStrECSActivate("NA");
		objModel.setStrReminderStatus1("NA");
		objModel.setDteRemainderDate1("NA");
		objModel.setStrReminderStatus2("NA");
		objModel.setDteRemainderDate2("NA");
		objModel.setStrReminderStatus3("NA");
		objModel.setDteRemainderDate3("NA");
		objModel.setStrReminderStatus4("NA");
		objModel.setDteRemainderDate4("NA");
		objModel.setStrReminderStatus5("NA");
		objModel.setDteRemainderDate5("");
		objModel.setStrAllInvoiceHeader("");

		objModel.setStrClientCode(clientCode);
		objModel.setStrPropertyCode(propertyCode);
		objModel.setStrUserCreated(userCode);
		objModel.setDteDateCreated(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserEdited(userCode);
		objModel.setDteDateEdited(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrAccountCode("");
		objModel.setStrAccountName("");
		objModel.setStrOperational(objBean.getStrOperational());
		return objModel;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadGetCustomerLocationProperty", method = RequestMethod.GET)
	public @ResponseBody clsPartyMasterModel funAssignFieldsForSupplierTaxDtl(@RequestParam("locCode") String locCode, @RequestParam("propCode") String propCode, HttpServletRequest req) {
		clsPartyMasterModel objPartyModel = new clsPartyMasterModel();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String hql = " from clsPartyMasterModel where  strLocCode='" + locCode + "' and strPropCode='" + propCode + "' and strClientCode='" + clientCode + "' ";
		List arrList = objGlobalFunctionsService.funGetList(hql, "hql");
		if (arrList.size() > 0) {
			objPartyModel = (clsPartyMasterModel) arrList.get(0);
		} else {
			objPartyModel.setStrPCode("");
			objPartyModel.setStrPName("");
		}
		return objPartyModel;
	}
	
	
	public List<String>  funGetCategoryList(String clientCode) throws Exception 
	{
		List<String> listCategory = new ArrayList<>();
		StringBuilder sbSql=new StringBuilder();
		sbSql.append("select a.strCategoryCode,a.strCategoryDesc from tblcategorymaster a where a.strClientCode='"+clientCode+"' ");
		List listGetRecord = objBaseService.funGetList(sbSql, "sql");
		if(listGetRecord.size()>0)
		{
			for (int j = 0; j < listGetRecord.size(); j++) 
			{
				Object[] obArr = (Object[]) listGetRecord.get(j);
				hmCategory.put(obArr[1].toString(),obArr[0].toString());
				listCategory.add(obArr[1].toString());
			}	
		}

		return listCategory;
	}
	
	public Map<String,String>  funGetRegionList(String clientCode) throws Exception 
	{
//		List<String> listRegion = new ArrayList<>();
		StringBuilder sbSql=new StringBuilder();
		Map<String,String>hm=new HashMap<String,String>();
		sbSql.append("select a.strRegionCode,a.strRegionDesc from tblregionmaster a where a.strClientCode='"+clientCode+"' ");
		List listGetRecord = objBaseService.funGetList(sbSql, "sql");
		if(listGetRecord.size()>0)
		{
			for (int j = 0; j < listGetRecord.size(); j++) 
			{
				Object[] obArr = (Object[]) listGetRecord.get(j);
				hm.put(obArr[0].toString(),obArr[1].toString());
//				listRegion.add(obArr[1].toString());
			}	
		}

		return hm;
	}
}
