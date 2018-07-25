package com.sanguine.crm.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.crm.model.clsPartyMasterModel;
import com.sanguine.crm.model.clsSubContractorGRNModelDtl;
import com.sanguine.crm.model.clsSubContractorGRNModelHd;
import com.sanguine.crm.model.clsSubContractorGRNModelHd_ID;
import com.sanguine.model.clsLocationMasterModel;

@Repository("clsSubContractorGRNDao")
public class clsSubContractorGRNDaoImpl implements clsSubContractorGRNDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("finally")
	@Override
	public boolean funAddUpdateSubContractorGRNHd(clsSubContractorGRNModelHd objHdModel) {
		boolean flgSave = false;
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(objHdModel);
			flgSave = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return flgSave;
		}

	}

	public void funAddUpdateSubContractorGRNDtl(clsSubContractorGRNModelDtl objDtlModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objDtlModel);
	}

	public clsSubContractorGRNModelHd funGetSubContractorGRNHd(String strSRCode, String clientCode) {
		return (clsSubContractorGRNModelHd) sessionFactory.getCurrentSession().get(clsSubContractorGRNModelHd.class, new clsSubContractorGRNModelHd_ID(strSRCode, clientCode));
	}

	@Override
	public void funDeleteDtl(String strSRCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete clsSubContractorGRNModelDtl " + " where strSRCode=:strSRCode and strClientCode=:clientCode " + "and strClientCode=:clientCode");
		query.setParameter("strSRCode", strSRCode);
		query.setParameter("clientCode", clientCode);
		query.executeUpdate();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Object> funLoadSubContractorGRNHDData(String docCode, String clientCode) {
		List<Object> objSCGRNHDList = null;
		Query query = sessionFactory.getCurrentSession().createQuery(" from clsSubContractorGRNModelHd a,clsPartyMasterModel b, clsLocationMasterModel c " + "	where a.strSRCode=:SRCode and a.strLocCode=c.strLocCode and a.strSCCode=b.strPCode and a.strClientCode=:clientCode " + "and b.strClientCode=:clientCode ");

		query.setParameter("SRCode", docCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		if (list.size() > 0) {
			objSCGRNHDList = list;

		}
		return objSCGRNHDList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Object> funLoadSubContractorGRNDtlData(String docCode, String clientCode) {
		List<Object> objSCGRNDtlList = null;
		Query query = sessionFactory.getCurrentSession().createQuery(" from clsSubContractorGRNModelDtl a,clsProductMasterModel b " + "	where a.strSRCode=:SRCode and a.strProdCode= b.strProdCode and a.strClientCode=:clientCode " + "and b.strClientCode=:clientCode ");
		query.setParameter("SRCode", docCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();

		if (list.size() > 0) {
			objSCGRNDtlList = list;

		}

		return objSCGRNDtlList;
	}

}
