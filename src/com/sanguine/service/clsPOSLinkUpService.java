package com.sanguine.service;

import com.sanguine.model.clsPOSLinkUpModel;

public interface clsPOSLinkUpService {

	public void funAddUpdatePOSLinkUp(clsPOSLinkUpModel objMaster);

	public clsPOSLinkUpModel funGetPOSLinkUp(String docCode, String clientCode);

	public int funExecute(String sql);
}
