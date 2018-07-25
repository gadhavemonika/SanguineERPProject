package com.sanguine.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.model.clsBatchHdModel;

@Repository("clsBatchProcessDao")
public class clsProductBatchDaoImpl implements clsProductBatchDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public void funSaveBatch(clsBatchHdModel batchModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(batchModel);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsBatchHdModel> funGetList(String clientCode, String strProdCode) {
		String sql = "from clsBatchHdModel where strProdCode=:strProdCode and strTransType='GRN' and strClientCode=:clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("strProdCode", strProdCode);
		query.setParameter("clientCode", clientCode);
		List<clsBatchHdModel> list = query.list();
		return list;
	}

}
