package com.sanguine.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.model.clsTCMasterModel;

@Repository("clsTCMasterDao")
public class clsTCMasterDaoImpl implements clsTCMasterDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public clsTCMasterModel funGetTCMaster(String tcCode, String clientCode) {
		String sql = "from clsTCMasterModel where strTCCode=:tcCode and strClientCode=:clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("tcCode", tcCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		if (list.size() > 0) {
			return (clsTCMasterModel) list.get(0);
		} else {
			return null;
		}
	}

	@Transactional
	public void funAddUpdate(clsTCMasterModel objTCMaster) {
		sessionFactory.getCurrentSession().saveOrUpdate(objTCMaster);
	}

	public List<clsTCMasterModel> funGetTCMasterList(String clientCode) {
		String sql = "from clsTCMasterModel where strClientCode=:clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("clientCode", clientCode);
		return (List<clsTCMasterModel>) query.list();
	}
}
