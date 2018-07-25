package com.sanguine.controller;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.sanguine.bean.clsRateContractBean;
import com.sanguine.model.clsRateContractDtlModel;
import com.sanguine.model.clsRateContractHdModel;
import com.sanguine.model.clsRateContractHdModel_ID;
import com.sanguine.model.clsSupplierMasterModel;
import com.sanguine.model.clsTCMasterModel;
import com.sanguine.model.clsTCTransModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsRateContractService;
import com.sanguine.service.clsTCMasterService;
import com.sanguine.service.clsTCTransService;

@Controller
public class clsRateContractorController {
	@Autowired
	private clsRateContractService objRateContService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsTCMasterService objTCMaster;
	@Autowired
	private clsTCTransService objTCTransService;
	private clsGlobalFunctions objGlobal = null;

	@Autowired
	clsCurrencyMasterService objCurrencyMasterService;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/frmRateContract", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") clsRateContractBean bean, Map<String, Object> model, HttpServletRequest request) {

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		request.getSession().setAttribute("formName", "frmWebStockHelpRateContract");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		List<String> listDateChange = new ArrayList<>();
		listDateChange.add("Yes");
		listDateChange.add("No");
		model.put("dateChgList", listDateChange);

		Map<String, String> hmCurrency = objCurrencyMasterService.funCurrencyListToDisplay(clientCode);
		if (hmCurrency.isEmpty()) {
			hmCurrency.put("", "");
		}
		model.put("currencyList", hmCurrency);

		List<String> listAllProd = new ArrayList<>();
		listAllProd.add("Yes");
		listAllProd.add("No");
		model.put("allProdList", listAllProd);

		String sql_Setup = "select a.strTCCode,b.strTCName,a.strTCDesc " + "from clsTCTransModel a,clsTCMasterModel b " + "where a.strTCCode=b.strTCCode and a.strTransCode=:transCode " + "and a.strClientCode=:clientCode and a.strTransType=:transType";
		List listTC_Setup = objTCTransService.funGetTCTransList(sql_Setup, propCode, clientCode, "Property Setup");

		if (listTC_Setup.size() == 0) {
			List<clsTCMasterModel> listTCMaster = objTCMaster.funGetTCMasterList(clientCode);
			bean.setListTCMaster(listTCMaster);
		} else {
			List<clsTCMasterModel> listTCMasterForRC = new ArrayList<clsTCMasterModel>();
			for (int cnt = 0; cnt < listTC_Setup.size(); cnt++) {
				clsTCMasterModel objTCMasterModel = new clsTCMasterModel();
				Object[] arrObject = (Object[]) listTC_Setup.get(cnt);
				objTCMasterModel.setStrTCCode(arrObject[0].toString());
				objTCMasterModel.setStrTCName(arrObject[1].toString());
				objTCMasterModel.setStrTCDesc(arrObject[2].toString());
				listTCMasterForRC.add(objTCMasterModel);
			}
			bean.setListTCMaster(listTCMasterForRC);
		}

		String docCode = "";
		boolean flagOpenFromAuthorization = true;
		try {
			docCode = request.getParameter("authorizationRateContractCode").toString();
		} catch (NullPointerException e) {
			flagOpenFromAuthorization = false;
		}
		model.put("flagOpenFromAuthorization", flagOpenFromAuthorization);
		if (flagOpenFromAuthorization) {
			model.put("authorizationRateContractCode", docCode);
		}
		model.put("TCMasterList", bean);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmRateContract_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmRateContract");
		} else {
			return null;
		}

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/frmRateContract1", method = RequestMethod.GET)
	public @ResponseBody clsRateContractBean funOpenFormWithBomCode(Map<String, Object> model, HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		List<String> listDateChange = new ArrayList<>();
		listDateChange.add("Yes");
		listDateChange.add("No");
		model.put("dateChgList", listDateChange);

		List<String> listCurrency = new ArrayList<>();
		listCurrency.add("Rs");
		listCurrency.add("$");
		model.put("currencyList", listCurrency);

		List<String> listAllProd = new ArrayList<>();
		listAllProd.add("Yes");
		listAllProd.add("No");
		model.put("allProdList", listAllProd);

		clsRateContractBean bean = new clsRateContractBean();
		String rateContNo = request.getParameter("rateContNo").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		// String
		// userCode=request.getSession().getAttribute("usercode").toString();

		List listRateContHd = objRateContService.funGetObject(rateContNo, clientCode);
		if (listRateContHd.isEmpty()) {
			bean = new clsRateContractBean();
			bean.setStrRateContNo("Invalid Code");
			return bean;
		} else {
			bean = funPrepareBean(listRateContHd);
			List<clsRateContractDtlModel> listRateContractDtl = objRateContService.funGetDtlList(rateContNo, clientCode);
			bean.setListRateContDtl(listRateContractDtl);

			String sql_PO = "select a.strTCCode,b.strTCName,a.strTCDesc " + "from clsTCTransModel a,clsTCMasterModel b " + "where a.strTCCode=b.strTCCode and a.strTransCode=:transCode " + "and a.strClientCode=:clientCode and a.strTransType=:transType";
			List listTC_RC = objTCTransService.funGetTCTransList(sql_PO, bean.getStrRateContNo(), clientCode, "Rate Contract");
			if (listTC_RC.isEmpty()) {
				List<clsTCMasterModel> listTCMaster = objTCMaster.funGetTCMasterList(clientCode);
				bean.setListTCMaster(listTCMaster);
			} else {
				List<clsTCMasterModel> listTCMasterForRC = new ArrayList<clsTCMasterModel>();
				for (int cnt = 0; cnt < listTC_RC.size(); cnt++) {
					clsTCMasterModel objTCMasterModel = new clsTCMasterModel();
					Object[] arrObject = (Object[]) listTC_RC.get(cnt);
					objTCMasterModel.setStrTCCode(arrObject[0].toString());
					objTCMasterModel.setStrTCName(objGlobal.funIfNull(arrObject[1].toString(), "", arrObject[1].toString()));
					objTCMasterModel.setStrTCDesc(objGlobal.funIfNull(arrObject[2].toString(), "", arrObject[2].toString()));
					listTCMasterForRC.add(objTCMasterModel);
				}
				bean.setListTCMaster(listTCMasterForRC);
			}

			return bean;
		}

	}

	@RequestMapping(value = "/saveRateCotract", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsRateContractBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		objGlobal = new clsGlobalFunctions();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		if (!result.hasErrors()) {
			List<clsRateContractDtlModel> listRateContDtl = objBean.getListRateContDtl();

			if (null != listRateContDtl && listRateContDtl.size() > 0) {
				clsRateContractHdModel objHdModel = funPrepareModel(objBean, userCode, clientCode, req);
				objRateContService.funAddUpdate(objHdModel);
				String rateContNo = objHdModel.getStrRateContNo();
				objRateContService.funDeleteDtl(rateContNo, clientCode);
				boolean flagDtlDataInserted = false;
				for (clsRateContractDtlModel ob : listRateContDtl) {
					if (null != ob.getStrProductCode()) {
						ob.setStrRateContNo(rateContNo);
						ob.setDtExpectedDate((objGlobal.funGetCurrentDateTime("yyyy-MM-dd")));
						ob.setStrClientCode(clientCode);
						// listDtl.add(ob);
						// objHdModel.getDtl().add(ob);
						objRateContService.funAddUpdateDtl(ob);
						flagDtlDataInserted = true;
					}
				}
				if (flagDtlDataInserted) {
					req.getSession().setAttribute("success", true);
					req.getSession().setAttribute("successMessage", "Rate ContNo : ".concat(objHdModel.getStrRateContNo()));
				}
				if (null != objBean.getListTCMaster()) {
					String sql_Delete = "delete from clsTCTransModel where strTransCode='" + rateContNo + "' " + "and strTransType='Rate Contract' and strClientCode='" + clientCode + "'";
					objTCTransService.funDeleteTCTransList(sql_Delete);

					List<clsTCTransModel> listTCTransModel = objGlobal.funPrepareTCTransModel(objBean.getListTCMaster(), rateContNo, userCode, clientCode, "Rate Contract");

					for (int cnt = 0; cnt < listTCTransModel.size(); cnt++) {
						clsTCTransModel objTCTrans = listTCTransModel.get(cnt);
						objTCTransService.funAddTCTrans(objTCTrans);
					}
				}
				objRateContService.funAddUpdate(objHdModel);
			}

			return new ModelAndView("redirect:/frmRateContract.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmRateContract?saddr=" + urlHits);
		}
	}

	@SuppressWarnings("rawtypes")
	private clsRateContractHdModel funPrepareModel(clsRateContractBean objBean, String userCode, String clientCode, HttpServletRequest request) {
		long lastNo = 0;
		clsRateContractHdModel objHdModel;
		if (objBean.getStrRateContNo().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblrateconthd", "RateContract", "intId", clientCode);
			String code = "RC" + String.format("%07d", lastNo);
			objHdModel = new clsRateContractHdModel(new clsRateContractHdModel_ID(code, clientCode));
			objHdModel.setIntId(lastNo);
			objHdModel.setStrUserCreated(userCode);
			objHdModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			List listRateContHd = objRateContService.funGetObject(objBean.getStrRateContNo(), clientCode);
			if (listRateContHd.size() == 0) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblrateconthd", "RateContract", "intId", clientCode);
				String code = "RC" + String.format("%07d", lastNo);
				objHdModel = new clsRateContractHdModel(new clsRateContractHdModel_ID(code, clientCode));
				objHdModel.setIntId(lastNo);
				objHdModel.setStrUserCreated(userCode);
				objHdModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				objHdModel = new clsRateContractHdModel(new clsRateContractHdModel_ID(objBean.getStrRateContNo(), clientCode));
			}
		}

		objHdModel.setDtRateContDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtRateContDate()));
		objHdModel.setStrSuppCode(objBean.getStrSuppCode());
		objHdModel.setDtFromDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtFromDate()));
		objHdModel.setDtToDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtToDate()));
		objHdModel.setStrDateChg(objBean.getStrDateChg());
		objHdModel.setStrAuthorise(objBean.getStrAuthorise());
		objHdModel.setStrCurrency(objBean.getStrCurrency());
		objHdModel.setStrUser(userCode);
		objHdModel.setDblConversion(1.00);
		objHdModel.setStrProdFlag(objBean.getStrProdFlag());
		objHdModel.setStrPropertyCode("01");
		boolean res = false;
		if (null != request.getSession().getAttribute("hmAuthorization")) {
			HashMap<String, Boolean> hmAuthorization = (HashMap) request.getSession().getAttribute("hmAuthorization");
			if (hmAuthorization.get("Rate Contract")) {
				res = true;
			}
		}
		if (res) {
			objHdModel.setStrAuthorise("No");
		} else {
			objHdModel.setStrAuthorise("Yes");
		}

		objHdModel.setStrUserModified(userCode);
		objHdModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		return objHdModel;
	}

	@SuppressWarnings("rawtypes")
	private clsRateContractBean funPrepareBean(List listRateContHd) {
		objGlobal = new clsGlobalFunctions();
		clsRateContractBean objBean = new clsRateContractBean();
		if (listRateContHd.size() > 0) {
			Object[] ob = (Object[]) listRateContHd.get(0);
			clsRateContractHdModel rateContHd = (clsRateContractHdModel) ob[0];
			clsSupplierMasterModel supplierMaster = (clsSupplierMasterModel) ob[1];
			objBean.setStrRateContNo(rateContHd.getStrRateContNo());
			objBean.setDtFromDate(objGlobal.funGetDate("yyyy/MM/dd", rateContHd.getDtFromDate()));
			objBean.setDtToDate(objGlobal.funGetDate("yyyy/MM/dd", rateContHd.getDtToDate()));
			objBean.setDtRateContDate(objGlobal.funGetDate("yyyy/MM/dd", rateContHd.getDtRateContDate()));
			objBean.setStrSuppCode(rateContHd.getStrSuppCode());
			objBean.setStrDateChg(rateContHd.getStrDateChg());
			objBean.setStrProdFlag(rateContHd.getStrProdFlag());
			objBean.setStrCurrency(rateContHd.getStrCurrency());
			objBean.setStrAuthorise(rateContHd.getStrAuthorise());
			objBean.setStrSuppName(supplierMaster.getStrPName());
		}
		return objBean;
	}
}
