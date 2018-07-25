package com.sanguine.controller;

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

import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsMonthEndModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsMonthEndService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsMonthEndController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsMonthEndService objMonthEndService;

	@Autowired
	clsLocationMasterService objLocationMasterService;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	clsGlobalFunctions objGlobal;

	/**
	 * Open Month End Form
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/frmMonthEnd", method = RequestMethod.GET)
	public ModelAndView funOpenMonthForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMonthEnd_1", "command", new clsReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMonthEnd", "command", new clsReportBean());
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
	@RequestMapping(value = "/saveMonthEnd", method = RequestMethod.POST)
	public String funSaveMonthEnd(@ModelAttribute("command") clsReportBean objBean, HttpServletRequest request, HttpServletResponse resp) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		try {
			objGlobal = new clsGlobalFunctions();
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
				clsMonthEndModel MonthEndModel = new clsMonthEndModel();
				MonthEndModel.setStrLocCode(tempLoc[i].toString());
				MonthEndModel.setStrMonthEnd(strMonthEnd);
				MonthEndModel.setStrUserCreated(userCode);
				MonthEndModel.setStrUserModified(userCode);
				MonthEndModel.setDtDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				MonthEndModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				MonthEndModel.setStrClientCode(clientCode);
				objMonthEndService.funAddMonthEnd(MonthEndModel);

				String hql = "update clsLocationMasterModel set strMonthEnd='" + strMonthEnd + "'  where strLocCode='" + tempLoc[i] + "' and strClientCode='" + clientCode + "'";
				objGlobalFunctionsService.funUpdate(hql, "hql");
			}
			request.getSession().setAttribute("success", true);
			request.getSession().setAttribute("successMessage", "Month End Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ("redirect:/frmMonthEnd.html?saddr=" + urlHits);

	}

	/**
	 * Check Month End Done or Not
	 * 
	 * @param locCode
	 * @param GRNDate
	 * @param request
	 * @param resp
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/checkMonthEnd", method = RequestMethod.GET)
	public @ResponseBody int funCheckMonthEnd(@RequestParam("locCode") String locCode, @RequestParam("GRNDate") String GRNDate, HttpServletRequest request, HttpServletResponse resp) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propertyCode = request.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		if (objSetup != null) {
			if (objSetup.getStrMonthEnd().equalsIgnoreCase("Y") && objSetup.getStrMonthEnd() != null) {
				Date monthEnddate;
				clsLocationMasterModel LocModel = objLocationMasterService.funGetObject(locCode, clientCode);
				String strMonthEnd = LocModel.getStrMonthEnd();
				// if(strMonthEnd.substring(4,5).equals("0"))
				// {
				// monthEnddate=new Date("01"+"/1/"+strMonthEnd.substring(0,4));
				// }else
				// {
				monthEnddate = new Date(strMonthEnd.substring(4, 6) + "/1/" + strMonthEnd.substring(0, 4));
				// }
				String temDate[] = GRNDate.split("-");
				// String day=temDate[0];
				int tempMonth = 0;
				String year = "";
				String month = temDate[1];
				if (month.equals("01")) {
					tempMonth = 12;
					year = temDate[2];
					Integer yr = Integer.parseInt(year);
					yr = yr - 1;
					year = yr.toString();

				} else {
					tempMonth = Integer.parseInt(month) - 1;
					year = temDate[2];
				}

				Date GrnDate = new Date(tempMonth + "/1/" + year);
				int comparison = GrnDate.compareTo(monthEnddate);
				if (comparison == -1) {
					comparison = 0;
				}
				return comparison;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

}
