package com.sanguine.excise.service;

import java.util.List;

@SuppressWarnings("rawtypes")
public interface clsExciseBillGenerateService {

	public List funGetList(String strFromDate, String strToDate, String clientCode);

	public List funGetSalesDtlList(long intId, String clientCode);

	public void funDeleteSaleData(String strFromDate, String strToDate, String clientCode, String licenceCode);

	public int funExecute(String sql);

}
