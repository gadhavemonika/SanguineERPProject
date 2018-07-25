package com.sanguine.crm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsSalesOrderPurchasetStatusController {

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	// Open Intent Status
	@RequestMapping(value = "/frmPO_Status", method = RequestMethod.GET)
	public ModelAndView funOpenSOIntentStausModal(@RequestParam("soCode") String soCode, HttpServletRequest req, HashMap<String, Object> model) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		// Remove Sales Order Code from Session
		req.getSession().removeAttribute("soCode");

		model.put("soCode", soCode);

		// Add Sales Order Code in Session
		req.getSession().setAttribute("soCode", soCode);

		String sql = "select e.strPIcode,a.strPOCode,DATE_FORMAT(a.dtPODate,'%d-%m-%Y'), a.strSuppCode,d.strPName, " + " b.strProdCode,c.strPartNo,c.strProdName, " + " c.strUOM,b.dblOrdQty,b.dblOrdQty,b.dblPrice,0.00,'Complete' " + "  from tblpurchaseorderhd a,tblpurchaseorderdtl b,tblproductmaster c," + " tblpartymaster d,tblpurchaseindendhd e "
				+ " where a.strPOCode=b.strPOCode and b.strProdCode=c.strProdCode and a.strSuppCode=d.strPCode " + "  and a.strSOCode=e.strPIcode  and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + clientCode + "'  and c.strClientCode='" + clientCode + "' " + "  and d.strClientCode='" + clientCode + "'  and e.strClientCode='" + clientCode + "' " + " and e.strDocCode='"
				+ soCode + "' ";
		List list = objGlobalFunctionsService.funGetList(sql, "sql");
		model.put("tableRows", list);
		return new ModelAndView("frmPO_Status");
	}

	@SuppressWarnings("rawtypes")
	public ArrayList<Object> funSetDataSOPurchaseStatusModal(@RequestParam("soCode") String soCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		ArrayList<Object> mainList = new ArrayList<Object>();
		String sqlData = "select a.strSGCode,a.dtCreatedDate, a.strProdCode ,a.strProdName ,a.strPartNo,a.dblCostRM,a.dtLastModified, " + "a.strSaleNo,a.strUOM,a.dtCreatedDate,a.dblBatchQty,a.dblBatchQty,a.strSpecification,a.strProdCode " + "from tblproductmaster a where a.strClientCode='" + clientCode + "'";
		List list = objGlobalFunctionsService.funGetList(sqlData, "sql");

		mainList.add(list);
		return mainList;
	}
}
