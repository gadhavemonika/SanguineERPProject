package com.sanguine.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsAttachDocDao;
import com.sanguine.model.clsAttachDocModel;

@Service("objAttDocService")
public class clsAttachDocServiceImpl implements clsAttachDocService {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private clsAttachDocDao objAttDoc;

	@Transactional
	public void funSaveDoc(clsAttachDocModel objModel) {
		objAttDoc.funSaveDoc(objModel);
	}

	@Transactional
	public List<clsAttachDocModel> funListDocs(String docCode, String clientCode) {
		return objAttDoc.funListDocs(docCode, clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public List funGetDoc(String code, String fileNo, String clientCode) {
		return objAttDoc.funGetDoc(code, fileNo, clientCode);
	}

	@Transactional
	public void funDeleteDoc(Long id) {
		objAttDoc.funDeleteDoc(id);
	}

	@Transactional
	public void funDeleteAttachment(String docName, String code, String clientCode) {
		objAttDoc.funDeleteAttachment(docName, code, clientCode);
	}

}
