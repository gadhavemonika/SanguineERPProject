package com.sanguine.webpms.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsPropertyMasterService;
import com.sanguine.webpms.bean.clsCheckInBean;
import com.sanguine.webpms.bean.clsCheckInDetailsBean;
import com.sanguine.webpms.bean.clsWalkinBean;
import com.sanguine.webpms.bean.clsWalkinDtlBean;
import com.sanguine.webpms.dao.clsExtraBedMasterDao;
import com.sanguine.webpms.dao.clsRoomTypeMasterDao;
import com.sanguine.webpms.dao.clsWalkinDao;
import com.sanguine.webpms.dao.clsWebPMSDBUtilityDao;
import com.sanguine.webpms.model.clsExtraBedMasterModel;
import com.sanguine.webpms.model.clsPackageMasterDtl;
import com.sanguine.webpms.model.clsPackageMasterHdModel;
import com.sanguine.webpms.model.clsPropertySetupHdModel;
import com.sanguine.webpms.model.clsReservationRoomRateModelDtl;
import com.sanguine.webpms.model.clsRoomMasterModel;
import com.sanguine.webpms.model.clsRoomPackageDtl;
import com.sanguine.webpms.model.clsRoomTypeMasterModel;
import com.sanguine.webpms.model.clsWalkinDtl;
import com.sanguine.webpms.model.clsWalkinHdModel;
import com.sanguine.webpms.model.clsWalkinRoomRateDtlModel;
import com.sanguine.webpms.service.clsPropertySetupService;
import com.sanguine.webpms.service.clsRoomMasterService;
import com.sanguine.webpms.service.clsWalkinService;

@Controller
public class clsWalkinController {

	@Autowired
	private clsWalkinService objWalkinService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsWalkinDao objWalkinDao;

	@Autowired
	private clsExtraBedMasterDao objExtraBedMasterDao;

	@Autowired
	private clsRoomMasterService objRoomMasterService;
	
	@Autowired
	private clsPropertySetupService objPropertySetupService;

	@Autowired
	private clsRoomTypeMasterDao objRoomTypeMasterDao;
	
	@Autowired
	private clsWebPMSDBUtilityDao objWebPMSUtility;
	
	@Autowired
	private intfBaseService objBaseService;


