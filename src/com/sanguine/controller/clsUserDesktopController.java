package com.sanguine.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsUserDesktopBean;
import com.sanguine.model.clsUserDesktopModel;
import com.sanguine.service.clsTreeMenuService;
import com.sanguine.util.clsUserDesktopUtil;

@Controller
public class clsUserDesktopController {
	@Autowired
	clsTreeMenuService objclsTreeMenuService;

	@RequestMapping(value = "/frmUserDesktop", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsUserDesktopBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest req) {

		List<clsUserDesktopModel> desktoplist = funGetUserDesktop(req);
		model.put("listDesktop", desktoplist);
		ModelAndView ob = new ModelAndView("frmUserDesktop");
		return ob;

	}

	private List<clsUserDesktopModel> funGetUserDesktop(HttpServletRequest req) {
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String isSuperUser = req.getSession().getAttribute("superuser").toString();

		List<clsUserDesktopModel> desktop = objclsTreeMenuService.getUserDesktopForm(userCode);

		if (null != desktop && desktop.size() == 0) {
			List<clsUserDesktopUtil> objModel = null;
			if ("YES".equalsIgnoreCase(isSuperUser)) {
				objModel = objclsTreeMenuService.funGetForms();
			} else {
				objModel = objclsTreeMenuService.funGetForms(userCode, clientCode);
			}
			for (Object ob : objModel) {
				clsUserDesktopModel tempOb = new clsUserDesktopModel();
				if (ob != null) {
					Object[] arrOb = (Object[]) ob;
					if (null != arrOb[0]) {
						tempOb.setStrformname(arrOb[0].toString());
					}
					if (null != arrOb[1]) {
						tempOb.setFormDesc(arrOb[1].toString());
					}
					tempOb.setDesktopForm(false);
				}
				desktop.add(tempOb);
			}
			return desktop;
		} else {
			List<String> fromName = new ArrayList<String>();
			for (Object ob : desktop) {
				Object[] arrOb = (Object[]) ob;
				if (null != arrOb[1]) {
					fromName.add(arrOb[1].toString());
				}
			}
			desktop.clear();
			List<clsUserDesktopUtil> objModel = null;
			if ("YES".equalsIgnoreCase(isSuperUser)) {
				objModel = objclsTreeMenuService.funGetForms();
			} else {
				objModel = objclsTreeMenuService.funGetForms(userCode, clientCode);
			}
			for (Object ob : objModel) {
				if (ob != null) {

					Object[] arrOb = (Object[]) ob;
					if (null != arrOb[0]) {
						clsUserDesktopModel tempOb = new clsUserDesktopModel();
						if (!fromName.contains(arrOb[0].toString())) {

							if (null != arrOb[0]) {
								tempOb.setStrformname(arrOb[0].toString());
							}
							if (null != arrOb[1]) {
								tempOb.setFormDesc(arrOb[1].toString());
							}
							tempOb.setDesktopForm(false);

						} else {
							if (null != arrOb[0]) {
								tempOb.setStrformname(arrOb[0].toString());
							}
							if (null != arrOb[1]) {
								tempOb.setFormDesc(arrOb[1].toString());
							}
							tempOb.setDesktopForm(true);

						}
						desktop.add(tempOb);
					}

				}
			}

			return desktop;
		}
	}

	@RequestMapping(value = "/saveUserDesktop", method = RequestMethod.POST)
	public ModelAndView funSaveDesktop(@ModelAttribute("command") @Valid clsUserDesktopBean objBean, BindingResult result, HttpServletRequest req) {
		System.out.println();
		String userCode = req.getSession().getAttribute("usercode").toString();
		if (!result.hasErrors()) {

			objclsTreeMenuService.funDeleteDesktopForm(userCode);
			for (clsUserDesktopModel ob : objBean.getListUserDesktop()) {
				ob.setStrusercode(userCode);
				if (ob.isDesktopForm()) {
					objclsTreeMenuService.funInsertDesktopForm(ob.getStrformname(), userCode);
				}
			}
		}
		List<clsUserDesktopUtil> userDesktop = funGetUserDesktopform(req);
		req.getSession().removeAttribute("desktop");
		req.getSession().setAttribute("desktop", userDesktop);
		return new ModelAndView("redirect:/frmHome.html");
	}

	private List<clsUserDesktopUtil> funGetUserDesktopform(HttpServletRequest req) {
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String isSuperUser = req.getSession().getAttribute("superuser").toString();
		List<clsUserDesktopUtil> desktop = new ArrayList<clsUserDesktopUtil>();
		List<clsUserDesktopUtil> objModel = null;
		if ("YES".equalsIgnoreCase(isSuperUser)) {
			objModel = objclsTreeMenuService.funGetDesktopForms(userCode);
		} else {
			objModel = objclsTreeMenuService.funGetDesktopForms(userCode, clientCode);
		}
		for (Object ob : objModel) {
			Object[] arrOb = (Object[]) ob;
			clsUserDesktopUtil obTreeRootItem = new clsUserDesktopUtil();
			if (null != arrOb[0]) {
				obTreeRootItem.setStrFormName(arrOb[0].toString());
			}
			if (null != arrOb[1]) {
				obTreeRootItem.setStrFormDesc(arrOb[1].toString());
			}
			if (null != arrOb[2]) {
				obTreeRootItem.setStrImgName(arrOb[2].toString());
			}
			if (null != arrOb[3]) {
				obTreeRootItem.setStrRequestMapping(arrOb[3].toString());
			}

			desktop.add(obTreeRootItem);
		}

		return desktop;
	}
}
