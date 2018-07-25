package com.sanguine.crm.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.model.clsPartyTaxIndicatorDtlModel;
import com.sanguine.crm.model.clsSubContractorMasterModel;

@Repository("clsSubContractorMasterDao")
public class clsSubContractorMasterDaoImpl implements clsSubContractorMasterDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Boolean funAddUpdate(clsSubContractorMasterModel objModel) {
		Boolean success = false;
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(objModel);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<clsSubContractorMasterModel> funGetList(String clientCode) {

		List<clsSubContractorMasterModel> retList = new ArrayList<clsSubContractorMasterModel>();
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("from clsSubContractorMasterModel where strClientCode='" + clientCode + "'");
			query.setParameter("strClientCode", clientCode);
			retList = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public clsSubContractorMasterModel funGetObject(String strPCode, String clientCode) {
		List list = new ArrayList();
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("from clsSubContractorMasterModel where strPCode = '" + strPCode + "' and strClientCode ='" + clientCode + "'");
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (list.size() > 0) {
			clsSubContractorMasterModel objclsSubConMaster = (clsSubContractorMasterModel) list.get(0);
			return objclsSubConMaster;
		} else {
			return null;
		}
	}

	@Override
	public Boolean funAddPartyTaxDtl(clsPartyTaxIndicatorDtlModel objPartyTaxIndicator) {
		// sessionFactory.getCurrentSession().save(objPartyTaxIndicator);
		return false;

	}

	@Override
	public void funDeletePartyTaxDtl(String partyCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete clsPartyTaxIndicatorDtlModel where strPCode = '" + partyCode + "' and strClientCode='" + clientCode + "'");
		int result = query.executeUpdate();

	}

}
