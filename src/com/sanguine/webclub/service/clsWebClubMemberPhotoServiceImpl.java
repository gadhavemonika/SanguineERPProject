package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubMemberPhotoDao;
import com.sanguine.webclub.model.clsWebClubMemberPhotoModel;

@Service("clsWebClubMemberPhotoService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubMemberPhotoServiceImpl implements clsWebClubMemberPhotoService {
	@Autowired
	private clsWebClubMemberPhotoDao objWebClubMemberPhotoDao;

	@Override
	public void funAddUpdateWebClubMemberPhoto(clsWebClubMemberPhotoModel objMaster) {
		objWebClubMemberPhotoDao.funAddUpdateWebClubMemberPhoto(objMaster);
	}

	@Override
	public clsWebClubMemberPhotoModel funGetWebClubMemberPhoto(String docCode, String clientCode) {
		return objWebClubMemberPhotoDao.funGetWebClubMemberPhoto(docCode, clientCode);
	}

}
