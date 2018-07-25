package com.sanguine.webbooks.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.model.clsLetterProcessingModel;
import com.sanguine.webbooks.model.clsLetterProcessingModel_ID;

@Repository("clsLetterProcessingDao")
public class clsLetterProcessingDaoImpl implements clsLetterProcessingDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	public void funAddUpdateLetterProcessing(clsLetterProcessingModel objMaster) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsLetterProcessingModel funGetLetterProcessing(String docCode, String clientCode) {
		return (clsLetterProcessingModel) webBooksSessionFactory.getCurrentSession().get(clsLetterProcessingModel.class, new clsLetterProcessingModel_ID(docCode, clientCode));
	}

	@Override
	public List funGetDebtoMemberList(String sqlQuery) {
		return webBooksSessionFactory.getCurrentSession().createQuery(sqlQuery).list();
	}

	@Override
	public void funClearLetterProcessing(String userCode) {
		Query query = webBooksSessionFactory.getCurrentSession().createQuery("delete from clsLetterProcessingModel where strUserCreated='" + userCode + "'");

		query.executeUpdate();
	}

}
