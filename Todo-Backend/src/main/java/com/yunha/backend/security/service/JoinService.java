package com.yunha.backend.security.service;


import com.yunha.backend.dto.FindAccountDTO;
import com.yunha.backend.entity.User;
import com.yunha.backend.security.dto.CustomUserDetails;
import com.yunha.backend.security.dto.JoinDTO;
import com.yunha.backend.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender javaMailSender;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.javaMailSender = javaMailSender;
    }

    @Transactional
    public void joinUser(JoinDTO joinDTO) throws Exception {
        Boolean isExist = userRepository.existsByUserIdAndUserNickname(joinDTO.getUserId(),joinDTO.getUserNickname());
        if(isExist){    //이미 아이디가 존재한다면?
            throw new Exception("이미 존재하는 아이디,닉네임입니다.");
        }
        //유저 정보가 없다면?
        try{
            User data = new User();
            data.setUserId(joinDTO.getUserId());
            data.setUserNickname(joinDTO.getUserNickname());
            data.setUserPw(bCryptPasswordEncoder.encode(joinDTO.getUserPw()));    //비밀번호 인코딩
            data.setUserRole("ROLE_USER");  //모든 유저를 어드민으로
            System.out.println(data);
            userRepository.save(data);
        } catch (Exception e){
            throw new Exception("회원가입 중 알 수 없는 에러가 발생했습니다. : "+e.getMessage());
        }
    }

    @Transactional
    public void modifyPassword(JoinDTO joinDTO, CustomUserDetails user) throws Exception {
        try{
            User data = new User();
            data.setUserId(user.getUsername());
            data.setUserCode(user.getUserCode());
            data.setUserNickname(user.getUserNickname());
            data.setUserRole("ROLE_USER");
            data.setUserPw(bCryptPasswordEncoder.encode(joinDTO.getUserPw()));
            userRepository.save(data);
        } catch (Exception e){
            throw new Exception("알 수 없는 오류");
        }
    }

    @Transactional
    public void modifyNickname(JoinDTO joinDTO, CustomUserDetails user) throws Exception {
        try{
            User userEntity = userRepository.findByUserCode(user.getUserCode());
            userEntity.setUserNickname(joinDTO.getUserNickname());
            userRepository.save(userEntity);
        } catch (Exception e){
            throw new Exception("알 수 없는 오류");
        }
    }

    public boolean findAccount(FindAccountDTO account) {
        boolean isExist = userRepository.existsByUserIdAndUserEmail(account.getUserId(),account.getUserEmail());
        if(isExist){
            String randomNumber = "1234";

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(account.getUserEmail());
            message.setSubject("Todo 비밀번호 찾기 이메일 인증입니다.");
            message.setText(randomNumber);
            javaMailSender.send(message);

            //randomNumber랑 userCode DB 저장
            return true;
        } else {
            return false;
        }
    }
}
