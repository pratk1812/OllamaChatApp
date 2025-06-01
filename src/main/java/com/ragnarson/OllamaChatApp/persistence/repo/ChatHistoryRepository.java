package com.ragnarson.OllamaChatApp.persistence.repo;

import com.ragnarson.OllamaChatApp.persistence.entity.ChatHistoryEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatHistoryRepository extends JpaRepository<ChatHistoryEntity, Long> {
  List<ChatHistoryEntity> findByUserIdAndChatId(String userId, String chatId);

  List<ChatHistoryEntity> findByUserId(String userId);

  void deleteByUserIdAndChatId(String userId, String chatId);
}
