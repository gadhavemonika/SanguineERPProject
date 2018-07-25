package com.sanguine.crm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.crm.bean.clsInvoiceBean;
import com.sanguine.crm.bean.clsSalesOrderBean;
import com.sanguine.crm.model.clsSalesOrderDtl;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsInvoiceProductVarianceController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@RequestMapping(value = "/frmInvoiceProductVariance", method = RequestMethod.GET)
	public ModelAndView funOpenInvoiceVariancerForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmInvoiceProductVariance_1", "command", new clsSalesOrderBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmInvoiceProductVariance", "command", new clsSalesOrderBean());
		} else {
			return null;
		}

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadProductVarianceData", method = RequestMethod.GET)
	public @ResponseBody clsSalesOrderDtl funLoadProductDtl(HttpServletRequest request) {
		String strProdCode = request.getParameter("strProdCode").toString();
		String date = request.getParameter("date").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsSalesOrderDtl objModel = new clsSalesOrderDtl();
		List arrList = new ArrayList();
		String sql = "select a.strSOCode,b.strProdCode,a.strCustCode,c.strPName,b.dblQty,b.dblAcceptQty " + "from tblsalesorderhd a ,tblsalesorderdtl b,tblpartymaster c where b.strProdCode='" + strProdCode + "' " + "and a.dteSODate='" + date + "' and a.strSOCode=b.strSOCode and c.strPCode=a.strCustCode and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "'";
		List list = objGlobalFunctionsService.funGetList(sql, "sql");
		if (!list.isEmpty()) {
			ArrayList arrProductDtl = new ArrayList();
			Object[] arrObj = (Object[]) list.get(0);
			objModel.setStrSOCode(arrObj[0].toString());
			objModel.setStrProdCode(arrObj[1].toString());
			objModel.setStrCustCode(arrObj[2].toString());
			objModel.setStrCustNmae(arrObj[3].toString());
			objModel.setDblQty(Double.parseDouble(arrObj[4].toString()));
			objModel.setDblAcceptQty(Double.parseDouble(arrObj[5].toString()));

		}

		return objModel;
	}

	@RequestMapping(value = "/saveInvoiceVariance", method = RequestMethod.GET)
	public ModelAndView funUpdateProductDtl(@ModelAttribute("command") @Valid clsSalesOrderBean objBean, BindingResult result, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {
			String clientCode = request.getSession().getAttribute("clientCode").toString();
			List<clsSalesOrderDtl> lstSPDtl = objBean.getListSODtl();
			for (clsSalesOrderDtl objModel : lstSPDtl) {
				if (objModel.getStrSOCode() != null) {
					String sql = "update tblsalesorderdtl a set a.dblAcceptQty='" + objModel.getDblQty() + "' where a.strSOCode='" + objModel.getStrSOCode() + "'  " + " and a.strProdCode='" + objModel.getStrProdCode() + "' and a.strClientCode='" + clientCode + "' ";
					objGlobalFunctionsService.funUpdateAllModule(sql, "sql");
				}
			}

			request.getSession().setAttribute("success", true);
			request.getSession().setAttribute("successMessage", "Update Qunatity  ");
			return new ModelAndView("redirect:/frmInvoiceProductVariance.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmInvoiceProductVariance?saddr=" + urlHits, "command", new clsSalesOrderBean());
		}
	}
}
