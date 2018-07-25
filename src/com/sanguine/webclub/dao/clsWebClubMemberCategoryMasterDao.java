package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubMemberCategoryMasterModel;

public interface clsWebClubMemberCategoryMasterDao {

	public void funAddUpdateWebClubMemberCategoryMaster(clsWebClubMemberCategoryMasterModel objMaster);

	public clsWebClubMemberCategoryMasterModel funGetWebClubMemberCategoryMaster(String docCode, String clientCode);

}
