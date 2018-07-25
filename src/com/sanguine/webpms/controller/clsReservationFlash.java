package com.sanguine.webpms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsStockFlashBean;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsStockFlashModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsReservationBean;
import com.sanguine.webpms.bean.clsReservatrionFlashBean;

@Controller
public class clsReservationFlash {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	clsGlobalFunctions objGlobal;

	@RequestMapping(value = "/frmReservationFlash", method = RequestMethod.GET)
	private ModelAndView funLoadPropertySelection(@ModelAttribute("command") clsReservatrionFlashBean objPropBean, BindingResult result, HttpServletRequest req, Map<String, Object> model) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		String sql = " select strPropertyCode,strPropertyName from "+webStockDB+".tblpropertymaster ";
		List listOfProperty = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		HashMap hmProperty = new HashMap<String, String>();
		hmProperty.put("ALL", "ALL");
		for (int i = 0; i < listOfProperty.size(); i++) {
			Object[] objArr = (Object[]) listOfProperty.get(i);
			hmProperty.put(objArr[0].toString(), objArr[1].toString());

		}
		model.put("hmProperty", hmProperty);

		sql = " select a.strBookingTypeCode,a.strBookingTypeDesc from tblbookingtype a where a.strClientCode='" + clientCode + "'   ";
		List listOfBooking = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		HashMap hmBooking = new HashMap<String, String>();
		hmBooking.put("ALL", "ALL");
		for (int i = 0; i < listOfBooking.size(); i++) {
			Object[] objArr = (Object[]) listOfBooking.get(i);
			hmBooking.put(objArr[0].toString(), objArr[1].toString());

		}
		model.put("hmBooking", hmBooking);

