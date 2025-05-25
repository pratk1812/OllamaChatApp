package com.ragnarson.StudentMVC.web;

import com.ragnarson.StudentMVC.model.bean.StudentBean;
import com.ragnarson.StudentMVC.service.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("studentBean")
public class DeleteStudentController {

	private static final Logger log = LogManager.getLogger(DeleteStudentController.class);
	
	@Autowired
	private StudentService service;
	
	@Autowired
	private AuthenticationManager manager;
	
	@GetMapping("/deleteStudent/load")
	ModelAndView load() {
		ModelAndView modelAndView = new ModelAndView("deleteStudent");
		modelAndView.addObject("studentBean", null);
		return modelAndView;
	}
	
	@PostMapping("/deleteStudent/find")
	ModelAndView find(Long id) {
		ModelAndView modelAndView = new ModelAndView("deleteStudent");
		StudentBean studentBean = service.findById(id);
		if (studentBean==null) {
			modelAndView.addObject("message", "No Data found");
			modelAndView.addObject("studentBean", null);
		}else
			modelAndView.addObject("studentBean", studentBean);
		return modelAndView;
	}
	
	@PostMapping("/delete/login")
	ModelAndView confirmCredentials(
			@ModelAttribute StudentBean studentBean,
			Authentication currentAuthentication,
			@RequestParam String username, 
			@RequestParam String password) {
		
		log.info("Controller called");
		log.info("username, password :: " + username + ", " + password);
		
		boolean isMatch = false;
		String message = "credentials verfification failed";

		
		if (currentAuthentication.getName().equals(username)) {
			try {
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
				Authentication newAuthentication = manager.authenticate(token);
				isMatch=newAuthentication.isAuthenticated();
				
			}catch (AuthenticationException e) {
				log.info(e.getMessage());
			}
			if (isMatch) {
				log.info("Deleting :"+studentBean.getId());
				service.deleteById(studentBean.getId());
				message="Student record deleted";
			}
		}
		
		ModelAndView modelAndView = new ModelAndView("deleteStudent");
		modelAndView.addObject("message", message);
		modelAndView.addObject("studentBean", null);

		return modelAndView;
	}
}
