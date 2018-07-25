package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsBillSeriesDtlBean;
import com.sanguine.webpos.bean.clsPOSPropertySetupBean;
import com.sanguine.webpos.bean.clsPrinterSetupBean;

@Controller
public class clsPOSPropertySetupController {
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	@Autowired
	private ServletContext servletContext;

	Date dte = new Date();
	int yy = dte.getYear() + 1900;
	int mm = dte.getMonth() + 1;
	int dd = dte.getDate();
	String dteEndDate = yy + "-" + mm + "-" + dd;
	boolean JioDeviceIDFound = false;
	String JioDeviceIDFromDB = "";

	@RequestMapping(value = "/frmPOSPropertySetup", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsPOSPropertySetupBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {

		Map mapPOS = new HashMap();
		Map mapPOSForDayEnd = new HashMap();
		Map mapArea = new HashMap();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String posCode = request.getSession().getAttribute("loginPOS").toString();

		JSONArray jArrList = new JSONArray();
		jArrList = objPOSGlobal.funGetAllPOSForMaster(clientCode);
		mapPOS.put("All", "All");
		if (jArrList != null) {
			for (int i = 0; i < jArrList.size(); i++) {
				JSONObject josnObjRet = (JSONObject) jArrList.get(i);
				mapPOSForDayEnd.put(josnObjRet.get("strPosCode"), josnObjRet.get("strPosName"));
				mapPOS.put(josnObjRet.get("strPosCode"), josnObjRet.get("strPosName"));
			}
		}
		model.put("posList", mapPOS);
		model.put("posListForDayEnd", mapPOSForDayEnd);

		jArrList = new JSONArray();
		jArrList = objPOSGlobal.funGetAllAreaForMaster(clientCode);
		mapArea.put("All", "All");
		if (jArrList != null) {
			for (int i = 0; i < jArrList.size(); i++) {
				JSONObject josnObjRet = (JSONObject) jArrList.get(i);

				mapArea.put(josnObjRet.get("strAreaCode"), josnObjRet.get("strAreaName"));
			}
		}
		model.put("areaList", mapArea);

		jArrList = new JSONArray();
		jArrList = objPOSGlobal.funGetAllAreaForMaster(clientCode);
		if (jArrList != null) {
			for (int i = 0; i < jArrList.size(); i++) {
				JSONObject josnObjRet = (JSONObject) jArrList.get(i);

				mapArea.put(josnObjRet.get("strAreaCode"), josnObjRet.get("strAreaName"));
			}
		}
		model.put("areaList", mapArea);

		return new ModelAndView("frmPOSPropertySetup");

	}

	@RequestMapping(value = "/loadPOSWisePropertySetupData", method = RequestMethod.GET)
	public @ResponseBody clsPOSPropertySetupBean funSetPOSWiseData(@RequestParam("posCode") String posCode, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		clsPOSPropertySetupBean objBean = null;

		/*
		 * JSONObject jObj =
		 * objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController
		 * .POSWSURL+"/WebPOSSetup/funGetPos?posCode="+posCode); long
		 * count=(long)jObj.get("count");
		 */
		objBean = funFillPOSBean(clientCode, posCode);

		return objBean;
	}

	@RequestMapping(value = "/funGetPos", method = RequestMethod.GET)
	public @ResponseBody JSONObject funGetPos(@RequestParam("posCode") String posCode, HttpServletRequest request) {

		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSSetup/funGetPos?posCode=" + posCode);

		return jObj;
	}

	@RequestMapping(value = "/loadPOSPropertySetupData", method = RequestMethod.GET)
	public @ResponseBody clsPOSPropertySetupBean funSetSearchFields(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String posCode = request.getSession().getAttribute("loginPOS").toString();

		clsPOSPropertySetupBean objBean = null;

		objBean = funFillPOSBean(clientCode, posCode);

		return objBean;
	}

	@RequestMapping(value = "/loadPrinterDtl", method = RequestMethod.GET)
	public @ResponseBody clsPOSPropertySetupBean funSetPrinterDtl(HttpServletRequest request) {

		List<clsPrinterSetupBean> listBillSeriesDtl = new ArrayList<clsPrinterSetupBean>();
		JSONArray jArr = new JSONArray();
		clsPOSPropertySetupBean objBean = new clsPOSPropertySetupBean();
		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSSetup/funGetPrinterDtl");
		jArr = (JSONArray) jObj.get("printerSetup");
		if (null != jArr) {
			for (int i = 0; i < jArr.size(); i++) {
				JSONObject jobj = (JSONObject) jArr.get(i);
				clsPrinterSetupBean obj = new clsPrinterSetupBean();
				obj.setStrCostCenterCode((String) jobj.get("strCostCenterCode"));
				obj.setStrCostCenterName((String) jobj.get("strCostCenterName"));
				obj.setStrPrimaryPrinterPort((String) jobj.get("PrimaryPrinter"));
				obj.setStrSecondaryPrinterPort((String) jobj.get("SecondaryPrinter"));
				obj.setStrPrintOnBothPrintersYN((String) jobj.get("PrintOnBothPrintersYN"));

				listBillSeriesDtl.add(obj);
			}
			objBean.setListPrinterDtl(listBillSeriesDtl);
		}

		return objBean;
	}

	@RequestMapping(value = "/loadOldSBillSeriesSetup", method = RequestMethod.GET)
	public @ResponseBody JSONObject funSetBillSeries(@RequestParam("posCode") String posCode, HttpServletRequest request) {

		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSSetup/loadOldSBillSeriesSetup?posCode=" + posCode);

		return jObj;
	}

	@RequestMapping(value = "/loadOldBillSeries", method = RequestMethod.GET)
	public @ResponseBody clsPOSPropertySetupBean funSetSelectedBillSeries(@RequestParam("posCode") String posCode, HttpServletRequest request) {

		List<clsBillSeriesDtlBean> listBillSeriesDtl = new ArrayList<clsBillSeriesDtlBean>();
		JSONArray jArr = new JSONArray();
		clsPOSPropertySetupBean objBean = new clsPOSPropertySetupBean();
		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSSetup/funGetOldBillSeries?posCode=" + posCode);
		jArr = (JSONArray) jObj.get("Billseries");
		if (null != jArr) {
			for (int i = 0; i < jArr.size(); i++) {
				JSONObject jobj = (JSONObject) jArr.get(i);
				clsBillSeriesDtlBean obj = new clsBillSeriesDtlBean();
				obj.setStrBillSeries((String) jobj.get("strBillSeries"));
				obj.setStrCodes((String) jobj.get("strCodes"));
				obj.setStrNames((String) jobj.get("strNames"));
				obj.setStrPrintGTOfOtherBills((String) jobj.get("strPrintGTOfOtherBills"));
				obj.setStrPrintInclusiveOfTaxOnBill((String) jobj.get("strPrintInclusiveOfTaxOnBill"));

				listBillSeriesDtl.add(obj);
			}
			objBean.setListBillSeriesDtl(listBillSeriesDtl);
		}

		return objBean;
	}

	@RequestMapping(value = "/loadSelectedTypeDtlTable", method = RequestMethod.GET)
	public @ResponseBody List funLoadSelectedTypeDtlTable(@RequestParam("strType") String strType, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		JSONArray jArrList = null;
		List listTypeData = new ArrayList();
		switch (strType) {
		case "Group":

			jArrList = objPOSGlobal.funGetAllGroup(clientCode);

			if (null != jArrList) {
				for (int cnt = 0; cnt < jArrList.size(); cnt++) {
					JSONObject jobj = (JSONObject) jArrList.get(cnt);
					listTypeData.add(jobj);
				}
			}
			break;
		case "Sub Group":
			jArrList = objPOSGlobal.funGetAllSubGroup(clientCode);
			if (null != jArrList) {
				for (int cnt = 0; cnt < jArrList.size(); cnt++) {
					JSONObject jobj = (JSONObject) jArrList.get(cnt);
					listTypeData.add(jobj);
				}
			}
			break;
		case "Menu Head":
			jArrList = objPOSGlobal.funGetAllMenuHeadForMaster(clientCode);
			if (null != jArrList) {
				for (int cnt = 0; cnt < jArrList.size(); cnt++) {
					JSONObject jobj = (JSONObject) jArrList.get(cnt);
					listTypeData.add(jobj);
				}
			}
			break;
		case "Revenue Head":
			jArrList = objPOSGlobal.funGetAllRevenueHead(clientCode);
			if (null != jArrList) {
				for (int cnt = 0; cnt < jArrList.size(); cnt++) {

					listTypeData.add((String) jArrList.get(cnt));
				}
			}
			break;
		}
		return listTypeData;
	}

	@RequestMapping(value = "/savePOSPropertySetup", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSPropertySetupBean objBean, BindingResult result, HttpServletRequest req, @RequestParam("companyLogo") MultipartFile file) {

		String posCode = "";
		try {

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjAreaMaster = new JSONObject();

			if (file.getSize() != 0) {
				Blob blobProdImage = Hibernate.createBlob(file.getInputStream());
				jObjAreaMaster.put("ClientImage", blobProdImage);
				FileOutputStream fileOuputStream = null;
				try {

					// Blob blob = Hibernate.createBlob(file.getInputStream());
					byte[] bytes = file.getBytes();
					String imagePath = servletContext.getRealPath("/resources/images");
					// int blobLength = (int) blob.length();
					// byte[] blobAsBytes = blob.getBytes(1, blobLength);

					fileOuputStream = new FileOutputStream(imagePath + "/imgClientImage.jpg");
					fileOuputStream.write(bytes);
					fileOuputStream.close();

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			jObjAreaMaster.put("User", webStockUserCode);
			jObjAreaMaster.put("dteEndDate", dteEndDate);
			jObjAreaMaster.put("strPosCode", objBean.getStrPosCode());
			jObjAreaMaster.put("strClientCode", objBean.getStrClientCode());

			jObjAreaMaster.put("strClientName", objBean.getStrClientName());
			jObjAreaMaster.put("strAddrLine1", objBean.getStrAddrLine1());

			jObjAreaMaster.put("strAddrLine2", objBean.getStrAddrLine2());
			jObjAreaMaster.put("strAddrLine3", objBean.getStrAddrLine3());

			jObjAreaMaster.put("strCity", objBean.getStrCity());
			jObjAreaMaster.put("strPinCode", objBean.getStrPinCode());

			jObjAreaMaster.put("strState", objBean.getStrState());
			jObjAreaMaster.put("strCountry", objBean.getStrCountry());

			jObjAreaMaster.put("strTelephone", objBean.getStrTelephone());
			jObjAreaMaster.put("strEmail", objBean.getStrEmail());

			jObjAreaMaster.put("strNatureOfBussness", objBean.getStrNatureOfBussness());
			jObjAreaMaster.put("strBillFooter", objBean.getStrBillFooter());

			jObjAreaMaster.put("strAdvRecPrintCount", objBean.getStrAdvRecPrintCount());
			jObjAreaMaster.put("strBillPrintMode", objBean.getStrBillPrintMode());

			jObjAreaMaster.put("strPrintingType", objBean.getStrPrintingType());
			jObjAreaMaster.put("strBillFormat", objBean.getStrBillFormat());

			jObjAreaMaster.put("chkShowBills", objGlobal.funIfNull(objBean.getChkShowBills(), "N", "Y"));
			jObjAreaMaster.put("chkNegBilling", objGlobal.funIfNull(objBean.getChkNegBilling(), "N", "Y"));

			jObjAreaMaster.put("chkPrintKotForDirectBiller", objGlobal.funIfNull(objBean.getChkPrintKotForDirectBiller(), "N", "Y"));
			// jObjAreaMaster.put("chkPrintKotForDirectBiller",objGlobal.funIfNull(
			// objBean.getChkPrintKotForDirectBiller(),"N","Y"));

			jObjAreaMaster.put("chkEnableKOT", objGlobal.funIfNull(objBean.getChkEnableKOT(), "N", "Y"));
			jObjAreaMaster.put("chkMultiBillPrint", objGlobal.funIfNull(objBean.getChkMultiBillPrint(), "N", "Y"));

			jObjAreaMaster.put("chkDayEnd", objGlobal.funIfNull(objBean.getChkDayEnd(), "N", "Y"));
			jObjAreaMaster.put("chkPrintShortNameOnKOT", objGlobal.funIfNull(objBean.getChkPrintShortNameOnKOT(), "N", "Y"));

			jObjAreaMaster.put("chkMultiKOTPrint", objGlobal.funIfNull(objBean.getChkMultiKOTPrint(), "N", "Y"));
			jObjAreaMaster.put("strCustSeries", objBean.getStrCustSeries());

			jObjAreaMaster.put("chkPrintInvoiceOnBill", objGlobal.funIfNull(objBean.getChkPrintInvoiceOnBill(), "N", "Y"));
			jObjAreaMaster.put("chkPrintTDHItemsInBill", objGlobal.funIfNull(objBean.getChkPrintTDHItemsInBill(), "N", "Y"));

			jObjAreaMaster.put("chkManualBillNo", objGlobal.funIfNull(objBean.getChkManualBillNo(), "N", "Y"));
			jObjAreaMaster.put("chkPrintInclusiveOfAllTaxesOnBill", objGlobal.funIfNull(objBean.getChkPrintInclusiveOfAllTaxesOnBill(), "N", "Y"));
			jObjAreaMaster.put("chkEffectOnPSP", objGlobal.funIfNull(objBean.getChkEffectOnPSP(), "N", "Y"));
			jObjAreaMaster.put("chkPrintVatNo", objGlobal.funIfNull(objBean.getChkPrintVatNo(), "N", "Y"));

			jObjAreaMaster.put("strVatNo", objBean.getStrVatNo());
			jObjAreaMaster.put("intBiilPaperSize", objBean.getIntBiilPaperSize());
			jObjAreaMaster.put("intColumnSize", objBean.getIntColumnSize());
			jObjAreaMaster.put("intNoOfLinesInKOTPrint", objBean.getIntNoOfLinesInKOTPrint());

			jObjAreaMaster.put("chkServiceTaxNo", objGlobal.funIfNull(objBean.getChkServiceTaxNo(), "N", "Y"));
			jObjAreaMaster.put("strServiceTaxNo", objBean.getStrServiceTaxNo());
			jObjAreaMaster.put("dblMaxDiscount", objBean.getDblMaxDiscount());
			jObjAreaMaster.put("strShowBillsDtlType", objBean.getStrShowBillsDtlType());
			jObjAreaMaster.put("strPOSType", objBean.getStrPOSType());

			jObjAreaMaster.put("strDataSendFrequency", objBean.getStrDataSendFrequency());
			jObjAreaMaster.put("strWebServiceLink", objBean.getStrWebServiceLink());
			jObjAreaMaster.put("dteHOServerDate", objBean.getDteHOServerDate());
			jObjAreaMaster.put("strChangeTheme", objBean.getStrChangeTheme());
			jObjAreaMaster.put("strDirectArea", objBean.getStrDirectArea());
			jObjAreaMaster.put("chkAreaWisePricing", objGlobal.funIfNull(objBean.getChkAreaWisePricing(), "N", "Y"));

			jObjAreaMaster.put("chkSlabBasedHomeDelCharges", objGlobal.funIfNull(objBean.getChkSlabBasedHomeDelCharges(), "N", "Y"));
			jObjAreaMaster.put("chkEditHomeDelivery", objGlobal.funIfNull(objBean.getChkEditHomeDelivery(), "N", "Y"));

			jObjAreaMaster.put("chkPrintForVoidBill", objGlobal.funIfNull(objBean.getChkPrintForVoidBill(), "N", "Y"));
			jObjAreaMaster.put("chkDirectKOTPrintMakeKOT", objGlobal.funIfNull(objBean.getChkDirectKOTPrintMakeKOT(), "N", "Y"));

			jObjAreaMaster.put("chkSkipWaiterSelection", objGlobal.funIfNull(objBean.getChkSkipWaiterSelection(), "N", "Y"));

			jObjAreaMaster.put("chkSkipPaxSelection", objGlobal.funIfNull(objBean.getChkSkipPaxSelection(), "N", "Y"));
			jObjAreaMaster.put("chkAreaMasterCompulsory", objGlobal.funIfNull(objBean.getChkAreaMasterCompulsory(), "N", "Y"));

			jObjAreaMaster.put("chkPostSalesDataToMMS", objGlobal.funIfNull(objBean.getChkPostSalesDataToMMS(), "N", "Y"));
			jObjAreaMaster.put("strItemType", objBean.getStrItemType());
			jObjAreaMaster.put("chkPrinterErrorMessage", objGlobal.funIfNull(objBean.getChkPrinterErrorMessage(), "N", "Y"));

			jObjAreaMaster.put("chkActivePromotions", objGlobal.funIfNull(objBean.getChkActivePromotions(), "N", "Y"));
			jObjAreaMaster.put("chkPrintKOTYN", objGlobal.funIfNull(objBean.getChkPrintKOTYN(), "N", "Y"));

			jObjAreaMaster.put("chkChangeQtyForExternalCode", objGlobal.funIfNull(objBean.getChkChangeQtyForExternalCode(), "N", "Y"));
			jObjAreaMaster.put("strStockInOption", objGlobal.funIfNull(objBean.getStrStockInOption(), "N", "Y"));

			jObjAreaMaster.put("chkShowItemStkColumnInDB", objGlobal.funIfNull(objBean.getChkShowItemStkColumnInDB(), "N", "Y"));

			jObjAreaMaster.put("strPriceFrom", objBean.getStrPriceFrom());
			jObjAreaMaster.put("strApplyDiscountOn", objBean.getStrApplyDiscountOn());

			jObjAreaMaster.put("chkPrintBill", objGlobal.funIfNull(objBean.getChkPrintBill(), "N", "Y"));

			jObjAreaMaster.put("chkUseVatAndServiceNoFromPos", objGlobal.funIfNull(objBean.getChkUseVatAndServiceNoFromPos(), "N", "Y"));
			jObjAreaMaster.put("chkManualAdvOrderCompulsory", objGlobal.funIfNull(objBean.getChkManualAdvOrderCompulsory(), "N", "Y"));

			jObjAreaMaster.put("chkPrintManualAdvOrderOnBill", objGlobal.funIfNull(objBean.getChkPrintManualAdvOrderOnBill(), "N", "Y"));
			jObjAreaMaster.put("chkPrintModifierQtyOnKOT", objGlobal.funIfNull(objBean.getChkPrintModifierQtyOnKOT(), "N", "Y"));

			jObjAreaMaster.put("strMenuItemSortingOn", objBean.getStrMenuItemSortingOn());

			jObjAreaMaster.put("chkBoxAllowNewAreaMasterFromCustMaster", objGlobal.funIfNull(objBean.getChkBoxAllowNewAreaMasterFromCustMaster(), "N", "Y"));
			jObjAreaMaster.put("chkAllowToCalculateItemWeight", objGlobal.funIfNull(objBean.getChkAllowToCalculateItemWeight(), "N", "Y"));
			jObjAreaMaster.put("strMenuItemDisSeq", objBean.getStrMenuItemDisSeq());

			jObjAreaMaster.put("chkItemWiseKOTPrintYN", objGlobal.funIfNull(objBean.getChkItemWiseKOTPrintYN(), "N", "Y"));
			jObjAreaMaster.put("chkItemQtyNumpad", objGlobal.funIfNull(objBean.getChkItemQtyNumpad(), "N", "Y"));

			jObjAreaMaster.put("chkSlipNoForCreditCardBillYN", objGlobal.funIfNull(objBean.getChkSlipNoForCreditCardBillYN(), "N", "Y"));
			jObjAreaMaster.put("chkPrintKOTToLocalPrinter", objGlobal.funIfNull(objBean.getChkPrintKOTToLocalPrinter(), "N", "Y"));
			jObjAreaMaster.put("chkExpDateForCreditCardBillYN", objGlobal.funIfNull(objBean.getChkExpDateForCreditCardBillYN(), "N", "Y"));
			jObjAreaMaster.put("chkDelBoyCompulsoryOnDirectBiller", objGlobal.funIfNull(objBean.getChkDelBoyCompulsoryOnDirectBiller(), "N", "Y"));

			jObjAreaMaster.put("chkSelectWaiterFromCardSwipe", objGlobal.funIfNull(objBean.getChkSelectWaiterFromCardSwipe(), "N", "Y"));
			jObjAreaMaster.put("chkEnableSettleBtnForDirectBillerBill", objGlobal.funIfNull(objBean.getChkEnableSettleBtnForDirectBillerBill(), "N", "Y"));
			jObjAreaMaster.put("chkMultipleWaiterSelectionOnMakeKOT", objGlobal.funIfNull(objBean.getChkMultipleWaiterSelectionOnMakeKOT(), "N", "Y"));
			jObjAreaMaster.put("chkDontShowAdvOrderInOtherPOS", objGlobal.funIfNull(objBean.getChkDontShowAdvOrderInOtherPOS(), "N", "Y"));

			jObjAreaMaster.put("chkMoveTableToOtherPOS", objGlobal.funIfNull(objBean.getChkMoveTableToOtherPOS(), "N", "Y"));
			jObjAreaMaster.put("chkPrintZeroAmtModifierInBill", objGlobal.funIfNull(objBean.getChkPrintZeroAmtModifierInBill(), "N", "Y"));
			jObjAreaMaster.put("chkMoveKOTToOtherPOS", objGlobal.funIfNull(objBean.getChkMoveKOTToOtherPOS(), "N", "Y"));

			jObjAreaMaster.put("chkPointsOnBillPrint", objGlobal.funIfNull(objBean.getChkPointsOnBillPrint(), "N", "Y"));
			jObjAreaMaster.put("chkCalculateTaxOnMakeKOT", objGlobal.funIfNull(objBean.getChkCalculateTaxOnMakeKOT(), "N", "Y"));

			jObjAreaMaster.put("chkCalculateDiscItemWise", objGlobal.funIfNull(objBean.getChkCalculateDiscItemWise(), "N", "Y"));

			jObjAreaMaster.put("chkTakewayCustomerSelection", objGlobal.funIfNull(objBean.getChkTakewayCustomerSelection(), "N", "Y"));
			jObjAreaMaster.put("chkSelectCustAddressForBill", objGlobal.funIfNull(objBean.getChkSelectCustAddressForBill(), "N", "Y"));

			jObjAreaMaster.put("chkGenrateMI", objGlobal.funIfNull(objBean.getChkGenrateMI(), "N", "Y"));
			jObjAreaMaster.put("chkPopUpToApplyPromotionsOnBill", objGlobal.funIfNull(objBean.getChkPopUpToApplyPromotionsOnBill(), "N", "Y"));
			jObjAreaMaster.put("strWSClientCode", objBean.getStrWSClientCode());
			jObjAreaMaster.put("intDaysBeforeOrderToCancel", objBean.getIntDaysBeforeOrderToCancel());

			jObjAreaMaster.put("chkCheckDebitCardBalOnTrans", objGlobal.funIfNull(objBean.getChkCheckDebitCardBalOnTrans(), "N", "Y"));
			jObjAreaMaster.put("intNoOfDelDaysForAdvOrder", objBean.getIntNoOfDelDaysForAdvOrder());

			jObjAreaMaster.put("chkSettlementsFromPOSMaster", objGlobal.funIfNull(objBean.getChkSettlementsFromPOSMaster(), "N", "Y"));
			jObjAreaMaster.put("chkShiftWiseDayEnd", objGlobal.funIfNull(objBean.getChkShiftWiseDayEnd(), "N", "Y"));
			jObjAreaMaster.put("intNoOfDelDaysForUrgentOrder", objBean.getIntNoOfDelDaysForUrgentOrder());
			jObjAreaMaster.put("chkProductionLinkup", objGlobal.funIfNull(objBean.getChkProductionLinkup(), "N", "Y"));
			jObjAreaMaster.put("chkLockDataOnShift", objGlobal.funIfNull(objBean.getChkLockDataOnShift(), "N", "Y"));

			jObjAreaMaster.put("chkSetUpToTimeForAdvOrder", objGlobal.funIfNull(objBean.getChkSetUpToTimeForAdvOrder(), "N", "Y"));
			jObjAreaMaster.put("chkEnableBillSeries", objGlobal.funIfNull(objBean.getChkEnableBillSeries(), "N", "Y"));

			String upToTimeForAdvOrder = objBean.getStrHours() + ":" + objBean.getStrMinutes() + " " + objBean.getStrAMPM();
			jObjAreaMaster.put("strUpToTimeForAdvOrder", upToTimeForAdvOrder);

			jObjAreaMaster.put("chkEnablePMSIntegration", objGlobal.funIfNull(objBean.getChkEnablePMSIntegration(), "N", "Y"));

			jObjAreaMaster.put("chkSetUpToTimeForUrgentOrder", objGlobal.funIfNull(objBean.getChkSetUpToTimeForUrgentOrder(), "N", "Y"));
			jObjAreaMaster.put("chkPrintTimeOnBill", objGlobal.funIfNull(objBean.getChkPrintTimeOnBill(), "N", "Y"));

			String upToTimeForUrgentOrder = objBean.getStrHoursUrgentOrder() + ":" + objBean.getStrMinutesUrgentOrder() + " " + objBean.getStrAMPMUrgent();
			jObjAreaMaster.put("strUpToTimeForUrgentOrder", upToTimeForUrgentOrder);

			jObjAreaMaster.put("chkPrintRemarkAndReasonForReprint", objGlobal.funIfNull(objBean.getChkPrintRemarkAndReasonForReprint(), "N", "Y"));

			jObjAreaMaster.put("chkEnableBothPrintAndSettleBtnForDB", objGlobal.funIfNull(objBean.getChkEnableBothPrintAndSettleBtnForDB(), "N", "Y"));
			jObjAreaMaster.put("chkCarryForwardFloatAmtToNextDay", objGlobal.funIfNull(objBean.getChkCarryForwardFloatAmtToNextDay(), "N", "Y"));

			jObjAreaMaster.put("chkOpenCashDrawerAfterBillPrint", objGlobal.funIfNull(objBean.getChkOpenCashDrawerAfterBillPrint(), "N", "Y"));
			jObjAreaMaster.put("chkShowItemDtlsForChangeCustomerOnBill", objGlobal.funIfNull(objBean.getChkShowItemDtlsForChangeCustomerOnBill(), "N", "Y"));

			jObjAreaMaster.put("chkPropertyWiseSalesOrder", objGlobal.funIfNull(objBean.getChkPropertyWiseSalesOrder(), "N", "Y"));

			jObjAreaMaster.put("chkShowPopUpForNextItemQuantity", objGlobal.funIfNull(objBean.getChkShowPopUpForNextItemQuantity(), "N", "Y"));
			jObjAreaMaster.put("strSenderEmailId", objBean.getStrSenderEmailId());
			jObjAreaMaster.put("strEmailPassword", objBean.getStrEmailPassword());
			jObjAreaMaster.put("strEmailServerName", objBean.getStrEmailServerName());

			jObjAreaMaster.put("strReceiverEmailId", objBean.getStrReceiverEmailId());
			jObjAreaMaster.put("strBodyPart", objBean.getStrBodyPart());
			jObjAreaMaster.put("strCardIntfType", objBean.getStrCardIntfType());

			jObjAreaMaster.put("strRFIDSetup", objBean.getStrRFIDSetup());
			jObjAreaMaster.put("strRFIDServerName", objBean.getStrRFIDServerName());

			jObjAreaMaster.put("strRFIDUserName", objBean.getStrRFIDUserName());
			jObjAreaMaster.put("strRFIDPassword", objBean.getStrRFIDPassword());

			jObjAreaMaster.put("strRFIDDatabaseName", objBean.getStrRFIDDatabaseName());
			jObjAreaMaster.put("strCRM", objBean.getStrCRM());
			jObjAreaMaster.put("strGetWebservice", objBean.getStrGetWebservice());
			jObjAreaMaster.put("strPostWebservice", objBean.getStrPostWebservice());

			jObjAreaMaster.put("strOutletUID", objBean.getStrOutletUID());
			jObjAreaMaster.put("strPOSID", objBean.getStrPOSID());
			jObjAreaMaster.put("strSMSType", objBean.getStrSMSType());
			jObjAreaMaster.put("strAreaSMSApi", objBean.getStrAreaSMSApi());

			jObjAreaMaster.put("chkHomeDelSMS", objGlobal.funIfNull(objBean.getChkHomeDelSMS(), "N", "Y"));

			jObjAreaMaster.put("strAreaSendHomeDeliverySMS", objBean.getStrAreaSendHomeDeliverySMS());
			jObjAreaMaster.put("chkBillSettlementSMS", objGlobal.funIfNull(objBean.getChkBillSettlementSMS(), "N", "Y"));
			jObjAreaMaster.put("strBillSeriesType", objBean.getStrBillSeriesType());

			jObjAreaMaster.put("strAreaBillSettlementSMS", objBean.getStrAreaBillSettlementSMS());
			jObjAreaMaster.put("strFTPAddress", objBean.getStrFTPAddress());

			jObjAreaMaster.put("strFTPServerUserName", objBean.getStrFTPServerUserName());
			jObjAreaMaster.put("strFTPServerPass", objBean.getStrFTPServerPass());
			jObjAreaMaster.put("strCMSIntegrationYN", objBean.getStrCMSIntegrationYN());
			jObjAreaMaster.put("strCMSWesServiceURL", objBean.getStrCMSWesServiceURL());

			jObjAreaMaster.put("chkMemberAsTable", objGlobal.funIfNull(objBean.getChkMemberAsTable(), "N", "Y"));
			jObjAreaMaster.put("chkMemberCodeForKOTJPOS", objGlobal.funIfNull(objBean.getChkMemberCodeForKOTJPOS(), "N", "Y"));
			jObjAreaMaster.put("chkMemberCodeForKotInMposByCardSwipe", objGlobal.funIfNull(objBean.getChkMemberCodeForKotInMposByCardSwipe(), "N", "Y"));

			jObjAreaMaster.put("chkMemberCodeForMakeBillInMPOS", objGlobal.funIfNull(objBean.getChkMemberCodeForMakeBillInMPOS(), "N", "Y"));
			jObjAreaMaster.put("chkMemberCodeForKOTMPOS", objGlobal.funIfNull(objBean.getChkMemberCodeForKOTMPOS(), "N", "Y"));

			jObjAreaMaster.put("chkSelectCustomerCodeFromCardSwipe", objGlobal.funIfNull(objBean.getChkSelectCustomerCodeFromCardSwipe(), "N", "Y"));
			jObjAreaMaster.put("strCMSPostingType", objBean.getStrCMSPostingType());

			jObjAreaMaster.put("strPOSForDayEnd", objBean.getStrPOSForDayEnd());
			jObjAreaMaster.put("strInrestoPOSIntegrationYN", objBean.getStrInrestoPOSIntegrationYN());
			jObjAreaMaster.put("strInrestoPOSWesServiceURL", objBean.getStrInrestoPOSWesServiceURL());

			jObjAreaMaster.put("strInrestoPOSId", objBean.getStrInrestoPOSId());
			jObjAreaMaster.put("strInrestoPOSKey", objBean.getStrInrestoPOSKey());
			jObjAreaMaster.put("strJioPOSIntegrationYN", objBean.getStrJioPOSIntegrationYN());

			jObjAreaMaster.put("strJioPOSWesServiceURL", objBean.getStrJioPOSWesServiceURL());
			jObjAreaMaster.put("strJioMID", objBean.getStrJioMID());
			jObjAreaMaster.put("strJioTID", objBean.getStrJioTID());

			jObjAreaMaster.put("strJioActivationCode", objBean.getStrJioActivationCode());
			jObjAreaMaster.put("strJioDeviceID", objBean.getStrJioDeviceID());

			jObjAreaMaster.put("chkNewBillSeriesForNewDay", objGlobal.funIfNull(objBean.getChkNewBillSeriesForNewDay(), "N", "Y"));
			jObjAreaMaster.put("chkShowReportsPOSWise", objGlobal.funIfNull(objBean.getChkShowReportsPOSWise(), "N", "Y"));

			jObjAreaMaster.put("chkEnableDineIn", objGlobal.funIfNull(objBean.getChkEnableDineIn(), "N", "Y"));
			jObjAreaMaster.put("chkAutoAreaSelectionInMakeKOT", objGlobal.funIfNull(objBean.getChkAutoAreaSelectionInMakeKOT(), "N", "Y"));

			jObjAreaMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjAreaMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

			if (objBean.getStrJioPOSIntegrationYN().equalsIgnoreCase("Y")) {
				if (!(objBean.getStrJioDeviceID().isEmpty())) {
					if (JioDeviceIDFound && ((!objBean.getStrJioDeviceID().equals(JioDeviceIDFromDB)))) {
						funSaveMapMyDevice(objBean.getStrJioMID(), objBean.getStrJioTID(), objBean.getStrJioDeviceID(), objBean.getStrJioActivationCode(), objBean.getStrPosCode());
					} else if (!JioDeviceIDFound) {
						funSaveMapMyDevice(objBean.getStrJioMID(), objBean.getStrJioTID(), objBean.getStrJioDeviceID(), objBean.getStrJioActivationCode(), objBean.getStrPosCode());
					}

				}
			}

			List<clsPrinterSetupBean> printerlist = objBean.getListPrinterDtl();
			JSONArray jArrList = new JSONArray();
			if (null != printerlist) {
				for (int i = 0; i < printerlist.size(); i++) {
					clsPrinterSetupBean obj = new clsPrinterSetupBean();
					obj = (clsPrinterSetupBean) printerlist.get(i);
					JSONObject jObjData = new JSONObject();

					jObjData.put("strCostCenterCode", obj.getStrCostCenterCode());
					jObjData.put("strCostCenterName", obj.getStrCostCenterName());
					jObjData.put("strPrimaryPrinterPort", obj.getStrPrimaryPrinterPort());
					jObjData.put("strSecondaryPrinterPort", obj.getStrSecondaryPrinterPort());
					if (obj.getStrPrintOnBothPrintersYN() != null)
						jObjData.put("strPrintOnBothPrintersYN", "Y");

					else
						jObjData.put("strPrintOnBothPrintersYN", "N");

					jArrList.add(jObjData);
				}
			}
			jObjAreaMaster.put("PrinterDetails", jArrList);

			List<clsBillSeriesDtlBean> list = objBean.getListBillSeriesDtl();
			jArrList = new JSONArray();
			if (null != list) {
				for (int i = 0; i < list.size(); i++) {
					clsBillSeriesDtlBean obj = new clsBillSeriesDtlBean();
					obj = (clsBillSeriesDtlBean) list.get(i);
					JSONObject jObjData = new JSONObject();

					jObjData.put("strCodes", obj.getStrCodes());
					jObjData.put("strNames", obj.getStrNames());
					jObjData.put("strBillSeries", obj.getStrBillSeries());

					if (obj.getStrPrintGTOfOtherBills() != null)
						jObjData.put("strPrintGTOfOtherBills", "Y");

					else
						jObjData.put("strPrintGTOfOtherBills", "N");

					if (obj.getStrPrintInclusiveOfTaxOnBill() != null)
						jObjData.put("strPrintInclusiveOfTaxOnBill", "Y");

					else
						jObjData.put("strPrintInclusiveOfTaxOnBill", "N");

					jArrList.add(jObjData);
				}
			}
			jObjAreaMaster.put("BillSeriesDetails", jArrList);

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSSetup/funSaveUpdatePropertySetup";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjAreaMaster.toString().getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = "", op = "";

			while ((output = br.readLine()) != null) {
				op += output;
			}
			System.out.println("Result= " + op);
			conn.disconnect();

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", " " + op);

			return new ModelAndView("redirect:/frmPOSPropertySetup.html");
		} catch (Exception ex) {

			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	public clsPOSPropertySetupBean funFillPOSBean(String clientCode, String posCode) {
		clsPOSPropertySetupBean objBean = new clsPOSPropertySetupBean();
		FileOutputStream fileOuputStream = null;
		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSSetup/funGetPOSWiseSetup?clientCode=" + clientCode + "&posCode=" + posCode);
		if (jObj != null && jObj.size() > 0) {
			try {
				if (jObj.get("ClientImage") != null) {

					String strContent = (String) jObj.get("ClientImage");
					;
					byte[] byteContent = strContent.getBytes();
					Blob blob = Hibernate.createBlob(byteContent);// Where
																	// connection
																	// is the
																	// connection
																	// to db
																	// object.
					// blob.setBytes(1, byteContent);
					String imagePath = servletContext.getRealPath("/resources/images");
					int blobLength = (int) blob.length();
					// byte[] blobAsBytes = blob.getBytes(1, blobLength);

					fileOuputStream = new FileOutputStream(imagePath + "/imgClientImage.jpg");
					fileOuputStream.write(byteContent);
					fileOuputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			objBean.setStrPosCode((String) jObj.get("posCode"));
			objBean.setStrPrintingType((String) jObj.get("gPrintType"));
			objBean.setIntColumnSize((long) jObj.get("gColumnSize"));
			objBean.setDblMaxDiscount((long) jObj.get("gMaxDiscount"));
			objBean.setChkAreaWisePricing((String) jObj.get("gAreaWisePricing"));
			objBean.setStrChangeTheme((String) jObj.get("gTheme"));
			objBean.setStrClientCode((String) jObj.get("gClientCode"));
			objBean.setStrClientName((String) jObj.get("gClientName"));
			objBean.setStrAddrLine1((String) jObj.get("gClientAddress1"));
			objBean.setStrAddrLine2((String) jObj.get("gClientAddress2"));
			objBean.setStrAddrLine3((String) jObj.get("gClientAddress3"));
			objBean.setStrEmail((String) jObj.get("gClientEmail"));
			objBean.setStrBillFooter((String) jObj.get("gBillFooter"));
			objBean.setIntBiilPaperSize((long) jObj.get("gBillPaperSize"));
			objBean.setChkNegBilling((String) jObj.get("gNegBilling"));
			objBean.setChkDayEnd((String) jObj.get("chkDayEnd"));
			objBean.setStrBillPrintMode((String) jObj.get("strBillPrintMode"));
			objBean.setStrCity((String) jObj.get("gCityName"));
			objBean.setStrState((String) jObj.get("gStateName"));
			objBean.setStrCountry((String) jObj.get("gCountryName"));
			objBean.setStrTelephone((long) jObj.get("gClientTelNo"));
			objBean.setStrNatureOfBussness((String) jObj.get("gNatureOfBusinnes"));
			objBean.setChkMultiBillPrint((String) jObj.get("gMultiBillPrint"));
			objBean.setChkEnableKOT((String) jObj.get("gEnableKOT"));
			objBean.setChkEffectOnPSP((String) jObj.get("gEffectOnPSP"));
			objBean.setChkPrintVatNo((String) jObj.get("gPrintVatNo"));
			objBean.setStrVatNo((String) jObj.get("gVatNo"));
			objBean.setChkShowBills((String) jObj.get("gShowBill"));
			objBean.setChkServiceTaxNo((String) jObj.get("gPrintServiceTaxNo"));
			objBean.setStrServiceTaxNo((String) jObj.get("gServiceTaxNo"));
			objBean.setChkManualBillNo((String) jObj.get("gManualBillNo"));
			objBean.setStrMenuItemDisSeq((String) jObj.get("gMenuItemSequence"));
			objBean.setStrSenderEmailId((String) jObj.get("gSenderEmailId"));
			objBean.setStrEmailPassword((String) jObj.get("gSenderMailPassword"));
			objBean.setStrBodyPart((String) jObj.get("gEmailMessage"));
			objBean.setStrEmailServerName((String) jObj.get("gEmailServerName"));
			objBean.setStrAreaSMSApi((String) jObj.get("gSMSApi"));
			objBean.setStrPOSType((String) jObj.get("gHOPOSType"));
			objBean.setStrWebServiceLink((String) jObj.get("gSanguineWebServiceURL"));
			objBean.setStrDataSendFrequency((String) jObj.get("gDataSendFrequency"));
			objBean.setStrRFIDSetup((String) jObj.get("gRFIDInterface"));
			objBean.setStrRFIDServerName((String) jObj.get("gRFIDDBServerName"));
			objBean.setStrRFIDUserName((String) jObj.get("gRFIDDBUserName"));
			objBean.setStrRFIDPassword((String) jObj.get("gRFIDDBPassword"));
			objBean.setStrRFIDDatabaseName((String) jObj.get("gRFIDDBName"));
			objBean.setChkPrintKotForDirectBiller((String) jObj.get("gKOTPrintingEnableForDirectBiller"));
			objBean.setStrPinCode((long) jObj.get("pinCode"));
			objBean.setStrMenuItemSortingOn((String) jObj.get("gMenuItemSortingOn"));
			objBean.setChkEditHomeDelivery((String) jObj.get("gEditHDCharges"));
			objBean.setChkSlabBasedHomeDelCharges((String) jObj.get("gSlabBasedHDCharges"));
			objBean.setChkSkipWaiterSelection((String) jObj.get("gSkipWaiter"));
			objBean.setChkDirectKOTPrintMakeKOT((String) jObj.get("gDirectKOTPrintingFromMakeKOT"));
			objBean.setChkSkipPaxSelection((String) jObj.get("gSkipPax"));
			objBean.setStrCRM((String) jObj.get("gCRMInterface"));
			objBean.setStrGetWebservice((String) jObj.get("gGetWebserviceURL"));
			objBean.setStrPostWebservice((String) jObj.get("gPostWebserviceURL"));
			objBean.setStrOutletUID((String) jObj.get("gOutletUID"));
			objBean.setStrPOSID((String) jObj.get("gPOSID"));
			objBean.setStrStockInOption((String) jObj.get("gStockInOption"));
			objBean.setStrCustSeries((String) jObj.get("custSeries"));
			objBean.setStrAdvRecPrintCount((long) jObj.get("gAdvRecPrintCount"));
			objBean.setStrAreaSendHomeDeliverySMS((String) jObj.get("gHomeDeliverySMS"));
			objBean.setStrAreaBillSettlementSMS((String) jObj.get("gBillSettlementSMS"));
			objBean.setStrBillFormat((String) jObj.get("gBillFormatType"));
			objBean.setChkActivePromotions((String) jObj.get("gActivePromotions"));
			objBean.setChkHomeDelSMS((String) jObj.get("gHomeDelSMSYN"));
			objBean.setChkBillSettlementSMS((String) jObj.get("gBillSettleSMSYN"));
			objBean.setStrSMSType((String) jObj.get("gSMSType"));
			objBean.setChkPrintShortNameOnKOT((String) jObj.get("gPrintShortNameOnKOT"));
			objBean.setChkPrintForVoidBill((String) jObj.get("gPrintOnVoidBill"));
			objBean.setChkPostSalesDataToMMS((String) jObj.get("gPostSalesDataToMMS"));
			objBean.setChkAreaMasterCompulsory((String) jObj.get("gCustAreaCompulsory"));
			objBean.setStrPriceFrom((String) jObj.get("gPriceFrom"));
			objBean.setChkPrinterErrorMessage((String) jObj.get("gShowPrinterErrorMsg"));
			objBean.setChkChangeQtyForExternalCode((String) jObj.get("gChangeQtyForExternalCode"));
			objBean.setChkPointsOnBillPrint((String) jObj.get("gPointsOnBillPrint"));
			objBean.setStrCardIntfType((String) jObj.get("gCardIntfType"));
			objBean.setStrCMSIntegrationYN((String) jObj.get("gCMSIntegrationYN"));
			objBean.setStrCMSWesServiceURL((String) jObj.get("gCMSWebServiceURL"));
			objBean.setChkManualAdvOrderCompulsory((String) jObj.get("gCompulsoryManualAdvOrderNo"));
			objBean.setChkPrintManualAdvOrderOnBill((String) jObj.get("gPrintManualAdvOrderNoOnBill"));
			objBean.setChkPrintModifierQtyOnKOT((String) jObj.get("gPrintModQtyOnKOT"));
			objBean.setIntNoOfLinesInKOTPrint((long) jObj.get("gNoOfLinesInKOTPrint"));
			objBean.setChkMultiKOTPrint((String) jObj.get("gMultipleKOTPrint"));
			objBean.setChkItemQtyNumpad((String) jObj.get("gItemQtyNumpad"));
			objBean.setChkMemberAsTable((String) jObj.get("gTreatMemberAsTable"));
			objBean.setChkPrintKOTToLocalPrinter((String) jObj.get("gPrintKotToLocaPrinter"));
			objBean.setChkEnableSettleBtnForDirectBillerBill((String) jObj.get("gEnableSettleBtnForDirectBiller"));
			objBean.setChkDelBoyCompulsoryOnDirectBiller((String) jObj.get("gDelBoyCompulsoryOnDirectBiller"));
			objBean.setChkMemberCodeForKOTJPOS((String) jObj.get("gCMSMemberCodeForKOTJPOS"));
			objBean.setChkMemberCodeForKOTMPOS((String) jObj.get("gCMSMemberCodeForKOTMPOS"));
			objBean.setChkDontShowAdvOrderInOtherPOS((String) jObj.get("gDontShowAdvOrderInOtherPOS"));
			objBean.setChkPrintZeroAmtModifierInBill((String) jObj.get("gPrintZeroAmtModifierOnBill"));
			objBean.setChkPrintKOTYN((String) jObj.get("gPrintKOTYN"));
			objBean.setChkSlipNoForCreditCardBillYN((String) jObj.get("gCreditCardSlipNo"));
			objBean.setChkExpDateForCreditCardBillYN((String) jObj.get("gCreditCardExpiryDate"));
			objBean.setChkSelectWaiterFromCardSwipe((String) jObj.get("gSelectWaiterFromCardSwipe"));
			objBean.setChkMultipleWaiterSelectionOnMakeKOT((String) jObj.get("gMultiWaiterSelOnMakeKOT"));
			objBean.setChkMoveTableToOtherPOS((String) jObj.get("gMoveTableToOtherPOS"));
			objBean.setChkMoveKOTToOtherPOS((String) jObj.get("gMoveKOTToOtherPOS"));
			objBean.setChkCalculateTaxOnMakeKOT((String) jObj.get("gCalculateTaxOnMakeKOT"));
			objBean.setStrReceiverEmailId((String) jObj.get("gReceiverEmailIds"));
			objBean.setChkCalculateDiscItemWise((String) jObj.get("gItemWiseDiscount"));
			objBean.setChkTakewayCustomerSelection((String) jObj.get("gRemarksOnTakeAway"));
			objBean.setChkShowItemStkColumnInDB((String) jObj.get("gShowItemStkColumnInDB"));
			objBean.setStrItemType((String) jObj.get("gItemType"));
			objBean.setChkBoxAllowNewAreaMasterFromCustMaster((String) jObj.get("gAllowNewAreaMasterFromCustMaster"));
			objBean.setChkSelectCustAddressForBill((String) jObj.get("gCustAddressSelectionForBill"));
			objBean.setChkGenrateMI((String) jObj.get("gGenrateMI"));
			objBean.setStrFTPAddress((String) jObj.get("gFTPAddress"));
			objBean.setStrFTPServerUserName((String) jObj.get("gFTPServerUserName"));
			objBean.setStrFTPServerPass((String) jObj.get("gFTPServerPass"));
			objBean.setChkAllowToCalculateItemWeight((String) jObj.get("gAllowToCalculateItemWeight"));
			objBean.setStrShowBillsDtlType((String) jObj.get("gShowBillsType"));
			objBean.setChkPrintInvoiceOnBill((String) jObj.get("gPrintTaxInvoice"));
			objBean.setChkPrintInclusiveOfAllTaxesOnBill((String) jObj.get("gPrintInclusiveOfAllTaxes"));
			objBean.setStrApplyDiscountOn((String) jObj.get("gApplyDiscountOn"));
			objBean.setChkMemberCodeForKotInMposByCardSwipe((String) jObj.get("gMemberCodeForKotInMposByCardSwipe"));
			objBean.setChkPrintBill((String) jObj.get("gPrintBillYN"));
			objBean.setChkUseVatAndServiceNoFromPos((String) jObj.get("gUseVatAndServiceTaxFromPos"));
			objBean.setChkMemberCodeForMakeBillInMPOS((String) jObj.get("gMemberCodeForMakeBillInMPOS"));
			objBean.setChkItemWiseKOTPrintYN((String) jObj.get("gItemWiseKOTPrintYN"));
			objBean.setStrPOSForDayEnd((String) jObj.get("gLastPOSForDayEnd"));
			objBean.setStrCMSPostingType((String) jObj.get("gCMSPostingType"));
			objBean.setChkPopUpToApplyPromotionsOnBill((String) jObj.get("gPopUpToApplyPromotionsOnBill"));
			objBean.setChkSelectCustomerCodeFromCardSwipe((String) jObj.get("gSelectCustomerCodeFromCardSwipe"));
			objBean.setChkCheckDebitCardBalOnTrans((String) jObj.get("gCheckDebitCardBalanceOnTrans"));
			objBean.setChkSettlementsFromPOSMaster((String) jObj.get("gPickSettlementsFromPOSMaster"));
			objBean.setChkShiftWiseDayEnd((String) jObj.get("gEnableShiftYN"));
			objBean.setChkProductionLinkup((String) jObj.get("gProductionLinkup"));
			objBean.setChkLockDataOnShift((String) jObj.get("gLockDataOnShiftYN"));
			objBean.setStrWSClientCode((String) jObj.get("gWSClientCode"));
			objBean.setChkEnableBillSeries((String) jObj.get("gEnableBillSeries"));
			objBean.setChkEnablePMSIntegration((String) jObj.get("gEnablePMSIntegrationYN"));
			objBean.setChkPrintTimeOnBill((String) jObj.get("gPrintTimeOnBillYN"));
			objBean.setChkPrintTDHItemsInBill((String) jObj.get("gPrintTDHItemsInBill"));
			objBean.setChkPrintRemarkAndReasonForReprint((String) jObj.get("gPrintRemarkAndReasonForReprint"));
			objBean.setIntDaysBeforeOrderToCancel((long) jObj.get("daysBeforeVoidAdvOrder"));
			objBean.setIntNoOfDelDaysForAdvOrder((long) jObj.get("gNoOfDelDaysForAdvOrder"));
			objBean.setIntNoOfDelDaysForUrgentOrder((long) jObj.get("gNoOfDelDaysForUrgentOrder"));
			objBean.setChkSetUpToTimeForAdvOrder((String) jObj.get("gSetUpToTimeForAdvOrder"));
			objBean.setChkSetUpToTimeForUrgentOrder((String) jObj.get("gSetUpToTimeForUrgentOrder"));

			String upToTimeForAdvOrder = ((String) jObj.get("gUpToTimeForAdvOrder")).split(" ")[0];
			objBean.setStrHours(upToTimeForAdvOrder.split(":")[0].trim());
			objBean.setStrMinutes(upToTimeForAdvOrder.split(":")[1].trim());
			objBean.setStrAMPM(((String) jObj.get("gUpToTimeForAdvOrder")).split(" ")[1]);

			String upToTimeForUrgentOrder = ((String) jObj.get("gUpToTimeForUrgentOrder")).split(" ")[0];
			objBean.setStrHoursUrgentOrder(upToTimeForUrgentOrder.split(":")[0].trim());
			objBean.setStrMinutesUrgentOrder(upToTimeForUrgentOrder.split(":")[1].trim());
			objBean.setStrAMPMUrgent(((String) jObj.get("gUpToTimeForUrgentOrder")).split(" ")[1]);

			objBean.setChkEnableBothPrintAndSettleBtnForDB((String) jObj.get("gEnablePrintAndSettleBtnForDB"));
			objBean.setStrInrestoPOSIntegrationYN((String) jObj.get("gInrestoPOSIntegrationYN"));
			objBean.setStrInrestoPOSWesServiceURL((String) jObj.get("gInrestoPOSWebServiceURL"));
			objBean.setStrInrestoPOSId((String) jObj.get("gInrestoPOSId"));
			objBean.setStrInrestoPOSKey((String) jObj.get("gInrestoPOSKey"));
			objBean.setChkCarryForwardFloatAmtToNextDay((String) jObj.get("flgCarryForwardFloatAmtToNextDay"));
			objBean.setChkOpenCashDrawerAfterBillPrint((String) jObj.get("gOpenCashDrawerAfterBillPrintYN"));
			objBean.setChkPropertyWiseSalesOrder((String) jObj.get("gPropertyWiseSalesOrderYN"));
			objBean.setChkShowItemDtlsForChangeCustomerOnBill((String) jObj.get("gShowItemDetailsGrid"));
			objBean.setChkShowPopUpForNextItemQuantity((String) jObj.get("ShowPopUpForNextItemQuantity"));
			objBean.setStrJioPOSIntegrationYN((String) jObj.get("gJioPOSIntegrationYN"));
			objBean.setStrJioPOSWesServiceURL((String) jObj.get("gJioPOSWesServiceURL"));
			objBean.setStrJioMID((String) jObj.get("strJioMID"));
			objBean.setStrJioTID((String) jObj.get("strJioTID"));
			objBean.setStrJioActivationCode((String) jObj.get("strJioActivationCode"));
			objBean.setStrJioDeviceID((String) jObj.get("strJioDeviceID"));
			if (!jObj.get("strJioDeviceID").toString().isEmpty()) {
				JioDeviceIDFound = true;
				JioDeviceIDFromDB = jObj.get("strJioDeviceID").toString();
			}
			objBean.setChkNewBillSeriesForNewDay((String) jObj.get("strNewBillSeriesForNewDay"));
			objBean.setChkShowReportsPOSWise((String) jObj.get("strShowReportsPOSWise"));
			objBean.setChkEnableDineIn((String) jObj.get("strEnableDineIn"));
			objBean.setChkAutoAreaSelectionInMakeKOT((String) jObj.get("strAutoAreaSelectionInMakeKOT"));

			dteEndDate = (String) jObj.get("gEndDate");
		}
		return objBean;
	}

	@RequestMapping(value = "/fetchDeviceID", method = RequestMethod.GET)
	public @ResponseBody JSONObject funFetchDeviceID(HttpServletRequest req) {
		String IP = "localhost", PORT = "5150";
		objPOSGlobal.funStartSocketBat();
		JSONObject jobj = new JSONObject();
		try {
			String host = IP; // IP address of the server
			int port = Integer.parseInt(PORT); // Port on which the socket is
												// going to connect
			String response = "";
			StringBuilder Res = new StringBuilder();
			String SendData = "getDongleId"; // getDongleId
			System.out.println("Request String:" + SendData);
			try (Socket s = new Socket(host, port)) // Creating socket class
			{
				DataOutputStream dout = new DataOutputStream(s.getOutputStream()); // creating
																					// outputstream
																					// to
																					// send
																					// data
																					// to
																					// server
				DataInputStream din = new DataInputStream(s.getInputStream()); // creating
																				// inputstream
																				// to
																				// receive
																				// data
																				// from
																				// server
				// BufferedReader br = new BufferedReader(new
				// InputStreamReader(System.in));
				byte[] str = SendData.getBytes("UTF-8");
				dout.write(str, 0, str.length);
				// System.out.println("Send data value = "+ SendData);
				dout.flush(); // Flush the streams

				byte[] bs = new byte[10024];
				din.read(bs);
				char c;
				for (byte b : bs) {
					c = (char) b;
					response = Res.append("").append(c).toString();
				}
				System.out.println("Device ID: " + response);
				dout.close(); // Closing the output stream
				din.close(); // Closing the input stream
			} // creating outputstream to send data to server
			jobj.put("deviceID", response.trim());
			return jobj;
		} catch (Exception e) {
			System.out.println("Exception:" + e);
			return jobj;
		}
	}

	public void funSaveMapMyDevice(String mid, String tid, String deviceId, String strJioMoneyActivationCode, String posCode) {

		objPOSGlobal.funStartSocketBat();
		try {

			String RequestType = "1008";
			String Amount = "0.00";

			String manufacturer = "JioPayDevice";// JioPayDevice
			String deviceStatus = "A";
			String linkDate = getCurrentDate();
			String deLinkDate = deLinkedDate();
			String superMerchantId = mid;
			String userName = "9820001759";
			String businessLegalName = "Sanguine Software";

			String requestData = "requestType=" + RequestType + "&mid=" + mid + "&deviceId=" + deviceId + "&manufacturer=" + manufacturer + "&deviceStatus=" + deviceStatus + "&linkDate=" + linkDate + "&deLinkDate=" + deLinkDate + "&superMerchantId=" + superMerchantId + "&userName=" + userName + "&businessLegalName=" + businessLegalName + "&tid=" + tid;

			System.out.println("RequestData : " + requestData);
			String Response = "";
			Response = objPOSGlobal.funMakeTransaction(requestData, RequestType, mid, tid, Amount, "PRE_PROD", "localhost", "5150", posCode, strJioMoneyActivationCode);
			// System.out.println("Server Response: " + response);
			System.out.println(Response);

			String strRes = Response.trim();
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(strRes);
			// String responseCode = (String) jsonObject.get("responseCode");
			JSONArray lang = (JSONArray) jsonObject.get("result");
			JSONParser jsonParser1 = new JSONParser();
			JSONObject jsonObject1 = (JSONObject) jsonParser1.parse(lang.get(0).toString());
			// String responseCode = (String) jsonObject.get("responseCode");
			String responseCode = (String) jsonObject1.get("messageCode");

			// String responseCode = lang.get(8).toString();

		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
	}

	public String getCurrentDate() {
		Date currentDate = new Date();
		String strCurrentDate = (currentDate.getDate() + "/" + (currentDate.getMonth() + 1) + "/" + (currentDate.getYear() + 1900));
		return strCurrentDate;
	}

	public String deLinkedDate() {
		String currentDate = getCurrentDate();
		String[] date1 = currentDate.split("/");
		int year = 30 + Integer.parseInt(date1[2]);
		String nextDate = (date1[0] + "/" + date1[1] + "/" + String.valueOf(year));
		return nextDate;
	}
}
