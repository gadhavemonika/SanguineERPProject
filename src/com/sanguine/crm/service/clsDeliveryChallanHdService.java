package com.sanguine.crm.service;

import java.util.List;

import com.sanguine.crm.model.clsDeliveryChallanHdModel;
import com.sanguine.crm.model.clsDeliveryChallanModelDtl;

public interface clsDeliveryChallanHdService {

	public boolean funAddUpdateDeliveryChallanHd(clsDeliveryChallanHdModel objHdModel);

	public void funAddUpdateDeliveryChallanDtl(clsDeliveryChallanModelDtl objDtlModel);

	public clsDeliveryChallanHdModel funGetDeliveryChallanHd(String dcCode, String clientCode);

	public void funDeleteDtl(String dCCode, String clientCode);

	public List<Object> funGetDeliveryChallan(String dcCode, String clientCode);

	public List<Object> funGetDeliveryChallanDtl(String dcCode, String clientCode);

}
