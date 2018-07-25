package com.sanguine.excise.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

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

import com.sanguine.bean.clsStockAdjFlashBean;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.excise.bean.clsExciseStockFlashBean;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsStkAdjustmentDtlModel;
import com.sanguine.model.clsStkAdjustmentHdModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.util.clsComparatorForOwnSorting;
import com.sanguine.webpos.bean.clsGroupSubGroupItemBean;

@Controller
public class clsExciseStkFlash {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/frmExciseStockFlash", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") clsExciseStockFlashBean objPropBean, BindingResult result, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		ArrayList<String> listLicence = new ArrayList<String>();
		ModelAndView objModelView = new ModelAndView("frmExciseStockFlash");

		String Licence_sql = "select a.strLicenceNo,CONCAT(a.strAddress1,',',a.strAddress2," + "',',a.strAddress3,',',b.strCityName,CONCAT('-',a.strPINCode)) as address,a.strLicenceCode " + "from tbllicencemaster a ,tblcitymaster b " + "where a.strCity=b.strCityCode and a.strClientCode='" + clientCode + "' and a.strPropertyCode='" + propertyCode + "' ";
		List licenceMaster = objGlobalFunctionsService.funGetDataList(Licence_sql, "sql");
		if (licenceMaster.size() > 0) {
			for (int i = 0; i < licenceMaster.size(); i++) {

				Object licenceData[] = (Object[]) licenceMaster.get(i);
				String licenceNo = licenceData[0].toString();
				listLicence.add(licenceData[2].toString());

			}
		} else {
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Please Add Licence To This Client");
		}
		listLicence.add("ALL");
		objModelView.addObject("listLicence", listLicence);

		return objModelView;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/flshExciseStockDetailReport", method = RequestMethod.GET)
	private @ResponseBody List funShowStockDetailsFlash(@RequestParam(value = "param1") String param1, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String[] spParam1 = param1.split(",");
		// String reportType=spParam1[0];
		String licCode = spParam1[1];
		String type = "pdf";
		// String propCode=spParam1[2];
		// String strReasonCode=spParam1[3];
		String saleType = spParam1[2];
		String fromDate = objGlobal.funGetDate("yyyy-MM-dd", fDate);
		String toDate = objGlobal.funGetDate("yyyy-MM-dd", tDate);

		String sql = "select  a.strSalesCode, a.dteBillDate,a.strLicenceCode,a.strPOSItem , a.strItemCode,b.strBrandName ,a.strUserCreated ,a.strRemark,sum(a.intTotalPeg),sum(a.intQty),a.strSourceEntry,a.intBillNo,a.dblTotalAmt   " + " from tblexcisesaledata a ,tblbrandmaster b  where a.strClientCode='" + clientCode + "' and a.strItemCode=b.strBrandCode " + " and a.dteBillDate between '"
				+ fromDate + "' and '" + toDate + "'  ";
		if (!licCode.equalsIgnoreCase("ALL")) {
			sql = sql + "and a.strLicenceCode='" + licCode + "' ";
		}

		if (saleType.equalsIgnoreCase("POS Sale Data")) {
			sql = sql + "and a.strSourceEntry like '%POS Sale%' ";
		} else if (saleType.equalsIgnoreCase("Manual")) {
			sql = sql + "and a.strSourceEntry like '%Manual%' ";
		}

		// System.out.println(sql);
		sql = sql + " group by a.strPOSItem ,a.strSalesCode,a.strItemCode order by a.intBillNo";
		List list = objGlobalFunctionsService.funGetDataList(sql, "sql");
		List listStockAdjFlashModel = new ArrayList();
		double dblPosQty = 0.0;
		double dblPosItemPrice = 0.0;
		for (int cnt = 0; cnt < list.size(); cnt++) {
			Object[] arrObj = (Object[]) list.get(cnt);
			List listStock = new ArrayList();
			listStock.add(arrObj[0]);
			listStock.add(arrObj[11]);
			listStock.add(objGlobal.funGetDate("dd-MM-yyyy", arrObj[1].toString()));
			listStock.add(arrObj[2]);

			if (!"".equals(arrObj[7].toString())) {
				String remark = arrObj[7].toString();
				String[] strposQty = remark.split("posQty:");
				String[] strItemPrice = strposQty[1].split("posItemPrice:");
				dblPosQty = Double.parseDouble(strItemPrice[0]);
				String[] posItemName = strItemPrice[1].split("posItemName:");

				listStock.add(posItemName[1]);// POS Item Name
				listStock.add(strItemPrice[0]);// POS Item Qty
				listStock.add(posItemName[0]);// POS Item Price
				listStock.add(arrObj[5]);// Exise Brand Name
				listStock.add(arrObj[9]);// Excise Qty in Ml
				listStock.add("ml");
				listStock.add(arrObj[12]);
			} else {
				listStock.add("");
				listStock.add("");
				listStock.add("");
				listStock.add(arrObj[5]);
				listStock.add(arrObj[9]);
				listStock.add("ml");
				listStock.add(arrObj[12]);
			}
			listStock.add(arrObj[10]);
			listStock.add(arrObj[6]);
			listStockAdjFlashModel.add(listStock);

		}// posQty:1posItemPrice:550.0posItemName:CHIVAS REGAL 12 YR WHKY
			// (L)-PCL

		return listStockAdjFlashModel;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/openRptExciseStockFlash", method = RequestMethod.GET)
	private void funopenRptExciseStockFlash(HttpServletRequest req, HttpServletResponse resp) {

		String rptSaleID = req.getParameter("rptSaleID").toString();
		req.getSession().removeAttribute("rptSaleID");

		String type = "pdf";
		funCallReportBeanWise(rptSaleID, type, req, resp);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallReportBeanWise(String rptSaleID, String type, HttpServletRequest req, HttpServletResponse resp) {

		objGlobal = new clsGlobalFunctions();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		if (objSetup == null) {
			objSetup = new clsPropertySetupModel();
		}

		try {

			String reportName = servletContext.getRealPath("/WEB-INF/exciseReport/rptExciseStockFlashSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sql = "SELECT a.strSalesCode, a.dteBillDate,a.strLicenceCode,a.strPOSItem, a.strItemCode,b.strBrandName,a.strUserCreated,a.strRemark, " + " sum(a.intTotalPeg), sum(a.intQty),a.strSourceEntry,a.intBillNo,c.strSubCategoryCode,c.strSubCategoryName,d.strCategoryCode,d.strCategoryName,e.strLicenceNo,a.dblTotalAmt "
					+ " FROM tblexcisesaledata a,tblbrandmaster b,tblsubcategorymaster c,tblcategorymaster d ,tbllicencemaster e  WHERE a.strClientCode='" + clientCode + "' " + " AND a.strItemCode=b.strBrandCode and a.strSalesCode='" + rptSaleID + "'  and b.strSubCategoryCode=c.strSubCategoryCode and c.strCategoryCode=d.strCategoryCode and a.strLicenceCode=e.strLicenceCode  "
					+ " group by a.strPOSItem , d.strCategoryCode, c.strSubCategoryCode, a.strPOSItem ,a.strSalesCode,a.strItemCode ";
			// System.out.println(sql);

			List<clsExciseStockFlashBean> listStockBean = new ArrayList();
			List list = objGlobalFunctionsService.funGetDataList(sql, "sql");
			String licenceNo = "";
			String sourceOfSale = "";
			String user = "";
			for (int i = 0; i < list.size(); i++) {

				clsExciseStockFlashBean objBean = new clsExciseStockFlashBean();
				Object[] obj = (Object[]) list.get(i);

				String remark = obj[7].toString();
				if (!"".equals(remark) && remark.contains("posItemName")) {
					String[] strposQty = remark.split("posQty:");
					String[] strItemPrice = strposQty[1].split("posItemPrice:");
					String[] posItemName = strItemPrice[1].split("posItemName:");
					objBean.setPosItemName(posItemName[1]);
					objBean.setPosQty(Integer.parseInt(strItemPrice[0]));
					objBean.setDblPOSProdPrice(Double.parseDouble(posItemName[0]));
				} else {
					objBean.setPosItemName("");
					objBean.setPosQty(0);
					objBean.setDblPOSProdPrice(0.0);
				}

				objBean.setStrSalesCode(obj[0].toString());
				objBean.setDteBillDate(objGlobal.funGetDate("dd-MM-yyyy", obj[1].toString()));
				objBean.setStrLicenceCode(obj[2].toString());
				objBean.setStrPOSItem(obj[3].toString());
				objBean.setStrItemCode(obj[4].toString());
				objBean.setStrBrandName(obj[5].toString());
				objBean.setStrUserCreated(obj[6].toString());
				objBean.setStrRemark(obj[7].toString());
				objBean.setIntTotalPeg(Integer.parseInt(obj[8].toString()));
				objBean.setIntQty(Integer.parseInt(obj[9].toString()));
				objBean.setStrSourceEntry(obj[10].toString());
				objBean.setIntBillNo(obj[11].toString());
				objBean.setStrSubCategoryCode(obj[12].toString());

				objBean.setStrSubcategoryName(obj[13].toString());
				objBean.setStrCategoryCode(obj[14].toString());
				objBean.setStrCategoryName(obj[15].toString());
				objBean.setDblAmount(Double.parseDouble(obj[17].toString()));
				licenceNo = obj[16].toString();
				sourceOfSale = obj[10].toString();
				user = obj[6].toString();
				listStockBean.add(objBean);
			}

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
			hm.put("salesId", rptSaleID);
			hm.put("licenceNo", licenceNo);
			hm.put("sourceOfSale", sourceOfSale);
			hm.put("user", user);
			hm.put("listStockBean", listStockBean);

			// JasperDesign jd = JRXmlLoader.load(reportName);
			// JasperReport jr = JasperCompileManager.compileReport(jd);
			// JRBeanCollectionDataSource beanCollectionDataSource = new
			// JRBeanCollectionDataSource(listStockBean);
			// JasperPrint print = JasperFillManager.fillReport(jr, hm,
			// beanCollectionDataSource);
			// List<JasperPrint> jprintlist =new ArrayList<JasperPrint>();
			// jprintlist.add(print);

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			JasperPrint jp = JasperFillManager.fillReport(jr, hm, new JREmptyDataSource());
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			jprintlist.add(jp);
			if (jprintlist.size() > 0) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				if (type.trim().equalsIgnoreCase("pdf")) {
					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=" + "rptExciseStockFlashSlip." + type.trim());
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				} else {
					JRExporter exporter = new JRXlsExporter();
					resp.setContentType("application/xlsx");
					exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "attachment;filename=" + "rptExciseStockFlashSlip." + type.trim());
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				}
			} else {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				try {
					resp.getWriter().append("No Record Found");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
