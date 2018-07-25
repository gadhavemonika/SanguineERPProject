package com.sanguine.crm.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
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
import com.sanguine.crm.bean.clsSubContractorMasterBean;
import com.sanguine.crm.model.clsPartyTaxIndicatorDtlModel;
import com.sanguine.crm.model.clsSubContractorMasterModel;
import com.sanguine.crm.service.clsSubContractorMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsGroupMasterService;
import com.sanguine.service.clsSetupMasterService;

@Controller
public class clsSubContractorMasterController {
	@Autowired
	private clsSubContractorMasterService objSubConMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private clsGroupMasterService objGrpMasterService;
	clsGlobalFunctions objGlobal;
	@Autowired
	clsGlobalFunctions objGlobalFunctions;

	@RequestMapping(value = "/frmSubContractorMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
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

		List<String> listCategory = new ArrayList<>();
		// listCategory.add("");
		listCategory.add("Wholesale Dealer");
		listCategory.add("Industrial Consumer");
		listCategory.add("Government Department");
		model.put("categoryList", listCategory);

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

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSubContractorMaster_1", "command", new clsSubContractorMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSubContractorMaster", "command", new clsSubContractorMasterBean());
		} else {
			return null;
		}

	}

	// Save or Update group master function to save or update record of group
	// master into database and also validates all fields of form.
	@RequestMapping(value = "/saveSubContractorMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsSubContractorMasterBean objBean, BindingResult result, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		if (!result.hasErrors()) {
			clsSubContractorMasterModel objModel = funPrepareModel(objBean, userCode, clientCode);
			objSubConMasterService.funAddUpdate(objModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Sub Contractor Code : ".concat(objModel.getStrPCode()));
			return new ModelAndView("redirect:/frmSubContractorMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmSubContractorMaster?saddr=" + urlHits, "command", new clsSubContractorMasterBean());
		}

	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadSubContractorMasterData", method = RequestMethod.GET)
	public @ResponseBody clsSubContractorMasterModel funAssignFields(@RequestParam("partyCode") String PCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsSubContractorMasterModel objModel = objSubConMasterService.funGetObject(PCode, clientCode);
		if (null == objModel) {
			objModel = new clsSubContractorMasterModel();
			objModel.setStrPCode("Invalid Code");
			return objModel;
		} else {
			return objModel;
		}

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadSubContractorTaxDtl", method = RequestMethod.GET)
	public @ResponseBody List funAssignFieldsForSupplierTaxDtl(@RequestParam("partyCode") String PCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String sql = "select a.strTaxCode,b.strTaxDesc from tblpartytaxdtl a,tbltaxhd b " + "where a.strTaxCode=b.strTaxCode and a.strPCode='" + PCode + "'";
		List arrList = objGlobalFunctionsService.funGetList(sql, clientCode);

		return arrList;
	}

	// Returns a single group master record by passing group code as primary
	// key. Also generates next Group Code if transaction is for Save Master
	private clsSubContractorMasterModel funPrepareModel(clsSubContractorMasterBean objBean, String userCode, String clientCode) {
		long lastNo = 0;

		clsSubContractorMasterModel objModel1 = objSubConMasterService.funGetObject(objBean.getStrPCode(), clientCode);
		clsSubContractorMasterModel objModel = new clsSubContractorMasterModel();
		if (objModel1 != null) {

			objModel.setStrPCode(objModel1.getStrPCode());
			objModel.setIntPId(objModel1.getIntPId());
			objModel.setStrUserCreated(objModel1.getStrUserCreated());
			objModel.setDtCreatedDate(objModel1.getDtCreatedDate());
			objModel.setStrClientCode(clientCode);
		} else {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblpartymaster", "PartyMaster", "intPid", clientCode);
			String strPCode = "C" + String.format("%06d", lastNo);
			objModel.setStrPCode(strPCode);
			objModel.setIntPId(lastNo);
			objModel.setStrUserCreated(userCode);
			objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			objModel.setStrClientCode(clientCode);
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
		objModel.setStrPmtTerms(objGlobal.funIfNull(objBean.getStrPmtTerms(), "", ""));
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
		objModel.setStrSubType(objGlobal.funIfNull(objBean.getStrSubType(), "", ""));
		objModel.setDtExpiryDate(objGlobal.funIfNull(objBean.getDtExpiryDate(), "2014-7-30", objBean.getDtExpiryDate()));
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
		objModel.setStrExcisable(objBean.getStrExcisable());
		objModel.setStrCategory(objGlobal.funIfNull(objBean.getStrCategory(), "", objBean.getStrCategory()));
		objModel.setStrUserModified(userCode);
		objModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserCreated(userCode);
		objModel.setStrPartyType(objGlobal.funIfNull(objBean.getStrPartyType(), " ", objBean.getStrPartyType()));
		objModel.setStrPType("subc");
		objModel.setStrAccManager("");
		objModel.setDtInstallions(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrGSTNo(objBean.getStrGSTNo());
		objModel.setStrLocCode("");
		objModel.setStrPropCode(objBean.getStrPropCode());

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
		return objModel;
	}

}
