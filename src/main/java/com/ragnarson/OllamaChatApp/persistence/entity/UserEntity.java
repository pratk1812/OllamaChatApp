package com.ragnarson.OllamaChatApp.persistence.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @ElementCollection(targetClass = Authority.class)
  @CollectionTable(name = "user_authorities")
  private List<Authority> authorities;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Authority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(List<Authority> authorities) {
    this.authorities = authorities;
  }

  public void setAuthoritiesAll(Collection<? extends GrantedAuthority> authorities) {
    this.authorities = authorities.stream().map(x -> new Authority(x.getAuthority())).toList();
  }

  @Override
  public String toString() {
    return "UserEntity [id="
        + id
        + ", username="
        + username
        + ", password="
        + password
        + ", authorities="
        + authorities
        + "]";
  }
}
