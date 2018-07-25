package com.sanguine.excise.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.excise.bean.clsExciseAuditBean;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsExciseAuditFalshController {
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	// Open Audit Flash
	@RequestMapping(value = "/frmExciseAuditFlash", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseAuditFlash_1", "command", new clsExciseAuditBean());
		} else {
			return new ModelAndView("frmExciseAuditFlash", "command", new clsExciseAuditBean());
		}

	}

	// load audit data in table

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@RequestMapping(value = "/loadExciseAuditData", method = RequestMethod.GET)
	public @ResponseBody ArrayList<ArrayList<Object>> funLoadAuditData(HttpServletRequest request) {
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String transType = request.getParameter("transType");
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String user = request.getSession().getAttribute("usercode").toString();

		ArrayList response = new ArrayList();

		String auditHdsql = "select a.strTransCode,a.dtBillDate,a.strUserModified, a.dtLastModified  from dbwebmms.tblaudithd a where a.dtBillDate between '" + fromDate + "' and '" + toDate + "' " + "and a.strTransType='" + transType + "' and a.strClientCode='" + clientCode + "' ";

		List auditHdList = objGlobalFunctionsService.funGetDataList(auditHdsql, "sql");

		if (!auditHdList.isEmpty()) {
			for (int i = 0; i < auditHdList.size(); i++) {

				Object[] objAuditHd = (Object[]) auditHdList.get(i);
				if (objAuditHd != null) {
					ArrayList auditHdArr = new ArrayList();
					auditHdArr.add(objAuditHd[0]);
					auditHdArr.add(objAuditHd[1]);
					auditHdArr.add(objAuditHd[2]);
					auditHdArr.add(objAuditHd[3]);
					response.add(auditHdArr);
				}
			}
		}

		return response;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/funExciseAuditdtl", method = RequestMethod.GET)
	private @ResponseBody ArrayList<ArrayList<Object>> funCallExciseAuditdtl(HttpServletResponse resp, HttpServletRequest req) {
		String strTransCode = req.getParameter("transCode").toString().trim();
		// String TransType=req.getParameter("TransType").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String TransMode = "Edited";
		String auditDtlsql = "";
		;
		ArrayList response = new ArrayList();

		if (TransMode.equalsIgnoreCase("Edited")) {
			auditDtlsql = "select a.strTransCode ,a.strCode,a.strProdCode,a.dblQty from dbwebmms.tblauditdtl a where a.strTransCode='" + strTransCode + "' and a.strClientCode='" + clientCode + "' ";
		}
		if (TransMode.equalsIgnoreCase("Deleted")) {
			auditDtlsql = "select a.strTransCode ,a.strCode,a.strProdCode,a.dblQty from dbwebmms.tblauditdtl a where a.strTransCode='" + strTransCode + "' and a.strClientCode='" + clientCode + "' ";
		}

		List auditHdList = objGlobalFunctionsService.funGetDataList(auditDtlsql, "sql");
		if (!auditHdList.isEmpty()) {
			for (int i = 0; i < auditHdList.size(); i++) {

				Object[] objAuditHd = (Object[]) auditHdList.get(i);
				if (objAuditHd != null) {
					ArrayList audiDtldArr = new ArrayList();
					audiDtldArr.add(objAuditHd[0]);
					if (objAuditHd[1] == null) {
						audiDtldArr.add("-");
					} else {
						audiDtldArr.add(objAuditHd[1]);
					}
					audiDtldArr.add(objAuditHd[2]);
					audiDtldArr.add(objAuditHd[3]);
					response.add(audiDtldArr);

				}
			}
		}
		System.out.print(response);

		return response;

	}

}
