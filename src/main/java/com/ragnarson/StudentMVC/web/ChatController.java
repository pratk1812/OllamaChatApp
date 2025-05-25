package com.ragnarson.StudentMVC.web;

import java.security.Principal;

import com.ragnarson.StudentMVC.model.request.MessageRequest;
import com.ragnarson.StudentMVC.model.response.MessageResponse;
import com.ragnarson.StudentMVC.service.ChatService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChatController {

  private static final Logger LOGGER = LogManager.getLogger(ChatController.class);

  private final ChatService chatService;

  @Autowired
  public ChatController(ChatService chatService) {
    this.chatService = chatService;
  }

  @MessageMapping("/hello-user")
  @SendToUser("/queue/specific-user")
  public MessageResponse generateMessage(MessageRequest message, Principal user) {
    LOGGER.info("request : {}", message);
    return chatService.generateMessage(message.getContent());
  }

  @GetMapping("/chat")
  public ModelAndView getChat() {
    return new ModelAndView("chat");
  }
}
