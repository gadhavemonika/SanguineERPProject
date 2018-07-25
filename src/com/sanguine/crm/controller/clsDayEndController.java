package com.sanguine.crm.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.crm.model.clsCRMDayEndModel;
import com.sanguine.crm.service.clsCRMDayEndService;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.util.clsReportBean;

@Controller("CRMDayEndController")
public class clsDayEndController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	clsLocationMasterService objLocationMasterService;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	clsGlobalFunctions objGlobal;

	@Autowired
	private clsCRMDayEndService objCRMDayEndService;

	/**
	 * Open Month End Form
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/frmCRMDayEnd", method = RequestMethod.GET)
	public ModelAndView funOpenDayForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCRMDayEnd_1", "command", new clsReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCRMDayEnd", "command", new clsReportBean());
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
	@RequestMapping(value = "/saveCRMDayEnd", method = RequestMethod.POST)
	public String funSaveCRMDayEnd(@ModelAttribute("command") clsReportBean objBean, HttpServletRequest request, HttpServletResponse resp) {
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

			for (int i = 0; i < tempLoc.length; i++) {
				clsLocationMasterModel objLocModel = objLocationMasterService.funGetObject(tempLoc[i].toString(), clientCode);
				String dayEnddate = request.getSession().getAttribute("dayEndDate").toString();

				if (dayEnddate.equals("")) {
					clsCRMDayEndModel DayEndModel = new clsCRMDayEndModel();
					DayEndModel.setStrLocCode(tempLoc[i].toString());
					DayEndModel.setStrDayEnd(objGlobal.funGetCurrentDate("yyyy-MM-dd"));
					DayEndModel.setStrUserCreated(userCode);
					DayEndModel.setStrUserModified(userCode);
					DayEndModel.setDtDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					DayEndModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					DayEndModel.setStrClientCode(clientCode);
					objCRMDayEndService.funAddUpdadte(DayEndModel);
				} else {
					// SimpleDateFormat obj=new SimpleDateFormat("yyyy-MM-dd");
					// Date oldDate = obj.parse(dayEnddate);
					// GregorianCalendar cal = new GregorianCalendar();
					// cal.setTime(oldDate);
					// cal.add(Calendar.DATE, 1);
					// String
					// newStartDate=cal.getTime().getYear()+1900+"-"+(cal.getTime().getMonth()+1)+"-"+(cal.getTime().getDate());
					clsCRMDayEndModel DayEndModel = new clsCRMDayEndModel();
					DayEndModel.setStrLocCode(tempLoc[i].toString());
					DayEndModel.setStrDayEnd(dayEnddate);
					DayEndModel.setStrUserCreated(userCode);
					DayEndModel.setStrUserModified(userCode);
					DayEndModel.setDtDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					DayEndModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					DayEndModel.setStrClientCode(clientCode);
					objCRMDayEndService.funAddUpdadte(DayEndModel);
				}

			}
			request.getSession().setAttribute("success", true);
			request.getSession().setAttribute("successMessage", "Day End Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ("redirect:/frmCRMDayEnd.html?saddr=" + urlHits);

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
	/*
	 * @SuppressWarnings("deprecation")
	 * 
	 * @RequestMapping(value = "/checkCRMDayEnd", method = RequestMethod.GET)
	 * public @ResponseBody int funCheckMonthEnd(@RequestParam("locCode") String
	 * locCode,@RequestParam("GRNDate") String GRNDate, HttpServletRequest
	 * request,HttpServletResponse resp) { String
	 * clientCode=request.getSession().getAttribute("clientCode").toString();
	 * String
	 * propertyCode=request.getSession().getAttribute("propertyCode").toString
	 * (); clsPropertySetupModel
	 * objSetup=objSetupMasterService.funGetObjectPropertySetup(propertyCode,
	 * clientCode); if(objSetup!=null) {
	 * if(objSetup.getStrMonthEnd().equalsIgnoreCase("Y") &&
	 * objSetup.getStrMonthEnd()!=null) { Date monthEnddate;
	 * clsLocationMasterModel
	 * LocModel=objLocationMasterService.funGetObject(locCode, clientCode);
	 * String strMonthEnd=LocModel.getStrMonthEnd(); //
	 * if(strMonthEnd.substring(4,5).equals("0")) // { // monthEnddate=new
	 * Date("01"+"/1/"+strMonthEnd.substring(0,4)); // }else // {
	 * monthEnddate=new
	 * Date(strMonthEnd.substring(4,6)+"/1/"+strMonthEnd.substring(0,4)); //}
	 * String temDate[]=GRNDate.split("-"); //String day=temDate[0]; int
	 * tempMonth=0; String year=""; String month=temDate[1];
	 * if(month.equals("01")) { tempMonth = 12; year=temDate[2]; Integer
	 * yr=Integer.parseInt(year); yr=yr-1; year=yr.toString();
	 * 
	 * }else { tempMonth=Integer.parseInt(month)-1; year=temDate[2]; }
	 * 
	 * Date GrnDate=new Date(tempMonth+"/1/"+year); int comparison =
	 * GrnDate.compareTo(monthEnddate); if(comparison==-1) { comparison=0; }
	 * return comparison; } else { return 0; } } else { return 0; } }
	 */

}
