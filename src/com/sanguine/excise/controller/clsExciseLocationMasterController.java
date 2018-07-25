package com.sanguine.excise.controller;

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
import com.sanguine.excise.bean.clsExciseLocationMasterBean;
import com.sanguine.excise.model.clsExciseLocationMasterModel;
import com.sanguine.excise.service.clsExciseLocationMasterService;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsExciseLocationMasterController {
	@Autowired
	private clsExciseLocationMasterService objClsExciseLocationMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@RequestMapping(value = "/frmExciseLocationMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		List<String> listType = new ArrayList<>();
		listType.add("Stores");
		listType.add("Cost Center");
		listType.add("Profit Center");
		model.put("listType", listType);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseLocationMaster_1", "command", new clsExciseLocationMasterModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseLocationMaster", "command", new clsExciseLocationMasterModel());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/saveExciseLocationMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsExciseLocationMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		if (!result.hasErrors()) {
			objGlobal = new clsGlobalFunctions();
			clsExciseLocationMasterModel objModel = funPrepareLocationModel(objBean, userCode, clientCode);
			boolean success = objClsExciseLocationMasterService.funAddUpdate(objModel);
			if (success) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Location : ".concat(objModel.getStrLocCode()));
				return new ModelAndView("redirect:/frmExciseLocationMaster.html?saddr=" + urlHits);
			} else {
				return new ModelAndView("frmExciseLocationMaster?saddr=" + urlHits);
			}
		} else {
			return new ModelAndView("frmExciseLocationMaster?saddr=" + urlHits);
		}
	}

	@RequestMapping(value = "/loadExciseLocationMasterData", method = RequestMethod.GET)
	public @ResponseBody clsExciseLocationMasterModel funAssignFields(@RequestParam("locCode") String locCode, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsExciseLocationMasterModel objLocCode = objClsExciseLocationMasterService.funGetObject(locCode, clientCode);
		if (null == objLocCode) {
			objLocCode = new clsExciseLocationMasterModel();
			objLocCode.setStrLocCode("Invalid Code");
		}

		return objLocCode;
	}

	private clsExciseLocationMasterModel funPrepareLocationModel(clsExciseLocationMasterBean objLocationBean, String userCode, String clientCode) {
		long lastNo = 0;
		clsExciseLocationMasterModel objModel = new clsExciseLocationMasterModel();

		if (objLocationBean != null) {
			if (!(objLocationBean.getStrLocCode().isEmpty())) {
				clsExciseLocationMasterModel objModel1 = objClsExciseLocationMasterService.funGetObject(objLocationBean.getStrLocCode(), clientCode);
				if (objModel1 != null) {
					objModel.setStrLocCode(objModel1.getStrLocCode());
					objModel.setStrUserCreated(objModel1.getStrUserCreated());
					objModel.setDteDateCreated(objModel1.getDteDateCreated());
					objModel.setIntId(objModel1.getIntId());
					objModel.setStrClientCode(objModel1.getStrClientCode());
				}
			} else {
				lastNo = objGlobalFunctionsService.funGetCount("tblexciselocationmaster", "intId");
				String locationCode = "EL" + String.format("%06d", lastNo);

				objModel.setStrLocCode(locationCode);
				objModel.setStrUserCreated(userCode);
				objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setIntId(lastNo);
				objModel.setStrClientCode(clientCode);
			}

			objModel.setStrLocName(objLocationBean.getStrLocName());
			objModel.setStrLocDesc(objLocationBean.getStrLocDesc());
			objModel.setStrAvlForSale(objGlobal.funIfNull(objLocationBean.getStrAvlForSale(), "N", "Y"));
			objModel.setStrActive(objGlobal.funIfNull(objLocationBean.getStrActive(), "N", "Y"));
			objModel.setStrPickable(objGlobal.funIfNull(objLocationBean.getStrPickable(), "N", "Y"));
			objModel.setStrReceivable(objGlobal.funIfNull(objLocationBean.getStrReceivable(), "N", "Y"));
			objModel.setStrExciseNo(objLocationBean.getStrExciseNo());
			objModel.setStrType(objLocationBean.getStrType());
			objModel.setStrPropertyCode(objLocationBean.getStrPropertyCode());
			objModel.setStrMonthEnd(objLocationBean.getStrMonthEnd());
			objModel.setStrExternalCode(objLocationBean.getStrExternalCode());
			objModel.setStrLocPropertyCode(objLocationBean.getStrLocPropertyCode());
			objModel.setStrUserModified(userCode);
			objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		}
		return objModel;
	}

}
