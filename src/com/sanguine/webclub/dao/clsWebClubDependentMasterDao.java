package com.sanguine.webclub.dao;

import java.util.List;

import com.sanguine.webclub.model.clsWebClubDependentMasterModel;

public interface clsWebClubDependentMasterDao {

	public void funAddUpdateWebClubDependentMaster(clsWebClubDependentMasterModel objMaster);

	public clsWebClubDependentMasterModel funGetWebClubDependentMaster(String docCode, String clientCode);

	public List funGetWebClubDependentMasterList(String docCode, String clientCode);

}
