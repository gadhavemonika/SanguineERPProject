package com.sanguine.excise.controller;

import java.util.ArrayList;
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

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.excise.bean.clsExcisePermitMasterBean;
import com.sanguine.excise.model.clsExcisePermitMasterModel;
import com.sanguine.excise.service.clsExcisePermitMasterService;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsExcisePermitMasterController {

	@Autowired
	private clsExcisePermitMasterService objOneDayPermitMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobal = null;

	// Open OneDayPermitMaster
	@RequestMapping(value = "/frmExcisePermitMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		ArrayList<String> permitType = new ArrayList<String>();
		permitType.add("Permit Holder");
		permitType.add("Temporal Holder");

		model.put("permitType", permitType);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExcisePermitMaster_1", "command", new clsExcisePermitMasterBean());
		} else {
			return new ModelAndView("frmExcisePermitMaster", "command", new clsExcisePermitMasterBean());
		}
	}

	// Load Master Data On Form
	@RequestMapping(value = "/loadExcisePermitMaster", method = RequestMethod.POST)
	public @ResponseBody clsExcisePermitMasterModel funLoadMasterData(HttpServletRequest req) {

		objGlobal = new clsGlobalFunctions();
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		clsExcisePermitMasterModel objModel = null;
		String permitCode = req.getParameter("permitCode").toString();

		objModel = objOneDayPermitMasterService.funGetPermitMaster(permitCode, clientCode);

		if (objModel == null) {

			objModel = new clsExcisePermitMasterModel();
			objModel.setStrPermitCode("Invalid Code");
		}

		return objModel;

	}

	// Save or Update ExcisePermitMaster
	@RequestMapping(value = "/saveExcisePermitMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsExcisePermitMasterBean objBean, BindingResult result, HttpServletRequest req) {

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (!result.hasErrors()) {
			clsExcisePermitMasterModel objclsPermitMasterModel = funPrepareModel(objBean, req);
			boolean success = objOneDayPermitMasterService.funAddUpdatePermitMaster(objclsPermitMasterModel);
			if (success) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Permit No : ".concat(objclsPermitMasterModel.getStrPermitNo()));
				return new ModelAndView("redirect:/frmExcisePermitMaster.html?saddr=" + urlHits);
			} else {
				return new ModelAndView("redirect:/frmExcisePermitMaster.html?saddr=" + urlHits);
			}
		} else {
			return new ModelAndView("redirect:/frmExcisePermitMaster.html?saddr=" + urlHits);
		}
	}

	private clsExcisePermitMasterModel funPrepareModel(clsExcisePermitMasterBean objBean, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();

		clsExcisePermitMasterModel objModel = new clsExcisePermitMasterModel();

		if (objBean != null) {
			if (!(objBean.getStrPermitCode().isEmpty())) {
				clsExcisePermitMasterModel objModel1 = objOneDayPermitMasterService.funGetPermitMaster(objBean.getStrPermitCode(), clientCode);
				if (objModel1 != null) {

					objModel.setStrPermitCode(objModel1.getStrPermitCode());
					objModel.setStrUserCreated(objModel1.getStrUserCreated());
					objModel.setDteDateCreated(objModel1.getDteDateCreated());
					objModel.setIntId(objModel1.getIntId());
				}
			} else {

				Long lastNo = objGlobalFunctionsService.funGetCount("tblexcisepermitmaster", "intId");
				String strPermitCode = "P" + String.format("%06d", lastNo);

				objModel.setStrPermitCode(strPermitCode);
				objModel.setStrUserCreated(userCode);
				objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setIntId(lastNo);
			}

			objModel.setStrPermitName(objBean.getStrPermitName());
			objModel.setStrPermitNo(objBean.getStrPermitNo());

			if (objBean.getIsLifeTime() != null) {
				if (objBean.getIsLifeTime().equalsIgnoreCase("Life Time")) {
					objModel.setDtePermitExp(objBean.getIsLifeTime());
				} else {
					objModel.setDtePermitExp(objBean.getDtePermitExp());
				}
			} else {
				objModel.setDtePermitExp(objBean.getDtePermitExp());
			}
			objModel.setStrPermitType(objBean.getStrPermitType());
			objModel.setStrClientCode(clientCode);
			objModel.setStrUserEdited(userCode);
			objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		}

		return objModel;
	}

}
