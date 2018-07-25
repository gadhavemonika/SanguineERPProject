package com.sanguine.webpms.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webpms.bean.clsFolioPrintingBean;
import com.sanguine.webpms.bean.clsPaxReportBean;

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

@Controller
public class clsPaxReportController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@RequestMapping(value = "/frmPaxReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPaxReport_1", "command", new clsPaxReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPaxReport", "command", new clsPaxReportBean());
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rptPaxReport", method = RequestMethod.GET)
	public void funGenerateCancelationReport(@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate, HttpServletRequest req, HttpServletResponse resp) {
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webpms/rptPaxReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			List<clsFolioPrintingBean> dataList = new ArrayList<clsFolioPrintingBean>();
			@SuppressWarnings("rawtypes")
			String propNameSql = "select a.strPropertyName  from "+webStockDB+".tblpropertymaster a where a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' ";
			List listPropName = objGlobalFunctionsService.funGetDataList(propNameSql, "sql");
			String propName = "";
			if (listPropName.size() > 0) {
				propName = listPropName.get(0).toString();
			}

			HashMap reportParams = new HashMap();

			reportParams.put("pCompanyName", companyName);
			reportParams.put("pAddress1", objSetup.getStrAdd1() + "," + objSetup.getStrAdd2() + "," + objSetup.getStrCity());
			reportParams.put("pAddress2", objSetup.getStrState() + "," + objSetup.getStrCountry() + "," + objSetup.getStrPin());
			reportParams.put("pContactDetails", "");
			reportParams.put("strImagePath", imagePath);
			reportParams.put("strUserCode", userCode);
			reportParams.put("pFromDate", fromDate);
			reportParams.put("pTtoDate", toDate);
			reportParams.put("propName", propName);

			// get all parameters
			String sqlPax = " select b.strGuestCode,b.strRoomNo,a.strCheckInNo,c.strFirstName,c.strMiddleName,c.strLastName,a.dteCheckInDate "
								+ ",a.strReservationNo,a.strWalkInNo,d.strRoomDesc "
								+ " from tblcheckinhd a, tblcheckindtl b,tblguestmaster c,tblroom d "
								+ " where a.strCheckInNo=b.strCheckInNo "
								+ " and b.strGuestCode=c.strGuestCode and b.strRoomNo=d.strRoomCode"
								+ " and a.strCheckInNo NOT IN(select d.strCheckInNo from tblbillhd d)"
								+ " and date(a.dteCheckInDate) between '"+fromDate+"' and '"+toDate+"'" ;

			List listOfPax = objGlobalFunctionsService.funGetDataList(sqlPax, "sql");
			ArrayList fieldList = new ArrayList();

			for (int i = 0; i < listOfPax.size(); i++) {
				Object[] arr = (Object[]) listOfPax.get(i);
				clsPaxReportBean objPaxBean = new clsPaxReportBean();
				objPaxBean.setStrRoomNo(arr[1].toString());
				objPaxBean.setStrCheckInNo(arr[2].toString());
				objPaxBean.setStrGuestFullName(arr[3].toString()+" "+arr[4].toString()+" "+arr[5].toString());
				objPaxBean.setDteCheckInDate(arr[6].toString());
				if(!arr[7].toString().equalsIgnoreCase(""))
				{
					objPaxBean.setStrAgainstType("Reservation");
				}
				else
				{
					objPaxBean.setStrAgainstType("Walk-In");
				}
				objPaxBean.setStrRoomName(arr[9].toString());
				fieldList.add(objPaxBean);

			}
			
			Comparator<clsPaxReportBean> roomNoComparator = new Comparator<clsPaxReportBean>() {

				@Override
				public int compare(clsPaxReportBean o1, clsPaxReportBean o2) {
					return o1.getStrRoomNo().compareToIgnoreCase(o2.getStrRoomNo());
				}
			};

			Collections.sort(fieldList, new clsPaxReportComparator1(roomNoComparator));


			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr, reportParams, beanCollectionDataSource);
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			if (jp != null) {
				jprintlist.add(jp);
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=PaxReport.pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

 class clsPaxReportComparator1 implements Comparator<clsPaxReportBean> {

	private List<Comparator<clsPaxReportBean>> listComparators;

	@SafeVarargs
	public clsPaxReportComparator1(Comparator<clsPaxReportBean>... comparators) {
		this.listComparators = Arrays.asList(comparators);
	}

	@Override
	public int compare(clsPaxReportBean o1, clsPaxReportBean o2) {
		for (Comparator<clsPaxReportBean> comparator : listComparators) {
			int result = comparator.compare(o1, o2);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}
}
