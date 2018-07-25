package com.sanguine.excise.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
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
import com.sanguine.excise.bean.clsExciseStockPostingBean;
import com.sanguine.excise.model.clsBrandMasterModel;
import com.sanguine.excise.model.clsExciseLicenceMasterModel;
import com.sanguine.excise.model.clsExciseStkPostingDtlModel;
import com.sanguine.excise.model.clsExciseStkPostingHdModel;
import com.sanguine.excise.model.clsRateMasterModel;
import com.sanguine.excise.model.clsSizeMasterModel;
import com.sanguine.excise.service.clsBrandMasterService;
import com.sanguine.excise.service.clsExciseStkPostingService;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsExciseStkPostingController {
	@Autowired
	private clsExciseStkPostingService objclsExciseStkPostingService;

	@Autowired
	private clsBrandMasterService objclsBrandMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsFLR3AController objclsFLR3AController;

	private clsGlobalFunctions objGlobal = null;

	@RequestMapping(value = "/frmExcisePhysicalStkPosting", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExcisePhysicalStkPosting_1", "command", new clsExciseStockPostingBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExcisePhysicalStkPosting", "command", new clsExciseStockPostingBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/saveExcisePhyStkPosting", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsExciseStockPostingBean objBean, BindingResult result, HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String urlHits = "1";

		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (!result.hasErrors()) {
			clsExciseStkPostingHdModel objHdModel = funPrepareModel(objBean, request);

			List<clsExciseStkPostingDtlModel> listStkPostDtl = objBean.getPhyStocklist();
			List<clsExciseStkPostingDtlModel> stkDtlList = new ArrayList<clsExciseStkPostingDtlModel>();
			if (null != listStkPostDtl && listStkPostDtl.size() > 0) {
				boolean success = false;
				objclsExciseStkPostingService.funAddUpdate(objHdModel);
				String strPSPCode = objHdModel.getStrPSPCode();
				objclsExciseStkPostingService.funDeleteDtl(strPSPCode, clientCode);
				Long lastNo = objGlobalFunctionsService.funGetCount("tblexcisestockpostingdtl", "intId");

				for (clsExciseStkPostingDtlModel obj : listStkPostDtl) {
					if (null != obj.getStrBrandCode()) {
						obj.setIntId(lastNo);
						obj.setStrPSPCode(strPSPCode);
						obj.setStrClientCode(clientCode);

						if (obj.getIntPhyBtl() == null) {
							obj.setIntPhyBtl(new Long(0));
						}
						if (obj.getIntPhyML() == null) {
							obj.setIntPhyML(new Long(0));
						}
						if (obj.getIntPhyPeg() == null) {
							obj.setIntPhyPeg(new Long(0));
						}
						stkDtlList.add(obj);
						lastNo++;
					}
				}

				if (stkDtlList.size() > 0) {
					success = objclsExciseStkPostingService.funAddUpdateDtl(stkDtlList);
				}
				if (success) {
					request.getSession().setAttribute("success", true);
					request.getSession().setAttribute("successMessage", "Physical Stock Posting Code : ".concat(objHdModel.getStrPSPCode()));
					return new ModelAndView("redirect:/frmExcisePhysicalStkPosting.html?saddr=" + urlHits);
				}
			}
			return new ModelAndView("redirect:/frmExcisePhysicalStkPosting.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmExcisePhysicalStkPosting.html?saddr=" + urlHits);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/exportExcisePhyStkPosting", method = RequestMethod.POST)
	public ModelAndView funExportExcel(@ModelAttribute("command") @Valid clsExciseStockPostingBean objBean, BindingResult result, HttpServletRequest request) {
		String urlHits = "1";

		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (!result.hasErrors()) {

			List<clsExciseStkPostingDtlModel> listStkPostDtl = objBean.getPhyStocklist();
			List ExportList = new ArrayList();
			String header = "SR. No.,Brand Code,Brand Name,Size In ML,Peg Size In ML,MRP,System Btl,System ML,System Peg," + "Physical Btl,Physical ML,Physical Peg,Variance In ML";
			String[] ExcelHeader = header.split(",");
			ExportList.add(ExcelHeader);

			if (null != listStkPostDtl && listStkPostDtl.size() > 0) {
				Long lastNo = new Long("1");
				List stkDtlList = new ArrayList();

				for (clsExciseStkPostingDtlModel obj : listStkPostDtl) {
					if (null != obj.getStrBrandCode()) {
						List DataList = new ArrayList();
						DataList.add(lastNo);
						DataList.add(obj.getStrBrandCode());
						DataList.add(obj.getStrBrandName());
						DataList.add(obj.getIntBrandSize() + "-ML");
						DataList.add(obj.getIntPegSize() + "-ML");
						DataList.add(obj.getDblBrandMRP());
						DataList.add(obj.getIntSysBtl());
						DataList.add(obj.getIntSysML());
						DataList.add(obj.getIntSysPeg());

						if (obj.getIntPhyBtl() == null) {
							DataList.add("0");
						} else {
							DataList.add(obj.getIntPhyBtl());
						}
						if (obj.getIntPhyML() == null) {
							DataList.add("0");
						} else {
							DataList.add(obj.getIntPhyML());
						}
						if (obj.getIntPhyPeg() == null) {
							DataList.add("0");
						} else {
							DataList.add(obj.getIntPhyPeg());
						}
						DataList.add(obj.getIntVarianceInML());
						stkDtlList.add(DataList);
						lastNo++;
					}
				}

				if (stkDtlList.size() > 0) {
					ExportList.add(stkDtlList);
					return new ModelAndView("excelView", "stocklist", ExportList);
				}
			}

			request.getSession().setAttribute("success", true);
			request.getSession().setAttribute("successMessage", "Data Not Found");
			return new ModelAndView("redirect:/frmExcisePhysicalStkPosting.html?saddr=" + urlHits);

		} else {
			return new ModelAndView("redirect:/frmExcisePhysicalStkPosting.html?saddr=" + urlHits);
		}
	}

	@SuppressWarnings({ "rawtypes" })
	private clsExciseStkPostingHdModel funPrepareModel(clsExciseStockPostingBean objBean, HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		Long lastNo = new Long(0);

		clsExciseStkPostingHdModel objHdModel = new clsExciseStkPostingHdModel();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();

		if (objBean != null) {
			if (!(objBean.getStrPSPCode().isEmpty())) {
				List objList = objclsExciseStkPostingService.funGetObject(objBean.getStrPSPCode(), clientCode);
				Object[] ob = (Object[]) objList.get(0);
				clsExciseStkPostingHdModel objHdModel1 = (clsExciseStkPostingHdModel) ob[0];

				if (objHdModel1 != null) {
					objHdModel.setStrPSPCode(objHdModel1.getStrPSPCode());
					objHdModel.setStrUserCreated(objHdModel1.getStrUserCreated());
					objHdModel.setDteDateCreated(objHdModel1.getDteDateCreated());
					objHdModel.setIntId(objHdModel1.getIntId());
					objHdModel.setStrClientCode(objHdModel1.getStrClientCode());
				}
			} else {

				lastNo = objGlobalFunctionsService.funGetCount("tblexcisestockpostinghd", "intId");
				String strPSPCode = "PS" + String.format("%06d", lastNo);

				objHdModel.setStrPSPCode(strPSPCode);
				objHdModel.setStrClientCode(clientCode);
				objHdModel.setStrUserCreated(userCode);
				objHdModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objHdModel.setIntId(lastNo);
			}

			objHdModel.setStrLicenceCode(objBean.getStrLicenceCode());
			objHdModel.setDtePostingDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtePostingDate()));
			objHdModel.setStrUserModified(userCode);
			objHdModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		}
		return objHdModel;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadExcisePhysicalStk", method = RequestMethod.GET)
	public @ResponseBody clsExciseStockPostingBean funAssignFieldsForForm(@RequestParam("strPSPCode") String StrPSPCode, HttpServletRequest request) {

		clsExciseStockPostingBean bean = new clsExciseStockPostingBean();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List listStkPostHd = objclsExciseStkPostingService.funGetObject(StrPSPCode, clientCode);
		if (listStkPostHd.isEmpty()) {
			bean = new clsExciseStockPostingBean();
			bean.setStrPSPCode("Invalid Code");
			return bean;
		} else {
			bean = funPrepareBean(listStkPostHd);

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

			List listStkDtl = objclsExciseStkPostingService.funGetDtlList(StrPSPCode, clientCode, tempSizeClientCode, tempBrandClientCode);
			List<clsExciseStkPostingDtlModel> listStkPostDtl = new ArrayList<clsExciseStkPostingDtlModel>();

			for (int i = 0; i < listStkDtl.size(); i++) {

				Object[] ob = (Object[]) listStkDtl.get(i);
				clsExciseStkPostingDtlModel stkPostDtl = (clsExciseStkPostingDtlModel) ob[0];
				clsBrandMasterModel brandMaster = (clsBrandMasterModel) ob[1];
				clsSizeMasterModel sizeMaster = (clsSizeMasterModel) ob[2];

				clsExciseStkPostingDtlModel objStkPostDtl = new clsExciseStkPostingDtlModel();
				objStkPostDtl.setStrPSPCode(stkPostDtl.getStrPSPCode());
				objStkPostDtl.setStrBrandCode(stkPostDtl.getStrBrandCode());
				objStkPostDtl.setStrBrandName(brandMaster.getStrBrandName());

				clsRateMasterModel objRateMaster = objclsBrandMasterService.funGetRateObject(brandMaster.getStrBrandCode(), clientCode);
				objStkPostDtl.setDblBrandMRP(objRateMaster != null ? objRateMaster.getDblRate() * (sizeMaster.getIntQty() / brandMaster.getIntPegSize()) : 0);

				objStkPostDtl.setIntBrandSize(sizeMaster.getIntQty());
				objStkPostDtl.setIntId(stkPostDtl.getIntId());
				objStkPostDtl.setIntPegSize(brandMaster.getIntPegSize());
				objStkPostDtl.setIntPhyBtl(stkPostDtl.getIntPhyBtl());
				objStkPostDtl.setIntPhyML(stkPostDtl.getIntPhyML());
				objStkPostDtl.setIntPhyPeg(stkPostDtl.getIntPhyPeg());
				objStkPostDtl.setIntSysBtl(stkPostDtl.getIntSysBtl());
				objStkPostDtl.setIntSysML(stkPostDtl.getIntSysML());
				objStkPostDtl.setIntSysPeg(stkPostDtl.getIntSysPeg());

				List brandDataList = new LinkedList();
				Integer intOpBtls = 0;
				Integer intOpPeg = 0;
				Integer intOpML = 0;

				brandDataList.add(brandMaster.getStrBrandCode());
				brandDataList.add(brandMaster.getStrSizeCode());
				brandDataList.add(brandMaster.getStrShortName());
				brandDataList.add(sizeMaster.getIntQty());
				brandDataList.add(brandMaster.getIntPegSize());
				brandDataList.add(intOpBtls);
				brandDataList.add(intOpPeg);
				brandDataList.add(intOpML);

				Long sysStock = funStockInML(objStkPostDtl.getIntSysBtl(), objStkPostDtl.getIntSysPeg(), objStkPostDtl.getIntSysML(), brandDataList);
				Long phyStock = funStockInML(objStkPostDtl.getIntPhyBtl(), objStkPostDtl.getIntPhyPeg(), objStkPostDtl.getIntPhyML(), brandDataList);

				objStkPostDtl.setIntVarianceInML(sysStock - phyStock);
				listStkPostDtl.add(objStkPostDtl);
			}
			bean.setPhyStocklist(listStkPostDtl);
		}
		return bean;
	}

	@SuppressWarnings("rawtypes")
	private clsExciseStockPostingBean funPrepareBean(List listStkPostHd) {
		objGlobal = new clsGlobalFunctions();
		clsExciseStockPostingBean objBean = new clsExciseStockPostingBean();
		if (listStkPostHd.size() > 0) {
			Object[] ob = (Object[]) listStkPostHd.get(0);
			clsExciseStkPostingHdModel stkPostHd = (clsExciseStkPostingHdModel) ob[0];
			clsExciseLicenceMasterModel licMaster = (clsExciseLicenceMasterModel) ob[1];
			objBean.setStrPSPCode(stkPostHd.getStrPSPCode());
			objBean.setDtePostingDate(objGlobal.funGetDate("yyyy/MM/dd", stkPostHd.getDtePostingDate()));
			objBean.setStrLicenceCode(stkPostHd.getStrLicenceCode());
			objBean.setStrLicenceNo(licMaster.getStrLicenceNo());
		}
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

}
