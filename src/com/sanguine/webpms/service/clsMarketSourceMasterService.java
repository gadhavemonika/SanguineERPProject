package com.sanguine.webpms.service;


import com.sanguine.webpms.bean.clsMarketSourceMasterBean;
import com.sanguine.webpms.model.clsMarketSourceMasterModel;

public interface clsMarketSourceMasterService {
	
	public clsMarketSourceMasterModel funPrepareMarketModel(clsMarketSourceMasterBean objMarketMasterBean, String clientCode, String userCode);


}
