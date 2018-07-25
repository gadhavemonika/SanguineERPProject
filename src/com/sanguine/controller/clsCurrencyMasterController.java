package com.sanguine.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsCurrencyMasterBean;
import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.model.clsCurrencyMasterModel_ID;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsCurrencyMasterController {

	@Autowired
	private clsCurrencyMasterService objCurrencyMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open CurrencyMaster
	@RequestMapping(value = "/frmCurrencyMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCurrencyMaster_1", "command", new clsCurrencyMasterModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCurrencyMaster", "command", new clsCurrencyMasterModel());
		} else {
			return null;
		}
	}

	// Load Master Data On Form
	@RequestMapping(value = "/loadCurrencyCode", method = RequestMethod.POST)
	public @ResponseBody clsCurrencyMasterBean funLoadMasterData(HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String docCode = request.getParameter("docCode").toString();
		clsCurrencyMasterModel objModel = objCurrencyMasterService.funGetCurrencyMaster(docCode, clientCode);
		clsCurrencyMasterBean objCurrencyMasterBean = new clsCurrencyMasterBean();
		if (objModel == null) {
			objCurrencyMasterBean.setStrCurrencyCode("Invalid Code");
		} else {
			objCurrencyMasterBean.setStrCurrencyCode(objModel.getStrCurrencyCode());
			objCurrencyMasterBean.setStrCurrencyName(objModel.getStrCurrencyName());
			objCurrencyMasterBean.setStrShortName(objModel.getStrShortName());
			objCurrencyMasterBean.setStrBankName(objModel.getStrBankName());
			objCurrencyMasterBean.setStrSwiftCode(objModel.getStrSwiftCode());
			objCurrencyMasterBean.setStrIbanNo(objModel.getStrIbanNo());
			objCurrencyMasterBean.setStrRouting(objModel.getStrRouting());
			objCurrencyMasterBean.setStrAccountNo(objModel.getStrAccountNo());
			objCurrencyMasterBean.setDblConvToBaseCurr(objModel.getDblConvToBaseCurr());
			objCurrencyMasterBean.setStrSubUnit(objModel.getStrSubUnit());
		}

		return objCurrencyMasterBean;
	}

	// Save or Update CurrencyMaster
	@RequestMapping(value = "/saveCurrencyMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsCurrencyMasterBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			try {
				String clientCode = req.getSession().getAttribute("clientCode").toString();
				String userCode = req.getSession().getAttribute("usercode").toString();
				clsCurrencyMasterModel objModel = funPrepareModel(objBean, userCode, clientCode);
				objCurrencyMasterService.funAddUpdateCurrencyMaster(objModel);
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Currency Code : ".concat(objModel.getStrCurrencyCode()));
				return new ModelAndView("redirect:/frmCurrencyMaster.html");
			} catch (Exception ex) {
				ex.printStackTrace();
				return new ModelAndView("frmCurrencyMaster");
			}
		} else {
			return new ModelAndView("frmCurrencyMaster");
		}
	}

	// Convert bean to model function
	private clsCurrencyMasterModel funPrepareModel(clsCurrencyMasterBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsCurrencyMasterModel objModel = null;
		if (objBean.getStrCurrencyCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblcurrencymaster", "Currencymaster", "intID", clientCode);
			String groupCode = "CU" + String.format("%05d", lastNo);
			objModel = new clsCurrencyMasterModel(new clsCurrencyMasterModel_ID(groupCode, clientCode));
			objModel.setIntID(lastNo);
			objModel.setStrUserCreated(userCode);
			objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			objModel = objCurrencyMasterService.funGetCurrencyMaster(objBean.getStrCurrencyCode(), clientCode);
			if (null == objModel) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblcurrencymaster", "Currencymaster", "intID", clientCode);
				String groupCode = "G" + String.format("%06d", lastNo);
				objModel = new clsCurrencyMasterModel(new clsCurrencyMasterModel_ID(groupCode, clientCode));
				objModel.setIntID(lastNo);
				objModel.setStrUserCreated(userCode);
				objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				objModel = new clsCurrencyMasterModel(new clsCurrencyMasterModel_ID(objBean.getStrCurrencyCode(), clientCode));
			}
		}
		objModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserModified(userCode);
		objModel.setStrCurrencyName(objBean.getStrCurrencyName());
		objModel.setStrShortName(objBean.getStrShortName());
		objModel.setStrBankName(objBean.getStrBankName());
		objModel.setStrSwiftCode(objBean.getStrSwiftCode());
		objModel.setStrIbanNo(objBean.getStrIbanNo());
		objModel.setStrRouting(objBean.getStrRouting());
		objModel.setStrAccountNo(objBean.getStrAccountNo());
		objModel.setDblConvToBaseCurr(objBean.getDblConvToBaseCurr());
		objModel.setStrSubUnit(objBean.getStrSubUnit());
		objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserCreated(userCode);

		return objModel;

	}

	@RequestMapping(value = "/loadAllCurrency", method = RequestMethod.POST)
	public @ResponseBody List<clsCurrencyMasterModel> funLoadAllCurrencyData(HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List<clsCurrencyMasterModel> listCurrency = objCurrencyMasterService.funGetAllCurrencyDataModel(clientCode);
		if (listCurrency == null) {
			listCurrency = new ArrayList<clsCurrencyMasterModel>();
		}
		return listCurrency;
	}

}
