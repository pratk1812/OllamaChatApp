package com.ragnarson.StudentMVC.web;

import java.security.Principal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ragnarson.StudentMVC.bean.StudentBean;
import com.ragnarson.StudentMVC.service.StudentService;

@Controller
@SessionAttributes("studentBean")
public class DeleteStudentController {

	private static Logger log = LogManager.getLogger(DeleteStudentController.class);
	
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
	ModelAndView findStudent(Long id) {
		ModelAndView modelAndView = new ModelAndView("deleteStudent");
		StudentBean studentBean = service.findById(id);
		if (studentBean==null) {
			modelAndView.addObject("message", "No Data found");
		}else
			modelAndView.addObject("studentBean", studentBean);
		return modelAndView;
	}
	
	@PostMapping("/deleteStudent/delete")
	ModelAndView deleteStudent(Long id) {
		log.info("Deleting :"+id);
		service.deleteById(id);
		ModelAndView modelAndView = new ModelAndView("deleteStudent");
		modelAndView.addObject("message", "Student record deleted");
		modelAndView.addObject("studentBean", null);
		return modelAndView;
	}
	
	@PostMapping("/delete/login")
	ModelAndView confirmCredentials(@ModelAttribute StudentBean studentBean,
			Authentication currentAuthentication,
			String username, 
			String password) {
		
		ModelAndView modelAndView = new ModelAndView("deleteStudent");
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		Authentication newAuthentication = manager.authenticate(token);
		
		boolean isCurrentUser = false;
		if(newAuthentication.isAuthenticated()) {
			isCurrentUser = newAuthentication.getName().equals(currentAuthentication.getName());
		}
		
		if (isCurrentUser) {
			log.info("Deleting :"+studentBean.getId());
			service.deleteById(studentBean.getId());
			modelAndView.addObject("message", "Student record deleted");
			modelAndView.addObject("studentBean", null);
		}else {
			log.info("user not verified");
			modelAndView.addObject("message", "credential verfification failed");
			modelAndView.addObject("studentBean", null);
		}
		return modelAndView;
	}
}
