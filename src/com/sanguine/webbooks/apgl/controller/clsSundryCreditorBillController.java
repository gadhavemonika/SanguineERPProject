package com.sanguine.webbooks.apgl.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.bind.JAXBException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.controller.clsUserController;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webbooks.apgl.bean.clsSuppGRNBillBean;
import com.sanguine.webbooks.apgl.model.clsSundaryCrBillDtlModel;
import com.sanguine.webbooks.apgl.model.clsSundaryCrBillGRNDtlModel;
import com.sanguine.webbooks.apgl.model.clsSundaryCrBillModel;
import com.sanguine.webbooks.apgl.service.clsSuppGRNBillService;
import com.sanguine.webbooks.model.clsSundaryCreditorMasterModel;
import com.sanguine.webbooks.service.clsSundryCreditorMasterService;
import com.sanguine.webpos.controller.clsPOSGlobalFunctionsController;

@Controller
public class clsSundryCreditorBillController {

	@Autowired
	private clsSuppGRNBillService objSuppGRNBillService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsSundryCreditorMasterService objSundryCreditorMasterService;

	// Open SuppGRNBill
	@RequestMapping(value = "/frmSundryCreditorBill", method = RequestMethod.GET)
	public ModelAndView funOpenForm() {
		return new ModelAndView("frmSundryCreditorBill", "command", new clsSuppGRNBillBean());
	}

