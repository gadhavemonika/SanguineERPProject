package com.sanguine.webpms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsGuestMasterBean;
import com.sanguine.webpms.dao.clsGuestMasterDao;
import com.sanguine.webpms.model.clsGuestMasterHdModel;
import com.sanguine.webpms.service.clsGuestMasterService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@Controller
public class clsGuestMasterController {
	@Autowired
	private clsGuestMasterService objGuestMasterService;

	@Autowired
	private clsGuestMasterDao objGuestMasterDao;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	// Open GuestMaster
	@RequestMapping(value = "/frmGuestMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String urlHits = "1";

		List<String> listPrefix = new ArrayList<>();
		listPrefix.add("Mr.");
		listPrefix.add("Mrs.");
		listPrefix.add("Both");
		model.put("prefix", listPrefix);

		List<String> listGender = new ArrayList<>();
		listGender.add("M");
		listGender.add("F");
		model.put("gender", listGender);
//WebStockDB
		String webStockDB=request.getSession().getAttribute("WebStockDB").toString();
		String sql = "select strCityName from "+webStockDB+".tblcitymaster where strClientCode='" + clientCode + "'";
		List list = objGlobalFunctionsService.funGetList(sql, "sql");
		List<String> listCity = new ArrayList<>();
		for (int cnt = 0; cnt < list.size(); cnt++) {
			listCity.add(list.get(cnt).toString());
		}
		model.put("listCity", listCity);

		sql = "select strStateName from "+webStockDB+".tblstatemaster where strClientCode='" + clientCode + "'";
		List listStateDetails = objGlobalFunctionsService.funGetList(sql, "sql");
		List<String> listState = new ArrayList<>();
		for (int cnt = 0; cnt < listStateDetails.size(); cnt++) {
			listState.add(listStateDetails.get(cnt).toString());
		}
		model.put("listState", listState);

		sql = "select strCountryName from "+webStockDB+".tblcountrymaster where strClientCode='" + clientCode + "'";
		List listCountryDetails = objGlobalFunctionsService.funGetList(sql, "sql");
		List<String> listCountry = new ArrayList<>();
		for (int cnt = 0; cnt < listCountryDetails.size(); cnt++) {
			listCountry.add(listCountryDetails.get(cnt).toString());
		}
		model.put("listCountry", listCountry);

		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (urlHits.equalsIgnoreCase("1")) {
			return new ModelAndView("frmGuestMaster", "command", new clsGuestMasterBean());
		} else {
			return new ModelAndView("frmGuestMaster_1", "command", new clsGuestMasterBean());
		}
	}

	// Save or Update GuestMaster
	@RequestMapping(value = "/saveGuestMaster", method = RequestMethod.GET)
	public ModelAndView funAddUpdateGuestMaster(@ModelAttribute("command") @Valid clsGuestMasterBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsGuestMasterHdModel objModel = objGuestMasterService.funPrepareGuestModel(objBean, clientCode, userCode);
			objGuestMasterDao.funAddUpdateGuestMaster(objModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Guest Code : ".concat(objModel.getStrGuestCode()));
			req.getSession().setAttribute("GuestDetails", objModel.getStrGuestCode()+"#"+objModel.getStrFirstName()+" "+objModel.getStrLastName()+"#"+objModel.getLngMobileNo());
			return new ModelAndView("redirect:/frmGuestMaster.html");
		} else {
			return new ModelAndView("frmGuestMaster");
		}
	}

	// Load data from database to form
	@RequestMapping(value = "/loadGuestCode", method = RequestMethod.GET)
	public @ResponseBody clsGuestMasterHdModel funFetchGuestMasterData(@RequestParam("guestCode") String guestCode, HttpServletRequest req) {
		clsGlobalFunctions objGlobal = new clsGlobalFunctions();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List listGuestData = objGuestMasterDao.funGetGuestMaster(guestCode, clientCode);
		clsGuestMasterHdModel objGuestMasterModel = (clsGuestMasterHdModel) listGuestData.get(0);
		objGuestMasterModel.setDteDOB(objGlobal.funGetDate("dd-MM-yyyy", objGuestMasterModel.getDteDOB()));
		objGuestMasterModel.setDtePassportExpiryDate(objGlobal.funGetDate("dd-MM-yyyy", objGuestMasterModel.getDtePassportExpiryDate()));
		objGuestMasterModel.setDtePassportIssueDate(objGlobal.funGetDate("dd-MM-yyyy", objGuestMasterModel.getDtePassportIssueDate()));
		objGuestMasterModel.setDteAnniversaryDate(objGlobal.funGetDate("dd-MM-yyyy", objGuestMasterModel.getDteAnniversaryDate()));

		return objGuestMasterModel;
	}
	@RequestMapping(value = "/checkGuestMobileNo", method = RequestMethod.GET)
	public @ResponseBody int funCheckGuestMobileNo(@RequestParam("mobileNo") String mobileNo, HttpServletRequest req) {
	
		clsGuestMasterHdModel objGuestMasterModel= new clsGuestMasterHdModel();
		clsGuestMasterBean objGuestMasterBean = new clsGuestMasterBean();
		objGuestMasterBean.setIntMobileNo(objGuestMasterModel.getLngMobileNo());
		String sql = "";
		int retunVal=0;
		sql = "select count(1) from tblguestmaster a where a.lngMobileNo='" + mobileNo + "'";
		List listGuestMaster = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		if(!listGuestMaster.isEmpty())
		{
			BigInteger bigintVal = (BigInteger) listGuestMaster.get(0);
			retunVal =bigintVal.intValue();
		}
		return retunVal;
	}
	



}
