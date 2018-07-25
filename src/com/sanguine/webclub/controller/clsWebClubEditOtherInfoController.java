package com.sanguine.webclub.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webclub.bean.clsWebClubEditOtherInfoBean;
import com.sanguine.webclub.model.clsWebClubEditOtherInfoModel;
import com.sanguine.webclub.service.clsWebClubEditOtherInfoService;

@Controller
public class clsWebClubEditOtherInfoController {

	@Autowired
	private clsWebClubEditOtherInfoService objWebClubEditOtherInfoService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open WebClubEditOtherInfo
	@RequestMapping(value = "/frmEditOtherInfo", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		List<String> listEditOtherInfo = new ArrayList<>();
		String sqlEditOtherInfo = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = 'tbleditotherinfo' ";
		listEditOtherInfo = objGlobalFunctionsService.funGetDataList(sqlEditOtherInfo, "sql");
		model.put("listEditOtherInfo", listEditOtherInfo);

		return new ModelAndView("frmEditOtherInfo", "command", new clsWebClubEditOtherInfoModel());
	}

	// Load Master Data On Form
	@RequestMapping(value = "/frmWebClubEditOtherInfo1", method = RequestMethod.POST)
	public @ResponseBody clsWebClubEditOtherInfoModel funLoadMasterData(HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String sql = "";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("userCode").toString();
		clsWebClubEditOtherInfoBean objBean = new clsWebClubEditOtherInfoBean();
		String docCode = request.getParameter("docCode").toString();
		List listModel = objGlobalFunctionsService.funGetList(sql);
		clsWebClubEditOtherInfoModel objWebClubEditOtherInfo = new clsWebClubEditOtherInfoModel();
		return objWebClubEditOtherInfo;
	}

	// Save or Update WebClubEditOtherInfo
	@RequestMapping(value = "/saveWebClubEditOtherInfo", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsWebClubEditOtherInfoBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("userCode").toString();
			clsWebClubEditOtherInfoModel objModel = funPrepareModel(objBean, userCode, clientCode);
			objWebClubEditOtherInfoService.funAddUpdateWebClubEditOtherInfo(objModel);
			return new ModelAndView("redirect:/frmEditOtherInfo.html");
		} else {
			return new ModelAndView("frmEditOtherInfo");
		}
	}

	// Convert bean to model function
	private clsWebClubEditOtherInfoModel funPrepareModel(clsWebClubEditOtherInfoBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsWebClubEditOtherInfoModel objModel;
		return null;

	}

}
