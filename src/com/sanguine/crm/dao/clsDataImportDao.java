package com.sanguine.crm.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("clsDataImportDao")
public class clsDataImportDao {

	@Autowired
	private SessionFactory sessionFactory;

}
