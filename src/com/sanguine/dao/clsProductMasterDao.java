package com.sanguine.dao;

import java.util.List;
import java.util.Map;

import com.sanguine.model.clsProdAttMasterModel;
import com.sanguine.model.clsProdCharMasterModel;
import com.sanguine.model.clsProdProcessModel;
import com.sanguine.model.clsProdSuppMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsProductReOrderLevelModel;

public interface clsProductMasterDao {
	public void funAddUpdateGeneral(clsProductMasterModel objModel);

	public void funAddUpdateProdSupplier(clsProdSuppMasterModel objProdSuppModel);

	public void funAddUpdateProdProcess(clsProdProcessModel objProdProcessModel);

	public void funAddUpdateProdAttribute(clsProdAttMasterModel objProdAttrModel);

	public void funAddUpdateProdReOrderLvl(clsProductReOrderLevelModel objProdReOrderModel);

	public void funAddUpdateProdChar(clsProdCharMasterModel objProdCharModel);

	public List<clsProductMasterModel> funGetList(String strClientCode);

	public clsProductMasterModel funGetObject(String prodCode, String clientCode);

	public List funGetAttrObject(String code, String clientCode);

	public List<clsProdSuppMasterModel> funGetProdSuppList(String prodCode, String clientCode);

	public List<clsProdProcessModel> funGetProdProcessList(String prodCode, String clientCode);

	public List<clsProdAttMasterModel> funGetProdAttributeList(String prodCode, String clientCode);

	public List<clsProductReOrderLevelModel> funGetProdReOrderList(String prodCode, String clientCode);

	public List<clsProdCharMasterModel> funGetProdCharList(String prodCode, String clientCode);

	public List funGetdtlList(String clientCode);

	public List funGetSuppdtlList(String prodCode, String clientCode);

	public clsProdSuppMasterModel funGetProdSupp(String prodCode, String clientCode);

	public int funDeleteProdReorder(String prodCode, String clientCode);

	public int funDeleteProdSupp(String prodCode, String clientCode);

	public int funDeleteProdAttr(String prodCode, String clientCode);

	public int funDeleteProdProcess(String prodCode, String clientCode);

	public String funGetProductName(String prodCode, String clientCode);

	public Map<String, clsProductMasterModel> mapProductDetail(String clientCode);

	public List<String> funGetTaxIndicator(String propertyCode, String clientCode);

	public void funProductUpdateCostRM(Double dblCostRM, String strProCode, String strClientCode);

	public clsProductMasterModel funGetSupplierWiseObject(String strSuppCode, String prodCode, String clientCode);

	public clsProductMasterModel funGetImportedPOSItem(String strPartNo, String strProdName, String strClientCode, String sGCode, String locCode);

	public clsProductMasterModel funGetBarCodeProductObject(String barCode, String clientCode);

	public void funDeleteProdChar(String prodCode, String charCode, String processCode, String strClientCode);

	public List funGetProdChar(String prodCode, String strClientCode);

	public List funGetAllProduct(String strClientCode);

	public int funDeleteSuppProds(String suppCode, String clientCode);

	public List funGetProdSuppWaiseProdList(String suppCode, String clientCode);

	public List funGetProdSuppDtl(String prodCode, String strCustCode, String clientCode);

	public List funGetALLProducedlProduct(String strClientCode);

	public clsProdSuppMasterModel funGetProdSuppMasterModel(String strCustCode, String prodCode, String clientCode);

	public int funDeleteProdSuppWise(String custCode, String prodCode, String clientCode);

	public List<clsProductMasterModel> funGetAllNonStockablProddSuppList(String clientCode);

	public List<clsProductMasterModel> funGetAllStockablProddSuppList(String clientCode);

	public List funGetSubGroupWiseProductList(String sgCode, String clientCode);

	public List funGetSubGroupNameWiseProductList(String sgName, String clientCode);

	public int funDeleteProdReorderLoc(String prodCode, String locCode, String clientCode);

	public clsProductReOrderLevelModel funGetProdReOrderLvl(String prodCode, String locCode, String clientCode);

}
