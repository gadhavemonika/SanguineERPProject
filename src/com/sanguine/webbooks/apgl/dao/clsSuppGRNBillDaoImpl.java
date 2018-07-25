package com.sanguine.webbooks.apgl.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.model.clsInvoiceHdModel;
import com.sanguine.webbooks.apgl.model.clsSundaryCrBillModel;
import com.sanguine.webbooks.apgl.model.clsSundaryCrBillModel_ID;

@Repository("clsSuppGRNBillDao")
public class clsSuppGRNBillDaoImpl implements clsSuppGRNBillDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	public void funAddUpdateSuppGRNBill(clsSundaryCrBillModel objMaster) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsSundaryCrBillModel funGetSuppGRNBill(String docCode, String clientCode) {
		return (clsSundaryCrBillModel) webBooksSessionFactory.getCurrentSession().get(clsSundaryCrBillModel.class, new clsSundaryCrBillModel_ID(docCode, clientCode));
	}

	@Override
	public clsSundaryCrBillModel funGetSundryCriditorDtl(String docCode, String clientCode) {
		Criteria cr = webBooksSessionFactory.getCurrentSession().createCriteria(clsSundaryCrBillModel.class);
		cr.add(Restrictions.eq("strVoucherNo", docCode));
		cr.add(Restrictions.eq("strClientCode", clientCode));
		List list = cr.list();
		clsSundaryCrBillModel objModel = null;
		if (list.size() > 0) {
			objModel = (clsSundaryCrBillModel) list.get(0);
			objModel.getListSCBillDtlModel().size();
			objModel.getListSCBillGRNDtlModel().size();
		}
		return objModel;
	}

}
