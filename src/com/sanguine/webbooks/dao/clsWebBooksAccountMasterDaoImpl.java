package com.sanguine.webbooks.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.webbooks.model.clsWebBooksAccountMasterModel;
import com.sanguine.webbooks.model.clsWebBooksAccountMasterModel_ID;

@Repository("clsWebBooksAccountMasterDao")
public class clsWebBooksAccountMasterDaoImpl implements clsWebBooksAccountMasterDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	public void funAddUpdateWebBooksAccountMaster(clsWebBooksAccountMasterModel objAccountMaster) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objAccountMaster);
	}

	@Override
	public List funGetWebBooksAccountMaster(String accountCode, String clientCode) {
		List list = null;
		try {
			Query query = webBooksSessionFactory.getCurrentSession().createQuery("from clsWebBooksAccountMasterModel a,clsACGroupMasterModel b " + "where a.strGroupCode=b.strGroupCode  and a.strClientCode=b.strClientCode and a.strClientCode=:clientCode " + "and a.strAccountCode=:accountCode ");

			query.setParameter("accountCode", accountCode);
			query.setParameter("clientCode", clientCode);

			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public clsWebBooksAccountMasterModel funGetAccountCodeAndName(String accountCode, String clientCode) {
		return (clsWebBooksAccountMasterModel) webBooksSessionFactory.getCurrentSession().get(clsWebBooksAccountMasterModel.class, new clsWebBooksAccountMasterModel_ID(accountCode, clientCode));
	}

	@Override
	public String funGetMaxAccountNo(String groupCode, String clientCode, String propertyCode) {
		Query sqlNextAccountNo = webBooksSessionFactory.getCurrentSession().createSQLQuery("select ifnull(max(strAccountCode),CONCAT('" + groupCode + "','-01-','00')) " + "from  tblacmaster " + "where strGroupCode='" + groupCode + "' and strClientCode='" + clientCode + "' ");

		return sqlNextAccountNo.list().get(0).toString();
	}

	@Override
	public clsWebBooksAccountMasterModel funGetAccountForNonDebtor(String accountCode, String clientCode) {
		Criteria cr = webBooksSessionFactory.getCurrentSession().createCriteria(clsWebBooksAccountMasterModel.class);
		cr.add(Restrictions.eq("strAccountCode", accountCode));
		cr.add(Restrictions.eq("strClientCode", clientCode));
		List list = cr.list();
		return (clsWebBooksAccountMasterModel) list.get(0);
	}

	@Override
	public List<clsWebBooksAccountMasterModel> funGetAccountForCashBank(String clientCode) {
		Criteria cr = webBooksSessionFactory.getCurrentSession().createCriteria(clsWebBooksAccountMasterModel.class);
		cr.add(Restrictions.eq("strClientCode", clientCode));
		cr.add(Restrictions.ne("strType", "GLCode"));
		List list = cr.list();
		return list;
	}

	@Override
	public List<clsWebBooksAccountMasterModel> funGetAccountForGLCode(String clientCode) {
		Criteria cr = webBooksSessionFactory.getCurrentSession().createCriteria(clsWebBooksAccountMasterModel.class);
		cr.add(Restrictions.eq("strClientCode", clientCode));
		cr.add(Restrictions.eq("strType", "GLCode"));
		List list = cr.list();
		return list;
	}

	@Override
	public List<clsWebBooksAccountMasterModel> funGetDebtorAccountList(String clientCode) {
		Criteria cr = webBooksSessionFactory.getCurrentSession().createCriteria(clsWebBooksAccountMasterModel.class);
		cr.add(Restrictions.eq("strClientCode", clientCode));
		cr.add(Restrictions.ne("strDebtor", "Yes"));
		List list = cr.list();
		return list;
	}

}
