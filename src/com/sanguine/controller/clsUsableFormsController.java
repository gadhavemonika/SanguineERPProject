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

import com.sanguine.bean.clsSecurityShellBean;
import com.sanguine.dao.clsGlobalFunctionsDaoImpl;
import com.sanguine.model.clsTreeMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSecurityShellService;

    @Controller
	public class clsUsableFormsController 
	{
		@Autowired
		private clsGlobalFunctionsService objGlobalFunctionsService;
		private clsGlobalFunctions objGlobal = null;
		@Autowired
		private clsSecurityShellService objSecurityShellService;
		@Autowired
		private clsGlobalFunctionsDaoImpl objTest;

		@RequestMapping(value = "/frmUsableForms", method = RequestMethod.GET)
		public ModelAndView funOpenSecurityShellForm(@ModelAttribute("command") @Valid clsSecurityShellBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) 
		{
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
				return new ModelAndView("frmUsableForms_1");
			} else if ("1".equalsIgnoreCase(urlHits)) {
				return new ModelAndView("frmUsableForms");
			} else {
				return new ModelAndView("frmUsableForms");
			}

		}	

}
