package com.ragnarson.StudentMVC.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ragnarson.StudentMVC.service.UserService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class MySecConfig {

	@Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder, UserService userService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,DaoAuthenticationProvider authenticationProvider) throws Exception {
    	http
        .authenticationProvider(authenticationProvider)
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/readStudents").hasRole("USER")
            .requestMatchers("/createStudent", "/updateStudent", "/deleteStudent").hasRole("ADMIN")
            .requestMatchers("/register", "/").permitAll()
            .anyRequest().authenticated())
        .formLogin(form->form
        		.loginPage("/login")
        		.failureUrl("/login?error=true")
        		.permitAll());

        return http.build();
    }
}
