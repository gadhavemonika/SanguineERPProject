package com.sanguine.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsPOSLinkUpBean;
import com.sanguine.model.clsPOSLinkUpModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsPOSLinkUpService;

@Controller
public class clsPOSLinkUpController {

	@Autowired
	private clsPOSLinkUpService objPOSLinkUpService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	// private clsGlobalFunctions objGlobal=null;

	// Open POSLinkUp
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/frmPOSLinkUp", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") clsPOSLinkUpBean objBean, Map<String, Object> model, HttpServletRequest request) {

		try {
			String sql = "select DISTINCT b.strPOSItemCode,b.strPOSItemName,ifnull(b.strWSItemCode,''),ifnull(b.strWSItemName,'') " + "from tblpossalesdtl a,tblposlinkup b " + "where a.strPOSItemCode=b.strPOSItemCode " + "order by b.strWSItemCode";
			System.out.println(sql);

			ArrayList list = (ArrayList) objGlobalFunctionsService.funGetList(sql, "sql");

			List listPOSLinkUp = new ArrayList<clsPOSLinkUpModel>();
			for (int cnt = 0; cnt < list.size(); cnt++) {
				clsPOSLinkUpModel objModel = new clsPOSLinkUpModel();
				Object[] arrObj = (Object[]) list.get(cnt);
				objModel.setStrPOSItemCode(arrObj[0].toString());
				objModel.setStrPOSItemName(arrObj[1].toString());
				objModel.setStrWSItemCode(arrObj[2].toString());
				objModel.setStrWSItemName(arrObj[3].toString());
				listPOSLinkUp.add(objModel);
			}

			objBean.setListPOSLinkUp(listPOSLinkUp);
			model.put("POSLinkUpList", objBean);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("frmPOSLinkUp", "command", objBean);
	}

	// Load Master Data On Form
	/*
	 * @RequestMapping(value = "/frmPOSLinkUp1", method = RequestMethod.POST)
	 * public @ResponseBody clsPOSLinkUpModel
	 * funLoadMasterData(HttpServletRequest request){ //objGlobal=new
	 * clsGlobalFunctions(); String sql=""; String
	 * clientCode=request.getSession().getAttribute("clientCode").toString();
	 * String userCode=request.getSession().getAttribute("userCode").toString();
	 * clsPOSLinkUpBean objBean=new clsPOSLinkUpBean(); String
	 * docCode=request.getParameter("docCode").toString(); //List
	 * listModel=objGlobalFunctionsService.funGetList(sql); clsPOSLinkUpModel
	 * objPOSLinkUp = new clsPOSLinkUpModel(); return objPOSLinkUp; }
	 */

	// Save or Update POSLinkUp
	@RequestMapping(value = "/savePOSLinkUp", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSLinkUpBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String sql_Update = "";
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			List<clsPOSLinkUpModel> listPOSLinkUp = objBean.getListPOSLinkUp();
			for (int cnt = 0; cnt < listPOSLinkUp.size(); cnt++) {
				clsPOSLinkUpModel objModel = listPOSLinkUp.get(cnt);
				objModel.setStrClientCode(clientCode);
				objPOSLinkUpService.funAddUpdatePOSLinkUp(objModel);
				sql_Update = "update tblpossalesdtl set strWSItemCode='" + objModel.getStrWSItemCode() + "' " + "where strPOSItemCode='" + objModel.getStrPOSItemCode() + "' and strClientCode='" + objModel.getStrClientCode() + "' ";
				objPOSLinkUpService.funExecute(sql_Update);
			}

			return new ModelAndView("redirect:/frmPOSLinkUp.html");
		} else {
			return new ModelAndView("frmPOSLinkUp");
		}
	}
}
