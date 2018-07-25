package com.sanguine.crm.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.crm.bean.clsPendingCustomerSOBean;
import com.sanguine.crm.bean.clsPendingCustomerSOProductDtlBean;
import com.sanguine.crm.bean.clsSalesOrderBean;
import com.sanguine.crm.model.clsSalesCharModel;
import com.sanguine.crm.model.clsSalesOrderDtl;
import com.sanguine.crm.model.clsSalesOrderHdModel;
import com.sanguine.crm.model.clsSalesOrderHdModel_ID;
import com.sanguine.crm.service.clsSalesOrderService;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsPendingCustomerSOController {

	@Autowired
	private clsSalesOrderService objSalesOrderService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private ServletContext servletContext;
	@Autowired
	private clsGlobalFunctions objGlobal;

	@RequestMapping(value = "/frmPendingCustomerSO", method = RequestMethod.GET)
	public ModelAndView funOpenPendingCustomerSO(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmPendingCustomerSO");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPendingCustomerSO_1", "command", new clsPendingCustomerSOBean());
		} else {
			return new ModelAndView("frmPendingCustomerSO", "command", new clsPendingCustomerSOBean());
		}

	}

	@RequestMapping(value = "/savePendingCustomerSO", method = RequestMethod.POST)
	public ModelAndView funSavePendingCustomerSO(@ModelAttribute("command") @Valid clsPendingCustomerSOBean objSOBean, BindingResult result, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String tempCustCodes = objSOBean.getStrSuppCode();
		String arrCustCodes[] = tempCustCodes.split(",");
		String strSOCodes = "";
		boolean saveDtlFlg = false;
		if (!result.hasErrors()) {

			for (String strCustCode : arrCustCodes) {
				clsSalesOrderHdModel SOModel = funPrepardHDModel(objSOBean, request, strCustCode);
				boolean saveFlg = objSalesOrderService.funAddUpdate(SOModel);

				String strSOCode = SOModel.getStrSOCode();
				strSOCodes = strSOCodes + "," + strSOCode;

				List<clsPendingCustomerSOProductDtlBean> listSODtlModel = objSOBean.getListPendingCustomerSOProductDtlBean();
				if (saveFlg) {
					if (null != listSODtlModel) {
						objSalesOrderService.funDeleteDtl(strSOCode, clientCode);
						int intindex = 1;
						// objSalesOrderService.funDeleteGRNTaxDtl(objSOBean.getStrSOCode(),
						// clientCode);
						for (clsPendingCustomerSOProductDtlBean obSOProdDtl : listSODtlModel) {
							if (strCustCode.equals(obSOProdDtl.getStrCustCode())) {
								if (obSOProdDtl.getDblQty() != 0) {
									clsSalesOrderDtl obSODtl = new clsSalesOrderDtl();
									obSODtl.setStrSOCode(strSOCode);
									obSODtl.setStrProdCode(obSOProdDtl.getStrProdCode());
									obSODtl.setDblQty(obSOProdDtl.getDblQty());
									obSODtl.setDblAcceptQty(obSOProdDtl.getDblQty());
									obSODtl.setDblDiscount(0.00);
									obSODtl.setStrTaxType("");
									obSODtl.setDblTaxableAmt(0.00);
									obSODtl.setDblTax(0.00);
									obSODtl.setDblTaxAmt(0.00);
									obSODtl.setDblUnitPrice(0.00);
									obSODtl.setDblWeight(0.00);
									obSODtl.setStrProdType("");
									obSODtl.setStrRemarks("");
									obSODtl.setIntindex(intindex);
									obSODtl.setStrProdChar("");
									// obSODtl.setDblTotalPrice();
									obSODtl.setStrClientCode(clientCode);
									objSalesOrderService.funAddUpdateDtl(obSODtl);
									saveDtlFlg = true;

								}
							}
						}

					}
				}
			}
			if (saveDtlFlg) {
				request.getSession().setAttribute("success", true);
				request.getSession().setAttribute("successMessage", "SO Code : ".concat(strSOCodes));

			}
			return new ModelAndView("redirect:/frmPendingCustomerSO.html?saddr=" + urlHits);

		} else {

			return new ModelAndView("frmPendingCustomerSO?saddr=" + urlHits, "command", new clsPendingCustomerSOBean());
		}

	}

	private clsSalesOrderHdModel funPrepardHDModel(clsPendingCustomerSOBean objBean, HttpServletRequest req, String strCustCode) {
		long lastNo = 0;
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		String locCode = req.getSession().getAttribute("locationCode").toString();
		clsSalesOrderHdModel SOHDModel;
		objGlobal = new clsGlobalFunctions();

		lastNo = objGlobalFunctionsService.funGetLastNo("tblsalesorderhd", "SOCode", "intId", clientCode);

		String year = objGlobal.funGetSplitedDate(startDate)[2];
		String cd = objGlobal.funGetTransactionCode("SO", propCode, year);
		String strSOCode = cd + String.format("%06d", lastNo);

		SOHDModel = new clsSalesOrderHdModel(new clsSalesOrderHdModel_ID(strSOCode, clientCode));
		SOHDModel.setStrSOCode(strSOCode);
		SOHDModel.setStrUserCreated(userCode);
		SOHDModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		SOHDModel.setIntId(lastNo);

		SOHDModel.setStrUserModified(userCode);
		SOHDModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		SOHDModel.setDteSODate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteSODate()));
		SOHDModel.setStrCustCode(strCustCode);
		SOHDModel.setStrCustPONo("");
		SOHDModel.setDteCPODate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		SOHDModel.setStrLocCode(locCode);
		SOHDModel.setDteFulmtDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteFulmtDate()));
		SOHDModel.setStrAgainst("Direct");
		SOHDModel.setStrCode("");
		SOHDModel.setStrCurrency("RS");
		SOHDModel.setIntwarmonth(0);
		SOHDModel.setStrPayMode("Cash");
		SOHDModel.setDblSubTotal(0);
		SOHDModel.setDblDisRate(0);
		SOHDModel.setDblDisAmt(0);
		SOHDModel.setStrNarration("Pending Customer SO Genrated");
		SOHDModel.setDblExtra(0);
		SOHDModel.setDblTotal(0);

		SOHDModel.setStrBAdd1("");
		SOHDModel.setStrBAdd2("");
		SOHDModel.setStrBCity("");
		SOHDModel.setStrBState("");
		SOHDModel.setStrBCountry("");
		SOHDModel.setStrBPin("");

		SOHDModel.setStrSAdd1("");
		SOHDModel.setStrSAdd2("");
		SOHDModel.setStrSCity("");
		SOHDModel.setStrSState("");
		SOHDModel.setStrSCountry("");
		SOHDModel.setStrSPin("");
		SOHDModel.setStrAuthorise("N");
		SOHDModel.setStrBOMFlag("N");
		SOHDModel.setStrBoomLen("");
		SOHDModel.setStrCranenModel("");
		SOHDModel.setStrImgName("");
		SOHDModel.setStrMaxCap("");
		SOHDModel.setStrNoFall("");
		SOHDModel.setStrReaCode("");
		SOHDModel.setStrStatus("Normal Order");

		SOHDModel.setStrSuppVolt("");
		SOHDModel.setStrSysModel("");
		SOHDModel.setStrWipeRopeDia("");
		;
		SOHDModel.setStrWarraValidity("");
		SOHDModel.setStrWarrPeriod("");
		SOHDModel.setStrCloseSO("N");

		return SOHDModel;
	}

}
