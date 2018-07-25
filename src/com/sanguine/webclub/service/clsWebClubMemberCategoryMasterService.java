package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubMemberCategoryMasterModel;

public interface clsWebClubMemberCategoryMasterService {

	public void funAddUpdateWebClubMemberCategoryMaster(clsWebClubMemberCategoryMasterModel objMaster);

	public clsWebClubMemberCategoryMasterModel funGetWebClubMemberCategoryMaster(String docCode, String clientCode);

}
