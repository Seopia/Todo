package com.yunha.backend.common;

import com.yunha.backend.entity.User;
import com.yunha.backend.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
    private final UserRepository userRepository;
    private final Tool tool;

    public FileService(UserRepository userRepository, Tool tool) {
        this.userRepository = userRepository;
        this.tool = tool;
    }

    @Transactional
    public void uploadFile(MultipartFile file, Long userCode) {
        User userEntity = userRepository.findByUserCode(userCode);
        String changedFileName = tool.upload(file);
        System.out.println(changedFileName);
        userEntity.setUserProfileImg(changedFileName);
        userRepository.save(userEntity);
    }

    public String getUserProfileImg(Long userCode) {
        return userRepository.findByUserCode(userCode).getUserProfileImg();
    }
}
