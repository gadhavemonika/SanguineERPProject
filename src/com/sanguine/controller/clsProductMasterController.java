package com.sanguine.controller;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.sanguine.bean.clsProductMasterBean;
import com.sanguine.model.clsAttributeMasterModel;
import com.sanguine.model.clsAttributeValueMasterModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProcessMasterModel;
import com.sanguine.model.clsProdAttMasterModel;
import com.sanguine.model.clsProdAttMasterModel_ID;
import com.sanguine.model.clsProdCharMasterModel;
import com.sanguine.model.clsProdProcessModel;
import com.sanguine.model.clsProdSuppMasterModel;
import com.sanguine.model.clsProdSuppMasterModel_ID;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsProductMasterModel_ID;
import com.sanguine.model.clsProductReOrderLevelModel;
import com.sanguine.model.clsProductReOrderLevelModel_ID;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsSubGroupMasterModel;
import com.sanguine.model.clsSupplierMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsGroupMasterService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsProcessMasterService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsSubGroupMasterService;
import com.sanguine.service.clsSupplierMasterService;
import com.sanguine.service.clsUOMService;
import com.sanguine.util.clsReportBean;
import com.sanguine.util.clsSupplierWiseProductModel;

@Controller
public class clsProductMasterController {
	@Autowired
	private clsProductMasterService objProductMasterService;

	@Autowired
	private clsProcessMasterService objProcessService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	
	@Autowired
	private clsLocationMasterService objLocationMasterService;

	@Autowired
	private clsUOMService objclsUOMService;

	@Autowired
	private ServletContext servletContext;
	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsGroupMasterService objGrpMasterService;

	@Autowired
	private clsSupplierMasterService objSupplierMasterService;

	@Autowired
	clsGlobalFunctions objGlobal;

	@Autowired
	private clsSubGroupMasterService objSubGrpMasterService;

	List<String> data = new ArrayList<String>();

	@RequestMapping(value = "/AutoCompletGetproductName", method = RequestMethod.POST)
	public @ResponseBody Set<String> getProductNames(@RequestParam String term, HttpServletResponse response) {
		return simulateSearchResult(term);

	}

	/*
	 * @param empName
	 * 
	 * @return
	 */
	private Set<String> simulateSearchResult(String prodName) {
		Set<String> result = new HashSet<String>();
		// iterate a list and filter by ProductName
		for (String Prod : data) {
			if (Prod.contains(prodName.toUpperCase())) {
				result.add(Prod);
			}
		}
		return result;
	}

