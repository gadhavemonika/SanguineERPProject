package com.sanguine.webbooks.dao;

import java.util.ArrayList;
import java.util.List;

import com.sanguine.webbooks.model.clsChargeProcessingHDModel;

public interface clsChargeProcessingDao {

	public void funAddUpdateChargeProcessing(clsChargeProcessingHDModel objMaster);

	public clsChargeProcessingHDModel funGetChargeProcessing(String docCode, String clientCode);

	public void funClearTblChargeGenerationTemp(String strMemberCode);

	public List funGetAllMembers(String clientCode, String propertyCode);

	public List funCalculateOutstanding(String string, String funGetDate, String funGetDate2, String strMemberCode, String clientCode, String propertyCode);

	public void funUpdateMemberOutstanding(String memberCode, double dblOutstanding, String clientCode, String propertyCode);

	public List funIsChargeApplicable(String memberCode, String chargeCode, String clientCode, String propertyCode);

}
