package com.sanguine.webpms.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsPostRoomTerrifBean;
import com.sanguine.webpms.bean.clsTaxCalculation;
import com.sanguine.webpms.bean.clsTaxProductDtl;
import com.sanguine.webpms.dao.clsExtraBedMasterDao;
import com.sanguine.webpms.dao.clsWebPMSDBUtilityDao;
import com.sanguine.webpms.model.clsExtraBedMasterModel;
import com.sanguine.webpms.model.clsFolioDtlModel;
import com.sanguine.webpms.model.clsFolioHdModel;
import com.sanguine.webpms.model.clsFolioTaxDtl;
import com.sanguine.webpms.service.clsFolioService;

@Controller
public class clsPostRoomTerrifController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	clsPMSUtilityFunctions objPMSUtility;

	@Autowired
	clsFolioService objFolioService;

	@Autowired
	clsGlobalFunctions objGlobal;

	@Autowired
	private clsExtraBedMasterDao objExtraBedMasterDao;
	
	@Autowired
	private clsWebPMSDBUtilityDao objWebPMSUtility;

	// Open Post Room Terrif
	@RequestMapping(value = "/frmPostRoomTerrif", method = RequestMethod.GET)
	public ModelAndView funOpenForm() {
		clsPostRoomTerrifBean objBean = new clsPostRoomTerrifBean();
		return new ModelAndView("frmPostRoomTerrif", "command", objBean);
	}

	// Load Header Table Data On Form
	@RequestMapping(value = "/loadRoomDetails", method = RequestMethod.GET)
	public @ResponseBody List funLoadRoomDetails(HttpServletRequest request) {

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		String roomNo = request.getParameter("roomNo").toString();
		String PMSDate = request.getSession().getAttribute("PMSDate").toString();

		String []date=PMSDate.split("-");
		String datePMS=date[2]+"-"+date[1]+"-"+date[0];
		
		
		String sql=" SELECT d.strRoomCode,d.strRoomDesc,a.strRegistrationNo,c.strFirstName,c.strMiddleName,c.strLastName,"
				+ " e.dblRoomTerrif, IFNULL(a.strReservationNo,''), IFNULL(a.strWalkInNo,''),e.strRoomTypeCode,ifnull(sum(f.dblIncomeHeadAmt),0),a.strCheckInNo"
				+ " FROM tblcheckinhd a left outer join tblroompackagedtl f on a.strCheckInNo=f.strCheckInNo and f.strRoomNo='',tblcheckindtl b,tblguestmaster c,tblroom d,tblroomtypemaster e"
				+ " WHERE a.strCheckInNo=b.strCheckInNo AND b.strGuestCode=c.strGuestCode "
				+ " AND b.strRoomNo=d.strRoomCode AND d.strRoomTypeCode=e.strRoomTypeCode AND b.strRoomNo='"+roomNo+"' "
				+ " AND a.strCheckInNo NOT IN (SELECT strCheckInNo FROM tblbillhd WHERE strRoomNo='"+roomNo+"') ";
		
		List listRoomDtl = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		if(listRoomDtl.size()>0)
		{
			Object []arrObjRoom=(Object[])listRoomDtl.get(0);
			double dblTotalAmt=0.0;
			double dblRoomRate=0.0;
			double dblPkgAmt=0.0;
			String sqlRoomCount=" select count(b.strRoomNo) from tblcheckinhd a,tblcheckindtl b"
					+ " where a.strCheckInNo=b.strCheckInNo and a.strCheckInNo='"+arrObjRoom[11].toString()+"' ";
			List listRoomCnt= objGlobalFunctionsService.funGetListModuleWise(sqlRoomCount, "sql");
			BigInteger roomCnt=new BigInteger(listRoomCnt.get(0).toString());
			
			String sqlCheckTerrifBalanceAmt=" SELECT b.strFolioNo,b.dblDebitAmt,b.dblBalanceAmt,b.strRevenueType "
					+ " FROM tblfoliohd a,tblfoliodtl b "
					+ " WHERE a.strFolioNo=b.strFolioNo  and a.strRoomNo=b.strRevenueCode AND a.strCheckInNo='"+arrObjRoom[11].toString()+"' "
					+ " and  (b.strRevenueType='Package' or b.strRevenueType='Room')  and a.strRoomNo='"+roomNo+"' "
					+ " group by b.strRevenueType  ORDER BY b.dteDocDate DESC ";
			 List listTerriff = objGlobalFunctionsService.funGetListModuleWise(sqlCheckTerrifBalanceAmt, "sql");
			 if(listTerriff.size()>0)
			 {
				 for (int cnt = 0; cnt < listTerriff.size(); cnt++) 
				 {
						Object[] arrObjTerriff = (Object[]) listTerriff.get(cnt);
						if(arrObjTerriff[3].toString().equals("Package"))
						{
							dblPkgAmt=Double.valueOf(arrObjTerriff[2].toString());
						}
						else
						{
							dblRoomRate=Double.valueOf(arrObjTerriff[2].toString());
						}	
				 }	
				 dblTotalAmt=dblRoomRate+dblPkgAmt;
			 }
			 else
			 {
				 if(!arrObjRoom[7].toString().equals(""))
					{
					 String sqlRoomRate=" select a.dblRoomRate from  tblreservationroomratedtl a "
						        +" where a.strReservationNo='"+arrObjRoom[7].toString()+"' and a.strClientCode='"+clientCode+"' and a.strRoomType='"+arrObjRoom[9].toString()+"' and a.dtDate='"+datePMS+"' ";
					 List listRoomRate = objGlobalFunctionsService.funGetListModuleWise(sqlRoomRate, "sql");
					 if(listRoomRate.size()>0)
					 {
					 dblRoomRate=Double.parseDouble(listRoomRate.get(0).toString());
					}
					}
					if(!arrObjRoom[8].toString().equals(""))
					{
					String sqlRoomRate=" select a.dblRoomRate from  tblwalkinroomratedtl a "
						        +" where a.strWalkinNo='"+arrObjRoom[8].toString()+"' and a.strClientCode='"+clientCode+"' and a.strRoomType='"+arrObjRoom[9].toString()+"' and a.dtDate='"+datePMS+"' ";
					 List listRoomRate = objGlobalFunctionsService.funGetListModuleWise(sqlRoomRate, "sql");
					 if(listRoomRate.size()>0)
					 {
					 dblRoomRate=Double.parseDouble(listRoomRate.get(0).toString());
					 }
					}
					dblPkgAmt=(Double.valueOf(arrObjRoom[10].toString())/roomCnt.intValue());
					dblTotalAmt=dblRoomRate+dblPkgAmt;
			 }
			 listRoomDtl.add(dblTotalAmt);
			 listRoomDtl.add(dblRoomRate);
			 listRoomDtl.add(dblPkgAmt);
		}
		return listRoomDtl;
	}

	// Save or Update Reservation
	@RequestMapping(value = "/postRoomTerrif", method = RequestMethod.POST)
	public ModelAndView funPostRoomTerrif(@ModelAttribute("command") @Valid clsPostRoomTerrifBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			String PMSDate = objGlobal.funGetDate("yyyy-MM-dd", req.getSession().getAttribute("PMSDate").toString());

			String sql = "select strFolioNo,strExtraBedCode " + " from tblfoliohd " + " where strRoomNo='" + objBean.getStrRoomNo() + "' and strRegistrationNo='" + objBean.getStrRegistrationNo() + "' " + " and strClientCode='" + clientCode + "'";
			List listFolio = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
			Object[] arrObjFolioDtl = (Object[]) listFolio.get(0);

			String folioNo = arrObjFolioDtl[0].toString();
			String extraBedCode = arrObjFolioDtl[1].toString();
			String docNo="";
			double totalRoomTarrif=objBean.getDblRoomTerrif();
			double totalPakageAmt=objBean.getDblPackageAmt();
			double actualPostingAmt=objBean.getDblActualPostingAmt();
			String roomNo=objBean.getStrRoomNo();
			if(actualPostingAmt>0)
			{
				if(totalPakageAmt>0 && totalPakageAmt<totalRoomTarrif)
				{
					if(actualPostingAmt>objBean.getDblPackageAmt())
					{
						objBean.setStrFolioType("Package");
						objBean.setDblRoomTerrif(totalPakageAmt);
						objBean.setDblOriginalPostingAmt(totalPakageAmt);
						docNo = funInsertFolioRecords(folioNo, clientCode, propCode, objBean, PMSDate, extraBedCode);
						
						if((actualPostingAmt-totalPakageAmt)>0)
						{
							objBean = new clsPostRoomTerrifBean();
							objBean.setStrFolioType("Room");
							objBean.setStrFolioNo(folioNo);
							objBean.setStrRoomNo(roomNo);
							objBean.setDblRoomTerrif(actualPostingAmt-totalPakageAmt);
							objBean.setDblOriginalPostingAmt(totalRoomTarrif);
							docNo = funInsertFolioRecords(folioNo, clientCode, propCode, objBean, PMSDate, extraBedCode);		
						}
						
					}
					else
					{
						objBean.setStrFolioType("Package");
						objBean.setDblRoomTerrif(totalPakageAmt-actualPostingAmt);
						objBean.setDblOriginalPostingAmt(totalPakageAmt);
						docNo = funInsertFolioRecords(folioNo, clientCode, propCode, objBean, PMSDate, extraBedCode);	
					}
					
				}
				else
				{
					objBean.setStrFolioType("Room");
					objBean.setDblRoomTerrif(actualPostingAmt);
					docNo = funInsertFolioRecords(folioNo, clientCode, propCode, objBean, PMSDate, extraBedCode);
				}
			}
			
			//String docNo = funInsertFolioRecords(folioNo, clientCode, propCode, objBean, PMSDate, extraBedCode);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Terrif Posted Successfully. " + docNo);

			return new ModelAndView("redirect:/frmPostRoomTerrif.html");
		} else {
			return new ModelAndView("frmPostRoomTerrif");
		}
	}

	public String funInsertFolioRecords(String folioNo, String clientCode, String propCode, clsPostRoomTerrifBean objBean, String PMSDate, String extraBedCode) {
		clsFolioHdModel objFolioHd = objFolioService.funGetFolioList(folioNo, clientCode, propCode);
		System.out.println(objFolioHd.getListFolioDtlModel().size());
		// long nextDocNo=objGlobalFunctionsService.funGetNextNo("tblfoliodtl",
		// "FolioPosting", "strDocNo", clientCode, "and left(strDocNo,2)='RM'");
		// docNo="RM"+String.format("%06d", nextDocNo);
		long doc = 0;
		doc = objPMSUtility.funGenerateFolioDocForRoom("RoomFolio");
		String docNo = "RM" + String.format("%06d", doc);
		double roomTerrif = objBean.getDblRoomTerrif();

		clsTaxProductDtl objTaxProductDtl = new clsTaxProductDtl();
		objTaxProductDtl.setStrTaxProdCode(objBean.getStrRoomNo());
		objTaxProductDtl.setStrTaxProdName("");
		objTaxProductDtl.setDblTaxProdAmt(roomTerrif);
		List<clsTaxProductDtl> listTaxProdDtl = new ArrayList<clsTaxProductDtl>();
		listTaxProdDtl.add(objTaxProductDtl);
		Map<String, List<clsTaxCalculation>> hmTaxCalDtl = objPMSUtility.funCalculatePMSTax(listTaxProdDtl, "Room Night");

		List<clsFolioDtlModel> listFolioDtl = new ArrayList<clsFolioDtlModel>();
		List<clsFolioTaxDtl> listFolioTaxDtl = new ArrayList<clsFolioTaxDtl>();		
		List<String> listDocNo = new ArrayList<String>();
		boolean flgDupRoomTerrif=false;

	    if(null!=objFolioHd.getListFolioDtlModel() && objFolioHd.getListFolioDtlModel().size()>0)
	    {
	    	for(clsFolioDtlModel obFolioDtlModel:objFolioHd.getListFolioDtlModel())
	    	{
	    		if(obFolioDtlModel.getStrRevenueType().equals(objBean.getStrFolioType()) && obFolioDtlModel.getDteDocDate().split(" ")[0].equals(PMSDate))
	    		{
	    			flgDupRoomTerrif=true;
	    			obFolioDtlModel.setDblDebitAmt(roomTerrif+obFolioDtlModel.getDblDebitAmt());
	    			obFolioDtlModel.setDblBalanceAmt(objBean.getDblOriginalPostingAmt()-roomTerrif);
	    			break;
	    		}
	    		else
	    		{
	    			listFolioDtl=objFolioHd.getListFolioDtlModel();
	    		}
	    		
	    	}
	    }
	    clsFolioDtlModel objFolioDtl = null; 	    	
	    if(!flgDupRoomTerrif)
	    {
		    objFolioDtl = new clsFolioDtlModel();
			objFolioDtl.setStrDocNo(docNo);
			objFolioDtl.setDteDocDate(PMSDate);
			objFolioDtl.setDblDebitAmt(roomTerrif);
			objFolioDtl.setDblBalanceAmt(objBean.getDblOriginalPostingAmt()-roomTerrif);
			objFolioDtl.setDblCreditAmt(0);
			if(objBean.getStrFolioType().equals("Room"))
			{
				objFolioDtl.setStrPerticulars("Room Revenue");
			}
			else
			{
				objFolioDtl.setStrPerticulars("Package");
			}
			objFolioDtl.setStrRevenueType(objBean.getStrFolioType());
			objFolioDtl.setStrRevenueCode(objBean.getStrRoomNo());
			listFolioDtl.add(objFolioDtl);
	    }

		if(!hmTaxCalDtl.isEmpty())
		{
			List<clsTaxCalculation> listTaxCal = hmTaxCalDtl.get(objBean.getStrRoomNo());
			for (clsTaxCalculation objTaxCal : listTaxCal) {
				clsFolioTaxDtl objFolioTaxDtl = new clsFolioTaxDtl();
				objFolioTaxDtl.setStrDocNo(docNo);
				objFolioTaxDtl.setStrTaxCode(objTaxCal.getStrTaxCode());
				objFolioTaxDtl.setStrTaxDesc(objTaxCal.getStrTaxDesc());
				objFolioTaxDtl.setDblTaxableAmt(objTaxCal.getDblTaxableAmt());
				objFolioTaxDtl.setDblTaxAmt(objTaxCal.getDblTaxAmt());
				listFolioTaxDtl.add(objFolioTaxDtl);
			}
		}
		if (!extraBedCode.isEmpty()) {
			List listExtraBed = objExtraBedMasterDao.funGetExtraBedMaster(extraBedCode, clientCode);
			clsExtraBedMasterModel objExtraBedMaster = (clsExtraBedMasterModel) listExtraBed.get(0);

			doc = objPMSUtility.funGenerateFolioDocForRoom("RoomFolio");
			docNo = "RM" + String.format("%06d", doc);

			objFolioDtl = new clsFolioDtlModel();
			objFolioDtl.setStrDocNo(docNo);
			objFolioDtl.setDteDocDate(PMSDate);
			objFolioDtl.setDblDebitAmt(objExtraBedMaster.getDblChargePerBed());
			objFolioDtl.setDblBalanceAmt(0);
			objFolioDtl.setDblCreditAmt(0);
			objFolioDtl.setStrPerticulars("Extra Bed Charges");
			objFolioDtl.setStrRevenueType("ExtraBed");
			objFolioDtl.setStrRevenueCode(objExtraBedMaster.getStrExtraBedTypeCode());
			listFolioDtl.add(objFolioDtl);

			objTaxProductDtl = new clsTaxProductDtl();
			objTaxProductDtl.setStrTaxProdCode(objExtraBedMaster.getStrExtraBedTypeCode());
			objTaxProductDtl.setStrTaxProdName("");
			objTaxProductDtl.setDblTaxProdAmt(objExtraBedMaster.getDblChargePerBed());
			List<clsTaxProductDtl> listTaxProdDtlForExtraBed = new ArrayList<clsTaxProductDtl>();
			listTaxProdDtlForExtraBed.add(objTaxProductDtl);
			Map<String, List<clsTaxCalculation>> hmTaxCalDtlForExtraBed = objPMSUtility.funCalculatePMSTax(listTaxProdDtlForExtraBed, "Room Night");
			List<clsTaxCalculation> listTaxCalForExtraBed = hmTaxCalDtlForExtraBed.get(objExtraBedMaster.getStrExtraBedTypeCode());
			for (clsTaxCalculation objTaxCal : listTaxCalForExtraBed) {
				clsFolioTaxDtl objFolioTaxDtl = new clsFolioTaxDtl();
				objFolioTaxDtl.setStrDocNo(docNo);
				objFolioTaxDtl.setStrTaxCode(objTaxCal.getStrTaxCode());
				objFolioTaxDtl.setStrTaxDesc(objTaxCal.getStrTaxDesc());
				objFolioTaxDtl.setDblTaxableAmt(objTaxCal.getDblTaxableAmt());
				objFolioTaxDtl.setDblTaxAmt(objTaxCal.getDblTaxAmt());
				listFolioTaxDtl.add(objFolioTaxDtl);
			}
		}
		//objWebPMSUtility.funExecuteUpdate("delete from tblfoliodtl where strFolioNo='"+objFolioHd.getStrFolioNo()+"' and strRevenueType='Room' or strRevenueType='Package' and strClientCode='"+clientCode+"'", "sql");
		objFolioHd.setListFolioDtlModel(listFolioDtl);
		objFolioHd.setListFolioTaxDtlModel(listFolioTaxDtl);
		objFolioService.funAddUpdateFolioHd(objFolioHd);

		return docNo;
	}

}
