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

import com.sanguine.bean.clsOpeningStkBean;
import com.sanguine.bean.clsPOSLinkUpBean;
import com.sanguine.bean.clsTallyLinkUpBean;
import com.sanguine.model.clsPOSLinkUpModel;
import com.sanguine.model.clsTallyLinkUpModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsTallyLinkUpService;

@Controller
public class clsTallyLinkUpController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsTallyLinkUpService objTallyLinkUpService;

	private clsGlobalFunctions objGlobal = null;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/frmTallyLinkUp", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") clsTallyLinkUpBean objBean, Map<String, Object> model, HttpServletRequest request) {

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
			String sql = "";
			// if(objBean.getStrLinkup().equals("Supplier"))
			// {
			sql = " select a.strPCode,a.strPName,ifnull(b.strGDes,''),ifnull(b.strTallyCode,'') " + " from tblpartymaster a " + " left outer join tbltallylinkup b on a.strPCode=b.strGroupCode " + " where a.strPType='Supp' and a.strClientCode='" + clientCode + "' order by a.strPName  ";
			// }

			// if(objBean.getStrLinkup().equals("Supplier"))
			// {
			// sql=
			// " select a.strPCode,a.strPName,ifnull(b.strGDes,''),ifnull(b.strTallyCode,'') "
			// + " from tblpartymaster a "
			// +
			// " left outer join tbltallylinkup b on a.strPCode=b.strGroupCode "
			// +
			// " where a.strPType='cust' and a.strClientCode='"+clientCode+"' order by a.strPName  ";
			// }

			ArrayList list = (ArrayList) objGlobalFunctionsService.funGetDataList(sql, "sql");
			List listTallyLinkUp = new ArrayList<clsTallyLinkUpModel>();
			for (int cnt = 0; cnt < list.size(); cnt++) {
				clsTallyLinkUpModel objModel = new clsTallyLinkUpModel();
				Object[] arrObj = (Object[]) list.get(cnt);
				objModel.setStrGroupCode(arrObj[0].toString());
				objModel.setStrGroupName(arrObj[1].toString());
				objModel.setStrGDes(arrObj[2].toString());
				objModel.setStrTallyCode(arrObj[3].toString());
				listTallyLinkUp.add(objModel);
			}

			objBean.setListTallyLinkUp(listTallyLinkUp);
			model.put("TallyLinkUpList", objBean);

		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmTallyLinkUp_1", "command", objBean);

		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmTallyLinkUp", "command", objBean);
		} else {
			return null;
		}
	}

	// Save or Update LinkUp
	@RequestMapping(value = "/saveTallyLinkUp", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsTallyLinkUpBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {

			objGlobal = new clsGlobalFunctions();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			List<clsTallyLinkUpModel> listTallyLinkUp = objBean.getListTallyLinkUp();
			for (int cnt = 0; cnt < listTallyLinkUp.size(); cnt++) {
				clsTallyLinkUpModel objModel = listTallyLinkUp.get(cnt);
				objModel.setStrClientCode(clientCode);
				// objTallyLinkUpService.funAddUpdatePOSLinkUp(objModel);
				String delete = " delete from tbltallylinkup where strGroupCode='" + objModel.getStrGroupCode() + "' and strClientCode='" + clientCode + "' ";
				objTallyLinkUpService.funExecute(delete);
				objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				;
				objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setStrUserCreated(userCode);
				objModel.setStrUserEdited(userCode);
				objTallyLinkUpService.funAddUpdate(objModel);
			}

			return new ModelAndView("redirect:/frmTallyLinkUp.html");
		} else {
			return new ModelAndView("frmTallyLinkUp");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadLinkUpData", method = RequestMethod.POST)
	public ModelAndView funLoadLinkupData(@ModelAttribute("command") clsTallyLinkUpBean objBean, Map<String, Object> model, HttpServletRequest request) {

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String urlHits = "1";
		try {
			// urlHits=request.getParameter("saddr").toString();
			String sql = "";
			if (objBean.getStrLinkup().equals("Supplier")) {
				sql = " select a.strPCode,a.strPName,ifnull(b.strGDes,''),ifnull(b.strTallyCode,'') " + " from tblpartymaster a " + " left outer join tbltallylinkup b on a.strPCode=b.strGroupCode " + " where a.strPType='Supp' and a.strClientCode='" + clientCode + "' order by a.strPName  ";
			}

			if (objBean.getStrLinkup().equals("Customer")) {
				sql = " select a.strPCode,a.strPName,ifnull(b.strGDes,''),ifnull(b.strTallyCode,'') " + " from tblpartymaster a " + " left outer join tbltallylinkup b on a.strPCode=b.strGroupCode " + " where a.strPType='cust' and a.strClientCode='" + clientCode + "' order by a.strPName  ";
			}

			if (objBean.getStrLinkup().equals("Tax")) {
				sql = " select a.strTaxCode,a.strTaxDesc,a.strTaxOnSP,ifnull(b.strTallyCode,'') " + " from tbltaxhd a left outer join tbltallylinkup b on a.strTaxCode=b.strGroupCode " + " where  a.strClientCode='" + clientCode + "' order by a.strTaxCode  ";
			}
			if (objBean.getStrLinkup().equals("SubGroup")) {
				sql = " select a.strSGCode,a.strSGName,a.strSGDesc,ifnull(b.strTallyCode,'') " + " from tblsubgroupmaster a " + " left outer join tbltallylinkup b on a.strSGCode=b.strGroupCode  " + " where  a.strClientCode='" + clientCode + "' order by a.strSGCode   ";
			}

			ArrayList list = (ArrayList) objGlobalFunctionsService.funGetDataList(sql, "sql");
			List listTallyLinkUp = new ArrayList<clsTallyLinkUpModel>();
			for (int cnt = 0; cnt < list.size(); cnt++) {
				clsTallyLinkUpModel objModel = new clsTallyLinkUpModel();
				Object[] arrObj = (Object[]) list.get(cnt);
				objModel.setStrGroupCode(arrObj[0].toString());
				objModel.setStrGroupName(arrObj[1].toString());
				objModel.setStrGDes(arrObj[2].toString());
				objModel.setStrTallyCode(arrObj[3].toString());
				listTallyLinkUp.add(objModel);
			}

			objBean.setListTallyLinkUp(listTallyLinkUp);
			model.put("TallyLinkUpList", objBean);

		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmTallyLinkUp_1", "command", objBean);

		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmTallyLinkUp", "command", objBean);
		} else {
			return null;
		}
	}

}
