package com.sanguine.webpms.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.model.clsBillDtlModel;
import com.sanguine.webpms.model.clsBillHdModel;
import com.sanguine.webpms.model.clsBillModel_ID;
import com.sanguine.webpms.model.clsBillTaxDtlModel;

@Repository("clsBillDao")
public class clsBillDaoImpl implements clsBillDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public void funAddUpdateBillHd(clsBillHdModel objHdModel) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objHdModel);
	}

	public void funAddUpdateBillDtl(clsBillDtlModel objDtlModel) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objDtlModel);
	}

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public clsBillHdModel funLoadBill(String docCode, String clientCode) {
		clsBillHdModel hdModel = (clsBillHdModel) webPMSSessionFactory.getCurrentSession().get(clsBillHdModel.class, new clsBillModel_ID(docCode, clientCode));
		/*
		 * List<clsBillDtlModel> list = hdModel.getListBillDtlModels();
		 * hdModel.setListBillDtlModels(list); List<clsBillTaxDtlModel> list2 =
		 * hdModel.getListBillTaxDtlModels();
		 * hdModel.setListBillTaxDtlModels(list2); clsBillHdModel hdModelRet
		 * =hdModel;
		 */
		return hdModel;
	}

	public List<clsBillDtlModel> funLoadBillDtl(String docCode, String clientCode) {
		List<clsBillDtlModel> objListDtl = null;
		try {
			Session currentsess = webPMSSessionFactory.getCurrentSession();
			Query query = currentsess.createQuery("from clsBillDtlModel where strBillNo= :billNo and strClientCode= :clientCode ");
			query.setParameter("billNo", docCode);
			query.setParameter("clientCode", clientCode);
			objListDtl = query.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return objListDtl;
		}

	}

	@Override
	public void funDeleteBill(clsBillHdModel objBillHdModel) {
		webPMSSessionFactory.getCurrentSession().delete(objBillHdModel);
	}

}