	// Load Master Data On Form
	@RequestMapping(value = "/loadSundryCreditorBillData", method = RequestMethod.GET)
	public @ResponseBody clsSuppGRNBillBean funLoadMasterData(HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String sql = "";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		clsSuppGRNBillBean objBean = new clsSuppGRNBillBean();
		String docCode = request.getParameter("docCode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		clsSundaryCrBillModel objModel = objSuppGRNBillService.funGetSundryCriditorDtl(docCode, clientCode);
		if (objModel == null) {
			objBean = new clsSuppGRNBillBean();
			objBean.setStrVoucherNo("Invalid Voucher No");
		} else {
			objBean.setStrAcCode(objModel.getStrAcCode());
			objBean.setStrAcName(objModel.getStrAcName());
			objBean.setStrVoucherNo(objModel.getStrVoucherNo());
			objBean.setStrBillNo(objModel.getStrBillNo());
			objBean.setStrModuleType("APGL");
			objBean.setStrNarration(objModel.getStrNarration());
			objBean.setStrPropertyCode(objModel.getStrPropertyCode());
			objBean.setStrSuppCode(objModel.getStrSuppCode());
			objBean.setStrSuppName(objModel.getStrSuppName());
			objBean.setDteBillDate(objGlobalFunctions.funGetDate("yyyy-MM-dd", objModel.getDteBillDate()));
			objBean.setDteDueDate(objGlobalFunctions.funGetDate("yyyy-MM-dd", objModel.getDteBillDate()));
			objBean.setDteVoucherDate(objGlobalFunctions.funGetDate("yyyy-MM-dd", objModel.getDteBillDate()));
			objBean.setDblTotalAmount(objModel.getDblTotalAmount());

			List<clsSundaryCrBillGRNDtlModel> listUnBillGRN = funUnBillGRNList(objModel.getStrSuppCode(), clientCode, propCode, objGlobalFunctions.funGetCurrentDateTime("dd-MM-yyyy"), objGlobalFunctions.funGetCurrentDateTime("dd-MM-yyyy"));

			ListIterator<clsSundaryCrBillGRNDtlModel> itScBillGRNDtl = objModel.getListSCBillGRNDtlModel().listIterator();
			while (itScBillGRNDtl.hasNext()) {
				clsSundaryCrBillGRNDtlModel objDtl = itScBillGRNDtl.next();
				objDtl.setStrPropertyCode(propCode);
				objDtl.setStrSelected("Tick");
			}

			for (clsSundaryCrBillGRNDtlModel objunBilledGrn : listUnBillGRN) {
				itScBillGRNDtl.add(objunBilledGrn);
			}

			ListIterator<clsSundaryCrBillDtlModel> itAccScBillDtl = objModel.getListSCBillDtlModel().listIterator();
			while (itAccScBillDtl.hasNext()) {
				clsSundaryCrBillDtlModel objDtl = itAccScBillDtl.next();
				if (objDtl.getStrCrDr().equalsIgnoreCase("Cr")) {
					clsSundaryCreditorMasterModel objSCMaster = objSundryCreditorMasterService.funGetSundryCreditorMaster(objModel.getStrCreditorCode(), clientCode);
					objDtl.setStrCreditorCode(objSCMaster.getStrCreditorCode());
					objDtl.setStrCreditorName(objSCMaster.getStrCreditorFullName());
				} else {
					objDtl.setStrCreditorCode("");
					objDtl.setStrCreditorName("");
				}

				objDtl.setStrPropertyCode(propCode);

			}

			objBean.setListSGBillAccDtl(objModel.getListSCBillDtlModel());
			objBean.setListSGBillGRNDtl(objModel.getListSCBillGRNDtlModel());
		}

		return objBean;
	}

	// Save or Update SuppGRNBill
	@RequestMapping(value = "/saveSuppGRNBill", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsSuppGRNBillBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsSundaryCrBillModel objModel = funPrepareModel(objBean, userCode, clientCode, req);
			objSuppGRNBillService.funAddUpdateSuppGRNBill(objModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Voucher Code : ".concat(objModel.getStrVoucherNo().toString()));

			return new ModelAndView("redirect:/frmSundryCreditorBill.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmSundryCreditorBill?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsSundaryCrBillModel funPrepareModel(clsSuppGRNBillBean objBean, String userCode, String clientCode, HttpServletRequest req) {

		String propCode = req.getSession().getAttribute("propertyCode").toString();
		long lastNo = 0;
		clsSundaryCrBillModel objModel = null;
		try {

			if (objBean.getStrVoucherNo() == null || objBean.getStrVoucherNo().equals("")) {
				objModel = new clsSundaryCrBillModel();
				String documentNo = objGlobalFunctions.funGenerateDocumentCode("frmSC", objBean.getDteVoucherDate(), req);
				lastNo = objGlobalFunctions.funGenerateDocumentCodeLastNo("frmSC", objBean.getDteVoucherDate(), req);
				objModel.setStrVoucherNo(documentNo);
				objModel.setIntVouchNum(lastNo);
				objModel.setDteDateCreated(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setStrUserCreated(userCode);
				objModel.setStrClientCode(clientCode);

			} else {
				objModel = objSuppGRNBillService.funGetSuppGRNBill(objBean.getStrVoucherNo(), clientCode);
				if (null == objModel) {
					String documentNo = objGlobalFunctions.funGenerateDocumentCode("frmSC", objBean.getDteVoucherDate(), req);
					objModel.setStrVoucherNo(documentNo);
					objModel.setDteDateCreated(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
					objModel.setStrUserCreated(userCode);
					objModel.setStrClientCode(clientCode);

				}

			}
			DateFormat inputDF = new SimpleDateFormat("yyyy-MM-dd");
			Date vocherDate = inputDF.parse(objBean.getDteVoucherDate());
			Calendar cal = Calendar.getInstance();
			cal.setTime(vocherDate);
			int voucherMonth = cal.get(Calendar.MONTH);

			objModel.setDteDateEdited(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
			objModel.setStrUserEdited(userCode);
			// objModel.setStrAcCode(objBean.getstr);
			// objModel.setStrAcName(strAcName);
			objModel.setStrBillNo(objBean.getStrBillNo());
			objModel.setStrModuleType("APGL");
			objModel.setStrNarration(objBean.getStrNarration());
			objModel.setStrPropertyCode(propCode);
			objModel.setStrSuppCode(objBean.getStrSuppCode());
			objModel.setStrSuppName(objBean.getStrSuppName());
			objModel.setDteBillDate(objGlobalFunctions.funGetDate("yyyy-MM-dd", objBean.getDteBillDate()));
			objModel.setDteDueDate(objGlobalFunctions.funGetDate("yyyy-MM-dd", objBean.getDteDueDate()));
			objModel.setDteVoucherDate(objGlobalFunctions.funGetDate("yyyy-MM-dd", objBean.getDteVoucherDate()));
			objModel.setStrPropertyCode(propCode);
			objModel.setIntVoucherMonth(voucherMonth);
			objModel.setStrAcCode(objBean.getStrAcCode());
			objModel.setStrAcName(objBean.getStrAcName());
			objModel.setDblTotalAmount(objBean.getDblTotalAmount());
			if (objBean.getListSGBillAccDtl().size() > 0) {
				ListIterator<clsSundaryCrBillDtlModel> itScBillDtl = objBean.getListSGBillAccDtl().listIterator();
				while (itScBillDtl.hasNext()) {
					clsSundaryCrBillDtlModel objDtl = itScBillDtl.next();
					if (objDtl.getStrCreditorCode().length() > 0) {
						objModel.setStrCreditorCode(objDtl.getStrCreditorCode());
						objModel.setStrCreditorName(objDtl.getStrCreditorName());

					}
					objDtl.setStrPropertyCode(propCode);

				}
				objModel.setListSCBillDtlModel(objBean.getListSGBillAccDtl());
			}

			if (objBean.getListSGBillGRNDtl().size() > 0) {
				ListIterator<clsSundaryCrBillGRNDtlModel> itScBillGRNDtl = objBean.getListSGBillGRNDtl().listIterator();
				while (itScBillGRNDtl.hasNext()) {
					clsSundaryCrBillGRNDtlModel objDtl = itScBillGRNDtl.next();
					objDtl.setStrPropertyCode(propCode);
				}
				List<clsSundaryCrBillGRNDtlModel> listGRNDtl = new ArrayList<clsSundaryCrBillGRNDtlModel>();
				for (clsSundaryCrBillGRNDtlModel objGRNDtl : objBean.getListSGBillGRNDtl()) {
					if (objGRNDtl.getStrSelected() != null && objGRNDtl.getStrSelected().equalsIgnoreCase("Tick")) {
						listGRNDtl.add(objGRNDtl);
					}
				}
				objModel.setListSCBillGRNDtlModel(listGRNDtl);

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return objModel;

	}

	@RequestMapping(value = "/loadUnBillGrn", method = RequestMethod.GET)
	public @ResponseBody List<clsSundaryCrBillGRNDtlModel> funloadUnBillGrn(HttpServletRequest request) {

		String sql = "";
		List<clsSundaryCrBillGRNDtlModel> listUnBillGRN = new ArrayList<clsSundaryCrBillGRNDtlModel>();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		String strSuppCode = request.getParameter("strSuppCode").toString();
		String fromDate = request.getParameter("fromDate").toString();
		String toDate = request.getParameter("toDate").toString();

		listUnBillGRN = funUnBillGRNList(strSuppCode, clientCode, propCode, fromDate, toDate);

		return listUnBillGRN;

	}

	private List<clsSundaryCrBillGRNDtlModel> funUnBillGRNList(String strSuppCode, String clientCode, String propCode, String fromDate, String toDate) {

		List<clsSundaryCrBillGRNDtlModel> listUnBillGRN = new ArrayList<clsSundaryCrBillGRNDtlModel>();
		JSONObject jObjData = new JSONObject();
		jObjData.put("clientCode", clientCode);
		jObjData.put("strSuppCode", strSuppCode);
		jObjData.put("fromDate", objGlobalFunctions.funGetDate("yyyy-MM-dd", fromDate));
		jObjData.put("toDate", objGlobalFunctions.funGetDate("yyyy-MM-dd", toDate));
		JSONObject jObjRet = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/MMSIntegrationAuto/funLoadUnBilledGRNDateWise", jObjData);
		if (jObjRet.size() > 0) {
			JSONArray jsonArr = (JSONArray) jObjRet.get("unBilledGRN");

			for (int i = 0; i < jsonArr.size(); i++) {
				JSONObject jObj = (JSONObject) jsonArr.get(i);
				clsSundaryCrBillGRNDtlModel objSundaryGRN = new clsSundaryCrBillGRNDtlModel();
				objSundaryGRN.setStrGRNCode(jObj.get("strGRNCode").toString());
				objSundaryGRN.setStrGRNBIllNo(jObj.get("strBillNo").toString());
				objSundaryGRN.setDteBillDate(jObj.get("dtBillDate").toString());
				objSundaryGRN.setDblGRNAmt(Double.parseDouble(jObj.get("dblTotal").toString()));
				objSundaryGRN.setDteGRNDate(jObj.get("dtGRNDate").toString());
				objSundaryGRN.setDteGRNDueDate(jObj.get("dtDueDate").toString());
				objSundaryGRN.setStrPropertyCode(propCode);
				listUnBillGRN.add(objSundaryGRN);
			}
		}

		return listUnBillGRN;
	}

	// Load Master Data On Form
	@RequestMapping(value = "/loadLinkedSuppCode", method = RequestMethod.POST)
	public @ResponseBody clsSundaryCrBillModel funloadLinkedSuppCode(HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String sql = "";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		clsSundaryCrBillModel objBean = new clsSundaryCrBillModel();
		String docCode = request.getParameter("docCode").toString();

		JSONObject jObjData = new JSONObject();
		jObjData.put("clientCode", clientCode);
		jObjData.put("suppCode", docCode);
		JSONObject jObjRet = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/MMSIntegrationAuto/funLoadWSLinkedSupp", jObjData);
		if (!jObjRet.isEmpty()) {
			objBean.setStrSuppCode(jObjRet.get("strSuppCode").toString());
			objBean.setStrSuppName(jObjRet.get("strSuppName").toString());

			clsSundaryCreditorMasterModel objModel = objSundryCreditorMasterService.funGetSundryCreditorMaster(jObjRet.get("strCreditorCodeOrAccountCode").toString(), clientCode);

			objBean.setStrCreditorCode(jObjRet.get("strCreditorCodeOrAccountCode").toString());
			objBean.setStrCreditorName(jObjRet.get("strCreditorNameOrAccountName").toString());
			objBean.setStrClientCode(clientCode);
		} else {
			objBean.setStrSuppCode("Invalid Supp Code");
		}

		return objBean;
	}

	@RequestMapping(value = "/loadGrnWiseAccountAmt", method = RequestMethod.GET)
	public @ResponseBody List<clsSundaryCrBillDtlModel> funLoadGrnWiseAccountAmt(HttpServletRequest request) {

		String sql = "";
		List<clsSundaryCrBillDtlModel> listUnBillGRN = new ArrayList<clsSundaryCrBillDtlModel>();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		String strGRNCode = request.getParameter("strGRNCode").toString();
		JSONObject jObjData = new JSONObject();
		jObjData.put("clientCode", clientCode);
		jObjData.put("grnCodes", strGRNCode);
		JSONObject jObjRet = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/MMSIntegrationAuto/funLoadGrnWiseAccountAmt", jObjData);
		if (jObjRet.size() > 0) {
			JSONArray jsonArr = (JSONArray) jObjRet.get("GrnWiseAccountAmt");

			for (int i = 0; i < jsonArr.size(); i++) {
				JSONObject jObj = (JSONObject) jsonArr.get(i);
				if (jObj.get("CrDr").toString().equals("Dr")) {
					clsSundaryCrBillDtlModel objSundaryAcc = new clsSundaryCrBillDtlModel();
					objSundaryAcc.setStrACCode(jObj.get("strAccountCode").toString());
					objSundaryAcc.setStrACName(jObj.get("strAccountName").toString());
					objSundaryAcc.setStrCrDr("Dr");
					objSundaryAcc.setDblDrAmt(Double.parseDouble(jObj.get("dblTotalPrice").toString()));
					objSundaryAcc.setDblCrAmt(0.0);
					objSundaryAcc.setStrNarration("AutoGenrate Amount of " + jObj.get("strSGName").toString());
					objSundaryAcc.setStrPropertyCode(propCode);
					objSundaryAcc.setStrCreditorCode("");
					objSundaryAcc.setStrCreditorName("");
					listUnBillGRN.add(objSundaryAcc);
				} else {
					clsSundaryCrBillDtlModel objSundaryAcc = new clsSundaryCrBillDtlModel();
					objSundaryAcc.setStrACCode(jObj.get("strAccountCode").toString());
					objSundaryAcc.setStrACName(jObj.get("strAccountName").toString());
					objSundaryAcc.setStrCrDr("Cr");
					objSundaryAcc.setDblDrAmt(0.0);
					objSundaryAcc.setDblCrAmt(Double.parseDouble(jObj.get("dblTotalPrice").toString()));
					objSundaryAcc.setStrNarration("AutoGenrate Amount of " + jObj.get("strSGName").toString());
					objSundaryAcc.setStrPropertyCode(propCode);
					listUnBillGRN.add(objSundaryAcc);
				}
			}
		}

		return listUnBillGRN;
	}

}
