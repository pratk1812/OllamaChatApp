package com.ragnarson.StudentMVC.web;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import com.ragnarson.StudentMVC.model.bean.ChatHistoryDTO;
import com.ragnarson.StudentMVC.model.request.MessageRequest;
import com.ragnarson.StudentMVC.model.response.MessageResponse;
import com.ragnarson.StudentMVC.service.ChatHistoryService;
import com.ragnarson.StudentMVC.service.ChatService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChatController {

  private static final Logger LOGGER = LogManager.getLogger(ChatController.class);

  private final ChatService chatService;
  private final ChatHistoryService chatHistoryService;
  private static final String CHAT_ID = "chatId";
  private static final String USER_ID = "userId";

  @Autowired
  public ChatController(ChatService chatService, ChatHistoryService chatHistoryService) {
    this.chatService = chatService;
      this.chatHistoryService = chatHistoryService;
  }

  @MessageMapping("/hello-user")
  @SendToUser("/queue/specific-user")
  public MessageResponse generateMessage(MessageRequest message, Principal user) {
    LOGGER.info("request : {}", message);
    return chatService.generateMessage(message.getContent());
  }

  @GetMapping("/chat-new")
  public ModelAndView getChat(Principal user) {
    ModelAndView modelAndView = new ModelAndView("chat");
    modelAndView.addObject(CHAT_ID, chatHistoryService.newChat());
    return new ModelAndView();
  }

  @GetMapping("/chat")
  public ModelAndView getChat(@RequestParam String chatId, Principal user) {
    Map<String, List<ChatHistoryDTO>> chayHistory = chatHistoryService.findAllChatHistory(user.getName(), chatId);
    ModelAndView modelAndView = new ModelAndView("chat");
    modelAndView.addObject(CHAT_ID, chatHistoryService.newChat());
    return new ModelAndView();
  }
}
