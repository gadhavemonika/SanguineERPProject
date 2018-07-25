package com.sanguine.webpms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sanguine.base.service.intfBaseService;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsPropertyMaster;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsPropertyMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webpms.bean.clsCheckInBean;
import com.sanguine.webpms.bean.clsCheckInDetailsBean;
import com.sanguine.webpms.bean.clsFolioHdBean;
import com.sanguine.webpms.bean.clsGuestMasterBean;
import com.sanguine.webpms.bean.clsReservationDetailsBean;
import com.sanguine.webpms.dao.clsExtraBedMasterDao;
import com.sanguine.webpms.dao.clsGuestMasterDao;
import com.sanguine.webpms.dao.clsWalkinDao;
import com.sanguine.webpms.dao.clsWebPMSDBUtilityDao;

import com.sanguine.webpms.model.clsCheckInDtl;
import com.sanguine.webpms.model.clsCheckInHdModel;
import com.sanguine.webpms.model.clsExtraBedMasterModel;
import com.sanguine.webpms.model.clsFolioDtlModel;
import com.sanguine.webpms.model.clsFolioHdModel;
import com.sanguine.webpms.model.clsGuestMasterHdModel;
import com.sanguine.webpms.model.clsPackageMasterDtl;
import com.sanguine.webpms.model.clsPackageMasterHdModel;
import com.sanguine.webpms.model.clsPropertySetupHdModel;
import com.sanguine.webpms.model.clsReservationDtlModel;
import com.sanguine.webpms.model.clsReservationHdModel;
import com.sanguine.webpms.model.clsReservationRoomRateModelDtl;
import com.sanguine.webpms.model.clsRoomMasterModel;
import com.sanguine.webpms.model.clsRoomPackageDtl;
import com.sanguine.webpms.model.clsWalkinHdModel;
import com.sanguine.webpms.model.clsWalkinRoomRateDtlModel;
import com.sanguine.webpms.service.clsCheckInService;
import com.sanguine.webpms.service.clsFolioService;
import com.sanguine.webpms.service.clsGuestMasterService;
import com.sanguine.webpms.service.clsPropertySetupService;
import com.sanguine.webpms.service.clsReservationService;
import com.sanguine.webpms.service.clsRoomMasterService;
import com.sanguine.webpms.service.clsWalkinService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@Controller
public class clsCheckInController {

	@Autowired
	private clsCheckInService objCheckInService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsGuestMasterService objGuestMasterService;

	@Autowired
	private clsGuestMasterDao objGuestMasterDao;

	@Autowired
	private clsFolioController objFolioController;

	@Autowired
	private clsFolioService objFolioService;

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
	clsPMSUtilityFunctions objPMSUtility;
	
	@Autowired 
	clsReservationService objReservationService;
	
	@Autowired
	private clsWebPMSDBUtilityDao objWebPMSUtility;

	@Autowired
	private intfBaseService objBaseService;
	
	
	@Autowired
	private clsWalkinDao objWalkinDao;
	
	
	
