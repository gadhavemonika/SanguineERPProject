package com.sanguine.controller;

import java.util.List;

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

import com.sanguine.bean.clsUDReportCategoryMasterBean;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsUDReportCategoryMasterModel;
import com.sanguine.model.clsUDReportCategoryMasterModel_ID;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsUDReportCategoryMasterService;

@Controller
public class clsUDReportCategoryMasterController {

	@Autowired
	private clsUDReportCategoryMasterService objUDReportCategoryMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open UDReportCategoryMaster
	@RequestMapping(value = "/frmUDReportCategoryMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm() {
		return new ModelAndView("frmUDReportCategoryMaster", "command", new clsUDReportCategoryMasterModel());
	}

	// Load Master Data On Form
	@RequestMapping(value = "/frmUDReportCategoryMaster1", method = RequestMethod.POST)
	public @ResponseBody clsUDReportCategoryMasterModel funLoadMasterData(HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String sql = "";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("userCode").toString();
		clsUDReportCategoryMasterBean objBean = new clsUDReportCategoryMasterBean();
		String docCode = request.getParameter("docCode").toString();
		List listModel = objGlobalFunctionsService.funGetList(sql);
		clsUDReportCategoryMasterModel objUDReportCategoryMaster = new clsUDReportCategoryMasterModel();
		return objUDReportCategoryMaster;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadUDCCode", method = RequestMethod.GET)
	public @ResponseBody clsUDReportCategoryMasterModel funAssignFields(@RequestParam("docCode") String docCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsUDReportCategoryMasterModel objModel = objUDReportCategoryMasterService.funGetUDReportCategoryMaster(docCode, clientCode);
		if (null == objModel) {
			objModel = new clsUDReportCategoryMasterModel();
			objModel.setStrUDCCode("Invalid Code");
		}

		return objModel;
	}

	// Save or Update UDReportCategoryMaster
	@RequestMapping(value = "/saveUDReportCategoryMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsUDReportCategoryMasterBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsUDReportCategoryMasterModel objModel = funPrepareModel(objBean, userCode, clientCode);
			objUDReportCategoryMasterService.funAddUpdateUDReportCategoryMaster(objModel);
			return new ModelAndView("redirect:/frmUDReportCategoryMaster.html");
		} else {
			return new ModelAndView("frmUDReportCategoryMaster");
		}
	}

	private clsUDReportCategoryMasterModel funPrepareModel(clsUDReportCategoryMasterBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsUDReportCategoryMasterModel objModel;

		if (objBean.getStrUDCCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tbludcategory", "udcategory", "intUDCode", clientCode);
			String udCode = "UD" + String.format("%06d", lastNo);
			objModel = new clsUDReportCategoryMasterModel(new clsUDReportCategoryMasterModel_ID());
			objModel.setIntUDCode(lastNo);
			objModel.setStrUDCCode(udCode);
			objModel.setStrUserCreated(userCode);
			objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsUDReportCategoryMasterModel objModel1 = objUDReportCategoryMasterService.funGetUDReportCategoryMaster(objBean.getStrUDCCode(), clientCode);
			if (null == objModel1) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblgroupmaster", "GroupMaster", "intGId", clientCode);
				String udCode = "UD" + String.format("%06d", lastNo);
				objModel = new clsUDReportCategoryMasterModel(new clsUDReportCategoryMasterModel_ID(udCode, clientCode));
				objModel.setIntUDCode(lastNo);
				objModel.setStrUDCCode(udCode);
				objModel.setStrUserCreated(userCode);
				objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

			} else {
				objModel = new clsUDReportCategoryMasterModel(new clsUDReportCategoryMasterModel_ID(objBean.getStrUDCCode(), clientCode));
			}
		}
		objModel.setStrUDCName(objBean.getStrUDCName());
		objModel.setStrUDCDesc(objBean.getStrUDCDesc());
		objModel.setStrUserModified(userCode);
		objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrClientCode(clientCode);
		return objModel;
	}
}
