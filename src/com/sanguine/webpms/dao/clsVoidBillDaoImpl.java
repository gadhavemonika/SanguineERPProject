package com.sanguine.webpms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.model.clsBillHdModel;
import com.sanguine.webpms.model.clsVoidBillHdModel;

@Repository("clsVoidBillDao")
public class clsVoidBillDaoImpl implements clsVoidBillDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Override
	@Transactional(value = "WebPMSTransactionManager", rollbackFor = { Exception.class })
	public clsBillHdModel funGetBillData(String roomNo, String strBillNo, String strClientCode) {
		
		Criteria criteria = webPMSSessionFactory.getCurrentSession().createCriteria(clsBillHdModel.class);
		criteria.add(Restrictions.eq("strRoomNo", roomNo));
		criteria.add(Restrictions.eq("strBillNo", strBillNo));
		criteria.add(Restrictions.eq("strClientCode", strClientCode));
		List list = criteria.list();
		return (clsBillHdModel) list.get(0);
	}

	@Override
	public void funUpdateVoidBillData(clsBillHdModel objBillModel, clsVoidBillHdModel objVoidHdModel) {
		webPMSSessionFactory.getCurrentSession().delete(objBillModel);
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objVoidHdModel);
	}

	@Override
	@Transactional
	public void funUpdateVoidBillItemData(clsBillHdModel objBillModel, clsVoidBillHdModel objVoidHdModel) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objBillModel);
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objVoidHdModel);
		
	}

	@Override
	public void funUpdateBillData(clsBillHdModel objBillModel) 
	{
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objBillModel);	
	}
	
	@Override
	public void funSaveVoidBillData(clsVoidBillHdModel objVoidHdModel) 
	{
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objVoidHdModel);	
	}
	
}
