package com.sanguine.crm.dao;

import java.util.List;

import com.sanguine.crm.model.clsDeliveryChallanHdModel;
import com.sanguine.crm.model.clsDeliveryChallanModelDtl;

public interface clsDeliveryChallanDao {

	public boolean funAddUpdateDeliveryChallanHd(clsDeliveryChallanHdModel objHdModel);

	public void funAddUpdateDeliveryChallanDtl(clsDeliveryChallanModelDtl objDtlModel);

	public clsDeliveryChallanHdModel funGetDeliveryChallanHd(String dcCode, String clientCode);

	public void funDeleteDtl(String soCode, String clientCode);

	public List<Object> funGetDeliveryChallan(String dcCode, String clientCode);

	public List<Object> funGetDeliveryChallanDtl(String dcCode, String clientCode);

}
