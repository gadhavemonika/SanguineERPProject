package com.sanguine.crm.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.sanguine.bean.clsAttachDocBean;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.crm.bean.clsProductionComPilationBean;
import com.sanguine.crm.service.clsAdvOrderReportService;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsAdvOrderReportController {

	@Autowired
	private clsSetupMasterService objSetupMasterService;
	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private clsAdvOrderReportService objAdvOrderService;

	@RequestMapping(value = "/frmAdvanceOrderReports", method = RequestMethod.GET)
	public ModelAndView funOpenAdvanceOrderReport(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmAdvanceOrderReports_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmAdvanceOrderReports", "command", new clsReportBean());
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/rptAdvOrderReport", method = RequestMethod.POST)
	public void funCallAdvOrderReport(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		Connection con = null;

		try {
			con = objGlobalFunctions.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			ArrayList fieldList = new ArrayList();
			ArrayList finalList = new ArrayList();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = "";

			if (objBean.getStrExportType().equalsIgnoreCase("No")) {
				reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptAdvanceOrderReport.jrxml");
			} else {
				reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptAdvanceOrderImageReport.jrxml");
			}
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			String fromDate = objBean.getDteFromDate();
			String toDate = objBean.getDteToDate();
			String fd = fromDate.split("-")[0];
			String fm = fromDate.split("-")[1];
			String fy = fromDate.split("-")[2];

			String td = toDate.split("-")[0];
			String tm = toDate.split("-")[1];
			String ty = toDate.split("-")[2];

			String dteFromDate = fy + "-" + fm + "-" + fd;
			String dteToDate = ty + "-" + tm + "-" + td;

			String orderType = objBean.getStrReportType();

			// group code
			String tempstrGCode[] = objBean.getStrGCode().toString().split(",");
			String strGCodes = "(";
			for (int i = 0; i < tempstrGCode.length; i++) {
				if (i == 0) {
					strGCodes = strGCodes + "'" + tempstrGCode[i] + "'";
				} else {
					strGCodes = strGCodes + ",'" + tempstrGCode[i] + "'";
				}
			}
			strGCodes += ")";

			// subgroup code
			String tempstrSGCode[] = objBean.getStrSGCode().split(",");
			String strSGCodes = "(";
			for (int i = 0; i < tempstrSGCode.length; i++) {
				if (i == 0) {
					strSGCodes = strSGCodes + "'" + tempstrSGCode[i] + "'";
				} else {
					strSGCodes = strSGCodes + ",'" + tempstrSGCode[i] + "'";
				}
			}
			strSGCodes += ")";

			String sqlAdvOrd = "";

			if (objBean.getStrExportType().equalsIgnoreCase("Yes")) {

				// sqlAdvOrd="SELECT d.strSGName,c.strProdCode,c.strProdName, SUM(b.dblQty)as dblQty, "
				// +" SUM(b.dblWeight*b.dblQty)as dblWeight ,a.strSOCode,f.strCharCode,f.strCharValue,g.strPName,DATE_FORMAT(a.dteSODate,'%d-%m-%Y'),b.strRemarks,h.strCharName "
				// +" FROM tblsalesorderhd a,tblsalesorderdtl b,tblproductmaster c,tblsubgroupmaster d,tblgroupmaster e, "
				// +" tblsaleschar f ,tblpartymaster g,tblcharacteristics h, tblattachdocument l WHERE a.strSOCode=b.strSOCode and l.strCode=b.strSOCode and a.strSOCode=f.strSOCode and  "
				// +" b.strProdCode=f.strProdCode and a.strStatus='"+orderType+"' AND  "
				// +" b.strProdCode=c.strProdCode AND c.strSGCode=d.strSGCode AND d.strGCode=e.strGCode and a.strCustCode=g.strPCode "
				// +" AND e.strGCode IN "+strGCodes+" AND c.strSGCode IN "+strSGCodes+"   AND "
				// +" DATE(a.dteFulmtDate) BETWEEN '"+dteFromDate+"' AND '"+dteToDate+"' and f.strCharCode=h.strCharCode GROUP BY f.strCharCode, b.strProdCode,e.strGName,d.strSGName, g.strPName "
				// +" ORDER BY  g.strPName,b.strProdCode,e.strGName,d.strSGName,f.strCharCode  ";

				// sqlAdvOrd
				// ="select i.strSGName,   g.strProdCode,g.strProdName,SUM(b.dblQty) AS dblQty, SUM(b.dblWeight*b.dblQty) AS dblWeight,b.strSOCode,c.strCharCode, "
				// +" c.strCharValue,e.strPName, DATE_FORMAT(a.dteSODate,'%d-%m-%Y'),b.strRemarks,d.strCharName,IfNull(g.strPartNo,'') from tblsalesorderhd a ,tblsalesorderdtl b ,tblsaleschar c,tblcharacteristics d ,tblpartymaster e,tblattachdocument f,tblproductmaster g,tblgroupmaster h,tblsubgroupmaster i "
				// +" where a.strSOCode=b.strSOCode  and c.strSOCode=a.strSOCode  and c.strSOCode=a.strSOCode and b.strProdCode=g.strProdCode and c.strCharCode=d.strCharCode "
				// +" and a.strCustCode=e.strPCode  and g.strSGCode=i.strSGCode AND h.strGCode IN  "+strGCodes+" "
				// +" AND i.strSGCode IN "+strSGCodes+" and h.strGCode=i.strGCode and f.strCode=a.strSOCode and f.strActualFileName=g.strPartNo "
				// +" and a.strStatus='Advance Order' AND DATE(a.dteFulmtDate) BETWEEN '"+dteFromDate+"' AND '"+dteToDate+"' "
				// +" GROUP BY c.strCharCode, b.strProdCode,h.strGName,i.strSGName, e.strPName "
				// +" ORDER BY e.strPName,b.strProdCode,h.strGName,i.strSGName,c.strCharCode"
				// ;

				// sqlAdvOrd =
				// " select i.strSGName,   g.strProdCode,g.strProdName,b.dblQty AS dblQty, b.dblWeight AS dblWeight,b.strSOCode,c.strCharCode, "
				// +
				// " c.strCharValue,e.strPName,  DATE_FORMAT(a.dteSODate,'%d-%m-%Y'),b.strRemarks,d.strCharName,IfNull(g.strPartNo,'') "
				// + " from tblsalesorderhd a "
				// +
				// " inner join tblsalesorderdtl b on a.strSOCode=b.strSOCode "
				// +
				// " inner join  tblsaleschar c on c.strSOCode=a.strSOCode and c.strProdCode=b.strProdCode "
				// +
				// " inner join tblcharacteristics d on c.strCharCode=d.strCharCode "
				// +
				// "  inner join tblpartymaster e on a.strCustCode=e.strPCode "
				// +
				// " inner join tblproductmaster g on b.strProdCode=g.strProdCode "
				// +
				// " inner join tblsubgroupmaster i on g.strSGCode=i.strSGCode "
				// + " inner join tblgroupmaster h on h.strGCode=i.strGCode "
				// +
				// " inner join tblattachdocument f on f.strCode=a.strSOCode and f.strActualFileName=g.strPartNo "
				// + " Where h.strGCode IN   "+strGCodes+"  "
				// + " AND i.strSGCode IN "+strSGCodes+"  "
				// + " and a.strStatus='"+orderType+"' "
				// +
				// " AND DATE(a.dteFulmtDate) BETWEEN '"+dteFromDate+"' AND '"+dteToDate+"' "
				// +
				// " GROUP BY c.strCharCode, b.strProdCode,b.dblWeight,h.strGName,i.strSGName, e.strPName "
				// +
				// "  ORDER BY e.strPName,b.strProdCode,b.dblWeight,h.strGName,i.strSGName,c.strCharCode  ";
				// }
				// else{
				//
				// sqlAdvOrd
				// ="  select i.strSGName,   g.strProdCode,g.strProdName,b.dblQty AS dblQty, b.dblWeight AS dblWeight,b.strSOCode,c.strCharCode,  c.strCharValue,e.strPName,  DATE_FORMAT(a.dteSODate,'%d-%m-%Y'),b.strRemarks,d.strCharName "
				// +
				// " from tblsalesorderhd a inner join tblsalesorderdtl b on a.strSOCode=b.strSOCode   "
				// +
				// " inner join  tblsaleschar c on c.strSOCode=a.strSOCode and c.strProdCode=b.strProdCode "
				// +
				// " inner join tblcharacteristics d on c.strCharCode=d.strCharCode "
				// + " inner join tblpartymaster e on a.strCustCode=e.strPCode "
				// +
				// " inner join tblproductmaster g on b.strProdCode=g.strProdCode "
				// +
				// " inner join tblsubgroupmaster i on g.strSGCode=i.strSGCode "
				// + " inner join tblgroupmaster h on h.strGCode=i.strGCode  "
				// +
				// " inner join tblattachdocument f on f.strCode!=a.strSOCode "
				// + " where h.strGCode IN  "+strGCodes+" "
				// + " AND i.strSGCode IN "+strSGCodes+" "
				// + "  and a.strStatus='"+orderType+"' "
				// +
				// " AND DATE(a.dteFulmtDate) BETWEEN '"+dteFromDate+"' AND '"+dteToDate+"' "
				// +
				// " GROUP BY a.strSOCode,b.strProdCode,b.dblWeight,c.strCharCode, h.strGName,i.strSGName, e.strPName "
				// +
				// " ORDER BY a.strSOCode,e.strPName,b.strProdCode,b.dblWeight,h.strGName,i.strSGName,c.strCharCode  "
				// ;

				sqlAdvOrd = " select i.strSGName,   g.strProdCode,g.strProdName,b.dblQty AS dblQty, b.dblWeight AS dblWeight,b.strSOCode,ifnull(c.strCharCode,''), " + " ifnull(c.strCharValue,''),e.strPName,  DATE_FORMAT(a.dteSODate,'%d-%m-%Y'),b.strRemarks,ifnull(d.strCharName,''),IfNull(g.strProdCode,'') " + " from tblsalesorderhd a "
						+ " left outer join tblsalesorderdtl b on a.strSOCode=b.strSOCode " + " left outer join  tblsaleschar c on c.strSOCode=a.strSOCode and c.strProdCode=b.strProdCode and b.strRemarks=c.strAdvOrderNo " + " left outer join tblcharacteristics d on c.strCharCode=d.strCharCode " + "  left outer join tblpartymaster e on a.strCustCode=e.strPCode "
						+ " left outer join tblproductmaster g on b.strProdCode=g.strProdCode " + " left outer join tblsubgroupmaster i on g.strSGCode=i.strSGCode " + " left outer join tblgroupmaster h on h.strGCode=i.strGCode " + " inner join tblattachdocument f on f.strCode=a.strSOCode and f.strActualFileName=g.strProdCode " + " Where h.strGCode IN   " + strGCodes + "  "
						+ " AND i.strSGCode IN " + strSGCodes + "  " + " and a.strStatus='" + orderType + "' " + " AND DATE(a.dteFulmtDate) BETWEEN '" + dteFromDate + "' AND '" + dteToDate + "' " + " GROUP BY c.strCharCode, b.strProdCode,b.dblWeight,h.strGName,i.strSGName, e.strPName ,b.strRemarks "
						// +
						// "  ORDER BY e.strPName,b.strProdCode,b.dblWeight,c.strAdvOrderNo,h.strGName,i.strSGName,c.strCharCode  ";
						+ "  ORDER BY e.strPName,a.strSOCode,c.strAdvOrderNo,b.strProdCode,b.dblWeight,h.strGName,i.strSGName,c.strCharCode ";
			} else {

				sqlAdvOrd = "  select i.strSGName,   g.strProdCode,g.strProdName,b.dblQty AS dblQty, b.dblWeight AS dblWeight,b.strSOCode,ifnull(c.strCharCode,''),  ifnull(c.strCharValue,''),e.strPName,  DATE_FORMAT(a.dteSODate,'%d-%m-%Y'),b.strRemarks,ifnull(d.strCharName,'') " + " from tblsalesorderhd a left outer join tblsalesorderdtl b on a.strSOCode=b.strSOCode   "
						+ " left outer join  tblsaleschar c on c.strSOCode=a.strSOCode and c.strProdCode=b.strProdCode and b.strRemarks=c.strAdvOrderNo " + " left outer join tblcharacteristics d on c.strCharCode=d.strCharCode " + " left outer join tblpartymaster e on a.strCustCode=e.strPCode " + " left outer join tblproductmaster g on b.strProdCode=g.strProdCode "
						+ " left outer join tblsubgroupmaster i on g.strSGCode=i.strSGCode " + " left outer join tblgroupmaster h on h.strGCode=i.strGCode  " + " left outer join tblattachdocument f on f.strCode!=a.strSOCode " + " where h.strGCode IN  " + strGCodes + " " + " AND i.strSGCode IN " + strSGCodes + " " + "  and a.strStatus='" + orderType + "' " + " AND DATE(a.dteFulmtDate) BETWEEN '"
						+ dteFromDate + "' AND '" + dteToDate + "' " + " GROUP BY a.strSOCode,b.strProdCode,b.dblWeight,c.strCharCode, h.strGName,i.strSGName, e.strPName ,b.strRemarks "
						// +
						// " ORDER BY e.strPName,a.strSOCode,b.strProdCode,b.dblWeight,c.strAdvOrderNo,h.strGName,i.strSGName,c.strCharCode  "
						// ;
						+ " ORDER BY e.strPName,a.strSOCode,c.strAdvOrderNo,b.strProdCode,b.dblWeight,h.strGName,i.strSGName,c.strCharCode ";
			}

			List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlAdvOrd, "sql");
			ArrayList listChar = new ArrayList();
			StringBuffer prodChar = new StringBuffer();
			String previousSoCode = "";
			String previousProdCode = "";
			double dblpreWt = 0.00;
			String preAdvOrderNo = "";

			int cnt = 0;
			clsProductionComPilationBean objProdCompilationBean = null;
			int i = 0;
			for (int j = 0; j < listProdDtl.size(); j++) {
				cnt = listProdDtl.size() - 1;
				Object[] prodArr = (Object[]) listProdDtl.get(j);
				String prodCode = prodArr[1].toString();
				String soCode = prodArr[5].toString();
				double dblWt = Double.parseDouble(prodArr[4].toString());
				String advOrderNo = prodArr[10].toString();
				System.out.println(prodArr[10].toString());
				if (soCode.equalsIgnoreCase(previousSoCode) && prodCode.equalsIgnoreCase(previousProdCode) && dblpreWt == dblWt && advOrderNo.equals(preAdvOrderNo)) {

				} else {
					if (j > 0) {
						objProdCompilationBean.setStrCharistics(prodChar.toString());
						fieldList.add(objProdCompilationBean);
						prodChar.setLength(0);
					}
				}
				objProdCompilationBean = new clsProductionComPilationBean();
				objProdCompilationBean.setStrSGName(prodArr[0].toString());
				objProdCompilationBean.setStrProdCode(prodCode);
				objProdCompilationBean.setStrProdName(prodArr[2].toString());
				objProdCompilationBean.setDblQty(Double.parseDouble(prodArr[3].toString()));
				objProdCompilationBean.setDblWeight(Double.parseDouble(prodArr[4].toString()));
				objProdCompilationBean.setStrCharCode(prodArr[6].toString());
				objProdCompilationBean.setStrPName(prodArr[8].toString());
				objProdCompilationBean.setDteSODate(prodArr[9].toString());
				objProdCompilationBean.setStrSOCode(soCode);
				objProdCompilationBean.setStrRemark(prodArr[10].toString());
				prodChar.append(prodArr[11].toString() + "->" + prodArr[7].toString() + "             ");

				if (objBean.getStrExportType().equalsIgnoreCase("Yes")) {
					objProdCompilationBean.setStrItemCode(prodArr[12].toString());

				}

				if (j == listProdDtl.size() - 1) {
					objProdCompilationBean.setStrCharistics(prodChar.toString());
					fieldList.add(objProdCompilationBean);
				}

				previousSoCode = soCode;
				previousProdCode = prodCode;
				dblpreWt = dblWt;
				preAdvOrderNo = advOrderNo;
			}

			if (objBean.getStrExportType().equalsIgnoreCase("Yes")) {

				clsProductionComPilationBean objProdBean = null;
				for (int a = 0; a < fieldList.size(); a++) {
					String prodImage = "";
					objProdBean = (clsProductionComPilationBean) fieldList.get(a);

					List listBean = objAdvOrderService.funGetImageAdvOrder(objProdBean.getStrSOCode(), objProdBean.getStrItemCode(), clientCode);
					Blob image = null;
					byte[] imgData = null;

					try {

						Object prodimage = (Object) listBean.get(0);
						image = (Blob) prodimage;
						if (null != image && image.length() > 0) {
							imgData = image.getBytes(1, (int) image.length());
							try {
								imgData = Base64.getDecoder().decode(imgData);// decoding
																				// of
																				// byte
							} catch (Exception ex) {
								ex.printStackTrace();
							}
							File imgFolder = new File(System.getProperty("user.dir") + "\\ProductImageIcon");
							if (!imgFolder.exists()) {
								if (imgFolder.mkdir()) {
									System.out.println("Directory is created! " + imgFolder.getAbsolutePath());
								} else {
									System.out.println("Failed to create directory!");
								}
							}
							File fileUserImage = new File(System.getProperty("user.dir") + "\\ProductImageIcon\\" + objProdBean.getStrPName() + "_" + objProdBean.getStrRemark() + "_" + objProdBean.getStrProdCode() + "_" + objProdBean.getDblWeight() + ".jpg");
							prodImage = System.getProperty("user.dir") + "\\ProductImageIcon\\" + objProdBean.getStrPName() + "_" + objProdBean.getStrRemark() + "_" + objProdBean.getStrProdCode() + "_" + objProdBean.getDblWeight() + ".jpg";
							if (fileUserImage.exists()) {
								fileUserImage.delete();
							}
							fileUserImage.createNewFile();
							FileOutputStream fos = new FileOutputStream(fileUserImage);
							fos.write(imgData);
							fos.close();

							objProdBean.setStrImage(prodImage);

						}

					} catch (Exception ex) {
						objProdBean.setStrImage(prodImage);

					}
					finalList.add(objProdBean);
				}

			}

			String printedDate = objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd");
			String date[] = printedDate.split(" ");
			date = date[0].split("-");
			printedDate = date[2] + "-" + date[1] + "-" + date[0];
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
			hm.put("dteFromDate", fromDate);
			hm.put("dteToDate", toDate);
			hm.put("listChar", listChar);
			hm.put("printedDate", printedDate);
			hm.put("orderType", orderType.toUpperCase());
			JRDataSource beanCollectionDataSource = null;
			if (objBean.getStrExportType().equalsIgnoreCase("Yes")) {
				beanCollectionDataSource = new JRBeanCollectionDataSource(finalList);
			} else {
				beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
			}
			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			if (jp != null) {
				jprintlist.add(jp);
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=rptAdvanceOrderReport_" + dteFromDate + "_To_" + dteToDate + "_" + userCode + ".pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@RequestMapping(value = "/frmProductionAdvOrderReport", method = RequestMethod.GET)
	public ModelAndView funOpenProductionAdvanceOrderReport(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmProductionAdvOrderReport_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmProductionAdvOrderReport", "command", new clsReportBean());
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/rptProductionAdvOrderMainReport", method = RequestMethod.POST)
	public void funCallProductionCompilationAdvOrderReport(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		Connection con = null;
		JasperPrint jp = null;
		try {
			String type = objBean.getStrDocType();
			con = objGlobalFunctions.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String suppName = "";

			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptAdvOrderMainReport.jasper");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String propNameSql = "select a.strPropertyName  from dbwebmms.tblpropertymaster a where a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' ";
			List listPropName = objGlobalFunctionsService.funGetDataList(propNameSql, "sql");
			String propName = "";
			if (listPropName.size() > 0) {
				propName = listPropName.get(0).toString();
			}

			// group code
			Collection strGCodes = new ArrayList();
			String strGroupCodes = objBean.getStrGCode();
			String tempstrGCode[] = strGroupCodes.split(",");

			for (int i = 0; i < tempstrGCode.length; i++) {

				strGCodes.add(tempstrGCode[i].toString());
			}

			// subgroup code
			List<String> strSGCodes = new ArrayList<String>();
			String strSubGroupCodes = objBean.getStrSGCode();
			// strSGCodes.add(objBean.getStrSGCode().split(","));
			String tempstrSGCode[] = strSubGroupCodes.split(",");

			for (int i = 0; i < tempstrSGCode.length; i++) {

				strSGCodes.add(tempstrSGCode[i].toString());
			}

			Collection fieldListEven = new ArrayList();
			Collection fieldListOdd = new ArrayList();

			String fromDate = objBean.getDteFromFulfillment();
			String toDate = objBean.getDteToFulfillment();

			String fd = fromDate.split("-")[0];
			String fm = fromDate.split("-")[1];
			String fy = fromDate.split("-")[2];

			String td = toDate.split("-")[0];
			String tm = toDate.split("-")[1];
			String ty = toDate.split("-")[2];

			String dteFromDate = fy + "-" + fm + "-" + fd;
			String dteToDate = ty + "-" + tm + "-" + td;

			String fromSODate = objBean.getDteFromDate();
			String toSODate = objBean.getDteToDate();

			String fdSO = fromSODate.split("-")[0];
			String fmSO = fromSODate.split("-")[1];
			String fySO = fromSODate.split("-")[2];

			String tdSO = toSODate.split("-")[0];
			String tmSO = toSODate.split("-")[1];
			String tySO = toSODate.split("-")[2];

			String dteSOFromDate = fySO + "-" + fmSO + "-" + fdSO;
			String dteSOToDate = tySO + "-" + tmSO + "-" + tdSO;

			String orderType = objBean.getStrReportType();

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
			hm.put("dteSOFromDate", dteSOFromDate);
			hm.put("dteSOToDate", dteSOToDate);
			hm.put("strSGCodes", strSGCodes);
			hm.put("strGCodes", strGCodes);
			hm.put("fromSODate", fromSODate);
			hm.put("toSODate", toSODate);
			hm.put("fromDate", fromDate);
			hm.put("toDate", toDate);
			hm.put("orderType", orderType);

			jp = JasperFillManager.fillReport(reportName, hm, con);
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			if (jp != null) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				jprintlist.add(jp);
				resp.setContentType("application/pdf");

				JRExporter exporter = new JRPdfExporter();
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=rptProductionAdvOrderMainReport." + fromDate + " To " + toDate + "&" + userCode + ".pdf");
				exporter.exportReport();

				servletOutputStream.flush();
				servletOutputStream.close();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@RequestMapping(value = "/attachImageAdvOrd", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") clsReportBean bean, HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		// String docCode=request.getSession().getAttribute("code").toString();
		String docCode = request.getParameter("code").toString();

		String[] date = docCode.split("dte");
		String toDate = date[0];
		String fromDate = date[1];
		String ordetype = date[2];
		String strGCode = request.getParameter("strGrpCode").toString();
		String strSGCode = request.getParameter("strSubGrpCode").toString();
		String formTitle = request.getParameter("formName").toString();
		String transactionName = request.getParameter("transName").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		// model.put("documentList",objAttDocService.funListDocs(docCode,
		// clientCode));
		model.put("fromDate", fromDate);
		model.put("toDate", toDate);
		model.put("ordetype", ordetype);
		model.put("strGCode", strGCode);
		model.put("strSGCode", strSGCode);
		model.put("transactionName", transactionName);
		return new ModelAndView("frmAdvanceOrderAttachImage", model);

	}

	@RequestMapping(value = "/saveImageAdvOrd", method = RequestMethod.GET)
	public void funsaveImageAdvOrd(@ModelAttribute("command") clsReportBean bean, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
		// String docCode=request.getParameter("code").toString();
		String dteFromDate = request.getParameter("fromDate").toString();
		String dteToDate = request.getParameter("toDate").toString();
		String strGCode = request.getParameter("strGrp").toString();
		String strSGCode = request.getParameter("strSGrp").toString();
		// group code
		String tempstrGCode[] = strGCode.split(",");
		String strGCodes = "(";
		for (int i = 0; i < tempstrGCode.length; i++) {
			if (i == 0) {
				strGCodes = strGCodes + "'" + tempstrGCode[i] + "'";
			} else {
				strGCodes = strGCodes + ",'" + tempstrGCode[i] + "'";
			}
		}
		strGCodes += ")";

		// subgroup code
		String tempstrSGCode[] = strSGCode.split(",");
		String strSGCodes = "(";
		for (int i = 0; i < tempstrSGCode.length; i++) {
			if (i == 0) {
				strSGCodes = strSGCodes + "'" + tempstrSGCode[i] + "'";
			} else {
				strSGCodes = strSGCodes + ",'" + tempstrSGCode[i] + "'";
			}
		}
		strSGCodes += ")";
		String[] dte = dteFromDate.split("-");
		dteFromDate = dte[2] + "-" + dte[1] + "-" + dte[0];

		dte = dteToDate.split("-");
		dteToDate = dte[2] + "-" + dte[1] + "-" + dte[0];
		String ordetype = request.getParameter("ordetype").toString();

		// String sqlAdvOrd =
		// " select i.strSGName,   g.strProdCode,g.strProdName,b.dblQty AS dblQty, b.dblWeight AS dblWeight,b.strSOCode,c.strCharCode, "
		// +
		// " c.strCharValue,e.strPName,  DATE_FORMAT(a.dteSODate,'%d-%m-%Y'),b.strRemarks,d.strCharName,IfNull(g.strPartNo,'') "
		// + " from tblsalesorderhd a "
		// + " inner join tblsalesorderdtl b on a.strSOCode=b.strSOCode "
		// +
		// " inner join  tblsaleschar c on c.strSOCode=a.strSOCode and c.strProdCode=b.strProdCode "
		// + " inner join tblcharacteristics d on c.strCharCode=d.strCharCode "
		// + " inner join tblpartymaster e on a.strCustCode=e.strPCode "
		// + " inner join tblproductmaster g on b.strProdCode=g.strProdCode "
		// + " inner join tblsubgroupmaster i on g.strSGCode=i.strSGCode "
		// + " inner join tblgroupmaster h on h.strGCode=i.strGCode "
		// +
		// " inner join tblattachdocument f on f.strCode=a.strSOCode and f.strActualFileName=g.strPartNo "
		// + " Where h.strGCode IN  "+strGCodes+" "
		// + " AND i.strSGCode IN "+strSGCodes+" "
		// +
		// " And a.strStatus='"+ordetype+"' and DATE(a.dteFulmtDate) BETWEEN '"+dteFromDate+"' AND '"+dteToDate+"' "
		// +
		// " GROUP BY c.strCharCode, b.strProdCode,b.dblWeight,h.strGName,i.strSGName, e.strPName "
		// +
		// " ORDER BY e.strPName,b.strProdCode,b.dblWeight,h.strGName,i.strSGName,c.strCharCode  ";

		String sqlAdvOrd = " select i.strSGName,   g.strProdCode,g.strProdName,b.dblQty AS dblQty, b.dblWeight AS dblWeight,b.strSOCode,ifnull(c.strCharCode,''), " + " ifnull(c.strCharValue,''),e.strPName,  DATE_FORMAT(a.dteSODate,'%d-%m-%Y'),b.strRemarks,ifnull(d.strCharName,''),IfNull(g.strProdCode,'') " + " from tblsalesorderhd a "
				+ " left outer join tblsalesorderdtl b on a.strSOCode=b.strSOCode " + " left outer join  tblsaleschar c on c.strSOCode=a.strSOCode and c.strProdCode=b.strProdCode and b.strRemarks=c.strAdvOrderNo " + " left outer join tblcharacteristics d on c.strCharCode=d.strCharCode " + "  left outer join tblpartymaster e on a.strCustCode=e.strPCode "
				+ " left outer join tblproductmaster g on b.strProdCode=g.strProdCode " + " left outer join tblsubgroupmaster i on g.strSGCode=i.strSGCode " + " left outer join tblgroupmaster h on h.strGCode=i.strGCode " + " inner join tblattachdocument f on f.strCode=a.strSOCode and f.strActualFileName=g.strProdCode " + " Where h.strGCode IN   " + strGCodes + "  " + " AND i.strSGCode IN "
				+ strSGCodes + "  " + " and a.strStatus='" + ordetype + "' " + " AND DATE(a.dteFulmtDate) BETWEEN '" + dteFromDate + "' AND '" + dteToDate + "' " + " GROUP BY c.strCharCode, b.strProdCode,b.dblWeight,h.strGName,i.strSGName, e.strPName ,b.strRemarks "
				// +
				// "  ORDER BY e.strPName,b.strProdCode,b.dblWeight,c.strAdvOrderNo,h.strGName,i.strSGName,c.strCharCode  ";
				+ "  ORDER BY e.strPName,a.strSOCode,c.strAdvOrderNo,b.strProdCode,b.dblWeight,h.strGName,i.strSGName,c.strCharCode ";

		List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlAdvOrd, "sql");
		ArrayList listChar = new ArrayList();
		StringBuffer prodChar = new StringBuffer();
		String previousSoCode = "";
		String previousProdCode = "";
		ArrayList fieldList = new ArrayList();
		int cnt = 0;
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsProductionComPilationBean objProdCompilationBean = null;

		for (int j = 0; j < listProdDtl.size(); j++) {
			cnt = listProdDtl.size() - 1;
			Object[] prodArr = (Object[]) listProdDtl.get(j);
			String prodCode = prodArr[1].toString();
			String soCode = prodArr[5].toString();

			if (soCode.equalsIgnoreCase(previousSoCode) && prodCode.equalsIgnoreCase(previousProdCode)) {
			} else {
				if (j > 0) {
					objProdCompilationBean.setStrCharistics(prodChar.toString());
					fieldList.add(objProdCompilationBean);
					prodChar.setLength(0);
				}
			}
			objProdCompilationBean = new clsProductionComPilationBean();
			objProdCompilationBean.setStrSGName(prodArr[0].toString());
			objProdCompilationBean.setStrProdCode(prodCode);
			objProdCompilationBean.setStrProdName(prodArr[2].toString());
			objProdCompilationBean.setDblQty(Double.parseDouble(prodArr[3].toString()));
			objProdCompilationBean.setDblWeight(Double.parseDouble(prodArr[4].toString()));
			objProdCompilationBean.setStrCharCode(prodArr[6].toString());
			objProdCompilationBean.setStrPName(prodArr[8].toString());
			objProdCompilationBean.setDteSODate(prodArr[9].toString());
			objProdCompilationBean.setStrSOCode(soCode);
			objProdCompilationBean.setStrRemark(prodArr[10].toString());
			prodChar.append(prodArr[11].toString() + "->" + prodArr[7].toString() + "             ");
			objProdCompilationBean.setStrItemCode(prodArr[12].toString());

			if (j == listProdDtl.size() - 1) {
				objProdCompilationBean.setStrCharistics(prodChar.toString());
				fieldList.add(objProdCompilationBean);
			}

			previousSoCode = soCode;
			previousProdCode = prodCode;
		}

		clsProductionComPilationBean objProdBean = null;
		String prodImage = "";
		List<String> listofImageName = new ArrayList<String>();
		List<byte[]> list = new ArrayList<byte[]>();
		for (int a = 0; a < fieldList.size(); a++) {

			objProdBean = (clsProductionComPilationBean) fieldList.get(a);

			List listBean = objAdvOrderService.funGetImageAdvOrder(objProdBean.getStrSOCode(), objProdBean.getStrItemCode(), clientCode);
			Blob image = null;
			byte[] imgData = null;

			try {

				Object prodimage = (Object) listBean.get(0);
				image = (Blob) prodimage;
				if (null != image && image.length() > 0) {
					imgData = image.getBytes(1, (int) image.length());
					try {
						imgData = Base64.getDecoder().decode(imgData);// decoding
																		// of
																		// byte
					} catch (Exception ex) {
						ex.printStackTrace();
					}

					/*
					 * response.setContentType("image/jpeg");
					 * ServletOutputStream out; out =
					 * response.getOutputStream(); FileInputStream fin = new
					 * FileInputStream("c:\\test\\java.jpg"); InputStream is =
					 * new ByteArrayInputStream(imgData); BufferedInputStream
					 * bin = new BufferedInputStream(is); BufferedOutputStream
					 * bout = new BufferedOutputStream(out); int ch =0; ;
					 * while((ch=bin.read())!=-1) { bout.write(ch); }
					 * 
					 * bin.close(); fin.close(); bout.close(); out.close();
					 */

					//
					// File imgFolder = new File(System.getProperty("user.dir")
					// + "\\ProductImageIcon");
					// if (!imgFolder.exists()) {
					// if (imgFolder.mkdir()) {
					// System.out.println("Directory is created! "+imgFolder.getAbsolutePath());
					// } else {
					// System.out.println("Failed to create directory!");
					// }
					// }
					//
					// File fileUserImage = new
					// File(System.getProperty("user.dir") +
					// "\\ProductImageIcon\\" +
					// objProdBean.getStrRemark()+"_"+objProdBean.getStrProdCode()+"_"+objProdBean.getDblWeight()+
					// ".jpg");
					// prodImage=System.getProperty("user.dir") +
					// "\\ProductImageIcon";
					listofImageName.add(objProdBean.getStrPName() + "_" + objProdBean.getStrRemark() + "_" + objProdBean.getStrProdCode() + "_" + objProdBean.getDblWeight() + ".jpg");
					// if (fileUserImage.exists()) {
					// fileUserImage.delete();
					// }
					//
					// fileUserImage.createNewFile();
					// FileOutputStream fos = new
					// FileOutputStream(fileUserImage);
					// fos.write(imgData);
					// fos.close();
					list.add(imgData);

				}

			} catch (Exception ex) {
				ex.printStackTrace();

			}

		}

		try {

			if (!list.isEmpty()) {
				ZipOutputStream output = null;
				// byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];

				response.setContentType("application/zip");
				if (ordetype.equalsIgnoreCase("Advance Order")) {
					response.setHeader("Content-Disposition", "attachment; filename=\"AdvaceOrderImage.zip\"");
				} else {
					response.setHeader("Content-Disposition", "attachment; filename=\"UrgentOrderImage.zip\"");
				}
				output = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
				try {
					int i = 0;

					for (byte[] strImgNmae : list) {
						// File downloadFile = new
						// File(prodImage+"\\"+strImgNmae);
						// FileInputStream inStream = new
						// FileInputStream(downloadFile);

						InputStream input = new ByteArrayInputStream(strImgNmae);

						output.putNextEntry(new ZipEntry(listofImageName.get(i)));
						byte[] buffer = new byte[4096];
						int bytesRead = -1;
						int length;
						while ((length = input.read(buffer)) > 0) {

							output.write(buffer, 0, length);

						}
						output.closeEntry();
						input.close();
						i++;
					}// for
				}// try

				catch (Exception e) {
					e.printStackTrace();
				} finally {

					output.close();
				}
			}
		}

		catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
