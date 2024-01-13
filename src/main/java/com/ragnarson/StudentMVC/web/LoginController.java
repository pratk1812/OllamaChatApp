package com.ragnarson.StudentMVC.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ragnarson.StudentMVC.service.UserService;

@Controller
public class LoginController {
	private static Logger log = LogManager.getLogger(LoginController.class);

	
	@Autowired
    private UserService service;

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/register")
	public ModelAndView getRegister() {
		return new ModelAndView("register");
	}
	
	@PostMapping("/register")
	public ModelAndView register(
			@RequestParam String username, 
			@RequestParam String password,
			 RedirectAttributes redirectAttributes) {
		boolean saved = false;
		if(!username.isEmpty() && !password.isEmpty()) {
			log.info("Username : " + username + " Password : " + password);
			 saved = service.registerNew(username, password);
		}
		else {
			log.error("error in attributes");
			saved = false;
		}
		
		ModelAndView modelAndView = new ModelAndView("redirect:/register");
		
		if(saved) {
	        redirectAttributes.addFlashAttribute("alert", "User registered successfully!");
	        modelAndView.setViewName("redirect:/login");
		}else {
	        redirectAttributes.addFlashAttribute("alert", "Failed user registeration!");
	        modelAndView.setViewName("redirect:/register");
		}
		
		return modelAndView;
	}
}
