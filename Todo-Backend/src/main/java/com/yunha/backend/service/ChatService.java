package com.yunha.backend.service;

import com.yunha.backend.entity.Chat;
import com.yunha.backend.repository.ChatRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Page<Chat> getPastMessage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "chatCode");
        return chatRepository.findAll(pageable);
    }
}
