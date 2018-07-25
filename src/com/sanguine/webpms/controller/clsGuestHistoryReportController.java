package com.sanguine.webpms.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.math.BigDecimal;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webpms.bean.clsGuestHistoryReportBean;

@Controller
public class clsGuestHistoryReportController {
	
	
	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@RequestMapping(value = "/frmGuestHistoryReport", method = RequestMethod.GET)
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
			return new ModelAndView("frmGuestHistoryReport_1", "command", new clsGuestHistoryReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmGuestHistoryReport", "command", new clsGuestHistoryReportBean());
		} else {
			return null;
		}

	}

	
	@RequestMapping(value = "/loadGuestHistoryReport", method = RequestMethod.GET)
	public @ResponseBody List<clsGuestHistoryReportBean> funGuestHistoryReport1(@RequestParam("guestCode")String strGuestCode,@RequestParam("dteFromDate")String dteFromDate,
			@RequestParam("dteToDate")String dteToDate, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		if (objSetup == null) {
			objSetup = new clsPropertySetupModel();
		}
		String[] fdte = dteFromDate.split("-");
		dteFromDate = fdte[2] + "-" + fdte[1] + "-" + fdte[0];

		String[] todte = dteToDate.split("-");
		dteToDate = todte[2] + "-" + todte[1] + "-" + todte[0];
		List<clsGuestHistoryReportBean> listGuestData=new ArrayList<clsGuestHistoryReportBean>();
		
		StringBuilder sql1=new StringBuilder("select a.strCheckInNo,ifnull(f.strBillNo,''),a.dteCheckInDate,a.strRegistrationNo,a.strType,"//4
				+ " b.strGuestCode,c.strGuestPrefix,c.strFirstName,c.strMiddleName,c.strLastName,"//9
				+ " a.strType,b.strRoomNo,d.strRoomDesc,ifnull(e.strExtraBedTypeDesc,''),"//13
				+ " DATE_FORMAT(a.dteArrivalDate,\"%d-%m-%Y\") ,DATE_FORMAT(a.dteDepartureDate,\"%d-%m-%Y\"),"//15
				+ " DATEDIFF(a.dteDepartureDate,a.dteArrivalDate),a.intNoOfAdults,a.intNoOfChild"//18
				+ " ,ifnull(f.dblGrandTotal,0)"//19
				+ " from tblcheckinhd a "
				+ " left outer join tblcheckindtl b on a.strCheckInNo=b.strCheckInNo"
				+ " left outer join tblguestmaster c on b.strGuestCode=c.strGuestCode "
				+ " left outer join tblroom d on b.strRoomNo=d.strRoomCode "
				+ " left outer join tblextrabed e on b.strExtraBedCode=e.strExtraBedTypeCode "
				+ " left outer join tblbillhd f on f.strCheckInNo=a.strCheckInNo"
				+ " where a.dteCheckInDate between '"+dteFromDate+"' and '"+dteToDate+"'");
					if(!strGuestCode.equals("")){
						sql1.append(" and b.strGuestCode ='"+strGuestCode+"'");
					}
				sql1.append(" order by b.strGuestCode ;");
		
		String sqlInner="";
		List list = objGlobalFunctionsService.funGetDataList(sql1.toString(), "sql");
		if(list!=null)
		{
			List listRec;
			clsGuestHistoryReportBean objBean=new clsGuestHistoryReportBean();
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					Object ob[]=(Object[]) list.get(i);
					objBean=new clsGuestHistoryReportBean();
					objBean.setStrCheckInNo(ob[0].toString());
					objBean.setStrBillNo(ob[1].toString());
					objBean.setStrGuestName(ob[6].toString()+" "+ob[7].toString()+" "+ob[8].toString()+" "+ob[9].toString());
					objBean.setStrEntryType(ob[10].toString());
					objBean.setDteCheckIn(ob[14].toString());
					objBean.setDteCheckOut(ob[15].toString());
					objBean.setStrRoomNo(ob[12].toString());
					objBean.setStrExtraBed(ob[13].toString());
					//objBean.setStrWalkin_ReservationNo(ob[4].toString());
					objBean.setDblbillAmt(Double.parseDouble(ob[19].toString()));
					
					objBean.setDblNoOfdaysStayed(Double.parseDouble(ob[16].toString()));
					objBean.setIntNoOfAdults(Double.parseDouble(ob[17].toString()));
					objBean.setIntNoOfChild(Double.parseDouble(ob[18].toString()));
					
					sqlInner="select a.strReceiptNo,b.strSettlementCode,ifnull(c.strSettlementDesc,'') from tblreceipthd a "
							+ " left outer join tblreceiptdtl b on a.strReceiptNo=b.strReceiptNo "
							+ " left outer join tblsettlementmaster c on b.strSettlementCode=c.strSettlementCode"
							+ " where a.strCheckInNo='"+ob[0].toString()+"' and a.strBillNo='"+ob[1].toString()+"';";
					listRec = objGlobalFunctionsService.funGetDataList(sqlInner, "sql");
					String paymentType="";
					if(listRec.size()>0){
						for(int j=0;j<listRec.size();j++){
							Object objRec[]=(Object[]) listRec.get(j);
							if(paymentType.equals(""))
								paymentType=objRec[2].toString();
							else
								paymentType=paymentType+","+objRec[2].toString();
						}
								
					}
					objBean.setStrPaymentType(paymentType);
					listGuestData.add(objBean);
				}
			}
		}
		return listGuestData;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/exportGuestHistoryExcel", method = RequestMethod.GET)
	public ModelAndView funExportGuestHistoryExcel(@RequestParam("guestCode")String strGuestCode,@RequestParam("dteFromDate")String dteFromDate,
			@RequestParam("dteToDate")String dteToDate, HttpServletRequest req) {
		
		
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		if (objSetup == null) {
			objSetup = new clsPropertySetupModel();
		}
		String[] fdte = dteFromDate.split("-");
		dteFromDate = fdte[2] + "-" + fdte[1] + "-" + fdte[0];

		String[] todte = dteToDate.split("-");
		dteToDate = todte[2] + "-" + todte[1] + "-" + todte[0];
		List<clsGuestHistoryReportBean> listGuestData=new ArrayList<clsGuestHistoryReportBean>();
		
		StringBuilder sql1=new StringBuilder("select a.strCheckInNo,ifnull(f.strBillNo,''),a.dteCheckInDate,a.strRegistrationNo,a.strType,"//4
				+ " b.strGuestCode,c.strGuestPrefix,c.strFirstName,c.strMiddleName,c.strLastName,"//9
				+ " a.strType,b.strRoomNo,d.strRoomDesc,ifnull(e.strExtraBedTypeDesc,''),"//13
				+ " DATE_FORMAT(a.dteArrivalDate,\"%d-%m-%Y\") ,DATE_FORMAT(a.dteDepartureDate,\"%d-%m-%Y\"),"//15
				+ " DATEDIFF(a.dteDepartureDate,a.dteArrivalDate),a.intNoOfAdults,a.intNoOfChild"//18
				+ " ,ifnull(f.dblGrandTotal,0)"//19
				+ " from tblcheckinhd a "
				+ " left outer join tblcheckindtl b on a.strCheckInNo=b.strCheckInNo"
				+ " left outer join tblguestmaster c on b.strGuestCode=c.strGuestCode "
				+ " left outer join tblroom d on b.strRoomNo=d.strRoomCode "
				+ " left outer join tblextrabed e on b.strExtraBedCode=e.strExtraBedTypeCode "
				+ " left outer join tblbillhd f on f.strCheckInNo=a.strCheckInNo"
				+ " where a.dteCheckInDate between '"+dteFromDate+"' and '"+dteToDate+"'");
					if(!strGuestCode.equals("")){
						sql1.append(" and b.strGuestCode ='"+strGuestCode+"'");
					}
				sql1.append(" order by b.strGuestCode ;");
		
		String sqlInner="";
		
		
		
		List listGuestExl = new ArrayList();

		String repeortfileName = "GuestHistoryReport" + "_" + strGuestCode + "_" + dteFromDate + "_To_" + dteToDate + "_" + userCode;
		repeortfileName = repeortfileName.replaceAll(" ", "");
		listGuestExl.add(repeortfileName);

		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblSubTotalValue = new BigDecimal(0);
		BigDecimal dblTaxTotalValue = new BigDecimal(0);
		
			//						
		String[] ExcelHeader = { "Guest Name", "Bill No", "Type", "Check In Date", "Check Out Date", "Room No","Extra Bed","No of Days Stayed","No Of Adults","No Of Child","Bill Amt","Payment Type" };
		listGuestExl.add(ExcelHeader);

		List listofGuestFlash = new ArrayList();

		List list = objGlobalFunctionsService.funGetDataList(sql1.toString(), "sql");
		if(list!=null)
		{
			List listRec;
			clsGuestHistoryReportBean objBean=new clsGuestHistoryReportBean();
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					Object ob[]=(Object[]) list.get(i);
					List DataList = new ArrayList<>();
					DataList.add(ob[6].toString()+" "+ob[7].toString()+" "+ob[8].toString()+" "+ob[9].toString());
					//DataList.add(ob[0].toString());
					DataList.add(ob[1].toString());
					DataList.add(ob[10].toString());
					DataList.add(ob[14].toString());
					DataList.add(ob[15].toString());
					DataList.add(ob[12].toString());
					DataList.add(ob[13].toString());
					DataList.add(ob[16].toString());
					DataList.add(ob[17].toString());
					DataList.add(ob[18].toString());
					
					DataList.add(ob[19].toString());
					sqlInner="select a.strReceiptNo,b.strSettlementCode,ifnull(c.strSettlementDesc,'') from tblreceipthd a "
							+ " left outer join tblreceiptdtl b on a.strReceiptNo=b.strReceiptNo "
							+ " left outer join tblsettlementmaster c on b.strSettlementCode=c.strSettlementCode"
							+ " where a.strCheckInNo='"+ob[0].toString()+"' and a.strBillNo='"+ob[1].toString()+"';";
					listRec = objGlobalFunctionsService.funGetDataList(sqlInner, "sql");
					String paymentType="";
					if(listRec.size()>0){
						for(int j=0;j<listRec.size();j++){
							Object objRec[]=(Object[]) listRec.get(j);
							if(paymentType.equals(""))
								paymentType=objRec[2].toString();
							else
								paymentType=paymentType+","+objRec[2].toString();
						}
								
					}
					DataList.add(paymentType);
					
					listofGuestFlash.add(DataList);

					
					
				}
			}
		}

		listGuestExl.add(listofGuestFlash);
		
		/*DecimalFormat df = new DecimalFormat("#.##");
		double floatingPoint = 0.0;*/
		
		// return new ModelAndView("excelView", "stocklist", listInvoice);
		return new ModelAndView("excelViewWithReportName", "listWithReportName", listGuestExl);
	}
	
	
	
	
	
	
	
		
}


