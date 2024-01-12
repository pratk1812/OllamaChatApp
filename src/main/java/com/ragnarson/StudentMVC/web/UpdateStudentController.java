package com.ragnarson.StudentMVC.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ragnarson.StudentMVC.bean.StudentBean;
import com.ragnarson.StudentMVC.service.StudentService;

import jakarta.validation.Valid;

@Controller
public class UpdateStudentController {
	
	private static Logger log = LogManager.getLogger(UpdateStudentController.class);

	
	@Autowired
	private StudentService service;

	@GetMapping("/updateStudent")
	public ModelAndView getUpdateStudent() {
		return new ModelAndView("updateStudent");
	}
	
	@PostMapping("/findStudentToUpdate")
	public ModelAndView findStudent(Long id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("updateStudent");
		StudentBean studentBean = service.findById(id);
		log.info(studentBean);
		modelAndView.addObject("studentBean", studentBean);
		return modelAndView;
	}
	
	@PostMapping("/updateStudent")
	public ModelAndView updateStudent(@Valid StudentBean studentBean) {
		
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
