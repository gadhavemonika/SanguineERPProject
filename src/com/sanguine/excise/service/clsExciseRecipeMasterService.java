package com.sanguine.excise.service;

import java.util.List;

import com.sanguine.excise.model.clsExciseRecipeMasterDtlModel;
import com.sanguine.excise.model.clsExciseRecipeMasterHdModel;

@SuppressWarnings("rawtypes")
public interface clsExciseRecipeMasterService {

	public boolean funAddUpdateRecipeMaster(clsExciseRecipeMasterHdModel objMaster);

	public boolean funAddUpdateRecipeDtl(clsExciseRecipeMasterDtlModel objRecipeDtl);

	public List funGetList(String clientCode);

	public List funGetObject(String RecipeCode, String clientCode);

	public List funGetRecipeDtlList(String RecipeCode, String clientCode);

	public void funDeleteDtl(String RecipeCode, String clientCode);

}
