package com.sanguine.webclub.service;

import java.util.List;

import com.sanguine.webclub.model.clsWebClubMemberProfileModel;

public interface clsWebClubMemberProfileService {

	public void funAddUpdateMemberProfile(clsWebClubMemberProfileModel objMaster);

	public clsWebClubMemberProfileModel funGetCustomer(String customerCode, String clientCode);

	public List<clsWebClubMemberProfileModel> funGetAllMember(String primaryCode, String clientCode);

	public clsWebClubMemberProfileModel funGetMember(String memberCode, String clientCode);

	public clsWebClubMemberProfileModel funGetWebClubAreaMaster(String areaCode, String clientCode);

	public String funGetCustomerID(String customerCode, String clientCode);

}
