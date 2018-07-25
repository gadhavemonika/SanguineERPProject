package com.sanguine.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsTreeMasterModel;
import com.sanguine.model.clsUserDtlModel;

@Repository("clsSecurityShellDao")
public class clsSecurityShellDaoImpl implements clsSecurityShellDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funAddUpdate(clsUserDtlModel objUserDtl) {
		sessionFactory.getCurrentSession().saveOrUpdate(objUserDtl);
	}

	@Override
	public List<clsTreeMasterModel> funGetFormList(String userCode, String strModuleNo) {
		String sql = "SELECT a.strFormName,a.strFormDesc,a.strType,a.strType,a.intFormKey,a.intFormNo," + "b.strAdd,b.strEdit,b.strDelete,b.strView,b.strPrint,b.strGrant,b.strAuthorise " + "FROM tbltreemast a " + "left outer join tbluserdtl b ON a.strFormName=b.strFormName and b.strUserCode='" + userCode + "' where  a.strModule='" + strModuleNo + "' order by a.strType, a.strFormName ";
		@SuppressWarnings("unchecked")
		List<clsTreeMasterModel> objTreeModel = (List<clsTreeMasterModel>) sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		return objTreeModel;

	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetForms(String userCode) {
		String sql = "SELECT a.strFormName,a.strFormDesc,a.strType,a.intFormKey,a.intFormNo,b.strAdd,b.strEdit,b.strDelete,b.strView,b.strPrint,b.strGrant,b.strAuthorise FROM tbltreemast a left outer join tbluserdtl b ON a.strFormName=b.strFormName and b.strUserCode='" + userCode + "'order by a.strType, a.strFormName ";
		return sessionFactory.getCurrentSession().createSQLQuery(sql).list();
	}

	@Override
	public void funDeleteForms(String userCode, String clientCode, String module) {
		Query query = sessionFactory.getCurrentSession().createQuery("DELETE clsUserDtlModel WHERE strUserCode= :userCode and strClientCode=:clientCode and strModule=:module");
		query.setParameter("userCode", userCode);
		query.setParameter("clientCode", clientCode);
		query.setParameter("module", module);
		query.executeUpdate();

	}

	@Override
	public List<clsTreeMasterModel> funGetFormList(String strModuleNo) {
		String sql = "SELECT a.strFormName,a.strFormDesc,a.strType,a.intFormKey,a.intFormNo " + "FROM tbltreemast a where a.strModule='" + strModuleNo + "' ";
		@SuppressWarnings("unchecked")
		List<clsTreeMasterModel> objTreeModel = (List<clsTreeMasterModel>) sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		return objTreeModel;
	}

}