		sql = " select a.strRoomTypeCode,a.strRoomTypeDesc from tblroomtypemaster a where a.strClientCode='" + clientCode + "'   ";
		List listOfRoomType = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		HashMap hmRoomType = new HashMap<String, String>();
		hmRoomType.put("ALL", "ALL");
		for (int i = 0; i < listOfRoomType.size(); i++) {
			Object[] objArr = (Object[]) listOfRoomType.get(i);
			hmRoomType.put(objArr[0].toString(), objArr[1].toString());

		}
		model.put("hmRoomType", hmRoomType);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmReservationFlash_1", "command", new clsReservatrionFlashBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmReservationFlash", "command", new clsReservatrionFlashBean());
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/funGetRevservationFlash", method = RequestMethod.GET)
	private @ResponseBody List funGetRevservationFlash(@RequestParam(value = "param1") String param1, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String[] spParam1 = param1.split(",");
		String propCode = spParam1[0];
		String bookingType = spParam1[1];
		String roomType = spParam1[2];
		String fArrDate = spParam1[3];
		fArrDate = objGlobal.funGetDate("yyyy-MM-dd", fArrDate);
		String tArrDate = spParam1[4];
		tArrDate = objGlobal.funGetDate("yyyy-MM-dd", tArrDate);
		String fDepDate = spParam1[5];
		fDepDate = objGlobal.funGetDate("yyyy-MM-dd", fDepDate);
		String tDepDate = spParam1[6];
		tDepDate = objGlobal.funGetDate("yyyy-MM-dd", tDepDate);

		String sql = " select a.strReservationNo,date(a.dteArrivalDate),date(a.dteDepartureDate),CONCAT(c.strFirstName,' ',c.strLastName) " + " ,ifnull(i.strRoomDesc,''),ifnull(e.strBookerName,'') as Source,ifnull(f.strCorporateDesc,'') as Corporate " + " ,ifnull(b.dblPaidAmt,0.00) as DepositAmt,a.strUserCreated,ifnull(d.strBookingTypeDesc,'') as Booking_Status  ,ifnull(h.strRoomTypeDesc,''),h.strRoomTypeDesc  "
				+ " from tblreservationhd a  " + " left outer join  tblreservationdtl g on a.strReservationNo=g.strReservationNo " + " left outer join  tblguestmaster c on g.strGuestcode=c.strGuestCode   " + " left outer join  tblreceipthd b on b.strReservationNo  = a.strReservationNo " + " left outer join  tblbookingtype d on a.strBookingTypeCode=d.strBookingTypeCode "
				+ " left outer join  tblbookermaster e on a.strBookerCode =e.strBookerName " + " left outer join  tblcorporatemaster f on a.strCorporateCode=f.strCorporateCode " + " left outer join  tblroomtypemaster h on g.strRoomType=h.strRoomTypeCode " + " left outer join  tblroom i on g.strRoomNo =i.strRoomCode " + " where Date(a.dteArrivalDate) between '" + fArrDate + "' and '" + tArrDate
				+ "'   " + " and Date(a.dteDepartureDate) between '" + fDepDate + "' and '" + tDepDate + "'   ";
		if (!propCode.equalsIgnoreCase("ALL")) {
			sql += " and a.strPropertyCode='" + propCode + "' ";
		}
		if (!bookingType.equalsIgnoreCase("ALL")) {
			sql += " and a.strBookingTypeCode='" + bookingType + "'  ";
		}
		if (!roomType.equalsIgnoreCase("ALL")) {
			sql += " and h.strRoomTypeCode='" + roomType + "' ";
		}
		sql += " and c.strGuestcode=g.strGuestCode and a.strClientCode='" + clientCode + "'  ";

		System.out.println(sql);

		List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");

		List<clsReservatrionFlashBean> listRevModel = new ArrayList<clsReservatrionFlashBean>();

		for (int cnt = 0; cnt < list.size(); cnt++) {
			Object[] arrObj = (Object[]) list.get(cnt);
			clsReservatrionFlashBean objRev = new clsReservatrionFlashBean();

			objRev.setStrResvervationCode(arrObj[0].toString());
			objRev.setDteArriveFromDate(objGlobal.funGetDate("dd-MM-yyyy", arrObj[1].toString()));
			objRev.setDteDepFromDate(objGlobal.funGetDate("dd-MM-yyyy", arrObj[2].toString()));
			objRev.setStrGuestName(arrObj[3].toString());
			objRev.setStrRoomNo(arrObj[4].toString());
			objRev.setStrSource(arrObj[5].toString());
			objRev.setStrCoparate(arrObj[6].toString());
			objRev.setDblPaidAmt(Double.parseDouble(arrObj[7].toString()));
			objRev.setStruser(arrObj[8].toString());
			objRev.setStrReservationTypeCode(arrObj[9].toString());
			objRev.setStrRoomTypeDesc(arrObj[11].toString());
			listRevModel.add(objRev);
		}

		ModelAndView objModelView = funGetModelAndView(req);
		objModelView.addObject("reservFlashData", listRevModel);
		List retList = new ArrayList();
		retList.add(listRevModel);

		return retList;

	}

	@SuppressWarnings("unchecked")
	private ModelAndView funGetModelAndView(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		// String usercode=req.getSession().getAttribute("usercode").toString();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		ModelAndView objModelView = null;
		if ("2".equalsIgnoreCase(urlHits)) {
			objModelView = new ModelAndView("frmReservationFlash_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			objModelView = new ModelAndView("frmReservationFlash");
		}

		return objModelView;
	}

	@RequestMapping(value = "/funExpoetReverationSheet", method = RequestMethod.GET)
	public ModelAndView funExpoetReverationSheet(@RequestParam(value = "param1") String param1, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();

		String[] spParam1 = param1.split(",");
		String propCode = spParam1[0];
		String bookingType = spParam1[1];
		String roomType = spParam1[2];
		String fArrDate = spParam1[3];
		fArrDate = objGlobal.funGetDate("yyyy-MM-dd", fArrDate);
		String tArrDate = spParam1[4];
		tArrDate = objGlobal.funGetDate("yyyy-MM-dd", tArrDate);
		String fDepDate = spParam1[5];
		fDepDate = objGlobal.funGetDate("yyyy-MM-dd", fDepDate);
		String tDepDate = spParam1[6];
		tDepDate = objGlobal.funGetDate("yyyy-MM-dd", tDepDate);
		List listMainExp = new ArrayList();
		String[] ExcelHeader = { "Reservation No", "Arrival Date", "Departure Date", "Guest", "Room", "Source", "Corporate", "Deposit", "User", "Booking Status", "Room Type" };
		listMainExp.add(ExcelHeader);

		String sql = " select a.strReservationNo,date(a.dteArrivalDate),date(a.dteDepartureDate),CONCAT(c.strFirstName,' ',c.strLastName) " + " ,ifnull(i.strRoomDesc,''),ifnull(e.strBookerName,'') as Source,ifnull(f.strCorporateDesc,'') as Corporate " + " ,ifnull(b.dblPaidAmt,0.00) as DepositAmt,a.strUserCreated,ifnull(d.strBookingTypeDesc,'') as Booking_Status  ,ifnull(h.strRoomTypeDesc,''),h.strRoomTypeDesc "
				+ " from tblreservationhd a  " + " left outer join  tblreservationdtl g on a.strReservationNo=g.strReservationNo " + " left outer join  tblguestmaster c on g.strGuestcode=c.strGuestCode   " + " left outer join  tblreceipthd b on b.strReservationNo  = a.strReservationNo " + " left outer join  tblbookingtype d on a.strBookingTypeCode=d.strBookingTypeCode "
				+ " left outer join  tblbookermaster e on a.strBookerCode =e.strBookerName " + " left outer join  tblcorporatemaster f on a.strCorporateCode=f.strCorporateCode " + " left outer join  tblroomtypemaster h on g.strRoomType=h.strRoomTypeCode " + " left outer join  tblroom i on g.strRoomNo =i.strRoomCode " + " where Date(a.dteArrivalDate) between '" + fArrDate + "' and '" + tArrDate
				+ "'   " + " and Date(a.dteDepartureDate) between '" + fDepDate + "' and '" + tDepDate + "'   ";
		if (!propCode.equalsIgnoreCase("ALL")) {
			sql += " and a.strPropertyCode='" + propCode + "' ";
		}
		if (!bookingType.equalsIgnoreCase("ALL")) {
			sql += " and a.strBookingTypeCode='" + bookingType + "'  ";
		}
		if (!roomType.equalsIgnoreCase("ALL")) {
			sql += " and h.strRoomTypeCode='" + roomType + "' ";
		}
		sql += " and c.strGuestcode=g.strGuestCode and a.strClientCode='" + clientCode + "'  ";

		System.out.println(sql);

		List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");

		List listGridFlashModel = new ArrayList();

		for (int cnt = 0; cnt < list.size(); cnt++) {
			Object[] arrObj = (Object[]) list.get(cnt);
			clsReservatrionFlashBean objRev = new clsReservatrionFlashBean();
			List DataList = new ArrayList<>();
			DataList.add(arrObj[0].toString());
			DataList.add(arrObj[1].toString());
			DataList.add(arrObj[2].toString());
			DataList.add(arrObj[3].toString());
			DataList.add(arrObj[4].toString());
			DataList.add(arrObj[5].toString());
			DataList.add(arrObj[6].toString());
			DataList.add(arrObj[7].toString());
			DataList.add(arrObj[8].toString());
			DataList.add(arrObj[9].toString());
			DataList.add(arrObj[11].toString());
			listGridFlashModel.add(DataList);
		}
		listMainExp.add(listGridFlashModel);
		return new ModelAndView("excelView", "stocklist", listMainExp);

	}

}
