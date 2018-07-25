package com.sanguine.excise.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsDeleteModuleListBean;
import com.sanguine.excise.service.clsExciseStructureUpdateService;
import com.sanguine.model.clsTreeMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSecurityShellService;

@Controller
public class clsExciseStructureUpdate {

	@Autowired
	private clsExciseStructureUpdateService objExciseStructureUpdateService;

	@Autowired
	private clsSecurityShellService objSecurityShellService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@RequestMapping(value = "/frmExciseStructureUpdate", method = RequestMethod.GET)
	public ModelAndView funOpenStructureUpdateForm(HttpServletRequest req) {
		return new ModelAndView("frmExciseStructureUpdate");

	}

	@RequestMapping(value = "/ExciseUpdateStructure", method = RequestMethod.GET)
	public @ResponseBody String funExciseUpdateStructure(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		objExciseStructureUpdateService.funExciseUpdateStructure(clientCode);
		return "Structure Update Successfully";
	}

	/**
	 * Open Delete Module form
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/frmExciseDeleteModuleList", method = RequestMethod.GET)
	public ModelAndView funOpenListForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		/**
		 * Set header on form
		 */
		model.put("headerName", "Transaction List");

		List<String> listPropertyName = new ArrayList<>();

		String sqlPropertyName = "select strPropertyName from dbwebmms.tblpropertymaster where strClientCode='" + clientCode + "' ";
		listPropertyName = objGlobalFunctionsService.funGetDataList(sqlPropertyName, "sql");
		model.put("listPropertyName", listPropertyName);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseDeleteModuleList", "command", new clsDeleteModuleListBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseDeleteModuleList", "command", new clsDeleteModuleListBean());
		} else {
			return null;
		}

	}

	/**
	 * Load Data on Form
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	@RequestMapping(value = "/frmExciseFillActionList", method = RequestMethod.GET)
	public @ResponseBody List funListForm(Map<String, Object> model, HttpServletRequest request) {

		String strModuleNo = request.getSession().getAttribute("moduleNo").toString();
		List<clsTreeMasterModel> objModel = objSecurityShellService.funGetFormList(strModuleNo);

		List<String> objMasters = new ArrayList<String>();
		String strType = request.getParameter("strHeadingType").toString();
		List list = new ArrayList();
		List<clsTreeMasterModel> ListTrans = new ArrayList<clsTreeMasterModel>();
		List<clsTreeMasterModel> ListMaster = new ArrayList<clsTreeMasterModel>();
		List<clsTreeMasterModel> objReports = new ArrayList<clsTreeMasterModel>();
		List<clsTreeMasterModel> objUtilitys = new ArrayList<clsTreeMasterModel>();

		for (Object ob : objModel) {
			List<String> objTransactions = new ArrayList<String>();
			Object[] arrOb = (Object[]) ob;
			String type = arrOb[2].toString();
			clsTreeMasterModel objTree = new clsTreeMasterModel();
			switch (type) {
			// Master
			case "M":

				objTree.setStrFormName(arrOb[0].toString());
				objTree.setStrFormDesc(arrOb[1].toString());
				objTree.setStrDelete("false");
				ListMaster.add(objTree);
				break;
			// Tools
			case "L":
				objTree.setStrFormName(arrOb[0].toString());
				objTree.setStrFormDesc(arrOb[1].toString());
				objTree.setStrDelete("false");
				objUtilitys.add(objTree);
				break;
			// Transaction
			case "T":

				objTree.setStrFormName(arrOb[0].toString());
				objTree.setStrFormDesc(arrOb[1].toString());
				objTree.setStrDelete("false");
				ListTrans.add(objTree);
				break;
			// Report
			case "R":
				objTree.setStrFormName(arrOb[0].toString());
				objTree.setStrFormDesc(arrOb[1].toString());
				objTree.setStrDelete("false");
				objReports.add(objTree);
				break;

			}

		}
		if (strType.equalsIgnoreCase("Transaction")) {
			list = ListTrans;
		} else if (strType.equalsIgnoreCase("Master")) {
			list = ListMaster;
		}
		// Return List
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadExcisePropertyName", method = RequestMethod.GET)
	public @ResponseBody List funLoadPropertyMaster(@RequestParam(value = "propName") String propName, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List<String> listPropertyName = new ArrayList<>();
		String sqlPropertyName = "select strPropertyName from dbwebmms.tblpropertymaster where strClientCode='" + clientCode + "' ";
		listPropertyName = objGlobalFunctionsService.funGetDataList(sqlPropertyName, "sql");
		return listPropertyName;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadExciseLocName", method = RequestMethod.GET)
	public @ResponseBody List funLoadLoctionMaster(@RequestParam(value = "propName") String propName, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List<String> listLocName = new ArrayList<>();
		String sqlLocName = "select a.strLocName from dbwebmms.tbllocationmaster a ,dbwebmms.tblpropertymaster b " + "where a.strPropertyCode=b.strPropertyCode and b.strPropertyName='" + propName + "' and a.strClientCode='" + clientCode + "' ";
		listLocName = objGlobalFunctionsService.funGetDataList(sqlLocName, "sql");
		return listLocName;
	}

	/**
	 * Update Structure in Data base
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/ExciseupdateStructure", method = RequestMethod.GET)
	public @ResponseBody String funUpdateStructure(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		objExciseStructureUpdateService.funExciseUpdateStructure(clientCode);
		return "Structure Update Successfully";
	}

	/**
	 * Clear Transaction
	 * 
	 * @param frmName
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/ExciseClearTransaction", method = RequestMethod.GET)
	public @ResponseBody String funExciseClearTransaction(@RequestParam(value = "frmName") String frmName, @RequestParam(value = "propName") String propName, @RequestParam(value = "locName") String locName, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String str[] = frmName.split(",");

		objExciseStructureUpdateService.funExciseClearTransaction(clientCode, str);
		return "Transaction Clear Successfully";
	}

	/**
	 * Clear Master
	 * 
	 * @param frmName
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/ExciseClearMaster", method = RequestMethod.GET)
	public @ResponseBody String funExciseClearMaster(@RequestParam(value = "frmName") String frmName, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String str[] = frmName.split(",");
		objExciseStructureUpdateService.funExciseClearMaster(clientCode, str);
		return "Master Clear Successfully";
	}

}
