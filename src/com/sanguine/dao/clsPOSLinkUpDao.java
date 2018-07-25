package com.sanguine.dao;

import com.sanguine.model.clsPOSLinkUpModel;

public interface clsPOSLinkUpDao {

	public void funAddUpdatePOSLinkUp(clsPOSLinkUpModel objMaster);

	public clsPOSLinkUpModel funGetPOSLinkUp(String docCode, String clientCode);

	public int funExecute(String sql);

}
