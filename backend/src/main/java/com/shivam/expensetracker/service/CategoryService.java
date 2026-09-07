package com.shivam.expensetracker.service;

import com.shivam.expensetracker.dto.CategoryRequest;
import com.shivam.expensetracker.entity.Category;
import com.shivam.expensetracker.repository.CategoryRepository;
import com.shivam.expensetracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository; this.userRepository = userRepository;
    }

    @Transactional
    public Category createCategory(CategoryRequest request, String username) {
        Long userId = userRepository.findByUsername(username).orElseThrow().getId();
        Category category = new Category(request.getName(), userId);
        category.setIcon(request.getIcon());
        return categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public List<Category> getUserCategories(String username) {
        Long userId = userRepository.findByUsername(username).orElseThrow().getId();
        return categoryRepository.findByUserId(userId);
    }
}