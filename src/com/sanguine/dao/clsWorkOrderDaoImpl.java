package com.sanguine.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsWorkOrderDtlModel;
import com.sanguine.model.clsWorkOrderHdModel;

@Repository("clsWorkOrderDao")
public class clsWorkOrderDaoImpl implements clsWorkOrderDao {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("finally")
	@Override
	public long funGetLastNo(String tableName, String masterName, String columnName) {

		long lastNo = 0;
		try {
			@SuppressWarnings("rawtypes")
			List listLastNo = sessionFactory.getCurrentSession().createSQLQuery("select max(" + columnName + ") from " + tableName).list();
			if (listLastNo.size() > 1) {
				lastNo = ((BigInteger) listLastNo.get(0)).longValue();
			}
			lastNo++;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return lastNo;
		}
	}

	@Override
	public void funAddWorkOrderHd(clsWorkOrderHdModel WoHd) {

		sessionFactory.getCurrentSession().saveOrUpdate(WoHd);

	}

	@Override
	public void funAddUpdateWorkOrderDtl(clsWorkOrderDtlModel WoDtl) {

		sessionFactory.getCurrentSession().saveOrUpdate(WoDtl);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsWorkOrderHdModel> funGetList() {

		return (List<clsWorkOrderHdModel>) sessionFactory.getCurrentSession().createCriteria(clsWorkOrderHdModel.class).list();
	}

	// @Override
	// public clsWorkOrderHdModel funGetObject(String code) {
	// return (clsWorkOrderHdModel)
	// sessionFactory.getCurrentSession().get(clsWorkOrderHdModel.class, code);
	// }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List funGetDtlList(String WoCode, String prodCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsWorkOrderDtlModel a, clsProdProcessModel b, clsProcessMasterModel c " + "where a.strWOCode = :woCode and  a.strProcessCode=b.strProcessCode and b.strProcessCode=c.strProcessCode " + "and b.strProdProcessCode=:prodCode and a.strClientCode= :clientCode");
		query.setParameter("woCode", WoCode);
		query.setParameter("prodCode", prodCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return list;
	}

	@Override
	public void funDeleteDtl(String WoCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete clsProductionDtlModel where strPDCode = :pdCode and strClientCode= :clientCode");
		query.setParameter("pdCode", WoCode);
		query.setParameter("clientCode", clientCode);
		int result = query.executeUpdate();
		System.out.println("Result=" + result);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<clsWorkOrderHdModel> funGetWOHdData(String WoCode, String clientCode) {
		List listWoHd = sessionFactory.getCurrentSession().createQuery("from clsWorkOrderHdModel a,clsProductMasterModel b where a.strWOCode='" + WoCode + "' and a.strClientCode='" + clientCode + "' and a.strProdCode=b.strProdCode").list();
		return listWoHd;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public clsWorkOrderHdModel funGetWOHd(String WoCode, String clientCode) {
		clsWorkOrderHdModel objWO = new clsWorkOrderHdModel();
		List listWoHd = sessionFactory.getCurrentSession().createQuery("from clsWorkOrderHdModel a where a.strWOCode='" + WoCode + "' and a.strClientCode='" + clientCode + "' ").list();
		if (!listWoHd.isEmpty()) {
			objWO = (clsWorkOrderHdModel) listWoHd.get(0);
		}

		return objWO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetProcessDet(String strWOCode, String strClientCode) {
		String sql = "", strProdCode, strProcessCode = "";
		int count = 0;
		double qty = 0, pendingQty = 0;
		Query query;
		List listProcessDtl1, listProcessDtl2;
		sql = "select count(*) from tblworkorderdtl  where strWOCode='" + strWOCode + "' and strClientCode='" + strClientCode + "'";
		query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		listProcessDtl1 = query.list();
		count = Integer.parseInt(listProcessDtl1.get(0).toString());
		listProcessDtl1.clear();
		sql = "from clsWorkOrderHdModel where strWOCode='" + strWOCode + "' and strClientCode='" + strClientCode + "'";
		query = sessionFactory.getCurrentSession().createQuery(sql);
		listProcessDtl1 = query.list();
		clsWorkOrderHdModel objHdWorkOrder = (clsWorkOrderHdModel) listProcessDtl1.get(0);
		strProdCode = objHdWorkOrder.getStrProdCode();
		qty = objHdWorkOrder.getDblQty();

		if (count > 0) {
			sql = "select strProcessCode from tblworkorderdtl where strWOCode='" + strWOCode + "' and strClientCode='" + strClientCode + "'";
			listProcessDtl2 = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
			// listProcessDtl2=query.list();

			if (null != listProcessDtl2) {
				strProcessCode = listProcessDtl2.get(0).toString();
			}
			//
			// clsWorkOrderDtlModel
			// objDtlWorkOrder=(clsWorkOrderDtlModel)listProcessDtl2.get(0);
			// strProcessCode=objDtlWorkOrder.getStrProcessCode();

			pendingQty = funQtyPendingForPdn(strProdCode, strProcessCode, strWOCode, strClientCode);

			sql = "select w.strProcessCode,strProcessName,strStatus,'" + pendingQty + "' as PendingQty" + " from tblworkorderdtl w left outer join tblprocessmaster p on w.strProcessCode=p.strProcessCode and p.strClientCode='" + strClientCode + "' " + " where w.strWOCode='" + strWOCode + "' and  w.strClientCode='" + strClientCode + "'";
			listProcessDtl2 = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		} else {
			sql = "select pp.strprocesscode,p.strProcessName,'Work In Progress','" + qty + "' " + "from tblprodprocess pp left outer join tblprocessmaster p on p.strProcessCode=pp.strProcessCode and  p.strClientCode='" + strClientCode + "'" + "where strprodcode=(select strProdcode from tblworkorderhd where strwocode='" + strWOCode + "' and strClientCode='" + strClientCode
					+ "') and strClientCode='" + strClientCode + "'";
			query = sessionFactory.getCurrentSession().createSQLQuery(sql);
			listProcessDtl2 = query.list();
		}

		return listProcessDtl2;
	}

	@SuppressWarnings("rawtypes")
	public double funQtyPendingForPdn(String strProdCode, String strProcessCode, String strWOCode, String strClientCode) {
		double dblsum, dblBal = 0, dblQty;
		Query query;
		List result1, result2;

		String sql = "SELECT IFNULL(sum(pd.dblQtyProd-pd.dblQtyRej),0) FROM tblproductiondtl AS pd INNER JOIN tblproductionhd AS ph ON pd.strPDCode = ph.strPDCode and ph.strClientCode='" + strClientCode + "' and ph.strWOCode='" + strWOCode + "'" + " where pd.strProdCode='" + strProdCode + "' and pd.strProcessCode='" + strProcessCode + "' and  pd.strClientCode='" + strClientCode + "'";
		query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		result1 = query.list();
		if (result1 != null) {
			dblsum = Float.parseFloat(result1.get(0).toString());
			sql = "from clsWorkOrderHdModel where strWOCode='" + strWOCode + "' and strClientCode='" + strClientCode + "'";
			query = sessionFactory.getCurrentSession().createQuery(sql);
			result2 = query.list();
			clsWorkOrderHdModel objHdWorkOrder = (clsWorkOrderHdModel) result2.get(0);
			dblQty = objHdWorkOrder.getDblQty();

			if (dblsum < dblQty) {
				dblBal = dblQty - dblsum;
			} else {
				dblBal = 0;
			}
		}

		return dblBal;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String funGetProdProcessStatus(String strProdCode, String strProcessCode, String strWoCode, String strClientCode) {
		String sql = "";
		float dblQty = 0, sumdblQty = 0;
		String strStatus = "";
		sql = "SELECT IFNULL(sum(pd.dblQtyProd-pd.dblQtyRej),0) FROM tblproductiondtl AS pd INNER JOIN tblproductionhd AS ph ON pd.strPDCode = ph.strPDCode and ph.strWOCode='" + strWoCode + "' and ph.strClientCode='" + strClientCode + "'  and ph.strAuthorise='Yes' " + " where pd.strProdCode='" + strProdCode + "' and pd.strProcessCode='" + strProcessCode + "'  and pd.strClientCode='"
				+ strClientCode + "'";
		// System.out.println("selectQuery\t"+sql);

		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);

		List sumQtylist = query.list();

		sumdblQty = Float.parseFloat(sumQtylist.get(0).toString());

		sql = "SELECT dblQty FROM tblworkorderhd where strWOCode='" + strWoCode + "' and strClientCode='" + strClientCode + "'";
		query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		List Qtylist = query.list();

		dblQty = Float.parseFloat(Qtylist.get(0).toString());
		// System.out.println("dblQty\t"+dblQty);
		if (sumdblQty >= dblQty) {
			strStatus = "Completed";
		} else {
			strStatus = "Work In Progress!";
		}
		return strStatus;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetProdProcess(String prodCode, String strClientCode) {

		Query query = sessionFactory.getCurrentSession().createQuery("from clsProdProcessModel a, clsProcessMasterModel b  where  a.strProcessCode=b.strProcessCode and a.strProdProcessCode=:prodCode and a.strClientCode=:clientCode and b.strClientCode=:clientCode");
		query.setParameter("prodCode", prodCode);
		query.setParameter("clientCode", strClientCode);
		List list = query.list();
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String funGetWOStatusforProduct(String strWOCode, String strClientCode) {

		int count = 0;
		String strStatus = "";
		try {

			String sql = " select w.strProdCode,p.strProcessCode,w.strWOCode " + " from tblworkorderhd w ,tblworkorderdtl p " + " where w.strWOCode='" + strWOCode + "' and w.strWOCode = p.strWOCode "

			+ " and w.strClientCode='" + strClientCode + "' " + " and p.strClientCode='" + strClientCode + "' ";
			List list = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
			;

			Object[] ob = (Object[]) list.get(0);

			String prodCode = ob[0].toString();
			String processCode = ob[1].toString();
			String wOCode = ob[2].toString();

			String Status = funGetProdProcessStatus(prodCode, processCode, wOCode, strClientCode);

			sql = "SELECT count(distinct '" + Status + "') FROM tblworkorderhd w inner join tblworkorderdtl p ON p.strWOcode = w.strWOcode and p.strClientCode='" + strClientCode + "' " + " where w.strWOCode='" + strWOCode + "' and w.strClientCode='" + strClientCode + "'";

			Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
			List Countlist = query.list();

			count = Integer.parseInt(Countlist.get(0).toString());
			if (count == 1) {
				sql = "SELECT distinct  '" + Status + "'" + " FROM tblworkorderhd w inner join tblworkorderdtl p ON p.strWOcode = w.strWOcode and p.strClientCode='" + strClientCode + "'" + "	 where w.strWOCode='" + strWOCode + "'  and w.strClientCode='" + strClientCode + "'";
				query = sessionFactory.getCurrentSession().createSQLQuery(sql);
				List Statuslist = query.list();

				strStatus = Statuslist.get(0).toString();
			} else {
				strStatus = "Work In Progress!";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return strStatus;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGenearteWOAgainstOPData(String oPCode, String soCCode, String strClientCode, String against) {
		String sql = "";

		if (against.equalsIgnoreCase("Production Order")) {
			if (soCCode.length() > 0) {
				sql = " select c.strProdCode,c.dblQty from tblproductionorderhd a,tblsalesorderhd b,tblsalesorderdtl c " + " where a.strCode=b.strSOCode and b.strSOCode=c.strSOCode and b.strSOCode='" + soCCode + "' " + " and b.strCloseSO='Y'  and a.strOPCode='" + strClientCode + "' and a.strClientCode='" + strClientCode + "' " + " and a.strClientCode='" + strClientCode + "'  ";
			} else {
				sql = "select b.strProdCode,b.dblQty from tblproductionorderhd a,tblproductionorderdtl  b " + " where a.strOPCode=b.strOPCode and a.strOPCode='" + oPCode + "' and a.strClientCode='" + strClientCode + "' " + " and a.strClientCode='" + strClientCode + "' ";
			}

		} else {

			sql = "select c.strProdCode,c.dblQty from tblsalesorderhd b,tblsalesorderdtl c where b.strSOCode=c.strSOCode and b.strSOCode='" + oPCode + "' " + " and b.strCloseSO='N'  and b.strClientCode='" + strClientCode + "' ";
		}

		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);

		List list = query.list();
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetRecipeList(String strParentCode, String strClientCode) {
		String sql = "select b.strBOMCode,b.strChildCode,b.dblQty" + " from tblbommasterhd a,tblbommasterdtl b " + " where a.strParentCode='" + strParentCode + "' and a.strBOMCode=b.strBOMCode " + " and a.strClientCode='" + strClientCode + "' and b.strClientCode='" + strClientCode + "' ";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		List list = query.list();
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetGeneratedWOAgainstOPData(String oPCode, String strClientCode) {
		String sql = "select a.strWOCode,a.strProdCode,c.strProdName,c.strReceivedUOM,a.dblQty, " + " a.strStatus,'Production' from tblworkorderhd a, tblworkorderdtl b,tblproductmaster c" + " where a.strWOCode=b.strWOCode and a.strProdCode=c.strProdCode and a.strSOCode='" + oPCode + "' " + " and a.strClientCode='" + strClientCode + "' and b.strClientCode='" + strClientCode
				+ "' and c.strClientCode='" + strClientCode + "'";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		List list = query.list();
		return list;
	}

}
