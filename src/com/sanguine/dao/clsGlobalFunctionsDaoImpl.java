package com.sanguine.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.bean.clsSecurityShellBean;
import com.sanguine.crm.bean.clsProductionComPilationBean;
import com.sanguine.model.clsAuditDtlModel;
import com.sanguine.model.clsAuditGRNTaxDtlModel;
import com.sanguine.model.clsAuditHdModel;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsPropertyMaster;
import com.sanguine.model.clsSupplierMasterModel;
import com.sanguine.model.clsTreeMasterModel;
import com.sanguine.model.clsUserLogsModel;
import com.sanguine.webbooks.model.clsCurrentAccountBalMaodel;
import com.sanguine.webbooks.model.clsLedgerSummaryModel;

@Repository("clsGlobalFunctionsDao")
public class clsGlobalFunctionsDaoImpl implements clsGlobalFunctionsDao {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	HttpServletRequest req;
	@Autowired
	private SessionFactory exciseSessionFactory;

	SessionFactory sessionFactory1 = null;

	// @Autowired
	// private SessionFactory CRMSessionFactory;

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Autowired
	private SessionFactory webPMSSessionFactory;
	final static Logger logger = Logger.getLogger(clsGlobalFunctionsDaoImpl.class);

	@SuppressWarnings("finally")
	public long funGetLastNo(String tableName, String masterName, String columnName, String clientCode) {
		long lastNo = 0;
		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
			sessionFactory1 = sessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {
			sessionFactory1 = exciseSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {
			sessionFactory1 = sessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("4-WebClub")) {
			sessionFactory1 = WebClubSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("5-WebBookAR") || req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL") || req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("5-WebBook")) {
			sessionFactory1 = webBooksSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("3-WebPMS")) {
			sessionFactory1 = webPMSSessionFactory;
		}

		try {
			@SuppressWarnings("rawtypes")
			List listLastNo = sessionFactory1.getCurrentSession().createSQLQuery("select ifnull(max(" + columnName + "),0),count(" + columnName + ") from " + tableName + " where strClientCode='" + clientCode + "'").list();
			Object[] ob = (Object[]) listLastNo.get(0);
			lastNo = Long.parseLong(ob[0].toString());
			lastNo++;

		} catch (Exception e) {
			lastNo = 0;
			logger.error(e);
			e.printStackTrace();
		} finally {
			return lastNo;
		}
	}

	public long funGetLastNoModuleWise(String tableName, String masterName, String columnName, String clientCode, String module) {
		long lastNo = 0;
		if (module.equalsIgnoreCase("1-WebStocks")) {
			sessionFactory1 = sessionFactory;
		} else if (module.equalsIgnoreCase("2-WebExcise")) {
			sessionFactory1 = exciseSessionFactory;
		} else if (module.equalsIgnoreCase("6-WebCRM")) {
			sessionFactory1 = sessionFactory;
		} else if (module.equalsIgnoreCase("4-WebClub")) {
			sessionFactory1 = WebClubSessionFactory;
		} else if (module.equalsIgnoreCase("5-WebBookAR") || module.equalsIgnoreCase("8-WebBookAPGL") || module.equalsIgnoreCase("5-WebBook")) {
			sessionFactory1 = webBooksSessionFactory;
		} else if (module.equalsIgnoreCase("3-WebPMS")) {
			sessionFactory1 = webPMSSessionFactory;
		}

		try {
			@SuppressWarnings("rawtypes")
			List listLastNo = sessionFactory1.getCurrentSession().createSQLQuery("select ifnull(max(" + columnName + "),0),count(" + columnName + ") from " + tableName + " where strClientCode='" + clientCode + "'").list();
			Object[] ob = (Object[]) listLastNo.get(0);
			lastNo = Long.parseLong(ob[0].toString());
			lastNo++;

		} catch (Exception e) {
			lastNo = 0;
			logger.error(e);
			e.printStackTrace();
		} finally {
			return lastNo;
		}
	}

	public long funGetMaxCountNo(String tableName, String masterName, String columnName, String clientCode) {
		long lastNo = 0;
		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
			sessionFactory1 = sessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {
			sessionFactory1 = exciseSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {
			sessionFactory1 = sessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("4-WebClub")) {
			sessionFactory1 = WebClubSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("5-WebBookAR") || req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL")) {
			sessionFactory1 = webBooksSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("3-WebPMS")) {
			sessionFactory1 = webPMSSessionFactory;
		}

		try {
			@SuppressWarnings("rawtypes")
			List listLastNo = sessionFactory1.getCurrentSession().createSQLQuery("select ifnull(count(" + columnName + "),0) from " + tableName + " where strClientCode='" + clientCode + "'").list();
			lastNo = Long.parseLong(listLastNo.get(0).toString());
			lastNo++;

		} catch (Exception e) {
			lastNo = 0;
			logger.error(e);
			e.printStackTrace();
		} finally {
			return lastNo;
		}
	}

	public Long funGetCount(String tableName, String columnName) {
		Long lastNo = new Long(0);
		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
			sessionFactory1 = sessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {
			sessionFactory1 = exciseSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {
			sessionFactory1 = sessionFactory;
		}
		try {
			@SuppressWarnings("rawtypes")
			List listLastNo = sessionFactory1.getCurrentSession().createSQLQuery("select ifnull(MAX(" + columnName + "),0) from " + tableName + "").list();
			String lastRecord = listLastNo.get(0).toString();
			lastNo = Long.parseLong(lastRecord);
			lastNo++;

		} catch (Exception e) {
			lastNo = new Long(0);
			logger.error(e);
			e.printStackTrace();
		}
		return lastNo;
	}

	public Long funGetCountByClient(String tableName, String columnName, String clientCode) {
		Long lastNo = new Long(0);
		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
			sessionFactory1 = sessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {
			sessionFactory1 = exciseSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {
			sessionFactory1 = sessionFactory;
		}
		try {
			@SuppressWarnings("rawtypes")
			List listLastNo = sessionFactory1.getCurrentSession().createSQLQuery("select ifnull(MAX(" + columnName + "),0),count(" + columnName + ") from " + tableName + " where strClientCode='" + clientCode + "'").list();
			Object[] ob = (Object[]) listLastNo.get(0);
			lastNo = Long.parseLong(ob[0].toString());
			lastNo++;

		} catch (Exception e) {
			lastNo = new Long(0);
			logger.error(e);
			e.printStackTrace();
		}
		return lastNo;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List funGetList(String sql, String queryType, String pageNo) {
		List listResult = null;
		Query query = null;

		SessionFactory sessionFactory1 = null;

		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
			sessionFactory1 = sessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {
			sessionFactory1 = exciseSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {
			sessionFactory1 = sessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("4-WebClub")) {
			sessionFactory1 = WebClubSessionFactory;
		}

		if (queryType.equalsIgnoreCase("hql")) {
			try {
				query = sessionFactory1.getCurrentSession().createQuery(sql);
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
			}

		} else {
			query = sessionFactory1.getCurrentSession().createSQLQuery(sql);

		}
		if ("1".equalsIgnoreCase(pageNo)) {
			query.setFirstResult(0);
		} else {
			query.setFirstResult(Integer.parseInt(pageNo) * 10 - 10);
		}
		query.setMaxResults(10);
		listResult = query.list();
		return listResult;
	}

	@Override
	public clsProductMasterModel funGetTaxAmount(String prodCode, String clientCode) {
		clsProductMasterModel objProdMasterModel = null;
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("from clsProductMasterModel " + "where strProdCode= :prodCode and strClientCode= :clientCode");
			query.setParameter("prodCode", prodCode);
			query.setParameter("clientCode", clientCode);
			@SuppressWarnings("rawtypes")
			List listTaxData = query.list();
			objProdMasterModel = (clsProductMasterModel) listTaxData.get(0);

			/*
			 * if(listTaxData.size()>0) { Object[] ob =
			 * (Object[])listTaxData.get(0);
			 * objProdMasterModel=(clsProductMasterModel)ob[0]; clsTaxHdModel
			 * tempTaxMaster=(clsTaxHdModel)ob[1]; String
			 * taxOnGD=tempTaxMaster.getStrTaxOnGD(); String
			 * taxCalculation=tempTaxMaster.getStrTaxCalculation(); String
			 * taxType=tempTaxMaster.getStrTaxType(); double
			 * taxableAmount=objProdMasterModel.getDblCostRM();
			 * taxPercentage=tempTaxMaster.getDblPercent();
			 * objProdMasterModel.setDblTaxPercentage(taxPercentage);
			 * objProdMasterModel.setStrTaxType(taxType);
			 * objProdMasterModel.setStrTaxOnGD(taxOnGD);
			 * objProdMasterModel.setStrTaxCalculation(taxCalculation); }
			 */
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return objProdMasterModel;
	}

	@SuppressWarnings({ "finally", "unchecked" })
	@Override
	public List<clsTreeMasterModel> funGetFormList() {
		List<clsTreeMasterModel> objTreeModel = null;
		try {
			String sql = "select * from tbltreemast order by strType,strFormName ";
			objTreeModel = (List<clsTreeMasterModel>) sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			return objTreeModel;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsTreeMasterModel> funGetFormList(String userCode) {
		String sql = "select * from tbltreemast a left outer join tblUserDtl b on a.strFormName=b.strFormName " + "where b.strUserCode = '" + userCode + "' " + "order by a.strType, a.strFormName ";
		List<clsTreeMasterModel> objTreeModel = (List<clsTreeMasterModel>) sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		return objTreeModel;
	}

	@SuppressWarnings({ "finally", "unchecked", "unused" })
	@Override
	public Map<String, List<clsSecurityShellBean>> funGetUserAccsDetails(String usercode) {
		Map<String, List<clsSecurityShellBean>> userdata = null;
		try {
			String hql_query = "select new com.sanguine.bean.clsSecurityShellBean (frmTable.strFormName, frmTable.strFormDesc" + ", userTable.strAdd, userTable.strEdit , userTable.strDelete , userTable.strView " + ", userTable.strPrint , userTable.strGrant , userTable.strAuthorise , userTable.strFormType " + ", userTable.strDesktop) "
					+ " from clsTreeMasterModel as frmTable ,clsSecurityShellMasterModel as userTable " + " where userTable.strUserCode =  '" + usercode + "'" + " and frmTable.strFormName = userTable.strFormName ";

			String hql1 = "select a.strFormName,a.strFormDesc,b.strAdd,b.strEdit from clsTreeMasterModel as a " + "left outer join clsSecurityShellMasterModel as b on a.strFormName=b.strFormName " + "where b.strUserCode = '" + usercode + "' " + "order by a.strType, a.strFormName ";

			String sql = "select * from tbltreemast a left outer join tbluserdtl b on a.strFormName=b.strFormName " + "where b.strUserCode = '" + usercode + "' " + "order by a.strType, a.strFormName ";

			List<clsSecurityShellBean> list = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
			userdata = new HashMap<String, List<clsSecurityShellBean>>();
		} catch (Exception e) {
			logger.error(e);
		} finally {
			return userdata;
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetPurchaseOrderList(String POCode, String clientCode) {
		String hql = "from clsPurchaseOrderDtlModel a,clsProductMasterModel b " + "where a.strPOCode = :poCode and a.strProdCode=b.strProdCode " + "and a.strClientCode= :clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("poCode", POCode);
		query.setParameter("clientCode", clientCode);
		@SuppressWarnings("rawtypes")
		List list = query.list();
		return list;
	}

	@Override
	public List funGetPurchaseReturnList(String PRCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsPurchaseReturnDtlModel a,clsProductMasterModel b " + "where a.strPRCode = :prCode and a.strProdCode=b.strProdCode and a.strClientCode= :clientCode");
		query.setParameter("prCode", PRCode);
		query.setParameter("clientCode", clientCode);
		@SuppressWarnings("rawtypes")
		List list = query.list();
		return list;
	}

	@Override
	public List funGetConsPOList(String consPOCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsConsPODtlModel a,clsProductMasterModel b " + "where a.strConsPOCode = :consPOCode and a.strProdCode=b.strProdCode and a.strClientCode= :clientCode");
		query.setParameter("consPOCode", consPOCode);
		query.setParameter("clientCode", clientCode);
		@SuppressWarnings("rawtypes")
		List list = query.list();
		return list;
	}

	@Override
	public int funAddCurrentStock(String clientCode, String userCode, String locCode, String stockableItem) {
		/*
		 * String hql=
		 * "insert into clsCurrentStockModel (strProdCode,strProdName,strLocCode,strClientCode,strUserCode) "
		 * + "select strProdCode,strProdName,'"+locCode+"','"+clientCode+"','"+
		 * userCode+"' from clsProductMasterModel ";
		 */

		// String
		// sql="insert into tblcurrentstock (strProdCode,strProdName,strLocCode,strClientCode,strUserCode) "
		// +
		// "select a.strProdCode,a.strProdName,'"+locCode+"','"+clientCode+"','"+userCode+"' "
		// + "from tblproductmaster a where "
		// + "(a.strProdCode IN (select distinct(strProdCode) from tblmisdtl ) "
		// +
		// "or a.strProdCode IN (select distinct(strProdCode) from tblgrndtl ) "
		// +
		// "or a.strProdCode IN (select distinct(strProdCode) from tblmaterialreturndtl) "
		// +
		// "or a.strProdCode IN (select distinct(strProdCode) from tblinitialinvdtl) "
		// +
		// "or a.strProdCode IN (select distinct(strProdCode) from tblstockadjustmentdtl) "
		// +
		// "or a.strProdCode IN (select distinct(strProdCode) from tblstocktransferdtl) "
		// +
		// "or a.strProdCode IN (select distinct(strProdCode) from tblpurchasereturndtl) "
		// +
		// "or a.strProdCode IN (select distinct(strProdCode) from tblproductiondtl)) ";

		String sql = "insert into tblcurrentstock (strProdCode,strProdName,strLocCode,strClientCode,strUserCode) " + " select a.strProdCode,a.strProdName,'" + locCode + "','" + clientCode + "','" + userCode + "' from tblproductmaster a ";

		if (stockableItem.equals("No")) {
			sql += " where a.strNonStockableItem='Y' ";
		}

		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		return query.executeUpdate();
	}

	@Override
	public int funUpdateCurrentStock(String hql) {
		/*
		 * Query query = sessionFactory.getCurrentSession().createQuery(hql);
		 * return query.executeUpdate();
		 */
		Query query = sessionFactory.getCurrentSession().createSQLQuery(hql);
		return query.executeUpdate();
	}

	@SuppressWarnings("finally")
	@Override
	public int funAddTempItemStock(String hql, String queryType) {
		Query query = null;
		int res = 0;
		try {
			if ("hql".equalsIgnoreCase(queryType)) {
				query = sessionFactory.getCurrentSession().createQuery(hql);
			} else if ("sql".equalsIgnoreCase(queryType)) {
				query = sessionFactory.getCurrentSession().createSQLQuery(hql);
			}
			res = query.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			return res;
		}
	}

	@Override
	public int funDeleteCurrentStock(String clientCode, String userCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete clsCurrentStockModel where strUserCode = :userCode and strClientCode= :clientCode");
		query.setParameter("clientCode", clientCode);
		query.setParameter("userCode", userCode);
		return query.executeUpdate();
	}

	@Override
	public int funDeleteTempItemStock(String clientCode, String userCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete clsTempItemStockModel where strUserCode = :userCode and strClientCode= :clientCode");
		query.setParameter("clientCode", clientCode);
		query.setParameter("userCode", userCode);
		return query.executeUpdate();
	}

	@Override
	public List funGetCurrentStock(String prodCode, String clientCode, String userCode) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select dblClosingStk from tblcurrentstock " + "where strProdCode=:prodCode and strUserCode =:userCode and strClientCode=:clientCode");
		query.setParameter("prodCode", prodCode);
		query.setParameter("clientCode", clientCode);
		query.setParameter("userCode", userCode);
		return query.list();
	}

	@Override
	public Map<String, String> funGetPropertyList(String clientCode) {
		Map<String, String> map = new HashMap<String, String>();
		String hql = "from clsPropertyMaster";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<clsPropertyMaster> mapProperty = query.list();
		for (clsPropertyMaster property : mapProperty) {
			map.put(property.getPropertyCode(), property.getPropertyName());
		}

		return map;
	}

	@SuppressWarnings("finally")
	@Override
	public Map<String, String> funGetUserWisePropertyList(String clientCode, String userCode, String userPrpoerty) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (!userPrpoerty.equals("ALL")) {
				String selectedMoule = req.getSession().getAttribute("selectedModuleName").toString();
				selectedMoule = selectedMoule.split("-")[1];
				if (selectedMoule.equalsIgnoreCase("WebBookAR") || selectedMoule.equalsIgnoreCase("WebBookAPGL")) {
					selectedMoule = "WebBooks";
				}
				String sql = " select a.strPropertyCode,a.strPropertyName,b.strModule " + " from tblpropertymaster a, tbluserlocdtl b " + " where a.strPropertyCode=b.strPropertyCode " + " and b.strUserCode='" + userCode + "' " + " and  a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + clientCode + "'" + " group by b.strModule, a.strPropertyCode  ";
				System.out.println(sql);
				Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
				List mapProperty = query.list();
				for (int i = 0; i < mapProperty.size(); i++) {
					Object[] ob = (Object[]) mapProperty.get(i);

					if (selectedMoule.contains(ob[2].toString())) {
						System.out.println("Prop Code=" + ob[0] + "\tName=" + ob[1]);
						map.put(ob[0].toString(), ob[1].toString());
					}
				}
				if (map.isEmpty()) {
					map.put("", "");
				}
			} else {
				String hql = "from clsPropertyMaster where strClientCode='" + clientCode + "'";
				System.out.println(hql);

				Query query = sessionFactory.getCurrentSession().createQuery(hql);
				List<clsPropertyMaster> mapProperty = query.list();
				for (clsPropertyMaster property : mapProperty) {
					map.put(property.getPropertyCode(), property.getPropertyName());
					System.out.println("Prop Code=" + property.getPropertyCode() + "\tName=" + property.getPropertyName());
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			return map;
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> funGetCompanyList(String clientCode) {
		Map<String, String> map = new HashMap<String, String>();
		String hql = "from clsCompanyMasterModel where strClientCode=:clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("clientCode", clientCode);
		List<clsCompanyMasterModel> mapCompany = query.list();

		for (clsCompanyMasterModel company : mapCompany) {
			map.put(company.getStrCompanyCode(), company.getStrCompanyName());
		}
		return map;
	}

	public clsCompanyMasterModel funGetCompanyObject(String companyCode) {
		clsCompanyMasterModel objComany = (clsCompanyMasterModel) sessionFactory.getCurrentSession().get(clsCompanyMasterModel.class, companyCode);
		return objComany;
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public HashMap<String, String> funGetLocationList(String propCode, String clientCode) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			String hql = "from clsLocationMasterModel where strPropertyCode=:PropCode and strClientCode=:clientCode ORDER BY strLocName";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setParameter("PropCode", propCode);
			query.setParameter("clientCode", clientCode);
			List<clsLocationMasterModel> mapLocation = query.list();
			for (clsLocationMasterModel location : mapLocation) {
				map.put(location.getStrLocCode(), location.getStrLocName());
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			return map;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public HashMap<String, String> funGetSupplierList(String propCode, String clientCode) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {

			String sql = "select strPCode,strPName from tblpartymaster " + "where strClientCode='" + clientCode + "' ORDER BY strPName";
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
			List listSupplier = query.list();
			for (int cnt = 0; cnt < listSupplier.size(); cnt++) {
				Object[] arrObjSupplier = (Object[]) listSupplier.get(cnt);
				map.put(arrObjSupplier[0].toString(), arrObjSupplier[1].toString());
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			return map;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public Map<Integer, String> funGetFinancialYearList(String clientCode) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		Map<Integer, String> mapSort = new TreeMap<Integer, String>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2.compareTo(o1);
			}

		});
		try {
			String hql = "from clsCompanyMasterModel where strClientCode=:clientCode ORDER BY intId DESC ";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setParameter("clientCode", clientCode);
			List<clsCompanyMasterModel> mapCompany = query.list();

			for (clsCompanyMasterModel company : mapCompany) {
				map.put(company.getIntId(), company.getStrFinYear());
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			mapSort.putAll(map);
			return mapSort;
		}
	}

	@SuppressWarnings({ "finally", "rawtypes" })
	public List funGetList(String query) {
		List list = null;
		try {
			list = sessionFactory.getCurrentSession().createSQLQuery(query).list();
			return list;
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			return list;
		}
	}

	@SuppressWarnings({ "finally", "rawtypes" })
	public List funGetProductDataForTransaction(String sql, String prodCode, String clientCode) {
		List list = new ArrayList();
		try {
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
			// query.setParameter("prodCode",prodCode);
			// query.setParameter("clientCode",clientCode);
			list = query.list();
			return list;
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			return list;
		}
	}

	@SuppressWarnings({ "rawtypes", "finally" })
	@Override
	public List funGetSetUpProcess(String strFrom, String strPropertyCode, String clientCode) {
		List<String> listProcess = new ArrayList<String>();
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("select strProcess from clsProcessSetupModel where strForm=:strFrom and strPropertyCode=:strPropertyCode and strClientCode=:clientCode ");
			query.setParameter("strFrom", strFrom);
			query.setParameter("strPropertyCode", strPropertyCode);
			query.setParameter("clientCode", clientCode);
			List list = query.list();

			String processes = list.get(0).toString();
			String str = processes;
			String delimiter = ",";
			String[] temp;
			temp = str.split(delimiter);
			for (int j = 0; j < temp.length; j++) {
				if (temp[j].toString().trim().length() > 0) {
					listProcess.add(temp[j].toString());
				}

			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			return listProcess;
		}
	}

	@SuppressWarnings({ "finally", "rawtypes" })
	public List funGetProdQtyForStock(String sql, String queryType) {
		Query query = null;
		List list = null;
		try {
			if ("hql".equalsIgnoreCase(queryType)) {
				query = sessionFactory.getCurrentSession().createQuery(sql);
			} else if ("sql".equalsIgnoreCase(queryType)) {
				query = sessionFactory.getCurrentSession().createSQLQuery(sql);
			}
			list = query.list();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			return list;
		}
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public long funGet_no_Of_Pages_forSearch(String colmnName, String tableName, boolean flgQuerySelection, String clientCode) {

		long count = 0;
		Query query = null;
		List listLastNo = null;

		SessionFactory sessionFactory1 = null;

		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
			sessionFactory1 = sessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {
			sessionFactory1 = exciseSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("3-WebPMS")) {
			sessionFactory1 = webPMSSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {
			sessionFactory1 = sessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("5-WebBookAR") || req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL")) {
			sessionFactory1 = webBooksSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("4-WebClub")) {
			sessionFactory1 = WebClubSessionFactory;
		}

		if (flgQuerySelection) {
			try {
				if (tableName.startsWith("select")) {
					query = sessionFactory1.getCurrentSession().createSQLQuery(tableName);
					listLastNo = query.list();
					count = listLastNo.size();
				} else {
					query = sessionFactory1.getCurrentSession().createSQLQuery("select count(" + colmnName + ")  " + tableName);
					listLastNo = query.list();

					count = (long) ((BigInteger) listLastNo.get(0)).longValue();
				}
			} catch (ClassCastException e) {

			} catch (Exception e) {

			}

		} else {
			query = sessionFactory1.getCurrentSession().createQuery("select count(" + colmnName + ") from " + tableName);
			listLastNo = query.list();
			count = (long) (listLastNo.get(0));
		}
		return count;
	}

	@SuppressWarnings({ "finally", "rawtypes" })
	@Override
	public List funGetList(String sql, String queryType) {

		List listResult = null;
		try {
			if (queryType.equalsIgnoreCase("hql")) {
				Query query = sessionFactory.getCurrentSession().createQuery(sql);
				listResult = query.list();
			} else {
				// System.out.println(sql.length());
				// System.out.println(sql.charAt(0));
				listResult = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			return listResult;
		}
	}

	@SuppressWarnings({ "finally", "rawtypes" })
	@Override
	public List funGetListModuleWise(String sql, String queryType) {

		List listResult = null;
		SessionFactory sesFacGlobal = null;
		try {
			if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
				sesFacGlobal = sessionFactory;
			} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {
				sesFacGlobal = exciseSessionFactory;
			} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("3-WebPMS")) {
				sesFacGlobal = webPMSSessionFactory;
			} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {
				sesFacGlobal = sessionFactory;
			} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("4-WebClub")) {
				sesFacGlobal = WebClubSessionFactory;
			} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("5-WebBookAR") || req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL")) {
				sesFacGlobal = webBooksSessionFactory;
			}

			if (queryType.equalsIgnoreCase("hql")) {
				Query query = sesFacGlobal.getCurrentSession().createQuery(sql);
				listResult = query.list();
			} else {
				listResult = sesFacGlobal.getCurrentSession().createSQLQuery(sql).list();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			return listResult;
		}
	}

	@SuppressWarnings({ "rawtypes", "finally" })
	@Override
	public List funCheckName(String name, String strClientCode, String formName) {
		String sql = "";
		List list = new ArrayList();
		try {
			switch (formName) {
			case "frmSubGroupMaster":
				sql = "select count(LOWER(strSGName)) from tblsubgroupmaster where strSGName='" + name + "' and strClientCode='" + strClientCode + "'";
				break;

			case "frmGroupMaster":
				sql = "select count(LOWER(strGName)) from tblgroupmaster where strGName='" + name + "' and strClientCode='" + strClientCode + "'";
				break;

			case "frmSubMenuHead":
				sql = "select count(LOWER(strSubMenuHeadCode)) from tblsubmenuhead where strSubMenuHeadName='" + name + "' and strClientCode='" + strClientCode + "'";
				break;

			case "frmMenuHead":
				sql = "select count(LOWER(strMenuCode)) from tblmenuhd where strMenuName='" + name + "' and strClientCode='" + strClientCode + "'";
				break;

			case "frmLocationMaster":
				sql = "select count(LOWER(strLocName)) from tbllocationmaster where strLocName='" + name + "' and strClientCode='" + strClientCode + "'";
				break;

			case "frmPropertyMaster":
				sql = "select count(LOWER(strPropertyName)) from tblpropertymaster where strPropertyName='" + name + "' and strClientCode='" + strClientCode + "'";
				break;

			case "frmProductMaster":
				sql = "select count(LOWER(strProdName)) from tblproductmaster where strProdName='" + name + "' and strClientCode='" + strClientCode + "'";
				break;

			case "frmReasonMaster":
				sql = "select count(LOWER(strReasonName)) from tblreasonmaster where strReasonName='" + name + "' and strClientCode='" + strClientCode + "'";
				break;

			case "frmSupplierMaster":
				sql = "select count(LOWER(strPName)) from tblpartymaster where strPName='" + name + "' and strClientCode='" + strClientCode + "'";
				break;

			case "frmTaxMaster":
				sql = "select count(LOWER(strTaxDesc)) from tbltaxhd where strTaxDesc='" + name + "' and strClientCode='" + strClientCode + "'";
				break;

			case "frmTCMaster":
				sql = "select count(LOWER(strTCName)) from tbltcmaster where strTCName='" + name + "' and strClientCode='" + strClientCode + "'";
				break;
			}
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
			list = query.list();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			return list;
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public int funCheckFromInWorkflowforSlabbasedAuth(String strForm, String strPropertyCode) {
		String sql = "select count(*) from tblworkflowforslabbasedauth where strFormName='" + strForm + "' and strPropertyCode='" + strPropertyCode + "'";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		List list = query.list();
		return Integer.parseInt(list.get(0).toString());
	}

	@SuppressWarnings("finally")
	@Override
	public int funAddUserLogEntry(clsUserLogsModel objModel) {
		int res = 0;
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(objModel);
			res = 1;
		} catch (Exception e) {
			res = 0;
			logger.error(e);
		} finally {
			return res;
		}

	}

	@Override
	public int funUpdate(String sql, String queryType) {
		int res = 0;
		try {
			if (queryType.equalsIgnoreCase("sql")) {

				Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
				res = query.executeUpdate();
			}
			if (queryType.equalsIgnoreCase("hql")) {
				Query query = sessionFactory.getCurrentSession().createQuery(sql);
				res = query.executeUpdate();
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return res;
	}

	@Override
	public int funUpdateAllModule(String sql, String queryType) {

		int res = 0;
		SessionFactory sesFacGlobal = null;
		try {

			if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
				sesFacGlobal = sessionFactory;
			} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {
				sesFacGlobal = exciseSessionFactory;
			} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("3-WebPMS")) {
				sesFacGlobal = webPMSSessionFactory;
			} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {
				sesFacGlobal = sessionFactory;
			} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("4-WebClub")) {
				sesFacGlobal = WebClubSessionFactory;
			} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("5-WebBookAR") || req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL")) {
				sesFacGlobal = webBooksSessionFactory;
			}

			if (queryType.equalsIgnoreCase("sql")) {

				Query query = sesFacGlobal.getCurrentSession().createSQLQuery(sql);
				res = query.executeUpdate();
			}
			if (queryType.equalsIgnoreCase("hql")) {
				Query query = sesFacGlobal.getCurrentSession().createQuery(sql);
				res = query.executeUpdate();
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return res;
	}

	@Override
	public void funSaveAuditDtl(clsAuditDtlModel auditDtlModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(auditDtlModel);

	}

	@Override
	public void funSaveAuditHd(clsAuditHdModel auditHdModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(auditHdModel);
	}

	@Override
	public void funSaveAuditTaxDtl(clsAuditGRNTaxDtlModel auditGRNTaxDtlModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(auditGRNTaxDtlModel);

	}

	@Override
	public List funGetDataList(String sql, String queryType) {
		List listResult = null;

		SessionFactory sessionFactory1 = null;

		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
			sessionFactory1 = sessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {
			sessionFactory1 = exciseSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("3-WebPMS")) {
			sessionFactory1 = webPMSSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {
			sessionFactory1 = sessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("4-WebClub")) {
			sessionFactory1 = WebClubSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("5-WebBookAR") || req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL")) {
			sessionFactory1 = webBooksSessionFactory;
		}

		if (queryType.equalsIgnoreCase("hql")) {
			try {
				Query query = sessionFactory1.getCurrentSession().createQuery(sql);
				listResult = query.list();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				listResult = sessionFactory1.getCurrentSession().createSQLQuery(sql).list();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return listResult;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List funGetListReportQuery(String sql) {
		final AliasToEntityMapResultTransformer INSTANCE = new AliasToEntityMapResultTransformer();
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(INSTANCE);
		List<Map<String, Object>> aliasToValueMapList = query.list();
		return aliasToValueMapList;
	}

	@SuppressWarnings("finally")
	@Override
	public long funGetPMSMasterLastNo(String tableName, String masterName, String columnName, String clientCode) {

		long lastNo = 0;
		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
			sessionFactory1 = sessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {
			sessionFactory1 = exciseSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {
			sessionFactory1 = sessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("4-WebClub")) {
			sessionFactory1 = WebClubSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("5-WebBookAR") || req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL")) {
			sessionFactory1 = webBooksSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("3-WebPMS")) {
			sessionFactory1 = webPMSSessionFactory;
		}

		try {
			@SuppressWarnings("rawtypes")
			String strCode = "";
			List listLastNo = sessionFactory1.getCurrentSession().createSQLQuery("select ifnull(max(" + columnName + "),0) from " + tableName + " where strClientCode='" + clientCode + "'").list();
			String code = (String) listLastNo.get(0);

			if (code.equals("0")) {
				lastNo = 1;
			} else {
				StringBuilder sb = new StringBuilder(code);
				String ss = sb.delete(0, 2).toString();

				for (int i = 0; i < ss.length(); i++) {
					if (ss.charAt(i) != '0') {
						strCode = ss.substring(i, ss.length());
						break;
					}
				}

				lastNo = Long.parseLong(strCode);
				lastNo++;

				/*
				 * if(ss.equalsIgnoreCase("000000")) { lastNo = 0; lastNo++;
				 * System.out.println("LastNo is"+lastNo); } else { for (int i =
				 * 0; i < ss.length(); i++) { if (ss.charAt(i) != '0') { strCode
				 * = ss.substring(i, ss.length()); break; } }
				 * 
				 * lastNo = Long.parseLong(strCode); lastNo++;
				 * 
				 * }
				 */
			}

		} catch (Exception e) {
			lastNo = 0;
			logger.error(e);
			e.printStackTrace();
		} finally {
			return lastNo;
		}
	}

	public int funExcuteQuery(String sql) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		return query.executeUpdate();

	}

	@SuppressWarnings("finally")
	@Override
	public long funGetNextNo(String tableName, String masterName, String columnName, String clientCode) {
		long nextNo = 0;
		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
			sessionFactory1 = sessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {
			sessionFactory1 = exciseSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {
			sessionFactory1 = sessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("4-WebClub")) {
			sessionFactory1 = WebClubSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("5-WebBookAR") || req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL")) {
			sessionFactory1 = webBooksSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("3-WebPMS")) {
			sessionFactory1 = webPMSSessionFactory;
		}

		try {
			@SuppressWarnings("rawtypes")
			List listLastNo = sessionFactory1.getCurrentSession().createSQLQuery("select ifnull(max(" + columnName + "),0) from " + tableName + " where strClientCode='" + clientCode + "'").list();
			String lastCode = "0";
			if (listLastNo.size() > 0) {
				lastCode = (String) listLastNo.get(0).toString();
			} else {
				lastCode = "0";
			}

			String num = "0";
			String regex = "(\\d+)";
			Matcher matcher = Pattern.compile(regex).matcher(lastCode);
			while (matcher.find()) {
				String n = matcher.group();
				num = num + n;
			}

			nextNo = Long.parseLong(num);
			nextNo = nextNo + 1;
			System.out.print("nextNo" + nextNo);

		} catch (Exception e) {
			nextNo = 0;
			logger.error(e);
			e.printStackTrace();
		} finally {
			return nextNo;
		}

	}

	public Long funGetCountAllModule(String tableName, String columnName) {
		Long lastNo = new Long(0);

		try {
			@SuppressWarnings("rawtypes")
			List listLastNo = exciseSessionFactory.getCurrentSession().createSQLQuery("select ifnull(MAX(" + columnName + "),0) from " + tableName + "").list();
			String lastRecord = listLastNo.get(0).toString();
			lastNo = Long.parseLong(lastRecord);
			lastNo++;

		} catch (Exception e) {
			lastNo = new Long(0);
			logger.error(e);
			e.printStackTrace();
		}
		return lastNo;
	}

	@Override
	public long funGetNextNo(String tableName, String masterName, String columnName, String clientCode, String condition) {
		long nextNo = 0;
		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
			sessionFactory1 = sessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {
			sessionFactory1 = exciseSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {
			sessionFactory1 = sessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("4-WebClub")) {
			sessionFactory1 = WebClubSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("5-WebBookAR") || req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("5-WebBookAPGL")) {
			sessionFactory1 = webBooksSessionFactory;
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("3-WebPMS")) {
			sessionFactory1 = webPMSSessionFactory;
		}

		try {
			@SuppressWarnings("rawtypes")
			List listLastNo = sessionFactory1.getCurrentSession().createSQLQuery("select ifnull(max(" + columnName + "),0) from " + tableName + " where strClientCode='" + clientCode + "' " + condition + " ").list();
			String lastCode = "0";
			if (listLastNo.size() > 0) {
				lastCode = (String) listLastNo.get(0).toString();
			} else {
				lastCode = "0";
			}

			String num = "0";
			String regex = "(\\d+)";
			Matcher matcher = Pattern.compile(regex).matcher(lastCode);
			while (matcher.find()) {
				String n = matcher.group();
				num = num + n;
			}

			nextNo = Long.parseLong(num);
			nextNo = nextNo + 1;
			System.out.print("nextNo" + nextNo);

		} catch (Exception e) {
			nextNo = 0;
			logger.error(e);
			e.printStackTrace();
		} finally {
			return nextNo;
		}
	}

	@Override
	public List funGetWebBooksAccountDtl(String accountCode, String clientCode) {

		List list = null;
		try {
			list = webBooksSessionFactory.getCurrentSession().createSQLQuery("select strAccountCode,strAccountName from tblacmaster where strAccountCode='" + accountCode + "' and strClientCode='" + clientCode + "' ").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return list;
		}

	}

	@Override
	public int funDeleteWebBookLedgerSummary(String clientCode, String userCode, String propertyCode) {
		Query query = webBooksSessionFactory.getCurrentSession().createQuery("delete clsLedgerSummaryModel where strUserCode = :userCode and strClientCode= :clientCode and strPropertyCode =:propertyCode ");
		query.setParameter("clientCode", clientCode);
		query.setParameter("userCode", userCode);
		query.setParameter("propertyCode", propertyCode);
		return query.executeUpdate();
	}

	public void funAddUpdateLedgerSummary(clsLedgerSummaryModel cobjLedgerSummaryModel) {
		if(null==cobjLedgerSummaryModel.getStrDebtorName())
		{
			cobjLedgerSummaryModel.setStrDebtorName("");
		}
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(cobjLedgerSummaryModel);
	}

	@Override
	public int funDeleteWebBookCurrentAccountBal(String clientCode, String userCode, String propertyCode) {
		Query query = webBooksSessionFactory.getCurrentSession().createQuery("delete clsCurrentAccountBalMaodel where strUserCode = :userCode and strClientCode= :clientCode and strPropertyCode =:propertyCode ");
		query.setParameter("clientCode", clientCode);
		query.setParameter("userCode", userCode);
		query.setParameter("propertyCode", propertyCode);
		return query.executeUpdate();
	}

	public void funAddUpdateCurrentAccountBalModel(clsCurrentAccountBalMaodel objclsCurrentAccountBalMaodel) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objclsCurrentAccountBalMaodel);
	}

	// @Override
	// public int funDeleteWebBookTempLedger(String clientCode,String
	// userCode,String propertyCode)
	// {
	// Query query =
	// webBooksSessionFactory.getCurrentSession().createQuery("delete clsTempLedgerModel where strUserCode = :userCode and strClientCode= :clientCode and strPropertyCode =:propertyCode ");
	// query.setParameter("clientCode", clientCode);
	// query.setParameter("userCode", userCode);
	// query.setParameter("userCode", userCode);
	// return query.executeUpdate();
	// }
	//
	// @Override
	// public int funAddCurrentDebtorCredtor(String clientCode,String
	// userCode,String propertyCode)
	// {
	// String sql =
	// "insert into tblcurrentledger (strDebtorCode,strDebtorName,strClientCode,strUserCode,strPropertyCode) "
	// + " select a.strDebtorCode,a.strDebtorName,'" + clientCode + "','" +
	// userCode + "','"+propertyCode+"' from tblsundarydebtormaster a ";
	// Query query =
	// webBooksSessionFactory.getCurrentSession().createSQLQuery(sql);
	// return query.executeUpdate();
	// }
	//
	public int funAddTempLedger(String hql, String queryType) {
		Query query = null;
		int res = 0;
		try {
			if ("hql".equalsIgnoreCase(queryType)) {
				query = webBooksSessionFactory.getCurrentSession().createQuery(hql);
			} else if ("sql".equalsIgnoreCase(queryType)) {
				query = webBooksSessionFactory.getCurrentSession().createSQLQuery(hql);
			}
			res = query.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			return res;
		}
	}

	@Override
	public double funGetCurrencyConversion(double amount, String currency, String clientCode) {
		double currencyConversion = amount;
		String sql = "select dblConvToBaseCurr from tblcurrencymaster where strShortName='" + currency + "' and strClientCode='" + clientCode + "' ";
		List list = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		if (list.size() > 0) {
			currencyConversion = Double.parseDouble(list.get(0).toString());
			currencyConversion = amount * currencyConversion;
		}

		return currencyConversion;
	}
}
