package com.yunha.backend.repository;

import com.yunha.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM category WHERE category_user_code = ?1 " +
            "OR (category_user_code != ?1 AND category_is_private = 0)", nativeQuery = true)
    List<Category> getPublicCategoryList(Long userCode);


}
