package com.sanguine.webbooks.service;

import com.sanguine.webbooks.model.clsJVDtlModel;
import com.sanguine.webbooks.model.clsJVHdModel;

public interface clsJVService {

	public void funAddUpdateJVHd(clsJVHdModel objHdModel);

	public void funAddUpdateJVDtl(clsJVDtlModel objDtlModel);

	public clsJVHdModel funGetJVList(String vouchNo, String clientCode, String propertyCode);

	public void funDeleteJV(clsJVHdModel objJVHdModel);

	public void funInsertJV(String sqltempDtlDr);

}
