package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubMemberPhotoModel;

public interface clsWebClubMemberPhotoService {

	public void funAddUpdateWebClubMemberPhoto(clsWebClubMemberPhotoModel objMaster);

	public clsWebClubMemberPhotoModel funGetWebClubMemberPhoto(String docCode, String clientCode);

}
