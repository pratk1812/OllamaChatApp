package com.ragnarson.StudentMVC.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController{

	@RequestMapping("/error")
	public ModelAndView handleError(HttpServletRequest request) {
	    ModelAndView errorPage = new ModelAndView("error");
        String errorMsg = request.getAttribute(RequestDispatcher.ERROR_MESSAGE).toString();
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
	}
	
	@ExceptionHandler
	public ModelAndView handleException(Exception e) {
	    ModelAndView errorPage = new ModelAndView("error");
	    errorPage.addObject("errorMsg", e.getMessage());
	    return errorPage;
	}
}
