package com.sanguine.crm.service;

import java.util.List;

import com.sanguine.crm.model.clsSubContractorGRNModelDtl;
import com.sanguine.crm.model.clsSubContractorGRNModelHd;

public interface clsSubContractorGRNService {

	public boolean funAddUpdateSubContractorGRNHd(clsSubContractorGRNModelHd objHdModel);

	public void funAddUpdateSubContractorGRNDtl(clsSubContractorGRNModelDtl objDtlModel);

	public clsSubContractorGRNModelHd funGetSubContractorGRNHd(String strSRCode, String clientCode);

	public void funDeleteDtl(String strSRCode, String clientCode);

	public List<Object> funLoadSubContractorGRNHDData(String docCode, String clientCode);

	public List<Object> funLoadSubContractorGRNDtlData(String docCode, String clientCode);

}
