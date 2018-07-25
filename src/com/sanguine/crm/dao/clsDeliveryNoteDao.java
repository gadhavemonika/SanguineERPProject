package com.sanguine.crm.dao;

import java.util.List;

import com.sanguine.crm.model.clsDeliveryNoteDtlModel;
import com.sanguine.crm.model.clsDeliveryNoteHdModel;

public interface clsDeliveryNoteDao {

	public Boolean funAddUpdateDeliveryNoteHd(clsDeliveryNoteHdModel objHdModel);

	public Boolean funAddUpdateDeliveryNoteDtl(clsDeliveryNoteDtlModel objDtlModel);

	@SuppressWarnings("rawtypes")
	public List funGetDelNoteHdObject(String DNCode, String clientCode);

	@SuppressWarnings("rawtypes")
	public List funGetDelNoteDtlList(String DNCode, String clientCode);

	public boolean funDeleteDtl(String DNCode, String clientCode);

}
