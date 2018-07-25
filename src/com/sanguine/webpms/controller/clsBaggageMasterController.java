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
import com.sanguine.webpms.dao.clsBaggageMasterDao;
import com.sanguine.webpms.model.clsBaggageMasterModel;
import com.sanguine.webpms.model.clsDepartmentMasterModel;
import com.sanguine.webpms.service.clsBaggageMasterService;

@Controller
public class clsBaggageMasterController {
	@Autowired
	private clsBaggageMasterService objBaggageMasterService;

	@Autowired
	private clsBaggageMasterDao objBaggageMasterDao;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open BaggageMaster
	@RequestMapping(value = "/frmBaggageMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (urlHits.equalsIgnoreCase("1")) {

			return new ModelAndView("frmBaggageMaster", "command", new clsBaggageMasterBean());
		} else {
			return new ModelAndView("frmBaggageMaster_1", "command", new clsBaggageMasterBean());
		}
	}

	@RequestMapping(value = "/saveBaggageMaster", method = RequestMethod.GET)
	public ModelAndView funAddUpdateBaggageMaster(@ModelAttribute("command") @Valid clsBaggageMasterBean objBaggageMasterBean, BindingResult result, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		clsBaggageMasterModel objBaggageMasterModel = objBaggageMasterService.funPrepareBaggageModel(objBaggageMasterBean, clientCode, userCode);
		objBaggageMasterDao.funAddUpdateBaggageMaster(objBaggageMasterModel);
		req.getSession().setAttribute("success", true);
		req.getSession().setAttribute("successMessage", "Baggage Code : ".concat(objBaggageMasterModel.getStrBaggageCode()));

		return new ModelAndView("redirect:/frmBaggageMaster.html");

	}

	@RequestMapping(value = "/loadBaggageMasterData", method = RequestMethod.GET)
	public @ResponseBody clsBaggageMasterModel funFetchBaggageMasterData(@RequestParam("baggageCode") String baggageCode, HttpServletRequest req) {
		clsBaggageMasterModel objBaggageMasterModel = null;

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List listDepData = objBaggageMasterDao.funGetBaggageMaster(baggageCode, clientCode);
		objBaggageMasterModel = (clsBaggageMasterModel) listDepData.get(0);

		return objBaggageMasterModel;
	}

}
