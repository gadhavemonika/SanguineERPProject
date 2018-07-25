package com.sanguine.bean;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class clsExportImportBean {

	private CommonsMultipartFile fileData;

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

}
