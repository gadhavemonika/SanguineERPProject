package com.sanguine.webbooks.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsSecurityShellBean;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.dao.clsGlobalFunctionsDaoImpl;
import com.sanguine.model.clsTreeMasterModel;
import com.sanguine.model.clsUserDtlModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSecurityShellService;

@Controller
public class clsARSecurityShell {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;
	@Autowired
	private clsSecurityShellService objSecurityShellService;
	@Autowired
	private clsGlobalFunctionsDaoImpl objTest;

	@RequestMapping(value = "/frmARSecurityShell", method = RequestMethod.GET)
	public ModelAndView funOpenSecurityShellForm(@ModelAttribute("command") @Valid clsSecurityShellBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		String strModuleNo = request.getSession().getAttribute("moduleNo").toString();
		List<clsTreeMasterModel> objModel = objSecurityShellService.funGetFormList(strModuleNo);
		List<clsTreeMasterModel> objMasters = new ArrayList<clsTreeMasterModel>();
		List<clsTreeMasterModel> objTransactions = new ArrayList<clsTreeMasterModel>();
		List<clsTreeMasterModel> objReports = new ArrayList<clsTreeMasterModel>();
		List<clsTreeMasterModel> objUtilitys = new ArrayList<clsTreeMasterModel>();

		for (Object ob : objModel) {
			Object[] arrOb = (Object[]) ob;
			String type = arrOb[2].toString();
			clsTreeMasterModel objTree = new clsTreeMasterModel();
			switch (type) {
			case "M":

				objTree.setStrFormName(arrOb[0].toString());
				objTree.setStrFormDesc(arrOb[1].toString());

				objMasters.add(objTree);
				break;
			case "L":
				objTree.setStrFormName(arrOb[0].toString());
				objTree.setStrFormDesc(arrOb[1].toString());
				objUtilitys.add(objTree);
				break;
			case "T":

				objTree.setStrFormName(arrOb[0].toString());
				objTree.setStrFormDesc(arrOb[1].toString());
				objTransactions.add(objTree);

				break;

			case "R":
				objTree.setStrFormName(arrOb[0].toString());
				objTree.setStrFormDesc(arrOb[1].toString());

				objReports.add(objTree);
				break;

			}

		}
		clsSecurityShellBean bean = new clsSecurityShellBean();
		bean.setListMasterForms(objMasters);
		bean.setListTransactionForms(objTransactions);
		bean.setListReportForms(objReports);
		bean.setListUtilityForms(objUtilitys);
		model.put("treeList", bean);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmARSecurityShell_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmARSecurityShell");
		} else {
			return new ModelAndView("frmARSecurityShell");
		}

	}

	@RequestMapping(value = "/saveARSecurityShell", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsSecurityShellBean objBean, BindingResult result, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String strModuleNo = req.getSession().getAttribute("moduleNo").toString();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {

			System.out.println(objBean.getListTransactionForms().size());
			// ListIterator<clsTreeMasterModel> lisIte =
			// objBean.getListMasterForms().listIterator();
			List<clsTreeMasterModel> listMasters = objBean.getListMasterForms();
			List<clsTreeMasterModel> listTransactions = objBean.getListTransactionForms();
			List<clsTreeMasterModel> listReports = objBean.getListReportForms();
			List<clsTreeMasterModel> listUtilitys = objBean.getListUtilityForms();
			objSecurityShellService.funDeleteForms(objBean.getStrUserCode(), clientCode, strModuleNo);// to
																										// delete
																										// all
																										// forms
																										// B4
																										// new
																										// entry
																										// add
			if (null != listMasters && listMasters.size() > 0) {

				// while(lisIte.hasNext()){
				//
				// clsTreeMasterModel treedtl = lisIte.next();
				// treedtl.setStrModule(strModuleNo);
				// lisIte.set(treedtl);
				//
				// }

				funSaveUserDtl(listMasters, "M", objBean.getStrUserCode(), req.getSession().getAttribute("usercode").toString(), clientCode, req);
			}
			if (null != listTransactions && listTransactions.size() > 0) {
				funSaveUserDtl(listTransactions, "T", objBean.getStrUserCode(), req.getSession().getAttribute("usercode").toString(), clientCode, req);
			}
			if (null != listReports && listReports.size() > 0) {
				funSaveUserDtl(listReports, "R", objBean.getStrUserCode(), req.getSession().getAttribute("usercode").toString(), clientCode, req);
			}
			if (null != listUtilitys && listUtilitys.size() > 0) {
				funSaveUserDtl(listUtilitys, "L", objBean.getStrUserCode(), req.getSession().getAttribute("usercode").toString(), clientCode, req);
			}
			// remove comment of report form list :RP
			req.getSession().setAttribute("success", true);
		}
		return new ModelAndView("redirect:/frmARSecurityShell.html?saddr=" + urlHits);

	}

	private void funSaveUserDtl(List<clsTreeMasterModel> listForms, String formType, String userCode, String loggedInUserCode, String clientCode, HttpServletRequest req) {
		boolean flgChecked = false;
		String strModuleNo = req.getSession().getAttribute("moduleNo").toString();
		String add = "false", edit = "false", view = "false", print = "false", authorize = "false", delete = "false", grant = "false";
		objGlobal = new clsGlobalFunctions();
		if (listForms.size() > 0) {
			for (int i = 0; i < listForms.size(); i++) {
				clsUserDtlModel objUserDtl = new clsUserDtlModel();
				flgChecked = false;
				add = "false";
				edit = "false";
				view = "false";
				print = "false";
				authorize = "false";
				delete = "false";
				grant = "false";
				clsTreeMasterModel objTemp = (clsTreeMasterModel) listForms.get(i);
				System.out.println("Form Desc=" + objTemp.getStrFormDesc());
				if (null != objTemp.getStrAdd()) {
					add = "true";
					flgChecked = true;
				}
				if (null != objTemp.getStrEdit()) {
					edit = "true";
					flgChecked = true;
				}
				if (null != objTemp.getStrDelete()) {
					delete = "true";
					flgChecked = true;
				}
				if (null != objTemp.getStrView()) {
					view = "true";
					flgChecked = true;
				}
				if (null != objTemp.getStrPrint()) {
					print = "true";
					flgChecked = true;
				}
				if (null != objTemp.getStrGrant()) {
					grant = "true";
					flgChecked = true;
				}
				if (null != objTemp.getStrAuthorise()) {
					authorize = "true";
					flgChecked = true;
				}

				if (flgChecked) {
					objUserDtl.setStrUserCode(userCode);
					objUserDtl.setStrFormName(objTemp.getStrFormName());
					objUserDtl.setStrAdd(add);
					objUserDtl.setStrEdit(edit);
					objUserDtl.setStrDelete(delete);
					objUserDtl.setStrView(view);
					objUserDtl.setStrPrint(print);
					objUserDtl.setStrAuthorise(authorize);
					objUserDtl.setStrGrant(grant);
					objUserDtl.setStrFormType(formType);
					objUserDtl.setIntFormKey(0);
					objUserDtl.setIntFormNo(0);
					objUserDtl.setStrUserCreated(loggedInUserCode);
					objUserDtl.setStrUserModified(loggedInUserCode);
					objUserDtl.setDtCreatedDate(objGlobal.funGetCurrentDate("yyyy-MM-dd"));
					objUserDtl.setDtLastModified(objGlobal.funGetCurrentDate("yyyy-MM-dd"));
					objUserDtl.setStrDesktop("");
					objUserDtl.setStrModule(strModuleNo);
					objUserDtl.setStrUserName("");
					objUserDtl.setStrClientCode(clientCode);

					objSecurityShellService.funAddUpdate(objUserDtl);
				}

				/*
				 * System.out.println("Form Name="+objTempMaster.getStrFormName()
				 * );
				 * System.out.println("Form Desc="+objTempMaster.getStrFormDesc
				 * ()); System.out.println("Add="+objTempMaster.getStrAdd());
				 * System.out.println("Edit="+objTempMaster.getStrEdit());
				 * System.out.println("Print="+objTempMaster.getStrPrint());
				 * System.out.println("View="+objTempMaster.getStrView());
				 */
			}
		}
	}

	private clsUserDtlModel funPrepareMasters(clsTreeMasterModel objBean, String string) {
		clsUserDtlModel model = new clsUserDtlModel();
		return model;
	}

	@RequestMapping(value = "/ARSecurity", method = RequestMethod.POST)
	public ModelAndView funLoadSecurityShellForm(@ModelAttribute("command") @Valid clsSecurityShellBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest req, @RequestParam("userCode") String code) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		String strModuleNo = req.getSession().getAttribute("moduleNo").toString();
		String userCode = objBean.getStrUserCode();
		String userName = objBean.getStrUserName();
		List<clsTreeMasterModel> objModel = objSecurityShellService.funGetFormList(objBean.getStrUserCode(), strModuleNo);
		List<clsTreeMasterModel> objMasters = new ArrayList<clsTreeMasterModel>();
		List<clsTreeMasterModel> objTransactions = new ArrayList<clsTreeMasterModel>();
		List<clsTreeMasterModel> objReports = new ArrayList<clsTreeMasterModel>();
		List<clsTreeMasterModel> objUtilitys = new ArrayList<clsTreeMasterModel>();

		for (Object ob : objModel) {
			Object[] arrOb = (Object[]) ob;
			String type = arrOb[2].toString();
			clsTreeMasterModel objTree = new clsTreeMasterModel();
			switch (type) {
			case "M":

				objTree.setStrFormName(arrOb[0].toString());
				objTree.setStrFormDesc(arrOb[1].toString());
				if (null != arrOb[6]) {
					objTree.setStrAdd(arrOb[6].toString());
				}
				if (null != arrOb[7]) {
					objTree.setStrEdit(arrOb[7].toString());
				}
				if (null != arrOb[9]) {
					objTree.setStrView(arrOb[9].toString());
				}
				if (null != arrOb[10]) {
					objTree.setStrPrint(arrOb[10].toString());
				}

				objMasters.add(objTree);
				break;
			case "L":
				objTree.setStrFormName(arrOb[0].toString());
				objTree.setStrFormDesc(arrOb[1].toString());
				if (null != arrOb[11]) {
					objTree.setStrGrant(arrOb[11].toString());
				}
				objUtilitys.add(objTree);
				break;
			case "T":

				objTree.setStrFormName(arrOb[0].toString());
				objTree.setStrFormDesc(arrOb[1].toString());
				if (null != arrOb[6]) {
					objTree.setStrAdd(arrOb[6].toString());
				}
				if (null != arrOb[7]) {
					objTree.setStrEdit(arrOb[7].toString());
				}
				if (null != arrOb[8]) {
					objTree.setStrDelete(arrOb[8].toString());
				}
				if (null != arrOb[9]) {
					objTree.setStrView(arrOb[9].toString());
				}
				if (null != arrOb[10]) {
					objTree.setStrPrint(arrOb[10].toString());
				}
				if (null != arrOb[12]) {
					objTree.setStrAuthorise(arrOb[12].toString());
				}
				objTransactions.add(objTree);

				break;
			case "R":
				objTree.setStrFormName(arrOb[0].toString());
				objTree.setStrFormDesc(arrOb[1].toString());
				if (null != arrOb[11]) {
					objTree.setStrGrant(arrOb[11].toString());
				}
				objReports.add(objTree);
				break;

			}

		}
		clsSecurityShellBean bean = new clsSecurityShellBean();
		bean.setStrUserCode(userCode);
		bean.setStrUserName(userName);
		bean.setListMasterForms(objMasters);
		bean.setListTransactionForms(objTransactions);
		bean.setListReportForms(objReports);
		bean.setListUtilityForms(objUtilitys);
		model.put("treeList", bean);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmARSecurityShell_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmARSecurityShell");
		} else {
			return new ModelAndView("frmARSecurityShell");
		}

	}

	@RequestMapping(value = "/LikeARsersecurity", method = RequestMethod.POST)
	public ModelAndView funLoadLikeUserSecurityShellForm(@ModelAttribute("command") @Valid clsSecurityShellBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest req, @RequestParam("userCode") String code) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		String strModuleNo = req.getSession().getAttribute("moduleNo").toString();
		String userCode = objBean.getStrLikeUserCode();
		String userName = objBean.getStrLikeUserName();
		List<clsTreeMasterModel> objModel = objSecurityShellService.funGetFormList(objBean.getStrLikeUserCode(), strModuleNo);
		// List<clsTreeMasterModel> objModel =
		// objSecurityShellService.funGetFormList("SUPER");
		// ArrayList list=(ArrayList)
		// objSecurityShellService.funGetForms("SUPER");
		List<clsTreeMasterModel> objMasters = new ArrayList<clsTreeMasterModel>();
		List<clsTreeMasterModel> objTransactions = new ArrayList<clsTreeMasterModel>();
		List<clsTreeMasterModel> objReports = new ArrayList<clsTreeMasterModel>();
		List<clsTreeMasterModel> objUtilitys = new ArrayList<clsTreeMasterModel>();

		for (Object ob : objModel) {
			Object[] arrOb = (Object[]) ob;
			String type = arrOb[2].toString();
			clsTreeMasterModel objTree = new clsTreeMasterModel();
			switch (type) {
			case "M":

				objTree.setStrFormName(arrOb[0].toString());
				objTree.setStrFormDesc(arrOb[1].toString());
				if (null != arrOb[6]) {
					objTree.setStrAdd(arrOb[6].toString());
				}
				if (null != arrOb[7]) {
					objTree.setStrEdit(arrOb[7].toString());
				}
				if (null != arrOb[9]) {
					objTree.setStrView(arrOb[9].toString());
				}
				if (null != arrOb[10]) {
					objTree.setStrPrint(arrOb[10].toString());
				}

				objMasters.add(objTree);
				break;
			case "L":
				objTree.setStrFormName(arrOb[0].toString());
				objTree.setStrFormDesc(arrOb[1].toString());
				if (null != arrOb[11]) {
					objTree.setStrGrant(arrOb[11].toString());
				}
				objUtilitys.add(objTree);
				break;
			case "T":

				objTree.setStrFormName(arrOb[0].toString());
				objTree.setStrFormDesc(arrOb[1].toString());
				if (null != arrOb[6]) {
					objTree.setStrAdd(arrOb[6].toString());
				}
				if (null != arrOb[7]) {
					objTree.setStrEdit(arrOb[7].toString());
				}
				if (null != arrOb[8]) {
					objTree.setStrDelete(arrOb[8].toString());
				}
				if (null != arrOb[9]) {
					objTree.setStrView(arrOb[9].toString());
				}
				if (null != arrOb[10]) {
					objTree.setStrPrint(arrOb[10].toString());
				}
				if (null != arrOb[12]) {
					objTree.setStrAuthorise(arrOb[12].toString());
				}
				objTransactions.add(objTree);

				break;
			case "R":
				objTree.setStrFormName(arrOb[0].toString());
				objTree.setStrFormDesc(arrOb[1].toString());
				if (null != arrOb[11]) {
					objTree.setStrGrant(arrOb[11].toString());
				}
				objReports.add(objTree);
				break;

			}

		}
		clsSecurityShellBean bean = new clsSecurityShellBean();
		bean.setStrLikeUserCode(userCode);
		bean.setStrLikeUserName(userName);
		bean.setListMasterForms(objMasters);
		bean.setListTransactionForms(objTransactions);
		bean.setListReportForms(objReports);
		bean.setListUtilityForms(objUtilitys);
		model.put("treeList", bean);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmARSecurityShell_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmARSecurityShell");
		} else {
			return new ModelAndView("frmARSecurityShell");
		}

	}

}
