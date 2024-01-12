package com.ragnarson.StudentMVC.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ragnarson.StudentMVC.bean.StudentBean;
import com.ragnarson.StudentMVC.service.ReadStudentParams;
import com.ragnarson.StudentMVC.service.StudentService;

import jakarta.validation.Valid;

@Controller
public class ReadStudentController {
	
	@Autowired
	private StudentService service;

	@GetMapping("/readStudent")
	public ModelAndView getReadStudent() {
		ModelAndView modelAndView = new ModelAndView("readStudent");
		modelAndView.addObject("readStudentParams", new ReadStudentParams());
		return modelAndView;
	}
	
	@PostMapping("/readStudent")
	public ModelAndView readStudent(@Valid ReadStudentParams studentParams, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		List<StudentBean> result = null;
		if(bindingResult.hasErrors()) {
			
		}else {
			result = service.readByParams(studentParams);
		}
		return modelAndView;
	}
}
