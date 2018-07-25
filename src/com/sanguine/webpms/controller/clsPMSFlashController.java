package com.sanguine.webpms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsPMSFlashBean;
import com.sanguine.webpms.bean.clsPMSFlashDtlBean;

@Controller
public class clsPMSFlashController {

	@Autowired
	private clsGlobalFunctionsService objGlobalService;

	// Open PlanMaster
	@RequestMapping(value = "/frmPMSFlash", method = RequestMethod.GET)
	public ModelAndView funOpenFlashForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (urlHits.equalsIgnoreCase("1")) {

			return new ModelAndView("frmPMSFlash", "command", new clsPMSFlashBean());
		} else {
			return new ModelAndView("frmPMSFlash_1", "command", new clsPMSFlashBean());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/frmPMSFlashDetail", method = RequestMethod.GET)
	private @ResponseBody List funShowFlash(@RequestParam(value = "report") String report, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		List<clsPMSFlashDtlBean> listDtlBean = null;
		if (report.equals("Corporate")) {
			listDtlBean = funCoprate(fDate, tDate, req);
		}
		if (report.equals("Booker")) {
			listDtlBean = funBooker(fDate, tDate, req);
		}

		if (report.equals("Agent")) {
			listDtlBean = funAgent(fDate, tDate, req);
		}

		return listDtlBean;
	}

	private List funCoprate(String fDate, String tDate, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String sql = " select a.strReservationNo,CONCAT(d.strFirstName,' ',d.strMiddleName,' ',d.strLastName) as guestName," + " c.strCorporateDesc,date(a.dteArrivalDate) as ArrivalDate ," + " TIME_FORMAT(a.tmeArrivalTime,'%r') as ArrivalTime,date(a.dteDepartureDate) as DepartureDate ," + " TIME_FORMAT(a.tmeDepartureTime,'%r') as DepartureTime"
				+ "	from tblreservationhd a ,tblreservationdtl b ,tblcorporatemaster c ,tblguestmaster d" + " where a.strReservationNo=b.strReservationNo" + " and a.strCorporateCode = c.strCorporateCode" + " and b.strGuestCode=d.strGuestCode and" + " date(a.dteReservationDate) between '" + fDate + "' and '" + tDate + "' " + "	and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='"
				+ clientCode + "' " + "	and c.strClientCode='" + clientCode + "' " + " and d.strClientCode='" + clientCode + "' ";

		List list = objGlobalService.funGetListModuleWise(sql, "sql");
		List<clsPMSFlashDtlBean> listDtlBean = new ArrayList<clsPMSFlashDtlBean>();

		for (int cnt = 0; cnt < list.size(); cnt++) {
			Object[] arrObj = (Object[]) list.get(cnt);
			clsPMSFlashDtlBean objPMSBean = new clsPMSFlashDtlBean();
			objPMSBean.setStrReservationNo(arrObj[0].toString());
			objPMSBean.setGuestName(arrObj[1].toString());
			objPMSBean.setStrCorporateDesc(arrObj[2].toString());
			objPMSBean.setDteArrivalDate(arrObj[3].toString());
			objPMSBean.setTmeArrivalTime(arrObj[4].toString());
			objPMSBean.setDteDepartureDate(arrObj[5].toString());
			objPMSBean.setTmeDepartureTime(arrObj[6].toString());
			listDtlBean.add(objPMSBean);
		}

		return listDtlBean;
	}

	private List funBooker(String fDate, String tDate, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String sql = " select a.strReservationNo,CONCAT(d.strFirstName,' ',d.strMiddleName,' ',d.strLastName) as guestName," + " c.strBookerName,date(a.dteArrivalDate) as ArrivalDate ," + " TIME_FORMAT(a.tmeArrivalTime,'%r') as ArrivalTime,date(a.dteDepartureDate) as DepartureDate ," + " TIME_FORMAT(a.tmeDepartureTime,'%r') as DepartureTime"
				+ "	from tblreservationhd a ,tblreservationdtl b ,tblbookermaster c ,tblguestmaster d" + " where a.strReservationNo=b.strReservationNo" + " and a.strBookerCode = c.strBookerCode" + " and b.strGuestCode=d.strGuestCode and" + " date(a.dteReservationDate) between '" + fDate + "' and '" + tDate + "' " + "	and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='"
				+ clientCode + "' " + "	and c.strClientCode='" + clientCode + "' " + " and d.strClientCode='" + clientCode + "' ";

		List list = objGlobalService.funGetListModuleWise(sql, "sql");
		List<clsPMSFlashDtlBean> listDtlBean = new ArrayList<clsPMSFlashDtlBean>();

		for (int cnt = 0; cnt < list.size(); cnt++) {
			Object[] arrObj = (Object[]) list.get(cnt);
			clsPMSFlashDtlBean objPMSBean = new clsPMSFlashDtlBean();
			objPMSBean.setStrReservationNo(arrObj[0].toString());
			objPMSBean.setGuestName(arrObj[1].toString());
			objPMSBean.setStrBookerName(arrObj[2].toString());
			objPMSBean.setDteArrivalDate(arrObj[3].toString());
			objPMSBean.setTmeArrivalTime(arrObj[4].toString());
			objPMSBean.setDteDepartureDate(arrObj[5].toString());
			objPMSBean.setTmeDepartureTime(arrObj[6].toString());
			listDtlBean.add(objPMSBean);
		}

		return listDtlBean;
	}

	private List funAgent(String fDate, String tDate, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String sql = " select a.strReservationNo,CONCAT(d.strFirstName,' ',d.strMiddleName,' ',d.strLastName) as guestName," + " c.strDescription,e.strCommisionPaid,date(a.dteArrivalDate) as ArrivalDate ," + " TIME_FORMAT(a.tmeArrivalTime,'%r') as ArrivalTime,date(a.dteDepartureDate) as DepartureDate ," + " TIME_FORMAT(a.tmeDepartureTime,'%r') as DepartureTime"
				+ "	from tblreservationhd a ,tblreservationdtl b ,tblagentmaster c ,tblguestmaster d,tblagentcommision e " + " where a.strReservationNo=b.strReservationNo" + " and a.strAgentCode = c.strAgentCode " + " and c.strAgentCommCode=e.strAgentCommCode " + " and b.strGuestCode=d.strGuestCode and" + " date(a.dteReservationDate) between '" + fDate + "' and '" + tDate + "' "
				+ "	and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + clientCode + "' " + "	and c.strClientCode='" + clientCode + "' " + " and d.strClientCode='" + clientCode + "' ";

		List list = objGlobalService.funGetListModuleWise(sql, "sql");
		List<clsPMSFlashDtlBean> listDtlBean = new ArrayList<clsPMSFlashDtlBean>();

		for (int cnt = 0; cnt < list.size(); cnt++) {
			Object[] arrObj = (Object[]) list.get(cnt);
			clsPMSFlashDtlBean objPMSBean = new clsPMSFlashDtlBean();
			objPMSBean.setStrReservationNo(arrObj[0].toString());
			objPMSBean.setGuestName(arrObj[1].toString());
			objPMSBean.setStrAgentName(arrObj[2].toString());
			objPMSBean.setStrCommisionPaid(arrObj[3].toString());
			objPMSBean.setDteArrivalDate(arrObj[4].toString());
			objPMSBean.setTmeArrivalTime(arrObj[5].toString());
			objPMSBean.setDteDepartureDate(arrObj[6].toString());
			objPMSBean.setTmeDepartureTime(arrObj[7].toString());
			listDtlBean.add(objPMSBean);
		}

		return listDtlBean;
	}

}
