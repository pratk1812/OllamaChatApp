package com.ragnarson.StudentMVC.model.request;

import java.time.LocalDateTime;

public class MessageRequest {
  private String role;
  private LocalDateTime timeStamp;
  private String content;

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public LocalDateTime getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(LocalDateTime timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof MessageRequest that)) return false;

    return role.equals(that.role) && timeStamp.equals(that.timeStamp);
  }

  @Override
  public int hashCode() {
    int result = role.hashCode();
    result = 31 * result + timeStamp.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "MessageResponse{"
        + "role='"
        + role
        + '\''
        + ", timeStamp="
        + timeStamp
        + ", content='"
        + content
        + '\''
        + '}';
  }
}
