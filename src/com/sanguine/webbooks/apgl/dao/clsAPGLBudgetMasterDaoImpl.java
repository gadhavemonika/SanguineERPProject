package com.sanguine.webbooks.apgl.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.webbooks.apgl.model.clsAPGLBudgetModel;

@Repository("clsAPGLBudgetMasterDao")
public class clsAPGLBudgetMasterDaoImpl implements clsAPGLBudgetMasterDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	public List funGetBudgetTableData(String strMonth, String strYear, String strClientCode) {
		// String
		// sql="select a.strAccountCode,a.strAccountName,IFNULL(b.strMonth,''),IFNULL(b.strYear,''),IFNULL(b.dblBudgetAmt,0.0), IFNULL(b.intID,' ') from tblacmaster a "
		// +" left outer join tblbudget b on a.strAccountCode=b.strAccCode and a.strClientCode=b.strClientCode and b.strMonth='"+strMonth+"' and "
		// +" b.strYear='"+strYear+"' and a.strClientCode='"+strClientCode+"' and b.strClientCode='"+strClientCode+"'  order by b.strMonth,b.strMonth"
		// ;
		//

		String sql = "select a.strAccountCode,a.strAccountName,IFNULL(b.dblBudgetAmt,0.0), IFNULL(b.intID,' ') from tblacmaster a " + " left outer join tblbudget b on a.strAccountCode=b.strAccCode and a.strClientCode=b.strClientCode where b.strMonth='" + strMonth + "' and " + " b.strYear='" + strYear + "' and a.strClientCode='" + strClientCode + "' and b.strClientCode='" + strClientCode
				+ "'  order by b.strMonth,b.strMonth";
		List list = webBooksSessionFactory.getCurrentSession().createSQLQuery(sql).list();
		return list;
	}

	public void funSaveBudgetTableData(clsAPGLBudgetModel objBudgetModel) {

		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objBudgetModel);
	}

}
