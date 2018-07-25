package com.sanguine.controller;

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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

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
import com.sanguine.bean.clsParentDataForBOM;
import com.sanguine.bean.clsRecipeMasterBean;
import com.sanguine.model.clsBomDtlModel;
import com.sanguine.model.clsBomHdModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsRecipeMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsRecipeMasterController {
	@Autowired
	private clsRecipeMasterService objRecipeMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	@Autowired
	private ServletContext servletContext;
	private clsGlobalFunctions objGlobal = null;

	@RequestMapping(value = "/frmBOMMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		List<String> listProcess = new ArrayList<>();
		listProcess.add("Select");
		listProcess.add("Production");
		model.put("processList", listProcess);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmBOMMaster_1", "command", new clsRecipeMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmBOMMaster", "command", new clsRecipeMasterBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/frmBOMMaster1", method = RequestMethod.POST)
	public ModelAndView funOpenFormWithBomCode(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		clsRecipeMasterBean bean = new clsRecipeMasterBean();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();

		String bomCode = request.getParameter("BOMCode").toString();
		clsBomHdModel objBomHd = objRecipeMasterService.funGetObject(bomCode, clientCode);
		bean = funPrepareBean(objBomHd, clientCode);
		List listBomDtl = objRecipeMasterService.funGetDtlList(bomCode, clientCode);
		List<clsBomDtlModel> listBOMDtlTemp = new ArrayList<clsBomDtlModel>();

		for (int i = 0; i < listBomDtl.size(); i++) {
			Object[] ob = (Object[]) listBomDtl.get(i);
			clsBomDtlModel bomDtl = (clsBomDtlModel) ob[0];
			clsProductMasterModel prodMaster = (clsProductMasterModel) ob[1];
			bomDtl.setStrProdName(prodMaster.getStrProdName());
			listBOMDtlTemp.add(bomDtl);
		}

		List<String> listProcess = new ArrayList<>();
		listProcess.add(bean.getStrProcessName());
		model.put("processList", listProcess);
		bean.setListBomDtlModel(listBOMDtlTemp);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmBOMMaster_1", "command", bean);
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmBOMMaster", "command", bean);
		} else {
			return new ModelAndView("frmBOMMaster", "command", bean);
		}

	}

	@RequestMapping(value = "/loadBOMMaster", method = RequestMethod.POST)
	public @ResponseBody clsRecipeMasterBean funLoadFormWithBomCode(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		clsRecipeMasterBean bean = new clsRecipeMasterBean();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();

		String bomCode = request.getParameter("BOMCode").toString();
		clsBomHdModel objBomHd = objRecipeMasterService.funGetObject(bomCode, clientCode);
		if (objBomHd != null) {
			bean = funPrepareBean(objBomHd, clientCode);
			List listBomDtl = objRecipeMasterService.funGetDtlList(bomCode, clientCode);
			List<clsBomDtlModel> listBOMDtlTemp = new ArrayList<clsBomDtlModel>();

			for (int i = 0; i < listBomDtl.size(); i++) {
				Object[] ob = (Object[]) listBomDtl.get(i);
				clsBomDtlModel bomDtl = (clsBomDtlModel) ob[0];
				clsProductMasterModel prodMaster = (clsProductMasterModel) ob[1];
				bomDtl.setStrProdName(prodMaster.getStrProdName());
				listBOMDtlTemp.add(bomDtl);
			}

			bean.setListBomDtlModel(listBOMDtlTemp);
		} else {
			bean.setStrBOMCode("Invalid Code");
		}

		return bean;

	}

	@RequestMapping(value = "/saveRecipeMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsRecipeMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		clsBomHdModel objHdModel = null;
		objGlobal = new clsGlobalFunctions();
		if (!result.hasErrors()) {
			List<clsBomDtlModel> listBomDtl = objBean.getListBomDtlModel();
			boolean flagDtlDataInserted = false;
			if (null != listBomDtl && listBomDtl.size() > 0) {
				objHdModel = funPrepareModel(objBean, userCode, clientCode);
				objRecipeMasterService.funAddUpdate(objHdModel);
				String bomCode = objHdModel.getStrBOMCode();
				objRecipeMasterService.funDeleteDtl(bomCode, clientCode);
				for (clsBomDtlModel ob : listBomDtl) {
					if (null != ob.getStrChildCode()) {
						ob.setStrBOMCode(bomCode);
						ob.setStrClientCode(clientCode);
						objRecipeMasterService.funAddUpdateDtl(ob);
					}
				}
				flagDtlDataInserted = true;
			}
			if (flagDtlDataInserted == true) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Recipe Code : ".concat(objHdModel.getStrBOMCode()));
			}
			return new ModelAndView("redirect:/frmBOMMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmBOMMaster.html?saddr=" + urlHits);
		}
	}

	// AssignField function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadProductData", method = RequestMethod.GET)
	public @ResponseBody clsParentDataForBOM funAssignFields(@RequestParam("prodCode") String code, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		return funGetParentDataForBOM(code, clientCode, "");
	}

	@RequestMapping(value = "/loadRecipeMaster", method = RequestMethod.GET)
	public @ResponseBody ModelAndView funAssignFields1(@RequestParam("BOMCode") String code, Map<String, Object> model, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		List<clsBomDtlModel> listBomDtl = objRecipeMasterService.funGetDtlList(code, clientCode);
		clsBomHdModel objBomHd = objRecipeMasterService.funGetObject(code, clientCode);
		clsRecipeMasterBean objBean = funPrepareBean(objBomHd, clientCode);
		objBean.setListBomDtlModel(listBomDtl);
		List<String> listProcess = new ArrayList<>();
		listProcess.add(objBean.getStrProcessName());
		model.put("processList", listProcess);
		return new ModelAndView("frmBOMMaster", "command", new clsRecipeMasterBean());
	}

	// Returns a single master record by passing code as primary key. Also
	// generates next Code if transaction is for Save Master
	private clsBomHdModel funPrepareModel(clsRecipeMasterBean objBean, String userCode, String clientCode) {
		long lastNo = 0;
		clsBomHdModel objHdModel = new clsBomHdModel();
		if (objBean.getStrBOMCode().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblbommasterhd", "BOMMaster", "intId", clientCode);
			String code = "B" + String.format("%07d", lastNo);
			objHdModel.setStrBOMCode(code);
			objHdModel.setIntId(lastNo);
			objHdModel.setStrUserCreated(userCode);
			objHdModel.setDtCreatedDate(objGlobal.funGetCurrentDate("yyyy-MM-dd"));
			objHdModel.setStrClientCode(clientCode);
		} else {
			objHdModel.setStrBOMCode(objBean.getStrBOMCode());
		}
		objHdModel.setStrParentCode(objBean.getStrParentCode());
		objHdModel.setStrProcessCode(objBean.getStrProcessCode());
		objHdModel.setDtValidFrom(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtValidFrom()));
		objHdModel.setDtValidTo(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtValidTo()));
		objHdModel.setStrUserModified(userCode);
		objHdModel.setDtLastModified(objGlobal.funGetCurrentDate("yyyy-MM-dd"));
		objHdModel.setStrUOM(objBean.getStrUOM());
		objHdModel.setDblQty(objBean.getDblQty());
		objHdModel.setStrMethod(objBean.getStrMethod());
		objHdModel.setStrBOMType("R");
		return objHdModel;
	}

	private clsRecipeMasterBean funPrepareBean(clsBomHdModel objModel, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		clsParentDataForBOM objParentData = funGetParentDataForBOM(objModel.getStrParentCode(), clientCode, objModel.getStrBOMCode());
		clsRecipeMasterBean objBean = new clsRecipeMasterBean();
		objBean.setStrBOMCode(objModel.getStrBOMCode());
		objBean.setStrParentCode(objModel.getStrParentCode());
		objBean.setStrProcessCode(objModel.getStrProcessCode());
		objBean.setDtValidFrom(objGlobal.funGetDate("yyyy/MM/dd", objModel.getDtValidFrom()));
		objBean.setDtValidTo(objGlobal.funGetDate("yyyy/MM/dd", objModel.getDtValidTo()));
		objBean.setStrProcessName(objParentData.getStrProcessName());
		objBean.setStrParentName(objParentData.getStrParentName());
		objBean.setStrPOSItemCode(objParentData.getStrPartNo());
		objBean.setStrSGCode(objParentData.getStrSGCode());
		objBean.setStrSGName(objParentData.getStrSGName());
		objBean.setStrType(objParentData.getStrProdType());
		objBean.setStrUOM(objParentData.getStrUOM());
		objBean.setDblQty(objModel.getDblQty());
		return objBean;
	}

	public clsParentDataForBOM funGetParentDataForBOM(String parentProdCode, String clientCode, String bomCode) {
		clsParentDataForBOM objParentProduct = new clsParentDataForBOM();

		String sqlCheckBom = " select a.strBOMCode from tblbommasterhd a " + " where a.strParentCode='" + parentProdCode + "' and a.strClientCode='" + clientCode + "' ";
		List listCheckBom = objRecipeMasterService.funGetProductList(sqlCheckBom);
		if (bomCode.equals("")) {
			if (listCheckBom.size() > 0) {
				objParentProduct.setStrBOMCode(listCheckBom.get(0).toString());
			} else {
				String sql = "select a.strProdCode,a.strProdName,ifnull(a.strPartNo,'') as strPartNo,ifnull(a.strProdType,'') as strProdType, " + "ifnull(b.strSGCode,'') as strSGCode, ifnull(b.strSGName,'') as strSGName, " + "ifnull(c.strProcessCode,'') as strProcessCode, ifnull(d.strProcessName,'') as strProcessName, " + "ifnull(a.strUOM,'') as strUOM " + "from tblproductmaster a "
						+ "left outer join tblsubgroupmaster b  on  a.strSGCode=b.strSGCode and b.strClientCode='" + clientCode + "' " + "left outer join tblprodprocess c on a.strProdCode=c.strProdCode and c.strClientCode='" + clientCode + "' " + "left outer join tblprocessmaster d on c.strProcessCode=d.strProcessCode and d.strClientCode='" + clientCode + "' " + "where a.strProdCode='"
						+ parentProdCode + "' and a.strClientCode='" + clientCode + "' ";
				@SuppressWarnings("rawtypes")
				List listProduct = objRecipeMasterService.funGetProductList(sql);

				if (listProduct.size() == 0) {
					objParentProduct.setStrParentCode("Invalid Product Code");
				} else {
					Object[] ob = (Object[]) listProduct.get(0);
					objParentProduct.setStrParentCode(ob[0].toString());
					objParentProduct.setStrParentName(ob[1].toString());
					objParentProduct.setStrPartNo(ob[2].toString());
					objParentProduct.setStrProdType(ob[3].toString());
					objParentProduct.setStrSGCode(ob[4].toString());
					objParentProduct.setStrSGName(ob[5].toString());
					objParentProduct.setStrProcessCode(ob[6].toString());
					objParentProduct.setStrProcessName(ob[7].toString());
					objParentProduct.setStrUOM(ob[8].toString());
					objParentProduct.setStrBOMCode("");
				}
			}

		} else {
			String sql = "select a.strProdCode,a.strProdName,ifnull(a.strPartNo,'') as strPartNo,ifnull(a.strProdType,'') as strProdType, " + "ifnull(b.strSGCode,'') as strSGCode, ifnull(b.strSGName,'') as strSGName, " + "ifnull(c.strProcessCode,'') as strProcessCode, ifnull(d.strProcessName,'') as strProcessName, " + "ifnull(a.strUOM,'') as strUOM " + "from tblproductmaster a "
					+ "left outer join tblsubgroupmaster b  on  a.strSGCode=b.strSGCode and b.strClientCode='" + clientCode + "' " + "left outer join tblprodprocess c on a.strProdCode=c.strProdCode and c.strClientCode='" + clientCode + "' " + "left outer join tblprocessmaster d on c.strProcessCode=d.strProcessCode and d.strClientCode='" + clientCode + "' " + "where a.strProdCode='"
					+ parentProdCode + "' and a.strClientCode='" + clientCode + "' ";
			@SuppressWarnings("rawtypes")
			List listProduct = objRecipeMasterService.funGetProductList(sql);

			if (listProduct.size() == 0) {
				objParentProduct.setStrParentCode("Invalid Product Code");
			} else {
				Object[] ob = (Object[]) listProduct.get(0);
				objParentProduct.setStrParentCode(ob[0].toString());
				objParentProduct.setStrParentName(ob[1].toString());
				objParentProduct.setStrPartNo(ob[2].toString());
				objParentProduct.setStrProdType(ob[3].toString());
				objParentProduct.setStrSGCode(ob[4].toString());
				objParentProduct.setStrSGName(ob[5].toString());
				objParentProduct.setStrProcessCode(ob[6].toString());
				objParentProduct.setStrProcessName(ob[7].toString());
				objParentProduct.setStrUOM(ob[8].toString());
				objParentProduct.setStrBOMCode("");
			}
		}

		return objParentProduct;
	}

	/**
	 * Report Code
	 * 
	 * @return
	 * @throws JRException
	 */
	// Jai chandra 05-01-2015

	@RequestMapping(value = "/frmRecipesList", method = RequestMethod.GET)
	public ModelAndView funOpenProductionOrderSlipForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmRecipesList_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmRecipesList", "command", new clsReportBean());
		}

	}

	@RequestMapping(value = "/rptRecipesList", method = RequestMethod.GET)
	private void funReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String ProdCode = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		funCallReport(ProdCode, type, resp, req);
	}

	@RequestMapping(value = "/invokeRecipesList", method = RequestMethod.GET)
	private void funCallReportOnClick(@RequestParam(value = "docCode") String docCode, HttpServletResponse resp, HttpServletRequest req) {
		String type = "pdf";
		funCallReport(docCode, type, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallReport(String ProdCode, String type, HttpServletResponse resp, HttpServletRequest req) {
		try {
			objGlobal = new clsGlobalFunctions();
			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);

			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptRecipesList.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlDtlQuery = "SELECT   h.strBOMCode as strBOMCode,h.strParentCode as strParentCode, " + "h.strprocesscode as strprocesscode,h.dblQty as ParentDdlQty,h.strUOM as ParentstrUOM, " + "p.strProdName as ParentProdName,ifnull(lp.strlocname,'') as parentLocation, d.strChildCode, " + "cp.strProdName as childProductName,cp.strRecipeUOM as childUOM, d.dblQty,cp.dblCostRM as  price, "
					+ "IFNULL(pr.strprocessname,'') as strprocessname,ifnull(cl.strlocname,'') as childLocation  ," + "date(h.dtCreatedDate) as dtCreatedDate,date(h.dtValidFrom) as dtValidFrom," + "date(h.dtValidTo) as dtValidTo, h.strUserCreated as strUserCreated ,((cp.dblCostRM /cp.dblRecipeConversion)*d.dblQty) as value "
					+ "from tblbommasterhd  h inner join tblbommasterdtl AS d ON h.strBOMCode = d.strBOMCode and d.strClientCode='" + clientCode + "' " + "left outer join tblproductmaster   p ON h.strParentCode = p.strProdCode and p.strClientCode='" + clientCode + "' " + "left outer join tblproductmaster AS cp ON d.strChildCode = cp.strProdCode and cp.strClientCode='" + clientCode + "' "
					+ "left outer join tbllocationmaster  lp ON lp.strLocCode = p.strLocCode and lp.strClientCode='" + clientCode + "' " + "left outer join tbllocationmaster AS cl ON cl.strLocCode = cp.strLocCode and cl.strClientCode='" + clientCode + "' " + "left outer join tblprocessmaster pr on h.strprocesscode=pr.strprocesscode and pr.strClientCode='" + clientCode + "' "
					+ "where  h.strClientCode='" + clientCode + "'";
			if (!ProdCode.equals("")) {
				sqlDtlQuery += " and h.strParentCode='" + ProdCode + "'  ";
			}

			String sqlHDQuery = "";
			JasperDesign jd = JRXmlLoader.load(reportName);
			/*
			 * JRDesignQuery newQuery= new JRDesignQuery();
			 * newQuery.setText(sqlDtlQuery); jd.setQuery(newQuery);
			 */

			JRDesignQuery subQuery = new JRDesignQuery();

			subQuery.setText(sqlDtlQuery);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsRecipesDtl");
			subDataset.setQuery(subQuery);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);

			hm.put("strAddr1", objSetup.getStrAdd1());
			hm.put("strAddr2", objSetup.getStrAdd2());
			hm.put("strCity", objSetup.getStrCity());
			hm.put("strState", objSetup.getStrState());
			hm.put("strCountry", objSetup.getStrCountry());
			hm.put("strPin", objSetup.getStrPin());

			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setContentLength(bytes.length);
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRPdfExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptRecipesList." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ///////////////////////////Yield Concept//////////////////////////////

	//
	// @RequestMapping(value = "/frmYieldMaster", method = RequestMethod.GET)
	// public ModelAndView funOpenFormYield(Map<String,Object> model,
	// HttpServletRequest request)
	// {
	// String urlHits="1";
	// try{
	// urlHits=request.getParameter("saddr").toString();
	// }catch(NullPointerException e){
	// urlHits="1";
	// }
	// model.put("urlHits",urlHits);
	// List<String> listProcess = new ArrayList<>();
	// listProcess.add("Select");
	// listProcess.add("Production");
	// model.put("processList", listProcess);
	// if("2".equalsIgnoreCase(urlHits)){
	// return new ModelAndView("frmYieldMaster_1","command", new
	// clsRecipeMasterBean());
	// }else if("1".equalsIgnoreCase(urlHits)){
	// return new ModelAndView("frmYieldMaster","command", new
	// clsRecipeMasterBean());
	// }else {
	// return null;
	// }
	//
	// }
	//
	//
	//
	// @RequestMapping(value = "/frmYieldMaster1", method = RequestMethod.POST)
	// public ModelAndView funOpenFormWithYieldCode(Map<String,Object> model,
	// HttpServletRequest request)
	// {
	// String urlHits="1";
	// try{
	// urlHits=request.getParameter("saddr").toString();
	// }catch(NullPointerException e){
	// urlHits="1";
	// }
	// model.put("urlHits",urlHits);
	// clsRecipeMasterBean bean=new clsRecipeMasterBean();
	// String
	// clientCode=request.getSession().getAttribute("clientCode").toString();
	// String userCode=request.getSession().getAttribute("usercode").toString();
	//
	// String bomCode=request.getParameter("BOMCode").toString();
	// clsBomHdModel
	// objBomHd=objRecipeMasterService.funGetObject(bomCode,clientCode);
	// bean = funPrepareBean(objBomHd,clientCode);
	// List listBomDtl=objRecipeMasterService.funGetDtlList(bomCode,clientCode);
	// List<clsBomDtlModel> listBOMDtlTemp = new ArrayList<clsBomDtlModel>();
	//
	// for(int i=0;i<listBomDtl.size();i++)
	// {
	// Object[] ob = (Object[])listBomDtl.get(i);
	// clsBomDtlModel bomDtl=(clsBomDtlModel)ob[0];
	// clsProductMasterModel prodMaster=(clsProductMasterModel)ob[1];
	// bomDtl.setStrProdName(prodMaster.getStrProdName());
	// listBOMDtlTemp.add(bomDtl);
	// }
	//
	// List<String> listProcess = new ArrayList<>();
	// listProcess.add(bean.getStrProcessName());
	// model.put("processList", listProcess);
	// bean.setListBomDtlModel(listBOMDtlTemp);
	//
	//
	// if("2".equalsIgnoreCase(urlHits)){
	// return new ModelAndView("frmYieldMaster_1","command",bean);
	// }else if("1".equalsIgnoreCase(urlHits)){
	// return new ModelAndView("frmYieldMaster","command",bean);
	// }else {
	// return new ModelAndView("frmYieldMaster","command",bean);
	// }
	//
	// }
	//
	//
	// @RequestMapping(value = "/saveYieldMaster", method = RequestMethod.POST)
	// public ModelAndView funSaveUpdate(@ModelAttribute("command") @Valid
	// clsRecipeMasterBean objBean,BindingResult result,HttpServletRequest req)
	// {
	// String urlHits="1";
	// try{
	// urlHits=req.getParameter("saddr").toString();
	// }catch(NullPointerException e){
	// urlHits="1";
	// }
	// String clientCode=req.getSession().getAttribute("clientCode").toString();
	// String userCode=req.getSession().getAttribute("usercode").toString();
	// clsBomHdModel objHdModel=null;
	// objGlobal=new clsGlobalFunctions();
	// if(!result.hasErrors())
	// {
	// List<clsBomDtlModel> listBomDtl = objBean.getListBomDtlModel();
	// boolean flagDtlDataInserted = false;
	// if(null != listBomDtl && listBomDtl.size() > 0)
	// {
	// objHdModel=funPrepareYieldModel(objBean,userCode,clientCode);
	// objRecipeMasterService.funAddUpdate(objHdModel);
	// String bomCode=objHdModel.getStrBOMCode();
	// objRecipeMasterService.funDeleteDtl(bomCode,clientCode);
	// for (clsBomDtlModel ob : listBomDtl)
	// {
	// if(null!=ob.getStrChildCode())
	// {
	// ob.setStrBOMCode(bomCode);
	// ob.setStrClientCode(clientCode);
	// objRecipeMasterService.funAddUpdateDtl(ob);
	// }
	// }
	// flagDtlDataInserted=true;
	// }
	// if(flagDtlDataInserted==true)
	// {
	// req.getSession().setAttribute("success", true);
	// req.getSession().setAttribute("successMessage","Recipe Code : ".concat(objHdModel.getStrBOMCode()));
	// }
	// return new ModelAndView("redirect:/frmBOMMaster.html?saddr="+urlHits);
	// }
	// else
	// {
	// return new ModelAndView("frmBOMMaster?saddr="+urlHits);
	// }
	// }
	//
	//
	// private clsBomHdModel funPrepareYieldModel(clsRecipeMasterBean
	// objBean,String userCode,String clientCode)
	// {
	// long lastNo=0;
	// clsBomHdModel objHdModel = new clsBomHdModel();
	// if(objBean.getStrBOMCode().length()==0)
	// {
	// lastNo=objGlobalFunctionsService.funGetLastNo("tblbommasterhd","BOMMaster","intId",
	// clientCode);
	// String code = "B" + String.format("%07d", lastNo);
	// objHdModel.setStrBOMCode(code);
	// objHdModel.setIntId(lastNo);
	// objHdModel.setStrUserCreated(userCode);
	// objHdModel.setDtCreatedDate(objGlobal.funGetCurrentDate("yyyy-MM-dd"));
	// objHdModel.setStrClientCode(clientCode);
	// }
	// else
	// {
	// objHdModel.setStrBOMCode(objBean.getStrBOMCode());
	// }
	// objHdModel.setStrParentCode(objBean.getStrParentCode());
	// objHdModel.setStrProcessCode(objBean.getStrProcessCode());
	// objHdModel.setDtValidFrom(objGlobal.funGetDate("yyyy-MM-dd",objBean.getDtValidFrom()));
	// objHdModel.setDtValidTo(objGlobal.funGetDate("yyyy-MM-dd",objBean.getDtValidTo()));
	// objHdModel.setStrUserModified(userCode);
	// objHdModel.setDtLastModified(objGlobal.funGetCurrentDate("yyyy-MM-dd"));
	// objHdModel.setStrUOM(objBean.getStrUOM());
	// objHdModel.setDblQty(objBean.getDblQty());
	// objHdModel.setStrMethod(objBean.getStrMethod());
	// objHdModel.setStrBOMType("Y");
	// return objHdModel;
	// }
	//
	//
	//
	//

}
