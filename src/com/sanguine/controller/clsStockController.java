package com.sanguine.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.sanguine.bean.clsOpeningStkBean;
import com.sanguine.model.clsAuditDtlModel;
import com.sanguine.model.clsAuditHdModel;
import com.sanguine.model.clsInitialInventoryModel;
import com.sanguine.model.clsOpeningStkDtl;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsStockService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsStockController {
	@Autowired
	private clsStockService objStkService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	@Autowired
	private ServletContext servletContext;

	@RequestMapping(value = "/frmOpeningStock", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmOpeningStock");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmOpeningStock_1", "command", new clsOpeningStkBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmOpeningStock", "command", new clsOpeningStkBean());
		} else {
			return null;
		}

	}

	// Save or Update.
	@RequestMapping(value = "/saveOpeningStk", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsOpeningStkBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		objGlobal = new clsGlobalFunctions();
		if (!result.hasErrors()) {
			clsInitialInventoryModel objHdModel = funPrepareModel(objBean, userCode, clientCode, propCode, startDate, req);
			List<clsOpeningStkDtl> listOpStkDtl = objBean.getListOpStkDtl();
			if (null != listOpStkDtl && listOpStkDtl.size() > 0) {

				String opStkCode = objHdModel.getStrOpStkCode();
				objStkService.funDeleteDtl(opStkCode, clientCode);

				for (clsOpeningStkDtl ob : listOpStkDtl) {
					if (null != ob.getStrProdCode()) {
						ob.setStrOpStkCode(objHdModel.getStrOpStkCode());
						ob.setStrClientCode(objHdModel.getStrClientCode());
						objStkService.funAddUpdateDtl(ob);
					}
				}
				objStkService.funAddUpdate(objHdModel);

				String updateUnitPriceQuery = "update tblproductmaster a join (select dblCostPerUnit as dblCostPerUnit ,strProdCode,strOpStkCode from tblinitialinvdtl) " + "b on b.strProdCode = a.strProdCode set a.dblCostRM = b.dblCostPerUnit " + " where b.strOpStkCode='" + objHdModel.getStrOpStkCode() + "' and a.strClientCode='" + clientCode + "' and b.dblCostPerUnit > 0 ";
				objGlobalFunctionsService.funUpdate(updateUnitPriceQuery, "sql");
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Stock Code : ".concat(objHdModel.getStrOpStkCode()));
				req.getSession().setAttribute("rptOPStkCode", objHdModel.getStrOpStkCode());
			}
			return new ModelAndView("redirect:/frmOpeningStock.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmOpeningStock?saddr=" + urlHits, "command", new clsOpeningStkBean());
		}
	}

	@RequestMapping(value = "/loadOpStkHdData", method = RequestMethod.GET)
	public @ResponseBody clsInitialInventoryModel funAssignFields(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		objGlobal = new clsGlobalFunctions();
		String opStkCode = request.getParameter("opStkCode").toString();

		clsInitialInventoryModel objOpStkHd = objStkService.funGetObject(opStkCode, clientCode);
		if (null == objOpStkHd) {
			objOpStkHd = new clsInitialInventoryModel();
			objOpStkHd.setStrOpStkCode("Invalid Code");
		} else {

			objOpStkHd.setDtExpDate(objGlobal.funGetDate("yyyy/MM/dd", objOpStkHd.getDtExpDate()));
		}
		return objOpStkHd;
	}

	@RequestMapping(value = "/loadOpStkDtlData", method = RequestMethod.GET)
	public @ResponseBody List funAssignFieldsForDtl(HttpServletRequest request) {
		String opStkCode = request.getParameter("opStkCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List<clsOpeningStkDtl> listOpStkDtl = new ArrayList<clsOpeningStkDtl>();
		objGlobal = new clsGlobalFunctions();
		List list = objStkService.funGetDtlList(opStkCode, clientCode);
		for (int i = 0; i < list.size(); i++) {
			Object[] ob = (Object[]) list.get(i);
			clsOpeningStkDtl objOpStkDtlModel = new clsOpeningStkDtl();
			objOpStkDtlModel.setStrOpStkCode(opStkCode);
			objOpStkDtlModel.setStrProdCode(ob[0].toString());
			objOpStkDtlModel.setStrProdName(ob[1].toString());
			objOpStkDtlModel.setDblQty(Double.parseDouble(ob[2].toString()));
			objOpStkDtlModel.setStrUOM(ob[3].toString());
			objOpStkDtlModel.setDblCostPUnit(Double.parseDouble(ob[4].toString()));
			objOpStkDtlModel.setDblRevLvl(Double.parseDouble(ob[5].toString()));
			objOpStkDtlModel.setStrLotNo(ob[6].toString());
			objOpStkDtlModel.setStrDisplyQty(ob[7].toString());
			objOpStkDtlModel.setDblLooseQty(Double.parseDouble(ob[8].toString()));
			listOpStkDtl.add(objOpStkDtlModel);
		}

		return listOpStkDtl;
	}

	public clsInitialInventoryModel funPrepareModel(clsOpeningStkBean objBean, String userCode, String clientCode, String propCode, String startDate, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsInitialInventoryModel objModel = new clsInitialInventoryModel();
		String statrDate = req.getSession().getAttribute("startDate").toString();
		String[] spDate = statrDate.split("/");
		String day = spDate[0];
		String month = spDate[1];
		String Year = spDate[2];

		String stDate = Year + "-" + month + "-" + day;
		if (objBean.getStrOpStkCode().trim().length() == 0) {
			String code = objGlobalFunctions.funGenerateDocumentCode("frmOpeningStock", day + "-" + month + "-" + Year, req);
			lastNo = objGlobalFunctions.funGenerateDocumentCodeLastNo("frmOpeningStock", day + "-" + month + "-" + Year, req);
			/*
			 * lastNo=objGlobalFunctionsService.funGetLastNo("tblinitialinventory"
			 * ,"OPStkCode","intId", clientCode); String
			 * year=objGlobal.funGetSplitedDate(startDate)[2]; String
			 * cd=objGlobal.funGetTransactionCode("OP",propCode,year); String
			 * code = cd + String.format("%06d", lastNo);
			 */
			objModel.setStrOpStkCode(code);
			objModel.setIntId(lastNo);
			objModel.setStrUserCreated(userCode);
			objModel.setDtCreatedDate(stDate);
		} else {
			clsInitialInventoryModel objOpStkHd = objStkService.funGetObject(objBean.getStrOpStkCode(), clientCode);
			if (null == objOpStkHd) {
				String code = objGlobalFunctions.funGenerateDocumentCode("frmOpeningStock", day + "-" + month + "-" + Year, req);
				lastNo = objGlobalFunctions.funGenerateDocumentCodeLastNo("frmOpeningStock", day + "-" + month + "-" + Year, req);
				/*
				 * lastNo=objGlobalFunctionsService.funGetLastNo(
				 * "tblinitialinventory","OPStkCode","intId", clientCode);
				 * String year=objGlobal.funGetSplitedDate(startDate)[2]; String
				 * cd=objGlobal.funGetTransactionCode("OP",propCode,year);
				 * String code = cd + String.format("%06d", lastNo);
				 */
				objModel.setStrOpStkCode(code);
				objModel.setIntId(lastNo);
				objModel.setStrUserCreated(userCode);
				objModel.setDtCreatedDate(stDate);
			} else {

				if ("Y".equalsIgnoreCase(req.getSession().getAttribute("audit").toString())) {
					funSaveAudit(objBean.getStrOpStkCode(), "Edit", req);
				}
				objModel.setStrOpStkCode(objBean.getStrOpStkCode());
			}
		}
		objModel.setDtExpDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtExpDate()));
		objModel.setStrLocCode(objBean.getStrLocCode());
		objModel.setStrUserModified(userCode);
		objModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrClientCode(clientCode);
		objModel.setStrConversionUOM(objBean.getStrConversionUOM());
		return objModel;
	}

	@SuppressWarnings({ "finally", "rawtypes", "unchecked" })
	@RequestMapping(value = "/checkProductData", method = RequestMethod.POST)
	private @ResponseBody List funCheckOpeningStockData(clsOpeningStkBean objBean, BindingResult result, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String sql = "";
		List list = new ArrayList();
		List Responselist = new ArrayList();
		Set<String> OpeningCodelist = new HashSet<String>();
		Set<String> ProdCodelist = new HashSet<String>();
		try {
			if (!result.hasErrors()) {
				String locCode = objBean.getStrLocCode();
				List<clsOpeningStkDtl> listOpStkDtl = objBean.getListOpStkDtl();
				if (null != listOpStkDtl && listOpStkDtl.size() > 0) {
					for (clsOpeningStkDtl ob : listOpStkDtl) {
						if (null != ob.getStrProdCode()) {
							sql = "select a.strOpStkCode from tblinitialinvdtl a,tblinitialinventory b where a.strOpStkCode=b.strOpStkCode " + " and a.strProdCode='" + ob.getStrProdCode() + "' and b.strLocCode='" + locCode + "' " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' ";
							list = objGlobalFunctionsService.funGetList(sql, "sql");
							if (!list.isEmpty()) {
								for (int cnt = 0; cnt < list.size(); cnt++) {
									ProdCodelist.add(ob.getStrProdCode());
									OpeningCodelist.add(list.get(cnt).toString());

								}

							}
						}
					}
					if (!ProdCodelist.isEmpty() && !OpeningCodelist.isEmpty()) {
						Responselist.add(ProdCodelist);
						Responselist.add(OpeningCodelist);
					}
				}
				if (Responselist.isEmpty()) {
					Responselist.add("Empty");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return Responselist;
		}

	}

	/**
	 * Audit Fuction Start
	 * 
	 * @param stCode
	 * @param req
	 */
	@SuppressWarnings("rawtypes")
	public void funSaveAudit(String opStkCode, String strTransMode, HttpServletRequest req) {
		try {
			objGlobal = new clsGlobalFunctions();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsInitialInventoryModel objOpStkHd = objStkService.funGetObject(opStkCode, clientCode);
			if (objOpStkHd != null) {
				List listTempDtl = objStkService.funGetDtlList(opStkCode, clientCode);
				if (null != objOpStkHd) {
					if (null != listTempDtl && listTempDtl.size() > 0) {
						String sql = "select count(*)+1 from tblaudithd where left(strTransCode,12)='" + objOpStkHd.getStrOpStkCode() + "'";
						List list = objGlobalFunctionsService.funGetList(sql, "sql");

						if (!list.isEmpty()) {
							String count = list.get(0).toString();
							clsAuditHdModel model = funPrepairAuditHdModel(objOpStkHd);
							if (strTransMode.equalsIgnoreCase("Deleted")) {
								model.setStrTransCode(objOpStkHd.getStrOpStkCode());
							} else {
								model.setStrTransCode(objOpStkHd.getStrOpStkCode() + "-" + count);
							}
							model.setStrClientCode(clientCode);
							model.setStrTransMode(strTransMode);
							model.setStrUserAmed(userCode);
							model.setDtLastModified(objGlobal.funGetCurrentDate("yyyy-MM-dd"));
							model.setStrUserModified(userCode);
							objGlobalFunctionsService.funSaveAuditHd(model);
							for (int i = 0; i < listTempDtl.size(); i++) {
								Object[] ob1 = (Object[]) listTempDtl.get(i);
								clsAuditDtlModel AuditMode = new clsAuditDtlModel();
								AuditMode.setStrTransCode(model.getStrTransCode());
								AuditMode.setStrProdCode(ob1[0].toString());
								AuditMode.setDblQty(Double.parseDouble(ob1[2].toString()));
								AuditMode.setStrUOM(ob1[3].toString());
								AuditMode.setDblUnitPrice(Double.parseDouble(ob1[4].toString()));
								AuditMode.setDblRevLvl(Double.parseDouble(ob1[5].toString()));
								AuditMode.setStrLotNo(ob1[6].toString());
								AuditMode.setStrClientCode(clientCode);
								AuditMode.setStrRemarks("");
								AuditMode.setStrAgainst("");
								AuditMode.setStrCode("");
								AuditMode.setStrTaxType("");
								AuditMode.setStrPICode("");
								AuditMode.setStrProdChar(ob1[7].toString());
								objGlobalFunctionsService.funSaveAuditDtl(AuditMode);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	private clsAuditHdModel funPrepairAuditHdModel(clsInitialInventoryModel objOpStkHd) {
		clsAuditHdModel AuditHdModel = new clsAuditHdModel();
		if (objOpStkHd != null) {
			AuditHdModel.setStrTransCode(objOpStkHd.getStrOpStkCode());
			AuditHdModel.setDtTransDate(objOpStkHd.getDtCreatedDate());
			AuditHdModel.setStrTransType("Opening Stock");
			AuditHdModel.setStrLocCode(objOpStkHd.getStrLocCode());
			AuditHdModel.setStrUserCreated(objOpStkHd.getStrUserCreated());
			AuditHdModel.setDtDateCreated(objOpStkHd.getDtCreatedDate());
			AuditHdModel.setStrLocBy("");
			AuditHdModel.setStrLocOn("");
			AuditHdModel.setStrAgainst("");
			AuditHdModel.setStrAuthorise("");
			AuditHdModel.setStrNarration("");
			AuditHdModel.setStrNo("");
			AuditHdModel.setStrCode("");
			AuditHdModel.setStrMInBy("");
			AuditHdModel.setStrTimeInOut("");
			AuditHdModel.setStrVehNo("");
			AuditHdModel.setStrGRNCode("");
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

	@RequestMapping(value = "/frmOpeningStockSlip", method = RequestMethod.GET)
	public ModelAndView funOpenform(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmOpeningStockSlip_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmOpeningStockSlip", "command", new clsReportBean());
		}

	}

	@RequestMapping(value = "/openRptOpeningStockSlip", method = RequestMethod.GET)
	private void funCallReportOnSubmit(HttpServletResponse resp, HttpServletRequest req) {
		String docCode = req.getParameter("rptOPStkCode").toString();
		// req.getSession().removeAttribute("rptGRNCode");
		String type = "pdf";
		funCallOpeningReport(docCode, type, resp, req);

	}

	@RequestMapping(value = "/rptOpeningStockSlip", method = RequestMethod.GET)
	private void funOpenOpeningReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String docCode = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		funCallOpeningReport(docCode, type, resp, req);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void funCallOpeningReport(String docCode, String type, HttpServletResponse resp, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		Connection con = objGlobal.funGetConnection(req);
		try {

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			String sql = "select a.strOpStkCode,date_format(a.dtCreatedDate, '%d-%m-%Y'),b.strLocName " + " from tblinitialinventory a,tbllocationmaster b " + " where a.strLocCode=b.strLocCode and a.strOpStkCode='" + docCode + "' ";
			List list = objGlobalFunctionsService.funGetList(sql, "sql");
			Object[] ob = (Object[]) list.get(0);
			String strOpStkCode = ob[0].toString();
			String dtOpeningStkdate = ob[1].toString();
			String LocName = ob[2].toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptOpeningStockSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			String sqlHDQuery = "select d.strGName,c.strSGName,a.strProdCode,b.strProdName,a.strUOM,a.dblQty,a.dblCostPerUnit ,(a.dblQty*a.dblCostPerUnit) as Value " + " from tblinitialinvdtl a, tblproductmaster b, tblsubgroupmaster c, tblgroupmaster d  " + " where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=d.strGCode " + " and a.strOpStkCode='" + docCode
					+ "' and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + "and c.strClientCode='" + clientCode + "' and d.strClientCode='" + clientCode + "' ";
			// System.out.println(sqlHDQuery);
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlHDQuery);
			jd.setQuery(newQuery);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			HashMap hm = new HashMap();
			hm.put("strOpStkCode", strOpStkCode);
			hm.put("dtOpeningStkdate", dtOpeningStkdate);
			hm.put("LocName", LocName);
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);
			// System.out.println(imagePath);
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
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptOpeningStockSlip." + type.trim());
				resp.setContentLength(bytes.length);
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRPdfExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptOpeningStockSlip." + type.trim());
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

}
