package com.sanguine.dao;

import java.util.List;

import com.sanguine.model.clsProductReOrderLevelModel;
import com.sanguine.model.clsPurchaseIndentDtlModel;
import com.sanguine.model.clsPurchaseIndentHdModel;

public interface clsPurchaseIndentHdDao {

	public void funAddUpdatePurchaseIndent(clsPurchaseIndentHdModel PurchaseIndentHdModel);

	public void funAddUpdatePurchaseIndentDtl(clsPurchaseIndentDtlModel PurchaseIndentDtlModel);

	public List<clsPurchaseIndentHdModel> funListPurchaseIndentHdModel();

	public clsPurchaseIndentHdModel funGetPurchaseIndent(String PurchaseIndentCode);

	public void funDeleteDtl(String PICode);

	@SuppressWarnings("rawtypes")
	public List funGetDtlList(String PICode, String clientCode, String strLocCode);

	public clsPurchaseIndentHdModel funGetObject(String code, String clientCode);

	public clsProductReOrderLevelModel funGetReOrderLevel(String strProdCode, String strLocCode, String clientCode);

}
