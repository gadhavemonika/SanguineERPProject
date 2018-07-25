package com.sanguine.webpms.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.model.clsPMSTaxMasterModel;
import com.sanguine.webpms.model.clsPMSTaxMasterModel_ID;

@Repository("clsPMSTaxMasterDao")
public class clsPMSTaxMasterDaoImpl implements clsPMSTaxMasterDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public void funAddUpdatePMSTaxMaster(clsPMSTaxMasterModel objMaster) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public clsPMSTaxMasterModel funGetPMSTaxMaster(String taxCode, String clientCode) {
		return (clsPMSTaxMasterModel) webPMSSessionFactory.getCurrentSession().get(clsPMSTaxMasterModel.class, new clsPMSTaxMasterModel_ID(taxCode, clientCode));
	}

	@Override
	public List<String> funGetPMSDepartments(String clientCode) {
		List<String> listDepartment = webPMSSessionFactory.getCurrentSession().createSQLQuery("select a.strDeptDesc from tbldepartmentmaster a where a.strOperational='Y' and a.strClientCode='" + clientCode + "' ").list();
		return listDepartment;
	}

	@Override
	public List<String> funGetIncomeHead(String clientCode) {
		List<String> listIncomeHead = webPMSSessionFactory.getCurrentSession().createSQLQuery("select a.strIncomeHeadDesc from tblincomehead a where  a.strClientCode='" + clientCode + "' ").list();
		return listIncomeHead;
	}

	@Override
	public List<String> funGetPMSTaxes(String clientCode) {
		List<String> listPMSTaxes = webPMSSessionFactory.getCurrentSession().createSQLQuery("select a.strTaxDesc from tbltaxmaster a where  a.strClientCode='" + clientCode + "' ").list();
		return listPMSTaxes;
	}

	@Override
	public List<String> funGetPMSTaxGroup(String clientCode) {
		List<String> listPMSTaxGroup = webPMSSessionFactory.getCurrentSession().createSQLQuery("select a.strTaxGroupDesc from tbltaxgroup a where  a.strClientCode='" + clientCode + "' ").list();
		return listPMSTaxGroup;
	}

	@Override
	public String funGetCodeFromName(String fieldToBeSeleted, String fieldName, String fromFieldNameValue, String tableName, String clientCode) {
		String code = webPMSSessionFactory.getCurrentSession().createSQLQuery("select " + fieldToBeSeleted + " from " + tableName + " where " + fieldName + "='" + fromFieldNameValue + "' and strClientCode='" + clientCode + "' ").list().get(0).toString();
		return code;
	}

	@Override
	public String funGetMasterName(String query) {
		String name = null;
		List<String> list = webPMSSessionFactory.getCurrentSession().createSQLQuery(query).list();
		if (list.size() > 0) {
			name = list.get(0).toString();
		} else {
			name = "";
		}
		return name;
	}
}
