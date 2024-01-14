package com.ragnarson.StudentMVC.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ragnarson.StudentMVC.bean.StudentBean;
import com.ragnarson.StudentMVC.service.StudentService;

@Controller
public class DeleteStudentController {

	private static Logger log = LogManager.getLogger(DeleteStudentController.class);
	
	@Autowired
	private StudentService service;
	
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
	
	@GetMapping("/deleteStudent/load")
	ModelAndView load() {
		return new ModelAndView("deleteStudent");
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
}
