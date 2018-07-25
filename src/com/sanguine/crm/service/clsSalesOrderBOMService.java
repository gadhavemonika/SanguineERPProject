package com.sanguine.crm.service;

import java.util.List;

import com.sanguine.crm.model.clsSalesOrderBOMModel;

public interface clsSalesOrderBOMService {

	public boolean funAddUpdateSoBomHd(clsSalesOrderBOMModel objHdModel);

	@SuppressWarnings({ "rawtypes" })
	public List funGetListOfMainParent(String soCode, String clientCode);

	@SuppressWarnings({ "rawtypes" })
	public List funGetListOnProdCode(String soCode, String prodCode, String clientCode);

	public void funDeleteSalesOrderBom(String soCode, String parentCode, String clientCode);

	public void funDeleteSOBomOnParent(String soCode, String strParentCode, String clientCode);

	public List funGetListOfSOBom(String soCode, String clientCode);

}
