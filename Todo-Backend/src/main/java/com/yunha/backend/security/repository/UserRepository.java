package com.yunha.backend.security.repository;


import com.yunha.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * 유저가 존재하는지 확인하는 메서드
     * @param accountId 확인할 ID
     * @return true or false
     */
    Boolean existsByUserId(String accountId);

    User findByUserId(String username);

    Boolean existsByUserIdAndUserNickname(String userId,String userNickname);

    User findByUserCode(Long userCode);

    boolean existsByUserIdAndUserEmail(String userId, String userEmail);

    User findByUserIdAndUserEmail(String userId, String userEmail);
}
