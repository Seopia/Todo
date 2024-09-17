package com.yunha.backend.service;


import com.yunha.backend.dto.CategoryDTO;
import com.yunha.backend.entity.Category;
import com.yunha.backend.entity.User;
import com.yunha.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getPublicCategoryList() {

        List<Category> categoryList = categoryRepository.getPublicCategoryList(1L);

        // 사용하지 않는 데이터 거르기 위함
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        // dto로 변환
        for(Category category : categoryList){

            CategoryDTO categoryDTO = new CategoryDTO(
                    category.getCategoryCode(),
                    category.getCategoryName(),
                    category.getCategoryUser().getUserId(),
                    category.isCategoryIsPrivate());

            categoryDTOList.add(categoryDTO);

        }
        return categoryDTOList;
    }


    public void createMyCategory(CategoryDTO newCategoryDTO) {

        // dto -> entity
        Category newCategory = new Category(
                newCategoryDTO.getCategoryCode(),
                newCategoryDTO.getCategoryName(),
                new User(1L), // User 클래스 타입
                newCategoryDTO.isCategoryIsPrivate());

        categoryRepository.save(newCategory);

    }


    public void removeMyCategory(Long categoryCode) {

        categoryRepository.deleteById(categoryCode);

    }
}