	// Open CheckIn
	@RequestMapping(value = "/frmCheckIn", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCheckIn_1", "command", new clsCheckInBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCheckIn", "command", new clsCheckInBean());
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/frmCheckIn1", method = RequestMethod.GET)
	public ModelAndView funOpenForm1(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		String checkInNo = request.getParameter("docCode").toString();

		try {
			urlHits = request.getParameter("saddr").toString();

		} catch (NullPointerException e) {
			urlHits = "1";
		}

		model.put("urlHits", urlHits);

		request.getSession().setAttribute("checkInNo", checkInNo);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCheckIn_1", "command", new clsCheckInBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCheckIn", "command", new clsCheckInBean());
		} else {
			return null;
		}
	}
	
	
	
	@RequestMapping(value = "/frmQuickCheckIn", method = RequestMethod.GET)
	public ModelAndView funOpenQuickCheckInForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		String walkinNo=request.getParameter("walkinNo").toString();
		request.setAttribute("walkinNo", walkinNo);
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCheckIn_1", "command", new clsCheckInBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCheckIn", "command", new clsCheckInBean());
		} else {
			return null;
		}
	}

	// Load Header Table Data On Form
	@RequestMapping(value = "/loadCheckInData", method = RequestMethod.GET)
	public @ResponseBody clsCheckInBean funLoadHdData(HttpServletRequest request) {

		String sql = "";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		clsCheckInBean objBean = new clsCheckInBean();
		String docCode = request.getParameter("docCode").toString();
		clsCheckInHdModel objCheckIn = objCheckInService.funGetCheckInData(docCode, clientCode);
		objBean.setStrCheckInNo(objCheckIn.getStrCheckInNo());
		objBean.setStrRegistrationNo(objCheckIn.getStrRegistrationNo());
		objBean.setStrType(objCheckIn.getStrType());
		if (objCheckIn.getStrType().equals("Reservation")) {
			objBean.setStrAgainstDocNo(objCheckIn.getStrReservationNo());
		} else {
			objBean.setStrAgainstDocNo(objCheckIn.getStrWalkInNo());
		}
		objBean.setDteArrivalDate(objGlobal.funGetDate("yyyy/MM/dd", objCheckIn.getDteArrivalDate()));
		objBean.setDteDepartureDate(objGlobal.funGetDate("yyyy/MM/dd", objCheckIn.getDteDepartureDate()));
		objBean.setTmeArrivalTime(objCheckIn.getTmeArrivalTime());
		objBean.setTmeDepartureTime(objCheckIn.getTmeDepartureTime());

		sql = "select a.strRoomDesc,b.strRoomTypeDesc from tblroom a,tblroomtypemaster b " + " where a.strRoomTypeCode=b.strRoomTypeCode and a.strRoomCode='" + objCheckIn.getStrRoomNo() + "' ";
		List listRoomData = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");

		if (!listRoomData.isEmpty()) {
			Object[] arrObjRoomData = (Object[]) listRoomData.get(0);
			objBean.setStrRoomNo(objCheckIn.getStrRoomNo());
			objBean.setStrRoomDesc(arrObjRoomData[0].toString());
		} else {
			objBean.setStrRoomNo("");
			objBean.setStrRoomDesc("");
		}

		if (!objCheckIn.getStrExtraBedCode().equals("")) {
			List listExtraBedData = objExtraBedMasterDao.funGetExtraBedMaster(objCheckIn.getStrExtraBedCode(), clientCode);
			clsExtraBedMasterModel objExtraBedMasterModel = (clsExtraBedMasterModel) listExtraBedData.get(0);
			objBean.setStrExtraBedCode(objCheckIn.getStrExtraBedCode());
			objBean.setStrExtraBedDesc(objExtraBedMasterModel.getStrExtraBedTypeDesc());
		} else {
			objBean.setStrExtraBedCode("");
			objBean.setStrExtraBedDesc("");
		}
		objBean.setIntNoOfAdults(objCheckIn.getIntNoOfAdults());
		objBean.setIntNoOfChild(objCheckIn.getIntNoOfChild());
		objBean.setStrNoPostFolio(objCheckIn.getStrNoPostFolio());
		
		List<clsCheckInDetailsBean> listCheckInDtlBean = new ArrayList<clsCheckInDetailsBean>();
		for (clsCheckInDtl objCheckInDtlModel : objCheckIn.getListCheckInDtl()) {
			clsCheckInDetailsBean objCheckInDtlBean = new clsCheckInDetailsBean();
			objCheckInDtlBean.setStrGuestCode(objCheckInDtlModel.getStrGuestCode());

			sql = "select strFirstName,strMiddleName,strLastName,lngMobileNo from tblguestmaster " + " where strGuestCode='" + objCheckInDtlModel.getStrGuestCode() + "' and strClientCode='" + clientCode + "' ";
			List listGuestMaster = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");

			// listGuestMaster.forEach(obj-> {});

			for (int cnt = 0; cnt < listGuestMaster.size(); cnt++) {
				Object[] arrObjGuest = (Object[]) listGuestMaster.get(cnt);
				String guestName = arrObjGuest[0] + " " + arrObjGuest[1] + " " + arrObjGuest[2];
				objCheckInDtlBean.setStrGuestName(guestName);
				objCheckInDtlBean.setLngMobileNo(Long.parseLong(arrObjGuest[3].toString()));
			}

			sql = "select a.strRoomDesc,b.strRoomTypeDesc,b.strRoomTypeCode from tblroom a,tblroomtypemaster b " + " where a.strRoomTypeCode=b.strRoomTypeCode and a.strRoomCode='" + objCheckInDtlModel.getStrRoomNo() + "' ";
			List listRoomData1 = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
			Object[] arrObjRoomData1 = (Object[]) listRoomData1.get(0);
			objCheckInDtlBean.setStrRoomNo(objCheckInDtlModel.getStrRoomNo());
			objCheckInDtlBean.setStrRoomDesc(arrObjRoomData1[0].toString());

			if (!objCheckInDtlModel.getStrExtraBedCode().equals("")) {
				List listExtraBedData = objExtraBedMasterDao.funGetExtraBedMaster(objCheckInDtlModel.getStrExtraBedCode(), clientCode);
				clsExtraBedMasterModel objExtraBedMasterModel = (clsExtraBedMasterModel) listExtraBedData.get(0);
				objCheckInDtlBean.setStrExtraBedCode(objCheckInDtlModel.getStrExtraBedCode());
				objCheckInDtlBean.setStrExtraBedDesc(objExtraBedMasterModel.getStrExtraBedTypeDesc());
			} else {
				objCheckInDtlBean.setStrExtraBedCode("");
				objCheckInDtlBean.setStrExtraBedDesc("");
			}
			objCheckInDtlBean.setStrPayee(objCheckInDtlModel.getStrPayee());
			objCheckInDtlBean.setStrRoomType(objCheckInDtlModel.getStrRoomType());
			listCheckInDtlBean.add(objCheckInDtlBean);
		}
		objBean.setListCheckInDetailsBean(listCheckInDtlBean);
		
		
		if (objCheckIn.getStrType().equals("Reservation")) 
		{
			clsReservationHdModel objReservationModel = objReservationService.funGetReservationList(objBean.getStrAgainstDocNo(), clientCode, propCode);
			objBean.setlistReservationRoomRateDtl(objReservationModel.getListReservationRoomRateDtl());
		}
		else
		{
			List listWalkinData = objWalkinDao.funGetWalkinDataDtl(objBean.getStrAgainstDocNo(), clientCode);
			clsWalkinHdModel objWalkinHdModel = (clsWalkinHdModel) listWalkinData.get(0);
			objBean.setListWalkinRoomRateDtl(objWalkinHdModel.getListWalkinRoomRateDtlModel());
		}
		objBean.setListRoomPackageDtl(objCheckInService.funGetCheckInIncomeList(docCode, clientCode));
		for (clsRoomPackageDtl objPkgDtlModel : objBean.getListRoomPackageDtl()) 
		{
			objBean.setStrPackageCode(objPkgDtlModel.getStrPackageCode());
			objBean.setStrPackageName(objPkgDtlModel.getStrPackageName());
			break;
		}

		return objBean;
	}

	// Save or Update CheckIn
	@RequestMapping(value = "/saveCheckIn", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsCheckInBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			String startDate = req.getSession().getAttribute("startDate").toString();
			String PMSDate = objGlobal.funGetDate("yyyy-MM-dd", req.getSession().getAttribute("PMSDate").toString());
 
			
			if (objBean.getStrType().equalsIgnoreCase("Reservation")) {
				Map<Long, String> hmGuestMbWithCode = new HashMap<Long, String>();
				List<clsCheckInDetailsBean> listCheckInDtlBean = objBean.getListCheckInDetailsBean();
                for (clsCheckInDetailsBean objCheckInDtlBean : listCheckInDtlBean) {
					clsGuestMasterBean objGuestMasterBean = new clsGuestMasterBean();
					objGuestMasterBean.setStrGuestCode(objCheckInDtlBean.getStrGuestCode());
					objGuestMasterBean.setStrGuestPrefix("");
					String[] arrSpGuest = objCheckInDtlBean.getStrGuestName().split(" ");
					objGuestMasterBean.setStrFirstName(arrSpGuest[0]);
					objGuestMasterBean.setStrMiddleName(arrSpGuest[1]);
					objGuestMasterBean.setStrLastName(arrSpGuest[2]);
					objGuestMasterBean.setIntFaxNo(0);
					objGuestMasterBean.setIntPinCode(0);
					objGuestMasterBean.setDteDOB("01-01-1900");
					objGuestMasterBean.setIntMobileNo(objCheckInDtlBean.getLngMobileNo());
					clsGuestMasterHdModel objGuestMasterModel = objGuestMasterService.funPrepareGuestModel(objGuestMasterBean, clientCode, userCode);
					objGuestMasterDao.funAddUpdateGuestMaster(objGuestMasterModel);
					hmGuestMbWithCode.put(objCheckInDtlBean.getLngMobileNo(), objGuestMasterModel.getStrGuestCode());
					
				}
				List<clsCheckInDetailsBean> listCheckInDetailBean = new ArrayList<clsCheckInDetailsBean>();
				for (clsCheckInDetailsBean objCheckInDtlBean : objBean.getListCheckInDetailsBean()) {
					if (null != hmGuestMbWithCode.get(objCheckInDtlBean.getLngMobileNo())) {
						objCheckInDtlBean.setStrGuestCode(hmGuestMbWithCode.get(objCheckInDtlBean.getLngMobileNo()));
					}
					listCheckInDetailBean.add(objCheckInDtlBean);
				}
				objBean.setListCheckInDetailsBean(listCheckInDetailBean);
			}

			clsCheckInHdModel objHdModel = funPrepareHdModel(objBean, userCode, clientCode, req);

			/*
			 * List<clsCheckInDtl>
			 * listCheckInDtlModel=objHdModel.getListCheckInDtl();
			 * for(clsCheckInDtl objCheckInDtlModel:listCheckInDtlModel) {
			 * clsFolioHdBean objFolioBean=new clsFolioHdBean(); int
			 * cntFolios=0;
			 * 
			 * while(cntFolios<objCheckInDtlModel.getIntNoOfFolios()) {
			 * objFolioBean.setStrRoomNo(objCheckInDtlModel.getStrRoomNo());
			 * objFolioBean.setStrCheckInNo(objHdModel.getStrCheckInNo());
			 * objFolioBean
			 * .setStrRegistrationNo(objHdModel.getStrRegistrationNo());
			 * objFolioBean
			 * .setStrReservationNo(objHdModel.getStrReservationNo());
			 * objFolioBean.setDteArrivalDate(objHdModel.getDteArrivalDate());
			 * objFolioBean
			 * .setDteDepartureDate(objHdModel.getDteDepartureDate());
			 * objFolioBean.setTmeArrivalTime(objHdModel.getTmeArrivalTime());
			 * objFolioBean
			 * .setTmeDepartureTime(objHdModel.getTmeDepartureTime());
			 * objFolioBean
			 * .setStrExtraBedCode(objCheckInDtlModel.getStrExtraBedCode());
			 * clsFolioHdModel
			 * objFolioHdModel=objFolioController.funPrepareFolioModel
			 * (objFolioBean, clientCode, req);
			 * objFolioService.funAddUpdateFolioHd(objFolioHdModel);
			 * cntFolios++; } }
			 */

			List<clsCheckInDtl> listCheckInDtlModel = objHdModel.getListCheckInDtl();
			// for(clsCheckInDtl objCheckInDtlModel:listCheckInDtlModel)
			// {
			// clsFolioHdBean objFolioBean=new clsFolioHdBean();
			// int cntFolios=0;
			//
			// if(null!=objCheckInDtlModel.getStrPayee())
			// {
			// if(objCheckInDtlModel.getStrPayee().equals("Y"))
			// {
			// objFolioBean.setStrRoomNo(objCheckInDtlModel.getStrRoomNo());
			// objFolioBean.setStrCheckInNo(objHdModel.getStrCheckInNo());
			// objFolioBean.setStrRegistrationNo(objHdModel.getStrRegistrationNo());
			// objFolioBean.setStrReservationNo(objHdModel.getStrReservationNo());
			// objFolioBean.setDteArrivalDate(objHdModel.getDteArrivalDate());
			// objFolioBean.setDteDepartureDate(objHdModel.getDteDepartureDate());
			// objFolioBean.setTmeArrivalTime(objHdModel.getTmeArrivalTime());
			// objFolioBean.setTmeDepartureTime(objHdModel.getTmeDepartureTime());
			// objFolioBean.setStrExtraBedCode(objCheckInDtlModel.getStrExtraBedCode());
			// objFolioBean.setStrGuestCode(objCheckInDtlModel.getStrGuestCode());
			// clsFolioHdModel
			// objFolioHdModel=objFolioController.funPrepareFolioModel(objFolioBean,
			// clientCode, req);
			// objFolioService.funAddUpdateFolioHd(objFolioHdModel);
			// cntFolios++;
			// }
			// }
			// }
			List<String> listCheckRomm = new ArrayList<String>();
			List<clsFolioDtlModel> listFolioDtl = new ArrayList<clsFolioDtlModel>();
			for (clsCheckInDtl objCheckInDtlModel : listCheckInDtlModel) {
				clsFolioHdBean objFolioBean = new clsFolioHdBean();
				int cntFolios = 0;
				if (!listCheckRomm.contains(objCheckInDtlModel.getStrRoomNo())) {
					objFolioBean.setStrRoomNo(objCheckInDtlModel.getStrRoomNo());
					objFolioBean.setStrCheckInNo(objHdModel.getStrCheckInNo());
					objFolioBean.setStrRegistrationNo(objHdModel.getStrRegistrationNo());
					objFolioBean.setStrReservationNo(objHdModel.getStrReservationNo());
					objFolioBean.setStrWalkInNo(objHdModel.getStrWalkInNo());
					objFolioBean.setDteArrivalDate(objHdModel.getDteArrivalDate());
					objFolioBean.setDteDepartureDate(objHdModel.getDteDepartureDate());
					objFolioBean.setTmeArrivalTime(objHdModel.getTmeArrivalTime());
					objFolioBean.setTmeDepartureTime(objHdModel.getTmeDepartureTime());
					objFolioBean.setStrExtraBedCode(objCheckInDtlModel.getStrExtraBedCode());
					objFolioBean.setStrGuestCode(objCheckInDtlModel.getStrGuestCode());
					
					
					clsFolioHdModel objFolioHdModel = objFolioController.funPrepareFolioModel(objFolioBean, clientCode, req);
					
					
//		@@@@			if(!(objHdModel.getStrReservationNo().equalsIgnoreCase("")))
//					{
//						
//						
//					}
//					
//					long doc = objPMSUtility.funGenerateFolioDocForRoom("RoomFolio");
//					String docNo = "RM" + String.format("%06d", doc);
//					double roomTerrif = 0.0;
//					clsFolioDtlModel objFolioDtl = new clsFolioDtlModel();
//					objFolioDtl.setStrDocNo(docNo);
//					objFolioDtl.setDteDocDate(PMSDate);
//					objFolioDtl.setDblDebitAmt(roomTerrif);
//					objFolioDtl.setDblBalanceAmt(0);
//					objFolioDtl.setDblCreditAmt(0);
//					objFolioDtl.setStrPerticulars("Room Revenue");
//					objFolioDtl.setStrRevenueType("Room");
//					objFolioDtl.setStrRevenueCode(objCheckInDtlModel.getStrRoomNo());
//					listFolioDtl.add(objFolioDtl);
//					if(objHdModel.getStrReservationNo().equalsIgnoreCase(""))
//					{
//						List<clsReservationRoomRateModelDtl>listReservationRoomRate= objReservationService.funGetReservationRoomRateList( objHdModel.getStrReservationNo(),  clientCode,  objCheckInDtlModel.getStrRoomNo()) ;
//					    	
//					
//					
//	@@				}
					objFolioService.funAddUpdateFolioHd(objFolioHdModel);
					
					cntFolios++;

				}
				listCheckRomm.add(objCheckInDtlModel.getStrRoomNo());
			}

			objCheckInService.funAddUpdateCheckInHd(objHdModel);
			
			if(objBean.getStrType().equals("Reservation"))
			{
				clsReservationHdModel objReservationModel = objReservationService.funGetReservationList(objBean.getStrAgainstDocNo(), clientCode, propCode);
				List<clsReservationRoomRateModelDtl> listRommRate = new ArrayList<clsReservationRoomRateModelDtl>();
				if(null!=objBean.getlistReservationRoomRateDtl())
				{
				for (clsReservationRoomRateModelDtl objRommDtlBean : objBean.getlistReservationRoomRateDtl()) 
				{
					String date=objRommDtlBean.getDtDate();
					if(date.split("-")[0].toString().length()<3)
					{	
					 objRommDtlBean.setDtDate(objGlobal.funGetDate("yyyy-MM-dd",date));
					}
					listRommRate.add(objRommDtlBean);
				}
			    }
				objReservationModel.setListReservationRoomRateDtl(listRommRate);
				objReservationService.funAddUpdateReservationHd(objReservationModel, objReservationModel.getStrBookingTypeCode());	
				
			}
			else
			{
				List listWalkinData = objWalkinDao.funGetWalkinDataDtl(objBean.getStrAgainstDocNo(), clientCode);
				clsWalkinHdModel objWalkinHdModel = (clsWalkinHdModel) listWalkinData.get(0);
				List<clsWalkinRoomRateDtlModel> listRommRate = new ArrayList<clsWalkinRoomRateDtlModel>();
				if(null!=objBean.getListWalkinRoomRateDtl()){
					for (clsWalkinRoomRateDtlModel objRommDtlBean : objBean.getListWalkinRoomRateDtl()) {
					
						String date=objRommDtlBean.getDtDate();
						if(date.split("-")[0].toString().length()<3)
						{	
						 objRommDtlBean.setDtDate(objGlobal.funGetDate("yyyy-MM-dd",date));
						}
						listRommRate.add(objRommDtlBean);
					}
					}
				objWalkinHdModel.setListWalkinRoomRateDtlModel(listRommRate);
				objWalkinDao.funAddUpdateWalkinHd(objWalkinHdModel);
			}
			
			
			if(null!=objBean.getListRoomPackageDtl() && objBean.getListRoomPackageDtl().size()>0 )
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
				objPkgHdModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objPkgHdModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objPkgHdModel.setStrClientCode(clientCode);
				List<clsPackageMasterDtl> listPkgDtlModel = new ArrayList<clsPackageMasterDtl>();
				String insertPkgDtl= "INSERT INTO `tblroompackagedtl` (`strWalkinNo`, `strReservationNo`,"
						+ " `strCheckInNo`, `strPackageCode`, `strIncomeHeadCode`, `dblIncomeHeadAmt`, "
						+ "`strType`,`strRoomNo`,`strClientCode`) VALUES";
				
				if(objBean.getStrType().equals("Reservation"))
				{
					objWebPMSUtility.funExecuteUpdate("delete from tblroompackagedtl where strReservationNo='"+objHdModel.getStrReservationNo()+"' and strClientCode='"+clientCode+"'", "sql");
					objWebPMSUtility.funExecuteUpdate("delete from tblroompackagedtl where strCheckInNo='"+objHdModel.getStrCheckInNo()+"' and strReservationNo='"+objHdModel.getStrReservationNo()+"' and strClientCode='"+clientCode+"'", "sql");
					for (clsRoomPackageDtl objPkgDtlBean : objBean.getListRoomPackageDtl()) 
					{
						insertSql+=",('','"+objHdModel.getStrReservationNo()+"','"+objHdModel.getStrCheckInNo()+"' "
								+ ",'"+packageCode+"','"+objPkgDtlBean.getStrIncomeHeadCode()+"','"+Double.valueOf(objPkgDtlBean.getDblIncomeHeadAmt())+"'"
								+ ",'IncomeHead','','"+clientCode+"')";
							flgData=true;
							
						clsPackageMasterDtl objPkdDtl=new clsPackageMasterDtl();
						objPkdDtl.setStrIncomeHeadCode(objPkgDtlBean.getStrIncomeHeadCode());
						objPkdDtl.setDblAmt(Double.valueOf(objPkgDtlBean.getDblIncomeHeadAmt()));
						listPkgDtlModel.add(objPkdDtl);
					}
					for (clsReservationRoomRateModelDtl objRommDtlBean : objBean.getlistReservationRoomRateDtl()) 
					{
						for (clsCheckInDetailsBean objCheckInDtlBean : objBean.getListCheckInDetailsBean()) 
						{
							if(objRommDtlBean.getStrRoomType().equals(objCheckInDtlBean.getStrRoomType()))
							{
								insertSql+=",('','"+objHdModel.getStrReservationNo()+"','"+objHdModel.getStrCheckInNo()+"' "
										+ ",'"+packageCode+"','','"+objRommDtlBean.getDblRoomRate()+"' "
										+ ",'RoomTariff','"+objCheckInDtlBean.getStrRoomNo()+"','"+clientCode+"')";
								
							}
						}
					 }
				}
				else
				{
					objWebPMSUtility.funExecuteUpdate("delete from tblroompackagedtl where strWalkinNo='"+objHdModel.getStrWalkInNo()+"' and strClientCode='"+clientCode+"'", "sql");
					objWebPMSUtility.funExecuteUpdate("delete from tblroompackagedtl where strCheckInNo='"+objHdModel.getStrCheckInNo()+"' and strWalkinNo='"+objHdModel.getStrWalkInNo()+"' and strClientCode='"+clientCode+"'", "sql");
					for (clsRoomPackageDtl objPkgDtlBean : objBean.getListRoomPackageDtl()) 
					{
						insertSql+=",('"+objHdModel.getStrWalkInNo()+"','','"+objHdModel.getStrCheckInNo()+"' "
								+ ",'"+packageCode+"','"+objPkgDtlBean.getStrIncomeHeadCode()+"','"+Double.valueOf(objPkgDtlBean.getDblIncomeHeadAmt())+"'"
								+ ",'IncomeHead','','"+clientCode+"')";
							flgData=true;
							
						clsPackageMasterDtl objPkdDtl=new clsPackageMasterDtl();
						objPkdDtl.setStrIncomeHeadCode(objPkgDtlBean.getStrIncomeHeadCode());
						objPkdDtl.setDblAmt(Double.valueOf(objPkgDtlBean.getDblIncomeHeadAmt()));
						listPkgDtlModel.add(objPkdDtl);
					}
					
					for (clsWalkinRoomRateDtlModel objRommDtlBean : objBean.getListWalkinRoomRateDtl()) 
					{
						for (clsCheckInDetailsBean objCheckInDtlBean : objBean.getListCheckInDetailsBean()) 
						{
							if(objRommDtlBean.getStrRoomType().equals(objCheckInDtlBean.getStrRoomType()))
							{
								insertSql+=",('"+objHdModel.getStrWalkInNo()+"','','"+objHdModel.getStrCheckInNo()+"' "
							    		 + ",'"+packageCode+"','','"+objRommDtlBean.getDblRoomRate()+"' "
											+ ",'RoomTariff','"+objCheckInDtlBean.getStrRoomNo()+"','"+clientCode+"')";
								
							}
						}
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
			if(null!=listCheckInDtlModel)
			{
				for (clsCheckInDtl objCheckInDtlModel : listCheckInDtlModel) 
				{
					String date="",oldRoomType="",oldRoomNo="";
					boolean isFound=false;
					String sql = " select a.strDocNo,a.strRoomType,a.dteToDate,a.strRoomNo from tblchangedroomtypedtl a where a.strDocNo='"+objHdModel.getStrCheckInNo()+"' "
							+ " and a.strClientCode='"+clientCode+"'  and a.strGuestCode='"+objCheckInDtlModel.getStrGuestCode()+"' ";
					List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
					if(list.size()>0)
					{
						for (int cnt = 0; cnt < list.size(); cnt++) {
							Object[] arrObjRoom = (Object[]) list.get(cnt);
							if(!arrObjRoom[1].toString().isEmpty())
							{
								date=arrObjRoom[2].toString();
								if(!objCheckInDtlModel.getStrRoomNo().equals(arrObjRoom[3]))
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
									oldRoomNo=(String) arrObjRoom[3];
								}
							}
						}	
					}
					
				if(oldRoomType.isEmpty())
				{
					oldRoomType=objCheckInDtlModel.getStrRoomType();	
					oldRoomNo=objCheckInDtlModel.getStrRoomNo();
				}
				
				objWebPMSUtility.funExecuteUpdate("delete from tblchangedroomtypedtl where strDocNo='"+objHdModel.getStrCheckInNo()+"' and strRoomType='"+oldRoomType+"' and strGuestCode='"+objCheckInDtlModel.getStrGuestCode()+"' and strClientCode='"+clientCode+"'", "sql");	
				
				String insertChangedRoomType = "INSERT INTO `tblchangedroomtypedtl` (`strDocNo`, `strType`,"
						+ " `strRoomNo`, `strRoomType`, `strGuestCode`, `dteFromDate`, "
						+ " `dteToDate`, `strClientCode`) "
						+ " VALUES ('"+objHdModel.getStrCheckInNo()+"','CheckIn','"+oldRoomNo+"','"+oldRoomType+"',"
						+ " '"+objCheckInDtlModel.getStrGuestCode()+"','"+PMSDate+"','"+date+"','"+clientCode+"') ";
				objWebPMSUtility.funExecuteUpdate(insertChangedRoomType, "sql");	

					if(isFound)
					{
						objWebPMSUtility.funExecuteUpdate("delete from tblchangedroomtypedtl where strDocNo='"+objHdModel.getStrCheckInNo()+"' and strRoomType='"+objCheckInDtlModel.getStrRoomType()+"' and strGuestCode='"+objCheckInDtlModel.getStrGuestCode()+"' and strClientCode='"+clientCode+"'", "sql");
						insertChangedRoomType = "INSERT INTO `tblchangedroomtypedtl` (`strDocNo`, `strType`,"
								+ " `strRoomNo`, `strRoomType`, `strGuestCode`, `dteFromDate`, "
								+ " `dteToDate`, `strClientCode`) "
								+ " VALUES ('"+objHdModel.getStrCheckInNo()+"','CheckIn','"+objCheckInDtlModel.getStrRoomNo()+"','"+objCheckInDtlModel.getStrRoomType()+"',"
								+ " '"+objCheckInDtlModel.getStrGuestCode()+"','"+PMSDate+"','','"+clientCode+"') ";
						objWebPMSUtility.funExecuteUpdate(insertChangedRoomType, "sql");
						
						sql="update tblroom a set a.strStatus='Occupied' where a.strRoomCode='"+objCheckInDtlModel.getStrRoomNo()+"'";
						objWebPMSUtility.funExecuteUpdate(sql, "sql"); 
						sql="update tblroom a set a.strStatus='Free' where a.strRoomCode='"+oldRoomNo+"'";
						objWebPMSUtility.funExecuteUpdate(sql, "sql"); 
						sql="update tblreservationdtl a set a.strRoomType='"+objCheckInDtlModel.getStrRoomType()+"' , a.strRoomNo='"+objCheckInDtlModel.getStrRoomNo()+"' where a.strReservationNo='"+objHdModel.getStrReservationNo()+"' and a.strGuestCode='"+objCheckInDtlModel.getStrGuestCode()+"' ";
						objWebPMSUtility.funExecuteUpdate(sql, "sql"); 
					}
				}
			}
		
			
			
			
			
			funSendSMSCheckIn(objHdModel.getStrCheckInNo(), clientCode, propCode);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Check In No : ".concat(objHdModel.getStrCheckInNo()));
			req.getSession().setAttribute("AdvanceAmount", objHdModel.getStrCheckInNo());
			return new ModelAndView("redirect:/frmCheckIn.html");
		} else {
			return new ModelAndView("frmCheckIn");
		}
	}

	// Convert bean to model function
	public clsCheckInHdModel funPrepareHdModel(clsCheckInBean objBean, String userCode, String clientCode, HttpServletRequest req) {

		// Insert or update Walkin HD Details
		clsCheckInHdModel objModel = new clsCheckInHdModel();

		String PMSDate = objGlobal.funGetDate("yyyy-MM-dd", req.getSession().getAttribute("PMSDate").toString());
		if (objBean.getStrRegistrationNo().isEmpty()) // New Entry
		{
			String transaDate = objGlobal.funGetCurrentDateTime("dd-MM-yyyy").split(" ")[0];
			String checkInNo = objGlobal.funGeneratePMSDocumentCode("frmCheckIn", transaDate, req);
			String regNo = objGlobal.funGeneratePMSDocumentCode("frmCheckInRegNo", transaDate, req);

			objModel.setStrCheckInNo(checkInNo);
			objModel.setStrRegistrationNo(regNo);
			objModel.setStrUserCreated(userCode);
			objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else // Update
		{
			objModel.setStrCheckInNo(objBean.getStrCheckInNo());
			objModel.setStrRegistrationNo(objBean.getStrRegistrationNo());
		}

		objModel.setDteCheckInDate(PMSDate);
		objModel.setStrType(objBean.getStrType());
		if (objBean.getStrType().equals("Reservation")) {
			objModel.setStrReservationNo(objBean.getStrAgainstDocNo());
			objModel.setStrWalkInNo("");
		} else {
			objModel.setStrReservationNo("");
			objModel.setStrWalkInNo(objBean.getStrAgainstDocNo());
		}
		objModel.setDteArrivalDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteArrivalDate()));
		objModel.setDteDepartureDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteDepartureDate()));
		objModel.setTmeArrivalTime(objBean.getTmeArrivalTime());
		objModel.setTmeDepartureTime(objBean.getTmeDepartureTime());
		objModel.setStrUserEdited(userCode);
		objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrClientCode(clientCode);
		objModel.setStrRoomNo("");
		if (objBean.getStrExtraBedCode() != null) {
			objModel.setStrExtraBedCode(objBean.getStrExtraBedCode());
		} else {
			objModel.setStrExtraBedCode("");
		}

		objModel.setIntNoOfAdults(objBean.getIntNoOfAdults());
		objModel.setIntNoOfChild(objBean.getIntNoOfChild());

		List<clsCheckInDtl> listCheckinDtlModel = new ArrayList<clsCheckInDtl>();
		if (objBean.getListCheckInDetailsBean().size() > 0) {
			for (clsCheckInDetailsBean objCheckinDetails : objBean.getListCheckInDetailsBean()) {
				clsCheckInDtl objClsCheckinDtlModel = new clsCheckInDtl();
				objClsCheckinDtlModel.setStrRegistrationNo(objModel.getStrRegistrationNo());
				objClsCheckinDtlModel.setStrGuestCode(objCheckinDetails.getStrGuestCode());
				objClsCheckinDtlModel.setStrRoomNo(objCheckinDetails.getStrRoomNo());
				objClsCheckinDtlModel.setStrExtraBedCode(objCheckinDetails.getStrExtraBedCode());
				objClsCheckinDtlModel.setIntNoOfFolios(objCheckinDetails.getIntNoOfFolios());
				System.out.println("PAYEE=" + objCheckinDetails.getStrPayee());
				if (objCheckinDetails.getStrPayee() != null) {
					objClsCheckinDtlModel.setStrPayee(objCheckinDetails.getStrPayee());
				} else {
					objClsCheckinDtlModel.setStrPayee("N");
				}
				objClsCheckinDtlModel.setStrRoomType(objCheckinDetails.getStrRoomType());
				/*
				 * if(objBean.getStrPayeeGuestCode().equals(objCheckinDetails.
				 * getStrGuestCode())) { objCheckinDetails.setStrPayee("Y");
				 * }else { objCheckinDetails.setStrPayee("N"); }
				 */

				listCheckinDtlModel.add(objClsCheckinDtlModel);
			}
		}
		objModel.setListCheckInDtl(listCheckinDtlModel);
		objModel.setStrNoPostFolio(objGlobal.funIfNull(objBean.getStrNoPostFolio(), "N", objBean.getStrNoPostFolio()));
		objModel.setStrRemarks("");
		objModel.setStrReasonCode("");
		
		return objModel;
	}

	private void funSendSMSCheckIn(String checkInNo, String clientCode, String propCode) {

		String strMobileNo = "";
		clsPropertySetupHdModel objSetup = objPropertySetupService.funGetPropertySetup(propCode, clientCode);

		// clsCheckInDtl
		// objModel=objCheckInService.funGetCheckInDtlData(checkInNo,
		// clientCode);
		clsCheckInHdModel objHdModel = objCheckInService.funGetCheckInData(checkInNo, clientCode);
		clsCheckInDtl objDtlModel = null;

		String smsAPIUrl = objSetup.getStrSMSAPI();

		String smsContent = objSetup.getStrCheckInSMSContent();

		if (!smsAPIUrl.equals("")) {
			if (smsContent.contains("%%CompanyName")) {
				List<clsCompanyMasterModel> listCompanyModel = objPropertySetupService.funGetListCompanyMasterModel(clientCode);
				smsContent = smsContent.replace("%%CompanyName", listCompanyModel.get(0).getStrCompanyName());
			}
			if (smsContent.contains("%%PropertyName")) {
				clsPropertyMaster objProperty = objPropertyMasterService.funGetProperty(propCode, clientCode);
				smsContent = smsContent.replace("%%PropertyName", objProperty.getPropertyName());
			}

			if (smsContent.contains("%%CheckIn")) {
				smsContent = smsContent.replace("%%CheckIn", checkInNo);
			}

			if (smsContent.contains("%%CheckInDate")) {
				smsContent = smsContent.replace("%%CheckInDate", objHdModel.getDteCheckInDate());
			}

			List listcheckIn = objHdModel.getListCheckInDtl();

			if (listcheckIn.size() > 0) {
				for (int i = 0; i < listcheckIn.size(); i++) {
					clsCheckInDtl objDtl = (clsCheckInDtl) listcheckIn.get(i);
					if (objDtl.getStrPayee().equals("Y")) {

						List list1 = objGuestService.funGetGuestMaster(objDtl.getStrGuestCode(), clientCode);
						clsGuestMasterHdModel objGuestModel = null;
						if (list1.size() > 0) {
							objGuestModel = (clsGuestMasterHdModel) list1.get(0);

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
							if (String.valueOf(objGuestModel.getLngMobileNo()).length() >= 10) {
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
}
