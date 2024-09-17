package com.yunha.backend.controller;


import com.yunha.backend.dto.CategoryDTO;
import com.yunha.backend.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {


    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }



    // 자신 카테고리 전체 + 다른 사용자 isPrivate = false 카테고리
    @GetMapping("/category")
    public ResponseEntity<?> getPublicCategory(){

        try{

            return ResponseEntity.ok().body(categoryService.getPublicCategoryList());

        }catch (Exception e){ // 스프링 데이터 jpa에서 select할 때 발생할 수 있는 모든 에러 참조/ 에러 커스텀하기

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("에러");

        }
    }



    // 내 카테고리 추가, 수정
    @PostMapping("/category")
    public ResponseEntity<String> createMyCategory(@RequestBody CategoryDTO newCategoryDTO){

        try {

            categoryService.createMyCategory(newCategoryDTO);
            return ResponseEntity.ok().body("카테고리 추가");

        } catch (Exception e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("에러");
        }

    }



    @DeleteMapping("/category/{categoryCode}")
    public ResponseEntity<String> removeMyCategory(@PathVariable Long categoryCode ){

        try {

            categoryService.removeMyCategory(categoryCode);
            return ResponseEntity.ok().body("카테고리 삭제");

        } catch (Exception e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("카테고리 삭제 에러");
        }

    }


}
