package com.sanguine.crm.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.crm.bean.clsSalesOrderBean;
import com.sanguine.crm.bean.clsSalesOrderStatusBean;
import com.sanguine.crm.model.clsPartyMasterModel;
import com.sanguine.crm.model.clsSalesOrderDtl;
import com.sanguine.crm.model.clsSalesOrderHdModel;
import com.sanguine.crm.service.clsSalesOrderBOMService;
import com.sanguine.crm.service.clsSalesOrderService;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsProductMasterService;

@Controller
public class clsSalesOrderStatusController {

	@Autowired
	private clsSalesOrderBOMService objSoBomService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsSalesOrderService objSalesOrderService;

	// private clsGlobalFunctions objGlobal=null;

	@Autowired
	private clsProductMasterService objProductMasterService;

	@Autowired
	private clsSalesOrderController objSalesOrderController;

	// Open Sales Order BOM Form
	@RequestMapping(value = "/frmSalesOrderStatus", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest req) {

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSalesOrderStatus_1", "command", new clsSalesOrderStatusBean());
		} else {
			return new ModelAndView("frmSalesOrderStatus", "command", new clsSalesOrderStatusBean());
		}
	}

	// Method use to display so details in modal
	@RequestMapping(value = "/SODataForModal", method = RequestMethod.GET)
	public @ResponseBody clsSalesOrderBean funSODataForModal(@RequestParam("soCode") String soCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsSalesOrderBean objBeanSale = new clsSalesOrderBean();

		List<Object> objSales = objSalesOrderService.funGetSalesOrder(soCode, clientCode);
		clsSalesOrderHdModel objSalesOrderHdModel = null;
		clsLocationMasterModel objLocationMasterModel = null;
		clsPartyMasterModel objPartyMasterModel = null;
		if (objSales != null) {
			Object[] ob = (Object[]) objSales.get(0);
			objSalesOrderHdModel = (clsSalesOrderHdModel) ob[0];
			objLocationMasterModel = (clsLocationMasterModel) ob[1];
			objPartyMasterModel = (clsPartyMasterModel) ob[2];
		}

		objBeanSale = funPrepardHdBean(objSalesOrderHdModel, objLocationMasterModel, objPartyMasterModel);

		List<clsSalesOrderDtl> listSaleDtl = new ArrayList<clsSalesOrderDtl>();
		List<Object> objSalesDtlModelList = objSalesOrderService.funGetSalesOrderDtl(soCode, clientCode);
		for (int i = 0; i < objSalesDtlModelList.size(); i++) {
			Object[] ob = (Object[]) objSalesDtlModelList.get(i);
			clsSalesOrderDtl saleDtl = (clsSalesOrderDtl) ob[0];
			clsProductMasterModel prodmast = (clsProductMasterModel) ob[1];
			saleDtl.setStrProdName(prodmast.getStrProdName());
			saleDtl.setStrProdType(prodmast.getStrProdType());
			saleDtl.setStrPartNo(prodmast.getStrPartNo());

			saleDtl.setDblAvalaibleStk(0.0);
			saleDtl.setDblRequiredQty(0.0);
			saleDtl.setStrStatus("Pending");

			listSaleDtl.add(saleDtl);
		}
		objBeanSale.setListSODtl(listSaleDtl);
		return objBeanSale;
	}

	private clsSalesOrderBean funPrepardHdBean(clsSalesOrderHdModel objSalesOrderHdModel, clsLocationMasterModel objLocationMasterModel, clsPartyMasterModel objPartyMasterModel) {
		clsSalesOrderBean objBeanSale = new clsSalesOrderBean();
		objBeanSale.setStrSOCode(objSalesOrderHdModel.getStrSOCode());
		objBeanSale.setDteSODate(objSalesOrderHdModel.getDteSODate());
		objBeanSale.setDteCPODate(objSalesOrderHdModel.getDteCPODate());
		objBeanSale.setStrCustCode(objSalesOrderHdModel.getStrCustCode());
		objBeanSale.setStrLocCode(objSalesOrderHdModel.getStrLocCode());
		objBeanSale.setStrAgainst(objSalesOrderHdModel.getStrAgainst());
		objBeanSale.setStrCurrency(objSalesOrderHdModel.getStrCurrency());
		objBeanSale.setStrPayMode(objSalesOrderHdModel.getStrPayMode());
		objBeanSale.setDblDisAmt(objSalesOrderHdModel.getDblDisAmt());
		objBeanSale.setStrNarration(objSalesOrderHdModel.getStrNarration());
		objBeanSale.setStrCloseSO(objSalesOrderHdModel.getStrCloseSO());
		objBeanSale.setDblSubTotal(objSalesOrderHdModel.getDblSubTotal());
		objBeanSale.setDblDisAmt(objSalesOrderHdModel.getDblDisAmt());
		objBeanSale.setDblDisRate(objSalesOrderHdModel.getDblDisRate());
		objBeanSale.setDblExtra(objSalesOrderHdModel.getDblExtra());
		objBeanSale.setDblTotal(objSalesOrderHdModel.getDblTotal());
		objBeanSale.setStrLocName(objLocationMasterModel.getStrLocName());
		objBeanSale.setStrcustName(objPartyMasterModel.getStrPName());
		objBeanSale.setDteFulmtDate(objSalesOrderHdModel.getDteFulmtDate());
		objBeanSale.setStrCustPONo(objSalesOrderHdModel.getStrCustPONo());
		objBeanSale.setStrCode(objSalesOrderHdModel.getStrCode());
		objBeanSale.setStrCloseSO(objSalesOrderHdModel.getStrCloseSO());

		objBeanSale.setStrBAdd1(objSalesOrderHdModel.getStrBAdd1());
		objBeanSale.setStrBAdd2(objSalesOrderHdModel.getStrBAdd2());
		objBeanSale.setStrBCity(objSalesOrderHdModel.getStrBCity());
		objBeanSale.setStrBCountry(objSalesOrderHdModel.getStrBCountry());
		objBeanSale.setStrBPin(objSalesOrderHdModel.getStrBPin());
		objBeanSale.setStrBState(objSalesOrderHdModel.getStrBState());

		objBeanSale.setStrSAdd1(objSalesOrderHdModel.getStrSAdd1());
		objBeanSale.setStrSAdd2(objSalesOrderHdModel.getStrSAdd2());
		objBeanSale.setStrSCity(objSalesOrderHdModel.getStrSCity());
		objBeanSale.setStrSCountry(objSalesOrderHdModel.getStrSCountry());
		objBeanSale.setStrSPin(objSalesOrderHdModel.getStrSPin());
		objBeanSale.setStrSState(objSalesOrderHdModel.getStrSState());
		return objBeanSale;
	}

	// Open Unplanned Items In Sales Order Modal
	@RequestMapping(value = "/frmUnplanned_Item", method = RequestMethod.GET)
	public ModelAndView funOpenSOSModal(@RequestParam("soCode") String soCode, HttpServletRequest req, HashMap<String, List> model) {

		// Remove Sales Order Code from Session
		List listUnPlanedPI = null;
		List listSO = new ArrayList();
		req.getSession().removeAttribute("soCode");
		listSO.add(soCode);
		model.put("tableRows", listSO);
		List listPI = objGlobalFunctionsService.funGetListModuleWise(" select * from tblpurchaseindendhd a where a.strDocCode='" + soCode + "' ", "sql");
		if (listPI.size() == 0) {
			listUnPlanedPI = objSalesOrderController.funLoadAutoSalesOrderForPI(soCode, req);
		}
		model.put("listUnPlanedPI", listUnPlanedPI);
		// Add Sales Order Code in Session
		req.getSession().setAttribute("soCode", soCode);

		return new ModelAndView("frmUnplanned_Item");
	}

	@RequestMapping(value = "/showSalesOrder", method = RequestMethod.GET)
	public @ResponseBody List funShowSalesOrder(@RequestParam("fromSoDate") String fromSoDate, @RequestParam("toSoDate") String toSoDate, @RequestParam("strCustCode") String strCustCode, HttpServletRequest req) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List<List<String>> retList = new ArrayList<>();
		try {
			String sql = "select a.strSOCode, DATE_FORMAT(a.dteSODate,'%d-%m-%Y'),b.strPName,  DATE_FORMAT(a.dteFulmtDate,'%d-%m-%Y') " + " from tblsalesorderhd a ,tblpartymaster b " + " where a.strCustCode=b.strPCode and  date(a.dteFulmtDate) " + " between '" + fromSoDate + "' and '" + toSoDate + "' " + " and a.strClientCode='" + clientCode + "' and b.strClientCode ='" + clientCode + "' ";

			if (!strCustCode.equals("")) {
				sql += " and  a.strCustCode = '" + strCustCode + "' ";
			}

			List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
			int percentofProcess = 0;

			for (Object obj : list) {

				Object[] objArr = (Object[]) obj;

				List<String> eachlistObj = new ArrayList();
				eachlistObj.add(objArr[0].toString());
				eachlistObj.add(objArr[1].toString());
				eachlistObj.add(objArr[2].toString());
				eachlistObj.add(objArr[3].toString());

				clsSalesOrderHdModel objSOModel = objSalesOrderService.funGetSalesOrderHd(objArr[0].toString(), clientCode);
				Date soDate = sf.parse(objSOModel.getDteSODate());
				Date fullfillDate = sf.parse(objSOModel.getDteFulmtDate());
				int totdays = Days.daysBetween(new LocalDate(soDate), new LocalDate(fullfillDate)).getDays();
				int poDays = 0;
				int scDays = 0;
				int processDays = 0;
				int opDays = 0;
				if (totdays > 0) {

					sql = " select Date(b.dtPODate) from tblpurchaseindendhd a,tblpurchaseorderhd b  " + " where a.strDocCode='" + objArr[0] + "' and a.strPIcode=b.strSOCode and a.strClientCode='" + clientCode + "'  ";

					List listPo = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
					if (listPo.size() > 0) {
						Object ob = listPo.get(0);
						String poDate = ob.toString();
						Date poDt = sf.parse(poDate);
						poDays = Days.daysBetween(new LocalDate(soDate), new LocalDate(poDt)).getDays();
						processDays = poDays;

					}
					sql = "  select Date(d.dteSRDate) from tbljoborderhd a,tbljoborderallocationhd b,tbljoborderallocationdtl e, " + " tbldeliverynotehd c,tblscreturnhd d " + " where a.strSOCode='" + objArr[0] + "'  and a.strJOCode=e.strJOCode and b.strJACode=e.strJACode  " + " and b.strJACode=c.strJACode and c.strDNCode=d.strSCDNCode;   ";

					List listSC = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
					if (listSC.size() > 0) {
						Object ob2 = listSC.get(0);
						String scDate = ob2.toString();
						Date scDt = sf.parse(scDate);
						scDays = Days.daysBetween(new LocalDate(soDate), new LocalDate(scDt)).getDays();
						processDays = scDays;

					}

					/*
					 * sql=
					 * " select date(a.dtFulmtDate) from tblproductionorderhd a,tblworkorderhd  b  where a.strCode='"
					 * +objArr[0]+"' " +
					 * " and a.strOPCode=b.strSOCode and a.strClientCode='"
					 * +clientCode+"' and b.strClientCode='"+clientCode+"' ";
					 */
					sql = "  select date(a.dtPDDate) from tblproductionhd a,tblworkorderhd  b   where b.strSOCode='" + objArr[0] + "' " + " and a.strWOCode=b.strWOCode and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' ";

					List listOP = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
					if (listOP.size() > 0) {
						/*
						 * Object ob3 = listOP.get(0); String
						 * opDate=ob3.toString(); Date opDt = sf.parse(opDate);
						 * opDays = Days.daysBetween(new LocalDate(soDate), new
						 * LocalDate(opDt)).getDays();
						 */
						opDays = totdays;
						processDays = opDays;
					}

					percentofProcess = (processDays * 100 / totdays);
				} else {
					percentofProcess = 100;
				}
				eachlistObj.add(String.valueOf(percentofProcess));

				retList.add(eachlistObj);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return retList;

	}

}
