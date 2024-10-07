package com.yunha.backend.security.controller;

import com.yunha.backend.dto.FindAccountDTO;
import com.yunha.backend.security.dto.FindSecurityNumberDTO;
import com.yunha.backend.security.dto.PasswordChangeDTO;
import com.yunha.backend.security.service.AccountFinderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountFinderController {
    private final AccountFinderService accountFinderService;

    public AccountFinderController(AccountFinderService accountFinderService) {
        this.accountFinderService = accountFinderService;
    }

    @PostMapping("/find-by-id-email")
    public ResponseEntity<Boolean> findAccount(FindAccountDTO account){
        try{
            boolean isExist = accountFinderService.findAccount(account);
            if(isExist){
                return ResponseEntity.ok().body(true);
            } else {
                return ResponseEntity.ok().body(false);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(false);
        }
    }
    @PostMapping("/find-by-sc-num")
    public ResponseEntity<Boolean> isSecurityNumberCollect(FindSecurityNumberDTO findSecurityNumberDTO){
        boolean isCollect = accountFinderService.checkSecurityNumber(findSecurityNumberDTO);
        if(isCollect){
            return ResponseEntity.ok().body(true);
        } else {
            return ResponseEntity.ok().body(false);
        }
    }
    @PostMapping("/password")
    public ResponseEntity<String> modifyPassword(PasswordChangeDTO passwordChangeDTO){
        try{
            accountFinderService.modifyPassword(passwordChangeDTO);
            return ResponseEntity.ok().body("비밀번호가 변경되었습니다. 다시 로그인해주세요.");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("알 수 없는 오류"+e.getMessage());
        }
    }
}
