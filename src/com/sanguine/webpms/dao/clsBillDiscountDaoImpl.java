package com.sanguine.webpms.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.model.clsBillDiscountHdModel;
import com.sanguine.webpms.model.clsBillDiscountModel_ID;

@Repository("clsBillDiscountDao")
public class clsBillDiscountDaoImpl implements clsBillDiscountDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public void funAddUpdateBillDiscount(clsBillDiscountHdModel objMaster) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public clsBillDiscountHdModel funGetBillDiscount(String docCode, String clientCode) {
		return (clsBillDiscountHdModel) webPMSSessionFactory.getCurrentSession().get(clsBillDiscountHdModel.class, new clsBillDiscountModel_ID(docCode, clientCode));
	}

	public boolean funDeleteBillDiscount(String docCode, String clientCode) {
		boolean flgRet = false;
		try {
			Session currentSession = webPMSSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("delete clsBillDiscountHdModel where strBillNo = :BillNo and strClientCode = :clientCode");
			query.setParameter("BillNo", docCode);
			query.setParameter("clientCode", clientCode);
			int result = query.executeUpdate();
			flgRet = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return flgRet;
		}

	}

}
