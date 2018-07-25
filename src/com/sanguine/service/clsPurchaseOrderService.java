package com.sanguine.service;

import java.util.List;

import org.hibernate.Query;

import com.sanguine.model.clsPOTaxDtlModel;
import com.sanguine.model.clsPurchaseOrderDtlModel;
import com.sanguine.model.clsPurchaseOrderHdModel;

public interface clsPurchaseOrderService {

	public void funAddUpdatePurchaseOrderHd(clsPurchaseOrderHdModel PurchaseOrderHdModel);

	public void funAddUpdatePurchaseOrderDtl(clsPurchaseOrderDtlModel PurchaseOrderDtlModel);

	public void funDeletePODtl(String POCode, String clientCode);

	public clsPurchaseOrderHdModel funGetObject(String POCode, String clientCode);

	public List<clsPurchaseOrderDtlModel> funGetDtlList(String POCode, String clientCode);

	public List funGetPIData(String sql, String PICode, String clientCode);

	public List funGetHelpdataPIforPo(String clientCode, String strPropCode);

	public int funDeletePOTaxDtl(String POCode, String clientCode);

	public void funAddUpdatePOTaxDtl(clsPOTaxDtlModel objModel);

	public List<clsPurchaseOrderDtlModel> funGetPODtlList(String strPOCode, String clientCode);

	public List funGetHdList(String fDate, String tDate, String clientCode);

	public List<clsPurchaseOrderDtlModel> funGetPODtlModelList(String POCode, String clientCode);

}
