package com.sanguine.webbooks.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webbooks.bean.clsReceiptBean;
import com.sanguine.webbooks.bean.clsReceiptDetailBean;
import com.sanguine.webbooks.model.clsReceiptHdModel;
import com.sanguine.webbooks.model.clsWebBooksAccountMasterModel;
import com.sanguine.webbooks.service.clsBankMasterService;
import com.sanguine.webbooks.service.clsReceiptService;
import com.sanguine.webbooks.service.clsWebBooksAccountMasterService;

@Controller
public class clsDebtorReceiptController {

	@Autowired
	private clsReceiptService objReceiptService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsWebBooksAccountMasterService objWebBooksAccountMasterService;

	@Autowired
	private clsBankMasterService objBankMasterService;

	@Autowired
	private clsReceiptController objRecController;

	// Open Receipt
	@RequestMapping(value = "/frmDebtorReceipt", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List<clsWebBooksAccountMasterModel> listCashAccMaster = objWebBooksAccountMasterService.funGetAccountForCashBank(clientCode);
		Map<String, String> hmCashBankAccounts = new HashMap<String, String>();
		for (clsWebBooksAccountMasterModel objAccMaster : listCashAccMaster) {
			hmCashBankAccounts.put(objAccMaster.getStrAccountCode(), objAccMaster.getStrAccountName());
		}

		List<clsWebBooksAccountMasterModel> listDebtorAccMaster = objWebBooksAccountMasterService.funGetDebtorAccountList(clientCode);
		Map<String, String> hmDebtorAccounts = new HashMap<String, String>();
		for (clsWebBooksAccountMasterModel objAccMaster : listDebtorAccMaster) {
			hmDebtorAccounts.put(objAccMaster.getStrAccountCode(), objAccMaster.getStrAccountName());
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			ModelAndView modelView = new ModelAndView("frmDebtorReceipt_1", "command", new clsReceiptBean());
			modelView.addObject("CashBankAccounts", hmCashBankAccounts);
			modelView.addObject("DebtorAccounts", hmDebtorAccounts);
			return modelView;

		} else if ("1".equalsIgnoreCase(urlHits)) {
			ModelAndView modelView = new ModelAndView("frmDebtorReceipt", "command", new clsReceiptBean());
			modelView.addObject("CashBankAccounts", hmCashBankAccounts);
			modelView.addObject("DebtorAccounts", hmDebtorAccounts);
			return modelView;
		} else {
			return null;
		}
	}

	// Save or Update Receipt
	@RequestMapping(value = "/saveDebtorReceipt", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsReceiptBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			String startDate = req.getSession().getAttribute("startDate").toString();
			clsReceiptDetailBean objRecDtlBean = new clsReceiptDetailBean();
			objRecDtlBean.setStrAccountCode(objBean.getStrDebtorAccCode());
			objRecDtlBean.setStrDC("Cr");
			objRecDtlBean.setStrDebtorCode(objBean.getStrReceivedFrom());
			objRecDtlBean.setStrDebtorYN("Y");
			objRecDtlBean.setStrDescription(objBean.getStrDebtorAccDesc());
			objRecDtlBean.setDblCreditAmt(objBean.getDblAmt());
			objRecDtlBean.setDblDebitAmt(0);
			objRecDtlBean.setStrDimension("No");
			List<clsReceiptDetailBean> listRecDtlBean = new ArrayList<clsReceiptDetailBean>();
			listRecDtlBean.add(objRecDtlBean);
			objBean.setListReceiptBeanDtl(listRecDtlBean);

			clsReceiptHdModel objHdModel = objRecController.funPrepareHdModel(objBean, userCode, clientCode, propCode, req);
			objReceiptService.funAddUpdateReceiptHd(objHdModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Receipt No : ".concat(objHdModel.getStrVouchNo()));

			return new ModelAndView("redirect:/frmDebtorReceipt.html");
		} else {
			return new ModelAndView("frmDebtorReceipt");
		}
	}
}
