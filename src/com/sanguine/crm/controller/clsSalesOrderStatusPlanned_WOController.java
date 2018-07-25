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
public class clsSalesOrderStatusPlanned_WOController {

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	// Open Intent Status
	@RequestMapping(value = "/frmPlanned_WO", method = RequestMethod.GET)
	public ModelAndView funOpenSOIntentStausModal(@RequestParam("soCode") String soCode, HttpServletRequest req, HashMap<String, Object> model) {

		// Remove Sales Order Code from Session
		req.getSession().removeAttribute("soCode");

		model.put("soCode", soCode);

		// Add Sales Order Code in Session
		req.getSession().setAttribute("soCode", soCode);
		// HashMap<Integer,String> hm=new HashMap<Integer, String>();

		model.put("tableRows", funSetDataSOPlanned_WOStatusModal(soCode, req));
		return new ModelAndView("frmPlanned_WO");
	}

	@SuppressWarnings("rawtypes")
	public ArrayList<Object> funSetDataSOPlanned_WOStatusModal(@RequestParam("soCode") String soCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		ArrayList<Object> mainList = new ArrayList<Object>();
		/*
		 * String sqlData=
		 * "select a.strSGCode,a.dtCreatedDate, a.strProdCode ,a.strProdName ,a.strPartNo,a.dblCostRM,a.dtLastModified, "
		 * +"a.strSaleNo,a.strSGCode,a.dtCreatedDate,a.dblBatchQty "
		 * +"from tblproductmaster a where a.strClientCode='"+clientCode+"'";
		 */

		String sqlData = " select a.strPDCode,DATE_FORMAT(a.dtPDDate,'%d-%m-%Y'),b.strProdCode,d.strProdName,d.strPartNo, " + " b.dblQtyProd,a.strWOCode,DATE_FORMAT(c.dtWODate,'%d-%m-%Y') ,c.dblQty " + " from tblproductionhd a,tblproductiondtl b,tblworkorderhd c ,tblproductmaster d " + " where a.strPDCode=b.strPDCode  and a.strWOCode=c.strWOCode "
				+ " and b.strProdCode=d.strProdCode  and c.strSOCode='" + soCode + "'  " + "  and a.strClientCode ='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + " and c.strClientCode='" + clientCode + "' ";

		List list = objGlobalFunctionsService.funGetList(sqlData, "sql");
		mainList.add(list);
		return mainList;
	}
}
