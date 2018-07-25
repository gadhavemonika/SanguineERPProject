package com.sanguine.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.bean.clsSecurityShellBean;
import com.sanguine.dao.clsGlobalFunctionsDao;
import com.sanguine.model.clsAuditDtlModel;
import com.sanguine.model.clsAuditGRNTaxDtlModel;
import com.sanguine.model.clsAuditHdModel;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsTreeMasterModel;
import com.sanguine.model.clsUserLogsModel;
import com.sanguine.webbooks.model.clsCurrentAccountBalMaodel;
import com.sanguine.webbooks.model.clsLedgerSummaryModel;

@Service("objGlobalFunctionsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsGlobalFunctionsServiceImpl implements clsGlobalFunctionsService {
	@Autowired
	private clsGlobalFunctionsDao objGlobalDao;

	public long funGetLastNo(String tableName, String masterName, String columnName, String clientCode) {
		return objGlobalDao.funGetLastNo(tableName, masterName, columnName, clientCode);
	}

	public Long funGetCount(String tableName, String columnName) {
		return objGlobalDao.funGetCount(tableName, columnName);
	}

	public Long funGetCountByClient(String tableName, String columnName, String clientCode) {
		return objGlobalDao.funGetCountByClient(tableName, columnName, clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetList(String sql, String queryType, String pageNo) {
		return objGlobalDao.funGetList(sql, queryType, pageNo);
	}

	@Override
	public clsProductMasterModel funGetTaxAmount(String prodCode, String clientCode) {
		return objGlobalDao.funGetTaxAmount(prodCode, clientCode);
	}

	@Override
	public Map<String, List<clsSecurityShellBean>> funGetUserAccsDetails(String usercode) {
		return objGlobalDao.funGetUserAccsDetails(usercode);
	}

	@Override
	public List<clsTreeMasterModel> funGetFormList(String userCode) {
		return objGlobalDao.funGetFormList(userCode);
	}

	@Override
	public List<clsTreeMasterModel> funGetFormList() {
		return objGlobalDao.funGetFormList();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetPurchaseOrderList(String POCode, String clientCode) {
		return objGlobalDao.funGetPurchaseOrderList(POCode, clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetConsPOList(String consPOCode, String clientCode) {
		return objGlobalDao.funGetConsPOList(consPOCode, clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetPurchaseReturnList(String PRCode, String clientCode) {
		return objGlobalDao.funGetPurchaseReturnList(PRCode, clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetCurrentStock(String prodCode, String clientCode, String userCode) {
		return objGlobalDao.funGetCurrentStock(prodCode, clientCode, userCode);
	}

	@Override
	public int funAddCurrentStock(String clientCode, String userCode, String locCode, String stockableItem) {
		return objGlobalDao.funAddCurrentStock(clientCode, userCode, locCode, stockableItem);
	}

	@Override
	public int funUpdateCurrentStock(String hql) {
		return objGlobalDao.funUpdateCurrentStock(hql);
	}

	@Override
	public int funAddTempItemStock(String hql, String queryType) {

		return objGlobalDao.funAddTempItemStock(hql, queryType);
	}

	@Override
	public int funDeleteCurrentStock(String clientCode, String userCode) {
		return objGlobalDao.funDeleteCurrentStock(clientCode, userCode);
	}

	@Override
	public int funDeleteTempItemStock(String clientCode, String userCode) {
		return objGlobalDao.funDeleteTempItemStock(clientCode, userCode);
	}

	@Override
	public Map<String, String> funGetCompanyList(String clientCode) {
		return objGlobalDao.funGetCompanyList(clientCode);
	}

	@Override
	public Map<String, String> funGetPropertyList(String clientCode) {
		return objGlobalDao.funGetPropertyList(clientCode);
	}

	@Override
	public HashMap<String, String> funGetLocationList(String propCode, String clientCode) {
		return objGlobalDao.funGetLocationList(propCode, clientCode);
	}

	@Override
	public HashMap<String, String> funGetSupplierList(String propCode, String clientCode) {
		return objGlobalDao.funGetSupplierList(propCode, clientCode);
	}

	@Override
	public Map<Integer, String> funGetFinancialYearList(String clientCode) {
		return objGlobalDao.funGetFinancialYearList(clientCode);
	}

	@Override
	public clsCompanyMasterModel funGetCompanyObject(String companyCode) {
		return objGlobalDao.funGetCompanyObject(companyCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetList(String query) {
		return objGlobalDao.funGetList(query);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetProductDataForTransaction(String sql, String prodCode, String clientCode) {
		return objGlobalDao.funGetProductDataForTransaction(sql, prodCode, clientCode);
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List funGetSetUpProcess(String strFrom, String strPropertyCode, String clientCode) {

		return objGlobalDao.funGetSetUpProcess(strFrom, strPropertyCode, clientCode);
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List funGetProdQtyForStock(String sql, String queryType) {
		return objGlobalDao.funGetProdQtyForStock(sql, queryType);
	}

	@Override
	public long funGet_no_Of_Pages_forSearch(String colmnName, String tableName, boolean flgQuerySelection, String clientCode) {

		return objGlobalDao.funGet_no_Of_Pages_forSearch(colmnName, tableName, flgQuerySelection, clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetList(String sql, String queryType) {

		return objGlobalDao.funGetList(sql, queryType);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetDataList(String sql, String queryType) {

		return objGlobalDao.funGetDataList(sql, queryType);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funCheckName(String name, String strClientCode, String tableName) {

		return objGlobalDao.funCheckName(name, strClientCode, tableName);
	}

	@Override
	public Map<String, String> funGetUserWisePropertyList(String clientCode, String usercode, String userPrpoerty) {

		return objGlobalDao.funGetUserWisePropertyList(clientCode, usercode, userPrpoerty);
	}

	@Override
	public int funCheckFromInWorkflowforSlabbasedAuth(String strForm, String strPropertyCode) {

		return objGlobalDao.funCheckFromInWorkflowforSlabbasedAuth(strForm, strPropertyCode);
	}

	@Override
	public int funAddUserLogEntry(clsUserLogsModel objModel) {
		return objGlobalDao.funAddUserLogEntry(objModel);
	}

	@Override
	public int funUpdate(String sql, String queryType) {

		return objGlobalDao.funUpdate(sql, queryType);
	}

	public int funUpdateAllModule(String sql, String queryType) {
		return objGlobalDao.funUpdateAllModule(sql, queryType);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public void funSaveAuditDtl(clsAuditDtlModel auditDtlModel) {
		objGlobalDao.funSaveAuditDtl(auditDtlModel);

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public void funSaveAuditHd(clsAuditHdModel auditHdModel) {
		objGlobalDao.funSaveAuditHd(auditHdModel);

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public void funSaveAuditTaxDtl(clsAuditGRNTaxDtlModel AuditGRNTaxDtlModel) {
		objGlobalDao.funSaveAuditTaxDtl(AuditGRNTaxDtlModel);

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public List funGetListReportQuery(String sql) {
		return objGlobalDao.funGetListReportQuery(sql);
	}

	@Override
	public List funGetListModuleWise(String sql, String queryType) {
		// TODO Auto-generated method stub
		return objGlobalDao.funGetListModuleWise(sql, queryType);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public int funExcuteQuery(String sql) {
		return objGlobalDao.funExcuteQuery(sql);

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public Long funGetCountAllModule(String tableName, String columnName) {
		return objGlobalDao.funGetCountAllModule(tableName, columnName);
	}

	@Override
	public long funGetMaxCountNo(String tableName, String masterName, String columnName, String clientCode) {
		return objGlobalDao.funGetMaxCountNo(tableName, masterName, columnName, clientCode);
	}

	@Override
	public long funGetPMSMasterLastNo(String tableName, String masterName, String columnName, String clientCode) {

		return objGlobalDao.funGetPMSMasterLastNo(tableName, masterName, columnName, clientCode);
	}

	@Override
	public long funGetNextNo(String tableName, String masterName, String columnName, String clientCode) {
		return objGlobalDao.funGetNextNo(tableName, masterName, columnName, clientCode);
	}

	@Override
	public long funGetNextNo(String tableName, String masterName, String columnName, String clientCode, String condition) {
		return objGlobalDao.funGetNextNo(tableName, masterName, columnName, clientCode, condition);
	}

	@Override
	public List funGetWebBooksAccountDtl(String accountCode, String clientCode) {
		return objGlobalDao.funGetWebBooksAccountDtl(accountCode, clientCode);
	}

	@Override
	public long funGetLastNoModuleWise(String tableName, String masterName, String columnName, String clientCode, String module) {
		return objGlobalDao.funGetLastNoModuleWise(tableName, masterName, columnName, clientCode, module);
	}

	/*
	 * @Override public int funDeleteWebBookTempLedger(String clientCode,String
	 * userCode,String propertyCode) { return
	 * objGlobalDao.funDeleteWebBookTempLedger
	 * (clientCode,userCode,propertyCode); }
	 */

	@Override
	public int funDeleteWebBookLedgerSummary(String clientCode, String userCode, String propertyCode) {
		return objGlobalDao.funDeleteWebBookLedgerSummary(clientCode, userCode, propertyCode);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false, value = "WebBooksTransactionManager")
	public void funAddUpdateLedgerSummary(clsLedgerSummaryModel cobjLedgerSummaryModel) {
		objGlobalDao.funAddUpdateLedgerSummary(cobjLedgerSummaryModel);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false, value = "WebBooksTransactionManager")
	public int funDeleteWebBookCurrentAccountBal(String clientCode, String userCode, String propertyCode) {
		return objGlobalDao.funDeleteWebBookCurrentAccountBal(clientCode, userCode, propertyCode);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false, value = "WebBooksTransactionManager")
	public void funAddUpdateCurrentAccountBalModel(clsCurrentAccountBalMaodel objclsCurrentAccountBalMaodel) {
		objGlobalDao.funAddUpdateCurrentAccountBalModel(objclsCurrentAccountBalMaodel);
	}

	/*
	 * @Override public int funAddCurrentDebtorCredtor(String clientCode,String
	 * userCode,String propertyCode) { return
	 * objGlobalDao.funAddCurrentDebtorCredtor
	 * (clientCode,userCode,propertyCode); }
	 * 
	 * @Override public int funAddTempLedger(String hql,String queryType) {
	 * return objGlobalDao.funAddTempLedger(hql, queryType); }
	 */

	public double funGetCurrencyConversion(double amount, String currency, String clientCode) {
		return objGlobalDao.funGetCurrencyConversion(amount, currency, clientCode);
	}
}
