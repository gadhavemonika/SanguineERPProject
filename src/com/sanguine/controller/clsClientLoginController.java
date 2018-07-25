package com.sanguine.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsClientBean;
import com.sanguine.bean.clsUserHdBean;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsTreeMenuService;
import com.sanguine.service.clsUserMasterService;
import com.sanguine.util.clsClientDetails;

@Controller
@SessionAttributes("userdetails")
public class clsClientLoginController {

	final static Logger logger = Logger.getLogger(clsUserController.class);
	private String strModule = "1";
	@Autowired
	private clsUserMasterService objUserMasterService;

	@Autowired
	private clsGlobalFunctions objGlobalFun;

	@Autowired
	private clsGlobalFunctionsService objGlobalService;

	@Autowired
	private clsTreeMenuService objclsTreeMenuService;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsLocationMasterService objLocationMasterService;

	@Value("${applicationType}")
	String applicationType;

	@RequestMapping(value = "/validateClient", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest req, @Valid clsClientBean objClientBean, BindingResult result, ModelMap map) {
		ModelAndView objMV = null;
		try {
			if (result.hasErrors()) {
				map.put("invalid", "1");
				objMV = new ModelAndView("frmClientLogin", "command", new clsClientBean());
			} else {
				if (objClientBean != null) {
					if (objClientBean.getStrClientCode() != null && objClientBean.getStrClientCode().trim().length() > 0 && objClientBean.getStrPassword() != null && objClientBean.getStrPassword().trim().length() > 0) {

						clsCompanyMasterModel objclsCompanyMasterModel = objSetupMasterService.funGetObject(objClientBean.getStrClientCode());
						if (objclsCompanyMasterModel != null) {
							clsClientDetails.funAddClientCodeAndName();
							SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
							Date systemDate = dFormat.parse(dFormat.format(new Date()));
							Date WebStockExpiryDate = dFormat.parse(dFormat.format(clsClientDetails.hmClientDtl.get(objClientBean.getStrClientCode().trim()).expiryDate));
							if (systemDate.compareTo(WebStockExpiryDate) <= 0) {
								BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
								if (passwordEncoder.matches(objClientBean.getStrPassword(), objclsCompanyMasterModel.getStrPassword())) {
									String startDate = objclsCompanyMasterModel.getDtStart();
									String[] spDate = startDate.split("-");
									String year = spDate[0];
									String month = spDate[1];
									String[] spDate1 = spDate[2].split(" ");
									String date = spDate1[0];
									startDate = date + "/" + month + "/" + year;
									req.getSession().setAttribute("clientCode", objclsCompanyMasterModel.getStrClientCode());
									req.getSession().setAttribute("companyCode", objclsCompanyMasterModel.getStrCompanyCode());
									req.getSession().setAttribute("companyName", objclsCompanyMasterModel.getStrCompanyName());
									req.getSession().setAttribute("startDate", startDate);
									req.getSession().setAttribute("strIndustryType", objclsCompanyMasterModel.getStrIndustryType());
									String strCRMModule = objclsCompanyMasterModel.getStrCRMModule();
									String strWebBookModule = objclsCompanyMasterModel.getStrWebBookModule();
									String strWebClubModule = objclsCompanyMasterModel.getStrWebClubModule();
									String strWebExciseModule = objclsCompanyMasterModel.getStrWebExciseModule();
									String strWebPMSModule = objclsCompanyMasterModel.getStrWebPMSModule();
									String strWebPOSModule = objclsCompanyMasterModel.getStrWebPOSModule();
									String strWebStockModule = objclsCompanyMasterModel.getStrWebStockModule();
									Map<String, String> moduleMap = new TreeMap<String, String>();
									if ("Yes".equalsIgnoreCase(strCRMModule)) {
										moduleMap.put("6-WebCRM", "webcrm_module_icon.png");
										strModule = "6";
									}
									if ("Yes".equalsIgnoreCase(strWebBookModule)) {
										moduleMap.put("5-WebBook", "webbooks_icon.png");
										strModule = "5";
									}
									if ("Yes".equalsIgnoreCase(strWebClubModule)) {
										moduleMap.put("4-WebClub", "webclub_module_icon.png");
										strModule = "4";
									}
									if ("Yes".equalsIgnoreCase(strWebExciseModule)) {
										moduleMap.put("2-WebExcise", "webexcise_module_icon.png");
										strModule = "2";
									}
									if ("Yes".equalsIgnoreCase(strWebPMSModule)) {
										moduleMap.put("3-WebPMS", "webpms_module_icon.png");
										strModule = "3";
									}
									if ("Yes".equalsIgnoreCase(strWebPOSModule)) {
										moduleMap.put("7-WebPOS", "webpos_module_icon.png");
										strModule = "7";
									}
									if ("Yes".equalsIgnoreCase(strWebStockModule)) {
										moduleMap.put("1-WebStocks", "webstocks_module_icon.png");
										strModule = "1";
									}
									req.getSession().setAttribute("moduleNo", strModule);
									req.getSession().setAttribute("moduleMap", moduleMap);
									return new ModelAndView("frmLogin", "command", new clsUserHdBean());
								} else {
									map.put("invalid", "1");
									objMV = new ModelAndView("frmClientLogin", "command", new clsClientBean());
								}
							} else {
								map.put("LicenceExpired", "1");
								objMV = new ModelAndView("frmClientLogin", "command", new clsClientBean());
							}
						} else {
							map.put("invalid", "1");
							objMV = new ModelAndView("frmClientLogin", "command", new clsClientBean());
						}
					} else {
						map.put("invalid", "1");
						objMV = new ModelAndView("frmClientLogin", "command", new clsClientBean());
					}
				} else {
					map.put("invalid", "1");
					objMV = new ModelAndView("frmClientLogin", "command", new clsClientBean());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			map.put("invalid", "1");
			objMV = new ModelAndView("frmClientLogin", "command", new clsClientBean());
		}
		return objMV;
	}

}
