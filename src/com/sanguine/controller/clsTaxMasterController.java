package com.sanguine.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsTaxMasterBean;
import com.sanguine.model.clsSettlementMasterModel;
import com.sanguine.model.clsTaxHdModel;
import com.sanguine.model.clsTaxHdModel_ID;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsTaxMasterService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsTaxMasterController {
	@Autowired
	private clsTaxMasterService objTaxMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	@RequestMapping(value = "/frmTaxMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		List<String> listTaxOnSP = new ArrayList<>();
		listTaxOnSP.add("Sales");
		listTaxOnSP.add("Purchase");
		model.put("taxOnSPList", listTaxOnSP);

		/*
		 * List<String> listTaxType = new ArrayList<>();
		 * listTaxType.add("Percent"); listTaxType.add("Slab");
		 * listTaxType.add("Fixed Amount"); model.put("taxTypeList",
		 * listTaxType);
		 */

		List<String> listTaxOnGD = new ArrayList<>();
		listTaxOnGD.add("Discount");
		listTaxOnGD.add("Gross");
		model.put("taxOnGDList", listTaxOnGD);

		List<String> listTaxIndicator = new ArrayList<>();
		listTaxIndicator.add(" ");
		String[] alphabetSet = objGlobal.funGetAlphabetSet();
		for (int i = 0; i < alphabetSet.length; i++) {
			listTaxIndicator.add(alphabetSet[i]);
		}
		model.put("taxIndicatorList", listTaxIndicator);

		List<String> listTaxRounded = new ArrayList<>();
		listTaxRounded.add("Tax Rounded");
		model.put("taxRoundedList", listTaxRounded);

		List<String> listTaxOnST = new ArrayList<>();
		listTaxOnST.add("Tax On Subtotal");
		model.put("taxOnSTList", listTaxOnST);

		List<String> listTaxOnTax = new ArrayList<>();
		listTaxOnTax.add("Tax On Tax");
		model.put("taxOnTaxList", listTaxOnTax);

		List<String> listExcisable = new ArrayList<>();
		listExcisable.add("Excisable");
		model.put("excisableList", listExcisable);

		List<String> listAppOn = new ArrayList<>();
		listAppOn.add("Local ");
		listAppOn.add("Foreign");
		listAppOn.add("Both");
		model.put("applicableOnList", listAppOn);

		List<String> listTaxCalType = new ArrayList<>();
		listTaxCalType.add("Forword");
		listTaxCalType.add("Backword");
		model.put("taxCalList", listTaxCalType);

		List<String> listPartyIndicator = new ArrayList<>();
		listPartyIndicator.add(" ");
		for (int i = 0; i < alphabetSet.length; i++) {
			listPartyIndicator.add(alphabetSet[i]);
		}
		model.put("partyIndicatorList", listPartyIndicator);

		clsTaxMasterBean objBean = new clsTaxMasterBean();
		objBean.setListSettlement(funGetSettlementList());
		objBean.setStrTaxRounded("Y");
		model.put("settlementGrid", objBean);

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmTaxMaster_1", "command", new clsTaxHdModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmTaxMaster", "command", new clsTaxHdModel());
		} else {
			return null;
		}
		// return new ModelAndView("frmTaxMaster","command", new
		// clsTaxHdModel());
	}

	// Save or Update group master function to save or update record of group
	// master into database and also validates all fields of form.
	@RequestMapping(value = "/saveTaxMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsTaxMasterBean objBean, BindingResult result, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsTaxHdModel objModel = funPrepareTaxHdModel(objBean, userCode, clientCode);
			objModel.setDtValidFrom(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtValidFrom()));
			objModel.setDtValidTo(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtValidTo()));
			objTaxMasterService.funAddUpdate(objModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Tax Code : ".concat(objModel.getStrTaxCode()));
			return new ModelAndView("redirect:/frmTaxMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmTaxMaster?saddr=" + urlHits);
		}
	}

	// AssignField function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadTaxMasterData", method = RequestMethod.GET)
	public @ResponseBody clsTaxHdModel funAssignFields(@RequestParam("taxCode") String code, HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsTaxHdModel objModel = objTaxMasterService.funGetObject(code, clientCode);
		if (null == objModel) {
			objModel = new clsTaxHdModel();
			objModel.setStrTaxCode("Invalid Code");
		} else {
			objModel.setDtValidFrom(objGlobal.funGetDate("yyyy/MM/dd", objModel.getDtValidFrom()));
			objModel.setDtValidTo(objGlobal.funGetDate("yyyy/MM/dd", objModel.getDtValidTo()));
		}
		return objModel;
	}

	// Returns a single master record by passing code as primary key. Also
	// generates next Code if transaction is for Save Master
	private clsTaxHdModel funPrepareTaxHdModel(clsTaxMasterBean objBean, String userCode, String clientCode) {
		long lastNo = 0;
		clsTaxHdModel objModel;
		if (objBean.getStrTaxCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tbltaxhd", "TaxMaster", "intId", clientCode);
			String code = "T" + String.format("%07d", lastNo);
			objModel = new clsTaxHdModel(new clsTaxHdModel_ID(code, clientCode));
			objModel.setIntId(lastNo);
			objModel.setStrUserCreated(userCode);
			objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsTaxHdModel objModel1 = objTaxMasterService.funGetObject(objBean.getStrTaxCode(), clientCode);
			if (null == objModel1) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tbltaxhd", "TaxMaster", "intId", clientCode);
				String code = "T" + String.format("%07d", lastNo);
				objModel = new clsTaxHdModel(new clsTaxHdModel_ID(code, clientCode));
				objModel.setIntId(lastNo);
				objModel.setStrUserCreated(userCode);
				objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				objModel = new clsTaxHdModel(new clsTaxHdModel_ID(objBean.getStrTaxCode(), clientCode));
			}
		}

		objModel.setStrTaxDesc(objBean.getStrTaxDesc());
		objModel.setStrTaxOnST(objGlobal.funIfNull(objBean.getStrTaxOnST(), "N", "Y"));
		objModel.setStrTaxType(objBean.getStrTaxType());
		objModel.setDblPercent(objBean.getDblPercent());
		objModel.setDblAmount(objBean.getDblAmount());
		objModel.setStrTaxOnGD(objBean.getStrTaxOnGD());
		objModel.setDtValidFrom(objBean.getDtValidFrom());
		objModel.setDtValidTo(objBean.getDtValidTo());
		objModel.setStrTaxRounded(objGlobal.funIfNull(objBean.getStrTaxRounded(), "N", "Y"));
		objModel.setStrTaxOnTax(objGlobal.funIfNull(objBean.getStrTaxOnTax(), "N", "Y"));
		objModel.setStrTaxIndicator(objGlobal.funIfNull(objBean.getStrTaxIndicator(), " ", objBean.getStrTaxIndicator()));
		objModel.setStrTaxCalculation(objGlobal.funIfNull(objBean.getStrTaxCalculation(), "", objBean.getStrTaxCalculation()));
		objModel.setStrPropertyCode(objGlobal.funIfNull(objBean.getStrPropertyCode(), "", objBean.getStrPropertyCode()));
		objModel.setStrTaxOnSP(objBean.getStrTaxOnSP());
		objModel.setStrExcisable(objGlobal.funIfNull(objBean.getStrExcisable(), "N", "Y"));
		objModel.setStrApplOn(objGlobal.funIfNull(objBean.getStrApplOn(), "", objBean.getStrApplOn()));
		objModel.setStrPartyIndicator(objGlobal.funIfNull(objBean.getStrPartyIndicator(), "", objBean.getStrPartyIndicator()));
		objModel.setStrUserModified(userCode);
		objModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrTaxOnTaxCode(objBean.getStrTaxOnTaxCode());
		objModel.setStrTaxOnSubGroup(objGlobal.funIfNull(objBean.getStrTaxOnSubGroup(), "N", "Y"));
		objModel.setStrCalTaxOnMRP(objGlobal.funIfNull(objBean.getStrCalTaxOnMRP(), "N", "Y"));
		objModel.setDblAbatement(objBean.getDblAbatement());
		objModel.setStrTOTOnMRPItems(objGlobal.funIfNull(objBean.getStrTOTOnMRPItems(), "N", "Y"));
		objModel.setStrExternalCode(objBean.getStrExternalCode());
		if (objBean.getStrShortName() == null) {
			objModel.setStrShortName(" ");
		} else {
			objModel.setStrShortName(objBean.getStrShortName());
		}
		objModel.setStrGSTNo(objBean.getStrGSTNo());
		objModel.setListTaxSGDtl(objBean.getListTaxSGDtl());
		objModel.setStrNotApplicableForComesa(objBean.getStrNotApplicableForComesa());
		objModel.setStrTaxReversal(objBean.getStrTaxReversal());

		// List<clsTaxSubGroupDtl> listTaxSGDtl=new
		// ArrayList<clsTaxSubGroupDtl>();

		return objModel;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<clsSettlementMasterModel> funGetSettlementList() {
		List listSettlement = new ArrayList<clsSettlementMasterModel>();

		clsSettlementMasterModel objModel = new clsSettlementMasterModel();
		objModel.setStrSettlementCode("S000001");
		objModel.setStrSettlementDesc("Cash");
		objModel.setStrSettlementType("Cash");
		objModel.setStrApplicable("Y");
		listSettlement.add(objModel);

		objModel = new clsSettlementMasterModel();
		objModel.setStrSettlementCode("S000002");
		objModel.setStrSettlementDesc("Credit-Card");
		objModel.setStrSettlementType("Credit");
		objModel.setStrApplicable("Y");
		listSettlement.add(objModel);

		return listSettlement;
	}

	@RequestMapping(value = "/frmTaxList", method = RequestMethod.GET)
	public ModelAndView funOpenShowReport() throws JRException {
		return new ModelAndView("frmTaxList", "command", new clsReportBean());
	}

	@SuppressWarnings("rawtypes")
	private List<clsTaxHdModel> funPrepareTaxList(List listTax) {
		List<clsTaxHdModel> objTaxList = new ArrayList<clsTaxHdModel>();
		for (int i = 0; i < listTax.size(); i++) {
			clsTaxHdModel tax = (clsTaxHdModel) listTax.get(i);
			clsTaxHdModel objTax = new clsTaxHdModel();
			objTax.setStrTaxOnSP(tax.getStrTaxOnSP());
			objTax.setStrTaxCode(tax.getStrTaxCode());
			objTax.setStrTaxDesc(tax.getStrTaxDesc());
			objTax.setStrTaxType(tax.getStrTaxType());
			objTax.setDblPercent(tax.getDblPercent());
			objTax.setStrTaxOnGD(tax.getStrTaxOnGD());
			objTax.setDtValidFrom(tax.getDtValidFrom());
			objTax.setDtValidTo(tax.getDtValidTo());
			objTax.setStrTaxIndicator(tax.getStrTaxIndicator());
			objTaxList.add(objTax);
		}
		return objTaxList;
	}

	@RequestMapping(value = "/rptTaxList", method = RequestMethod.GET)
	public ModelAndView funshowReport(@ModelAttribute("command") clsReportBean objBean, ModelAndView modelAndView, Map<String, Object> model, HttpServletRequest req) throws JRException {
		String companyName = "DSS";
		String taxOn = "ALL";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		objGlobal = new clsGlobalFunctions();
		String taxCode = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		List<clsTaxHdModel> taxHDList = funPrepareTaxList(objTaxMasterService.funGetDtlList(taxCode, clientCode));
		JRDataSource jRdataSource = new JRBeanCollectionDataSource(taxHDList);
		model.put("datasource", jRdataSource);
		model.put("strCompanyName", companyName);
		model.put("strUserCode", userCode);
		model.put("strTaxType", taxOn);
		modelAndView = new ModelAndView("TaxList" + type.trim().toUpperCase() + "Report", model);
		return modelAndView;
	}

	@RequestMapping(value = "/loadTaxesData", method = RequestMethod.GET)
	public @ResponseBody List funLoadTaxes(@RequestParam("taxCode") String code, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List list = objTaxMasterService.funGetTaxes(code, clientCode);
		if (null == list) {
			list = new ArrayList();
			list.add("Invalid Code");
		}

		return list;
	}

	@RequestMapping(value = "/loadSubGroupData", method = RequestMethod.GET)
	public @ResponseBody List funLoadSubGroupList(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List list = objTaxMasterService.funGetSubGroupList(clientCode);
		if (null == list) {
			list = new ArrayList();
			list.add("Invalid Code");
		}

		return list;
	}

}
