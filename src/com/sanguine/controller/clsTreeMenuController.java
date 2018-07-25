package com.sanguine.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsTreeMenuBean;
import com.sanguine.model.clsTreeMasterModel;
import com.sanguine.service.clsTreeMenuService;

@Controller
public class clsTreeMenuController {
	@Autowired
	clsTreeMenuService objclsTreeMenuService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/h", method = RequestMethod.GET)
	public ModelAndView funOpenPDForm() {
		clsTreeMenuBean bean = new clsTreeMenuBean();
		List<clsTreeMasterModel> objModel = objclsTreeMenuService.funGetMenuForm();
		List<clsTreeMasterModel> objMasters = new ArrayList<clsTreeMasterModel>();
		List<clsTreeMasterModel> objTransactions = new ArrayList<clsTreeMasterModel>();
		List<clsTreeMasterModel> objReports = new ArrayList<clsTreeMasterModel>();
		List<clsTreeMasterModel> objUtilitys = new ArrayList<clsTreeMasterModel>();

		ArrayList list = new ArrayList<>();
		list = (ArrayList) objclsTreeMenuService.funGetMenuForm();
		for (Object ob : objModel) {
			Object[] arrOb = (Object[]) ob;
			String type = arrOb[1].toString();
			clsTreeMasterModel objTree = new clsTreeMasterModel();
			switch (type) {
			case "M":
				if (null != arrOb[0]) {
					objTree.setStrFormDesc(arrOb[0].toString());
				}
				if (null != arrOb[2]) {
					objTree.setStrRequestMapping(arrOb[2].toString());
				}
				objMasters.add(objTree);
				break;
			case "L":
				if (null != arrOb[0]) {
					objTree.setStrFormDesc(arrOb[0].toString());
				}
				if (null != arrOb[2]) {
					objTree.setStrRequestMapping(arrOb[2].toString());
				}
				objUtilitys.add(objTree);
				break;
			case "T":
				if (null != arrOb[0]) {
					objTree.setStrFormDesc(arrOb[0].toString());
				}
				if (null != arrOb[2]) {
					objTree.setStrRequestMapping(arrOb[2].toString());
				}
				objTransactions.add(objTree);
				break;
			case "R":
				if (null != arrOb[0]) {
					objTree.setStrFormDesc(arrOb[0].toString());
				}
				if (null != arrOb[2]) {
					objTree.setStrRequestMapping(arrOb[2].toString());
				}
				objReports.add(objTree);
				break;
			}

		}
		bean.setMenu(list);
		bean.setListMasterForms(objMasters);
		bean.setListReportForms(objReports);
		bean.setListTransactionForms(objTransactions);
		bean.setListUtilityForms(objUtilitys);
		return new ModelAndView("frmTreeMenu", "command", bean);
	}
}
