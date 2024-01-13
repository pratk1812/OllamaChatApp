package com.ragnarson.StudentMVC.service;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ragnarson.StudentMVC.entity.UserEntity;
import com.ragnarson.StudentMVC.enums.Roles;
import com.ragnarson.StudentMVC.repo.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService {
	
	private static Logger log = LogManager.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Loading user : " + username);
		UserEntity entity = userRepository.findByUsername(username);
		
		if(entity==null)
			throw new UsernameNotFoundException("user not found : " + username);
		
		log.info("user returned : " + entity);
		Collection<GrantedAuthority> authorities = entity.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.name()))
				.collect(Collectors.toList());
		
		return new MyUserPrincipal(entity.getUsername(), entity.getPassword(), authorities);
	}

	public boolean registerNew(String username, String password) {
		log.info("Username : " + username + " Password : " + password);
		UserEntity entity = userRepository.findByUsername(username);
		if(entity!=null) {
			log.warn("user already exists " + entity);
			return false;
		}else {
			try {
				entity = new UserEntity();
				entity.setUsername(username);
				entity.setPassword(passwordEncoder.encode(password));
				entity.setRoles(Collections.singletonList(Roles.USER));
				userRepository.save(entity);
			}catch (Exception e) {
				log.error("Exception caught " + e);
				return false;
			}
			log.info("save successful");
			return true;
		}
	}

}
