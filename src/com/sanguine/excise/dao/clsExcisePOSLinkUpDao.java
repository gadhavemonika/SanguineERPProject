package com.sanguine.excise.dao;

import com.sanguine.excise.model.clsExcisePOSLinkUpModel;

public interface clsExcisePOSLinkUpDao {

	public void funAddUpdatePOSLinkUp(clsExcisePOSLinkUpModel objMaster);

	public clsExcisePOSLinkUpModel funGetPOSLinkUp(String docCode, String clientCode);

	public int funExecute(String sql);

}
