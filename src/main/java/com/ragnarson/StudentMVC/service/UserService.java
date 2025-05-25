package com.ragnarson.StudentMVC.service;

import com.ragnarson.StudentMVC.persistence.entity.Authority;
import com.ragnarson.StudentMVC.persistence.entity.UserEntity;
import com.ragnarson.StudentMVC.enums.Roles;
import com.ragnarson.StudentMVC.persistence.repo.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService implements UserDetailsManager {
	
	private static final Logger log = LogManager.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Loading user : " + username);
		UserEntity entity = userRepository.findByUsername(username);
		
		if(entity==null) {
			log.info("user not found");
			throw new UsernameNotFoundException("user not found : " + username);
		}
		return new User(entity.getUsername(), entity.getPassword(), entity.getAuthorities());
	}

	@Override
	public void createUser(UserDetails user) {
		if(user==null) {
			log.info("user invalid");
			return;
		}
		
		log.info("Creating user");
		UserEntity userEntity = new UserEntity();
		userEntity.setUsername(user.getUsername());
		userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
		userEntity.setAuthoritiesAll(user.getAuthorities());
		userRepository.save(userEntity);
	}

	@Override
	public void updateUser(UserDetails user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean userExists(String username) {
		return userRepository.existsByUsername(username);
	}

	public void register(String username, String password, List<Roles> roles) throws ServiceException {
		
		log.info("found : " + userRepository.findByUsername(username));
		
		boolean checkParams = (username==null || username.isEmpty()) || (password==null || password.isEmpty()) || (roles==null || roles.isEmpty());
		if (checkParams) {
			throw new ServiceException("Bad Credentials");
		}else if (userRepository.findByUsername(username)!=null) {
			throw new ServiceException("User already exists");
		}else {
			UserEntity entity = new UserEntity();
			entity.setUsername(username);
			entity.setPassword(password);
			entity.setAuthorities(roles.stream().map(x -> new Authority(x)).toList());
			userRepository.save(entity);
		}
	}
}
