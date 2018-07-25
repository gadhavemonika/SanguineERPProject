package com.sanguine.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.dao.clsManufactureMasterDao;
import com.sanguine.model.clsManufactureMasterModel;

@Repository("clsManufactureMasterDao")
public class clsManufactureMasterDaoImpl implements clsManufactureMasterDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funAddUpdate(clsManufactureMasterModel objModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objModel);
	}

	@Override
	public clsManufactureMasterModel funGetObject(String strCode, String clientCode) {
		List list = new ArrayList();
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("from clsManufactureMasterModel where strManufacturerCode = '" + strCode + "' and strClientCode ='" + clientCode + "'");
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (list.size() > 0) {
			clsManufactureMasterModel objMaster = (clsManufactureMasterModel) list.get(0);
			return objMaster;
		} else {
			return null;
		}
	}

	@Override
	public Map<String, String> funGetManufacturerComboBox(String clientCode) {
		Map<String, String> map = new TreeMap<String, String>();
		Query query = sessionFactory.getCurrentSession().createQuery("from clsManufactureMasterModel where strClientCode=:clientCode");
		query.setParameter("clientCode", clientCode);
		List<clsManufactureMasterModel> subGroups = query.list();
		for (clsManufactureMasterModel subGroup : subGroups) {
			map.put(subGroup.getStrManufacturerCode(), subGroup.getStrManufacturerName());
		}
		return map;

	}

}
