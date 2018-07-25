package com.sanguine.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.record.CalcCountRecord;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsFundFlowBean;
import com.sanguine.crm.model.clsInvoiceHdModel;
import com.sanguine.crm.model.clsSalesOrderHdModel;
import com.sanguine.crm.service.clsInvoiceHdService;
import com.sanguine.crm.service.clsSalesOrderService;
import com.sanguine.model.clsPurchaseIndentHdModel;
import com.sanguine.model.clsPurchaseOrderHdModel;
import com.sanguine.model.clsSupplierMasterModel;
import com.sanguine.service.clsPurchaseOrderService;
import com.sanguine.service.clsSubGroupMasterService;
import com.sanguine.service.clsSupplierMasterService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsFundFlowController {
	@Autowired
	private clsSubGroupMasterService objSubGrpMasterService;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private clsPurchaseOrderService objPurchaseOrderService;

	@Autowired
	private clsSalesOrderService objSalesHdService;

	@Autowired
	private clsSupplierMasterService objSupplierMasterService;

	@RequestMapping(value = { "/frmFundFlowReport" }, method = { RequestMethod.GET })
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmFundFlow_1", "command", new clsReportBean());
		}
		if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmFundFlow", "command", new clsReportBean());
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadFundFlowData", method = RequestMethod.GET)
	public @ResponseBody List funLoadFundFlowData(HttpServletRequest request) {
		String fromDate = request.getParameter("fromDate").toString();
		String toDate = request.getParameter("toDate").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List<clsFundFlowBean> listFF = new ArrayList<clsFundFlowBean>();
		try {

			SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
			/*
			 * GregorianCalendar calinv = new GregorianCalendar();
			 * calinv.setTime(sf.parse(toDate)); calinv.add(Calendar.DATE, 1);
			 * String newToDateInv = (calinv.getTime().getYear() + 1900) + "-" +
			 * (calinv.getTime().getMonth() + 1) + "-" +
			 * (calinv.getTime().getDate());
			 */
			List<clsSalesOrderHdModel> listSOhd = objSalesHdService.funGetHdList(objGlobalFunctions.funGetDate("yyyy-MM-dd", fromDate), objGlobalFunctions.funGetDate("yyyy-MM-dd", toDate), clientCode);
			List<clsPurchaseOrderHdModel> listPOhd = objPurchaseOrderService.funGetHdList(objGlobalFunctions.funGetDate("yyyy-MM-dd", fromDate), objGlobalFunctions.funGetDate("yyyy-MM-dd", toDate), clientCode);
			Date fDate = sf.parse(fromDate);
			Date tDate = sf.parse(toDate);
			int days = Days.daysBetween(new LocalDate(fDate), new LocalDate(tDate)).getDays();

			for (int i = 0; i <= days; i++) {

				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(sf.parse(fromDate));
				cal.add(Calendar.DATE, i);
				String newNextDate = (cal.getTime().getYear() + 1900) + "-" + (cal.getTime().getMonth() + 1) + "-" + (cal.getTime().getDate());
				// System.out.println(newNextDate);

				for (clsSalesOrderHdModel objSO : listSOhd) {
					// clsInvoiceHdModel objInv = (clsInvoiceHdModel) obj;
					if (sf.parse(objSO.getDteSODate().split(" ")[0]).equals(sf.parse(newNextDate))) {

						clsFundFlowBean objFF = new clsFundFlowBean();
						objFF.setStrTransectionType("Sales Order");
						objFF.setStrRefNo(objSO.getStrSOCode());
						objFF.setDteDoc(objGlobalFunctions.funGetDate("yyyy-MM-dd", objSO.getDteSODate().split(" ")[0]));
						objFF.setStrPartyCode(objSO.getStrCustCode());
						clsSupplierMasterModel objParty = objSupplierMasterService.funGetObject(objSO.getStrCustCode(), clientCode);
						objFF.setStrPartyName(objParty.getStrPName());
						objFF.setDtePay(objGlobalFunctions.funGetDate("yyyy-MM-dd", objSO.getDteFulmtDate().split(" ")[0]));
						objFF.setDblAmt(objSO.getDblTotal());
						listFF.add(objFF);
					}

				}

				for (clsPurchaseOrderHdModel objPO : listPOhd) {
					// clsPurchaseOrderHdModel objPO = (clsPurchaseOrderHdModel)
					// obj;
					if (sf.parse(objPO.getDtPODate().split(" ")[0]).equals(sf.parse(newNextDate))) {
						clsFundFlowBean objFF = new clsFundFlowBean();
						objFF.setStrTransectionType("Purchase Order");
						objFF.setStrRefNo(objPO.getStrPOCode());
						objFF.setDteDoc(objGlobalFunctions.funGetDate("yyyy-MM-dd", objPO.getDtPODate().split(" ")[0]));
						objFF.setStrPartyCode(objPO.getStrSuppCode());
						clsSupplierMasterModel objParty = objSupplierMasterService.funGetObject(objPO.getStrSuppCode(), clientCode);
						objFF.setStrPartyName(objParty.getStrPName());
						objFF.setDtePay(objGlobalFunctions.funGetDate("yyyy-MM-dd", objPO.getDtPayDate().split(" ")[0]));
						objFF.setDblAmt(objPO.getDblFinalAmt());
						listFF.add(objFF);
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return listFF;

	}

}
