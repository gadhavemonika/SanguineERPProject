package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsWorkOrderDtlModel;
import com.sanguine.model.clsWorkOrderHdModel;

@SuppressWarnings("rawtypes")
public interface clsWorkOrderService {
	public long funGetLastNo(String tableName, String masterName, String columnName);

	public void funAddWorkOrderHd(clsWorkOrderHdModel WoHd);

	public void funAddUpdateWorkOrderDtl(clsWorkOrderDtlModel WoDtl);

	public List<clsWorkOrderHdModel> funGetList();

	public List funGetDtlList(String WoCode, String prodCode, String clientCode);

	public void funDeleteDtl(String WoCode, String clientCode);

	public List<clsWorkOrderHdModel> funGetWOHdData(String WoHdcode, String clientCode);

	public clsWorkOrderHdModel funGetWOHd(String WoHdcode, String clientCode);

	public List funGetProdProcess(String prodCode, String strClientCode);

	public String funGetProdProcessStatus(String strProdCode, String strProcessCode, String strWoCode, String strClientCode);

	public List funGetProcessDet(String strWOCode, String strClientCode);

	public String funGetWOStatusforProduct(String strWOCode, String strClientCode);

	public List funGenearteWOAgainstOPData(String oPCode, String strSoCode, String strClientCode, String against);

	public List funGetRecipeList(String strParentCode, String strClientCode);

	public List funGetGeneratedWOAgainstOPData(String oPCode, String clientCode);

}
