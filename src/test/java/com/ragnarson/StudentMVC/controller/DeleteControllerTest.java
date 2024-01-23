package com.ragnarson.StudentMVC.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;


import java.util.Collection;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.ragnarson.StudentMVC.TestSecurityConfiguration;
import com.ragnarson.StudentMVC.service.StudentService;
import com.ragnarson.StudentMVC.web.DeleteStudentController;

@WebMvcTest(DeleteStudentController.class)
@TestInstance(Lifecycle.PER_CLASS)
@Import(TestSecurityConfiguration.class)
public class DeleteControllerTest {

	@MockBean
	private StudentService studentService;

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	InMemoryUserDetailsManager userService;
	
	private static GrantedAuthority adminAuthority = new SimpleGrantedAuthority("ADMIN");
	
	public Stream<UserDetails> testUsers() {
		return Stream.of(
					userService.loadUserByUsername("admin"),
					userService.loadUserByUsername("user"));
	}

	@ParameterizedTest
	@MethodSource("testUsers")
	public void testLoad(UserDetails userDetails) throws Exception {
		ResultActions resultActions = mockMvc
				.perform(get("/deleteStudent/load")
						.with(user(userDetails)));

		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		
		if (authorities.contains(adminAuthority)) {
			resultActions.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("deleteStudent"));
		}else {
			resultActions.andExpect(status().is4xxClientError());
		}
	}
	@Test
	@WithAnonymousUser
	public void testLoadAnonymous() throws Exception {
		mockMvc.perform(get("/deleteStudent/load"))
			.andExpect(redirectedUrlPattern("**/login"))
			.andExpect(status().is3xxRedirection());
	}
	@ParameterizedTest
	@MethodSource("testUsers")
	public void testDelete(UserDetails userDetails) throws Exception {
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		
		if (authorities.contains(adminAuthority)) {
			mockMvc.perform(post("/deleteStudent/login")
					.with(user(userDetails))
					.param("username", userDetails.getUsername())
					.param("password", userDetails.getPassword()))
			.andExpect(status().is2xxSuccessful());
		}else {
			mockMvc.perform(post("/deleteStudent/login"))
			.andExpect(status().is4xxClientError());
		}
	}
	
	@Test
	@WithAnonymousUser
	public void testDeleteAnonymous() throws Exception{
		mockMvc.perform(post("/deleteStudent/login"))
		.andExpect(status().is4xxClientError());
	}
}