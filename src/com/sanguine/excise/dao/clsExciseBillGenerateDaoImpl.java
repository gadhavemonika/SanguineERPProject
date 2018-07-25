package com.sanguine.excise.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("clsExciseBillGenerateDao")
public class clsExciseBillGenerateDaoImpl implements clsExciseBillGenerateDao {

	@Autowired
	private SessionFactory exciseSessionFactory;

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetList(String strFromDate, String strToDate, String clientCode) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetSalesDtlList(long intId, String clientCode) {
		List ls = null;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			String sql_Qry = "from clsExciseManualSaleDtlModel a, clsBrandMasterModel b, clsSizeMasterModel c " + " where a.intSalesHd='" + intId + "' and b.strSizeCode=c.strSizeCode and " + " a.strBrandCode=b.strBrandCode and a.strClientCode= '" + clientCode + "' " + " order by b.strSubCategoryCode,c.intQty desc ";
			Query query = currentSession.createQuery(sql_Qry);
			ls = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	@Override
	public void funDeleteSaleData(String strFromDate, String strToDate, String clientCode, String licenceCode) {
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createSQLQuery("DELETE FROM tblexcisesaledata WHERE " + " date(dteBillDate) >= '" + strFromDate + "' AND date(dteBillDate) <= '" + strToDate + "' and strClientCode='" + clientCode + "' and strLicenceCode='" + licenceCode + "' and strSourceEntry='Manual' ");
			query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public int funExecute(String sql) {
		int i = 0;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			i = currentSession.createSQLQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

}
