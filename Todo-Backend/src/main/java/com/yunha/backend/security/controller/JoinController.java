package com.yunha.backend.security.controller;

import com.yunha.backend.security.dto.JoinDTO;
import com.yunha.backend.security.service.JoinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinUser(JoinDTO joinDTO){
        try {
            joinService.joinUser(joinDTO);
            return ResponseEntity.ok().body("회원가입 성공");
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("회원 가입에 실패했습니다. : "+e.getMessage());
        }

    }


}
