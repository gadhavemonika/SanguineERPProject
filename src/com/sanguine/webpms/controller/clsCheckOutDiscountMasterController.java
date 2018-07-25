package com.sanguine.webpms.controller;

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
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsFolioDtlBean;
import com.sanguine.webpms.bean.clsIncomeHeadMasterBean;
import com.sanguine.webpms.bean.clsTaxCalculation;
import com.sanguine.webpms.bean.clsTaxProductDtl;
import com.sanguine.webpms.model.clsBookerMasterHdModel;
import com.sanguine.webpms.model.clsFolioDtlModel;
import com.sanguine.webpms.model.clsFolioHdModel;
import com.sanguine.webpms.model.clsFolioTaxDtl;
import com.sanguine.webpms.service.clsFolioService;

@Controller
public class clsCheckOutDiscountMasterController {
	@Autowired
	private clsFolioService objFolioService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	clsPMSUtilityFunctions objPMSUtility;

	// Open Folio
	@RequestMapping(value = "/frmCheckoutDiscountMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCheckoutDiscountMaster_1", "command", new clsFolioDtlBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCheckoutDiscountMaster", "command", new clsFolioDtlBean());
		} else {
			return null;
		}
	}

	// load folio data on form
	@RequestMapping(value = "/loadFolioDataCheckOutDiscount", method = RequestMethod.GET)
	public @ResponseBody clsFolioHdModel funFetchFolioData(@RequestParam("folioNo") String folioNo, HttpServletRequest req) {
		
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		clsFolioHdModel objFolioHdModel = objFolioService.funGetFolioList(folioNo, clientCode, propertyCode);
		if (objFolioHdModel == null) {
			objFolioHdModel = new clsFolioHdModel();
			objFolioHdModel.setStrFolioNo("Invalid Code");
		}
		return objFolioHdModel;
	}

	// Save folio posting
	@RequestMapping(value = "/saveCheckoutDiscount", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsFolioDtlBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String PMSDate = objGlobal.funGetDate("yyyy-MM-dd", req.getSession().getAttribute("PMSDate").toString());

			clsFolioHdModel objFolioHdModel = objFolioService.funGetFolioList(objBean.getStrFolioNo(), clientCode, "");
			List<clsFolioDtlModel> listFolioDtlModels = objFolioHdModel.getListFolioDtlModel();
			
			double discountAmt = (objBean.getDblDebitAmt() * objBean.getDblDiscPer())/100;
			
			// generate doc No.
			String transaDate = objGlobal.funGetCurrentDateTime("dd-MM-yyyy").split(" ")[0];

			String docNo ="";

			clsFolioDtlModel objFolioDtlModel = new clsFolioDtlModel();
			objFolioDtlModel.setDteDocDate(PMSDate);
			objFolioDtlModel.setStrDocNo(docNo);
			objFolioDtlModel.setStrPerticulars("Folio Discount");
			objFolioDtlModel.setDblDebitAmt(0.00);
			objFolioDtlModel.setDblCreditAmt(discountAmt);
			objFolioDtlModel.setDblBalanceAmt(0.00);
			objFolioDtlModel.setStrRevenueType("Folio Discount");
			objFolioDtlModel.setStrRevenueCode("");
			listFolioDtlModels.add(objFolioDtlModel);
			objFolioHdModel.setListFolioDtlModel(listFolioDtlModels);
			objFolioService.funAddUpdateFolioHd(objFolioHdModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Folio No. : ".concat(objFolioHdModel.getStrFolioNo()));

			return new ModelAndView("redirect:/frmCheckoutDiscountMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmCheckoutDiscountMaster?saddr=" + urlHits);
		}
	}
}
