package com.sanguine.webpms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsBaggageMasterBean;
import com.sanguine.webpms.bean.clsPMSSettlementMasterBean;
import com.sanguine.webpms.dao.clsBaggageMasterDao;
import com.sanguine.webpms.dao.clsPMSSettlementMasterDao;
import com.sanguine.webpms.model.clsBaggageMasterModel;
import com.sanguine.webpms.model.clsBathTypeMasterModel;
import com.sanguine.webpms.model.clsChargePostingHdModel;
import com.sanguine.webpms.model.clsPMSSettlementMasterHdModel;
import com.sanguine.webpms.model.clsPMSSettlementMasterModel_ID;
import com.sanguine.webpms.service.clsBaggageMasterService;
import com.sanguine.webpms.service.clsPMSSettlementMasterService;

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
public class clsPMSSettlementMasterController {

	@Autowired
	private clsPMSSettlementMasterService objSettlementMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsPMSSettlementMasterDao objPMSSettlementMasterDao;

	// Open SettlementMaster
	@RequestMapping(value = "/frmSettlementMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSettlementMaster_1", "command", new clsPMSSettlementMasterHdModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSettlementMaster", "command", new clsPMSSettlementMasterHdModel());
		} else {
			return null;
		}
	}

	// Save or Update SettlementMaster
	@RequestMapping(value = "/saveSettlementMaster", method = RequestMethod.GET)
	public ModelAndView funAddUpdateSettlementMaster(@ModelAttribute("command") @Valid clsPMSSettlementMasterBean objPMSSettlementMasterBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			clsPMSSettlementMasterHdModel objSettlementModel = objSettlementMasterService.funPrepareSettlementModel(objPMSSettlementMasterBean, clientCode);
			objPMSSettlementMasterDao.funAddUpdateSettlementMaster(objSettlementModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Settlement Code : ".concat(objSettlementModel.getStrSettlementCode()));
			return new ModelAndView("redirect:/frmSettlementMaster.html");
		} else {
			return new ModelAndView("frmSettlementMaster");
		}
	}

	// Load Settlement data on form

	@RequestMapping(value = "/loadSettlementCode", method = RequestMethod.GET)
	public @ResponseBody clsPMSSettlementMasterHdModel funFetchSettlementMasterData(@RequestParam("settlementCode") String settlementCode, HttpServletRequest req) {
		clsPMSSettlementMasterHdModel objSettlementMasterModel = null;

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List listSettleData = objPMSSettlementMasterDao.funGetPMSSettlementMaster(settlementCode, clientCode);
		objSettlementMasterModel = (clsPMSSettlementMasterHdModel) listSettleData.get(0);
		return objSettlementMasterModel;
	}

}
