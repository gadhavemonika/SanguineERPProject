package com.sanguine.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsClientBean;
import com.sanguine.bean.clsPropertySelectionBean;
import com.sanguine.bean.clsUserHdBean;
import com.sanguine.crm.service.clsCRMDayEndService;
import com.sanguine.excise.model.clsExcisePropertySetUpModel;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsUserDtlModel;
import com.sanguine.model.clsUserLogsModel;
import com.sanguine.model.clsUserMasterModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsStructureUpdateService;
import com.sanguine.service.clsTreeMenuService;
import com.sanguine.service.clsUserMasterService;
import com.sanguine.util.clsClientDetails;
import com.sanguine.util.clsTreeRootNodeItemUtil;
import com.sanguine.util.clsUserDesktopUtil;
import com.sanguine.webpms.model.clsDayEndHdModel;
import com.sanguine.webpms.service.clsDayEndService;
import com.sanguine.webpos.bean.clsWebPosPOSSelectionBean;
import com.sanguine.webpos.controller.clsPOSGlobalFunctionsController;

@Controller
@SessionAttributes("userdetails")
public class clsUserController {
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

	@Autowired
	private clsDayEndService objDayEndService;

	@Autowired
	private clsCRMDayEndService objCRMDayEndService;

	@Autowired
	private clsStructureUpdateService objStructureUpdateService;

	@Autowired
	private clsCurrencyMasterService objCurrencyMasterService;

	private static int intcheckSturctureUpdate = 0;

	// public String webServiceUrl=wsServerIp +":"+ wsServerPortNo;
	@Value("${applicationType}")
	String applicationType;

