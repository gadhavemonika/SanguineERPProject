package com.sanguine.webbooks.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.SQLError;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webbooks.bean.clsChargeMasterBean;
import com.sanguine.webbooks.model.clsChargeMasterModel;
import com.sanguine.webbooks.model.clsChargeMasterModel_ID;
import com.sanguine.webbooks.model.clsWebBooksAccountMasterModel;
import com.sanguine.webbooks.service.clsChargeMasterService;

@Controller
public class clsChargeMasterController
{

	@Autowired
	private clsChargeMasterService objChargeMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open ChargeMaster
	@RequestMapping(value = "/frmChargeMaster", method = RequestMethod.GET)
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

		List listVMemberDebtorDtlColumnNames = objGlobalFunctionsService.funGetDataList("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = 'vwdebtormemberdtl'", "sql");

		ArrayList<String> listDimension = new ArrayList<String>();
		listDimension.add("NA");

		ArrayList<String> listValue1 = new ArrayList<String>();
		listValue1.add("NA");

		ArrayList<String> listValue2 = new ArrayList<String>();
		listValue2.add("NA");

		ArrayList<String> listType = new ArrayList<String>();
		listType.add("Amount");
		listType.add("Percentage");
		listType.add("Profile Amount");

		ArrayList<String> listRs2 = new ArrayList<String>();
		listRs2.add("CR");
		listRs2.add("DR");

		ArrayList<String> listFrequency = new ArrayList<String>();
		listFrequency.add("Monthly");
		listFrequency.add("Quaterly");
		listFrequency.add("Half Yearly");
		listFrequency.add("Yearly");

		ArrayList<String> listAllowEditing = new ArrayList<String>();
		listAllowEditing.add("Yes");
		listAllowEditing.add("No");

		ArrayList<String> listTAXIndicator = new ArrayList<String>();
		listTAXIndicator.add("");
		listTAXIndicator.add("A");
		listTAXIndicator.add("B");

		ArrayList<String> listActive = new ArrayList<String>();
		listActive.add("Yes");
		listActive.add("No");

		ArrayList<String> listOpenCharge = new ArrayList<String>();
		listOpenCharge.add("Yes");
		listOpenCharge.add("No");

		ArrayList<String> listCriteriyaType = new ArrayList<String>();
		listCriteriyaType.add("Parameter Based Criteria");
		listCriteriyaType.add("Formula Based Criteria");

		Map<String, String> mapCriteria = new HashMap();
		mapCriteria.put("", "");
		mapCriteria.put("Outstanding", "Outstanding");

		Map<String, String> mapCondition = new HashMap();
		mapCondition.put("=", "=");
		mapCondition.put("<", "<");
		mapCondition.put("<=", "<=");
		mapCondition.put(">", ">");
		mapCondition.put(">=", ">=");

		model.put("mapCriteria", mapCriteria);
		model.put("mapCondition", mapCondition);

		model.put("urlHits", urlHits);

		model.put("listVMemberDebtorDtlColumnNames", listVMemberDebtorDtlColumnNames);
		model.put("listDimension", listDimension);
		model.put("listValue1", listValue1);
		model.put("listValue2", listValue2);
		model.put("listType", listType);
		model.put("listRs2", listRs2);
		model.put("listFrequency", listFrequency);
		model.put("listAllowEditing", listAllowEditing);
		model.put("listTAXIndicator", listTAXIndicator);
		model.put("listActive", listActive);
		model.put("listOpenCharge", listOpenCharge);
		model.put("listCriteriyaType", listCriteriyaType);

