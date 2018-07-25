package com.sanguine.webpms.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.sanguine.webpms.model.clsFolioHdModel;

@Repository("clsFolioDao")
public class clsFolioDaoImpl implements clsFolioDao {
	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public void funAddUpdateFolioHd(clsFolioHdModel objHdModel) {
		String deleteSql = "delete from tblfoliohd " + " where strCheckInNo='" + objHdModel.getStrCheckInNo() + "' and strRoomNo='" + objHdModel.getStrRoomNo() + "' " + " and strClientCode='" + objHdModel.getStrClientCode() + "' ";
		webPMSSessionFactory.getCurrentSession().createSQLQuery(deleteSql).executeUpdate();

		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objHdModel);
	}

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public clsFolioHdModel funGetFolioList(String folioNo, String clientCode, String propertyCode) {
		Criteria cr = webPMSSessionFactory.getCurrentSession().createCriteria(clsFolioHdModel.class);
		cr.add(Restrictions.eq("strFolioNo", folioNo));
		cr.add(Restrictions.eq("strClientCode", clientCode));
		List list = cr.list();

		clsFolioHdModel objModel = null;
		if (list.size() > 0) {
			objModel = (clsFolioHdModel) list.get(0);
			objModel.getListFolioDtlModel().size();
			objModel.getListFolioTaxDtlModel().size();
		}
		return objModel;
	}

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public List funGetParametersList(String sqlParameters) {
		List listOfParameters = null;
		listOfParameters = webPMSSessionFactory.getCurrentSession().createSQLQuery(sqlParameters).list();
		return listOfParameters;
	}
}
