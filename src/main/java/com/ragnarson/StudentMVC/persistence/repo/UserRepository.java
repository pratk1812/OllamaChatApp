package com.ragnarson.StudentMVC.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ragnarson.StudentMVC.persistence.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  UserEntity findByUsername(String username);

  boolean existsByUsername(String username);
}
