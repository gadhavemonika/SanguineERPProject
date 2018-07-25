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
public class clsSalesOrderUnplanned_JOController {

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	// Open Intent Status
	@RequestMapping(value = "/frmUnplanned_JO", method = RequestMethod.GET)
	public ModelAndView funOpenSOIntentStausModal(@RequestParam("soCode") String soCode, HttpServletRequest req, HashMap<String, Object> model) {

		// Remove Sales Order Code from Session
		req.getSession().removeAttribute("soCode");

		model.put("soCode", soCode);

		// Add Sales Order Code in Session
		req.getSession().setAttribute("soCode", soCode);

		model.put("tableRows", funSetDataSOUnPlanned_JOStatusModal(soCode, req));
		return new ModelAndView("frmUnplanned_JO");
	}

	@SuppressWarnings("rawtypes")
	public ArrayList<Object> funSetDataSOUnPlanned_JOStatusModal(@RequestParam("soCode") String soCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		ArrayList<Object> mainList = new ArrayList<Object>();
		// String
		// sqlData="select a.strJOCode,a.dteJODate,b.strProdCode,a.dblQty,a.strSOCode, "
		// + "a.dteDateCreated,a.dblQty ,b.strStagDel,b.strUOM, "
		// +
		// "b.strProdName,b.strDesc,a.dteLastModified,a.dteJODate,a.dblQty,a.strSOCode,a.strParentJOCode,  "
		// + "a.dteJODate,a.strAuthorise,a.strStatus "
		// +
		// "from tbljoborderhd a ,tblproductmaster b where a.strClientCode='060.001' and a.strProdCode=b.strProdCode";

		String sqlData = "SELECT ifnull(a.strJOCode,''),ifnull( DATE_FORMAT(a.dteJODate,'%d-%m-%Y'),''),ifnull(b.strProdCode,''),ifnull(f.strProdName,''), ifnull(ROUND(a.dblQty,2),'')," + " ifnull(b.strJACode,''),ifnull( DATE_FORMAT(c.dteJADate,'%d-%m-%Y'),''), ifnull(ROUND(b.dblQty,2),''),ifnull(c.strSCCode,''),ifnull(g.strPName,''),ifnull( d.strDNCode,''), "
				+ " ifnull(DATE_FORMAT(d.dteDNDate,'%d-%m-%Y'),''), ifnull(DATE_FORMAT(d.dteExpDate,'%d-%m-%Y'),''),ifnull( ROUND(d.dblTotal,2),''),ifnull(e.strSRCode,''), " + " ifnull(b.strNatureOfProcessing,''),ifnull( DATE_FORMAT(e.dteSRDate,'%d-%m-%Y'),''), ifnull(ROUND(h.dblAcceptQty,2),'') " + " FROM tbljoborderhd a left join tbljoborderallocationdtl b on a.strJOCode=b.strJOCode "
				+ " left join  tbljoborderallocationhd c on  b.strJACode=c.strJACode " + " left join tbldeliverynotehd d on b.strJACode=d.strJACode " + " left join tblscreturnhd e on d.strDNCode=e.strSCDNCode " + " left join tblproductmaster f on b.strProdCode=f.strProdCode left join tblpartymaster g on c.strSCCode=g.strPCode " + " left join tblscreturndtl h on e.strSRCode=h.strSRCode "
				+ " WHERE a.strSOCode='" + soCode + "' and a.strClientCode='" + clientCode + "' ";

		List list = objGlobalFunctionsService.funGetList(sqlData, "sql");
		List finalList = new ArrayList();

		for (int i = 0; i < list.size(); i++) {
			int chkForSocompleted = 0;
			List listData = new ArrayList();
			Object[] obj = (Object[]) list.get(i);
			String strSCCode = obj[14].toString();

			for (int a = 0; a < obj.length; a++) {
				listData.add(obj[a]);
			}
			if (strSCCode.isEmpty()) {
				listData.add("Pending");
				chkForSocompleted = 1;
			} else {
				listData.add("Completed");
			}
			if (chkForSocompleted == 1) {
				finalList.add(listData);
			}
		}
		mainList.add(finalList);
		return mainList;
	}
}
