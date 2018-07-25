package com.sanguine.webbooks.apgl.controller;

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

import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webbooks.apgl.bean.clsAPGLBudgetBean;
import com.sanguine.webbooks.apgl.model.clsAPGLBudgetModel;
import com.sanguine.webbooks.apgl.service.clsAPGLBudgetMasterService;
import com.sanguine.webbooks.apgl.bean.clsBudgetDtlBean;

@Controller
public class clsAPGLBudgetMasterController {

	@Autowired
	clsAPGLBudgetMasterService objAPGLBudgetMasterService;

	@Autowired
	clsGlobalFunctionsService objGlobalFunctionsService;

	// Open Buget Form
	@RequestMapping(value = "/frmAPGLBudgetMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmAPGLBudgetMaster_1", "command", new clsAPGLBudgetBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmAPGLBudgetMaster", "command", new clsAPGLBudgetBean());
		} else {
			return null;
		}

	}

	// Load Budget TableData

	@RequestMapping(value = "/fillBudgetTableData", method = RequestMethod.GET)
	public @ResponseBody List funFillBudgetTableData(HttpServletRequest request) {

		String strMonth = request.getParameter("month").toString();
		String strYear = request.getParameter("year").toString();
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		List listBudget = objAPGLBudgetMasterService.funGetBudgetTableData(strMonth, strYear, strClientCode);

		return listBudget;
	}

	@RequestMapping(value = "/saveAPGLBudget", method = RequestMethod.POST)
	public ModelAndView funSaveBudgetTableData(@ModelAttribute("command") @Valid clsAPGLBudgetBean budgetBean, BindingResult result, HttpServletRequest request) {

		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		List<clsBudgetDtlBean> list = budgetBean.getListBudgetDtlModel();

		clsAPGLBudgetModel objBudgetModel = new clsAPGLBudgetModel();
		for (clsBudgetDtlBean obj : list) {
			if (!(obj.getIntId() == null || obj.getIntId() == ' ')) {
				objBudgetModel.setIntId(Long.parseLong(obj.getIntId().toString()));
				objBudgetModel.setStrAccCode(obj.getStrAccCode().toString());
				objBudgetModel.setStrAccName(obj.getStrAccName().toString());
				objBudgetModel.setDblBudgetAmt(obj.getDblBudgetAmt());
				objBudgetModel.setStrMonth(budgetBean.getStrMonth().toString());
				objBudgetModel.setStrYear(budgetBean.getStrYear());
				objBudgetModel.setStrClientCode(strClientCode);
				objAPGLBudgetMasterService.funSaveBudgetTableData(objBudgetModel);
			} else {

				long lastNo = objGlobalFunctionsService.funGetCount("tblbudget", "intId");
				objBudgetModel.setIntId(lastNo);
				objBudgetModel.setStrAccCode(obj.getStrAccCode().toString());
				objBudgetModel.setStrAccName(obj.getStrAccName().toString());
				objBudgetModel.setDblBudgetAmt(obj.getDblBudgetAmt());
				objBudgetModel.setStrMonth(budgetBean.getStrMonth().toString());
				objBudgetModel.setStrYear(budgetBean.getStrYear());
				objBudgetModel.setStrClientCode(strClientCode);
				objAPGLBudgetMasterService.funSaveBudgetTableData(objBudgetModel);
			}
		}

		return new ModelAndView("redirect:/frmAPGLBudgetMaster.html");
	}

}
