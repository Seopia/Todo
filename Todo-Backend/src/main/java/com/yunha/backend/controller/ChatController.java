package com.yunha.backend.controller;

import com.yunha.backend.dto.ChatDTO;
import com.yunha.backend.entity.Chat;
import com.yunha.backend.entity.User;
import com.yunha.backend.repository.ChatRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    private final ChatRepository chatRepository;

    public ChatController(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    /**
     * request 시 message만 DTO로 받고 유저 정보는 필터단에서 JWT토큰으로 얻어온다.
     * response 시 유저의 닉네임과, 메세지 내용을 전달한다.
     * 좀 더 효율적으로 바꿔야 한다. 고민해봅시다.
     * @param message
     * @param headerAccessor
     * @return
     */
    @MessageMapping("/all")
    @SendTo("/topic/all")
    public Chat allChat(ChatDTO message, SimpMessageHeaderAccessor headerAccessor){
        //요청이 왔다.
        Chat chat = new Chat();
        chat.setChatMessage(message.getMessage());
        chat.setUser(new User((long)headerAccessor.getSessionAttributes().get("userCode")));
        chatRepository.save(chat);

        return chat;
    }
}