	@RequestMapping(value = "/frmProductMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String sql = "select strProdName from tblproductmaster";
		@SuppressWarnings("rawtypes")
		List list = objGlobalFunctionsService.funGetList(sql, "sql");
		for (int i = 0; i < list.size(); i++) {
			String prodName = list.get(i).toString();
			data.add(prodName.toUpperCase());
		}
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		// String
		// propertyCode=request.getSession().getAttribute("propertyCode").toString();

		objGlobal = new clsGlobalFunctions();
		List<String> listTaxIndicator = new ArrayList<>();
		listTaxIndicator.add(" ");
		String[] alphabetSet = objGlobal.funGetAlphabetSet();
		for (int i = 0; i < alphabetSet.length; i++) {
			listTaxIndicator.add(alphabetSet[i]);
		}
		model.put("taxIndicatorList", listTaxIndicator);
		List<String> uomList = new ArrayList<String>();
		uomList = objclsUOMService.funGetUOMList(clientCode);
		model.put("uomList", uomList);
		List<String> listType = new ArrayList<String>();
		listType.add("Procured");
		listType.add("Produced");
		listType.add("Sub-Contracted");
		listType.add("Tools");
		listType.add("Service");
		listType.add("Labour");
		listType.add("Overhead");
		listType.add("Scrap");
		listType.add("Non-Inventory");
		listType.add("Trading");
		model.put("typeList", listType);

		List<String> listCalAmtOn = new ArrayList<>();
		listCalAmtOn.add("Quantity");
		listCalAmtOn.add("Weight");
		model.put("calAmtOnList", listCalAmtOn);

		List<String> listClass = new ArrayList<>();
		listClass.add("A");
		listClass.add("B");
		listClass.add("C");
		model.put("classList", listClass);
		List<String> listbomCal = new ArrayList<>();
		listbomCal.add("First Level");
		listbomCal.add("Last Level");
		model.put("bomCalList", listbomCal);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmProductMaster_1", "command", new clsProductMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmProductMaster", "command", new clsProductMasterBean());
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/frmProductMaster1", method = RequestMethod.POST)
	public ModelAndView funOpenFormWithSACode(Map<String, Object> model, HttpServletRequest req) {
		List<String> listTaxIndicator = new ArrayList<String>();
		listTaxIndicator.add("A");
		listTaxIndicator.add("B");
		listTaxIndicator.add("C");
		model.put("taxIndicatorList", listTaxIndicator);

		List<String> listType = new ArrayList<String>();

		listType.add("Procured");
		listType.add("Produced");
		listType.add("Sub-Contracted");
		listType.add("Tools");
		listType.add("Service");
		listType.add("Labour");
		listType.add("Overhead");
		listType.add("Scrap");
		listType.add("Non-Inventory");
		listType.add("Trading");
		model.put("typeList", listType);

		List<String> listCalAmtOn = new ArrayList<>();

		listCalAmtOn.add("Quantity");
		listCalAmtOn.add("Weight");
		model.put("calAmtOnList", listCalAmtOn);

		List<String> listClass = new ArrayList<>();
		listClass.add(" ");
		listClass.add("A");
		listClass.add("B");
		listClass.add("C");
		model.put("classList", listClass);

		List<String> listbomCal = new ArrayList<>();

		listbomCal.add("First Level");
		listbomCal.add("Last Level");
		model.put("bomCalList", listbomCal);

		String productCode = req.getParameter("prodCode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		// String userCode=req.getSession().getAttribute("usercode").toString();
		clsProductMasterBean objProdMasterBean = new clsProductMasterBean();

		List listProdSuppModel = objProductMasterService.funGetProdSuppList(productCode, clientCode);
		objProdMasterBean.setListProdSupp(listProdSuppModel);
		objProdMasterBean.setListProdAtt(objProductMasterService.funGetProdAttributeList(productCode, clientCode));
		objProdMasterBean.setListProdProcess(objProductMasterService.funGetProdProcessList(productCode, clientCode));
		objProdMasterBean.setListReorderLvl(objProductMasterService.funGetProdReOrderList(productCode, clientCode));

		return new ModelAndView("frmProductMaster", "command", objProdMasterBean);
	}

	@RequestMapping(value = "/saveProductMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsProductMasterBean objBean, BindingResult result, HttpServletRequest req, HttpServletResponse resp, @RequestParam("prodImage") MultipartFile file) throws IOException {
		String urlHits = "1";
		String defaulltSupplier = "", strProductNameMarathi = "";
		try {
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");
			urlHits = req.getParameter("saddr").toString();
			defaulltSupplier = req.getParameter("defaultSupplier").toString();
			strProductNameMarathi = req.getParameter("txtProdNameMarathi").toString();
			objBean.setStrProdNameMarathi(strProductNameMarathi);
		} catch (Exception e) {
			urlHits = "1";
		}
		objGlobal = new clsGlobalFunctions();
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsProductMasterModel objGeneralModel = funPrepareModelGeneral(objBean, userCode, clientCode, file);

			objProductMasterService.funAddUpdateGeneral(objGeneralModel);

			if (objGeneralModel.getStrRecipeUOM().trim().length() == 0 && objGeneralModel.getStrIssueUOM().trim().length() == 0) {
				String sqlUpdateConversion = "update tblproductmaster set strIssueUOM='" + objGeneralModel.getStrUOM() + "'" + ",strRecipeUOM='" + objGeneralModel.getStrUOM() + "' where strProdCode='" + objGeneralModel.getStrProdCode() + "' " + "and strClientCode='" + objGeneralModel.getStrClientCode() + "'";
				objGlobalFunctionsService.funUpdate(sqlUpdateConversion, "sql");
			}

			List<clsProdSuppMasterModel> listProdSuppMaster = objBean.getListProdSupp();
			List<clsProdSuppMasterModel> listobjOldModel = new ArrayList<clsProdSuppMasterModel>();
			List<clsProdSuppMasterModel> listProdCustMargin = objBean.getListProdCustMargin();

			if (null != listProdSuppMaster) {
				for (int counter = 0; counter < listProdSuppMaster.size(); counter++) {
					clsProdSuppMasterModel objProdSupp = (clsProdSuppMasterModel) listProdSuppMaster.get(counter);
					if (null != objProdSupp.getStrSuppCode()) {
						clsProdSuppMasterModel objOldModel = objProductMasterService.funGetProdSuppMasterModel(objProdSupp.getStrSuppCode(), objGeneralModel.getStrProdCode(), clientCode);
						listobjOldModel.add(objOldModel);
					}
				}
			}

			objProductMasterService.funDeleteProdSupp(objGeneralModel.getStrProdCode(), clientCode);
			if (null != listProdSuppMaster) {
				for (int counter = 0; counter < listProdSuppMaster.size(); counter++) {
					clsProdSuppMasterModel objProdSupp = (clsProdSuppMasterModel) listProdSuppMaster.get(counter);
					if (null != objProdSupp.getStrSuppCode()) {
						clsProdSuppMasterModel_ID clsProdSuppMasterModel_ID = new clsProdSuppMasterModel_ID(objProdSupp.getStrSuppCode(), objGeneralModel.getStrProdCode(), clientCode);
						objGlobal = new clsGlobalFunctions();
						clsSupplierMasterModel objSuppModel = objSupplierMasterService.funGetObject(objProdSupp.getStrSuppCode(), clientCode);
						clsProdSuppMasterModel ob = new clsProdSuppMasterModel(clsProdSuppMasterModel_ID);
						ob.setStrSuppUOM(objGlobal.funIfNull(objProdSupp.getStrSuppUOM(), "", objProdSupp.getStrSuppUOM()));
						ob.setDtLastDate(objGlobal.funGetDate("yyyy-MM-dd", objProdSupp.getDtLastDate()));
						ob.setStrLeadTime(objGlobal.funIfNull(objProdSupp.getStrLeadTime(), "", objProdSupp.getStrLeadTime()));
						ob.setStrDefault(objGlobal.funIfNull(objProdSupp.getStrDefault(), "N", "Y"));
						if (defaulltSupplier.equalsIgnoreCase(objProdSupp.getStrSuppCode())) {
							ob.setStrDefault("Y");
						}
						ob.setStrSuppPartNo(objGlobal.funIfNull(objProdSupp.getStrSuppPartNo(), "", objProdSupp.getStrSuppPartNo()));
						ob.setStrSuppPartDesc(objGlobal.funIfNull(objProdSupp.getStrSuppPartDesc(), "", objProdSupp.getStrSuppPartDesc()));
						if (objSuppModel.getStrPType().equals("cust")) {
							if (!(listobjOldModel.isEmpty())) {
								clsProdSuppMasterModel objOldModel = listobjOldModel.get(counter);
								ob.setDblLastCost(objGeneralModel.getDblMRP());
								ob.setDblStandingOrder(objOldModel.getDblStandingOrder());
								ob.setDblMargin(objOldModel.getDblMargin());
							}

						} else {
							ob.setDblLastCost(objProdSupp.getDblLastCost());
						}
						ob.setDblMaxQty(objProdSupp.getDblMaxQty());
						objProductMasterService.funAddUpdateProdSupplier(ob);
					}
				}
			}

			List<clsProdAttMasterModel> listProdAttMaster = objBean.getListProdAtt();
			if (null != listProdAttMaster) {
				objProductMasterService.funDeleteProdAttr(objGeneralModel.getStrProdCode(), clientCode);
				for (int counter = 0; counter < listProdAttMaster.size(); counter++) {
					clsProdAttMasterModel objProdAtt = (clsProdAttMasterModel) listProdAttMaster.get(counter);
					if (null != objProdAtt.getStrAttCode()) {
						clsProdAttMasterModel_ID clsProdAttMasterModel_ID = new clsProdAttMasterModel_ID(objGeneralModel.getStrProdCode(), objProdAtt.getStrAttCode(), clientCode);
						clsProdAttMasterModel ob = new clsProdAttMasterModel(clsProdAttMasterModel_ID);
						objProductMasterService.funAddUpdateProdAttribute(ob);
					}
				}
			}

			List<clsProdProcessModel> listProdProcessMaster = objBean.getListProdProcess();
			if (null != listProdProcessMaster) {
				objProductMasterService.funDeleteProdProcess(objGeneralModel.getStrProdCode(), clientCode);
				for (int counter = 0; counter < listProdProcessMaster.size(); counter++) {
					clsProdProcessModel objProdProcess = (clsProdProcessModel) listProdProcessMaster.get(counter);
					clsProdProcessModel objProdProcessModel = funPrepareModelProcess(objProdProcess);
					if (null != objProdProcessModel.getStrProcessCode()) {
						objProdProcessModel.setStrProdProcessCode(objGeneralModel.getStrProdCode());
						objProdProcessModel.setStrClientCode(clientCode);
						objProductMasterService.funAddUpdateProdProcess(objProdProcessModel);
					}
				}
			}

			List<clsProductReOrderLevelModel> listProdReOrder = objBean.getListReorderLvl();
			objProductMasterService.funDeleteProdReorder(objGeneralModel.getStrProdCode(), clientCode);
			if (null != listProdReOrder) {
				for (int counter = 0; counter < listProdReOrder.size(); counter++) {
					clsProductReOrderLevelModel objTempReorderLevel = (clsProductReOrderLevelModel) listProdReOrder.get(counter);
					if (null != objTempReorderLevel.getStrLocationCode()) {
						clsProductReOrderLevelModel objProdReOrder = new clsProductReOrderLevelModel(new clsProductReOrderLevelModel_ID(objTempReorderLevel.getStrLocationCode(), clientCode, objGeneralModel.getStrProdCode()));
						objProdReOrder.setDblReOrderLevel(objTempReorderLevel.getDblReOrderLevel());
						objProdReOrder.setDblReOrderQty(objTempReorderLevel.getDblReOrderQty());
						objProdReOrder.setDblPrice(objTempReorderLevel.getDblPrice());
						objProductMasterService.funAddUpdateProdReOrderLvl(objProdReOrder);
					}
				}
			}

			List<clsProdCharMasterModel> listProdChar = objBean.getListProdChar();
			if (null != listProdChar) {
				objProductMasterService.funDeleteProdChar(objGeneralModel.getStrProdCode(), "", "", clientCode);
				for (int counter = 0; counter < listProdChar.size(); counter++) {
					clsProdCharMasterModel objProdCharModel = (clsProdCharMasterModel) listProdChar.get(counter);
					if (null != objProdCharModel.getStrCharCode()) {

						clsProdCharMasterModel objProdChar = new clsProdCharMasterModel();
						objProdChar.setStrProcessCode(objProdCharModel.getStrProcessCode());
						objProdChar.setStrProdCode(objGeneralModel.getStrProdCode());
						objProdChar.setStrCharCode(objProdCharModel.getStrCharCode());
						objProdChar.setStrGaugeNo(objProdCharModel.getStrGaugeNo());
						objProdChar.setStrSpecf(objProdCharModel.getStrSpecf());
						objProdChar.setStrClientCode(clientCode);
						objProdChar.setStrTollerance(objProdCharModel.getStrTollerance());
						objProductMasterService.funAddUpdateProdChar(objProdChar);
					}
				}
			}

			if (null != listProdCustMargin) {
				objProductMasterService.funDeleteProdSupp(objGeneralModel.getStrProdCode(), clientCode);
				for (int counter = 0; counter < listProdCustMargin.size(); counter++) {
					clsProdSuppMasterModel objProdSupp = (clsProdSuppMasterModel) listProdCustMargin.get(counter);
					if (objProdSupp.getDblStandingOrder() > 0) {
						// objProductMasterService.funDeleteProdSuppWise(objProdSupp.getStrSuppCode(),
						// objGeneralModel.getStrProdCode(), clientCode);

						clsProdSuppMasterModel_ID clsProdSuppMasterModel_ID = new clsProdSuppMasterModel_ID(objProdSupp.getStrSuppCode(), objGeneralModel.getStrProdCode(), clientCode);
						objGlobal = new clsGlobalFunctions();
						clsSupplierMasterModel objSuppModel = objSupplierMasterService.funGetObject(objProdSupp.getStrSuppCode(), clientCode);
						clsProdSuppMasterModel ob = new clsProdSuppMasterModel(clsProdSuppMasterModel_ID);

						ob.setStrSuppCode(objProdSupp.getStrSuppCode());
						ob.setStrProdCode(objGeneralModel.getStrProdCode());
						ob.setDblLastCost(objGeneralModel.getDblMRP());
						ob.setStrSuppUOM("");
						ob.setDtLastDate(objGeneralModel.getDtLastModified());
						ob.setStrClientCode(clientCode);
						ob.setDblMargin(objProdSupp.getDblMargin());
						ob.setDblStandingOrder(objProdSupp.getDblStandingOrder());

						objProductMasterService.funAddUpdateProdSupplier(ob);
					}

				}
			}

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Product Code : ".concat(objGeneralModel.getStrProdCode()));
			return new ModelAndView("redirect:/frmProductMaster.html?saddr=" + urlHits);
		}
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmProductMaster_1", "command", new clsProductMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmProductMaster", "command", new clsProductMasterBean());
		} else {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getProductCount", method = RequestMethod.GET)
	public @ResponseBody int funGetProductCountFromTransactions(@RequestParam("prodCode") String prodCode, HttpServletRequest req) {
		int prodCount = 0;
		objGlobal = new clsGlobalFunctions();
		// String
		// clientCode=req.getSession().getAttribute("clientCode").toString();
		String sql = "select strProdCode from tblproductmaster where strProdCode='" + prodCode + "' and " + "(strProdCode in (select strProdCode as ProductCode from tblmisdtl) or " + "strProdCode in (select strProdCode as ProductCode from tblgrndtl) or " + "strProdCode in (select strProdCode as ProductCode from tblmaterialreturndtl) or "
				+ "strProdCode in (select strProdCode as ProductCode from tblpurchaseindenddtl) or " + "strProdCode in (select strProdCode as ProductCode from tblpurchaseorderdtl) or " + "strProdCode in (select strProdCode as ProductCode from tblpurchasereturndtl) or " + "strProdCode in (select strProdCode as ProductCode from tblinitialinvdtl) or "
				+ "strProdCode in (select strProdCode as ProductCode from tblstockadjustmentdtl) or " + "strProdCode in (select strProdCode as ProductCode from tblstockpostingdtl) or " + "strProdCode in (select strProdCode as ProductCode from tblstocktransferdtl) or " + "strProdCode in (select strProdCode as ProductCode from tblproductiondtl) or "
				+ "strProdCode in (select strProdCode as ProductCode from tblproductionorderdtl) or " + "strProdCode in (select strProductCode as ProductCode from tblratecontdtl))";

		List listProdCountInTrans = objGlobalFunctionsService.funGetList(sql, "sql");
		if (null != listProdCountInTrans) {
			prodCount = listProdCountInTrans.size();
		}

		return prodCount;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadSupplierData", method = RequestMethod.GET)
	public @ResponseBody List funAssignProdSupplier(@RequestParam("prodCode") String prodCode, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List<clsProdSuppMasterModel> listProdSupp = new ArrayList<clsProdSuppMasterModel>();
		List listObjects = objProductMasterService.funGetProdSuppList(prodCode, clientCode);
		for (int i = 0; i < listObjects.size(); i++) {
			Object[] ob = (Object[]) listObjects.get(i);
			clsProdSuppMasterModel objProdSupplier = (clsProdSuppMasterModel) ob[0];
			clsSupplierMasterModel objSupplier = (clsSupplierMasterModel) ob[1];
			objProdSupplier.setStrSuppName(objSupplier.getStrPName());
			objProdSupplier.setDtLastDate(objGlobal.funGetDate("yyyy/MM/dd", objProdSupplier.getDtLastDate()));
			listProdSupp.add(objProdSupplier);
		}
		return listProdSupp;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadAttrData", method = RequestMethod.GET)
	public @ResponseBody List funAssignProdAttribute(@RequestParam("prodCode") String prodCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List<clsProdAttMasterModel> listProdAttr = new ArrayList<clsProdAttMasterModel>();
		List listObjects = objProductMasterService.funGetProdAttributeList(prodCode, clientCode);
		for (int i = 0; i < listObjects.size(); i++) {
			Object[] ob = (Object[]) listObjects.get(i);
			clsProdAttMasterModel objProdAttr = (clsProdAttMasterModel) ob[0];
			clsAttributeMasterModel objAttribute = (clsAttributeMasterModel) ob[1];
			objProdAttr.setStrAttName(objAttribute.getStrAttName());
			listProdAttr.add(objProdAttr);
		}
		return listProdAttr;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadProdProcessData", method = RequestMethod.GET)
	public @ResponseBody List funAssignProdProcess(@RequestParam("prodCode") String prodCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List<clsProdProcessModel> listProdProcess = new ArrayList<clsProdProcessModel>();
		List listObjects = objProductMasterService.funGetProdProcessList(prodCode, clientCode);
		for (int i = 0; i < listObjects.size(); i++) {
			Object[] ob = (Object[]) listObjects.get(i);
			clsProdProcessModel objProdProcess = (clsProdProcessModel) ob[0];
			clsProcessMasterModel objProcess = (clsProcessMasterModel) ob[1];
			objProdProcess.setStrProcessName(objProcess.getStrProcessName());
			listProdProcess.add(objProdProcess);
		}
		return listProdProcess;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadProdReorderData", method = RequestMethod.GET)
	public @ResponseBody List funAssignProdReorder(@RequestParam("prodCode") String prodCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List<clsProductReOrderLevelModel> listProdReorder = new ArrayList<clsProductReOrderLevelModel>();
		List listObjects = objProductMasterService.funGetProdReOrderList(prodCode, clientCode);
		for (int i = 0; i < listObjects.size(); i++) {
			Object[] ob = (Object[]) listObjects.get(i);
			clsProductReOrderLevelModel objProdReorder = (clsProductReOrderLevelModel) ob[0];
			clsLocationMasterModel objLocation = (clsLocationMasterModel) ob[1];
			objProdReorder.setStrLocationName(objLocation.getStrLocName());
			listProdReorder.add(objProdReorder);
		}
		return listProdReorder;
	}

	// @RequestMapping(value = "/loadProcessData1", method = RequestMethod.GET)
	// public @ResponseBody clsProcessMasterModel
	// funAssignProcess(@RequestParam("processCode") String processCode,
	// HttpServletRequest req)
	// {
	// String clientCode=req.getSession().getAttribute("clientCode").toString();
	// clsProcessMasterModel
	// objProcessMaster=objProcessService.funGetProcessMaster(processCode,
	// clientCode);
	// return objProcessMaster;
	// }

	@RequestMapping(value = "/loadProductMasterData", method = RequestMethod.GET)
	public @ResponseBody clsProductMasterModel funAssignFields(@RequestParam("prodCode") String prodCode, HttpServletRequest req, HttpServletResponse response) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsProductMasterModel objModel = null;

		String propCode = req.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetUp = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
		objGlobal = new clsGlobalFunctions();
		// For Accoding to Bar Code checking length

		if (objSetUp.getStrMultiCurrency().equalsIgnoreCase("N")) {
			if (prodCode.length() > 8) {
				objModel = objProductMasterService.funGetBarCodeProductObject(prodCode, clientCode);
			} else {
				objModel = objProductMasterService.funGetObject(prodCode, clientCode);
			}
		} else {

			double dblreOrderPrice = 0;
			String locCode = req.getSession().getAttribute("locationCode").toString();
			clsProductReOrderLevelModel objReOrder = objProductMasterService.funGetProdReOrderLvl(prodCode, locCode, clientCode);
			if (objReOrder != null) {
				dblreOrderPrice = objReOrder.getDblPrice();
			}

			if (prodCode.length() > 8) {
				objModel = objProductMasterService.funGetBarCodeProductObject(prodCode, clientCode);
			} else {
				objModel = objProductMasterService.funGetObject(prodCode, clientCode);
			}

			objModel.setDblCostRM(dblreOrderPrice);
		}

		if (null == objModel) {
			clsProductMasterModel objModel1 = new clsProductMasterModel();
			objModel1.setStrProdCode("Invalid Code");
			return objModel1;
		} else {
			return objModel;
		}
	}

	@RequestMapping(value = "/loadProductMasterDataforDblCostRM", method = RequestMethod.GET)
	public @ResponseBody clsProductMasterModel funAssignFieldsForDblCostRM(@RequestParam("prodCode") String prodCode, HttpServletRequest req, HttpServletResponse response) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsProductMasterModel objModel = null;

		String propCode = req.getSession().getAttribute("propertyCode").toString();
		if (prodCode.length() > 8) {
			objModel = objProductMasterService.funGetBarCodeProductObject(prodCode, clientCode);
		} else {
			objModel = objProductMasterService.funGetObject(prodCode, clientCode);
		}

		if (null == objModel) {
			clsProductMasterModel objModel1 = new clsProductMasterModel();
			objModel1.setStrProdCode("Invalid Code");
			return objModel1;
		} else {
			return objModel;
		}
	}

	@RequestMapping(value = "/getProdImage", method = RequestMethod.GET)
	public void getImage(@RequestParam("prodCode") String prodCode, HttpServletRequest req, HttpServletResponse response) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsProductMasterModel objModel = null;
		if (prodCode.length() > 8) {
			objModel = objProductMasterService.funGetBarCodeProductObject(prodCode, clientCode);
		} else {
			objModel = objProductMasterService.funGetObject(prodCode, clientCode);
		}

		try {
			Blob image = null;
			byte[] imgData = null;
			image = objModel.getStrProductImage();
			if (null != image && image.length() > 0) {
				imgData = image.getBytes(1, (int) image.length());
				response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
				OutputStream o = response.getOutputStream();
				o.write(imgData);
				o.flush();
				o.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/loadAttrMaster", method = RequestMethod.GET)
	public @ResponseBody clsAttributeMasterModel funAssignAttr(@RequestParam("attCode") String attrCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		@SuppressWarnings("rawtypes")
		List listAttribute = objProductMasterService.funGetAttrObject(attrCode, clientCode);
		if (listAttribute.size() == 0) {
			clsAttributeMasterModel objAttrModel = new clsAttributeMasterModel();
			objAttrModel.setStrAttCode("Invalid Code");
			return objAttrModel;
		} else {
			Object[] ob = (Object[]) listAttribute.get(0);
			clsAttributeMasterModel objAttrModel = (clsAttributeMasterModel) ob[0];
			clsAttributeValueMasterModel objTempAttrValue = (clsAttributeValueMasterModel) ob[1];
			objAttrModel.setStrAVCode(objTempAttrValue.getStrAVCode());
			return objAttrModel;
		}
	}

	private clsProductMasterModel funPrepareModelGeneral(clsProductMasterBean objBean, String userCode, String clientCode, MultipartFile file) throws IOException {
		long lastNo = 0;
		clsProductMasterModel objModel;
		if (objBean.getStrProdCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblproductmaster", "ProductMaster", "intId", clientCode);
			String productCode = "P" + String.format("%07d", lastNo);
			objModel = new clsProductMasterModel(new clsProductMasterModel_ID(productCode, clientCode));
			objModel.setIntId(lastNo);
			objModel.setStrUserCreated(userCode);
			objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			if (file.getSize() != 0) {
				System.out.println(file.getOriginalFilename());
				File imgFolder = new File(System.getProperty("user.dir") + "\\ProductIcon");
				if (!imgFolder.exists()) {
					if (imgFolder.mkdir()) {
						System.out.println("Directory is created! " + imgFolder.getAbsolutePath());
					} else {
						System.out.println("Failed to create directory!");
					}
				}
				File fileImageIcon = new File(System.getProperty("user.dir") + "\\ProductIcon\\" + file.getOriginalFilename());
				String formatName = "jpg";
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				BufferedImage bufferedImage = ImageIO.read(funInputStreamToBytearrayInputStrean(file.getInputStream()));
				String path = fileImageIcon.getPath().toString();
				ImageIO.write(bufferedImage, "jpg", new File(path));
				BufferedImage bfImg = scaleImage(200, 240, path);
				ImageIO.write(bfImg, "jpg", byteArrayOutputStream);
				byte[] imageBytes = byteArrayOutputStream.toByteArray();
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);

				Blob blobProdImage = Hibernate.createBlob(byteArrayInputStream);
				objModel.setStrProductImage(blobProdImage);

				if (fileImageIcon.exists()) {
					fileImageIcon.delete();
				}
			} else {
				objModel.setStrProductImage(funBlankBlob());
			}
		} else {
			clsProductMasterModel objModel1 = objProductMasterService.funGetObject(objBean.getStrProdCode(), clientCode);
			if (null == objModel1) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblproductmaster", "ProductMaster", "intId", clientCode);
				String productCode = "P" + String.format("%07d", lastNo);
				objModel = new clsProductMasterModel(new clsProductMasterModel_ID(productCode, clientCode));
				objModel.setIntId(lastNo);
				objModel.setStrUserCreated(userCode);
				objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setStrProductImage(funBlankBlob());
			} else {
				objModel = new clsProductMasterModel(new clsProductMasterModel_ID(objBean.getStrProdCode(), clientCode));
				if (file.getSize() != 0) {

					File imgFolder = new File(System.getProperty("user.dir") + "\\ProductIcon");
					if (!imgFolder.exists()) {
						if (imgFolder.mkdir()) {
							System.out.println("Directory is created! " + imgFolder.getAbsolutePath());
						} else {
							System.out.println("Failed to create directory!");
						}
					}
					File fileImageIcon = new File(System.getProperty("user.dir") + "\\ProductIcon\\" + file.getOriginalFilename());
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					BufferedImage bufferedImage = ImageIO.read(funInputStreamToBytearrayInputStrean(file.getInputStream()));
					String path = fileImageIcon.getPath().toString();
					ImageIO.write(bufferedImage, "jpg", new File(path));
					BufferedImage bfImg = scaleImage(200, 240, path);
					ImageIO.write(bfImg, "jpg", byteArrayOutputStream);
					byte[] imageBytes = byteArrayOutputStream.toByteArray();
					ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);

					Blob blobProdImage = Hibernate.createBlob(byteArrayInputStream);
					objModel.setStrProductImage(blobProdImage);

					if (fileImageIcon.exists()) {
						fileImageIcon.delete();
					}

				} else {
					clsProductMasterModel imageModel = objProductMasterService.funGetObject(objModel.getStrProdCode(), clientCode);
					if (imageModel.getStrProductImage() != null) {
						objModel.setStrProductImage(imageModel.getStrProductImage());
						System.out.println(imageModel.getStrProductImage());
					} else {
						objModel.setStrProductImage(funBlankBlob());
					}
				}

			}

		}

		objModel.setStrProdName(objBean.getStrProdName().toUpperCase());
		objModel.setStrPartNo(objBean.getStrPartNo());
		objModel.setStrUOM(objGlobal.funIfNull(objBean.getStrUOM(), "", objBean.getStrUOM()));
		objModel.setStrSGCode(objGlobal.funIfNull(objBean.getStrSGCode(), "", objBean.getStrSGCode()));
		objModel.setStrProdType(objGlobal.funIfNull(objBean.getStrProdType(), "", objBean.getStrProdType()));
		objModel.setDblCostRM(objBean.getDblCostRM());
		objModel.setDblCostManu(objBean.getDblCostManu());
		objModel.setStrLocCode(objBean.getStrLocCode());
		objModel.setDblOrduptoLvl(objBean.getDblOrduptoLvl());
		objModel.setDblReorderLvl(objBean.getDblReorderLvl());
		objModel.setStrNotInUse(objGlobal.funIfNull(objBean.getStrNotInUse(), "N", "Y"));
		objModel.setStrExpDate(objGlobal.funIfNull(objBean.getStrExpDate(), "N", "Y"));
		objModel.setStrLotNo(objGlobal.funIfNull(objBean.getStrRevLevel(), "N", "Y"));
		objModel.setStrRevLevel(objGlobal.funIfNull(objBean.getStrRevLevel(), "N", "Y"));
		objModel.setStrSlNo(objGlobal.funIfNull(objBean.getStrSlNo(), "N", "Y"));
		objModel.setStrForSale(objGlobal.funIfNull(objBean.getStrForSale(), "N", "Y"));
		objModel.setStrSaleNo(objGlobal.funIfNull(objBean.getStrSaleNo(), "", objBean.getStrSaleNo()));
		objModel.setStrDesc(objGlobal.funIfNull(objBean.getStrDesc(), "", objBean.getStrDesc()));
		objModel.setDblUnitPrice(objBean.getDblUnitPrice());
		objModel.setStrTaxIndicator(objGlobal.funIfNull(objBean.getStrTaxIndicator(), "", objBean.getStrTaxIndicator()));
		objModel.setStrExceedPO(objGlobal.funIfNull(objBean.getStrExceedPO(), "N", "Y"));
		objModel.setStrStagDel(objGlobal.funIfNull(objBean.getStrStagDel(), "N", "Y"));
		objModel.setIntDelPeriod(objBean.getIntDelPeriod());
		objModel.setStrType(objGlobal.funIfNull(objBean.getStrType(), "", objBean.getStrType()));
		objModel.setStrSpecification(objGlobal.funIfNull(objBean.getStrSpecification(), "", objBean.getStrSpecification()));
		objModel.setDblWeight(objBean.getDblWeight());
		objModel.setStrBomCal(objGlobal.funIfNull(objBean.getStrBomCal(), "", objBean.getStrBomCal()));
		objModel.setStrWtUOM(objGlobal.funIfNull(objBean.getStrWtUOM(), "", objBean.getStrWtUOM()));
		objModel.setStrCalAmtOn(objGlobal.funIfNull(objBean.getStrCalAmtOn(), "", objBean.getStrCalAmtOn()));
		objModel.setStrClass(objGlobal.funIfNull(objBean.getStrClass(), "", objBean.getStrClass()));
		objModel.setStrUserModified(userCode);
		objModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setDblBatchQty(objBean.getDblBatchQty());
		objModel.setDblMaxLvl(objBean.getDblMaxLvl());
		objModel.setStrBinNo(objGlobal.funIfNull(objBean.getStrBinNo(), "", objBean.getStrBinNo()));
		objModel.setStrTariffNo(objGlobal.funIfNull(objBean.getStrTariffNo(), "", objBean.getStrTariffNo()));
		objModel.setDblListPrice(objBean.getDblListPrice());
		objModel.setStrRemark(objGlobal.funIfNull(objBean.getStrRemark(), "", objBean.getStrRemark()));
		objModel.setStrIssueUOM(objGlobal.funIfNull(objBean.getStrIssueUOM(), "", objBean.getStrIssueUOM()));
		objModel.setStrReceivedUOM(objGlobal.funIfNull(objBean.getStrReceivedUOM(), "", objBean.getStrReceivedUOM()));
		objModel.setStrRecipeUOM(objGlobal.funIfNull(objBean.getStrRecipeUOM(), "", objBean.getStrRecipeUOM()));
		objModel.setDblIssueConversion(objBean.getDblIssueConversion());
		objModel.setDblReceiveConversion(objBean.getDblReceiveConversion());
		objModel.setDblRecipeConversion(objBean.getDblRecipeConversion());
		objModel.setStrSpecification(objBean.getStrSpecification());
		objModel.setStrNonStockableItem(objGlobal.funIfNull(objBean.getStrNonStockableItem(), "N", "Y"));
		objModel.setStrPickMRPForTaxCal(objGlobal.funIfNull(objBean.getStrPickMRPForTaxCal(), "N", "Y"));
		objModel.setStrManufacturerCode(objBean.getStrManufacturerCode());

		if (objBean.getDblYieldPer() == 0.0) {
			double yieldper = 100.00;
			objBean.setDblYieldPer(yieldper);

		}
		objModel.setDblYieldPer(objBean.getDblYieldPer());
		objModel.setStrBarCode(objBean.getStrBarCode());
		objModel.setDblMRP((objBean.getDblMRP()));
		objModel.setStrExciseable(objBean.getStrExciseable());
		objModel.setStrComesaItem(objBean.getStrComesaItem());

		if (objBean.getStrProdNameMarathi() == null) {
			objModel.setStrProdNameMarathi("");
		} else {
			objModel.setStrProdNameMarathi(objBean.getStrProdNameMarathi());
		}

		return objModel;
	}

	private clsProdProcessModel funPrepareModelProcess(clsProdProcessModel objPPModel) {
		objGlobal = new clsGlobalFunctions();
		objPPModel.setStrProcessCode(objPPModel.getStrProcessCode());
		double weight = Double.parseDouble(objGlobal.funIfNull(String.valueOf(objPPModel.getDblWeight()), "0.00", String.valueOf(objPPModel.getDblWeight())));
		objPPModel.setDblWeight(weight);
		double cycleTime = Double.parseDouble(objGlobal.funIfNull(String.valueOf(objPPModel.getDblCycleTime()), "0.00", String.valueOf(objPPModel.getDblCycleTime())));
		objPPModel.setDblCycleTime(cycleTime);
		return objPPModel;
	}

	@RequestMapping(value = "/frmProductList1", method = RequestMethod.GET)
	public ModelAndView funProductListfrom() throws JRException {
		return new ModelAndView("redirect:/rptProductList1.html");

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<clsProductMasterModel> funPrepareProductList(List listProduct) {
		List<clsProductMasterModel> productList = new ArrayList<clsProductMasterModel>();
		for (int i = 0; i < listProduct.size(); i++) {
			Object[] ob = (Object[]) listProduct.get(i);
			clsProductMasterModel objProList = new clsProductMasterModel();
			System.out.println(ob[0].toString());
			objProList.setStrProdCode(ob[0].toString());
			objProList.setStrProdName(ob[1].toString());
			objProList.setStrSGName(ob[2].toString());
			objProList.setStrUOM(ob[3].toString());
			objProList.setDblCostRM(Double.parseDouble(ob[4].toString()));
			objProList.setDblCostManu(Double.parseDouble(ob[5].toString()));
			objProList.setDblListPrice(Double.parseDouble(ob[6].toString()));
			objProList.setStrLocCode(ob[7].toString());
			objProList.setStrSpecification(ob[8].toString());
			objProList.setStrBinNo(ob[9].toString());

			productList.add(objProList);
		}
		return listProduct;
	}

	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping(value = "/rptProductList1", method = RequestMethod.GET)
	public ModelAndView funShowReport(@ModelAttribute("command") clsReportBean objBean, ModelAndView modelAndView, Map<String, Object> model, HttpServletRequest req) throws JRException {
		String companyName = "DSS";
		String strProdType = "ALL";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		objGlobal = new clsGlobalFunctions();
		String type = objBean.getStrDocType();
		List ProdLoc = objProductMasterService.funGetdtlList(clientCode);
		objGlobal = new clsGlobalFunctions();
		List<clsProductMasterModel> productMasterList = funPrepareProductList(objProductMasterService.funGetdtlList(clientCode));
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(productMasterList);
		model.put("datasource", JRdataSource);
		model.put("strCompanyName", companyName);
		model.put("strUserCode", userCode);
		model.put("strProdType", strProdType);
		modelAndView = new ModelAndView("productList1PDFReport", model);
		return modelAndView;
	}

	@RequestMapping(value = "/frmSupplierWiseProductList", method = RequestMethod.GET)
	public ModelAndView funOpenSuppWiseList() throws JRException {
		return new ModelAndView("redirect:/rptSupplierWiseProductList.html");
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/rptSupplierWiseProductList", method = RequestMethod.GET)
	public ModelAndView funSuppWiseProdShowReport(@ModelAttribute("command") clsReportBean objBean, ModelAndView modelAndView, Map<String, Object> model, HttpServletRequest req) throws JRException {
		String companyName = "DSS";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String prodCode = objBean.getStrDocCode();
		List listSuppWiseProd = objProductMasterService.funGetSuppdtlList(prodCode, clientCode);
		objGlobal = new clsGlobalFunctions();
		List<clsSupplierWiseProductModel> suppWaiseProdList = funPrepareSuppWiseProd(objProductMasterService.funGetSuppdtlList(prodCode, clientCode));
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(suppWaiseProdList);
		model.put("datasource", JRdataSource);
		model.put("strCompanyName", companyName);
		model.put("strUserCode", userCode);

		return new ModelAndView("SuppWiseProdPDFReport", model);
	}

	@SuppressWarnings("rawtypes")
	private List<clsSupplierWiseProductModel> funPrepareSuppWiseProd(List listSuppProd) {
		List<clsSupplierWiseProductModel> objProdList = new ArrayList<clsSupplierWiseProductModel>();
		for (int i = 0; i < listSuppProd.size(); i++) {
			Object[] ob = (Object[]) listSuppProd.get(i);
			clsProductMasterModel prodMaster = (clsProductMasterModel) ob[0];
			clsProdSuppMasterModel prodSupp = (clsProdSuppMasterModel) ob[1];

			clsSupplierWiseProductModel objProdMaster = new clsSupplierWiseProductModel();
			objProdMaster.setStrPartNo(prodMaster.getStrPartNo());
			objProdMaster.setStrProdName(prodMaster.getStrProdName());
			objProdMaster.setStrProdType(prodMaster.getStrProdType());
			objProdMaster.setStrUOM(prodMaster.getStrUOM());
			objProdMaster.setStrSuppPartNo(prodSupp.getStrSuppPartNo());
			objProdMaster.setStrSuppPartDesc(prodSupp.getStrSuppPartDesc());
			objProdList.add(objProdMaster);
		}
		return objProdList;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadProdReorderAllLocation", method = RequestMethod.GET)
	public @ResponseBody List<clsProductReOrderLevelModel> funAssignAllLocation(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String prodCode = req.getParameter("prodCode").toString();
		List LocationMasterList = objLocationMasterService.funGetdtlList(prodCode, clientCode);
		List<clsProductReOrderLevelModel> list = new ArrayList<clsProductReOrderLevelModel>();
		for (int i = 0; i < LocationMasterList.size(); i++) {
			Object[] ob = (Object[]) LocationMasterList.get(i);
			clsProductReOrderLevelModel reOrderModel = new clsProductReOrderLevelModel();
			reOrderModel.setStrLocationCode(ob[0].toString());
			reOrderModel.setStrLocationName(ob[1].toString());
			reOrderModel.setDblReOrderLevel(Double.valueOf(ob[2].toString()));
			reOrderModel.setDblReOrderQty(Double.valueOf(ob[3].toString()));
			reOrderModel.setDblPrice(0.0);
			list.add(reOrderModel);
		}
		return list;
	}

	@RequestMapping(value = "/checkProdName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckGroupName(@RequestParam("prodName") String name, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		//boolean count = objGlobal.funCheckName(Name.trim().toLowerCase(), clientCode, "frmProductMaster");
		List countList = objGlobalFunctionsService.funCheckName(name, clientCode, "frmProductMaster");
		int count = Integer.parseInt(countList.get(0).toString());
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Product List Report
	 * 
	 * @return
	 * @throws JRException
	 * 
	 *             19-1-15 by Jai chandra
	 */
	@RequestMapping(value = "/frmProductList", method = RequestMethod.GET)
	public ModelAndView funOpenProductListForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		String clientCode = request.getSession().getAttribute("clientCode").toString();

		List<String> listType = new ArrayList<String>();
		listType.add("ALL");
		listType.add("Procured");
		listType.add("Produced");
		listType.add("Sub-Contracted");
		listType.add("Tools");
		listType.add("Service");
		listType.add("Labour");
		listType.add("Overhead");
		listType.add("Scrap");
		listType.add("Non-Inventory");
		listType.add("Trading");

		Map<String, String> mapGroup = objGrpMasterService.funGetGroups(clientCode);
		mapGroup.put("ALL", "ALL");
		Map<String, String> mapSubGroup = new HashMap<String, String>();
		mapSubGroup.put("ALL", "ALL");
		ModelAndView objModelView = null;
		if ("2".equalsIgnoreCase(urlHits)) {
			objModelView = new ModelAndView("frmProductList_1", "command", new clsReportBean());

		} else {
			objModelView = new ModelAndView("frmProductList", "command", new clsReportBean());
		}

		objModelView.addObject("listgroup", mapGroup);
		objModelView.addObject("listsubGroup", mapSubGroup);
		objModelView.addObject("typeList", listType);

		// return new ModelAndView("frmProductList","command", new
		// clsReportBean());
		return objModelView;
	}

	@RequestMapping(value = "/rptProductList", method = RequestMethod.GET)
	private void funReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		funCallReport(objBean, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallReport(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String prodType = objBean.getStrProdType();
		String type = objBean.getStrDocType();
		objGlobal = new clsGlobalFunctions();
		Connection con = objGlobal.funGetConnection(req);
		try {

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String strLocCode = req.getSession().getAttribute("locationCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);

			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptProductList.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sql = "";
			if (objSetup.getStrShowProdMaster().equalsIgnoreCase("Y")) {
				sql = " select p.strProdCode as ProdCode,p.strProdName as ProdName,s.strSGName as SGName," + " p.strUOM as UOM,p.dblCostRM as CostRM,p.dblListPrice as ListPrice," + " l.strlocname as Locname,p.strSpecification as Specification ,p.strBinNo as BinNo " + " from tblproductmaster p" + " left outer join tblsubgroupmaster s on p.strSGCode=s.strSGCode and s.strClientcode='" + clientCode
						+ "' " + " left outer join tbllocationmaster l on p.strloccode=l.strloccode and l.strClientcode='" + clientCode + "' " + " and l.strPropertyCode='" + propertyCode + "' where p.strClientcode='" + clientCode + "' and (p.strLocCode='" + strLocCode + "' or p.strLocCode='') ";
				if (!objBean.getStrProdType().equalsIgnoreCase("ALL")) {
					sql += " and p.strProdType='" + objBean.getStrProdType() + "' ";
				}

			} else {
				sql = "select p.strProdCode as ProdCode,p.strProdName as ProdName,s.strSGName as SGName," + " p.strUOM as UOM,p.dblCostRM as CostRM,p.dblListPrice as ListPrice," + " l.strlocname as Locname,p.strSpecification as Specification ,p.strBinNo as BinNo " + " from tblproductmaster p" + " left outer join tblsubgroupmaster s on p.strSGCode=s.strSGCode and s.strClientcode='" + clientCode
						+ "' " + " left outer join tbllocationmaster l on p.strloccode=l.strloccode and l.strClientcode='" + clientCode + "' " + " where p.strClientcode='" + clientCode + "' ";
				if (!objBean.getStrProdType().equalsIgnoreCase("ALL")) {
					sql += " and p.strProdType='" + objBean.getStrProdType() + "' ";
				}
			}
			if (!objBean.getStrGCode().equalsIgnoreCase("ALL") && !objBean.getStrSGCode().equalsIgnoreCase("ALL")) {
				sql += " and s.strSGCode='" + objBean.getStrSGCode() + "' and s.strGCode='" + objBean.getStrGCode() + "'";
			} else if (!objBean.getStrGCode().equalsIgnoreCase("ALL") && objBean.getStrSGCode().equalsIgnoreCase("ALL")) {
				sql += "  and s.strGCode='" + objBean.getStrGCode() + "'";
			}

			// getting multi copy of small data in table in Detail thats why we
			// add in subDataset
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sql);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsProddtl");
			subDataset.setQuery(subQuery);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);
			hm.put("strProdType", prodType);
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
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptProductList." + type.trim());
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

	private Blob funBlankBlob() {
		Blob blob = new Blob() {

			@Override
			public void truncate(long len) throws SQLException {
				// TODO Auto-generated method stub

			}

			@Override
			public int setBytes(long pos, byte[] bytes, int offset, int len) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int setBytes(long pos, byte[] bytes) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public OutputStream setBinaryStream(long pos) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long position(Blob pattern, long start) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long position(byte[] pattern, long start) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long length() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public byte[] getBytes(long pos, int length) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public InputStream getBinaryStream(long pos, long length) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public InputStream getBinaryStream() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void free() throws SQLException {
				// TODO Auto-generated method stub

			}
		};
		return blob;
	}

	public BufferedImage scaleImage(int WIDTH, int HEIGHT, String filename) {
		BufferedImage bi = null;
		try {
			ImageIcon ii = new ImageIcon(filename);// path to image
			bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics2D gra2d = (Graphics2D) bi.createGraphics();
			gra2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
			gra2d.drawImage(ii.getImage(), 0, 0, WIDTH, HEIGHT, null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return bi;
	}

	@SuppressWarnings("finally")
	private ByteArrayInputStream funInputStreamToBytearrayInputStrean(InputStream ins) {
		ByteArrayInputStream byteArrayInputStream = null;
		try {
			byte[] buff = new byte[8000];

			int bytesRead = 0;

			ByteArrayOutputStream bao = new ByteArrayOutputStream();

			while ((bytesRead = ins.read(buff)) != -1) {
				bao.write(buff, 0, bytesRead);
			}

			byte[] data = bao.toByteArray();

			byteArrayInputStream = new ByteArrayInputStream(data);
			System.out.println(byteArrayInputStream.available());

		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			return byteArrayInputStream;
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadCustomerData", method = RequestMethod.GET)
	public @ResponseBody List funProdCustomerData(@RequestParam("prodCode") String prodCode, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List<clsProdSuppMasterModel> listProdSupp = new ArrayList<clsProdSuppMasterModel>();
		String sql = " select a.strSuppCode, b.strPName,a.dblStandingOrder,a.dblMargin " + " from tblprodsuppmaster a ,tblpartymaster b  " + " where a.strProdCode='" + prodCode + "' and a.strSuppCode=b.strPCode and b.strPType='cust' and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + clientCode + "';  ";
		List list = objGlobalFunctionsService.funGetList(sql, "sql");

		// List
		// listObjects=objProductMasterService.funGetProdSuppList(prodCode,clientCode);
		for (int i = 0; i < list.size(); i++) {
			Object[] ob = (Object[]) list.get(i);
			clsProdSuppMasterModel objProdSupplier = new clsProdSuppMasterModel();
			objProdSupplier.setStrSuppCode(ob[0].toString());
			objProdSupplier.setStrSuppName(ob[1].toString());
			objProdSupplier.setDblStandingOrder(Double.parseDouble(ob[2].toString()));
			objProdSupplier.setDblMargin(Double.parseDouble(ob[3].toString()));

			listProdSupp.add(objProdSupplier);
		}
		return listProdSupp;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadSubGroupWiseProduct", method = RequestMethod.GET)
	public @ResponseBody List funloadSubGroupWiseProduct(@RequestParam("sgCode") String sgCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List<clsProductMasterModel> listProd = new ArrayList<clsProductMasterModel>();
		ListIterator<clsProductMasterModel> itProdModel = null;
		try {
			// listProd=objProductMasterService.funGetSubGroupWiseProductList(sgCode,
			// clientCode);
			itProdModel = (ListIterator<clsProductMasterModel>) objProductMasterService.funGetSubGroupWiseProductList(sgCode, clientCode).listIterator();
			while (itProdModel.hasNext()) {
				clsProductMasterModel objProd = itProdModel.next();
				clsSubGroupMasterModel objSubGroup = objSubGrpMasterService.funGetObject(objProd.getStrSGCode(), clientCode);
				if (!objProd.getStrLocCode().equals("")) {
					clsLocationMasterModel objLocCode = objLocationMasterService.funGetObject(objProd.getStrLocCode(), clientCode);
					objProd.setStrLocName(objLocCode.getStrLocName());
				} else {
					objProd.setStrLocName("");
				}
				objProd.setStrSGName(objSubGroup.getStrSGName());
				listProd.add(objProd);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listProd;
	}

}