package com.ragnarson.StudentMVC.persistence.repo;

import java.util.List;

import com.ragnarson.StudentMVC.persistence.entity.ChatHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatHistoryRepository extends JpaRepository<ChatHistoryEntity, Long> {
  List<ChatHistoryEntity> findByUserIdAndChatId(String userId, String chatId);

  List<ChatHistoryEntity> findByUserId(String userId);

  void deleteByUserIdAndChatId(String userId, String chatId);
}
