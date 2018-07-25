package com.sanguine.excise.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.excise.bean.clsExciseDeleteTransactionBean;
import com.sanguine.excise.model.clsBrandMasterModel;
import com.sanguine.excise.model.clsOpeningStockModel;
import com.sanguine.excise.model.clsSizeMasterModel;
import com.sanguine.excise.model.clsTransportPassModel;
import com.sanguine.excise.model.clsTransportPassDtlModel;
import com.sanguine.excise.service.clsOpeningStockService;
import com.sanguine.excise.service.clsExciseManualSaleService;
import com.sanguine.excise.service.clsTransportPassService;
import com.sanguine.service.clsDelTransService;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsExciseDeleteTransController {
	@Autowired
	private clsDelTransService objDelTransService;

	@Autowired
	private clsExciseManualSaleService objSaleService;

	@Autowired
	private clsTransportPassService objTPService;

	@Autowired
	private clsOpeningStockService objExBrandOpMasterService;

	clsGlobalFunctions objGlobal;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunService;

	@Autowired
	private clsFLR3AController objclsFLR3AController;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/frmExciseDeleteTransaction", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest req) {

		Map<String, String> mapTransForms = new HashMap<String, String>();
		String sql = " select strFormName,strFormDesc from clsTreeMasterModel " + " where strType='T' and strModule='2' " + " order by strFormName";
		List list = objGlobalFunService.funGetList(sql, "hql");
		for (int cnt = 0; cnt < list.size(); cnt++) {
			Object[] arrObj = (Object[]) list.get(cnt);
			mapTransForms.put(arrObj[0].toString(), arrObj[1].toString());
		}

		String urlHits = "1";

		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		ArrayList<String> listLicence = new ArrayList<String>();
		String Licence_sql = "select a.strLicenceNo,CONCAT(a.strAddress1,',',a.strAddress2," + "',',a.strAddress3,',',b.strCityName,CONCAT('-',a.strPINCode)) as address,a.strLicenceCode " + "from tbllicencemaster a ,tblcitymaster b " + "where a.strCity=b.strCityCode and a.strClientCode='" + clientCode + "' and a.strPropertyCode='" + propertyCode + "' ";
		List licenceMaster = objGlobalFunctionsService.funGetDataList(Licence_sql, "sql");
		if (licenceMaster.size() > 0) {
			for (int i = 0; i < licenceMaster.size(); i++) {

				Object licenceData[] = (Object[]) licenceMaster.get(i);
				listLicence.add(licenceData[2].toString());

			}

		} else {
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Please Add Licence To This Client");
		}

		model.put("listLicence", listLicence);
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		model.put("listFormName", mapTransForms);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseDeleteTransaction_1", "command", new clsExciseDeleteTransactionBean());
		} else {
			return new ModelAndView("frmExciseDeleteTransaction", "command", new clsExciseDeleteTransactionBean());
		}

	}

	@RequestMapping(value = "/exciseDeleteTransaction", method = RequestMethod.POST)
	public ModelAndView funDeleteTransaction(@ModelAttribute("command") clsExciseDeleteTransactionBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (!result.hasErrors()) {

			objGlobal = new clsGlobalFunctions();
			boolean flgTrans = false;
			String formName = objBean.getStrFormName().toString();

			switch (formName) {
			case "frmExciseManualSale":
				flgTrans = funDeleteSale(req, objBean);
				break;

			case "frmExciseTransportPass":
				flgTrans = funDeleteTP(req, objBean);
				break;

			case "frmExciseOpeningStock":
				flgTrans = funDeleteBrandOpening(req, objBean);
				break;

			}

			if (flgTrans) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", " Transaction Deletion Successful.\\n Please Update Bill Generation.");
				return new ModelAndView("redirect:/frmExciseDeleteTransaction.html?saddr=" + urlHits);

			} else {

				if ("2".equalsIgnoreCase(urlHits)) {
					req.getSession().setAttribute("success", true);
					req.getSession().setAttribute("successMessage", " Transaction Deletion UnSuccessful");
					return new ModelAndView("redirect:/frmExciseDeleteTransaction.html?saddr=" + urlHits);
				} else {
					req.getSession().setAttribute("success", true);
					req.getSession().setAttribute("successMessage", " Transaction Deletion UnSuccessful");
					return new ModelAndView("redirect:/frmExciseDeleteTransaction.html?saddr=" + urlHits);
				}

			}

		} else {

			if ("2".equalsIgnoreCase(urlHits)) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", " Transaction Deletion UnSuccessful");
				return new ModelAndView("redirect:/frmExciseDeleteTransaction.html?saddr=" + urlHits);
			} else {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", " Transaction Deletion UnSuccessful");
				return new ModelAndView("redirect:/frmExciseDeleteTransaction.html?saddr=" + urlHits);
			}
		}
	}

	public Boolean funDeleteSale(HttpServletRequest req, clsExciseDeleteTransactionBean objBean) {

		boolean success = false;
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		Integer intId = Integer.parseInt(objBean.getStrTransCode());
		try {
			objSaleService.funDeleteHd(intId, clientCode);
			objSaleService.funDeleteDtl(intId, clientCode);
			success = objSaleService.funDeleteSaleData(intId, clientCode);
		} catch (Exception e) {
			success = false;
		}
		return success;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Boolean funDeleteTP(HttpServletRequest req, clsExciseDeleteTransactionBean objBean) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		Boolean success = false;
		Boolean isDelete = false;

		String isBrandGlobal = "Custom";
		String tempBrandClientCode = clientCode;
		try {
			isBrandGlobal = req.getSession().getAttribute("strBrandMaster").toString();
		} catch (Exception e) {
			isBrandGlobal = "Custom";
		}
		if (isBrandGlobal.equalsIgnoreCase("All")) {
			tempBrandClientCode = "All";
		}

		// Here Check the Stock adjustable for Delete the TP

		String tpCode = objBean.getStrTransCode();
		List objList = objTPService.funGetObject(tpCode, clientCode);
		clsTransportPassModel objTpModel = null;
		if (objList.size() > 0) {
			Object obj[] = (Object[]) objList.get(0);
			objTpModel = (clsTransportPassModel) obj[0];
		}
		List objDtlList = objTPService.funGetTPDtlList(tpCode, clientCode);
		for (int i = 0; i < objDtlList.size(); i++) {
			List brandDataList = new LinkedList();
			Object[] ob = (Object[]) objDtlList.get(i);
			clsTransportPassDtlModel tpDtl = (clsTransportPassDtlModel) ob[0];
			clsBrandMasterModel brandMaster = (clsBrandMasterModel) ob[1];
			clsSizeMasterModel sizeMaster = (clsSizeMasterModel) ob[2];

			String sql_OpData = "select c.intOpBtls,c.intOpPeg,c.intOpML from tblbrandmaster a,tblopeningstock c " + " where a.strBrandCode='" + brandMaster.getStrBrandCode() + "' and a.strBrandCode=c.strBrandCode  " + " and  a.strClientCode='" + tempBrandClientCode + "' and  c.strClientCode='" + clientCode + "' ";
			List ObjOPDataList = objGlobalFunService.funGetDataList(sql_OpData, "sql");
			Integer intOpBtls = 0;
			Integer intOpPeg = 0;
			Integer intOpML = 0;
			if (ObjOPDataList.size() > 0) {
				Object opData[] = (Object[]) ObjOPDataList.get(0);
				intOpBtls = Integer.parseInt(opData[0].toString());
				intOpPeg = Integer.parseInt(opData[1].toString());
				intOpML = Integer.parseInt(opData[2].toString());
			}
			brandDataList.add(brandMaster.getStrBrandCode());
			brandDataList.add(sizeMaster.getStrSizeCode());
			brandDataList.add(brandMaster.getStrShortName());
			brandDataList.add(sizeMaster.getIntQty());
			brandDataList.add(brandMaster.getIntPegSize());
			brandDataList.add(intOpBtls);
			brandDataList.add(intOpPeg);
			brandDataList.add(intOpML);

			String fromDate = req.getSession().getAttribute("startDate").toString();
			fromDate = fromDate.split("/")[2] + "-" + fromDate.split("/")[1] + "-" + fromDate.split("/")[0];
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			String toDate = format1.format(cal.getTime());

			LinkedList currentStkData = objclsFLR3AController.funStockList(brandDataList, req, fromDate, toDate);
			String currentStk = "0.0";
			if (currentStkData != null) {
				currentStk = currentStkData.get(6).toString().trim();
			}

			String[] strCurentArr = String.valueOf(currentStk).split("\\.");
			Long stBls = Long.parseLong(strCurentArr[0].toString().trim());
			Long stkPeg = new Long(0);
			Long stkML = Long.parseLong(strCurentArr[1].toString().trim());
			Long currentAvailableStk = funStockInML(stBls, stkPeg, stkML, brandDataList);

			Integer tpBls = new Integer(0);
			Long tpPeg = new Long(0);
			Long tpML = new Long(0);

			if (tpBls != null) {
				tpBls = tpDtl.getIntBottals();
			}
			Long tpQty = funStockInML(new Long(tpBls.toString()), tpPeg, tpML, brandDataList);

			if (currentAvailableStk >= tpQty) {

				String toDate1 = objTpModel.getStrTpDate();
				LinkedList onTpDateStkData = objclsFLR3AController.funStockList(brandDataList, req, fromDate, toDate1);
				String onTpDateStk = "0.0";
				if (onTpDateStkData != null) {
					onTpDateStk = onTpDateStkData.get(6).toString().trim();
				}

				String[] onTpDateStkArr = String.valueOf(onTpDateStk).split("\\.");
				Long onTPDateBls = Long.parseLong(onTpDateStkArr[0].toString().trim());
				Long onTPDatePeg = new Long(0);
				Long onTPDateML = Long.parseLong(onTpDateStkArr[1].toString().trim());
				Long availableStkOnTpDate = funStockInML(onTPDateBls, onTPDatePeg, onTPDateML, brandDataList);

				if (availableStkOnTpDate >= tpQty) {
					isDelete = true;
				} else {
					return false;
				}

			} else {
				return false;
			}

		}

		// Delete Transaction Will Start Here

		if (isDelete) {
			objTPService.funDeleteHd(tpCode, clientCode);
			success = objTPService.funDeleteDtl(tpCode, clientCode);
		}

		return success;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Boolean funDeleteBrandOpening(HttpServletRequest req, clsExciseDeleteTransactionBean objBean) {

		Boolean success = false;
		Boolean isDelete = false;
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		Long intId = Long.parseLong(objBean.getStrTransCode());
		try {
			clsOpeningStockModel objclsExOpeningModel = objExBrandOpMasterService.funGetExBrandOpMaster(intId, clientCode);

			if (objclsExOpeningModel != null) {
				List brandDataList = new LinkedList();

				String isBrandGlobal = "Custom";
				String isSizeGlobal = "Custom";
				String tempSizeClientCode = clientCode;
				try {
					isSizeGlobal = req.getSession().getAttribute("strSizeMaster").toString();
				} catch (Exception e) {
					isSizeGlobal = "Custom";
				}
				if (isSizeGlobal.equalsIgnoreCase("All")) {
					tempSizeClientCode = "All";
				}

				String tempBrandClientCode = clientCode;
				try {
					isBrandGlobal = req.getSession().getAttribute("strBrandMaster").toString();
				} catch (Exception e) {
					isBrandGlobal = "Custom";
				}
				if (isBrandGlobal.equalsIgnoreCase("All")) {
					tempBrandClientCode = "All";
				}

				String sql_BrandList = "select a.strBrandCode,a.strSizeCode,a.strShortName,b.intQty,a.intPegSize, " + " a.strBrandName from tblbrandmaster a, tblsizemaster b where a.strBrandCode='" + objclsExOpeningModel.getStrBrandCode() + "' and a.strSizeCode=b.strSizeCode and " + " a.strClientCode='" + tempBrandClientCode + "' and b.strClientCode='" + tempSizeClientCode
						+ "' ORDER BY b.intQty DESC";
				Object brandData[] = (Object[]) objGlobalFunService.funGetDataList(sql_BrandList, "sql").get(0);

				Long intOpBtls = objclsExOpeningModel.getIntOpBtls();
				Long intOpPeg = objclsExOpeningModel.getIntOpPeg();
				Long intOpML = objclsExOpeningModel.getIntOpML();

				brandDataList.add(brandData[0]);
				brandDataList.add(brandData[1]);
				brandDataList.add(brandData[2]);
				brandDataList.add(brandData[3]);
				brandDataList.add(brandData[4]);
				brandDataList.add(intOpBtls);
				brandDataList.add(intOpPeg);
				brandDataList.add(intOpML);

				String fromDate = req.getSession().getAttribute("startDate").toString();
				fromDate = fromDate.split("/")[2] + "-" + fromDate.split("/")[1] + "-" + fromDate.split("/")[0];
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				String toDate = format1.format(cal.getTime());

				LinkedList currentStkData = objclsFLR3AController.funStockList(brandDataList, req, fromDate, toDate);
				String currentStk = "0.0";
				if (currentStkData != null) {
					currentStk = currentStkData.get(6).toString().trim();
				}

				String[] strCurentArr = String.valueOf(currentStk).split("\\.");

				Long stBls = Long.parseLong(strCurentArr[0].toString().trim());
				Long stkPeg = new Long(0);
				Long stkML = Long.parseLong(strCurentArr[1].toString().trim());

				Long currentAvailableStk = funStockInML(stBls, stkPeg, stkML, brandDataList);

				Long openingQty = funStockInML(intOpBtls, intOpPeg, intOpML, brandDataList);

				if (currentAvailableStk >= openingQty) {
					isDelete = true;
				}

				// Delete Transaction Will Start Here

				if (isDelete) {
					success = objExBrandOpMasterService.funDeleteDtl(intId, clientCode);
				}

				return success;

			}// If Null is closed
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	@SuppressWarnings("rawtypes")
	public Long funStockInML(Long bottals, Long intpegs, Long intML, List brandDataList) {

		Integer brandSize = Integer.parseInt(brandDataList.get(3).toString());
		Integer pegSize = Integer.parseInt(brandDataList.get(4).toString());

		Long quantity = (long) 0;
		if (pegSize <= 0) {
			pegSize = brandSize;
		}
		Long btsMl = brandSize * bottals;
		Long pegMl = pegSize * intpegs;
		quantity = btsMl + pegMl + intML;

		return quantity;
	}

}
