package com.ragnarson.StudentMVC.web;

import com.ragnarson.StudentMVC.model.bean.StudentBean;
import com.ragnarson.StudentMVC.service.StudentService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UpdateStudentController {
	
	private static final Logger log = LogManager.getLogger(UpdateStudentController.class);

	
	@Autowired
	private StudentService service;

	@GetMapping("/updateStudent/load")
	public ModelAndView load() {
		return new ModelAndView("updateStudent");
	}
	
	@PostMapping("/updateStudent/find")
	public ModelAndView find(Long id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("updateStudent");
		StudentBean studentBean = service.findById(id);
		log.info(studentBean);
		modelAndView.addObject("studentBean", studentBean);
		return modelAndView;
	}
	
	@PostMapping("/updateStudent/update")
	public ModelAndView update(@ModelAttribute @Valid StudentBean studentBean) {
		
		log.info(studentBean);
		
		Long id = service.update(studentBean);
		String message = "Update method called with return value of :: " + id;
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("updateStudent");
		modelAndView.addObject("studentBean", null);
		modelAndView.addObject("message", message);
		
		return modelAndView;
	}
}
