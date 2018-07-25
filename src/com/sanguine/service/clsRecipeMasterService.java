package com.sanguine.service;

import java.util.List;

import com.sanguine.bean.clsParentDataForBOM;
import com.sanguine.model.clsBomDtlModel;
import com.sanguine.model.clsBomHdModel;
import com.sanguine.model.clsTaxHdModel;
import com.sanguine.model.clsTaxSettlementMasterModel;

public interface clsRecipeMasterService {
	public void funAddUpdate(clsBomHdModel object);

	public void funAddUpdateDtl(clsBomDtlModel object);

	public void funDeleteDtl(String bomCode, String clientCode);

	public List<clsBomHdModel> funGetList(String bomCode, String clientCode);

	public clsBomHdModel funGetObject(String code, String clientCode);

	public List funGetProductList(String sql);

	public List funGetDtlList(String bomCode, String clientCode);

	public List funGetBOMCode(String strParentCode, String strClientCode);

	public List funGetBOMDtl(String strClientCode, String BOMCode);
}
