package com.sanguine.webpms.controller;

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
import com.sanguine.webpms.bean.clsBathTypeMasterBean;
import com.sanguine.webpms.bean.clsExtraBedMasterBean;
import com.sanguine.webpms.bean.clsPlanMasterBean;
import com.sanguine.webpms.dao.clsBathTypeMasterDao;
import com.sanguine.webpms.dao.clsExtraBedMasterDao;
import com.sanguine.webpms.model.clsExtraBedMasterModel;
import com.sanguine.webpms.model.clsIncomeHeadMasterModel;
import com.sanguine.webpms.model.clsPlanMasterModel;
import com.sanguine.webpms.service.clsBathTypeMasterService;
import com.sanguine.webpms.service.clsExtraBedMasterService;

@Controller
public class clsExtraBedMasterController {
	@Autowired
	private clsExtraBedMasterService objExtraBedMasterService;

	@Autowired
	private clsExtraBedMasterDao objExtraBedMasterDao;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open ExtraBedMaster
	@RequestMapping(value = "/frmExtraBed", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (urlHits.equalsIgnoreCase("1")) {

			return new ModelAndView("frmExtraBed", "command", new clsExtraBedMasterBean());
		} else {
			return new ModelAndView("frmExtraBed_1", "command", new clsExtraBedMasterBean());
		}
	}

	@RequestMapping(value = "/saveExtraBedMaster", method = RequestMethod.GET)
	public ModelAndView funAddUpdatePlanMaster(@ModelAttribute("command") @Valid clsExtraBedMasterBean objExtraBedMasterBean, BindingResult result, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		clsExtraBedMasterModel objExtraBedMasterModel = objExtraBedMasterService.funPrepareExtraBedModel(objExtraBedMasterBean, clientCode, userCode);
		objExtraBedMasterDao.funAddUpdateExtraBedMaster(objExtraBedMasterModel);

		req.getSession().setAttribute("success", true);
		req.getSession().setAttribute("successMessage", "ExtraBed Code : ".concat(objExtraBedMasterModel.getStrExtraBedTypeCode()));

		return new ModelAndView("redirect:/frmExtraBed.html");

	}

	@RequestMapping(value = "/loadExtraBedMasterData", method = RequestMethod.GET)
	public @ResponseBody clsExtraBedMasterModel funFetchIncomeHeadMasterData(@RequestParam("extraBedCode") String extraBedCode, HttpServletRequest req) {
		clsExtraBedMasterModel objExtraBedMasterModel = null;

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List listExtraBedData = objExtraBedMasterDao.funGetExtraBedMaster(extraBedCode, clientCode);
		objExtraBedMasterModel = (clsExtraBedMasterModel) listExtraBedData.get(0);
		return objExtraBedMasterModel;
	}

}
