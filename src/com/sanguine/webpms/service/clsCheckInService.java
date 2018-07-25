package com.sanguine.webpms.service;

import java.util.List;

import com.sanguine.webpms.model.clsCheckInHdModel;
import com.sanguine.webpms.model.clsRoomPackageDtl;

public interface clsCheckInService {

	public void funAddUpdateCheckInHd(clsCheckInHdModel objHdModel);

	public clsCheckInHdModel funGetCheckInData(String checkInNo, String clientCode);
	
	public List<clsRoomPackageDtl> funGetCheckInIncomeList(String checkInNo, String clientCode) ;
	
}
