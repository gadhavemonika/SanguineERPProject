package com.sanguine.webpos.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.IOFileUploadException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
import com.sanguine.webpos.bean.clsPOSCustomerSMSSendDtl;
import com.sanguine.webpos.bean.clsPOSSendBulkSMSBean;

@Controller
public class clsPOSSendBulkSMSController {
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	Map map = new HashMap();

	final static Logger logger = Logger.getLogger(clsPOSSendBulkSMSController.class);

	@RequestMapping(value = "/frmPOSSendBulkSMS", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsPOSSendBulkSMSBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		String clientCode = request.getSession().getAttribute("clientCode").toString();

		JSONArray jArryList = new JSONArray();

		Map mapCustomerType = new HashMap<>();

		mapCustomerType.put("All", "All");
		jArryList = objPOSGlobal.funFillCustTypeCombo(clientCode);

		if (null != jArryList) {
			for (int i = 0; i < jArryList.size(); i++) {
				JSONObject josnObjRet = (JSONObject) jArryList.get(i);
				mapCustomerType.put(josnObjRet.get("strCustomeTypeCode"), josnObjRet.get("strCustomeTypeName"));
			}
		}
		model.put("mapCustomerType", mapCustomerType);

		Map mapCusomerArea = new HashMap<>();
		mapCusomerArea.put("All", "All");
		JSONArray jArrayList = new JSONArray();

		jArrayList = objPOSGlobal.funGetAllCustomerAreaForMaster(clientCode);

		if (null != jArrayList) {
			for (int i = 0; i < jArrayList.size(); i++) {
				JSONObject josnObjRet = (JSONObject) jArrayList.get(i);
				mapCusomerArea.put(josnObjRet.get("strBuildingCode"), josnObjRet.get("strBuildingName"));
			}
		}
		model.put("mapCusomerArea", mapCusomerArea);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSSendBulkSMS_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSSendBulkSMS");
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/funFillCustomerTable", method = RequestMethod.POST)
	public @ResponseBody JSONObject funFillCustomerTable(HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String custTypeCode = req.getParameter("custTypeCode");
		String areaCode = req.getParameter("areaCode");
		String txtSms = req.getParameter("txtSms");
		String dobCheck = req.getParameter("isDOBSelected");

		JSONObject jObj;
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funFillCustomerTable" + "?custTypeCode=" + custTypeCode + "&areaCode=" + areaCode + "&dobCheck=" + dobCheck + "&txtSms=" + txtSms;

		jObj = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		JSONArray jArrKOTList = (JSONArray) jObj.get("customerTblData");

		JSONObject jObjCustomerTableData = new JSONObject();

		jObjCustomerTableData.put("customerTblData", jArrKOTList);

		return jObjCustomerTableData;
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/SendBulkSmsImport", method = RequestMethod.POST)
	public @ResponseBody clsPOSSendBulkSMSBean funUploadExcel(@RequestParam("file") MultipartFile excelfile, HttpServletRequest request, HttpServletResponse res) throws IOFileUploadException {
		List list = new ArrayList<>();
		clsPOSSendBulkSMSBean objBean = new clsPOSSendBulkSMSBean();

		try {

			// Creates a workbook object from the uploaded excelfile
			HSSFWorkbook workbook = new HSSFWorkbook(excelfile.getInputStream());
			// Creates a worksheet object representing the first sheet
			HSSFSheet worksheet = workbook.getSheetAt(0);
			// Reads the data in excel file until last row is encountered
			// list=funPhyStkPsting(worksheet,request);
			objBean = funPhyStkPsting(worksheet, request);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return objBean;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public clsPOSSendBulkSMSBean funPhyStkPsting(HSSFSheet worksheet, HttpServletRequest request) {
		clsPOSSendBulkSMSBean objBean = null;
		List listCusromerSmsSendlist = new ArrayList<>();
		int RowCount = 0;
		String customerName = "";
		long mobileNumber = 0;

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		try {
			int i = 7;
			while (i <= worksheet.getLastRowNum()) {
				clsPOSCustomerSMSSendDtl objSendSms = new clsPOSCustomerSMSSendDtl();
				HSSFRow row = worksheet.getRow(i++);
				// Sets the Read data to the model class
				HSSFCell cell = row.getCell(3);

				String space = " ";
				String total = "Total";
				if ((row.getCell(0) == null) || (row.getCell(0).equals(space))) {
					break;
				} else {
					if (row.getCell(0) != null) {
						if (row.getCell(0).toString().equalsIgnoreCase(" ")) {
							break;
						}
						// if(row.getCell(0).toString().isEmpty())
						// {
						// break;
						// }
						else {
							// if(!row.getCell(0).toString().isEmpty())
							// {
							mobileNumber = (long) row.getCell(0).getNumericCellValue();

							customerName = row.getCell(1).getStringCellValue();

							objSendSms.setStrMobileNumber(mobileNumber);
							objSendSms.setStrCustomerName(customerName);

							listCusromerSmsSendlist.add(objSendSms);

						}
					}
				}
			}

			if (listCusromerSmsSendlist.size() > 0) {
				objBean = new clsPOSSendBulkSMSBean();
				objBean.setListSmsDtl(listCusromerSmsSendlist);
			}

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			List list = new ArrayList<>();
			list.add("Invalid Excel File");
			list.add("Invalid Entry In Row No." + RowCount + " and Product Code " + mobileNumber + " ");
			return objBean;
		}
		return objBean;
	}

	@RequestMapping(value = "/funSendBulkSMS", method = RequestMethod.POST)
	public @ResponseBody JSONObject funSendBulkSMS(HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String txtTestMobileNo = req.getParameter("txtTestMobileNo");
		String txtSms = req.getParameter("txtSms");
		String posCode = req.getSession().getAttribute("loginPOS").toString();

		JSONObject jObj;
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funSendBulkSMS" + "?txtTestMobileNo=" + txtTestMobileNo + "&clientCode=" + clientCode + "&posCode=" + posCode + "&txtSms=" + txtSms;

		jObj = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		String result = (String) jObj.get("returnResult");
		JSONObject jObjresult = new JSONObject();
		jObjresult.put("returnResult", result);

		return jObjresult;
	}

	@RequestMapping(value = "/funSendSMS", method = RequestMethod.POST)
	public @ResponseBody JSONObject funSendSMS(@RequestParam("arrKOTItemDtlList") List<String> arrKOTItemDtlList, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		// String txtTestMobileNo=req.getParameter("txtTestMobileNo");
		// String txtSms = req.getParameter("txtSms");
		String posCode = req.getSession().getAttribute("loginPOS").toString();
		JSONArray jArr = new JSONArray();
		for (int i = 0; i < arrKOTItemDtlList.size(); i++) {
			jArr.add(arrKOTItemDtlList.get(i));
		}
		JSONObject obj = new JSONObject();
		obj.put("jArr", jArr);
		obj.put("clientCode", clientCode);
		obj.put("posCode", posCode);

		JSONObject jObj;

		jObj = objGlobal.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funSendSMS", obj);

		String result = (String) jObj.get("returnResult");
		JSONObject jObjresult = new JSONObject();
		jObjresult.put("returnResult", result);

		return jObjresult;
	}

}