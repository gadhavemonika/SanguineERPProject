package com.sanguine.webpms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webpms.bean.clsNoShowReportBean;
import com.sanguine.webpms.service.clsFolioService;

@Controller
public class clsNoShowReportController {

	@Autowired
	private clsFolioService objFolioService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	// Open Folio Printing
	@RequestMapping(value = "/frmNoShowReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmNoShowReport_1", "command", new clsNoShowReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmNoShowReport", "command", new clsNoShowReportBean());
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/frmNoShowReportList", method = RequestMethod.GET)
	public @ResponseBody List funGenerateCheckInListReport(@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate, HttpServletRequest req, HttpServletResponse resp) {
		
		List listRet = new ArrayList();
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			
			// get all parameters
			String sqlParametersNoShowList = "select a.strReservationNo,a.dteArrivalDate,ifnull(b.dblReceiptAmt,0)"
											+ " ,d.strGuestcode,c.strFirstName,c.strMiddleName,c.strLastName,a.strNoRoomsBooked "
											+ " from tblreservationhd a left outer join tblreceipthd b "
											+ " on a.strReservationNo=b.strReservationNo,tblguestmaster c,tblreservationdtl d "
											+ " where  a.strReservationNo=d.strReservationNo and d.strGuestCode=c.strGuestCode "
											+ " and date(a.dteArrivalDate) between '"+fromDate+"' and '"+toDate+"' and "
											+ " date(a.dteDepartureDate) between '"+fromDate+"' and '"+toDate+"' "
											+ " and  a.strReservationNo Not IN(select strReservationNo from tblcheckinhd )";

			List listOfNoShow = objGlobalFunctionsService.funGetDataList(sqlParametersNoShowList, "sql");
			List fieldList = new ArrayList();
			double receiptTot=0.00;
			
			if(listOfNoShow!=null)
			{
			for (int i = 0; i < listOfNoShow.size(); i++) {
				Object[] arr = (Object[]) listOfNoShow.get(i);
				
				clsNoShowReportBean objNoShoeBean = new clsNoShowReportBean();

				objNoShoeBean.setStrReservationNo(arr[0].toString());
				objNoShoeBean.setStrNoOfRooms(arr[7].toString());
				objNoShoeBean.setDblPayment(Double.parseDouble(arr[2].toString()));
				receiptTot = receiptTot + Double.parseDouble(arr[2].toString());
				objNoShoeBean.setStrGuestName(arr[4].toString()+" "+arr[5].toString()+" "+arr[6].toString());
			
				fieldList.add(objNoShoeBean);
				
			}
			
			listRet.add(fieldList);
			listRet.add(receiptTot);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listRet;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/downloadNoShowFlashExcel", method = RequestMethod.GET)
	public ModelAndView downloadNoShowFlashExcel(@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();

		List listExportDoc = new ArrayList();
		
		String[] fDate = fromDate.split("-");
		String fromDateToDisplay = fDate[2]+fDate[1]+fDate[0];
		String[] tDate = toDate.split("-");
		String toDateToDisplay = tDate[2]+tDate[1]+tDate[0];
		
		listExportDoc.add("NoShowFlash_" + fromDate + "to" + toDate + "_" + userCode);
		
			String[] ExcelHeader = { "Reservation No", "No Of Rooms", "Payment", "Guest Name" };
			listExportDoc.add(ExcelHeader);
			double receiptTot=0.00;
			List list = new ArrayList<>();
			String sqlParametersNoShowList = "SELECT a.strReservationNo,a.dteArrivalDate, IFNULL(b.dblReceiptAmt,0),d.strGuestcode,c.strFirstName,c.strMiddleName,c.strLastName,a.strNoRoomsBooked "
						+ " FROM tblreservationhd a "
						+ " LEFT OUTER "
						+ " JOIN tblreceipthd b ON a.strReservationNo=b.strReservationNo,tblguestmaster c,tblreservationdtl d "
						+ " WHERE a.strReservationNo=d.strReservationNo and d.strGuestCode=c.strGuestCode  "
						+ " and DATE(a.dteArrivalDate)  between '"+fromDateToDisplay+"' and '"+toDateToDisplay+"' "
						+ " AND DATE(a.dteDepartureDate)  between '"+fromDateToDisplay+"' and '"+toDateToDisplay+"' " 
						+ " AND a.strReservationNo NOT IN( "
						+ " SELECT strReservationNo "
						+ " FROM tblcheckinhd)";

			List listOfNoShow = objGlobalFunctionsService.funGetDataList(sqlParametersNoShowList, "sql");
			
			for (int cnt = 0; cnt < listOfNoShow.size(); cnt++) {
				Object[] arr = (Object[]) listOfNoShow.get(cnt);
								
				List DataList = new ArrayList<>();
				DataList.add(arr[0].toString());
				DataList.add(arr[7].toString());
				DataList.add(Double.parseDouble(arr[2].toString()));
				receiptTot = receiptTot + Double.parseDouble(arr[2].toString());
				DataList.add(arr[4].toString()+" "+arr[5].toString()+" "+arr[6].toString());
				
				list.add(DataList);
			}

			
	
			List DataList = new ArrayList<>();
			DataList.add("");
			DataList.add("");
			DataList.add("");
			list.add(DataList);
			listExportDoc.add(list);

			
		// return a view which will be resolved by an excel view resolver
		return new ModelAndView("excelViewWithReportName", "listWithReportName", listExportDoc);

	}

}