/*	String sql=" select a.strReceiptNo,a.strAgainst,a.strCheckInNo,a.strRegistrationNo," //3
+ " a.strReservationNo,a.strBillNo,d.strGuestCode,e.strGuestPrefix,e.strFirstName,e.strMiddleName,e.strLastName,"//10
+ " c.strSettlementDesc,"
+ " DATE_FORMAT(h.dteArrivalDate,\"%d-%m-%Y\") ,DATE_FORMAT(h.dteDepartureDate,\"%d-%m-%Y\"),"//13
+ " DATEDIFF(h.dteDepartureDate,h.dteArrivalDate),a.dblReceiptAmt " //15
+ " from tblreceipthd a"
+ " inner join tblreceiptdtl b on a.strReceiptNo=b.strReceiptNo "
+ " left outer join tblsettlementmaster c on b.strSettlementCode=c.strSettlementCode "
+ " left outer join tblreservationhd f on a.strReservationNo=f.strReservationNo "
+ " left outer join tblcheckindtl d on a.strCheckInNo=d.strCheckInNo "
+ " left outer join tblguestmaster e on d.strGuestCode=e.strGuestCode or e.strGuestCode=f.strGuestCode "
+ " left outer join tblwalkindtl g on e.strGuestCode=g.strGuestCode "
+ " left outer join tblcheckinhd h on d.strCheckInNo=h.strCheckInNo and h.strWalkInNo=g.strWalkinNo"
+ " where h.dteCheckInDate between '"+dteFromDate+"' and '"+dteToDate+"'"
+ " order by d.strGuestCode";*/