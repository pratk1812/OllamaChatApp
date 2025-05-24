package com.ragnarson.StudentMVC.service;

import com.ragnarson.StudentMVC.enums.Major;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class ReadStudentParams implements Serializable{

	private static final long serialVersionUID = 5986514814221815281L;
	
	@DecimalMin(value = "0.0", message = "GPA value cannot be below 0")
	@DecimalMax(value = "10.0", message = "GPA value cannot be above 10")
	private double fromGPA;
	@DecimalMin(value = "0.0", message = "GPA value cannot be below 0")
	@DecimalMax(value = "10.0", message = "GPA value cannot be above 10")
	private double toGPA;
	
	@Min(value = 18, message = "Age cannot be below 18")
	@Max(value = 23, message = "Age cannot be above 23")
	private int fromAge;
	@Min(value = 18, message = "Age cannot be below 18")
	@Max(value = 23, message = "Age cannot be above 23")
	private int toAge;
	
	@DateTimeFormat(iso = ISO.DATE)
	private Date fromDate;
	@DateTimeFormat(iso = ISO.DATE)
	private Date toDate;
	
	private Major major;

	public double getFromGPA() {
		return fromGPA;
	}

	public void setFromGPA(double fromGPA) {
		this.fromGPA = fromGPA;
	}

	public double getToGPA() {
		return toGPA;
	}

	public void setToGPA(double toGPA) {
		this.toGPA = toGPA;
	}

	public int getFromAge() {
		return fromAge;
	}

	public void setFromAge(int fromAge) {
		this.fromAge = fromAge;
	}

	public int getToAge() {
		return toAge;
	}

	public void setToAge(int toAge) {
		this.toAge = toAge;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
}
