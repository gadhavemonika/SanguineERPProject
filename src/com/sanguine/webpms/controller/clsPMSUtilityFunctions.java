package com.sanguine.webpms.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsTaxCalculation;
import com.sanguine.webpms.bean.clsTaxProductDtl;
import com.sanguine.webpms.dao.clsWebPMSDBUtilityDao;

@Controller
public class clsPMSUtilityFunctions {

	@Autowired
	clsGlobalFunctionsService objGlobalFunService;

	@Autowired
	clsWebPMSDBUtilityDao objWebPMSUtility;

	public Map<String, List<clsTaxCalculation>> funCalculatePMSTax(List<clsTaxProductDtl> listTaxProdDtl, String taxOnType) {
		Map<String, List<clsTaxCalculation>> hmTaxCalDtl = new HashMap<String, List<clsTaxCalculation>>();

		Date dt = new Date();
		String date = (dt.getYear() + 1900) + "-" + (dt.getMonth() + 1) + "-" + dt.getDate();

		String sql = "select strTaxCode,strTaxDesc,strIncomeHeadCode,strTaxType,dblTaxValue,strTaxOn " + " from tbltaxmaster where date(dteValidFrom) <='" + date + "' and date(dteValidTo)>='" + date + "' " + " and strTaxOnType ='" + taxOnType + "'";
		List listTaxDtl = objGlobalFunService.funGetListModuleWise(sql, "sql");

		for (clsTaxProductDtl objTaxProdDtl : listTaxProdDtl) {
			List<clsTaxCalculation> listTaxCalDtl = new ArrayList<clsTaxCalculation>();
			for (int cnt = 0; cnt < listTaxDtl.size(); cnt++) {
				String taxCalType = "Forward";
				Object[] arrObjTaxDtl = (Object[]) listTaxDtl.get(cnt);
				if (taxOnType.equalsIgnoreCase("Income Head")) {
					if (arrObjTaxDtl[2].toString().equals(objTaxProdDtl.getStrTaxProdCode())) {
						clsTaxCalculation objTaxCal = new clsTaxCalculation();
						double taxValue = Double.parseDouble(arrObjTaxDtl[4].toString());
						if (arrObjTaxDtl[3].toString().equalsIgnoreCase("Percentage")) // Check
																						// for
																						// Tax
																						// Type
																						// Per/Amt
						{
							double taxAmt = 0;
							if (taxCalType.equals("Forward")) // Forward Tax
																// Calculation
							{
								taxAmt = (objTaxProdDtl.getDblTaxProdAmt() * taxValue) / 100;
							} else // Backward Tax Calculation
							{
								taxAmt = objTaxProdDtl.getDblTaxProdAmt() * 100 / (100 + taxValue);
								taxAmt = objTaxProdDtl.getDblTaxProdAmt() - taxAmt;
							}
							if (taxAmt > 0) {
								objTaxCal.setStrTaxCode(arrObjTaxDtl[0].toString());
								objTaxCal.setStrTaxDesc(arrObjTaxDtl[1].toString());
								objTaxCal.setStrTaxType(taxCalType);
								objTaxCal.setDblTaxableAmt(objTaxProdDtl.getDblTaxProdAmt());
								objTaxCal.setDblTaxAmt(taxAmt);

								listTaxCalDtl.add(objTaxCal);
							}
						} else {
						}
					}
				} else {
					clsTaxCalculation objTaxCal = new clsTaxCalculation();
					double taxValue = Double.parseDouble(arrObjTaxDtl[4].toString());
					if (arrObjTaxDtl[3].toString().equalsIgnoreCase("Percentage")) // Check
																					// for
																					// Tax
																					// Type
																					// Per/Amt
					{
						double taxAmt = 0;
						if (taxCalType.equals("Forward")) // Forward Tax
															// Calculation
						{
							taxAmt = (objTaxProdDtl.getDblTaxProdAmt() * taxValue) / 100;
						} else // Backward Tax Calculation
						{
							taxAmt = objTaxProdDtl.getDblTaxProdAmt() * 100 / (100 + taxValue);
							taxAmt = objTaxProdDtl.getDblTaxProdAmt() - taxAmt;
						}
						if (taxAmt > 0) {
							objTaxCal.setStrTaxCode(arrObjTaxDtl[0].toString());
							objTaxCal.setStrTaxDesc(arrObjTaxDtl[1].toString());
							objTaxCal.setStrTaxType(taxCalType);
							objTaxCal.setDblTaxableAmt(objTaxProdDtl.getDblTaxProdAmt());
							objTaxCal.setDblTaxAmt(taxAmt);

							listTaxCalDtl.add(objTaxCal);
						}
					} else {
					}
				}
			}
			if (listTaxCalDtl.size() > 0) {
				hmTaxCalDtl.put(objTaxProdDtl.getStrTaxProdCode(), listTaxCalDtl);
			}
		}

		return hmTaxCalDtl;
	}

	public long funGenerateFolioNo(String transType) {
		long docNo = 1;
		String sql = "select lngDocNo,strTransactionType from tblinternal where strTransactionType='Folio' ";
		List list = objWebPMSUtility.funExecuteQuery(sql, "sql");

		if (list.size() > 0) {
			Object[] arrObjDocNo = (Object[]) list.get(0);
			docNo = Long.parseLong(arrObjDocNo[0].toString());
			docNo = docNo + 1;

			sql = "update tblinternal set lngDocNo='" + docNo + "' where strTransactionType='Folio' ";
			objWebPMSUtility.funExecuteUpdate(sql, "sql");
		} else {
			sql = "insert into tblinternal values ('Folio','" + docNo + "')";
			objWebPMSUtility.funExecuteUpdate(sql, "sql");
		}

		return docNo;
	}

	public long funGenerateFolioDocForIncomeHead(String transType) {
		long docNo = 1;
		String sql = "select lngDocNo,strTransactionType from tblinternal where strTransactionType='IncomeHeadFolio' ";
		List list = objWebPMSUtility.funExecuteQuery(sql, "sql");

		if (list.size() > 0) {
			Object[] arrObjDocNo = (Object[]) list.get(0);
			docNo = Long.parseLong(arrObjDocNo[0].toString());
			docNo = docNo + 1;

			sql = "update tblinternal set lngDocNo='" + docNo + "' where strTransactionType='IncomeHeadFolio' ";
			objWebPMSUtility.funExecuteUpdate(sql, "sql");
		} else {
			sql = "insert into tblinternal values ('IncomeHeadFolio','" + docNo + "')";
			objWebPMSUtility.funExecuteUpdate(sql, "sql");
		}

		return docNo;
	}

	public long funGenerateFolioDocForRoom(String transType) {
		long docNo = 1;
		String sql = "select lngDocNo,strTransactionType from tblinternal where strTransactionType='RoomFolio' ";
		List list = objWebPMSUtility.funExecuteQuery(sql, "sql");

		if (list.size() > 0) {
			Object[] arrObjDocNo = (Object[]) list.get(0);
			docNo = Long.parseLong(arrObjDocNo[0].toString());
			docNo = docNo + 1;

			sql = "update tblinternal set lngDocNo='" + docNo + "' where strTransactionType='RoomFolio' ";
			objWebPMSUtility.funExecuteUpdate(sql, "sql");
		} else {
			sql = "insert into tblinternal values ('RoomFolio','" + docNo + "')";
			objWebPMSUtility.funExecuteUpdate(sql, "sql");
		}

		return docNo;
	}

}
