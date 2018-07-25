package com.sanguine.webbooks.dao;

import com.sanguine.webbooks.model.clsJVDtlModel;
import com.sanguine.webbooks.model.clsJVHdModel;

public interface clsJVDao {

	public void funAddUpdateJVHd(clsJVHdModel objHdModel);

	public void funAddUpdateJVDtl(clsJVDtlModel objDtlModel);

	public clsJVHdModel funGetJVList(String vouchNo, String clientCode, String propertyCode);

	public void funDeleteJV(clsJVHdModel objJVHdModel);

	public void funInsertJV(String sqltempDtlDr);

}
