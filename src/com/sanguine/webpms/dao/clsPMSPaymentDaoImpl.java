package com.sanguine.webpms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.model.clsPaymentHdModel;
import com.sanguine.webpms.model.clsPMSPaymentHdModel;

@Repository("clsPMSPaymentDao")
public class clsPMSPaymentDaoImpl implements clsPMSPaymentDao {
	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdatePaymentHd(clsPMSPaymentHdModel objHdModel) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objHdModel);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsPMSPaymentHdModel funGetPaymentModel(String receiptNo, String clientCode) {
		Criteria cr = webPMSSessionFactory.getCurrentSession().createCriteria(clsPMSPaymentHdModel.class);
		cr.add(Restrictions.eq("strReceiptNo", receiptNo));
		cr.add(Restrictions.eq("strClientCode", clientCode));
		List list = cr.list();
		clsPMSPaymentHdModel objModel = null;
		if (list.size() > 0) {
			objModel = (clsPMSPaymentHdModel) list.get(0);
			objModel.getListPaymentRecieptDtlModel().size();
		}
		return objModel;

	}
}
