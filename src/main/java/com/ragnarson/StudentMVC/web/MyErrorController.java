package com.ragnarson.StudentMVC.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class MyErrorController implements ErrorController{

	private static final Logger log = LogManager.getLogger(MyErrorController.class);

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
	
	@RequestMapping("/report")
	public ResponseEntity<Void> report(@RequestBody String report) {
		log.warn("Malicious activity detected :: " + report);
		
		return ResponseEntity.noContent().build();
	}
}
