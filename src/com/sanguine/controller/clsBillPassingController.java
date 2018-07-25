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

import com.sanguine.bean.clsBillPassingBean;
import com.sanguine.model.clsBillPassDtlModel;
import com.sanguine.model.clsBillPassHdModel;
import com.sanguine.model.clsBillPassingTaxDtlModel;
import com.sanguine.model.clsGRNHdModel;
import com.sanguine.service.clsBillPassingService;
import com.sanguine.service.clsGRNService;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsBillPassingController {
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	clsGlobalFunctions objGlobal = null;
	@Autowired
	clsBillPassingService objBillPassingService;
	@Autowired
	clsGRNService objGRNService;

	@RequestMapping(value = "/frmBillPassing", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		request.getSession().setAttribute("formName", "frmBillPassing");
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		String docCode = "";
		boolean flagOpenFromAuthorization = true;
		try {
			docCode = request.getParameter("authorizationBillPassingCode").toString();
		} catch (NullPointerException e) {
			flagOpenFromAuthorization = false;
		}
		model.put("flagOpenFromAuthorization", flagOpenFromAuthorization);
		if (flagOpenFromAuthorization) {
			model.put("authorizationBillPassingCode", docCode);
		}
		model.put("urlHits", urlHits);

		clsBillPassingBean objBean = new clsBillPassingBean();
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmBillPassing_1", "command", objBean);
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmBillPassing", "command", objBean);
		} else {
			return null;
		}

	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/saveBillPassing", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsBillPassingBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		ModelAndView objModelView = null;
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();

		try {
			if (!result.hasErrors()) {
				List<clsBillPassDtlModel> listclsBillPassDtlModel = objBean.getListBillPassDtl();
				List<clsBillPassingTaxDtlModel> listBillPassTaxDtlModel = objBean.getListBillPassTaxDtl();

				if (null != listclsBillPassDtlModel && listclsBillPassDtlModel.size() > 0) {
					clsBillPassHdModel BillPassHdModel = funPrepareMaster(objBean, userCode, clientCode, propCode, startDate, req);
					objBillPassingService.funAddUpdateBillPassingHD(BillPassHdModel);

					String billPassNo = BillPassHdModel.getStrBillPassNo();
					objBillPassingService.funDeleteBillPassingDtlData(billPassNo, clientCode);
					for (clsBillPassDtlModel ob : listclsBillPassDtlModel) {
						ob.setStrClientCode(clientCode);
						ob.setStrBillPassNo(billPassNo);
						ob.setDtGRNDate(objGlobal.funGetDate("yyyy-MM-dd", ob.getDtGRNDate()));
						objBillPassingService.funAddUpdateBillPassingDtl(ob);
					}

					objBillPassingService.funDeleteBillPassTaxDtl(objBean.getStrBillPassNo(), clientCode);
					if (null != listBillPassTaxDtlModel) {
						for (clsBillPassingTaxDtlModel obTaxDtl : listBillPassTaxDtlModel) {
							obTaxDtl.setStrBillPassNo(billPassNo);
							obTaxDtl.setStrClientCode(clientCode);
							objBillPassingService.funAddUpdateBillPassingTaxDtl(obTaxDtl);
							req.getSession().setAttribute("success", true);
							req.getSession().setAttribute("successMessage", "Bill Passing Code : ".concat(billPassNo));
							req.getSession().setAttribute("rptBillPassingCode", billPassNo);

						}
					} else {
						req.getSession().setAttribute("success", true);
						req.getSession().setAttribute("successMessage", "Bill Passing Code : ".concat(billPassNo));
						req.getSession().setAttribute("rptBillPassingCode", billPassNo);

					}
				}
				objModelView = new ModelAndView("redirect:/frmBillPassing.html?saddr=" + urlHits);
			} else {
				objModelView = new ModelAndView("frmBillPassing.html?saddr=" + urlHits);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return objModelView;
		}
	}

	@SuppressWarnings({ "rawtypes", "finally" })
	@RequestMapping(value = "/loadGRNData", method = RequestMethod.GET)
	public @ResponseBody clsGRNHdModel funAssignFields(HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		clsGRNHdModel objGRNHd = null;
		// String userCode=req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			objGlobal = new clsGlobalFunctions();
			String grnNo = req.getParameter("grnNo").toString();
			String sql = "select strGRNCode,dtGRNDate,strBillNo,dblTotal " + "from clsGRNHdModel where strGRNCode='" + grnNo + "' and strClientCode='" + clientCode + "'";
			List listGRNHd = objGlobalFunctionsService.funGetList(sql, "hql");
			if (listGRNHd.size() == 0) {
				objGRNHd = new clsGRNHdModel();
				objGRNHd.setStrGRNCode("Invalid Code");
			} else {
				Object[] arrObj = (Object[]) listGRNHd.get(0);
				objGRNHd = new clsGRNHdModel();
				objGRNHd.setStrGRNCode(arrObj[0].toString());
				objGRNHd.setDtGRNDate(objGlobal.funGetDate("yyyy/MM/dd", arrObj[1].toString()));
				objGRNHd.setStrBillNo(arrObj[2].toString());
				objGRNHd.setDblTotal(Double.parseDouble(arrObj[3].toString()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return objGRNHd;
		}
	}

	@RequestMapping(value = "/loadBillPassHd", method = RequestMethod.GET)
	public @ResponseBody clsBillPassHdModel funAssignBillPassHd(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		// String
		// userCode=request.getSession().getAttribute("usercode").toString();
		objGlobal = new clsGlobalFunctions();
		String billPassNo = request.getParameter("billPassNo").toString();
		clsBillPassHdModel objBillPassHd = objBillPassingService.funGetObject(billPassNo, clientCode);

		if (null != objBillPassHd) {

			objBillPassHd.setDtBillDate(objGlobal.funGetDate("yyyy/MM/dd", objBillPassHd.getDtBillDate()));
			objBillPassHd.setDtPassDate(objGlobal.funGetDate("yyyy/MM/dd", objBillPassHd.getDtPassDate()));

		} else {
			objBillPassHd = new clsBillPassHdModel();
			objBillPassHd.setStrBillPassNo("Invalid Code");
		}

		return objBillPassHd;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadBillPassDtl", method = RequestMethod.GET)
	public @ResponseBody List funAssignBillPassDtl(HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		// String
		// userCode=request.getSession().getAttribute("usercode").toString();
		String billPassNo = request.getParameter("billPassNo").toString();
		List<clsBillPassDtlModel> listclsBillPassDtlModel = objBillPassingService.funGetDtlList(billPassNo, clientCode);
		List<clsBillPassDtlModel> listBillPassDtl = new ArrayList<clsBillPassDtlModel>();

		for (int cnt = 0; cnt < listclsBillPassDtlModel.size(); cnt++) {
			clsBillPassDtlModel objBillPassDtl = (clsBillPassDtlModel) listclsBillPassDtlModel.get(0);
			objBillPassDtl.setDtGRNDate(objGlobal.funGetDate("yyyy/MM/dd", objBillPassDtl.getDtGRNDate()));
			listBillPassDtl.add(objBillPassDtl);
		}

		return listBillPassDtl;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadBillPassTaxDtl", method = RequestMethod.GET)
	public @ResponseBody List funAssignBillPassTaxDtl(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		// String
		// userCode=request.getSession().getAttribute("usercode").toString();
		String billPassNo = request.getParameter("billPassNo").toString();
		String sql = "select strTaxCode,strTaxDesc,strTaxableAmt,strTaxAmt from clsBillPassingTaxDtlModel " + "where strBillPassNo='" + billPassNo + "' and strClientCode='" + clientCode + "'";
		List list = objGlobalFunctionsService.funGetList(sql, "hql");
		List<clsBillPassingTaxDtlModel> listTaxDtl = new ArrayList<clsBillPassingTaxDtlModel>();
		for (int cnt = 0; cnt < list.size(); cnt++) {
			clsBillPassingTaxDtlModel objTaxDtl = new clsBillPassingTaxDtlModel();
			Object[] arrObj = (Object[]) list.get(cnt);
			objTaxDtl.setStrTaxCode(arrObj[0].toString());
			objTaxDtl.setStrTaxDesc(arrObj[1].toString());
			objTaxDtl.setStrTaxableAmt(Double.parseDouble(arrObj[2].toString()));
			objTaxDtl.setStrTaxAmt(Double.parseDouble(arrObj[3].toString()));
			listTaxDtl.add(objTaxDtl);
		}
		return listTaxDtl;
	}

	@RequestMapping(value = "/loadBillPassHd", method = RequestMethod.POST)
	public ModelAndView funLoadBillPassingData(HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String billNo = request.getParameter("billNo").toString();
		clsBillPassingBean bean = new clsBillPassingBean();
		clsBillPassHdModel objBillPassHdModel = objBillPassingService.funGetObject(billNo, clientCode);
		List<clsBillPassDtlModel> listclsBillPassDtlModel = objBillPassingService.funGetDtlList(billNo, clientCode);
		bean.setStrBillNo(billNo);
		bean.setIntId(objBillPassHdModel.getIntId());
		bean.setDtBillDate(objGlobal.funGetDate("yyyy/MM/dd", objBillPassHdModel.getDtBillDate()));
		bean.setStrAgainst(objBillPassHdModel.getStrAgainst());
		bean.setStrSuppCode(objBillPassHdModel.getStrSuppCode());
		bean.setStrPVno(objBillPassHdModel.getStrPVno());
		bean.setDblBillAmt(objBillPassHdModel.getDblBillAmt());
		bean.setDtPassDate(objGlobal.funGetDate("yyyy/MM/dd", objBillPassHdModel.getDtPassDate()));
		bean.setStrNarration(objBillPassHdModel.getStrNarration());
		bean.setStrCurrency(objBillPassHdModel.getStrCurrency());
		bean.setListBillPassDtl(listclsBillPassDtlModel);

		return new ModelAndView("frmBillPassing", "command", bean);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private clsBillPassHdModel funPrepareMaster(clsBillPassingBean objBean, String userCode, String clientCode, String propCode, String startDate, HttpServletRequest req) {
		clsBillPassHdModel objBillPassHdModel = new clsBillPassHdModel();
		long lastNo = 0;
		objGlobal = new clsGlobalFunctions();
		if (objBean.getStrBillPassNo().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblbillpasshd", "clsBillPassHdModel", "intId", clientCode);

			String year = objGlobal.funGetSplitedDate(startDate)[2];
			String cd = objGlobal.funGetTransactionCode("BP", propCode, year);
			String strBPCode = cd + String.format("%06d", lastNo);
			objBillPassHdModel.setStrBillPassNo(strBPCode);
			objBillPassHdModel.setIntId(lastNo);
			objBillPassHdModel.setStrUserCreated(userCode);
			objBillPassHdModel.setDtDateCreated(objGlobal.funGetCurrentDate("yyyy-MM-dd"));
		} else {
			objBillPassHdModel.setStrBillPassNo(objBean.getStrBillPassNo());
		}
		boolean res = false;
		if (null != req.getSession().getAttribute("hmAuthorization")) {
			HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
			if (hmAuthorization.get("Bill Passing")) {
				res = true;
			}
		}

		objBillPassHdModel.setStrUserModified(userCode);
		objBillPassHdModel.setDtLastModified(objGlobal.funGetCurrentDate("yyyy-MM-dd"));
		objBillPassHdModel.setDtBillDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtBillDate()));
		objBillPassHdModel.setStrSuppCode(objBean.getStrSuppCode());
		objBillPassHdModel.setStrPVno(objBean.getStrPVno());
		objBillPassHdModel.setDblBillAmt(objBean.getDblBillAmt());
		objBillPassHdModel.setDtPassDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtPassDate()));
		objBillPassHdModel.setStrNarration(objBean.getStrNarration());
		objBillPassHdModel.setStrCurrency(objBean.getStrCurrency());
		objBillPassHdModel.setDblConversion(0.0000);
		objBillPassHdModel.setStrClientCode(clientCode);
		if (res) {
			objBillPassHdModel.setStrAuthorise("N");
		} else {
			objBillPassHdModel.setStrAuthorise("Y");
		}
		objBillPassHdModel.setStrAgainst(objGlobal.funIfNull(objBean.getStrAgainst(), "", objBean.getStrAgainst()));
		objBillPassHdModel.setStrAgainstCode(objGlobal.funIfNull(objBean.getStrAgainstCode(), "", objBean.getStrAgainstCode()));
		return objBillPassHdModel;
	}
}
