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
import com.sanguine.webbooks.bean.clsWebBooksAccountMasterBean;
import com.sanguine.webbooks.model.clsACGroupMasterModel;
import com.sanguine.webbooks.model.clsEmployeeMasterModel;
import com.sanguine.webbooks.model.clsWebBooksAccountMasterModel;
import com.sanguine.webbooks.model.clsWebBooksAccountMasterModel_ID;
import com.sanguine.webbooks.service.clsWebBooksAccountMasterService;

@Controller
public class clsWebBooksAccountMasterController {

	@Autowired
	private clsWebBooksAccountMasterService objWebBooksAccountMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	
	@Autowired
	private clsGlobalFunctions objGlobal;


	@Autowired
	private intfBaseService objBaseService;
	
	// Open WebBooksAccountMaster
	@RequestMapping(value = "/frmWebBooksAccountMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		ArrayList<String> listAccountType = new ArrayList<String>();
		listAccountType.add("GL Code");
		listAccountType.add("Cash");
		listAccountType.add("Bank");

		ArrayList<String> listOperational = new ArrayList<String>();
		listOperational.add("Yes");
		listOperational.add("No");

		ArrayList<String> listDebtor = new ArrayList<String>();
		listDebtor.add("Yes");
		listDebtor.add("No");

		ArrayList<String> listCreditor = new ArrayList<String>();
		listCreditor.add("No");
		listCreditor.add("Yes");

		ArrayList<String> listOpeningBalance = new ArrayList<String>();
		listOpeningBalance.add("DR");
		listOpeningBalance.add("CR");
		
		ArrayList<String> listEmployee = new ArrayList<String>();
		listEmployee.add("No");
		listEmployee.add("Yes");
		

		model.put("listAccountType", listAccountType);
		model.put("listOperational", listOperational);
		model.put("listDebtor", listDebtor);
		model.put("listCreditor", listCreditor);
		model.put("listOpeningBalance", listOpeningBalance);
		model.put("listEmployee", listEmployee);

		if (urlHits.equalsIgnoreCase("1")) {
			return new ModelAndView("frmWebBooksAccountMaster", "command", new clsWebBooksAccountMasterModel());
		} else {
			return new ModelAndView("frmWebBooksAccountMaster_1", "command", new clsWebBooksAccountMasterModel());
		}
	}

