package com.sanguine.dao;

import java.util.List;

import com.sanguine.model.clsMISDtlModel;
import com.sanguine.model.clsMRPIDtl;
import com.sanguine.model.clsProductStandardModel;
import com.sanguine.model.clsRequisitionDtlModel;
import com.sanguine.model.clsRequisitionHdModel;

public interface clsRequisitionDao {
	public long funGetLastNo(String tableName, String masterName, String columnName);

	public void funAddRequisionHd(clsRequisitionHdModel reqhd);

	public void funAddUpdateRequisionDtl(clsRequisitionDtlModel reqdtl);

	public List<clsRequisitionHdModel> funGetList();

	public clsRequisitionHdModel funGetObject(String code, String strClientCode);

	public List funGetProductList(String sql);

	public void funDeleteDtl(String bomCode);

	public clsRequisitionHdModel funGetReqHdData(String reqCode);

	public List<clsRequisitionDtlModel> funGetDtlList(String ReqCode, String clientCode);

	public List funGetReqDtlList(String reqCode, String clientCode, String locCode, String userCode);

	public List funGenerateAutoReq(String strLocCode, String clientCode, String userCode, String strGCode, String strSGCode, String strSuppCode);

	public void funSaveMRPIDtl(clsMRPIDtl funSaveMRPIDtl);

	public void funAddProductStandard(List<clsProductStandardModel> objProdStandard);

	public void funDeleteProductStandard(String strLocCode, String strPropertyCode, String clientCode);

	public List<clsProductStandardModel> funGetProductStandartList(String strPropertyCode, String strLocCode, String clientCode);

	public List<String> funGetProductDtl(String strProdCode, String clientCode);
}
