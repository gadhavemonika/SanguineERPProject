package com.sanguine.excise.controller;

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

import com.sanguine.excise.bean.clsPOSExciseLinkUpBean;
import com.sanguine.excise.model.clsExcisePOSLinkUpModel;
import com.sanguine.excise.service.clsExcisePOSLinkUpService;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsPOSExciseLinkUpController {

	@Autowired
	private clsExcisePOSLinkUpService objExcisePOSLinkUpService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	// Open POSLinkUp
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/frmExcisePOSLinkUp", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") clsPOSExciseLinkUpBean objBean, Map<String, Object> model, HttpServletRequest req) {

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			String sql = "select distinct a.strPOSItemCode,a.strPOSItemName,a.strBrandCode,a.strBrandName,a.intConversionFactor " + " from tblexciseposlinkup a where a.strClientCode='" + clientCode + "' order by a.strBrandCode  ";
			ArrayList list = (ArrayList) objGlobalFunctionsService.funGetDataList(sql, "sql");

			List listExcisePOSLinkUp = new ArrayList<clsExcisePOSLinkUpModel>();
			for (int cnt = 0; cnt < list.size(); cnt++) {
				clsExcisePOSLinkUpModel objModel = new clsExcisePOSLinkUpModel();
				Object[] arrObj = (Object[]) list.get(cnt);

				objModel.setStrPOSItemCode(arrObj[0].toString());
				objModel.setStrPOSItemName(arrObj[1].toString());
				objModel.setStrBrandCode(arrObj[2].toString());
				objModel.setStrBrandName(arrObj[3].toString());
				objModel.setIntConversionFactor(Integer.parseInt(arrObj[4].toString()));
				listExcisePOSLinkUp.add(objModel);
			}

			objBean.setListExcisePOSLinkUp(listExcisePOSLinkUp);
			model.put("ExcisePOSLinkUpList", objBean);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExcisePOSLinkUp_1", "command", objBean);
		} else {
			return new ModelAndView("frmExcisePOSLinkUp", "command", objBean);
		}
	}

	// Save or Update POSLinkUp
	@RequestMapping(value = "/savePOSExciseLinkUp", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSExciseLinkUpBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {
			String sql_Update = "";
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			List<clsExcisePOSLinkUpModel> listExcisePOSLinkUp = objBean.getListExcisePOSLinkUp();
			boolean flgBlankModel = false;
			for (int cnt = 0; cnt < listExcisePOSLinkUp.size(); cnt++) {
				clsExcisePOSLinkUpModel objModel = listExcisePOSLinkUp.get(cnt);
				if (objModel.getStrPOSItemCode() == null) {
					flgBlankModel = true;
					// objModel.setIntConversionFactor(1);
					// objModel.setStrBrandCode("");
					// objModel.setStrBrandName("");
					// objModel.setStrClientCode("");
					// objModel.setStrPOSItemCode("");
					// objModel.setStrPOSItemName("");
				}
				objModel.setStrClientCode(clientCode);
				if (!flgBlankModel) {
					objExcisePOSLinkUpService.funAddUpdatePOSLinkUp(objModel);
				}

				sql_Update = "update tblexcisepossale set strBrandCode='" + objModel.getStrBrandCode() + "' " + "where strPOSItemCode='" + objModel.getStrPOSItemCode() + "' and strClientCode='" + objModel.getStrClientCode() + "' ";
				objExcisePOSLinkUpService.funExecute(sql_Update);
			}
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Data Save Successfully");
			return new ModelAndView("redirect:/frmExcisePOSLinkUp.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmExcisePOSLinkUp.html?saddr=" + urlHits);
		}
	}
}
