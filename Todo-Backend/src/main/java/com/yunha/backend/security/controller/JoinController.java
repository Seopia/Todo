package com.yunha.backend.security.controller;

import com.yunha.backend.dto.FindAccountDTO;
import com.yunha.backend.security.dto.CustomUserDetails;
import com.yunha.backend.security.dto.JoinDTO;
import com.yunha.backend.security.service.JoinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinUser(JoinDTO joinDTO){
        try {
            joinService.joinUser(joinDTO);
            return ResponseEntity.ok().body("회원가입에 성공했습니다. 로그인 해주세요.");
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("회원 가입에 실패했습니다. : "+e.getMessage());
        }
    }
    @PostMapping("/password")
    public ResponseEntity<String> modifyPassword(JoinDTO joinDTO, @AuthenticationPrincipal CustomUserDetails user){
        try{
            joinService.modifyPassword(joinDTO,user);
            return ResponseEntity.ok().body("비밀번호가 변경되었습니다. 다시 로그인 해주세요.");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/nickname")
    public ResponseEntity<String> modifyNickname(JoinDTO joinDTO,@AuthenticationPrincipal CustomUserDetails user){
        try{
            joinService.modifyNickname(joinDTO,user);
            return ResponseEntity.ok().body("닉네임 변경에 성공했습니다. 다시 로그인해주세요.");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




}
