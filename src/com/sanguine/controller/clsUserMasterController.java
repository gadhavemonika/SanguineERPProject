package com.sanguine.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsUserLocDtlBean;
import com.sanguine.bean.clsUserMasterBean;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsUserLocationDtl;
import com.sanguine.model.clsUserMasterModel;
import com.sanguine.model.clsUserMasterModel_ID;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsTreeMenuService;
import com.sanguine.service.clsUserMasterService;
import com.sanguine.util.clsClientDetails;

@Controller
public class clsUserMasterController {
	@Autowired
	clsTreeMenuService objclsTreeMenuService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsUserMasterService objUserMasterService;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	clsGlobalFunctions objGlobal;

	@RequestMapping(value = "/frmUserMaster", method = RequestMethod.GET)
	public ModelAndView funOpenUserMaster(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		/*
		 * List<String> moduleList= new ArrayList<String>();
		 * List<clsCompanyMasterModel>
		 * listClsCompanyMasterModel=objSetupMasterService
		 * .funGetListCompanyMasterModel();
		 * if(listClsCompanyMasterModel.size()>0){ clsCompanyMasterModel
		 * objCompanyMasterModel=listClsCompanyMasterModel.get(0);
		 * if(objCompanyMasterModel
		 * .getStrWebStockModule().equalsIgnoreCase("Yes")) {
		 * moduleList.add("WebStocks"); }
		 * if(objCompanyMasterModel.getStrWebBookModule
		 * ().equalsIgnoreCase("Yes")) { moduleList.add("WebBooks"); }
		 * if(objCompanyMasterModel
		 * .getStrWebClubModule().equalsIgnoreCase("Yes")) {
		 * moduleList.add("WebClub"); }
		 * if(objCompanyMasterModel.getStrWebExciseModule
		 * ().equalsIgnoreCase("Yes")) { moduleList.add("WebExcise"); }
		 * if(objCompanyMasterModel
		 * .getStrWebPMSModule().equalsIgnoreCase("Yes")) {
		 * moduleList.add("WebPMS"); }
		 * if(objCompanyMasterModel.getStrWebPOSModule
		 * ().equalsIgnoreCase("Yes")) { moduleList.add("WebPOS"); }
		 * 
		 * } if(moduleList.isEmpty()) { moduleList.add(""); }
		 */

		model.put("urlHits", urlHits);
		ModelAndView ob = null;
		if ("2".equalsIgnoreCase(urlHits)) {
			ob = new ModelAndView("frmUserMaster_1", "command", new clsUserMasterModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			ob = new ModelAndView("frmUserMaster", "command", new clsUserMasterModel());
		} else {
			ob = new ModelAndView("frmUserMaster", "command", new clsUserMasterModel());
		}
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		Map<String, String> properties = objUserMasterService.funGetUserProperties(clientCode);

		if (properties.isEmpty()) {
			properties.put("", "");
		}
		ob.addObject("propertyList", properties);
		// ob.addObject("moduleList",moduleList);
		return ob;
	}

	@RequestMapping(value = "/saveUserMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsUserMasterBean bean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsClientDetails objClient = clsClientDetails.hmClientDtl.get(clientCode);
		String strListContainWebPOS = "No";
		if (!result.hasErrors()) {
			clsUserMasterModel objUserMaster = funPrepareMaster(bean, userCode, clientCode);
			for (clsUserLocationDtl objUserDtl : objUserMaster.getListUserLocDtlModel()) {
				if (objUserDtl.getStrModule().equalsIgnoreCase("WebPOS")) {
					strListContainWebPOS = "Yes";
				}
			}
			if (strListContainWebPOS.equalsIgnoreCase("Yes")) {
				String sql = "select DISTINCT a.strUserCode from tbluserhd a ,tbluserlocdtl b where a.strUserCode=b.strUserCode and b.strModule='WebPOS' and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "'  ";
				List list = objGlobalFunctionsService.funGetList(sql, "sql");
				boolean flgSave = funNumberofCurrentUsers(list, bean.getStrUserCode1(), objClient.intUserNo);
				if (flgSave) {
					objUserMasterService.funAddUpdateUser(objUserMaster);
					req.getSession().setAttribute("success", true);
					req.getSession().setAttribute("successMessage", "User Code : ".concat(objUserMaster.getStrUserCode1()));
				} else {
					req.getSession().setAttribute("success", true);
					req.getSession().setAttribute("successMessage", "Maximum Users Limits : ".concat(Integer.toString(objClient.intUserNo)));
				}

			} else {
				objUserMasterService.funAddUpdateUser(objUserMaster);
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "User Code : ".concat(objUserMaster.getStrUserCode1()));
			}

			return new ModelAndView("redirect:/frmUserMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmUserMaster?saddr=" + urlHits);
		}
	}

