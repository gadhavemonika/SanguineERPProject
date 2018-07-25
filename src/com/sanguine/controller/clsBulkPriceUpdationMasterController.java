package com.sanguine.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsProductMasterBean;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsProductMasterModel_ID;
import com.sanguine.model.clsSubGroupMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsManufactureMasterService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsSubGroupMasterService;
import com.sanguine.service.clsUOMService;

@Controller
public class clsBulkPriceUpdationMasterController {

	@Autowired
	private clsSubGroupMasterService objSubGrpMasterService;

	@Autowired
	private clsProductMasterService objProductMasterService;
	@Autowired
	clsGlobalFunctions objGlobal;

	@Autowired
	private clsLocationMasterService objLocationMasterService;

	@Autowired
	private clsUOMService objclsUOMService;

	@Autowired
	private clsManufactureMasterService objManufactureMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@RequestMapping(value = "/frmBulkProductUpdate", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		Map<String, String> mpSubgroup = objSubGrpMasterService.funGetSubgroupsCombobox(clientCode);
		model.put("mpsubGroup", mpSubgroup);

		Map<String, String> mpManufacturer = objManufactureMasterService.funGetManufacturerComboBox(clientCode);
		model.put("manufacturerList", mpManufacturer);

		List<String> listTaxIndicator = new ArrayList<>();
		listTaxIndicator.add(" ");
		String[] alphabetSet = objGlobal.funGetAlphabetSet();
		for (int i = 0; i < alphabetSet.length; i++) {
			listTaxIndicator.add(alphabetSet[i]);
		}
		model.put("taxIndicatorList", listTaxIndicator);
		List<String> uomList = new ArrayList<String>();
		uomList = objclsUOMService.funGetUOMList(clientCode);
		model.put("uomList", uomList);

		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmBulkProductUpdate_1", "command", new clsProductMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmBulkProductUpdate", "command", new clsProductMasterBean());
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/updateBulkProduct", method = RequestMethod.POST)
	public ModelAndView funUpdateBulkProduct(@ModelAttribute("command") clsProductMasterBean objBean, Map<String, Object> model, HttpServletRequest request) {
		String message = "";
		String urlHits = "1";
		String user = request.getSession().getAttribute("usercode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		Map<String, String> mpSubgroup = objSubGrpMasterService.funGetSubgroupsCombobox(clientCode);
		model.put("mpsubGroup", mpSubgroup);
		model.put("urlHits", urlHits);

		if (objBean.getListProdModel().size() > 0)
			for (clsProductMasterModel obModel : objBean.getListProdModel()) {

				if (obModel.getStrProdCode() != null) {
					clsProductMasterModel objModel1 = objProductMasterService.funGetObject(obModel.getStrProdCode(), clientCode);

					objModel1.setStrSGCode(obModel.getStrSGCode());
					clsSubGroupMasterModel objSubGroup = objSubGrpMasterService.funGetObject(obModel.getStrSGCode(), clientCode);
					objModel1.setStrSGName(objSubGroup.getStrSGName());
					objModel1.setDblCostRM(obModel.getDblCostRM());
					if (obModel.getStrLocCode() != null && !(obModel.getStrLocCode().equals(""))) {
						objModel1.setStrLocCode(obModel.getStrLocCode());
						clsLocationMasterModel objLocCode = objLocationMasterService.funGetObject(obModel.getStrLocCode(), clientCode);
						objModel1.setStrLocName(objLocCode.getStrLocName());
					}
					objModel1.setDblWeight(obModel.getDblWeight());
					objModel1.setDblMRP(obModel.getDblMRP());
					objModel1.setStrProdType(obModel.getStrProdType());
					objModel1.setStrTaxIndicator(obModel.getStrTaxIndicator());

					objModel1.setStrNonStockableItem(objGlobal.funIfNull(obModel.getStrNonStockableItem(), "N", "Y"));
					objModel1.setDblYieldPer(obModel.getDblYieldPer());
					objModel1.setStrExciseable(objGlobal.funIfNull(obModel.getStrExciseable(), "N", "Y"));
					objModel1.setStrNotInUse(objGlobal.funIfNull(obModel.getStrNotInUse(), "N", "Y"));
					objModel1.setStrPickMRPForTaxCal(objGlobal.funIfNull(obModel.getStrPickMRPForTaxCal(), "N", "Y"));
					objModel1.setStrBarCode(obModel.getStrBarCode());
					objModel1.setDblReceiveConversion(obModel.getDblReceiveConversion());
					objModel1.setStrReceivedUOM(obModel.getStrReceivedUOM());
					objModel1.setStrIssueUOM(obModel.getStrIssueUOM());
					objModel1.setDblIssueConversion(obModel.getDblIssueConversion());
					objModel1.setStrRecipeUOM(obModel.getStrRecipeUOM());
					objModel1.setDblRecipeConversion(obModel.getDblRecipeConversion());
					objModel1.setStrUserModified(user);
					objModel1.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

					if (objModel1.getStrProductImage() == null) {
						objModel1.setStrProductImage(funBlankBlob());
					}
					objProductMasterService.funAddUpdateGeneral(objModel1);
				}

			}
		request.getSession().setAttribute("success", true);
		request.getSession().setAttribute("successMessage", "Update SucessFully ");

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmBulkProductUpdate_1", "command", new clsProductMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmBulkProductUpdate", "command", new clsProductMasterBean());
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/loadUOMList", method = RequestMethod.GET)
	public @ResponseBody List funLoadUOMList(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List<String> uomList = new ArrayList<String>();
		uomList = objclsUOMService.funGetUOMList(clientCode);
		return uomList;
	}

	@RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
	public ModelAndView funSaveProduct(@ModelAttribute("command") clsProductMasterBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) throws IOException

	{
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String userCode = request.getSession().getAttribute("usercode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		if (!result.hasErrors()) {
			clsProductMasterModel objModel = funPrepareModelGeneral(objBean, userCode, clientCode);
			objProductMasterService.funAddUpdateGeneral(objModel);
			request.getSession().setAttribute("success", true);
			request.getSession().setAttribute("successMessage", "Product Code : ".concat(objModel.getStrProdCode()));
			return new ModelAndView("redirect:/frmBulkProductUpdate.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmBulkProductUpdate?saddr=" + urlHits, "command", new clsProductMasterBean());
		}

	}

	private clsProductMasterModel funPrepareModelGeneral(clsProductMasterBean objBean, String userCode, String clientCode) throws IOException {
		long lastNo = 0;
		clsProductMasterModel objModel;

		lastNo = objGlobalFunctionsService.funGetLastNo("tblproductmaster", "ProductMaster", "intId", clientCode);
		String productCode = "P" + String.format("%07d", lastNo);
		objModel = new clsProductMasterModel(new clsProductMasterModel_ID(productCode, clientCode));
		objModel.setIntId(lastNo);
		objModel.setStrUserCreated(userCode);
		objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrProductImage(funBlankBlob());
		objModel.setStrProdName(objBean.getStrProdName().toUpperCase());
		objModel.setStrPartNo(objGlobal.funIfNull(objBean.getStrPartNo(), "", objBean.getStrPartNo()));
		objModel.setStrUOM(objGlobal.funIfNull(objBean.getStrUOM(), "", objBean.getStrUOM()));
		objModel.setStrSGCode(objGlobal.funIfNull(objBean.getStrSGCode(), "", objBean.getStrSGCode()));
		objModel.setStrProdType(objGlobal.funIfNull(objBean.getStrProdType(), "", objBean.getStrProdType()));
		objModel.setDblCostRM(objBean.getDblCostRM());
		objModel.setDblCostManu(0.0);
		objModel.setStrLocCode("");
		objModel.setDblOrduptoLvl(0.0);
		objModel.setDblReorderLvl(0.0);
		objModel.setStrNotInUse("N");
		objModel.setStrExpDate(objGlobal.funIfNull(objBean.getStrExpDate(), "N", "Y"));
		objModel.setStrLotNo(objGlobal.funIfNull(objBean.getStrRevLevel(), "N", "Y"));
		objModel.setStrRevLevel(objGlobal.funIfNull(objBean.getStrRevLevel(), "N", "Y"));
		objModel.setStrSlNo(objGlobal.funIfNull(objBean.getStrSlNo(), "N", "Y"));
		objModel.setStrForSale(objGlobal.funIfNull(objBean.getStrForSale(), "N", "Y"));
		objModel.setStrSaleNo(objGlobal.funIfNull(objBean.getStrSaleNo(), "", objBean.getStrSaleNo()));
		objModel.setStrDesc(objGlobal.funIfNull(objBean.getStrDesc(), "", objBean.getStrDesc()));
		objModel.setDblUnitPrice(objBean.getDblUnitPrice());
		objModel.setStrTaxIndicator(objGlobal.funIfNull(objBean.getStrTaxIndicator(), "", objBean.getStrTaxIndicator()));
		objModel.setStrExceedPO(objGlobal.funIfNull(objBean.getStrExceedPO(), "N", "Y"));
		objModel.setStrStagDel(objGlobal.funIfNull(objBean.getStrStagDel(), "N", "Y"));
		objModel.setIntDelPeriod(0);
		objModel.setStrType(objGlobal.funIfNull(objBean.getStrType(), "", objBean.getStrType()));
		objModel.setStrSpecification(objGlobal.funIfNull(objBean.getStrSpecification(), "", objBean.getStrSpecification()));
		objModel.setDblWeight(objBean.getDblWeight());
		objModel.setStrBomCal(objGlobal.funIfNull(objBean.getStrBomCal(), "", objBean.getStrBomCal()));
		objModel.setStrWtUOM(objGlobal.funIfNull(objBean.getStrWtUOM(), "", objBean.getStrWtUOM()));
		objModel.setStrCalAmtOn(objGlobal.funIfNull(objBean.getStrCalAmtOn(), "", objBean.getStrCalAmtOn()));
		objModel.setStrClass(objGlobal.funIfNull(objBean.getStrClass(), "", objBean.getStrClass()));
		objModel.setStrUserModified(userCode);
		objModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setDblBatchQty(0.0);
		objModel.setDblMaxLvl(0.0);
		objModel.setStrBinNo(objGlobal.funIfNull(objBean.getStrBinNo(), "", objBean.getStrBinNo()));
		objModel.setStrTariffNo(objGlobal.funIfNull(objBean.getStrTariffNo(), "", objBean.getStrTariffNo()));
		objModel.setDblListPrice(0.0);
		objModel.setStrRemark(objGlobal.funIfNull(objBean.getStrRemark(), "", objBean.getStrRemark()));
		objModel.setStrIssueUOM(objGlobal.funIfNull(objBean.getStrIssueUOM(), "", objBean.getStrIssueUOM()));
		objModel.setStrReceivedUOM(objGlobal.funIfNull(objBean.getStrReceivedUOM(), "", objBean.getStrReceivedUOM()));
		objModel.setStrRecipeUOM(objGlobal.funIfNull(objBean.getStrRecipeUOM(), "", objBean.getStrRecipeUOM()));
		objModel.setDblIssueConversion(0.0);
		objModel.setDblReceiveConversion(0.0);
		objModel.setDblRecipeConversion(0.0);
		objModel.setStrSpecification("");
		objModel.setStrNonStockableItem(objGlobal.funIfNull(objBean.getStrNonStockableItem(), "N", "Y"));
		objModel.setStrPickMRPForTaxCal(objGlobal.funIfNull(objBean.getStrPickMRPForTaxCal(), "N", "Y"));

		double yieldper = 100.00;
		objBean.setDblYieldPer(yieldper);

		objModel.setStrBarCode("");
		objModel.setDblMRP((objBean.getDblMRP()));
		objModel.setStrExciseable("");

		return objModel;
	}

	private Blob funBlankBlob() {
		Blob blob = new Blob() {

			@Override
			public void truncate(long len) throws SQLException {
				// TODO Auto-generated method stub

			}

			@Override
			public int setBytes(long pos, byte[] bytes, int offset, int len) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int setBytes(long pos, byte[] bytes) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public OutputStream setBinaryStream(long pos) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long position(Blob pattern, long start) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long position(byte[] pattern, long start) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long length() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public byte[] getBytes(long pos, int length) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public InputStream getBinaryStream(long pos, long length) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public InputStream getBinaryStream() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void free() throws SQLException {
				// TODO Auto-generated method stub

			}
		};
		return blob;
	}

}
