package com.sanguine.service;

import java.io.File;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.springframework.web.servlet.view.jasperreports.AbstractJasperReportsSingleFormatView;

public class JasperReportsPrintView extends AbstractJasperReportsSingleFormatView {

	private String reportUrl;

	public JasperReportsPrintView() {
		setContentType("application/octet-stream");
	}

	protected void renderReport(JasperPrint jasperPrint, Map<String, Object> attrMap, HttpServletResponse response) throws Exception {
		ServletOutputStream ouputStream = response.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(ouputStream);
		oos.writeObject(jasperPrint);
		oos.flush();
		oos.close();

		ouputStream.flush();
		ouputStream.close();

	}

	public String getReportUrl() {
		return reportUrl;
	}

	public void setReportUrl(String reportUrl) {
		this.reportUrl = reportUrl;
	}

	@Override
	protected JRExporter createExporter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean useWriter() {
		// TODO Auto-generated method stub
		return false;
	}
}
