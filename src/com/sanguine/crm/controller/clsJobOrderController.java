package com.sanguine.crm.controller;

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
import com.sanguine.crm.bean.clsJobOrderBean;
import com.sanguine.crm.model.clsJobOrderModel;
import com.sanguine.crm.model.clsSalesOrderBOMModel;
import com.sanguine.crm.model.clsSalesOrderDtl;
import com.sanguine.crm.service.clsJobOrderService;
import com.sanguine.crm.service.clsSalesOrderBOMService;
import com.sanguine.crm.service.clsSalesOrderService;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsJobOrderController {

	@Autowired
	private clsJobOrderService objJobOrderService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsSalesOrderService objSalesOrderService;

	@Autowired
	private clsSalesOrderBOMService objSOBOMService;

	@Autowired
	private clsProductMasterService objProdMastService;

	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	// Open JobOrder
	@RequestMapping(value = "/frmJobOrder", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest req) {

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmJobOrder_1", "command", new clsJobOrderBean());
		} else {
			return new ModelAndView("frmJobOrder", "command", new clsJobOrderBean());
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadJobOrderAgainst", method = RequestMethod.GET)
	public @ResponseBody List funLoadSalesOrderData(@RequestParam("strAgainst") String strAgainst, @RequestParam("SOcode") String strSOcode, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List resList = new ArrayList();

		if (strAgainst.equalsIgnoreCase("salesOrder")) {

			List list = objJobOrderService.funGetJobOrderUsingSOCode(strSOcode, clientCode);

			for (int i = 0; i < list.size(); i++) {

				Object[] ob = (Object[]) list.get(i);
				clsJobOrderModel objJO = new clsJobOrderModel();
				objJO.setStrJOCode(ob[0].toString());
				objJO.setStrSOCode(ob[1].toString());
				objJO.setStrProdCode(ob[2].toString());
				objJO.setStrProdName(ob[3].toString());
				objJO.setDblQty(Double.parseDouble(ob[4].toString()));
				if (ob[5].toString().equals("") || ob[5].toString().equals("Pending")) {
					objJO.setStrStatus("Pending");
				} else {
					objJO.setStrStatus(ob[5].toString());
				}

				/*
				 * clsSalesOrderBOMModel objSOBOM = (clsSalesOrderBOMModel)
				 * ob[0]; clsProductMasterModel prodmast =
				 * (clsProductMasterModel) ob[1]; clsJobOrderModel objJO =
				 * (clsJobOrderModel) ob[2];
				 * objJO.setStrProdName(prodmast.getStrProdName());
				 */
				resList.add(objJO);
			}

		} else if (strAgainst.equalsIgnoreCase("productionOrder")) {

		} else if (strAgainst.equalsIgnoreCase("serviceOrder")) {

		}
		return resList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/generateJobOrderAgainst", method = RequestMethod.GET)
	public @ResponseBody List funGenerateJobOrder(@RequestParam("strAgainst") String strAgainst, @RequestParam("SOcode") String strSOcode, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		objGlobal = new clsGlobalFunctions();
		List respList = new ArrayList();

		if (strAgainst.equalsIgnoreCase("salesOrder")) {

			List<Object> objSalesDtlModelList = objSalesOrderService.funGetSalesOrderDtl(strSOcode, clientCode);

			for (int i = 0; i < objSalesDtlModelList.size(); i++) {

				Object[] ob = (Object[]) objSalesDtlModelList.get(i);
				clsSalesOrderDtl saleDtl = (clsSalesOrderDtl) ob[0];
				clsProductMasterModel prodmast = (clsProductMasterModel) ob[1];
				Double salesOrderQty = saleDtl.getDblQty();

				if (prodmast.getStrProdType().toString().equalsIgnoreCase("Sub-Contracted")) {

					List tempList = funIsJobOrderAvailable(saleDtl.getStrProdCode(), strSOcode, "", salesOrderQty, req);
					if (tempList.size() > 0) {
						respList.addAll(tempList);
					}

				} else if (prodmast.getStrProdType().toString().equalsIgnoreCase("Produced")) {

					List tempList = funIsJobOrderAvailable(saleDtl.getStrProdCode(), strSOcode, "", salesOrderQty, req);
					if (tempList.size() > 0) {
						respList.addAll(tempList);
					}
				}
			}

		} else if (strAgainst.equalsIgnoreCase("productionOrder")) {

		} else if (strAgainst.equalsIgnoreCase("serviceOrder")) {

		}
		return respList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List funIsJobOrderAvailable(String productCode, String strSOcode, String ParentJOCode, Double salesOrderQty, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		objGlobal = new clsGlobalFunctions();

		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		String year = objGlobal.funGetSplitedDate(startDate)[2];

		List respList = new ArrayList();

		// List ListSOBOMModel =
		// objSOBOMService.funGetListOnParent(strSOcode,productCode,clientCode);

		List ListSOBOMModel = objSOBOMService.funGetListOnProdCode(strSOcode, productCode, clientCode);

		for (int i = 0; ListSOBOMModel.size() > i; i++) {

			Object[] objArray = (Object[]) ListSOBOMModel.get(i);

			clsSalesOrderBOMModel saleDtl = (clsSalesOrderBOMModel) objArray[0];
			clsProductMasterModel productmast = (clsProductMasterModel) objArray[1];

			if (productmast.getStrProdType().toString().equalsIgnoreCase("Sub-Contracted")) {

				clsJobOrderModel objModel = new clsJobOrderModel();

				Long lastNo = objGlobalFunctionsService.funGetCount("tbljoborderhd", "intId");
				String Jd = objGlobal.funGetTransactionCode("JO", propCode, year);
				String strJOCode = Jd + String.format("%06d", lastNo);

				objModel.setStrJOCode(strJOCode);
				objModel.setIntId(lastNo);
				objModel.setDteJODate(objGlobal.funGetCurrentDate("yyyy-MM-dd"));
				objModel.setStrSOCode(strSOcode);
				objModel.setStrProdCode(productmast.getStrProdCode());
				objModel.setStrProdName(productmast.getStrProdName());
				objModel.setStrParentJOCode(ParentJOCode);
				objModel.setStrStatus("In Progress");
				objModel.setStrAuthorise("false");
				objModel.setStrUserCreated(userCode);
				objModel.setStrUserModified(userCode);
				objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setStrClientCode(clientCode);

				Double soQty = salesOrderQty;
				Double soBOMQty = saleDtl.getDblQty();
				Double totalQty = soQty * soBOMQty;

				if (totalQty > 0) {
					objModel.setDblQty(totalQty);
					boolean success = objJobOrderService.funAddUpdateJobOrder(objModel);
					if (success) {
						respList.add(objModel);
					}
				}

				List tempList = funIsJobOrderAvailable(saleDtl.getStrChildCode(), strSOcode, strJOCode, salesOrderQty, req);

				if (tempList.size() > 0) {
					respList.addAll(tempList);
				}
			} else if (productmast.getStrProdType().toString().equalsIgnoreCase("Produced")) {

				List tempList = funIsJobOrderAvailable(saleDtl.getStrChildCode(), strSOcode, "", salesOrderQty, req);
				if (tempList.size() > 0) {
					respList.addAll(tempList);
				}
			}

		}

		return respList;
	}

	@RequestMapping(value = "/saveJobOrder", method = RequestMethod.POST)
	public ModelAndView funSaveJobOrder(@ModelAttribute("command") @Valid clsJobOrderBean objBean, BindingResult result, HttpServletRequest req) {

		String urlHits = "1";
		Boolean success = false;
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (!result.hasErrors()) {
			clsJobOrderModel objModel = funPrepareHdModel(objBean, req);
			success = objJobOrderService.funAddUpdateJobOrder(objModel);
			if (success) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Job Order Code : ".concat(objModel.getStrJOCode()));
				return new ModelAndView("redirect:/frmJobOrder.html?saddr=" + urlHits);
			} else {
				return new ModelAndView("redirect:/frmJobOrder.html?saddr=" + urlHits);
			}

		} else {
			return new ModelAndView("redirect:/frmJobOrder.html?saddr=" + urlHits);
		}

	}

	@RequestMapping(value = "/loadJobOrderData", method = RequestMethod.GET)
	public @ResponseBody clsJobOrderModel funLoadJobOrderData(@RequestParam("JOcode") String JOcode, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		clsJobOrderModel objModel = null;

		List<Object> objModelList = objJobOrderService.funGetJobOrder(JOcode, clientCode);
		if (objModelList.size() > 0) {

			Object[] objArr = (Object[]) objModelList.get(0);
			objModel = (clsJobOrderModel) objArr[0];

			clsProductMasterModel objProdMaster = (clsProductMasterModel) objArr[1];
			objModel.setStrProdName(objProdMaster.getStrProdName());

		} else {
			objModel = new clsJobOrderModel();
			objModel.setStrJOCode("Invalid Code");
		}
		return objModel;
	}

	private clsJobOrderModel funPrepareHdModel(clsJobOrderBean objBean, HttpServletRequest req) {

		objGlobal = new clsGlobalFunctions();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		String year = objGlobal.funGetSplitedDate(startDate)[2];

		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		clsJobOrderModel objModel = new clsJobOrderModel();

		List<Object> objModelList = objJobOrderService.funGetJobOrder(objBean.getStrJOCode(), clientCode);

		if (objModelList.size() > 0) {

			Object[] objArr = (Object[]) objModelList.get(0);
			clsJobOrderModel objModel1 = (clsJobOrderModel) objArr[0];

			objModel.setStrJOCode(objModel1.getStrJOCode());
			objModel.setIntId(objModel1.getIntId());
			objModel.setStrUserCreated(objModel1.getStrUserCreated());
			objModel.setDteDateCreated(objModel1.getDteDateCreated());
			objModel.setStrStatus(objModel1.getStrStatus());

		} else {
			Long lastNo = objGlobalFunctionsService.funGetCount("tbljoborderhd", "intId");
			String Jd = objGlobal.funGetTransactionCode("JO", propCode, year);
			String strJOCode = Jd + String.format("%06d", lastNo);

			objModel.setStrJOCode(strJOCode);
			objModel.setIntId(lastNo);
			objModel.setStrUserCreated(userCode);
			objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			objModel.setStrStatus("In Progress");
		}

		objModel.setDteJODate(objBean.getDteJODate());
		objModel.setStrSOCode(objBean.getStrSOCode());
		objModel.setStrProdCode(objBean.getStrProdCode());
		objModel.setStrParentJOCode("");
		objModel.setStrAuthorise("false");
		objModel.setStrUserModified(userCode);
		objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrClientCode(clientCode);
		objModel.setDblQty(objBean.getDblQty());

		return objModel;
	}

	@RequestMapping(value = "/frmJobOrderSlip", method = RequestMethod.GET)
	public ModelAndView funOpenSalesOrderSlipForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmJobOrderSlip_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmJobOrderSlip", "command", new clsReportBean());
		}
	}

	@RequestMapping(value = "/frmJobOrderList", method = RequestMethod.GET)
	public ModelAndView funOpenSalesOrderListForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		List<String> strAgainst = new ArrayList<>();
		strAgainst.add("Direct");
		strAgainst.add("Sales Order");
		strAgainst.add("Production Order");
		model.put("againstList", strAgainst);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmJobOrderList_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmJobOrderList", "command", new clsReportBean());
		}
	}

	@RequestMapping(value = "/rptJobOrderList", method = RequestMethod.GET)
	private void funDeliveryNoteListReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String SOCode = objBean.getStrSOCode();
		String type = objBean.getStrDocType();
		String fromDate = objBean.getDtFromDate();
		String toDate = objBean.getDtToDate();
		String Against = objBean.getStrAgainst();
		funCallJobOrderListReport(SOCode, type, fromDate, toDate, Against, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallJobOrderListReport(String SOCode, String type, String fromDate, String toDate, String Against, HttpServletResponse resp, HttpServletRequest req) {

		try {

			objGlobal = new clsGlobalFunctions();
			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName;

			reportName = servletContext.getRealPath("/WEB-INF/reports/rptJobOrderList.jrxml");

			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			// List list=objGlobalFunctionsService.funGetList(sqlHd,"sql");
			// if(!list.isEmpty())
			// {
			// Object[] arrObj=(Object[])list.get(0);
			//
			// dteDCDate=arrObj[0].toString();
			// strPName=arrObj[1].toString();
			// strSAdd1=arrObj[2].toString();
			// strSAdd2=arrObj[3].toString();
			// strSCity=arrObj[4].toString();
			// }

			StringBuilder sqlDtl = new StringBuilder();

			sqlDtl.append("select b.strJOCode,b.dteJODate,b.strSOCode,b.strProdCode,a.strPartNo, " + " a.strProdName,b.dblQty,a.strUOM,b.strStatus " + "from tbljoborderhd b,tblproductmaster a  ");

			if (Against.equalsIgnoreCase("Direct")) {
				sqlDtl.append("where b.strSOCode='' ");
			} else {
				sqlDtl.append("where b.strSOCode='" + SOCode + "' ");

			}

			sqlDtl.append("and b.strProdCode=a.strProdCode and b.strClientCode= '" + clientCode + "'  " + "and b.dteJODate  between '" + fromDate + "'and '" + toDate + "'");

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sqlDtl.toString());
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset;
			subDataset = (JRDesignDataset) datasetMap.get("dsJobOrderList");
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
			hm.put("strPin", objSetup.getStrPin());
			hm.put("SOCode", SOCode);
			hm.put("fromDate", fromDate);
			hm.put("toDate", toDate);
			hm.put("Against", Against);

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
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptBillPassingReport." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	@RequestMapping(value = "/rptJobOrderSlip", method = RequestMethod.GET)
	private void funReportJobOrderReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String JOCode = objBean.getStrDocCode();
		String type = objBean.getStrDocType();

		funCallReportJobOrderReport(JOCode, type, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallReportJobOrderReport(String JOCode, String type, HttpServletResponse resp, HttpServletRequest req) {
		try {

			String strJOCode = "";
			String dteJODate = "";
			String strPartNo = "";
			String strRemark = "";

			objGlobal = new clsGlobalFunctions();
			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptSalesOrderSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHd = "select a.strJOCode ,date(a.dteJODate),b.strPartNo,b.strRemark " + "	from tbljoborderhd a ,tblproductmaster b  " + "  where a.strProdCode=b.strProdCode " + "  and a.strJOCode='" + JOCode + "' and a.strClientCode='" + clientCode + "' " + "	and a.strClientCode=b.strClientCode";
			List list = objGlobalFunctionsService.funGetList(sqlHd, "sql");
			if (!list.isEmpty()) {
				Object[] arrObj = (Object[]) list.get(0);
				strJOCode = arrObj[0].toString();
				dteJODate = arrObj[1].toString();
				strPartNo = arrObj[2].toString();
				strRemark = arrObj[3].toString();

			}

			String sqlDtl = " ";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sqlDtl);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("ds");
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
			hm.put("strPartNo", strPartNo);
			hm.put("strRemark", strRemark);
			hm.put("strJOCode", strJOCode);
			hm.put("dteJODate", dteJODate);

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
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptBillPassingReport." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
