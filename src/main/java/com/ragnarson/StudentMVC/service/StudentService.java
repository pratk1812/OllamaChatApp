package com.ragnarson.StudentMVC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ragnarson.StudentMVC.repo.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository repository;
}
