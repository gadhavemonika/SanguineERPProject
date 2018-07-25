package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.model.clsWebClubEditOtherInfoModel;
import com.sanguine.webclub.model.clsWebClubEditOtherInfoModel_ID;

@Repository("clsWebClubEditOtherInfoDao")
public class clsWebClubEditOtherInfoDaoImpl implements clsWebClubEditOtherInfoDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubEditOtherInfo(clsWebClubEditOtherInfoModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubEditOtherInfoModel funGetWebClubEditOtherInfo(String docCode, String clientCode) {
		return (clsWebClubEditOtherInfoModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubEditOtherInfoModel.class, new clsWebClubEditOtherInfoModel_ID(docCode, clientCode));
	}

}
