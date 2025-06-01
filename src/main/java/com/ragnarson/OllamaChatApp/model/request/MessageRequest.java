package com.ragnarson.OllamaChatApp.model.request;

import java.time.LocalDateTime;
import java.util.Objects;

public class MessageRequest {
  private String chatId;
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

  public String getChatId() {
    return chatId;
  }

  public void setChatId(String chatId) {
    this.chatId = chatId;
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof MessageRequest that)) return false;

    return Objects.equals(chatId, that.chatId)
        && Objects.equals(role, that.role)
        && Objects.equals(timeStamp, that.timeStamp);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(chatId);
    result = 31 * result + Objects.hashCode(role);
    result = 31 * result + Objects.hashCode(timeStamp);
    return result;
  }
}
