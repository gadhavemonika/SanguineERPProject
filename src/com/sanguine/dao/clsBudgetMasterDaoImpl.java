package com.sanguine.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsBudgetMasterHdModel;
import com.sanguine.model.clsBudgetMasterHdModel_ID;

@Repository("clsBudgetMasterDao")
public class clsBudgetMasterDaoImpl implements clsBudgetMasterDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void funAddUpdate(clsBudgetMasterHdModel objBudgetMasterModel) {

		try {
			sessionFactory.getCurrentSession().saveOrUpdate(objBudgetMasterModel);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public clsBudgetMasterHdModel funGetBudget(String budgetCode, String clientCode) {
		return (clsBudgetMasterHdModel) sessionFactory.getCurrentSession().get(clsBudgetMasterHdModel.class, new clsBudgetMasterHdModel_ID(budgetCode, clientCode));
	}

	public void funDeleteBudgetDtl(String budgetCode, String clientCode) {

		Query query = sessionFactory.getCurrentSession().createSQLQuery("delete from tblbudgetmasterdtl " + " where strBudgetCode='" + budgetCode + "' and strClientCode='" + clientCode + "' ");
		query.executeUpdate();

	}

	public List funGetMasterData(String propCode, String ClientCode, String year, String strGroupCode) {

		Query query = sessionFactory.getCurrentSession().createSQLQuery("select b.* from tblbudgetmasterhd a ,tblbudgetmasterdtl b where a.strBudgetCode=b.strBudgetCode " + " and a.strPropertyCode=:propCode and a.strClientCode=:ClientCode and a.strFinYear=:year and b.strGroupCode=:strGroupCode ");

		query.setParameter("propCode", propCode);
		query.setParameter("ClientCode", ClientCode);
		query.setParameter("year", year);
		query.setParameter("strGroupCode", strGroupCode);

		List list = query.list();
		return list;
	}

}
