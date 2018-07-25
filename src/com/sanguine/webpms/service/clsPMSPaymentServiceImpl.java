package com.sanguine.webpms.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsPMSPaymentBean;
import com.sanguine.webpms.dao.clsPMSPaymentDao;
import com.sanguine.webpms.model.clsBillHdModel;
import com.sanguine.webpms.model.clsPMSPaymentHdModel;
import com.sanguine.webpms.model.clsPMSPaymentReceiptDtl;

@Service("clsPMSPaymentService")
public class clsPMSPaymentServiceImpl implements clsPMSPaymentService {

	@Autowired
	private clsPMSPaymentDao objPaymentDao;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsBillService objBillService;

	@Override
	public void funAddUpdatePaymentHd(clsPMSPaymentHdModel objHdModel) {
		// TODO Auto-generated method stub
	}

	@Override
	public clsPMSPaymentHdModel funPreparePaymentModel(clsPMSPaymentBean objPaymentBean, String clientCode, HttpServletRequest request, String userCode) {
		String reservationNo = "";
		String registrationNo = "";
		String folioNo = "";
		String checkInNo = "";
		String billNo = "";
		String PMSDate = objGlobal.funGetDate("yyyy-MM-dd", request.getSession().getAttribute("PMSDate").toString());
		;

		clsPMSPaymentHdModel objModel = new clsPMSPaymentHdModel();

		if (objPaymentBean.getStrReceiptNo().isEmpty()) {
			String transaDate = objGlobal.funGetCurrentDateTime("dd-MM-yyyy").split(" ")[0];
			String documentNo = objGlobal.funGeneratePMSDocumentCode("frmPMSPayment", transaDate, request);
			objModel.setStrReceiptNo(documentNo);
			objModel.setStrUserCreated(userCode);
			objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			objModel.setStrReceiptNo(objPaymentBean.getStrReceiptNo());
		}
		objModel.setStrAgainst(objPaymentBean.getStrAgainst());
		objModel.setDblPaidAmt(objPaymentBean.getDblReceiptAmt());
		objModel.setDblReceiptAmt(objPaymentBean.getDblReceiptAmt());

		if (objPaymentBean.getStrAgainst().equals("Reservation")) {
			String sql = "select ifnull(b.strCheckInNo,''),ifnull(b.strRegistrationNo,''),a.strReservationNo" + " ,ifnull(b.strFolioNo,'') " + " from tblreservationhd a left outer join tblfoliohd b on a.strReservationNo=b.strReservationNo " + " where a.strReservationNo='" + objPaymentBean.getStrDocNo() + "' ";
			List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
			if (list.size() > 0) {
				for (int cnt = 0; cnt < list.size(); cnt++) {
					Object[] arrObj = (Object[]) list.get(cnt);
					checkInNo = arrObj[0].toString();
					registrationNo = arrObj[1].toString();
					reservationNo = arrObj[2].toString();
					folioNo = arrObj[3].toString();
					billNo = "";
				}
			}
		} else if (objPaymentBean.getStrAgainst().equals("Check-In")) {
			String sql = " select b.strCheckInNo,a.strRegistrationNo,b.strReservationNo,b.strFolioNo " + " from tblcheckinhd a,tblfoliohd b " + " where a.strCheckInNo=b.strCheckInNo and a.strCheckInNo='" + objPaymentBean.getStrDocNo() + "' ";
			List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
			if (list.size() > 0) {
				for (int cnt = 0; cnt < list.size(); cnt++) {
					Object[] arrObj = (Object[]) list.get(cnt);
					checkInNo = arrObj[0].toString();
					registrationNo = arrObj[1].toString();
					reservationNo = arrObj[2].toString();
					folioNo = arrObj[3].toString();
					billNo = "";
				}
			}
		}

		else if (objPaymentBean.getStrAgainst().equals("Folio-No")) {
			String sql = " select b.strCheckInNo,a.strRegistrationNo,b.strReservationNo,b.strFolioNo " + " from tblcheckinhd a,tblfoliohd b " + " where a.strCheckInNo=b.strCheckInNo and b.strFolioNo='" + objPaymentBean.getStrDocNo() + "' ";
			List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
			if (list.size() > 0) {
				for (int cnt = 0; cnt < list.size(); cnt++) {
					Object[] arrObj = (Object[]) list.get(cnt);
					checkInNo = arrObj[0].toString();
					registrationNo = arrObj[1].toString();
					reservationNo = arrObj[2].toString();
					folioNo = arrObj[3].toString();
					billNo = "";
				}
			}
		}

		else {
			String sql = " select a.strCheckInNo,a.strRegistrationNo,a.strReservationNo,a.strFolioNo " + " from tblbillhd a " + " where a.strBillNo='" + objPaymentBean.getStrDocNo() + "'";
			List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
			if (list.size() > 0) {
				for (int cnt = 0; cnt < list.size(); cnt++) {
					Object[] arrObj = (Object[]) list.get(cnt);
					checkInNo = arrObj[0].toString();
					registrationNo = arrObj[1].toString();
					reservationNo = arrObj[2].toString();
					folioNo = arrObj[3].toString();
					billNo = objPaymentBean.getStrDocNo();
					clsBillHdModel objBillHdModel = objBillService.funLoadBill(billNo, clientCode);
					objBillHdModel.setStrBillSettled("Y");
					objBillService.funAddUpdateBillHd(objBillHdModel);
				}
			}
		}

		objModel.setDteReceiptDate(PMSDate);
		objModel.setStrCheckInNo(checkInNo);
		objModel.setStrBillNo(billNo);
		objModel.setStrRegistrationNo(registrationNo);
		objModel.setStrReservationNo(reservationNo);
		objModel.setStrFolioNo(folioNo);
		objModel.setStrUserEdited(userCode);
		objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrClientCode(clientCode);
		objModel.setStrFlagOfAdvAmt(objPaymentBean.getStrFlagOfAdvAmt());

		List<clsPMSPaymentReceiptDtl> listPaymentReceiptDtlModel = new ArrayList<clsPMSPaymentReceiptDtl>();
		clsPMSPaymentReceiptDtl objPaymentReceiptDtlModel = new clsPMSPaymentReceiptDtl();
		objPaymentReceiptDtlModel.setDblSettlementAmt(objPaymentBean.getDblReceiptAmt());

		String[] newDate = objPaymentBean.getDteExpiryDate().split("-");
		if (newDate[0].length() >= 3) {
			objPaymentReceiptDtlModel.setDteExpiryDate(objPaymentBean.getDteExpiryDate());
		} else {
			objPaymentReceiptDtlModel.setDteExpiryDate(objGlobal.funGetDate("yyyy/MM/dd", objPaymentBean.getDteExpiryDate()));
		}
		objPaymentReceiptDtlModel.setStrCardNo(objPaymentBean.getStrCardNo());
		objPaymentReceiptDtlModel.setStrRemarks(objPaymentBean.getStrRemarks());
		objPaymentReceiptDtlModel.setStrSettlementCode(objPaymentBean.getStrSettlementCode());
		listPaymentReceiptDtlModel.add(objPaymentReceiptDtlModel);

		objModel.setListPaymentRecieptDtlModel(listPaymentReceiptDtlModel);
		return objModel;
	}
}
