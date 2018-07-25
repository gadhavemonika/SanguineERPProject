package com.sanguine.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.model.clsUOMModel;

@Repository("clsUOMDao")
public class clsUOMDaoImpl implements clsUOMDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public void funSaveOrUpdateUOM(clsUOMModel obj) {
		sessionFactory.getCurrentSession().saveOrUpdate(obj);

	}

	@Override
	public List funGetUOMObject(String clientCode, String UOM) {

		Query query = sessionFactory.getCurrentSession().createQuery("select strUOMName from clsUOMModel WHERE strClientCode='" + clientCode + "' and strUOMName='" + UOM + "' ");
		List list = query.list();
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List funGetUOMList(String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("select upper(strUOMName) from clsUOMModel WHERE strClientCode='" + clientCode + "' ");
		List list = query.list();
		// list.add(0, " ");
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void funDeleteUOM(String clientCode, String UOM) {

		String hql = "Delete from clsUOMModel WHERE strClientCode=:strClientCode and strUOMName=:UOM";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("strClientCode", clientCode);
		query.setString("UOM", UOM);
		query.executeUpdate();

	}

}
