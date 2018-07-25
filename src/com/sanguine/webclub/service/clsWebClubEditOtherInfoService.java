package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubEditOtherInfoModel;

public interface clsWebClubEditOtherInfoService {

	public void funAddUpdateWebClubEditOtherInfo(clsWebClubEditOtherInfoModel objMaster);

	public clsWebClubEditOtherInfoModel funGetWebClubEditOtherInfo(String docCode, String clientCode);

}
