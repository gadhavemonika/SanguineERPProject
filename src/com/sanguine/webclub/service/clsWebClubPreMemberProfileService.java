package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubPreMemberProfileModel;

public interface clsWebClubPreMemberProfileService {

	public void funAddUpdateWebClubPreMemberProfile(clsWebClubPreMemberProfileModel objMaster);

	public clsWebClubPreMemberProfileModel funGetWebClubPreMemberProfile(String docCode, String clientCode);

}
