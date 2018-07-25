package com.sanguine.webclub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubMemberProfileDao;
import com.sanguine.webclub.model.clsWebClubMemberProfileModel;

@Service("clsMemberProfileService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubMemberProfileServiceImpl implements clsWebClubMemberProfileService {
	@Autowired
	private clsWebClubMemberProfileDao objMemberProfileDao;

	@Override
	public void funAddUpdateMemberProfile(clsWebClubMemberProfileModel objMaster) {
		objMemberProfileDao.funAddUpdateMemberProfile(objMaster);
	}

	public clsWebClubMemberProfileModel funGetCustomer(String customerCode, String clientCode) {
		return objMemberProfileDao.funGetCustomer(customerCode, clientCode);
	}

	public clsWebClubMemberProfileModel funGetMember(String memberCode, String clientCode) {
		return objMemberProfileDao.funGetMember(memberCode, clientCode);
	}

	public List<clsWebClubMemberProfileModel> funGetAllMember(String primaryCode, String clientCode) {
		return objMemberProfileDao.funGetAllMember(primaryCode, clientCode);
	}

	public clsWebClubMemberProfileModel funGetWebClubAreaMaster(String areaCode, String clientCode) {
		return objMemberProfileDao.funGetWebClubAreaMaster(areaCode, clientCode);

	}

	public String funGetCustomerID(String customerCode, String clientCode) {

		return objMemberProfileDao.funGetCustomerID(customerCode, clientCode);
	}

}
