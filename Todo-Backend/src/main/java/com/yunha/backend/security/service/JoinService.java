package com.yunha.backend.security.service;


import com.yunha.backend.entity.User;
import com.yunha.backend.security.dto.JoinDTO;
import com.yunha.backend.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public void joinUser(JoinDTO joinDTO) throws Exception {
        Boolean isExist = userRepository.existsByUserId(joinDTO.getUserId());
        if(isExist){    //이미 아이디가 존재한다면?
            throw new Exception("이미 존재하는 아이디입니다.");
        }
        //유저 정보가 없다면?
        try{
            User data = new User();
            data.setUserId(joinDTO.getUserId());
            data.setUserPw(bCryptPasswordEncoder.encode(joinDTO.getUserPw()));    //비밀번호 인코딩
            data.setUserRole("ROLE_USER");  //모든 유저를 어드민으로
            userRepository.save(data);
        } catch (Exception e){
            throw new Exception("회원가입 중 알 수 없는 에러가 발생했습니다. : "+e.getMessage());
        }
    }
}
