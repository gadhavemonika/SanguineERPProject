package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubCommitteeMemberRoleMasterModel;

public interface clsWebClubCommitteeMemberRoleMasterService {

	public void funAddUpdateWebClubCommitteeMemberRoleMaster(clsWebClubCommitteeMemberRoleMasterModel objMaster);

	public clsWebClubCommitteeMemberRoleMasterModel funGetWebClubCommitteeMemberRoleMaster(String docCode, String clientCode);

	public clsWebClubCommitteeMemberRoleMasterModel funGetWebClubCommitteeMemberRoleName(String roleName, String clientCode);

}
