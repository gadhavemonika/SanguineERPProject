package com.sanguine.crm.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.model.clsSalesOrderBOMModel;

@Repository("clsSalesOrderBOMDao")
public class clsSalesOrderBOMDaoImpl implements clsSalesOrderBOMDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("finally")
	@Override
	public boolean funAddUpdateSoBomHd(clsSalesOrderBOMModel objHdModel) {
		boolean flgSave = false;
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(objHdModel);
			flgSave = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			flgSave = false;
		} finally {
			return flgSave;
		}

	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List funGetListOfMainParent(String soCode, String clientCode) {

		List list = new ArrayList();
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("from clsSalesOrderBOMModel a,clsProductMasterModel b where " + "a.strSOCode = '" + soCode + "' and b.strProdCode= a.strParentCode and a.strProdCode=b.strProdCode and b.strClientCode ='" + clientCode + "' and a.strClientCode ='" + clientCode + "' group by a.strParentCode  ");
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List funGetListOnProdCode(String soCode, String prodCode, String clientCode) {
		List list = new ArrayList();
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("from clsSalesOrderBOMModel a,clsProductMasterModel b where " + "a.strSOCode = '" + soCode + "' and a.strParentCode= '" + prodCode + "' and a.strChildCode=b.strProdCode and b.strClientCode ='" + clientCode + "' and a.strClientCode ='" + clientCode + "' ORDER BY a.intindex ASC");
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void funDeleteSalesOrderBom(String soCode, String parentCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete clsSalesOrderBOMModel where strSOCode = :SOCode and strParentCode= :parentCode  and strClientCode= :clientCode");
		query.setParameter("SOCode", soCode);
		query.setParameter("parentCode", parentCode);
		query.setParameter("clientCode", clientCode);
		query.executeUpdate();

	}

	public void funDeleteSOBomOnParent(String soCode, String strParentCode, String clientCode) {
		try {

			Query query = sessionFactory.getCurrentSession().createQuery("delete clsSalesOrderBOMModel where strSOCode = :SOCode and  strParentCode = :strParentCode and strClientCode= :clientCode");
			query.setParameter("SOCode", soCode);
			query.setParameter("strParentCode", strParentCode);
			query.setParameter("clientCode", clientCode);
			query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List funGetListOfSOBom(String soCode, String clientCode) {
		List list = new ArrayList();
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("from clsSalesOrderBOMModel a,clsProductMasterModel b where " + "a.strSOCode = '" + soCode + "' and a.strChildCode=b.strProdCode and b.strClientCode ='" + clientCode + "' " + " and a.strClientCode ='" + clientCode + "' and b.strProdType != 'Sub-Contracted'  ");
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
