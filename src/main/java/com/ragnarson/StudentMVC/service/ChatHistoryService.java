package com.ragnarson.StudentMVC.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.uuid.Generators;
import com.ragnarson.StudentMVC.mapper.ChatHistoryMapper;
import com.ragnarson.StudentMVC.model.bean.ChatHistoryDTO;
import com.ragnarson.StudentMVC.persistence.entity.ChatHistoryEntity;
import com.ragnarson.StudentMVC.persistence.repo.ChatHistoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatHistoryService {
  private static final Logger LOGGER = LogManager.getLogger(ChatHistoryService.class);

  private final ChatHistoryRepository chatHistoryRepository;

  @Autowired
  public ChatHistoryService(ChatHistoryRepository chatHistoryRepository) {
    this.chatHistoryRepository = chatHistoryRepository;
  }

  public void saveUserMessage(String message) {
    LOGGER.info("Saving user message");
    ChatHistoryEntity chatHistory = new ChatHistoryEntity();
    chatHistory.setUserId("1");
    chatHistory.setChatId("1");
    chatHistory.setMessageType(MessageType.USER.getValue());
    chatHistory.setTimeStamp(LocalDateTime.now());
    chatHistory.setContent(message);
    chatHistoryRepository.save(chatHistory);
  }

  public void saveAssistantMessage(String message) {
    LOGGER.info("Saving assistant message");
    ChatHistoryEntity chatHistory = new ChatHistoryEntity();
    chatHistory.setUserId("1");
    chatHistory.setChatId("1");
    chatHistory.setMessageType(MessageType.ASSISTANT.getValue());
    chatHistory.setTimeStamp(LocalDateTime.now());
    chatHistory.setContent(message);
    chatHistoryRepository.save(chatHistory);
  }

  public List<ChatHistoryDTO> findAll(String userId, String chatId) {
    LOGGER.info("fetching chat history");
    List<ChatHistoryEntity> allChatHistory =
        chatHistoryRepository.findByUserIdAndChatId(userId, chatId);

    return allChatHistory == null
        ? List.of()
        : allChatHistory.stream().map(ChatHistoryMapper::mapChatHistory).toList();
  }

  public Map<String, List<ChatHistoryDTO>> findAllChatHistory(String userId, String chatId) {
    LOGGER.info("fetching chat history map");
    return findAll(userId, chatId)
            .stream().collect(Collectors.groupingBy(ChatHistoryDTO::getChatId));
  }

  public String newChat() {
    return Generators.timeBasedEpochGenerator().generate().toString();
  }
}
