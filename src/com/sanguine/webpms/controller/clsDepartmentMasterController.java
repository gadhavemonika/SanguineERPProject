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

import com.sanguine.bean.clsGroupMasterBean;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webbooks.service.clsBankMasterService;
import com.sanguine.webpms.bean.clsDepartmentMasterBean;
import com.sanguine.webpms.dao.clsDepartmentMasterDao;
import com.sanguine.webpms.model.clsDepartmentMasterModel;
import com.sanguine.webpms.service.clsDepartmentMasterService;

@Controller
public class clsDepartmentMasterController {

	@Autowired
	private clsDepartmentMasterService objDeptMasterService;

	@Autowired
	private clsDepartmentMasterDao objDeptMasterDao;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open DepartmentMaster
	@RequestMapping(value = "/frmDepartmentMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (urlHits.equalsIgnoreCase("1")) {

			return new ModelAndView("frmDepartmentMaster", "command", new clsDepartmentMasterBean());
		} else {
			return new ModelAndView("frmDepartmentMaster_1", "command", new clsDepartmentMasterBean());
		}
	}

	@RequestMapping(value = "/saveDepartmentMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdateDeptMaster(@ModelAttribute("command") @Valid clsDepartmentMasterBean objDeptMasterBean, BindingResult result, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		clsDepartmentMasterModel objDeptMasterModel = objDeptMasterService.funPrepareDeptModel(objDeptMasterBean, clientCode, userCode);
		objDeptMasterDao.funAddUpdateDeptMaster(objDeptMasterModel);

		req.getSession().setAttribute("success", true);
		req.getSession().setAttribute("successMessage", "Dept Code : ".concat(objDeptMasterModel.getStrDeptCode()));

		return new ModelAndView("redirect:/frmDepartmentMaster.html");

	}

	@RequestMapping(value = "/loadDeptMasterData", method = RequestMethod.GET)
	public @ResponseBody clsDepartmentMasterModel funFetchDeptMasterData(@RequestParam("deptCode") String deptCode, HttpServletRequest req) {
		clsDepartmentMasterModel objDepartmentMasterModel = null;

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List listDepData = objDeptMasterDao.funGetDepartmentMaster(deptCode, clientCode);
		objDepartmentMasterModel = (clsDepartmentMasterModel) listDepData.get(0);

		return objDepartmentMasterModel;
	}

}