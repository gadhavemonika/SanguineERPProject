package com.sanguine.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsProductMasterBean;
import com.sanguine.bean.clsTransectionProdCharBean;
import com.sanguine.model.clsProdCharMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsProductMasterService;

@Controller
public class clsTransectionProdCharController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@RequestMapping(value = "/frmTransectionProdChar", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		String prodCode = req.getParameter("prodCode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		@SuppressWarnings("rawtypes")
		List listProdChar = objGlobalFunctionsService.funGetList("select b.strCharName,a.strSpecf from tblprodchar a,tblcharacteristics b where a.strCharCode=b.strCharCode and a.strProdCode='" + prodCode + "' and a.strClientCode='" + clientCode + "'");
		ArrayList<clsProdCharMasterModel> list = new ArrayList<clsProdCharMasterModel>();
		for (int count = 0; count < listProdChar.size(); count++) {
			clsProdCharMasterModel objModel = new clsProdCharMasterModel();
			Object[] obj = (Object[]) listProdChar.get(count);
			objModel.setStrProdCode(prodCode);
			objModel.setStrCharName(obj[0].toString());
			objModel.setStrSpecf(obj[1].toString());
			list.add(objModel);
		}
		model.put("listProdCharData", list);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmTransectionProdChar", "command", new clsTransectionProdCharBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmTransectionProdChar", "command", new clsTransectionProdCharBean());
		} else {
			return null;
		}

	}

}
