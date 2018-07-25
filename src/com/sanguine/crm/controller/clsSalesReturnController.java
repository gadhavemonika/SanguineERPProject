package com.sanguine.crm.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.controller.clsJVGeneratorController;
import com.sanguine.crm.bean.clsInvoiceBean;
import com.sanguine.crm.bean.clsInvoiceDtlBean;
import com.sanguine.crm.bean.clsSalesReturnBean;
import com.sanguine.crm.model.clsInvoiceHdModel;
import com.sanguine.crm.model.clsInvoiceModelDtl;
import com.sanguine.crm.model.clsInvoiceTaxDtlModel;
import com.sanguine.crm.model.clsPartyMasterModel;
import com.sanguine.crm.model.clsSalesRetrunTaxModel;
import com.sanguine.crm.model.clsSalesReturnDtlModel;
import com.sanguine.crm.model.clsSalesReturnHdModel;
import com.sanguine.crm.model.clsSalesReturnHdModel_ID;
import com.sanguine.crm.service.clsInvoiceHdService;
import com.sanguine.crm.service.clsSalesReturnService;
import com.sanguine.model.clsAuditDtlModel;
import com.sanguine.model.clsAuditHdModel;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.model.clsLinkUpHdModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLinkUpService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsUOMService;
import com.sanguine.util.clsReportBean;
import com.sanguine.webpos.controller.clsPOSGlobalFunctionsController;

