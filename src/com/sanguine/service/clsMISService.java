package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsMISDtlModel;
import com.sanguine.model.clsMISHdModel;
import com.sanguine.model.clsRequisitionHdModel;

public interface clsMISService {
	public long funGetLastNo(String tableName, String masterName, String columnName);

	public void funAddMISHd(clsMISHdModel MISHd);

	public void funAddUpdateMISDtl(clsMISDtlModel MISDtl);

	public List<clsMISHdModel> funGetList();

	public clsMISHdModel funGetObject(String code, String clientCode);

	public List funGetDtlList(String MISCode, String clientCode);

	public void funDeleteDtl(String MISCode, String strClientCode);

	public List funMISforMRDetails(String strLocFrom, String strLocTo, String strClientCode);

	public List<String> funGetProductDtl(String strProdCode, String clientCode);

	public int funInsertNonStkItemDirect(String sql);

}