	// Load Master Data On Form
	@RequestMapping(value = "/loadAccountMasterData", method = RequestMethod.GET)
	public @ResponseBody clsWebBooksAccountMasterModel funAssignFields(@RequestParam("accountCode") String accountCode, HttpServletRequest req) {
		clsWebBooksAccountMasterModel objModel = null;

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List listModel = objWebBooksAccountMasterService.funGetWebBooksAccountMaster(accountCode, clientCode);
		if (null == listModel) {
			objModel = new clsWebBooksAccountMasterModel();
			objModel.setStrAccountCode("Invalid Code");
		} else {
			Object objects[] = (Object[]) listModel.get(0);

			objModel = (clsWebBooksAccountMasterModel) objects[0];
			clsACGroupMasterModel objGroupModel = (clsACGroupMasterModel) objects[1];
			objModel.setStrGroupName(objGroupModel.getStrGroupName());
			
			objModel = (clsWebBooksAccountMasterModel) objects[0];
			
		
			objModel.setStrEmployeeCode(objModel.getStrEmployeeCode());
			StringBuilder sbSql=new StringBuilder("from clsEmployeeMasterModel where strEmployeeCode='"+objModel.getStrEmployeeCode()+"' and strClientCode='"+clientCode+"'");
			clsEmployeeMasterModel objEmployeeMasterModel = null;
			List list=null;
			try
			{
				list=objBaseService.funGetListForWebBooks(sbSql, "hql");
						
				if(null==list || list.size()==0)
				{
					objEmployeeMasterModel = new clsEmployeeMasterModel();
					objEmployeeMasterModel.setStrEmployeeCode("Invalid Code");
				}
				else
				{
					objEmployeeMasterModel=(clsEmployeeMasterModel)list.get(0);
				}
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
			objModel.setStrEmployeeName(objEmployeeMasterModel.getStrEmployeeName());
		}
		

		return objModel;
	}

	// Get Account Code And Name
	@RequestMapping(value = "/loadAccontCodeAndName", method = RequestMethod.GET)
	public @ResponseBody clsWebBooksAccountMasterModel funGetAccountCodeAndName(@RequestParam("accountCode") String accountCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebBooksAccountMasterModel objModel = objWebBooksAccountMasterService.funGetAccountCodeAndName(accountCode, clientCode);
		if (null == objModel) {
			objModel = new clsWebBooksAccountMasterModel();
			objModel.setStrAccountCode("Invalid Code");
		}

		return objModel;
	}

	// Save or Update WebBooksAccountMaster
	@RequestMapping(value = "/saveWebBooksAccountMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsWebBooksAccountMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();

			clsWebBooksAccountMasterModel objModel = funPrepareModel(objBean, userCode, clientCode, propCode);
			objWebBooksAccountMasterService.funAddUpdateWebBooksAccountMaster(objModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Account Code : ".concat(objModel.getStrAccountCode()));

			return new ModelAndView("redirect:/frmWebBooksAccountMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmWebBooksAccountMaster.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsWebBooksAccountMasterModel funPrepareModel(clsWebBooksAccountMasterBean objBean, String userCode, String clientCode, String propertyCode) {
		
		long lastNo = 0;
		clsWebBooksAccountMasterModel objACModel;
		if (objBean.getStrAccountCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblacmaster", "AccountMaster", "intGId", clientCode);
			String nextAccountNo = objWebBooksAccountMasterService.funGetMaxAccountNo(objBean.getStrGroupCode(), clientCode, propertyCode);

			String[] objAccNOArr = nextAccountNo.split("-");
			String accPrefixCode = objAccNOArr[0];
			String nextAccNo = String.format("%02d", (Integer.parseInt(objAccNOArr[1]) + 1));
			String accSuffixCode = objAccNOArr[2];
			String accountCode = accPrefixCode + "-" + nextAccNo + "-" + accSuffixCode;

			objACModel = new clsWebBooksAccountMasterModel(new clsWebBooksAccountMasterModel_ID(accountCode, clientCode));
			objACModel.setIntGId(lastNo);

		} else {
			objACModel = new clsWebBooksAccountMasterModel(new clsWebBooksAccountMasterModel_ID(objBean.getStrAccountCode(), clientCode));
		}
		objACModel.setStrAccountName(objBean.getStrAccountName().toUpperCase());
		objACModel.setStrType(objBean.getStrType());
		objACModel.setStrOperational(objBean.getStrOperational());
		objACModel.setStrDebtor(objBean.getStrDebtor());
		objACModel.setStrGroupCode(objBean.getStrGroupCode());
		objACModel.setStrGroupName(objBean.getStrGroupName());
		objACModel.setStrBranch(objBean.getStrBranch());
		objACModel.setintOpeningBal(objBean.getintOpeningBal());
		objACModel.setStrTaxonPurchase(objGlobal.funIfNull(objBean.getStrTaxonPurchase(), "NA", objBean.getStrTaxonPurchase()));
		objACModel.setStrRRGRN(objGlobal.funIfNull(objBean.getStrRRGRN(), "NA", objBean.getStrRRGRN()));
		objACModel.setStrEmployee(objGlobal.funIfNull(objBean.getStrEmployee(), "NA", objBean.getStrEmployee()));
		objACModel.setStrTaxonSales(objGlobal.funIfNull(objBean.getStrTaxonSales(), "NA", objBean.getStrTaxonSales()));
		objACModel.setIntPreYearGrpCode(objGlobal.funIfNull(String.valueOf(objBean.getIntPreYearGrpCode()), "0", String.valueOf(objBean.getIntPreYearGrpCode())));
		objACModel.setIntMSGrpCode(objGlobal.funIfNull(String.valueOf(objBean.getIntMSGrpCode()), "0", String.valueOf(objBean.getIntMSGrpCode())));
		objACModel.setStrCashflowCode(objGlobal.funIfNull(objBean.getStrCashflowCode(), "NA", objBean.getStrCashflowCode()));
		objACModel.setStrDeptCode(objGlobal.funIfNull(objBean.getStrDeptCode(), "NA", objBean.getStrDeptCode()));
		objACModel.setStrDeduction(objGlobal.funIfNull(objBean.getStrDeduction(), "NA", objBean.getStrDeduction()));
		objACModel.setStrFBT(objGlobal.funIfNull(objBean.getStrFBT(), "NA", objBean.getStrFBT()));
		objACModel.setStrCreditor(objGlobal.funIfNull(objBean.getStrCreditor(), "No", objBean.getStrCreditor()));
		objACModel.setStrChequeNo(objGlobal.funIfNull(objBean.getStrChequeNo(), "NA", objBean.getStrChequeNo()));
		objACModel.setStrEmployeeCode(objBean.getStrEmployeeCode());
		
		
		objACModel.setStrClientCode(clientCode);
		objACModel.setStrPropertyCode(propertyCode);
		objACModel.setStrUserCreated(userCode);
		objACModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objACModel.setStrUserModified(userCode);
		objACModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objACModel.setStrCrDr(objBean.getStrCrDr());
		return objACModel;

	}

}
