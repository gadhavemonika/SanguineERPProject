package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsAttachDocModel;

public interface clsAttachDocService {
	public void funSaveDoc(clsAttachDocModel objModel);

	public List<clsAttachDocModel> funListDocs(String docCode, String clientCode);

	@SuppressWarnings("rawtypes")
	public List funGetDoc(String code, String fileNo, String clientCode);

	public void funDeleteDoc(Long id);

	public void funDeleteAttachment(String docName, String code, String clientCode);

}
