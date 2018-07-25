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
import com.sanguine.webpms.bean.clsIncomeHeadMasterBean;
import com.sanguine.webpms.bean.clsPlanMasterBean;
import com.sanguine.webpms.dao.clsIncomeHeadMasterDao;
import com.sanguine.webpms.dao.clsPlanMasterDao;
import com.sanguine.webpms.model.clsIncomeHeadMasterModel;
import com.sanguine.webpms.model.clsPlanMasterModel;
import com.sanguine.webpms.service.clsIncomeHeadMasterService;
import com.sanguine.webpms.service.clsPlanMasterService;

@Controller
public class clsIncomeHeadMasterController {

	@Autowired
	private clsIncomeHeadMasterService objIncomeHeadMasterService;

	@Autowired
	private clsIncomeHeadMasterDao objIncomeHeadMasterDao;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open PlanMaster
	@RequestMapping(value = "/frmIncomehead", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (urlHits.equalsIgnoreCase("1")) {
			return new ModelAndView("frmIncomehead", "command", new clsIncomeHeadMasterBean());
		} else {
			return new ModelAndView("frmIncomehead_1", "command", new clsIncomeHeadMasterBean());
		}
	}

	@RequestMapping(value = "/saveIncomeHeadMaster", method = RequestMethod.GET)
	public ModelAndView funAddUpdateIncomeHeadMaster(@ModelAttribute("command") @Valid clsIncomeHeadMasterBean objIncomeHeadMasterBean, BindingResult result, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		clsIncomeHeadMasterModel objIncomeHeadMasterModel = objIncomeHeadMasterService.funPrepareIncomeHeadModel(objIncomeHeadMasterBean, clientCode, userCode);
		objIncomeHeadMasterDao.funAddUpdateIncomeHeadMaster(objIncomeHeadMasterModel);
		req.getSession().setAttribute("success", true);
		req.getSession().setAttribute("successMessage", "IncomeHead Code : ".concat(objIncomeHeadMasterModel.getStrIncomeHeadCode()));

		return new ModelAndView("redirect:/frmIncomehead.html");

	}

	@RequestMapping(value = "/loadIncomeHeadMasterData", method = RequestMethod.GET)
	public @ResponseBody clsIncomeHeadMasterModel funFetchIncomeHeadMasterData(@RequestParam("incomeCode") String incomeCode, HttpServletRequest req) {
		clsIncomeHeadMasterModel objIncomeHeadMasterModel = new clsIncomeHeadMasterModel();

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List listIncomeHeadData = objIncomeHeadMasterDao.funGetIncomeHeadMaster(incomeCode, clientCode);
		if (listIncomeHeadData.size() > 0) {
			objIncomeHeadMasterModel = (clsIncomeHeadMasterModel) listIncomeHeadData.get(0);
		}
		return objIncomeHeadMasterModel;
	}

}