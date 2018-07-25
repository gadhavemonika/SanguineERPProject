package com.sanguine.webclub.service;

import java.util.List;

import com.sanguine.webclub.model.clsWebClubDependentMasterModel;

public interface clsWebClubDependentMasterService {

	public void funAddUpdateWebClubDependentMaster(clsWebClubDependentMasterModel objMaster);

	public clsWebClubDependentMasterModel funGetWebClubDependentMaster(String docCode, String clientCode);

	public List funGetWebClubDependentMasterList(String docCode, String clientCode);

}
