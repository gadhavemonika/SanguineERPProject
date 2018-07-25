package com.sanguine.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsPOSSalesDtlBean;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsPOSSalesDtlModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsStkAdjustmentDtlModel;
import com.sanguine.model.clsStkAdjustmentHdModel;
import com.sanguine.model.clsStkAdjustmentHdModel_ID;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsPOSLinkUpService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsStkAdjustmentService;

@Controller
public class clsPOSSalesDtlController {

	private static final Logger logger = LoggerFactory.getLogger(clsPOSSalesDtlController.class);

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsPOSLinkUpService objPOSLinkUpService;

	@Autowired
	private clsStkAdjustmentService objStkAdjService;

	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsLocationMasterService objLocationMasterService;

	String fromDate = "";
	String toDate = "";

	// Open POSSalesDtl
	@RequestMapping(value = "/frmPOSSalesDtl", method = RequestMethod.GET)
	public ModelAndView funOpenForm() {
		return new ModelAndView("frmPOSSalesDtl", "command", new clsPOSSalesDtlBean());
	}

	String posCode = "";

	// Load Master Data On Form
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/loadPOSLinkUpData", method = RequestMethod.GET)
	public @ResponseBody List funLoadMasterData(HttpServletRequest request) {
		List list = new ArrayList();
		try {
			objGlobal = new clsGlobalFunctions();
			String clientCode = request.getSession().getAttribute("clientCode").toString();
			// String
			// userCode=request.getSession().getAttribute("usercode").toString();

			String param = request.getParameter("param").toString();
			String[] spParam = param.split(",");
			String locCode = spParam[0];
			fromDate = spParam[1];
			toDate = spParam[2];

			fromDate = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];
			toDate = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];

			// String
			// sql="insert into tblposlinkup (strPOSItemCode,strPOSItemName,strClientCode) "
			// +
			// "(select strPOSItemCode,strPOSItemName,strClientCode from tblpossalesdtl "
			// + "where strClientCode='"+clientCode+"' "
			// +
			// "and strPOSItemCode not in (select strPOSItemCode from tblposlinkup))";
			// // group by strPOSItemCode

			String sql = "insert into tblposlinkup (strPOSItemCode,strPOSItemName,strClientCode) " + "(select DISTINCT strPOSItemCode,strPOSItemName,strClientCode from tblpossalesdtl " + "where strClientCode='" + clientCode + "' " + "and strPOSItemCode not in (select strPOSItemCode from tblposlinkup))"; // group
																																																																													// by
																																																																													// strPOSItemCode
			objPOSLinkUpService.funExecute(sql);

			String updateSql = "update tblpossalesdtl a set " + " strWSItemCode=ifnull((select strWSItemCode from tblposlinkup b " + " where a.strPOSItemCode=b.strPOSItemCode and strClientCode=b.strClientCode),'') " + " where strWSItemCode='' and strClientCode='" + clientCode + "' ";
			objPOSLinkUpService.funExecute(updateSql);

			sql = "select strExternalCode from tbllocationmaster where strLocCode='" + locCode + "' and strClientCode='" + clientCode + "'";
			List list1 = objGlobalFunctionsService.funGetList(sql, "sql");
			if (list1.size() > 0 && !list1.isEmpty() && !list1.get(0).toString().equals("")) {
				posCode = list1.get(0).toString();
				posCode = posCode.substring(8, posCode.length());
				/*
				 * sql="select a.strPOSItemCode,a.strPOSItemName" +
				 * ",ifnull((select strBOMCode from tblbommasterhd where strParentCode=b.strWSItemCode),ifnull(b.strWSItemCode,''))"
				 * +
				 * ",ifnull(b.strWSItemName,''),ifnull(a.strSACode,''),a.dblQuantity,a.dblRate,ifnull(c.strProdType,'') "
				 * +
				 * "from tblpossalesdtl a left outer join tblposlinkup b on a.strPOSItemCode=b.strPOSItemCode "
				 * +
				 * "left outer join tblproductmaster c on b.strWSItemCode=c.strProdCode "
				 * + "where a.strPOSCode='"+posCode+
				 * "' and date(a.dteBillDate) between '"
				 * +fromDate+"' and '"+toDate+"' " + "order by b.strWSItemCode";
				 */
				sql = "select a.strPOSItemCode, a.strPOSItemName, ifnull(d.strBOMCode,ifnull(b.strWSItemCode,'')) as strWSItemCode, " + " ifnull(b.strWSItemName,'') as strWSItemName,ifnull(a.strSACode,'') as strSACode,sum(a.dblQuantity),a.dblRate, ifnull(c.strProdType,'') as strProdType ,a.strPOSCode,a.dteBillDate " + " from tblpossalesdtl a "
						+ " left outer join tblposlinkup b on a.strPOSItemCode=b.strPOSItemCode " + " left outer join tblproductmaster c on b.strWSItemCode=c.strProdCode " + " left outer join tblbommasterhd d on b.strWSItemCode = d.strParentCode " + " where a.strPOSCode='" + posCode + "' and date(a.dteBillDate) between '" + fromDate + "' and '" + toDate + "' "
						+ " group by a.strPOSItemCode order by b.strWSItemCode ";
				list = objGlobalFunctionsService.funGetList(sql, "sql");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		return list;
	}

