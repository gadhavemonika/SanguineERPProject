package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubMemberPhotoModel;

public interface clsWebClubMemberPhotoDao {

	public void funAddUpdateWebClubMemberPhoto(clsWebClubMemberPhotoModel objMaster);

	public clsWebClubMemberPhotoModel funGetWebClubMemberPhoto(String docCode, String clientCode);

}
