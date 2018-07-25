package com.sanguine.webpms.dao;

import java.util.List;
import com.sanguine.webpms.model.clsFolioHdModel;

public interface clsFolioDao {
	public void funAddUpdateFolioHd(clsFolioHdModel objHdModel);

	public clsFolioHdModel funGetFolioList(String folioNo, String clientCode, String propertyCode);

	public List funGetParametersList(String sqlParameters);
}
