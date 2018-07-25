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
import com.sanguine.webpms.bean.clsBusinessSourceMasterBean;
import com.sanguine.webpms.bean.clsPlanMasterBean;
import com.sanguine.webpms.dao.clsBusinessSourceMasterDao;
import com.sanguine.webpms.dao.clsPlanMasterDao;
import com.sanguine.webpms.model.clsBaggageMasterModel;
import com.sanguine.webpms.model.clsBusinessSourceMasterModel;
import com.sanguine.webpms.model.clsPlanMasterModel;
import com.sanguine.webpms.service.clsBusinessSourceMasterService;
import com.sanguine.webpms.service.clsPlanMasterService;

@Controller
public class clsBusinessSourceMasterController {

	@Autowired
	private clsBusinessSourceMasterService objBusinessSourceMasterService;

	@Autowired
	private clsBusinessSourceMasterDao objBusinessSourceMasterDao;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open BusinessSourceMaster
	@RequestMapping(value = "/frmBusinessSource", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {

			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (urlHits.equalsIgnoreCase("1")) {

			return new ModelAndView("frmBusinessSource", "command", new clsBusinessSourceMasterBean());
		} else {
			return new ModelAndView("frmBusinessSource_1", "command", new clsBusinessSourceMasterBean());
		}
	}

	@RequestMapping(value = "/saveBusinessSourceMaster", method = RequestMethod.GET)
	public ModelAndView funAddUpdateBusinessSourceMaster(@ModelAttribute("command") @Valid clsBusinessSourceMasterBean objBusinessMasterBean, BindingResult result, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		clsBusinessSourceMasterModel objBusinessSourceMasterModel = objBusinessSourceMasterService.funPrepareBusinessModel(objBusinessMasterBean, clientCode, userCode);
		objBusinessSourceMasterDao.funAddUpdateBusinessMaster(objBusinessSourceMasterModel);
		req.getSession().setAttribute("success", true);
		req.getSession().setAttribute("successMessage", "Business Code : ".concat(objBusinessSourceMasterModel.getStrBusinessSourceCode()));

		return new ModelAndView("redirect:/frmBusinessSource.html");

	}

	@RequestMapping(value = "/loadBusinessMasterData", method = RequestMethod.GET)
	public @ResponseBody clsBusinessSourceMasterModel funFetchBusinessMasterData(@RequestParam("businessCode") String businessCode, HttpServletRequest req) {
		clsBusinessSourceMasterModel objBusinessSourceMasterModel = null;

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List listBusinessData = objBusinessSourceMasterDao.funGetBusinessMaster(businessCode, clientCode);
		if (listBusinessData.size() > 0) {
			objBusinessSourceMasterModel = (clsBusinessSourceMasterModel) listBusinessData.get(0);
		} else {
			objBusinessSourceMasterModel = new clsBusinessSourceMasterModel();
		}
		return objBusinessSourceMasterModel;
	}

}
