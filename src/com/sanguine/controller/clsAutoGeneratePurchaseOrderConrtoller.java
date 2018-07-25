package com.sanguine.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsAttachDocBean;
import com.sanguine.bean.clsAutoGeneratePOBean;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsPurchaseOrderDtlModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsProductMasterService;

@Controller
public class clsAutoGeneratePurchaseOrderConrtoller {

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsProductMasterService objProductMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@RequestMapping(value = "/autoGeneratePO", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") clsAutoGeneratePOBean bean, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		// String docCode=request.getSession().getAttribute("code").toString();

		String formTitle = request.getParameter("formName").toString();
		String transactionName = request.getParameter("transName").toString();
		String suppCode = request.getParameter("suppCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String locCode = request.getSession().getAttribute("locationCode").toString();
		String fromDate = objGlobal.funGetCurrentDateTime("yyyy-MM-dd");
		String toDate = objGlobal.funGetCurrentDateTime("yyyy-MM-dd");
		String startDate = request.getSession().getAttribute("startDate").toString();
		String[] sp = startDate.split(" ");
		String[] spDate = sp[0].split("/");
		startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
		String stockableItem = "B";
		objGlobal.funInvokeStockFlash(startDate, locCode, fromDate.split(" ")[0], toDate.split(" ")[0], clientCode, userCode, stockableItem, request, response);
		model.put("formTitle", formTitle);
		model.put("transactionName", transactionName);
		model.put("suppCode", suppCode);
		return new ModelAndView("frmAutoGeneratePO", model);
	}

	@RequestMapping(value = "/loadAutoPOData", method = RequestMethod.POST)
	public @ResponseBody List<clsPurchaseOrderDtlModel> funOpenFormWithDocs(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> model = new HashMap<String, Object>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		// model.put("documentList",objAttDocService.funListDocs(docCode,
		// clientCode));

		List<clsAutoGeneratePOBean> temp = (List<clsAutoGeneratePOBean>) req.getSession().getAttribute("autoPO");
		List<clsPurchaseOrderDtlModel> listPODtlModel = new ArrayList<clsPurchaseOrderDtlModel>();
		for (clsAutoGeneratePOBean ob : temp) {
			if (ob.getDblTotalQty() > 0) {
				clsPurchaseOrderDtlModel objDtl = new clsPurchaseOrderDtlModel();
				objDtl.setStrProdCode(ob.getStrProdCode());
				objDtl.setStrProdName(ob.getStrProdName());
				objDtl.setStrUOM(ob.getStrUOM());
				objDtl.setDblOrdQty(ob.getDblTotalQty());
				objDtl.setStrRemarks("");
				objDtl.setStrSuppCode("");
				objDtl.setStrSuppName("");
				objDtl.setStrPICode("");
				objDtl.setDblPrice(ob.getDblUnitPrice());
				objDtl.setDblWeight(ob.getDblWeight());
				objDtl.setStrSuppCode(ob.getStrSuppCode());
				objDtl.setStrSuppName(ob.getStrSuppName());
				objDtl.setStrUpdate("N");

				listPODtlModel.add(objDtl);
			}

		}
		req.getSession().setAttribute("AutoPOData", listPODtlModel);
		return listPODtlModel;

	}

	@RequestMapping(value = "/loadProdPOData", method = RequestMethod.GET)
	public @ResponseBody clsAutoGeneratePOBean funLoadProdPOData(@RequestParam("prodCode") String prodCode, @RequestParam("dtFrom") String dtFrom, @RequestParam("dtTo") String dtTo, @RequestParam("noDays") int noDays, @RequestParam("suppCode") String suppCode, HttpServletRequest req, HttpServletResponse response) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String locCode = req.getSession().getAttribute("locationCode").toString();
		clsProductMasterModel objModel = objProductMasterService.funGetObject(prodCode, clientCode);
		clsAutoGeneratePOBean objAutoPO = new clsAutoGeneratePOBean();
		objAutoPO.setStrProdCode(prodCode);
		objAutoPO.setStrProdName(objModel.getStrProdName());
		objAutoPO.setStrUOM(objModel.getStrUOM());
		try {
			Date frmDate = sf.parse(dtFrom);
			Date toDate = sf.parse(dtTo);
			int totdays = Days.daysBetween(new LocalDate(frmDate), new LocalDate(toDate)).getDays();

			if (totdays >= 0) {
				totdays = totdays + 1;
			}

			List listRateSupplier = funGetProductRateAccoringToSupplier(prodCode, suppCode, req);

			for (Object objRate : listRateSupplier) {
				Object[] objArrRate = (Object[]) objRate;

				objAutoPO.setDblUnitPrice(Double.parseDouble(objArrRate[3].toString()));
				objAutoPO.setStrSuppCode(objArrRate[4].toString());
				objAutoPO.setStrSuppName(objArrRate[5].toString());
				objAutoPO.setDblWeight(Double.parseDouble(objArrRate[7].toString()));
			}

			String sql = " select b.strProdCode,sum(b.dblQty) from tblinvoicehd a, tblinvoicedtl b  " + "  where a.strInvCode=b.strInvCode and b.strProdCode='" + prodCode + "' " + " and date(a.dteInvDate) between '" + dtFrom + "' and '" + dtTo + "'  and a.strClientCode='" + clientCode + "' " + " group by b.strProdCode; ";
			List listOrder = objGlobalFunctionsService.funGetList(sql, "sql");
			double dblOrderQty = 0;
			for (Object obj : listOrder) {
				Object[] objArr = (Object[]) obj;
				dblOrderQty = Double.parseDouble(objArr[1].toString());
				dblOrderQty = (dblOrderQty / totdays) * noDays;
			}
			objAutoPO.setDblOrderQty(dblOrderQty);

			sql = " select a.strProdCode,a.dblClosingStk from tblcurrentstock a  " + " where a.strProdCode='" + prodCode + "' and a.strLocCode ='" + locCode + "' " + " and a.strUserCode='" + userCode + "' and a.strClientCode='" + clientCode + "' ; ";

			List listCurr = objGlobalFunctionsService.funGetList(sql, "sql");
			double dblCurrstk = 0;
			for (Object ob : listCurr) {
				Object[] obArr = (Object[]) ob;
				dblCurrstk = Double.parseDouble(obArr[1].toString());
			}
			objAutoPO.setDblCurrentStk(dblCurrstk);
			sql = " select b.strProdCode,sum(b.dblOrdQty) from tblpurchaseorderhd a ,tblpurchaseorderdtl b  " + " where a.strPOCode = b.strPOCode   and  b.strProdCode='" + prodCode + "'  and a.strClientCode='" + clientCode + "'  " + "  and a.strPOCode not in  (select g.strPONo from tblgrnhd g where g.strClientCode='" + clientCode + "')  " + "  group by b.strProdCode; ";

			List listOpenPO = objGlobalFunctionsService.funGetList(sql, "sql");
			double dblOpenPOQty = 0;
			for (Object ob1 : listOpenPO) {
				Object[] obArr1 = (Object[]) ob1;
				dblOpenPOQty = Double.parseDouble(obArr1[1].toString());
			}
			objAutoPO.setDblOpenPOQty(dblOpenPOQty);
			double dblTotalQty = dblOrderQty - dblCurrstk - dblOpenPOQty;
			if (dblTotalQty < 0) {
				dblTotalQty = 0;
			}
			objAutoPO.setDblTotalQty(dblTotalQty);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return objAutoPO;

	}

	public List funGetProductRateAccoringToSupplier(String prodCode, String suppCode, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String RateFrom = req.getSession().getAttribute("RateFrom").toString();
		String sql = "";
		List list = new ArrayList<>();
		List listRet = new ArrayList<>();
		if (RateFrom.equalsIgnoreCase("PurchaseRate")) {
			sql = "select a.strProdcode,a.strProdName,a.strUOM,a.dblCostRM, ifnull(b.strSuppCode,'') as strSuppCode," + " ifnull(c.strPName,'') as strPName,a.strProdType,a.dblWeight" + " from tblproductmaster a left outer join tblprodsuppmaster b on a.strProdCode=b.strProdCode and b.strClientCode='" + clientCode + "' and b.strDefault='Y' "
					+ " left outer join tblpartymaster c on b.strSuppCode=c.strPCode and c.strClientCode='" + clientCode + "' " + " where  a.strProdCode='" + prodCode + "' and a.strClientCode='" + clientCode + "'";
			list = objGlobalFunctionsService.funGetProductDataForTransaction(sql, prodCode, clientCode);
		}
		if (RateFrom.equalsIgnoreCase("SupplierRate")) {
			if (suppCode != null && suppCode != "") {
				sql = "select a.strProdcode,a.strProdName,a.strUOM,b.dblLastCost, ifnull(b.strSuppCode,'') as strSuppCode," + " ifnull(c.strPName,'') as strPName,a.strProdType,a.dblWeight" + " from tblproductmaster a left outer join tblprodsuppmaster b on a.strProdCode=b.strProdCode and b.strClientCode='" + clientCode + "' "
						+ " left outer join tblpartymaster c on b.strSuppCode=c.strPCode and c.strClientCode='" + clientCode + "' " + " where  a.strProdCode='" + prodCode + "' and a.strClientCode='" + clientCode + "' and strSuppCode='" + suppCode + "'";
				list = objGlobalFunctionsService.funGetProductDataForTransaction(sql, prodCode, clientCode);
				if (list.size() < 0 || list.isEmpty() || list == null) {
					sql = "select a.strProdcode,a.strProdName,a.strUOM,a.dblCostRM, ifnull(b.strSuppCode,'') as strSuppCode," + " ifnull(c.strPName,'') as strPName,a.strProdType,a.dblWeight" + " from tblproductmaster a left outer join tblprodsuppmaster b on a.strProdCode=b.strProdCode and b.strClientCode='" + clientCode + "' and b.strDefault='Y'"
							+ " left outer join tblpartymaster c on b.strSuppCode=c.strPCode and c.strClientCode='" + clientCode + "' " + " where  a.strProdCode='" + prodCode + "' and a.strClientCode='" + clientCode + "'";
					list = objGlobalFunctionsService.funGetProductDataForTransaction(sql, prodCode, clientCode);
				}
			} else {
				sql = "select a.strProdcode,a.strProdName,a.strUOM,a.dblCostRM, ifnull(b.strSuppCode,'') as strSuppCode," + " ifnull(c.strPName,'') as strPName,a.strProdType,a.dblWeight" + " from tblproductmaster a left outer join tblprodsuppmaster b on a.strProdCode=b.strProdCode and b.strClientCode='" + clientCode + "' and b.strDefault='Y'"
						+ " left outer join tblpartymaster c on b.strSuppCode=c.strPCode and c.strClientCode='" + clientCode + "' " + " where  a.strProdCode='" + prodCode + "' and a.strClientCode='" + clientCode + "'";
				list = objGlobalFunctionsService.funGetProductDataForTransaction(sql, prodCode, clientCode);
			}
		}

		sql = "select b.strProductCode,c.strProdName,c.strUOM,b.dblRate,a.strSuppCode,d.strPName,c.strProdType,c.dblWeight " + " from tblrateconthd a,tblratecontdtl b ,  tblproductmaster c,tblpartymaster d " + " where a.strRateContNo=b.strRateContNo and a.strSuppCode='" + suppCode + "' and b.strProductCode='" + prodCode + "'   "
				+ " and b.strProductCode=c.strProdCode and a.strSuppCode=d.strPCode   and a.strClientCode='" + clientCode + "' ";
		List listProdRate = objGlobalFunctionsService.funGetList(sql, "sql");
		if (listProdRate.size() > 0) {
			listRet = listProdRate;
		} else {
			listRet = list;
		}

		if (listRet.size() == 0) {
			listRet.add("Invalid Product Code");
			return listRet;
		} else {
			return listRet;
		}

	}

	@RequestMapping(value = "/SaveSessionPO", method = RequestMethod.POST)
	public @ResponseBody List funSaveSessionPOForm(@ModelAttribute("command") clsAutoGeneratePOBean bean, BindingResult result, HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		List<clsPurchaseOrderDtlModel> listPODtlModel = new ArrayList<clsPurchaseOrderDtlModel>();
		for (clsAutoGeneratePOBean ob : bean.getListAutoGenPOBean()) {
			if (ob.getDblTotalQty() > 0) {
				clsPurchaseOrderDtlModel objDtl = new clsPurchaseOrderDtlModel();
				objDtl.setStrProdCode(ob.getStrProdCode());
				objDtl.setStrProdName(ob.getStrProdName());
				objDtl.setStrUOM(ob.getStrUOM());
				objDtl.setDblOrdQty(ob.getDblTotalQty());
				objDtl.setStrRemarks("");
				objDtl.setStrSuppCode("");
				objDtl.setStrSuppName("");
				objDtl.setStrPICode("");
				objDtl.setDblPrice(ob.getDblUnitPrice());
				objDtl.setDblWeight(ob.getDblWeight());
				objDtl.setStrSuppCode(ob.getStrSuppCode());
				objDtl.setStrSuppName(ob.getStrSuppName());
				objDtl.setStrUpdate("N");

				listPODtlModel.add(objDtl);
			}
		}
		request.getSession().setAttribute("AutoPOData", listPODtlModel);
		return listPODtlModel;
	}

}
