package com.sanguine.crm.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsSettlementMasterModel;

@Repository("clsCRMSettlementMasterDao")
public class clsCRMSettlementMasterDaoImpl implements clsCRMSettlementMasterDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funAddUpdate(clsSettlementMasterModel objModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objModel);
	}

	@Override
	public clsSettlementMasterModel funGetObject(String strCode, String clientCode) {
		List list = new ArrayList();
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("from clsSettlementMasterModel where strSettlementCode = '" + strCode + "' and strClientCode ='" + clientCode + "'");
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (list.size() > 0) {
			clsSettlementMasterModel objMaster = (clsSettlementMasterModel) list.get(0);
			return objMaster;
		} else {
			return null;
		}
	}

	@Override
	public Map<String, String> funGetSettlementComboBox(String clientCode) {
		Map<String, String> map = new TreeMap<String, String>();
		Query query = sessionFactory.getCurrentSession().createQuery("from clsSettlementMasterModel where strClientCode=:clientCode");
		query.setParameter("clientCode", clientCode);
		List<clsSettlementMasterModel> subGroups = query.list();
		for (clsSettlementMasterModel subGroup : subGroups) {
			map.put(subGroup.getStrSettlementCode(), subGroup.getStrSettlementDesc());
		}
		return map;
	}

	@Override
	public clsSettlementMasterModel funGeSettlementObject(String strCode, String dteInvDate, String clientCode) {
		List list = new ArrayList();
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("from clsSettlementMasterModel where strSettlementCode = '" + strCode + "' and  dteInvDate='" + dteInvDate + "' and strClientCode ='" + clientCode + "'");
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (list.size() > 0) {
			clsSettlementMasterModel objMaster = (clsSettlementMasterModel) list.get(0);
			return objMaster;
		} else {
			return null;
		}
	}
}
