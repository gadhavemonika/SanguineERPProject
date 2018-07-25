package com.sanguine.excise.service;

import java.util.List;

import com.sanguine.excise.model.clsExciseManualSaleHdModel;
import com.sanguine.excise.model.clsExciseManualSaleDtlModel;

@SuppressWarnings("rawtypes")
public interface clsExciseManualSaleService {

	public boolean funAddUpdateExciseSalesMaster(clsExciseManualSaleHdModel objMaster);

	public boolean funAddUpdateExciseSalesDtl(clsExciseManualSaleDtlModel objSalesDtl);

	public List funGetObject(long intId, String clientCode);

	public List funGetList(String clientCode);

	public List funGetSalesDtlList(long intId, String clientCode);

	public boolean funDeleteHd(long intId, String clientCode);

	public boolean funDeleteDtl(long intId, String clientCode);

	public boolean funDeleteSaleData(long intId, String clientCode);

}
