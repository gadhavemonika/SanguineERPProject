package com.sanguine.controller;

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

import com.sanguine.bean.clsAttributeValueMasterBean;
import com.sanguine.model.clsAttributeValueMasterModel;
import com.sanguine.model.clsAttributeValueMasterModel_ID;
import com.sanguine.service.clsAttributeValueMasterService;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsAttributeValueMasterController {
	@Autowired
	private clsAttributeValueMasterService objAttrValService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal;

	@RequestMapping(value = "/frmAttributeValueMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmAttributeValueMaster_1", "command", new clsAttributeValueMasterModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmAttributeValueMaster", "command", new clsAttributeValueMasterModel());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/saveAttributeValueMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsAttributeValueMasterBean objBean, BindingResult result, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		if (!result.hasErrors()) {
			clsAttributeValueMasterModel objModel = funPrepareAttrValueModel(objBean, userCode, clientCode);
			objAttrValService.funAddUpdate(objModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Attribute Value Code : ".concat(objModel.getStrAVCode()));
			return new ModelAndView("redirect:/frmAttributeValueMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmAttributeValueMaster?saddr=" + urlHits);
		}
	}

	@RequestMapping(value = "/loadAttributeValueMasterData", method = RequestMethod.GET)
	public @ResponseBody clsAttributeValueMasterModel funAssignFields(@RequestParam("attValueCode") String AVCode, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsAttributeValueMasterModel objModel = objAttrValService.funGetObject(AVCode, clientCode);

		if (null == objModel) {
			objModel = new clsAttributeValueMasterModel();
			objModel.setStrAVCode("Invalid Code");
		}

		return objModel;
	}

	private clsAttributeValueMasterModel funPrepareAttrValueModel(clsAttributeValueMasterBean objBean, String userCode, String clientCode) {
		long lastNo = 0;
		clsAttributeValueMasterModel objModel;

		if (objBean.getStrAVCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblattvaluemaster", "AttributeValueMaster", "intId", clientCode);
			String AVCode = "AV" + String.format("%07d", lastNo);
			objModel = new clsAttributeValueMasterModel(new clsAttributeValueMasterModel_ID(AVCode, clientCode));
			objModel.setIntId(lastNo);
			objModel.setStrUserCreated(userCode);
			objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsAttributeValueMasterModel objModel1 = objAttrValService.funGetObject(objBean.getStrAVCode(), clientCode);
			if (null == objModel1) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblattvaluemaster", "AttributeValueMaster", "intId", clientCode);
				String AVCode = "AV" + String.format("%07d", lastNo);
				objModel = new clsAttributeValueMasterModel(new clsAttributeValueMasterModel_ID(AVCode, clientCode));
				objModel.setIntId(lastNo);
				objModel.setStrUserCreated(userCode);
				objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				objModel = new clsAttributeValueMasterModel(new clsAttributeValueMasterModel_ID(objBean.getStrAVCode(), clientCode));
			}
		}
		objModel.setStrAVName(objBean.getStrAVName());
		objModel.setStrAVDesc(objBean.getStrAVDesc());
		objModel.setStrAttCode(objBean.getStrAttCode());
		objModel.setStrUserModified(userCode);
		objModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		return objModel;
	}
}

// yhuyhuy6