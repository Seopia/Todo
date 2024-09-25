package com.yunha.backend.controller;

import com.yunha.backend.dto.ChatDTO;
import com.yunha.backend.dto.ChatResponseDTO;
import com.yunha.backend.entity.Chat;
import com.yunha.backend.entity.User;
import com.yunha.backend.repository.ChatRepository;
import com.yunha.backend.service.ChatService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChatController {
    private final ChatRepository chatRepository;
    private final SimpMessagingTemplate template;
    private final ChatService chatService;

    public ChatController(ChatRepository chatRepository, SimpMessagingTemplate template, ChatService chatService) {
        this.chatRepository = chatRepository;
        this.template = template;
        this.chatService = chatService;
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
    @ResponseBody
    @GetMapping("/chat")
    public ResponseEntity<?> getPastMessage(@RequestParam int page){
        System.out.println("d?");
        Page<Chat> chat = chatService.getPastMessage(page, 10);
        return ResponseEntity.ok().body(chat);
    }
}
