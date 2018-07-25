package com.sanguine.webpms.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.SessionFactory;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.base.service.intfBaseService;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsPropertyMaster;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsPropertyMasterService;
import com.sanguine.webpms.bean.clsGuestMasterBean;
import com.sanguine.webpms.bean.clsReservationBean;
import com.sanguine.webpms.bean.clsReservationDetailsBean;
import com.sanguine.webpms.dao.clsExtraBedMasterDao;
import com.sanguine.webpms.dao.clsGuestMasterDao;
import com.sanguine.webpms.dao.clsIncomeHeadMasterDao;
import com.sanguine.webpms.dao.clsRoomTypeMasterDao;
import com.sanguine.webpms.dao.clsWebPMSDBUtilityDao;

import com.sanguine.webpms.model.clsExtraBedMasterModel;
import com.sanguine.webpms.model.clsGuestMasterHdModel;
import com.sanguine.webpms.model.clsPackageMasterDtl;
import com.sanguine.webpms.model.clsPackageMasterHdModel;
import com.sanguine.webpms.model.clsPropertySetupHdModel;
import com.sanguine.webpms.model.clsReservationDtlModel;
import com.sanguine.webpms.model.clsReservationHdModel;
import com.sanguine.webpms.model.clsReservationRoomRateModelDtl;
import com.sanguine.webpms.model.clsRoomMasterModel;
import com.sanguine.webpms.model.clsRoomPackageDtl;
import com.sanguine.webpms.model.clsRoomTypeMasterModel;
import com.sanguine.webpms.service.clsGuestMasterService;
import com.sanguine.webpms.service.clsPropertySetupService;
import com.sanguine.webpms.service.clsReservationService;
import com.sanguine.webpms.service.clsRoomMasterService;

@Controller
public class clsReservationController {

	@Autowired
	private clsReservationService objReservationService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsGuestMasterService objGuestMasterService;

	@Autowired
	private clsGuestMasterDao objGuestMasterDao;

	@Autowired
	private clsRoomMasterService objRoomMasterService;

	@Autowired
	private clsExtraBedMasterDao objExtraBedMasterDao;

	@Autowired
	private clsPropertySetupService objPropertySetupService;

	@Autowired
	private clsGuestMasterService objGuestService;

	@Autowired
	private clsPropertyMasterService objPropertyMasterService;

	@Autowired
	clsRoomMasterService objRoomMaster;
	
	@Autowired
	private clsIncomeHeadMasterDao objIncomeHeadMasterDao;
	
	@Autowired
	private clsRoomTypeMasterDao objRoomTypeMasterDao;
	
	@Autowired
	private clsWebPMSDBUtilityDao objWebPMSUtility;
	
	@Autowired
	private intfBaseService objBaseService;
	
