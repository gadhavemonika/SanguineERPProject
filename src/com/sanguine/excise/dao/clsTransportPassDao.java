package com.sanguine.excise.dao;

import java.util.List;

import com.sanguine.excise.model.clsTransportPassModel;
import com.sanguine.excise.model.clsTransportPassDtlModel;

@SuppressWarnings("rawtypes")
public interface clsTransportPassDao {

	public boolean funAddUpdateTPMaster(clsTransportPassModel objMaster);

	public boolean funAddUpdateTPDtl(clsTransportPassDtlModel objTPDtl);

	public List<clsTransportPassModel> funGetList(String clientCode);

	public List funGetObject(String tpCode, String clientCode);

	public List funGetTPDtlList(String tpCode, String clientCode);

	public boolean funDeleteHd(String tpCode, String clientCode);

	public boolean funDeleteDtl(String tpCode, String clientCode);

	public List funGetTpNOObject(String tpNo, String clientCode);

}
