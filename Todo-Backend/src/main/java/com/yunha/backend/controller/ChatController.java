package com.yunha.backend.controller;

import com.yunha.backend.dto.ChatDTO;
import com.yunha.backend.dto.ChatResponseDTO;
import com.yunha.backend.entity.Chat;
import com.yunha.backend.entity.User;
import com.yunha.backend.repository.ChatRepository;
import jakarta.transaction.Transactional;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    private final ChatRepository chatRepository;
    private final SimpMessagingTemplate template;

    public ChatController(ChatRepository chatRepository, SimpMessagingTemplate template) {
        this.chatRepository = chatRepository;
        this.template = template;
    }

    /**
     * /app/all을 Mapping한다.
     * /topic/all을 구독한 독자에게 보낸다.
     * @param message Long userCode, String message
     * @return ChatResponseDTO
     */
    @MessageMapping("/all")
    @Transactional
    public void allChat(ChatDTO message){
        try {
            Chat chat = new Chat(new User(message.getUserCode()), message.getMessage());
            chatRepository.save(chat);
            template.convertAndSend("/topic/all",new ChatResponseDTO(message.getUserNickname(),chat.getChatMessage()));
        } catch (Exception e){
            template.convertAndSend("/topic/all",new ChatResponseDTO("관리자", "에러가 발생했습니다."));
        }
    }
}
