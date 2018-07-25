package com.sanguine.webpms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsDayEndBean;
import com.sanguine.webpms.bean.clsPostRoomTerrifBean;
import com.sanguine.webpms.model.clsDayEndHdModel;
import com.sanguine.webpms.service.clsDayEndService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@Controller
public class clsDayEndController {

	@Autowired
	private clsDayEndService objDayEndService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	clsPostRoomTerrifController objPostRoomTerrif;

	// Open DayEnd
	@RequestMapping(value = "/frmDayEnd", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		Date dt = new Date();
		String PMSDate = request.getSession().getAttribute("PMSDate").toString();
		// String
		// PMSStartDay=request.getSession().getAttribute("PMSStartDay").toString();
		model.put("PMSDate", objGlobal.funGetDate("dd-MM-yyyy", PMSDate));

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmDayEnd_1", "command", new clsDayEndHdModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmDayEnd", "command", new clsDayEndHdModel());
		} else {
			return null;
		}
	}

	// Save or Update DayEnd
	@RequestMapping(value = "/dayEndProcess", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsDayEndBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			String startDate = req.getSession().getAttribute("startDate").toString();
			String PMSDate = req.getSession().getAttribute("PMSDate").toString();

			String []date=PMSDate.split("-");
			String datePMS=date[2]+"-"+date[1]+"-"+date[0];
			List<String> listRoomTerrifDocNo = new ArrayList<String>();
			/*String sql = "select a.strFolioNo,a.strRoomNo,c.dblRoomTerrif,a.strExtraBedCode,ifnull(a.strReservationNo,''),ifnull(a.strWalkInNo,''),c.strRoomTypeCode " 
					   + " from tblfoliohd a,tblroom b,tblroomtypemaster c " 
					   + " where a.strRoomNo=b.strRoomCode and b.strRoomTypeCode=c.strRoomTypeCode";
			*/
			String sql= "SELECT a.strFolioNo,a.strRoomNo,c.dblRoomTerrif,a.strExtraBedCode, IFNULL(a.strReservationNo,''), "
					+ " IFNULL(a.strWalkInNo,''),c.strRoomTypeCode,ifnull(sum(d.dblIncomeHeadAmt),0)"
					+ " FROM tblfoliohd a left outer join tblroompackagedtl d on a.strCheckInNo=d.strCheckInNo,tblroom b,tblroomtypemaster c "
					+ " WHERE a.strRoomNo=b.strRoomCode AND b.strRoomTypeCode=c.strRoomTypeCode"
					+ " group by a.strFolioNo";
			List listRoomInfo = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
             
			for (int cnt = 0; cnt < listRoomInfo.size(); cnt++) 
			{
				clsPostRoomTerrifBean objPostRoomTerrifBean = new clsPostRoomTerrifBean();
				Object[] arrObjRoom = (Object[]) listRoomInfo.get(cnt);
				double dblRoomRate=0.0;
				
				if(!arrObjRoom[4].toString().equals(""))
				{
				 String sqlRoomRate=" select a.dblRoomRate from  tblreservationroomratedtl a "
					        +" where a.strReservationNo='"+arrObjRoom[4].toString()+"' and a.strClientCode='"+clientCode+"' and a.strRoomType='"+arrObjRoom[6].toString()+"' and a.dtDate='"+datePMS+"' ";
				 List listRoomRate = objGlobalFunctionsService.funGetListModuleWise(sqlRoomRate, "sql");
				 if(listRoomRate.size()>0)
				 {
				 dblRoomRate=Double.parseDouble(listRoomRate.get(0).toString());
				}
				}
				if(!arrObjRoom[5].toString().equals(""))
				{
				String sqlRoomRate=" select a.dblRoomRate from  tblwalkinroomratedtl a "
					        +" where a.strWalkinNo='"+arrObjRoom[5].toString()+"' and a.strClientCode='"+clientCode+"' and a.strRoomType='"+arrObjRoom[6].toString()+"' and a.dtDate='"+datePMS+"' ";
				 List listRoomRate = objGlobalFunctionsService.funGetListModuleWise(sqlRoomRate, "sql");
				 if(listRoomRate.size()>0)
				 {
				 dblRoomRate=Double.parseDouble(listRoomRate.get(0).toString());
				 }
				}
				objPostRoomTerrifBean = new clsPostRoomTerrifBean();
				objPostRoomTerrifBean.setStrFolioNo(arrObjRoom[0].toString());
				objPostRoomTerrifBean.setStrRoomNo(arrObjRoom[1].toString());
				objPostRoomTerrifBean.setDblRoomTerrif(dblRoomRate);
				objPostRoomTerrifBean.setDblOriginalPostingAmt(dblRoomRate);
				objPostRoomTerrifBean.setStrFolioType("Room");
				String folioNo = arrObjRoom[0].toString();
				String docNo = objPostRoomTerrif.funInsertFolioRecords(folioNo, clientCode, propCode, objPostRoomTerrifBean, objGlobal.funGetDate("yyyy-MM-dd", PMSDate), arrObjRoom[3].toString());
				listRoomTerrifDocNo.add(docNo);
				if(Double.valueOf(arrObjRoom[7].toString())>0)
				{   
					dblRoomRate=Double.valueOf(arrObjRoom[7].toString())/2;
					objPostRoomTerrifBean = new clsPostRoomTerrifBean();
					objPostRoomTerrifBean.setStrFolioNo(arrObjRoom[0].toString());
					objPostRoomTerrifBean.setStrRoomNo(arrObjRoom[1].toString());
					objPostRoomTerrifBean.setDblRoomTerrif(dblRoomRate);
					objPostRoomTerrifBean.setDblOriginalPostingAmt(dblRoomRate);
					objPostRoomTerrifBean.setStrFolioType("Package");
					folioNo = arrObjRoom[0].toString();
					docNo=objPostRoomTerrif.funInsertFolioRecords(folioNo, clientCode, propCode, objPostRoomTerrifBean, objGlobal.funGetDate("yyyy-MM-dd", PMSDate), arrObjRoom[3].toString());	
					listRoomTerrifDocNo.add(docNo);
				}
				
				
			}

			double dayEndAmt = 0;
			for (int cnt = 0; cnt < listRoomTerrifDocNo.size(); cnt++) {
				sql = "select sum(dblDebitAmt) from tblfoliodtl " + " where strDocNo='" + listRoomTerrifDocNo.get(cnt) + "' group by strDocNo";
				List listFolioAmt = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
				dayEndAmt += Double.parseDouble(listFolioAmt.get(0).toString());
			}

			objBean.setDblDayEndAmt(dayEndAmt);
			objBean.setStrDayEnd("Y");
			objBean.setStrPropertyCode(propCode);
			objBean.setStrClientCode(clientCode);

			clsDayEndHdModel objHdModel = funPrepareHdModel(objBean, userCode);
			objDayEndService.funAddUpdateDayEndHd(objHdModel);

			// Insert row in tbldayendprocess for next date.
			String[] arrSpDate = objBean.getDtePMSDate().split("-");
			// Date dtNextDate=new
			// Date(Integer.parseInt(arrSpDate[2]),Integer.parseInt(arrSpDate[1]),Integer.parseInt(arrSpDate[0]));
			Date dtNextDate = new Date(Integer.parseInt(arrSpDate[0]), Integer.parseInt(arrSpDate[1]), Integer.parseInt(arrSpDate[2]));
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(dtNextDate);
			cal.add(Calendar.DATE, 1);
			String newStartDate = cal.getTime().getYear() + "-" + (cal.getTime().getMonth()) + "-" + (cal.getTime().getDate());
			clsDayEndHdModel objModel = new clsDayEndHdModel();
			objModel.setStrClientCode(clientCode);
			objModel.setStrPropertyCode(propCode);
			objModel.setStrStartDay("Y");
			objModel.setStrDayEnd("N");
			objModel.setDblDayEndAmt(0);
			objModel.setDtePMSDate(newStartDate);
			objModel.setStrUserCode(userCode);
			objDayEndService.funAddUpdateDayEndHd(objModel);

			return new ModelAndView("redirect:/frmModuleSelection.html");
		} else {
			return new ModelAndView("frmDayEnd");
		}
	}

	// Convert bean to model function
	private clsDayEndHdModel funPrepareHdModel(clsDayEndBean objBean, String userCode) {

		clsDayEndHdModel objModel = new clsDayEndHdModel();
		objModel.setStrStartDay("Y");
		objModel.setDtePMSDate(objBean.getDtePMSDate());
		objModel.setStrDayEnd(objBean.getStrDayEnd());
		objModel.setDblDayEndAmt(objBean.getDblDayEndAmt());
		objModel.setStrUserCode(userCode);
		objModel.setStrPropertyCode(objBean.getStrPropertyCode());
		objModel.setStrClientCode(objBean.getStrClientCode());
		return objModel;
	}

	// Start Day function
	@RequestMapping(value = "/startPMSDay", method = RequestMethod.GET)
	public @ResponseBody int funLoadMasterData(@RequestParam("PMSDate") String PMSDate, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();

		/*
		 * String sql="update tbldayendprocess set strStartDay='Y' " +
		 * " where strPropertyCode='" + propCode +
		 * "' and strClientCode='"+clientCode+"' " +
		 * " and strDayEnd='N' and date(dtePMSDate)='"+PMSDate+"'";
		 */

		clsDayEndHdModel objModel = new clsDayEndHdModel();
		objModel.setDtePMSDate(PMSDate);
		objModel.setStrStartDay("Y");
		objModel.setStrDayEnd("N");
		objModel.setDblDayEndAmt(0);
		objModel.setStrUserCode(userCode);
		objModel.setStrPropertyCode(propCode);
		objModel.setStrClientCode(clientCode);
		objDayEndService.funAddUpdateDayEndHd(objModel);
		return 1;
	}

}
