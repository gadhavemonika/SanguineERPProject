package com.sanguine.webpms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsFolioDtlBean;
import com.sanguine.webpms.bean.clsFolioHdBean;
import com.sanguine.webpms.model.clsFolioDtlModel;
import com.sanguine.webpms.model.clsFolioHdModel;
import com.sanguine.webpms.service.clsFolioService;

@Controller
public class clsFolioController {

	@Autowired
	private clsFolioService objFolioService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	clsPMSUtilityFunctions objPMSUtility;

	// Open Folio
	@RequestMapping(value = "/frmFolio", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmFolio_1", "command", new clsFolioHdModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmFolio", "command", new clsFolioHdModel());
		} else {
			return null;
		}
	}

	public clsFolioHdModel funPrepareFolioModel(clsFolioHdBean objFolioBean, String clientCode, HttpServletRequest req) {
		String transaDate = objGlobal.funGetCurrentDateTime("dd-MM-yyyy").split(" ")[0];
		// String
		// folioNo=objGlobal.funGeneratePMSDocumentCode("frmFolio",transaDate,req);

		String folioNo = "";
		clsFolioHdModel objFolioModel = new clsFolioHdModel();

		/*
		 * String sql="select strFolioNo from tblfoliohd " +
		 * " where strCheckInNo='"
		 * +objFolioBean.getStrCheckInNo()+"' and strClientCode='"
		 * +clientCode+"' "; List
		 * listCheckInData=objGlobalFunctionsService.funGetListModuleWise(sql,
		 * "sql"); if(listCheckInData.size()>0) {
		 * folioNo=listCheckInData.get(0).toString(); } else {
		 */
	
		
		if(null!=objFolioBean.getStrFolioNo())
		{
			if(objFolioBean.getStrFolioNo().isEmpty())
			{
				long docNo = objPMSUtility.funGenerateFolioNo("Folio");
				folioNo = "FO" + String.format("%06d", docNo);
			}
			else
			{
				folioNo=objFolioBean.getStrFolioNo();
			}
		}
		else
		{
			long docNo = objPMSUtility.funGenerateFolioNo("Folio");
			folioNo = "FO" + String.format("%06d", docNo);
		}
		// }

		objFolioModel.setStrFolioNo(folioNo);
		objFolioModel.setStrRegistrationNo(objFolioBean.getStrRegistrationNo());
		objFolioModel.setStrReservationNo(objFolioBean.getStrReservationNo());
		objFolioModel.setStrWalkInNo(objFolioBean.getStrWalkInNo());
		objFolioModel.setStrCheckInNo(objFolioBean.getStrCheckInNo());
		objFolioModel.setStrRoomNo(objFolioBean.getStrRoomNo());
		objFolioModel.setDteArrivalDate(objFolioBean.getDteArrivalDate());
		objFolioModel.setDteDepartureDate(objFolioBean.getDteDepartureDate());
		objFolioModel.setTmeArrivalTime(objFolioBean.getTmeArrivalTime());
		objFolioModel.setTmeDepartureTime(objFolioBean.getTmeDepartureTime());
		objFolioModel.setStrExtraBedCode(objFolioBean.getStrExtraBedCode());
		objFolioModel.setStrGuestCode(objFolioBean.getStrGuestCode());
		objFolioModel.setStrClientCode(clientCode);

		/*
		 * List<clsFolioDtlModel> listFolioDtlModel=new
		 * ArrayList<clsFolioDtlModel>(); List<clsFolioDtlBean>
		 * listFolioDtlBean=objFolioBean.getListFolioDtlBean();
		 * for(clsFolioDtlBean objFolioDtlBean:listFolioDtlBean) {
		 * clsFolioDtlModel objFolioDtlModel=new clsFolioDtlModel();
		 * objFolioDtlModel.setStrDocNo(objFolioDtlBean.getStrDocNo());
		 * objFolioDtlModel.setDteDocDate(objFolioDtlBean.getDteDocDate());
		 * objFolioDtlModel
		 * .setStrPerticulars(objFolioDtlBean.getStrPerticulars());
		 * objFolioDtlModel.setDblDebitAmt(objFolioDtlBean.getDblDebitAmt());
		 * objFolioDtlModel.setDblCreditAmt(objFolioDtlBean.getDblCreditAmt());
		 * 
		 * listFolioDtlModel.add(objFolioDtlModel); }
		 * objFolioModel.setListFolioDtlModel(listFolioDtlModel);
		 */

		return objFolioModel;
	}

}
