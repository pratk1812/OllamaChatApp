package com.ragnarson.StudentMVC.model.bean;

import java.time.LocalDateTime;

public class ChatHistoryDTO {

  private String userId;
  private String chatId;
  private String messageType;
  private LocalDateTime timeStamp;
  private String content;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getChatId() {
    return chatId;
  }

  public void setChatId(String chatId) {
    this.chatId = chatId;
  }

  public String getMessageType() {
    return messageType;
  }

  public void setMessageType(String messageType) {
    this.messageType = messageType;
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
    if (!(o instanceof ChatHistoryDTO that)) return false;

    return userId.equals(that.userId)
        && chatId.equals(that.chatId)
        && messageType.equals(that.messageType)
        && timeStamp.equals(that.timeStamp);
  }

  @Override
  public int hashCode() {
    int result = userId.hashCode();
    result = 31 * result + chatId.hashCode();
    result = 31 * result + messageType.hashCode();
    result = 31 * result + timeStamp.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "ChatHistoryDTO{"
        + "userId='"
        + userId
        + '\''
        + ", chatId='"
        + chatId
        + '\''
        + ", messageType='"
        + messageType
        + '\''
        + ", timeStamp="
        + timeStamp
        + ", contentSize='"
        + content.length()
        + '\''
        + '}';
  }
}
