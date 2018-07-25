package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsStkPostingDtlModel;
import com.sanguine.model.clsStkPostingHdModel;

public interface clsStkPostingService {
	public void funAddUpdate(clsStkPostingHdModel object);

	public void funAddUpdateDtl(clsStkPostingDtlModel object);

	public List<clsStkPostingHdModel> funGetList(String clientCode);

	public List<clsStkPostingDtlModel> funGetDtlList(String PSCode, String clientCode);

	public List funGetObject(String PSCode, String clientCode);

	public void funDeleteDtl(String PSCode, String clientCode);

	public clsStkPostingHdModel funGetModelObject(String strphyStkpostCode, String clientCode);
}