@Controller
public class clsSalesReturnController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobalfunction;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsUOMService objclsUOMService;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsSalesReturnService objSalesReturnService;

	@Autowired
	private clsInvoiceHdService objInvoiceHdService;

	@Autowired
	private clsProductMasterService objProductMasterService;

	@Autowired
	private clsLinkUpService objLinkupService;
	
	@Autowired
	private clsJVGeneratorController objJVGeneratorController;
	
	@Autowired
	private clsCurrencyMasterService objCurrencyMasterService;

	// Open Sales Return
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/frmSalesReturn", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		ArrayList list = new ArrayList();
		list.add("Direct");
		list.add("Invoice");
		list.add("Delivery Challan");
		list.add("Retail Billing");
		list.add("Purchase Return");
		model.put("againstList", list);
		
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		Map<String, String> hmCurrency = objCurrencyMasterService.funCurrencyListToDisplay(clientCode);
		if (hmCurrency.isEmpty()) {
			hmCurrency.put("", "");
		}
		model.put("currencyList", hmCurrency);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSalesReturn_1", "command", new clsSalesReturnBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSalesReturn", "command", new clsSalesReturnBean());
		} else {
			return null;
		}

	}

	// Save or Update Invoice
	@SuppressWarnings("unused")
	@RequestMapping(value = "/saveSalesReturnData", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsSalesReturnBean objBean, BindingResult result, HttpServletRequest req) {
		boolean flgHdSave = false;
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		List<clsSalesRetrunTaxModel> listSRTaxModel = new ArrayList<clsSalesRetrunTaxModel>();
		if (!result.hasErrors()) {

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			//double currValue = Double.parseDouble(req.getSession().getAttribute("currValue").toString());
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			String startDate = req.getSession().getAttribute("startDate").toString();
//			double currValue = 1.0;
//			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(objBean.getStrCurrency(), clientCode);
//			if (objCurrModel != null) {
//				currValue = objCurrModel.getDblConvToBaseCurr();
//			}
			
			double currConversion = 1.0;
			if(objBean.getDblConversion()>0)
			{
				currConversion=objBean.getDblConversion();
			}
			
			clsSalesReturnHdModel objHdModel = funPrepareHdModel(objBean, userCode, clientCode, req, currConversion);
			// String date=objHdModel.getDteInvDate().split("/");
			/*
			 * String[] InvDate = objHdModel.getDteSRDate().split("/"); String
			 * date = InvDate[2]+"-"+InvDate[0]+"-"+InvDate[1];
			 */
			objHdModel.setDteSRDate(objHdModel.getDteSRDate());
			objHdModel.setStrJVNo("");
			flgHdSave = objSalesReturnService.funAddUpdateSalesReturnHd(objHdModel);
			String strSRCode = objHdModel.getStrSRCode();
			List<clsSalesReturnDtlModel> listSRDtlModel = objBean.getListSalesReturn();
			if (flgHdSave) {
				if (null != listSRDtlModel) {
					objSalesReturnService.funDeleteDtl(strSRCode, clientCode);
					int intindex = 1;

					for (clsSalesReturnDtlModel obSrDtl : listSRDtlModel) {
						if (null != obSrDtl.getStrProdCode()) {
							obSrDtl.setDblPrice(obSrDtl.getDblPrice() * currConversion);
							obSrDtl.setStrSRCode(strSRCode);
							obSrDtl.setStrClientCode(clientCode);
							if (objHdModel.getStrAgainst().equalsIgnoreCase("Delivery Challan")) {
								String sqlCloseDC = "update tbldeliverychallanhd set strCloseDC='Y'  where strDCCode='" + objBean.getStrDCCode() + "' and strClientCode='" + clientCode + "'";
								objGlobalFunctionsService.funUpdateAllModule(sqlCloseDC, "sql");
							} else {
								if (objHdModel.getStrAgainst().equalsIgnoreCase("Invoice")) {

									String sqlCloseDC = "update tblinvoicehd set strCloseDC='Y'  where strInvCode='" + objBean.getStrDCCode() + "' and strClientCode='" + clientCode + "'";
									objGlobalFunctionsService.funUpdateAllModule(sqlCloseDC, "sql");
								}

							}
							objSalesReturnService.funAddUpdateSalesReturnDtl(obSrDtl);
							intindex++;
						}
					}
					// Save Data SalesReturn Tax
					double totalTaxAmt = 0.00;
					double totalTaxablAmt = 0.00;
					// double dblGrandAmt = 0.00;
					
					if (null != objBean.getListSalesRetrunTaxModel()) {

						objSalesReturnService.funDeleteTax(strSRCode, clientCode);
						for (clsSalesRetrunTaxModel ojInvoiceTaxDtlModel : objBean.getListSalesRetrunTaxModel())
						{
							ojInvoiceTaxDtlModel.setStrTaxableAmt(ojInvoiceTaxDtlModel.getStrTaxableAmt() * currConversion);
							ojInvoiceTaxDtlModel.setStrTaxAmt(ojInvoiceTaxDtlModel.getStrTaxAmt() * currConversion);
							ojInvoiceTaxDtlModel.setStrClientCode(clientCode);
							ojInvoiceTaxDtlModel.setStrSRCode(strSRCode);
							objSalesReturnService.funAddTaxDtl(ojInvoiceTaxDtlModel);
							totalTaxAmt += ojInvoiceTaxDtlModel.getStrTaxAmt();
							totalTaxablAmt = ojInvoiceTaxDtlModel.getStrTaxableAmt();
							listSRTaxModel.add(ojInvoiceTaxDtlModel);
						}
						objHdModel.setDblTotalAmt(String.valueOf(totalTaxablAmt + totalTaxAmt));
						objSalesReturnService.funAddUpdateSalesReturnHd(objHdModel);
					}
					
					req.getSession().setAttribute("success", true);
					req.getSession().setAttribute("successMessage", "SalesReturn Code : ".concat(objHdModel.getStrSRCode()));
					req.getSession().setAttribute("rptSRCode", objHdModel.getStrSRCode());
					req.getSession().setAttribute("rptSRCurrencyCode", objHdModel.getStrCurrency());
				}
			}

			clsCompanyMasterModel objCompModel = objSetupMasterService.funGetObject(clientCode);
			if (objCompModel.getStrWebBookModule().equals("Yes")) {
				
				boolean authorisationFlag=false;
				if (null != req.getSession().getAttribute("hmAuthorization")) {
					HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
					if (hmAuthorization.containsKey("Sales Return")) {
						authorisationFlag=hmAuthorization.get("Sales Return");
					}
				}
				
				if(!authorisationFlag)
				{
					String retuenVal=objJVGeneratorController.funGenrateJVforSalesReturn(strSRCode, clientCode, userCode, propCode, req);
					String JVGenMessage="";
					String[] arrVal=retuenVal.split("!");
					
					boolean flgJVPosting=true;
					if(arrVal[0].equals("ERROR"))
					{
						JVGenMessage=arrVal[1];
						flgJVPosting=false;
					}else{
						objHdModel.setStrJVNo(arrVal[0]);
						objSalesReturnService.funAddUpdateSalesReturnHd(objHdModel);
					}
					req.getSession().setAttribute("JVGen", flgJVPosting);
					req.getSession().setAttribute("JVGenMessage", JVGenMessage);
				}
			}
			
			return new ModelAndView("redirect:/frmSalesReturn.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmSalesReturn?saddr=" + urlHits, "command", new clsSalesReturnBean());
		}
	}

	// Convert bean to model function
	@SuppressWarnings("unused")
	private clsSalesReturnHdModel funPrepareHdModel(clsSalesReturnBean objBean, String userCode, String clientCode, HttpServletRequest req, double currValue) {

		long lastNo = 0;
		clsSalesReturnHdModel objModel;

		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		clsSalesReturnHdModel srHdModel;

		String[] date = objBean.getDteSRDate().split("-");
		String date1 = date[2] + "-" + date[1] + "-" + date[0];
		if (objBean.getStrSRCode().trim().length() == 0) {

			String strSRCode = objGlobalfunction.funGenerateDocumentCode("frmSalesReturn", date1, req);

			srHdModel = new clsSalesReturnHdModel(new clsSalesReturnHdModel_ID(strSRCode, clientCode));
			srHdModel.setStrSRCode(strSRCode);
			srHdModel.setStrUserCreated(userCode);
			srHdModel.setDteDateCreated(objGlobalfunction.funGetCurrentDateTime("yyyy-MM-dd"));
			// srHdModel.setIntid(lastNo);

		}

		else {
			clsSalesReturnHdModel objSRModel = objSalesReturnService.funGetSalesReturnHd(objBean.getStrSRCode(), clientCode);
			if (null == objSRModel) {
				String strSRCode = objGlobalfunction.funGenerateDocumentCode("frmSalesReturn", date1, req);

				srHdModel = new clsSalesReturnHdModel(new clsSalesReturnHdModel_ID(strSRCode, clientCode));
				// srHDModel.setIntid(lastNo);
				srHdModel.setStrUserCreated(userCode);

				srHdModel.setDteDateCreated(objGlobalfunction.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				srHdModel = new clsSalesReturnHdModel(new clsSalesReturnHdModel_ID(objBean.getStrSRCode(), clientCode));
				// objModel.setStrPropertyCode(propCode);
			}
		}

		srHdModel.setStrUserEdited(userCode);
		srHdModel.setDteDateEdited(objGlobalfunction.funGetCurrentDateTime("yyyy-MM-dd"));
		srHdModel.setDteSRDate(objBean.getDteSRDate());
		srHdModel.setStrAgainst(objBean.getStrAgainst());

		srHdModel.setStrCustCode(objBean.getStrCustCode());

		srHdModel.setStrLocCode(objBean.getStrLocCode());

		if (objBean.getStrDCCode() == null) {
			srHdModel.setStrDCCode("");
		} else {
			srHdModel.setStrDCCode(objBean.getStrDCCode());
		}

		// srHdModel.setDblSubTotalAmt(objBean.get());
		// double taxamt=0.0;
		//
		// if(objBean.getDblTaxAmt()!=null)
		// {
		// taxamt=objBean.getDblTaxAmt();
		// }
		// Double amt=objBean.getDblSubTotalAmt()+taxamt;
		srHdModel.setDblTotalAmt(String.valueOf(Double.parseDouble(objBean.getDblTotalAmt()) * currValue));
		srHdModel.setDblTaxAmt(objBean.getDblTaxAmt() * currValue);
		srHdModel.setDblDiscPer(objBean.getDblDiscPer());
		srHdModel.setDblDiscAmt(objBean.getDblDiscAmt() * currValue);
		srHdModel.setStrCurrency(objBean.getStrCurrency());
		srHdModel.setDblConversion(currValue);

		return srHdModel;

	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadSalesHdData", method = RequestMethod.GET)
	public @ResponseBody clsSalesReturnBean funAssignFields(@RequestParam("srCode") String srCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsSalesReturnBean objBeanSalesRet = new clsSalesReturnBean();
		List<Object> objDC = objSalesReturnService.funGetSalesReturn(srCode, clientCode);
		clsSalesReturnHdModel objsrHdModel = null;
		clsLocationMasterModel objLocationMasterModel = null;
		clsPartyMasterModel objPartyMasterModel = null;

		for (int i = 0; i < objDC.size(); i++) {
			Object[] ob = (Object[]) objDC.get(i);
			objsrHdModel = (clsSalesReturnHdModel) ob[0];
			objLocationMasterModel = (clsLocationMasterModel) ob[1];
			objPartyMasterModel = (clsPartyMasterModel) ob[2];
		}

		objBeanSalesRet = funPrepardHdBean(objsrHdModel, objLocationMasterModel, objPartyMasterModel);
		objBeanSalesRet.setStrCustName(objPartyMasterModel.getStrPName());
		objBeanSalesRet.setStrLocName(objLocationMasterModel.getStrLocName());
		List<clsSalesReturnDtlModel> listSalesDtl = new ArrayList<clsSalesReturnDtlModel>();
		List<Object> objInvDtlModelList = funGetSalesReturnDtl(srCode, clientCode);
		for (int i = 0; i < objInvDtlModelList.size(); i++) {
			Object[] obj = (Object[]) objInvDtlModelList.get(i);
			clsSalesReturnDtlModel salesRetDtl = new clsSalesReturnDtlModel();
			salesRetDtl.setStrSRCode(obj[0].toString());
			salesRetDtl.setStrProdCode(obj[1].toString());
			salesRetDtl.setDblQty(Double.valueOf(obj[2].toString()));
			salesRetDtl.setDblPrice(Double.valueOf(obj[3].toString()));
			salesRetDtl.setDblWeight(Double.valueOf(obj[4].toString()));
			salesRetDtl.setStrRemarks(obj[5].toString());
			salesRetDtl.setStrClientCode(obj[6].toString());
			salesRetDtl.setStrProdName(obj[7].toString());
			// clsSalesReturnDtlModel invDtl = (clsSalesReturnDtlModel) obj[0];
			// clsProductMasterModel prodmast =(clsProductMasterModel) obj[1];
			// clsInvoiceModelDtl invModDtl =(clsInvoiceModelDtl) obj[4];
			// prodmast.setDblUnitPrice(invModDtl.getDblPrice());

			// invDtl.setStrProdName(prodmast.getStrProdName());
			listSalesDtl.add(salesRetDtl);
		}

		objBeanSalesRet.setListSalesReturn(listSalesDtl);

		String sql = "select strTaxCode,strTaxDesc,strTaxableAmt,strTaxAmt from clsSalesRetrunTaxModel " + "where strSRCode='" + srCode + "' and strClientCode='" + clientCode + "'";

		List list = objGlobalFunctionsService.funGetList(sql, "hql");
		List<clsSalesRetrunTaxModel> listTaxDtl = new ArrayList<clsSalesRetrunTaxModel>();
		if (null != list) {
			for (int cnt = 0; cnt < list.size(); cnt++) {
				clsSalesRetrunTaxModel objTaxDtl = new clsSalesRetrunTaxModel();
				Object[] arrObj = (Object[]) list.get(cnt);
				objTaxDtl.setStrTaxCode(arrObj[0].toString());
				objTaxDtl.setStrTaxDesc(arrObj[1].toString());
				objTaxDtl.setStrTaxableAmt(Double.parseDouble(arrObj[2].toString()));
				objTaxDtl.setStrTaxAmt(Double.parseDouble(arrObj[3].toString()));
				objTaxDtl.setStrSRCode(srCode);
				objTaxDtl.setStrClientCode(clientCode);
				listTaxDtl.add(objTaxDtl);
			}
			objBeanSalesRet.setListSalesRetrunTaxModel(listTaxDtl);
		}
		objBeanSalesRet.setDblConversion(objsrHdModel.getDblConversion());
		objBeanSalesRet.setStrCurrency(objsrHdModel.getStrCurrency());
		return objBeanSalesRet;

	}

	private clsSalesReturnBean funPrepardHdBean(clsSalesReturnHdModel objsalRetHdModel, clsLocationMasterModel objLocationMasterModel, clsPartyMasterModel objPartyMasterModel) {

		clsSalesReturnBean objBean = new clsSalesReturnBean();

		objBean.setDteSRDate(objsalRetHdModel.getDteSRDate());
		objBean.setStrAgainst(objsalRetHdModel.getStrAgainst());
		objBean.setStrCustCode(objsalRetHdModel.getStrCustCode());
		objBean.setStrSRCode(objsalRetHdModel.getStrSRCode());
		objBean.setStrDCCode(objsalRetHdModel.getStrDCCode());

		objBean.setStrLocCode(objsalRetHdModel.getStrLocCode());

		// objBean.setDblTaxAmt(objInvHdModel.getDblTaxAmt());
		objBean.setDblTotalAmt(objsalRetHdModel.getDblTotalAmt());
		objBean.setDblDiscAmt(objsalRetHdModel.getDblDiscAmt());
		objBean.setDblDiscPer(objsalRetHdModel.getDblDiscPer());
		return objBean;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadSalesReturnRetailInvoiceHdData", method = RequestMethod.GET)
	public @ResponseBody clsSalesReturnBean funAssignRetailInvoiceFields(@RequestParam("invCode") String invCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsSalesReturnBean objSaleBeanInv = new clsSalesReturnBean();
		clsInvoiceBean objBeanInv = new clsInvoiceBean();

		List<Object> objDC = objInvoiceHdService.funGetInvoice(invCode, clientCode);
		clsInvoiceHdModel objInvoiceHdModel = null;
		clsLocationMasterModel objLocationMasterModel = null;
		clsPartyMasterModel objPartyMasterModel = null;

		for (int i = 0; i < objDC.size(); i++) {
			Object[] ob = (Object[]) objDC.get(i);
			objInvoiceHdModel = (clsInvoiceHdModel) ob[0];
			objLocationMasterModel = (clsLocationMasterModel) ob[1];
			objPartyMasterModel = (clsPartyMasterModel) ob[2];
			objSaleBeanInv.setStrCustCode(objPartyMasterModel.getStrPCode());
			objSaleBeanInv.setStrCustName(objPartyMasterModel.getStrPName());
			objSaleBeanInv.setStrLocCode(objLocationMasterModel.getStrLocCode());
			objSaleBeanInv.setStrLocName(objLocationMasterModel.getStrLocName());
			objSaleBeanInv.setDteSRDate(objInvoiceHdModel.getDteInvDate().split(" ")[0]);
		}

		clsInvoiceHdModel objInvHDModelList = objInvoiceHdService.funGetInvoiceDtl(invCode, clientCode);
		List<clsInvoiceModelDtl> listInvDtlModel = objInvHDModelList.getListInvDtlModel();
		List<clsInvoiceDtlBean> listInvDtlBean = new ArrayList();

		List<clsSalesReturnDtlModel> listRBDtl = new ArrayList<clsSalesReturnDtlModel>();
		for (int i = 0; i < listInvDtlModel.size(); i++) {
			clsInvoiceDtlBean objBeanInvoice = new clsInvoiceDtlBean();

			clsInvoiceModelDtl obj = listInvDtlModel.get(i);
			clsProductMasterModel objProdModle = objProductMasterService.funGetObject(obj.getStrProdCode(), clientCode);
			clsSalesReturnDtlModel invDtl = new clsSalesReturnDtlModel();
			invDtl.setStrSRCode(invCode);
			invDtl.setStrProdCode(obj.getStrProdCode());
			invDtl.setDblQty(obj.getDblQty());
			invDtl.setDblPrice(obj.getDblUnitPrice());
			invDtl.setDblWeight(0.0);
			invDtl.setStrRemarks(obj.getStrRemarks());
			invDtl.setStrClientCode(clientCode);
			invDtl.setStrProdName(objProdModle.getStrProdName());
			listRBDtl.add(invDtl);
		}
		objSaleBeanInv.setListSalesReturn(listRBDtl);
		
		List<clsSalesRetrunTaxModel> listSalesRetTaxDtl=new ArrayList<clsSalesRetrunTaxModel>();
		List<clsInvoiceTaxDtlModel> listInvTaxDtlModel = objInvHDModelList.getListInvTaxDtlModel();
		
		for(clsInvoiceTaxDtlModel objInvoiceTaxDtlModel : listInvTaxDtlModel)
		{
			clsSalesRetrunTaxModel objSalesRetrunTaxModel=new clsSalesRetrunTaxModel();
			objSalesRetrunTaxModel.setStrTaxCode(objInvoiceTaxDtlModel.getStrTaxCode());
			objSalesRetrunTaxModel.setStrTaxDesc(objInvoiceTaxDtlModel.getStrTaxDesc());
			objSalesRetrunTaxModel.setStrTaxAmt(objInvoiceTaxDtlModel.getDblTaxAmt());
			objSalesRetrunTaxModel.setStrTaxableAmt(objInvoiceTaxDtlModel.getDblTaxableAmt());
			
			listSalesRetTaxDtl.add(objSalesRetrunTaxModel);
		}
		objSaleBeanInv.setListSalesRetrunTaxModel(listSalesRetTaxDtl);
		
		return objSaleBeanInv;
	}

	public List<Object> funGetSalesReturnDtl(String srCode, String clientCode) {
		List<Object> objInvDtlList = null;

		String sql = "  select a.strSRCode,a.strProdCode,a.dblQty,a.dblPrice,a.dblWeight,a.strRemarks,a.strClientCode,b.strProdName " + " from tblsalesreturndtl a,tblproductmaster b ,tblsalesreturnhd c  " + " where a.strSRCode='" + srCode + "' and a.strProdCode= b.strProdCode and a.strSRCode=c.strSRCode " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' ";

		List list = objGlobalFunctionsService.funGetList(sql, "sql");

		if (list.size() > 0) {
			objInvDtlList = list;

		}
		return objInvDtlList;
	}

	@RequestMapping(value = "/frmSalesReturnSlip", method = RequestMethod.GET)
	public ModelAndView funOpenSalesReturnSlipForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSalesReturnSlip_1", "command", new clsReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSalesReturnSlip", "command", new clsReportBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/openRptSalesReturnSlip", method = RequestMethod.GET)
	private void funOpenReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String strSRCode = req.getParameter("rptSRCode").toString();
		String currencyCode=req.getParameter("currency").toString();
		req.getSession().removeAttribute("currency");
		objBean.setStrSRCode(strSRCode);
		objBean.setStrDocType("pdf");
		funCallReport(objBean,currencyCode, resp, req);
	}

	@RequestMapping(value = "/rptSalesReturnSlip", method = RequestMethod.GET)
	private void funReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		funCallReport(objBean,"", resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void funCallReport(clsReportBean objBean,String currencyCode, HttpServletResponse resp, HttpServletRequest req) {

		String prodType = objBean.getStrProdType();
		String type = objBean.getStrDocType();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		
		Connection con = objGlobalfunction.funGetConnection(req);
		try {

			/*String strCurr = req.getSession().getAttribute("currValue").toString();
			double currValue = Double.parseDouble(strCurr);
			*/
			//double currValue = Double.parseDouble(req.getSession().getAttribute("currValue").toString());
			double currValue = 1.0;
			if(!currencyCode.isEmpty())
			{
				clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
				if (objCurrModel != null) {
					currValue = objCurrModel.getDblConvToBaseCurr();
				}
			}
			
			String srCode = "", locationCode = "", locationName = "";
			String againstName = "", custCode = "", custName = "", dcCode = "",currencyName="";
			String userCode = req.getSession().getAttribute("usercode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String strLocCode = req.getSession().getAttribute("locationCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			String reportName ="";
			if(clientCode.equals("226.001"))
			{
				reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptSalesReturnWithoutTaxSlip.jrxml");	
				
			}else{
				reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptSalesReturnSlip.jrxml");
			}
			
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			String userCreated="";
			String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
			String sqlDS = " select a.strSRCode, b.strLocCode, b.strLocName, a.strAgainst, a.strDCCode, a.strCustCode, c.strPName,d.strCurrencyName,a.strCurrency,a.strUserCreated from "+webStockDB+".tblsalesreturnhd a, "+webStockDB+".tbllocationmaster b, "+webStockDB+".tblpartymaster c,"+webStockDB+".tblcurrencymaster d " + " where a.strLocCode=b.strLocCode and a.strClientCode=b.strClientCode " + " and a.strCustCode=c.strPCode and a.strCurrency=d.strCurrencyCode and a.strClientCode=b.strClientCode " + " and a.strClientCode='" + clientCode
					+ "' and a.strSRCode='" + objBean.getStrSRCode() + "' ";

			List listds = objGlobalFunctionsService.funGetList(sqlDS, "sql");

			if (listds.size() > 0) {

				for (int i = 0; i < listds.size(); i++) {
					Object[] obProdDtl = (Object[]) listds.get(i);

					srCode = obProdDtl[0].toString();
					locationCode = obProdDtl[1].toString();
					locationName = obProdDtl[2].toString();
					againstName = obProdDtl[3].toString();
					dcCode = obProdDtl[4].toString();
					custCode = obProdDtl[5].toString();
					custName = obProdDtl[6].toString();
					currencyName= obProdDtl[7].toString();
					currencyCode= obProdDtl[8].toString();
					userCreated= obProdDtl[9].toString();
					clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
					if (objCurrModel != null) {
						currValue = objCurrModel.getDblConvToBaseCurr();
					}
				}
			}

			String sql = " select b.strProdCode, c.strProdName, b.dblQty, b.dblPrice/" + currValue + " as dblPrice, (b.dblQty*b.dblPrice)/" + currValue + " as dblTotalAmt, b.strRemarks,a.dblTotalAmt/" + currValue + " as dblTotalAmt  from "+webStockDB+".tblsalesreturnhd a, "+webStockDB+".tblsalesreturndtl b, "+webStockDB+".tblproductmaster c "
					+ " where a.strSRCode=b.strSRCode and b.strProdCode=c.strProdCode and a.strClientCode=b.strClientCode " + " and b.strClientCode=c.strClientCode and a.strClientCode='" + clientCode + "'  and a.strSRCode='" + objBean.getStrSRCode() + "' ";

			// getting multi copy of small data in table in Detail thats why we
			// add in subDataset
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sql);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsProddtl");
			subDataset.setQuery(subQuery);

			if(!(clientCode.equals("226.001"))){
				
			String taxSummary = "select a.strTaxCode,a.strTaxDesc,a.strTaxableAmt/" + currValue + " as strTaxableAmt ,a.strTaxAmt/" + currValue + " as strTaxAmt,b.dblTotalAmt/" + currValue + " as dblTotalAmt from "+webStockDB+".tblsalesreturntaxdtl a ,"+webStockDB+".tblsalesreturnhd b " + "where a.strSRCode='" + srCode + "' and a.strClientCode='" + clientCode + "' and a.strSRCode=b.strSRCode ";
		
			JRDesignQuery taxSummQuery = new JRDesignQuery();
			taxSummQuery.setText(taxSummary);
			JRDesignDataset taxSumDataset = (JRDesignDataset) datasetMap.get("dsTaxSummary");
			taxSumDataset.setQuery(taxSummQuery);
			
			}
			
			JasperReport jr = JasperCompileManager.compileReport(jd);
			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCreated);
			hm.put("strImagePath", imagePath);
			hm.put("strAddr1", objSetup.getStrAdd1());
			hm.put("strAddr2", objSetup.getStrAdd2());
			hm.put("strCity", objSetup.getStrCity());
			hm.put("strState", objSetup.getStrState());
			hm.put("strCountry", objSetup.getStrCountry());
			hm.put("strPin", objSetup.getStrPin());
			hm.put("srCode", srCode);
			hm.put("locationCode", locationCode);
			hm.put("locationName", locationName);
			hm.put("againstName", againstName);
			hm.put("dcCode", dcCode);
			hm.put("custCode", custCode);
			hm.put("custName", custName);
			hm.put("currencyName", currencyName);

			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setContentLength(bytes.length);
				servletOutputStream.write(bytes, 0, bytes.length);
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptDeliveryScheduleSlip." + type.trim());
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptDeliveryScheduleSlip." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}


	
	private String funGenrateJVforSalesReturn(clsSalesReturnHdModel objModel, List<clsSalesReturnDtlModel> listSRDtlModel, List<clsSalesRetrunTaxModel> listSRTaxModel, String clientCode, String userCode, String propCode, HttpServletRequest req) {
		JSONObject jObjJVData = new JSONObject();

		JSONArray jArrJVdtl = new JSONArray();
		JSONArray jArrJVDebtordtl = new JSONArray();
		String jvCode = "";
		String custCode = objModel.getStrCustCode();
		String amt = objModel.getDblTotalAmt();
		double debitAmt = Double.parseDouble(amt);
		double dblSubtotal = debitAmt -( objModel.getDblDiscAmt()) + objModel.getDblTaxAmt();
		String strCurr = req.getSession().getAttribute("currValue").toString();
		double currValue = Double.parseDouble(strCurr);
		clsLinkUpHdModel objLinkCust = objLinkupService.funGetARLinkUp(custCode, clientCode, propCode, "Customer", "Sale");
		if (objLinkCust != null) {
			if (objModel.getStrSRCode().equals("")) {
				jObjJVData.put("strVouchNo", "");
				jObjJVData.put("strNarration", "JV Genrated by Sales-Ret:" + objModel.getStrSRCode());
				jObjJVData.put("strSancCode", "");
				jObjJVData.put("strType", "");
				jObjJVData.put("dteVouchDate", objModel.getDteSRDate());
				jObjJVData.put("intVouchMonth", 1);
//				jObjJVData.put("dblAmt", debitAmt);
				jObjJVData.put("dblAmt", dblSubtotal);
				jObjJVData.put("strTransType", "R");
				jObjJVData.put("strTransMode", "A");
				jObjJVData.put("strModuleType", "AP");
				jObjJVData.put("strMasterPOS", "CRM");
				jObjJVData.put("strUserCreated", userCode);
				jObjJVData.put("strUserEdited", userCode);
				jObjJVData.put("dteDateCreated", objGlobalfunction.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjJVData.put("dteDateEdited", objGlobalfunction.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjJVData.put("strClientCode", clientCode);
				jObjJVData.put("strPropertyCode", propCode);

			} else {
				jObjJVData.put("strVouchNo", objModel.getStrSRCode());
				jObjJVData.put("strNarration", "JV Genrated by Sales-Ret:" + objModel.getStrSRCode());
				jObjJVData.put("strSancCode", "");
				jObjJVData.put("strType", "");
				jObjJVData.put("dteVouchDate", objModel.getDteSRDate());
				jObjJVData.put("intVouchMonth", 1);
//				jObjJVData.put("dblAmt", debitAmt);
				jObjJVData.put("dblAmt", dblSubtotal);
				jObjJVData.put("strTransType", "R");
				jObjJVData.put("strTransMode", "A");
				jObjJVData.put("strModuleType", "AP");
				jObjJVData.put("strMasterPOS", "CRM");
				jObjJVData.put("strUserCreated", userCode);
				jObjJVData.put("strUserEdited", userCode);
				jObjJVData.put("dteDateCreated", objGlobalfunction.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjJVData.put("dteDateEdited", objGlobalfunction.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjJVData.put("strClientCode", clientCode);
				jObjJVData.put("strPropertyCode", propCode);

			}
			// jvhd entry end

			// jvdtl entry Start
			for (clsSalesReturnDtlModel objDtl : listSRDtlModel) {

				JSONObject jObjDtl = new JSONObject();

				clsProductMasterModel objProdModle = objProductMasterService.funGetObject(objDtl.getStrProdCode(), clientCode);
				clsLinkUpHdModel objLinkSubGroup = objLinkupService.funGetARLinkUp(objProdModle.getStrSGCode(), clientCode, propCode, "SubGroup", "Sale");
				if (objProdModle != null && objLinkSubGroup != null) {
					jObjDtl.put("strVouchNo", "");
					jObjDtl.put("strAccountCode", objLinkSubGroup.getStrAccountCode());
					jObjDtl.put("strAccountName", objLinkSubGroup.getStrMasterDesc());
					jObjDtl.put("strCrDr", "Dr");
					jObjDtl.put("dblDrAmt", objDtl.getDblQty() * objDtl.getDblPrice());
					jObjDtl.put("dblCrAmt", 0.00);
					jObjDtl.put("strNarration", "WS Product code :" + objDtl.getStrProdCode());
					jObjDtl.put("strOneLine", "R");
					jObjDtl.put("strClientCode", clientCode);
					jObjDtl.put("strPropertyCode", propCode);
					jArrJVdtl.add(jObjDtl);
				}
			}

			if (listSRDtlModel != null) {
				for (clsSalesRetrunTaxModel objTaxDtl : listSRTaxModel) {
					JSONObject jObjTaxDtl = new JSONObject();
					clsLinkUpHdModel objLinkTax = objLinkupService.funGetARLinkUp(objTaxDtl.getStrTaxCode(), clientCode, propCode, "Tax", "Sale");
					if (objLinkTax != null) {
						jObjTaxDtl.put("strVouchNo", "");
						jObjTaxDtl.put("strAccountCode", objLinkTax.getStrAccountCode());
						jObjTaxDtl.put("strAccountName", objLinkTax.getStrMasterDesc());
						jObjTaxDtl.put("strCrDr", "Dr");
						jObjTaxDtl.put("dblDrAmt", objTaxDtl.getStrTaxAmt());
						jObjTaxDtl.put("dblCrAmt", 0.00);
						jObjTaxDtl.put("strNarration", "WS Tax Desc :" + objTaxDtl.getStrTaxDesc());
						jObjTaxDtl.put("strOneLine", "R");
						jObjTaxDtl.put("strClientCode", clientCode);
						jObjTaxDtl.put("strPropertyCode", propCode);
						jArrJVdtl.add(jObjTaxDtl);
					}
				}
			}
			
			if (objModel.getDblDiscAmt() / currValue != 0) {
				JSONObject jObjTaxDtl = new JSONObject();
				clsLinkUpHdModel objLinkDisc = objLinkupService.funGetARLinkUp("Discount", clientCode, propCode, "Discount", "Sale");
				if (objLinkDisc != null) {
					jObjTaxDtl.put("strVouchNo", "");
					jObjTaxDtl.put("strAccountCode", objLinkDisc.getStrAccountCode());
					jObjTaxDtl.put("strAccountName", objLinkDisc.getStrMasterDesc());
					jObjTaxDtl.put("strCrDr", "Cr");
					jObjTaxDtl.put("dblCrAmt", objModel.getDblDiscAmt());
					jObjTaxDtl.put("dblDrAmt", 0.00);
					jObjTaxDtl.put("strNarration", "WS Sale Discount Desc :" + objModel.getDblDiscAmt());
					jObjTaxDtl.put("strOneLine", "R");
					jObjTaxDtl.put("strClientCode", clientCode);
					jObjTaxDtl.put("strPropertyCode", propCode);
					jArrJVdtl.add(jObjTaxDtl);
				}

			}
			
			
			JSONObject jObjCustDtl = new JSONObject();
			jObjCustDtl.put("strVouchNo", "");
			jObjCustDtl.put("strAccountCode", "");
			jObjCustDtl.put("strAccountName", "");
			jObjCustDtl.put("strCrDr", "Cr");
			jObjCustDtl.put("dblDrAmt", 0.00);
			jObjCustDtl.put("dblCrAmt", objModel.getDblTotalAmt());
			jObjCustDtl.put("strNarration", "Invoice Customer Return");
			jObjCustDtl.put("strOneLine", "R");
			jObjCustDtl.put("strClientCode", clientCode);
			jObjCustDtl.put("strPropertyCode", propCode);
			jArrJVdtl.add(jObjCustDtl);

			jObjJVData.put("ArrJVDtl", jArrJVdtl);

			// jvdtl entry end

			// jvDebtor detail entry start
			String sql = "  select a.strSRCode,a.dblTotalAmt,b.strDebtorCode,b.strPName,a.strCustCode,date(a.dteSRDate) ," 
					   + " a.strDCCode   from tblsalesreturnhd a,tblpartymaster b  "
			// + "	  a.strLocCode =b.strLocCode  and	 "
					  + " where  a.strSRCode='" + objModel.getStrSRCode() + "' " 
			          + " and a.strClientCode='" + objModel.getStrClientCode() + "' " 
					  + " and a.strCustCode='" + objModel.getStrCustCode() + "'  and a.strCustCode=b.strPCode  ";
			List listTax = objGlobalFunctionsService.funGetList(sql, "sql");

			if (null != listTax) {
				for (int i = 0; i < listTax.size(); i++) {
					JSONObject jObjDtl = new JSONObject();
					Object[] ob = (Object[]) listTax.get(i);
					jObjDtl.put("strVouchNo", "");
					jObjDtl.put("strDebtorCode", ob[2].toString());
					jObjDtl.put("strDebtorName", ob[3].toString());
					jObjDtl.put("strCrDr", "Cr");
					jObjDtl.put("dblAmt", ob[1].toString());
					jObjDtl.put("strBillNo", ob[0].toString());
					jObjDtl.put("strInvoiceNo", ob[0].toString());
					jObjDtl.put("strGuest", "");
					jObjDtl.put("strAccountCode", objLinkCust.getStrAccountCode());
					jObjDtl.put("strCreditNo", "");
					jObjDtl.put("dteBillDate", ob[5].toString());
					jObjDtl.put("dteInvoiceDate", ob[5].toString());
					jObjDtl.put("strNarration", "");
					jObjDtl.put("dteDueDate", ob[5].toString());
					jObjDtl.put("strClientCode", clientCode);
					jObjDtl.put("strPropertyCode", propCode);
					jObjDtl.put("strPOSCode", "");
					jObjDtl.put("strPOSName", "");
					jObjDtl.put("strRegistrationNo", "");

					jArrJVDebtordtl.add(jObjDtl);
				}
			}

			jObjJVData.put("ArrJVDebtordtl", jArrJVDebtordtl);
			// jvDebtor detail entry end

			JSONObject jObj = objGlobalfunction.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebBooksIntegration/funGenrateJVforInvoice", jObjJVData);
			jvCode = jObj.get("strJVCode").toString();
		}
		return jvCode;
	}

	/**
	 * Auditing Function Start
	 * 
	 * @param SRCode
	 * @param req
	 */
	public void funSaveAudit(String srCode, String strTransMode, HttpServletRequest req) {
		
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			List<Object> objDC = objSalesReturnService.funGetSalesReturn(srCode, clientCode);
			clsSalesReturnHdModel srModel = null;
			for (int i = 0; i < objDC.size(); i++) {
				Object[] ob = (Object[]) objDC.get(i);
				srModel = (clsSalesReturnHdModel) ob[0];
				
			}
			if (null != srModel) {
			
				List<clsSalesReturnDtlModel> listSalesDtl = new ArrayList<clsSalesReturnDtlModel>();
				List<Object> objInvDtlModelList = funGetSalesReturnDtl(srCode, clientCode);
				if(objInvDtlModelList!=null){
					for (int i = 0; i < objInvDtlModelList.size(); i++) {
						Object[] obj = (Object[]) objInvDtlModelList.get(i);
						clsSalesReturnDtlModel salesRetDtl = new clsSalesReturnDtlModel();
						salesRetDtl.setStrSRCode(obj[0].toString());
						salesRetDtl.setStrProdCode(obj[1].toString());
						salesRetDtl.setDblQty(Double.valueOf(obj[2].toString()));
						salesRetDtl.setDblPrice(Double.valueOf(obj[3].toString()));
						salesRetDtl.setDblWeight(Double.valueOf(obj[4].toString()));
						salesRetDtl.setStrRemarks(obj[5].toString());
						salesRetDtl.setStrClientCode(obj[6].toString());
						salesRetDtl.setStrProdName(obj[7].toString());
						listSalesDtl.add(salesRetDtl);
					}
				}
				//List listSalesDtl = objSalesReturnService.funGetDtlList(srCode, clientCode);
				if (null != listSalesDtl && listSalesDtl.size() > 0) {
					String sql = "select count(*)+1 from tblaudithd where left(strTransCode,12)='" + srModel.getStrSRCode() + "'";
					List list = objGlobalFunctionsService.funGetList(sql, "sql");
					if (!list.isEmpty()) {
						String count = list.get(0).toString();
						clsAuditHdModel model = funPrepairAuditHdModel(srModel);
						if (strTransMode.equalsIgnoreCase("Deleted")) {
							model.setStrTransCode(srModel.getStrSRCode());
						} else {
							model.setStrTransCode(srModel.getStrSRCode() + "-" + count);
						}
						model.setStrClientCode(clientCode);
						model.setStrTransMode(strTransMode);
						model.setStrUserAmed(userCode);
						model.setDtLastModified(objGlobalfunction.funGetCurrentDate("yyyy-MM-dd"));
						model.setStrUserModified(userCode);
						objGlobalFunctionsService.funSaveAuditHd(model);
						for (int i = 0; i < listSalesDtl.size(); i++) {
							
							clsSalesReturnDtlModel srDtlModel = listSalesDtl.get(i);
							clsAuditDtlModel AuditMode = new clsAuditDtlModel();
							AuditMode.setStrTransCode(model.getStrTransCode());
							AuditMode.setStrProdCode(srDtlModel.getStrProdCode());
							AuditMode.setStrProdName(srDtlModel.getStrProdName());
							AuditMode.setStrUOM("");
							AuditMode.setStrAgainst("");
							AuditMode.setStrCode("");
							AuditMode.setStrRemarks(srDtlModel.getStrRemarks());
							AuditMode.setDblQty(srDtlModel.getDblQty());
							AuditMode.setDblUnitPrice(srDtlModel.getDblPrice());
							AuditMode.setDblWeight(srDtlModel.getDblWeight());
							AuditMode.setDblTax(0.00);
							AuditMode.setDblTaxableAmt(0.00);
							AuditMode.setDblTaxAmt(0.00);
							AuditMode.setStrTaxType("");
							AuditMode.setDblTotalPrice(0.00);
							AuditMode.setStrPICode("");
							AuditMode.setStrClientCode(srDtlModel.getStrClientCode());
							objGlobalFunctionsService.funSaveAuditDtl(AuditMode);
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * Prepare Audit HdModel
	 * 
	 * @param srModel
	 * @return
	 */
	private clsAuditHdModel funPrepairAuditHdModel(clsSalesReturnHdModel srModel) {
		clsAuditHdModel AuditHdModel = new clsAuditHdModel();
		if (srModel != null) {
			AuditHdModel.setStrTransCode(srModel.getStrSRCode());
			AuditHdModel.setDtTransDate(srModel.getDteSRDate());
			AuditHdModel.setStrTransType("Sales Return");
			AuditHdModel.setStrSuppCode(srModel.getStrCustCode());
			AuditHdModel.setStrAgainst(srModel.getStrAgainst());
			
			AuditHdModel.setDblDisRate(srModel.getDblDiscPer());
			AuditHdModel.setDblDiscount(srModel.getDblDiscAmt());
			
			AuditHdModel.setDblTotalAmt(Double.parseDouble(srModel.getDblTotalAmt()));
			
			AuditHdModel.setStrGRNCode(srModel.getStrDCCode());
			AuditHdModel.setStrLocCode(srModel.getStrLocCode());
			AuditHdModel.setStrNo(srModel.getStrSRCode());
			AuditHdModel.setDtDateCreated(srModel.getDteDateCreated());
			AuditHdModel.setStrUserCreated(srModel.getStrUserCreated());
			AuditHdModel.setStrClosePO("");
			AuditHdModel.setStrExcise("");
			AuditHdModel.setStrLocBy("");
			AuditHdModel.setStrLocOn("");
			AuditHdModel.setStrCloseReq("");
			AuditHdModel.setStrWoCode("");
			AuditHdModel.setStrBillNo("");
			AuditHdModel.setDblWOQty(0);
			AuditHdModel.setStrNo("");
			AuditHdModel.setStrRefNo("");
			AuditHdModel.setStrShipmentMode("");
			AuditHdModel.setStrPayMode("");
			AuditHdModel.setStrAuthorise("");
		}
		return AuditHdModel;
	}
/**
	 * End Function Audit
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void funCallReportFormat2(clsReportBean objBean,String currencyCode, HttpServletResponse resp, HttpServletRequest req) {

		String prodType = objBean.getStrProdType();
		String type = objBean.getStrDocType();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		
		Connection con = objGlobalfunction.funGetConnection(req);
		try {

			/*String strCurr = req.getSession().getAttribute("currValue").toString();
			double currValue = Double.parseDouble(strCurr);
			*/
			//double currValue = Double.parseDouble(req.getSession().getAttribute("currValue").toString());
			double currValue = 1.0;
			if(!currencyCode.isEmpty())
			{
				clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
				if (objCurrModel != null) {
					currValue = objCurrModel.getDblConvToBaseCurr();
				}
			}
			
			String srCode = "", locationCode = "", locationName = "";
			String againstName = "", custCode = "", custName = "", dcCode = "",currencyName="";
			String userCode = req.getSession().getAttribute("usercode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String strLocCode = req.getSession().getAttribute("locationCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			String reportName ="";
			if(clientCode.equals("226.001"))
			{
				reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptSalesReturnWithoutTaxSlip.jrxml");	
				
			}else{
				reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptSalesReturnWithDiscountSlip.jrxml");
			}
			
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			String userCreated="";
			String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
			String sqlDS = " select a.strSRCode, b.strLocCode, b.strLocName, a.strAgainst, a.strDCCode, a.strCustCode, "
					+ "c.strPName,ifnull(d.strCurrencyName,''),"
					+ " if((d.strCurrencyCode is null) or (d.strCurrencyCode ='NA' ) or (d.strCurrencyCode ='' ),"
					+ " (select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster where strCurrencyCode='CU00001') ,"
					+ " a.dblConversion) as strCurrency ,a.strUserCreated "
					+ " from "+webStockDB+".tblsalesreturnhd a, "+webStockDB+".tbllocationmaster b, "+webStockDB+".tblpartymaster c,"+webStockDB+".tblcurrencymaster d " 
					+ " where a.strLocCode=b.strLocCode and a.strClientCode=b.strClientCode " 
					+ " and a.strCustCode=c.strPCode and a.strCurrency=d.strCurrencyCode and a.strClientCode=b.strClientCode " 
					+ " and a.strClientCode='" + clientCode+ " ' "
					+ " and a.strSRCode='" + objBean.getStrSRCode() + "' ";

			List listds = objGlobalFunctionsService.funGetList(sqlDS, "sql");

			if (listds.size() > 0) {

				for (int i = 0; i < listds.size(); i++) {
					Object[] obProdDtl = (Object[]) listds.get(i);

					srCode = obProdDtl[0].toString();
					locationCode = obProdDtl[1].toString();
					locationName = obProdDtl[2].toString();
					againstName = obProdDtl[3].toString();
					dcCode = obProdDtl[4].toString();
					custCode = obProdDtl[5].toString();
					custName = obProdDtl[6].toString();
					currencyName= obProdDtl[7].toString();
					currencyCode= obProdDtl[8].toString();
					userCreated= obProdDtl[9].toString();
					clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
					if (objCurrModel != null) {
						currValue = objCurrModel.getDblConvToBaseCurr();
					}
				}
			}

			String sql = " select b.strProdCode, c.strProdName, b.dblQty, b.dblPrice/" + currValue + " as dblPrice,"
					+ " (b.dblQty*b.dblPrice)/" + currValue + " as dblTotalAmt, b.strRemarks,"
					+ "a.dblTotalAmt/" + currValue + " as dblTotalAmt,a.dblDiscPer as discPer"
					+ "  from "+webStockDB+".tblsalesreturnhd a, "
					+ ""+webStockDB+".tblsalesreturndtl b, "+webStockDB+".tblproductmaster c,"+webStockDB+".tblpartymaster d "
					+ " where a.strSRCode=b.strSRCode and b.strProdCode=c.strProdCode and a.strClientCode=b.strClientCode " 
					+ " and b.strClientCode=c.strClientCode and a.strClientCode='" + clientCode + "'  "
					+ "and a.strSRCode='" + objBean.getStrSRCode() + "' and d.strPCode=a.strCustCode ";

			// getting multi copy of small data in table in Detail thats why we
			// add in subDataset
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sql);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsProddtl");
			subDataset.setQuery(subQuery);

			if(!(clientCode.equals("226.001"))){
				
			String taxSummary = "select a.strTaxCode,a.strTaxDesc,a.strTaxableAmt/" + currValue + " as strTaxableAmt ,a.strTaxAmt/" + currValue + " as strTaxAmt,b.dblTotalAmt/" + currValue + " as dblTotalAmt from "+webStockDB+".tblsalesreturntaxdtl a ,"+webStockDB+".tblsalesreturnhd b " + "where a.strSRCode='" + srCode + "' and a.strClientCode='" + clientCode + "' and a.strSRCode=b.strSRCode ";
		
			JRDesignQuery taxSummQuery = new JRDesignQuery();
			taxSummQuery.setText(taxSummary);
			JRDesignDataset taxSumDataset = (JRDesignDataset) datasetMap.get("dsTaxSummary");
			taxSumDataset.setQuery(taxSummQuery);
			
			}
			
			JasperReport jr = JasperCompileManager.compileReport(jd);
			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCreated);
			hm.put("strImagePath", imagePath);
			hm.put("strAddr1", objSetup.getStrAdd1());
			hm.put("strAddr2", objSetup.getStrAdd2());
			hm.put("strCity", objSetup.getStrCity());
			hm.put("strState", objSetup.getStrState());
			hm.put("strCountry", objSetup.getStrCountry());
			hm.put("strPin", objSetup.getStrPin());
			hm.put("srCode", srCode);
			hm.put("locationCode", locationCode);
			hm.put("locationName", locationName);
			hm.put("againstName", againstName);
			hm.put("dcCode", dcCode);
			hm.put("custCode", custCode);
			hm.put("custName", custName);
			hm.put("currencyName", currencyName);

			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setContentLength(bytes.length);
				servletOutputStream.write(bytes, 0, bytes.length);
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptDeliveryScheduleSlip." + type.trim());
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptDeliveryScheduleSlip." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}
	

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadInvoiceDtlForSR", method = RequestMethod.GET)
	public @ResponseBody List<clsInvoiceDtlBean> funInvoiceHdDataForSR(@RequestParam("invCode") String invCode,@RequestParam("currValue") double currValue, HttpServletRequest req)
	{
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		List listDtl= objSalesReturnService.funGetInvDtlList(invCode, clientCode);
		List<clsInvoiceDtlBean> listInvDtlBean = new ArrayList();
		clsInvoiceDtlBean objBeanInvoice;
		for (int i = 0; i < listDtl.size(); i++)
		{
			objBeanInvoice = new clsInvoiceDtlBean();

			Object[] obj = (Object[])listDtl.get(i);
			objBeanInvoice.setStrProdCode(obj[0].toString());
			objBeanInvoice.setStrProdName(obj[8].toString());
			objBeanInvoice.setStrProdType(obj[14].toString());
			objBeanInvoice.setDblQty(Double.parseDouble(obj[4].toString()));
			objBeanInvoice.setDblWeight(Double.parseDouble(obj[6].toString()));
			objBeanInvoice.setDblUnitPrice(Double.parseDouble(obj[5].toString()) / currValue);
			objBeanInvoice.setStrPktNo(obj[16].toString());
			objBeanInvoice.setStrRemarks(obj[15].toString());
			objBeanInvoice.setDblDisAmt(Double.parseDouble(obj[10].toString())/ currValue);
			
			listInvDtlBean.add(objBeanInvoice);
		}
		
		return listInvDtlBean;
	}



}
