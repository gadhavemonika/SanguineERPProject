package com.sanguine.webbooks.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.base.service.intfBaseService;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webbooks.bean.clsEmployeeMasterBean;
import com.sanguine.webbooks.model.clsEmployeeMasterModel;
import com.sanguine.webbooks.model.clsEmployeeMasterModel_ID;
import com.sanguine.webbooks.model.clsEmployeeOpeningBalModel;

@Controller
public class clsEmployeeMasterController
{

	@Autowired
	private intfBaseService objBaseService;

	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	// Open Employee Master
	@RequestMapping(value = "/frmEmployeeMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request)
	{
		String urlHits = "1";
		try
		{
			urlHits = request.getParameter("saddr").toString();
		}
		catch (NullPointerException e)
		{
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		ArrayList<String> listDrCr = new ArrayList<String>();
		listDrCr.add("Dr");
		listDrCr.add("Cr");

		model.put("listDrCr", listDrCr);

		if (urlHits.equalsIgnoreCase("1"))
		{
			return new ModelAndView("frmEmployeeMaster", "command", new clsEmployeeMasterModel());
		}
		else
		{
			return new ModelAndView("frmEmployeeMaster_1", "command", new clsEmployeeMasterModel());
		}
	}

	// Save or Update EmployeeMaster
	@RequestMapping(value = "/saveEmployeeMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsEmployeeMasterBean objBean, BindingResult result, HttpServletRequest req) throws Exception
	{
		String urlHits = "1";
		try
		{
			urlHits = req.getParameter("saddr").toString();
		}
		catch (NullPointerException e)
		{
			urlHits = "1";
		}
		if (!result.hasErrors())
		{
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			clsEmployeeMasterModel objModel = funPrepareModel(objBean, userCode, clientCode, propCode);

			List<clsEmployeeOpeningBalModel> list = objBean.getListEmployeeOpenongBalModel();

			List<clsEmployeeOpeningBalModel> listOfOpeningBal = new ArrayList<clsEmployeeOpeningBalModel>();

			if (!list.equals(null))
			{
				for (int i = 1; i < list.size(); i++)
				{
					clsEmployeeOpeningBalModel obj = (clsEmployeeOpeningBalModel) list.get(i);
					if (obj.getStrAccountCode()!=null)
					{
						obj.setStrAccountCode(obj.getStrAccountCode());
						obj.setStrAccountName(obj.getStrAccountName());
						obj.setDblOpeningbal(obj.getDblOpeningbal());
						obj.setStrCrDr(obj.getStrCrDr());
						listOfOpeningBal.add(obj);
					}

				}
				objModel.setListEmployeeOpenongBalModel(listOfOpeningBal);
			}

			objBaseService.funSaveForWebBooks(objModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Employee Code : ".concat(objModel.getStrEmployeeCode()));

			return new ModelAndView("redirect:/frmEmployeeMaster.html?saddr=" + urlHits);
		}
		else
		{
			return new ModelAndView("redirect:/frmEmployeeMaster.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsEmployeeMasterModel funPrepareModel(clsEmployeeMasterBean objBean, String userCode, String clientCode, String propCode)
	{
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsEmployeeMasterModel objModel;
		if (objBean.getStrEmployeeCode().trim().length() == 0)
		{
			lastNo = objGlobalFunctionsService.funGetLastNo("tblemployeemaster", "Employee Master", "intID", clientCode);
			String employeeCode = "E" + String.format("%06d", lastNo);
			objModel = new clsEmployeeMasterModel(new clsEmployeeMasterModel_ID(employeeCode, clientCode));
			objModel.setIntID(lastNo);

		}
		else
		{
			objModel = new clsEmployeeMasterModel(new clsEmployeeMasterModel_ID(objBean.getStrEmployeeCode(), clientCode));
		}

		objModel.setStrEmployeeName(objBean.getStrEmployeeName());
		objModel.setStrUserCreated(userCode);
		objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserModified(userCode);
		objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrClientCode(clientCode);
		objModel.setStrPropertyCode(propCode);

		return objModel;

	}

	// Load Master Data On Form
	@RequestMapping(value = "/loadEmployeeMasterData", method = RequestMethod.GET)
	public @ResponseBody clsEmployeeMasterModel funAssignFields(@RequestParam("employeeCode") String employeeCode, HttpServletRequest req)
	{

		clsEmployeeMasterModel objEmployeeMasterModel = null;

		try
		{

			String clientCode = req.getSession().getAttribute("clientCode").toString();

			StringBuilder sbSql = new StringBuilder("from clsEmployeeMasterModel where strEmployeeCode='" + employeeCode + "' and strClientCode='" + clientCode + "'");

			List list = null;
			list = objBaseService.funGetListForWebBooks(sbSql, "hql");

			if (null == list || list.size() == 0)
			{
				objEmployeeMasterModel = new clsEmployeeMasterModel();
				objEmployeeMasterModel.setStrEmployeeCode("Invalid Code");
			}
			else
			{
				objEmployeeMasterModel = (clsEmployeeMasterModel) list.get(0);
			}

			clsEmployeeOpeningBalModel obj = new clsEmployeeOpeningBalModel();

			if (objEmployeeMasterModel != null)
			{
				String sqlQuery = "select a.strAccountCode,a.strAccountName,a.dblOpeningbal,a.strCrDr " + "FROM tblemployeeopeningbalance a " + "where a.strEmployeeCode = '" + employeeCode + "'  and a.strClientCode='" + clientCode + "'";

				List listProperty = objGlobalFunctionsService.funGetListModuleWise(sqlQuery, "sql");

				if (listProperty != null && listProperty.size() > 0)
				{
					objEmployeeMasterModel.setListEmployeeOpenongBalModel(listProperty);
				}

			}

			if (null == objEmployeeMasterModel)
			{
				objEmployeeMasterModel = new clsEmployeeMasterModel();
				objEmployeeMasterModel.setStrEmployeeCode("Invalid Code");
			}

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return objEmployeeMasterModel;

	}

	// // Load Master Data On Form
	// @RequestMapping(value = "/loadEmployeeMasterData", method =
	// RequestMethod.GET)
	// public @ResponseBody clsEmployeeMasterModel
	// funAssignFields(@RequestParam("employeeCode") String employeeCode,
	// HttpServletRequest req) {
	// String clientCode = req.getSession().getAttribute("clientCode").toString();
	// clsEmployeeMasterModel objModel = (employeeCode, clientCode);
	// if (null == objModel) {
	// objModel = new clsEmployeeMasterModel();
	// objModel.setStrEmployeeCode("Invalid Code");
	// }
	//
	// return objModel;
	// }

}
