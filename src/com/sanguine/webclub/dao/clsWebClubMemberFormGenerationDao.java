package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubMemberFormGenerationModel;

public interface clsWebClubMemberFormGenerationDao {

	public void funAddUpdateWebClubMemberFormGeneration(clsWebClubMemberFormGenerationModel objMaster);

	public clsWebClubMemberFormGenerationModel funGetWebClubMemberFormGeneration(String docCode, String clientCode);

}