	@RequestMapping(value = "/loadUserMasterData", method = RequestMethod.GET)
	public @ResponseBody clsUserMasterModel funAssignFields(@RequestParam("userCode") String code, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsUserMasterModel objModel = objUserMasterService.funGetObject(code, clientCode);
		if (null == objModel) {
			objModel = new clsUserMasterModel();
			objModel.setStrUserCode1("Invalid Code");
		}

		return objModel;
	}

	private clsUserMasterModel funPrepareMaster(clsUserMasterBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		lastNo = objGlobalFunctionsService.funGetLastNo("tbluserhd", "UserMaster", "intid", clientCode);
		clsUserMasterModel objUserMaster = new clsUserMasterModel(new clsUserMasterModel_ID(objBean.getStrUserCode1(), clientCode));
		objUserMaster.setIntid(lastNo);
		objUserMaster.setStrUserCreated(userCode);
		objUserMaster.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objUserMaster.setStrUserName1(objBean.getStrUserName1());
		String hashedPassword="";
		clsUserMasterModel dbUserModel = objUserMasterService.funGetUser(objBean.getStrUserCode1(), clientCode);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//if (passwordEncoder.matches(objBean.getStrPassword1(), dbUserModel.getStrPassword1())) 
		if (objBean.getStrPassword1().toString().equals(dbUserModel.getStrPassword1().toString())) 
		{
			hashedPassword=dbUserModel.getStrPassword1();
		}
		else
		{
			hashedPassword = passwordEncoder.encode(objBean.getStrPassword1());
		}
		

		objUserMaster.setStrPassword1(hashedPassword);
		objUserMaster.setStrProperty(objBean.getStrProperty());
		objUserMaster.setStrSuperUser(objBean.getStrSuperUser());
		if (objBean.getStrRetire().equals("Yes")) {
			objUserMaster.setStrRetire("Y");
		} else {
			objUserMaster.setStrRetire("N");
		}
		objUserMaster.setStrType(objGlobal.funIfNull(objBean.getStrType(), "", objBean.getStrType()));
		objUserMaster.setStrLoginStatus(objGlobal.funIfNull(objBean.getStrLoginStatus(), "", objBean.getStrLoginStatus()));
		objUserMaster.setStrSignatureImg(objGlobal.funIfNull(objBean.getStrSignatureImg(), "", objBean.getStrSignatureImg()));
		objUserMaster.setStrUserModified(userCode);
		objUserMaster.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objUserMaster.setStrLocation("");
		objUserMaster.setStrShowDashBoard(objGlobal.funIfNull(objBean.getStrShowDashBoard(), "N", "Y"));
		objUserMaster.setStrReorderLevel(objGlobal.funIfNull(objBean.getStrReorderLevel(), "N", "Y"));
		List<clsUserLocationDtl> listUserLocDtlModel = new ArrayList<clsUserLocationDtl>();
		List<clsUserLocDtlBean> listUserLocDtl = objBean.getListUserLocDtlBean();
		for (clsUserLocDtlBean objUserLocDtl : listUserLocDtl) {
			if (null != objUserLocDtl.getStrLocCode()) {
				clsUserLocationDtl objUserLocationDtlModel = new clsUserLocationDtl();
				objUserLocationDtlModel.setStrLocCode(objUserLocDtl.getStrLocCode());
				objUserLocationDtlModel.setStrPropertyCode(objUserLocDtl.getStrPropertyCode());
				objUserLocationDtlModel.setStrModule(objUserLocDtl.getStrModule());
				listUserLocDtlModel.add(objUserLocationDtlModel);
			}
		}
		objUserMaster.setListUserLocDtlModel(listUserLocDtlModel);

		return objUserMaster;
	}

	@RequestMapping(value = "/frmSystemUsers", method = RequestMethod.GET)
	public ModelAndView funSystemUserfrom() throws JRException {
		return new ModelAndView("redirect:/rptSystemUsers.html");
	}

