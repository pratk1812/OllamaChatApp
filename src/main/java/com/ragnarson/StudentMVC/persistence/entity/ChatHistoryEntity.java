package com.ragnarson.StudentMVC.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
    name = "chat_history",
    indexes = {
      @Index(name = "idx_chat_history", columnList = "user_id"),
      @Index(name = "idx_chat_history_user_id_time_stamp", columnList = "user_id, time_stamp"),
      @Index(
          name = "idx_chat_history_chat",
          columnList = "user_id, chat_id, time_stamp")
    })
public class ChatHistoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private String userId;

  @Column(name = "chat_id")
  private String chatId;

  @Column(name = "message_type", nullable = false)
  private String messageType;

  @Column(name = "time_stamp", nullable = false)
  @JdbcTypeCode(SqlTypes.TIMESTAMP)
  private LocalDateTime timeStamp;

  @Column(name = "content", columnDefinition = "LONGTEXT")
  private String content;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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
    if (!(o instanceof ChatHistoryEntity that)) return false;

    return id.equals(that.id)
        && userId.equals(that.userId)
        && chatId.equals(that.chatId)
        && messageType.equals(that.messageType)
        && timeStamp.equals(that.timeStamp);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + userId.hashCode();
    result = 31 * result + chatId.hashCode();
    result = 31 * result + messageType.hashCode();
    result = 31 * result + timeStamp.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "ChatHistoryEntity{"
        + "id="
        + id
        + ", userId='"
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
