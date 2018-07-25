package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsMRPIDtl;
import com.sanguine.model.clsProductStandardModel;
import com.sanguine.model.clsRequisitionDtlModel;
import com.sanguine.model.clsRequisitionHdModel;

public interface clsRequisitionService {
	public void funAddRequisionHd(clsRequisitionHdModel reqhd);

	public void funAddUpdateRequisionDtl(clsRequisitionDtlModel reqdtl);

	public List<clsRequisitionHdModel> funGetList();

	public clsRequisitionHdModel funGetObject(String ReqCode, String clientCode);

	@SuppressWarnings("rawtypes")
	public List funGetProductList(String sql);

	public void funDeleteDtl(String strReqCode);

	public clsRequisitionHdModel funGetReqHdData(String reqCode);

	public List<clsRequisitionDtlModel> funGetDtlList(String reqCode, String clientCode);

	@SuppressWarnings("rawtypes")
	public List funGetReqDtlList(String reqCode, String clientCode, String locCode, String userCode);

	@SuppressWarnings("rawtypes")
	public List funGenerateAutoReq(String strLocCode, String clientCode, String userCode, String strGCode, String strSGCode, String strSuppCode);

	public void funSaveMRPIDtl(clsMRPIDtl mRPIDtlModel);

	public void funAddProductStandard(List<clsProductStandardModel> objProdStandard);

	public void funDeleteProductStandard(String strLocCode, String strPropertyCode, String clientCode);

	public List<clsProductStandardModel> funGetProductStandartList(String strPropertyCode, String strLocCode, String clientCode);

	public List<String> funGetProductDtl(String strProdCode, String clientCode);

}