	// Save or Update POSSalesDtl
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/savePOSSalesDtl", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSSalesDtlBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {

			boolean flgSACode = false;
			objGlobal = new clsGlobalFunctions();
			double dblTotalAmt = 0.00;
			Map<String, String> mapSACode = new HashMap<String, String>();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			String startDate = req.getSession().getAttribute("startDate").toString();
			clsStkAdjustmentHdModel objHdModel = null;
			String locCode = objBean.getStrLocCode();
			long lastNo = 0;

			String[] arrPOSTSADate = objBean.getDtePostSADate().split("-");

			String postSADate = arrPOSTSADate[2] + "-" + arrPOSTSADate[1] + "-" + arrPOSTSADate[0];

			List<clsPOSSalesDtlModel> lisPOSSalesDtl = objBean.getListPOSSalesDtl();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);

			if (null != lisPOSSalesDtl && lisPOSSalesDtl.size() > 0) {
				// String saCode=objHdModel.getStrSACode();
				// objStkAdjService.funDeleteDtl(saCode,clientCode);
				// boolean flagDtlDataInserted=false;
				for (clsPOSSalesDtlModel ob : lisPOSSalesDtl) {
					clsStkAdjustmentDtlModel objStkAdjDtl = new clsStkAdjustmentDtlModel();
					if (null != ob.getStrWSItemCode() && !(ob.getStrWSItemCode().isEmpty()) && ob.getStrSACode() == "" || ob.getStrSACode() == null) {
						lastNo = objGlobalFunctionsService.funGetLastNo("tblstockadjustmenthd", "SACode", "intId", clientCode);

						String strStkCode = objGlobalFunctions.funGenerateDocumentCode("frmStockAdjustment", objBean.getDtePostSADate(), req);
						objHdModel = new clsStkAdjustmentHdModel(new clsStkAdjustmentHdModel_ID(strStkCode, clientCode));
						objHdModel.setStrUserModified(userCode);
						objHdModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

					} else if (null != ob.getStrWSItemCode() && !(ob.getStrWSItemCode().isEmpty())) {

						objHdModel = objStkAdjService.funGetDataOfModel(ob.getStrSACode(), clientCode);
						System.out.println("SACode=" + ob.getStrSACode());
						System.out.println("HDmodel=" + objHdModel);
						objHdModel.setStrUserModified(userCode);
						objHdModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
						objHdModel.setStrLocCode(locCode);
					}

					if (null != ob.getStrWSItemCode() && !(ob.getStrWSItemCode().isEmpty())) {
						if (ob.getStrWSItemCode().trim().length() > 0) {

							String saCode = objHdModel.getStrSACode();
							boolean flagDtlDataInserted = false;
							if (null != ob.getStrWSItemCode() && !(ob.getStrWSItemCode().isEmpty()) && ob.getStrSACode().trim().length() == 0) {
								if (ob.getStrWSItemCode().trim().length() > 0) {
									if (ob.getStrProdType().equals("Produced")) {
										/*
										 * String sql_ProducedItems=
										 * "select a.strChildCode,a.dblQty,b.dblCostRM "
										 * +
										 * "from tblbommasterdtl a,tblproductmaster b "
										 * +
										 * "where a.strChildCode=b.strProdCode and a.strBOMCode='"
										 * +ob.getStrWSItemCode()+"' " +
										 * "and a.strClientCode='"
										 * +clientCode+"'";
										 */

										String sql_ProducedItems = "";

										sql_ProducedItems = "select a.strChildCode,a.dblQty,b.dblCostRM,c.strBOMCode,c.strParentCode" + " from tblbommasterdtl a,tblproductmaster b ,tblbommasterhd c " + " where a.strChildCode=b.strProdCode and a.strBOMCode=c.strBOMCode " + " and (c.strParentCode='" + ob.getStrWSItemCode() + "' or c.strBOMCode='" + ob.getStrWSItemCode() + "') and a.strClientCode='"
												+ clientCode + "' ";
										if (ob.getStrWSItemCode().equals("P0000858")) {
											System.out.println(sql_ProducedItems);
										}
										List listChildItems = objGlobalFunctionsService.funGetList(sql_ProducedItems, "sql");
										if (listChildItems.size() > 0) {
											for (int cnt = 0; cnt < listChildItems.size(); cnt++) {
												objStkAdjDtl = new clsStkAdjustmentDtlModel();
												Object[] objChildItems = (Object[]) listChildItems.get(cnt);
												double conversionRatio = 1;
												String sql_Conversion = "select dblReceiveConversion,dblIssueConversion,dblRecipeConversion, " + " strReceivedUOM,strIssueUOM,strRecipeUOM " + " from tblproductmaster where strProdCode='" + objChildItems[0].toString() + "' " + " and strClientCode='" + clientCode + "' ";
												List listItems = objGlobalFunctionsService.funGetList(sql_Conversion, "sql");
												String strReceivedUOM = "";
												System.out.println("Child=" + objChildItems[0].toString());
												@SuppressWarnings("unused")
												String strIssueUOM = "";
												String strRecipeUOM = "";
												BigDecimal recipe = new BigDecimal(0.00);
												for (int cnt1 = 0; cnt1 < listItems.size(); cnt1++) {
													Object[] arrObjConvItems = (Object[]) listItems.get(cnt1);
													BigDecimal issue = (BigDecimal) arrObjConvItems[1];
													recipe = (BigDecimal) arrObjConvItems[2];
													conversionRatio = 1 / issue.doubleValue() / recipe.doubleValue();

													// if(objChildItems[0].toString().equals("P0000058"))
													// {
													// System.out.println("Recipe Conv="+recipe);
													// System.out.println("Conv Ratio="+conversionRatio);
													// }
													strReceivedUOM = arrObjConvItems[3].toString();
													strIssueUOM = arrObjConvItems[4].toString();
													strRecipeUOM = arrObjConvItems[5].toString();
												}
												double childQty = Double.parseDouble(objChildItems[1].toString());
												double rate = Double.parseDouble(objChildItems[2].toString());

												Double qty = ob.getDblQuantity() * childQty;
												qty = qty * conversionRatio;
												// if(objChildItems[0].toString().equals("P0000058"))
												// {
												// System.out.println("QTY="+qty);
												// }
												objStkAdjDtl.setStrSACode(saCode);
												objStkAdjDtl.setStrProdCode(objChildItems[0].toString());
												objStkAdjDtl.setDblQty(qty);
												objStkAdjDtl.setStrType("Out");
												objStkAdjDtl.setDblPrice(qty * rate);
												dblTotalAmt = dblTotalAmt + (qty * rate);
												objStkAdjDtl.setStrProdChar(" ");
												objStkAdjDtl.setStrClientCode(clientCode);
												objStkAdjDtl.setStrRemark("BOM Code:" + objChildItems[3].toString() + "," + "Parent Code:" + objChildItems[4].toString());
												objStkAdjDtl.setDblRate(rate);
												objStkAdjDtl.setStrWSLinkedProdCode(objChildItems[4].toString());
												String tempDisQty[] = qty.toString().split("\\.");
												String Displyqty = tempDisQty[0] + " " + strReceivedUOM + "." + Math.round(Float.parseFloat("0." + tempDisQty[1]) * (recipe.floatValue())) + " " + strRecipeUOM;
												objStkAdjDtl.setStrDisplayQty(Displyqty);
												objStkAdjService.funAddUpdateDtl(objStkAdjDtl);
												mapSACode.put(ob.getStrPOSItemCode() + "," + posCode, saCode);
											}
											flgSACode = true;
										} else {
											objStkAdjDtl = new clsStkAdjustmentDtlModel();
											objStkAdjDtl.setStrSACode(saCode);
											objStkAdjDtl.setStrProdCode(ob.getStrWSItemCode());
											objStkAdjDtl.setDblQty(ob.getDblQuantity());
											objStkAdjDtl.setStrType("Out");
											objStkAdjDtl.setDblPrice(ob.getDblQuantity() * ob.getDblRate());
											dblTotalAmt = dblTotalAmt + (ob.getDblQuantity() * ob.getDblRate());
											objStkAdjDtl.setStrProdChar(" ");
											objStkAdjDtl.setStrClientCode(clientCode);
											objStkAdjDtl.setStrRemark("BOM Code:" + ob.getStrWSItemCode() + ":" + "Parent Code:" + ob.getStrWSItemCode());

											Double qty = ob.getDblQuantity();
											String sql_Conversion = "select dblReceiveConversion,dblIssueConversion,dblRecipeConversion, " + " strReceivedUOM,strIssueUOM,strRecipeUOM " + " from tblproductmaster where strProdCode='" + ob.getStrWSItemCode().toString() + "'";
											List listItems = objGlobalFunctionsService.funGetList(sql_Conversion, "sql");
											String strReceivedUOM = "";
											String strIssueUOM = "";
											String strRecipeUOM = "";
											BigDecimal recipe = new BigDecimal(0.00);
											double conversionRatio = 1;
											for (int cnt1 = 0; cnt1 < listItems.size(); cnt1++) {
												Object[] arrObjConvItems = (Object[]) listItems.get(cnt1);
												BigDecimal issue = (BigDecimal) arrObjConvItems[1];
												recipe = (BigDecimal) arrObjConvItems[2];
												conversionRatio = 1 / issue.doubleValue() / recipe.doubleValue();
												strReceivedUOM = arrObjConvItems[3].toString();
												strIssueUOM = arrObjConvItems[4].toString();
												strRecipeUOM = arrObjConvItems[5].toString();
											}
											String tempDisQty[] = qty.toString().split("\\.");
											String Displyqty = tempDisQty[0] + " " + strReceivedUOM + "." + Math.round(Float.parseFloat("0." + tempDisQty[1]) * (recipe.floatValue())) + " " + strRecipeUOM;

											objStkAdjDtl.setStrDisplayQty(Displyqty);
											objStkAdjService.funAddUpdateDtl(objStkAdjDtl);
											mapSACode.put(ob.getStrPOSItemCode() + "," + posCode, saCode);
											flgSACode = true;
										}

									} else {
										objStkAdjDtl = new clsStkAdjustmentDtlModel();
										objStkAdjDtl.setStrSACode(saCode);
										objStkAdjDtl.setStrProdCode(ob.getStrWSItemCode());
										objStkAdjDtl.setDblQty(ob.getDblQuantity());
										objStkAdjDtl.setStrType("Out");
										objStkAdjDtl.setDblPrice(ob.getDblQuantity() * ob.getDblRate());
										dblTotalAmt = dblTotalAmt + (ob.getDblQuantity() * ob.getDblRate());
										objStkAdjDtl.setStrProdChar(" ");
										objStkAdjDtl.setStrClientCode(clientCode);
										objStkAdjDtl.setStrWSLinkedProdCode(ob.getStrWSItemCode());
										objStkAdjDtl.setStrRemark("BOM Code:" + ob.getStrWSItemCode() + ":" + "Parent Code:" + ob.getStrWSItemCode());

										Double qty = ob.getDblQuantity();
										String sql_Conversion = "select dblReceiveConversion,dblIssueConversion,dblRecipeConversion, " + " strReceivedUOM,strIssueUOM,strRecipeUOM " + " from tblproductmaster where strProdCode='" + ob.getStrWSItemCode().toString() + "'";
										List listItems = objGlobalFunctionsService.funGetList(sql_Conversion, "sql");
										String strReceivedUOM = "";
										String strIssueUOM = "";
										String strRecipeUOM = "";
										BigDecimal recipe = new BigDecimal(0.00);
										double conversionRatio = 1;
										for (int cnt1 = 0; cnt1 < listItems.size(); cnt1++) {
											Object[] arrObjConvItems = (Object[]) listItems.get(cnt1);
											BigDecimal issue = (BigDecimal) arrObjConvItems[1];
											recipe = (BigDecimal) arrObjConvItems[2];
											conversionRatio = 1 / issue.doubleValue() / recipe.doubleValue();
											strReceivedUOM = arrObjConvItems[3].toString();
											strIssueUOM = arrObjConvItems[4].toString();
											strRecipeUOM = arrObjConvItems[5].toString();
										}
										String tempDisQty[] = qty.toString().split("\\.");
										String Displyqty = tempDisQty[0] + " " + strReceivedUOM + "." + Math.round(Float.parseFloat("0." + tempDisQty[1]) * (recipe.floatValue())) + " " + strRecipeUOM;
										objStkAdjDtl.setStrDisplayQty(Displyqty);

										// if(ob.getStrWSItemCode().equals("P0000058"))
										// {
										// System.out.println("Recipe="+recipe);
										// System.out.println("Conv="+conversionRatio);
										// }

										objStkAdjService.funAddUpdateDtl(objStkAdjDtl);

										mapSACode.put(ob.getStrPOSItemCode() + "," + posCode, saCode);
										flgSACode = true;
									}
									objHdModel.setIntId(lastNo);
									objHdModel.setStrUserCreated(userCode);
									objHdModel.setDtCreatedDate(objGlobal.funGetCurrentDate("yyyy-MM-dd"));
									objHdModel.setDtSADate(postSADate);
									objHdModel.setStrLocCode(locCode); // Location
																		// Code
									clsLocationMasterModel locationModel = objLocationMasterService.funGetObject(locCode, clientCode);
									objHdModel.setStrAuthorise("Yes");
									objHdModel.setStrNarration("POS Sale Data Posting Manual - Dated: '" + objHdModel.getDtSADate() + "' " + "POS Name:" + locationModel.getStrLocName());
									objHdModel.setStrUserModified(userCode);
									objHdModel.setDtLastModified(objGlobal.funGetCurrentDate("yyyy-MM-dd"));
									objHdModel.setStrConversionUOM("RecipeUOM");
									objHdModel.setStrReasonCode(objSetup.getStrStkAdjReason());

									flagDtlDataInserted = true;
								}
							}

						}

					}

				}
				if (flgSACode) {
					objHdModel.setDblTotalAmt(dblTotalAmt);
					objStkAdjService.funAddUpdate(objHdModel);

				}

				for (Map.Entry<String, String> entry : mapSACode.entrySet()) {
					String[] key = entry.getKey().split(",");
					String itemCode = key[0];
					String POSCode = key[1];

					String updateSACode = "update tblpossalesdtl set strSACode='" + entry.getValue() + "' " + "where strPOSItemCode='" + itemCode + "' and strPOSCode='" + POSCode + "' and strClientCode='" + clientCode + "' " + " and date(dteBillDate) between '" + fromDate + "' and '" + toDate + "'   ";
					objPOSLinkUpService.funExecute(updateSACode);
				}
			}

			if (flgSACode) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "SA Code : ".concat(objHdModel.getStrSACode()));
				req.getSession().setAttribute("rptSACode", objHdModel.getStrSACode());
			} else {
				req.getSession().setAttribute("fail", true);
			}
			return new ModelAndView("redirect:/frmPOSSalesDtl.html");
		} else {
			return new ModelAndView("frmPOSSalesDtl");
		}
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/checkPOSLinkedToLocation", method = RequestMethod.GET)
	public @ResponseBody String funCheckPOSLinkedToLocation(HttpServletRequest request) {
		String linkedPOSYN = "NotLinked";
		try {

			objGlobal = new clsGlobalFunctions();
			String clientCode = request.getSession().getAttribute("clientCode").toString();
			String locCode = request.getParameter("locCode").toString();

			String sql = "select strExternalCode from tbllocationmaster where strLocCode='" + locCode + "' and strClientCode='" + clientCode + "'";
			List list1 = objGlobalFunctionsService.funGetList(sql, "sql");
			if (list1.size() > 0 && !list1.isEmpty() && !list1.get(0).toString().equals("")) {
				String posCode = list1.get(0).toString();
				posCode = posCode.substring(8, posCode.length());
				linkedPOSYN = "Linked";
			} else {
				linkedPOSYN = "NotLinked";
			}
		} catch (Exception e) {
			linkedPOSYN = "NotLinked";
			e.printStackTrace();
			logger.error(e.toString());
		}
		return linkedPOSYN;
	}

}
