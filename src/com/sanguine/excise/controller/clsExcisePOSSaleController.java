package com.sanguine.excise.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import com.sanguine.excise.bean.clsExciseSaleBean;
import com.sanguine.excise.model.clsExcisePOSSaleModel;
import com.sanguine.excise.model.clsExciseSaleModel;
import com.sanguine.excise.service.clsExcisePOSLinkUpService;
import com.sanguine.excise.service.clsExcisePOSSaleService;
import com.sanguine.excise.service.clsExciseSaleService;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsExcisePOSSaleController {

	@Autowired
	private clsExcisePOSSaleService objclsExcisePOSSalesDtlService;

	@Autowired
	private clsExciseSaleService objclsExciseSaleService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsExcisePOSLinkUpService objExcisePOSLinkUpService;

	@Autowired
	private clsExciseSaleService objExciseStkAdjService;

	@Autowired
	private clsFLR3AController objclsFLR3AController;

	private clsGlobalFunctions objGlobal = null;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/frmExcisePOSSale", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propertyCode = request.getSession().getAttribute("propertyCode").toString();

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		ArrayList<String> listLicence = new ArrayList<String>();
		String sql_Licence = "select strLicenceCode,strLicenceNo from tbllicencemaster where " + "strClientCode='" + clientCode + "' and strPropertyCode='" + propertyCode + "'";

		List LicenceList = objGlobalFunctionsService.funGetDataList(sql_Licence, "sql");
		if (LicenceList.size() > 0) {
			for (int i = 0; i < LicenceList.size(); i++) {
				Object obj[] = (Object[]) LicenceList.get(i);
				model.put("LicenceCode", obj[0]);
				model.put("LicenceNo", obj[1]);

			}
		} else {
			request.getSession().setAttribute("success", true);
			request.getSession().setAttribute("successMessage", "Please Add Licence To this Client \\n And Also Enter External Code");
		}
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExcisePOSSale_1", "command", new clsExciseSaleBean());
		} else {
			return new ModelAndView("frmExcisePOSSale", "command", new clsExciseSaleBean());
		}
	}

	// Load Master Data On Form
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadExcisePOSLinkUpData", method = RequestMethod.GET)
	public @ResponseBody List funLoadMasterData(HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		ArrayList list = new ArrayList();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String param = request.getParameter("param").toString();
		String[] spParam = param.split(",");
		String licenceCode = spParam[0];
		String fromDate = spParam[1];
		String toDate = spParam[2];

		fromDate = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];
		toDate = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];

		String sql = "insert into tblexciseposlinkup (strPOSItemCode,strPOSItemName,strClientCode) " + "(select distinct b.strPOSItemCode,b.strPOSItemName,b.strClientCode from tblexcisepossale b " + " where b.strClientCode='" + clientCode + "' and b.strPOSItemCode not in (select c.strPOSItemCode " + " from tblexciseposlinkup c where c.strClientCode='" + clientCode + "'))";
		objExcisePOSLinkUpService.funExecute(sql);

		String posCode = "P01";
		sql = "select strExternalCode from tbllicencemaster where strLicenceCode='" + licenceCode + "' and strClientCode='" + clientCode + "'";
		List licenceList = objGlobalFunctionsService.funGetDataList(sql, "sql");
		if (licenceList.size() > 0) {
			String extCode = licenceList.get(0).toString();
			if (extCode.length() > 7) {
				posCode = extCode.substring(8, extCode.length());
			}

		}

		sql = "select a.strPOSItemCode,a.strPOSItemName, ifnull(b.strBrandCode,'') as strBrandCode," + "ifnull(b.strBrandName,''),a.strBillNo,sum(a.intQuantity),a.dblRate,date(a.dteBillDate),a.intId,a.strPOSCode " + "from tblexcisepossale a left outer join tblexciseposlinkup b on a.strPOSItemCode=b.strPOSItemCode " + "left outer join tblbrandmaster c on a.strBrandCode=c.strBrandCode "
				+ "where a.strPOSCode='" + posCode + "' and date(a.dteBillDate) between '" + fromDate + "' and '" + toDate + "' " + " group by a.strPOSItemCode order by strBrandCode";

		list = (ArrayList) objGlobalFunctionsService.funGetDataList(sql, "sql");

		return list;
	}

	// Save or Update POSSalesDtl Also Bill Generation Logic

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/saveExcisePOSSale", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsExciseSaleBean objBean, BindingResult result, HttpServletRequest req) {

		String urlHits = "1";
		Object dtlBillGen = new Object();
		boolean returnResult = true;
		String stkNotAvailableForBrand = "";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {

			objGlobal = new clsGlobalFunctions();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			List<clsExcisePOSSaleModel> listOfPOSSale = objBean.getListExcisePOSSale();

			if (null != listOfPOSSale && listOfPOSSale.size() > 0) {
				for (clsExcisePOSSaleModel obj : listOfPOSSale) {
					if (obj.getStrBrandCode() != null && !(obj.getStrBrandCode().isEmpty())) {
						if (obj.getStrBillNo().trim() != null) {
							if (!(obj.getStrBillNo().trim().length() > 0)) {
								if (Long.valueOf(obj.getIntQuantity()) != null) {

									// Check is Given Item Has Recipe or Not

									String sql_isRecipe = "select a.strParentCode,a.strParentName from tblexciserecipermasterhd a " + "where strParentCode='" + obj.getStrBrandCode() + "' and a.strClientCode='" + clientCode + "'";
									List isRecipe = objGlobalFunctionsService.funGetDataList(sql_isRecipe, "sql");
									if (isRecipe.size() > 0) {

									} else {
										dtlBillGen = funGenerateBill(objBean, obj, req);
										if (dtlBillGen.toString().equalsIgnoreCase("true")) {
											// returnResult=true;
										} else if (dtlBillGen.toString().equalsIgnoreCase("false")) {
											returnResult = false;
										} else {
											stkNotAvailableForBrand = dtlBillGen.toString() + "," + stkNotAvailableForBrand;
											returnResult = false;
										}
									}
								}
							}
						}
					}

				}
			}

			if (returnResult) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Data Successfully Saved");
				return new ModelAndView("redirect:/frmExcisePOSSale.html?saddr=" + urlHits);
			} else {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Stock Not Available For Brands \\n  " + stkNotAvailableForBrand);
				return new ModelAndView("redirect:/frmExcisePOSSale.html?saddr=" + urlHits);
			}

		} else {
			return new ModelAndView("frmExcisePOSSale?saddr=" + urlHits);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object funGenerateBill(clsExciseSaleBean objBean, clsExcisePOSSaleModel objModel, HttpServletRequest req) {

		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		objGlobal = new clsGlobalFunctions();
		Long saleQty = objModel.getIntQuantity();

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

		String fromDate = req.getSession().getAttribute("startDate").toString();
		fromDate = fromDate.split("/")[2] + "-" + fromDate.split("/")[1] + "-" + fromDate.split("/")[0];
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String toDate = format1.format(cal.getTime());
		String licenceCode = objBean.getStrLicenceCode();
		List brandDataList = new LinkedList();

		String sql_BrandList = "select a.strBrandCode,a.strSizeCode,a.strShortName,b.intQty,a.intPegSize," + " a.dblPegPrice,a.dblMRP,c.strSubCategoryName,c.intMaxSaleQty,d.intConversionFactor " + " from tblbrandmaster a, tblsizemaster b,tblsubcategorymaster c, tblexciseposlinkup d" + " where a.strBrandCode='" + objModel.getStrBrandCode() + "' and a.strSizeCode=b.strSizeCode "
				+ " and a.strBrandCode=d.strBrandCode and d.strClientCode='" + clientCode + "' " + " and a.strSubCategoryCode=c.strSubCategoryCode and  a.strClientCode='" + tempBrandClientCode + "' " + " and b.strClientCode='" + tempSizeClientCode + "' ORDER BY b.intQty DESC";

		Object brandData[] = (Object[]) objGlobalFunctionsService.funGetDataList(sql_BrandList, "sql").get(0);

		String brandCode = brandData[0].toString();
		Integer brandSize = Integer.parseInt(brandData[3].toString());
		Integer brandPegSize = Integer.parseInt(brandData[4].toString());
		Double dblPegPrice = Double.valueOf(brandData[5].toString());
		Double dblMRP = Double.valueOf(brandData[6].toString());
		Integer maxSaleQty = Integer.parseInt(brandData[8].toString());

		String sql_OpData = "select c.intOpBtls,c.intOpPeg,c.intOpML from tblbrandmaster a,tblopeningstock c " + " where a.strBrandCode='" + objModel.getStrBrandCode() + "' and a.strBrandCode=c.strBrandCode  " + " and  a.strClientCode='" + tempBrandClientCode + "' and  c.strClientCode='" + clientCode + "' and c.strLicenceCode='" + licenceCode + "' ";
		List ObjOPDataList = objGlobalFunctionsService.funGetDataList(sql_OpData, "sql");

		Integer intOpBtls = 0;
		Integer intOpPeg = 0;
		Integer intOpML = 0;
		if (ObjOPDataList.size() > 0) {
			Object opData[] = (Object[]) ObjOPDataList.get(0);
			intOpBtls = Integer.parseInt(opData[0].toString());
			intOpPeg = Integer.parseInt(opData[1].toString());
			intOpML = Integer.parseInt(opData[2].toString());
		}
		brandDataList.add(brandData[0]);
		brandDataList.add(brandData[1]);
		brandDataList.add(brandData[2]);
		brandDataList.add(brandData[3]);
		brandDataList.add(brandData[4]);
		brandDataList.add(intOpBtls);
		brandDataList.add(intOpPeg);
		brandDataList.add(intOpML);
		req.getSession().removeAttribute("licenceCode");
		req.getSession().setAttribute("licenceCode", licenceCode);
		LinkedList stkData = objclsFLR3AController.funStockList(brandDataList, req, fromDate, toDate);
		req.getSession().removeAttribute("licenceCode");

		String currentStk = "0.0";
		if (stkData != null) {
			currentStk = stkData.get(6).toString().trim();
		}

		String[] strCurentArr = String.valueOf(currentStk).split("\\.");
		Long stBls = Long.parseLong(strCurentArr[0].toString().trim());
		Long stkPeg = new Long(0);
		Long stkML = Long.parseLong(strCurentArr[1].toString().trim());
		Long availableStk = funStockInML(stBls, stkPeg, stkML, brandDataList);

		if (brandPegSize <= 0) {
			brandPegSize = brandSize;
		}
		Long saleBls = new Long(0);
		Long salePeg = new Long(0);
		Long saleML = new Long(0);

		if (brandSize.equals(brandPegSize)) {
			saleBls = saleQty;
		} else {
			if (Integer.parseInt(brandData[9].toString()) >= 1) {
				salePeg = saleQty;
			} else {
				saleML = saleQty;
			}
		}
		Long SaleQtyInML = funStockInML(saleBls, salePeg, saleML, brandDataList);

		if (availableStk >= SaleQtyInML) {

			if (dblPegPrice == 0) {
				dblPegPrice = dblMRP;
			}

			ArrayList<Integer> ls = new ArrayList<Integer>();
			Random ObjRandom = new Random();

			int cnt = (int) Math.ceil(SaleQtyInML / brandPegSize);
			int intpeg = (int) Math.floor(maxSaleQty / brandPegSize);
			Integer minPeg = 0;
			if (intpeg > 20 && cnt > 20) {
				minPeg = intpeg - 10;
			} else if (intpeg > 10 && cnt > 10) {
				minPeg = 10;
			}

			int totalPegSum = 0;
			for (int i = 0; i < cnt; i++) {
				int randomPeg = ObjRandom.nextInt(intpeg) + 1;
				if (cnt > randomPeg) {
					if (randomPeg >= minPeg) {
						ls.add(randomPeg);
						totalPegSum = totalPegSum + randomPeg;
						if (totalPegSum == cnt) {
							break;
						} else {
							if ((cnt - totalPegSum) < intpeg + 1) {
								ls.add((int) (cnt - totalPegSum));
								break;
							}
						}
					} else {
						i--;
					}
				} else {
					ls.add(cnt);
					break;
				}
			}

			// Logic For Bill Generation And Save in to tblexcisesaledata

			Integer permitLastNo = Integer.parseInt("" + objGlobalFunctionsService.funGetCount("tblpermitmaster", "intId"));
			Long lastNo = objGlobalFunctionsService.funGetCountByClient("tblexcisesaledata", "intBillNo", clientCode);
			ArrayList<clsExciseSaleModel> objList = new ArrayList<clsExciseSaleModel>();
			String saleCode = objModel.getStrPOSCode() + "-" + objModel.getIntId();

			for (int i = 0; i < ls.size(); i++) {
				if (ls.get(i) != 0) {
					clsExciseSaleModel objHdModel = new clsExciseSaleModel();
					String strLicenceCode = objBean.getStrLicenceCode();

					objHdModel.setStrLicenceCode(strLicenceCode);
					objHdModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					objHdModel.setDteBillDate(objModel.getDteBillDate());
					objHdModel.setStrUserCreated(userCode);
					objHdModel.setStrSalesCode(saleCode);
					objHdModel.setStrUserModified(userCode);
					objHdModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					objHdModel.setStrClientCode(clientCode);
					objHdModel.setIntTotalPeg(ls.get(i));

					Double totalAmt = dblPegPrice * (ls.get(i));
					objHdModel.setDblTotalAmt(totalAmt);

					int totalQty = (brandPegSize) * (ls.get(i));
					objHdModel.setIntQty(totalQty);
					objHdModel.setStrItemCode(brandCode);
					objHdModel.setStrSourceEntry("POS-" + objModel.getStrPOSCode());

					int randomPermit = ObjRandom.nextInt(permitLastNo);
					if (randomPermit <= 0) {
						randomPermit = 1;
					}

					String strPermitCode = "PM" + String.format("%06d", randomPermit);
					objHdModel.setStrPermitCode(strPermitCode);
					objHdModel.setIntBillNo(lastNo);
					objList.add(objHdModel);
					lastNo++;
				}
			}
			Boolean retVal = false;
			try {
				retVal = objclsExciseSaleService.funAddBulkly(objList);
				String sql = "UPDATE tblexcisepossale SET strBillNo ='" + lastNo + "' WHERE intId='" + objModel.getIntId() + "' and strClientCode='" + clientCode + "'";
				objExcisePOSLinkUpService.funExecute(sql);
			} catch (Exception e) {
				e.printStackTrace();
				retVal = false;
			}
			return retVal;
		} else {
			return objModel.getStrBrandName();
		}
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
