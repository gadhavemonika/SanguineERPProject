package com.sanguine.controller;

import java.util.ArrayList;
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

import com.sanguine.bean.clsStockAdjFlashBean;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsReasonMasterService;
import com.sanguine.service.clsStkAdjustmentService;
import com.sanguine.service.clsStockFlashService;

@Controller
public class clsStockAdjustmentFlashController {

	@Autowired
	private clsGlobalFunctionsService objGlobalService;
	@Autowired
	private clsStockFlashService objStkFlashService;
	@Autowired
	private clsReasonMasterService objReasonMasterService;
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsStkAdjustmentService objStkAdjustmentService;

	/**
	 * Open Stock Adjustment Flash Form
	 * 
	 * @param objPropBean
	 * @param result
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/frmStockAdjustmentFlash", method = RequestMethod.GET)
	private ModelAndView funLoadPropertySelection(@ModelAttribute("command") clsStockAdjFlashBean objPropBean, BindingResult result, HttpServletRequest req) {
		return funGetModelAndView(req);
	}

	/**
	 * Return Stock Adjustment Model and View
	 * 
	 * @param req
	 * @return
	 */
	private ModelAndView funGetModelAndView(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		// String usercode=req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		ModelAndView objModelView = new ModelAndView("frmStockAdjustmentFlash");

		Map<String, String> mapProperty = objGlobalService.funGetPropertyList(clientCode);
		if (mapProperty.isEmpty()) {
			mapProperty.put("", "");
		}
		objModelView.addObject("listProperty", mapProperty);

		Map<String, String> mapLocation = objGlobalService.funGetLocationList(propertyCode, clientCode);
		if (!mapLocation.isEmpty()) {
			mapLocation.put("ALL", "ALL");
		} else {
			mapLocation.put("", "");
		}
		objModelView.addObject("listLocation", mapLocation);

		Map<String, String> mapReason = objReasonMasterService.funGetResonList(clientCode);
		if (mapReason.isEmpty()) {
			mapReason.put("", "");
		}
		objModelView.addObject("listReason", mapReason);

		return objModelView;
	}

	/**
	 * Load Stock Adjustment Summary Data
	 * 
	 * @param param1
	 * @param fDate
	 * @param tDate
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/flshStockAdjSummaryReport", method = RequestMethod.GET)
	private @ResponseBody List funShowStockSummaryFlash(@RequestParam(value = "param1") String param1, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String[] spParam1 = param1.split(",");
		// String reportType=spParam1[0];
		String locCode = spParam1[1];
		// String propCode=spParam1[2];
		String strReasonCode = spParam1[3];
		String strAdjType = spParam1[4];
		String fromDate = objGlobal.funGetDate("yyyy-MM-dd", fDate);
		String toDate = objGlobal.funGetDate("yyyy-MM-dd", tDate);

		String sql = "select a.strSACode,Date(a.dtSADate),c.strReasonName,d.strLocName,a.dblTotalAmt,a.strNarration,a.strUserCreated " + " from clsStkAdjustmentHdModel a,clsStkAdjustmentDtlModel b ,clsReasonMaster c,clsLocationMasterModel d " + " where a.strSACode=b.strSACode and a.strReasonCode=c.strReasonCode and a.strLocCode=d.strLocCode " + " and c.strReasonCode='" + strReasonCode + "' "
				+ "	and a.dtSADate between '" + fromDate + "' and '" + toDate + "' and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + clientCode + "' and  c.strClientCode='" + clientCode + "' ";

		if (!locCode.equalsIgnoreCase("ALL")) {
			sql = sql + " and a.strLocCode='" + locCode + "' ";
		}

		if (strAdjType.equalsIgnoreCase("Physical Stock Posting")) {
			String adjtype = "'%Physical Stock Posting%'";
			sql = sql + " and a.strNarration like  " + adjtype;
		} else if (strAdjType.equalsIgnoreCase("POS Sale Data")) {
			String adjtype = "'%POS Sale Data%'";
			sql = sql + " and a.strNarration like " + adjtype;
		}

		sql = sql + " group by a.strSACode,a.strReasonCode";

		System.out.println(sql);
		List list = objStkAdjustmentService.funGetStockAdjFlashData(sql, clientCode, userCode);
		List listStockAdjFlashModel = new ArrayList();

		for (int cnt = 0; cnt < list.size(); cnt++) {
			Object[] arrObj = (Object[]) list.get(cnt);
			listStockAdjFlashModel.add(arrObj);
		}

		return listStockAdjFlashModel;
	}

	/**
	 * Load Stock Adjustment Detail Data
	 * 
	 * @param param1
	 * @param fDate
	 * @param tDate
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/flshStockAdjDetailReport", method = RequestMethod.GET)
	private @ResponseBody List funShowStockDetailsFlash(@RequestParam(value = "param1") String param1, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String[] spParam1 = param1.split(",");
		// String reportType=spParam1[0];
		String locCode = spParam1[1];
		// String propCode=spParam1[2];
		String strReasonCode = spParam1[3];
		String strAdjType = spParam1[4];
		String fromDate = objGlobal.funGetDate("yyyy-MM-dd", fDate);
		String toDate = objGlobal.funGetDate("yyyy-MM-dd", tDate);

		String sql = "select a.strSACode,Date(a.dtSADate),c.strReasonName,e.strLocName,d.strProdName,d.strIssueUOM,b.dblQty,b.dblRate,b.dblPrice,b.strRemark,a.strUserCreated " + " from clsStkAdjustmentHdModel a,clsStkAdjustmentDtlModel b ,clsReasonMaster c ,clsProductMasterModel d ,clsLocationMasterModel e"
				+ " where a.strSACode=b.strSACode and a.strReasonCode=c.strReasonCode  and c.strReasonCode='" + strReasonCode + "' " + " and a.strLocCode=e.strLocCode  and b.strProdCode=d.strProdCode " + " and a.dtSADate between '" + fromDate + "' and '" + toDate + "' and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + clientCode + "' and  c.strClientCode='" + clientCode
				+ "' and d.strClientCode='" + clientCode + "'";
		if (!locCode.equalsIgnoreCase("ALL")) {
			sql = sql + "and a.strLocCode='" + locCode + "' ";
		}

		if (strAdjType.equalsIgnoreCase("Physical Stock Posting")) {
			sql = sql + "and a.strNarration like '%Physical Stock Posting%' ";
		} else if (strAdjType.equalsIgnoreCase("POS Sale Data")) {
			sql = sql + "and a.strNarration like '%POS Sale Data%' ";
		}

		// System.out.println(sql);
		List list = objStkAdjustmentService.funGetStockAdjFlashData(sql, clientCode, userCode);
		List listStockAdjFlashModel = new ArrayList();

		for (int cnt = 0; cnt < list.size(); cnt++) {
			Object[] arrObj = (Object[]) list.get(cnt);
			listStockAdjFlashModel.add(arrObj);
		}

		return listStockAdjFlashModel;
	}

}
