package com.sanguine.crm.dao;

import java.util.List;

import org.hibernate.Query;

import com.sanguine.crm.model.clsSubContractorGRNModelDtl;
import com.sanguine.crm.model.clsSubContractorGRNModelHd;

public interface clsSubContractorGRNDao {

	public boolean funAddUpdateSubContractorGRNHd(clsSubContractorGRNModelHd objHdModel);

	public void funAddUpdateSubContractorGRNDtl(clsSubContractorGRNModelDtl objDtlModel);

	public clsSubContractorGRNModelHd funGetSubContractorGRNHd(String strSRCode, String clientCode);

	public void funDeleteDtl(String strSRCode, String clientCode);

	public List<Object> funLoadSubContractorGRNHDData(String docCode, String clientCode);

	public List<Object> funLoadSubContractorGRNDtlData(String docCode, String clientCode);

}
