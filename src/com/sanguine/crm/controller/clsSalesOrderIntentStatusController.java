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
public class clsSalesOrderIntentStatusController {

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	// Open Intent Status
	@RequestMapping(value = "/frmIndend_Status", method = RequestMethod.GET)
	public ModelAndView funOpenSOIntentStausModal(@RequestParam("soCode") String soCode, HttpServletRequest req, HashMap<String, Object> model) {

		// Remove Sales Order Code from Session
		req.getSession().removeAttribute("soCode");

		model.put("soCode", soCode);

		// Add Sales Order Code in Session
		req.getSession().setAttribute("soCode", soCode);
		// HashMap<Integer,String> hm=new HashMap<Integer, String>();

		model.put("tableRows", funSetDataSOIntentStatusModal(soCode, req));
		return new ModelAndView("frmIndend_Status");
	}

	@SuppressWarnings("rawtypes")
	public ArrayList<Object> funSetDataSOIntentStatusModal(@RequestParam("soCode") String soCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		ArrayList<Object> mainList = new ArrayList<Object>();
		/*
		 * String sqlData=
		 * "select a.strSGCode,a.dtCreatedDate, a.strProdCode ,a.strProdName ,a.strPartNo,a.dblCostRM,a.dtLastModified, "
		 * +"a.strSaleNo,a.strSGCode,a.dtCreatedDate,a.dblBatchQty "
		 * +"from tblproductmaster a where a.strClientCode='"+clientCode+"'";
		 */

		String sqlData = " select a.strPIcode,b.strProdCode,c.strProdName,c.strPartNo,b.strInStock,b.dblQty,DATE_FORMAT(b.dtReqDate,'%d-%m-%Y') " + " from tblpurchaseindendhd a,tblpurchaseindenddtl b ,tblproductmaster c " + " where a.strPIcode=b.strPIcode and  a.strDocCode='" + soCode + "' " + " and b.strProdCode=c.strProdCode ";
		List list = objGlobalFunctionsService.funGetList(sqlData, "sql");
		mainList.add(list);
		return mainList;
	}
}
