package com.sanguine.dao;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsCurrentStockModel;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProcessMasterModel;
import com.sanguine.model.clsPropertyMaster;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsStockFlashModel;
import com.sanguine.model.clsSubGroupMasterModel;

@Repository("clsStockFlashDao")
public class clsStockFlashDaoImpl implements clsStockFlashDao {
	@Autowired
	private SessionFactory sessionFactory;

	public List funGetStockFlashData(String sql, String clientCode, String userCode) {
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("clientCode", clientCode);
		query.setParameter("userCode", userCode);
		List list = query.list();
		return list;
	}

}
