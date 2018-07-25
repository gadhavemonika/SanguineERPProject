package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.IOFileUploadException;
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

import com.sanguine.controller.clsExcelExportImportController;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsStkPostingDtlModel;
import com.sanguine.model.clsSubGroupMasterModel;
import com.sanguine.webpos.bean.clsAddKOTToBillBean;
import com.sanguine.webpos.bean.clsBillItemDtlBean;
import com.sanguine.webpos.bean.clsPOSMenuItemMasterBean;
import com.sanguine.webpos.bean.clsPOSMoveTableBean;
import com.sanguine.webpos.bean.clsPOSPSPDtl;
import com.sanguine.webpos.bean.clsPOSPhysicalStockPostingBean;
import com.sanguine.webpos.bean.clsPOSReasonMasterBean;
import com.sanguine.webpos.bean.clsPOSTableMasterBean;
import com.sanguine.webpos.bean.clsPOSUserAccessBean;

@Controller
public class clsPOSPhysicalStkPostingController {

	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	@Autowired
	private ServletContext servletContext;

	Map<String, String> map = new HashMap<String, String>();
	private Map<String, clsPOSPSPDtl> hmPSPDtl = null;
	final static Logger logger = Logger.getLogger(clsPOSPhysicalStkPostingController.class);
	List<clsPOSReasonMasterBean> listReason = new ArrayList<clsPOSReasonMasterBean>();

