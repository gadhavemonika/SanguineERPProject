package com.sanguine.excise.controller;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.excise.bean.clsExciseManualSaleBean;
import com.sanguine.excise.model.clsBrandMasterModel;
import com.sanguine.excise.model.clsCategoryMasterModel;
import com.sanguine.excise.model.clsExciseLicenceMasterModel;
import com.sanguine.excise.model.clsExciseManualSaleDtlModel;
import com.sanguine.excise.model.clsExciseManualSaleHdModel;
import com.sanguine.excise.model.clsExciseSaleModel;
import com.sanguine.excise.model.clsRateMasterModel;
import com.sanguine.excise.model.clsSizeMasterModel;
import com.sanguine.excise.model.clsSubCategoryMasterModel;
import com.sanguine.excise.service.clsBrandMasterService;
import com.sanguine.excise.service.clsExciseManualSaleService;
import com.sanguine.excise.service.clsExciseSaleService;
import com.sanguine.model.clsAuditDtlModel;
import com.sanguine.model.clsAuditHdModel;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsExciseManualSaleController {

	@Autowired
	private clsExciseManualSaleService objExciseSalesMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsExciseSaleService objclsExciseSaleService;

	@Autowired
	private clsFLR3AController objclsFLR3AController;

	@Autowired
	private clsGlobalFunctions objGlobalFunction;

	@Autowired
	private clsBrandMasterService objBrandMasterService;

	// Open ExciseSalesMaster
	@SuppressWarnings({ "unused" })
	@RequestMapping(value = "/frmExciseManualSale", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		// String
		// sql_Licence="select strLicenceCode,strLicenceNo from tbllicencemaster where "
		// +
		// "strClientCode='"+clientCode+"' and strPropertyCode='"+propertyCode+"' ";
		//
		// List LicenceList=
		// objGlobalFunctionsService.funGetDataList(sql_Licence,"sql");
		// if(LicenceList.size()>0){
		// Object obj[] = (Object[]) LicenceList.get(0);
		// model.put("LicenceCode",obj[0]);
		// model.put("LicenceNo",obj[1]);
		// }else{
		// req.getSession().setAttribute("success", true);
		// req.getSession().setAttribute("successMessage","Please Add Licence To This Client");
		// }

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseManualSale_1", "command", new clsExciseManualSaleBean());
		} else {
			return new ModelAndView("frmExciseManualSale", "command", new clsExciseManualSaleBean());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/saveExciseSalesMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsExciseManualSaleBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		boolean dtlSuccess = false;
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String licenceCode = objBean.getStrLicenceCode();
			clsExciseManualSaleHdModel objclsSalesModel = funPrepareSalesHdModel(objBean, userCode, clientCode);
			List<clsExciseManualSaleDtlModel> listSalesdtl = objBean.getObjSalesDtlList();

			if (null != listSalesdtl && listSalesdtl.size() > 0) {
				boolean sucess = objExciseSalesMasterService.funAddUpdateExciseSalesMaster(objclsSalesModel);
				if (sucess) {
					Long billId = objclsSalesModel.getIntId();
					objExciseSalesMasterService.funDeleteDtl(billId, clientCode);
					objExciseSalesMasterService.funDeleteSaleData(billId, clientCode);
					Boolean negFlag = false;
					String negBrands = "";
					for (clsExciseManualSaleDtlModel objSalesDtl : listSalesdtl) {

						if (objSalesDtl.getStrBrandCode() != null) {

							// Logic for Check Available stock

							String fromDate = req.getSession().getAttribute("startDate").toString();
							fromDate = fromDate.split("/")[2] + "-" + fromDate.split("/")[1] + "-" + fromDate.split("/")[0];
							String toDate = objclsSalesModel.getDteBillDate();
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

							String sql_BrandList = "select a.strBrandCode,a.strSizeCode,a.strShortName,b.intQty,a.intPegSize,a.strBrandName " + "  from tblbrandmaster a, tblsizemaster b " + " where a.strBrandCode='" + objSalesDtl.getStrBrandCode() + "' and a.strSizeCode=b.strSizeCode  " + " and  a.strClientCode='" + tempBrandClientCode + "' and b.strClientCode='" + tempSizeClientCode
									+ "'  ORDER BY b.intQty DESC";

							Object brandData[] = (Object[]) objGlobalFunctionsService.funGetDataList(sql_BrandList, "sql").get(0);

							String sql_OpData = "select c.intOpBtls,c.intOpPeg,c.intOpML from tblbrandmaster a,tblopeningstock c " + " where a.strBrandCode='" + objSalesDtl.getStrBrandCode() + "' and a.strBrandCode=c.strBrandCode  " + " and  a.strClientCode='" + tempBrandClientCode + "' and  c.strClientCode='" + clientCode + "' and c.strLicenceCode='" + licenceCode + "' ";

							List ObjOPDataList = objGlobalFunctionsService.funGetDataList(sql_OpData, "sql");

							Integer intOpBtls = 0;
							Integer intOpPeg = 0;
							Integer intOpML = 0;
							if (ObjOPDataList.size() > 0) {
								Object opData[] = (Object[]) ObjOPDataList.get(0);
								intOpBtls = Integer.parseInt(opData[0].toString().trim());
								intOpPeg = Integer.parseInt(opData[1].toString().trim());
								intOpML = Integer.parseInt(opData[2].toString().trim());
							}
							brandDataList.add(brandData[0]);
							brandDataList.add(brandData[1]);
							brandDataList.add(brandData[2]);
							brandDataList.add(brandData[3]);
							brandDataList.add(brandData[4]);
							brandDataList.add(intOpBtls);
							brandDataList.add(intOpPeg);
							brandDataList.add(intOpML);
							brandDataList.add(brandData[5]);

							req.getSession().removeAttribute("licenceCode");
							req.getSession().setAttribute("licenceCode", licenceCode);
							LinkedList stkData = objclsFLR3AController.funStockList(brandDataList, req, fromDate, toDate);
							req.getSession().removeAttribute("licenceCode");
							String currentStk = "0.0";
							if (stkData != null) {
								currentStk = stkData.get(6).toString().trim();
							}

							Long saleBls = new Long(0);
							Long salePeg = new Long(0);
							Long saleML = new Long(0);

							if (saleBls != null) {
								saleBls = objSalesDtl.getIntBtl();
							}

							if (salePeg != null) {
								salePeg = objSalesDtl.getIntPeg();
							}

							if (saleBls != null) {
								saleML = objSalesDtl.getIntML();
							}

							Long SaleQty = funStockInML(saleBls, salePeg, saleML, brandDataList);

							String[] strCurentArr = String.valueOf(currentStk).split("\\.");
							Long stBls = Long.parseLong(strCurentArr[0].toString().trim());
							Long stkPeg = new Long(0);
							Long stkML = Long.parseLong(strCurentArr[1].toString().trim());
							Long availableStk = funStockInML(stBls, stkPeg, stkML, brandDataList);

							if (availableStk >= SaleQty) {
								if (objSalesDtl.getIntBtl() != 0 || objSalesDtl.getIntPeg() != 0 || objSalesDtl.getIntML() != 0) {
									long lastSalesDtlNo = objGlobalFunctionsService.funGetCount("tblmanualsalesdtl", "intId");
									objSalesDtl.setIntId(lastSalesDtlNo);
									objSalesDtl.setIntSalesHd(billId);
									objSalesDtl.setStrClientCode(clientCode);
									dtlSuccess = objExciseSalesMasterService.funAddUpdateExciseSalesDtl(objSalesDtl);
								}
							} else {
								negFlag = true;
								negBrands = brandDataList.get(8).toString() + "," + negBrands;
							}
							funAuditSaleDtl(objSalesDtl, req);
						}
					}

					if (negFlag) {
						objExciseSalesMasterService.funDeleteSaleData(billId, clientCode);
						req.getSession().setAttribute("success", true);
						req.getSession().setAttribute("successMessage", "Sale Not Save. \\n For Brand ".concat("" + negBrands) + " \\n Stock Not Available on Date " + objclsSalesModel.getDteBillDate());
						return new ModelAndView("redirect:/frmExciseManualSale.html?saddr=" + urlHits);
					}

				}
			}
			if (dtlSuccess) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", " Sale Details No : ".concat("" + objclsSalesModel.getIntId()) + " Saved Successfully");
				return new ModelAndView("redirect:/frmExciseManualSale.html?saddr=" + urlHits);
			} else {
				return new ModelAndView("redirect:/frmExciseManualSale.html?saddr=" + urlHits);
			}

		} else {
			return new ModelAndView("redirect:/frmExciseManualSale.html?saddr=" + urlHits);
		}
	}

	@SuppressWarnings("rawtypes")
	private clsExciseManualSaleHdModel funPrepareSalesHdModel(clsExciseManualSaleBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();

		clsExciseManualSaleHdModel objModel = new clsExciseManualSaleHdModel();
		if (objBean != null) {
			if (objBean.getObjSalesDtlList() != null && objBean.getObjSalesDtlList().size() > 0) {
				if (objBean.getIntId() != null) {
					if (objBean.getIntId() != 0) {
						List listObject = objExciseSalesMasterService.funGetObject(objBean.getIntId(), clientCode);

						if (listObject != null && listObject.size() > 0) {
							Object obj[] = (Object[]) listObject.get(0);
							clsExciseManualSaleHdModel saleObject = (clsExciseManualSaleHdModel) obj[0];
							objModel.setIntId(saleObject.getIntId());
							objModel.setStrUserCreated(saleObject.getStrUserCreated());
							objModel.setDteDateCreated(saleObject.getDteDateCreated());
							objModel.setStrClientCode(saleObject.getStrClientCode());
						}
					}
				} else {
					long lastNo = objGlobalFunctionsService.funGetCount("tblmanualsaleshd", "intId");
					objModel.setIntId(lastNo);
					objModel.setStrUserCreated(userCode);
					objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					objModel.setStrClientCode(clientCode);
				}

				objModel.setStrLicenceCode(objBean.getStrLicenceCode());
				objModel.setDteBillDate(objBean.getDteBillDate());
				objModel.setStrUserEdited(userCode);
				objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setStrSourceEntry(objBean.getStrSourceEntry());
			}
		}
		funAuditSale(objModel);
		return objModel;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadExciseSalesMasterData", method = RequestMethod.GET)
	public @ResponseBody clsExciseManualSaleBean funAssignFieldsForForm(@RequestParam("saleId") long saleId, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsExciseManualSaleBean objBean = null;
		List objList = objExciseSalesMasterService.funGetObject(saleId, clientCode);

		if (objList.size() > 0) {
			Object obj[] = (Object[]) objList.get(0);
			clsExciseManualSaleHdModel objModel = (clsExciseManualSaleHdModel) obj[0];
			clsExciseLicenceMasterModel licenceModel = (clsExciseLicenceMasterModel) obj[1];
			objBean = funPreapareBean(objModel);
			objBean.setStrLicenceNo(licenceModel.getStrLicenceNo());

			List objDtlList = objExciseSalesMasterService.funGetSalesDtlList(saleId, clientCode);
			List<clsExciseManualSaleDtlModel> listSaleDtl = new ArrayList<clsExciseManualSaleDtlModel>();
			for (int i = 0; i < objDtlList.size(); i++) {
				Object[] ob = (Object[]) objDtlList.get(i);
				clsExciseManualSaleDtlModel salesDtl = (clsExciseManualSaleDtlModel) ob[0];
				clsBrandMasterModel brandMaster = (clsBrandMasterModel) ob[1];
				salesDtl.setStrBrandName(brandMaster.getStrBrandName());

				String isBrandGlobal = "Custom";
				String isSizeGlobal = "Custom";
				String tempSizeClientCode = clientCode;
				try {
					isSizeGlobal = request.getSession().getAttribute("strSizeMaster").toString();
				} catch (Exception e) {
					isSizeGlobal = "Custom";
				}
				if (isSizeGlobal.equalsIgnoreCase("All")) {
					tempSizeClientCode = "All";
				}

				String tempBrandClientCode = clientCode;
				try {
					isBrandGlobal = request.getSession().getAttribute("strBrandMaster").toString();
				} catch (Exception e) {
					isBrandGlobal = "Custom";
				}
				if (isBrandGlobal.equalsIgnoreCase("All")) {
					tempBrandClientCode = "All";
				}

				String fromDate = request.getSession().getAttribute("startDate").toString();
				fromDate = fromDate.split("/")[2] + "-" + fromDate.split("/")[1] + "-" + fromDate.split("/")[0];
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				String toDate = format1.format(cal.getTime());
				List brandDataList = new LinkedList();
				String licenceCode = request.getParameter("licenceCode");

				String sql_BrandList = "select a.strBrandCode,a.strSizeCode,a.strShortName,b.intQty,a.intPegSize " + "  from tblbrandmaster a, tblsizemaster b " + " where a.strBrandCode='" + brandMaster.getStrBrandCode() + "' and a.strSizeCode=b.strSizeCode  " + " and  a.strClientCode='" + tempBrandClientCode + "' and b.strClientCode='" + tempSizeClientCode + "' ORDER BY b.intQty DESC";

				Object brandData[] = (Object[]) objGlobalFunctionsService.funGetDataList(sql_BrandList, "sql").get(0);

				String sql_OpData = "select c.intOpBtls,c.intOpPeg,c.intOpML from tblbrandmaster a,tblopeningstock c " + " where a.strBrandCode='" + brandMaster.getStrBrandCode() + "' and a.strBrandCode=c.strBrandCode  " + " and  a.strClientCode='" + tempBrandClientCode + "' and  c.strClientCode='" + clientCode + "' and c.strLicenceCode='" + licenceCode + "' ";

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
				request.getSession().removeAttribute("licenceCode");
				request.getSession().setAttribute("licenceCode", licenceCode);
				LinkedList stkData = objclsFLR3AController.funStockList(brandDataList, request, fromDate, toDate);
				if (stkData != null) {
					String currentStk = stkData.get(6).toString().trim();
					salesDtl.setStrOpStk(currentStk);
				} else {
					salesDtl.setStrOpStk("" + 0.0);
				}

				salesDtl.setIntBrandSize(Long.parseLong(brandDataList.get(3).toString()));
				salesDtl.setIntPegSize(Long.parseLong(brandDataList.get(4).toString()));

				listSaleDtl.add(salesDtl);

			}
			objBean.setObjSalesDtlList(listSaleDtl);
			// funAuditSaleDtl(objBean);
		} else {
			objBean = new clsExciseManualSaleBean();
			objBean.setIntId(new Long(0));
		}

		return objBean;
	}

	public clsExciseManualSaleBean funPreapareBean(clsExciseManualSaleHdModel objModel) {

		clsExciseManualSaleBean objBean = new clsExciseManualSaleBean();
		objBean.setIntId(objModel.getIntId());
		objBean.setStrLicenceCode(objModel.getStrLicenceCode());
		objBean.setDteBillDate(objModel.getDteBillDate());
		objBean.setStrClientCode(objModel.getStrClientCode());
		objBean.setStrUserCreated(objModel.getStrUserCreated());
		objBean.setStrUserEdited(objModel.getStrUserEdited());
		objBean.setDteDateCreated(objModel.getDteDateCreated());
		objBean.setDteDateEdited(objModel.getDteDateEdited());
		objBean.setStrSourceEntry(objModel.getStrSourceEntry());

		return objBean;
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

	@SuppressWarnings("unchecked")
	public void funAuditSale(clsExciseManualSaleHdModel objModel) {

		clsAuditHdModel objAuditHd = new clsAuditHdModel();
		// int count;
		String count = objModel.getIntId().toString();
		int cnt = count.length();
		String countSql = "select count(*) from dbwebmms.tblaudithd " + "where left(strTransCode," + cnt + ")='" + objModel.getIntId() + "' and strClientCode='" + objModel.getStrClientCode() + "'";

		List<BigInteger> saveUpdateList = objGlobalFunctionsService.funGetDataList(countSql, "sql");
		cnt = saveUpdateList.get(0).intValue();
		if (saveUpdateList != null) {
			objAuditHd.setDtBillDate(objModel.getDteBillDate());
			if (cnt == 0) {
				objAuditHd.setStrTransCode(objModel.getIntId().toString());
			} else {
				objAuditHd.setStrTransCode(objModel.getIntId().toString() + "-" + cnt);
			}
			objAuditHd.setStrUserCreated(objModel.getStrUserCreated());
			objAuditHd.setStrUserModified(objModel.getStrUserEdited());
			objAuditHd.setStrClientCode(objModel.getStrClientCode());
			objAuditHd.setDtDateCreated(objModel.getDteDateCreated());
			objAuditHd.setDtLastModified(objModel.getDteDateEdited());
			objAuditHd.setStrAuthorise("No");
			objAuditHd.setStrTransMode("Save");
			objAuditHd.setStrTransType("frmExciseManualSale");
		}
		objGlobalFunctionsService.funSaveAuditHd(objAuditHd);

	}

	@SuppressWarnings("unchecked")
	public void funAuditSaleDtl(clsExciseManualSaleDtlModel objSaleDtl, HttpServletRequest req) {

		clsAuditDtlModel objAduitDtl = new clsAuditDtlModel();
		if (objSaleDtl.getIntSalesHd() != null) {
			int cntTransCode = objSaleDtl.getIntSalesHd().toString().length();
			int cntBrandCode = objSaleDtl.getStrBrandCode().toString().length();
			String countSql = "select count(*) from dbwebmms.tblauditdtl " + "where left(strTransCode," + cntTransCode + ")='" + objSaleDtl.getIntSalesHd() + "' and left(strProdCode," + cntBrandCode + ")='" + objSaleDtl.getStrBrandCode() + "' and strClientCode='" + objSaleDtl.getStrClientCode() + "'";

			List<BigInteger> countList = objGlobalFunctionsService.funGetDataList(countSql, "sql");
			int cnt = countList.get(0).intValue();

			if (cnt == 0) {
				objAduitDtl.setStrTransCode(objSaleDtl.getIntSalesHd().toString());
			} else {
				objAduitDtl.setStrTransCode(objSaleDtl.getIntSalesHd().toString() + "-" + cnt);
			}
			objAduitDtl.setStrProdCode(objSaleDtl.getStrBrandCode().toString());
			String btlQty = objSaleDtl.getIntBtl().toString();
			String pegQty = objSaleDtl.getIntPeg().toString();
			String btlPeg = btlQty + "." + pegQty;
			String mlQty = objSaleDtl.getIntML().toString();
			if (mlQty.equalsIgnoreCase("0")) {
				String totlbtlPeg = objGlobalFunction.funConversionMLPeg(objSaleDtl.getStrBrandCode(), btlPeg, "ml", objSaleDtl.getStrClientCode(), req);
				objAduitDtl.setDblQty(Double.parseDouble(totlbtlPeg));
			} else {
				String qtyInBtl = objGlobalFunction.funConversionMLPeg(objSaleDtl.getStrBrandCode(), mlQty, "ml", objSaleDtl.getStrClientCode(), req);
				objAduitDtl.setDblQty(Double.parseDouble(qtyInBtl + "BTL"));
			}
			objAduitDtl.setStrClientCode(objSaleDtl.getStrClientCode());
			objGlobalFunctionsService.funSaveAuditDtl(objAduitDtl);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadBrandData", method = RequestMethod.GET)
	public @ResponseBody clsBrandMasterModel funAssignFieldsForForm(@RequestParam("brandCode") String brandCode, @RequestParam("licencecode") String licencecode, HttpServletRequest req) {

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

		clsBrandMasterModel objModel = null;
		List objList = objBrandMasterService.funGetObject(brandCode, tempBrandClientCode);
		if (objList.isEmpty()) {
			objModel = new clsBrandMasterModel();
			objModel.setStrBrandCode("Invalid Code");
		} else {

			Object obj[] = (Object[]) objList.get(0);
			clsBrandMasterModel objModel1 = (clsBrandMasterModel) obj[0];
			clsSubCategoryMasterModel objSubCategoryMasterModel = (clsSubCategoryMasterModel) obj[1];
			clsCategoryMasterModel objCategoryMasterModel = (clsCategoryMasterModel) obj[2];
			clsSizeMasterModel objSizeMaster = (clsSizeMasterModel) obj[3];

			objModel = objModel1;
			objModel.setStrCategoryName(objCategoryMasterModel.getStrCategoryName());
			objModel.setStrSubCategoryName(objSubCategoryMasterModel.getStrSubCategoryName());
			objModel.setStrSizeName(objSizeMaster.getStrSizeName());
			objModel.setIntSizeQty(objSizeMaster.getIntQty());

			// Set Rate of the Brand
			clsRateMasterModel objRateModel = objBrandMasterService.funGetRateObject(brandCode, clientCode);
			objModel.setDblRate(objRateModel != null ? objRateModel.getDblRate() : 0);

			String fromDate = req.getSession().getAttribute("startDate").toString();
			fromDate = fromDate.split("/")[2] + "-" + fromDate.split("/")[1] + "-" + fromDate.split("/")[0];
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			String toDate = format1.format(cal.getTime());
			List brandDataList = new LinkedList();

			String sql_BrandList = "select a.strBrandCode,a.strSizeCode,a.strShortName,b.intQty,a.intPegSize " + "  from tblbrandmaster a, tblsizemaster b " + " where a.strBrandCode='" + brandCode + "' and a.strSizeCode=b.strSizeCode  " + " and  a.strClientCode='" + tempBrandClientCode + "' and b.strClientCode='" + tempSizeClientCode + "' ORDER BY b.intQty DESC";
			Object brandData[] = (Object[]) objGlobalFunctionsService.funGetDataList(sql_BrandList, "sql").get(0);

			String sql_OpData = "select c.intOpBtls,c.intOpPeg,c.intOpML from tblbrandmaster a,tblopeningstock c " + " where a.strBrandCode='" + brandCode + "' and a.strBrandCode=c.strBrandCode  " + " and  a.strClientCode='" + tempBrandClientCode + "' and  c.strClientCode='" + clientCode + "' and c.strLicenceCode='" + licencecode + "' ";
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

			req.getSession().removeAttribute("licenceCode");
			req.getSession().setAttribute("licenceCode", licencecode);
			LinkedList stkData = objclsFLR3AController.funStockList(brandDataList, req, fromDate, toDate);
			req.getSession().removeAttribute("licenceCode");
			if (stkData != null) {
				String currentStk = stkData.get(6).toString().trim();
				objModel.setStrAvailableStk(currentStk);
			} else {
				objModel.setStrAvailableStk("" + 0.0);
			}
		}
		return objModel;
	}

}
