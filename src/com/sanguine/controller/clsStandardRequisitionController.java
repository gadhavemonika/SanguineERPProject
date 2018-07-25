package com.sanguine.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsRequisitionBean;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsProductStandardModel;
import com.sanguine.model.clsRequisitionDtlModel;
import com.sanguine.model.clsTransactionTimeModel;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsRequisitionService;
import com.sanguine.service.clsTransactionTimeService;
import com.sanguine.service.clsUOMService;

@Controller
public class clsStandardRequisitionController {

	@Autowired
	private clsRequisitionService objReqService;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	private clsUOMService objclsUOMService;
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	clsProductMasterService objProductMasterService;
	@Autowired
	private clsTransactionTimeService objTransactionTimeService;

	@RequestMapping(value = "/frmStandardRequisition", method = RequestMethod.GET)
	public ModelAndView funOpenStandardRequisitionForm(Map<String, Object> model, HttpServletRequest req) {
		clsRequisitionBean bean = new clsRequisitionBean();
		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		/**
		 * Checking Authorization
		 */
		String authorizationPOCode = "";
		boolean flagOpenFromAuthorization = true;
		try {
			authorizationPOCode = req.getParameter("authorizationReqCode").toString();
		} catch (NullPointerException e) {
			flagOpenFromAuthorization = false;
		}
		model.put("flagOpenFromAuthorization", flagOpenFromAuthorization);
		if (flagOpenFromAuthorization) {

			model.put("authorizationReqCode", authorizationPOCode);
		}
		/**
		 * Set UOM List
		 */
		List<String> uomList = new ArrayList<String>();
		uomList = objclsUOMService.funGetUOMList(clientCode);
		model.put("uomList", uomList);
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmStandardRequisition_1", "command", bean);
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmStandardRequisition", "command", bean);
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/saveStandardRequisition", method = RequestMethod.POST)
	public ModelAndView funSaveReq(@ModelAttribute("command") clsRequisitionBean reqBean, BindingResult result, HttpServletRequest req) throws ParseException {

		objGlobal = new clsGlobalFunctions();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		String loginLocationCode = reqBean.getStrLocBy();

		List<clsTransactionTimeModel> listclsTransactionTimeModel = new ArrayList<clsTransactionTimeModel>();
		listclsTransactionTimeModel = objTransactionTimeService.funLoadTransactionTimeLocationWise(propCode, clientCode, reqBean.getStrLocBy());
		String fromTime = "", toTime = "";
		if (!result.hasErrors()) {
			if (listclsTransactionTimeModel.size() > 0) {
				clsTransactionTimeModel objTransactionTimeModel = (clsTransactionTimeModel) listclsTransactionTimeModel.get(0);
				SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mma");
				SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm");
				Date fdate, tdate;
				fromTime = objTransactionTimeModel.getTmeFrom();
				toTime = objTransactionTimeModel.getTmeTo();

				fdate = parseFormat.parse(fromTime);
				tdate = parseFormat.parse(toTime);
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				String currentTime = sdf.format(cal.getTime());
				Date currentTme = sdf.parse(currentTime);
				System.out.println("System Time=" + sdf.format(cal.getTime()));
				if (loginLocationCode.equalsIgnoreCase(objTransactionTimeModel.getStrLocCode())) {
					if ((fdate.getTime() > currentTme.getTime()) || (tdate.getTime() < currentTme.getTime()))
					// currentTme)&&tdate.before(currentTme)&&fdate.equals(currentTme)&&tdate.equals(currentTme))
					{
						req.getSession().setAttribute("success", false);
						req.getSession().setAttribute("successMessage", "Your Transaction Time Is Over");
						return new ModelAndView("redirect:/frmStandardRequisition.html?saddr=" + urlHits);
					}else{
						List<clsRequisitionDtlModel> listonReqDtl = reqBean.getListReqDtl();
						if (null != listonReqDtl && listonReqDtl.size() > 0) {
							objReqService.funDeleteProductStandard(reqBean.getStrLocBy(), propCode, clientCode);
							List<clsProductStandardModel> listProdStdModel = funPrepardStandarBean(reqBean, clientCode, propCode);

							objReqService.funAddProductStandard(listProdStdModel);

							req.getSession().setAttribute("success", true);
							req.getSession().setAttribute("successMessage", "Standrad Requsition Code : ".concat(reqBean.getStrReqCode()));

						}
						
					}
				}
			} else {
				List<clsRequisitionDtlModel> listonReqDtl = reqBean.getListReqDtl();
				if (null != listonReqDtl && listonReqDtl.size() > 0) {
					objReqService.funDeleteProductStandard(reqBean.getStrLocBy(), propCode, clientCode);
					List<clsProductStandardModel> listProdStdModel = funPrepardStandarBean(reqBean, clientCode, propCode);

					objReqService.funAddProductStandard(listProdStdModel);

					req.getSession().setAttribute("success", true);
					req.getSession().setAttribute("successMessage", "Standrad Requsition Code : ".concat(reqBean.getStrReqCode()));

				}
			}
			return new ModelAndView("redirect:/frmStandardRequisition.html?saddr=" + urlHits);

		} else {
			return new ModelAndView("redirect:/frmStandardRequisition.html?saddr=" + urlHits);
		}

	}

	private List<clsProductStandardModel> funPrepardStandarBean(clsRequisitionBean objBean, String clientCode, String propCode) {
		List<clsProductStandardModel> listprodStandard = new ArrayList<clsProductStandardModel>();
		for (clsRequisitionDtlModel objDtl : objBean.getListReqDtl()) {
			clsProductStandardModel prodStdModel = new clsProductStandardModel();
			prodStdModel.setStrProdCode(objDtl.getStrProdCode());
			prodStdModel.setStrRemarks(objDtl.getStrRemarks());
			prodStdModel.setStrClientCode(clientCode);
			prodStdModel.setStrStandardType("RequestionStandard");
			prodStdModel.setDblQty(objDtl.getDblQty());
			prodStdModel.setDblUnitPrice(objDtl.getDblUnitPrice());
			prodStdModel.setDblTotalPrice(objDtl.getDblTotalPrice());
			prodStdModel.setStrPropertyCode(propCode);
			prodStdModel.setStrLocCode(objBean.getStrLocBy());
			listprodStandard.add(prodStdModel);
		}
		return listprodStandard;

	}

	@RequestMapping(value = "/loadStandardReqData", method = RequestMethod.GET)
	public @ResponseBody List funLoadStandardReqData(@RequestParam(value = "locCode") String strLocCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		List<clsProductStandardModel> stdList = objReqService.funGetProductStandartList(propCode, strLocCode, clientCode);
		List resList = new ArrayList();
		for (clsProductStandardModel obj : stdList) {
			clsProductMasterModel objProd = objProductMasterService.funGetObject(obj.getStrProdCode(), clientCode);
			List dataList = new ArrayList();

			dataList.add(obj.getStrProdCode());
			dataList.add(objProd.getStrProdName());
			dataList.add(objProd.getStrIssueUOM());
			dataList.add(obj.getDblQty());
			dataList.add(obj.getDblUnitPrice());
			dataList.add(obj.getDblTotalPrice());
			dataList.add(obj.getStrRemarks());

			resList.add(dataList);

		}
		return resList;

	}

}
