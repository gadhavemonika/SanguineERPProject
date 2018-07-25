package com.sanguine.dao;

import java.util.List;

import com.sanguine.model.clsGRNDtlModel;
import com.sanguine.model.clsGRNHdModel;
import com.sanguine.model.clsGRNTaxDtlModel;

public interface clsGRNDao {
	public void funAddUpdate(clsGRNHdModel object);

	public void funAddUpdateDtl(clsGRNDtlModel object);

	public void funUpdateProductSupplier(String suppCode, String prodCode, String clientCode, String maxQty, String price);

	public List funGetProdSupp(String suppCode, String prodCode, String clientCode);

	public List<clsGRNHdModel> funGetList(String clientCode);

	public List funGetDtlList(String GRNCode, String clientCode);

	public List funGetDtlListAgainst(String code, String clientCode, String againstTableName);

	public List funGetObject(String GRNCode, String clientCode);

	public void funDeleteDtl(String GRNCode, String clientCode);

	public int funDeleteProdSupp(String suppCode, String prodCode, String clientCode);

	public List funGetFixedAmtTaxList(String clientCode);

	public List funGetList(String groupCode, String clientCode);

	public List funLoadPOforGRN(String strSuppCode, String strPropCode, String strClientCode);

	public List funGetNonStkData(String strPOCode, String strGrnCode, String strClientCode);

	public int funDeleteGRNTaxDtl(String GRNCode, String clientCode);

	public void funAddUpdateGRNTaxDtl(clsGRNTaxDtlModel objModel);
}
