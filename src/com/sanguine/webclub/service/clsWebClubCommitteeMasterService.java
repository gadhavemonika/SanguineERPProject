package com.sanguine.webclub.service;

import java.util.List;

import com.sanguine.webclub.model.clsWebClubCommitteeMasterDtl;
import com.sanguine.webclub.model.clsWebClubCommitteeMasterModel;

public interface clsWebClubCommitteeMasterService {

	public void funAddUpdateWebClubCommitteeMaster(clsWebClubCommitteeMasterModel objMaster);

	public clsWebClubCommitteeMasterModel funGetWebClubCommitteeMaster(String docCode, String clientCode);

	public void funAddUpdateCommittteeMasterDtl(clsWebClubCommitteeMasterDtl objMaster);

	public List<Object> funGetWebClubCommittteeMasterDtl(String docCode, String clientCode);

}
