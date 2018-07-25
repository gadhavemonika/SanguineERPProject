package com.sanguine.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsInitialInventoryModel;
import com.sanguine.model.clsOpeningStkDtl;

@Repository("clsStockDao")
public class clsStockDaoImpl implements clsStockDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funAddUpdate(clsInitialInventoryModel object) {
		sessionFactory.getCurrentSession().saveOrUpdate(object);
	}

	@Override
	public void funAddUpdateDtl(clsOpeningStkDtl object) {
		sessionFactory.getCurrentSession().saveOrUpdate(object);
	}

	@Override
	public void funDeleteDtl(String opStkCode, String clientCode) {
		String sql = "delete clsOpeningStkDtl where strOpStkCode=:opStkCode " + "and strClientCode=:clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("opStkCode", opStkCode);
		query.setParameter("clientCode", clientCode);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<clsInitialInventoryModel> funGetList() {
		return (List<clsInitialInventoryModel>) sessionFactory.getCurrentSession().createCriteria(clsInitialInventoryModel.class).list();
	}

	@SuppressWarnings("rawtypes")
	public clsInitialInventoryModel funGetObject(String code, String clientCode) {
		// return (clsInitialInventoryModel)
		// sessionFactory.getCurrentSession().get(clsInitialInventoryModel.class,
		// code);
		String sql = "select a.strOpStkCode,a.strLocCode,b.strLocName,a.dtExpDate,a.dtCreatedDate,a.dtLastModified,a.strUserCreated,a.strUserModified,a.strConversionUOM " + "from clsInitialInventoryModel a,clsLocationMasterModel b " + "where a.strOpStkCode=:opStkCode " + "and a.strLocCode=b.strLocCode and a.strClientCode=:clientCode and b.strClientCode=:clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("opStkCode", code);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		clsInitialInventoryModel objHdModel = null;
		if (list.size() > 0) {
			objHdModel = new clsInitialInventoryModel();
			Object[] ob = (Object[]) list.get(0);
			objHdModel.setStrOpStkCode(ob[0].toString());
			objHdModel.setStrLocCode(ob[1].toString());
			objHdModel.setStrLocName(ob[2].toString());
			objHdModel.setDtExpDate(ob[3].toString());
			objHdModel.setDtCreatedDate(ob[4].toString());
			objHdModel.setDtLastModified(ob[5].toString());
			objHdModel.setStrUserCreated(ob[6].toString());
			objHdModel.setStrUserModified(ob[7].toString());
			objHdModel.setStrConversionUOM(ob[8].toString());
		}
		return objHdModel;
	}

	@SuppressWarnings("rawtypes")
	public List funGetDtlList(String code, String clientCode) {
		String sql = "select a.strProdCode,b.strProdName,a.dblQty,a.strUOM,a.dblCostPUnit,a.dblRevLvl" + ",a.strLotNo,a.strDisplyQty,a.dblLooseQty " + "from clsOpeningStkDtl a,clsProductMasterModel b " + "where a.strOpStkCode=:opStkCode " + "and a.strProdCode=b.strProdCode and a.strClientCode=:clientCode and b.strClientCode=:clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("opStkCode", code);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funInitialInventoryReport(String clientCode) {

		Query query = sessionFactory.getCurrentSession().createQuery("from clsInitialInventoryModel a , clsProductMasterModel b, clsLocationMasterModel c " + "where a.strProdCode=b.strProdCode and a.strLocCode =c.strLocCode and a.strClientCode =:clientCode and b.strClientCode=:clientCode and c.strClientCode=:clientCode ");

		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return list;

	}
}