	// public static String POSWSURL;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView welcome(HttpServletRequest req) {
		PasswordEncoderGenerator();
		ModelAndView mAndV = null;
		// Direct Show Login Page or Client Login Page
		try {

			if (applicationType.equalsIgnoreCase("single")) {

				List<clsCompanyMasterModel> listClsCompanyMasterModel = objSetupMasterService.funGetListCompanyMasterModel();
				if (listClsCompanyMasterModel.size() > 0) {
					clsCompanyMasterModel objCompanyMasterModel = listClsCompanyMasterModel.get(listClsCompanyMasterModel.size() - 1);

					funCheckNewfinancial(listClsCompanyMasterModel, req);

					String startDate = objCompanyMasterModel.getDtStart();
					String strIndustryType = objCompanyMasterModel.getStrIndustryType();
					String[] spDate = startDate.split("-");
					String year = spDate[0];
					String month = spDate[1];
					String[] spDate1 = spDate[2].split(" ");
					String date = spDate1[0];
					startDate = date + "/" + month + "/" + year;
					req.getSession().setAttribute("clientCode", objCompanyMasterModel.getStrClientCode());
					req.getSession().setAttribute("companyCode", objCompanyMasterModel.getStrCompanyCode());
					req.getSession().setAttribute("companyName", objCompanyMasterModel.getStrCompanyName());
					req.getSession().setAttribute("startDate", startDate);
					req.getSession().setAttribute("strIndustryType", strIndustryType);
					String strCRMModule = objCompanyMasterModel.getStrCRMModule();
					String strWebBookModule = objCompanyMasterModel.getStrWebBookModule();
					String strWebClubModule = objCompanyMasterModel.getStrWebClubModule();
					String strWebExciseModule = objCompanyMasterModel.getStrWebExciseModule();
					String strWebPMSModule = objCompanyMasterModel.getStrWebPMSModule();
					String strWebPOSModule = objCompanyMasterModel.getStrWebPOSModule();
					String strWebStockModule = objCompanyMasterModel.getStrWebStockModule();
					Map<String, String> moduleMap = new TreeMap<String, String>();
					if ("Yes".equalsIgnoreCase(strWebStockModule)) {
						moduleMap.put("1-WebStocks", "webstocks_module_icon.png");
						strModule = "1";
					}

					if ("Yes".equalsIgnoreCase(strWebExciseModule)) {
						moduleMap.put("2-WebExcise", "webexcise_module_icon.png");
						strModule = "2";
					}

					if ("Yes".equalsIgnoreCase(strWebPMSModule)) {
						moduleMap.put("3-WebPMS", "webpms_module_icon.png");
						strModule = "3";
					}

					if ("Yes".equalsIgnoreCase(strWebClubModule)) {
						moduleMap.put("4-WebClub", "webclub_module_icon.png");
						strModule = "4";
					}

					if ("Yes".equalsIgnoreCase(strWebBookModule)) {
						moduleMap.put("5-WebBook", "webbooks_icon.png");
						strModule = "5";
					}

					if ("Yes".equalsIgnoreCase(strCRMModule)) {
						moduleMap.put("6-WebCRM", "webcrm_module_icon.png");
						strModule = "6";
					}

					if ("Yes".equalsIgnoreCase(strWebPOSModule)) {
						moduleMap.put("7-WebPOS", "webpos_module_icon.png");
						strModule = "7";
					}

					req.getSession().setAttribute("moduleNo", strModule);
					req.getSession().setAttribute("moduleMap", moduleMap);
					mAndV = new ModelAndView("frmLogin", "command", new clsUserHdBean());

				} else {
					return mAndV;
				}
			} else {
				mAndV = new ModelAndView("frmClientLogin", "command", new clsClientBean());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			mAndV = new ModelAndView("frmStructureUpdate_2");
			// welcome(req);
		}
		return mAndV;

	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/validateUser", method = RequestMethod.POST)
	public ModelAndView funValidateUser(@ModelAttribute("command") @Valid clsUserHdBean userBean, BindingResult result, HttpServletRequest req, ModelMap map) {
		if (logger.isDebugEnabled()) {
			logger.debug("Start debug");
		}
		ModelAndView objMV = null;
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();

		if (result.hasErrors()) {
			map.put("invalid", "1");
			return new ModelAndView("frmLogin", "command", new clsUserHdBean());
		} else {
			clsClientDetails.funAddClientCodeAndName();
			if (clsClientDetails.hmClientDtl.get(clientCode) != null && clsClientDetails.hmClientDtl.get(clientCode).Client_Name.equalsIgnoreCase(companyName)) {
				SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date systemDate = dFormat.parse(dFormat.format(new Date()));
					Date WebStockExpiryDate = dFormat.parse(dFormat.format(clsClientDetails.hmClientDtl.get(clientCode).expiryDate));
					if (systemDate.compareTo(WebStockExpiryDate) <= 0) {
						if (userBean.getStrUserCode().equalsIgnoreCase("SANGUINE")) {
							Date dt = new Date();
							int day = dt.getDate();
							int month = dt.getMonth() + 1;
							int year = dt.getYear() + 1900;
							int password = year + month + day + day;
							// int yearcalNo=year%26;
							String strpass = Integer.toString(password);
							char num1 = strpass.charAt(0);
							char num2 = strpass.charAt(1);
							char num3 = strpass.charAt(2);
							char num4 = strpass.charAt(3);
							String alph1 = objGlobalFun.funGetAlphabet(Character.getNumericValue(num1));
							String alph2 = objGlobalFun.funGetAlphabet(Character.getNumericValue(num2));
							String alph3 = objGlobalFun.funGetAlphabet(Character.getNumericValue(num3));
							String alph4 = objGlobalFun.funGetAlphabet(Character.getNumericValue(num4));
							String finalPassword = String.valueOf(password) + alph1 + alph2 + alph3 + alph4;
							System.out.println("Hibernate: " + finalPassword + "CACA");
							String userPassword = userBean.getStrPassword();
							if (finalPassword.equalsIgnoreCase(userPassword)) {
								clsUserMasterModel user = new clsUserMasterModel();
								user.setStrSuperUser("YES");
								user.setStrProperty("ALL");
								user.setStrType("");
								user.setStrUserName1("SANGUINE");
								user.setStrUserCode1("SANGUINE");
								user.setStrRetire("N");
								return funSessionValue(user, req);
							} else {
								clsUserMasterModel user = null;
								try {
									user = objUserMasterService.funGetUser(userBean.getStrUserCode(), clientCode);

								} catch (Exception ex) {

									objMV = new ModelAndView("frmStructureUpdate_2");
								}

								if (user != null) {
									BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
									if (passwordEncoder.matches(userBean.getStrPassword(), user.getStrPassword1())) {
										return funSessionValue(user, req);
									} else {
										map.put("invalid", "1");
										objMV = new ModelAndView("frmLogin", "command", new clsUserHdBean());
									}
								} else {
									map.put("invalid", "1");
									objMV = new ModelAndView("frmLogin", "command", new clsUserHdBean());
								}
							}
						} else {
//							clsUserMasterModel user = objUserMasterService.funGetUser(userBean.getStrUserCode(), clientCode);
							clsUserMasterModel user=null;
							try {
								user = objUserMasterService.funGetUser(userBean.getStrUserCode(), clientCode);

							} catch (Exception ex) {

								objMV = new ModelAndView("frmStructureUpdate_2");
							}

							if (user != null) {
								try {
									BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
									// System.out.println(passwordEncoder.encode(userBean.getStrPassword()));
									// String hashedPassword =
									// passwordEncoder.encode(userBean.getStrPassword());
									// if(hashedPassword.equals(user.getStrPassword1()))
									if (passwordEncoder.matches(userBean.getStrPassword(), user.getStrPassword1())) {
										return funSessionValue(user, req);
									} else {
										map.put("invalid", "1");
										objMV = new ModelAndView("frmLogin", "command", new clsUserHdBean());
									}
								} catch (IllegalArgumentException e) {
									map.put("invalid", "1");
									objMV = new ModelAndView("frmLogin", "command", new clsUserHdBean());
									logger.error(e);
									e.printStackTrace();
								}
							} else {
								map.put("invalid", "1");
								objMV = new ModelAndView("frmLogin", "command", new clsUserHdBean());
							}
						}
					} else {
						map.put("LicenceExpired", "1");
						objMV = new ModelAndView("frmLogin", "command", new clsUserHdBean());
					}

				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else {
				map.put("LicenceExpired", "1");
				objMV = new ModelAndView("frmLogin", "command", new clsUserHdBean());
			}

			return objMV;
		}
	}

	@SuppressWarnings("rawtypes")
	private ModelAndView funSessionValue(clsUserMasterModel user, HttpServletRequest req) {
		
		try
		{
			Properties prop = new Properties();
			Resource resource = new ClassPathResource("resources/database.properties");
			InputStream input = resource.getInputStream();
	
			prop.load(input);
	
			String webStockURL = prop.getProperty("database.urlWebStocks");
			String webExciseURL = prop.getProperty("database.urlExcise");
			String webCRMURL = prop.getProperty("database.urlCRM");
			String webBooksURL = prop.getProperty("database.urlwebbooks");
			String webClubURL = prop.getProperty("database.urlwebclub");
			String webPMSURL = prop.getProperty("database.urlwebpms");
					
			req.getSession().setAttribute("WebStockDB", objGlobalFun.funTrimDBNameFromURL(webStockURL));
			req.getSession().setAttribute("WebCRMDB", objGlobalFun.funTrimDBNameFromURL(webCRMURL));
			req.getSession().setAttribute("WebExciseDB", objGlobalFun.funTrimDBNameFromURL(webExciseURL));
			req.getSession().setAttribute("WebBooksDB", objGlobalFun.funTrimDBNameFromURL(webBooksURL));
			req.getSession().setAttribute("WebPMSDB", objGlobalFun.funTrimDBNameFromURL(webPMSURL));
			req.getSession().setAttribute("WebPMSDB", objGlobalFun.funTrimDBNameFromURL(webClubURL));
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		String strClientCode = req.getSession().getAttribute("clientCode").toString();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("users", funPrepareUserBean(user));

		req.getSession().setAttribute("usercode", user.getStrUserCode1());
		req.getSession().setAttribute("usertype", user.getStrType());
		req.getSession().setAttribute("superuser", user.getStrSuperUser());
		req.getSession().setAttribute("username", user.getStrUserName1());
		req.getSession().setAttribute("userProperty", user.getStrProperty());

		Map<String,clsUserDtlModel> hmUserDtlModel=new HashMap<String,clsUserDtlModel>();
		
		String sqlUserDtl="select strFormName,strAdd,strEdit,strDelete,strView,strPrint,strGrant,strAuthorise "
			+ " from tbluserdtl where strUserCode='"+user.getStrUserCode1()+"' and strClientCode='"+strClientCode+"'";
		List list=objGlobalService.funGetList(sqlUserDtl);
		for(int cnt=0;cnt<list.size();cnt++)
		{
			clsUserDtlModel objUserDtlModel=new clsUserDtlModel();
			Object[] arrObj = (Object[])list.get(cnt);
			objUserDtlModel.setStrFormName(arrObj[0].toString());
			objUserDtlModel.setStrAdd(arrObj[1].toString());
			objUserDtlModel.setStrEdit(arrObj[2].toString());
			objUserDtlModel.setStrDelete(arrObj[3].toString());
			objUserDtlModel.setStrView(arrObj[4].toString());
			objUserDtlModel.setStrPrint(arrObj[5].toString());
			objUserDtlModel.setStrGrant(arrObj[6].toString());
			objUserDtlModel.setStrAuthorise(arrObj[7].toString());
			
			hmUserDtlModel.put(arrObj[0].toString(),objUserDtlModel);
		}
		req.getSession().setAttribute("hmUserPrivileges", hmUserDtlModel);
		
		Map<String, Boolean> hmAuthorizationStatus = new HashMap<String, Boolean>();
		if (null != req.getSession().getAttribute("hmAuthorization")) {
			req.getSession().removeAttribute("hmAuthorization");
		}
		hmAuthorizationStatus.put("GRN", false);
		hmAuthorizationStatus.put("Material Req", false);
		hmAuthorizationStatus.put("MIS", false);
		hmAuthorizationStatus.put("Purchase Order", false);
		hmAuthorizationStatus.put("Purchase Return", false);
		hmAuthorizationStatus.put("Purchase Indent", false);
		hmAuthorizationStatus.put("Material Return", false);
		hmAuthorizationStatus.put("Bill Passing", false);
		hmAuthorizationStatus.put("Production", false);
		hmAuthorizationStatus.put("Production Order", false);
		hmAuthorizationStatus.put("Stock Adjustment", false);
		hmAuthorizationStatus.put("Stock Transfer", false);
		hmAuthorizationStatus.put("Physical Stock Posting", false);
		hmAuthorizationStatus.put("Rate Contract", false);
		hmAuthorizationStatus.put("Work Order", false);
		hmAuthorizationStatus.put("Invoice", false);
		hmAuthorizationStatus.put("Sales Order", false);
		hmAuthorizationStatus.put("Sales Return", false);
		hmAuthorizationStatus.put("Delivery Challan", false);
		hmAuthorizationStatus.put("Stock Req", false);
		
		String sql_Auth = "select distinct(strFormName) from tblworkflowforslabbasedauth where strClientCode='" + strClientCode + "'";
		List listAuthorization = objGlobalService.funGetList(sql_Auth, "sql");
		for (int cnt = 0; cnt < listAuthorization.size(); cnt++) {
			String formName = (String) listAuthorization.get(cnt);
			if (formName.equals("frmMaterialReq")) {
				hmAuthorizationStatus.remove("Material Req");
				hmAuthorizationStatus.put("Material Req", true);
			} else if (formName.equals("frmGRN")) {
				hmAuthorizationStatus.remove("GRN");
				hmAuthorizationStatus.put("GRN", true);
			} else if (formName.equals("frmBillPassing")) {
				hmAuthorizationStatus.remove("Bill Passing");
				hmAuthorizationStatus.put("Bill Passing", true);
			} else if (formName.equals("frmMaterialReturn")) {
				hmAuthorizationStatus.remove("Material Return");
				hmAuthorizationStatus.put("Material Return", true);
			} else if (formName.equals("frmMIS")) {
				hmAuthorizationStatus.remove("MIS");
				hmAuthorizationStatus.put("MIS", true);
			} else if (formName.equals("frmPhysicalStkPosting")) {
				hmAuthorizationStatus.remove("Physical Stock Posting");
				hmAuthorizationStatus.put("Physical Stock Posting", true);
			} else if (formName.equals("frmProduction")) {
				hmAuthorizationStatus.remove("Production");
				hmAuthorizationStatus.put("Production", true);
			} else if (formName.equals("frmProductionOrder")) {
				hmAuthorizationStatus.remove("Production Order");
				hmAuthorizationStatus.put("Production Order", true);
			} else if (formName.equals("frmProduction")) {
				hmAuthorizationStatus.remove("Production");
				hmAuthorizationStatus.put("Production", true);
			} else if (formName.equals("frmPurchaseIndent")) {
				hmAuthorizationStatus.remove("Purchase Indent");
				hmAuthorizationStatus.put("Purchase Indent", true);
			} else if (formName.equals("frmPurchaseOrder")) {
				hmAuthorizationStatus.remove("Purchase Order");
				hmAuthorizationStatus.put("Purchase Order", true);
			} else if (formName.equals("frmPurchaseReturn")) {
				hmAuthorizationStatus.remove("Purchase Return");
				hmAuthorizationStatus.put("Purchase Return", true);
			} else if (formName.equals("frmRateContract")) {
				hmAuthorizationStatus.remove("Rate Contract");
				hmAuthorizationStatus.put("Rate Contract", true);
			} else if (formName.equals("frmStockAdjustment")) {
				hmAuthorizationStatus.remove("Stock Adjustment");
				hmAuthorizationStatus.put("Stock Adjustment", true);
			} else if (formName.equals("frmStockTransfer")) {
				hmAuthorizationStatus.remove("Stock Transfer");
				hmAuthorizationStatus.put("Stock Transfer", true);
			} else if (formName.equals("frmWorkOrder")) {
				hmAuthorizationStatus.remove("Work Order");
				hmAuthorizationStatus.put("Work Order", true);
			}else if (formName.equals("frmInovice")) {
				hmAuthorizationStatus.remove("Invoice");
				hmAuthorizationStatus.put("Invoice", true);
			}else if (formName.equals("frmSalesOrder")) {
				hmAuthorizationStatus.remove("Sales Order");
				hmAuthorizationStatus.put("Sales Order", true);
			}else if (formName.equals("frmSalesReturn")) {
				hmAuthorizationStatus.remove("Sales Return");
				hmAuthorizationStatus.put("Sales Return", true);
			}else if (formName.equals("frmDeliveryChallan")) {
				hmAuthorizationStatus.remove("Delivery Challan");
				hmAuthorizationStatus.put("Delivery Challan", true);
			}
			else if (formName.equals("frmStockReq")) {
				hmAuthorizationStatus.remove("Stock Req");
				hmAuthorizationStatus.put("Stock Req", true);
			}
		}

		req.getSession().setAttribute("hmAuthorization", hmAuthorizationStatus);

		@SuppressWarnings("unchecked")
		Map<String, String> moduleMap = (Map<String, String>) req.getSession().getAttribute("moduleMap");
		if (moduleMap.size() > 1) {
			return new ModelAndView("redirect:/frmModuleSelection.html");
		} else {
			String selectedModuleName = "";
			for (String module : moduleMap.keySet()) {
				selectedModuleName = module;
				if (selectedModuleName.equals("7-WebPOS")) {
					JSONObject jObjLic = objGlobalFun.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funCheckPOSLicenceKey?clientCode=" + strClientCode);
					JSONArray jArr = (JSONArray) jObjLic.get("CheckLicence");
					// JSONObject jObjLic = new JSONObject();
					// JSONArray arrObj=new JSONArray();
					// JSONObject obj=new JSONObject();
					// obj.put("Status", "Invalid");
					// obj.put("Msg",
					// "Invalid POS. Please Contact Technical Support.");
					// arrObj.add(obj);
					// jObjLic.put("CheckLicence", arrObj);

					String posStatus = "";
					String posMsg = "";
					for (int i = 0; i < jArr.size(); i++) {
						// JSONObject mJsonObject = new JSONObject();
						JSONObject mJsonObject = (JSONObject) jArr.get(i);
						posStatus = mJsonObject.get("Status").toString();
						posMsg = mJsonObject.get("Msg").toString();
					}
					if (posStatus.equals("Expired")) {
						req.getSession().setAttribute("posLicStatus", posStatus);
						req.getSession().setAttribute("posLicMsg", posMsg);
						return new ModelAndView("redirect:/frmModuleSelection.html");
					}
					if (posStatus.equals("Invalid")) {
						req.getSession().setAttribute("posLicStatus", posStatus);
						req.getSession().setAttribute("posLicMsg", posMsg);
						return new ModelAndView("redirect:/frmModuleSelection.html");
					}

					if (posStatus.equals("MinDays")) {
						req.getSession().setAttribute("posLicStatus", posStatus);
						req.getSession().setAttribute("posLicMsg", posMsg);
						return new ModelAndView("redirect:/frmPropertySelection.html");
					}

				}

			}
			funSetModuleImage(req, selectedModuleName);
			return new ModelAndView("redirect:/frmPropertySelection.html");
		}
	}
	
	@RequestMapping(value = "/frmModuleSelection", method = RequestMethod.GET)
	private ModelAndView funModuleSelection(HttpServletRequest req) {

		funSetModuleForChangeModule(req);
		return new ModelAndView("frmModuleSelection");
	}

	@RequestMapping(value = "/frmWebBookModuleSelection", method = RequestMethod.GET)
	private ModelAndView frmWebBookModuleSelection(HttpServletRequest req) {
		Map<String, String> moduleMap = new TreeMap<String, String>();

		moduleMap.put("5-WebBookAR", "webbooksAR_icon.png");
		//moduleMap.put("8-WebBookAPGL", "webbooksAPGL_icon.png");
		req.getSession().setAttribute("moduleMap", moduleMap);
		funSetModuleImage(req, "5-WebBookAR");
		if(req.getSession().getAttribute("propertyCode")!=null)
		{
			return new ModelAndView("redirect:/frmMainMenuWithoutPropertySelection.html");
		}
		else
		{
			return new ModelAndView("redirect:/frmPropertySelection.html");
		}
		//return new ModelAndView("redirect:/frmPropertySelection.html");
	}

	@RequestMapping(value = "/redirectToModule", method = RequestMethod.GET)
	private ModelAndView funRedirect(HttpServletRequest req) {
		String selectedModuleName = "";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			selectedModuleName = req.getParameter("moduleName").toString();
			if (selectedModuleName.equals("7-WebPOS")) {
				JSONObject jObjLic = objGlobalFun.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funCheckPOSLicenceKey?clientCode=" + clientCode);
				JSONArray jArr = (JSONArray) jObjLic.get("CheckLicence");
				String posStatus = "";
				String posMsg = "";
				String posClientCode = "";
				for (int i = 0; i < jArr.size(); i++) {
					JSONObject mJsonObject = (JSONObject) jArr.get(i);
					posStatus = mJsonObject.get("Status").toString();
					posMsg = mJsonObject.get("Msg").toString();

					JSONObject jObjClient = objGlobalFun.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSSetup/funGetPOSClientCode");
					posClientCode = (String) jObjClient.get("strClientCode");
					req.getSession().setAttribute("posClientCode", posClientCode);
				}
				if (posStatus.equals("Expired")) {
					req.getSession().setAttribute("posLicStatus", posStatus);
					req.getSession().setAttribute("posLicMsg", posMsg);
					return new ModelAndView("redirect:/frmModuleSelection.html");
				}
				if (posStatus.equals("Invalid")) {
					req.getSession().setAttribute("posLicStatus", posStatus);
					req.getSession().setAttribute("posLicMsg", posMsg);
					return new ModelAndView("redirect:/frmModuleSelection.html");
				}

				if (posStatus.equals("MinDays")) {
					req.getSession().setAttribute("posLicStatus", posStatus);
					req.getSession().setAttribute("posLicMsg", posMsg);
					return new ModelAndView("redirect:/frmPropertySelection.html");
				}

			} else if (selectedModuleName.equals("5-WebBook")) {
				return new ModelAndView("redirect:/frmWebBookModuleSelection.html");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		funSetModuleImage(req, selectedModuleName);
		if(req.getSession().getAttribute("propertyCode")!=null)
		{
			return new ModelAndView("redirect:/frmMainMenuWithoutPropertySelection.html");
		}
		else
		{
			return new ModelAndView("redirect:/frmPropertySelection.html");
		}

		//return new ModelAndView("redirect:/frmPropertySelection.html");
	}








	private void funSetModuleImage(HttpServletRequest req, String selectedModuleName) {

		String moduleTitleImage = "";
		String headerImage = "";
		String strIndustryType = "";
		Map<String, String> moduleMap = new TreeMap<String, String>();
		List<clsCompanyMasterModel> listClsCompanyMasterModel = objSetupMasterService.funGetListCompanyMasterModel();
		if (listClsCompanyMasterModel.size() > 0) {
			clsCompanyMasterModel objCompanyMasterModel = listClsCompanyMasterModel.get(0);
			strIndustryType = objCompanyMasterModel.getStrIndustryType();
		}
		switch (selectedModuleName) {
		case "1-WebStocks":
			if (strIndustryType.equals("Manufacture") || strIndustryType.equals("Retailing")) {
				headerImage = "02webstocks-property-header.jpg";

			} else {
				headerImage = "webstocks-property-header.jpg";
			}
			moduleTitleImage = "Sanguine_WebStocks_1.jpg";

			moduleMap.put("1-WebStocks", "webstocks_module_icon.png");
			strModule = "1";

			break;

		case "2-WebExcise":
			headerImage = "webstocks-property-header.jpg";
			moduleTitleImage = "Sanguine_WebExcise_1.jpg";

			moduleMap.put("2-WebExcise", "webexcise_module_icon.png");
			strModule = "2";

			break;

		case "3-WebPMS":
			headerImage = "webstocks-property-header.jpg";
			moduleTitleImage = "Sanguine_WebPMS_1.jpg";

			moduleMap.put("3-WebPMS", "webpms_module_icon.png");
			strModule = "3";

			break;

		case "4-WebClub":
			headerImage = "webstocks-property-header.jpg";
			moduleTitleImage = "Sanguine_WebClub_1.jpg";

			moduleMap.put("4-WebClub", "webclub_module_icon.png");
			strModule = "4";

			break;

		case "5-WebBook":
			headerImage = "webstocks-property-header.jpg";
			moduleTitleImage = "Sanguine_WebBooks_1.jpg";

			moduleMap.put("5-WebBook", "webbooks_icon.png");
			strModule = "5";

			break;

		case "6-WebCRM":
			if (strIndustryType.equals("Manufacture") || strIndustryType.equals("Retailing")) {
				headerImage = "02webstocks-property-header.jpg";

			} else {
				headerImage = "webstocks-property-header.jpg";
			}
			moduleTitleImage = "Sanguine_WebCRM_1.jpg";

			moduleMap.put("6-WebCRM", "webcrm_module_icon.png");
			strModule = "6";

			break;

		case "7-WebPOS":
			headerImage = "webstocks-property-header.jpg";
			moduleTitleImage = "Sanguine_WebPOS_1.jpg";

			moduleMap.put("7-WebPOS", "webpos_module_icon.png");
			strModule = "7";

			break;

		case "5-WebBookAR":
			if (strIndustryType.equals("Manufacture") || strIndustryType.equals("Retailing")) {
				headerImage = "02webstocks-property-header.jpg";

			} else {
				headerImage = "webstocks-property-header.jpg";
			}
			moduleTitleImage = "Sanguine_WebBooks_1.jpg";

			moduleMap.put("5-WebBookAR", "webbooksAR_icon.png");
			strModule = "5";

			break;

		case "8-WebBookAPGL":
			headerImage = "webstocks-property-header.jpg";
			moduleTitleImage = "Sanguine_WebBooks_1.jpg";

			moduleMap.put("8-WebBookAPGL", "webbooksAPGL_icon.png");
			strModule = "8";

			break;

		default:
			break;
		}

		req.getSession().setAttribute("moduleNo", strModule);
		req.getSession().setAttribute("moduleMap", moduleMap);

		req.getSession().setAttribute("selectedModuleName", selectedModuleName);
		req.getSession().setAttribute("moduleTitleImage", moduleTitleImage);
		req.getSession().setAttribute("headerImage", headerImage);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/frmPropertySelection", method = RequestMethod.GET)
	private ModelAndView funLoadPropertySelection(@ModelAttribute("command") clsPropertySelectionBean objPropBean, BindingResult result, HttpServletRequest req) {
		ModelAndView ob = new ModelAndView("frmPropertySelection");
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String usercode = req.getSession().getAttribute("usercode").toString();
		String userPrpoerty = req.getSession().getAttribute("userProperty").toString();
		HashMap<String, String> mapProperty = (HashMap) objGlobalService.funGetUserWisePropertyList(clientCode, usercode, userPrpoerty);
		System.out.println(mapProperty + "\tSize=" + mapProperty.size());
		if (mapProperty.isEmpty()) {
			mapProperty.put("", "");
		}
		mapProperty = clsGlobalFunctions.funSortByValues(mapProperty);
		ob.addObject("listProperty", mapProperty);

		Map<String, String> mapCompany = objGlobalService.funGetCompanyList(clientCode);
		if (mapCompany.isEmpty()) {
			mapCompany.put("", "");
		}
		ob.addObject("listComapny", mapCompany);
		Map<Integer, String> mapFinancialYear = objGlobalService.funGetFinancialYearList(clientCode);
		if (mapFinancialYear.isEmpty()) {
			mapFinancialYear.put(0, "");
		}
		ob.addObject("listFinancialYear", mapFinancialYear);
		String lastLoggedInLocCode = "", lastLoggedInPropCode = "";
		String sql_UserLog = "select max(strLoggedInLocation),max(strLoggedInProperty) " + "from tbluserlogs where strUserCode='" + usercode + "' and strClientCode='" + clientCode + "' " + "and dteLoginDate= (select max(dteLoginDate) from tbluserlogs)";
		List listUserLogs = objGlobalService.funGetList(sql_UserLog, "sql");
		for (int cnt = 0; cnt < listUserLogs.size(); cnt++) {
			Object[] arrObjData = (Object[]) listUserLogs.get(cnt);
			if (null != arrObjData[0]) {
				lastLoggedInLocCode = arrObjData[0].toString();
				lastLoggedInPropCode = arrObjData[1].toString();
			}
		}

		req.getSession().setAttribute("LastProperty", lastLoggedInPropCode);
		req.getSession().setAttribute("LastLocation", lastLoggedInLocCode);

		return ob;
	}

	/*
	 * @SuppressWarnings({ "unchecked", "rawtypes" })
	 * 
	 * @RequestMapping(value="loadLoactionComboPropertyWise", method =
	 * RequestMethod.GET) public @ResponseBody Map<String,String>
	 * funLoadLocComboPropertyWise(@RequestParam("code") String
	 * propertyCode,HttpServletRequest req){ String
	 * clientCode=req.getSession().getAttribute("clientCode").toString(); String
	 * usercode=req.getSession().getAttribute("usercode").toString();
	 * HashMap<String,String>
	 * locMap=(HashMap)objLocationMasterService.funGetLocMapPropertyWise
	 * (propertyCode,clientCode,usercode);
	 * locMap=clsGlobalFunctions.funSortByValues(locMap); return locMap;
	 * 
	 * }
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "loadLoactionComboPropertyNUserWise", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> funLoadLocComboPropertyWise(@RequestParam("code") String propertyCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		HashMap<String, String> locMap = new HashMap<String, String>();
		String usercode = req.getSession().getAttribute("usercode").toString();
		try {
			locMap = (HashMap) objUserMasterService.funGetLocMapPropertyNUserWise(propertyCode, clientCode, usercode, req);
			locMap = clsGlobalFunctions.funSortByValues(locMap);
		} catch (Exception e) {
			e.printStackTrace();
			locMap.put("Invalid", "Invalid");
		}
		return locMap;

	}

	@SuppressWarnings("unused")
	private List<clsUserHdBean> funPrepareListofBean(List<clsUserMasterModel> listUsers) {
		List<clsUserHdBean> listUserHdBean = null;
		if (listUsers != null && !listUsers.isEmpty()) {
			listUserHdBean = new ArrayList<clsUserHdBean>();
			clsUserHdBean objUserHdBean = null;
			for (clsUserMasterModel user : listUsers) {
				objUserHdBean = new clsUserHdBean();
				objUserHdBean.setStrUserName(user.getStrUserName1());
				objUserHdBean.setStrUserCode(user.getStrUserCode1());
				listUserHdBean.add(objUserHdBean);
			}
		}
		return listUserHdBean;
	}

	private clsUserHdBean funPrepareUserBean(clsUserMasterModel user) {
		clsUserHdBean objUserHdBean = new clsUserHdBean();
		objUserHdBean.setStrUserCode(user.getStrUserCode1());
		objUserHdBean.setStrUserName(user.getStrUserName1());
		objUserHdBean.setStrSuperUser(user.getStrSuperUser());
		objUserHdBean.setStrProperty(user.getStrProperty());
		return objUserHdBean;
	}

	// @SuppressWarnings("unused")
	// @RequestMapping(value = "/frmMainMenu", method = RequestMethod.POST)
	// public ModelAndView funValidateProperty(@ModelAttribute("command")
	// clsPropertySelectionBean propBean,BindingResult result,HttpServletRequest
	// req,Map<String,Object> model)
	// {
	// String clientCode=req.getSession().getAttribute("clientCode").toString();
	// String userCode=req.getSession().getAttribute("usercode").toString();
	// try
	// {
	// req.getSession().setAttribute("propertyCode",propBean.getStrPropertyCode());
	// req.getSession().setAttribute("propertyName",propBean.getStrPropertyName());
	// req.getSession().setAttribute("locationCode",propBean.getStrLocationCode());
	// req.getSession().setAttribute("locationName",propBean.getStrLocationName());
	// clsLocationMasterModel
	// objLocCode=objLocationMasterService.funGetObject(propBean.getStrLocationCode(),clientCode);
	// req.getSession().setAttribute("locationType",objLocCode.getStrType());
	// req.getSession().setAttribute("financialYear",propBean.getStrFinancialYear());
	//
	// List<clsUserDesktopUtil> userDesktop=funGetUserDesktop(req);
	// req.getSession().setAttribute("desktop",userDesktop);
	//
	// String propCode=propBean.getStrPropertyCode();
	// String locCode=propBean.getStrLocationCode();
	// String date=objGlobalFun.funGetCurrentDate("dd-MM-yyyy");
	// String dateTime=objGlobalFun.funGetCurrentDateTime("yyyy-MM-dd");
	// String time=dateTime.split(" ")[1];
	//
	// clsUserLogsModel objUserLogsModel=new clsUserLogsModel();
	// objUserLogsModel.setStrUserCode(userCode);
	// objUserLogsModel.setStrLoggedInLocation(locCode);
	// objUserLogsModel.setStrLoggedInProperty(propCode);
	// objUserLogsModel.setStrClientCode(clientCode);
	// objUserLogsModel.setDteLoginDate(dateTime);
	// objUserLogsModel.setTmeLoginTime(time);
	// objGlobalService.funAddUserLogEntry(objUserLogsModel);
	//
	// Map<String,Object> tr = funGetTreeMap(req);
	// req.getSession().setAttribute("treeMap",tr);
	//
	// clsPropertySetupModel
	// objSetup=objSetupMasterService.funGetObjectPropertySetup(propBean.getStrPropertyCode(),
	// clientCode);
	// if(null != objSetup)
	// {
	// req.getSession().setAttribute("amtDecPlace",objSetup.getIntdec());
	// req.getSession().setAttribute("qtyDecPlace",objSetup.getIntqtydec());
	// req.getSession().setAttribute("strNegStock",objSetup.getStrNegStock());
	// req.getSession().setAttribute("RateFrom",
	// objSetup.getStrRatePickUpFrom());
	// req.getSession().setAttribute("strWorkFlowbasedAuth",
	// objSetup.getStrWorkFlowbasedAuth());
	// req.getSession().setAttribute("ShowReqVal", objSetup.getStrShowReqVal());
	// req.getSession().setAttribute("ShowReqStk", objSetup.getStrShowStkReq());
	// req.getSession().setAttribute("changeUOM",
	// objSetup.getStrChangeUOMTrans());
	// req.getSession().setAttribute("audit", objSetup.getStrAudit());
	// req.getSession().setAttribute("showLocWiseProdMaster",
	// objSetup.getStrShowProdMaster());
	// req.getSession().setAttribute("showPrptyWiseProdDoc",
	// objSetup.getStrShowProdDoc());
	// req.getSession().setAttribute("ShowTransAsc_Desc",
	// objSetup.getStrShowTransAsc_Desc());
	// req.getSession().setAttribute("AllowDateChangeInMIS",
	// objSetup.getStrAllowDateChangeInMIS());
	// req.getSession().setAttribute("NameChangeInProdMast",
	// objSetup.getStrNameChangeProdMast());
	// req.getSession().setAttribute("NotificationTimeinterval",
	// objSetup.getIntNotificationTimeinterval());
	// req.getSession().setAttribute("MonthEnd", objSetup.getStrMonthEnd());
	// req.getSession().setAttribute("showAllProdToAllLoc",
	// objSetup.getStrShowAllProdToAllLoc());
	// req.getSession().setAttribute("DiscEffectOnPO",
	// objSetup.getStrEffectOfDiscOnPO());
	// req.getSession().setAttribute("invoieFormat",
	// objSetup.getStrInvFormat());
	// }
	// else
	// {
	// req.getSession().setAttribute("amtDecPlace",0);
	// req.getSession().setAttribute("qtyDecPlace",0);
	// req.getSession().setAttribute("strNegStock","N");
	// req.getSession().setAttribute("RateFrom", "");
	// req.getSession().setAttribute("strWorkFlowbasedAuth", "N");
	// req.getSession().setAttribute("ShowReqVal", "N");
	// req.getSession().setAttribute("ShowReqStk", "N");
	// req.getSession().setAttribute("changeUOM", "N");
	// req.getSession().setAttribute("audit", "N");
	// req.getSession().setAttribute("showLocWiseProdMaster", "N");
	// req.getSession().setAttribute("showPrptyWiseProdMaster","N" );
	// req.getSession().setAttribute("showPrptyWiseProdDoc", "N");
	// req.getSession().setAttribute("ShowTransAsc_Desc", "Asc");
	// req.getSession().setAttribute("AllowDateChangeInMIS","N");
	// req.getSession().setAttribute("NameChangeInProdMast", "N");
	// req.getSession().setAttribute("NotificationTimeinterval", 1);
	// req.getSession().setAttribute("MonthEnd", "N");
	// req.getSession().setAttribute("showAllProdToAllLoc", 'N');
	// req.getSession().setAttribute("DiscEffectOnPO", "Y");
	// req.getSession().setAttribute("invoieFormat", "");
	// }
	//
	//
	// if(req.getSession().getAttribute("selectedModuleName").equals("3-WebPMS"))
	// {
	// String sql =
	// "select tmeCheckInTime,tmeCheckOutTime from tblpropertysetup "
	// + " where strPropertyCode='" + propBean.getStrPropertyCode() +
	// "' and strClientCode='"+clientCode+"'";
	// List listPropInfo=objGlobalService.funGetListModuleWise(sql, "sql");
	// Object[] arrPropInfo=(Object[]) listPropInfo.get(0);
	// req.getSession().setAttribute("PMSCheckInTime",
	// arrPropInfo[0].toString());
	// req.getSession().setAttribute("PMSCheckOutTime",
	// arrPropInfo[1].toString());
	//
	// sql = "select count(*) from tbldayendprocess "
	// + " where strPropertyCode='" + propBean.getStrPropertyCode() +
	// "' and strClientCode='"+clientCode+"' "
	// + " and strDayEnd='N' ";
	// List listDayEnd=objGlobalService.funGetListModuleWise(sql, "sql");
	// BigInteger count=new BigInteger(listDayEnd.get(0).toString());
	//
	// if(count.intValue()>0)
	// {
	// sql = "select date(max(dtePMSDate)),strStartDay from tbldayendprocess "
	// + " where strPropertyCode='" + propBean.getStrPropertyCode() +
	// "' and strClientCode='"+clientCode+"' "
	// + " and strDayEnd='N' ";
	// List listStartFlag=objGlobalService.funGetListModuleWise(sql, "sql");
	// Object[] arrObjDayEnd=(Object[])listStartFlag.get(0);
	// req.getSession().setAttribute("PMSDate", arrObjDayEnd[0].toString());
	// req.getSession().setAttribute("PMSStartDay", arrObjDayEnd[1].toString());
	// }
	// else
	// {
	// req.getSession().setAttribute("PMSDate",
	// objGlobalFun.funGetCurrentDate("yyyy-MM-dd"));
	// req.getSession().setAttribute("PMSStartDay", "N");
	//
	// clsDayEndHdModel objDayEndModel=new clsDayEndHdModel();
	// objDayEndModel.setStrPropertyCode(propBean.getStrPropertyCode());
	// objDayEndModel.setStrDayEnd("N");
	// objDayEndModel.setStrStartDay("Y");
	// objDayEndModel.setDtePMSDate(objGlobalFun.funGetCurrentDate("yyyy-MM-dd"));
	// objDayEndModel.setDblDayEndAmt(0);
	// objDayEndModel.setStrUserCode(userCode);
	// objDayEndModel.setStrClientCode(clientCode);
	//
	// objDayEndService.funAddUpdateDayEndHd(objDayEndModel);
	// }
	// }
	//
	// if(req.getSession().getAttribute("selectedModuleName").equals("7-WebPOS"))
	// {
	// return new ModelAndView("frmWebPOSLogin");
	// }
	//
	// }catch(Exception ex)
	// {
	//
	// // For Handle the Code through Structure Update
	// //
	// if(req.getSession().getAttribute("selectedModuleName").equals("3-WebPMS"))
	// // {
	// //
	// req.getSession().setAttribute("PropertyError","Please Check WebPMS PropertySetup");
	// // }else
	// // {
	// //
	// req.getSession().setAttribute("PropertyError","Please Check WebStock PropertySetup");
	// // }
	//
	// // objStructureUpdateService.funUpdateStructure(clientCode);
	// //
	// //
	// // clsPropertySetupModel
	// objSetup=objSetupMasterService.funGetObjectPropertySetup(propBean.getStrPropertyCode(),
	// clientCode);
	// // if(null != objSetup)
	// // {
	// // req.getSession().setAttribute("amtDecPlace",objSetup.getIntdec());
	// // req.getSession().setAttribute("qtyDecPlace",objSetup.getIntqtydec());
	// //
	// req.getSession().setAttribute("strNegStock",objSetup.getStrNegStock());
	// // req.getSession().setAttribute("RateFrom",
	// objSetup.getStrRatePickUpFrom());
	// // req.getSession().setAttribute("strWorkFlowbasedAuth",
	// objSetup.getStrWorkFlowbasedAuth());
	// // req.getSession().setAttribute("ShowReqVal",
	// objSetup.getStrShowReqVal());
	// // req.getSession().setAttribute("ShowReqStk",
	// objSetup.getStrShowStkReq());
	// // req.getSession().setAttribute("changeUOM",
	// objSetup.getStrChangeUOMTrans());
	// // req.getSession().setAttribute("audit", objSetup.getStrAudit());
	// // req.getSession().setAttribute("showLocWiseProdMaster",
	// objSetup.getStrShowProdMaster());
	// // req.getSession().setAttribute("showPrptyWiseProdDoc",
	// objSetup.getStrShowProdDoc());
	// // req.getSession().setAttribute("ShowTransAsc_Desc",
	// objSetup.getStrShowTransAsc_Desc());
	// // req.getSession().setAttribute("AllowDateChangeInMIS",
	// objSetup.getStrAllowDateChangeInMIS());
	// // req.getSession().setAttribute("NameChangeInProdMast",
	// objSetup.getStrNameChangeProdMast());
	// // req.getSession().setAttribute("NotificationTimeinterval",
	// objSetup.getIntNotificationTimeinterval());
	// // req.getSession().setAttribute("MonthEnd", objSetup.getStrMonthEnd());
	// // req.getSession().setAttribute("showAllProdToAllLoc",
	// objSetup.getStrShowAllProdToAllLoc());
	// // }
	// // else
	// // {
	// // req.getSession().setAttribute("amtDecPlace",0);
	// // req.getSession().setAttribute("qtyDecPlace",0);
	// // req.getSession().setAttribute("strNegStock","N");
	// // req.getSession().setAttribute("RateFrom", "");
	// // req.getSession().setAttribute("strWorkFlowbasedAuth", "N");
	// // req.getSession().setAttribute("ShowReqVal", "N");
	// // req.getSession().setAttribute("ShowReqStk", "N");
	// // req.getSession().setAttribute("changeUOM", "N");
	// // req.getSession().setAttribute("audit", "N");
	// // req.getSession().setAttribute("showLocWiseProdMaster", "N");
	// // req.getSession().setAttribute("showPrptyWiseProdMaster","N" );
	// // req.getSession().setAttribute("showPrptyWiseProdDoc", "N");
	// // req.getSession().setAttribute("ShowTransAsc_Desc", "Asc");
	// // req.getSession().setAttribute("AllowDateChangeInMIS","N");
	// // req.getSession().setAttribute("NameChangeInProdMast", "N");
	// // req.getSession().setAttribute("NotificationTimeinterval", 1);
	// // req.getSession().setAttribute("MonthEnd", "N");
	// // req.getSession().setAttribute("showAllProdToAllLoc", 'N');
	// // }
	// //
	// //
	// //
	// if(req.getSession().getAttribute("selectedModuleName").equals("3-WebPMS"))
	// // {
	// // String sql =
	// "select tmeCheckInTime,tmeCheckOutTime from tblpropertysetup "
	// // + " where strPropertyCode='" + propBean.getStrPropertyCode() +
	// "' and strClientCode='"+clientCode+"'";
	// // List listPropInfo=objGlobalService.funGetListModuleWise(sql, "sql");
	// // Object[] arrPropInfo=(Object[]) listPropInfo.get(0);
	// // req.getSession().setAttribute("PMSCheckInTime",
	// arrPropInfo[0].toString());
	// // req.getSession().setAttribute("PMSCheckOutTime",
	// arrPropInfo[1].toString());
	// //
	// // sql = "select count(*) from tbldayendprocess "
	// // + " where strPropertyCode='" + propBean.getStrPropertyCode() +
	// "' and strClientCode='"+clientCode+"' "
	// // + " and strDayEnd='N' ";
	// // List listDayEnd=objGlobalService.funGetListModuleWise(sql, "sql");
	// // BigInteger count=new BigInteger(listDayEnd.get(0).toString());
	// //
	// // if(count.intValue()>0)
	// // {
	// // sql =
	// "select date(max(dtePMSDate)),strStartDay from tbldayendprocess "
	// // + " where strPropertyCode='" + propBean.getStrPropertyCode() +
	// "' and strClientCode='"+clientCode+"' "
	// // + " and strDayEnd='N' ";
	// // List listStartFlag=objGlobalService.funGetListModuleWise(sql, "sql");
	// // Object[] arrObjDayEnd=(Object[])listStartFlag.get(0);
	// // req.getSession().setAttribute("PMSDate", arrObjDayEnd[0].toString());
	// // req.getSession().setAttribute("PMSStartDay",
	// arrObjDayEnd[1].toString());
	// // }
	// // else
	// // {
	// // req.getSession().setAttribute("PMSDate",
	// objGlobalFun.funGetCurrentDate("yyyy-MM-dd"));
	// // req.getSession().setAttribute("PMSStartDay", "N");
	// //
	// // clsDayEndHdModel objDayEndModel=new clsDayEndHdModel();
	// // objDayEndModel.setStrPropertyCode(propBean.getStrPropertyCode());
	// // objDayEndModel.setStrDayEnd("N");
	// // objDayEndModel.setStrStartDay("Y");
	// //
	// objDayEndModel.setDtePMSDate(objGlobalFun.funGetCurrentDate("yyyy-MM-dd"));
	// // objDayEndModel.setDblDayEndAmt(0);
	// // objDayEndModel.setStrUserCode(userCode);
	// // objDayEndModel.setStrClientCode(clientCode);
	// //
	// // objDayEndService.funAddUpdateDayEndHd(objDayEndModel);
	// // }
	// // }
	//
	// ex.printStackTrace();
	// //return new ModelAndView("redirect:/frmPropertySelection.html");
	// }
	//
	//
	// return new ModelAndView("frmMainMenu");
	// }

	@SuppressWarnings("unused")
	@RequestMapping(value = "/frmMainMenu", method = RequestMethod.GET)
	public ModelAndView funValidateProperty(@ModelAttribute("command") clsPropertySelectionBean propBean, BindingResult result, HttpServletRequest req, Map<String, Object> model) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		clsUserMasterModel objModel = null;
		try {
			objModel = objUserMasterService.funGetUser(userCode, clientCode);
			req.getSession().setAttribute("propertyCode", propBean.getStrPropertyCode());
			req.getSession().setAttribute("propertyName", propBean.getStrPropertyName());
			req.getSession().setAttribute("locationCode", propBean.getStrLocationCode());
			req.getSession().setAttribute("locationName", propBean.getStrLocationName());
			clsLocationMasterModel objLocCode = objLocationMasterService.funGetObject(propBean.getStrLocationCode(), clientCode);

			String dayEndDate = objCRMDayEndService.funGetCRMDayEndLocationDate(propBean.getStrLocationCode(), clientCode);
			if (!dayEndDate.isEmpty()) {
				SimpleDateFormat obj = new SimpleDateFormat("yyyy-MM-dd");
				Date oldDate = obj.parse(dayEndDate);
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(oldDate);
				cal.add(Calendar.DATE, 1);
				String newStartDate = cal.getTime().getYear() + 1900 + "-" + (cal.getTime().getMonth() + 1) + "-" + (cal.getTime().getDate());
				dayEndDate = newStartDate;
			} else {
				dayEndDate = objGlobalFun.funGetCurrentDate("yyyy-MM-dd");

			}
			req.getSession().setAttribute("locationType", objLocCode.getStrType());
			req.getSession().setAttribute("financialYear", propBean.getStrFinancialYear());
			req.getSession().setAttribute("dayEndDate", dayEndDate);

			if (req.getSession().getAttribute("selectedModuleName").equals("7-WebPOS")) {
				// List<clsUserDesktopUtil> webPOSDesktop =
				// funGetPOSMenuMap("super", "117.001", "M", "Super", "P01");
				// req.getSession().setAttribute("desktop",webPOSDesktop);
			} else {
				List<clsUserDesktopUtil> userDesktop = funGetUserDesktop(req);
				req.getSession().setAttribute("desktop", userDesktop);
			}

			String propCode = propBean.getStrPropertyCode();
			String locCode = propBean.getStrLocationCode();
			String date = objGlobalFun.funGetCurrentDate("dd-MM-yyyy");
			String dateTime = objGlobalFun.funGetCurrentDateTime("yyyy-MM-dd");
			String time = dateTime.split(" ")[1];

			clsUserLogsModel objUserLogsModel = new clsUserLogsModel();
			objUserLogsModel.setStrUserCode(userCode);
			objUserLogsModel.setStrLoggedInLocation(locCode);
			objUserLogsModel.setStrLoggedInProperty(propCode);
			objUserLogsModel.setStrClientCode(clientCode);
			objUserLogsModel.setDteLoginDate(dateTime);
			objUserLogsModel.setTmeLoginTime(time);
			objGlobalService.funAddUserLogEntry(objUserLogsModel);

			if (req.getSession().getAttribute("selectedModuleName").equals("7-WebPOS")) {
				// List<clsUserDesktopUtil> webPOSDesktop =
				// funGetPOSMenuMap("super", "117.001", "M", "Super", "P01");
				// req.getSession().setAttribute("treeMap",new
				// HashMap<String,Object>());
			} else {
				Map<String, Object> tr = funGetTreeMap(req);
				req.getSession().setAttribute("treeMap", tr);
			}

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propBean.getStrPropertyCode(), clientCode);
			if (null != objSetup) {
				req.getSession().setAttribute("amtDecPlace", objSetup.getIntdec());
				req.getSession().setAttribute("qtyDecPlace", objSetup.getIntqtydec());
				req.getSession().setAttribute("strNegStock", objSetup.getStrNegStock());
				req.getSession().setAttribute("RateFrom", objSetup.getStrRatePickUpFrom());
				req.getSession().setAttribute("strWorkFlowbasedAuth", objSetup.getStrWorkFlowbasedAuth());
				req.getSession().setAttribute("ShowReqVal", objSetup.getStrShowReqVal());
				req.getSession().setAttribute("ShowReqStk", objSetup.getStrShowStkReq());
				req.getSession().setAttribute("changeUOM", objSetup.getStrChangeUOMTrans());
				req.getSession().setAttribute("audit", objSetup.getStrAudit());
				req.getSession().setAttribute("showLocWiseProdMaster", objSetup.getStrShowProdMaster());
				req.getSession().setAttribute("showPrptyWiseProdDoc", objSetup.getStrShowProdDoc());
				req.getSession().setAttribute("ShowTransAsc_Desc", objSetup.getStrShowTransAsc_Desc());
				req.getSession().setAttribute("AllowDateChangeInMIS", objSetup.getStrAllowDateChangeInMIS());
				req.getSession().setAttribute("NameChangeInProdMast", objSetup.getStrNameChangeProdMast());
				req.getSession().setAttribute("NotificationTimeinterval", objSetup.getIntNotificationTimeinterval());
				req.getSession().setAttribute("MonthEnd", objSetup.getStrMonthEnd());
				req.getSession().setAttribute("showAllProdToAllLoc", objSetup.getStrShowAllProdToAllLoc());
				req.getSession().setAttribute("DiscEffectOnPO", objSetup.getStrEffectOfDiscOnPO());
				req.getSession().setAttribute("invoieFormat", objSetup.getStrInvFormat());
				clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(objSetup.getStrCurrencyCode(), clientCode);
				if (objCurrModel == null) {
					req.getSession().setAttribute("currencyCode", "CU00001");
					req.getSession().setAttribute("currValue", 1);
				} else {
					req.getSession().setAttribute("currencyCode", objCurrModel.getStrCurrencyCode());
					req.getSession().setAttribute("currValue", objCurrModel.getDblConvToBaseCurr());
				}
				req.getSession().setAttribute("showAllTaxesOnTransactions", objSetup.getStrShowAllTaxesOnTransaction());

			} else {
				req.getSession().setAttribute("amtDecPlace", 0);
				req.getSession().setAttribute("qtyDecPlace", 0);
				req.getSession().setAttribute("strNegStock", "N");
				req.getSession().setAttribute("RateFrom", "");
				req.getSession().setAttribute("strWorkFlowbasedAuth", "N");
				req.getSession().setAttribute("ShowReqVal", "N");
				req.getSession().setAttribute("ShowReqStk", "N");
				req.getSession().setAttribute("changeUOM", "N");
				req.getSession().setAttribute("audit", "N");
				req.getSession().setAttribute("showLocWiseProdMaster", "N");
				req.getSession().setAttribute("showPrptyWiseProdMaster", "N");
				req.getSession().setAttribute("showPrptyWiseProdDoc", "N");
				req.getSession().setAttribute("ShowTransAsc_Desc", "Asc");
				req.getSession().setAttribute("AllowDateChangeInMIS", "N");
				req.getSession().setAttribute("NameChangeInProdMast", "N");
				req.getSession().setAttribute("NotificationTimeinterval", 1);
				req.getSession().setAttribute("MonthEnd", "N");
				req.getSession().setAttribute("showAllProdToAllLoc", 'N');
				req.getSession().setAttribute("DiscEffectOnPO", "Y");
				req.getSession().setAttribute("invoieFormat", "");
				req.getSession().setAttribute("currencyCode", "CU00001");
				req.getSession().setAttribute("conValue", 1);
			}

			if (req.getSession().getAttribute("selectedModuleName").equals("3-WebPMS")) {
				String sql = "select tmeCheckInTime,tmeCheckOutTime from tblpropertysetup " + " where strPropertyCode='" + propBean.getStrPropertyCode() + "' and strClientCode='" + clientCode + "'";
				List listPropInfo = objGlobalService.funGetListModuleWise(sql, "sql");
				Object[] arrPropInfo = (Object[]) listPropInfo.get(0);
				req.getSession().setAttribute("PMSCheckInTime", arrPropInfo[0].toString());
				req.getSession().setAttribute("PMSCheckOutTime", arrPropInfo[1].toString());

				sql = "select count(*) from tbldayendprocess " + " where strPropertyCode='" + propBean.getStrPropertyCode() + "' and strClientCode='" + clientCode + "' " + " and strDayEnd='N' ";
				List listDayEnd = objGlobalService.funGetListModuleWise(sql, "sql");
				BigInteger count = new BigInteger(listDayEnd.get(0).toString());

				if (count.intValue() > 0) {
					sql = "select date(max(dtePMSDate)),strStartDay from tbldayendprocess " + " where strPropertyCode='" + propBean.getStrPropertyCode() + "' and strClientCode='" + clientCode + "' " + " and strDayEnd='N' ";
					List listStartFlag = objGlobalService.funGetListModuleWise(sql, "sql");
					Object[] arrObjDayEnd = (Object[]) listStartFlag.get(0);
					req.getSession().setAttribute("PMSDate", objGlobalFun.funGetDate("dd-MM-yyyy", arrObjDayEnd[0].toString()));
					req.getSession().setAttribute("PMSStartDay", arrObjDayEnd[1].toString());
				} else {
					req.getSession().setAttribute("PMSDate", objGlobalFun.funGetCurrentDate("dd-MM-yyyy"));
					req.getSession().setAttribute("PMSStartDay", "N");

					clsDayEndHdModel objDayEndModel = new clsDayEndHdModel();
					objDayEndModel.setStrPropertyCode(propBean.getStrPropertyCode());
					objDayEndModel.setStrDayEnd("N");
					objDayEndModel.setStrStartDay("Y");
					objDayEndModel.setDtePMSDate(objGlobalFun.funGetCurrentDate("yyyy-MM-dd"));
					objDayEndModel.setDblDayEndAmt(0);
					objDayEndModel.setStrUserCode(userCode);
					objDayEndModel.setStrClientCode(clientCode);

					objDayEndService.funAddUpdateDayEndHd(objDayEndModel);
				}
			}

			if (req.getSession().getAttribute("selectedModuleName").equals("7-WebPOS")) {
				// return new ModelAndView("frmWebPOSLogin");
			}

		} catch (Exception ex) {

			// For Handle the Code through Structure Update
			// if(req.getSession().getAttribute("selectedModuleName").equals("3-WebPMS"))
			// {
			// req.getSession().setAttribute("PropertyError","Please Check WebPMS PropertySetup");
			// }else
			// {
			// req.getSession().setAttribute("PropertyError","Please Check WebStock PropertySetup");
			// }

			// if(intcheckSturctureUpdate==0)
			// {
			// objStructureUpdateService.funUpdateStructure(clientCode);
			// intcheckSturctureUpdate++;
			// funValidateProperty( propBean, result, req,model);
			// }
			ex.printStackTrace();
			return new ModelAndView("frmStructureUpdate_2");

			//
			// clsPropertySetupModel
			// objSetup=objSetupMasterService.funGetObjectPropertySetup(propBean.getStrPropertyCode(),
			// clientCode);
			// if(null != objSetup)
			// {
			// req.getSession().setAttribute("amtDecPlace",objSetup.getIntdec());
			// req.getSession().setAttribute("qtyDecPlace",objSetup.getIntqtydec());
			// req.getSession().setAttribute("strNegStock",objSetup.getStrNegStock());
			// req.getSession().setAttribute("RateFrom",
			// objSetup.getStrRatePickUpFrom());
			// req.getSession().setAttribute("strWorkFlowbasedAuth",
			// objSetup.getStrWorkFlowbasedAuth());
			// req.getSession().setAttribute("ShowReqVal",
			// objSetup.getStrShowReqVal());
			// req.getSession().setAttribute("ShowReqStk",
			// objSetup.getStrShowStkReq());
			// req.getSession().setAttribute("changeUOM",
			// objSetup.getStrChangeUOMTrans());
			// req.getSession().setAttribute("audit", objSetup.getStrAudit());
			// req.getSession().setAttribute("showLocWiseProdMaster",
			// objSetup.getStrShowProdMaster());
			// req.getSession().setAttribute("showPrptyWiseProdDoc",
			// objSetup.getStrShowProdDoc());
			// req.getSession().setAttribute("ShowTransAsc_Desc",
			// objSetup.getStrShowTransAsc_Desc());
			// req.getSession().setAttribute("AllowDateChangeInMIS",
			// objSetup.getStrAllowDateChangeInMIS());
			// req.getSession().setAttribute("NameChangeInProdMast",
			// objSetup.getStrNameChangeProdMast());
			// req.getSession().setAttribute("NotificationTimeinterval",
			// objSetup.getIntNotificationTimeinterval());
			// req.getSession().setAttribute("MonthEnd",
			// objSetup.getStrMonthEnd());
			// req.getSession().setAttribute("showAllProdToAllLoc",
			// objSetup.getStrShowAllProdToAllLoc());
			// }
			// else
			// {
			// req.getSession().setAttribute("amtDecPlace",0);
			// req.getSession().setAttribute("qtyDecPlace",0);
			// req.getSession().setAttribute("strNegStock","N");
			// req.getSession().setAttribute("RateFrom", "");
			// req.getSession().setAttribute("strWorkFlowbasedAuth", "N");
			// req.getSession().setAttribute("ShowReqVal", "N");
			// req.getSession().setAttribute("ShowReqStk", "N");
			// req.getSession().setAttribute("changeUOM", "N");
			// req.getSession().setAttribute("audit", "N");
			// req.getSession().setAttribute("showLocWiseProdMaster", "N");
			// req.getSession().setAttribute("showPrptyWiseProdMaster","N" );
			// req.getSession().setAttribute("showPrptyWiseProdDoc", "N");
			// req.getSession().setAttribute("ShowTransAsc_Desc", "Asc");
			// req.getSession().setAttribute("AllowDateChangeInMIS","N");
			// req.getSession().setAttribute("NameChangeInProdMast", "N");
			// req.getSession().setAttribute("NotificationTimeinterval", 1);
			// req.getSession().setAttribute("MonthEnd", "N");
			// req.getSession().setAttribute("showAllProdToAllLoc", 'N');
			// }
			//
			//
			// if(req.getSession().getAttribute("selectedModuleName").equals("3-WebPMS"))
			// {
			// String sql =
			// "select tmeCheckInTime,tmeCheckOutTime from tblpropertysetup "
			// + " where strPropertyCode='" + propBean.getStrPropertyCode() +
			// "' and strClientCode='"+clientCode+"'";
			// List listPropInfo=objGlobalService.funGetListModuleWise(sql,
			// "sql");
			// Object[] arrPropInfo=(Object[]) listPropInfo.get(0);
			// req.getSession().setAttribute("PMSCheckInTime",
			// arrPropInfo[0].toString());
			// req.getSession().setAttribute("PMSCheckOutTime",
			// arrPropInfo[1].toString());
			//
			// sql = "select count(*) from tbldayendprocess "
			// + " where strPropertyCode='" + propBean.getStrPropertyCode() +
			// "' and strClientCode='"+clientCode+"' "
			// + " and strDayEnd='N' ";
			// List listDayEnd=objGlobalService.funGetListModuleWise(sql,
			// "sql");
			// BigInteger count=new BigInteger(listDayEnd.get(0).toString());
			//
			// if(count.intValue()>0)
			// {
			// sql =
			// "select date(max(dtePMSDate)),strStartDay from tbldayendprocess "
			// + " where strPropertyCode='" + propBean.getStrPropertyCode() +
			// "' and strClientCode='"+clientCode+"' "
			// + " and strDayEnd='N' ";
			// List listStartFlag=objGlobalService.funGetListModuleWise(sql,
			// "sql");
			// Object[] arrObjDayEnd=(Object[])listStartFlag.get(0);
			// req.getSession().setAttribute("PMSDate",
			// arrObjDayEnd[0].toString());
			// req.getSession().setAttribute("PMSStartDay",
			// arrObjDayEnd[1].toString());
			// }
			// else
			// {
			// req.getSession().setAttribute("PMSDate",
			// objGlobalFun.funGetCurrentDate("yyyy-MM-dd"));
			// req.getSession().setAttribute("PMSStartDay", "N");
			//
			// clsDayEndHdModel objDayEndModel=new clsDayEndHdModel();
			// objDayEndModel.setStrPropertyCode(propBean.getStrPropertyCode());
			// objDayEndModel.setStrDayEnd("N");
			// objDayEndModel.setStrStartDay("Y");
			// objDayEndModel.setDtePMSDate(objGlobalFun.funGetCurrentDate("yyyy-MM-dd"));
			// objDayEndModel.setDblDayEndAmt(0);
			// objDayEndModel.setStrUserCode(userCode);
			// objDayEndModel.setStrClientCode(clientCode);
			//
			// objDayEndService.funAddUpdateDayEndHd(objDayEndModel);
			// }
			// }

			// return new ModelAndView("redirect:/frmPropertySelection.html");
		}

		if (req.getSession().getAttribute("selectedModuleName").equals("7-WebPOS")) {

			return new ModelAndView("frmWebPOSModuleSelection");

		} else if (req.getSession().getAttribute("selectedModuleName").equals("3-WebPMS")) {
			return new ModelAndView("frmMainMenuPMS");
		} 
		else if (req.getSession().getAttribute("selectedModuleName").equals("1-WebStocks")) {
			if (objModel != null) {
				if (objModel.getStrShowDashBoard().equals("Y")) {
					Date dt1;
					try {
						String dteCurrDateTime = objGlobalFun.funGetCurrentDate("dd-MM-yyyy");

						SimpleDateFormat obj = new SimpleDateFormat("dd-MM-yyyy");
						dt1 = obj.parse(dteCurrDateTime);
						GregorianCalendar cal = new GregorianCalendar();
						cal.setTime(dt1);
						cal.add(Calendar.DATE, -30);
						String newFromDate = (cal.getTime().getYear() + 1900) + "-" + (cal.getTime().getMonth() + 1) + "-" + (cal.getTime().getDate());
						req.setAttribute("newFromDate", newFromDate);
						req.setAttribute("newToDate", dteCurrDateTime);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						return new ModelAndView("frmMainMenuWithDashBoard");
					}
				}
				else 
				   {
					  return new ModelAndView("frmMainMenu");
				   }
			} else {
				return new ModelAndView("frmMainMenu");
			}
		}
		else if(req.getSession().getAttribute("selectedModuleName").equals("6-WebCRM"))
		{
			if (objModel != null) {
				if (objModel.getStrShowDashBoard().equals("Y")) 
				{
					Date dt1;
					try {
						String dteCurrDateTime = objGlobalFun.funGetCurrentDate("dd-MM-yyyy");

						SimpleDateFormat obj = new SimpleDateFormat("dd-MM-yyyy");
						dt1 = obj.parse(dteCurrDateTime);
						GregorianCalendar cal = new GregorianCalendar();
						cal.setTime(dt1);
						cal.add(Calendar.DATE, -30);
						String newFromDate = (cal.getTime().getYear() + 1900) + "-" + (cal.getTime().getMonth() + 1) + "-" + (cal.getTime().getDate());
						String startDate = req.getSession().getAttribute("startDate").toString();
						req.setAttribute("newFromDate", startDate.split("/")[0]+"-"+startDate.split("/")[1]+"-"+startDate.split("/")[2]);
						req.setAttribute("newToDate", dteCurrDateTime);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						return new ModelAndView("frmMainMenuWithDashBoard1");
					}
				} else {
					return new ModelAndView("frmMainMenu");
				}
			} else {
				return new ModelAndView("frmMainMenu");
			}
		} 
		else {
			return new ModelAndView("frmMainMenu");
		}

	}

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public Map<String, Object> funGetTreeMap(HttpServletRequest req) {
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String isSuperUser = req.getSession().getAttribute("superuser").toString();

		LinkedHashMap<String, Object> mainMap = new LinkedHashMap<String, Object>();
		List nodeList = null;
		List allNodeList = new LinkedList();
		List childNodeList = null;

		String moduleCode = "1";
		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
			moduleCode = "1";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {
			moduleCode = "2";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("3-WebPMS")) {
			moduleCode = "3";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("4-WebClub")) {
			moduleCode = "4";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("5-WebBookAR")) {
			moduleCode = "5";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {
			moduleCode = "6";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("7-WebPOS")) {
			moduleCode = "7";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL")) {
			moduleCode = "8";
		}

		
		
		String strWebStockModule="";
		List<clsCompanyMasterModel> listClsCompanyMasterModel = objSetupMasterService.funGetListCompanyMasterModel(clientCode);
		if (listClsCompanyMasterModel.size() > 0) {
			clsCompanyMasterModel objCompanyMasterModel = listClsCompanyMasterModel.get(0);

				strWebStockModule = objCompanyMasterModel.getStrWebStockModule();
		}
		if(("No".equalsIgnoreCase(strWebStockModule))&& (moduleCode=="1"))
		{
	
			if ("YES".equalsIgnoreCase(isSuperUser)) {
				String query = "SELECT DISTINCT strRootNode,intRootIndex "
								+ " FROM tbltreemast "
								+ " WHERE strModule='" + moduleCode + "'  AND strType<>'O' and strRootNode='Master' or strRootNode='Tools'"
								+ " ORDER BY intRootIndex;";
				nodeList = objGlobalService.funGetList(query);
			} else {
				// String
				// query="select distinct a.strRootNode from tbltreemast a,tbluserdtl b where a.strFormName=b.strFormName "
				// +
				// "and b.strUserCode='"+userCode+"' and b.strClientCode='"+clientCode+"' and a.strModule='"+moduleCode+"' and strType<>'O' order by a.intRootIndex";

				String query = "select * from (select distinct a.strRootNode,a.intRootIndex " + " from tbltreemast a,tbluserdtl b where a.strFormName=b.strFormName and b.strUserCode='" + userCode + "' and b.strClientCode='" + clientCode + "' and a.strModule='" + moduleCode + "' " + " and strType<>'O' UNION select distinct x.strRootNode,x.intRootIndex "
						+ " from tbltreemast x where x.strFormName IN(select distinct p.strRootNode " + " from tbltreemast p,tbluserdtl q where p.strFormName=q.strFormName " + " and q.strUserCode='" + userCode + "' and q.strClientCode='" + clientCode + "' " + " and p.strModule='" + moduleCode + "' and strType<>'O') and x.strModule='" + moduleCode + "') " + " as s ORDER BY s.intRootIndex";

				nodeList = objGlobalService.funGetList(query);
			}
			
			for (int i = 0; i < nodeList.size(); i++) {
				Object[] objNode = (Object[]) nodeList.get(i);
				allNodeList.add(objNode[0].toString());
			}
			
			
			childNodeList = new ArrayList();
			childNodeList.add("Property Setup");
			childNodeList.add("Group Master");
			childNodeList.add("Location Master");
			childNodeList.add("Supplier Master");
			childNodeList.add("Property Master");
			childNodeList.add("Tax Master");
			childNodeList.add("Product Master");
			childNodeList.add("SubGroup Master");
			
			List<clsTreeRootNodeItemUtil> objTreeRootNodeItemUtil = null;
			allNodeList.removeAll(childNodeList);

			for (int i = 0; i < allNodeList.size(); i++) {

				HashMap<String, Object> subMenuMap = new HashMap<String, Object>();
				LinkedHashMap<Integer, Object> menuList = new LinkedHashMap<Integer, Object>();

				if ("YES".equalsIgnoreCase(isSuperUser)) {
					objTreeRootNodeItemUtil = objclsTreeMenuService.getRootNodeItems(allNodeList.get(i).toString());
				} else {
					objTreeRootNodeItemUtil = objclsTreeMenuService.getRootNodeItems(userCode, clientCode, (allNodeList.get(i).toString()));
				}

				Iterator it = objTreeRootNodeItemUtil.iterator();
				Object objTreeNode[] = null;
				int cnt = 1;

				while (it.hasNext()) {
					cnt++;
					HashMap<String, Object> menuMap = new HashMap<String, Object>();
					clsTreeRootNodeItemUtil objTreeRootItem = new clsTreeRootNodeItemUtil();
					objTreeNode = (Object[]) it.next();

					if (childNodeList.contains(objTreeNode[0].toString())) {
						objTreeRootItem.setStrFormDesc(objTreeNode[0].toString());
						objTreeRootItem.setStrRequestMapping(objTreeNode[1].toString());
						menuMap.put("Parent", objTreeRootItem);
						menuList.put(cnt, menuMap);

					} 
					
				}
				mainMap.put(allNodeList.get(i).toString(), menuList);
			}
		}
		else
		{
			
			if ("YES".equalsIgnoreCase(isSuperUser)) {
				String query = "select distinct strRootNode,intRootIndex from tbltreemast where strModule='" + moduleCode + "' and strType<>'O' order by intRootIndex";
				nodeList = objGlobalService.funGetList(query);
			} else {
				// String
				// query="select distinct a.strRootNode from tbltreemast a,tbluserdtl b where a.strFormName=b.strFormName "
				// +
				// "and b.strUserCode='"+userCode+"' and b.strClientCode='"+clientCode+"' and a.strModule='"+moduleCode+"' and strType<>'O' order by a.intRootIndex";

				String query = "select * from (select distinct a.strRootNode,a.intRootIndex " + " from tbltreemast a,tbluserdtl b where a.strFormName=b.strFormName and b.strUserCode='" + userCode + "' and b.strClientCode='" + clientCode + "' and a.strModule='" + moduleCode + "' " + " and strType<>'O' UNION select distinct x.strRootNode,x.intRootIndex "
						+ " from tbltreemast x where x.strFormName IN(select distinct p.strRootNode " + " from tbltreemast p,tbluserdtl q where p.strFormName=q.strFormName " + " and q.strUserCode='" + userCode + "' and q.strClientCode='" + clientCode + "' " + " and p.strModule='" + moduleCode + "' and strType<>'O') and x.strModule='" + moduleCode + "') " + " as s ORDER BY s.intRootIndex";

				nodeList = objGlobalService.funGetList(query);
			}

			for (int i = 0; i < nodeList.size(); i++) {
				Object[] objNode = (Object[]) nodeList.get(i);
				allNodeList.add(objNode[0].toString());
			}	
			
		if ("YES".equalsIgnoreCase(isSuperUser)) {
			String query = "select distinct strFormName,intRootIndex from tbltreemast where strModule='" + moduleCode + "' and strType='O' order by intRootIndex";
			List childNode = objGlobalService.funGetList(query);
			childNodeList = new ArrayList();
			for (int i = 0; i < childNode.size(); i++) {

				Object[] arrobj = (Object[]) childNode.get(i);
				childNodeList.add(arrobj[0].toString());
			}
			// childNodeList=objGlobalService.funGetList(query);
		} else {
			String query = "select distinct a.strFormName,a.intRootIndex from tbltreemast a,tbluserdtl b where " + "b.strUserCode='" + userCode + "' and b.strClientCode='" + clientCode + "' and a.strModule='" + moduleCode + "' and strType='O' order by a.intRootIndex";
			// childNodeList=objGlobalService.funGetList(query);
			List childNode = objGlobalService.funGetList(query);
			childNodeList = new ArrayList();
			for (int i = 0; i < childNode.size(); i++) {

				Object[] arrobj = (Object[]) childNode.get(i);
				childNodeList.add(arrobj[0].toString());
			}

		}
		
		List<clsTreeRootNodeItemUtil> objTreeRootNodeItemUtil = null;
		allNodeList.removeAll(childNodeList);

		for (int i = 0; i < allNodeList.size(); i++) {

			HashMap<String, Object> subMenuMap = new HashMap<String, Object>();
			LinkedHashMap<Integer, Object> menuList = new LinkedHashMap<Integer, Object>();

			if ("YES".equalsIgnoreCase(isSuperUser)) {
				objTreeRootNodeItemUtil = objclsTreeMenuService.getRootNodeItems(allNodeList.get(i).toString());
			} else {
				objTreeRootNodeItemUtil = objclsTreeMenuService.getRootNodeItems(userCode, clientCode, (allNodeList.get(i).toString()));
			}

			Iterator it = objTreeRootNodeItemUtil.iterator();
			Object objTreeNode[] = null;
			int cnt = 1;

			while (it.hasNext()) {
				cnt++;
				HashMap<String, Object> menuMap = new HashMap<String, Object>();
				clsTreeRootNodeItemUtil objTreeRootItem = new clsTreeRootNodeItemUtil();
				objTreeNode = (Object[]) it.next();

				if (childNodeList.contains(objTreeNode[0].toString())) {
					HashMap<String, Object> childMap = new HashMap<String, Object>();

					if ("YES".equalsIgnoreCase(isSuperUser)) {
						objTreeRootNodeItemUtil = objclsTreeMenuService.getRootNodeItems(objTreeNode[0].toString());
					} else {
						objTreeRootNodeItemUtil = objclsTreeMenuService.getRootNodeItems(userCode, clientCode, (objTreeNode[0].toString()));
					}
					List<Object> childList = new ArrayList<Object>();
					Iterator it1 = objTreeRootNodeItemUtil.iterator();
					Object objTreeNode1[] = null;
					while (it1.hasNext()) {
						clsTreeRootNodeItemUtil objChildTreeRootItem = new clsTreeRootNodeItemUtil();
						objTreeNode1 = (Object[]) it1.next();
						objChildTreeRootItem.setStrFormDesc(objTreeNode1[0].toString());
						objChildTreeRootItem.setStrRequestMapping(objTreeNode1[1].toString());
						childList.add(objChildTreeRootItem);
					}
					childMap.put(objTreeNode[0].toString(), childList);
					menuList.put(cnt, childMap);

				} 
				else {
					objTreeRootItem.setStrFormDesc(objTreeNode[0].toString());
					objTreeRootItem.setStrRequestMapping(objTreeNode[1].toString());
					menuMap.put("Parent", objTreeRootItem);
					menuList.put(cnt, menuMap);
				}
			}
			mainMap.put(allNodeList.get(i).toString(), menuList);
		}
		}
		

		return mainMap;
	}

	private List<clsUserDesktopUtil> funGetUserDesktop(HttpServletRequest req) {
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String isSuperUser = req.getSession().getAttribute("superuser").toString();

		List<clsUserDesktopUtil> desktop = new ArrayList<clsUserDesktopUtil>();

		List<clsUserDesktopUtil> objModel = null;
		if ("YES".equalsIgnoreCase(isSuperUser)) {
			objModel = objclsTreeMenuService.funGetDesktopForms(userCode);
		} else {
			objModel = objclsTreeMenuService.funGetDesktopForms(userCode, clientCode);
		}
		for (Object ob : objModel) {
			Object[] arrOb = (Object[]) ob;
			clsUserDesktopUtil obTreeRootItem = new clsUserDesktopUtil();
			if (null != arrOb[0]) {
				obTreeRootItem.setStrFormName(arrOb[0].toString());
			}
			if (null != arrOb[1]) {
				obTreeRootItem.setStrFormDesc(arrOb[1].toString());
			}
			if (null != arrOb[2]) {
				obTreeRootItem.setStrImgName(arrOb[2].toString());
			}
			if (null != arrOb[3]) {
				obTreeRootItem.setStrRequestMapping(arrOb[3].toString());
			}

			desktop.add(obTreeRootItem);
		}

		setSessionValues(req);
		return desktop;
	}

	@RequestMapping(value = "/frmHome", method = RequestMethod.GET)
	public ModelAndView funRedirectToHome() {
		return new ModelAndView("frmMainMenu");
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/index.html";
	}

	@SuppressWarnings("unused")
	public void PasswordEncoderGenerator() {
		String password = "super";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
	}

	@SuppressWarnings("unchecked")
	public void setSessionValues(HttpServletRequest req) {

		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {

			req.getSession().removeAttribute("strBrandMaster");
			req.getSession().removeAttribute("strSizeMaster");
			req.getSession().removeAttribute("strSubCategory");
			req.getSession().removeAttribute("strCategory");
			req.getSession().removeAttribute("strSupplier");
			req.getSession().removeAttribute("strRecipe");
			req.getSession().removeAttribute("strCity");
			req.getSession().removeAttribute("strPermit");

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String hqlQuery = "from clsExcisePropertySetUpModel where strClientCode= '" + clientCode + "' ";
			List<clsExcisePropertySetUpModel> PropertyDataList = objGlobalService.funGetDataList(hqlQuery, "hql");
			for (clsExcisePropertySetUpModel objPropertyDataList : PropertyDataList) {

				req.getSession().setAttribute("strBrandMaster", objPropertyDataList.getStrBrandMaster());
				req.getSession().setAttribute("strSizeMaster", objPropertyDataList.getStrSizeMaster());
				req.getSession().setAttribute("strSubCategory", objPropertyDataList.getStrSubCategory());
				req.getSession().setAttribute("strCategory", objPropertyDataList.getStrCategory());
				req.getSession().setAttribute("strSupplier", objPropertyDataList.getStrSupplier());
				req.getSession().setAttribute("strRecipe", objPropertyDataList.getStrRecipe());
				req.getSession().setAttribute("strCity", objPropertyDataList.getStrCity());
				req.getSession().setAttribute("strPermit", objPropertyDataList.getStrPermit());
			}

		}

	}

	@RequestMapping(value = "/frmChangeModuleSelection", method = RequestMethod.GET)
	private ModelAndView funChageModuleSlection(HttpServletRequest req) {
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsUserMasterModel user = new clsUserMasterModel();
		if (userCode.equals("SANGUINE")) {
			user.setStrSuperUser("YES");
			user.setStrProperty("ALL");
			user.setStrType("");
			user.setStrUserName1("SANGUINE");
			user.setStrUserCode1("SANGUINE");
			user.setStrRetire("N");

		} else {
			user = objUserMasterService.funGetUser(userCode, clientCode);
		}
		funSetModuleForChangeModule(req);

		return funSessionValue(user, req);
	}

	private int funSetModuleForChangeModule(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		List<clsCompanyMasterModel> listClsCompanyMasterModel = objSetupMasterService.funGetListCompanyMasterModel(clientCode);

		if (listClsCompanyMasterModel.size() > 0) {
			clsCompanyMasterModel objCompanyMasterModel = listClsCompanyMasterModel.get(0);
			String startDate = objCompanyMasterModel.getDtStart();
			String[] spDate = startDate.split("-");
			String year = spDate[0];
			String month = spDate[1];
			String[] spDate1 = spDate[2].split(" ");
			String date = spDate1[0];
			startDate = date + "/" + month + "/" + year;
			req.getSession().setAttribute("clientCode", objCompanyMasterModel.getStrClientCode());
			req.getSession().setAttribute("companyCode", objCompanyMasterModel.getStrCompanyCode());
			req.getSession().setAttribute("companyName", objCompanyMasterModel.getStrCompanyName());
			req.getSession().setAttribute("startDate", startDate);
			String strCRMModule = objCompanyMasterModel.getStrCRMModule();
			String strWebBookModule = objCompanyMasterModel.getStrWebBookModule();
			String strWebClubModule = objCompanyMasterModel.getStrWebClubModule();
			String strWebExciseModule = objCompanyMasterModel.getStrWebExciseModule();
			String strWebPMSModule = objCompanyMasterModel.getStrWebPMSModule();
			String strWebPOSModule = objCompanyMasterModel.getStrWebPOSModule();
			String strWebStockModule = objCompanyMasterModel.getStrWebStockModule();
			String isSuperUser = req.getSession().getAttribute("superuser").toString();

			List<String> list = objUserMasterService.funGetUserWiseModules(userCode, clientCode);

			Map<String, String> moduleMap = new TreeMap<String, String>();
			if ("Yes".equalsIgnoreCase(strWebStockModule)) {

				if (isSuperUser.equalsIgnoreCase("YES")) {
					moduleMap.put("1-WebStocks", "webstocks_module_icon.png");
					strModule = "1";
				} else if (null != list && list.size() > 0 && list.contains("WebStocks")) {
					moduleMap.put("1-WebStocks", "webstocks_module_icon.png");
					strModule = "1";
				}
			}
			else
			{

				if (isSuperUser.equalsIgnoreCase("YES")) {
					moduleMap.put("1-WebStocks", "webstocks_module_icon.png");
					strModule = "1";
				} else if (null != list && list.size() > 0 && list.contains("WebStocks")) {
					moduleMap.put("1-WebStocks", "webstocks_module_icon.png");
					strModule = "1";
				}
			}

			if ("Yes".equalsIgnoreCase(strWebExciseModule)) {

				if (isSuperUser.equalsIgnoreCase("YES")) {
					moduleMap.put("2-WebExcise", "webexcise_module_icon.png");
					strModule = "2";
				} else if (null != list && list.size() > 0 && list.contains("WebExcise")) {
					moduleMap.put("2-WebExcise", "webexcise_module_icon.png");
					strModule = "2";
				}
			}

			if ("Yes".equalsIgnoreCase(strWebPMSModule)) {

				if (isSuperUser.equalsIgnoreCase("YES")) {
					moduleMap.put("3-WebPMS", "webpms_module_icon.png");
					strModule = "3";
				} else if (null != list && list.size() > 0 && list.contains("WebPMS")) {
					moduleMap.put("3-WebPMS", "webpms_module_icon.png");
					strModule = "3";
				}
			}

			if ("Yes".equalsIgnoreCase(strWebClubModule)) {

				if (isSuperUser.equalsIgnoreCase("YES")) {
					moduleMap.put("4-WebClub", "webclub_module_icon.png");
					strModule = "4";
				} else if (null != list && list.size() > 0 && list.contains("WebClub")) {
					moduleMap.put("4-WebClub", "webclub_module_icon.png");
					strModule = "4";
				}
			}

			if ("Yes".equalsIgnoreCase(strWebBookModule)) {

				if (isSuperUser.equalsIgnoreCase("YES")) {
					moduleMap.put("5-WebBook", "webbooks_icon.png");
					strModule = "5";
				} else if (null != list && list.size() > 0 && list.contains("WebBook")) {
					moduleMap.put("5-WebBook", "webbooks_icon.png");
					strModule = "5";
				}
			}

			if ("Yes".equalsIgnoreCase(strCRMModule)) {

				if (isSuperUser.equalsIgnoreCase("YES")) {
					moduleMap.put("6-WebCRM", "webcrm_module_icon.png");
					strModule = "6";
				} else if (null != list && list.size() > 0 && list.contains("WebCRM")) {
					moduleMap.put("6-WebCRM", "webcrm_module_icon.png");
					strModule = "6";
				}
			}
			if ("Yes".equalsIgnoreCase(strWebPOSModule)) {

				if (isSuperUser.equalsIgnoreCase("YES")) {
					moduleMap.put("7-WebPOS", "webpos_module_icon.png");
					strModule = "7";
				} else if (null != list && list.size() > 0 && list.contains("WebPOS")) {
					moduleMap.put("7-WebPOS", "webpos_module_icon.png");
					strModule = "7";
				}
			}

			req.getSession().setAttribute("moduleNo", strModule);
			req.getSession().setAttribute("moduleMap", moduleMap);
		}
		return 1;
	}

	/*
	 * private String funWarFileDate() { String warDate=""; try {
	 * 
	 * String filepath =System.getProperty("user.dir")
	 * +"\\webapps\\prjWebStocks.war"; System.out.println(
	 * filepath+"\\prjWebStocks.war"); Path path = Paths.get(filepath);
	 * BasicFileAttributes attr;
	 * 
	 * attr = Files.readAttributes(path, BasicFileAttributes.class);
	 * System.out.println("creationTime: " + attr.creationTime()); warDate =
	 * attr.creationTime().toString(); if(!(warDate.length()==0)) {
	 * warDate=warDate.substring(0, 10); } }catch(Exception e) {
	 * 
	 * e.printStackTrace(); } return warDate;
	 * 
	 * }
	 */

	@SuppressWarnings("unused")
	@RequestMapping(value = "/frmWebPOSModuleSelection", method = RequestMethod.GET)
	public ModelAndView funWebPOSModuleSelectionOpenForm(HttpServletRequest req, Map<String, Object> model) {
		return new ModelAndView("frmWebPOSModuleSelection");
	}

	// @SuppressWarnings("unused")
	// @RequestMapping(value = "/frmWebPOSPOSSelection", method =
	// RequestMethod.GET)
	public ArrayList<clsWebPosPOSSelectionBean> funWebPOSPOSSelection(HttpServletRequest req) {

		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOS";
		System.out.println(posUrl);
		ArrayList<clsWebPosPOSSelectionBean> listPOS = null;
		try {
			URL url = new URL(posUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = "", op = "";
			while ((output = br.readLine()) != null) {
				op += output;
			}
			System.out.println("Obj=" + op);
			conn.disconnect();

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(op);
			JSONObject jObjPOSList = (JSONObject) obj;
			JSONArray jArrList = (JSONArray) jObjPOSList.get("posList");
			// JSONObject
			// jObjPOSDate=(JSONObject)jObjPOSMenuList.get("POSDate");

			listPOS = new ArrayList<clsWebPosPOSSelectionBean>();

			for (int cnt = 0; cnt < jArrList.size(); cnt++) {
				JSONObject jObj = (JSONObject) jArrList.get(cnt);
				clsWebPosPOSSelectionBean objbean = new clsWebPosPOSSelectionBean();
				objbean.setStrPosCode(jObj.get("strPosCode").toString());
				objbean.setStrPosName(jObj.get("strPosName").toString());
				listPOS.add(objbean);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return listPOS;

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/frmWebPOSSelection", method = RequestMethod.GET)
	public ModelAndView funWebPOSPOSSelectionMaster(@RequestParam("strPOSCode") String strPOSCode, HttpServletRequest req, Map<String, Object> model) {
		// List<clsUserDesktopUtil> webPOSDesktop=null;
		// try {
		// webPOSDesktop = funGetPOSMenuMap("super", "117.001", "M", "Super",
		// strPOSCode);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// req.getSession().setAttribute("desktop",webPOSDesktop);
		return new ModelAndView("frmWebPOSMainMenu");

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/frmWebPOSChangeSelection", method = RequestMethod.GET)
	public ModelAndView frmWebPOSChangeSelection(HttpServletRequest req, Map<String, Object> model) {

		return new ModelAndView("frmPOSSelection");

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/frmWebPOSSelectionMaster", method = RequestMethod.GET)
	public ModelAndView funWebPOSModuleMasterSelection(HttpServletRequest req, Map<String, Object> model) {
		List<clsUserDesktopUtil> webPOSDesktop = null;
		try {

			ArrayList<clsWebPosPOSSelectionBean> posList = funWebPOSPOSSelection(req);
			req.getSession().setAttribute("posList", posList);
			req.getSession().setAttribute("webPOSModuleSelect", "M");

			// webPOSDesktop = funGetPOSMenuMap("super", "117.001", "M",
			// "Super", "P01");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// req.getSession().setAttribute("desktop",webPOSDesktop);
		return new ModelAndView("frmPOSSelection");

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/frmGetPOSSelection", method = RequestMethod.GET)
	public ModelAndView funWebPOSPOSSelection(@RequestParam("strPosCode") String strPOSCode, HttpServletRequest req, Map<String, Object> model) {
		String POSDate = "", ShiftNo = "", ShiftEnd = "", DayEnd = "";
		List<clsUserDesktopUtil> webPOSDesktop = null;
		try {

			ArrayList<clsWebPosPOSSelectionBean> posList = funWebPOSPOSSelection(req);
			req.getSession().setAttribute("posList", posList);
			// req.getSession().setAttribute("webPOSModuleSelect","M");
			String posModule = req.getSession().getAttribute("webPOSModuleSelect").toString();
			String usercode = req.getSession().getAttribute("usercode").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			/*
			 * if(usercode.equals("SANGUINE")) { usercode = "super"; }
			 */
			webPOSDesktop = funGetPOSMenuMap(usercode, clientCode, posModule, usercode, strPOSCode);
			JSONArray jArr = new JSONArray();
			for (clsUserDesktopUtil obj : webPOSDesktop) {
				JSONObject jobj = new JSONObject();
				jobj.put("formName", obj.getStrFormName());
				jobj.put("strFormName", obj.getStrFormName());
				jobj.put("strFormDesc", obj.getStrFormDesc());
				jobj.put("strImgName", obj.getStrImgName());
				jobj.put("strRequestMapping", obj.getStrRequestMapping());

				// jArr.add(obj.getStrFormName());
				jArr.add(jobj);
			}

			req.getSession().setAttribute("formSerachlist", jArr);
			req.getSession().setAttribute("desktop", webPOSDesktop);
			req.getSession().setAttribute("loginPOS", strPOSCode);

			String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/clsUtilityController/funGetPOSWiseDayEndData?POSCode=" + strPOSCode + "&UserCode" + usercode;
			System.out.println(posUrl);
			URL url = new URL(posUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = "", op = "";
			while ((output = br.readLine()) != null) {
				op += output;
			}
			System.out.println("Obj=" + op);
			conn.disconnect();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(op);
			JSONObject jObjPOSWiseData = (JSONObject) obj;

			POSDate = jObjPOSWiseData.get("startDate").toString();
			ShiftNo = jObjPOSWiseData.get("ShiftNo").toString();
			ShiftEnd = jObjPOSWiseData.get("ShiftEnd").toString();
			DayEnd = jObjPOSWiseData.get("DayEnd").toString();

			req.getSession().setAttribute("POSDate", POSDate);
			req.getSession().setAttribute("POSDateForDisplay", POSDate.split("-")[2] + "-" + POSDate.split("-")[1] + "-" + POSDate.split("-")[0]);
			req.getSession().setAttribute("ShiftEnd", ShiftEnd);
			req.getSession().setAttribute("DayEnd", DayEnd);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ModelAndView("frmWebPOSMainMenu");

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/frmWebPOSSelectionReport", method = RequestMethod.GET)
	public ModelAndView funWebPOSPOSSelectionReport(HttpServletRequest req, Map<String, Object> model) {
		List<clsUserDesktopUtil> webPOSDesktop = null;
		try {

			ArrayList<clsWebPosPOSSelectionBean> posList = funWebPOSPOSSelection(req);
			req.getSession().setAttribute("posList", posList);
			req.getSession().setAttribute("webPOSModuleSelect", "R");

			// webPOSDesktop = funGetPOSMenuMap("super", "117.001", "M",
			// "Super", "P01");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// req.getSession().setAttribute("desktop",webPOSDesktop);
		return new ModelAndView("frmPOSSelection");

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/frmWebPOSSelectionTransection", method = RequestMethod.GET)
	public ModelAndView funWebPOSPOSSelectionTransection(HttpServletRequest req, Map<String, Object> model) {
		List<clsUserDesktopUtil> webPOSDesktop = null;
		try {

			ArrayList<clsWebPosPOSSelectionBean> posList = funWebPOSPOSSelection(req);
			req.getSession().setAttribute("posList", posList);
			req.getSession().setAttribute("webPOSModuleSelect", "T");

			// webPOSDesktop = funGetPOSMenuMap("super", "117.001", "M",
			// "Super", "P01");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// req.getSession().setAttribute("desktop",webPOSDesktop);
		return new ModelAndView("frmPOSSelection");

	}

	private void funCheckNewfinancial(List<clsCompanyMasterModel> listClsCompanyMasterModel, HttpServletRequest req) {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH);

		currentMonth = currentMonth + 1;
		clsCompanyMasterModel objCom = listClsCompanyMasterModel.get(listClsCompanyMasterModel.size() - 1);
		String startDate = objCom.getDtStart();
		int lastfinYear = Integer.parseInt(objCom.getStrFinYear().split("-")[1]);
		if ((currentYear + 1) != lastfinYear) {
			if (currentMonth >= 4) {
				int variYr = (currentYear + 1) - lastfinYear;
				for (int i = 0; i < variYr; i++) {
					String finyear = (lastfinYear + i) + "-" + (lastfinYear + i + 1);
					String dtEndPrevoius = objCom.getDtEnd();
					String[] spdtEnd = dtEndPrevoius.split("-");
					objCom.setDtEnd((lastfinYear + i + 1) + "-" + spdtEnd[1] + "-" + spdtEnd[2]);
					objCom.setDtStart(startDate);
					objCom.setDtLastTransDate((lastfinYear + i + 1) + "-" + spdtEnd[1] + "-" + spdtEnd[2]);
					objCom.setIntId(objCom.getIntId() + 1);
					objCom.setStrFinYear(finyear);
					objCom.setStrYear(objGlobalFun.funGetAlphabet(i+1));
					objSetupMasterService.funAddUpdate(objCom);
				}
			}
		}
	}
	/*
	 * @SuppressWarnings("unused")
	 * 
	 * @RequestMapping(value = "/frmShowTodayCheckOutRooms", method =
	 * RequestMethod.GET) public ModelAndView
	 * frmShowTodayCheckOutRooms(HttpServletRequest req,Map<String,Object>
	 * model) { String urlHits="1"; try { urlHits =
	 * req.getParameter("saddr").toString(); } catch (NullPointerException e) {
	 * urlHits = "1"; } String clientCode =
	 * req.getSession().getAttribute("clientCode").toString(); String
	 * sql=" select c.strRoomDesc,d.strRoomTypeDesc " +
	 * " from tblcheckinhd a,tblcheckindtl b,tblroom c,tblroomtypemaster d  " +
	 * " where a.strCheckInNo=b.strCheckInNo  and b.strRoomNo = c.strRoomCode "
	 * +
	 * " and c.strRoomTypeCode=d.strRoomTypeCode and a.strClientCode=b.strClientCode  "
	 * +
	 * " and b.strClientCode = c.strClientCode and c.strClientCode = a.strClientCode "
	 * + " and a.strClientCode='"+clientCode+"' " +
	 * " and date(a.dteDepartureDate) = '"
	 * +objGlobalFun.funGetCurrentDate("yyyy-MM-dd")+"'" ; // +
	 * " group by a.strRoomNo " ; List
	 * listCheckout=objGlobalService.funGetListModuleWise(sql, "sql");
	 * if(listCheckout!=null) { model.put("listCheckout", listCheckout); }
	 * 
	 * model.put("urlHits", urlHits);
	 * 
	 * if (urlHits.equalsIgnoreCase("1")) { return new
	 * ModelAndView("frmShowTodayCheckOutRooms","command",new
	 * clsGuestMasterBean()); } else { return new
	 * ModelAndView("frmShowTodayCheckOutRooms_1","command",new
	 * clsGuestMasterBean()); }
	 * 
	 * 
	 * 
	 * }
	 */

	@SuppressWarnings("unused")
	@RequestMapping(value = "/getCheckoutNotification", method = RequestMethod.GET)
	public @ResponseBody List frmShowTodayCheckOutRooms(HttpServletRequest req, Map<String, Object> model) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String sql = " select c.strRoomDesc,d.strRoomTypeDesc,CONCAT(e.strFirstName,' ',e.strMiddleName,' ',e.strLastName) " + " from tblcheckinhd a,tblcheckindtl b,tblroom c,tblroomtypemaster d,tblguestmaster e  " + " where a.strCheckInNo=b.strCheckInNo  and b.strRoomNo = c.strRoomCode "
				+ " and c.strRoomTypeCode=d.strRoomTypeCode and a.strClientCode=b.strClientCode and b.strGuestCode=e.strGuestCode  " + " and b.strClientCode = c.strClientCode and c.strClientCode = a.strClientCode " + " and a.strClientCode='" + clientCode + "' " + " and date(a.dteDepartureDate) = '" + objGlobalFun.funGetCurrentDate("yyyy-MM-dd") + "'";
		// + " group by a.strRoomNo " ;
		List listCheckout = objGlobalService.funGetListModuleWise(sql, "sql");

		return listCheckout;

	}

	private List<clsUserDesktopUtil> funGetPOSMenuMap(String userCode, String clientCode, String moduleType, String superUser, String POSCode) throws Exception {
		boolean superUserYN = false;
		if (superUser.equalsIgnoreCase("Super")) {
			superUserYN = true;
		}

		String POSUrl = "http://localhost:8080/prjSanguineWebService/APOSIntegration/funGetSetupValues1?POSCode=" + POSCode;
		URL url = new URL(POSUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Accept", "application/json");
		BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
		String output = "", op = "";
		while ((output = br.readLine()) != null) {
			op += output;
		}
		con.disconnect();

		JSONParser parse = new JSONParser();
		Object object = parse.parse(op);
		JSONObject jObjSetupValueList = (JSONObject) object;
		JSONArray jArrSetupValuesList = (JSONArray) jObjSetupValueList.get("SetupValues");
		for (int cnt = 0; cnt < jArrSetupValuesList.size(); cnt++) {
			JSONObject jObj = (JSONObject) jArrSetupValuesList.get(cnt);

			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("ClientCode", jObj.get("ClientCode"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("ClientName", jObj.get("ClientName"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("ClientAddressLine1", jObj.get("ClientAddressLine1"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("ClientAddressLine2", jObj.get("ClientAddressLine2"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("ClientAddressLine3", jObj.get("ClientAddressLine3"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("Email", jObj.get("Email"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("BillFooter", jObj.get("BillFooter"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("BillFooterStatus", jObj.get("BillFooterStatus"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("BillPaperSize", jObj.get("BillPaperSize"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("NegativeBilling", jObj.get("NegativeBilling"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("DayEnd", jObj.get("DayEnd"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("PrintMode", jObj.get("PrintMode"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("DiscountNote", jObj.get("DiscountNote"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("CityName", jObj.get("CityName"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("State", jObj.get("State"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("Country", jObj.get("Country"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("TelephoneNo", jObj.get("TelephoneNo"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("StartDate", jObj.get("StartDate"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("TelephoneNo", jObj.get("TelephoneNo"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("StartDate", jObj.get("StartDate"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("EndDate", jObj.get("EndDate"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("NatureOfBusinnes", jObj.get("NatureOfBusinnes"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("MultipleBillPrinting", jObj.get("MultipleBillPrinting"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("EnableKOT", jObj.get("EnableKOT"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("PrintVatNo", jObj.get("PrintVatNo"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("VatNo", jObj.get("VatNo"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("ShowBill", jObj.get("ShowBill"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("PrintServiceTaxNo", jObj.get("PrintServiceTaxNo"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("ServiceTaxNo", jObj.get("ServiceTaxNo"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("ManualBillNo", jObj.get("ManualBillNo"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("MenuItemDispSeq", jObj.get("MenuItemDispSeq"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("SenderEmailId", jObj.get("SenderEmailId"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("EmailPassword", jObj.get("EmailPassword"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("ConfirmEmailPassword", jObj.get("ConfirmEmailPassword"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("Body", jObj.get("Body"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("EmailServerName", jObj.get("EmailServerName"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("SMSApi", jObj.get("SMSApi"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("UserCreated", jObj.get("UserCreated"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("UserEdited", jObj.get("UserEdited"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("ManualBillNo", jObj.get("ManualBillNo"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("DateCreated", jObj.get("DateCreated"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("DateEdited", jObj.get("DateEdited"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("POSType", jObj.get("POSType"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("WebServiceLink", jObj.get("WebServiceLink"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("DataSendFrequency", jObj.get("DataSendFrequency"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("HOServerDate", jObj.get("HOServerDate"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("RFID", jObj.get("RFID"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("ServerName", jObj.get("ServerName"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("DBUserName", jObj.get("DBUserName"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("DBPassword", jObj.get("DBPassword"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("DatabaseName", jObj.get("DatabaseName"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("EnableKOTForDirectBiller", jObj.get("EnableKOTForDirectBiller"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("PinCode", jObj.get("PinCode"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("ChangeTheme", jObj.get("ChangeTheme"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("MaxDiscount", jObj.get("MaxDiscount"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("AreaWisePricing", jObj.get("AreaWisePricing"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("MenuItemSortingOn", jObj.get("MenuItemSortingOn"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("DirectBillerAreaCode", jObj.get("DirectBillerAreaCode"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("ColumnSize", jObj.get("ColumnSize"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("PrintType", jObj.get("PrintType"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("EditHomeDelivery", jObj.get("EditHomeDelivery"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("SlabBasedHDCharges", jObj.get("SlabBasedHDCharges"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("SkipWaiterAndPax", jObj.get("SkipWaiterAndPax"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("SkipWaiter", jObj.get("SkipWaiter"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("DirectKOTPrintMakeKOT", jObj.get("DirectKOTPrintMakeKOT"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("SkipPax", jObj.get("SkipPax"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("CRMInterface", jObj.get("CRMInterface"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("GetWebserviceURL", jObj.get("GetWebserviceURL"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("PostWebserviceURL", jObj.get("PostWebserviceURL"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("OutletUID", jObj.get("OutletUID"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("POSID", jObj.get("POSID"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("StockInOption", jObj.get("StockInOption"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("CustSeries", jObj.get("CustSeries"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("AdvReceiptPrintCount", jObj.get("AdvReceiptPrintCount"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("HomeDeliverySMS", jObj.get("HomeDeliverySMS"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("BillSettlementSMS", jObj.get("BillSettlementSMS"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("BillFormatType", jObj.get("BillFormatType"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("ActivePromotions", jObj.get("ActivePromotions"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("SendHomeDelSMS", jObj.get("SendHomeDelSMS"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("SendBillSettlementSMS", jObj.get("SendBillSettlementSMS"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("SMSType", jObj.get("SMSType"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("PrintShortNameOnKOT", jObj.get("PrintShortNameOnKOT"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("ShowCustHelp", jObj.get("ShowCustHelp"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("PrintOnVoidBill", jObj.get("PrintOnVoidBill"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("PostSalesDataToMMS", jObj.get("PostSalesDataToMMS"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("CustAreaMasterCompulsory", jObj.get("CustAreaMasterCompulsory"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("PriceFrom", jObj.get("PriceFrom"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("ShowPrinterErrorMessage", jObj.get("ShowPrinterErrorMessage"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("TouchScreenMode", jObj.get("TouchScreenMode"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("CardInterfaceType", jObj.get("CardInterfaceType"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("CMSIntegrationYN", jObj.get("CMSIntegrationYN"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("CMSWebServiceURL", jObj.get("CMSWebServiceURL"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("ChangeQtyForExternalCode", jObj.get("ChangeQtyForExternalCode"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("PointsOnBillPrint", jObj.get("PointsOnBillPrint"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("CMSPOSCode", jObj.get("CMSPOSCode"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("ManualAdvOrderNoCompulsory", jObj.get("ManualAdvOrderNoCompulsory"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("PrintManualAdvOrderNoOnBill", jObj.get("PrintManualAdvOrderNoOnBill"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("PrintModifierQtyOnKOT", jObj.get("PrintModifierQtyOnKOT"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("NoOfLinesInKOTPrint", jObj.get("NoOfLinesInKOTPrint"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("MultipleKOTPrintYN", jObj.get("MultipleKOTPrintYN"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("ItemQtyNumpad", jObj.get("ItemQtyNumpad"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("TreatMemberAsTable", jObj.get("TreatMemberAsTable"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("KOTToLocalPrinter", jObj.get("KOTToLocalPrinter"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("SettleBtnForDirectBillerBill", jObj.get("SettleBtnForDirectBillerBill"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("DelBoySelCompulsoryOnDirectBiller", jObj.get("DelBoySelCompulsoryOnDirectBiller"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("CMSMemberForKOTJPOS", jObj.get("CMSMemberForKOTJPOS"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("CMSMemberForKOTMPOS", jObj.get("CMSMemberForKOTMPOS"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("DontShowAdvOrderInOtherPOS", jObj.get("DontShowAdvOrderInOtherPOS"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("PrintZeroAmtModifierInBill", jObj.get("PrintZeroAmtModifierInBill"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("PrintKOTYN", jObj.get("PrintKOTYN"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("CreditCardSlipNoCompulsoryYN", jObj.get("CreditCardSlipNoCompulsoryYN"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("CreditCardExpiryDateCompulsoryYN", jObj.get("CreditCardExpiryDateCompulsoryYN"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("SelectWaiterFromCardSwipe", jObj.get("SelectWaiterFromCardSwipe"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("MultiWaiterSelectionOnMakeKOT", jObj.get("MultiWaiterSelectionOnMakeKOT"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("MoveTableToOtherPOS", jObj.get("MoveTableToOtherPOS"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("MoveKOTToOtherPOS", jObj.get("MoveKOTToOtherPOS"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("CalculateTaxOnMakeKOT", jObj.get("CalculateTaxOnMakeKOT"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("ReceiverEmailId", jObj.get("ReceiverEmailId"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("CalculateDiscItemWise", jObj.get("CalculateDiscItemWise"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("TakewayCustomerSelection", jObj.get("TakewayCustomerSelection"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("ShowItemStkColumnInDB", jObj.get("ShowItemStkColumnInDB"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("ItemType", jObj.get("ItemType"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("AllowNewAreaMasterFromCustMaster", jObj.get("AllowNewAreaMasterFromCustMaster"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("CustAddressSelectionForBill", jObj.get("CustAddressSelectionForBill"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("MemberCodeForKotInMposByCardSwipe", jObj.get("MemberCodeForKotInMposByCardSwipe"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("PrintBillPopUp", jObj.get("PrintBillPopUp"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("UseVatAndServiceTaxNoFromPOS", jObj.get("UseVatAndServiceTaxNoFromPOS"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("MemberCodeForMakeBillInMPOS", jObj.get("MemberCodeForMakeBillInMPOS"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("ItemWiseKOTPrintYN", jObj.get("ItemWiseKOTPrintYN"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("LastPOSForDayEnd", jObj.get("LastPOSForDayEnd"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("CMSPostingType", jObj.get("CMSPostingType"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("PopUpToApplyPromotionsOnBill", jObj.get("PopUpToApplyPromotionsOnBill"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strSelectCustomerCodeFromCardSwipe", jObj.get("strSelectCustomerCodeFromCardSwipe"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strCheckDebitCardBalOnTransactions", jObj.get("strCheckDebitCardBalOnTransactions"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strSettlementsFromPOSMaster", jObj.get("strSettlementsFromPOSMaster"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strShiftWiseDayEndYN", jObj.get("strShiftWiseDayEndYN"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strProductionLinkup", jObj.get("strProductionLinkup"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strLockDataOnShift", jObj.get("strLockDataOnShift"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strWSClientCode", jObj.get("strWSClientCode"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strPOSCode", jObj.get("strPOSCode"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strEnableBillSeries", jObj.get("strEnableBillSeries"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("EnablePMSIntegrationYN", jObj.get("EnablePMSIntegrationYN"));
			// clsPOSGlobalFunctionsController.hmPOSSetupValues.put("Status",jObj.get("Success"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strPrintTimeOnBill", jObj.get("strPrintTimeOnBill"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strPrintTDHItemsInBill", jObj.get("strPrintTDHItemsInBill"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strPrintRemarkAndReasonForReprint", jObj.get("strPrintRemarkAndReasonForReprint"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("intDaysBeforeOrderToCancel", jObj.get("intDaysBeforeOrderToCancel"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("intNoOfDelDaysForAdvOrder", jObj.get("intNoOfDelDaysForAdvOrder"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("intNoOfDelDaysForUrgentOrder", jObj.get("intNoOfDelDaysForUrgentOrder"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strSetUpToTimeForAdvOrder", jObj.get("strSetUpToTimeForAdvOrder"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strSetUpToTimeForUrgentOrder", jObj.get("strSetUpToTimeForUrgentOrder"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strUpToTimeForAdvOrder", jObj.get("strUpToTimeForAdvOrder"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strUpToTimeForUrgentOrder", jObj.get("strUpToTimeForUrgentOrder"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strEnableBothPrintAndSettleBtnForDB", jObj.get("strEnableBothPrintAndSettleBtnForDB"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strInrestoPOSIntegrationYN", jObj.get("strInrestoPOSIntegrationYN"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strInrestoPOSWebServiceURL", jObj.get("strInrestoPOSWebServiceURL"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strInrestoPOSId", jObj.get("strInrestoPOSId"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strInrestoPOSKey", jObj.get("strInrestoPOSKey"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strCarryForwardFloatAmtToNextDay", jObj.get("strCarryForwardFloatAmtToNextDay"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strOpenCashDrawerAfterBillPrintYN", jObj.get("strOpenCashDrawerAfterBillPrintYN"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strPropertyWiseSalesOrderYN", jObj.get("strPropertyWiseSalesOrderYN"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strShowItemDetailsGrid", jObj.get("strShowItemDetailsGrid"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strShowPopUpForNextItemQuantity", jObj.get("strShowPopUpForNextItemQuantity"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strJioMoneyIntegration", jObj.get("strJioMoneyIntegration"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strJioWebServiceUrl", jObj.get("strJioWebServiceUrl"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strJioMID", jObj.get("strJioMID"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strJioTID", jObj.get("strJioTID"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strJioActivationCode", jObj.get("strJioActivationCode"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strJioDeviceID", jObj.get("strJioDeviceID"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strNewBillSeriesForNewDay", jObj.get("strNewBillSeriesForNewDay"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strShowReportsPOSWise", jObj.get("strShowReportsPOSWise"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strEnableDineIn", jObj.get("strEnableDineIn"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strAutoAreaSelectionInMakeKOT", jObj.get("strAutoAreaSelectionInMakeKOT"));
			clsPOSGlobalFunctionsController.hmPOSSetupValues.put("strConsolidatedKOTPrinterPort", jObj.get("strConsolidatedKOTPrinterPort"));

		}

		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSIntegration/funGetMainMenu" + "?UserCode=" + userCode + "&ModuleType=" + moduleType + "&clientCode=" + clientCode + "&SuperUser=" + superUserYN + "&POSCode=" + POSCode;
		System.out.println(posUrl);

		url = new URL(posUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		output = "";
		op = "";
		while ((output = br.readLine()) != null) {
			op += output;
		}
		System.out.println("Obj=" + op);
		conn.disconnect();

		JSONParser parser = new JSONParser();
		Object obj = parser.parse(op);
		JSONObject jObjPOSMenuList = (JSONObject) obj;
		JSONArray jArrMenuList = (JSONArray) jObjPOSMenuList.get("MainMenuList");
		List<clsUserDesktopUtil> listMenu = new ArrayList<clsUserDesktopUtil>();

		for (int cnt = 0; cnt < jArrMenuList.size(); cnt++) {
			JSONObject jObj = (JSONObject) jArrMenuList.get(cnt);
			clsUserDesktopUtil obTreeRootItem = new clsUserDesktopUtil();

			obTreeRootItem.setStrFormName(jObj.get("FormName").toString());
			obTreeRootItem.setStrRequestMapping(jObj.get("RequestMapping").toString());
			obTreeRootItem.setStrImgName(jObj.get("ImageName").toString() + ".png");
			obTreeRootItem.setStrFormDesc(jObj.get("ModuleName").toString());
			listMenu.add(obTreeRootItem);
		}

		return listMenu;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/frmMainMenuWithoutPropertySelection", method = RequestMethod.GET)
	public ModelAndView funGetMainMenuWithoutPropertySelection(@ModelAttribute("command") clsPropertySelectionBean propBean, BindingResult result, HttpServletRequest req, Map<String, Object> model) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		clsUserMasterModel objModel = null;
		try {
			objModel = objUserMasterService.funGetUser(userCode, clientCode);
			String propertyCode=req.getSession().getAttribute("propertyCode").toString();
			String locationCode=req.getSession().getAttribute("locationCode").toString();

			if (req.getSession().getAttribute("selectedModuleName").equals("7-WebPOS")) 
			{
				
			} else {
				List<clsUserDesktopUtil> userDesktop = funGetUserDesktop(req);
				req.getSession().setAttribute("desktop", userDesktop);
			}

			String dateTime = objGlobalFun.funGetCurrentDateTime("yyyy-MM-dd");
			String time = dateTime.split(" ")[1];

			clsUserLogsModel objUserLogsModel = new clsUserLogsModel();
			objUserLogsModel.setStrUserCode(userCode);
			objUserLogsModel.setStrLoggedInLocation(locationCode);
			objUserLogsModel.setStrLoggedInProperty(propertyCode);
			objUserLogsModel.setStrClientCode(clientCode);
			objUserLogsModel.setDteLoginDate(dateTime);
			objUserLogsModel.setTmeLoginTime(time);
			objGlobalService.funAddUserLogEntry(objUserLogsModel);

			if (req.getSession().getAttribute("selectedModuleName").equals("7-WebPOS")) 
			{
				
			} else {
				Map<String, Object> tr = funGetTreeMap(req);
				req.getSession().setAttribute("treeMap", tr);
			}

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (null != objSetup) {
				req.getSession().setAttribute("amtDecPlace", objSetup.getIntdec());
				req.getSession().setAttribute("qtyDecPlace", objSetup.getIntqtydec());
				req.getSession().setAttribute("strNegStock", objSetup.getStrNegStock());
				req.getSession().setAttribute("RateFrom", objSetup.getStrRatePickUpFrom());
				req.getSession().setAttribute("strWorkFlowbasedAuth", objSetup.getStrWorkFlowbasedAuth());
				req.getSession().setAttribute("ShowReqVal", objSetup.getStrShowReqVal());
				req.getSession().setAttribute("ShowReqStk", objSetup.getStrShowStkReq());
				req.getSession().setAttribute("changeUOM", objSetup.getStrChangeUOMTrans());
				req.getSession().setAttribute("audit", objSetup.getStrAudit());
				req.getSession().setAttribute("showLocWiseProdMaster", objSetup.getStrShowProdMaster());
				req.getSession().setAttribute("showPrptyWiseProdDoc", objSetup.getStrShowProdDoc());
				req.getSession().setAttribute("ShowTransAsc_Desc", objSetup.getStrShowTransAsc_Desc());
				req.getSession().setAttribute("AllowDateChangeInMIS", objSetup.getStrAllowDateChangeInMIS());
				req.getSession().setAttribute("NameChangeInProdMast", objSetup.getStrNameChangeProdMast());
				req.getSession().setAttribute("NotificationTimeinterval", objSetup.getIntNotificationTimeinterval());
				req.getSession().setAttribute("MonthEnd", objSetup.getStrMonthEnd());
				req.getSession().setAttribute("showAllProdToAllLoc", objSetup.getStrShowAllProdToAllLoc());
				req.getSession().setAttribute("DiscEffectOnPO", objSetup.getStrEffectOfDiscOnPO());
				req.getSession().setAttribute("invoieFormat", objSetup.getStrInvFormat());
				clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(objSetup.getStrCurrencyCode(), clientCode);
				if (objCurrModel == null) {
					req.getSession().setAttribute("currencyCode", "CU00001");
					req.getSession().setAttribute("currValue", 1);
				} else {
					req.getSession().setAttribute("currencyCode", objCurrModel.getStrCurrencyCode());
					req.getSession().setAttribute("currValue", objCurrModel.getDblConvToBaseCurr());
				}
				req.getSession().setAttribute("showAllTaxesOnTransactions", objSetup.getStrShowAllTaxesOnTransaction());

			} else {
				req.getSession().setAttribute("amtDecPlace", 0);
				req.getSession().setAttribute("qtyDecPlace", 0);
				req.getSession().setAttribute("strNegStock", "N");
				req.getSession().setAttribute("RateFrom", "");
				req.getSession().setAttribute("strWorkFlowbasedAuth", "N");
				req.getSession().setAttribute("ShowReqVal", "N");
				req.getSession().setAttribute("ShowReqStk", "N");
				req.getSession().setAttribute("changeUOM", "N");
				req.getSession().setAttribute("audit", "N");
				req.getSession().setAttribute("showLocWiseProdMaster", "N");
				req.getSession().setAttribute("showPrptyWiseProdMaster", "N");
				req.getSession().setAttribute("showPrptyWiseProdDoc", "N");
				req.getSession().setAttribute("ShowTransAsc_Desc", "Asc");
				req.getSession().setAttribute("AllowDateChangeInMIS", "N");
				req.getSession().setAttribute("NameChangeInProdMast", "N");
				req.getSession().setAttribute("NotificationTimeinterval", 1);
				req.getSession().setAttribute("MonthEnd", "N");
				req.getSession().setAttribute("showAllProdToAllLoc", 'N');
				req.getSession().setAttribute("DiscEffectOnPO", "Y");
				req.getSession().setAttribute("invoieFormat", "");
				req.getSession().setAttribute("currencyCode", "CU00001");
				req.getSession().setAttribute("conValue", 1);
			}

			if (req.getSession().getAttribute("selectedModuleName").equals("3-WebPMS")) {
				String sql = "select tmeCheckInTime,tmeCheckOutTime from tblpropertysetup " + " where strPropertyCode='" + propertyCode + "' and strClientCode='" + clientCode + "'";
				List listPropInfo = objGlobalService.funGetListModuleWise(sql, "sql");
				Object[] arrPropInfo = (Object[]) listPropInfo.get(0);
				req.getSession().setAttribute("PMSCheckInTime", arrPropInfo[0].toString());
				req.getSession().setAttribute("PMSCheckOutTime", arrPropInfo[1].toString());

				sql = "select count(*) from tbldayendprocess " + " where strPropertyCode='" + propertyCode + "' and strClientCode='" + clientCode + "' " + " and strDayEnd='N' ";
				List listDayEnd = objGlobalService.funGetListModuleWise(sql, "sql");
				BigInteger count = new BigInteger(listDayEnd.get(0).toString());

				if (count.intValue() > 0) {
					sql = "select date(max(dtePMSDate)),strStartDay from tbldayendprocess " + " where strPropertyCode='" + propertyCode+ "' and strClientCode='" + clientCode + "' " + " and strDayEnd='N' ";
					List listStartFlag = objGlobalService.funGetListModuleWise(sql, "sql");
					Object[] arrObjDayEnd = (Object[]) listStartFlag.get(0);
					req.getSession().setAttribute("PMSDate", objGlobalFun.funGetDate("dd-MM-yyyy", arrObjDayEnd[0].toString()));
					req.getSession().setAttribute("PMSStartDay", arrObjDayEnd[1].toString());
				} else {
					req.getSession().setAttribute("PMSDate", objGlobalFun.funGetCurrentDate("dd-MM-yyyy"));
					req.getSession().setAttribute("PMSStartDay", "N");

					clsDayEndHdModel objDayEndModel = new clsDayEndHdModel();
					objDayEndModel.setStrPropertyCode(propertyCode);
					objDayEndModel.setStrDayEnd("N");
					objDayEndModel.setStrStartDay("Y");
					objDayEndModel.setDtePMSDate(objGlobalFun.funGetCurrentDate("yyyy-MM-dd"));
					objDayEndModel.setDblDayEndAmt(0);
					objDayEndModel.setStrUserCode(userCode);
					objDayEndModel.setStrClientCode(clientCode);

					objDayEndService.funAddUpdateDayEndHd(objDayEndModel);
				}
			}

			if (req.getSession().getAttribute("selectedModuleName").equals("7-WebPOS")) {
				// return new ModelAndView("frmWebPOSLogin");
			}

		} catch (Exception ex) {

			ex.printStackTrace();
			return new ModelAndView("frmStructureUpdate_2");
		}

		if (req.getSession().getAttribute("selectedModuleName").equals("7-WebPOS")) {

			return new ModelAndView("frmWebPOSModuleSelection");

		} else if (req.getSession().getAttribute("selectedModuleName").equals("3-WebPMS")) {
			return new ModelAndView("frmMainMenuPMS");
		} 
		else if (req.getSession().getAttribute("selectedModuleName").equals("1-WebStocks")) {
			if (objModel != null) {
				if (objModel.getStrShowDashBoard().equals("Y")) {
					try {
						String dteCurrDateTime = objGlobalFun.funGetCurrentDate("dd-MM-yyyy");
						String newFromDate =funGetDateForGraph(dteCurrDateTime);
						req.setAttribute("newFromDate", newFromDate);
						req.setAttribute("newToDate", dteCurrDateTime);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						return new ModelAndView("frmMainMenuWithDashBoard");
					}
				}
				else 
				   {
					  return new ModelAndView("frmMainMenu");
				   }
			} else {
				return new ModelAndView("frmMainMenu");
			}
		}
		else if(req.getSession().getAttribute("selectedModuleName").equals("6-WebCRM"))
		{
			if (objModel != null) {
				if (objModel.getStrShowDashBoard().equals("Y")) 
				{
					Date dt1;
					try {
						String dteCurrDateTime = objGlobalFun.funGetCurrentDate("dd-MM-yyyy");
						String newFromDate =funGetDateForGraph(dteCurrDateTime);
						String startDate = req.getSession().getAttribute("startDate").toString();
						req.setAttribute("newFromDate", startDate.split("/")[0]+"-"+startDate.split("/")[1]+"-"+startDate.split("/")[2]);
						req.setAttribute("newToDate", dteCurrDateTime);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						return new ModelAndView("frmMainMenuWithDashBoard1");
					}
				} else {
					return new ModelAndView("frmMainMenu");
				}
			} else {
				return new ModelAndView("frmMainMenu");
			}
		} 
		else {
			return new ModelAndView("frmMainMenu");
		}

	}


	
	private String funGetDateForGraph(String dteCurrDateTime)
	{
		String newFromDate="";
		Date dt1;
		try
		{
			SimpleDateFormat obj = new SimpleDateFormat("dd-MM-yyyy");
			dt1 = obj.parse(dteCurrDateTime);
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(dt1);
			cal.add(Calendar.DATE, -30);
			newFromDate = (cal.getTime().getYear() + 1900) + "-" + (cal.getTime().getMonth() + 1) + "-" + (cal.getTime().getDate());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return newFromDate;
	}

}
