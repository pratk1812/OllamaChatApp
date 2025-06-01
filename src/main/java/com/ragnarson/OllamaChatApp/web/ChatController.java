package com.ragnarson.OllamaChatApp.web;

import com.ragnarson.OllamaChatApp.model.bean.ChatHistoryDTO;
import com.ragnarson.OllamaChatApp.model.request.MessageRequest;
import com.ragnarson.OllamaChatApp.model.response.MessageResponse;
import com.ragnarson.OllamaChatApp.service.ChatHistoryService;
import com.ragnarson.OllamaChatApp.service.ChatService;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChatController {

  private static final Logger LOGGER = LogManager.getLogger(ChatController.class);
  private static final String CHAT_ID = "chatId";
  private static final String CHAT_HISTORY = "chatHistory";
  private static final String CHAT_LIST = "chatList";
  private final ChatService chatService;
  private final ChatHistoryService chatHistoryService;

  @Autowired
  public ChatController(ChatService chatService, ChatHistoryService chatHistoryService) {
    this.chatService = chatService;
      this.chatHistoryService = chatHistoryService;
  }

  @MessageMapping("/hello-user")
  @SendToUser("/queue/specific-user")
  public MessageResponse generateMessage(MessageRequest message, Principal user) {
    LOGGER.info("request : {}", message);
    return chatService.generateMessage(user.getName(), message.getChatId(), message.getContent());
  }

  @GetMapping("/chat")
  public ModelAndView getChat(@RequestParam(required = false) String chatId, Principal user) {
    LOGGER.info("chatId : {}", chatId);
    ModelAndView modelAndView = new ModelAndView("chat");
    if (user != null) {
      Map<String, List<ChatHistoryDTO>> chayHistory =
              chatHistoryService.findAllChatHistory(user.getName());
      modelAndView.addObject(CHAT_HISTORY, chayHistory);
      if (chatId!=null) {
        List<ChatHistoryDTO> chatHistoryDTOs = chayHistory.getOrDefault(chatId, List.of());
        modelAndView.addObject(CHAT_LIST, chatHistoryDTOs);
      }else {
        chatId = chatHistoryService.newChat();
      }
      modelAndView.addObject(CHAT_ID, chatId);
    }
    return modelAndView;
  }

  @PostMapping("/chat")
  public ModelAndView deleteChat(@RequestParam String deleteChatId, Principal user) {
    LOGGER.info("Deleting chat");
    chatHistoryService.deleteChat(deleteChatId,user.getName());
    return new ModelAndView("redirect:/chat");
  }

  @GetMapping("/")
  public ModelAndView getAllChat(@RequestParam(required = false) String chatId, Principal user) {
      return new ModelAndView("index");
  }
}
