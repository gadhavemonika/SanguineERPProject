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
import com.sanguine.webpms.bean.clsBathTypeMasterBean;
import com.sanguine.webpms.dao.clsBaggageMasterDao;
import com.sanguine.webpms.dao.clsBathTypeMasterDao;
import com.sanguine.webpms.model.clsBaggageMasterModel;
import com.sanguine.webpms.model.clsBathTypeMasterModel;
import com.sanguine.webpms.service.clsBaggageMasterService;
import com.sanguine.webpms.service.clsBathTypeMasterService;

@Controller
public class clsBathTypeMasterController {
	@Autowired
	private clsBathTypeMasterService objBathTypeMasterService;

	@Autowired
	private clsBathTypeMasterDao objBathTypeMasterDao;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open BathTypeMaster
	@RequestMapping(value = "/frmBathTypeMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (urlHits.equalsIgnoreCase("1")) {

			return new ModelAndView("frmBathTypeMaster", "command", new clsBathTypeMasterBean());
		} else {
			return new ModelAndView("frmBathTypeMaster_1", "command", new clsBathTypeMasterBean());
		}
	}

	@RequestMapping(value = "/saveBathTypeMaster", method = RequestMethod.GET)
	public ModelAndView funAddUpdateBathTypeMaster(@ModelAttribute("command") @Valid clsBathTypeMasterBean objBathTypeMasterBean, BindingResult result, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		clsBathTypeMasterModel objBathTypeMasterModel = objBathTypeMasterService.funPrepareBathTypeModel(objBathTypeMasterBean, clientCode, userCode);
		objBathTypeMasterDao.funAddUpdateBathTypeMaster(objBathTypeMasterModel);
		req.getSession().setAttribute("success", true);
		req.getSession().setAttribute("successMessage", "BathType Code : ".concat(objBathTypeMasterModel.getStrBathTypeCode()));

		return new ModelAndView("redirect:/frmBathTypeMaster.html");

	}

	// Load bath type data to form
	@RequestMapping(value = "/loadBathTypeMasterData", method = RequestMethod.GET)
	public @ResponseBody clsBathTypeMasterModel funFetchBathTypeMasterData(@RequestParam("bathTypeCode") String bathTypeCode, HttpServletRequest req) {
		clsBathTypeMasterModel objBathTypeMasterModel = null;

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List listBathTypeData = objBathTypeMasterDao.funGetBathTypeMaster(bathTypeCode, clientCode);
		objBathTypeMasterModel = (clsBathTypeMasterModel) listBathTypeData.get(0);
		return objBathTypeMasterModel;
	}

}
