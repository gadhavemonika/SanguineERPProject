package com.sanguine.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.model.clsProdAttMasterModel;
import com.sanguine.model.clsProdCharMasterModel;
import com.sanguine.model.clsProdProcessModel;
import com.sanguine.model.clsProdSuppMasterModel;
import com.sanguine.model.clsProdSuppMasterModel_ID;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsProductMasterModel_ID;
import com.sanguine.model.clsProductReOrderLevelModel;
import com.sanguine.model.clsSubGroupMasterModel;

@Repository("clsProductMasterDao")
public class clsProductMasterDaoImpl implements clsProductMasterDao {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<clsProductMasterModel> funGetList(String strClientCode) {
		String hql = "from clsProductMasterModel where strClientCode=:strClientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("strClientCode", strClientCode);
		List list = query.list();
		return (List<clsProductMasterModel>) list;
	}

	public clsProductMasterModel funGetObject(String prodCode, String clientCode) {
		return (clsProductMasterModel) sessionFactory.getCurrentSession().get(clsProductMasterModel.class, new clsProductMasterModel_ID(prodCode, clientCode));
	}

	@SuppressWarnings("rawtypes")
	public clsProductMasterModel funGetSupplierWiseObject(String strSuppCode, String prodCode, String clientCode) {
		// For Accoding to Bar Code checking length
		String barCode = "";
		if (prodCode.length() > 8) {
			barCode = " a.strBarCode =:prodCode ";
		} else {
			barCode = " a.strProdCode=:prodCode ";
		}
		String hql = "from clsProductMasterModel a, clsProdSuppMasterModel b where " + barCode + " and a.strProdCode=b.strProdCode and b.strSuppCode=:strSuppCode and a.strClientCode= :clientCode and b.strClientCode= :clientCode ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("strSuppCode", strSuppCode);
		query.setParameter("prodCode", prodCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		if (!list.isEmpty()) {
			Object[] ob = (Object[]) list.get(0);
			clsProductMasterModel objProdMasterModel = (clsProductMasterModel) ob[0];
			clsProdSuppMasterModel prodSuppMaster = (clsProdSuppMasterModel) ob[1];
			objProdMasterModel.setDblCostRM(prodSuppMaster.getDblLastCost());
			return objProdMasterModel;
		} else {
			return (clsProductMasterModel) sessionFactory.getCurrentSession().get(clsProductMasterModel.class, new clsProductMasterModel_ID(prodCode, clientCode));
		}
	}

	public List<clsProdSuppMasterModel> funGetProdSuppList(String prodCode, String clientCode) {
		String hql = "from clsProdSuppMasterModel a,clsSupplierMasterModel b " + "where a.strProdCode= :prodCode and a.strSuppCode=b.strPCode and a.strClientCode= :clientCode and b.strPType='Supp' ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("prodCode", prodCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return (List<clsProdSuppMasterModel>) list;
	}

	@SuppressWarnings("unchecked")
	public List<clsProdAttMasterModel> funGetProdAttributeList(String prodCode, String clientCode) {
		String hql = "from clsProdAttMasterModel a,clsAttributeMasterModel b " + "where a.strProdCode= :prodCode and a.strAttCode=b.strAttCode and a.strClientCode= :clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("prodCode", prodCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return (List<clsProdAttMasterModel>) list;
	}

	@SuppressWarnings("unchecked")
	public List<clsProductReOrderLevelModel> funGetProdReOrderList(String prodCode, String clientCode) {
		String hql = "from clsProductReOrderLevelModel a,clsLocationMasterModel b " + "where a.strProdCode= :prodCode and a.strLocationCode=b.strLocCode and a.strClientCode= :clientCode and b.strClientCode= :clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("prodCode", prodCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return (List<clsProductReOrderLevelModel>) list;
	}

	public List<clsProdCharMasterModel> funGetProdCharList(String prodCode, String clientCode) {
		String hql = "from clsProdCharMasterModel where strProdCode= :prodCode and strClientCode= :clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("prodCode", prodCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return (List<clsProdCharMasterModel>) list;
	}

	public List<clsProdProcessModel> funGetProdProcessList(String prodCode, String clientCode) {
		String hql = "from clsProdProcessModel a,clsProcessMasterModel b " + "where a.strProdProcessCode= :prodCode and a.strProcessCode=b.strProcessCode and a.strClientCode= :clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("prodCode", prodCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return (List<clsProdProcessModel>) list;
	}

	public List funGetAttrObject(String code, String clientCode) {
		String hql = "from clsAttributeMasterModel a,clsAttributeValueMasterModel b " + "where a.strAttCode = :attCode and a.strAttCode=b.strAttCode and a.strClientCode= :clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("attCode", code);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return list;
	}

	public void funAddUpdateGeneral(clsProductMasterModel objModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objModel);
	}

