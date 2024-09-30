package com.yunha.backend.security.controller;

import com.yunha.backend.dto.FindAccountDTO;
import com.yunha.backend.security.service.AccountFinderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class AccountFinderController {
    private final AccountFinderService accountFinderService;

    public AccountFinderController(AccountFinderService accountFinderService) {
        this.accountFinderService = accountFinderService;
    }

    @PostMapping("/account/find-by-id-email")
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
    @GetMapping("/account/find-by-sc-num")
    public ResponseEntity<Boolean> isSecurityNumberCollect(@RequestParam String scNumber){
        return ResponseEntity.ok().body(true);
    }
}