	/*
	 * @RequestMapping(value="/rptSystemUsers",method=RequestMethod.GET) private
	 * ModelAndView funShowReport(@ModelAttribute("command") clsReportBean
	 * objBean,ModelAndView modelAndView ,Map<String, Object> model
	 * ,HttpServletRequest req) throws JRException { String companyName="DSS";
	 * String clientCode
	 * =req.getSession().getAttribute("clientCode").toString(); String userCode
	 * =req.getSession().getAttribute("usercode").toString();
	 * 
	 * List<clsUserHdModel>
	 * userList=funPrepareGroupMasterModel(objUserMasterService
	 * .funGetDtlList(clientCode)); JRDataSource JRdataSource = new
	 * JRBeanCollectionDataSource(userList); model.put("datasource",
	 * JRdataSource); model.put("strUserCode",userCode );
	 * model.put("strCompanyName", companyName); modelAndView=new
	 * ModelAndView("UserSystemPDFReport", model);
	 * 
	 * return modelAndView; }
	 */

	/*
	 * private List<clsUserHdModel> funPrepareGroupMasterModel(List
	 * listUserSystem) { List<clsUserHdModel> userList =new
	 * ArrayList<clsUserHdModel>(); for(int i=0;i<listUserSystem.size();i++) {
	 * clsUserHdModel userModelList =(clsUserHdModel)listUserSystem.get(i);
	 * clsUserHdModel objUserModelList=new clsUserHdModel();
	 * objUserModelList.setStrUserCode(userModelList.getStrUserCode());
	 * objUserModelList.setStrUserName(userModelList.getStrUserName());
	 * objUserModelList.setStrSuperUser(userModelList.getStrSuperUser());
	 * userList.add(objUserModelList);
	 * 
	 * } return listUserSystem; }
	 */

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadPropAndLocName", method = RequestMethod.GET)
	public @ResponseBody String fun(@RequestParam("propCode") String propCode, @RequestParam("locCode") String locCode, HttpServletRequest req) {
		String data = "";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String sql = "select strPropertyName from tblpropertymaster where strPropertyCode='" + propCode + "' and strClientCode='" + clientCode + "' ";
		List listProp = objGlobalFunctionsService.funGetList(sql, "sql");
		if (listProp.size() > 0) {
			data = listProp.get(0).toString();
		}

		sql = "select strLocName from tbllocationmaster where strLocCode='" + locCode + "' and strClientCode='" + clientCode + "' ";
		List listLoc = objGlobalFunctionsService.funGetList(sql, "sql");
		if (listLoc.size() > 0) {
			data = data + "#" + listLoc.get(0).toString();
		}

		return data;
	}

	@RequestMapping(value = "/loadModuleName", method = RequestMethod.GET)
	// public @ResponseBody Map<String,String> funLoadModule(HttpServletRequest
	// req)
	public @ResponseBody List<String> funLoadModule(HttpServletRequest req) {
		String data = "";
		List<String> mmodule = new ArrayList<String>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		List<clsCompanyMasterModel> listClsCompanyMasterModel = objSetupMasterService.funGetListCompanyMasterModel();
		if (listClsCompanyMasterModel.size() > 0) {
			clsCompanyMasterModel objCompanyMasterModel = listClsCompanyMasterModel.get(0);

			if (objCompanyMasterModel.getStrWebStockModule().equalsIgnoreCase("Yes")) {
				mmodule.add("WebStocks");
			}
			if (objCompanyMasterModel.getStrWebBookModule().equalsIgnoreCase("Yes")) {
				mmodule.add("WebBook");
			}
			if (objCompanyMasterModel.getStrWebClubModule().equalsIgnoreCase("Yes")) {
				mmodule.add("WebClub");
			}
			if (objCompanyMasterModel.getStrWebExciseModule().equalsIgnoreCase("Yes")) {
				mmodule.add("WebExcise");
			}
			if (objCompanyMasterModel.getStrWebPMSModule().equalsIgnoreCase("Yes")) {
				mmodule.add("WebPMS");
			}
			if (objCompanyMasterModel.getStrWebPOSModule().equalsIgnoreCase("Yes")) {
				mmodule.add("WebPOS");
			}
			if (objCompanyMasterModel.getStrCRMModule().equalsIgnoreCase("Yes")) {
				mmodule.add("WebCRM");
			}

		}
		if (mmodule.isEmpty()) {
			mmodule.add("");
		}
		return mmodule;
	}

	public boolean funNumberofCurrentUsers(List list, String userCode, int intUserNos) {
		boolean flgAllow = false;
		int intNos = list.size();

		for (Object ob : list) {
			String userDb = (String) ob;
			if (userDb.equals(userCode)) {
				flgAllow = true;
			}
		}

		if (!flgAllow) {
			if (intUserNos >= (intNos + 1)) {
				flgAllow = true;
			}
		}

		return flgAllow;
	}

}
