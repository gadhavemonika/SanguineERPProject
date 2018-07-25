package com.sanguine.webpms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webpms.bean.clsGuestListReportBean;

@Controller
public class clsGuestListReportController {

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@RequestMapping(value = "/frmGuestListReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmGuestListReport_1", "command", new clsGuestListReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmGuestListReport", "command", new clsGuestListReportBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/rptGuestListReport", method = RequestMethod.GET)
	public void funOpenGuestReport(@ModelAttribute("command") clsGuestListReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		try {

			String fromDate = objBean.getDteFromDate();
			String[] fdte = fromDate.split("-");
			fromDate = fdte[2] + "-" + fdte[1] + "-" + fdte[0];

			String toDate = objBean.getDteTodate();

			String[] todte = toDate.split("-");
			toDate = todte[2] + "-" + todte[1] + "-" + todte[0];

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webpms/rptGuestListReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			List<clsGuestListReportBean> dataList = new ArrayList<clsGuestListReportBean>();

			HashMap reportParams = new HashMap();

			// String
			// checkInSql=" select b.strRoomNo ,a.dteArrivalDate,a.dteDepartureDate,c.intNoOfNights,'', concat(ifnull(d.strFirstName,''),ifnull(d.strMiddleName,''),ifnull(d.strLastName,'') ), "
			// +" ifnull(d.strPANNo,d.strPassportNo),ifnull(d.strNationality,''),concat(ifnull(d.strAddress,''),ifnull(d.strCity,''),ifnull(d.strCountry,'')) "
			// +" from tblcheckinhd a,tblcheckindtl b ,tblreservationhd c,tblguestmaster d  "
			// +"	where a.dteArrivalDate between '"+fromDate+"' and '"+toDate+"'	 and a.strCheckInNo=b.strCheckInNo "
			// +" and a.strReservationNo=c.strReservationNo   and c.strGuestcode=d.strGuestCode "
			// +" and b.strPayee='Y'	";

			String checkInSql = "select f.strRoomDesc ,date(a.dteArrivalDate),date(a.dteDepartureDate),ifnull(e.intNoOfNights,c.intNoOfNights),'', concat(ifnull(d.strFirstName,'')," + " ifnull(d.strMiddleName,''),ifnull(d.strLastName,'') ),ifnull(d.strPANNo,d.strPassportNo),ifnull(d.strNationality,''), "
					+ " concat(ifnull(d.strAddress,''),ifnull(d.strCity,''),ifnull(d.strCountry,'')),a.intNoOfAdults,a.intNoOfChild,ifnull(g.strDescription,'') " + " from tblcheckinhd a " + " left  join tblcheckindtl b on a.strCheckInNo=b.strCheckInNo and b.strPayee='Y' " + " left  join tblwalkinhd c on a.strWalkInNo=c.strWalkinNo  "
					+ " left  join tblreservationhd e on  a.strReservationNo=e.strReservationNo " + " left  join tblguestmaster d  on b.strGuestCode=d.strGuestCode " + " left  join  tblroom f on b.strRoomNo=f.strRoomCode " + " left join tblagentmaster g on e.strAgentCode=g.strAgentCode or c.strAgentCode=g.strAgentCode " + " where a.dteArrivalDate between '" + fromDate + "' and '" + toDate + "' ";

			List listOfCheckIn = objGlobalFunctionsService.funGetDataList(checkInSql, "sql");
			for (int i = 0; i < listOfCheckIn.size(); i++) {
				Object[] arr = (Object[]) listOfCheckIn.get(i);
				clsGuestListReportBean listBean = new clsGuestListReportBean();
				String[] arrDate = arr[1].toString().split("-");
				String[] depDate = arr[2].toString().split("-");
				listBean.setReportGroup("Check-IN");
				listBean.setStrRoomNo(arr[0].toString());
				listBean.setDteArrivalDate(arrDate[2] + "-" + arrDate[1] + "-" + arrDate[0]);
				listBean.setDteDepartureDate(depDate[2] + "-" + depDate[1] + "-" + depDate[0]);
				int noOfNights=0;
				if(Integer.valueOf(arr[3].toString())>0)
				{
					noOfNights=Integer.valueOf(arr[3].toString());
				}
				else
				{
					noOfNights=1;
				}
				listBean.setIntNoOfNights(String.valueOf(noOfNights));
				listBean.setStrGuestType(arr[4].toString());
				listBean.setStrGuestName(arr[5].toString());
				listBean.setStrIdentity(arr[6].toString());
				listBean.setStrNationality(arr[7].toString());
				listBean.setStrAddress(arr[8].toString());
				String intpax = String.valueOf(Integer.parseInt(arr[9].toString()) + Integer.parseInt(arr[10].toString()));
				listBean.setStrPax(intpax);
				listBean.setStrRateType("");
				listBean.setStrSource(arr[11].toString());
				dataList.add(listBean);
			}

			/**
			 * String checkOutSql=
			 * "select b.strRoomNo ,date(a.dteBillDate),date(a.dteDateCreated),ifnull(c.intNoOfNights,f.intNoOfNights),'', "
			 * +
			 * " concat(ifnull(d.strFirstName,''),ifnull(d.strMiddleName,''),ifnull(d.strLastName,'') ) as  Name "
			 * +
			 * " ,ifnull(d.strPANNo,d.strPassportNo),ifnull(d.strNationality,''),concat(ifnull(d.strAddress,''),ifnull(d.strCity,''),ifnull(d.strCountry,'')) as address  "
			 * +" from  tblbillhd a  " +
			 * " left join tblcheckindtl b on a.strCheckInNo=b.strCheckInNo and b.strPayee='Y' "
			 * // +
			 * " left join  tblreservationhd c on a.strReservationNo=c.strReservationNo  "
			 * +" left join tblguestmaster d  on b.strGuestCode=d.strGuestCode "
			 * + "left join tblcheckinhd e on a.strCheckInNo=e.strCheckInNo "
			 * +" left join tblwalkinhd f on e.strWalkInNo=f.strWalkinNo " +
			 * " left join  tblreservationhd c on e.strReservationNo=c.strReservationNo  "
			 * +" where a.dteBillDate between '"+fromDate+"' and '"+toDate+"' "
			 * +" group by a.strCheckInNo " ;
			 **/
			String checkOutSql = "select  g.strRoomDesc,date(a.dteBillDate),date(a.dteDateCreated),ifnull(c.intNoOfNights,f.intNoOfNights),'', " + "  ifnull(d.strFirstName,''),ifnull(d.strMiddleName,''),ifnull(d.strLastName,'') "
					+ " ,ifnull(d.strPANNo,d.strPassportNo),ifnull(d.strNationality,''),ifnull(d.strAddress,''),ifnull(d.strCity,''),ifnull(d.strCountry,'') ,e.intNoOfAdults,e.intNoOfChild,ifnull(h.strDescription,'')  " + " from  tblbillhd a  " + " left join tblcheckindtl b on a.strCheckInNo=b.strCheckInNo and b.strPayee='Y' "
					// +" left join  tblreservationhd c on a.strReservationNo=c.strReservationNo  "
					+ " left join tblguestmaster d  on b.strGuestCode=d.strGuestCode " + "left join tblcheckinhd e on a.strCheckInNo=e.strCheckInNo " + " left join tblwalkinhd f on e.strWalkInNo=f.strWalkinNo " + " left join  tblreservationhd c on e.strReservationNo=c.strReservationNo  " + " left  join  tblroom g on b.strRoomNo=g.strRoomCode "
					+ " left join tblagentmaster h on f.strAgentCode=h.strAgentCode or c.strAgentCode=h.strAgentCode " + " where a.dteBillDate between '" + fromDate + "' and '" + toDate + "' " + " group by a.strCheckInNo ";

			// List listOfCheckOut =
			// objGlobalFunctionsService.funGetPMSDataDemo(fromDate,toDate,"sql");
			List listOfCheckOut = objGlobalFunctionsService.funGetDataList(checkOutSql, "sql");
			// List listOfCheckOut=new ArrayList();
			for (int i = 0; i < listOfCheckOut.size(); i++) {
				Object[] arr = (Object[]) listOfCheckOut.get(i);
				String[] arrDate = arr[1].toString().split("-");
				String[] depDate = arr[2].toString().split("-");
				clsGuestListReportBean listBean = new clsGuestListReportBean();
				listBean.setReportGroup("Check-OUt");
				listBean.setStrRoomNo(arr[0].toString());
				listBean.setDteArrivalDate(arrDate[2] + "-" + arrDate[1] + "-" + arrDate[0]);
				listBean.setDteDepartureDate(depDate[2] + "-" + depDate[1] + "-" + depDate[0]);
				int noOfNights=0;
				if(Integer.valueOf(arr[3].toString())>0)
				{
					noOfNights=Integer.valueOf(arr[3].toString());
				}
				else
				{
					noOfNights=1;
				}
				listBean.setIntNoOfNights(String.valueOf(noOfNights));
				listBean.setStrGuestType(arr[4].toString());
				listBean.setStrGuestName(arr[5].toString() + " " + arr[6].toString() + " " + arr[7].toString());
				listBean.setStrIdentity(arr[8].toString());
				listBean.setStrNationality(arr[9].toString());
				listBean.setStrAddress(arr[10].toString() + " " + arr[11].toString() + " " + arr[12].toString());
				String intpax = String.valueOf(Integer.parseInt(arr[13].toString()) + Integer.parseInt(arr[14].toString()));
				listBean.setStrPax(intpax);
				listBean.setStrRateType("");
				listBean.setStrSource(arr[15].toString());
				dataList.add(listBean);
			}

			String sqlBookingType = " SELECT 'NA', DATE(a.dteArrivalDate), DATE(a.dteDepartureDate),a.intNoOfNights,'',  "
					+ " IFNULL(d.strFirstName,''), IFNULL(d.strMiddleName,''), IFNULL(d.strLastName,''), IFNULL(d.strPANNo, "
					+ " IFNULL(d.strPassportNo,'')), IFNULL(d.strNationality,''), IFNULL(d.strAddress,''), IFNULL(d.strCity,''), "
					+ " IFNULL(d.strCountry,''), a.strBookingTypeCode,c.strBookingTypeDesc,a.intNoOfAdults,a.intNoOfChild, "
					+ " IFNULL(h.strDescription,'')  "
					+ " FROM tblreservationhd a " 
					+ " LEFT JOIN tblreservationdtl b ON a.strReservationNo=b.strReservationNo  " 
					+ " LEFT OUTER JOIN tblguestmaster d ON b.strGuestcode=d.strGuestCode "
					+ " LEFT OUTER JOIN tblbookingtype c ON a.strBookingTypeCode=c.strBookingTypeCode " 
					+ " LEFT JOIN tblroomtypemaster g ON b.strRoomType=g.strRoomTypeCode " 
					+ " LEFT JOIN tblagentmaster h ON a.strAgentCode=h.strAgentCode " 
					+ " where a.dteArrivalDate between '" + fromDate + "' and '" + toDate + "' " 
					+ " group by a.strReservationNo " 
					+ " order by a.strBookingTypeCode ";

			List listBookingType = objGlobalFunctionsService.funGetDataList(sqlBookingType, "sql");
			for (int i = 0; i < listBookingType.size(); i++) {
				Object[] arr = (Object[]) listBookingType.get(i);
				clsGuestListReportBean listBean = new clsGuestListReportBean();
				String[] arrDate = arr[1].toString().split("-");
				String[] depDate = arr[2].toString().split("-");
				listBean.setReportGroup(arr[14].toString());
				listBean.setStrRoomNo(arr[0].toString());
				listBean.setDteArrivalDate(arrDate[2] + "-" + arrDate[1] + "-" + arrDate[0]);
				listBean.setDteDepartureDate(depDate[2] + "-" + depDate[1] + "-" + depDate[0]);
				int noOfNights=0;
				if(Integer.valueOf(arr[3].toString())>0)
				{
					noOfNights=Integer.valueOf(arr[3].toString());
				}
				else
				{
					noOfNights=1;
				}
				listBean.setIntNoOfNights(String.valueOf(noOfNights));
				listBean.setStrGuestType(arr[4].toString());
				listBean.setStrGuestName(arr[5].toString() + " " + arr[6].toString() + " " + arr[7].toString());
				listBean.setStrIdentity(arr[8].toString());
				listBean.setStrNationality(arr[9].toString());
				listBean.setStrAddress(arr[10].toString() + " " + arr[11].toString() + " " + arr[12].toString());
				String intpax = String.valueOf(Integer.parseInt(arr[15].toString()) + Integer.parseInt(arr[16].toString()));
				listBean.setStrPax(intpax);
				listBean.setStrRateType("");
				listBean.setStrSource(arr[17].toString());
				dataList.add(listBean);
			}

			HashMap hm = new HashMap();
			hm.put("pCompanyName", companyName);
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);
			hm.put("pAddress1", objSetup.getStrAdd1());
			hm.put("pAddress2", objSetup.getStrAdd2());
			hm.put("strCity", objSetup.getStrCity());
			hm.put("strState", objSetup.getStrState());
			hm.put("strCountry", objSetup.getStrCountry());
			hm.put("strPin", objSetup.getStrPin());
			hm.put("dataList", dataList);
			hm.put("fromDate", objBean.getDteFromDate());
			hm.put("toDate", objBean.getDteTodate());
			hm.put("pContactDetails", "");

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			JasperPrint jp = JasperFillManager.fillReport(jr, hm, new JREmptyDataSource());
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			jprintlist.add(jp);
			if (jprintlist.size() > 0) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				String type = "pdf";
				if (type.trim().equalsIgnoreCase("pdf")) {
					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=" + "rptGuestListReport." + type.trim());
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				} else {
					JRExporter exporter = new JRXlsExporter();
					resp.setContentType("application/xlsx");
					exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "attachment;filename=" + "rptGuestListReport." + type.trim());
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

}
