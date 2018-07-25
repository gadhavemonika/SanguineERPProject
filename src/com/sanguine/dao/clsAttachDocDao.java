package com.sanguine.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.model.clsAttachDocModel;

public interface clsAttachDocDao {
	public void funSaveDoc(clsAttachDocModel objModel);

	public List<clsAttachDocModel> funListDocs(String docCode, String clientCode);

	public List funGetDoc(String code, String fileNo, String clientCode);

	public void funDeleteDoc(Long id);

	public void funDeleteAttachment(String docName, String dcode, String clientCode);
}
