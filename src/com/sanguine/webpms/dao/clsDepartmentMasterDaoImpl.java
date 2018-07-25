package com.sanguine.webpms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.sanguine.webbooks.model.clsBankMasterModel;
import com.sanguine.webpms.bean.clsDepartmentMasterBean;
import com.sanguine.webpms.model.clsDepartmentMasterModel;

@Repository("clsDeptMasterDao")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsDepartmentMasterDaoImpl implements clsDepartmentMasterDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateDeptMaster(clsDepartmentMasterModel objDeptMasterModel) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objDeptMasterModel);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public List funGetDepartmentMaster(String deptCode, String clientCode) {
		List list = null;
		try {
			Criteria cr = webPMSSessionFactory.getCurrentSession().createCriteria(clsDepartmentMasterModel.class);
			cr.add(Restrictions.eq("strDeptCode", deptCode));
			cr.add(Restrictions.eq("strClientCode", clientCode));
			list = cr.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
