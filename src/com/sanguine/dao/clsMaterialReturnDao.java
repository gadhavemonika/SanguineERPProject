package com.sanguine.dao;

import java.util.List;

import com.sanguine.model.clsMISDtlModel;
import com.sanguine.model.clsMISHdModel;
import com.sanguine.model.clsMaterialReturnDtlModel;
import com.sanguine.model.clsMaterialReturnHdModel;

public interface clsMaterialReturnDao {
	public void funAddUpdateMaterialReturnHd(clsMaterialReturnHdModel objMaterialReturnHd);

	public void funAddUpdateMaterialReturnDtl(clsMaterialReturnDtlModel objMaterialReturnDtl);

	public List<clsMaterialReturnHdModel> funGetList(String clientCode);

	public List funGetObject(String MRCode, String clientCode);

	public List funGetMRDtlList(String MRCode, String clientCode);

	public void funDeleteDtl(String MRCode, String clientCode);

}