	// Open Walkin
	@RequestMapping(value = "/frmWalkin", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		String webStockDB=request.getSession().getAttribute("WebStockDB").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List<String> listPrefix = new ArrayList<>();
		listPrefix.add("Mr.");
		listPrefix.add("Mrs.");
		listPrefix.add("Miss");
		model.put("prefix", listPrefix);

		List<String> listGender = new ArrayList<>();
		listGender.add("M");
		listGender.add("F");
		model.put("gender", listGender);

		String sql = "select strCityName from "+webStockDB+".tblcitymaster where strClientCode='" + clientCode + "'";
		List list = objGlobalFunctionsService.funGetList(sql, "sql");
		List<String> listCity = new ArrayList<>();
		for (int cnt = 0; cnt < list.size(); cnt++) {
			listCity.add(list.get(cnt).toString());
		}
		model.put("listCity", listCity);

		sql = "select strStateName from "+webStockDB+".tblstatemaster where strClientCode='" + clientCode + "'";
		List listStateDetails = objGlobalFunctionsService.funGetList(sql, "sql");
		List<String> listState = new ArrayList<>();
		for (int cnt = 0; cnt < listStateDetails.size(); cnt++) {
			listState.add(listStateDetails.get(cnt).toString());
		}
		model.put("listState", listState);

		sql = "select strCountryName from "+webStockDB+".tblcountrymaster where strClientCode='" + clientCode + "'";
		List listCountryDetails = objGlobalFunctionsService.funGetList(sql, "sql");
		List<String> listCountry = new ArrayList<>();
		for (int cnt = 0; cnt < listCountryDetails.size(); cnt++) {
			listCountry.add(listCountryDetails.get(cnt).toString());
		}
		model.put("listCountry", listCountry);

		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWalkin_1", "command", new clsWalkinBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWalkin", "command", new clsWalkinBean());
		} else {
			return null;
		}
	}

	// Load Header Table Data On Form
	@RequestMapping(value = "/loadWalkinData", method = RequestMethod.GET)
	public @ResponseBody clsWalkinBean funLoadWalkinHdData(HttpServletRequest request) {
		clsWalkinHdModel objWalkinHdModel = null;
		String sql = "";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		// String
		// userCode=request.getSession().getAttribute("usercode").toString();
		String docCode = request.getParameter("docCode").toString();

		List listWalkinData = objWalkinDao.funGetWalkinDataDtl(docCode, clientCode);
		objWalkinHdModel = (clsWalkinHdModel) listWalkinData.get(0);
		clsWalkinBean objWalkinBean = null;
		if (null == objWalkinHdModel) {
			objWalkinBean = new clsWalkinBean();
			objWalkinBean.setStrWalkinNo("Invalid");
		} else {
			Map<String, clsWalkinDtlBean> hmWalkinDtlBean = new HashMap<String, clsWalkinDtlBean>();

			objWalkinBean = new clsWalkinBean();
			objWalkinBean.setStrWalkinNo(objWalkinHdModel.getStrWalkinNo());
			objWalkinBean.setDteWalkinDate(objGlobal.funGetDate("yyyy/MM/dd", objWalkinHdModel.getDteWalkinDate()));
			objWalkinBean.setDteCheckOutDate(objGlobal.funGetDate("yyyy/MM/dd", objWalkinHdModel.getDteCheckOutDate()));
			objWalkinBean.setTmeWalkinTime(objWalkinHdModel.getTmeWalkinTime());
			objWalkinBean.setTmeCheckOutTime(objWalkinHdModel.getTmeCheckOutTime());
			objWalkinBean.setStrCorporateCode(objWalkinHdModel.getStrCorporateCode());
			objWalkinBean.setStrBookerCode(objWalkinHdModel.getStrBookerCode());
			objWalkinBean.setStrAgentCode(objWalkinHdModel.getStrAgentCode());
			objWalkinBean.setIntNoOfNights(objWalkinHdModel.getIntNoOfNights());
			objWalkinBean.setStrRemarks(objWalkinHdModel.getStrRemarks());
			objWalkinBean.setStrUserCreated(objWalkinHdModel.getStrUserCreated());
			objWalkinBean.setStrUserEdited(objWalkinHdModel.getStrUserEdited());
			objWalkinBean.setDteDateCreated(objWalkinHdModel.getDteDateCreated());
			objWalkinBean.setDteDateEdited(objWalkinHdModel.getDteDateEdited());
			objWalkinBean.setStrIncomeHeadCode(objWalkinHdModel.getStrIncomeHeadCode());

			clsRoomMasterModel objRoomMasterModel = objRoomMasterService.funGetRoomMaster(objWalkinHdModel.getStrRoomNo(), clientCode);
			objWalkinBean.setStrRoomNo(objWalkinHdModel.getStrRoomNo());
			
			if (objRoomMasterModel != null) {
				objWalkinBean.setStrRoomDesc(objRoomMasterModel.getStrRoomDesc());
			} else {
				objWalkinBean.setStrRoomDesc("");
			}
			
			
			if (!objWalkinHdModel.getStrExtraBedCode().isEmpty()) {
				List listExtraBedData = objExtraBedMasterDao.funGetExtraBedMaster(objWalkinHdModel.getStrExtraBedCode(), clientCode);
				clsExtraBedMasterModel objExtraBedMasterModel = (clsExtraBedMasterModel) listExtraBedData.get(0);
				objWalkinBean.setStrExtraBedCode(objWalkinHdModel.getStrExtraBedCode());
				objWalkinBean.setStrExtraBedDesc(objExtraBedMasterModel.getStrExtraBedTypeDesc());
			} else {
				objWalkinBean.setStrExtraBedCode("");
				objWalkinBean.setStrExtraBedDesc("");
			}
			objWalkinBean.setIntNoOfAdults(objWalkinHdModel.getIntNoOfAdults());
			objWalkinBean.setIntNoOfChild(objWalkinHdModel.getIntNoOfChild());

			List<clsWalkinDtlBean> listWalkinDtlBean = new ArrayList<clsWalkinDtlBean>();
			for (clsWalkinDtl objWalkinDtlModel : objWalkinHdModel.getListWalkinDtlModel()) {
				clsWalkinDtlBean objWalkinDtlBean = new clsWalkinDtlBean();

				sql = "select strFirstName,strMiddleName,strLastName,lngMobileNo from tblguestmaster " + " where strGuestCode='" + objWalkinDtlModel.getStrGuestCode() + "' and strClientCode='" + clientCode + "' ";
				List listGuestMaster = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
				for (int cnt = 0; cnt < listGuestMaster.size(); cnt++) {
					Object[] arrObjGuest = (Object[]) listGuestMaster.get(cnt);
					objWalkinDtlBean.setStrGuestFirstName(String.valueOf(arrObjGuest[0]));
					objWalkinDtlBean.setStrGuestMiddleName(String.valueOf(arrObjGuest[1]));
					objWalkinDtlBean.setStrGuestLastName(String.valueOf(arrObjGuest[2]));
					objWalkinDtlBean.setLngMobileNo(Long.parseLong(arrObjGuest[3].toString()));
				}
				objWalkinDtlBean.setStrGuestCode(objWalkinDtlModel.getStrGuestCode());
				

//				sql = "select strRoomCode,strRoomDesc from tblroom " + " where strRoomCode='" + objWalkinDtlModel.getStrRoomNo() + "' and strClientCode='" + clientCode + "' ";
//				List listRoomMaster = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
//				for (int cnt = 0; cnt < listRoomMaster.size(); cnt++) {
//					Object[] arrObjRoom = (Object[]) listRoomMaster.get(cnt);
//					objWalkinDtlBean.setStrRoomDesc(String.valueOf(arrObjRoom[1]));
//				}
				clsRoomMasterModel objRoomMasterModel1 = objRoomMasterService.funGetRoomMaster(objWalkinDtlModel.getStrRoomNo(), clientCode);
				if (objRoomMasterModel1 != null) {
					objWalkinDtlBean.setStrRoomNo(objWalkinDtlModel.getStrRoomNo());
					objWalkinDtlBean.setStrRoomDesc(objRoomMasterModel1.getStrRoomDesc());
				} else {
					objWalkinDtlBean.setStrRoomNo("");
					objWalkinDtlBean.setStrRoomDesc("");
				}
				
			
				objWalkinDtlBean.setStrRoomType(objWalkinDtlModel.getStrRoomType());
				objWalkinDtlBean.setIntNoOfAdults(objWalkinDtlModel.getIntNoOfAdults());
				objWalkinDtlBean.setIntNoOfChild(objWalkinDtlModel.getIntNoOfChild());

				List listExtraBedData = objExtraBedMasterDao.funGetExtraBedMaster(objWalkinDtlModel.getStrExtraBedCode(), clientCode);
				if (listExtraBedData.size() > 0) {
					clsExtraBedMasterModel objExtraBedMasterModel = (clsExtraBedMasterModel) listExtraBedData.get(0);
					objWalkinDtlBean.setStrExtraBedCode(objWalkinDtlModel.getStrExtraBedCode());
					objWalkinDtlBean.setStrExtraBedDesc(objExtraBedMasterModel.getStrExtraBedTypeDesc());
				} else {
					objWalkinDtlBean.setStrExtraBedCode("");
					objWalkinDtlBean.setStrExtraBedDesc("");
				}

				listWalkinDtlBean.add(objWalkinDtlBean);
				// hmWalkinDtlBean.put(objWalkinDtlModel.getStrRoomNo(),objWalkinDtlBean);
			}

			/*
			 * for (Map.Entry<String, clsWalkinDtlBean> entry :
			 * hmWalkinDtlBean.entrySet()) { listWalkinDtlBean.add(entry.getValue());
			 * }
			 */

			objWalkinBean.setListWalkinDetailsBean(listWalkinDtlBean);
			objWalkinBean.setListWalkinRoomRateDtl(objWalkinHdModel.getListWalkinRoomRateDtlModel());
			objWalkinBean.setListRoomPackageDtl(objWalkinDao.funGetWalkinIncomeList(docCode, clientCode));
			for (clsRoomPackageDtl objPkgDtlModel : objWalkinBean.getListRoomPackageDtl()) 
			{
				objWalkinBean.setStrPackageCode(objPkgDtlModel.getStrPackageCode());
				objWalkinBean.setStrPackageName(objPkgDtlModel.getStrPackageName());
				break;
			}
		}
		return objWalkinBean;
	}

	// Save or Update Walkin
	@RequestMapping(value = "/saveWalkin", method = RequestMethod.GET)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsWalkinBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsWalkinHdModel objHdModel = objWalkinService.funPrepareWalkinModel(objBean, clientCode, req, userCode);

			
			
			clsCheckInBean objCheckInBean = new clsCheckInBean();
			List<clsCheckInDetailsBean> listCheckinDtlBean = new ArrayList<clsCheckInDetailsBean>();
			Map<Long, String> hmGuestMbWithCode = new HashMap<Long, String>();

			objCheckInBean.setStrRegistrationNo("");
			objCheckInBean.setStrType("Walk In");
			objCheckInBean.setStrAgainstDocNo(objHdModel.getStrWalkinNo());
			objCheckInBean.setDteArrivalDate(objGlobal.funGetDate("yyyy/MM/dd", objHdModel.getDteWalkinDate()));
			objCheckInBean.setTmeDepartureTime(objHdModel.getTmeCheckOutTime());
			objCheckInBean.setTmeArrivalTime(objHdModel.getTmeWalkinTime());
			objCheckInBean.setDteDepartureDate(objGlobal.funGetDate("yyyy/MM/dd", objHdModel.getDteCheckOutDate()));
			
			objWalkinDao.funAddUpdateWalkinHd(objHdModel);
			
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
				objWebPMSUtility.funExecuteUpdate("delete from tblroompackagedtl where strWalkinNo='"+objHdModel.getStrWalkinNo()+"' and strClientCode='"+clientCode+"'", "sql");	
				List<clsPackageMasterDtl> listPkgDtlModel = new ArrayList<clsPackageMasterDtl>();
				String insertPkgDtl= "INSERT INTO `tblroompackagedtl` (`strWalkinNo`, `strReservationNo`,"
						+ " `strCheckInNo`, `strPackageCode`, `strIncomeHeadCode`, `dblIncomeHeadAmt`, "
						+ "`strType`,`strRoomNo`,`strClientCode`) VALUES";
				for (clsRoomPackageDtl objPkgDtlBean : objBean.getListRoomPackageDtl()) 
				{
					insertSql+=",('"+objHdModel.getStrWalkinNo()+"','','' "
							+ ",'"+packageCode+"','"+objPkgDtlBean.getStrIncomeHeadCode()+"','"+Double.valueOf(objPkgDtlBean.getDblIncomeHeadAmt())+"'"
							+ ",'IncomeHead','','"+clientCode+"')";
						flgData=true;
						
					clsPackageMasterDtl objPkdDtl=new clsPackageMasterDtl();
					objPkdDtl.setStrIncomeHeadCode(objPkgDtlBean.getStrIncomeHeadCode());
					objPkdDtl.setDblAmt(Double.valueOf(objPkgDtlBean.getDblIncomeHeadAmt()));
					listPkgDtlModel.add(objPkdDtl);
				}
				if(null!=objBean.getListWalkinRoomRateDtl())
				{
				for (clsWalkinRoomRateDtlModel objRommDtlBean : objBean.getListWalkinRoomRateDtl()) 
				{
				     insertSql+=",('"+objHdModel.getStrWalkinNo()+"','','' "
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
			
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Walkin Code:".concat(objHdModel.getStrWalkinNo()));
			req.getSession().setAttribute("AdvanceAmount", objHdModel.getStrWalkinNo());
			return new ModelAndView("redirect:/frmWalkin.html");
		} else {
			return new ModelAndView("frmWalkin");
		}
	}

	// Load Master Data On Form
	@RequestMapping(value = "/loadRoomInformation", method = RequestMethod.GET)
	public @ResponseBody List funLoadMasterData(@RequestParam("roomCode") String roomCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String sql = " select a.strRoomCode,a.strRoomDesc,b.strRoomTypeDesc " 
				+ " from tblroom a,tblroomtypemaster b  " 
				+ " where a.strClientCode='" + clientCode + "' and a.strRoomCode='" + roomCode + "' " 
				+ " and a.strRoomTypeCode=b.strRoomTypeCode  ";
		List listRoom = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		return listRoom;

	}

	// Load data from database to form
	@RequestMapping(value = "/loadGuestInformation", method = RequestMethod.GET)
	public @ResponseBody List funFetchGuestMasterData(@RequestParam("guestCode") String guestCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String sql = " select a.strGuestCode,a.strFirstName,a.strMiddleName,a.strLastName,a.lngMobileNo,a.strAddress " 
		+ " from tblguestmaster a " 
		+ " where a.strClientCode='" + clientCode + "' and a.strGuestCode='" + guestCode + "' ";

		List listGuest = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");

		return listGuest;
	}

	// Load data from database to form
	@RequestMapping(value = "/loadWalkinInformation", method = RequestMethod.GET)
	public @ResponseBody List funFetchWalkinInformation(@RequestParam("walkinNo") String walkinNo, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String sql = "  select a.strWalkInNo,a.dteArrivalDate,a.tmeArrivalTime,a.dteDepartureDate,a.tmeDepartureTime," 
		+ " a.strCorporateCode,a.strBookerCode,a.strAgentCode,a.intNoOfNights" 
		+ " from tblcheckinhd a where a.strWalkInNo='" + walkinNo + "' ";

		List listWalkinData = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");

		return listWalkinData;
	}
	
	@RequestMapping(value = "/loadRoomRateWalkin", method = RequestMethod.POST)
	public @ResponseBody List funLoadRoomRate(String arrivalDate, String departureDate,String roomDescList, HttpServletRequest req) throws ParseException {
		
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
			returnList.add(listRoomRate);
			
			
		}
		listRoomCode.add(roomCode);
		}
		
		}
			
		}
		return returnList;
	}

	
}
