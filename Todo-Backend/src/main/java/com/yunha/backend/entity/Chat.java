package com.yunha.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat")
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    @Id
    @Column(name = "chat_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatCode;
    @JoinColumn(name = "chat_user")
    @ManyToOne
    private User user;
    @Column(name = "chat_message")
    private String chatMessage;
    @Column(name = "chat_time")
    private LocalDateTime chatTime;

    public Chat(User user, String chatMessage, LocalDateTime chatTime) {
        this.user = user;
        this.chatMessage = chatMessage;
        this.chatTime = chatTime;
    }
}
