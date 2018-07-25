package com.sanguine.webpms.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sanguine.webbooks.model.clsPaymentHdModel;
import com.sanguine.webpms.model.clsPMSPaymentHdModel;
import com.sanguine.webpms.model.clsPMSPaymentReceiptDtl;

public interface clsPMSPaymentDao {
	public void funAddUpdatePaymentHd(clsPMSPaymentHdModel objHdModel);

	public clsPMSPaymentHdModel funGetPaymentModel(String receiptNo, String clientCode);
}
