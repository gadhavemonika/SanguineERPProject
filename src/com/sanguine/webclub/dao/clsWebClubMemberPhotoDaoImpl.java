package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.model.clsWebClubMemberPhotoModel;
import com.sanguine.webclub.model.clsWebClubMemberPhotoModel_ID;

@Repository("clsWebClubMemberPhotoDao")
public class clsWebClubMemberPhotoDaoImpl implements clsWebClubMemberPhotoDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubMemberPhoto(clsWebClubMemberPhotoModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubMemberPhotoModel funGetWebClubMemberPhoto(String docCode, String clientCode) {
		return (clsWebClubMemberPhotoModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubMemberPhotoModel.class, new clsWebClubMemberPhotoModel_ID(docCode, clientCode));
	}

}
