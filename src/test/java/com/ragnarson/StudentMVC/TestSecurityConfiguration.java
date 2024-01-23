package com.ragnarson.StudentMVC;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter.HeaderValue;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import com.ragnarson.StudentMVC.enums.Roles;
import com.ragnarson.StudentMVC.service.UserService;

@TestConfiguration
@EnableWebSecurity
public class TestSecurityConfiguration {

	private static Logger log = LogManager.getLogger(TestSecurityConfiguration.class);

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}
	@Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

	@Bean
	public InMemoryUserDetailsManager userService() {
		return new InMemoryUserDetailsManager(
				User.withUsername("admin")
					.password("admin")
					.authorities(new SimpleGrantedAuthority(Roles.ADMIN.name()),new SimpleGrantedAuthority(Roles.USER.name()))
					//.roles(Roles.ADMIN.name(),Roles.USER.name())
					.build(),
				User.withUsername("user")
					.password("user")
					.authorities(new SimpleGrantedAuthority(Roles.USER.name()))
					.build());
	}
	
    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder, InMemoryUserDetailsManager userService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,DaoAuthenticationProvider authenticationProvider) throws Exception {
    	log.info("Create application SecurityFilterChain");

    	
    	//final String REPORT_TO = "{\"group\":\"csp-violation-report\",\"max_age\":2592000,\"endpoints\":[{\"url\":\"https://localhost:8080/report\"}]}";
    	http
        .authenticationProvider(authenticationProvider)
        .authorizeHttpRequests(authorize -> authorize
        	.requestMatchers("/static/**").permitAll()
            .requestMatchers(RegexRequestMatcher.regexMatcher("/(addStudent|readStudent)/[A-Za-z0-9]+")).hasAuthority(Roles.USER.name())
            .requestMatchers(RegexRequestMatcher.regexMatcher("/(deleteStudent|updateStudent)/[A-Za-z0-9]+")).hasAuthority(Roles.ADMIN.name())
            .requestMatchers("/**").permitAll()
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
