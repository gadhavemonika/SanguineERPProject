package com.sanguine.excise.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.excise.model.clsExciseLicenceMasterModel;
import com.sanguine.excise.model.clsExciseMonthEndModel;
import com.sanguine.excise.service.clsExciseMonthEndService;
import com.sanguine.excise.service.clsLicenceMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsExciseMonthEndController {
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsExciseMonthEndService objMonthEndService;
	@Autowired
	private clsLicenceMasterService objLicenceMasterService;

	clsGlobalFunctions objGlobal;
	@Autowired
	private clsGlobalFunctions objGlobal1;

	@RequestMapping(value = "/frmExciseMonthEnd", method = RequestMethod.GET)
	public ModelAndView funOpenMonthForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseMonthEnd_1", "command", new clsReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseMonthEnd", "command", new clsReportBean());
		} else {
			return null;
		}

	}

	/**
	 * Save Month End form
	 * 
	 * @param objBean
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/saveExciseMonthEnd", method = RequestMethod.POST)
	public String funSaveMonthEnd(@ModelAttribute("command") clsReportBean objBean, HttpServletRequest request, HttpServletResponse resp) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		try {
			objGlobal = new clsGlobalFunctions();
			String propCode = request.getSession().getAttribute("propertyCode").toString();
			String clientCode = request.getSession().getAttribute("clientCode").toString();
			String userCode = request.getSession().getAttribute("usercode").toString();
			String tempLoc[] = objBean.getStrFromLocCode().split(",");
			String date = objGlobal.funGetCurrentDate("yyyy-MM-dd");
			String tempDate[] = date.split("-");
			String strYear = tempDate[0];
			String strMonth = tempDate[1];
			int tempMonth = 0;
			if (strMonth.equals("01")) {
				tempMonth = 12;
				Integer yr = Integer.parseInt(strYear);
				yr = yr - 1;
				strYear = yr.toString();
			} else {
				tempMonth = Integer.parseInt(strMonth) - 1;
			}
			String strMonthEnd = "";
			int length = (int) Math.log10(tempMonth) + 1;
			if (length == 1) {
				strMonthEnd = strYear + "0" + tempMonth;
			} else {
				strMonthEnd = strYear + tempMonth;
			}
			for (int i = 0; i < tempLoc.length; i++) {
				clsExciseMonthEndModel MonthEndModel = new clsExciseMonthEndModel();
				MonthEndModel.setStrLocCode(tempLoc[i].toString());
				MonthEndModel.setStrMonthEnd(strMonthEnd);
				MonthEndModel.setStrUserCreated(userCode);
				MonthEndModel.setStrUserModified(userCode);
				MonthEndModel.setDtDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				MonthEndModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				MonthEndModel.setStrClientCode(clientCode);
				objMonthEndService.funAddMonthEnd(MonthEndModel);

				String hql = "update clsExciseLicenceMasterModel set strMonthEnd='" + strMonthEnd + "'  where strLicenceCode='" + tempLoc[i] + "' and strClientCode='" + clientCode + "'  ";
				objGlobalFunctionsService.funUpdateAllModule(hql, "hql");
			}
			request.getSession().setAttribute("success", true);
			request.getSession().setAttribute("successMessage", "Month End Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ("redirect:/frmExciseMonthEnd.html?saddr=" + urlHits);

	}

	/**
	 * Check Month End Done or Not
	 * 
	 * @param licenceCode
	 * @param date
	 * @param request
	 * @param resp
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/checkExciseMonthEnd", method = RequestMethod.GET)
	public @ResponseBody int funCheckMonthEnd(@RequestParam("licenceCode") String licenceCode, @RequestParam("date") String date, @RequestParam("formName") String formName, HttpServletRequest request, HttpServletResponse resp) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propertyCode = request.getSession().getAttribute("propertyCode").toString();
		// clsPropertySetupModel
		// objSetup=objSetupMasterService.funGetObjectPropertySetup(propertyCode,
		// clientCode);
		// if(objSetup!=null)
		// {
		// if(objSetup.getStrMonthEnd().equalsIgnoreCase("Y") &&
		// objSetup.getStrMonthEnd()!=null)
		// {
		Date monthEnddate;

		clsExciseLicenceMasterModel LicModel = objLicenceMasterService.funGetObject(licenceCode, propertyCode, clientCode);
		String strMonthEnd = LicModel.getStrMonthEnd();
		monthEnddate = new Date(strMonthEnd.substring(4, 6) + "/1/" + strMonthEnd.substring(0, 4));
		// SimpleDateFormat fromUser = new SimpleDateFormat("MM/dd/yyyy");
		if (formName.equalsIgnoreCase("Bill Generation"))
			date = objGlobal1.funGetDate("yyyy-MM-dd", date);

		String temDate[] = date.split("-");
		// String day=temDate[0];
		int tempMonth = 0;
		String year = "";
		String month = temDate[1];
		if (month.equals("01")) {
			tempMonth = 12;
			year = temDate[0];
			Integer yr = Integer.parseInt(year);
			yr = yr - 1;
			year = yr.toString();

		} else {
			tempMonth = Integer.parseInt(month) - 1;
			year = temDate[0];
		}

		Date date1 = new Date(tempMonth + "/1/" + year);
		int comparison = date1.compareTo(monthEnddate);
		if (comparison == -1) {
			comparison = 0;
		}
		return comparison;
		// }
		// else
		// {
		// return 0;
		// }
		// //}
		// else
		// {
		// return 0;
		// }
	}

}
