package com.sanguine.excise.service;

import java.util.List;

import com.sanguine.excise.model.clsExciseStkPostingDtlModel;
import com.sanguine.excise.model.clsExciseStkPostingHdModel;

@SuppressWarnings("rawtypes")
public interface clsExciseStkPostingService {
	public boolean funAddUpdate(clsExciseStkPostingHdModel object);

	public boolean funAddUpdateDtl(List<clsExciseStkPostingDtlModel> objList);

	public List<clsExciseStkPostingHdModel> funGetList(String clientCode);

	public List funGetDtlList(String strPSPCode, String clientCode, String tempSizeClientCode, String tempBrandClientCode);

	public List funGetObject(String strPSPCode, String clientCode);

	public void funDeleteDtl(String strPSPCode, String clientCode);
}
