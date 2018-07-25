package com.sanguine.excise.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
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
import com.sanguine.excise.bean.clsBrandMasterBean;
import com.sanguine.excise.model.clsBrandMasterModel;
import com.sanguine.excise.model.clsCategoryMasterModel;
import com.sanguine.excise.model.clsRateMasterModel;
import com.sanguine.excise.model.clsSizeMasterModel;
import com.sanguine.excise.model.clsSubCategoryMasterModel;
import com.sanguine.excise.service.clsBrandMasterService;
import com.sanguine.excise.service.clsSizeMasterService;
import com.sanguine.service.clsGlobalFunctionsService;

@SuppressWarnings({ "rawtypes" })
@Controller
public class clsBrandMasterController {

	@Autowired
	private clsBrandMasterService objBrandMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsFLR3AController objclsFLR3AController;

	@Autowired
	private clsSizeMasterService objSizeMasterService;

	// Open BrandMaster
	@RequestMapping(value = "/frmExciseBrandMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseBrandMaster_1", "command", new clsBrandMasterBean());
		} else {
			return new ModelAndView("frmExciseBrandMaster", "command", new clsBrandMasterBean());
		}

	}

	// Save or Update BrandMaster
	@RequestMapping(value = "/saveExciseBrandMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsBrandMasterBean objBean, BindingResult result, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		String isBrandGlobal = "Custom";
		try {
			isBrandGlobal = req.getSession().getAttribute("strBrandMaster").toString();
		} catch (Exception e) {
			isBrandGlobal = "Custom";
		}
		String tempClientCode = clientCode;
		if (isBrandGlobal.equalsIgnoreCase("All")) {
			tempClientCode = "All";
		}

		if (!result.hasErrors()) {
			clsBrandMasterModel objclsBrandMasterModel = funPrepareModel(objBean, userCode, tempClientCode);
			boolean success = objBrandMasterService.funAddUpdateBrandMaster(objclsBrandMasterModel);
			if (success) {
				clsRateMasterModel rateModel = new clsRateMasterModel();

				rateModel.setDblRate(objBean.getDblRate() != null ? objBean.getDblRate() : 0);
				rateModel.setStrBrandCode(objclsBrandMasterModel.getStrBrandCode());
				rateModel.setDteDateCreated(objclsBrandMasterModel.getDteDateCreated());
				rateModel.setDteDateEdited(objclsBrandMasterModel.getDteDateEdited());
				rateModel.setStrUserCreated(objclsBrandMasterModel.getStrUserCreated());
				rateModel.setStrUserEdited(objclsBrandMasterModel.getStrUserEdited());
				rateModel.setStrClientCode(clientCode);

				boolean rateSuccess = objBrandMasterService.funAddUpdateRateMaster(rateModel);

				if (rateSuccess) {
					req.getSession().setAttribute("success", true);
					req.getSession().setAttribute("successMessage", "Brand Name : ".concat(objclsBrandMasterModel.getStrBrandName()));
					return new ModelAndView("redirect:/frmExciseBrandMaster.html?saddr=" + urlHits);
				} else {
					return new ModelAndView("redirect:/frmExciseBrandMaster.html?saddr=" + urlHits);
				}

			} else {
				return new ModelAndView("redirect:/frmExciseBrandMaster.html?saddr=" + urlHits);
			}
		} else {
			return new ModelAndView("redirect:/frmExciseBrandMaster.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsBrandMasterModel funPrepareModel(clsBrandMasterBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		Long lastNo = new Long(0);

		clsBrandMasterModel objModel = new clsBrandMasterModel();

		if (objBean != null) {
			if (!(objBean.getStrBrandCode().isEmpty())) {
				List objList = objBrandMasterService.funGetObject(objBean.getStrBrandCode(), clientCode);
				Object obj[] = (Object[]) objList.get(0);
				clsBrandMasterModel objModel1 = (clsBrandMasterModel) obj[0];
				if (objModel1 != null) {
					objModel.setStrBrandCode(objModel1.getStrBrandCode());
					objModel.setStrUserCreated(objModel1.getStrUserCreated());
					objModel.setDteDateCreated(objModel1.getDteDateCreated());
					objModel.setIntId(objModel1.getIntId());
				}
			} else {

				lastNo = objGlobalFunctionsService.funGetCount("tblbrandmaster", "intId");
				String brandCode = "B" + String.format("%04d", lastNo);
				objModel.setStrBrandCode(brandCode);
				objModel.setStrUserCreated(userCode);
				objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setIntId(lastNo);
			}

			objModel.setStrBrandName(objBean.getStrBrandName());
			objModel.setStrShortName(objBean.getStrShortName());
			objModel.setStrSubCategoryCode(objBean.getStrSubCategoryCode());
			objModel.setStrSizeCode(objBean.getStrSizeCode());
			objModel.setDblStrength(objBean.getDblStrength() != null ? objBean.getDblStrength() : 0);
			objModel.setIntPegSize(objBean.getIntPegSize() != null ? objBean.getIntPegSize() : 0);
			objModel.setStrClientCode(clientCode);
			objModel.setStrUserEdited(userCode);
			objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		}

		return objModel;

	}

	// Assign filed function to set data onto form for edit transaction.
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadExciseBrandMasterData", method = RequestMethod.GET)
	public @ResponseBody clsBrandMasterModel funAssignFieldsForForm(@RequestParam("brandCode") String brandCode, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String isBrandGlobal = "Custom";
		String isSizeGlobal = "Custom";
		String tempSizeClientCode = clientCode;
		try {
			isSizeGlobal = req.getSession().getAttribute("strSizeMaster").toString();
		} catch (Exception e) {
			isSizeGlobal = "Custom";
		}
		if (isSizeGlobal.equalsIgnoreCase("All")) {
			tempSizeClientCode = "All";
		}

		String tempBrandClientCode = clientCode;
		try {
			isBrandGlobal = req.getSession().getAttribute("strBrandMaster").toString();
		} catch (Exception e) {
			isBrandGlobal = "Custom";
		}
		if (isBrandGlobal.equalsIgnoreCase("All")) {
			tempBrandClientCode = "All";
		}

		clsBrandMasterModel objModel = null;
		List objList = objBrandMasterService.funGetObject(brandCode, tempBrandClientCode);
		if (objList.isEmpty()) {
			objModel = new clsBrandMasterModel();
			objModel.setStrBrandCode("Invalid Code");
		} else {

			Object obj[] = (Object[]) objList.get(0);
			clsBrandMasterModel objModel1 = (clsBrandMasterModel) obj[0];
			clsSubCategoryMasterModel objSubCategoryMasterModel = (clsSubCategoryMasterModel) obj[1];
			clsCategoryMasterModel objCategoryMasterModel = (clsCategoryMasterModel) obj[2];
			clsSizeMasterModel objSizeMaster = (clsSizeMasterModel) obj[3];

			objModel = objModel1;
			objModel.setStrCategoryName(objCategoryMasterModel.getStrCategoryName());
			objModel.setStrSubCategoryName(objSubCategoryMasterModel.getStrSubCategoryName());
			objModel.setStrSizeName(objSizeMaster.getStrSizeName());
			objModel.setIntSizeQty(objSizeMaster.getIntQty());

			// Set Rate of the Brand
			clsRateMasterModel objRateModel = objBrandMasterService.funGetRateObject(brandCode, clientCode);
			objModel.setDblRate(objRateModel != null ? objRateModel.getDblRate() : 0);

			String fromDate = req.getSession().getAttribute("startDate").toString();
			fromDate = fromDate.split("/")[2] + "-" + fromDate.split("/")[1] + "-" + fromDate.split("/")[0];
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			String toDate = format1.format(cal.getTime());
			List brandDataList = new LinkedList();

			String sql_BrandList = "select a.strBrandCode,a.strSizeCode,a.strShortName,b.intQty,a.intPegSize " + "  from tblbrandmaster a, tblsizemaster b " + " where a.strBrandCode='" + brandCode + "' and a.strSizeCode=b.strSizeCode  " + " and  a.strClientCode='" + tempBrandClientCode + "' and b.strClientCode='" + tempSizeClientCode + "' ORDER BY b.intQty DESC";
			Object brandData[] = (Object[]) objGlobalFunctionsService.funGetDataList(sql_BrandList, "sql").get(0);

			String sql_OpData = "select c.intOpBtls,c.intOpPeg,c.intOpML from tblbrandmaster a,tblopeningstock c " + " where a.strBrandCode='" + brandCode + "' and a.strBrandCode=c.strBrandCode  " + " and  a.strClientCode='" + tempBrandClientCode + "' and  c.strClientCode='" + clientCode + "' ";
			List ObjOPDataList = objGlobalFunctionsService.funGetDataList(sql_OpData, "sql");

			Integer intOpBtls = 0;
			Integer intOpPeg = 0;
			Integer intOpML = 0;
			if (ObjOPDataList.size() > 0) {
				Object opData[] = (Object[]) ObjOPDataList.get(0);
				intOpBtls = Integer.parseInt(opData[0].toString());
				intOpPeg = Integer.parseInt(opData[1].toString());
				intOpML = Integer.parseInt(opData[2].toString());
			}

			brandDataList.add(brandData[0]); // BrandCode
			brandDataList.add(brandData[1]); // SizeCode
			brandDataList.add(brandData[2]); // ShortName
			brandDataList.add(brandData[3]); // intSize
			brandDataList.add(brandData[4]); // intPegSize
			brandDataList.add(intOpBtls); // OpeningBottals
			brandDataList.add(intOpPeg); // OpeningPegs
			brandDataList.add(intOpML); // OpeningMLs

			LinkedList stkData = objclsFLR3AController.funStockList(brandDataList, req, fromDate, toDate);
			if (stkData != null) {
				String currentStk = stkData.get(6).toString().trim();
				objModel.setStrAvailableStk(currentStk);
			} else {
				objModel.setStrAvailableStk("" + 0.0);
			}
		}
		return objModel;
	}
}
