package com.marketplace.product_service.service.impl;

import com.marketplace.product_service.dto.CategoryDto;
import com.marketplace.product_service.entity.Category;
import com.marketplace.product_service.mapper.CategoryMapper;
import com.marketplace.product_service.repository.CategoryRepo;
import com.marketplace.product_service.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepo categoryRepo, CategoryMapper categoryMapper){
        this.categoryRepo = categoryRepo;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        category.setName(categoryDto.getName());
        category.setDescription(category.getDescription());
        category.setParentId(categoryDto.getParentId());
        return categoryMapper.toDto(categoryRepo.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepo.findById(id).orElseThrow(()-> new RuntimeException("Category not found with id: "+id));
    }

    @Override
    public CategoryDto getCategory(Long id) {
        Category category = categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categoryList = categoryRepo.findAll();
        return categoryList.stream().map(categoryMapper::toDto).toList();
    }
}
