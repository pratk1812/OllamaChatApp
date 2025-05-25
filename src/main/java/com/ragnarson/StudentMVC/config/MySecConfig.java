package com.ragnarson.StudentMVC.config;

import com.ragnarson.StudentMVC.enums.Roles;
import com.ragnarson.StudentMVC.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter.HeaderValue;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
public class MySecConfig {

	private static final Logger log = LogManager.getLogger(MySecConfig.class);

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}
	@Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
		//return new BCryptPasswordEncoder();
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
    	log.info("Create application SecurityFilterChain");

    	
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
        				.policyDirectives("form-action 'self'; style-src 'self' https://maxcdn.bootstrapcdn.com; script-src 'self' https://code" +
								".jquery.com https://cdn.jsdelivr.net; report-to " +
								"csp-violation-report"))
        		.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
        .authenticationProvider(authenticationProvider)
        .authorizeHttpRequests(authorize -> authorize
        	.requestMatchers("/static/**").permitAll()
            .requestMatchers(RegexRequestMatcher.regexMatcher("/(addStudent|readStudent)/[A-Za-z0-9]+")).hasAuthority(Roles.USER.name())
            .requestMatchers(RegexRequestMatcher.regexMatcher("/(deleteStudent|updateStudent)/[A-Za-z0-9]+")).hasAuthority(Roles.ADMIN.name())
				.requestMatchers("/chat", "/app/**", "/topic/**", "/queue/**", "/gs-guide-websocket").authenticated()
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
    
    @DependsOn("securityFilterChain")
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) {
    	return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
    			.getOrBuild();
    }
}
