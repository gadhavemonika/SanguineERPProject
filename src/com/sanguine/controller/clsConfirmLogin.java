package com.sanguine.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsUserHdBean;
import com.sanguine.model.clsUserMasterModel;
import com.sanguine.service.clsUserMasterService;

@Controller
public class clsConfirmLogin {

	@Autowired
	private clsUserMasterService objUserMasterService;

	/**
	 * Open Confirm Login Form
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/frmConfirmLoginUser", method = RequestMethod.GET)
	public ModelAndView funOpenConfirmLoginUserForm(HttpServletRequest req) {

		clsUserHdBean bean = new clsUserHdBean();
		return new ModelAndView("frmConfirmLoginUser", "command", bean);
	}

	/**
	 * validating User
	 * 
	 * @param userBean
	 * @param result
	 * @param req
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/confirmUser", method = RequestMethod.POST)
	public @ResponseBody String funCheckConfirmLoginUserForm(clsUserHdBean userBean, BindingResult result, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getParameter("userName").toString();
		String password = req.getParameter("password").toString();
		clsUserMasterModel user = objUserMasterService.funGetUser(userCode, clientCode);
		String retValue = "";
		try {
			if (!result.hasErrors()) {

				if (user != null) {
					BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					if (passwordEncoder.matches(password, user.getStrPassword1())) {
						retValue = "Successfull Login";
					} else {
						retValue = "Invalid Login";
					}
				} else {
					retValue = "Invalid Login";
				}
			} else {
				retValue = "Invalid Login";
			}
		} catch (Exception e) {
			e.printStackTrace();
			retValue = "Invalid Login";

		} finally {
			return retValue;
		}

	}
}
