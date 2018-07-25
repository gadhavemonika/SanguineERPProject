package com.sanguine.webpms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsChargePostingBean;
import com.sanguine.webpms.dao.clsBaggageMasterDao;
import com.sanguine.webpms.dao.clsChargePostingDao;
import com.sanguine.webpms.model.clsBaggageMasterModel;
import com.sanguine.webpms.model.clsChargePostingHdModel;
import com.sanguine.webpms.model.clsDepartmentMasterModel;
import com.sanguine.webpms.service.clsChargePostingService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@Controller
public class clsChargePostingController {

	@Autowired
	private clsChargePostingService objChargePostingService;

	@Autowired
	private clsChargePostingDao objChargePostingDao;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobal = null;

	// Open ChargePosting
	@RequestMapping(value = "/frmChargePosting", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmChargePosting_1", "command", new clsChargePostingHdModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmChargePosting", "command", new clsChargePostingHdModel());
		} else {
			return null;
		}
	}

	// Save or Update ChargePosting
	@RequestMapping(value = "/saveChargePosting", method = RequestMethod.GET)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsChargePostingBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsChargePostingHdModel objModel = objChargePostingService.funPrepareChargePostingModel(objBean, userCode, clientCode);
			objChargePostingDao.funAddUpdateChargePosting(objModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Service Code : ".concat(objModel.getStrService()));

			return new ModelAndView("redirect:/frmChargePosting.html");

		} else {
			return new ModelAndView("frmChargePosting");
		}
	}

	// Load Charge Posting data on form
	@RequestMapping(value = "/loadService", method = RequestMethod.GET)
	public @ResponseBody clsChargePostingHdModel funFetchChargePostingMasterData(@RequestParam("serviceCode") String serviceCode, HttpServletRequest req) {
		clsChargePostingHdModel objChargePostingMasterModel = null;

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List listChargePostingData = objChargePostingDao.funGetChargePosting(serviceCode, clientCode);
		objChargePostingMasterModel = (clsChargePostingHdModel) listChargePostingData.get(0);

		return objChargePostingMasterModel;
	}

}
