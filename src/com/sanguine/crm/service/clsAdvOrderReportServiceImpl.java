package com.sanguine.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsAdvOrderReportDao;

@Service("clsAdvOrderReportService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsAdvOrderReportServiceImpl implements clsAdvOrderReportService {

	@Autowired
	private clsAdvOrderReportDao objAdvOrdDao;

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetImageAdvOrder(String code, String itemCode, String clientCode) {

		return objAdvOrdDao.funGetImageAdvOrder(code, itemCode, clientCode);
	}
}
