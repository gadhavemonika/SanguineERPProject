package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubEditOtherInfoModel;

public interface clsWebClubEditOtherInfoDao {

	public void funAddUpdateWebClubEditOtherInfo(clsWebClubEditOtherInfoModel objMaster);

	public clsWebClubEditOtherInfoModel funGetWebClubEditOtherInfo(String docCode, String clientCode);

}
