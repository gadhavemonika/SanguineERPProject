package com.sanguine.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsDocumentRecoBean;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsDocumentRecoController {
	@Autowired
	clsGlobalFunctionsService objGlobalFunService;

	clsGlobalFunctions objGlobal;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/frmDocumentReconciliation", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") clsDocumentRecoBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest req) {
		Map<String, String> mapTransForms = new HashMap<String, String>();
		String sql = "select strFormName,strFormDesc from clsTreeMasterModel " + "where strType='T' and (strFormName='frmBillPassing' or strFormName='frmGRN' or strFormName='frmPurchaseOrder' or strFormName='frmPurchaseIndent' " + "or strFormName='frmMIS' or strFormName='frmMaterialReq') " + "order by strFormName";
		List list = objGlobalFunService.funGetList(sql, "hql");
		for (int cnt = 0; cnt < list.size(); cnt++) {
			Object[] arrObj = (Object[]) list.get(cnt);
			mapTransForms.put(arrObj[0].toString(), arrObj[1].toString());
		}
		ModelAndView objModelAndView = new ModelAndView();
		objModelAndView.addObject("listFormName", mapTransForms);
		return objModelAndView;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@RequestMapping(value = "/showDocReconc", method = RequestMethod.GET)
	public @ResponseBody Map<String, HashMap<String, HashMap<String, HashMap<String, String>>>> funShowDocReconciliation(@RequestParam(value = "param1") String param1, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		Map<String, String> mapTransForms = new HashMap<String, String>();

		String sql = "select strFormName,strFormDesc from clsTreeMasterModel " + "where strType='T' and (strFormName='frmBillPassing' or strFormName='frmGRN' or strFormName='frmPurchaseOrder' or strFormName='frmPurchaseIndent' " + "or strFormName='frmMIS' or strFormName='frmMaterialReq') " + "order by strFormName";

		List list = objGlobalFunService.funGetList(sql, "hql");
		for (int cnt = 0; cnt < list.size(); cnt++) {
			Object[] arrObj = (Object[]) list.get(cnt);
			mapTransForms.put(arrObj[0].toString(), arrObj[1].toString());
		}

		String[] spParam1 = param1.split(",");
		String formName = spParam1[0];
		String transCode = spParam1[1];
		String type = spParam1[2];

		Map mapNode1 = new HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>>();
		Map mapNode2 = new HashMap<String, HashMap<String, HashMap<String, String>>>();

		if (formName.equals("frmBillPassing") && type.equals("Backward")) {
			String sql_BillPass = "select b.strBillPassNo,a.strGRNCode,ifnull(c.strPName,''),date(b.dtBillDate),b.dblBillAmt " + "from tblbillpassdtl a,tblbillpasshd b " + "left outer join tblpartymaster c on b.strSuppCode=c.strPCode " + "where a.strBillPassNo=b.strBillPassNo and a.strBillPassNo='" + transCode + "' " + "and a.strClientCode='" + clientCode + "'";
			String billPassNo = transCode;
			List listBillPass = objGlobalFunService.funGetList(sql_BillPass, "sql");

			if (listBillPass.size() > 0) {
				for (int bpCnt = 0; bpCnt < listBillPass.size(); bpCnt++) {
					Object[] arrObjBillPass = (Object[]) listBillPass.get(bpCnt);
					String billPassDate = objGlobal.funGetDate("yyyy/MM/dd", arrObjBillPass[3].toString());
					billPassNo = arrObjBillPass[0].toString() + " " + billPassDate;
					if (arrObjBillPass[2].toString().trim().length() > 0) {
						double totalAmt = Double.parseDouble(arrObjBillPass[4].toString());
						totalAmt = Math.rint(totalAmt);
						billPassNo = arrObjBillPass[0].toString() + " " + billPassDate + " (" + arrObjBillPass[2].toString() + ") " + totalAmt + "#frmBillPassing";
					}

					String sql_GRN = " select b.strGRNCode,a.strPONo,ifnull(c.strPName,''),date(a.dtGRNDate),a.dblTotal " + "from tblgrnhd a, tblgrndtl b ,tblpartymaster c " + "where a.strGRNCode=b.strGRNCode and a.strSuppCode=c.strPCode and a.strAgainst='Purchase Order' " + "and a.strGRNCode='" + arrObjBillPass[1].toString() + "' and a.strClientCode='" + clientCode + "' group by a.strGRNCode ";
					List listGRN = objGlobalFunService.funGetList(sql_GRN, "sql");
					String grnCode = arrObjBillPass[1].toString();

					if (listGRN.size() > 0) {
						Map mapNode3 = new HashMap<String, HashMap<String, String>>();
						for (int cnt = 0; cnt < listGRN.size(); cnt++) {
							Object[] arrObjGRN = (Object[]) listGRN.get(cnt);
							String grnDate = objGlobal.funGetDate("yyyy/MM/dd", arrObjGRN[3].toString());
							grnCode = arrObjGRN[0].toString() + " " + grnDate;
							if (arrObjGRN[2].toString().trim().length() > 0) {
								double totalAmt = Double.parseDouble(arrObjGRN[4].toString());
								totalAmt = Math.rint(totalAmt);
								grnCode = arrObjGRN[0].toString() + " " + grnDate + " (" + arrObjGRN[2].toString() + ") " + totalAmt + "#frmGRN";
							}

							String sql_PO = "select a.strPOCode,a.strPICode, ifnull(c.strPName,''),date(b.dtPODate),b.dblFinalAmt " + "from tblpurchaseorderdtl a,tblpurchaseorderhd b " + "left outer join tblpartymaster c on b.strSuppCode=c.strPCode " + "where a.strPOCode=b.strPOCode and a.strPOCode='" + arrObjGRN[1].toString() + "' " + "and a.strClientCode='" + clientCode
									+ "' group by a.strPOCode ";

							List listPO = objGlobalFunService.funGetList(sql_PO, "sql");
							if (listPO.size() > 0) {
								for (int poCnt = 0; poCnt < listPO.size(); poCnt++) {
									Map mapNode4 = new HashMap<String, String>();
									Object[] arrObjPO = (Object[]) listPO.get(poCnt);

									String sql_PI = "select a.strPICode,ifnull(c.strLocName,''),date(b.dtPIDate) " + "from tblpurchaseindenddtl a,tblpurchaseindendhd b " + "left outer join tbllocationmaster c on b.strLocCode=c.strLocCode " + "where a.strPIcode=b.strPIcode and a.strPICode='" + arrObjPO[1].toString() + "'";

									List listPI = objGlobalFunService.funGetList(sql_PI, "sql");
									if (listPI.size() > 0) {
										for (int piCnt = 0; piCnt < listPI.size(); piCnt++) {
											Object[] objPICode = (Object[]) listPI.get(piCnt);
											String piDate = objGlobal.funGetDate("yyyy/MM/dd", objPICode[2].toString());
											String piCode = objPICode[0].toString() + " " + piDate;
											if (objPICode[1].toString().trim().length() > 0) {
												// double
												// totalAmt=Double.parseDouble(arrObjPO[4].toString());
												double totalAmt = 0;
												totalAmt = Math.rint(totalAmt);
												piCode = objPICode[0].toString() + " " + piDate + " (" + objPICode[1].toString() + ") " + totalAmt + "#frmPurchaseIndent";
											}
											mapNode4.put(piCode, objPICode[0].toString());
										}
									}

									String poDate = objGlobal.funGetDate("yyyy/MM/dd", arrObjPO[3].toString());
									String poCode = arrObjPO[0].toString() + " " + poDate;
									if (arrObjPO[2].toString().trim().length() > 0) {
										double totalAmt = Double.parseDouble(arrObjPO[4].toString());
										totalAmt = Math.rint(totalAmt);
										poCode = arrObjPO[0].toString() + " " + poDate + " (" + arrObjPO[2].toString() + ") " + totalAmt + "#frmPurchaseOrder";
									}
									mapNode3.put(poCode, mapNode4);
								}
							}
						}
						mapNode2.put(grnCode, mapNode3);
					}
				}

				mapNode1.put(billPassNo, mapNode2);
			}
		}

		if (formName.equals("frmGRN") && type.equalsIgnoreCase("Forward")) {
			String sql_BillPass = "select b.strBillPassNo,a.strGRNCode,ifnull(c.strPName,''),date(b.dtBillDate),b.dblBillAmt " + "from tblbillpassdtl a,tblbillpasshd b " + "left outer join tblpartymaster c on b.strSuppCode=c.strPCode " + "where a.strBillPassNo=b.strBillPassNo and a.strBillPassNo='" + transCode + "' " + "and a.strClientCode='" + clientCode + "'";
			String billPassNo = transCode;
			List listBillPass = objGlobalFunService.funGetList(sql_BillPass, "sql");
			Map mapNode3 = new HashMap<String, HashMap<String, String>>();

			if (listBillPass.size() > 0) {
				Map mapNode4 = new HashMap<String, String>();
				for (int bpCnt = 0; bpCnt < listBillPass.size(); bpCnt++) {
					mapNode4 = new HashMap<String, String>();
					Object[] arrObjBillPass = (Object[]) listBillPass.get(bpCnt);
					String billPassDate = objGlobal.funGetDate("yyyy/MM/dd", arrObjBillPass[3].toString());
					billPassNo = arrObjBillPass[0].toString() + " " + billPassDate;
					if (arrObjBillPass[2].toString().trim().length() > 0) {
						double totalAmt = Double.parseDouble(arrObjBillPass[4].toString());
						totalAmt = Math.rint(totalAmt);
						billPassNo = arrObjBillPass[0].toString() + " " + billPassDate + " (" + arrObjBillPass[2].toString() + ") " + totalAmt + "#frmBillPassing";
					}
					mapNode4.put(billPassNo, arrObjBillPass[1].toString());
				}
				mapNode3.put("Empty", mapNode4);
				mapNode2.put("Empty", mapNode3);
				mapNode1.put("Empty", mapNode2);
			}
		}

		if (formName.equals("frmGRN") && type.equalsIgnoreCase("Backward")) {
			/*
			 * String
			 * sql_GRN="select a.strGRNCode,b.strCode from tblgrnhd a, tblgrndtl b "
			 * +
			 * "where a.strGRNCode=b.strGRNCode and a.strAgainst='Purchase Order' "
			 * + "and a.strGRNCode='"+transCode+"'";
			 */

			String sql_GRN = "select b.strGRNCode,a.strCode,ifnull(c.strPName,''),date(b.dtGRNDate),b.dblTotal " + "from tblgrndtl a, tblgrnhd b " + "left outer join tblpartymaster c on b.strSuppCode=c.strPCode " + "where b.strGRNCode=b.strGRNCode and b.strAgainst='Purchase Order' " + "and b.strGRNCode='" + transCode + "' and b.strClientCode='" + clientCode + "'";

			List listGRN = objGlobalFunService.funGetList(sql_GRN, "sql");
			String grnCode = transCode;

			if (listGRN.size() > 0) {
				Map mapNode3 = new HashMap<String, HashMap<String, String>>();
				for (int cnt = 0; cnt < listGRN.size(); cnt++) {
					Object[] arrObjGRN = (Object[]) listGRN.get(cnt);
					String grnDate = objGlobal.funGetDate("yyyy/MM/dd", arrObjGRN[3].toString());
					grnCode = arrObjGRN[0].toString() + " " + grnDate;
					String strCode = arrObjGRN[1].toString().trim();
					if (arrObjGRN[2].toString().trim().length() > 0) {
						double grnAmt = Double.parseDouble(arrObjGRN[4].toString());
						grnAmt = Math.rint(grnAmt);
						grnCode = arrObjGRN[0].toString() + " " + grnDate + " (" + arrObjGRN[2].toString() + ") " + grnAmt + "#frmGRN";
					}

					// String
					// sql_PO="select strPOCode,strPICode from tblpurchaseorderdtl where strPOCode='"+arrObjGRN[1].toString()+"'";
					String sql_PO = "select a.strPOCode,a.strPICode, ifnull(c.strPName,''),date(b.dtPODate),b.dblFinalAmt " + "from tblpurchaseorderdtl a,tblpurchaseorderhd b " + "left outer join tblpartymaster c on b.strSuppCode=c.strPCode " + "where a.strPOCode=b.strPOCode and a.strPOCode='" + strCode + "' " + "and a.strClientCode='" + clientCode + "'";
					List listPO = objGlobalFunService.funGetList(sql_PO, "sql");
					if (listPO.size() > 0) {
						for (int poCnt = 0; poCnt < listPO.size(); poCnt++) {
							Map mapNode4 = new HashMap<String, String>();
							Object[] arrObjPO = (Object[]) listPO.get(poCnt);
							// String
							// sql_PI="select strPICode from tblpurchaseindenddtl where strPICode='"+arrObjPO[1].toString()+"'";

							String sql_PI = "select a.strPICode,ifnull(c.strLocName,''),date(b.dtPIDate) " + "from tblpurchaseindenddtl a,tblpurchaseindendhd b " + "left outer join tbllocationmaster c on b.strLocCode=c.strLocCode " + "where a.strPIcode=b.strPIcode and a.strPICode='" + arrObjPO[1].toString() + "'";

							List listPI = objGlobalFunService.funGetList(sql_PI, "sql");
							if (listPI.size() > 0) {
								for (int piCnt = 0; piCnt < listPI.size(); piCnt++) {
									Object[] objPICode = (Object[]) listPI.get(piCnt);
									String piDate = objGlobal.funGetDate("yyyy/MM/dd", objPICode[2].toString());
									String piCode = objPICode[0].toString() + " " + piDate;
									if (objPICode[1].toString().trim().length() > 0) {
										// double
										// totalAmt=Double.parseDouble(arrObjPO[3].toString());
										double totalAmt = 0;
										totalAmt = Math.rint(totalAmt);
										piCode = objPICode[0].toString() + " " + piDate + " (" + objPICode[1].toString() + ") " + totalAmt + "#frmPurchaseIndent";
									}
									mapNode4.put(piCode, objPICode[0].toString());
								}
							}

							String poDate = objGlobal.funGetDate("yyyy/MM/dd", arrObjPO[3].toString());
							String poCode = arrObjPO[0].toString() + " " + poDate;
							if (arrObjPO[2].toString().trim().length() > 0) {
								double totalAmt = Double.parseDouble(arrObjPO[4].toString());
								totalAmt = Math.rint(totalAmt);
								poCode = arrObjPO[0].toString() + " " + poDate + " (" + arrObjPO[2].toString() + ") " + totalAmt + "#frmPurchaseOrder";
							}
							mapNode3.put(poCode, mapNode4);
						}
					}
				}
				mapNode2.put(grnCode, mapNode3);
				mapNode1.put("Empty", mapNode2);
			}

			Set setNode2 = mapNode2.entrySet();
			Iterator itNode2 = setNode2.iterator();
			while (itNode2.hasNext()) {
				Map.Entry me = (Map.Entry) itNode2.next();

				if (null != me.getValue()) {
					Map tempMap1 = (HashMap<String, HashMap<String, String>>) me.getValue();
					Set setNode3 = tempMap1.entrySet();
					Iterator itNode3 = setNode3.iterator();
					while (itNode3.hasNext()) {
						Map.Entry me3 = (Map.Entry) itNode3.next();

						if (null != me3.getValue()) {
							Map tempMap2 = (HashMap<String, String>) me3.getValue();
							Set setNode4 = tempMap2.entrySet();
							Iterator itNode4 = setNode4.iterator();
							while (itNode4.hasNext()) {
								Map.Entry me4 = (Map.Entry) itNode4.next();
							}
						}
					}
				}
			}
		}

		if (formName.equals("frmPurchaseOrder") && type.equalsIgnoreCase("Forward")) {
			Map mapNode3 = new HashMap<String, HashMap<String, String>>();
			// String
			// sql_PO="select strPOCode,strPICode from tblpurchaseorderdtl where strPOCode='"+transCode+"'";
			String sql_PO = "select a.strPOCode,a.strPICode, ifnull(c.strPName,''),date(b.dtPODate),b.dblFinalAmt " + "from tblpurchaseorderdtl a,tblpurchaseorderhd b " + "left outer join tblpartymaster c on b.strSuppCode=c.strPCode " + "where a.strPOCode=b.strPOCode and a.strPOCode='" + transCode + "' " + "and a.strClientCode='" + clientCode + "'";
			List listPO = objGlobalFunService.funGetList(sql_PO, "sql");
			if (listPO.size() > 0) {
				for (int poCnt = 0; poCnt < listPO.size(); poCnt++) {
					Map mapNode4 = new HashMap<String, String>();
					Object[] arrObjPO = (Object[]) listPO.get(poCnt);
					// String
					// sql_PI="select strPICode from tblpurchaseindenddtl where strPICode='"+arrObjPO[1].toString()+"'";

					String sql_PI = "select a.strPICode,ifnull(c.strLocName,''),date(b.dtPIDate) " + "from tblpurchaseindenddtl a,tblpurchaseindendhd b " + "left outer join tbllocationmaster c on b.strLocCode=c.strLocCode " + "where a.strPIcode=b.strPIcode and a.strPICode='" + arrObjPO[1].toString() + "'";

					List listPI = objGlobalFunService.funGetList(sql_PI, "sql");
					if (listPI.size() > 0) {
						for (int piCnt = 0; piCnt < listPI.size(); piCnt++) {
							Object[] objPICode = (Object[]) listPI.get(piCnt);
							String piDate = objGlobal.funGetDate("yyyy/MM/dd", objPICode[2].toString());
							String piCode = objPICode[0].toString() + " " + piDate;
							if (objPICode[1].toString().trim().length() > 0) {
								// double
								// totalAmt=Double.parseDouble(arrObjPO[4].toString());
								double totalAmt = 0;
								totalAmt = Math.rint(totalAmt);
								piCode = objPICode[0].toString() + " " + piDate + " (" + objPICode[1].toString() + ") " + totalAmt + "#frmPurchaseIndent";
							}
							mapNode4.put(piCode, objPICode[0].toString());
						}
					}
					String poDate = objGlobal.funGetDate("yyyy/MM/dd", arrObjPO[3].toString());
					String poCode = arrObjPO[0].toString() + " " + poDate;
					if (arrObjPO[2].toString().trim().length() > 0) {
						double totalAmt = Double.parseDouble(arrObjPO[4].toString());
						totalAmt = Math.rint(totalAmt);
						poCode = arrObjPO[0].toString() + " " + poDate + " (" + arrObjPO[2].toString() + ") " + totalAmt + "#frmPurchaseOrder";
					}
					mapNode3.put(poCode, mapNode4);
				}
			}
			mapNode2.put("Empty", mapNode3);
			mapNode1.put("Empty", mapNode2);
		}

		if (formName.equals("frmPurchaseOrder") && type.equalsIgnoreCase("Backward")) {
			String poCode = transCode;
			Map mapNode3 = new HashMap<String, HashMap<String, String>>();

			String sql_PurOrderInfo = "select a.strPOCode,date(a.dtPODate),b.strPName,a.dblFinalAmt " + "from tblpurchaseorderhd a, tblpartymaster b " + "where a.strSuppCode=b.strPCode and a.strPOCode='" + transCode + "'";
			List arrListPOInfo = objGlobalFunService.funGetList(sql_PurOrderInfo, "sql");
			if (arrListPOInfo.size() > 0) {
				Object[] arrObjPO = (Object[]) arrListPOInfo.get(0);
				String poDate = objGlobal.funGetDate("yyyy/MM/dd", arrObjPO[1].toString());
				double totalAmt = Double.parseDouble(arrObjPO[3].toString());
				totalAmt = Math.rint(totalAmt);
				poCode = poCode + " " + poDate + " (" + arrObjPO[2].toString() + ") " + totalAmt + "#frmPurchaseOrder";
			}

			String sql_GRN = "select a.strGRNCode,a.strCode,ifnull(c.strPName,''),date(b.dtGRNDate),b.dblTotal " + "from tblgrndtl a,tblgrnhd b " + "left outer join tblpartymaster c on b.strSuppCode=c.strPCode " + "where a.strGRNCode=b.strGRNCode and a.strCode='" + transCode + "'";
			List listGRN = objGlobalFunService.funGetList(sql_GRN, "sql");
			if (listGRN.size() > 0) {
				for (int gCnt = 0; gCnt < listGRN.size(); gCnt++) {
					Map mapNode4 = new HashMap<String, String>();
					Object[] arrObjGRN = (Object[]) listGRN.get(gCnt);
					String grnDate = objGlobal.funGetDate("yyyy/MM/dd", arrObjGRN[3].toString());
					String grnCode = arrObjGRN[0].toString() + " " + grnDate;
					if (arrObjGRN[2].toString().trim().length() > 0) {
						double totalAmt = Double.parseDouble(arrObjGRN[4].toString());
						totalAmt = Math.rint(totalAmt);
						grnCode = arrObjGRN[0].toString() + " " + grnDate + "(" + arrObjGRN[2].toString() + ") " + totalAmt + "#frmGRN";
					}

					String sql_BillPass = "select b.strBillPassNo, ifnull(c.strPName,''),date(b.dtBillDate),b.dblBillAmt " + "from tblbillpassdtl a,tblbillpasshd b " + "left outer join tblpartymaster c on b.strSuppCode=c.strPCode " + "where a.strBillPassNo=b.strBillPassNo and a.strGRNCode='" + arrObjGRN[0].toString() + "'";

					List listBillPass = objGlobalFunService.funGetList(sql_BillPass, "sql");
					if (listBillPass.size() > 0) {
						for (int bpCnt = 0; bpCnt < listBillPass.size(); bpCnt++) {
							Object[] arrObjBillPass = (Object[]) listBillPass.get(bpCnt);
							String billPassDate = objGlobal.funGetDate("yyyy/MM/dd", arrObjBillPass[2].toString());
							String billPassNo = arrObjBillPass[0].toString() + " " + billPassDate;
							if (arrObjBillPass[1].toString().trim().length() > 0) {
								double totalAmt = Double.parseDouble(arrObjBillPass[3].toString());
								totalAmt = Math.rint(totalAmt);
								billPassNo = arrObjBillPass[0].toString() + " " + billPassDate + " (" + arrObjBillPass[1].toString() + ") " + totalAmt + "#frmBillPassing";
							}

							mapNode4.put(billPassNo, arrObjBillPass[0].toString());
						}
					}
					mapNode3.put(grnCode, mapNode4);
				}
			}
			mapNode2.put(poCode, mapNode3);
			mapNode1.put("Empty", mapNode2);
		}

		if (formName.equals("frmPurchaseIndent")) {
			Map mapNode3 = new HashMap<String, HashMap<String, String>>();
			Map mapNode4 = new HashMap<String, String>();

			String sql_PI = "select a.strPICode,ifnull(c.strLocName,''),date(b.dtPIDate) " + "from tblpurchaseindenddtl a,tblpurchaseindendhd b " + "left outer join tbllocationmaster c on b.strLocCode=c.strLocCode " + "where a.strPIcode=b.strPIcode and a.strPICode='" + transCode + "'";

			List listPI = objGlobalFunService.funGetList(sql_PI, "sql");
			if (listPI.size() > 0) {
				for (int piCnt = 0; piCnt < listPI.size(); piCnt++) {
					Object[] objPICode = (Object[]) listPI.get(piCnt);
					String piDate = objGlobal.funGetDate("yyyy/MM/dd", objPICode[2].toString());
					String piCode = objPICode[0].toString() + " " + piDate;
					if (objPICode[1].toString().trim().length() > 0) {
						double totalAmt = 0;
						piCode = objPICode[0].toString() + " " + piDate + " (" + objPICode[1].toString() + ") " + totalAmt + "#frmPurchaseIndent";
					}
					mapNode4.put(piCode, objPICode[0].toString());
				}
			}
			mapNode3.put("Empty", mapNode4);
			mapNode2.put("Empty", mapNode3);
			mapNode1.put("Empty", mapNode2);
		}

		if (formName.equals("frmMIS") && type.equalsIgnoreCase("Forward")) {
			Map mapNode3 = new HashMap<String, HashMap<String, String>>();
			Map mapNode4 = new HashMap<String, String>();

			// String
			// sql_MIS="select strMISCode from tblmisdtl where strMISCode='"+transCode+"'";
			String sql_MIS = "select a.strMISCode,a.strReqCode, c.strLocName,date(b.dtMISDate) " + "from tblmisdtl a,tblmishd b " + "left outer join tbllocationmaster c on b.strLocFrom=c.strLocCode " + "where a.strMISCode=b.strMISCode and a.strMISCode='" + transCode + "'";

			List listMIS = objGlobalFunService.funGetList(sql_MIS, "sql");
			if (listMIS.size() > 0) {
				for (int misCnt = 0; misCnt < listMIS.size(); misCnt++) {
					Object[] arrObjMIS = (Object[]) listMIS.get(misCnt);

					String misDate = objGlobal.funGetDate("yyyy/MM/dd", arrObjMIS[3].toString());
					String misCode = arrObjMIS[0].toString() + " " + misDate;
					if (arrObjMIS[2].toString().trim().length() > 0) {
						// double
						// totalAmt=Double.parseDouble(arrObjMIS[3].toString());
						double totalAmt = 0;
						totalAmt = Math.rint(totalAmt);
						misCode = arrObjMIS[0].toString() + " " + misDate + " (" + arrObjMIS[2].toString() + ") " + totalAmt + "#frmMIS";
					}
					mapNode4.put(misCode, arrObjMIS[0].toString());
				}
			}
			mapNode3.put("Empty", mapNode4);
			mapNode2.put("Empty", mapNode3);
			mapNode1.put("Empty", mapNode2);
		}

		if (formName.equals("frmMIS") && type.equalsIgnoreCase("Backward")) {
			Map mapNode3 = new HashMap<String, HashMap<String, String>>();
			// String
			// sql_MIS="select strMISCode,strReqCode from tblmisdtl where strMISCode='"+transCode+"'";

			String sql_MIS = "select a.strMISCode,a.strReqCode, c.strLocName,date(b.dtMISDate) " + "from tblmisdtl a,tblmishd b " + "left outer join tbllocationmaster c on b.strLocFrom=c.strLocCode " + "where a.strMISCode=b.strMISCode and a.strMISCode='" + transCode + "'";

			List listMIS = objGlobalFunService.funGetList(sql_MIS, "sql");
			if (listMIS.size() > 0) {
				for (int mCnt = 0; mCnt < listMIS.size(); mCnt++) {
					Map mapNode4 = new HashMap<String, String>();
					Object[] arrObjMIS = (Object[]) listMIS.get(mCnt);
					// String
					// sql_MatReq="select strReqCode from tblreqdtl where strReqCode='"+arrObjMIS[1].toString()+"'";
					String sql_MatReq = "select a.strReqCode,c.strLocName,date(b.dtReqDate),b.dblSubTotal " + "from tblreqdtl a,tblreqhd b " + "left outer join tbllocationmaster c on b.strLocBy=c.strLocCode " + "where a.strReqCode=b.strReqCode and a.strReqCode='" + arrObjMIS[1].toString() + "'";

					List listMatReq = objGlobalFunService.funGetList(sql_MatReq, "sql");
					if (listMatReq.size() > 0) {
						for (int mrCnt = 0; mrCnt < listMatReq.size(); mrCnt++) {
							Object[] arrObjMatReq = (Object[]) listMatReq.get(mrCnt);
							String reqDate = objGlobal.funGetDate("yyyy/MM/dd", arrObjMatReq[2].toString());
							String reqCode = arrObjMatReq[0].toString() + " " + reqDate;
							if (arrObjMatReq[2].toString().trim().length() > 0) {
								double totalAmt = Double.parseDouble(arrObjMatReq[3].toString());
								totalAmt = Math.rint(totalAmt);
								reqCode = arrObjMatReq[0].toString() + " " + reqDate + " (" + arrObjMatReq[1].toString() + ") " + totalAmt + "#frmMaterialReq";
							}
							mapNode4.put(reqCode, arrObjMatReq[0].toString());
						}
					}

					String misDate = objGlobal.funGetDate("yyyy/MM/dd", arrObjMIS[3].toString());
					String misCode = arrObjMIS[0].toString() + " " + misDate;
					if (arrObjMIS[2].toString().trim().length() > 0) {
						// double
						// totalAmt=Double.parseDouble(arrObjMIS[3].toString());
						double totalAmt = 0;
						totalAmt = Math.rint(totalAmt);
						misCode = arrObjMIS[0].toString() + " " + misDate + " (" + arrObjMIS[2].toString() + ") " + totalAmt + "#frmMIS";
					}
					mapNode3.put(misCode, mapNode4);
				}
			}
			mapNode2.put("Empty", mapNode3);
			mapNode1.put("Empty", mapNode2);
		}

		if (formName.equals("frmMaterialReq")) {
			Map mapNode3 = new HashMap<String, HashMap<String, String>>();
			Map mapNode4 = new HashMap<String, String>();

			String sql_MatReq = "select a.strReqCode,c.strLocName,date(b.dtReqDate),b.dblSubTotal " + "from tblreqdtl a,tblreqhd b " + "left outer join tbllocationmaster c on b.strLocBy=c.strLocCode " + "where a.strReqCode=b.strReqCode and a.strReqCode='" + transCode + "'";

			List listMatReq = objGlobalFunService.funGetList(sql_MatReq, "sql");
			if (listMatReq.size() > 0) {
				for (int mrCnt = 0; mrCnt < listMatReq.size(); mrCnt++) {
					Object[] arrObjMatReq = (Object[]) listMatReq.get(mrCnt);
					String reqDate = objGlobal.funGetDate("yyyy/MM/dd", arrObjMatReq[2].toString());
					String reqCode = arrObjMatReq[0].toString() + " " + reqDate;
					if (arrObjMatReq[2].toString().trim().length() > 0) {
						double totalAmt = Double.parseDouble(arrObjMatReq[3].toString());
						totalAmt = Math.rint(totalAmt);
						reqCode = arrObjMatReq[0].toString() + " " + reqDate + " (" + arrObjMatReq[1].toString() + ") " + totalAmt + "#frmMaterialReq";
					}
					mapNode4.put(reqCode, reqCode);
				}
			}

			mapNode3.put("Empty", mapNode4);
			mapNode2.put("Empty", mapNode3);
			mapNode1.put("Empty", mapNode2);
		}

		return mapNode1;
	}

}