		if (urlHits.equalsIgnoreCase("1"))
		{
			return new ModelAndView("frmChargeMaster", "command", new clsChargeMasterModel());
		}
		else
		{
			return new ModelAndView("frmChargeMaster_1", "command", new clsChargeMasterModel());
		}
	}

	// Load Master Data On Form
	@RequestMapping(value = "/loadChargeMasterData", method = RequestMethod.GET)
	public @ResponseBody clsChargeMasterModel funAssignFields(@RequestParam("chargeCode") String chargeCode, HttpServletRequest req)
	{
		clsChargeMasterModel objModel = null;
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List listModel = objChargeMasterService.funGetChargeMaster(chargeCode, clientCode);
		if (null == listModel)
		{
			objModel = new clsChargeMasterModel();
			objModel.setStrChargeCode("Invalid Code");
		}
		else
		{
			Object objects[] = (Object[]) listModel.get(0);
			objModel = (clsChargeMasterModel) objects[0];
			clsWebBooksAccountMasterModel objAccountMasterModel = (clsWebBooksAccountMasterModel) objects[1];

			objModel.setStrAccountName(objAccountMasterModel.getStrAccountName());
		}

		return objModel;
	}

	// Save or Update ChargeMaster
	@RequestMapping(value = "/saveChargeMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsChargeMasterBean objBean, BindingResult result, HttpServletRequest req)
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

			clsChargeMasterModel objModel = funPrepareModel(objBean, userCode, clientCode, propCode);
			objChargeMasterService.funAddUpdateChargeMaster(objModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Charge Code : ".concat(objModel.getStrChargeCode()));

			return new ModelAndView("redirect:/frmChargeMaster.html?saddr=" + urlHits);
		}
		else
		{
			return new ModelAndView("redirect:/frmChargeMaster.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsChargeMasterModel funPrepareModel(clsChargeMasterBean objBean, String userCode, String clientCode, String propertyCode)
	{
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsChargeMasterModel objModel;
		if (objBean.getStrChargeCode().trim().length() == 0)
		{
			lastNo = objGlobalFunctionsService.funGetLastNo("tblchargemaster", "ChargeMaster", "intGId", clientCode);
			String chargeCode = String.format("%06d", lastNo);
			objModel = new clsChargeMasterModel(new clsChargeMasterModel_ID(chargeCode, clientCode));
			objModel.setIntGid(lastNo);

		}
		else
		{
			objModel = new clsChargeMasterModel(new clsChargeMasterModel_ID(objBean.getStrChargeCode(), clientCode));
		}
		objModel.setStrChargeName(objBean.getStrChargeName().toUpperCase());
		objModel.setStrAcctCode(objBean.getStrAcctCode());
		objModel.setStrDeptCode(objGlobal.funIfNull(objBean.getStrDeptCode(), "NA", objBean.getStrDeptCode()));
		objModel.setStrRemark(objBean.getStrRemark());
		objModel.setStrStopSupply(objGlobal.funIfNull(objBean.getStrStopSupply(), "NA", objBean.getStrStopSupply()));
		objModel.setStrOpenCharge(objBean.getStrOpenCharge());
		objModel.setStrOutstandInvoise(objGlobal.funIfNull(objBean.getStrOutstandInvoise(), "NA", objBean.getStrOutstandInvoise()));
		objModel.setStrType(objBean.getStrType());
		objModel.setDblAmt(objBean.getDblAmt());
		objModel.setStrCrDr(objBean.getStrCrDr());
		objModel.setStrFreq(objBean.getStrFreq());
		objModel.setStrCharge(objGlobal.funIfNull(objBean.getStrCharge(), "NA", objBean.getStrCharge()));
		objModel.setIntRecAffected(Long.parseLong(objGlobal.funIfNull(String.valueOf(objBean.getIntRecAffected()), "0", String.valueOf(objBean.getIntRecAffected()))));
		objModel.setStrSql(objGlobal.funIfNull(objBean.getStrSql(), "NA", objBean.getStrSql()));
		objModel.setStrCriteriaType(objBean.getStrCriteriaType());
		objModel.setStrAllowEditing(objBean.getStrAllowEditing());
		objModel.setStrTaxIndicator(objBean.getStrTaxIndicator());
		objModel.setStrDimensionCode(objGlobal.funIfNull(objBean.getStrDimensionCode(), "NA", objBean.getStrDimensionCode()));
		objModel.setStrDimension(objBean.getStrDimension());
		objModel.setStrDimensionValue(objBean.getStrDimensionValue());
		objModel.setStrDimensionValue2(objBean.getStrDimensionValue2());
		objModel.setStrActive(objBean.getStrActive());

		objModel.setStrClientCode(clientCode);
		objModel.setStrPropertyCode(propertyCode);
		objModel.setStrUserCreated(userCode);
		objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserModified(userCode);
		objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		objModel.setStrCriteria(objBean.getStrCriteria());
		objModel.setStrCondition(objBean.getStrCondition());
		objModel.setDblConditionValue(objBean.getDblConditionValue());

		return objModel;
	}

	/* To Check SQL Query Syntax */
	@SuppressWarnings(
	{ "finally", "unchecked" })
	@RequestMapping(value = "/checkSQLQuerySyntax", method = RequestMethod.GET)
	public @ResponseBody String funCheckSQLQuerySyntax(@RequestParam("sqlQuery") String sqlQuery, HttpServletRequest request)
	{

		String response = "{\"result\":\"true\"}";
		try
		{
			List listData = objChargeMasterService.funGetDebtoMemberList(sqlQuery);
		}
		catch (Exception e)
		{
			response = "{\"result\":\"" + e.getCause().getMessage() + "\"}";
			// e.printStackTrace();
		}
		finally
		{
			return response;
		}
	}
}
