package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubPreMemberProfileModel;

public interface clsWebClubPreMemberProfileDao {

	public void funAddUpdateWebClubPreMemberProfile(clsWebClubPreMemberProfileModel objMaster);

	public clsWebClubPreMemberProfileModel funGetWebClubPreMemberProfile(String docCode, String clientCode);

}
