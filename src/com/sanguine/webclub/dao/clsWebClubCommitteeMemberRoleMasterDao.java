package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubCommitteeMemberRoleMasterModel;

public interface clsWebClubCommitteeMemberRoleMasterDao {

	public void funAddUpdateWebClubCommitteeMemberRoleMaster(clsWebClubCommitteeMemberRoleMasterModel objMaster);

	public clsWebClubCommitteeMemberRoleMasterModel funGetWebClubCommitteeMemberRoleMaster(String docCode, String clientCode);

	public clsWebClubCommitteeMemberRoleMasterModel funGetWebClubCommitteeMemberRoleName(String roleName, String clientCode);

}
