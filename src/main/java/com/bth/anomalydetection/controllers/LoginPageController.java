package com.bth.anomalydetection.controllers;

import com.bth.anomalydetection.global.ViewConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller responsible for actions on login page.
 */

@Controller
@RequestMapping("/login")
public class LoginPageController {
	
    @RequestMapping(method= RequestMethod.GET)
	public String populateView() throws Exception {
		return ViewConstants.LOGIN_PAGE_VIEW;
	}
	
}
