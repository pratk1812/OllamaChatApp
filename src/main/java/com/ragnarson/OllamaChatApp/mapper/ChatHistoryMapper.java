package com.ragnarson.OllamaChatApp.mapper;

import com.ragnarson.OllamaChatApp.model.bean.ChatHistoryDTO;
import com.ragnarson.OllamaChatApp.persistence.entity.ChatHistoryEntity;

public class ChatHistoryMapper {
  public static ChatHistoryDTO mapChatHistory(ChatHistoryEntity entity) {
    ChatHistoryDTO dto = new ChatHistoryDTO();
    dto.setUserId(entity.getUserId());
    dto.setChatId(entity.getChatId());
    dto.setMessageType(entity.getMessageType());
    dto.setTimeStamp(entity.getTimeStamp());
    dto.setContent(entity.getContent());
    return dto;
  }

  public static ChatHistoryEntity mapChatHistory(ChatHistoryDTO dto) {
    ChatHistoryEntity entity = new ChatHistoryEntity();
    entity.setUserId(dto.getUserId());
    entity.setChatId(dto.getChatId());
    entity.setMessageType(dto.getMessageType());
    entity.setTimeStamp(dto.getTimeStamp());
    entity.setContent(dto.getContent());
    return entity;
  }
}
