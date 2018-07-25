package com.sanguine.crm.controller;

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
import com.sanguine.bean.clsManufactureMasterBean;
import com.sanguine.crm.bean.clsSettlementMasterBean;
import com.sanguine.crm.service.clsCRMSettlementMasterService;
import com.sanguine.model.clsSettlementMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsCRMSettlementMasterController {
	@Autowired
	clsGlobalFunctions objGlobal;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	clsCRMSettlementMasterService objSttlementMasterService;

	@RequestMapping(value = "/frmCRMSettlementMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCRMSettlementMaster_1", "command", new clsSettlementMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCRMSettlementMaster", "command", new clsSettlementMasterBean());
		} else {
			return null;
		}
	}

	// Save or Update group master function to save or update record of group
	// master into database and also validates all fields of form.
	@RequestMapping(value = "/saveCRMSettlementMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsSettlementMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		if (!result.hasErrors()) {
			clsSettlementMasterModel objModel = funPrepareModel(objBean, userCode, clientCode);
			objSttlementMasterService.funAddUpdate(objModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Settlement Code : ".concat(objModel.getStrSettlementCode()));
			return new ModelAndView("redirect:/frmCRMSettlementMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmCRMSettlementMaster?saddr=" + urlHits, "command", new clsSettlementMasterBean());
		}

	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadSettlementMasterData", method = RequestMethod.GET)
	public @ResponseBody clsSettlementMasterModel funAssignFields(@RequestParam("code") String code, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsSettlementMasterModel objModel = objSttlementMasterService.funGetObject(code, clientCode);
		if (null == objModel) {
			objModel = new clsSettlementMasterModel();
			objModel.setStrSettlementCode("Invalid Code");
			return objModel;
		} else {
			return objModel;
		}

	}

	private clsSettlementMasterModel funPrepareModel(clsSettlementMasterBean objBean, String userCode, String clientCode) {
		long lastNo = 0;

		clsSettlementMasterModel objModel1 = objSttlementMasterService.funGetObject(objBean.getStrSettlementCode(), clientCode);
		clsSettlementMasterModel objModel = new clsSettlementMasterModel();
		if (objModel1 != null) {

			objModel.setStrSettlementCode(objModel1.getStrSettlementCode());
			objModel.setIntId(objModel1.getIntId());
			objModel.setStrUserCreated(objModel1.getStrUserCreated());
			objModel.setDtCreatedDate(objModel1.getDtCreatedDate());
			objModel.setStrClientCode(clientCode);
		} else {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblsettlementmaster", "SettlementMaster", "intId", clientCode);
			String strSettlementCode = "S" + String.format("%06d", lastNo);
			objModel.setStrSettlementCode(strSettlementCode);
			objModel.setIntId(lastNo);
			objModel.setStrUserCreated(userCode);
			objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			objModel.setStrClientCode(clientCode);
		}

		objModel.setStrSettlementDesc(objBean.getStrSettlementDesc());
		objModel.setStrApplicable(String.valueOf(objBean.isStrApplicable()));
		objModel.setStrSettlementType(objBean.getStrSettlementType());
		objModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserModified(userCode);
		return objModel;
	}

}