	public void funAddUpdateProdSupplier(clsProdSuppMasterModel objProdSuppModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objProdSuppModel);
	}

	public void funAddUpdateProdProcess(clsProdProcessModel objProdProcessModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objProdProcessModel);
	}

	public void funAddUpdateProdAttribute(clsProdAttMasterModel objProdAttrModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objProdAttrModel);
	}

	public void funAddUpdateProdChar(clsProdCharMasterModel objProdCharModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objProdCharModel);
	}

	public void funAddUpdateProdReOrderLvl(clsProductReOrderLevelModel objProdReOrderModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objProdReOrderModel);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsProdSuppMasterModel funGetProdSupp(String prodCode, String clientCode) {
		String sql = "from clsProdSuppMasterModel where strProdCode=:prodCode " + "and strClientCode=:clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("prodCode", prodCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return (clsProdSuppMasterModel) list.get(0);
	}

	@Override
	public int funDeleteProdSupp(String prodCode, String clientCode) {
		String hql = "delete clsProdSuppMasterModel where strProdCode = :prodCode and strClientCode= :clientCode ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("prodCode", prodCode);
		query.setParameter("clientCode", clientCode);
		return query.executeUpdate();
	}

	@Override
	public int funDeleteProdAttr(String prodCode, String clientCode) {
		String hql = "delete clsProdAttMasterModel where strProdCode = :prodCode and strClientCode= :clientCode ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("prodCode", prodCode);
		query.setParameter("clientCode", clientCode);
		return query.executeUpdate();
	}

	@Override
	public int funDeleteProdProcess(String prodCode, String clientCode) {
		String hql = "delete clsProdProcessModel where strProdCode = :prodCode and strClientCode= :clientCode ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("prodCode", prodCode);
		query.setParameter("clientCode", clientCode);
		return query.executeUpdate();
	}

	@Override
	public int funDeleteProdReorder(String prodCode, String clientCode) {
		String hql = "delete clsProductReOrderLevelModel where strProdCode = :prodCode and strClientCode= :clientCode ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("prodCode", prodCode);
		query.setParameter("clientCode", clientCode);
		return query.executeUpdate();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetdtlList(String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"select a.strProdCode, a.strProdName,c.strSGName," + "a.strUOM,a.dblCostRM,a.dblCostManu,a.dblListPrice,a.strLocCode,a.strSpecification,a.strBinNo " + "from clsProductMasterModel a , clsLocationMasterModel b , clsSubGroupMasterModel c" + " where a.strLocCode=b.strLocCode and a.strSGCode=c.strSGCode and a.strClientCode= :clientCode ");

		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetSuppdtlList(String prodCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsProductMasterModel a ,clsProdSuppMasterModel b " + "where a.strProdCode=b.strProdCode ");
		List list = query.list();
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String funGetProductName(String prodCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("select strProdName from clsProductMasterModel where strProdCode=:prodCode and strClientCode=:clientCode");
		query.setParameter("prodCode", prodCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		// Object[] obj=(Object[])list.get(0);
		String productName = list.get(0).toString();
		return productName;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Map<String, clsProductMasterModel> mapProductDetail(String clientCode) {
		Map<String, clsProductMasterModel> map = new HashMap<String, clsProductMasterModel>();
		Query query = sessionFactory.getCurrentSession().createQuery("select product.strProdCode,product from clsProductMasterModel product where  product.strClientCode=:clientCode");
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			String key = obj[0].toString();
			clsProductMasterModel ob = (clsProductMasterModel) obj[1];
			map.put(key, ob);
		}

		return map;
	}

	@Override
	public List<String> funGetTaxIndicator(String propertyCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("select tax.strTaxIndicator from clsTaxHdModel tax where tax.strPropertyCode=:propertyCode  and tax.strClientCode=:clientCode  GROUP BY tax.strTaxIndicator");
		query.setParameter("propertyCode", propertyCode);
		query.setParameter("clientCode", clientCode);
		@SuppressWarnings("unchecked")
		List<String> list = query.list();
		return list;
	}

	@Override
	public void funProductUpdateCostRM(Double dblCostRM, String strProCode, String strClientCode) {
		String upadateQuery = "update tblproductmaster set dblCostRM='" + dblCostRM + "' where strProdCode='" + strProCode + "' and strClientCode='" + strClientCode + "' ";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(upadateQuery);
		query.executeUpdate();
	}

	@SuppressWarnings("finally")
	@Override
	public clsProductMasterModel funGetImportedPOSItem(String strPartNo, String strProdName, String strClientCode, String sGCode, String locCode) {
		List list = null;
		try {
			String sql = "from clsProductMasterModel where strPartNo=:partNo and strProdName=:prodName and strSGCode=:sGCode and strLocCode=:locCode " + " and strClientCode=:clientCode";
			Query query = sessionFactory.getCurrentSession().createQuery(sql);
			query.setParameter("partNo", strPartNo);
			query.setParameter("prodName", strProdName);
			query.setParameter("clientCode", strClientCode);
			query.setParameter("sGCode", sGCode);
			query.setParameter("locCode", locCode);
			list = query.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (list.isEmpty()) {
				return null;

			} else {
				return (clsProductMasterModel) list.get(0);
			}
		}

	}

	@Override
	public clsProductMasterModel funGetBarCodeProductObject(String barCode, String clientCode) {
		String sql = "from clsProductMasterModel where strBarCode=:barCode " + "and strClientCode=:clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("barCode", barCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return (clsProductMasterModel) list.get(0);

	}

	@Override
	public void funDeleteProdChar(String prodCode, String charCode, String processCode, String strClientCode) {
		try {
			String deleteQuery = "delete from tblprodchar where strProdCode='" + prodCode + "'"
			// +
			// " and strCharCode='"+charCode+"' and strProcessCode='"+processCode+"' "
					+ "and strClientCode='" + strClientCode + "' ";
			Query query = sessionFactory.getCurrentSession().createSQLQuery(deleteQuery);
			query.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public List funGetProdChar(String prodCode, String clientCode) {
		// List<clsProdCharMasterModel> listProdCharModel=new
		// ArrayList<clsProdCharMasterModel>();
		String sql = "from clsProdCharMasterModel where strProdCode=:prodCode " + "and strClientCode=:clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("prodCode", prodCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		// if(!(list==null))
		// {
		// //clsProdCharMasterModel objModel= new clsProdCharMasterModel();
		// for(int i=0;i<list.size();i++){
		// clsProdCharMasterModel objModel=(clsProdCharMasterModel)list.get(i);
		// listProdCharModel.add(objModel);
		// }
		//
		//
		// }
		return list;

	}

	@Override
	public List funGetAllProduct(String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("" + "from clsProductMasterModel a where a.strClientCode= :clientCode ");

		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return list;

	}

	@Override
	public int funDeleteSuppProds(String suppCode, String clientCode) {
		String hql = "delete clsProdSuppMasterModel where strSuppCode = :suppCode and strClientCode= :clientCode ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("suppCode", suppCode);
		query.setParameter("clientCode", clientCode);
		return query.executeUpdate();
	}

	@Override
	public List funGetProdSuppWaiseProdList(String suppCode, String clientCode) {
		String sql = "select a.strProdCode,a.dblLastCost,a.dblMargin,a.dblStandingOrder from tblprodsuppmaster a,tblproductmaster b " + "where a.strSuppCode='" + suppCode + "' and a.strClientCode= '" + clientCode + "' and a.strProdCode=b.strProdCode order by b.strProdName ";
		List list = sessionFactory.getCurrentSession().createSQLQuery(sql).list();

		return list;
	}

	public List funGetProdSuppDtl(String prodCode, String strCustCode, String clientCode) {
		String sql = "select a.strProdCode,a.dblLastCost,a.dblMargin from tblprodsuppmaster a " + "where a.strSuppCode='" + strCustCode + "' and a.strProdCode='" + prodCode + "'and a.strClientCode= '" + clientCode + "' ";
		List list = sessionFactory.getCurrentSession().createSQLQuery(sql).list();

		return list;

	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetALLProducedlProduct(String strClientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("" + "from clsProductMasterModel a where a.strClientCode= :clientCode and a.strProdType<> 'Procured' ");

		query.setParameter("clientCode", strClientCode);
		List list = query.list();
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public clsProdSuppMasterModel funGetProdSuppMasterModel(String strCustCode, String prodCode, String clientCode) {
		return (clsProdSuppMasterModel) sessionFactory.getCurrentSession().get(clsProdSuppMasterModel.class, new clsProdSuppMasterModel_ID(strCustCode, prodCode, clientCode));
	}

	@Override
	public int funDeleteProdSuppWise(String custCode, String prodCode, String clientCode) {
		String hql = "delete clsProdSuppMasterModel where strSuppCode = :custCode  and strProdCode = :prodCode and strClientCode= :clientCode ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("custCode", custCode);
		query.setParameter("prodCode", prodCode);
		query.setParameter("clientCode", clientCode);
		return query.executeUpdate();
	}

	public List<clsProductMasterModel> funGetAllNonStockablProddSuppList(String clientCode) {
		String hql = "from clsProductMasterModel a " + "where a.strNonStockableItem= :strNonStockableItem and  a.strClientCode= :clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("strNonStockableItem", "Y");
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return (List<clsProductMasterModel>) list;
	}

	public List<clsProductMasterModel> funGetAllStockablProddSuppList(String clientCode) {
		String hql = "from clsProductMasterModel a " + "where a.strNonStockableItem= :strNonStockableItem and  a.strClientCode= :clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("strNonStockableItem", "N");
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return (List<clsProductMasterModel>) list;
	}

	@Override
	public List funGetSubGroupWiseProductList(String sgCode, String clientCode) {
		String hql = "from clsProductMasterModel a " + "where a.strSGCode= :strSGCode and  a.strClientCode= :clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("strSGCode", sgCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return list;
	}

	@Override
	public List funGetSubGroupNameWiseProductList(String sgName, String clientCode) {
		String hql = "from clsProductMasterModel a,clsSubGroupMasterModel b " + "where a.strSGCode= b.strSGCode and b.strSGName = :strSGName and  a.strClientCode= :clientCode and  b.strClientCode= :clientCode ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("strSGName", sgName);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return list;
	}

	@Override
	public int funDeleteProdReorderLoc(String prodCode, String locCode, String clientCode) {
		String hql = "delete clsProductReOrderLevelModel where strProdCode = :prodCode and strLocationCode =:locCode and strClientCode= :clientCode ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("prodCode", prodCode);
		query.setParameter("locCode", locCode);
		query.setParameter("clientCode", clientCode);
		return query.executeUpdate();
	}

	@Override
	public clsProductReOrderLevelModel funGetProdReOrderLvl(String prodCode, String locCode, String clientCode) {
		clsProductReOrderLevelModel objReorderModel = null;
		String hql = "from clsProductReOrderLevelModel a " + "where strProdCode= :strProdCode and a.strLocationCode= :strLocationCode and  a.strClientCode= :clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("strProdCode", prodCode);
		query.setParameter("strLocationCode", locCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		if (!list.isEmpty()) {
			objReorderModel = (clsProductReOrderLevelModel) list.get(0);
		}
		return objReorderModel;

	}

}
