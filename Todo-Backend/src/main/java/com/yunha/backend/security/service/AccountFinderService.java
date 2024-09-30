package com.yunha.backend.security.service;

import com.yunha.backend.dto.FindAccountDTO;
import com.yunha.backend.security.repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class AccountFinderService {
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    public AccountFinderService(UserRepository userRepository, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
    }

    public boolean findAccount(FindAccountDTO account) {
        boolean isExist = userRepository.existsByUserIdAndUserEmail(account.getUserId(),account.getUserEmail());
        if(isExist){
            String randomNumber = "1234";
            String text = "Todo 비밀번호 찾기 이메일 인증입니다."+randomNumber;

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(account.getUserEmail());
            message.setSubject("Todo 비밀번호 찾기 이메일 인증입니다.");
            message.setText(text);
//            javaMailSender.send(message);

            //randomNumber랑 userCode DB 저장
            return true;
        } else {
            return false;
        }
    }
}
