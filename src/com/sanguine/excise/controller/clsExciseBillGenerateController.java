package com.sanguine.excise.controller;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.sanguine.excise.bean.clsExciseBillGenerateBean;
import com.sanguine.excise.model.clsBrandMasterModel;
import com.sanguine.excise.model.clsExciseManualSaleDtlModel;
import com.sanguine.excise.model.clsExciseManualSaleHdModel;
import com.sanguine.excise.model.clsExciseSaleModel;
import com.sanguine.excise.model.clsOneDayPassHdModel;
import com.sanguine.excise.service.clsExciseBillGenerateService;
import com.sanguine.excise.service.clsExciseSaleService;
import com.sanguine.excise.service.clsOneDayPassService;
import com.sanguine.model.clsAuditDtlModel;
import com.sanguine.model.clsAuditHdModel;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsExciseBillGenerateController {

	@Autowired
	private clsExciseBillGenerateService objBillGenService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsExciseSaleService objclsExciseSaleService;

	@Autowired
	private clsFLR3AController objclsFLR3AController;

	@Autowired
	private clsOneDayPassService objclsOneDayPassService;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	// Global Variable
	String licenceCode = "";

	// Open ExciseBillGenerate Form
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/frmExciseBillGenerate", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest req) {

		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propoertyCode = req.getSession().getAttribute("propertyCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		String Licence_sql = "select a.strLicenceNo,CONCAT(a.strAddress1,',',a.strAddress2," + "',',a.strAddress3,',',b.strCityName,CONCAT('-',a.strPINCode)) as address,a.strLicenceCode " + "from tbllicencemaster a ,tblcitymaster b " + "where a.strCity=b.strCityCode and a.strClientCode='" + clientCode + "' and a.strPropertyCode='" + propoertyCode + "' ";
		List licenceMaster = objGlobalFunctionsService.funGetDataList(Licence_sql, "sql");

		if (licenceMaster.size() > 0) {
			Object licenceData[] = (Object[]) licenceMaster.get(0);

			String licenceNo = licenceData[0].toString();
			req.getSession().removeAttribute("LicenceNo");
			req.getSession().setAttribute("LicenceNo", licenceNo);

			String address = licenceData[1].toString();
			req.getSession().removeAttribute("address");
			req.getSession().setAttribute("address", address);

			String licenceCode = licenceData[2].toString();
			req.getSession().removeAttribute("licenceCode");
			req.getSession().setAttribute("licenceCode", licenceCode);

		} else {
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Please Add Licence To This Client");
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseBillGenerate_1", "command", new clsExciseBillGenerateBean());
		} else {
			return new ModelAndView("frmExciseBillGenerate", "command", new clsExciseBillGenerateBean());
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadSaleDataColumns", method = RequestMethod.GET)
	public @ResponseBody List funExciseAbstractReportColumns(HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String fromDate1 = req.getParameter("fromDate");
		String toDate1 = req.getParameter("toDate");
		String licenceCode = req.getParameter("licenceCode");

		String fromDate = fromDate1.split("-")[2] + "-" + fromDate1.split("-")[1] + "-" + fromDate1.split("-")[0];
		String toDate = toDate1.split("-")[2] + "-" + toDate1.split("-")[1] + "-" + toDate1.split("-")[0];
		LinkedList<Object> resList = new LinkedList<Object>();

		String saleData_sql = "select a.intId,a.dteBillDate,a.strUserCreated,a.dteDateCreated,a.strLicenceCode,a.strSourceEntry " + " from tblmanualsaleshd a where date(a.dteBillDate) BETWEEN '" + fromDate + "' and '" + toDate + "' and a.strLicenceCode='" + licenceCode + "' " + " and a.strClientCode='" + clientCode + "' ";
		List saleData = objGlobalFunctionsService.funGetDataList(saleData_sql, "sql");
		for (int i = 0; i < saleData.size(); i++) {
			LinkedList<String> ls = new LinkedList<String>();
			Object data[] = (Object[]) saleData.get(i);
			ls.add(data[0].toString());
			ls.add(data[1].toString());
			ls.add(data[2].toString());
			ls.add(data[3].toString());
			ls.add(data[4].toString());
			ls.add(data[5].toString());
			resList.add(ls);
		}
		return resList;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/exciseBillGenerate", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsExciseBillGenerateBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";

		boolean dtlBillGen = false;
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
			// licenceCode=
			// req.getSession().getAttribute("licenceCode").toString();
			licenceCode = objBean.getStrlicenceCode();

		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {

			String fromDate1 = objBean.getStrFromDate().toString();
			String fromDate = fromDate1.split("-")[2] + "-" + fromDate1.split("-")[1] + "-" + fromDate1.split("-")[0];

			// String toDate1=objBean.getStrToDate().toString();
			// String
			// toDate=toDate1.split("-")[2]+"-"+toDate1.split("-")[1]+"-"+toDate1.split("-")[0];

			Date date = new Date();
			String toDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

			Boolean negFlag = false;
			String negBrands = "";

			if (objBean.getSaleHdList() != null) {
				if (objBean.getSaleHdList().size() > 0) {
					objBillGenService.funDeleteSaleData(fromDate, toDate, clientCode, licenceCode);
					for (clsExciseManualSaleHdModel objSalesHdModel : objBean.getSaleHdList()) {

						if (objSalesHdModel.getIntId() != null && objSalesHdModel.getIntId().toString().trim().length() > 0) {
							funAuditHdBill(objSalesHdModel, userCode, clientCode);
							List objDtlList = objBillGenService.funGetSalesDtlList(objSalesHdModel.getIntId(), clientCode);

							for (int i = 0; i < objDtlList.size(); i++) {

								Object obj[] = (Object[]) objDtlList.get(i);
								clsExciseManualSaleDtlModel objSalesDtl = (clsExciseManualSaleDtlModel) obj[0];
								clsBrandMasterModel objBrandModel = (clsBrandMasterModel) obj[1];
								try {

									String sqlRecipe = "select a.strParentCode,b.strBrandCode,b.dblQty,c.intPegSize,d.intQty  from tblexciserecipermasterhd a, " + " tblexciserecipermasterdtl b,tblbrandmaster c,tblsizemaster d  " + " where a.strRecipeCode=b.strRecipeCode and a.strParentCode=c.strBrandCode " + " and c.strSizeCode=d.strSizeCode and a.strParentCode='" + objSalesDtl.getStrBrandCode()
											+ "' and b.strClientCode='" + clientCode + "'" + " and a.strClientCode='" + clientCode + "'";

									String strBrandCode = "";
									String qtyBtl = "";
									String qtyPeg = "";
									String qtyML = "";

									List listRecipeData = objGlobalFunctionsService.funGetDataList(sqlRecipe, "sql");
									if (listRecipeData.size() > 0) {
										for (int cnt = 0; cnt < listRecipeData.size(); cnt++) {
											Object[] objRecipeData = (Object[]) listRecipeData.get(cnt);

											strBrandCode = objRecipeData[1].toString();

											Long dblConvBtlInML = (Long.parseLong(objRecipeData[4].toString())) * (Long.parseLong(objSalesDtl.getIntBtl().toString()));
											Long dblPegintoML = (Long.parseLong(objRecipeData[3].toString())) * (Long.parseLong(objSalesDtl.getIntPeg().toString()));
											Long totalInML = Long.parseLong(objSalesDtl.getIntML().toString()) + dblConvBtlInML + dblPegintoML;
											totalInML = totalInML * Math.round(Double.parseDouble((objRecipeData[2].toString())));
											String strtotalInML = String.valueOf(totalInML);

											dtlBillGen = funGenerateBill(strBrandCode, "0", "", strtotalInML, objSalesHdModel.getDteBillDate(), objSalesHdModel.getIntId().toString(), licenceCode, objSalesHdModel.getStrSourceEntry(), req);

										}

									} else {

										dtlBillGen = funGenerateBill(objSalesDtl.getStrBrandCode(), objSalesDtl.getIntBtl().toString(), objSalesDtl.getIntPeg().toString(), objSalesDtl.getIntML().toString(), objSalesHdModel.getDteBillDate(), objSalesHdModel.getIntId().toString(), licenceCode, objSalesHdModel.getStrSourceEntry(), req);
									}
									if (!dtlBillGen) {
										negFlag = true;
										objBillGenService.funDeleteSaleData(fromDate, toDate, clientCode, licenceCode);
										negBrands = objBrandModel.getStrBrandName();
									}

									if (negFlag) {
										req.getSession().setAttribute("success", true);
										req.getSession().setAttribute("successMessage", "Bill Not Generate. \\n Due To Brand ".concat("" + negBrands) + " \\n Stock Not Available on Date " + objSalesHdModel.getDteBillDate());
										return new ModelAndView("redirect:/frmExciseBillGenerate.html?saddr=" + urlHits);
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}

			if (dtlBillGen) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Bill Generated Successfully");
				return new ModelAndView("redirect:/frmExciseBillGenerate.html?saddr=" + urlHits);
			} else {
				return new ModelAndView("redirect:/frmExciseBillGenerate.html?saddr=" + urlHits);
			}
		} else {
			return new ModelAndView("redirect:/frmExciseBillGenerate.html?saddr=" + urlHits);
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean funGenerateBill(String strBrandCode, String strBtls, String strPeg, String strML, String dteBillDate, String strSaleCode, String strLicenceCode, String strSourceEntry, HttpServletRequest req) {
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();

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

		objGlobal = new clsGlobalFunctions();
		boolean result = false;
		// if(objSalesDtl.getStrBillGenFlag().equalsIgnoreCase("N")){

		if (!strBrandCode.isEmpty()) {

			Long bottals = new Long(0);
			Long intpegs = new Long(0);
			Long intML = new Long(0);

			if (strBtls != null && !(strBtls.isEmpty())) {
				bottals = Long.parseLong(strBtls);
			}

			if (strPeg != null && !(strPeg.isEmpty())) {
				intpegs = Long.parseLong(strPeg);
			}

			if (strML != null && !(strML.isEmpty())) {
				intML = Long.parseLong(strML);
			}

			List brandDataList = new LinkedList();
			String sql_BrandList = "select a.strBrandCode,a.strSizeCode,a.strShortName,b.intQty,a.intPegSize,  " + " ifnull(d.dblRate,'0') as rate,c.intMaxSaleQty, " + " a.strBrandName from  tblbrandmaster a LEFT OUTER JOIN tblratemaster d " + " ON d.strBrandCode = a.strBrandCode  AND d.strClientCode='" + clientCode + "'," + " tblsizemaster b,tblsubcategorymaster c " + " where a.strBrandCode='"
					+ strBrandCode + "' " + " and a.strSizeCode=b.strSizeCode and a.strSubCategoryCode=c.strSubCategoryCode " + " and a.strClientCode='" + tempBrandClientCode + "' and b.strClientCode='" + tempSizeClientCode + "' ";

			Object brandData[] = (Object[]) objGlobalFunctionsService.funGetDataList(sql_BrandList, "sql").get(0);

			String sql_OpData = "select c.intOpBtls,c.intOpPeg,c.intOpML from tblbrandmaster a,tblopeningstock c " + " where a.strBrandCode='" + strBrandCode + "' and a.strBrandCode=c.strBrandCode  " + " and  a.strClientCode='" + tempBrandClientCode + "' and  c.strClientCode='" + clientCode + "' and c.strLicenceCode='" + strLicenceCode + "' ";
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

			brandDataList.add(brandData[0]); // BrandCode
			brandDataList.add(brandData[1]); // SizeCode
			brandDataList.add(brandData[2]); // ShortName
			brandDataList.add(brandData[3]); // intSize
			brandDataList.add(brandData[4]); // intPegSize
			brandDataList.add(intOpBtls); // OpeningBottals
			brandDataList.add(intOpPeg); // OpeningPegs
			brandDataList.add(intOpML); // OpeningMLs
			System.out.println(brandData[7]);

			// brandDataList.add("B0002"); //BrandCode
			// brandDataList.add("S00005"); //SizeCode
			// brandDataList.add("FOSTER PINT"); //ShortName
			// brandDataList.add(""); //intSize
			// brandDataList.add(brandData[4]); //intPegSize
			// brandDataList.add(intOpBtls); //OpeningBottals
			// brandDataList.add(intOpPeg); //OpeningPegs
			// brandDataList.add(intOpML); //OpeningMLs
			// System.out.println(brandData[7]);
			System.out.println(brandData[2]);

			String fromDate = req.getSession().getAttribute("startDate").toString();
			fromDate = fromDate.split("/")[2] + "-" + fromDate.split("/")[1] + "-" + fromDate.split("/")[0];

			String currentStk = "0.0";
			req.getSession().removeAttribute("licenceCode");
			req.getSession().setAttribute("licenceCode", licenceCode);
			LinkedList stkData = objclsFLR3AController.funStockList(brandDataList, req, fromDate, dteBillDate);
			if (stkData != null) {
				currentStk = stkData.get(6).toString().trim();
			}

			Long SaleQty = funStockInML(bottals, intpegs, intML, brandDataList);
			String[] strCurentArr = String.valueOf(currentStk).split("\\.");
			Long stBls = Long.parseLong(strCurentArr[0].toString().trim());
			Long stkPeg = new Long(0);
			Long stkML = Long.parseLong(strCurentArr[1].toString().trim());
			Long availableStk = funStockInML(stBls, stkPeg, stkML, brandDataList);

			if (availableStk >= SaleQty) {

				Integer brandSize = Integer.parseInt(brandData[3].toString());
				Integer pegSize = Integer.parseInt(brandData[4].toString());
				Double rate = Double.parseDouble(brandData[5].toString());
				Integer maxSaleQty = Integer.parseInt(brandData[6].toString());

				ArrayList<Integer> saleDevidedList = new ArrayList<Integer>();
				Random ObjRandom = new Random();
				Long quantity = (long) 0;

				if (pegSize <= 0) {
					pegSize = brandSize;
				}

				Long btsMl = brandSize * bottals;
				Long pegMl = pegSize * intpegs;
				quantity = btsMl + pegMl + intML;

				int cnt = (int) Math.ceil(quantity / pegSize);

				int totalPegSum = 0;
				int intpeg = 0;
				intpeg = (int) Math.floor(maxSaleQty / pegSize);
				Integer minPeg = 0;
				if (intpeg > 20 && cnt > 20) {
					minPeg = intpeg - 10;
				} else if (intpeg > 10 && cnt > 10) {
					minPeg = 10;
				}

				for (int i = 0; i < cnt; i++) {
					int randomPeg = ObjRandom.nextInt(intpeg) + 1;
					if (cnt > randomPeg) {
						if (randomPeg >= minPeg) {
							saleDevidedList.add(randomPeg);
							totalPegSum = totalPegSum + randomPeg;
							if (totalPegSum == cnt) {
								break;
							} else {
								if ((cnt - totalPegSum) < intpeg + 1) {
									saleDevidedList.add((int) (cnt - totalPegSum));
									break;
								}
							}
						} else {
							i--;
						}
					} else {
						saleDevidedList.add(cnt);
						break;
					}
				}
				result = funAddSaleDataBulkly(saleDevidedList, strBrandCode, pegSize, rate, dteBillDate, strLicenceCode, strSaleCode, strSourceEntry, clientCode, userCode);

			} else {
				result = false;
			}// Availabel stock check

		}// BrandCode Empty

		return result;
	}

	@SuppressWarnings("rawtypes")
	private boolean funAddSaleDataBulkly(ArrayList<Integer> saleDevidedList, String strBrandCode, Integer pegSize, Double rate, String dteBillDate, String strLicenceCode, String strSaleCode, String strSourceEntry, String clientCode, String userCode) {

		Boolean success = false;

		// For one Day Pass permit Holder

		Long oneDaypermitFirst = new Long(0);
		Long oneDaypermitLast = new Long(0);
		clsOneDayPassHdModel oneDayPassModel = objclsOneDayPassService.funGetOneDayPassByDate(dteBillDate, clientCode);

		List<String> checkDuplicateRecords = new ArrayList<String>();

		if (oneDayPassModel != null) {

			oneDaypermitLast = oneDayPassModel.getIntToNo();
			// String oneDayPassNoString =
			// " SELECT MAX(CAST(a.strPermitCode AS SIGNED INTEGER)) AS maxNumber "
			// +
			// " from tblexcisesaledata a  where date(a.dteBillDate) ='"+oneDayPassModel.getDteDate()+"' and  a.strClientCode='"+clientCode+"' ";

			// String maxNumber = (String)ObjOPDataList.get(0);
			// if(maxNumber ==null){
			// maxNumber= oneDayPassModel.getIntFromNo().toString();
			// oneDaypermitFirst= Long.parseLong(maxNumber);
			// }else{
			// oneDaypermitFirst= Long.parseLong(maxNumber);
			// oneDaypermitFirst++;
			// }

			String oneDayPassNoString = " SELECT a.strPermitCode AS maxNumber " + " from tblexcisesaledata a  where date(a.dteBillDate) ='" + oneDayPassModel.getDteDate() + "' " + " and  a.strClientCode='" + clientCode + "' order by a.intBillNo desc limit 1  ";
			List ObjOPDataList = objGlobalFunctionsService.funGetDataList(oneDayPassNoString, "sql");
			if (ObjOPDataList.size() <= 0) {
				oneDaypermitFirst = oneDayPassModel.getIntFromNo();
			} else {
				String maxNumber = (String) ObjOPDataList.get(0);
				oneDaypermitFirst = Long.parseLong(maxNumber);
				oneDaypermitFirst++;
			}

		}

		// Logic For Bill Generation And Save in to tblexcisesaledata

		Integer permitLastNo = Integer.parseInt("" + objGlobalFunctionsService.funGetCount("tblpermitmaster", "intId"));
		Long lastNo = objGlobalFunctionsService.funGetCountByClient("tblexcisesaledata", "intBillNo", clientCode);
		ArrayList<clsExciseSaleModel> objList = new ArrayList<clsExciseSaleModel>();
		Random ObjRandom = new Random();

		// One To Many Logic
		Integer oneToMany = 0;

		for (int i = 0; i < saleDevidedList.size(); i++) {
			if (saleDevidedList.get(i) != 0) {

				// String year=dteBillDate.split("-")[0];
				// String mnth=dteBillDate.split("-")[1];
				// String
				// strBillNo=objGlobalFunctions.funGenerateDocumentCode("frmExciseBillGenerate",
				// dteBillDate, req);

				clsExciseSaleModel objHdModel = new clsExciseSaleModel();
				objHdModel.setStrUserCreated(userCode);
				objHdModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objHdModel.setDteBillDate(dteBillDate);
				objHdModel.setStrLicenceCode(strLicenceCode);
				objHdModel.setStrSalesCode(strSaleCode);
				objHdModel.setStrUserModified(userCode);
				objHdModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objHdModel.setStrClientCode(clientCode);
				objHdModel.setIntTotalPeg(saleDevidedList.get(i));

				Double totalAmt = rate * (saleDevidedList.get(i));
				objHdModel.setDblTotalAmt(totalAmt);

				int totalQty = (pegSize) * (saleDevidedList.get(i));
				objHdModel.setIntQty(totalQty);
				objHdModel.setStrItemCode(strBrandCode);
				objHdModel.setStrSourceEntry(strSourceEntry);

				// One To Many Logic Implementation
				int oneToManyCnt = ObjRandom.nextInt(1);
				if (oneToManyCnt == 0) {
					oneToMany++;
				}

				if (oneToMany >= 3) {
					// String oneToManypermitCode
					// ="select a.intBillNo,a.strPermitCode from tblexcisesaledata a "
					// +
					// " where date(a.dteBillDate) ='"+dteBillDate+"' and a.strItemCode !='"+strBrandCode+"' "
					// + " and a.strClientCode='"+clientCode+"' ";

					String oneToManypermitCode = "SELECT a.intBillNo,a.strPermitCode,b.strSubCategoryCode " + " FROM tblexcisesaledata a,tblbrandmaster b " + " WHERE DATE(a.dteBillDate)='" + dteBillDate + "' AND a.strItemCode !='" + strBrandCode + "' " + " AND a.strItemCode =b.strBrandCode AND b.strSubCategoryCode "
							+ " NOT IN(select p.strSubCategoryCode from tblbrandmaster p where p.strBrandCode='" + strBrandCode + "') " + " AND a.strClientCode='" + clientCode + "' ";
					List ObjOPDataList = objGlobalFunctionsService.funGetDataList(oneToManypermitCode, "sql");

					if (ObjOPDataList.size() > 0) {
						int randomNumber = ObjRandom.nextInt(ObjOPDataList.size());
						Object[] strPermitArr = (Object[]) ObjOPDataList.get(randomNumber);

						// Check Duplicates But Still Not Saved in DataBase
						if (checkDuplicateRecords.contains(strPermitArr[0].toString() + strBrandCode + clientCode)) {

							if (oneDaypermitFirst < oneDaypermitLast) {
								objHdModel.setStrPermitCode(oneDaypermitFirst + "");
								objHdModel.setIntBillNo(lastNo);
								oneDaypermitFirst++;
							} else {
								// int randomPermit =
								// ObjRandom.nextInt(permitLastNo);
								// if(randomPermit<=0){
								// randomPermit=1;
								// }
								// String strPermitCode = "PM" +
								// String.format("%06d", randomPermit);
								// objHdModel.setStrPermitCode(strPermitCode);
								// objHdModel.setIntBillNo(lastNo);
								objHdModel.setStrPermitCode(strPermitArr[1].toString());
								objHdModel.setIntBillNo(Long.parseLong(strPermitArr[0].toString()));
							}

						} else {
							int randomPermit = ObjRandom.nextInt(permitLastNo);
							if (randomPermit <= 0) {
								randomPermit = 1;
							}
							String strPermitCode = "PM" + String.format("%06d", randomPermit);
							objHdModel.setStrPermitCode(strPermitCode);
							objHdModel.setIntBillNo(lastNo);
							// objHdModel.setStrPermitCode(strPermitArr[1].toString());
							// objHdModel.setIntBillNo(Long.parseLong(strPermitArr[0].toString()));
							oneToMany = 0;
						}
					} else {

						if (oneDaypermitFirst < oneDaypermitLast) {
							objHdModel.setStrPermitCode(oneDaypermitFirst + "");
							objHdModel.setIntBillNo(lastNo);
							oneDaypermitFirst++;
						} else {
							int randomPermit = ObjRandom.nextInt(permitLastNo);
							if (randomPermit <= 0) {
								randomPermit = 1;
							}
							String strPermitCode = "PM" + String.format("%06d", randomPermit);
							objHdModel.setStrPermitCode(strPermitCode);
							objHdModel.setIntBillNo(lastNo);
						}// Is One Day Pass

					}// Is Previous Bill
					oneToMany = 0;
				} else {

					if (oneDaypermitFirst < oneDaypermitLast) {

						objHdModel.setStrPermitCode(oneDaypermitFirst + "");
						objHdModel.setIntBillNo(lastNo);
						oneDaypermitFirst++;

					} else {

						int randomPermit = ObjRandom.nextInt(permitLastNo);
						if (randomPermit <= 0) {
							randomPermit = 1;
						}
						String strPermitCode = "PM" + String.format("%06d", randomPermit);
						objHdModel.setStrPermitCode(strPermitCode);
						objHdModel.setIntBillNo(lastNo);

					}// One Day Permit Logic

				}// one To Many Logic

				checkDuplicateRecords.add(objHdModel.getIntBillNo() + objHdModel.getStrItemCode() + objHdModel.getStrClientCode());
				objList.add(objHdModel);
				funAuditDtl(objHdModel);
				lastNo++;
			}// Is Sale Quantity Greater Than Zero
		}

		try {
			success = objclsExciseSaleService.funAddBulkly(objList);
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

	public void funAuditHdBill(clsExciseManualSaleHdModel objHdModel, String userCode, String clientCode) {

		clsAuditHdModel objAuditHd = new clsAuditHdModel();
		// int count;
		String count = objHdModel.getIntId().toString();
		int cnt = count.length();
		String countSql = "select count(*) from dbwebmms.tblaudithd " + "where left(strTransCode," + cnt + ")='" + objHdModel.getIntId() + "' and strClientCode='" + clientCode + "' ";

		List<BigInteger> saveUpdateList = objGlobalFunctionsService.funGetDataList(countSql, "sql");
		cnt = saveUpdateList.get(0).intValue();
		if (saveUpdateList != null) {
			objAuditHd.setDtBillDate(objHdModel.getDteBillDate());
			if (cnt == 0) {
				objAuditHd.setStrTransCode(objHdModel.getIntId().toString());
			} else {
				objAuditHd.setStrTransCode(objHdModel.getIntId().toString() + "-" + cnt);
			}
			objAuditHd.setDtBillDate(objHdModel.getDteBillDate());

			objAuditHd.setStrUserCreated(userCode);
			objAuditHd.setStrUserModified(userCode);
			Date dNow = new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

			String curntDate = ft.format(dNow);
			objAuditHd.setDtDateCreated(curntDate);
			objAuditHd.setDtLastModified(curntDate);
			objAuditHd.setStrClientCode(objHdModel.getStrClientCode());
			objAuditHd.setStrAuthorise("No");
			objAuditHd.setStrTransMode("Save");
			objAuditHd.setStrTransType("frmExciseBillGenerate");
			objAuditHd.setStrClientCode(clientCode);
			objGlobalFunctionsService.funSaveAuditHd(objAuditHd);
		}
	}

	private void funAuditDtl(clsExciseSaleModel objDtlModel) {

		String count = objDtlModel.getStrSalesCode().toString();
		int cnt = count.length();
		String countSql = "select count(*) from dbwebmms.tblaudithd " + "where left(strTransCode," + cnt + ")='" + objDtlModel.getStrSalesCode() + "' and strClientCode='" + objDtlModel.getStrClientCode() + "'";

		List<BigInteger> saveUpdateList = objGlobalFunctionsService.funGetDataList(countSql, "sql");
		cnt = saveUpdateList.get(0).intValue();
		if (saveUpdateList != null) {
			clsAuditDtlModel objAuditDtl = new clsAuditDtlModel();
			cnt = cnt - 1;
			if (cnt == 0) {
				objAuditDtl.setStrTransCode(objDtlModel.getStrSalesCode().toString());
			} else {
				objAuditDtl.setStrTransCode(objDtlModel.getStrSalesCode().toString() + "-" + cnt);
			}
			objAuditDtl.setStrProdCode(objDtlModel.getStrItemCode());
			objAuditDtl.setStrCode(objDtlModel.getIntBillNo().toString());
			objAuditDtl.setDblQty(objDtlModel.getIntQty());
			objAuditDtl.setStrClientCode(objDtlModel.getStrClientCode());
			objGlobalFunctionsService.funSaveAuditDtl(objAuditDtl);
		}
	}

}
