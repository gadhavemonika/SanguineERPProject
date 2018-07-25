package com.sanguine.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsBudgetMasterBean;
import com.sanguine.bean.clsBudgetMasterMonthBean;
import com.sanguine.model.clsBudgetMasterDtlModel;
import com.sanguine.model.clsBudgetMasterHdModel;
import com.sanguine.model.clsBudgetMasterHdModel_ID;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsGroupMasterModel_ID;
import com.sanguine.service.clsBudgetMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsGroupMasterService;

@Controller
public class clsBudgetMasterController {

	@Autowired
	private clsGlobalFunctionsService objGlobalService;

	@Autowired
	private clsGroupMasterService objGrpMasterService;

	@Autowired
	private clsBudgetMasterService objBudgetMasterService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@RequestMapping(value = "/frmBudgetMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		ModelAndView objModelView = null;
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		Map<String, String> mapProperties = objGlobalService.funGetPropertyList(clientCode);

		if (mapProperties.isEmpty()) {
			mapProperties.put("", "");
		}
		model.put("properties", mapProperties);
		String propertyCode = request.getSession().getAttribute("propertyCode").toString();

		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			objModelView = new ModelAndView("frmBudgetMaster_1", "command", new clsBudgetMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			objModelView = new ModelAndView("frmBudgetMaster", "command", new clsBudgetMasterBean());
		}
		objModelView.addObject("LoggedInProp", propertyCode);
		return objModelView;
	}

