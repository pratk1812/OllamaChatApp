package com.ragnarson.StudentMVC.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ragnarson.StudentMVC.model.bean.ChatHistoryDTO;
import com.ragnarson.StudentMVC.model.response.MessageResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
  private static final Logger LOGGER = LogManager.getLogger(ChatService.class);

  private final ChatHistoryService chatHistoryService;
  private final OllamaChatModel ollamaChatModel;

  @Autowired
  public ChatService(ChatHistoryService chatHistoryService, OllamaChatModel ollamaChatModel) {
    this.chatHistoryService = chatHistoryService;
    this.ollamaChatModel = ollamaChatModel;
  }

  public MessageResponse generateMessage(String userId, String chatId, String message) {

    chatHistoryService.saveUserMessage(userId, chatId, message);

    List<ChatHistoryDTO> allChatHistoryDTO = chatHistoryService.findAll(userId, chatId);

    List<Message> allMessages =
        allChatHistoryDTO.stream()
            .map(
                dto ->
                    (Message)
                        switch (MessageType.fromValue(dto.getMessageType())) {
                          case USER -> new UserMessage(dto.getContent());
                          case ASSISTANT -> new AssistantMessage(dto.getContent());
                          default -> throw new RuntimeException("Invalid type");
                        })
            .toList();

    ChatResponse chatResponse = ollamaChatModel.call(new Prompt(allMessages));

    chatHistoryService.saveAssistantMessage(userId, chatId, chatResponse.getResult().getOutput().getText());

    MessageResponse messageResponse = new MessageResponse();
    messageResponse.setRole(MessageType.ASSISTANT.getValue());
    messageResponse.setTimeStamp(LocalDateTime.now());
    messageResponse.setContent(chatResponse.getResult().getOutput().getText());

    return messageResponse;
  }
}
