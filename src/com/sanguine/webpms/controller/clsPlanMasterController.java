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
import com.sanguine.webpms.bean.clsBaggageMasterBean;
import com.sanguine.webpms.bean.clsDepartmentMasterBean;
import com.sanguine.webpms.bean.clsPlanMasterBean;
import com.sanguine.webpms.dao.clsBaggageMasterDao;
import com.sanguine.webpms.dao.clsPlanMasterDao;
import com.sanguine.webpms.model.clsBaggageMasterModel;
import com.sanguine.webpms.model.clsPMSReasonMasterModel;
import com.sanguine.webpms.model.clsPlanMasterModel;
import com.sanguine.webpms.service.clsBaggageMasterService;
import com.sanguine.webpms.service.clsPlanMasterService;

@Controller
public class clsPlanMasterController {

	@Autowired
	private clsPlanMasterService objPlanMasterService;

	@Autowired
	private clsPlanMasterDao objPlanMasterDao;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open PlanMaster
	@RequestMapping(value = "/frmPlanMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (urlHits.equalsIgnoreCase("1")) {

			return new ModelAndView("frmPlanMaster", "command", new clsPlanMasterBean());
		} else {
			return new ModelAndView("frmPlanMaster_1", "command", new clsPlanMasterBean());
		}
	}

	@RequestMapping(value = "/savePlanMaster", method = RequestMethod.GET)
	public ModelAndView funAddUpdatePlanMaster(@ModelAttribute("command") @Valid clsPlanMasterBean objPlanMasterBean, BindingResult result, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		clsPlanMasterModel objPlanMasterModel = objPlanMasterService.funPreparePlanModel(objPlanMasterBean, clientCode, userCode);
		objPlanMasterDao.funAddUpdatePlanMaster(objPlanMasterModel);
		req.getSession().setAttribute("success", true);
		req.getSession().setAttribute("successMessage", "Plan Code : ".concat(objPlanMasterModel.getStrPlanCode()));

		return new ModelAndView("redirect:/frmPlanMaster.html");

	}

	@RequestMapping(value = "/loadPlanMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPlanMasterModel funFetchPlanMasterData(@RequestParam("planCode") String planCode, HttpServletRequest req) {
		clsPlanMasterModel objPlanMasterModel = null;

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List listPlanData = objPlanMasterDao.funGetPlanMaster(planCode, clientCode);
		objPlanMasterModel = (clsPlanMasterModel) listPlanData.get(0);
		return objPlanMasterModel;
	}

}
