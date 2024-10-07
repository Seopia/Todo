package com.yunha.backend.security.service;

import com.yunha.backend.dto.FindAccountDTO;
import com.yunha.backend.entity.PasswordToken;
import com.yunha.backend.entity.User;
import com.yunha.backend.security.dto.FindSecurityNumberDTO;
import com.yunha.backend.security.dto.PasswordChangeDTO;
import com.yunha.backend.security.repository.PasswordTokenRepository;
import com.yunha.backend.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AccountFinderService {
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordTokenRepository passwordTokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public AccountFinderService(UserRepository userRepository, JavaMailSender javaMailSender, PasswordTokenRepository passwordTokenRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
        this.passwordTokenRepository = passwordTokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Transactional
    public boolean findAccount(FindAccountDTO account) {
        boolean isExist = userRepository.existsByUserIdAndUserEmail(account.getUserId(),account.getUserEmail());
        if(isExist){
            String randomNumber = String.valueOf(new Random().nextInt((99999-10000)+1)+10000);


            SimpleMailMessage message = new SimpleMailMessage();

            try{
                User user = userRepository.findByUserIdAndUserEmail(account.getUserId(),account.getUserEmail());

                message.setTo(account.getUserEmail());
                message.setFrom("wjdwltjq7289@naver.com");
                message.setSubject("[Todo] 이메일 인증");
                String text = "안녕하세요 "+user.getUserNickname()+"님! Todo 비밀번호 찾기 이메일 인증입니다."+randomNumber;
                message.setText(text);

                javaMailSender.send(message);

                //randomNumber랑 userCode DB 저장
                PasswordToken passwordToken = new PasswordToken(user, LocalDateTime.now().plusMinutes(10), randomNumber);
                try{
                    passwordTokenRepository.deleteByUser(user);
                } catch (EmptyResultDataAccessException e){
                    System.out.println("찾을 수 없음");
                }
                passwordTokenRepository.save(passwordToken);
                return true;
            } catch (MailException e){
                System.err.println(e.getMessage());
                System.err.println(e.getCause());
                return false;
            }

        } else {
            return false;
        }
    }

    public boolean checkSecurityNumber(FindSecurityNumberDTO findSecurityNumberDTO) {
        User user = userRepository.findByUserIdAndUserEmail(findSecurityNumberDTO.getUserId(),findSecurityNumberDTO.getUserEmail());
        return passwordTokenRepository.existsByPasswordTokenTokenAndUser(findSecurityNumberDTO.getToken(),user);
    }

    public void modifyPassword(PasswordChangeDTO passwordChangeDTO) {
        User user = userRepository.findByUserIdAndUserEmail(passwordChangeDTO.getId(),passwordChangeDTO.getEmail());
        user.setUserPw(bCryptPasswordEncoder.encode(passwordChangeDTO.getPassword()));
        userRepository.save(user);
    }
}
