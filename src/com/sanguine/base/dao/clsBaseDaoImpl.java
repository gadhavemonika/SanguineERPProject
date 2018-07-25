package com.sanguine.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.base.model.clsBaseModel;

@Repository("intfBaseDao")
@Transactional(value = "hibernateTransactionManager")
public class clsBaseDaoImpl implements intfBaseDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private SessionFactory webPMSSessionFactory;
	
	@Autowired
	private SessionFactory exciseSessionFactory;

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Autowired
	private SessionFactory webBooksSessionFactory;


	@Override
	public String funSave(clsBaseModel objBaseModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objBaseModel);
		return objBaseModel.getDocCode();
	}

	@Override
	public clsBaseModel funLoad(clsBaseModel objBaseModel, Serializable key) {
		return (clsBaseModel) sessionFactory.getCurrentSession().load(objBaseModel.getClass(), key);
	}

	@Override
	public clsBaseModel funGet(clsBaseModel objBaseModel, Serializable key) {
		return (clsBaseModel) sessionFactory.getCurrentSession().get(objBaseModel.getClass(), key);
	}

	@Override
	public List funLoadAll(clsBaseModel objBaseModel, String clientCode) {
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(objBaseModel.getClass());
		cr.add(Restrictions.eq("strClientCode", clientCode));

		return sessionFactory.getCurrentSession().createCriteria(objBaseModel.getClass()).list();
	}

	@Override
	public List funGetSerachList(String sql, String clientCode) throws Exception {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(sql);
		query.setParameter("clientCode", clientCode);

		return query.list();
	}

	public List funGetList(StringBuilder strQuery, String queryType) throws Exception {
		Query query;
		if (queryType.equals("sql")) {
			query = sessionFactory.getCurrentSession().createSQLQuery(strQuery.toString());
			return query.list();
		} else {
			query = sessionFactory.getCurrentSession().createQuery(strQuery.toString());
			return query.list();
		}
	}
	
	
	public List funGetListModuleWise(StringBuilder strQuery, String queryType, String moduleType) throws Exception {
		Query query;

		SessionFactory objSessionFactory = null;
		
		if (moduleType.equals("WebStocks")) {
			objSessionFactory = sessionFactory;
		} else if (moduleType.equals("WebExcise")) {
			objSessionFactory = exciseSessionFactory;
		} else if (moduleType.equals("WebPMS")) {
			objSessionFactory = webPMSSessionFactory;
		} else if (moduleType.equals("WebCRM")) {
			objSessionFactory = sessionFactory;
		} else if (moduleType.equals("WebClub")) {
			objSessionFactory = WebClubSessionFactory;
		} else if (moduleType.equals("WebBooks")) {
			objSessionFactory = webBooksSessionFactory;
		}
		
		if (queryType.equals("sql")) {
			query = objSessionFactory.getCurrentSession().createSQLQuery(strQuery.toString());
			return query.list();
		} else {
			query = objSessionFactory.getCurrentSession().createQuery(strQuery.toString());
			return query.list();
		}
	}
	

	public int funExecuteUpdate(String strQuery, String queryType) throws Exception {
		Query query;
		if (queryType.equalsIgnoreCase("sql")) {
			query = sessionFactory.getCurrentSession().createSQLQuery(strQuery);
			return query.executeUpdate();
		} else {
			query = sessionFactory.getCurrentSession().createQuery(strQuery);
			return query.executeUpdate();
		}
	}

	@Override
	public List funLoadAllPOSWise(clsBaseModel objBaseModel, String clientCode, String strPOSCode) throws Exception {
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(objBaseModel.getClass());
		cr.add(Restrictions.eq("strClientCode", clientCode));
		cr.add(Restrictions.eq("strPOSCode", strPOSCode));

		return sessionFactory.getCurrentSession().createCriteria(objBaseModel.getClass()).list();
	}

	@Override
	public List funLoadAllCriteriaWise(clsBaseModel objBaseModel, String criteriaName, String criteriaValue) {
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(objBaseModel.getClass());
		cr.add(Restrictions.eq(criteriaName, criteriaValue));

		return sessionFactory.getCurrentSession().createCriteria(objBaseModel.getClass()).list();
	}

	@Override
	public clsBaseModel funGetModelCriteriaWise(String sql, Map<String, String> hmParameters) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(sql);
		for (Map.Entry<String, String> entrySet : hmParameters.entrySet()) {
			query.setParameter(entrySet.getKey(), entrySet.getValue());
		}
		List list = query.list();

		clsBaseModel model = new clsBaseModel();
		if (list.size() > 0) {
			model = (clsBaseModel) list.get(0);

		}
		return model;
	}

	public String funGetValue(StringBuilder strQuery, String queryType) throws Exception {
		Query query;
		if (queryType.equals("sql")) {
			query = sessionFactory.getCurrentSession().createSQLQuery(strQuery.toString());
			return query.toString();
		} else {
			query = sessionFactory.getCurrentSession().createQuery(strQuery.toString());
			return query.toString();
		}
	}
	
	@Override
	public String funSaveForPMS(clsBaseModel objBaseModel) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objBaseModel);
		return objBaseModel.getDocCode();
	}

	@Override
	public clsBaseModel funLoadForPMS(clsBaseModel objBaseModel, Serializable key) {
		return (clsBaseModel) webPMSSessionFactory.getCurrentSession().load(objBaseModel.getClass(), key);
	}
	
	@Override
	public String funSaveForWebBooks(clsBaseModel objBaseModel) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objBaseModel);
		return objBaseModel.getDocCode();
	}
	
	public List funGetListForWebBooks(StringBuilder strQuery, String queryType) throws Exception {
		Query query;
		if (queryType.equals("sql")) {
			query = webBooksSessionFactory.getCurrentSession().createSQLQuery(strQuery.toString());
			return query.list();
		} else {
			query = webBooksSessionFactory.getCurrentSession().createQuery(strQuery.toString());
			return query.list();
		}
	}

	
	public void funExcecteUpdateModuleWise(StringBuilder strQuery, String queryType, String moduleType) throws Exception {
		Query query;

		SessionFactory objSessionFactory = null;
		
		if (moduleType.equals("WebStocks")) {
			objSessionFactory = sessionFactory;
		} else if (moduleType.equals("WebExcise")) {
			objSessionFactory = exciseSessionFactory;
		} else if (moduleType.equals("WebPMS")) {
			objSessionFactory = webPMSSessionFactory;
		} else if (moduleType.equals("WebCRM")) {
			objSessionFactory = sessionFactory;
		} else if (moduleType.equals("WebClub")) {
			objSessionFactory = WebClubSessionFactory;
		} else if (moduleType.equals("WebBooks")) {
			objSessionFactory = webBooksSessionFactory;
		}
		if (queryType.equals("sql")) {
			objSessionFactory.getCurrentSession().createSQLQuery(strQuery.toString()).executeUpdate();
		} else {
			objSessionFactory.getCurrentSession().createQuery(strQuery.toString()).executeUpdate();
		}
	}

//	@Override
//	public clsBaseModel funLoadForWebBooks(clsBaseModel objBaseModel, Serializable key) {
//		return (clsBaseModel) webBooksSessionFactory.getCurrentSession().load(objBaseModel.getClass(), key);
//	}

}
