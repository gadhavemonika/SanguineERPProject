package com.sanguine.excise.controller;

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
import com.sanguine.excise.bean.clsOpeningStockBean;
import com.sanguine.excise.model.clsBrandMasterModel;
import com.sanguine.excise.model.clsOpeningStockModel;
import com.sanguine.excise.service.clsOpeningStockService;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsOpeningStockController {

	@Autowired
	private clsOpeningStockService objExBrandOpMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	private clsGlobalFunctions objGlobal = null;

	private long intIDOpening;

	// Open ExciseBrandOpeningMaster
	@SuppressWarnings("unused")
	@RequestMapping(value = "/frmExciseOpeningStock", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		// String
		// sql_Licence="select strLicenceCode,strLicenceNo from tbllicencemaster where "
		// +
		// "strClientCode='"+clientCode+"' and  strPropertyCode='"+propertyCode+"'";
		// List LicenceList=
		// objGlobalFunctionsService.funGetDataList(sql_Licence, "sql");
		//
		// if(LicenceList.size()>0){
		// Object obj[] = (Object[]) LicenceList.get(0);
		// model.put("LicenceCode",obj[0]);
		// model.put("LicenceNo",obj[1]);
		// }else{
		// req.getSession().setAttribute("success", true);
		// req.getSession().setAttribute("successMessage","Please Add Licence To This Client");
		// }

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseOpeningStock_1", "command", new clsOpeningStockBean());
		} else {
			return new ModelAndView("frmExciseOpeningStock", "command", new clsOpeningStockBean());
		}
	}

	// Load Master Data On Form
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadExciseBrandOpeningMasterData", method = RequestMethod.GET)
	public @ResponseBody clsOpeningStockModel funLoadMasterData(@RequestParam("intId") String intId, HttpServletRequest req) {

		objGlobal = new clsGlobalFunctions();
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		clsOpeningStockModel objModel = null;
		Long Id = new Long(0);
		try {
			Id = Long.parseLong(intId);
		} catch (Exception e) {
			objModel = new clsOpeningStockModel();
			objModel.setIntId(new Long(0));
			return objModel;
		}

		List objList = objExBrandOpMasterService.funGetMasterObject(Id, clientCode);
		if (objList.isEmpty()) {
			objModel = new clsOpeningStockModel();
			objModel.setIntId(new Long(0));
		} else {
			Object obj[] = (Object[]) objList.get(0);
			clsOpeningStockModel objMasterModel = (clsOpeningStockModel) obj[0];
			clsBrandMasterModel objbrandModel = (clsBrandMasterModel) obj[1];
			objModel = objMasterModel;
			objModel.setStrBrandName(objbrandModel.getStrBrandName());
		}

		return objModel;
	}

	// Save or Update ExciseBrandOpeningMaster
	@RequestMapping(value = "/saveExciseBrandOpeningMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsOpeningStockBean objBean, BindingResult result, HttpServletRequest req) {

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsOpeningStockModel objModel = funPrepareModel(objBean, userCode, clientCode, req);
			boolean success = objExBrandOpMasterService.funAddUpdateExBrandOpMaster(objModel);
			if (success) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", objModel.getIntId());
				return new ModelAndView("redirect:/frmExciseOpeningStock.html?saddr=" + urlHits);
			} else {

				req.getSession().setAttribute("warning", true);
				req.getSession().setAttribute("warningMessage", intIDOpening);
				return new ModelAndView("redirect:/frmExciseOpeningStock.html?saddr=" + urlHits);
			}
		} else {
			return new ModelAndView("redirect:/frmExciseOpeningStock.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsOpeningStockModel funPrepareModel(clsOpeningStockBean objBean, String userCode, String clientCode, HttpServletRequest req) {

		clsOpeningStockModel objModel = new clsOpeningStockModel();
		objGlobal = new clsGlobalFunctions();
		String statrDate = req.getSession().getAttribute("startDate").toString();
		String[] spDate = statrDate.split("/");
		String day = spDate[0];
		// String[] spDate1 =monthYear.split("/");
		String month = spDate[1];
		String yr = spDate[2];
		String stDate = yr + "-" + month + "-" + day;

		if (objBean != null) {
			if (objBean.getIntId() != null) {
				clsOpeningStockModel objModel1 = objExBrandOpMasterService.funGetExBrandOpMaster(objBean.getIntId(), clientCode);
				if (objModel1 != null) {
					objModel.setIntId(objModel1.getIntId());
					objModel.setStrUserCreated(objModel1.getStrUserCreated());
					objModel.setDteDateCreated(stDate);
				}
			} else {

				objModel = objExBrandOpMasterService.funGetOpenedEXBrandMaster(objBean.getStrBrandCode(), objBean.getStrLicenceCode(), clientCode);
				if (objModel != null) {
					intIDOpening = objModel.getIntId();
					return objModel = null;

				} else {
					objModel = new clsOpeningStockModel();
					objModel.setStrUserCreated(userCode);
					objModel.setDteDateCreated(stDate);
				}
			}

			objModel.setStrBrandCode(objBean.getStrBrandCode());
			objModel.setIntOpBtls(objBean.getIntOpBtls() != null ? objBean.getIntOpBtls() : 0);
			objModel.setIntOpML(objBean.getIntOpML() != null ? objBean.getIntOpML() : 0);
			objModel.setIntOpPeg(objBean.getIntOpPeg() != null ? objBean.getIntOpPeg() : 0);
			objModel.setStrClientCode(clientCode);
			objModel.setStrUserEdited(userCode);
			objModel.setDteDateEdited(stDate);
			objModel.setStrLicenceCode(objBean.getStrLicenceCode().toString());
		}
		return objModel;
	}
}
