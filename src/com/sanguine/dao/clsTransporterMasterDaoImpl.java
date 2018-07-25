package com.sanguine.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsTransporterHdModel;
import com.sanguine.model.clsTransporterHdModel_ID;
import com.sanguine.model.clsTransporterModelDtl;

@Repository("clsTransporterMasterDao")
public class clsTransporterMasterDaoImpl implements clsTransporterMasterDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("finally")
	@Override
	public boolean funAddUpdateHd(clsTransporterHdModel object) {
		boolean saveFlg = false;
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(object);
			saveFlg = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return saveFlg;
		}
	}

	@Override
	public clsTransporterHdModel funGetTransporterMaster(String docCode, String clientCode) {
		return (clsTransporterHdModel) sessionFactory.getCurrentSession().get(clsTransporterHdModel.class, new clsTransporterHdModel_ID(docCode, clientCode));
	}

	@Override
	public void funAddTransporterDtl(clsTransporterModelDtl object) {
		boolean saveFlg = false;
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(object);
			saveFlg = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void funDeleteDtl(String transCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("delete from tbltransportermasterdtl " + " where strTransCode='" + transCode + "' and strClientCode='" + clientCode + "' ");
		query.executeUpdate();
	}

	@Override
	public List funListVehicle(String vehCode, String clientCode) {

		String sql = "select a.strTransCode ,a.strVehCode from tbltransportermasterdtl a where  a.strVehCode='" + vehCode + "' and a.strClientCode='" + clientCode + "' ";

		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		List list = query.list();
		return list;

	}

	@Override
	public List funGetTransporterMasterDtl(String transCode, String clientCode) {

		String sql = "select a.strVehCode,a.strVehNo  from tbltransportermasterdtl a where a.strTransCode='" + transCode + "' and a.strClientCode='" + clientCode + "' ";

		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		List list = query.list();
		return list;

	}

}
