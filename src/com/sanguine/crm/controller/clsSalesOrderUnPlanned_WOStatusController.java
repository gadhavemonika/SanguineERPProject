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
public class clsSalesOrderUnPlanned_WOStatusController {

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	// Open Intent Status
	@RequestMapping(value = "/frmUnplanned_WO", method = RequestMethod.GET)
	public ModelAndView funOpenSOIntentStausModal(@RequestParam("soCode") String soCode, HttpServletRequest req, HashMap<String, Object> model) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		// Remove Sales Order Code from Session
		req.getSession().removeAttribute("soCode");

		model.put("soCode", soCode);

		// Add Sales Order Code in Session
		req.getSession().setAttribute("soCode", soCode);

		String sql = " select * from tblproductionhd a,tblworkorderhd b " + " where a.strWOCode=b.strWOCode and b.strSOCode='" + soCode + "' " + "  and a.strClientCode ='" + clientCode + "' and b.strClientCode='" + clientCode + "' ";
		List list = objGlobalFunctionsService.funGetList(sql, "sql");
		if (list.size() == 0) {
			model.put("tableRows", funSetDataSOStatusUnPlanned_WOModal(soCode, req));
		} else {
			model.put("tableRows", new ArrayList<Object>());
		}

		return new ModelAndView("frmUnplanned_WO");
	}

	@SuppressWarnings("rawtypes")
	public ArrayList<Object> funSetDataSOStatusUnPlanned_WOModal(@RequestParam("soCode") String soCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		ArrayList<Object> mainList = new ArrayList<Object>();
		/*
		 * String sqlData=
		 * "select a.strJOCode,a.dteJODate,b.strProdCode,a.dblQty,a.strSOCode, "
		 * + "a.dteDateCreated,a.dblQty ,b.strUOM, " +
		 * "b.strProdName,a.dteLastModified,a.dteJODate,a.dblQty,a.strSOCode , "
		 * + "a.dteJODate,a.strStatus " +
		 * "from tbljoborderhd a ,tblproductmaster b where a.strClientCode='"
		 * +clientCode+"' and a.strProdCode=b.strProdCode";
		 */

		String sqlData = " select a.strProdCode,c.strProdName,c.strPartNo,a.strWOCode,DATE_FORMAT(a.dtWODate,'%d-%m-%Y'), " + " a.dblQty,b.strProcessCode,d.strProcessName,b.strStatus,'','' " + " from tblworkorderhd a ,tblworkorderdtl b,tblproductmaster c,tblprocessmaster d " + " where a.strWOCode=b.strWOCode and a.strSOCode='" + soCode + "' "
				+ " and a.strProdCode=c.strProdCode and b.strProcessCode=d.strProcessCode " + " and b.strClientCode ='" + clientCode + "'  and c.strClientCode='" + clientCode + "' " + " and d.strClientCode='" + clientCode + "' and a.strClientCode='" + clientCode + "'  ";

		List list = objGlobalFunctionsService.funGetList(sqlData, "sql");

		mainList.add(list);
		return mainList;
	}
}