	@RequestMapping(value = "/frmPOSPhysicalStkPosting", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String posCode = request.getSession().getAttribute("loginPOS").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		List reasonList = new ArrayList();
		funGetPSPReasonList("All");
		for (int cnt = 0; cnt < listReason.size(); cnt++) {
			clsPOSReasonMasterBean obj = listReason.get(cnt);
			reasonList.add(obj.getStrReasonName());
			map.put(obj.getStrReasonCode(), obj.getStrReasonName());
		}
		model.put("reasonList", reasonList);

		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {

			return new ModelAndView("frmPOSPhysicalStkPosting_1", "command", new clsPOSPhysicalStockPostingBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSPhysicalStkPosting", "command", new clsPOSPhysicalStockPostingBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/loadItemList", method = RequestMethod.GET)
	public @ResponseBody clsPOSPhysicalStockPostingBean funSetSearchFields(@RequestParam("ItemCode") String ItemCode, HttpServletRequest req) {
		clsPOSPhysicalStockPostingBean objBean = null;

		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funGetItemList" + "?searchCode=" + ItemCode;
		System.out.println(posUrl);

		try {
			URL url = new URL(posUrl);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = "", op = "";
			while ((output = br.readLine()) != null) {
				op += output;
			}
			System.out.println("Obj=" + op);
			conn.disconnect();

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(op);
			jObjSearchDetails = (JSONObject) obj;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray jArrSearchList = (JSONArray) jObjSearchDetails.get("POSItemList");
		if (null != jArrSearchList) {
			for (int cnt = 0; cnt < jArrSearchList.size(); cnt++) {
				JSONArray jArrList = (JSONArray) jArrSearchList.get(cnt);
				objBean = new clsPOSPhysicalStockPostingBean();
				objBean.setStrItemCode((String) jArrList.get(0));
				objBean.setStrItemName((String) jArrList.get(1));
				objBean.setStrExternalCode((String) jArrList.get(3));
			}

		}
		return objBean;
	}

	@RequestMapping(value = "/getItemStock", method = RequestMethod.GET)
	public @ResponseBody clsPOSPhysicalStockPostingBean funGetItemStock(@RequestParam("ItemCode") String ItemCode, HttpServletRequest req) {
		String posCode = req.getSession().getAttribute("loginPOS").toString();
		clsPOSPhysicalStockPostingBean objBean = new clsPOSPhysicalStockPostingBean();

		JSONObject jObjItemDetails = new JSONObject();
		JSONArray jArrItemDetails = null;
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funGetItemDetails" + "?ItemCode=" + ItemCode + "&POSCode=" + posCode;
		System.out.println(posUrl);

		jObjItemDetails = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		jArrItemDetails = (JSONArray) jObjItemDetails.get("ItemStock");

		if (null != jArrItemDetails) {
			for (int cnt = 0; cnt < jArrItemDetails.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArrItemDetails.get(cnt);
				long stock = (long) jobj.get("Stock");
				objBean.setDblStock(String.valueOf(stock));
			}

		}

		jArrItemDetails = (JSONArray) jObjItemDetails.get("ItemPurchaseRate");

		if (null != jArrItemDetails) {
			for (int cnt = 0; cnt < jArrItemDetails.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArrItemDetails.get(cnt);
				long purchaseRate = (long) jobj.get("PurchaseRate");
				objBean.setDblPurchaseRate(String.valueOf(purchaseRate));
			}

		}

		jArrItemDetails = (JSONArray) jObjItemDetails.get("ItemSaleRate");

		if (null != jArrItemDetails) {
			for (int cnt = 0; cnt < jArrItemDetails.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArrItemDetails.get(cnt);
				long saleRate = (long) jobj.get("SaleRate");
				objBean.setDblSaleRate(String.valueOf(saleRate));
			}

		}

		return objBean;
	}

	@RequestMapping(value = "/savePhysicalStock", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSPhysicalStockPostingBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";

		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();
			String posCode = req.getSession().getAttribute("loginPOS").toString();

			JSONObject jObj = new JSONObject();
			clsPOSUserAccessBean obj = null;
			List<clsPOSPSPDtl> listOfItem = objBean.getListPSPDtl();

			String reasonCode = "";
			JSONArray jArrItemList = new JSONArray();

			if (listOfItem.size() > 0) {
				for (int i = 1; i < listOfItem.size(); i++) {
					clsPOSPSPDtl objPSPDtl = listOfItem.get(i);
					JSONObject jObjItemDtl = new JSONObject();
					jObjItemDtl.put("ItemCode", objPSPDtl.getStrItemCode());
					jObjItemDtl.put("ItemName", objPSPDtl.getStrItemName());
					jObjItemDtl.put("CompQty", objPSPDtl.getDblCompStk());
					jObjItemDtl.put("PhyQty", objPSPDtl.getDblPhyStk());
					jObjItemDtl.put("Variance", objPSPDtl.getDblVariance());
					jObjItemDtl.put("VarianceAmt", objPSPDtl.getDblVairanceAmt());
					jArrItemList.add(jObjItemDtl);
				}
			}

			if (objBean.getStrReason() != null) {
				if (map.size() > 0) {
					for (String key : map.keySet()) {
						if (map.get(key).equals(objBean.getStrReason())) {
							reasonCode = key;
						}
					}
				}
			}
			if (objBean.getStrPSPCode() != null) {
				jObj.put("PSPCode", objBean.getStrPSPCode());
			} else {
				jObj.put("PSPCode", "");
			}
			jObj.put("ReasonCode", reasonCode);
			jObj.put("Remark", objBean.getStrRemark());
			jObj.put("ItemList", jArrItemList);
			jObj.put("POSCode", posCode);
			jObj.put("ClientCode", clientCode);
			jObj.put("UserCode", webStockUserCode);
			jObj.put("SaleAmount", objBean.getStrSaleAmt());
			jObj.put("StockOutAmount", objBean.getStrStockOutAmt());
			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funSavePhysicalStock";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObj.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSPhysicalStkPosting.html?saddr=" + urlHits);

		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	private clsPOSPhysicalStockPostingBean funGetPSPReasonList(String reasonCode) {
		clsPOSPhysicalStockPostingBean objBean = null;

		JSONObject jObjReasonDetails = new JSONObject();
		JSONArray jArrReasonList = null;
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funGetReasonCode" + "?ReasonCode=" + reasonCode + "&Type=" + "strPSP";
		System.out.println(posUrl);

		jObjReasonDetails = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		jArrReasonList = (JSONArray) jObjReasonDetails.get("ReasonList");

		if (null != jArrReasonList) {
			for (int cnt = 0; cnt < jArrReasonList.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArrReasonList.get(cnt);
				// objBean=new clsPOSPhysicalStockPostingBean();
				// objBean.setStrReason(jobj.get("ReasonName"));
				clsPOSReasonMasterBean objReasonDtl = new clsPOSReasonMasterBean();
				objReasonDtl.setStrReasonCode((String) jobj.get("ReasonCode"));
				objReasonDtl.setStrReasonName((String) jobj.get("ReasonName"));
				listReason.add(objReasonDtl);
				map.put((String) jobj.get("ReasonCode"), (String) jobj.get("ReasonName"));
			}

		}
		return objBean;
	}

	/**
	 * Exporting Physical Stock Posting Data
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/PhysicalStockPostingExcelExport", method = RequestMethod.GET)
	public ModelAndView funPhysicalStockPostingExcelExport(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String webStockUserCode = request.getSession().getAttribute("usercode").toString();
		String posCode = request.getSession().getAttribute("loginPOS").toString();
		List PhyStkPstlist = new ArrayList();
		String FileName = "PhysicalStockItemList";
		String header = "Item Code, SubGroup Name,Item Name,Computer Stock,Physical Stock";
		List ExportList = new ArrayList();
		String[] ExcelHeader = header.split(",");
		ExportList.add(ExcelHeader);
		JSONObject jObjItemDetails = new JSONObject();
		JSONArray jArrItemDetails = null;
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funGetItemForExport";
		System.out.println(posUrl);

		jObjItemDetails = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		jArrItemDetails = (JSONArray) jObjItemDetails.get("PhysicalStockExportItems");

		if (null != jArrItemDetails) {
			for (int cnt = 0; cnt < jArrItemDetails.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArrItemDetails.get(cnt);
				String itemCode = (String) jobj.get("ItemCode");
				String subGroupName = (String) jobj.get("SubGroupName");
				String itemName = (String) jobj.get("ItemName");
				long compStock = (long) jobj.get("CompStock");
				String computerStock = String.valueOf(compStock);
				List DataList = new ArrayList<>();
				DataList.add(itemCode);
				DataList.add(subGroupName);
				DataList.add(itemName);
				DataList.add(compStock);
				DataList.add("");
				PhyStkPstlist.add(DataList);
			}

		}
		ExportList.add(PhyStkPstlist);

		return new ModelAndView("excelView", "stocklist", ExportList);
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/PhysicalStockExcelExportImport", method = RequestMethod.POST)
	public @ResponseBody clsPOSPhysicalStockPostingBean funUploadExcel(@RequestParam("file") MultipartFile excelfile, HttpServletRequest request, HttpServletResponse res) throws IOFileUploadException {
		List list = new ArrayList<>();
		clsPOSPhysicalStockPostingBean objBean = new clsPOSPhysicalStockPostingBean();
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
	public clsPOSPhysicalStockPostingBean funPhyStkPsting(HSSFSheet worksheet, HttpServletRequest request) {
		clsPOSPhysicalStockPostingBean objBean = null;
		List listPhyStklist = new ArrayList<>();
		int RowCount = 0;
		String itemCode = "", itemName = "";
		double phyQty = 0, compQty = 0;
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		try {
			int i = 1;
			while (i <= worksheet.getLastRowNum()) {
				clsPOSPSPDtl objPSPDtl = new clsPOSPSPDtl();
				HSSFRow row = worksheet.getRow(i++);
				// Sets the Read data to the model class
				HSSFCell cell = row.getCell(3);

				if (row.getCell(4) != null) {
					if (!row.getCell(4).toString().isEmpty()) {
						itemCode = row.getCell(0).getStringCellValue();
						itemName = row.getCell(2).getStringCellValue();
						compQty = Double.valueOf(row.getCell(3).getNumericCellValue());
						phyQty = row.getCell(4).getNumericCellValue();
						objPSPDtl.setStrItemCode(itemCode);
						objPSPDtl.setStrItemName(itemName);
						objPSPDtl.setStrPSPCode("");
						objPSPDtl.setDblCompStk(compQty);
						objPSPDtl.setDblPhyStk(phyQty);
						double variance = 0.00;
						variance = (phyQty - compQty);
						objPSPDtl.setDblVariance(variance);
						objPSPDtl.setDblVairanceAmt(0);
						listPhyStklist.add(objPSPDtl);

					}
				}
			}
			if (listPhyStklist.size() > 0) {
				objBean = new clsPOSPhysicalStockPostingBean();
				objBean.setListPSPDtl(listPhyStklist);
			}

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			List list = new ArrayList<>();
			list.add("Invalid Excel File");
			list.add("Invalid Entry In Row No." + RowCount + " and Product Code " + itemCode + " ");
			return objBean;
		}
		return objBean;
	}

	@RequestMapping(value = "/loadPhysicalStockDetails", method = RequestMethod.GET)
	public @ResponseBody clsPOSPhysicalStockPostingBean funLoadPhysicalStockDetails(@RequestParam("PSPCode") String pspCode, HttpServletRequest req) {
		clsPOSPhysicalStockPostingBean objBean = null;

		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funGetPhysicalStkData" + "?searchCode=" + pspCode;
		System.out.println(posUrl);

		try {
			URL url = new URL(posUrl);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = "", op = "";
			while ((output = br.readLine()) != null) {
				op += output;
			}
			System.out.println("Obj=" + op);
			conn.disconnect();

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(op);
			jObjSearchDetails = (JSONObject) obj;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray jArrSearchList = (JSONArray) jObjSearchDetails.get("PhysicalStock");
		if (null != jArrSearchList) {
			String reasonName = "", posCode = "", posName = "";
			objBean = new clsPOSPhysicalStockPostingBean();
			List listPhyStklist = new ArrayList<>();
			for (int cnt = 0; cnt < jArrSearchList.size(); cnt++) {
				JSONArray jArrList = (JSONArray) jArrSearchList.get(cnt);
				clsPOSPSPDtl objPSPDtl = new clsPOSPSPDtl();
				objPSPDtl.setStrItemCode((String) jArrList.get(2));
				objPSPDtl.setStrItemName((String) jArrList.get(1));
				objPSPDtl.setStrPSPCode((String) jArrList.get(0));
				objPSPDtl.setDblCompStk(Double.valueOf(jArrList.get(3).toString()));
				objPSPDtl.setDblPhyStk(Double.valueOf(jArrList.get(4).toString()));
				objPSPDtl.setDblVariance(Double.valueOf(jArrList.get(5).toString()));
				objPSPDtl.setDblVairanceAmt(Double.valueOf(jArrList.get(6).toString()));

				reasonName = (String) jArrList.get(8);
				posCode = (String) jArrList.get(9);
				posName = (String) jArrList.get(10);
				pspCode = (String) jArrList.get(0);
				listPhyStklist.add(objPSPDtl);
			}
			objBean.setListPSPDtl(listPhyStklist);
			objBean.setStrReason(reasonName);
			objBean.setStrPSPCode(pspCode);
			objBean.setStrPOSCode(posCode);
			objBean.setStrPOSName(posName);

		}
		return objBean;
	}

}
