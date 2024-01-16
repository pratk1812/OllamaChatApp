package com.ragnarson.StudentMVC.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter.HeaderValue;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import com.ragnarson.StudentMVC.enums.Roles;
import com.ragnarson.StudentMVC.service.UserService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class MySecConfig {

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}
	@Bean
    public PasswordEncoder passwordEncoder() {
        //return NoOpPasswordEncoder.getInstance();
		return new BCryptPasswordEncoder();
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
    	final String REPORT_TO = "{\"group\":\"csp-violation-report\",\"max_age\":2592000,\"endpoints\":[{\"url\":\"https://localhost:8080/report\"}]}";
    	http
    	.sessionManagement(session->session
    			.enableSessionUrlRewriting(false)
    			.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
    			.sessionConcurrency(concurrency->concurrency
    					.maximumSessions(1)
    					.maxSessionsPreventsLogin(true))
    			.sessionFixation().changeSessionId())
    	.headers(headers->headers
    			.addHeaderWriter(new StaticHeadersWriter("Report-to", REPORT_TO))
        		.xssProtection(xss->xss
        				.headerValue(HeaderValue.ENABLED_MODE_BLOCK))
        		.contentSecurityPolicy(policy->policy
        				.policyDirectives("form-action 'self'; style-src 'self'; script-src 'self'; report-to csp-violation-report")))
        .authenticationProvider(authenticationProvider)
        .authorizeHttpRequests(authorize -> authorize
        	.requestMatchers("/static/**").permitAll()
            .requestMatchers(RegexRequestMatcher.regexMatcher("/(addStudent|readStudent)/[A-Za-z0-9]+")).hasAuthority(Roles.USER.name())
            .requestMatchers(RegexRequestMatcher.regexMatcher("/(deleteStudent|updateStudent)/[A-Za-z0-9]+")).hasAuthority(Roles.ADMIN.name())
            .requestMatchers("/").permitAll()
            .anyRequest().permitAll())
        .formLogin(form->form
        		.loginPage("/login")
        		.failureUrl("/login?error=true")
        		.permitAll())
        .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID"));

        return http.build();
    }
}
