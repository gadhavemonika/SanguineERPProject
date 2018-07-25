package com.sanguine.dao;

import java.util.List;

import com.sanguine.model.clsProcessMasterModel;
import com.sanguine.model.clsProdProcessModel;
import com.sanguine.model.clsWorkOrderDtlModel;
import com.sanguine.model.clsWorkOrderHdModel;

public interface clsWorkOrderDao {
	public long funGetLastNo(String tableName, String masterName, String columnName);

	public void funAddWorkOrderHd(clsWorkOrderHdModel WoHd);

	public void funAddUpdateWorkOrderDtl(clsWorkOrderDtlModel WoDtl);

	public List<clsWorkOrderHdModel> funGetList();

	// public clsWorkOrderHdModel funGetObject(String code);
	public List<clsWorkOrderDtlModel> funGetDtlList(String WoCode, String prodCode, String clientCode);

	public void funDeleteDtl(String WoCode, String clientCode);

	public List<clsWorkOrderHdModel> funGetWOHdData(String WoCode, String clientCode);

	public clsWorkOrderHdModel funGetWOHd(String WoCode, String clientCode);

	public List funGetProdProcess(String prodCode, String strClientCode);

	public String funGetProdProcessStatus(String strProdCode, String strProcessCode, String strWoCode, String strClientCode);

	public List funGetProcessDet(String strWOCode, String strClientCode);

	public String funGetWOStatusforProduct(String strWOCode, String strClientCode);

	public List funGenearteWOAgainstOPData(String oPCode, String soCode, String strClientCode, String against);

	public List funGetRecipeList(String strParentCode, String strClientCode);

	public List funGetGeneratedWOAgainstOPData(String oPCode, String clientCode);

}
