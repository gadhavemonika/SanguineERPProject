package com.sanguine.webpms.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.dao.clsExtraBedMasterDao;
import com.sanguine.webpms.dao.clsGuestMasterDao;
import com.sanguine.webpms.model.clsBillDtlModel;
import com.sanguine.webpms.model.clsBillHdModel;
import com.sanguine.webpms.service.clsBillService;
import com.sanguine.webpms.service.clsCheckInService;
import com.sanguine.webpms.service.clsFolioService;
import com.sanguine.webpms.service.clsGuestMasterService;

@Controller
public class clsSplitBillController {

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
	private clsBillService objBillService;

	// Open CheckIn
	@RequestMapping(value = "/frmSplitBill", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSplitBill_1", "command", new clsBillHdModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSplitBill", "command", new clsBillHdModel());
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/splitingBill", method = RequestMethod.POST)
	public ModelAndView funSplitBillDo(@ModelAttribute("command") @Valid clsBillHdModel objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String splitBillCodes = "",splitTypeName="",message="unsuccessfully";
		boolean flagForGuestWise=false;
		if (!result.hasErrors()) {

			if (!objBean.getStrBillNo().contains("-")) {
				boolean flgDeleteMainHdData = false;
				String sql = "";
				String[] arrSplitType = objBean.getStrSplitType().split(",");
				int i = 1;
				int guestCount=0;
				for (String splitType : arrSplitType) {

					double grandAmt = 0.00;

					clsBillHdModel objBillHdModel = objBillService.funLoadBill(objBean.getStrBillNo(), clientCode);

					objBillHdModel.setStrBillNo(objBean.getStrBillNo() + "-" + i);
					objBillHdModel.setStrUserEdited(clientCode);
					objBillHdModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					List<clsBillDtlModel> tempDtl = new ArrayList<clsBillDtlModel>();
					
					if (splitType.equalsIgnoreCase("GUEST WISE")) {
						
						sql = "select count(a.strGuestCode) from tblcheckindtl a,tblbillhd b "
							+ " where a.strCheckInNo=b.strCheckInNo and b.strBillNo='" + objBean.getStrBillNo() + "' and b.strClientCode='"+clientCode+"' "; 
						List listDtl = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
						if(listDtl.size()>0)
						{
							for (int cnt = 0; cnt < listDtl.size(); cnt++) {
								guestCount = Integer.parseInt(listDtl.get(cnt).toString());
							}
						}
						
						if(guestCount>1)
						{	
						sql = " Select *  from tblbilldtl a where a.strBillNo='" + objBean.getStrBillNo() + "' and a.strClientCode='" + clientCode + "' ";
						listDtl = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");

						// for(clsBillDtlModel dtlModel :listDtl)
						for (int j = 0; j < listDtl.size(); j++) {
							Object[] objMdl = (Object[]) listDtl.get(j);
							clsBillDtlModel billDtlModel = new clsBillDtlModel();
							grandAmt += Double.parseDouble(objMdl[7].toString())/guestCount;
							billDtlModel.setStrFolioNo(objMdl[1].toString());
							billDtlModel.setDteDocDate(objMdl[2].toString());
							billDtlModel.setStrDocNo(objMdl[3].toString());
							billDtlModel.setStrPerticulars(objMdl[4].toString());
							billDtlModel.setStrRevenueType(objMdl[5].toString());
							billDtlModel.setStrRevenueCode(objMdl[6].toString());
							billDtlModel.setDblDebitAmt(Double.parseDouble(objMdl[7].toString())/guestCount);
							billDtlModel.setDblCreditAmt(Double.parseDouble(objMdl[8].toString())/guestCount);
							billDtlModel.setDblBalanceAmt(Double.parseDouble(objMdl[9].toString())/guestCount);
							tempDtl.add(billDtlModel);
							

						}
						flagForGuestWise=true;
						}
						if(flagForGuestWise)
						{
							objBillHdModel.setListBillDtlModels(tempDtl);
							// objBillHdModel.setSetBillDtlModels(tempDtl);
							objBillHdModel.setDblGrandTotal(grandAmt);
							objBillService.funAddUpdateBillHd(objBillHdModel);
							flgDeleteMainHdData = true;
							splitBillCodes += objBillHdModel.getStrBillNo() + ",";
							i++;
							splitTypeName = splitType;
						}
					}
					else
					{
						sql = " Select *  from tblbilldtl a where a.strBillNo='" + objBean.getStrBillNo() + "' and a.strRevenueType='" + splitType + "' and a.strClientCode='" + clientCode + "' ";
						List listDtl = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");

						// for(clsBillDtlModel dtlModel :listDtl)
						for (int j = 0; j < listDtl.size(); j++) {
							Object[] objMdl = (Object[]) listDtl.get(j);
							clsBillDtlModel billDtlModel = new clsBillDtlModel();
							if (splitType.equalsIgnoreCase(objMdl[5].toString())) {
								grandAmt += Double.parseDouble(objMdl[7].toString());
								billDtlModel.setStrFolioNo(objMdl[1].toString());
								billDtlModel.setDteDocDate(objMdl[2].toString());
								billDtlModel.setStrDocNo(objMdl[3].toString());
								billDtlModel.setStrPerticulars(objMdl[4].toString());
								billDtlModel.setStrRevenueType(objMdl[5].toString());
								billDtlModel.setStrRevenueCode(objMdl[6].toString());
								billDtlModel.setDblDebitAmt(Double.parseDouble(objMdl[7].toString()));
								billDtlModel.setDblCreditAmt(Double.parseDouble(objMdl[8].toString()));
								billDtlModel.setDblBalanceAmt(Double.parseDouble(objMdl[9].toString()));
								tempDtl.add(billDtlModel);
							}

						}
						objBillHdModel.setListBillDtlModels(tempDtl);
						// objBillHdModel.setSetBillDtlModels(tempDtl);
						objBillHdModel.setDblGrandTotal(grandAmt);
						objBillService.funAddUpdateBillHd(objBillHdModel);
						flgDeleteMainHdData = true;
						splitBillCodes += objBillHdModel.getStrBillNo() + ",";
						i++;
						splitTypeName = splitType;
					}
					
					
				}

				double grandAmtRest = 0.00;
				List arrSplitTypeRest = new ArrayList<String>();
				
				
				if (arrSplitType.length != 4) {

					clsBillHdModel objBillHdModel = objBillService.funLoadBill(objBean.getStrBillNo(), clientCode);
					objBillHdModel.setStrBillNo(objBean.getStrBillNo() + "-" + i);
					objBillHdModel.setStrUserEdited(clientCode);
					objBillHdModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					grandAmtRest = objBillHdModel.getDblGrandTotal();
					String fillter = "";
					for (String splitType : arrSplitType) 
					{
						if (!splitType.equalsIgnoreCase("GUEST WISE")) 
						{
						fillter += "a.strRevenueType<>'" + splitType + "' or ";
						}
					}
					

					List<clsBillDtlModel> tempDtl = new ArrayList<clsBillDtlModel>();
					
					double debitAmt = 0.00;
					double criditAmt = 0.00;
					double balAmt = 0.00;
					String revenuType = "";
					boolean flgCheck = false;
					clsBillDtlModel billDtlModel = new clsBillDtlModel();
					if(fillter.equalsIgnoreCase(""))
					{
						if(flagForGuestWise)
						{
							sql = " Select *  from tblbilldtl a where a.strBillNo='" + objBean.getStrBillNo() + "'  and a.strClientCode='" + clientCode + "' ";
							List listDtl = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");

							for (int j = 0; j < listDtl.size(); j++) {
								String chekSplit = "";
								Object[] objMdl = (Object[]) listDtl.get(j);
								billDtlModel = new clsBillDtlModel();
								billDtlModel.setStrFolioNo(objMdl[1].toString());
								billDtlModel.setDteDocDate(objMdl[2].toString());
								billDtlModel.setStrDocNo(objMdl[3].toString());
								billDtlModel.setStrPerticulars(objMdl[4].toString());
								billDtlModel.setStrRevenueType( objMdl[5].toString());
								billDtlModel.setStrRevenueCode(objMdl[6].toString());
								billDtlModel.setDblDebitAmt(Double.parseDouble(objMdl[7].toString())/guestCount);
								billDtlModel.setDblCreditAmt( Double.parseDouble(objMdl[8].toString())/guestCount);
								billDtlModel.setDblBalanceAmt(Double.parseDouble(objMdl[9].toString())/guestCount);
								tempDtl.add(billDtlModel);
								
								grandAmtRest -= Double.parseDouble(objMdl[7].toString())/guestCount;

							}
						}
						if(flagForGuestWise)
						{
							objBillHdModel.setDblGrandTotal(objBillHdModel.getDblGrandTotal() - grandAmtRest);
							objBillHdModel.setListBillDtlModels(tempDtl);
							// objBillHdModel.setSetBillDtlModels(tempDtl);
							objBillService.funAddUpdateBillHd(objBillHdModel);
							flgDeleteMainHdData = true;
							splitBillCodes += objBillHdModel.getStrBillNo() + ",";
						}
					}
					else
					{
						fillter = fillter.substring(0, fillter.length() - 3);
						sql = " Select *  from tblbilldtl a where a.strBillNo='" + objBean.getStrBillNo() + "'  and a.strClientCode='" + clientCode + "' and " + fillter;
						List listDtl = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");

						for (int j = 0; j < listDtl.size(); j++) {
							String chekSplit = "";
							Object[] objMdl = (Object[]) listDtl.get(j);
							billDtlModel = new clsBillDtlModel();
							debitAmt += Double.parseDouble(objMdl[7].toString());
							criditAmt += Double.parseDouble(objMdl[8].toString());
							balAmt += Double.parseDouble(objMdl[9].toString());
							revenuType += objMdl[5].toString() + ",";

							billDtlModel.setStrFolioNo(objMdl[1].toString());
							billDtlModel.setDteDocDate(objMdl[2].toString());
							billDtlModel.setStrDocNo(objMdl[3].toString());
							billDtlModel.setStrPerticulars(objMdl[4].toString());
							billDtlModel.setStrRevenueType(revenuType);
							billDtlModel.setStrRevenueCode(objMdl[6].toString());
							billDtlModel.setDblDebitAmt(debitAmt);
							billDtlModel.setDblCreditAmt(criditAmt);
							billDtlModel.setDblBalanceAmt(balAmt);

							flgCheck = true;

							grandAmtRest -= Double.parseDouble(objMdl[7].toString());

						}
						if (flgCheck) {
							tempDtl.add(billDtlModel);
						}
						
						objBillHdModel.setDblGrandTotal(objBillHdModel.getDblGrandTotal() - grandAmtRest);
						objBillHdModel.setListBillDtlModels(tempDtl);
						// objBillHdModel.setSetBillDtlModels(tempDtl);
						objBillService.funAddUpdateBillHd(objBillHdModel);
						flgDeleteMainHdData = true;
						splitBillCodes += objBillHdModel.getStrBillNo() + ",";
					}
	
				}
				

				if (flgDeleteMainHdData) {
					clsBillHdModel objBillHdModelForDelete = objBillService.funLoadBill(objBean.getStrBillNo(), clientCode);
					objBillService.funDeleteBill(objBillHdModelForDelete);
					message="successfully";
				}
				

			}

		}

		req.getSession().setAttribute("success", true);
		req.getSession().setAttribute("successMessage", "Bill Splitted. : "+message.concat(splitBillCodes));

		return new ModelAndView("redirect:/frmSplitBill.html?saddr=" + urlHits);
	}
	// else
	// {
	// return new ModelAndView("frmCheckOut?saddr=" + urlHits);
	// }

}
