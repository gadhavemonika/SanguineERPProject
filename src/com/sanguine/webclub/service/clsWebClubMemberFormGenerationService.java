package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubMemberFormGenerationModel;

public interface clsWebClubMemberFormGenerationService {

	public void funAddUpdateWebClubMemberFormGeneration(clsWebClubMemberFormGenerationModel objMaster);

	public clsWebClubMemberFormGenerationModel funGetWebClubMemberFormGeneration(String docCode, String clientCode);

}
