package com.sanguine.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsNonStkProductBean;
import com.sanguine.model.clsGRNHdModel;
import com.sanguine.model.clsMISDtlModel;
import com.sanguine.model.clsMISHdModel;
import com.sanguine.model.clsMISHdModel_ID;
import com.sanguine.service.clsGRNService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsMISService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.util.clsNonStkProductModel;
import com.sanguine.util.clsReportBean;

@Controller
public class clsNonStkMISController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGRNService objGRNService;
	@Autowired
	private clsMISService objMISService;
	clsGlobalFunctions objGlobal = null;
	@Autowired
	private clsProductMasterService objProductMasterService;

	@SuppressWarnings({ "rawtypes", })
	@RequestMapping(value = "/frmNonStkMIS", method = RequestMethod.GET)
	public ModelAndView funOpenNonStkMIS(@ModelAttribute("setUpAttribute") clsNonStkProductBean objBean, HttpServletRequest req, Model model) {
		objGlobal = new clsGlobalFunctions();
		String strGrnCode = req.getParameter("GRNCode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String PoCode = "";
		String strFlow = "Direct";
		int count = 0;

		List listGRNHd = objGRNService.funGetObject(strGrnCode, clientCode);
		if (listGRNHd.size() > 0) {
			Object[] obj = (Object[]) listGRNHd.get(0);
			clsGRNHdModel tempGRNHd = (clsGRNHdModel) obj[0];
			if (!tempGRNHd.getStrPONo().equals("") && null != tempGRNHd.getStrPONo()) {
				PoCode = tempGRNHd.getStrPONo();
				String strPoCodes[] = PoCode.split(",");
				for (int j = 0; j < strPoCodes.length; j++) {
					String sql = "select IfNULL(b.strReqCode,'') from tblpurchaseorderdtl a " + " inner join tblmrpidtl b on a.strPIcode =b.strPIcode" + " where a.strPOCode='" + strPoCodes[j] + "' group by b.strReqCode";
					List list = objGlobalFunctionsService.funGetList(sql, "sql");
					if (list.size() > 0) {
						count++;
					}
				}
				if (count > 0) {
					strFlow = "Against";
				} else {
					strFlow = "Direct";
				}
			}
			List<clsNonStkProductModel> NonStkData = new ArrayList<clsNonStkProductModel>();
			if (strFlow.equalsIgnoreCase("Against")) {
				String strPoCodes[] = PoCode.split(",");
				for (int j = 0; j < strPoCodes.length; j++) {
					List NonStkList = objGRNService.funGetNonStkData(strPoCodes[j], strGrnCode, clientCode);

					for (int i = 0; i < NonStkList.size(); i++) {
						Object[] ob = (Object[]) NonStkList.get(i);
						clsNonStkProductModel NonStkModel = new clsNonStkProductModel();
						NonStkModel.setStrReqCode(ob[0].toString());
						NonStkModel.setDtReqDate(ob[1].toString());
						NonStkModel.setStrProdCode(ob[2].toString());
						NonStkModel.setStrProdName(ob[3].toString());
						NonStkModel.setStrToLocName(ob[4].toString());
						NonStkModel.setStrToLocCode(ob[5].toString());
						NonStkModel.setStrFromLocName(ob[6].toString());
						NonStkModel.setStrFromLocCode(ob[7].toString());
						NonStkModel.setDblQty(Double.parseDouble(ob[8].toString()));
						NonStkModel.setStrRemarks(ob[9].toString());
						NonStkModel.setDblCostRM(Double.parseDouble(ob[10].toString()));
						NonStkModel.setDblPrice(Double.parseDouble(ob[11].toString()));
						NonStkModel.setDblSubTotal(Double.parseDouble(ob[12].toString()));
						NonStkModel.setDblGRNQty(Double.parseDouble(ob[13].toString()));
						NonStkData.add(NonStkModel);
					}

				}
				objBean.setStrNarration("Generated POCode:=" + PoCode + "," + "GRNCode:=" + strGrnCode);
				objBean.setStrGRNCode(strGrnCode);
				model.addAttribute("nonStkMISList", NonStkData);
				model.addAttribute("flow", strFlow);
				model.addAttribute("GRNCode", strGrnCode);
				req.getSession().setAttribute("NonStkMISCode", null);
			} else if (strFlow.equalsIgnoreCase("Direct")) {
				String strGRNCode = req.getParameter("GRNCode").toString();
				clientCode = req.getSession().getAttribute("clientCode").toString();
				String userCode = req.getSession().getAttribute("usercode").toString();
				// List
				// listTempDtl=objGRNService.funGetDtlList(strGRNCode,clientCode);

				String sql = "select '',b.strProdCode,d.strProdName,b.dblUnitPrice,sum(b.dblQty),a.strLocCode,c.strLocName " + " from tblgrnhd a ,tblgrndtl b ,tbllocationmaster c,tblproductmaster d " + " where a.strGRNCode=b.strGRNCode and a.strLocCode=c.strLocCode " + " and a.strGRNCode='" + strGRNCode + "' and d.strProdCode=b.strProdCode and d.strNonStockableItem='Y' "
						+ " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + " and d.strClientCode='" + clientCode + "' group by  b.strProdCode";
				List list = objGlobalFunctionsService.funGetList(sql, "sql");

				if (list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						Object[] ob = (Object[]) list.get(i);
						clsNonStkProductModel NonStkModel = new clsNonStkProductModel();
						NonStkModel.setStrReqCode(ob[0].toString());
						NonStkModel.setStrProdCode(ob[1].toString());
						NonStkModel.setStrProdName(ob[2].toString());
						NonStkModel.setDblCostRM(Double.valueOf(ob[3].toString()));
						NonStkModel.setDblGRNQty(Double.valueOf(ob[4].toString()));
						NonStkModel.setDblQty(Double.valueOf(ob[4].toString()));
						NonStkModel.setStrFromLocCode(ob[5].toString());
						NonStkModel.setStrFromLocName(ob[6].toString());
						NonStkModel.setStrToLocCode("");
						NonStkModel.setStrToLocName("");
						NonStkModel.setDblPrice(Double.valueOf(ob[3].toString()) * Double.valueOf(ob[4].toString()));
						NonStkModel.setStrRemarks("");
						NonStkData.add(NonStkModel);
					}
				}

				if (PoCode.length() > 0) {
					objBean.setStrNarration("Generated POCode:=" + PoCode + "," + "GRNCode:=" + strGrnCode);
				} else {
					objBean.setStrNarration("MIS Generated from GRN No: ".concat(strGRNCode).concat(" Dated: " + objGlobal.funGetCurrentDateTime("dd-MM-yyyy") + " by User: " + userCode + "  for Non Stockable Products"));
				}
				objBean.setStrGRNCode(strGRNCode);
				model.addAttribute("nonStkMISList", NonStkData);
				model.addAttribute("flow", strFlow);
				model.addAttribute("GRNCode", strGrnCode);
				req.getSession().setAttribute("GRNCode", strGRNCode);
			}

		}

		return new ModelAndView("frmNonStkMIS");
	}

	@RequestMapping(value = "/frmNonStkMIS1", method = RequestMethod.GET)
	public String funOpenNonStkMISBlank(@ModelAttribute("setUpAttribute") clsNonStkProductBean objBean, HttpServletRequest req, Model model) {
		List<clsNonStkProductModel> NonStkData = new ArrayList<clsNonStkProductModel>();
		objBean.setStrNarration("");
		model.addAttribute("nonStkMISList", NonStkData);
		return "frmNonStkMIS";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadGRNProductData", method = RequestMethod.GET)
	public @ResponseBody clsNonStkProductModel funGetGRNProdData(HttpServletRequest req, HttpServletResponse res) {
		String prodCode = req.getParameter("prodCode").toString();
		String GRNCode = "";
		if (req.getSession().getAttribute("GRNCode") != null) {
			GRNCode = req.getSession().getAttribute("GRNCode").toString();
		}
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsNonStkProductModel nonstkmodel = new clsNonStkProductModel();
		/*
		 * String sql=
		 * "from clsGRNDtlModel a ,clsGRNHdModel b,clsProductMasterModel c,clsLocationMasterModel d"
		 * +
		 * " where a.strGRNCode=b.strGRNCode and a.strProdCode=c.strProdCode and b.strLocCode=d.strLocCode and "
		 * + " a.strGRNCode='"+GRNCode+"' and a.strProdCode='"+prodCode+
		 * "' and a.strClientCode='"
		 * +clientCode+"' and b.strClientCode='"+clientCode+"'" +
		 * " and c.strClientCode='"
		 * +clientCode+"' and d.strClientCode='"+clientCode+"' ";
		 */
		String sql = "select a.strProdCode,c.strProdName,d.strLocCode,d.strLocName,sum(a.dblQty) as GRNQty, " + " a.dblUnitPrice from  tblgrndtl a ,tblgrnhd b,tblproductmaster c,tbllocationmaster d " + " where a.strGRNCode=b.strGRNCode and a.strProdCode=c.strProdCode and b.strLocCode=d.strLocCode " + " and  a.strGRNCode='" + GRNCode + "' and a.strProdCode='" + prodCode + "' and a.strClientCode='"
				+ clientCode + "' " + "  and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "' and d.strClientCode='" + clientCode + "'" + "  group by a.strProdCode";
		List list = objGlobalFunctionsService.funGetList(sql, "sql");
		for (int i = 0; i < list.size(); i++) {
			Object[] ob = (Object[]) list.get(i);
			/*
			 * clsGRNDtlModel grndtlModel=(clsGRNDtlModel)ob[0];
			 * clsProductMasterModel ProdModel=(clsProductMasterModel)ob[2];
			 * clsLocationMasterModel locModel=(clsLocationMasterModel)ob[3];
			 */

			nonstkmodel.setStrProdCode(ob[0].toString());
			nonstkmodel.setStrProdName(ob[1].toString());
			nonstkmodel.setStrToLocCode(ob[2].toString());
			nonstkmodel.setStrToLocName(ob[3].toString());
			nonstkmodel.setDblGRNQty(Double.valueOf(ob[4].toString()));
			nonstkmodel.setDblQty(Double.valueOf(ob[4].toString()));
			nonstkmodel.setDblCostRM(Double.valueOf(ob[5].toString()));
		}
		return nonstkmodel;
	}

	@RequestMapping(value = "/saveNonStlMIS", method = RequestMethod.POST)
	public String funOpenNonStkMIS(@ModelAttribute("setUpAttribute") @Valid clsNonStkProductBean objBean, BindingResult result, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		String strgrnCode = objBean.getStrGRNCode();
		String strGeneratedMISCode = "";
		if (!result.hasErrors()) {
			List<clsNonStkProductModel> temphdList = objBean.getListItems();
			clsNonStkProductModel firstObj = temphdList.get(0);
			if (firstObj.getStrReqCode().trim().length() != 0) {
				clsMISHdModel MISHdModel = funPrepareModelHd(objBean, userCode, clientCode, propCode, startDate);
				objMISService.funAddMISHd(MISHdModel);
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "MIS Code : ".concat(MISHdModel.getStrMISCode()));

				String SqlInsertgrnmisdtl = "insert into tblgrnmisdtl(strGRNCode,strMISCode,strClientCode)" + " value('" + strgrnCode + "','" + MISHdModel.getStrMISCode() + "','" + clientCode + "')";
				objMISService.funInsertNonStkItemDirect(SqlInsertgrnmisdtl);

			} else {
				String strMisCodes = "";
				List<clsNonStkProductModel> temphdList1 = objBean.getListItems();
				clsNonStkProductModel firstObj1 = temphdList.get(0);
				Set<String> setLocCode = new HashSet<>();
				// Map<String,String> map=new HashMap<String, String>();
				for (clsNonStkProductModel tempOb : temphdList1) {
					if (null != tempOb.getStrProdCode()) {
						setLocCode.add(tempOb.getStrToLocCode());
						// map.put(tempOb.getStrToLocCode(),
						// tempOb.getStrToLocName());
					}
				}
				for (String strLocCode : setLocCode) {
					long lastNo = 0;
					lastNo = objGlobalFunctionsService.funGetLastNo("tblmishd", "MaterailReq", "intid", clientCode);
					String year = objGlobal.funGetSplitedDate(startDate)[2];
					String cd = objGlobal.funGetTransactionCode("MI", propCode, year);
					String strMISCode = cd + String.format("%06d", lastNo);

					String dtMISDate = objGlobal.funGetCurrentDateTime("yyyy-MM-dd");
					String strAgainst = "Direct";
					String strReqCode = "";
					String strLocFrom = firstObj1.getStrFromLocCode();
					String strLocTo = strLocCode;
					String strAuthorise = "Yes";
					String strUserModified = userCode;

					String strNarration = objBean.getStrNarration();
					String strUserCreated = userCode;
					String dtCreatedDate = objGlobal.funGetCurrentDateTime("yyyy-MM-dd");
					String dtLastModified = objGlobal.funGetCurrentDateTime("yyyy-MM-dd");

					String Sql = "Insert into tblmishd(strMISCode,intid,dtMISDate,strAgainst,strReqCode,strLocFrom,strLocTo,strNarration,strAuthorise,dtCreatedDate,strUserModified,dtLastModified,strUserCreated,strClientCode)" + "value('" + strMISCode + "'," + lastNo + ",'" + dtMISDate + "','" + strAgainst + "','" + strReqCode + "','" + strLocFrom + "','" + strLocTo + "','" + strNarration + "','"
							+ strAuthorise + "','" + dtCreatedDate + "','" + strUserModified + "','" + dtLastModified + "','" + strUserCreated + "','" + clientCode + "')";
					// System.out.println(Sql);
					int res = objMISService.funInsertNonStkItemDirect(Sql);
					if (res > 0) {
						List<clsNonStkProductModel> List1 = objBean.getListItems();
						for (int i = 0; i < List1.size(); i++) {
							clsNonStkProductModel Obj1 = temphdList.get(i);
							if (Obj1.getStrToLocCode().equals(strLocCode)) {
								String subSql = "insert into tblmisdtl(strMISCode,strProdCode,dblQty,dblUnitPrice,dblTotalPrice,strRemarks,strReqCode,strClientCode)" + " value('" + strMISCode + "','" + Obj1.getStrProdCode() + "'," + Obj1.getDblQty() + "," + Obj1.getDblCostRM() + "," + Obj1.getDblPrice() + ",'" + "" + "','" + "" + "','" + clientCode + "')";
								// System.out.println(subSql);
								res = objMISService.funInsertNonStkItemDirect(subSql);
							}
						}
					}
					if (strMisCodes.length() > 0) {
						strMisCodes = strMisCodes + " or a.strmiscode='" + strMISCode + "' ";
					} else {
						strMisCodes = "a.strmiscode='" + strMISCode + "' ";

					}

					strGeneratedMISCode = strGeneratedMISCode + "," + strMISCode;

				}
				String grnCode = objBean.getStrGRNCode();
				String sql = "select b.strProdCode, c.strLocName " + " from tblmishd a, tblmisdtl b, tbllocationmaster c " + " where a.strMISCode = b.strMISCode and " + " (" + strMisCodes + ") " + " and a.strLocTo = c.strLocCode order by strProdCode ";
				@SuppressWarnings("rawtypes")
				List list = objGlobalFunctionsService.funGetList(sql, "sql");
				if (!list.isEmpty()) {
					for (int i = 0; i < list.size(); i++) {
						Object[] ob = (Object[]) list.get(i);
						String pordCode = ob[0].toString();
						String locName = ob[1].toString();
						String updateQuery = "update tblgrndtl set strRemarks = CONCAT(strRemarks, ' " + locName + "') " + "	where strgrnCode = '" + grnCode + "' and strprodcode = '" + pordCode + "' ";
						objMISService.funInsertNonStkItemDirect(updateQuery);
					}
				}
				String tempMISCode[] = strGeneratedMISCode.split(",");
				for (int i = 1; i < tempMISCode.length; i++) {
					String SqlInsertgrnmisdtl = "insert into tblgrnmisdtl(strGRNCode,strMISCode,strClientCode)" + " value('" + grnCode + "','" + tempMISCode[i] + "','" + clientCode + "')";
					objMISService.funInsertNonStkItemDirect(SqlInsertgrnmisdtl);
				}

				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "MIS Code : ".concat(strGeneratedMISCode));
			}

		}
		return ("redirect:/frmNonStkMIS1.html");
	}

	private clsMISHdModel funPrepareModelHd(clsNonStkProductBean objBean, String userCode, String clientCode, String propCode, String startDate) {
		long lastNo = 0;
		clsMISHdModel objMISHd = new clsMISHdModel();
		lastNo = objGlobalFunctionsService.funGetLastNo("tblmishd", "MaterailReq", "intid", clientCode);
		String year = objGlobal.funGetSplitedDate(startDate)[2];
		String cd = objGlobal.funGetTransactionCode("MI", propCode, year);
		String strMISCode = cd + String.format("%06d", lastNo);
		objMISHd = new clsMISHdModel(new clsMISHdModel_ID(strMISCode, clientCode));
		objMISHd.setIntid(lastNo);
		List<clsNonStkProductModel> temphdList = objBean.getListItems();
		if (!temphdList.isEmpty()) {
			clsNonStkProductModel firstObj = temphdList.get(0);
			objMISHd.setStrMISCode(strMISCode);
			objMISHd.setStrLocFrom(firstObj.getStrFromLocCode());
			objMISHd.setStrLocTo(firstObj.getStrToLocCode());
			if (firstObj.getStrReqCode().equals("")) {
				objMISHd.setStrAgainst("Direct");
			} else {
				objMISHd.setStrAgainst("Requisition");
			}
			objMISHd.setDtMISDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			objMISHd.setStrAuthorise("Yes");
			objMISHd.setStrUserModified(userCode);
			objMISHd.setStrReqCode(firstObj.getStrReqCode());
			objMISHd.setStrNarration(objBean.getStrNarration());
			objMISHd.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			objMISHd.setStrUserCreated(userCode);
			objMISHd.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		}

		List<clsNonStkProductModel> tempList = objBean.getListItems();
		List<clsMISDtlModel> listMisDtlModel = new ArrayList<clsMISDtlModel>();

		if (!tempList.isEmpty()) {
			for (clsNonStkProductModel objNonStock : tempList) {
				clsMISDtlModel tempDetailObj = new clsMISDtlModel();
				tempDetailObj.setStrReqCode(objNonStock.getStrReqCode());
				tempDetailObj.setStrProdCode(objNonStock.getStrProdCode());
				tempDetailObj.setDblQty(objNonStock.getDblQty());
				tempDetailObj.setStrRemarks("");
				tempDetailObj.setDblUnitPrice(objNonStock.getDblCostRM());
				tempDetailObj.setDblTotalPrice(objNonStock.getDblPrice());
				listMisDtlModel.add(tempDetailObj);
			}
		}
		objMISHd.setClsMISDtlModel(listMisDtlModel);
		return objMISHd;
	}

	@RequestMapping(value = "/frmPendingNonStkMIS", method = RequestMethod.GET)
	public ModelAndView funOpenPendingNonStkMIS() {
		return new ModelAndView("frmPendingNonStkMIS", "command", new clsReportBean());

	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/loadPendingNonStockable", method = RequestMethod.GET)
	private @ResponseBody List funLoadPendingNonStockable(@RequestParam(value = "locCode") String locCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String sql = "select a.strGRNCode,Date(a.dtGRNDate) as GRNDate,b.strPName as SupplierName,c.strLocName as LocationName, " + " a.strBillNo,a.strNarration,a.dblTaxAmt,a.dblTotal from tblgrnhd a,tblpartymaster b,tbllocationmaster c " + " where strgrncode IN (select strGRNCode from tblgrndtl a, tblproductmaster b where a.strProdCode = b.strProdCode "
				+ " and b.strNonStockableItem = 'Yes' and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "') " + " and strgrncode NOT IN (select strgrncode from tblgrnmisdtl where  strClientCode='" + clientCode + "') " + " and a.strClientCode='" + clientCode + "' and a.strSuppCode=b.strPCode and a.strLocCode=c.strLocCode and a.strLocCode='" + locCode + "'";

		List list = objGlobalFunctionsService.funGetList(sql, "sql");
		if (list == null) {
			list = new ArrayList();
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/CheckGRNCode", method = RequestMethod.GET)
	private @ResponseBody String funCheckGRNCode(@RequestParam(value = "GRNCode") String GRNCode, HttpServletRequest req) {
		String flag = "false";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String sql = "select strMISCode from tblgrnmisdtl where strGRNCode='" + GRNCode + "' and  strClientCode='" + clientCode + "'";
		List list = objGlobalFunctionsService.funGetList(sql, "sql");

		if (list != null && !list.isEmpty() && list.size() > 0) {
			String MISCode = list.get(0).toString();
			sql = "Select count(*) from tblmishd where strMISCode='" + MISCode + "' and  strClientCode='" + clientCode + "'";
			List MISlist = objGlobalFunctionsService.funGetList(sql, "sql");
			if (MISlist.size() > 0 && MISlist != null) {
				flag = "true";
			}
		}
		return flag;
		// return null;
	}
}
