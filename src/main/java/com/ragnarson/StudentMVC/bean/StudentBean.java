package com.ragnarson.StudentMVC.bean;

import java.util.Date;

import com.ragnarson.StudentMVC.enums.Major;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class StudentBean {

	private long id;
	
	@NotNull(message = "Value cannot be null")
	@Size(min = 3, max = 30, message = "Size must be between 3 and 30")
	private String name;
	
	@DecimalMin(value = "0.0", message = "GPA value cannot be below 0")
	@DecimalMax(value = "10.0", message = "GPA value cannot be above 10")
	private double gpa;
	
	@Min(value = 18, message = "Age cannot be below 18")
	@Max(value = 23, message = "Age cannot be above 23")
	private int age;
	
	@NotNull
	@Email(message = "Invalid email string")
	private String email;
	
	@NotNull(message = "Invalid Major")
	private Major major;
	
	@Past(message = "Value must be a Past Date")
	@NotNull(message = "Value cannot be null")
	private Date enrollmentDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getGpa() {
		return gpa;
	}

	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	@Override
	public String toString() {
		return "StudentBean [id=" + id + ", name=" + name + ", gpa=" + gpa + ", age=" + age + ", email=" + email
				+ ", enrollmentDate=" + enrollmentDate + "]";
	}
}
