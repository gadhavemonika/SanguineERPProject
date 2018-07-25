package com.sanguine.webpms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsGuestMasterBean;
import com.sanguine.webpms.bean.clsWalkinBean;
import com.sanguine.webpms.bean.clsWalkinDtlBean;
import com.sanguine.webpms.dao.clsGuestMasterDao;
import com.sanguine.webpms.dao.clsWalkinDao;
import com.sanguine.webpms.model.clsGuestMasterHdModel;
import com.sanguine.webpms.model.clsReservationRoomRateModelDtl;
import com.sanguine.webpms.model.clsRoomPackageDtl;
import com.sanguine.webpms.model.clsWalkinDtl;
import com.sanguine.webpms.model.clsWalkinHdModel;
import com.sanguine.webpms.model.clsWalkinRoomRateDtlModel;

@Service("clsWalkinService")
public class clsWalkinServiceImpl implements clsWalkinService {
	@Autowired
	private clsWalkinDao objWalkinDao;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	clsWalkinHdModel objHdModel;

	@Autowired
	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsGuestMasterService objGuestMasterService;

	@Autowired
	private clsGuestMasterDao objGuestMasterDao;

	@Override
	public void funAddUpdateWalkinHd(clsWalkinHdModel objHdModel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void funAddUpdateWalkinDtl(clsWalkinDtl objDtlModel) {
		// TODO Auto-generated method stub

	}

	@Override
	public clsWalkinHdModel funPrepareWalkinModel(clsWalkinBean objWalkinBean, String clientCode, HttpServletRequest request, String userCode) {

		clsWalkinHdModel objModel = new clsWalkinHdModel();
		Map<String, clsWalkinDtl> hmWalkinDtl = new HashMap<String, clsWalkinDtl>();
		String transaDate = "";

		// Insert or update Walkin HD Details

		if (objWalkinBean.getStrWalkinNo().isEmpty()) // New Entry
		{
			transaDate = objGlobal.funGetCurrentDateTime("dd-MM-yyyy").split(" ")[0];
			String documentNo = objGlobal.funGeneratePMSDocumentCode("frmWalkin", transaDate, request);
			objModel.setStrWalkinNo(documentNo);
			objModel.setDteWalkinDate(objGlobal.funGetDate("yyyy/MM/dd", transaDate));
			objModel.setStrUserCreated(userCode);
			objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		} else // Update
		{
			objModel.setStrWalkinNo(objWalkinBean.getStrWalkinNo());
		}

		objModel.setStrRoomNo(objWalkinBean.getStrRoomNo());
		objModel.setStrExtraBedCode(objWalkinBean.getStrExtraBedCode());
		objModel.setIntNoOfAdults(objWalkinBean.getIntNoOfAdults());
		objModel.setIntNoOfChild(objWalkinBean.getIntNoOfChild());
		objModel.setStrCorporateCode(objWalkinBean.getStrCorporateCode());
		objModel.setStrAgentCode(objWalkinBean.getStrAgentCode());
		objModel.setStrBookerCode(objWalkinBean.getStrBookerCode());
		objModel.setIntNoOfNights((int) objWalkinBean.getIntNoOfNights());
		objModel.setStrRemarks(objWalkinBean.getStrRemarks());
		objModel.setTmeWalkinTime(objWalkinBean.getTmeWalkinTime());
		objModel.setTmeCheckOutTime(objWalkinBean.getTmeCheckOutTime());
		objModel.setStrIncomeHeadCode(objWalkinBean.getStrIncomeHeadCode());
		
		if (null == objWalkinBean.getDteWalkinDate()) {
			objModel.setDteWalkinDate("1900-01-01");
		} else {
			String[] walkinDate = objWalkinBean.getDteWalkinDate().split("-");
			if (walkinDate[0].length() >= 3) {
				objModel.setDteWalkinDate(objWalkinBean.getDteWalkinDate());
			} else {
				objModel.setDteWalkinDate(objGlobal.funGetDate("yyyy/MM/dd", objWalkinBean.getDteWalkinDate()));
			}
		}

		if (null == objWalkinBean.getDteCheckOutDate()) {
			objModel.setDteCheckOutDate("1900-01-01");
		} else {
			String[] checkoutDate = objWalkinBean.getDteCheckOutDate().split("-");
			if (checkoutDate[0].length() >= 3) {
				objModel.setDteCheckOutDate(objWalkinBean.getDteCheckOutDate());
			} else {
				objModel.setDteCheckOutDate(objGlobal.funGetDate("yyyy/MM/dd", objWalkinBean.getDteCheckOutDate()));
			}
		}

		objModel.setStrUserEdited(userCode);
		objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrClientCode(clientCode);
		
		List<clsWalkinRoomRateDtlModel> listRommRate = new ArrayList<clsWalkinRoomRateDtlModel>();
		if(null!=objWalkinBean.getListWalkinRoomRateDtl()){
		for (clsWalkinRoomRateDtlModel objRommDtlBean : objWalkinBean.getListWalkinRoomRateDtl()) {
		
			String date=objRommDtlBean.getDtDate();
			if(date.split("-")[0].toString().length()<3)
			{	
			 objRommDtlBean.setDtDate(objGlobal.funGetDate("yyyy-MM-dd",date));
			}
			listRommRate.add(objRommDtlBean);
		}
		}
		
		objModel.setListWalkinRoomRateDtlModel(listRommRate);

		// Insert or update Guest Master Details
		/*
		 * String guestCode=objWalkinBean.getStrGuestCode();
		 * 
		 * clsGuestMasterBean objGuestMasterBean = new clsGuestMasterBean();
		 * objGuestMasterBean.setStrGuestCode(guestCode);
		 * objGuestMasterBean.setStrGuestPrefix (objWalkinBean.getStrGuestPrefix());
		 * objGuestMasterBean.setStrFirstName(objWalkinBean.getStrFirstName());
		 * objGuestMasterBean .setStrMiddleName(objWalkinBean.getStrMiddleName());
		 * objGuestMasterBean.setStrLastName(objWalkinBean.getStrLastName());
		 * objGuestMasterBean.setStrGender(objWalkinBean.getStrGender());
		 * objGuestMasterBean .setStrDesignation(objWalkinBean.getStrDesignation());
		 * objGuestMasterBean
		 * .setDteDOB(objGlobal.funGetDate("yyyy/MM/dd",objWalkinBean
		 * .getDteDOB()));
		 * objGuestMasterBean.setStrAddress(objWalkinBean.getStrAddress());
		 * objGuestMasterBean.setStrCity(objWalkinBean.getStrCity());
		 * objGuestMasterBean.setStrState(objWalkinBean.getStrState());
		 * objGuestMasterBean.setStrCountry(objWalkinBean.getStrCountry());
		 * objGuestMasterBean .setStrNationality(objWalkinBean.getStrNationality());
		 * objGuestMasterBean.setIntPinCode(objWalkinBean.getIntPinCode());
		 * objGuestMasterBean.setIntMobileNo(objWalkinBean.getIntMobileNo());
		 * objGuestMasterBean.setIntFaxNo(objWalkinBean.getIntFaxNo());
		 * objGuestMasterBean.setStrEmailId(objWalkinBean.getStrEmailId());
		 * objGuestMasterBean.setStrPANNo(objWalkinBean.getStrPANNo());
		 * objGuestMasterBean .setStrArrivalFrom(objWalkinBean.getStrArrivalFrom());
		 * objGuestMasterBean
		 * .setStrProceedingTo(objWalkinBean.getStrProceedingTo());
		 * objGuestMasterBean.setStrStatus(objWalkinBean.getStrStatus());
		 * objGuestMasterBean
		 * .setStrVisitingType(objWalkinBean.getStrVisitingType());
		 * objGuestMasterBean.setStrUserCreated(userCode);
		 * objGuestMasterBean.setStrUserEdited(userCode);
		 * objGuestMasterBean.setDteDateCreated
		 * (objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		 * objGuestMasterBean.setDteDateEdited
		 * (objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		 * objGuestMasterBean.setStrProceedingTo
		 * (objWalkinBean.getStrProceedingTo());
		 * objGuestMasterBean.setDtePassportExpiryDate
		 * (objGlobal.funGetDate("yyyy/MM/dd"
		 * ,objWalkinBean.getDtePassportExpiryDate()));
		 * objGuestMasterBean.setDtePassportIssueDate
		 * (objGlobal.funGetDate("yyyy/MM/dd"
		 * ,objWalkinBean.getDtePassportIssueDate()));
		 * objGuestMasterBean.setStrCorporate("N");
		 * objGuestMasterBean.setStrPassportNo (objWalkinBean.getStrPassportNo());
		 * 
		 * clsGuestMasterHdModel objGuestMasterModel
		 * =objGuestMasterService.funPrepareGuestModel(objGuestMasterBean,
		 * clientCode, userCode);
		 * objModel.setStrGuestCode(objGuestMasterModel.getStrGuestCode());
		 * objGuestMasterDao.funAddUpdateGuestMaster(objGuestMasterModel);
		 */

		
		
		// Insert or update Walkin Details Data
		List<clsWalkinDtl> listWalkinDtlModel = new ArrayList<clsWalkinDtl>();
		for (clsWalkinDtlBean objWalkinDetails : objWalkinBean.getListWalkinDetailsBean()) {
			if (null != objWalkinDetails.getStrGuestCode())
			{
				// Fill Guest Master Details
				clsGuestMasterBean objGuestMasterBean = new clsGuestMasterBean();
				objGuestMasterBean.setStrGuestCode(objWalkinDetails.getStrGuestCode());
				objGuestMasterBean.setStrGuestPrefix("");
				objGuestMasterBean.setStrFirstName(objWalkinDetails.getStrGuestFirstName());
				objGuestMasterBean.setStrMiddleName(objWalkinDetails.getStrGuestMiddleName());
				objGuestMasterBean.setStrLastName(objWalkinDetails.getStrGuestLastName());
				objGuestMasterBean.setIntFaxNo(0);
				objGuestMasterBean.setIntPinCode(0);
				objGuestMasterBean.setDteDOB("01-01-1900");
				objGuestMasterBean.setIntMobileNo(objWalkinDetails.getLngMobileNo());
				objGuestMasterBean.setStrAddress(objWalkinDetails.getStrAddress());
				clsGuestMasterHdModel objGuestMasterModel = objGuestMasterService.funPrepareGuestModel(objGuestMasterBean, clientCode, userCode);
				objGuestMasterDao.funAddUpdateGuestMaster(objGuestMasterModel);

				clsWalkinDtl objWalkinDtlModel = new clsWalkinDtl();
				objWalkinDtlModel.setStrGuestCode(objGuestMasterModel.getStrGuestCode());
				objWalkinDtlModel.setStrRoomNo(objWalkinDetails.getStrRoomNo());
				objWalkinDtlModel.setStrRoomType(objWalkinDetails.getStrRoomType());
				objWalkinDtlModel.setIntNoOfAdults(objWalkinDetails.getIntNoOfAdults());
				objWalkinDtlModel.setIntNoOfChild(objWalkinDetails.getIntNoOfChild());
				objWalkinDtlModel.setStrExtraBedCode(objWalkinDetails.getStrExtraBedCode());
				objWalkinDtlModel.setStrRemark(objWalkinDetails.getStrRemark());

				listWalkinDtlModel.add(objWalkinDtlModel);
			}
		}
		objModel.setListWalkinDtlModel(listWalkinDtlModel);

		return objModel;
	}
	
	public List<clsRoomPackageDtl> funGetWalkinIncomeList(String walkInNo, String clientCode) {
		return objWalkinDao.funGetWalkinIncomeList(walkInNo, clientCode);
	}

}
