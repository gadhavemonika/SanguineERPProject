package com.sanguine.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.sanguine.bean.clsStockTransferBean;
import com.sanguine.crm.model.clsPartyMasterModel;
import com.sanguine.crm.service.clsPartyMasterService;
import com.sanguine.model.clsAuditDtlModel;
import com.sanguine.model.clsAuditHdModel;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsLinkUpHdModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsProductReOrderLevelModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsStkTransferDtlModel;
import com.sanguine.model.clsStkTransferHdModel;
import com.sanguine.model.clsStkTransferHdModel_ID;
import com.sanguine.service.clsAttributeMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLinkUpService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsStkAdjustmentService;
import com.sanguine.service.clsStkTransferService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsStkTransferController
{
	@Autowired
	ServletContext servletContext;
	@Autowired
	private clsStkTransferService objStkTransService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsLocationMasterService objLocationMasterService;
	@Autowired
	private clsAttributeMasterService objAttributeMasterService;
	@Autowired
	private clsStkAdjustmentService objStkAdjService;
	@Autowired
	clsSetupMasterService objSetupMasterService;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsPartyMasterService objPartyMasterService;

	@Autowired
	private clsProductMasterService objProductMasterService;

	@Autowired
	private clsLinkUpService objLinkupService;

	/**
	 * Open Stock Transfer Form
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/frmStockTransfer", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request)
	{
		request.getSession().setAttribute("formName", "frmStockTransfer");
		String urlHits = "1";
		try
		{
			urlHits = request.getParameter("saddr").toString();
		}
		catch (NullPointerException e)
		{
			urlHits = "1";
		}
		/**
		 * Checking Authorization
		 */
		String docCode = "";
		boolean flagOpenFromAuthorization = true;
		try
		{
			docCode = request.getParameter("authorizationSTCode").toString();
		}
		catch (NullPointerException e)
		{
			flagOpenFromAuthorization = false;
		}
		model.put("flagOpenFromAuthorization", flagOpenFromAuthorization);
		if (flagOpenFromAuthorization)
		{
			model.put("authorizationSTCode", docCode);
		}
		model.put("urlHits", urlHits);
		clsStockTransferBean bean = new clsStockTransferBean();
		bean.setStrMaterialIssue("");
		if ("2".equalsIgnoreCase(urlHits))
		{
			return new ModelAndView("frmStockTransfer_1", "command", bean);
		}
		else if ("1".equalsIgnoreCase(urlHits))
		{
			return new ModelAndView("frmStockTransfer", "command", bean);
		}
		else
		{
			return null;
		}

	}

	/**
	 * Load Stock Transfer Data
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/frmStockTransfer1", method = RequestMethod.POST)
	public @ResponseBody clsStockTransferBean funOpenFormWithSPCode(Map<String, Object> model, HttpServletRequest request)
	{

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		double currValue = Double.parseDouble(request.getSession().getAttribute("currValue").toString());
		// String
		// userCode=request.getSession().getAttribute("usercode").toString();
		clsStockTransferBean bean = new clsStockTransferBean();
		String stCode = request.getParameter("STCode").toString();
		List listStkTransHd = objStkTransService.funGetObject(stCode, clientCode);
		if (listStkTransHd.size() > 0)
		{
			bean = funPrepareBean(listStkTransHd, request, currValue);
			List listTempDtl = objStkTransService.funGetDtlList(stCode, clientCode);
			List<clsStkTransferDtlModel> listStkTransDtl = funPrepareStkTransferDtlModel(listTempDtl, currValue);
			bean.setListStkTransDtl(listStkTransDtl);
			return bean;
		}
		else
		{
			bean.setStrSTCode("Invalid Code");
			return bean;

		}

	}

	/**
	 * Save Stock Transfer Data
	 * 
	 * @param objBean
	 * @param result
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveStkTransfer", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") clsStockTransferBean objBean, BindingResult result, HttpServletRequest request)
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
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		String startDate = request.getSession().getAttribute("startDate").toString();
		double currValue = Double.parseDouble(request.getSession().getAttribute("currValue").toString());
		clsStkTransferHdModel objHdModel = funPrepareModel(objBean, userCode, clientCode, propCode, startDate, request);
		objGlobal = new clsGlobalFunctions();
		if (!result.hasErrors())
		{
			List<clsStkTransferDtlModel> listStkTransDtl = objBean.getListStkTransDtl();
			if (null != listStkTransDtl && listStkTransDtl.size() > 0)
			{
				objStkTransService.funAddUpdate(objHdModel);
				String stCode = objHdModel.getStrSTCode();
				objStkTransService.funDeleteDtl(stCode, clientCode);

				for (clsStkTransferDtlModel ob : listStkTransDtl)
				{
					if (null != ob.getStrProdCode())
					{
						ob.setDblPrice(ob.getDblPrice() * currValue);
						ob.setDblTotalPrice(ob.getDblTotalPrice() * currValue);
						ob.setStrSTCode(stCode);
						ob.setStrProdChar(" ");
						ob.setStrClientCode(clientCode);
						objStkTransService.funAddUpdateDtl(ob);
					}
				}
				request.getSession().setAttribute("success", true);
				request.getSession().setAttribute("successMessage", "Stock Transfer Code : ".concat(objHdModel.getStrSTCode()));
				request.getSession().setAttribute("rptStockTranferCode", objHdModel.getStrSTCode());

				/*
				 * clsCompanyMasterModel objCompModel =
				 * objSetupMasterService.funGetObject(clientCode); if(
				 * objCompModel.getStrWebBookModule().equals("Yes") ) {
				 * funGenrateJVforSalesReturnPropertyWiseSupplier( objHdModel,listStkTransDtl
				 * ,"",clientCode,userCode,propCode,request);
				 * funGenrateJVforSalesReturnPropertyWiseCustomer( objHdModel,listStkTransDtl
				 * ,"",clientCode,userCode,propCode,request); }
				 */

			}
			return new ModelAndView("redirect:/frmStockTransfer.html?saddr=" + urlHits);
		}
		else
		{
			return new ModelAndView("frmStockTransfer?saddr=" + urlHits);
		}
	}

	/**
	 * Prepare Stock Transfer Dtl Data
	 * 
	 * @param listTempDtl
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List<clsStkTransferDtlModel> funPrepareStkTransferDtlModel(List listTempDtl, double currValue)
	{
		List<clsStkTransferDtlModel> listStkTransDtl = new ArrayList<clsStkTransferDtlModel>();

		for (int i = 0; i < listTempDtl.size(); i++)
		{
			Object[] ob = (Object[]) listTempDtl.get(i);
			clsStkTransferDtlModel stkTransDtl = (clsStkTransferDtlModel) ob[0];
			clsProductMasterModel prodMaster = (clsProductMasterModel) ob[1];
			clsStkTransferDtlModel objStkTransDtl = new clsStkTransferDtlModel();
			double totalWt = stkTransDtl.getDblWeight() * objStkTransDtl.getDblQty();
			objStkTransDtl.setStrSTCode(stkTransDtl.getStrSTCode());
			objStkTransDtl.setStrProdCode(stkTransDtl.getStrProdCode());
			objStkTransDtl.setStrProdChar(stkTransDtl.getStrProdChar());
			objStkTransDtl.setDblQty(stkTransDtl.getDblQty());
			objStkTransDtl.setStrClientCode(stkTransDtl.getStrClientCode());
			objStkTransDtl.setDblTotalWt(totalWt);
			objStkTransDtl.setStrRemark(stkTransDtl.getStrRemark());
			objStkTransDtl.setIntProdIndex(0);
			objStkTransDtl.setStrProdName(prodMaster.getStrProdName());
			objStkTransDtl.setDblPrice(stkTransDtl.getDblPrice() * currValue);
			objStkTransDtl.setDblWeight(stkTransDtl.getDblWeight());
			objStkTransDtl.setStrUOM(prodMaster.getStrUOM());
			objStkTransDtl.setStrProdType(prodMaster.getStrProdType());
			objStkTransDtl.setStrPOSItemCode(prodMaster.getStrPartNo());
			objStkTransDtl.setDblTotalPrice(stkTransDtl.getDblTotalPrice() * currValue);
			listStkTransDtl.add(objStkTransDtl);
		}

		return listStkTransDtl;
	}

	/**
	 * Prepare Stock Transfer HdData
	 * 
	 * @param objBean
	 * @param userCode
	 * @param clientCode
	 * @param propCode
	 * @param startDate
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private clsStkTransferHdModel funPrepareModel(clsStockTransferBean objBean, String userCode, String clientCode, String propCode, String startDate, HttpServletRequest request)
	{
		objGlobal = new clsGlobalFunctions();
		double currValue = Double.parseDouble(request.getSession().getAttribute("currValue").toString());
		long lastNo = 0;
		clsStkTransferHdModel objHdModel;

		if (objBean.getStrSTCode().length() == 0)
		{
			String strStkTransCode = objGlobalFunctions.funGenerateDocumentCode("frmStockTransfer", objBean.getDtSTDate(), request);
			objHdModel = new clsStkTransferHdModel(new clsStkTransferHdModel_ID(strStkTransCode, clientCode));
			objHdModel.setIntId(lastNo);
			objHdModel.setStrUserCreated(userCode);
			objHdModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		}
		else
		{
			if (objGlobalFunctions.funCheckAuditFrom("frmStockTransfer", request))
			{
				funSaveAudit(objBean.getStrSTCode(), "Edit", request);
			}
			objHdModel = new clsStkTransferHdModel(new clsStkTransferHdModel_ID(objBean.getStrSTCode(), clientCode));
		}

		boolean res = false;
		if (null != request.getSession().getAttribute("hmAuthorization"))
		{
			HashMap<String, Boolean> hmAuthorization = (HashMap) request.getSession().getAttribute("hmAuthorization");
			if (hmAuthorization.get("Stock Transfer"))
			{
				res = true;
			}
		}
		if (res)
		{
			objHdModel.setStrAuthorise("No");
		}
		else
		{
			objHdModel.setStrAuthorise("Yes");
		}
		objHdModel.setStrNo(objBean.getStrNo());
		objHdModel.setDtSTDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtSTDate()));
		objHdModel.setStrFromLocCode(objBean.getStrFromLocCode());
		objHdModel.setStrToLocCode(objBean.getStrToLocCode());
		objHdModel.setStrAgainst(objBean.getStrAgainst());
		objHdModel.setStrNarration(objGlobal.funIfNull(objBean.getStrNarration(), " ", objBean.getStrNarration()));
		objHdModel.setStrMaterialIssue("");
		objHdModel.setStrReqCode(objBean.getStrReqCode());
		objHdModel.setStrUserModified(userCode);
		objHdModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objHdModel.setStrWOCode(" ");
		objHdModel.setDblTotalAmt(String.valueOf((Double.parseDouble(objBean.getDblTotalAmt()) * currValue)));
		return objHdModel;
	}

	/**
	 * Prepare Bean
	 * 
	 * @param listStkTransHd
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	private clsStockTransferBean funPrepareBean(List listStkTransHd, HttpServletRequest request, double currValue)
	{
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		objGlobal = new clsGlobalFunctions();
		clsStockTransferBean objBean = new clsStockTransferBean();
		if (listStkTransHd.size() > 0)
		{
			Object[] ob = (Object[]) listStkTransHd.get(0);
			clsStkTransferHdModel stkTransHd = (clsStkTransferHdModel) ob[0];
			clsLocationMasterModel objLocFrom = objLocationMasterService.funGetObject(stkTransHd.getStrFromLocCode(), clientCode);
			clsLocationMasterModel objLocTo = objLocationMasterService.funGetObject(stkTransHd.getStrToLocCode(), clientCode);
			objBean.setStrSTCode(stkTransHd.getStrSTCode());
			objBean.setDtSTDate(objGlobal.funGetDate("yyyy/MM/dd", stkTransHd.getDtSTDate()));
			objBean.setStrFromLocCode(stkTransHd.getStrFromLocCode());
			objBean.setStrFromLocName(objLocFrom.getStrLocName());
			objBean.setStrToLocCode(stkTransHd.getStrToLocCode());
			objBean.setStrToLocName(objLocTo.getStrLocName());
			objBean.setStrNarration(stkTransHd.getStrNarration());
			objBean.setStrMaterialIssue("");
			objBean.setStrNo(stkTransHd.getStrNo());
			objBean.setDblTotalAmt(String.valueOf(Double.parseDouble(stkTransHd.getDblTotalAmt()) * currValue));
			return objBean;
		}
		else
		{
			objBean.setStrSTCode("Invalid Code");
			return objBean;
		}

	}

	/**
	 * Audit Function Start
	 * 
	 * @param stCode
	 * @param req
	 */
	@SuppressWarnings("rawtypes")
	public void funSaveAudit(String stCode, String strTransMode, HttpServletRequest req)
	{
		try
		{
			objGlobal = objGlobalFunctions;
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			List listStkTransHd = objStkTransService.funGetObject(stCode, clientCode);
			if (!listStkTransHd.isEmpty())
			{
				Object[] ob = (Object[]) listStkTransHd.get(0);
				clsStkTransferHdModel stkTransHd = (clsStkTransferHdModel) ob[0];
				List listTempDtl = objStkTransService.funGetDtlList(stCode, clientCode);
				if (null != stkTransHd)
				{
					if (null != listTempDtl && listTempDtl.size() > 0)
					{
						String sql = "select count(*)+1 from tblaudithd where left(strTransCode,12)='" + stkTransHd.getStrSTCode() + "'";
						List list = objGlobalFunctionsService.funGetList(sql, "sql");

						if (!list.isEmpty())
						{
							String count = list.get(0).toString();
							clsAuditHdModel model = funPrepairAuditHdModel(stkTransHd);
							if (strTransMode.equalsIgnoreCase("Deleted"))
							{
								model.setStrTransCode(stkTransHd.getStrSTCode());
							}
							else
							{
								model.setStrTransCode(stkTransHd.getStrSTCode() + "-" + count);
							}
							model.setStrClientCode(clientCode);
							model.setStrTransMode(strTransMode);
							model.setStrUserAmed(userCode);
							model.setDtLastModified(objGlobal.funGetCurrentDate("yyyy-MM-dd"));
							model.setStrUserModified(userCode);
							objGlobalFunctionsService.funSaveAuditHd(model);
							for (int i = 0; i < listTempDtl.size(); i++)
							{
								Object[] ob1 = (Object[]) listTempDtl.get(i);
								clsStkTransferDtlModel stkTransDtl = (clsStkTransferDtlModel) ob1[0];
								clsAuditDtlModel AuditMode = new clsAuditDtlModel();
								AuditMode.setStrTransCode(model.getStrTransCode());
								AuditMode.setStrProdCode(stkTransDtl.getStrProdCode());
								AuditMode.setDblQty(stkTransDtl.getDblQty());
								AuditMode.setDblUnitPrice(stkTransDtl.getDblPrice());
								AuditMode.setStrRemarks(stkTransDtl.getStrRemark());
								AuditMode.setStrUOM("");
								AuditMode.setStrAgainst("");
								AuditMode.setStrCode("");
								AuditMode.setStrTaxType("");
								AuditMode.setStrPICode("");
								AuditMode.setStrClientCode(stkTransDtl.getStrClientCode());
								objGlobalFunctionsService.funSaveAuditDtl(AuditMode);
							}
						}
					}
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();

		}
	}

	/**
	 * Prepare Audit HdModel Data
	 * 
	 * @param stkHdModel
	 * @return
	 */
	private clsAuditHdModel funPrepairAuditHdModel(clsStkTransferHdModel stkHdModel)
	{
		clsAuditHdModel AuditHdModel = new clsAuditHdModel();
		if (stkHdModel != null)
		{
			AuditHdModel.setStrTransCode(stkHdModel.getStrSTCode());
			AuditHdModel.setDtTransDate(stkHdModel.getDtSTDate());
			AuditHdModel.setStrTransType("Stock Transfer");
			// AuditHdModel.setStrTransMode("Edit");
			AuditHdModel.setStrLocBy(stkHdModel.getStrFromLocCode());
			AuditHdModel.setStrLocOn(stkHdModel.getStrToLocCode());
			AuditHdModel.setStrAgainst(stkHdModel.getStrAgainst());
			AuditHdModel.setStrAuthorise(stkHdModel.getStrAuthorise());
			AuditHdModel.setStrNarration(stkHdModel.getStrNarration());
			AuditHdModel.setStrNo(stkHdModel.getStrNo());
			AuditHdModel.setStrMaterialIssue(stkHdModel.getStrMaterialIssue());
			AuditHdModel.setStrUserCreated(stkHdModel.getStrUserCreated());
			AuditHdModel.setDtDateCreated(stkHdModel.getDtCreatedDate());
			AuditHdModel.setStrCode("");
			AuditHdModel.setStrMInBy("");
			AuditHdModel.setStrTimeInOut("");
			AuditHdModel.setStrVehNo("");
			AuditHdModel.setStrGRNCode("");
			AuditHdModel.setStrLocCode("");
			AuditHdModel.setStrSuppCode("");
			AuditHdModel.setStrClosePO("");
			AuditHdModel.setStrExcise("");
			AuditHdModel.setStrCloseReq("");
			AuditHdModel.setStrWoCode("");
			AuditHdModel.setStrBillNo("");
			AuditHdModel.setDblWOQty(0);
			AuditHdModel.setStrRefNo("");
			AuditHdModel.setStrShipmentMode("");
			AuditHdModel.setStrPayMode("");
		}
		return AuditHdModel;
	}

	/**
	 * 
	 * Report Function Start
	 * 
	 * @return
	 * @throws JRException
	 */

	@RequestMapping(value = "/frmStockTransferSlip", method = RequestMethod.GET)
	public ModelAndView funOpenStkTransferSlipForm(Map<String, Object> model, HttpServletRequest request)
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

		if ("2".equalsIgnoreCase(urlHits))
		{
			return new ModelAndView("frmStockTransferSlip_1", "command", new clsReportBean());
		}
		else
		{
			return new ModelAndView("frmStockTransferSlip", "command", new clsReportBean());
		}

	}

	/**
	 * Range Printing
	 * 
	 * @param objBean
	 * @param resp
	 * @param req
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rpttransferslip", method = RequestMethod.POST)
	private void funReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req)
	{
		objGlobal = new clsGlobalFunctions();
		String fromDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtFromDate());
		String toDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtToDate());

		String strFromLoc = objBean.getStrFromLocCode();
		String strToLoc = objBean.getStrToLocCode();

		String strFromSTCode = objBean.getStrFromDocCode();
		String strToSTCode = objBean.getStrToDocCode();

		String type = objBean.getStrDocType();

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try
		{
			String sql = "select strSTCode from tblstocktransferhd a where date(a.dtSTDate) between '" + fromDate + "' and '" + toDate + "' and a.strClientCode='" + clientCode + "'";
			if (strFromLoc.trim().length() > 0)
			{
				sql = sql + " and a.strFromLocCode='" + strFromLoc + "' ";
			}
			if (strToLoc.trim().length() > 0)
			{
				sql = sql + " and a.strToLocCode='" + strToLoc + "' ";
			}

			if (strFromSTCode.trim().length() > 0 && strToSTCode.trim().length() > 0)
			{
				sql = sql + " and a.strSTCode between '" + strFromSTCode + "' and '" + strToSTCode + "' ";
			}
			List list = objGlobalFunctionsService.funGetList(sql, "sql");
			if (list != null && !list.isEmpty())
			{
				List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				for (int i = 0; i < list.size(); i++)
				{
					JasperPrint p = funCallRangePrintReport(list.get(i).toString(), resp, req);
					jprintlist.add(p);
				}

				if (type.trim().equalsIgnoreCase("pdf"))
				{

					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					resp.setHeader("Content-Disposition", "inline;filename=" + "rptStkTransferslip." + type.trim());
					exporter.exportReport();

					servletOutputStream.flush();
					servletOutputStream.close();
				}
				// else if (type.trim().equalsIgnoreCase("xls")) {
				// JRExporter exporterXLS = new JRXlsExporter();
				// exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST,
				// jprintlist);
				// exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
				// resp.getOutputStream());
				// resp.setHeader("Content-Disposition", "attachment;filename=" +
				// "rptStkTransferslip." + type.trim());
				// exporterXLS.exportReport();
				// resp.setContentType("application/xlsx");
				//
				// }
			}
			else
			{
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.getWriter().append("No Record Found");
			}
		}
		catch (JRException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "finally", "rawtypes", "unchecked" })
	public JasperPrint funCallRangePrintReport(String stCode, HttpServletResponse resp, HttpServletRequest req)
	{
		objGlobal = new clsGlobalFunctions();
		Connection con = objGlobal.funGetConnection(req);
		JasperPrint p = null;
		try
		{

			objGlobal = new clsGlobalFunctions();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null)
			{
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptStockTransferSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sql = "select a.strSTCode,DATE_FORMAT(a.dtSTDate,'%m-%d-%Y') as dtSTDate,a.strFromLocCode,a.strToLocCode,a.strNarration,a.strMaterialIssue,a.strWOCode" + " ,a.strAgainst,b.strLocName as strFromLocName,c.strLocName as strToLocName from tblstocktransferhd a,tbllocationmaster b,tbllocationmaster c" + " where a.strFromLocCode=b.strLocCode and a.strToLocCode=c.strLocCode and a.strSTCode='" + stCode + "' " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + " and c.strClientCode='" + clientCode + "' ";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sql);
			jd.setQuery(newQuery);
			String sql2 = "select a.strSTCode,a.strProdCode,b.strProdName,a.dblQty,a.dblWeight,a.dblPrice,a.strRemark " + " from tblstocktransferdtl a,tblproductmaster b " + " where a.strProdCode=b.strProdCode and a.strSTCode='" + stCode + "' and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' ";

			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sql2);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsStkDtl");
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
			hm.put("strFax", objSetup.getStrFax());
			hm.put("strPhoneNo", objSetup.getStrPhone());
			hm.put("strEmailAddress", objSetup.getStrEmail());
			hm.put("strWebSite", objSetup.getStrWebsite());
			hm.put("strUserCreated", objSetup.getStrUserCreated());
			p = JasperFillManager.fillReport(jr, hm, con);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				con.close();
			}
			catch (SQLException e)
			{

				e.printStackTrace();
			}
			return p;
		}
	}

	@RequestMapping(value = "/openRptStockTransferSlip", method = RequestMethod.GET)
	private void funCallReportOnSubmit(HttpServletResponse resp, HttpServletRequest req)
	{
		String stCode = req.getParameter("rptStockTranferCode").toString();
		req.getSession().removeAttribute("rptStockTranferCode");
		String type = "pdf";
		funCallReport(stCode, type, resp, req);
	}

	@RequestMapping(value = "/invokeRptStkTransSlip", method = RequestMethod.GET)
	private void funCallReportOnClick(@RequestParam(value = "docCode") String docCode, HttpServletResponse resp, HttpServletRequest req)
	{
		String type = "pdf";
		funCallReport(docCode, type, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void funCallReport(String stCode, String type, HttpServletResponse resp, HttpServletRequest req)
	{
		try
		{
			objGlobal = new clsGlobalFunctions();
			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null)
			{
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptStockTransferSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sql = "select a.strSTCode,DATE_FORMAT(a.dtSTDate,'%m-%d-%Y') as dtSTDate,a.strFromLocCode," + "a.strToLocCode,a.strNarration,a.strMaterialIssue,a.strWOCode" + " ,a.strAgainst," + "b.strLocName as strFromLocName,c.strLocName as strToLocName,a.strUserCreated," + "a.strAuthLevel2,a.strAuthLevel1 " + " from tblstocktransferhd a,tbllocationmaster b,tbllocationmaster c" + " where a.strFromLocCode=b.strLocCode and a.strToLocCode=c.strLocCode and a.strSTCode='" + stCode + "' " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + " and c.strClientCode='" + clientCode + "' ";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sql);
			jd.setQuery(newQuery);
			String sql2 = "select a.strSTCode,a.strProdCode,b.strProdName,a.dblQty,a.dblWeight,a.dblPrice,a.strRemark,b.strUOM " + " from tblstocktransferdtl a,tblproductmaster b " + " where a.strProdCode=b.strProdCode and a.strSTCode='" + stCode + "' and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' ";

			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sql2);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsStkDtl");
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
			hm.put("strFax", objSetup.getStrFax());
			hm.put("strPhoneNo", objSetup.getStrPhone());
			hm.put("strEmailAddress", objSetup.getStrEmail());
			hm.put("strWebSite", objSetup.getStrWebsite());
			hm.put("strUserCreated", objSetup.getStrUserCreated());
			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf"))
			{
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptStkTransferslip." + type.trim());
				resp.setContentLength(bytes.length);
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			}
			else if (type.trim().equalsIgnoreCase("xls"))
			{
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRPdfExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptStkTransferslip." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");

			}
			else if (type.trim().equalsIgnoreCase("HTML"))
			{
				JRExporter exporterXLS = new JRHtmlExporter();
				exporterXLS.setParameter(JRPdfExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptStkTransferslip." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/HTML");
			}
			else if (type.trim().equalsIgnoreCase("CSV"))
			{
				JRExporter exporterXLS = new JRCsvExporter();
				exporterXLS.setParameter(JRPdfExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptStkTransferslip." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/CSV");

			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/loadLocCustomerLinkedProductData", method = RequestMethod.GET)
	private @ResponseBody clsProductMasterModel funLoadLocCustomerLinkedProductData(HttpServletResponse resp, HttpServletRequest req)
	{
		String frmlocCode = req.getParameter("frmLocCode").toString();
		String prodCode = req.getParameter("prodCode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String strCurr = req.getSession().getAttribute("currValue").toString();
		double currValue = Double.parseDouble(strCurr);
		clsProductMasterModel objProd = objProductMasterService.funGetObject(prodCode, clientCode);
		List<clsPartyMasterModel> objlist = objPartyMasterService.funGetLinkLocCustomer(frmlocCode, clientCode);
		if (objlist.size() > 0)
		{
			clsPartyMasterModel objCust = objlist.get(0);

			String sql = " select a.dblLastCost from tblprodsuppmaster a " + " where a.strSuppCode='" + objCust.getStrPCode() + "' " + " and a.strProdCode='" + prodCode + "' " + " and a.strClientCode='" + clientCode + "' ";

			List list = objGlobalFunctionsService.funGetList(sql, "sql");
			if (list.size() > 0)
			{
				Object ob = list.get(0);
				objProd.setDblCostRM((Double.parseDouble(ob.toString()) / currValue));
			}
		}
		else
		{
			objProd = objProductMasterService.funGetObject(prodCode, clientCode);
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup.getStrMultiCurrency().equalsIgnoreCase("Y"))
			{
				clsProductReOrderLevelModel objReOrder = objProductMasterService.funGetProdReOrderLvl(prodCode, frmlocCode, clientCode);
				double dblreOrderPrice = 0;
				if (objReOrder != null)
				{
					dblreOrderPrice = objReOrder.getDblPrice();
				}
				objProd.setDblCostRM(dblreOrderPrice);
			}
		}
		return objProd;

	}

	private String funGenrateJVforSalesReturnPropertyWiseSupplier(clsStkTransferHdModel objModel, List<clsStkTransferDtlModel> listDtlModel, String str, String clientCode, String userCode, String propCode, HttpServletRequest req)
	{
		JSONObject jObjJVData = new JSONObject();

		JSONArray jArrJVdtl = new JSONArray();
		JSONArray jArrJVDebtordtl = new JSONArray();
		String jvCode = "";
		String locfrom = objModel.getStrFromLocCode();
		String custCode = "";
		String sqlCustCode = " select a.strPCode from tblpartymaster a " + "  where a.strLocCode='" + locfrom + "' and a.strPType='cust' and a.strClientCode='" + clientCode + "'  ";
		List listParty = objGlobalFunctionsService.funGetList(sqlCustCode, "sql");
		for (int i = 0; i < listParty.size(); i++)
		{
			custCode = listParty.get(0).toString();
		}
		clsLocationMasterModel objloc = objLocationMasterService.funGetObject(objModel.getStrToLocCode(), clientCode);
		propCode = objloc.getStrPropertyCode();
		String debitAmt = objModel.getDblTotalAmt();
		clsLinkUpHdModel objLinkCust = objLinkupService.funGetARLinkUp(custCode, clientCode, propCode, "Customer", "Sale");
		if (objLinkCust != null)
		{
			if (objModel.getStrNo().equals(""))
			{
				jObjJVData.put("strVouchNo", "");
				jObjJVData.put("strNarration", "JV Genrated by Sales-Ret:" + objModel.getStrSTCode());
				jObjJVData.put("strSancCode", "");
				jObjJVData.put("strType", "");
				jObjJVData.put("dteVouchDate", objModel.getDtSTDate());
				jObjJVData.put("intVouchMonth", 1);
				jObjJVData.put("dblAmt", debitAmt);
				jObjJVData.put("strTransType", "R");
				jObjJVData.put("strTransMode", "A");
				jObjJVData.put("strModuleType", "AP");
				jObjJVData.put("strMasterPOS", "WEBSTOCK");
				jObjJVData.put("strUserCreated", userCode);
				jObjJVData.put("strUserEdited", userCode);
				jObjJVData.put("dteDateCreated", objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjJVData.put("dteDateEdited", objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjJVData.put("strClientCode", clientCode);
				jObjJVData.put("strPropertyCode", propCode);

			}
			else
			{
				jObjJVData.put("strVouchNo", objModel.getStrNo());
				jObjJVData.put("strNarration", "JV Genrated by Sales-Ret:" + objModel.getStrSTCode());
				jObjJVData.put("strSancCode", "");
				jObjJVData.put("strType", "");
				jObjJVData.put("dteVouchDate", objModel.getDtSTDate());
				jObjJVData.put("intVouchMonth", 1);
				jObjJVData.put("dblAmt", debitAmt);
				jObjJVData.put("strTransType", "R");
				jObjJVData.put("strTransMode", "A");
				jObjJVData.put("strModuleType", "AP");
				jObjJVData.put("strMasterPOS", "WEBSTOCK");
				jObjJVData.put("strUserCreated", userCode);
				jObjJVData.put("strUserEdited", userCode);
				jObjJVData.put("dteDateCreated", objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjJVData.put("dteDateEdited", objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjJVData.put("strClientCode", clientCode);
				jObjJVData.put("strPropertyCode", propCode);

			}
			// jvhd entry end

			// jvdtl entry Start
			for (clsStkTransferDtlModel objDtl : listDtlModel)
			{

				JSONObject jObjDtl = new JSONObject();

				clsProductMasterModel objProdModle = objProductMasterService.funGetObject(objDtl.getStrProdCode(), clientCode);
				clsLinkUpHdModel objLinkSubGroup = objLinkupService.funGetARLinkUp(objProdModle.getStrSGCode(), clientCode, propCode, "SubGroup", "Purchase");
				if (objProdModle != null && objLinkSubGroup != null)
				{
					jObjDtl.put("strVouchNo", "");
					jObjDtl.put("strAccountCode", objLinkSubGroup.getStrAccountCode());
					jObjDtl.put("strAccountName", objLinkSubGroup.getStrMasterDesc());
					jObjDtl.put("strCrDr", "Dr");
					jObjDtl.put("dblDrAmt", objDtl.getDblTotalPrice());
					jObjDtl.put("dblCrAmt", 0.00);
					jObjDtl.put("strNarration", "WS Product code :" + objDtl.getStrProdCode());
					jObjDtl.put("strOneLine", "R");
					jObjDtl.put("strClientCode", clientCode);
					jObjDtl.put("strPropertyCode", propCode);
					jArrJVdtl.add(jObjDtl);
				}
			}

			/*
			 * if(listTaxDtl!=null) { for(clsGRNTaxDtlModel objTaxDtl : listTaxDtl) {
			 * JSONObject jObjTaxDtl =new JSONObject(); clsLinkUpHdModel objLinkTax =
			 * objLinkupService.funGetARLinkUp(objTaxDtl
			 * .getStrTaxCode(),clientCode,propCode); if(objLinkTax!=null ) {
			 * jObjTaxDtl.put("strVouchNo", ""); jObjTaxDtl.put("strAccountCode",
			 * objLinkTax.getStrAccountCode()); jObjTaxDtl.put("strAccountName",
			 * objLinkTax.getStrGDes()); jObjTaxDtl.put("strCrDr", "Dr");
			 * jObjTaxDtl.put("dblDrAmt", objTaxDtl.getStrTaxAmt());
			 * jObjTaxDtl.put("dblCrAmt", 0.00); jObjTaxDtl.put("strNarration",
			 * "WS Tax Desc :"+objTaxDtl.getStrTaxDesc()); jObjTaxDtl.put("strOneLine",
			 * "R"); jObjTaxDtl.put("strClientCode", clientCode);
			 * jObjTaxDtl.put("strPropertyCode", propCode); jArrJVdtl.add(jObjTaxDtl); } } }
			 */
			JSONObject jObjCustDtl = new JSONObject();
			jObjCustDtl.put("strVouchNo", "");
			jObjCustDtl.put("strAccountCode", "");
			jObjCustDtl.put("strAccountName", "");
			jObjCustDtl.put("strCrDr", "Cr");
			jObjCustDtl.put("dblDrAmt", 0.00);
			jObjCustDtl.put("dblCrAmt", objModel.getDblTotalAmt());
			jObjCustDtl.put("strNarration", "Invoice Customer");
			jObjCustDtl.put("strOneLine", "R");
			jObjCustDtl.put("strClientCode", clientCode);
			jObjCustDtl.put("strPropertyCode", propCode);
			jArrJVdtl.add(jObjCustDtl);

			jObjJVData.put("ArrJVDtl", jArrJVdtl);

			// jvdtl entry end

			// jvDebtor detail entry start
			String sql = "  select a.strSTCode,a.dblTotalAmt,b.strDebtorCode,b.strPName,date(a.dtSTDate), " + "  a.strNarration ,date(a.dtSTDate),a.strNo " + " from dbwebmms.tblstocktransferhd a,dbwebmms.tblpartymaster b  " + "  where a.strFromLocCode =b.strLocCode  " + "   and a.strSTCode='" + objModel.getStrSTCode() + "' and a.strClientCode='" + clientCode + "'   ";
			List listTax = objGlobalFunctionsService.funGetList(sql, "sql");
			for (int i = 0; i < listTax.size(); i++)
			{
				JSONObject jObjDtl = new JSONObject();
				Object[] ob = (Object[]) listTax.get(i);
				jObjDtl.put("strVouchNo", "");
				jObjDtl.put("strDebtorCode", ob[2].toString());
				jObjDtl.put("strDebtorName", ob[3].toString());
				jObjDtl.put("strCrDr", "Cr");
				jObjDtl.put("dblAmt", ob[1].toString());
				jObjDtl.put("strBillNo", ob[7].toString());
				jObjDtl.put("strInvoiceNo", ob[0].toString());
				jObjDtl.put("strGuest", "");
				jObjDtl.put("strAccountCode", objLinkCust.getStrAccountCode());
				jObjDtl.put("strCreditNo", "");
				jObjDtl.put("dteBillDate", ob[4].toString());
				jObjDtl.put("dteInvoiceDate", ob[4].toString());
				jObjDtl.put("strNarration", ob[5].toString());
				jObjDtl.put("dteDueDate", ob[6].toString());
				jObjDtl.put("strClientCode", clientCode);
				jObjDtl.put("strPropertyCode", propCode);
				jObjDtl.put("strPOSCode", "");
				jObjDtl.put("strPOSName", "");
				jObjDtl.put("strRegistrationNo", "");

				jArrJVDebtordtl.add(jObjDtl);
			}

			jObjJVData.put("ArrJVDebtordtl", jArrJVDebtordtl);
			// jvDebtor detail entry end

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/WebBooksIntegration/funGenrateJVforInvoice", jObjJVData);
			jvCode = jObj.get("strJVCode").toString();
		}
		return jvCode;
	}

	private String funGenrateJVforSalesReturnPropertyWiseCustomer(clsStkTransferHdModel objModel, List<clsStkTransferDtlModel> listDtlModel, String str, String clientCode, String userCode, String propCode, HttpServletRequest req)
	{
		JSONObject jObjJVData = new JSONObject();

		JSONArray jArrJVdtl = new JSONArray();
		JSONArray jArrJVDebtordtl = new JSONArray();
		String jvCode = "";
		String locTo = objModel.getStrToLocCode();
		String suppCode = "";
		String sqlSuppCode = " select a.strPCode from tblpartymaster a " + "  where a.strLocCode='" + locTo + "' and a.strPType='supp' and a.strClientCode='" + clientCode + "'  ";
		List listParty = objGlobalFunctionsService.funGetList(sqlSuppCode, "sql");
		for (int i = 0; i < listParty.size(); i++)
		{
			suppCode = listParty.get(0).toString();
		}
		clsLocationMasterModel objloc = objLocationMasterService.funGetObject(objModel.getStrFromLocCode(), clientCode);
		propCode = objloc.getStrPropertyCode();
		String debitAmt = objModel.getDblTotalAmt();
		clsLinkUpHdModel objLinkCust = objLinkupService.funGetARLinkUp(suppCode, clientCode, propCode, "Customer", "Sale");
		if (objLinkCust != null)
		{
			if (objModel.getStrNo().equals(""))
			{
				jObjJVData.put("strVouchNo", "");
				jObjJVData.put("strNarration", "JV Genrated by Pur-Ret:" + objModel.getStrSTCode());
				jObjJVData.put("strSancCode", "");
				jObjJVData.put("strType", "");
				jObjJVData.put("dteVouchDate", objModel.getDtSTDate());
				jObjJVData.put("intVouchMonth", 1);
				jObjJVData.put("dblAmt", debitAmt);
				jObjJVData.put("strTransType", "R");
				jObjJVData.put("strTransMode", "A");
				jObjJVData.put("strModuleType", "AP");
				jObjJVData.put("strMasterPOS", "WEBSTOCK");
				jObjJVData.put("strUserCreated", userCode);
				jObjJVData.put("strUserEdited", userCode);
				jObjJVData.put("dteDateCreated", objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjJVData.put("dteDateEdited", objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjJVData.put("strClientCode", clientCode);
				jObjJVData.put("strPropertyCode", propCode);

			}
			else
			{
				jObjJVData.put("strVouchNo", objModel.getStrNo());
				jObjJVData.put("strNarration", "JV Genrated by Pur-Ret:" + objModel.getStrSTCode());
				jObjJVData.put("strSancCode", "");
				jObjJVData.put("strType", "");
				jObjJVData.put("dteVouchDate", objModel.getDtSTDate());
				jObjJVData.put("intVouchMonth", 1);
				jObjJVData.put("dblAmt", debitAmt);
				jObjJVData.put("strTransType", "R");
				jObjJVData.put("strTransMode", "A");
				jObjJVData.put("strModuleType", "AP");
				jObjJVData.put("strMasterPOS", "WEBSTOCK");
				jObjJVData.put("strUserCreated", userCode);
				jObjJVData.put("strUserEdited", userCode);
				jObjJVData.put("dteDateCreated", objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjJVData.put("dteDateEdited", objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjJVData.put("strClientCode", clientCode);
				jObjJVData.put("strPropertyCode", propCode);

			}
			// jvhd entry end

			// jvdtl entry Start
			for (clsStkTransferDtlModel objDtl : listDtlModel)
			{

				JSONObject jObjDtl = new JSONObject();

				clsProductMasterModel objProdModle = objProductMasterService.funGetObject(objDtl.getStrProdCode(), clientCode);
				clsLinkUpHdModel objLinkSubGroup = objLinkupService.funGetARLinkUp(objProdModle.getStrSGCode(), clientCode, propCode, "SubGroup", "Sale");
				if (objProdModle != null && objLinkSubGroup != null)
				{
					jObjDtl.put("strVouchNo", "");
					jObjDtl.put("strAccountCode", objLinkSubGroup.getStrAccountCode());
					jObjDtl.put("strAccountName", objLinkSubGroup.getStrMasterDesc());
					jObjDtl.put("strCrDr", "Cr");
					jObjDtl.put("dblDrAmt", 0.00);
					jObjDtl.put("dblCrAmt", objDtl.getDblTotalPrice());
					jObjDtl.put("strNarration", "WS Product code :" + objDtl.getStrProdCode());
					jObjDtl.put("strOneLine", "R");
					jObjDtl.put("strClientCode", clientCode);
					jObjDtl.put("strPropertyCode", propCode);
					jArrJVdtl.add(jObjDtl);
				}
			}

			/*
			 * if(listTaxDtl!=null) { for(clsGRNTaxDtlModel objTaxDtl : listTaxDtl) {
			 * JSONObject jObjTaxDtl =new JSONObject(); clsLinkUpHdModel objLinkTax =
			 * objLinkupService.funGetARLinkUp(objTaxDtl
			 * .getStrTaxCode(),clientCode,propCode); if(objLinkTax!=null ) {
			 * jObjTaxDtl.put("strVouchNo", ""); jObjTaxDtl.put("strAccountCode",
			 * objLinkTax.getStrAccountCode()); jObjTaxDtl.put("strAccountName",
			 * objLinkTax.getStrGDes()); jObjTaxDtl.put("strCrDr", "Dr");
			 * jObjTaxDtl.put("dblDrAmt", objTaxDtl.getStrTaxAmt());
			 * jObjTaxDtl.put("dblCrAmt", 0.00); jObjTaxDtl.put("strNarration",
			 * "WS Tax Desc :"+objTaxDtl.getStrTaxDesc()); jObjTaxDtl.put("strOneLine",
			 * "R"); jObjTaxDtl.put("strClientCode", clientCode);
			 * jObjTaxDtl.put("strPropertyCode", propCode); jArrJVdtl.add(jObjTaxDtl); } } }
			 */
			JSONObject jObjCustDtl = new JSONObject();
			jObjCustDtl.put("strVouchNo", "");
			jObjCustDtl.put("strAccountCode", "");
			jObjCustDtl.put("strAccountName", "");
			jObjCustDtl.put("strCrDr", "Dr");
			jObjCustDtl.put("dblDrAmt", objModel.getDblTotalAmt());
			jObjCustDtl.put("dblCrAmt", 0.00);
			jObjCustDtl.put("strNarration", "GRN Supplier Return");
			jObjCustDtl.put("strOneLine", "R");
			jObjCustDtl.put("strClientCode", clientCode);
			jObjCustDtl.put("strPropertyCode", propCode);
			jArrJVdtl.add(jObjCustDtl);

			jObjJVData.put("ArrJVDtl", jArrJVdtl);

			// jvdtl entry end

			// jvDebtor detail entry start
			String sql = "  select a.strSTCode,a.dblTotalAmt,b.strDebtorCode,b.strPName,date(a.dtSTDate), " + "  a.strNarration ,date(a.dtSTDate),a.strNo " + " from dbwebmms.tblstocktransferhd a,dbwebmms.tblpartymaster b  " + "  where a.strToLocCode =b.strLocCode  " + "   and a.strSTCode='" + objModel.getStrSTCode() + "' and a.strClientCode='" + clientCode + "'   ";
			List listTax = objGlobalFunctionsService.funGetList(sql, "sql");
			for (int i = 0; i < listTax.size(); i++)
			{
				JSONObject jObjDtl = new JSONObject();
				Object[] ob = (Object[]) listTax.get(i);
				jObjDtl.put("strVouchNo", "");
				jObjDtl.put("strDebtorCode", ob[2].toString());
				jObjDtl.put("strDebtorName", ob[3].toString());
				jObjDtl.put("strCrDr", "Cr");
				jObjDtl.put("dblAmt", ob[1].toString());
				jObjDtl.put("strBillNo", ob[7].toString());
				jObjDtl.put("strInvoiceNo", ob[0].toString());
				jObjDtl.put("strGuest", "");
				jObjDtl.put("strAccountCode", objLinkCust.getStrAccountCode());
				jObjDtl.put("strCreditNo", "");
				jObjDtl.put("dteBillDate", ob[4].toString());
				jObjDtl.put("dteInvoiceDate", ob[4].toString());
				jObjDtl.put("strNarration", ob[5].toString());
				jObjDtl.put("dteDueDate", ob[6].toString());
				jObjDtl.put("strClientCode", clientCode);
				jObjDtl.put("strPropertyCode", propCode);
				jObjDtl.put("strPOSCode", "");
				jObjDtl.put("strPOSName", "");
				jObjDtl.put("strRegistrationNo", "");

				jArrJVDebtordtl.add(jObjDtl);
			}

			jObjJVData.put("ArrJVDebtordtl", jArrJVDebtordtl);
			// jvDebtor detail entry end

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/WebBooksIntegration/funGenrateJVforGRN", jObjJVData);
			jvCode = jObj.get("strJVCode").toString();
		}
		return jvCode;
	}

	/**
	 * Export to Excel Stock Transfer Report
	 */
	@RequestMapping(value = "/rpttransferslipExcel", method = RequestMethod.POST)
	private ModelAndView funStockTransferExportXLSReport(@ModelAttribute("command") clsReportBean objBean, @RequestParam(value = "param1") String param1, HttpServletResponse resp, HttpServletRequest req, ModelMap map)
	{
		String[] spParam1 = param1.split(",");
		String LocCodeFrom = spParam1[1];
		String LocCodeTo = spParam1[2];

		String strFromSTCode = objBean.getStrFromDocCode();
		String strToSTCode = objBean.getStrToDocCode();

		ModelAndView mv = new ModelAndView();
		objGlobal = new clsGlobalFunctions();
		try
		{
			String startDate = req.getSession().getAttribute("startDate").toString();
			String[] sp = startDate.split(" ");
			String[] spDate = sp[0].split("/");
			startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];

			String toDate = objGlobal.funGetCurrentDate("yyyy-MM-dd");
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();

			String fromDate = startDate;
			String stockableItem = "Y";
			// funStockFlash(startDate, LocCode, fromDate, toDate, clientCode, userCode,
			// stockableItem, req, resp);
			// objGlobalFunctions.funInvokeStockFlash(startDate, LocCode,
			// fromDate, toDate, clientCode, userCode,stockableItem);
			String sqlHDQuery = " select a.strSTCode,a.strProdCode,b.strProdName,a.dblQty,a.dblWeight,a.dblPrice,a.strRemark " + " from tblstocktransferdtl a,tblproductmaster b " + " where a.strProdCode=b.strProdCode and a.strSTCode='" + strFromSTCode + "' and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' ";

			mv = funExcelExport(LocCodeFrom, sqlHDQuery, req, map);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * Excel Export Function
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ModelAndView funExcelExport(String stCode, String sql, HttpServletRequest req, ModelMap map)
	{
		List listStock = new ArrayList();
		String[] ExcelHeader = { "Sr.No.", "Product Code", "Product Name", "Qty", "Price", "Remark" };
		listStock.add(ExcelHeader);
		double qty = 0.0;
		double price = 0.0;

		List listStockFlashModel = new ArrayList();
		List list = objGlobalFunctionsService.funGetList(sql, "sql");
		if (list.size() > 0 && !list.isEmpty() && list != null)
		{
			for (int i = 0; i < list.size(); i++)
			{
				Object[] arrObj = (Object[]) list.get(i);
				List DataList = new ArrayList<>();

				qty = qty + Double.parseDouble(arrObj[3].toString());
				price = price + Double.parseDouble(arrObj[5].toString());

				DataList.add(i + 1);
				DataList.add(arrObj[1].toString());
				DataList.add(arrObj[2].toString());
				DataList.add(Double.parseDouble(arrObj[3].toString()));
				DataList.add(Double.parseDouble(arrObj[5].toString()));
				DataList.add(arrObj[6].toString());

				listStockFlashModel.add(DataList);
			}

			List totalData = new ArrayList();
			totalData.add("");
			totalData.add("");
			totalData.add("Total");
			totalData.add(qty);
			totalData.add(price);
			totalData.add("");

			listStockFlashModel.add(totalData);

			listStock.add(listStockFlashModel);

		}
		// return a view which will be resolved by an excel view resolver
		return new ModelAndView("excelView", "stocklist", listStock);

	}

	/**
	 * Open Pending Material Requisition
	 * 
	 * @return
	 */
	@RequestMapping(value = "/frmStockTransferforSR", method = RequestMethod.GET)
	public ModelAndView funOpenMISforMR() {
		return new ModelAndView("frmStockTransferforSR");

	}
	
	/**
	 * Load on form; Pending Material Requisition Data
	 * 
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/loadStockTransferforSR", method = RequestMethod.GET)
	public @ResponseBody List funOpenHelpStockTransferforSR(HttpServletRequest req) {
		String strLocFrom = req.getParameter("strLocFrom").toString();
		String strLocTo = req.getParameter("strLocTo").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		return objStkTransService.funStkforSRDetails(strLocFrom, strLocTo, clientCode);
	}
}
