package com.sanguine.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsDeliveryScheduleBean;
import com.sanguine.bean.clsGroupMasterBean;
import com.sanguine.bean.clsRequisitionBean;
import com.sanguine.model.clsDeliveryScheduleModuledtl;
import com.sanguine.model.clsDeliveryScheduleModulehd;
import com.sanguine.model.clsDeliveryScheduleModulehd_ID;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsGroupMasterModel_ID;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsProductStandardModel;
import com.sanguine.model.clsPurchaseOrderDtlModel;
import com.sanguine.model.clsPurchaseOrderHdModel;
import com.sanguine.model.clsRequisitionDtlModel;
import com.sanguine.service.clsDeliveryScheduleService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsPurchaseOrderService;

@Controller
public class clsDeliverScheduleController {

	@Autowired
	private clsDeliveryScheduleService objDeliveryScheduleService;

	@Autowired
	private clsPurchaseOrderService objPurchaseOrderService;

	@Autowired
	private clsProductMasterService objProductMasterService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	// @Autowired
	// private clsLocationMasterService objLocationMasterService;
	//
	// @Autowired
	// private clsDeliveryScheduleModulehd objDeliveryScheduleModulehd;

	@RequestMapping(value = "/frmDeliverySchedule", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmDeliverySchedule_1", "command", new clsDeliveryScheduleBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmDeliverySchedule", "command", new clsDeliveryScheduleBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/loadPOForDSRest", method = RequestMethod.GET)
	public @ResponseBody List<clsDeliveryScheduleModuledtl> funAssignFields(@RequestParam("poCode") String poCode, HttpServletRequest req, HttpServletResponse response) {
		List<clsDeliveryScheduleModuledtl> listDSPODtl = new ArrayList<clsDeliveryScheduleModuledtl>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsDeliveryScheduleModulehd objModel = null;
		List<clsPurchaseOrderDtlModel> listPODtl = objPurchaseOrderService.funGetPODtlModelList(poCode, clientCode);

		String sqlRestPOData = " SELECT c.strProdCode,c.strProdName,c.strUOM, a.dblOrdQty - IFNULL(b.DSQty,0), " + " a.dblPrice,(a.dblOrdQty - IFNULL(b.DSQty,0))*a.dblPrice,a.strRemarks " + " FROM tblpurchaseorderdtl a " + " LEFT OUTER JOIN ( " + " SELECT a.strPOCode AS POCode, b.strProdCode, sum(b.dblQty) AS DSQty " + " FROM tbldeliveryschedulehd a , tbldeliveryscheduledtl b "
				+ " where a.strDSCode = b.strDSCode AND b.strClientCode='" + clientCode + "' " + " and (a.strAgainst = 'PO') AND a.strClientCode='" + clientCode + "' " + " and a.strPOCode='" + poCode + "' GROUP BY  b.strProdCode,a.strPOCode " + " ) b ON a.strPOCode = b.POCode " + " AND a.strProdCode = b.strProdCode "
				+ " LEFT OUTER JOIN tblproductmaster c ON a.strProdCode=c.strProdCode AND c.strClientCode='" + clientCode + "' " + " WHERE a.dblOrdQty > IFNULL(b.DSQty,0) AND a.strPOCode='" + poCode + "' AND a.strClientCode='" + clientCode + "' ";

		List listds = objGlobalFunctionsService.funGetList(sqlRestPOData, "sql");
		if (listds.size() > 0) {

			for (int i = 0; i < listds.size(); i++) {
				Object[] obProdDtl = (Object[]) listds.get(i);

				clsDeliveryScheduleModuledtl objDS = new clsDeliveryScheduleModuledtl();
				objDS.setStrProdCode(obProdDtl[0].toString());
				objDS.setStrProdName(obProdDtl[1].toString());
				objDS.setStrUOM(obProdDtl[2].toString());
				objDS.setDblQty(Double.parseDouble(obProdDtl[3].toString()));
				objDS.setDblUnitPrice(Double.parseDouble(obProdDtl[4].toString()));
				objDS.setDblTotalPrice(Double.parseDouble(obProdDtl[5].toString()));
				objDS.setStrRemarks(obProdDtl[6].toString());

				listDSPODtl.add(objDS);
			}

		}
		return listDSPODtl;

	}

	@RequestMapping(value = "/saveDeliverySchedule", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsDeliveryScheduleBean dsBean, BindingResult result, HttpServletRequest req, HttpServletResponse resp) {
		String strMarathiName = "";
		String urlHits = "1";
		try {
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");
			urlHits = req.getParameter("saddr").toString();

		} catch (Exception e) {
			e.printStackTrace();
			urlHits = "1";
		}

		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsDeliveryScheduleModulehd obDSHd = funPreparedDSModel(dsBean, userCode, clientCode, req, resp);
			objDeliveryScheduleService.funAddUpdate(obDSHd);

			String sqlRestPOData = " SELECT c.strProdCode,c.strProdName,c.strUOM, a.dblOrdQty - IFNULL(b.DSQty,0), " + " a.dblPrice,(a.dblOrdQty - IFNULL(b.DSQty,0))*a.dblPrice,a.strRemarks " + " FROM tblpurchaseorderdtl a " + " LEFT OUTER JOIN ( " + " SELECT a.strPOCode AS POCode, b.strProdCode, sum(b.dblQty) AS DSQty " + " FROM tbldeliveryschedulehd a , tbldeliveryscheduledtl b "
					+ " where a.strDSCode = b.strDSCode AND b.strClientCode='" + clientCode + "' " + " and (a.strAgainst = 'PO') AND a.strClientCode='" + clientCode + "' " + " and a.strPOCode='" + obDSHd.getStrPOCode() + "' GROUP BY  b.strProdCode,a.strPOCode " + " ) b ON a.strPOCode = b.POCode " + " AND a.strProdCode = b.strProdCode "
					+ " LEFT OUTER JOIN tblproductmaster c ON a.strProdCode=c.strProdCode AND c.strClientCode='" + clientCode + "' " + " WHERE a.dblOrdQty > IFNULL(b.DSQty,0) AND a.strPOCode='" + obDSHd.getStrPOCode() + "' AND a.strClientCode='" + clientCode + "' ";

			List listds = objGlobalFunctionsService.funGetList(sqlRestPOData, "sql");
			if (listds.isEmpty()) {
				String sqlPOClose = "update tblpurchaseorderhd set strclosePO='Yes' Where strPOCode='" + obDSHd.getStrPOCode() + "' and strClientCode='" + clientCode + "' ";
				objGlobalFunctionsService.funExcuteQuery(sqlPOClose);

			}

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "DS Code : ".concat(obDSHd.getStrDSCode()));
			req.getSession().setAttribute("rptDSCode", obDSHd.getStrDSCode());
			return new ModelAndView("redirect:/frmDeliverySchedule.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmDeliverySchedule.html?saddr=" + urlHits);
		}
	}

	private clsDeliveryScheduleModulehd funPreparedDSModel(clsDeliveryScheduleBean dsBean, String userCode, String clientCode, HttpServletRequest req, HttpServletResponse resp) {

		long lastNo = 0;
		String strDocNo = "";
		clsDeliveryScheduleModulehd objDSModel = null;
		if (dsBean.getStrDSCode().trim().length() == 0) {
			objDSModel = new clsDeliveryScheduleModulehd();
			strDocNo = objGlobal.funGenerateDocumentCode("frmDS", dsBean.getDteDSDate(), req);
			objDSModel.setStrDSCode(strDocNo);
			objDSModel.setStrUserCreated(userCode);
			objDSModel.setStrClientCode(clientCode);
			objDSModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		} else {

			objDSModel = new clsDeliveryScheduleModulehd(new clsDeliveryScheduleModulehd_ID(dsBean.getStrDSCode(), clientCode));
			objDSModel.setStrUserCreated(userCode);
			objDSModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		}

		objDSModel.setStrAgainst(dsBean.getStrAgainst());
		objDSModel.setStrLocCode(dsBean.getStrLocCode());
		objDSModel.setStrNarration(dsBean.getStrNarration());
		objDSModel.setStrPOCode(dsBean.getStrPOCode());
		objDSModel.setDteDSDate(objGlobal.funGetDate("yyyy-MM-dd", dsBean.getDteDSDate()));
		objDSModel.setDteScheduleDate(objGlobal.funGetDate("yyyy-MM-dd", dsBean.getDteDSDate()));
		objDSModel.setStrUserEdited(userCode);
		objDSModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objDSModel.setStrCloseDS(objGlobal.funIfNull(dsBean.getStrCloseDS(), "N", dsBean.getStrCloseDS()));

		List<clsDeliveryScheduleModuledtl> listObjDtl = new ArrayList<clsDeliveryScheduleModuledtl>();
		double totalamt = 0.0;
		for (clsDeliveryScheduleModuledtl objDtl : dsBean.getListObjDeliveryScheduleModuledtl()) {

			totalamt = totalamt + objDtl.getDblTotalPrice();
			objDtl.setDblWeight(0.0);
			listObjDtl.add(objDtl);
		}
		objDSModel.setListDeliveryModelDtl(listObjDtl);

		objDSModel.setDblTotalAmount(totalamt);

		return objDSModel;
	}

	@RequestMapping(value = "/loadDSCode", method = RequestMethod.GET)
	public @ResponseBody clsDeliveryScheduleBean funAssignFields1(@RequestParam("dsCode") String dsCode, HttpServletRequest req, HttpServletResponse response) {
		List<clsDeliveryScheduleModuledtl> listDSDtl = new ArrayList<clsDeliveryScheduleModuledtl>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsDeliveryScheduleModulehd objModel = objDeliveryScheduleService.funLoadDSData(dsCode, clientCode);
		clsDeliveryScheduleBean objBean = new clsDeliveryScheduleBean();

		if (null != objModel) {
			objBean.setStrDSCode(objModel.getStrDSCode());
			objBean.setStrPOCode(objModel.getStrPOCode());
			objBean.setStrAgainst(objModel.getStrAgainst());
			objBean.setStrLocCode(objModel.getStrLocCode());
			objBean.setStrNarration(objModel.getStrNarration());
			objBean.setDteDSDate(objGlobal.funGetDate("dd-MM-yyyy", objModel.getDteDSDate()));
			objBean.setStrCloseDS(objModel.getStrCloseDS());

			for (clsDeliveryScheduleModuledtl objDtl : objModel.getListDeliveryModelDtl()) {

				clsProductMasterModel objProdModel = objProductMasterService.funGetObject(objDtl.getStrProdCode(), clientCode);
				objDtl.setStrProdName(objProdModel.getStrProdName());
				objDtl.setStrRemarks(objDtl.getStrRemarks());

				listDSDtl.add(objDtl);
			}
			objBean.setListObjDeliveryScheduleModuledtl(listDSDtl);
		}

		return objBean;

	}

	@RequestMapping(value = "/frmPOForDS", method = RequestMethod.GET)
	public ModelAndView funOpenPOForDS() {
		return new ModelAndView("frmPOForDS");

	}

	@RequestMapping(value = "/loadPOforDS", method = RequestMethod.GET)
	public @ResponseBody List funAssignFields2(HttpServletRequest req, HttpServletResponse response) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String sql = "Select a.strPOCode, DATE_FORMAT(a.dtPODate,'%d-%m-%Y') from tblpurchaseorderhd a " + " where a.strClientCode='" + clientCode + "' and a.strclosePO='No'; ";
		List listPO = objGlobalFunctionsService.funGetList(sql, "sql");

		return listPO;

	}

}