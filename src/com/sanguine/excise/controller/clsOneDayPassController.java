package com.sanguine.excise.controller;

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
import com.sanguine.excise.bean.clsOneDayPassBean;
import com.sanguine.excise.model.clsOneDayPassHdModel;
import com.sanguine.excise.service.clsOneDayPassService;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsOneDayPassController {
	@Autowired
	public clsOneDayPassService objOneDayPassService;
	@Autowired
	public clsGlobalFunctions objGlobal;
	@Autowired
	public clsGlobalFunctionsService objGlobalFunctionsService;

	// open OneDayPass frmExciseOneDayPass
	@RequestMapping(value = "/frmExciseOneDayPass", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseOneDayPass_1", "command", new clsOneDayPassBean());
		} else {
			return new ModelAndView("frmExciseOneDayPass", "command", new clsOneDayPassBean());
		}

	}

	@RequestMapping(value = "/saveOneDayPass", method = RequestMethod.GET)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsOneDayPassBean oneDayPassBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		boolean success = false;

		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();

			clsOneDayPassHdModel obOneDayPass = funPrepareModel(oneDayPassBean, userCode, clientCode);

			if (obOneDayPass != null) {
				success = objOneDayPassService.funAddOneDayPass(obOneDayPass);
			} else {

				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "One Day Pass Already Added For This Day");
				return new ModelAndView("redirect:/frmExciseOneDayPass.html?saddr=" + urlHits);
			}

			if (success) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Data Successfully Saved \\n One Day Pass Id:-" + obOneDayPass.getIntId());
				return new ModelAndView("redirect:/frmExciseOneDayPass.html?saddr=" + urlHits);
			} else {
				return new ModelAndView("redirect:/frmExciseOneDayPass.html?saddr=" + urlHits);

			}
		} else {
			return new ModelAndView("redirect:/frmExciseOneDayPass.html?saddr=" + urlHits);
		}

	}

	@RequestMapping(value = "/loadOneDaypass", method = RequestMethod.GET)
	public @ResponseBody clsOneDayPassHdModel funAssignFields(@RequestParam("oneDayId") String oneDayId, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		clsOneDayPassHdModel obOneDayPassHdModel = objOneDayPassService.funGetOneDayPass(Long.parseLong(oneDayId), clientCode);
		if (obOneDayPassHdModel == null) {
			obOneDayPassHdModel = new clsOneDayPassHdModel();
			obOneDayPassHdModel.setIntId(new Long(0));
		}
		return obOneDayPassHdModel;
	}

	private clsOneDayPassHdModel funPrepareModel(clsOneDayPassBean passBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();

		clsOneDayPassHdModel objPassModel = new clsOneDayPassHdModel();

		clsOneDayPassHdModel objPassModelOld = null;
		if (passBean.getIntId() != null) {
			if (passBean.getIntId() != 0) {
				objPassModelOld = objOneDayPassService.funGetOneDayPass(passBean.getIntId(), clientCode);
			}
		}

		if (objPassModelOld != null) {

			objPassModel.setIntId(objPassModelOld.getIntId());
			objPassModel.setStrUserCreated(objPassModelOld.getStrUserCreated());
			objPassModel.setDteDateCreated(objPassModelOld.getDteDateCreated());
			objPassModel.setStrClientCode(clientCode);

		} else {

			clsOneDayPassHdModel objPassByDate = objOneDayPassService.funGetOneDayPassByDate(passBean.getDteDate(), clientCode);

			if (objPassByDate != null) {

				objPassModel = null;

			} else {

				objPassModel.setStrUserCreated(userCode);
				objPassModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objPassModel.setStrClientCode(clientCode);
			}
		}

		if (objPassModel != null) {
			objPassModel.setDteDate(passBean.getDteDate());
			objPassModel.setIntFromNo(passBean.getIntFromNo());
			objPassModel.setIntToNo(passBean.getIntToNo());
			objPassModel.setStrUserEdited(userCode);
			objPassModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		}

		return objPassModel;
	}

}
