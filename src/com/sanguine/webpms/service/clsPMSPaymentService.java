package com.sanguine.webpms.service;

import javax.servlet.http.HttpServletRequest;

import com.sanguine.webbooks.model.clsPaymentHdModel;
import com.sanguine.webpms.bean.clsPMSPaymentBean;
import com.sanguine.webpms.bean.clsWalkinBean;
import com.sanguine.webpms.model.clsPMSPaymentHdModel;
import com.sanguine.webpms.model.clsWalkinHdModel;

public interface clsPMSPaymentService {

	public void funAddUpdatePaymentHd(clsPMSPaymentHdModel objHdModel);

	public clsPMSPaymentHdModel funPreparePaymentModel(clsPMSPaymentBean objPaymentBean, String clientCode, HttpServletRequest request, String userCode);

}
