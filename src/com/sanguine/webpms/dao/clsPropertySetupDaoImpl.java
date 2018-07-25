package com.sanguine.webpms.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.webpms.model.clsPropertySetupHdModel;
import com.sanguine.webpms.model.clsPropertySetupModel_ID;

@Repository("clsPropertySetupDao")
public class clsPropertySetupDaoImpl implements clsPropertySetupDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public void funAddUpdatePropertySetup(clsPropertySetupHdModel objMaster) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public clsPropertySetupHdModel funGetPropertySetup(String propertyCode, String clientCode) {
		return (clsPropertySetupHdModel) webPMSSessionFactory.getCurrentSession().get(clsPropertySetupHdModel.class, new clsPropertySetupModel_ID(propertyCode, clientCode));
	}

	@Override
	public List<clsCompanyMasterModel> funGetListCompanyMasterModel(String clientCode) {

		String sql = "from clsCompanyMasterModel where strClientCode=:clientCode order by intId desc";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("clientCode", clientCode);
		@SuppressWarnings("unchecked")
		List<clsCompanyMasterModel> list = query.list();
		return (List<clsCompanyMasterModel>) list;
	}

}
