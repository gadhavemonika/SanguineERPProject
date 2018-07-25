package com.sanguine.excise.controller;

import java.math.BigInteger;
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

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.excise.bean.clsTransportPassPBean;
import com.sanguine.excise.model.clsBrandMasterModel;
import com.sanguine.excise.model.clsExciseLicenceMasterModel;
import com.sanguine.excise.model.clsExciseSupplierMasterModel;
import com.sanguine.excise.model.clsRateMasterModel;
import com.sanguine.excise.model.clsSizeMasterModel;
import com.sanguine.excise.model.clsTransportPassDtlModel;
import com.sanguine.excise.model.clsTransportPassModel;
import com.sanguine.excise.service.clsBrandMasterService;
import com.sanguine.excise.service.clsTransportPassService;
import com.sanguine.model.clsAuditDtlModel;
import com.sanguine.model.clsAuditHdModel;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsTransportPassController {

	@Autowired
	private clsTransportPassService objTPMasterService;

	@Autowired
	private clsBrandMasterService objclsBrandMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobalFunction;

	private clsGlobalFunctions objGlobal = null;

	// Open Transport
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/frmExciseTransportPass", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propertyCode = request.getSession().getAttribute("propertyCode").toString();

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		// String
		// sql_Licence="select strLicenceCode,strLicenceNo from tbllicencemaster where "
		// +
		// "strClientCode='"+clientCode+"' and strPropertyCode='"+propertyCode+"'";
		// List LicenceList=
		// objGlobalFunctionsService.funGetDataList(sql_Licence, "sql");
		//
		// if(LicenceList.size()>0){
		// Object obj[] = (Object[]) LicenceList.get(0);
		// model.put("LicenceCode",obj[0]);
		// model.put("LicenceNo",obj[1]);
		// }else{
		// request.getSession().setAttribute("success", true);
		// request.getSession().setAttribute("successMessage","Please Add Licence To This Client");
		// }
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseTransportPass_1", "command", new clsTransportPassPBean());
		} else {
			return new ModelAndView("frmExciseTransportPass", "command", new clsTransportPassPBean());

		}
	}

	// Load Master Data On Form
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadExciseTPMasterData", method = RequestMethod.GET)
	public @ResponseBody clsTransportPassPBean funAssignFieldsForForm(@RequestParam("tpCode") String tpCode, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsTransportPassPBean objModel = null;
		List objList = objTPMasterService.funGetObject(tpCode, clientCode);
		clsTransportPassModel objModel1 = null;
		if (objList.size() > 0) {
			Object obj[] = (Object[]) objList.get(0);
			objModel1 = (clsTransportPassModel) obj[0];

			objModel = funPrepareBean(objModel1);
			clsExciseSupplierMasterModel objclsExciseSupplierMasterModel = (clsExciseSupplierMasterModel) obj[1];
			objModel.setStrSupplierName(objclsExciseSupplierMasterModel.getStrSupplierName());
			clsExciseLicenceMasterModel objLicence = (clsExciseLicenceMasterModel) obj[2];
			objModel.setStrLicenceNo(objLicence.getStrLicenceNo());

			List objDtlList = objTPMasterService.funGetTPDtlList(tpCode, clientCode);
			List<clsTransportPassDtlModel> listTPDtl = new ArrayList<clsTransportPassDtlModel>();
			for (int i = 0; i < objDtlList.size(); i++) {
				Object[] ob = (Object[]) objDtlList.get(i);
				clsTransportPassDtlModel tpDtl = (clsTransportPassDtlModel) ob[0];
				clsBrandMasterModel brandMaster = (clsBrandMasterModel) ob[1];
				clsSizeMasterModel sizeMaster = (clsSizeMasterModel) ob[2];
				tpDtl.setStrBrandName(brandMaster.getStrBrandName());
				tpDtl.setStrBrandSize(sizeMaster.getStrSizeName());

				clsRateMasterModel objRateMaster = objclsBrandMasterService.funGetRateObject(brandMaster.getStrBrandCode(), clientCode);
				tpDtl.setDblBrandMRP(objRateMaster != null ? objRateMaster.getDblRate() * (sizeMaster.getIntQty() / brandMaster.getIntPegSize()) : 0);
				listTPDtl.add(tpDtl);

			}
			objModel.setObjTPMasterdtlModel(listTPDtl);
		} else {
			objModel = new clsTransportPassPBean();
			objModel.setStrTPCode("Invalid Code");
		}
		return objModel;
	}

	public clsTransportPassPBean funPrepareBean(clsTransportPassModel objModel) {

		clsTransportPassPBean bean = new clsTransportPassPBean();
		bean.setStrTPCode(objModel.getStrTPCode());
		bean.setStrTPNo(objModel.getStrTPNo());
		bean.setStrLicenceCode(objModel.getStrLicenceCode());
		bean.setStrInvoiceNo(objModel.getStrInvoiceNo());
		bean.setStrTpDate(objModel.getStrTpDate());
		bean.setStrUserCreated(objModel.getStrUserCreated());
		bean.setDteDateCreated(objModel.getDteDateCreated());
		bean.setIntId(objModel.getIntId());
		bean.setDblTotalBill(objModel.getDblTotalBill());
		bean.setDblTotalFees(objModel.getDblTotalFees());
		bean.setDblTotalPurchase(objModel.getDblTotalPurchase());
		bean.setDblTotalTax(objModel.getDblTotalTax());
		bean.setStrSupplierCode(objModel.getStrSupplierCode());
		bean.setStrClientCode(objModel.getStrClientCode());
		bean.setStrUserEdited(objModel.getStrUserEdited());
		bean.setDteDateEdited(objModel.getDteDateEdited());
		return bean;
	}

	// Save or Update Transport
	@RequestMapping(value = "/saveExciseTPMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(Map<String, Object> model, @ModelAttribute("command") @Valid clsTransportPassPBean objBean, BindingResult result, HttpServletRequest req) {
		boolean dtlSuccess = false;

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsTransportPassModel objclsTPModel = funPrepareTPHDModel(objBean, userCode, clientCode);

			List<clsTransportPassDtlModel> listTPdtl = objBean.getObjTPMasterdtlModel();
			if (null != listTPdtl && listTPdtl.size() > 0) {
				boolean sucess = objTPMasterService.funAddUpdateTPMaster(objclsTPModel);
				if (sucess) {
					String strTPCode = objclsTPModel.getStrTPCode();
					objTPMasterService.funDeleteDtl(strTPCode, clientCode);

					for (clsTransportPassDtlModel objTPDtl : listTPdtl) {
						if (objTPDtl.getStrBrandCode() != null) {
							if (objTPDtl.getIntBottals() > 0) {
								long lastNo = objGlobalFunctionsService.funGetCount("tbltpdtl", "intId");
								objTPDtl.setStrTPCode(strTPCode);
								objTPDtl.setStrClientCode(clientCode);
								objTPDtl.setIntId(lastNo);
								dtlSuccess = objTPMasterService.funAddUpdateTPDtl(objTPDtl);
								funAuditTpDtl(objTPDtl, req);
							}
						}
					}

				}

			}
			if (dtlSuccess) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Transport Pass : ".concat(objclsTPModel.getStrTPCode()));
				return new ModelAndView("redirect:/frmExciseTransportPass.html?saddr=" + urlHits);
			} else {
				return new ModelAndView("redirect:/frmExciseTransportPass.html?saddr=" + urlHits);
			}

		} else {
			return new ModelAndView("redirect:/frmExciseTransportPass.html?saddr=" + urlHits);
		}

	}

	// Convert bean to model function
	@SuppressWarnings("rawtypes")
	private clsTransportPassModel funPrepareTPHDModel(clsTransportPassPBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();

		long lastNo = 0;
		clsTransportPassModel objModel = new clsTransportPassModel();

		if (objBean != null) {
			if (objBean.getObjTPMasterdtlModel() != null && objBean.getObjTPMasterdtlModel().size() > 0) {
				if (!(objBean.getStrTPCode().isEmpty())) {
					List objList = objTPMasterService.funGetObject(objBean.getStrTPCode(), clientCode);
					Object obj[] = (Object[]) objList.get(0);
					clsTransportPassModel objModel1 = (clsTransportPassModel) obj[0];
					if (objModel1 != null) {
						objModel.setStrTPCode(objModel1.getStrTPCode());
						objModel.setStrUserCreated(objModel1.getStrUserCreated());
						objModel.setDteDateCreated(objModel1.getDteDateCreated());
						objModel.setIntId(objModel1.getIntId());
					}
				} else {

					lastNo = objGlobalFunctionsService.funGetCount("tbltphd", "intId");
					String TPCode = "T" + String.format("%06d", lastNo);
					objModel.setStrTPCode(TPCode);
					objModel.setStrUserCreated(userCode);
					objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					objModel.setIntId(lastNo);
				}

				objModel.setStrLicenceCode(objBean.getStrLicenceCode());
				objModel.setStrTPNo(objBean.getStrTPNo());
				objModel.setStrInvoiceNo(objBean.getStrInvoiceNo());
				objModel.setStrTpDate(objBean.getStrTpDate());
				objModel.setDblTotalBill(objBean.getDblTotalBill());
				objModel.setDblTotalFees(objBean.getDblTotalFees());
				objModel.setDblTotalPurchase(objBean.getDblTotalPurchase());
				objModel.setDblTotalTax(objBean.getDblTotalTax());
				objModel.setStrSupplierCode(objBean.getStrSupplierCode());
				objModel.setStrClientCode(clientCode);
				objModel.setStrUserEdited(userCode);
				objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

			}
		}
		funAuditTpHd(objModel);
		return objModel;

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadTpNoData", method = RequestMethod.GET)
	public @ResponseBody clsTransportPassModel funCheckTpNo(@RequestParam("tpNo") String tpNo, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsTransportPassModel objModel = null;
		List objList = objTPMasterService.funGetTpNOObject(tpNo, clientCode);

		if (objList.size() > 0) {

			objModel = (clsTransportPassModel) objList.get(0);

		} else {
			objModel = new clsTransportPassModel();
			objModel.setStrClientCode(clientCode);
			objModel.setStrTPNo("");
		}
		return objModel;
	}

	@SuppressWarnings("unchecked")
	public void funAuditTpHd(clsTransportPassModel objModel) {

		String count = objModel.getStrTPCode().toString();
		int cnt = count.length();
		String countSql = "select count(*) from dbwebmms.tblaudithd " + "where left(strTransCode," + cnt + ")='" + objModel.getStrTPCode() + "' and strClientCode='" + objModel.getStrClientCode() + "'";

		List<BigInteger> saveUpdateList = objGlobalFunctionsService.funGetDataList(countSql, "sql");
		cnt = saveUpdateList.get(0).intValue();
		if (saveUpdateList != null) {
			clsAuditHdModel objAuditHd = new clsAuditHdModel();
			if (cnt == 0) {
				objAuditHd.setStrTransCode(objModel.getStrTPCode());
			} else {
				objAuditHd.setStrTransCode(objModel.getStrTPCode() + "-" + cnt);
			}
			objAuditHd.setStrSuppCode(objModel.getStrSupplierCode());
			objAuditHd.setStrBillNo(objModel.getStrInvoiceNo());
			objAuditHd.setDtBillDate(objModel.getStrTpDate());
			objAuditHd.setIntId(objModel.getIntId());
			objAuditHd.setDblTaxAmt(objModel.getDblTotalTax());
			objAuditHd.setDblTotalAmt(objModel.getDblTotalBill());
			objAuditHd.setStrUserCreated(objModel.getStrUserCreated());
			objAuditHd.setStrUserModified(objModel.getStrUserEdited());
			objAuditHd.setDtDateCreated(objModel.getDteDateCreated());
			objAuditHd.setDtLastModified(objModel.getDteDateEdited());
			objAuditHd.setStrClientCode(objModel.getStrClientCode());
			objAuditHd.setStrAuthorise("No");
			objAuditHd.setStrTransMode("Edit");
			objAuditHd.setStrTransType("frmExciseTransportPass");
			objGlobalFunctionsService.funSaveAuditHd(objAuditHd);
		}

	}

	@SuppressWarnings("unchecked")
	public void funAuditTpDtl(clsTransportPassDtlModel objdtlModel, HttpServletRequest req) {

		clsAuditDtlModel objAduitDtl = new clsAuditDtlModel();
		if (objdtlModel.getStrTPCode() != null) {
			int cntTransCode = objdtlModel.getStrTPCode().toString().length();

			String countSql = "select count(*) from dbwebmms.tblaudithd " + "where left(strTransCode," + cntTransCode + ")='" + objdtlModel.getStrTPCode() + "' and strClientCode='" + objdtlModel.getStrClientCode() + "'";

			List<BigInteger> countList = objGlobalFunctionsService.funGetDataList(countSql, "sql");
			int cnt = countList.get(0).intValue();
			cnt = cnt - 1;
			if (cnt == 0) {
				objAduitDtl.setStrTransCode(objdtlModel.getStrTPCode());
			} else {
				objAduitDtl.setStrTransCode(objdtlModel.getStrTPCode() + "-" + cnt);
			}
			// objAduitDtl.setIntId(objdtlModel.getIntId());
			objAduitDtl.setStrProdCode(objdtlModel.getStrBrandCode());
			String btlQty = objdtlModel.getIntBottals().toString();
			String btlPeg = btlQty + "." + 0;
			String totlbtlPeg = objGlobalFunction.funConversionMLPeg(objdtlModel.getStrBrandCode(), btlPeg, "ml", objdtlModel.getStrClientCode(), req);
			objAduitDtl.setDblQty(Double.parseDouble(totlbtlPeg));
			objAduitDtl.setStrClientCode(objdtlModel.getStrClientCode());
			objAduitDtl.setDblTax(objdtlModel.getDblBrandTax());
			objGlobalFunctionsService.funSaveAuditDtl(objAduitDtl);
		}
	}
}
