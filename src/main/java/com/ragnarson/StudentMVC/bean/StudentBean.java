package com.ragnarson.StudentMVC.bean;

import com.ragnarson.StudentMVC.enums.Major;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class StudentBean implements Serializable{

	private static final long serialVersionUID = 8565523033516376541L;

	private Long id = -1L;
	
	@NotEmpty(message = "Value cannot be Empty")
	@Size(min = 3, max = 30, message = "Size must be between 3 and 30")
	private String name;
	
	@DecimalMin(value = "0.0", message = "GPA value cannot be below 0")
	@DecimalMax(value = "10.0", message = "GPA value cannot be above 10")
	private double gpa;
	
	@Min(value = 18, message = "Age cannot be below 18")
	@Max(value = 23, message = "Age cannot be above 23")
	private int age;
	
	@NotEmpty(message = "Value cannot be Empty")
	@Email(message = "Invalid email string")
	private String email;
	
	@NotNull(message = "Invalid Major")
	private Major major;
	
	@DateTimeFormat(iso = ISO.DATE)
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

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	@Override
	public String toString() {
		return "StudentBean [id=" + id + ", name=" + name + ", gpa=" + gpa + ", age=" + age + ", email=" + email
				+ ", major=" + major + ", enrollmentDate=" + enrollmentDate + "]";
	}

	
}
