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
import com.sanguine.webpms.bean.clsPMSReasonMasterBean;
import com.sanguine.webpms.dao.clsPMSReasonMasterDao;
import com.sanguine.webpms.model.clsPMSReasonMasterModel;
import com.sanguine.webpms.model.clsRoomTypeMasterModel;
import com.sanguine.webpms.service.clsPMSReasonMasterService;

@Controller
public class clsPMSReasonMasterController {

	@Autowired
	private clsPMSReasonMasterService objReasonMasterService;

	@Autowired
	private clsPMSReasonMasterDao objReasonMasterDao;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open ReasonMaster
	@RequestMapping(value = "/frmReason", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (urlHits.equalsIgnoreCase("1")) {

			return new ModelAndView("frmReason", "command", new clsPMSReasonMasterBean());
		} else {
			return new ModelAndView("frmReason_1", "command", new clsPMSReasonMasterBean());
		}
	}

	@RequestMapping(value = "/saveReasonMaster", method = RequestMethod.GET)
	public ModelAndView funAddUpdaterReasonMaster(@ModelAttribute("command") @Valid clsPMSReasonMasterBean objReasonMasterBean, BindingResult result, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		clsPMSReasonMasterModel objReasonMasterModel = objReasonMasterService.funPrepareReasonModel(objReasonMasterBean, clientCode, userCode);
		objReasonMasterDao.funAddUpdateReasonMaster(objReasonMasterModel);
		req.getSession().setAttribute("success", true);
		req.getSession().setAttribute("successMessage", "Reason Code : ".concat(objReasonMasterModel.getStrReasonCode()));

		return new ModelAndView("redirect:/frmReason.html");

	}

	@RequestMapping(value = "/loadPMSReasonMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPMSReasonMasterModel funFetchReasonMasterData(@RequestParam("reasonCode") String reasonCode, HttpServletRequest req) {
		clsPMSReasonMasterModel objReasonMasterModel = null;

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List listReasonData = objReasonMasterDao.funGetPMSReasonMaster(reasonCode, clientCode);
		objReasonMasterModel = (clsPMSReasonMasterModel) listReasonData.get(0);
		return objReasonMasterModel;
	}

}
