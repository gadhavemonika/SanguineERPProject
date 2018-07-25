package com.sanguine.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.model.clsVehicleMasterModel;
import com.sanguine.model.clsVehicleMasterModel_ID;
import com.sanguine.model.clsVehicleRouteModel;
import com.sanguine.webbooks.model.clsWebBooksAccountMasterModel;

@Repository("clsVehicleMasterDao")
public class clsVehicleMasterDaoImpl implements clsVehicleMasterDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funAddUpdateVehicleMaster(clsVehicleMasterModel objMaster) {
		sessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsVehicleMasterModel funGetVehicleMaster(String docCode, String clientCode) {
		return (clsVehicleMasterModel) sessionFactory.getCurrentSession().get(clsVehicleMasterModel.class, new clsVehicleMasterModel_ID(docCode, clientCode));
	}

	@SuppressWarnings("finally")
	@Override
	public boolean funAddUpdateVehicleRouteMaster(clsVehicleRouteModel objVehRouteDtl) {
		boolean flgSave = false;
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(objVehRouteDtl);
			flgSave = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return flgSave;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsVehicleRouteModel> funGetListVehicleRouteModel(String docCode, String clientCode) {
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(clsVehicleRouteModel.class);
		cr.add(Restrictions.eq("strVehCode", docCode));
		cr.add(Restrictions.eq("strClientCode", clientCode));
		@SuppressWarnings("rawtypes")
		List list = cr.list();
		return list;

	}

	@SuppressWarnings("unchecked")
	@Override
	public clsVehicleMasterModel funGetVehicleCode(String vehNumber, String clientCode) {
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(clsVehicleMasterModel.class);
		cr.add(Restrictions.eq("strVehNo", vehNumber));
		cr.add(Restrictions.eq("strClientCode", clientCode));
		@SuppressWarnings("rawtypes")
		clsVehicleMasterModel listModel = (clsVehicleMasterModel) cr.list().get(0);
		return listModel;
	}

	public int funDeleteVehRouteDtl(String vehCode, String clientCode) {

		Query query = sessionFactory.getCurrentSession().createSQLQuery("delete from tblvehroutedtl where strVehCode =:vehCode and strClientCode =:clientCode");
		query.setParameter("vehCode", vehCode);
		query.setParameter("clientCode", clientCode);

		int result = query.executeUpdate();

		return result;

	}

}
