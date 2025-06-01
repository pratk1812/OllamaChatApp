package com.ragnarson.OllamaChatApp.service;

import com.fasterxml.uuid.Generators;
import com.ragnarson.OllamaChatApp.mapper.ChatHistoryMapper;
import com.ragnarson.OllamaChatApp.model.bean.ChatHistoryDTO;
import com.ragnarson.OllamaChatApp.persistence.entity.ChatHistoryEntity;
import com.ragnarson.OllamaChatApp.persistence.repo.ChatHistoryRepository;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatHistoryService {
  private static final Logger LOGGER = LogManager.getLogger(ChatHistoryService.class);

  private final ChatHistoryRepository chatHistoryRepository;

  @Autowired
  public ChatHistoryService(ChatHistoryRepository chatHistoryRepository) {
    this.chatHistoryRepository = chatHistoryRepository;
  }

  public void saveUserMessage(String userId, String chatId, String message) {
    LOGGER.info("Saving user message");
    ChatHistoryEntity chatHistory = new ChatHistoryEntity();
    chatHistory.setUserId(userId);
    chatHistory.setChatId(chatId);
    chatHistory.setMessageType(MessageType.USER.getValue());
    chatHistory.setTimeStamp(LocalDateTime.now());
    chatHistory.setContent(message);
    chatHistoryRepository.save(chatHistory);
  }

  public void saveAssistantMessage(String userId, String chatId, String message) {
    LOGGER.info("Saving assistant message");
    ChatHistoryEntity chatHistory = new ChatHistoryEntity();
    chatHistory.setUserId(userId);
    chatHistory.setChatId(chatId);
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
        : allChatHistory.stream()
            .map(ChatHistoryMapper::mapChatHistory)
            .sorted(Comparator.comparing(ChatHistoryDTO::getTimeStamp))
            .toList();
  }

  public List<ChatHistoryDTO> findAll(String userId) {
    LOGGER.info("fetching chat history");
    List<ChatHistoryEntity> allChatHistory = chatHistoryRepository.findByUserId(userId);

    return allChatHistory == null
        ? List.of()
        : allChatHistory.stream().map(ChatHistoryMapper::mapChatHistory).toList();
  }

  public Map<String, List<ChatHistoryDTO>> findAllChatHistory(String userId) {
    LOGGER.info("fetching chat history map");
    return findAll(userId).stream().collect(Collectors.groupingBy(ChatHistoryDTO::getChatId));
  }

  public String newChat() {
    return Generators.timeBasedEpochGenerator().generate().toString();
  }

  @Transactional
  public void deleteChat(String chatId, String name) {
    chatHistoryRepository.deleteByUserIdAndChatId(name, chatId);
  }
}
