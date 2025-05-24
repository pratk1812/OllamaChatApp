package com.ragnarson.StudentMVC.web;

import com.ragnarson.StudentMVC.enums.Roles;
import com.ragnarson.StudentMVC.service.ServiceException;
import com.ragnarson.StudentMVC.service.UserService;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
	private static final Logger log = LogManager.getLogger(LoginController.class);

	@Autowired
	private UserService service;

	@RequestMapping("/login")
	public String login(Authentication authentication) throws Exception {
		if (authentication != null && authentication.isAuthenticated())
			throw new Exception("User aleardy logged in");

		return "login";
	}

	@GetMapping("/register")
	public ModelAndView getRegister() {
		ModelAndView modelAndView = new ModelAndView("register");
		List<Roles> roles = Arrays.asList(Roles.values());
		modelAndView.addObject("roles", roles);
		return modelAndView;
	}

	@PostMapping("/register")
	public ModelAndView register(
			@RequestParam List<Roles> options,
			@RequestParam String username, 
			@RequestParam String password,
			RedirectAttributes redirectAttributes) {
		
		ModelAndView modelAndView = new ModelAndView();
		try {
			service.register(username,password,options);
			redirectAttributes.addFlashAttribute("alert", "User registered successfully!");
			modelAndView.setViewName("redirect:/login");
		} catch (ServiceException e) {
			redirectAttributes.addFlashAttribute("alert", "Failed! " + e.getMessage());
			modelAndView.setViewName("redirect:/register");
		}
		log.info("Username : " + username + " Password : " + password + "Roles : " + options);
		
		return modelAndView;
	}
}
