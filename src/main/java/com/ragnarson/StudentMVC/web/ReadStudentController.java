package com.ragnarson.StudentMVC.web;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ragnarson.StudentMVC.bean.StudentBean;
import com.ragnarson.StudentMVC.service.ReadStudentParams;
import com.ragnarson.StudentMVC.service.StudentService;

import jakarta.validation.Valid;

@Controller
public class ReadStudentController {
	private static Logger log = LogManager.getLogger(ReadStudentController.class);

	
	@Autowired
	private StudentService service;

	@GetMapping("/readStudent/load")
	public ModelAndView load() {
		ModelAndView modelAndView = new ModelAndView("readStudent");
		modelAndView.addObject("readStudentParams", new ReadStudentParams());
		return modelAndView;
	}
	
	@PostMapping("/readStudent/read")
	public ModelAndView read(
			@ModelAttribute @Valid ReadStudentParams studentParams, 
			BindingResult bindingResult) {
		log.info("Reading controller");
		ModelAndView modelAndView = new ModelAndView("readStudent");
		List<StudentBean> result = null;
		if(!bindingResult.hasErrors()) {
			result = service.readByParams(studentParams);
			log.info("no errors");
			if(result!=null) {
				log.info("List size"+result.size());
				modelAndView.addObject("students", result);
				modelAndView.addObject("message", "data read with list size " + result.size());
			}else {
				log.info("result is null");
			}
			log.info("Errors in params");
			log.info(bindingResult.toString());
		}
		return modelAndView;
	}
}
