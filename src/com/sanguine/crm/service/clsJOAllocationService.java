package com.sanguine.crm.service;

import java.util.List;

import com.sanguine.crm.model.clsJOAllocationDtlModel;
import com.sanguine.crm.model.clsJOAllocationHdModel;

public interface clsJOAllocationService {

	public boolean funAddUpdateJAHd(clsJOAllocationHdModel objMaster);

	public clsJOAllocationHdModel funGetJAHdObject(String strJAcode, String clientCode);

	@SuppressWarnings("rawtypes")
	public List funGetJAHdData(String strJAcode, String clientCode);

	public void funDeleteDtl(String strJAcode, String clientCode);

	public boolean funAddUpdateJADtl(clsJOAllocationDtlModel objMaster);

	@SuppressWarnings("rawtypes")
	public List funGetJADtlData(String strJAcode, String clientCode);

}
