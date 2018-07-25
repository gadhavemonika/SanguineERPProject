package com.sanguine.webbooks.apgl.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.webbooks.apgl.bean.clsAPGLReceiptsBean;

@Controller
public class clsAPGLReceiptController {

	@RequestMapping(value = "/frmAPGLReceipts", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		List listType = new ArrayList<String>();
		listType.add("Cash");
		listType.add("Cheque");
		model.put("listType", listType);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmAPGLReceipts_1", "command", new clsAPGLReceiptsBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmAPGLReceipts", "command", new clsAPGLReceiptsBean());
		} else {
			return null;
		}

	}

}
