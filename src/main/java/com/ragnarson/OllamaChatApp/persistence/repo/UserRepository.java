package com.ragnarson.OllamaChatApp.persistence.repo;

import com.ragnarson.OllamaChatApp.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  UserEntity findByUsername(String username);

  boolean existsByUsername(String username);
}
