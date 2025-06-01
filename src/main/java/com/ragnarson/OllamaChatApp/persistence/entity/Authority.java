package com.ragnarson.OllamaChatApp.persistence.entity;

import com.ragnarson.OllamaChatApp.enums.Roles;
import jakarta.persistence.Embeddable;
import java.io.Serial;
import org.springframework.security.core.GrantedAuthority;

@Embeddable
public class Authority implements GrantedAuthority {

  @Serial private static final long serialVersionUID = 8105496665857811927L;

  private String authority;

  public Authority() {
    super();
  }

  public Authority(String authority) {
    super();
    this.authority = authority;
  }

  public Authority(Roles role) {
    authority = role.name();
  }

  @Override
  public String getAuthority() {
    return this.authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }

  @Override
  public String toString() {
    return "Authority [authority=" + authority + "]";
  }
}
