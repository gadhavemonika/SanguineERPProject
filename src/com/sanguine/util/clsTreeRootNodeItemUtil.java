package com.sanguine.util;

import java.io.Serializable;

@SuppressWarnings("serial")
public class clsTreeRootNodeItemUtil implements Serializable {
	private String strFormDesc;
	private String strRequestMapping;

	public String getStrFormDesc() {
		return strFormDesc;
	}

	public void setStrFormDesc(String strFormDesc) {
		this.strFormDesc = strFormDesc;
	}

	public String getStrRequestMapping() {
		return strRequestMapping;
	}

	public void setStrRequestMapping(String strRequestMapping) {
		this.strRequestMapping = strRequestMapping;
	}

}
