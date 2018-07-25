package com.sanguine.crm.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mysql.jdbc.Connection;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.crm.bean.clsShopOrderBean;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsGroupMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.util.clsReportBean;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Controller
public class clsShopOrderListReportController {

	@Autowired
	private clsSetupMasterService objSetupMasterService;
	@Autowired
	private ServletContext servletContext;
	private clsGlobalFunctions objGlobal = null;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	private clsGroupMasterService objGrpMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@RequestMapping(value = "/rptShopOrderList", method = RequestMethod.POST)
	private void funShopOrderList(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		// there two Frmate aviable for shop order list 1st without table wise
		// and 2nd table wise which contain two table

		// frmCallShopOrderListWithoutTablewise(objBean, resp, req);
		frmCallShopOrderListTablewise(objBean, resp, req);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void frmCallShopOrderListTablewise(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		try {

			Connection con = objGlobalFunctions.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String suppName = "";

			String propNameSql = "select a.strPropertyName  from dbwebmms.tblpropertymaster a where a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' ";
			List listPropName = objGlobalFunctionsService.funGetDataList(propNameSql, "sql");
			String propName = "";
			if (listPropName.size() > 0) {
				propName = listPropName.get(0).toString();
			}

			// Customer code
			String strCustCodes = objBean.getStrSuppCode();
			String tempstrCCode[] = strCustCodes.split(",");
			String strCCodes = "(";
			for (int i = 0; i < tempstrCCode.length; i++) {
				if (i == 0) {
					strCCodes = strCCodes + "'" + tempstrCCode[i] + "'";
				} else {
					strCCodes = strCCodes + ",'" + tempstrCCode[i] + "'";
				}
			}
			strCCodes += ")";

			String fromDate = objBean.getDtFromDate();
			String toDate = objBean.getDtToDate();

			String fd = fromDate.split("-")[0];
			String fm = fromDate.split("-")[1];
			String fy = fromDate.split("-")[2];

			String td = toDate.split("-")[0];
			String tm = toDate.split("-")[1];
			String ty = toDate.split("-")[2];

			String dteFromDate = fy + "-" + fm + "-" + fd;
			String dteToDate = ty + "-" + tm + "-" + td;

			String fromFulDate = objBean.getDteFromFulfillment();
			String toFulDate = objBean.getDteToFulfillment();

			String ffd = fromFulDate.split("-")[0];
			String ffm = fromFulDate.split("-")[1];
			String ffy = fromFulDate.split("-")[2];

			String tfd = toFulDate.split("-")[0];
			String tfm = toFulDate.split("-")[1];
			String tfy = toFulDate.split("-")[2];

			String dteFromFulDate = ffy + "-" + ffm + "-" + ffd;
			String dteToFulDate = tfy + "-" + tfm + "-" + tfd;

			String allGroupSql = "select a.strGCode  from tblgroupmaster a where  a.strClientCode='" + clientCode + "' ";
			List listAllGroupCode = objGlobalFunctionsService.funGetDataList(allGroupSql, "sql");
			String strGCodes = "(";
			for (int g = 0; g < listAllGroupCode.size(); g++) {
				if (g == 0) {
					strGCodes = strGCodes + "'" + listAllGroupCode.get(g) + "'";
				} else {
					strGCodes = strGCodes + ",'" + listAllGroupCode.get(g) + "'";
				}
			}
			strGCodes += ")";

			/*
			 * @ listCustSubgroup is used for only those customer which is for
			 * customer only that have actual order this also for new page new
			 * customer order print
			 */

			String sqlSubGroup = " select  a.strCustCode from tblsalesorderhd a,tblsalesorderdtl b,tblsubgroupmaster c, " + " tblproductmaster d , tblpartymaster e ,tblgroupmaster f " + " where a.strSOCode=b.strSOCode  and b.strProdCode=d.strProdCode  " + "and d.strSGCode=c.strSGCode  and a.strCustCode=e.strPCode " + "and c.strGCode=f.strGCode  and a.strCustCode IN " + strCCodes + " "
					+ " and date(a.dteSODate)  between '" + dteFromDate + "' and '" + dteToDate + "' " + " and date(a.dteFulmtDate)  between '" + dteFromFulDate + "' and '" + dteToFulDate + "' " + " group by a.strCustCode " + " ORDER BY e.strPName  ";

			List listCustSubgroup = objGlobalFunctionsService.funGetDataList(sqlSubGroup, "sql");

			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();

			for (int cnt = 0; cnt < listCustSubgroup.size(); cnt++) {
				String strCustCode = listCustSubgroup.get(cnt).toString();

				String sqlGroup = " select  c.strGCode from tblsalesorderhd a,tblsalesorderdtl b,tblsubgroupmaster c, " + " tblproductmaster d , tblpartymaster e ,tblgroupmaster f " + " where a.strSOCode=b.strSOCode  and b.strProdCode=d.strProdCode  " + " and d.strSGCode=c.strSGCode  and a.strCustCode=e.strPCode " + " and c.strGCode=f.strGCode  and a.strCustCode='" + strCustCode + "' "
						+ " and c.strGCode IN  " + strGCodes + "  " + " and date(a.dteSODate)  between '" + dteFromDate + "' and '" + dteToDate + "' " + " and date(a.dteFulmtDate)  between '" + dteFromFulDate + "' and '" + dteToFulDate + "' " + " group by c.strGCode " + " ORDER BY c.strGCode ";

				List listGroup = objGlobalFunctionsService.funGetDataList(sqlGroup, "sql");

				for (int i = 0; i < listGroup.size(); i++) {
					String strGCode = listGroup.get(i).toString();
					JasperPrint jp = funCallReportShopOrderList(strCustCode, strGCode, objBean, resp, req);
					jprintlist.add(jp);
				}
			}

			if (jprintlist.size() > 0) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				if (objBean.getStrDocType().equals("PDF")) {
					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=ShopOrderListTableWise_" + dteFromFulDate + "_To_" + dteToFulDate + "_" + userCode + ".pdf");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				} else {
					JRExporter exporter = new JRXlsExporter();
					resp.setContentType("application/xlsx");
					exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=ShopOrderListTableWise_" + dteFromFulDate + "_To_" + dteToFulDate + "_" + userCode + ".xls");
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

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused", "finally" })
	private JasperPrint funCallReportShopOrderList(String strCustName, String strGCode, clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) throws SQLException {

		JasperPrint jp = null;
		try {

			String type = objBean.getStrDocType();

			Connection con = objGlobalFunctions.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String suppName = "";

			// String reportName =
			// servletContext.getRealPath("/WEB-INF/reports/webcrm/rptShopOrderList.jrxml");
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptShopOrderListTableWise.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String propNameSql = "select a.strPropertyName  from dbwebmms.tblpropertymaster a where a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' ";
			List listPropName = objGlobalFunctionsService.funGetDataList(propNameSql, "sql");
			String propName = "";
			if (listPropName.size() > 0) {
				propName = listPropName.get(0).toString();
			}

			ArrayList fieldList = new ArrayList();

			String sqlEvnQuery = " select  e.strPName,f.strGName,c.strSGName,d.strProdName,sum(b.dblQty) as dblQty  ,sum(b.dblAcceptQty) as dblAcceptQty ," + " a.strSOCode,a.strCustCode,c.intSortingNo " + " from tblsalesorderhd a,tblsalesorderdtl b,tblsubgroupmaster c, tblproductmaster d ," + " tblpartymaster e ,tblgroupmaster f  " + " where a.strSOCode=b.strSOCode "
					+ " and b.strProdCode=d.strProdCode " + " and d.strSGCode=c.strSGCode " + " and a.strCustCode=e.strPCode " + " and c.strGCode=f.strGCode " + " and a.strCustCode = '" + strCustName + "' and c.strGCode='" + strGCode + "' ";

			String fromDate = objBean.getDtFromDate();
			String toDate = objBean.getDtToDate();

			String fd = fromDate.split("-")[0];
			String fm = fromDate.split("-")[1];
			String fy = fromDate.split("-")[2];

			String td = toDate.split("-")[0];
			String tm = toDate.split("-")[1];
			String ty = toDate.split("-")[2];

			String dteFromDate = fy + "-" + fm + "-" + fd;
			String dteToDate = ty + "-" + tm + "-" + td;

			String fromFulDate = objBean.getDteFromFulfillment();
			String toFulDate = objBean.getDteToFulfillment();

			String ffd = fromFulDate.split("-")[0];
			String ffm = fromFulDate.split("-")[1];
			String ffy = fromFulDate.split("-")[2];

			String tfd = toFulDate.split("-")[0];
			String tfm = toFulDate.split("-")[1];
			String tfy = toFulDate.split("-")[2];

			String dteFromFulDate = ffy + "-" + ffm + "-" + ffd;
			String dteToFulDate = tfy + "-" + tfm + "-" + tfd;

			HashMap<String, String> hmCustWiseSub = new HashMap<String, String>();

			sqlEvnQuery = sqlEvnQuery + " and date(a.dteSODate) between '" + dteFromDate + "' and '" + dteToDate + "' " + " and date(a.dteFulmtDate) between '" + dteFromFulDate + "' and '" + dteToFulDate + "' " + " group by b.strProdCode,d.strSGCode,f.strGCode,a.strCustCode  " + " ORDER BY e.strPName,c.intSortingNo,d.strProdName   ";

			/*
			 * @ listEvenProdDtl is for main product data
			 */

			List listEvenProdDtl = objGlobalFunctionsService.funGetDataList(sqlEvnQuery, "sql");

			String sqlSubGroup = " select  e.strPName,c.strSGName from tblsalesorderhd a,tblsalesorderdtl b,tblsubgroupmaster c, " + " tblproductmaster d , tblpartymaster e ,tblgroupmaster f " + " where a.strSOCode=b.strSOCode  and b.strProdCode=d.strProdCode  " + "and d.strSGCode=c.strSGCode  and a.strCustCode=e.strPCode " + "and c.strGCode=f.strGCode and a.strCustCode  =  '" + strCustName
					+ "' and c.strGCode='" + strGCode + "' " + " and date(a.dteSODate) " + " between '" + dteFromDate + "' and '" + dteToDate + "' " + " and date(a.dteFulmtDate) between '" + dteFromFulDate + "' and '" + dteToFulDate + "' " + " group by d.strSGCode,a.strCustCode " + " ORDER BY e.strPName,c.intSortingNo,d.strProdName   ";

			List listCustSubgroup = objGlobalFunctionsService.funGetDataList(sqlSubGroup, "sql");
			Map<String, List<String>> hmCustSG = new HashMap<String, List<String>>();
			// Map<String,String> hmCustSG=new HashMap<String,String>();

			Set<String> setCust = new HashSet<String>();
			Set<String> seSubGroupName = new HashSet<String>();

			/*
			 * @ setCust is for only customer
			 */

			for (int i = 0; i < listCustSubgroup.size(); i++) {
				Object[] obj = (Object[]) listCustSubgroup.get(i);
				setCust.add(obj[0].toString());

			}

			/*
			 * @ hmCustSG is for customer as key and list as of that customer
			 * sub group
			 * 
			 * @ list contain Subgroup and # seprate count
			 */

			Iterator iterator = setCust.iterator();
			while (iterator.hasNext()) {
				List<String> listSG = new ArrayList<String>();
				String cust = iterator.next().toString();
				for (int i = 0; i < listCustSubgroup.size(); i++) {
					Object[] obj = (Object[]) listCustSubgroup.get(i);

					if (cust.equals(obj[0].toString())) {
						listSG.add(obj[1].toString() + "#" + (i + 1));
					}

				}
				hmCustSG.put(cust, listSG);

			}

			/*
			 * main data set in list of clsShopOrderBean class bean
			 */

			List<clsShopOrderBean> listEvnBean = new ArrayList<clsShopOrderBean>();
			int srNo = 1;
			for (int i = 0; i < listEvenProdDtl.size(); i++) {
				Object[] obj = (Object[]) listEvenProdDtl.get(i);
				String custName = obj[0].toString();
				String sgName = obj[2].toString();

				clsShopOrderBean objShopBean = new clsShopOrderBean();
				objShopBean.setStrPName(obj[0].toString());
				objShopBean.setStrGName(obj[1].toString());
				objShopBean.setStrSGName(obj[2].toString());
				objShopBean.setStrProdName(obj[3].toString());
				objShopBean.setDblRequiredQty(Double.parseDouble(obj[4].toString()));
				objShopBean.setIntSortingNo(Integer.parseInt(obj[8].toString()));

				// for Even odd half half Print logic only Start
				objShopBean.setIntSrNo(srNo);
				srNo++;
				// End//

				listEvnBean.add(objShopBean);
			}

			List objEven = new ArrayList<clsShopOrderBean>();
			List objOdd = new ArrayList<clsShopOrderBean>();
			// ListIterator<clsShopOrderBean> lisIte =
			// listEvnBean.listIterator();

			/*
			 * iterrating hmCustSG map according to cust and it's list and set
			 * Sub group wise Count of each subgroup of that hashmap for Even
			 * odd print in jasper table one is for even and second is for Odd
			 * add List objEven for even subgroup and odd is for
			 */

			/*
			 * This is for full Subgroup Product Print Even odd SubGroup wise
			 * for (Map.Entry<String, List<String>> entry : hmCustSG.entrySet())
			 * { for(clsShopOrderBean shopITdtl : listEvnBean)
			 * //while(lisIte.hasNext())
			 * 
			 * { //clsShopOrderBean shopITdtl = lisIte.next();
			 * System.out.println("SGNameMain=="+shopITdtl.getStrSGName());
			 * if(entry.getKey().equals(shopITdtl.getStrPName())) {
			 * 
			 * List<String> listKeyVal =entry.getValue();
			 * 
			 * for(String strSGName : listKeyVal) { String
			 * strSubGName=strSGName.split("#")[0];
			 * if(strSubGName.equals(shopITdtl.getStrSGName())) {
			 * 
			 * int counter=Integer.parseInt(strSGName.split("#")[1]);
			 * shopITdtl.setCount(counter); if(shopITdtl.getIntSortingNo()%2==0)
			 * { System.out.println("MapList SGName=="+strSGName);
			 * objEven.add(shopITdtl); } else {
			 * System.out.println("MapList SGName=="+strSGName);
			 * objOdd.add(shopITdtl); } } }
			 * 
			 * 
			 * }
			 * 
			 * 
			 * }
			 * 
			 * }
			 * 
			 * 
			 * 
			 * Collections.sort(objEven,
			 * clsShopOrderBean.intSortingNoComparator);
			 * ListIterator<clsShopOrderBean> lisIteEven =
			 * objEven.listIterator(); int srno=1; while(lisIteEven.hasNext()) {
			 * clsShopOrderBean shopITdtl = lisIteEven.next();
			 * shopITdtl.setIntSrNo(srno); srno++;
			 * 
			 * } Collections.sort(objOdd,
			 * clsShopOrderBean.intSortingNoComparator);
			 * ListIterator<clsShopOrderBean> lisIteOdd = objOdd.listIterator();
			 * 
			 * while(lisIteOdd.hasNext()) { clsShopOrderBean shopITdtl =
			 * lisIteOdd.next(); shopITdtl.setIntSrNo(srno); srno++; }
			 */
			// for Even odd half half Print logic only Start

			for (clsShopOrderBean shopITdtl : listEvnBean) {
				if (shopITdtl.getIntSrNo() % 2 == 0) {
					objEven.add(shopITdtl);
				} else {
					objOdd.add(shopITdtl);
				}
			}
			// End ///

			System.out.println(objEven);
			System.out.println(objOdd);

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
			hm.put("dteFromDate", dteFromFulDate);
			hm.put("dteToDate", dteToFulDate);
			hm.put("objEven", objEven);
			hm.put("objOdd", objOdd);
			// hm.put("strCustName", strCustName);

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			jp = JasperFillManager.fillReport(jr, hm, new JREmptyDataSource());

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return jp;
		}

	}

	// //////////////////////////shop order list for all Customer old table
	// Logic Start //////////////

	// @SuppressWarnings({ "unchecked", "rawtypes" })
	// private void frmCallShopOrderList(clsReportBean objBean,
	// HttpServletResponse resp, HttpServletRequest req)
	// {
	//
	//
	// String type = objBean.getStrDocType();
	//
	// Connection con = objGlobalFunctions.funGetConnection(req);
	// String clientCode =
	// req.getSession().getAttribute("clientCode").toString();
	// String companyName =
	// req.getSession().getAttribute("companyName").toString();
	// String userCode = req.getSession().getAttribute("usercode").toString();
	// String propertyCode =
	// req.getSession().getAttribute("propertyCode").toString();
	// clsPropertySetupModel objSetup =
	// objSetupMasterService.funGetObjectPropertySetup(propertyCode,
	// clientCode);
	// if (objSetup == null)
	// {
	// objSetup = new clsPropertySetupModel();
	// }
	// String suppName = "";
	//
	// // String reportName =
	// servletContext.getRealPath("/WEB-INF/reports/webcrm/rptShopOrderList.jrxml");
	// String reportName =
	// servletContext.getRealPath("/WEB-INF/reports/webcrm/rptShopOrderListTableWise.jrxml");
	// String imagePath =
	// servletContext.getRealPath("/resources/images/company_Logo.png");
	//
	// String
	// propNameSql="select a.strPropertyName  from dbwebmms.tblpropertymaster a where a.strPropertyCode='"+propertyCode+"' and a.strClientCode='"+clientCode+"' ";
	// List listPropName =
	// objGlobalFunctionsService.funGetDataList(propNameSql,"sql");
	// String propName="";
	// if(listPropName.size()>0)
	// {
	// propName=listPropName.get(0).toString();
	// }
	//
	// // Customer code
	// String strCustCodes = objBean.getStrSuppCode();
	// String tempstrCCode[] = strCustCodes.split(",");
	// String strCCodes = "(";
	// for (int i = 0; i < tempstrCCode.length; i++)
	// {
	// if (i == 0)
	// {
	// strCCodes = strCCodes + "'" + tempstrCCode[i] + "'";
	// }
	// else
	// {
	// strCCodes = strCCodes + ",'" + tempstrCCode[i] + "'";
	// }
	// }
	// strCCodes += ")";
	//
	//
	//
	//
	//
	// ArrayList fieldList=new ArrayList();
	//
	//
	// String sqlEvnQuery =
	// " select  e.strPName,f.strGName,c.strSGName,d.strProdName,sum(b.dblQty) as dblQty  ,sum(b.dblAcceptQty) as dblAcceptQty ,"
	// + " a.strSOCode,a.strCustCode "
	// +
	// " from tblsalesorderhd a,tblsalesorderdtl b,tblsubgroupmaster c, tblproductmaster d ,"
	// + " tblpartymaster e ,tblgroupmaster f  "
	// + " where a.strSOCode=b.strSOCode "
	// + " and b.strProdCode=d.strProdCode "
	// + " and d.strSGCode=c.strSGCode "
	// + " and a.strCustCode=e.strPCode "
	// + " and c.strGCode=f.strGCode "
	// + " and a.strCustCode IN " + strCCodes + " ";
	//
	//
	//
	// String fromDate = objBean.getDtFromDate();
	// String toDate = objBean.getDtToDate();
	//
	// String fd = fromDate.split("-")[0];
	// String fm = fromDate.split("-")[1];
	// String fy = fromDate.split("-")[2];
	//
	// String td = toDate.split("-")[0];
	// String tm = toDate.split("-")[1];
	// String ty = toDate.split("-")[2];
	//
	// String dteFromDate = fy + "-" + fm + "-" + fd;
	// String dteToDate = ty + "-" + tm + "-" + td;
	//
	// HashMap <String,String> hmCustWiseSub=new HashMap<String,String>();
	//
	// sqlEvnQuery = sqlEvnQuery + "and date(a.dteSODate) between '" +
	// dteFromDate+ "' and '" + dteToDate + "' "
	// + " group by b.strProdCode,d.strSGCode,f.strGCode,a.strCustCode  "
	// + " ORDER BY e.strPName,f.strGName,c.strSGName,d.strProdName   ";
	//
	//
	// List listEvenProdDtl =
	// objGlobalFunctionsService.funGetDataList(sqlEvnQuery,"sql");
	//
	// String
	// sqlSubGroup=" select  e.strPName,c.strSGName from tblsalesorderhd a,tblsalesorderdtl b,tblsubgroupmaster c, "
	// + " tblproductmaster d , tblpartymaster e ,tblgroupmaster f "
	// + " where a.strSOCode=b.strSOCode  and b.strProdCode=d.strProdCode  "
	// + "and d.strSGCode=c.strSGCode  and a.strCustCode=e.strPCode "
	// + "and c.strGCode=f.strGCode and a.strCustCode  IN  "+strCCodes+" "
	// + " and date(a.dteSODate) "
	// + " between '" + dteFromDate+ "' and '" + dteToDate + "' "
	// + " group by d.strSGCode,a.strCustCode "
	// + " ORDER BY e.strPName,c.strSGName,d.strProdName   ";
	//
	// List listCustSubgroup =
	// objGlobalFunctionsService.funGetDataList(sqlSubGroup,"sql");
	// Map<String,List<String>> hmCustSG=new HashMap<String,List<String>>();
	// // Map<String,String> hmCustSG=new HashMap<String,String>();
	//
	// Set<String> setCust =new HashSet<String>();
	// Set<String> seSubGroupName =new HashSet<String>();
	//
	//
	//
	// for(int i=0;i<listCustSubgroup.size();i++)
	// {
	// Object[] obj = (Object[]) listCustSubgroup.get(i);
	// setCust.add(obj[0].toString());
	//
	// }
	//
	//
	// Iterator iterator = setCust.iterator();
	// while(iterator.hasNext())
	// {
	// List<String> listSG =new ArrayList<String>();
	// String cust=iterator.next().toString();
	// for(int i=0;i<listCustSubgroup.size();i++)
	// {
	// Object[] obj = (Object[]) listCustSubgroup.get(i);
	//
	// if(cust.equals(obj[0].toString()))
	// {
	// listSG.add(obj[1].toString()+"#"+(i+1));
	// }
	//
	// }
	// hmCustSG.put(cust, listSG);
	//
	// }
	//
	// List <clsShopOrderBean> listEvnBean =new ArrayList<clsShopOrderBean>();
	// for(int i=0;i<listEvenProdDtl.size();i++)
	// {
	// Object[] obj = (Object[]) listEvenProdDtl.get(i);
	// String custName=obj[0].toString();
	// String sgName= obj[2].toString();
	//
	// clsShopOrderBean objShopBean = new clsShopOrderBean();
	// objShopBean.setStrPName(obj[0].toString());
	// objShopBean.setStrGName(obj[1].toString());
	// objShopBean.setStrSGName(obj[2].toString());
	// objShopBean.setStrProdName(obj[3].toString());
	// objShopBean.setDblRequiredQty(Double.parseDouble(obj[4].toString()));
	// listEvnBean.add(objShopBean);
	// }
	//
	// List objEven= new ArrayList<clsShopOrderBean>();
	// List objOdd= new ArrayList<clsShopOrderBean>();
	// ListIterator<clsShopOrderBean> lisIte = listEvnBean.listIterator();
	//
	// for (Map.Entry<String, List<String>> entry : hmCustSG.entrySet())
	// {
	// for(clsShopOrderBean shopITdtl : listEvnBean)
	// //while(lisIte.hasNext())
	//
	// {
	// //clsShopOrderBean shopITdtl = lisIte.next();
	// System.out.println("SGNameMain=="+shopITdtl.getStrSGName());
	// if(entry.getKey().equals(shopITdtl.getStrPName()))
	// {
	//
	// List<String> listKeyVal =entry.getValue();
	//
	// for(String strSGName : listKeyVal)
	// {
	// String strSubGName=strSGName.split("#")[0];
	// if(strSubGName.equals(shopITdtl.getStrSGName()))
	// {
	//
	// int counter=Integer.parseInt(strSGName.split("#")[1]);
	// shopITdtl.setCount(counter);
	// if(counter%2==0)
	// {
	// System.out.println("MapList SGName=="+strSGName);
	// objEven.add(shopITdtl);
	// }
	// else
	// {
	// System.out.println("MapList SGName=="+strSGName);
	// objOdd.add(shopITdtl);
	// }
	// }
	// }
	//
	//
	// }
	//
	//
	// }
	//
	// }
	//
	// System.out.println(objEven);
	// System.out.println(objOdd);
	//
	//
	//
	//
	// try{
	//
	//
	//
	//
	// HashMap hm = new HashMap();
	// hm.put("strCompanyName", companyName);
	// hm.put("strUserCode", userCode);
	// hm.put("strImagePath", imagePath);
	// hm.put("strAddr1", objSetup.getStrAdd1());
	// hm.put("strAddr2", objSetup.getStrAdd2());
	// hm.put("strCity", objSetup.getStrCity());
	// hm.put("strState", objSetup.getStrState());
	// hm.put("strCountry", objSetup.getStrCountry());
	// hm.put("strPin", objSetup.getStrPin());
	// hm.put("dteFromDate", dteFromDate);
	// hm.put("dteToDate", dteToDate);
	// hm.put("objEven", objEven);
	// hm.put("objOdd", objOdd);
	//
	// JasperDesign jd = JRXmlLoader.load(reportName);
	// JasperReport jr = JasperCompileManager.compileReport(jd);
	// List<JasperPrint> jprintlist =new ArrayList<JasperPrint>();
	// JasperPrint jp = JasperFillManager.fillReport(jr, hm, new
	// JREmptyDataSource());
	// JRDataSource beanCollectionDataSource = new
	// JRBeanCollectionDataSource(fieldList);
	// jprintlist.add(jp);
	//
	//
	// ServletOutputStream servletOutputStream = resp.getOutputStream();
	// JRExporter exporter = new JRPdfExporter();
	// resp.setContentType("application/pdf");
	// exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST,
	// jprintlist);
	// exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM,
	// servletOutputStream);
	// exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS,
	// Boolean.TRUE);
	// resp.setHeader("Content-Disposition",
	// "inline;filename=ShopOrderList_"+dteFromFulDate+"_To_"+dteToFulDate+"_"+userCode+".pdf");
	// exporter.exportReport();
	// servletOutputStream.flush();
	// servletOutputStream.close();
	//
	//
	// }
	// catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	//
	//
	// }

	// //////////////////////////shop order list for all Customer old table
	// Logic End //////////////

	// //////////////////// shop Order list Report without table wise Start
	// //////////////////////////

	private void frmCallShopOrderListWithoutTablewise(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		String type = objBean.getStrDocType();

		Connection con = objGlobalFunctions.funGetConnection(req);
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		if (objSetup == null) {
			objSetup = new clsPropertySetupModel();
		}
		String suppName = "";

		String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptShopOrderList.jrxml");
		String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

		String propNameSql = "select a.strPropertyName  from dbwebmms.tblpropertymaster a where a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' ";
		List listPropName = objGlobalFunctionsService.funGetDataList(propNameSql, "sql");
		String propName = "";
		if (listPropName.size() > 0) {
			propName = listPropName.get(0).toString();
		}

		// Customer code
		String strCustCodes = objBean.getStrSuppCode();
		String tempstrCCode[] = strCustCodes.split(",");
		String strCCodes = "(";
		for (int i = 0; i < tempstrCCode.length; i++) {
			if (i == 0) {
				strCCodes = strCCodes + "'" + tempstrCCode[i] + "'";
			} else {
				strCCodes = strCCodes + ",'" + tempstrCCode[i] + "'";
			}
		}
		strCCodes += ")";

		ArrayList fieldList = new ArrayList();

		String sqlQuery = " select e.strPName,f.strGName,c.strSGName,d.strProdName,sum(b.dblQty) as dblQty  ,sum(b.dblAcceptQty) as dblAcceptQty ," + " a.strSOCode,a.strCustCode " + " from tblsalesorderhd a,tblsalesorderdtl b,tblsubgroupmaster c, tblproductmaster d ," + " tblpartymaster e ,tblgroupmaster f " + " where a.strSOCode=b.strSOCode " + " and b.strProdCode=d.strProdCode "
				+ " and d.strSGCode=c.strSGCode " + " and a.strCustCode=e.strPCode " + " and c.strGCode=f.strGCode " + " and a.strCustCode IN " + strCCodes + " ";

		String fromDate = objBean.getDtFromDate();
		String toDate = objBean.getDtToDate();

		String fd = fromDate.split("-")[0];
		String fm = fromDate.split("-")[1];
		String fy = fromDate.split("-")[2];

		String td = toDate.split("-")[0];
		String tm = toDate.split("-")[1];
		String ty = toDate.split("-")[2];

		String dteFromDate = fy + "-" + fm + "-" + fd;
		String dteToDate = ty + "-" + tm + "-" + td;

		String fromFulDate = objBean.getDteFromFulfillment();
		String toFulDate = objBean.getDteToFulfillment();

		String ffd = fromFulDate.split("-")[0];
		String ffm = fromFulDate.split("-")[1];
		String ffy = fromFulDate.split("-")[2];

		String tfd = toFulDate.split("-")[0];
		String tfm = toFulDate.split("-")[1];
		String tfy = toFulDate.split("-")[2];

		String dteFromFulDate = ffy + "-" + ffm + "-" + ffd;
		String dteToFulDate = tfy + "-" + tfm + "-" + tfd;

		sqlQuery = sqlQuery + " and date(a.dteSODate) between '" + dteFromDate + "' and '" + dteToDate + "' " + " and date(a.dteFulmtDate) between '" + dteFromFulDate + "' and '" + dteToFulDate + "' " + " group by b.strProdCode,d.strSGCode,f.strGCode,a.strCustCode  ORDER BY e.strPName,f.strGName,c.strSGName,d.strProdName   ";

		List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");

		try {
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlQuery);
			jd.setQuery(newQuery);
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
			hm.put("dteFromDate", dteFromDate);
			hm.put("dteToDate", dteToDate);

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
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptSOVarianceList." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ////////////////////shop Order list Report without table wise End
	// //////////////////////////

	//
	// public class clsShopOrderBean
	// {
	// private String strPName;
	// private String strGName;
	// private String strSGName;
	// private String strProdName;
	// private double dblRequiredQty;
	// private int count;
	// private int intSrNo;
	// private int intSortingNo;
	//
	// public String getStrPName() {
	// return strPName;
	// }
	// public void setStrPName(String strPName) {
	// this.strPName = strPName;
	// }
	// public String getStrGName() {
	// return strGName;
	// }
	// public void setStrGName(String strGName) {
	// this.strGName = strGName;
	// }
	// public String getStrSGName() {
	// return strSGName;
	// }
	// public void setStrSGName(String strSGName) {
	// this.strSGName = strSGName;
	// }
	// public String getStrProdName() {
	// return strProdName;
	// }
	// public void setStrProdName(String strProdName) {
	// this.strProdName = strProdName;
	// }
	// public double getDblRequiredQty() {
	// return dblRequiredQty;
	// }
	// public void setDblRequiredQty(double dblRequiredQty) {
	// this.dblRequiredQty = dblRequiredQty;
	// }
	// public int getCount() {
	// return count;
	// }
	// public void setCount(int count) {
	// this.count = count;
	// }
	// public int getIntSrNo() {
	// return intSrNo;
	// }
	// public void setIntSrNo(int intSrNo) {
	// this.intSrNo = intSrNo;
	// }
	// public int getIntSortingNo() {
	// return intSortingNo;
	// }
	// public void setIntSortingNo(int intSortingNo) {
	// this.intSortingNo = intSortingNo;
	// }
	//
	//
	// }

}