	/**
	 * Load Group Data
	 * 
	 * @param code
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/loadAllGroup", method = RequestMethod.GET)
	public @ResponseBody List funGroupFroGroup(@RequestParam("propCode") String propCode, @RequestParam("year") String year, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		Map<String, String> mpgroup = objGrpMasterService.funGetGroups(clientCode);
		List resList = new ArrayList();
		for (Map.Entry<String, String> entry : mpgroup.entrySet()) {
			List list1 = objBudgetMasterService.funGetMasterData(propCode, clientCode, year, entry.getKey());

			List list = new ArrayList();
			if (!(list1.size() > 0)) {

				list.add(entry.getValue());
				list.add("0");
				list.add("0");
				list.add("0");
				list.add("0");
				list.add("0");
				list.add("0");
				list.add("0");
				list.add("0");
				list.add("0");
				list.add("0");
				list.add("0");
				list.add("0");
				list.add(entry.getKey());
				list.add("");
			} else {

				Object[] obj = (Object[]) list1.get(0);
				list.add(entry.getValue());
				list.add(obj[2].toString());
				list.add(obj[3].toString());
				list.add(obj[4].toString());
				list.add(obj[5].toString());
				list.add(obj[6].toString());
				list.add(obj[7].toString());
				list.add(obj[8].toString());
				list.add(obj[9].toString());
				list.add(obj[10].toString());
				list.add(obj[11].toString());
				list.add(obj[12].toString());
				list.add(obj[13].toString());
				list.add(entry.getKey());
				list.add(obj[0].toString());

			}
			resList.add(list);
		}

		return resList;
	}

	// Save or Update
	@RequestMapping(value = "/saveBudgetMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsBudgetMasterBean objBean, BindingResult result, HttpServletRequest req, HttpServletResponse resp) {

		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String startDate = req.getSession().getAttribute("startDate").toString();
			String[] arrMonth = startDate.split("/");
			clsBudgetMasterHdModel objBudgetMasterModel = funPrepareModel(objBean, clientCode, userCode, arrMonth[1]);

			List<clsBudgetMasterMonthBean> listMonthBean = objBean.getListBudgetMonth();
			List<clsBudgetMasterDtlModel> lisModelDtl = new ArrayList<clsBudgetMasterDtlModel>();

			if (listMonthBean.size() > 0) {

				objBudgetMasterService.funDeleteBudgetDtl(objBudgetMasterModel.getStrBudgetCode(), objBudgetMasterModel.getStrClientCode());

				for (clsBudgetMasterMonthBean objMonthBean : listMonthBean) {
					clsBudgetMasterDtlModel objBudgetDtlModel = new clsBudgetMasterDtlModel();
					// if(!(objMonthBean.getStrMonth1()==null))
					objBudgetDtlModel.setStrMonth1(objMonthBean.getStrMonth1());
					objBudgetDtlModel.setStrMonth2(objMonthBean.getStrMonth2());
					objBudgetDtlModel.setStrMonth3(objMonthBean.getStrMonth3());
					objBudgetDtlModel.setStrMonth4(objMonthBean.getStrMonth4());
					objBudgetDtlModel.setStrMonth5(objMonthBean.getStrMonth5());
					objBudgetDtlModel.setStrMonth6(objMonthBean.getStrMonth6());
					objBudgetDtlModel.setStrMonth7(objMonthBean.getStrMonth7());
					objBudgetDtlModel.setStrMonth8(objMonthBean.getStrMonth8());
					objBudgetDtlModel.setStrMonth9(objMonthBean.getStrMonth9());
					objBudgetDtlModel.setStrMonth10(objMonthBean.getStrMonth10());
					objBudgetDtlModel.setStrMonth11(objMonthBean.getStrMonth11());
					objBudgetDtlModel.setStrMonth12(objMonthBean.getStrMonth12());
					objBudgetDtlModel.setStrGroupCode(objMonthBean.getStrGroupCode());

					lisModelDtl.add(objBudgetDtlModel);
				}
			}

			objBudgetMasterModel.setListBudgetDtlModel(lisModelDtl);
			objBudgetMasterService.funAddUpdate(objBudgetMasterModel);

			req.getSession().setAttribute("success", true);
			return new ModelAndView("redirect:/frmBudgetMaster.html");
		} else {
			return new ModelAndView("redirect:/frmBudgetMaster.html");
		}
	}

	private clsBudgetMasterHdModel funPrepareModel(clsBudgetMasterBean objBean, String clientCode, String userCode, String strtMonth) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsBudgetMasterHdModel objBudgetMasterHdModel;

		if (objBean.getStrBudgetCode().trim().length() == 0) {
			lastNo = objGlobalService.funGetLastNo("tblbudgetmasterhd", "BudgetMaster", "intBId", clientCode);
			String budgetCode = "BM" + String.format("%06d", lastNo);
			objBudgetMasterHdModel = new clsBudgetMasterHdModel(new clsBudgetMasterHdModel_ID(budgetCode, clientCode));
			objBudgetMasterHdModel.setStrBudgetCode(budgetCode);
			objBudgetMasterHdModel.setIntBId(lastNo);
			objBudgetMasterHdModel.setStrClientCode(clientCode);
			objBudgetMasterHdModel.setStrUserCreated(userCode);
			objBudgetMasterHdModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		}

		else {
			clsBudgetMasterHdModel objBudget = objBudgetMasterService.funGetBudget(objBean.getStrBudgetCode(), clientCode);
			if (null == objBudget) {
				lastNo = objGlobalService.funGetLastNo("tblbudgetmasterhd", "BudgetMaster", "intBId", clientCode);
				String budgetCode = "BM" + String.format("%06d", lastNo);
				objBudgetMasterHdModel = new clsBudgetMasterHdModel(new clsBudgetMasterHdModel_ID(budgetCode, clientCode));
				objBudgetMasterHdModel.setStrBudgetCode(budgetCode);
				objBudgetMasterHdModel.setIntBId(lastNo);
				objBudgetMasterHdModel.setStrClientCode(clientCode);
				objBudgetMasterHdModel.setStrUserCreated(userCode);
				objBudgetMasterHdModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			}

			else {
				objBudgetMasterHdModel = new clsBudgetMasterHdModel(new clsBudgetMasterHdModel_ID(objBean.getStrBudgetCode(), clientCode));
				objBudgetMasterHdModel.setStrUserCreated(userCode);
				objBudgetMasterHdModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			}
		}

		objBudgetMasterHdModel.setStrClientCode(clientCode);
		objBudgetMasterHdModel.setStrFinYear(objBean.getStrFinYear());
		objBudgetMasterHdModel.setStrPropertyCode(objBean.getStrPropertyCode());
		objBudgetMasterHdModel.setStrUserEdited(userCode);
		objBudgetMasterHdModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objBudgetMasterHdModel.setStrStartMonth(strtMonth);
		return objBudgetMasterHdModel;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadBudgetMasterHeader", method = RequestMethod.GET)
	public @ResponseBody List funLoadFlashData(@RequestParam("year") String year, HttpServletRequest request) {

		List listFlash = new ArrayList();

		String[] strYear = year.split("-");
		String[] monthName = { "Jan", "Feb", "March", "April", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec" };
		;

		Calendar cal = Calendar.getInstance();

		String startDate = request.getSession().getAttribute("startDate").toString();
		String[] arrMonth = startDate.split("/");

		int fincialMonth = Integer.parseInt(arrMonth[1]);

		for (int i = fincialMonth - 1; i < monthName.length; i++) {
			String listMonth = monthName[i];
			listFlash.add(listMonth + "-" + strYear[0]);
		}
		for (int j = 0; j < fincialMonth - 1; j++) {
			String listMonth = monthName[j];
			listFlash.add(listMonth + "-" + strYear[1]);
		}
		return listFlash;
	}

}
