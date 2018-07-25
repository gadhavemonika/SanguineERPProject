package com.sanguine.webpms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.model.clsBaggageMasterModel;
import com.sanguine.webpms.model.clsPMSSettlementMasterHdModel;

@Repository("clsPMSSettlementMasterDao")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsPMSSettlementMasterDaoImpl implements clsPMSSettlementMasterDao {
	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public void funAddUpdateSettlementMaster(clsPMSSettlementMasterHdModel objSettlementMasterModel) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objSettlementMasterModel);

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public List funGetPMSSettlementMaster(String settlementCode, String clientCode) {

		List list = null;
		try {
			Criteria cr = webPMSSessionFactory.getCurrentSession().createCriteria(clsPMSSettlementMasterHdModel.class);
			cr.add(Restrictions.eq("strSettlementCode", settlementCode));
			cr.add(Restrictions.eq("strClientCode", clientCode));
			list = cr.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

}