	// Open Reservation
	@RequestMapping(value = "/frmReservation", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		String webStockDB=request.getSession().getAttribute("WebStockDB").toString();
		List listOfProperty = objGlobalFunctionsService.funGetList("select strPropertyName from "+webStockDB+".tblpropertymaster where strPropertyCode='" + propCode + "' ");
		model.put("listOfProperty", listOfProperty);

		model.put("urlHits", urlHits);
		
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		

//		clsPropertySetupHdModel objPropertySetupModel = objPropertySetupService.funGetPropertySetup(propCode, clientCode);
//		String noOfRoom = objPropertySetupModel.getStrRoomLimit();
//		
//		model.put("noOfRoom", noOfRoom);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmReservation_1", "command", new clsReservationBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmReservation", "command", new clsReservationBean());
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/frmReservation1", method = RequestMethod.GET)
	public ModelAndView funOpenForm1(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		String reservationNo = request.getParameter("docCode").toString();
		String webStockDB=request.getSession().getAttribute("WebStockDB").toString();

		try {
			urlHits = request.getParameter("saddr").toString();

		} catch (NullPointerException e) {
			urlHits = "1";
		}

		List listOfProperty = objGlobalFunctionsService.funGetList("select strPropertyName from "+webStockDB+".tblpropertymaster");
		model.put("listOfProperty", listOfProperty);

		model.put("urlHits", urlHits);

		request.getSession().setAttribute("ResNo", reservationNo);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmReservation_1", "command", new clsReservationBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmReservation", "command", new clsReservationBean());
		} else {
			return null;
		}
	}

	// Load Header Table Data On Form
	@RequestMapping(value = "/loadReservation", method = RequestMethod.GET)
	public @ResponseBody clsReservationBean funLoadHdData(HttpServletRequest request) {

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		String reservationNo = request.getParameter("docCode").toString();
		clsReservationHdModel objReservationModel = objReservationService.funGetReservationList(reservationNo, clientCode, propCode);

		clsReservationBean objBean = new clsReservationBean();
		objBean.setStrReservationNo(objReservationModel.getStrReservationNo());

		objBean.setStrRemarks(objReservationModel.getStrRemarks());
		objBean.setStrAgentCode(objReservationModel.getStrAgentCode());
		objBean.setStrBillingInstCode(objReservationModel.getStrBillingInstCode());
		objBean.setStrBookerCode(objReservationModel.getStrBookerCode());

		objBean.setStrBookingTypeCode(objReservationModel.getStrBookingTypeCode());
		objBean.setStrBusinessSourceCode(objReservationModel.getStrBusinessSourceCode());
		objBean.setStrCancelReservation(objReservationModel.getStrCancelReservation());
		objBean.setStrContactPerson(objReservationModel.getStrContactPerson());

		objBean.setStrCorporateCode(objReservationModel.getStrCorporateCode());
		objBean.setStrEmailId(objReservationModel.getStrEmailId());
		objBean.setStrPropertyCode(objReservationModel.getStrPropertyCode());

		objBean.setStrPayeeGuestCode(objReservationModel.getStrGuestcode());
		/*
		 * objBean.setStrGuestCode(objReservationModel.getStrGuestCode()); String
		 * sql=
		 * "select strGuestPrefix,strFirstName,strMiddleName,strLastName from tblguestmaster where strGuestCode='"
		 * +objReservationModel.getStrGuestCode()+"'"; List
		 * list=objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		 * 
		 * if(list.size()>0) { Object[] arrObjGuestInfo=(Object[])list.get(0);
		 * objBean.setStrGuestPrefix(arrObjGuestInfo[0].toString());
		 * objBean.setStrFirstName(arrObjGuestInfo[1].toString());
		 * objBean.setStrMiddleName(arrObjGuestInfo[2].toString());
		 * objBean.setStrLastName(arrObjGuestInfo[3].toString()); } else {
		 * objBean.setStrGuestPrefix(""); objBean.setStrFirstName("");
		 * objBean.setStrMiddleName(""); objBean.setStrLastName(""); }
		 */

		objBean.setStrRemarks(objReservationModel.getStrRemarks());
		objBean.setIntNoOfNights(objReservationModel.getIntNoOfNights());
		objBean.setIntNoRoomsBooked(Integer.parseInt(objReservationModel.getStrNoRoomsBooked()));
		objBean.setDteArrivalDate(objGlobal.funGetDate("yyyy/MM/dd", objReservationModel.getDteArrivalDate()));

		objBean.setDteDepartureDate(objGlobal.funGetDate("yyyy/MM/dd", objReservationModel.getDteDepartureDate()));
		objBean.setDteCancelDate(objGlobal.funGetDate("yyyy/MM/dd", objReservationModel.getDteCancelDate()));
		objBean.setDteConfirmDate(objGlobal.funGetDate("yyyy/MM/dd", objReservationModel.getDteConfirmDate()));
		objBean.setTmeArrivalTime(objReservationModel.getTmeArrivalTime());
		objBean.setTmeDepartureTime(objReservationModel.getTmeDepartureTime());
		objBean.setStrOTANo(objReservationModel.getStrOTANo());
		objBean.setStrMarketSourceCode(objReservationModel.getStrMarketSourceCode());
		objBean.setStrIncomeHeadCode(objReservationModel.getStrIncomeHeadCode());
		objBean.setTmePickUpTime(objReservationModel.getTmePickUpTime());
		objBean.setTmeDropTime(objReservationModel.getTmeDropTime());
		
		clsRoomMasterModel objRoomMasterModel = objRoomMasterService.funGetRoomMaster(objReservationModel.getStrRoomNo(), clientCode);
		objBean.setStrRoomNo(objReservationModel.getStrRoomNo());
		if (objRoomMasterModel != null) {
			objBean.setStrRoomDesc(objRoomMasterModel.getStrRoomDesc());
		} else {
			objBean.setStrRoomDesc("");
		}
		

		if (!objReservationModel.getStrExtraBedCode().isEmpty()) {
			List listExtraBedData = objExtraBedMasterDao.funGetExtraBedMaster(objReservationModel.getStrExtraBedCode(), clientCode);
			clsExtraBedMasterModel objExtraBedMasterModel = (clsExtraBedMasterModel) listExtraBedData.get(0);
			objBean.setStrExtraBedCode(objReservationModel.getStrExtraBedCode());
			objBean.setStrExtraBedDesc(objExtraBedMasterModel.getStrExtraBedTypeDesc());
		} else {
			objBean.setStrExtraBedCode("");
			objBean.setStrExtraBedDesc("");
		}
		objBean.setIntNoOfAdults(objReservationModel.getIntNoOfAdults());
		objBean.setIntNoOfChild(objReservationModel.getIntNoOfChild());

		String sql = "";
		List<clsReservationDetailsBean> listResDetailsBean = new ArrayList<clsReservationDetailsBean>();
		for (clsReservationDtlModel objResDtlModel : objReservationModel.getListReservationDtlModel()) {
			clsReservationDetailsBean objReservationDtlBean = new clsReservationDetailsBean();
			objReservationDtlBean.setStrGuestCode(objResDtlModel.getStrGuestCode());
			objReservationDtlBean.setStrPayee(objResDtlModel.getStrPayee());

			sql = "select strFirstName,strMiddleName,strLastName,lngMobileNo,strAddress from tblguestmaster " + " where strGuestCode='" + objResDtlModel.getStrGuestCode() + "' and strClientCode='" + clientCode + "' ";
			List listGuestMaster = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
			for (int cnt = 0; cnt < listGuestMaster.size(); cnt++) {
				Object[] arrObjGuest = (Object[]) listGuestMaster.get(cnt);
				String guestName = arrObjGuest[0] + " " + arrObjGuest[1] + " " + arrObjGuest[2];
				objReservationDtlBean.setStrGuestName(guestName);
				objReservationDtlBean.setLngMobileNo(Long.parseLong(arrObjGuest[3].toString()));
				objReservationDtlBean.setStrAddress(arrObjGuest[4].toString());
			}

			objReservationDtlBean.setStrGuestCode(objResDtlModel.getStrGuestCode());

			clsRoomMasterModel objRoomMasterModel1 = objRoomMasterService.funGetRoomMaster(objResDtlModel.getStrRoomNo(), clientCode);
			if (objRoomMasterModel1 != null) {
				objReservationDtlBean.setStrRoomNo(objResDtlModel.getStrRoomNo());
				objReservationDtlBean.setStrRoomDesc(objRoomMasterModel1.getStrRoomDesc());
			} else {
				objReservationDtlBean.setStrRoomNo("");
				objReservationDtlBean.setStrRoomDesc("");
			}

			if (!objResDtlModel.getStrExtraBedCode().isEmpty()) {
				List listExtraBedData = objExtraBedMasterDao.funGetExtraBedMaster(objResDtlModel.getStrExtraBedCode(), clientCode);
				clsExtraBedMasterModel objExtraBedMasterModel = (clsExtraBedMasterModel) listExtraBedData.get(0);
				objReservationDtlBean.setStrExtraBedCode(objResDtlModel.getStrExtraBedCode());
				objReservationDtlBean.setStrExtraBedDesc(objExtraBedMasterModel.getStrExtraBedTypeDesc());
			} else {
				objReservationDtlBean.setStrExtraBedCode("");
				objReservationDtlBean.setStrExtraBedDesc("");
			}

			objReservationDtlBean.setStrRoomType(objResDtlModel.getStrRoomType());
			listResDetailsBean.add(objReservationDtlBean);
		}
		objBean.setListReservationDetailsBean(listResDetailsBean);
	
		objBean.setListReservationRoomRateDtl(objReservationModel.getListReservationRoomRateDtl());
		
		objBean.setListRoomPackageDtl(objReservationService.funGetReservationIncomeList(reservationNo, clientCode));
		for (clsRoomPackageDtl objPkgDtlModel : objBean.getListRoomPackageDtl()) 
		{
			objBean.setStrPackageCode(objPkgDtlModel.getStrPackageCode());
			objBean.setStrPackageName(objPkgDtlModel.getStrPackageName());
			break;
		}
			
		return objBean;
	}

	// Save or Update Reservation
	@RequestMapping(value = "/saveReservation", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsReservationBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			String startDate = req.getSession().getAttribute("startDate").toString();
			String PMSDate = objGlobal.funGetDate("yyyy-MM-dd", req.getSession().getAttribute("PMSDate").toString());
			Map<Long, String> hmGuestMbWithCode = new HashMap<Long, String>();
			List<clsReservationDetailsBean> listResDtlBean = objBean.getListReservationDetailsBean();

			for (clsReservationDetailsBean objResDtlBean : listResDtlBean) 
			{
				if (null != objResDtlBean.getStrGuestCode())
				{
					clsGuestMasterBean objGuestMasterBean = new clsGuestMasterBean();
					objGuestMasterBean.setStrGuestCode(objResDtlBean.getStrGuestCode());
					objGuestMasterBean.setStrGuestPrefix("");
					String[] arrSpGuest = objResDtlBean.getStrGuestName().split(" ");
					objGuestMasterBean.setStrFirstName(arrSpGuest[0]);
					objGuestMasterBean.setStrMiddleName(objGlobal.funIfNull(arrSpGuest[1], "", arrSpGuest[1]));
					if(arrSpGuest.length==2)
					{
						objGuestMasterBean.setStrLastName("");
					}
					else
					{
						objGuestMasterBean.setStrLastName(objGlobal.funIfNull(arrSpGuest[2], "", arrSpGuest[2]));
					}
					
					objGuestMasterBean.setIntFaxNo(0);
					objGuestMasterBean.setIntPinCode(0);
					objGuestMasterBean.setDteDOB("01-01-1900");
					objGuestMasterBean.setIntMobileNo(objResDtlBean.getLngMobileNo());
					objGuestMasterBean.setStrAddress(objResDtlBean.getStrAddress());

					///////
					clsGuestMasterHdModel objGuestMasterModel = objGuestMasterService.funPrepareGuestModel(objGuestMasterBean, clientCode, userCode);
					objGuestMasterDao.funAddUpdateGuestMaster(objGuestMasterModel);
					hmGuestMbWithCode.put(objResDtlBean.getLngMobileNo(), objGuestMasterModel.getStrGuestCode());	
				}
			}

			List<clsReservationDetailsBean> listReservationDtlBean = new ArrayList<clsReservationDetailsBean>();
			for (clsReservationDetailsBean objResDtlBean : objBean.getListReservationDetailsBean()) 
			{
				if (null != objResDtlBean.getStrGuestCode())
				{
					if (null != hmGuestMbWithCode.get(objResDtlBean.getLngMobileNo())) 
					{
						objResDtlBean.setStrGuestCode(hmGuestMbWithCode.get(objResDtlBean.getLngMobileNo()));
					}
					listReservationDtlBean.add(objResDtlBean);	
				}
			}
			objBean.setListReservationDetailsBean(listReservationDtlBean);
			List<clsReservationRoomRateModelDtl> listRommRate = new ArrayList<clsReservationRoomRateModelDtl>();
			if(null!=objBean.getListReservationRoomRateDtl())
			{
			for (clsReservationRoomRateModelDtl objRommDtlBean : objBean.getListReservationRoomRateDtl()) 
			{
			
				String date=objRommDtlBean.getDtDate();
				if(date.split("-")[0].toString().length()<3)
				{	
				 objRommDtlBean.setDtDate(objGlobal.funGetDate("yyyy-MM-dd",date));
				}
				listRommRate.add(objRommDtlBean);
			}
		    }
			

			clsReservationHdModel objHdModel = funPrepareHdModel(objBean, userCode, clientCode, req, propCode);
			objHdModel.setListReservationRoomRateDtl(listRommRate);
			String sql = "select strBookingTypeDesc from tblbookingtype " + " where strBookingTypeCode='" + objBean.getStrBookingTypeCode() + "' and strClientCode='" + clientCode + "' ";
			List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
			String bookingTypeDesc = (String) list.get(0);
			
			objReservationService.funAddUpdateReservationHd(objHdModel, bookingTypeDesc);	
//			funSendSMSReservation(objHdModel.getStrReservationNo(), clientCode, propCode);
			
			
			if(null!=objBean.getListRoomPackageDtl() && objBean.getListRoomPackageDtl().size()>0  )
			{
				long lastNo=0;
				boolean flgData=false;
				String packageCode="",insertSql="";
				clsPackageMasterHdModel objPkgHdModel=null;
				if (objBean.getStrPackageCode().trim().length() == 0) 
				{
					lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblpackagemasterhd", "PackageMaster", "strPackageCode", clientCode);
					packageCode = "PK" + String.format("%06d", lastNo);
				} 
				else
				{
					packageCode=objBean.getStrPackageCode();
				}
				objPkgHdModel=new clsPackageMasterHdModel();
				objPkgHdModel.setStrPackageCode(packageCode);
				objPkgHdModel.setStrPackageName(objBean.getStrPackageName());
				objPkgHdModel.setDblPackageAmt(Double.valueOf(objBean.getStrTotalPackageAmt()));
				objPkgHdModel.setStrUserCreated(userCode);
				objPkgHdModel.setStrUserEdited(userCode);
				objPkgHdModel.setDteDateCreated(PMSDate);
				objPkgHdModel.setDteDateEdited(PMSDate);
				objPkgHdModel.setStrClientCode(clientCode);
				objWebPMSUtility.funExecuteUpdate("delete from tblroompackagedtl where strReservationNo='"+objHdModel.getStrReservationNo()+"' and strClientCode='"+clientCode+"'", "sql");	
				List<clsPackageMasterDtl> listPkgDtlModel = new ArrayList<clsPackageMasterDtl>();
				String insertPkgDtl= "INSERT INTO `tblroompackagedtl` (`strWalkinNo`, `strReservationNo`,"
						+ " `strCheckInNo`, `strPackageCode`, `strIncomeHeadCode`, `dblIncomeHeadAmt`, "
						+ "`strType`,`strRoomNo`,`strClientCode`) VALUES";
				for (clsRoomPackageDtl objPkgDtlBean : objBean.getListRoomPackageDtl()) 
				{
					insertSql+=",('','"+objHdModel.getStrReservationNo()+"','' "
							+ ",'"+packageCode+"','"+objPkgDtlBean.getStrIncomeHeadCode()+"','"+Double.valueOf(objPkgDtlBean.getDblIncomeHeadAmt())+"'"
							+ ",'IncomeHead','','"+clientCode+"')";
						flgData=true;
						
					clsPackageMasterDtl objPkdDtl=new clsPackageMasterDtl();
					objPkdDtl.setStrIncomeHeadCode(objPkgDtlBean.getStrIncomeHeadCode());
					objPkdDtl.setDblAmt(Double.valueOf(objPkgDtlBean.getDblIncomeHeadAmt()));
					listPkgDtlModel.add(objPkdDtl);
				}
				
				
				if(null!=objBean.getListReservationRoomRateDtl())
				{
				for (clsReservationRoomRateModelDtl objRommDtlBean : objBean.getListReservationRoomRateDtl()) 
				{
				     insertSql+=",('','"+objHdModel.getStrReservationNo()+"','' "
							+ ",'"+packageCode+"','','"+objRommDtlBean.getDblRoomRate()+"' "
							+ ",'RoomTariff','"+objRommDtlBean.getStrRoomType()+"','"+clientCode+"')";
				}
			    }
				if(flgData)
				{
					insertSql=insertSql.substring(1,insertSql.length());
					insertPkgDtl+=" "+insertSql;
					objWebPMSUtility.funExecuteUpdate(insertPkgDtl, "sql");
					objPkgHdModel.setListPackageDtl(listPkgDtlModel);
					try {
						objBaseService.funSaveForPMS(objPkgHdModel);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
		    }
			
			
			
			///Change for saving changed roomtype details....
			if(null!=objBean.getListReservationDetailsBean())
			{
				for (clsReservationDetailsBean objRommDtlBean : objBean.getListReservationDetailsBean()) 
				{
					String date="",oldRoomType="";
					boolean isFound=false;
					sql = " select a.strDocNo,a.strRoomType,a.dteToDate from tblchangedroomtypedtl a where a.strDocNo='"+objHdModel.getStrReservationNo()+"' "
							+ " and a.strClientCode='"+clientCode+"'  and a.strGuestCode='"+objRommDtlBean.getStrGuestCode()+"' ";
					list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
					if(list.size()>0)
					{
						for (int cnt = 0; cnt < list.size(); cnt++) {
							Object[] arrObjRoom = (Object[]) list.get(cnt);
							if(!arrObjRoom[1].toString().isEmpty())
							{
								date=arrObjRoom[2].toString();
								if(!objRommDtlBean.getStrRoomType().equals(arrObjRoom[1]))
								{
									if(arrObjRoom[2].toString().equalsIgnoreCase(""))
									{
										String []spDate=PMSDate.split("-");
										int changedDate=Integer.valueOf(spDate[2])-1;
										if(changedDate<10)
										{
											date=spDate[0]+"-"+spDate[1]+"-0"+changedDate;
										}
										else
										{
											date=spDate[0]+"-"+spDate[1]+"-"+changedDate;
										}
										isFound=true;
									}
									oldRoomType=(String) arrObjRoom[1];
								}
							}
						}	
					}
					
				if(oldRoomType.isEmpty())
				{
					oldRoomType=objRommDtlBean.getStrRoomType();	
				}
				
				objWebPMSUtility.funExecuteUpdate("delete from tblchangedroomtypedtl where strDocNo='"+objHdModel.getStrReservationNo()+"' and strRoomType='"+oldRoomType+"' and strGuestCode='"+objRommDtlBean.getStrGuestCode()+"' and strClientCode='"+clientCode+"'", "sql");	
				
				String insertChangedRoomType = "INSERT INTO `tblchangedroomtypedtl` (`strDocNo`, `strType`,"
						+ " `strRoomNo`, `strRoomType`, `strGuestCode`, `dteFromDate`, "
						+ " `dteToDate`, `strClientCode`) "
						+ " VALUES ('"+objHdModel.getStrReservationNo()+"','Reservation','','"+oldRoomType+"',"
						+ " '"+objRommDtlBean.getStrGuestCode()+"','"+PMSDate+"','"+date+"','"+clientCode+"') ";
				objWebPMSUtility.funExecuteUpdate(insertChangedRoomType, "sql");	
					
				if(isFound)
				{
					objWebPMSUtility.funExecuteUpdate("delete from tblchangedroomtypedtl where strDocNo='"+objHdModel.getStrReservationNo()+"' and strRoomType='"+objRommDtlBean.getStrRoomType()+"' and strGuestCode='"+objRommDtlBean.getStrGuestCode()+"' and strClientCode='"+clientCode+"'", "sql");
					insertChangedRoomType = "INSERT INTO `tblchangedroomtypedtl` (`strDocNo`, `strType`,"
							+ " `strRoomNo`, `strRoomType`, `strGuestCode`, `dteFromDate`, "
							+ " `dteToDate`, `strClientCode`) "
							+ " VALUES ('"+objHdModel.getStrReservationNo()+"','Reservation','','"+objRommDtlBean.getStrRoomType()+"',"
							+ " '"+objRommDtlBean.getStrGuestCode()+"','"+PMSDate+"','','"+clientCode+"') ";
					objWebPMSUtility.funExecuteUpdate(insertChangedRoomType, "sql");		
				}
				}
			}
		

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Reservation No : ".concat(objHdModel.getStrReservationNo()));
			req.getSession().setAttribute("AdvanceAmount", objHdModel.getStrReservationNo());
			return new ModelAndView("redirect:/frmReservation.html");
		} else {
			return new ModelAndView("frmReservation");
		}
	}

	// Convert bean to model function
	private clsReservationHdModel funPrepareHdModel(clsReservationBean objBean, String userCode, String clientCode, HttpServletRequest request, String propertyCode) {

		String PMSDate = objGlobal.funGetDate("yyyy-MM-dd", request.getSession().getAttribute("PMSDate").toString());
		clsReservationHdModel objModel = new clsReservationHdModel();
		if (objBean.getStrReservationNo().isEmpty()) // New Entry
		{
			/*
			 * Date dt=new Date(); String
			 * date=dt.getDate()+"-"+dt.getMonth()+"-"+dt.getYear();
			 */
			String date = objGlobal.funGetCurrentDate("DD-MM-yyyy");
			String documentNo = objGlobal.funGeneratePMSDocumentCode("frmReservation", date, request);
			objModel.setStrReservationNo(documentNo);
			objModel.setStrUserCreated(userCode);
			objModel.setDteDateCreated(PMSDate);
			objModel.setStrUserEdited(userCode);
			objModel.setDteDateEdited(PMSDate);
		} else // Update
		{
			objModel.setStrUserEdited(userCode);
			objModel.setDteDateEdited(PMSDate);
			objModel.setStrReservationNo(objBean.getStrReservationNo());
		}

		// objModel.setStrGuestCode(objBean.getStrGuestCode());

		objModel.setDteReservationDate(PMSDate);
		objModel.setStrPropertyCode(propertyCode);
		objModel.setStrBookingTypeCode(objGlobal.funIfNull(objBean.getStrBookingTypeCode(), "", objBean.getStrBookingTypeCode()));
		objModel.setStrCorporateCode(objGlobal.funIfNull(objBean.getStrCorporateCode(), "", objBean.getStrCorporateCode()));
		objModel.setDteArrivalDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteArrivalDate()));
		objModel.setDteDepartureDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteDepartureDate()));
		objModel.setTmeArrivalTime(objBean.getTmeArrivalTime());
		objModel.setTmeDepartureTime(objBean.getTmeDepartureTime());
		objModel.setIntNoOfNights(objBean.getIntNoOfNights());
		objModel.setStrContactPerson(objGlobal.funIfNull(objBean.getStrContactPerson(), "", objBean.getStrContactPerson()));
		objModel.setStrEmailId(objGlobal.funIfNull(objBean.getStrEmailId(), "", objBean.getStrEmailId()));
		objModel.setStrBookerCode(objGlobal.funIfNull(objBean.getStrBookerCode(), "", objBean.getStrBookerCode()));
		objModel.setStrBusinessSourceCode(objGlobal.funIfNull(objBean.getStrBusinessSourceCode(), "", objBean.getStrBusinessSourceCode()));
		objModel.setStrBillingInstCode(objGlobal.funIfNull(objBean.getStrBillingInstCode(), "", objBean.getStrBillingInstCode()));
		objModel.setStrAgentCode(objGlobal.funIfNull(objBean.getStrAgentCode(), "", objBean.getStrAgentCode()));
		objModel.setStrRemarks(objGlobal.funIfNull(objBean.getStrRemarks(), "", objBean.getStrRemarks()));
		objModel.setDteCancelDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteCancelDate()));
		objModel.setDteConfirmDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteConfirmDate()));
		objModel.setStrCancelReservation(objGlobal.funIfNull(objBean.getStrCancelReservation(), "N", objBean.getStrCancelReservation()));
		objModel.setStrClientCode(clientCode);
		objModel.setStrRoomNo(objBean.getStrRoomNo());
		objModel.setStrExtraBedCode(objBean.getStrExtraBedCode());
		objModel.setIntNoOfAdults(objBean.getIntNoOfAdults());
		objModel.setIntNoOfChild(objBean.getIntNoOfChild());
		objModel.setStrNoRoomsBooked(String.valueOf(objBean.getIntNoRoomsBooked()));
		objModel.setStrOTANo(objBean.getStrOTANo());
		objModel.setStrMarketSourceCode(objGlobal.funIfNull(objBean.getStrMarketSourceCode(), "", objBean.getStrMarketSourceCode()));
		objModel.setStrIncomeHeadCode(objBean.getStrIncomeHeadCode());
		objModel.setTmePickUpTime(objBean.getTmePickUpTime());
		objModel.setTmeDropTime(objBean.getTmeDropTime());
		

		List<clsReservationDtlModel> listResDtlModel = new ArrayList<clsReservationDtlModel>();

		for (clsReservationDetailsBean objResDtl : objBean.getListReservationDetailsBean()) {
			clsReservationDtlModel objResDtlModel = new clsReservationDtlModel();
			if (objBean.getStrPayeeGuestCode().equals(objResDtl.getStrGuestCode())) {
				objResDtlModel.setStrPayee("Y");
				objModel.setStrGuestcode(objBean.getStrPayeeGuestCode());
			} else {
				objResDtlModel.setStrPayee("N");
				objModel.setStrGuestcode("");
			}
			/*
			 * if(objModel.getStrGuestcode().equals("")||objModel.getStrGuestcode
			 * ().equals(null)) { objModel.setStrGuestcode(""); }
			 */
			objResDtlModel.setStrGuestCode(objResDtl.getStrGuestCode());
			objResDtlModel.setStrRoomNo(objResDtl.getStrRoomNo());
			objResDtlModel.setStrExtraBedCode(objResDtl.getStrExtraBedCode());
			objResDtlModel.setStrRoomType(objResDtl.getStrRoomType());
			objResDtlModel.setStrRemark(objResDtl.getStrRemark());
			objResDtlModel.setStrAddress(objResDtl.getStrAddress());
			

			listResDtlModel.add(objResDtlModel);
		}
		objModel.setListReservationDtlModel(listResDtlModel);

		return objModel;
	}

	private void funSendSMSReservation(String reservationNo, String clientCode, String propCode) {

		String strMobileNo = "";
		clsPropertySetupHdModel objSetup = objPropertySetupService.funGetPropertySetup(propCode, clientCode);

		clsReservationHdModel objModel = objReservationService.funGetReservationList(reservationNo, clientCode, propCode);

		String smsAPIUrl = objSetup.getStrSMSAPI();

		String smsContent = objSetup.getStrReservationSMSContent();

		if (!smsAPIUrl.equals("")) {
			if (smsContent.contains("%%CompanyName")) {
				List<clsCompanyMasterModel> listCompanyModel = objPropertySetupService.funGetListCompanyMasterModel(clientCode);
				smsContent = smsContent.replace("%%CompanyName", listCompanyModel.get(0).getStrCompanyName());
			}
			if (smsContent.contains("%%PropertyName")) {
				clsPropertyMaster objProperty = objPropertyMasterService.funGetProperty(propCode, clientCode);
				smsContent = smsContent.replace("%%PropertyName", objProperty.getPropertyName());
			}

			if (smsContent.contains("%%RNo")) {
				smsContent = smsContent.replace("%%RNo", reservationNo);
			}

			if (smsContent.contains("%%RDate")) {
				smsContent = smsContent.replace("%%RDate", objGlobal.funGetDate("dd-MM-yyyy", objModel.getDteReservationDate()));
			}

			if (smsContent.contains("%%NoNights")) {
				smsContent = smsContent.replace("%%NoNights", String.valueOf(objModel.getIntNoOfNights()));
			}

			List<clsReservationDtlModel> listReservationmodel = objModel.getListReservationDtlModel();

			if (listReservationmodel.size() > 0) {
				for (int i = 0; i < listReservationmodel.size(); i++) {
					clsReservationDtlModel objDtl = listReservationmodel.get(i);
					if (objDtl.getStrPayee().equals("Y")) {

						List list = objGuestService.funGetGuestMaster(objDtl.getStrGuestCode(), clientCode);
						clsGuestMasterHdModel objGuestModel = null;
						if (list.size() > 0) {
							objGuestModel = (clsGuestMasterHdModel) list.get(0);

						}
						if (smsContent.contains("%%GuestName")) {
							smsContent = smsContent.replace("%%GuestName", objGuestModel.getStrFirstName() + " " + objGuestModel.getStrMiddleName() + " " + objGuestModel.getStrLastName());
						}

						if (smsContent.contains("%%RoomNo")) {
							clsRoomMasterModel roomNo = objRoomMaster.funGetRoomMaster(objDtl.getStrRoomNo(), clientCode);
							smsContent = smsContent.replace("%%RoomNo", roomNo.getStrRoomDesc());
						}

						if (smsAPIUrl.contains("ReceiverNo")) {

							smsAPIUrl = smsAPIUrl.replace("ReceiverNo", String.valueOf(objGuestModel.getLngMobileNo()));
							strMobileNo = String.valueOf(objGuestModel.getLngMobileNo());
						}
						if (smsAPIUrl.contains("MsgContent")) {
							smsAPIUrl = smsAPIUrl.replace("MsgContent", smsContent);
							smsAPIUrl = smsAPIUrl.replace(" ", "%20");
						}

						URL url;
						HttpURLConnection uc = null;
						StringBuilder output = new StringBuilder();

						try {
							url = new URL(smsAPIUrl);
							uc = (HttpURLConnection) url.openConnection();
							if (strMobileNo.length() >= 10) {
								BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), Charset.forName("UTF-8")));
								String inputLine;
								while ((inputLine = in.readLine()) != null) {
									output.append(inputLine);
								}
								in.close();
							}

						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							uc.disconnect();
						}
					}

				}
			}

		}

	}
	
	// For Check Room Limt while making Reservation
	
	@RequestMapping(value = "/loadRoomLimit", method = RequestMethod.GET)
	public @ResponseBody int loadRoomLimit(String ArrivalDate, String DepartureDate, HttpServletRequest req) throws ParseException {
		
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		
		SimpleDateFormat formatter1=new SimpleDateFormat("dd-MM-yyyy");  
		Date arrvDate=formatter1.parse(ArrivalDate);
		Date deptDate=formatter1.parse(DepartureDate);
		
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		String arrDte= formatter.format(arrvDate);
		String dptDte= formatter.format(deptDate);
		
	
		clsPropertySetupHdModel objPropertySetupModel= objPropertySetupService.funGetPropertySetup(propertyCode, clientCode);
		
		clsReservationBean objReservationBean = new clsReservationBean();
		objReservationBean.setStrRoomLimit(objPropertySetupModel.getStrRoomLimit());
		
		String sql = "";
		int retunVal=0;
		sql = "select count(*) from tblreservationdtl a, tblreservationhd b " 
										+ " where b.dteArrivalDate between '" + arrDte + "' and '" + dptDte + "' and " 
										+ " b.dteDepartureDate between '" + arrDte + "' and '" + dptDte + "' and " 
										+ " a.strReservationNo=b.strReservationNo and a.strClientCode='" + clientCode + "' ";
		
		List listPropertySetup = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		
		
		String RoomLimit = objPropertySetupModel.getStrRoomLimit();
		if(RoomLimit==null || RoomLimit.equalsIgnoreCase(""))
		{
			retunVal = 1;
		}else{
		int countRoom = Integer.parseInt(RoomLimit);
		int listcnt = 0;
		if(listPropertySetup!=null){
			listcnt = Integer.parseInt(listPropertySetup.get(0).toString());
		}

		if(countRoom>listcnt){
			
			retunVal = 1;
			
		}
		}
		
		return retunVal;
		
	}
	
	
	
	@RequestMapping(value = "/loadRoomRate", method = RequestMethod.GET)
	public @ResponseBody List funLoadRoomRate(String arrivalDate, String departureDate,String roomDescList,String noOfNights, HttpServletRequest req) throws ParseException {
		
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String arrvDate=objGlobal.funGetDate("yyyy-MM-dd", arrivalDate);
		String deptDate=objGlobal.funGetDate("yyyy-MM-dd", departureDate);
		
		List returnList=new ArrayList();
		List listRoomCode = new ArrayList();

		String fromDate = req.getParameter("arrivalDate");
		String toDate = req.getParameter("departureDate");
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
//		DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
		LocalDate localArrvDate = dtf.parseLocalDate(arrvDate);
		
		LocalDate localDdeptDate = dtf.parseLocalDate(deptDate);
		clsPropertySetupHdModel objPropertySetupModel= objPropertySetupService.funGetPropertySetup(propertyCode, clientCode);
		String roomCode="";
		if(roomDescList!="")
		{
			String[] roomCodeList = roomDescList.split(",");
			for(int i=0; i<roomCodeList.length; i++)
			{
				roomCode = roomCodeList[i];
				int noOfDay=Integer.valueOf(noOfNights);
				if(!listRoomCode.contains(roomCode))
				{
		
//		clsRoomMasterModel objRoomMaster=objRoomMasterService.funGetRoomMaster(roomCode, clientCode);
//		String roomType="";
//		if(!objRoomMaster.getStrRoomTypeCode().equals(""))
//		{
//			roomType=objRoomMaster.getStrRoomTypeCode();
//		}
		List listRoomData = objRoomTypeMasterDao.funGetRoomTypeMaster(roomCode, clientCode);
		double roomRate=0.0;
		String roomTypedesc="";
		if(null!=listRoomData && listRoomData.size()>0)
		{
			clsRoomTypeMasterModel objRoomTypeMasterModel = (clsRoomTypeMasterModel) listRoomData.get(0);
			roomRate=objRoomTypeMasterModel.getDblRoomTerrif();
			roomTypedesc=objRoomTypeMasterModel.getStrRoomTypeDesc();
		}else{
			roomRate=0.0;
		}
		
		for (LocalDate date = localArrvDate;(date.isBefore(localDdeptDate)|| date.isEqual(localDdeptDate)); date = date.plusDays(1))
		{
			List listRoomRate=new ArrayList();
			listRoomRate.add(date.toString());
			listRoomRate.add(roomRate);
			listRoomRate.add(roomTypedesc);
			listRoomRate.add(roomCode);
			
			if(noOfDay==0)
			{
				returnList.add(listRoomRate);
				break;
			}
			else
			{
				returnList.add(listRoomRate);
				noOfDay=noOfDay-1;
				if(noOfDay==0)
				{
					break;
				}
			}
			
		}
		listRoomCode.add(roomCode);
		}
		
		}
			
		}
		return returnList;
	}
	
	
	@RequestMapping(value = "/loadRoomDesc", method = RequestMethod.GET)
	public @ResponseBody String funLoadRoomDesc(String roomNo, HttpServletRequest req) throws ParseException {
		
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		
		clsRoomMasterModel objRoomMasterModel = objRoomMasterService.funGetRoomMaster(roomNo, clientCode) ;
		String roomDesc = objRoomMasterModel.getStrRoomDesc();
		
		return roomDesc;
	}
	
	@RequestMapping(value = "/getGuestCode", method = RequestMethod.GET)
	public @ResponseBody clsGuestMasterHdModel fungetGuestCode(@RequestParam("guestCode") String guestCode, HttpServletRequest req){
		
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		
		clsGuestMasterBean objGuestMasterBean = new clsGuestMasterBean();
		clsGuestMasterHdModel objGuestMasterModel = new clsGuestMasterHdModel();
		
		long lastNo = 0;
		
		if (guestCode.length()==0) {
		
			lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblguestmaster", "GuestMaster", "strGuestCode", clientCode);
			String guestCode1 = "GT" + String.format("%06d", lastNo);
			objGuestMasterModel.setStrGuestCode(guestCode1);
			objGuestMasterModel.setStrUserCreated(userCode);
			objGuestMasterModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			objGuestMasterModel.setStrGuestCode(objGuestMasterBean.getStrGuestCode());
			objGuestMasterModel.setStrUserCreated(userCode);
			objGuestMasterModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		}
	
		return objGuestMasterModel;
	
	}
	
	
	// Load selected package data
		@RequestMapping(value = "/loadPackageMaster", method = RequestMethod.GET)
		public @ResponseBody clsPackageMasterHdModel funLoadPackageData(HttpServletRequest request) {

			String clientCode = request.getSession().getAttribute("clientCode").toString();
			String propCode = request.getSession().getAttribute("propertyCode").toString();
			String packageCode = request.getParameter("docCode").toString();
			clsPackageMasterHdModel objHdModel=new clsPackageMasterHdModel();
			List<clsPackageMasterDtl> listPkgDtl=new ArrayList<>();
			String packageName="";
			List list = objGlobalFunctionsService.funGetListModuleWise("SELECT a.strPackageCode,a.strPackageName,b.strIncomeHeadCode,b.dblAmt,c.strIncomeHeadDesc "
					+ " FROM tblpackagemasterhd a,tblpackagemasterdtl b,tblincomehead c "
					+ " where a.strPackageCode=b.strPackageCode and b.strIncomeHeadCode=c.strIncomeHeadCode "
					+ " and a.strPackageCode ='"+packageCode+"' and a.strClientCode='"+clientCode+"' "
					+ " group by b.strIncomeHeadCode", "sql");
			if(list.size()>0)
			{
				for (int cnt = 0; cnt < list.size(); cnt++) 
				{
					Object[] arrObj = (Object[]) list.get(cnt);
					clsPackageMasterDtl objPkg=new clsPackageMasterDtl();
					objPkg.setStrIncomeHeadCode(arrObj[2].toString()+"#"+arrObj[4].toString());
					objPkg.setDblAmt(Double.valueOf(arrObj[3].toString()));
					listPkgDtl.add(objPkg);
					packageName=arrObj[1].toString();
				}
			}	
			objHdModel.setStrPackageCode(packageCode);
			objHdModel.setStrPackageName(packageName);
			objHdModel.setListPackageDtl(listPkgDtl);
			return objHdModel;
		}
}
