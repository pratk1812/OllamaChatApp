package com.ragnarson.StudentMVC.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ragnarson.StudentMVC.service.ReadStudentParams;
import com.ragnarson.StudentMVC.service.StudentService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class ReadControllerTest {

	@Autowired
	private UserDetailsService userDetailsServiceTest;
	
	@Autowired
	private WebApplicationContext context;

	@MockBean
	private StudentService studentService;

	private MockMvc mockMvc;

	private Stream<UserDetails> allUsers() {
		return Stream.of(
				userDetailsServiceTest.loadUserByUsername("admin"),
				userDetailsServiceTest.loadUserByUsername("user"));
	}

	@BeforeAll
	public void setup() {
		final WebApplicationContext context2 = context;
		if (context2 != null) {
			mockMvc = MockMvcBuilders.webAppContextSetup(context2).build();
		} else {
			throw null;
		}
	}
	
	@SuppressWarnings("null")
	@ParameterizedTest
	@MethodSource("allUsers")
	public void testLoad(UserDetails userDetails) throws Exception {
		mockMvc.perform(get("/readStudent/load")
				.with(user(userDetails)))
		.andExpect(status().is2xxSuccessful())
		.andExpect(view().name("readStudent"))
		.andExpect(model().attributeExists("readStudentParams"));
	}
	
	@SuppressWarnings("null")
	@ParameterizedTest
	@MethodSource("allUsers")
	public void testRead(UserDetails userDetails) throws Exception {
		when(studentService.readByParams(any(ReadStudentParams.class))).thenReturn(null);
		
		if (userDetails!=null) {
			mockMvc.perform(post("/readStudent/read")
					.with(user(userDetails)))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("readStudent"));
		} else {
			throw null;
		}
	}
}
