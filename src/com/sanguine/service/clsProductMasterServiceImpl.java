package com.sanguine.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsProductMasterDao;
import com.sanguine.model.clsProdAttMasterModel;
import com.sanguine.model.clsProdCharMasterModel;
import com.sanguine.model.clsProdProcessModel;
import com.sanguine.model.clsProdSuppMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsProductReOrderLevelModel;

@Service("objProductMasterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsProductMasterServiceImpl implements clsProductMasterService {
	@Autowired
	private clsProductMasterDao objProductMasterDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsProductMasterModel> funGetList(String strClientCode) {
		return objProductMasterDao.funGetList(strClientCode);
	}

	public clsProductMasterModel funGetObject(String prodCode, String clientCode) {
		return objProductMasterDao.funGetObject(prodCode, clientCode);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateGeneral(clsProductMasterModel objModel) {
		objProductMasterDao.funAddUpdateGeneral(objModel);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateProdSupplier(clsProdSuppMasterModel objProdSuppModel) {
		objProductMasterDao.funAddUpdateProdSupplier(objProdSuppModel);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateProdProcess(clsProdProcessModel objProdProcessModel) {
		objProductMasterDao.funAddUpdateProdProcess(objProdProcessModel);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateProdAttribute(clsProdAttMasterModel objProdAttrModel) {
		objProductMasterDao.funAddUpdateProdAttribute(objProdAttrModel);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateProdReOrderLvl(clsProductReOrderLevelModel objProdReOrderModel) {
		objProductMasterDao.funAddUpdateProdReOrderLvl(objProdReOrderModel);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateProdChar(clsProdCharMasterModel objProdCharModel) {
		objProductMasterDao.funAddUpdateProdChar(objProdCharModel);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetAttrObject(String code, String clientCode) {
		return objProductMasterDao.funGetAttrObject(code, clientCode);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsProdSuppMasterModel> funGetProdSuppList(String prodCode, String clientCode) {
		return objProductMasterDao.funGetProdSuppList(prodCode, clientCode);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsProdAttMasterModel> funGetProdAttributeList(String prodCode, String clientCode) {
		return objProductMasterDao.funGetProdAttributeList(prodCode, clientCode);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsProductReOrderLevelModel> funGetProdReOrderList(String prodCode, String clientCode) {
		return objProductMasterDao.funGetProdReOrderList(prodCode, clientCode);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsProdCharMasterModel> funGetProdCharList(String prodCode, String clientCode) {
		return objProductMasterDao.funGetProdCharList(prodCode, clientCode);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsProdProcessModel> funGetProdProcessList(String prodCode, String clientCode) {
		return objProductMasterDao.funGetProdProcessList(prodCode, clientCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetdtlList(String clientCode) {
		return objProductMasterDao.funGetdtlList(clientCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetSuppdtlList(String prodCode, String clientCode) {
		return objProductMasterDao.funGetSuppdtlList(prodCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int funDeleteProdReorder(String prodCode, String clientCode) {
		return objProductMasterDao.funDeleteProdReorder(prodCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int funDeleteProdSupp(String prodCode, String clientCode) {
		return objProductMasterDao.funDeleteProdSupp(prodCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int funDeleteProdAttr(String prodCode, String clientCode) {
		return objProductMasterDao.funDeleteProdAttr(prodCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int funDeleteProdProcess(String prodCode, String clientCode) {
		return objProductMasterDao.funDeleteProdProcess(prodCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsProdSuppMasterModel funGetProdSupp(String prodCode, String clientCode) {
		return objProductMasterDao.funGetProdSupp(prodCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String funGetProductName(String prodCode, String clientCode) {

		return objProductMasterDao.funGetProductName(prodCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Map<String, clsProductMasterModel> mapProductDetail(String clientCode) {

		return objProductMasterDao.mapProductDetail(clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<String> funGetTaxIndicator(String propertyCode, String clientCode) {
		return objProductMasterDao.funGetTaxIndicator(propertyCode, clientCode);
	}

	@Override
	public void funProductUpdateCostRM(Double dblCostRM, String strProCode, String strClientCode) {
		objProductMasterDao.funProductUpdateCostRM(dblCostRM, strProCode, strClientCode);
	}

	@Override
	public clsProductMasterModel funGetSupplierWiseObject(String strSuppCode, String prodCode, String clientCode) {
		return objProductMasterDao.funGetSupplierWiseObject(strSuppCode, prodCode, clientCode);
	}

	@Override
	public clsProductMasterModel funGetImportedPOSItem(String strPartNo, String strProdName, String strClientCode, String sGCode, String locCode) {

		return objProductMasterDao.funGetImportedPOSItem(strPartNo, strProdName, strClientCode, sGCode, locCode);
	}

	@Override
	public clsProductMasterModel funGetBarCodeProductObject(String barCode, String clientCode) {
		return objProductMasterDao.funGetBarCodeProductObject(barCode, clientCode);
	}

	@Override
	public void funDeleteProdChar(String prodCode, String charCode, String processCode, String strClientCode) {
		objProductMasterDao.funDeleteProdChar(prodCode, charCode, processCode, strClientCode);
	}

	@Override
	public List funGetProdChar(String prodCode, String strClientCode) {
		return objProductMasterDao.funGetProdChar(prodCode, strClientCode);
	}

	@Override
	public int funDeleteSuppProds(String suppCode, String clientCode) {
		return objProductMasterDao.funDeleteSuppProds(suppCode, clientCode);
	}

	@Override
	public List funGetProdSuppWaiseProdList(String suppCode, String clientCode) {
		return objProductMasterDao.funGetProdSuppWaiseProdList(suppCode, clientCode);
	}

	@Override
	public List funGetAllProduct(String strClientCode) {
		return objProductMasterDao.funGetAllProduct(strClientCode);
	}

	public List funGetProdSuppDtl(String prodCode, String strCustCode, String clientCode) {

		return objProductMasterDao.funGetProdSuppDtl(prodCode, strCustCode, clientCode);

	}

	@Override
	public List funGetALLProducedlProduct(String strClientCode) {
		return objProductMasterDao.funGetALLProducedlProduct(strClientCode);
	}

	@Override
	public clsProdSuppMasterModel funGetProdSuppMasterModel(String strCustCode, String prodCode, String clientCode) {
		return objProductMasterDao.funGetProdSuppMasterModel(strCustCode, prodCode, clientCode);
	}

	@Override
	public int funDeleteProdSuppWise(String custCode, String prodCode, String clientCode) {
		return objProductMasterDao.funDeleteProdSuppWise(custCode, prodCode, clientCode);
	}

	public List<clsProductMasterModel> funGetAllNonStockablProddSuppList(String clientCode) {
		return objProductMasterDao.funGetAllNonStockablProddSuppList(clientCode);
	}

	public List<clsProductMasterModel> funGetAllStockablProddSuppList(String clientCode) {
		return objProductMasterDao.funGetAllStockablProddSuppList(clientCode);
	}

	public List funGetSubGroupWiseProductList(String sgCode, String clientCode) {
		return objProductMasterDao.funGetSubGroupWiseProductList(sgCode, clientCode);
	}

	@Override
	public List funGetSubGroupNameWiseProductList(String sgName, String clientCode) {
		return objProductMasterDao.funGetSubGroupNameWiseProductList(sgName, clientCode);
	}

	@Override
	public int funDeleteProdReorderLoc(String prodCode, String locCode, String clientCode) {
		return objProductMasterDao.funDeleteProdReorderLoc(prodCode, locCode, clientCode);
	}

	public clsProductReOrderLevelModel funGetProdReOrderLvl(String prodCode, String locCode, String clientCode) {
		return objProductMasterDao.funGetProdReOrderLvl(prodCode, locCode, clientCode);
	}

}
