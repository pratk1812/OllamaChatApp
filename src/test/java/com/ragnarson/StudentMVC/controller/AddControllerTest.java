package com.ragnarson.StudentMVC.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;


import java.util.GregorianCalendar;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ragnarson.StudentMVC.bean.StudentBean;
import com.ragnarson.StudentMVC.enums.Major;
import com.ragnarson.StudentMVC.service.StudentService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class AddControllerTest {

	@Autowired
	private UserDetailsService userDetailsServiceTest;
	
	@Autowired
	private WebApplicationContext context;

	@MockBean
	private StudentService studentService;

	private MockMvc mockMvc;

	private Stream<UserDetails> allUsers() {
		return Stream.of(
				User.withUsername("test")
				.password("test")
				.authorities(new SimpleGrantedAuthority("ADMIN"))
				.build(),
			User.withUsername("test")
				.password("test")
				.authorities(new SimpleGrantedAuthority("USER"))
				.build());
	}

	@BeforeAll
	public void setup() {
		final WebApplicationContext context2 = context;
		if (context2 != null) {
			mockMvc = MockMvcBuilders
					.webAppContextSetup(context2)
					.apply(SecurityMockMvcConfigurers.springSecurity())
					.build();
		} else {
			throw null;
		}
	}

	@SuppressWarnings("null")
	@ParameterizedTest
	@MethodSource("allUsers")
	public void testSave(UserDetails userDetails) throws Exception {
		when(studentService.saveStudent(any(StudentBean.class))).thenReturn(1L);

		StudentBean bean = new StudentBean();
		bean.setAge(22);
		bean.setEmail("qwerty@test.com");
		bean.setEnrollmentDate(new GregorianCalendar(2022, 8, 4).getTime());
		bean.setGpa(22.55);
		bean.setMajor(Major.MATH);
		bean.setName("TEST");

		if (userDetails != null) {
			mockMvc.perform(post("/addStudent/save")
					.with(user(userDetails))
					.sessionAttr("studentBean", bean))
					.andExpect(status().is2xxSuccessful());
		} else {
			throw new IllegalArgumentException("UserDetails cannot be null");
		}
	}

	@SuppressWarnings("null")
	@ParameterizedTest
	@MethodSource("allUsers")
	public void testLoad(UserDetails userDetails) throws Exception {
		if (userDetails != null) {
			mockMvc.perform(get("/addStudent/load")
					.with(user(userDetails)))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("addStudent"))
			.andExpect(model().attributeExists("studentBean"));
		} else {
			throw new IllegalArgumentException("UserDetails cannot be null");
		}
	}

}
