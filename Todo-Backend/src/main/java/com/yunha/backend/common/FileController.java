package com.yunha.backend.common;

import com.yunha.backend.security.dto.CustomUserDetails;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }


    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file, @AuthenticationPrincipal CustomUserDetails user){
        fileService.uploadFile(file,user.getUserCode());
        return ResponseEntity.ok().body(null);
    }
    @GetMapping("/profile")
    public ResponseEntity<String> getUserProfileImg(@AuthenticationPrincipal CustomUserDetails user){
        String fileName = fileService.getUserProfileImg(user.getUserCode());
        return ResponseEntity.ok().body("http://localhost:8080/uploads/"+fileName);
    }
}
