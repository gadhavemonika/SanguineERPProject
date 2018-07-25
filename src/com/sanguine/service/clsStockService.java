package com.sanguine.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.bean.clsParentDataForBOM;
import com.sanguine.model.clsBomDtlModel;
import com.sanguine.model.clsBomHdModel;
import com.sanguine.model.clsInitialInventoryModel;
import com.sanguine.model.clsOpeningStkDtl;
import com.sanguine.model.clsTaxHdModel;
import com.sanguine.model.clsTaxSettlementMasterModel;

public interface clsStockService {
	public void funAddUpdate(clsInitialInventoryModel object);

	public void funAddUpdateDtl(clsOpeningStkDtl object);

	public List<clsInitialInventoryModel> funGetList();

	public clsInitialInventoryModel funGetObject(String code, String clientCode);

	public List funGetDtlList(String code, String clientCode);

	public List funInitialInventoryReport(String clientCode);

	public void funDeleteDtl(String opStkCode, String clientCode);
}
