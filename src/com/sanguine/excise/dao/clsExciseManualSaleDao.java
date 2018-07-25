package com.sanguine.excise.dao;

import java.util.List;

import com.sanguine.excise.model.clsExciseManualSaleHdModel;
import com.sanguine.excise.model.clsExciseManualSaleDtlModel;

@SuppressWarnings("rawtypes")
public interface clsExciseManualSaleDao {

	public boolean funAddUpdateExciseSalesMaster(clsExciseManualSaleHdModel objMaster);

	public List funGetObject(long intId, String clientCode);

	public boolean funAddUpdateSalesDtl(clsExciseManualSaleDtlModel objSalesDtl);

	public List funGetList(String clientCode);

	public List funGetSalesDtlList(long intId, String clientCode);

	public boolean funDeleteHd(long intId, String clientCode);

	public boolean funDeleteDtl(long intId, String clientCode);

	public boolean funDeleteSaleData(long intId, String clientCode);

}
