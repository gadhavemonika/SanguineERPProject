package com.sanguine.webpms.controller;

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
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsCorporateMasterBean;
import com.sanguine.webpms.model.clsCorporateMasterHdModel;
import com.sanguine.webpms.service.clsCorporateMasterService;

@Controller
public class clsCorporateMasterController {

	@Autowired
	private clsCorporateMasterService objCorporateMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	// Open CorporateMaster
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/frmCorporateMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String webStockDB=request.getSession().getAttribute("WebStockDB").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		ArrayList cityArrList = new ArrayList();
		ArrayList stateArrList = new ArrayList();
		ArrayList countryArrList = new ArrayList();

		String citySql = "select strCityName from "+webStockDB+".tblcitymaster where strClientCode='" + clientCode + "' ";
		List cityList = objGlobalFunctionsService.funGetDataList(citySql, "sql");
		if (cityList.size() > 0) {
			for (int cnt = 0; cnt < cityList.size(); cnt++) {
				cityArrList.add(cityList.get(cnt));
			}
		}

		String stateSql = "select strStateName from "+webStockDB+".tblstatemaster where strClientCode='" + clientCode + "' ";
		List stateList = objGlobalFunctionsService.funGetDataList(stateSql, "sql");
		if (stateList.size() > 0) {
			for (int cnt = 0; cnt < stateList.size(); cnt++) {
				stateArrList.add(stateList.get(cnt));
			}
		}

		String countrySql = "select strCountryName from "+webStockDB+".tblcountrymaster where strClientCode='" + clientCode + "' ";
		List countryList = objGlobalFunctionsService.funGetDataList(countrySql, "sql");
		if (countryList.size() > 0) {
			for (int cnt = 0; cnt < countryList.size(); cnt++) {
				countryArrList.add(countryList.get(cnt));
			}
		}

		model.put("cityArrLsit", cityArrList);
		model.put("stateArrLsit", stateArrList);
		model.put("countryArrLsit", countryArrList);

		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCorporateMaster_1", "command", new clsCorporateMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCorporateMaster", "command", new clsCorporateMasterBean());
		} else {
			return null;
		}
	}

	// Save or Update CorporateMaster
	@RequestMapping(value = "/saveCorporateMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsCorporateMasterBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsCorporateMasterHdModel objModel = objCorporateMasterService.funPrepareModel(objBean, userCode, clientCode);
			objCorporateMasterService.funAddUpdateCorporateMaster(objModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Corporate Code : ".concat(objModel.getStrCorporateCode()));
			return new ModelAndView("redirect:/frmCorporateMaster.html");
		} else {
			return new ModelAndView("frmCorporateMaster");
		}
	}

	// Load Corporate Master Data On Form
	@RequestMapping(value = "/loadCorporateCode", method = RequestMethod.GET)
	public @ResponseBody clsCorporateMasterHdModel funLoadCorporateCode(@RequestParam("corpcode") String corpcode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsCorporateMasterHdModel objModel = objCorporateMasterService.funGetCorporateMaster(corpcode, clientCode);
		if (objModel == null) {
			objModel = new clsCorporateMasterHdModel();
			objModel.setStrCorporateCode("Invalid Code");
		}
		return objModel;
	}
}
